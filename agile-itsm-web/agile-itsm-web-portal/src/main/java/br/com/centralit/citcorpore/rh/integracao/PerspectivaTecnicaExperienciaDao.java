package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaExperienciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PerspectivaTecnicaExperienciaDao extends CrudDaoDefaultImpl {

    public PerspectivaTecnicaExperienciaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOME = " select idperspectivatecnicaexperiencia, descricaoexperiencia from rh_perspectivatecnicaexperiencia where upper(descricaoexperiencia) like upper(?)";

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idPerspectivaTecnicaExperiencia", "idPerspectivaTecnicaExperiencia", true, true, false, false));
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("descricaoExperiencia", "descricaoExperiencia", false, false, false, false));
        listFields.add(new Field("detalheExperiencia", "detalheExperiencia", false, false, false, false));
        listFields.add(new Field("obrigatorioExperiencia", "obrigatorioExperiencia", false, false, false, false));
        listFields.add(new Field("idConhecimento", "idConhecimento", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_perspectivatecnicaexperiencia";
    }

    @Override
    public Class getBean() {
        return PerspectivaTecnicaExperienciaDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricaoExperiencia"));
        return super.list(list);
    }

    public Collection findByidSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idPerspectivaTecnicaExperiencia"));
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
        listRetorno.add("idPerspectivaTecnicaExperiencia");
        listRetorno.add("descricaoExperiencia");

        final List result = engine.listConvertion(this.getBean(), list, listRetorno);

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<PerspectivaTecnicaExperienciaDTO> findByIdFuncao(final Integer IdFuncao) throws PersistenceException {
        final List paramentros = new ArrayList();
        List dados = new ArrayList();
        final List fields = new ArrayList();

        final String sql = "SELECT a.idperspectivatecnicaexperiencia, idsolicitacaoServico, descricao, detalhe, obrigatorioexperiencia, b.idconhecimento "
                + "FROM rh_perspectivatecnicaexperiencia a inner join rh_conhecimento b on a.idconhecimento = b.idconhecimento "
                + "where idsolicitacaoservico =  ? order by descricao ";

        paramentros.add(IdFuncao);

        dados = this.execSQL(sql, paramentros.toArray());

        fields.add("idPerspectivaTecnicaExperiencia");
        fields.add("idSolicitacaoServico");
        fields.add("descricaoExperiencia");
        fields.add("detalheExperiencia");
        fields.add("obrigatorioExperiencia");
        fields.add("idConhecimento");

        return this.listConvertion(this.getBean(), dados, fields);
    }

    public void deleteByIdSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

}
