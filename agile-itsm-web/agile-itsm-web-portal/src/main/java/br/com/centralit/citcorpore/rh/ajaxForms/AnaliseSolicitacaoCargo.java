package br.com.centralit.citcorpore.rh.ajaxForms;
 
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.rh.bean.DescricaoCargoDTO;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
 
 
public class AnaliseSolicitacaoCargo extends SolicitacaoCargo {
	
	  public String getAcao() {
		 return DescricaoCargoDTO.ACAO_ANALISE; 
	  }
	  
	  public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
          JustificativaParecerService justificativaService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, WebUtil.getUsuarioSistema(request));
          HTMLSelect idJustificativa = (HTMLSelect) document.getSelectById("idJustificativaValidacao");
          idJustificativa.removeAllOptions();
          idJustificativa.addOption("", "---");
          Collection colJustificativas = justificativaService.listAplicaveisRequisicao();
          if(colJustificativas != null && !colJustificativas.isEmpty())
              idJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);

          super.load(document, request, response); 
      }
	  
      public boolean verificaSolicitacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  DescricaoCargoDTO descricaoCargoDto = (DescricaoCargoDTO) document.getBean();
    	  if (descricaoCargoDto.getIdDescricaoCargo() == null)
      	   return false;
		  return true;
     }    
}
