package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RelatorioTop10IncidentesRequisicoesDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.Top10IncidentesRequisicoesDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Top10IncidentesRequisicoesDAO extends CrudDaoDefaultImpl {

	public Top10IncidentesRequisicoesDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	
	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("id", "id", true, true, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("qtde", "qtde", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return null;
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return Top10IncidentesRequisicoesDTO.class;
	}
	
	public ArrayList<Top10IncidentesRequisicoesDTO> listSolicitantesMaisAbriramIncSol(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("id");
			listRetorno.add("descricao");
			listRetorno.add("qtde");
			
			boolean seLimita = ((relatorioTop10IncidentesRequisicoesDTO.getTopList()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getTopList().intValue()>0));
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))){
				sql.append("TOP "+relatorioTop10IncidentesRequisicoesDTO.getTopList().toString()+" ");
			}
			
			sql.append("ss.idsolicitante as id, e.nome as descricao, count(ss.idsolicitacaoservico) as qtde ");
			sql.append("from servicocontrato as sc join solicitacaoservico as ss on ");
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
				sql.append("sc.idcontrato=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
			}

			if ((relatorioTop10IncidentesRequisicoesDTO.getIdServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdServico().intValue()>0)){
				sql.append("sc.idservico=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdServico());
			}			
			
			sql.append("sc.idservicocontrato = ss.idservicocontrato ");
			sql.append("and (ss.datahorasolicitacao between ? and ?) ");
			parametro.add(UtilDatas.getSqlDate(relatorioTop10IncidentesRequisicoesDTO.getDataInicial()));
			parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioTop10IncidentesRequisicoesDTO.getDataFinal()));
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico().intValue()>0)){
				sql.append("and ss.idtipodemandaservico=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
				sql.append("and ss.idprioridade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdUnidade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade().intValue()>0)){
				sql.append("and ss.idunidade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade());
			}			
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getSituacao()!=null)&&(!relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase("0"))&&(relatorioTop10IncidentesRequisicoesDTO.getSituacao().length()>0)){
				sql.append("and UPPER(ss.situacao)=UPPER(?) ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getSituacao());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
				sql.append("and ss.idorigem=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
			}
			
			sql.append("join servico as s on sc.idservico = s.idservico ");
			sql.append("join empregados as e on ss.idsolicitante=idempregado ");
			
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))){
				sql.append("WHERE ROWNUM <= ? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getTopList());
			}
			
			sql.append("group by ss.idsolicitante, e.nome ");
			sql.append("order by qtde desc, descricao, idsolicitante");
			
			if ((seLimita)&&((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL)))){
				sql.append(" LIMIT ?");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getTopList());
			}
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<Top10IncidentesRequisicoesDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<Top10IncidentesRequisicoesDTO>() : result);
	}

	public Collection<SolicitacaoServicoDTO> listDetalheSolicitanteMaisAbriuIncSol(Integer idSolicitante, RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("idSolicitacaoServico");
			listRetorno.add("nomeServico");
			listRetorno.add("situacao");
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ss.idsolicitacaoservico, s.nomeservico, ss.situacao ");
			sql.append("from servicocontrato as sc join solicitacaoservico as ss on ");
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
				sql.append("sc.idcontrato=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
			}

			if ((relatorioTop10IncidentesRequisicoesDTO.getIdServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdServico().intValue()>0)){
				sql.append("sc.idservico=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdServico());
			}			
			
			sql.append("sc.idservicocontrato = ss.idservicocontrato ");
			sql.append("and ss.idsolicitante = ? ");
			parametro.add(idSolicitante);
			sql.append("and (ss.datahorasolicitacao between ? and ?) ");
			parametro.add(UtilDatas.getSqlDate(relatorioTop10IncidentesRequisicoesDTO.getDataInicial()));
			parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioTop10IncidentesRequisicoesDTO.getDataFinal()));
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico().intValue()>0)){
				sql.append("and ss.idtipodemandaservico=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
				sql.append("and ss.idprioridade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdUnidade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade().intValue()>0)){
				sql.append("and ss.idunidade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade());
			}			
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getSituacao()!=null)&&(!relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase("0"))&&(relatorioTop10IncidentesRequisicoesDTO.getSituacao().length()>0)){
				sql.append("and UPPER(ss.situacao)=UPPER(?) ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getSituacao());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
				sql.append("and ss.idorigem=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
			}
			sql.append("join servico as s on sc.idservico = s.idservico ");
			sql.append("order by ss.idsolicitacaoservico, s.nomeservico, ss.situacao");
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(SolicitacaoServicoDTO.class, resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<SolicitacaoServicoDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<SolicitacaoServicoDTO>() : result);
	}
	
	public ArrayList<Top10IncidentesRequisicoesDTO> listGruposMaisResolveramIncSol(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("id");
			listRetorno.add("descricao");
			listRetorno.add("qtde");
			
			boolean seLimita = ((relatorioTop10IncidentesRequisicoesDTO.getTopList()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getTopList().intValue()>0));
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))){
				sql.append("TOP "+relatorioTop10IncidentesRequisicoesDTO.getTopList().toString()+" ");
			}
			
			sql.append("idgrupo as id, nome as descricao, count(idsolicitacaoservico) as qtde ");
			sql.append("from ");
				sql.append("(select distinct bpm_atribuicaofluxo.idgrupo, grupo.nome, solicitacaoservico.idsolicitacaoservico, servico.nomeservico, bpm_instanciafluxo.situacao,  empregados.nome as responsavel ");
				 sql.append("from solicitacaoservico join execucaosolicitacao on ");
				 if ((relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico().intValue()>0)){
					sql.append("solicitacaoservico.idtipodemandaservico=? and ");
					parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico());
				 }
					
				 if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
					sql.append("solicitacaoservico.idprioridade=? and ");
					parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
				 }
					
				 if ((relatorioTop10IncidentesRequisicoesDTO.getIdUnidade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade().intValue()>0)){
					sql.append("solicitacaoservico.idunidade=? and ");
					parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade());
				 }			
					
				 if ((relatorioTop10IncidentesRequisicoesDTO.getSituacao()!=null)&&(!relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase("0"))&&(relatorioTop10IncidentesRequisicoesDTO.getSituacao().length()>0)){
					sql.append("UPPER(solicitacaoservico.situacao)=UPPER(?) and ");
					parametro.add(relatorioTop10IncidentesRequisicoesDTO.getSituacao());
				 }
					
				 if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
					sql.append("solicitacaoservico.idorigem=? and ");
					parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
				 }
				 
				 sql.append("UPPER(solicitacaoservico.situacao)=UPPER('"+Enumerados.SituacaoSolicitacaoServico.Fechada.toString()+"') and solicitacaoservico.idsolicitacaoservico=execucaosolicitacao.idsolicitacaoservico ");
				                         sql.append("join bpm_instanciafluxo on  execucaosolicitacao.idinstanciafluxo = bpm_instanciafluxo.idinstancia and UPPER(bpm_instanciafluxo.situacao) = UPPER('"+br.com.centralit.bpm.util.Enumerados.INSTANCIA_ENCERRADA+"') ");
				                         sql.append("join bpm_itemtrabalhofluxo on ");
				                         											sql.append("(bpm_itemtrabalhofluxo.datahorafinalizacao between ? and ?) and ");
				                         											parametro.add(UtilDatas.getSqlDate(relatorioTop10IncidentesRequisicoesDTO.getDataInicial()));
				                         											parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioTop10IncidentesRequisicoesDTO.getDataFinal()));
				                         											sql.append("execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia and (bpm_itemtrabalhofluxo.idresponsavelatual is not null) ");
				                         sql.append("join bpm_atribuicaofluxo on (bpm_atribuicaofluxo.idgrupo is not null) and bpm_itemtrabalhofluxo.iditemtrabalho = bpm_atribuicaofluxo.iditemtrabalho and UPPER(bpm_atribuicaofluxo.tipo) = UPPER('"+br.com.centralit.bpm.util.Enumerados.TipoAtribuicao.Automatica.toString()+"') ");
				                         sql.append("join servicocontrato on ");
				                         if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
				         					sql.append("servicocontrato.idcontrato=? and ");
				         					parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
				         				 }
				                         if ((relatorioTop10IncidentesRequisicoesDTO.getIdServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdServico().intValue()>0)){
				             				sql.append("servicocontrato.idservico=? and ");
				             				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdServico());
				             			 }
				                         sql.append("solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
				                         sql.append("join servico on servicocontrato.idservico = servico.idservico ");
				                         sql.append("join empregados on bpm_itemtrabalhofluxo.idresponsavelatual=idempregado ");
				                         sql.append("left join grupo on grupo.idgrupo = bpm_atribuicaofluxo.idgrupo ");
		    if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))){
				sql.append("WHERE ROWNUM <= ? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getTopList());
			}
		    if(!CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))
		    	sql.append("order by bpm_atribuicaofluxo.idgrupo,solicitacaoservico.idsolicitacaoservico ");
		    sql.append(") as t ");
			sql.append("group by idgrupo, nome ");
			sql.append("order by qtde desc, descricao, id");
			
			if ((seLimita)&&((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL)))){
				sql.append(" LIMIT ?");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getTopList());
			}
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<Top10IncidentesRequisicoesDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<Top10IncidentesRequisicoesDTO>() : result);
	}
	
	public ArrayList<SolicitacaoServicoDTO> listDetalheGruposMaisResolveramIncSol(Integer idGrupo, RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("idSolicitacaoServico");
			listRetorno.add("nomeServico");
			listRetorno.add("situacao");
			listRetorno.add("responsavel");
			
			StringBuilder sql = new StringBuilder();
			sql.append("select distinct solicitacaoservico.idsolicitacaoservico, servico.nomeservico, bpm_instanciafluxo.situacao,  empregados.nome as responsavel ");
			sql.append("from solicitacaoservico join execucaosolicitacao on ");
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico().intValue()>0)){
				sql.append("solicitacaoservico.idtipodemandaservico=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico());
			}
					
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
				sql.append("solicitacaoservico.idprioridade=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
			}
					
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdUnidade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade().intValue()>0)){
				sql.append("solicitacaoservico.idunidade=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade());
			}			
					
			if ((relatorioTop10IncidentesRequisicoesDTO.getSituacao()!=null)&&(!relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase("0"))&&(relatorioTop10IncidentesRequisicoesDTO.getSituacao().length()>0)){
				sql.append("UPPER(solicitacaoservico.situacao)=UPPER(?) and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getSituacao());
			}
					
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
				sql.append("solicitacaoservico.idorigem=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
			}
				 
				sql.append("UPPER(solicitacaoservico.situacao)=UPPER('"+Enumerados.SituacaoSolicitacaoServico.Fechada.toString()+"') and solicitacaoservico.idsolicitacaoservico=execucaosolicitacao.idsolicitacaoservico ");
			    sql.append("join bpm_instanciafluxo on  execucaosolicitacao.idinstanciafluxo = bpm_instanciafluxo.idinstancia and UPPER(bpm_instanciafluxo.situacao = '"+br.com.centralit.bpm.util.Enumerados.INSTANCIA_ENCERRADA+"') ");
				sql.append("join bpm_itemtrabalhofluxo on ");
														sql.append("(bpm_itemtrabalhofluxo.datahorafinalizacao between ? and ?) and ");
														parametro.add(UtilDatas.getSqlDate(relatorioTop10IncidentesRequisicoesDTO.getDataInicial()));
														parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioTop10IncidentesRequisicoesDTO.getDataFinal()));
														sql.append("execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia and (bpm_itemtrabalhofluxo.idresponsavelatual is not null) ");
				sql.append("join bpm_atribuicaofluxo on (bpm_atribuicaofluxo.idgrupo = ?) and bpm_itemtrabalhofluxo.iditemtrabalho = bpm_atribuicaofluxo.iditemtrabalho and bpm_atribuicaofluxo.tipo = '"+br.com.centralit.bpm.util.Enumerados.TipoAtribuicao.Automatica.toString()+"' ");
				parametro.add(idGrupo);
				sql.append("join servicocontrato on ");
							if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
								sql.append("servicocontrato.idcontrato=? and ");
								parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
							}
							if ((relatorioTop10IncidentesRequisicoesDTO.getIdServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdServico().intValue()>0)){
				   				sql.append("servicocontrato.idservico=? and ");
				   				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdServico());
				   		    }
				            sql.append("solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
				            sql.append("join servico on servicocontrato.idservico = servico.idservico ");
				            sql.append("join empregados on bpm_itemtrabalhofluxo.idresponsavelatual=idempregado ");
			sql.append("order by idsolicitacaoservico, nomeservico, situacao, responsavel");
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(SolicitacaoServicoDTO.class, resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<SolicitacaoServicoDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<SolicitacaoServicoDTO>() : result);
	}
	
	public ArrayList<Top10IncidentesRequisicoesDTO> listReqIncMaisSolicitados(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("id");
			listRetorno.add("descricao");
			listRetorno.add("idServico");
			listRetorno.add("nomeServico");
			listRetorno.add("qtde");
			
			boolean seLimita = ((relatorioTop10IncidentesRequisicoesDTO.getTopList()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getTopList().intValue()>0));
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))){
				sql.append("TOP "+relatorioTop10IncidentesRequisicoesDTO.getTopList().toString()+" ");
			}
			
			sql.append("ss.idtipodemandaservico as id, t.nometipodemandaservico as descricao, s.idservico, s.nomeservico, count(ss.idsolicitacaoservico) as qtde ");
			sql.append("from servicocontrato as sc join solicitacaoservico as ss on ");
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdSolicitante() !=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdSolicitante().intValue()>0)){
				sql.append("ss.idsolicitante=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdSolicitante());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
				sql.append("sc.idcontrato=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
			}

			/*if ((relatorioTop10IncidentesRequisicoesDTO.getIdServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdServico().intValue()>0)){
				sql.append("sc.idservico=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdServico());
			}*/			
			
			sql.append("sc.idservicocontrato = ss.idservicocontrato ");
			sql.append("and (ss.datahorasolicitacao between ? and ?) ");
			parametro.add(UtilDatas.getSqlDate(relatorioTop10IncidentesRequisicoesDTO.getDataInicial()));
			parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioTop10IncidentesRequisicoesDTO.getDataFinal()));
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico().intValue()>0)){
				sql.append("and ss.idtipodemandaservico=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
				sql.append("and ss.idprioridade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdUnidade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade().intValue()>0)){
				sql.append("and ss.idunidade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade());
			}			
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getSituacao()!=null)&&(!relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase("0"))&&(relatorioTop10IncidentesRequisicoesDTO.getSituacao().length()>0)){
				sql.append("and UPPER(ss.situacao)=UPPER(?) ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getSituacao());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
				sql.append("and ss.idorigem=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
			}
			
			sql.append("join servico as s on sc.idservico = s.idservico ");
			sql.append("join empregados as e on ss.idsolicitante=idempregado ");
			sql.append("join tipodemandaservico as t on ss.idtipodemandaservico = t.idtipodemandaservico ");
			
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))){
				sql.append("WHERE ROWNUM <= ? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getTopList());
			}
			
			sql.append("group by ss.idtipodemandaservico, t.nometipodemandaservico, s.idservico, s.nomeservico ");
			sql.append("order by qtde desc,nometipodemandaservico,nomeservico");
			
			if ((seLimita)&&((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL)))){
				sql.append(" LIMIT ?");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getTopList());
			}
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<Top10IncidentesRequisicoesDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<Top10IncidentesRequisicoesDTO>() : result);
	}

	public ArrayList<SolicitacaoServicoDTO> listDetalheReqIncMaisSolicitados(Integer idTipoDemanda, Integer idServico, RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("idSolicitacaoServico");
			listRetorno.add("nomeServico");
			listRetorno.add("situacao");
			listRetorno.add("nomeSolicitante");
			listRetorno.add("nomeGrupo");
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ss.idsolicitacaoservico, s.nomeservico, ss.situacao, e.nome as nomesolicitante, grupo.nome as nomegrupo ");
			sql.append("from servicocontrato as sc join solicitacaoservico as ss on ");
			
			sql.append("sc.idservico=? and ");
			parametro.add(idServico);
			
			sql.append("ss.idtipodemandaservico=? and ");
			parametro.add(idTipoDemanda);
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdSolicitante() !=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdSolicitante().intValue()>0)){
				sql.append("ss.idsolicitante=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdSolicitante());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
				sql.append("sc.idcontrato=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
			}

			sql.append("sc.idservicocontrato = ss.idservicocontrato ");
			sql.append("and (ss.datahorasolicitacao between ? and ?) ");
			parametro.add(UtilDatas.getSqlDate(relatorioTop10IncidentesRequisicoesDTO.getDataInicial()));
			parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioTop10IncidentesRequisicoesDTO.getDataFinal()));
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
				sql.append("and ss.idprioridade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdUnidade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade().intValue()>0)){
				sql.append("and ss.idunidade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade());
			}			
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getSituacao()!=null)&&(!relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase("0"))&&(relatorioTop10IncidentesRequisicoesDTO.getSituacao().length()>0)){
				sql.append("and UPPER(ss.situacao)=UPPER(?) ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getSituacao());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
				sql.append("and ss.idorigem=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
			}
			
			sql.append("join servico as s on sc.idservico = s.idservico ");
			sql.append("join empregados as e on ss.idsolicitante=idempregado ");
			sql.append("join tipodemandaservico as t on ss.idtipodemandaservico = t.idtipodemandaservico ");
			sql.append("left join grupo on grupo.idgrupo = ss.idgrupoatual ");
			
			sql.append("order by ss.idsolicitacaoservico, s.nomeservico, ss.situacao, e.nome, grupo.nome");
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(SolicitacaoServicoDTO.class, resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<SolicitacaoServicoDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<SolicitacaoServicoDTO>() : result);
	}
	
	public ArrayList<Top10IncidentesRequisicoesDTO> listUnidadesMaisAbriramReqInc(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("id");
			listRetorno.add("descricao");
			listRetorno.add("qtde");
			
			boolean seLimita = ((relatorioTop10IncidentesRequisicoesDTO.getTopList()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getTopList().intValue()>0));
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))){
				sql.append("TOP "+relatorioTop10IncidentesRequisicoesDTO.getTopList().toString()+" ");
			}
			
			sql.append("ss.idunidade as id, unidade.nome as descricao, count(ss.idsolicitacaoservico) as qtde ");
			
			sql.append("from servicocontrato as sc join solicitacaoservico as ss on ");
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
				sql.append("sc.idcontrato=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
			}

			if ((relatorioTop10IncidentesRequisicoesDTO.getIdServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdServico().intValue()>0)){
				sql.append("sc.idservico=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdServico());
			}			
			
			sql.append("sc.idservicocontrato = ss.idservicocontrato ");
			sql.append("and (ss.datahorasolicitacao between ? and ?) ");
			parametro.add(UtilDatas.getSqlDate(relatorioTop10IncidentesRequisicoesDTO.getDataInicial()));
			parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioTop10IncidentesRequisicoesDTO.getDataFinal()));
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico().intValue()>0)){
				sql.append("and ss.idtipodemandaservico=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
				sql.append("and ss.idprioridade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
			}
			
			/*if ((relatorioTop10IncidentesRequisicoesDTO.getIdUnidade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade().intValue()>0)){
				sql.append("and ss.idunidade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade());
			}*/			
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getSituacao()!=null)&&(!relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase("0"))&&(relatorioTop10IncidentesRequisicoesDTO.getSituacao().length()>0)){
				sql.append("and UPPER(ss.situacao)=UPPER(?) ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getSituacao());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
				sql.append("and ss.idorigem=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
			}
			
			sql.append("join servico as s on sc.idservico = s.idservico ");
			sql.append("join empregados as e on ss.idsolicitante=idempregado ");
			sql.append("join tipodemandaservico as t on ss.idtipodemandaservico = t.idtipodemandaservico ");
			sql.append("join unidade on unidade.idunidade=ss.idunidade ");
			
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))){
				sql.append("WHERE ROWNUM <= ? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getTopList());
			}
			
			sql.append("group by ss.idunidade, unidade.nome ");
			sql.append("order by qtde desc, descricao");
			
			if ((seLimita)&&((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL)))){
				sql.append(" LIMIT ?");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getTopList());
			}
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<Top10IncidentesRequisicoesDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<Top10IncidentesRequisicoesDTO>() : result);
	}

	public ArrayList<SolicitacaoServicoDTO> listDetalheUnidadesMaisAbriramReqInc(Integer idUnidade, RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("idSolicitacaoServico");
			listRetorno.add("nomeServico");
			listRetorno.add("situacao");
			listRetorno.add("nomeTipoDemandaServico");
			listRetorno.add("nomeGrupo");
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ss.idsolicitacaoservico, s.nomeservico, ss.situacao, t.nometipodemandaservico, grupo.nome as nomegrupo ");
			sql.append("from servicocontrato as sc join solicitacaoservico as ss on ");
			
			sql.append("ss.idunidade=? and ");
			parametro.add(idUnidade);
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
				sql.append("sc.idcontrato=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
			}

			if ((relatorioTop10IncidentesRequisicoesDTO.getIdServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdServico().intValue()>0)){
				sql.append("sc.idservico=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdServico());
			}			
			
			sql.append("sc.idservicocontrato = ss.idservicocontrato ");
			sql.append("and (ss.datahorasolicitacao between ? and ?) ");
			parametro.add(UtilDatas.getSqlDate(relatorioTop10IncidentesRequisicoesDTO.getDataInicial()));
			parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioTop10IncidentesRequisicoesDTO.getDataFinal()));
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico().intValue()>0)){
				sql.append("and ss.idtipodemandaservico=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
				sql.append("and ss.idprioridade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getSituacao()!=null)&&(!relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase("0"))&&(relatorioTop10IncidentesRequisicoesDTO.getSituacao().length()>0)){
				sql.append("and UPPER(ss.situacao)=UPPER(?) ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getSituacao());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
				sql.append("and ss.idorigem=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
			}
			
			sql.append("join servico as s on sc.idservico = s.idservico ");
			sql.append("join empregados as e on ss.idsolicitante=idempregado ");
			sql.append("join tipodemandaservico as t on ss.idtipodemandaservico = t.idtipodemandaservico ");
			sql.append("join unidade on unidade.idunidade=ss.idunidade ");
			sql.append("left join grupo on grupo.idgrupo = ss.idgrupoatual ");
			sql.append("order by ss.idsolicitacaoservico, s.nomeservico, ss.situacao, t.nometipodemandaservico, grupo.nome");
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(SolicitacaoServicoDTO.class, resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<SolicitacaoServicoDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<SolicitacaoServicoDTO>() : result);
	}

	public ArrayList<Top10IncidentesRequisicoesDTO> listLocMaisAbriramReqInc(RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("id");
			listRetorno.add("descricao");
			listRetorno.add("nomeServico");
			listRetorno.add("qtde");
			
			boolean seLimita = ((relatorioTop10IncidentesRequisicoesDTO.getTopList()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getTopList().intValue()>0));
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ");
			
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))){
				sql.append("TOP "+relatorioTop10IncidentesRequisicoesDTO.getTopList().toString()+" ");
			}
			
			sql.append("localidade.idlocalidade as id, localidade.nomelocalidade as descricao, unidade.nome as nomeServico, count(ss.idsolicitacaoservico) as qtde ");
			sql.append("from servicocontrato as sc join solicitacaoservico as ss on ");
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
				sql.append("sc.idcontrato=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
			}

			if ((relatorioTop10IncidentesRequisicoesDTO.getIdServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdServico().intValue()>0)){
				sql.append("sc.idservico=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdServico());
			}			
			
			sql.append("sc.idservicocontrato = ss.idservicocontrato ");
			sql.append("and (ss.datahorasolicitacao between ? and ?) ");
			parametro.add(UtilDatas.getSqlDate(relatorioTop10IncidentesRequisicoesDTO.getDataInicial()));
			parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioTop10IncidentesRequisicoesDTO.getDataFinal()));
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico().intValue()>0)){
				sql.append("and ss.idtipodemandaservico=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
				sql.append("and ss.idprioridade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdUnidade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade().intValue()>0)){
				sql.append("and ss.idunidade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade());
			}		
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getSituacao()!=null)&&(!relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase("0"))&&(relatorioTop10IncidentesRequisicoesDTO.getSituacao().length()>0)){
				sql.append("and UPPER(ss.situacao)=UPPER(?) ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getSituacao());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
				sql.append("and ss.idorigem=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
			}
			
			sql.append("join servico as s on sc.idservico = s.idservico ");
			sql.append("join tipodemandaservico as t on ss.idtipodemandaservico = t.idtipodemandaservico ");
			sql.append("join contatosolicitacaoservico on contatosolicitacaoservico.idcontatosolicitacaoservico = ss.idcontatosolicitacaoservico ");
			sql.append("join localidade on contatosolicitacaoservico.idlocalidade = localidade.idlocalidade ");
            
			sql.append("left join localidadeunidade on contatosolicitacaoservico.idlocalidade = localidadeunidade.idlocalidade ");
			sql.append("left join unidade on localidadeunidade.idunidade = unidade.idunidade ");
                    
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))){
				sql.append("WHERE ROWNUM <= ? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getTopList());
			}
			
			sql.append("group by localidade.idlocalidade, localidade.nomelocalidade, unidade.nome ");
			sql.append("order by qtde desc, descricao");
			
			if ((seLimita)&&((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL)))){
				sql.append(" LIMIT ?");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getTopList());
			}
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<Top10IncidentesRequisicoesDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<Top10IncidentesRequisicoesDTO>() : result);
	}
	
	public Collection<SolicitacaoServicoDTO> listDetalheLocMaisAbriramReqInc(Integer idLocalidade, RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			listRetorno.add("idSolicitacaoServico");
			listRetorno.add("nomeServico");
			listRetorno.add("situacao");
			listRetorno.add("nomeTipoDemandaServico");
			listRetorno.add("nomeGrupo");
			
			StringBuilder sql = new StringBuilder();
			sql.append("select ss.idsolicitacaoservico, s.nomeservico, ss.situacao, t.nometipodemandaservico, grupo.nome as nomeGrupo ");
			sql.append("from servicocontrato as sc join solicitacaoservico as ss on ");
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdContrato()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdContrato().intValue()>0)){
				sql.append("sc.idcontrato=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdContrato());
			}

			if ((relatorioTop10IncidentesRequisicoesDTO.getIdServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdServico().intValue()>0)){
				sql.append("sc.idservico=? and ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdServico());
			}			
			
			sql.append("sc.idservicocontrato = ss.idservicocontrato ");
			sql.append("and (ss.datahorasolicitacao between ? and ?) ");
			parametro.add(UtilDatas.getSqlDate(relatorioTop10IncidentesRequisicoesDTO.getDataInicial()));
			parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioTop10IncidentesRequisicoesDTO.getDataFinal()));
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico().intValue()>0)){
				sql.append("and ss.idtipodemandaservico=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdTipoDemandaServico());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade().intValue()>0)){
				sql.append("and ss.idprioridade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdPrioridade());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdUnidade()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade().intValue()>0)){
				sql.append("and ss.idunidade=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdUnidade());
			}		
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getSituacao()!=null)&&(!relatorioTop10IncidentesRequisicoesDTO.getSituacao().equalsIgnoreCase("0"))&&(relatorioTop10IncidentesRequisicoesDTO.getSituacao().length()>0)){
				sql.append("and UPPER(ss.situacao)=UPPER(?) ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getSituacao());
			}
			
			if ((relatorioTop10IncidentesRequisicoesDTO.getIdOrigem()!=null)&&(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem().intValue()>0)){
				sql.append("and ss.idorigem=? ");
				parametro.add(relatorioTop10IncidentesRequisicoesDTO.getIdOrigem());
			}
			
			sql.append("join servico as s on sc.idservico = s.idservico ");
			sql.append("join tipodemandaservico as t on ss.idtipodemandaservico = t.idtipodemandaservico ");
			sql.append("join contatosolicitacaoservico on contatosolicitacaoservico.idcontatosolicitacaoservico = ss.idcontatosolicitacaoservico ");
			
			sql.append(" and contatosolicitacaoservico.idlocalidade=? ");
			parametro.add(idLocalidade);
			
			sql.append("join localidade on contatosolicitacaoservico.idlocalidade = localidade.idlocalidade ");
			sql.append("left join grupo on grupo.idgrupo = ss.idgrupoatual ");
                    
			sql.append("order by ss.idsolicitacaoservico, s.nomeservico, ss.situacao, t.nometipodemandaservico, grupo.nome");
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(SolicitacaoServicoDTO.class, resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<SolicitacaoServicoDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<SolicitacaoServicoDTO>() : result);
	}



}