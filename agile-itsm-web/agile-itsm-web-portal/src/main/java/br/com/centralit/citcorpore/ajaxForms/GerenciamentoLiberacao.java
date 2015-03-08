package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GerenciamentoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ExecucaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class GerenciamentoLiberacao extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return GerenciamentoLiberacaoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		exibeTarefas(document, request, response);
	}

	public void exibeTarefas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sessao expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoLiberacaoDTO gerenciamentoBean = (GerenciamentoLiberacaoDTO) document.getBean();

		ExecucaoLiberacaoService execucaoLiberacaoService = (ExecucaoLiberacaoService) ServiceLocator.getInstance().getService(ExecucaoLiberacaoService.class, null);
		RequisicaoLiberacaoService liberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
		List<TarefaFluxoDTO> colTarefas = execucaoLiberacaoService.recuperaTarefas(usuario.getLogin());
		if (colTarefas == null)
			return;

		boolean bFiltroPorSolicitacao = gerenciamentoBean.getIdRequisicaoSel() != null && gerenciamentoBean.getIdRequisicaoSel().length() > 0;
		List<TarefaFluxoDTO> colTarefasFiltradas = new ArrayList();
		if (!bFiltroPorSolicitacao)
			colTarefasFiltradas.addAll(colTarefas);
		else {
			for (TarefaFluxoDTO tarefaDto : colTarefas) {
				boolean bAdicionar = false;
				String idRequisicao = "" + ((RequisicaoLiberacaoDTO) tarefaDto.getRequisicaoLiberacaoDto()).getIdRequisicaoLiberacao();
				bAdicionar = idRequisicao.indexOf(gerenciamentoBean.getIdRequisicaoSel()) >= 0;
				if (bAdicionar)
					colTarefasFiltradas.add(tarefaDto);
			}
		}
		List colTarefasFiltradasFinal = new ArrayList();
		HashMap mapAtr = new HashMap();
		mapAtr.put("-- Sem AtribuiÁ„o --", "-- Sem AtribuiÁ„o --");
		for (TarefaFluxoDTO tarefaDto : colTarefasFiltradas) {
			RequisicaoLiberacaoDTO requisicaoLiberacaoDto = (RequisicaoLiberacaoDTO) tarefaDto.getRequisicaoLiberacaoDto();
			requisicaoLiberacaoDto.setDataHoraLimiteToString(""); // Apenas forca atualizacao
			requisicaoLiberacaoDto.setDataHoraInicioToString(""); // Apenas forca atualizacao
			requisicaoLiberacaoDto.setDescricao("");
			int prazoHH = 0;
			int prazoMM = 0;
			if (requisicaoLiberacaoDto.getPrazoHH() != null) {
				prazoHH = requisicaoLiberacaoDto.getPrazoHH();
			}
			if (requisicaoLiberacaoDto.getPrazoMM() != null) {
				prazoMM = requisicaoLiberacaoDto.getPrazoMM();
			}

			if (tarefaDto.getResponsavelAtual() != null) {
				if (!mapAtr.containsKey(requisicaoLiberacaoDto.getResponsavelAtual())) {
					mapAtr.put(requisicaoLiberacaoDto.getResponsavelAtual(), requisicaoLiberacaoDto.getResponsavelAtual());
				}
			}
			
			requisicaoLiberacaoDto.setDataHoraInicioStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, requisicaoLiberacaoDto.getDataHoraInicio(), WebUtil.getLanguage(request)));
			requisicaoLiberacaoDto.setDataHoraSolicitacaoStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, requisicaoLiberacaoDto.getDataHoraInicio(), WebUtil.getLanguage(request)));
			requisicaoLiberacaoDto.setDataHoraTerminoStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, requisicaoLiberacaoDto.getDataHoraTermino(), WebUtil.getLanguage(request)));
			requisicaoLiberacaoDto.setDataInicioStr(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, requisicaoLiberacaoDto.getDataHoraInicioAgendada(), WebUtil.getLanguage(request)));
			requisicaoLiberacaoDto.setDataTerminoStr(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, requisicaoLiberacaoDto.getDataHoraTerminoAgendada(), WebUtil.getLanguage(request)));
			requisicaoLiberacaoDto.setDatahoraAprovacaoStr(UtilDatas.convertDateToString(TipoDate.TIMESTAMP_DEFAULT, requisicaoLiberacaoDto.getDatahoraAprovacao(), WebUtil.getLanguage(request)));
			//Verifica se usuario È o responsavel pela liberaÁ„o da requisiÁ„o
			//(usuario.getIdEmpregado() == dtoSol.getIdSolicitante() ){
			//	dtoSol.setAutorizadoLiberar("S");
			//}

			if (gerenciamentoBean.getAtribuidaCompartilhada() == null || gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase("")) {
				colTarefasFiltradasFinal.add(tarefaDto);
			} else {
				if (gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase(UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao"))) {
					if (requisicaoLiberacaoDto.getResponsavelAtual() == null || requisicaoLiberacaoDto.getResponsavelAtual().trim().equalsIgnoreCase("")) {
						colTarefasFiltradasFinal.add(tarefaDto);
					}
				} else {
					if (gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase(requisicaoLiberacaoDto.getResponsavelAtual())) {
						colTarefasFiltradasFinal.add(tarefaDto);
					}
				}
			}
		}
		String tarefasStr = serializaTarefas(colTarefasFiltradasFinal, request);
	
		/*
		 * Collections.sort(colTarefasFiltradasFinal, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC)); String tarefasStr = new Gson().toJson(colTarefasFiltradasFinal);
		 * tarefasStr = tarefasStr.replaceAll("\n", " "); tarefasStr = tarefasStr.replaceAll("\r", " "); tarefasStr = tarefasStr.replaceAll("\\\\n", " ");
		 */

        document.executeScript("exibirTarefas('" + tarefasStr + "');");

		document.getSelectById("atribuidaCompartilhada").removeAllOptions();
		document.getSelectById("atribuidaCompartilhada").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		
		for (Iterator it = mapAtr.values().iterator(); it.hasNext();) {
			String str = (String) it.next();
			document.getSelectById("atribuidaCompartilhada").addOption(str, str);
		}
		
		document.getSelectById("atribuidaCompartilhada").setValue(gerenciamentoBean.getAtribuidaCompartilhada());
		document.getJanelaPopupById("parent.JANELA_AGUARDE_MENU").hide();
	}

	public void preparaExecucaoTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sess„o expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoLiberacaoDTO gerenciamentoBean = (GerenciamentoLiberacaoDTO) document.getBean();
		if (gerenciamentoBean.getIdTarefa() == null)
			return;

		ExecucaoLiberacaoService execucaoLiberacaoService = (ExecucaoLiberacaoService) ServiceLocator.getInstance().getService(ExecucaoLiberacaoService.class, null);
		TarefaFluxoDTO tarefaDto = execucaoLiberacaoService.recuperaTarefa(usuario.getLogin(), gerenciamentoBean.getIdTarefa());
		if (tarefaDto == null || tarefaDto.getElementoFluxoDto() == null || !tarefaDto.getExecutar().equals("S") || tarefaDto.getElementoFluxoDto().getTipoInteracao() == null)
			return;

		if (tarefaDto.getElementoFluxoDto().getTipoInteracao().equals(Enumerados.INTERACAO_VISAO)) {
			if (tarefaDto.getIdVisao() != null) {
				document.executeScript("exibirVisao('Executar tarefa " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','" + tarefaDto.getIdVisao() + "','"
						+ tarefaDto.getElementoFluxoDto().getIdFluxo() + "','" + tarefaDto.getIdItemTrabalho() + "','" + gerenciamentoBean.getAcaoFluxo() + "');");
			} else {
				document.alert("Vis„o para tarefa \"" + tarefaDto.getElementoFluxoDto().getDocumentacao() + "\" n„o encontrada");
			}
		} else {
			String caracterParmURL = "?";
			if (tarefaDto.getElementoFluxoDto().getUrl().indexOf("?") > -1) { // Se na URL j√° conter ?, entao colocar &
				caracterParmURL = "&";
			}
			document.executeScript("exibirUrl('Executar tarefa " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','" + tarefaDto.getElementoFluxoDto().getUrl() + caracterParmURL
					+ "idRequisicaoLiberacao=" + ((RequisicaoLiberacaoDTO) tarefaDto.getRequisicaoLiberacaoDto()).getIdRequisicaoLiberacao() + "&idTarefa=" + tarefaDto.getIdItemTrabalho() + "&acaoFluxo="
					+ gerenciamentoBean.getAcaoFluxo() + "');");
		}
	}

	public void reativaRequisicao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sess„o expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoLiberacaoDTO gerenciamentoBean = (GerenciamentoLiberacaoDTO) document.getBean();
		if (gerenciamentoBean.getIdRequisicao() == null)
			return;

		RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, null);
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = requisicaoLiberacaoService.restoreAll(gerenciamentoBean.getIdRequisicao());
		 requisicaoLiberacaoService.reativa(usuario, requisicaoLiberacaoDto);
		exibeTarefas(document, request, response);
	}
	
	public void aprovarLiberacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sess„o expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoLiberacaoDTO gerenciamentoBean = (GerenciamentoLiberacaoDTO) document.getBean();
		if (gerenciamentoBean.getIdRequisicao() == null)
			return;

		RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, WebUtil.getUsuarioSistema(request));
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = requisicaoLiberacaoService.restoreAll(gerenciamentoBean.getIdRequisicao());
		
		if(requisicaoLiberacaoDto != null && requisicaoLiberacaoDto.getIdAprovador() == null){
			if(requisicaoLiberacaoDto != null){
				if(usuario.getIdUsuario() != null){
					requisicaoLiberacaoDto.setIdAprovador(usuario.getIdUsuario());
					requisicaoLiberacaoDto.setDatahoraAprovacao(UtilDatas.getDataHoraAtual());
					requisicaoLiberacaoDto.setUsuarioDto(usuario);
				}
			}
			requisicaoLiberacaoService.updateLiberacaoAprovada(requisicaoLiberacaoDto);
			document.alert(UtilI18N.internacionaliza(request, "requisicaoLiberacao.LiberacaoSucesso"));

		}else{
			document.alert(UtilI18N.internacionaliza(request, "requisicaoLiberacao.requisicaoLiderada"));
			return;
		}

		exibeTarefas(document, request, response);
	}

	private String serializaTarefas(List<TarefaFluxoDTO> colTarefas, HttpServletRequest request) throws Exception {
		if (colTarefas == null)
			return null;
		for (TarefaFluxoDTO tarefaDto : colTarefas) {
			String elementoFluxo_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getElementoFluxoDto(), WebUtil.getLanguage(request)));
			String requisicaoLiberacao_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getRequisicaoLiberacaoDto(), WebUtil.getLanguage(request)));
			
			tarefaDto.setElementoFluxo_serialize(elementoFluxo_serialize);
			tarefaDto.setSolicitacao_serialize(requisicaoLiberacao_serialize);
		}
		return br.com.citframework.util.WebUtil.serializeObjects(colTarefas, WebUtil.getLanguage(request));
	}

	public void capturaTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoLiberacaoDTO requisicaoBean = (GerenciamentoLiberacaoDTO) document.getBean();
		

		if (requisicaoBean.getIdTarefa() == null) {
			return;
		}
		
		RequisicaoLiberacaoService reqService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, WebUtil.getUsuarioSistema(request));
		RequisicaoLiberacaoDTO reqDto = new RequisicaoLiberacaoDTO();
		reqDto.setIdRequisicaoLiberacao(requisicaoBean.getIdRequisicao());
		reqDto = (RequisicaoLiberacaoDTO) reqService.restore(reqDto);

		reqDto.setIdProprietario(usuario.getIdUsuario());
		reqService.updateSimples(reqDto);
		
		ExecucaoLiberacaoService execucaoLiberacaoService = (ExecucaoLiberacaoService) ServiceLocator.getInstance().getService(ExecucaoLiberacaoService.class, WebUtil.getUsuarioSistema(request));
		execucaoLiberacaoService.executa(usuario, requisicaoBean.getIdTarefa(), Enumerados.ACAO_INICIAR); 
		exibeTarefas(document, request, response);

		requisicaoBean = null;
	}
	
	public void onClosePopUp(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		exibeTarefas(document,request,response);
	}
}
