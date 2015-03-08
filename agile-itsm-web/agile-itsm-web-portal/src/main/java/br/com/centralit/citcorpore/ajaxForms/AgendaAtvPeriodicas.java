package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AgendaAtvPeriodicasDTO;
import br.com.centralit.citcorpore.negocio.GrupoAtvPeriodicaService;
import br.com.centralit.citcorpore.negocio.MotivoSuspensaoAtividadeService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class AgendaAtvPeriodicas extends AjaxFormAction {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return AgendaAtvPeriodicasDTO.class;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int idGrupo = 0;
		String ID_GRUPO_ATV_PER = (String)request.getSession().getAttribute("ID_GRUPO_ATV_PER");
		if (ID_GRUPO_ATV_PER == null){
			ID_GRUPO_ATV_PER = "";
		}
		if (!ID_GRUPO_ATV_PER.equalsIgnoreCase("")){
			try{
				idGrupo = new Integer(ID_GRUPO_ATV_PER);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

        request.getSession(true).setAttribute("colUploadsGED", null);
		HTMLSelect idGrupoAtvPeriodica = (HTMLSelect) document.getSelectById("idGrupoAtvPeriodica");
		GrupoAtvPeriodicaService grupoAtvPeriodicaService = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);
		Collection colGrupos = grupoAtvPeriodicaService.listGrupoAtividadePeriodicaAtiva();
		idGrupoAtvPeriodica.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		idGrupoAtvPeriodica.addOptions(colGrupos, "idGrupoAtvPeriodica", "nomeGrupoAtvPeriodica", "" + idGrupo);
		carregarComboMotivo(document, request, response);

        String grupoPesquisado = (String)request.getSession().getAttribute("idGrupoPesquisa");

        if (grupoPesquisado != null) {
        	int idGrupoPesquisa = Integer.parseInt(grupoPesquisado);
        	document.executeScript("setSelectGrupo(" + idGrupoPesquisa + ")");
        }



	}
	public void mudaGrupo(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AgendaAtvPeriodicasDTO agendaAtvPeriodicasDTO = (AgendaAtvPeriodicasDTO)document.getBean();
		if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null) {
			request.getSession().setAttribute("idGrupoPesquisa", "" + agendaAtvPeriodicasDTO.getIdGrupoPesquisa());
		}
		if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoAtvPeriodica() != null){
			request.getSession().setAttribute("ID_GRUPO_ATV_PER", "" + agendaAtvPeriodicasDTO.getIdGrupoAtvPeriodica());
			document.executeScript("refresh()");
		}
	}

	public void carregarComboMotivo(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		((HTMLSelect) document.getSelectById("idMotivoSuspensao")).removeAllOptions();
		HTMLSelect idMotivoSuspensao = (HTMLSelect) document.getSelectById("idMotivoSuspensao");
		idMotivoSuspensao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		MotivoSuspensaoAtividadeService motivoSuspensaoService = (MotivoSuspensaoAtividadeService) ServiceLocator.getInstance().getService(MotivoSuspensaoAtividadeService.class, null);
	    Collection colMotivos = motivoSuspensaoService.listarMotivosSuspensaoAtividadeAtivos();
	    idMotivoSuspensao.addOptions(colMotivos, "idMotivo", "descricao", "");
	}
}
