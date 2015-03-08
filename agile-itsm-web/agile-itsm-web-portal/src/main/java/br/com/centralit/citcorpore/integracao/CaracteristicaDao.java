/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * DAO de Caracteristica.
 *
 * @author valdoilo.damasceno
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CaracteristicaDao extends CrudDaoDefaultImpl {

	private static final String SQL_GET_CARACTERISTICAS = "SELECT DISTINCT caracteristica.idcaracteristica, " + "caracteristica.nomecaracteristica, " + "caracteristica.tagcaracteristica, " + "caracteristica.descricao, "
			+ "caracteristica.idEmpresa, " + "caracteristica.dataInicio, " + "caracteristica.dataFim " + "FROM caracteristica caracteristica " + "INNER JOIN tipoitemcfgcaracteristica tipo "
			+ "ON caracteristica.idcaracteristica = tipo.idcaracteristica " + "LEFT JOIN valor valor on caracteristica.idcaracteristica = valor.idcaracteristica ";

	private static final String SQL_GET_EXISTE_CARACTERISTICAS = "select C.idcaracteristica, C.nomecaracteristica, C.tagcaracteristica, " + "C.descricao, C.idEmpresa, C.dataInicio, C.dataFim   "
			+ " from tipoitemconfiguracao  A " + " INNER JOIN tipoitemcfgcaracteristica B on A.idtipoitemconfiguracao = B.idtipoitemconfiguracao "
			+ " INNER JOIN caracteristica C on C.idcaracteristica = B.idcaracteristica ";

	public CaracteristicaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto caracteristicaDto) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("nome"));
		return super.find(caracteristicaDto, ordem);
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDCARACTERISTICA", "idCaracteristica", true, true, false, false));
		listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
		listFields.add(new Field("NOMECARACTERISTICA", "nome", false, false, false, false));
		listFields.add(new Field("TAGCARACTERISTICA", "tag", false, false, false, false));
		listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
		listFields.add(new Field("SISTEMA", "sistema", false, false, false, false));
		listFields.add(new Field("TIPO", "tipo", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "CARACTERISTICA";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nome"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return CaracteristicaDTO.class;
	}

	/**
	 * Consulta Características por idTipoItemConfiguracao.
	 *
	 * @param idTipoItemConfiguracao
	 *            Identificador do Tipo Item Configuracao.
	 * @param dataFim
	 *            Se false, retorna Características que não foram excluídas.
	 * @return List
	 * @throws Exception
	 * @author VMD
	 */
	public List consultarCaracteristicasAtivas(Integer idTipoItemConfiguracao) throws PersistenceException {
		Object[] objs = new Object[] { idTipoItemConfiguracao };

		String sql = SQL_GET_CARACTERISTICAS;
		sql += " WHERE (tipo.idtipoitemconfiguracao = ?) " + "and (caracteristica.dataFim is null) " + "and (tipo.datafim is null) " + "ORDER BY caracteristica.nomecaracteristica";

		List lista = this.execSQL(sql, objs);

		List listRetorno = prepararListaDeRetorno();

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0)
			return null;
		return result;
	}

	/**
	 * Consulta Características por idTipoItemConfiguracao passando um array de caracteristicas
	 *
	 * @param idTipoItemConfiguracao
	 *            Identificador do Tipo Item Configuracao.
	 * @param dataFim
	 *            Se false, retorna Características que não foram excluídas.
	 * @return List
	 * @throws Exception
	 * @author flavio.santana
	 */
	public List consultarCaracteristicasAtivas(Integer idTipoItemConfiguracao, String [] arr) throws PersistenceException {
		Object[] objs = new Object[] { idTipoItemConfiguracao };

		String sql = SQL_GET_CARACTERISTICAS;
		sql += " WHERE (tipo.idtipoitemconfiguracao = ?) " + "and (caracteristica.dataFim is null) " + "and (tipo.datafim is null) ";
		boolean flag = false;
		if(arr.length > 0) {
			sql+=" AND caracteristica.nomecaracteristica in (";
			for (int i = 0; i < arr.length; i++) {
				if(!flag) {
					sql+=" '" + arr[i] + "' ";
					flag = true;
				}
				else {
					sql+=", '" + arr[i] + "' ";
				}

			}
			sql+=") ";
		}
		sql+="ORDER BY caracteristica.nomecaracteristica";
		List lista = this.execSQL(sql, objs);

		List listRetorno = prepararListaDeRetorno();

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0)
			return null;
		return result;
	}

	/**
	 * Consulta Características por idTipoItemConfiguracao.
	 *
	 * @param idTipoItemConfiguracao
	 *            Identificador do Tipo Item Configuracao.
	 * @param dataFim
	 *            Se false, retorna Características que não foram excluídas.
	 * @return List
	 * @throws Exception
	 */
	public List consultarCaracteristicas(String tagtipoitemconfiguracao, String tagcaracteristica, Boolean dataFim) throws PersistenceException {
		List objs = new ArrayList();
		objs.add(tagcaracteristica.toUpperCase());

		String sql = SQL_GET_EXISTE_CARACTERISTICAS;

		sql += " WHERE ";
		sql += " (UPPER(C.TAGCARACTERISTICA) = ?) ";

		if (tagtipoitemconfiguracao.length() > 0) {
			sql += " AND  UPPER(A.tagtipoitemconfiguracao) = ? ";
			objs.add(tagtipoitemconfiguracao);
		}

		if (dataFim == false) {
			sql += "and (C.dataFim is null) ";

		}
		sql += " ORDER BY C.TAGCARACTERISTICA";

		List lista = this.execSQL(sql, objs.toArray());

		List listRetorno = this.prepararListaDeRetorno();

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0)
			return null;
		return result;
	}

	/**
	 * Prepara Lista de Retorno.
	 *
	 * @return List
	 */
	private List prepararListaDeRetorno() {
		List listRetorno = new ArrayList();
		listRetorno.add("idCaracteristica");
		listRetorno.add("nome");
		listRetorno.add("tag");
		listRetorno.add("descricao");
		listRetorno.add("idEmpresa");
		listRetorno.add("dataInicio");
		listRetorno.add("dataFim");
		return listRetorno;
	}

	/**
	 * Verifica se Caracteristica informada existe.
	 *
	 * @param grupo
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 * @author Thays.araujo
	 */

	public boolean verificarSeCaracteristicaExiste(CaracteristicaDTO caracteristica) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select IDCARACTERISTICA from " + getTableName() + "  where  NOMECARACTERISTICA = ? and datafim is null ");
		parametro.add(caracteristica.getNome());

		if (caracteristica.getIdCaracteristica() != null) {

			sql.append(" and IDCARACTERISTICA <> ?");

			parametro.add(caracteristica.getIdCaracteristica());

		}
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
