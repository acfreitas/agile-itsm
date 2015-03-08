package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.EnderecoCurriculoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class EnderecoCurriculoDao extends CrudDaoDefaultImpl {

    public EnderecoCurriculoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();

        listFields.add(new Field("idEndereco", "idEndereco", true, true, false, true));
        listFields.add(new Field("logradouro", "logradouro", false, false, false, false));
        listFields.add(new Field("cep", "cep", false, false, false, false));
        listFields.add(new Field("complemento", "complemento", false, false, false, false));
        listFields.add(new Field("idTipoEndereco", "idTipoEndereco", false, false, false, false));
        listFields.add(new Field("principal", "principal", false, false, false, false));
        listFields.add(new Field("nomeCidade", "nomeCidade", false, false, false, false));
        listFields.add(new Field("nomeBairro", "nomeBairro", false, false, false, false));
        listFields.add(new Field("idUF", "enderecoIdUF", false, false, false, false));
        listFields.add(new Field("idCurriculo", "idCurriculo", false, false, false, false));
        listFields.add(new Field("idCidade", "idCidade", false, false, false, false));

        return listFields;
    }

    @Override
    public String getTableName() {
        return "RH_EnderecoCurriculo";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class getBean() {
        return EnderecoCurriculoDTO.class;
    }

    private List getColunasRestoreAll() {

        final List listRetorno = new ArrayList();

        listRetorno.add("idEndereco");
        listRetorno.add("logradouro");
        listRetorno.add("cep");
        listRetorno.add("complemento");
        listRetorno.add("idTipoEndereco");
        listRetorno.add("principal");
        listRetorno.add("idCidade");
        listRetorno.add("nomeCidade");
        listRetorno.add("nomeBairro");
        listRetorno.add("idUf");
        listRetorno.add("nomeUF");
        listRetorno.add("idCurriculo");
        listRetorno.add("nomePais");
        return listRetorno;
    }

    public Collection findByIdCurriculo(final Integer parm) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT ender.idEndereco,ender.logradouro,ender.cep,ender.complemento,ender.idTipoEndereco,");
        sql.append(" ender.principal,ender.idcidade,ender.nomeCidade,ender.nomeBairro,uf.idUf,uf.nomeUf,ender.idCurriculo, pais.nomePais");
        sql.append(" FROM rh_enderecoCurriculo ender ");
        sql.append(" INNER JOIN cidades cid ON cid.idcidade = ender.idcidade");
        sql.append(" INNER JOIN ufs uf ON uf.idUf = cid.iduf");
        sql.append(" INNER JOIN pais pais ON pais.idpais = uf.idpais");
        sql.append(" WHERE ender.idCurriculo = ? ");
        sql.append(" ORDER BY ender.idEndereco");

        final List parametro = new ArrayList();

        parametro.add(parm);

        final List lista = this.execSQL(sql.toString(), parametro.toArray());

        return engine.listConvertion(this.getBean(), lista, this.getColunasRestoreAll());
    }

    public void deleteByIdCurriculo(final Integer parm) throws PersistenceException {
        final List condicao = new ArrayList();
        condicao.add(new Condition("idCurriculo", "=", parm));
        super.deleteByCondition(condicao);
    }

}
