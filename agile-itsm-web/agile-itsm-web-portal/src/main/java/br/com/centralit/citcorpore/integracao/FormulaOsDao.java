package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FormulaOsDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FormulaOsDao extends CrudDaoDefaultImpl {


	public FormulaOsDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}


	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idFormulaOs", "idFormulaOs", true, true, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("formula", "formula", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		return listFields;
		
	}

	@Override
	public String getTableName() {
		return "formulaOs";
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}
	

	@Override
	public Class getBean() {
		return FormulaOsDTO.class;
	}


	@Override
	public Collection find(IDto obj) throws PersistenceException {
				return null;
	}


	@Override
	public Collection list() throws PersistenceException {
				return null;
	}
	
	public Collection listar(int idContrato) throws PersistenceException {
		List lista = new ArrayList();
		List listRetorno = new ArrayList();
		List param = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append(" SELECT DISTINCT formulaos.idformulaos,	formulaos.descricao,formulaos.formula FROM formulaos ");
		sql.append(" INNER JOIN contratoformulaos ON formulaos.idformulaos = contratoformulaos.idformulaos AND contratoformulaos.idcontrato = ? AND contratoformulaos.deleted = 'N' AND formulaos.situacao = 'A' ");
		param.add(idContrato);
		lista = this.execSQL(sql.toString(), param.toArray());
		listRetorno.add("idFormulaOs");
		listRetorno.add("descricao");
		listRetorno.add("formula");
		
		if (lista != null && !lista.isEmpty()) {
			Collection<FormulaOsDTO> listResultado = this.engine.listConvertion(FormulaOsDTO.class, lista, listRetorno);
			return listResultado;
		}
		return null;
		
	}
	public FormulaOsDTO buscarPorFormula(String formula) throws PersistenceException {
		List result = new ArrayList();
		List listRetorno = new ArrayList();
		List param = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT * from formulaos ");
		sql.append(" Where situacao = 'A' and formula = ? ");
		
		param.add(formula);
		
		result = this.execSQL(sql.toString(), param.toArray());
		listRetorno.add("idFormulaOs");
		listRetorno.add("descricao");
		listRetorno.add("formula");
		
		if (result != null && !result.isEmpty()) {
			List listResultado = this.engine.listConvertion(FormulaOsDTO.class, result, listRetorno);
			FormulaOsDTO dto =  (FormulaOsDTO) listResultado.get(0);
			return dto;
		}
		return null;
		
	}
	
	public boolean verificaSerExisteFormulaIgual(String formula, int idFormula) throws PersistenceException {
		List result = new ArrayList();
		List listRetorno = new ArrayList();
		List param = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append(" select * from formulaos where formulaos.formula like ? ");
		
		param.add(formula);
		if(idFormula!=0){
			sql.append(" and formulaos.idformulaos <> ? ");
			param.add(idFormula);
		}
		result = this.execSQL(sql.toString(), param.toArray());
		listRetorno.add("idFormulaOs");
		listRetorno.add("descricao");
		listRetorno.add("formula");
		
		if (result != null && !result.isEmpty()) {
			List listResultado = this.engine.listConvertion(FormulaOsDTO.class, result, listRetorno);
			FormulaOsDTO dto =  (FormulaOsDTO) listResultado.get(0);
			return true;
		}
		return false;
		
	}


}
