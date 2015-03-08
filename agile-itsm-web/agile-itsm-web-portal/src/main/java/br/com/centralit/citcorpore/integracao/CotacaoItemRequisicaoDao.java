package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CotacaoItemRequisicaoDTO;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacaoItemRequisicao;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CotacaoItemRequisicaoDao extends CrudDaoDefaultImpl {
	public CotacaoItemRequisicaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idColetaPreco" ,"idColetaPreco", true, false, false, false));
		listFields.add(new Field("idItemRequisicaoProduto" ,"idItemRequisicaoProduto", true, false, false, false));
		listFields.add(new Field("idParecer" ,"idParecer", false, false, false, false));
        listFields.add(new Field("idItemTrabalho" ,"idItemTrabalho", false, false, false, false));
        listFields.add(new Field("idSolicitacaoServico" ,"idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("idCotacao" ,"idCotacao", false, false, false, false));
		listFields.add(new Field("quantidade" ,"quantidade", false, false, false, false));
        listFields.add(new Field("quantidadeEntregue" ,"quantidadeEntregue", false, false, false, false));
	    listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "CotacaoItemRequisicao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return CotacaoItemRequisicaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findDisponiveisAprovacaoByIdSolicitacaoServico(Integer idSolicitacaoServico) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        condicao.add(new Condition("situacao", "=", SituacaoCotacaoItemRequisicao.AguardaAprovacao.name()));
        condicao.add(new Condition("idItemTrabalho", "IS", null));
        ordenacao.add(new Order("idItemRequisicaoProduto"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection findByIdCotacao(Integer idCotacao) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idCotacao", "=", idCotacao));
        ordenacao.add(new Order("idItemRequisicaoProduto"));
        return super.findByCondition(condicao, ordenacao);
    }
    public Collection findPendentesByIdCotacao(Integer idCotacao) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idCotacao", "=", idCotacao));
        condicao.add(new Condition("situacao", "=", SituacaoCotacaoItemRequisicao.AguardaAprovacao.name()));
        ordenacao.add(new Order("idItemRequisicaoProduto"));
        return super.findByCondition(condicao, ordenacao);
    }
	public Collection findByIdRequisicaoProduto(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ci.idSolicitacaoServico = ? ");

        parametro.add(parm);
        sql.append("ORDER BY ci.idItemRequisicaoProduto");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
	}
	public Collection findAprovadasByIdCentroResultado(Integer idCentroResultado) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  INNER JOIN solicitacaoservico sol ON sol.idSolicitacaoServico = ci.idSolicitacaoServico ");
        sql.append("  WHERE rp.idCentroCusto = ? ");
        sql.append("    AND sol.situacao <> ? ");
        sql.append("    AND item.situacao <> ? ");
        sql.append("    AND (ci.situacao = ? or ci.situacao = ?) ");

        parametro.add(idCentroResultado);
        parametro.add(SituacaoSolicitacaoServico.Cancelada.name());
        parametro.add(SituacaoItemRequisicaoProduto.Cancelado.name());
        parametro.add(SituacaoCotacaoItemRequisicao.PreAprovado.name());
        parametro.add(SituacaoCotacaoItemRequisicao.Aprovado.name());
        sql.append("ORDER BY ci.idItemRequisicaoProduto");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
	}
	public Collection findByIdColetaPreco(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ci.idColetaPreco = ? ");

        parametro.add(parm);
        sql.append("ORDER BY item.descricaoItem");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
	}
    public Collection findByIdColetaPrecoOrderQtde(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ci.idColetaPreco = ? ");

        parametro.add(parm);
        sql.append("ORDER BY item.quantidade desc");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }
    public void excluiRelacionamentos(Collection<CotacaoItemRequisicaoDTO> col) throws PersistenceException {
        if (col == null)
            return;
        EntregaItemRequisicaoDao inspecaoEntregaDao = new EntregaItemRequisicaoDao();
        inspecaoEntregaDao.setTransactionControler(this.getTransactionControler());
        for (CotacaoItemRequisicaoDTO itemDto : col) {
            inspecaoEntregaDao.deleteByIdItemRequisicaoAndIdColetaPreco(itemDto.getIdItemRequisicaoProduto(), itemDto.getIdColetaPreco());
        }
    }
	public void deleteByIdColetaPreco(Integer parm) throws PersistenceException {
        Collection<CotacaoItemRequisicaoDTO> col = findByIdColetaPreco(parm);
        excluiRelacionamentos(col);
		List condicao = new ArrayList();
		condicao.add(new Condition("idColetaPreco", "=", parm));
		super.deleteByCondition(condicao);
	}
    public void deleteByIdCotacao(Integer parm) throws PersistenceException {
        Collection<CotacaoItemRequisicaoDTO> col = findByIdCotacao(parm);
        excluiRelacionamentos(col);
        List condicao = new ArrayList();
        condicao.add(new Condition("idCotacao", "=", parm));
        super.deleteByCondition(condicao);
    }
    public Collection findByIdItemRequisicaoProduto(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ci.idItemRequisicaoProduto = ? ");

        parametro.add(parm);
        sql.append("ORDER BY ci.idColetaPreco");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }
	public void deleteByIdItemRequisicaoProduto(Integer parm) throws PersistenceException {
        Collection<CotacaoItemRequisicaoDTO> col = findByIdItemRequisicaoProduto(parm);
        excluiRelacionamentos(col);
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemRequisicaoProduto", "=", parm));
		super.deleteByCondition(condicao);
	}

    private StringBuilder getSQLRestoreAll() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ci.idColetaPreco, ci.idItemRequisicaoProduto, ci.idParecer, ci.idItemTrabalho,  ");
        sql.append("       ci.idSolicitacaoServico, ci.quantidade, ci.quantidadeEntregue, ci.situacao, ci.idCotacao, ");
        sql.append("       par.idJustificativa, par.complementoJustificativa, par.aprovado, item.idProduto, prod.idMarca, item.idUnidadeMedida, ");
        sql.append("       item.descricaoItem, item.idCategoriaProduto, item.percVariacaoPreco, prod.codigoProduto, tprod.nomeProduto, forn.cnpj, forn.nomeFantasia, ");
        sql.append("       c.nomeCategoria, u.siglaUnidadeMedida, cp.especificacoes, ((cp.preco - cp.valorDesconto + cp.valorAcrescimo) / cp.quantidadeCotada), ");
        sql.append("       ((cp.preco - cp.valorDesconto + cp.valorAcrescimo) / cp.quantidadeCotada) * ci.quantidade, cp.prazoEntrega, cp.taxaJuros, forn.tipoPessoa, cp.idItemCotacao, item.idParecerAutorizacao, rp.finalidade   ");
        sql.append("  FROM cotacaoitemrequisicao ci ");
        sql.append("       INNER JOIN itemrequisicaoproduto item ON item.idItemRequisicaoProduto = ci.idItemRequisicaoProduto ");
        sql.append("       INNER JOIN coletapreco cp ON cp.idColetaPreco = ci.idColetaPreco ");
        sql.append("       INNER JOIN itemcotacao ic ON ic.idItemCotacao = cp.idItemCotacao ");
        sql.append("       INNER JOIN fornecedor forn ON forn.idfornecedor = cp.idfornecedor ");
        sql.append("       INNER JOIN requisicaoproduto rp ON rp.idSolicitacaoServico = ci.idSolicitacaoServico ");
        sql.append("        LEFT JOIN parecer par ON par.idParecer = ci.idParecer ");
        sql.append("        LEFT JOIN produto prod ON prod.idProduto = item.idProduto ");
        sql.append("        LEFT JOIN tipoproduto tprod  ON tprod.idTipoProduto = prod.idTipoProduto ");
        sql.append("        LEFT JOIN categoriaproduto c ON c.idCategoria = tprod.idCategoria ");
        sql.append("        LEFT JOIN unidademedida u ON u.idunidademedida = item.idunidademedida ");

        return sql;
    }

    private List getColunasRestoreAll() {
        List listRetorno = new ArrayList();
        listRetorno.add("idColetaPreco");
        listRetorno.add("idItemRequisicaoProduto");
        listRetorno.add("idParecer");
        listRetorno.add("idItemTrabalho");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("quantidade");
        listRetorno.add("quantidadeEntregue");
        listRetorno.add("situacao");
        listRetorno.add("idCotacao");
        listRetorno.add("idJustificativa");
        listRetorno.add("complementoJustificativa");
        listRetorno.add("aprovado");
        listRetorno.add("idProduto");
        listRetorno.add("idMarca");
        listRetorno.add("idUnidadeMedida");
        listRetorno.add("descricaoItem");
        listRetorno.add("idCategoriaProduto");
        listRetorno.add("percVariacaoPreco");
        listRetorno.add("codigoProduto");
        listRetorno.add("nomeProduto");
        listRetorno.add("cpfCnpjFornecedor");
        listRetorno.add("nomeFornecedor");
        listRetorno.add("nomeCategoria");
        listRetorno.add("siglaUnidadeMedida");
        listRetorno.add("especificacoes");
        listRetorno.add("preco");
        listRetorno.add("valorTotal");
        listRetorno.add("prazoEntrega");
        listRetorno.add("taxaJuros");
        listRetorno.add("tipoFornecedor");
        listRetorno.add("idItemCotacao");
        listRetorno.add("idParecerAutorizacao");
        listRetorno.add("finalidade");
        return listRetorno;
    }

    public Collection findByIdItemTrabalho(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ci.idItemTrabalho = ? ");

        parametro.add(parm);
        sql.append("ORDER BY item.descricaoItem");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }
}
