package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.GaleriaImagensDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class GaleriaImagensDao extends CrudDaoDefaultImpl {

	public GaleriaImagensDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}
	
	public Collection findByCategoria(Integer idCategoria) throws PersistenceException {
		List lstCondicao = new ArrayList();
		List lstOrder = new ArrayList();
		
		lstCondicao.add(new Condition("idCategoriaGaleriaImagem", "=", idCategoria));
		
		lstOrder.add(new Order("descricaoImagem"));
		
		return super.findByCondition(lstCondicao, lstOrder);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idImagem", "idImagem", true, true, false, false));
		listFields.add(new Field("idCategoriaGaleriaImagem", "idCategoriaGaleriaImagem", false, false, false, false));
		listFields.add(new Field("nomeImagem", "nomeImagem", false, false, false, false));
		listFields.add(new Field("descricaoImagem", "descricaoImagem", false, false, false, false));
		listFields.add(new Field("detalhamento", "detalhamento", false, false, false, false));
		listFields.add(new Field("extensao", "extensao", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "GaleriaImagens";
	}

	public Collection list() throws PersistenceException {
		return null;
	}
	
	public Collection listOrderByCategoria() throws PersistenceException {
		List lstOrder = new ArrayList();
		Collection col = getFields();
		List lstCamposRetorno = new ArrayList();
		String sql = "";
		if (col != null){
			for(Iterator it = col.iterator(); it.hasNext();){
				Field field = (Field)it.next();
				if (!sql.equalsIgnoreCase("")){
					sql += ","; 
				}
				sql += "A." + field.getFieldDB();
				
				lstCamposRetorno.add(field.getFieldClass());
			}
		}
		sql = "SELECT " + sql + ", B.nomeCategoria FROM " + this.getTableName() + " A INNER JOIN CategoriaGaleriaImagem B " +
				"ON B.idCategoriaGaleriaImagem = A.idCategoriaGaleriaImagem ";
		sql += " ORDER BY B.nomeCategoria, A.descricaoImagem";
		
		lstCamposRetorno.add("nomeCategoria");
		
		List lista = this.execSQL(sql, null);
        List result = this.engine.listConvertion(getBean(), lista, lstCamposRetorno);
        return result;		
	}	

	public Class getBean() {
		return GaleriaImagensDTO.class;
	}

}
