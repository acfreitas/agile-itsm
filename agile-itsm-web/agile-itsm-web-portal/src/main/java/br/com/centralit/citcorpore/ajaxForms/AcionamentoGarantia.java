package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.EntregaItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.ParecerDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EntregaItemRequisicaoService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.ParecerService;
import br.com.centralit.citcorpore.negocio.RequisicaoProdutoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes" })
public class AcionamentoGarantia extends RequisicaoProduto {

    @Override
    public Class getBeanClass() {
        return RequisicaoProdutoDTO.class;
    }

    @Override
    protected String getAcao() {
        return RequisicaoProdutoDTO.ACAO_GARANTIA;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        Collection<EntregaItemRequisicaoDTO> itensRequisicao = entregaItemRequisicaoService.findNaoAprovadasByIdSolicitacaoServico(requisicaoProdutoDto.getIdSolicitacaoServico());
        if (itensRequisicao != null) {
            ParecerService parecerService = (ParecerService) ServiceLocator.getInstance().getService(ParecerService.class, WebUtil.getUsuarioSistema(request));
            for (EntregaItemRequisicaoDTO entregaItemRequisicaoDto : itensRequisicao) {
                if (entregaItemRequisicaoDto.getIdParecer() == null)
                    continue;
                ParecerDTO parecerDto = new ParecerDTO();
                parecerDto.setIdParecer(entregaItemRequisicaoDto.getIdParecer());
                parecerDto = (ParecerDTO) parecerService.restore(parecerDto);
                if (parecerDto != null) {
                    entregaItemRequisicaoDto.setIdJustificativa(parecerDto.getIdJustificativa());
                    entregaItemRequisicaoDto.setComplementoJustificativa(parecerDto.getComplementoJustificativa());
                }
            }
            tblItensRequisicao.addRowsByCollection(itensRequisicao,
                                                new String[] {"","descricaoItem","nomeFornecedor","numeroPedido","quantidadeEntregue","dataEntrega","descrSituacao"},
                                                null,
                                                "",
                                                new String[] {"gerarImg"},
                                                "editarItem",
                                                null);
        }

        requisicaoProdutoDto.setAcao(getAcao());
        HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(requisicaoProdutoDto);
    }


}
