package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaFormacaoAcademicaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PerspectivaTecnicaFormacaoAcademicaDao extends CrudDaoDefaultImpl {

    public PerspectivaTecnicaFormacaoAcademicaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOME = " select idPerspectivaTecnicaFormacaoAcademica, descricaoFormacaoAcademica from rh_perspectivatecnicaformacaoacademica "
            + " where upper(descricaoFormacaoAcademica) like upper(?)";

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idPerspectivaTecnicaFormacaoAcademica", "idPerspectivaTecnicaFormacaoAcademica", true, true, false, false));
        listFields.add(new Field("idFormacaoAcademica", "idFormacaoAcademica", false, false, false, false));
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("descricaoFormacaoAcademica", "descricaoFormacaoAcademica", false, false, false, false));
        listFields.add(new Field("detalheFormacaoAcademica", "detalheFormacaoAcademica", false, false, false, false));
        listFields.add(new Field("obrigatorioFormacaoAcademica", "obrigatorioFormacaoAcademica", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_perspectivatecnicaformacaoacademica";
    }

    @Override
    public Class getBean() {
        return PerspectivaTecnicaFormacaoAcademicaDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricaoFormacaoAcademica"));
        return super.list(list);
    }

    public Collection findByidSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idPerspectivaTecnicaFormacaoAcademica"));
        return super.findByCondition(condicao, ordenacao);
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
        listRetorno.add("idPerspectivaTecnicaFormacaoAcademica");
        listRetorno.add("descricaoFormacaoAcademica");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public List<PerspectivaTecnicaFormacaoAcademicaDTO> findByIdFuncao(final Integer IdFuncao) throws PersistenceException {
        final List paramentros = new ArrayList();
        List dados = new ArrayList();
        final List fields = new ArrayList();

        final String sql = "SELECT idPerspectivaTecnicaFormacaoAcademica, form.idFormacaoAcademica, idSolicitacaoServico , descricao, detalhe, obrigatorioFormacaoAcademica "
                + " FROM rh_perspectivatecnicaformacaoacademica pers "
                + " inner join rh_formacaoacademica form on pers.idformacaoacademica = form.idformacaoacademica where idsolicitacaoservico = ? order by descricao ";

        paramentros.add(IdFuncao);

        dados = this.execSQL(sql, paramentros.toArray());

        fields.add("idPerspectivaTecnicaFormacaoAcademica");
        fields.add("idFormacaoAcademica");
        fields.add("idSolicitacaoServico");
        fields.add("descricaoFormacaoAcademica");
        fields.add("detalheFormacaoAcademica");
        fields.add("obrigatorioFormacaoAcademica");

        return this.listConvertion(this.getBean(), dados, fields);
    }

    public void deleteByIdSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

}
