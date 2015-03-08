package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LiberacaoMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes","unchecked"})
public class LiberacaoMudancaDao extends CrudDaoDefaultImpl {


	public LiberacaoMudancaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	private static final String SQL_DELETE = 
	          "delete from  liberacaomudanca where idliberacao = ? and idhistoricoliberacao is null";
	private static final String SQL_DELETE_BY_ID_MUDANCA = 
	          "delete from  liberacaomudanca where idRequisicaoMudanca = ? and idhistoricomudanca is null ";
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idLiberacao" ,"idLiberacao", false, false, false, false));
		listFields.add(new Field("idRequisicaoMudanca" ,"idRequisicaoMudanca", true, false, false, false));
		listFields.add(new Field("idhistoricoliberacao" ,"idHistoricoLiberacao", true, false, false, false));
		//listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		listFields.add(new Field("status" ,"status", false, false, false, false));
		listFields.add(new Field("situacaoliberacao" ,"situacaoLiberacao", false, false, false, false));
		listFields.add(new Field("idHistoricoMudanca" ,"idHistoricoMudanca", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "liberacaomudanca";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return LiberacaoMudancaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdLiberacao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		String param2 = "is null";
		condicao.add(new Condition("idLiberacao", "=", parm)); 
		condicao.add(new Condition("idHistoricoLiberacao", "is", null));
		ordenacao.add(new Order("idRequisicaoMudanca"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdLiberacao(Integer parm) throws Exception {
		/*List condicao = new ArrayList();
		condicao.add(new Condition("idLiberacao", "=", parm));
		super.deleteByCondition(condicao);*/
		 super.execUpdate(SQL_DELETE, new Object[]{parm});
	}
	public ArrayList<LiberacaoMudancaDTO> listByIdRequisicaoLiberacao(Integer idrequisicaoliberacao) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idLiberacao", "=", idrequisicaoliberacao));
		
		return (ArrayList<LiberacaoMudancaDTO>) super.findByCondition(condicoes, null);
	}
	public ArrayList<LiberacaoMudancaDTO> listByIdRequisicaMudanca(Integer idrequisicaoMudanca) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idRequisicaoMudanca", "=", idrequisicaoMudanca));
		
		return (ArrayList<LiberacaoMudancaDTO>) super.findByCondition(condicoes, null);
	}
	
	public ArrayList<LiberacaoMudancaDTO> listByIdHistoricoLiberacao(Integer idHistoricoLiberacao) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idHistoricoLiberacao", "=", idHistoricoLiberacao));
		
		return (ArrayList<LiberacaoMudancaDTO>) super.findByCondition(condicoes, null);
	}
	public ArrayList<LiberacaoMudancaDTO> listByIdHistoricoMudanca(Integer idHistoricoMudanca) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		
		condicoes.add(new Condition("idHistoricoMudanca", "=", idHistoricoMudanca));
		
		return (ArrayList<LiberacaoMudancaDTO>) super.findByCondition(condicoes, null);
	}
	
	//geber.costa 
	public Collection<LiberacaoMudancaDTO> listAll() throws ServiceException, Exception {
		
		StringBuilder sb = new StringBuilder();
		List listRetorno = new ArrayList();

		sb.append("SELECT distinct status ");
		sb.append("FROM liberacaomudanca ");
		sb.append("WHERE status is not null ");
		
		List lista = this.execSQL(sb.toString(), null);
		listRetorno.add("status");
		
		List listaSolicitacoes = this.engine.listConvertion(getBean(), lista, listRetorno);

		return listaSolicitacoes;
		
	}
	
	public Collection findByIdRequisicaoMudanca(Integer idLiberacao , Integer idRequisicaoMudanca) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql =  new StringBuilder();
		sql.append(" SELECT libmud.idliberacao,libmud.idrequisicaomudanca, lib.titulo, lib.descricao, libmud.status, libmud.situacaoliberacao  ");
		sql.append(" FROM liberacaomudanca libmud  ");
		sql.append(" inner join liberacao lib on libmud.idliberacao = lib.idliberacao ");
		sql.append(" inner join requisicaomudanca mud on libmud.idrequisicaomudanca = mud.idrequisicaomudanca  where idhistoricoliberacao is null and idhistoricomudanca is null and ");
		
		if(idRequisicaoMudanca != null){
			if(idLiberacao != null){
				sql.append(" libmud.idrequisicaomudanca = ? AND ");
			}else{
				sql.append(" libmud.idrequisicaomudanca = ? ");
			}
			parametro.add(idRequisicaoMudanca);
		}
		
		if(idLiberacao != null){
			sql.append(" libmud.idliberacao  = ? ");
			parametro.add(idLiberacao);
		}
		
		
		
		
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idLiberacao");
		fields.add("idRequisicaoMudanca");
		fields.add("titulo");
		fields.add("descricao");
		fields.add("status");
		fields.add("situacaoLiberacao");
		if (list != null && !list.isEmpty()) {
			return (List<LiberacaoMudancaDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}		
	}
	public Collection listByIdHistoricoMudanca2(Integer idHistoricoMudanca) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql =  new StringBuilder();
		sql.append("SELECT libmud.idliberacao,libmud.idrequisicaomudanca, lib.titulo, lib.descricao, libmud.status, libmud.situacaoliberacao "+
		"FROM liberacaomudanca libmud  "+
		"inner join liberacao lib on libmud.idliberacao = lib.idliberacao "+
		"inner join requisicaomudanca mud on libmud.idrequisicaomudanca = mud.idrequisicaomudanca "+
		"where idhistoricomudanca = ?   ");
		

			parametro.add(idHistoricoMudanca);
		
		
		
		
		
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idLiberacao");
		fields.add("idRequisicaoMudanca");
		fields.add("titulo");
		fields.add("descricao");
		fields.add("status");
		fields.add("situacaoLiberacao");
		if (list != null && !list.isEmpty()) {
			return (List<LiberacaoMudancaDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}		
	}
//	
//	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception {
//		List parametro = new ArrayList();
//		List fields = new ArrayList();
//		List list = new ArrayList();
//		StringBuilder sql =  new StringBuilder();
//		
//		sql.append(" SELECT idRequisicaoMudanca,idLiberacao,descricao,status,situacaoLiberacao FROM "+getTableName());
//		sql.append(" WHERE idHistoricoLiberacao is null ");
//		sql.append(" AND idliberacao =  ?");
//		parametro.add(parm);
//		list = this.execSQL(sql.toString(), parametro.toArray());
//		fields.add("idRequisicaoMudanca");
//		fields.add("idLiberacao");
//		fields.add("descricao");
//		fields.add("status");
//		fields.add("situacaoLiberacao");
//		if (list != null && !list.isEmpty()) {
//			return (List<LiberacaoMudancaDTO>) this.listConvertion(getBean(), list, fields);
//		} else {
//			return null;
//		}		
//	}
	
	public Collection findByIdLiberacao2(Integer parm) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql =  new StringBuilder();
		sql.append(" select libm.idliberacao, l.titulo, l.descricao, libm.status, libm.situacaoliberacao, libm.idrequisicaomudanca  from liberacaomudanca libm  ");
		sql.append(" inner join liberacao l on  l.idliberacao = libm.idliberacao ");
		sql.append(" where libm.idliberacao = ? and idhistoricoliberacao is null");
		parametro.add(parm);
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idLiberacao");
		fields.add("titulo");
		fields.add("descricao");
		fields.add("status");
		fields.add("situacaoLiberacao");
		fields.add("idRequisicaoMudanca");
		if (list != null && !list.isEmpty()) {
			return (List<LiberacaoMudancaDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}		
	}
	
	public void deleteByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception{
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));
		super.deleteByCondition(condicoes);
	}
	
	public void deleteByIdMudanca(Integer parm) throws Exception {
		/*List condicao = new ArrayList();
		condicao.add(new Condition("idLiberacao", "=", parm));
		super.deleteByCondition(condicao);*/
		 super.execUpdate(SQL_DELETE_BY_ID_MUDANCA, new Object[]{parm});
	}
	
	
}
