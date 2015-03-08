package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CausaIncidenteDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CausaIncidenteDao extends CrudDaoDefaultImpl {
	public CausaIncidenteDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idCausaIncidente" ,"idCausaIncidente", true, true, false, false));
		listFields.add(new Field("idCausaIncidentePai" ,"idCausaIncidentePai", false, false, false, false));
		listFields.add(new Field("descricaoCausa" ,"descricaoCausa", false, false, false, false));
		listFields.add(new Field("dataInicio" ,"dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "CausaIncidente";
	}
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idCausaIncidente"));
		return super.list(ordenacao);
	}

	public Class getBean() {
		return CausaIncidenteDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findSemPai() throws PersistenceException {
		
	    String sql = "SELECT idCausaIncidente, idCausaIncidentePai, descricaoCausa, dataInicio, dataFim FROM causaincidente WHERE idCausaIncidentePai IS NULL AND dataFim IS NULL AND ";
	    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) 
			sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N') ";
	    else if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER))
			sql += "(deleted IS NULL OR deleted = 'N') ";
	    else sql += "(deleted IS NULL OR deleted = 'N') ";
		sql += "ORDER BY descricaoCausa ";
		
	    List colDados = this.execSQL(sql, null);
	    if (colDados != null){
		List fields = new ArrayList();
		fields.add("idCausaIncidente");
		fields.add("idCausaIncidentePai");
		fields.add("descricaoCausa");
		fields.add("dataInicio");
		fields.add("dataFim");
		return this.listConvertion(CausaIncidenteDTO.class, colDados, fields);
	    }
	    return null;
	}
	public Collection findByIdPai(Integer idCausaIncidentePaiParm) throws PersistenceException {
	    String sql = "SELECT idCausaIncidente, idCausaIncidentePai, descricaoCausa, dataInicio, dataFim FROM causaincidente " +
	    		"WHERE idCausaIncidentePai = ? AND dataFim IS NULL AND ";
	    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) 
			sql += "(UPPER(deleted) IS NULL OR UPPER(deleted) = 'N') ";
		 else if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER))
			sql += "(deleted IS NULL OR deleted = 'N') ";
		 else sql += "(deleted IS NULL OR deleted = 'N') ";
		sql += "ORDER BY descricaoCausa ";
	    
	    List colDados = this.execSQL(sql, new Object[] {idCausaIncidentePaiParm});
	    if (colDados != null){
		List fields = new ArrayList();
		fields.add("idCausaIncidente");
		fields.add("idCausaIncidentePai");
		fields.add("descricaoCausa");
		fields.add("dataInicio");
		fields.add("dataFim");
		return this.listConvertion(CausaIncidenteDTO.class, colDados, fields);
	    }
	    return null;
	}	
	public Collection findByIdCausaIncidentePai(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCausaIncidentePai", "=", parm)); 
		ordenacao.add(new Order("descricaoCausa"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCausaIncidentePai(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCausaIncidentePai", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	/**
	 * Retorna uma lista de causa de acordo com o parametro passado
	 * @param descricaoCausa
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection listaCausaByDescricaoCausa(String descricaoCausa) throws PersistenceException {

		List fields = new ArrayList();
		if(descricaoCausa == null)
			descricaoCausa = "";
		
		String text = descricaoCausa;
		text = text.replaceAll("´`^''-+=", "");
		descricaoCausa = text;		
		descricaoCausa = "%" + descricaoCausa + "%";
		List parametros = new ArrayList();

		parametros.add(descricaoCausa);
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT idcausaincidente , descricaoCausa");
		sql.append( " FROM causaincidente "); 
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)){
			sql.append("where  descricaoCausa ilike ? ");
		}else{
			sql.append( "where  UPPER(descricaoCausa) like UPPER(?) ");
		}

			sql.append(" ORDER BY descricaoCausa");

		List listaR = this.execSQL(sql.toString(), parametros.toArray());
		
		fields.add("idCausaIncidente");
		fields.add("descricaoCausa");
		
		
		return this.listConvertion(CausaIncidenteDTO.class, listaR, fields);
	}
	
	public Collection listaCausasAtivas() throws PersistenceException {
		List fields = new ArrayList();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT idcausaincidente, descricaoCausa FROM causaincidente WHERE datafim is NULL AND (deleted is NULL OR deleted = 'n') "); 

		List listaR = this.execSQL(sql.toString(), null);
		
		fields.add("idCausaIncidente");
		fields.add("descricaoCausa");
		
		
		return this.listConvertion(CausaIncidenteDTO.class, listaR, fields);
	}
}
