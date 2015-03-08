package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorCotacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AvaliacaoFornecedorService;
import br.com.centralit.citcorpore.negocio.CotacaoService;
import br.com.centralit.citcorpore.negocio.FornecedorCotacaoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class FornecedorCotacao extends AjaxFormAction {


    @SuppressWarnings("rawtypes")
    public Class getBeanClass() {
        return CotacaoDTO.class;
    }
    
    private void configuraBotoes(DocumentHTML document, HttpServletRequest request, CotacaoDTO cotacaoDto) throws Exception {
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoDto = (CotacaoDTO) cotacaoService.restore(cotacaoDto);
        if (cotacaoDto == null)
            return;
        
        document.executeScript("document.getElementById('addFornecedor').style.display = 'none';");
        document.executeScript("document.getElementById('btnSugerirFornecedores').style.display = 'none';");

        SituacaoCotacao situacao = SituacaoCotacao.valueOf(cotacaoDto.getSituacao());
        request.setAttribute("situacao", situacao);
        if (situacao.equals(SituacaoCotacao.EmAndamento)) {
            document.executeScript("document.getElementById('addFornecedor').style.display = 'block';");
            document.executeScript("document.getElementById('btnSugerirFornecedores').style.display = 'block';");
        }
    }
    
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            CotacaoDTO cotacaoDto = (CotacaoDTO) document.getBean();
            request.setAttribute("idCotacao", ""+cotacaoDto.getIdCotacao());       
            exibeFornecedoresCotacao(document, request, cotacaoDto);
            configuraBotoes(document, request, cotacaoDto);
        }finally{
            document.executeScript("parent.escondeJanelaAguarde()");
        }
    }

    private void exibeFornecedoresCotacao(DocumentHTML document, HttpServletRequest request, CotacaoDTO cotacaoDto) throws Exception {
        HTMLTable tblFornecedoresCotacao = document.getTableById("tblFornecedoresCotacao");
        tblFornecedoresCotacao.deleteAllRows();

        FornecedorCotacaoService fornecedorCotacaoService = (FornecedorCotacaoService) ServiceLocator.getInstance().getService(FornecedorCotacaoService.class, null);
        Collection<FornecedorCotacaoDTO> colFornecedorCotacao = fornecedorCotacaoService.findByIdCotacao(cotacaoDto.getIdCotacao());
        if (colFornecedorCotacao != null && !colFornecedorCotacao.isEmpty()) {
            Collection<FornecedorDTO> colFornecedores = new ArrayList();
            FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
            AvaliacaoFornecedorService avaliacaoFornecedorService = (AvaliacaoFornecedorService) ServiceLocator.getInstance().getService(AvaliacaoFornecedorService.class, null);
            for (FornecedorCotacaoDTO fornecedorCotacaoDto : colFornecedorCotacao) {
                FornecedorDTO fornecedorDto = new FornecedorDTO();
                fornecedorDto.setIdFornecedor(fornecedorCotacaoDto.getIdFornecedor());
                fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
                if (fornecedorDto != null) {
                    if (avaliacaoFornecedorService.fornecedorQualificado(fornecedorDto.getIdFornecedor())) 
                        fornecedorDto.setQualificado(UtilI18N.internacionaliza(request, "citcorpore.comum.sim"));
                    else
                        fornecedorDto.setQualificado(UtilI18N.internacionaliza(request, "citcorpore.comum.nao"));
                    colFornecedores.add(fornecedorDto);
                }
            }
            tblFornecedoresCotacao.addRowsByCollection(colFornecedores, 
                                                new String[] {"","cnpjFormatado","nomeFantasia","telefone","email","nomeContato","qualificado"}, 
                                                null, 
                                                "", 
                                                new String[] {"gerarImgExclusaoFornec"}, 
                                                null, 
                                                null);       
        }    
    }

    public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        CotacaoDTO cotacaoDto = (CotacaoDTO) document.getBean();
        if (cotacaoDto.getIdFornecedor() == null)
            return;
        
        FornecedorCotacaoService fornecedorCotacaoService = (FornecedorCotacaoService) ServiceLocator.getInstance().getService(FornecedorCotacaoService.class, null);
        FornecedorCotacaoDTO fornecedorCotacaoDto = new FornecedorCotacaoDTO();
        fornecedorCotacaoDto.setIdCotacao(cotacaoDto.getIdCotacao());
        fornecedorCotacaoDto.setIdFornecedor(cotacaoDto.getIdFornecedor());
        fornecedorCotacaoService.delete(fornecedorCotacaoDto);
        exibeFornecedoresCotacao(document, request, cotacaoDto);
    }
    
    public void sugereFornecedores(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        HTMLTable tblFornecedores = document.getTableById("tblFornecedores");
        tblFornecedores.deleteAllRows();

        CotacaoDTO cotacaoDto = (CotacaoDTO) document.getBean();
        
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, WebUtil.getUsuarioSistema(request));
        Collection<FornecedorDTO> fornecedores = cotacaoService.sugereFornecedores(cotacaoDto);
        
        if (fornecedores != null && !fornecedores.isEmpty()) {
            tblFornecedores.addRowsByCollection(fornecedores, 
                                                new String[] {"","cnpjFormatado","nomeFantasia","telefone","email","nomeContato"}, 
                                                null, 
                                                "", 
                                                new String[] {"gerarSelecaoFornecedor"}, 
                                                null, 
                                                null);  
            document.executeScript("document.getElementById('btnSelecionarFornecedores').style.display = 'block';");       
        }
        document.executeScript("$(\"#POPUP_SUGESTAO_FORNECEDORES\").dialog(\"open\");");
    }
    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        CotacaoDTO cotacaoDto = (CotacaoDTO) document.getBean();
        Collection<FornecedorDTO> colFornecedores = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FornecedorDTO.class, "colFornecedores_Serialize", request);
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoService.incluiFornecedores(cotacaoDto, colFornecedores);
        exibeFornecedoresCotacao(document, request, cotacaoDto);
    }    
}
