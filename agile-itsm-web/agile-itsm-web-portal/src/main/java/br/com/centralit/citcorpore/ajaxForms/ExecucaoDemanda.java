package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ExecucaoDemandaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ExecucaoDemandaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

public class ExecucaoDemanda extends AjaxFormAction {

	public Class getBeanClass() {
		return ExecucaoDemandaDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLTable tblMinhasTarefas = (HTMLTable) document.getTableById("tblMinhasTarefas");
		
		ExecucaoDemandaService execucaoDemandaService = (ExecucaoDemandaService) ServiceLocator.getInstance().getService(ExecucaoDemandaService.class, null);
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		Collection col = execucaoDemandaService.getAtividadesByGrupoAndPessoa(new Integer(usuario.getIdUsuario()), usuario.getGrupos());
		if (col != null){
			for(Iterator it = col.iterator(); it.hasNext();){ 
				ExecucaoDemandaDTO execucaoDemandaDTO = (ExecucaoDemandaDTO)it.next();
				
				String ser = br.com.citframework.util.WebUtil.serializeObject(execucaoDemandaDTO, WebUtil.getLanguage(request));
				if (ser != null){
					ser = ser.replaceAll("'", "");
				}
				execucaoDemandaDTO.setObjSerializado(ser);
			}
		}
		request.setAttribute("colecao", col);
		/*
		tblMinhasTarefas.deleteAllRows();
		tblMinhasTarefas.addRowsByCollection(col, 
				new String[] {"imagem", "nomeFluxo", "nomeEtapaHTML", "nomeAtividadeHTML", "expectativaFimStr", "prioridadeDescHTML", "nomeProjeto", "situacaoDescHTML", "nome", "qtdeHorasStr"}, 
				null, 
				"Já existe registrado esta demanda na tabela", 
				null, 
				"CHAMA_Opcoes", 
				null);
		tblMinhasTarefas.applyStyleClassInAllCells("tamanho10");
		*/
	}
	
	public void atribuir(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ExecucaoDemandaService execucaoDemandaService = (ExecucaoDemandaService) ServiceLocator.getInstance().getService(ExecucaoDemandaService.class, null);
		ExecucaoDemandaDTO execucaoDemandaBean = (ExecucaoDemandaDTO)document.getBean();
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}		
		
		if (execucaoDemandaBean.getQtdeHoras()==null){
			document.alert("Favor preencher a Quantidade de horas!");
			return;
		}
		if (execucaoDemandaBean.getTerminoPrevisto()==null){
			document.alert("Favor preencher o Prazo Final!");
			return;
		}		

		usuario = WebUtil.getUsuario(request);	
		Integer idUsuarioLogado = new Integer(usuario.getIdUsuario());
		execucaoDemandaBean.setIdEmpregadoLogado(idUsuarioLogado);
		
		boolean b = execucaoDemandaService.temAtividadeNaSequencia(execucaoDemandaBean);
		if (!b){
			document.alert("Não existe Atividade na Sequencia para este Fluxo! Não é possível efetuar atribuição!");
			return;			
		}
		execucaoDemandaService.updateAtribuir(execucaoDemandaBean);
		
		Collection col = execucaoDemandaService.getAtividadesByGrupoAndPessoa(new Integer(usuario.getIdUsuario()), usuario.getGrupos());
		
		/*
		HTMLTable tblMinhasTarefas = (HTMLTable) document.getTableById("tblMinhasTarefas");
		tblMinhasTarefas.deleteAllRows();
		tblMinhasTarefas.addRowsByCollection(col, 
				new String[] {"imagem", "nomeFluxo", "nomeEtapaHTML", "nomeAtividadeHTML", "expectativaFimStr", "prioridadeDescHTML", "nomeProjeto", "situacaoDescHTML", "nome", "qtdeHorasStr"}, 
				null, 
				"Já existe registrado esta demanda na tabela", 
				null, 
				"CHAMA_Opcoes", 
				null);		
		
		tblMinhasTarefas.applyStyleClassInAllCells("tamanho10");
		document.getComandsExecute().add("POPUP_ATRIBUIR.hide()");
		*/
		document.alert("Atribuição efetuada com sucesso!");
		document.executeScript("atualizarLista()");
	}
	
	public void paralisarDemandaCliente(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ExecucaoDemandaService execucaoDemandaService = (ExecucaoDemandaService) ServiceLocator.getInstance().getService(ExecucaoDemandaService.class, null);
		ExecucaoDemandaDTO execucaoDemandaBean = (ExecucaoDemandaDTO)document.getBean();

		UsuarioDTO usuario = WebUtil.getUsuario(request);	
		Integer idUsuarioLogado = new Integer(usuario.getIdUsuario());
		execucaoDemandaBean.setIdEmpregadoLogado(idUsuarioLogado);
		execucaoDemandaBean.setSituacao("C");
		execucaoDemandaService.updateStatus(execucaoDemandaBean);
		
		Collection col = execucaoDemandaService.getAtividadesByGrupoAndPessoa(new Integer(usuario.getIdUsuario()), usuario.getGrupos());
		/*
		HTMLTable tblMinhasTarefas = (HTMLTable) document.getTableById("tblMinhasTarefas");
		tblMinhasTarefas.deleteAllRows();
		tblMinhasTarefas.addRowsByCollection(col, 
				new String[] {"imagem", "nomeFluxo", "nomeEtapaHTML", "nomeAtividadeHTML", "expectativaFimStr", "prioridadeDescHTML", "nomeProjeto", "situacaoDescHTML", "nome", "qtdeHorasStr"}, 
				null, 
				"Já existe registrado esta demanda na tabela", 
				null, 
				"CHAMA_Opcoes", 
				null);		
		
		tblMinhasTarefas.applyStyleClassInAllCells("tamanho10");
		*/
		document.alert("Situação Atualizada com sucesso!");
		document.executeScript("atualizarLista()");
	}
	
	public void paralisarDemandaInterno(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ExecucaoDemandaService execucaoDemandaService = (ExecucaoDemandaService) ServiceLocator.getInstance().getService(ExecucaoDemandaService.class, null);
		ExecucaoDemandaDTO execucaoDemandaBean = (ExecucaoDemandaDTO)document.getBean();

		UsuarioDTO usuario = WebUtil.getUsuario(request);	
		Integer idUsuarioLogado = new Integer(usuario.getIdUsuario());
		execucaoDemandaBean.setIdEmpregadoLogado(idUsuarioLogado);
		execucaoDemandaBean.setSituacao("P");
		execucaoDemandaService.updateStatus(execucaoDemandaBean);
		
		Collection col = execucaoDemandaService.getAtividadesByGrupoAndPessoa(new Integer(usuario.getIdUsuario()), usuario.getGrupos());
		/*
		HTMLTable tblMinhasTarefas = (HTMLTable) document.getTableById("tblMinhasTarefas");
		tblMinhasTarefas.deleteAllRows();
		tblMinhasTarefas.addRowsByCollection(col, 
				new String[] {"imagem", "nomeFluxo", "nomeEtapaHTML", "nomeAtividadeHTML", "expectativaFimStr", "prioridadeDescHTML", "nomeProjeto", "situacaoDescHTML", "nome", "qtdeHorasStr"}, 
				null, 
				"Já existe registrado esta demanda na tabela", 
				null, 
				"CHAMA_Opcoes", 
				null);		
		
		tblMinhasTarefas.applyStyleClassInAllCells("tamanho10");
		*/
		document.alert("Situação Atualizada com sucesso!");
		document.executeScript("atualizarLista()");
	}
	
	public void alterarSituacaoEmExecucao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ExecucaoDemandaService execucaoDemandaService = (ExecucaoDemandaService) ServiceLocator.getInstance().getService(ExecucaoDemandaService.class, null);
		ExecucaoDemandaDTO execucaoDemandaBean = (ExecucaoDemandaDTO)document.getBean();

		UsuarioDTO usuario = WebUtil.getUsuario(request);	
		Integer idUsuarioLogado = new Integer(usuario.getIdUsuario());
		execucaoDemandaBean.setIdEmpregadoLogado(idUsuarioLogado);
		execucaoDemandaBean.setSituacao("I");
		execucaoDemandaService.updateStatus(execucaoDemandaBean);
		
		Collection col = execucaoDemandaService.getAtividadesByGrupoAndPessoa(new Integer(usuario.getIdUsuario()), usuario.getGrupos());
		/*
		HTMLTable tblMinhasTarefas = (HTMLTable) document.getTableById("tblMinhasTarefas");
		tblMinhasTarefas.deleteAllRows();
		tblMinhasTarefas.addRowsByCollection(col, 
				new String[] {"imagem", "nomeFluxo", "nomeEtapaHTML", "nomeAtividadeHTML", "expectativaFimStr", "prioridadeDescHTML", "nomeProjeto", "situacaoDescHTML", "nome", "qtdeHorasStr"}, 
				null, 
				"Já existe registrado esta demanda na tabela", 
				null, 
				"CHAMA_Opcoes", 
				null);		
		
		tblMinhasTarefas.applyStyleClassInAllCells("tamanho10");
		document.getComandsExecute().add("POPUP_OPCOES.hide()");
		*/
		document.alert("Situação Atualizada com sucesso!");
		document.executeScript("atualizarLista()");
	}

	public void finalizar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ExecucaoDemandaService execucaoDemandaService = (ExecucaoDemandaService) ServiceLocator.getInstance().getService(ExecucaoDemandaService.class, null);
		ExecucaoDemandaDTO execucaoDemandaBean = (ExecucaoDemandaDTO)document.getBean();

		UsuarioDTO usuario = WebUtil.getUsuario(request);	
		Integer idUsuarioLogado = new Integer(usuario.getIdUsuario());
		execucaoDemandaBean.setIdEmpregadoLogado(idUsuarioLogado);
		execucaoDemandaBean.setSituacao("F");
		execucaoDemandaBean.setTerminoReal(UtilDatas.getDataAtual());
		execucaoDemandaBean.setIdFluxo(execucaoDemandaBean.getIdFluxoSelecionado());
		execucaoDemandaService.updateFinalizar(execucaoDemandaBean);
		
		Collection col = execucaoDemandaService.getAtividadesByGrupoAndPessoa(new Integer(usuario.getIdUsuario()), usuario.getGrupos());
		/*
		HTMLTable tblMinhasTarefas = (HTMLTable) document.getTableById("tblMinhasTarefas");
		tblMinhasTarefas.deleteAllRows();
		tblMinhasTarefas.addRowsByCollection(col, 
				new String[] {"imagem", "nomeFluxo", "nomeEtapaHTML", "nomeAtividadeHTML", "expectativaFimStr", "prioridadeDescHTML", "nomeProjeto", "situacaoDescHTML", "nome", "qtdeHorasStr"}, 
				null, 
				"Já existe registrado esta demanda na tabela", 
				null, 
				"CHAMA_Opcoes", 
				null);		
		
		tblMinhasTarefas.applyStyleClassInAllCells("tamanho10");
		document.getComandsExecute().add("POPUP_OPCOES.hide()");
		*/
		document.alert("Finalização efetuada com sucesso!");
		document.executeScript("atualizarLista()");
	}
}
