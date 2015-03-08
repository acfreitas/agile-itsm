/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * DAO de TipoItemConfiguracao.
 * 
 * @author valdoilo.damasceno
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TipoItemConfiguracaoDAO extends CrudDaoDefaultImpl {

	private static final String SQL_TIPOITEMCONFIGURACAO = "SELECT IDTIPOITEMCONFIGURACAO, IDEMPRESA, NOMETIPOITEMCONFIGURACAO, TAGTIPOITEMCONFIGURACAO, DATAINICIO, DATAFIM "
			+ "FROM TIPOITEMCONFIGURACAO " + "where UPPER(NOMETIPOITEMCONFIGURACAO) = ? order by NOMETIPOITEMCONFIGURACAO";

	/**
	 * Construtor padrão.
	 */
	public TipoItemConfiguracaoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDTIPOITEMCONFIGURACAO", "id", true, true, false, false));
		listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
		listFields.add(new Field("NOMETIPOITEMCONFIGURACAO", "nome", false, false, false, false));
		listFields.add(new Field("TAGTIPOITEMCONFIGURACAO", "tag", false, false, false, false));
		listFields.add(new Field("CATEGORIA", "categoria", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		listFields.add(new Field("SISTEMA", "sistema", false, false, false, false));
		listFields.add(new Field("imagem", "imagem", false, false, false, false));

		return listFields;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.integracao.CrudDaoDefaultImpl#getTableName()
	 */
	@Override
	public String getTableName() {
		return "TIPOITEMCONFIGURACAO";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.integracao.CrudDaoDefaultImpl#list()
	 */
	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("NOMETIPOITEMCONFIGURACAO"));
		return super.list(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.citframework.integracao.DaoTransactDefaultImpl#getBean()
	 */
	@Override
	public Class getBean() {
		return TipoItemConfiguracaoDTO.class;
	}

	/**
	 * Consulta Tipo Item Configuração por nome.
	 * 
	 * @param nomeTipoItemConfiguracao
	 * @return List
	 * @throws Exception
	 */
	public List findByNomeTipoItemConfiguracao(String nomeTipoItemConfiguracao) throws PersistenceException {
		Object[] objs = new Object[] { nomeTipoItemConfiguracao.toUpperCase() };

		String sql = SQL_TIPOITEMCONFIGURACAO;

		List lista = this.execSQL(sql, objs);

		List result = this.engine.listConvertion(getBean(), lista, prepararListaDeRetorno());
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
		listRetorno.add("id");
		listRetorno.add("idEmpresa");
		listRetorno.add("nome");
		listRetorno.add("tag");
		listRetorno.add("dataInicio");
		listRetorno.add("dataFim");
		return listRetorno;
	}

	/**
	 * Verifica se Tipo Item Configuração informada existe.
	 * 
	 * @param grupo
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeTipoItemConfiguracaoExiste(TipoItemConfiguracaoDTO tipoItemConfiguracao) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select IDTIPOITEMCONFIGURACAO from " + getTableName() + "  where  NOMETIPOITEMCONFIGURACAO = ? and datafim is null  AND IDTIPOITEMCONFIGURACAO <> ? ");
		parametro.add(tipoItemConfiguracao.getNome());
		parametro.add(tipoItemConfiguracao.getId() == null ? 0 : tipoItemConfiguracao.getId());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verifica se existe Tipo Item Configuracao associado a um Item Configuracao
	 * 
	 * @param tipoItemConfiguracao
	 * @return
	 * @throws PersistenceException
	 * @author thyen.chang
	 */
	public boolean verificaAssociacaoTipoConfiguracao(TipoItemConfiguracaoDTO tipoItemConfiguracao) throws PersistenceException{
		List parametros = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT idtipoitemconfiguracao ");
		sql.append("FROM itemconfiguracao ");
		sql.append("WHERE idtipoitemconfiguracao = ?");
		parametros.add(tipoItemConfiguracao.getId());
		list = this.execSQL(sql.toString(), parametros.toArray());
		if(list != null && !list.isEmpty()) //Caso exista algum item, isso significa que o Tipo item Configuração possui uma associação com Item Configuração
			return true;
		else
			return false;
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
}
