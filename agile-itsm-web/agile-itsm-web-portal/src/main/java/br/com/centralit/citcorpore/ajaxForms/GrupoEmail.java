package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.citframework.service.ServiceLocator;

public class GrupoEmail extends AjaxFormAction {
	
	

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		document.focusInFirstActivateField(null);
		
	}

	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return GrupoEmailDTO.class;
	}
	
	public void restoreEmpregadoEmail(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoEmailDTO grupoemailDto = (GrupoEmailDTO) document.getBean();

		EmpregadoDTO empregadoBean = new EmpregadoDTO();
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		empregadoBean.setIdEmpregado(grupoemailDto.getIdEmpregado());
		empregadoBean = (EmpregadoDTO) empregadoService.restore(empregadoBean);
		grupoemailDto.setNome(empregadoBean.getNome());
		grupoemailDto.setEmail(empregadoBean.getEmail().trim());

		HTMLForm form = document.getForm("formEmail");
		form.setValues(grupoemailDto);
		document.executeScript("complementoRestore()");
	}
	
}
