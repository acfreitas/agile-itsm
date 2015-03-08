package br.com.centralit.citcorpore.metainfo.ajaxForms;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citajax.framework.ParserRequest;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ColumnsDTO;
import br.com.centralit.citcorpore.metainfo.bean.DinamicViewsDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO;
import br.com.centralit.citcorpore.metainfo.negocio.BotaoAcaoVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.DinamicViewsService;
import br.com.centralit.citcorpore.metainfo.negocio.GrupoVisaoCamposNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.GrupoVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.HtmlCodeVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.ScriptsVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.ValorVisaoCamposNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.VisaoRelacionadaService;
import br.com.centralit.citcorpore.metainfo.negocio.VisaoService;
import br.com.centralit.citcorpore.metainfo.script.ScriptRhinoJSExecute;
import br.com.centralit.citcorpore.metainfo.util.JSONUtil;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.ServicoContratoServiceEjb;
import br.com.centralit.citcorpore.negocio.SlaRequisitoSlaServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DinamicViews extends AjaxFormAction {

    private static final Logger LOGGER = Logger.getLogger(DinamicViews.class);

    private static boolean DEBUG = false;

    private static Gson GSON = new Gson();

    @Override
    public Class<DinamicViewsDTO> getBeanClass() {
        return DinamicViewsDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
        if (usuarioDto == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        final DinamicViewsDTO dinamicViewsDTO = (DinamicViewsDTO) document.getBean();

        if (dinamicViewsDTO.getIdVisao() == null) {
            if (StringUtils.isNotBlank(dinamicViewsDTO.getIdentificacao())) {
                final VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
                final VisaoDTO visaoDto = visaoService.findByIdentificador(dinamicViewsDTO.getIdentificacao());
                if (visaoDto == null) {
                    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.visao_nao_encontrada"));
                    return;
                }
                dinamicViewsDTO.setIdVisao(visaoDto.getIdVisao());
            } else {
                document.alert(UtilI18N.internacionaliza(request, "dinamicview.visaonaoencontrada"));
                return;
            }
        }

        final VisaoDTO visaoDto = this.recuperaVisao(dinamicViewsDTO.getIdVisao(), true);
        if (visaoDto == null) {
            document.alert(UtilI18N.internacionaliza(request, "dinamicview.visaonaoencontrada"));
            return;
        }

        final Map hashValores = this.getFormFields(request);
        if (DEBUG) {
            this.debugValuesFromRequest(hashValores);
        }

        final Map<String, Object> map = new HashMap<>();

        map.put("SESSION.DINAMICVIEWS_SAVEINFO", request.getSession().getAttribute("DinamicViews_SaveInfo"));
        map.put("SESSION.NUMERO_CONTRATO_EDICAO", request.getSession().getAttribute("NUMERO_CONTRATO_EDICAO"));

        request.setAttribute("visao", visaoDto);

        request.getSession().setAttribute("DinamicViews_SaveInfo", "");
        request.getSession().setAttribute("DinamicViews_SaveInfo", dinamicViewsDTO.getSaveInfo());

        final BotaoAcaoVisaoService botaoAcaoVisaoService = (BotaoAcaoVisaoService) ServiceLocator.getInstance().getService(BotaoAcaoVisaoService.class, null);
        final Collection colBotoes = botaoAcaoVisaoService.findByIdVisao(dinamicViewsDTO.getIdVisao());
        request.setAttribute("botoes", colBotoes);

        final String strScript = (String) visaoDto.getMapScripts().get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_LOAD.getName());
        if (StringUtils.isNotBlank(strScript)) {
            final ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
            final Context cx = Context.enter();
            final Scriptable scope = cx.initStandardObjects();
            scope.put("usuarioDto", scope, usuarioDto);
            scope.put("document", scope, document);
            scope.put("mapFields", scope, map);
            scope.put("visaoDto", scope, visaoDto);
            scope.put("request", scope, request);
            scope.put("response", scope, response);
            scope.put("language", scope, WebUtil.getLanguage(request));
            scriptExecute.processScript(cx, scope, strScript, DinamicViews.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_LOAD.getName());
        }

        this.setValuesFromMap(document, request, map, "document.form");

        if (StringUtils.isNotBlank(dinamicViewsDTO.getId())) {
            final VisaoDTO visaoPesquisaDto = this.recuperaVisao(visaoDto.getIdVisao(), false);
            final Collection colFilter = new ArrayList();
            final Collection colGrupos = visaoPesquisaDto.getColGrupos();
            for (final Iterator it = colGrupos.iterator(); it.hasNext();) {
                final GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
                if (grupoVisaoDTO.getColCamposVisao() != null) {
                    for (final Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
                        final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();
                        if (grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().getPk().equalsIgnoreCase("S")) {
                            grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().setValue(dinamicViewsDTO.getId());
                            colFilter.add(grupoVisaoCamposNegocioDTO);
                        }
                    }
                }
            }
            final String metodoOrigem = "load";
            this.restoreVisao(document, request, response, visaoDto.getIdVisao(), colFilter, metodoOrigem);
        }

        /**
         * Recuperação de permissão de menus feita através do mapa estático da sessão
         *
         * @author thyen.chang
         * @since 28/01/2015 - OPERAÇÃO USAIN BOLT
         */
        final MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
        final String pathInfo = this.getRequestedPath(request);
        final String url = pathInfo.replaceAll("/pages", "");
        final Integer idMenu = menuService.buscarIdMenu(url);
        String acessoGravar = "N";
        String acessoDeletar = "N";
        String acessoPesquisar = "N";
        if (idMenu != null) {
            final List<PerfilAcessoMenuDTO> listaAcessoMenus = WebUtil.getPerfilAcessoUsuarioByMenu(request, usuarioDto, idMenu);
            if (listaAcessoMenus != null) {
                for (final PerfilAcessoMenuDTO perfilAcessoMenu : listaAcessoMenus) {
                    if (perfilAcessoMenu.getGrava() != null && perfilAcessoMenu.getGrava().equalsIgnoreCase("S")) {
                        acessoGravar = "S";
                    }
                    if (perfilAcessoMenu.getDeleta() != null && perfilAcessoMenu.getDeleta().equalsIgnoreCase("S")) {
                        acessoDeletar = "S";
                    }
                    if (perfilAcessoMenu.getPesquisa() != null && perfilAcessoMenu.getPesquisa().equalsIgnoreCase("S")) {
                        acessoPesquisar = "S";
                    }
                }
            }
        } else {
            acessoGravar = "S";
            acessoDeletar = "S";
            acessoPesquisar = "S";
        }
        if (acessoGravar.equalsIgnoreCase("N")) {
            document.executeScript("try{document.getElementById('btnGravar').style.display='none';}catch(e){}");
        }
        if (acessoDeletar.equalsIgnoreCase("N")) {
            document.executeScript("try{document.getElementById('btnExcluir').style.display='none';}catch(e){}");
        }
        if (acessoPesquisar.equalsIgnoreCase("S")) {
            document.executeScript("try{acaoPesquisar = 'S';}catch(e){}");
        }

        if (dinamicViewsDTO.getIdentificacao() != null && "Contratos".equalsIgnoreCase(dinamicViewsDTO.getIdentificacao())) {
            this.limparCamposFirefox(document);
        }
    }

    /**
     * Metodo responsavel por limpar os campos da dinamic view contrato.
     *
     * A criação dessa função diretamente na tela, estava gerando um alert de erro.
     *
     * a solução encontrada para não gerar alerta de erro ao usuário, foi criar a função direto na classe e
     *
     * será executada apenas se a identificação da dinamicView for igual a 'Contratos'
     *
     * @param document
     *
     * @author Ezequiel Bispo Nunes
     */
    private void limparCamposFirefox(final DocumentHTML document) {
        final StringBuilder acaoLimparCamposFirefox = new StringBuilder();
        acaoLimparCamposFirefox.append("if ( $('#IDCONTRATO').val() != null &&  $('#IDCONTRATO').val() != ''){ ");
        acaoLimparCamposFirefox.append("    $('#form').find(\"input[type='text'], select, textarea\").val(\"\"); ");
        acaoLimparCamposFirefox.append("    $(\"#codigo\").val(); ");
        acaoLimparCamposFirefox.append("    $('#IDCONTRATO').val(\"\"); ");
        acaoLimparCamposFirefox.append("    jQuery(\"input[type='radio']\").attr(\"checked\",false); ");
        acaoLimparCamposFirefox.append("    limpaVinculacoes(); ");
        acaoLimparCamposFirefox.append("}");
        document.executeScript(acaoLimparCamposFirefox.toString());
    }

    private String getRequestedPath(final HttpServletRequest request) {
        String path = request.getRequestURI() + "?" + request.getQueryString();
        path = path.substring(request.getContextPath().length());
        return path;
    }

    public String getObjectName(final String path) {
        String strResult = "";
        boolean b = false;
        for (int i = path.length() - 1; i >= 0; i--) {
            if (b) {
                if (path.charAt(i) == '/') {
                    return strResult;
                } else {
                    strResult = path.charAt(i) + strResult;
                }
            } else {
                if (path.charAt(i) == '.') {
                    b = true;
                }
            }
        }
        return strResult;
    }

    public void setDadosTemporarios(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
        if (usuarioDto == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        final DinamicViewsDTO dinamicViewsDTO = (DinamicViewsDTO) document.getBean();
        request.getSession(true).setAttribute("tempData_" + usuarioDto.getIdUsuario() + "_" + dinamicViewsDTO.getKeyControl(), dinamicViewsDTO.getDinamicViewsJson_tempData());
    }

    public void recuperaVisaoFluxo(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final DinamicViewsDTO dinamicViewsDTO = (DinamicViewsDTO) document.getBean();

        if (dinamicViewsDTO.getIdTarefa() != null) {
            final Collection<GrupoVisaoCamposNegocioDTO> colFilter = this.findCamposTarefa(dinamicViewsDTO.getIdTarefa());
            if (colFilter != null) {
                final String metodoOrigem = "recuperaVisaoFluxo";
                this.restoreVisao(document, request, response, dinamicViewsDTO.getDinamicViewsIdVisao(), colFilter, metodoOrigem);
            }
        }
    }

    public VisaoDTO recuperaVisao(final Integer idVisao, final boolean comFilhos) throws ServiceException, Exception {
        final VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
        final GrupoVisaoService grupoVisaoService = (GrupoVisaoService) ServiceLocator.getInstance().getService(GrupoVisaoService.class, null);
        final GrupoVisaoCamposNegocioService grupoVisaoCamposNegocioService = (GrupoVisaoCamposNegocioService) ServiceLocator.getInstance().getService(
                GrupoVisaoCamposNegocioService.class, null);
        final CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
        final ValorVisaoCamposNegocioService valorVisaoCamposNegocioService = (ValorVisaoCamposNegocioService) ServiceLocator.getInstance().getService(
                ValorVisaoCamposNegocioService.class, null);
        final VisaoRelacionadaService visaoRelacionadaService = (VisaoRelacionadaService) ServiceLocator.getInstance().getService(VisaoRelacionadaService.class, null);
        final ScriptsVisaoService scriptsVisaoService = (ScriptsVisaoService) ServiceLocator.getInstance().getService(ScriptsVisaoService.class, null);
        final HtmlCodeVisaoService htmlCodeVisaoService = (HtmlCodeVisaoService) ServiceLocator.getInstance().getService(HtmlCodeVisaoService.class, null);

        VisaoDTO visaoDto = new VisaoDTO();
        visaoDto.setIdVisao(idVisao);
        visaoDto = (VisaoDTO) visaoService.restore(visaoDto);
        if (visaoDto == null) {
            return null;
        }

        final Collection colGrupos = grupoVisaoService.findByIdVisaoAtivos(idVisao);
        if (colGrupos != null) {
            for (final Iterator it = colGrupos.iterator(); it.hasNext();) {
                final GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
                grupoVisaoDTO.setColCamposVisao(grupoVisaoCamposNegocioService.findByIdGrupoVisaoAtivos(grupoVisaoDTO.getIdGrupoVisao()));

                if (grupoVisaoDTO.getColCamposVisao() != null) {
                    for (final Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
                        final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();

                        CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
                        camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                        camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioService.restore(camposObjetoNegocioDTO);

                        grupoVisaoCamposNegocioDTO.setCamposObjetoNegocioDto(camposObjetoNegocioDTO);

                        final Collection colValores = valorVisaoCamposNegocioService.findByIdGrupoVisaoAndIdCampoObjetoNegocio(grupoVisaoDTO.getIdGrupoVisao(),
                                grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                        grupoVisaoCamposNegocioDTO.setColValores(colValores);
                    }
                }
            }
        }
        visaoDto.setColGrupos(colGrupos);

        if (comFilhos) {
            final Collection colVisoesRelacionadas = visaoRelacionadaService.findByIdVisaoPaiAtivos(idVisao);
            visaoDto.setColVisoesRelacionadas(colVisoesRelacionadas);
            if (colVisoesRelacionadas != null) {
                for (final Iterator it = colVisoesRelacionadas.iterator(); it.hasNext();) {
                    final VisaoRelacionadaDTO visaoRelacionadaDto = (VisaoRelacionadaDTO) it.next();
                    if (visaoRelacionadaDto.getIdVisaoFilha() != null) {
                        final VisaoDTO visaoFilhaDTO = this.recuperaVisao(visaoRelacionadaDto.getIdVisaoFilha(), false);
                        if (visaoFilhaDTO != null) {
                            visaoFilhaDTO.setFilha(true);
                            visaoFilhaDTO.setAcaoVisaoFilhaPesqRelacionada(visaoRelacionadaDto.getAcaoEmSelecaoPesquisa());
                            visaoRelacionadaDto.setVisaoFilhaDto(visaoFilhaDTO);
                        }
                    }
                }
            }

            final Collection colScripts = scriptsVisaoService.findByIdVisao(idVisao);
            visaoDto.setColScripts(colScripts);
            HashMap map = new HashMap();
            if (colScripts != null) {
                for (final Iterator it = colScripts.iterator(); it.hasNext();) {
                    final ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO) it.next();
                    map.put(scriptsVisaoDTO.getTypeExecute() + "#" + scriptsVisaoDTO.getScryptType().trim(), scriptsVisaoDTO.getScript());
                }
            }
            visaoDto.setMapScripts(map);

            final Collection colHtmlCode = htmlCodeVisaoService.findByIdVisao(idVisao);
            visaoDto.setColHtmlCode(colHtmlCode);
            map = new HashMap();
            if (colHtmlCode != null) {
                for (final Iterator it = colHtmlCode.iterator(); it.hasNext();) {
                    final HtmlCodeVisaoDTO htmlCodeVisaoDTO = (HtmlCodeVisaoDTO) it.next();
                    map.put(htmlCodeVisaoDTO.getHtmlCodeType().trim(), htmlCodeVisaoDTO.getHtmlCode());
                }
            }
            visaoDto.setMapHtmlCodes(map);
        }

        if (visaoDto.getMapScripts() == null) {
            visaoDto.setMapScripts(new HashMap());
        }
        if (visaoDto.getMapHtmlCodes() == null) {
            visaoDto.setMapHtmlCodes(new HashMap());
        }

        return visaoDto;
    }

    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
        if (usuarioDto == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        try {
            final DinamicViewsDTO dinamicViewsDto = (DinamicViewsDTO) document.getBean();
            final DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance().getService(DinamicViewsService.class,
                    WebUtil.getUsuarioSistema(request));
            final Map hashValores = this.getFormFields(request);
            if (DEBUG) {
                this.debugValuesFromRequest(hashValores);
            }

            Map<String, Object> map = null;
            try {
                map = JSONUtil.convertJsonToMap(dinamicViewsDto.getDinamicViewsDadosAdicionais(), true);
            } catch (final Exception e) {
                LOGGER.error("dinamicViewsDto.getDinamicViewsDadosAdicionais(): " + dinamicViewsDto.getDinamicViewsDadosAdicionais(), e);
                document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
                throw e;
            }

            dinamicViewsDto.setDinamicViewsMapDadosAdicional(map);
            if (map != null) {
                hashValores.putAll(map);
            }

            hashValores.put("SESSION.DINAMICVIEWS_SAVEINFO", request.getSession().getAttribute("DinamicViews_SaveInfo"));
            hashValores.put("SESSION.NUMERO_CONTRATO_EDICAO", request.getSession().getAttribute("NUMERO_CONTRATO_EDICAO"));

            if (dinamicViewsDto.getJsonMatriz() != null && !dinamicViewsDto.getJsonMatriz().trim().equalsIgnoreCase("")) {
                dinamicViewsService.saveMatriz(usuarioDto, dinamicViewsDto, request);
            } else {
                dinamicViewsService.save(usuarioDto, dinamicViewsDto, hashValores, request);
            }
            if (dinamicViewsDto.getMsgRetorno() == null || dinamicViewsDto.getMsgRetorno().trim().equalsIgnoreCase("")) {
                document.alert(UtilI18N.internacionaliza(request, "dinamicview.gravadocomsucesso"));
                if (dinamicViewsDto.getIdFluxo() == null) {
                    document.executeScript("limpar()");
                } else {
                    document.executeScript("cancelar()");
                }
                document.executeScript("fecharSePOPUP()");
            } else {
                document.alert(dinamicViewsDto.getMsgRetorno());
            }
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
            final String msgErro = e.getMessage().replaceAll("java.lang.Exception:", "");
            document.alert(msgErro);
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }
    }

    private Map getFormFields(final HttpServletRequest req) {
        try {
            req.setCharacterEncoding("ISO-8859-1");
        } catch (final UnsupportedEncodingException e) {
            LOGGER.warn("PROBLEMA COM CODIFICACAO DE CARACTERES!!! [AjaxProcessEvent.getFormFields()]: " + e.getMessage(), e);
        }
        final Map formFields = new HashMap();
        final Enumeration en = req.getParameterNames();
        String[] strValores;
        while (en.hasMoreElements()) {
            final String nomeCampo = (String) en.nextElement();
            strValores = req.getParameterValues(nomeCampo);
            if (strValores.length == 0) {
                formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
            } else {
                if (strValores.length == 1) {
                    formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(Util.tratarAspasSimples(strValores[0])));
                } else {
                    formFields.put(nomeCampo.toUpperCase(), strValores);
                }
            }
        }
        return formFields;
    }

    public void delete(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ScriptsVisaoService scriptsVisaoService = (ScriptsVisaoService) ServiceLocator.getInstance().getService(ScriptsVisaoService.class, null);
        final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
        if (usuarioDto == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        final DinamicViewsDTO dinamicViewsDto = (DinamicViewsDTO) document.getBean();
        final DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance()
                .getService(DinamicViewsService.class, WebUtil.getUsuarioSistema(request));
        final ParserRequest parser = new ParserRequest();
        final Map hashValores = parser.getFormFields(request);
        if (DEBUG) {
            this.debugValuesFromRequest(hashValores);
        }

        final Map<String, Object> map = JSONUtil.convertJsonToMap(dinamicViewsDto.getDinamicViewsDadosAdicionais(), true);

        dinamicViewsDto.setDinamicViewsMapDadosAdicional(map);
        if (map != null) {
            hashValores.putAll(map);
        }

        // tratamento de deleção para a visão de contratos

        if (hashValores.containsKey("IDCONTRATO") && hashValores.containsKey("DATAFIMCONTRATO") && hashValores.containsKey("COTACAOMOEDA")) {
            final boolean defineExclusao = new ServicoContratoServiceEjb().pesquisaServicosVinculados(document, hashValores, request);
            if (!defineExclusao) {
                document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
                return;
            }
        }

        /**
         * Tratativa para exclusão de Requisitos de SLA
         *
         * @author thyen.chang
         */
        if (hashValores.containsKey("IDREQUISITOSLA") && hashValores.containsKey("REQUISITADOEM") && hashValores.get("IDREQUISITOSLA") != null
                && !((String) hashValores.get("IDREQUISITOSLA")).isEmpty()) {
            final boolean existeRelacionamento = new SlaRequisitoSlaServiceEjb().existeAcordoByRequisito(Integer.parseInt((String) hashValores.get("IDREQUISITOSLA")));
            if (existeRelacionamento) {
                document.alert(UtilI18N.internacionaliza(request, "dinamicview.existerequisitorelacionado"));
                document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
                return;
            }
        }

        final Collection colScripts = scriptsVisaoService.findByIdVisao(dinamicViewsDto.getDinamicViewsIdVisao());
        final HashMap mapScritps = new HashMap();
        if (colScripts != null) {
            for (final Iterator it = colScripts.iterator(); it.hasNext();) {
                final ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO) it.next();
                mapScritps.put(scriptsVisaoDTO.getTypeExecute() + "#" + scriptsVisaoDTO.getScryptType().trim(), scriptsVisaoDTO.getScript());
            }
        }
        final String strScript = (String) mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_ONDELETE.getName());

        if (StringUtils.isNotBlank(strScript)) {
            final ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
            final Context cx = Context.enter();
            final Scriptable scope = cx.initStandardObjects();
            scope.put("usuarioDto", scope, usuarioDto);
            scope.put("dinamicViewsDto", scope, dinamicViewsDto);
            scope.put("document", scope, document);
            scope.put("mapFields", scope, hashValores);
            scope.put("request", scope, request);
            scope.put("response", scope, response);
            scope.put("language", scope, WebUtil.getLanguage(request));
            scriptExecute.processScript(cx, scope, strScript, DinamicViews.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_ONDELETE.getName());
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }

        hashValores.put("REMOVED", "true");

        /*
         * Thiago Fernandes Oliveira - 28/10/2013 - Sol. 121468 - Só sera excluido algum registro caso ele tenha sido restaurado, evitando o cadastro de algum registro ao clicar em
         * excluir.
         * Modulos, Liberação, Mudança e Problema.
         */
        if (hashValores.containsKey("IDJUSTIFICATIVAMUDANCA") || hashValores.containsKey("IDJUSTIFICATIVALIBERACAO") || hashValores.containsKey("IDJUSTIFICATIVAPROBLEMA")) {
            if (hashValores.get("IDJUSTIFICATIVAMUDANCA") != null && !hashValores.get("IDJUSTIFICATIVAMUDANCA").equals("") || hashValores.get("IDJUSTIFICATIVALIBERACAO") != null
                    && !hashValores.get("IDJUSTIFICATIVALIBERACAO").equals("") || hashValores.get("IDJUSTIFICATIVAPROBLEMA") != null
                    && !hashValores.get("IDJUSTIFICATIVAPROBLEMA").equals("")) {

                dinamicViewsService.save(usuarioDto, dinamicViewsDto, hashValores, request); // A exclusão é sempre lógica.
                document.alert(UtilI18N.internacionaliza(request, "MSG07") + "!");
                if (dinamicViewsDto.getIdFluxo() == null) {
                    document.executeScript("limpar()");
                    document.executeScript("location.reload()");
                } else {
                    document.executeScript("cancelar()");
                }
                document.executeScript("fecharSePOPUP()");
                return;
            } else {
                document.alert(UtilI18N.internacionaliza(request, "jornadaTrabalho.necessarioSelecionarRegistro"));
                document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
                return;
            }
        }

        /*
         * Inserido para tratar quando não existe nenhum elemento a ser excluído.
         * Mário Júnior - 14/02/2014
         */
        final Collection colCamposPKPrincipal = new ArrayList();
        final Collection colCamposTodosPrincipal = new ArrayList();

        dinamicViewsService.setInfoSave(dinamicViewsDto.getDinamicViewsIdVisao(), colCamposPKPrincipal, colCamposTodosPrincipal);

        final boolean existeAlgumElemento = dinamicViewsService.isPKExists(colCamposPKPrincipal, hashValores);
        if (!existeAlgumElemento) {
            document.alert(UtilI18N.internacionaliza(request, "jornadaTrabalho.necessarioSelecionarRegistro"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        dinamicViewsService.save(usuarioDto, dinamicViewsDto, hashValores, request); // A exclusão é sempre lógica.

        /*
         * Rodrigo Pecci Acorse - 21/01/2014 15h30 - #131113
         * Utiliza os scripts da dinamic view e valida se a exclusão foi feita ou não.
         */
        if (dinamicViewsDto.getMsgRetorno() == null || dinamicViewsDto.getMsgRetorno().trim().equalsIgnoreCase("")) {
            document.alert(UtilI18N.internacionaliza(request, "MSG07") + "!");
        } else {
            document.alert(dinamicViewsDto.getMsgRetorno());
        }
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

        if (dinamicViewsDto.getIdFluxo() == null) {
            document.executeScript("limpar()");
            document.executeScript("location.reload()");
        } else {
            document.executeScript("cancelar()");
        }

        document.executeScript("fecharSePOPUP()");
    }

    public void restoreVisao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response, final Integer idVisao, final Collection colFilter,
            final String metodoOrigem) throws Exception {
        final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
        if (usuarioDto == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }
        final DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance().getService(DinamicViewsService.class, null);
        final ScriptsVisaoService scriptsVisaoService = (ScriptsVisaoService) ServiceLocator.getInstance().getService(ScriptsVisaoService.class, null);
        final Collection col = dinamicViewsService.restoreVisao(idVisao, colFilter);
        final HashMap map = new HashMap();
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it.next();
                final CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();

                if (camposObjetoNegocioDTO.getTipoDB().startsWith("NUMBER") && camposObjetoNegocioDTO.getPrecisionDB() == 0) {
                    if (camposObjetoNegocioDTO.getValue() instanceof BigDecimal) {
                        final BigDecimal aux = (BigDecimal) camposObjetoNegocioDTO.getValue();
                        camposObjetoNegocioDTO.setValue(new Integer(aux.intValue()));
                    }
                }

                map.put(camposObjetoNegocioDTO.getNomeDB(), camposObjetoNegocioDTO.getValue());

                if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
                    if (grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE)) {
                        if (camposObjetoNegocioDTO.getReturnLookupDTO() != null) {
                            map.put(camposObjetoNegocioDTO.getNomeDB() + "_label", camposObjetoNegocioDTO.getReturnLookupDTO().getLabel());
                        }
                    }
                }
            }
            final Collection colScripts = scriptsVisaoService.findByIdVisao(idVisao);
            final HashMap mapScritps = new HashMap();
            if (colScripts != null) {
                for (final Iterator it = colScripts.iterator(); it.hasNext();) {
                    final ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO) it.next();
                    mapScritps.put(scriptsVisaoDTO.getTypeExecute() + "#" + scriptsVisaoDTO.getScryptType().trim(), scriptsVisaoDTO.getScript());
                }
            }
            final String strScript = (String) mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_ONRESTORE.getName());
            if (StringUtils.isNotBlank(strScript)) {
                final ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
                final Context cx = Context.enter();
                final Scriptable scope = cx.initStandardObjects();
                scope.put("usuarioDto", scope, usuarioDto);
                scope.put("document", scope, document);
                scope.put("mapFields", scope, map);
                scope.put("request", scope, request);
                scope.put("response", scope, response);
                scope.put("language", scope, WebUtil.getLanguage(request));
                scriptExecute.processScript(cx, scope, strScript, DinamicViews.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_ONRESTORE.getName());
            }
            document.executeScript("try{limpar();}catch(e){}");
            this.setValuesFromMap(document, request, map, "document.form");
            document.getElementById("dinamicViewsIdVisao").setValue("" + idVisao);
            document.executeScript("try{$( '#tabs' ).tabs('select', 0);}catch(e){}");
            document.executeScript("carregaVinculacoes()");

            String modoExibicao = request.getParameter("modoExibicao");
            if (modoExibicao == null || modoExibicao.trim().length() == 0) {
                modoExibicao = "N";
            }

            if (!modoExibicao.equals("J")) {
                if (metodoOrigem == null || !metodoOrigem.equalsIgnoreCase("load")) {
                    document.executeScript("restore_scripts()");
                }
            } else {
                document.executeScript("restore_scripts()");
            }
        } else {
            document.alert(UtilI18N.internacionaliza(request, "dinamicview.naofoipossivelrecuperar"));
        }
    }

    /**
     * Converte e seta os valores registrados no HashMap para a grid
     *
     * @param document
     * @param request
     * @param map
     * @param formName
     */
    public void setValuesFromMap(final DocumentHTML document, final HttpServletRequest request, final Map map, final String formName) {
        for (final Iterator it = map.entrySet().iterator(); it.hasNext();) {
            final Map.Entry me = (Map.Entry) it.next();
            final Object valor = me.getValue();

            String property = (String) me.getKey();
            property = property.trim();

            String valorTransf = null;
            if (valor instanceof BigInteger) {
                valorTransf = UtilFormatacao.formatInt(((BigInteger) valor).intValue(), "################");
            }

            if (valor instanceof Long) {
                valorTransf = UtilFormatacao.formatInt(((Long) valor).intValue(), "################");
            }
            if (valor instanceof Integer) {
                valorTransf = UtilFormatacao.formatInt(((Integer) valor).intValue(), "################");
            }

            if (valor instanceof Float) {
                valorTransf = valor.toString();
            }

            if (valor instanceof Double) {
                valorTransf = UtilFormatacao.formatBigDecimal(new BigDecimal(((Double) valor).doubleValue()), 2);
            }
            if (valor instanceof BigDecimal) {
                if (property.startsWith("ID")) {
                    valorTransf = UtilFormatacao.formatBigDecimal((BigDecimal) valor, 2);
                    valorTransf = valorTransf.replaceAll(",00", "");
                } else {
                    valorTransf = UtilFormatacao.formatBigDecimal((BigDecimal) valor, 2);
                }
            }
            if (valor instanceof String) {
                valorTransf = (String) valor;
            }
            if (valor instanceof Date) {
                valorTransf = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, (Date) valor, WebUtil.getLanguage(request));
            }
            if (valor instanceof Timestamp) {
                valorTransf = UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, (Timestamp) valor, WebUtil.getLanguage(request));
            }

            if (valorTransf != null) {
                // #139700 - Correção da solicitação hack para tratamento do combogrid
                if (property.contains("label")) {
                    property = property.replace("_label", "");
                    document.executeScript("try{$('#" + property + "').combogrid('setValue', ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('"
                            + StringEscapeUtils.escapeJavaScript(valorTransf) + "')));}catch(e){}");
                    continue;
                }
                document.executeScript("HTMLUtils.setValue('" + property + "', ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('"
                        + StringEscapeUtils.escapeJavaScript(valorTransf) + "')), " + formName + ")");
            }
        }
    }

    public void tableSearchClick(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final DinamicViewsDTO dinamicViewsDTO = (DinamicViewsDTO) document.getBean();

        Map<String, Object> map = null;
        try {
            map = JSONUtil.convertJsonToMap(dinamicViewsDTO.getDinamicViewsJson_data(), true);
        } catch (final Exception e) {
            LOGGER.error("dinamicViewsDTO.getDinamicViewsJson_data(): " + dinamicViewsDTO.getDinamicViewsJson_data(), e);
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            throw e;
        }

        final VisaoDTO visaoPesquisaDto = this.recuperaVisao(dinamicViewsDTO.getDinamicViewsIdVisaoPesquisaSelecionada(), false);

        final Collection colFilter = new ArrayList();
        if (dinamicViewsDTO.getDinamicViewsAcaoPesquisaSelecionada() != null
                && dinamicViewsDTO.getDinamicViewsAcaoPesquisaSelecionada().equalsIgnoreCase(VisaoRelacionadaDTO.ACAO_RECUPERAR_PRINCIPAL)) {
            final Collection colGrupos = visaoPesquisaDto.getColGrupos();
            for (final Iterator it = colGrupos.iterator(); it.hasNext();) {
                final GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
                if (grupoVisaoDTO.getColCamposVisao() != null) {
                    for (final Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
                        if (map != null) {
                            final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();
                            if (grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().getPk().equalsIgnoreCase("S")) {
                                grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().setValue(map.get(grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().getNomeDB()));
                                colFilter.add(grupoVisaoCamposNegocioDTO);
                            }
                        }
                    }
                }
            }
            final String metodoOrigem = "tableSearchClick";
            this.restoreVisao(document, request, response, dinamicViewsDTO.getDinamicViewsIdVisao(), colFilter, metodoOrigem);
        }
    }

    public void tableEditClick(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final DinamicViewsDTO dinamicViewsDTO = (DinamicViewsDTO) document.getBean();

        final ColumnsDTO columnsDTO = GSON.fromJson(dinamicViewsDTO.getJsonDataEdit(), ColumnsDTO.class);

        final VisaoDTO visaoPesquisaDto = this.recuperaVisao(dinamicViewsDTO.getIdVisaoEdit(), false);

        final Collection colFilter = new ArrayList();
        final Collection colGrupos = visaoPesquisaDto.getColGrupos();
        int i = 1; // A primeira coluna (indice 0) é de controle do sistema.
        for (final Iterator it = colGrupos.iterator(); it.hasNext();) {
            final GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
            if (grupoVisaoDTO.getColCamposVisao() != null) {
                for (final Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
                    final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();

                    if (columnsDTO.getColuna() != null && columnsDTO.getColuna().length > i) {
                        if (grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().getPk().equalsIgnoreCase("S")) {
                            if (columnsDTO.getColuna()[i].equalsIgnoreCase("")) {
                                document.alert("Por favor, salve o serviço antes de restaurar o fluxo.");
                                return;
                            }
                            grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().setValue(columnsDTO.getColuna()[i]);
                            colFilter.add(grupoVisaoCamposNegocioDTO);
                        }
                    }
                    i++;
                }
            }
        }
        this.restoreVisaoEdit(document, request, response, dinamicViewsDTO.getIdVisaoEdit(), colFilter);
    }

    public void restoreVisaoEdit(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response, final Integer idVisao,
            final Collection colFilter) throws Exception {
        final DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance().getService(DinamicViewsService.class, null);
        final Collection col = dinamicViewsService.restoreVisao(idVisao, colFilter);
        final HashMap map = new HashMap();
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it.next();
                final CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();
                map.put(camposObjetoNegocioDTO.getNomeDB(), camposObjetoNegocioDTO.getValue());

                if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
                    if (grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE)) {
                        if (camposObjetoNegocioDTO.getReturnLookupDTO() != null) {
                            map.put(camposObjetoNegocioDTO.getNomeDB() + "_label", camposObjetoNegocioDTO.getReturnLookupDTO().getLabel());
                        }
                    }
                }
            }
            document.executeScript("limparForm(document.formEdit" + idVisao + ")");
            this.setValuesFromMap(document, request, map, "document.formEdit" + idVisao);
            document.executeScript("$('#TABLE_EDIT_" + idVisao + "' ).dialog( 'open' );");
        } else {
            document.alert(UtilI18N.internacionaliza(request, "dinamicview.naofoipossivelrecuperar"));
        }
    }

    public Collection<GrupoVisaoCamposNegocioDTO> findCamposTarefa(final Integer idTarefa) throws Exception {
        final ExecucaoSolicitacaoService execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
        return execucaoSolicitacaoService.findCamposTarefa(idTarefa);
    }

}
