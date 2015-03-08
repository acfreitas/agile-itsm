package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoRequisicaoComprasDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequisicaoLiberacaoRequisicaoComprasDAO extends CrudDaoDefaultImpl {
	public RequisicaoLiberacaoRequisicaoComprasDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idrequisicaoliberacaocompras" ,"idRequisicaoLiberacaoCompras", true, true, false, false));
		listFields.add(new Field("idRequisicaoLiberacao" ,"idRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("idSolicitacaoServico" ,"idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idItemRequisicaoProduto" ,"idItemRequisicaoProduto", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		return listFields;

	}
	public String getTableName() {
		return this.getOwner() + "requisicaoliberacaoCompras";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return RequisicaoLiberacaoRequisicaoComprasDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	
	public Collection findByIdLiberacao(Integer idRequisicaoLiberacao) throws Exception {
		List fields = new ArrayList(); 
		
		String sql = " select ReqCompras.idRequisicaoLiberacaocompras, ReqCompras.idRequisicaoLiberacao, ReqCompras.idSolicitacaoServico, serv.nomeServico, ss.situacao"+
				" from requisicaoliberacaocompras ReqCompras "+ 
				" inner join liberacao lib on ReqCompras.idRequisicaoLiberacao = lib.idLiberacao "+
				" inner join solicitacaoservico ss on ReqCompras.idSolicitacaoServico = ss.idSolicitacaoServico "+
				" inner join servicocontrato sc on ss.idservicocontrato = sc.idservicocontrato "+
				" inner join servico serv on serv.idservico = sc.idservico "+
				" where ReqCompras.idRequisicaoLiberacao = ? ";
				
		
	  List resultado = 	execSQL(sql, new Object[]{idRequisicaoLiberacao	});
	 
	  fields.add("idRequisicaoLiberacaocompras");
	  fields.add("idRequisicaoLiberacao");
	  fields.add("idSolicitacaoServico");
	  fields.add("nomeServico");
	  fields.add("situacaoServicos");
	  
	  return listConvertion(getBean(), resultado,fields) ;
	}
	public Collection findByIdLiberacaoAndIdHistorico(Integer idRequisicaoLiberacao) throws Exception {
		List fields = new ArrayList(); 
		
		String sql = " select ReqCompras.idRequisicaoLiberacaocompras, ReqCompras.idRequisicaoLiberacao, "+
                 "ReqCompras.idSolicitacaoServico, serv.nomeServico, ss.situacao "+
				 "from requisicaoliberacaocompras ReqCompras "+
				 "inner join ligacaolibhistcompras lig on ReqCompras.idrequisicaoliberacaocompras = lig.idrequisicaoliberacaocompras "+
				 "inner join solicitacaoservico ss on ReqCompras.idSolicitacaoServico = ss.idSolicitacaoServico "+
				 "inner join servicocontrato sc on ss.idservicocontrato = sc.idservicocontrato "+
				 "inner join servico serv on serv.idservico = sc.idservico "+
				 "where lig.idhistoricoliberacao = ? ";
		
		
		List resultado = 	execSQL(sql, new Object[]{idRequisicaoLiberacao	});
		
		fields.add("idRequisicaoLiberacaocompras");
		fields.add("idRequisicaoLiberacao");
		fields.add("idSolicitacaoServico");
		fields.add("nomeServico");
		fields.add("situacaoServicos");
		
		return listConvertion(getBean(), resultado,fields) ;
	}
	
	public Collection findByIdLiberacaoAndDataFim(Integer idRequisicaoLiberacao) throws Exception {
		StringBuilder montarSql = new StringBuilder();
		List fields = new ArrayList(); 
		
		montarSql.append("select reqCompras.idrequisicaoliberacaocompras, reqCompras.idRequisicaoLiberacao , itemReqCom.iditemrequisicaoproduto ,solserv.idsolicitacaoservico, itemReqCom.descricaoitem,  pro.nomeprojeto, centReult.codigocentroresultado ,centReult.nomecentroresultado ,itemReqCom.situacao from solicitacaoservico solserv ");
		montarSql.append(" inner join itemrequisicaoproduto itemReqCom on solserv.idsolicitacaoservico = itemReqCom.idsolicitacaoservico  ");
		montarSql.append(" inner join requisicaoproduto reqProd on solserv.idsolicitacaoservico  = reqProd.idsolicitacaoservico ");
		montarSql.append(" inner join centroresultado centReult on reqProd.idcentrocusto  = centReult.idcentroresultado  ");
		montarSql.append(" inner join projetos pro on reqProd.idprojeto = pro.idprojeto ");
		montarSql.append(" inner join requisicaoliberacaocompras reqCompras on itemReqCom.iditemrequisicaoproduto = reqCompras.iditemrequisicaoproduto ");
		montarSql.append("where reqCompras.idRequisicaoLiberacao = ? and reqCompras.dataFim is null ");
	
		List dados  = 	execSQL(montarSql.toString(), new Object[]{idRequisicaoLiberacao});
		
		fields.add("idRequisicaoLiberacaoCompras");
		fields.add("idRequisicaoLiberacao");
		fields.add("idItemRequisicaoProduto");
		fields.add("idSolicitacaoServico");
		fields.add("descricaoItem");
		fields.add("nomeProjeto");
		fields.add("codigoCentroResultado");
		fields.add("nomeCentroResultado");
		fields.add("situacaoItemRequisicao");
		return  listConvertion(getBean(), dados, fields);
	}
	
	public void deleteByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idRequisicaoLiberacao", "=", idRequisicaoLiberacao));
		
		super.deleteByCondition(condicoes);	
	}
	
	public RequisicaoLiberacaoRequisicaoComprasDTO carregaItemRequisicaoComprasByidItemRequisicaProduto(Integer idItemrRequisicaoProduto) throws Exception{
		StringBuilder montarSql = new StringBuilder();
		List fields = new ArrayList(); 
		
		montarSql.append("select itemReqCom.iditemrequisicaoproduto ,solserv.idsolicitacaoservico, itemReqCom.descricaoitem,  pro.nomeprojeto, centReult.codigocentroresultado ,centReult.nomecentroresultado ,itemReqCom.situacao from solicitacaoservico solserv ");
		montarSql.append(" inner join itemrequisicaoproduto itemReqCom on solserv.idsolicitacaoservico = itemReqCom.idsolicitacaoservico  ");
		montarSql.append(" inner join requisicaoproduto reqProd on solserv.idsolicitacaoservico  = reqProd.idsolicitacaoservico ");
		montarSql.append(" inner join centroresultado centReult on reqProd.idcentrocusto  = centReult.idcentroresultado  ");
		montarSql.append(" inner join projetos pro on reqProd.idprojeto = pro.idprojeto ");
		montarSql.append("where itemReqCom.iditemrequisicaoproduto = ? ");
	
		List dados  = 	execSQL(montarSql.toString(), new Object[]{idItemrRequisicaoProduto});
		
		fields.add("idItemRequisicaoProduto");
		fields.add("idSolicitacaoServico");
		fields.add("descricaoItem");
		fields.add("nomeProjeto");
		fields.add("codigoCentroResultado");
		fields.add("nomeCentroResultado");
		fields.add("situacaoItemRequisicao");
		
		List resultado  =  listConvertion(getBean(), dados, fields);
		
		return (RequisicaoLiberacaoRequisicaoComprasDTO) resultado.get(0);
	}
}
