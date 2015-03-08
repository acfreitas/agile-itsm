package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CursoDao extends CrudDaoDefaultImpl {

    public CursoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idCurso", "idCurso", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("detalhe", "detalhe", false, false, false, false));

        return listFields;
    }

    public Collection findByNotIdFuncao(final Integer idFuncao) throws PersistenceException {
        List dados = new ArrayList();
        final List fields = new ArrayList();
        final List parametros = new ArrayList();

        final String sql = "select idcurso, descricao, detalhe from  rh_curso where idcurso not  in(select idcurso from rh_perspectivatecnicacurso where idsolicitacaoservico = ? and obrigatoriocurso = 'S') order by descricao ";

        parametros.add(idFuncao);

        dados = this.execSQL(sql, parametros.toArray());

        fields.add("idCurso");
        fields.add("descricao");
        fields.add("detalhe");

        return this.listConvertion(this.getBean(), dados, fields);
    }

    @Override
    public String getTableName() {
        return "RH_Curso";
    }

    @Override
    public Class getBean() {
        return CursoDTO.class;
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

        sql.append("select  c.idcurso, c.descricao, c.detalhe ");
        sql.append(" from rh_curso c ");
        sql.append(" where upper(c.descricao) like upper(?) ");
        sql.append(" order by c.detalhe limit 0,10");

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idCurso");
        listRetorno.add("descricao");
        listRetorno.add("detalhe");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

}
