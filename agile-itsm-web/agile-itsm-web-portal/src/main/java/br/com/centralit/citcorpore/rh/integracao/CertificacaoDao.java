package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CertificacaoDao extends CrudDaoDefaultImpl {

    public CertificacaoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idCertificacao", "idCertificacao", true, true, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("detalhe", "detalhe", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_Certificacao";
    }

    @Override
    public Class getBean() {
        return CertificacaoDTO.class;
    }

    public Collection findByNotIdFuncao(final Integer idFuncao) throws PersistenceException {
        List dados = new ArrayList();
        final List fields = new ArrayList();
        final List parametros = new ArrayList();

        final String sql = "select idcertificacao, descricao, detalhe from  rh_certificacao where idcertificacao not "
                + " in(select idcertificacao from rh_perspectivatecnicacertificacao where idsolicitacaoservico = ? and obrigatoriocertificacao = 'S') order by descricao ";

        parametros.add(idFuncao);

        dados = this.execSQL(sql, parametros.toArray());

        fields.add("idCertificacao");
        fields.add("descricao");
        fields.add("detalhe");

        return this.listConvertion(this.getBean(), dados, fields);
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

        nome = "%" + nome.toUpperCase() + "%";

        final Object[] objs = new Object[] {nome};

        final StringBuilder sql = new StringBuilder();

        sql.append("select  c.idcertificacao, c.descricao, c.detalhe ");
        sql.append(" from rh_certificacao c ");
        sql.append(" where upper(c.descricao) like upper(?) ");
        sql.append(" order by c.detalhe ");

        final List list = this.execSQL(sql.toString(), objs);

        final List listRetorno = new ArrayList();
        listRetorno.add("idCertificacao");
        listRetorno.add("descricao");
        listRetorno.add("detalhe");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public CertificacaoDTO findById(final Integer idCertificacao) throws PersistenceException {
        final List condicao = new ArrayList();
        final List ordenacao = new ArrayList();

        condicao.add(new Condition("idCertificacao", "=", idCertificacao));
        ordenacao.add(new Order("idCertificacao"));

        final ArrayList<CertificacaoDTO> listaCertificacao = (ArrayList) super.findByCondition(condicao, ordenacao);

        return listaCertificacao != null && !listaCertificacao.isEmpty() ? listaCertificacao.get(0) : null;
    }

}
