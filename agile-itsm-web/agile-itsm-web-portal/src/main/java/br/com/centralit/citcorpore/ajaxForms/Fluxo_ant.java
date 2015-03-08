package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.servico.ExecucaoFluxoService;
import br.com.centralit.bpm.servico.FluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.PermissoesFluxoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

import com.google.gson.Gson;

public class Fluxo_ant extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return FluxoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		
		PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);
		Collection<FluxoDTO> colFluxos = permissoesFluxoService.findFluxosByUsuario(usuario);
		if (colFluxos == null)
			return;

		document.executeScript("exibirFluxos('"+new Gson().toJson(colFluxos)+"');");
		exibeTarefas(document,request,response);
		
	}

	public void exibeTarefas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		
		ExecucaoFluxoService fluxoService = (ExecucaoFluxoService) ServiceLocator.getInstance().getService(ExecucaoFluxoService.class, null);
		List<TarefaFluxoDTO> colTarefas = fluxoService.recuperaTarefas(usuario.getLogin());
		if (colTarefas == null)
			return;
		
		document.executeScript("exibirTarefas('"+new Gson().toJson(colTarefas)+"');");
	}

	public void preparaCriacaoFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		
		FluxoDTO fluxoDto = (FluxoDTO)document.getBean();	
		if (fluxoDto.getIdFluxo() == null)
			return;
		
		FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);
		fluxoDto = (FluxoDTO) fluxoService.restore(fluxoDto);
		if (fluxoDto == null)
			return;
		
		document.executeScript("exibirVisao('Criação de novo "+fluxoDto.getNomeFluxo()+"','"+"???idVisao????"+"','"+fluxoDto.getIdFluxo()+"','');");
	}

	public void preparaExecucaoTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		
		FluxoDTO fluxoDto = (FluxoDTO)document.getBean();	
		if (fluxoDto.getIdTarefa() == null)
			return;
		
		ExecucaoFluxoService fluxoService = (ExecucaoFluxoService) ServiceLocator.getInstance().getService(ExecucaoFluxoService.class, null);
		TarefaFluxoDTO tarefaDto = fluxoService.recuperaTarefa(usuario.getLogin(), fluxoDto.getIdTarefa());
		if (tarefaDto == null || !tarefaDto.getExecutar().equals("S") || tarefaDto.getIdVisao() == null)
			return;
		
		document.executeScript("exibirVisao('Executar tarefa "+tarefaDto.getElementoFluxoDto().getDocumentacao()+"','"+tarefaDto.getIdVisao()+"','"+tarefaDto.getElementoFluxoDto().getIdFluxo()+"','"+tarefaDto.getIdInstancia()+"','"+fluxoDto.getAcaoFluxo()+"');");
	}
	
	public void delegaTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		
		FluxoDTO fluxoDto = (FluxoDTO)document.getBean();	
		if (fluxoDto.getIdTarefa() == null || fluxoDto.getUsuarioDestino() == null)
			return;
		
		ExecucaoFluxoService fluxoService = (ExecucaoFluxoService) ServiceLocator.getInstance().getService(ExecucaoFluxoService.class, null);
		//fluxoService.delegaTarefa(usuario.getLogin(), fluxoDto.getIdTarefa(), fluxoDto.getUsuarioDestino());
		exibeTarefas(document, request, response);
	}
}
