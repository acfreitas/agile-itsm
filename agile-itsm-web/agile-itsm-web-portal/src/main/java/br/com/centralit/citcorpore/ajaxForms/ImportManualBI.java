package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ImportManualBIDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bi.operation.BICitsmartOperation;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class ImportManualBI extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		request.getSession(true).setAttribute("colUploadsGED", null);
	}

	@Override
	public Class getBeanClass() {
		return ImportManualBIDTO.class;
	}

	public void importar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ImportManualBIDTO importManualBIDTO = (ImportManualBIDTO) document.getBean();
		BICitsmartOperation biCitsmartOperation = new BICitsmartOperation();
		
		if (importManualBIDTO.getIdConexaoBI() != null && !importManualBIDTO.getIdConexaoBI().equals("")) {
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
				
				boolean importResult = biCitsmartOperation.importacaoManualBICitsmart(importManualBIDTO.getIdConexaoBI(), uploadDTO.getPath());
				
				if (!importResult) {
					document.executeScript("JANELA_AGUARDE_MENU.hide();");
					document.alert(UtilI18N.internacionaliza(request, "importManualBI.importFalha"));
					return;
				}
			}
			
			request.getSession(true).setAttribute("colUploadsGED", null);
			document.alert(UtilI18N.internacionaliza(request, "importManualBI.importSucesso"));
			document.executeScript("JANELA_AGUARDE_MENU.hide();");
			document.executeScript("parent.fecharModalImportacaoManual();");
		} else {
			document.executeScript("JANELA_AGUARDE_MENU.hide();");
			document.alert(UtilI18N.internacionaliza(request, "conexaoBI.informeIdConexao"));			
		}

	}

}