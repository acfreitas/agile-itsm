package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes","unchecked" })
public class TipoMudancaDAO extends CrudDaoDefaultImpl{
	
	public TipoMudancaDAO(){
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@Override
	public Collection find(IDto obj) throws PersistenceException {
				return null;
	}

	
	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idTipoMudanca", "idTipoMudanca", true, true, false, false));
		listFields.add(new Field("nomeTipoMudanca", "nomeTipoMudanca", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("idTipoFluxo", "idTipoFluxo", false, false, false, false));
		listFields.add(new Field("idModeloEmailCriacao", "idModeloEmailCriacao", false, false, false, false));
		listFields.add(new Field("idModeloEmailFinalizacao", "idModeloEmailFinalizacao", false, false, false, false));
		listFields.add(new Field("idModeloEmailAcoes", "idModeloEmailAcoes", false, false, false, false));
		listFields.add(new Field("idGrupoExecutor", "idGrupoExecutor", false, false, false, false));
		listFields.add(new Field("idCalendario", "idCalendario", false, false, false, false));
		listFields.add(new Field("impacto", "impacto", false, false, false, false));
		listFields.add(new Field("urgencia", "urgencia", false, false, false, false));
		listFields.add(new Field("exigeAprovacao", "exigeAprovacao", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return this.getOwner() + "tipomudanca";
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeTipoMudanca"));
		return super.list(list);
	}
	
	@Override
	public Class getBean() {
		
		return TipoMudancaDTO.class;
	}
	
	public Collection findByIdTipoMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idTipoMudanca", "=", parm));
		ordenacao.add(new Order("idTipoMudanca"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public void deleteByIdTipoMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idTipoMudanca", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public Collection findByNomeTipoMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("nomeTipoMudanca", "=", parm));
		ordenacao.add(new Order("nomeTipoMudanca"));
		condicao.add(new Condition(Condition.AND, "dataFim","is",null));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public Collection encontrarPorNomeTipoMudanca(TipoMudancaDTO tipoMudancaDTO) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("nomeTipoMudanca", "=", tipoMudancaDTO.getNomeTipoMudanca()));
		ordenacao.add(new Order("nomeTipoMudanca"));
		condicao.add(new Condition(Condition.AND, "dataFim","is",null));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByNomeTipoMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("nomeTipoMudanca", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	/**
	 * Retorna lista de status de tipo mudança.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean verificarTipoMudancaAtivos(TipoMudancaDTO obj) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idtipomudanca From " + getTableName() + "  where  nometipomudanca = ?   and dataFim is null ";
		
		if(obj.getIdTipoMudanca() != null){
			sql+=" and idtipomudanca <> "+ obj.getIdTipoMudanca();
		}
		
		parametro.add(obj.getNomeTipoMudanca());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public Collection getAtivos()throws PersistenceException {
		List order = new ArrayList();
		List condition = new ArrayList();
		condition.add(new Condition("dataFim", "is",null));
		//condition.add(new Condition(Condition.AND,"nomeTipoMudanca", "is", "null"));
		order.add(new Order("nomeTipoMudanca"));
		return super.findByCondition(condition, order);
	
	}

	/**
	 * @author euler.ramos
	 * @param idCalendario
	 * @return
	 * @throws Exception
	 */	
	public ArrayList<TipoMudancaDTO> findByIdCalendario(Integer idCalendario) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCalendario", "=", idCalendario)); 
		ordenacao.add(new Order("idTipoMudanca"));
		ArrayList<TipoMudancaDTO> result = (ArrayList<TipoMudancaDTO>) super.findByCondition(condicao, ordenacao);
		return (result == null ? new ArrayList<TipoMudancaDTO>() : result);
	}
}

