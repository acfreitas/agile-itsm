package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class FormacaoCurriculoDao extends CrudDaoDefaultImpl {

    public FormacaoCurriculoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final List<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idFormacao", "idFormacao", true, true, false, false));
        listFields.add(new Field("idTipoFormacao", "idTipoFormacao", false, false, false, false));
        listFields.add(new Field("instituicao", "instituicao", false, false, false, false));
        listFields.add(new Field("idSituacao", "idSituacao", false, false, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_FormacaoCurriculo";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return FormacaoCurriculoDTO.class;
    }

    public Collection findByIdCurriculo(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idCurriculo", "=", parm));
        ordenacao.add(new Order("idFormacao"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdCurriculo(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idCurriculo", "=", parm));
        super.deleteByCondition(condicao);
    }

}
