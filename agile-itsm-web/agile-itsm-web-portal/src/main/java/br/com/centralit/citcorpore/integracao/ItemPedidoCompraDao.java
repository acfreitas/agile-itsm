package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemPedidoCompraDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ItemPedidoCompraDao extends CrudDaoDefaultImpl {
	public ItemPedidoCompraDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idItemPedido" ,"idItemPedido", true, true, false, false));
		listFields.add(new Field("idPedido" ,"idPedido", false, false, false, false));
	    listFields.add(new Field("idColetaPreco" ,"idColetaPreco", false, false, false, false));
		listFields.add(new Field("idProduto" ,"idProduto", false, false, false, false));
		listFields.add(new Field("quantidade" ,"quantidade", false, false, false, false));
		listFields.add(new Field("valorTotal" ,"valorTotal", false, false, false, false));
		listFields.add(new Field("valorDesconto" ,"valorDesconto", false, false, false, false));
		listFields.add(new Field("valorAcrescimo" ,"valorAcrescimo", false, false, false, false));
		listFields.add(new Field("baseCalculoIcms" ,"baseCalculoIcms", false, false, false, false));
		listFields.add(new Field("aliquotaIcms" ,"aliquotaIcms", false, false, false, false));
		listFields.add(new Field("aliquotaIpi" ,"aliquotaIpi", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ItemPedidoCompra";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ItemPedidoCompraDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdPedido(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idPedido", "=", parm));
		ordenacao.add(new Order("idItemPedido"));
		return super.findByCondition(condicao, ordenacao);
	}
    public Collection findByIdColetaPreco(Integer parm) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idColetaPreco", "=", parm));
        ordenacao.add(new Order("idItemPedido"));
        return super.findByCondition(condicao, ordenacao);
    }
	public void deleteByIdPedido(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idPedido", "=", parm));
		super.deleteByCondition(condicao);
	}
    public void deleteByIdColetaPreco(Integer parm) throws PersistenceException {
        List condicao = new ArrayList();
        condicao.add(new Condition("idColetaPreco", "=", parm));
        super.deleteByCondition(condicao);
    }

    private StringBuilder getSQLRestoreAll() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ip.idItemPedido, ip.idPedido, ip.idColetaPreco, ip.idProduto, ip.quantidade, ip.valorTotal, ip.valorDesconto, ip.valorAcrescimo, ");
        sql.append("       item.descricaoItem, item.idSolicitacaoServico, item.idParecerValidacao, item.idParecerAutorizacao, cp.idParecer, e1.nome, e2.nome, e3.nome ");
        sql.append("  FROM itempedidocompra ip ");
        sql.append("       INNER JOIN cotacaoitemrequisicao cp ON cp.idColetaPreco = ip.idColetaPreco ");
        sql.append("       INNER JOIN itemrequisicaoproduto item ON item.idItemRequisicaoProduto = cp.idItemRequisicaoProduto ");
        sql.append("        LEFT JOIN parecer p1 ON item.idParecerValidacao = p1.idParecer ");
        sql.append("        LEFT JOIN parecer p2 ON item.idParecerAutorizacao = p2.idParecer ");
        sql.append("        LEFT JOIN parecer p3 ON cp.idParecer = p3.idParecer ");
        sql.append("        LEFT JOIN empregados e1 ON p1.idResponsavel = e1.idEmpregado ");
        sql.append("        LEFT JOIN empregados e2 ON p2.idResponsavel = e2.idEmpregado ");
        sql.append("        LEFT JOIN empregados e3 ON p3.idResponsavel = e3.idEmpregado ");

        return sql;
    }

    private List getColunasRestoreAll() {
        List listRetorno = new ArrayList();
		listRetorno.add("idItemPedido");
		listRetorno.add("idPedido");
	    listRetorno.add("idColetaPreco");
		listRetorno.add("idProduto");
		listRetorno.add("quantidade");
		listRetorno.add("valorTotal");
		listRetorno.add("valorDesconto");
		listRetorno.add("valorAcrescimo");
		listRetorno.add("descricaoItem");
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("idParecerValidacao");
		listRetorno.add("idParecerAutorizacao");
		listRetorno.add("idParecerCotacao");
		listRetorno.add("autoridadeValidacao");
		listRetorno.add("autoridadeAprovacao");
		listRetorno.add("autoridadeCotacao");
        return listRetorno;
    }

    public Collection findByIdPedidoOrderByIdSolicitacao(Integer idPedido) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ip.idPedido = ? ");

        parametro.add(idPedido);
        sql.append("ORDER BY item.idSolicitacaoServico, ip.idItemPedido");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }
}
