package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class JornadaTrabalhoDao extends CrudDaoDefaultImpl {
	public JornadaTrabalhoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idJornada" ,"idJornada", true, true, false, false));
		listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		listFields.add(new Field("inicio1" ,"inicio1", false, false, false, false));
		listFields.add(new Field("termino1" ,"termino1", false, false, false, false));
		listFields.add(new Field("inicio2" ,"inicio2", false, false, false, false));
		listFields.add(new Field("termino2" ,"termino2", false, false, false, false));
		listFields.add(new Field("inicio3" ,"inicio3", false, false, false, false));
		listFields.add(new Field("termino3" ,"termino3", false, false, false, false));
		listFields.add(new Field("inicio4" ,"inicio4", false, false, false, false));
		listFields.add(new Field("termino4" ,"termino4", false, false, false, false));
		listFields.add(new Field("inicio5" ,"inicio5", false, false, false, false));
		listFields.add(new Field("termino5" ,"termino5", false, false, false, false));
		listFields.add(new Field("cargaHoraria" ,"cargaHoraria", false, false, false, false));
		listFields.add(new Field("datainicio", "datainicio", false, false, false, false));
		listFields.add(new Field("datafim", "datafim", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "JornadaTrabalho";
	}

	public Class getBean() {
		return JornadaTrabalhoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection list() throws PersistenceException {
	    List list = new ArrayList();
	    list.add(new Order("descricao"));
	    return super.list(list);
	}
	
	public Collection listarJornadasAtivas() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("datafim", "is", null));
		ordenacao.add(new Order("descricao"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}
	
	/**
	 * Retorna se existe jornada já cadastrada
	 * 
	 * @author rodrigo.oliveira
	 * @param jornadaTrabalhoDTO
	 * @return Se caso existe jornada já cadastrada retorna true
	 * @throws Exception
	 */
	public boolean verificaJornadaExistente(JornadaTrabalhoDTO jornadaTrabalho) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idjornada from " + getTableName() + " where descricao = ? and dataFim is null ";
		parametro.add(jornadaTrabalho.getDescricao());
		
		if(jornadaTrabalho.getIdJornada() != null){
			sql+=" and idjornada <> ? ";
			parametro.add(jornadaTrabalho.getIdJornada());
		}
		
		
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
