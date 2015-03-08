package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ImportConfigDTO;
import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ImportConfigDao extends CrudDaoDefaultImpl {
	
	public ImportConfigDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idImportConfig" ,"idImportConfig", true, true, false, false));
		listFields.add(new Field("idimportardados" ,"idImportarDados", false, false, false, false));
		listFields.add(new Field("nome" ,"nome", false, false, false, false));
		listFields.add(new Field("tipo" ,"tipo", false, false, false, false));
		listFields.add(new Field("idExternalConnection" ,"idExternalConnection", false, false, false, false));
		listFields.add(new Field("tabelaOrigem" ,"tabelaOrigem", false, false, false, false));
		listFields.add(new Field("tabelaDestino" ,"tabelaDestino", false, false, false, false));
		listFields.add(new Field("filtroOrigem" ,"filtroOrigem", false, false, false, false));
		listFields.add(new Field("datafim" ,"dataFim", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ImportConfig";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ImportConfigDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	public void deleteByIdImportConfig(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idImportConfig", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	
	/**
	 * Consulta o registro ImportConfig pelo idImportConfig
	 * @param importarDadosDTO
	 * @throws Exception
	 */
	public ImportConfigDTO consultarImportConfigDTO(ImportarDadosDTO importarDadosDTO) throws PersistenceException {
		
		List condicao = new ArrayList();
		condicao.add(new Condition("idImportarDados", "=", importarDadosDTO.getIdImportarDados()));
		
		List ordenacao = new ArrayList(); 
		ordenacao.add(new Order("idImportConfig"));
		
		List<ImportConfigDTO> lista = (List<ImportConfigDTO>) super.findByCondition(condicao, ordenacao);

		if(lista != null && !lista.isEmpty())
			return lista.get(0);
		else return null;
		
	}
}