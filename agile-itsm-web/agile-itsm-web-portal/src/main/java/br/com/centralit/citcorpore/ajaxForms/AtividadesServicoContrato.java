package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.centralit.citcorpore.bean.FormulaOsDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.AtividadesServicoContratoService;
import br.com.centralit.citcorpore.negocio.FormulaOsService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author Cledson.junior
 * 
 */
public class AtividadesServicoContrato extends AjaxFormAction {

	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("ss");
	}

	/**
	 * Inclui registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtividadesServicoContratoDTO atividadesServicoContrato = (AtividadesServicoContratoDTO) document.getBean();
		if (atividadesServicoContrato.getComplexidade() == null || atividadesServicoContrato.getComplexidade().equalsIgnoreCase("") || atividadesServicoContrato.getComplexidade().equalsIgnoreCase("undefined")) {
			atividadesServicoContrato.setComplexidade(atividadesServicoContrato.getComplexidadeCustoTotal());
		}

		AtividadesServicoContratoService atividadeServicoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class,
				WebUtil.getUsuarioSistema(request));
		if (atividadesServicoContrato.getIdAtividadeServicoContrato() == null || atividadesServicoContrato.getIdAtividadeServicoContrato().intValue() == 0) {
			atividadeServicoService.create(atividadesServicoContrato);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			atividadeServicoService.update(atividadesServicoContrato);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("document.getElementById('complexidadeCustoTotal').value = '';");
		document.executeScript("document.getElementById('complexidade').value = ''");
		document.executeScript("closePopup(" + atividadesServicoContrato.getIdServicoContrato() + ");");
	}

	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtividadesServicoContratoDTO atividadesServicoContrato = (AtividadesServicoContratoDTO) document.getBean();
		AtividadesServicoContratoService atividadeServicoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class,WebUtil.getUsuarioSistema(request));
		atividadesServicoContrato = (AtividadesServicoContratoDTO) atividadeServicoService.restore(atividadesServicoContrato);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(atividadesServicoContrato);
	}

	public void restoreAtividadeServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtividadesServicoContratoDTO atividadesServicoContrato = (AtividadesServicoContratoDTO) document.getBean();

		ServicoDTO servicoBean = new ServicoDTO();
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);

		servicoBean.setIdServico(atividadesServicoContrato.getIdServicoContratoContabil());
		servicoBean = (ServicoDTO) servicoService.restore(servicoBean);
		atividadesServicoContrato.setNomeServico(servicoBean.getNomeServico());
		atividadesServicoContrato.setIdServicoContratoContabil(servicoBean.getIdServico());
		HTMLForm form = document.getForm("form");
		// form.clear();
		form.setValues(atividadesServicoContrato);
		document.executeScript("fecharPopup()");

	}

	/**
	 * recupera os dados ao carregar página
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			AtividadesServicoContratoDTO atividadesServicoContrato = (AtividadesServicoContratoDTO) document.getBean();
			AtividadesServicoContratoService atividadeServicoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class,
					WebUtil.getUsuarioSistema(request));
			if (atividadesServicoContrato.getIdAtividadeServicoContrato() != null || atividadesServicoContrato.getIdAtividadeServicoContrato().intValue() != 0) {
				atividadesServicoContrato.setDeleted("y");
				atividadeServicoService.update(atividadesServicoContrato);
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			}
			HTMLForm form = document.getForm("formInterno");
			form.clear();
			document.executeScript("closePopup(" + atividadesServicoContrato.getIdServicoContrato() + ");");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Class<AtividadesServicoContratoDTO> getBeanClass() {
		return AtividadesServicoContratoDTO.class;
	}

	public void ListarFormulas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtividadesServicoContratoDTO atividadesServicoContratoDTO = (AtividadesServicoContratoDTO) document.getBean();
		FormulaOsService formulaOsService = (FormulaOsService) ServiceLocator.getInstance().getService(FormulaOsService.class,WebUtil.getUsuarioSistema(request));
		Collection<FormulaOsDTO> listaFormulas = formulaOsService.listar(atividadesServicoContratoDTO.getIdContrato());
		
		HTMLSelect comboFormula = document.getSelectById("Formulas");
		comboFormula.removeAllOptions();
		
		comboFormula.addOption("-1", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if(listaFormulas!=null && !listaFormulas.isEmpty()){
			for (FormulaOsDTO formula : listaFormulas) {
				comboFormula.addOption(formula.getIdFormulaOs().toString(), formula.getDescricao());
			}
		}
		
	}
	public String gerarFormula(DocumentHTML document, HttpServletRequest request, HttpServletResponse response,String  formula){
		boolean existeComplexidade = true;
        
		String html = "<b id='idB' style='margin-left: 20px !important;'>";
		html += formula;
		
		document.executeScript("setEstruturaFormulaOs(\"" + formula + "\")");		
		html = html.replace("{", "  ");
		html = html.replace("}", "  ");
		if (html.contains("vValor")) {
			html = html.replaceAll("vValor", "<input type='text' class='objFormula vValor span12' style='width: 50px !important;' id=vValor name='vValor'  required='required'>");
		}
		if (html.contains("vDiasUteis")) {
			html = html.replaceAll("vDiasUteis", "<b id='VarDiasUteis'>   "+UtilI18N.internacionaliza(request, "citcorpore.comum.diasUteis")+"   <span>");

		}
		if (html.contains("vDiasCorridos")) {
			html = html.replaceAll("vDiasCorridos", "<b id='VarDiasUteis'>  "+UtilI18N.internacionaliza(request, "citcorpore.comum.diasCorridos")+"   <span>");

		}
		if (html.contains("vNumeroUsuarios")) {
			html = html.replaceAll("vNumeroUsuarios", "<b id='vNumeroUsuarios'>   "+UtilI18N.internacionaliza(request, "formula.NumeroDeUsuarios")+"   <span>");

		}
		if (html.contains("vComplexidade")) {
			existeComplexidade = false;
			html = html.replaceAll(
					"vComplexidade",
					UtilI18N.internacionaliza(request, "matrizvisao.complexidade")+" :    <select id='vComplexidade' name='vComplexidade' style='width: 110px !important;' class='vComplexidade objFormula span12 Valid[Required]'>" + "<option value='B'>"
							+ UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeBaixa") + "</option>" + "<option value='I'>"
							+ UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeIntermediaria") + "</option>" + "<option value='M'>"
							+ UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeMediana") + "</option>" + "<option value='A'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeAlta")+"</option>" +
							"<option value='E'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeEspecialista")+"</option></select>");
				} 
				html += "</b>";
				
				if(existeComplexidade){
					html += "<br><br><div><label class='campoObrigatorio' style='margin-left: 13px !important;' ><b>"+UtilI18N.internacionaliza(request, "matrizvisao.complexidade")+":</b></label>";
					html += "<select id='complexidade' name='complexidade' class='noClearCITAjax objFormula' style='width: 150px !important;margin-left: 18px !important;'>";
					html += "<option value='B'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeBaixa")+"</option>" +
							"<option value='I'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeIntermediaria")+"</option>" +
							"<option value='M'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeMediana")+"</option>" +
							"<option value='A'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeAlta")+"</option>" +
							"<option value='E'>"+UtilI18N.internacionaliza(request, "citcorpore.comum.complexidadeEspecialista")+"</option></select>"+"</select></div>";
				}
				
		document.executeScript("montarFormula(\""+html+"\")");
		document.executeScript("addClassMoedaInput();");
		
		return "";
	}
	
	public void restoreFormula(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtividadesServicoContratoDTO atividadesServicoContratoDTO = (AtividadesServicoContratoDTO) document.getBean();
		FormulaOsService formulaOsService = (FormulaOsService) ServiceLocator.getInstance().getService(FormulaOsService.class,WebUtil.getUsuarioSistema(request));
		FormulaOsDTO formulaOsDTO = new FormulaOsDTO();
		formulaOsDTO.setIdFormulaOs(atividadesServicoContratoDTO.getIdFormulaOs());
		formulaOsDTO = (FormulaOsDTO) formulaOsService.restore(formulaOsDTO);
		gerarFormula(document,request, response, formulaOsDTO.getFormula());
	}
	
	public void recupera(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtividadesServicoContratoDTO atividadesServicoContrato = (AtividadesServicoContratoDTO) document.getBean();
		AtividadesServicoContratoService atividadeServicoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class,WebUtil.getUsuarioSistema(request));
		FormulaOsService formulaOsService = (FormulaOsService) ServiceLocator.getInstance().getService(FormulaOsService.class,WebUtil.getUsuarioSistema(request));
		HTMLForm form = document.getForm("form");
		if (atividadesServicoContrato.getIdAtividadeServicoContrato() != null) {
			atividadesServicoContrato = (AtividadesServicoContratoDTO) atividadeServicoService.restore(atividadesServicoContrato);
			atividadesServicoContrato.setComplexidadeCustoTotal(atividadesServicoContrato.getComplexidade());
			if (atividadesServicoContrato.getIdServicoContratoContabil() != null) {
				ServicoDTO servicoBean = new ServicoDTO();
				ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
				servicoBean.setIdServico(atividadesServicoContrato.getIdServicoContratoContabil());
				servicoBean = (ServicoDTO) servicoService.restore(servicoBean);
				atividadesServicoContrato.setNomeServico(servicoBean.getNomeServico());
			}

			form.setValues(atividadesServicoContrato);
			
			if (atividadesServicoContrato.getFormula() == null || atividadesServicoContrato.getFormula().isEmpty()) {
				document.executeScript("document.getElementById('TIPOCUSTO').value = 'C'");
				document.executeScript("document.getElementById('divByCustoFormula').style.display = 'none'");
				document.executeScript("document.getElementById('divByCustoFormula').style.display = 'none'");
				document.executeScript("document.getElementById('divByCustoTotal').style.display = 'block'");
				document.executeScript("document.getElementById('divByCustoTotal2').style.display = 'block'");
			}
			if (atividadesServicoContrato.getTipoCusto() != null && atividadesServicoContrato.getTipoCusto().equalsIgnoreCase("F")) {
				ListarFormulas(document,request, response);
				gerarFormula(document,request, response, atividadesServicoContrato.getEstruturaFormulaOs());
				FormulaOsDTO formulaDto = formulaOsService.buscarPorFormula(atividadesServicoContrato.getEstruturaFormulaOs());
				if(formulaDto==null){
					document.executeScript("selecionarFormula('"+0+"')");
				}else{
					document.executeScript("selecionarFormula('"+formulaDto.getIdFormulaOs()+"')");
				}
				
				String formulaFinalSerializada = "";
				if(atividadesServicoContrato.getFormulaCalculoFinal() != null) {
					char[] index = atividadesServicoContrato.getFormulaCalculoFinal().toCharArray();
					for (char c : index) {
						if(c != ' ') {
							if(c=='+' || c=='-' || c=='*' || c=='/' || c==')'){
								if(formulaFinalSerializada.charAt(formulaFinalSerializada.length()-1) != ';'){
									formulaFinalSerializada+=";";
								}
							} else {
								if (c!='('){
									formulaFinalSerializada += c;
								}
							}
						}
					}
				}
				if(!formulaFinalSerializada.equals("")) {
					if(formulaFinalSerializada.charAt(formulaFinalSerializada.length()-1) == ';'){
						formulaFinalSerializada = formulaFinalSerializada.substring(0, formulaFinalSerializada.length()-1);
					}
				}
				
				
				document.executeScript("colocarValorNaFormula('"+formulaFinalSerializada+"')");
				document.executeScript("document.getElementById('TIPOCUSTO').value = 'F'");
				document.executeScript("document.getElementById('divByCustoFormula').style.display = 'block'");
				document.executeScript("document.getElementById('divByCustoFormula2').style.display = 'block'");
				document.executeScript("document.getElementById('divByCustoTotal').style.display = 'none'");
				document.executeScript("document.getElementById('divByCustoTotal2').style.display = 'none'");
			}
			if (atividadesServicoContrato.getNomeServico() != null && atividadesServicoContrato.getNomeServico() != "") {
				document.executeScript("document.getElementById('CONTABILIZAR').value = 'S'");
				document.executeScript("document.getElementById('divComboServicoContrato').style.display = 'block'");
				document.executeScript("document.getElementById('addServicoContrato').value = " + atividadesServicoContrato.getNomeServico());
			}

		}
	}

}
