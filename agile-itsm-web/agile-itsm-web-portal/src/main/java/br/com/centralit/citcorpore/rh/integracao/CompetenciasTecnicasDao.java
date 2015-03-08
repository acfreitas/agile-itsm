package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CompetenciasTecnicasDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CompetenciasTecnicasDao extends CrudDaoDefaultImpl {

    public CompetenciasTecnicasDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOME = " select idcompetenciastecnicas, descricaocompetenciastecnicas from rh_competenciatecnica "
            + " where upper(descricaocompetenciastecnicas) like upper(?)";

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idCompetenciasTecnicas", "idCompetenciasTecnicas", true, true, false, false));
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("nivelCompetenciasTecnicas", "nivelCompetenciasTecnicas", false, false, false, false));
        listFields.add(new Field("descricaoCompetenciasTecnicas", "descricaoCompetenciasTecnicas", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_competenciatecnica";
    }

    @Override
    public Class getBean() {
        return CompetenciasTecnicasDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricaoCompetenciasTecnicas"));
        return super.list(list);
    }

    public Collection findByidSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idCompetenciasTecnicas"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection findByNome(String nome) throws Exception {
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
        listRetorno.add("idCompetenciasTecnicas");
        listRetorno.add("descricaoCompetenciasTecnicas");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public void deleteByIdSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

}
