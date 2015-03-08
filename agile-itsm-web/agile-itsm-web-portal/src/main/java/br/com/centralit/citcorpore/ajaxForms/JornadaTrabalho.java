package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.negocio.JornadaTrabalhoService;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author rosana.godinho
 * 
 */
public class JornadaTrabalho extends AjaxFormAction {

	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		JornadaTrabalhoDTO jornadaTrabalho = (JornadaTrabalhoDTO) document.getBean();
		JornadaTrabalhoService jornadaTrabalhoService = (JornadaTrabalhoService) ServiceLocator.getInstance().getService(JornadaTrabalhoService.class, WebUtil.getUsuarioSistema(request));
		
		double horaInicial;
		double horaFinal;
		if (jornadaTrabalho.getInicio1().trim().isEmpty()){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getElementById("inicio1").setFocus();
			return;
		} else {
			if (jornadaTrabalho.getTermino1().trim().isEmpty()){
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
				document.getElementById("termino1").setFocus();
				return;
			} else {
				horaInicial =  Util.getHoraDbl(jornadaTrabalho.getInicio1().trim());
				horaFinal = Util.getHoraDbl(jornadaTrabalho.getTermino1().trim());
				if (horaFinal < horaInicial){
					document.alert(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaFinalMenorQueInicial"));
					document.getElementById("termino1").setFocus();
					return;
				}
			}
		}
		if ((jornadaTrabalho.getInicio1().trim().isEmpty())&&((!jornadaTrabalho.getTermino1().trim().isEmpty()))){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getElementById("inicio1").setFocus();
			return;
		} else {
			if ((!jornadaTrabalho.getInicio1().trim().isEmpty())){
				if (jornadaTrabalho.getTermino1().trim().isEmpty()){
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
					document.getElementById("termino1").setFocus();
					return;
				} else {
					horaInicial =  Util.getHoraDbl(jornadaTrabalho.getInicio1().trim());
					horaFinal = Util.getHoraDbl(jornadaTrabalho.getTermino1().trim());
					if (horaFinal < horaInicial){
						document.alert(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaFinalMenorQueInicial"));
						document.getElementById("termino1").setFocus();
						return;
					}
				}
			}
		}
		if ((jornadaTrabalho.getInicio2().trim().isEmpty())&&((!jornadaTrabalho.getTermino2().trim().isEmpty()))){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getElementById("inicio2").setFocus();
			return;
		} else {
			if ((!jornadaTrabalho.getInicio2().trim().isEmpty())){
				if (jornadaTrabalho.getTermino2().trim().isEmpty()){
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
					document.getElementById("termino2").setFocus();
					return;
				} else {
					horaInicial =  Util.getHoraDbl(jornadaTrabalho.getInicio2().trim());
					horaFinal = Util.getHoraDbl(jornadaTrabalho.getTermino2().trim());
					if (horaFinal < horaInicial){
						document.alert(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaFinalMenorQueInicial"));
						document.getElementById("termino2").setFocus();
						return;
					}
				}
			}
		}
		if ((jornadaTrabalho.getInicio3().trim().isEmpty())&&((!jornadaTrabalho.getTermino3().trim().isEmpty()))){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getElementById("inicio3").setFocus();
			return;
		} else {
			if ((!jornadaTrabalho.getInicio3().trim().isEmpty())){
				if (jornadaTrabalho.getTermino3().trim().isEmpty()){
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
					document.getElementById("termino3").setFocus();
					return;
				} else {
					horaInicial =  Util.getHoraDbl(jornadaTrabalho.getInicio3().trim());
					horaFinal = Util.getHoraDbl(jornadaTrabalho.getTermino3().trim());
					if (horaFinal < horaInicial){
						document.alert(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaFinalMenorQueInicial"));
						document.getElementById("termino3").setFocus();
						return;
					}
				}
			}
		}
		if ((jornadaTrabalho.getInicio4().trim().isEmpty())&&((!jornadaTrabalho.getTermino4().trim().isEmpty()))){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getElementById("inicio4").setFocus();
			return;
		} else {
			if ((!jornadaTrabalho.getInicio4().trim().isEmpty())){
				if (jornadaTrabalho.getTermino4().trim().isEmpty()){
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
					document.getElementById("termino4").setFocus();
					return;
				} else {
					horaInicial =  Util.getHoraDbl(jornadaTrabalho.getInicio4().trim());
					horaFinal = Util.getHoraDbl(jornadaTrabalho.getTermino4().trim());
					if (horaFinal < horaInicial){
						document.alert(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaFinalMenorQueInicial"));
						document.getElementById("termino4").setFocus();
						return;
					}
				}
			}
		}		
		if ((jornadaTrabalho.getInicio5().trim().isEmpty())&&((!jornadaTrabalho.getTermino5().trim().isEmpty()))){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datainicio"));
			document.getElementById("inicio5").setFocus();
			return;
		} else {
			if ((!jornadaTrabalho.getInicio5().trim().isEmpty())){
				if (jornadaTrabalho.getTermino5().trim().isEmpty()){
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim"));
					document.getElementById("termino5").setFocus();
					return;
				} else {
					horaInicial =  Util.getHoraDbl(jornadaTrabalho.getInicio5().trim());
					horaFinal = Util.getHoraDbl(jornadaTrabalho.getTermino5().trim());
					if (horaFinal < horaInicial){
						document.alert(UtilI18N.internacionaliza(request, "requisicaoMudanca.horaFinalMenorQueInicial"));
						document.getElementById("termino5").setFocus();
						return;
					}
				}
			}
		}		
		if (jornadaTrabalho.getIdJornada() == null) {
			if (jornadaTrabalhoService.verificaJornadaExistente(jornadaTrabalho)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			jornadaTrabalho.setDataInicio(UtilDatas.getDataAtual());
			jornadaTrabalhoService.create(jornadaTrabalho);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			if (jornadaTrabalhoService.verificaJornadaExistente(jornadaTrabalho)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			jornadaTrabalhoService.update(jornadaTrabalho);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_JORNADATRABALHO()");
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
		JornadaTrabalhoDTO jornadaTrabalhoDto = (JornadaTrabalhoDTO) document.getBean();
		JornadaTrabalhoService jornadaTrabalhoService = (JornadaTrabalhoService) ServiceLocator.getInstance().getService(JornadaTrabalhoService.class, null);

		jornadaTrabalhoDto = (JornadaTrabalhoDTO) jornadaTrabalhoService.restore(jornadaTrabalhoDto);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(jornadaTrabalhoDto);

	}

	/**
	 * Exclui um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JornadaTrabalhoDTO jornadaTrabalhoDto = (JornadaTrabalhoDTO) document.getBean();
		JornadaTrabalhoService jornadaTrabalhoService = (JornadaTrabalhoService) ServiceLocator.getInstance().getService(JornadaTrabalhoService.class, WebUtil.getUsuarioSistema(request));
		String resp = "";

		if (jornadaTrabalhoDto.getIdJornada().intValue() > 0) {
			resp = jornadaTrabalhoService.deletarJornada(jornadaTrabalhoDto);
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.alert(resp);

		document.executeScript("limpar_LOOKUP_JORNADATRABALHO()");
	}

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return JornadaTrabalhoDTO.class;
	}

}
