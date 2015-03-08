package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LigacaoRequisicaoLiberacaoHistoricoResponsavelDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoResponsavelDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class LigacaoRequisicaoLiberacaoResponsavelDao extends CrudDaoDefaultImpl {


	public LigacaoRequisicaoLiberacaoResponsavelDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idLigacao_lib_hist_resp", "idLigacao_lib_hist_resp", true, true, false, false));
		listFields.add(new Field("idRequisicaoLiberacao" ,"idRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("idHistoricoLiberacao", "idHistoricoLiberacao", false, false, false, false));
		listFields.add(new Field("idRequisicaoLiberacaoResp", "idRequisicaoLiberacaoResp", false, false, false, false));
		return listFields;
	}
	
	public String getTableName() {
		return this.getOwner() + "LIGACAO_LIB_HIST_RESP";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return LigacaoRequisicaoLiberacaoHistoricoResponsavelDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	public void deleteByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idRequisicaoLiberacao", "=", idRequisicaoLiberacao));
		
		super.deleteByCondition(condicoes);
	}
	
	public Collection<RequisicaoLiberacaoResponsavelDTO> findByIdLiberacao(Integer idRequisicaoLiberacao) throws Exception {
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
	  
	  return listConvertion(RequisicaoLiberacaoResponsavelDTO.class, resultado,fields) ;
	}

}
