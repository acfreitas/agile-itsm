package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.negocio.CursoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
 
 
public class Curso extends AjaxFormAction {
 
      public Class getBeanClass() {
            return CursoDTO.class;
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

          CursoDTO cursosDto = new CursoDTO();
        
          HTMLForm form = document.getForm("form");
          form.setValues(cursosDto);
      }
              
      
      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          CursoDTO cursosDto  = (CursoDTO) document.getBean();
          CursoService cursosService = (CursoService) ServiceLocator.getInstance().getService(CursoService.class, null);
          
          if (cursosDto.getIdCurso() != null){
        	  
              cursosService.update(cursosDto);
              
              document.alert(UtilI18N.internacionaliza(request, "MSG06"));
          }else{
        	  
              cursosService.create(cursosDto);
              
              document.alert(UtilI18N.internacionaliza(request, "MSG05"));
          }
          
          document.executeScript("limpar()");
      }
      
      public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          CursoDTO cursosDto = (CursoDTO) document.getBean();
          
          CursoService cursosService = (CursoService) ServiceLocator.getInstance().getService(CursoService.class, null);
          cursosDto = (CursoDTO) cursosService.restore(cursosDto);
          
          cursosService.delete(cursosDto);
          document.executeScript("limpar()");
          
          document.alert(UtilI18N.internacionaliza(request, "MSG07")); 
      }
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          CursoDTO cursosDto = (CursoDTO) document.getBean();
          if (cursosDto.getIdCurso() == null)
                return;
          
          CursoService cursosService = (CursoService) ServiceLocator.getInstance().getService(CursoService.class, null);
          cursosDto = (CursoDTO) cursosService.restore(cursosDto);
 
          HTMLForm form = document.getForm("form");
          form.setValues(cursosDto);
      } 
      
      public void exibeSelecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  CursoService cursosService = (CursoService) ServiceLocator.getInstance().getService(CursoService.class, null);
        
    	  String idFuncao =  (String) request.getParameter("idFuncao");
	  		Collection<CursoDTO> colCursos = null;
	  		if(idFuncao != null && !idFuncao.equalsIgnoreCase("")){
	  			colCursos = cursosService.findByNotIdFuncao(new Integer(idFuncao));
	  		}else{
	  			document.alert("Selecione uma função!");
	  		}
    	  
          if (colCursos != null) {
          	for (CursoDTO beanDto : colCursos) {
          		document.executeScript("incluirOpcaoSelecao(\""+beanDto.getIdCurso()+"\",\""+beanDto.getDescricao().trim()+"\",\""+beanDto.getDetalhe().trim()+"\");");
  			}
          }
      }
}


