package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO;
import br.com.centralit.citcorpore.negocio.CriterioAvaliacaoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

public class CriterioAvaliacao extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CriterioAvaliacaoDTO criterioAvaliacao = (CriterioAvaliacaoDTO) document.getBean();
		CriterioAvaliacaoService criterioService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, null);


		if (criterioService.verificarSeCriterioExiste(criterioAvaliacao)) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
			return;
		}

		if (criterioAvaliacao.getIdCriterio() == null) {
			criterioService.create(criterioAvaliacao);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			criterioService.update(criterioAvaliacao);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CriterioAvaliacaoDTO criterioAvaliacao = (CriterioAvaliacaoDTO) document.getBean();
		CriterioAvaliacaoService criterioService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, null);

		criterioAvaliacao = (CriterioAvaliacaoDTO) criterioService.restore(criterioAvaliacao);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(criterioAvaliacao);
	}

	public void remove(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CriterioAvaliacaoDTO criterioAvaliacao = (CriterioAvaliacaoDTO) document.getBean();
		CriterioAvaliacaoService criterioService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, null);

		try {
			criterioService.delete(criterioAvaliacao);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			HTMLForm form = document.getForm("form");
			form.clear();
		} catch (Exception e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
		}

	}

	public void atualizaGridCriterio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CriterioAvaliacaoDTO criterioAvaliacaoDto = (CriterioAvaliacaoDTO) document.getBean();

		HTMLTable tblCriterio = document.getTableById("tblCriterio");

		if (criterioAvaliacaoDto.getDescricao() == null || criterioAvaliacaoDto.getDescricao().equalsIgnoreCase("")) {
			document.alert(UtilI18N.internacionaliza(request, "avaliacaoFornecedor.criterio") +": " + UtilI18N.internacionaliza(request, "citcorpore.comum.campo_obrigatorio"));
			return;
		}

		if (criterioAvaliacaoDto.getValor() == null) {
		    criterioAvaliacaoDto.setValor("1");
		}

		if (criterioAvaliacaoDto.getSequencia() == null) {
			if (criterioAvaliacaoDto.getValor().equalsIgnoreCase("0")) {
				criterioAvaliacaoDto.setValor("Não");
			} else {
				if (criterioAvaliacaoDto.getValor().equalsIgnoreCase("1")) {
					criterioAvaliacaoDto.setValor("Sim");
				} else {
					criterioAvaliacaoDto.setValor("N/A");
				}
			}

			tblCriterio.addRow(criterioAvaliacaoDto, new String[] { "", "", "descricao", "obs" }, new String[] { "idCriterio" }, "Criterio já cadastrado!!",
					new String[] { "exibeIconesCriterio" }, null, null);
		} else {
			if (criterioAvaliacaoDto.getValor().equalsIgnoreCase("0")) {
				criterioAvaliacaoDto.setValor("Não");
			} else {
				if (criterioAvaliacaoDto.getValor().equalsIgnoreCase("1")) {
					criterioAvaliacaoDto.setValor("Sim");
				} else {
					criterioAvaliacaoDto.setValor("N/A");
				}
			}
			tblCriterio.updateRow(criterioAvaliacaoDto, new String[] { "", "", "descricao", "obs" }, null, "", new String[] { "exibeIconesCriterio" }, null, null,
					criterioAvaliacaoDto.getSequencia());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblCriterio', 'tblCriterio');");
		document.executeScript("fechaCriterio();");
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return CriterioAvaliacaoDTO.class;
	}

}
