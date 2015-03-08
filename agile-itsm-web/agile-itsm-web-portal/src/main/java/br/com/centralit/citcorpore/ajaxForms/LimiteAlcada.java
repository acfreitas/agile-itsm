/**
 *
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.LimiteAlcadaDTO;
import br.com.centralit.citcorpore.negocio.AlcadaService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.LimiteAlcadaService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author rodrigo.oliveira
 *
 */
public class LimiteAlcada extends AjaxFormAction {

	private GrupoService grupoService;
	private LimiteAlcadaService limiteAlcadaService;
	private AlcadaService alcadaService;

	@Override
	public Class<LimiteAlcadaDTO> getBeanClass() {
		return LimiteAlcadaDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		preencheComboAlcada(document, request);

		preencheComboGrupo(document, request);

	}

	private void inicializaCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	private void preencheComboGrupo(DocumentHTML document, HttpServletRequest request) throws Exception {
		HTMLSelect combo = (HTMLSelect) document.getSelectById("idGrupo");
		inicializaCombo(combo, request);

		Collection<GrupoDTO> listaCategoriaAux = getGrupoService().list();
		for (GrupoDTO c : listaCategoriaAux) {
			if (c.getDataFim() == null) {
				combo.addOption(c.getIdGrupo().toString(), c.getNome());
			}
		}

	}

	private void preencheComboAlcada(DocumentHTML document, HttpServletRequest request) throws Exception {
		HTMLSelect combo = (HTMLSelect) document.getSelectById("idAlcada");
		inicializaCombo(combo, request);

		Collection<AlcadaDTO> listaAlcada = getAlcadaService().list();
		for (AlcadaDTO c : listaAlcada) {
			if (c.getSituacao().equalsIgnoreCase("A")) {
				combo.addOption(c.getIdAlcada().toString(), c.getNomeAlcada());
			}
		}

	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LimiteAlcadaDTO limiteAlcadaDto = (LimiteAlcadaDTO) document.getBean();

		if (limiteAlcadaDto.getIdLimiteAlcada() == null || limiteAlcadaDto.getIdLimiteAlcada() == 0) {
			getLimiteAlcadaService(request).create(limiteAlcadaDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			getLimiteAlcadaService(request).update(limiteAlcadaDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LimiteAlcadaDTO limiteAlcadaDto = (LimiteAlcadaDTO) document.getBean();

		if (limiteAlcadaDto.getIdLimiteAlcada().intValue() > 0) {
			getLimiteAlcadaService(request).delete(limiteAlcadaDto);
		}

		HTMLForm form = document.getForm("form");
		form.clear();
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LimiteAlcadaDTO limiteAlcadaDto = (LimiteAlcadaDTO) document.getBean();
		limiteAlcadaDto = (LimiteAlcadaDTO) getLimiteAlcadaService(request).restore(limiteAlcadaDto);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(limiteAlcadaDto);
	}

	private GrupoService getGrupoService() throws ServiceException, Exception{
		if(grupoService == null){
			grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);;
		}
		return grupoService;
	}

	private AlcadaService getAlcadaService() throws ServiceException, Exception{
		if(alcadaService == null){
			alcadaService = (AlcadaService) ServiceLocator.getInstance().getService(AlcadaService.class, null);;
		}
		return alcadaService;
	}

	public LimiteAlcadaService getLimiteAlcadaService(HttpServletRequest request) throws ServiceException, Exception {
		if(limiteAlcadaService == null){
			limiteAlcadaService = (LimiteAlcadaService) ServiceLocator.getInstance().getService(LimiteAlcadaService.class, WebUtil.getUsuarioSistema(request));
		}
		return limiteAlcadaService;
	}

}
