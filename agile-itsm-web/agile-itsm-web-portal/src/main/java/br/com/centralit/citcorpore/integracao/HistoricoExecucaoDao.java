package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoExecucaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class HistoricoExecucaoDao extends CrudDaoDefaultImpl {
	
	private static final String SQL_HISTORICO_DEMANDA = "SELECT H.data, H.situacao, H.detalhamento, H.hora, E.nome " +
		"FROM HISTORICOEXECUCAO H " +
		" INNER JOIN EXECUCAODEMANDA EX on EX.idExecucao = H.idExecucao " +
		" INNER JOIN EMPREGADOS E on E.idEmpregado = H.idEmpregadoExecutor " +
		"where EX.idDemanda = ? order by data";	

	public HistoricoExecucaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return HistoricoExecucaoDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idHistorico", "idHistorico", true, true, false, false));
		listFields.add(new Field("idExecucao", "idExecucao", false, false, false, false));
		listFields.add(new Field("data", "data", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("idEmpregadoExecutor", "idEmpregadoExecutor", false, false, false, false));
		listFields.add(new Field("detalhamento", "detalhamento", false, false, false, false));
		listFields.add(new Field("hora", "hora", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "HISTORICOEXECUCAO";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Collection findByDemanda(Integer idDemanda) throws PersistenceException {
		Object[] objs = new Object[] {idDemanda};
		
		String sql = SQL_HISTORICO_DEMANDA;
		
		List lista = this.execSQL(sql, objs);
		
		List listRetorno = new ArrayList();
		listRetorno.add("data");
		listRetorno.add("situacao");
		listRetorno.add("detalhamento");
		listRetorno.add("hora");
		listRetorno.add("nomeEmpregado");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0) return null;
		return result;
	}
}
