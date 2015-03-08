package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoAcademicaDTO;
import br.com.centralit.citcorpore.rh.negocio.FormacaoAcademicaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
 
@SuppressWarnings({"rawtypes","unchecked"}) 
public class FormacaoAcademica extends AjaxFormAction {
 
	public Class getBeanClass() {
            return FormacaoAcademicaDTO.class;
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
    	  
          FormacaoAcademicaDTO formacaoAcademicaDto = new FormacaoAcademicaDTO();
        
          HTMLForm form = document.getForm("form");
          form.setValues(formacaoAcademicaDto);
      }
      
      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          FormacaoAcademicaDTO formacaoAcademicaDto  = (FormacaoAcademicaDTO) document.getBean();
          FormacaoAcademicaService formacaoAcademicaService = (FormacaoAcademicaService) ServiceLocator.getInstance().getService(FormacaoAcademicaService.class, null);
          
          if (formacaoAcademicaDto.getIdFormacaoAcademica() != null){
        	  
              formacaoAcademicaService.update(formacaoAcademicaDto);
              
              document.alert(UtilI18N.internacionaliza(request, "MSG06"));
          }else{
        	  
              formacaoAcademicaService.create(formacaoAcademicaDto);
              
              document.alert(UtilI18N.internacionaliza(request, "MSG05"));
          }
          
          document.executeScript("limpar()");
      }
      
      public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          FormacaoAcademicaDTO formacaoAcademicaDto = (FormacaoAcademicaDTO) document.getBean();
          
          FormacaoAcademicaService formacaoAcademicaService = (FormacaoAcademicaService) ServiceLocator.getInstance().getService(FormacaoAcademicaService.class, null);
          formacaoAcademicaDto = (FormacaoAcademicaDTO) formacaoAcademicaService.restore(formacaoAcademicaDto);
          
          formacaoAcademicaService.delete(formacaoAcademicaDto);
          document.executeScript("limpar()");
          
          document.alert(UtilI18N.internacionaliza(request, "MSG07")); 
      }
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

    	  if (!this.validaSessao(document, request)) {
    		  
    		  return;
    	  }
          
          FormacaoAcademicaDTO formacaoAcademicaDto = (FormacaoAcademicaDTO) document.getBean();
          if (formacaoAcademicaDto.getIdFormacaoAcademica() == null)
                return;
          
          FormacaoAcademicaService formacaoAcademicaService = (FormacaoAcademicaService) ServiceLocator.getInstance().getService(FormacaoAcademicaService.class, null);
          formacaoAcademicaDto = (FormacaoAcademicaDTO) formacaoAcademicaService.restore(formacaoAcademicaDto);
 
          HTMLForm form = document.getForm("form");
          form.setValues(formacaoAcademicaDto);
      }
      
    public void exibeSelecao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FormacaoAcademicaService formacaoAcademicaService = (FormacaoAcademicaService) ServiceLocator.getInstance().getService(FormacaoAcademicaService.class, null);
		
		String idFuncao =  (String) request.getParameter("idFuncao");
		Collection<FormacaoAcademicaDTO> colFormacao = null;
		if(idFuncao != null && !idFuncao.equalsIgnoreCase("")){
			colFormacao = formacaoAcademicaService.findByNotIdFuncao(new Integer(idFuncao));
		}else{
			document.alert("Selecione uma função!");
		}
		
        if (colFormacao != null) {
        	for (FormacaoAcademicaDTO formacaoAcademicaDto : colFormacao) {
        		document.executeScript("incluirOpcaoSelecao(\""+formacaoAcademicaDto.getIdFormacaoAcademica()+"\",\""+formacaoAcademicaDto.getDescricao().trim()+"\",\""+formacaoAcademicaDto.getDetalhe().trim()+"\");");
			}
        }
    }
      
}


