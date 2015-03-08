package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;
import br.com.centralit.citcorpore.rh.negocio.IdiomaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
 
 
public class Idioma extends AjaxFormAction {
 
      public Class getBeanClass() {
            return IdiomaDTO.class;
      }
      
      private boolean validaSessao(DocumentHTML document, HttpServletRequest request) {

    	  UsuarioDTO usuario = WebUtil.getUsuario(request);
    	  
          if (usuario == null) {

                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                
                return false;
          }

    	  return true;
      }
 
      public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          
    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
    	  
          IdiomaDTO idiomasDto = new IdiomaDTO();
        
          HTMLForm form = document.getForm("form");
          form.setValues(idiomasDto);
      }

      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  
    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          IdiomaDTO idiomasDto  = (IdiomaDTO) document.getBean();
          IdiomaService idiomasService = (IdiomaService) ServiceLocator.getInstance().getService(IdiomaService.class, null);
          
          if (idiomasDto.getIdIdioma() != null) {
        	  
        	  idiomasService.update(idiomasDto);
        	  
        	  document.alert(UtilI18N.internacionaliza(request, "MSG06"));
          } else {
        	  
        	  idiomasService.create(idiomasDto);
        	  
        	  document.alert(UtilI18N.internacionaliza(request, "MSG05"));
          }
          
          document.executeScript("limpar()");
      }
      
      public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  
    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          IdiomaDTO idiomasDto = (IdiomaDTO) document.getBean();
          
          IdiomaService idiomasService = (IdiomaService) ServiceLocator.getInstance().getService(IdiomaService.class, null);
          idiomasDto = (IdiomaDTO) idiomasService.restore(idiomasDto);
          
          idiomasService.delete(idiomasDto);
          document.executeScript("limpar()");
          
          document.alert(UtilI18N.internacionaliza(request, "MSG07")); 
      }           
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          
    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          IdiomaDTO idiomasDto = (IdiomaDTO) document.getBean();
          if (idiomasDto.getIdIdioma() == null)
                return;
          
          IdiomaService idiomasService = (IdiomaService) ServiceLocator.getInstance().getService(IdiomaService.class, null);
          idiomasDto = (IdiomaDTO) idiomasService.restore(idiomasDto);
 
          HTMLForm form = document.getForm("form");
          form.setValues(idiomasDto);
      }        
      
	  public void exibeSelecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		  
		  IdiomaService idiomasService = (IdiomaService) ServiceLocator.getInstance().getService(IdiomaService.class, null);
	      
		  String idFuncao =  (String) request.getParameter("idFuncao");
			Collection<IdiomaDTO> colIdioma = null;
			if(idFuncao != null && !idFuncao.equalsIgnoreCase("")){
				colIdioma = idiomasService.findByNotIdFuncao(new Integer(idFuncao));
			}else{
				document.alert("Selecione uma função!");
			}
	      
		  if (colIdioma != null) {
				for (IdiomaDTO beanDto : colIdioma) {
					document.executeScript("incluirOpcaoSelecao(\""+beanDto.getIdIdioma()+"\",\""+beanDto.getDescricao().trim()+"\",\""+beanDto.getDetalhe().trim()+"\");");
				}
		  }
	  }
}


