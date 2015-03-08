package br.com.centralit.citgerencial.generateservices.incidentes;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citgerencial.bean.GerencialGenerateService;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilNumbersAndDecimals;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HoraDemandaGenerateSLA extends GerencialGenerateService {

	public List execute(HashMap parametersValues, Collection paramtersDefinition) throws ParseException {
		String datainicial = (String) parametersValues.get("PARAM.dataInicial");
		String datafinal = (String) parametersValues.get("PARAM.dataFinal");
		List parametersValuesBusca = new ArrayList();
		List lstRetorno = new ArrayList();
		/**
		 * Checa se há limite para pesquisa
		 * 
		 * @author thyen.chang
		 */
		boolean seLimita = Integer.parseInt((String) parametersValues.get("PARAM.topList")) != 0;
		
		Date datafim = UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datafinal, super.getLanguage(paramtersDefinition));
		Date datainicio = UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datainicial, super.getLanguage(paramtersDefinition));

		Calendar calendar = Calendar.getInstance();
		if (datafim != null) {
			calendar.setTime(datafim);
		}
		calendar.add(GregorianCalendar.DATE, 1);

		datafim = new java.sql.Date(calendar.getTime().getTime());

		String sql = "select ";
		/**
		 * Limita pesquisa no SQLServer
		 * @author thyen.chang
		 */
		if((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)))
			sql += "TOP " + (String) parametersValues.get("PARAM.topList") + " ";
		sql +=    "sol.idsolicitacaoservico, emp.nome, cont.numero, sol.datahorasolicitacao, sol.datahorafim, sol.datahoralimite, "
				+ "prazohh, prazomm, seqreabertura, tempoCapturaHH, tempoCapturaMM, tempoAtrasoHH, tempoAtrasoMM, "
				+ "tempoAtendimentoHH, tempoAtendimentoMM, dataHoraCaptura, slaACombinar, idprioridade, sol.situacao " + "from solicitacaoservico sol "
				+ "left outer join empregados emp on emp.idempregado = sol.idsolicitante " + "inner join servicocontrato serc on serc.idservicocontrato = sol.idservicocontrato "
				+ "left outer join servico serv on serv.idservico = serc.idservicocontrato " + "left outer join contratos cont on cont.idcontrato = serc.idcontrato "
				+ "left outer join tipodemandaservico tpdem on tpdem.idtipodemandaservico = serv.idtipodemandaservico ";

		sql = sql + "WHERE ";
		/**
		 * Limita pesquisa no Oracle
		 * @author thyen.chang
		 */
		if((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE)))
			sql += " ROWNUM <= " + (String) parametersValues.get("PARAM.topList") + " AND ";
		sql += "1 = 1 ";
		String classificacao = (String) parametersValues.get("PARAM.classificacao");
		if (classificacao != null && !classificacao.equalsIgnoreCase("*")) {
			sql = sql + "AND tpdem.classificacao = '" + classificacao + "' ";
		}
		String noPrazoInfo = (String) parametersValues.get("PARAM.noPrazo");
		if (noPrazoInfo == null) {
			noPrazoInfo = "*";
		}

		sql = sql + "AND (serc.idservico = ? OR ? = -1) ";
		sql = sql + "AND (serc.idcontrato = ? OR ? = -1) ";
		sql = sql + "AND (sol.idprioridade = ? OR ? = -1) ";
		sql = sql + "AND (sol.idorigem = ? OR ? = -1) ";
		sql = sql + "AND (sol.idunidade = ? OR ? = -1) ";
		sql = sql + "AND (sol.situacao = ? OR ? = '*') ";
		sql = sql + "AND (sol.datahorasolicitacao BETWEEN ? AND ?) ";
		sql = sql + "AND (sol.idtipodemandaservico is not null) ";

		sql = sql + "order by prazohh, prazomm, sol.datahorasolicitacao, sol.idsolicitacaoservico ";
		
		if((seLimita) && ((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL))))
			sql += "LIMIT " + (String) parametersValues.get("PARAM.topList");

		JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);

		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idServico")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idServico")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idContrato")));
		parametersValuesBusca.add(Integer.parseInt((String) parametersValues.get("PARAM.idContrato")));
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

		HashMap mapControle = new HashMap();
		for (int i = 0; i <= 23; i++) {
			mapControle.put("" + i, "0");
		}

		double qtdeTotal = 0;
		try {
			List listaDados = jdbcEngine.execSQL(sql, parametersValuesBusca.toArray(), 0);
			if (listaDados != null) {
				for (int i = 0; i < listaDados.size(); i++) {
					Object[] row = (Object[]) listaDados.get(i);
					Timestamp dataHoraSol = (Timestamp) row[3];
					Timestamp dataHoraFim = (Timestamp) row[4];
					String dataHoraFimStr = "--";
					if (dataHoraFim != null) {
						dataHoraFimStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, dataHoraFim, null);
					}
					Timestamp dataHoraLimite = (Timestamp) row[5];
					String dataHoraLimiteStr = "--";
					if (dataHoraFim != null) {
						dataHoraLimiteStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, dataHoraLimite, null);
					}
					String slaComb = (String) row[16];
					if (slaComb == null) {
						slaComb = "N";
					}
					String sla = "";
					Integer slaHH = UtilNumbersAndDecimals.convertToInteger(row[6]);
					Integer slaMM = UtilNumbersAndDecimals.convertToInteger(row[7]);
					if (slaHH != null && slaHH.intValue() > 0) {
						sla = slaHH + "h";
					}
					if (slaMM != null && slaMM.intValue() > 0) {
						sla += " " + slaMM + "m";
					}
					if (sla.equalsIgnoreCase("") || slaComb.equalsIgnoreCase("S")) {
						sla = "A comb.";
						slaComb = "S";
					} else {
						slaComb = "N";
					}
					String captura = "";
					Integer capturaHH = UtilNumbersAndDecimals.convertToInteger(row[9]);
					Integer capturaMM = UtilNumbersAndDecimals.convertToInteger(row[10]);
					if (capturaHH != null && capturaHH.intValue() > 0) {
						captura = capturaHH + "h";
					}
					if (capturaMM != null && capturaMM.intValue() > 0) {
						captura += " " + capturaMM + "m";
					}
					String atraso = "";
					String noPrazo = "SIM";
					String noPrazoComp = "N";
					if (slaComb.equalsIgnoreCase("N")) {
						Integer atrasoHH = UtilNumbersAndDecimals.convertToInteger(row[11]);
						Integer atrasoMM = UtilNumbersAndDecimals.convertToInteger(row[12]);
						if (atrasoHH != null && atrasoHH.intValue() > 0) {
							atraso = atrasoHH + "h";
						}
						if (atrasoMM != null && atrasoMM.intValue() > 0) {
							atraso += " " + atrasoMM + "m";
						}
						if (atraso.equalsIgnoreCase("--") || atraso.equalsIgnoreCase("")) {
							noPrazo = "SIM";
							noPrazoComp = "N";
						} else {
							noPrazo = "NÃO";
							noPrazoComp = "F";
						}
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
					String situacao = (String) row[18];
					if (situacao == null) {
						situacao = "";
					}
					if (situacao.equalsIgnoreCase("EmAndamento")) {
						situacao = "Em And.";
					}
					if (situacao.equalsIgnoreCase("Cancelada")) {
						situacao = "Canc.";
					}
					if (situacao.equalsIgnoreCase("ReClassificada")) {
						situacao = "Recl.";
					}
					if (situacao.equalsIgnoreCase("ReClassificada")) {
						situacao = "Recl.";
					}

					Calendar c = Calendar.getInstance();
					c.setTime(dataHoraSol);
					int hora = c.get(Calendar.HOUR_OF_DAY);
					String qtdeStr = (String) mapControle.get("" + hora);
					if (hora < 7) {
						System.out.println("SOLICITACAO: " + row[0]);
					}
					int qtde = Integer.parseInt(qtdeStr);
					qtde++;
					qtdeTotal++;
					mapControle.put("" + hora, "" + qtde);
				}
			}
			lstRetorno = new ArrayList();
			if (qtdeTotal != 0) {
				for (int i = 0; i <= 23; i++) {
					String qtdeStr = (String) mapControle.get("" + i);
					int qtde = Integer.parseInt(qtdeStr);

					lstRetorno.add(new Object[] { UtilFormatacao.formatInt(i, "00") + ":00 - " + UtilFormatacao.formatInt(i, "00") + ":59", (qtde / qtdeTotal) * 100, qtde });
				}
			}
			return lstRetorno;
		} catch (PersistenceException e) {
			e.printStackTrace();
			return null;
		}
	}
}
