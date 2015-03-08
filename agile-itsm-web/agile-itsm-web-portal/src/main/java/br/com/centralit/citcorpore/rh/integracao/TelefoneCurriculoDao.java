package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.TelefoneCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class TelefoneCurriculoDao extends CrudDaoDefaultImpl {

    public TelefoneCurriculoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<TelefoneCurriculoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idTelefone", "idTelefone", true, true, false, false));
        listFields.add(new Field("idTipoTelefone", "idTipoTelefone", false, false, false, false));
        listFields.add(new Field("ddd", "ddd", false, false, false, false));
        listFields.add(new Field("numeroTelefone", "numeroTelefone", false, false, false, false));
        listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_TelefoneCurriculo";
    }

    @Override
    public Collection<TelefoneCurriculoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<TelefoneCurriculoDTO> getBean() {
        return TelefoneCurriculoDTO.class;
    }

    public Collection<TelefoneCurriculoDTO> findByIdCurriculo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idCurriculo", "=", parm));
        ordenacao.add(new Order("idTelefone"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdCurriculo(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idCurriculo", "=", parm));
        super.deleteByCondition(condicao);
    }

}
