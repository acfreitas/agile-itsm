package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.RiscoDTO;
import br.com.centralit.citcorpore.negocio.RiscoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class Risco extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HTMLSelect comboNivel = (HTMLSelect) document.getSelectById("nivelRisco");
		
		comboNivel.removeAllOptions();
		comboNivel.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboNivel.addOption("1", UtilI18N.internacionaliza(request, "citcorpore.comum.baixo"));
		comboNivel.addOption("2", UtilI18N.internacionaliza(request, "citcorpore.comum.medio"));
		comboNivel.addOption("3", UtilI18N.internacionaliza(request, "citcorpore.comum.alto"));
		
		
	}

	@Override
	public Class getBeanClass() {
		return RiscoDTO.class;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RiscoDTO risco = (RiscoDTO) document.getBean();
		risco.setDataInicio(UtilDatas.getDataAtual());
		RiscoService riscoService = (RiscoService) ServiceLocator.getInstance().getService(RiscoService.class, WebUtil.getUsuarioSistema(request));
		if (risco.getIdRisco() == null || risco.getIdRisco().intValue() == 0) {
			riscoService.create(risco);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			riscoService.update(risco);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("limpar_LOOKUP_RISCO()");
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RiscoDTO risco = (RiscoDTO) document.getBean();
		RiscoService riscoService = (RiscoService) ServiceLocator.getInstance().getService(RiscoService.class, WebUtil.getUsuarioSistema(request));

		risco = (RiscoDTO) riscoService.restore(risco);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(risco);

	}
	
	public void atualizaData(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RiscoDTO risco = (RiscoDTO) document.getBean();
		RiscoService riscoService = (RiscoService) ServiceLocator.getInstance().getService(RiscoService.class, null);
		if (risco.getIdRisco().intValue() > 0) {
			risco.setDataFim(UtilDatas.getDataAtual());

			riscoService.update(risco);

		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));

		document.executeScript("limpar_LOOKUP_RISCO()");

	}
	
}
