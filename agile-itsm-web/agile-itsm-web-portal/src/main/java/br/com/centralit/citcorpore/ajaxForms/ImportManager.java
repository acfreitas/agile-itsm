package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.centralit.citcorpore.bean.ExternalConnectionDTO;
import br.com.centralit.citcorpore.bean.ImportConfigCamposDTO;
import br.com.centralit.citcorpore.bean.ImportConfigDTO;
import br.com.centralit.citcorpore.bean.ImportManagerDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.util.JSONUtil;
import br.com.centralit.citcorpore.negocio.ExternalConnectionService;
import br.com.centralit.citcorpore.negocio.ImportConfigCamposService;
import br.com.centralit.citcorpore.negocio.ImportConfigService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilI18N;

public class ImportManager extends AjaxFormAction {

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
        final Collection colExternalConnections = externalConnectionService.list();
        document.getSelectById("idExternalConnection").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        if (colExternalConnections != null) {
            for (final Iterator it = colExternalConnections.iterator(); it.hasNext();) {
                final ExternalConnectionDTO externalConnectionDTO = (ExternalConnectionDTO) it.next();
                document.getSelectById("idExternalConnection").addOption("" + externalConnectionDTO.getIdExternalConnection(), externalConnectionDTO.getNome());
            }
        }
        final Collection colObjs = externalConnectionService.getLocalTables();
        document.getSelectById("tabelaDestino").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        if (colObjs != null) {
            for (final Iterator it = colObjs.iterator(); it.hasNext();) {
                final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
                document.getSelectById("tabelaDestino").addOption(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());
            }
        }
    }

    public void selecionaExternalConnection(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
        final ImportManagerDTO importManagerDTO = (ImportManagerDTO) document.getBean();
        document.getSelectById("tabelaOrigem").removeAllOptions();
        document.getSelectById("tabelaOrigem").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        if (importManagerDTO.getIdExternalConnection() != null) {
            final Collection colObjs = externalConnectionService.getTables(importManagerDTO.getIdExternalConnection());
            if (colObjs != null) {
                for (final Iterator it = colObjs.iterator(); it.hasNext();) {
                    final ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO) it.next();
                    document.getSelectById("tabelaOrigem").addOption(objetoNegocioDTO.getNomeTabelaDB(), objetoNegocioDTO.getNomeTabelaDB());
                }
            }
        }
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    public void getOrigemDestinoDados(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ImportManagerDTO importManagerDTO = (ImportManagerDTO) document.getBean();
        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
        if (importManagerDTO.getIdExternalConnection() != null) {
            if (importManagerDTO.getTabelaOrigem() != null && !importManagerDTO.getTabelaOrigem().trim().equalsIgnoreCase("")) {
                String cmdOrigem = "origem = [";
                final Collection colObjs = externalConnectionService.getFieldsTable(importManagerDTO.getIdExternalConnection(), importManagerDTO.getTabelaOrigem());
                if (colObjs != null && colObjs.size() > 0) {
                    cmdOrigem += "{id:'##AUTO##',text:'--AUTO INCREMENT--'}";
                    for (final Iterator it = colObjs.iterator(); it.hasNext();) {
                        final CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
                        cmdOrigem += ",";
                        cmdOrigem += "{id:'" + camposObjetoNegocioDTO.getNomeDB() + "',text:'" + camposObjetoNegocioDTO.getNomeDB() + "'}";
                    }
                }
                cmdOrigem += "];";
                document.executeScript(cmdOrigem);
            }
            if (importManagerDTO.getTabelaDestino() != null && !importManagerDTO.getTabelaDestino().trim().equalsIgnoreCase("")) {
                String cmdDestino = "destino = [";
                final Collection colObjs = externalConnectionService.getFieldsLocalTable(importManagerDTO.getTabelaDestino());
                if (colObjs != null && colObjs.size() > 0) {
                    int i = 0;
                    for (final Iterator it = colObjs.iterator(); it.hasNext();) {
                        final CamposObjetoNegocioDTO camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) it.next();
                        if (i > 0) {
                            cmdDestino += ",";
                        }
                        cmdDestino += "{id:'" + camposObjetoNegocioDTO.getNomeDB() + "',text:'" + camposObjetoNegocioDTO.getNomeDB() + "'}";
                        i++;
                    }
                }
                cmdDestino += "];";
                document.executeScript(cmdDestino);
            }
        }
        document.executeScript("geraGrid()");
    }

    public void carregarDados(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ExternalConnectionService externalConnectionService = (ExternalConnectionService) ServiceLocator.getInstance().getService(ExternalConnectionService.class, null);
        final ImportManagerDTO importManagerDTO = (ImportManagerDTO) document.getBean();
        final String jsonMatriz = importManagerDTO.getJsonMatriz();
        Map<String, Object> mapMatriz = null;
        try {
            mapMatriz = JSONUtil.convertJsonToMap(jsonMatriz, true);
        } catch (final Exception e) {
            System.out.println("jsonMatriz: " + jsonMatriz);
            e.printStackTrace();
            throw e;
        }
        final List colMatrizTratada = (List) mapMatriz.get("MATRIZ");

        externalConnectionService.processImport(importManagerDTO, colMatrizTratada);
        document.alert(UtilI18N.internacionaliza(request, "importmanager.ok"));
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    public void gravar(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ImportManagerDTO importManagerDTO = (ImportManagerDTO) document.getBean();
        final ImportConfigDTO importConfigDTO = new ImportConfigDTO();
        Reflexao.copyPropertyValues(importManagerDTO, importConfigDTO);
        final ImportConfigService importConfigService = (ImportConfigService) ServiceLocator.getInstance().getService(ImportConfigService.class, null);
        final String jsonMatriz = importManagerDTO.getJsonMatriz();
        Map<String, Object> mapMatriz = null;
        try {
            mapMatriz = JSONUtil.convertJsonToMap(jsonMatriz, true);
        } catch (final Exception e) {
            System.out.println("jsonMatriz: " + jsonMatriz);
            e.printStackTrace();
            throw e;
        }

        if (mapMatriz != null && mapMatriz.size() > 0) {

            final ArrayList colMatrizTratada = (ArrayList) mapMatriz.get("MATRIZ");
            final Collection colDadosCampos = new ArrayList();
            for (final Iterator it = colMatrizTratada.iterator(); it.hasNext();) {
                final ImportConfigCamposDTO importConfigCamposDTO = new ImportConfigCamposDTO();
                final HashMap mapItem = (HashMap) it.next();
                final String idOrigem = (String) mapItem.get("IDORIGEM");
                final String idDestino = (String) mapItem.get("IDDESTINO");
                final String script = (String) mapItem.get("SCRIPT");

                importConfigCamposDTO.setOrigem(idOrigem);
                importConfigCamposDTO.setDestino(idDestino);
                importConfigCamposDTO.setScript(script);

                colDadosCampos.add(importConfigCamposDTO);
            }
            importConfigDTO.setColDadosCampos(colDadosCampos);

            if (importConfigDTO.getIdImportConfig() == null || importConfigDTO.getIdImportConfig().intValue() == 0) {
                importConfigService.create(importConfigDTO);
                document.alert(UtilI18N.internacionaliza(request, "MSG05"));
            } else {
                importConfigService.update(importConfigDTO);
                document.alert(UtilI18N.internacionaliza(request, "MSG06"));
            }
            document.getElementById("idImportConfig").setValue("" + importConfigDTO.getIdImportConfig());

        }
    }

    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ImportManagerDTO importManagerDTO = (ImportManagerDTO) document.getBean();
        ImportConfigDTO importConfigDTO = new ImportConfigDTO();
        Reflexao.copyPropertyValues(importManagerDTO, importConfigDTO);
        final ImportConfigService importConfigService = (ImportConfigService) ServiceLocator.getInstance().getService(ImportConfigService.class, null);
        final ImportConfigCamposService importConfigCamposService = (ImportConfigCamposService) ServiceLocator.getInstance().getService(ImportConfigCamposService.class, null);

        importConfigDTO = (ImportConfigDTO) importConfigService.restore(importConfigDTO);
        document.executeScript("limpaGrid()");
        final HTMLForm form = document.getForm("form");
        form.clear();
        if (importConfigDTO != null) {
            importManagerDTO.setIdExternalConnection(importConfigDTO.getIdExternalConnection());
            importManagerDTO.setTabelaOrigem(importConfigDTO.getTabelaOrigem());
            importManagerDTO.setTabelaDestino(importConfigDTO.getTabelaDestino());
            document.setBean(importManagerDTO);
            this.selecionaExternalConnection(document, request, response);
            this.getOrigemDestinoDados(document, request, response);
            if (importConfigDTO.getIdImportConfig() != null) {
                final Collection colCampos = importConfigCamposService.findByIdImportConfig(importConfigDTO.getIdImportConfig());
                if (colCampos != null) {
                    for (final Iterator it = colCampos.iterator(); it.hasNext();) {
                        final ImportConfigCamposDTO importConfigCamposDTO = (ImportConfigCamposDTO) it.next();
                        document.getElementById("auxData").setValue("");
                        if (importConfigCamposDTO.getScript() != null && !importConfigCamposDTO.getScript().trim().equalsIgnoreCase("")) {
                            document.executeScript("HTMLUtils.setValue('auxData', ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('"
                                    + CitAjaxWebUtil.codificaAspasApostrofe(CitAjaxWebUtil.codificaEnter(importConfigCamposDTO.getScript())) + "')))");
                        }
                        document.executeScript("adicionaLinha('" + importConfigCamposDTO.getOrigem() + "', '" + importConfigCamposDTO.getDestino()
                                + "', document.form.auxData.value)");
                    }
                }
            }
        }

        if (importConfigDTO != null) {
            form.setValues(importConfigDTO);
        }
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    @Override
    public Class getBeanClass() {
        return ImportManagerDTO.class;
    }

}
