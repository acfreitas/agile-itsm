package br.com.centralit.citcorpore.rh.ajaxForms;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.AtitudeOrganizacionalDTO;
import br.com.centralit.citcorpore.rh.negocio.AtitudeOrganizacionalService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
 
 
public class AtitudeOrganizacional extends AjaxFormAction {
 
      public Class getBeanClass() {
            return AtitudeOrganizacionalDTO.class;
      }
 
      public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          AtitudeOrganizacionalDTO atitudeOrganizacionalDto = new AtitudeOrganizacionalDTO();
        
          HTMLForm form = document.getForm("form");
          form.setValues(atitudeOrganizacionalDto);
 
      }
              
      
      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
          AtitudeOrganizacionalDTO atitudeOrganizacionalDto  = (AtitudeOrganizacionalDTO) document.getBean();
          AtitudeOrganizacionalService atitudeOrganizacionalService = (AtitudeOrganizacionalService) ServiceLocator.getInstance().getService(AtitudeOrganizacionalService.class, null);
          if (atitudeOrganizacionalDto.getIdAtitudeOrganizacional() != null){
        	  atitudeOrganizacionalService.update(atitudeOrganizacionalDto);
          }else{
        	  atitudeOrganizacionalService.create(atitudeOrganizacionalDto);
          }
          
          document.alert("Registro gravado com sucesso!");
          document.executeScript("limpar()");
      }
      
      public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
          AtitudeOrganizacionalDTO atitudeOrganizacionalDto = (AtitudeOrganizacionalDTO) document.getBean();
          
          AtitudeOrganizacionalService atitudeOrganizacionalService = (AtitudeOrganizacionalService) ServiceLocator.getInstance().getService(AtitudeOrganizacionalService.class, null);
          atitudeOrganizacionalDto = (AtitudeOrganizacionalDTO) atitudeOrganizacionalService.restore(atitudeOrganizacionalDto);
          
          atitudeOrganizacionalService.delete(atitudeOrganizacionalDto);
          document.executeScript("limpar()");
          
          document.alert("Solicitação excluída com sucesso!"); 
    }           
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
          AtitudeOrganizacionalDTO atitudeOrganizacionalDto = (AtitudeOrganizacionalDTO) document.getBean();
          if (atitudeOrganizacionalDto.getIdAtitudeOrganizacional() == null)
                return;
          
          AtitudeOrganizacionalService atitudeOrganizacionalService = (AtitudeOrganizacionalService) ServiceLocator.getInstance().getService(AtitudeOrganizacionalService.class, null);
          atitudeOrganizacionalDto = (AtitudeOrganizacionalDTO) atitudeOrganizacionalService.restore(atitudeOrganizacionalDto);
 
          HTMLForm form = document.getForm("form");
          form.setValues(atitudeOrganizacionalDto);
          
    } 
      
}


