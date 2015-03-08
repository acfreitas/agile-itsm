package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsAbstractExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.jsoup.Jsoup;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FaseServicoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.PesquisaSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FaseServicoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.PermissoesFluxoService;
import br.com.centralit.citcorpore.negocio.PrioridadeService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Arvore;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

@SuppressWarnings({"rawtypes", "unchecked"})
public class PesquisaSolicitacoesServicos extends AjaxFormAction {

    private UsuarioDTO usuario;

    @Override
    public Class getBeanClass() {
        return PesquisaSolicitacaoServicoDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        usuario = WebUtil.getUsuario(request);

        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        document.getSelectById("idContrato").removeAllOptions();
        final ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
        final Collection colContrato = contratoService.list();
        document.getSelectById("idContrato").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
        document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);

        document.getSelectById("idPrioridade").removeAllOptions();
        final PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
        final Collection col = prioridadeService.list();
        document.getSelectById("idPrioridade").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
        document.getSelectById("idPrioridade").addOptions(col, "idPrioridade", "nomePrioridade", null);

        document.getSelectById("idGrupoAtual").removeAllOptions();
        final GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
        final Collection colGrupos = grupoSegurancaService.findGruposAtivos();
        document.getSelectById("idGrupoAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
        document.getSelectById("idGrupoAtual").addOptions(colGrupos, "idGrupo", "nome", null);

        document.getSelectById("idFaseAtual").removeAllOptions();
        final FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
        final Collection colFases = faseServicoService.list();
        document.getSelectById("idFaseAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
        document.getSelectById("idFaseAtual").addOptions(colFases, "idFase", "nomeFase", null);

        document.getSelectById("idOrigem").removeAllOptions();
        final OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
        final Collection colOrigem = origemAtendimentoService.list();
        document.getSelectById("idOrigem").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
        document.getSelectById("idOrigem").addOptions(colOrigem, "idOrigem", "descricao", null);

        document.getSelectById("idTipoDemandaServico").removeAllOptions();
        final TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
        final Collection colTiposDemanda = tipoDemandaServicoService.list();
        document.getSelectById("idTipoDemandaServico").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
        document.getSelectById("idTipoDemandaServico").addOptions(colTiposDemanda, "idTipoDemandaServico", "nomeTipoDemandaServico", null);

        document.getElementById("paginaAtual").setInnerHTML("1");
        document.getElementById("paginaTotal").setInnerHTML("0");

        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
        StringBuilder objeto;
        if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")) {
            objeto = new StringBuilder();
            objeto.append("<label>");
            objeto.append(UtilI18N.internacionaliza(request, "unidade.unidade"));
            objeto.append("</label>");
            objeto.append("<input type='text' name='unidadeDes' id='unidadeDes' style='width: 100%;' onkeypress='onkeypressUnidadeDes();' onfocus='montaParametrosAutocompleteUnidade();'>");
            objeto.append("<input type='hidden' name='idUnidade' id='idUnidade' value='0'/>");
            document.getElementById("divUnidade").setInnerHTML(objeto.toString());
            document.executeScript("montaParametrosAutocompleteUnidade()");
        } else {
            objeto = new StringBuilder();
            objeto.append("<label>");
            objeto.append(UtilI18N.internacionaliza(request, "unidade.unidade"));
            objeto.append("</label>");
            objeto.append("<select  class='span12' name='idUnidade' id='idUnidade'></select>");
            document.getElementById("divUnidade").setInnerHTML(objeto.toString());
            this.preencherComboUnidade(document, request, response);
        }

    }

    /**
     * Alterado método de paginação para um mais eficiente.
     * 
     * @since 11/12/2014
     * @author thyen.chang
     */
    public void preencheSolicitacoesRelacionadas(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException,
            Exception {

        String paginacao = "0";
        Integer pagAtual = 0;
        Integer pagAtualAux = 0;

        final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();

        request.getParameter("totalItens");

        if (request.getParameter("paginacao") != null && !request.getParameter("paginacao").equals("")) {
            paginacao = UtilStrings.decodeCaracteresEspeciais(request.getParameter("paginacao"));
        }
        if (request.getParameter("paginacao") == null || request.getParameter("paginacao") == "") {
            paginacao = "0";
        }
        if (request.getParameter("paginacao") == null) {
            paginacao = "0";
        }

        final Integer quantidadePaginator = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "5"));
        if (paginacao.equalsIgnoreCase(quantidadePaginator.toString())) {
            pagAtual = quantidadePaginator;
        } else if (new Integer(paginacao) == 1) {
            pagAtual = pesquisaSolicitacaoServicoDto.getPagAtual() + quantidadePaginator;
            pagAtualAux = pesquisaSolicitacaoServicoDto.getPagAtualAux() + 1;
            if (pagAtual >= pesquisaSolicitacaoServicoDto.getTotalItens()) {
                pagAtual = pesquisaSolicitacaoServicoDto.getPagAtual();
            }
            if (pagAtualAux >= pesquisaSolicitacaoServicoDto.getTotalPagina()) {
                pagAtualAux = pesquisaSolicitacaoServicoDto.getTotalPagina();
            }
        } else if (new Integer(paginacao) < 0) {
            pagAtual = pesquisaSolicitacaoServicoDto.getPagAtual() - quantidadePaginator;
            pagAtualAux = pesquisaSolicitacaoServicoDto.getPagAtualAux() - 1;
            if (pagAtual < 1) {
                pagAtual = 0;
                pagAtualAux = 1;
            }
        } else if (new Integer(paginacao) == 0) {
            pagAtual = 0;
            pagAtualAux = 1;

        }

        else {
            pagAtualAux = pesquisaSolicitacaoServicoDto.getTotalPagina() + 1;
            final Integer modulo = pesquisaSolicitacaoServicoDto.getTotalItens() % quantidadePaginator;
            if (modulo.intValue() == quantidadePaginator.intValue() || modulo.intValue() == 0) {
                pagAtual = new Integer(paginacao) - quantidadePaginator;
            } else {
                pagAtual = new Integer(paginacao) - modulo;
            }
            if (pagAtualAux > pesquisaSolicitacaoServicoDto.getTotalPagina()) {
                pagAtualAux = pesquisaSolicitacaoServicoDto.getTotalPagina();
            }
        }

        usuario = WebUtil.getUsuario(request);

        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        if (pesquisaSolicitacaoServicoDto.getDataInicio() == null) {
            pesquisaSolicitacaoServicoDto.setDataInicio(UtilDatas.strToSQLDate("01/01/1970"));
        }

        if (pesquisaSolicitacaoServicoDto.getDataFim() == null) {
            pesquisaSolicitacaoServicoDto.setDataFim(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
        }

        if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() == null) {
            pesquisaSolicitacaoServicoDto.setDataInicioFechamento(UtilDatas.strToSQLDate("01/01/1970"));
        } else {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        if (pesquisaSolicitacaoServicoDto.getDataFimFechamento() == null) {
            pesquisaSolicitacaoServicoDto.setDataFimFechamento(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
        } else {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

        if (pesquisaSolicitacaoServicoDto.getFlag().equalsIgnoreCase("semPag")) {
            pagAtual = 0;
            pagAtualAux = 1;
        }

        // Passamos o usuário para que o sistema possa obter os IDs das unidades que ele pode acessar!
        pesquisaSolicitacaoServicoDto.setUsuarioLogado(usuario);

        final ArrayList<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService.listRelatorioGetListaPaginada(
                pesquisaSolicitacaoServicoDto, pagAtualAux, quantidadePaginator);

        final StringBuilder script = new StringBuilder();
        if (listaSolicitacaoServicoPorCriterios != null) {
            document.getElementById("tblResumo").setInnerHTML(this.montaHTMLResumoSolicitacoes(listaSolicitacaoServicoPorCriterios, script, request, response));
        } else {
            document.getElementById("tblResumo").setInnerHTML(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.criterioinformado"));
        }

        document.getElementById("paginaAtual").setInnerHTML(pagAtualAux.toString());
        document.getElementById("pagAtual").setValue(pagAtual.toString());
        document.getElementById("pagAtualAux").setValue(pagAtualAux.toString());

        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

    }

    public void setNumeroPaginas(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {

        final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();

        if (pesquisaSolicitacaoServicoDto.getDataInicio() == null) {
            pesquisaSolicitacaoServicoDto.setDataInicio(UtilDatas.strToSQLDate("01/01/1970"));
        }

        if (pesquisaSolicitacaoServicoDto.getDataFim() == null) {
            pesquisaSolicitacaoServicoDto.setDataFim(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
        }

        if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() == null) {
            pesquisaSolicitacaoServicoDto.setDataInicioFechamento(UtilDatas.strToSQLDate("01/01/1970"));
        } else {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        if (pesquisaSolicitacaoServicoDto.getDataFimFechamento() == null) {
            pesquisaSolicitacaoServicoDto.setDataFimFechamento(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
        } else {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);

        Integer noTotalItens, noTotalPaginas, quantidadePorPagina;

        // Passamos o usuário para que o sistema possa obter os IDs das unidades que ele pode acessar!
        pesquisaSolicitacaoServicoDto.setUsuarioLogado(usuario);

        noTotalItens = solicitacaoService.listaRelatorioGetQuantidadeRegistros(pesquisaSolicitacaoServicoDto).intValue();

        quantidadePorPagina = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "5"));

        noTotalPaginas = (int) Math.ceil(noTotalItens.doubleValue() / quantidadePorPagina.doubleValue());

        if (!ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANTIDADE_REGISTROS_PESQUISA_AVANCADA, "-1").equals("-1")
                && noTotalItens > Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANTIDADE_REGISTROS_PESQUISA_AVANCADA, "0"))) {
            document.alert(UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.registroMaiorPermitido"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        } else {
            document.getElementById("paginaAtual").setInnerHTML("0");
            document.getElementById("paginaTotal").setInnerHTML(noTotalPaginas.toString());
            document.getElementById("totalPagina").setValue(noTotalPaginas.toString());
            document.getElementById("totalItens").setValue(noTotalItens.toString());
            document.getElementById("pagAtual").setValue("0");
            document.getElementById("pagAtualAux").setValue("1");

            switch (request.getParameter("origemSolicitacao")) {
            case "pesquisa":
                document.executeScript("executaPesquisa('pesquisa');");
                break;
            case "pdf":
                document.executeScript("executaPesquisa('pdf');");
                break;
            case "xls":
                document.executeScript("executaPesquisa('xls');");
                break;
            case "pdfdetalhado":
                document.executeScript("executaPesquisa('pdfdetalhado');");
                break;
            case "xlsdetalhado":
                document.executeScript("executaPesquisa('xlsdetalhado');");
                break;

            }
        }

    }

    private String montaHTMLResumoSolicitacoes(final ArrayList<SolicitacaoServicoDTO> resumo, final StringBuilder script, final HttpServletRequest request,
            final HttpServletResponse response) throws ServiceException, Exception {
        usuario = WebUtil.getUsuario(request);
        final StringBuilder html = new StringBuilder();
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class,
                WebUtil.getUsuarioSistema(request));
        final PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);
        /* Foi necessário diminuir a fonte e porcetagem da largura da table para adequear ao modal */
        html.append("<table class='dynamicTable table  table-bordered table-condensed dataTable' id='tbRetorno' width='98%' style = 'font-size: 9px' >");
        html.append("<tr>");
        html.append("<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>");
        html.append("<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>");
        html.append("<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>");
        html.append("<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacao") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitante") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.criadopor") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.tipo") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoraabertura") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "gerenciaservico.sla") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.descricao") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solucaoResposta") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "servico.servico") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoralimite") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "unidade.grupo") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoraencerramento") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.temporestante"));
        html.append("<img width='20' height='20'");
        html.append("alt='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativaotemporizador") + "' id='imgAtivaTimer' style='opacity:0.5' ");
        html.append("title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativadestemporizador") + "'");
        html.append("src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/cronometro.png'/>");
        html.append("</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.responsavelatual.desc"));
        html.append("</tr>");
        final HashMap<String, PermissoesFluxoDTO> mapPermissoes = new HashMap();
        for (final SolicitacaoServicoDTO r : resumo) {
            final SolicitacaoServicoDTO solDto = new SolicitacaoServicoDTO();
            solDto.setIdSolicitacaoServico(r.getIdSolicitacaoServico());

            final FluxoDTO fluxoDto = solicitacaoService.recuperaFluxo(solDto);
            if (fluxoDto == null) {
                continue;
            }
            PermissoesFluxoDTO permFluxoDto = mapPermissoes.get("" + fluxoDto.getIdFluxo());
            html.append("<tr>");
            html.append("<hidden id='idSolicitante' value='" + r.getIdSolicitante() + "'/>");
            html.append("<hidden id='idResponsavel' value='" + r.getIdResponsavel() + "'/>");
            html.append("<hidden id='idUsuarioResponsavelAtual' value='" + r.getIdUsuarioResponsavelAtual() + "'/>");
            if (permFluxoDto == null) {
                permFluxoDto = permissoesFluxoService.findByUsuarioAndFluxo(usuario, fluxoDto);
                if (permFluxoDto != null) {
                    mapPermissoes.put("" + fluxoDto.getIdFluxo(), permFluxoDto);
                }
            }

            html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
                    + "/imagens/search.png' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.consultasolicitacaoincidente")
                    + "' onclick='consultarOcorrencias(\"" + r.getIdSolicitacaoServico() + "\")' style='cursor:pointer'/></td>");
            html.append("<td>" + "<a onclick='visualizarSolicitacao(" + r.getIdSolicitacaoServico() + ")' >" + ""
                    + UtilI18N.internacionaliza(request, "citcorpore.comum.visualizar") + "</a>" + "</td>");
            if (permFluxoDto != null && permFluxoDto.getReabrir() != null && permFluxoDto.getReabrir().equalsIgnoreCase("S")) {
                if (r.encerrada()) {
                    html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")
                            + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/reabrir.jpg' border='0' title='"
                            + UtilI18N.internacionaliza(request, "pesquisasolicitacao.reabrirsol") + "' onclick='reabrir(\"" + r.getIdSolicitacaoServico()
                            + "\")' style='cursor:pointer'/></td>");
                } else {
                    html.append("<td>&nbsp;</td>");
                }
            } else {
                html.append("<td>&nbsp;</td>");
            }
            final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
            final Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, r.getIdSolicitacaoServico());

            if (colAnexos != null && !colAnexos.isEmpty()) {
                html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
                        + "/imagens/Paperclip4-black-32.png' width='16' height='16' border='0' title='"
                        + UtilI18N.internacionaliza(request, "pesquisasolicitacao.visualizaranexos") + "' id='btAnexos' onclick='anexos(\"" + r.getIdSolicitacaoServico()
                        + "\")' style='cursor:pointer'/></td>");
            } else {
                html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
                        + "/imagens/file.png' width='16' height='16' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.visualizaranexos")
                        + "' id='btAnexos' onclick='anexos(\"" + r.getIdSolicitacaoServico() + "\")' style='cursor:pointer'/></td>");
            }
            html.append("<td>" + r.getIdSolicitacaoServico() + "</td>");
            html.append("<td>" + r.getNomeSolicitante() + "</td>");
            html.append("<td>" + r.getResponsavel() + "</td>");
            html.append("<td>" + r.getNomeTipoDemandaServico() + "</td>");
            if (r.getSeqReabertura() == null || r.getSeqReabertura().intValue() == 0) {
                html.append("<td id='dataHoraSolicitacao'>"
                        + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraSolicitacao(), WebUtil.getLanguage(request)) + "</td>");
            } else {
                html.append("<td id='dataHoraSolicitacao'>"
                        + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraSolicitacao(), WebUtil.getLanguage(request)) + "<br><br>"
                        + UtilI18N.internacionaliza(request, "solicitacaoServico.seqreabertura") + ": <span style='color:red'><b>" + r.getSeqReabertura() + "</b></span></td>");
            }

            final boolean slaACombinar = (r.getPrazoHH() == null || r.getPrazoHH() == 0) && (r.getPrazoMM() == null || r.getPrazoMM() == 0);

            if (slaACombinar) {
                html.append("<td>" + UtilI18N.internacionaliza(request, "citcorpore.comum.aCombinar") + "</td>");
            } else {
                html.append("<td>" + r.getPrazoHH() + ":" + r.getPrazoMM() + "</td>");
            }

            html.append("<td>" + UtilStrings.nullToVazio(UtilStrings.unescapeJavaString(r.getDescricaoSemFormatacao())) + "</td>");
            html.append("<td>" + UtilStrings.nullToVazio(UtilStrings.unescapeJavaString(r.getResposta())) + "</td>");
            html.append("<td>" + r.getNomeServico().replace(".", ". ") + "</td>");

            /*
             * -- Para internacionalizar corretamente -- A query possui um case when para colocar "Em Andamento" se a
             * situação for "EmAndamento" Essa query é utilizada em outros programas Por esse motivo, o mais simples e
             * menos arriscado é colocar esse teste em vez de mudar a query e arriscar bagunçar em outro lugar Quando a
             * query for melhorada retirando o case when, esse if não será mais utilizado Uelen Paulo - 26/09/2013
             */
            if (r.getSituacao().equalsIgnoreCase("Em Andamento")) {

                r.setSituacao("EmAndamento");
            }

            html.append("<td>" + r.obterSituacaoInternacionalizada(request) + "</td>");

            if (r.getDataHoraLimite() != null && !slaACombinar) {
                html.append("<td>" + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraLimite(), WebUtil.getLanguage(request)) + "</td>");
            } else {
                html.append("<td>&nbsp;</td>");
            }
            html.append("<td>" + UtilStrings.nullToVazio(r.getSiglaGrupo()) + "</td>");
            String d = "";
            if (r.getDataHoraFim() != null) {
                d = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraFim(), WebUtil.getLanguage(request));
            }
            html.append("<td id='dataHoraFimSolicitacao'>" + d + "</td>");
            if (r.getSituacao().equals("EmAndamento")) {
                script.append("temporizador.addOuvinte(new Solicitacao('tempoRestante" + r.getIdSolicitacaoServico() + "', " + "'barraProgresso" + r.getIdSolicitacaoServico()
                        + "', " + "'" + r.getDataHoraSolicitacao() + "', '" + r.getDataHoraLimite() + "'));");
            }
            html.append("<td><label id='tempoRestante" + r.getIdSolicitacaoServico() + "'></label>");
            html.append("<div id='barraProgresso" + r.getIdSolicitacaoServico() + "'></div></td>");

            /*
             * campo Responsavel Atual, buscando o usuario pelo id para obter o nome do mesmo.
             */
            if (r.getIdUsuarioResponsavelAtual() != null) {
                final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
                final UsuarioDTO usuarioResponsavelAtual = usuarioService.restoreByID(r.getIdUsuarioResponsavelAtual());
                if (usuarioResponsavelAtual != null && usuarioResponsavelAtual.getNomeUsuario() != null) {
                    html.append("<td>" + usuarioResponsavelAtual.getNomeUsuario() + "</td>");
                } else {
                    html.append("<td> </td>");
                }
            } else {
                html.append("<td> </td>");
            }

            html.append("</tr>");
        }
        html.append("</table>");
        return html.toString();
    }

    public void reabre(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();
        SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
        if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico() == null) {
            document.alert(UtilI18N.internacionaliza(request, "pesquisasolicitacao.informeReabrir"));
            return;
        } else {
            solicitacaoServicoDto.setIdSolicitacaoServico(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico());
            solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoService.restore(solicitacaoServicoDto);

            int numDias;
            int numDiasParametro;
            boolean permiteReabrir = false;

            try {

                numDias = UtilDatas.dataDiff(solicitacaoServicoDto.getDataHoraFim(), UtilDatas.getDataAtual());
                numDiasParametro = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.DIAS_LIMITE_REABERTURA_INCIDENTE_REQUISICAO, null));

                if (numDias <= numDiasParametro) {
                    permiteReabrir = true;
                }
            } catch (final NumberFormatException ne) {
                document.alert(UtilI18N.internacionaliza(request, "pesquisasolicitacao.prazoReaberturaNaoConfigurado"));
                return;
            }

            if (!permiteReabrir) {
                document.alert(UtilI18N.internacionaliza(request, "pesquisasolicitacao.prazoReaberturaExcedido"));
                return;
            } else {
                solicitacaoService.reabre(usuario, solicitacaoServicoDto);
            }
        }

        document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.reaberta"));
        document.executeScript("filtrar()");
    }

    public void verificaServicoAtivoSolicitacao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException,
            Exception {
        usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();
        final ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
        if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico() == null) {
            document.alert(UtilI18N.internacionaliza(request, "pesquisasolicitacao.informeReabrir"));
            return;
        } else {
            final boolean seServicoAtivo = servicoContratoService.verificaServicoEstaVinculadoContrato(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico());
            if (!seServicoAtivo) {
                document.executeScript("verificaServico('" + pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico() + "')");
            } else {
                this.reabre(document, request, response);
            }
        }

    }

    public void imprimirRelatorio(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HttpSession session = request.getSession();
        final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
        TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
        final TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
        GrupoDTO grupoDto = new GrupoDTO();
        final GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
        OrigemAtendimentoDTO origemDto = new OrigemAtendimentoDTO();
        final OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
        FaseServicoDTO faseDto = new FaseServicoDTO();
        final FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
        PrioridadeDTO prioridadeDto = new PrioridadeDTO();
        final PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
        ContratoDTO contratoDto = new ContratoDTO();
        final ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
        UsuarioDTO usuarioDto = new UsuarioDTO();
        final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        UnidadeDTO unidadeDto = new UnidadeDTO();
        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

        usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null) {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        if (pesquisaSolicitacaoServicoDto.getDataFimFechamento() != null) {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        // Passamos o usuário para que o sistema possa obter os IDs das unidades que ele pode acessar!
        pesquisaSolicitacaoServicoDto.setUsuarioLogado(usuario);

        ArrayList<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService
                .listaSolicitacaoServicoPorCriterios(pesquisaSolicitacaoServicoDto);

        if (listaSolicitacaoServicoPorCriterios == null || listaSolicitacaoServicoPorCriterios.size() == 0) {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
            return;
        }

        for (final SolicitacaoServicoDTO solicitacaoServico : listaSolicitacaoServicoPorCriterios) {
            solicitacaoServico.setResposta(UtilStrings.unescapeJavaString(solicitacaoServico.getResposta()));
            if ((solicitacaoServico.getPrazoHH() == null || solicitacaoServico.getPrazoHH() == 0)
                    && (solicitacaoServico.getPrazoMM() == null || solicitacaoServico.getPrazoMM() == 0)) {
                solicitacaoServico.setSlaACombinar("S");
            } else {
                solicitacaoServico.setSlaACombinar("N");
            }
            solicitacaoServico.setDescricaoSemFormatacao(UtilStrings.unescapeJavaString(solicitacaoServico.getDescricaoSemFormatacao()));
            solicitacaoServico.setDescricao(UtilStrings.unescapeJavaString(solicitacaoServico.getDescricao()));

            /*
             * Internacionaliza a situação
             */
            solicitacaoServico.setSituacao(solicitacaoServico.obterSituacaoInternacionalizada(request));

            if (pesquisaSolicitacaoServicoDto.getNomeUsuarioResponsavelAtual() != null) {
                solicitacaoServico.setNomeUsuarioResponsavelAtual(pesquisaSolicitacaoServicoDto.getNomeUsuarioResponsavelAtual());
            }

            if (solicitacaoServico != null && solicitacaoServico.getResponsavelAtual() == null) {
                solicitacaoServico.setResponsavelAtual(UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.semResponsavelAtual"));
            }

            if (solicitacaoServico != null && solicitacaoServico.getNomeTarefa() == null) {
                solicitacaoServico.setNomeTarefa(UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
            }
        }

        final Date dt = new Date();
        final String strCompl = "" + dt.getTime();
        final String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaSolicitacaoServico.jasper";
        final String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
        final String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

        Map<String, Object> parametros = new HashMap<String, Object>();

        parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

        parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "citcorporeRelatorio.pesquisaSolicitacoesServicos"));
        parametros.put("CIDADE", "Brasília,");
        parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
        parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
        parametros.put("dataInicio", pesquisaSolicitacaoServicoDto.getDataInicio() == null ? pesquisaSolicitacaoServicoDto.getDataInicioFechamento()
                : pesquisaSolicitacaoServicoDto.getDataInicio());
        parametros.put("dataFim",
                pesquisaSolicitacaoServicoDto.getDataFim() == null ? pesquisaSolicitacaoServicoDto.getDataFimFechamento() : pesquisaSolicitacaoServicoDto.getDataFim());
        parametros.put("Logo", LogoRel.getFile());
        parametros.put("exibirCampoDescricao", UtilStrings.unescapeJavaString(pesquisaSolicitacaoServicoDto.getExibirCampoDescricao()));
        parametros.put("quantidade", listaSolicitacaoServicoPorCriterios.size());
        parametros.put("criado_por", UtilI18N.internacionaliza(request, "citcorpore.comum.criadopor") + ":");

        if (pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao() != null && !pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao().equalsIgnoreCase("")) {
            parametros.put("nomeItemConfiguracao", pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao());
        } else {
            parametros.put("nomeItemConfiguracao", null);
        }
        if (pesquisaSolicitacaoServicoDto.getNomeSolicitante() != null && !pesquisaSolicitacaoServicoDto.getNomeSolicitante().equalsIgnoreCase("")) {
            parametros.put("nomeSolicitante", pesquisaSolicitacaoServicoDto.getNomeSolicitante());
        } else {
            parametros.put("nomeSolicitante", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
            tipoDemandaServicoDto.setIdTipoDemandaServico(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
            tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDto);
            pesquisaSolicitacaoServicoDto.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
            parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
        } else {
            parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
        }
        if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
            parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        } else {
            parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        }
        if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equals("")) {
            parametros.put("situacao", pesquisaSolicitacaoServicoDto.getSituacaoInternacionalizada(request));
        } else {
            parametros.put("situacao", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
            grupoDto.setIdGrupo(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());
            grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
            pesquisaSolicitacaoServicoDto.setGrupoAtual(grupoDto.getSigla());
            parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
        } else {
            parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
            origemDto.setIdOrigem(pesquisaSolicitacaoServicoDto.getIdOrigem());
            origemDto = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemDto);
            pesquisaSolicitacaoServicoDto.setOrigem(UtilStrings.unescapeJavaString(origemDto.getDescricao()));
            parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
        } else {
            parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
        }
        if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
            faseDto.setIdFase(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
            faseDto = (FaseServicoDTO) faseServicoService.restore(faseDto);
            pesquisaSolicitacaoServicoDto.setFaseAtual(faseDto.getNomeFase());
            parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
        } else {
            parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
            prioridadeDto.setIdPrioridade(pesquisaSolicitacaoServicoDto.getIdPrioridade());
            prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
            pesquisaSolicitacaoServicoDto.setPrioridade(prioridadeDto.getNomePrioridade());
            parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
        } else {
            parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
        }

        if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
            contratoDto.setIdContrato(pesquisaSolicitacaoServicoDto.getIdContrato());
            contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
            parametros.put("contrato", contratoDto.getNumero());
        } else {
            parametros.put("contrato", contratoDto.getNumero());
        }

        if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
            usuarioDto.setIdUsuario(pesquisaSolicitacaoServicoDto.getIdResponsavel());
            usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
            parametros.put("responsavel", usuarioDto.getNomeUsuario());
        } else {
            parametros.put("responsavel", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
            usuarioDto.setIdUsuario(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
            usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
            parametros.put("nomeUsuarioResponsavelAtual", usuarioDto.getNomeUsuario());
        } else {
            parametros.put("nomeUsuarioResponsavelAtual", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
            unidadeDto.setIdUnidade(pesquisaSolicitacaoServicoDto.getIdUnidade());
            unidadeDto = (UnidadeDTO) unidadeService.restore(unidadeDto);
            if (unidadeDto != null && unidadeDto.getNome() != null) {
                parametros.put("unidade", unidadeDto.getNome());
            } else {
                parametros.put("unidade", null);
            }
        } else {
            parametros.put("unidade", null);
        }

        parametros.put("nomeTarefaString", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.nomeTarefa"));

        try {
            JRDataSource dataSource = new JRBeanCollectionDataSource(listaSolicitacaoServicoPorCriterios);

            JRAbstractLRUVirtualizer virtualizer = new JRGzipVirtualizer(50);

            // Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
            parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

            // Preenche o relatório e exibe numa GUI
            final Timestamp ts1 = UtilDatas.getDataHoraAtual();
            final JasperPrint jp = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
            final Timestamp ts2 = UtilDatas.getDataHoraAtual();
            final double tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(ts2, ts1);
            System.out.println("########## Tempo fillReport: " + tempo);

            JasperExportManager.exportReportToPdfFile(jp, diretorioReceita + "/RelatorioSolicitacaoServico" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
            document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
                    + diretorioRelativoOS + "/RelatorioSolicitacaoServico" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
            virtualizer = null;
            dataSource = null;
            listaSolicitacaoServicoPorCriterios = null;
        } catch (final OutOfMemoryError e) { // TODO capturar error não né amigão??
            /*
             * Desenvolvedor: Thiago Matias - Data: 30/10/2013 - Horário: 15h35min - ID Citsmart: 122665 -
             * Motivo/Comentário: alterando o a chave de citcorpore.erro.memoria para citsmart.erro.memoria
             */
            document.alert(UtilI18N.internacionaliza(request, "citsmart.erro.memoria"));
        }
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    public void imprimirRelatorioXlsDetalhado(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HttpSession session = request.getSession();
        final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
        TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
        final TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
        GrupoDTO grupoDto = new GrupoDTO();
        final GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
        OrigemAtendimentoDTO origemDto = new OrigemAtendimentoDTO();
        final OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
        FaseServicoDTO faseDto = new FaseServicoDTO();
        final FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
        PrioridadeDTO prioridadeDto = new PrioridadeDTO();
        final PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
        ContratoDTO contratoDto = new ContratoDTO();
        final ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
        UsuarioDTO usuarioDto = new UsuarioDTO();
        final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        UnidadeDTO unidadeDto = new UnidadeDTO();
        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

        usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null) {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        if (pesquisaSolicitacaoServicoDto.getDataFimFechamento() != null) {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        // Passamos o usuário para que o sistema possa obter os IDs das unidades que ele pode acessar!
        pesquisaSolicitacaoServicoDto.setUsuarioLogado(usuario);

        final ArrayList<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService
                .listaSolicitacaoServicoPorCriterios(pesquisaSolicitacaoServicoDto);

        if (listaSolicitacaoServicoPorCriterios == null || listaSolicitacaoServicoPorCriterios.size() == 0) {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
            return;
        }

        for (final SolicitacaoServicoDTO solicitacaoServico : listaSolicitacaoServicoPorCriterios) {
            solicitacaoServico.setResposta(UtilStrings.unescapeJavaString(solicitacaoServico.getResposta()));
            if ((solicitacaoServico.getPrazoHH() == null || solicitacaoServico.getPrazoHH() == 0)
                    && (solicitacaoServico.getPrazoMM() == null || solicitacaoServico.getPrazoMM() == 0)) {
                solicitacaoServico.setSlaACombinar("S");
            } else {
                solicitacaoServico.setSlaACombinar("N");
            }
            solicitacaoServico.setDescricaoSemFormatacao(UtilStrings.unescapeJavaString(solicitacaoServico.getDescricaoSemFormatacao()));
            solicitacaoServico.setDescricao(UtilStrings.unescapeJavaString(solicitacaoServico.getDescricao()));

            /*
             * Internacionaliza a situação
             */
            solicitacaoServico.setSituacao(solicitacaoServico.obterSituacaoInternacionalizada(request));

            if (pesquisaSolicitacaoServicoDto.getNomeUsuarioResponsavelAtual() != null) {
                solicitacaoServico.setNomeUsuarioResponsavelAtual(pesquisaSolicitacaoServicoDto.getNomeUsuarioResponsavelAtual());
            }
            /**
             * Cria SLA para solicitacao
             *
             * @author thyen.chang
             */
            if (solicitacaoServico != null && solicitacaoServico.getPrazoHH() != null && solicitacaoServico.getPrazoMM() != null) {
                solicitacaoServico.setSla((solicitacaoServico.getPrazoHH() < 10 ? "0" + solicitacaoServico.getPrazoHH().toString() : solicitacaoServico.getPrazoHH().toString())
                        + ":" + (solicitacaoServico.getPrazoMM() < 10 ? "0" + solicitacaoServico.getPrazoMM().toString() : solicitacaoServico.getPrazoMM().toString()));
            } else {
                solicitacaoServico.setSla("");
            }
            /**
             * Determina localidade caso não exista
             *
             * @author thyen.chang
             */
            if (solicitacaoServico != null && (solicitacaoServico.getLocalidade() == null || solicitacaoServico.getLocalidade().equals(""))) {
                solicitacaoServico.setLocalidade(UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
            }
            /**
             * Determina responsável caso não exista
             *
             * @author thyen.chang
             */
            if (solicitacaoServico != null && solicitacaoServico.getResponsavelAtual() == null) {
                solicitacaoServico.setResponsavelAtual(UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.semResponsavelAtual"));
            }

            if (solicitacaoServico != null && solicitacaoServico.getNomeTarefa() == null) {
                solicitacaoServico.setNomeTarefa(UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
            }

            if (solicitacaoServico != null && solicitacaoServico.getResposta() != null) {
                solicitacaoServico.setResposta(formataCaracteresInválidos(solicitacaoServico.getResposta()));
            }
        }

        final Date dt = new Date();
        final String strCompl = "" + dt.getTime();
        final String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
        final String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

        parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "citcorporeRelatorio.pesquisaSolicitacoesServicosDetalhado"));
        parametros.put("CIDADE", "Brasília,");
        parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
        parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
        parametros.put("dataInicio", pesquisaSolicitacaoServicoDto.getDataInicio() == null ? pesquisaSolicitacaoServicoDto.getDataInicioFechamento()
                : pesquisaSolicitacaoServicoDto.getDataInicio());
        parametros.put("dataFim",
                pesquisaSolicitacaoServicoDto.getDataFim() == null ? pesquisaSolicitacaoServicoDto.getDataFimFechamento() : pesquisaSolicitacaoServicoDto.getDataFim());
        parametros.put("Logo", LogoRel.getFile());
        parametros.put("exibirCampoDescricao", pesquisaSolicitacaoServicoDto.getExibirCampoDescricao());
        parametros.put("quantidade", listaSolicitacaoServicoPorCriterios.size());
        parametros.put("criado_por", UtilI18N.internacionaliza(request, "citcorpore.comum.criadopor") + ":");

        if (pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao() != null && !pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao().equalsIgnoreCase("")) {
            parametros.put("nomeItemConfiguracao", pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao());
        } else {
            parametros.put("nomeItemConfiguracao", null);
        }
        if (pesquisaSolicitacaoServicoDto.getNomeSolicitante() != null && !pesquisaSolicitacaoServicoDto.getNomeSolicitante().equalsIgnoreCase("")) {
            parametros.put("nomeSolicitante", pesquisaSolicitacaoServicoDto.getNomeSolicitante());
        } else {
            parametros.put("nomeSolicitante", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
            tipoDemandaServicoDto.setIdTipoDemandaServico(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
            tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDto);
            pesquisaSolicitacaoServicoDto.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
            parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
        } else {
            parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
        }
        if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
            parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        } else {
            parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        }
        if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equals("")) {
            parametros.put("situacao", pesquisaSolicitacaoServicoDto.getSituacaoInternacionalizada(request));
        } else {
            parametros.put("situacao", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
            grupoDto.setIdGrupo(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());
            grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
            pesquisaSolicitacaoServicoDto.setGrupoAtual(grupoDto.getSigla());
            parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
        } else {
            parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
            origemDto.setIdOrigem(pesquisaSolicitacaoServicoDto.getIdOrigem());
            origemDto = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemDto);
            pesquisaSolicitacaoServicoDto.setOrigem(UtilStrings.unescapeJavaString(origemDto.getDescricao()));
            parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
        } else {
            parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
        }
        if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
            faseDto.setIdFase(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
            faseDto = (FaseServicoDTO) faseServicoService.restore(faseDto);
            pesquisaSolicitacaoServicoDto.setFaseAtual(faseDto.getNomeFase());
            parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
        } else {
            parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
            prioridadeDto.setIdPrioridade(pesquisaSolicitacaoServicoDto.getIdPrioridade());
            prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
            pesquisaSolicitacaoServicoDto.setPrioridade(prioridadeDto.getNomePrioridade());
            parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
        } else {
            parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
        }

        if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
            contratoDto.setIdContrato(pesquisaSolicitacaoServicoDto.getIdContrato());
            contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
            parametros.put("contrato", contratoDto.getNumero());
        } else {
            parametros.put("contrato", contratoDto.getNumero());
        }

        if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
            usuarioDto.setIdUsuario(pesquisaSolicitacaoServicoDto.getIdResponsavel());
            usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
            parametros.put("responsavel", usuarioDto.getNomeUsuario());
        } else {
            parametros.put("responsavel", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
            usuarioDto.setIdUsuario(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
            usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
            parametros.put("nomeUsuarioResponsavelAtual", usuarioDto.getNomeUsuario());
        } else {
            parametros.put("nomeUsuarioResponsavelAtual", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
            unidadeDto.setIdUnidade(pesquisaSolicitacaoServicoDto.getIdUnidade());
            unidadeDto = (UnidadeDTO) unidadeService.restore(unidadeDto);
            if (unidadeDto != null && unidadeDto.getNome() != null) {
                parametros.put("unidade", unidadeDto.getNome());
            } else {
                parametros.put("unidade", null);
            }
        } else {
            parametros.put("unidade", null);
        }

        parametros.put("nomeTarefaString", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.nomeTarefa"));

        try {
            final JRDataSource dataSource = new JRBeanCollectionDataSource(listaSolicitacaoServicoPorCriterios);

            final JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS")
                    + "RelatorioPesquisaSolicitacaoServicoXlsDetalhado.jrxml");

            final JasperReport relatorio = JasperCompileManager.compileReport(desenho);

            final JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

            final JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressao);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
                    diretorioReceita + "/RelatorioPesquisaSolicitacaoServicoXlsDetalhado" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

            exporter.exportReport();

            document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
                    + diretorioRelativoOS + "/RelatorioPesquisaSolicitacaoServicoXlsDetalhado" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
        } catch (final OutOfMemoryError e) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
        }
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

    }

    public void restoreUpload(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        request.getSession(true).setAttribute("colUploadsGED", null);
        /* Realida o refresh do iframe */
        document.executeScript("document.getElementById('fraUpload_uploadAnexos').contentWindow.location.reload(true)");

        usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();

        if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico() == null) {
            return;
        }
        final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        final Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico());
        final Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

        request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);
        document.executeScript("$('#POPUP_menuAnexos').dialog('open');");
    }

    public String mostrarOcorrencia() throws Exception {
        final OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(
                OcorrenciaSolicitacaoService.class, null);
        final Collection<OcorrenciaSolicitacaoDTO> col = ocorrenciaSolicitacaoService.findByIdSolicitacaoServico(13458);
        for (final OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO : col) {
            String dadosSolicitacao = UtilStrings.nullToVazio(ocorrenciaSolicitacaoDTO.getDadosSolicitacao());
            SolicitacaoServicoDTO solicitacaoDto = null;
            if (dadosSolicitacao.length() > 0) {
                try {
                    solicitacaoDto = new Gson().fromJson(dadosSolicitacao, SolicitacaoServicoDTO.class);
                    if (solicitacaoDto != null) {
                        dadosSolicitacao = solicitacaoDto.getDadosStr();

                        if (solicitacaoDto.getSituacao().equals("Resolvido") && !solicitacaoDto.getRegistradoPor().equals("Automático")) {
                            return solicitacaoDto.getRegistradoPor();
                        }
                    }
                } catch (final Exception e) {
                    dadosSolicitacao = "";
                }
            }
            System.out.println(dadosSolicitacao);
        }
        return "";
    }

    public void imprimirRelatorioDetalhado(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HttpSession session = request.getSession();
        final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
        TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
        final TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
        GrupoDTO grupoDto = new GrupoDTO();
        final GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
        OrigemAtendimentoDTO origemDto = new OrigemAtendimentoDTO();
        final OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
        FaseServicoDTO faseDto = new FaseServicoDTO();
        final FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
        PrioridadeDTO prioridadeDto = new PrioridadeDTO();
        final PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
        ContratoDTO contratoDto = new ContratoDTO();
        final ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
        UsuarioDTO usuarioDto = new UsuarioDTO();
        final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        UnidadeDTO unidadeDto = new UnidadeDTO();
        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

        usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null) {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        if (pesquisaSolicitacaoServicoDto.getDataFimFechamento() != null) {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        // Passamos o usuário para que o sistema possa obter os IDs das unidades que ele pode acessar!
        pesquisaSolicitacaoServicoDto.setUsuarioLogado(usuario);

        ArrayList<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService
                .listaSolicitacaoServicoPorCriterios(pesquisaSolicitacaoServicoDto);

        if (listaSolicitacaoServicoPorCriterios == null || listaSolicitacaoServicoPorCriterios.size() == 0) {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
            return;
        }

        for (final SolicitacaoServicoDTO solicitacaoServico : listaSolicitacaoServicoPorCriterios) {
            solicitacaoServico.setResposta(UtilStrings.unescapeJavaString(solicitacaoServico.getResposta()));
            if ((solicitacaoServico.getPrazoHH() == null || solicitacaoServico.getPrazoHH() == 0)
                    && (solicitacaoServico.getPrazoMM() == null || solicitacaoServico.getPrazoMM() == 0)) {
                solicitacaoServico.setSlaACombinar("S");
            } else {
                solicitacaoServico.setSlaACombinar("N");
            }
            solicitacaoServico.setDescricaoSemFormatacao(UtilStrings.unescapeJavaString(solicitacaoServico.getDescricaoSemFormatacao()));
            solicitacaoServico.setDescricao(UtilStrings.unescapeJavaString(solicitacaoServico.getDescricao()));

            /*
             * Internacionaliza a situação
             */
            solicitacaoServico.setSituacao(solicitacaoServico.obterSituacaoInternacionalizada(request));

            if (pesquisaSolicitacaoServicoDto.getNomeUsuarioResponsavelAtual() != null) {
                solicitacaoServico.setNomeUsuarioResponsavelAtual(pesquisaSolicitacaoServicoDto.getNomeUsuarioResponsavelAtual());
            }
            /**
             * Cria SLA para solicitacao
             *
             * @author thyen.chang
             */
            if (solicitacaoServico != null && solicitacaoServico.getPrazoHH() != null && solicitacaoServico.getPrazoMM() != null) {
                solicitacaoServico.setSla((solicitacaoServico.getPrazoHH() < 10 ? "0" + solicitacaoServico.getPrazoHH().toString() : solicitacaoServico.getPrazoHH().toString())
                        + ":" + (solicitacaoServico.getPrazoMM() < 10 ? "0" + solicitacaoServico.getPrazoMM().toString() : solicitacaoServico.getPrazoMM().toString()));
            } else {
                solicitacaoServico.setSla("");
            }
            /**
             * Determina localidade caso não exista
             *
             * @author thyen.chang
             */
            if (solicitacaoServico != null && (solicitacaoServico.getLocalidade() == null || solicitacaoServico.getLocalidade().equals(""))) {
                solicitacaoServico.setLocalidade(UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
            }
            /**
             * Determina responsável caso não exista
             *
             * @author thyen.chang
             */
            if (solicitacaoServico != null && solicitacaoServico.getResponsavelAtual() == null) {
                solicitacaoServico.setResponsavelAtual(UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.semResponsavelAtual"));
            }

            if (solicitacaoServico != null && solicitacaoServico.getNomeTarefa() == null) {
                solicitacaoServico.setNomeTarefa(UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
            }

        }

        final Date dt = new Date();
        final String strCompl = "" + dt.getTime();
        final String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaSolicitacaoServicoDetalhado.jasper";
        final String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
        final String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

        Map<String, Object> parametros = new HashMap<String, Object>();

        parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

        parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "citcorporeRelatorio.pesquisaSolicitacoesServicosDetalhado"));
        parametros.put("CIDADE", "Brasília,");
        parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
        parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
        parametros.put("dataInicio", pesquisaSolicitacaoServicoDto.getDataInicio() == null ? pesquisaSolicitacaoServicoDto.getDataInicioFechamento()
                : pesquisaSolicitacaoServicoDto.getDataInicio());
        parametros.put("dataFim",
                pesquisaSolicitacaoServicoDto.getDataFim() == null ? pesquisaSolicitacaoServicoDto.getDataFimFechamento() : pesquisaSolicitacaoServicoDto.getDataFim());
        parametros.put("Logo", LogoRel.getFile());
        parametros.put("exibirCampoDescricao", UtilStrings.unescapeJavaString(pesquisaSolicitacaoServicoDto.getExibirCampoDescricao()));
        parametros.put("quantidade", listaSolicitacaoServicoPorCriterios.size());
        parametros.put("criado_por", UtilI18N.internacionaliza(request, "citcorpore.comum.criadopor") + ":");

        if (pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao() != null && !pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao().equalsIgnoreCase("")) {
            parametros.put("nomeItemConfiguracao", pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao());
        } else {
            parametros.put("nomeItemConfiguracao", null);
        }
        if (pesquisaSolicitacaoServicoDto.getNomeSolicitante() != null && !pesquisaSolicitacaoServicoDto.getNomeSolicitante().equalsIgnoreCase("")) {
            parametros.put("nomeSolicitante", pesquisaSolicitacaoServicoDto.getNomeSolicitante());
        } else {
            parametros.put("nomeSolicitante", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
            tipoDemandaServicoDto.setIdTipoDemandaServico(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
            tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDto);
            pesquisaSolicitacaoServicoDto.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
            parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
        } else {
            parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
        }
        if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
            parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        } else {
            parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        }
        if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equals("")) {
            parametros.put("situacao", pesquisaSolicitacaoServicoDto.getSituacaoInternacionalizada(request));
        } else {
            parametros.put("situacao", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
            grupoDto.setIdGrupo(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());
            grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
            pesquisaSolicitacaoServicoDto.setGrupoAtual(grupoDto.getSigla());
            parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
        } else {
            parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
            origemDto.setIdOrigem(pesquisaSolicitacaoServicoDto.getIdOrigem());
            origemDto = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemDto);
            pesquisaSolicitacaoServicoDto.setOrigem(UtilStrings.unescapeJavaString(origemDto.getDescricao()));
            parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
        } else {
            parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
        }
        if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
            faseDto.setIdFase(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
            faseDto = (FaseServicoDTO) faseServicoService.restore(faseDto);
            pesquisaSolicitacaoServicoDto.setFaseAtual(faseDto.getNomeFase());
            parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
        } else {
            parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
            prioridadeDto.setIdPrioridade(pesquisaSolicitacaoServicoDto.getIdPrioridade());
            prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
            pesquisaSolicitacaoServicoDto.setPrioridade(prioridadeDto.getNomePrioridade());
            parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
        } else {
            parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
        }

        if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
            contratoDto.setIdContrato(pesquisaSolicitacaoServicoDto.getIdContrato());
            contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
            parametros.put("contrato", contratoDto.getNumero());
        } else {
            parametros.put("contrato", contratoDto.getNumero());
        }

        if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
            usuarioDto.setIdUsuario(pesquisaSolicitacaoServicoDto.getIdResponsavel());
            usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
            parametros.put("responsavel", usuarioDto.getNomeUsuario());
        } else {
            parametros.put("responsavel", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
            usuarioDto.setIdUsuario(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
            usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
            parametros.put("nomeUsuarioResponsavelAtual", usuarioDto.getNomeUsuario());
        } else {
            parametros.put("nomeUsuarioResponsavelAtual", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
            unidadeDto.setIdUnidade(pesquisaSolicitacaoServicoDto.getIdUnidade());
            unidadeDto = (UnidadeDTO) unidadeService.restore(unidadeDto);
            if (unidadeDto != null && unidadeDto.getNome() != null) {
                parametros.put("unidade", unidadeDto.getNome());
            } else {
                parametros.put("unidade", null);
            }
        } else {
            parametros.put("unidade", null);
        }

        parametros.put("nomeTarefaString", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.nomeTarefa"));

        try {
            JRDataSource dataSource = new JRBeanCollectionDataSource(listaSolicitacaoServicoPorCriterios);

            JRAbstractLRUVirtualizer virtualizer = new JRGzipVirtualizer(50);

            // Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
            parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

            // Preenche o relatório e exibe numa GUI
            final Timestamp ts1 = UtilDatas.getDataHoraAtual();
            final JasperPrint jp = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
            final Timestamp ts2 = UtilDatas.getDataHoraAtual();
            final double tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(ts2, ts1);
            System.out.println("########## Tempo fillReport: " + tempo);

            JasperExportManager.exportReportToPdfFile(jp, diretorioReceita + "/RelatorioSolicitacaoServicoDetalhado" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
            document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
                    + diretorioRelativoOS + "/RelatorioSolicitacaoServicoDetalhado" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
            virtualizer = null;
            dataSource = null;
            listaSolicitacaoServicoPorCriterios = null;
        } catch (final OutOfMemoryError e) { // TODO capturar error não né amigão??
            /*
             * Desenvolvedor: Thiago Matias - Data: 30/10/2013 - Horário: 15h35min - ID Citsmart: 122665 -
             * Motivo/Comentário: alterando o a chave de citcorpore.erro.memoria para citsmart.erro.memoria
             */
            document.alert(UtilI18N.internacionaliza(request, "citsmart.erro.memoria"));
        }
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    public void imprimirRelatorioXls(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HttpSession session = request.getSession();
        final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
        TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
        final TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
        GrupoDTO grupoDto = new GrupoDTO();
        final GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
        OrigemAtendimentoDTO origemDto = new OrigemAtendimentoDTO();
        final OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
        FaseServicoDTO faseDto = new FaseServicoDTO();
        final FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
        PrioridadeDTO prioridadeDto = new PrioridadeDTO();
        final PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
        ContratoDTO contratoDto = new ContratoDTO();
        final ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
        UsuarioDTO usuarioDto = new UsuarioDTO();
        final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        UnidadeDTO unidadeDto = new UnidadeDTO();
        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

        usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null) {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        if (pesquisaSolicitacaoServicoDto.getDataFimFechamento() != null) {
            document.executeScript("$('#situacao').attr('disabled', 'true')");
            pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
        }

        // Passamos o usuário para que o sistema possa obter os IDs das unidades que ele pode acessar!
        pesquisaSolicitacaoServicoDto.setUsuarioLogado(usuario);

        final List<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService
                .listaSolicitacaoServicoPorCriterios(pesquisaSolicitacaoServicoDto);

        if (listaSolicitacaoServicoPorCriterios == null || listaSolicitacaoServicoPorCriterios.size() == 0) {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
            return;
        }

        for (final SolicitacaoServicoDTO solicitacaoServico : listaSolicitacaoServicoPorCriterios) {
            solicitacaoServico.setResposta(UtilStrings.unescapeJavaString(solicitacaoServico.getResposta()));
            if ((solicitacaoServico.getPrazoHH() == null || solicitacaoServico.getPrazoHH() == 0)
                    && (solicitacaoServico.getPrazoMM() == null || solicitacaoServico.getPrazoMM() == 0)) {
                solicitacaoServico.setSlaACombinar("S");
            } else {
                solicitacaoServico.setSlaACombinar("N");
            }
            solicitacaoServico.setDescricaoSemFormatacao(UtilStrings.unescapeJavaString(solicitacaoServico.getDescricaoSemFormatacao()));
            solicitacaoServico.setDescricao(UtilStrings.unescapeJavaString(solicitacaoServico.getDescricao()));

            /*
             * Internacionaliza a situação
             */
            solicitacaoServico.setSituacao(solicitacaoServico.obterSituacaoInternacionalizada(request));

            if (pesquisaSolicitacaoServicoDto.getNomeUsuarioResponsavelAtual() != null) {
                solicitacaoServico.setNomeUsuarioResponsavelAtual(pesquisaSolicitacaoServicoDto.getNomeUsuarioResponsavelAtual());
            }

            if (solicitacaoServico != null && solicitacaoServico.getResponsavelAtual() == null) {
                solicitacaoServico.setResponsavelAtual(UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.semResponsavelAtual"));
            }

            if (solicitacaoServico != null && solicitacaoServico.getNomeTarefa() == null) {
                solicitacaoServico.setNomeTarefa(UtilI18N.internacionaliza(request, "citcorpore.comum.naoInformado"));
            }

            if (solicitacaoServico != null && solicitacaoServico.getResposta() != null) {
                solicitacaoServico.setResposta(formataCaracteresInválidos(solicitacaoServico.getResposta()));
            }

        }

        final Date dt = new Date();
        final String strCompl = "" + dt.getTime();
        final String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
        final String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

        parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "citcorporeRelatorio.pesquisaSolicitacoesServicos"));
        parametros.put("CIDADE", "Brasília,");
        parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
        parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
        parametros.put("dataInicio", pesquisaSolicitacaoServicoDto.getDataInicio() == null ? pesquisaSolicitacaoServicoDto.getDataInicioFechamento()
                : pesquisaSolicitacaoServicoDto.getDataInicio());
        parametros.put("dataFim",
                pesquisaSolicitacaoServicoDto.getDataFim() == null ? pesquisaSolicitacaoServicoDto.getDataFimFechamento() : pesquisaSolicitacaoServicoDto.getDataFim());
        parametros.put("Logo", LogoRel.getFile());
        parametros.put("exibirCampoDescricao", pesquisaSolicitacaoServicoDto.getExibirCampoDescricao());
        parametros.put("quantidade", listaSolicitacaoServicoPorCriterios.size());
        parametros.put("criado_por", UtilI18N.internacionaliza(request, "citcorpore.comum.criadopor") + ":");

        if (pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao() != null && !pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao().equalsIgnoreCase("")) {
            parametros.put("nomeItemConfiguracao", pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao());
        } else {
            parametros.put("nomeItemConfiguracao", null);
        }
        if (pesquisaSolicitacaoServicoDto.getNomeSolicitante() != null && !pesquisaSolicitacaoServicoDto.getNomeSolicitante().equalsIgnoreCase("")) {
            parametros.put("nomeSolicitante", pesquisaSolicitacaoServicoDto.getNomeSolicitante());
        } else {
            parametros.put("nomeSolicitante", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
            tipoDemandaServicoDto.setIdTipoDemandaServico(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
            tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDto);
            pesquisaSolicitacaoServicoDto.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
            parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
        } else {
            parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
        }
        if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
            parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        } else {
            parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        }
        if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equals("")) {
            parametros.put("situacao", pesquisaSolicitacaoServicoDto.getSituacaoInternacionalizada(request));
        } else {
            parametros.put("situacao", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
            grupoDto.setIdGrupo(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());
            grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
            pesquisaSolicitacaoServicoDto.setGrupoAtual(grupoDto.getSigla());
            parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
        } else {
            parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
            origemDto.setIdOrigem(pesquisaSolicitacaoServicoDto.getIdOrigem());
            origemDto = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemDto);
            pesquisaSolicitacaoServicoDto.setOrigem(UtilStrings.unescapeJavaString(origemDto.getDescricao()));
            parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
        } else {
            parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
        }
        if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
            faseDto.setIdFase(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
            faseDto = (FaseServicoDTO) faseServicoService.restore(faseDto);
            pesquisaSolicitacaoServicoDto.setFaseAtual(faseDto.getNomeFase());
            parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
        } else {
            parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
            prioridadeDto.setIdPrioridade(pesquisaSolicitacaoServicoDto.getIdPrioridade());
            prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
            pesquisaSolicitacaoServicoDto.setPrioridade(prioridadeDto.getNomePrioridade());
            parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
        } else {
            parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
        }

        if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
            contratoDto.setIdContrato(pesquisaSolicitacaoServicoDto.getIdContrato());
            contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
            parametros.put("contrato", contratoDto.getNumero());
        } else {
            parametros.put("contrato", contratoDto.getNumero());
        }

        if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
            usuarioDto.setIdUsuario(pesquisaSolicitacaoServicoDto.getIdResponsavel());
            usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
            parametros.put("responsavel", usuarioDto.getNomeUsuario());
        } else {
            parametros.put("responsavel", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
            usuarioDto.setIdUsuario(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
            usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
            parametros.put("nomeUsuarioResponsavelAtual", usuarioDto.getNomeUsuario());
        } else {
            parametros.put("nomeUsuarioResponsavelAtual", null);
        }

        if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
            unidadeDto.setIdUnidade(pesquisaSolicitacaoServicoDto.getIdUnidade());
            unidadeDto = (UnidadeDTO) unidadeService.restore(unidadeDto);
            if (unidadeDto != null && unidadeDto.getNome() != null) {
                parametros.put("unidade", unidadeDto.getNome());
            } else {
                parametros.put("unidade", null);
            }
        } else {
            parametros.put("unidade", null);
        }

        parametros.put("nomeTarefaString", UtilI18N.internacionaliza(request, "citcorporeRelatorio.comum.nomeTarefa"));

        try {
            final JRDataSource dataSource = new JRBeanCollectionDataSource(listaSolicitacaoServicoPorCriterios);

            final JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS")
                    + "RelatorioPesquisaSolicitacaoServicoXls.jrxml");

            final JasperReport relatorio = JasperCompileManager.compileReport(desenho);

            final JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

            final JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressao);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporter.setParameter(JRXlsAbstractExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioPesquisaSolicitacaoServicoXls" + strCompl + "_" + usuario.getIdUsuario()
                    + ".xls");

            exporter.exportReport();

            document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
                    + diretorioRelativoOS + "/RelatorioPesquisaSolicitacaoServicoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
        } catch (final OutOfMemoryError e) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
        }
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

    }

    public void preencherComboUnidade(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) {

        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
        if (UNIDADE_AUTOCOMPLETE == null || !UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")) {
            usuario = WebUtil.getUsuario(request);
            final PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();
            final String UNIDADE_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(
                    br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");

            Integer idContrato = 0;
            if (UNIDADE_VINC_CONTRATOS != null && UNIDADE_VINC_CONTRATOS.equalsIgnoreCase("S")) {
                idContrato = pesquisaSolicitacaoServicoDto.getIdContrato() != null && pesquisaSolicitacaoServicoDto.getIdContrato().intValue() > 0 ? pesquisaSolicitacaoServicoDto
                        .getIdContrato().intValue() : -1;
            }

            EmpregadoDTO empregadoDTO = new EmpregadoDTO();
            try {
                final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

                empregadoDTO.setIdEmpregado(usuario.getIdEmpregado());
                empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
            } catch (final LogicException e1) {
                e1.printStackTrace();
            } catch (final ServiceException e1) {
                e1.printStackTrace();
            } catch (final Exception e) {
                e.printStackTrace();
            }
            final Integer idUnidadeColaborador = empregadoDTO.getIdUnidade() != null && empregadoDTO.getIdUnidade().intValue() > 0 ? empregadoDTO.getIdUnidade() : 0;

            final String tipoHierarquia = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.TIPO_HIERARQUIA_UNIDADE, "1");

            new ArrayList<UnidadeDTO>();
            Arvore arvore = new Arvore();
            try {
                final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
                arvore = unidadeService.obtemArvoreUnidades("", idContrato, idUnidadeColaborador, tipoHierarquia, 0);
            } catch (final Exception e1) {
                e1.printStackTrace();
            }

            HTMLSelect comboUnidade;
            try {
                comboUnidade = document.getSelectById("idUnidade");
                if (comboUnidade != null) {
                    comboUnidade.removeAllOptions();
                    comboUnidade.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.todas"));

                    for (int i = 0; i < arvore.getListaID().size(); i++) {
                        comboUnidade.addOption(arvore.getListaID().get(i).toString(), arvore.getListaTexto().get(i));
                    }
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Função para retirar caracteres HTML inválidos no relatório XLS
     * 
     * @param texto
     * @return
     */
    public static String formataCaracteresInválidos(final String texto) {
        return Jsoup.parse(texto).text();
    }

}
