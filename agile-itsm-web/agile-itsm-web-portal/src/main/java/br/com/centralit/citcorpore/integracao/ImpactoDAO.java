package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ImpactoDTO;
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
public class ImpactoDAO extends CrudDaoDefaultImpl {
	
	public ImpactoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@Override
	public Collection find(IDto obj) throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idImpacto"));
		return super.find(obj, list);
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idImpacto", "idImpacto", true, true, false, false));
		listFields.add(new Field("nivelImpacto", "nivelImpacto", false, false, false, false));
		listFields.add(new Field("siglaImpacto", "siglaImpacto", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return "Impacto";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idImpacto"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return ImpactoDTO.class;
	}
	
	/*public void deleteImpacto() throws PersistenceException{
		List parametros = new ArrayList();
		parametros.add(0);
		String sql = "delete from " + getTableName() +" where idimpacto > ?";
		this.execSQL(sql, null);
		this.execUpdate(sql, parametros.toArray());
	}*/
	
	public void deleteImpacto() throws PersistenceException {
		List condicao = new ArrayList();
		List parametros = new ArrayList();
		parametros.add(0);
		condicao.add(new Condition("idImpacto", ">", parametros));
		super.deleteByCondition(condicao);
	}
	
	public List<ImpactoDTO> restoreBySigla(String siglaImpacto) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("siglaImpacto", "=", siglaImpacto));
		return (List<ImpactoDTO>) super.findByCondition(condicao, null);
	}
	
	public List<ImpactoDTO> restoreByNivel(String nivelImpacto) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("nivelImpacto", "=", nivelImpacto));
		return (List<ImpactoDTO>) super.findByCondition(condicao, null);
	}

}
