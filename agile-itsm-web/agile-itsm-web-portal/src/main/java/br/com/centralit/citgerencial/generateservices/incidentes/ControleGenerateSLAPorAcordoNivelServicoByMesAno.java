package br.com.centralit.citgerencial.generateservices.incidentes;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import br.com.centralit.citcorpore.util.AdaptacaoBD;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilNumbersAndDecimals;

public class ControleGenerateSLAPorAcordoNivelServicoByMesAno {
	public List execute(Integer idAcordoNivelServico, Integer ano, Integer mes)
			throws ParseException {
		/*
		String datainicial = (String) parametersValues.get("PARAM.dataInicial");
		String datafinal = (String) parametersValues.get("PARAM.dataFinal");
		*/
		List parametersValuesBusca = new ArrayList();
		List lstRetorno = new ArrayList();
/*
		Date datafim = null;
		Date datainicio = null;
		try {
			datainicio = UtilDatas.strToSQLDate(datainicial);
		} catch (LogicException e) {
			e.printStackTrace();
		}
		try {
			datafim = UtilDatas.strToSQLDate(datafinal);
		} catch (LogicException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(datafim);
		calendar.add(GregorianCalendar.DATE, 1);

		datafim = new java.sql.Date(calendar.getTime().getTime());
*/
		String sql = "select sol.idsolicitacaoservico, emp.nome, cont.numero, sol.datahorasolicitacao, sol.datahorafim, sol.datahoralimite, "
				+ "prazohh, prazomm, seqreabertura, tempoCapturaHH, tempoCapturaMM, tempoAtrasoHH, tempoAtrasoMM, "
				+ "tempoAtendimentoHH, tempoAtendimentoMM, dataHoraCaptura, slaACombinar, idprioridade, sol.situacao "
				+ "from solicitacaoservico sol "
				+ "left outer join empregados emp on emp.idempregado = sol.idsolicitante "
				+ "left outer join servicocontrato serc on serc.idservicocontrato = sol.idservicocontrato "
				+ "left outer join servico serv on serv.idservico = serc.idservicocontrato "
				+ "left outer join tiposervico tipos ON serv.idtiposervico = tipos.idtiposervico "
				+ "left outer join contratos cont on cont.idcontrato = serc.idcontrato "
				+ "left outer join tipodemandaservico tpdem on tpdem.idtipodemandaservico = serv.idtipodemandaservico ";

		// sql = sql + "WHERE (sol.datahorasolicitacao BETWEEN ? AND ?) ";
		sql = sql + "WHERE (sol.idacordonivelservico = ? and " + AdaptacaoBD.getYear("sol.datahorasolicitacao") + " = ? and " + AdaptacaoBD.getMonth("sol.datahorasolicitacao") + " = ?) ";

		sql = sql
				+ "order by prazohh, prazomm, sol.datahorasolicitacao, sol.idsolicitacaoservico ";

		JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);

		parametersValuesBusca.add(idAcordoNivelServico);
		parametersValuesBusca.add(ano);
		parametersValuesBusca.add(mes);

		double qtdePrazo = 0;
		double qtdeForaPrazo = 0;
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
					Integer slaHH = UtilNumbersAndDecimals
							.convertToInteger(row[6]);
					Integer slaMM = UtilNumbersAndDecimals
							.convertToInteger(row[7]);
					if (slaHH != null && slaHH.intValue() > 0) {
						sla = slaHH + "h";
					}
					if (slaMM != null && slaMM.intValue() > 0) {
						sla += " " + slaMM + "m";
					}
					if (sla.equalsIgnoreCase("")
							|| slaComb.equalsIgnoreCase("S")) {
						sla = "A comb.";
						slaComb = "S";
					} else {
						slaComb = "N";
					}
					String captura = "";
					Integer capturaHH = UtilNumbersAndDecimals
							.convertToInteger(row[9]);
					Integer capturaMM = UtilNumbersAndDecimals
							.convertToInteger(row[10]);
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
						Integer atrasoHH = UtilNumbersAndDecimals
								.convertToInteger(row[11]);
						Integer atrasoMM = UtilNumbersAndDecimals
								.convertToInteger(row[12]);
						if (atrasoHH != null && atrasoHH.intValue() > 0) {
							atraso = atrasoHH + "h";
						}
						if (atrasoMM != null && atrasoMM.intValue() > 0) {
							atraso += " " + atrasoMM + "m";
						}
						if (atraso.equalsIgnoreCase("--")
								|| atraso.equalsIgnoreCase("")) {
							noPrazo = "SIM";
							qtdePrazo++;
							noPrazoComp = "N";
						} else {
							noPrazo = "NÃO";
							qtdeForaPrazo++;
							noPrazoComp = "F";
						}
					}
					String atend = "";
					Integer atendHH = UtilNumbersAndDecimals
							.convertToInteger(row[13]);
					Integer atendMM = UtilNumbersAndDecimals
							.convertToInteger(row[14]);
					if (atendHH != null && atendHH.intValue() > 0) {
						atend = atendHH + "h";
					}
					if (atendMM != null && atendMM.intValue() > 0) {
						atend += " " + atendMM + "m";
					}
					Integer prioridade = UtilNumbersAndDecimals
							.convertToInteger(row[17]);
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
				}
			}
			lstRetorno = new ArrayList();
			lstRetorno
					.add(new Object[] { "Dentro do SLA",
							(qtdePrazo / (qtdePrazo + qtdeForaPrazo)) * 100,
							qtdePrazo });
			lstRetorno.add(new Object[] { "Fora do SLA",
					(qtdeForaPrazo / (qtdePrazo + qtdeForaPrazo)) * 100,
					qtdeForaPrazo });
			return lstRetorno;
		} catch (PersistenceException e) {
			e.printStackTrace();
			return null;
		}
	}
}
