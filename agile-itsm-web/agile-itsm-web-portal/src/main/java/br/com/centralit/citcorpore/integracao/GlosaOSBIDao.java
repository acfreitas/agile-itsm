package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.GlosaOSBIDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked"})
public class GlosaOSBIDao extends CrudDaoDefaultImpl {
	public GlosaOSBIDao() {
		super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idGlosaOS", "idGlosaOS", true, true, false, false));
		listFields.add(new Field("idOs", "idOs", false, false, false, false));
		listFields.add(new Field("dataCriacao", "dataCriacao", false, false, false, false));
		listFields.add(new Field("dataUltModificacao", "dataUltModificacao", false, false, false, false));
		listFields.add(new Field("descricaoGlosa", "descricaoGlosa", false, false, false, false));
		listFields.add(new Field("ocorrencias", "ocorrencias", false, false, false, false));
		listFields.add(new Field("percAplicado", "percAplicado", false, false, false, false));
		listFields.add(new Field("custoGlosa", "custoGlosa", false, false, false, false));
		listFields.add(new Field("numeroOcorrencias", "numeroOcorrencias", false, false, false, false));
		listFields.add(new Field("idConexaoBI", "idConexaoBI", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return "GlosaOS";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return GlosaOSBIDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

}
