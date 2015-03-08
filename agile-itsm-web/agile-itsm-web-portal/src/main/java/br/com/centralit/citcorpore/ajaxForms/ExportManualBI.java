package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ExportManualBIDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bi.operation.BICitsmartOperation;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class ExportManualBI extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request,"citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '"+ Constantes.getValue("SERVER_ADDRESS")+ request.getContextPath() + "'");
			return;
		}
	}

	@Override
	public Class getBeanClass() {
		return ExportManualBIDTO.class;
	}
	
	public void exportarDownload(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		String idConexaoBI = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.BICITSMART_ID_CONEXAO, "");

		if (idConexaoBI == null || idConexaoBI.equals("")) {
			document.alert(UtilI18N.internacionaliza(request,"exportManualBI.exportFalha") + "\n" + UtilI18N.internacionaliza(request,"exportManualBI.exportFalhaIDConexaoNaoDefinido"));
		} else {
			document.executeScript("submitForm('formGetExportBI');");
		}
		
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	public void exportar(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		ExportManualBIDTO exportManualBIDTO = (ExportManualBIDTO) document.getBean();
		exportManualBIDTO.setPasta(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CAMINHOEXPORTACAOMANUALBICITSMART, ""));
		BICitsmartOperation biCitsmartOperation = new BICitsmartOperation();
		
		String idConexaoBI = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.BICITSMART_ID_CONEXAO, "");
		if (idConexaoBI == null || idConexaoBI.equals("")) {
			document.executeScript("JANELA_AGUARDE_MENU.hide();");
			document.alert(UtilI18N.internacionaliza(request,"exportManualBI.exportFalha") + "\n" + UtilI18N.internacionaliza(request,"exportManualBI.exportFalhaIDConexaoNaoDefinido"));
			return;
		}
		
		if (!exportManualBIDTO.getPasta().trim().equals("")) {
			if (biCitsmartOperation.exportacaoManualBICitsmart(exportManualBIDTO.getPasta())){
				document.executeScript("JANELA_AGUARDE_MENU.hide();");
				document.alert(UtilI18N.internacionaliza(request,"exportManualBI.exportSucesso") + "\n" + UtilI18N.internacionaliza(request,"exportManualBI.caminho") + ": " + exportManualBIDTO.getPasta());
			} else {
				document.executeScript("JANELA_AGUARDE_MENU.hide();");
				document.alert(UtilI18N.internacionaliza(request,"exportManualBI.exportFalha"));
			}
		} else {
			document.executeScript("JANELA_AGUARDE_MENU.hide();");
			document.alert(UtilI18N.internacionaliza(request,"exportManualBI.exportFalha") + "\n" + UtilI18N.internacionaliza(request,"exportManualBI.exportFalhaCaminhoNaoDefinido"));			
		}
	}

}