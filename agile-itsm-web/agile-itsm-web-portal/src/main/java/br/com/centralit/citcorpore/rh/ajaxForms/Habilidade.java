package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.HabilidadeDTO;
import br.com.centralit.citcorpore.rh.negocio.HabilidadeService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
 
 
public class Habilidade extends AjaxFormAction {
 
      public Class getBeanClass() {
            return HabilidadeDTO.class;
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
    	  
          HabilidadeDTO habilidadesDto = new HabilidadeDTO();
        
          HTMLForm form = document.getForm("form");
          form.setValues(habilidadesDto);
      }
              
      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          HabilidadeDTO habilidadesDto  = (HabilidadeDTO) document.getBean();
          HabilidadeService habilidadesService = (HabilidadeService) ServiceLocator.getInstance().getService(HabilidadeService.class, null);
          
          if (habilidadesDto.getIdHabilidade() != null){
        	  
        	  habilidadesService.update(habilidadesDto);
        	  
        	  document.alert(UtilI18N.internacionaliza(request, "MSG06"));
          }else{
        	  
        	  habilidadesService.create(habilidadesDto);
        	  
        	  document.alert(UtilI18N.internacionaliza(request, "MSG05"));
          }
          
          document.executeScript("limpar()");
      }
      
      public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          HabilidadeDTO habilidadesDto = (HabilidadeDTO) document.getBean();
          
          HabilidadeService habilidadesService = (HabilidadeService) ServiceLocator.getInstance().getService(HabilidadeService.class, null);
          habilidadesDto = (HabilidadeDTO) habilidadesService.restore(habilidadesDto);
          
          habilidadesService.delete(habilidadesDto);
          document.executeScript("limpar()");
          
          document.alert(UtilI18N.internacionaliza(request, "MSG07")); 
      }           
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          HabilidadeDTO habilidadesDto = (HabilidadeDTO) document.getBean();
          if (habilidadesDto.getIdHabilidade() == null)
                return;
          
          HabilidadeService habilidadesService = (HabilidadeService) ServiceLocator.getInstance().getService(HabilidadeService.class, null);
          habilidadesDto = (HabilidadeDTO) habilidadesService.restore(habilidadesDto);
 
          HTMLForm form = document.getForm("form");
          form.setValues(habilidadesDto);
      }
    
      public void exibeSelecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
  		HabilidadeService habilidadesService = (HabilidadeService) ServiceLocator.getInstance().getService(HabilidadeService.class, null);
          Collection<HabilidadeDTO> colHabilidade = habilidadesService.list();
          if (colHabilidade != null) {
          	for (HabilidadeDTO habilidadesDto : colHabilidade) {
          		document.executeScript("incluirOpcaoSelecao(\""+habilidadesDto.getIdHabilidade()+"\",\""+habilidadesDto.getDescricao().trim()+"\",\""+habilidadesDto.getDetalhe().trim()+"\");");
  			}
          }
      }
}


