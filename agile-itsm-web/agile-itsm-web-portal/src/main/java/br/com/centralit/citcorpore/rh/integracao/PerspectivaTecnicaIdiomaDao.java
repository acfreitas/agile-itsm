package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.PerspectivaTecnicaIdiomaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PerspectivaTecnicaIdiomaDao extends CrudDaoDefaultImpl {

    public PerspectivaTecnicaIdiomaDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOME = " select idperspectivatecnicaidioma, descricaoidioma from rh_perspectivatecnicaidioma " + " where upper(descricaoidioma) like upper(?)";

    @Override
    public Collection<PerspectivaTecnicaIdiomaDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idPerspectivaTecnicaIdioma", "idPerspectivaTecnicaIdioma", true, true, false, false));
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("descricaoIdioma", "descricaoIdioma", false, false, false, false));
        listFields.add(new Field("detalheIdioma", "detalheIdioma", false, false, false, false));
        listFields.add(new Field("obrigatorioIdioma", "obrigatorioIdioma", false, false, false, false));
        listFields.add(new Field("idIdioma", "idIdioma", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "rh_perspectivatecnicaidioma";
    }

    @Override
    public Class<PerspectivaTecnicaIdiomaDTO> getBean() {
        return PerspectivaTecnicaIdiomaDTO.class;
    }

    public List<PerspectivaTecnicaIdiomaDTO> findByIdFuncao(final Integer IdFuncao) throws PersistenceException {
        final List<Integer> paramentros = new ArrayList<>();
        final List<String> fields = new ArrayList<>();

        final String sql = "SELECT idperspectivatecnicaidioma,  idsolicitacaoServico, descricao, detalhe, obrigatorioidioma, a.ididioma "
                + " FROM rh_perspectivatecnicaidioma a inner join rh_idioma b on a.ididioma = b.ididioma where idsolicitacaoservico = ? order by descricao  ";

        paramentros.add(IdFuncao);

        final List dados = this.execSQL(sql, paramentros.toArray());

        fields.add("idPerspectivaTecnicaIdioma");
        fields.add("idSolicitacaoServico");
        fields.add("descricaoIdioma");
        fields.add("detalheIdioma");
        fields.add("obrigatorioIdioma");
        fields.add("idIdioma");

        return this.listConvertion(this.getBean(), dados, fields);
    }

    @Override
    public Collection<PerspectivaTecnicaIdiomaDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("descricaoIdioma"));
        return super.list(list);
    }

    public Collection<PerspectivaTecnicaIdiomaDTO> findByidSolicitacao(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        final List<Order> ordenacao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idPerspectivaTecnicaIdioma"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection<PerspectivaTecnicaIdiomaDTO> findByNome(String nome) throws PersistenceException {
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

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idPerspectivaTecnicaIdioma");
        listRetorno.add("descricaoIdioma");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public void deleteByIdSolicitacao(final Integer parm) throws PersistenceException {
        final List<Condition> condicao = new ArrayList<>();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        super.deleteByCondition(condicao);
    }

}
