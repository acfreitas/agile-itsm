package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoProcessoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class DelegacaoCentroResultadoProcessoDao extends CrudDaoDefaultImpl {
	public DelegacaoCentroResultadoProcessoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idDelegacaoCentroResultado" ,"idDelegacaoCentroResultado", true, false, false, false));
		listFields.add(new Field("idProcessoNegocio" ,"idProcessoNegocio", true, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "DelegCentroResultadoProcesso";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return DelegacaoCentroResultadoProcessoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdDelegacaoCentroResultado(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idDelegacaoCentroResultado", "=", parm)); 
		ordenacao.add(new Order("idProcessoNegocio"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdDelegacaoCentroResultado(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idDelegacaoCentroResultado", "=", parm));
		super.deleteByCondition(condicao);
	}
}
