package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ModeloEmailService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class ModeloEmail extends AjaxFormAction {

	public Class getBeanClass() {
		return ModeloEmailDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModeloEmailDTO modeloEmailDTO = (ModeloEmailDTO) document.getBean();
		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, WebUtil.getUsuarioSistema(request) );
		
		// Verificvando a existência do DTO e do Serviço.
		if (modeloEmailDTO != null & modeloEmailService != null) {
			// Atualizando o modelo de e-mail.
			if (modeloEmailDTO.getIdModeloEmail() != null && modeloEmailDTO.getIdModeloEmail().intValue() > 0) {
				modeloEmailService.update(modeloEmailDTO);
				document.alert(UtilI18N.internacionaliza(request, "MSG06") );
			} else { // Inserção
				// Verificando se o modelo já existe.
				if (modeloEmailService.findByIdentificador(modeloEmailDTO.getIdentificador() ) != null) { 
					document.alert(UtilI18N.internacionaliza(request, "MSE01") );
				} else {
					// Inserindo.
					modeloEmailService.create(modeloEmailDTO);
					document.alert(UtilI18N.internacionaliza(request, "MSG05") );
				}				
			}
			
			HTMLForm form = document.getForm("form");
			form.clear();
			
			document.executeScript("limpar()");
		}
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModeloEmailDTO modeloTextualBean = (ModeloEmailDTO) document.getBean();
		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, WebUtil.getUsuarioSistema(request) );
		modeloTextualBean = (ModeloEmailDTO) modeloEmailService.restore(modeloTextualBean);

		HTMLForm form = document.getForm("form");
		form.clear();		
		form.setValues(modeloTextualBean);

		document.executeScript("setDataEditor()");
	}

	public void listModelosInPopup(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
			return;
		}
		
		ModeloEmailDTO modeloTextualForm = (ModeloEmailDTO) document.getBean();
		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, WebUtil.getUsuarioSistema(request) );
		
		Collection col = null;
		col = modeloEmailService.getAtivos();

		if (modeloTextualForm.getMetodoExecutarVolta() == null || modeloTextualForm.getMetodoExecutarVolta().trim().equalsIgnoreCase("") ) {
			modeloTextualForm.setMetodoExecutarVolta("associarModelo");
		}

		String strTable = "<table width='100%'>";

		strTable += "<tr>";
		strTable += "<td class=\"linhaSubtituloGrid\">";
		strTable += "&nbsp;";
		strTable += "</td>";
		strTable += "<td class=\"linhaSubtituloGrid\">";
		strTable += ""
				+ UtilI18N.internacionaliza(request, "baseConhecimento.titulo")
				+ "";
		strTable += "</td>";
		strTable += "</tr>";

		if (col != null && col.size() > 0) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				ModeloEmailDTO modeloEmailDTO = (ModeloEmailDTO) it.next();

				strTable += "<tr>";
				strTable += "<td>";
				strTable += "<img src=\""
						+ Constantes.getValue("SERVER_ADDRESS")
						+ Constantes.getValue("CONTEXTO_APLICACAO")
						+ "/imagens/setaBaixo.JPG\" title='"
						+ UtilI18N
								.internacionaliza(request,
										"modeloemail.cliqueAquiParaCopiarTextoModeloCampo")
						+ "' border=\"0\" onclick=\""
						+ modeloTextualForm.getMetodoExecutarVolta() + "('"
						+ modeloTextualForm.getCampoSelecaoModeloTextual()
						+ "'," + modeloEmailDTO.getIdModeloEmail()
						+ ")\" style=\"cursor:pointer\" >";
				strTable += "</td>";
				strTable += "<td>";
				strTable += modeloEmailDTO.getTitulo();
				strTable += "</td>";
				strTable += "</tr>";
			}
		} else {
			strTable += "<tr>";
			strTable += "<td>";
			strTable += "<b>"
					+ UtilI18N.internacionaliza(request,
							"modeloemail.naoHaModelosCadastradosAtivos")
					+ "</b>";
			strTable += "</td>";
			strTable += "</tr>";
		}
		strTable += "</table>";

		HTMLElement listagemModelosTextuais = document
				.getElementById(modeloTextualForm.getDiv());
		listagemModelosTextuais.setInnerHTML(strTable);
	}

	public void listModelos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
		
			return;
		}
		
		ModeloEmailDTO modeloTextualForm = (ModeloEmailDTO) document.getBean();
		
		if (modeloTextualForm.getTipoCampo() == null || modeloTextualForm.getTipoCampo().equalsIgnoreCase("") ) {
			modeloTextualForm.setTipoCampo("L");
		}
		
		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, WebUtil.getUsuarioSistema(request) );
		
		Collection col = null;
		col = modeloEmailService.getAtivos();

		String strTable = "<table width='100%'>";

		strTable += "<tr>";
		strTable += "<td colspan='2'>";
		strTable += "<input type='button' name='btnFecharModeloTextual' value='Fechar' title='"
				+ UtilI18N.internacionaliza(request, "citcorpore.comum.fechar")
				+ "' onclick=\"document.getElementById('listagemModelosTextuais"
				+ modeloTextualForm.getCampoSelecaoModeloTextual()
				+ "').style.display='none'\"><br><font color='red'>"
				+ UtilI18N.internacionaliza(request,
						"modeloemail.selecioneModelodesejaAplicarTexto")
				+ "</font>";
		strTable += "</td>";
		strTable += "</tr>";
		strTable += "<tr>";
		strTable += "<td class=\"linhaSubtituloGrid\">";
		strTable += "&nbsp;";
		strTable += "</td>";
		strTable += "<td class=\"linhaSubtituloGrid\">";
		strTable += ""
				+ UtilI18N.internacionaliza(request, "baseConhecimento.titulo")
				+ "";
		strTable += "</td>";
		strTable += "</tr>";

		if (col != null && col.size() > 0) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				ModeloEmailDTO modeloEmailDTO = (ModeloEmailDTO) it.next();

				strTable += "<tr>";
				strTable += "<td>";
				strTable += "<img src=\""
						+ Constantes.getValue("SERVER_ADDRESS")
						+ Constantes.getValue("CONTEXTO_APLICACAO")
						+ "/imagens/setaBaixo.JPG\" title='"
						+ UtilI18N
								.internacionaliza(request,
										"modeloemail.cliqueAquiParaCopiarTextoModeloCampo")
						+ "' border=\"0\" onclick=\"associarModelo('"
						+ modeloTextualForm.getCampoSelecaoModeloTextual()
						+ "'," + modeloEmailDTO.getIdModeloEmail() + ", '"
						+ modeloTextualForm.getTipoCampo()
						+ "')\" style=\"cursor:pointer\" >";
				strTable += "</td>";
				strTable += "<td>";
				strTable += modeloEmailDTO.getTitulo();
				strTable += "</td>";
				strTable += "</tr>";
			}
		} else {
			strTable += "<tr>";
			strTable += "<td>";
			strTable += "<b>"
					+ UtilI18N.internacionaliza(request,
							"modeloemail.naoHaModelosCadastradosAtivos")
					+ "</b>";
			strTable += "</td>";
			strTable += "</tr>";
		}
		strTable += "</table>";

		HTMLElement listagemModelosTextuais = document
				.getElementById("listagemModelosTextuais"
						+ modeloTextualForm.getCampoSelecaoModeloTextual());
		listagemModelosTextuais.setInnerHTML(strTable);
	}

	public void copiaModeloParaTextoInPopup(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
			return;
		}
		
		ModeloEmailDTO orientacaoForm = (ModeloEmailDTO) document.getBean();
		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, WebUtil.getUsuarioSistema(request) );
		ModeloEmailDTO modeloTextualBean = (ModeloEmailDTO) modeloEmailService.restore(orientacaoForm);
		
		if (modeloTextualBean != null) {
			document.executeScript("FCKeditorAPI.GetInstance( '"
					+ orientacaoForm.getCampoSelecaoModeloTextual()
					+ "' ).SetData('" + modeloTextualBean.getTexto() + "')");
			document.executeScript(orientacaoForm.getDiv() + ".hide()");
		}
	}

	public void copiaModeloParaTexto(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada") );
		
			return;
		}
		
		ModeloEmailDTO modeloTextualForm = (ModeloEmailDTO) document.getBean();
		
		if (modeloTextualForm.getTipoCampo() == null || modeloTextualForm.getTipoCampo().equalsIgnoreCase("") ) {
			modeloTextualForm.setTipoCampo("L");
		}
		
		String tipoCampo = modeloTextualForm.getTipoCampo();
		
		ModeloEmailService modeloEmailService = (ModeloEmailService) ServiceLocator.getInstance().getService(ModeloEmailService.class, WebUtil.getUsuarioSistema(request) );
		ModeloEmailDTO modeloTextualBean = (ModeloEmailDTO) modeloEmailService.restore(modeloTextualForm);
		
		if (modeloTextualBean != null) {
			if (tipoCampo.equalsIgnoreCase("L")) {
				document.executeScript("FCKeditorAPI.GetInstance( '"
						+ modeloTextualForm.getCampoSelecaoModeloTextual()
						+ "' ).SetData('" + modeloTextualBean.getTexto() + "')");
			} else {
				document.executeScript("document.formQuestionario."
						+ modeloTextualForm.getCampoSelecaoModeloTextual()
						+ ".value = ObjectUtils.decodificaEnter('"
						+ UtilHTML.retiraFormatacaoHTML(UtilHTML
								.decodeHTML(modeloTextualBean.getTexto() ) )
						+ "')");
			}
			HTMLElement listagemModelosTextuais = document
					.getElementById("listagemModelosTextuais"
							+ modeloTextualForm.getCampoSelecaoModeloTextual() );
			listagemModelosTextuais.setVisible(false);
		}
	}
}
