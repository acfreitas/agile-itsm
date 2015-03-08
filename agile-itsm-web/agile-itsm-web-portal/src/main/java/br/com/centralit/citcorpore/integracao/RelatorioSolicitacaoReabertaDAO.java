package br.com.centralit.citcorpore.integracao;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.centralit.citcorpore.bean.RelatorioSolicitacaoReabertaDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

public class RelatorioSolicitacaoReabertaDAO extends CrudDaoDefaultImpl {

	public RelatorioSolicitacaoReabertaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", true, true, false, false));
		listFields.add(new Field("nomeTipoDemandaServico", "nomeTipoDemandaServico", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("dataHoraCriacao", "dataHoraCriacao", false, false, false, false));
		listFields.add(new Field("dataHoraInicioAtendimento", "dataHoraInicioAtendimento", false, false, false, false));
		listFields.add(new Field("origem", "origem", false, false, false, false));
		listFields.add(new Field("dataReabertura", "dataReabertura", false, false, false, false));
		listFields.add(new Field("nomeServico", "nomeServico", false, false, false, false));
		listFields.add(new Field("dataHoraFimAtendimento", "dataHoraFimAtendimento", false, false, false, false));
		listFields.add(new Field("solicitante", "solicitante", false, false, false, false));
		listFields.add(new Field("nomeUnidade", "nomeUnidade", false, false, false, false));
		listFields.add(new Field("nomeResponsavel", "nomeResponsavel", false, false, false, false));
		listFields.add(new Field("descricaoServico", "descricaoServico", false, false, false, false));
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
		return RelatorioSolicitacaoReabertaDTO.class;
	}
	
	public ArrayList<RelatorioSolicitacaoReabertaDTO> listSolicitacaoReaberta(RelatorioSolicitacaoReabertaDTO relatorioSolicitacaoReabertaDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			/**
			 * Checa se há limite para listagem
			 * 
			 * @author thyen.chang
			 */
			boolean seLimita = relatorioSolicitacaoReabertaDTO.getTopList() != 0;
			
			listRetorno.add("idSolicitacaoServico");
			listRetorno.add("nomeTipoDemandaServico");
			listRetorno.add("situacao");
			listRetorno.add("dataHoraCriacao");
			listRetorno.add("dataHoraInicioAtendimento");
			listRetorno.add("dataReabertura");
			listRetorno.add("horaReabertura");
			listRetorno.add("origem");
			listRetorno.add("nomeServico");
			listRetorno.add("dataHoraFimAtendimento");
			listRetorno.add("solicitante");
			listRetorno.add("nomeUnidade");
			listRetorno.add("nomeResponsavel");
			listRetorno.add("descricaoServico");
			
			
			StringBuilder  sql = new StringBuilder();
			sql.append("SELECT DISTINCT ");
			/**
			 * Limite para SQLServer
			 * 
			 * @author thyen.chang
			 */
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))){
				sql.append("TOP "+relatorioSolicitacaoReabertaDTO.getTopList().toString()+" ");
			}
			sql.append("				sc.idsolicitacaoservico, ");
			sql.append("                tds.nometipodemandaservico, ");
			sql.append("                sc.situacao, ");
			sql.append("                sc.datahorasolicitacao AS datahoracriacao, ");
			sql.append("                sc.datahorainiciosla   AS datahorainicioatendimento, ");
			sql.append("                osc.dataregistro       AS datareabertura, ");
			sql.append("                osc.horaregistro       AS horareabertura, ");
			sql.append("                ora.descricao          AS origem, ");
			sql.append("                s.nomeservico, ");
			sql.append("                sc.datahorafim         AS datahorafimatendimento, ");
			sql.append("                e.nome                 AS solicitante, ");
			sql.append("                u.nome                 AS nomeunidade, ");
			sql.append("                responsavel.nome       AS nomeresponsavel, ");
			if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("ORACLE")) {
				sql.append("             cast(sc.descricao as varchar2(4000)) AS descricaoservico ");
			}else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
				sql.append("             cast(sc.descricao as varchar(4000)) AS descricaoservico ");
			}else{
				sql.append("            sc.descricao       AS descricaoservico ");
			}
			sql.append("FROM   solicitacaoservico sc ");
			sql.append("       INNER JOIN tipodemandaservico tds ");
			sql.append("               ON sc.idtipodemandaservico = tds.idtipodemandaservico ");
			sql.append("       INNER JOIN origematendimento ora ");
			sql.append("               ON sc.idorigem = ora.idorigem ");
			sql.append("       INNER JOIN ocorrenciasolicitacao osc ");
			sql.append("               ON sc.idsolicitacaoservico = osc.idsolicitacaoservico ");
			sql.append("       INNER JOIN servicocontrato serc ");
			sql.append("               ON sc.idservicocontrato = serc.idservicocontrato ");
			sql.append("       INNER JOIN servico s ");
			sql.append("               ON serc.idservico = s.idservico ");
			sql.append("       INNER JOIN empregados e ");
			sql.append("               ON sc.idsolicitante = e.idempregado ");
			sql.append(" 	   INNER JOIN execucaosolicitacao exs ");
			sql.append(" 			   ON sc.idsolicitacaoservico =  exs.idsolicitacaoservico ");
			sql.append(" 	   INNER JOIN bpm_itemtrabalhofluxo bpmitem ");
			sql.append(" 	           ON exs.idinstanciafluxo = bpmitem.idinstancia ");
			sql.append(" 	   LEFT JOIN empregados responsavel ");
			sql.append("               ON bpmitem.idresponsavelatual = responsavel.idempregado ");
			sql.append("       LEFT JOIN unidade u ");
			sql.append("              ON sc.idunidade = u.idunidade ");
			sql.append("WHERE ");
			/**
			 * Limite para Oracle
			 * 
			 * @author thyen.chang
			 */
			if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))){
				sql.append(" ROWNUM <= ? AND ");
				parametro.add(relatorioSolicitacaoReabertaDTO.getTopList());
			}
			sql.append("	UPPER(osc.categoria) = UPPER('Reabertura') ");
			sql.append("       AND sc.seqreabertura > 0 ");
			if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("ORACLE")) {
				sql.append("       AND trunc(osc.datainicio) BETWEEN to_date(? ,'yyyy-mm-dd') AND to_date(? ,'yyyy-mm-dd') " );
				parametro.add(UtilDatas.dateToSTRWithFormat(relatorioSolicitacaoReabertaDTO.getDataInicialReabertura(),"yyyy-MM-dd"));
				parametro.add(UtilDatas.dateToSTRWithFormat(relatorioSolicitacaoReabertaDTO.getDataFinalReabertura(),"yyyy-MM-dd"));
			}else{
				sql.append("       AND osc.datainicio BETWEEN ? AND ? ");
				parametro.add(relatorioSolicitacaoReabertaDTO.getDataInicialReabertura());
				parametro.add(relatorioSolicitacaoReabertaDTO.getDataFinalReabertura());
			}
				
			if (relatorioSolicitacaoReabertaDTO.getIdContrato() != null && relatorioSolicitacaoReabertaDTO.getIdContrato()!= 0){
				sql.append(" AND serc.idcontrato = ? ");
				parametro.add(relatorioSolicitacaoReabertaDTO.getIdContrato());
			}
			
			if (relatorioSolicitacaoReabertaDTO.getIdGrupo() != 0){
				sql.append(" AND sc.idgrupoatual = ? ");
				parametro.add(relatorioSolicitacaoReabertaDTO.getIdGrupo());
			}
			
			if (!relatorioSolicitacaoReabertaDTO.getSituacao().equals("0")){
				sql.append(" AND sc.situacao = ? ");
				parametro.add(relatorioSolicitacaoReabertaDTO.getSituacao());
			}

			if (relatorioSolicitacaoReabertaDTO.getIdTipoDemandaServico() != 0){
				sql.append(" AND tds.idtipodemandaservico = ? ");
				parametro.add(relatorioSolicitacaoReabertaDTO.getIdTipoDemandaServico());
			}
			
			if (relatorioSolicitacaoReabertaDTO.getDataInicialEncerramento() != null && relatorioSolicitacaoReabertaDTO.getDataFinalEncerramento() != null){
				sql.append(" AND sc.datahorafim >= ? AND sc.datahorafim <= ? ");
				// para a data final de enconrramento foi necessario fazer este tratamento pois por exemplo, se a pesquisa é até o dia 10/02, buscava <= 10/02/2014 00:00:00
				//ou seja se tivesse sido encerrada no dia 10/02/2014 as 00:01:00 não traria pois tava superior a 10/02/2014 00:00:00, portanto fora da faixa. - Thiago Matias
				Date dFinal= relatorioSolicitacaoReabertaDTO.getDataFinalEncerramento(); 
				long dFinal2; 

				dFinal2 = dFinal.getTime(); 
				dFinal2 = dFinal2 + (86400000*1L); //aumentando em um dia a faixa de consulta.
				dFinal.setTime(dFinal2);

				parametro.add(relatorioSolicitacaoReabertaDTO.getDataInicialEncerramento());
				parametro.add(dFinal);
			}
			/**
			 * Limite da listagem para Postgres e MySQL
			 * 
			 * @author thyen.chang
			 */
			if((seLimita) && ((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL))) ){
				sql.append(" LIMIT ? ");
				parametro.add(relatorioSolicitacaoReabertaDTO.getTopList());
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
		return (ArrayList<RelatorioSolicitacaoReabertaDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<RelatorioSolicitacaoReabertaDTO>() : result);
	}

	private Timestamp preparaHoraInicio(Date dataI) throws ParseException {
		java.sql.Date data = new java.sql.Date(dataI.getTime());
		String dataHora = data + " 00:00:00";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date d = sdf.parse(dataHora);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
		return sqlDate;
	}
	
	private Timestamp preparaHoraFim(Date dataF) throws ParseException {
		java.sql.Date data = new java.sql.Date(dataF.getTime());
		String dataHora = data + " 23:59:59";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date d = sdf.parse(dataHora);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
		return sqlDate;
	}

}