package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.RequisicaoCursoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RequisicaoCursoDao extends CrudDaoDefaultImpl {

    public RequisicaoCursoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", true, false, false, false));
        listFields.add(new Field("idCurso", "idCurso", true, false, false, false));
        listFields.add(new Field("obrigatorio", "obrigatorio", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "RH_RequisicaoCurso";
    }

    @Override
    public Collection<RequisicaoCursoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<RequisicaoCursoDTO> getBean() {
        return RequisicaoCursoDTO.class;
    }

    @Override
    public Collection<RequisicaoCursoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection<RequisicaoCursoDTO> findByIdSolicitacaoServico(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idCurso"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdSolicitacaoServico(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

}
