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

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citgerencial.bean.GerencialGenerateService;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilNumbersAndDecimals;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GenerateServiceInicioTratamentoAnalitico extends GerencialGenerateService {

	public List execute(HashMap parametersValues, Collection paramtersDefinition) throws ParseException {
		String datainicial = (String) parametersValues.get("PARAM.dataInicial");
		String datafinal = (String) parametersValues.get("PARAM.dataFinal");
		List parametersValuesBusca = new ArrayList();
		List lstRetorno = new ArrayList();

		Date datafim = UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datafinal, super.getLanguage(paramtersDefinition));
		Date datainicio = UtilDatas.convertStringToSQLDate(TipoDate.DATE_DEFAULT, datainicial, super.getLanguage(paramtersDefinition));
			
 		Calendar calendar = Calendar.getInstance();
		if(datafim != null){
			calendar.setTime(datafim);
		}
		calendar.add(GregorianCalendar.DATE, 1);

		datafim = new java.sql.Date(calendar.getTime().getTime());

		StringBuilder sql = new StringBuilder();

		sql.append("select ");
		sql.append("sol.idsolicitacaoservico as valor1, ");
		sql.append("emp.nome as valor2, ");
		sql.append("cont.numero as valor3,");
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
		sql.append("sol.prazoCapturaHH as valor20, ");
		sql.append("sol.prazoCapturaMM as valor21 ");
		sql.append("from solicitacaoservico sol ");
		sql.append("inner join execucaosolicitacao es on es.idsolicitacaoservico = sol.idsolicitacaoservico ");
		sql.append("left outer join empregados emp on emp.idempregado = sol.idsolicitante left outer join servicocontrato serc on serc.idservicocontrato = sol.idservicocontrato ");
		sql.append("left outer join servico serv on serv.idservico = serc.idservicocontrato left outer join tiposervico tipos ON serv.idtiposervico = tipos.idtiposervico ");
		sql.append("left outer join contratos cont on cont.idcontrato = serc.idcontrato left outer join tipodemandaservico tpdem on tpdem.idtipodemandaservico = serv.idtipodemandaservico ");

		sql.append("WHERE UPPER(sol.situacao) = 'FECHADA' ");

		String classificacao = (String) parametersValues.get("PARAM.classificacao");

		if (classificacao != null && !classificacao.equalsIgnoreCase("*")) {
			sql.append("AND tpdem.classificacao = '" + classificacao + "' ");
		}

		sql.append("AND (serc.idservico = ? OR ? = -1) ");
		sql.append("AND (serc.idcontrato = ? OR ? = -1) ");
		sql.append("AND (tipos.idtiposervico = ? OR ? = -1) ");
		sql.append("AND (sol.idprioridade = ? OR ? = -1) ");
		sql.append("AND (sol.idorigem = ? OR ? = -1) ");
		sql.append("AND (sol.idunidade = ? OR ? = -1) ");
		sql.append("AND (sol.datahorasolicitacao BETWEEN ? AND ?) ");


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
		parametersValuesBusca.add(datainicio);
		parametersValuesBusca.add(datafim);

		this.acrescentarNaSqlOLimitadorDeRegistros(sql, this.obterValorParametroNumeroMaximoDeRegistros(parametersValues));
		
		sql.append("order by valor4, valor1 ");
		
		// HttpServletRequest request = null;

		try {

			List listaDados = jdbcEngine.execSQL(sql.toString(), parametersValuesBusca.toArray(), 0);

			if (listaDados != null && !listaDados.isEmpty()) {
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
					if (dataHoraLimite != null) {
						dataHoraLimiteStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, dataHoraLimite, super.getLanguage(paramtersDefinition));
					}

					Timestamp dataHoraCaptura = (Timestamp) row[15];
					String dataHoraCapturaStr = "--";
					if (dataHoraCaptura != null) {
						dataHoraCapturaStr = UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, dataHoraCaptura, super.getLanguage(paramtersDefinition));
					}

					double tempoCapturaDbl = 0;
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
					if (capturaHH != null) {
						tempoCapturaDbl = capturaHH.doubleValue();
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
					if (capturaMM != null) {
						tempoCapturaDbl += capturaMM.doubleValue() / 60;
					}

					Integer prioridade = UtilNumbersAndDecimals.convertToInteger(row[17]);
					String prioridadeStr = "";
					if (prioridade == null) {
						prioridadeStr = "--";
					} else {
						prioridadeStr = "" + prioridade;
					}

					double prazoCapturaDbl = 0;
					String prazoCaptura = "";
					Integer prazoCapturaHH = UtilNumbersAndDecimals.convertToInteger(row[19]);
					Integer prazoCapturaMM = UtilNumbersAndDecimals.convertToInteger(row[20]);
					if (prazoCapturaHH != null && prazoCapturaHH.intValue() > 0) {
						prazoCaptura = prazoCaptura + "h";
						prazoCapturaDbl = prazoCapturaHH.doubleValue();
					}
					if (prazoCapturaMM != null && prazoCapturaMM.intValue() > 0) {
						prazoCaptura += " " + prazoCapturaMM + "m";
						prazoCapturaDbl += prazoCapturaMM.doubleValue() / 60;
					}

					String noPrazo = "S";
					if ((tempoCapturaDbl > prazoCapturaDbl) || (dataHoraCaptura == null && prazoCapturaDbl > 0)) {
						noPrazo = "N";
					}

					lstRetorno.add(new Object[] { row[0] + "", row[1], row[2], UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, dataHoraSol, super.getLanguage(paramtersDefinition)), dataHoraLimiteStr, dataHoraFimStr, prioridadeStr, dataHoraCapturaStr,
							prazoCaptura, captura, noPrazo });
				}

			}

			if (lstRetorno == null || lstRetorno.size() == 0) {
				lstRetorno = new ArrayList();
				/* Desenvolvedor: Rodrigo Pecci - Data: 30/10/2013 - Horário: 14h15min - ID Citsmart: 120770
				 * Motivo/Comentário: Se lstRetorno for null ou vazio, é necessário retornar somente um ArrayList vazio.
				 */
				//lstRetorno.add(new Object[] { "", "", "", "", "", "", "", "", "", "", "" });
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
