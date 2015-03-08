package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SuspensaoReativacaoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class SuspensaoReativacaoSolicitacao extends AjaxFormAction {

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return SuspensaoReativacaoSolicitacaoDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		preencherComboContratoeGrupoeJustificativa(document,request,response);
	}
	
	public void processarSolicitacoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		
		SuspensaoReativacaoSolicitacaoDTO suspensaoReativacaoSolicitacaoDTO = (SuspensaoReativacaoSolicitacaoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		
		
		Collection<SolicitacaoServicoDTO> listaEmAndamento = null;
		Collection<SolicitacaoServicoDTO> listaSuspensas = null;
		
		if(suspensaoReativacaoSolicitacaoDTO.getTipoAcao().equals("suspender")){
			listaEmAndamento = solicitacaoServicoService.listarSolicitacoesAbertasEmAndamentoPorGrupo(Integer.parseInt(suspensaoReativacaoSolicitacaoDTO.getIdGrupo()), "A");
		}else{
			listaSuspensas = solicitacaoServicoService.listarSolicitacoesMultadasSuspensasPorGrupo(Integer.parseInt(suspensaoReativacaoSolicitacaoDTO.getIdGrupo()), "M");
		}
		
		if(listaEmAndamento!=null){
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaEmAndamento) {
					
				SolicitacaoServicoDTO solicitacaoServicoDto = solicitacaoServicoService.restoreAll(solicitacaoServicoDTO.getIdSolicitacaoServico());
					
				solicitacaoServicoDto.setIdJustificativa(suspensaoReativacaoSolicitacaoDTO.getIdJustificativa());
				solicitacaoServicoDto.setComplementoJustificativa(suspensaoReativacaoSolicitacaoDTO.getSolicitante()+" - "+suspensaoReativacaoSolicitacaoDTO.getJustificativa());
					
				if(solicitacaoServicoDto!=null){
					if(suspensaoReativacaoSolicitacaoDTO.getTipoAcao().equals("suspender") && solicitacaoServicoDto.emAtendimento()){
						solicitacaoServicoDto.setSituacaoSLA(SituacaoSLA.M.name());
						solicitacaoServicoService.suspende(usuario, solicitacaoServicoDto);
					}
				}
				
			}
			document.executeScript("parent.refreshTelaGerenciamento();");
			document.executeScript("parent.fecharModalSuspensaoReativacaoSolicitacao();");
			document.alert(UtilI18N.internacionaliza(request, "suspensaoReativacaoSolicitacao.solicitacaoGrupoSuspensa"));
		} else if(listaSuspensas!=null){
			for (SolicitacaoServicoDTO solicitacaoServicoDTO : listaSuspensas) {
				
				SolicitacaoServicoDTO solicitacaoServicoDto = solicitacaoServicoService.restoreAll(solicitacaoServicoDTO.getIdSolicitacaoServico());
					
				solicitacaoServicoDto.setIdJustificativa(suspensaoReativacaoSolicitacaoDTO.getIdJustificativa());
				solicitacaoServicoDto.setComplementoJustificativa(suspensaoReativacaoSolicitacaoDTO.getJustificativa());
					
				if(solicitacaoServicoDto!=null){
					if(suspensaoReativacaoSolicitacaoDTO.getTipoAcao().equals("reativar") && solicitacaoServicoDto.suspensa()){
						//solicitacaoServicoDto.setSituacaoSLA("A");
						solicitacaoServicoService.reativa(usuario, solicitacaoServicoDto);
					}
				}
				
			}
			document.executeScript("parent.refreshTelaGerenciamento();");
			document.executeScript("parent.fecharModalSuspensaoReativacaoSolicitacao();");
			document.alert(UtilI18N.internacionaliza(request, "suspensaoReativacaoSolicitacao.solicitacaoGrupoReativada"));
		} else{
			document.executeScript("parent.refreshTelaGerenciamento();");
			document.executeScript("parent.fecharModalSuspensaoReativacaoSolicitacao();");
			document.alert(UtilI18N.internacionaliza(request, "suspensaoReativacaoSolicitacao.naoHaSolicitacaoSeremReativadaSuspensa"));
			
		}
		
	}
	
	public void preencherComboContratoeGrupoeJustificativa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		JustificativaSolicitacaoService justificativaService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		
		Collection<ContratoDTO> listaContrato = new ArrayList<ContratoDTO>();
		Collection<GrupoDTO> listaGrupo = new ArrayList<GrupoDTO>();
		
		listaContrato = contratoService.listAtivos();
		listaGrupo = grupoService.listGruposPorUsuario(usuario.getIdUsuario());
		Collection colJustificativas = justificativaService.listAtivasParaSuspensao();
		
		HTMLSelect comboContrato = document.getSelectById("idContrato");
		HTMLSelect comboGrupo = document.getSelectById("idGrupo");
		HTMLSelect comboJustificativa = document.getSelectById("idJustificativa");
		
		comboContrato.removeAllOptions();
		comboGrupo.removeAllOptions();
		comboJustificativa.removeAllOptions();
		
		comboJustificativa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboGrupo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		
		if(listaContrato!=null){
			for (ContratoDTO contratoDTO : listaContrato) {
				comboContrato.addOption(contratoDTO.getIdContrato().toString(), StringEscapeUtils.escapeJavaScript(contratoDTO.getNumero() + " de "+UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDTO.getDataContrato(), WebUtil.getLanguage(request))));
			}
		}
		if(listaGrupo!=null){
			for (GrupoDTO grupoDTO : listaGrupo) {
				comboGrupo.addOption(grupoDTO.getIdGrupo().toString(), StringEscapeUtils.escapeJavaScript(grupoDTO.getNome()));
			}
		}
		
		if(colJustificativas!=null){
			comboJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
		}
		
	}

}
