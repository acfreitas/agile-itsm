package br.com.centralit.citcorpore.integracao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

public class CidadesDao extends CrudDaoDefaultImpl {

    public CidadesDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<CidadesDTO> find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idCidade", "idCidade", true, true, false, false));
        listFields.add(new Field("nomeCidade", "nomeCidade", false, false, false, true));
        listFields.add(new Field("idUf", "idUf", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "CIDADES";
    }

    public Collection<CidadesDTO> listByIdUf(final Integer idUf) throws PersistenceException {
        final List<Condition> lstCond = new ArrayList<>();
        lstCond.add(new Condition("idUf", "=", idUf));

        final List<Order> lstOrder = new ArrayList<>();
        lstOrder.add(new Order("nomeCidade"));

        return super.findByCondition(lstCond, lstOrder);
    }

    @Override
    public Collection<CidadesDTO> list() throws PersistenceException {
        final List<Order> list = new ArrayList<>();
        list.add(new Order("nomeCidade"));
        return super.list(list);
    }

    @Override
    public Class<CidadesDTO> getBean() {
        return CidadesDTO.class;
    }

    public CidadesDTO findByCodigoIBGE(final String codigoIBGE) throws PersistenceException {
        final Object[] objs = new Object[] {codigoIBGE};
        final List<?> lista = this.execSQL("SELECT idCidade, nomeCidade, idUf, codigoIBGE FROM CIDADES WHERE codigoIBGE = ?", objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idCidade");
        listRetorno.add("nomeCidade");
        listRetorno.add("idUf");
        listRetorno.add("codigoIBGE");

        final List<?> result = engine.listConvertion(this.getBean(), lista, listRetorno);
        if (result != null && result.size() > 0) {
            return (CidadesDTO) result.get(0);
        } else {
            return null;
        }
    }

    public Collection<CidadesDTO> listByIdCidades(final CidadesDTO obj) throws PersistenceException {
        final List<String> fields = new ArrayList<>();

        final String sql = "select idcidade, nomecidade from " + this.getTableName() + " where iduf = ? ";
        fields.add("idCidade");
        fields.add("nomeCidade");

        final List<?> list = this.execSQL(sql, new Object[] {obj.getIdUf()});

        final Collection<CidadesDTO> result = engine.listConvertion(this.getBean(), list, fields);
        if (result != null && result.size() > 0) {
            return result;
        } else {
            return null;
        }
    }

    /**
     * Realiza consulta por nome atraves do AutoComplete
     *
     * @param nome
     * @return
     * @throws Exception
     */
    public Collection<CidadesDTO> findByNome(String nome) throws PersistenceException {
        if (nome == null) {
            nome = "";
        }

        String texto = Normalizer.normalize(nome, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{ASCII}]", "");
        texto = texto.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC ");
        texto = "%" + texto.toUpperCase() + "%";

        final Object[] objs = new Object[] {texto};

        final StringBuilder sql = new StringBuilder("select c.idcidade, c.nomecidade, uf.iduf, uf.siglauf from cidades c ");
        sql.append(" inner join ufs uf on uf.iduf = c.iduf ");
        sql.append(" where upper(c.nomecidade) like upper(?)");
        sql.append(" order by uf.siglauf, c.nomecidade ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append(" ");
        } else {
            sql.append(" limit 10 ");
        }

        final List<?> list = this.execSQL(sql.toString(), objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idCidade");
        listRetorno.add("nomeCidade");
        listRetorno.add("IdUf");
        listRetorno.add("nomeUf");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

    public List<CidadesDTO> findCidadeUF(final Integer idCidade) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List<Integer> parametros = new ArrayList<>();

        sql.append("select c.idcidade, c.nomecidade, uf.iduf, uf.siglauf from cidades c ").append(" inner join ufs uf on uf.iduf = c.iduf ").append(" where c.idcidade = ? ")
                .append(" order by uf.siglauf");

        parametros.add(idCidade);

        final List<?> list = this.execSQL(sql.toString(), parametros.toArray());

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idCidade");
        listRetorno.add("nomeCidade");
        listRetorno.add("IdUf");
        listRetorno.add("nomeUf");

        return this.listConvertion(this.getBean(), list, listRetorno);
    }

    public CidadesDTO findByIdCidade(final Integer idCidade) throws PersistenceException {
        final StringBuilder sql = new StringBuilder();
        final List<Integer> parametros = new ArrayList<>();

        sql.append("select c.idcidade, c.nomecidade, uf.iduf, uf.siglauf from cidades c ");
        sql.append(" inner join ufs uf on uf.iduf = c.iduf ");
        sql.append(" where upper(c.idcidade) = ? ");
        sql.append(" order by uf.siglauf");

        parametros.add(idCidade);

        final List<?> list = this.execSQL(sql.toString(), parametros.toArray());

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idCidade");
        listRetorno.add("nomeCidade");
        listRetorno.add("IdUf");
        listRetorno.add("nomeUf");

        final List<CidadesDTO> res = this.listConvertion(this.getBean(), list, listRetorno);
        return res.get(0);
    }

    public List<CidadesDTO> findByIdEstadoAndNomeCidade(final Integer IdEstado, final String nomeCidade) throws PersistenceException {
        String texto = Normalizer.normalize(nomeCidade, Normalizer.Form.NFD);
        texto = texto.replaceAll("[^\\p{ASCII}]", "");
        texto = texto.replaceAll("·‡„‚ÈÍÌÛÙı˙¸Á¡¿√¬… Õ”‘’⁄‹«¥`^''-+=", "aaaaeeiooouucAAAAEEIOOOUUC ");
        texto = "%" + texto.toUpperCase() + "%";

        final Object[] objs = new Object[] {IdEstado, texto};

        final StringBuilder sql = new StringBuilder("select c.idcidade, c.nomecidade from cidades c ").append(" inner join ufs uf on uf.iduf = c.iduf ")
                .append(" where uf.iduf = ? and upper(c.nomecidade) like upper(?) ").append(" order by uf.siglauf, c.nomecidade limit 10");

        final List<?> list = this.execSQL(sql.toString(), objs);

        final List<String> listRetorno = new ArrayList<>();
        listRetorno.add("idCidade");
        listRetorno.add("nomeCidade");

        return engine.listConvertion(this.getBean(), list, listRetorno);
    }

}
