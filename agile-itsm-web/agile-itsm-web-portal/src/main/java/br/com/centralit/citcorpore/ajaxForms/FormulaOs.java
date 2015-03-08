package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.FormulaOsDTO;
import br.com.centralit.citcorpore.negocio.FormulaOsService;
import br.com.centralit.citcorpore.util.UtilCalculo;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author CentralIT
 * 
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class FormulaOs extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboIdSituacaoFuncional = (HTMLSelect) document.getSelectById("situacao");
		comboIdSituacaoFuncional.removeAllOptions();
		comboIdSituacaoFuncional.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboIdSituacaoFuncional.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.ativo"));
		comboIdSituacaoFuncional.addOption("I", UtilI18N.internacionaliza(request, "citcorpore.comum.inativo"));
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		FormulaOsDTO formulaOsDTO = (FormulaOsDTO) document.getBean();

		FormulaOsService formulaOsService = (FormulaOsService) ServiceLocator.getInstance().getService(FormulaOsService.class, null);
		boolean existeFormulaIgual = false;
		
		if(formulaOsDTO.getIdFormulaOs() != null){
			existeFormulaIgual = formulaOsService.verificaSerExisteFormulaIgual(formulaOsDTO.getFormula(),formulaOsDTO.getIdFormulaOs());
		}else{
			existeFormulaIgual = formulaOsService.verificaSerExisteFormulaIgual(formulaOsDTO.getFormula(),0);
		}
		
		if(existeFormulaIgual){
			document.alert(UtilI18N.internacionaliza(request, "formulaOs.JaExisteFormulaCadastrada"));
		}else{
			if (formulaOsDTO.getIdFormulaOs() == null || formulaOsDTO.getIdFormulaOs().intValue() == 0) {
				formulaOsService.create(formulaOsDTO);
				document.executeScript("limpar()");
				document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				formulaOsService.update(formulaOsDTO);
				document.executeScript("limpar()");
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
		}
	}

	public void simularCalculo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FormulaOsDTO formulaOsDTO = (FormulaOsDTO) document.getBean();
		String formulaSimulada = formulaOsDTO.getFormulaSimulada();
		formulaSimulada = formulaSimulada.replace("vNumeroUsuarios", "100");
		formulaSimulada = formulaSimulada.replace("vDiasUteis", "24");
		formulaSimulada = formulaSimulada.replace("vDiasCorridos", "30");
		
		double custo = UtilCalculo.calculaExpressao(formulaSimulada);
		String resultado = "<b>"+custo+"</b>";
		document.executeScript("setarResultado('<b>"+formulaSimulada+"</b> = "+resultado+"')");
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FormulaOsDTO formulaOsDTO = (FormulaOsDTO) document.getBean();

		FormulaOsService formulaOsService = (FormulaOsService) ServiceLocator.getInstance().getService(FormulaOsService.class, null);

		formulaOsService.delete(formulaOsDTO);
		document.executeScript("limpar()");
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		FormulaOsDTO formulaOsDTO = (FormulaOsDTO) document.getBean();
		FormulaOsService formulaOsService = (FormulaOsService) ServiceLocator.getInstance().getService(FormulaOsService.class, null);
		formulaOsDTO = (FormulaOsDTO) formulaOsService.restore(formulaOsDTO);
		HTMLForm form = document.getForm("form");
		document.executeScript("limpar()");
		form.setValues(formulaOsDTO);
	}

	public Class getBeanClass() {
		return FormulaOsDTO.class;
	}

}
