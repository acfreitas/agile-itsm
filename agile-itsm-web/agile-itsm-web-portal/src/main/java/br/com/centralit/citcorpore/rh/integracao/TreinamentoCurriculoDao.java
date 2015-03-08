package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.TreinamentoCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class TreinamentoCurriculoDao extends CrudDaoDefaultImpl {

    public TreinamentoCurriculoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<TreinamentoCurriculoDTO> find(final IDto obj) throws PersistenceException {
        final List<String> lst = new ArrayList<>();
        lst.add("idTreinamento");
        return super.find(obj, lst);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idTreinamento", "idTreinamento", true, true, false, false));
        listFields.add(new Field("idCurso", "idCurso", false, false, false, false));
        listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_TreinamentoCurriculo";
    }

    @Override
    public Collection<TreinamentoCurriculoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<TreinamentoCurriculoDTO> getBean() {
        return TreinamentoCurriculoDTO.class;
    }

    public Collection<TreinamentoCurriculoDTO> findByIdCurriculo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idCurriculo", "=", parm));
        ordenacao.add(new Order("idTreinamento"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdCurriculo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idCurriculo", "=", parm));
        super.deleteByCondition(condicao);
    }

}
