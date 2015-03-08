package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaInformaticaDTO;
import br.com.centralit.citcorpore.rh.negocio.ExperienciaInformaticaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
 
 
public class ExperienciaInformatica extends AjaxFormAction {
 
      public Class getBeanClass() {
            return ExperienciaInformaticaDTO.class;
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
    	  
          ExperienciaInformaticaDTO experienciaInformaticaDto = new ExperienciaInformaticaDTO();
        
          HTMLForm form = document.getForm("form");
          form.setValues(experienciaInformaticaDto);
      }
              
      
      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
    	  
          ExperienciaInformaticaDTO experienciaInformaticaDto  = (ExperienciaInformaticaDTO) document.getBean();
          ExperienciaInformaticaService experienciaInformaticaService = (ExperienciaInformaticaService) ServiceLocator.getInstance().getService(ExperienciaInformaticaService.class, null);
          
          if (experienciaInformaticaDto.getIdExperienciaInformatica() != null){
        	  
        	  experienciaInformaticaService.update(experienciaInformaticaDto);

        	  document.alert(UtilI18N.internacionaliza(request, "MSG06"));
          }else{
        	  
        	  experienciaInformaticaService.create(experienciaInformaticaDto);

        	  document.alert(UtilI18N.internacionaliza(request, "MSG05"));
          }
          
          document.executeScript("limpar()");
      }
      
      public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
    	  
          ExperienciaInformaticaDTO experienciaInformaticaDto = (ExperienciaInformaticaDTO) document.getBean();
          
          ExperienciaInformaticaService experienciaInformaticaService = (ExperienciaInformaticaService) ServiceLocator.getInstance().getService(ExperienciaInformaticaService.class, null);
          experienciaInformaticaDto = (ExperienciaInformaticaDTO) experienciaInformaticaService.restore(experienciaInformaticaDto);
          
          experienciaInformaticaService.delete(experienciaInformaticaDto);
          document.executeScript("limpar()");
          
          document.alert(UtilI18N.internacionaliza(request, "MSG07")); 
      }
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
    	  
          ExperienciaInformaticaDTO experienciaInformaticaDto = (ExperienciaInformaticaDTO) document.getBean();
          if (experienciaInformaticaDto.getIdExperienciaInformatica() == null)
                return;
          
          ExperienciaInformaticaService experienciaInformaticaService = (ExperienciaInformaticaService) ServiceLocator.getInstance().getService(ExperienciaInformaticaService.class, null);
          experienciaInformaticaDto = (ExperienciaInformaticaDTO) experienciaInformaticaService.restore(experienciaInformaticaDto);
 
          HTMLForm form = document.getForm("form");
          form.setValues(experienciaInformaticaDto);
      }
      
      public void exibeSelecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  ExperienciaInformaticaService experienciaInformaticaService = (ExperienciaInformaticaService) ServiceLocator.getInstance().getService(ExperienciaInformaticaService.class, null);
          Collection<ExperienciaInformaticaDTO> col = experienciaInformaticaService.list();
          if (col != null) {
          	for (ExperienciaInformaticaDTO beanDto : col) {
          		document.executeScript("incluirOpcaoSelecao(\""+beanDto.getIdExperienciaInformatica()+"\",\""+beanDto.getDescricao().trim()+"\",\""+beanDto.getDetalhe().trim()+"\");");
  			}
          }
      }
}


