package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.centralit.citcorpore.bean.ImportConfigCamposDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"unchecked", "rawtypes"})
public class ImportConfigCamposDao extends CrudDaoDefaultImpl {

	public ImportConfigCamposDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idImportConfigCampo" ,"idImportConfigCampo", true, true, false, false));
		listFields.add(new Field("idImportConfig" ,"idImportConfig", false, false, false, false));
		listFields.add(new Field("idimportardados" ,"idImportarDados", false, false, false, false));
		listFields.add(new Field("origem" ,"origem", false, false, false, false));
		listFields.add(new Field("destino" ,"destino", false, false, false, false));
		listFields.add(new Field("script" ,"script", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ImportConfigCampos";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ImportConfigCamposDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdImportConfig(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idImportConfig", "=", parm)); 
		ordenacao.add(new Order("idImportConfigCampo"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdImportConfig(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idImportConfig", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public Collection findByIdImportarDados(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idImportarDados", "=", parm)); 
		ordenacao.add(new Order("idImportConfigCampo"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	
	/**
	 * Deleta o registro de forma logica, seta o campo dataFim igual a data de execução
	 * 
	 * @param parm
	 * @throws Exception
	 */
	public void deletarLogicamentePorIdImportarDados(Integer parm) throws PersistenceException {
		
		List parametros = new ArrayList();

		parametros.add(new Date());
		parametros.add(parm);

		String sql = "UPDATE importconfig SET datafim = ? WHERE idimportardados = ?";
		
		super.execUpdate(sql, parametros.toArray());
		
	}
}