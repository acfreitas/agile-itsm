package br.com.centralit.citcorpore.integracao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"rawtypes","unchecked"})
public class RequisicaoViagemDAO extends CrudDaoDefaultImpl{

	public RequisicaoViagemDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idsolicitacaoservico" ,"idSolicitacaoServico", true, false, false, false));
		listFields.add(new Field("idcidadeorigem" ,"idCidadeOrigem", false, false, false, false));
		listFields.add(new Field("idcidadedestino" ,"idCidadeDestino", false, false, false, false));
		listFields.add(new Field("idprojeto" ,"idProjeto", false, false, false, false));
        listFields.add(new Field("idcentroresultado" ,"idCentroCusto", false, false, false, false));
		listFields.add(new Field("idmotivoviagem" ,"idMotivoViagem", false, false, false, false));
		listFields.add(new Field("idaprovacao" ,"idAprovacao", false, false, false, false));
		listFields.add(new Field("descricaomotivo" ,"descricaoMotivo", false, false, false, false));
		listFields.add(new Field("datainicio" ,"dataInicioViagem", false, false, false, false));
		listFields.add(new Field("datafim" ,"dataFimViagem", false, false, false, false));
		listFields.add(new Field("qtdedias" ,"qtdeDias", false, false, false, false));
		listFields.add(new Field("estado" ,"estado", false, false, false, false));
		listFields.add(new Field("tarefainiciada" ,"tarefaIniciada", false, false, false, false));
		listFields.add(new Field("remarcacao" ,"remarcacao", false, false, false, false));
		listFields.add(new Field("iditemtrabalho" ,"idItemTrabalho", false, false, false, false));
		listFields.add(new Field("cancelarrequisicao" ,"cancelarRequisicao", false, false, false, false));
		
		return listFields;
	}

	@Override
	public String getTableName() {
		return this.getOwner() + "requisicaoviagem";
	}

	@Override
	public Collection list() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getBean() {
		return RequisicaoViagemDTO.class;
	}

	/* (non-Javadoc)
	 * @see br.com.citframework.integracao.CrudDaoDefaultImpl#updateNotNull(br.com.citframework.dto.IDto)
	 */
	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		// TODO Auto-generated method stub
		super.updateNotNull(obj);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection<RequisicaoViagemDTO> recuperaRequisicaoByFluxo(RequisicaoViagemDTO requisicaoViagemDto)throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();
		List lista = new ArrayList();
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataInicio");
        listRetorno.add("dataFim");
        listRetorno.add("descricaoMotivo");
        listRetorno.add("estado");		
		sql.append("select distinct rv.idsolicitacaoservico, rv.datainicio, rv.datafim, rv.cidadeorigem, rv.cidadedestino, rv.estado  from bpm_atribuicaofluxo aff inner join bpm_itemtrabalhofluxo i "
				+ "ON aff.iditemtrabalho = i.iditemtrabalho inner join execucaosolicitacao ex on ex.idinstanciafluxo = i.idinstancia inner join usuario u ON u.idusuario = aff.idusuario inner join bpm_elementofluxo el on el.idelemento = i.idelemento inner "
				+ "join requisicaoviagem rv on rv.idsolicitacaoservico = ex.idsolicitacaoservico  "
				+ " where ex.idsolicitacaoservico = ?   and  el.nome = 'Prestação de Contas' and rv.estado = 'Aguardando Prestação de Contas'  ");
				
		parametro.add(requisicaoViagemDto.getIdSolicitacaoServico());
		
		if(requisicaoViagemDto.getDataInicio() != null){
			sql.append("and rv.datainicio >= ? ");
			sql.append("and rv.datainicio <= ? ");
			parametro.add(requisicaoViagemDto.getDataInicioViagem());
			parametro.add(requisicaoViagemDto.getDataInicioViagemAux());
		}
		
		if(requisicaoViagemDto.getDataFim() != null){
			sql.append("and rv.datafim >= ? ");
			sql.append("and rv.datafim <= ? ");
			parametro.add(requisicaoViagemDto.getDataFimViagem());
			parametro.add(requisicaoViagemDto.getDataFimViagemAux());
		}
				
		
		lista = this.execSQL(sql.toString(), parametro.toArray());
		return this.engine.listConvertion(getBean(), lista, listRetorno);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection<RequisicaoViagemDTO> recuperaRequisicoesViagem(RequisicaoViagemDTO requisicaoViagemDto, Integer pgAtual, Integer qtdPaginacao) throws PersistenceException {
		
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
			sql.append(" ;WITH TabelaTemporaria AS ( ");
		}
		
        sql.append(" SELECT idsolicitacaoservico, datainicio, datafim, descricaomotivo, estado ");
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
        	sql.append(" , ROW_NUMBER() OVER (ORDER BY idsolicitacaoservico) AS Row ");
    	}
        sql.append(" FROM requisicaoviagem ");
        sql.append(" WHERE estado = 'Aguardando Prestação de Contas'");
        
        if (requisicaoViagemDto.getIdSolicitacaoServico() != null) {
            sql.append(" AND idSolicitacaoServico = ? ");
            parametro.add(requisicaoViagemDto.getIdSolicitacaoServico());
        }
        
	    if (requisicaoViagemDto.getDataInicio() != null) {
            sql.append(" AND datainicio between ? ");
            parametro.add(requisicaoViagemDto.getDataInicio());
        }
        
	    if (requisicaoViagemDto.getDataFim() != null) {
            sql.append(" AND ? ");
            parametro.add(requisicaoViagemDto.getDataFim());
        }
        
        /*sql.append(" ORDER BY idsolicitacaoservico");*/
        	
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
        	sql.append(" ORDER BY idsolicitacaoservico");
        	Integer pgTotal = pgAtual * qtdPaginacao;
        	pgAtual = pgTotal - qtdPaginacao;
        	sql.append(" LIMIT " + qtdPaginacao + " OFFSET " +pgAtual);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)){
        	sql.append(" ORDER BY idsolicitacaoservico");
        	Integer pgTotal = pgAtual * qtdPaginacao;
        	pgAtual = pgTotal - qtdPaginacao;
        	sql.append(" LIMIT " +pgAtual+ ", "+qtdPaginacao);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)){
        	Integer quantidadePaginator2 = new Integer(0);
        	if (pgAtual > 0) {
        		quantidadePaginator2 = qtdPaginacao * pgAtual;
        		pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
        	}else{
        		quantidadePaginator2 = qtdPaginacao;
        		pgAtual = 0;
        	}
        	sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row> "+pgAtual+" and Row<"+(quantidadePaginator2+1)+" ");
        }

        String sqlOracle = "";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)){
        	Integer quantidadePaginator2 = new Integer(0);
        	if (pgAtual > 1) {
        		quantidadePaginator2 = qtdPaginacao * pgAtual;
        		pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
        		pgAtual = pgAtual + 1;
        	}else{
        		quantidadePaginator2 = qtdPaginacao;
        		pgAtual = 0;
        	}
        	int intInicio = pgAtual;
        	int intLimite = quantidadePaginator2;
        	sqlOracle = paginacaoOracle(sql.toString(), intInicio, intLimite);
        }
        
        List lista = new ArrayList();
        
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)){
        	lista = this.execSQL(sqlOracle, parametro.toArray());
    	}else{
    		lista = this.execSQL(sql.toString(), parametro.toArray());
    	}
        
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataInicio");
        listRetorno.add("dataFim");
        listRetorno.add("descricaoMotivo");
        listRetorno.add("estado");
        
        return this.engine.listConvertion(getBean(), lista, listRetorno);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Integer calculaTotalPaginas(Integer itensPorPagina, RequisicaoViagemDTO requisicaoViagemDto) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
        sql.append("SELECT COUNT(*) ");
        sql.append("FROM requisicaoviagem ");
        sql.append("WHERE estado <> 'Rejeitada Planejamento' AND estado <> 'Não Aprovada' AND estado <>'Finalizada'");
        
        if (requisicaoViagemDto.getIdSolicitacaoServico() != null) {
            sql.append(" AND idSolicitacaoServico = ? ");
            parametro.add(requisicaoViagemDto.getIdSolicitacaoServico());
        }
        
	    if (requisicaoViagemDto.getDataInicio() != null) {
            sql.append(" AND datainicio between ? ");
            parametro.add(requisicaoViagemDto.getDataInicio());
        }
        
	    if (requisicaoViagemDto.getDataFim() != null) {
            sql.append(" AND ? ");
            parametro.add(requisicaoViagemDto.getDataFim());
        }
        
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        Long totalLinhaLong = 0l;
        Long totalPagina = 0l;
        Integer total = 0;
        BigDecimal totalLinhaBigDecimal;
        Integer totalLinhaInteger;
        int intLimite = itensPorPagina;
        if(lista != null){
        	Object[] totalLinha = (Object[]) lista.get(0);
        	if(totalLinha != null && totalLinha.length > 0){
        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
        			totalLinhaLong = (Long) totalLinha[0];
        		}
        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
        			totalLinhaBigDecimal = (BigDecimal) totalLinha[0];
        			totalLinhaLong = totalLinhaBigDecimal.longValue();
        		}
        		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
        			totalLinhaInteger = (Integer) totalLinha[0];
        			totalLinhaLong = Long.valueOf(totalLinhaInteger);
        		}
        	}
        }

        if (totalLinhaLong > 0) {
        	totalPagina = (totalLinhaLong / intLimite);
        	if(totalLinhaLong % intLimite != 0){
        		totalPagina = totalPagina + 1;
        	}
        }
        total = Integer.valueOf(totalPagina.toString());
        return total;
        
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public String paginacaoOracle(String strSQL, int intInicio, int intLimite) {
		strSQL = strSQL + " order by idsolicitacaoservico ";
		return "SELECT * FROM (SELECT PAGING.*, ROWNUM PAGING_RN FROM" +
		" (" + strSQL + ") PAGING WHERE (ROWNUM <= " + intLimite + "))" +
		" WHERE (PAGING_RN >= " + intInicio + ") ";
	}

	
	/**
	 * Busca uma coleção de requisicao de viagem pelo idCentroCusto
	 * 
	 * @param idCentroCusto
	 * @return
	 * @throws Exception
	 */
	public Collection findByIdCentroCusto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCentroCusto", "=", parm)); 
		ordenacao.add(new Order("idSolicitacaoServico"));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Retorna uma requisicao de viagem pelo idsolicitacaoservico
	 * 
	 * @param idSolicitacaoServico
	 * @return
	 * @throws Exception
	 */
	public RequisicaoViagemDTO findByIdSolicitacao(Integer idSolicitacaoServico) throws PersistenceException {
		
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		
		List result = new ArrayList<RequisicaoViagemDTO>();
		result = (List) super.findByCondition(condicao, null);
		
		if(result != null && !result.isEmpty())
			return (RequisicaoViagemDTO) result.get(0);
		else
			return null;
		
	}	
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public boolean isEstadoAutorizacao(RequisicaoViagemDTO requisicaoViagemDto) throws PersistenceException {
		List result = null;
		if(requisicaoViagemDto.getEstado().equalsIgnoreCase(RequisicaoViagemDTO.AGUARDANDO_APROVACAO)){
			List condicao = new ArrayList();
			List ordenacao = new ArrayList();
			condicao.add(new Condition("idSolicitacaoServico", "=", requisicaoViagemDto.getIdSolicitacaoServico()));
			condicao.add(new Condition("tarefaIniciada", "=", "S"));
			result = (List) super.findByCondition(condicao, ordenacao);
			if(result == null)
				return true;
		}
		return  false;
	}
	
	/**
	 * Retorna uma lista de requisicao viagem conforme idsolicitacaoservico e template passados
	 * 
	 * @param idSolicitacaoServico
	 * @param template
	 * @return
	 * @throws Exception
	 */
	public List<RequisicaoViagemDTO> retornaRequisicaoByTemplateAndIdsolicitacao(Integer idSolicitacaoServico, String template) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List lista = new ArrayList();
		StringBuilder  sql = new StringBuilder();
		sql.append("SELECT DISTINCT req.idsolicitacaoservico, ");
		sql.append("                req.idprojeto, ");
		sql.append("                req.idcentroresultado, ");
		sql.append("                req.idcidadeorigem, ");
		sql.append("                req.idcidadedestino, ");
		sql.append("                req.idmotivoviagem, ");
		sql.append("                req.idaprovacao, ");
		sql.append("                req.descricaomotivo, ");
		sql.append("                req.datainicio, ");
		sql.append("                req.datafim, ");
		sql.append("                req.qtdedias, ");
		sql.append("                req.estado, ");
		sql.append("                req.tarefainiciada, ");
		sql.append("                req.remarcacao ");
		sql.append("FROM   bpm_itemtrabalhofluxo i ");
		sql.append("       INNER JOIN bpm_instanciafluxo ins ");
		sql.append("               ON i.idinstancia = ins.idinstancia ");
		sql.append("       INNER JOIN bpm_elementofluxo el ");
		sql.append("               ON el.idelemento = i.idelemento ");
		sql.append("       INNER JOIN execucaosolicitacao ex ");
		sql.append("               ON ex.idinstanciafluxo = i.idinstancia ");
		sql.append("       INNER JOIN requisicaoviagem req ");
		sql.append("               ON ex.idsolicitacaoservico = req.idsolicitacaoservico ");
		sql.append("WHERE  el.template = ? ");
		sql.append("       AND ex.idsolicitacaoservico = ? ");
		sql.append("       AND ( i.situacao = 'Disponivel' ");
		sql.append("              OR i.situacao = 'EmAndamento' ) ");
		sql.append("       AND ins.situacao = 'Aberta'");
		
		parametro.add(idSolicitacaoServico);
		parametro.add(template);
		
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("idProjeto");
		listRetorno.add("idCentroCusto");
		listRetorno.add("idCidadeOrigem");
		listRetorno.add("idCidadeDestino");
		listRetorno.add("idMotivoViagem");
		listRetorno.add("idAprovacao");
		listRetorno.add("descricaoMotivo");
		listRetorno.add("dataInicioViagem");
		listRetorno.add("dataFimViagem");
		listRetorno.add("qtdeDias");
		listRetorno.add("estado");
		listRetorno.add("tarefaIniciada");
		listRetorno.add("remarcacao");

		lista = this.execSQL(sql.toString(), parametro.toArray());
		return listConvertion(this.getBean(), lista , listRetorno);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public void cancelaAutorizacaoByIdSolicitacao(Integer idSolicitacao){
		List parametro = new ArrayList();
		StringBuilder  sql = new StringBuilder();
		
		sql.append("UPDATE bpm_itemtrabalhofluxo i ");
		sql.append("       INNER JOIN execucaosolicitacao ex ");
		sql.append("               ON ex.idinstanciafluxo = i.idinstancia ");
		sql.append("       LEFT JOIN bpm_elementofluxo el ");
		sql.append("              ON el.idelemento = i.idelemento ");
		sql.append("SET    i.situacao = ?, ");
		sql.append("       i.datahorafinalizacao = ? ");
		sql.append("WHERE  ex.idsolicitacaoservico = ? ");
		sql.append("       AND el.nome = ? ");
		sql.append("       AND i.situacao = ? ");
		
		parametro.add("Executado");
		parametro.add(UtilDatas.getDataHoraAtual());
		parametro.add(idSolicitacao);
		parametro.add("Autorizar requisição");
		parametro.add("Disponivel");
		
	    try {
			super.execSQL(sql.toString(), parametro.toArray());
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}