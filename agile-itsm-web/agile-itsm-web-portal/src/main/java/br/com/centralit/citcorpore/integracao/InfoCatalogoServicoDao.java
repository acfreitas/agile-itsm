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


public class InfoCatalogoServicoDao extends CrudDaoDefaultImpl {

	/**
     * @author pedro
     */
	private static final String SQL_DELETE = 
	          "DELETE FROM infocatalogoservico WHERE idcatalogoservico = ? ";
	
	private static final String SQL_FIND = 
		      "SELECT * "
			+ "FROM infocatalogoservico WHERE idcatalogoservico = ? ";
	
	public InfoCatalogoServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idInfoCatalogoServico", "idInfoCatalogoServico", true, true, false, false));
		listFields.add(new Field("idCatalogoServico", "idCatalogoServico", false, false, false, false));
		listFields.add(new Field("descInfoCatalogoServico", "descInfoCatalogoServico", false, false, false, false));
		listFields.add(new Field("nomeInfoCatalogoServico", "nomeInfoCatalogoServico", false, false, false, false));
		listFields.add(new Field("idServicoCatalogo", "idServicoCatalogo", false, false, false, false));
		listFields.add(new Field("nomeServicoContrato", "nomeServicoContrato", false, false, false, false));
		
		return listFields;
	}
	
	public String getTableName() {
		return "INFOCATALOGOSERVICO";
	}
	
	@SuppressWarnings("unchecked")
	public Collection<InfoCatalogoServicoDTO> find(IDto obj) throws PersistenceException {
		List<InfoCatalogoServicoDTO> ordem = new ArrayList<InfoCatalogoServicoDTO>();

		return super.find(obj, ordem);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<InfoCatalogoServicoDTO> list() throws PersistenceException {
		List<InfoCatalogoServicoDTO> list = new ArrayList<InfoCatalogoServicoDTO>();

		return super.list(list);
	}
	
	@SuppressWarnings("rawtypes")
	public Class getBean() {
		return InfoCatalogoServicoDTO.class;
	}
	
	public void deleteByIdInfoCatalogo(CatalogoServicoDTO catalogo)
		    throws PersistenceException {
		        super.execUpdate(SQL_DELETE, new Object[]{catalogo.getIdCatalogoServico()});
		    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Collection<InfoCatalogoServicoDTO> findByIdInfoCatalogo(InfoCatalogoServicoDTO infoCatalogoServicoDTO) throws PersistenceException {
        return super.listConvertion(getBean(), 
                super.execSQL(SQL_FIND, new Object[]{infoCatalogoServicoDTO.getIdCatalogoServico()}), 
                new ArrayList(getFields()));

	}
	@SuppressWarnings("unchecked")
	public Collection<InfoCatalogoServicoDTO> findByCatalogoServico (Integer idCatalogoServico) throws PersistenceException {
		String sql = "SELECT idInfoCatalogoServico, idServicoCatalogo, nomeServicoContrato, nomeInfoCatalogoServico, descInfoCatalogoServico FROM " + getTableName() + "  WHERE idCatalogoServico = ?";
		List<InfoCatalogoServicoDTO> dados = this.execSQL(sql, new Object[] { idCatalogoServico });
		List<String> fields = new ArrayList<String>();
		fields.add("idInfoCatalogoServico");
		fields.add("idServicoCatalogo");
		fields.add("nomeServicoContrato");
		fields.add("nomeInfoCatalogoServico");
		fields.add("descInfoCatalogoServico");
		return this.listConvertion(getBean(), dados, fields);
	}
	
	@SuppressWarnings("unchecked")
	public boolean findByContratoServico (Integer idContratoServico) throws PersistenceException {
		String sql = "select serv.idservico,nomeServico FROM servico serv inner join servicocontrato servcont on serv.idservico = servcont.idservico  WHERE IDCONTRATO = ? ORDER BY NOMESERVICO";
		List lista = this.execSQL(sql, new Object[] { idContratoServico });
		if (lista.isEmpty()) {
			return false;
		}
		else{
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	public InfoCatalogoServicoDTO findById(Integer idInfoCatalogo) throws PersistenceException {
		
		String sql = "SELECT idInfoCatalogoServico, idServicoCatalogo, nomeServicoContrato, nomeInfoCatalogoServico, descInfoCatalogoServico FROM " + getTableName() + "  WHERE idInfoCatalogoServico = ?";
		
		List<InfoCatalogoServicoDTO> dados = this.execSQL(sql, new Object[] { idInfoCatalogo });
		
		List<String> fields = new ArrayList<String>();
		
		fields.add("idInfoCatalogoServico");
		
		fields.add("idServicoCatalogo");
		
		fields.add("nomeServicoContrato");
		
		fields.add("nomeInfoCatalogoServico");
		
		fields.add("descInfoCatalogoServico");
		
		List<InfoCatalogoServicoDTO> resultado = this.listConvertion(getBean(), dados, fields);
		
		return resultado != null && resultado.size() > 0 ? resultado.get(0) : null;
	}
}
