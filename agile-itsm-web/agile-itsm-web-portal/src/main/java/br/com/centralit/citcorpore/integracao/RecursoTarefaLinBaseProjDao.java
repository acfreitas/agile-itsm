package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RecursoTarefaLinBaseProjDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RecursoTarefaLinBaseProjDao extends CrudDaoDefaultImpl {
	public RecursoTarefaLinBaseProjDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idRecursoTarefaLinBaseProj" ,"idRecursoTarefaLinBaseProj", true, true, false, false));
		listFields.add(new Field("idTarefaLinhaBaseProjeto" ,"idTarefaLinhaBaseProjeto", false, false, false, false));
		listFields.add(new Field("idPerfilContrato" ,"idPerfilContrato", false, false, false, false));
		listFields.add(new Field("idEmpregado" ,"idEmpregado", false, false, false, false));
		listFields.add(new Field("percentualAloc" ,"percentualAloc", false, false, false, false));
		listFields.add(new Field("tempoAloc" ,"tempoAloc", false, false, false, false));
		listFields.add(new Field("percentualExec" ,"percentualExec", false, false, false, false));
		listFields.add(new Field("tempoAlocMinutos" ,"tempoAlocMinutos", false, false, false, false));
		listFields.add(new Field("custo" ,"custo", false, false, false, false));
		listFields.add(new Field("custoPerfil" ,"custoPerfil", false, false, false, false));
		listFields.add(new Field("esforcoPorOS" ,"esforcoPorOS", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "RecursoTarefaLinBaseProj";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return RecursoTarefaLinBaseProjDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdTarefaLinhaBaseProjeto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idTarefaLinhaBaseProjeto", "=", parm));
		ordenacao.add(new Order("idEmpregado"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdTarefaLinhaBaseProjetoAndIdEmpregado(Integer parm, Integer idEmpregado) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idTarefaLinhaBaseProjeto", "=", parm));
		condicao.add(new Condition("idEmpregado", "=", idEmpregado));
		ordenacao.add(new Order("idEmpregado"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdLinhaBaseProjeto(Integer idLinhaBaseProjeto) throws PersistenceException {
		List parametros = new ArrayList();
		String sql = "DELETE FROM RecursoTarefaLinBaseProj WHERE idTarefaLinhaBaseProjeto IN (Select idTarefaLinhaBaseProjeto FROM TarefaLinhaBaseProjeto WHERE idLinhaBaseProjeto = ?)";
		parametros.add(idLinhaBaseProjeto);
		this.execUpdate(sql, parametros.toArray());
	}
	public void deleteByIdTarefaLinhaBaseProjeto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idTarefaLinhaBaseProjeto", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdEmpregado(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idEmpregado", "=", parm));
		ordenacao.add(new Order("idTarefaLinhaBaseProjeto"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdEmpregado(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idEmpregado", "=", parm));
		super.deleteByCondition(condicao);
	}
	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

}
