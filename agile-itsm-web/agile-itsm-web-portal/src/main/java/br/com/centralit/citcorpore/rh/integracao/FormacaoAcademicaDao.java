package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.FormacaoAcademicaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class FormacaoAcademicaDao extends CrudDaoDefaultImpl {

    private static final String SQL_NOME = " select idFormacaoAcademica, descricao from RH_FormacaoAcademica  where upper(descricao) like upper(?)";

    public FormacaoAcademicaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idFormacaoAcademica", "idFormacaoAcademica", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("detalhe", "detalhe", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_FormacaoAcademica";
    }

    @Override
    public Class getBean() {
        return FormacaoAcademicaDTO.class;
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
        listRetorno.add("idFormacaoAcademica");
        listRetorno.add("descricao");

        final List result = engine.listConvertion(this.getBean(), list, listRetorno);

        return result;
    }

    public Collection findByNotIdFuncao(final Integer idFuncao) throws PersistenceException {
        List dados = new ArrayList();
        final List fields = new ArrayList();
        final List parametros = new ArrayList();

        final String sql = "select idformacaoacademica, descricao, detalhe "
                + "from  rh_formacaoacademica where idformacaoacademica not in(select idformacaoacademica from rh_perspectivatecnicaformacaoacademica where idsolicitacaoservico = ? and obrigatorioFormacaoAcademica = 'S' order by descricao  )";

        parametros.add(idFuncao);

        dados = this.execSQL(sql, parametros.toArray());

        fields.add("idFormacaoAcademica");
        fields.add("descricao");
        fields.add("detalhe");

        return this.listConvertion(this.getBean(), dados, fields);
    }

    public FormacaoAcademicaDTO findById(final Integer idFormacaoAcademica) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();

        condicao.add(new Condition("idFormacaoAcademica", "=", idFormacaoAcademica));
        ordenacao.add(new Order("idFormacaoAcademica"));

        final ArrayList<FormacaoAcademicaDTO> listaFormacao = (ArrayList) super.findByCondition(condicao, ordenacao);

        return listaFormacao != null && !listaFormacao.isEmpty() ? listaFormacao.get(0) : null;
    }

}
