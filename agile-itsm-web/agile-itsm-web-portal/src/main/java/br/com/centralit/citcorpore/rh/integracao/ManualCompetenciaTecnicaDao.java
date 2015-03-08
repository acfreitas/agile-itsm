package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.ManualCompetenciaTecnicaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class ManualCompetenciaTecnicaDao extends CrudDaoDefaultImpl {

    public ManualCompetenciaTecnicaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idManualCompetenciaTecnica", "idManualCompetenciaTecnica", true, true, false, false));
        listFields.add(new Field("idNivelCompetenciaAcesso", "idNivelCompetenciaAcesso", false, false, false, false));
        listFields.add(new Field("idCompetenciaTecnica", "idCompetenciaTecnica", false, false, false, false));
        listFields.add(new Field("idNivelCompetenciaFuncao", "idNivelCompetenciaFuncao", false, false, false, false));
        listFields.add(new Field("idManualFuncao", "idManualFuncao", false, false, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_manualcompetenciatecnica";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return ManualCompetenciaTecnicaDTO.class;
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

        sql.append("select  c.idmanualcompetenciatecnica, c.descricao, c.situacao, c.idManualFuncao, c.idNivelCompetenciaAcesso, c.idNivelCompetenciaFuncao, c.idCompetenciaTecnica ");
        sql.append(" from rh_manualcompetenciatecnica c ");
        sql.append(" where upper(c.descricao) like upper(?) ");
        sql.append(" order by c.descricao limit 0,10");

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idmanualcompetenciatecnica");
        listRetorno.add("descricao");
        listRetorno.add("situcao");
        listRetorno.add("idManualFuncao");
        listRetorno.add("idNivelCompetenciaAcesso");
        listRetorno.add("idNivelCompetenciaFuncao");
        listRetorno.add("idCompetenciaTecnica");

        final List result = engine.listConvertion(this.getBean(), list, listRetorno);

        return result;

    }

    public Collection findByIdManualFuncao(final Integer idManualFuncao) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();

        sql.append("select  c.idManualCompetenciaTecnica, c.descricao, c.situacao, c.idManualFuncao, c.idNivelCompetenciaAcesso, c.idNivelCompetenciaFuncao, c.idCompetenciaTecnica,  com.descricao as nomeCompetenciaTecnica ");
        sql.append(" from rh_manualcompetenciatecnica c ");
        sql.append(" inner join rh_competenciatecnica_ com on com.idcompetencia =  c.idCompetenciaTecnica");
        sql.append(" where c.idManualFuncao = ?");

        final Object[] objs = new Object[] {idManualFuncao};

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idManualCompetenciaTecnica");
        listRetorno.add("descricao");
        listRetorno.add("situacao");
        listRetorno.add("idManualFuncao");
        listRetorno.add("idNivelCompetenciaAcesso");
        listRetorno.add("idNivelCompetenciaFuncao");
        listRetorno.add("idCompetenciaTecnica");
        listRetorno.add("nomeCompetenciaTecnica");

        final List result = engine.listConvertion(this.getBean(), list, listRetorno);

        return result;

    }

    public void deleteByIdManualFuncao(final Integer idManualFuncao) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idManualFuncao", "=", idManualFuncao));
        super.deleteByCondition(condicao);
    }

}
