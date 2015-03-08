package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class EnderecoDao extends CrudDaoDefaultImpl {

    public EnderecoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idEndereco", "idEndereco", true, true, false, false));
        listFields.add(new Field("logradouro", "logradouro", false, false, false, false));
        listFields.add(new Field("numero", "numero", false, false, false, false));
        listFields.add(new Field("complemento", "complemento", false, false, false, false));
        listFields.add(new Field("bairro", "bairro", false, false, false, false));
        listFields.add(new Field("idCidade", "idCidade", false, false, false, false));
        listFields.add(new Field("idPais", "idPais", false, false, false, false));
        listFields.add(new Field("cep", "cep", false, false, false, false));
        listFields.add(new Field("idUf", "idUf", false, false, false, false));
        listFields.add(new Field("latitude", "latitude", false, false, false, false));
        listFields.add(new Field("longitude", "longitude", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return this.getOwner() + "Endereco";
    }

    @Override
    public Collection list() throws PersistenceException {
        return super.list("logradouro");
    }

    @Override
    public Class<EnderecoDTO> getBean() {
        return EnderecoDTO.class;
    }

    @Override
    public Collection<EnderecoDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    public EnderecoDTO recuperaEnderecoCompleto(final EnderecoDTO endereco) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final StringBuilder sql = this.getSQLRestoreAll();
        sql.append(" WHERE e.idEndereco = ? ");
        parametro.add(endereco.getIdEndereco());

        List lista = new ArrayList<>();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return (EnderecoDTO) engine.listConvertion(this.getBean(), lista, this.getColunasRestoreAll()).get(0);
    }

    public EnderecoDTO recuperaEnderecoComUnidade(final Integer idEndereco) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();
        final StringBuilder sql = this.getSQLRestoreEnderecoUnidade();
        sql.append(" WHERE e.idEndereco = ? ");
        parametro.add(idEndereco);

        List lista = new ArrayList<>();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        final List<String> colunas = this.getColunasRestoreAll();
        colunas.add("identificacao");

        final List<EnderecoDTO> list = engine.listConvertion(this.getBean(), lista, colunas);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    private StringBuilder getSQLRestoreAll() {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT e.idEndereco, e.logradouro, e.numero, e.complemento, e.bairro, e.idCidade, e.idPais, cep, e.latitude, e.longitude, ");
        sql.append("       c.nomeCidade, uf.siglaUf");
        sql.append("  FROM endereco e ");
        sql.append("       LEFT JOIN cidades c ON c.idCidade = e.idCidade ");
        sql.append("       LEFT JOIN ufs uf on uf.idUf = c.idUf ");
        return sql;
    }

    private StringBuilder getSQLRestoreEnderecoUnidade() {
        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT e.idEndereco, e.logradouro, e.numero, e.complemento, e.bairro, e.idCidade, e.idPais, cep, e.latitude, e.longitude, ");
        sql.append("       c.nomeCidade, uf.siglaUf, unid.nome ");
        sql.append("  FROM endereco e ");
        sql.append("      INNER JOIN unidade unid ON unid.idEndereco = e.idEndereco ");
        sql.append("       LEFT JOIN cidades c ON c.idCidade = e.idCidade ");
        sql.append("       LEFT JOIN ufs uf on uf.idUf = c.idUf ");
        return sql;
    }

    private List<String> getColunasRestoreAll() {
        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idEndereco");
        listRetorno.add("logradouro");
        listRetorno.add("numero");
        listRetorno.add("complemento");
        listRetorno.add("bairro");
        listRetorno.add("idCidade");
        listRetorno.add("idPais");
        listRetorno.add("cep");
        listRetorno.add("latitude");
        listRetorno.add("longitude");
        listRetorno.add("nomeCidade");
        listRetorno.add("siglaUf");
        return listRetorno;
    }

    public Collection<EnderecoDTO> recuperaEnderecosEntregaProduto() throws PersistenceException {
        final List<?> parametro = new ArrayList<>();

        final StringBuilder sql = this.getSQLRestoreEnderecoUnidade();
        sql.append("  WHERE unid.aceitaEntregaProduto = 'S' ");
        sql.append("ORDER BY unid.nome");

        List lista = new ArrayList<>();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        final List<String> colunas = this.getColunasRestoreAll();
        colunas.add("identificacao");

        return engine.listConvertion(this.getBean(), lista, colunas);
    }

}
