package br.com.centralit.citcorpore.integracao;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ServicoContratoDao extends CrudDaoDefaultImpl {
    public ServicoContratoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public static String strSGBDPrincipal = null;

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idServicoContrato", "idServicoContrato", true, true, false, false));
        listFields.add(new Field("idServico", "idServico", false, false, false, false));
        listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
        listFields.add(new Field("idCondicaoOperacao", "idCondicaoOperacao", false, false, false, false));
        listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
        listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
        listFields.add(new Field("observacao", "observacao", false, false, false, false));
        listFields.add(new Field("custo", "custo", false, false, false, false));
        listFields.add(new Field("restricoesPressup", "restricoesPressup", false, false, false, false));
        listFields.add(new Field("objetivo", "objetivo", false, false, false, false));
        listFields.add(new Field("permiteSLANoCadInc", "permiteSLANoCadInc", false, false, false, false));
        listFields.add(new Field("linkProcesso", "linkProcesso", false, false, false, false));
        listFields.add(new Field("descricaoProcesso", "descricaoProcesso", false, false, false, false));
        listFields.add(new Field("tipoDescProcess", "tipoDescProcess", false, false, false, false));
        listFields.add(new Field("areaRequisitante", "areaRequisitante", false, false, false, false));
        listFields.add(new Field("idModeloEmailCriacao", "idModeloEmailCriacao", false, false, false, false));
        listFields.add(new Field("idModeloEmailFinalizacao", "idModeloEmailFinalizacao", false, false, false, false));
        listFields.add(new Field("idModeloEmailAcoes", "idModeloEmailAcoes", false, false, false, false));
        listFields.add(new Field("idGrupoNivel1", "idGrupoNivel1", false, false, false, false));
        listFields.add(new Field("idGrupoExecutor", "idGrupoExecutor", false, false, false, false));
        listFields.add(new Field("idGrupoAprovador", "idGrupoAprovador", false, false, false, false));
        listFields.add(new Field("idCalendario", "idCalendario", false, false, false, false));
        listFields.add(new Field("permSLATempoACombinar", "permSLATempoACombinar", false, false, false, false));
        listFields.add(new Field("permMudancaSLA", "permMudancaSLA", false, false, false, false));
        listFields.add(new Field("permMudancaCalendario", "permMudancaCalendario", false, false, false, false));
        listFields.add(new Field("deleted", "deleted", false, false, false, false));
        listFields.add(new Field("expandir", "expandir", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "ServicoContrato";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return ServicoContratoDTO.class;
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection findByIdServico(final Integer parm) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List parametro = new ArrayList();
        List list = new ArrayList();
        sql.append("SELECT idServico, idContrato, deleted, dataInicio, dataFim ");
        sql.append("FROM " + getTableName() + " ");
        sql.append("WHERE idservico = " + parm + " ");
        sql.append("AND (datafim is null or datafim > ? )");
        parametro.add(UtilDatas.getDataAtual());
        list = this.execSQL(sql.toString(), parametro.toArray());
        final List listRetorno = new ArrayList();
        listRetorno.add("idServico");
        listRetorno.add("idContrato");
        listRetorno.add("deleted");
        listRetorno.add("dataInicio");
        listRetorno.add("dataFim");

        final List result = engine.listConvertion(getBean(), list, listRetorno);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }

    public void deleteByIdServico(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idServico", "=", parm));
        super.deleteByCondition(condicao);
    }

    public Collection findByIdContrato(final Integer parm) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List parametro = new ArrayList();
        List list = new ArrayList();
        sql.append("SELECT " + this.getNamesFieldsStr() + " ");
        sql.append("FROM " + getTableName() + " ");
        sql.append("WHERE idContrato = ? ");
        sql.append("AND (deleted is null or deleted <> 'y')");

        parametro.add(parm);

        list = this.execSQL(sql.toString(), parametro.toArray());

        final List listRetorno = this.getListNamesFieldClass();

        List result = new ArrayList();
        result = engine.listConvertion(getBean(), list, listRetorno);

        return result;

    }

    public Collection findServicoComNomeByIdContrato(final Integer parm) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List parametro = new ArrayList();
        List list = new ArrayList();
        sql.append("SELECT sc.idservicocontrato, s.nomeservico FROM servicocontrato sc ");
        sql.append("inner join servico s on s.idservico = sc.idservico WHERE idContrato = ? ");
        sql.append("AND (sc.deleted is null or sc.deleted <> 'y')");

        parametro.add(parm);

        list = this.execSQL(sql.toString(), parametro.toArray());

        final List listRetorno = new ArrayList();
        listRetorno.add("idServicoContrato");
        listRetorno.add("nomeServico");

        List result = new ArrayList();
        result = engine.listConvertion(getBean(), list, listRetorno);

        return result;

    }

    public Collection findByIdContratoDistinct(final Integer idContrato) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List parametro = new ArrayList();
        List list = new ArrayList();
        sql.append("SELECT distinct servicocontrato.idServico ");
        sql.append("FROM servicocontrato ");
        sql.append("INNER JOIN servico ON servico.idservico = servicocontrato.idservico ");
        sql.append("WHERE servicocontrato.idcontrato = " + idContrato);
        list = this.execSQL(sql.toString(), parametro.toArray());
        final List listRetorno = new ArrayList();
        listRetorno.add("idServico");

        final List result = engine.listConvertion(getBean(), list, listRetorno);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }

    public Collection findServicoContratoByIdContrato(final Integer idContrato) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List parametro = new ArrayList();
        List list = new ArrayList();
        sql.append("SELECT servicocontrato.idServicoContrato, servico.nomeServico, servico.idServico ");
        sql.append("FROM servicocontrato servicocontrato ");
        sql.append("INNER JOIN servico servico ON servicocontrato.idservico = servico.idservico ");
        sql.append("WHERE servicocontrato.idContrato = " + idContrato);
        sql.append(" AND (servicocontrato.deleted is null or servicocontrato.deleted <> 'y') ");
        sql.append("order by servico.nomeservico ");
        list = this.execSQL(sql.toString(), parametro.toArray());
        final List listRetorno = new ArrayList();
        listRetorno.add("idServicoContrato");
        listRetorno.add("nomeServico");
        listRetorno.add("idServico");

        final List result = engine.listConvertion(getBean(), list, listRetorno);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }

    /**
     * Localiza os serviços contrato utilizando o id do serviço
     *
     * @param idServico
     * @return
     * @throws Exception
     */
    public Collection findServicoContratoByIdServico(final Integer idServico) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List parametro = new ArrayList();
        List list = new ArrayList();
        sql.append(" SELECT servicocontrato.idServicoContrato ");
        sql.append(" FROM servicocontrato ");
        sql.append(" WHERE servicocontrato.idServico = " + idServico);
        sql.append(" AND (servicocontrato.deleted is null or servicocontrato.deleted <> 'y') ");

        list = this.execSQL(sql.toString(), parametro.toArray());
        final List listRetorno = new ArrayList();
        listRetorno.add("idServicoContrato");

        final List result = engine.listConvertion(getBean(), list, listRetorno);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }

    /**
     * Retorna ServicoContrato Ativo de acordo com o idContrato e idServico informado.
     *
     * @param idContrato
     *            - Identificador do Contrato.
     * @param idServico
     *            - Identificador do Serviço.
     * @return ServicoContratoDTO
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public ServicoContratoDTO findByIdContratoAndIdServico(final Integer idContrato, final Integer idServico)
            throws PersistenceException {

        final StringBuilder sql = new StringBuilder();

        sql.append(" SELECT ");
        sql.append(this.getNamesFieldsStr());
        sql.append(" FROM servicocontrato ");
        sql.append(" WHERE idcontrato = ? AND idservico = ? AND (datafim is null OR datafim > ? ) AND (deleted <> 'y' OR deleted <> 'Y' OR deleted is null) ");

        final List parametros = new ArrayList();

        parametros.add(idContrato);
        parametros.add(idServico);
        parametros.add(UtilDatas.getDataAtual());

        final List list = this.execSQL(sql.toString(), parametros.toArray());

        final List<ServicoContratoDTO> listServicoContratoDto = this.listConvertion(this.getBean(), list,
                this.getListNamesFieldClass());

        if (listServicoContratoDto != null && !listServicoContratoDto.isEmpty()) {
            return listServicoContratoDto.get(0);
        } else {
            return null;
        }

    }

    /**
     * @author euler.ramos
     * @return
     * @throws Exception
     */
    public ServicoContratoDTO findByIdServicoContrato(final Integer idServicoContrato) throws PersistenceException {

        final StringBuilder sql = new StringBuilder();

        sql.append("SELECT servicocontrato.idServicoContrato, servico.nomeServico ");
        sql.append("FROM servicocontrato JOIN servico servico ON servicocontrato.idservico = servico.idservico ");
        sql.append("WHERE idservicocontrato = ? AND (servicocontrato.datafim is null OR servicocontrato.datafim > ? ) AND (servicocontrato.deleted <> 'y' OR servicocontrato.deleted <> 'Y' OR servicocontrato.deleted is null) ");

        final List parametros = new ArrayList();
        final List listaRetorno = new ArrayList();

        listaRetorno.add("idServicoContrato");
        listaRetorno.add("nomeServico");

        parametros.add(idServicoContrato);
        parametros.add(UtilDatas.getDataAtual());

        final List list = this.execSQL(sql.toString(), parametros.toArray());

        final List<ServicoContratoDTO> listServicoContratoDto = this.listConvertion(this.getBean(), list, listaRetorno);

        if (listServicoContratoDto != null && !listServicoContratoDto.isEmpty()) {
            return listServicoContratoDto.get(0);
        } else {
            return null;
        }

    }

    public void deleteByIdContrato(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idContrato", "=", parm));
        super.deleteByCondition(condicao);
    }

    public void setDataFim(final Integer idServicoContrato) throws PersistenceException {

        final ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();

        servicoContratoDto.setIdServicoContrato(idServicoContrato);
        servicoContratoDto.setDataFim(UtilDatas.getDataAtual());

        super.updateNotNull(servicoContratoDto);
    }

    public Collection findByIdFornecedor(final Integer idFornecedor) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List parametro = new ArrayList();
        List list = new ArrayList();

        final Collection fields = getFields();
        final List listaNomes = new ArrayList();
        sql.append("SELECT ");
        int i = 0;
        for (final Iterator it = fields.iterator(); it.hasNext();) {
            final Field field = (Field) it.next();
            if (i > 0) {
                sql.append(", ");
            }
            sql.append("sc." + field.getFieldDB());
            listaNomes.add(field.getFieldClass());
            i++;
        }

        sql.append(" FROM " + getTableName());
        sql.append(" sc INNER JOIN contratos c ON c.idcontrato = sc.idcontrato WHERE c.idfornecedor = ? ");
        // sql.append("order by nomeservico ");

        parametro.add(idFornecedor);

        list = this.execSQL(sql.toString(), parametro.toArray());

        final List result = engine.listConvertion(getBean(), list, listaNomes);
        if (result == null || result.size() == 0) {
            return null;
        } else {
            return result;
        }
    }

    public boolean validaServicoContrato(final Integer idContrato, final Integer idServico) throws PersistenceException {

        final StringBuilder sql = new StringBuilder();
        final List parametro = new ArrayList();

        List list = new ArrayList();
        sql.append("SELECT idServicoContrato ");
        sql.append("FROM " + getTableName() + " ");
        sql.append("WHERE idContrato = ? ");
        sql.append("AND idServico = ? ");
        sql.append("AND deleted IS NULL ");

        parametro.add(idContrato);
        parametro.add(idServico);

        list = this.execSQL(sql.toString(), parametro.toArray());
        final List listRetorno = new ArrayList();
        listRetorno.add("idServicoContrato");

        final List result = engine.listConvertion(getBean(), list, listRetorno);
        if (result == null || result.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param idServicoContrato
     * @param data
     * @throws PersistenceException
     * @author cledson.junioro
     */
    public void updateServicoContrato(final Integer idServicoContrato, final Date data) throws PersistenceException {
        final List parametros = new ArrayList();
        if (data != null) {
            parametros.add(data);
        } else {
            parametros.add(null);
        }
        parametros.add("y");
        parametros.add(idServicoContrato);
        final String sql = "UPDATE " + getTableName() + " SET datafim = ?, deleted = ? WHERE idServicoContrato = ?";
        execUpdate(sql, parametros.toArray());
    }

    /**
     * @param servicoContratoDTO
     * @param paginacao
     * @param pagAtual
     * @param pagAtualAux
     * @param totalPag
     * @param quantidadePaginator
     * @param campoPesquisa
     * @return
     * @throws Exception
     * @author cledson.junioro
     *
     *         Paginação da tela Administração de Contratos
     */
    public Collection findByIdContratoPaginada(final ServicoContratoDTO servicoContratoDTO, final String paginacao,
            Integer pagAtual, final Integer pagAtualAux, Integer totalPag, final Integer quantidadePaginator,
            final String campoPesquisa) throws PersistenceException {
        if (strSGBDPrincipal == null) {
            strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;
            strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
        }
        final StringBuilder sql = new StringBuilder();
        String trim = "";
        String sql2 = "";

        sql.append("SELECT sc.idServicoContrato,sc.idServico,sc.idContrato,sc.idCondicaoOperacao,sc.dataInicio,sc.dataFim,sc.observacao,");
        sql.append("sc.custo,sc.restricoesPressup,sc.objetivo,sc.permiteSLANoCadInc,sc.linkProcesso,sc.descricaoProcesso,sc.tipoDescProcess,");
        sql.append("sc.areaRequisitante,sc.idModeloEmailCriacao,sc.idModeloEmailFinalizacao,sc.idModeloEmailAcoes,sc.idGrupoNivel1,sc.idGrupoExecutor,");
        sql.append("sc.idGrupoAprovador,sc.idCalendario,sc.permSLATempoACombinar,sc.permMudancaSLA,sc.permMudancaCalendario,sc.deleted, sc.expandir ");
        sql.append(" FROM " + getTableName() + " sc INNER JOIN servico s ON sc.idServico = s.idServico");

        if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
            trim = "";
        } else {
            trim = " order by trim(s.nomeservico)";
        }

        sql.append(" WHERE sc.idContrato = " + servicoContratoDTO.getIdContrato()
                + " AND (upper(s.nomeservico) like '%" + campoPesquisa.toUpperCase()
                + "%' OR upper(s.siglaabrev) like '%" + campoPesquisa.toUpperCase()
                + "%') AND (sc.deleted is null or sc.deleted <> 'y')" + trim);
        final List listaTotal = this.execSQL(sql.toString(), null);
        if (quantidadePaginator != null) {
            if (strSGBDPrincipal.equalsIgnoreCase("POSTGRESQL") || strSGBDPrincipal.equalsIgnoreCase("POSTGRES")) {
                sql.append(" LIMIT " + quantidadePaginator + " OFFSET " + pagAtual);
            } else if (strSGBDPrincipal.equalsIgnoreCase("MYSQL")) {
                sql.append(" LIMIT " + pagAtual + ", " + quantidadePaginator);
            } else if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
                Integer quantidadePaginator2 = new Integer(0);
                quantidadePaginator2 = quantidadePaginator + pagAtual;
                sql2 = sql.toString();
                sql.delete(0, sql.length());
                sql.append("SELECT sc.idServicoContrato,sc.idServico,sc.idContrato,sc.idCondicaoOperacao,sc.dataInicio,sc.dataFim,sc.observacao,");
                sql.append("sc.custo,sc.restricoesPressup,sc.objetivo,sc.permiteSLANoCadInc,sc.linkProcesso,sc.descricaoProcesso,sc.tipoDescProcess,");
                sql.append("sc.areaRequisitante,sc.idModeloEmailCriacao,sc.idModeloEmailFinalizacao,sc.idModeloEmailAcoes,sc.idGrupoNivel1,");
                sql.append("sc.idGrupoExecutor,sc.idGrupoAprovador,sc.idCalendario,sc.permSLATempoACombinar,sc.permMudancaSLA,sc.permMudancaCalendario,sc.deleted, sc.expandir ");
                sql.append(" FROM " + getTableName() + " sc INNER JOIN servico s ON sc.idServico = s.idServico ");
                sql.append(" WHERE sc.idContrato = " + servicoContratoDTO.getIdContrato()
                        + " AND ( upper(s.nomeservico) like '%" + campoPesquisa.toUpperCase()
                        + "%' OR upper(s.siglaabrev)  like '%" + campoPesquisa.toUpperCase() + "%')");
                sql.append(" AND (sc.deleted is null or sc.deleted <> 'y') and IDSERVICOCONTRATO in ");
                sql.append("(select IDSERVICOCONTRATO from (select table_.*, rownum rownum_ from (select count(*) over() as totalRowCount,");
                sql.append(sql2.substring(6, sql2.length()));
                sql.append(") table_ where rownum <= " + quantidadePaginator2
                        + " ) as SERVICOCONTRATO where rownum_ > " + pagAtual + ")");
            } else if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                Integer quantidadePaginator2 = new Integer(0);
                quantidadePaginator2 = quantidadePaginator + pagAtual;
                if (pagAtual != 1) {
                    pagAtual++;
                }
                sql2 = sql.toString();
                sql.delete(0, sql.length());
                sql.append("SELECT idServicoContrato,idServico,idContrato,idCondicaoOperacao,dataInicio,dataFim,observacao,");
                sql.append("custo,restricoesPressup,objetivo,permiteSLANoCadInc,linkProcesso,descricaoProcesso,tipoDescProcess,");
                sql.append("areaRequisitante,idModeloEmailCriacao,idModeloEmailFinalizacao,idModeloEmailAcoes,idGrupoNivel1,");
                sql.append("idGrupoExecutor,idGrupoAprovador,idCalendario,permSLATempoACombinar,permMudancaSLA,permMudancaCalendario,deleted,expandir");
                sql.append(" FROM" + "(select ROW_NUMBER() OVER( order by s.nomeservico) as rownum_, ");
                sql.append(sql2.substring(6, sql2.length()) + ")  as table_ where table_.rownum_ between " + pagAtual
                        + " and " + quantidadePaginator2);
            }
        }

        if (listaTotal != null) {
            servicoContratoDTO.setTotalItens(listaTotal.size());
            if (listaTotal.size() > quantidadePaginator) {
                totalPag = listaTotal.size() / quantidadePaginator;
                if (listaTotal.size() % quantidadePaginator != 0) {
                    totalPag = totalPag + 1;
                }
            } else {
                totalPag = 1;
            }
        }
        servicoContratoDTO.setTotalPagina(totalPag);
        final List lista = execSQL(sql.toString(), null);
        if (lista == null || lista.size() == 0) {
            final TransactionControler tc = this.getTransactionControler();
            if (tc != null) {
                tc.close();
            }
            return null;
        }

        List result = new ArrayList();
        if (lista == null || lista.size() == 0) {
            final TransactionControler tc = this.getTransactionControler();
            if (tc != null) {
                tc.close();
            }
            return result;
        }

        final TransactionControler tc = this.getTransactionControler();
        if (tc != null) {
            tc.close();
        }
        final List listRetorno = this.getListNamesFieldClass();

        result = engine.listConvertion(getBean(), lista, listRetorno);

        return result;
    }

    /**
     *
     * @param idServicoContrato
     *            metodo para buscar por inner join informações sobre o servico pelo idServicoContrato para exibir no
     *            carrinho de serviços(Portal)
     * @return ServicoDTO
     * @throws Exception
     */
    public ServicoContratoDTO findByIdServicoContrato(final Integer idServico, final Integer idContrato)
            throws PersistenceException {
        final List parametro = new ArrayList();
        final List fields = new ArrayList();
        List list = new ArrayList();
        final String sql = "select servicocontrato.idservicocontrato, valorservicocontrato.valorservico, servico.nomeservico, categoriaservico.nomecategoriaservico "
                + "from servicocontrato servicocontrato "
                + "left join valorservicocontrato valorservicocontrato on valorservicocontrato.idservicocontrato =  servicocontrato.idservicocontrato "
                + "inner join servico servico on servicocontrato.idservico = servico.idservico "
                + "inner join categoriaservico categoriaservico on servico.idcategoriaservico = categoriaservico.idcategoriaservico "
                + "where valorservicocontrato.datafim is null "
                + "and servicocontrato.datafim is null and servicocontrato.idservico = ? and servicocontrato.idcontrato = ? "
                + "and (servico.deleted is null OR servico.deleted = 'n') "
				+ "and (servicocontrato.deleted is null OR servicocontrato.deleted = 'n')";
        parametro.add(idServico);
        parametro.add(idContrato);
        list = this.execSQL(sql, parametro.toArray());
        fields.add("idServicoContrato");
        fields.add("valorServico");
        fields.add("nomeServico");
        fields.add("nomeCategoriaServico");
        if (list != null && !list.isEmpty()) {
            return (ServicoContratoDTO) this.listConvertion(getBean(), list, fields).get(0);
        } else {
            return null;
        }
    }

    public Collection<ServicoContratoDTO> findAtivosByIdGrupo(final Integer idGrupo) throws PersistenceException {
        final List parametro = new ArrayList();
        final List fields = new ArrayList();
        List list = new ArrayList();
        final String sql = "select servicocontrato.idservicocontrato "
                + "from servicocontrato servicocontrato "
                + "where (servicocontrato.idgruponivel1 = ? or servicocontrato.idgrupoexecutor = ? or servicocontrato.idgrupoaprovador = ?) "
                + "and servicocontrato.datafim is null";

        parametro.add(idGrupo);
        parametro.add(idGrupo);
        parametro.add(idGrupo);
        fields.add("idServicoContrato");
        list = this.execSQL(sql, parametro.toArray());

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, fields);
        } else {
            return null;
        }
    }

    /**
     * @author euler.ramos
     * @param idCalendario
     * @return
     * @throws Exception
     */
    public ArrayList<ServicoContratoDTO> findByIdCalendario(final Integer idCalendario) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idCalendario", "=", idCalendario));
        ordenacao.add(new Order("idServico"));
        final ArrayList<ServicoContratoDTO> result = (ArrayList<ServicoContratoDTO>) super.findByCondition(condicao,
                ordenacao);
        return result == null ? new ArrayList<ServicoContratoDTO>() : result;
    }

    /**
     * Verifica se existe Solicitação aberta vinculado com serviço que está sendo excluído.
     *
     * @param idServico
     * @return
     * @throws Exception
     * @author mario.haysaki
     */
    public boolean verificaSeExisteSolicitacaoAbertaVinculadoComServico(final Integer idServico,
            final Integer idContrato) throws PersistenceException {

        final StringBuilder sql = new StringBuilder();
        List list = new ArrayList();
        final List parametros = new ArrayList();

        sql.append("select COUNT(*) from solicitacaoservico sol  ");
        sql.append("inner join servicocontrato sc on sol.idservicocontrato = sc.idservicocontrato ");
        sql.append("inner join execucaosolicitacao ex on sol.idsolicitacaoservico = ex.idsolicitacaoservico ");
        sql.append("inner join bpm_instanciafluxo ins on ex.idinstanciafluxo = ins.idinstancia  ");
        sql.append("where  sc.idServico = ? and sc.idContrato = ? and (ins.situacao <>'Encerrada' and sol.situacao<>'Fechada') ");

        parametros.add(idServico);
        parametros.add(idContrato);

        list = this.execSQL(sql.toString(), parametros.toArray());
        Long totalSolLong = 0l;
        BigDecimal totalSolBigDecimal;
        Integer totalSolInteger;
        if (list != null) {
            final Object[] totalSol = (Object[]) list.get(0);
            if (totalSol != null && totalSol.length > 0) {
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)
                        || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
                    totalSolLong = (Long) totalSol[0];
                }
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                    totalSolBigDecimal = (BigDecimal) totalSol[0];
                    totalSolLong = totalSolBigDecimal.longValue();
                }
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
                    totalSolInteger = (Integer) totalSol[0];
                    totalSolLong = Long.valueOf(totalSolInteger);
                }
            }
        }

        return list != null && !list.isEmpty() && totalSolLong > 0;

    }

    /**
     * Verifica se o serviço está vinculado com o Contrato
     *
     * @param idSolicitacaoServico
     * @return
     * @throws Exception
     * @author mario.haysaki
     */
    public boolean verificaServicoEstaVinculadoContrato(final Integer idSolicitacaoServico) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        List list = new ArrayList();
        final List parametros = new ArrayList();

        sql.append("select COUNT(*) from servicocontrato sc ");
        sql.append("inner join solicitacaoservico sol on sol.idservicocontrato = sc.idservicocontrato ");
        sql.append("where (sc.datafim is null or sc.datafim >= '" + UtilDatas.getDataAtual()
                + "') and (sc.deleted ='n' or sc.deleted is null) and sol.idsolicitacaoservico = ? ");

        parametros.add(idSolicitacaoServico);

        list = this.execSQL(sql.toString(), parametros.toArray());
        Long totalSolLong = 0l;
        BigDecimal totalSolBigDecimal;
        Integer totalSolInteger;
        if (list != null) {
            final Object[] totalSol = (Object[]) list.get(0);
            if (totalSol != null && totalSol.length > 0) {
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)
                        || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
                    totalSolLong = (Long) totalSol[0];
                }
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                    totalSolBigDecimal = (BigDecimal) totalSol[0];
                    totalSolLong = totalSolBigDecimal.longValue();
                }
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
                    totalSolInteger = (Integer) totalSol[0];
                    totalSolLong = Long.valueOf(totalSolInteger);
                }
            }
        }

        return list != null && !list.isEmpty() && totalSolLong > 0;

    }

	public ServicoContratoDTO findByIdSolicitacaoServico(Integer idSolicitacaoServico) throws PersistenceException {
		
		 final StringBuilder  query = new StringBuilder();
		
		 query.append("SELECT sc.idgruponivel1, sc.idgrupoexecutor FROM servicocontrato sc ");

 		 query.append("INNER JOIN servico s ON s.idservico = sc.idservico ");

		 query.append("INNER JOIN solicitacaoservico ss ON ss.idservicocontrato = sc.idservicocontrato ");

		 query.append("WHERE ss.idsolicitacaoservico = ?");
		
		 List<String> fields = new ArrayList<String>();
		 
		 List parametro = new ArrayList<>();
		 
		 parametro.add(idSolicitacaoServico);
		 
	     fields.add("idGrupoNivel1");
	     
	     fields.add("idGrupoExecutor");
	     
	     List list = this.execSQL(query.toString(), parametro.toArray());

        if (list != null && !list.isEmpty()) {
            return (ServicoContratoDTO) this.listConvertion(ServicoContratoDTO.class, list, fields).get(0);
        } else {
            return null;
        }		 
	}

}
