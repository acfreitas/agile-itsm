package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.MovimentacaoPessoalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class MovimentacaoPessoalDao extends CrudDaoDefaultImpl {

    public MovimentacaoPessoalDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idMovimentacaoPessoal", "idMovimentacaoPessoal", true, true, false, false));
        listFields.add(new Field("idEmpregado", "idEmpregado", false, false, false, false));
        listFields.add(new Field("data", "data", false, false, false, false));
        listFields.add(new Field("tipoMovimentacao", "tipoMovimentacao", false, false, false, false));
        listFields.add(new Field("idResponsavel", "idResponsavel", false, false, false, false));
        listFields.add(new Field("idUnidade", "idUnidade", false, false, false, false));
        listFields.add(new Field("idCargo", "idCargo", false, false, false, false));
        listFields.add(new Field("idCentroResultado", "idCentroResultado", false, false, false, false));
        listFields.add(new Field("idProjeto", "idProjeto", false, false, false, false));
        listFields.add(new Field("matricula", "matricula", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "RH_MovimentacaoPessoal";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return MovimentacaoPessoalDTO.class;
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection findByIdEmpregado(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idEmpregado", "=", parm));
        ordenacao.add(new Order("idEmpregado"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdEmpregado(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idEmpregado", "=", parm));
        super.deleteByCondition(condicao);
    }

}
