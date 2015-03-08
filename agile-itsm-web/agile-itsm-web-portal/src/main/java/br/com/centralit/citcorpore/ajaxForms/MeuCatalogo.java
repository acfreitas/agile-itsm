package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.MeuCatalogoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.MeuCatalogoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

/**
 * @author Flávio
 *
 */
public class MeuCatalogo extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
    	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}    	
		Integer idUsuario = new Integer(usrDto.getIdEmpregado());
		MeuCatalogoDTO catalogo = (MeuCatalogoDTO) document.getBean();
		//Meu Catalogo de Servicos
    	MeuCatalogoService meuCatalogoService = (MeuCatalogoService) ServiceLocator.getInstance().getService(MeuCatalogoService.class, null);
    	
    	catalogo.setIdUsuario(idUsuario);
    	meuCatalogoService.create(catalogo);
    	Portal portal = new Portal();
    	portal.load(document, request, response);
    	
    }


    public void restore(DocumentHTML document, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

		
    }
    
    public void delete(DocumentHTML document, HttpServletRequest request,
    	    HttpServletResponse response) throws Exception {
    	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    
     }

    public Class<MeuCatalogoDTO> getBeanClass()
    {
    	return MeuCatalogoDTO.class;
    }
    
}
