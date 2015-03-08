package br.com.centralit.citcorpore.integracao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.centralit.citcorpore.bean.GerenciamentoRotasDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.result.GerenciamentoRotasResultDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.core.DataBase;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.PageRequest;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.integracao.core.PagingQueryUtil;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.geo.GeoLocation;
import br.com.citframework.util.geo.GeoUtils;

/**
 * DAO para as consultas de {@link SolicitacaoServicoDTO} realizadas pelo mobile V2
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @author maycon.fernandes - <a href="mailto:maycon.fernandes@centrait.com.br">maycon.fernandes@centrait.com.br</a>
 * @since 09/10/2014
 *
 */
public class SolicitacaoServicoForMobileV2Dao extends SolicitacaoServicoDao {

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";

    private static final int DEFAULT_PAGE = 0;
    private static final String WHERE_ID_SOLICITACAO = " WHERE sol.idsolicitacaoservico ";
    private static final String ORDER_BY_ID_SOLICITACAO = " ORDER BY sol.idsolicitacaoservico DESC, atrSol.dataexecucao, atrSol.priorityOrder ";

    public Page<SolicitacaoServicoDTO> listNewest(final Integer newestNumber, final UsuarioDTO usuario, final TipoSolicitacaoServico[] tiposSolicitacao, final String aprovacao)
            throws PersistenceException {
        final List<Object> parametros = new ArrayList<>();
        final Pageable pageable = this.getDefaultPageable();

        final StringBuilder from = this.fromQueryPiece(usuario, parametros);
        final StringBuilder sql = this.montarSQLSolicitacao(from);
        final StringBuilder where = new StringBuilder(WHERE_ID_SOLICITACAO);
        where.append(" > ? ");

        sql.append(where);
        parametros.add(newestNumber);

        final String filtrarPorTipoAndAprovacao = this.fitrarPorTipoAndAprovacao(parametros, tiposSolicitacao, aprovacao);
        sql.append(filtrarPorTipoAndAprovacao);
        sql.append(ORDER_BY_ID_SOLICITACAO);

        final Object[] parametrosArray = parametros.toArray();

        final List<SolicitacaoServicoDTO> result = this.executeQuery(sql.toString(), pageable, parametrosArray, this.getListNamesFieldDB(), this.getBean());

        this.setTipoSoliticacaoOnResult(result);

        return this.makePage(result, pageable, 0L);
    }

    public Page<SolicitacaoServicoDTO> listOldest(final Integer oldestNumber, final UsuarioDTO usuario, final TipoSolicitacaoServico[] tiposSolicitacao, final String aprovacao)
            throws PersistenceException {
        final Pageable pageable = this.getDefaultPageable();
        final List<Object> parametros = new ArrayList<>();

        final StringBuilder from = this.fromQueryPiece(usuario, parametros);
        final StringBuilder sql = this.montarSQLSolicitacao(from);
        final StringBuilder where = new StringBuilder(WHERE_ID_SOLICITACAO);

        where.append(" < ? ");

        sql.append(where);
        parametros.add(oldestNumber);

        final String filtrarPorTipoAndAprovacao = this.fitrarPorTipoAndAprovacao(parametros, tiposSolicitacao, aprovacao);
        sql.append(filtrarPorTipoAndAprovacao);
        sql.append(ORDER_BY_ID_SOLICITACAO);

        final Object[] parametrosArray = parametros.toArray();

        final List<SolicitacaoServicoDTO> result = this.executeQuery(sql.toString(), pageable, parametrosArray, this.getListNamesFieldDB(), this.getBean());

        this.setTipoSoliticacaoOnResult(result);

        return this.makePage(result, pageable, 0L);
    }

    public Page<SolicitacaoServicoDTO> listByCoordinates(final GeoLocation referencePoint, final GeoLocation[] bounds, final double distanceRadius,
            final boolean meridian180WithinDistance, final UsuarioDTO usuario, final TipoSolicitacaoServico[] tiposSolicitacao, final String aprovacao, final Pageable pageable)
            throws PersistenceException {
        final List<Object> parametros = new ArrayList<>();

        final StringBuilder from = this.fromQueryPiece(usuario, parametros);
        final StringBuilder sql = this.montarSQLSolicitacao(from);
        final StringBuilder where = new StringBuilder(" WHERE ");

        switch (MAIN_SGBD) {
        case ORACLE:
        case POSTGRESQL:
            where.append(GeoUtils.getSQLWherePieceForDistanceWithIndex("ender.latitude", "ender.longitude", meridian180WithinDistance));
            break;
        default:
            where.append(GeoUtils.getSQLWherePieceForDistanceWithIndexForMySQLAndMSSQLServer("ender.latitude_radians", "ender.longitude_radians", meridian180WithinDistance));
            break;
        }

        sql.append(where);
        parametros.add(bounds[0].getLatitudeInRadians());
        parametros.add(bounds[1].getLatitudeInRadians());
        parametros.add(bounds[0].getLongitudeInRadians());
        parametros.add(bounds[1].getLongitudeInRadians());
        parametros.add(referencePoint.getLatitudeInRadians());
        parametros.add(referencePoint.getLatitudeInRadians());
        parametros.add(referencePoint.getLongitudeInRadians());
        parametros.add(distanceRadius);

        final String filtrarPorTipoAndAprovacao = this.fitrarPorTipoAndAprovacao(parametros, tiposSolicitacao, aprovacao);
        sql.append(filtrarPorTipoAndAprovacao);
        sql.append(ORDER_BY_ID_SOLICITACAO);

        final Object[] parametrosArray = parametros.toArray();

        final List<SolicitacaoServicoDTO> result = this.executeQuery(sql.toString(), pageable, parametrosArray, this.getListNamesFieldDB(), this.getBean());

        this.setTipoSoliticacaoOnResult(result);

        final StringBuilder sqlCount = this.countQueryPiece(from);
        sqlCount.append(where);
        sqlCount.append(filtrarPorTipoAndAprovacao);

        final Long totalElements = this.countElements(sqlCount.toString(), parametrosArray);
        return this.makePage(result, pageable, totalElements);
    }

    public Page<SolicitacaoServicoDTO> listNotificationByNumberAndUser(final Integer number, final UsuarioDTO user) throws PersistenceException {
        final Pageable pageable = this.getDefaultPageable();
        final List<Object> parametros = new ArrayList<>();

        final StringBuilder from = this.fromQueryPiece(user, parametros);
        final StringBuilder sql = this.montarSQLSolicitacao(from);
        final StringBuilder where = new StringBuilder(WHERE_ID_SOLICITACAO);

        where.append(" = ? ");

        sql.append(where);
        parametros.add(number);

        final Object[] parametrosArray = parametros.toArray();

        final List<SolicitacaoServicoDTO> result = this.executeQuery(sql.toString(), pageable, parametrosArray, this.getListNamesFieldDB(), this.getBean());

        return this.makePage(result, pageable, 0L);
    }

    private void setTipoSoliticacaoOnResult(final List<SolicitacaoServicoDTO> result) {
        if (result != null) {
            for (final SolicitacaoServicoDTO solicitacao : result) {
                if (solicitacao.getIdRequisicaoProduto() != null) {
                    solicitacao.setTipoSolicitacao(TipoSolicitacaoServico.COMPRA);
                } else if (solicitacao.getIdRequisicaoViagem() != null) {
                    solicitacao.setTipoSolicitacao(TipoSolicitacaoServico.VIAGEM);
                } else if (solicitacao.getIdRequisicaoPessoal() != null) {
                    solicitacao.setTipoSolicitacao(TipoSolicitacaoServico.RH);
                } else if (solicitacao.getClassificacao() != null && solicitacao.getClassificacao().equalsIgnoreCase("R")) {
                    solicitacao.setTipoSolicitacao(TipoSolicitacaoServico.REQUISICAO);
                } else {
                    solicitacao.setTipoSolicitacao(TipoSolicitacaoServico.INCIDENTE);
                }
            }
        }
    }

    public Page<GerenciamentoRotasResultDTO> listarSolicitacoesParaRoteirizacao(final GerenciamentoRotasDTO filter, final Pageable pageable) throws PersistenceException {
        final List<Object> params = new ArrayList<>();

        final StringBuilder sql = new StringBuilder();
        sql.append("SELECT atrSol.id                        AS idatribuicao, ");
        sql.append("       atrSol.datainicioatendimento, ");
        sql.append("       atrSol.priorityOrder, ");
        sql.append("       sol.idsolicitacaoservico, ");
        sql.append("       bpmItem.iditemtrabalho           AS idtarefa, ");
        sql.append("       contr.numero                     AS nomeContrato, ");
        sql.append("       sol.prazohh, ");
        sql.append("       sol.prazomm, ");
        sql.append("       tipoDeman.nometipodemandaservico AS tipo, ");
        sql.append("       sol.situacao, ");
        sql.append("       und.nome                         AS nomeunidade, ");
        sql.append("       ender.latitude, ");
        sql.append("       ender.longitude, ");

        if (MAIN_SGBD.equals(DataBase.ORACLE)) {
            sql.append("       dbms_lob.SUBSTR");
        } else {
            sql.append("       SUBSTRING");
        }

        sql.append("(sol.descricao, 1, 100)          AS descricao ");

        final StringBuilder from = new StringBuilder();
        from.append("FROM   bpm_atribuicaofluxo bpmAtr ");
        from.append("       INNER JOIN bpm_itemtrabalhofluxo bpmItem ");
        from.append("               ON bpmAtr.iditemtrabalho = bpmItem.iditemtrabalho ");
        from.append("       INNER JOIN bpm_instanciafluxo bpmIstancia ");
        from.append("               ON bpmItem.idinstancia = bpmIstancia.idinstancia ");
        from.append("       INNER JOIN bpm_elementofluxo bpm_ele ");
        from.append("               ON bpmItem.idelemento = bpm_ele.idelemento ");
        from.append("       LEFT JOIN templatesolicitacaoservico tem ");
        from.append("              ON bpm_ele.template = tem.identificacao ");
        from.append("       LEFT JOIN execucaosolicitacao exeSol ");
        from.append("              ON bpmIstancia.idinstancia = exeSol.idinstanciafluxo ");
        from.append("       LEFT JOIN solicitacaoservico sol ");
        from.append("              ON exeSol.idsolicitacaoservico = sol.idsolicitacaoservico ");
        from.append("       LEFT JOIN servicocontrato servContrato ");
        from.append("              ON servContrato.idservicocontrato = sol.idservicocontrato ");
        from.append("       LEFT JOIN contratos contr ");
        from.append("              ON contr.idcontrato = servContrato.idcontrato ");
        from.append("       LEFT JOIN servico serv ");
        from.append("              ON serv.idservico = servContrato.idservico ");
        from.append("       LEFT JOIN tipodemandaservico tipoDeman ");
        from.append("              ON tipoDeman.idtipodemandaservico = serv.idtipodemandaservico ");
        from.append("       LEFT JOIN unidade und ");
        from.append("              ON sol.idunidade = und.idunidade ");
        from.append("       LEFT JOIN endereco ender ");
        from.append("              ON und.idendereco = ender.idendereco ");
        from.append("       LEFT JOIN ufs ");
        from.append("              ON ender.iduf = ufs.iduf ");
        from.append("       LEFT JOIN cidades cid ");
        from.append("              ON ender.idcidade = cid.idcidade ");
        from.append("       LEFT JOIN requisicaoproduto reqprod ");
        from.append("              ON reqprod.idsolicitacaoservico = sol.idsolicitacaoservico ");
        from.append("       LEFT JOIN rh_requisicaopessoal reqrh ");
        from.append("              ON reqrh.idsolicitacaoservico = sol.idsolicitacaoservico ");
        from.append("       LEFT JOIN requisicaoviagem reqviagem ");
        from.append("              ON reqviagem.idsolicitacaoservico = sol.idsolicitacaoservico ");
        from.append("       LEFT JOIN atribuicaosolicitacao atrSol ");
        from.append("              ON sol.idsolicitacaoservico = atrSol.idsolicitacao AND atrSol.active = 1 ");
        from.append("WHERE  bpmItem.situacao NOT IN ( 'Executado', 'Cancelado' ) ");
        from.append("       AND ( bpmAtr.idusuario = ? ");
        from.append("              OR bpmAtr.idgrupo IN (SELECT gr.idgrupo ");
        from.append("                                    FROM   grupo gr ");
        from.append("                                           INNER JOIN gruposempregados gremp ");
        from.append("                                                   ON gr.idgrupo = gremp.idgrupo ");
        from.append("                                           INNER JOIN empregados emp ");
        from.append("                                                   ON emp.idempregado = ");
        from.append("                                                      gremp.idempregado ");
        from.append("                                           INNER JOIN usuario usu ");
        from.append("                                                   ON usu.idempregado = ");
        from.append("                                                      emp.idempregado ");
        from.append("                                    WHERE  usu.idusuario = ?) ");
        from.append("              OR bpmItem.idresponsavelatual = ? ) ");
        from.append("       AND ( atrSol.id IN (SELECT MAX(id) ");
        from.append("                           FROM   atribuicaosolicitacao ");
        from.append("                           GROUP  BY idsolicitacao) ");
        from.append("              OR atrSol.id IS NULL ) ");
        from.append("       AND bpmAtr.tipo = 'Automatica' ");

        final Integer idUsuario = filter.getIdUsuario();
        params.add(idUsuario);
        params.add(idUsuario);
        params.add(idUsuario);

        final Timestamp dataTimeInicio = filter.getTimestampInicio();
        final Timestamp dataTimeFim = filter.getTimestampFim();

        if (MAIN_SGBD.equals(DataBase.ORACLE)) {
            from.append("       AND TO_CHAR(sol.datahorasolicitacao, 'YYYY-MM-DD HH24:MI:SS') BETWEEN ? AND ? ");
            params.add(UtilDatas.dateToSTRWithFormat(dataTimeInicio, YYYY_MM_DD_HH_MM_SS));
            params.add(UtilDatas.dateToSTRWithFormat(dataTimeFim, YYYY_MM_DD_HH_MM_SS));
        } else {
            from.append("       AND sol.datahorasolicitacao BETWEEN ? AND ? ");
            params.add(dataTimeInicio);
            params.add(dataTimeFim);
        }

        from.append("       AND cid.idcidade = ? ");

        params.add(filter.getIdCidade());

        final Integer contrato = filter.getIdContrato();
        if (contrato != null && !contrato.equals(0)) {
            from.append("       AND contr.idcontrato = ? ");
            params.add(contrato);
        }

        final Integer unidade = filter.getIdUnidade();
        if (unidade != null && !unidade.equals(0)) {
            from.append("       AND und.idunidade = ? ");
            params.add(unidade);
        }

        final Integer tipo = filter.getIdTipoSolicitacao();
        if (tipo != null && !tipo.equals(0)) {
            from.append("       AND tipoDeman.idtipodemandaservico = ? ");
            params.add(tipo);
        }

        sql.append(from);
        sql.append("ORDER  BY sol.idsolicitacaoservico, atrSol.dataexecucao, atrSol.priorityOrder ");

        final Object[] paramsArray = params.toArray();
        final List<GerenciamentoRotasResultDTO> result = this.executeQuery(sql.toString(), pageable, paramsArray, this.getFieldsRoteirizacao(), GerenciamentoRotasResultDTO.class);

        final StringBuilder sqlCount = this.countQueryPiece(from);

        final Long totalElements = this.countElements(sqlCount.toString(), paramsArray);
        return this.makePage(result, pageable, totalElements);
    }

    private List<String> listNamesFieldDB;

    @Override
    public List<String> getListNamesFieldDB() {
        if (listNamesFieldDB == null) {
            listNamesFieldDB = new ArrayList<>();
            listNamesFieldDB.add("idSolicitacaoIndividual");
            listNamesFieldDB.add("dataInicioAtendimento");
            listNamesFieldDB.add("idSolicitacaoServico");
            listNamesFieldDB.add("situacao");
            listNamesFieldDB.add("dataHoraLimite");
            listNamesFieldDB.add("prazoHH");
            listNamesFieldDB.add("prazoMM");
            listNamesFieldDB.add("dataHoraSolicitacao");
            listNamesFieldDB.add("prazoCapturaHH");
            listNamesFieldDB.add("prazoCapturaMM");
            listNamesFieldDB.add("dataHoraInicio");
            listNamesFieldDB.add("dataHoraFim");
            listNamesFieldDB.add("slaACombinar");
            listNamesFieldDB.add("prazohhAnterior");
            listNamesFieldDB.add("prazommAnterior");
            listNamesFieldDB.add("idCalendario");
            listNamesFieldDB.add("tempoDecorridoHH");
            listNamesFieldDB.add("tempoDecorridoMM");
            listNamesFieldDB.add("dataHoraSuspensao");
            listNamesFieldDB.add("dataHoraReativacao");
            listNamesFieldDB.add("dataHoraCaptura");
            listNamesFieldDB.add("tempoCapturaHH");
            listNamesFieldDB.add("tempoCapturaMM");
            listNamesFieldDB.add("tempoAtrasoHH");
            listNamesFieldDB.add("tempoAtrasoMM");
            listNamesFieldDB.add("tempoAtendimentoHH");
            listNamesFieldDB.add("tempoAtendimentoMM");
            listNamesFieldDB.add("dataHoraInicioSLA");
            listNamesFieldDB.add("situacaoSLA");
            listNamesFieldDB.add("dataHoraSuspensaoSLA");
            listNamesFieldDB.add("dataHoraReativacaoSLA");
            listNamesFieldDB.add("latitude");
            listNamesFieldDB.add("longitude");
            listNamesFieldDB.add("idTarefa");
            listNamesFieldDB.add("nomeElementoFluxo");
            listNamesFieldDB.add("servico");
            listNamesFieldDB.add("aprovacao");
            listNamesFieldDB.add("tipoAtribuicao");
            listNamesFieldDB.add("idResponsavel");
            listNamesFieldDB.add("idContrato");
            listNamesFieldDB.add("idUnidade");
            listNamesFieldDB.add("priorityorder");
            listNamesFieldDB.add("identificacaoTemplate");
            listNamesFieldDB.add("idFluxo");
            listNamesFieldDB.add("idRequisicaoProduto");
            listNamesFieldDB.add("idRequisicaoViagem");
            listNamesFieldDB.add("idRequisicaoPessoal");
            listNamesFieldDB.add("classificacao");
        }
        return listNamesFieldDB;
    }

    private List<String> fieldsRoteirizacao;

    private List<String> getFieldsRoteirizacao() {
        if (fieldsRoteirizacao == null) {
            fieldsRoteirizacao = new ArrayList<>();
            fieldsRoteirizacao.add("idAtribuicao");
            fieldsRoteirizacao.add("dataInicioAtendimento");
            fieldsRoteirizacao.add("priorityOrder");
            fieldsRoteirizacao.add("idSolicitacao");
            fieldsRoteirizacao.add("idTarefa");
            fieldsRoteirizacao.add("nomeContrato");
            fieldsRoteirizacao.add("prazoHH");
            fieldsRoteirizacao.add("prazoMM");
            fieldsRoteirizacao.add("tipo");
            fieldsRoteirizacao.add("situacao");
            fieldsRoteirizacao.add("nomeUnidade");
            fieldsRoteirizacao.add("latitude");
            fieldsRoteirizacao.add("longitude");
            fieldsRoteirizacao.add("descricao");
        }
        return fieldsRoteirizacao;
    }

    public StringBuilder montarSQLSolicitacao(final StringBuilder sqlFrom) {
        final StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("        atrSol.idsolicitacao, ");
        sql.append("        atrSol.datainicioatendimento, ");
        sql.append("        sol.idsolicitacaoservico, ");
        sql.append("        sol.situacao, ");
        sql.append("        sol.datahoralimite, ");
        sql.append("        sol.prazohh, ");
        sql.append("        sol.prazomm, ");
        sql.append("        sol.datahorasolicitacao, ");
        sql.append("        sol.prazocapturahh, ");
        sql.append("        sol.prazocapturamm, ");
        sql.append("        sol.datahorainicio, ");
        sql.append("        sol.datahorafim, ");
        sql.append("        sol.slaacombinar, ");
        sql.append("        sol.prazohhanterior, ");
        sql.append("        sol.prazommanterior, ");
        sql.append("        sol.idcalendario, ");
        sql.append("        sol.tempodecorridohh, ");
        sql.append("        sol.tempodecorridomm, ");
        sql.append("        sol.datahorasuspensao, ");
        sql.append("        sol.datahorareativacao, ");
        sql.append("        sol.datahoracaptura, ");
        sql.append("        sol.tempocapturahh, ");
        sql.append("        sol.tempocapturamm, ");
        sql.append("        sol.tempoatrasohh, ");
        sql.append("        sol.tempoatrasomm, ");
        sql.append("        sol.tempoatendimentohh, ");
        sql.append("        sol.tempoatendimentomm, ");
        sql.append("        sol.datahorainiciosla, ");
        sql.append("        sol.situacaosla, ");
        sql.append("        sol.datahorasuspensaosla, ");
        sql.append("        sol.datahorareativacaosla, ");
        sql.append("        ender.latitude, ");
        sql.append("        ender.longitude, ");
        sql.append("        tarefa.idtarefa, ");
        sql.append("        tarefa.nomeElemento, ");
        sql.append("        s.nomeservico, ");
        sql.append("        tarefa.aprovar, ");
        sql.append("        tarefa.tipoAtribuicao, ");
        sql.append("        tarefa.idresponsavelatual, ");
        sql.append("        c.idcontrato, ");
        sql.append("        unidade.idunidade, ");
        sql.append("        atrSol.priorityorder, ");
        sql.append("        tarefa.identificacao, ");
        sql.append("        tarefa.idfluxo, ");
        sql.append("        reqprod.idSolicitacaoServico as idrequisicaoproduto, ");
        sql.append("        reqviagem.idSolicitacaoServico as idrequisicaoviagem, ");
        sql.append("        reqrh.idSolicitacaoServico as idrequisicaopessoal, ");
        sql.append("        td.classificacao ");
        sql.append(sqlFrom);
        return sql;
    }

    private StringBuilder fromQueryPiece(final UsuarioDTO usuario, final List<Object> parametros) {
        final StringBuilder sqlFrom = new StringBuilder();
        sqlFrom.append("    from ( ");
        sqlFrom.append("            SELECT DISTINCT i.iditemtrabalho idtarefa, ");
        sqlFrom.append("                    bpm_ele.nome nomeElemento, ");
        sqlFrom.append("                    tem.aprovacao aprovar, ");
        sqlFrom.append("                    instancia.idinstancia, ");
        sqlFrom.append("                    i.idresponsavelatual, ");
        sqlFrom.append("                    a.tipo tipoAtribuicao, ");
        sqlFrom.append("                    tem.identificacao,  ");
        sqlFrom.append("                    instancia.idfluxo  ");
        sqlFrom.append("            FROM   bpm_atribuicaofluxo a ");
        sqlFrom.append("                   INNER JOIN bpm_itemtrabalhofluxo i  ON a.iditemtrabalho = i.iditemtrabalho ");
        sqlFrom.append("                   INNER JOIN bpm_instanciafluxo instancia ON i.idinstancia = instancia.idinstancia ");
        sqlFrom.append("                   INNER JOIN bpm_elementofluxo bpm_ele ON i.idelemento = bpm_ele.idelemento ");
        sqlFrom.append("                   LEFT JOIN templatesolicitacaoservico tem ON bpm_ele.template = tem.identificacao ");
        sqlFrom.append("            WHERE  i.situacao NOT IN ( ?, ? ) ");

        parametros.add(Enumerados.SituacaoItemTrabalho.Executado.name());
        parametros.add(Enumerados.SituacaoItemTrabalho.Cancelado.name());

        sqlFrom.append("                   AND ( a.idusuario = ? OR a.idgrupo IN (SELECT gr.idgrupo ");

        parametros.add(usuario.getIdUsuario());

        sqlFrom.append("                               FROM   grupo gr ");
        sqlFrom.append("                                  INNER JOIN gruposempregados gremp  ON gr.idgrupo = gremp.idgrupo ");
        sqlFrom.append("                                  INNER JOIN empregados emp  ON emp.idempregado = gremp.idempregado ");
        sqlFrom.append("                                  INNER JOIN usuario usu ON usu.idempregado = emp.idempregado  WHERE  usu.idusuario = ?) ");

        parametros.add(usuario.getIdUsuario());

        sqlFrom.append("                      OR i.idresponsavelatual = ? ) ");

        parametros.add(usuario.getIdUsuario());

        sqlFrom.append("                   AND a.tipo = ? ");

        parametros.add(TipoAtribuicao.Automatica.toString());

        sqlFrom.append("        UNION ALL ");
        sqlFrom.append("            SELECT DISTINCT ");
        sqlFrom.append("                    i.iditemtrabalho idtarefa, ");
        sqlFrom.append("                    bpm_ele.nome nomeElemento, ");
        sqlFrom.append("                    tem.aprovacao aprovar, ");
        sqlFrom.append("                    instancia.idinstancia, ");
        sqlFrom.append("                    i.idresponsavelatual, ");
        sqlFrom.append("                    a.tipo tipoAtribuicao, ");
        sqlFrom.append("                    tem.identificacao,  ");
        sqlFrom.append("                    instancia.idfluxo  ");
        sqlFrom.append("            FROM   bpm_atribuicaofluxo a ");
        sqlFrom.append("                   INNER JOIN bpm_itemtrabalhofluxo i ON a.iditemtrabalho = i.iditemtrabalho ");
        sqlFrom.append("                   INNER JOIN bpm_instanciafluxo instancia  ON i.idinstancia = instancia.idinstancia ");
        sqlFrom.append("                   INNER JOIN bpm_elementofluxo bpm_ele  ON i.idelemento = bpm_ele.idelemento ");
        sqlFrom.append("                   LEFT JOIN templatesolicitacaoservico tem ON bpm_ele.template = tem.identificacao ");
        sqlFrom.append("            WHERE  i.situacao NOT IN ( ?, ? ) ");

        parametros.add(Enumerados.SituacaoItemTrabalho.Executado.name());
        parametros.add(Enumerados.SituacaoItemTrabalho.Cancelado.name());

        sqlFrom.append("                   AND ( a.idusuario = ? ");

        parametros.add(usuario.getIdUsuario());

        sqlFrom.append("                      OR a.idgrupo IN (SELECT gr.idgrupo ");
        sqlFrom.append("                               FROM   grupo gr ");
        sqlFrom.append("                                  INNER JOIN gruposempregados gremp  ON gr.idgrupo = gremp.idgrupo ");
        sqlFrom.append("                                  INNER JOIN empregados emp  ON emp.idempregado = gremp.idempregado ");
        sqlFrom.append("                                  INNER JOIN usuario usu ON usu.idempregado = emp.idempregado  WHERE  usu.idusuario = ?) ");

        parametros.add(usuario.getIdUsuario());

        sqlFrom.append("                      OR i.idresponsavelatual = ? ) ");

        parametros.add(usuario.getIdUsuario());

        sqlFrom.append("                   AND a.tipo = ? ");

        parametros.add(TipoAtribuicao.Acompanhamento.toString());

        sqlFrom.append("                   AND a.iditemtrabalho NOT IN (SELECT a.iditemtrabalho ");
        sqlFrom.append("                                FROM   bpm_atribuicaofluxo a ");
        sqlFrom.append("                                   INNER JOIN bpm_itemtrabalhofluxo i ON a.iditemtrabalho = i.iditemtrabalho ");
        sqlFrom.append("                                WHERE  i.situacao NOT IN (? , ? ) ");

        parametros.add(Enumerados.SituacaoItemTrabalho.Executado.name());
        parametros.add(Enumerados.SituacaoItemTrabalho.Cancelado.name());

        sqlFrom.append("                                   AND ( a.idusuario = ?  OR a.idgrupo IN (SELECT gr.idgrupo  FROM   grupo gr ");

        parametros.add(usuario.getIdUsuario());

        sqlFrom.append("                                                  INNER JOIN gruposempregados gremp  ON gr.idgrupo = gremp.idgrupo ");
        sqlFrom.append("                                                  INNER JOIN empregados emp ON emp.idempregado = gremp.idempregado ");
        sqlFrom.append("                                                  INNER JOIN usuario usu ON usu.idempregado = emp.idempregado WHERE  usu.idusuario = ?) ");

        parametros.add(usuario.getIdUsuario());

        sqlFrom.append("                                      OR i.idresponsavelatual = ? ) ");

        parametros.add(usuario.getIdUsuario());

        sqlFrom.append("             AND a.tipo = ? ) ) tarefa ");

        parametros.add(TipoAtribuicao.Automatica.toString());

        sqlFrom.append("       INNER JOIN execucaosolicitacao exsol ON tarefa.idinstancia = exsol.idinstanciafluxo ");
        sqlFrom.append("       INNER JOIN solicitacaoservico sol ON sol.idsolicitacaoservico = exsol.idsolicitacaoservico ");
        sqlFrom.append("       LEFT JOIN (SELECT usu.idusuario, ");
        sqlFrom.append("                         emp.nome nomeempregado ");
        sqlFrom.append("                  FROM   empregados emp INNER JOIN usuario usu ON usu.idempregado = emp.idempregado) empUsu ON tarefa.idresponsavelatual = empUsu.idusuario ");
        sqlFrom.append("       LEFT JOIN servicocontrato sc  ON sc.idservicocontrato = sol.idservicocontrato ");
        sqlFrom.append("       LEFT JOIN contratos c  ON c.idcontrato = sc.idcontrato ");
        sqlFrom.append("       LEFT JOIN servico s  ON s.idservico = sc.idservico ");
        sqlFrom.append("       LEFT JOIN tipodemandaservico td  ON td.idtipodemandaservico = s.idtipodemandaservico ");
        sqlFrom.append("       LEFT JOIN unidade unidade  ON unidade.idunidade = sol.idunidade ");
        sqlFrom.append("       LEFT JOIN usuario usu    ON usu.idusuario = sol.idresponsavel ");
        sqlFrom.append("       LEFT JOIN endereco ender on unidade.idendereco = ender.idendereco ");
        sqlFrom.append("       LEFT JOIN grupo g1  ON g1.idgrupo = sol.idgrupoatual ");
        sqlFrom.append("       LEFT JOIN grupo g2  ON g2.idgrupo = sol.idgruponivel1 ");
        sqlFrom.append("       LEFT JOIN contatosolicitacaoservico cs   ON cs.idcontatosolicitacaoservico = sol.idcontatosolicitacaoservico ");
        sqlFrom.append("       LEFT JOIN aprovacaosolicitacaoservico aprov  ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");
        sqlFrom.append("       LEFT JOIN requisicaoproduto reqprod   ON reqprod.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sqlFrom.append("       LEFT JOIN rh_requisicaopessoal reqrh ON reqrh.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sqlFrom.append("       LEFT JOIN requisicaoviagem reqviagem  ON reqviagem.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sqlFrom.append("       LEFT JOIN atribuicaosolicitacao atrSol on sol.idsolicitacaoservico = atrSol.idsolicitacao AND atrSol.idusuario = ? AND atrSol.active = 1 ");

        parametros.add(usuario.getIdUsuario());

        return sqlFrom;
    }

    private String fitrarPorTipoAndAprovacao(final List<Object> parametros, final TipoSolicitacaoServico[] tiposSolicitacao, final String aprovacao) {
        final StringBuilder sql = new StringBuilder();
        boolean bIncidentes = false;
        boolean bRequisicoes = false;
        boolean bCompras = false;
        boolean bViagens = false;
        boolean bRH = false;

        if (aprovacao != null && aprovacao.trim().equalsIgnoreCase("S")) {
            sql.append(" AND tarefa.aprovar = ? ");
            parametros.add("S");
        }

        if (tiposSolicitacao != null) {
            for (final TipoSolicitacaoServico tipo : tiposSolicitacao) {
                if (!bIncidentes && tipo.equals(TipoSolicitacaoServico.INCIDENTE)) {
                    bIncidentes = true;
                }
                if (!bRequisicoes && tipo.equals(TipoSolicitacaoServico.REQUISICAO)) {
                    bRequisicoes = true;
                }
                if (!bCompras && tipo.equals(TipoSolicitacaoServico.COMPRA)) {
                    bCompras = true;
                }
                if (!bViagens && tipo.equals(TipoSolicitacaoServico.VIAGEM)) {
                    bViagens = true;
                }
                if (!bRH && tipo.equals(TipoSolicitacaoServico.RH)) {
                    bRH = true;
                }
            }
        }

        boolean bFiltrouTipos = false;
        if (bIncidentes) {
            sql.append(" AND (td.classificacao = ? ");
            parametros.add("I");

            bFiltrouTipos = true;
        }

        if (bRequisicoes) {
            if (bFiltrouTipos) {
                sql.append(" OR ");
            } else {
                sql.append(" AND (");
            }
            sql.append(" reqprod.idsolicitacaoservico IS NULL AND reqviagem.idsolicitacaoservico IS NULL AND reqrh.idsolicitacaoservico IS NULL AND td.classificacao = ?  ");

            parametros.add("R");

            bFiltrouTipos = true;
        }

        if (bCompras) {
            if (bFiltrouTipos) {
                sql.append(" OR ");
            } else {
                sql.append(" AND (");
            }
            sql.append(" reqprod.idsolicitacaoservico IS NOT NULL ");
            bFiltrouTipos = true;
        }

        if (bViagens) {
            if (bFiltrouTipos) {
                sql.append(" OR ");
            } else {
                sql.append(" AND (");
            }
            sql.append(" reqviagem.idsolicitacaoservico IS NOT NULL ");
            bFiltrouTipos = true;
        }

        if (bRH) {
            if (bFiltrouTipos) {
                sql.append(" OR ");
            } else {
                sql.append(" AND (");
            }
            sql.append(" reqrh.idsolicitacaoservico IS NOT NULL ");
            bFiltrouTipos = true;
        }

        if (bFiltrouTipos) {
            sql.append(") ");
        }

        return sql.toString();
    }

    private <E> List<E> executeQuery(final String query, final Pageable pageable, final Object[] params, final List<String> fields, final Class<E> beanClass)
            throws PersistenceException {
        final String sqlForPaging = PagingQueryUtil.concatPagingPieceOnQuery(pageable, query, DataBase.fromStringId(CITCorporeUtil.SGBD_PRINCIPAL));
        final List<?> dados = this.execSQL(sqlForPaging, params);
        return this.listConvertion(beanClass, dados, fields);
    }

    private Pageable getDefaultPageable() {
        return new PageRequest(DEFAULT_PAGE, ParametroUtil.getValorParametro(ParametroSistema.REST_SERVICES_DEFAULT_PAGE_SIZE, "10"));
    }

}
