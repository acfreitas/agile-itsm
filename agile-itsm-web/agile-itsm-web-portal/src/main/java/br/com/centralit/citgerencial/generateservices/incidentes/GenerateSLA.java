package br.com.centralit.citgerencial.generateservices.incidentes;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citgerencial.bean.GerencialGenerateService;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilNumbersAndDecimals;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class GenerateSLA extends GerencialGenerateService {

	public List execute(HashMap parametersValues, Collection paramtersDefinition) throws ParseException {
		String datainicial = (String) parametersValues.get("PARAM.dataInicial");
		String datafinal = (String) parametersValues.get("PARAM.dataFinal");
		List parametersValuesBusca = new ArrayList();
		List lstRetorno = new ArrayList();

		Date datafim = UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datafinal, super.getLanguage(paramtersDefinition));
		Date datainicio = UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datainicial, super.getLanguage(paramtersDefinition));

		Calendar calendar = Calendar.getInstance();
		if (datafim != null) {
			calendar.setTime(datafim);
		}
		calendar.add(GregorianCalendar.DATE, 1);

		datafim = new java.sql.Date(calendar.getTime().getTime());

		/*
		 * Desenvolvedor: Rodrigo Pecci - Data: 31/10/2013 - Horário: 17h40min - ID Citsmart: 120770 Motivo/Comentário: O servicocontrato no select foi alterado para inner join.
		 */

		StringBuilder sql = new StringBuilder();
		sql.append("select ");
		sql.append("sol.idsolicitacaoservico as valor1, ");
		sql.append("emp.nome as valor2, ");
		sql.append("cont.numero as valor3, ");
		sql.append("sol.datahorasolicitacao as valor4, ");
		sql.append("sol.datahorafim as valor5, ");
		sql.append("sol.datahoralimite as valor6, ");
		sql.append("sol.prazohh as valor7, ");
		sql.append("sol.prazomm as valor8, ");
		sql.append("sol.seqreabertura as valor9, ");
		sql.append("sol.tempoCapturaHH as valor10, ");
		sql.append("sol.tempoCapturaMM as valor11, ");
		sql.append("sol.tempoAtrasoHH as valor12, ");
		sql.append("sol.tempoAtrasoMM as valor13, ");
		sql.append("sol.tempoAtendimentoHH as valor14, ");
		sql.append("sol.tempoAtendimentoMM as valor15, ");
		sql.append("sol.dataHoraCaptura as valor16, ");
		sql.append("slaACombinar as valor17, ");
		sql.append("idprioridade as valor18, ");
		sql.append("sol.situacao as valor19, ");
		sql.append("sol.situacaosla as valor20, ");
		sql.append("sol.datahorasuspensaosla as valor21 ");
		sql.append("from solicitacaoservico sol ");
		sql.append("inner join execucaosolicitacao es on es.idsolicitacaoservico = sol.idsolicitacaoservico ");
		sql.append("left outer join empregados emp on emp.idempregado = sol.idsolicitante inner join servicocontrato serc on serc.idservicocontrato = sol.idservicocontrato ");
		sql.append("left outer join servico serv on serv.idservico = serc.idservicocontrato left outer join tiposervico tipos ON serv.idtiposervico = tipos.idtiposervico ");
		sql.append("left outer join contratos cont on cont.idcontrato = serc.idcontrato left outer join tipodemandaservico tpdem on tpdem.idtipodemandaservico = serv.idtipodemandaservico ");

		sql.append("WHERE 1 = 1 ");
		String classificacao = (String) parametersValues.get("PARAM.classificacao");
		if (classificacao != null && !classificacao.equalsIgnoreCase("*")) {
			sql.append("AND tpdem.classificacao = '" + classificacao + "' ");
		}
		String noPrazoInfo = (String) parametersValues.get("PARAM.noPrazo");
		if (noPrazoInfo == null) {
			noPrazoInfo = "*";
		}

		sql.append("AND (serc.idservico = ? OR ? = -1) ");
		sql.append("AND (serc.idcontrato = ? OR ? = -1) ");
		sql.append("AND (tipos.idtiposervico = ? OR ? = -1) ");
		sql.append("AND (sol.idprioridade = ? OR ? = -1) ");
		sql.append("AND (sol.idorigem = ? OR ? = -1) ");
		sql.append("AND (sol.idunidade = ? OR ? = -1) ");
		sql.append("AND (sol.situacao = ? OR ? = '*') ");
		sql.append("AND (sol.datahorasolicitacao BETWEEN ? AND ?) ");
		sql.append("AND (sol.idtipodemandaservico is not null) ");

		JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);

		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idServico")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idServico")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idContrato")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idContrato")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idTipoServico")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idTipoServico")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idPrioridade")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idPrioridade")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idOrigem")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idOrigem")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idUnidade")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idUnidade")));
		parametersValuesBusca.add(parametersValues.get("PARAM.situacao"));
		parametersValuesBusca.add(parametersValues.get("PARAM.situacao"));
		parametersValuesBusca.add(datainicio);
		parametersValuesBusca.add(datafim);

		this.acrescentarNaSqlOLimitadorDeRegistros(sql, this.obterValorParametroNumeroMaximoDeRegistros(parametersValues));

		sql.append("order by valor7, valor8, valor4, valor1 ");

		HttpServletRequest request = null;

		try {
			List listaDados = jdbcEngine.execSQL(sql.toString(), parametersValuesBusca.toArray(), 0);
			if (listaDados != null) {
				for (int i = 0; i < listaDados.size(); i++) {
					Object[] row = (Object[]) listaDados.get(i);
					Timestamp dataHoraSol = (Timestamp) row[3];
					Timestamp dataHoraFim = (Timestamp) row[4];
					String dataHoraFimStr = "--";
					if (dataHoraFim != null) {
						dataHoraFimStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, dataHoraFim, super.getLanguage(paramtersDefinition));
					}
					Timestamp dataHoraLimite = (Timestamp) row[5];
					String dataHoraLimiteStr = "--";
					if (dataHoraFim != null) {
						dataHoraLimiteStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, dataHoraLimite, super.getLanguage(paramtersDefinition));
					}
					String slaComb = (String) row[16];
					if (slaComb == null) {
						slaComb = "N";
					}
					String sla = "";
					Integer slaHH = null;
					if (BigDecimal.class.isInstance(row[6])) {
						BigDecimal auxBig = (BigDecimal) row[6];
						slaHH = new Integer(auxBig.intValue());
					} else {
						slaHH = Integer.parseInt(row[6].toString());
					}
					Integer slaMM = null;
					if (BigDecimal.class.isInstance(row[7])) {
						BigDecimal auxBig = (BigDecimal) row[7];
						slaMM = new Integer(auxBig.intValue());
					} else {
						slaMM = Integer.parseInt(row[7].toString());
					}
					if (slaHH != null && slaHH.intValue() > 0) {
						sla = slaHH + "h";
					}
					if (slaMM != null && slaMM.intValue() > 0) {
						sla += " " + slaMM + "m";
					}

					// Pega o request dos parâmetros
					for (Iterator iterator = paramtersDefinition.iterator(); iterator.hasNext();) {
						Object parametro = (Object) iterator.next();
						if (parametro != null && "org.apache.catalina.connector.RequestFacade".equals(parametro.getClass().getName())) {
							request = (HttpServletRequest) parametro;
							break;
						}
					}

					boolean slaACombinar = (slaHH == null || slaHH.intValue() == 0) && (slaMM == null || slaMM.intValue() == 0);

					if (slaACombinar) {
						sla = UtilI18N.internacionaliza(request, "citcorpore.comum.aCombinar");
						slaComb = "S";
					} else {
						slaComb = "N";
					}
					String captura = "";
					Integer capturaHH = null;
					if (BigDecimal.class.isInstance(row[9])) {
						BigDecimal auxBig = (BigDecimal) row[9];
						capturaHH = new Integer(auxBig.intValue());
					} else {
						if (row[9] != null) {
							capturaHH = Integer.parseInt(row[9].toString());
						}

					}
					Integer capturaMM = null;
					if (BigDecimal.class.isInstance(row[10])) {
						BigDecimal auxBig = (BigDecimal) row[10];
						capturaMM = new Integer(auxBig.intValue());
					} else {
						if (row[10] != null) {
							capturaMM = Integer.parseInt(row[10].toString());
						}

					}
					if (capturaHH != null && capturaHH.intValue() > 0) {
						captura = capturaHH + "h";
					}
					if (capturaMM != null && capturaMM.intValue() > 0) {
						captura += " " + capturaMM + "m";
					}
					String atraso = "";
					String noPrazo = UtilI18N.internacionaliza(request, "citcorpore.comum.sim");
					String noPrazoComp = "N";

					String situacao = (String) row[18];
					if (situacao == null) {
						situacao = "";
					}
					if (situacao.equalsIgnoreCase("EmAndamento")) {
						situacao = UtilI18N.internacionaliza(request, "requisitosla.andamento");
					}
					if (situacao.equalsIgnoreCase("Cancelada")) {
						situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.cancelada");
					}
					if (situacao.equalsIgnoreCase("ReClassificada")) {
						situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.reclassificada");
					}
					if (situacao.equalsIgnoreCase("ReClassificada")) {
						situacao = UtilI18N.internacionaliza(request, "citcorpore.comum.reclassificada");
					}

					if (slaComb.equalsIgnoreCase("N")) {
						Integer atrasoHH = null;
						Integer atrasoMM = null;
						if (BigDecimal.class.isInstance(row[11])) {
							BigDecimal auxBig = (BigDecimal) row[11];
							atrasoHH = new Integer(auxBig.intValue());
						} else if (Short.class.isInstance(row[11])) {
							Short sh = (Short) row[11];
							atrasoHH = new Integer(sh.intValue());
						} else {
							atrasoHH = (Integer) row[11];
						}
						if (BigDecimal.class.isInstance(row[12])) {
							BigDecimal auxBig = (BigDecimal) row[12];
							atrasoMM = new Integer(auxBig.intValue());
						} else if (Short.class.isInstance(row[12])) {
							Short sh = (Short) row[12];
							atrasoHH = new Integer(sh.intValue());
						} else {
							atrasoMM = (Integer) row[12];
						}

						long atrasoSLA = 0;
						Timestamp dataHoraSuspensao = (Timestamp) row[20];
						String situacaoSLA = (String) row[19];
						if (dataHoraLimite != null) {
							boolean bCalcula = true;
							if (situacao.equals(SituacaoSolicitacaoServico.Suspensa.name())) {
								if (dataHoraSuspensao != null && dataHoraSuspensao.compareTo(dataHoraLimite) > 0) {
									bCalcula = true;
								} else {
									bCalcula = false;
								}
							} else if (situacaoSLA != null && situacaoSLA.equalsIgnoreCase("S")) {
								if (dataHoraSuspensao != null && dataHoraSuspensao.compareTo(dataHoraLimite) > 0) {
									bCalcula = true;
								} else {
									bCalcula = false;
								}
							}
							if (bCalcula) {
								Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
								if (situacao != null && (situacao.equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Fechada.name())))
									dataHoraComparacao = dataHoraFim;
								if (dataHoraComparacao != null) {
									if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
										try {
											atrasoSLA = UtilDatas.calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;
										} catch (Exception e) {
											e.printStackTrace();
										}
									}
								}
							}
						}

						/*
						 * if (atrasoHH != null && atrasoHH.intValue() > 0) { atraso = atrasoHH + "h"; } if (atrasoMM != null && atrasoMM.intValue() > 0) { atraso += " " + atrasoMM + "m"; }
						 */

						if (atrasoSLA == 0) {
							noPrazo = UtilI18N.internacionaliza(request, "citcorpore.comum.sim");
							noPrazoComp = "N";
						} else {
							atraso = "";
							long hours = atrasoSLA / 3600;
							long minutes = (atrasoSLA - 3600 * hours) / 60;

							if (hours > 0) {
								atraso = hours + "h";
							}
							if (minutes > 0) {
								atraso += " " + minutes + "m";
							}

							noPrazo = UtilI18N.internacionaliza(request, "citcorpore.comum.nao");
							noPrazoComp = "F";
						}
					} else if(slaComb.equalsIgnoreCase("S")){
						noPrazo = UtilI18N.internacionaliza(request, "citcorpore.comum.sim");
						dataHoraLimiteStr = "";
					}
					if(situacao.equalsIgnoreCase("Cancelada")){
						noPrazo = "";
					}
					String atend = "";
					Integer atendHH = UtilNumbersAndDecimals.convertToInteger(row[13]);
					Integer atendMM = UtilNumbersAndDecimals.convertToInteger(row[14]);
					if (atendHH != null && atendHH.intValue() > 0) {
						atend = atendHH + "h";
					}
					if (atendMM != null && atendMM.intValue() > 0) {
						atend += " " + atendMM + "m";
					}
					Integer prioridade = UtilNumbersAndDecimals.convertToInteger(row[17]);
					String prioridadeStr = "";
					if (prioridade == null) {
						prioridadeStr = "--";
					} else {
						prioridadeStr = "" + prioridade;
					}

					if (noPrazoInfo.equalsIgnoreCase("*") || noPrazoInfo.equalsIgnoreCase(noPrazoComp)) {
						lstRetorno.add(new Object[] { sla, row[0] + "", row[1], row[2], UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, dataHoraSol, super.getLanguage(paramtersDefinition)), dataHoraLimiteStr, dataHoraFimStr, prioridadeStr, captura, atraso,
								situacao, atend, noPrazo });
					}
				}
			}
			HashMap mapAux1 = new HashMap();
			HashMap mapAux2 = new HashMap();
			int qtdeTotal = 0;
			for (Iterator itAux = lstRetorno.iterator(); itAux.hasNext();) {
				Object[] obj = (Object[]) itAux.next();
				Double qtdeDentroPrazo = (double) 0;
				Double qtdeForaPrazo = (double) 0;
				if (mapAux1.get(obj[0]) != null) {
					qtdeDentroPrazo = (Double) mapAux1.get(obj[0]);
				}
				if (mapAux2.get(obj[0]) != null) {
					qtdeForaPrazo = (Double) mapAux2.get(obj[0]);
				}
				if ((((String) obj[12]).equalsIgnoreCase(UtilI18N.internacionaliza(request, "citcorpore.comum.sim"))||((String)obj[12]).equalsIgnoreCase(""))) {
					qtdeDentroPrazo = qtdeDentroPrazo + 1;
				} else {
					qtdeForaPrazo = qtdeForaPrazo + 1;
				}

				mapAux1.put(obj[0], qtdeDentroPrazo);
				mapAux2.put(obj[0], qtdeForaPrazo);
				qtdeTotal++;
			}
			for (Iterator itAux = lstRetorno.iterator(); itAux.hasNext();) {
				Object[] obj = (Object[]) itAux.next();
				Double qtdeDentroPrazo = (double) 0;
				Double qtdeForaPrazo = (double) 0;
				if (mapAux1.get(obj[0]) != null) {
					qtdeDentroPrazo = (Double) mapAux1.get(obj[0]);
				}
				if (mapAux2.get(obj[0]) != null) {
					qtdeForaPrazo = (Double) mapAux2.get(obj[0]);
				}
				if (qtdeDentroPrazo == null) {
					qtdeDentroPrazo = (double) 0;
				}
				if (qtdeForaPrazo == null) {
					qtdeForaPrazo = (double) 0;
				}
				double perc1 = 0;
				try {
					perc1 = ((qtdeDentroPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100);
				} catch (Exception e) {
				}
				double perc2 = 0;
				try {
					perc2 = ((qtdeForaPrazo / (qtdeDentroPrazo + qtdeForaPrazo)) * 100);
				} catch (Exception e) {
				}
				obj[0] = obj[0] + " -  " + UtilI18N.internacionaliza(request, "citcorpore.comum.dentroPrazo") + ": " + UtilFormatacao.formatDouble(qtdeDentroPrazo, 0) + "  ("
						+ UtilFormatacao.formatDouble(perc1, 2) + "%)";
				obj[0] = obj[0] + "  " + UtilI18N.internacionaliza(request, "citcorpore.comum.foraPrazo") + ": " + UtilFormatacao.formatDouble(qtdeForaPrazo, 0) + "  ("
						+ UtilFormatacao.formatDouble(perc2, 2) + "%)";
			}
			if (lstRetorno == null || lstRetorno.size() == 0) {
				lstRetorno = new ArrayList();
				// lstRetorno.add(new Object[] { "", "", "", "", "", "", "", "", "", "", "", "", "" });
				/**
				 * Foi adicionado comentado o Item acima pois, ao gerar o relatório com a lista vazia o relatório estava sendo mostrado. Bruno.Aquino
				 */
			}
			for (Iterator iterator = paramtersDefinition.iterator(); iterator.hasNext();) {
				Object parametro = (Object) iterator.next();
				if (parametro != null && "org.apache.catalina.connector.RequestFacade".equals(parametro.getClass().getName())) {
					iterator.remove();
					break;
				}
			}
			return lstRetorno;
		} catch (PersistenceException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Retorna o Número Máximo de registros que deverá ser retornado na consulta.
	 * 
	 * @param parametros
	 * @return Número Máximo de Registros
	 * @author valdoilo.damasceno
	 */
	private Integer obterValorParametroNumeroMaximoDeRegistros(HashMap parametros) {

		String valor = (String) parametros.get("PARAM.topList");

		if (StringUtils.isNotBlank(valor) && !valor.trim().equals("*")) {

			try {

				return new Integer(valor);

			} catch (NumberFormatException e) {

				return null;
			}

		} else {
			return null;
		}
	}

	/**
	 * Acrescenta no SQL a condição para limitar o retorno de registros de acordo com o valor selecionado.
	 * 
	 * @param sql
	 *            - String SQL.
	 * @param maximoRegistros
	 *            - Número máximo de registros.
	 * @author valdoilo.damasceno
	 */
	private void acrescentarNaSqlOLimitadorDeRegistros(StringBuilder sql, Integer maximoRegistros) {

		String valores = " valor1, valor2, valor3, valor4, valor5, valor6, valor7, valor8, valor9, valor10, valor11, valor12, valor13, valor14, valor15, valor16, valor17, valor18, valor19, valor20, valor21 ";

		if (maximoRegistros == null)
			return;

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {

			sql.insert(0, "SELECT " + valores + "FROM (");
			sql.insert(sql.length(), ") where rownum <= " + maximoRegistros);

		} else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {

			sql.replace(0, 6, " select " + valores + " from (select ROW_NUMBER() OVER(order by (select 1)) rownum, ");
			sql.append(" ) as teste where rownum between 0 and " + maximoRegistros);

		} else {
			sql.append(" LIMIT " + maximoRegistros);
		}
	}
}
