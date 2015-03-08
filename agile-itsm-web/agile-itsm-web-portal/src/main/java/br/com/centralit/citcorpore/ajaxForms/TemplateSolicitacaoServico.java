package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.CategoriaProblemaService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.TemplateSolicitacaoServicoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citquestionario.negocio.QuestionarioService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author Augusto
 * 
 */
public class TemplateSolicitacaoServico extends AjaxFormAction {

	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        QuestionarioService questionarioService = (QuestionarioService) ServiceLocator.getInstance().getService(QuestionarioService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idQuestionario = (HTMLSelect) document.getSelectById("idQuestionario");
        idQuestionario.removeAllOptions();
        idQuestionario.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colQuestionarios = questionarioService.list();
        if(colQuestionarios != null && !colQuestionarios.isEmpty())
            idQuestionario.addOptions(colQuestionarios, "idQuestionarioOrigem", "nomeQuestionario", null);

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
		TemplateSolicitacaoServicoDTO templateSolicitacaoServicoDTO = (TemplateSolicitacaoServicoDTO) document.getBean();
		TemplateSolicitacaoServicoService templateSolicitacaoServicoService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance()
				.getService(TemplateSolicitacaoServicoService.class, null);

		if (templateSolicitacaoServicoDTO.getHabilitaDirecionamento() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}

		if (templateSolicitacaoServicoDTO.getHabilitaSituacao() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}
		if (templateSolicitacaoServicoDTO.getHabilitaSolucao() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}
		if (templateSolicitacaoServicoDTO.getHabilitaUrgenciaImpacto() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}
		if (templateSolicitacaoServicoDTO.getHabilitaNotificacaoEmail() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}
		if (templateSolicitacaoServicoDTO.getHabilitaProblema() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}
		if (templateSolicitacaoServicoDTO.getHabilitaMudanca() == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}

		if (templateSolicitacaoServicoDTO.getIdTemplate() == null || templateSolicitacaoServicoDTO.getIdTemplate().intValue() == 0) {
			templateSolicitacaoServicoService.create(templateSolicitacaoServicoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			templateSolicitacaoServicoService.update(templateSolicitacaoServicoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
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
		TemplateSolicitacaoServicoDTO templateSolicitacaoServicoDTO = (TemplateSolicitacaoServicoDTO) document.getBean();
		TemplateSolicitacaoServicoService templateSolicitacaoServicoService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance()
				.getService(TemplateSolicitacaoServicoService.class, null);

		templateSolicitacaoServicoDTO = (TemplateSolicitacaoServicoDTO) templateSolicitacaoServicoService.restore(templateSolicitacaoServicoDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(templateSolicitacaoServicoDTO);
	}

	public Class<TemplateSolicitacaoServicoDTO> getBeanClass() {
		return TemplateSolicitacaoServicoDTO.class;
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		TemplateSolicitacaoServicoDTO templateSolicitacaoServicoDTO = (TemplateSolicitacaoServicoDTO) document.getBean();

		TemplateSolicitacaoServicoService templateSolicitacaoServicoService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(TemplateSolicitacaoServicoService.class,
				WebUtil.getUsuarioSistema(request));

		CategoriaProblemaService categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, WebUtil.getUsuarioSistema(request));
		try {
			categoriaProblemaService.desvincularCategoriaProblemasRelacionadasTemplate(templateSolicitacaoServicoDTO.getIdTemplate());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, WebUtil.getUsuarioSistema(request));
		try {
			servicoService.desvincularServicosRelacionadosTemplate(templateSolicitacaoServicoDTO.getIdTemplate());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (templateSolicitacaoServicoDTO.getIdTemplate().intValue() > 0) {
			templateSolicitacaoServicoService.delete(templateSolicitacaoServicoDTO);
		}
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));

		HTMLForm form = document.getForm("form");
		form.clear();
	}

}
