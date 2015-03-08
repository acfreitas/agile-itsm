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
 
 
public class ExperienciaAnterior extends AjaxFormAction {
 
      public Class getBeanClass() {
            return ConhecimentoDTO.class;
      }
 
      public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          ConhecimentoDTO conhecimentosDto = new ConhecimentoDTO();
        
          HTMLForm form = document.getForm("form");
          form.setValues(conhecimentosDto);
 
      }
              
      
      public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
                return;
          }
          
          ConhecimentoDTO conhecimentosDto  = (ConhecimentoDTO) document.getBean();
          ConhecimentoService conhecimentosService = (ConhecimentoService) ServiceLocator.getInstance().getService(ConhecimentoService.class, null);
          if (conhecimentosDto.getIdConhecimento() != null){
              conhecimentosService.update(conhecimentosDto);
          }else{
              conhecimentosService.create(conhecimentosDto);
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
          
          ConhecimentoDTO conhecimentosDto = (ConhecimentoDTO) document.getBean();
          
          ConhecimentoService conhecimentosService = (ConhecimentoService) ServiceLocator.getInstance().getService(ConhecimentoService.class, null);
          conhecimentosDto = (ConhecimentoDTO) conhecimentosService.restore(conhecimentosDto);
          
          conhecimentosService.delete(conhecimentosDto);
          document.executeScript("limpar()");
          
          document.alert("Solicitação excluída com sucesso!"); 
    }           
      
      public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          UsuarioDTO usuario = WebUtil.getUsuario(request);
          if (usuario == null){
                document.alert("Sessão expirada! Favor efetuar logon novamente!");
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
		String idFuncao =  (String) request.getParameter("idFuncao");
		
		Collection<ConhecimentoDTO> colConhecimento = null;
		if(idFuncao != null && !idFuncao.equalsIgnoreCase("")){
			colConhecimento = conhecimentosService.findByNotIdFuncao(new Integer(idFuncao));
		}else{
			document.alert("Selecione uma função!");
		}
        if (colConhecimento != null) {
        	for (ConhecimentoDTO conhecimentosDto : colConhecimento) {
        		document.executeScript("incluirOpcaoSelecao(\""+conhecimentosDto.getIdConhecimento()+"\",\""+conhecimentosDto.getDescricao().trim()+"\",\""+conhecimentosDto.getDetalhe().trim()+"\");");
			}
        }
    }
      
}


