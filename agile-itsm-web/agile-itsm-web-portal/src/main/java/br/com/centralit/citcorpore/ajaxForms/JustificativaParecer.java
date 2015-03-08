package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.ParecerService;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class JustificativaParecer extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	@Override
	public Class getBeanClass() {
		return JustificativaParecerDTO.class;
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exceptio
	 *             Metodo de salvar
	 * @author thays.araujo
	 */

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		JustificativaParecerDTO justificativaParecerDto = (JustificativaParecerDTO) document.getBean();

		JustificativaParecerService justificativaParecerService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, null);
		
		if(justificativaParecerDto.getSituacao()== null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.camposObrigatorios"));
			return;
		}
		
		if (justificativaParecerDto.getAplicavelRequisicao() == null)
		    justificativaParecerDto.setAplicavelRequisicao("N");
        if (justificativaParecerDto.getAplicavelCotacao() == null)
            justificativaParecerDto.setAplicavelCotacao("N");

        if (justificativaParecerDto.getIdJustificativa() == null || justificativaParecerDto.getIdJustificativa() == 0) {
        	if(justificativaParecerService.consultarJustificativaAtiva(justificativaParecerDto)){
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
        	}
        	justificativaParecerDto .setDataInicio(Util.getSqlDataAtual());
			justificativaParecerService.create(justificativaParecerDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));

		} else {
			if(justificativaParecerService.consultarJustificativaAtiva(justificativaParecerDto)){
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
				return;
        	}
			justificativaParecerService.update(justificativaParecerDto);
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
	 *             Metodo colocar status Inativo quando for solicitado a exclusão do usuario.
	 */

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JustificativaParecerDTO justificativaParecerDto = (JustificativaParecerDTO) document.getBean();
		
		ParecerService parecerService = (ParecerService)ServiceLocator.getInstance().getService(ParecerService.class, null);
		
		ParecerDTO parecerDto = new ParecerDTO();

		JustificativaParecerService justificativaParecerService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, null);

		if (justificativaParecerDto.getIdJustificativa().intValue() > 0) {
		
			parecerDto.setIdJustificativa(justificativaParecerDto.getIdJustificativa());
			
			if(parecerService.verificarSeExisteJustificativaParecer(parecerDto)){
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroNaoPodeSerExcluido"));
				return;
			}
			justificativaParecerDto.setDataFim(Util.getSqlDataAtual());
			justificativaParecerService.update(justificativaParecerDto);
			
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
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
		JustificativaParecerDTO justificativaParecerDto = (JustificativaParecerDTO) document.getBean();

		JustificativaParecerService justificativaParecerService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, null);

		justificativaParecerDto = (JustificativaParecerDTO) justificativaParecerService.restore(justificativaParecerDto);

		HTMLForm form = document.getForm("form");

		form.clear();

		form.setValues(justificativaParecerDto);

	}

}
