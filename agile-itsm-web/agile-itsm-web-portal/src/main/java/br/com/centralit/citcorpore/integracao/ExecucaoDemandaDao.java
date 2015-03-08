package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExecucaoDemandaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ExecucaoDemandaDao extends CrudDaoDefaultImpl {
	private static final String SQL_GET_ATIVIDADES_PESSOA = "SELECT F.nomeFluxo, " +
		"E.nomeEtapa, " +
		"A.nomeAtividade, " +
		"D.expectativaFim, " +
		"D.prioridade, " +
		"TA.DescricaoTipoAtividade, " +
		"EXD.situacao, " +
		"EMP.nome, " +
		"EXD.qtdeHoras, " +
		"EXD.relato, " +
		"EXD.idExecucao, " +
		"D.idFluxo, " +
		"EXD.idAtividade, " +
		"EXD.idDemanda, " +
		"D.idProjeto, " +
		"P.nomeProjeto, " +
		"D.idTipoDemanda, " +
		"D.detalhamento, " +
		"EXD.terminoPrevisto, " +
		"O.numero, " +
		"O.ano " +
	"FROM EXECUCAODEMANDA EXD " +
		"INNER JOIN DEMANDAS D ON D.idDemanda = EXD.idDemanda " +
		"INNER JOIN FLUXOS F ON F.idFluxo = D.idFluxo " +
		"INNER JOIN ETAPAS E ON E.idFluxo = F.idFluxo " +
		"INNER JOIN ATIVIDADES A ON A.idAtividade = EXD.idAtividade " +
		"INNER JOIN TIPOSATIVIDADES TA on TA.idTipoAtividade = A.idTipoAtividade " +
		"INNER JOIN TIPODEMANDA TD on TD.idTipoDemanda = D.idTipoDemanda " +
		"LEFT OUTER JOIN PROJETOS P on P.idProjeto = D.idProjeto " +
		"LEFT OUTER JOIN EMPREGADOS EMP on EMP.idEmpregado = EXD.idEmpregadoExecutor " +
		"LEFT OUTER JOIN OS O on O.idOS = D.idOS";

	public ExecucaoDemandaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return ExecucaoDemandaDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idExecucao", "idExecucao", true, true, false, false));
		listFields.add(new Field("idDemanda", "idDemanda", false, false, false, false));
		listFields.add(new Field("idAtividade", "idAtividade", false, false, false, false));
		listFields.add(new Field("idEmpregadoExecutor", "idEmpregadoExecutor", false, false, false, false));
		listFields.add(new Field("idEmpregadoReceptor", "idEmpregadoReceptor", false, false, false, false));
		listFields.add(new Field("relato", "relato", false, false, false, false));
		listFields.add(new Field("qtdeHoras", "qtdeHoras", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("grupoExecutor", "grupoExecutor", false, false, false, false));
		listFields.add(new Field("terminoPrevisto", "terminoPrevisto", false, false, false, false));
		listFields.add(new Field("terminoReal", "terminoReal", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "EXECUCAODEMANDA";
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idDemanda"));
		return super.list(list);
	}

	public Collection getAtividadesByGrupoAndPessoa(Integer idEmpregado, String[] grupo) throws PersistenceException {
		//Object[] objs = new Object[] {idEmpregado};
		Object[] objs = null;
		
		String sql = SQL_GET_ATIVIDADES_PESSOA;
		sql += " WHERE (";
		/*
		if (grupo != null && grupo.length > 0){
			sql += " (EXD.grupoExecutor IN (";
			for (int i = 0; i < grupo.length; i++){
				if (i > 0) sql += ",";
				sql += "'" + grupo[i] + "'";
			}
			sql += ")) OR ";
		}		
		sql += " (idEmpregadoExecutor = ?) AND ";
		*/
		sql += " EXD.situacao <> 'F' AND (D.idSituacaoDemanda <> 0 AND D.idSituacaoDemanda <> 7 AND D.idSituacaoDemanda <> 6) ";
		sql += ") ORDER BY D.expectativaFim";
		
		List lista = this.execSQL(sql, objs);
		
		List listRetorno = new ArrayList();
		listRetorno.add("nomeFluxo");
		listRetorno.add("nomeEtapa");
		listRetorno.add("nomeAtividade");
		listRetorno.add("expectativaFim");
		listRetorno.add("prioridade");
		listRetorno.add("DescricaoTipoAtividade");
		listRetorno.add("situacao");
		listRetorno.add("nome");
		listRetorno.add("qtdeHoras");
		listRetorno.add("relato");
		listRetorno.add("idExecucao");
		listRetorno.add("idFluxo");
		listRetorno.add("idAtividade");
		listRetorno.add("idDemanda");
		listRetorno.add("idProjeto");
		listRetorno.add("nomeProjeto");
		listRetorno.add("idTipoDemanda");
		listRetorno.add("detalhamento");
		listRetorno.add("terminoPrevisto");
		listRetorno.add("numeroOS");
		listRetorno.add("anoOS");
		
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0) return null;
		return result;
	}
	
	public void updateAtribuir(IDto obj) throws PersistenceException {
		ExecucaoDemandaDTO execDemanda = (ExecucaoDemandaDTO)obj;
		ExecucaoDemandaDTO execDemandaUpdate = new ExecucaoDemandaDTO();
		
		execDemandaUpdate.setIdEmpregadoExecutor(execDemanda.getIdEmpregadoLogado());
		execDemandaUpdate.setIdExecucao(execDemanda.getIdExecucaoAtribuir());
		execDemandaUpdate.setSituacao("F");
		
		super.updateNotNull(execDemandaUpdate);
	}
	
	public void updateStatus(IDto obj) throws PersistenceException {
		ExecucaoDemandaDTO execDemanda = (ExecucaoDemandaDTO)obj;
		ExecucaoDemandaDTO execDemandaUpdate = new ExecucaoDemandaDTO();
		
		execDemandaUpdate.setIdExecucao(execDemanda.getIdExecucao());
		execDemandaUpdate.setSituacao(execDemanda.getSituacao());
		execDemandaUpdate.setTerminoReal(execDemanda.getTerminoReal());
		
		super.updateNotNull(execDemandaUpdate);
	}	

}
