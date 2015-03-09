package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.rh.bean.MovimentacaoPessoalDTO;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

public class MovimentacaoPessoal extends AjaxFormAction {

    @Override
    public Class<MovimentacaoPessoalDTO> getBeanClass() {
        return MovimentacaoPessoalDTO.class;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final HTMLSelect idCargo = document.getSelectById("idCargo");
        idCargo.removeAllOptions();
        idCargo.addOption("", "--- Selecione ---");

        final ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(
                ProjetoService.class, WebUtil.getUsuarioSistema(request));
        final HTMLSelect idProjeto = document.getSelectById("idProjeto");
        idProjeto.removeAllOptions();
        idProjeto.addOption("", "--- Selecione ---");
        final Collection colProjeto = projetoService.list();
        if (colProjeto != null && !colProjeto.isEmpty()) {
            idProjeto.addOptions(colProjeto, "idProjeto", "nomeProjeto", null);
        }

        final CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance()
                .getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
        final HTMLSelect idCentroCusto = document.getSelectById("idCentroResultado");
        idCentroCusto.removeAllOptions();
        idCentroCusto.addOption("", "--- Selecione ---");
        final Collection colCentroCusto = centroResultadoService.list();
        if (colCentroCusto != null && !colCentroCusto.isEmpty()) {
            idCentroCusto.addOptions(colCentroCusto, "idCentroResultado", "nomeCentroResultado", null);
        }
    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            return;
        }
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert("Sessão expirada! Favor efetuar logon novamente!");
            return;
        }
    }

}
