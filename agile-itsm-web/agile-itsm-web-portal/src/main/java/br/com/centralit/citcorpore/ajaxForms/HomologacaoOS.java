package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.DemandaDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.DemandaService;
import br.com.centralit.citcorpore.negocio.OSService;
import br.com.centralit.citcorpore.negocio.TipoOSService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilFormatacao;

public class HomologacaoOS extends AjaxFormAction {
	public Class getBeanClass(){
		return OSDTO.class;
	}	
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
		
		/*
		comboIdSituacaoFuncional.addOption("", "-- Selecione --");
		comboIdSituacaoFuncional.addOption("1", "Em cadastro");
		comboIdSituacaoFuncional.addOption("2", "Enviada para aprovação");
		*/
		
		HTMLSelect idContrato = (HTMLSelect) document.getSelectById("idContrato");
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContratos = contratoService.list();
		idContrato.addOption("", "--");
		idContrato.addOptions(colContratos, "idContrato", "numero", null);
		
		HTMLSelect idClassificacaoOS = (HTMLSelect) document.getSelectById("idClassificacaoOS");
		TipoOSService tipoOSService = (TipoOSService) ServiceLocator.getInstance().getService(TipoOSService.class, null);
		Collection colTiposOS = tipoOSService.list();
		idClassificacaoOS.addOption("", "--");
		idClassificacaoOS.addOptions(colTiposOS, "idClassificacaoOS", "descricao", null);		
		
		document.focusInFirstActivateField(null);
	}
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		OSDTO os = (OSDTO) document.getBean();
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		Collection colItens = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(DemandaDTO.class, "colItens_Serialize", request);
		
		os.setColItens(colItens);
		if (os.getIdOS()==null || os.getIdOS().intValue()==0){
			osService.create(os);
		}else{
			osService.update(os);
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		
		document.executeScript("GRID_ITENS.deleteAllRows()");
		
		document.alert("Registro gravado com sucesso!");		
	}
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		OSDTO os = (OSDTO) document.getBean();
		OSService osService = (OSService) ServiceLocator.getInstance().getService(OSService.class, null);
		DemandaService demandaService = (DemandaService) ServiceLocator.getInstance().getService(DemandaService.class, null);
		
		os = (OSDTO)osService.restore(os);
		
		HTMLForm form = document.getForm("form");
		form.clear();	
		form.setValues(os);		
		
		document.executeScript("GRID_ITENS.deleteAllRows()");
		
		if (os != null){
			int i = 0;
			Collection col = demandaService.findByIdOS(os.getIdOS());
			for(Iterator it = col.iterator(); it.hasNext();){
				i++;
				DemandaDTO demandaDTO = (DemandaDTO)it.next();
				document.executeScript("GRID_ITENS.addRow()");
				document.executeScript("seqSelecionada = NumberUtil.zerosAEsquerda(GRID_ITENS.getMaxIndex(),5)");
				String strQtde = UtilFormatacao.formatDouble(demandaDTO.getCustoTotal(), 2);
				if (demandaDTO.getGlosa() == null){
					demandaDTO.setGlosa(new Double(0));
				}
				String strGlosa = UtilFormatacao.formatDouble(demandaDTO.getGlosa(), 2);
				String strDet = demandaDTO.getDetalhamento();
				String strObs = demandaDTO.getObservacao();
				if (strDet != null){
					strDet = strDet.replaceAll("'", "");
				}
				if (strObs != null){
					strObs = strDet.replaceAll("'", "");
				}				
				
				document.executeScript("setaRestoreItem('" + demandaDTO.getComplexidade() + "'," +
						"'" + br.com.citframework.util.WebUtil.codificaEnter(strDet) + "'," +
						"'" + br.com.citframework.util.WebUtil.codificaEnter(strObs) + "'," +
						"'" + strQtde + "'," +
						"'" + strGlosa + "'" +
						")");
			}
		}		
		
		document.alert("Registro recuperado !");
	}	

}
