package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.servico.FluxoService;
import br.com.centralit.bpm.servico.TipoFluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ProcessoNegocioDTO;
import br.com.centralit.citcorpore.bean.ProcessoNivelAutoridadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.NivelAutoridadeService;
import br.com.centralit.citcorpore.negocio.ProcessoNegocioService;
import br.com.centralit.citcorpore.negocio.ProcessoNivelAutoridadeService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProcessoNegocio extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");

			return;
		}

		document.executeScript("GRID_AUTORIDADES.deleteAllRows();");

		NivelAutoridadeService nivelAutoridadeService = (NivelAutoridadeService) ServiceLocator.getInstance().getService(NivelAutoridadeService.class, WebUtil.getUsuarioSistema(request));
		request.setAttribute("colAutoridades", nivelAutoridadeService.list());
		
        GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idGrupoExecutor = (HTMLSelect) document.getSelectById("idGrupoExecutor");
        idGrupoExecutor.removeAllOptions();
        idGrupoExecutor.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        HTMLSelect idGrupoAdministrador = (HTMLSelect) document.getSelectById("idGrupoAdministrador");
        idGrupoAdministrador.removeAllOptions();
        idGrupoAdministrador.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colGrupos = grupoService.list();
        if(colGrupos != null && !colGrupos.isEmpty()) {
        	idGrupoExecutor.addOptions(colGrupos, "idGrupo", "nome", null);
        	idGrupoAdministrador.addOptions(colGrupos, "idGrupo", "nome", null);
        }
        
		FluxoService fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, WebUtil.getUsuarioSistema(request));
		request.setAttribute("colTiposFluxo", fluxoService.list());
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcessoNegocioDTO processoNegocioDto = (ProcessoNegocioDTO) document.getBean();
		ProcessoNegocioService processoNegocioService = (ProcessoNegocioService) ServiceLocator.getInstance().getService(ProcessoNegocioService.class, null);
		if (processoNegocioDto == null || processoNegocioDto.getIdProcessoNegocio() == null)
			return;

		processoNegocioDto = (ProcessoNegocioDTO) processoNegocioService.restore(processoNegocioDto);
		if (processoNegocioDto != null) {
			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(processoNegocioDto);

			ProcessoNivelAutoridadeService processoNivelAutoridadeService = (ProcessoNivelAutoridadeService) ServiceLocator.getInstance().getService(ProcessoNivelAutoridadeService.class, null);
			Collection<ProcessoNivelAutoridadeDTO> colAutoridades = processoNivelAutoridadeService.findByIdProcessoNegocio(processoNegocioDto.getIdProcessoNegocio());
			document.executeScript("GRID_AUTORIDADES.deleteAllRows();");
			if (colAutoridades != null) {
				int i = 0;
				for (ProcessoNivelAutoridadeDTO processoNivelAutoridadeDto : colAutoridades) {
					i++;
					document.executeScript("GRID_AUTORIDADES.addRow()");
					processoNivelAutoridadeDto.setSequencia(i);
					document.executeScript("seqAutoridade = NumberUtil.zerosAEsquerda(" + i + ",5)");
					document.executeScript("exibeAutoridade('" + br.com.citframework.util.WebUtil.serializeObject(processoNivelAutoridadeDto, WebUtil.getLanguage(request)) + "')");
				}
			}
			
			TipoFluxoService tipoFluxoService = (TipoFluxoService) ServiceLocator.getInstance().getService(TipoFluxoService.class, WebUtil.getUsuarioSistema(request));
			Collection<TipoFluxoDTO> colTiposFluxo = tipoFluxoService.findByIdProcessoNegocio(processoNegocioDto.getIdProcessoNegocio());

			document.executeScript("clearAllCheckBoxIdTipoFluxo()");
			if (colTiposFluxo != null){
				for (TipoFluxoDTO tipoFluxoDto : colTiposFluxo) {
					document.executeScript("selectCheckBoxIdTipoFluxo('" + tipoFluxoDto.getIdTipoFluxo() + "')");
				} 
			}
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcessoNegocioDTO processoNegocioDto = (ProcessoNegocioDTO) document.getBean();
		ProcessoNegocioService processoNegocioService = (ProcessoNegocioService) ServiceLocator.getInstance().getService(ProcessoNegocioService.class, null);
		processoNegocioDto.setColAutoridades(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ProcessoNivelAutoridadeDTO.class, "colAutoridades_Serialize", request));
		
		if (processoNegocioDto.getPermissaoSolicitacao() == null)
			processoNegocioDto.setPermissaoSolicitacao("T");

		if (processoNegocioDto.getIdProcessoNegocio() == null) {
			processoNegocioService.create(processoNegocioDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else { 
			processoNegocioService.update(processoNegocioDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.setBean(new ProcessoNegocioDTO());
		load(document, request, response);	
	}
	@Override
	public Class getBeanClass() {
		return ProcessoNegocioDTO.class;
	}
}