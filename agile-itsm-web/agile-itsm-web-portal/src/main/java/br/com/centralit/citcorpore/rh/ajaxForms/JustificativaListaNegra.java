package br.com.centralit.citcorpore.rh.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.JustificativaListaNegraDTO;
import br.com.centralit.citcorpore.rh.negocio.JustificativaListaNegraService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author david.silva
 *
 */
@SuppressWarnings("rawtypes")
public class JustificativaListaNegra extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);
		
		if(!(isUserInGroup(request, "RH"))){
			document.executeScript("alert('Voce não tem permição para usar essa Funcionalidade. Apenas Participantes do Grupo RH');");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/index/index.jsp'"); 
			return; 
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
	}
	
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);
		
		JustificativaListaNegraDTO justificativaListaNegraDTO = (JustificativaListaNegraDTO) document.getBean();
		JustificativaListaNegraService justificativaListaNegraService = (JustificativaListaNegraService) ServiceLocator.getInstance().getService(JustificativaListaNegraService.class, WebUtil.getUsuarioSistema(request));

		justificativaListaNegraDTO.setDtCriacao(UtilDatas.getDataAtual());
		
		if(justificativaListaNegraDTO.getIdJustificativa() == null || justificativaListaNegraDTO.getIdJustificativa() == 0){
			justificativaListaNegraService.create(justificativaListaNegraDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			justificativaListaNegraService.update(justificativaListaNegraDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		
		HTMLForm form = document.getForm("form");
		form.clear();
		
	}
	
	public void delete(DocumentHTML document, HttpServletRequest request,	HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);
		
		JustificativaListaNegraDTO justificativaListaNegraDTO = (JustificativaListaNegraDTO) document.getBean();
		JustificativaListaNegraService justificativaListaNegraService = (JustificativaListaNegraService) ServiceLocator.getInstance().getService(JustificativaListaNegraService.class, WebUtil.getUsuarioSistema(request));

		justificativaListaNegraDTO = (JustificativaListaNegraDTO) justificativaListaNegraService.restore(justificativaListaNegraDTO);
		justificativaListaNegraService.delete(justificativaListaNegraDTO);
		
		document.executeScript("limpar()");
        
        document.alert(UtilI18N.internacionaliza(request, "MSG07")); 
		
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		WebUtil.validarSeUsuarioEstaNaSessao(request, document);
		
		JustificativaListaNegraDTO justificativaListaNegraDTO = (JustificativaListaNegraDTO) document.getBean();
		JustificativaListaNegraService justificativaListaNegraService = (JustificativaListaNegraService) ServiceLocator.getInstance().getService(JustificativaListaNegraService.class, null);

		justificativaListaNegraDTO = (JustificativaListaNegraDTO) justificativaListaNegraService.restore(justificativaListaNegraDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(justificativaListaNegraDTO);

	}
	
	public boolean isUserInGroup(HttpServletRequest req, String grupo) {
		UsuarioDTO usuario = WebUtil.getUsuario(req);
		if (usuario == null) {
			return false;
		}

		String[] grupos = usuario.getGrupos();
		String grpAux = UtilStrings.nullToVazio(grupo);
		for (int i = 0; i < grupos.length; i++) {
			if (grupos[i] != null) {
				if (grupos[i].trim().indexOf(grpAux.trim()) > 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public Class getBeanClass() {
		return JustificativaListaNegraDTO.class;
	}

}
