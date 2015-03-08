package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CompetenciaCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CompetenciaCurriculoDao extends CrudDaoDefaultImpl {

    public CompetenciaCurriculoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        final List lst = new ArrayList();
        lst.add("idCompetencia");
        return super.find(obj, lst);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idCompetencia", "idCompetencia", true, true, false, false));
        listFields.add(new Field("descricaoCompetencia", "descricaoCompetencia", false, false, false, false));
        listFields.add(new Field("nivelCompetencia", "nivelCompetencia", false, false, false, false));
        listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_competencia";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return CompetenciaCurriculoDTO.class;
    }

    public Collection findByIdCurriculo(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idCurriculo", "=", parm));
        ordenacao.add(new Order("idCompetencia"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdCurriculo(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idCurriculo", "=", parm));
        super.deleteByCondition(condicao);
    }

}
