package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MarcaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MarcaDao extends CrudDaoDefaultImpl {
	public MarcaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idMarca" ,"idMarca", true, true, false, false));
		listFields.add(new Field("idFabricante" ,"idFabricante", false, false, false, false));
		listFields.add(new Field("nomeMarca" ,"nomeMarca", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Marca";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return MarcaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdFabricante(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idFabricante", "=", parm)); 
		ordenacao.add(new Order("nomeMarca"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdFabricante(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idFabricante", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	/*public Collection consultarMarcasAtivas(MarcaDTO marca) throws PersistenceException {
		
		
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("nomeMarca", "=", marca.getNomeMarca()));
		if(marca.getIdMarca()!=null){
			condicao.add(new Condition("idMarca", "<>", marca.getIdMarca()));
		}
		ordenacao.add(new Order("nomeMarca"));
		return super.findByCondition(condicao, ordenacao);
	}*/
	
	/**
	 *Retorna true ou falso caso nomeMarca ja exista no banco de dados
	 * 
	 * @param marcaDto
	 * @return boolean
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean consultarMarcas(MarcaDTO marcaDto) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql  = new StringBuilder();
		 sql.append("select idmarca From " + getTableName() + "  where  nomeMarca = ?    ");
		
		parametro.add(marcaDto.getNomeMarca());
		
		if (marcaDto.getIdMarca() != null) {
			sql.append("and idmarca <> ?");
			parametro.add(marcaDto.getIdMarca());
		}
		
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
