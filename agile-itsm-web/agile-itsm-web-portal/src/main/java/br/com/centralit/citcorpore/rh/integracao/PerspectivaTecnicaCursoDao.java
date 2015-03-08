package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaCursoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PerspectivaTecnicaCursoDao extends CrudDaoDefaultImpl {

    public PerspectivaTecnicaCursoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOME = " select idperspectivatecnicacurso, descricaocurso from rh_perspectivatecnicacurso  where upper(descricaocurso) like upper(?)";

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idPerspectivaTecnicaCurso", "idPerspectivaTecnicaCurso", true, true, false, false));
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("descricaoCurso", "descricaoCurso", false, false, false, false));
        listFields.add(new Field("detalheCurso", "detalheCurso", false, false, false, false));
        listFields.add(new Field("obrigatorioCurso", "obrigatorioCurso", false, false, false, false));
        listFields.add(new Field("idCurso", "idCurso", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_perspectivatecnicacurso";
    }

    @Override
    public Class getBean() {
        return PerspectivaTecnicaCursoDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricaoCurso"));
        return super.list(list);
    }

    public Collection findByidSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idPerspectivaTecnicaCurso"));
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
        listRetorno.add("idPerspectivaTecnicaCurso");
        listRetorno.add("descricaoCurso");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    @SuppressWarnings("unchecked")
    public List<PerspectivaTecnicaCursoDTO> findByIdFuncao(final Integer IdFuncao) throws PersistenceException {
        final List paramentros = new ArrayList();
        List dados = new ArrayList();
        final List fields = new ArrayList();

        final String sql = "SELECT idperspectivatecnicacurso,  idsolicitacaoServico, descricao, detalhe, obrigatoriocurso, a.idcurso "
                + " FROM rh_perspectivatecnicacurso a inner join rh_curso b on a.idcurso = b.idcurso where idsolicitacaoservico = ?  order by descricao ";

        paramentros.add(IdFuncao);

        dados = this.execSQL(sql, paramentros.toArray());

        fields.add("idPerspectivaTecnicaCurso");
        fields.add("idSolicitacaoServico");
        fields.add("descricaoCurso");
        fields.add("detalheCurso");
        fields.add("obrigatorioCurso");
        fields.add("idCurso");

        super.getListNamesFieldClass();

        return this.listConvertion(this.getBean(), dados, fields);
    }

    public void deleteByIdSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

}
