package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CustoAdicionalProjetoDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.CustoAdicionalProjetoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class CustoAdicionalProjeto extends AjaxFormAction {

	public Class getBeanClass() {
		return CustoAdicionalProjetoDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse arg2) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		/*
		if (!WebUtil.isUserInGroup(request, Constantes.getValue("GRUPO_FSW_GPROJ")) &&
				!WebUtil.isUserInGroup(request, Constantes.getValue("GRUPO_DIRETORIA"))){
			document.alert("Você não tem permissão para acessar esta funcionalidade!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/index/index.jsp'");
			return;			
		}	
		*/	
		
		HTMLSelect comboTipoCusto = (HTMLSelect) document.getSelectById("tipoCusto");
		HTMLSelect comboClientes = (HTMLSelect) document.getSelectById("idCliente");
		
		comboTipoCusto.addOption("", "-- Selecione --");
		comboTipoCusto.addOption("C", "Despesas de Comerciais com o Projeto"); //Despesas de Comerciais com o Projeto
		comboTipoCusto.addOption("L", "Despesas de Locomoção (Taxi, etc.)"); //Despesas de Locomocao (Taxi, ...)
		comboTipoCusto.addOption("V", "Despesas de Viagem"); //Despesas de Viagem
		comboTipoCusto.addOption("D", "Diárias de Hotel"); //Diárias de Hotel
		comboTipoCusto.addOption("M", "Material"); //Material
		comboTipoCusto.addOption("O", "Outros"); //Outros
		comboTipoCusto.addOption("H", "Recursos Humanos"); //Humano
		comboTipoCusto.addOption("R", "Refeição (Almoço, lanches, etc.)"); //Refeicao
		
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		
		Collection colClientes = clienteService.list();
				
		comboClientes.addOption("", "--");
		comboClientes.addOptions(colClientes, "idCliente", "nomeFantasia", null);		
		
		document.focusInFirstActivateField(null);		
	}
	public void idCliente_onchange(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		CustoAdicionalProjetoDTO custoAdicional = (CustoAdicionalProjetoDTO) document.getBean();
		
		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
		ProjetoDTO prjPesq = new ProjetoDTO();
		prjPesq.setIdCliente(custoAdicional.getIdCliente());
		prjPesq.setSituacao("A");
		
		Collection colProjetos = new ArrayList();
		if (custoAdicional.getIdCliente() != null){
			colProjetos = projetoService.find(prjPesq);
		}
		
		HTMLSelect comboProjeto = (HTMLSelect) document.getSelectById("idProjeto");
		comboProjeto.removeAllOptions();
		comboProjeto.addOption("", "--");
		comboProjeto.addOptions(colProjetos, "idProjeto", "nomeProjeto", null);
		
		document.alert("Selecione o Projeto!");
	}
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		CustoAdicionalProjetoDTO custoAdicional = (CustoAdicionalProjetoDTO) document.getBean();
		CustoAdicionalProjetoService cusAddService = (CustoAdicionalProjetoService) ServiceLocator.getInstance().getService(CustoAdicionalProjetoService.class, null);
		
		if (custoAdicional.getIdCustoAdicional()==null || custoAdicional.getIdCustoAdicional().intValue()==0){
			cusAddService.create(custoAdicional);
		}else{
			cusAddService.update(custoAdicional);
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.alert("Registro gravado com sucesso!");
	}	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		CustoAdicionalProjetoDTO custoAdicional = (CustoAdicionalProjetoDTO) document.getBean();
		CustoAdicionalProjetoService cusAddService = (CustoAdicionalProjetoService) ServiceLocator.getInstance().getService(CustoAdicionalProjetoService.class, null);
		
		custoAdicional = (CustoAdicionalProjetoDTO) cusAddService.restore(custoAdicional);

		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
		ProjetoDTO prjPesq = new ProjetoDTO();
		prjPesq.setIdProjeto(custoAdicional.getIdProjeto());
		prjPesq = (ProjetoDTO) projetoService.restore(prjPesq);		
		custoAdicional.setIdCliente(prjPesq.getIdCliente());
		
		prjPesq = new ProjetoDTO();
		prjPesq.setIdCliente(custoAdicional.getIdCliente());
		prjPesq.setSituacao("A");
		
		Collection colProjetos = new ArrayList();
		if (custoAdicional.getIdCliente() != null){
			colProjetos = projetoService.find(prjPesq);
		}
		
		HTMLSelect comboProjeto = (HTMLSelect) document.getSelectById("idProjeto");
		comboProjeto.removeAllOptions();
		comboProjeto.addOption("", "--");
		comboProjeto.addOptions(colProjetos, "idProjeto", "nomeProjeto", null);
		
		HTMLForm form = document.getForm("form");
		form.clear();	
		form.setValues(custoAdicional);
		
		document.alert("Registro recuperado !");
	}	
}
