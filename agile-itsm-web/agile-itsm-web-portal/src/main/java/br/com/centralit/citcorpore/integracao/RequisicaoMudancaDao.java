package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PesquisaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequisicaoMudancaDao extends CrudDaoDefaultImpl {

	public RequisicaoMudancaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idrequisicaomudanca", "idRequisicaoMudanca", true, true, false, false));
		listFields.add(new Field("idproprietario", "idProprietario", false, false, false, false));
		listFields.add(new Field("idsolicitante", "idSolicitante", false, false, false, false));
		// /listFields.add(new Field("tipo", "tipo", false, false, false, false));
		// listFields.add(new Field("idcategoriamudanca", "idCategoriaMudanca", false, false, false, false));
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
		listFields.add(new Field("idcategoriasolucao", "idCategoriaSolucao", false, false, false, false));
		listFields.add(new Field("fecharItensRelacionados", "fecharItensRelacionados", false, false, false, false));
		listFields.add(new Field("vencendo", "vencendo", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "requisicaomudanca";
	}

	public Class getBean() {
		return RequisicaoMudancaDTO.class;
	}

	public Collection find(IDto solicitacaoServicoDTO) throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idRequisicaoMudanca"));
		return super.find(solicitacaoServicoDTO, ordenacao);
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	@Override
	public Collection list() throws PersistenceException {
		return super.list("idRequisicaoMudanca");
	}

	public void updateFase(Integer idRequisicao, String fase) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE " + getTableName() + " SET fase = ? WHERE (idrequisicaomudanca = ?)");
		Object[] params = { fase, idRequisicao };
		try {
			this.execUpdate(sql.toString(), params);
		} catch (PersistenceException e) {
			System.out.println("Problemas com atualização da requisição de mudança.");
			e.printStackTrace();
		}
	}

	public List<RequisicaoMudancaDTO> listMudancaByIdSolicitacao(RequisicaoMudancaDTO bean) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append(" select distinct  sol.idSolicitacaoServico, ser.nomeServico  from requisicaomudanca  mud ");
		sql.append(" inner join solicitacaoservicomudanca solmud on solmud.idrequisicaoMudanca = mud.idrequisicaoMudanca ");
		sql.append(" inner join solicitacaoservico sol on sol.idsolicitacaoservico = solmud.idsolicitacaoservico ");
		sql.append(" inner join servicocontrato sercont on  sol.idservicocontrato =  sercont.idservicocontrato ");
		sql.append(" inner join servico ser on ser.idservico = sercont.idservico ");

		if (bean != null) {
			if (bean.getIdSolicitacaoServico() != null) {
				sql.append("and sol.idSolicitacaoServico = ? ");
				parametro.add(bean.getIdSolicitacaoServico());
			}
		}

		if (bean != null) {
			if (bean.getIdRequisicaoMudanca() != null) {
				sql.append("and mud.idrequisicaomudanca = ? ");
				parametro.add(bean.getIdRequisicaoMudanca());
			}
		}

		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idSolicitacaoServico");
		fields.add("nomeServico");

		if (list != null && !list.isEmpty()) {
			return (List<RequisicaoMudancaDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}
	}

	public Collection findByIdRequisicaoMudancaEDataFim(Integer idRequisicaoMudanca) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql =  new StringBuilder();
		sql.append("select mud.idrequisicaomudanca,  sol.idSolicitacaoServico, ser.nomeServico ");
		sql.append("from requisicaomudanca  mud ");
		sql.append("inner join solicitacaoservicomudanca solmud on solmud.idrequisicaoMudanca = mud.idrequisicaoMudanca ");
		sql.append("inner join solicitacaoservico sol on sol.idsolicitacaoservico = solmud.idsolicitacaoservico ");
		sql.append("inner join servicocontrato sercont on  sol.idservicocontrato =  sercont.idservicocontrato ");
		sql.append("inner join servico ser on ser.idservico = sercont.idservico and mud.idrequisicaomudanca = ? and idhistoricomudanca is null ");

		parametro.add(idRequisicaoMudanca);
		list = this.execSQL(sql.toString(), parametro.toArray());
		fields.add("idRequisicaoMudanca");
		fields.add("idSolicitacaoServico");
		fields.add("nomeServico");
		if (list != null && !list.isEmpty()) {
			return (List<RequisicaoMudancaDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}
	}

	public List<RequisicaoMudancaDTO> listProblemaByIdSolicitacao(RequisicaoMudancaDTO bean) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = " select  mud.idrequisicaomudanca , mud.titulo, empcriadopor.nome, empmudpri.nome, mud.status, sol.idSolicitacaoServico, ser.nomeServico  from requisicaomudanca  mud  "
				+ "	inner join  empregados empcriadopor on mud.idsolicitante = empcriadopor.idempregado  " + "	inner join  empregados empmudpri on mud.idproprietario = empmudpri.idempregado  "
				+ "	inner join solicitacaoservicomudanca solmud on solmud.idrequisicaoMudanca = mud.idrequisicaoMudanca  "
				+ "	inner join solicitacaoservico sol on sol.idsolicitacaoservico = solmud.idsolicitacaoservico  "
				+ "	inner join servicocontrato sercont on  sol.idservicocontrato =  sercont.idservicocontrato  " + "	inner join servico ser on ser.idservico = sercont.idservico  ";

		if (bean != null) {
			if (bean.getIdSolicitacaoServico() != null) {
				sql += "and sol.idSolicitacaoServico = ? ";
				parametro.add(bean.getIdSolicitacaoServico());
			}
		}

		if (bean != null) {
			if (bean.getIdRequisicaoMudanca() != null) {
				sql += "and mud.idrequisicaomudanca = ? ";
				parametro.add(bean.getIdRequisicaoMudanca());
			}
		}

		list = this.execSQL(sql, parametro.toArray());
		fields.add("idRequisicaoMudanca");
		fields.add("titulo");
		fields.add("nomeSolicitante");
		fields.add("nomeProprietario");
		fields.add("status");
		fields.add("idSolicitacaoServico");
		fields.add("nomeServico");

		if (list != null && !list.isEmpty()) {
			return (List<RequisicaoMudancaDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}
	}

	public List<RequisicaoMudancaDTO> listMudancaByIdItemConfiguracao(Integer idItemConfiguracao) throws Exception {
		String sql = " select  mud.idrequisicaomudanca, mud.titulo, mud.descricao , mud.idproprietario, mud.idsolicitante, "
				//+ " mud.nivelimpacto,mud.idCategoriaMudanca " +
				+ " mud.nivelimpacto,mud.idTipoMudanca "
				+ ", mud.motivo, mud.nivelImportanciaNegocio, mud.status, mud.tipo , emp.nome, us.nome , "
				+ " mud.dataHoraInicio from requisicaomudancaitemconfiguracao reqconf  "
				+ " inner join  requisicaomudanca mud on reqconf.idrequisicaomudanca = mud.idrequisicaomudanca "
				+ " inner join itemconfiguracao item on item.iditemconfiguracao = reqconf.iditemconfiguracao "
				+ " inner join empregados emp on emp.idempregado = mud.idsolicitante "
				+ " inner join usuario us on us.idusuario = mud.idproprietario "
				+ " where item.iditemconfiguracao = ?  " +
        " and (mud.status like 'EmExecucao' or mud.status like 'Registrada' or  mud.status like 'Aprovada' or  mud.status like 'Planejada')";
		/**
		 * A regra relacionada ao status estava comentada
		 * @author flavio.santana
		 * 25/10/2013 14:00
		 */
		List parametro = new ArrayList();
		List<String> listRetorno = new ArrayList<String>();
		parametro.add(idItemConfiguracao);

		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("titulo");
		listRetorno.add("descricao");
		listRetorno.add("idProprietario");
		listRetorno.add("idSolicitante");
		listRetorno.add("nivelImpacto");
		// listRetorno.add("idCategoriaMudanca");
		listRetorno.add("idTipoMudanca");
		listRetorno.add("motivo");
		listRetorno.add("nivelImportanciaNegocio");
		listRetorno.add("status");
		listRetorno.add("tipo");
		listRetorno.add("nomeSolicitante");
		listRetorno.add("nomeProprietario");
		listRetorno.add("dataHoraInicio");


		List lista = this.execSQL(sql, parametro.toArray());
		return this.engine.listConvertion(getBean(), lista, listRetorno);
	}

	public Collection<RequisicaoMudancaDTO> listaMudancasPorBaseConhecimento(RequisicaoMudancaDTO mudanca) throws Exception {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();

		sql.append("SELECT rm.idrequisicaomudanca, rm.tipo, rm.motivo, rm.descricao, rm.risco, rm.status, ");
		sql.append("rm.planoreversao, rm.datahorainicio, rm.datahoraconclusao, e.nome FROM requisicaomudanca rm, empregados e ");
		sql.append("WHERE rm.idproprietario = e.idempregado AND rm.idbaseconhecimento = ? ");

		parametro.add(mudanca.getIdBaseConhecimento());

		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("tipo");
		listRetorno.add("motivo");
		listRetorno.add("descricao");
		listRetorno.add("risco");
		listRetorno.add("status");
		listRetorno.add("planoReversao");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("dataHoraConclusao");
		listRetorno.add("nomeProprietario");

		List list = execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			Collection<RequisicaoMudancaDTO> listaMudancaPorBaseConhecimento = this.listConvertion(RequisicaoMudancaDTO.class, list, listRetorno);
			return listaMudancaPorBaseConhecimento;
		}

		return null;
	}

	public Collection<RequisicaoMudancaDTO> quantidadeMudancaPorBaseConhecimento(RequisicaoMudancaDTO mudanca) throws Exception {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();

		sql.append("select idbaseconhecimento,count(idbaseconhecimento) from requisicaomudanca where idbaseconhecimento = ? group by idbaseconhecimento");

		parametro.add(mudanca.getIdBaseConhecimento());
		listRetorno.add("idBaseConhecimento");
		listRetorno.add("quantidade");

		List list = execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			Collection<RequisicaoMudancaDTO> listaQuantidadeMudancaPorBaseConhecimento = this.listConvertion(RequisicaoMudancaDTO.class, list, listRetorno);
			return listaQuantidadeMudancaPorBaseConhecimento;
		}
		return null;
	}

	/**
	 * Retorna Requisições de Mudança associadas a Base de Conhecimento.
	 *
	 * @param baseConhecimentoDto
	 * @return List<RequisicaoMudancaDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public List<RequisicaoMudancaDTO> findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws Exception {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select requisicaomudanca.idRequisicaoMudanca, requisicaomudanca.titulo, requisicaomudanca.tipo, requisicaomudanca.motivo, requisicaomudanca.descricao, requisicaomudanca.risco, requisicaomudanca.status from requisicaomudanca ");
		sql.append("inner join conhecimentomudanca on requisicaomudanca.idRequisicaoMudanca = conhecimentomudanca.idrequisicaomudanca ");
		sql.append("where conhecimentomudanca.idbaseconhecimento = ? ");

		parametro.add(baseConhecimentoDto.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("titulo");
		listRetorno.add("tipo");
		listRetorno.add("motivo");
		listRetorno.add("descricao");
		listRetorno.add("risco");
		listRetorno.add("status");

		if (list != null && !list.isEmpty()) {

			return (List<RequisicaoMudancaDTO>) this.listConvertion(getBean(), list, listRetorno);

		} else {

			return null;
		}
	}

    public List<RequisicaoMudancaDTO> findByPeriodoAndSituacao(Date dataInicial, Date dataFinal, String situacao) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT idRequisicaoMudanca, titulo, dataHoraInicio, dataHoraConclusao, descricao, risco, status FROM requisicaomudanca ");
        sql.append("WHERE dataHoraInicio BETWEEN ? AND ? ");
        if(situacao != null && !"".equals(situacao))
        	sql.append("AND status = ? ");

        parametro.add(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataInicial, "yyyy-MM-dd") + " 00:00:00"));
        parametro.add(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataFinal, "yyyy-MM-dd") + " 23:59:59"));

        if(situacao != null && !"".equals(situacao))
        	parametro.add(situacao);

        sql.append("ORDER BY idRequisicaoMudanca");

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idRequisicaoMudanca");
        listRetorno.add("titulo");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraConclusao");
        listRetorno.add("descricao");
        listRetorno.add("risco");
        listRetorno.add("status");

        if (list != null && !list.isEmpty()) {
            return (List<RequisicaoMudancaDTO>) this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

	/**
	 * Retorna uma lista de requisicao mudanca de acordo com os criterios passados.
	 *
	 * @param requisicaoMudancaDto
	 * @return Collection<RequisicaoMudancaDTO>
	 * @throws Exception
	 * @author thays.araujo
	 * @author bruno.franco
	 */
	public Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios(PesquisaRequisicaoMudancaDTO pesquisaRequisicaoMudancaDto) throws Exception {

		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("ORACLE")) {

			sql.append("SELECT requisicaomudanca.idrequisicaomudanca,tipomudanca.nometipomudanca,requisicaomudanca.status,requisicaomudanca.titulo, ");
			sql.append("requisicaomudanca.motivo, requisicaomudanca.datahoraconclusao, requisicaomudanca.datahorainicio,requisicaomudanca.datahoratermino, ");
			sql.append("requisicaomudanca.descricao, e.nome, em.nome, requisicaomudanca.nomecategoriamudanca, grupo.nome ");
			sql.append("FROM " + getTableName() + " ");
			sql.append("inner join empregados e on e.idempregado = requisicaomudanca.idproprietario ");
			sql.append("inner join empregados em on em.idempregado = requisicaomudanca.idsolicitante ");
			sql.append("inner join grupo on grupo.idgrupo = requisicaomudanca.idgrupoatual ");
			sql.append("inner join tipomudanca on tipomudanca.idtipomudanca = requisicaomudanca.idtipomudanca ");
			sql.append("left join requisicaomudancaitemconfigura r on r.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca ");

			if (pesquisaRequisicaoMudancaDto.getDataInicio() != null) {
				// Adaptação realizada passando o timestamp concatenado no
				// próprio sqp para poder colocar aspas simples na pesquisa
				sql.append(" where requisicaomudanca.datahorainicio BETWEEN TO_DATE('" + UtilDatas.dateToSTR(pesquisaRequisicaoMudancaDto.getDataInicio()) + "', 'DD/MM/YYYY ')  and TO_DATE('"
						+ UtilDatas.dateToSTR(pesquisaRequisicaoMudancaDto.getDataFim()) + "', 'DD/MM/YYYY ') ");

			}
			if (pesquisaRequisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
				if (pesquisaRequisicaoMudancaDto.getDataInicio() == null) {
					sql.append(" where requisicaomudanca.idrequisicaomudanca = ?");
					parametro.add(pesquisaRequisicaoMudancaDto.getIdRequisicaoMudanca());
				} else {
					sql.append(" and requisicaomudanca.idrequisicaomudanca = ?");
					parametro.add(pesquisaRequisicaoMudancaDto.getIdRequisicaoMudanca());
				}
			}
			if (pesquisaRequisicaoMudancaDto.getStatus() != null && !pesquisaRequisicaoMudancaDto.getStatus().equalsIgnoreCase("")) {
				sql.append(" and requisicaomudanca.status = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getStatus());
			}

			if (pesquisaRequisicaoMudancaDto.getIdSolicitante() != null) {
				sql.append(" and requisicaomudanca.idsolicitante = ? ");
				parametro.add(pesquisaRequisicaoMudancaDto.getIdSolicitante());
			}

			if (pesquisaRequisicaoMudancaDto.getIdProprietario() != null) {
				sql.append(" and requisicaomudanca.idproprietario = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getIdProprietario());
			}

			if (pesquisaRequisicaoMudancaDto.getIdGrupoAtual() != null) {
				sql.append(" and requisicaomudanca.idgrupoatual = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getIdGrupoAtual());
			}

			if (pesquisaRequisicaoMudancaDto.getIdItemConfiguracao() != null) {
				sql.append(" and requisicaomudancaitemconfiguracao.iditemconfiguracao = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getIdItemConfiguracao());
			}

			if (pesquisaRequisicaoMudancaDto.getIdTipoMudanca() != null) {
				sql.append(" and tipomudanca.idtipomudanca = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getIdTipoMudanca());
			}

			if (pesquisaRequisicaoMudancaDto.getNomeCategoriaMudanca() != null && !pesquisaRequisicaoMudancaDto.getNomeCategoriaMudanca().equalsIgnoreCase("")) {
				sql.append(" and requisicaomudanca.nomecategoriamudanca = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getNomeCategoriaMudanca());
			}

			if (pesquisaRequisicaoMudancaDto.getOrdenacao() != null) {

				sql.append(" ORDER BY " + pesquisaRequisicaoMudancaDto.getOrdenacao());
				// parametro.add(pesquisaRequisicaoMudancaDto.getOrdenacao());
			}

		} else {

			sql.append("SELECT requisicaomudanca.idrequisicaomudanca,tipomudanca.nometipomudanca,requisicaomudanca.status,requisicaomudanca.titulo,requisicaomudanca.motivo, requisicaomudanca.datahoraconclusao, ");
			sql.append("requisicaomudanca.datahorainicio,requisicaomudanca.datahoratermino, requisicaomudanca.descricao, ");
			sql.append("proprietario.nome, solicitante.nome, requisicaomudanca.nomecategoriamudanca, grupo.nome ");
			sql.append("FROM " + getTableName() + " ");
			sql.append("inner join empregados as proprietario on proprietario.idempregado = requisicaomudanca.idproprietario ");
			sql.append("inner join empregados as solicitante on solicitante.idempregado = requisicaomudanca.idsolicitante ");
			// sql.append("inner join categoriamudanca on categoriamudanca.idcategoriamudanca = requisicaomudanca.idcategoriamudanca ");categoriamudanca.nomecategoria,
			sql.append("inner join grupo on grupo.idgrupo = requisicaomudanca.idgrupoatual ");
			sql.append("inner join tipomudanca on tipomudanca.idtipomudanca = requisicaomudanca.idtipomudanca ");
			sql.append("left join requisicaomudancaitemconfiguracao on requisicaomudancaitemconfiguracao.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca ");

			if (pesquisaRequisicaoMudancaDto.getDataInicio() != null) {
				// Adaptação realizada passando o timestamp concatenado no
				// próprio sqp para poder colocar aspas simples na pesquisa
				sql.append(" where requisicaomudanca.datahorainicio BETWEEN  '" + UtilDatas.dateToSTR(pesquisaRequisicaoMudancaDto.getDataInicio()) + " 00:00:00'   and '"
						+ UtilDatas.dateToSTR(pesquisaRequisicaoMudancaDto.getDataFim()) + " 23:59:59' ");
				// parametro.add(UtilDatas.formatTimestamp();
				// parametro.add(pesquisaRequisicaoMudancaDto.getDataInicio() +
				// " 00:00:00 ");
				// parametro.add( "'" +
				// pesquisaRequisicaoMudancaDto.getDataFim() + " 23:59:59 ");

			}
			if (pesquisaRequisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
				if (pesquisaRequisicaoMudancaDto.getDataInicio() == null) {
					sql.append(" where requisicaomudanca.idrequisicaomudanca = ?");
					parametro.add(pesquisaRequisicaoMudancaDto.getIdRequisicaoMudanca());
				} else {
					sql.append(" and requisicaomudanca.idrequisicaomudanca = ?");
					parametro.add(pesquisaRequisicaoMudancaDto.getIdRequisicaoMudanca());
				}
			}
			if (pesquisaRequisicaoMudancaDto.getStatus() != null && !pesquisaRequisicaoMudancaDto.getStatus().equalsIgnoreCase("")) {
				sql.append(" and requisicaomudanca.status = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getStatus());
			}

			if (pesquisaRequisicaoMudancaDto.getIdSolicitante() != null) {
				sql.append(" and requisicaomudanca.idsolicitante = ? ");
				parametro.add(pesquisaRequisicaoMudancaDto.getIdSolicitante());
			}

			if (pesquisaRequisicaoMudancaDto.getIdProprietario() != null) {
				sql.append(" and requisicaomudanca.idproprietario = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getIdProprietario());
			}

			if (pesquisaRequisicaoMudancaDto.getIdGrupoAtual() != null) {
				sql.append(" and requisicaomudanca.idgrupoatual = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getIdGrupoAtual());
			}

			if (pesquisaRequisicaoMudancaDto.getIdItemConfiguracao() != null) {
				sql.append(" and requisicaomudancaitemconfiguracao.iditemconfiguracao = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getIdItemConfiguracao());
			}

			if (pesquisaRequisicaoMudancaDto.getIdTipoMudanca() != null) {
				sql.append(" and tipomudanca.idtipomudanca = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getIdTipoMudanca());
			}

			if (pesquisaRequisicaoMudancaDto.getNomeCategoriaMudanca() != null && !pesquisaRequisicaoMudancaDto.getNomeCategoriaMudanca().equalsIgnoreCase("")) {
				sql.append(" and requisicaomudanca.nomecategoriamudanca = ?");
				parametro.add(pesquisaRequisicaoMudancaDto.getNomeCategoriaMudanca());
			}

			if (pesquisaRequisicaoMudancaDto.getOrdenacao() != null) {

				sql.append(" ORDER BY " + pesquisaRequisicaoMudancaDto.getOrdenacao());
				// parametro.add(pesquisaRequisicaoMudancaDto.getOrdenacao());
			}
		}

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("tipo");
		listRetorno.add("status");
		listRetorno.add("titulo");
		listRetorno.add("motivo");
		listRetorno.add("dataHoraConclusao");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("dataHoraTermino");
		listRetorno.add("descricao");
		listRetorno.add("nomeProprietario");
		listRetorno.add("nomeSolicitante");
		listRetorno.add("nomeCategoriaMudanca");
		listRetorno.add("nomeGrupoAtual");

		if (list != null && !list.isEmpty()) {

			Collection<PesquisaRequisicaoMudancaDTO> listRequisicaoMudancaByCriterios = this.listConvertion(PesquisaRequisicaoMudancaDTO.class, list, listRetorno);

			return listRequisicaoMudancaByCriterios;

		}

		return null;

	}

	/**
	 * Retorna uma lista de requisicao mudança associados a um tipo mudança
	 *
	 * @param idCargo
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarSeRequisicaoMudancaPossuiTipoMudanca(Integer idTipoMudanca) throws Exception {
		List parametro = new ArrayList();
		String sql = "select idrequisicaomudanca from " + getTableName() + " where idtipomudanca = ? ";
		parametro.add(idTipoMudanca);
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		if (lista != null && !lista.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public RequisicaoMudancaDTO findByIdInstanciaFluxo(Integer idInstanciaFluxo) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idInstanciaFluxo", "=", idInstanciaFluxo));

		Collection col = super.findByCondition(condicao, null);
		if (col == null || col.size() == 0)
			return null;
		return (RequisicaoMudancaDTO) ((List) col).get(0);
	}

	public Collection listaIdETituloMudancasPorPeriodo(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listResultado = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select requisicaomudanca.idrequisicaomudanca, ");
		sql.append("       requisicaomudanca.titulo ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");
		sql.append("group by requisicaomudanca.idrequisicaomudanca,requisicaomudanca.titulo ");
		sql.append("order by requisicaomudanca.idrequisicaomudanca,requisicaomudanca.titulo ");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("titulo");
		if (lista != null && !lista.isEmpty()) {
			listResultado = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listResultado;
	}

	public Collection listaMudancaIncidente(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listResultado = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select requisicaomudanca.idrequisicaomudanca, ");
		sql.append("       requisicaomudanca.titulo mudanca, ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
			sql.append("   cast(solicitacaoservico.idsolicitacaoservico as varchar) incidente ");
		} else {
			sql.append("   solicitacaoservico.idsolicitacaoservico incidente ");
		}
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("inner join solicitacaoservicomudanca solicitacaoservicomudanca ");
		sql.append("	  on solicitacaoservicomudanca.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca ");
		sql.append("inner join solicitacaoservico solicitacaoservico  ");
		sql.append("      on solicitacaoservico.idsolicitacaoservico = solicitacaoservicomudanca.idsolicitacaoservico ");
		sql.append("inner join tipodemandaservico tipodemandaservico  ");
		sql.append("      on tipodemandaservico.idtipodemandaservico = solicitacaoservico.idtipodemandaservico ");
		sql.append("         and tipodemandaservico.classificacao = 'I' ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");
		sql.append("order by requisicaomudanca.idrequisicaomudanca ");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("titulo");
		listRetorno.add("incidente");
		if (lista != null && !lista.isEmpty()) {
			listResultado = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listResultado;
	}

	public Collection listaMudancaServico(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listResultado = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select requisicaomudanca.idrequisicaomudanca, ");
		sql.append("       requisicaomudanca.titulo mudanca, ");
		sql.append("       servico.nomeservico servico ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("inner join requisicaomudancaservico requisicaomudancaservico ");
		sql.append("       on requisicaomudancaservico.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca ");
		sql.append("inner join servico servico ");
		sql.append("       on servico.idservico = requisicaomudancaservico.idservico ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");
		sql.append("order by requisicaomudanca.idrequisicaomudanca ");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("titulo");
		listRetorno.add("servico");
		if (lista != null && !lista.isEmpty()) {
			listResultado = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listResultado;
	}

	public Collection listaMudancaProblema(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listResultado = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select requisicaomudanca.idrequisicaomudanca, ");
		sql.append("       requisicaomudanca.titulo mudanca, ");
		sql.append("	   problema.titulo problema ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("inner join problemamudanca problemamudanca ");
		sql.append("       on problemamudanca.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca ");
		sql.append("inner join problema problema ");
		sql.append("      on problema.idproblema = problemamudanca.idproblema ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("titulo");
		listRetorno.add("problema");
		if (lista != null && !lista.isEmpty()) {
			listResultado = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listResultado;
	}

	public Collection listaMudancaConhecimento(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listResultado = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select requisicaomudanca.idrequisicaomudanca, ");
		sql.append("      requisicaomudanca.titulo mudanca, ");
		sql.append("      baseconhecimento.titulo conhecimento ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("inner join baseconhecimento baseconhecimento ");
		sql.append("       on baseconhecimento.idbaseconhecimento = requisicaomudanca.idbaseconhecimento ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("titulo");
		listRetorno.add("conhecimento");
		if (lista != null && !lista.isEmpty()) {
			listResultado = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listResultado;
	}

	public Collection listaMudancaItemConfiguracao(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listResultado = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select requisicaomudanca.idrequisicaomudanca, ");
		sql.append("       requisicaomudanca.titulo mudanca, ");
		sql.append("       itemconfiguracao.identificacao itemconfigucacao ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("inner join requisicaomudancaitemconfiguracao requisicaomudancaitemconfiguracao ");
		sql.append("       on requisicaomudancaitemconfiguracao.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca ");
		sql.append("inner join itemconfiguracao itemconfiguracao ");
		sql.append("       on itemconfiguracao.iditemconfiguracao = requisicaomudancaitemconfiguracao.iditemconfiguracao ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("titulo");
		listRetorno.add("itemConfiguracao");
		if (lista != null && !lista.isEmpty()) {
			listResultado = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listResultado;
	}

	public Collection listaQuantidadeMudancaPorPeriodo(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {

		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listaQuantidadeMudancaPorPeriodo = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select count(*) ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("quantidadeMudancaPorPeriodo");
		if (lista != null && !lista.isEmpty()) {
			listaQuantidadeMudancaPorPeriodo = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listaQuantidadeMudancaPorPeriodo;
	}

	public Collection listaQuantidadeMudancaPorSolicitante(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listaQuantidadeMudancaPorSolicitante = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select empregados.nome, count(*) ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("inner join empregados empregados on empregados.idempregado = requisicaomudanca.idsolicitante ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");
		sql.append("group by empregados.nome");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("nomeSolicitante");
		listRetorno.add("quantidadeSolicitante");
		if (lista != null && !lista.isEmpty()) {
			listaQuantidadeMudancaPorSolicitante = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listaQuantidadeMudancaPorSolicitante;
	}

	public Collection listaQuantidadeMudancaPorImpacto(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listaQuantidadeMudancaPorImpacto = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select requisicaomudanca.nivelimpacto, count(*) ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");
		sql.append("group by requisicaomudanca.nivelimpacto");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("nivelImpacto");
		listRetorno.add("quantidadeImpacto");
		if (lista != null && !lista.isEmpty()) {
			listaQuantidadeMudancaPorImpacto = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listaQuantidadeMudancaPorImpacto;
	}

	public Collection listaQuantidadeMudancaPorUrgencia(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listaQuantidadeMudancaPorImpacto = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select requisicaomudanca.nivelurgencia, count(*) ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");
		sql.append("group by requisicaomudanca.nivelurgencia");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("nivelUrgencia");
		listRetorno.add("quantidadeUrgencia");
		if (lista != null && !lista.isEmpty()) {
			listaQuantidadeMudancaPorImpacto = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listaQuantidadeMudancaPorImpacto;
	}

	public Collection listaQuantidadeSemAprovacaoPorPeriodo(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listaQuantidadeMudancaPorImpacto = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT Count(DISTINCT requisicaomudanca.idrequisicaomudanca) ");
		sql.append("FROM   requisicaomudanca requisicaomudanca ");
		sql.append("       INNER JOIN aprovacaomudanca aprovacaomudanca ");
		sql.append("               ON aprovacaomudanca.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca ");
		sql.append("WHERE  (aprovacaomudanca.voto IS NULL ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("       OR aprovacaomudanca.voto LIKE ' ') ");
		} else {
			if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
				sql.append(" OR ltrim(rtrim(aprovacaomudanca.voto)) LIKE '') ");
			} else {
				sql.append("  OR trim(aprovacaomudanca.voto) LIKE '') ");
			}

		}
		sql.append("       AND requisicaomudanca.datahorainicio BETWEEN ? AND ? ");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("quantidadeMudancaSemAprovacaoPorPeriodo");
		if (lista != null && !lista.isEmpty()) {
			listaQuantidadeMudancaPorImpacto = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listaQuantidadeMudancaPorImpacto;
	}

	public Collection listaQuantidadeMudancaPorProprietario(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listaQuantidadeMudancaPorProprietario = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select empregados.nome, count(*) ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("inner join empregados empregados on empregados.idempregado = requisicaomudanca.idproprietario ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");
		sql.append("group by empregados.nome");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("nomeProprietario");
		listRetorno.add("quantidadeProprietario");
		if (lista != null && !lista.isEmpty()) {
			listaQuantidadeMudancaPorProprietario = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listaQuantidadeMudancaPorProprietario;
	}

	public Collection listaQuantidadeMudancaPorStatus(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listaQuantidadeMudancaPorStatus = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select requisicaomudanca.status, count(*) ");
		sql.append("from requisicaomudanca requisicaomudanca ");
		sql.append("where requisicaomudanca.datahorainicio BETWEEN ? AND ? ");
		sql.append("group by requisicaomudanca.status");

		parametro.add(requisicaoMudancaDTO.getDataInicio());
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			parametro.add(requisicaoMudancaDTO.getDataFim());
		} else {
			parametro.add(transformaHoraFinal(requisicaoMudancaDTO.getDataFim()));
		}

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("status");
		listRetorno.add("quantidadeStatus");
		if (lista != null && !lista.isEmpty()) {
			listaQuantidadeMudancaPorStatus = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listaQuantidadeMudancaPorStatus;
	}

	public Collection listaMudancaGrupo(RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		List listResultado = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select grupo.idgrupo, ");
		sql.append(" grupo.nome grupo ");
		sql.append(" from requisicaomudanca requisicaomudanca ");
		sql.append(" inner join gruporequisicaomudanca grupomudanca ");
	    sql.append(" on grupomudanca.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca ");
	    sql.append(" inner join grupo grupo ");
	    sql.append(" on grupo.idgrupo = grupomudanca.idgrupo ");
	    sql.append(" where grupomudanca.idrequisicaomudanca=? ");

		parametro.add(requisicaoMudancaDTO.getIdRequisicaoMudanca());

		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("grupo");
		if (lista != null && !lista.isEmpty()) {
			listResultado = this.engine.listConvertion(RequisicaoMudancaDTO.class, lista, listRetorno);
		}
		return listResultado;
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
		sql.append("SELECT distinct (s.idrequisicaomudanca), "
		 + " s.prioridade, s.datahoratermino,  regra1.idcontrato as idcontrato, regra3.idsolicitante as idsolicitante,  "
		 + " regra4.idgrupo as idgrupo, s.vencendo, s.datahorainicio, s.prazohh, s.prazomm, s.nivelimpacto, s.nivelurgencia  "
		 + " FROM requisicaomudanca s   "
		 + " INNER JOIN contratos contratos on contratos.idcontrato = s.idcontrato "
		 + " INNER JOIN empregados solicitante on solicitante.idempregado = s.idsolicitante "
		 + " LEFT JOIN grupo grupo on grupo.idgrupo = s.idgrupoatual "
		 + " LEFT JOIN regraescalonamento regra1 on regra1.idcontrato = contratos.idcontrato "
		 + " LEFT JOIN regraescalonamento regra3 on regra3.idsolicitante = solicitante.idempregado "
		 + " LEFT JOIN regraescalonamento regra4 on regra4.idgrupo = grupo.idgrupo  "
		 + " WHERE UPPER(s.status) not in ('FECHADA', 'CANCELADA', 'RESOLVIDA', 'SUSPENSA', 'CONCLUIDA') ");

		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("prioridade");
		listRetorno.add("dataHoraTermino");
		listRetorno.add("idContrato");
		listRetorno.add("idSolicitante");
		listRetorno.add("idGrupo");
		listRetorno.add("vencendo");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("prazoHH");
		listRetorno.add("prazoMM");
		listRetorno.add("nivelImpacto");
		listRetorno.add("nivelUrgencia");

		List list = this.execSQL(sql.toString(), null);
		if (list != null && !list.isEmpty()) {
			return (Collection<RequisicaoMudancaDTO>) this.listConvertion(getBean(), list, listRetorno);
		} else {
			return null;
		}
	}


}
