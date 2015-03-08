package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.JustificacaoEventoHistoricoDTO;
import br.com.centralit.citcorpore.bean.JustificacaoFalhasDTO;
import br.com.centralit.citcorpore.negocio.JustificacaoFalhasService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.WebUtil;
@SuppressWarnings({"rawtypes","unchecked","unused"})
public class JustificacaoFalhas extends AjaxFormAction {
    
    JustificacaoFalhasService justificacaoService;
    
   
	@Override
    public Class getBeanClass() {
	return JustificacaoFalhasDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {
	
	
    }
    
	public void salvarJustificativa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {	
	JustificacaoFalhasService justificacaoService = (JustificacaoFalhasService) ServiceLocator
						.getInstance().getService(JustificacaoFalhasService.class, null);
	
	ArrayList<JustificacaoFalhasDTO> listaItens = (ArrayList) WebUtil.deserializeCollectionFromRequest(JustificacaoFalhasDTO.class,"listaItensSerializado", request);
	
	
	if(listaItens != null){	    
	    for(JustificacaoFalhasDTO j : listaItens){
		justificacaoService.create(j);
	    }
	} /*else {
	}*/
	pesquisar(document, request, response);
    }
    
	public void pesquisar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {	
	JustificacaoFalhasDTO justificacao = (JustificacaoFalhasDTO) document.getBean();
	JustificacaoFalhasService justificacaoService = (JustificacaoFalhasService) ServiceLocator
						.getInstance().getService(JustificacaoFalhasService.class, null);
	ArrayList<JustificacaoEventoHistoricoDTO> resultados = (ArrayList<JustificacaoEventoHistoricoDTO>) justificacaoService.findEventosComFalha(justificacao);
	
	String identificacaoAux = "";
/*	if(resultados != null){	    
        	for(JustificacaoEventoHistoricoDTO o : resultados){
        	    if(identificacaoAux == ""){
        		identificacaoAux = o.getIdentificacaoItemConfiguracao();
        		System.out.println("=================FIRST IN ACTION==================");
        		System.out.println("Identificação: " + o.getIdentificacaoItemConfiguracao());
        		
        	    }
        	    if(!identificacaoAux.equals(o.getIdentificacaoItemConfiguracao())){			
        		System.out.println("---------------------------------------------");
        		System.out.println("Identificação: " + o.getIdentificacaoItemConfiguracao());
        		identificacaoAux = o.getIdentificacaoItemConfiguracao();
        		
        	    }
        	    System.out.println("Descrição do Falha: " + o.getDescricaoTentativa());
        	}
	}*/	    	
	justificacao.setListaItensSerializado(WebUtil.serializeObjects(resultados, WebUtil.getLanguage(request)));
	//System.out.println("Serializado: " + justificacao.getListaItensSerializado());
	
	document.getForm("form").setValues(justificacao);
	document.executeScript("$('#loading_overlay').hide();");
	document.executeScript("alimentaListaFalhas();");

    }
}
