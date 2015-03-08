package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.CotacaoItemRequisicaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ColetaPrecoService;
import br.com.centralit.citcorpore.negocio.CotacaoItemRequisicaoService;
import br.com.centralit.citcorpore.negocio.CotacaoService;
import br.com.centralit.citcorpore.negocio.PedidoCompraService;
import br.com.centralit.citcorpore.negocio.RequisicaoProdutoService;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacaoItemRequisicao;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;

public class ConsultaAprovacaoCotacao extends AjaxFormAction {
    
    @SuppressWarnings("rawtypes")
    public Class getBeanClass() {
        return CotacaoDTO.class;
    }

    private void configuraBotoes(DocumentHTML document, HttpServletRequest request, CotacaoDTO cotacaoDto) throws Exception {
        if(cotacaoDto.getIdCotacao() != null) {
	        cotacaoDto.setIdCotacao(cotacaoDto.getIdCotacao());
	        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
	        cotacaoDto = (CotacaoDTO) cotacaoService.restore(cotacaoDto);
	        if (cotacaoDto == null)
	            return;
	        
	        document.executeScript("document.getElementById('btnReabrirColeta').style.display = 'none'");
	        SituacaoCotacao situacao = SituacaoCotacao.valueOf(cotacaoDto.getSituacao());
	        if (situacao.equals(SituacaoCotacao.Publicada)) {
	            document.executeScript("document.getElementById('btnReabrirColeta').style.display = 'block'");
	        }
        }
        document.executeScript("JANELA_AGUARDE_MENU.hide();");
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
            
            CotacaoDTO cotacaoDto = (CotacaoDTO) document.getBean();
            if (cotacaoDto.getIdCotacao() == null)
                return;
            
            request.setAttribute("idCotacao", ""+cotacaoDto.getIdCotacao());  
            
            CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
            cotacaoDto = (CotacaoDTO) cotacaoService.restore(cotacaoDto);
            if (cotacaoDto == null)
                return;
            
            if (cotacaoDto.getSituacao().equals(SituacaoCotacao.Publicada.name())) 
                document.executeScript("document.getElementById('divReabrirColeta').style.display = 'block'");
            
            ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, null);
            Collection<ColetaPrecoDTO> colColetas = coletaPrecoService.findHabilitadasByIdCotacao(cotacaoDto.getIdCotacao());
            if (colColetas == null)
                return;
            
            CotacaoItemRequisicaoService cotacaoItemService = (CotacaoItemRequisicaoService) ServiceLocator.getInstance().getService(CotacaoItemRequisicaoService.class, null);

            String str = 
                   "<table class=\"table\" width=\"100%\">";
            str += "    <tr>";
            str += "        <th style=\"font-size:14px;background-color:#BFC6C3\" colspan=\"4\">"+UtilI18N.internacionaliza(request, "coletaPreco") + "</th>";
            str += "        <th style=\"font-size:14px;background-color:#BFC6C3\" colspan=\"5\">"+UtilI18N.internacionaliza(request, "requisicaoProduto.requisicao") + "</th>";
            str += "    </tr>";
            str += "    <tr>";
            str += "        <th width=\"5%\">"+UtilI18N.internacionaliza(request, "coletaPreco.numero") + "</th>";
            str += "        <th width=\"5%\">"+UtilI18N.internacionaliza(request, "coletaPreco.pontuacao") + "</th>";
            str += "        <th width=\"20%\">"+UtilI18N.internacionaliza(request, "fornecedor") + "</th>";
            str += "        <th >"+UtilI18N.internacionaliza(request, "coletaPreco.item") + "</th>";
            str += "        <th width=\"5%\">"+UtilI18N.internacionaliza(request, "coletaPreco.numero") + "</th>";
            str += "        <th width=\"5%\">"+UtilI18N.internacionaliza(request, "itemRequisicaoProduto.quantidade") + "</th>";
            str += "        <th width=\"8%\">"+UtilI18N.internacionaliza(request, "itemRequisicaoProduto.percVariacaoPreco") + "</th>";
            str += "        <th width=\"8%\">"+UtilI18N.internacionaliza(request, "citcorpore.comum.aprovador") + "</th>";
            str += "        <th width=\"15%\">"+UtilI18N.internacionaliza(request, "citcorpore.comum.situacao") + "</th>";
            str += "    </tr>";

            for (ColetaPrecoDTO coletaPrecoDto : colColetas) {
                Collection<CotacaoItemRequisicaoDTO> colItens = cotacaoItemService.findByIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
                int rowSpan = 1;
                if (colItens != null && colItens.size() > 0)
                    rowSpan = colItens.size();
                str += "<tr onMouseOver='TrowOn(this,\"#FFCC99\");' onMouseOut='TrowOff(this)'>";
                str += "    <td rowspan=\""+rowSpan+"\" style=\"text-align:right;\" >"+coletaPrecoDto.getIdColetaPreco()+"</td>";
                str += "    <td rowspan=\""+rowSpan+"\" >"+UtilFormatacao.formatDouble(coletaPrecoDto.getPontuacao(),2)+"</td>";
                str += "    <td rowspan=\""+rowSpan+"\" >"+coletaPrecoDto.getNomeFornecedor()+"</td>";
                str += "    <td rowspan=\""+rowSpan+"\" >"+coletaPrecoDto.getDescricaoItem()+"</td>";
                if (colItens != null && colItens.size() > 0) {
                    int i = 0;
                    HashMap<String, StringBuilder> mapAprovadores = new HashMap();
                    RequisicaoProdutoService requisicaoProdutoService = (RequisicaoProdutoService) ServiceLocator.getInstance().getService(RequisicaoProdutoService.class, null);
                    for (CotacaoItemRequisicaoDTO cotacaoItemDto : colItens) {
                        StringBuilder aprovadores = mapAprovadores.get(""+cotacaoItemDto.getIdSolicitacaoServico());
                        if (aprovadores == null) {
                            aprovadores = requisicaoProdutoService.recuperaLoginAutorizadores(cotacaoItemDto.getIdSolicitacaoServico());
                            mapAprovadores.put(""+cotacaoItemDto.getIdSolicitacaoServico(), aprovadores);
                        }
                        if (i > 0)
                            str += "<tr>";
                        String cor = "";
                        if (cotacaoItemDto.getSituacao().equals(SituacaoCotacaoItemRequisicao.NaoAprovado.name()))
                            cor = "red";
                        str += "    <td style=\"text-align:right;color:"+cor+"\" >"+cotacaoItemDto.getIdSolicitacaoServico()+"</td>";
                        str += "    <td style=\"text-align:right;color:"+cor+"\" >"+cotacaoItemDto.getQuantidade()+"</td>";
                        str += "    <td style=\"text-align:right;color:"+cor+"\" >"+UtilFormatacao.formatDouble(cotacaoItemDto.getPercVariacaoPreco(),2)+"</td>";
                        str += "    <td style=\"color:"+cor+"\">"+aprovadores.toString()+"</td>";
                        str += "    <td style=\"color:"+cor+"\">"+cotacaoItemDto.getDescrSituacao()+"</td>";
                        str += "</tr>";
                        i++;
                    }
                }else{
                    str += "    <td>&nbsp;</td>";
                    str += "    <td>&nbsp;</td>";
                    str += "    <td>&nbsp;</td>";
                    str += "    <td>&nbsp;</td>";
                    str += "    <td>&nbsp;</td>";
                    str += "</tr>";
                }
            }
            str += "</table>";
            
            document.getElementById("divAprovacoes").setInnerHTML(str);
            
            PedidoCompraService pedidoCompraService = (PedidoCompraService) ServiceLocator.getInstance().getService(PedidoCompraService.class, null);
            Collection colPedidos = pedidoCompraService.findEntreguesByIdCotacao(cotacaoDto.getIdCotacao());
            if (colPedidos != null && colPedidos.size() > 0)
                document.executeScript("document.getElementById('divReabrirColeta').style.display = 'none'");
            
            configuraBotoes(document, request, cotacaoDto);
        }finally{
            document.executeScript("parent.escondeJanelaAguarde()");
        }
    }
    
    public void reabreColetaPrecos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        CotacaoDTO cotacaoDto = (CotacaoDTO) document.getBean();
        cotacaoDto.setUsuarioDto(usuario);
        
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoService.reabreColetaPrecos(cotacaoDto);
        document.executeScript("parent.atualiza();");
        document.executeScript("parent.exibeAprovacao()");
        document.alert(UtilI18N.internacionaliza(request, "cotacao.confirmacaoReaberturaColeta"));
     }
    
    
}
