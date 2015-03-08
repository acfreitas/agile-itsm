package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

public class RequisicaoProdutoDao extends CrudDaoDefaultImpl {
	public RequisicaoProdutoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idSolicitacaoServico" ,"idSolicitacaoServico", true, false, false, false));
		listFields.add(new Field("idProjeto" ,"idProjeto", false, false, false, false));
        listFields.add(new Field("idCentroCusto" ,"idCentroCusto", false, false, false, false));
        listFields.add(new Field("finalidade" ,"finalidade", false, false, false, false));
        listFields.add(new Field("idEnderecoEntrega" ,"idEnderecoEntrega", false, false, false, false));
        listFields.add(new Field("rejeitada" ,"rejeitada", false, false, false, false));
        listFields.add(new Field("exigeNovaAprovacao" ,"exigeNovaAprovacao", false, false, false, false));
        listFields.add(new Field("itemAlterado" ,"itemAlterado", false, false, false, false));
        
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "RequisicaoProduto";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return RequisicaoProdutoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	/**
	 * Adicionado parâmetros para adicionar limite na consulta
	 * 
	 * @param seLimita - Se existe limite para listagem
	 * @param limite - Quantidade máxima de elementos da listagem
	 * @return
	 * @author thyen.chang
	 */
    private String getSQLRestoreAll(boolean seLimita, String limite) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)))
			sql.append("TOP "+ limite +" ");
        sql.append("sol.idSolicitacaoServico, sol.idbaseconhecimento, sol.idServicoContrato, sol.idSolicitante, ");
        sql.append("       sol.idItemConfiguracao, sol.idItemConfiguracaoFilho, sol.idtipodemandaservico, sol.idcontatosolicitacaoservico, ");
        sql.append("       sol.idOrigem, sol.idResponsavel, sol.idTipoProblema, sol.idPrioridade, sol.idUnidade, sol.idFaseAtual, ");
        sql.append("       sol.idGrupoAtual, sol.dataHoraSolicitacao, sol.dataHoraLimite, sol.atendimentoPresencial, sol.prazoHH, ");
        sql.append("       sol.prazoMM, sol.descricao, sol.resposta, sol.dataHoraInicio, sol.dataHoraFim, sol.situacao, ");
        sql.append("       sol.idSolicitacaoPai, sol.detalhamentoCausa, sol.idCausaIncidente, sol.idCategoriaSolucao, ");
        sql.append("       sol.seqreabertura, sol.enviaEmailCriacao, sol.enviaEmailFinalizacao, sol.enviaEmailAcoes, ");
        sql.append("       sol.idgruponivel1, sol.solucaoTemporaria, sol.houveMudanca, sol.slaACombinar, sol.prazohhAnterior, ");
        sql.append("       sol.prazommAnterior, sol.idCalendario, sol.tempoDecorridoHH, sol.tempoDecorridoMM, sol.dataHoraSuspensao, ");
        sql.append("       sol.dataHoraReativacao, sol.impacto, sol.urgencia, sol.dataHoraCaptura, sol.tempoCapturaHH, sol.tempoCapturaMM, ");
        sql.append("       sol.tempoAtrasoHH, sol.tempoAtrasoMM, sol.tempoAtendimentoHH, sol.tempoAtendimentoMM, sol.idacordonivelservico, ");
        sql.append("       s.idservico, s.nomeServico, td.idTipoDemandaServico, td.nomeTipoDemandaServico, c.idContrato, c.numero, e1.nome, u1.nome, ");
        sql.append("       e2.nome, u2.nome, oa.descricao, p.nomeprioridade, fs.nomefase,  ");
        sql.append("       g1.sigla, g2.sigla, cs.nomecontato, cs.emailcontato, cs.telefonecontato, cs.localizacaofisica ,cs.idlocalidade, ");
        sql.append("       rp.idCentroCusto, cc.nomeCentroResultado, rp.idProjeto, proj.nomeProjeto, rp.finalidade, rp.idEnderecoEntrega, rp.rejeitada, rp.exigeNovaAprovacao, rp.itemAlterado ");
        sql.append("  FROM requisicaoproduto rp ");
        sql.append("       INNER JOIN solicitacaoservico sol ON sol.idsolicitacaoservico = rp.idsolicitacaoservico ");
        sql.append("       INNER JOIN centroresultado cc ON cc.idcentroresultado = rp.idCentroCusto ");
        sql.append("       INNER JOIN projetos proj ON proj.idprojeto = rp.idProjeto ");
        sql.append("        LEFT JOIN servicocontrato sc ON sc.idservicocontrato = sol.idservicocontrato ");
        sql.append("        LEFT JOIN contratos c ON c.idcontrato = sc.idcontrato ");
        sql.append("        LEFT JOIN servico s ON s.idservico = sc.idservico ");
        sql.append("        LEFT JOIN tipodemandaservico td ON td.idtipodemandaservico = s.idtipodemandaservico ");
        sql.append("        LEFT JOIN empregados e1 ON e1.idempregado = sol.idsolicitante ");
        sql.append("        LEFT JOIN unidade u1 ON u1.idunidade = e1.idunidade ");
        sql.append("        LEFT JOIN usuario usu ON usu.idusuario = sol.idresponsavel ");
        sql.append("        LEFT JOIN empregados e2 ON e2.idempregado = usu.idempregado ");
        sql.append("        LEFT JOIN unidade u2 ON u2.idunidade = e2.idunidade ");
        sql.append("        LEFT JOIN origematendimento oa ON oa.idorigem = sol.idorigem ");
        sql.append("        LEFT JOIN prioridade p ON p.idprioridade = sol.idprioridade ");
        sql.append("        LEFT JOIN faseservico fs ON fs.idfase = sol.idfaseatual ");
        sql.append("        LEFT JOIN grupo g1 ON g1.idgrupo = sol.idgrupoatual ");
        sql.append("        LEFT JOIN grupo g2 ON g2.idgrupo = sol.idgruponivel1 ");
        sql.append("        LEFT JOIN contatosolicitacaoservico cs ON cs.idcontatosolicitacaoservico = sol.idcontatosolicitacaoservico ");
        sql.append(" WHERE ");
        if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE)))
			sql.append("ROWNUM <= " + limite + " AND ");
        sql.append("1 = 1 ");
        return sql.toString();
    }

    private List getColunasRestoreAll() {
        List listRetorno = new ArrayList();
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("idbaseconhecimento");
        listRetorno.add("idServicoContrato");
        listRetorno.add("idSolicitante");
        listRetorno.add("idItemConfiguracao");
        listRetorno.add("idItemConfiguracaoFilho");
        listRetorno.add("idTipodemandaServico");
        listRetorno.add("idContatoSolicitacaoServico");
        listRetorno.add("idOrigem");
        listRetorno.add("idResponsavel");
        listRetorno.add("idTipoProblema");
        listRetorno.add("idPrioridade");
        listRetorno.add("idUnidade");
        listRetorno.add("idFaseAtual");
        listRetorno.add("idGrupoAtual");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("atendimentoPresencial");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("descricao");
        listRetorno.add("resposta");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraFim");
        listRetorno.add("situacao");
        listRetorno.add("idSolicitacaoPai");
        listRetorno.add("detalhamentoCausa");
        listRetorno.add("idCausaIncidente");
        listRetorno.add("idCategoriaSolucao");
        listRetorno.add("seqReabertura");
        listRetorno.add("enviaEmailCriacao");
        listRetorno.add("enviaEmailFinalizacao");
        listRetorno.add("enviaEmailAcoes");
        listRetorno.add("idGrupoNivel1");
        listRetorno.add("solucaoTemporaria");
        listRetorno.add("houveMudanca");
        listRetorno.add("slaACombinar");
        listRetorno.add("prazohhAnterior");
        listRetorno.add("prazommAnterior");
        listRetorno.add("idCalendario");
        listRetorno.add("tempoDecorridoHH");
        listRetorno.add("tempoDecorridoMM");
        listRetorno.add("dataHoraSuspensao");
        listRetorno.add("dataHoraReativacao");
        listRetorno.add("impacto");
        listRetorno.add("urgencia");
        listRetorno.add("dataHoraCaptura");
        listRetorno.add("tempoCapturaHH");
        listRetorno.add("tempoCapturaMM");
        listRetorno.add("tempoAtrasoHH");
        listRetorno.add("tempoAtrasoMM");
        listRetorno.add("tempoAtendimentoHH");
        listRetorno.add("tempoAtendimentoMM");
        listRetorno.add("idAcordoNivelServico");
        listRetorno.add("idServico");
        listRetorno.add("servico");
        listRetorno.add("idTipoDemandaServico");
        listRetorno.add("demanda");
        listRetorno.add("idContrato");
        listRetorno.add("contrato");
        listRetorno.add("solicitante");
        listRetorno.add("nomeUnidadeSolicitante");
        listRetorno.add("responsavel");
        listRetorno.add("nomeUnidadeResponsavel");
        listRetorno.add("origem");
        listRetorno.add("prioridade");
        listRetorno.add("faseAtual");
        listRetorno.add("grupoAtual");
        listRetorno.add("grupoNivel1");
        listRetorno.add("nomecontato");
        listRetorno.add("emailcontato");
        listRetorno.add("telefonecontato");
        listRetorno.add("observacao");
        listRetorno.add("idLocalidade");
        listRetorno.add("idCentroCusto");
        listRetorno.add("centroCusto");
        listRetorno.add("idProjeto");
        listRetorno.add("projeto");
        listRetorno.add("finalidade");
        listRetorno.add("idEnderecoEntrega");
        listRetorno.add("rejeitada");
        listRetorno.add("exigeNovaAprovacao");
        listRetorno.add("itemAlterado");
        return listRetorno;
    }
    
    public Collection<RequisicaoProdutoDTO> consultaRequisicoesPorCCusto(HashMap parametros) throws PersistenceException {
        List parametrosBusca = new ArrayList();
        
        boolean seLimita = !(parametros.get("PARAM.topList").equals("*"));
        
        String idEnderecoEntregaStr = (String) parametros.get("PARAM.idEnderecoEntrega");
        if (idEnderecoEntregaStr == null || idEnderecoEntregaStr.trim().length() == 0)
            idEnderecoEntregaStr = "-1";
        
        String idUnidadeStr = (String) parametros.get("PARAM.idUnidade");
        if (idUnidadeStr == null || idUnidadeStr.trim().length() == 0)
            idUnidadeStr = "-1";
        
        String numeroStr = (String) parametros.get("PARAM.numero");        
        if (numeroStr == null || numeroStr.trim().length() == 0)
            numeroStr = "-1";
        
        String idServicoStr = (String) parametros.get("PARAM.idServico");        
        if (idServicoStr == null || idServicoStr.trim().length() == 0)
            idServicoStr = "-1";

        String situacao = (String) parametros.get("PARAM.situacao");        
        if (situacao == null || situacao.trim().length() == 0)
            situacao = "*";        
        
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));	   
        parametrosBusca.add(situacao);
        parametrosBusca.add(situacao);
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
        parametrosBusca.add(Integer.parseInt(idServicoStr));
		parametrosBusca.add(Integer.parseInt(idServicoStr));	
		parametrosBusca.add(Integer.parseInt(idUnidadeStr));
		parametrosBusca.add(Integer.parseInt(idUnidadeStr));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idCentroCusto")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idCentroCusto")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idProjeto")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idProjeto")));
        parametrosBusca.add(Integer.parseInt(idEnderecoEntregaStr));
        parametrosBusca.add(Integer.parseInt(idEnderecoEntregaStr));
        parametrosBusca.add(Integer.parseInt(numeroStr));
        parametrosBusca.add(Integer.parseInt(numeroStr));
        
        StringBuilder sql = new StringBuilder();
        
        sql.append(getSQLRestoreAll(seLimita, parametros.get("PARAM.topList").toString()));
        sql.append("AND (c.idcontrato = ? OR ? = -1) ")
           .append("AND (sol.situacao = ? OR ? = '*') ")
           .append("AND (sol.idprioridade = ? OR ? = -1) ")
           .append("AND (s.idservico = ? OR ? = -1) ")
           .append("AND (sol.idunidade = ? OR ? = -1) ")
           .append("AND (sol.idorigem = ? OR ? = -1) ")
           .append("AND (rp.idCentroCusto = ? OR ? = -1) ")
           .append("AND (rp.idProjeto = ? OR ? = -1) ")
           .append("AND (rp.idEnderecoEntrega = ? OR ? = -1) ")
           .append("AND (rp.idSolicitacaoServico = ? OR ? = -1) ");
        
        Date dataInicial = null;
        if (parametros.get("PARAM.dataInicial") != null && !"".equals(parametros.get("PARAM.dataInicial"))) {
        	try {
        		dataInicial = UtilDatas.strToSQLDate(parametros.get("PARAM.dataInicial").toString());
        	} catch (LogicException e) {
        	    e.printStackTrace();
        	}
            sql.append("AND (sol.datahorasolicitacao >= ?) ");
            parametrosBusca.add(dataInicial);
        }
        Date dataFinal = null;
        if (parametros.get("PARAM.dataFinal") != null && !"".equals(parametros.get("PARAM.dataFinal"))) {
        	try {
        		dataFinal = UtilDatas.strToSQLDate(parametros.get("PARAM.dataFinal").toString());
        	} catch (LogicException e) {
        	    e.printStackTrace();
        	}
            sql.append("AND (sol.datahorasolicitacao <= ?) ");
            parametrosBusca.add(dataFinal);
        }
        
        sql.append("ORDER BY cc.nomeCentroResultado, sol.datahorasolicitacao ");
        
        //Se o banco for Postgres ou MySQL, limita a consulta
        if((seLimita) && ((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL))) )
        	sql.append("LIMIT " + parametros.get("PARAM.topList").toString() + " ");
        
        List lista = this.execSQL(sql.toString(), parametrosBusca.toArray());

        return this.engine.listConvertion(RequisicaoProdutoDTO.class, lista, getColunasRestoreAll());
    }
    
    public Collection<RequisicaoProdutoDTO> consultaRequisicoesPorUnidade(HashMap parametros) throws PersistenceException {
        List parametrosBusca = new ArrayList();
        
        parametrosBusca.add(parametros.get("PARAM.dataInicial"));
        parametrosBusca.add(parametros.get("PARAM.dataFinal"));
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));	    
        parametrosBusca.add(parametros.get("PARAM.situacao"));
        parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
        parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));	
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idCentroCusto")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idCentroCusto")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idProjeto")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idProjeto")));
        
        StringBuilder sql = new StringBuilder();
        
        sql.append(getSQLRestoreAll(false, ""));
        sql.append("AND (sol.datahorasolicitacao BETWEEN ? AND ?) ")
           .append("AND (c.idcontrato = ? OR ? = -1) ")
           .append("AND (sol.situacao = ? OR ? = '*') ")
           .append("AND (sol.idprioridade = ? OR ? = -1) ")
           .append("AND (s.idservico = ? OR ? = -1) ")
           .append("AND (sol.idunidade = ? OR ? = -1) ")
           .append("AND (sol.idorigem = ? OR ? = -1) ")
           .append("AND (rp.idCentroCusto = ? OR ? = -1) ")
           .append("AND (rp.idProjeto = ? OR ? = -1) ")
           .append("ORDER BY u1.nome, sol.datahorasolicitacao ");
        
        
        List lista = this.execSQL(sql.toString(), parametrosBusca.toArray());

        return this.engine.listConvertion(RequisicaoProdutoDTO.class, lista, getColunasRestoreAll());
    }
    
    @Override
    public IDto restore(IDto obj) throws PersistenceException {
        RequisicaoProdutoDTO requisicaoDto = (RequisicaoProdutoDTO) obj;
        List parametro = new ArrayList();
        parametro.add(requisicaoDto.getIdSolicitacaoServico());

        String sql = getSQLRestoreAll(false, "");
        sql += "  AND rp.idsolicitacaoservico = ? ";

        List lista = this.execSQL(sql.toString(), parametro.toArray());

        if (lista != null && !lista.isEmpty()) {
            List listaResult = this.engine.listConvertion(RequisicaoProdutoDTO.class, lista, getColunasRestoreAll());
            return (RequisicaoProdutoDTO) listaResult.get(0);
        } else {
            return null;
        }
    }
    
    public Collection findByIdCentroCusto(Integer idCentroCusto) throws PersistenceException {
        List parametro = new ArrayList();
        parametro.add(idCentroCusto);

        String sql = getSQLRestoreAll(false, "");
        sql += "  AND rp.idCentroCusto = ? ";

        List lista = this.execSQL(sql.toString(), parametro.toArray());
        return this.engine.listConvertion(RequisicaoProdutoDTO.class, lista, getColunasRestoreAll());
    }

    public Collection consultaRequisicoesEmAndamento() throws PersistenceException {
        List parametro = new ArrayList();
        parametro.add(SituacaoSolicitacaoServico.EmAndamento.name());
        parametro.add(SituacaoSolicitacaoServico.Reaberta.name());
        parametro.add(SituacaoSolicitacaoServico.Resolvida.name());

        String sql = getSQLRestoreAll(false, "");
        sql += "  AND (sol.situacao = ? OR sol.situacao = ? OR sol.situacao = ?)";

        List lista = this.execSQL(sql.toString(), parametro.toArray());
        return this.engine.listConvertion(RequisicaoProdutoDTO.class, lista, getColunasRestoreAll());
}
}
