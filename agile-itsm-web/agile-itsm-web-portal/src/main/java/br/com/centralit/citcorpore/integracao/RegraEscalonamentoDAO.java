package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RegraEscalonamentoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class RegraEscalonamentoDAO extends CrudDaoDefaultImpl {

	public RegraEscalonamentoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idregraescalonamento", "idRegraEscalonamento", true, true, false, false));
		listFields.add(new Field("idtipogerenciamento", "idTipoGerenciamento", false, false, false, false));
		listFields.add(new Field("idservico", "idServico", false, false, false, false));
		listFields.add(new Field("idcontrato", "idContrato", false, false, false, false));
		listFields.add(new Field("idsolicitante", "idSolicitante", false, false, false, false));
		listFields.add(new Field("idgrupo", "idGrupo", false, false, false, false));
		listFields.add(new Field("idtipodemandaservico", "idTipoDemandaServico", false, false, false, false));
		listFields.add(new Field("urgencia", "urgencia", false, false, false, false));
		listFields.add(new Field("impacto", "impacto", false, false, false, false));
		listFields.add(new Field("tempoexecucao", "tempoExecucao", false, false, false, false));
		listFields.add(new Field("intervalonotificacao", "intervaloNotificacao", false, false, false, false));
		listFields.add(new Field("enviaremail", "enviarEmail", false, false, false, false));
		listFields.add(new Field("criaproblema", "criaProblema", false, false, false, false));
		listFields.add(new Field("datainicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("datafim", "dataFim", false, false, false, false));
		listFields.add(new Field("prazocriarproblema", "prazoCriarProblema", false, false, false, false));
		listFields.add(new Field("tipodataescalonamento", "tipoDataEscalonamento", false, false, false, false));
		
		return listFields;
	}

	@Override
	public String getTableName() {
		return "regraescalonamento";
	}

	@Override
	public Collection list() throws PersistenceException {
		return super.list("idRegraEscalonamento");
	}

	@Override
	public Class getBean() {
		return RegraEscalonamentoDTO.class;
	}
	
	/*Define os tipos de Solicitação as serem filtrados
	 * 1- Solicitação serviço/Incidente
	 * 2- Problema
	 * 3- Mudança
	 */
	public Collection findByTipoSolicitacao(int idTipoGerenciamento) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		sql.append("select idregraescalonamento, idtipogerenciamento, idservico, idcontrato, idsolicitante, idgrupo, idtipodemandaservico, urgencia, impacto, tempoexecucao, intervalonotificacao, enviaremail, criaproblema, datainicio, prazocriarproblema, tipodataescalonamento from regraescalonamento where idtipogerenciamento = ? AND (datafim is null)");
		
		parametro.add(idTipoGerenciamento);
		
	    listRetorno.add("idRegraEscalonamento");
		listRetorno.add("idTipoGerenciamento");
		listRetorno.add("idServico");
		listRetorno.add("idContrato");
		listRetorno.add("idSolicitante");
		listRetorno.add("idGrupo");
		listRetorno.add("idTipoDemandaServico");
		listRetorno.add("urgencia");
		listRetorno.add("impacto");
		listRetorno.add("tempoExecucao");
		listRetorno.add("intervaloNotificacao");
		listRetorno.add("enviarEmail");
		listRetorno.add("criaProblema");
		listRetorno.add("dataInicio");
		listRetorno.add("prazoCriarProblema");
		listRetorno.add("tipoDataEscalonamento");
		
		List list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return (Collection<RegraEscalonamentoDTO>) this.listConvertion(getBean(), list, listRetorno);
		} else {
			return null;
		}
		
	}
	
	public Collection<RegraEscalonamentoDTO> findRegraBySolicitacao(SolicitacaoServicoDTO solicitacaoServicoDTO, Integer idTipoGerenciamento) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();
		boolean temServico = false, temContrato = false, temSolicitante = false, temGrupo = false, temDemandaServico = false, temImpacto = false, temUrgencia = false;
		sql.append(" SELECT idregraescalonamento, idtipogerenciamento, idservico, idcontrato, idsolicitante, idgrupo, ");
		sql.append(" idtipodemandaservico, urgencia, impacto, ");
		sql.append(" tempoexecucao, intervalonotificacao, enviaremail, criaproblema, prazocriarproblema, tipodataescalonamento FROM regraescalonamento");
		sql.append(" where datafim is null and idtipogerenciamento = ?");
		if(solicitacaoServicoDTO.getIdServico() != null) {
			temServico = true;
		}
		if(solicitacaoServicoDTO.getIdContrato() != null) {
			temContrato = true;
		}
		if(solicitacaoServicoDTO.getIdSolicitante() != null) {
			temSolicitante = true;
		}
		if(solicitacaoServicoDTO.getIdGrupo() != null) {
			temGrupo = true;
		}
		if(solicitacaoServicoDTO.getIdTipoDemandaServico() != null) {
			temDemandaServico = true;
		}
		if(solicitacaoServicoDTO.getImpacto() != null) {
			temImpacto = true;
		}
		if(solicitacaoServicoDTO.getUrgencia() != null) {
			temUrgencia = true;
		}
		
	    listRetorno.add("idRegraEscalonamento");
		listRetorno.add("idTipoGerenciamento");
		listRetorno.add("idServico");
		listRetorno.add("idContrato");
		listRetorno.add("idSolicitante");
		listRetorno.add("idGrupo");
		listRetorno.add("idTipoDemandaServico");
		listRetorno.add("urgencia");
		listRetorno.add("impacto");
		listRetorno.add("tempoExecucao");
		listRetorno.add("intervaloNotificacao");
		listRetorno.add("enviarEmail");
		listRetorno.add("criaProblema");
		listRetorno.add("prazoCriarProblema");
		listRetorno.add("tipoDataEscalonamento");
	    
		parametro.add(idTipoGerenciamento);
		
		List list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			
			Collection<RegraEscalonamentoDTO> lista = (Collection<RegraEscalonamentoDTO>) this.listConvertion(getBean(), list, listRetorno);
			Collection<RegraEscalonamentoDTO> novaLista = new ArrayList<RegraEscalonamentoDTO>(lista); 
			for (RegraEscalonamentoDTO regraEscalonamentoDTO : lista) {
				if(!temServico && regraEscalonamentoDTO.getIdServico() != null || temServico && regraEscalonamentoDTO.getIdServico() != null &&
						solicitacaoServicoDTO.getIdServico().intValue() != regraEscalonamentoDTO.getIdServico().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temContrato && regraEscalonamentoDTO.getIdContrato() != null || temContrato && regraEscalonamentoDTO.getIdContrato() != null &&
						solicitacaoServicoDTO.getIdContrato().intValue() != regraEscalonamentoDTO.getIdContrato().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temSolicitante && regraEscalonamentoDTO.getIdSolicitante() != null || temSolicitante && regraEscalonamentoDTO.getIdSolicitante() != null  &&
						solicitacaoServicoDTO.getIdSolicitante().intValue() != regraEscalonamentoDTO.getIdSolicitante().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temGrupo && regraEscalonamentoDTO.getIdGrupo() != null || temGrupo && regraEscalonamentoDTO.getIdGrupo() != null &&
						solicitacaoServicoDTO.getIdGrupo().intValue() != regraEscalonamentoDTO.getIdGrupo().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temDemandaServico && regraEscalonamentoDTO.getIdTipoDemandaServico() != null || temDemandaServico && regraEscalonamentoDTO.getIdTipoDemandaServico() != null &&
						solicitacaoServicoDTO.getIdTipoDemandaServico().intValue() != regraEscalonamentoDTO.getIdTipoDemandaServico().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if((!temImpacto && regraEscalonamentoDTO.getImpacto() != null && !regraEscalonamentoDTO.getImpacto().isEmpty()) || temImpacto && regraEscalonamentoDTO.getImpacto() != null &&
						!solicitacaoServicoDTO.getImpacto().equalsIgnoreCase(regraEscalonamentoDTO.getImpacto()) && !regraEscalonamentoDTO.getImpacto().isEmpty()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temUrgencia && regraEscalonamentoDTO.getUrgencia() != null && !regraEscalonamentoDTO.getUrgencia().isEmpty()|| temUrgencia && regraEscalonamentoDTO.getUrgencia() != null &&
						!solicitacaoServicoDTO.getUrgencia().equalsIgnoreCase(regraEscalonamentoDTO.getUrgencia()) && !regraEscalonamentoDTO.getUrgencia().isEmpty()) {
					novaLista.remove(regraEscalonamentoDTO);
				}

			}
			return novaLista;
		} else {
			return null;
		}
		
	}
	
	public Collection<RegraEscalonamentoDTO> findRegraByRequisicaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDTO, Integer idTipoGerenciamento) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();
		boolean temContrato = false, temSolicitante = false, temGrupo = false, temImpacto = false, temUrgencia = false;
		sql.append(" SELECT idregraescalonamento, idtipogerenciamento, idservico, idcontrato, idsolicitante, idgrupo, ");
		sql.append(" idtipodemandaservico, urgencia, impacto, ");
		sql.append(" tempoexecucao, intervalonotificacao, enviaremail, criaproblema, prazocriarproblema, tipodataescalonamento FROM regraescalonamento");
		sql.append(" where datafim is null and idtipogerenciamento = ?");
		
		if(requisicaoMudancaDTO.getIdContrato() != null) {
			temContrato = true;
		}
		if(requisicaoMudancaDTO.getIdSolicitante() != null) {
			temSolicitante = true;
		}
		if(requisicaoMudancaDTO.getIdGrupoAtual() != null) {
			temGrupo = true;
		}
		if(requisicaoMudancaDTO.getNivelImpacto() != null) {
			temImpacto = true;
		}
		if(requisicaoMudancaDTO.getNivelUrgencia() != null) {
			temUrgencia = true;
		}
		
	    listRetorno.add("idRegraEscalonamento");
		listRetorno.add("idTipoGerenciamento");
		listRetorno.add("idServico");
		listRetorno.add("idContrato");
		listRetorno.add("idSolicitante");
		listRetorno.add("idGrupo");
		listRetorno.add("idTipoDemandaServico");
		listRetorno.add("urgencia");
		listRetorno.add("impacto");
		listRetorno.add("tempoExecucao");
		listRetorno.add("intervaloNotificacao");
		listRetorno.add("enviarEmail");
		listRetorno.add("criaProblema");
		listRetorno.add("prazoCriarProblema");
		listRetorno.add("tipoDataEscalonamento");
	    
		parametro.add(idTipoGerenciamento);
		
		List list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			
			Collection<RegraEscalonamentoDTO> lista = (Collection<RegraEscalonamentoDTO>) this.listConvertion(getBean(), list, listRetorno);
			Collection<RegraEscalonamentoDTO> novaLista = new ArrayList<RegraEscalonamentoDTO>(lista); 
			for (RegraEscalonamentoDTO regraEscalonamentoDTO : lista) {
				if(!temContrato && regraEscalonamentoDTO.getIdContrato() != null || temContrato && regraEscalonamentoDTO.getIdContrato() != null &&
						requisicaoMudancaDTO.getIdContrato().intValue() != regraEscalonamentoDTO.getIdContrato().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temSolicitante && regraEscalonamentoDTO.getIdSolicitante() != null || temSolicitante && regraEscalonamentoDTO.getIdSolicitante() != null  &&
						requisicaoMudancaDTO.getIdSolicitante().intValue() != regraEscalonamentoDTO.getIdSolicitante().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temGrupo && regraEscalonamentoDTO.getIdGrupo() != null || temGrupo && regraEscalonamentoDTO.getIdGrupo() != null &&
						requisicaoMudancaDTO.getIdGrupoAtual().intValue() != regraEscalonamentoDTO.getIdGrupo().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if((!temImpacto && regraEscalonamentoDTO.getImpacto() != null && !regraEscalonamentoDTO.getImpacto().isEmpty()) || temImpacto && regraEscalonamentoDTO.getImpacto() != null &&
						!requisicaoMudancaDTO.getNivelImpacto().equalsIgnoreCase(regraEscalonamentoDTO.getImpacto()) && !regraEscalonamentoDTO.getImpacto().isEmpty()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temUrgencia && regraEscalonamentoDTO.getUrgencia() != null && !regraEscalonamentoDTO.getUrgencia().isEmpty()|| temUrgencia && regraEscalonamentoDTO.getUrgencia() != null &&
						!requisicaoMudancaDTO.getNivelUrgencia().equalsIgnoreCase(regraEscalonamentoDTO.getUrgencia()) && !regraEscalonamentoDTO.getUrgencia().isEmpty()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				
			}
			return novaLista;
		} else {
			return null;
		}
		
	}
	
	public Collection<RegraEscalonamentoDTO> findRegraByProblema(ProblemaDTO problemaDTO, Integer idTipoGerenciamento) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();
		boolean temContrato = false, temSolicitante = false, temGrupo = false, temImpacto = false, temUrgencia = false;
		sql.append(" SELECT idregraescalonamento, idtipogerenciamento, idservico, idcontrato, idsolicitante, idgrupo, ");
		sql.append(" idtipodemandaservico, urgencia, impacto, ");
		sql.append(" tempoexecucao, intervalonotificacao, enviaremail, criaproblema, prazocriarproblema, tipodataescalonamento FROM regraescalonamento");
		sql.append(" where datafim is null and idtipogerenciamento = ?");
		
		if(problemaDTO.getIdContrato() != null) {
			temContrato = true;
		}
		if(problemaDTO.getIdSolicitante() != null) {
			temSolicitante = true;
		}
		if(problemaDTO.getIdGrupoAtual() != null) {
			temGrupo = true;
		}
		if(problemaDTO.getImpacto() != null) {
			temImpacto = true;
		}
		if(problemaDTO.getUrgencia() != null) {
			temUrgencia = true;
		}
		
	    listRetorno.add("idRegraEscalonamento");
		listRetorno.add("idTipoGerenciamento");
		listRetorno.add("idServico");
		listRetorno.add("idContrato");
		listRetorno.add("idSolicitante");
		listRetorno.add("idGrupo");
		listRetorno.add("idTipoDemandaServico");
		listRetorno.add("urgencia");
		listRetorno.add("impacto");
		listRetorno.add("tempoExecucao");
		listRetorno.add("intervaloNotificacao");
		listRetorno.add("enviarEmail");
		listRetorno.add("criaProblema");
		listRetorno.add("prazoCriarProblema");
		listRetorno.add("tipoDataEscalonamento");
	    
		parametro.add(idTipoGerenciamento);
		
		List list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			
			Collection<RegraEscalonamentoDTO> lista = (Collection<RegraEscalonamentoDTO>) this.listConvertion(getBean(), list, listRetorno);
			Collection<RegraEscalonamentoDTO> novaLista = new ArrayList<RegraEscalonamentoDTO>(lista); 
			for (RegraEscalonamentoDTO regraEscalonamentoDTO : lista) {
				if(!temContrato && regraEscalonamentoDTO.getIdContrato() != null || temContrato && regraEscalonamentoDTO.getIdContrato() != null &&
						problemaDTO.getIdContrato().intValue() != regraEscalonamentoDTO.getIdContrato().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temSolicitante && regraEscalonamentoDTO.getIdSolicitante() != null || temSolicitante && regraEscalonamentoDTO.getIdSolicitante() != null  &&
						problemaDTO.getIdSolicitante().intValue() != regraEscalonamentoDTO.getIdSolicitante().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temGrupo && regraEscalonamentoDTO.getIdGrupo() != null || temGrupo && regraEscalonamentoDTO.getIdGrupo() != null &&
						problemaDTO.getIdGrupoAtual().intValue() != regraEscalonamentoDTO.getIdGrupo().intValue()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if((!temImpacto && regraEscalonamentoDTO.getImpacto() != null && !regraEscalonamentoDTO.getImpacto().isEmpty()) || temImpacto && regraEscalonamentoDTO.getImpacto() != null &&
						!problemaDTO.getImpacto().equalsIgnoreCase(regraEscalonamentoDTO.getImpacto()) && !regraEscalonamentoDTO.getImpacto().isEmpty()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				else if(!temUrgencia && regraEscalonamentoDTO.getUrgencia() != null && !regraEscalonamentoDTO.getUrgencia().isEmpty()|| temUrgencia && regraEscalonamentoDTO.getUrgencia() != null &&
						!problemaDTO.getUrgencia().equalsIgnoreCase(regraEscalonamentoDTO.getUrgencia()) && !regraEscalonamentoDTO.getUrgencia().isEmpty()) {
					novaLista.remove(regraEscalonamentoDTO);
				}
				
			}
			return novaLista;
		} else {
			return null;
		}
		
	}
	
}
