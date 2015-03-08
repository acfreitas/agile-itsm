package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.ConhecimentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ConhecimentoDao extends CrudDaoDefaultImpl {

    public ConhecimentoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOME = " select idConhecimento, descricao from RH_Conhecimento " + " where upper(descricao) like upper(?)";

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idConhecimento", "idConhecimento", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("detalhe", "detalhe", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_Conhecimento";
    }

    @Override
    public Class getBean() {
        return ConhecimentoDTO.class;
    }

    public Collection findByNotIdFuncao(final Integer idFuncao) throws PersistenceException {
        List dados = new ArrayList();
        final List fields = new ArrayList();
        final List parametros = new ArrayList();

        final String sql = "select idconhecimento, descricao, detalhe from  rh_conhecimento where idconhecimento not  "
                + "in(select idperspectivatecnicaexperiencia from rh_perspectivatecnicaexperiencia where idsolicitacaoservico = ? and obrigatorioexperiencia = 'S') order by descricao ";

        parametros.add(idFuncao);

        dados = this.execSQL(sql, parametros.toArray());

        fields.add("idConhecimento");
        fields.add("descricao");
        fields.add("detalhe");

        return this.listConvertion(this.getBean(), dados, fields);
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricao"));
        return super.list(list);
    }

    public Collection findByNome(String nome) throws PersistenceException {
        if (nome == null) {
            nome = "";
        }
        nome = "%" + nome.toUpperCase() + "%";
        final Object[] objs = new Object[] {nome};
        final List list = this.execSQL(SQL_NOME, objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idConhecimento");
        listRetorno.add("descricao");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

}
