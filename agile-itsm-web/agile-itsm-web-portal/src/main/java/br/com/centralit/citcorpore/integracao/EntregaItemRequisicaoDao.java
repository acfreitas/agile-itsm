package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EntregaItemRequisicaoDTO;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoEntregaItemRequisicao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class EntregaItemRequisicaoDao extends CrudDaoDefaultImpl {
	public EntregaItemRequisicaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idEntrega" ,"idEntrega", true, true, false, false));
		listFields.add(new Field("idPedido" ,"idPedido", false, false, false, false));
		listFields.add(new Field("idColetaPreco" ,"idColetaPreco", false, false, false, false));
		listFields.add(new Field("idItemRequisicaoProduto" ,"idItemRequisicaoProduto", false, false, false, false));
	    listFields.add(new Field("idSolicitacaoServico" ,"idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("idItemTrabalho" ,"idItemTrabalho", false, false, false, false));
        listFields.add(new Field("idParecer" ,"idParecer", false, false, false, false));
		listFields.add(new Field("quantidadeEntregue" ,"quantidadeEntregue", false, false, false, false));
        listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "EntregaItemRequisicao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return EntregaItemRequisicaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
    public Collection findDisponiveisInspecaoByIdSolicitacaoServico(Integer idSolicitacaoServico) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        condicao.add(new Condition("situacao", "=", SituacaoEntregaItemRequisicao.Aguarda.name()));
        condicao.add(new Condition("idItemTrabalho", "IS", null));
        ordenacao.add(new Order("idItemRequisicaoProduto"));
        return super.findByCondition(condicao, ordenacao);
    }
    public Collection findInspecaoByIdSolicitacaoServico(Integer idSolicitacaoServico) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        condicao.add(new Condition("situacao", "=", SituacaoEntregaItemRequisicao.Aguarda.name()));
        condicao.add(new Condition("idItemTrabalho", "IS NOT", null));
        ordenacao.add(new Order("idItemRequisicaoProduto"));
        return super.findByCondition(condicao, ordenacao);
    }
	public Collection findByIdPedido(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idPedido", "=", parm));
		ordenacao.add(new Order("idEntrega"));
		return super.findByCondition(condicao, ordenacao);
	}
    public Collection findByIdSolicitacaoServico(Integer parm) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", parm));
        ordenacao.add(new Order("idEntrega"));
        return super.findByCondition(condicao, ordenacao);
    }
    public void excluiRelacionamentos(Collection<EntregaItemRequisicaoDTO> col) throws PersistenceException {
        if (col == null)
            return;
        InspecaoEntregaItemDao inspecaoEntregaDao = new InspecaoEntregaItemDao();
        inspecaoEntregaDao.setTransactionControler(this.getTransactionControler());
        for (EntregaItemRequisicaoDTO entregaDto : col) {
            inspecaoEntregaDao.deleteByIdEntrega(entregaDto.getIdEntrega());
        }
    }
	public void deleteByIdPedido(Integer parm) throws PersistenceException {
	    Collection col = findByIdPedido(parm);
	    excluiRelacionamentos(col);
		List condicao = new ArrayList();
		condicao.add(new Condition("idPedido", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdColetaPreco(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idColetaPreco", "=", parm));
		ordenacao.add(new Order("idEntrega"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdColetaPreco(Integer parm) throws PersistenceException {
        Collection col = findByIdColetaPreco(parm);
        excluiRelacionamentos(col);
		List condicao = new ArrayList();
		condicao.add(new Condition("idColetaPreco", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdItemRequisicaoProduto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemRequisicaoProduto", "=", parm));
		ordenacao.add(new Order("idEntrega"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdItemRequisicaoProduto(Integer parm) throws PersistenceException {
        Collection col = findByIdItemRequisicaoProduto(parm);
        excluiRelacionamentos(col);
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemRequisicaoProduto", "=", parm));
		super.deleteByCondition(condicao);
	}
    public Collection findByIdItemRequisicaoAndIdColetaPreco(Integer idItemRequisicaoProduto, Integer idColetaPreco) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idItemRequisicaoProduto", "=", idItemRequisicaoProduto));
        condicao.add(new Condition("idColetaPreco", "=", idColetaPreco));
        ordenacao.add(new Order("idEntrega"));
        return super.findByCondition(condicao, ordenacao);
    }
    public void deleteByIdItemRequisicaoAndIdColetaPreco(Integer idItemRequisicaoProduto, Integer idColetaPreco) throws PersistenceException {
        Collection col = findByIdItemRequisicaoAndIdColetaPreco(idItemRequisicaoProduto, idColetaPreco);
        excluiRelacionamentos(col);
        List condicao = new ArrayList();
        condicao.add(new Condition("idItemRequisicaoProduto", "=", idItemRequisicaoProduto));
        condicao.add(new Condition("idColetaPreco", "=", idColetaPreco));
        super.deleteByCondition(condicao);
    }
    private StringBuilder getSQLRestoreAll() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ei.idEntrega, ei.idColetaPreco, ei.idItemRequisicaoProduto, ei.idItemTrabalho, ei.idSolicitacaoServico, ei.quantidadeEntregue, ");
        sql.append("       ei.situacao, ei.idParecer, ped.numeroPedido, ped.dataEntrega, item.descricaoItem, forn.cnpj, forn.nomeFantasia, forn.tipoPessoa ");
        sql.append("  FROM entregaitemrequisicao ei ");
        sql.append("       INNER JOIN itemrequisicaoproduto item ON item.idItemRequisicaoProduto = ei.idItemRequisicaoProduto ");
        sql.append("       INNER JOIN coletapreco cp ON cp.idColetaPreco = ei.idColetaPreco ");
        sql.append("       INNER JOIN pedidocompra ped ON ped.idPedido = ei.idPedido ");
        sql.append("       INNER JOIN fornecedor forn ON forn.idfornecedor = cp.idfornecedor ");

        return sql;
    }

    private List getColunasRestoreAll() {
        List listRetorno = new ArrayList();
        listRetorno.add("idEntrega");
        listRetorno.add("idColetaPreco");
        listRetorno.add("idItemRequisicaoProduto");
        listRetorno.add("idItemTrabalho");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("quantidadeEntregue");
        listRetorno.add("situacao");
        listRetorno.add("idParecer");
        listRetorno.add("numeroPedido");
        listRetorno.add("dataEntrega");
        listRetorno.add("descricaoItem");
        listRetorno.add("cpfCnpjFornecedor");
        listRetorno.add("nomeFornecedor");
        listRetorno.add("tipoFornecedor");
        return listRetorno;
    }

    public Collection findByIdItemTrabalho(Integer parm) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ei.idItemTrabalho = ? ");

        parametro.add(parm);
        sql.append("ORDER BY item.descricaoItem");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }
    public Collection<EntregaItemRequisicaoDTO> findNaoAprovadasByIdSolicitacaoServico(Integer idSolicitacaoServico) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE ei.idSolicitacaoServico = ? ");
        sql.append("    AND ei.situacao = ? ");

        parametro.add(idSolicitacaoServico);
        parametro.add(SituacaoEntregaItemRequisicao.NaoAprovada.name());
        sql.append("ORDER BY item.descricaoItem");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }
    public Collection<EntregaItemRequisicaoDTO> findNaoAprovadasEDisponiveisByIdSolicitacaoServico(Integer idSolicitacaoServico) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        condicao.add(new Condition("situacao", "=", SituacaoEntregaItemRequisicao.NaoAprovada.name()));
        condicao.add(new Condition("idItemTrabalho", "IS", null));
        ordenacao.add(new Order("idItemRequisicaoProduto"));
        return super.findByCondition(condicao, ordenacao);
    }

    @Override
    public void updateNotNull(IDto obj) throws PersistenceException {
    	super.updateNotNull(obj);
    }
}
