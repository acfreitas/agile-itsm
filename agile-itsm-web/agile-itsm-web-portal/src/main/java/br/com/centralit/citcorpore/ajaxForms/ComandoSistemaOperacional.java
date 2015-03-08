package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ComandoDTO;
import br.com.centralit.citcorpore.bean.ComandoSistemaOperacionalDTO;
import br.com.centralit.citcorpore.bean.SistemaOperacionalDTO;
import br.com.centralit.citcorpore.negocio.ComandoService;
import br.com.centralit.citcorpore.negocio.ComandoSistemaOperacionalService;
import br.com.centralit.citcorpore.negocio.SistemaOperacionalService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author ygor.magalhaes
 *
 */

@SuppressWarnings("rawtypes")
public class ComandoSistemaOperacional extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SistemaOperacionalService sistemaOperacionalService = (SistemaOperacionalService) ServiceLocator.getInstance().getService(SistemaOperacionalService.class, null);

		// Verificando a existência do serviço.
		if (sistemaOperacionalService != null) {

			HTMLSelect selectSO = (HTMLSelect) document.getSelectById("idSistemaOperacional");

			Collection<SistemaOperacionalDTO> lista = sistemaOperacionalService.list();

			selectSO.removeAllOptions();
			selectSO.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") );

			for (SistemaOperacionalDTO sis : lista) {
				selectSO.addOption(String.valueOf(sis.getId() ), StringEscapeUtils.escapeJavaScript(sis.getNome() ));
			}

			ComandoService comandoService = (ComandoService) ServiceLocator.getInstance().getService(ComandoService.class, null);

			HTMLSelect selectComando = (HTMLSelect) document.getSelectById("idComando");

			Collection<ComandoDTO> listaComando = comandoService.list();

			selectComando.removeAllOptions();

			selectComando.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione") );

			for (ComandoDTO sis : listaComando) {
				selectComando.addOption(String.valueOf(sis.getId() ), StringEscapeUtils.escapeJavaScript(sis.getDescricao() ));
			}
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Obtendo os dados do formulário.
		ComandoSistemaOperacionalDTO comandoSODTO = (ComandoSistemaOperacionalDTO) document.getBean();
		// Criando serviço (objeto da camada de serviço).
		ComandoSistemaOperacionalService comandoSOService = (ComandoSistemaOperacionalService) ServiceLocator.getInstance()
				.getService(ComandoSistemaOperacionalService.class, null);

		// Verificando a existência do DTO e do serviço.
		if (comandoSODTO != null && comandoSOService != null) {
			// Inserindo o comando de SO.
			if (comandoSODTO.getId() == null || comandoSODTO.getId().intValue() == 0) {
				Collection comandoSOJaCadastrado = comandoSOService.find(comandoSODTO);

				// boolean comandoSOjaExiste = comandoSOService.pesquisarExistenciaComandoSO(comandoSODTO);

				// Verificando se o comando de SO já foi cadastrado.
				if (comandoSOJaCadastrado != null && !comandoSOJaCadastrado.isEmpty() ) {
					// Se verdadeiro então alerta o usuário e pede para cadastrar outro comando de SO.
					document.alert(UtilI18N.internacionaliza(request, "MSE01") );
				} else {
					comandoSOService.create(comandoSODTO);
				    document.alert(UtilI18N.internacionaliza(request, "MSG05") );
				}
			} else { // Atualizando o comando de SO.
			    comandoSOService.update(comandoSODTO);
			    document.alert(UtilI18N.internacionaliza(request, "MSG06") );
			}

			// Limpando o formulário.
			HTMLForm form = document.getForm("form");
			form.clear();
		}
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Obtendo os dados do formulário de cadastro.
		ComandoSistemaOperacionalDTO comandoSODTO = (ComandoSistemaOperacionalDTO) document.getBean();
		// Obtendo o serviço.
		ComandoSistemaOperacionalService comandoSOService = (ComandoSistemaOperacionalService) ServiceLocator.getInstance()
				.getService(ComandoSistemaOperacionalService.class, null);

		// Verificando a existência do DTO e do serviço.
		if (comandoSODTO != null && comandoSOService != null) {
			// Recuperando o objeto.
			comandoSODTO = (ComandoSistemaOperacionalDTO) comandoSOService.restore(comandoSODTO);

			// Limpando o formulário.
			HTMLForm form = document.getForm("form");
			form.clear();

			// Preenchendo o formulário com os dados do objeto recuperado.
			form.setValues(comandoSODTO);
		}
	}

	public Class getBeanClass() {
		return ComandoSistemaOperacionalDTO.class;
	}
}