package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ReuniaoRequisicaoMudancaDTO;

public class ReuniaoRequisicaoMudanca extends AjaxFormAction{

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Class getBeanClass() {
		return ReuniaoRequisicaoMudancaDTO.class;
	}

}
