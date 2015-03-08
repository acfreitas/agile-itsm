package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author breno.guimaraes
 *
 */
public class JustificativaParecerDao extends CrudDaoDefaultImpl {

    public JustificativaParecerDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<JustificativaParecerDTO> find(final IDto justificativa) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idjustificativa", "idJustificativa", true, true, false, false));
        listFields.add(new Field("descricaojustificativa", "descricaoJustificativa", false, false, false, false));
        listFields.add(new Field("aplicavelRequisicao", "aplicavelRequisicao", false, false, false, false));
        listFields.add(new Field("aplicavelCotacao", "aplicavelCotacao", false, false, false, false));
        listFields.add(new Field("aplicavelInspecao", "aplicavelInspecao", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
        listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "justificativaparecer";
    }

    @Override
    public Collection<JustificativaParecerDTO> list() throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("descricaoJustificativa"));
        return super.findByCondition(condicao, ordenacao);
    }

    @Override
    public Class<JustificativaParecerDTO> getBean() {
        return JustificativaParecerDTO.class;
    }

    public Collection<JustificativaParecerDTO> listAplicaveisCotacao() throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("aplicavelCotacao", "=", "S"));
        ordenacao.add(new Order("descricaoJustificativa"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<JustificativaParecerDTO> listAplicaveisRequisicao() throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("aplicavelRequisicao", "=", "S"));
        ordenacao.add(new Order("descricaoJustificativa"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<JustificativaParecerDTO> listAplicaveisInspecao() throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("aplicavelInspecao", "=", "S"));
        ordenacao.add(new Order("descricaoJustificativa"));
        return super.findByCondition(condicao, ordenacao);
    }

    public boolean consultarJustificativaAtiva(final JustificativaParecerDTO justificativaParecerDto) throws PersistenceException {
        final List<Object> parametro = new ArrayList<>();
        List<?> list = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();
        sql.append("select idjustificativa from ");
        sql.append(this.getTableName());
        sql.append(" where descricaojustificativa = ? and situacao = 'A'");

        parametro.add(justificativaParecerDto.getDescricaoJustificativa());

        if (justificativaParecerDto.getIdJustificativa() != null) {
            sql.append("and idjustificativa <> ?");
            parametro.add(justificativaParecerDto.getIdJustificativa());
        }

        list = this.execSQL(sql.toString(), parametro.toArray());
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }

    public Collection<JustificativaParecerDTO> listAplicaveisRequisicaoViagem() throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("aplicavelRequisicao", "=", "S"));
        condicao.add(new Condition("viagem", "=", "S"));
        ordenacao.add(new Order("descricaoJustificativa"));
        return super.findByCondition(condicao, ordenacao);
    }

}
