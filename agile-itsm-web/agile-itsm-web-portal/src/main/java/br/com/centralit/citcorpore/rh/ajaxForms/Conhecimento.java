package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.ConhecimentoDTO;
import br.com.centralit.citcorpore.rh.negocio.ConhecimentoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
 
 
public class Conhecimento extends AjaxFormAction {
 
      public Class getBeanClass() {
            return ConhecimentoDTO.class;
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
    	  
          ConhecimentoDTO conhecimentosDto = new ConhecimentoDTO();
        
          HTMLForm form = document.getForm("form");
          form.setValues(conhecimentosDto);
      }
              
      
      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
    	  
          ConhecimentoDTO conhecimentosDto  = (ConhecimentoDTO) document.getBean();
          ConhecimentoService conhecimentosService = (ConhecimentoService) ServiceLocator.getInstance().getService(ConhecimentoService.class, null);
          
          Collection<ConhecimentoDTO> colConhecimentoDTOs = conhecimentosService.findByNome(conhecimentosDto.getDescricao());
          
          
        	  if (conhecimentosDto.getIdConhecimento() != null){
        		 
	        		  conhecimentosService.update(conhecimentosDto);
	        		  document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        		   
        	  }else{
        		  if(colConhecimentoDTOs == null || colConhecimentoDTOs.size() < 1){
	        		  conhecimentosService.create(conhecimentosDto);
	        		  document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        		  }else{
                	  document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
                  }	
        	  }
          
          
          document.executeScript("limpar()");
      }
      
      public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          
    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          ConhecimentoDTO conhecimentosDto = (ConhecimentoDTO) document.getBean();
          
          ConhecimentoService conhecimentosService = (ConhecimentoService) ServiceLocator.getInstance().getService(ConhecimentoService.class, null);
          conhecimentosDto = (ConhecimentoDTO) conhecimentosService.restore(conhecimentosDto);
          
          conhecimentosService.delete(conhecimentosDto);
          document.executeScript("limpar()");
          
          document.alert(UtilI18N.internacionaliza(request, "MSG07")); 
    }           
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
    	  
          ConhecimentoDTO conhecimentosDto = (ConhecimentoDTO) document.getBean();
          if (conhecimentosDto.getIdConhecimento() == null)
                return;
          
          ConhecimentoService conhecimentosService = (ConhecimentoService) ServiceLocator.getInstance().getService(ConhecimentoService.class, null);
          conhecimentosDto = (ConhecimentoDTO) conhecimentosService.restore(conhecimentosDto);
 
          HTMLForm form = document.getForm("form");
          form.setValues(conhecimentosDto);
          
    } 
     
      public void exibeSelecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  ConhecimentoService conhecimentosService = (ConhecimentoService) ServiceLocator.getInstance().getService(ConhecimentoService.class, null);
          Collection<ConhecimentoDTO> col = conhecimentosService.list();
          if (col != null) {
          	for (ConhecimentoDTO beanDto : col) {
          		document.executeScript("incluirOpcaoSelecao(\""+beanDto.getIdConhecimento()+"\",\""+beanDto.getDescricao().trim()+"\",\""+beanDto.getDetalhe().trim()+"\");");
  			}
          }
      }
}


