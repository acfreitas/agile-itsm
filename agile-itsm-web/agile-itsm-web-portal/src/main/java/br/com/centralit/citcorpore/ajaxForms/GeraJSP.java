package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BIConsultaColunasService;
import br.com.centralit.citcorpore.negocio.BIConsultaService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citgerencial.util.WebUtilGerencial;
import br.com.citframework.dto.Usuario;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilTratamentoArquivos;

public class GeraJSP extends AjaxFormAction{
	@Override
	public Class getBeanClass() {
		return BIConsultaDTO.class;
	}
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BIConsultaDTO biConsultaParm = (BIConsultaDTO)document.getBean();
		BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);
		BIConsultaColunasService biConsultaColunasService = (BIConsultaColunasService) ServiceLocator.getInstance().getService(BIConsultaColunasService.class, null);
		Usuario user = WebUtilGerencial.getUsuario(request);
		if (user == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			return;
		}	
		UsuarioDTO userAux = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		BIConsultaDTO biConsultaDTO = new BIConsultaDTO();
		biConsultaDTO.setIdConsulta(biConsultaParm.getIdConsulta());
		try{
			biConsultaDTO = (BIConsultaDTO) biConsultaService.restore(biConsultaDTO);
		}catch(Exception e){
			document.alert("Consulta inexistente!");
			return;
		}
		if (biConsultaDTO == null){
			document.alert("Consulta inexistente!");
			return;
		}
		String conteudoJSP = biConsultaDTO.getScriptExec();
		if (conteudoJSP == null || conteudoJSP.trim().equalsIgnoreCase("")){
			conteudoJSP = biConsultaDTO.getTemplate();
		}
		File f = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/jspEmbedded");
		if (!f.exists()){
			f.mkdirs();
		}		
		f = new File(CITCorporeUtil.CAMINHO_REAL_APP + "/jspEmbedded/" + userAux.getIdUsuario());
		if (!f.exists()){
			f.mkdirs();
		}
		
		String strPathTemplate = CITCorporeUtil.CAMINHO_REAL_APP + "/jspEmbedded/" + userAux.getIdUsuario() + "/jsp_" + biConsultaDTO.getIdConsulta() + "_process.jsp"; 
		UtilTratamentoArquivos.geraFileTxtFromString(strPathTemplate, conteudoJSP);		
		request.setAttribute("url", Constantes.getValue("CONTEXTO_APLICACAO") + "/jspEmbedded/" + userAux.getIdUsuario() + "/jsp_" + biConsultaDTO.getIdConsulta() + "_process.jsp");
	}
}
