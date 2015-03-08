package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AgendaAtvPeriodicasDTO;
import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class AtividadePeriodicaDao extends CrudDaoDefaultImpl {
	public AtividadePeriodicaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAtividadePeriodica" ,"idAtividadePeriodica", true, true, false, false));
		listFields.add(new Field("idContrato" ,"idContrato", false, false, false, false));
		listFields.add(new Field("idProcedimentoTecnico" ,"idProcedimentoTecnico", false, false, false, false));
		listFields.add(new Field("idGrupoAtvPeriodica" ,"idGrupoAtvPeriodica", false, false, false, false));
		listFields.add(new Field("tituloAtividade" ,"tituloAtividade", false, false, false, false));
		listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		listFields.add(new Field("orientacaoTecnica" ,"orientacaoTecnica", false, false, false, false));
		listFields.add(new Field("criadoPor" ,"criadoPor", false, false, false, false));
		listFields.add(new Field("alteradoPor" ,"alteradoPor", false, false, false, false));
		listFields.add(new Field("dataCriacao" ,"dataCriacao", false, false, false, false));
		listFields.add(new Field("dataUltAlteracao" ,"dataUltAlteracao", false, false, false, false));
		listFields.add(new Field("idSolicitacaoServico" ,"idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idRequisicaoMudanca" ,"idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("idProblema" ,"idProblema", false, false, false, false));
		listFields.add(new Field("blackout" ,"blackout", false, false, false, false));
		listFields.add(new Field("idLiberacao" ,"idRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "AtividadePeriodica";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return AtividadePeriodicaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdSolicitacaoServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", parm));
		ordenacao.add(new Order("dataCriacao"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdRequisicaoMudanca(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm));
		ordenacao.add(new Order("dataCriacao"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdRequisicaoLiberacao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idRequisicaoLiberacao", "=", parm));
		ordenacao.add(new Order("dataCriacao"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", parm));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("dataCriacao"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idContrato", "=", parm));
		ordenacao.add(new Order(""));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idContrato", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdProcedimentoTecnico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idProcedimentoTecnico", "=", parm));
		ordenacao.add(new Order(""));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdProcedimentoTecnico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProcedimentoTecnico", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdGrupoAtvPeriodica(AgendaAtvPeriodicasDTO agendaAtvPeriodicasDTO) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idGrupoAtvPeriodica", "=", agendaAtvPeriodicasDTO.getIdGrupoAtvPeriodica()));
//		if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() == 1 && agendaAtvPeriodicasDTO.getIdGrupoAtvPeriodica() != 1) {
//			condicao.add(new Condition("idSolicitacaoServico", ">", 0));
//		}
		ordenacao.add(new Order("tituloAtividade"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection<AtividadePeriodicaDTO> listSomenteReqMudanca(AgendaAtvPeriodicasDTO agendaAtvPeriodicasDTO) throws PersistenceException {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();

		sql.append("select ap.tituloAtividade, ap.descricao, ap.idAtividadePeriodica, ap.idRequisicaoMudanca, ap.criadoPor, ap.idContrato, ");
		sql.append(" ap.idGrupoAtvPeriodica, ap.orientacaoTecnica, tm.nomeTipoMudanca, ap.idProcedimentoTecnico, ap.idSolicitacaoServico, ap.idProblema, ap.blackout, ap.idliberacao, ");
		sql.append(" ap.dataCriacao, ap.dataFim, ap.dataUltAlteracao, ap.dataInicio ");
		sql.append(" from atividadeperiodica ap ");
		sql.append(" inner join requisicaomudanca rm on rm.idrequisicaomudanca = ap.idrequisicaomudanca ");
		sql.append(" inner join tipomudanca tm on tm.idtipomudanca = rm.idtipomudanca ");
		sql.append(" inner join gruporequisicaomudanca grm on rm.idrequisicaomudanca=GRM.idrequisicaomudanca ");
		sql.append(" inner join gruposempregados ge on GE.idgrupo = GRM.idgrupo  ");
		sql.append(" where ap.idGrupoAtvPeriodica = ? and ge.idempregado = ? and grm.datafim is null ");
		sql.append(" and ap.idrequisicaomudanca > 0  ");
		sql.append(" order by tituloAtividade ");

		parametro.add(agendaAtvPeriodicasDTO.getIdGrupoAtvPeriodica());
		parametro.add(agendaAtvPeriodicasDTO.getIdEmpregado());

		listRetorno.add("tituloAtividade");
		listRetorno.add("descricao");
		listRetorno.add("idAtividadePeriodica");
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("criadoPor");
		listRetorno.add("idContrato");
		listRetorno.add("idGrupoAtvPeriodica");
		listRetorno.add("orientacaoTecnica");
		listRetorno.add("idProcedimentoTecnico");
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("idProblema");
		listRetorno.add("blackout");
		listRetorno.add("idLiberacao");
		listRetorno.add("dataCriacao");
		listRetorno.add("dataFim");
		listRetorno.add("dataUltAlteracao");
		listRetorno.add("dataInicio");


		List list = execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			Collection<AtividadePeriodicaDTO> listaAgendaAtvPeriodica = this.listConvertion(AtividadePeriodicaDTO.class, list, listRetorno);
			return listaAgendaAtvPeriodica;
		}

		return null;
	}

	public Collection<AtividadePeriodicaDTO> listAgendamentoVinculados(AgendaAtvPeriodicasDTO agendaAtvPeriodicasDTO) throws PersistenceException {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();

		sql.append("select ap.tituloAtividade, ap.descricao, ap.idAtividadePeriodica, ap.idRequisicaoMudanca, ap.criadoPor, ap.idContrato, ");
		sql.append("ap.idGrupoAtvPeriodica, ap.orientacaoTecnica, ap.idProcedimentoTecnico, ap.idSolicitacaoServico, ap.idProblema, ap.blackout, ap.idliberacao,  ");
		sql.append("ap.dataCriacao, ap.dataFim, ap.dataUltAlteracao, ap.dataInicio ");
		sql.append("from atividadeperiodica ap ");
		sql.append("where ap.idGrupoAtvPeriodica = ? ");

		if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() == 1) {
			sql.append("and ap.idsolicitacaoservico > 0 ");
		}
		if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() == 2) {
			sql.append("and ap.idrequisicaomudanca > 0 ");
		}
		if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() == 3) {
			sql.append("and ap.idliberacao > 0 ");
		}
		if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() == 4) {
			sql.append("and ap.idproblema > 0 ");
		}

		sql.append(" order by tituloAtividade ");

		if(agendaAtvPeriodicasDTO != null){
			parametro.add(agendaAtvPeriodicasDTO.getIdGrupoAtvPeriodica());
		}

		listRetorno.add("tituloAtividade");
		listRetorno.add("descricao");
		listRetorno.add("idAtividadePeriodica");
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("criadoPor");
		listRetorno.add("idContrato");
		listRetorno.add("idGrupoAtvPeriodica");
		listRetorno.add("orientacaoTecnica");
		listRetorno.add("idProcedimentoTecnico");
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("idProblema");
		listRetorno.add("blackout");
		listRetorno.add("idLiberacao");
		listRetorno.add("dataCriacao");
		listRetorno.add("dataFim");
		listRetorno.add("dataUltAlteracao");
		listRetorno.add("dataInicio");


		List list = execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			Collection<AtividadePeriodicaDTO> listaAgendaAtvPeriodica = this.listConvertion(AtividadePeriodicaDTO.class, list, listRetorno);
			return listaAgendaAtvPeriodica;
		}

		return null;
	}

	public Collection<AtividadePeriodicaDTO> listAgendamentoSemVinculacao(AgendaAtvPeriodicasDTO agendaAtvPeriodicasDTO) throws PersistenceException {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();

		sql.append("select ap.tituloAtividade, ap.descricao, ap.idAtividadePeriodica, ap.idRequisicaoMudanca, ap.criadoPor, ap.idContrato, ");
		sql.append("ap.idGrupoAtvPeriodica, ap.orientacaoTecnica, ap.idProcedimentoTecnico, ap.idSolicitacaoServico, ap.idProblema, ap.blackout, ap.idliberacao,  ");
		sql.append("ap.dataCriacao, ap.dataFim, ap.dataUltAlteracao, ap.dataInicio ");
		sql.append("from atividadeperiodica ap ");
		sql.append("where idrequisicaomudanca is null ");
		if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != 0) {
			sql.append("and idsolicitacaoservico is null and idproblema is null and idliberacao is null ");
		}
		sql.append(" and ap.idGrupoAtvPeriodica = ? ");
		if(agendaAtvPeriodicasDTO != null){
			parametro.add(agendaAtvPeriodicasDTO.getIdGrupoAtvPeriodica());
		}
		sql.append(" order by ap.tituloAtividade ");


		listRetorno.add("tituloAtividade");
		listRetorno.add("descricao");
		listRetorno.add("idAtividadePeriodica");
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("criadoPor");
		listRetorno.add("idContrato");
		listRetorno.add("idGrupoAtvPeriodica");
		listRetorno.add("orientacaoTecnica");
		listRetorno.add("idProcedimentoTecnico");
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("idProblema");
		listRetorno.add("blackout");
		listRetorno.add("idLiberacao");
		listRetorno.add("dataCriacao");
		listRetorno.add("dataFim");
		listRetorno.add("dataUltAlteracao");
		listRetorno.add("dataInicio");


		List list = execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			Collection<AtividadePeriodicaDTO> listaAgendaAtvPeriodica = this.listConvertion(AtividadePeriodicaDTO.class, list, listRetorno);
			return listaAgendaAtvPeriodica;
		}

		return null;
	}

	public void deleteByIdGrupoAtvPeriodica(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idGrupoAtvPeriodica", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByTituloAtividade(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("tituloAtividade", "=", parm));
		ordenacao.add(new Order("tituloAtividade"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByTituloAtividade(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("tituloAtividade", "=", parm));
		super.deleteByCondition(condicao);
	}

	/**
	 * Metodo que verifica se existe um registro com os mesmos dados na base de dados.
	 *
	 * @param tituloAtividade - nome da atividade periodica.
	 * @return Retorna 'true' se existir um registro igual e 'false' caso contrario.
	 * @throws Exception
	 */
	public boolean existeDuplicacao(String tituloAtividade, Integer idAtividade) throws PersistenceException {
		List condicao = new ArrayList();
    	List ordenacao = new ArrayList();
    	condicao.add(new Condition("tituloAtividade", "=", tituloAtividade));

    	if(idAtividade != null ){
    		if(idAtividade > 0){
    			condicao.add(new Condition("idAtividadePeriodica", "<>", idAtividade));
    		}
    	}

    	ordenacao.add(new Order("tituloAtividade"));

    	List result = (List) super.findByCondition(condicao, ordenacao);

    	if (result != null && !result.isEmpty()) {
    		return true;
    	} else {
            return false;
    	}
	}
}
