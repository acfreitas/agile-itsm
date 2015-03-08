package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TarefaLinhaBaseProjetoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class TarefaLinhaBaseProjetoDao extends CrudDaoDefaultImpl {
	public TarefaLinhaBaseProjetoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idTarefaLinhaBaseProjeto" ,"idTarefaLinhaBaseProjeto", true, true, false, false));
		listFields.add(new Field("idLinhaBaseProjeto" ,"idLinhaBaseProjeto", false, false, false, false));
		listFields.add(new Field("sequencia" ,"sequencia", false, false, false, false));
		listFields.add(new Field("idCalendario" ,"idCalendario", false, false, false, false));
		listFields.add(new Field("idTarefaLinhaBaseProjetoVinc" ,"idTarefaLinhaBaseProjetoVinc", false, false, false, false));
		listFields.add(new Field("dataInicio" ,"dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		listFields.add(new Field("codeTarefa" ,"codeTarefa", false, false, false, false));
		listFields.add(new Field("nomeTarefa" ,"nomeTarefa", false, false, false, false));
		listFields.add(new Field("detalhamentoTarefa" ,"detalhamentoTarefa", false, false, false, false));
		listFields.add(new Field("progresso" ,"progresso", false, false, false, false));
		listFields.add(new Field("duracaoHora" ,"duracaoHora", false, false, false, false));
		listFields.add(new Field("nivel" ,"nivel", false, false, false, false));
		listFields.add(new Field("idInternal" ,"idInternal", false, false, false, false));
		listFields.add(new Field("collapsed" ,"collapsed", false, false, false, false));
		listFields.add(new Field("custo" ,"custo", false, false, false, false));
		listFields.add(new Field("custoPerfil" ,"custoPerfil", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("estimada" ,"estimada", false, false, false, false));
		listFields.add(new Field("trabalho" ,"trabalho", false, false, false, false));
		listFields.add(new Field("dataInicioReal" ,"dataInicioReal", false, false, false, false));
		listFields.add(new Field("dataFimReal" ,"dataFimReal", false, false, false, false));
		listFields.add(new Field("duracaoHoraReal" ,"duracaoHoraReal", false, false, false, false));
		listFields.add(new Field("custoReal" ,"custoReal", false, false, false, false));
		listFields.add(new Field("custoRealPerfil" ,"custoRealPerfil", false, false, false, false));
		listFields.add(new Field("idTarefaLinhaBaseProjetoMigr" ,"idTarefaLinhaBaseProjetoMigr", false, false, false, false));
		listFields.add(new Field("idTarefaLinhaBaseProjetoPai" ,"idTarefaLinhaBaseProjetoPai", false, false, false, false));
		listFields.add(new Field("tempoTotAlocMinutos" ,"tempoTotAlocMinutos", false, false, false, false));
		listFields.add(new Field("idPagamentoProjeto" ,"idPagamentoProjeto", false, false, false, false));
		listFields.add(new Field("idMarcoPagamentoPrj" ,"idMarcoPagamentoPrj", false, false, false, false));
		listFields.add(new Field("depends" ,"depends", false, false, false, false));
		listFields.add(new Field("esforcoPorOS" ,"esforcoPorOS", false, false, false, false));
		
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "TarefaLinhaBaseProjeto";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return TarefaLinhaBaseProjetoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdTarefaLinhaBaseProjetoMigr(Integer idTarefaLinhaBaseProjetoMigr) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idTarefaLinhaBaseProjetoMigr", "=", idTarefaLinhaBaseProjetoMigr)); 
		ordenacao.add(new Order("sequencia"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdTarefaLinhaBaseProjetoPai(Integer idTarefaLinhaBaseProjetoPai) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idTarefaLinhaBaseProjetoPai", "=", idTarefaLinhaBaseProjetoPai)); 
		ordenacao.add(new Order("sequencia"));
		return super.findByCondition(condicao, ordenacao);
	}	
	public Collection findByIdLinhaBaseProjeto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idLinhaBaseProjeto", "=", parm)); 
		ordenacao.add(new Order("sequencia"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteFilhasByIdLinhaBaseProjeto(Integer idLinhaBaseProjeto) throws PersistenceException {
		Collection<TarefaLinhaBaseProjetoDTO> tarefas = findByIdLinhaBaseProjeto(idLinhaBaseProjeto);
		if (tarefas != null && !tarefas.isEmpty()) {
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM tarefalinhabaseprojeto ");
			sql.append("WHERE  idtarefalinhabaseprojetopai IN ( ");
			boolean primeiro = true;
			for (TarefaLinhaBaseProjetoDTO tarefa : tarefas) {
				if (primeiro) {
					primeiro = false;
				} else {
					sql.append(", ");
				}
				sql.append(tarefa.getIdTarefaLinhaBaseProjeto().toString());
			}
			sql.append(" ) ");
			execUpdate(sql.toString(), null);
		}
	}
	public void deleteByIdLinhaBaseProjeto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idLinhaBaseProjeto", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findCarteiraByIdEmpregado(Integer idEmpregado) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetorno = new ArrayList();
		String sql = "Select " + this.getNamesFieldsStr("TarefaLinhaBaseProjeto") + ", idRecursoTarefaLinBaseProj, idPerfilContrato, idEmpregado, percentualAloc, tempoAloc, percentualExec from " + this.getTableName() + " inner join RecursoTarefaLinBaseProj on " +
				"RecursoTarefaLinBaseProj.idTarefaLinhaBaseProjeto = TarefaLinhaBaseProjeto.idTarefaLinhaBaseProjeto where RecursoTarefaLinBaseProj.idempregado = ? AND " +
				"TarefaLinhaBaseProjeto.idLinhaBaseProjeto IN (Select idLinhaBaseProjeto FROM LinhaBaseProjeto where situacao = 'E') " +
				"AND TarefaLinhaBaseProjeto.situacao = '" + TarefaLinhaBaseProjetoDTO.ATIVO + "' AND (RecursoTarefaLinBaseProj.percentualExec IS NULL OR RecursoTarefaLinBaseProj.percentualExec < 100) ";
		
		parametro.add(idEmpregado);
		list = this.execSQL(sql, parametro.toArray());

		listRetorno = this.getListNamesFieldClass();
		listRetorno.add("idRecursoTarefaLinBaseProj");
		listRetorno.add("idPerfilContrato");
		listRetorno.add("idEmpregado");
		listRetorno.add("percentualAloc");
		listRetorno.add("tempoAloc");
		listRetorno.add("percentualExec");

		if (list != null && !list.isEmpty()) {

			return (List<TarefaLinhaBaseProjetoDTO>) this.listConvertion(getBean(), list, listRetorno);

		} else {

			return null;
		}		
	}
	public Collection calculaValoresTarefasFilhas(Integer idTarefaLinhaBaseProjetoParm) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetorno = new ArrayList();
		String sql = "Select SUM(tempoTotAlocMinutos), SUM(custo), SUM(custoPerfil), SUM(tempoTotAlocMinutos * (progresso/ 100)) / ( CASE WHEN SUM(tempoTotAlocMinutos) = 0 THEN 1 ELSE SUM(tempoTotAlocMinutos) END ) from " + this.getTableName() + " " +
				"where TarefaLinhaBaseProjeto.idTarefaLinhaBaseProjetoPai = ?";
		
		parametro.add(idTarefaLinhaBaseProjetoParm);
		list = this.execSQL(sql, parametro.toArray());

		listRetorno.add("tempoTotAlocMinutos");
		listRetorno.add("custo");
		listRetorno.add("custoPerfil");
		listRetorno.add("progresso");

		if (list != null && !list.isEmpty()) {

			return (List<TarefaLinhaBaseProjetoDTO>) this.listConvertion(getBean(), list, listRetorno);

		} else {

			return null;
		}		
	}	
	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}
	
}
