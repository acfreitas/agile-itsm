package br.com.centralit.citcorpore.rh.ajaxForms;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.JornadaEmpregadoDTO;
import br.com.centralit.citcorpore.rh.negocio.JornadaEmpregadoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;
 
@SuppressWarnings("rawtypes")
public class JornadaEmpregado extends AjaxFormAction {
      
	public Class getBeanClass() {
            return JornadaEmpregadoDTO.class;
      }
 
      public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  
      }
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
		  JornadaEmpregadoDTO jornadaEmpregadoDto = (JornadaEmpregadoDTO) document.getBean();
		  JornadaEmpregadoService jornadaEmpregadoService = (JornadaEmpregadoService) ServiceLocator.getInstance().getService(JornadaEmpregadoService.class, null);
		
		  jornadaEmpregadoDto = (JornadaEmpregadoDTO) jornadaEmpregadoService.restore(jornadaEmpregadoDto);
		  HTMLForm form = document.getForm("form");
		  form.clear();
		  form.setValues(jornadaEmpregadoDto);
      } 
      
      public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  UsuarioDTO usuario = WebUtil.getUsuario(request);
    	  if (usuario == null){
    		  document.alert("Sessão expirada! Favor efetuar logon novamente!");
    		  return;
    	  }
    	  
  		 JornadaEmpregadoDTO jornadaEmpregadoDto = (JornadaEmpregadoDTO) document.getBean();
         JornadaEmpregadoService jornadaEmpregadoService = (JornadaEmpregadoService) ServiceLocator.getInstance().getService(JornadaEmpregadoService.class, null);
  		
  		if (jornadaEmpregadoDto.getIdJornada().intValue() > 0) {
  			jornadaEmpregadoService.delete(jornadaEmpregadoDto);
  		}

  		HTMLForm form = document.getForm("form");
  		form.clear();

  		document.executeScript("limpar_LOOKUP_JORNADAEMPREGADO()");
    	  
      } 
      
      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
          JornadaEmpregadoDTO jornadaEmpregadoDto = (JornadaEmpregadoDTO) document.getBean();
          JornadaEmpregadoService jornadaEmpregadoService = (JornadaEmpregadoService) ServiceLocator.getInstance().getService(JornadaEmpregadoService.class, null);
          
          if(jornadaEmpregadoDto.getIdJornada() == null || jornadaEmpregadoDto.getIdJornada() == 0 ){
        	  jornadaEmpregadoService.create(jornadaEmpregadoDto);
        	  document.alert(UtilI18N.internacionaliza(request, "MSG05"));
          }else {
        	  jornadaEmpregadoService.update(jornadaEmpregadoDto);
  			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
          }
          
        HTMLForm form = document.getForm("form");
  		form.clear();

  		document.executeScript("limpar_LOOKUP_JORNADAEMPREGADO()");
  	}
}


