package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MatrizPrioridadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class MatrizPrioridadeDAO extends CrudDaoDefaultImpl {

	public MatrizPrioridadeDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idMatrizPrioridade"));
		return super.find(obj, list);
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idMatrizPrioridade", "idMatrizPrioridade", true, true, false, false));
		listFields.add(new Field("siglaImpacto", "siglaImpacto", false, false, false, false));
		listFields.add(new Field("siglaUrgencia", "siglaUrgencia", false, false, false, false));
		listFields.add(new Field("valorPrioridade", "valorPrioridade", false, false, false, false));
		listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		
		return listFields;
	}

	@Override
	public String getTableName() {
		return "MatrizPrioridade";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idMatrizPrioridade"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return MatrizPrioridadeDTO.class;
	}
	
	public Integer consultaValorPrioridade(String siglaImpacto, String siglaUrgencia) throws PersistenceException {
		List parametros = new ArrayList();
		parametros.add(siglaImpacto);
		parametros.add(siglaUrgencia);
		
		String sql = "SELECT * FROM "+getTableName()+" WHERE siglaImpacto = ? and siglaUrgencia = ? ";
		List dados = this.execSQL(sql,parametros.toArray());
		
		List fields = new ArrayList();
		fields.add("idMatrizPrioridade");
		fields.add("siglaImpacto");
		fields.add("siglaUrgencia");
		fields.add("valorPrioridade");
		fields.add("idContrato");
		
		Integer valorResp = new Integer(0);
		
		Collection<MatrizPrioridadeDTO> listaMatriz = this.listConvertion(getBean(), dados, fields);

		if (listaMatriz != null && !listaMatriz.isEmpty()) {
			for (MatrizPrioridadeDTO matrizPrioridadeDTO : listaMatriz) {
				return matrizPrioridadeDTO.getValorPrioridade();
			}
		}
		return valorResp;
		
	}
	
	public void deleteMatriz() throws PersistenceException {
//		String sql = "delete from " + getTableName();
//		this.execUpdate(sql, null);
		List condicao = new ArrayList();
		List parametros = new ArrayList();
		parametros.add(0);
		condicao.add(new Condition("idMatrizPrioridade", ">", parametros));
		super.deleteByCondition(condicao);
	}

}
