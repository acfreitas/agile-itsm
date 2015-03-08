package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaCertificacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PerspectivaTecnicaCertificacaoDao extends CrudDaoDefaultImpl {

    public PerspectivaTecnicaCertificacaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOME = " select idperspectivatecnicacertificacao, descricaocertificacao from rh_perspectivatecnicacertificacao  where upper(descricaocertificacao) like upper(?)";

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idPerspectivaTecnicaCertificacao", "idPerspectivaTecnicaCertificacao", true, true, false, false));
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("descricaoCertificacao", "descricaoCertificacao", false, false, false, false));
        listFields.add(new Field("versaoCertificacao", "versaoCertificacao", false, false, false, false));
        listFields.add(new Field("obrigatorioCertificacao", "obrigatorioCertificacao", false, false, false, false));
        listFields.add(new Field("idCertificacao", "idCertificacao", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_perspectivatecnicacertificacao";
    }

    @Override
    public Class getBean() {
        return PerspectivaTecnicaCertificacaoDTO.class;
    }

    @Override
    public Collection list() throws PersistenceException {
        final List list = new ArrayList();
        list.add(new Order("descricaoCertificacao"));
        return super.list(list);
    }

    public Collection findByidSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idPerspectivaTecnicaCertificacao"));
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
        listRetorno.add("idPerspectivaTecnicaCertificacao");
        listRetorno.add("descricaoCertificacao");

        final List result = engine.listConvertion(this.getBean(), list, listRetorno);

        return result;
    }

    @SuppressWarnings("unchecked")
    public List<PerspectivaTecnicaCertificacaoDTO> findByIdFuncao(final Integer IdFuncao) throws PersistenceException {
        final List paramentros = new ArrayList();
        List dados = new ArrayList();
        final List fields = new ArrayList();

        final String sql = "SELECT idperspectivatecnicacertificacao, cert.idcertificacao, idsolicitacaoServico, descricao, detalhe, obrigatoriocertificacao, versaocertificacao  "
                + " FROM rh_perspectivatecnicacertificacao pers inner join rh_certificacao cert on pers.idcertificacao = cert.idcertificacao "
                + "where idsolicitacaoservico = ? order by descricao ";

        paramentros.add(IdFuncao);

        dados = this.execSQL(sql, paramentros.toArray());

        fields.add("idPerspectivaTecnicaCertificacao");
        fields.add("idCertificacao");
        fields.add("idSolicitacaoServico");
        fields.add("descricaoCertificacao");
        fields.add("detalhe");
        fields.add("obrigatorioCertificacao");
        fields.add("versaoCertificacao");

        return this.listConvertion(this.getBean(), dados, fields);
    }

    public void deleteByIdSolicitacao(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

}
