package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.AvaliacaoColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO;
import br.com.centralit.citcorpore.bean.CriterioItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorCotacaoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.AvaliacaoColetaPrecoService;
import br.com.centralit.citcorpore.negocio.ColetaPrecoService;
import br.com.centralit.citcorpore.negocio.CotacaoService;
import br.com.centralit.citcorpore.negocio.CriterioAvaliacaoService;
import br.com.centralit.citcorpore.negocio.CriterioItemCotacaoService;
import br.com.centralit.citcorpore.negocio.FornecedorCotacaoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ItemCotacaoService;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

public class ColetaPreco extends AjaxFormAction {
    
    @SuppressWarnings("rawtypes")
    public Class getBeanClass() {
        return ColetaPrecoDTO.class;
    }

    private void configuraBotoes(DocumentHTML document, HttpServletRequest request, ColetaPrecoDTO coletaPrecoBean) throws Exception {
        if (coletaPrecoBean.getIdCotacao() == null)
            return;
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(coletaPrecoBean.getIdCotacao());
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoDto = (CotacaoDTO) cotacaoService.restore(cotacaoDto);
        if (cotacaoDto == null)
            return;
        
        document.executeScript("document.getElementById('btnGravar').style.display = 'none'");
        document.executeScript("document.getElementById('btnLimpar').style.display = 'none'");
        document.executeScript("document.getElementById('btnExcluir').style.display = 'none'");

        SituacaoCotacao situacao = SituacaoCotacao.valueOf(cotacaoDto.getSituacao());
        if (situacao.equals(SituacaoCotacao.EmAndamento)) {
            document.executeScript("document.getElementById('btnGravar').style.display = 'block'");
            document.executeScript("document.getElementById('btnLimpar').style.display = 'block'");
            if (coletaPrecoBean.getIdColetaPreco() != null)
                document.executeScript("document.getElementById('btnExcluir').style.display = 'block'");
        }
    }
    
    public void carregaCombos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, ColetaPrecoDTO coletaPrecoDto) throws Exception {
        HTMLSelect idFornecedor = (HTMLSelect) document.getSelectById("idFornecedor");
        idFornecedor.removeAllOptions();
        idFornecedor.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        
        FornecedorCotacaoService fornecedorCotacaoService = (FornecedorCotacaoService) ServiceLocator.getInstance().getService(FornecedorCotacaoService.class, null);
        Collection<FornecedorCotacaoDTO> colFornecedorCotacao = fornecedorCotacaoService.findByIdCotacao(coletaPrecoDto.getIdCotacao());
        if (colFornecedorCotacao != null) {
            Collection<FornecedorDTO> colFornecedores = new ArrayList();
            FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
            for (FornecedorCotacaoDTO fornecedorCotacaoDto : colFornecedorCotacao) {
                FornecedorDTO fornecedorDto = new FornecedorDTO();
                fornecedorDto.setIdFornecedor(fornecedorCotacaoDto.getIdFornecedor());
                fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
                if (fornecedorDto != null)
                    colFornecedores.add(fornecedorDto);
            }
            String id = null;
            if (coletaPrecoDto.getIdFornecedor() != null)
            	id = ""+coletaPrecoDto.getIdFornecedor();
            idFornecedor.addOptions(colFornecedores, "idFornecedor", "identificacao", id);  
        }
        montaItensFornecedor(document,request,response);
        configuraBotoes(document, request, coletaPrecoDto);
    }
    
    public void montaItensFornecedor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) document.getBean();

        HTMLSelect idItemCotacao = (HTMLSelect) document.getSelectById("idItemCotacao");
        idItemCotacao.removeAllOptions();
        idItemCotacao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        ItemCotacaoService itemCotacaoService = (ItemCotacaoService) ServiceLocator.getInstance().getService(ItemCotacaoService.class, null);
        Collection<ItemCotacaoDTO> colItens = itemCotacaoService.findByIdCotacao(coletaPrecoDto.getIdCotacao());
        if (colItens == null || colItens.isEmpty())
        	return;

        Integer idFornecedor = coletaPrecoDto.getIdFornecedor();
        String id = null;
        if (coletaPrecoDto.getIdItemCotacao() != null)
        	id = ""+coletaPrecoDto.getIdItemCotacao();
    	if (idFornecedor == null) {
            idItemCotacao.addOptions(colItens, "idItemCotacao", "identificacao", id); 
    	}else{
            ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, WebUtil.getUsuarioSistema(request));
            for (ItemCotacaoDTO itemCotacaoDto : colItens) {
                if (coletaPrecoService.findByIdItemCotacaoAndIdFornecedor(idFornecedor, itemCotacaoDto.getIdItemCotacao()) == null)
                	idItemCotacao.addOption(""+itemCotacaoDto.getIdItemCotacao(), itemCotacaoDto.getIdentificacao());
			}
            if (id != null)
            	idItemCotacao.setValue(id);
    	}
    }
    
    public void atualiza(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }
            
            ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) document.getBean();
            carregaCombos(document,request,response,coletaPrecoDto);
        }finally{
        	document.executeScript("habilitaDesabilitaCampos()");
            document.executeScript("parent.escondeJanelaAguarde()");
        }
    }
    
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession(true).setAttribute("colUploadsGED", null);
        try{
            UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }
            
            ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) document.getBean();
            carregaCombos(document,request,response,coletaPrecoDto);
            
            request.setAttribute("idCotacao", ""+coletaPrecoDto.getIdCotacao()); 
            exibeColetasPreco(document,request,coletaPrecoDto);
            restore(document,request,response);
            
        }finally{
            document.executeScript("parent.escondeJanelaAguarde()");
        }
    }

    public void exibeCriteriosVariaveis(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) document.getBean();
        if (coletaPrecoDto.getIdItemCotacao() == null)
            return;
        
        exibeCriteriosVariaveis(document, request, coletaPrecoDto);
    }

    public void exibeEspecificacoesItem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) document.getBean();
        if (coletaPrecoDto.getIdItemCotacao() == null)
            return;
        
        ItemCotacaoService itemCotacaoService = (ItemCotacaoService) ServiceLocator.getInstance().getService(ItemCotacaoService.class, null);
        ItemCotacaoDTO itemCotacaoDto = new ItemCotacaoDTO();
        itemCotacaoDto.setIdItemCotacao(coletaPrecoDto.getIdItemCotacao());
        itemCotacaoDto = (ItemCotacaoDTO) itemCotacaoService.restore(itemCotacaoDto);
        if (itemCotacaoDto != null) {
            itemCotacaoDto.setQuantidadeCotada(itemCotacaoDto.getQuantidade());
            HTMLForm form = document.getForm("form");
            form.setValues(itemCotacaoDto);
        }
    }
    
    private void exibeCriteriosVariaveis(DocumentHTML document, HttpServletRequest request, ColetaPrecoDTO coletaPrecoDto) throws Exception {
        document.executeScript("limpaDivCriterios();");  
        CriterioItemCotacaoService criterioItemCotacaoService = (CriterioItemCotacaoService) ServiceLocator.getInstance().getService(CriterioItemCotacaoService.class, null);
        Collection<CriterioItemCotacaoDTO> colCriterios = criterioItemCotacaoService.findByIdItemCotacao(coletaPrecoDto.getIdItemCotacao());
        
        if (colCriterios != null){
            CriterioAvaliacaoService criterioAvaliacaoService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, null);
            AvaliacaoColetaPrecoService avaliacaoColetaService = (AvaliacaoColetaPrecoService) ServiceLocator.getInstance().getService(AvaliacaoColetaPrecoService.class, null);
            int i = 0;
            for (CriterioItemCotacaoDTO criterioItemCotacaoDto : colCriterios) {
                i++;
                CriterioAvaliacaoDTO criterioDto = new CriterioAvaliacaoDTO();
                criterioDto.setIdCriterio(criterioItemCotacaoDto.getIdCriterio());
                criterioDto = (CriterioAvaliacaoDTO) criterioAvaliacaoService.restore(criterioDto);
                if (criterioDto == null) 
                    return;
                
                String value = "";
                if (coletaPrecoDto.getIdColetaPreco() != null) {
                    AvaliacaoColetaPrecoDTO avaliacaoColetaDto = new AvaliacaoColetaPrecoDTO();
                    avaliacaoColetaDto.setIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
                    avaliacaoColetaDto.setIdCriterio(criterioDto.getIdCriterio());
                    avaliacaoColetaDto = (AvaliacaoColetaPrecoDTO) avaliacaoColetaService.restore(avaliacaoColetaDto);
                    if (avaliacaoColetaDto != null)
                        value = " "+avaliacaoColetaDto.getAvaliacao();
                }
                
                document.executeScript("criaCampoCriterio("+i+","+criterioDto.getIdCriterio()+",'"+criterioDto.getDescricao()+"&nbsp;"+UtilI18N.internacionaliza(request, "cotacao.valoresCriterio")+"','"+value+"');");
            }
        } 
    }
    
    private void exibeColetasPreco(DocumentHTML document, HttpServletRequest request, ColetaPrecoDTO coletaPrecoDto) throws Exception {
        HTMLTable tblColetasPreco = document.getTableById("tblColetasPreco");
        tblColetasPreco.deleteAllRows();

        ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, null);
        Collection<ColetaPrecoDTO> colColetas = coletaPrecoService.findByIdCotacao(coletaPrecoDto.getIdCotacao());
        if (colColetas != null && !colColetas.isEmpty()) {
            tblColetasPreco.addRowsByCollection(colColetas, 
                                                new String[] {"","idColetaPreco","descricaoItem","nomeFornecedor"}, 
                                                null, 
                                                null,
                                                new String[] {"gerarImgAlteracaoColeta"}, 
                                                "exibirColetaPrecos", 
                                                null);
        }    
    }    
    
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) document.getBean();
        request.getSession(true).setAttribute("colUploadsGED", null);
        document.executeScript("uploadAnexos.refresh()");
        
        HTMLForm form = document.getForm("form");
        form.clear();
        if (coletaPrecoDto.getIdColetaPreco() != null) {
            Integer idCotacao = coletaPrecoDto.getIdCotacao();
            ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, WebUtil.getUsuarioSistema(request));
            coletaPrecoDto = (ColetaPrecoDTO) coletaPrecoService.restore(coletaPrecoDto);
            coletaPrecoDto.setIdCotacao(idCotacao);
            
            exibeCriteriosVariaveis(document, request, coletaPrecoDto);
            
            restaurarAnexos(request, coletaPrecoDto.getIdColetaPreco());
        }
        configuraBotoes(document, request, coletaPrecoDto);
        form.setValues(coletaPrecoDto);
    }
    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) document.getBean();
        Collection<UploadDTO> anexos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
        coletaPrecoDto.setAnexos(anexos);
        
        ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, WebUtil.getUsuarioSistema(request));
        if (coletaPrecoDto.getValorAcrescimo() == null)
            coletaPrecoDto.setValorAcrescimo(new Double(0));
        if (coletaPrecoDto.getIdColetaPreco() != null) {
            coletaPrecoService.update(coletaPrecoDto);
        }else{
            coletaPrecoDto.setIdResponsavel(usuario.getIdEmpregado());
            coletaPrecoService.create(coletaPrecoDto);
        }
        exibeColetasPreco(document,request,coletaPrecoDto);
        document.alert(UtilI18N.internacionaliza(request, "coletaPreco.mensagemAtualizacao"));
        document.executeScript("limpar();");
    }
    
    public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        ColetaPrecoDTO coletaPrecoDto = (ColetaPrecoDTO) document.getBean();
        
        if (coletaPrecoDto.getIdColetaPreco() != null) {
            ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, WebUtil.getUsuarioSistema(request));
            coletaPrecoService.delete(coletaPrecoDto);
            exibeColetasPreco(document,request,coletaPrecoDto);
            document.alert(UtilI18N.internacionaliza(request, "coletaPreco.mensagemExclusao"));
            document.executeScript("limpar();");
        }
    }    
    protected void restaurarAnexos(HttpServletRequest request, Integer idColetaPreco) throws ServiceException, Exception {
        ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_COLETAPRECOS, idColetaPreco);
        Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

        request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);
    }

}
