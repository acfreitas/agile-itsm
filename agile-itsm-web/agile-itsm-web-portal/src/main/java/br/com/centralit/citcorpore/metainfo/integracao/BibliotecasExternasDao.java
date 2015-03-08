package br.com.centralit.citcorpore.metainfo.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.BibliotecasExternasDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class BibliotecasExternasDao extends CrudDaoDefaultImpl {
	public BibliotecasExternasDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		final List<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idBibliotecasExterna" ,"idBibliotecasExterna", true, true, false, false));
		listFields.add(new Field("nome" ,"nome", false, false, false, false));
		listFields.add(new Field("caminho" ,"caminho", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "BibliotecasExternas";
	}
	public Collection list() throws PersistenceException {
		return super.list("nome");
	}

	public Class getBean() {
		return BibliotecasExternasDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
}
