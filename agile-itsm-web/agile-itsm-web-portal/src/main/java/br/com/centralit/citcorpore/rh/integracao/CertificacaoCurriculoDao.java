package br.com.centralit.citcorpore.rh.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CertificacaoCurriculoDao extends CrudDaoDefaultImpl {

    public CertificacaoCurriculoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    private static final String SQL_NOME = " select idCertificacao, descricao from rh_certificacaocurriculo where upper(descricao) like upper(?)";

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        final List lst = new ArrayList();
        lst.add("idCertificacao");
        return super.find(obj, lst);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idCertificacao", "idCertificacao", true, true, false, false));
        listFields.add(new Field("versao", "versao", false, false, false, false));
        listFields.add(new Field("validade", "validade", false, false, false, false));
        listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_CertificacaoCurriculo";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return CertificacaoCurriculoDTO.class;
    }

    public Collection findByIdCurriculo(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();
        condicao.add(new Condition("idCurriculo", "=", parm));
        ordenacao.add(new Order("idCertificacao"));
        final List<CertificacaoCurriculoDTO> result = (List<CertificacaoCurriculoDTO>) super.findByCondition(condicao, ordenacao);

        return result;
    }

    public void deleteByIdCurriculo(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idCurriculo", "=", parm));
        super.deleteByCondition(condicao);
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
        listRetorno.add("idCertificacao");
        listRetorno.add("descricao");

        final List result = engine.listConvertion(this.getBean(), list, listRetorno);

        return result;
    }

}
