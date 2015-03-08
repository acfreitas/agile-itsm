package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.AvaliacaoReferenciaFornecedorDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class AvaliacaoReferenciaFornecedor extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}
	
	
	
	 public void atualizaGridAvaliacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		 AvaliacaoReferenciaFornecedorDTO avaliacaoReferenciaFornecedorDTO = (AvaliacaoReferenciaFornecedorDTO) document.getBean();
	        
	        
	        HTMLTable tblAvaliacao = document.getTableById("tblAprovacao");
	        
	        if(avaliacaoReferenciaFornecedorDTO.getIdEmpregado()==null){
	        	document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.campo_obrigatorio"));
	        	return;
	        }
	        
	        if(avaliacaoReferenciaFornecedorDTO.getDecisao()==null){
	            avaliacaoReferenciaFornecedorDTO.setDecisao("S");
	        }
	        
			if (avaliacaoReferenciaFornecedorDTO.getDecisao().equalsIgnoreCase("S")) {
				avaliacaoReferenciaFornecedorDTO.setDecisao("Sim");
			} else {
				avaliacaoReferenciaFornecedorDTO.setDecisao("Não");
			}
	        
	        if (avaliacaoReferenciaFornecedorDTO.getSequencia() == null){
	        	
	        	tblAvaliacao.addRow(avaliacaoReferenciaFornecedorDTO, 
	                                    new String[] {"", "", "nome" ,"telefone", "observacoes"}, 
	                                    new String[] { "idEmpregado"}, 
	                                    "Empregado já cadastrado!!", 
	                                    new String[] {"exibeIconesAprovacao"}, 
	                                    null, 
	                                    null);  
	        }else{
	        	
	        	tblAvaliacao.updateRow(avaliacaoReferenciaFornecedorDTO, 
	        							new String[] {"", "", "nome","telefone", "observacoes"}, 
	                                    null, 
	                                    "", 
	                                    new String[] {"exibeIconesAprovacao"}, 
	                                    null, 
	                                    null,
	                                    avaliacaoReferenciaFornecedorDTO.getSequencia());  
	        }
	        document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblAprovacao', 'tblAprovacao');");
	        document.executeScript("fechaAprovacao();");
	    }
	 
		public void preencheEmpregado(DocumentHTML document, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			 AvaliacaoReferenciaFornecedorDTO avaliacaoReferenciaFornecedorDTO = (AvaliacaoReferenciaFornecedorDTO) document.getBean();
			 
			 EmpregadoService empService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			 EmpregadoDTO empregadoDto = new EmpregadoDTO();
			 empregadoDto.setIdEmpregado(avaliacaoReferenciaFornecedorDTO.getIdEmpregado());
			 empregadoDto = (EmpregadoDTO) empService.restore(empregadoDto);
			 
             document.executeScript("document.formAprovacao.nome.value = '" + empregadoDto.getNome() + "';");
			 document.executeScript("document.formAprovacao.telefone.value = '" + empregadoDto.getTelefone() + "';");
			 document.executeScript("fechaEmpregado()");
		}
	 
	 
	 

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return AvaliacaoReferenciaFornecedorDTO.class;
	}

}
