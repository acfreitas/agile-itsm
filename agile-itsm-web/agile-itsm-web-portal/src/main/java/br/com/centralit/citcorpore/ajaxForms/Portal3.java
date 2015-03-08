package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PortalDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.citframework.service.ServiceLocator;

public class Portal3 extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BaseConhecimentoService baseServicoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		
		Collection<BaseConhecimentoDTO> listBaseFaq = baseServicoService.listarBaseConhecimentoFAQ();
		
		StringBuilder strFaq = new StringBuilder();
				if (listBaseFaq != null){
					for (BaseConhecimentoDTO baseDTO : listBaseFaq) {
						if(baseDTO.getDataFim() == null){
							
							strFaq.append("<div class='accordion-heading'>");//Inicio div Cabeçalho
    						strFaq.append("<a class='accordion-toggle glyphicons circle_question_mark' data-toggle='collapse' data-parent='#accordion' href='#collapse-"+baseDTO.getIdBaseConhecimento()+"'>"); 
    						strFaq.append("<i></i>"+ baseDTO.getTitulo());//Titulo   								
    						strFaq.append("</a>");//Fim da div Cabeçalho	
    								
    						HTMLElement divFaq = document.getElementById("grupo");//Encontrando o elemento no html
    						divFaq.setInnerHTML(strFaq.toString());//Adicionando o elemento no html
    						strFaq.append("</div>");// Fim div cabeçalho
    						
    						strFaq.append("<div id='collapse-"+baseDTO.getIdBaseConhecimento()+"' class='accordion-body collapse'>");//Inicio div Corpo
    						
    						strFaq.append("<div class='accordion-inner' sytle='text-align:center;'>"+baseDTO.getConteudo()+"<br></div>");//Corpo do conteudo
    						
    						strFaq.append("</div>");//Fim div corpo
    						divFaq.setInnerHTML(strFaq.toString());//Adicionando o elemento no html
    						
						}//Fim do if					
					}//Fim do for
				}//Fim do if
	}//Fim do metodo
		
    public Class<PortalDTO> getBeanClass(){
    	return PortalDTO.class;
    }
}
