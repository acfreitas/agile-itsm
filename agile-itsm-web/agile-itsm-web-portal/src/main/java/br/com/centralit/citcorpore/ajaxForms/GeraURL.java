package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BIConsultaService;
import br.com.centralit.citgerencial.util.WebUtilGerencial;
import br.com.citframework.dto.Usuario;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class GeraURL extends AjaxFormAction{
	@Override
	public Class getBeanClass() {
		return BIConsultaDTO.class;
	}
	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BIConsultaDTO biConsultaParm = (BIConsultaDTO)document.getBean();
		BIConsultaService biConsultaService = (BIConsultaService) ServiceLocator.getInstance().getService(BIConsultaService.class, null);
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
		String conteudoURL = biConsultaDTO.getScriptExec();
		if (conteudoURL == null || conteudoURL.trim().equalsIgnoreCase("")){
			conteudoURL = biConsultaDTO.getTemplate();
		}
		request.setAttribute("url", conteudoURL);
	}
}
