package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PerfilContratoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PerfilContratoDao extends CrudDaoDefaultImpl {
	public PerfilContratoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idPerfilContrato" ,"idPerfilContrato", true, true, false, false));
		listFields.add(new Field("idContrato" ,"idContrato", false, false, false, false));
		listFields.add(new Field("nomePerfilContrato" ,"nomePerfilContrato", false, false, false, false));
		listFields.add(new Field("custoHora" ,"custoHora", false, false, false, false));
		listFields.add(new Field("deleted" ,"deleted", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "PerfilContrato";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return PerfilContratoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idContrato", "=", parm)); 
		ordenacao.add(new Order("nomePerfilContrato"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idContrato", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection getTotaisSEMIdMarcoPagamentoPrj(Integer idLinhaBaseProjetoParm) throws PersistenceException {
		String sql = "SELECT RecursoTarefaLinBaseProj.idPerfilContrato, nomePerfilContrato, SUM(RecursoTarefaLinBaseProj.tempoAlocMinutos), SUM(RecursoTarefaLinBaseProj.custo), SUM(RecursoTarefaLinBaseProj.custoPerfil) FROM RecursoTarefaLinBaseProj " +
				"INNER JOIN PerfilContrato ON RecursoTarefaLinBaseProj.idPerfilContrato = PerfilContrato.idPerfilContrato " +
				"INNER JOIN TarefaLinhaBaseProjeto ON TarefaLinhaBaseProjeto.idTarefaLinhaBaseProjeto = RecursoTarefaLinBaseProj.idTarefaLinhaBaseProjeto " +
				"WHERE TarefaLinhaBaseProjeto.idMarcoPagamentoPrj IS NULL AND TarefaLinhaBaseProjeto.idLinhaBaseProjeto = ? AND (deleted IS NULL or deleted = 'N') " +
				"GROUP BY RecursoTarefaLinBaseProj.idPerfilContrato, nomePerfilContrato " +
				"ORDER BY nomePerfilContrato";
		List lstDados = this.execSQL(sql, new Object[]{idLinhaBaseProjetoParm});
		List fields = new ArrayList();
		fields.add("idPerfilContrato");
		fields.add("nomePerfilContrato");
		fields.add("tempoAlocMinutosTotal");
		fields.add("custoTotal");
		fields.add("custoTotalPerfil");
		return this.listConvertion(getBean(), lstDados, fields);
	}	
	public Collection getTotaisByIdMarcoPagamentoPrj(Integer idMarcoPagamentoPrjParm, Integer idLinhaBaseProjetoParm) throws PersistenceException {
		String sql = "SELECT RecursoTarefaLinBaseProj.idPerfilContrato, nomePerfilContrato, SUM(RecursoTarefaLinBaseProj.tempoAlocMinutos), SUM(RecursoTarefaLinBaseProj.custo), SUM(RecursoTarefaLinBaseProj.custoPerfil) FROM RecursoTarefaLinBaseProj " +
				"INNER JOIN PerfilContrato ON RecursoTarefaLinBaseProj.idPerfilContrato = PerfilContrato.idPerfilContrato " +
				"INNER JOIN TarefaLinhaBaseProjeto ON TarefaLinhaBaseProjeto.idTarefaLinhaBaseProjeto = RecursoTarefaLinBaseProj.idTarefaLinhaBaseProjeto " +
				"WHERE TarefaLinhaBaseProjeto.idMarcoPagamentoPrj = ? AND TarefaLinhaBaseProjeto.idLinhaBaseProjeto = ? AND (deleted IS NULL or deleted = 'N') " +
				"GROUP BY RecursoTarefaLinBaseProj.idPerfilContrato, nomePerfilContrato " +
				"ORDER BY nomePerfilContrato";
		List lstDados = this.execSQL(sql, new Object[]{idMarcoPagamentoPrjParm, idLinhaBaseProjetoParm});
		List fields = new ArrayList();
		fields.add("idPerfilContrato");
		fields.add("nomePerfilContrato");
		fields.add("tempoAlocMinutosTotal");
		fields.add("custoTotal");
		fields.add("custoTotalPerfil");
		return this.listConvertion(getBean(), lstDados, fields);
	}	
	public Collection getTotaisByIdTarefaLinhaBaseProjeto(Integer idTarefaLinhaBaseProjetoParm) throws PersistenceException {
		String sql = "SELECT RecursoTarefaLinBaseProj.idPerfilContrato, nomePerfilContrato, SUM(tempoAlocMinutos) FROM RecursoTarefaLinBaseProj INNER JOIN PerfilContrato ON RecursoTarefaLinBaseProj.idPerfilContrato = PerfilContrato.idPerfilContrato " +
				"WHERE RecursoTarefaLinBaseProj.idTarefaLinhaBaseProjeto = ? " +
				"GROUP BY RecursoTarefaLinBaseProj.idPerfilContrato, nomePerfilContrato " +
				"ORDER BY nomePerfilContrato";
		List lstDados = this.execSQL(sql, new Object[]{idTarefaLinhaBaseProjetoParm});
		List fields = new ArrayList();
		fields.add("idPerfilContrato");
		fields.add("nomePerfilContrato");
		fields.add("tempoAlocMinutosTotal");
		return this.listConvertion(getBean(), lstDados, fields);
	}
	public Collection getTotaisByIdLinhaBaseProjeto(Integer idLinhaBaseProjetoParm) throws PersistenceException {
		String sql = "SELECT RecursoTarefaLinBaseProj.idPerfilContrato, nomePerfilContrato, SUM(tempoAlocMinutos), SUM(custo), SUM(custoPerfil) FROM RecursoTarefaLinBaseProj INNER JOIN PerfilContrato ON RecursoTarefaLinBaseProj.idPerfilContrato = PerfilContrato.idPerfilContrato " +
				"WHERE RecursoTarefaLinBaseProj.idTarefaLinhaBaseProjeto IN (SELECT idTarefaLinhaBaseProjeto FROM TarefaLinhaBaseProjeto WHERE idLinhaBaseProjeto = ?) " +
				"GROUP BY RecursoTarefaLinBaseProj.idPerfilContrato, nomePerfilContrato " +
				"ORDER BY nomePerfilContrato";
		List lstDados = this.execSQL(sql, new Object[]{idLinhaBaseProjetoParm});
		List fields = new ArrayList();
		fields.add("idPerfilContrato");
		fields.add("nomePerfilContrato");
		fields.add("tempoAlocMinutosTotal");
		fields.add("custoTotal");
		fields.add("custoTotalPerfil");
		return this.listConvertion(getBean(), lstDados, fields);
	}	
}
