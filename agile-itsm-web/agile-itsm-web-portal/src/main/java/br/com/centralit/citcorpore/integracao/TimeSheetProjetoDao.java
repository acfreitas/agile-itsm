package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TimeSheetProjetoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class TimeSheetProjetoDao extends CrudDaoDefaultImpl {
	public TimeSheetProjetoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idTimeSheetProjeto" ,"idTimeSheetProjeto", true, true, false, false));
		listFields.add(new Field("idRecursoTarefaLinBaseProj" ,"idRecursoTarefaLinBaseProj", false, false, false, false));
		listFields.add(new Field("dataHoraReg" ,"dataHoraReg", false, false, false, false));
		listFields.add(new Field("data" ,"data", false, false, false, false));
		listFields.add(new Field("hora" ,"hora", false, false, false, false));
		listFields.add(new Field("qtdeHoras" ,"qtdeHoras", false, false, false, false));
		listFields.add(new Field("custo" ,"custo", false, false, false, false));
		listFields.add(new Field("detalhamento" ,"detalhamento", false, false, false, false));
		listFields.add(new Field("percExecutado" ,"percExecutado", false, false, false, false));
		
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "TimeSheetProjeto";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return TimeSheetProjetoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdRecursoTarefaLinBaseProj(Integer parm) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		
		List list = new ArrayList();
		sql.append("select * from timesheetprojeto where idrecursotarefalinbaseproj = ?  order by datahorareg desc");
		parametro.add(parm);
		List listRetorno = new ArrayList();
		listRetorno.add("idTimeSheetProjeto");
		listRetorno.add("idRecursoTarefaLinBaseProj");
		listRetorno.add("dataHoraReg");
		listRetorno.add("data");
		listRetorno.add("hora");
		listRetorno.add("qtdeHoras");
		listRetorno.add("custo");
		listRetorno.add("detalhamento");
		listRetorno.add("percExecutado");
		
		list = this.execSQL(sql.toString(), parametro.toArray());
		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		if (result != null && !result.isEmpty()) {
			return result;
		}else{
			return null;
		}
	}
	public void deleteByIdRecursoTarefaLinBaseProj(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRecursoTarefaLinBaseProj", "=", parm));
		super.deleteByCondition(condicao);
	}
}
