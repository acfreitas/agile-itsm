package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ExternalConnectionDTO;
import br.com.centralit.citcorpore.bean.ImportConfigCamposDTO;
import br.com.centralit.citcorpore.bean.ImportConfigDTO;
import br.com.centralit.citcorpore.bean.ImportManagerDTO;
import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.util.JSONUtil;
import br.com.centralit.citcorpore.negocio.ExternalConnectionService;
import br.com.centralit.citcorpore.negocio.ImportConfigCamposService;
import br.com.centralit.citcorpore.negocio.ImportConfigService;
import br.com.centralit.citcorpore.negocio.ImportarDadosService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
public class ImportarDados extends AjaxFormAction {

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        if (!WebUtil.validarSeUsuarioEstaNaSessao(request, document)) {
            return;
        }

        // Popular combo Importar por
        this.popularComboImportarPor(document, request, response);

        // Popular combo Tipo
        this.popularComboTipo(document, request, response);

        // Popular combo Periodo/Horario
        this.popularComboPeriodo(document, request, response);

        // Iniciando combo das conexoes
        this.popularComboComAsConexoes(document, request, response);

        // Limpa anexos
        this.limparFormulario(document, request, response);

    }

    /**
     * Popular combo com as conexoes ativas
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     *
     */
    public void popularComboComAsConexoes(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        document.getSelectById("idExternalConnection").removeAllOptions();

        document.getSelectById("idExternalConnection").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);

        final Collection listaDeConexoes = externalConnectionService.listarAtivas();

        if (listaDeConexoes != null && !listaDeConexoes.isEmpty()) {

            ExternalConnectionDTO ec;

            for (final Iterator it = listaDeConexoes.iterator(); it.hasNext();) {

                ec = (ExternalConnectionDTO) it.next();

                document.getSelectById("idExternalConnection").addOption(ec.getIdExternalConnection().toString(), UtilI18N.internacionaliza(request, ec.getNome()));

            }

        }

    }

    /**
     * Popular combo do campo Importar Por
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     *
     */
    public void popularComboImportarPor(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        document.getSelectById("importarPor").removeAllOptions();

        document.getSelectById("importarPor").addOption("S", UtilI18N.internacionaliza(request, "importarDados.script"));
        document.getSelectById("importarPor").addOption("E", UtilI18N.internacionaliza(request, "visaoAdm.classeExterna"));
        document.getSelectById("importarPor").addOption("T", UtilI18N.internacionaliza(request, "importarDados.tabela"));

    }

    /**
     * Popular combo do campo Tipo
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     *
     */
    public void popularComboTipo(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        document.getSelectById("tipo").removeAllOptions();
        document.getSelectById("tipo").addOption("J", UtilI18N.internacionaliza(request, "importmanager.tipo.jdbc"));

    }

    /**
     * Popular combo do Periodo
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     *
     */
    public void popularComboPeriodo(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        document.getSelectById("executarPor").removeAllOptions();

        document.getSelectById("executarPor").addOption("P", UtilI18N.internacionaliza(request, "visao.periodo"));
        document.getSelectById("executarPor").addOption("H", UtilI18N.internacionaliza(request, "citcorpore.comum.horario"));

    }

    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        if (!WebUtil.validarSeUsuarioEstaNaSessao(request, document)) {
            return;
        }

        request.getSession(true).setAttribute("colUploadsGED", null);
        document.executeScript("uploadAnexos.refresh()");

        ImportarDadosDTO importarDadosDTO = (ImportarDadosDTO) document.getBean();
        final ImportarDadosService importarDadosService = (ImportarDadosService) ServiceLocator.getInstance().getService(ImportarDadosService.class, null);

        importarDadosDTO = (ImportarDadosDTO) importarDadosService.restore(importarDadosDTO);

        final HTMLForm form = document.getForm("form");
        form.clear();

        form.setValues(importarDadosDTO);

        if (importarDadosDTO != null && importarDadosDTO.getIdImportarDados() > 0) {

            if (importarDadosDTO.getAgendarRotina().equalsIgnoreCase("S")) {
                document.getCheckboxById("agendarRotina").setChecked(true);
            } else {
                document.getCheckboxById("agendarRotina").setChecked(false);
            }

            if (importarDadosDTO.getImportarPor().equalsIgnoreCase("E")) {
                this.restaurarAnexos(request, importarDadosDTO.getIdImportarDados());
            }

            if (importarDadosDTO.getImportarPor().equalsIgnoreCase("T")) {
                this.carregarTabelasRestore(importarDadosDTO, document, request, response);
                this.restoreImportManager(document, request, response, importarDadosDTO);
            }

        }

        document.executeScript("mostrarDiv();");
        document.executeScript("JANELA_AGUARDE_MENU.hide();");
        document.executeScript("agendamentoParaRotina();");
        document.executeScript("alterarExecutarPor();");

    }

    /**
     * Save
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        if (!WebUtil.validarSeUsuarioEstaNaSessao(request, document)) {
            return;
        }

        try {

            ImportarDadosDTO importarDadosDTO = (ImportarDadosDTO) document.getBean();

            if (importarDadosDTO.getAgendarRotina() == null || importarDadosDTO.getAgendarRotina().equals("")) {
                importarDadosDTO.setAgendarRotina("N");
                importarDadosDTO.setHoraExecucao(null);
                importarDadosDTO.setPeriodoHora(null);
            }

            final ImportarDadosService importarDadosService = (ImportarDadosService) ServiceLocator.getInstance().getService(ImportarDadosService.class, null);

            final Collection<UploadDTO> anexos = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
            importarDadosDTO.setAnexos(anexos);

            if (importarDadosDTO.getImportarPor().equals("E") && (anexos == null || anexos.isEmpty())) {
                document.alert(UtilI18N.internacionaliza(request, "MSG17"));
                return;
            }

            if (importarDadosDTO.getIdImportarDados() == null || importarDadosDTO.getIdImportarDados() <= 0) {

                importarDadosDTO = importarDadosService.create(importarDadosDTO);
                document.alert(UtilI18N.internacionaliza(request, "MSG05"));

            } else {
                importarDadosService.update(importarDadosDTO);
                document.alert(UtilI18N.internacionaliza(request, "MSG06"));
            }

            if (importarDadosDTO.getImportarPor().equalsIgnoreCase("T")) {

                // Consulta o importconfig
                final ImportConfigService importConfigService = (ImportConfigService) ServiceLocator.getInstance().getService(ImportConfigService.class, null);
                ImportConfigDTO importConfigDTO = importConfigService.consultarImportConfigDTO(importarDadosDTO);

                // Se existir um registro para o importconfig exclui para
                // cadastra-lo novamente
                if (importConfigDTO != null && importConfigDTO.getIdImportConfig() != null) {
                    this.excluirImportConfigEItens(importConfigDTO);
                }

                // Salvar registro ImportConfig
                importConfigDTO = new ImportConfigDTO();

                importConfigDTO.setIdImportarDados(importarDadosDTO.getIdImportarDados());
                importConfigDTO.setIdExternalConnection(importarDadosDTO.getIdExternalConnection());
                importConfigDTO.setNome(importarDadosDTO.getNome());
                importConfigDTO.setTabelaDestino(importarDadosDTO.getTabelaDestino());
                importConfigDTO.setTabelaOrigem(importarDadosDTO.getTabelaOrigem());
                importConfigDTO.setTipo(importarDadosDTO.getTipo());

                // Salvando importconfigcampos
                Map<String, Object> mapMatriz = null;

                final String jsonMatriz = importarDadosDTO.getJsonMatriz();

                if (jsonMatriz != null) {
                    mapMatriz = JSONUtil.convertJsonToMap(jsonMatriz, true);
                }

                if (mapMatriz != null && mapMatriz.size() > 0) {

                    final ArrayList colMatrizTratada = (ArrayList) mapMatriz.get("MATRIZ");
                    final Collection colDadosCampos = new ArrayList();
                    ImportConfigCamposDTO importConfigCamposDTO;

                    String idOrigem;
                    String idDestino;
                    String script;

                    for (final Iterator it = colMatrizTratada.iterator(); it.hasNext();) {

                        importConfigCamposDTO = new ImportConfigCamposDTO();
                        final HashMap mapItem = (HashMap) it.next();
                        idOrigem = (String) mapItem.get("CAMPOSTABELAORIGEM");
                        idDestino = (String) mapItem.get("CAMPOSTABELADESTINO");
                        script = (String) mapItem.get("SCRIPT");

                        importConfigCamposDTO.setOrigem(idOrigem);
                        importConfigCamposDTO.setDestino(idDestino);
                        importConfigCamposDTO.setScript(script);
                        importConfigCamposDTO.setIdImportarDados(importConfigDTO.getIdImportarDados());

                        colDadosCampos.add(importConfigCamposDTO);
                    }

                    importConfigDTO.setColDadosCampos(colDadosCampos);

                    importConfigService.create(importConfigDTO);

                }

            }

            this.load(document, request, response);
            document.executeScript("load()");

        } catch (final Exception e) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.dadosDaConexaoOuDosCamposInvalidos"));
        }

    }

    @Override
    public Class<ImportarDadosDTO> getBeanClass() {
        return ImportarDadosDTO.class;
    }

    /**
     * Delete
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void delete(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        if (!WebUtil.validarSeUsuarioEstaNaSessao(request, document)) {
            return;
        }

        ImportarDadosDTO importarDadosDTO = (ImportarDadosDTO) document.getBean();

        if (importarDadosDTO == null || importarDadosDTO.getIdImportarDados() == null) {

            document.alert(UtilI18N.internacionaliza(request, "importarDados.importacaoNaoSalvo"));
            return;
        }

        // Excluir logicamente o ImportarDados
        final ImportarDadosService importarDadosService = (ImportarDadosService) ServiceLocator.getInstance().getService(ImportarDadosService.class, null);

        importarDadosDTO = (ImportarDadosDTO) importarDadosService.restore(importarDadosDTO);

        importarDadosDTO.setDataFim(UtilDatas.getDataAtual());
        importarDadosService.update(importarDadosDTO);

        // Excluir logicamente o importManager
        final ImportConfigService importConfigService = (ImportConfigService) ServiceLocator.getInstance().getService(ImportConfigService.class, null);
        importConfigService.excluirRegistroLogicamente(importarDadosDTO.getIdImportarDados());

        document.alert(UtilI18N.internacionaliza(request, "MSG07"));

        this.limparFormulario(document, request, response);

    }

    /**
     * Limpa o formulario
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void limparFormulario(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        // Limpa grid anexos
        request.getSession(true).setAttribute("colUploadsGED", null);
        document.executeScript("uploadAnexos.refresh()");
        document.executeScript("mostrarDiv()");
        final HTMLForm form = document.getForm("form");
        form.clear();
        document.getElementById("agendarRotina").setValue("S");
        document.executeScript("alterarExecutarPor()");
        document.executeScript("gridImport.deleteAllRows();");

    }

    /**
     * Executar rotina
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void executarRotina(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        if (!WebUtil.validarSeUsuarioEstaNaSessao(request, document)) {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        final ImportarDadosDTO importarDadosDTO = (ImportarDadosDTO) document.getBean();

        if (importarDadosDTO == null || importarDadosDTO.getIdImportarDados() == null) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.salvarRegistroAntesDeExecutarRotina"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        if (importarDadosDTO == null || importarDadosDTO.getScript() == null) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.campoScriptObrigatorio"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }

        if (importarDadosDTO.getIdExternalConnection() == null) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.dadosDaConexaoOuDosCamposInvalidos"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        try {

            final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);

            if (importarDadosDTO.getImportarPor().equals("S")) {

                // Recuperar campos da conexão (ExternalConnectionDTO)
                ExternalConnectionDTO externalConnectionDTO = new ExternalConnectionDTO();
                externalConnectionDTO.setIdExternalConnection(importarDadosDTO.getIdExternalConnection());
                externalConnectionDTO = (ExternalConnectionDTO) externalConnectionService.restore(externalConnectionDTO);

                if (externalConnectionDTO == null || externalConnectionDTO.getIdExternalConnection() == null) {
                    document.alert(UtilI18N.internacionaliza(request, "importarDados.dadosDaConexaoOuDosCamposInvalidos"));
                    return;
                }

                final Context cx = Context.enter();
                final Scriptable scope = cx.initStandardObjects();

                final String conteudoScript = importarDadosDTO.getScript();

                final JdbcEngine jdbcEngine = new JdbcEngine("jdbc/citsmart", null);

                scope.put("jdbcEngine", scope, jdbcEngine);
                scope.put("url", scope, externalConnectionDTO.getUrlJdbc());
                scope.put("dbName", scope, externalConnectionDTO.getJdbcDbName());
                scope.put("driver", scope, externalConnectionDTO.getJdbcDriver());
                scope.put("password", scope, externalConnectionDTO.getJdbcPassword());
                scope.put("user", scope, externalConnectionDTO.getJdbcUser());
                scope.put("schema", scope, externalConnectionDTO.getSchemaDb());
                scope.put("dataAtualFormatada", scope, UtilDatas.getDataAtual().toLocaleString().replace("/", "-").replace(" 00:00:00", ""));
                scope.put("dataAtual", scope, UtilDatas.getDataAtual());

                final Object result = cx.evaluateString(scope, conteudoScript, "JavaScript", 0, null);

                System.out.println(result.toString());

            } else if (importarDadosDTO.getImportarPor().equals("E")) {

                final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
                final Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_IMPORTARDADOS, importarDadosDTO.getIdImportarDados());
                final Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

                ProcessBuilder pb;
                UploadDTO upload;
                final String caminho = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

                for (final Iterator<UploadDTO> it = colAnexosUploadDTO.iterator(); it.hasNext();) {

                    upload = it.next();

                    pb = new ProcessBuilder("java", "-jar", upload.getNameFile());

                    pb.directory(new File(caminho));
                    pb.start();

                }

            } else if (importarDadosDTO.getImportarPor().equals("T")) {

                this.carregarDados(document, request, response, importarDadosDTO);

            }

            document.alert(UtilI18N.internacionaliza(request, "importarDados.scriptExecutadoComSucesso"));

        } catch (final SQLException e) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.erroConexao"));
        } catch (final Exception e) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.scriptInvalido"));
        } finally {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }

    }

    /**
     * Restaurar Anexos
     *
     * @param request
     * @param idImportaDados
     * @throws ServiceException
     * @throws Exception
     */
    protected void restaurarAnexos(final HttpServletRequest request, final Integer idImportaDados) throws ServiceException, Exception {

        final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        final Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_IMPORTARDADOS, idImportaDados);
        final Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

        request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);

    }

    /**
     * Carrega as tabelas disponiveis na conexão externa e na conexão local
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void carregarTabelas(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        document.getSelectById("tabelaOrigem").removeAllOptions();
        document.getSelectById("tabelaDestino").removeAllOptions();

        document.getSelectById("tabelaOrigem").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        document.getSelectById("tabelaDestino").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        final ImportarDadosDTO importarDadosDTO = (ImportarDadosDTO) document.getBean();

        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);

        try {

            if (importarDadosDTO.getIdExternalConnection() != null) {

                // Buscar campos da Tabela Origem
                Collection colObjs = externalConnectionService.getTables(importarDadosDTO.getIdExternalConnection());
                if (colObjs != null) {

                    for (final Iterator it = colObjs.iterator(); it.hasNext();) {

                        final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
                        document.getSelectById("tabelaOrigem").addOption(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());

                    }
                }

                // Buscar campos da Tabela Destino
                colObjs = externalConnectionService.getLocalTables();

                if (colObjs != null) {

                    for (final Iterator it = colObjs.iterator(); it.hasNext();) {

                        final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
                        document.getSelectById("tabelaDestino").addOption(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());

                    }
                }

            }

        } catch (final Exception e) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.erroConexao"));
        } finally {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }
    }

    /**
     * Carrega as tabelas disponiveis na conexão externa e na conexão local no metodo restore
     * (sem a linha de comando ImportarDadosDTO importarDadosDTO = (ImportarDadosDTO) document.getBean() porque o objeto ja é passado como parametro no metodo)
     *
     * @param importarDadosDTO
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void carregarTabelasRestore(final ImportarDadosDTO importarDadosDTO, final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        document.getSelectById("tabelaOrigem").removeAllOptions();
        document.getSelectById("tabelaDestino").removeAllOptions();

        document.getSelectById("tabelaOrigem").addOption(importarDadosDTO.getTabelaOrigem(), importarDadosDTO.getTabelaDestino());
        document.getSelectById("tabelaDestino").addOption(importarDadosDTO.getTabelaOrigem(), importarDadosDTO.getTabelaDestino());

        /*
         * document.getSelectById("tabelaOrigem").addOption("",UtilI18N.internacionaliza(request,"citcorpore.comum.selecione"));
         * document.getSelectById("tabelaDestino").addOption("",UtilI18N.internacionaliza(request,"citcorpore.comum.selecione"));
         * ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
         * try {
         * if (importarDadosDTO != null && importarDadosDTO.getIdExternalConnection() != null) {
         * //Buscar campos da Tabela Origem
         * Collection colObjs = externalConnectionService.getTables(importarDadosDTO.getIdExternalConnection());
         * if (colObjs != null) {
         * for (Iterator it = colObjs.iterator(); it.hasNext();) {
         * ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
         * document.getSelectById("tabelaOrigem").addOption(objetoNegocioDTO.getNomeTabelaDB(),objetoNegocioDTO.getNomeTabelaDB());
         * }
         * }
         * // Buscar campos da Tabela Destino
         * colObjs = externalConnectionService.getLocalTables();
         * if (colObjs != null) {
         * for (Iterator it = colObjs.iterator(); it.hasNext();) {
         * ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
         * document.getSelectById("tabelaDestino").addOption(objetoNegocioDTO.getNomeTabelaDB(),objetoNegocioDTO.getNomeTabelaDB());
         * }
         * }
         * }
         * } catch (Exception e) {
         * document.alert(UtilI18N.internacionaliza(request,
         * "importarDados.erroConexao"));
         * } finally {
         * document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
         * }
         */
    }

    /**
     *
     * Consulta os campos da tabela origem
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void consultarCamposDaTabelaOrigem(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        final String seq = request.getParameter("seqGrid");

        document.getSelectById("camposTabelaOrigem").removeAllOptions();

        document.getSelectById("camposTabelaOrigem").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        document.getSelectById("camposTabelaOrigem" + seq).addOption("##AUTO##", "##AUTO##");

        final ImportarDadosDTO importarDadosDTO = (ImportarDadosDTO) document.getBean();

        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);

        try {

            // Buscar campos da Tabela Origem
            if (importarDadosDTO.getIdExternalConnection() != null) {

                final Collection colObjs = externalConnectionService.getFieldsTable(importarDadosDTO.getIdExternalConnection(), importarDadosDTO.getTabelaOrigem());
                if (colObjs != null && colObjs.size() > 0) {

                    CamposObjetoNegocioDTO camposObjetoNegocioDTO;

                    for (final Iterator it = colObjs.iterator(); it.hasNext();) {
                        camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
                        document.getSelectById("camposTabelaOrigem" + seq).addOption(camposObjetoNegocioDTO.getNome(), camposObjetoNegocioDTO.getNome());
                    }
                }

            }

        } catch (final Exception e) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.camposTabelaInvalida"));
        } finally {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }
    }

    /**
     *
     * Busca os campos da tabela destino
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void consultarCamposDaTabelaDestino(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        final String seq = request.getParameter("seqGrid");

        document.getSelectById("camposTabelaDestino" + seq).removeAllOptions();

        final ImportarDadosDTO importarDadosDTO = (ImportarDadosDTO) document.getBean();

        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);

        try {

            // Buscar campos da Tabela Origem
            if (importarDadosDTO.getIdExternalConnection() != null) {

                final Collection colObjs = externalConnectionService.getFieldsLocalTable(importarDadosDTO.getTabelaDestino());

                if (colObjs != null && colObjs.size() > 0) {

                    CamposObjetoNegocioDTO camposObjetoNegocioDTO;

                    for (final Iterator it = colObjs.iterator(); it.hasNext();) {

                        camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
                        document.getSelectById("camposTabelaDestino" + seq).addOption(camposObjetoNegocioDTO.getNome(), camposObjetoNegocioDTO.getNome());

                    }
                }

            }

        } catch (final Exception e) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.camposTabelaInvalida"));
        } finally {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }
    }

    /**
     *
     * Consulta os campos da tabela origem no metodo restore
     *
     * @param document
     * @param request
     * @param response
     * @param importarDadosDTO
     * @throws Exception
     */
    public void consultarCamposDaTabelaOrigemNoRestore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final ImportarDadosDTO importarDadosDTO) throws Exception {

        final String seq = (String) request.getSession().getValue("seqGrid");

        document.getSelectById("camposTabelaOrigem" + seq).removeAllOptions();

        document.getSelectById("camposTabelaOrigem" + seq).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        document.getSelectById("camposTabelaOrigem" + seq).addOption("##AUTO##", "##AUTO##");

        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);

        try {

            // Buscar campos da Tabela Origem
            if (importarDadosDTO.getIdExternalConnection() != null) {

                final Collection colObjs = externalConnectionService.getFieldsTable(importarDadosDTO.getIdExternalConnection(), importarDadosDTO.getTabelaOrigem());
                if (colObjs != null && colObjs.size() > 0) {

                    CamposObjetoNegocioDTO camposObjetoNegocioDTO;

                    for (final Iterator it = colObjs.iterator(); it.hasNext();) {

                        camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
                        document.getSelectById("camposTabelaOrigem" + seq).addOption(camposObjetoNegocioDTO.getNome(), camposObjetoNegocioDTO.getNome());

                    }
                }
            }

        } catch (final Exception e) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.camposTabelaInvalida"));
        } finally {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }
    }

    /**
     *
     * Consulta os campos da tabela destino no metodo restore
     *
     * @param document
     * @param request
     * @param response
     * @param importarDadosDTO
     * @throws Exception
     */
    public void consultarCamposDaTabelaDestinoNoRestore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final ImportarDadosDTO importarDadosDTO) throws Exception {

        final String seq = (String) request.getSession().getValue("seqGrid");

        document.getSelectById("camposTabelaDestino" + seq).removeAllOptions();

        document.getSelectById("camposTabelaDestino" + seq).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);

        try {

            // Buscar campos da Tabela Origem
            if (importarDadosDTO.getIdExternalConnection() != null) {

                final Collection colObjs = externalConnectionService.getFieldsLocalTable(importarDadosDTO.getTabelaDestino());

                if (colObjs != null && colObjs.size() > 0) {

                    CamposObjetoNegocioDTO camposObjetoNegocioDTO;
                    for (final Iterator it = colObjs.iterator(); it.hasNext();) {

                        camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
                        document.getSelectById("camposTabelaDestino" + seq).addOption(camposObjetoNegocioDTO.getNome(), camposObjetoNegocioDTO.getNome());

                    }
                }
            }

        } catch (final Exception e) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.camposTabelaInvalida"));
        } finally {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }
    }

    /**
     *
     * Carregar dados cadastrados na tela para executar a rotina no modo Tabela
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void carregarDados(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response, final ImportarDadosDTO importarDadosDTO)
            throws Exception {
        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
        final String jsonMatriz = importarDadosDTO.getJsonMatriz();
        final Map<String, Object> mapMatriz = JSONUtil.convertJsonToMap(
                jsonMatriz.toString().replace("camposTabelaOrigem", "IDORIGEM").replace("camposTabelaDestino", "IDDESTINO"), true);

        final List colMatrizTratada = (List) mapMatriz.get("MATRIZ");

        final ImportManagerDTO importManagerDTO = new ImportManagerDTO();

        importManagerDTO.setIdImportarDados(importarDadosDTO.getIdImportarDados());
        importManagerDTO.setIdExternalConnection(importarDadosDTO.getIdExternalConnection());
        importManagerDTO.setJsonMatriz(importarDadosDTO.getJsonMatriz());
        importManagerDTO.setNome(importarDadosDTO.getNome());
        importManagerDTO.setTabelaDestino(importarDadosDTO.getTabelaDestino());
        importManagerDTO.setTabelaOrigem(importarDadosDTO.getTabelaOrigem());
        importManagerDTO.setTipo(importarDadosDTO.getTipo());

        externalConnectionService.processImport(importManagerDTO, colMatrizTratada);
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    /**
     *
     * Exclui o registro da tabela importconfig e seus subtitens
     *
     * @param importConfigDTO
     * @throws ServiceException
     * @throws Exception
     */
    public void excluirImportConfigEItens(final ImportConfigDTO importConfigDTO) throws ServiceException, Exception {
        final ImportConfigService importConfigService = (ImportConfigService) ServiceLocator.getInstance().getService(ImportConfigService.class, null);
        importConfigService.excluirRegistroESubItens(importConfigDTO);
    }

    /**
     *
     * Restaura os itens do importmanager no restore
     *
     * @param document
     * @param request
     * @param response
     * @param importarDadosDTO
     */
    public void restoreImportManager(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response, final ImportarDadosDTO importarDadosDTO) {
        try {
            final ImportConfigCamposService importConfigCamposService = (ImportConfigCamposService) ServiceLocator.getInstance().getService(ImportConfigCamposService.class, null);
            final Collection<ImportConfigCamposDTO> campos = importConfigCamposService.findByIdImportarDados(importarDadosDTO);

            document.executeScript("gridImport.deleteAllRows();");
            String seq;

            if (campos != null) {

                int i = 1;
                for (final ImportConfigCamposDTO importConfigCamposDTO : campos) {

                    document.executeScript("gridImport.addRow();");
                    seq = String.format("%05d", i);
                    request.getSession().setAttribute("seqGrid", seq);

                    // Busca os valores nas combos
                    this.consultarCamposDaTabelaOrigemNoRestore(document, request, response, importarDadosDTO);
                    this.consultarCamposDaTabelaDestinoNoRestore(document, request, response, importarDadosDTO);

                    // Seta os valores na combo
                    document.getSelectById("camposTabelaOrigem" + seq).setValue(importConfigCamposDTO.getOrigem());
                    document.getSelectById("camposTabelaDestino" + seq).setValue(importConfigCamposDTO.getDestino());
                    document.getSelectById("script" + seq).setValue(importConfigCamposDTO.getScript());

                    i++;

                }
            }

        } catch (final Exception e) {
            document.alert(UtilI18N.internacionaliza(request, "importarDados.scriptInvalido"));
        }

    }

    /**
     * Validação quando a conexão for alterada e existir campos cadastrados
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void validarAlteracaoDaConexao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        final ImportarDadosDTO importarDadosDTO = (ImportarDadosDTO) document.getBean();

        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);

        final String valorAntigoTabela = document.getSelectById("tabelaOrigem").getValue();
        final String valorAntigoConexao = request.getParameter("valorDaConexaoAntiga");

        try {

            if (importarDadosDTO.getIdExternalConnection() != null) {

                // Buscar campos da Tabela Origem
                final Collection colObjs = externalConnectionService.getTables(importarDadosDTO.getIdExternalConnection());
                if (colObjs != null) {

                    ObjetoNegocioDTO objetoNegocioDTO;
                    for (final Iterator it = colObjs.iterator(); it.hasNext();) {

                        objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
                        if (valorAntigoTabela.equals(objetoNegocioDTO.getNomeTabelaDB())) {

                            document.getSelectById("tabelaOrigem").removeAllOptions();
                            document.getSelectById("tabelaOrigem").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

                            // Buscar campos da Tabela Origem
                            final Collection colObjs2 = externalConnectionService.getTables(importarDadosDTO.getIdExternalConnection());
                            if (colObjs2 != null) {

                                for (final Iterator it2 = colObjs2.iterator(); it2.hasNext();) {

                                    objetoNegocioDTO = (ObjetoNegocioDTO) it2.next();
                                    document.getSelectById("tabelaOrigem").addOption(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());

                                }

                                document.getSelectById("tabelaOrigem").setValue(valorAntigoTabela);
                                return;

                            }
                        }
                    }
                }
            }

            document.getSelectById("idExternalConnection").setValue(valorAntigoConexao);
            document.alert(UtilI18N.internacionaliza(request, "conexaoBI.testeConexaoErroConexaoNaoEstabelecida"));

        } catch (final Exception e) {

            document.getSelectById("idExternalConnection").setValue(valorAntigoConexao);
            document.alert(UtilI18N.internacionaliza(request, "importarDados.erroConexao"));

        } finally {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }
    }

}
