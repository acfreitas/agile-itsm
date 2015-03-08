package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ColetaPrecoService;
import br.com.centralit.citcorpore.negocio.CotacaoItemRequisicaoService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.negocio.RequisicaoProdutoService;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AprovacaoCotacao extends RequisicaoProduto {

    @Override
    public Class getBeanClass() {
        return RequisicaoProdutoDTO.class;
    }

    @Override
    protected String getAcao() {
        return RequisicaoProdutoDTO.ACAO_APROVACAO;
    }

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JustificativaParecerService justificativaService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, WebUtil.getUsuarioSistema(request));

        HTMLSelect idJustificativa = (HTMLSelect) document.getSelectById("item#idJustificativa");
        idJustificativa.removeAllOptions();
        idJustificativa.addOption("", "---");

        HTMLSelect idJustificativaPopup = (HTMLSelect) document.getSelectById("idJustificativaPopup");
        idJustificativaPopup.removeAllOptions();
        idJustificativaPopup.addOption("", "---");

        Collection colJustificativas = justificativaService.listAplicaveisRequisicao();
        if(colJustificativas != null && !colJustificativas.isEmpty()) {
            idJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
            idJustificativaPopup.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
        }

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

        CotacaoItemRequisicaoService cotacaoItemRequisicaoService = (CotacaoItemRequisicaoService) ServiceLocator.getInstance().getService(CotacaoItemRequisicaoService.class, WebUtil.getUsuarioSistema(request));
        Collection<CotacaoItemRequisicaoDTO> itensRequisicao = cotacaoItemRequisicaoService.findByIdItemTrabalho(requisicaoProdutoDto.getIdTarefa());
        if (itensRequisicao != null) {
            tblItensRequisicao.addRowsByCollection(itensRequisicao,
                                                new String[] {"","","","descricaoItem","quantidade","valorTotal","","descrSituacao"},
                                                null,
                                                "",
                                                new String[] {"gerarImg"},
                                                null,
                                                null);
        }
        document.executeScript("totalizarItens();");
        requisicaoProdutoDto.setAcao(getAcao());
        HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(requisicaoProdutoDto);
    }

    public void exibeResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        document.getElementById("divResultado").setInnerHTML("");

        RequisicaoProdutoDTO requisicaoProdutoBean = (RequisicaoProdutoDTO) document.getBean();
        if (requisicaoProdutoBean.getIdItemCotacao() == null)
            return;

        ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, null);
        Collection<ColetaPrecoDTO> colResultado = coletaPrecoService.findResultadoByIdItemCotacao(requisicaoProdutoBean.getIdItemCotacao());
        if (colResultado == null)
            return;

        String str =
               "<table class=\"table\" width=\"100%\">";
        str += "    <tr>";
        str += "        <th width=\"12%\">"+UtilI18N.internacionaliza(request, "coletaPreco.pontuacao")+"</th>";
        str += "        <th width=\"3%\">"+UtilI18N.internacionaliza(request, "cotacao")+"</th>";
        str += "        <th width=\"5%\">"+UtilI18N.internacionaliza(request, "coletaPreco.quantidade")+"</th>";
        str += "        <th width=\"15%\">"+UtilI18N.internacionaliza(request, "coletaPreco.preco")+"</th>";
        str += "        <th >"+UtilI18N.internacionaliza(request, "coletaPreco.fornecedor")+"</th>";
        str += "    </tr>";

        for (ColetaPrecoDTO coletaPrecoDto : colResultado) {
            String corLetra = "black";
            String corLinha = "#ADF7DA";
            String imagem = "trophy.png";
            if (coletaPrecoDto.getResultadoFinal() == null) {
                corLinha = "";
                imagem = "";
            }else if (coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_DESCLASSIFICADA)) {
                corLinha = "";
                corLetra = "red";
                imagem = "thumb-down.png";
            }else if (coletaPrecoDto.getResultadoFinal().equals(ColetaPrecoDTO.RESULT_EMPATE)) {
                imagem = "interrog.png";
            }
            str += "<tr style=\"background-color:"+corLinha+"\" >";
            if (coletaPrecoDto.getPontuacao() != null) {
                str += "<td style=\"text-align:right;color:"+corLetra+"\"\" >"+UtilFormatacao.formatDecimal(coletaPrecoDto.getPontuacao(),"##.####")+"&nbsp;";
                str += "    <img style=\"cursor: pointer;\" src=\""+Constantes.getValue("CONTEXTO_APLICACAO")+"/imagens/"+imagem+"\" title=\""+coletaPrecoDto.getResultadoFinalStr()+"\" ></td>";
            }else{
                str += "<td>&nbsp;</td>";
            }
            str += "    <td style=\"text-align:right;color:"+corLetra+"\" >"+coletaPrecoDto.getIdCotacao()+"</td>";
            if (coletaPrecoDto.getQuantidadeCompra() != null && coletaPrecoDto.getQuantidadeCompra().doubleValue() > 0) {
                str += "    <td style=\"text-align:right;color:"+corLetra+"\"\" >"+coletaPrecoDto.getQuantidadeCompra()+"</td>";
                str += "    <td style=\"text-align:right;color:"+corLetra+"\"\" >"+UtilFormatacao.formatDouble(coletaPrecoDto.getValorLiquido()/coletaPrecoDto.getQuantidadeCotada()*coletaPrecoDto.getQuantidadeCompra(),2)+"</td>";
            }else{
                str += "    <td style=\"text-align:right;color:"+corLetra+"\"\" >"+coletaPrecoDto.getQuantidadeCotada()+"</td>";
                str += "    <td style=\"text-align:right;color:"+corLetra+"\"\" >"+UtilFormatacao.formatDouble(coletaPrecoDto.getValorLiquido(),2)+"</td>";
            }
            str += "    <td style=\"color:"+corLetra+"\">"+coletaPrecoDto.getNomeFornecedor()+"</td>";
            str += "</tr>";
        }
        str += "</table>";

        document.getElementById("divResultado").setInnerHTML(str);
    }

}
