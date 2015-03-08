package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
@SuppressWarnings({"rawtypes","unchecked"})
public class CategoriaProblemaDAO extends CrudDaoDefaultImpl {
	public CategoriaProblemaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	
	/**
	 * @author geber.costa
	 * inclusao dos fields : idTipoFluxo, idGrupoExecutor, dataInicio, dataFim e comentado o idCategoriaProblemaPai
	 */

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idCategoriaProblema" ,"idCategoriaProblema", true, true, false, false));
		//listFields.add(new Field("idCategoriaProblemaPai" ,"idCategoriaProblemaPai", false, false, false, false));
		listFields.add(new Field("nomeCategoriaProblema" ,"nomeCategoria", false, false, false, false));
		listFields.add(new Field("idTipoFluxo" ,"idTipoFluxo", false, false, false, false));
		listFields.add(new Field("idGrupoExecutor" ,"idGrupoExecutor", false, false, false, false));
		listFields.add(new Field("dataInicio" ,"dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		listFields.add(new Field("idtemplate" ,"idTemplate", false, false, false, false));
		listFields.add(new Field("impacto", "impacto", false, false, false, false));
		listFields.add(new Field("urgencia", "urgencia", false, false, false, false));
		
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "categoriaproblema";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return CategoriaProblemaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdCategoriaProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCategoriaProblema", "=", parm)); 
		ordenacao.add(new Order("idCategoriaProblema"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCategoriaProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCategoriaProblema", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByNomeCategoria(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("nomeCategoria", "=", parm)); 
		ordenacao.add(new Order("nomeCategoria"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByNomeCategoriaProblema(CategoriaProblemaDTO categoriaProblemaDto) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("nomecategoria ", " = ", categoriaProblemaDto.getNomeCategoria())); 
		ordenacao.add(new Order("nomecategoria"));
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
		return super.findByCondition(condicao, ordenacao);
	}
	
	
	public void deleteByNomeCategoria(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("nomeCategoria", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public Collection findCategoriaProblemaSemPai() throws PersistenceException {
		List condicao = new ArrayList();
		
		condicao.add(new Condition("idCategoriaProblemaPai", "IS", null));
		
		return super.findByCondition(condicao, null);
	}
	
	public Collection findByIdCategoriaProblemaPai(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idCategoriaProblemaPai", "=", parm));
		ordenacao.add(new Order("idCategoriaProblemaPai"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public boolean consultarCategoriasAtivas(CategoriaProblemaDTO obj) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idcategoriaproblema from " + getTableName() + "  where  nomecategoriaproblema = ? and dataFim is null ";
		
		if(obj.getIdCategoriaProblema() != null){
			sql+=" and idcategoriaproblema <> "+ obj.getIdCategoriaProblema();
		}
		
		parametro.add(obj.getNomeCategoria());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Retorna uma lista de categoria problema ativas
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection getAtivos()throws PersistenceException {
		List order = new ArrayList();
		List condition = new ArrayList();
		condition.add(new Condition("dataFim", "is",null));
		//condition.add(new Condition(Condition.AND,"nomeTipoMudanca", "is", "null"));
		order.add(new Order("nomeCategoria"));
		return super.findByCondition(condition, order);
		
	}
	
	public Collection<CategoriaProblemaDTO> findByIdTemplate(Integer idTemplate) throws PersistenceException {
		Collection<CategoriaProblemaDTO> resultado = new ArrayList<CategoriaProblemaDTO>();
		if (idTemplate != null) {
			List condicao = new ArrayList();
			List ordenacao = new ArrayList();

			condicao.add(new Condition("idTemplate", " = ", idTemplate));
			resultado = super.findByCondition(condicao, ordenacao);
		}
		return resultado;
	}
	
}
