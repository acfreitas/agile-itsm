package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.AgendaAtvPeriodicasDTO;
import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.EventoDTO;
import br.com.centralit.citcorpore.bean.EventosDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.EventosService;
import br.com.centralit.citcorpore.negocio.ProgramacaoAtividadeService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

public class Eventos extends AjaxFormAction {
	public Class getBeanClass() {
		return AgendaAtvPeriodicasDTO.class;
	}

	@SuppressWarnings("unchecked")
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AgendaAtvPeriodicasDTO agendaAtvPeriodicasDTO = (AgendaAtvPeriodicasDTO)document.getBean();
		agendaAtvPeriodicasDTO.setIdGrupoAtvPeriodica(0);
		String ID_GRUPO_ATV_PER = (String)request.getSession().getAttribute("ID_GRUPO_ATV_PER");
		String idGrupoPesquisa = (String)request.getSession().getAttribute("idGrupoPesquisa");
		if (ID_GRUPO_ATV_PER == null){
			ID_GRUPO_ATV_PER = "";
		}
		if (!ID_GRUPO_ATV_PER.equalsIgnoreCase("")){
			try{
				agendaAtvPeriodicasDTO.setIdGrupoAtvPeriodica(new Integer(ID_GRUPO_ATV_PER));
				agendaAtvPeriodicasDTO.setIdGrupoPesquisa(new Integer(idGrupoPesquisa));
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		java.util.Date dataInicio = new java.util.Date(agendaAtvPeriodicasDTO.getStart() * 1000); //A api fullcalendar - divide por 1000
		java.util.Date dataFim = new java.util.Date(agendaAtvPeriodicasDTO.getEnd() * 1000); //A api fullcalendar - divide por 1000
		
		List lst = new ArrayList();
		int qtdeDias = UtilDatas.dataDiff(dataInicio, dataFim);
		
		AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
		ProgramacaoAtividadeService programacaoAtividadeService = (ProgramacaoAtividadeService) ServiceLocator.getInstance().getService(ProgramacaoAtividadeService.class, null);
		
		UsuarioDTO usr = WebUtil.getUsuario(request);
		agendaAtvPeriodicasDTO.setIdEmpregado(usr.getIdEmpregado());
		Collection<AtividadePeriodicaDTO> colAtividades = atividadePeriodicaService.findByIdGrupoAtvPeriodica(agendaAtvPeriodicasDTO);
		if (colAtividades != null){
			for (AtividadePeriodicaDTO atividadePeriodicaDto : colAtividades) {
				Collection<EventoDTO> colEventos = programacaoAtividadeService.findEventosAgenda(atividadePeriodicaDto, dataInicio, qtdeDias);
				if (colEventos != null) {
				    for (EventoDTO eventoDto : colEventos) {
                        String tit = atividadePeriodicaDto.getTituloAtividade();
                        if (tit == null){
                            tit = "--";
                        }
                        tit = tit.replaceAll("'", "");
                        eventoDto.setUrl("javascript:validaEvento('" + eventoDto.getIdExecucao() + "'," 
                                    + atividadePeriodicaDto.getIdAtividadePeriodica() + ", " 
                                    + eventoDto.getIdProgramacao() + ", " 
                                    +"'" + tit + "'," 
                                    +"'" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, eventoDto.getData(), WebUtil.getLanguage(request)) + "'," 
                                    +"'" + eventoDto.getNumeroOS() + "',"
                                    +"'" + eventoDto.getDescricaoAtividadeOS() + "',"
                                    +"'" + eventoDto.getHoraInicio() +"');");
                        lst.add(eventoDto);
                    }
				}
			}
		}
		
		Gson gson = new Gson();
		
		String json = gson.toJson(lst);
		request.setAttribute("json_response", json);
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO"));
		if (usuario == null){
			document.alert("O usuário não está logado! Favor logar no sistema!");
			return;
		}
		
		EventosDTO eventos = (EventosDTO) document.getBean();
		//eventos.setIdUsuario(Integer.valueOf(usuario.getIdUsuario()));
		eventos.setIdEmpresa(usuario.getIdEmpresa());
		
		EventosService eventosService = (EventosService) ServiceLocator.getInstance().getService(EventosService.class, null);
		
		if (eventos.getIdEvento() == null || eventos.getIdEvento().intValue() == 0){
			eventos.setDataCriacao(UtilDatas.getDataAtual());
			eventosService.create(eventos);
		} else {
			eventosService.update(eventos);
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.alert("Registro gravado com sucesso!");
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EventosDTO eventos = (EventosDTO) document.getBean();
		EventosService eventosService = (EventosService) ServiceLocator.getInstance().getService(EventosService.class, null);
		
		eventos = (EventosDTO) eventosService.restore(eventos);
		
		HTMLForm form = document.getForm("form");
		form.clear();	
		form.setValues(eventos);
	}
	
}
