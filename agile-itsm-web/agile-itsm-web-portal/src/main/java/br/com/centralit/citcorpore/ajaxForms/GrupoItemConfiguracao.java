package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.GrupoItemConfiguracaoService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class GrupoItemConfiguracao extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.focusInFirstActivateField(null);
	}

	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return GrupoItemConfiguracaoDTO.class;
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exceptio
	 *             Metodo de salvar
	 * @author flavio.santana
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoItemConfiguracaoDTO grupoItemConfiguracao = (GrupoItemConfiguracaoDTO) document.getBean();

		GrupoItemConfiguracaoService grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		if (grupoItemConfiguracao.getIdGrupoItemConfiguracao() == null || grupoItemConfiguracao.getIdGrupoItemConfiguracao() == 0) {
			if (grupoItemConfiguracaoService.VerificaSeCadastrado(grupoItemConfiguracao)) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
			}
			grupoItemConfiguracao.setDataInicio(UtilDatas.getDataAtual());
			grupoItemConfiguracaoService.create(grupoItemConfiguracao);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			grupoItemConfiguracaoService.update(grupoItemConfiguracao);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *          
	 */

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoItemConfiguracaoDTO grupoItemConfiguracao = (GrupoItemConfiguracaoDTO) document.getBean();
		GrupoItemConfiguracaoService grupoICService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		GrupoItemConfiguracaoDTO grupoIcDTO = new GrupoItemConfiguracaoDTO();
		
		grupoIcDTO.setIdGrupoItemConfiguracao(grupoItemConfiguracao.getIdGrupoItemConfiguracao());
		grupoIcDTO.setDataFim(UtilDatas.getDataAtual());
		if(!grupoICService.verificaICRelacionados(grupoIcDTO)) {
			grupoICService.updateNotNull(grupoIcDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}else {
			document.alert(UtilI18N.internacionaliza(request, "gerenciaConfiguracaoTree.GrupoNaoPodeSerApagado"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 *             Metodo para restaura os campos.
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoItemConfiguracaoDTO grupoItemConfiguracao = (GrupoItemConfiguracaoDTO) document.getBean();
		GrupoItemConfiguracaoService grupoItemConfiguracaoService = (GrupoItemConfiguracaoService) ServiceLocator.getInstance().getService(GrupoItemConfiguracaoService.class, null);
		grupoItemConfiguracao = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoService.restore(grupoItemConfiguracao);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(grupoItemConfiguracao);

	}

}
