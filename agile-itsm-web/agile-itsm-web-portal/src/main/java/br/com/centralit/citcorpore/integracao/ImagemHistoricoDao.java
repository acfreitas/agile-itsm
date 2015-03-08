package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ImagemHistoricoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ImagemHistoricoDao extends CrudDaoDefaultImpl {
	
	private static final String SQL_FIND_BY_NAME_FILE = 
	      "SELECT idImagem, data, nomeArquivo, observacao, idContrato, "
	    + "idProfissional, idEmpresa, aba FROM ImagemHistorico "
	    + "WHERE nomeArquivo = ?";
	
	public ImagemHistoricoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}	
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idImagem", "idImagem", true, true, false, false));
		listFields.add(new Field("data", "data", false, false, false, false));
		listFields.add(new Field("nomeArquivo", "nomeArquivo", false, false, false, false));
		listFields.add(new Field("observacao", "observacao", false, false, false, false));
		listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
		listFields.add(new Field("idProfissional", "idProfissional", false, false, false, false));
		listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
		listFields.add(new Field("aba", "aba", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "ImagemHistorico";
	}
	public static String getTableNameAssDigital() {
		return "ImagemHistorico";
	}
	
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return br.com.centralit.citcorpore.bean.ImagemHistoricoDTO.class;
	}

	public Collection listByIdContrato(Integer idContrato) throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("data"));		
		ImagemHistoricoDTO obj = new ImagemHistoricoDTO();
		obj.setIdContrato(idContrato);
		return super.find(obj, list);	
	}	
	public Collection listByIdContratoAndAba(Integer idContrato, String aba) throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("data"));		
		ImagemHistoricoDTO obj = new ImagemHistoricoDTO();
		obj.setIdContrato(idContrato);
		obj.setAba(aba);
		return super.find(obj, list);	
	}	
	
	public ImagemHistoricoDTO findByNomeArquivo(final ImagemHistoricoDTO filter) 
            throws Exception
    {
	    List ret = listConvertion(getBean(), 
	            execSQL(SQL_FIND_BY_NAME_FILE, 
	                    new Object[]{filter.getNomeArquivo()}), 
	            new ArrayList(getFields()));
	    if(ret != null && !ret.isEmpty())
	        return (ImagemHistoricoDTO)ret.get(0);
	    return null;
    }
}