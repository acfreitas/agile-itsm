package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citcorpore.bean.MatrizVisaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MatrizVisaoDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.DinamicViewsDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.LookupDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ReturnLookupDTO;
import br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VinculoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO;
import br.com.centralit.citcorpore.metainfo.integracao.CamposObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoCamposNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.GrupoVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.ObjetoNegocioDao;
import br.com.centralit.citcorpore.metainfo.integracao.ScriptsVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.VinculoVisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoRelacionadaDao;
import br.com.centralit.citcorpore.metainfo.script.ScriptRhinoJSExecute;
import br.com.centralit.citcorpore.metainfo.util.HashMapUtil;
import br.com.centralit.citcorpore.metainfo.util.JSONUtil;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.metainfo.util.RuntimeScript;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.integracao.PersistenceEngine;
import br.com.citframework.integracao.RegistraLogDinamicView;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes", "unchecked"})
public class DinamicViewsServiceEjb extends CrudServiceImpl implements DinamicViewsService {

    private static final Logger LOGGER = Logger.getLogger(DinamicViewsServiceEjb.class);

    private VisaoDao dao;

    @Override
    protected VisaoDao getDao() {
        if (dao == null) {
            dao = new VisaoDao();
        }
        return dao;
    }

    /**
     * TRATAMENTO DE MATRIZ Este metodo trata do motor do sistema dinamico de gravacao de dados de visoes (montadas dinamicamente)
     */
    @Override
    public void saveMatriz(final UsuarioDTO usuarioDto, final DinamicViewsDTO dinamicViewDto, final HttpServletRequest request) throws Exception {
        final String jsonMatriz = dinamicViewDto.getJsonMatriz();
        Map<String, Object> mapMatriz = null;
        try {
            mapMatriz = JSONUtil.convertJsonToMap(jsonMatriz, true);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            LOGGER.debug("jsonMatriz: " + jsonMatriz);
            throw e;
        }
        final Collection colCamposPKPrincipal = new ArrayList();
        final Collection colCamposTodosPrincipal = new ArrayList();
        final List colMatrizTratada = (List) mapMatriz.get("MATRIZ");
        if (colMatrizTratada != null) {
            final Integer idVisao = dinamicViewDto.getDinamicViewsIdVisao();
            this.setInfoSave(idVisao, colCamposPKPrincipal, colCamposTodosPrincipal);
            CamposObjetoNegocioDTO camposObjetoNegocioDTO = null;
            if (colCamposPKPrincipal != null && colCamposPKPrincipal.size() > 0) {
                camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) colCamposPKPrincipal.iterator().next();
            }
            if (camposObjetoNegocioDTO == null) {
                throw new Exception("Problema ao obter a chave da VISAO!");
            }
            for (final Iterator it = colMatrizTratada.iterator(); it.hasNext();) {
                final HashMap mapItem = (HashMap) it.next();
                mapItem.put(camposObjetoNegocioDTO.getNomeDB(), mapItem.get("FLD_0"));
                LOGGER.debug("Processando... " + mapItem);
                this.save(usuarioDto, dinamicViewDto, mapItem, request);
            }
        }
    }

    /**
     * Este metodo trata do motor do sistema dinamico de gravacao de dados de visoes (montadas dinamicamente)
     */
    @Override
    public void save(final UsuarioDTO usuarioDto, final DinamicViewsDTO dinamicViewDto, final Map map, final HttpServletRequest request) throws Exception {
        final VisaoRelacionadaDao visaoRelacionadaDao = new VisaoRelacionadaDao();
        final GrupoVisaoDao grupoVisaoDao = new GrupoVisaoDao();
        final GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = new GrupoVisaoCamposNegocioDao();
        final CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
        final VinculoVisaoDao vinculoVisaoDao = new VinculoVisaoDao();
        final ScriptsVisaoDao scriptsVisaoDao = new ScriptsVisaoDao();
        final MatrizVisaoDao matrizVisaoDao = new MatrizVisaoDao();
        final VisaoDao visaoDao = this.getDao();
        final TransactionControler tc = this.getDao().getTransactionControler();

        visaoRelacionadaDao.setTransactionControler(tc);
        grupoVisaoDao.setTransactionControler(tc);
        grupoVisaoCamposNegocioDao.setTransactionControler(tc);
        camposObjetoNegocioDao.setTransactionControler(tc);
        vinculoVisaoDao.setTransactionControler(tc);
        scriptsVisaoDao.setTransactionControler(tc);
        matrizVisaoDao.setTransactionControler(tc);

        final Integer idVisao = dinamicViewDto.getDinamicViewsIdVisao();
        final Collection colScripts = scriptsVisaoDao.findByIdVisao(idVisao);
        final HashMap mapScritps = new HashMap();
        if (colScripts != null) {
            for (final Iterator it = colScripts.iterator(); it.hasNext();) {
                final ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO) it.next();
                mapScritps.put(scriptsVisaoDTO.getTypeExecute() + "#" + scriptsVisaoDTO.getScryptType().trim(), scriptsVisaoDTO.getScript());
            }
        }

        final Collection colCamposPKPrincipal = new ArrayList();
        final Collection colCamposTodosPrincipal = new ArrayList();
        Collection colCamposTodosVinc = null;
        CamposObjetoNegocioDTO camposObjetoNegocioChaveMatriz = new CamposObjetoNegocioDTO();

        this.setInfoSave(idVisao, colCamposPKPrincipal, colCamposTodosPrincipal);

        final Collection colVisoesRelacionadas = visaoRelacionadaDao.findByIdVisaoPaiAtivos(idVisao);

        try {
            tc.start();

            if (this.isPKExists(colCamposPKPrincipal, map)) {
                String strScript = (String) mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_ONUPDATE.getName());
                if (strScript != null && !strScript.trim().equalsIgnoreCase("")) {
                    final ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
                    final RuntimeScript runtimeScript = new RuntimeScript();
                    final Context cx = Context.enter();
                    final Scriptable scope = cx.initStandardObjects();
                    scope.put("mapFields", scope, map);
                    final String action = "UPDATE";
                    scope.put("ACTION", scope, action);
                    scope.put("userLogged", scope, usuarioDto);
                    scope.put("transactionControler", scope, tc);
                    scope.put("dinamicViewDto", scope, dinamicViewDto);
                    scope.put("RuntimeScript", scope, runtimeScript);
                    scope.put("language", scope, WebUtil.getLanguage(request));
                    scriptExecute.processScript(cx, scope, strScript, VisaoServiceEjb.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_ONUPDATE.getName());
                }
                if (!dinamicViewDto.isAbortFuncaoPrincipal()) {
                    this.updateFromMap(map, colCamposTodosPrincipal, usuarioDto, visaoDao, request);
                    strScript = (String) mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_AFTERUPDATE.getName());
                    if (strScript != null && !strScript.trim().equalsIgnoreCase("")) {
                        final ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
                        final RuntimeScript runtimeScript = new RuntimeScript();
                        final Context cx = Context.enter();
                        final Scriptable scope = cx.initStandardObjects();
                        scope.put("mapFields", scope, map);
                        final String action = "UPDATE";
                        scope.put("ACTION", scope, action);
                        scope.put("userLogged", scope, usuarioDto);
                        scope.put("transactionControler", scope, tc);
                        scope.put("dinamicViewDto", scope, dinamicViewDto);
                        scope.put("RuntimeScript", scope, runtimeScript);
                        scope.put("language", scope, WebUtil.getLanguage(request));
                        scriptExecute.processScript(cx, scope, strScript, VisaoServiceEjb.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_AFTERUPDATE.getName());
                    }
                }
            } else {
                String strScript = (String) mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_ONCREATE.getName());
                if (strScript != null && !strScript.trim().equalsIgnoreCase("")) {
                    final ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
                    final RuntimeScript runtimeScript = new RuntimeScript();
                    final Context cx = Context.enter();
                    final Scriptable scope = cx.initStandardObjects();
                    scope.put("mapFields", scope, map);
                    final String action = "CREATE";
                    scope.put("ACTION", scope, action);
                    scope.put("userLogged", scope, usuarioDto);
                    scope.put("transactionControler", scope, tc);
                    scope.put("dinamicViewDto", scope, dinamicViewDto);
                    scope.put("RuntimeScript", scope, runtimeScript);
                    scope.put("language", scope, WebUtil.getLanguage(request));
                    scriptExecute.processScript(cx, scope, strScript, VisaoServiceEjb.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_ONCREATE.getName());
                }
                if (!dinamicViewDto.isAbortFuncaoPrincipal()) {
                    this.createFromMap(map, colCamposTodosPrincipal, usuarioDto, visaoDao, request);
                    strScript = (String) mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_AFTERCREATE.getName());
                    if (strScript != null && !strScript.trim().equalsIgnoreCase("")) {
                        final ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
                        final RuntimeScript runtimeScript = new RuntimeScript();
                        final Context cx = Context.enter();
                        final Scriptable scope = cx.initStandardObjects();
                        scope.put("mapFields", scope, map);
                        final String action = "CREATE";
                        scope.put("ACTION", scope, action);
                        scope.put("userLogged", scope, usuarioDto);
                        scope.put("transactionControler", scope, tc);
                        scope.put("dinamicViewDto", scope, dinamicViewDto);
                        scope.put("RuntimeScript", scope, runtimeScript);
                        scope.put("language", scope, WebUtil.getLanguage(request));
                        scriptExecute.processScript(cx, scope, strScript, VisaoServiceEjb.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_AFTERCREATE.getName());
                    }
                }
            }
            if (colVisoesRelacionadas != null) {
                for (final Iterator it = colVisoesRelacionadas.iterator(); it.hasNext();) {
                    final VisaoRelacionadaDTO visaoRelacionadaDto = (VisaoRelacionadaDTO) it.next();
                    final Collection colVinculos = vinculoVisaoDao.findByIdVisaoRelacionada(visaoRelacionadaDto.getIdVisaoRelacionada());

                    final Object objFromHash = map.get(VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA + visaoRelacionadaDto.getIdVisaoFilha());
                    VisaoDTO visaoDtoAux = new VisaoDTO();
                    visaoDtoAux.setIdVisao(visaoRelacionadaDto.getIdVisaoFilha());
                    visaoDtoAux = (VisaoDTO) visaoDao.restore(visaoDtoAux);
                    MatrizVisaoDTO matrizVisaoDTO = new MatrizVisaoDTO();
                    boolean ehMatriz = false;
                    if (visaoDtoAux != null) {
                        if (visaoDtoAux.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
                            ehMatriz = true;
                            matrizVisaoDTO.setIdVisao(visaoDtoAux.getIdVisao());
                            final Collection colMatriz = matrizVisaoDao.findByIdVisao(visaoDtoAux.getIdVisao());
                            if (colMatriz != null && colMatriz.size() > 0) {
                                matrizVisaoDTO = (MatrizVisaoDTO) colMatriz.iterator().next();
                                camposObjetoNegocioChaveMatriz.setIdCamposObjetoNegocio(matrizVisaoDTO.getIdCamposObjetoNegocio1());
                                camposObjetoNegocioChaveMatriz.setIdObjetoNegocio(matrizVisaoDTO.getIdObjetoNegocio());
                                camposObjetoNegocioChaveMatriz = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioChaveMatriz);
                            }
                        }
                    }

                    if (HashMap.class.isInstance(objFromHash)) {
                        final HashMap mapVinc = (HashMap) objFromHash;
                        if (mapVinc != null) { // Se existir dados recebidos.
                            final Collection colCamposPKVinc = new ArrayList();
                            colCamposTodosVinc = new ArrayList();
                            this.setInfoSave(visaoRelacionadaDto.getIdVisaoFilha(), colCamposPKVinc, colCamposTodosVinc);
                            // Grava os dados de informacoes vinculadas.
                            if (this.isPKExists(colCamposPKVinc, mapVinc)) {
                                this.updateFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao, request);
                            } else {
                                this.createFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao, request);
                            }
                        }
                    } else if (Collection.class.isInstance(objFromHash)) {
                        final Collection colVinc = (Collection) objFromHash;
                        if (colVinc != null) {
                            for (final Iterator it2 = colVinc.iterator(); it2.hasNext();) {
                                Map mapVinc = (Map) it2.next();
                                if (mapVinc != null) { // Se existir dados recebidos.
                                    final Collection colCamposPKVinc = new ArrayList();
                                    colCamposTodosVinc = new ArrayList();
                                    this.setInfoSave(visaoRelacionadaDto.getIdVisaoFilha(), colCamposPKVinc, colCamposTodosVinc);// *****
                                    String tipoVinc = "";
                                    if (colVinculos != null && colVinculos.size() > 0) {
                                        final VinculoVisaoDTO vinculoVisaoDTO = (VinculoVisaoDTO) ((List) colVinculos).get(0);
                                        tipoVinc = vinculoVisaoDTO.getTipoVinculo();
                                    }
                                    if (ehMatriz) {
                                        if (camposObjetoNegocioChaveMatriz != null) {
                                            mapVinc.put(camposObjetoNegocioChaveMatriz.getNomeDB(), mapVinc.get("FLD_0"));
                                        }
                                        CamposObjetoNegocioDTO camposObjetoNegocioDTO = null;
                                        if (colCamposPKVinc != null && colCamposPKVinc.size() > 0) {
                                            for (final Iterator itVinc = colCamposPKVinc.iterator(); itVinc.hasNext();) {
                                                camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) itVinc.next();
                                                if (!camposObjetoNegocioDTO.getNomeDB().trim().equalsIgnoreCase(camposObjetoNegocioChaveMatriz.getNomeDB().trim())) {
                                                    mapVinc.put(camposObjetoNegocioDTO.getNomeDB(), map.get(camposObjetoNegocioDTO.getNomeDB()));
                                                }
                                            }
                                        }
                                        if (tipoVinc == null || tipoVinc.equalsIgnoreCase("")) {
                                            tipoVinc = VinculoVisaoDTO.VINCULO_1_TO_N;
                                        }
                                    }
                                    if (tipoVinc.equalsIgnoreCase(VinculoVisaoDTO.VINCULO_N_TO_N)) {
                                        // Grava os dados de informacoes vinculadas.
                                        if (this.isPKExists(colCamposPKVinc, mapVinc)) {
                                            this.updateFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao, request);
                                        } else {
                                            this.createFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao, request);
                                        }
                                        this.processCreateVinc(visaoRelacionadaDto, colVinculos, map, mapVinc, usuarioDto, request);
                                    }
                                    if (tipoVinc.equalsIgnoreCase(VinculoVisaoDTO.VINCULO_1_TO_N)) {
                                        mapVinc = this.createUniqueMap(map, mapVinc);// ******
                                        // Grava os dados de informacoes vinculadas.
                                        if (this.isPKExists(colCamposPKVinc, mapVinc)) {
                                            this.updateFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao, request);
                                        } else {
                                            this.createFromMap(mapVinc, colCamposTodosVinc, usuarioDto, visaoDao, request);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            if (dinamicViewDto.getIdFluxo() != null || dinamicViewDto.getIdTarefa() != null) {
                new ExecucaoSolicitacaoServiceEjb().executa(usuarioDto, tc, dinamicViewDto.getIdFluxo(), dinamicViewDto.getIdTarefa(), dinamicViewDto.getAcaoFluxo(), map,
                        colCamposTodosPrincipal, colCamposTodosVinc);
            }

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    private Map createUniqueMap(final Map mapSrc, final Map mapDest) {
        final Set set = mapSrc.entrySet();
        final Iterator i = set.iterator();
        while (i.hasNext()) {
            final Map.Entry me = (Map.Entry) i.next();
            final String str1 = (String) me.getKey();
            if (!str1.equalsIgnoreCase("REMOVED")) { // Mantem a opcao REMOVED para a visao original.
                mapDest.get(me.getKey());
                mapDest.put(me.getKey(), me.getValue());
            }
        }
        return mapDest;
    }

    public void processCreateVinc(final VisaoRelacionadaDTO visaoRelacionadaDto, final Collection colVinculos, final Map mapPai, final Map mapVinc, final UsuarioDTO usuarioDto,
            final HttpServletRequest request) throws Exception {
        final CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
        if (colVinculos != null) {
            final Collection colCamposVinc = new ArrayList();
            final Map mapNew = new HashMap();
            for (final Iterator itVinculos = colVinculos.iterator(); itVinculos.hasNext();) {
                final VinculoVisaoDTO vinculoVisaoDTO = (VinculoVisaoDTO) itVinculos.next();
                if (vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN() != null) {
                    CamposObjetoNegocioDTO camposObjetoNegocioDTOPai = new CamposObjetoNegocioDTO();
                    camposObjetoNegocioDTOPai.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN());
                    camposObjetoNegocioDTOPai = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTOPai);
                    final String valuePai = (String) mapPai.get(camposObjetoNegocioDTOPai.getNomeDB().trim().toUpperCase());
                    mapNew.put(camposObjetoNegocioDTOPai.getNomeDB().trim().toUpperCase(), valuePai);
                    camposObjetoNegocioDTOPai.setSequence("N");
                    colCamposVinc.add(camposObjetoNegocioDTOPai);
                }

                if (vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN() != null) {
                    CamposObjetoNegocioDTO camposObjetoNegocioDTOFilho = new CamposObjetoNegocioDTO();
                    camposObjetoNegocioDTOFilho.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN());
                    camposObjetoNegocioDTOFilho = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTOFilho);

                    final String valueFilho = (String) mapVinc.get(camposObjetoNegocioDTOFilho.getNomeDB().trim().toUpperCase());
                    mapNew.put(camposObjetoNegocioDTOFilho.getNomeDB().trim().toUpperCase(), valueFilho);
                    camposObjetoNegocioDTOFilho.setSequence("N");
                    colCamposVinc.add(camposObjetoNegocioDTOFilho);
                }
            }
            if (colCamposVinc != null && colCamposVinc.size() > 0) {
                if (!this.isPKExists(colCamposVinc, mapNew)) {
                    this.createFromMap(mapNew, colCamposVinc, usuarioDto, this.getDao(), request);
                }
            }
        }
    }

    /**
     * Seta informacoes importantes para a gravacao dados. As informacoes serao preenchidas nas collections: colCamposPK, colCamposTodos
     *
     * @param colGrupos
     * @param colCamposPK
     * @param colCamposTodos
     * @throws Exception
     */
    @Override
    public void setInfoSave(final Integer idVisao, final Collection colCamposPK, final Collection colCamposTodos) throws Exception {
        final GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = new GrupoVisaoCamposNegocioDao();
        final CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
        final GrupoVisaoDao grupoVisaoDao = new GrupoVisaoDao();

        final Collection colGrupos = grupoVisaoDao.findByIdVisaoAtivos(idVisao);
        if (colGrupos != null) {
            for (final Iterator it = colGrupos.iterator(); it.hasNext();) {
                final GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
                grupoVisaoDTO.setColCamposVisao(grupoVisaoCamposNegocioDao.findByIdGrupoVisaoAtivos(grupoVisaoDTO.getIdGrupoVisao()));

                if (grupoVisaoDTO.getColCamposVisao() != null) {
                    for (final Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
                        final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();

                        CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
                        camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                        camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);

                        if (camposObjetoNegocioDTO != null) {
                            camposObjetoNegocioDTO.setFormula(grupoVisaoCamposNegocioDTO.getFormula());
                            camposObjetoNegocioDTO.setTipoNegocio(grupoVisaoCamposNegocioDTO.getTipoNegocio());
                            if (camposObjetoNegocioDTO.getPk().equalsIgnoreCase("S")) {
                                colCamposPK.add(camposObjetoNegocioDTO);
                            }
                            colCamposTodos.add(camposObjetoNegocioDTO);
                        }
                    }
                }
            }
        }
    }

    public Map createFromMap(final Map map, final Collection colCampos, final UsuarioDTO usuarioDto, final VisaoDao visaoDao, final HttpServletRequest request) throws Exception {
        final List lstParms = new ArrayList();
        String strValues = "";
        String strFields = "";

        if (colCampos == null) {
            LOGGER.debug("DinamicViewsServiceEjb - colCampos é null ");
        }

        final String strTable = this.generateFrom(colCampos);
        String sql = "INSERT INTO " + strTable.toLowerCase() + " ";
        if (colCampos != null) {
            for (final Iterator it = colCampos.iterator(); it.hasNext();) {
                final CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();

                String strVal = "";
                if (camposObjetoNegocioDTO != null && camposObjetoNegocioDTO.getFormula() != null && !camposObjetoNegocioDTO.getFormula().trim().equalsIgnoreCase("")
                        && !camposObjetoNegocioDTO.getTipoNegocio().trim().equalsIgnoreCase(MetaUtil.CLASS_AND_METHOD)) {
                    strVal = this.executeFormula(camposObjetoNegocioDTO.getFormula(), map, camposObjetoNegocioDTO);
                } else {
                    if (camposObjetoNegocioDTO != null) {
                        strVal = (String) map.get(camposObjetoNegocioDTO.getNomeDB().trim());
                    }
                }

                if (camposObjetoNegocioDTO != null && camposObjetoNegocioDTO.getSequence().equalsIgnoreCase("S")) {
                    final int val = PersistenceEngine.getNextKey(this.getDao().getAliasDB(), strTable.toLowerCase(), camposObjetoNegocioDTO.getNomeDB().trim().toLowerCase());

                    if (!strValues.equalsIgnoreCase("")) {
                        strValues += ",";
                    }
                    strValues += "?";

                    if (!strFields.equalsIgnoreCase("")) {
                        strFields += ",";
                    }
                    strFields += "" + camposObjetoNegocioDTO.getNomeDB().trim();

                    map.put(camposObjetoNegocioDTO.getNomeDB().trim().toUpperCase(), "" + val);

                    lstParms.add(val);
                } else {
                    // Se o campo for obrigatório e o valor for null ou vazio, não pode continuar.
                    if (camposObjetoNegocioDTO != null && camposObjetoNegocioDTO.getObrigatorio() != null && camposObjetoNegocioDTO.getObrigatorio().equalsIgnoreCase("S")) {
                        if (strVal == null || strVal.trim().equals("")) {
                            return map;
                        }
                    } else {
                        /*
                         * Desenvolvedor: euler.ramos e thiago.oliveira Data: 08/11/2013 Horário: 16h00min ID Citsmart: 123627 Motivo/Comentário: As telas dinamic view não estavam
                         * listando os registros que estavam com o campo deleted igual a null
                         */
                        if (camposObjetoNegocioDTO != null && camposObjetoNegocioDTO.getNome().equalsIgnoreCase("deleted")) {
                            if (strVal == null || strVal.equals("")) {
                                strVal = "n";
                            }
                        }
                        // Se o campo não for obrigatório mas o valor for null, recebe vazio para garantir que não de erro de sql (para campos tipo not null)
                        if (strVal == null) {
                            strVal = UtilStrings.nullToVazio(strVal);
                        }
                    }

                    if (!strValues.equalsIgnoreCase("")) {
                        strValues += ",";
                    }
                    strValues += "?";

                    if (!strFields.equalsIgnoreCase("")) {
                        strFields += ",";
                    }
                    if (camposObjetoNegocioDTO != null) {
                        strFields += "" + camposObjetoNegocioDTO.getNomeDB().trim();
                        lstParms.add(MetaUtil.convertType(camposObjetoNegocioDTO.getTipoDB().trim(), strVal, camposObjetoNegocioDTO.getPrecisionDB(), request));
                    }
                }
            }
        }
        sql += "(" + strFields + ") VALUES (" + strValues + ")";
        visaoDao.execUpdate(sql, lstParms.toArray());

        if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
            new Thread(new RegistraLogDinamicView(lstParms.toArray(), "I", sql, usuarioDto, strTable)).start();
        }

        return map;
    }

    public void updateFromMap(final Map map, final Collection colCampos, final UsuarioDTO usuarioDto, final VisaoDao visaoDao, final HttpServletRequest request) throws Exception {
        final List lstParms = new ArrayList();
        final List lstWhere = new ArrayList();
        String strFields = "";
        String strWhere = "";
        final String strTable = this.generateFrom(colCampos);
        String sql = "UPDATE " + strTable + " ";
        for (final Iterator it = colCampos.iterator(); it.hasNext();) {
            final CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();

            String strVal = "";
            if (camposObjetoNegocioDTO.getFormula() != null && !camposObjetoNegocioDTO.getFormula().trim().equalsIgnoreCase("")
                    && !camposObjetoNegocioDTO.getTipoNegocio().trim().equalsIgnoreCase(MetaUtil.CLASS_AND_METHOD)) {
                strVal = this.executeFormula(camposObjetoNegocioDTO.getFormula(), map, camposObjetoNegocioDTO);
            } else {
                strVal = (String) map.get(camposObjetoNegocioDTO.getNomeDB().trim().toUpperCase());
            }
            if (!camposObjetoNegocioDTO.getPk().equalsIgnoreCase("S")) {
                if (strVal != null) {
                    if (!strFields.equalsIgnoreCase("")) {
                        strFields += ",";
                    }
                    strFields += "" + camposObjetoNegocioDTO.getNomeDB().trim() + " = ?";
                    lstParms.add(MetaUtil.convertType(camposObjetoNegocioDTO.getTipoDB().trim(), strVal, camposObjetoNegocioDTO.getPrecisionDB(), request));
                }
            } else {
                if (strVal != null) {
                    if (!strWhere.equalsIgnoreCase("")) {
                        strWhere += " AND ";
                    }
                    strWhere += "" + camposObjetoNegocioDTO.getNomeDB() + " = ?";
                    lstWhere.add(MetaUtil.convertType(camposObjetoNegocioDTO.getTipoDB().trim(), strVal, camposObjetoNegocioDTO.getPrecisionDB(), request));
                }
            }
        }
        sql += " SET " + strFields + " WHERE " + strWhere;

        lstParms.addAll(lstWhere);

        if (strFields != null && !strFields.trim().equalsIgnoreCase("")) {
            visaoDao.execUpdate(sql, lstParms.toArray());
            if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
                new Thread(new RegistraLogDinamicView(lstParms.toArray(), "U", sql, usuarioDto, strTable)).start();
            }
        }

        if (!map.containsKey("REMOVED")) {
            map.put("REMOVED", "false");
        }
        String removed = (String) map.get("REMOVED");
        if (removed == null) {
            removed = "false";
        }
        if (removed.equalsIgnoreCase("X")) { // A nova arquitetura coloca esta informacao.
            removed = "true";
        }
        String sqlUltAtualizAndLogicDelete = "";
        if (removed.equalsIgnoreCase("true")) {
            sqlUltAtualizAndLogicDelete = "UPDATE " + strTable + " SET DELETED = 'Y' WHERE " + strWhere;
            try {
                visaoDao.execUpdate(sqlUltAtualizAndLogicDelete, lstWhere.toArray());
                if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
                    new Thread(new RegistraLogDinamicView(lstWhere.toArray(), "D", sqlUltAtualizAndLogicDelete, usuarioDto, strTable)).start();
                }
            } catch (final Exception e) {
                LOGGER.debug("SQL executado:" + sqlUltAtualizAndLogicDelete);
                throw new LogicException("Não foi possí­vel realizar a exclusão do registro! Verifique se possui o Campo 'DELETED' do tipo CHAR(1) no Banco de dados!");
            }
        }
    }

    @Override
    public Collection restoreVisao(final Integer idVisao, final Collection colFilter) throws Exception {
        final GrupoVisaoDao grupoVisaoDao = new GrupoVisaoDao();
        final GrupoVisaoCamposNegocioDao grupoVisaoCamposNegocioDao = new GrupoVisaoCamposNegocioDao();
        final CamposObjetoNegocioDao camposObjetoNegocioDao = new CamposObjetoNegocioDao();
        final Collection colGrupos = grupoVisaoDao.findByIdVisaoAtivos(idVisao);

        final LookupServiceEjb lookupService = new LookupServiceEjb();

        final Collection colCamposTodos = new ArrayList();
        if (colGrupos != null) {
            for (final Iterator it = colGrupos.iterator(); it.hasNext();) {
                final GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
                grupoVisaoDTO.setColCamposVisao(grupoVisaoCamposNegocioDao.findByIdGrupoVisaoAtivos(grupoVisaoDTO.getIdGrupoVisao()));

                if (grupoVisaoDTO.getColCamposVisao() != null) {
                    for (final Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
                        final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();

                        CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
                        camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
                        camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioDao.restore(camposObjetoNegocioDTO);

                        if (camposObjetoNegocioDTO != null) {
                            grupoVisaoCamposNegocioDTO.setCamposObjetoNegocioDto(camposObjetoNegocioDTO);
                            camposObjetoNegocioDTO.setFormula(grupoVisaoCamposNegocioDTO.getFormula());
                            colCamposTodos.add(grupoVisaoCamposNegocioDTO);
                        }
                    }
                }
            }
        }

        final String sql = this.generateSQLRestore(colCamposTodos, colFilter);

        final Collection colRetorno = this.getDao().execSQL(sql, null);
        if (colRetorno != null) {
            for (final Iterator it = colRetorno.iterator(); it.hasNext();) {
                final Object[] objs = (Object[]) it.next();
                int i = 0;
                for (final Iterator it2 = colCamposTodos.iterator(); it2.hasNext();) {
                    final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();
                    final CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();
                    camposObjetoNegocioDTO.setValue(objs[i]);

                    if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
                        if (grupoVisaoCamposNegocioDTO.getTipoLigacao() == null) {
                            grupoVisaoCamposNegocioDTO.setTipoLigacao(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE);
                        }
                        if (grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE)) {
                            final LookupDTO lookupDto = new LookupDTO();
                            lookupDto.setTermoPesquisa("");
                            if (objs[i] != null) {
                                lookupDto.setTermoPesquisa(objs[i].toString());
                                lookupDto.setIdGrupoVisao(grupoVisaoCamposNegocioDTO.getIdGrupoVisao());
                                lookupDto.setIdCamposObjetoNegocio(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());

                                final ReturnLookupDTO returnLookupAux = lookupService.restoreSimple(lookupDto);
                                camposObjetoNegocioDTO.setReturnLookupDTO(returnLookupAux);
                            } else {
                                camposObjetoNegocioDTO.setReturnLookupDTO(null);
                            }
                        }
                    }

                    i++;
                }
                break; // Como é restore, pega só o 1.o
            }
        }
        return colCamposTodos;
    }

    private String generateSQLRestore(final Collection colPresentation, final Collection colFilter) throws Exception {
        String sql = "SELECT ";

        sql += this.generateFields(colPresentation);
        sql += " FROM ";
        sql += this.generateFromWithRelatios(colPresentation, colFilter);

        final String strFilter = this.generateFilter(colFilter);
        if (!strFilter.equalsIgnoreCase("")) {
            sql += " WHERE " + strFilter;
        }

        return sql;
    }

    private String generateFields(final Collection colPresentation) throws Exception {
        final ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
        String sqlFields = "";
        if (colPresentation != null) {
            int i = 1;
            for (final Iterator it = colPresentation.iterator(); it.hasNext();) {
                final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it.next();
                final CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();
                ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();

                objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
                objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);

                if (objetoNegocioDTO != null) {
                    if (!sqlFields.equalsIgnoreCase("")) {
                        sqlFields += ", ";
                    }
                    sqlFields += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " Fld_" + i;
                }
                i++;
            }
        }
        return sqlFields;
    }

    private String generateFromWithRelatios(final Collection colPresentation, final Collection colFilter) throws Exception {
        final ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
        final HashMap map = new HashMap();

        final Collection colGeral = new ArrayList();
        if (colPresentation != null) {
            colGeral.addAll(colPresentation);
        }
        if (colFilter != null) {
            colGeral.addAll(colFilter);
        }

        if (colGeral != null) {
            for (final Iterator it = colGeral.iterator(); it.hasNext();) {
                final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it.next();
                final CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();
                ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();

                objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
                objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);

                if (objetoNegocioDTO != null) {
                    if (!map.containsKey(objetoNegocioDTO.getNomeTabelaDB())) {
                        map.put(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());
                    }
                }
            }
        }

        final Set set = map.entrySet();
        final Iterator i = set.iterator();

        String fromSql = "";
        while (i.hasNext()) {
            final Map.Entry me = (Map.Entry) i.next();
            if (!fromSql.equalsIgnoreCase("")) {
                fromSql += ",";
            }
            fromSql += me.getKey();
        }

        return fromSql;
    }

    private String generateFilter(final Collection colFilter) throws Exception {
        final ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
        String sqlFilter = "";
        if (colFilter != null) {
            for (final Iterator it = colFilter.iterator(); it.hasNext();) {
                final GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it.next();
                final CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();
                ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();

                objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
                objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);

                if (objetoNegocioDTO != null) {
                    if (!sqlFilter.equalsIgnoreCase("")) {
                        sqlFilter += " AND ";
                    }
                    String pref = "";
                    String suf = "";
                    String comp = "=";
                    String value = "" + camposObjetoNegocioDTO.getValue();
                    if (MetaUtil.isStringType(camposObjetoNegocioDTO.getTipoDB())) {
                        pref = "'%";
                        suf = "%'";
                        comp = "LIKE";
                    } else {
                        if (MetaUtil.isNumericType(camposObjetoNegocioDTO.getTipoDB())) {
                            value = this.getNumber(value);
                        }
                    }
                    sqlFilter += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " " + comp + " " + pref + value + suf;
                }
            }
        }
        return sqlFilter;
    }

    public String getNumber(String value) {
        if (value == null) {
            return null;
        }
        if (value.indexOf("[") > -1) {
            final String aux = value.substring(value.indexOf("["));
            return UtilStrings.apenasNumeros(aux);
        }
        value = value.replaceAll(",00", "");
        value = value.replaceAll(",000", "");
        value = value.replaceAll("\\,", ".");
        return value;
    }

    public String executeFormula(String formula, final Map map, final CamposObjetoNegocioDTO camposObjetoNegocioDTO) {
        if (formula == null) {
            return null;
        }
        // SE FOR EXECUCAO DE CLASSE, CAI FORA! SERVE PARA CARREGAR COMBOS.
        if (camposObjetoNegocioDTO.getTipoNegocio() != null && camposObjetoNegocioDTO.getTipoNegocio().equalsIgnoreCase("CLASS")) {
            return null;
        }
        final org.mozilla.javascript.Context cx = org.mozilla.javascript.Context.enter();
        final org.mozilla.javascript.Scriptable scope = cx.initStandardObjects();

        final String sourceName = this.getClass().getName() + "_Formula";

        br.com.centralit.citcorpore.metainfo.util.HashMapUtil.map = map;

        final String retorno = "";
        formula = formula.replaceAll("TEXTSEARCH", "utilStrings.generateNomeBusca");
        formula = formula.replaceAll("GETFIELD", "hashMapUtil.getFieldInHash");
        formula = "retorno = " + formula;

        final StringBuilder compl = new StringBuilder();
        compl.append("var importNames = JavaImporter();\n");
        compl.append("importNames.importPackage(Packages.br.com.citframework.util);\n");
        compl.append("importNames.importPackage(Packages.br.com.centralit.citcorpore.metainfo.util);\n");

        formula = compl.toString() + "\n" + formula;

        scope.put("retorno", scope, retorno);
        scope.put("utilStrings", scope, new UtilStrings());
        scope.put("hashMapUtil", scope, new HashMapUtil());

        final Object result = cx.evaluateString(scope, formula, sourceName, 1, null);

        return Context.toString(result);
    }

    @Override
    public boolean isPKExists(final Collection colCamposPK, final Map hashValores) throws Exception {
        final ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
        String sql = "SELECT ";
        String sqlFields = "";
        String sqlFilter = "";
        int i = 1;
        if (colCamposPK == null || colCamposPK.size() == 0) {
            return false;
        }
        for (final Iterator it = colCamposPK.iterator(); it.hasNext();) {
            final CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
            ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();

            objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
            objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);

            if (objetoNegocioDTO != null) {
                if (!sqlFields.equalsIgnoreCase("")) {
                    sqlFields += ", ";
                }
                sqlFields += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " Val_" + i;
            }
            if (!sqlFilter.equalsIgnoreCase("")) {
                sqlFilter += " AND ";
            }
            String pref = "";
            String suf = "";
            final String comp = "=";
            if (MetaUtil.isStringType(camposObjetoNegocioDTO.getTipoDB().trim())) {
                pref = "'";
                suf = "'";
            }
            String strVal = (String) hashValores.get(camposObjetoNegocioDTO.getNomeDB().toUpperCase());
            if (strVal != null) {
                if (strVal.trim().equalsIgnoreCase("")) {
                    // Se nao existir valor para a PK, eh que nao existe!
                    return false;
                }
                if (MetaUtil.isNumericType(camposObjetoNegocioDTO.getTipoDB().trim())) {
                    final int x = strVal.indexOf("["); // Vai ate este ponto, pois o codigo fica entre [x]
                    if (x > -1) {
                        strVal = strVal.substring(x);
                    }
                    strVal = UtilStrings.apenasNumeros(strVal);
                }
                strVal = strVal.replaceAll(",00", "");
                strVal = strVal.replaceAll("\\,", ".");
                sqlFilter += objetoNegocioDTO.getNomeTabelaDB() + "." + camposObjetoNegocioDTO.getNomeDB() + " " + comp + " " + pref + strVal + suf;
            } else {
                // Se nao existir valor para a PK, eh que nao existe!
                return false;
            }
            i++;
        }

        sql += sqlFields + " FROM " + this.generateFrom(colCamposPK) + " WHERE " + sqlFilter;

        final Collection colRet = this.getDao().execSQL(sql, null);
        if (colRet == null) {
            return false;
        }
        if (colRet.size() > 0) {
            return true;
        }
        return false;
    }

    public String generateFrom(final Collection colGeral) throws Exception {
        final ObjetoNegocioDao objetoNegocioDao = new ObjetoNegocioDao();
        final Map map = new HashMap();

        if (colGeral != null) {
            for (final Iterator it = colGeral.iterator(); it.hasNext();) {
                final CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
                ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();

                objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
                objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioDao.restore(objetoNegocioDTO);

                if (objetoNegocioDTO != null) {
                    if (!map.containsKey(objetoNegocioDTO.getNomeTabelaDB())) {
                        map.put(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());
                    }
                }
            }
        }

        final Set set = map.entrySet();
        final Iterator i = set.iterator();

        String fromSql = "";
        while (i.hasNext()) {
            final Map.Entry me = (Map.Entry) i.next();
            if (!fromSql.equalsIgnoreCase("")) {
                fromSql += ",";
            }
            fromSql += me.getKey();
        }

        return fromSql;
    }

    @Override
    public String internacionalizaScript(final String script, final Locale locale) throws Exception {
        return this.internacionalizaScript(script, locale.getLanguage());
    }

    @Override
    public String internacionalizaScript(String script, final String locale) throws Exception {
        final Pattern pattern = Pattern.compile("\\$[\\w_\\d.]+");
        final Matcher matcher = pattern.matcher(script);
        while (matcher.find()) {
            final String chave = matcher.group();
            final String chaveInternacionalizada = UtilI18N.internacionaliza(locale, chave.substring(1));
            if (chaveInternacionalizada != null && !chaveInternacionalizada.isEmpty()) {
                script = script.replaceAll("\\" + chave, chaveInternacionalizada);
            }
        }
        return script;
    }

}
