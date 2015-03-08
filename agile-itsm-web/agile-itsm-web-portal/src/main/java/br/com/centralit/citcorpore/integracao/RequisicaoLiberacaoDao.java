package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequisicaoLiberacaoDao extends CrudDaoDefaultImpl {

	public RequisicaoLiberacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idLiberacao" ,"idRequisicaoLiberacao", true, true, false, false));
		listFields.add(new Field("idSolicitante" ,"idSolicitante", false, false, false, false));
		listFields.add(new Field("idresponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("titulo" ,"titulo", false, false, false, false));
		listFields.add(new Field("idContatoRequisicaoLiberacao" ,"idContatoRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		listFields.add(new Field("dataInicial" ,"dataInicial", false, false, false, false));
		listFields.add(new Field("dataFinal" ,"dataFinal", false, false, false, false));
		listFields.add(new Field("dataLiberacao" ,"dataLiberacao", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("risco" ,"risco", false, false, false, false));
		listFields.add(new Field("versao" ,"versao", false, false, false, false));
		listFields.add(new Field("idTipoLiberacao" ,"idTipoLiberacao", false, false, false, false));
		listFields.add(new Field("datahoraterminoagendada" ,"dataHoraTerminoAgendada", false, false, false, false));
		listFields.add(new Field("datahorainicioagendada" ,"dataHoraInicioAgendada", false, false, false, false));
		listFields.add(new Field("idProprietario" ,"idProprietario", false, false, false, false));
		listFields.add(new Field("prazomm" ,"prazoMM", false, false, false, false));
		listFields.add(new Field("prazohh" ,"prazoHH", false, false, false, false));
		listFields.add(new Field("enviaemailfinalizacao" ,"enviaEmailFinalizacao", false, false, false, false));
		listFields.add(new Field("datahorasuspensao" ,"dataHoraSuspensao", false, false, false, false));
		listFields.add(new Field("idcalendario" ,"idCalendario", false, false, false, false));
		listFields.add(new Field("dataHoraInicio" ,"dataHoraInicio", false, false, false, false));
		listFields.add(new Field("datahorareativacao" ,"dataHoraReativacao", false, false, false, false));
		listFields.add(new Field("datahoracaptura" ,"dataHoraCaptura", false, false, false, false));
		listFields.add(new Field("tempoatendimentomm" ,"tempoAtendimentoMM", false, false, false, false));
		listFields.add(new Field("tempoatendimentohh" ,"tempoAtendimentoHH", false, false, false, false));
		listFields.add(new Field("tempodecorridomm" ,"tempoDecorridoMM", false, false, false, false));
		listFields.add(new Field("tempodecorridohh" ,"tempoDecorridoHH", false, false, false, false));
		listFields.add(new Field("status" ,"status", false, false, false, false));
		listFields.add(new Field("datahoraconclusao" ,"dataHoraConclusao", false, false, false, false));
		listFields.add(new Field("dataHoraTermino" ,"dataHoraTermino", false, false, false, false));
		listFields.add(new Field("tempocapturamm" ,"tempoCapturaMM", false, false, false, false));
		listFields.add(new Field("seqreabertura" ,"seqreabertura", false, false, false, false));
		listFields.add(new Field("tempoatrasomm" ,"tempoAtrasoMM", false, false, false, false));
		listFields.add(new Field("tempoatrasohh" ,"tempoAtrasoHH", false, false, false, false));
		listFields.add(new Field("enviaemailacoes" ,"enviaEmailAcoes", false, false, false, false));
		listFields.add(new Field("enviaemailcriacao" ,"enviaEmailCriacao", false, false, false, false));
		listFields.add(new Field("tempocapturahh" ,"tempoCapturaHH", false, false, false, false));
		listFields.add(new Field("idGrupoAtual" ,"idGrupoAtual", false, false, false, false));
		listFields.add(new Field("prioridade" ,"prioridade", false, false, false, false));
		listFields.add(new Field("nivelUrgencia" ,"nivelUrgencia", false, false, false, false));
		listFields.add(new Field("nivelImpacto" ,"nivelImpacto", false, false, false, false));
		listFields.add(new Field("idAprovador" ,"idAprovador", false, false, false, false));
		listFields.add(new Field("datahoraAprovacao" ,"datahoraAprovacao", false, false, false, false));
		listFields.add(new Field("idContrato" ,"idContrato", false, false, false, false));
		listFields.add(new Field("idUltimaAprovacao" ,"idUltimaAprovacao", false, false, false, false));
		listFields.add(new Field("fechamento" ,"fechamento", false, false, false, false));
		listFields.add(new Field("idgruponivel1", "idGrupoNivel1", false, false, false, false));
		listFields.add(new Field("idGrupoAprovador", "idGrupoAprovador", false, false, false, false));
		listFields.add(new Field("idCategoriaSolucao", "idCategoriaSolucao", false, false, false, false));
		listFields.add(new Field("idGrupoAtvPeriodica", "idGrupoAtvPeriodica", false, false, false, false));
		
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Liberacao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return RequisicaoLiberacaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdSolicitante(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idSolicitante", "=", parm)); 
		ordenacao.add(new Order("dataInicial"));
		return super.findByCondition(condicao, ordenacao);
	}
	public List<RequisicaoLiberacaoDTO> listLiberacoes() throws Exception {
		List lstRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select l.idliberacao, l.idsolicitante, u.nome, l.descricao, l.datainicial, l.datafinal, l.situacao ");
		sql.append("from liberacao l ");
		sql.append("INNER JOIN usuario u ON u.idempregado = l.idsolicitante;");


		list = this.execSQL(sql.toString(), null);

		lstRetorno.add("idLiberacao");
		lstRetorno.add("idSolicitante");
		lstRetorno.add("nomeSolicitante");
		lstRetorno.add("descricao");
		lstRetorno.add("dataInicial");
		lstRetorno.add("dataFinal");
		lstRetorno.add("situacao");
		

		if (list != null && !list.isEmpty()) {

			return (List<RequisicaoLiberacaoDTO>) this.listConvertion(getBean(), list, lstRetorno);

		} else {

			return null;
		}
	}
	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}
	
	public void updateFase(Integer idRequisicao, String fase) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE " + getTableName() + " SET fase = ? WHERE (idliberacao = ?)");
		Object[] params = { fase, idRequisicao };
		try {
			this.execUpdate(sql.toString(), params);
		} catch (PersistenceException e) {
			System.out.println("Problemas com atualização da requisição de Liberação.");
			e.printStackTrace();
		}
	}
	
	public Collection<RequisicaoLiberacaoDTO> listaRequisicaoLiberacaoPorCriterios(PesquisaRequisicaoLiberacaoDTO pesquisaRequisicaoLiberacaoDto) throws Exception {

		List listRetorno = new ArrayList();

		StringBuilder sql = new StringBuilder();

		List lista = new ArrayList();

		List parametros = new ArrayList();

		sql.append("SELECT l.tempoAtendimentoHH,l.tempoAtendimentoMM,l.datahoracaptura, l.datahoratermino, l.idliberacao, l.titulo, l.status, l.datahorainicioagendada, l.datahoraterminoagendada, l.prazohh, l.prazomm, ");
		sql.append("l.descricao, l.idgrupoatual, l.seqreabertura, l.idsolicitante, l.idcontatorequisicaoliberacao, l.prioridade, l.idcontrato, l.idresponsavel, e.nome , g.nome, cont.idunidade," +
				" l.descricao, l.datahoraconclusao, cont.nomecontato ");
		sql.append("FROM liberacao l ");
		sql.append("INNER JOIN grupo g ON l.idgrupoatual = g.idgrupo ");
		sql.append("INNER JOIN empregados e ON l.idsolicitante = e.idempregado ");
		sql.append("INNER JOIN contatorequisicaoliberacao cont on l.idcontatorequisicaoliberacao = cont.idcontatorequisicaoliberacao ");
		sql.append("WHERE (UPPER(l.situacao) = UPPER('*') OR '*' = '*') ");

		if (pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacaoPesquisa() != null) {
			sql.append("AND (l.idLiberacao = ?) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getIdRequisicaoLiberacaoPesquisa());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdSolicitante() != null) {
			sql.append("AND (l.idsolicitante = ?) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getIdSolicitante());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdPrioridade() != null) {
			sql.append("AND (l.prioridade = ? ) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getIdPrioridade());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdContrato() != null) {
			sql.append("AND (l.idcontrato = ? ) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getIdContrato());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdUnidade() != null) {
			sql.append("AND (cont.idunidade = ? ) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getIdUnidade());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdGrupoAtual() != null) {
			sql.append("AND (l.idgrupoatual = ? ) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getIdGrupoAtual());

		}
		if (pesquisaRequisicaoLiberacaoDto.getSituacao() != null && !pesquisaRequisicaoLiberacaoDto.getSituacao().equalsIgnoreCase("")) {
			sql.append("AND (l.status = ? ) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getSituacao());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdResponsavel() != null) {
			sql.append("AND (l.idResponsavel = ?  ) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getIdResponsavel());
		}
		if (pesquisaRequisicaoLiberacaoDto.getIdContato() != null) {
			sql.append("AND (l.idContatoRequisicaoLiberacao = ?  ) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getIdContato());
		}
		
		if(pesquisaRequisicaoLiberacaoDto.getDataInicio() != null && pesquisaRequisicaoLiberacaoDto.getDataFim() != null){
			sql.append("AND (l.datahorainicio BETWEEN ? AND ?) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getDataInicio());
			parametros.add(transformaHoraFinal(pesquisaRequisicaoLiberacaoDto.getDataFim()));
		}
		

		if (pesquisaRequisicaoLiberacaoDto.getDataInicioFechamento() != null && !StringUtils.equalsIgnoreCase(pesquisaRequisicaoLiberacaoDto.getDataInicioFechamento().toString(), "1970-01-01")) {
			sql.append("AND (l.datahoraconclusao BETWEEN ? AND ?) ");
			parametros.add(pesquisaRequisicaoLiberacaoDto.getDataInicioFechamento());
			parametros.add(transformaHoraFinal(pesquisaRequisicaoLiberacaoDto.getDataFimFechamento()));
		}

		if (pesquisaRequisicaoLiberacaoDto.getOrdenacao() != null) {
			sql.append(" ORDER BY " + pesquisaRequisicaoLiberacaoDto.getOrdenacao() + "");
		}

		lista = this.execSQL(sql.toString(), parametros.toArray());
		
		
		listRetorno.add("tempoAtendimentoHH");
		listRetorno.add("tempoAtendimentoMM");
		listRetorno.add("dataHoraCaptura");
		listRetorno.add("dataHoraTermino");
		listRetorno.add("idRequisicaoLiberacao");
		listRetorno.add("titulo");
		listRetorno.add("status");
		listRetorno.add("dataHoraInicioAgendada");
		listRetorno.add("dataHoraTerminoAgendada");
		listRetorno.add("prazoHH");
		listRetorno.add("prazoMM");
		listRetorno.add("descricao");
		listRetorno.add("idGrupoAtual");
		listRetorno.add("seqReabertura");
		listRetorno.add("idSolicitante");
		listRetorno.add("idContatoRequisicaoLiberacao");
		listRetorno.add("prioridade");
		listRetorno.add("idContrato");
		listRetorno.add("idResponsavel");
		listRetorno.add("nomeSolicitante");
		listRetorno.add("nomeGrupoAtual");
		listRetorno.add("idUnidade");
		listRetorno.add("descricao");
		listRetorno.add("dataHoraConclusao");
		listRetorno.add("nomeContato");
		
		List listaSolicitacoes = this.engine.listConvertion(getBean(), lista, listRetorno);

		return listaSolicitacoes;
	}
	
	
	private Timestamp transformaHoraFinal(Date data) throws ParseException {
		String dataHora = data + " 23:59:59";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date d = sdf.parse(dataHora);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
		return sqlDate;
	}
	
	public List<RequisicaoLiberacaoDTO> findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws Exception {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select r.idliberacao, r.titulo, r.versao, r.descricao, r.risco, r.status ");
		sql.append("from liberacao r ");
		sql.append("inner join conhecimentoliberacao c on r.idliberacao = c.idrequisicaoliberacao ");
		sql.append("where c.idbaseconhecimento = ? ");

		parametro.add(baseConhecimentoDto.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idRequisicaoLiberacao");
		listRetorno.add("titulo");
		listRetorno.add("versao");
		listRetorno.add("descricao");
		listRetorno.add("risco");
		listRetorno.add("status");

		if (list != null && !list.isEmpty()) {

			return (List<RequisicaoLiberacaoDTO>) this.listConvertion(getBean(), list, listRetorno);

		} else {

			return null;
		}
	}
	
	public List<RequisicaoLiberacaoDTO> listLiberacaoByIdItemConfiguracao(Integer idItemConfiguracao) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append("select lib.idliberacao, lib.titulo, lib.idsolicitante, lib.idproprietario, lib.nivelimpacto, lib.nivelurgencia, lib.descricao, ");
		sql.append("emp.nome, us.nome ");
		sql.append("from requisicaoliberacaoitemconfiguracao req ");
		sql.append("inner join liberacao lib on lib.idliberacao = req.idrequisicaoliberacao ");
		sql.append("inner join itemconfiguracao item on item.iditemconfiguracao = req.iditemconfiguracao ");
		sql.append("inner join empregados emp on emp.idempregado = lib.idsolicitante ");
		sql.append("inner join usuario us on us.idusuario = lib.idproprietario ");
		sql.append("where req.iditemconfiguracao = ? ");
		
		List parametro = new ArrayList();
		List<String> listRetorno = new ArrayList<String>();
		parametro.add(idItemConfiguracao);

		listRetorno.add("idLiberacao");
		listRetorno.add("titulo");
		listRetorno.add("idSolicitante");
		listRetorno.add("idProprietario");
		listRetorno.add("nivelImpacto");
		listRetorno.add("nivelUrgencia");
		listRetorno.add("descricao");
		listRetorno.add("nomeSolicitante");
		listRetorno.add("nomeProprietario");


		List lista = this.execSQL(sql.toString(), parametro.toArray());
		return this.engine.listConvertion(getBean(), lista, listRetorno);
	}
	
}
