package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FornecedorProdutoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FornecedorProdutoDao extends CrudDaoDefaultImpl {

	public FornecedorProdutoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idFornecedorProduto", "idFornecedorProduto", true, true, false, false));
		listFields.add(new Field("idFornecedor", "idFornecedor", false, false, false, false));
		listFields.add(new Field("idTipoProduto", "idTipoProduto", false, false, false, false));
		listFields.add(new Field("idMarca", "idMarca", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "FornecedorProduto";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return FornecedorProdutoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdTipoProduto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idTipoProduto", "=", parm));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("idMarca"));
		return super.findByCondition(condicao, ordenacao);
	}

	public FornecedorProdutoDTO findByIdTipoProdutoAndFornecedor(Integer parm, Integer parm2) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idTipoProduto", "=", parm));
		condicao.add(new Condition("idFornecedor", "=", parm2));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("idMarca"));
		List resultados = (List) findByCondition(condicao, ordenacao);
		if (resultados != null) {
			return (FornecedorProdutoDTO) resultados.get(0);
		} else
			return null;
	}
	
	

}
