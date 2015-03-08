package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ControleImportarDadosDTO;
import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ControleImportarDadosDao extends CrudDaoDefaultImpl {
	
	public ControleImportarDadosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idcontroleimportardados" ,"idControleImportarDados", true, true, false, false));
		listFields.add(new Field("idimportardados" ,"idImportarDados", false, false, false, false));
		listFields.add(new Field("dataexecucao" ,"dataExecucao", false, false, false, false));
		
		return listFields;

	}
	
	public String getTableName() {
		return this.getOwner() + "ControleImportarDados";
	}
	
	public Collection list() throws PersistenceException {
		return super.list("nome");
	}
	
	public Class getBean() {
		return ControleImportarDadosDTO.class;
	}
	
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	/**
	 * Consulta o ultimo elemento inserido na tabela pelo parametro
	 * 
	 * @param importar
	 * @return
	 * @throws Exception
	 */
	public ControleImportarDadosDTO consultarControleImportarDados(ImportarDadosDTO importar) throws PersistenceException {
		
		String sql = "select * from controleimportardados where idimportardados = ? order by idcontroleimportardados desc limit 1";
		
		List lstParms = new ArrayList();
		lstParms.add(importar.getIdImportarDados());
		
		List lstDados = this.execSQL(sql, lstParms.toArray());
		
		List lst = this.listConvertion(getBean(), lstDados, this.getListNamesFieldClass());
		
		if (lst == null || lst.size() == 0){
			return null;
		}
		
		return (ControleImportarDadosDTO) lst.get(0);
		
	}
	
}