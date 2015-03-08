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
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilNumbersAndDecimals;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GenerateServiceInicioTratamentoSintetico extends GerencialGenerateService {

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
		sql.append("cont.numero as valor3, ");
		sql.append("sol.datahorasolicitacao as valor4, ");
		sql.append("sol.datahorafim as valor5, ");
		sql.append("sol.datahoralimite as valor6, ");
		sql.append("prazohh as valor7, ");
		sql.append("prazomm as valor8, ");
		sql.append("seqreabertura as valor9, ");
		sql.append("tempoCapturaHH as valor10, ");
		sql.append("tempoCapturaMM as valor11, ");
		sql.append("tempoAtrasoHH as valor12, ");
		sql.append("tempoAtrasoMM as valor13, ");
		sql.append("tempoAtendimentoHH as valor14, ");
		sql.append("tempoAtendimentoMM as valor15, ");
		sql.append("dataHoraCaptura as valor16, ");
		sql.append("slaACombinar as valor17, ");
		sql.append("idprioridade as valor18, ");
		sql.append("sol.situacao as valor19, ");
		sql.append("sol.prazoCapturaHH as valor20, ");
		sql.append("sol.prazoCapturaMM as  valor21 ");
		sql.append("from solicitacaoservico sol ");
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

		// HttpServletRequest request = null;

		try {
			List listaDados = jdbcEngine.execSQL(sql.toString(), parametersValuesBusca.toArray(), 0);
			if (listaDados != null && !listaDados.isEmpty()) {
				int total = 0;
				int totalNoPrazo = 0;
				HashMap<String, Object[]> map = new HashMap();
				for (int i = 0; i < listaDados.size(); i++) {
					total++;
					Object[] row = (Object[]) listaDados.get(i);

					Timestamp dataHoraCaptura = (Timestamp) row[15];

					double tempoCapturaDbl = 0;
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
					if (capturaMM != null) {
						tempoCapturaDbl += capturaMM.doubleValue() / 60;
					}

					Integer prioridade = UtilNumbersAndDecimals.convertToInteger(row[17]);

					double prazoCapturaDbl = 0;
					Integer prazoCapturaHH = UtilNumbersAndDecimals.convertToInteger(row[19]);
					Integer prazoCapturaMM = UtilNumbersAndDecimals.convertToInteger(row[20]);
					if (prazoCapturaHH != null && prazoCapturaHH.intValue() > 0) {
						prazoCapturaDbl = prazoCapturaHH.doubleValue();
					}
					if (prazoCapturaMM != null && prazoCapturaMM.intValue() > 0) {
						prazoCapturaDbl += prazoCapturaMM.doubleValue() / 60;
					}

					boolean noPrazo = true;
					if ((tempoCapturaDbl > prazoCapturaDbl) || (dataHoraCaptura == null && prazoCapturaDbl > 0)) {
						noPrazo = false;
					}

					Object[] objs = map.get("" + prioridade);
					if (objs == null) {
						objs = new Object[] { 0, 0, "" };
					}
					objs[0] = new Integer(((Integer) objs[0]).intValue() + 1);
					if (noPrazo) {
						objs[1] = new Integer(((Integer) objs[1]).intValue() + 1);
						totalNoPrazo++;
					}
					map.put("" + prioridade, objs);
				}

				lstRetorno = new ArrayList();
				for (String key : map.keySet()) {
					Object[] objs = map.get(key);
					double perc = ((Integer) objs[1]).doubleValue() / ((Integer) objs[0]).doubleValue() * 100;
					if (perc != perc)
						perc = 0.0;
					objs[2] = UtilFormatacao.formatDouble(perc, 2);
					lstRetorno.add(new Object[] { key, objs[0], objs[1], objs[2] });
				}

				Object[] objs = new Object[] { 0, 0, "" };
				objs[0] = new Integer(total);
				objs[1] = new Integer(totalNoPrazo);
				double perc = (new Double(totalNoPrazo) / new Double(total)) * 100;
				if (perc != perc)
					perc = 0.0;
				objs[2] = UtilFormatacao.formatDouble(perc, 2);
				lstRetorno.add(new Object[] { "Todas", objs[0], objs[1], objs[2] });
			}

			if (lstRetorno == null || lstRetorno.size() == 0) {
				lstRetorno = new ArrayList();
				/* Desenvolvedor: Rodrigo Pecci - Data: 30/10/2013 - Horário: 14h15min - ID Citsmart: 120770
				 * Motivo/Comentário: Se lstRetorno for null ou vazio, é necessário retornar somente um ArrayList vazio.
				 */
				//lstRetorno.add(new Object[] { "", 0, 0, "" });
			}
			for (Iterator iterator = paramtersDefinition.iterator(); iterator.hasNext();) {
				Object parametro = (Object) iterator.next();
				if (parametro != null && "org.apache.catalina.connector.RequestFacade".equals(parametro.getClass().getName())) {
					iterator.remove();
					break;
				}
			}
		} catch (PersistenceException e) {
			e.printStackTrace();
			return null;
		}
		return lstRetorno;
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

			sql.insert(0, " SELECT " + valores + " FROM (");
			sql.insert(sql.length(), ") WHERE rownum <= " + maximoRegistros);

		} else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {

			sql.replace(0, 6, " SELECT " + valores + " FROM (SELECT ROW_NUMBER() OVER(order by (select 1)) rownum, ");
			sql.append(" ) as teste where rownum between 0 and " + maximoRegistros);

		} else {
			sql.append(" LIMIT " + maximoRegistros);
		}
	}
}
