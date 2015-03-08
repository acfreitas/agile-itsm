package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ProblemaMudancaDAO extends CrudDaoDefaultImpl {
	public ProblemaMudancaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idProblemaMudanca" ,"idProblemaMudanca", true, true, false, false));
		listFields.add(new Field("idProblema" ,"idProblema", false, false, false, false));
		listFields.add(new Field("idRequisicaoMudanca" ,"idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "problemamudanca";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ProblemaMudancaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdProblemaMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProblemaMudanca", "=", parm)); 
		ordenacao.add(new Order("idProblemaMudanca"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdProblemaMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblemaMudanca", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProblema", "=", parm)); 
		ordenacao.add(new Order("idProblema"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdRequisicaoMudanca(Integer parm) throws PersistenceException {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql =  new StringBuilder();
		sql.append(" SELECT distinct pm.idproblema as idproblema, titulo, status FROM problema pro JOIN problemamudanca pm ON pro.idproblema = pm.idproblema WHERE pm.idrequisicaomudanca = ? AND pm.datafim is null ORDER BY pm.idproblema");
		parametro.add(parm);
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idProblema");
		fields.add("titulo");
		fields.add("status");
		if (list != null && !list.isEmpty()) {
			return (List<ProblemaMudancaDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection findByIdMudancaEDataFim(Integer idRequisicaoMudanca) throws PersistenceException {
		List fields = new ArrayList(); 
		
		
		String sql = "select idproblemamudanca, idproblema, idrequisicaomudanca, datafim from problemamudanca WHERE idrequisicaomudanca = ? and datafim is null";
		
	  List resultado = 	execSQL(sql, new Object[]{idRequisicaoMudanca});
	  

	  
	  fields.add("idProblemaMudanca");
	  fields.add("idProblema");
	  fields.add("idRequisicaoMudanca");
	  fields.add("dataFim");
	  
	  return listConvertion(getBean(), resultado,fields) ;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection listByIdHistoricoMudanca(Integer idHistoricoMudanca) throws PersistenceException {
		List fields = new ArrayList(); 
		
		
		String sql = "select distinct pr.idproblemamudanca, pr.idproblema, pr.idrequisicaomudanca, pr.datafim "+
				"from problemamudanca pr "+
				"inner join ligacao_mud_hist_pr ligpr on ligpr.idproblemamudanca = pr.idproblemamudanca "+
				 "WHERE ligpr.idhistoricomudanca = ?";
		
		List resultado = 	execSQL(sql, new Object[]{idHistoricoMudanca});
		
		
		
		fields.add("idProblemaMudanca");
		fields.add("idProblema");
		fields.add("idRequisicaoMudanca");
		fields.add("dataFim");
		
		return listConvertion(getBean(), resultado,fields) ;
	}
	
	
	
	public void deleteByIdRequisicaoMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ProblemaDTO> listProblemaByIdMudanca (Integer idMudanca) throws PersistenceException {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		sql.append("select pr.idproblema ");
		sql.append(" from problema pr  ");
		sql.append(" inner join problemamudanca pm ");
		sql.append(" on pr.idproblema = pm.idproblema ");
		sql.append(" where pm.idrequisicaomudanca =  ? ");
	
		parametro.add(idMudanca);
		
		fields.add("idProblema");
   
		List dados =  this.execSQL(sql.toString(), parametro.toArray());
		 
		return (List<ProblemaDTO>) this.listConvertion(ProblemaDTO.class, dados, fields);
	}
	
	public ProblemaMudancaDTO restoreByIdProblema(Integer idProblema) throws PersistenceException {
		List parametro = new ArrayList();
		parametro.add(idProblema);
		
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("idProblemaMudanca");
		listRetorno.add("idProblema");
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("dataFim");

		String sql = "  select * from problemamudanca where idproblema = ? and datafim is null ";

		List lista = this.execSQL(sql.toString(), parametro.toArray());

		if (lista != null && !lista.isEmpty()) {
			List listaResult = this.engine.listConvertion(ProblemaMudancaDTO.class, lista, listRetorno);
			return (ProblemaMudancaDTO) listaResult.get(0);
		} else {
			return null;
		}
	}
}
