package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.AtitudeIndividualDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AtitudeIndividualDao extends CrudDaoDefaultImpl {

    public AtitudeIndividualDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOME = " select idAtitudeIndividual, descricao from RH_AtitudeIndividual where upper(descricao) like upper(?)";

    @Override
    public Collection<AtitudeIndividualDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idAtitudeIndividual", "idAtitudeIndividual", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("detalhe", "detalhe", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_AtitudeIndividual";
    }

    @Override
    public Class<AtitudeIndividualDTO> getBean() {
        return AtitudeIndividualDTO.class;
    }

    @Override
    public Collection<AtitudeIndividualDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("descricao"));
        return super.list(list);
    }

    public Collection<AtitudeIndividualDTO> findByNome(String nome) throws PersistenceException {
        if (nome == null) {
            nome = "";
        }
        nome = "%" + nome.toUpperCase() + "%";
        final Object[] objs = new Object[] {nome};
        final List list = this.execSQL(SQL_NOME, objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idAtitudeIndividual");
        listRetorno.add("descricao");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public Collection<AtitudeIndividualDTO> findByIdSolicitacaoServico(final Integer idSolicitacaoServico) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List listRetorno = new ArrayList();
        final List listParametro = new ArrayList();

        sql.append("select ai.descricao, rai.idAtitudeIndividual from rh_requisicaoatitudeindividual rai ");
        sql.append("inner join rh_atitudeindividual ai on rai.idatitudeindividual = ai.idatitudeindividual ");
        sql.append("where rai.idsolicitacaoservico = ? ");

        listParametro.add(idSolicitacaoServico);
        listRetorno.add("descricao");
        listRetorno.add("idAtitudeIndividual");

        final List list = this.execSQL(sql.toString(), listParametro.toArray());

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

}
