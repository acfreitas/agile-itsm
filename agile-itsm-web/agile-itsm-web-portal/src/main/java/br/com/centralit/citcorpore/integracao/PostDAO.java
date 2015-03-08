package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PostDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PostDAO extends CrudDaoDefaultImpl  {

	public PostDAO() 
	{
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}



	
	@Override
	public Collection getFields() 
	{
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idPost", "idPost", true, true, false, false));
		listFields.add(new Field("titulo", "titulo", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("conteudo", "conteudo", false, false, false, false));
		listFields.add(new Field("imagem", "imagem", false, false, false, false));
		listFields.add(new Field("idCategoriaPost", "idCategoriaPost", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		
		return listFields;
	}

	@Override
	public String getTableName() 
	{
		return "post";
	}
	public Collection list() throws PersistenceException 
	{
		List list = new ArrayList();
		list.add(new Order("idPost"));
		return super.list(list);
	}
	
	public Collection findByCondition(Integer id) throws Exception 
	{
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		list1.add(new Condition("idPost", "=", id));
		return super.findByCondition(list1, list2);
	}
	
	public Collection listNotNull() throws Exception 
	{
		String sql = null;
		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("ORACLE")){
			sql = "SELECT idPost, titulo, descricao, conteudo, imagem, dataInicio FROM " + getTableName() + " WHERE dataFim IS NULL AND ROWNUM <= 3 ORDER BY idPost DESC";
		}else if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)){
			sql = " SELECT TOP (3) idPost, titulo, descricao, conteudo, imagem, dataInicio FROM " + getTableName() + " WHERE dataFim IS NULL  ORDER BY idPost DESC";
		}else{
			sql = "SELECT idPost, titulo, descricao, conteudo, imagem, dataInicio FROM " + getTableName() + " WHERE dataFim IS NULL ORDER BY idPost DESC LIMIT 3";	
		}
		
		List colDados = this.execSQL(sql, null);
		if (colDados != null) {
			List fields = new ArrayList();
			fields.add("idPost");
			fields.add("titulo");
			fields.add("descricao");
			fields.add("conteudo");
			fields.add("imagem");
			fields.add("dataInicio");
			return this.listConvertion(PostDTO.class, colDados, fields);
		}
		return null;
	}
	
	public Collection findByConditionByCategoria(Integer idCategoriaPost) throws Exception 
	{
		List list1 = new ArrayList();
		List list2 = new ArrayList();
		list1.add(new Condition("idPost", "=", idCategoriaPost));
		list2.add(new Order("titulo"));
		return super.findByCondition(list1, list2);
	}

	public Collection find(IDto obj) throws PersistenceException 
    {
		List ordem = new ArrayList();
		ordem.add(new Order("idPost"));
		return super.find(obj, ordem);
    }

	@Override
	public Class getBean() {
		return PostDTO.class;
	}

}
