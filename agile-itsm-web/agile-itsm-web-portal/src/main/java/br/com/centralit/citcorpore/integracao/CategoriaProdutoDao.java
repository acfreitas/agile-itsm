package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CategoriaProdutoDao extends CrudDaoDefaultImpl {
    @Override
	public IDto restore(IDto obj) throws PersistenceException {
return super.restore(obj);
	}
	private static final String SQL_GET_LISTA_SEM_PAI = 
        "SELECT idCategoria, idCategoriaPai, nomeCategoria, situacao " +
        "  FROM categoriaproduto " +
        " WHERE idCategoriaPai IS NULL " +
        " ORDER BY nomeCategoria";
    
	public CategoriaProdutoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idCategoria" ,"idCategoria", true, true, false, false));
        listFields.add(new Field("idCategoriaPai" ,"idCategoriaPai", false, false, false, false));
		listFields.add(new Field("nomeCategoria" ,"nomeCategoria", false, false, false, false));
        listFields.add(new Field("pesoCotacaoPreco" ,"pesoCotacaoPreco", false, false, false, false));
        listFields.add(new Field("pesoCotacaoPrazoEntrega" ,"pesoCotacaoPrazoEntrega", false, false, false, false));
        listFields.add(new Field("pesoCotacaoPrazoPagto" ,"pesoCotacaoPrazoPagto", false, false, false, false));
        listFields.add(new Field("pesoCotacaoTaxaJuros" ,"pesoCotacaoTaxaJuros", false, false, false, false));
        listFields.add(new Field("pesoCotacaoPrazoGarantia" ,"pesoCotacaoPrazoGarantia", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "categoriaproduto";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return CategoriaProdutoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
    public Collection<CategoriaProdutoDTO> findSemPai() throws PersistenceException {
        Object[] objs = new Object[] {};
        List lista = this.execSQL(SQL_GET_LISTA_SEM_PAI, objs);
        
        List listRetorno = new ArrayList();
        listRetorno.add("idCategoria");
        listRetorno.add("idCategoriaPai");
        listRetorno.add("nomeCategoria");
        listRetorno.add("situacao");
        
        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        return result;      
    }
    public Collection<CategoriaProdutoDTO> findByIdPai(Integer idPai) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("idCategoriaPai", "=", idPai));
        ordenacao.add(new Order("nomeCategoria"));
        return super.findByCondition(condicao, ordenacao);
    }
    
    /**
     * Se existir igual retorna true.
     */
    public boolean existeIgual(CategoriaProdutoDTO categoriaProduto) throws PersistenceException {
    	List condicao = new ArrayList();
    	List ordenacao = new ArrayList();
    	condicao.add(new Condition("nomeCategoria", "=", categoriaProduto.getNomeCategoria()));
    	if (categoriaProduto.getIdCategoria() != null)
            condicao.add(new Condition("idCategoria", "<>", categoriaProduto.getIdCategoria()));
    	ordenacao.add(new Order("nomeCategoria"));
    	
    	List result = (List) super.findByCondition(condicao, ordenacao);
    	
    	if (result != null && !result.isEmpty()) 
            return true;
        else
            return false;
    }
}
