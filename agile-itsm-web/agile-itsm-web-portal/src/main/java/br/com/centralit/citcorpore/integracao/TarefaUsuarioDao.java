package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.util.Enumerados.SituacaoInstanciaFluxo;
import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.bean.ParamRecuperacaoTarefasDTO;
import br.com.centralit.citcorpore.bean.TarefaUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CalendarioServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.core.DataBase;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.PageImpl;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.integracao.core.PagingQueryUtil;
import br.com.citframework.util.Assert;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

public class TarefaUsuarioDao extends CrudDaoDefaultImpl {

    public TarefaUsuarioDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection<TarefaUsuarioDTO> find(final IDto obj) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        return null;
    }

    @Override
    public String getTableName() {
        return null;
    }

    @Override
    public Collection<TarefaUsuarioDTO> list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<TarefaUsuarioDTO> getBean() {
        return TarefaUsuarioDTO.class;
    }

    private StringBuilder userGroupsPiece(final UsuarioDTO usuario) {
        final StringBuilder str = new StringBuilder("(");
        final Collection<GrupoEmpregadoDTO> gruposEmpregado = usuario.getColGrupoEmpregado();
        if (gruposEmpregado != null) {
            int i = 0;
            for (final GrupoEmpregadoDTO grupoEmpregado : gruposEmpregado) {
                if (i > 0) {
                    str.append(",");
                }
                str.append(grupoEmpregado.getIdGrupo());
                i++;
            }
        }
        str.append(")");
        return str;
    }

    public Page<TarefaUsuarioDTO> recuperaTarefas(final ParamRecuperacaoTarefasDTO param, final Pageable pageable) throws PersistenceException {
        Assert.isTrue(param.getUsuarioDto() != null && param.getUsuarioDto().getIdUsuario() != null, "User Id must not be null.");

        final List<Object> parametros = new ArrayList<>();

        Page<TarefaUsuarioDTO> taskPage;
        if (!param.isSomenteTotalizacao()) {
            String ordenarPor = "";
            if (param.getGerenciamentoServicosDto() != null) {
                ordenarPor = param.getGerenciamentoServicosDto().getOrdenarPor() == null ? "" : param.getGerenciamentoServicosDto().getOrdenarPor();
            }

            if (ordenarPor.equals("NSolicitacao")) {
                ordenarPor = " ORDER BY sol.idsolicitacaoservico ";
            } else if (ordenarPor.equals("servico")) {
                ordenarPor = " ORDER BY s.nomeservico ";
            } else if (ordenarPor.equals("responsavel")) {
                ordenarPor = " ORDER BY e2.nome ";
            } else if (ordenarPor.equals("prioridade")) {
                ordenarPor = " ORDER BY sol.idprioridade ";
            } else if (ordenarPor.equals("situacao")) {
                ordenarPor = " ORDER BY sol.situacao ";
            } else if (ordenarPor.equals("descricao")) {
                if (MAIN_SGBD.equals(DataBase.MSSQLSERVER)) {
                    ordenarPor = " ORDER BY CAST(sol.descricao as Varchar(1000)) ";
                } else {
                    ordenarPor = " ORDER BY sol.descricao ";
                }
            } else if (ordenarPor.equals("dataHoraLimite")) {
                ordenarPor = " ORDER BY sol.datahoralimite ";
            } else if (ordenarPor.equals("dataHoraLimiteCriacao")) {
                ordenarPor = " ORDER BY sol.datahorasolicitacao ";
            } else {
                ordenarPor = " ORDER BY sol.idresponsavel ";
            }

            final StringBuilder selectQueryPiece = this.selectQueryPiece(parametros);
            final StringBuilder fromWhereQueryPiece = this.fromQueryPiece(param, parametros);

            String query;
            if (MAIN_SGBD.equals(DataBase.MSSQLSERVER)) {
                query = PagingQueryUtil.constructsSQLServerPagingPiece(pageable, selectQueryPiece.toString(), ordenarPor, fromWhereQueryPiece.toString());
            } else {
                selectQueryPiece.append(fromWhereQueryPiece);
                selectQueryPiece.append(ordenarPor);
                query = PagingQueryUtil.concatPagingPieceOnQuery(pageable, selectQueryPiece.toString(), MAIN_SGBD);
            }

            final List<?> lista = this.execSQL(query, parametros.toArray());

            List<TarefaUsuarioDTO> result = new ArrayList<>();
            if (lista != null && !lista.isEmpty()) {
                result = engine.listConvertion(TarefaUsuarioDTO.class, lista, getListRetorno());
            }

            taskPage = new PageImpl<TarefaUsuarioDTO>(result, pageable, 1L);
        } else {
            final StringBuilder fromWhereQueryPiece = this.fromQueryPiece(param, parametros);
            final StringBuilder sqlCount = this.countQueryPiece(fromWhereQueryPiece);
            final Long totalElements = this.countElements(sqlCount.toString(), parametros.toArray());
            final List<TarefaUsuarioDTO> result = new ArrayList<>();
            taskPage = this.makePage(result, pageable, totalElements);
        }

        return taskPage;
    }

    private StringBuilder selectQueryPiece(final List<Object> parametros) {
        final StringBuilder selectQueryPiece = new StringBuilder();

        selectQueryPiece.append("SELECT sol.idsolicitacaoservico, ");
        selectQueryPiece.append("       sol.idbaseconhecimento, ");
        selectQueryPiece.append("       sol.idservicocontrato, ");
        selectQueryPiece.append("       sol.idsolicitante, ");
        selectQueryPiece.append("       sol.iditemconfiguracao, ");
        selectQueryPiece.append("       sol.iditemconfiguracaofilho, ");
        selectQueryPiece.append("       sol.idtipodemandaservico, ");
        selectQueryPiece.append("       sol.idcontatosolicitacaoservico, ");
        selectQueryPiece.append("       sol.idorigem, ");
        selectQueryPiece.append("       sol.idresponsavel, ");
        selectQueryPiece.append("       sol.idtipoproblema, ");
        selectQueryPiece.append("       sol.idprioridade, ");
        selectQueryPiece.append("       sol.idunidade, ");
        selectQueryPiece.append("       sol.idfaseatual, ");
        selectQueryPiece.append("       sol.idgrupoatual, ");
        selectQueryPiece.append("       sol.datahorasolicitacao, ");
        selectQueryPiece.append("       sol.datahoralimite, ");
        selectQueryPiece.append("       sol.atendimentopresencial, ");
        selectQueryPiece.append("       sol.prazocapturahh, ");
        selectQueryPiece.append("       sol.prazocapturamm, ");
        selectQueryPiece.append("       sol.prazohh, ");
        selectQueryPiece.append("       sol.prazomm, ");
        selectQueryPiece.append("       sol.descricao, ");
        selectQueryPiece.append("       sol.resposta, ");
        selectQueryPiece.append("       sol.datahorainicio, ");
        selectQueryPiece.append("       sol.datahorafim, ");
        selectQueryPiece.append("       sol.situacao, ");
        selectQueryPiece.append("       sol.idsolicitacaopai, ");
        selectQueryPiece.append("       sol.detalhamentocausa, ");
        selectQueryPiece.append("       sol.idcausaincidente, ");
        selectQueryPiece.append("       sol.idcategoriasolucao, ");
        selectQueryPiece.append("       sol.seqreabertura, ");
        selectQueryPiece.append("       sol.enviaemailcriacao, ");
        selectQueryPiece.append("       sol.enviaemailfinalizacao, ");
        selectQueryPiece.append("       sol.enviaemailacoes, ");
        selectQueryPiece.append("       sol.idgruponivel1, ");
        selectQueryPiece.append("       sol.solucaotemporaria, ");
        selectQueryPiece.append("       sol.houvemudanca, ");
        selectQueryPiece.append("       sol.slaacombinar, ");
        selectQueryPiece.append("       sol.prazohhanterior, ");
        selectQueryPiece.append("       sol.prazommanterior, ");
        selectQueryPiece.append("       sol.idcalendario, ");
        selectQueryPiece.append("       sol.tempodecorridohh, ");
        selectQueryPiece.append("       sol.tempodecorridomm, ");
        selectQueryPiece.append("       sol.datahorasuspensao, ");
        selectQueryPiece.append("       sol.datahorareativacao, ");
        selectQueryPiece.append("       sol.impacto, ");
        selectQueryPiece.append("       sol.urgencia, ");
        selectQueryPiece.append("       sol.datahoracaptura, ");
        selectQueryPiece.append("       sol.tempocapturahh, ");
        selectQueryPiece.append("       sol.tempocapturamm, ");
        selectQueryPiece.append("       sol.tempoatrasohh, ");
        selectQueryPiece.append("       sol.tempoatrasomm, ");
        selectQueryPiece.append("       sol.tempoatendimentohh, ");
        selectQueryPiece.append("       sol.tempoatendimentomm, ");
        selectQueryPiece.append("       sol.idacordonivelservico, ");
        selectQueryPiece.append("       sol.idultimaaprovacao, ");
        selectQueryPiece.append("       sol.datahorainiciosla, ");
        selectQueryPiece.append("       sol.datahorasuspensaosla, ");
        selectQueryPiece.append("       sol.datahorareativacaosla, ");
        selectQueryPiece.append("       sol.situacaosla, ");
        selectQueryPiece.append("       aprov.aprovacao, ");
        selectQueryPiece.append("       s.idservico, ");
        selectQueryPiece.append("       s.nomeservico, ");
        selectQueryPiece.append("       td.nometipodemandaservico, ");
        selectQueryPiece.append("       c.idcontrato, ");
        selectQueryPiece.append("       c.numero, ");
        selectQueryPiece.append("       e1.nome             AS solicitante, ");
        selectQueryPiece.append("       u1.nome             AS nomeUnidadeSolicitante, ");
        selectQueryPiece.append("       e2.nome             AS responsavel, ");
        selectQueryPiece.append("       u2.nome             AS nomeUnidadeResponsavel, ");
        selectQueryPiece.append("       oa.descricao        AS origem, ");
        selectQueryPiece.append("       p.nomeprioridade, ");
        selectQueryPiece.append("       fs.nomefase, ");
        selectQueryPiece.append("       g1.sigla            AS grupoAtual, ");
        selectQueryPiece.append("       g2.sigla            AS grupoNivel1, ");
        selectQueryPiece.append("       cs.nomecontato, ");
        selectQueryPiece.append("       cs.emailcontato, ");
        selectQueryPiece.append("       cs.telefonecontato, ");
        selectQueryPiece.append("       cs.localizacaofisica, ");
        selectQueryPiece.append("       cs.idlocalidade, ");
        selectQueryPiece.append("       es.idinstanciafluxo, ");
        selectQueryPiece.append("       sol.vencendo, ");
        selectQueryPiece.append("       item.iditemtrabalho, ");
        selectQueryPiece.append("       item.idelemento, ");
        selectQueryPiece.append("       item.idresponsavelatual, ");
        selectQueryPiece.append("       item.datahoracriacao, ");
        selectQueryPiece.append("       item.datahorainicio AS dataHoraInicioTarefa, ");
        selectQueryPiece.append("       item.datahorafinalizacao, ");
        selectQueryPiece.append("       item.datahoraexecucao, ");
        selectQueryPiece.append("       item.situacao       AS situacaoTarefa, ");
        selectQueryPiece.append("       instancia.idfluxo, ");
        selectQueryPiece.append("       fluxo.idtipofluxo, ");
        selectQueryPiece.append("       atrib.tipo, ");
        selectQueryPiece.append("       usuresp.nome, ");
        selectQueryPiece.append("       (SELECT COUNT(*) ");
        selectQueryPiece.append("        FROM   controleged ");
        selectQueryPiece.append("        WHERE  idtabela = ? ");
        parametros.add(ControleGEDDTO.TABELA_SOLICITACAOSERVICO);
        selectQueryPiece.append("               AND id = sol.idsolicitacaoservico) AS qtdeAnexos, ");
        selectQueryPiece.append("       (SELECT COUNT(*) ");
        selectQueryPiece.append("        FROM   solicitacaoservico ");
        selectQueryPiece.append("        WHERE  idsolicitacaorelacionada = sol.idsolicitacaoservico) AS qtdeFilhas ");

        return selectQueryPiece;
    }

    private StringBuilder fromQueryPiece(final ParamRecuperacaoTarefasDTO param, final List<Object> parametros) {
        final StringBuilder strGrupos = this.userGroupsPiece(param.getUsuarioDto());

        final StringBuilder fromWhere = new StringBuilder();

        fromWhere.append("FROM   bpm_itemtrabalhofluxo item ");
        fromWhere.append("       INNER JOIN bpm_instanciafluxo instancia ");
        fromWhere.append("          ON instancia.idinstancia = item.idinstancia ");
        fromWhere.append("       INNER JOIN bpm_fluxo fluxo ");
        fromWhere.append("          ON fluxo.idfluxo = instancia.idfluxo ");
        fromWhere.append("       INNER JOIN bpm_atribuicaofluxo atrib ");
        fromWhere.append("          ON atrib.iditemtrabalho = item.iditemtrabalho ");
        fromWhere.append("       INNER JOIN execucaosolicitacao es ");
        fromWhere.append("          ON es.idinstanciafluxo = item.idinstancia ");
        fromWhere.append("       INNER JOIN solicitacaoservico sol ");
        fromWhere.append("          ON sol.idsolicitacaoservico = es.idsolicitacaoservico ");
        fromWhere.append("       LEFT JOIN usuario usuresp ");
        fromWhere.append("         ON usuresp.idusuario = item.idresponsavelatual ");
        fromWhere.append("       LEFT JOIN servicocontrato sc ");
        fromWhere.append("              ON sc.idservicocontrato = sol.idservicocontrato ");
        fromWhere.append("       LEFT JOIN contratos c ");
        fromWhere.append("              ON c.idcontrato = sc.idcontrato ");
        fromWhere.append("       LEFT JOIN servico s ");
        fromWhere.append("              ON s.idservico = sc.idservico ");
        fromWhere.append("       LEFT JOIN tipodemandaservico td ");
        fromWhere.append("              ON td.idtipodemandaservico = s.idtipodemandaservico ");
        fromWhere.append("       LEFT JOIN empregados e1 ");
        fromWhere.append("              ON e1.idempregado = sol.idsolicitante ");
        fromWhere.append("       LEFT JOIN unidade u1 ");
        fromWhere.append("              ON u1.idunidade = e1.idunidade ");
        fromWhere.append("       LEFT JOIN usuario usu ");
        fromWhere.append("              ON usu.idusuario = sol.idresponsavel ");
        fromWhere.append("       LEFT JOIN empregados e2 ");
        fromWhere.append("              ON e2.idempregado = usu.idempregado ");
        fromWhere.append("       LEFT JOIN unidade u2 ");
        fromWhere.append("              ON u2.idunidade = e2.idunidade ");
        fromWhere.append("       LEFT JOIN origematendimento oa ");
        fromWhere.append("              ON oa.idorigem = sol.idorigem ");
        fromWhere.append("       LEFT JOIN prioridade p ");
        fromWhere.append("              ON p.idprioridade = sol.idprioridade ");
        fromWhere.append("       LEFT JOIN faseservico fs ");
        fromWhere.append("              ON fs.idfase = sol.idfaseatual ");
        fromWhere.append("       LEFT JOIN grupo g1 ");
        fromWhere.append("              ON g1.idgrupo = sol.idgrupoatual ");
        fromWhere.append("       LEFT JOIN grupo g2 ");
        fromWhere.append("              ON g2.idgrupo = sol.idgruponivel1 ");
        fromWhere.append("       LEFT JOIN contatosolicitacaoservico cs ");
        fromWhere.append("              ON cs.idcontatosolicitacaoservico = ");
        fromWhere.append("                 sol.idcontatosolicitacaoservico ");
        fromWhere.append("       LEFT JOIN aprovacaosolicitacaoservico aprov ");
        fromWhere.append("              ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");
        fromWhere.append("WHERE  sol.idsolicitacaopai IS NULL ");
        fromWhere.append("       AND item.situacao NOT IN ( ?, ? ) ");
        parametros.add(SituacaoItemTrabalho.Executado.name());
        parametros.add(SituacaoItemTrabalho.Cancelado.name());

        fromWhere.append("       AND instancia.situacao IN ( ?, ? ) ");
        parametros.add(SituacaoInstanciaFluxo.Aberta.name());
        parametros.add(SituacaoInstanciaFluxo.Suspensa.name());

        // Tratamento para eliminação de duplicidades de atribuição quando o usuário pertence a mais de um grupo com o mesmo tipo de permissão
        fromWhere.append("       AND atrib.idatribuicao = (SELECT max(a.idatribuicao) FROM bpm_atribuicaofluxo a");
        fromWhere.append("                                   WHERE a.iditemtrabalho = item.iditemtrabalho");
        fromWhere.append("                                    AND a.tipo = atrib.tipo");
        fromWhere.append("                                    AND (a.idusuario = ?");
        fromWhere.append("                                     OR  ((a.idusuario is null OR a.idusuario <> ?) AND a.idgrupo IN ").append(strGrupos).append(")))");
        parametros.add(param.getUsuarioDto().getIdUsuario());
        parametros.add(param.getUsuarioDto().getIdUsuario());

        fromWhere.append("       AND ( item.idresponsavelatual = ? ");
        parametros.add(param.getUsuarioDto().getIdUsuario());

        fromWhere.append("              OR atrib.idusuario = ? ");
        parametros.add(param.getUsuarioDto().getIdUsuario());

        fromWhere.append("              OR ((atrib.idusuario is null OR atrib.idusuario <> ?) and atrib.idgrupo IN ").append(strGrupos).append("))");
        parametros.add(param.getUsuarioDto().getIdUsuario());

        if (param.getIdTarefa() != null) {
            fromWhere.append("    AND item.iditemtrabalhofluxo = ? ");
            parametros.add(param.getIdTarefa());
        }

        // ********** Início tratamento dos tipos de atribuição
        fromWhere.append("       AND (");

        // atribuição automática
        fromWhere.append("            atrib.tipo = ? ");
        parametros.add(TipoAtribuicao.Automatica.name());

        // ou atribuição por delegação e não exista atribuição automática p/ o item
        fromWhere.append("            OR (atrib.tipo = ? AND 0 = ");
        parametros.add(TipoAtribuicao.Delegacao.name());

        fromWhere.append("                  (SELECT count(*) ");
        fromWhere.append("                     FROM bpm_atribuicaofluxo a ");
        fromWhere.append("                    WHERE a.iditemtrabalho = item.iditemtrabalho ");
        fromWhere.append("                      AND a.tipo IN (?) ");
        parametros.add(TipoAtribuicao.Automatica.name());

        fromWhere.append("                      AND (a.idusuario = ? ");
        parametros.add(param.getUsuarioDto().getIdUsuario());

        fromWhere.append("                       OR a.idgrupo IN ").append(strGrupos).append(")))");

        // ou atribuição por acompanhamento e não exista atribuição automática ou delegação p/ o item
        fromWhere.append("            OR (atrib.tipo = ? AND 0 = ");
        parametros.add(TipoAtribuicao.Acompanhamento.name());

        fromWhere.append("                  (SELECT count(*) ");
        fromWhere.append("                     FROM bpm_atribuicaofluxo a ");
        fromWhere.append("                    WHERE a.iditemtrabalho = item.iditemtrabalho ");
        fromWhere.append("                      AND a.tipo IN (?, ?) ");
        parametros.add(TipoAtribuicao.Automatica.name());
        parametros.add(TipoAtribuicao.Delegacao.name());

        fromWhere.append("                      AND (a.idusuario = ? ");
        parametros.add(param.getUsuarioDto().getIdUsuario());

        fromWhere.append("                       OR a.idgrupo IN ").append(strGrupos).append(")))");

        // ********** Fim tratamento dos tipos de atribuição
        fromWhere.append("           )");

        if (param.getContratosUsuario() != null && !param.getContratosUsuario().isEmpty()) {
            fromWhere.append(" AND c.idcontrato in ( ");
            boolean first = true;
            for (final ContratoDTO contrato : param.getContratosUsuario()) {
                if (first) {
                    fromWhere.append(contrato.getIdContrato());
                    first = false;
                } else {
                    fromWhere.append(",");
                    fromWhere.append(contrato.getIdContrato());
                }
            }
            fromWhere.append(" )");
        }

        this.adicionaFiltroPesquisa(fromWhere, param.getGerenciamentoServicosDto(), parametros);

        return fromWhere;
    }

    /**
     * Adiciona o filtro de pesquisa de solicitação
     *
     * @param sql
     * @param gerenciamento
     * @param parametros
     */
    private void adicionaFiltroPesquisa(final StringBuilder sql, final GerenciamentoServicosDTO gerenciamento, final List<Object> parametros) {
        if (gerenciamento == null) {
            return;
        }

        if (gerenciamento.getIdSolicitacao() != null && !gerenciamento.getIdSolicitacao().equals(new Integer(-1))) {
            sql.append(" AND sol.idSolicitacaoServico = ? ");
            parametros.add(gerenciamento.getIdSolicitacao());
        }
        if (gerenciamento.getIdSolicitante() != null && !gerenciamento.getIdSolicitante().equals(new Integer(-1))) {
            sql.append(" AND sol.idSolicitante = ? ");
            parametros.add(gerenciamento.getIdSolicitante());
        }
        if (gerenciamento.getIdTipo() != null && !gerenciamento.getIdTipo().equals(new Integer(-1))) {
            sql.append(" AND sol.idTipoDemandaServico = ? ");
            parametros.add(gerenciamento.getIdTipo());
        }
        if (gerenciamento.getIdContrato() != null && !gerenciamento.getIdContrato().equals(new Integer(-1))) {
            sql.append(" AND c.idcontrato = ? ");
            parametros.add(gerenciamento.getIdContrato());
        }
        if (gerenciamento.getIdGrupoAtual() != null && !gerenciamento.getIdGrupoAtual().equals(new Integer(-1))) {
            if (gerenciamento.getIdGrupoAtual().equals(new Integer(0))) {
                sql.append(" AND sol.idGrupoAtual is null ");
            } else {
                sql.append(" AND sol.idGrupoAtual = ? ");
                parametros.add(gerenciamento.getIdGrupoAtual());
            }
        }
        if (StringUtils.isNotEmpty(gerenciamento.getPalavraChave())) {
            sql.append(" AND ( ");
            sql.append("    sol.descricao like ? ");
            parametros.add("%" + gerenciamento.getPalavraChave() + "%");
            sql.append("    OR s.nomeServico like ? ");
            parametros.add("%" + gerenciamento.getPalavraChave() + "%");
            sql.append("    OR e1.nome like ? ");
            parametros.add("%" + gerenciamento.getPalavraChave() + "%");
            sql.append("    OR g1.sigla like ? ");
            parametros.add("%" + gerenciamento.getPalavraChave() + "%");
            sql.append(") ");
        }
        if (StringUtils.isNotBlank(gerenciamento.getSituacao())) {
            sql.append(" AND sol.situacao = ? ");
            parametros.add(gerenciamento.getSituacao());
        }
        if (StringUtils.isNotEmpty(gerenciamento.getTarefaAtual())) {
            sql.append(" AND item.idelemento IN (SELECT idelemento FROM bpm_elementofluxo e WHERE e.documentacao LIKE ? )");
            parametros.add("%" + gerenciamento.getTarefaAtual() + "%");
        }

        final String tipoVisualizacao = gerenciamento.getTipoVisualizacao() == null ? "" : gerenciamento.getTipoVisualizacao();

        // tipo de visualização
        if (tipoVisualizacao.equalsIgnoreCase("possoExecutar")) {
            sql.append(" AND atrib.tipo IN (?, ?) ");
            parametros.add(TipoAtribuicao.Delegacao.name());
            parametros.add(TipoAtribuicao.Automatica.name());
        } else if (tipoVisualizacao.equalsIgnoreCase("possoVisualizar")) {
            sql.append(" AND atrib.tipo IN (?) ");
            parametros.add(TipoAtribuicao.Acompanhamento.name());
        }

        this.adicionaFiltroSLA(sql, gerenciamento, parametros);
    }

    /**
     * Adiciona o filtro de pesquisa de SLA
     *
     * @param sql
     * @param gerenciamento
     * @param parametros
     */
    private void adicionaFiltroSLA(final StringBuilder sql, final GerenciamentoServicosDTO gerenciamento, final List<Object> parametros) {
        if (gerenciamento == null || gerenciamento.getSituacaoSla() == null || gerenciamento.getSituacaoSla().trim().equals("")) {
            return;
        }

        final String situacaoSla = gerenciamento.getSituacaoSla() == null ? "" : gerenciamento.getSituacaoSla();

        // tipo de Sla
        if (situacaoSla.equalsIgnoreCase("vencido")) {
            sql.append(" AND sol.situacao <> ? ");
            sql.append(" AND sol.situacaoSLA = 'A' ");
            sql.append(" AND (sol.slaacombinar IS NULL OR sol.slaacombinar <> 'S') ");
            sql.append(" AND sol.prazohh IS NOT NULL AND sol.prazomm IS NOT NULL AND (sol.prazohh + sol.prazomm > 0) ");
            sql.append(" AND sol.datahoralimite is not null AND sol.datahoralimite < ? ");
            parametros.add(SituacaoSolicitacaoServico.Suspensa.name());
            parametros.add(UtilDatas.getDataHoraAtual());
        } else if (situacaoSla.equalsIgnoreCase("aguardandoAprovacao")) {
            sql.append(" AND item.idelemento IN (SELECT idelemento FROM bpm_elementofluxo e INNER JOIN templatesolicitacaoservico t ON t.identificacao = e.template ");
            sql.append("                          WHERE t.aprovacao = 'S' )");
        } else if (situacaoSla.equalsIgnoreCase("avencerHoje")) {
            sql.append(" AND sol.situacao <> ? ");
            sql.append(" AND sol.situacaoSLA = 'A' ");
            sql.append(" AND (sol.slaacombinar IS NULL OR sol.slaacombinar <> 'S') ");
            sql.append(" AND sol.prazohh IS NOT NULL AND sol.prazomm IS NOT NULL AND (sol.prazohh + sol.prazomm > 0) ");
            sql.append(" AND sol.datahoralimite is not null AND sol.datahoralimite > ? ");
            if (MAIN_SGBD.equals(DataBase.ORACLE)) {
                sql.append(" AND TRUNC(sol.datahoralimite) = ? ");
            } else if (MAIN_SGBD.equals(DataBase.MSSQLSERVER)) {
                sql.append(" AND CAST(sol.datahoralimite AS DATE) = ? ");
            } else if (MAIN_SGBD.equals(DataBase.MYSQL)) {
                sql.append(" AND DATE(sol.datahoralimite) = ? ");
            } else {
                sql.append(" AND TRUNC(sol.datahoralimite) = ? ");
            }
            parametros.add(SituacaoSolicitacaoServico.Suspensa.name());
            parametros.add(UtilDatas.getDataHoraAtual());
            parametros.add(UtilDatas.getDataAtual());
        } else if (situacaoSla.equalsIgnoreCase("avencerProxDiaUtil")) {
            final String idCalendarioStr = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_CALENDARIO_PADRAO, "1");
            final CalendarioServiceEjb calendarioService = new CalendarioServiceEjb();
            Timestamp diaUtilSeguinte = null;
            try {
                final CalendarioDTO calendarioDto = calendarioService.recuperaCalendario(new Integer(idCalendarioStr));
                if (calendarioDto != null) {
                    JornadaTrabalhoDTO jornadaDtoVerificaSeExiste = null;
                    int cont = 0;
                    do {
                        if (cont == 0) {
                            cont = 1;
                            diaUtilSeguinte = calendarioService.incrementaDias(UtilDatas.getDataHoraAtual(), 1);
                        } else {
                            diaUtilSeguinte = calendarioService.incrementaDias(diaUtilSeguinte, 1);
                        }

                        final Date dataRef = new Date(diaUtilSeguinte.getTime());
                        jornadaDtoVerificaSeExiste = calendarioService.recuperaJornada(calendarioDto, dataRef, Util.getHoraDbl(UtilDatas.getHoraHHMM(diaUtilSeguinte)));
                    } while (jornadaDtoVerificaSeExiste == null);
                    diaUtilSeguinte = new Timestamp(UtilDatas.incrementaDiasEmData(diaUtilSeguinte, 1).getTime());
                    sql.append(" AND sol.situacao <> ? ");
                    sql.append(" AND sol.situacaoSLA = 'A' ");
                    sql.append(" AND (sol.slaacombinar IS NULL OR sol.slaacombinar <> 'S') ");
                    sql.append(" AND sol.prazohh IS NOT NULL AND sol.prazomm IS NOT NULL AND (sol.prazohh + sol.prazomm > 0) ");
                    sql.append(" AND sol.datahoralimite is not null AND sol.datahoralimite > ? ");
                    sql.append(" AND sol.datahoralimite < ? ");
                    parametros.add(SituacaoSolicitacaoServico.Suspensa.name());
                    parametros.add(UtilDatas.getDataHoraAtual());
                    parametros.add(diaUtilSeguinte);
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        } else if (StringUtils.containsIgnoreCase(situacaoSla, "avencer")) {
            int horas = 0;
            int minutos = 0;
            if (situacaoSla.equalsIgnoreCase("avencer30min")) {
                minutos = 30;
            } else if (situacaoSla.equalsIgnoreCase("avencer60min")) {
                horas = 1;
            } else if (situacaoSla.equalsIgnoreCase("avencer90min")) {
                horas = 1;
                minutos = 30;
            } else if (situacaoSla.equalsIgnoreCase("avencer2h")) {
                horas = 2;
            } else if (situacaoSla.equalsIgnoreCase("avencer3h")) {
                horas = 3;
            }

            final Timestamp ts = UtilDatas.somaSegundos(UtilDatas.getDataHoraAtual(), minutos * 60 + horas * 60 * 60);
            sql.append(" AND sol.situacao <> ? ");
            sql.append(" AND sol.situacaoSLA = 'A' ");
            sql.append(" AND (sol.slaacombinar IS NULL OR sol.slaacombinar <> 'S') ");
            sql.append(" AND sol.prazohh IS NOT NULL AND sol.prazomm IS NOT NULL AND (sol.prazohh + sol.prazomm > 0) ");
            sql.append(" AND sol.datahoralimite is not null AND sol.datahoralimite > ? ");
            sql.append(" AND sol.datahoralimite < ? ");
            parametros.add(SituacaoSolicitacaoServico.Suspensa.name());
            parametros.add(UtilDatas.getDataHoraAtual());
            parametros.add(ts);
        }
    }

    private static final List<String> resultList = new ArrayList<>();

    private static List<String> getListRetorno() {
        if (resultList.isEmpty()) {
            resultList.add("idSolicitacaoServico");
            resultList.add("idbaseconhecimento");
            resultList.add("idServicoContrato");
            resultList.add("idSolicitante");
            resultList.add("idItemConfiguracao");
            resultList.add("idItemConfiguracaoFilho");
            resultList.add("idTipodemandaServico");
            resultList.add("idContatoSolicitacaoServico");
            resultList.add("idOrigem");
            resultList.add("idResponsavel");
            resultList.add("idTipoProblema");
            resultList.add("idPrioridade");
            resultList.add("idUnidade");
            resultList.add("idFaseAtual");
            resultList.add("idGrupoAtual");
            resultList.add("dataHoraSolicitacao");
            resultList.add("dataHoraLimite");
            resultList.add("atendimentoPresencial");
            resultList.add("prazoCapturaHH");
            resultList.add("prazoCapturaMM");
            resultList.add("prazoHH");
            resultList.add("prazoMM");
            resultList.add("descricao");
            resultList.add("resposta");
            resultList.add("dataHoraInicio");
            resultList.add("dataHoraFim");
            resultList.add("situacao");
            resultList.add("idSolicitacaoPai");
            resultList.add("detalhamentoCausa");
            resultList.add("idCausaIncidente");
            resultList.add("idCategoriaSolucao");
            resultList.add("seqReabertura");
            resultList.add("enviaEmailCriacao");
            resultList.add("enviaEmailFinalizacao");
            resultList.add("enviaEmailAcoes");
            resultList.add("idGrupoNivel1");
            resultList.add("solucaoTemporaria");
            resultList.add("houveMudanca");
            resultList.add("slaACombinar");
            resultList.add("prazohhAnterior");
            resultList.add("prazommAnterior");
            resultList.add("idCalendario");
            resultList.add("tempoDecorridoHH");
            resultList.add("tempoDecorridoMM");
            resultList.add("dataHoraSuspensao");
            resultList.add("dataHoraReativacao");
            resultList.add("impacto");
            resultList.add("urgencia");
            resultList.add("dataHoraCaptura");
            resultList.add("tempoCapturaHH");
            resultList.add("tempoCapturaMM");
            resultList.add("tempoAtrasoHH");
            resultList.add("tempoAtrasoMM");
            resultList.add("tempoAtendimentoHH");
            resultList.add("tempoAtendimentoMM");
            resultList.add("idAcordoNivelServico");
            resultList.add("idUltimaAprovacao");
            resultList.add("dataHoraInicioSLA");
            resultList.add("dataHoraSuspensaoSLA");
            resultList.add("dataHoraReativacaoSLA");
            resultList.add("situacaoSLA");
            resultList.add("aprovacao");
            resultList.add("idServico");
            resultList.add("servico");
            resultList.add("demanda");
            resultList.add("idContrato");
            resultList.add("contrato");
            resultList.add("solicitante");
            resultList.add("nomeUnidadeSolicitante");
            resultList.add("responsavel");
            resultList.add("nomeUnidadeResponsavel");
            resultList.add("origem");
            resultList.add("prioridade");
            resultList.add("faseAtual");
            resultList.add("grupoAtual");
            resultList.add("grupoNivel1");
            resultList.add("nomecontato");
            resultList.add("emailcontato");
            resultList.add("telefonecontato");
            resultList.add("observacao");
            resultList.add("idLocalidade");
            resultList.add("idInstanciaFluxo");
            resultList.add("vencendo");
            resultList.add("idTarefa");
            resultList.add("idElementoFluxo");
            resultList.add("idResponsavelAtual");
            resultList.add("dataHoraCriacaoTarefa");
            resultList.add("dataHoraInicioTarefa");
            resultList.add("dataHoraFinalizacaoTarefa");
            resultList.add("dataHoraExecucaoTarefa");
            resultList.add("situacaoTarefa");
            resultList.add("idFluxo");
            resultList.add("idTipoFluxo");
            resultList.add("tipoAtribuicao");
            resultList.add("nomeUsuarioResponsavelAtual");
            resultList.add("qtdeAnexos");
            resultList.add("qtdefilhas");
        }
        return resultList;
    }

}
