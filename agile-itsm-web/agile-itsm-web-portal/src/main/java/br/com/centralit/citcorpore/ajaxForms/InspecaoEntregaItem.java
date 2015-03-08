package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO;
import br.com.centralit.citcorpore.bean.EntregaItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.InspecaoEntregaItemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CriterioAvaliacaoService;
import br.com.centralit.citcorpore.negocio.EntregaItemRequisicaoService;
import br.com.centralit.citcorpore.negocio.InspecaoEntregaItemService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.RequisicaoProdutoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class InspecaoEntregaItem extends RequisicaoProduto {

    @Override
    public Class getBeanClass() {
        return RequisicaoProdutoDTO.class;
    }

    @Override
    protected String getAcao() {
        return RequisicaoProdutoDTO.ACAO_INSPECAO;
    }
    
    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CriterioAvaliacaoService criterioAvaliacaoService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, WebUtil.getUsuarioSistema(request));
        Collection<CriterioAvaliacaoDTO> colCriterios = criterioAvaliacaoService.findByAplicavelAvaliacaoSolicitante();
        request.setAttribute("colCriterios", colCriterios);

        JustificativaParecerService justificativaService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, WebUtil.getUsuarioSistema(request));
        HTMLSelect idJustificativa = (HTMLSelect) document.getSelectById("item#idJustificativa");
        idJustificativa.removeAllOptions();
        idJustificativa.addOption("", "---");
        Collection colJustificativas = justificativaService.listAplicaveisInspecao();
        if(colJustificativas != null && !colJustificativas.isEmpty())
            idJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
        
        super.load(document, request, response);
    }
    
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) document.getBean();
        String editar = requisicaoProdutoDto.getEditar();
        Integer idTarefa = requisicaoProdutoDto.getIdTarefa();
        RequisicaoProdutoService requisicaoProdutoService = (RequisicaoProdutoService) ServiceLocator.getInstance().getService(RequisicaoProdutoService.class, WebUtil.getUsuarioSistema(request));

        requisicaoProdutoDto = (RequisicaoProdutoDTO) requisicaoProdutoService.restore(requisicaoProdutoDto);
        if (requisicaoProdutoDto == null) 
            return;

        requisicaoProdutoDto.setRejeitada("N");

        requisicaoProdutoDto.setIdTarefa(idTarefa);
        requisicaoProdutoDto.setEditar(editar);
        if (requisicaoProdutoDto.getEditar() == null)
            requisicaoProdutoDto.setEditar("S");
        
        HTMLTable tblItensRequisicao = document.getTableById("tblItensRequisicao");
        tblItensRequisicao.deleteAllRows();
        
        EntregaItemRequisicaoService entregaItemRequisicaoService = (EntregaItemRequisicaoService) ServiceLocator.getInstance().getService(EntregaItemRequisicaoService.class, WebUtil.getUsuarioSistema(request));
        Collection<EntregaItemRequisicaoDTO> itensRequisicao = entregaItemRequisicaoService.findByIdItemTrabalho(requisicaoProdutoDto.getIdTarefa());
        if (itensRequisicao != null) {
            tblItensRequisicao.addRowsByCollection(itensRequisicao, 
                                                new String[] {"","","quantidadeEntregue","descrSituacao"}, 
                                                null, 
                                                "", 
                                                new String[] {"gerarImg"}, 
                                                null, 
                                                null);  
        }
        
        requisicaoProdutoDto.setAcao(getAcao());
        HTMLForm form = document.getForm("form");
        form.clear();   
        form.setValues(requisicaoProdutoDto);
    }
    
    public void exibeAvaliacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) document.getBean();
        
        if (requisicaoProdutoDto.getIdEntrega() == null)
            return;
        
        HTMLForm form = document.getForm("form");
        InspecaoEntregaItemService inspecaoEntregaService = (InspecaoEntregaItemService) ServiceLocator.getInstance().getService(InspecaoEntregaItemService.class, null);
        Collection<InspecaoEntregaItemDTO> colInspecao = inspecaoEntregaService.findByIdEntrega(requisicaoProdutoDto.getIdEntrega());
        document.executeScript("GRID_CRITERIOS.deleteAllRows();");
        if (colInspecao != null) {
            CriterioAvaliacaoService criterioAvaliacaoService = (CriterioAvaliacaoService) ServiceLocator.getInstance().getService(CriterioAvaliacaoService.class, null);
            int i = 0;
            for (InspecaoEntregaItemDTO inspecaoDto : colInspecao) {
                CriterioAvaliacaoDTO criterioDto = new CriterioAvaliacaoDTO();
                criterioDto.setIdCriterio(inspecaoDto.getIdCriterio());
                criterioDto = (CriterioAvaliacaoDTO) criterioAvaliacaoService.restore(criterioDto);
                if (criterioDto == null)
                    continue;
                
                i++;
                document.executeScript("GRID_CRITERIOS.addRow()");
                inspecaoDto.setTipoAvaliacao(criterioDto.getTipoAvaliacao());
                inspecaoDto.setSequencia(i);
                document.executeScript("seqCriterio = NumberUtil.zerosAEsquerda("+i+",5)");
                document.executeScript("exibeCriterio('" + br.com.citframework.util.WebUtil.serializeObject(inspecaoDto, WebUtil.getLanguage(request)) + "')");
            }    
        }
        
        document.executeScript("$('#POPUP_ITEM_REQUISICAO').dialog('open')");
    }
    
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        
        RequisicaoProdutoDTO requisicaoProdutoDto = (RequisicaoProdutoDTO) document.getBean();
        
        if (requisicaoProdutoDto.getIdEntrega() == null)
            return;

        EntregaItemRequisicaoDTO entregaItemDto = new EntregaItemRequisicaoDTO();
        entregaItemDto.setIdEntrega(requisicaoProdutoDto.getIdEntrega());
        entregaItemDto.setUsuarioDto(usuario);
        
        Collection<InspecaoEntregaItemDTO> colInspecao = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(InspecaoEntregaItemDTO.class, "colCriterios_Serialize", request);
        entregaItemDto.setColInspecao(colInspecao);
        
        EntregaItemRequisicaoService entregaService = (EntregaItemRequisicaoService) ServiceLocator.getInstance().getService(EntregaItemRequisicaoService.class, null);
        entregaService.atualizaInspecao(entregaItemDto);
        document.executeScript("$('#POPUP_ITEM_REQUISICAO').dialog('close')");
    }

}
