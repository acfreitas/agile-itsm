package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.integracao.ExportacaoContratosDao;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.util.DataBaseMetaDadosUtil;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.util.ImportInfoField;
import br.com.centralit.citcorpore.util.ImportInfoRecord;
import br.com.centralit.citcorpore.util.UtilImportData;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ExportacaoContratosServiceEjb extends CrudServiceImpl implements ExportacaoContratosService {

    private ExportacaoContratosDao dao;

    @Override
    protected ExportacaoContratosDao getDao() {
        if (dao == null) {
            dao = new ExportacaoContratosDao();
        }
        return dao;
    }

    /**
     * Recupera o objeto negocio utilizando o nome da tabela
     *
     * @param name
     * @return ObjetoNegocioDTO
     * @throws Exception
     */
    @Override
    public ObjetoNegocioDTO restoreByName(final String name) throws Exception {
        final ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        return objetoNegocioService.findByNomeObjetoNegocio(name);
    }

    /**
     * Recupera o xml do objeto negocio indicado
     *
     * @param dbName
     * @param filterAditional
     * @param fieldValidExistence
     * @return String
     * @throws Exception
     * @author rodrigo.acorse
     */
    @Override
    public String exportDB(final String dbName, final String filterAditional, final String[] fieldValidExistence, final String[] fieldValidExclusion, final String type)
            throws Exception {
        final ObjetoNegocioDTO objetoNegocioDto = this.restoreByName(dbName);
        if (objetoNegocioDto != null) {
            final HashMap<String, String> map = new HashMap<String, String>();
            map.put("excluirAoExportar", "N");
            map.put("exportarVinculos", "N");

            final StringBuilder result = this.geraExportObjetoNegocio(map, objetoNegocioDto.getIdObjetoNegocio(), "", filterAditional, "", fieldValidExistence, fieldValidExclusion,
                    type);
            return result.toString();
        } else {
            return "";
        }
    }

    /**
     * Gera um IN em SQL com os IDs do xml indicado
     *
     * @param xml
     * @param id
     * @return String
     * @throws Exception
     * @author rodrigo.acorse
     */
    @Override
    public String generateSQLIn(String xml, final String id) throws Exception {
        final ArrayList cols = new ArrayList();
        final List colRecordsGeral = new ArrayList();
        boolean exists;

        if (xml != null && !xml.equals("")) {
            xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='0'>\n" + xml + "\n</tables>";

            final Collection colRecords = UtilImportData.readXMLSource(xml);
            if (colRecords != null) {
                colRecordsGeral.addAll(colRecords);
            }

            for (final Iterator itRecords = colRecordsGeral.iterator(); itRecords.hasNext();) {
                final ImportInfoRecord importInfoRecord = (ImportInfoRecord) itRecords.next();
                for (final Iterator it = importInfoRecord.getColFields().iterator(); it.hasNext();) {
                    final ImportInfoField importInfoField = (ImportInfoField) it.next();
                    if (importInfoField.getNameField().equalsIgnoreCase(id)) {
                        if (importInfoField.getValueField() != null && !importInfoField.getValueField().equals("") && !importInfoField.getValueField().equals("null")) {
                            exists = false;
                            for (int i = 0; i < cols.size(); i++) {
                                if (cols.get(i).equals(importInfoField.getValueField())) {
                                    exists = true;
                                }
                            }

                            if (!exists) {
                                cols.add(importInfoField.getValueField());
                            }
                        }
                    }
                }
            }
        }

        if (cols.isEmpty()) {
            return "";
        } else {
            return id.toUpperCase() + " IN (" + StringUtils.join(cols.toArray(), ", ") + ") ";
        }
    }

    /**
     * Retorna o xml de todas as tabelas
     *
     * @return String
     * @throws Exception
     * @author rodrigo.acorse
     */
    @Override
    public String recuperaXmlTabelas(final Integer idContrato, final Integer[] idGrupos, final String exportarUnidades, final String exportarAcordoNivelServico,
            final String exportarCatalogoServico) throws Exception {
        try {
            // Guarda o xml das tabelas exportadas.
            final HashMap<String, String> xmlTabelas = new HashMap<String, String>();
            // Guarda o sql utilizado no where para outras tabelas.
            final HashMap<String, String> mapSql = new HashMap<String, String>();
            mapSql.put("idempresa", " IDEMPRESA = 1 ");

            // Inicia a string que receberá o xml final.
            final StringBuilder contentXml = new StringBuilder();

            if (idGrupos != null) {
                // Recupera a tabela grupo com base no idgrupo selecionados.
                xmlTabelas.put("grupo",
                        this.exportDB("grupo", " IDGRUPO IN (" + StringUtils.join(idGrupos, ", ") + ") ", new String[] {"nome"}, new String[] {"datafim"}, "concrete") + "\n");
                if (idContrato != null) {
                    // Recupera a tabela contratosgrupos com base no idgrupo selecionados e no contrato selecionado.
                    xmlTabelas.put(
                            "contratosgrupos",
                            this.exportDB("contratosgrupos", " IDGRUPO IN (" + StringUtils.join(idGrupos, ", ") + ") AND IDCONTRATO = " + idContrato + " ", new String[] {
                                    "idcontrato", "idgrupo"}, null, "relation")
                                    + "\n");
                }
            }

            if (exportarUnidades != null && exportarUnidades.equalsIgnoreCase("y")) {
                if (idContrato != null) {
                    // Recupera a tabela contratounidades com base no contrato selecionado.
                    xmlTabelas.put("contratosunidades",
                            this.exportDB("contratosunidades", " IDCONTRATO = " + idContrato + " ", new String[] {"idcontrato", "idunidade"}, null, "relation") + "\n");
                    // Monta a clausula where de idunidade recuperados da tabela contratosunidades. ex: idunidade in (1,
                    // 2).
                    mapSql.put("idunidade", this.generateSQLIn(xmlTabelas.get("contratosunidades"), "idunidade"));

                    if (mapSql.get("idunidade") != null && !mapSql.get("idunidade").equals("")) {
                        // Recupera a tabela unidade com base no idunidade recuperados da tabela contratosunidades.
                        xmlTabelas.put("unidade", this.exportDB("unidade", mapSql.get("idunidade"), new String[] {"nome"}, new String[] {"datafim"}, "concrete") + "\n");
                    }
                } else {
                    // Recupera a tabela unidade.
                    xmlTabelas.put("unidade", this.exportDB("unidade", "", new String[] {"nome"}, new String[] {"datafim"}, "concrete") + "\n");
                }

                // Monta a clausula where de idendereco recuperados da tabela unidade. ex: idendereco in (1, 2).
                mapSql.put("idenderecounidade", this.generateSQLIn(xmlTabelas.get("unidade"), "idendereco"));

                // Monta a clausula where de idtipounidade recuperados da tabela unidade. ex: idtipounidade in (1, 2).
                mapSql.put("idtipounidade", this.generateSQLIn(xmlTabelas.get("unidade"), "idtipounidade"));

                if (mapSql.get("idunidade") != null && !mapSql.get("idunidade").equals("")) {
                    // Recupera a tabela unidade com base no idunidade recuperados da tabela contratosunidades e
                    // idunidadepai igual a null.
                    xmlTabelas.put("unidadepai",
                            this.exportDB("unidade", mapSql.get("idunidade") + " AND IDUNIDADEPAI IS NULL ", new String[] {"nome"}, new String[] {"datafim"}, "concrete") + "\n");
                    // Monta a clausula where de idendereco recuperados da tabela unidade. ex: idendereco in (1, 2).
                    mapSql.put("idenderecounidadepai", this.generateSQLIn(xmlTabelas.get("unidadepai"), "idendereco"));
                }

                // Recupera a tabela endereco com base no idendereco recuperados da tabela unidade.
                if (mapSql.get("idenderecounidade") != null && !mapSql.get("idenderecounidade").equals("") && mapSql.get("idenderecounidadepai") != null
                        && !mapSql.get("idenderecounidadepai").equals("")) {
                    xmlTabelas.put(
                            "endereco",
                            this.exportDB("endereco", mapSql.get("idenderecounidade") + " OR " + mapSql.get("idenderecounidadepai"), new String[] {"logradouro", "numero",
                                    "complemento", "bairro", "idcidade", "idpais", "cep", "iduf"}, null, "concrete")
                                    + "\n");
                } else if (mapSql.get("idenderecounidade") != null && !mapSql.get("idenderecounidade").equals("")) {
                    xmlTabelas.put(
                            "endereco",
                            this.exportDB("endereco", mapSql.get("idenderecounidade"), new String[] {"logradouro", "numero", "complemento", "bairro", "idcidade", "idpais", "cep",
                                    "iduf"}, null, "concrete")
                                    + "\n");
                } else if (mapSql.get("idenderecounidadepai") != null && !mapSql.get("idenderecounidadepai").equals("")) {
                    xmlTabelas.put(
                            "endereco",
                            this.exportDB("endereco", mapSql.get("idenderecounidadepai"), new String[] {"logradouro", "numero", "complemento", "bairro", "idcidade", "idpais",
                                    "cep", "iduf"}, null, "concrete")
                                    + "\n");
                }

                if (mapSql.get("idtipounidade") != null && !mapSql.get("idtipounidade").equals("")) {
                    // Recupera a tabela tipounidade com base no idtipounidade recuperados da tabela unidade.
                    xmlTabelas.put("tipounidade", this.exportDB("tipounidade", mapSql.get("idtipounidade"), new String[] {"nometipounidade"}, new String[] {"datafim"}, "concrete")
                            + "\n");
                }
            }

            if (exportarCatalogoServico != null && exportarCatalogoServico.equalsIgnoreCase("y")) {
                if (idContrato != null) {
                    // Recupera a tabela catalogoservico com base no idcontrato selecionado.
                    xmlTabelas.put(
                            "catalogoservico",
                            this.exportDB("catalogoservico", " DATAFIM IS NULL AND IDCONTRATO = " + idContrato + " ", new String[] {"titulocatalogoservico"},
                                    new String[] {"datafim"}, "concrete") + "\n");
                    // Recupera a tabela servicocontrato com base no idcontrato selecionado.
                    xmlTabelas.put(
                            "servicocontrato",
                            this.exportDB("servicocontrato", " (DELETED IS NULL OR DELETED = 'n' OR DELETED = 'N') AND IDCONTRATO = " + idContrato + " ", new String[] {
                                    "idcontrato", "idservico"}, new String[] {"deleted"}, "concrete")
                                    + "\n");
                    // Recupera a tabela servicocontrato com base no idcontrato selecionado.
                    xmlTabelas.put("servcontratocatalogoserv",
                            this.exportDB("servcontratocatalogoserv", "", new String[] {"idservicocontrato", "idcatalogoservico"}, null, "relation") + "\n");
                    // Monta a clausula where de idservicocontrato recuperados da tabela servicocontrato. ex:
                    // idservicocontrato in (1, 2).
                    mapSql.put("idservicocontrato", this.generateSQLIn(xmlTabelas.get("servicocontrato"), "idservicocontrato"));
                    // Monta a clausula where de idservico recuperados da tabela servicocontrato. ex: idservico in (1,
                    // 2).
                    mapSql.put("idservico", this.generateSQLIn(xmlTabelas.get("servicocontrato"), "idservico"));
                }

                // Recupera a tabela servico com base no idservico recuperados da tabela servicocontrato.
                xmlTabelas.put("servico", this.exportDB("servico", mapSql.get("idservico"), new String[] {"nomeservico"}, new String[] {"deleted"}, "concrete") + "\n");
                // Monta a clausula where de idcategoriaservico recuperados da tabela servico. ex: idcategoriaservico in
                // (1, 2).
                mapSql.put("idcategoriaservico", this.generateSQLIn(xmlTabelas.get("servico"), "idcategoriaservico"));
                // Monta a clausula where de idtiposervico recuperados da tabela servico. ex: idtiposervico in (1, 2).
                mapSql.put("idtiposervico", this.generateSQLIn(xmlTabelas.get("servico"), "idtiposervico"));
                // Monta a clausula where de idimportancianegocio recuperados da tabela servico. ex:
                // idimportancianegocio in (1, 2).
                mapSql.put("idimportancianegocio", this.generateSQLIn(xmlTabelas.get("servico"), "idimportancianegocio"));
                // Monta a clausula where de idtipoeventoservico recuperados da tabela servico. ex: idtipoeventoservico
                // in (1, 2).
                mapSql.put("idtipoeventoservico", this.generateSQLIn(xmlTabelas.get("servico"), "idtipoeventoservico"));
                // Monta a clausula where de idtipodemandaservico recuperados da tabela servico. ex:
                // idtipodemandaservico in (1, 2).
                mapSql.put("idtipodemandaservico", this.generateSQLIn(xmlTabelas.get("servico"), "idtipodemandaservico"));
                // Monta a clausula where de idlocalexecucaoservico recuperados da tabela servico. ex:
                // idlocalexecucaoservico in (1, 2).
                mapSql.put("idlocalexecucaoservico", this.generateSQLIn(xmlTabelas.get("servico"), "idlocalexecucaoservico"));
                // Monta a clausula where de idsituacaoservico recuperados da tabela servico. ex: idsituacaoservico in
                // (1, 2).
                mapSql.put("idsituacaoservico", this.generateSQLIn(xmlTabelas.get("servico"), "idsituacaoservico"));

                // Recupera a tabela categoriaservico com base no idcategoriaservico recuperados da tabela servico.
                xmlTabelas.put("categoriaservico",
                        this.exportDB("categoriaservico", mapSql.get("idcategoriaservico"), new String[] {"nomecategoriaservicoconcatenado"}, new String[] {"datafim"}, "concrete")
                                + "\n");

                if (mapSql.get("idcategoriaservico") != null && !mapSql.get("idcategoriaservico").equals("")) {
                    // Recupera a tabela categoriaservico com base no idcategoriaservico recuperados da tabela servico e
                    // idcategoriaservicopai igual a null.
                    xmlTabelas.put(
                            "categoriaservicopai",
                            this.exportDB("categoriaservico", mapSql.get("idcategoriaservico") + " AND IDCATEGORIASERVICOPAI IS NULL ",
                                    new String[] {"nomecategoriaservicoconcatenado"}, new String[] {"datafim"}, "concrete") + "\n");
                }

                if (mapSql.get("idsituacaoservico") != null && !mapSql.get("idsituacaoservico").equals("")) {
                    // Recupera a tabela situacaoservico com base no idempresa igual a 1 e idsituacaoservico recuperados
                    // da tabela servico.
                    xmlTabelas.put(
                            "situacaoservico",
                            this.exportDB("situacaoservico", mapSql.get("idempresa") + " AND " + mapSql.get("idsituacaoservico"), new String[] {"nomesituacaoservico"},
                                    new String[] {"datafim"}, "concrete") + "\n");
                }

                if (mapSql.get("idtiposervico") != null && !mapSql.get("idtiposervico").equals("")) {
                    // Recupera a tabela tiposervico com base no idempresa igual a 1 e idtiposervico recuperados da
                    // tabela servico.
                    xmlTabelas.put(
                            "tiposervico",
                            this.exportDB("tiposervico", mapSql.get("idempresa") + " AND " + mapSql.get("idtiposervico"), new String[] {"nometiposervico"},
                                    new String[] {"situacao"}, "concrete") + "\n");
                }

                if (mapSql.get("idimportancianegocio") != null && !mapSql.get("idimportancianegocio").equals("")) {
                    // Recupera a tabela importancianegocio com base no idempresa igual a 1 e idimportancianegocio
                    // recuperados da tabela servico.
                    xmlTabelas.put(
                            "importancianegocio",
                            this.exportDB("importancianegocio", mapSql.get("idempresa") + " AND " + mapSql.get("idimportancianegocio"), new String[] {"nomeimportancianegocio"},
                                    new String[] {"situacao"}, "concrete") + "\n");
                }

                if (mapSql.get("idtipoeventoservico") != null && !mapSql.get("idtipoeventoservico").equals("")) {
                    // Recupera a tabela tipoeventoservico com base no idempresa igual a 1 e idtipoeventoservico
                    // recuperados da tabela servico.
                    xmlTabelas.put("tipoeventoservico",
                            this.exportDB("tipoeventoservico", mapSql.get("idtipoeventoservico"), new String[] {"nometipoeventoservico"}, new String[] {"deleted"}, "concrete")
                                    + "\n");
                }

                if (mapSql.get("idtipodemandaservico") != null && !mapSql.get("idtipodemandaservico").equals("")) {
                    // Recupera a tabela tipodemandaservico com base no idempresa igual a 1 e idtipodemandaservico
                    // recuperados da tabela servico.
                    xmlTabelas.put("tipodemandaservico",
                            this.exportDB("tipodemandaservico", mapSql.get("idtipodemandaservico"), new String[] {"nometipodemandaservico"}, new String[] {"deleted"}, "concrete")
                                    + "\n");
                }

                if (mapSql.get("idlocalexecucaoservico") != null && !mapSql.get("idlocalexecucaoservico").equals("")) {
                    // Recupera a tabela localexecucaoservico com base no idempresa igual a 1 e idlocalexecucaoservico
                    // recuperados da tabela servico.
                    xmlTabelas.put(
                            "localexecucaoservico",
                            this.exportDB("localexecucaoservico", mapSql.get("idlocalexecucaoservico"), new String[] {"nomelocalexecucaoservico"}, new String[] {"deleted"},
                                    "concrete") + "\n");
                }
            }

            if (exportarAcordoNivelServico != null && exportarAcordoNivelServico.equalsIgnoreCase("y")) {
                // Recupera a tabela prioridade.
                xmlTabelas.put("prioridade", this.exportDB("prioridade", "", new String[] {"nomeprioridade"}, new String[] {"situacao"}, "concrete") + "\n");

                if (idContrato != null) {
                    // Recupera a tabela acordonivelservicocontrato com base no idcontrato selecionado.
                    xmlTabelas.put(
                            "acordonivelservicocontrato",
                            this.exportDB("acordonivelservicocontrato", " (DELETED IS NULL OR DELETED = 'n' OR DELETED = 'N') AND IDCONTRATO = " + idContrato + " ", new String[] {
                                    "idcontrato", "descricaoacordo"}, new String[] {"deleted"}, "concrete")
                                    + "\n");

                    if (mapSql.get("idservicocontrato") != null && !mapSql.get("idservicocontrato").equals("")) {
                        // Recupera a tabela acordonivelservico com base nos idservicocontrato recuperados da tabela
                        // servicocontrato.
                        xmlTabelas.put(
                                "acordonivelservico",
                                this.exportDB("acordonivelservico", mapSql.get("idservicocontrato"), new String[] {"idservicocontrato", "descricaosla"}, new String[] {"deleted"},
                                        "concrete") + "\n");
                        // Monta a clausula where de idacordonivelservico recuperados da tabela acordonivelservico. ex:
                        // idacordonivelservico in (1, 2).
                        mapSql.put("idacordonivelservico", this.generateSQLIn(xmlTabelas.get("acordonivelservico"), "idacordonivelservico"));
                    }

                    if (mapSql.get("idacordonivelservico") != null && !mapSql.get("idacordonivelservico").equals("") && mapSql.get("idservicocontrato") != null
                            && !mapSql.get("idservicocontrato").equals("")) {
                        // Recupera a tabela acordoservicocontrato com base no idacordonivelservico recuperados da
                        // tabela acordonivelservico e idservicocontrato recuperados da tabela servicocontrato.
                        xmlTabelas.put(
                                "acordoservicocontrato",
                                this.exportDB("acordoservicocontrato", mapSql.get("idacordonivelservico") + " AND " + mapSql.get("idservicocontrato"), new String[] {
                                        "idacordonivelservico", "idservicocontrato"}, new String[] {"deleted"}, "concrete")
                                        + "\n");
                    } else if (mapSql.get("idacordonivelservico") != null && !mapSql.get("idacordonivelservico").equals("")) {
                        // Recupera a tabela acordoservicocontrato com base no idacordonivelservico recuperados da
                        // tabela acordonivelservico.
                        xmlTabelas.put(
                                "acordoservicocontrato",
                                this.exportDB("acordoservicocontrato", mapSql.get("idacordonivelservico"), new String[] {"idacordonivelservico", "idservicocontrato"},
                                        new String[] {"deleted"}, "concrete") + "\n");
                    } else if (mapSql.get("idservicocontrato") != null && !mapSql.get("idservicocontrato").equals("")) {
                        // Recupera a tabela acordoservicocontrato com base no idservicocontrato recuperados da tabela
                        // servicocontrato.
                        xmlTabelas.put(
                                "acordoservicocontrato",
                                this.exportDB("acordoservicocontrato", mapSql.get("idservicocontrato"), new String[] {"idacordonivelservico", "idservicocontrato"},
                                        new String[] {"deleted"}, "concrete") + "\n");
                    }

                    if (mapSql.get("idacordonivelservico") != null && !mapSql.get("idacordonivelservico").equals("") && mapSql.get("idservico") != null
                            && !mapSql.get("idservico").equals("")) {
                        // Recupera a tabela ansservicocontratorelacionado com base no idacordonivelservico recuperados
                        // da tabela acordonivelservico e idservico recuperados da tabela servicocontrato.
                        xmlTabelas.put(
                                "ansservicocontratorelacionado",
                                this.exportDB("ansservicocontratorelacionado", mapSql.get("idacordonivelservico") + " AND " + mapSql.get("idservico"), new String[] {
                                        "idacordonivelservico", "idservico"}, null, "relation")
                                        + "\n");
                    } else if (mapSql.get("idacordonivelservico") != null && !mapSql.get("idacordonivelservico").equals("")) {
                        // Recupera a tabela ansservicocontratorelacionado com base no idacordonivelservico recuperados
                        // da tabela acordonivelservico.
                        xmlTabelas.put(
                                "ansservicocontratorelacionado",
                                this.exportDB("ansservicocontratorelacionado", mapSql.get("idacordonivelservico"), new String[] {"idacordonivelservico", "idservico"}, null,
                                        "relation") + "\n");
                    } else if (mapSql.get("idservico") != null && !mapSql.get("idservico").equals("")) {
                        // Recupera a tabela ansservicocontratorelacionado com base no idservico recuperados da tabela
                        // servicocontrato.
                        xmlTabelas.put("ansservicocontratorelacionado",
                                this.exportDB("ansservicocontratorelacionado", mapSql.get("idservico"), new String[] {"idacordonivelservico", "idservico"}, null, "relation")
                                        + "\n");
                    }

                    // Recupera a tabela slarequisitosla com base no idacordonivelservico recuperados da tabela
                    // acordonivelservico.
                    xmlTabelas.put("slarequisitosla",
                            this.exportDB("slarequisitosla", mapSql.get("idacordonivelservico"), new String[] {"idacordonivelservico"}, new String[] {"deleted"}, "relation")
                                    + "\n");
                    // Recupera a tabela requisitosla.
                    xmlTabelas.put("requisitosla", this.exportDB("requisitosla", "", new String[] {"assunto"}, new String[] {"deleted"}, "concrete") + "\n");
                    // Monta a clausula where de idempregado recuperados da tabela requisitosla. ex: idempregado in (1,
                    // 2).
                    mapSql.put("idempregado", this.generateSQLIn(xmlTabelas.get("requisitosla"), "idempregado"));

                    if (mapSql.get("idempregado") != null && !mapSql.get("idempregado").equals("")) {
                        // Recupera a tabela empregados com base no idempregado recuperados da tabela requisitosla.
                        xmlTabelas.put("empregados", this.exportDB("empregados", mapSql.get("idempregado"), new String[] {"nomeprocura"}, new String[] {"datafim"}, "concrete")
                                + "\n");
                    }
                } else {
                    // Recupera a tabela acordonivelservico com idservicocontrato igual a null.
                    xmlTabelas.put("acordonivelservico",
                            this.exportDB("acordonivelservico", " IDSERVICOCONTRATO is NULL ", new String[] {"descricaosla"}, new String[] {"deleted"}, "concrete") + "\n");
                }
            }

            // Adiciona o topo do xml final.
            contentXml.append("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
            if (idContrato != null) {
                contentXml.append("<tables origem='0' filter='" + idContrato + "'>\n");
            } else {
                contentXml.append("<tables origem='0' filter=''>\n");
            }

            // Adiciona tabelas no xml final.
            if (xmlTabelas.get("grupo") != null) {
                contentXml.append(xmlTabelas.get("grupo"));
            }
            if (xmlTabelas.get("contratosgrupos") != null) {
                contentXml.append(xmlTabelas.get("contratosgrupos"));
            }

            if (xmlTabelas.get("tipounidade") != null) {
                contentXml.append(xmlTabelas.get("tipounidade"));
            }
            if (xmlTabelas.get("endereco") != null) {
                contentXml.append(xmlTabelas.get("endereco"));
            }
            if (xmlTabelas.get("unidadepai") != null) {
                contentXml.append(xmlTabelas.get("unidadepai"));
            }
            if (xmlTabelas.get("unidade") != null) {
                contentXml.append(xmlTabelas.get("unidade"));
            }
            if (xmlTabelas.get("contratosunidades") != null) {
                contentXml.append(xmlTabelas.get("contratosunidades"));
            }

            if (xmlTabelas.get("localexecucaoservico") != null) {
                contentXml.append(xmlTabelas.get("localexecucaoservico"));
            }
            if (xmlTabelas.get("tipodemandaservico") != null) {
                contentXml.append(xmlTabelas.get("tipodemandaservico"));
            }
            if (xmlTabelas.get("tipoeventoservico") != null) {
                contentXml.append(xmlTabelas.get("tipoeventoservico"));
            }
            if (xmlTabelas.get("importancianegocio") != null) {
                contentXml.append(xmlTabelas.get("importancianegocio"));
            }
            if (xmlTabelas.get("tiposervico") != null) {
                contentXml.append(xmlTabelas.get("tiposervico"));
            }
            if (xmlTabelas.get("situacaoservico") != null) {
                contentXml.append(xmlTabelas.get("situacaoservico"));
            }
            if (xmlTabelas.get("categoriaservicopai") != null) {
                contentXml.append(xmlTabelas.get("categoriaservicopai"));
            }
            if (xmlTabelas.get("categoriaservico") != null) {
                contentXml.append(xmlTabelas.get("categoriaservico"));
            }
            if (xmlTabelas.get("servico") != null) {
                contentXml.append(xmlTabelas.get("servico"));
            }
            if (xmlTabelas.get("servicocontrato") != null) {
                contentXml.append(xmlTabelas.get("servicocontrato"));
            }
            if (xmlTabelas.get("catalogoservico") != null) {
                contentXml.append(xmlTabelas.get("catalogoservico"));
            }
            if (xmlTabelas.get("servcontratocatalogoserv") != null) {
                contentXml.append(xmlTabelas.get("servcontratocatalogoserv"));
            }

            if (xmlTabelas.get("empregados") != null) {
                contentXml.append(xmlTabelas.get("empregados"));
            }
            if (xmlTabelas.get("requisitosla") != null) {
                contentXml.append(xmlTabelas.get("requisitosla"));
            }
            if (xmlTabelas.get("prioridade") != null) {
                contentXml.append(xmlTabelas.get("prioridade"));
            }
            if (xmlTabelas.get("acordonivelservico") != null) {
                contentXml.append(xmlTabelas.get("acordonivelservico"));
            }
            if (xmlTabelas.get("acordoservicocontrato") != null) {
                contentXml.append(xmlTabelas.get("acordoservicocontrato"));
            }
            if (xmlTabelas.get("ansservicocontratorelacionado") != null) {
                contentXml.append(xmlTabelas.get("ansservicocontratorelacionado"));
            }
            if (xmlTabelas.get("slarequisitosla") != null) {
                contentXml.append(xmlTabelas.get("slarequisitosla"));
            }

            // Adiciona o rodapé do xml final.
            contentXml.append("\n</tables>");

            return StringEscapeUtils.escapeHtml(contentXml.toString());
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gera o XML do objeto negocio indicado
     *
     * @param hashValores
     * @param idObjetoNegocio
     * @param sqlDelete
     * @param filterAditional
     * @param order
     * @return StringBuilder
     * @throws ServiceException
     * @throws Exception
     */
    @Override
    public StringBuilder geraExportObjetoNegocio(final HashMap hashValores, final Integer idObjetoNegocio, String sqlDelete, final String filterAditional, final String order,
            final String[] fieldValidExistence, final String[] fieldValidExclusion, final String type) throws ServiceException, Exception {
        final CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
        final ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
        ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
        objetoNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
        objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioService.restore(objetoNegocioDTO);
        final Collection col = camposObjetoNegocioService.findByIdObjetoNegocio(idObjetoNegocio);
        String sqlCondicao = "";
        String sqlCampos = "";

        final String excluirAoExportar = (String) hashValores.get("excluirAoExportar".toUpperCase());

        // Antes de fazer a exportacao, faz o sincronismo com o DB, pois pode estar desatualizado!
        final DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
        dataBaseMetaDadosUtil.sincronizaObjNegDB(objetoNegocioDTO.getNomeTabelaDB(), false);
        System.out.println("Sincronizando tabela: " + objetoNegocioDTO.getNomeTabelaDB());

        hashValores.put("NOMETABELADB", objetoNegocioDTO.getNomeTabelaDB());
        String tabelasTratadas = (String) hashValores.get("TABELASTRATADAS");
        tabelasTratadas = UtilStrings.nullToVazio(tabelasTratadas);
        tabelasTratadas = tabelasTratadas + "'" + objetoNegocioDTO.getNomeTabelaDB() + "'";
        hashValores.put("TABELASTRATADAS", tabelasTratadas);

        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
                if (!sqlCampos.trim().equalsIgnoreCase("")) {
                    sqlCampos += ",";
                }
                sqlCampos = sqlCampos + camposObjetoNegocioDto.getNomeDB();
                final String cond = (String) hashValores.get("COND_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio());
                String valor = (String) hashValores.get("VALOR_" + camposObjetoNegocioDto.getIdCamposObjetoNegocio());
                if (!UtilStrings.nullToVazio(cond).trim().equalsIgnoreCase("") && !UtilStrings.nullToVazio(valor).trim().equalsIgnoreCase("")) {
                    sqlCondicao = sqlCondicao + " " + camposObjetoNegocioDto.getNomeDB();
                    if (cond != null && cond.equalsIgnoreCase("1")) {
                        sqlCondicao = sqlCondicao + " <> ";
                    } else if (cond != null && cond.equalsIgnoreCase("2")) {
                        sqlCondicao = sqlCondicao + " > ";
                    } else if (cond != null && cond.equalsIgnoreCase("3")) {
                        sqlCondicao = sqlCondicao + " < ";
                    } else {
                        sqlCondicao = sqlCondicao + " " + cond + " ";
                    }
                    final boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
                    if (isStringType) {
                        if (cond.equalsIgnoreCase("=") || cond.equalsIgnoreCase("1") || cond.equalsIgnoreCase("2") || cond.equalsIgnoreCase("3")) {
                            valor = valor.replaceAll("'", "");
                            valor = "'" + valor + "'";
                        }
                    }
                    if (cond != null && !cond.trim().equalsIgnoreCase("IS NULL")) {
                        sqlCondicao = sqlCondicao + valor;
                    }
                }
            }
        }
        String sqlFinal = "SELECT " + sqlCampos + " FROM " + objetoNegocioDTO.getNomeTabelaDB();
        sqlDelete = "DELETE FROM " + objetoNegocioDTO.getNomeTabelaDB();
        if (!sqlCondicao.trim().equalsIgnoreCase("")) {
            if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
                sqlFinal = sqlFinal + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
                sqlDelete = sqlDelete + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
            } else {
                sqlFinal = sqlFinal + " WHERE " + sqlCondicao;
                sqlDelete = sqlDelete + " WHERE " + sqlCondicao;
            }
        } else {
            if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
                sqlFinal = sqlFinal + " WHERE (" + filterAditional + ")";
                sqlDelete = sqlDelete + " WHERE (" + filterAditional + ")";
            }
        }
        if (order != null && !order.trim().equalsIgnoreCase("")) {
            sqlFinal += " ORDER BY " + order;
        }

        String sqlDeleteAux = (String) hashValores.get("COMMANDDELETE");
        String sqlExportAux = (String) hashValores.get("COMMAND");

        if (!UtilStrings.nullToVazio(sqlDeleteAux).trim().equalsIgnoreCase("")) {
            sqlDeleteAux = sqlDelete + "; " + UtilStrings.nullToVazio(sqlDeleteAux);
        } else {
            sqlDeleteAux = sqlDelete;
        }
        if (!UtilStrings.nullToVazio(sqlExportAux).trim().equalsIgnoreCase("")) {
            sqlExportAux = sqlFinal + "; " + UtilStrings.nullToVazio(sqlExportAux);
        } else {
            sqlExportAux = sqlFinal;
        }

        hashValores.put("COMMANDDELETE", sqlDeleteAux);
        hashValores.put("COMMAND", sqlExportAux);
        final JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
        List lst = null;
        try {
            lst = jdbcEngine.execSQL(sqlFinal, null, 0);
        } catch (final Exception e) {
            e.printStackTrace();
            return new StringBuilder("OCORREU ERRO NA GERACAO DOS DADOS!" + e.getMessage());
        }
        final StringBuilder strXML = new StringBuilder();
        strXML.append("<table name='" + objetoNegocioDTO.getNomeTabelaDB() + "' type='" + type + "'>\n");
        strXML.append("<command><![CDATA[" + sqlFinal + "]]></command>\n");
        if (excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S")) {
            strXML.append("<commandDelete><![CDATA[" + sqlDelete + "]]></commandDelete>\n");
        } else {
            strXML.append("<commandDelete>NONE</commandDelete>\n");
        }

        if (lst != null) {
            int j = 0;
            for (final Iterator itDados = lst.iterator(); itDados.hasNext();) {
                final Object[] obj = (Object[]) itDados.next();
                int i = 0;
                j++;
                strXML.append("<record number='" + j + "'>\n");
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();

                    final boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());

                    String key = "n";
                    if (camposObjetoNegocioDto.getPk() != null && camposObjetoNegocioDto.getPk().equalsIgnoreCase("S")) {
                        key = "y";
                    }

                    String sequence = "n";
                    if (camposObjetoNegocioDto.getSequence() != null && camposObjetoNegocioDto.getSequence().equalsIgnoreCase("S")) {
                        sequence = "y";
                    }

                    String check = "n";
                    if (fieldValidExistence != null) {
                        for (final String s : fieldValidExistence) {
                            if (camposObjetoNegocioDto.getNome().equalsIgnoreCase(s)) {
                                check = "y";
                            }
                        }
                    }

                    String exclusion = "n";
                    if (fieldValidExclusion != null) {
                        for (final String s : fieldValidExclusion) {
                            if (camposObjetoNegocioDto.getNome().equalsIgnoreCase(s)) {
                                exclusion = "y";
                            }
                        }
                    }

                    strXML.append("<field name='" + camposObjetoNegocioDto.getNomeDB() + "' key='" + key + "' sequence='" + sequence + "' check='" + check + "' exclusion='"
                            + exclusion + "' type='" + camposObjetoNegocioDto.getTipoDB().trim() + "'>");
                    if (isStringType) {
                        strXML.append("<![CDATA[");
                    }

                    if (objetoNegocioDTO.getNomeTabelaDB().equalsIgnoreCase("ACORDONIVELSERVICO")
                            && (camposObjetoNegocioDto.getNomeDB().equalsIgnoreCase("CRIADOEM") || camposObjetoNegocioDto.getNomeDB().equalsIgnoreCase("MODIFICADOEM"))
                            && obj[i] == null) {
                        strXML.append("0000-00-00 00:00:00");
                    } else {
                        strXML.append(obj[i]);
                    }

                    if (isStringType) {
                        strXML.append("]]>");
                    }
                    strXML.append("</field>\n");

                    i++;
                }

                strXML.append("</record>\n");
            }
        }
        strXML.append("</table>\n");

        return strXML;
    }

}
