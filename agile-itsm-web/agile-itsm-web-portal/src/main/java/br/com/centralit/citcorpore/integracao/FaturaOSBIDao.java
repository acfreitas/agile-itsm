package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.FaturaOSDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked"})
public class FaturaOSBIDao extends CrudDaoDefaultImpl {
	public FaturaOSBIDao() {
		super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idFatura" ,"idFatura", true, false, false, false));
		listFields.add(new Field("idOs" ,"idOs", true, false, false, false));
		listFields.add(new Field("idConexaoBI" ,"idConexaoBI", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return "FaturaOS";
	}
	public Collection list() throws PersistenceException {
		return null;
	}
	public Class getBean() {
		return FaturaOSDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

}
