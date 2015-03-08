package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.ExperienciaInformaticaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ExperienciaInformaticaDao extends CrudDaoDefaultImpl {

    public ExperienciaInformaticaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idExperienciaInformatica", "idExperienciaInformatica", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("detalhe", "detalhe", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_ExperienciaInformatica";
    }

    @Override
    public Class getBean() {
        return ExperienciaInformaticaDTO.class;
    }

    public Collection findByNotIdFuncao(final Integer idFuncao) throws PersistenceException {
        final List fields = new ArrayList();
        final List parametros = new ArrayList();

        final String sql = "select idexperienciainformatica, descricao, detalhe from  rh_experienciainformatica where idexperienciainformatica not"
                + " in(select idexperienciainformatica from rh_requisicaoexperienciainformatica where idsolicitacaoservico = ? and obrigatorio = 'S') order by descricao ";

        parametros.add(idFuncao);

        final List dados = this.execSQL(sql, parametros.toArray());

        fields.add("idExperienciaInformatica");
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

}
