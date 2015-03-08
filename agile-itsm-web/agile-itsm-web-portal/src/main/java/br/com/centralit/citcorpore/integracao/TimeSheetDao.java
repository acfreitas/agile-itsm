package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TimeSheetDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class TimeSheetDao extends CrudDaoDefaultImpl {

	private static final String SQL_TIMESHEET_PESSOA_PERIODO = "SELECT data, qtdeHoras, nomeFantasia, nomeProjeto, D.detalhamento, T.detalhamento " +
			"FROM TIMESHEET T " +
			" LEFT OUTER JOIN PROJETOS P on T.idProjeto = P.idProjeto " +
			" LEFT OUTER JOIN CLIENTES C on C.idCliente = P.idCliente " +
			" LEFT OUTER JOIN DEMANDAS D on D.idDemanda = T.idDemanda " +
			"where idEmpregado = ? AND data between ? AND ? order by data";
	
	private static final String SQL_TIMESHEET_DEMANDA = "SELECT data, qtdeHoras, nomeFantasia, nomeProjeto, E.nome, T.detalhamento " +
			"FROM TIMESHEET T " +
			" LEFT OUTER JOIN PROJETOS P on T.idProjeto = P.idProjeto " +
			" LEFT OUTER JOIN CLIENTES C on C.idCliente = P.idCliente " +
			" INNER JOIN EMPREGADOS E on E.idEmpregado = T.idEmpregado " +
			"where idDemanda = ? order by data";
	
	
	public TimeSheetDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return TimeSheetDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idTimeSheet", "idTimeSheet", true, true, false, false));
		listFields.add(new Field("idDemanda", "idDemanda", false, false, false, false));
		listFields.add(new Field("idEmpregado", "idEmpregado", false, false, false, false));
		listFields.add(new Field("idProjeto", "idProjeto", false, false, false, false));
		listFields.add(new Field("qtdeHoras", "qtdeHoras", false, false, false, false));
		listFields.add(new Field("data", "data", false, false, false, false));
		listFields.add(new Field("custoPorHora", "custoPorHora", false, false, false, false));
		listFields.add(new Field("detalhamento", "detalhamento", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "TIMESHEET";
	}

	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("data"));		
		return super.find(obj, ordem);
	}
	
	public Collection findByPessoaAndPeriodo(Integer idEmpregado, Date dataInicio, Date dataFim) throws PersistenceException {
		Object[] objs = new Object[] {idEmpregado, dataInicio, dataFim};
		
		String sql = SQL_TIMESHEET_PESSOA_PERIODO;
		
		List lista = this.execSQL(sql, objs);
		
		List listRetorno = new ArrayList();
		listRetorno.add("data");
		listRetorno.add("qtdeHoras");
		listRetorno.add("nomeCliente");
		listRetorno.add("nomeProjeto");
		listRetorno.add("detalhamentoDemanda");
		listRetorno.add("detalhamento");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0) return null;
		return result;
	}	
	
	public Collection findByDemanda(Integer idDemanda) throws PersistenceException {
		Object[] objs = new Object[] {idDemanda};
		
		String sql = SQL_TIMESHEET_DEMANDA;
		
		List lista = this.execSQL(sql, objs);
		
		List listRetorno = new ArrayList();
		listRetorno.add("data");
		listRetorno.add("qtdeHoras");
		listRetorno.add("nomeCliente");
		listRetorno.add("nomeProjeto");
		listRetorno.add("nomeEmpregado");
		listRetorno.add("detalhamento");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0) return null;
		return result;
	}		
	
	public Collection list() throws PersistenceException {
		return null;
	}


}
