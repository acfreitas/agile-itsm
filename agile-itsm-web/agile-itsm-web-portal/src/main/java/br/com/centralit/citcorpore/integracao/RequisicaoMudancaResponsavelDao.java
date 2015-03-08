package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaResponsavelDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class RequisicaoMudancaResponsavelDao extends CrudDaoDefaultImpl {
	public RequisicaoMudancaResponsavelDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idrequisicaomudancaresp" ,"idRequisicaoMudancaResp", true, true, false, false));
		listFields.add(new Field("idRequisicaoMudanca" ,"idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("papelResponsavel" ,"papelResponsavel", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		return listFields;

	}
	public String getTableName() {
		return this.getOwner() + "requisicaoMudancaResponsavel";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return RequisicaoMudancaResponsavelDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	
	public Collection findByIdMudanca(Integer idRequisicaoMudanca) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList(); 
		
		
		String sql = " select rqResponsavel.idResponsavel, rqResponsavel.idRequisicaoMudanca, responsavel.nome, responsavel.telefone, responsavel.email, cargo.nomecargo , rqResponsavel.papelresponsavel "+
				" from requisicaomudancaresponsavel rqResponsavel "+ 
				" inner join requisicaomudanca lib on rqResponsavel.idRequisicaoMudanca = lib.idrequisicaomudanca "+
				" inner join empregados responsavel on rqResponsavel.idResponsavel = responsavel.idempregado "+ 
				" inner join cargos cargo on responsavel.idcargo = cargo.idcargo" +
				" where rqResponsavel.idRequisicaoMudanca = ? ";
		
	  List resultado = 	execSQL(sql, new Object[]{idRequisicaoMudanca	});
	  
	  fields.add("idResponsavel");
	  fields.add("idRequisicaoMudanca");
	  fields.add("nomeResponsavel");
	  fields.add("telResponsavel");
	  fields.add("emailResponsavel");
	  fields.add("nomeCargo");
	  fields.add("papelResponsavel");
	  
	  return listConvertion(getBean(), resultado,fields) ;
	}
	
	public Collection listByIdHistoricoMudanca(Integer idHistoricoMudanca) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList(); 
		
		
		String sql = " select rqResponsavel.idResponsavel, rqResponsavel.idRequisicaoMudanca, responsavel.nome, responsavel.telefone, responsavel.email, cargo.nomecargo , rqResponsavel.papelresponsavel "+ 
				 "from requisicaomudancaresponsavel rqResponsavel "+ 
				 "inner join requisicaomudanca lib on rqResponsavel.idRequisicaoMudanca = lib.idrequisicaomudanca "+ 
				 "inner join empregados responsavel on rqResponsavel.idResponsavel = responsavel.idempregado "+ 
				 "inner join cargos cargo on responsavel.idcargo = cargo.idcargo "+ 
				 "inner join ligacao_mud_hist_resp ligresp on ligresp.idrequisicaomudancaresp = rqResponsavel.idrequisicaomudancaresp "+ 
				 "where ligresp.idhistoricomudanca = ? ";
		
		List resultado = 	execSQL(sql, new Object[]{idHistoricoMudanca});
		
		fields.add("idResponsavel");
		fields.add("idRequisicaoMudanca");
		fields.add("nomeResponsavel");
		fields.add("telResponsavel");
		fields.add("emailResponsavel");
		fields.add("nomeCargo");
		fields.add("papelResponsavel");
		
		return listConvertion(getBean(), resultado,fields) ;
	}
	
	public void deleteByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));
		
		super.deleteByCondition(condicoes);
	}	

	/*public ArrayList<RequisicaoLiberacaoResponsavelDTO> listByIdRequisicaoLiberacao(Integer idrequisicaoliberacao) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idLiberacao", "=", idrequisicaoliberacao));
		
		return (ArrayList<RequisicaoLiberacaoResponsavelDTO>) super.findByCondition(condicoes, null);
	}*/
	
	/*public ArrayList<RequisicaoLiberacaoResponsavelDTO> listByIdHistorico(Integer idHistoricoLiberacao) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idHistoricoLiberacao", "=", idHistoricoLiberacao));
		
		return (ArrayList<RequisicaoLiberacaoResponsavelDTO>) super.findByCondition(condicoes, null);
	}*/
	public Collection findByIdMudancaEDataFim(Integer idRequisicaoMudanca) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList(); 
		
		
		String sql = " select rqResponsavel.idRequisicaoMudancaResp, rqResponsavel.idResponsavel, rqResponsavel.idRequisicaoMudanca, responsavel.nome, responsavel.telefone, responsavel.email, cargo.nomecargo , rqResponsavel.papelresponsavel "+
				" from requisicaomudancaresponsavel rqResponsavel "+ 
				" inner join requisicaomudanca lib on rqResponsavel.idRequisicaoMudanca = lib.idrequisicaomudanca "+
				" inner join empregados responsavel on rqResponsavel.idResponsavel = responsavel.idempregado "+ 
				" inner join cargos cargo on responsavel.idcargo = cargo.idcargo" +
				" where rqResponsavel.idRequisicaoMudanca = ? and rqResponsavel.datafim is null";
		
	  List resultado = 	execSQL(sql, new Object[]{idRequisicaoMudanca});
	  
	  fields.add("idRequisicaoMudancaResp");
	  fields.add("idResponsavel");
	  fields.add("idRequisicaoMudanca");
	  fields.add("nomeResponsavel");
	  fields.add("telResponsavel");
	  fields.add("emailResponsavel");
	  fields.add("nomeCargo");
	  fields.add("papelResponsavel");
	  
	  return listConvertion(getBean(), resultado,fields) ;
	}
	
}
