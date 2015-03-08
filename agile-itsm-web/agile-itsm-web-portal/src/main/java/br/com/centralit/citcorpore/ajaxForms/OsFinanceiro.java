package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.DemandaService;
import br.com.centralit.citcorpore.negocio.OSService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class OsFinanceiro extends AjaxFormAction {
	public Class getBeanClass(){
		return OSDTO.class;
	}	
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OSDTO os = (OSDTO) document.getBean();
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		DemandaService demandaService = (DemandaService) ServiceLocator.getInstance().getService(DemandaService.class, null);
		if (os.getIdContrato() != null){
			//Collection col = osService.findByIdContratoAndSituacaoAndPeriodo(os.getIdContrato(), new Integer(6), os.getDataInicio(), os.getDataFim());
			Collection col = null;
			if (col != null){
				for(Iterator it = col.iterator(); it.hasNext();){
					OSDTO osDto = (OSDTO)it.next();
					
					Collection colDem = demandaService.findByIdOS(osDto.getIdOS());
					osDto.setColItens(colDem);
				}
			}
			request.setAttribute("colecao", col);
			
			ContratoDTO contratoDto = new ContratoDTO();
			contratoDto.setIdContrato(os.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			if (contratoDto != null){
				request.setAttribute("cotacaoMoeda", contratoDto.getCotacaoMoeda());
			}
		}
		
		document.getForm("form").setValues(os);
		
		HTMLSelect idContrato = (HTMLSelect) document.getSelectById("idContrato");
		Collection colContratos = contratoService.list();
		idContrato.addOption("", "--");
		idContrato.addOptions(colContratos, "idContrato", "numero", "" + os.getIdContrato());		
	}
}
