/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ConexaoBIService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

/**
 * @author thiago.barbosa
 * 
 * 
 */
public class CadastroConexaoBI extends AjaxFormAction {

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
    	return ConexaoBIDTO.class;
    }

    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		boolean editar = false;
		if(request.getParameter("editar") != null && !request.getParameter("editar").equals("") && !request.getParameter("editar").equals("false")){
			editar = true;
		}
		if (editar){
			restore(document, request, response);
		}
		document.executeScript("document.getElementById(\"nome\").focus();");
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	UsuarioDTO usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		
		try {		
			ConexaoBIDTO conexaoBIDTO = (ConexaoBIDTO) document.getBean();
			ConexaoBIService conexaoBIService = (ConexaoBIService) ServiceLocator.getInstance().getService(ConexaoBIService.class, WebUtil.getUsuarioSistema(request));
			boolean validacaoValoresExistentes = this.verificaDadosExistentes(document, request, response);
			if (validacaoValoresExistentes){
				if (conexaoBIDTO.getIdConexaoBI() == null){
					conexaoBIDTO = (ConexaoBIDTO) conexaoBIService.create(conexaoBIDTO);
					document.alert(UtilI18N.internacionaliza(request, "MSG05"));
					document.executeScript("parent.fecharModalConexaoBI();");
				}else {
					conexaoBIService.update(conexaoBIDTO);
					document.alert(UtilI18N.internacionaliza(request, "MSG06"));
					document.executeScript("parent.fecharModalConexaoBI();");
				}
			}	
		} catch (Exception e){
			String msgErro = e.getMessage();
			msgErro = msgErro.replaceAll("java.lang.Exception:", "");
			msgErro = msgErro.replaceAll("br.com.citframework.excecao.ServiceException:", "");
			msgErro = msgErro.replaceAll("br.com.citframework.excecao.LogicException:", "");
			document.alert(msgErro);
		}
    }
    
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ConexaoBIDTO conexaoBIDTO = (ConexaoBIDTO) document.getBean();
    	ConexaoBIService conexaoBIService = (ConexaoBIService)  ServiceLocator.getInstance().getService(ConexaoBIService.class, null);
    	
    	conexaoBIDTO = (ConexaoBIDTO) conexaoBIService.restore(conexaoBIDTO);
    	/**
    	 * condição para setar o valor do tipo de importação para o padrão A, isso é para tratar valores já existentes no banco, inseridos antes do campo tipo importação existir. 
    	 */
    	if (conexaoBIDTO.getTipoImportacao() == null)
    		conexaoBIDTO.setTipoImportacao("A");
    	
    	HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(conexaoBIDTO);
    }
    
    /**
     * Metodo para verificar se nome ou link já existe no banco de dados
     * @param document
     * @param request
     * @param response
     * @return
     * @throws Exception
     */    
    public boolean verificaDadosExistentes (DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ConexaoBIDTO conexaoBIDTO = (ConexaoBIDTO) document.getBean();
		ConexaoBIService conexaoBIService = (ConexaoBIService) ServiceLocator.getInstance().getService(ConexaoBIService.class, WebUtil.getUsuarioSistema(request));
		
		boolean nomeExiste = conexaoBIService.jaExisteRegistroComMesmoNome(conexaoBIDTO);
		boolean linkExiste = conexaoBIService.jaExisteRegistroComMesmoLink(conexaoBIDTO);
		
		if (nomeExiste) {
			document.executeScript("nomeJaExiste();");
			return false;
		}else if (linkExiste) {
			document.executeScript("linkJaExiste();");
			return false;
		}
		return true;
    }

}
