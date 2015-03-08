package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.DemandaDTO;
import br.com.centralit.citcorpore.bean.ProjetoDTO;
import br.com.centralit.citcorpore.bean.SituacaoDemandaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.DemandaService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class Demanda extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		HTMLSelect comboCliente = (HTMLSelect) document.getSelectById("idCliente");
		HTMLSelect comboFluxo = (HTMLSelect) document.getSelectById("idFluxo");
		//HTMLSelect comboProjeto = (HTMLSelect) document.getSelectById("idProjeto");
		HTMLSelect comboTipoDemanda = (HTMLSelect) document.getSelectById("idTipoDemanda");
		
		/*//--- Fluxos
		FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);		
		Collection colFluxos = fluxoService.list();
				
		comboFluxo.addOption("", "--");
		comboFluxo.addOptions(colFluxos, "idFluxo", "nomeFluxo", null);*/
		
		//--- Tipos de Demanda
		TipoDemandaService tipoDemandaService = (TipoDemandaService) ServiceLocator.getInstance().getService(TipoDemandaService.class, null);
		Collection colTiposDemanda = tipoDemandaService.list();
		
		comboTipoDemanda.addOption("", "--");
		comboTipoDemanda.addOptions(colTiposDemanda, "idTipoDemanda", "nomeTipoDemanda", null);
		
		//--- Clientes
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		Collection colClientes = clienteService.list();
		
		comboCliente.addOption("", "--");
		comboCliente.addOptions(colClientes, "idCliente", "nomeFantasia", null);
		
				
		document.focusInFirstActivateField(null);
	}
	public void idCliente_onchange(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DemandaDTO demanda = (DemandaDTO) document.getBean();
		
		ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, null);
		ProjetoDTO prjPesq = new ProjetoDTO();
		prjPesq.setIdCliente(demanda.getIdCliente());
		prjPesq.setSituacao("A");
		
		Collection colProjetos = new ArrayList();
		if (demanda.getIdCliente() != null){
			colProjetos = projetoService.find(prjPesq);
		}
		
		HTMLSelect comboProjeto = (HTMLSelect) document.getSelectById("idProjeto");
		comboProjeto.removeAllOptions();
		comboProjeto.addOption("", "--");
		comboProjeto.addOptions(colProjetos, "idProjeto", "nomeProjeto", null);
		
		document.alert("Selecione o Projeto!");
	}
	public void Demanda_onsave(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		DemandaDTO demanda = (DemandaDTO) document.getBean();
		DemandaService demandaService = (DemandaService) ServiceLocator.getInstance().getService(DemandaService.class, null);
		
		if (demanda.getIdDemanda()==null || demanda.getIdDemanda().intValue()==0){
			demanda.setIdSituacaoDemanda(SituacaoDemandaDTO.SITUACAO_NAO_INICIADA);
			demandaService.create(demanda);
		}else{
			demandaService.update(demanda);
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.alert("Registro gravado com sucesso!");
	}
	public void Demanda_onrestore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		/*
		EmpregadoDTO empregado = (EmpregadoDTO) document.getBean();
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		
		empregado = (EmpregadoDTO) empregadoService.restore(empregado);
		
		HTMLForm form = document.getForm("form");
		form.clear();	
		form.setValues(empregado);
		
		document.alert("Registro recuperado !");
		*/
	}
	public Class getBeanClass(){
		return DemandaDTO.class;
	}
}
