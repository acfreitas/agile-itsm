package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AnaliseTendenciasDTO;
import br.com.centralit.citcorpore.bean.TendenciaDTO;
import br.com.centralit.citcorpore.bean.TendenciaGanttDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

/**
 * @author euler.ramos
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class AnaliseTendenciasDAO extends CrudDaoDefaultImpl {

	public AnaliseTendenciasDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		return null;
	}

	@Override
	public String getTableName() {
		return "solicitacaoservico";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return TendenciaDTO.class;
	}

	//Método criado para completar as consultas do gráfico com as datas que não tiveram lançamento de solicitações de serviço
	//É utilizado apenas nos bancos que não são o PostgreSQL
	public ArrayList<TendenciaGanttDTO> montarPeriodo(AnaliseTendenciasDTO analiseTendenciasDTO, ArrayList<TendenciaGanttDTO> resultado ) {
		ArrayList<TendenciaGanttDTO> listaPeriodo = new ArrayList<TendenciaGanttDTO>();
		try {
        	   Date DataInicio = null;
        	   Timestamp DataFim = null;
               TendenciaGanttDTO registroGrafico;

               if (analiseTendenciasDTO.getDataInicio()!=null){
                      DataInicio = UtilDatas.getSqlDate(analiseTendenciasDTO.getDataInicio());
               }

               if (analiseTendenciasDTO.getDataFim()!=null){
                      DataFim = UtilDatas.getTimeStampComUltimaHoraDoDia(analiseTendenciasDTO.getDataFim());
               }

               Date DataAux = DataInicio;
               do{
            	   registroGrafico = new TendenciaGanttDTO();
            	   registroGrafico.setData(DataAux);
            	   registroGrafico.setQtde(0);

            	   if((resultado!=null)&&(resultado.size()>0)){
            		   for (TendenciaGanttDTO tendenciaGanttDTO : resultado) {
            			   if (tendenciaGanttDTO.getData().equals(DataAux)){
            				   registroGrafico.setQtde(tendenciaGanttDTO.getQtde());
            			   }
            		   }
            	   }

            	   listaPeriodo.add(registroGrafico);

            	   if(DataAux.before(DataFim)){
            		   java.util.Date utilDate = UtilDatas.incrementaDiasEmData(DataAux, 1);
            		   DataAux = new java.sql.Date(utilDate.getTime());
                   }
               }while(DataAux.before(DataFim));
        } catch (Exception e) {
               System.out.println(e);
        }
		return listaPeriodo;
    }

	public List<TendenciaDTO> buscarTendenciasServico(AnaliseTendenciasDTO analiseTendenciasDTO){
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();

			java.sql.Date DataInicio = null;
			Timestamp DataFim = null;

			if (analiseTendenciasDTO.getDataInicio()!=null){
				DataInicio = UtilDatas.getSqlDate(analiseTendenciasDTO.getDataInicio());
			}

			if (analiseTendenciasDTO.getDataFim()!=null){
				DataFim = UtilDatas.getTimeStampComUltimaHoraDoDia(analiseTendenciasDTO.getDataFim());
			}

			listRetorno.add("id");
			listRetorno.add("descricao");
			listRetorno.add("qtdeCritica");

			StringBuilder sql = new StringBuilder();
			sql.append("select ");

			sql.append("servico.idservico id, servico.nomeservico descricao, a.qtde qtdecritica ");
			sql.append("from servico join ");
			                  sql.append("(select servicocontrato.idservico,count(solicitacaoservico.idsolicitacaoservico) as qtde ");
			                   sql.append("from solicitacaoservico join servicocontrato on ");
			if ((DataInicio!=null) && (DataFim!=null)){
                									sql.append("(solicitacaoservico.datahorasolicitacao between ? and ?) and ");
                									parametro.add(DataInicio);
                									parametro.add(DataFim);
			}

			sql.append("solicitacaoservico.situacao <> ? and ");
			parametro.add(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Cancelada.getDescricao());

			sql.append("solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
			if ((analiseTendenciasDTO.getIdContrato()!=null)&&(analiseTendenciasDTO.getIdContrato().intValue()>0)){
													sql.append("and servicocontrato.idcontrato = ? ");
													parametro.add(analiseTendenciasDTO.getIdContrato());
			}
			if ((analiseTendenciasDTO.getIdServico()!=null)&&(analiseTendenciasDTO.getIdServico().intValue()>0)){
													sql.append("and servicocontrato.idservico = ? ");
													parametro.add(analiseTendenciasDTO.getIdServico());
			}
			if ((analiseTendenciasDTO.getIdGrupoExecutor()!=null)&&(analiseTendenciasDTO.getIdGrupoExecutor().intValue()>0)){
													sql.append("and solicitacaoservico.idgrupoatual = ? ");
													parametro.add(analiseTendenciasDTO.getIdGrupoExecutor());
			}

			if ((analiseTendenciasDTO.getIdEmpregado()!=null)&&(analiseTendenciasDTO.getIdEmpregado().intValue()>0)){
													sql.append("and solicitacaoservico.idsolicitante = ? ");
													parametro.add(analiseTendenciasDTO.getIdEmpregado());
			}

			if ((analiseTendenciasDTO.getIdTipoDemandaServico()!=null)&&(analiseTendenciasDTO.getIdTipoDemandaServico().intValue()>0)){
													sql.append("and solicitacaoservico.idtipodemandaservico = ? ");
													parametro.add(analiseTendenciasDTO.getIdTipoDemandaServico());
			}

			if ((analiseTendenciasDTO.getUrgencia()!=null)&&(analiseTendenciasDTO.getUrgencia().length()>0)){
													sql.append("and solicitacaoservico.urgencia = ? ");
													parametro.add(analiseTendenciasDTO.getUrgencia());
			}

			if ((analiseTendenciasDTO.getImpacto()!=null)&&(analiseTendenciasDTO.getImpacto().length()>0)){
													sql.append("and solicitacaoservico.impacto = ? ");
													parametro.add(analiseTendenciasDTO.getImpacto());
			}

			if ((analiseTendenciasDTO.getIdCausaIncidente()!=null)&&(analiseTendenciasDTO.getIdCausaIncidente().intValue()>0)){
													sql.append("and solicitacaoservico.idcausaincidente = ? ");
													parametro.add(analiseTendenciasDTO.getIdCausaIncidente());
			}

			if ((analiseTendenciasDTO.getIdItemConfiguracao()!=null)&&(analiseTendenciasDTO.getIdItemConfiguracao().intValue()>0)){
									sql.append("join itemcfgsolicitacaoserv on solicitacaoservico.idsolicitacaoservico = itemcfgsolicitacaoserv.idsolicitacaoservico and ");
																		sql.append("itemcfgsolicitacaoserv.iditemconfiguracao = ? and ");
																		sql.append("(itemcfgsolicitacaoserv.datafim is null) ");
									parametro.add(analiseTendenciasDTO.getIdItemConfiguracao());
			}

			sql.append("group by servicocontrato.idservico) a on servico.idservico = a.idservico ");

			if ((analiseTendenciasDTO.getQtdeCritica() !=null)&&(analiseTendenciasDTO.getQtdeCritica().intValue()>0)){
				sql.append("and qtde >= ? ");
				parametro.add(analiseTendenciasDTO.getQtdeCritica());
			}
			sql.append("order by qtde desc, descricao");

			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<TendenciaDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<TendenciaDTO>() : result);
	}

	public List<TendenciaDTO> buscarTendenciasCausa(AnaliseTendenciasDTO analiseTendenciasDTO){
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();

			java.sql.Date DataInicio = null;
			Timestamp DataFim = null;

			if (analiseTendenciasDTO.getDataInicio()!=null){
				DataInicio = UtilDatas.getSqlDate(analiseTendenciasDTO.getDataInicio());
			}

			if (analiseTendenciasDTO.getDataFim()!=null){
				DataFim = UtilDatas.getTimeStampComUltimaHoraDoDia(analiseTendenciasDTO.getDataFim());
			}

			listRetorno.add("id");
			listRetorno.add("descricao");
			listRetorno.add("qtdeCritica");

			StringBuilder sql = new StringBuilder();
			sql.append("select ");

			sql.append("causaincidente.idcausaincidente id, causaincidente.descricaocausa descricao, a.qtde qtdecritica ");
			sql.append("from causaincidente join ");
			                  sql.append("(select solicitacaoservico.idcausaincidente,count(solicitacaoservico.idsolicitacaoservico) as qtde ");
			                   sql.append("from solicitacaoservico join servicocontrato on ");
			if ((DataInicio!=null) && (DataFim!=null)){
                									sql.append("(solicitacaoservico.datahorasolicitacao between ? and ?) and ");
                									parametro.add(DataInicio);
                									parametro.add(DataFim);
			}

			sql.append("solicitacaoservico.situacao <> ? and ");
			parametro.add(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Cancelada.getDescricao());

			sql.append("solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
			if ((analiseTendenciasDTO.getIdContrato()!=null)&&(analiseTendenciasDTO.getIdContrato().intValue()>0)){
													sql.append("and servicocontrato.idcontrato = ? ");
													parametro.add(analiseTendenciasDTO.getIdContrato());
			}
			if ((analiseTendenciasDTO.getIdServico()!=null)&&(analiseTendenciasDTO.getIdServico().intValue()>0)){
													sql.append("and servicocontrato.idservico = ? ");
													parametro.add(analiseTendenciasDTO.getIdServico());
			}
			if ((analiseTendenciasDTO.getIdGrupoExecutor()!=null)&&(analiseTendenciasDTO.getIdGrupoExecutor().intValue()>0)){
													sql.append("and solicitacaoservico.idgrupoatual = ? ");
													parametro.add(analiseTendenciasDTO.getIdGrupoExecutor());
			}

			if ((analiseTendenciasDTO.getIdEmpregado()!=null)&&(analiseTendenciasDTO.getIdEmpregado().intValue()>0)){
													sql.append("and solicitacaoservico.idsolicitante = ? ");
													parametro.add(analiseTendenciasDTO.getIdEmpregado());
			}

			if ((analiseTendenciasDTO.getIdTipoDemandaServico()!=null)&&(analiseTendenciasDTO.getIdTipoDemandaServico().intValue()>0)){
													sql.append("and solicitacaoservico.idtipodemandaservico = ? ");
													parametro.add(analiseTendenciasDTO.getIdTipoDemandaServico());
			}

			if ((analiseTendenciasDTO.getUrgencia()!=null)&&(analiseTendenciasDTO.getUrgencia().length()>0)){
													sql.append("and solicitacaoservico.urgencia = ? ");
													parametro.add(analiseTendenciasDTO.getUrgencia());
			}

			if ((analiseTendenciasDTO.getImpacto()!=null)&&(analiseTendenciasDTO.getImpacto().length()>0)){
													sql.append("and solicitacaoservico.impacto = ? ");
													parametro.add(analiseTendenciasDTO.getImpacto());
			}

			if ((analiseTendenciasDTO.getIdCausaIncidente()!=null)&&(analiseTendenciasDTO.getIdCausaIncidente().intValue()>0)){
													sql.append("and solicitacaoservico.idcausaincidente = ? ");
													parametro.add(analiseTendenciasDTO.getIdCausaIncidente());
			}

			if ((analiseTendenciasDTO.getIdItemConfiguracao()!=null)&&(analiseTendenciasDTO.getIdItemConfiguracao().intValue()>0)){
									sql.append("join itemcfgsolicitacaoserv on solicitacaoservico.idsolicitacaoservico = itemcfgsolicitacaoserv.idsolicitacaoservico and ");
																		sql.append("itemcfgsolicitacaoserv.iditemconfiguracao = ? and ");
																		sql.append("(itemcfgsolicitacaoserv.datafim is null) ");
									parametro.add(analiseTendenciasDTO.getIdItemConfiguracao());
			}

			sql.append("group by solicitacaoservico.idcausaincidente) a on causaincidente.idcausaincidente = a.idcausaincidente ");

			if ((analiseTendenciasDTO.getQtdeCritica() !=null)&&(analiseTendenciasDTO.getQtdeCritica().intValue()>0)){
				sql.append("and qtde >= ? ");
				parametro.add(analiseTendenciasDTO.getQtdeCritica());
			}
			sql.append("order by qtde desc, descricao");

			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<TendenciaDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<TendenciaDTO>() : result);
	}

	public List<TendenciaDTO> buscarTendenciasItemConfiguracao(AnaliseTendenciasDTO analiseTendenciasDTO){
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();

			java.sql.Date DataInicio = null;
			Timestamp DataFim = null;

			if (analiseTendenciasDTO.getDataInicio()!=null){
				DataInicio = UtilDatas.getSqlDate(analiseTendenciasDTO.getDataInicio());
			}

			if (analiseTendenciasDTO.getDataFim()!=null){
				DataFim = UtilDatas.getTimeStampComUltimaHoraDoDia(analiseTendenciasDTO.getDataFim());
			}

			listRetorno.add("id");
			listRetorno.add("descricao");
			listRetorno.add("qtdeCritica");

			StringBuilder sql = new StringBuilder();
			sql.append("select ");

			sql.append("itemconfiguracao.iditemconfiguracao id,itemconfiguracao.identificacao descricao, a.qtde qtdeCritica ");
			sql.append("from itemconfiguracao join ");
			                  sql.append("(select itemcfgsolicitacaoserv.iditemconfiguracao,count(solicitacaoservico.idsolicitacaoservico) as qtde ");
			                   sql.append("from solicitacaoservico join servicocontrato on ");
			if ((DataInicio!=null) && (DataFim!=null)){
                									sql.append("(solicitacaoservico.datahorasolicitacao between ? and ?) and ");
                									parametro.add(DataInicio);
                									parametro.add(DataFim);
			}

			sql.append("solicitacaoservico.situacao <> ? and ");
			parametro.add(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Cancelada.getDescricao());

			sql.append("solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
			if ((analiseTendenciasDTO.getIdContrato()!=null)&&(analiseTendenciasDTO.getIdContrato().intValue()>0)){
													sql.append("and servicocontrato.idcontrato = ? ");
													parametro.add(analiseTendenciasDTO.getIdContrato());
			}
			if ((analiseTendenciasDTO.getIdServico()!=null)&&(analiseTendenciasDTO.getIdServico().intValue()>0)){
													sql.append("and servicocontrato.idservico = ? ");
													parametro.add(analiseTendenciasDTO.getIdServico());
			}
			if ((analiseTendenciasDTO.getIdGrupoExecutor()!=null)&&(analiseTendenciasDTO.getIdGrupoExecutor().intValue()>0)){
													sql.append("and solicitacaoservico.idgrupoatual = ? ");
													parametro.add(analiseTendenciasDTO.getIdGrupoExecutor());
			}

			if ((analiseTendenciasDTO.getIdEmpregado()!=null)&&(analiseTendenciasDTO.getIdEmpregado().intValue()>0)){
													sql.append("and solicitacaoservico.idsolicitante = ? ");
													parametro.add(analiseTendenciasDTO.getIdEmpregado());
			}

			if ((analiseTendenciasDTO.getIdTipoDemandaServico()!=null)&&(analiseTendenciasDTO.getIdTipoDemandaServico().intValue()>0)){
													sql.append("and solicitacaoservico.idtipodemandaservico = ? ");
													parametro.add(analiseTendenciasDTO.getIdTipoDemandaServico());
			}

			if ((analiseTendenciasDTO.getUrgencia()!=null)&&(analiseTendenciasDTO.getUrgencia().length()>0)){
													sql.append("and solicitacaoservico.urgencia = ? ");
													parametro.add(analiseTendenciasDTO.getUrgencia());
			}

			if ((analiseTendenciasDTO.getImpacto()!=null)&&(analiseTendenciasDTO.getImpacto().length()>0)){
													sql.append("and solicitacaoservico.impacto = ? ");
													parametro.add(analiseTendenciasDTO.getImpacto());
			}

			if ((analiseTendenciasDTO.getIdCausaIncidente()!=null)&&(analiseTendenciasDTO.getIdCausaIncidente().intValue()>0)){
													sql.append("and solicitacaoservico.idcausaincidente = ? ");
													parametro.add(analiseTendenciasDTO.getIdCausaIncidente());
			}

			sql.append("join itemcfgsolicitacaoserv on solicitacaoservico.idsolicitacaoservico = itemcfgsolicitacaoserv.idsolicitacaoservico and ");
			sql.append("(itemcfgsolicitacaoserv.datafim is null) ");

			if ((analiseTendenciasDTO.getIdItemConfiguracao()!=null)&&(analiseTendenciasDTO.getIdItemConfiguracao().intValue()>0)){
																		sql.append("and itemcfgsolicitacaoserv.iditemconfiguracao = ? ");
									parametro.add(analiseTendenciasDTO.getIdItemConfiguracao());
			}

			sql.append("group by itemcfgsolicitacaoserv.iditemconfiguracao) a on itemconfiguracao.iditemconfiguracao = a.iditemconfiguracao ");

			if ((analiseTendenciasDTO.getQtdeCritica() !=null)&&(analiseTendenciasDTO.getQtdeCritica().intValue()>0)){
				sql.append("and qtde >= ? ");
				parametro.add(analiseTendenciasDTO.getQtdeCritica());
			}
			sql.append("order by qtde desc, descricao");

			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<TendenciaDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<TendenciaDTO>() : result);
	}

	public List<TendenciaGanttDTO> listarGraficoGanttServico(AnaliseTendenciasDTO analiseTendenciasDTO, Integer idServico){
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();

			java.sql.Date DataInicio = null;
			Timestamp DataFim = null;

			if (analiseTendenciasDTO.getDataInicio()!=null){
				DataInicio = UtilDatas.getSqlDate(analiseTendenciasDTO.getDataInicio());
			}

			if (analiseTendenciasDTO.getDataFim()!=null){
				DataFim = UtilDatas.getTimeStampComUltimaHoraDoDia(analiseTendenciasDTO.getDataFim());
			}

			//Deve-se ter um período informado
			if ((DataInicio==null) || (DataFim==null)){
				return new ArrayList<TendenciaGanttDTO>();
			}

			listRetorno.add("data");
			listRetorno.add("qtde");

			StringBuilder sql = new StringBuilder();

			//Para o PostgreSQL já podemos trazer a coleção completa com as datas que não tem ocorrência de solicitações já com a qtde zerada!
			if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
				sql.append("select gdias.data, case when qtde is null then 0 else qtde end as qtde ");
				sql.append("from ");
				    sql.append("(SELECT generate_series::date as data ");
				     sql.append("FROM generate_series(?::timestamp, ?, '1 day')) gdias left join (");
				parametro.add(DataInicio);
				parametro.add(DataFim);
			}

			sql.append("select CAST(datahorasolicitacao AS DATE) as data, count(solicitacaoservico.idsolicitacaoservico) as qtde ");
			sql.append("from solicitacaoservico join servicocontrato on ");
                									sql.append("(solicitacaoservico.datahorasolicitacao between ? and ?) and ");
                									parametro.add(DataInicio);
                									parametro.add(DataFim);
			sql.append("solicitacaoservico.situacao <> ? and ");
			parametro.add(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Cancelada.getDescricao());

			sql.append("solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
			if ((analiseTendenciasDTO.getIdContrato()!=null)&&(analiseTendenciasDTO.getIdContrato().intValue()>0)){
													sql.append("and servicocontrato.idcontrato = ? ");
													parametro.add(analiseTendenciasDTO.getIdContrato());
			}
			if ((idServico!=null)&&(idServico.intValue()>0)){
													sql.append("and servicocontrato.idservico = ? ");
													parametro.add(idServico);
			}
			if ((analiseTendenciasDTO.getIdGrupoExecutor()!=null)&&(analiseTendenciasDTO.getIdGrupoExecutor().intValue()>0)){
													sql.append("and solicitacaoservico.idgrupoatual = ? ");
													parametro.add(analiseTendenciasDTO.getIdGrupoExecutor());
			}

			if ((analiseTendenciasDTO.getIdEmpregado()!=null)&&(analiseTendenciasDTO.getIdEmpregado().intValue()>0)){
													sql.append("and solicitacaoservico.idsolicitante = ? ");
													parametro.add(analiseTendenciasDTO.getIdEmpregado());
			}

			if ((analiseTendenciasDTO.getIdTipoDemandaServico()!=null)&&(analiseTendenciasDTO.getIdTipoDemandaServico().intValue()>0)){
													sql.append("and solicitacaoservico.idtipodemandaservico = ? ");
													parametro.add(analiseTendenciasDTO.getIdTipoDemandaServico());
			}

			if ((analiseTendenciasDTO.getUrgencia()!=null)&&(analiseTendenciasDTO.getUrgencia().length()>0)){
													sql.append("and solicitacaoservico.urgencia = ? ");
													parametro.add(analiseTendenciasDTO.getUrgencia());
			}

			if ((analiseTendenciasDTO.getImpacto()!=null)&&(analiseTendenciasDTO.getImpacto().length()>0)){
													sql.append("and solicitacaoservico.impacto = ? ");
													parametro.add(analiseTendenciasDTO.getImpacto());
			}

			if ((analiseTendenciasDTO.getIdCausaIncidente()!=null)&&(analiseTendenciasDTO.getIdCausaIncidente().intValue()>0)){
													sql.append("and solicitacaoservico.idcausaincidente = ? ");
													parametro.add(analiseTendenciasDTO.getIdCausaIncidente());
			}

			if ((analiseTendenciasDTO.getIdItemConfiguracao()!=null)&&(analiseTendenciasDTO.getIdItemConfiguracao().intValue()>0)){
									sql.append("join itemcfgsolicitacaoserv on solicitacaoservico.idsolicitacaoservico = itemcfgsolicitacaoserv.idsolicitacaoservico and ");
																		sql.append("itemcfgsolicitacaoserv.iditemconfiguracao = ? and ");
																		sql.append("(itemcfgsolicitacaoserv.datafim is null) ");
									parametro.add(analiseTendenciasDTO.getIdItemConfiguracao());
			}

			sql.append("group by CAST(datahorasolicitacao AS DATE) ");
			sql.append("order by data");

			//Para o PostgreSQL já podemos trazer a coleção completa com as datas que não tem ocorrência de solicitações já com a qtde zerada!
			if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
				sql.append(") s on gdias.data=s.data");
			}

			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(TendenciaGanttDTO.class, resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}

		//Para o PostgreSQL já podemos trazer a coleção completa com as datas que não tem ocorrência de solicitações já com a qtde zerada!
		if (!CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
			//Popula uma coleção com o intervalo de datas! DataInicio - DataFim
		}
		result = this.montarPeriodo(analiseTendenciasDTO,(ArrayList<TendenciaGanttDTO>) result);
		return (ArrayList<TendenciaGanttDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<TendenciaGanttDTO>() : result);
	}

	public List<TendenciaGanttDTO> listarGraficoGanttCausa(AnaliseTendenciasDTO analiseTendenciasDTO, Integer idCausa){
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();

			java.sql.Date DataInicio = null;
			Timestamp DataFim = null;

			if (analiseTendenciasDTO.getDataInicio()!=null){
				DataInicio = UtilDatas.getSqlDate(analiseTendenciasDTO.getDataInicio());
			}

			if (analiseTendenciasDTO.getDataFim()!=null){
				DataFim = UtilDatas.getTimeStampComUltimaHoraDoDia(analiseTendenciasDTO.getDataFim());
			}

			//Deve-se ter um período informado
			if ((DataInicio==null) || (DataFim==null)){
				return new ArrayList<TendenciaGanttDTO>();
			}

			listRetorno.add("data");
			listRetorno.add("qtde");

			StringBuilder sql = new StringBuilder();

			//Para o PostgreSQL já podemos trazer a coleção completa com as datas que não tem ocorrência de solicitações já com a qtde zerada!
			if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
				sql.append("select gdias.data, case when qtde is null then 0 else qtde end as qtde ");
				sql.append("from ");
				    sql.append("(SELECT generate_series::date as data ");
				     sql.append("FROM generate_series(?::timestamp, ?, '1 day')) gdias left join (");
				parametro.add(DataInicio);
				parametro.add(DataFim);
			}

			sql.append("select CAST(datahorasolicitacao AS DATE) as data, count(solicitacaoservico.idsolicitacaoservico) as qtde ");
			sql.append("from solicitacaoservico join servicocontrato on ");
                									sql.append("(solicitacaoservico.datahorasolicitacao between ? and ?) and ");
                									parametro.add(DataInicio);
                									parametro.add(DataFim);
			sql.append("solicitacaoservico.situacao <> ? and ");
			parametro.add(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Cancelada.getDescricao());

			sql.append("solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");

			if ((analiseTendenciasDTO.getIdContrato()!=null)&&(analiseTendenciasDTO.getIdContrato().intValue()>0)){
													sql.append("and servicocontrato.idcontrato = ? ");
													parametro.add(analiseTendenciasDTO.getIdContrato());
			}

			if ((analiseTendenciasDTO.getIdServico()!=null)&&(analiseTendenciasDTO.getIdServico().intValue()>0)){
				sql.append("and servicocontrato.idservico = ? ");
				parametro.add(analiseTendenciasDTO.getIdServico());
			}

			if ((analiseTendenciasDTO.getIdGrupoExecutor()!=null)&&(analiseTendenciasDTO.getIdGrupoExecutor().intValue()>0)){
													sql.append("and solicitacaoservico.idgrupoatual = ? ");
													parametro.add(analiseTendenciasDTO.getIdGrupoExecutor());
			}

			if ((analiseTendenciasDTO.getIdEmpregado()!=null)&&(analiseTendenciasDTO.getIdEmpregado().intValue()>0)){
													sql.append("and solicitacaoservico.idsolicitante = ? ");
													parametro.add(analiseTendenciasDTO.getIdEmpregado());
			}

			if ((analiseTendenciasDTO.getIdTipoDemandaServico()!=null)&&(analiseTendenciasDTO.getIdTipoDemandaServico().intValue()>0)){
													sql.append("and solicitacaoservico.idtipodemandaservico = ? ");
													parametro.add(analiseTendenciasDTO.getIdTipoDemandaServico());
			}

			if ((analiseTendenciasDTO.getUrgencia()!=null)&&(analiseTendenciasDTO.getUrgencia().length()>0)){
													sql.append("and solicitacaoservico.urgencia = ? ");
													parametro.add(analiseTendenciasDTO.getUrgencia());
			}

			if ((analiseTendenciasDTO.getImpacto()!=null)&&(analiseTendenciasDTO.getImpacto().length()>0)){
													sql.append("and solicitacaoservico.impacto = ? ");
													parametro.add(analiseTendenciasDTO.getImpacto());
			}

			if ((idCausa!=null)&&(idCausa.intValue()>0)){
													sql.append("and solicitacaoservico.idcausaincidente = ? ");
													parametro.add(idCausa);
			}

			if ((analiseTendenciasDTO.getIdItemConfiguracao()!=null)&&(analiseTendenciasDTO.getIdItemConfiguracao().intValue()>0)){
									sql.append("join itemcfgsolicitacaoserv on solicitacaoservico.idsolicitacaoservico = itemcfgsolicitacaoserv.idsolicitacaoservico and ");
																		sql.append("itemcfgsolicitacaoserv.iditemconfiguracao = ? and ");
																		sql.append("(itemcfgsolicitacaoserv.datafim is null) ");
									parametro.add(analiseTendenciasDTO.getIdItemConfiguracao());
			}

			sql.append("group by CAST(datahorasolicitacao AS DATE) ");
			sql.append("order by data");

			//Para o PostgreSQL já podemos trazer a coleção completa com as datas que não tem ocorrência de solicitações já com a qtde zerada!
			if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
				sql.append(") s on gdias.data=s.data");
			}

			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(TendenciaGanttDTO.class, resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		result = this.montarPeriodo(analiseTendenciasDTO,(ArrayList<TendenciaGanttDTO>) result);
		return (ArrayList<TendenciaGanttDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<TendenciaGanttDTO>() : result);
	}

	public List<TendenciaGanttDTO> listarGraficoGanttItemConfiguracao(AnaliseTendenciasDTO analiseTendenciasDTO, Integer idItemConfiguracao){
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();

			java.sql.Date DataInicio = null;
			Timestamp DataFim = null;

			if (analiseTendenciasDTO.getDataInicio()!=null){
				DataInicio = UtilDatas.getSqlDate(analiseTendenciasDTO.getDataInicio());
			}

			if (analiseTendenciasDTO.getDataFim()!=null){
				DataFim = UtilDatas.getTimeStampComUltimaHoraDoDia(analiseTendenciasDTO.getDataFim());
			}

			//Deve-se ter um período informado
			if ((DataInicio==null) || (DataFim==null)){
				return new ArrayList<TendenciaGanttDTO>();
			}

			listRetorno.add("data");
			listRetorno.add("qtde");

			StringBuilder sql = new StringBuilder();

			//Para o PostgreSQL já podemos trazer a coleção completa com as datas que não tem ocorrência de solicitações já com a qtde zerada!
			if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
				sql.append("select gdias.data, case when qtde is null then 0 else qtde end as qtde ");
				sql.append("from ");
				    sql.append("(SELECT generate_series::date as data ");
				     sql.append("FROM generate_series(?::timestamp, ?, '1 day')) gdias left join (");
				parametro.add(DataInicio);
				parametro.add(DataFim);
			}

			sql.append("select CAST(datahorasolicitacao AS DATE) as data, count(solicitacaoservico.idsolicitacaoservico) as qtde ");
			sql.append("from solicitacaoservico join servicocontrato on ");
                									sql.append("(solicitacaoservico.datahorasolicitacao between ? and ?) and ");
                									parametro.add(DataInicio);
                									parametro.add(DataFim);
			sql.append("solicitacaoservico.situacao <> ? and ");
			parametro.add(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Cancelada.getDescricao());

			sql.append("solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");

			if ((analiseTendenciasDTO.getIdContrato()!=null)&&(analiseTendenciasDTO.getIdContrato().intValue()>0)){
													sql.append("and servicocontrato.idcontrato = ? ");
													parametro.add(analiseTendenciasDTO.getIdContrato());
			}

			if ((analiseTendenciasDTO.getIdServico()!=null)&&(analiseTendenciasDTO.getIdServico().intValue()>0)){
				sql.append("and servicocontrato.idservico = ? ");
				parametro.add(analiseTendenciasDTO.getIdServico());
			}

			if ((analiseTendenciasDTO.getIdGrupoExecutor()!=null)&&(analiseTendenciasDTO.getIdGrupoExecutor().intValue()>0)){
													sql.append("and solicitacaoservico.idgrupoatual = ? ");
													parametro.add(analiseTendenciasDTO.getIdGrupoExecutor());
			}

			if ((analiseTendenciasDTO.getIdEmpregado()!=null)&&(analiseTendenciasDTO.getIdEmpregado().intValue()>0)){
													sql.append("and solicitacaoservico.idsolicitante = ? ");
													parametro.add(analiseTendenciasDTO.getIdEmpregado());
			}

			if ((analiseTendenciasDTO.getIdTipoDemandaServico()!=null)&&(analiseTendenciasDTO.getIdTipoDemandaServico().intValue()>0)){
													sql.append("and solicitacaoservico.idtipodemandaservico = ? ");
													parametro.add(analiseTendenciasDTO.getIdTipoDemandaServico());
			}

			if ((analiseTendenciasDTO.getUrgencia()!=null)&&(analiseTendenciasDTO.getUrgencia().length()>0)){
													sql.append("and solicitacaoservico.urgencia = ? ");
													parametro.add(analiseTendenciasDTO.getUrgencia());
			}

			if ((analiseTendenciasDTO.getImpacto()!=null)&&(analiseTendenciasDTO.getImpacto().length()>0)){
													sql.append("and solicitacaoservico.impacto = ? ");
													parametro.add(analiseTendenciasDTO.getImpacto());
			}

			if ((analiseTendenciasDTO.getIdCausaIncidente()!=null)&&(analiseTendenciasDTO.getIdCausaIncidente().intValue()>0)){
													sql.append("and solicitacaoservico.idcausaincidente = ? ");
													parametro.add(analiseTendenciasDTO.getIdCausaIncidente());
			}

			if ((idItemConfiguracao!=null)&&(idItemConfiguracao.intValue()>0)){
									sql.append("join itemcfgsolicitacaoserv on solicitacaoservico.idsolicitacaoservico = itemcfgsolicitacaoserv.idsolicitacaoservico and ");
																		sql.append("itemcfgsolicitacaoserv.iditemconfiguracao = ? and ");
																		sql.append("(itemcfgsolicitacaoserv.datafim is null) ");
									parametro.add(idItemConfiguracao);
			}

			sql.append("group by CAST(datahorasolicitacao AS DATE) ");
			sql.append("order by data");

			//Para o PostgreSQL já podemos trazer a coleção completa com as datas que não tem ocorrência de solicitações já com a qtde zerada!
			if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
				sql.append(") s on gdias.data=s.data");
			}

			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(TendenciaGanttDTO.class, resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		result = this.montarPeriodo(analiseTendenciasDTO,(ArrayList<TendenciaGanttDTO>) result);
		return (ArrayList<TendenciaGanttDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<TendenciaGanttDTO>() : result);
	}

}