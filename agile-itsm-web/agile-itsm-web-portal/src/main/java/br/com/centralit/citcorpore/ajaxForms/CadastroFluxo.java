package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.servico.FluxoService;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

public class CadastroFluxo extends AjaxFormAction {

    @Override
    public Class<FluxoDTO> getBeanClass() {
        return FluxoDTO.class;
    }

    private FluxoService fluxoService;

    private FluxoService getFluxoService() throws Exception {
        if (fluxoService == null) {
            fluxoService = (FluxoService) ServiceLocator.getInstance().getService(FluxoService.class, null);
        }
        return fluxoService;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        final FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
        if (fluxoDto.getIdFluxo() != null) {
            this.restore(document, request, response);
        }
    }

    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        FluxoDTO fluxoDto = (FluxoDTO) document.getBean();

        String variaveis = fluxoDto.getVariaveis();
        variaveis = variaveis.replaceAll("\n", ";");
        fluxoDto.setVariaveis(variaveis);
        if (fluxoDto.getIdFluxo() == null || fluxoDto.getIdFluxo().intValue() == 0) {
            fluxoDto = (FluxoDTO) this.getFluxoService().create(fluxoDto);
        } else {
            this.getFluxoService().update(fluxoDto);
        }

        document.alert("Registro gravado com sucesso");
        document.executeScript("parent.atualizar(" + fluxoDto.getIdFluxo() + ");");
    }

    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        FluxoDTO fluxoDto = (FluxoDTO) document.getBean();
        fluxoDto = (FluxoDTO) this.getFluxoService().restore(fluxoDto);
        if (fluxoDto.getDataFim() != null) {
            fluxoDto = this.getFluxoService().findByTipoFluxo(fluxoDto.getIdTipoFluxo());
        }

        if (fluxoDto != null) {
            if (fluxoDto.getVariaveis() != null) {
                String variaveis = fluxoDto.getVariaveis();
                variaveis = variaveis.replaceAll(";", "\n");
                fluxoDto.setVariaveis(variaveis);
            }
            final HTMLForm form = document.getForm("form");
            form.setValues(fluxoDto);
        }
    }

    public void delete(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final FluxoDTO fluxoDto = (FluxoDTO) document.getBean();

        if (fluxoDto.getIdFluxo() == null || fluxoDto.getIdFluxo().intValue() == 0) {
            return;
        }

        this.getFluxoService().delete(fluxoDto);
        document.alert("Fluxo excluído com sucesso");
        document.executeScript("parent.atualizar(" + fluxoDto.getIdFluxo() + ");");
    }

}
