package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class UfDao extends CrudDaoDefaultImpl {

	public UfDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return UfDTO.class;
	}

	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idUF", "idUf", true, false, false, true));
		listFields.add(new Field("idregioes", "idRegioes", true, false, false, true));
		listFields.add(new Field("NomeUF", "nomeUf", false, false, false, false));
		listFields.add(new Field("SiglaUF", "siglaUf", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "UFS";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}
	
	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeUf"));
		return super.list(list);
	}

	public Collection<UfDTO> listByIdRegioes(UfDTO obj) throws PersistenceException {
		List list = new ArrayList();
		List fields = new ArrayList();
		String sql = "select iduf,nomeuf from " + getTableName()
				+ " where idregioes = ? ";
		fields.add("idUf");
		fields.add("nomeUf");
		list = this.execSQL(sql, new Object[] {obj.getIdRegioes()});
		Collection<UfDTO> result = this.engine.listConvertion(getBean(), list,fields);
		return result;
	}
	
	public Collection<UfDTO> listByIdPais(UfDTO obj) throws PersistenceException {
		List list = new ArrayList();
		List fields = new ArrayList();
		String sql = "select iduf,nomeuf from " + getTableName()
				+ " where idpais = ? ";
		
		
		fields.add("idUf");
		fields.add("nomeUf");
		list = this.execSQL(sql, new Object[] {obj.getIdPais()});
		if(list!=null && !list.isEmpty()){
			Collection<UfDTO> result = this.engine.listConvertion(getBean(), list,fields);
			return result;
		}
		return null;
		
	}
	
	public UfDTO findByIdUf(Integer idUf) throws PersistenceException {
		List list = new ArrayList();
		List fields = new ArrayList();
		String sql = "select iduf,nomeuf,idPais from " + getTableName()
				+ " where idUf = ? ";
		fields.add("idUf");
		fields.add("nomeUf");
		fields.add("idPais");
		
		list = this.execSQL(sql, new Object[] {idUf});
		List<UfDTO> result = this.engine.listConvertion(getBean(), list,fields);
		if (result != null && !result.isEmpty())
			return result.get(0);
		else
			return null;
	}


}
