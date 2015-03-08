package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.ServContratoCatalogoServDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * 
 * @author pedro
 *
 */
public class ServContratoCatalogoServDao extends CrudDaoDefaultImpl {

	/**
     * 
     */
	private static final String SQL_DELETE = 
	          "DELETE FROM servcontratocatalogoserv WHERE idcatalogoservico = ? ";
	
	private static final String SQL_FIND = 
		      "SELECT * FROM servcontratocatalogoserv WHERE idcatalogoservico = ? ";
	
	public ServContratoCatalogoServDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
		listFields.add(new Field("idCatalogoServico", "idCatalogoServico", false, false, false, false));
		
		return listFields;
	}
	
	public String getTableName() {
		return "SERVCONTRATOCATALOGOSERV";
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();

		return super.find(obj, ordem);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection list() throws PersistenceException {
		List list = new ArrayList();

		return super.list(list);
	}
	
	@SuppressWarnings("rawtypes")
	public Class getBean() {
		return ServContratoCatalogoServDTO.class;
	}
	
	public void deleteByIdServContratoCatalogo(CatalogoServicoDTO catalogo)
		    throws PersistenceException {
		        super.execUpdate(SQL_DELETE, new Object[]{catalogo.getIdCatalogoServico()});
		    }
	public Collection findByIdCatalogo(ServContratoCatalogoServDTO servContratoCatalogoServDTO) throws PersistenceException {
        return super.listConvertion(getBean(), 
                super.execSQL(SQL_FIND, new Object[]{servContratoCatalogoServDTO.getIdCatalogoServico()}), 
                new ArrayList(getFields()));
}

}
