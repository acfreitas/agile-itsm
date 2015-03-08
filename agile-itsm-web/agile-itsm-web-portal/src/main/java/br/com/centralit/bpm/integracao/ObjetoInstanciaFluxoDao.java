package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ObjetoInstanciaFluxoDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "bpm_objetoinstanciafluxo";

    public ObjetoInstanciaFluxoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idObjetoInstancia", "idObjetoInstancia", true, true, false, false));
        listFields.add(new Field("idInstancia", "idInstancia", false, false, false, false));
        listFields.add(new Field("idItemTrabalho", "idItemTrabalho", false, false, false, false));
        listFields.add(new Field("idObjetoNegocio", "idObjetoNegocio", false, false, false, false));
        listFields.add(new Field("nomeObjeto", "nomeObjeto", false, false, false, false));
        listFields.add(new Field("nomeClasse", "nomeClasse", false, false, false, false));
        listFields.add(new Field("tipoAssociacao", "tipoAssociacao", false, false, false, false));
        listFields.add(new Field("campoChave", "campoChave", false, false, false, false));
        listFields.add(new Field("objetoPrincipal", "objetoPrincipal", false, false, false, false));
        listFields.add(new Field("nomeTabelaBD", "nomeTabelaBD", false, false, false, false));
        listFields.add(new Field("nomeCampoBD", "nomeCampoBD", false, false, false, false));
        listFields.add(new Field("tipoCampoBD", "tipoCampoBD", false, false, false, false));
        listFields.add(new Field("valor", "valor", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection<ObjetoInstanciaFluxoDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<ObjetoInstanciaFluxoDTO> getBean() {
        return ObjetoInstanciaFluxoDTO.class;
    }

    @Override
    public Collection<ObjetoInstanciaFluxoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public Collection findByIdInstancia(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idInstancia", "=", parm));
        condicao.add(new Condition("tipoAssociacao", "=", "I"));
        ordenacao.add(new Order("nomeObjeto"));
        return super.findByCondition(condicao, ordenacao);
    }

    public ObjetoInstanciaFluxoDTO findByIdInstanciaAndNomeObjeto(final Integer idInstancia, final String nomeObjeto) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idInstancia", "=", idInstancia));
        condicao.add(new Condition("tipoAssociacao", "=", "I"));
        condicao.add(new Condition("nomeObjeto", "=", nomeObjeto));
        ordenacao.add(new Order("nomeObjeto"));
        final Collection col = super.findByCondition(condicao, ordenacao);
        if (col != null && !col.isEmpty()) {
            return (ObjetoInstanciaFluxoDTO) ((List) col).get(0);
        }
        return null;
    }

    public Collection findByIdInstanciaAndNome(final Integer idInstancia, final String nomeCampo) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idInstancia", "=", idInstancia));
        condicao.add(new Condition("tipoAssociacao", "=", "I"));
        condicao.add(new Condition("nomeObjeto", "=", nomeCampo));
        ordenacao.add(new Order("nomeCampo"));
        return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdInstancia(final Integer idInstancia) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idInstancia", "=", idInstancia));
        condicao.add(new Condition("tipoAssociacao", "=", "I"));
        super.deleteByCondition(condicao);
    }

    public void deleteByIdInstanciaAndNome(final Integer idInstanciaFluxo, final String nomeCampo) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idInstancia", "=", idInstanciaFluxo));
        condicao.add(new Condition("tipoAssociacao", "=", "I"));
        condicao.add(new Condition("nomeObjeto", "=", nomeCampo));
        super.deleteByCondition(condicao);
    }

    public Collection findByIdTarefa(final Integer idTarefa) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idItemTrabalho", "=", idTarefa));
        condicao.add(new Condition("tipoAssociacao", "=", "T"));
        ordenacao.add(new Order("nomeObjeto"));
        return super.findByCondition(condicao, ordenacao);
    }

}
