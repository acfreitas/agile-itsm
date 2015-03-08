package br.com.centralit.citcorpore.metainfo.complementos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ComplementoSLA_MostrarContratosTerceiro extends ComplementoSLA_MostrarContratos {
	public void execute(HttpServletRequest request, HttpServletResponse response){
		this.execute(request, response, "U");
	}
}
