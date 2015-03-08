package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
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

@SuppressWarnings({ "rawtypes", "unchecked" })
public class CatalogoServicoDao extends CrudDaoDefaultImpl {

	/**
     * 
     */
	public CatalogoServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idCatalogoServico", "idCatalogoServico", true, true, false, false));
		listFields.add(new Field("tituloCatalogoServico", "tituloCatalogoServico", false, false, false, false));
		listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("obs", "obs", false, false, false, false));
		listFields.add(new Field("nomeServico", "nomeServico", false, false, false, false));
		
		return listFields;
	}
	
	public String getTableName() {
		return "CATALOGOSERVICO";
	}

	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();

		return super.find(obj, ordem);
	}
	
	public Collection list() throws PersistenceException {
		List list = new ArrayList();

		return super.list(list);
	}
	
	public Collection<CatalogoServicoDTO> listAllCatalogos() throws PersistenceException {
		List parametro = new ArrayList();
		String sql = "SELECT idCatalogoServico, tituloCatalogoServico FROM " + getTableName() + "  WHERE dataFim IS NULL";
		List<InfoCatalogoServicoDTO> dados = this.execSQL(sql, parametro.toArray());
		List<String> fields = new ArrayList<String>();
		fields.add("idCatalogoServico");
		fields.add("tituloCatalogoServico");
		return this.listConvertion(getBean(), dados, fields);
	}
	
	public boolean verificaSeCatalogoExiste(CatalogoServicoDTO catalogoServicoDTO) throws PersistenceException {
		
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select idCatalogoServico from " + getTableName() + "  where  tituloCatalogoServico = ? and datafim is null ");
		parametro.add(catalogoServicoDTO.getTituloCatalogoServico());

		if (catalogoServicoDTO.getIdCatalogoServico() != null) {
			sql.append("and idCatalogoServico <> ?");
			parametro.add(catalogoServicoDTO.getIdCatalogoServico());
		}
		list = this.execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public Class getBean() {
		return CatalogoServicoDTO.class;
	}
	
	public Collection<CatalogoServicoDTO> listByIdContrato(Integer idContrato) throws PersistenceException {
		List parametro = new ArrayList();
		String sql = "SELECT idCatalogoServico, idContrato, tituloCatalogoServico FROM " + getTableName() + "  WHERE dataFim IS NULL AND  idcontrato = ?";
		parametro.add(idContrato);
		List<InfoCatalogoServicoDTO> dados = this.execSQL(sql, parametro.toArray());
		List<String> fields = new ArrayList<String>();
		fields.add("idCatalogoServico");
		fields.add("idContrato");
		fields.add("tituloCatalogoServico");
		return this.listConvertion(getBean(), dados, fields);
	}

}
