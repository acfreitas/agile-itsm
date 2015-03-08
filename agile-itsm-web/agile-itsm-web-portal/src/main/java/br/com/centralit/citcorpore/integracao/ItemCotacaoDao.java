package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemCotacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ItemCotacaoDao extends CrudDaoDefaultImpl {
	public ItemCotacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idItemCotacao" ,"idItemCotacao", true, true, false, false));
		listFields.add(new Field("idCotacao" ,"idCotacao", false, false, false, false));
		listFields.add(new Field("idProduto" ,"idProduto", false, false, false, false));
		listFields.add(new Field("quantidade" ,"quantidade", false, false, false, false));
        listFields.add(new Field("dataHoraLimite" ,"dataHoraLimite", false, false, false, false));  
        listFields.add(new Field("idCategoriaProduto" ,"idCategoriaProduto", false, false, false, false));
        listFields.add(new Field("idUnidadeMedida" ,"idUnidadeMedida", false, false, false, false));
        listFields.add(new Field("descricaoItem" ,"descricaoItem", false, false, false, false));
        listFields.add(new Field("especificacoes" ,"especificacoes", false, false, false, false));
        listFields.add(new Field("marcaPreferencial" ,"marcaPreferencial", false, false, false, false));
        listFields.add(new Field("precoAproximado" ,"precoAproximado", false, false, false, false));
        listFields.add(new Field("tipoIdentificacao" ,"tipoIdentificacao", false, false, false, false));
        listFields.add(new Field("resultadoValidacao","resultadoValidacao",false, false, false, false));  
        listFields.add(new Field("mensagensValidacao","mensagensValidacao",false, false, false, false));  
        listFields.add(new Field("pesoPreco" ,"pesoPreco", false, false, false, false));
        listFields.add(new Field("pesoPrazoEntrega" ,"pesoPrazoEntrega", false, false, false, false));
        listFields.add(new Field("pesoPrazoPagto" ,"pesoPrazoPagto", false, false, false, false));
        listFields.add(new Field("pesoTaxaJuros" ,"pesoTaxaJuros", false, false, false, false));
        listFields.add(new Field("pesoPrazoGarantia" ,"pesoPrazoGarantia", false, false, false, false));
        listFields.add(new Field("exigeFornecedorQualificado" ,"exigeFornecedorQualificado", false, false, false, false));
        
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ItemCotacao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ItemCotacaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
    
    private StringBuilder getSQLRestoreAll() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT item.idItemCotacao, item.idCotacao, item.idProduto, item.descricaoItem, item.especificacoes, item.idCategoriaProduto," );
        sql.append("       item.precoAproximado, item.idUnidadeMedida, item.tipoIdentificacao, item.quantidade, item.marcaPreferencial, ");
        sql.append("       item.dataHoraLimite, item.resultadoValidacao, item.mensagensValidacao, item.exigeFornecedorQualificado, ");
        sql.append("       item.pesoPreco, item.pesoPrazoEntrega, item.pesoPrazoPagto, item.pesoTaxaJuros, item.pesoPrazoGarantia, ");
        sql.append("       prod.codigoProduto, tp.nomeProduto, prod.complemento, prod.modelo, m.nomeMarca ");
        sql.append("  FROM itemcotacao item ");
        sql.append("        LEFT JOIN produto prod ON prod.idproduto = item.idproduto ");
        sql.append("        LEFT JOIN tipoproduto tp ON tp.idtipoproduto = prod.idtipoproduto ");
        sql.append("        LEFT JOIN marca m ON m.idmarca = prod.idmarca ");
        return sql;
    }
    
    private List getColunasRestoreAll() {
    
        List listRetorno = new ArrayList();

        listRetorno.add("idItemCotacao");
        listRetorno.add("idCotacao");
        listRetorno.add("idProduto");
        listRetorno.add("descricaoItem");
        listRetorno.add("especificacoes");
        listRetorno.add("idCategoriaProduto");
        listRetorno.add("precoAproximado");
        listRetorno.add("idUnidadeMedida");
        listRetorno.add("tipoIdentificacao");
        listRetorno.add("quantidade");
        listRetorno.add("marcaPreferencial");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("resultadoValidacao");
        listRetorno.add("mensagensValidacao");
        listRetorno.add("exigeFornecedorQualificado");
        listRetorno.add("pesoPreco");        
        listRetorno.add("pesoPrazoEntrega");        
        listRetorno.add("pesoPrazoPagto");        
        listRetorno.add("pesoTaxaJuros");        
        listRetorno.add("pesoPrazoGarantia");    
        listRetorno.add("codigoProduto");
        listRetorno.add("nomeProduto");
        listRetorno.add("complemento");
        listRetorno.add("modelo");
        listRetorno.add("nomeMarca");
        return listRetorno;
    }	
    
    public Collection findByIdCotacao(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE item.idCotacao = ? ");
        
        parametro.add(parm);
        sql.append("ORDER BY tp.nomeProduto");
        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        
        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }

    public void deleteByIdCotacao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCotacao", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdItemRequisicaoProduto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idItemRequisicaoProduto", "=", parm)); 
		ordenacao.add(new Order("idItemCotacao"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdItemRequisicaoProduto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemRequisicaoProduto", "=", parm));
		super.deleteByCondition(condicao);
	}
	
    public void limpaMensagensValidacao(ItemCotacaoDTO itemCotacaoDto) throws PersistenceException {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + getTableName() + " SET resultadoValidacao = null, mensagensValidacao = null WHERE idItemCotacao = ?");
        Object[] params = {itemCotacaoDto.getIdItemCotacao() };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização do item de cotação.");
            e.printStackTrace();
        }
    }
}
