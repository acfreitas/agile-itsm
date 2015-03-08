package br.com.centralit.citcorpore.integracao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExternalConnectionDTO;
import br.com.centralit.citcorpore.bean.ImportManagerDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author thiago.borges
 *
 */
public class ExternalConnectionDao extends CrudDaoDefaultImpl {
	public ExternalConnectionDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idexternalconnection" ,"idExternalConnection", true, true, false, false));
		listFields.add(new Field("nome" ,"nome", false, false, false, false));
		listFields.add(new Field("tipo" ,"tipo", false, false, false, false));
		listFields.add(new Field("urljdbc" ,"urlJdbc", false, false, false, false));
		listFields.add(new Field("jdbcdbname" ,"jdbcDbName", false, false, false, false));
		listFields.add(new Field("jdbcdriver" ,"jdbcDriver", false, false, false, false));
		listFields.add(new Field("jdbcuser" ,"jdbcUser", false, false, false, false));
		listFields.add(new Field("jdbcpassword" ,"jdbcPassword", false, false, false, false));
		listFields.add(new Field("filename" ,"fileName", false, false, false, false));
		listFields.add(new Field("schemadb" ,"schemaDb", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ExternalConnection";
	}
	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nome"));
		return super.list(list);
	}

	public Class getBean() {
		return ExternalConnectionDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Object getLastValueFromDestino(ImportManagerDTO importManagerDTO, String idDestino) throws PersistenceException{
		String sql = "SELECT MAX(" + idDestino + ") FROM " + importManagerDTO.getTabelaDestino();
		List lstData = this.execSQL(sql, null);
		if (lstData != null && lstData.size() > 0){
			Object o = ((Object[])lstData.get(0))[0];
			if (o == null){
				return 1;
			}
			if (o instanceof Integer){
				Integer intAux = (Integer)o;
				return intAux.intValue() + 1;
			}
			if (o instanceof Long){
				Long aux = (Long)o;
				return aux.longValue() + 1;
			}
			if (o instanceof Double){
				Double aux = (Double)o;
				return aux.doubleValue() + 1;
			}
			if (o instanceof BigDecimal){
				BigDecimal aux = (BigDecimal)o;
				return aux.doubleValue() + 1;
			}
			if (o instanceof BigInteger){
				BigInteger aux = (BigInteger)o;
				return aux.doubleValue() + 1;
			}	
			//Se chegou aqui, eh que nao passou por lugar algum acima.
			Integer intAux = (Integer)o;
			return intAux.intValue() + 1;			
		}
		return 1;
	}	
	public void executeSQLUpdate(String sql, Object[] objs) throws PersistenceException{
		this.execUpdate(sql, objs);
	}
	
	public boolean consultarConexoesAtivas(ExternalConnectionDTO obj) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idexternalconnection From " + getTableName() + "  where  nome = ?   and deleted is null ";
		
		if(obj.getIdExternalConnection() != null){
			sql+=" and idexternalconnection <> "+ obj.getIdExternalConnection();
		}
		
		parametro.add(obj.getNome());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean validaInsert(ExternalConnectionDTO obj){
		
		
		return false;
		
	}
	
	public Collection findByNome(ExternalConnectionDTO conexoesDTO) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nome", "=", conexoesDTO.getNome())); 
		ordenacao.add(new Order("nome"));
		condicao.add(new Condition(Condition.AND, "deleted", "is", null));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public Collection<ExternalConnectionDTO>  seConexaoJaCadastrada(ExternalConnectionDTO conexoesDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "";
		sql = " select lower(nome) from externalconnection where nome = lower(?) ";

		parametro.add(conexoesDTO.getNome().trim().toLowerCase());
		list = this.execSQL(sql, parametro.toArray());
		return list;
	}
	
	public Collection<ExternalConnectionDTO> listarAtivas() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		ordenacao.add(new Order("nome"));
		condicao.add(new Condition("deleted", "is", null));
		return super.findByCondition(condicao, ordenacao);
	}
}
