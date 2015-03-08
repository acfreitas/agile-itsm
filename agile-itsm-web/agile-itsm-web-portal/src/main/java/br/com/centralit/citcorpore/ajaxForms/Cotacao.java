package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Timestamp;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CentroResultadoService;
import br.com.centralit.citcorpore.negocio.CotacaoService;
import br.com.centralit.citcorpore.negocio.EnderecoService;
import br.com.centralit.citcorpore.negocio.ProjetoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class Cotacao extends AjaxFormAction {

    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CentroResultadoService centroResultadoService = (CentroResultadoService) ServiceLocator.getInstance().getService(CentroResultadoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idCentroCusto = (HTMLSelect) document.getSelectById("idCentroCusto");
        idCentroCusto.removeAllOptions();
        idCentroCusto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colCCusto = centroResultadoService.listPermiteRequisicaoProduto();
        if(colCCusto != null && !colCCusto.isEmpty())
            idCentroCusto.addOptions(colCCusto, "idCentroResultado", "nomeHierarquizado", null);
                
        HTMLSelect idProjeto = (HTMLSelect) document.getSelectById("idProjeto");
        idProjeto.removeAllOptions();
        idProjeto.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        ProjetoService projetoService = (ProjetoService) ServiceLocator.getInstance().getService(ProjetoService.class, WebUtil.getUsuarioSistema(request));
        Collection colProjetos = projetoService.list();
        if(colProjetos != null && !colProjetos.isEmpty()) 
            idProjeto.addOptions(colProjetos, "idProjeto", "nomeProjeto", null);
        
        EnderecoService enderecoService = (EnderecoService) ServiceLocator.getInstance().getService(EnderecoService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idEnderecoEntrega = (HTMLSelect) document.getSelectById("idEnderecoEntrega");
        idEnderecoEntrega.removeAllOptions();
        idEnderecoEntrega.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        Collection colEnderecos = enderecoService.recuperaEnderecosEntregaProduto();
        if(colEnderecos != null && !colEnderecos.isEmpty())
            idEnderecoEntrega.addOptions(colEnderecos, "idEndereco", "enderecoStr", null);
        
    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        CotacaoDTO cotacaoDto = (CotacaoDTO) document.getBean();
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        if (cotacaoDto.getIdCotacao() == null) {
            cotacaoDto.setUsuarioDto(usuario);
            cotacaoDto.setIdResponsavel(usuario.getIdEmpregado());
            cotacaoDto.setIdEmpresa(usuario.getIdEmpresa());
            cotacaoDto.setDataHoraCadastro(new Timestamp(System.currentTimeMillis()));
            cotacaoDto = (CotacaoDTO) cotacaoService.create(cotacaoDto);
            HTMLForm form = document.getForm("form");
            form.setValues(cotacaoDto);
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        }else{
        	cotacaoDto.setDataHoraCadastro(new Timestamp(System.currentTimeMillis()));
            cotacaoService.update(cotacaoDto);
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        }
       
        document.executeScript("document.getElementById('divEncerramento').style.display = 'block'");
        document.executeScript("carregarFrames()");
    }

    public void encerra(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        CotacaoDTO cotacaoDto = (CotacaoDTO) document.getBean();
        if (cotacaoDto.getIdCotacao() == null) 
            return;
        
        cotacaoDto.setUsuarioDto(usuario);
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoService.encerra(cotacaoDto);
        document.alert(UtilI18N.internacionaliza(request, "cotacao.confirmacaoEncerramento"));
        document.executeScript("limpar()");
    }
    
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CotacaoDTO cotacaoDto = (CotacaoDTO) document.getBean();
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);

        cotacaoDto = (CotacaoDTO) cotacaoService.restore(cotacaoDto);

        HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(cotacaoDto);
    }

    @SuppressWarnings("rawtypes")
    public Class getBeanClass() {
        return CotacaoDTO.class;
    }
}
