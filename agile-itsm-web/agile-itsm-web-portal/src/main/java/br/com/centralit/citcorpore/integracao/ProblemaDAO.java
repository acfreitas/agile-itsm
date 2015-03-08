package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PesquisaProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RelatorioProblemaIncidentesDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoProblema;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked"})
public class ProblemaDAO extends CrudDaoDefaultImpl {


	public ProblemaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idProblema", "idProblema", true, true, false, false));
		listFields.add(new Field("status", "status", false, false, false, false));
		listFields.add(new Field("prioridade", "prioridade", false, false, false, false));
		listFields.add(new Field("idCriador", "idCriador", false, false, false, false));
		listFields.add(new Field("idProprietario", "idProprietario", false, false, false, false));
		listFields.add(new Field("titulo", "titulo", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("idCategoriaProblema", "idCategoriaProblema", false, false, false, false));
		listFields.add(new Field("impacto", "impacto", false, false, false, false));
		listFields.add(new Field("urgencia", "urgencia", false, false, false, false));
		listFields.add(new Field("severidade", "severidade", false, false, false, false));
		listFields.add(new Field("proativoReativo", "proativoReativo", false, false, false, false));
		listFields.add(new Field("dataHoraLimiteSolucionar", "dataHoraLimiteSolucionar", false, false, false, false));
		listFields.add(new Field("msgErroAssociada", "msgErroAssociada", false, false, false, false));
		listFields.add(new Field("idProblemaItemConfiguracao", "idProblemaItemConfiguracao", false, false, false, false));
		// listFields.add(new Field("idErrosConhecidos", "idErrosConhecidos", false, false, false, false));
		listFields.add(new Field("idProblemaMudanca", "idProblemaMudanca", false, false, false, false));
		listFields.add(new Field("idProblemaIncidente", "idProblemaIncidente", false, false, false, false));
		listFields.add(new Field("dataHoraInicio", "dataHoraInicio", false, false, false, false));
		listFields.add(new Field("dataHoraFim", "dataHoraFim", false, false, false, false));
		listFields.add(new Field("solucaoDefinitiva", "solucaoDefinitiva", false, false, false, false));
		listFields.add(new Field("adicionarBDCE", "adicionarBDCE", false, false, false, false));
		listFields.add(new Field("statusBaseConhecimento", "statusBaseConhecimento", false, false, false, false));
		listFields.add(new Field("idPastaBaseConhecimento", "idPastaBaseConhecimento", false, false, false, false));
		listFields.add(new Field("causaRaiz", "causaRaiz", false, false, false, false));
		listFields.add(new Field("solucaoContorno", "solucaoContorno", false, false, false, false));
		listFields.add(new Field("idBaseConhecimento", "idBaseConhecimento", false, false, false, false));
		listFields.add(new Field("idServico", "idServico", false, false, false, false));
		listFields.add(new Field("idGrupo", "idGrupo", false, false, false, false));
		listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
		listFields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
		listFields.add(new Field("prazoHH", "prazoHH", false, false, false, false));
		listFields.add(new Field("prazoMM", "prazoMM", false, false, false, false));
		listFields.add(new Field("slaACombinar", "slaACombinar", false, false, false, false));
		listFields.add(new Field("dataHoraLimite", "dataHoraLimite", false, false, false, false));
		listFields.add(new Field("idPrioridade", "idPrioridade", false, false, false, false));
		listFields.add(new Field("dataHoraSolicitacao", "dataHoraSolicitacao", false, false, false, false));
		listFields.add(new Field("dataHoraCaptura", "dataHoraCaptura", false, false, false, false));
		listFields.add(new Field("dataHoraInicioSLA", "dataHoraInicioSLA", false, false, false, false));
		listFields.add(new Field("dataHoraReativacao", "dataHoraReativacao", false, false, false, false));
		listFields.add(new Field("dataHoraReativacaoSLA", "dataHoraReativacaoSLA", false, false, false, false));
		listFields.add(new Field("dataHoraSuspensao", "dataHoraSuspensao", false, false, false, false));
		listFields.add(new Field("dataHoraSuspensaoSLA", "dataHoraSuspensaoSLA", false, false, false, false));
		listFields.add(new Field("enviaEmailAcoes", "enviaEmailAcoes", false, false, false, false));
		listFields.add(new Field("enviaEmailCriacao", "enviaEmailCriacao", false, false, false, false));
		listFields.add(new Field("enviaEmailFinalizacao", "enviaEmailFinalizacao", false, false, false, false));
		listFields.add(new Field("idCalendario", "idCalendario", false, false, false, false));
		listFields.add(new Field("idFaseAtual", "idFaseAtual", false, false, false, false));
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idSolicitante", "idSolicitante", false, false, false, false));
		listFields.add(new Field("resposta", "resposta", false, false, false, false));
		listFields.add(new Field("seqreabertura", "seqReabertura", false, false, false, false));
		listFields.add(new Field("situacaoSLA", "situacaoSLA", false, false, false, false));
		listFields.add(new Field("tempoAtendimentoHH", "tempoAtendimentoHH", false, false, false, false));
		listFields.add(new Field("tempoAtendimentoMM", "tempoAtendimentoMM", false, false, false, false));
		listFields.add(new Field("tempoAtrasoHH", "tempoAtrasoHH", false, false, false, false));
		listFields.add(new Field("tempoAtrasoMM", "tempoAtrasoMM", false, false, false, false));
		listFields.add(new Field("tempoCapturaHH", "tempoCapturaHH", false, false, false, false));
		listFields.add(new Field("tempoCapturaMM", "tempoCapturaMM", false, false, false, false));
		listFields.add(new Field("tempoDecorridoHH", "tempoDecorridoHH", false, false, false, false));
		listFields.add(new Field("tempoDecorridoMM", "tempoDecorridoMM", false, false, false, false));
		listFields.add(new Field("diagnostico", "diagnostico", false, false, false, false));
		listFields.add(new Field("idorigematendimento", "idOrigemAtendimento", false, false, false, false));
		listFields.add(new Field("idcontatoproblema", "idContatoProblema", false, false, false, false));
		listFields.add(new Field("acoescorretas", "acoesCorretas", false, false, false, false));
		listFields.add(new Field("acoesincorretas", "acoesIncorretas", false, false, false, false));
		listFields.add(new Field("melhoriasfuturas", "melhoriasFuturas", false, false, false, false));
		listFields.add(new Field("recorrenciaproblema", "recorrenciaProblema", false, false, false, false));
		listFields.add(new Field("responsabilidadeterceiros", "responsabilidadeTerceiros", false, false, false, false));
		listFields.add(new Field("acompanhamento", "acompanhamento", false, false, false, false));
		listFields.add(new Field("idcategoriasolucao", "idCategoriaSolucao", false, false, false, false));
		listFields.add(new Field("confirmasolucaocontorno", "confirmaSolucaoContorno", false, false, false, false));
		listFields.add(new Field("fechamento", "fechamento", false, false, false, false));
		listFields.add(new Field("idunidade", "idUnidade", false, false, false, false));
		listFields.add(new Field("grave", "grave", false, false, false, false));
		listFields.add(new Field("precisamudanca", "precisaMudanca", false, false, false, false));
		listFields.add(new Field("precisasolucaocontorno", "precisaSolucaoContorno", false, false, false, false));
		listFields.add(new Field("fase", "fase", false, false, false, false));
		listFields.add(new Field("resolvido", "resolvido", false, false, false, false));
		listFields.add(new Field("enviaemailprazosolucionarexpirou", "enviaEmailPrazoSolucionarExpirou", false, false, false, false));
		listFields.add(new Field("idCausa", "idCausa", false, false, false, false));
		listFields.add(new Field("fecharItensRelacionados", "fecharItensRelacionados", false, false, false, false));

		return listFields;
	}

	public void delecaoLogica(ProblemaDTO parm) throws Exception {
		parm.setDataHoraFim(UtilDatas.getDataHoraAtual());
		update(parm);
	}

	@Override
	public IDto restore(IDto obj) throws PersistenceException {
		ProblemaDTO problemaDto = (ProblemaDTO) obj;

		List condicao = new ArrayList();
		condicao.add(new Condition("dataHoraFim", "IS", null));
		condicao.add(new Condition("idProblema", "=", problemaDto.getIdProblema()));

		ArrayList<ProblemaDTO> p = (ArrayList<ProblemaDTO>) super.findByCondition(condicao, null);

		if (p != null && p.size() > 0) {
			return p.get(0);
		}

		return null;
	}

	public ProblemaDTO restoreAll(Integer idProblema) throws Exception {
		List parametro = new ArrayList();
		parametro.add(idProblema);

		String sql = getSQLRestoreAll();
		sql += "  WHERE pro.idProblema = ? ";

		List lista = this.execSQL(sql.toString(), parametro.toArray());

		if (lista != null && !lista.isEmpty()) {
			List listaResult = this.engine.listConvertion(ProblemaDTO.class, lista, getColunasRestoreAll());
			return (ProblemaDTO) listaResult.get(0);
		} else {
			return null;
		}
	}

	private String getSQLRestoreAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT pro.idProblema, pro.titulo, empcriadopor.nome, emppropri.nome, pro.status, ");
		sql.append("       pro.urgencia, pro.impacto, pro.severidade, serv.nomeServico, grp.nome, con.numero, ");
		sql.append("       pro.idPrioridade, pro.dataHoraLimite, pro.prazohh, pro.prazomm ");
		sql.append("FROM problema pro ");
		sql.append("        LEFT OUTER JOIN empregados empcriadopor on pro.idCriador = empcriadopor.idEmpregado ");
		sql.append("        LEFT OUTER JOIN servico serv on pro.idServico = serv.idServico ");
		sql.append("        LEFT OUTER JOIN grupo grp on pro.idGrupo = grp.idGrupo ");
		sql.append("        LEFT OUTER JOIN contratos con on con.idContrato = pro.idContrato ");
		sql.append("        LEFT OUTER JOIN empregados emppropri on pro.idProprietario = emppropri.idEmpregado ");
		return sql.toString();
	}

	private List getColunasRestoreAll() {
		List listRetorno = new ArrayList();
		listRetorno.add("idProblema");
		listRetorno.add("titulo");
		listRetorno.add("nomeCriador");
		listRetorno.add("nomeProprietario");
		listRetorno.add("status");
		listRetorno.add("urgencia");
		listRetorno.add("impacto");
		listRetorno.add("severidade");
		listRetorno.add("servico");
		listRetorno.add("grupo");
		listRetorno.add("contrato");
		listRetorno.add("idPrioridade");
		listRetorno.add("dataHoraLimite");
		listRetorno.add("prazoHH");
		listRetorno.add("prazoMM");
		return listRetorno;
	}

	public List<ProblemaDTO> listProblema(ProblemaDTO bean) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();

		String sql = "SELECT pro.idProblema, pro.titulo, con.numero, pro.datahoracaptura, us.nome, pro.idSolicitante, "
				+ " us1.login, pro.status, pro.prioridade, pro.urgencia, pro.impacto, pro.severidade, "
				+ " serv.nomeServico, grp.nome, pro.dataHoraLimite, pro.prazohh, pro.prazomm, pro.idServicoContrato, pro.SituacaoSLA, pro.idFaseAtual, fase.nomefase, fase.fasecaptura "
				+ " FROM problema pro " + " LEFT OUTER JOIN usuario us on pro.idProprietario = us.idUsuario " + " LEFT OUTER JOIN servico serv on pro.idServico = serv.idServico "
				+ " LEFT OUTER JOIN grupo grp on pro.idGrupo = grp.idGrupo " + " LEFT OUTER JOIN contratos con on con.idContrato = pro.idContrato "
				+ " LEFT OUTER JOIN usuario us1 on pro.idSolicitante = us1.idEmpregado " + " LEFT OUTER JOIN servicocontrato sc ON sc.idservicocontrato = pro.idservicocontrato "
				+ " LEFT OUTER JOIN faseservico fase ON fase.idfase = pro.idFaseAtual " + " WHERE pro.dataHoraFim is null ";

		if (bean != null) {
			if (bean.getIdProblema() != null) {
				sql += "and pro.idProblema = ? ";
				parametro.add(bean.getIdProblema());
			}

		}

		list = this.execSQL(sql, parametro.toArray());
		fields.add("idProblema");
		fields.add("titulo");
		fields.add("contrato");
		fields.add("dataHoraCaptura");
		fields.add("solicitante");
		fields.add("idSolicitante");
		fields.add("responsavel");
		fields.add("status");
		fields.add("prioridade");
		fields.add("urgencia");
		fields.add("impacto");
		fields.add("severidade");
		fields.add("servico");
		fields.add("grupo");
		fields.add("dataHoraLimite");
		fields.add("prazoHH");
		fields.add("prazoMM");
		fields.add("idServicoContrato");
		fields.add("situacaoSLA");
		fields.add("nomeFase");
		fields.add("faseCaptura");

		if (list != null && !list.isEmpty()) {
			return (List<ProblemaDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}
	}

	public List<ProblemaDTO> listProblemaByIdSolicitacao(ProblemaDTO bean) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = " select  pro.idProblema , pro.titulo, empcriadopor.nome, emppropri.nome, pro.status, pro.urgencia, pro.impacto, pro.severidade, sol.idSolicitacaoServico, ser.nomeServico  from problema pro "
				+ " inner join  empregados empcriadopor on pro.idcriador = empcriadopor.idempregado "
				+ " inner join  empregados emppropri on pro.idproprietario = emppropri.idempregado "
				+ " inner join solicitacaoservicoproblema solpro on solpro.idproblema = pro.idproblema "
				+ " inner join solicitacaoservico sol on sol.idsolicitacaoservico = solpro.idsolicitacaoservico "
				+ " inner join servicocontrato sercont on  sol.idservicocontrato =  sercont.idservicocontrato "
				+ " inner join servico ser on ser.idservico = sercont.idservico  where pro.dataHoraFim is null ";

		if (bean != null) {
			if (bean.getIdSolicitacaoServico() != null) {
				sql += "and sol.idSolicitacaoServico = ? ";
				parametro.add(bean.getIdSolicitacaoServico());
			}
		}

		if (bean != null) {
			if (bean.getIdProblema() != null) {
				sql += "and pro.idProblema = ? ";
				parametro.add(bean.getIdProblema());
			}
		}

		list = this.execSQL(sql, parametro.toArray());
		fields.add("idProblema");
		fields.add("titulo");
		fields.add("nomeCriador");
		fields.add("nomeProprietario");
		fields.add("status");
		fields.add("urgencia");
		fields.add("impacto");
		fields.add("severidade");
		fields.add("idSolicitacaoServico");
		fields.add("nomeServico");

		if (list != null && !list.isEmpty()) {
			return (List<ProblemaDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}
	}

	public String getTableName() {
		return this.getOwner() + "problema";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	public Class getBean() {
		return ProblemaDTO.class;
	}

	public Collection find(IDto problemaDTO) throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idProblema"));
		return super.find(problemaDTO, ordenacao);
	}

	public Collection findByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", parm));
		ordenacao.add(new Order("idProblema"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection<ProblemaDTO> findByIdProblemaPai(Integer idProblemaPai) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblemaPai", "=", idProblemaPai));
		return super.findByCondition(condicao, null);
	}

	public void deleteByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByStatus(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("status", "=", parm));
		ordenacao.add(new Order("status"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByStatus(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("status", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByPrioridade(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("prioridade", "=", parm));
		ordenacao.add(new Order("prioridade"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByPrioridade(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("prioridade", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdCriador(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idCriador", "=", parm));
		ordenacao.add(new Order("idCriador"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdCriador(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCriador", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdProprietario(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idProprietario", "=", parm));
		ordenacao.add(new Order("idProprietario"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdProprietario(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProprietario", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByTitulo(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("titulo", "=", parm));
		ordenacao.add(new Order("titulo"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByTitulo(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("titulo", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByDescricao(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("descricao", "=", parm));
		ordenacao.add(new Order("descricao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByDescricao(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("descricao", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdCategoriaProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idCategoriaProblema", "=", parm));
		ordenacao.add(new Order("idCategoriaProblema"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdCategoriaProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCategoriaProblema", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByImpacto(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("impacto", "=", parm));
		ordenacao.add(new Order("impacto"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByImpacto(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("impacto", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByUrgencia(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("urgencia", "=", parm));
		ordenacao.add(new Order("urgencia"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByUrgencia(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("urgencia", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByProativoReativo(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("proativoReativo", "=", parm));
		ordenacao.add(new Order("proativoReativo"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByProativoReativo(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("proativoReativo", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByMsgErroAssociada(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("msgErroAssociada", "=", parm));
		ordenacao.add(new Order("msgErroAssociada"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByMsgErroAssociada(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("msgErroAssociada", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdProblemaItemConfiguracao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idProblemaItemConfiguracao", "=", parm));
		ordenacao.add(new Order("idProblemaItemConfiguracao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdProblemaItemConfiguracao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblemaItemConfiguracao", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdErrosConhecidos(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idErrosConhecidos", "=", parm));
		ordenacao.add(new Order("idErrosConhecidos"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdErrosConhecidos(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idErrosConhecidos", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdProblemaMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idProblemaMudanca", "=", parm));
		ordenacao.add(new Order("idProblemaMudanca"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdProblemaMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblemaMudanca", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdProblemaIncidente(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idProblemaIncidente", "=", parm));
		ordenacao.add(new Order("idProblemaIncidente"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdProblemaIncidente(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblemaIncidente", "=", parm));
		super.deleteByCondition(condicao);
	}

	public List<ProblemaDTO> listProblemaByIdItemConfiguracao(Integer idItemConfiguracao) throws Exception {
		String sql = " Select pro.idProblema, pro.idsolicitante, pro.idproprietario, pro.titulo, pro.descricao, " + " pro.impacto, pro.urgencia, pro.proativoReativo, pro.severidade ," +
				"emp.nome, us.nome "
				+ " from problema pro " + " inner join problemaitemconfiguracao proitem on pro.idproblema = proitem.idproblema "
				+ " inner join itemconfiguracao item on item.iditemconfiguracao = proitem.iditemconfiguracao " +
				" inner join empregados emp on emp.idempregado = pro.idsolicitante " +
				" inner join usuario us on us.idusuario = pro.idproprietario " + " where item.iditemconfiguracao = ? " +
		"and (pro.status like 'SolucaoContornoDefinida' or pro.status like 'Investigacao' or  pro.status like 'Registrado' or  pro.status like 'Registrada') ";
		/**
		 * A regra relacionada ao status estava comentada
		 * @author flavio.santana
		 * 25/10/2013 14:00
		 */
		List parametro = new ArrayList();
		List<String> listRetorno = new ArrayList<String>();
		parametro.add(idItemConfiguracao);

		listRetorno.add("idProblema");
		listRetorno.add("idSolicitante");
		listRetorno.add("idProprietario");
		listRetorno.add("titulo");
		listRetorno.add("descricao");
		listRetorno.add("impacto");
		listRetorno.add("urgencia");
		listRetorno.add("proativoReativo");
		listRetorno.add("severidade");
		listRetorno.add("nomeProprietario");
		listRetorno.add("solicitante");


		List lista = this.execSQL(sql, parametro.toArray());
		return this.engine.listConvertion(ProblemaDTO.class, lista, listRetorno);
	}

	public Collection<ProblemaDTO> quantidadeProblemaPorBaseConhecimento(ProblemaDTO problema) throws Exception {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();

		sql.append("select idbaseconhecimento,count(idbaseconhecimento) from problema where idbaseconhecimento = ? group by idbaseconhecimento");

		parametro.add(problema.getIdBaseConhecimento());
		listRetorno.add("idBaseConhecimento");
		listRetorno.add("quantidade");

		List list = execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			Collection<ProblemaDTO> listaQuantidadeProblemaPorBaseConhecimento = this.listConvertion(ProblemaDTO.class, list, listRetorno);
			return listaQuantidadeProblemaPorBaseConhecimento;
		}
		return null;
	}

	public Collection<ProblemaDTO> listaProblemasPorBaseConhecimento(ProblemaDTO problema) throws Exception {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();

		sql.append("SELECT p.idBaseConhecimento, p.idproblema, p.titulo, p.datahorainicio, p.idproprietario, p.status, ");
		sql.append("p.descricao, p.causaraiz, p.solucaocontorno, p.solucaodefinitiva, e.nome ");
		sql.append("FROM problema p, empregados e WHERE p.idproprietario = e.idempregado and p.idbaseconhecimento = ? ");

		parametro.add(problema.getIdBaseConhecimento());

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("idProblema");
		listRetorno.add("titulo");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("idProprietario");
		listRetorno.add("status");
		listRetorno.add("descricao");
		listRetorno.add("causaRaiz");
		listRetorno.add("solucaoContorno");
		listRetorno.add("solucaoDefinitiva");
		listRetorno.add("nomeProprietario");

		List list = execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			Collection<ProblemaDTO> listaProblemaPorBaseConhecimento = this.listConvertion(ProblemaDTO.class, list, listRetorno);
			return listaProblemaPorBaseConhecimento;
		}

		return null;
	}

	/**
	 * Retorna Problemas associados ao conhecimento.
	 *
	 * @param baseConhecimentoDto
	 * @return
	 * @throws Exception
	 */
	public List<ProblemaDTO> findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select problema.idProblema, problema.titulo, problema.status, problema.urgencia, problema.impacto, problema.severidade from problema ");
		sql.append("inner join conhecimentoproblema on problema.idproblema = conhecimentoproblema.idproblema ");
		sql.append("where conhecimentoproblema.idbaseconhecimento = ? ");

		parametro.add(baseConhecimentoDto.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());

		fields.add("idProblema");
		fields.add("titulo");
		fields.add("status");
		fields.add("urgencia");
		fields.add("impacto");
		fields.add("severidade");

		if (list != null && !list.isEmpty()) {

			return (List<ProblemaDTO>) this.listConvertion(getBean(), list, fields);

		} else {

			return null;
		}
	}

	public void atualizaDataHoraCaptura(ProblemaDTO problemaDto) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE " + getTableName() + " SET dataHoraCaptura = ? WHERE idproblema = ?");
		Object[] params = { problemaDto.getDataHoraCaptura(), problemaDto.getIdProblema() };
		try {
			this.execUpdate(sql.toString(), params);
		} catch (PersistenceException e) {
			System.out.println("Problemas com atualização da tabela problema.");
			e.printStackTrace();
		}
	}

	// public Collection<ProblemaDTO> listProblemasFilhos() throws Exception {
	// List condicao = new ArrayList();
	// condicao.add(new Condition("idProblemaPai", "is not", null));
	// return super.findByCondition(condicao, null);
	// }

	public Collection<ProblemaDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefa) throws Exception {

		List listRetorno = new ArrayList();
		listRetorno.add("idProblema");
		listRetorno.add("titulo");
		listRetorno.add("contrato");
		listRetorno.add("dataHoraCaptura");
		listRetorno.add("nomeCriador");
		listRetorno.add("idSolicitante");
		listRetorno.add("nomeProprietario");
		listRetorno.add("status");
		listRetorno.add("idPrioridade");
		listRetorno.add("urgencia");
		listRetorno.add("impacto");
		listRetorno.add("severidade");
		listRetorno.add("servico");
		listRetorno.add("grupo");
		listRetorno.add("dataHoraLimite");
		listRetorno.add("prazoHH");
		listRetorno.add("prazoMM");
		listRetorno.add("idServicoContrato");
		listRetorno.add("idInstanciaFluxo");

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT pro.idProblema, pro.titulo, con.numero, pro.datahoracaptura, empcriadopor.nome, pro.idSolicitante, ");
		sql.append("       emppropri.nome, pro.status, pro.idPrioridade, pro.urgencia, pro.impacto, pro.severidade, ");
		sql.append("       serv.nomeServico, grp.nome, pro.dataHoraLimite, pro.prazohh, pro.prazomm, pro.idServicoContrato, ep.idInstanciaFluxo ");
		sql.append("FROM problema pro ");
		sql.append("        INNER JOIN  empregados empcriadopor on pro.idCriador = empcriadopor.idEmpregado ");
		sql.append("        LEFT OUTER JOIN servico serv on pro.idServico = serv.idServico ");
		sql.append("        LEFT OUTER JOIN grupo grp on pro.idGrupo = grp.idGrupo ");
		sql.append("        LEFT OUTER JOIN contratos con on con.idContrato = pro.idContrato ");
		sql.append("        INNER JOIN  empregados emppropri on pro.idProprietario = emppropri.idEmpregado ");
		sql.append("        LEFT JOIN servicocontrato sc ON sc.idservicocontrato = pro.idservicocontrato ");
		sql.append(" 		INNER JOIN execucaoproblema ep ON ep.idProblema = pro.idProblema ");
		sql.append("        WHERE pro.dataHoraFim is null ");

		if (listTarefa != null && !listTarefa.isEmpty()) {
			// Hack oracle
			if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE))
				sql.append("and ep.idInstanciaFluxo in ( select * from table(sys.odcinumberlist( ");
			else
				sql.append("and ep.idInstanciaFluxo in  ( ");

			int size = listTarefa.size();
			int max = 1;
			int aux = 0;
			int CONSTANT = 999;
			// Hack oracle
			if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
				for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
					aux += 1;
					sql.append(tarefaFluxoDto.getIdInstancia());

					if (aux == size) {
						sql.append(")))");
					} else if (aux > (CONSTANT * (max - 1)) && aux <= (CONSTANT * max)) {
						sql.append("))  union  select * from table(sys.odcinumberlist( ");
						max++;
					} else {
						sql.append(",");
					}
				}
			} else {
				for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
					aux += 1;
					sql.append(tarefaFluxoDto.getIdInstancia());

					if (aux == size) {
						sql.append(")");
					} else {
						sql.append(",");
					}
				}

			}

		} else {
			return null;
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), null);

		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(ProblemaDTO.class, lista, listRetorno);
		} else {
			return null;
		}
	}

	public ProblemaDTO restoreByIdInstanciaFluxo(Integer idInstanciaFluxo) throws Exception {
		List parametro = new ArrayList();
		parametro.add(idInstanciaFluxo);

		String sql = getSQLRestoreAll();
		sql += " INNER JOIN execucaosolicitacao ep ON ep.idProblema = pro.idProblema ";
		sql += " WHERE pro.idProblemaPai is null and ep.idInstanciaFluxo = ? ";

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());

		if (lista != null && !lista.isEmpty()) {
			List listaResult = this.engine.listConvertion(ProblemaDTO.class, lista, getColunasRestoreAll());
			return (ProblemaDTO) listaResult.get(0);
		} else {
			return null;
		}
	}

	public void updateFase(Integer idProblema, String fase) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE " + getTableName() + " SET fase = ? WHERE (idProblema = ?)");
		Object[] params = { fase, idProblema };
		try {
			this.execUpdate(sql.toString(), params);
		} catch (PersistenceException e) {
			System.out.println("Problemas com atualização da requisição de mudança.");
			e.printStackTrace();
		}
	}

	/**
	 * Método responsável por efetuar a contagem de problemas com prazo (data e hora) limite de c,ontorno expirado. A contagem é feita por usuário proprietário do problema. Caso existão um ou mais
	 * registros de problema com o prazo limite expirado o usuário proprietário e o grupo de interessados (grupo executor) deverá ser notificado por e-mail.
	 *
	 * @author thiago.monteiro (Thiago Alexandre Martins Monteiro)
	 * @param UsuarioDTO
	 *            - usuarioDTO
	 * @return int
	 * @throws Exception
	 */
	public int contarProblemasComPrazoLimiteContornoExpirado(UsuarioDTO usuarioDTO) throws Exception {

		int quantidadeRegistros = 0;

		if (usuarioDTO != null && usuarioDTO.getIdUsuario() != null) {

			StringBuilder sql = new StringBuilder();

			sql.append("SELECT\n");
			sql.append("    COUNT(datahoralimite) AS quantidade\n");
			sql.append("FROM\n");
			sql.append("    problema,\n");
			sql.append("    usuario AS u\n");
			sql.append("WHERE\n");
			sql.append("    datahoralimite IS NOT NULL AND\n");
			sql.append("    idproprietario = u.idusuario AND\n");
			sql.append("    idproprietario = ?");

			List parametros = new ArrayList();

			parametros.add(usuarioDTO.getIdUsuario());

			try {

				// Executando a consulta SQL.
				List resultado = this.execSQL(sql.toString(), parametros.toArray());

				// Verificando se o resultado existe.
				if (resultado != null && !resultado.isEmpty()) {

					List colunas_retornadas = new ArrayList();

					colunas_retornadas.add("quantidade");

					// Convertendo a lista para o DTO apropriado.
					// d=p diz: O atributo quantidade e os metodos publicos de acesso já são criados automaticamente pelo framework.
					resultado = this.listConvertion(ProblemaDTO.class, resultado, colunas_retornadas);

					if (resultado != null && !resultado.isEmpty()) {

						quantidadeRegistros = ((ProblemaDTO) resultado.get(0)).getQuantidade();

					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return quantidadeRegistros;
	}

	/**
	 * Retorna uma lista de problemas com prazo limite expeirado.
	 *
	 * @param UsuarioDTO
	 *            - usuarioDTO
	 * @return Collection
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<ProblemaDTO> listaProblemasComPrazoLimiteContornoExpirado(UsuarioDTO usuarioDTO) throws Exception {

		List resultado = new ArrayList();

		List colunas_retornadas = new ArrayList();

		List parametros = new ArrayList();



			StringBuilder sql = new StringBuilder();

			sql.append("SELECT idproblema,idgrupo ");
			sql.append("FROM\n");
			sql.append("    problema,\n");
			sql.append("    usuario AS u\n");
			sql.append("WHERE\n");
			sql.append("    datahoralimite IS NOT NULL AND\n");
			sql.append("    idproprietario = u.idusuario AND\n");
			sql.append(" problema.status != ?  and problema.status != ?");
			parametros.add(SituacaoRequisicaoProblema.Cancelada.getDescricao());
			parametros.add(SituacaoRequisicaoProblema.Concluida.getDescricao());
			if (usuarioDTO != null && usuarioDTO.getIdUsuario() != null) {
				sql.append("  and  idproprietario = ?  ");
				parametros.add(usuarioDTO.getIdUsuario());
			}

			resultado = this.execSQL(sql.toString(), parametros.toArray());

			colunas_retornadas.add("idProblema");
			colunas_retornadas.add("idGrupo");

			if (resultado != null && !resultado.isEmpty()) {

				resultado = this.listConvertion(ProblemaDTO.class, resultado, colunas_retornadas);

				return resultado;

			}else{
				return resultado = null;
			}



	}

	/**
	 * @author geber.costa
	 * @param pesquisaProblemaDto
	 * @return
	 * @throws Exception
	 */
	public Collection<PesquisaProblemaDTO> listProblemaByCriterios(PesquisaProblemaDTO pesquisaProblemaDto) throws Exception {

		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();


		sql.append("SELECT  problema.idproblema, categoriaproblema.nomecategoriaproblema,problema.status,problema.titulo, ");
		sql.append("problema.dataHoraCaptura,problema.datahorafim, problema.descricao, ");
		sql.append("proprietario.nome, solicitante.nome, grupo.nome ");
		sql.append("FROM " + getTableName() + " ");
		sql.append("left join usuario as proprietario on proprietario.idusuario = problema.idproprietario ");
		sql.append("inner join empregados as solicitante on solicitante.idempregado = problema.idsolicitante ");
		sql.append("left join grupo on grupo.idgrupo = problema.idgrupo ");
		sql.append("inner join categoriaproblema on categoriaproblema.idcategoriaproblema = problema.idcategoriaproblema ");
		sql.append("left join problemaitemconfiguracao on problemaitemconfiguracao.idproblema = problema.idproblema");

		if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)){
			if (pesquisaProblemaDto.getDataInicio() != null) {
				sql.append(" where problema.datahorainicio BETWEEN  ?  and ? ");

				parametro.add(pesquisaProblemaDto.getDataInicio() + " 00:00:00");
				parametro.add(pesquisaProblemaDto.getDataFim() + " 23:59:59");

			}
		}else{
			if (pesquisaProblemaDto.getDataInicio() != null) {
				// Adaptação realizada passando o timestamp concatenado no
				// próprio sqp para poder colocar aspas simples na pesquisa
				sql.append(" where problema.datahorainicio BETWEEN TO_DATE('"
						+ UtilDatas.dateToSTR(pesquisaProblemaDto.getDataInicio())
						+ "', 'DD/MM/YYYY ')  and TO_DATE('"
						+ UtilDatas.dateToSTR(pesquisaProblemaDto.getDataFim())
						+ "', 'DD/MM/YYYY ') ");

			}
		}

		if (pesquisaProblemaDto.getIdProblema() != null) {
			if(pesquisaProblemaDto.getDataInicio()==null){
				sql.append(" where problema.idproblema = ?");
				parametro.add(pesquisaProblemaDto.getIdProblema());
			}else{
				sql.append(" and problema.idproblema = ?");
				parametro.add(pesquisaProblemaDto.getIdProblema());
			}
		}
		if (pesquisaProblemaDto.getStatus() != null && !pesquisaProblemaDto.getStatus().equalsIgnoreCase("")) {
			sql.append(" and problema.status = ?");
			parametro.add(pesquisaProblemaDto.getStatus());
		}

		if (pesquisaProblemaDto.getIdSolicitante() != null) {
			sql.append(" and problema.idsolicitante = ? ");
			parametro.add(pesquisaProblemaDto.getIdSolicitante());
		}

		if (pesquisaProblemaDto.getIdProprietario() != null) {
			sql.append(" and problema.idproprietario = ?");
			parametro.add(pesquisaProblemaDto.getIdProprietario());
		}

		if (pesquisaProblemaDto.getIdGrupoAtual() != null) {
			sql.append(" and problema.idgrupo= ?");
			parametro.add(pesquisaProblemaDto.getIdGrupoAtual());
		}

		if (pesquisaProblemaDto.getIdItemConfiguracao() != null) {
			sql.append(" and problemaitemconfiguracao.iditemconfiguracao = ?");
			parametro.add(pesquisaProblemaDto.getIdItemConfiguracao());
		}

		if(pesquisaProblemaDto.getIdCalendario()!= null){
			sql.append(" and tipomudanca.idtipomudanca = ?");
			parametro.add(pesquisaProblemaDto.getIdCalendario());
		}

		if(pesquisaProblemaDto.getIdCategoriaProblema() != null && pesquisaProblemaDto.getIdCategoriaProblema().intValue()>0){
		//if(pesquisaProblemaDto.getNomeCategoriaProblema()!=null && !pesquisaProblemaDto.getNomeCategoriaProblema().equalsIgnoreCase("")){
			//sql.append(" and categoriaproblema.nomecategoria = ?");
			sql.append(" and categoriaproblema.idcategoriaproblema = ?");
			//parametro.add(pesquisaProblemaDto.getNomeCategoriaProblema());
			parametro.add(pesquisaProblemaDto.getIdCategoriaProblema());
		//}
		}

		if (pesquisaProblemaDto.getOrdenacao() != null) {

			sql.append(" ORDER BY " + pesquisaProblemaDto.getOrdenacao()+"");

		}

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idProblema");
		listRetorno.add("categoria");
		listRetorno.add("status");
		listRetorno.add("titulo");
		listRetorno.add("dataHoraCaptura");
		listRetorno.add("dataHoraFim");
		listRetorno.add("descricao");
		listRetorno.add("nomeProprietario");
		listRetorno.add("nomeSolicitante");
		listRetorno.add("nomeGrupoAtual");

		if (list != null && !list.isEmpty()) {

			Collection<PesquisaProblemaDTO> listProblemaoMudancaByCriterios = this.listConvertion(PesquisaProblemaDTO.class, list, listRetorno);

			return listProblemaoMudancaByCriterios;

		}

		return null;

	}

//	public Collection<RelatorioProblemaIncidentesDTO> listProblemaIncidentes(RelatorioProblemaIncidentesDTO problemasIncidentes) throws Exception {
//
//		List parametro = new ArrayList();
//		List listRetorno = new ArrayList();
//		List list = new ArrayList();
//		StringBuilder sql = new StringBuilder();
//
//		sql.append("select distinct ssp.idproblema, pr.titulo ");
//		sql.append("from solicitacaoservicoproblema ssp ");
//		sql.append("inner join problema pr on ssp.idproblema = pr.idproblema ");
//
//
//		if (problemasIncidentes.getDataInicio() != null) {
//			sql.append(" where problema.datahorainicio BETWEEN  ?  and ? ");
//
//			parametro.add(problemasIncidentes.getDataInicio() + " 00:00:00");
//			parametro.add(problemasIncidentes.getDataFim() + " 23:59:59");
//
//		}
//
//		if (problemasIncidentes.getIdProblema() != null) {
//			if(problemasIncidentes.getDataInicio()==null){
//				sql.append(" where problema.idproblema = ?");
//				parametro.add(problemasIncidentes.getIdProblema());
//			}else{
//				sql.append(" and problema.idproblema = ?");
//				parametro.add(problemasIncidentes.getIdProblema());
//			}
//		}
//
//		sql.append(" ORDER BY ssp.idproblema asc ");
//
//
//		list = this.execSQL(sql.toString(), parametro.toArray());
//
//		listRetorno.add("idProblema");
//		listRetorno.add("titulo");
//
//		if (list != null && !list.isEmpty()) {
//
//			Collection<RelatorioProblemaIncidentesDTO> listProblemaoMudancaByCriterios = this.listConvertion(RelatorioProblemaIncidentesDTO.class, list, listRetorno);
//
//			return listProblemaoMudancaByCriterios;
//
//		}
//
//		return null;
//
//	}
	public Collection<RelatorioProblemaIncidentesDTO> listProblemaIncidentes(RelatorioProblemaIncidentesDTO problemasIncidentes) throws Exception {

		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select distinct ssp.idproblema, pr.titulo ,pr.descricao ");
		sql.append("from solicitacaoservicoproblema ssp ");
		sql.append("inner join problema pr on ssp.idproblema = pr.idproblema ");

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)){
			if (problemasIncidentes.getDataInicio() != null && problemasIncidentes.getDataFim() != null) {
				sql.append(" where pr.datahorainicio BETWEEN  ?  and ? ");

				parametro.add(problemasIncidentes.getDataInicio() + " 00:00:00");
				parametro.add(problemasIncidentes.getDataFim() + " 23:59:59");

			}
		}else{
			if (problemasIncidentes.getDataInicio() != null) {
				// Adaptação realizada passando o timestamp concatenado no
				// próprio sqp para poder colocar aspas simples na pesquisa
				sql.append(" where pr.datahorainicio BETWEEN TO_DATE('"
						+ UtilDatas.dateToSTR(problemasIncidentes.getDataInicio())
						+ "', 'DD/MM/YYYY ')  and TO_DATE('"
						+ UtilDatas.dateToSTR(problemasIncidentes.getDataFim())
						+ "', 'DD/MM/YYYY ') ");
				}
		}
		if (problemasIncidentes.getIdProblema() != null) {
			if(problemasIncidentes.getDataInicio()==null){
				sql.append(" where pr.idproblema = ?");
				parametro.add(problemasIncidentes.getIdProblema());
			}else{
				sql.append(" and pr.idproblema = ?");
				parametro.add(problemasIncidentes.getIdProblema());
			}
		}

		if(problemasIncidentes.getIdContrato() != null){
			sql.append(" and pr.idcontrato = ?");
			parametro.add(problemasIncidentes.getIdContrato());
		} else { //Se a opção for "Todos", seleciona somente os contratos que não estão deletados
			sql.append(" and pr.idcontrato not in (select idcontrato from contratos where deleted = 'Y')");
		}

		sql.append(" ORDER BY ssp.idproblema asc ");


		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idProblema");
		listRetorno.add("titulo");
		listRetorno.add("descricao");

		if (list != null && !list.isEmpty()) {

			Collection<RelatorioProblemaIncidentesDTO> listProblemaoMudancaByCriterios = this.listConvertion(RelatorioProblemaIncidentesDTO.class, list, listRetorno);

			return listProblemaoMudancaByCriterios;

		}

		return null;

	}

	public IDto restoreTela(IDto obj) throws Exception {
		ProblemaDTO problemaDto = (ProblemaDTO) obj;

		List condicao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", problemaDto.getIdProblema()));

		ArrayList<ProblemaDTO> p = (ArrayList<ProblemaDTO>) super.findByCondition(condicao, null);

		if (p != null && p.size() > 0) {
			return p.get(0);
		}
		return null;

	}
		public IDto restauraTodos(IDto obj) throws Exception {
		ProblemaDTO problemaDto = (ProblemaDTO) obj;

		List condicao = new ArrayList();
		//condicao.add(new Condition("dataHoraFim", "IS", null));
		condicao.add(new Condition("idProblema", "=", problemaDto.getIdProblema()));

		ArrayList<ProblemaDTO> p = (ArrayList<ProblemaDTO>) super.findByCondition(condicao, null);

		if (p != null && p.size() > 0) {
			return p.get(0);
		}

		return null;
	}

	/**
	 * Retorna uma lista quantitativa de problema por situação
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorSituacao(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select problema.status, count(*) ");
		sql.append("from " + getTableName() + " problema ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("where to_char(problema.datahorainicio, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		} else {
			sql.append("where problema.datahorainicio BETWEEN ? AND ? ");
		}


		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataInicio()));
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataFim()));
		} else {
			parametro.add(relatorioQuantitativoProblemaDto.getDataInicio());
			parametro.add(transformaHoraFinal(relatorioQuantitativoProblemaDto.getDataFim()));
		}

		if(relatorioQuantitativoProblemaDto.getIdContrato()!=null){
			sql.append(" and problema.idcontrato = ? ");
			parametro.add(relatorioQuantitativoProblemaDto.getIdContrato());
		}

		sql.append("group by problema.status");

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("situacao");
		listRetorno.add("quantidadeSituacao");
		if (lista != null && !lista.isEmpty()) {
			List listaQuantidadeProblemaPorSituacao = new ArrayList();
			listaQuantidadeProblemaPorSituacao = this.engine.listConvertion(RelatorioQuantitativoProblemaDTO.class, lista, listRetorno);
			return listaQuantidadeProblemaPorSituacao;
		}
		return null;
	}

	/**
	 * Retorna uma lista quantitativa de problema por grupo
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorGrupo(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select  grupo.sigla, count(*) ");
		sql.append("from " + getTableName() + " problema ");
		sql.append("inner join grupo grupo  on grupo.idgrupo = problema.idgrupo  ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("where to_char(problema.datahorainicio, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		} else {
			sql.append("where problema.datahorainicio BETWEEN ? AND ? ");
		}


		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataInicio()));
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataFim()));
		} else {
			parametro.add(relatorioQuantitativoProblemaDto.getDataInicio());
			parametro.add(transformaHoraFinal(relatorioQuantitativoProblemaDto.getDataFim()));
		}

		if(relatorioQuantitativoProblemaDto.getIdContrato()!=null){
			sql.append(" and problema.idcontrato = ? ");
			parametro.add(relatorioQuantitativoProblemaDto.getIdContrato());
		}

		sql.append("group by grupo.sigla ");

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("grupo");
		listRetorno.add("quantidadeGrupo");
		if (lista != null && !lista.isEmpty()) {
			List listaQuantidadeProblemaPorGrupo = new ArrayList();
			listaQuantidadeProblemaPorGrupo = this.engine.listConvertion(RelatorioQuantitativoProblemaDTO.class, lista, listRetorno);
			return listaQuantidadeProblemaPorGrupo;
		}
		return null;
	}

	/**
	 * Retorna uma lista quantitativa de problema por origem
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorOrigem(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select  origem.descricao, count(*) ");
		sql.append("from " + getTableName() + " problema ");
		sql.append("inner join origematendimento origem   on problema.idorigematendimento = origem.idorigem ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("where to_char(problema.datahorainicio, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		} else {
			sql.append("where problema.datahorainicio BETWEEN ? AND ? ");
		}


		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataInicio()));
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataFim()));
		} else {
			parametro.add(relatorioQuantitativoProblemaDto.getDataInicio());
			parametro.add(transformaHoraFinal(relatorioQuantitativoProblemaDto.getDataFim()));
		}

		if(relatorioQuantitativoProblemaDto.getIdContrato()!=null){
			sql.append(" and problema.idcontrato = ? ");
			parametro.add(relatorioQuantitativoProblemaDto.getIdContrato());
		}

		sql.append("group by origem.descricao ");

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("origem");
		listRetorno.add("quantidadeOrigem");
		if (lista != null && !lista.isEmpty()) {
			List listaQuantidadeProblemaPorOrigem = new ArrayList();
			listaQuantidadeProblemaPorOrigem = this.engine.listConvertion(RelatorioQuantitativoProblemaDTO.class, lista, listRetorno);
			return listaQuantidadeProblemaPorOrigem;
		}
		return null;
	}

	/**
	 * Retorna uma lista quantitativa de problema por solicitante
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorSolicitante(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select  ltrim(empregado.nome), count(*) ");
		sql.append("from " + getTableName() + " problema ");
		sql.append("inner join empregados empregado on empregado.idempregado = problema.idsolicitante ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("where to_char(problema.datahorainicio, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		} else {
			sql.append("where problema.datahorainicio BETWEEN ? AND ? ");
		}


		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataInicio()));
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataFim()));
		} else {
			parametro.add(relatorioQuantitativoProblemaDto.getDataInicio());
			parametro.add(transformaHoraFinal(relatorioQuantitativoProblemaDto.getDataFim()));
		}

		if(relatorioQuantitativoProblemaDto.getIdContrato()!=null){
			sql.append(" and problema.idcontrato = ? ");
			parametro.add(relatorioQuantitativoProblemaDto.getIdContrato());
		}

		sql.append(" group by empregado.nome order by ltrim(empregado.nome) ");

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("solicitante");
		listRetorno.add("quantidadeSolicitante");
		if (lista != null && !lista.isEmpty()) {
			List listaQuantidadeProblemaPorSolicitante = new ArrayList();
			listaQuantidadeProblemaPorSolicitante = this.engine.listConvertion(RelatorioQuantitativoProblemaDTO.class, lista, listRetorno);
			return listaQuantidadeProblemaPorSolicitante;
		}
		return null;
	}

	/**
	 * Retorna uma lista quantitativa de problema por prioridade
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorPrioridade(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select  prioridade.nomeprioridade, count(*) ");
		sql.append("from " + getTableName() + " problema ");
		sql.append("inner join prioridade prioridade   on prioridade.idprioridade = problema.prioridade ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("where to_char(problema.datahorainicio, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		} else {
			sql.append("where problema.datahorainicio BETWEEN ? AND ? ");
		}


		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataInicio()));
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataFim()));
		} else {
			parametro.add(relatorioQuantitativoProblemaDto.getDataInicio());
			parametro.add(transformaHoraFinal(relatorioQuantitativoProblemaDto.getDataFim()));
		}

		if(relatorioQuantitativoProblemaDto.getIdContrato()!=null){
			sql.append(" and problema.idcontrato = ? ");
			parametro.add(relatorioQuantitativoProblemaDto.getIdContrato());
		}

		sql.append("group by prioridade.nomeprioridade ");

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("prioridade");
		listRetorno.add("quantidadePrioridade");
		if (lista != null && !lista.isEmpty()) {
			List listaQuantidadeProblemaPorPrioridade = new ArrayList();
			listaQuantidadeProblemaPorPrioridade = this.engine.listConvertion(RelatorioQuantitativoProblemaDTO.class, lista, listRetorno);
			return listaQuantidadeProblemaPorPrioridade;
		}
		return null;
	}

	/**
	 * Retorna uma lista quantitativa de problema por categoria problema
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorCategoria(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select  categoriaproblema.nomecategoriaproblema, count(*) ");
		sql.append("from " + getTableName() + " problema ");
		sql.append("inner join categoriaproblema categoriaproblema   on categoriaproblema.idcategoriaproblema = problema.idcategoriaproblema ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("where to_char(problema.datahorainicio, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		} else {
			sql.append("where problema.datahorainicio BETWEEN ? AND ? ");
		}


		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataInicio()));
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataFim()));
		} else {
			parametro.add(relatorioQuantitativoProblemaDto.getDataInicio());
			parametro.add(transformaHoraFinal(relatorioQuantitativoProblemaDto.getDataFim()));
		}

		if(relatorioQuantitativoProblemaDto.getIdContrato()!=null){
			sql.append(" and problema.idcontrato = ? ");
			parametro.add(relatorioQuantitativoProblemaDto.getIdContrato());
		}

		sql.append("group by categoriaproblema.nomecategoriaproblema ");

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("categoriaProblema");
		listRetorno.add("quantidadeCategoriaProblema");
		if (lista != null && !lista.isEmpty()) {
			List listaQuantidadeProblemaPorPrioridade = new ArrayList();
			listaQuantidadeProblemaPorPrioridade = this.engine.listConvertion(RelatorioQuantitativoProblemaDTO.class, lista, listRetorno);
			return listaQuantidadeProblemaPorPrioridade;
		}
		return null;
	}

	/**
	 * Retorna uma lista quantitativa de problema por proprietario
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorProprietario(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select  ltrim(usuario.nome), count(*) ");
		sql.append("from " + getTableName() + " problema ");
		sql.append("inner join usuario usuario on  usuario.idusuario = problema.idproprietario ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("where to_char(problema.datahorainicio, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		} else {
			sql.append("where problema.datahorainicio BETWEEN ? AND ? ");
		}


		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataInicio()));
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataFim()));
		} else {
			parametro.add(relatorioQuantitativoProblemaDto.getDataInicio());
			parametro.add(transformaHoraFinal(relatorioQuantitativoProblemaDto.getDataFim()));
		}

		if(relatorioQuantitativoProblemaDto.getIdContrato()!=null){
			sql.append(" and problema.idcontrato = ? ");
			parametro.add(relatorioQuantitativoProblemaDto.getIdContrato());
		}

		sql.append(" group by usuario.nome order by ltrim(usuario.nome) ");

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("proprietario");
		listRetorno.add("quantidadeProprietario");
		if (lista != null && !lista.isEmpty()) {
			List listaQuantidadeProblemaPorProprietario = new ArrayList();
			listaQuantidadeProblemaPorProprietario = this.engine.listConvertion(RelatorioQuantitativoProblemaDTO.class, lista, listRetorno);
			return listaQuantidadeProblemaPorProprietario;
		}
		return null;
	}

	/**
	 * Retorna uma lista quantitativa de problema por impacto
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorImpacto(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select  CASE WHEN problema.impacto = 'B' then 'Baixo'  ");
		sql.append("WHEN problema.impacto = 'M' then 'Medio' ");
		sql.append("WHEN problema.impacto = 'A' then 'Alto' ");
		sql.append("else problema.impacto end as impacto, count(*) ");
		sql.append("from " + getTableName() + " problema ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("where to_char(problema.datahorainicio, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		} else {
			sql.append("where problema.datahorainicio BETWEEN ? AND ? ");
		}


		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataInicio()));
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataFim()));
		} else {
			parametro.add(relatorioQuantitativoProblemaDto.getDataInicio());
			parametro.add(transformaHoraFinal(relatorioQuantitativoProblemaDto.getDataFim()));
		}

		if(relatorioQuantitativoProblemaDto.getIdContrato()!=null){
			sql.append(" and problema.idcontrato = ? ");
			parametro.add(relatorioQuantitativoProblemaDto.getIdContrato());
		}

		sql.append("group by problema.impacto");

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("impacto");
		listRetorno.add("quantidadeImpacto");
		if (lista != null && !lista.isEmpty()) {
			List listaQuantidadeProblemaPorImpacto = new ArrayList();
			listaQuantidadeProblemaPorImpacto = this.engine.listConvertion(RelatorioQuantitativoProblemaDTO.class, lista, listRetorno);
			return listaQuantidadeProblemaPorImpacto;
		}
		return null;
	}

	/**
	 * Retorna uma lista quantitativa de problema por urgencia
	 * @param relatorioQuantitativoProblemaDto
	 * @return Collection<RelatorioQuantitativoProblemaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<RelatorioQuantitativoProblemaDTO> listaQuantidadeProblemaPorUrgencia(RelatorioQuantitativoProblemaDTO relatorioQuantitativoProblemaDto) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select  CASE WHEN problema.urgencia = 'B' then 'Baixo'  ");
		sql.append("WHEN problema.urgencia = 'M' then 'Medio' ");
		sql.append("WHEN problema.urgencia = 'A' then 'Alto' ");
		sql.append("else problema.urgencia end as urgencia , count(*) ");
		sql.append("from " + getTableName() + " problema ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("where to_char(problema.datahorainicio, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		} else {
			sql.append("where problema.datahorainicio BETWEEN ? AND ? ");
		}


		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataInicio()));
			parametro.add(formatter.format(relatorioQuantitativoProblemaDto.getDataFim()));
		} else {
			parametro.add(relatorioQuantitativoProblemaDto.getDataInicio());
			parametro.add(transformaHoraFinal(relatorioQuantitativoProblemaDto.getDataFim()));
		}

		if(relatorioQuantitativoProblemaDto.getIdContrato()!=null){
			sql.append(" and problema.idcontrato = ? ");
			parametro.add(relatorioQuantitativoProblemaDto.getIdContrato());
		}

		sql.append("group by problema.urgencia");

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("urgencia");
		listRetorno.add("quantidadeUrgencia");
		if (lista != null && !lista.isEmpty()) {
			List listaQuantidadeProblemaPorUrgencia = new ArrayList();
			listaQuantidadeProblemaPorUrgencia = this.engine.listConvertion(RelatorioQuantitativoProblemaDTO.class, lista, listRetorno);
			return listaQuantidadeProblemaPorUrgencia;
		}
		return null;
	}


	private Timestamp transformaHoraFinal(Date data) throws ParseException {
		String dataHora = data + " 23:59:59";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date d = sdf.parse(dataHora);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
		return sqlDate;
	}

	public Collection listSolicitacoesByRegra() throws Exception {
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct (s.idproblema), "
		 + " s.prioridade, s.datahoralimite, s.datahorasolicitacao,  regra1.idcontrato as idcontrato, regra3.idsolicitante as idsolicitante,  "
		 + " regra4.idgrupo as idgrupo, s.vencendo, s.datahorainicio, s.prazohh, s.prazomm, s.impacto, s.urgencia  "
		 + " FROM problema s   "
		 + " INNER JOIN contratos contratos on contratos.idcontrato = s.idcontrato "
		 + " INNER JOIN empregados solicitante on solicitante.idempregado = s.idsolicitante "
		 + " LEFT JOIN grupo grupo on grupo.idgrupo = s.idgrupo "
		 + " LEFT JOIN regraescalonamento regra1 on regra1.idcontrato = contratos.idcontrato "
		 + " LEFT JOIN regraescalonamento regra3 on regra3.idsolicitante = solicitante.idempregado "
		 + " LEFT JOIN regraescalonamento regra4 on regra4.idgrupo = grupo.idgrupo  "
		 + " WHERE UPPER(s.status) not in ('FECHADA', 'CANCELADA', 'RESOLVIDA', 'SUSPENSA', 'CONCLUIDA') ");

		listRetorno.add("idProblema");
		listRetorno.add("prioridade");
		listRetorno.add("dataHoraLimite");
		listRetorno.add("dataHoraSolicitacao");
		listRetorno.add("idContrato");
		listRetorno.add("idSolicitante");
		listRetorno.add("idGrupo");
		listRetorno.add("vencendo");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("prazoHH");
		listRetorno.add("prazoMM");
		listRetorno.add("impacto");
		listRetorno.add("urgencia");

		List list = this.execSQL(sql.toString(), null);
		if (list != null && !list.isEmpty()) {
			return (Collection<ProblemaDTO>) this.listConvertion(getBean(), list, listRetorno);
		} else {
			return null;
		}
	}

}
