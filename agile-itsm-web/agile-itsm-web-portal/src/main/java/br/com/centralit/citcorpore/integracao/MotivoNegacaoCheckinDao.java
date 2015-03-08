package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MotivoNegacaoCheckinDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.core.DataBase;
import br.com.citframework.util.Constantes;

/**
 * DAO para manipulação de {@link MotivoNegacaoCheckinDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 06/10/2014
 *
 */
public class MotivoNegacaoCheckinDao extends CrudDaoDefaultImpl {

    public MotivoNegacaoCheckinDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idMotivo", "idMotivo", true, true, false, true));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "motivonegacaocheckin";
    }

    @Override
    public Collection<MotivoNegacaoCheckinDTO> list() throws PersistenceException {
        return super.list("descricao");
    }

    @Override
    public Class<MotivoNegacaoCheckinDTO> getBean() {
        return MotivoNegacaoCheckinDTO.class;
    }

    @Override
    public Collection<MotivoNegacaoCheckinDTO> find(final IDto motivo) throws PersistenceException {
        return null;
    }

    /**
     * Verifica se o registro informado já consta gravado no BD. Considera apenas registros ativos.
     *
     * @param descricao
     *            descrição do motivo a ser consultado
     * @return boolean
     * @throws Exception
     */
    public boolean hasWithSameName(final MotivoNegacaoCheckinDTO motivo) throws PersistenceException {
        final DataBase db = DataBase.fromStringId(CITCorporeUtil.SGBD_PRINCIPAL);

        String function = "";
        switch (db) {
        case MSSQLSERVER:
            function = "dbo.REMOVE_ACENTO";
            break;
        case POSTGRESQL:
        case ORACLE:
            function = "REMOVE_ACENTO";
            break;
        }

        final List<Object> params = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT m.idmotivo ");
        sql.append("FROM   motivonegacaocheckin m ");
        sql.append("WHERE  UPPER(");
        sql.append(function);
        sql.append("(m.descricao)) = UPPER(");
        sql.append(function);
        sql.append("(?)) ");

        params.add(motivo.getDescricao());

        if (motivo.getIdMotivo() != null) {
            params.add(motivo.getIdMotivo());
            sql.append("       AND m.idmotivo <> ?");
        }

        sql.append("       AND m.datafim IS NULL ");

        final List<?> retorno = this.execSQL(sql.toString(), params.toArray());

        if (retorno != null && retorno.size() > 0) {
            return true;
        }
        return false;
    }

    public Collection<MotivoNegacaoCheckinDTO> listAllActiveDeniedReasons() throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("dataFim", "is", null));
        ordenacao.add(new Order("descricao"));
        return super.findByCondition(condicao, ordenacao);
    }

}
