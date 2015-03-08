package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoMudancaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoProblemaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SolicitacaoServicoMudancaDao extends CrudDaoDefaultImpl {

	public SolicitacaoServicoMudancaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
		// TODO Auto-generated constructor stub
	}

	/**
     * 
     */

	@Override
	public Collection find(IDto obj) throws PersistenceException {

		return null;
	}
	
	//corrigir, foi retirado às pressas, precisa de reavaliação "delete from  SOLICITACAOSERVICOMUDANCA where idRequisicaoMudanca = ? and idhistoricomudanca is null";
	private static final String SQL_DELETE_BY_ID_MUDANCA = 
	          "delete from  SOLICITACAOSERVICOMUDANCA where idRequisicaoMudanca = ? ";

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("IDREQUISICAOMUDANCA", "idRequisicaoMudanca", true, false, false, false));
		listFields.add(new Field("IDSOLICITACAOSERVICO", "idSolicitacaoServico", true, false, false, false));
		listFields.add(new Field("idhistoricomudanca", "idHistoricoMudanca", false, false, false, false));


		return listFields;
	}

	@Override
	public String getTableName() {
		return "SOLICITACAOSERVICOMUDANCA";
	}

	
	@Override
	public Class getBean() {
		return SolicitacaoServicoProblemaDTO.class;
	}

	@Override
	public Collection list() throws PersistenceException {
return null;
	}
	
	public void deleteByIdMudanca(Integer parm) throws PersistenceException {
		/*List condicao = new ArrayList();
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm));
		super.deleteByCondition(condicao);*/
		 super.execUpdate(SQL_DELETE_BY_ID_MUDANCA, new Object[]{parm});
	}
	
	public void deleteByIdSolictacaoServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public Collection findByIdSolictacaoServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", parm));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection listByIdHistoricoMudanca(Integer idRequisicaoMudanca) throws PersistenceException {
		List fields = new ArrayList(); 
		
		
		String sql = "select IDREQUISICAOMUDANCA,IDSOLICITACAOSERVICO,idhistoricomudanca from SOLICITACAOSERVICOMUDANCA where idhistoricomudanca = ?";
		
		List resultado = 	execSQL(sql, new Object[]{idRequisicaoMudanca});


		fields.add("idRequisicaoMudanca");
		fields.add("idSolicitacaoServico");
		fields.add("idHistoricoMudanca");
		
		return listConvertion(SolicitacaoServicoMudancaDTO.class, resultado,fields) ;
	}
	
	public Collection findByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public List<SolicitacaoServicoMudancaDTO> listByIdMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm));
		return (List<SolicitacaoServicoMudancaDTO>) super.findByCondition(condicao, ordenacao);
	}
	
	
	
	public List<SolicitacaoServicoDTO> listSolicitacaoByIdMudanca (Integer idMudanca) throws PersistenceException {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		sql.append("select sm.idsolicitacaoservico ");
		sql.append(" from solicitacaoservico ss ");
		sql.append(" inner join solicitacaoservicomudanca sm ");
		sql.append(" on ss.idsolicitacaoservico = sm.idsolicitacaoservico ");
		sql.append(" where sm.idrequisicaomudanca = ? and sm.idhistoricomudanca is null ");
	
		parametro.add(idMudanca);
		
		fields.add("idSolicitacaoServico");
   
		List dados =  this.execSQL(sql.toString(), parametro.toArray());
		 
		return (List<SolicitacaoServicoDTO>) this.listConvertion(SolicitacaoServicoDTO.class, dados, fields);
	}
	
	
}
