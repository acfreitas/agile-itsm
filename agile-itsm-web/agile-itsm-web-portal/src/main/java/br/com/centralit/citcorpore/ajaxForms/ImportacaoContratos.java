package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ImportacaoContratosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ImportacaoContratosService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ImportacaoContratos extends AjaxFormAction {
	
	public Class getBeanClass() {
		return ImportacaoContratosDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		document.getSelectById("idContrato").removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, WebUtil.getUsuarioSistema(request));
		Collection colContratos = contratoService.listAtivos();
		document.getSelectById("idContrato").addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		document.getSelectById("idContrato").addOptions(colContratos, "idContrato", "numero", null);
		
		request.getSession(true).setAttribute("colUploadsGED", null);
	}
	
	public void importar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ImportacaoContratosDTO importacaoContratosDTO = (ImportacaoContratosDTO) document.getBean();
		ImportacaoContratosService importacaoContratosService = (ImportacaoContratosService) ServiceLocator.getInstance().getService(ImportacaoContratosService.class, WebUtil.getUsuarioSistema(request));
		
		ImportacaoContratosDTO importacaoContratosResultadoDto = new ImportacaoContratosDTO();
		importacaoContratosResultadoDto.setResultado(false);

		Collection colUploadsGED = (Collection) request.getSession(true).getAttribute("colUploadsGED");
		if (colUploadsGED == null || colUploadsGED.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "dataManager.naoHaArquivosImportar"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		for (Iterator it = colUploadsGED.iterator(); it.hasNext();) {
			UploadDTO uploadDTO = (UploadDTO) it.next();
			
			String ext = FilenameUtils.getExtension(uploadDTO.getPath());
			
			if (!ext.equalsIgnoreCase("xml")) {
				document.executeScript("JANELA_AGUARDE_MENU.hide();");
				document.alert(UtilI18N.internacionaliza(request, "importManualBI.importFalhaExtensaoInvalida"));
				return;					
			}
			
			try {
				String xmlSource = FileUtils.readFileToString(new File(uploadDTO.getPath()), "ISO-8859-1");
				
				if (!xmlSource.equals("")) {
					importacaoContratosResultadoDto = importacaoContratosService.persisteDados(request, importacaoContratosDTO.getIdContrato(), xmlSource);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (importacaoContratosResultadoDto != null && !importacaoContratosResultadoDto.isResultado()) {
				document.executeScript("JANELA_AGUARDE_MENU.hide();");
				document.alert(UtilI18N.internacionaliza(request, "importManualBI.importFalha"));
				if (!importacaoContratosResultadoDto.getMensagem().equals("")) {
					document.alert(importacaoContratosResultadoDto.getMensagem());
				} else {
					document.alert(UtilI18N.internacionaliza(request, "importManualBI.importFalha"));
				}
				return;
			} else {
				if (importacaoContratosResultadoDto != null && importacaoContratosResultadoDto.getMensagem() != null && !importacaoContratosResultadoDto.getMensagem().equals("")) {
					document.alert(UtilI18N.internacionaliza(request, "importacaoContratos.importSucessoComErros"));
					document.executeScript("$('#resultadoImportacao').html('<b>" + UtilI18N.internacionaliza(request, "bi.painelControle.conexao.logExecucao") + "</b><br/><br/>" + importacaoContratosResultadoDto.getMensagem() + "');");
				} else {
					document.alert(UtilI18N.internacionaliza(request, "importacaoContratos.importSucesso"));
					document.executeScript("$('#resultadoImportacao').html('');");
				}
			}
		}
		
		request.getSession(true).setAttribute("colUploadsGED", null);
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
		//document.executeScript("parent.fecharModalImportacaoManual();");

	}
	
}
