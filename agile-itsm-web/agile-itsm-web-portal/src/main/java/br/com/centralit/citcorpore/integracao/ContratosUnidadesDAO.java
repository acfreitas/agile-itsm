package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ContratosUnidadesDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ContratosUnidadesDAO extends CrudDaoDefaultImpl {

    public ContratosUnidadesDAO() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<ContratosUnidadesDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idUnidade", "idUnidade", true, false, false, false));
        listFields.add(new Field("idContrato", "idContrato", true, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "contratosunidades";
    }

    @Override
    public Collection<ContratosUnidadesDTO> list() throws PersistenceException {
        return null;
    }

    public void deleteByIdUnidade(final Integer idUnidade) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idUnidade", "=", idUnidade));
        super.deleteByCondition(condicao);
    }

    @Override
    public Class<ContratosUnidadesDTO> getBean() {
        return ContratosUnidadesDTO.class;
    }

    public Collection<ContratosUnidadesDTO> findByIdUnidade(final Integer idUnidade) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        ordenacao.add(new Order("idContrato"));
        condicao.add(new Condition("idUnidade", "=", idUnidade));
        return super.findByCondition(condicao, ordenacao);
    }

}
