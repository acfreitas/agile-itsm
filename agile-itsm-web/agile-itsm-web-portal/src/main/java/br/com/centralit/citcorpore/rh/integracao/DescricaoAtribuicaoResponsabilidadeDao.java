package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.DescricaoAtribuicaoResponsabilidadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class DescricaoAtribuicaoResponsabilidadeDao extends CrudDaoDefaultImpl {

    private static final String TABLE_NAME = "rh_descricao_atruibuicaoresponsabilidade";

    public DescricaoAtribuicaoResponsabilidadeDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idDescricao", "idDescricao", true, false, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricao"));
        return super.list(list);
    }

    public Collection listAtivos() throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();

        condicao.add(new Condition("situacao", "=", "A"));
        ordenacao.add(new Order("descricao"));
        return super.findByCondition(condicao, ordenacao);
    }

    /**
     * Realiza consulta por nome atraves do AutoComplete
     *
     * @param nome
     * @return
     * @throws Exception
     */
    public Collection findByNome(String nome) throws PersistenceException {
        if (nome == null) {
            nome = "";
        }

        String texto = Normalizer.normalize(nome, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{ASCII}]", "");
        texto = texto.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC ");
        texto = "%" + texto.toUpperCase() + "%";

        final Object[] objs = new Object[] {texto};

        final StringBuilder sql = new StringBuilder();

        sql.append("select d.iddescricao, d.descricao, d.situacao ");
        sql.append(" from rh_descricao_atruibuicaoresponsabilidade d ");
        sql.append(" where upper(d.descricao) like upper(?) ");
        sql.append(" and d.situacao = 'A' ");
        sql.append(" order by d.descricao limit 0,10");

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idDescricao");
        listRetorno.add("descricao");
        listRetorno.add("situaca");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    @Override
    public Class getBean() {
        return DescricaoAtribuicaoResponsabilidadeDTO.class;
    }

}
