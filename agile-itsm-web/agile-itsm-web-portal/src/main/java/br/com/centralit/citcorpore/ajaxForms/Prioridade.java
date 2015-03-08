package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.PrioridadeService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

/**
 * @author leandro.viana
 * 
 */
@SuppressWarnings({ "rawtypes", "unused" })
public class Prioridade extends AjaxFormAction {

	/**
	 * Inicializa dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		// if (!WebUtil.isUserInGroup(request,
		// Constantes.getValue("GRUPO_GPESS"))
		// && !WebUtil.isUserInGroup(request,
		// Constantes.getValue("GRUPO_DIRETORIA"))) {
		// document.alert("Você não tem permissão para acessar esta funcionalidade!");
		// document.executeScript("window.location = '" +
		// Constantes.getValue("SERVER_ADDRESS")
		// + request.getContextPath() + "/pages/index/index.jsp'");
		// return;
		// }

		this.preencherComboGrupoPrioridade(document, request, response);

		document.focusInFirstActivateField(null);
	}

	/**
	 * Inclui ou Atualiza um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// Obtendo o objeto de transferência de dados (DTO).
		PrioridadeDTO prioridadeDTO = (PrioridadeDTO) document.getBean();
		
		// Obtendo o serviço.
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		ArrayList<PrioridadeDTO> listPrioridades = null;
		if(prioridadeService != null){
			listPrioridades = (ArrayList<PrioridadeDTO>) prioridadeService.prioridadesAtivasPorNome(prioridadeDTO.getNomePrioridade() );
		}
		// Verificando se a prioridade informada já foi cadastrada.
		if(listPrioridades!=null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado") );
			return;
		}
		// Verificando se o DTO e o serviço existem.
		if (prioridadeDTO != null && prioridadeService != null) {
			
			// Inserindo.
			if (prioridadeDTO.getIdPrioridade() == null) {
					prioridadeDTO.setSituacao("A");
					
					prioridadeDTO.setIdEmpresa(WebUtil.getIdEmpresa(request) );
					
					prioridadeService.create(prioridadeDTO);
					
					// Notificando o usuário do sucesso da inserção.
					document.alert(UtilI18N.internacionaliza(request, "MSG05") );
				
			} else {
				prioridadeService.update(prioridadeDTO);
				
				// Notificando o usuário do sucesso da atualização.
				document.alert(UtilI18N.internacionaliza(request, "MSG06") );
			}
			
			// Referenciando o formulário.
			HTMLForm form = document.getForm("form");
			
			// Limpando o formulário.
			form.clear();
		}
	}

	/**
	 * Restaura os dados ao clicar em um registro na tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrioridadeDTO prioridade = (PrioridadeDTO) document.getBean();
		this.preencherComboGrupoPrioridade(document, request, response);
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		prioridade = (PrioridadeDTO) prioridadeService.restore(prioridade);
		
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(prioridade);

	}

	/**
	 * Altera a situação da prioridade para Inativo ao confirmar a exclusão.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void alterarSituacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		PrioridadeDTO prioridadeDTO = (PrioridadeDTO) document.getBean();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		if (prioridadeDTO.getIdPrioridade() != null && prioridadeDTO.getIdPrioridade().intValue() != 0) {
			prioridadeDTO.setSituacao("I");
			prioridadeService.update(prioridadeDTO);
			HTMLForm form = document.getForm("form");
			form.clear();
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}

	}
	
	public void preencherComboGrupoPrioridade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);


		HTMLSelect comboGrupoPrioridade = (HTMLSelect) document.getSelectById("grupoPrioridade");
		comboGrupoPrioridade.removeAllOptions();
		comboGrupoPrioridade.addOption("",UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboGrupoPrioridade.addOption("G1",UtilI18N.internacionaliza(request, "prioridade.gp1"));
		comboGrupoPrioridade.addOption("G2",UtilI18N.internacionaliza(request, "prioridade.gp2") );
		comboGrupoPrioridade.addOption("G3",UtilI18N.internacionaliza(request, "prioridade.gp3") );
	}
	

	/**
	 * Informa a classe DTO.
	 */
	public Class getBeanClass() {
		return PrioridadeDTO.class;
	}

}
