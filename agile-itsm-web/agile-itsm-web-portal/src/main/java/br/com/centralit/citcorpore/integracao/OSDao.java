package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FaturaDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.RelatorioAcompanhamentoDTO;
import br.com.centralit.citcorpore.bean.RelatorioOrdemServicoUstDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class OSDao extends CrudDaoDefaultImpl {

    public OSDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("idOS", "idOS", true, true, false, false));
	listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
	listFields.add(new Field("idClassificacaoOS", "idClassificacaoOS", false, false, false, false));
	listFields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
	listFields.add(new Field("ano", "ano", false, false, false, false));
	listFields.add(new Field("numero", "numero", false, false, false, false));
	listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
	listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
	listFields.add(new Field("demanda", "demanda", false, false, false, false));
	listFields.add(new Field("objetivo", "objetivo", false, false, false, false));
	listFields.add(new Field("nomeAreaRequisitante", "nomeAreaRequisitante", false, false, false, false));
	listFields.add(new Field("situacaoOS", "situacaoOS", false, false, false, false));
	listFields.add(new Field("obsFinalizacao", "obsFinalizacao", false, false, false, false));
	listFields.add(new Field("quantidadeGlosasAnterior", "quantidadeGlosasAnterior", false, false, false, false));
	listFields.add(new Field("idOSPai", "idOSPai", false, false, false, false));
	listFields.add(new Field("quantidade", "quantidade", false, false, false, false));
	listFields.add(new Field("dataEmissao", "dataEmissao", false, false, false, false));
	listFields.add(new Field("idgrupoassinatura", "idGrupoAssinatura", false, false, false, false));

	return listFields;
    }

    @Override
    public String getTableName() {
	return this.getOwner() + "OS";
    }

    @Override
    public Collection list() throws PersistenceException {
	return null;
    }

    @Override
    public Class getBean() {
	return OSDTO.class;
    }

    @Override
    public Collection find(IDto arg0) throws PersistenceException {
	return null;
    }

    public Collection findByIdContratoAndSituacao(Integer parm, Integer sit) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("idContrato", "=", parm));
	condicao.add(new Condition("situacaoOS", "=", sit));
	ordenacao.add(new Order("numero"));
	return super.findByCondition(condicao, ordenacao);
    }

    public Collection findByIdContrato(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("idContrato", "=", parm));
	ordenacao.add(new Order("numero"));
	return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdContrato(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	condicao.add(new Condition("idContrato", "=", parm));
	super.deleteByCondition(condicao);
    }

    public Collection findByIdClassificacaoOS(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("idClassificacaoOS", "=", parm));
	ordenacao.add(new Order("numero"));
	return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByIdClassificacaoOS(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	condicao.add(new Condition("idClassificacaoOS", "=", parm));
	super.deleteByCondition(condicao);
    }

    public Collection findByAno(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("ano", "=", parm));
	ordenacao.add(new Order("numero"));
	return super.findByCondition(condicao, ordenacao);
    }

    public Collection findByIdOsPai(Integer idospai) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("idOSPai", "=", idospai));
	ordenacao.add(new Order("numero"));
	return super.findByCondition(condicao, ordenacao);
    }

    public void deleteByAno(Integer parm) throws PersistenceException {
	List condicao = new ArrayList();
	condicao.add(new Condition("ano", "=", parm));
	super.deleteByCondition(condicao);
    }

    public void updateSituacao(Integer idOs, Integer situacao) throws PersistenceException {
	OSDTO osDTO = new OSDTO();
	osDTO.setIdOS(idOs);
	osDTO.setSituacaoOS(situacao);
	super.updateNotNull(osDTO);
    }

    public void updateObsFinal(Integer idOs, String obsFinal) throws PersistenceException {
	OSDTO osDTO = new OSDTO();
	osDTO.setIdOS(idOs);
	osDTO.setObsFinalizacao(obsFinal);
	super.updateNotNull(osDTO);
    }

    public void updateQuantidade(Integer idOs, Integer quantidade) throws PersistenceException {
	OSDTO osDTO = new OSDTO();
	osDTO.setIdOS(idOs);
	osDTO.setQuantidade(quantidade);
	super.updateNotNull(osDTO);
    }

    @Override
    public void updateNotNull(IDto obj) throws PersistenceException {
	super.updateNotNull(obj);
    }

    public void cancelaOSFilhas(OSDTO os) throws PersistenceException {
	Collection<OSDTO> resp = this.retornaSeExisteOSFilha(os.getIdOS());
	if((resp != null) && (resp.size() > 0)){
	    for (OSDTO osDto : resp) {
		this.updateSituacao(osDto.getIdOS(), OSDTO.CANCELADA);
	    }
	}
    }

    /**
     * @author rodrigo.oliveira
     * @param idOSPai
     * @return
     * @throws Exception
     */
    public Collection retornaSeExisteOSFilha(Integer idOSPai) throws PersistenceException {
	List lstParametros = new ArrayList();
	String sql = "SELECT * FROM "+ this.getTableName() +" WHERE idospai = ? ";
	lstParametros.add(idOSPai);
	List lstDados = super.execSQL(sql, lstParametros.toArray());
	List fields = new ArrayList();
	fields.add("idOS");
	return super.listConvertion(OSDTO.class, lstDados, fields);
    }

    public Collection findByIdContratoAndPeriodoAndSituacao(Integer idContrato, Date dataInicio, Date dataFim, Integer[] situacao) throws PersistenceException {
	List lstParametros = new ArrayList();
	String sql = "SELECT idOS, O.idServicoContrato, numero, ano, O.dataInicio, O.dataFim, demanda, O.objetivo, situacaoOS, nomeServico, quantidadeGlosasAnterior, quantidade, idOSPai, ";
	sql = sql + " (SELECT SUM(custoAtividade) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS custoOS, ";
	sql = sql + " (SELECT SUM(glosaAtividade) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS glosaOS ";
	sql = sql + " FROM " + this.getTableName() + " O ";
	sql = sql + " INNER JOIN SERVICOCONTRATO SC ON SC.idServicoContrato = O.idServicoContrato ";
	sql = sql + " INNER JOIN SERVICO S ON S.idServico = SC.idServico ";
	sql = sql + "WHERE O.idServicoContrato IN (SELECT idServicoContrato FROM SERVICOCONTRATO WHERE idContrato = ?) ";
	lstParametros.add(idContrato);
	if ((situacao != null) || ((dataInicio != null) || (dataFim != null))) {
	    if ((dataInicio != null) && (dataFim != null)) {
		sql = sql + "AND ";
		sql = sql + " (O.dataInicio BETWEEN ? AND ?) ";
		lstParametros.add(dataInicio);
		lstParametros.add(dataFim);
	    } else {
		if (dataInicio != null) {
		    sql = sql + "AND ";
		    sql = sql + " (O.dataInicio = ?) ";
		    lstParametros.add(dataInicio);
		}
		if (dataFim != null) {
		    sql = sql + "AND ";
		    sql = sql + " (O.dataFim = ?) ";
		    lstParametros.add(dataFim);
		}
	    }
	    if (situacao != null) {
		sql = sql + "AND ";
		sql = sql + " (situacaoOS IN (";
		String strSqlAux = "";
		for (Integer element : situacao) {
		    if (!strSqlAux.equalsIgnoreCase("")) {
			strSqlAux = strSqlAux + ",";
		    }
		    strSqlAux = strSqlAux + "" + element.intValue() + "";
		}
		sql = sql + strSqlAux;
		sql = sql + ")) ";
	    }
	}

	//sql += " AND IDOSPAI IS NOT NULL ";

	sql = sql + "ORDER BY O.dataInicio, O.dataFim, idOS ";
	Object[] parametros = lstParametros.toArray();
	List lstDados = super.execSQL(sql, parametros);

	List fields = new ArrayList();
	fields.add("idOS");
	fields.add("idServicoContrato");
	fields.add("numero");
	fields.add("ano");
	fields.add("dataInicio");
	fields.add("dataFim");
	fields.add("demanda");
	fields.add("objetivo");
	fields.add("situacaoOS");
	fields.add("nomeServico");
	fields.add("quantidadeGlosasAnterior");
	fields.add("quantidade");
	fields.add("idOSPai");
	fields.add("custoOS");
	fields.add("glosaOS");

	return super.listConvertion(OSDTO.class, lstDados, fields);
    }

    public Collection findByIdContratoAndPeriodoAndSituacao(Integer idContrato, Date dataInicio, Date dataFim, Integer[] situacao, Integer idosPai) throws PersistenceException {
	List lstParametros = new ArrayList();
	String sql = "SELECT idOS, O.idServicoContrato, numero, ano, O.dataInicio, O.dataFim, demanda, O.objetivo, situacaoOS, nomeServico, quantidadeGlosasAnterior, quantidade, idOSPai, ";
	sql = sql + " (SELECT SUM(custoAtividade) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS custoOS, ";
	sql = sql + " (SELECT SUM(glosaAtividade) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS glosaOS ";
	sql = sql + " FROM " + this.getTableName() + " O ";
	sql = sql + " INNER JOIN SERVICOCONTRATO SC ON SC.idServicoContrato = O.idServicoContrato ";
	sql = sql + " INNER JOIN SERVICO S ON S.idServico = SC.idServico ";
	sql = sql + "WHERE O.idServicoContrato IN (SELECT idServicoContrato FROM SERVICOCONTRATO WHERE idContrato = ?) ";
	lstParametros.add(idContrato);
	if ((situacao != null) || ((dataInicio != null) || (dataFim != null))) {
	    if ((dataInicio != null) && (dataFim != null)) {
		sql = sql + "AND ";
		sql = sql + " (O.dataInicio BETWEEN ? AND ?) ";
		lstParametros.add(dataInicio);
		lstParametros.add(dataFim);
	    } else {
		if (dataInicio != null) {
		    sql = sql + "AND ";
		    sql = sql + " (O.dataInicio = ?) ";
		    lstParametros.add(dataInicio);
		}
		if (dataFim != null) {
		    sql = sql + "AND ";
		    sql = sql + " (O.dataFim = ?) ";
		    lstParametros.add(dataFim);
		}
	    }
	    if (situacao != null) {
		sql = sql + "AND ";
		sql = sql + " (situacaoOS IN (";
		String strSqlAux = "";
		for (Integer element : situacao) {
		    if (!strSqlAux.equalsIgnoreCase("")) {
			strSqlAux = strSqlAux + ",";
		    }
		    strSqlAux = strSqlAux + "" + element.intValue() + "";
		}
		sql = sql + strSqlAux;
		sql = sql + ")) ";
	    }
	}

	if (idosPai != null) {
	    sql += " AND IDOSPAI = ? ";
	    lstParametros.add(idosPai);
	} else {
	    sql += " AND IDOSPAI IS NULL ";
	}

	sql = sql + "ORDER BY O.dataInicio, O.dataFim, idOS ";
	Object[] parametros = lstParametros.toArray();
	List lstDados = super.execSQL(sql, parametros);

	List fields = new ArrayList();
	fields.add("idOS");
	fields.add("idServicoContrato");
	fields.add("numero");
	fields.add("ano");
	fields.add("dataInicio");
	fields.add("dataFim");
	fields.add("demanda");
	fields.add("objetivo");
	fields.add("situacaoOS");
	fields.add("nomeServico");
	fields.add("quantidadeGlosasAnterior");
	fields.add("quantidade");
	fields.add("idOSPai");
	fields.add("custoOS");
	fields.add("glosaOS");

	return super.listConvertion(OSDTO.class, lstDados, fields);
    }

    public Collection listOSHomologadasENaoAssociadasFatura(Integer idContrato) throws PersistenceException {
	List lstParametros = new ArrayList();
	String sql = "SELECT idOS, O.idServicoContrato, numero, ano, O.dataInicio, O.dataFim, demanda, O.objetivo, situacaoOS, nomeServico, ";
	sql = sql + " (SELECT SUM(custoAtividade) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS custoOS, ";
	sql = sql + " (SELECT SUM(glosaAtividade) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS glosaOS ";
	sql = sql + " FROM " + this.getTableName() + " O ";
	sql = sql + " INNER JOIN SERVICOCONTRATO SC ON SC.idServicoContrato = O.idServicoContrato ";
	sql = sql + " INNER JOIN SERVICO S ON S.idServico = SC.idServico ";
	sql = sql + "WHERE O.idServicoContrato IN (SELECT idServicoContrato FROM SERVICOCONTRATO WHERE idContrato = ?) AND ";
	sql = sql + " idOS NOT IN (SELECT fos.idOs FROM faturaos fos JOIN fatura f ON f.idfatura = fos.idfatura WHERE f.situacaofatura <> ? AND fos.idos = idOs) AND O.situacaoOS = ? ";
	sql = sql + "ORDER BY O.dataInicio, O.dataFim, idOS ";
	lstParametros.add(idContrato);
	lstParametros.add(FaturaDTO.CANCELADA);
	lstParametros.add(OSDTO.EXECUTADA);

	Object[] parametros = lstParametros.toArray();
	List lstDados = super.execSQL(sql, parametros);

	List fields = new ArrayList();
	fields.add("idOS");
	fields.add("idServicoContrato");
	fields.add("numero");
	fields.add("ano");
	fields.add("dataInicio");
	fields.add("dataFim");
	fields.add("demanda");
	fields.add("objetivo");
	fields.add("situacaoOS");
	fields.add("nomeServico");
	fields.add("custoOS");
	fields.add("glosaOS");

	return super.listConvertion(OSDTO.class, lstDados, fields);
    }

    public Collection listOSAssociadasFatura(Integer idFatura) throws PersistenceException {
	List lstParametros = new ArrayList();
	String sql = "SELECT idOS, O.idServicoContrato, numero, ano, O.dataInicio, O.dataFim, demanda, O.objetivo, situacaoOS, nomeServico, ";
	sql = sql + " (SELECT SUM(custoAtividade) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS custoOS, ";
	sql = sql + " (SELECT SUM(glosaAtividade) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS glosaOS, ";
	sql = sql + " (SELECT SUM(qtdeExecutada) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS executadoOS ";
	sql = sql + " FROM " + this.getTableName() + " O ";
	sql = sql + " INNER JOIN SERVICOCONTRATO SC ON SC.idServicoContrato = O.idServicoContrato ";
	sql = sql + " INNER JOIN SERVICO S ON S.idServico = SC.idServico ";
	sql = sql + "WHERE idOS IN (SELECT idOs FROM FaturaOS where idFatura = ?) ";
	sql = sql + "ORDER BY O.dataInicio, O.dataFim, idOS ";
	lstParametros.add(idFatura);

	Object[] parametros = lstParametros.toArray();
	List lstDados = super.execSQL(sql, parametros);

	List fields = new ArrayList();
	fields.add("idOS");
	fields.add("idServicoContrato");
	fields.add("numero");
	fields.add("ano");
	fields.add("dataInicio");
	fields.add("dataFim");
	fields.add("demanda");
	fields.add("objetivo");
	fields.add("situacaoOS");
	fields.add("nomeServico");
	fields.add("custoOS");
	fields.add("glosaOS");
	fields.add("executadoOS");

	return super.listConvertion(OSDTO.class, lstDados, fields);
    }

    public Collection listOSByIdAtividadePeriodica(Integer idatividadeperiodica) throws PersistenceException {
	String sql = "SELECT os.idOS, os.idcontrato, os.idclassificacaoos, os.idservicocontrato, os.ano, os.numero, "
		+ "   os.datainicio, os.datafim, os.demanda, os.objetivo, os.nomearearequisitante, os.situacaoos, os.obsfinalizacao "
		+ "FROM osatividadeperiodica osAtv INNER JOIN os  ON os.idos = osAtv.idos WHERE osAtv.idatividadeperiodica = ?";

	List lstParametros = new ArrayList();
	lstParametros.add(idatividadeperiodica);
	Object[] parametros = lstParametros.toArray();
	List lstDados = super.execSQL(sql, parametros);

	List listFields = new ArrayList<>();
	listFields.add("idOS");
	listFields.add("idContrato");
	listFields.add("idClassificacaoOS");
	listFields.add("idServicoContrato");
	listFields.add("ano");
	listFields.add("numero");
	listFields.add("dataInicio");
	listFields.add("dataFim");
	listFields.add("demanda");
	listFields.add("objetivo");
	listFields.add("nomeAreaRequisitante");
	listFields.add("situacaoOS");
	listFields.add("obsFinalizacao");

	return super.listConvertion(OSDTO.class, lstDados, listFields);
    }

    public Collection listAtividadePeridodicaByIdos(Integer idos) throws PersistenceException {
	String sql = "SELECT os.idOS, os.idcontrato, os.idclassificacaoos, os.idservicocontrato, os.ano, os.numero, "
		+ "   os.datainicio, os.datafim, os.demanda, os.objetivo, os.nomearearequisitante, os.situacaoos, os.obsfinalizacao,  atvper.tituloAtividade, atvper.idAtividadePeriodica "
		+ "FROM osatividadeperiodica osAtv " + "INNER JOIN os  ON os.idos = osAtv.idos "
		+ "INNER JOIN atividadeperiodica atvper on osAtv.idatividadeperiodica = atvper.idatividadeperiodica " + "WHERE os.idos = ?";

	List lstParametros = new ArrayList();
	lstParametros.add(idos);
	Object[] parametros = lstParametros.toArray();
	List lstDados = super.execSQL(sql, parametros);

	List listFields = new ArrayList<>();
	listFields.add("idOS");
	listFields.add("idContrato");
	listFields.add("idClassificacaoOS");
	listFields.add("idServicoContrato");
	listFields.add("ano");
	listFields.add("numero");
	listFields.add("dataInicio");
	listFields.add("dataFim");
	listFields.add("demanda");
	listFields.add("objetivo");
	listFields.add("nomeAreaRequisitante");
	listFields.add("situacaoOS");
	listFields.add("obsFinalizacao");
	listFields.add("tituloAtividade");
	listFields.add("idAtividadePeriodica");

	return super.listConvertion(OSDTO.class, lstDados, listFields);
    }

    public Collection listOSByIds(Integer idContrato, Integer[] idOSs) throws PersistenceException {
	List lstParametros = new ArrayList();
	String sql = "SELECT idOS, O.idServicoContrato, numero, ano, O.dataInicio, O.dataFim, demanda, O.objetivo, situacaoOS, nomeServico, ";
	sql = sql + " (SELECT SUM(custoAtividade) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS custoOS, ";
	sql = sql + " (SELECT SUM(glosaAtividade) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS glosaOS, ";
	sql = sql + " (SELECT SUM(qtdeExecutada) FROM ATIVIDADESOS ATVOS WHERE ATVOS.idOS = O.idOS) AS executadoOS ";
	sql = sql + " FROM " + this.getTableName() + " O ";
	sql = sql + " INNER JOIN SERVICOCONTRATO SC ON SC.idServicoContrato = O.idServicoContrato ";
	sql = sql + " INNER JOIN SERVICO S ON S.idServico = SC.idServico ";
	sql = sql + "WHERE O.idServicoContrato IN (SELECT idServicoContrato FROM SERVICOCONTRATO WHERE idContrato = ?) AND ";
	sql = sql + " idOS IN (-999";
	if (idOSs != null) {
	    for (Integer idOS : idOSs) {
		sql = sql + ", " + idOS;
	    }
	}
	sql = sql + ") ";
	sql = sql + "ORDER BY O.dataInicio, O.dataFim, idOS ";
	lstParametros.add(idContrato);

	Object[] parametros = lstParametros.toArray();
	List lstDados = super.execSQL(sql, parametros);

	List fields = new ArrayList();
	fields.add("idOS");
	fields.add("idServicoContrato");
	fields.add("numero");
	fields.add("ano");
	fields.add("dataInicio");
	fields.add("dataFim");
	fields.add("demanda");
	fields.add("objetivo");
	fields.add("situacaoOS");
	fields.add("nomeServico");
	fields.add("custoOS");
	fields.add("glosaOS");
	fields.add("executadoOS");

	return super.listConvertion(OSDTO.class, lstDados, fields);
    }

    /**
     * Método que atualiza observacao das OS's não homologadas
     *
     * @param observacao
     * @throws throws Exception
     */
    public List<OSDTO> buscaOsEmCriacao() throws PersistenceException {

	List parametros = new ArrayList();
	parametros.add(OSDTO.EM_CRIACAO.intValue());

	// Selecionar o id das OS que estão criação
	String sql = "SELECT idos FROM " + this.getTableName() + " WHERE situacaoos = ? ";

	List respOs = super.execSQL(sql, parametros.toArray());

	List fields = new ArrayList();
	fields.add("idOS");

	List<OSDTO> listOs = super.listConvertion(OSDTO.class, respOs, fields);

	return listOs;

    }

    public Collection<RelatorioOrdemServicoUstDTO> listaCustoAtividadeOrdemServicoPorPeriodo(RelatorioOrdemServicoUstDTO relatorio) throws PersistenceException {
	StringBuilder sql = new StringBuilder();
	List parametro = new ArrayList();
	List listaRetornor = new ArrayList();
	sql.append("select sum(tabelaAux.somacustoatividade) , tabelaAux.mes, count(tabelaAux.idtipoeventoservico), tabelaAux.nometipoeventoservico From ");
	sql.append("(SELECT sum(atividadesos.custoatividade) as somacustoatividade ,  ");

	if(CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.ORACLE)|| CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
	    sql.append("EXTRACT(MONTH FROM os.datainicio) as mes ,");
	}else{
	    sql.append("month(os.datainicio) as mes, ");
	}
	sql.append("servico.idtipoeventoservico, ");
	sql.append("nometipoeventoservico, ");
	sql.append("os.idos ");
	sql.append("FROM os, atividadesos, servicocontrato, servico, tipoeventoservico, contratos ");
	sql.append("where os.idservicocontrato = servicocontrato.idservicocontrato ");
	sql.append("and servicocontrato.idservico = servico.idservico ");
	sql.append("and contratos.idcontrato = servicocontrato.idcontrato ");
	sql.append("and tipoeventoservico.idtipoeventoservico = servico.idtipoeventoservico ");
	sql.append("and  atividadesos.idos = os.idos ");
	if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.ORACLE)|| CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
	    sql.append("AND EXTRACT(YEAR FROM os.datainicio)= ? ");
	}else{
	    sql.append("and year(os.datainicio) = ? ");
	}
	sql.append("and os.idospai is not null  ");
	sql.append("and contratos.idcontrato = ? ");
	sql.append("and os.situacaoos =  "+ OSDTO.EXECUTADA +" " );

	if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.ORACLE)|| CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
	    sql.append("GROUP BY  EXTRACT(MONTH FROM OS.DATAINICIO), SERVICO.IDTIPOEVENTOSERVICO, NOMETIPOEVENTOSERVICO, OS.IDOS) TABELAAUX ");
	}else{
	    if(CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.SQLSERVER)){
		sql.append("group by month(os.datainicio),servico.idtipoeventoservico, nometipoeventoservico, os.idos ) as tabelaAux ");
	    }else{
		sql.append("group by 2,3,4, os.idos) as tabelaAux ");
	    }

	}

	sql.append("inner join os on tabelaAux.idos = os.idos ");

	if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.ORACLE)|| CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
	    sql.append("GROUP BY TABELAAUX.NOMETIPOEVENTOSERVICO, TABELAAUX.MES ");
	}else{
	    if(CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.SQLSERVER)){
		sql.append("group by tabelaAux.nometipoeventoservico , tabelaAux.mes");
	    }else{
		sql.append("group by 2,4 ");
	    }

	}

	parametro.add(relatorio.getAnoInteger());
	parametro.add(relatorio.getIdContrato());

	List list = super.execSQL(sql.toString(), parametro.toArray());

	listaRetornor.add("custoAtividade");
	listaRetornor.add("periodoDouble");
	listaRetornor.add("quantidadePorTipoEventoServico");
	listaRetornor.add("tipoEventoServico");
	if ((list != null) && !list.isEmpty()) {
	    Collection<RelatorioOrdemServicoUstDTO> listaCustoAtividadePorPeriodo = this.listConvertion(RelatorioOrdemServicoUstDTO.class, list, listaRetornor);
	    return listaCustoAtividadePorPeriodo;

	}

	return null;

    }

    public Collection<RelatorioOrdemServicoUstDTO> listaAnos() throws PersistenceException {
	StringBuilder sql = new StringBuilder();
	List listaRetornor = new ArrayList();

	if(CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.ORACLE) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL) ) {
	    sql.append("select distinct  EXTRACT(YEAR FROM os.datainicio) from " + this.getTableName() + " order by EXTRACT(YEAR FROM os.datainicio)  ");
	} else{

	    sql.append("select distinct  year(os.datainicio) from " + this.getTableName() + " order by year(os.datainicio)  ");

	}


	List list = super.execSQL(sql.toString(), null);
	listaRetornor.add("ano");

	if ((list != null) && !list.isEmpty()) {
	    Collection<RelatorioOrdemServicoUstDTO> listaCustoAtividadePorPeriodo = this.listConvertion(RelatorioOrdemServicoUstDTO.class, list, listaRetornor);
	    return listaCustoAtividadePorPeriodo;
	}

	return null;

    }

    public Collection<RelatorioAcompanhamentoDTO> listaAcompanhamentoPorPeriodoDoContrato(RelatorioAcompanhamentoDTO relatorio) throws PersistenceException {
	StringBuilder sql = new StringBuilder();
	List parametro = new ArrayList();
	List listaRetornor = new ArrayList();

	if(CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.ORACLE) || CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.POSTGRESQL) ){
	    sql.append("SELECT sum(atividadesos.custoatividade) , EXTRACT(MONTH FROM os.datainicio), EXTRACT(YEAR FROM os.datainicio), contratos.valorestimado , contratos.datacontrato,contratos.datafimcontrato  ");
	}else{
	    sql.append("SELECT sum(atividadesos.custoatividade) , month(os.datainicio), year(os.datainicio), contratos.valorestimado , contratos.datacontrato,contratos.datafimcontrato  ");
	}

	sql.append("FROM os ");
	sql.append("inner join atividadesos atividadesos on atividadesos.idos = os.idos ");
	sql.append("inner join servicocontrato servicocontrato on os.idservicocontrato = servicocontrato.idservicocontrato ");
	sql.append("inner join servico servico on servicocontrato.idservico = servico.idservico ");
	sql.append("inner join contratos contratos on contratos.idcontrato = servicocontrato.idcontrato ");
	sql.append("where os.idospai is not null  ");
	sql.append("and contratos.idcontrato = ? ");
	sql.append("and os.situacaoos = " + OSDTO.EXECUTADA +" ");




	if(CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.ORACLE)|| CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
	    sql.append("GROUP BY EXTRACT(MONTH FROM os.datainicio),EXTRACT(YEAR FROM OS.DATAINICIO),contratos.valorestimado , contratos.datacontrato,contratos.datafimcontrato  ");
	}else{
	    sql.append("group by month(os.datainicio), year(os.datainicio), contratos.valorestimado , contratos.datacontrato,contratos.datafimcontrato  ");
	}
	parametro.add(relatorio.getIdContrato());

	List list = super.execSQL(sql.toString(), parametro.toArray());

	listaRetornor.add("custoAtividade");
	listaRetornor.add("numeroMesDouble");
	listaRetornor.add("anoDouble");
	listaRetornor.add("valorEstimadoContrato");
	listaRetornor.add("dataInicioContrato");
	listaRetornor.add("dataFimContrato");
	if ((list != null) && !list.isEmpty()) {
	    Collection<RelatorioAcompanhamentoDTO> listaAcompanhamentoPorPeriodoDoContrato = this.listConvertion(RelatorioAcompanhamentoDTO.class, list, listaRetornor);
	    return listaAcompanhamentoPorPeriodoDoContrato;
	}
	return null;

    }

}
