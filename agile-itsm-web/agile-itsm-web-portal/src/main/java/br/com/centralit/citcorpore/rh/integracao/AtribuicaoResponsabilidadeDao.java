package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.AtribuicaoResponsabilidadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AtribuicaoResponsabilidadeDao extends CrudDaoDefaultImpl {

    public AtribuicaoResponsabilidadeDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idAtribuicaoResponsabilidade", "idAtribuicaoResponsabilidade", true, true, false, false));
        listFields.add(new Field("descricaoPerspectivaComplexidade", "descricaoPerspectivaComplexidade", false, false, false, false));
        listFields.add(new Field("idNivel", "idNivel", false, false, false, false));
        listFields.add(new Field("idManualFuncao", "idManualFuncao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_atribuicaoresponsabilidade";
    }

    @Override
    public Class getBean() {
        return AtribuicaoResponsabilidadeDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricaoPerspectivaComplexidade"));
        return super.list(list);
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

        sql.append("select  c.idAtribuicaoResponsabilidade, c.descricaoPerspectivaComplexidade, c.idNivel ");
        sql.append(" from rh_atribuicaoresponsabilidade c ");
        sql.append(" where upper(c.descricaoPerspectivaComplexidade) like upper(?) ");
        sql.append(" order by c.descricaoPerspectivaComplexidade limit 0,10");

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idAtribuicaoResponsabilidade");
        listRetorno.add("descricaoPerspectivaComplexidade");
        listRetorno.add("idNivel");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public Collection findByIdManualFuncao(final Integer idManualFuncao) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();

        sql.append("select  c.idAtribuicaoResponsabilidade, c.descricaoPerspectivaComplexidade, c.idNivel, c.idManualFuncao ");
        sql.append(" from rh_atribuicaoresponsabilidade c ");
        sql.append(" where c.idManualFuncao = ?");

        final Object[] objs = new Object[] {idManualFuncao};

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idAtribuicaoResponsabilidade");
        listRetorno.add("descricaoPerspectivaComplexidade");
        listRetorno.add("idNivel");
        listRetorno.add("idManualFuncao");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public void deleteByIdManualFuncao(final Integer idManualFuncao) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idManualFuncao", "=", idManualFuncao));
        super.deleteByCondition(condicao);
    }

}
