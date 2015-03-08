package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * 
 * @author Cledson.junior
 *
 */
public class ServicoContratoUnico extends ServicoContrato {
	
	/**
	 * Inclui registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) document.getBean();
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		List<FluxoServicoDTO> fluxoServicoDTOs = (ArrayList<FluxoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FluxoServicoDTO.class, "fluxosSerializados", request);
		
		servicoContratoDTO.setListaFluxo((fluxoServicoDTOs == null ? new ArrayList<FluxoServicoDTO>() : fluxoServicoDTOs ));
		
		if (servicoContratoDTO.getIdServicoContrato() == null || servicoContratoDTO.getIdServicoContrato().intValue() == 0) {
			if(servicoContratoService.findByIdServicoContrato(servicoContratoDTO.getIdServico(), servicoContratoDTO.getIdContrato()) != null) {
				document.alert(UtilI18N.internacionaliza(request, "servicoContrato.servicoJaVinculadoContrato"));
				return;
			}
			servicoContratoDTO = (ServicoContratoDTO)servicoContratoService.create(servicoContratoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		}else {
			servicoContratoService.update(servicoContratoDTO);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		document.executeScript("closePopup(" + servicoContratoDTO.getIdServicoContrato() + ");");
	}
	
	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	
	public Class<ServicoContratoDTO> getBeanClass() {
		return ServicoContratoDTO.class;
	}
	
}
