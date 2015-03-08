package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ProdutoTarefaLinBaseProjDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ProdutoTarefaLinBaseProjDao extends CrudDaoDefaultImpl {
	public ProdutoTarefaLinBaseProjDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idTarefaLinhaBaseProjeto" ,"idTarefaLinhaBaseProjeto", true, false, false, false));
		listFields.add(new Field("idProdutoContrato" ,"idProdutoContrato", true, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ProdutoTarefaLinBaseProj";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ProdutoTarefaLinBaseProjDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdTarefaLinhaBaseProjeto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idTarefaLinhaBaseProjeto", "=", parm)); 
		ordenacao.add(new Order("idProdutoContrato"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdLinhaBaseProjeto(Integer idLinhaBaseProjeto) throws PersistenceException {
		List parametros = new ArrayList();
		String sql = "DELETE FROM ProdutoTarefaLinBaseProj WHERE idTarefaLinhaBaseProjeto IN (Select idTarefaLinhaBaseProjeto FROM TarefaLinhaBaseProjeto WHERE idLinhaBaseProjeto = ?)";
		parametros.add(idLinhaBaseProjeto);
		this.execUpdate(sql, parametros.toArray());
	}	
	public void deleteByIdTarefaLinhaBaseProjeto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idTarefaLinhaBaseProjeto", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdProdutoContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProdutoContrato", "=", parm)); 
		ordenacao.add(new Order("idTarefaLinhaBaseProjeto"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdProdutoContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProdutoContrato", "=", parm));
		super.deleteByCondition(condicao);
	}
}
