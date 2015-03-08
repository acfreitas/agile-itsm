package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.LimiteAprovacaoAutoridadeDTO;
import br.com.centralit.citcorpore.bean.LimiteAprovacaoDTO;
import br.com.centralit.citcorpore.bean.LimiteAprovacaoProcessoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorLimiteAprovacaoDTO;
import br.com.centralit.citcorpore.negocio.LimiteAprovacaoAutoridadeService;
import br.com.centralit.citcorpore.negocio.LimiteAprovacaoProcessoService;
import br.com.centralit.citcorpore.negocio.LimiteAprovacaoService;
import br.com.centralit.citcorpore.negocio.NivelAutoridadeService;
import br.com.centralit.citcorpore.negocio.ProcessoNegocioService;
import br.com.centralit.citcorpore.negocio.ValorLimiteAprovacaoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class LimiteAprovacao extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");

			return;
		}

		document.executeScript("GRID_VALORES.deleteAllRows();");

		NivelAutoridadeService nivelAutoridadeService = (NivelAutoridadeService) ServiceLocator.getInstance().getService(NivelAutoridadeService.class, WebUtil.getUsuarioSistema(request));
		request.setAttribute("colAutoridades", nivelAutoridadeService.list());
		
		ProcessoNegocioService processoNegocioService = (ProcessoNegocioService) ServiceLocator.getInstance().getService(ProcessoNegocioService.class, WebUtil.getUsuarioSistema(request));
		request.setAttribute("colProcessos", processoNegocioService.list());
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LimiteAprovacaoDTO limiteAprovacaoDto = (LimiteAprovacaoDTO) document.getBean();
		LimiteAprovacaoService limiteAprovacaoService = (LimiteAprovacaoService) ServiceLocator.getInstance().getService(LimiteAprovacaoService.class, null);
		if (limiteAprovacaoDto == null || limiteAprovacaoDto.getIdLimiteAprovacao() == null)
			return;

		limiteAprovacaoDto = (LimiteAprovacaoDTO) limiteAprovacaoService.restore(limiteAprovacaoDto);
		if (limiteAprovacaoDto != null) {
			HTMLForm form = document.getForm("form");
			form.clear();
			form.setValues(limiteAprovacaoDto);

			ValorLimiteAprovacaoService valorLimiteAprovacaoService = (ValorLimiteAprovacaoService) ServiceLocator.getInstance().getService(ValorLimiteAprovacaoService.class, null);
			Collection<ValorLimiteAprovacaoDTO> colValores = valorLimiteAprovacaoService.findByIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());
			document.executeScript("GRID_VALORES.deleteAllRows();");
			if (colValores != null) {
				int i = 0;
				for (ValorLimiteAprovacaoDTO valorDto : colValores) {
					i++;
					document.executeScript("GRID_VALORES.addRow()");
					valorDto.setSequencia(i);
					document.executeScript("seqValor = NumberUtil.zerosAEsquerda(" + i + ",5)");
					document.executeScript("exibeValor('" + br.com.citframework.util.WebUtil.serializeObject(valorDto, WebUtil.getLanguage(request)) + "')");
				}
			}

			LimiteAprovacaoProcessoService limiteAprovacaoProcessoService = (LimiteAprovacaoProcessoService) ServiceLocator.getInstance().getService(LimiteAprovacaoProcessoService.class, null);
			LimiteAprovacaoAutoridadeService limiteAprovacaoAutoridadeService = (LimiteAprovacaoAutoridadeService) ServiceLocator.getInstance().getService(LimiteAprovacaoAutoridadeService.class, null);

			Collection<LimiteAprovacaoProcessoDTO> colProcessos = limiteAprovacaoProcessoService.findByIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());
			Collection<LimiteAprovacaoAutoridadeDTO> colAutoridades = limiteAprovacaoAutoridadeService.findByIdLimiteAprovacao(limiteAprovacaoDto.getIdLimiteAprovacao());

			document.executeScript("clearAllCheckBoxIdProcessoNegocio()");
			document.executeScript("clearAllCheckBoxIdNivelAutoridade()");
			
			if (colProcessos != null && colProcessos.size() > 0) {
				Integer[] idProcessoNegocio = new Integer[colProcessos.size()];
				int i = 0;
				for (LimiteAprovacaoProcessoDTO limiteDto: colProcessos) {
					idProcessoNegocio[i] = new Integer(limiteDto.getIdProcessoNegocio());
					i++;
				}
				limiteAprovacaoDto.setIdProcessoNegocio(idProcessoNegocio);
			}
			
			if (colAutoridades != null && colAutoridades.size() > 0) {
				Integer[] idNivelAutoridade = new Integer[colAutoridades.size()];
				int i = 0;
				for (LimiteAprovacaoAutoridadeDTO limiteDto: colAutoridades) {
					idNivelAutoridade[i] = new Integer(limiteDto.getIdNivelAutoridade());
					i++;
				}
				limiteAprovacaoDto.setIdNivelAutoridade(idNivelAutoridade);
			}

			if (limiteAprovacaoDto.getIdProcessoNegocio() != null){
				for (int i = 0; i < limiteAprovacaoDto.getIdProcessoNegocio().length; i++) {
					document.executeScript("selectCheckBoxIdProcessoNegocio('" + limiteAprovacaoDto.getIdProcessoNegocio()[i] + "')");
				}
			}
			if (limiteAprovacaoDto.getIdNivelAutoridade() != null){
				for (int i = 0; i < limiteAprovacaoDto.getIdNivelAutoridade().length; i++) {
					document.executeScript("selectCheckBoxIdNivelAutoridade('" + limiteAprovacaoDto.getIdNivelAutoridade()[i] + "')");
				}
			}
		}
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		LimiteAprovacaoDTO limiteAprovacaoDto = (LimiteAprovacaoDTO) document.getBean();
		LimiteAprovacaoService limiteAprovacaoService = (LimiteAprovacaoService) ServiceLocator.getInstance().getService(LimiteAprovacaoService.class, null);
		limiteAprovacaoDto.setColValores(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ValorLimiteAprovacaoDTO.class, "colValores_Serialize", request));

		if (limiteAprovacaoDto.getIdLimiteAprovacao() == null) {
			limiteAprovacaoService.create(limiteAprovacaoDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else { 
			limiteAprovacaoService.update(limiteAprovacaoDto);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();
		document.setBean(new LimiteAprovacaoDTO());
		load(document, request, response);	
	}
	@Override
	public Class getBeanClass() {
		return LimiteAprovacaoDTO.class;
	}
}