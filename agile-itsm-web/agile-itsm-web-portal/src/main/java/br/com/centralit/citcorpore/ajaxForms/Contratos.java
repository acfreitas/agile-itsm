package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MoedaDao;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class Contratos extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		/*
		if (!WebUtil.isUserInGroup(request, Constantes.getValue("GRUPO_DIRETORIA"))){
			document.alert("Você não tem permissão para acessar esta funcionalidade!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/index/index.jsp'");
			return;			
		}		
		*/
		
		HTMLSelect comboIdSituacaoFuncional = (HTMLSelect) document.getSelectById("situacao");
		HTMLSelect comboClientes = (HTMLSelect) document.getSelectById("idCliente");
		HTMLSelect idFluxo = (HTMLSelect) document.getSelectById("idFluxo");
		HTMLSelect idMoeda = (HTMLSelect) document.getSelectById("idMoeda");
		
		comboIdSituacaoFuncional.addOption("", "-- Selecione --");
		comboIdSituacaoFuncional.addOption("A", "Ativo");
		comboIdSituacaoFuncional.addOption("C", "Cancelado");
		comboIdSituacaoFuncional.addOption("F", "Finalizado");
		comboIdSituacaoFuncional.addOption("P", "Paralisado");
		
		
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		
		Collection colClientes = clienteService.list();
				
		comboClientes.addOption("", "--");
		comboClientes.addOptions(colClientes, "idCliente", "nomeFantasia", null);	
		
		//FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);
		//Collection colFluxos = fluxoService.list();
		
		//idFluxo.addOption("", "--");
		//idFluxo.addOptions(colFluxos, "idFluxo", "nomeFluxo", null);	
		
		MoedaDao moedaDao = new MoedaDao();
		Collection colMoedas = moedaDao.list();
		
		idMoeda.addOption("", "--");
		idMoeda.addOptions(colMoedas, "idMoeda", "nomeMoeda", null);		
		
		document.focusInFirstActivateField(null);
	}
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ContratoDTO contratoDTO = (ContratoDTO) document.getBean();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		
		contratoDTO.setTipo("C");
		if (contratoDTO.getIdContrato()==null || contratoDTO.getIdContrato().intValue()==0){
			contratoService.create(contratoDTO);
		}else{
			contratoService.update(contratoDTO);
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.alert("Registro gravado com sucesso!");
	}
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		ContratoDTO contratoDTO = (ContratoDTO) document.getBean();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		
		contratoDTO = (ContratoDTO) contratoService.restore(contratoDTO);
		
		HTMLForm form = document.getForm("form");
		form.clear();	
		form.setValues(contratoDTO);
		
		document.alert("Registro recuperado !");
	}
	public Class getBeanClass(){
		return ContratoDTO.class;
	}
}
