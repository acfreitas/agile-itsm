package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.servico.FluxoService;
import br.com.centralit.bpm.servico.TipoFluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class Fluxo extends AjaxFormAction {

    @Override
    public Class getBeanClass() {
	return FluxoDTO.class;
    }

    private FluxoService getFluxoService() throws ServiceException, Exception {
	return (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);
    }

    private TipoFluxoService getTipoFluxoService() throws ServiceException, Exception {
	return (TipoFluxoService) ServiceLocator.getInstance().getService(TipoFluxoService.class, null);
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario == null) {
	    document.alert("Sessão expirada! Favor efetuar logon novamente!");
	    document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
	    return;
	}
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
	
	if (fluxoDto.getIdFluxo() == null || fluxoDto.getIdFluxo().intValue() == 0) {
		getFluxoService().create(fluxoDto);
	} else {
		getFluxoService().update(fluxoDto);
	}
	
	document.alert("Registro gravado com sucesso!");

	HTMLForm form = document.getForm("form");
	form.clear();
    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
	fluxoDto = (FluxoDTO) getFluxoService().restore(fluxoDto);
	if (fluxoDto.getDataFim() != null) 
		fluxoDto = getFluxoService().findByTipoFluxo(fluxoDto.getIdTipoFluxo());
	
	if (fluxoDto != null) {
	    TipoFluxoDTO tipoFluxo = new TipoFluxoDTO();
	    tipoFluxo.setIdTipoFluxo(fluxoDto.getIdTipoFluxo());
	    tipoFluxo = (TipoFluxoDTO) getTipoFluxoService().restore(tipoFluxo);

	    document.getElementById("nomeTipo").setValue(tipoFluxo.getNomeFluxo());
	    HTMLForm form = document.getForm("form");
	    form.setValues(fluxoDto);
	}
    }
}
