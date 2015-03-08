package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.servico.TipoFluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class TipoFluxo extends AjaxFormAction {

    @Override
    public Class getBeanClass() {
	return TipoFluxoDTO.class;
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

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	TipoFluxoDTO fluxoDto = (TipoFluxoDTO) document.getBean();
	fluxoDto = (TipoFluxoDTO) getTipoFluxoService().restore(fluxoDto);
	if (fluxoDto != null) {
	    HTMLForm form = document.getForm("form");
	    form.setValues(fluxoDto);
	}
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	TipoFluxoDTO fluxoDto = (TipoFluxoDTO) document.getBean();

    	if (fluxoDto.getIdTipoFluxo() == null || fluxoDto.getIdTipoFluxo().intValue() == 0) {
    		getTipoFluxoService().create(fluxoDto);
    	} else {
    		getTipoFluxoService().update(fluxoDto);
    	}

    	document.alert("Registro gravado com sucesso!");

    	HTMLForm form = document.getForm("form");
    	form.clear();
        }

}
