package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class DicionarioDao extends CrudDaoDefaultImpl {

    public DicionarioDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<DicionarioDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> fields = new ArrayList<>();
        fields.add(new Field("iddicionario", "idDicionario", true, true, false, false));
        fields.add(new Field("idlingua", "idLingua", false, false, false, false));
        fields.add(new Field("nome", "nome", false, false, false, false));
        fields.add(new Field("valor", "valor", false, false, false, false));
        fields.add(new Field("personalizado", "personalizado", false, false, false, false));
        return fields;
    }

    @Override
    public String getTableName() {
        return "dicionario";
    }

    @Override
    public Collection<DicionarioDTO> list() throws PersistenceException {
        final List<String> listaRetorno = new ArrayList<>();
        listaRetorno.add("iddicionario");
        listaRetorno.add("idlingua");
        listaRetorno.add("nome");
        listaRetorno.add("valor");
        listaRetorno.add("sigla");

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT d.iddicionario, d.idlingua, d.nome, d.valor, l.sigla FROM ");
        sql.append(this.getTableName());
        sql.append(" d JOIN LINGUA l ON l.idlingua = d.idlingua ORDER BY d.idlingua");

        final List<?> list = this.execSQL(sql.toString(), null);
        return this.listConvertion(this.getBean(), list, listaRetorno);
    }

    @Override
    public Class<DicionarioDTO> getBean() {
        return DicionarioDTO.class;
    }

    /**
     * Consulta Dicionário de acordo com a Key (nome) e Id da Lingua informada.
     *
     * @param dicionarioDto
     * @return DicionarioDTO
     * @throws Exception
     */
    public DicionarioDTO findDicionarioByKeyAndLingua(final DicionarioDTO dicionarioDto) throws PersistenceException {
        final List<Object> parametro = new ArrayList<>();
        final StringBuilder sql = new StringBuilder();

        sql.append("select " + this.getNamesFieldsStr("dicionario") + ", lingua.sigla from " + this.getTableName());
        sql.append(" inner join lingua on dicionario.idlingua = lingua.idlingua");

        if (dicionarioDto.getNome() != null) {
            sql.append(" where  dicionario.nome = ? ");
            parametro.add(dicionarioDto.getNome());
        }

        if (dicionarioDto.getIdLingua() != null) {
            sql.append(" and dicionario.idlingua = ?");
            parametro.add(dicionarioDto.getIdLingua());
        }

        final List<?> list = this.execSQL(sql.toString(), parametro.toArray());

        final List<String> listRetorno = new ArrayList<>(this.getListNamesFieldClass());
        listRetorno.add("sigla");

        final List<DicionarioDTO> listaDicionario = this.listConvertion(this.getBean(), list, listRetorno);

        if (listaDicionario != null && !listaDicionario.isEmpty()) {
            return listaDicionario.get(0);
        }

        return dicionarioDto;

    }

    public Collection<DicionarioDTO> listaDicionario(final DicionarioDTO dicionarioDto) throws PersistenceException {
        final List<Integer> parametro = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();
        sql.append("select * from ");
        sql.append(this.getTableName());
        sql.append(" ");

        if (dicionarioDto.getIdLingua() != null) {
            sql.append("where idlingua = ? ");
            parametro.add(dicionarioDto.getIdLingua());
        }
        sql.append(" order by nome");

        final List<?> list = this.execSQL(sql.toString(), parametro.toArray());

        final List<String> listaRetorno = new ArrayList<>();
        listaRetorno.add("idDicionario");
        listaRetorno.add("nome");
        listaRetorno.add("valor");
        listaRetorno.add("idLingua");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(DicionarioDTO.class, list, listaRetorno);
        }
        return null;
    }

    public Collection<DicionarioDTO> listaPorDescricaoOuKey(final DicionarioDTO dicionarioDto) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();

        sql.append("SELECT dic.iddicionario, dic.nome, dic.valor from dicionario dic where dic.idlingua = ?");

        if (dicionarioDto.getKeyIdioma() != null) {
            sql.append(" AND dic.nome LIKE '%" + dicionarioDto.getKeyIdioma() + "%' ");
        }
        if (dicionarioDto.getKeyDescricao() != null) {
            sql.append(" AND dic.valor like '%" + dicionarioDto.getKeyDescricao() + "%' ");
        }
        sql.append("  ORDER BY dic.nome ");

        final List<Integer> parametro = new ArrayList<>();
        parametro.add(dicionarioDto.getIdLingua3());

        final List<?> list = this.execSQL(sql.toString(), parametro.toArray());

        final List<String> listaRetorno = new ArrayList<>();
        listaRetorno.add("idDicionario");
        listaRetorno.add("nome");
        listaRetorno.add("valor");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(DicionarioDTO.class, list, listaRetorno);
        }
        return null;
    }

    public Integer qtdItensCustomizados(final DicionarioDTO dicionarioDto) throws PersistenceException {
        final String sql = "SELECT COUNT(*) as qtdCustomizados from dicionario where personalizado LIKE 'S' and idlingua = ?";

        final List<Integer> parametro = new ArrayList<>();
        parametro.add(dicionarioDto.getIdLingua());

        final List<?> list = this.execSQL(sql, parametro.toArray());

        final List<String> listaRetorno = new ArrayList<>();
        listaRetorno.add("qtdCustomizados");

        if (list != null && !list.isEmpty()) {
            final List<DicionarioDTO> listaDicionario = this.listConvertion(DicionarioDTO.class, list, listaRetorno);
            return listaDicionario.get(0).getQtdCustomizados();
        }
        return 0;
    }

}
