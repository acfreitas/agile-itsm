package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.GrupoNivelAutoridadeDTO;
import br.com.centralit.citcorpore.bean.NivelAutoridadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.GrupoNivelAutoridadeService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.NivelAutoridadeService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NivelAutoridade extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");

			return;
		}

		document.executeScript("GRID_GRUPOS.deleteAllRows();");

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, WebUtil.getUsuarioSistema(request));
		request.setAttribute("colGrupos", grupoService.listaGruposAtivos());
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		NivelAutoridadeDTO nivelAutoridadeDTO = (NivelAutoridadeDTO) document.getBean();
		NivelAutoridadeService nivelAutoridadeService = (NivelAutoridadeService) ServiceLocator.getInstance().getService(NivelAutoridadeService.class, null);
		if (nivelAutoridadeDTO == null || nivelAutoridadeDTO.getIdNivelAutoridade() == null)
			return;

		nivelAutoridadeDTO = (NivelAutoridadeDTO) nivelAutoridadeService.restore(nivelAutoridadeDTO);
		if (nivelAutoridadeDTO != null) {
			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(nivelAutoridadeDTO);

			GrupoNivelAutoridadeService grupoNivelAutoridadeService = (GrupoNivelAutoridadeService) ServiceLocator.getInstance().getService(GrupoNivelAutoridadeService.class, null);
			Collection<GrupoNivelAutoridadeDTO> colGrupos = grupoNivelAutoridadeService.findByIdNivelAutoridade(nivelAutoridadeDTO.getIdNivelAutoridade());
			document.executeScript("GRID_GRUPOS.deleteAllRows();");
			if (colGrupos != null) {
				int i = 0;
				for (GrupoNivelAutoridadeDTO grupoNivelAutoridadeDto : colGrupos) {
					i++;
					document.executeScript("GRID_GRUPOS.addRow()");
					grupoNivelAutoridadeDto.setSequencia(i);
					document.executeScript("seqGrupo = NumberUtil.zerosAEsquerda(" + i + ",5)");
					document.executeScript("exibeGrupo('" + br.com.citframework.util.WebUtil.serializeObject(grupoNivelAutoridadeDto, WebUtil.getLanguage(request)) + "')");
				}
			}
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		NivelAutoridadeDTO nivelAutoridadeDTO = (NivelAutoridadeDTO) document.getBean();
		NivelAutoridadeService nivelAutoridadeService = (NivelAutoridadeService) ServiceLocator.getInstance().getService(NivelAutoridadeService.class, null);
		nivelAutoridadeDTO.setColGrupos(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GrupoNivelAutoridadeDTO.class, "colGrupos_Serialize", request));

		if (nivelAutoridadeDTO.getIdNivelAutoridade() == null) {
			nivelAutoridadeService.create(nivelAutoridadeDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			nivelAutoridadeService.update(nivelAutoridadeDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.setBean(new NivelAutoridadeDTO());
		load(document, request, response);
	}
	@Override
	public Class getBeanClass() {
		return NivelAutoridadeDTO.class;
	}
}