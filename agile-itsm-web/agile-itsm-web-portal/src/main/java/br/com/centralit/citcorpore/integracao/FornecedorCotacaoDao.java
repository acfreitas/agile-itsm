package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FornecedorCotacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class FornecedorCotacaoDao extends CrudDaoDefaultImpl {
	public FornecedorCotacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idCotacao" ,"idCotacao", true, false, false, false));
		listFields.add(new Field("idFornecedor" ,"idFornecedor", true, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "FornecedorCotacao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return FornecedorCotacaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdCotacao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCotacao", "=", parm)); 
		ordenacao.add(new Order("idFornecedor"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCotacao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCotacao", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdFornecedor(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idFornecedor", "=", parm)); 
		ordenacao.add(new Order("idCotacao"));
		return super.findByCondition(condicao, ordenacao);
	}
}
