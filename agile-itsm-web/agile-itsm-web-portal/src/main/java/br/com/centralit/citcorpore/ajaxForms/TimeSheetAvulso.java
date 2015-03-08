package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.TimeSheetDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.TimeSheetService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class TimeSheetAvulso extends AjaxFormAction {

	public Class getBeanClass() {
		return TimeSheetDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest arg1,
			HttpServletResponse arg2) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(arg1);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + arg1.getContextPath() + "'");
			return;
		}		
		
		HTMLSelect comboClientes = (HTMLSelect) document.getSelectById("idCliente");
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		
		Collection colClientes = clienteService.list();
				
		comboClientes.addOption("", "--");
		comboClientes.addOptions(colClientes, "idCliente", "nomeFantasia", null);		
	}
	
	public void idCliente_onchange(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeSheetDTO timeSheet = (TimeSheetDTO) document.getBean();
		
		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
		ProjetoDTO prjPesq = new ProjetoDTO();
		prjPesq.setIdCliente(timeSheet.getIdCliente());
		prjPesq.setSituacao("A");
		
		Collection colProjetos = new ArrayList();
		if (timeSheet.getIdCliente() != null){
			colProjetos = projetoService.find(prjPesq);
		}
		
		HTMLSelect comboProjeto = (HTMLSelect) document.getSelectById("idProjeto");
		comboProjeto.removeAllOptions();
		comboProjeto.addOption("", "--");
		comboProjeto.addOptions(colProjetos, "idProjeto", "nomeProjeto", null);
		
		document.alert("Selecione o Projeto!");		
	}

	public void TimeSheetAvulso_onsave(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TimeSheetService timeSheetService = (TimeSheetService) ServiceLocator.getInstance().getService(TimeSheetService.class, null);
		TimeSheetDTO timeSheetBean = (TimeSheetDTO)document.getBean();
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + request.getContextPath() + "'");
			return;
		}		
		timeSheetBean.setIdEmpregado(new Integer(usuario.getIdUsuario()));
		
		if (timeSheetBean.getData()==null){
			document.alert("Informe uma data correta!");
			HTMLElement txtData = document.getElementById("data");
			txtData.setFocus();
			return;
		}
		
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		EmpregadoDTO empregadoBean = new EmpregadoDTO();
		empregadoBean.setIdEmpregado(timeSheetBean.getIdEmpregado());
		empregadoBean = (EmpregadoDTO) empregadoService.restore(empregadoBean);
		
		if (empregadoBean == null){
			document.alert("ERRO: Não foi possivel recuperar dados do empregado logado!");
			return;
		}
		
		timeSheetBean.setCustoPorHora(empregadoBean.getCustoPorHora());
		
		timeSheetService.create(timeSheetBean);
		
		document.alert("TimeSheet registrado com sucesso!");
		
		HTMLForm form = document.getForm("form");
		form.clear();
	}
}
