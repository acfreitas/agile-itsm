package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.TipoProdutoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProdutoDao extends CrudDaoDefaultImpl {
	public ProdutoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	} 
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idProduto" ,"idProduto", true, true, false, false));
		listFields.add(new Field("idTipoProduto" ,"idTipoProduto", false, false, false, false));
		listFields.add(new Field("idMarca" ,"idMarca", false, false, false, false));
        listFields.add(new Field("modelo" ,"modelo", false, false, false, false));
        listFields.add(new Field("detalhes" ,"detalhes", false, false, false, false));
        listFields.add(new Field("precomercado" ,"precomercado", false, false, false, false));
        listFields.add(new Field("codigoProduto" ,"codigoProduto", false, false, false, false));
        listFields.add(new Field("complemento" ,"complemento", false, false, false, false));
        listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
	          
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Produto";
	}
	public Collection list() throws PersistenceException {
		return null;
	}
	public Class getBean() {
		return ProdutoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public void deleteByIdTipoProduto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idTipoProduto", "=", parm));
		super.deleteByCondition(condicao);
	}
   private StringBuilder getSQLRestore() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT e.idProduto, t.idTipoProduto, t.idCategoria, t.idUnidadeMedida, t.nomeProduto, ");
        sql.append("       t.aceitaRequisicao, e.codigoProduto, e.detalhes, e.precoMercado, ");
        sql.append("       e.idMarca, e.modelo, e.situacao, c.nomeCategoria, u.siglaUnidadeMedida, m.nomeMarca, e.complemento  ");
        sql.append("  FROM produto e  ");
        sql.append("      INNER JOIN tipoproduto t ON t.idTipoProduto = e.idTipoProduto ");
        sql.append("       LEFT JOIN marca m ON m.idMarca = e.idMarca ");
        sql.append("      INNER JOIN categoriaproduto c ON c.idCategoria = t.idCategoria ");
        sql.append("       LEFT JOIN unidademedida u ON u.idUnidadeMedida = t.idUnidadeMedida ");
        return sql;
    }
    
    private List getColunasRestore() {
        List listRetorno = new ArrayList();
        listRetorno.add("idProduto");
        listRetorno.add("idTipoProduto");
        listRetorno.add("idCategoria");
        listRetorno.add("idUnidadeMedida");
        listRetorno.add("nomeProduto");
        listRetorno.add("aceitaRequisicao");
        listRetorno.add("codigoProduto");
        listRetorno.add("detalhes");
        listRetorno.add("precoMercado");
        listRetorno.add("idMarca");
        listRetorno.add("modelo");
        listRetorno.add("situacao"); 
        listRetorno.add("nomeCategoria"); 
        listRetorno.add("siglaUnidadeMedida"); 
        listRetorno.add("nomeMarca"); 
        listRetorno.add("complemento"); 
        
        return listRetorno;
    }  
    
    @Override
    public IDto restore(IDto obj) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestore();
        sql.append("  WHERE e.idProduto = ? ");
        
        parametro.add(((ProdutoDTO) obj).getIdProduto());
        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        
        List<ProdutoDTO> result = this.engine.listConvertion(getBean(), lista, getColunasRestore());
        if (result != null && !result.isEmpty())
            return result.get(0);
        else
            return null;
    }   
    
    public List<TipoProdutoDTO> findByIdCategoria(Integer idCategoria) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestore();
        sql.append("  WHERE t.idCategoria = ? ");
        sql.append("  ORDER BY t.nomeProduto, m.nomeMarca ");
        
        parametro.add(idCategoria);
        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        
        return this.engine.listConvertion(getBean(), lista, getColunasRestore());
    } 	
    
    public Collection findByIdTipoProduto(Integer idTipoProduto) throws PersistenceException {
    	List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("idTipoProduto", "=", idTipoProduto));
        ordenacao.add(new Order("idMarca"));
        return super.findByCondition(condicao, ordenacao);
    }
    
    public Collection findByIdCategoriaAndAceitaRequisicao(Integer idCategoria, String aceitaRequisicao) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestore();
        sql.append("  WHERE 1 = 1 ");
        if (idCategoria != null) {
            sql.append("  AND t.idCategoria = ? ");
            parametro.add(idCategoria);
        }
        
        sql.append("    AND t.aceitaRequisicao = ? ");
        sql.append("  ORDER BY t.nomeProduto, m.nomeMarca ");

        parametro.add(aceitaRequisicao);
        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        
        return this.engine.listConvertion(getBean(), lista, getColunasRestore());
    }   
    
    public Collection validaNovoProduto(ProdutoDTO produtoDTO) throws PersistenceException {
    	List parametro = new ArrayList();
        
    	StringBuilder sql = getSQLRestore();
        sql.append("  WHERE e.codigoProduto = ? ");
        
        parametro.add(produtoDTO.getCodigoProduto());
        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        
        return this.engine.listConvertion(getBean(), lista, getColunasRestore());
    	
    }

}
