package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

public class ItemRequisicaoProdutoDao extends CrudDaoDefaultImpl {
	public ItemRequisicaoProdutoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idItemRequisicaoProduto" ,"idItemRequisicaoProduto", true, true, false, false));
		listFields.add(new Field("idSolicitacaoServico" ,"idSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("idCategoriaProduto" ,"idCategoriaProduto", false, false, false, false));
        listFields.add(new Field("idProduto" ,"idProduto", false, false, false, false));
        listFields.add(new Field("idUnidadeMedida" ,"idUnidadeMedida", false, false, false, false));
        listFields.add(new Field("idParecerValidacao" ,"idParecerValidacao", false, false, false, false));		
        listFields.add(new Field("idParecerAutorizacao" ,"idParecerAutorizacao", false, false, false, false));  
		listFields.add(new Field("descricaoItem" ,"descricaoItem", false, false, false, false));
		listFields.add(new Field("especificacoes" ,"especificacoes", false, false, false, false));
		listFields.add(new Field("quantidade" ,"quantidade", false, false, false, false));
		listFields.add(new Field("marcaPreferencial" ,"marcaPreferencial", false, false, false, false));
		listFields.add(new Field("precoAproximado" ,"precoAproximado", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("percVariacaoPreco" ,"percVariacaoPreco", false, false, false, false));
        listFields.add(new Field("qtdeAprovada" ,"qtdeAprovada", false, false, false, false));		
        listFields.add(new Field("idItemCotacao" ,"idItemCotacao", false, false, false, false));      
        listFields.add(new Field("tipoAtendimento" ,"tipoAtendimento", false, false, false, false));      
        listFields.add(new Field("tipoIdentificacao" ,"tipoIdentificacao", false, false, false, false));     
        listFields.add(new Field("qtdeCotada" ,"qtdeCotada", false, false, false, false));  
        listFields.add(new Field("aprovaCotacao" ,"aprovaCotacao", false, false, false, false));      
        listFields.add(new Field("valorAprovado" ,"valorAprovado", false, false, false, false));      
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ItemRequisicaoProduto";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ItemRequisicaoProdutoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdSolicitacaoServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idSolicitacaoServico", "=", parm)); 
        condicao.add(new Condition("situacao", "<>", SituacaoItemRequisicaoProduto.Cancelado.name())); 
		ordenacao.add(new Order("idItemRequisicaoProduto"));
		return super.findByCondition(condicao, ordenacao);
	}
    public Collection findTodosByIdSolicitacaoServico(Integer parm) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("idSolicitacaoServico", "=", parm)); 
        ordenacao.add(new Order("idItemRequisicaoProduto"));
        return super.findByCondition(condicao, ordenacao);
    }
    public Collection findByIdSolicitacaoServicoAndSituacao(Integer parm, SituacaoItemRequisicaoProduto situacao) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("idSolicitacaoServico", "=", parm)); 
        condicao.add(new Condition("situacao", "=", situacao.name()));         
        ordenacao.add(new Order("idItemRequisicaoProduto"));
        return super.findByCondition(condicao, ordenacao);
    }	
    public Collection findByIdSolicitacaoAndSituacaoAndTipoAtendimento(Integer parm, SituacaoItemRequisicaoProduto situacao, String tipoAtendimento) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("idSolicitacaoServico", "=", parm)); 
        condicao.add(new Condition("situacao", "=", situacao.name()));         
        condicao.add(new Condition("tipoAtendimento", "=", tipoAtendimento));         
        ordenacao.add(new Order("idItemRequisicaoProduto"));
        return super.findByCondition(condicao, ordenacao);
    }   
    public void deleteByIdSolicitacaoServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdProduto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProduto", "=", parm)); 
        condicao.add(new Condition("situacao", "<>", SituacaoItemRequisicaoProduto.Cancelado.name())); 
		ordenacao.add(new Order("idItemRequisicaoProduto"));
		return super.findByCondition(condicao, ordenacao);
	}
    public Collection findByIdItemCotacao(Integer idItemCotacao) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("idItemCotacao", "=", idItemCotacao)); 
        ordenacao.add(new Order("idItemRequisicaoProduto"));
        return super.findByCondition(condicao, ordenacao);
    }
    public Collection findByIdItemCotacaoOrderQtde(Integer idItemCotacao) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("idItemCotacao", "=", idItemCotacao)); 
        ordenacao.add(new Order("qtdeAprovada",Order.DESC));
        return super.findByCondition(condicao, ordenacao);
    }
    
    private StringBuilder getSQLRestoreAll() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT item.idItemRequisicaoProduto, sol.idSolicitacaoServico, sol.dataHoraSolicitacao, sol.dataHoraLimite, req.idProjeto, req.idCentroCusto, ");
        sql.append("       item.idProduto, item.descricaoItem, item.especificacoes, item.precoAproximado, item.idUnidadeMedida, item.tipoIdentificacao, ");
        sql.append("       item.quantidade, item.marcaPreferencial, item.situacao, item.qtdeAprovada, item.percVariacaoPreco, item.tipoAtendimento, item.idCategoriaProduto, ");
        sql.append("       item.aprovaCotacao, item.qtdeCotada, item.valorAprovado, cc.nomeCentroResultado, proj.nomeProjeto, c.nomeCategoria, u.siglaUnidadeMedida, tprod.nomeProduto, prod.codigoProduto, req.idEnderecoEntrega   ");
        sql.append("  FROM itemrequisicaoproduto item ");
        sql.append("       INNER JOIN requisicaoproduto req ON req.idsolicitacaoservico = item.idsolicitacaoservico ");
        sql.append("       INNER JOIN solicitacaoservico sol ON sol.idsolicitacaoservico = item.idsolicitacaoservico ");
        sql.append("        LEFT JOIN produto prod  ON prod.idProduto = item.idProduto ");
        sql.append("        LEFT JOIN tipoproduto tprod  ON tprod.idTipoProduto = prod.idTipoProduto ");
        sql.append("        LEFT JOIN categoriaproduto c ON c.idCategoria = tprod.idCategoria ");
        sql.append("        LEFT JOIN unidademedida u ON u.idunidademedida = item.idunidademedida ");
        sql.append("        LEFT JOIN projetos proj ON proj.idprojeto = req.idprojeto ");
        sql.append("        LEFT JOIN centroresultado cc ON cc.idcentroresultado = req.idcentrocusto ");      
        return sql;
    }
    
    private List getColunasRestoreAll() {
        List listRetorno = new ArrayList();
        listRetorno.add("idItemRequisicaoProduto");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");        
        listRetorno.add("idProjeto");
        listRetorno.add("idCentroCusto");
        listRetorno.add("idProduto");
        listRetorno.add("descricaoItem");
        listRetorno.add("especificacoes");
        listRetorno.add("precoAproximado");
        listRetorno.add("idUnidadeMedida");
        listRetorno.add("tipoIdentificacao");
        listRetorno.add("quantidade");
        listRetorno.add("marcaPreferencial");
        listRetorno.add("situacao");
        listRetorno.add("qtdeAprovada");
        listRetorno.add("percVariacaoPreco");
        listRetorno.add("tipoAtendimento");
        listRetorno.add("idCategoriaProduto");
        listRetorno.add("aprovaCotacao");
        listRetorno.add("qtdeCotada");
        listRetorno.add("valorAprovado");
        listRetorno.add("nomeCentroCusto");
        listRetorno.add("nomeProjeto");
        listRetorno.add("nomeCategoria");
        listRetorno.add("siglaUnidadeMedida");
        listRetorno.add("nomeProduto");
        listRetorno.add("codigoProduto");        
        listRetorno.add("idEnderecoEntrega"); 
        return listRetorno;
    }
    
	
    public Collection<ItemRequisicaoProdutoDTO> recuperaItensParaCotacao(Date dataInicio, Date dataFim, Integer idCentroCusto,
            Integer idProjeto, Integer idEnderecoEntrega, Integer idSolicitacaoServico) throws PersistenceException {
        List parametro = new ArrayList();

        StringBuilder sql = getSQLRestoreAll();
        sql.append("  WHERE item.idItemCotacao IS NULL ");
        sql.append("    AND item.tipoAtendimento = 'C' ");
        sql.append("    AND sol.situacao = ? ");
        sql.append("    AND item.situacao = ? ");
        
        parametro.add(SituacaoSolicitacaoServico.EmAndamento.name());
        parametro.add(SituacaoItemRequisicaoProduto.AguardandoCotacao.name());

        if (idSolicitacaoServico != null) {
            sql.append("    AND item.idSolicitacaoServico = ? ");
            parametro.add(idSolicitacaoServico);
        }
        if (dataInicio != null) {
            sql.append("    AND sol.dataHoraSolicitacao >= ? ");
            parametro.add(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataInicio, "yyyy-MM-dd") + " 00:00:00"));
        }
        if (dataFim != null) {
            sql.append("    AND sol.dataHoraSolicitacao <= ? ");
            parametro.add(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataFim, "yyyy-MM-dd") + " 23:59:59"));
        }
        if (idProjeto != null) {
            sql.append("    AND req.idProjeto = ? ");
            parametro.add(idProjeto);
        }
        if (idCentroCusto != null) {
            sql.append("    AND req.idCentroCusto = ? ");
            parametro.add(idCentroCusto);
        }
        if (idEnderecoEntrega != null) {
            sql.append("    AND req.idEnderecoEntrega = ? ");
            parametro.add(idEnderecoEntrega);
        }
        sql.append("ORDER BY sol.dataHoraSolicitacao, tprod.nomeProduto");
        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        
        return this.engine.listConvertion(getBean(), lista, getColunasRestoreAll());
    }
    
    @Override
    public void updateNotNull(IDto obj) throws PersistenceException {
    	super.updateNotNull(obj);
    }

}
