package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class HistoricoMudancaDao extends CrudDaoDefaultImpl {

	public HistoricoMudancaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idHistoricoMudanca", "idHistoricoMudanca", true, true, false, false));
		listFields.add(new Field("dataHoraModificacao", "dataHoraModificacao", false, false, false, false));
		listFields.add(new Field("idExecutorModificacao", "idExecutorModificacao", false, false, false, false));
		listFields.add(new Field("tipoModificacao", "tipoModificacao", false, false, false, false));
		listFields.add(new Field("historicoVersao", "historicoVersao", false, false, false, false));
		listFields.add(new Field("baseLine", "baseLine", false, false, false, false));
		listFields.add(new Field("acaoFluxo", "acaoFluxo", false, false, false, false));
		listFields.add(new Field("alterarSituacao", "alterarSituacao", false, false, false, false));
		listFields.add(new Field("emailSolicitante", "emailSolicitante", false, false, false, false));
		listFields.add(new Field("registroExecucao", "registroExecucao", false, false, false, false));
		
		listFields.add(new Field("idrequisicaomudanca", "idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("idproprietario", "idProprietario", false, false, false, false));
		listFields.add(new Field("idsolicitante", "idSolicitante", false, false, false, false));
		listFields.add(new Field("idtipomudanca", "idTipoMudanca", false, false, false, false));
		listFields.add(new Field("motivo", "motivo", false, false, false, false));
		listFields.add(new Field("nivelimportancianegocio", "nivelImportanciaNegocio", false, false, false, false));
		listFields.add(new Field("classificacao", "classificacao", false, false, false, false));
		listFields.add(new Field("nivelimpacto", "nivelImpacto", false, false, false, false));
		listFields.add(new Field("analiseimpacto", "analiseImpacto", false, false, false, false));
		listFields.add(new Field("razaoMudanca", "razaoMudanca", false, false, false, false));
		listFields.add(new Field("datahoraconclusao", "dataHoraConclusao", false, false, false, false));
		listFields.add(new Field("dataaceitacao", "dataAceitacao", false, false, false, false));
		listFields.add(new Field("datavotacao", "dataVotacao", false, false, false, false));
		listFields.add(new Field("datahorainicio", "dataHoraInicio", false, false, false, false));
		listFields.add(new Field("datahoratermino", "dataHoraTermino", false, false, false, false));
		listFields.add(new Field("titulo", "titulo", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("risco", "risco", false, false, false, false));
		listFields.add(new Field("estimativacusto", "estimativaCusto", false, false, false, false));
		listFields.add(new Field("planoreversao", "planoReversao", false, false, false, false));
		listFields.add(new Field("status", "status", false, false, false, false));
		listFields.add(new Field("prioridade", "prioridade", false, false, false, false));
		listFields.add(new Field("nomecategoriamudanca", "nomeCategoriaMudanca", false, false, false, false));
		listFields.add(new Field("enviaemailcriacao", "enviaEmailCriacao", false, false, false, false));
		listFields.add(new Field("enviaemailfinalizacao", "enviaEmailFinalizacao", false, false, false, false));
		listFields.add(new Field("enviaemailacoes", "enviaEmailAcoes", false, false, false, false));
		listFields.add(new Field("exibirquadromudancas", "exibirQuadroMudancas", false, false, false, false));
		listFields.add(new Field("seqreabertura", "seqReabertura", false, false, false, false));
		listFields.add(new Field("tempodecorridohh", "tempoDecorridoHH", false, false, false, false));
		listFields.add(new Field("tempodecorridomm", "tempoDecorridoMM", false, false, false, false));
		listFields.add(new Field("datahorasuspensao", "dataHoraSuspensao", false, false, false, false));
		listFields.add(new Field("datahorareativacao", "dataHoraReativacao", false, false, false, false));
		listFields.add(new Field("prazohh", "prazoHH", false, false, false, false));
		listFields.add(new Field("prazomm", "prazoMM", false, false, false, false));
		listFields.add(new Field("idgrupoatual", "idGrupoAtual", false, false, false, false));
		listFields.add(new Field("idgruponivel1", "idGrupoNivel1", false, false, false, false));
		listFields.add(new Field("idcalendario", "idCalendario", false, false, false, false));
		listFields.add(new Field("datahoracaptura", "dataHoraCaptura", false, false, false, false));
		listFields.add(new Field("tempocapturahh", "tempoCapturaHH", false, false, false, false));
		listFields.add(new Field("tempocapturamm", "tempoCapturaMM", false, false, false, false));
		listFields.add(new Field("tempoatrasohh", "tempoAtrasoHH", false, false, false, false));
		listFields.add(new Field("tempoatrasomm", "tempoAtrasoMM", false, false, false, false));
		listFields.add(new Field("tempoatendimentohh", "tempoAtendimentoHH", false, false, false, false));
		listFields.add(new Field("tempoatendimentomm", "tempoAtendimentoMM", false, false, false, false));
		listFields.add(new Field("fase", "fase", false, false, false, false));
		listFields.add(new Field("nivelurgencia", "nivelUrgencia", false, false, false, false));
		listFields.add(new Field("idbaseconhecimento", "idBaseConhecimento", false, false, false, false));
		listFields.add(new Field("idcontrato", "idContrato", false, false, false, false));
		listFields.add(new Field("idGrupoComite", "idGrupoComite", false, false, false, false));
		listFields.add(new Field("idunidade", "idUnidade", false, false, false, false));
		listFields.add(new Field("idcontatorequisicaomudanca", "idContatoRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("enviaEmailGrupoComite", "enviaEmailGrupoComite", false, false, false, false));
		listFields.add(new Field("datahorainicioagendada", "dataHoraInicioAgendada", false, false, false, false));
		listFields.add(new Field("datahoraterminoagendada", "dataHoraTerminoAgendada", false, false, false, false));
		listFields.add(new Field("idlocalidade", "IdLocalidade", false, false, false, false));
		listFields.add(new Field("fechamento", "fechamento", false, false, false, false));
		listFields.add(new Field("ehPropostaAux", "ehPropostaAux", false, false, false, false));
		listFields.add(new Field("votacaoPropostaAprovadaAux", "votacaoPropostaAprovadaAux", false, false, false, false));
		listFields.add(new Field("idGrupoAtvPeriodica", "idGrupoAtvPeriodica", false, false, false, false));
		return listFields;
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
return null;
	}

	@Override
	public String getTableName() {
		return this.getOwner() + "historicoMudanca";
	}

	@Override
	public Collection list() throws PersistenceException {
return null;
	}

	@Override
	public Class getBean() {
		return HistoricoMudancaDTO.class;
	}

	public HistoricoMudancaDTO maxIdHistorico(RequisicaoMudancaDTO requisicaoMudancaDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT MAX(idHistoricoMudanca) AS idHistoricoMudanca FROM " + getTableName() + " WHERE idrequisicaomudanca = ?");
		parametro.add(requisicaoMudancaDTO.getIdRequisicaoMudanca());
		List resultado = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("idHistoricoMudanca");
		List result = listConvertion(HistoricoMudancaDTO.class , resultado, listRetorno);
		HistoricoMudancaDTO historicoMudancaDTO = new HistoricoMudancaDTO();
		historicoMudancaDTO = (HistoricoMudancaDTO) result.get(0);
		return historicoMudancaDTO;
	}
	
	public List<HistoricoMudancaDTO> listHistoricoMudancaByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws PersistenceException {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		String sql = "SELECT HIST.REGISTROEXECUCAO, HIST.EMAILSOLICITANTE, HIST.ALTERARSITUACAO, HIST.ACAOFLUXO, HIST.IDHISTORICOMUDANCA, HIST.IDREQUISICAOMUDANCA, HIST.IDSOLICITANTE, "+
                                "HIST.STATUS, HIST.IDEXECUTORMODIFICACAO, HIST.DATAHORAMODIFICACAO, "+
                                "HIST.HISTORICOVERSAO, EMP.NOME ,USU.NOME, HIST.BASELINE, g.nome,HIST.nivelimpacto, HIST.nivelurgencia, HIST.datahoraterminoagendada, HIST.fase "+
                                "FROM HISTORICOMUDANCA HIST "+
                                "INNER JOIN EMPREGADOS EMP ON EMP.IDEMPREGADO = HIST.IDSOLICITANTE "+
                                "INNER JOIN USUARIO USU ON USU.IDEMPREGADO = HIST.IDEXECUTORMODIFICACAO "+
                                "LEFT JOIN GRUPO G on hist.idgrupoatual = g.idgrupo "+
                                "WHERE HIST.IDREQUISICAOMUDANCA = ? "+
                                "group by HIST.IDHISTORICOMUDANCA,HIST.REGISTROEXECUCAO, HIST.EMAILSOLICITANTE, HIST.ALTERARSITUACAO, HIST.ACAOFLUXO, HIST.IDHISTORICOMUDANCA, HIST.IDREQUISICAOMUDANCA, HIST.IDSOLICITANTE,  "+
                                "HIST.STATUS, HIST.IDEXECUTORMODIFICACAO, HIST.DATAHORAMODIFICACAO,  "+
                                "HIST.HISTORICOVERSAO, EMP.NOME ,USU.NOME, HIST.BASELINE, g.nome,HIST.nivelimpacto, HIST.nivelurgencia, HIST.datahoraterminoagendada, HIST.fase  "+
                                "ORDER BY HIST.IDHISTORICOMUDANCA DESC ";
		
		parametro.add(idRequisicaoMudanca);
		
		List resultado  = execSQL(sql, parametro.toArray());
		
		listRetorno.add("registroExecucao");
		listRetorno.add("emailSolicitante");
		listRetorno.add("alterarSituacao");
		listRetorno.add("acaoFluxo");
		listRetorno.add("idHistoricoMudanca");
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("idProprietario");
		listRetorno.add("status");
		listRetorno.add("idExecutorModificacao");
		listRetorno.add("dataHoraModificacao");
		listRetorno.add("historicoVersao");
		listRetorno.add("nomeProprietario");
		listRetorno.add("nomeExecutorModificacao");
		listRetorno.add("baseLine");
		listRetorno.add("status");
		listRetorno.add("nivelImpacto");
		listRetorno.add("nivelUrgencia");
		listRetorno.add("dataHoraTerminoAgendada");
		listRetorno.add("fase");
		return listConvertion(HistoricoMudancaDTO.class , resultado, listRetorno);
	}
	
	

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}
}