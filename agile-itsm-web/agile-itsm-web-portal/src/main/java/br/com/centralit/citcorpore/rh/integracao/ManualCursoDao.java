package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.ManualCursoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ManualCursoDao extends CrudDaoDefaultImpl {

    public ManualCursoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idManualCurso", "idManualCurso", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("detalhe", "detalhe", false, false, false, false));
        listFields.add(new Field("idManualFuncao", "idManualFuncao", false, false, false, false));
        listFields.add(new Field("RAouRF", "RAouRF", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_manualcurso";
    }

    @Override
    public Class getBean() {
        return ManualCursoDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricao"));
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

        sql.append("select  c.idmanualcurso, c.descricao, c.detalhe, c.idmanualfuncao, c.RAouRF ");
        sql.append(" from rh_manualcurso c ");
        sql.append(" where upper(c.detalhe) like upper(?) ");
        sql.append(" order by c.detalhe limit 0,10");

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idManualCurso");
        listRetorno.add("descricao");
        listRetorno.add("detalhe");
        listRetorno.add("idManualFuncao");
        listRetorno.add("RAouRF");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public Collection findByIdManualFuncao(final Integer idManualFuncao) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();

        sql.append("select  c.idManualCurso, c.descricao, c.detalhe, c.idManualFuncao, c.RAouRF ");
        sql.append(" from rh_manualcurso c ");
        sql.append(" where c.idManualFuncao = ?");

        final Object[] objs = new Object[] {idManualFuncao};

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idManualCurso");
        listRetorno.add("descricao");
        listRetorno.add("detalhe");
        listRetorno.add("idManualFuncao");
        listRetorno.add("RAouRF");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public void deleteByIdManualFuncaoRA(final Integer idManualFuncao) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idManualFuncao", "=", idManualFuncao));
        condicao.add(new Condition("RAouRF", "=", "RA"));
        super.deleteByCondition(condicao);
    }

    public void deleteByIdManualFuncaoRF(final Integer idManualFuncao) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idManualFuncao", "=", idManualFuncao));
        condicao.add(new Condition("RAouRF", "=", "RF"));
        super.deleteByCondition(condicao);
    }

}
