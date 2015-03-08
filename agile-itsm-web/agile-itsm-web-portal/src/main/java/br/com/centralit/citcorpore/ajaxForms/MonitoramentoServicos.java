package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes","unchecked"})
public class MonitoramentoServicos extends AjaxFormAction {

	
	@Override
	public Class getBeanClass() {
		return GerenciamentoServicosDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		exibeTarefas(document,request,response);
	}

	public void exibeTarefas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		int contador = 0;
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		ArrayList<SolicitacaoServicoDTO> resultSolicitacoes = new ArrayList<SolicitacaoServicoDTO>();
		List colTarefasFiltradasFinal = new ArrayList();
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		
		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO)document.getBean();	
		
		ExecucaoSolicitacaoService execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
		List<TarefaFluxoDTO> colTarefas = execucaoSolicitacaoService.recuperaTarefas(usuario.getLogin());
		if (colTarefas == null)
			return;
		
		if(gerenciamentoBean.getIdContrato()!= null){
			gerenciamentoBean.setNumeroContratoSel(""+gerenciamentoBean.getIdContrato());
		}
		
		gerenciamentoBean.setOrdenacaoAsc("false");
		gerenciamentoBean.setNomeCampoOrdenacao("");
		boolean bFiltroPorContrato = gerenciamentoBean.getNumeroContratoSel() != null && gerenciamentoBean.getNumeroContratoSel().length() > 0;
		boolean bFiltroPorSolicitacao = gerenciamentoBean.getIdGrupoAtual() != null && gerenciamentoBean.getIdGrupoAtual() > 0;
		List<TarefaFluxoDTO> colTarefasFiltradas = new ArrayList();
		if (!bFiltroPorContrato && !bFiltroPorSolicitacao) 
			colTarefasFiltradas.addAll(colTarefas);
		else{
			for (TarefaFluxoDTO tarefaDto : colTarefas) {
				boolean bAdicionar = false;
				String contrato = ""+((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getIdContrato();
				String grupoAtual = ""+((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getIdGrupoAtual();

				if (bFiltroPorContrato && bFiltroPorSolicitacao)
					bAdicionar = contrato.equals(gerenciamentoBean.getNumeroContratoSel()) && grupoAtual.equals(""+gerenciamentoBean.getIdGrupoAtual()); 
				else if (bFiltroPorContrato)
					bAdicionar = contrato.equals(gerenciamentoBean.getNumeroContratoSel());
				else
					bAdicionar = grupoAtual.equals(""+gerenciamentoBean.getIdGrupoAtual());
				if (bAdicionar)
					colTarefasFiltradas.add(tarefaDto);
			}
		}
		
		boolean asc = true;
		
		for(TarefaFluxoDTO tarefaDto : colTarefasFiltradas){
			SolicitacaoServicoDTO dtoSol = (SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto();
			dtoSol.setDataHoraLimiteToString(""); //Apenas forca atualizacao
			dtoSol.setDataHoraSolicitacaoToString(""); //Apenas forca atualizacao
			dtoSol.setDescricao("");
			dtoSol.setDescricaoSemFormatacao("");
			dtoSol.setResposta("");
			dtoSol.setDetalhamentoCausa("");
			dtoSol.setCaracteristica("");
			dtoSol.setColArquivosUpload(null);
			dtoSol.setColItensICSerialize(null);
			dtoSol.setColItensMudanca(null);
			dtoSol.setColItensProblema(null);
			dtoSol.setComplementoJustificativa("");
			dtoSol.setDemanda("");
			dtoSol.setDetalhamentoCausa("");
			dtoSol.setEditar("");
			dtoSol.setEmailcontato("");
			dtoSol.setEnviaEmailAcoes("");
			dtoSol.setEnviaEmailCriacao("");
			dtoSol.setEnviaEmailFinalizacao("");
			dtoSol.setEscalar("");
			dtoSol.setExibirCampoDescricao("");
			dtoSol.setObservacao("");
			dtoSol.setPalavraChave("");
			if (dtoSol.getSlaACombinar() == null){
			    dtoSol.setSlaACombinar("N");
			}
			
			
			colTarefasFiltradasFinal.add(tarefaDto);
			resultSolicitacoes.add((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto());
			
			int prazoHH = 0;
			int prazoMM = 0;
			if (dtoSol.getPrazoHH() != null){
			    prazoHH = dtoSol.getPrazoHH();
			}
			if (dtoSol.getPrazoMM() != null){
			    prazoMM = dtoSol.getPrazoMM();
			}	
			if (prazoHH == 0 && prazoMM == 0){
			    dtoSol.setSlaACombinar("S");
			    dtoSol.setAtrasoSLA(0);
			    dtoSol.setAtrasoSLAStr("");
			    dtoSol.setDataHoraLimiteStr("");
			}
			
			if (dtoSol.getSlaACombinar().equalsIgnoreCase("S")) {
				dtoSol.setDataHoraLimite(null);
				dtoSol.setAtrasoSLA(0);
				dtoSol.setAtrasoSLAStr("");
				if (asc) {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), 50, Calendar.YEAR).getTime()));
				} else {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), -10, Calendar.YEAR).getTime()));
				}
			}
			
			if (dtoSol.getSituacao().equalsIgnoreCase(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Suspensa.name())) {
				dtoSol.setDataHoraLimite(null);
				dtoSol.setAtrasoSLA(0);
				dtoSol.setAtrasoSLAStr("");
				if (asc) {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), -50, Calendar.YEAR).getTime()));
				} else {
					tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), 10, Calendar.YEAR).getTime()));
				}
			}
			
			if (dtoSol.getSolicitanteUnidade() != null && !dtoSol.getSolicitanteUnidade().trim().equalsIgnoreCase("")){
			    dtoSol.setSolicitante(""); //pra nao enviar no JSON
			}else{
			    dtoSol.setSolicitanteUnidade(dtoSol.getSolicitante());
			}
			
			dtoSol.setDataHoraSolicitacaoStr(dtoSol.obterDataHoraSolicitacaoStrWithLanguage(WebUtil.getLanguage(request)));
			dtoSol.setDataHoraLimiteStr(dtoSol.obterDataHoraLimiteStrWithLanguage(WebUtil.getLanguage(request)));
			
			if (gerenciamentoBean.getNomeCampoOrdenacao() != null && !gerenciamentoBean.getNomeCampoOrdenacao().trim().equalsIgnoreCase("")
					&& !gerenciamentoBean.getNomeCampoOrdenacao().trim().equalsIgnoreCase("dataHoraLimite")) {
				Collections.sort(resultSolicitacoes, new ObjectSimpleComparator(getWithGet(gerenciamentoBean.getNomeCampoOrdenacao()), (asc ? ObjectSimpleComparator.ASC : ObjectSimpleComparator.DESC)));
				colTarefasFiltradasFinal = (List<TarefaFluxoDTO>) reordena((ArrayList<TarefaFluxoDTO>) colTarefasFiltradasFinal, resultSolicitacoes);
			} else {
				if (asc) {
					Collections.sort(colTarefasFiltradasFinal, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC));
				} else {
					Collections.sort(colTarefasFiltradasFinal, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.DESC));
				}
			}
			contador++;
			if(contador>100)break;
		}
		//Collections.sort(colTarefasFiltradas, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC));
//		String tarefasStr = new Gson().toJson(colTarefasFiltradas);
//		tarefasStr = tarefasStr.replaceAll("\n", " ");
//		tarefasStr = tarefasStr.replaceAll("\r", " ");
//		tarefasStr = tarefasStr.replaceAll("\\\\n", " ");
		
		String tarefasStr = serializaTarefas(colTarefasFiltradasFinal, request);

		document.executeScript("exibirTarefas('" + tarefasStr + "');");
		document.executeScript("fechaJanelaAguarde();");
		
	}
	
	
	public void pesquisaItens(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{		
		exibeTarefas(document,request,response);
		
	}

	private String serializaTarefas(List<TarefaFluxoDTO> colTarefas, HttpServletRequest request) throws Exception {
		if (colTarefas == null)
			return null;
		for (TarefaFluxoDTO tarefaDto : colTarefas) {
			String elementoFluxo_serialize = br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getElementoFluxoDto(), WebUtil.getLanguage(request));
			String requisicaoSolicitacao_serialize = br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getSolicitacaoDto(), WebUtil.getLanguage(request));
			
			tarefaDto.setElementoFluxo_serialize(elementoFluxo_serialize);
			tarefaDto.setSolicitacao_serialize(requisicaoSolicitacao_serialize);
		}
		return br.com.citframework.util.WebUtil.serializeObjects(colTarefas, WebUtil.getLanguage(request));
	}
	
	/**
	 * Insere 'get' antes do elemento para se adequar à reflexão.
	 * 
	 * @param palavra
	 * @return
	 * @author breno.guimaraes
	 */
	private String getWithGet(String palavra) {
		return "get" + palavra.substring(0, 1).toUpperCase() + palavra.substring(1);
	}
	
	private ArrayList<TarefaFluxoDTO> reordena(ArrayList<TarefaFluxoDTO> tarefas, ArrayList<SolicitacaoServicoDTO> solicitacoes) {
		ArrayList<TarefaFluxoDTO> novaLista = new ArrayList<TarefaFluxoDTO>();
		for (SolicitacaoServicoDTO s : solicitacoes) {
			loopTarefas: for (TarefaFluxoDTO t : tarefas) {
				if (((SolicitacaoServicoDTO) t.getSolicitacaoDto()).getIdSolicitacaoServico().equals(s.getIdSolicitacaoServico())) {
					novaLista.add(t);
					break loopTarefas;
				}
			}
		}

		return novaLista;
	}


}
