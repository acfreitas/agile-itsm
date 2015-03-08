package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AssinaturaAprovacaoProjetoDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AssinaturaAprovacaoProjetoDao extends CrudDaoDefaultImpl {

    public AssinaturaAprovacaoProjetoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idAssinaturaAprovacaoProjeto", "idAssinaturaAprovacaoProjeto", true, true, false, false));
        listFields.add(new Field("idProjeto", "idProjeto", true, false, false, false));
        listFields.add(new Field("idEmpregado", "idEmpregadoAssinatura", true, false, false, false));
        listFields.add(new Field("papel", "papel", false, false, false, false));
        listFields.add(new Field("ordem", "ordem", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "AssinaturaAprovacaoProjeto";
    }

    @Override
    public Class<AssinaturaAprovacaoProjetoDTO> getBean() {
        return AssinaturaAprovacaoProjetoDTO.class;
    }

    public Collection<AssinaturaAprovacaoProjetoDTO> findByIdProjeto(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idProjeto", "=", parm));
        ordenacao.add(new Order("idEmpregadoAssinatura"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdProjeto(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idProjeto", "=", parm));
        super.deleteByCondition(condicao);
    }

    public Collection<AssinaturaAprovacaoProjetoDTO> findByIdEmpregado(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idEmpregadoAssinatura", "=", parm));
        ordenacao.add(new Order("idProjeto"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdEmpregado(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idEmpregadoAssinatura", "=", parm));
        super.deleteByCondition(condicao);
    }

}
