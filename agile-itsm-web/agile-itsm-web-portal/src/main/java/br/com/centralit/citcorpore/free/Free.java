package br.com.centralit.citcorpore.free;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.util.UtilI18N;

public class Free {

	public static String getMsgCampoIndisponivel(HttpServletRequest request) {
		return "<br /><br /><font color='red'><b>" + UtilI18N.internacionaliza(request, "citsmart.mensagemEnterprise") + "</b></font><br /><br /><br />";
	}
}