package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class IdiomaDao extends CrudDaoDefaultImpl {

    public IdiomaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOME = " select idIdioma, descricao from rh_idioma  where upper(descricao) like upper(?)";

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idIdioma", "idIdioma", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("detalhe", "detalhe", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_Idioma";
    }

    @Override
    public Class getBean() {
        return IdiomaDTO.class;
    }

    public Collection findByNotIdFuncao(final Integer idFuncao) throws PersistenceException {
        List dados = new ArrayList();
        final List fields = new ArrayList();
        final List parametros = new ArrayList();

        final String sql = "select ididioma, descricao, detalhe from  rh_idioma where ididioma not in(select ididioma from rh_perspectivatecnicaidioma where idsolicitacaoservico = ? and obrigatorioidioma = 'S')";

        parametros.add(idFuncao);

        dados = this.execSQL(sql, parametros.toArray());

        fields.add("idIdioma");
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
        String text = nome.trim().replaceAll(" ", "");
        text = Normalizer.normalize(text, Normalizer.Form.NFD);
        text = text.replaceAll("[^\\p{ASCII}]", "");
        text = text.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC ");
        nome = text;
        nome = "%" + nome.toUpperCase() + "%";
        final Object[] objs = new Object[] {nome};
        final List list = this.execSQL(SQL_NOME, objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idIdioma");
        listRetorno.add("descricao");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

}
