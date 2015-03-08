package br.com.centralit.citquestionario.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citquestionario.bean.QuestionarioDTO;

public class QuestionarioResponser extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return QuestionarioDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}

}
