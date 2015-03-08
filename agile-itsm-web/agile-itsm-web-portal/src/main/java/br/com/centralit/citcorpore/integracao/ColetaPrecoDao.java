package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ColetaPrecoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ColetaPrecoDao extends CrudDaoDefaultImpl {
	public ColetaPrecoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idColetaPreco" ,"idColetaPreco", true, true, false, false));
		listFields.add(new Field("idItemCotacao" ,"idItemCotacao", false, false, false, false));
        listFields.add(new Field("idFornecedor" ,"idFornecedor", false, false, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
        listFields.add(new Field("especificacoes" ,"especificacoes", false, false, false, false));
        listFields.add(new Field("dataColeta" ,"dataColeta", false, false, false, false));
		listFields.add(new Field("dataValidade" ,"dataValidade", false, false, false, false));
		listFields.add(new Field("preco" ,"preco", false, false, false, false));
        listFields.add(new Field("valorAcrescimo" ,"valorAcrescimo", false, false, false, false));
        listFields.add(new Field("valorDesconto" ,"valorDesconto", false, false, false, false));
        listFields.add(new Field("valorFrete" ,"valorFrete", false, false, false, false));
		listFields.add(new Field("quantidadeCotada" ,"quantidadeCotada", false, false, false, false));
        listFields.add(new Field("prazoEntrega" ,"prazoEntrega", false, false, false, false));
        listFields.add(new Field("prazoMedioPagto" ,"prazoMedioPagto", false, false, false, false));
        listFields.add(new Field("taxaJuros" ,"taxaJuros", false, false, false, false));
        listFields.add(new Field("prazoGarantia" ,"prazoGarantia", false, false, false, false));
		listFields.add(new Field("pontuacao" ,"pontuacao", false, false, false, false));
        listFields.add(new Field("resultadoCalculo" ,"resultadoCalculo", false, false, false, false));
        listFields.add(new Field("resultadoFinal" ,"resultadoFinal", false, false, false, false));
        listFields.add(new Field("quantidadeCalculo" ,"quantidadeCalculo", false, false, false, false));
        listFields.add(new Field("quantidadeAprovada" ,"quantidadeAprovada", false, false, false, false));
		listFields.add(new Field("quantidadeCompra" ,"quantidadeCompra", false, false, false, false));
	    listFields.add(new Field("quantidadePedido" ,"quantidadePedido", false, false, false, false));
        listFields.add(new Field("idRespResultado" ,"idRespResultado", false, false, false, false));
        listFields.add(new Field("idJustifResultado" ,"idJustifResultado", false, false, false, false));
        listFields.add(new Field("complemJustifResultado" ,"complemJustifResultado", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ColetaPreco";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ColetaPrecoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public void excluiRelacionamentos(Collection<ColetaPrecoDTO> col) throws PersistenceException {
        if (col == null)
            return;
        AvaliacaoColetaPrecoDao avaliacaoColetaDao = new AvaliacaoColetaPrecoDao();
        avaliacaoColetaDao.setTransactionControler(this.getTransactionControler());
        ItemPedidoCompraDao itemPedidoDao = new ItemPedidoCompraDao();
        itemPedidoDao.setTransactionControler(this.getTransactionControler());
        EntregaItemRequisicaoDao inspecaoEntregaDao = new EntregaItemRequisicaoDao();
        inspecaoEntregaDao.setTransactionControler(this.getTransactionControler());
        CotacaoItemRequisicaoDao cotacaoItemRequisicaoDao = new CotacaoItemRequisicaoDao();
        cotacaoItemRequisicaoDao.setTransactionControler(this.getTransactionControler());
        for (ColetaPrecoDTO coletaPrecoDto : col) {
            avaliacaoColetaDao.deleteByIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
            itemPedidoDao.deleteByIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
            inspecaoEntregaDao.deleteByIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
            cotacaoItemRequisicaoDao.deleteByIdColetaPreco(coletaPrecoDto.getIdColetaPreco());
        }
	}
	public Collection findByIdItemCotacao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemCotacao", "=", parm));
		ordenacao.add(new Order("idColetaPreco"));
		return super.findByCondition(condicao, ordenacao);
	}
    public Collection findByIdItemCotacaoAndPontuacao(Integer idItemCotacao, Double pontuacao) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idItemCotacao", "=", idItemCotacao));
        condicao.add(new Condition("pontuacao", "=", pontuacao));
        ordenacao.add(new Order("idColetaPreco"));
        return super.findByCondition(condicao, ordenacao);
    }
	public void deleteByIdItemCotacao(Integer parm) throws PersistenceException {
	    Collection<ColetaPrecoDTO> col = findByIdItemCotacao(parm);
	    excluiRelacionamentos(col);
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemCotacao", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdPedido(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idPedido", "=", parm));
		ordenacao.add(new Order("idColetaPreco"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdFornecedor(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE cp.idFornecedor = ? ");

        parametro.add(parm);
        sql.append("ORDER BY ic.descricaoItem");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
	}
	public Collection findByIdCotacaoAndIdFornecedor(Integer idCotacao, Integer idFornecedor) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ic.idCotacao = ? AND cp.idFornecedor = ? ");

        parametro.add(idCotacao);
        parametro.add(idFornecedor);
        sql.append("ORDER BY ic.descricaoItem");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
	}
	public void deleteByIdFornecedor(Integer parm) throws PersistenceException {
        Collection<ColetaPrecoDTO> col = findByIdFornecedor(parm);
        excluiRelacionamentos(col);
		List condicao = new ArrayList();
		condicao.add(new Condition("idFornecedor", "=", parm));
		super.deleteByCondition(condicao);
	}
    public Collection<ColetaPrecoDTO> findByIdItemCotacaoAndIdFornecedor(Integer idFornecedor, Integer idItemCotacao) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idFornecedor", "=", idFornecedor));
        condicao.add(new Condition("idItemCotacao", "=", idItemCotacao));
        ordenacao.add(new Order("idColetaPreco"));
        return super.findByCondition(condicao, ordenacao);
    }
    public void deleteByIdItemCotacaoAndIdFornecedor(Integer idFornecedor, Integer idItemCotacao) throws PersistenceException {
	    Collection<ColetaPrecoDTO> col = findByIdItemCotacaoAndIdFornecedor(idFornecedor,idItemCotacao);
	    excluiRelacionamentos(col);
        List condicao = new ArrayList();
        condicao.add(new Condition("idFornecedor", "=", idFornecedor));
        condicao.add(new Condition("idItemCotacao", "=", idItemCotacao));
        super.deleteByCondition(condicao);
    }

    private StringBuilder getSQLRestoreAll() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ic.idCotacao, cp.idColetaPreco, cp.idItemCotacao, cp.idFornecedor, ");
        sql.append("       cp.idResponsavel, cp.dataColeta, cp.dataValidade, cp.preco, cp.valorDesconto, cp.valorAcrescimo, ");
        sql.append("       cp.valorFrete, cp.prazoEntrega, cp.prazoMedioPagto, cp.taxaJuros, cp.prazoGarantia, cp.especificacoes, cp.quantidadeCotada, ");
        sql.append("       cp.pontuacao, cp.resultadoCalculo, cp.quantidadeCalculo, cp.resultadoFinal, cp.quantidadeCompra, cp.quantidadeAprovada, cp.quantidadePedido, ");
        sql.append("       cp.idRespResultado, cp.idJustifResultado, cp.complemJustifResultado, ic.idProduto, p.codigoProduto, ic.descricaoItem, ic.quantidade, f.cnpj, f.nomeFantasia ");
        sql.append("  FROM coletapreco cp ");
        sql.append("      INNER JOIN itemcotacao ic ON ic.iditemcotacao = cp.iditemcotacao ");
        sql.append("      INNER JOIN fornecedor f ON f.idfornecedor = cp.idfornecedor ");
        sql.append("       LEFT JOIN produto p ON p.idProduto = ic.idProduto ");
        return sql;
    }

    private List getColunasRestoreAll() {

        List listRetorno = new ArrayList();
        listRetorno.add("idCotacao");
        listRetorno.add("idColetaPreco");
        listRetorno.add("idItemCotacao");
        listRetorno.add("idFornecedor");
        listRetorno.add("idResponsavel");
        listRetorno.add("dataColeta");
        listRetorno.add("dataValidade");
        listRetorno.add("preco");
        listRetorno.add("valorDesconto");
        listRetorno.add("valorAcrescimo");
        listRetorno.add("valorFrete");
        listRetorno.add("prazoEntrega");
        listRetorno.add("prazoMedioPagto");
        listRetorno.add("taxaJuros");
        listRetorno.add("prazoGarantia");
        listRetorno.add("especificacoes");
        listRetorno.add("quantidadeCotada");
        listRetorno.add("pontuacao");
        listRetorno.add("resultadoCalculo");
        listRetorno.add("quantidadeCalculo");
        listRetorno.add("resultadoFinal");
        listRetorno.add("quantidadeCompra");
        listRetorno.add("quantidadeAprovada");
        listRetorno.add("quantidadePedido");
        listRetorno.add("idRespResultado");
        listRetorno.add("idJustifResultado");
        listRetorno.add("complemJustifResultado");
        listRetorno.add("idProduto");
        listRetorno.add("codigoProduto");
        listRetorno.add("descricaoItem");
        listRetorno.add("quantidade");
        listRetorno.add("cpfCnpjFornecedor");
        listRetorno.add("nomeFornecedor");
        return listRetorno;
    }

    public Collection findByIdCotacao(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ic.idCotacao = ? ");

        parametro.add(parm);
        sql.append("ORDER BY cp.idColetaPreco");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }

    public Collection findHabilitadasByIdCotacao(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE cp.resultadoFinal = 'M' ");
        sql.append("    AND cp.quantidadeCompra > 0 ");
        sql.append("    AND ic.idCotacao = ? ");

        parametro.add(parm);
        sql.append("ORDER BY cp.idColetaPreco");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }

    public Collection findHabilitadasByIdItemCotacao(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE cp.resultadoFinal = 'M' ");
        sql.append("    AND cp.quantidadeCompra > 0 ");
        sql.append("    AND ic.idItemCotacao = ? ");

        parametro.add(parm);
        sql.append("ORDER BY cp.idColetaPreco");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }

    public Collection findResultadoByIdItemCotacao(Integer idItemCotacao) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ic.idItemCotacao = ? ");

        parametro.add(idItemCotacao);
        sql.append("ORDER BY cp.pontuacao desc, cp.resultadoFinal desc, cp.quantidadeCompra desc, cp.idColetaPreco");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }

    public void atualizaResultadoCalculo(ColetaPrecoDTO coletaPrecoDto) throws PersistenceException {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + getTableName() + " SET pontuacao = ?, quantidadeCalculo = ?, resultadoCalculo = ? WHERE (idColetaPreco = ?)");
        Object[] params = {coletaPrecoDto.getPontuacao(), coletaPrecoDto.getQuantidadeCalculo(), coletaPrecoDto.getResultadoCalculo(), coletaPrecoDto.getIdColetaPreco() };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização da pontuação da coleta de preços.");
            e.printStackTrace();
        }
    }
    public void atualizaResultadoFinal(ColetaPrecoDTO coletaPrecoDto) throws PersistenceException {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + getTableName() + " SET quantidadeCompra = ?, " +
        		"resultadoFinal = ?, idRespResultado = ?, idJustifResultado = ?, complemJustifResultado = ? WHERE (idColetaPreco = ?)");
        Object[] params = {coletaPrecoDto.getQuantidadeCompra(), coletaPrecoDto.getResultadoFinal(),
                           coletaPrecoDto.getIdRespResultado(), coletaPrecoDto.getIdJustifResultado(),
                           coletaPrecoDto.getComplemJustifResultado(), coletaPrecoDto.getIdColetaPreco() };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização da pontuação da coleta de preços.");
            e.printStackTrace();
        }
    }

    @Override
    public IDto restore(IDto obj) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE cp.idColetaPreco = ? ");

        parametro.add(((ColetaPrecoDTO) obj).getIdColetaPreco());

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        List<ColetaPrecoDTO> result = this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
        if (result != null && !result.isEmpty())
            return result.get(0);
        else
            return null;

    }

    @Override
    public void delete(IDto obj) throws PersistenceException {
    	Collection col = new ArrayList();
    	col.add(obj);
    	excluiRelacionamentos(col);
    	super.delete(obj);
    }
}
