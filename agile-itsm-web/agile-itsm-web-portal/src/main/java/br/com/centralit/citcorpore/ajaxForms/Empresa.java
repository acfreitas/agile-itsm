package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.EmpresaDTO;
import br.com.centralit.citcorpore.negocio.EmpresaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author rosana.godinho
 * 
 */
public class Empresa extends AjaxFormAction {

	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		/*
		 * UsuarioDTO usuario = WebUtil.getUsuario(request);
		 * 
		 * if (usuario == null){ document.alert("Sessão expirada! Favor efetuar logon novamente!"); document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") +
		 * request.getContextPath() + "'"); return;
		 * 
		 * if (!WebUtil.isUserInGroup(request,Constantes.getValue("GRUPO_GPESS")) && !WebUtil.isUserInGroup(request,Constantes.getValue("GRUPO_DIRETORIA"))){
		 * document.alert("Você não tem permissão para acessar esta funcionalidade!"); document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() +
		 * "/pages/index/index.jsp'"); return; }
		 */
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
		EmpresaDTO empresa = (EmpresaDTO) document.getBean();
		EmpresaService empresaService = (EmpresaService) ServiceLocator.getInstance().getService(EmpresaService.class, WebUtil.getUsuarioSistema(request));
		// if (!empresaService.jaExisteRegistroComMesmoNome(empresa)) {
		if (empresa.getIdEmpresa() == null || empresa.getIdEmpresa().intValue() == 0) {
			empresa.setDataInicio(UtilDatas.getDataAtual());
			empresaService.create(empresa);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			empresaService.update(empresa);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("limpar_LOOKUP_EMPRESA()");
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
		EmpresaDTO empresa = (EmpresaDTO) document.getBean();
		EmpresaService empresaService = (EmpresaService) ServiceLocator.getInstance().getService(EmpresaService.class, null);

		empresa = (EmpresaDTO) empresaService.restore(empresa);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(empresa);

	}

	/**
	 * Seta a data atual na data final ao excluir um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void atualizaData(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		EmpresaDTO empresa = (EmpresaDTO) document.getBean();
		EmpresaService empresaService = (EmpresaService) ServiceLocator.getInstance().getService(EmpresaService.class, null);
		if (empresa.getIdEmpresa().intValue() > 0) {
			empresa.setDataFim(UtilDatas.getDataAtual());

			empresaService.update(empresa);

		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.alert(UtilI18N.internacionaliza(request, "MSG07"));

		document.executeScript("limpar_LOOKUP_EMPRESA()");

	}

	@SuppressWarnings("rawtypes")
	public Class getBeanClass() {
		return EmpresaDTO.class;
	}

}
