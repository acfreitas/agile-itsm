package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ModuloSistemaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author Pedro
 *
 */
public class ModuloSistemaDao extends CrudDaoDefaultImpl {

    public ModuloSistemaDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @SuppressWarnings("rawtypes")
    public Class getBean() {
	return ModuloSistemaDTO.class;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("idmodulosistema", "idModuloSistema", true, true, false, false));
	listFields.add(new Field("nomemodulosistema", "nomeModuloSistema", false, false, false, false));

	return listFields;
    }

    public String getTableName() {
	return "MODULOSISTEMA";
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection find(IDto obj) throws PersistenceException {
	List ordem = new ArrayList();
	ordem.add(new Order("nomeModuloSistema"));
	return super.find(obj, ordem);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Collection list() throws PersistenceException {
	List list = new ArrayList();
	return super.list(list);
    }

}
