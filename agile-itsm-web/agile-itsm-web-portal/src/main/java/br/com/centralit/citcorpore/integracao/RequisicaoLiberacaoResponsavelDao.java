package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoResponsavelDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequisicaoLiberacaoResponsavelDao extends CrudDaoDefaultImpl {
	public RequisicaoLiberacaoResponsavelDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idrequisicaoliberacaoresp" ,"idRequisicaoLiberacaoResp", true, true, false, false));
		listFields.add(new Field("idRequisicaoLiberacao" ,"idRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("papelResponsavel" ,"papelResponsavel", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		return listFields;

	}
	public String getTableName() {
		return this.getOwner() + "requisicaoliberacaoResponsavel";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return RequisicaoLiberacaoResponsavelDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	
	public Collection findByIdLiberacao(Integer idRequisicaoLiberacao) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList(); 
		
		
		String sql = " select rqResponsavel.idResponsavel, rqResponsavel.idRequisicaoLiberacao, responsavel.nome, responsavel.telefone, responsavel.email, cargo.nomecargo , rqResponsavel.papelresponsavel "+
				" from requisicaoliberacaoresponsavel rqResponsavel "+ 
				" inner join liberacao lib on rqResponsavel.idRequisicaoLiberacao = lib.idLiberacao "+
				" inner join empregados responsavel on rqResponsavel.idResponsavel = responsavel.idempregado "+ 
				" inner join cargos cargo on responsavel.idcargo = cargo.idcargo" +
				" where rqResponsavel.idRequisicaoLiberacao = ? ";
		
	  List resultado = 	execSQL(sql, new Object[]{idRequisicaoLiberacao	});
	  
	  fields.add("idResponsavel");
	  fields.add("idRequisicaoLiberacao");
	  fields.add("nomeResponsavel");
	  fields.add("telResponsavel");
	  fields.add("emailResponsavel");
	  fields.add("nomeCargo");
	  fields.add("papelResponsavel");
	  
	  return listConvertion(getBean(), resultado,fields) ;
	}
	public Collection findByIdLiberacaoEDataFim(Integer idRequisicaoLiberacao) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList(); 
		
		
		String sql = " select rqResponsavel.idRequisicaoLiberacaoResp, rqResponsavel.idResponsavel, rqResponsavel.idRequisicaoLiberacao, responsavel.nome, responsavel.telefone, responsavel.email, cargo.nomecargo , rqResponsavel.papelresponsavel "+
				" from requisicaoliberacaoresponsavel rqResponsavel "+ 
				" inner join liberacao lib on rqResponsavel.idRequisicaoLiberacao = lib.idLiberacao "+
				" inner join empregados responsavel on rqResponsavel.idResponsavel = responsavel.idempregado "+ 
				" inner join cargos cargo on responsavel.idcargo = cargo.idcargo" +
				" where rqResponsavel.idRequisicaoLiberacao = ? and rqResponsavel.datafim is null";
		
		List resultado = 	execSQL(sql, new Object[]{idRequisicaoLiberacao	});
		
		fields.add("idRequisicaoLiberacaoResp");
		fields.add("idResponsavel");
		fields.add("idRequisicaoLiberacao");
		fields.add("nomeResponsavel");
		fields.add("telResponsavel");
		fields.add("emailResponsavel");
		fields.add("nomeCargo");
		fields.add("papelResponsavel");
		
		return listConvertion(getBean(), resultado,fields) ;
	}
	
	public Collection listByIdHistoricoLiberacao(Integer idHistoricoLiberacao) throws Exception {
		List fields = new ArrayList(); 
		
		
		String sql = " select rqResponsavel.idRequisicaoLiberacaoResp, rqResponsavel.idResponsavel, "+
                	 " rqResponsavel.idRequisicaoLiberacao, responsavel.nome, responsavel.telefone, responsavel.email, cargo.nomecargo , rqResponsavel.papelresponsavel "+
                	 " from requisicaoliberacaoresponsavel rqResponsavel "+
                	 " inner join ligacao_lib_hist_resp lig on rqResponsavel.idrequisicaoliberacaoresp = lig.idrequisicaoliberacaoresp "+
                	 " inner join empregados responsavel on rqResponsavel.idResponsavel = responsavel.idempregado "+
                	 " inner join cargos cargo on responsavel.idcargo = cargo.idcargo "+
                	 " where lig.idhistoricoliberacao = ?";
		
		List resultado = 	execSQL(sql, new Object[]{idHistoricoLiberacao	});
		
		fields.add("idRequisicaoLiberacaoResp");
		fields.add("idResponsavel");
		fields.add("idRequisicaoLiberacao");
		fields.add("nomeResponsavel");
		fields.add("telResponsavel");
		fields.add("emailResponsavel");
		fields.add("nomeCargo");
		fields.add("papelResponsavel");
		
		return listConvertion(getBean(), resultado,fields) ;
	}
	
	public void deleteByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idRequisicaoLiberacao", "=", idRequisicaoLiberacao));
		
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
}
