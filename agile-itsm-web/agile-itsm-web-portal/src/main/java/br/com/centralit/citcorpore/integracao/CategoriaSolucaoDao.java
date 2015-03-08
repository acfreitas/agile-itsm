package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaSolucaoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

public class CategoriaSolucaoDao extends CrudDaoDefaultImpl {

    public CategoriaSolucaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idCategoriaSolucao", "idCategoriaSolucao", true, true, false, false));
        listFields.add(new Field("idCategoriaSolucaoPai", "idCategoriaSolucaoPai", false, false, false, false));
        listFields.add(new Field("descricaoCategoriaSolucao", "descricaoCategoriaSolucao", false, false, false, false));
        listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
        listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "CategoriaSolucao";
    }

    @Override
    public Collection<CategoriaSolucaoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<CategoriaSolucaoDTO> getBean() {
        return CategoriaSolucaoDTO.class;
    }

    @Override
    public Collection<CategoriaSolucaoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection<CategoriaSolucaoDTO> findByIdCategoriaSolucaoPai(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idCategoriaSolucaoPai", "=", parm));
        ordenacao.add(new Order("descricaoCategoriaSolucao"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdCategoriaSolucaoPai(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idCategoriaSolucaoPai", "=", parm));
        super.deleteByCondition(condicao);
    }

    public Collection<CategoriaSolucaoDTO> findSemPai() throws PersistenceException {
        String sql = "SELECT idCategoriaSolucao, idCategoriaSolucaoPai, descricaoCategoriaSolucao, dataInicio, dataFim FROM categoriasolucao WHERE idCategoriaSolucaoPai IS NULL AND dataFim IS NULL AND ";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N') ";
        } else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql += "(deleted IS NULL OR deleted = 'N') ";
        } else {
            sql += "(deleted IS NULL OR deleted = 'N') ";
        }
        sql += " ORDER BY descricaoCategoriaSolucao ";

        final List<?> colDados = this.execSQL(sql, null);
        if (colDados != null) {
            return this.listConvertion(CategoriaSolucaoDTO.class, colDados, this.getResultFields());
        }
        return null;
    }

    public Collection<CategoriaSolucaoDTO> findByIdPai(final Integer idPaiParm) throws PersistenceException {
        String sql = "SELECT idCategoriaSolucao, idCategoriaSolucaoPai, descricaoCategoriaSolucao, dataInicio, dataFim FROM categoriasolucao "
                + "WHERE idCategoriaSolucaoPai = ? AND dataFim IS NULL AND ";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N') ";
        } else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql += "(deleted IS NULL OR deleted = 'N') ";
        } else {
            sql += "(deleted IS NULL OR deleted = 'N') ";
        }
        sql += "ORDER BY descricaoCategoriaSolucao ";

        final List<?> colDados = this.execSQL(sql, new Object[] {idPaiParm});
        if (colDados != null) {
            return this.listConvertion(CategoriaSolucaoDTO.class, colDados, this.getResultFields());
        }
        return null;
    }

    public Collection<CategoriaSolucaoDTO> verificaDescricaoDuplicadaCategoriaAoCriar(final String DescricaoCategoriaSolucao) throws PersistenceException {
        String sql = "select * from categoriasolucao where descricaocategoriasolucao = ? AND ";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N') ";
        } else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql += "(deleted IS NULL OR deleted = 'N') ";
        } else {
            sql += "(deleted IS NULL OR deleted = 'N') ";
        }
        sql += "ORDER BY descricaoCategoriaSolucao ";

        final List colDados = this.execSQL(sql, new Object[] {DescricaoCategoriaSolucao});
        if (colDados != null) {
            return this.listConvertion(CategoriaSolucaoDTO.class, colDados, this.getResultFields());
        }
        return null;
    }

    public Collection<CategoriaSolucaoDTO> verificaDescricaoDuplicadaCategoriaAoAtualizar(final Integer idCategoriaSolucao, final String DescricaoCategoriaSolucao)
            throws PersistenceException {
        String sql = "select * from categoriasolucao where descricaocategoriasolucao = ? AND idcategoriasolucao <> ? AND ";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N') ";
        } else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql += "(deleted IS NULL OR deleted = 'N') ";
        } else {
            sql += "(deleted IS NULL OR deleted = 'N') ";
        }
        sql += "ORDER BY descricaoCategoriaSolucao ";

        final List<?> colDados = this.execSQL(sql, new Object[] {DescricaoCategoriaSolucao, idCategoriaSolucao});
        if (colDados != null) {
            return this.listConvertion(CategoriaSolucaoDTO.class, colDados, this.getResultFields());
        }
        return null;
    }

    public Collection<CategoriaSolucaoDTO> listaCategoriasSolucaoAtivas() throws PersistenceException {
        final List<String> fields = new ArrayList<>();
        final String sql = "SELECT idCategoriaSolucao, descricaoCategoriaSolucao FROM categoriasolucao WHERE datafim is NULL AND (deleted is NULL OR deleted = 'n') ";

        final List<?> listaR = this.execSQL(sql, null);

        fields.add("idCategoriaSolucao");
        fields.add("descricaoCategoriaSolucao");

        return this.listConvertion(CategoriaSolucaoDTO.class, listaR, fields);
    }

    private List<String> resultFields;

    private List<String> getResultFields() {
        if (resultFields == null) {
            resultFields = new ArrayList<>();
            resultFields.add("idCategoriaSolucao");
            resultFields.add("idCategoriaSolucaoPai");
            resultFields.add("descricaoCategoriaSolucao");
            resultFields.add("dataInicio");
            resultFields.add("dataFim");
        }
        return resultFields;
    }

}
