package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.PerspectivaComportamentalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PerspectivaComportamentalDao extends CrudDaoDefaultImpl {

    public PerspectivaComportamentalDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idPerspectivaComportamental", "idPerspectivaComportamental", true, true, false, false));
        listFields.add(new Field("cmbCompetenciaComportamental", "cmbCompetenciaComportamental", false, false, false, false));
        listFields.add(new Field("comportamento", "comportamento", false, false, false, false));
        listFields.add(new Field("idManualFuncao", "idManualFuncao", false, false, false, false));
        listFields.add(new Field("descricaoPerspectivaComportamental", "descricaoPerspectivaComportamental", false, false, false, false));
        listFields.add(new Field("detalhePerspectivaComportamental", "detalhePerspectivaComportamental", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_perspectivacomportamental";
    }

    @Override
    public Class getBean() {
        return PerspectivaComportamentalDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("cmbCompetenciaComportamental"));
        return super.list(list);
    }

    public Collection findByidSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idPerspectivaComportamental"));
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

        sql.append("select  c.idPerspectivaComportamental, c.cmbCompetenciaComportamental, c.comportamento, c.idManualFuncao ");
        sql.append(" from rh_perspectivacomportamental c ");
        sql.append(" where upper(c.cmbCompetenciaComportamental) like upper(?) ");
        sql.append(" order by c.cmbCompetenciaComportamental limit 0,10");

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idPerspectivaTecnica");
        listRetorno.add("cmbCompetenciaComportamental");
        listRetorno.add("comportamento");
        listRetorno.add("idManualFuncao");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public Collection findByIdManualFuncao(final Integer idManualFuncao) throws PersistenceException {

        final StringBuilder sql = new StringBuilder();

        sql.append("select  c.idPerspectivaComportamental, c.cmbCompetenciaComportamental, c.comportamento, c.idManualFuncao ");
        sql.append(" from rh_perspectivacomportamental c ");
        sql.append(" where c.idManualFuncao = ?");

        final Object[] objs = new Object[] {idManualFuncao};

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idPerspectivaComportamental");
        listRetorno.add("cmbCompetenciaComportamental");
        listRetorno.add("comportamento");
        listRetorno.add("idManualFuncao");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public void deleteByIdManualFuncao(final Integer idManualFuncao) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idManualFuncao", "=", idManualFuncao));
        super.deleteByCondition(condicao);
    }

}
