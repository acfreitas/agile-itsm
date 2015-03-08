package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.centralit.citcorpore.bean.CotacaoDTO;
import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ColetaPrecoService;
import br.com.centralit.citcorpore.negocio.CotacaoService;
import br.com.centralit.citcorpore.negocio.ItemCotacaoService;
import br.com.centralit.citcorpore.negocio.JustificativaParecerService;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;

public class ResultadoCotacao extends AjaxFormAction {

    @SuppressWarnings("rawtypes")
    public Class getBeanClass() {
        return ColetaPrecoDTO.class;
    }
    
    private void configuraBotoes(DocumentHTML document, HttpServletRequest request, ColetaPrecoDTO coletaPrecoBean) throws Exception {
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        if(coletaPrecoBean.getIdCotacao() != null) {
	        cotacaoDto.setIdCotacao(coletaPrecoBean.getIdCotacao());
	        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
	        cotacaoDto = (CotacaoDTO) cotacaoService.restore(cotacaoDto);
	        if (cotacaoDto == null)
	            return;
	        
	        document.executeScript("document.getElementById('btnCalcularResultado').style.display = 'none'");
	        document.executeScript("document.getElementById('btnPublicarResultado').style.display = 'none'");
	        document.executeScript("document.getElementById('btnReabrirColeta').style.display = 'none'");
	
	        SituacaoCotacao situacao = SituacaoCotacao.valueOf(cotacaoDto.getSituacao());
	        if (situacao.equals(SituacaoCotacao.EmAndamento)) {
	            document.executeScript("document.getElementById('btnCalcularResultado').style.display = 'block'");
	            document.executeScript("document.getElementById('btnPublicarResultado').style.display = 'none'");
	        }
	        if (situacao.equals(SituacaoCotacao.Calculada)) {
	            document.executeScript("document.getElementById('btnCalcularResultado').style.display = 'none'");
	            document.executeScript("document.getElementById('btnPublicarResultado').style.display = 'block'");
	            document.executeScript("document.getElementById('btnReabrirColeta').style.display = 'block'");
	        }
	        if (situacao.equals(SituacaoCotacao.Publicada)) {
	            document.executeScript("document.getElementById('btnCalcularResultado').style.display = 'none'");
	            document.executeScript("document.getElementById('btnPublicarResultado').style.display = 'none'");
	        }
        }
        document.executeScript("JANELA_AGUARDE_MENU.hide();");
    }
    
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
            JustificativaParecerService justificativaService = (JustificativaParecerService) ServiceLocator.getInstance().getService(JustificativaParecerService.class, WebUtil.getUsuarioSistema(request));
            HTMLSelect idJustificativa = (HTMLSelect) document.getSelectById("idJustifResultado");
            idJustificativa.removeAllOptions();
            idJustificativa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            Collection colJustificativas = justificativaService.listAplicaveisCotacao();
            if(colJustificativas != null && !colJustificativas.isEmpty())
                idJustificativa.addOptions(colJustificativas, "idJustificativa", "descricaoJustificativa", null);
            
            ColetaPrecoDTO coletaPrecoBean = (ColetaPrecoDTO) document.getBean();
            request.setAttribute("idCotacao", ""+coletaPrecoBean.getIdCotacao());       
            exibeItensCotacao(document, request, coletaPrecoBean);
        }finally{
            document.executeScript("parent.escondeJanelaAguarde()");
        }
    }

    private void exibeItensCotacao(DocumentHTML document, HttpServletRequest request, ColetaPrecoDTO coletaPrecoBean) throws Exception {
        HTMLTable tblItensCotacao = document.getTableById("tblItensCotacao");
        tblItensCotacao.deleteAllRows();
        
        document.getElementById("divResultado").setInnerHTML("");

        ItemCotacaoService itemCotacaoService = (ItemCotacaoService) ServiceLocator.getInstance().getService(ItemCotacaoService.class, null);
        Collection<ItemCotacaoDTO> colItens = itemCotacaoService.findByIdCotacao(coletaPrecoBean.getIdCotacao());
        if (colItens != null && !colItens.isEmpty()) {
            tblItensCotacao.addRowsByCollection(colItens, 
                                                new String[] {"","codigoProduto","descricaoItem","marcaPreferencial","quantidade"}, 
                                                null, 
                                                "", 
                                                new String[] {"gerarImgResultados"}, 
                                                "exibirResultado", 
                                                null); 
        }  
        configuraBotoes(document, request, coletaPrecoBean);
    }
    
    public void exibeResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        document.getElementById("divResultado").setInnerHTML("");

        ColetaPrecoDTO coletaPrecoBean = (ColetaPrecoDTO) document.getBean();
        if (coletaPrecoBean.getIdItemCotacao() == null)
            return;
        
        ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, null);
        Collection<ColetaPrecoDTO> colResultado = coletaPrecoService.findResultadoByIdItemCotacao(coletaPrecoBean.getIdItemCotacao());
        if (colResultado == null)
            return;
        
        String str = 
               "<table class=\"table\" width=\"100%\">";
        str += "    <tr>";
        str += "        <th width=\"16px\"></th>";
        str += "        <th width=\"12%\">"+UtilI18N.internacionaliza(request, "coletaPreco.pontuacao")+"</th>";
        str += "        <th width=\"3%\">"+UtilI18N.internacionaliza(request, "coletaPreco.coleta")+"</th>";
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
                str += "<td><img style=\"cursor: pointer;\" src=\""+Constantes.getValue("CONTEXTO_APLICACAO")+"/imagens/hammer.png\" title=\"Definir resultado\" onclick=\"exibirDefinicaoResultado("+coletaPrecoDto.getIdColetaPreco()+")\" ></td>";
                str += "<td style=\"text-align:right;color:"+corLetra+"\"\" >"+UtilFormatacao.formatDecimal(coletaPrecoDto.getPontuacao(),"##.####")+"&nbsp;";
                str += "    <img style=\"cursor: pointer;\" src=\""+Constantes.getValue("CONTEXTO_APLICACAO")+"/imagens/"+imagem+"\" title=\""+coletaPrecoDto.getResultadoFinalStr()+"\" ></td>";
            }else{
                str += "<td>&nbsp;</td>";
                str += "<td>&nbsp;</td>";
            }
            str += "    <td style=\"text-align:right;color:"+corLetra+"\" >"+coletaPrecoDto.getIdColetaPreco()+"</td>";
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

    public void calculaResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        ColetaPrecoDTO coletaPrecoBean = (ColetaPrecoDTO) document.getBean();
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(coletaPrecoBean.getIdCotacao());
        cotacaoDto.setUsuarioDto(usuario);
        
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoService.calculaResultado(cotacaoDto);
        exibeItensCotacao(document,request,coletaPrecoBean);
        String idItem = "";
        if (coletaPrecoBean.getIdItemCotacao() != null)
            idItem = ""+coletaPrecoBean.getIdItemCotacao();
        document.executeScript("exibirResultadoItem('"+idItem+"');");
        document.executeScript("parent.atualiza();");
    }
 
    public void publicaResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        ColetaPrecoDTO coletaPrecoBean = (ColetaPrecoDTO) document.getBean();
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(coletaPrecoBean.getIdCotacao());
        cotacaoDto.setUsuarioDto(usuario);
        
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoService.publicaResultado(cotacaoDto);
        configuraBotoes(document, request, coletaPrecoBean);
        document.alert(UtilI18N.internacionaliza(request, "cotacao.confirmacaoPublicacao"));
        document.executeScript("parent.atualiza();");
    }
    
    public void exibeDefinicaoResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        ColetaPrecoDTO coletaPrecoBean = (ColetaPrecoDTO) document.getBean();
        if (coletaPrecoBean.getIdColetaPreco() == null) 
            return;

        HTMLForm form = document.getForm("formResultado");
        form.clear();
        ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, WebUtil.getUsuarioSistema(request));
        ColetaPrecoDTO coletaPrecoDto = new ColetaPrecoDTO();
        coletaPrecoDto.setIdColetaPreco(coletaPrecoBean.getIdColetaPreco());
        coletaPrecoDto = (ColetaPrecoDTO) coletaPrecoService.restore(coletaPrecoDto);
        form.setValues(coletaPrecoDto);
        
        configuraTelaResultado(document,request,coletaPrecoDto);
        document.executeScript("$(\"#POPUP_RESULTADO\").dialog(\"open\");");
    }
    
    public void configuraTelaResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        ColetaPrecoDTO coletaPrecoBean = (ColetaPrecoDTO) document.getBean();
        if (coletaPrecoBean.getIdColetaPreco() == null) 
            return;

        configuraTelaResultado(document,request,coletaPrecoBean);
    }
    
    private void configuraTelaResultado(DocumentHTML document, HttpServletRequest request, ColetaPrecoDTO coletaPrecoBean) throws Exception {
        document.executeScript("document.getElementById('divJustificativa').style.display = 'none';");
        
        boolean bExibeJustificativa = !coletaPrecoBean.getResultadoCalculo().equalsIgnoreCase(coletaPrecoBean.getResultadoFinal());
        if (!bExibeJustificativa)
            bExibeJustificativa = coletaPrecoBean.getQuantidadeCompra().doubleValue() != coletaPrecoBean.getQuantidadeCalculo().doubleValue();
        
        if (bExibeJustificativa)
            document.executeScript("document.getElementById('divJustificativa').style.display = 'block';");
    }
    
    public void defineResultado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        ColetaPrecoDTO coletaPrecoBean = (ColetaPrecoDTO) document.getBean();
        if (coletaPrecoBean.getIdColetaPreco() == null) 
            return;

        coletaPrecoBean.setIdRespResultado(usuario.getIdEmpregado());
        ColetaPrecoService coletaPrecoService = (ColetaPrecoService) ServiceLocator.getInstance().getService(ColetaPrecoService.class, WebUtil.getUsuarioSistema(request));
        coletaPrecoService.defineResultado(coletaPrecoBean);
        document.executeScript("$(\"#POPUP_RESULTADO\").dialog(\"close\");");
        exibeResultado(document,request,response);
        exibeItensCotacao(document, request, coletaPrecoBean);
        String idItem = "";
        if (coletaPrecoBean.getIdItemCotacao() != null)
            idItem = ""+coletaPrecoBean.getIdItemCotacao();
        document.executeScript("exibirResultadoItem('"+idItem+"');");
    }
    
    public void reabreColetaPrecos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        ColetaPrecoDTO coletaPrecoBean = (ColetaPrecoDTO) document.getBean();
        CotacaoDTO cotacaoDto = new CotacaoDTO();
        cotacaoDto.setIdCotacao(coletaPrecoBean.getIdCotacao());
        cotacaoDto.setUsuarioDto(usuario);
        
        CotacaoService cotacaoService = (CotacaoService) ServiceLocator.getInstance().getService(CotacaoService.class, null);
        cotacaoService.reabreColetaPrecos(cotacaoDto);
        document.executeScript("parent.atualiza();");
        document.executeScript("parent.exibeResultadoCotacao()");
        document.alert(UtilI18N.internacionaliza(request, "cotacao.confirmacaoReaberturaColeta"));
    }

}
