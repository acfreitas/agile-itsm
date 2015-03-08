package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoAtendimentoDTO;
import br.com.centralit.citcorpore.bean.result.HistoricoAtendimentoSearchResultDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServicoNaRota;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.integracao.core.DataBase;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

/**
 * DAO para recuperação de informação de {@link HistoricoAtendimentoDTO}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 12/11/2014
 *
 */
public class HistoricoAtendimentoDAO {

    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd hh:mm:ss";

    private final JdbcEngine engine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);

    public List<HistoricoAtendimentoSearchResultDTO> listHistoricoAtendimentoWithSolicitationInfo(final HistoricoAtendimentoDTO historicoAtendimento) throws PersistenceException {
        List<HistoricoAtendimentoSearchResultDTO> historicos = new ArrayList<>();

        final List<Object> parametros = new ArrayList<>();
        final String query = this.getSQLListagem(historicoAtendimento, parametros).toString();
        final Object[] parametrosArray = parametros.toArray();
        final List<?> result = engine.execSQL(query, parametrosArray, 0);

        if (result.size() > 0) {
            historicos = engine.listConvertion(HistoricoAtendimentoSearchResultDTO.class, result, this.getFieldsToReturn());
        }

        return historicos;
    }

    private StringBuilder getSQLListagem(final HistoricoAtendimentoDTO historico, final List<Object> parametros) {
        final DataBase db = DataBase.fromStringId(CITCorporeUtil.SGBD_PRINCIPAL);

        final Integer idGrupo = historico.getIdGrupo();
        final Integer idUnidade = historico.getIdUnidade();
        final Integer idSituacao = historico.getIdSituacao();
        final Integer idAtendente = historico.getIdAtendente();
        final Date dataInicio = historico.getDataInicio();
        final Date dataFim = historico.getDataFim();
        final Timestamp dataTimeInicio = historico.getTimestampInicio();
        final Timestamp dataTimeFim = historico.getTimestampFim();

        final boolean hasIdGrupo = idGrupo != null && idGrupo.intValue() > 0;
        final boolean hasIdUnidade = idUnidade != null && idUnidade.intValue() > 0;
        final boolean hasIdSituacao = idSituacao != null && idSituacao.intValue() > 0;
        final boolean hasIdAtendente = idAtendente != null && idAtendente.intValue() > 0;

        final StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT usr.idusuario                    AS atendenteId, ");
        sql.append("                emp.nome                         AS atendenteNome, ");
        sql.append("                sol.idsolicitacaoservico         AS solicitacaoNumero, ");

        if (db.equals(DataBase.ORACLE)) {
            sql.append("       dbms_lob.SUBSTR");
        } else {
            sql.append("       SUBSTRING");
        }

        sql.append("(sol.descricao, 1, 100) AS solicitacaoDescricao, ");

        sql.append("                serv.nomeservico                 AS solicitacaoServico, ");
        sql.append("                sol.situacao                     AS solicitacaoSituacao, ");
        sql.append("                solic.nome                       AS solicitacaoSolicitante, ");
        sql.append("                sol.prazohh                      AS solicitacaoPrazoHH, ");
        sql.append("                sol.prazomm                      AS solicitacaoPrazoMM, ");
        sql.append("                ender.latitude                   AS solicitacaoLatitude, ");
        sql.append("                ender.longitude                  AS solicitacaoLongitude, ");
        sql.append("                und.nome                         AS unidadeNome, ");
        sql.append("                pos.latitude                     AS posicaoLatitude, ");
        sql.append("                pos.longitude                    AS posicaoLongitude, ");
        sql.append("                atr.id                           AS atribuicaoId, ");
        sql.append("                atr.datainicioatendimento        AS atribuicaoDatetime, ");
        sql.append("                pos.datetime ");
        sql.append("FROM   solicitacaoservico sol ");
        sql.append("       INNER JOIN atribuicaosolicitacao atr ");
        sql.append("               ON sol.idsolicitacaoservico = atr.idsolicitacao ");
        sql.append("       INNER JOIN usuario usr ");
        sql.append("               ON atr.idusuario = usr.idusuario ");
        sql.append("       LEFT JOIN posatendentehistorico pos ");
        sql.append("              ON pos.idusuario = usr.idusuario ");
        sql.append("       INNER JOIN empregados emp ");
        sql.append("               ON usr.idempregado = emp.idempregado ");
        sql.append("       INNER JOIN empregados solic ");
        sql.append("               ON sol.idsolicitante = solic.idempregado ");

        if (hasIdGrupo) {
            sql.append("       LEFT JOIN gruposempregados gemp ");
            sql.append("              ON gemp.idempregado = emp.idempregado ");
        }

        sql.append("       INNER JOIN servicocontrato servContrato ");
        sql.append("               ON sol.idservicocontrato = servContrato.idservicocontrato ");
        sql.append("       INNER JOIN servico serv ");
        sql.append("               ON servContrato.idservico = serv.idservico ");
        sql.append("       LEFT JOIN checkin checkin ");
        sql.append("              ON sol.idsolicitacaoservico = checkin.idsolicitacao ");
        sql.append("       INNER JOIN unidade und ");
        sql.append("               ON sol.idunidade = und.idunidade ");
        sql.append("       LEFT JOIN contratosunidades contunid ");
        sql.append("              ON contunid.idunidade = und.idunidade ");
        sql.append("       LEFT JOIN contratos con ");
        sql.append("              ON con.idcontrato = contunid.idcontrato ");
        sql.append("       INNER JOIN endereco ender ");
        sql.append("               ON und.idendereco = ender.idendereco ");
        sql.append("WHERE  servContrato.idcontrato = ? ");

        parametros.add(historico.getIdContrato());

        if (db.equals(DataBase.ORACLE)) {
            sql.append("       AND TO_CHAR(pos.datetime, 'YYYY-MM-DD HH24:MI:SS') BETWEEN ? AND ? ");
            sql.append("       AND TO_CHAR(atr.dataexecucao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
            parametros.add(UtilDatas.dateToSTRWithFormat(dataTimeInicio, YYYY_MM_DD_HH_MM_SS));
            parametros.add(UtilDatas.dateToSTRWithFormat(dataTimeFim, YYYY_MM_DD_HH_MM_SS));
            parametros.add(UtilDatas.dateToSTRWithFormat(dataInicio, YYYY_MM_DD));
            parametros.add(UtilDatas.dateToSTRWithFormat(dataFim, YYYY_MM_DD));
        } else {
            sql.append("       AND pos.datetime BETWEEN ? AND ? ");
            sql.append("       AND ( atr.dataexecucao BETWEEN ? AND ? ");
            sql.append("        OR  (atr.datainicioatendimento BETWEEN ? AND ?  ) ) ");
            parametros.add(dataTimeInicio);
            parametros.add(dataTimeFim);
            parametros.add(dataInicio);
            parametros.add(dataFim);
            parametros.add(dataTimeInicio);
            parametros.add(dataTimeFim);
        }

        if (hasIdGrupo) {
            sql.append("       AND gemp.idgrupo = ? ");
            parametros.add(idGrupo);
        }

        if (hasIdUnidade) {
            sql.append("       AND und.idunidade = ? ");
            parametros.add(idUnidade);
        }

        if (hasIdAtendente) {
            sql.append("       AND emp.idempregado = ? ");
            parametros.add(idAtendente);
        }

        if (hasIdSituacao) {
            final SituacaoSolicitacaoServicoNaRota situacao = SituacaoSolicitacaoServicoNaRota.fromId(idSituacao);
            switch (situacao) {
            case ATENDIDA_FINALIZADA:
                sql.append("       AND ( sol.situacao = 'Resolvida' OR sol.situacao = 'Fechada') ");
                break;
            case NAO_ATENDIDA:
                sql.append("       AND sol.situacao = 'EmAndamento' ");
                sql.append("       AND atr.id IS NOT NULL ");
                sql.append("       AND atr.datainicioatendimento IS  NULL ");
                break;
            case EM_ATENDIMENTO:
                sql.append("       AND sol.situacao = 'EmAndamento' ");
                sql.append("       AND atr.id IS NOT NULL ");
                sql.append("       AND atr.datainicioatendimento IS NOT NULL ");
                break;
            case ATENDIDA_COM_PENDENCIA:
                sql.append("       AND sol.situacao = 'Suspensa' ");
                sql.append("       AND atr.id IS NOT NULL ");
                break;
                
            }
        }

        sql.append("ORDER  BY sol.idsolicitacaoservico, ");
        sql.append("          pos.datetime, ");
        sql.append("          usr.idusuario");

        return sql;
    }

    private List<String> fields;

    private List<String> getFieldsToReturn() {
        if (fields == null) {
            fields = new ArrayList<>();
            fields.add("atendenteId");
            fields.add("atendenteNome");
            fields.add("solicitacaoNumero");
            fields.add("solicitacaoDescricao");
            fields.add("solicitacaoServico");
            fields.add("solicitacaoSituacao");
            fields.add("solicitacaoSolicitante");
            fields.add("solicitacaoPrazoHH");
            fields.add("solicitacaoPrazoMM");
            fields.add("solicitacaoLatitude");
            fields.add("solicitacaoLongitude");
            fields.add("unidadeNome");
            fields.add("posicaoLatitude");
            fields.add("posicaoLongitude");
            fields.add("atribuicaoId");
            fields.add("atribuicaoDatetime");
        }
        return fields;
    }

}
