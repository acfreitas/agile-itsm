package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class HistoricoLiberacaoDao extends CrudDaoDefaultImpl {

	public HistoricoLiberacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		
		listFields.add(new Field("idhistoricoliberacao" ,"idHistoricoLiberacao", true, true, false, false));
		listFields.add(new Field("datahoramodificacao" ,"dataHoraModificacao", false, false, false, false));
		listFields.add(new Field("idexecutormodificacao" ,"idExecutorModificacao", false, false, false, false));
		listFields.add(new Field("tipomodificacao" ,"tipoModificacao", false, false, false, false));
		listFields.add(new Field("historicoversao" ,"historicoVersao", false, false, false, false));
		listFields.add(new Field("idLiberacao" ,"idRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("idSolicitante" ,"idSolicitante", false, false, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("titulo" ,"titulo", false, false, false, false));
		listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		listFields.add(new Field("dataInicial" ,"dataInicial", false, false, false, false));
		listFields.add(new Field("dataFinal" ,"dataFinal", false, false, false, false));
		listFields.add(new Field("dataLiberacao" ,"dataLiberacao", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("risco" ,"risco", false, false, false, false));
		listFields.add(new Field("versao" ,"versao", false, false, false, false));
		listFields.add(new Field("idproprietario" ,"idProprietario", false, false, false, false));
		listFields.add(new Field("enviaemailacoes" ,"enviaEmailAcoes", false, false, false, false));
		listFields.add(new Field("enviaemailcriacao" ,"enviaEmailCriacao", false, false, false, false));
		listFields.add(new Field("tempoatrasohh" ,"tempoAtrasoHH", false, false, false, false));
		listFields.add(new Field("tempoatrasomm" ,"tempoAtrasoMM", false, false, false, false));
		listFields.add(new Field("tempocapturahh" ,"tempoCapturaHH", false, false, false, false));
		listFields.add(new Field("tempocapturamm" ,"tempoCapturaMM", false, false, false, false));
		listFields.add(new Field("datahoratermino" ,"dataHoraTermino", false, false, false, false));
		listFields.add(new Field("datahoraconclusao" ,"dataHoraConclusao", false, false, false, false));
		listFields.add(new Field("status" ,"status", false, false, false, false));
		listFields.add(new Field("tempodecorridohh" ,"tempoDecorridoHH", false, false, false, false));
		listFields.add(new Field("tempodecorridomm" ,"tempoDecorridoMM", false, false, false, false));
		listFields.add(new Field("tempoatendimentohh" ,"tempoAtendimentoHH", false, false, false, false));
		listFields.add(new Field("tempoatendimentomm" ,"tempoAtendimentoMM", false, false, false, false));
		listFields.add(new Field("datahoracaptura" ,"dataHoraCaptura", false, false, false, false));
		listFields.add(new Field("datahorareativacao" ,"dataHoraReativacao", false, false, false, false));
		listFields.add(new Field("datahorainicio" ,"dataHoraInicio", false, false, false, false));
		listFields.add(new Field("idcalendario" ,"idCalendario", false, false, false, false));
		listFields.add(new Field("datahorasuspensao" ,"dataHoraSuspensao", false, false, false, false));
		listFields.add(new Field("enviaemailfinalizacao" ,"enviaEmailFinalizacao", false, false, false, false));
		listFields.add(new Field("prazohh" ,"prazoHH", false, false, false, false));
		listFields.add(new Field("prazomm" ,"prazoMM", false, false, false, false));
		listFields.add(new Field("datahorainicioagendada" ,"dataHoraInicioAgendada", false, false, false, false));
		listFields.add(new Field("datahoraterminoagendada" ,"dataHoraTerminoAgendada", false, false, false, false));
		listFields.add(new Field("idtipoliberacao" ,"idTipoLiberacao", false, false, false, false));
		listFields.add(new Field("idGrupoAtual" ,"idGrupoAtual", false, false, false, false));
		listFields.add(new Field("baseline" ,"baseLine", false, false, false, false));
		listFields.add(new Field("idcontatorequisicaoliberacao" ,"idContatoRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("telefonecontato" ,"telefoneContato", false, false, false, false));
		listFields.add(new Field("ramal" ,"ramal", false, false, false, false));
		listFields.add(new Field("observacao" ,"observacao", false, false, false, false));
		listFields.add(new Field("idunidade" ,"idUnidade", false, false, false, false));
		listFields.add(new Field("Idlocalidade" ,"IdLocalidade", false, false, false, false));
		listFields.add(new Field("nomecontato2" ,"nomeContato2", false, false, false, false));
		listFields.add(new Field("emailcontato" ,"emailContato", false, false, false, false));
		listFields.add(new Field("alterarSituacao" ,"alterarSituacao", false, false, false, false));
		listFields.add(new Field("acaoFluxo" ,"acaoFluxo", false, false, false, false));
		
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "historicoliberacao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return HistoricoLiberacaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	public HistoricoLiberacaoDTO maxIdHistorico(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT MAX(idhistoricoliberacao) AS idhistoricoliberacao FROM " + getTableName() + " WHERE idliberacao = ?");
		parametro.add(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());
		List resultado = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("idHistoricoLiberacao");
		List result = listConvertion(HistoricoLiberacaoDTO.class , resultado, listRetorno);
		HistoricoLiberacaoDTO historicoLiberacaoDTO = new HistoricoLiberacaoDTO();
		historicoLiberacaoDTO = (HistoricoLiberacaoDTO) result.get(0);
		return historicoLiberacaoDTO;
	}
	
	public List<HistoricoLiberacaoDTO> listHistoricoLiberacaoByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws PersistenceException {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		String sql = "SELECT HIST.IDHISTORICOLIBERACAO, HIST.IDLIBERACAO, HIST.IDSOLICITANTE, " +
                                "HIST.VERSAO, HIST.STATUS, HIST.IDEXECUTORMODIFICACAO, HIST.DATAHORAMODIFICACAO, "+
                                "HIST.HISTORICOVERSAO, EMP.NOME ,USU.NOME, HIST.BASELINE "+
                                "FROM HISTORICOLIBERACAO HIST "+
                                "INNER JOIN EMPREGADOS EMP ON EMP.IDEMPREGADO = HIST.IDSOLICITANTE "+
                                "INNER JOIN USUARIO USU ON USU.IDEMPREGADO = HIST.IDEXECUTORMODIFICACAO "+
                                "WHERE HIST.IDLIBERACAO = ? ORDER BY HIST.IDHISTORICOLIBERACAO DESC ";
		
		parametro.add(idRequisicaoLiberacao);
		
		List resultado  = execSQL(sql, parametro.toArray());
		
		listRetorno.add("idHistoricoLiberacao");
		listRetorno.add("idRequisicaoLiberacao");
		listRetorno.add("idProprietario");
		listRetorno.add("versao");
		listRetorno.add("status");
		listRetorno.add("idExecutorModificacao");
		listRetorno.add("dataHoraModificacao");
		listRetorno.add("historicoVersao");
		listRetorno.add("nomeProprietario");
		listRetorno.add("nomeExecutorModificacao");
		listRetorno.add("baseLine");
		return listConvertion(HistoricoLiberacaoDTO.class , resultado, listRetorno);
	}
	
	

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

}
