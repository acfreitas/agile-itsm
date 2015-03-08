package br.com.centralit.citcorpore.integracao;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.CausaIncidenteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.bean.PesquisaSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.RelatorioCausaSolucaoDTO;
import br.com.centralit.citcorpore.bean.RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO;
import br.com.centralit.citcorpore.bean.RelatorioEficaciaTesteDTO;
import br.com.centralit.citcorpore.bean.RelatorioIncidentesNaoResolvidosDTO;
import br.com.centralit.citcorpore.bean.RelatorioKpiProdutividadeDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoRetornoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO;
import br.com.centralit.citcorpore.bean.RelatorioSolicitacaoPorExecutanteDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CalendarioServiceEjb;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico;
import br.com.centralit.citcorpore.util.Enumerados.TipoSolicitacaoServico;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class SolicitacaoServicoDao extends CrudDaoDefaultImpl {

    public static String strSGBDPrincipal = null;
    private static final String TABLE_NAME = "solicitacaoservico";

    public SolicitacaoServicoDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    /**
     * Adicona o filtro de pesquisa de solicitação a paginação dos itens Precisa ser adicionado na lista de tarefas e na paginação dos itens
     *
     * @param sql
     * @param gerenciamentoBean
     * @throws Exception
     */
    public void adicionarFiltroPesquisa(StringBuilder sql, GerenciamentoServicosDTO gerenciamentoBean, List parametros) throws Exception {
        if (gerenciamentoBean != null) {
            if (gerenciamentoBean.getIdSolicitacao() != null && !gerenciamentoBean.getIdSolicitacao().equals(new Integer(-1))) {
                sql.append(" AND sol.idSolicitacaoServico = ? ");
                parametros.add(gerenciamentoBean.getIdSolicitacao());
            }
            if (gerenciamentoBean.getIdSolicitante() != null && !gerenciamentoBean.getIdSolicitante().equals(new Integer(-1))) {
                sql.append(" AND sol.idSolicitante = ? ");
                parametros.add(gerenciamentoBean.getIdSolicitante());
            }
            if (gerenciamentoBean.getIdTipo() != null && !gerenciamentoBean.getIdTipo().equals(new Integer(-1))) {
                sql.append(" AND sol.idTipoDemandaServico = ? ");
                parametros.add(gerenciamentoBean.getIdTipo());
            }
            if (gerenciamentoBean.getIdContrato() != null && !gerenciamentoBean.getIdContrato().equals(new Integer(-1))) {
                sql.append(" AND c.idcontrato = ? ");
                parametros.add(gerenciamentoBean.getIdContrato());
            }
            if (gerenciamentoBean.getIdGrupoAtual() != null && !gerenciamentoBean.getIdGrupoAtual().equals(new Integer(-1))) {
                // Hack para itens sem atribuição)
                if (gerenciamentoBean.getIdGrupoAtual().equals(new Integer(0))) {
                    sql.append(" AND sol.idGrupoAtual is null ");
                } else {
                    sql.append(" AND sol.idGrupoAtual = ? ");
                    parametros.add(gerenciamentoBean.getIdGrupoAtual());
                }
            }
            if (gerenciamentoBean.getPalavraChave() != null && !StringUtils.isEmpty(gerenciamentoBean.getPalavraChave())) {
                /*
                 * Rodrigo Pecci Acorse - 27/01/2014 10h30 - #132118 Adicionado % nos likes para melhor o resultado das buscas por palavra chave.
                 */
                sql.append(" AND ( ");
                sql.append("	sol.descricao like ? ");
                parametros.add("%" + gerenciamentoBean.getPalavraChave() + "%");
                sql.append("	OR s.nomeServico like ? ");
                parametros.add("%" + gerenciamentoBean.getPalavraChave() + "%");
                sql.append("	OR e1.nome like ? ");
                parametros.add("%" + gerenciamentoBean.getPalavraChave() + "%");
                sql.append("	OR g1.sigla like ? ");
                parametros.add("%" + gerenciamentoBean.getPalavraChave() + "%");
                sql.append(") ");
            }
            if (gerenciamentoBean.getSituacao() != null && StringUtils.isNotBlank(gerenciamentoBean.getSituacao())) {
                sql.append(" AND sol.situacao = ? ");
                parametros.add(gerenciamentoBean.getSituacao());
            }

        }
    }

    public void atualizaDataHoraCaptura(SolicitacaoServicoDTO solicitacaoDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + getTableName() + " SET dataHoraCaptura = ? WHERE idsolicitacaoservico = ?");
        Object[] params = { solicitacaoDto.getDataHoraCaptura(), solicitacaoDto.getIdSolicitacaoServico() };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização da solicitacaoServico.");
            e.printStackTrace();
        }
    }

    public void atualizaIdTarefaEncerramento(SolicitacaoServicoDTO solicitacaoServicoDto) {
        StringBuilder sql = new StringBuilder();
        Object[] params = null;
        if (solicitacaoServicoDto.getIdTarefaEncerramento() != null) {
            sql.append("UPDATE " + getTableName() + " SET idTarefaEncerramento = ? WHERE idsolicitacaoservico = ?");
            params = new Object[] { solicitacaoServicoDto.getIdTarefaEncerramento(), solicitacaoServicoDto.getIdSolicitacaoServico() };
        } else {
            sql.append("UPDATE " + getTableName() + " SET idTarefaEncerramento = null WHERE idsolicitacaoservico = ?");
            params = new Object[] { solicitacaoServicoDto.getIdSolicitacaoServico() };
        }
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização da tarefa de encerramento da solicitação.");
            e.printStackTrace();
        }
    }

    public void atualizaIdUltimaAprovacao(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + getTableName() + " SET idultimaaprovacao = ? WHERE idsolicitacaoservico = ?");
        Object[] params = { solicitacaoServicoDto.getIdUltimaAprovacao(), solicitacaoServicoDto.getIdSolicitacaoServico() };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas na aprovação da solicitacao de serviço.");
            e.printStackTrace();
        }
    }

    public void atualizaIdUsuarioResponsavel(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + getTableName() + " SET idusuarioresponsavelatual = ? WHERE (idsolicitacaoservico = ?)");
        Object[] params = { solicitacaoServicoDto.getIdUsuarioResponsavelAtual(), solicitacaoServicoDto.getIdSolicitacaoServico() };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização da solicitacaoServico.");
            e.printStackTrace();
        }
    }

    public void atualizaSituacao(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + getTableName() + " SET situacao = ? WHERE (idsolicitacaoservico = ?)");
        Object[] params = { solicitacaoServicoDto.getSituacao(), solicitacaoServicoDto.getIdSolicitacaoServico() };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização da solicitacaoServico.");
            e.printStackTrace();
        }
    }

    public void atualizaUrgenciaImpacto(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + getTableName() + " SET urgencia = ?, impacto = ?, idprioridade = ? WHERE (idsolicitacaoservico = ?)");
        Object[] params = { solicitacaoServicoDto.getUrgencia(), solicitacaoServicoDto.getImpacto(), solicitacaoServicoDto.getIdPrioridade(), solicitacaoServicoDto.getIdSolicitacaoServico() };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização da solicitacaoServico.");
            e.printStackTrace();
        }
    }

    public SolicitacaoServicoDTO buscarNumeroItemTrabalhoPorNumeroSolicitacao(int idSolicitacao) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT bpm_itemtrabalhofluxo.iditemtrabalho as idItemFluxoTrabalho FROM ");
        sql.append("solicitacaoservico solicitacaoservico ");
        sql.append("INNER JOIN execucaosolicitacao ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("INNER JOIN bpm_itemtrabalhofluxo ON execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia ");
        sql.append("INNER JOIN usuario ON bpm_itemtrabalhofluxo.idresponsavelatual = usuario.idusuario WHERE solicitacaoservico.idsolicitacaoservico = ? ");
        if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)) {
            sql.append("ORDER BY bpm_itemtrabalhofluxo.iditemtrabalho desc TOP 1 ");
        } else if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE)) {
            sql.append("ORDER BY bpm_itemtrabalhofluxo.iditemtrabalho desc ROWNUM 1 ");
        } else if (((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)) || (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase()
                .equalsIgnoreCase(SQLConfig.MYSQL)))) {
            sql.append(" ORDER BY bpm_itemtrabalhofluxo.iditemtrabalho desc LIMIT 1 ");
        }
        parametro.add(idSolicitacao);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idItemFluxoTrabalho");

        if (list != null && !list.isEmpty()) {
            return (SolicitacaoServicoDTO) this.listConvertion(getBean(), list, listRetorno).get(0);
        } else {
            return null;
        }
    }

    public boolean confirmaEncerramento(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO, Integer idElemento) throws Exception {
        List parametro = new ArrayList();
        StringBuilder sb = new StringBuilder();

        sb.append("select * ");
        sb.append("from bpm_itemtrabalhofluxo ");
        sb.append("where idinstancia = ?  ");
        sb.append("and idelemento = ? ");

        parametro.add(relatorioQuantitativoRetornoDTO.getIdInstancia());
        parametro.add(idElemento);

        List listaDados = this.execSQL(sb.toString(), parametro.toArray());
        if (listaDados != null && listaDados.size() == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean existeSolicitacaoServico(SolicitacaoServicoDTO solicitacaoservico) throws Exception {

        List parametro = new ArrayList();
        List list = new ArrayList();
        String sql = "select idsolicitacaoservico From " + getTableName() + " where idsolicitacaoservico = ?";
        parametro.add(solicitacaoservico.getIdSolicitacaoServico());
        list = this.execSQL(sql, parametro.toArray());
        if (list != null && !list.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public List<TarefaFluxoDTO> filtrarElementosDaLista(Collection<TarefaFluxoDTO> listTarefa, GerenciamentoServicosDTO dto) throws Exception {
        String tipoVisualizacao = dto.getTipoVisualizacao() == null ? "" : dto.getTipoVisualizacao();
        String situacaoSla = dto.getSituacaoSla() == null ? "" : dto.getSituacaoSla();
        List<TarefaFluxoDTO> listaRetornoTipoVisualizacao = new ArrayList<TarefaFluxoDTO>();
        List<TarefaFluxoDTO> listaRetornoSituacaoSla = new ArrayList<TarefaFluxoDTO>();
        List<TarefaFluxoDTO> listaRetornoFinal = new ArrayList<TarefaFluxoDTO>();

        // tipo de visualização
        for (TarefaFluxoDTO tarefaFluxoDTO : listTarefa) {
            if (tipoVisualizacao.equals("possoExecutar")) {
                if (tarefaFluxoDTO.isSomenteAcompanhamento() == false) {
                    listaRetornoTipoVisualizacao.add(tarefaFluxoDTO);
                }
            } else if (tipoVisualizacao.equals("possoVisualizar")) {
                if (tarefaFluxoDTO.isSomenteAcompanhamento() == true) {
                    listaRetornoTipoVisualizacao.add(tarefaFluxoDTO);
                }
            } else {
                listaRetornoTipoVisualizacao.add(tarefaFluxoDTO);
            }
        }
        // tipo de Sla
        for (TarefaFluxoDTO tarefaFluxoDTO : listaRetornoTipoVisualizacao) {
            SolicitacaoServicoDTO solicitacaoDto = (SolicitacaoServicoDTO) tarefaFluxoDTO.getSolicitacaoDto();
            ElementoFluxoDTO eletmentoFluxoDto = tarefaFluxoDTO.getElementoFluxoDto();

            boolean aCombinar;
            if ((solicitacaoDto.getPrazoHH().equals(0) || solicitacaoDto.getPrazoHH() == null) && (solicitacaoDto.getPrazoMM().equals(0) || solicitacaoDto.getPrazoMM() == null)
                    && !solicitacaoDto.getSituacao().equals(SituacaoSolicitacaoServico.Suspensa.name())) {
                aCombinar = true;
            } else {
                aCombinar = false;
            }

            if (situacaoSla.equals("vencido")) {
                if (tarefaFluxoDTO.getDataHoraFinalizacao() != null) {
                    if (tarefaFluxoDTO.getDataHoraLimite() != null && tarefaFluxoDTO.getDataHoraFinalizacao().after(tarefaFluxoDTO.getDataHoraLimite())
                            && !solicitacaoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name()) && solicitacaoDto.getSituacaoSLA().equals(SituacaoSLA.A.name())
                            && aCombinar != true) {
                        listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                    }
                } else {
                    if (tarefaFluxoDTO.getDataHoraLimite() != null) {
                        if (UtilDatas.getDataHoraAtual().compareTo(tarefaFluxoDTO.getDataHoraLimite()) > 0
                                && !solicitacaoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name()) && solicitacaoDto.getSituacaoSLA().equals("A") && aCombinar != true) {
                            listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                        }
                        // foi definido que a regra não é pra trazer solicitações suspensas, mesmo que estejam suspensas e vencidas
                        // else if (solicitacaoDto.getSituacaoSLA() != null && solicitacaoDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.S.name())
                        // && solicitacaoDto.getDataHoraSuspensaoSLA().compareTo(solicitacaoDto.getDataHoraLimite()) > 0) {
                        // listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                        // }
                    }
                }
            } else if (situacaoSla.equals("aguardandoAprovacao")) {
                if (eletmentoFluxoDto != null && eletmentoFluxoDto.getDocumentacao() != null && eletmentoFluxoDto.getDocumentacao().equalsIgnoreCase("Aprovar requisição")) {// Comparação feita com o nome da tarefa que está no Banco
                    listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                }
            } else if (situacaoSla.equals("avencer30min")) {
                if (tarefaFluxoDTO.getDataHoraLimite() != null) {
                    if (!UtilDatas.getDataHoraAtual().after(tarefaFluxoDTO.getDataHoraLimite()) && !solicitacaoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name())
                            && solicitacaoDto.getSituacaoSLA().equals("A") && aCombinar != true) {
                        double minutos = ((UtilDatas.calculaDiferencaTempoEmMilisegundos(tarefaFluxoDTO.getDataHoraLimite(), UtilDatas.getDataHoraAtual()) / 1000) / 60);
                        if (minutos <= 30) {
                            listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                        }
                    }
                }
            } else if (situacaoSla.equals("avencer60min")) {
                if (tarefaFluxoDTO.getDataHoraLimite() != null) {
                    if (!UtilDatas.getDataHoraAtual().after(tarefaFluxoDTO.getDataHoraLimite()) && !solicitacaoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name())
                            && solicitacaoDto.getSituacaoSLA().equals("A") && aCombinar != true) {
                        long minutos = ((UtilDatas.calculaDiferencaTempoEmMilisegundos(tarefaFluxoDTO.getDataHoraLimite(), UtilDatas.getDataHoraAtual()) / 1000) / 60);
                        if (minutos <= 60) {
                            listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                        }
                    }
                }
            } else if (situacaoSla.equals("avencer90min")) {
                if (tarefaFluxoDTO.getDataHoraLimite() != null) {
                    if (!UtilDatas.getDataHoraAtual().after(tarefaFluxoDTO.getDataHoraLimite()) && !solicitacaoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name())
                            && solicitacaoDto.getSituacaoSLA().equals("A") && aCombinar != true) {
                        double minutos = ((UtilDatas.calculaDiferencaTempoEmMilisegundos(tarefaFluxoDTO.getDataHoraLimite(), UtilDatas.getDataHoraAtual()) / 1000) / 60);
                        if (minutos <= 90) {
                            listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                        }
                    }
                }
            } else if (situacaoSla.equals("avencer2h")) {
                if (tarefaFluxoDTO.getDataHoraLimite() != null) {
                    if (!UtilDatas.getDataHoraAtual().after(tarefaFluxoDTO.getDataHoraLimite()) && !solicitacaoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name())
                            && solicitacaoDto.getSituacaoSLA().equals("A") && aCombinar != true) {
                        double minutos = ((UtilDatas.calculaDiferencaTempoEmMilisegundos(tarefaFluxoDTO.getDataHoraLimite(), UtilDatas.getDataHoraAtual()) / 1000) / 60);
                        if (minutos <= 120) {
                            listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                        }
                    }
                }

            } else if (situacaoSla.equals("avencer3h")) {
                if (tarefaFluxoDTO.getDataHoraLimite() != null) {
                    if (!UtilDatas.getDataHoraAtual().after(tarefaFluxoDTO.getDataHoraLimite()) && !solicitacaoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name())
                            && solicitacaoDto.getSituacaoSLA().equals("A") && aCombinar != true) {
                        double minutos = ((UtilDatas.calculaDiferencaTempoEmMilisegundos(tarefaFluxoDTO.getDataHoraLimite(), UtilDatas.getDataHoraAtual()) / 1000) / 60);
                        if (minutos <= 180) {
                            listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                        }
                    }
                }
            } else if (situacaoSla.equals("avencerHoje")) {
                if (tarefaFluxoDTO.getDataHoraLimite() != null) {
                    Date DataAtual = UtilDatas.getDataAtual();
                    Date DataLimit = new Date(tarefaFluxoDTO.getDataHoraLimite().getTime());
                    if (DataAtual.toString().equals(DataLimit.toString()) && !solicitacaoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name())
                            && solicitacaoDto.getSituacaoSLA().equals("A") && aCombinar != true) {
                        if (!UtilDatas.getDataHoraAtual().after(tarefaFluxoDTO.getDataHoraLimite())) {
                            listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                        }
                    }
                }
            } else if (situacaoSla.equals("avencerProxDiaUtil")) {
                CalendarioServiceEjb serviceCalendario = new CalendarioServiceEjb();
                CalendarioServiceEjb calendarioService = new CalendarioServiceEjb();

                CalendarioDTO calendarioDto = calendarioService.recuperaCalendario(solicitacaoDto.getIdCalendario());

                JornadaTrabalhoDTO jornadaDtoVerificaSeExiste = null;
                Timestamp diaUtilSeguinte = null;
                int cont = 0;
                do {
                    if (cont == 0) {
                        cont = 1;
                        diaUtilSeguinte = serviceCalendario.incrementaDias(UtilDatas.getDataHoraAtual(), 1);
                    } else {
                        diaUtilSeguinte = serviceCalendario.incrementaDias(diaUtilSeguinte, 1);
                    }

                    Date dataRef = new Date(diaUtilSeguinte.getTime());
                    jornadaDtoVerificaSeExiste = calendarioService.recuperaJornada(calendarioDto, dataRef, Util.getHoraDbl(UtilDatas.getHoraHHMM(diaUtilSeguinte)));
                } while (jornadaDtoVerificaSeExiste == null);
                if (tarefaFluxoDTO.getDataHoraLimite() != null) {
                    Date DataDiaSeguinte = new Date(diaUtilSeguinte.getTime());
                    Date DataLimit = new Date(tarefaFluxoDTO.getDataHoraLimite().getTime());
                    Date DataAtual = UtilDatas.getDataAtual();
                    if (jornadaDtoVerificaSeExiste != null && (DataDiaSeguinte.toString().equals(DataLimit.toString())) || (DataAtual.toString().equals(DataLimit.toString()))
                            && !solicitacaoDto.getSituacao().equalsIgnoreCase(SituacaoSolicitacaoServico.Suspensa.name()) && solicitacaoDto.getSituacaoSLA().equals("A") && aCombinar != true) {
                        if (!UtilDatas.getDataHoraAtual().after(tarefaFluxoDTO.getDataHoraLimite())) {
                            listaRetornoSituacaoSla.add(tarefaFluxoDTO);
                        }
                    }
                }

            } else {

                listaRetornoSituacaoSla.add(tarefaFluxoDTO);
            }
        }
        listaRetornoFinal = listaRetornoSituacaoSla;
        return listaRetornoFinal;
    }

    /**
     * @param lista
     * @param dto
     * @return
     * @throws Exception
     * @author bruno.aquino Filtra a lista de Solicitações de Acordo com os Filtros Selecionados
     */
    public void filtroExternoDosElementos(StringBuilder sql, GerenciamentoServicosDTO dto) throws Exception {
        String tipoVisualizacao = dto.getTipoVisualizacao() == null ? "" : dto.getTipoVisualizacao();
        String situacaoSla = dto.getSituacaoSla() == null ? "" : dto.getSituacaoSla();

        /* tipo de visualização */
        if (tipoVisualizacao.equals("possoExecutar")) {
            sql.append("");
        } else if (tipoVisualizacao.equals("possoVisualizar")) {
            sql.append("");
        }
        /* tipo de Sla */
        if (situacaoSla.equals("vencido")) {
            sql.append(" and sol.datahoralimite<CURRENT_TIMESTAMP ");
        } else if (situacaoSla.equals("aguardandoAprovacao")) {
            sql.append("");
        } else if (situacaoSla.equals("avencer30min")) {
            Date dt = new Date(System.currentTimeMillis());
            sql.append(" and " + dt.getTime() + " - sol.datahoralimite");
        } else if (situacaoSla.equals("avencer60min")) {
            sql.append("");
        } else if (situacaoSla.equals("avencer90min")) {
            sql.append("");
        } else if (situacaoSla.equals("avencer2h")) {
            sql.append("");
        } else if (situacaoSla.equals("avencer3h")) {
            sql.append("");
        } else if (situacaoSla.equals("avencerHoje")) {
            sql.append("");
        } else if (situacaoSla.equals("avencerProxDiaUtil")) {
            sql.append("");
        }
    }

    @Override
    public Collection find(IDto solicitacaoServicoDTO) throws PersistenceException {
        List ordenacao = new ArrayList();
        ordenacao.add(new Order("idSolicitacaoServico"));
        return super.find(solicitacaoServicoDTO, ordenacao);
    }

    public Collection<SolicitacaoServicoDTO> findByCodigoExterno(String codigoExterno) throws Exception {
        List condicao = new ArrayList();
        condicao.add(new Condition("codigoExterno", "=", codigoExterno));
        return super.findByCondition(condicao, null);
    }

    /**
     * Retorna Solicitações de Serviço associadas a Base de Conhecimento.
     *
     * @param baseConhecimentoDto
     * @return List<SolicitacaoServicoDTO>
     * @throws Exception
     * @author Vadoilo Damasceno
     */
    public List<SolicitacaoServicoDTO> findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("select solicitacaoservico.idsolicitacaoservico, servico.nomeservico ");
        sql.append("from solicitacaoservico ");
        sql.append("inner join conhecimentosolicitacaoservico on solicitacaoservico.idsolicitacaoservico = conhecimentosolicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        sql.append("inner join servico on servicocontrato.idservico = servico.idservico ");
        sql.append("where conhecimentosolicitacaoservico.idbaseconhecimento = ? ");

        parametro.add(baseConhecimentoDto.getIdBaseConhecimento());

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");

        if (list != null && !list.isEmpty()) {

            return this.listConvertion(getBean(), list, listRetorno);

        } else {

            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdContratoPaginada(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto, String paginacao, Integer pagAtual, Integer pagAtualAux,
            Integer totalPag, Integer quantidadePaginator, String campoPesquisa) throws Exception {
        if (strSGBDPrincipal == null) {
            strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;
            strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
        }

        List listRetorno = new ArrayList();

        StringBuilder sql = new StringBuilder();

        List listaTotal = new ArrayList();

        List parametros = new ArrayList();
        List parametros2 = new ArrayList();
        List parametros3 = new ArrayList();

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento, faseservico e empregados estava utilizando INNER
         * JOIN, isso fazia com que as solicitações que não possuem origem contrato, fase ou solicitante não fossem retornadas. Alterado para LEFT JOIN
         */

        // sql para Postgres e Mysql
        sql.append("SELECT tempoAtendimentoHH,tempoAtendimentoMM,datahorainicio, datahorafim, idsolicitacaoservico, nomeservico, unidade.nome, CASE WHEN solicitacaoservico.situacao = 'EmAndamento' THEN 'Em Andamento' ELSE solicitacaoservico.situacao END AS situacao, dataHoraSolicitacao, dataHoraLimite, nomeTipoDemandaServico, prazohh, prazomm, ");
        sql.append("solicitacaoservico.descricaoSemFormatacao, resposta, grupo.sigla, seqreabertura, empregado.nome, faseservico.nomefase, origematendimento.descricao,prioridade.nomeprioridade, usuario.nome, contratos.numero,  idUsuarioResponsavelAtual ");
        sql.append("FROM solicitacaoservico ");
        sql.append("INNER JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
        sql.append("INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
            sql.append("AND (idcontrato = ?) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdContrato());
        }
        sql.append("INNER JOIN servico ON servicocontrato.idservico = servico.idservico LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual ");
        sql.append("INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");
        sql.append("LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante ");
        sql.append("INNER JOIN usuario usuario on usuario.idusuario = solicitacaoservico.idresponsavel ");
        sql.append("LEFT JOIN  faseservico faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual ");
        sql.append("LEFT JOIN  origematendimento origematendimento  ON origematendimento.idorigem = solicitacaoservico.idorigem ");
        sql.append("INNER JOIN prioridade prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade ");
        sql.append("LEFT JOIN unidade unidade ON unidade.idunidade = solicitacaoservico.idunidade ");
        sql.append("LEFT JOIN contatosolicitacaoservico contatosolicitacaoservico ON contatosolicitacaoservico.idcontatosolicitacaoservico = solicitacaoservico.idcontatosolicitacaoservico ");
        sql.append("WHERE (UPPER(tipodemandaservico.classificacao) = UPPER('*') OR '*' = '*') ");

        if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
            sql.append("AND (solicitacaoservico.idSolicitacaoServico = ?) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        }
        if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
            sql.append("AND (servicocontrato.idservico = ?) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdServico());
        }
        if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
            sql.append("AND (solicitacaoservico.idprioridade = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdPrioridade());
        }
        if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
            sql.append("AND (solicitacaoservico.idorigem = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdOrigem());

        }
        if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
            sql.append("AND (solicitacaoservico.idunidade = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdUnidade());
        }
        if (pesquisaSolicitacaoServicoDto.getIdLocalidade() != null) {
            sql.append("AND (contatosolicitacaoservico.idlocalidade = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdLocalidade());
        }
        if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
            sql.append("AND (solicitacaoservico.idfaseatual = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
            sql.append("AND (solicitacaoservico.idgrupoatual = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());

        }
        if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equalsIgnoreCase("")) {
            sql.append("AND (solicitacaoservico.situacao = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getSituacao());
        }
        if (pesquisaSolicitacaoServicoDto.getIdSolicitante() != null) {
            sql.append("AND (solicitacaoservico.idsolicitante = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdSolicitante());
        }
        if (pesquisaSolicitacaoServicoDto.getIdItemConfiguracao() != null) {
            sql.append("AND (solicitacaoservico.iditemconfiguracao = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdItemConfiguracao());
        }
        if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
            sql.append("AND (solicitacaoservico.idTipoDemandaServico = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
        }
        if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
            sql.append("AND (solicitacaoservico.idResponsavel = ?  ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdResponsavel());
        }

        if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
            sql.append("AND (solicitacaoservico.idusuarioresponsavelatual = ?  ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
            sql.append("AND (servico.idservico = ?  ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdServico());
        }
        if (pesquisaSolicitacaoServicoDto.getPalavraChave() != null && !pesquisaSolicitacaoServicoDto.getPalavraChave().equalsIgnoreCase("")) {
            if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                sql.append("AND (solicitacaoservico.descricao like '%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%')  ");
                // parametros.add(pesquisaSolicitacaoServicoDto.getPalavraChave());
            } else {
                sql.append("AND (UPPER(solicitacaoservico.descricao) like UPPER('%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%') ) ");
            }
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametros.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicio()));
            parametros.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFim()));
        } else {
            sql.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getDataInicio());
            parametros.add(transformaHoraFinal(pesquisaSolicitacaoServicoDto.getDataFim()));
        }
        if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null && !StringUtils.equalsIgnoreCase(pesquisaSolicitacaoServicoDto.getDataInicioFechamento().toString(), "1970-01-01")) {
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                parametros.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicioFechamento()));
                parametros.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFimFechamento()));
            } else {
                sql.append("AND (solicitacaoservico.datahorafim BETWEEN ? AND ?) ");
                parametros.add(pesquisaSolicitacaoServicoDto.getDataInicioFechamento());
                parametros.add(transformaHoraFinal(pesquisaSolicitacaoServicoDto.getDataFimFechamento()));
            }

        }

        if (pesquisaSolicitacaoServicoDto.getOrdenacao() != null) {
            sql.append(" ORDER BY " + pesquisaSolicitacaoServicoDto.getOrdenacao() + "");
        }

        listaTotal = this.execSQL(sql.toString(), parametros.toArray());

        if (quantidadePaginator != null) {
            if (strSGBDPrincipal.equalsIgnoreCase("POSTGRESQL") || strSGBDPrincipal.equalsIgnoreCase("POSTGRES")) {
                sql.append(" LIMIT " + quantidadePaginator + " OFFSET " + pagAtual);
            } else if (strSGBDPrincipal.equalsIgnoreCase("MYSQL")) {
                sql.append(" LIMIT " + pagAtual + ", " + quantidadePaginator);
            }

            // para Oracle o sql de paginação é diferente
            else if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
                Integer quantidadePaginator2 = new Integer(0);
                sql.setLength(0);
                quantidadePaginator2 = quantidadePaginator + pagAtual;

                /*
                 * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento, faseservico e empregados estava utilizando
                 * INNER JOIN, isso fazia com que as solicitações que não possuem origem contrato, fase ou solicitante não fossem retornadas. Alterado para LEFT JOIN
                 */

                sql.append("SELECT tempoAtendimentoHH,tempoAtendimentoMM,datahorainicio, datahorafim, idsolicitacaoservico, nomeservico, unidade.nome AS NOMEUNIDADE, CASE WHEN solicitacaoservico.situacao = 'EmAndamento' THEN 'Em Andamento' ELSE solicitacaoservico.situacao END AS situacao, dataHoraSolicitacao, dataHoraLimite, nomeTipoDemandaServico, prazohh, prazomm, ");
                sql.append("solicitacaoservico.descricaoSemFormatacao, resposta, grupo.sigla, seqreabertura, empregado.nome AS NOMEEMPREGADO, faseservico.nomefase, origematendimento.descricao,prioridade.nomeprioridade, usuario.nome AS NOMEUSUARIO, contratos.numero, idUsuarioResponsavelAtual  ");
                sql.append("FROM solicitacaoservico ");
                sql.append("INNER JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
                sql.append("INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
                if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
                    sql.append("AND (idcontrato = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdContrato());
                }
                sql.append("INNER JOIN servico ON servicocontrato.idservico = servico.idservico LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual ");
                sql.append("INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");
                sql.append("LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante ");
                sql.append("inner join usuario usuario on usuario.idusuario = solicitacaoservico.idresponsavel ");
                sql.append("LEFT JOIN  faseservico faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual ");
                sql.append("LEFT JOIN  origematendimento origematendimento  ON origematendimento.idorigem = solicitacaoservico.idorigem ");
                sql.append("INNER JOIN prioridade prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade ");
                sql.append("LEFT JOIN unidade unidade ON unidade.idunidade = solicitacaoservico.idunidade ");
                sql.append("LEFT JOIN contatosolicitacaoservico contatosolicitacaoservico ON contatosolicitacaoservico.idcontatosolicitacaoservico = solicitacaoservico.idcontatosolicitacaoservico ");
                sql.append("WHERE (UPPER(tipodemandaservico.classificacao) = UPPER('*') OR '*' = '*') ");

                if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
                    sql.append("AND (solicitacaoservico.idSolicitacaoServico = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
                }
                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servicocontrato.idservico = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
                    sql.append("AND (solicitacaoservico.idprioridade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdPrioridade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
                    sql.append("AND (solicitacaoservico.idorigem = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdOrigem());

                }
                if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
                    sql.append("AND (solicitacaoservico.idunidade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdUnidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdLocalidade() != null) {
                    sql.append("AND (contatosolicitacaoservico.idlocalidade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdLocalidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
                    sql.append("AND (solicitacaoservico.idfaseatual = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
                }
                if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
                    sql.append("AND (solicitacaoservico.idgrupoatual = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());

                }
                if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equalsIgnoreCase("")) {
                    sql.append("AND (solicitacaoservico.situacao = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getSituacao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdSolicitante() != null) {
                    sql.append("AND (solicitacaoservico.idsolicitante = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdSolicitante());
                }
                if (pesquisaSolicitacaoServicoDto.getIdItemConfiguracao() != null) {
                    sql.append("AND (solicitacaoservico.iditemconfiguracao = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdItemConfiguracao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
                    sql.append("AND (solicitacaoservico.idTipoDemandaServico = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
                    sql.append("AND (solicitacaoservico.idResponsavel = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdResponsavel());
                }

                if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
                    sql.append("AND (solicitacaoservico.idusuarioresponsavelatual = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
                }

                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servico.idservico = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getPalavraChave() != null && !pesquisaSolicitacaoServicoDto.getPalavraChave().equalsIgnoreCase("")) {
                    sql.append("AND (UPPER(solicitacaoservico.descricao) like UPPER('%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%') ) ");
                    // parametros.add(pesquisaSolicitacaoServicoDto.getPalavraChave());
                }
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                    sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicio()));
                    parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFim()));
                }

                if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null && !StringUtils.equalsIgnoreCase(pesquisaSolicitacaoServicoDto.getDataInicioFechamento().toString(), "1970-01-01")) {
                    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                        sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicioFechamento()));
                        parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFimFechamento()));
                    }

                }

                /*
                 * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento, faseservico e empregados estava utilizando
                 * INNER JOIN, isso fazia com que as solicitações que não possuem origem contrato, fase ou solicitante não fossem retornadas. Alterado para LEFT JOIN
                 */

                sql.append(" AND IDSOLICITACAOSERVICO IN(SELECT IDSOLICITACAOSERVICO FROM(SELECT row_.*, ROWNUM ROWNUM_ FROM (SELECT COUNT(*) OVER() AS TOTALROWCOUNT, ");
                sql.append(" tempoAtendimentoMM,datahorainicio, datahorafim, idsolicitacaoservico, nomeservico, unidade.nome AS NOMEUNIDADE, CASE WHEN solicitacaoservico.situacao = 'EmAndamento' THEN 'Em Andamento' ELSE solicitacaoservico.situacao END AS situacao, dataHoraSolicitacao, dataHoraLimite, nomeTipoDemandaServico, prazohh, prazomm, ");
                sql.append("solicitacaoservico.descricaoSemFormatacao, resposta, grupo.sigla, seqreabertura, empregado.nome AS NOMEEMPREGADO, faseservico.nomefase, origematendimento.descricao,prioridade.nomeprioridade, usuario.nome AS NOMEUSUARIO, contratos.numero, idUsuarioResponsavelAtual  ");
                sql.append("FROM solicitacaoservico ");
                sql.append("INNER JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
                sql.append("INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
                if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
                    sql.append("AND (idcontrato = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdContrato());
                }
                sql.append("INNER JOIN servico ON servicocontrato.idservico = servico.idservico LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual ");
                sql.append("INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");
                sql.append("LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante ");
                sql.append("inner join usuario usuario on usuario.idusuario = solicitacaoservico.idresponsavel ");
                sql.append("LEFT JOIN  faseservico faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual ");
                sql.append("LEFT JOIN  origematendimento origematendimento  ON origematendimento.idorigem = solicitacaoservico.idorigem ");
                sql.append("INNER JOIN prioridade prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade ");
                sql.append("LEFT JOIN unidade unidade ON unidade.idunidade = solicitacaoservico.idunidade ");
                sql.append("LEFT JOIN contatosolicitacaoservico contatosolicitacaoservico ON contatosolicitacaoservico.idcontatosolicitacaoservico = solicitacaoservico.idcontatosolicitacaoservico ");
                sql.append("WHERE (UPPER(tipodemandaservico.classificacao) = UPPER('*') OR '*' = '*') ");

                if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
                    sql.append("AND (solicitacaoservico.idSolicitacaoServico = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
                }
                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servicocontrato.idservico = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
                    sql.append("AND (solicitacaoservico.idprioridade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdPrioridade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
                    sql.append("AND (solicitacaoservico.idorigem = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdOrigem());

                }
                if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
                    sql.append("AND (solicitacaoservico.idunidade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdUnidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdLocalidade() != null) {
                    sql.append("AND (contatosolicitacaoservico.idlocalidade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdLocalidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
                    sql.append("AND (solicitacaoservico.idfaseatual = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
                }
                if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
                    sql.append("AND (solicitacaoservico.idgrupoatual = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());

                }
                if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equalsIgnoreCase("")) {
                    sql.append("AND (solicitacaoservico.situacao = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getSituacao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdSolicitante() != null) {
                    sql.append("AND (solicitacaoservico.idsolicitante = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdSolicitante());
                }
                if (pesquisaSolicitacaoServicoDto.getIdItemConfiguracao() != null) {
                    sql.append("AND (solicitacaoservico.iditemconfiguracao = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdItemConfiguracao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
                    sql.append("AND (solicitacaoservico.idTipoDemandaServico = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
                    sql.append("AND (solicitacaoservico.idResponsavel = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdResponsavel());
                }

                if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
                    sql.append("AND (solicitacaoservico.idusuarioresponsavelatual = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
                }

                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servico.idservico = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getPalavraChave() != null && !pesquisaSolicitacaoServicoDto.getPalavraChave().equalsIgnoreCase("")) {
                    sql.append("AND (UPPER(solicitacaoservico.descricao) like UPPER('%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%') ) ");
                    // parametros.add(pesquisaSolicitacaoServicoDto.getPalavraChave());
                }
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                    sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicio()));
                    parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFim()));
                }

                if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null && !StringUtils.equalsIgnoreCase(pesquisaSolicitacaoServicoDto.getDataInicioFechamento().toString(), "1970-01-01")) {
                    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                        sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicioFechamento()));
                        parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFimFechamento()));
                    }

                }

                if (pesquisaSolicitacaoServicoDto.getOrdenacao() != null) {
                    sql.append(" ORDER BY " + pesquisaSolicitacaoServicoDto.getOrdenacao() + "");
                }

                sql.append(") row_ WHERE ROWNUM <= " + quantidadePaginator2 + " ) WHERE ROWNUM_ > " + pagAtual + ")");
            }

            // para sqlserver o sql é diferente
            else if (strSGBDPrincipal.equalsIgnoreCase("sqlserver")) {
                Integer quantidadePaginator2 = new Integer(0);
                sql.setLength(0);
                quantidadePaginator2 = quantidadePaginator + pagAtual;

                /*
                 * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento, faseservico e empregados estava utilizando
                 * INNER JOIN, isso fazia com que as solicitações que não possuem origem contrato, fase ou solicitante não fossem retornadas. Alterado para LEFT JOIN
                 */

                sql.append(";WITH TabelaTemporaria AS ( ");
                sql.append(" SELECT tempoatendimentoHH, tempoAtendimentoMM,datahorainicio, datahorafim, idsolicitacaoservico, nomeservico, unidade.nome as nomeunidade, ");
                sql.append(" CASE WHEN solicitacaoservico.situacao = 'EmAndamento' THEN 'Em Andamento' ELSE solicitacaoservico.situacao END AS situacao, dataHoraSolicitacao, dataHoraLimite, ");
                sql.append(" nomeTipoDemandaServico, prazohh, prazomm, solicitacaoservico.descricaoSemFormatacao, resposta, grupo.sigla, seqreabertura, empregado.nome as nomeempregado, faseservico.nomefase, ");
                sql.append(" origematendimento.descricao,prioridade.nomeprioridade, usuario.nome as nomeusuario, contratos.numero, idUsuarioResponsavelAtual, ROW_NUMBER() OVER (ORDER BY solicitacaoservico.idsolicitacaoservico) AS Row ");
                sql.append("        FROM solicitacaoservico ");
                sql.append("        INNER JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
                sql.append("        INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
                sql.append("        INNER JOIN servico ON servicocontrato.idservico = servico.idservico ");
                sql.append("        LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual ");
                sql.append("        INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");
                sql.append("		LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante ");
                sql.append("		inner join usuario usuario on usuario.idusuario = solicitacaoservico.idresponsavel ");
                sql.append("		LEFT JOIN  faseservico faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual ");
                sql.append("		LEFT JOIN  origematendimento origematendimento  ON origematendimento.idorigem = solicitacaoservico.idorigem ");
                sql.append("		INNER JOIN prioridade prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade ");
                sql.append("		LEFT JOIN unidade unidade ON unidade.idunidade = solicitacaoservico.idunidade ");
                sql.append("        LEFT JOIN contatosolicitacaoservico contatosolicitacaoservico ON contatosolicitacaoservico.idcontatosolicitacaoservico = solicitacaoservico.idcontatosolicitacaoservico ");
                sql.append("        WHERE (UPPER(tipodemandaservico.classificacao) = UPPER('*') OR '*' = '*') ");

                if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
                    sql.append("AND (solicitacaoservico.idSolicitacaoServico = ?) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
                }
                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servicocontrato.idservico = ?) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
                    sql.append("AND (solicitacaoservico.idprioridade = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdPrioridade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
                    sql.append("AND (solicitacaoservico.idorigem = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdOrigem());

                }
                if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
                    sql.append("AND (solicitacaoservico.idunidade = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdUnidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdLocalidade() != null) {
                    sql.append("AND (contatosolicitacaoservico.idlocalidade = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdLocalidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
                    sql.append("AND (solicitacaoservico.idfaseatual = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
                }
                if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
                    sql.append("AND (solicitacaoservico.idgrupoatual = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());

                }
                if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equalsIgnoreCase("")) {
                    sql.append("AND (solicitacaoservico.situacao = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getSituacao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdSolicitante() != null) {
                    sql.append("AND (solicitacaoservico.idsolicitante = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdSolicitante());
                }
                if (pesquisaSolicitacaoServicoDto.getIdItemConfiguracao() != null) {
                    sql.append("AND (solicitacaoservico.iditemconfiguracao = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdItemConfiguracao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
                    sql.append("AND (solicitacaoservico.idTipoDemandaServico = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
                    sql.append("AND (solicitacaoservico.idResponsavel = ?  ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdResponsavel());
                }

                if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
                    sql.append("AND (solicitacaoservico.idusuarioresponsavelatual = ?  ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
                }

                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servico.idservico = ?  ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getPalavraChave() != null && !pesquisaSolicitacaoServicoDto.getPalavraChave().equalsIgnoreCase("")) {
                    sql.append("AND (solicitacaoservico.descricao like '%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%' ) ");
                }

                if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null && !StringUtils.equalsIgnoreCase(pesquisaSolicitacaoServicoDto.getDataInicioFechamento().toString(), "1970-01-01")) {
                    sql.append(" AND (solicitacaoservico.datahorafim BETWEEN ? AND ?) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getDataInicioFechamento());
                    parametros3.add(pesquisaSolicitacaoServicoDto.getDataFimFechamento());
                }

                sql.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ");
                parametros3.add(pesquisaSolicitacaoServicoDto.getDataInicio());
                parametros3.add(transformaHoraFinal(pesquisaSolicitacaoServicoDto.getDataFim()));

                sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row>" + pagAtual + " and Row<" + (quantidadePaginator2 + 1) + " ");

            }
        }

        if (listaTotal != null) {
            pesquisaSolicitacaoServicoDto.setTotalItens(listaTotal.size());
            if (listaTotal.size() > quantidadePaginator) {
                totalPag = ((listaTotal.size() / quantidadePaginator));
                if (listaTotal.size() % quantidadePaginator != 0) {
                    totalPag = totalPag + 1;
                }
            } else {
                totalPag = 1;
            }
        }
        pesquisaSolicitacaoServicoDto.setTotalPagina(totalPag);
        List lista;

        if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
            lista = execSQL(sql.toString().toUpperCase(), parametros2.toArray());
        } else if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
            lista = execSQL(sql.toString().toUpperCase(), parametros3.toArray());
        } else {
            lista = execSQL(sql.toString().toUpperCase(), parametros.toArray());
        }

        if (lista == null || lista.size() == 0) {
            TransactionControler tc = this.getTransactionControler();
            if (tc != null) {
                tc.close();
            }
            return null;
        }

        List result = new ArrayList();
        if (lista == null || lista.size() == 0) {
            TransactionControler tc = this.getTransactionControler();
            if (tc != null) {
                tc.close();
            }

            return result;
        }

        TransactionControler tc = this.getTransactionControler();
        if (tc != null) {
            tc.close();
        }

        listRetorno.add("tempoAtendimentoHH");
        listRetorno.add("tempoAtendimentoMM");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraFim");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("nomeUnidadeSolicitante");
        listRetorno.add("situacao");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("descricaoSemFormatacao");
        listRetorno.add("resposta");
        listRetorno.add("siglaGrupo");
        listRetorno.add("seqReabertura");
        listRetorno.add("nomeSolicitante");
        listRetorno.add("faseAtual");
        listRetorno.add("origem");
        listRetorno.add("prioridade");
        listRetorno.add("responsavel");
        listRetorno.add("contrato");
        listRetorno.add("idUsuarioResponsavelAtual");

        List listaSolicitacoes = engine.listConvertion(getBean(), lista, listRetorno);

        return listaSolicitacoes;
    }

    
    public Collection<SolicitacaoServicoDTO> findByIdContratoPaginada(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto, String paginacao, Integer pagAtual, Integer pagAtualAux,
            Integer totalPag, Integer quantidadePaginator, String campoPesquisa, Collection<UnidadeDTO> unidadesColaborador) throws Exception {
        if (strSGBDPrincipal == null) {
            strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;
            strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
        }

        List listRetorno = new ArrayList();

        StringBuilder sql = new StringBuilder();

        List listaTotal = new ArrayList();

        List parametros = new ArrayList();
        List parametros2 = new ArrayList();
        List parametros3 = new ArrayList();

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento, faseservico e empregados estava utilizando INNER
         * JOIN, isso fazia com que as solicitações que não possuem origem contrato, fase ou solicitante não fossem retornadas. Alterado para LEFT JOIN
         */

        // sql para Postgres e Mysql
        sql.append("SELECT tempoAtendimentoHH,tempoAtendimentoMM,datahorainicio, datahorafim, idsolicitacaoservico, nomeservico, unidade.nome, CASE WHEN solicitacaoservico.situacao = 'EmAndamento' THEN 'Em Andamento' ELSE solicitacaoservico.situacao END AS situacao, dataHoraSolicitacao, dataHoraLimite, nomeTipoDemandaServico, prazohh, prazomm, ");
        sql.append("solicitacaoservico.descricaoSemFormatacao, resposta, grupo.sigla, seqreabertura, empregado.nome, faseservico.nomefase, origematendimento.descricao,prioridade.nomeprioridade, usuario.nome, contratos.numero,  idUsuarioResponsavelAtual ");
        sql.append("FROM solicitacaoservico ");
        sql.append("INNER JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
        sql.append("INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
            sql.append("AND (idcontrato = ?) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdContrato());
        }
        sql.append("INNER JOIN servico ON servicocontrato.idservico = servico.idservico LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual ");
        sql.append("INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");
        sql.append("LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante ");
        sql.append("INNER JOIN usuario usuario on usuario.idusuario = solicitacaoservico.idresponsavel ");
        sql.append("LEFT JOIN  faseservico faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual ");
        sql.append("LEFT JOIN  origematendimento origematendimento  ON origematendimento.idorigem = solicitacaoservico.idorigem ");
        sql.append("INNER JOIN prioridade prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade ");
        sql.append("LEFT JOIN unidade unidade ON unidade.idunidade = solicitacaoservico.idunidade ");
        sql.append("LEFT JOIN contatosolicitacaoservico contatosolicitacaoservico ON contatosolicitacaoservico.idcontatosolicitacaoservico = solicitacaoservico.idcontatosolicitacaoservico ");
        sql.append("WHERE (UPPER(tipodemandaservico.classificacao) = UPPER('*') OR '*' = '*') ");

        if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
            sql.append("AND (solicitacaoservico.idSolicitacaoServico = ?) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        }
        if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
            sql.append("AND (servicocontrato.idservico = ?) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdServico());
        }
        if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
            sql.append("AND (solicitacaoservico.idprioridade = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdPrioridade());
        }
        if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
            sql.append("AND (solicitacaoservico.idorigem = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdOrigem());

        }
        if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
            sql.append("AND (solicitacaoservico.idunidade = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdUnidade());
        }
        if (pesquisaSolicitacaoServicoDto.getIdLocalidade() != null) {
            sql.append("AND (contatosolicitacaoservico.idlocalidade = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdLocalidade());
        }
        if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
            sql.append("AND (solicitacaoservico.idfaseatual = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
            sql.append("AND (solicitacaoservico.idgrupoatual = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());

        }
        if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equalsIgnoreCase("")) {
            sql.append("AND (solicitacaoservico.situacao = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getSituacao());
        }
        if (pesquisaSolicitacaoServicoDto.getIdSolicitante() != null) {
            sql.append("AND (solicitacaoservico.idsolicitante = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdSolicitante());
        }
        if (pesquisaSolicitacaoServicoDto.getIdItemConfiguracao() != null) {
            sql.append("AND (solicitacaoservico.iditemconfiguracao = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdItemConfiguracao());
        }
        if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
            sql.append("AND (solicitacaoservico.idTipoDemandaServico = ? ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
        }
        if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
            sql.append("AND (solicitacaoservico.idResponsavel = ?  ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdResponsavel());
        }

        if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
            sql.append("AND (solicitacaoservico.idusuarioresponsavelatual = ?  ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
            sql.append("AND (servico.idservico = ?  ) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdServico());
        }
        if (pesquisaSolicitacaoServicoDto.getPalavraChave() != null && !pesquisaSolicitacaoServicoDto.getPalavraChave().equalsIgnoreCase("")) {
            if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
                sql.append("AND (solicitacaoservico.descricao like '%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%')  ");
                // parametros.add(pesquisaSolicitacaoServicoDto.getPalavraChave());
            } else {
                sql.append("AND (UPPER(solicitacaoservico.descricao) like UPPER('%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%') ) ");
            }
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametros.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicio()));
            parametros.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFim()));
        } else {
            sql.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ");
            parametros.add(pesquisaSolicitacaoServicoDto.getDataInicio());
            parametros.add(transformaHoraFinal(pesquisaSolicitacaoServicoDto.getDataFim()));
        }
        if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null && !StringUtils.equalsIgnoreCase(pesquisaSolicitacaoServicoDto.getDataInicioFechamento().toString(), "1970-01-01")) {
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                parametros.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicioFechamento()));
                parametros.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFimFechamento()));
            } else {
                sql.append("AND (solicitacaoservico.datahorafim BETWEEN ? AND ?) ");
                parametros.add(pesquisaSolicitacaoServicoDto.getDataInicioFechamento());
                parametros.add(transformaHoraFinal(pesquisaSolicitacaoServicoDto.getDataFimFechamento()));
            }

        }
        
        /**
         * @author Cristian: obtém as unidades que o usuário logado tem permissão para acessar
         */
        
        if ((unidadesColaborador!=null) && (unidadesColaborador.size()>0)) {
            sql.append("AND (solicitacaoservico.idunidade in ( ");
            
            
        	StringBuilder sb = new StringBuilder();
        	for (UnidadeDTO n : unidadesColaborador) { 
        	    if (sb.length() > 0) sb.append(',');
        	    sb.append(String.valueOf(n.getIdUnidade()));
        	}
        	  
        	sql.append(sb.toString()+") ) ");
        	
        }

        if ((pesquisaSolicitacaoServicoDto.getOrdenacao() != null) && (pesquisaSolicitacaoServicoDto.getOrdenacao().trim().isEmpty()==false)) {
            sql.append(" ORDER BY " + pesquisaSolicitacaoServicoDto.getOrdenacao() + "");
        }

        listaTotal = this.execSQL(sql.toString(), parametros.toArray());

        if (quantidadePaginator != null) {
            if (strSGBDPrincipal.equalsIgnoreCase("POSTGRESQL") || strSGBDPrincipal.equalsIgnoreCase("POSTGRES")) {
                sql.append(" LIMIT " + quantidadePaginator + " OFFSET " + pagAtual);
            } else if (strSGBDPrincipal.equalsIgnoreCase("MYSQL")) {
                sql.append(" LIMIT " + pagAtual + ", " + quantidadePaginator);
            }

            // para Oracle o sql de paginação é diferente
            else if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
                Integer quantidadePaginator2 = new Integer(0);
                sql.setLength(0);
                quantidadePaginator2 = quantidadePaginator + pagAtual;

                /*
                 * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento, faseservico e empregados estava utilizando
                 * INNER JOIN, isso fazia com que as solicitações que não possuem origem contrato, fase ou solicitante não fossem retornadas. Alterado para LEFT JOIN
                 */

                sql.append("SELECT tempoAtendimentoHH,tempoAtendimentoMM,datahorainicio, datahorafim, idsolicitacaoservico, nomeservico, unidade.nome AS NOMEUNIDADE, CASE WHEN solicitacaoservico.situacao = 'EmAndamento' THEN 'Em Andamento' ELSE solicitacaoservico.situacao END AS situacao, dataHoraSolicitacao, dataHoraLimite, nomeTipoDemandaServico, prazohh, prazomm, ");
                sql.append("solicitacaoservico.descricaoSemFormatacao, resposta, grupo.sigla, seqreabertura, empregado.nome AS NOMEEMPREGADO, faseservico.nomefase, origematendimento.descricao,prioridade.nomeprioridade, usuario.nome AS NOMEUSUARIO, contratos.numero, idUsuarioResponsavelAtual  ");
                sql.append("FROM solicitacaoservico ");
                sql.append("INNER JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
                sql.append("INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
                if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
                    sql.append("AND (idcontrato = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdContrato());
                }
                sql.append("INNER JOIN servico ON servicocontrato.idservico = servico.idservico LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual ");
                sql.append("INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");
                sql.append("LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante ");
                sql.append("inner join usuario usuario on usuario.idusuario = solicitacaoservico.idresponsavel ");
                sql.append("LEFT JOIN  faseservico faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual ");
                sql.append("LEFT JOIN  origematendimento origematendimento  ON origematendimento.idorigem = solicitacaoservico.idorigem ");
                sql.append("INNER JOIN prioridade prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade ");
                sql.append("LEFT JOIN unidade unidade ON unidade.idunidade = solicitacaoservico.idunidade ");
                sql.append("LEFT JOIN contatosolicitacaoservico contatosolicitacaoservico ON contatosolicitacaoservico.idcontatosolicitacaoservico = solicitacaoservico.idcontatosolicitacaoservico ");
                sql.append("WHERE (UPPER(tipodemandaservico.classificacao) = UPPER('*') OR '*' = '*') ");

                if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
                    sql.append("AND (solicitacaoservico.idSolicitacaoServico = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
                }
                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servicocontrato.idservico = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
                    sql.append("AND (solicitacaoservico.idprioridade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdPrioridade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
                    sql.append("AND (solicitacaoservico.idorigem = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdOrigem());

                }
                if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
                    sql.append("AND (solicitacaoservico.idunidade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdUnidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdLocalidade() != null) {
                    sql.append("AND (contatosolicitacaoservico.idlocalidade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdLocalidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
                    sql.append("AND (solicitacaoservico.idfaseatual = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
                }
                if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
                    sql.append("AND (solicitacaoservico.idgrupoatual = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());

                }
                if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equalsIgnoreCase("")) {
                    sql.append("AND (solicitacaoservico.situacao = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getSituacao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdSolicitante() != null) {
                    sql.append("AND (solicitacaoservico.idsolicitante = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdSolicitante());
                }
                if (pesquisaSolicitacaoServicoDto.getIdItemConfiguracao() != null) {
                    sql.append("AND (solicitacaoservico.iditemconfiguracao = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdItemConfiguracao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
                    sql.append("AND (solicitacaoservico.idTipoDemandaServico = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
                    sql.append("AND (solicitacaoservico.idResponsavel = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdResponsavel());
                }

                if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
                    sql.append("AND (solicitacaoservico.idusuarioresponsavelatual = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
                }

                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servico.idservico = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getPalavraChave() != null && !pesquisaSolicitacaoServicoDto.getPalavraChave().equalsIgnoreCase("")) {
                    sql.append("AND (UPPER(solicitacaoservico.descricao) like UPPER('%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%') ) ");
                    // parametros.add(pesquisaSolicitacaoServicoDto.getPalavraChave());
                }
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                    sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicio()));
                    parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFim()));
                }

                if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null && !StringUtils.equalsIgnoreCase(pesquisaSolicitacaoServicoDto.getDataInicioFechamento().toString(), "1970-01-01")) {
                    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                        sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicioFechamento()));
                        parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFimFechamento()));
                    }

                }

                /*
                 * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento, faseservico e empregados estava utilizando
                 * INNER JOIN, isso fazia com que as solicitações que não possuem origem contrato, fase ou solicitante não fossem retornadas. Alterado para LEFT JOIN
                 */

                sql.append(" AND IDSOLICITACAOSERVICO IN(SELECT IDSOLICITACAOSERVICO FROM(SELECT row_.*, ROWNUM ROWNUM_ FROM (SELECT COUNT(*) OVER() AS TOTALROWCOUNT, ");
                sql.append(" tempoAtendimentoMM,datahorainicio, datahorafim, idsolicitacaoservico, nomeservico, unidade.nome AS NOMEUNIDADE, CASE WHEN solicitacaoservico.situacao = 'EmAndamento' THEN 'Em Andamento' ELSE solicitacaoservico.situacao END AS situacao, dataHoraSolicitacao, dataHoraLimite, nomeTipoDemandaServico, prazohh, prazomm, ");
                sql.append("solicitacaoservico.descricaoSemFormatacao, resposta, grupo.sigla, seqreabertura, empregado.nome AS NOMEEMPREGADO, faseservico.nomefase, origematendimento.descricao,prioridade.nomeprioridade, usuario.nome AS NOMEUSUARIO, contratos.numero, idUsuarioResponsavelAtual  ");
                sql.append("FROM solicitacaoservico ");
                sql.append("INNER JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
                sql.append("INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
                if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
                    sql.append("AND (idcontrato = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdContrato());
                }
                sql.append("INNER JOIN servico ON servicocontrato.idservico = servico.idservico LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual ");
                sql.append("INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");
                sql.append("LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante ");
                sql.append("inner join usuario usuario on usuario.idusuario = solicitacaoservico.idresponsavel ");
                sql.append("LEFT JOIN  faseservico faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual ");
                sql.append("LEFT JOIN  origematendimento origematendimento  ON origematendimento.idorigem = solicitacaoservico.idorigem ");
                sql.append("INNER JOIN prioridade prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade ");
                sql.append("LEFT JOIN unidade unidade ON unidade.idunidade = solicitacaoservico.idunidade ");
                sql.append("LEFT JOIN contatosolicitacaoservico contatosolicitacaoservico ON contatosolicitacaoservico.idcontatosolicitacaoservico = solicitacaoservico.idcontatosolicitacaoservico ");
                sql.append("WHERE (UPPER(tipodemandaservico.classificacao) = UPPER('*') OR '*' = '*') ");

                if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
                    sql.append("AND (solicitacaoservico.idSolicitacaoServico = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
                }
                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servicocontrato.idservico = ?) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
                    sql.append("AND (solicitacaoservico.idprioridade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdPrioridade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
                    sql.append("AND (solicitacaoservico.idorigem = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdOrigem());

                }
                if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
                    sql.append("AND (solicitacaoservico.idunidade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdUnidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdLocalidade() != null) {
                    sql.append("AND (contatosolicitacaoservico.idlocalidade = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdLocalidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
                    sql.append("AND (solicitacaoservico.idfaseatual = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
                }
                if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
                    sql.append("AND (solicitacaoservico.idgrupoatual = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());

                }
                if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equalsIgnoreCase("")) {
                    sql.append("AND (solicitacaoservico.situacao = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getSituacao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdSolicitante() != null) {
                    sql.append("AND (solicitacaoservico.idsolicitante = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdSolicitante());
                }
                if (pesquisaSolicitacaoServicoDto.getIdItemConfiguracao() != null) {
                    sql.append("AND (solicitacaoservico.iditemconfiguracao = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdItemConfiguracao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
                    sql.append("AND (solicitacaoservico.idTipoDemandaServico = ? ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
                    sql.append("AND (solicitacaoservico.idResponsavel = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdResponsavel());
                }

                if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
                    sql.append("AND (solicitacaoservico.idusuarioresponsavelatual = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
                }

                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servico.idservico = ?  ) ");
                    parametros2.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getPalavraChave() != null && !pesquisaSolicitacaoServicoDto.getPalavraChave().equalsIgnoreCase("")) {
                    sql.append("AND (UPPER(solicitacaoservico.descricao) like UPPER('%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%') ) ");
                    // parametros.add(pesquisaSolicitacaoServicoDto.getPalavraChave());
                }
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                    sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicio()));
                    parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFim()));
                }

                if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null && !StringUtils.equalsIgnoreCase(pesquisaSolicitacaoServicoDto.getDataInicioFechamento().toString(), "1970-01-01")) {
                    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                        sql.append("AND (to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? )");
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataInicioFechamento()));
                        parametros2.add(formatter.format(pesquisaSolicitacaoServicoDto.getDataFimFechamento()));
                    }

                }

                if (pesquisaSolicitacaoServicoDto.getOrdenacao() != null) {
                    sql.append(" ORDER BY " + pesquisaSolicitacaoServicoDto.getOrdenacao() + "");
                }

                sql.append(") row_ WHERE ROWNUM <= " + quantidadePaginator2 + " ) WHERE ROWNUM_ > " + pagAtual + ")");
            }

            // para sqlserver o sql é diferente
            else if (strSGBDPrincipal.equalsIgnoreCase("sqlserver")) {
                Integer quantidadePaginator2 = new Integer(0);
                sql.setLength(0);
                quantidadePaginator2 = quantidadePaginator + pagAtual;

                /*
                 * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento, faseservico e empregados estava utilizando
                 * INNER JOIN, isso fazia com que as solicitações que não possuem origem contrato, fase ou solicitante não fossem retornadas. Alterado para LEFT JOIN
                 */

                sql.append(";WITH TabelaTemporaria AS ( ");
                sql.append(" SELECT tempoatendimentoHH, tempoAtendimentoMM,datahorainicio, datahorafim, idsolicitacaoservico, nomeservico, unidade.nome as nomeunidade, ");
                sql.append(" CASE WHEN solicitacaoservico.situacao = 'EmAndamento' THEN 'Em Andamento' ELSE solicitacaoservico.situacao END AS situacao, dataHoraSolicitacao, dataHoraLimite, ");
                sql.append(" nomeTipoDemandaServico, prazohh, prazomm, solicitacaoservico.descricaoSemFormatacao, resposta, grupo.sigla, seqreabertura, empregado.nome as nomeempregado, faseservico.nomefase, ");
                sql.append(" origematendimento.descricao,prioridade.nomeprioridade, usuario.nome as nomeusuario, contratos.numero, idUsuarioResponsavelAtual, ROW_NUMBER() OVER (ORDER BY solicitacaoservico.idsolicitacaoservico) AS Row ");
                sql.append("        FROM solicitacaoservico ");
                sql.append("        INNER JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
                sql.append("        INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
                sql.append("        INNER JOIN servico ON servicocontrato.idservico = servico.idservico ");
                sql.append("        LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual ");
                sql.append("        INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");
                sql.append("		LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante ");
                sql.append("		inner join usuario usuario on usuario.idusuario = solicitacaoservico.idresponsavel ");
                sql.append("		LEFT JOIN  faseservico faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual ");
                sql.append("		LEFT JOIN  origematendimento origematendimento  ON origematendimento.idorigem = solicitacaoservico.idorigem ");
                sql.append("		INNER JOIN prioridade prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade ");
                sql.append("		LEFT JOIN unidade unidade ON unidade.idunidade = solicitacaoservico.idunidade ");
                sql.append("        LEFT JOIN contatosolicitacaoservico contatosolicitacaoservico ON contatosolicitacaoservico.idcontatosolicitacaoservico = solicitacaoservico.idcontatosolicitacaoservico ");
                sql.append("        WHERE (UPPER(tipodemandaservico.classificacao) = UPPER('*') OR '*' = '*') ");

                if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
                    sql.append("AND (solicitacaoservico.idSolicitacaoServico = ?) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
                }
                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servicocontrato.idservico = ?) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
                    sql.append("AND (solicitacaoservico.idprioridade = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdPrioridade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
                    sql.append("AND (solicitacaoservico.idorigem = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdOrigem());

                }
                if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
                    sql.append("AND (solicitacaoservico.idunidade = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdUnidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdLocalidade() != null) {
                    sql.append("AND (contatosolicitacaoservico.idlocalidade = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdLocalidade());
                }
                if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
                    sql.append("AND (solicitacaoservico.idfaseatual = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
                }
                if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
                    sql.append("AND (solicitacaoservico.idgrupoatual = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());

                }
                if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equalsIgnoreCase("")) {
                    sql.append("AND (solicitacaoservico.situacao = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getSituacao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdSolicitante() != null) {
                    sql.append("AND (solicitacaoservico.idsolicitante = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdSolicitante());
                }
                if (pesquisaSolicitacaoServicoDto.getIdItemConfiguracao() != null) {
                    sql.append("AND (solicitacaoservico.iditemconfiguracao = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdItemConfiguracao());
                }
                if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
                    sql.append("AND (solicitacaoservico.idTipoDemandaServico = ? ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
                }
                if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
                    sql.append("AND (solicitacaoservico.idResponsavel = ?  ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdResponsavel());
                }

                if (pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null) {
                    sql.append("AND (solicitacaoservico.idusuarioresponsavelatual = ?  ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
                }

                if (pesquisaSolicitacaoServicoDto.getIdServico() != null) {
                    sql.append("AND (servico.idservico = ?  ) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getIdServico());
                }
                if (pesquisaSolicitacaoServicoDto.getPalavraChave() != null && !pesquisaSolicitacaoServicoDto.getPalavraChave().equalsIgnoreCase("")) {
                    sql.append("AND (solicitacaoservico.descricao like '%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%' ) ");
                }

                if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null && !StringUtils.equalsIgnoreCase(pesquisaSolicitacaoServicoDto.getDataInicioFechamento().toString(), "1970-01-01")) {
                    sql.append(" AND (solicitacaoservico.datahorafim BETWEEN ? AND ?) ");
                    parametros3.add(pesquisaSolicitacaoServicoDto.getDataInicioFechamento());
                    parametros3.add(pesquisaSolicitacaoServicoDto.getDataFimFechamento());
                }

                sql.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ");
                parametros3.add(pesquisaSolicitacaoServicoDto.getDataInicio());
                parametros3.add(transformaHoraFinal(pesquisaSolicitacaoServicoDto.getDataFim()));

                sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row>" + pagAtual + " and Row<" + (quantidadePaginator2 + 1) + " ");

            }
        }

        if (listaTotal != null) {
            pesquisaSolicitacaoServicoDto.setTotalItens(listaTotal.size());
            if (listaTotal.size() > quantidadePaginator) {
                totalPag = ((listaTotal.size() / quantidadePaginator));
                if (listaTotal.size() % quantidadePaginator != 0) {
                    totalPag = totalPag + 1;
                }
            } else {
                totalPag = 1;
            }
        }
        pesquisaSolicitacaoServicoDto.setTotalPagina(totalPag);
        List lista;

        if (strSGBDPrincipal.equalsIgnoreCase("ORACLE")) {
            lista = execSQL(sql.toString().toUpperCase(), parametros2.toArray());
        } else if (strSGBDPrincipal.equalsIgnoreCase("SQLSERVER")) {
            lista = execSQL(sql.toString().toUpperCase(), parametros3.toArray());
        } else {
            lista = execSQL(sql.toString().toUpperCase(), parametros.toArray());
        }

        if (lista == null || lista.size() == 0) {
            TransactionControler tc = this.getTransactionControler();
            if (tc != null) {
                tc.close();
            }
            return null;
        }

        List result = new ArrayList();
        if (lista == null || lista.size() == 0) {
            TransactionControler tc = this.getTransactionControler();
            if (tc != null) {
                tc.close();
            }

            return result;
        }

        TransactionControler tc = this.getTransactionControler();
        if (tc != null) {
            tc.close();
        }

        listRetorno.add("tempoAtendimentoHH");
        listRetorno.add("tempoAtendimentoMM");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraFim");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("nomeUnidadeSolicitante");
        listRetorno.add("situacao");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("descricaoSemFormatacao");
        listRetorno.add("resposta");
        listRetorno.add("siglaGrupo");
        listRetorno.add("seqReabertura");
        listRetorno.add("nomeSolicitante");
        listRetorno.add("faseAtual");
        listRetorno.add("origem");
        listRetorno.add("prioridade");
        listRetorno.add("responsavel");
        listRetorno.add("contrato");
        listRetorno.add("idUsuarioResponsavelAtual");

        List listaSolicitacoes = engine.listConvertion(getBean(), lista, listRetorno);

        return listaSolicitacoes;
    }
    
    
    public Collection<SolicitacaoServicoDTO> findByIdGrupo(Integer idGrupo) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao from solicitacaoservico sol where sol.idgrupoatual = ? and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada'");
        parametro.add(idGrupo);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAlta(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato " + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and urgencia like ? and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada'");
        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add("A");

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasAlta(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        Date dataAtual = UtilDatas.getDataAtual();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato "
                + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and urgencia like ? and datahorafim < datahoralimite and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada' "
                + "and situacao <> 'Suspensa' and (datahoralimite < ?)");

        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add("A");
        parametro.add(dataAtual);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasBaixa(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        Date dataAtual = UtilDatas.getDataAtual();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato "
                + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and urgencia like ? and datahorafim < datahoralimite and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada' "
                + "and situacao <> 'Suspensa' and (datahoralimite < ?)");
        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add("B");
        parametro.add(dataAtual);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasMedia(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        Date dataAtual = UtilDatas.getDataAtual();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato "
                + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and urgencia like ? and datahorafim < datahoralimite and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada' "
                + "and situacao <> 'Suspensa' and (datahoralimite < ?)");

        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add("M");
        parametro.add(dataAtual);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtendidasTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        Date dataAtual = UtilDatas.getDataAtual();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade from solicitacaoservico sol " + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato "
                + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and datahorafim < datahoralimite and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada' "
                + "and situacao <> 'Suspensa' and (datahoralimite < ?)");
        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add(dataAtual);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataAtrasadasTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        Date dataAtual = UtilDatas.getDataAtual();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico from solicitacaoservico sol " + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato "
                + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo " + "where g.idgrupo = ? and datahorainiciosla between ? and ? "
                + "and (datahorafim > datahoralimite or (datahorafim is null and datahoralimite > ?)) "
                + "and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada' and situacao <> 'Suspensa' " + "and (datahoralimite < ?)");

        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add(dataAtual);
        parametro.add(dataAtual);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataBaixa(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato " + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and urgencia like ? and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada'");
        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add("B");

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataMedia(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato " + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and urgencia like ? and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada' order by idsolicitacaoservico ");
        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add("M");

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataSuspensasTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia, sol.datahorafim, sol.datahoralimite from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato " + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and situacao = 'Suspensa'");

        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");
        listRetorno.add("dataHoraFim");
        listRetorno.add("dataHoraLimite");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdGrupoEDataTotal(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia, sol.datahorafim, sol.datahoralimite from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato " + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and datahoralimite < ? and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada'");
        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add(UtilDatas.getDataHoraAtual());

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");
        listRetorno.add("dataHoraFim");
        listRetorno.add("dataHoraLimite");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdPessoaEData(Integer idGrupo, Integer idPessoa, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia, sol.datahorafim, sol.datahoralimite from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato " + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo " + "where g.idgrupo = ? "
                + "and datahorainiciosla between ? and ? and idresponsavel = ?");
        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add(idPessoa);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");
        listRetorno.add("dataHoraFim");
        listRetorno.add("dataHoraLimite");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdPessoaEData(Integer idGrupo, String login, String nome, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select distinct sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia, sol.datahorafim, sol.datahoralimite from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato " + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "inner join ocorrenciasolicitacao o on sol.idsolicitacaoservico = o.idsolicitacaoservico " + "where g.idgrupo = ? " + "and datahorainiciosla between ? and ? "
                + "and (o.registradopor like ? or o.registradopor like ?) " + "and o.ocorrencia like 'Execução da tarefa \"Desenvolvimento\"' "
                + "and o.descricao like '%Registro de Execução%' and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada'");

        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add(login);
        parametro.add(nome);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");
        listRetorno.add("dataHoraFim");
        listRetorno.add("dataHoraLimite");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdPessoaEDataAtendidas(Integer idGrupo, Integer idPessoa, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia, sol.datahorafim, sol.datahoralimite from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato " + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and datahorafim < datahoralimite and sol.idresponsavel = ?");
        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add(idPessoa);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");
        listRetorno.add("dataHoraFim");
        listRetorno.add("dataHoraLimite");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdPessoaEDataAtendidas(Integer idGrupo, String login, String nome, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select distinct sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia, sol.datahorafim, sol.datahoralimite from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato " + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
                + "inner join ocorrenciasolicitacao o on sol.idsolicitacaoservico = o.idsolicitacaoservico "
                + "where g.idgrupo = ? and datahorainiciosla between ? and ? and datahorafim < datahoralimite " + "and (o.registradopor like ? or o.registradopor like ?) "
                + "and o.ocorrencia like 'Execução da tarefa \"Desenvolvimento\"' "
                + "and o.descricao like '%Registro de Execução%' and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada'");
        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add(login);
        parametro.add(nome);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");
        listRetorno.add("dataHoraFim");
        listRetorno.add("dataHoraLimite");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdPessoaEDataNaoAtendidas(Integer idGrupo, Date dataInicio, Date dataFim) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idResponsavel, sol.idsolicitacaoservico, sol.situacao, sol.idprioridade, sol.urgencia from solicitacaoservico sol "
                + "inner join servicocontrato sc on sc.idservicocontrato = sol.idservicocontrato " + "inner join grupo g on sc.idgrupoexecutor = g.idgrupo " + "where g.idgrupo = ? "
                + "and datahorainiciosla between ? and ? and datahoralimite > ? and (sol.prazohh <> 0 or sol.prazomm <> 0) and situacao <> 'Cancelada'");
        parametro.add(idGrupo);
        parametro.add(dataInicio);
        parametro.add(dataFim);
        parametro.add(UtilDatas.getDataHoraAtual());

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idResponsavel");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("idPrioridade");
        listRetorno.add("urgencia");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findByIdSolicitacaoPai(Integer idSolicitacaoPai) throws Exception {
        List condicao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoPai", "=", idSolicitacaoPai));
        return super.findByCondition(condicao, null);
    }

    /**
     * Retorna a solicitação de servico pelo id
     *
     * @param idSolicitacaoServico
     * @return
     * @throws Exception
     */
    public SolicitacaoServicoDTO findByIdSolicitacaoServico(Integer idSolicitacaoServico) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select sol.idsolicitacaoservico, ser.nomeservico , sol.situacao from solicitacaoservico sol inner join servicocontrato "
                + "sercont on  sol.idservicocontrato =  sercont.idservicocontrato inner join servico ser on ser.idservico = sercont.idservico " + "where sol.idsolicitacaoservico = ?");
        parametro.add(idSolicitacaoServico);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("situacao");

        if (list != null && !list.isEmpty()) {
            return (SolicitacaoServicoDTO) this.listConvertion(getBean(), list, listRetorno).get(0);
        } else {
            return null;
        }
    }

    /**
     * Executando em: sqlServer, mySql e POstgreSQL
     **/
    public Collection<SolicitacaoServicoDTO> findByServico(Integer idServico) throws Exception {
        List parametro = new ArrayList();
        List fields = new ArrayList();
        List list = new ArrayList();
        String sql = "   select nometiposervico, nomeservico, nomecategoriaservico, idServico from servico " + "inner join tiposervico  on servico.idtiposervico = tiposervico.idtiposervico "
                + "inner join categoriaservico on servico.idcategoriaservico = categoriaservico.idcategoriaservico " + "where ";

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql += "UPPER(servico.deleted) <> 'Y' ";
        } else {
            sql += "servico.deleted <> 'y' ";
        }
        sql += "and situacao = 'A' and servico.idServico = ? and dispportal = 'S' order by nomecategoriaservico ";

        parametro.add(idServico);
        list = this.execSQL(sql, parametro.toArray());
        fields.add("nomeTipoServico");
        fields.add("nomeServico");
        fields.add("nomeCategoriaServico");
        fields.add("idServico");
        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, fields);
        } else {
            return null;
        }

    }

    /**
     * Executando em: sqlServer, mySql e POstgreSQL
     **/
    public Collection<SolicitacaoServicoDTO> findByServico(Integer idServico, String nome) throws Exception {
        List parametro = new ArrayList();
        List fields = new ArrayList();
        List list = new ArrayList();
        String sql = "   select nometiposervico, nomeservico, nomecategoriaservico, idServico from servico " + "inner join tiposervico  on servico.idtiposervico = tiposervico.idtiposervico "
                + "inner join categoriaservico on servico.idcategoriaservico = categoriaservico.idcategoriaservico " + "where ";

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql += "UPPER(servico.deleted) <> 'Y' ";
        } else {
            sql += "servico.deleted <> 'y' ";
        }
        sql += "and situacao = 'A' and servico.idServico = ? and dispportal = 'S' and servico.nomeservico like '%" + nome + "%' order by nomecategoriaservico ";

        parametro.add(idServico);
        list = this.execSQL(sql, parametro.toArray());
        fields.add("nomeTipoServico");
        fields.add("nomeServico");
        fields.add("nomeCategoriaServico");
        fields.add("idServico");
        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, fields);
        } else {
            return null;
        }

    }

    public SolicitacaoServicoDTO findInfosCriacaoProblemaByIdSolServico(SolicitacaoServicoDTO solServico) throws Exception {
        Object[] params = new Object[] { solServico.getIdSolicitacaoServico() };
        List listRetorno = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select C.NOMECONTATO, C.TELEFONECONTATO, C.EMAILCONTATO, SE.IDCONTRATO " + " from solicitacaoservico S "
                + " INNER JOIN CONTATOSOLICITACAOSERVICO C ON S.idcontatosolicitacaoservico= C.idcontatosolicitacaoservico "
                + " INNER JOIN SERVICOCONTRATO SE ON S.IDSERVICOCONTRATO = SE.IDSERVICOCONTRATO " + " WHERE S.IDSOLICITACAOSERVICO = ?");

        listRetorno.add("nomecontato");
        listRetorno.add("telefonecontato");
        listRetorno.add("emailcontato");
        listRetorno.add("idContrato");

        List list = this.execSQL(sql.toString(), params);
        if (list != null && !list.isEmpty()) {
            return (SolicitacaoServicoDTO) this.listConvertion(getBean(), list, listRetorno).get(0);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> findResponsavelAtual(Integer idSolicitacaoServico) throws Exception {
        StringBuilder sql = new StringBuilder();
        List parametro = new ArrayList();

        List list = new ArrayList();

        sql.append("SELECT bpm_itemtrabalhofluxo.idresponsavelatual ");
        sql.append("FROM solicitacaoservico solicitacaoservico ");
        sql.append("INNER JOIN execucaosolicitacao execucaosolicitacao ");
        sql.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("INNER JOIN bpm_itemtrabalhofluxo bpm_itemtrabalhofluxo ");
        sql.append("ON bpm_itemtrabalhofluxo.idinstancia = execucaosolicitacao.idinstanciafluxo ");
        sql.append("WHERE solicitacaoservico.idsolicitacaoservico = ? ");
        sql.append("AND bpm_itemtrabalhofluxo.datahorafinalizacao  is null  ");
        sql.append("AND bpm_itemtrabalhofluxo.idresponsavelatual  is not null  ");

        parametro.add(idSolicitacaoServico);

        list = this.execSQL(sql.toString(), parametro.toArray());
        List listRetorno = new ArrayList();
        listRetorno.add("idUsuarioResponsavelAtual");
        List result = engine.listConvertion(getBean(), list, listRetorno);
        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }


    public ArrayList<SolicitacaoServicoDTO> findSolicitacoesServicosUsuario(Integer idUsuario, String status, String campoBusca) throws Exception {
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("select idSolicitacaoServico");
        sql.append(" from solicitacaoservico");
        sql.append(" where 1=1 ");
        if (idUsuario != null) {
            sql.append(" and idSolicitante = " + idUsuario.toString());
        } else {
            return null;
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" and situacao like '" + status + "'");
        }
        if (campoBusca != null && !campoBusca.isEmpty()) {
            sql.append(" and (upper(descricao) like upper('%" + campoBusca + "%')");
            sql.append(" or upper(descricao) like upper('%" + Util.encodeHTML(campoBusca) + "%')");
            sql.append(" or upper(resposta) like upper('%" + campoBusca + "%')");
            sql.append(" or upper(resposta) like upper('%" + Util.encodeHTML(campoBusca) + "%')");
            if (isInteger(campoBusca)) {
                sql.append(" or idSolicitacaoServico = " + campoBusca);
            }
            sql.append(")");
        }

        list = this.execSQL(sql.toString(), null);

        listRetorno.add("idSolicitacaoServico");

        if (list != null && !list.isEmpty()) {
            return (ArrayList<SolicitacaoServicoDTO>) this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    @Override
    public Class<SolicitacaoServicoDTO> getBean() {
        return SolicitacaoServicoDTO.class;
    }

    protected List<String> getColunasRestoreAll() {
        final List<String> listRetorno = new ArrayList<>();
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
        listRetorno.add("prazoCapturaHH");
        listRetorno.add("prazoCapturaMM");
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
        listRetorno.add("idUltimaAprovacao");
        listRetorno.add("dataHoraInicioSLA");
        listRetorno.add("dataHoraSuspensaoSLA");
        listRetorno.add("dataHoraReativacaoSLA");
        listRetorno.add("situacaoSLA");
        listRetorno.add("idTarefaEncerramento");
        listRetorno.add("aprovacao");
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
        listRetorno.add("idSolicitacaoRelacionada");
        listRetorno.add("emailResponsavel");
        return listRetorno;
    }

    /**
     * Executando em: sqlServer, mySql e POstgreSQL
     **/
    public Collection getEmAndamentoParaTratamentoBatch() throws Exception {
        String sql = "SELECT s.idsolicitacaoservico, s.idAcordoNivelServico, ans.tempoauto, ans.idprioridadeauto1, ans.idgrupo1, s.idprioridade FROM solicitacaoservico s "
                + "INNER JOIN acordonivelservico ans on ans.idacordonivelservico = s.idacordonivelservico " + "where UPPER(s.situacao) not in ('FECHADA', 'CANCELADA', 'RESOLVIDA', 'SUSPENSA') ";
        List listDados = this.execSQL(sql, null);
        if (listDados != null) {
            List fields = new ArrayList();
            fields.add("idSolicitacaoServico");
            fields.add("idAcordoNivelServico");
            fields.add("tempoAuto");
            fields.add("idPrioridadeAuto1");
            fields.add("idGrupo1");
            fields.add("idPrioridade");
            List lstReturn = this.listConvertion(SolicitacaoServicoDTO.class, listDados, fields);
            return lstReturn;
        }
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", true, true, false, false));
        listFields.add(new Field("idbaseconhecimento", "idBaseConhecimento", false, false, false, false));
        listFields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
        listFields.add(new Field("idSolicitante", "idSolicitante", false, false, false, false));
        listFields.add(new Field("idItemConfiguracao", "idItemConfiguracao", false, false, false, false));
        listFields.add(new Field("idItemConfiguracaoFilho", "idItemConfiguracaoFilho", false, false, false, false));
        listFields.add(new Field("idtipodemandaservico", "idTipoDemandaServico", false, false, false, false));
        listFields.add(new Field("idcontatosolicitacaoservico", "idContatoSolicitacaoServico", false, false, false, false));
        listFields.add(new Field("idOrigem", "idOrigem", false, false, false, false));
        listFields.add(new Field("idResponsavel", "idResponsavel", false, false, false, false));
        listFields.add(new Field("idTipoProblema", "idTipoProblema", false, false, false, false));
        listFields.add(new Field("idPrioridade", "idPrioridade", false, false, false, false));
        listFields.add(new Field("idUnidade", "idUnidade", false, false, false, false));
        listFields.add(new Field("idFaseAtual", "idFaseAtual", false, false, false, false));
        listFields.add(new Field("idGrupoAtual", "idGrupoAtual", false, false, false, false));
        listFields.add(new Field("dataHoraSolicitacao", "dataHoraSolicitacao", false, false, false, false));
        listFields.add(new Field("dataHoraLimite", "dataHoraLimite", false, false, false, false));
        listFields.add(new Field("atendimentoPresencial", "atendimentoPresencial", false, false, false, false));
        listFields.add(new Field("prazoCapturaHH", "prazoCapturaHH", false, false, false, false));
        listFields.add(new Field("prazoCapturaMM", "prazoCapturaMM", false, false, false, false));
        listFields.add(new Field("prazoHH", "prazoHH", false, false, false, false));
        listFields.add(new Field("prazoMM", "prazoMM", false, false, false, false));
        listFields.add(new Field("descricao", "descricao", false, false, false, false));
        listFields.add(new Field("resposta", "resposta", false, false, false, false));
        listFields.add(new Field("dataHoraInicio", "dataHoraInicio", false, false, false, false));
        listFields.add(new Field("dataHoraFim", "dataHoraFim", false, false, false, false));
        listFields.add(new Field("situacao", "situacao", false, false, false, false));
        listFields.add(new Field("idSolicitacaoPai", "idSolicitacaoPai", false, false, false, false));
        listFields.add(new Field("detalhamentoCausa", "detalhamentoCausa", false, false, false, false));
        listFields.add(new Field("idCausaIncidente", "idCausaIncidente", false, false, false, false));
        listFields.add(new Field("idCategoriaSolucao", "idCategoriaSolucao", false, false, false, false));
        listFields.add(new Field("seqreabertura", "seqReabertura", false, false, false, false));
        listFields.add(new Field("enviaEmailCriacao", "enviaEmailCriacao", false, false, false, false));
        listFields.add(new Field("enviaEmailFinalizacao", "enviaEmailFinalizacao", false, false, false, false));
        listFields.add(new Field("enviaEmailAcoes", "enviaEmailAcoes", false, false, false, false));
        listFields.add(new Field("idgruponivel1", "idGrupoNivel1", false, false, false, false));
        listFields.add(new Field("solucaoTemporaria", "solucaoTemporaria", false, false, false, false));
        listFields.add(new Field("houveMudanca", "houveMudanca", false, false, false, false));
        listFields.add(new Field("slaACombinar", "slaACombinar", false, false, false, false));
        listFields.add(new Field("prazohhAnterior", "prazohhAnterior", false, false, false, false));
        listFields.add(new Field("prazommAnterior", "prazommAnterior", false, false, false, false));
        listFields.add(new Field("idCalendario", "idCalendario", false, false, false, false));
        listFields.add(new Field("tempoDecorridoHH", "tempoDecorridoHH", false, false, false, false));
        listFields.add(new Field("tempoDecorridoMM", "tempoDecorridoMM", false, false, false, false));
        listFields.add(new Field("dataHoraSuspensao", "dataHoraSuspensao", false, false, false, false));
        listFields.add(new Field("dataHoraReativacao", "dataHoraReativacao", false, false, false, false));
        listFields.add(new Field("impacto", "impacto", false, false, false, false));
        listFields.add(new Field("urgencia", "urgencia", false, false, false, false));
        listFields.add(new Field("dataHoraCaptura", "dataHoraCaptura", false, false, false, false));
        listFields.add(new Field("tempoCapturaHH", "tempoCapturaHH", false, false, false, false));
        listFields.add(new Field("tempoCapturaMM", "tempoCapturaMM", false, false, false, false));
        listFields.add(new Field("tempoAtrasoHH", "tempoAtrasoHH", false, false, false, false));
        listFields.add(new Field("tempoAtrasoMM", "tempoAtrasoMM", false, false, false, false));
        listFields.add(new Field("tempoAtendimentoHH", "tempoAtendimentoHH", false, false, false, false));
        listFields.add(new Field("tempoAtendimentoMM", "tempoAtendimentoMM", false, false, false, false));
        listFields.add(new Field("idAcordoNivelServico", "idAcordoNivelServico", false, false, false, false));
        listFields.add(new Field("idSolicitacaoRelacionada", "idSolicitacaoRelacionada", false, false, false, false));
        listFields.add(new Field("descricaosemformatacao", "descricaoSemFormatacao", false, false, false, false));
        listFields.add(new Field("idUltimaAprovacao", "idUltimaAprovacao", false, false, false, false));
        listFields.add(new Field("dataHoraInicioSLA", "dataHoraInicioSLA", false, false, false, false));
        listFields.add(new Field("situacaoSLA", "situacaoSLA", false, false, false, false));
        listFields.add(new Field("dataHoraSuspensaoSLA", "dataHoraSuspensaoSLA", false, false, false, false));
        listFields.add(new Field("dataHoraReativacaoSLA", "dataHoraReativacaoSLA", false, false, false, false));
        listFields.add(new Field("idTarefaEncerramento", "idTarefaEncerramento", false, false, false, false));
        listFields.add(new Field("codigoExterno", "codigoExterno", false, false, false, false));
        listFields.add(new Field("vencendo", "vencendo", false, false, false, false));
        listFields.add(new Field("criouproblemaautomatico", "criouproblemaautomatico", false, false, false, false));
        listFields.add(new Field("idUsuarioResponsavelAtual", "idUsuarioResponsavelAtual", false, false, false, false));
        listFields.add(new Field("latitude", "latitude", false, false, false, false));
        listFields.add(new Field("longitude", "longitude", false, false, false, false));
        return listFields;
    }

    /**
     * Executando em: sqlServer, mySql e POstgreSQL
     **/
    public Collection<SolicitacaoServicoDTO> getHistoricoByIdSolicitacao(Integer idSolicitacao) throws Exception {
        List listRetorno = new ArrayList();
        String sql = "";
        sql = "select s.idsolicitacaoservico, ";
        sql += "       e.seqreabertura, ";
        sql += "       h.datahora, ";
        sql += "       u.login responsavel, ";
        sql += "       h.acao, ";
        sql += "       l.documentacao as tarefa, ";
        sql += "       gd.sigla as atribuido_grupo, ";
        sql += "       ud.login as atribuido_usuario ";
        sql += "from bpm_historicoitemtrabalho h ";
        sql += "    inner join bpm_itemtrabalhofluxo i ";
        sql += "       on h.iditemtrabalho = i.iditemtrabalho ";
        sql += "    inner join bpm_elementofluxo l ";
        sql += "       on l.idelemento = i.idelemento ";
        sql += "    inner join bpm_instanciafluxo f ";
        sql += "       on f.idinstancia = i.idinstancia ";
        sql += "    inner join execucaosolicitacao e ";
        sql += "       on e.idinstanciafluxo = i.idinstancia ";
        sql += "    inner join solicitacaoservico s ";
        sql += "       on s.idsolicitacaoservico = i.idinstancia ";
        sql += "    left outer join usuario u ";
        sql += "       on u.idusuario = h.idresponsavel ";
        sql += "    left outer join usuario ud ";
        sql += "       on ud.idusuario = h.idusuario   ";
        sql += "    left outer join grupo gd ";
        sql += "       on gd.idgrupo = h.idgrupo ";
        sql += "  where h.acao in ('Iniciar', 'Executar', 'Delegar') ";
        sql += "  and s.idsolicitacaoservico = ? ";
        sql += "  order by s.idsolicitacaoservico, h.datahora, u.login ";

        List lista = new ArrayList();
        List lstParms = new ArrayList();
        lstParms.add(idSolicitacao);
        lista = this.execSQL(sql, lstParms.toArray());
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("seqReabertura");
        listRetorno.add("dataHora");
        listRetorno.add("responsavel");
        listRetorno.add("acaoFluxo");
        listRetorno.add("tarefa");
        listRetorno.add("siglaGrupo");
        listRetorno.add("nomeUsuario");

        List listSolicitacoes = engine.listConvertion(getBean(), lista, listRetorno);

        return listSolicitacoes;
    }

    /**
     * Executando em: sqlServer, mySql e POstgreSQL
     **/
    public Integer getQuantidadeByIdServico(int idServico) throws Exception {
        List parametro = new ArrayList();
        String sql = "SELECT count(*) FROM solicitacaoservico ss, servicocontrato sc " + "where ss.idservicocontrato =  sc.idservicocontrato and sc.idservico = ?";
        parametro.add(idServico);
        List resultado = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("quantidade");
        List result = engine.listConvertion(SolicitacaoServicoDTO.class, resultado, listRetorno);
        SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) result.get(0);
        return solicitacaoServicoDTO.getQuantidade();
    }

    public Integer getQuantidadeByIdServicoContrato(int idServicoContrato) throws Exception {
        List parametro = new ArrayList();
        String sql = "SELECT count(*) FROM solicitacaoservico ss, servicocontrato sc " + "where ss.idservicocontrato =  sc.idservicocontrato and sc.idServicoContrato = ?";
        parametro.add(idServicoContrato);
        List resultado = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("quantidade");
        List result = engine.listConvertion(SolicitacaoServicoDTO.class, resultado, listRetorno);
        SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) result.get(0);
        return solicitacaoServicoDTO.getQuantidade();
    }

    /**
     * Executando em: sqlServer, mySql e POstgreSQL
     **/
    private String getSQLRestoreAll() {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT sol.idSolicitacaoServico, sol.idbaseconhecimento, sol.idServicoContrato, sol.idSolicitante, ");
        sql.append("       sol.idItemConfiguracao, sol.idItemConfiguracaoFilho, sol.idtipodemandaservico, sol.idcontatosolicitacaoservico, ");
        sql.append("       sol.idOrigem, sol.idResponsavel, sol.idTipoProblema, sol.idPrioridade, sol.idUnidade, sol.idFaseAtual, ");
        sql.append("       sol.idGrupoAtual, sol.dataHoraSolicitacao, sol.dataHoraLimite, sol.atendimentoPresencial, sol.prazoCapturaHH, sol.prazoCapturaMM, ");
        sql.append("       sol.prazoHH, sol.prazoMM, sol.descricao, sol.resposta, sol.dataHoraInicio, sol.dataHoraFim, sol.situacao, ");
        sql.append("       sol.idSolicitacaoPai, sol.detalhamentoCausa, sol.idCausaIncidente, sol.idCategoriaSolucao, ");
        sql.append("       sol.seqreabertura, sol.enviaEmailCriacao, sol.enviaEmailFinalizacao, sol.enviaEmailAcoes, ");
        sql.append("       sol.idgruponivel1, sol.solucaoTemporaria, sol.houveMudanca, sol.slaACombinar, sol.prazohhAnterior, ");
        sql.append("       sol.prazommAnterior, sol.idCalendario, sol.tempoDecorridoHH, sol.tempoDecorridoMM, sol.dataHoraSuspensao, ");
        sql.append("       sol.dataHoraReativacao, sol.impacto, sol.urgencia, sol.dataHoraCaptura, sol.tempoCapturaHH, sol.tempoCapturaMM, ");
        sql.append("       sol.tempoAtrasoHH, sol.tempoAtrasoMM, sol.tempoAtendimentoHH, sol.tempoAtendimentoMM, sol.idacordonivelservico, ");
        sql.append("       sol.idultimaaprovacao, sol.datahorainiciosla, sol.datahorasuspensaosla, sol.datahorareativacaosla, sol.situacaosla, sol.idtarefaencerramento, ");
        sql.append("       aprov.aprovacao, s.idservico, s.nomeServico, td.idTipoDemandaServico, td.nomeTipoDemandaServico, c.idContrato, c.numero, e1.nome, u1.nome, ");
        sql.append("       e2.nome, u2.nome, oa.descricao, p.nomeprioridade, fs.nomefase,  ");
        sql.append("       g1.sigla, g2.sigla, cs.nomecontato, cs.emailcontato, cs.telefonecontato, cs.localizacaofisica ,cs.idlocalidade,sol.idSolicitacaoRelacionada, e3.email as emailResponsavel ");
        sql.append("  FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN empregados e3 ON e3.idempregado = sol.idresponsavel ");
        sql.append("        LEFT JOIN contatosolicitacaoservico cs ON cs.idcontatosolicitacaoservico = sol.idcontatosolicitacaoservico ");
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");
        return sql.toString();
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

    public boolean hasSolicitacoesServicosUsuario(Integer idUsuario, String status) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select idSolicitacaoServico");
        sql.append(" from solicitacaoservico");
        sql.append(" where idSolicitante = " + idUsuario.toString());
        if (status != null && !status.isEmpty()) {
            sql.append(" and situacao like '%" + status + "%'");
        }

        List list = this.execSQL(sql.toString(), null);
        return (list != null && !list.isEmpty());
    }

    public Collection incidentesPorContrato(Integer idContrato) throws Exception {
        StringBuilder sql = new StringBuilder();
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();

        sql.append(" SELECT idsolicitacaoservico, nomeservico, contratos.numero ,empregado.nome, usuario.nome");
        sql.append(" FROM solicitacaoservico");
        sql.append(" INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato");
        sql.append(" AND (idcontrato = ?)");
        sql.append(" INNER JOIN servico ON servicocontrato.idservico = servico.idservico LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual");
        sql.append(" INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato");
        sql.append(" INNER JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante");
        sql.append(" INNER JOIN usuario usuario on usuario.idusuario = solicitacaoservico.idresponsavel");

        parametro.add(idContrato);
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("numero");
        listRetorno.add("nome");
        listRetorno.add("nomeUsu");

        List list = execSQL(sql.toString(), parametro.toArray());
        List result = engine.listConvertion(SolicitacaoServicoDTO.class, list, listRetorno);

        if (result != null && !result.isEmpty()) {
            return result;
        } else {
            return null;
        }
    }

    public boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void limpaDataReativacao(SolicitacaoServicoDTO solicitacaoDto) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + getTableName() + " SET dataHoraReativacao = NULL WHERE idsolicitacaoservico = ?");
        Object[] params = { solicitacaoDto.getIdSolicitacaoServico() };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização da solicitacaoServico.");
            e.printStackTrace();
        }
    }

    @Override
    public Collection list() throws PersistenceException {
        List list = new ArrayList();
        list.add(new Order("idSolicitacaoServico"));
        return super.list(list);
    }

    public SolicitacaoServicoDTO restoreByIdTarefa(Integer idTarefa) throws Exception {
        final List<Integer> parametro = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT  ");

        for (String fildesDB : this.getListNamesFieldDB()) {
            sb.append("solicitacao.");
            sb.append(fildesDB);
            sb.append(", ");
        }

        sb.append("  bpm_itemtrabalhofluxo.iditemtrabalho  FROM bpm_itemtrabalhofluxo bpm_itemtrabalhofluxo ");
        sb.append("INNER JOIN execucaosolicitacao execucaosolicitacao ");
        sb.append("ON execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia ");
        sb.append("INNER JOIN solicitacaoservico solicitacao on execucaosolicitacao.idsolicitacaoservico = solicitacao.idsolicitacaoservico ");
        sb.append("WHERE bpm_itemtrabalhofluxo.iditemtrabalho = ?  ");

        parametro.add(idTarefa);

        List dados = this.execSQL(sb.toString(), parametro.toArray());

        if (dados != null && !dados.isEmpty()) {
            List<SolicitacaoServicoDTO> listRestore = listConvertion(getBean(), dados, this.getListFields());
            return (SolicitacaoServicoDTO) listRestore.get(0);
        }
        return null;
    }
    
    private List getListFields() {
        final List<String> listFields = getListNamesFieldClass();
        listFields.add("idTarefa");
        return listFields;
    }

    public SolicitacaoServicoDTO listaIdItemTrabalho(Integer idInstancia) throws Exception {
        List parametro = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT bpm_itemtrabalhofluxo.iditemtrabalho ");
        sb.append("FROM bpm_itemtrabalhofluxo bpm_itemtrabalhofluxo ");
        sb.append("INNER JOIN execucaosolicitacao execucaosolicitacao ");
        sb.append("ON execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia ");
        sb.append("WHERE bpm_itemtrabalhofluxo.situacao LIKE 'Disponivel' ");
        sb.append("AND execucaosolicitacao.idsolicitacaoservico = ? ");

        parametro.add(idInstancia);

        List lista = this.execSQL(sb.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("idItemTrabalho");
        List result = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        return (SolicitacaoServicoDTO) result.get(0);
    }

    public Collection<SolicitacaoServicoDTO> listaIDSolicitacaoNaoRespondida(Date dataLimite) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        Collection<SolicitacaoServicoDTO> solicitacoes = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("select idsolicitacaoservico from (");
        sql.append("select solicitacaoservico.idsolicitacaoservico ");
        sql.append("from solicitacaoservico ");
        sql.append("INNER JOIN execucaosolicitacao ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
		sql.append("left join pesquisasatisfacao on solicitacaoservico.idsolicitacaoservico=pesquisasatisfacao.idsolicitacaoservico ");
        sql.append("where (solicitacaoservico.datahorafim <= ?) and (pesquisasatisfacao.idsolicitacaoservico is null)) as s;");

        parametro.add(transformaHoraFinal(dataLimite));

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");

        if (list != null && !list.isEmpty()) {
            solicitacoes = this.listConvertion(getBean(), list, listRetorno);
        }
        return solicitacoes;
    }

    /**
     * Executando em: sqlServer, mySql e POstgreSQL
     **/
    public Collection<SolicitacaoServicoDTO> listAllIncidentes(Integer idEmpregado) throws Exception {
        List parametro = new ArrayList();
        List fields = new ArrayList();
        List list = new ArrayList();
        String sql = "SELECT solicitacaoservico.idSolicitacaoServico, solicitacaoservico.dataHoraSolicitacao, solicitacaoservico.dataHoraLimite, "
                + "solicitacaoservico.Situacao, faseservico.nomeFase as faseAtual, prioridade.nomePrioridade " + "FROM solicitacaoservico "
                + "INNER JOIN faseservico ON solicitacaoservico.idfaseatual = faseservico.idfase "
                + "INNER JOIN prioridade ON solicitacaoservico.idprioridade = prioridade.idprioridade WHERE solicitacaoservico.idSolicitante = ?";
        parametro.add(idEmpregado);
        list = this.execSQL(sql, parametro.toArray());
        fields.add("idSolicitacaoServico");
        fields.add("dataHoraSolicitacao");
        fields.add("dataHoraLimite");
        fields.add("situacao");
        fields.add("faseAtual");
        fields.add("prioridade");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, fields);
        } else {
            return null;
        }

    }

    /**
     * Executando em: sqlServer, mySql e POstgreSQL
     **/
    public Collection<SolicitacaoServicoDTO> listAllServicos() throws Exception {
        List parametro = new ArrayList();
        List fields = new ArrayList();
        List list = new ArrayList();
        String sql = "select nometiposervico, nomeservico, nomecategoriaservico, idServico from servico " + "inner join tiposervico  on servico.idtiposervico = tiposervico.idtiposervico "
                + "inner join categoriaservico on servico.idcategoriaservico = categoriaservico.idcategoriaservico " + "where ";

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql += "UPPER(servico.deleted) <> 'Y' ";
        } else {
            sql += "servico.deleted <> 'y' ";
        }
        sql += "and situacao = 'A' and dispportal = 'S' order by nomecategoriaservico ";

        list = this.execSQL(sql, parametro.toArray());
        fields.add("nomeTipoServico");
        fields.add("nomeServico");
        fields.add("nomeCategoriaServico");
        fields.add("idServico");
        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, fields);
        } else {
            return null;
        }

    }

    /**
     * Executando em: sqlServer, mySql e POstgreSQL
     **/
    public Collection<SolicitacaoServicoDTO> listAllServicosLikeNomeServico(String nome) throws Exception {
        List parametro = new ArrayList();
        List fields = new ArrayList();
        List list = new ArrayList();
        String sql = "   select nometiposervico, nomeservico, nomecategoriaservico, idServico from servico " + "inner join tiposervico  on servico.idtiposervico = tiposervico.idtiposervico "
                + "inner join categoriaservico on servico.idcategoriaservico = categoriaservico.idcategoriaservico " + "where ";

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql += "UPPER(servico.deleted) <> 'Y' ";
        } else {
            sql += "servico.deleted <> 'y' ";
        }
        sql += "and situacao = 'A' and dispportal = 'S' and servico.nomeservico like '%" + nome + "%' order by nomecategoriaservico ";

        list = this.execSQL(sql, parametro.toArray());
        fields.add("nomeTipoServico");
        fields.add("nomeServico");
        fields.add("nomeCategoriaServico");
        fields.add("idServico");
        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, fields);
        } else {
            return null;
        }

    }

    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorFase(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("select  fase.nomefase, count(*)  from " + getTableName() + " solicitacaoservico ");
        sql.append("inner join  faseservico fase   on solicitacaoservico.idfaseatual = fase.idfase ");
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("INNER JOIN empregados empregados ");
            sql.append("           ON empregados.idempregado = solicitacaoservico.idsolicitante ");
            sql.append("INNER JOIN prioridadeservicounidade prioridadeservicounidade ");
            sql.append("           ON prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
            sql.append("              AND prioridadeservicounidade.idunidade = empregados.idunidade ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND   servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND   solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");
        sql.append(" group by fase.nomefase");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("fase");
        listRetorno.add("quantidadeFase");
        if (lista != null && !lista.isEmpty()) {
            List listaQuantidadeSolicitacaoPorPrioridade = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
            return listaQuantidadeSolicitacaoPorPrioridade;

        } else {
            return null;
        }
    }

    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorGrupo(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        List listaQuantidadeSolicitacaoPorGrupo = new ArrayList();
        StringBuilder sql = new StringBuilder();

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 23/10/2013 - Horário: 10h47min - ID Citsmart: 120770 Motivo/Comentário: O grupo estava utilizando INNER JOIN e quando não existia relacionamento o
         * resultado do grupo não era retornado.
         */
        sql.append("select (CASE WHEN grupo.sigla IS NULL THEN 'SEM ATRIBUIÇÃO' ELSE grupo.sigla end), servico.nomeservico, count(*) from solicitacaoservico solicitacaoservico  ");
        sql.append("left join grupo grupo  on solicitacaoservico.idgrupoatual = grupo.idgrupo  ");
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        sql.append("inner join servico servico on servico.idservico = servicocontrato.idservico  ");
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("left join prioridadeservicounidade prioridadeservicounidade on prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND  servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND   solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");

        sql.append(" group by grupo.sigla, servico.nomeservico");
        sql.append(" order by grupo.sigla, servico.nomeservico");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("grupo");
        listRetorno.add("servico");
        listRetorno.add("quantidadeServico");
        if (lista != null && !lista.isEmpty()) {
            listaQuantidadeSolicitacaoPorGrupo = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
        }
        return listaQuantidadeSolicitacaoPorGrupo;
    }

    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorHoraAbertura(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append("select datepart(hour, solicitacaoservico.datahorasolicitacao) as int, count(*) from " + getTableName() + " solicitacaoservico ");
        } else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            sql.append("select cast(extract(hour from solicitacaoservico.datahorasolicitacao) as int), count(*) from " + getTableName() + " solicitacaoservico ");
        } else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("select to_char(solicitacaoservico.datahorasolicitacao, 'hh24') datahorasolicitacao, count(*) from " + getTableName() + " solicitacaoservico ");
        } else {
            sql.append("select extract(hour from solicitacaoservico.datahorasolicitacao), count(*) from " + getTableName() + " solicitacaoservico ");
        }
        // if (solicitacaoDto.getIdContrato() != null) {
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        // }
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("INNER JOIN empregados empregados ");
            sql.append("           ON empregados.idempregado = solicitacaoservico.idsolicitante ");
            sql.append("INNER JOIN prioridadeservicounidade prioridadeservicounidade ");
            sql.append("           ON prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
            sql.append("              AND prioridadeservicounidade.idunidade = empregados.idunidade ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append("group by datepart(hour, solicitacaoservico.datahorasolicitacao)");
        } else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append(" group by solicitacaoservico.datahorasolicitacao ");
        } else {
            sql.append(" group by extract(hour from solicitacaoservico.datahorasolicitacao)");
        }

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("horaAbertura");
        listRetorno.add("quantidadeHoraAbertura");
        if (lista != null && !lista.isEmpty()) {
            List listaQuantidadeSolicitacaoPorTipo = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
            return listaQuantidadeSolicitacaoPorTipo;
        } else {
            return null;
        }
    }

    /**
     * Retorna a quantidade de solicitações separado por item de configuração.
     *
     * @param solicitacaoDto
     * @return Collection<RelatorioQuantitativoSolicitacaoDTO>
     * @throws Exception
     * @author rodrigo.acorse - Data: 23/10/2013 - Horário: 10h47min - ID Citsmart: 120770
     */
    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorItemConfiguracao(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 23/10/2013 - Horário: 10h47min - ID Citsmart: 120770 Motivo/Comentário: O item de configuração estava utilizando INNER JOIN e quando não existia
         * relacionamento o resultado do item não era retornado.
         */
        sql.append("select (CASE WHEN itemconfiguracao.identificacao IS NULL THEN 'SEM ATRIBUIÇÃO' ELSE itemconfiguracao.identificacao end), count(*)   from solicitacaoservico solicitacaoservico  ");
        sql.append("left join itemconfiguracao itemconfiguracao  on solicitacaoservico.iditemconfiguracao = itemconfiguracao.iditemconfiguracao ");
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("INNER JOIN empregados empregados ");
            sql.append("           ON empregados.idempregado = solicitacaoservico.idsolicitante ");
            sql.append("INNER JOIN prioridadeservicounidade prioridadeservicounidade ");
            sql.append("           ON prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
            sql.append("              AND prioridadeservicounidade.idunidade = empregados.idunidade ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND   servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND   solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");

        sql.append(" group by itemconfiguracao.identificacao ");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("itemConfiguracao");
        listRetorno.add("quantidadeItemConfiguracao");
        if (lista != null && !lista.isEmpty()) {
            List listaQuantidadeSolicitacaoPorItemConfiguracao = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
            return listaQuantidadeSolicitacaoPorItemConfiguracao;
        } else {
            return null;
        }
    }

    /**
     * Retorna a quantidade de solicitações separado por item de configuração. Alterado o script para mostrar todas os serviços com a origem fazia com o nome 'SEM ATRIBUIÇÃO'
     *
     * @param solicitacaoDto
     * @return Collection<RelatorioQuantitativoSolicitacaoDTO>
     * @throws Exception
     * @author bruno.aquino - Data: 23/10/2013 - Horário: 15h32min - ID Citsmart: 120770|122034
     */
    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorOrigem(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select  (CASE WHEN origem.descricao IS NULL THEN 'SEM ATRIBUIÇÃO' ELSE origem.descricao end), count(*)  from " + getTableName() + " solicitacaoservico ");
        sql.append("left join origematendimento origem   on solicitacaoservico.idorigem = origem.idorigem ");
        // if (solicitacaoDto.getIdContrato() != null) {
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        // }
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("INNER JOIN empregados empregados ");
            sql.append("           ON empregados.idempregado = solicitacaoservico.idsolicitante ");
            sql.append("INNER JOIN prioridadeservicounidade prioridadeservicounidade ");
            sql.append("           ON prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
            sql.append("              AND prioridadeservicounidade.idunidade = empregados.idunidade ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND   servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND   solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");

        sql.append(" group by origem.descricao");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("origem");
        listRetorno.add("quantidadeOrigem");
        if (lista != null && !lista.isEmpty()) {
            List listaQuantidadeSolicitacaoPorOrigem = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
            return listaQuantidadeSolicitacaoPorOrigem;

        } else {
            return null;
        }
    }

    /**
     * Retorna a quantidade de solicitações de serviço por pesquisa de satisfação
     *
     * @param solicitacaoDto
     * @return Collection<RelatorioQuantitativoSolicitacaoDTO>
     * @throws Exception
     */
    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorPesquisaSatisfacao(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        List listaQuantidadeSolicitacaoPorGrupo = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select (CASE WHEN grupo.sigla IS NULL THEN 'SEM ATRIBUIÇÃO' ELSE grupo.sigla end), servico.nomeservico, count(*) from solicitacaoservico solicitacaoservico  ");
        sql.append("inner join pesquisasatisfacao pesquisasatisfacao  on pesquisasatisfacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico  ");
        sql.append("left join grupo grupo  on solicitacaoservico.idgrupoatual = grupo.idgrupo  ");
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        sql.append("inner join servico servico on servico.idservico = servicocontrato.idservico  ");
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("left join prioridadeservicounidade prioridadeservicounidade on prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND  servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND   solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");

        sql.append(" group by grupo.sigla, servico.nomeservico");
        sql.append(" order by grupo.sigla, servico.nomeservico");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("grupoPesquisaSatisfacao");
        listRetorno.add("servicoPesquisaSatisfacao");
        listRetorno.add("quantidadePesquisaSatisfacao");
        if (lista != null && !lista.isEmpty()) {
            listaQuantidadeSolicitacaoPorGrupo = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
        }
        return listaQuantidadeSolicitacaoPorGrupo;
    }

    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorPrioridade(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select  prioridade.nomeprioridade, count(*)  from " + getTableName() + " solicitacaoservico ");
        sql.append("inner join prioridade prioridade   on solicitacaoservico.idprioridade = prioridade.idprioridade ");
        // if (solicitacaoDto.getIdContrato() != null) {
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        // }
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("INNER JOIN empregados empregados ");
            sql.append("           ON empregados.idempregado = solicitacaoservico.idsolicitante ");
            sql.append("INNER JOIN prioridadeservicounidade prioridadeservicounidade ");
            sql.append("           ON prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
            sql.append("              AND prioridadeservicounidade.idunidade = empregados.idunidade ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND   servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND   solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");

        sql.append(" group by prioridade.nomeprioridade");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("prioridade");
        listRetorno.add("quantidadePrioridade");
        if (lista != null && !lista.isEmpty()) {
            List listaQuantidadeSolicitacaoPorPrioridade = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
            return listaQuantidadeSolicitacaoPorPrioridade;

        } else {
            return null;
        }
    }

    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorResponsavel(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select  ");
        sql.append("	ltrim(usuario.nome), count(*) ");
        sql.append("from ");
        sql.append("	solicitacaoservico solicitacaoservico ");
        sql.append("inner join ");
        sql.append("	execucaosolicitacao execucaosolicitacao on execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join ");
        sql.append("	bpm_itemtrabalhofluxo bpm_itemtrabalhofluxo ON bpm_itemtrabalhofluxo.idinstancia = execucaosolicitacao.idinstanciafluxo ");
        sql.append("inner join ");
        sql.append("	usuario usuario ON bpm_itemtrabalhofluxo.idresponsavelatual = usuario.idusuario ");
        sql.append("inner join ");
        sql.append("	servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("inner join ");
            sql.append("	empregados empregados on empregados.idempregado = solicitacaoservico.idsolicitante ");
            sql.append("inner join prioridadeservicounidade prioridadeservicounidade ");
            sql.append("	on prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato and prioridadeservicounidade.idunidade = empregados.idunidade ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND   servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND   solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" and solicitacaoservico.idtipodemandaservico is not null ");
        sql.append(" and bpm_itemtrabalhofluxo.situacao in ( '"+SituacaoItemTrabalho.Executado+"' , '"+SituacaoItemTrabalho.EmAndamento+"' ) ");
        sql.append(" and bpm_itemtrabalhofluxo.datahorafinalizacao is null ");

        sql.append(" group by usuario.nome order by ltrim(usuario.nome) ");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("responsavel");
        listRetorno.add("quantidadeResponsavel");
        if (lista != null && !lista.isEmpty()) {
            List listaQuantidadeSolicitacaoSolicitante = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
            return listaQuantidadeSolicitacaoSolicitante;
        } else {
            return null;
        }
    }

    /* Seleciona o servico e a quantidade de servicos dentro das solicitações por contrato */
    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorServico(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
        StringBuilder sql = new StringBuilder();
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 31/10/2013 - Horário: 15h35min - ID Citsmart: 120770 Motivo/Comentário: Foi adicionado um join com o fluxo de trabalho e novas cláusulas foram no where
         * para garantir a consistência do relatório.
         */

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append("select * from (SELECT nomeServico, tiposervico.nometiposervico, servicocontrato.idServico, count(servicocontrato.idServico) cont, ");
            sql.append(" ROW_NUMBER() OVER(order by servicocontrato.idServico) as RowNum ");
        } else {
            sql.append("select * from (SELECT nomeServico, tiposervico.nometiposervico, servicocontrato.idServico, count(servicocontrato.idServico) cont ");
        }
        sql.append("FROM " + getTableName() + " solicitacaoservico ");
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        sql.append("INNER JOIN servico ON servicocontrato.idservico = servico.idservico ");
        sql.append("INNER JOIN tiposervico ON tiposervico.idtiposervico = servico.idtiposervico ");
        sql.append("INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        /*
         * Apenas registros de contratos não excluídos
         */
        sql.append("and (upper(contratos.deleted) = 'N' or contratos.deleted is null) ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoServicoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoServicoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoServicoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoServicoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoServicoDto.getIdContrato() != null) {
            sql.append("AND  solicitacaoservico.idservicocontrato in (select servicocontrato.idservicocontrato from servicocontrato servicocontrato where servicocontrato.idcontrato = ?) ");
            parametro.add(solicitacaoServicoDto.getIdContrato());
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");

        /*
         * Desenvolvedor: Thiago Matias - Data: 29/10/2013 - Horário: 11h00 - ID Citsmart: 122025 Motivo/Comentário: Adicionado
         * "AND solicitacaoservico.situacao != 'EmAndamento' AND solicitacaoservico.situacao != 'Suspensa'" para não retornar solicitações de serviço em andamento ou suspensa
         */
        sql.append(" AND solicitacaoservico.situacao in ('Fechada', 'Cancelada', 'Resolvida') ");

        if (solicitacaoServicoDto.getNumeroRegistros() == null || solicitacaoServicoDto.getNumeroRegistros().equals("")) {
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
                sql.append(" GROUP BY nomeServico, tiposervico.nometiposervico, servicocontrato.idServico ) as aux ORDER BY cont DESC");
            } else {
                sql.append(" GROUP BY nomeServico, tiposervico.nometiposervico, servicocontrato.idServico ORDER BY cont DESC) aux");
            }
        } else {
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append(" GROUP BY nomeServico, tiposervico.nometiposervico, servicocontrato.idServico ORDER BY cont DESC) aux where rownum <= " + solicitacaoServicoDto.getNumeroRegistros() + " ");
            } else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
                sql.append("GROUP BY nomeservico, servicocontrato.idServico,tiposervico.nometiposervico ) as solicitacaoservico where solicitacaoservico.RowNum BETWEEN 1 and "
                        + solicitacaoServicoDto.getNumeroRegistros() + " ");

            } else {
                sql.append(" GROUP BY nomeservico, servicocontrato.idServico,tiposervico.nometiposervico ORDER BY cont DESC) aux LIMIT " + solicitacaoServicoDto.getNumeroRegistros() + " ");
            }

        }

        List list = execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("nomeServico");
        listRetorno.add("nometiposervico");
        listRetorno.add("idServico");
        listRetorno.add("quantidadeServico");

        if (list != null && !list.isEmpty()) {
            List listaQuantidadeSolicitacaoServico = this.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, list, listRetorno);
            return listaQuantidadeSolicitacaoServico;
        }
        return null;
    }

    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSituacao(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select solicitacaoservico.situacao, count(*)  from " + getTableName() + " solicitacaoservico   ");
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("INNER JOIN empregados empregados ");
            sql.append("           ON empregados.idempregado = solicitacaoservico.idsolicitante ");
            sql.append("INNER JOIN prioridadeservicounidade prioridadeservicounidade ");
            sql.append("           ON prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
            sql.append("              AND prioridadeservicounidade.idunidade = empregados.idunidade ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND   servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");

        sql.append(" group by solicitacaoservico.situacao");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("situacao");
        listRetorno.add("quantidadeSituacao");
        if (lista != null && !lista.isEmpty()) {
            List listaQuantidadeSolicitacaoPorSituacao = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
            return listaQuantidadeSolicitacaoPorSituacao;
        } else {
            return null;
        }
    }

    public List<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSituacaoSLA(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List listaQuantidadeSolicitacaoPorPrioridade = new ArrayList();
        List parametro = new ArrayList();

        // String dataInicio = solicitacaoDto.getDataInicio().toString();
        // String dataFim = (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE) ? solicitacaoDto.getDataFim() : transformaHoraFinal(solicitacaoDto.getDataFim())).toString();

        StringBuilder from_where = new StringBuilder();

        from_where.append("FROM " + getTableName() + " solicitacaoservico ");
        from_where.append("INNER JOIN execucaosolicitacao exs ON exs.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");

        if (solicitacaoDto.getIdContrato() != null) {
            from_where.append("INNER JOIN servicocontrato servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        }
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            from_where.append("INNER JOIN empregados empregados ");
            from_where.append("           ON empregados.idempregado = solicitacaoservico.idsolicitante ");
            from_where.append("INNER JOIN prioridadeservicounidade prioridadeservicounidade ");
            from_where.append("           ON prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
            from_where.append("              AND prioridadeservicounidade.idunidade = empregados.idunidade ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            from_where.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            from_where.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            from_where.append("AND prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            from_where.append("AND prioridadeservicounidade.idprioridade <> 1 ");
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            from_where.append("AND servicocontrato.idcontrato = " + solicitacaoDto.getIdContrato() + " ");
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            from_where.append("AND solicitacaoservico.situacao LIKE '" + solicitacaoDto.getSituacao() + "' ");
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        from_where.append(" AND solicitacaoservico.idtipodemandaservico is not null ");

        StringBuilder sql = new StringBuilder();
        // quantidade de solcitacoes ATRASADAS
        sql.append("SELECT Count(*), 'a' ");
        sql.append(from_where);
        sql.append(" AND CASE ");
        sql.append("       WHEN solicitacaoservico.datahorafim IS NOT NULL THEN solicitacaoservico.datahorafim ");
        sql.append("       ELSE CURRENT_TIMESTAMP ");
        sql.append("     END > solicitacaoservico.datahoralimite ");
        sql.append("UNION ");
        // quantidade de solcitacoes DENTRO DO PRAZO
        sql.append("SELECT Count(*), 'b' ");
        sql.append(from_where);
        sql.append(" AND CASE ");
        sql.append("       WHEN solicitacaoservico.datahorafim IS NOT NULL THEN solicitacaoservico.datahorafim ");
        sql.append("       ELSE CURRENT_TIMESTAMP ");
        sql.append("     END <= solicitacaoservico.datahoralimite ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        // List lista = this.execSQL(sql.toString(), null);
        listRetorno.add("quantidadeSituacaoSLA");
        if (lista != null && !lista.isEmpty()) {
            listaQuantidadeSolicitacaoPorPrioridade = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
        }
        return listaQuantidadeSolicitacaoPorPrioridade;
    }

    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorSolicitante(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select ltrim(empregado.nome), count(*) from solicitacaoservico solicitacaoservico ");
        sql.append("inner join empregados empregado on solicitacaoservico.idsolicitante = empregado.idempregado ");
        /*
         * if (solicitacaoDto.getIdContrato() != null) { sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato "); }
         */
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("INNER JOIN empregados empregados ");
            sql.append("           ON empregados.idempregado = solicitacaoservico.idsolicitante ");
            sql.append("INNER JOIN prioridadeservicounidade prioridadeservicounidade ");
            sql.append("           ON prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
            sql.append("              AND prioridadeservicounidade.idunidade = empregados.idunidade ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where solicitacaoservico.idsolicitante = empregado.idempregado AND to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append("where solicitacaoservico.idsolicitante = empregado.idempregado AND solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.idsolicitante = empregado.idempregado AND solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND   servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }

        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND   solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");

        sql.append(" group by empregado.nome order by ltrim(empregado.nome) ");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("solicitante");
        listRetorno.add("quantidadeSolicitante");
        if (lista != null && !lista.isEmpty()) {
            List listaQuantidadeSolicitacaoSolicitante = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
            return listaQuantidadeSolicitacaoSolicitante;
        } else {
            return null;
        }
    }

    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorTipo(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select  tipodemandaservico.nometipodemandaservico, count(*)  from " + getTableName() + " solicitacaoservico ");
        // if (solicitacaoDto.getIdContrato() != null) {
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        // }
        sql.append("inner join tipodemandaservico tipodemandaservico  on solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("INNER JOIN empregados empregados ");
            sql.append("           ON empregados.idempregado = solicitacaoservico.idsolicitante ");
            sql.append("INNER JOIN prioridadeservicounidade prioridadeservicounidade ");
            sql.append("           ON prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
            sql.append("              AND prioridadeservicounidade.idunidade = empregados.idunidade ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND   servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND   solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }
        sql.append(" group by tipodemandaservico.nometipodemandaservico");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("tipo");
        listRetorno.add("quantidadeTipo");
        if (lista != null && !lista.isEmpty()) {
            List listaQuantidadeSolicitacaoPorTipo = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
            return listaQuantidadeSolicitacaoPorTipo;
        } else {
            return null;
        }
    }

    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaQuantidadeSolicitacaoPorTipoServico(SolicitacaoServicoDTO solicitacaoDto) throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("select tiposervico.nometiposervico, count(*)  from " + getTableName() + " solicitacaoservico ");
        sql.append("INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("inner join servicocontrato servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        sql.append("inner join servico servico on servico.idservico = servicocontrato.idservico  ");
        sql.append("inner join tiposervico tiposervico on tiposervico.idtiposervico = servico.idtiposervico ");
        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario()) || "usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append("left join prioridadeservicounidade prioridadeservicounidade on prioridadeservicounidade.idservicocontrato = solicitacaoservico.idservicocontrato ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if ("usuarioVip".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade = 1 ");
        } else if ("usuarioNormal".equals(solicitacaoDto.getTipoUsuario())) {
            sql.append(" and prioridadeservicounidade.idprioridade <> 1 ");
        }
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoDto.getDataFim()));
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços que
         * foram deletados. Todos devem ser exibidos.
         */
        if (solicitacaoDto.getIdContrato() != null) {
            sql.append("AND   servicocontrato.idcontrato = ? ");
            parametro.add(solicitacaoDto.getIdContrato());
        }
        if (solicitacaoDto.getSituacao() != null && !solicitacaoDto.getSituacao().isEmpty()) {
            sql.append("AND   solicitacaoservico.situacao = ? ");
            parametro.add(solicitacaoDto.getSituacao());
        }
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h17min - ID Citsmart: 120770 Motivo/Comentário: Adicionado "AND solicitacaoservico.idtipodemandaservico is not null" para evitar
         * retornar solicitações de serviço sem tipo demanda (inconsistentes)
         */
        sql.append(" AND solicitacaoservico.idtipodemandaservico is not null ");
        sql.append(" group by tiposervico.nometiposervico");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("tipoServico");
        listRetorno.add("quantidadeTipoServico");
        if (lista != null && !lista.isEmpty()) {
            List listaQuantidadeSolicitacaoPorPrioridade = engine.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, lista, listRetorno);
            return listaQuantidadeSolicitacaoPorPrioridade;

        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listarSLA() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("select distinct prazohh, prazomm from solicitacaoservico order by prazohh,prazomm");

        List lista = this.execSQL(sql.toString(), null);
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        List result = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        if (result != null) {
            return result;
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listarSolicitacoesAbertasEmAndamentoPorGrupo(int idGrupoAtual, String situacaoSla) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *  from solicitacaoservico ss,tipodemandaservico td where situacao LIKE 'EmAndamento' AND ss.datahorafim IS NULL AND ss.idgrupoatual = ? AND situacaosla LIKE ? AND td.classificacao LIKE 'R'");
        parametro.add(idGrupoAtual);
        parametro.add(situacaoSla);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listarSolicitacoesMultadasSuspensasPorGrupo(int idGrupoAtual, String situacaoSla) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT *  from solicitacaoservico ss,tipodemandaservico td where situacao like 'Suspensa' and ss.datahorafim is null and ss.idgrupoatual = ? and td.classificacao LIKE 'R' and situacaosla LIKE ?");
        parametro.add(idGrupoAtual);
        parametro.add(situacaoSla);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public String listaServico(int idsolicitacaoservico) throws Exception {
        List parametro = new ArrayList();
        String sql = "SELECT s.nomeservico FROM solicitacaoservico ss, servicocontrato sc, servico s "
                + "where ss.idservicocontrato =  sc.idservicocontrato and sc.idservico = s.idservico and ss.idsolicitacaoservico = ?";
        parametro.add(idsolicitacaoservico);
        List resultado = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("servico");
        List result = engine.listConvertion(SolicitacaoServicoDTO.class, resultado, listRetorno);
        SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) result.get(0);
        String servico = solicitacaoServicoDTO.getServico();
        return servico;

    }

    /**
     * Retorna uma lista de Serviços que estejam associada a uma solicitação serviço.
     *
     * @param relatorioAnaliseServicoDto
     * @return Collection<RelatorioAnaliseServicoDTO>
     * @throws Exception
     * @author thays.araujo
     */
    public Collection<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO> listaServicoPorSolicitacaoServico(RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO relatorioAnaliseServicoDto)
            throws Exception {
        List parametro = new ArrayList();

        StringBuilder sql = new StringBuilder();

        sql.append("select    servicocontrato.idServico from solicitacaoservico solicitacaoservico  ");
        sql.append("INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        sql.append("INNER JOIN servico ON servicocontrato.idservico = servico.idservico ");
        sql.append("INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("where to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("where solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?  ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(relatorioAnaliseServicoDto.getDataInicio()));
            parametro.add(formatter.format(relatorioAnaliseServicoDto.getDataFim()));
        } else {
            parametro.add(relatorioAnaliseServicoDto.getDataInicio());
            parametro.add(transformaHoraFinal(relatorioAnaliseServicoDto.getDataFim()));
        }

        if (relatorioAnaliseServicoDto.getIdContrato() != null) {
            sql.append(" and contratos.idcontrato = ? ");
            parametro.add(relatorioAnaliseServicoDto.getIdContrato());
        }

        sql.append("group by  servicocontrato.idServico ");

        List lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("idServico");

        if (lista != null && !lista.isEmpty()) {
            Collection<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO> listaServicoPorSolicitacaoServico = new ArrayList<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO>();
            listaServicoPorSolicitacaoServico = engine.listConvertion(RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO.class, lista, listRetorno);
            return listaServicoPorSolicitacaoServico;
        } else {
            return null;
        }
    }

    public Collection<RelatorioQuantitativoSolicitacaoDTO> listaServicosAbertosAprovados(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {

        StringBuilder sql = new StringBuilder();
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        /**
         * Checa se há limite para pesquisa
         * @author thyen.chang
         */
        boolean seLimita = !solicitacaoServicoDto.getTopList().equals(0);
        /**
         * Limita pesquisa no SQLServer
         * @author thyen.chang
         */
        sql.append("select ");
        if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))){
            sql.append("TOP "+solicitacaoServicoDto.getTopList().toString()+" ");
        }
        sql.append("solicitacaoservico.idsolicitacaoservico, servico.nomeservico,empregados.nome,solicitacaoservico.datahorasolicitacao, solicitacaoservico.situacao, contratos.numero ");
        sql.append("  from solicitacaoservico  ");
        sql.append(" inner join servicocontrato ");
        sql.append("         on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        sql.append(" inner join servico ");
        sql.append("	     on servicocontrato.idservico = servico.idservico ");
        sql.append(" inner join contratos ");
        sql.append("         on contratos.idcontrato = servicocontrato.idcontrato ");
        sql.append("        and (upper(contratos.deleted)  = 'N' or contratos.deleted is null) ");
        sql.append(" inner join empregados ");
        sql.append("         on solicitacaoservico.idsolicitante = empregados.idempregado ");

        /*
         * Aprovado
         */
        if (solicitacaoServicoDto.getSituacaoAprovacao().equals("1")) {

            sql.append("INNER JOIN aprovacaosolicitacaoservico aprovacaosolicitacaoservico ");
            sql.append("ON aprovacaosolicitacaoservico.idaprovacaosolicitacaoservico = solicitacaoservico.idultimaaprovacao ");
            sql.append("WHERE ");
            /**
             * Limita pesquisa no Oracle
             * @author thyen.chang
             */
            if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))){
                sql.append(" ROWNUM <= ? AND ");
                parametro.add(solicitacaoServicoDto.getTopList());
            }
            sql.append("aprovacaosolicitacaoservico.aprovacao = 'A' ");
        }

        /*
         * Reprovado
         */
        if (solicitacaoServicoDto.getSituacaoAprovacao().equals("2")) {

            sql.append("INNER JOIN execucaosolicitacao execucaosolicitacao ");
            sql.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
            sql.append("INNER JOIN bpm_itemtrabalhofluxo bpm_itemtrabalhofluxo ");
            sql.append("ON bpm_itemtrabalhofluxo.idinstancia = execucaosolicitacao.idinstanciafluxo ");
            sql.append("INNER JOIN bpm_elementofluxo bpm_elementofluxo ");
            sql.append("ON bpm_elementofluxo.idelemento = bpm_itemtrabalhofluxo.idelemento ");
            sql.append("WHERE ");
            /**
             * Limita pesquisa no Oracle
             * @author thyen.chang
             */
            if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))){
                sql.append(" ROWNUM <= ? AND ");
                parametro.add(solicitacaoServicoDto.getTopList());
            }
            sql.append("bpm_elementofluxo.documentacao like 'Aprovar solicitação' ");
            sql.append("AND bpm_itemtrabalhofluxo.situacao NOT IN ( 'Executado', 'Cancelado' ) ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("and to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
        } else {
            sql.append("and solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoServicoDto.getDataInicio()));
            parametro.add(formatter.format(solicitacaoServicoDto.getDataFim()));
        } else {
            parametro.add(solicitacaoServicoDto.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoServicoDto.getDataFim()));
        }

        if (solicitacaoServicoDto.getIdContrato() != null) {
            sql.append("AND  solicitacaoservico.idservicocontrato in (select servicocontrato.idservicocontrato from servicocontrato servicocontrato where servicocontrato.idcontrato = ?) ");
            parametro.add(solicitacaoServicoDto.getIdContrato());
        }

        sql.append("ORDER BY solicitacaoservico.idsolicitacaoservico");
        /**
         * Limita pesquisa no Postgres e MySQL
         * @author thyen.chang]
         */
        if((seLimita) && ((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL))) ){
            sql.append(" LIMIT ? ");
            parametro.add(solicitacaoServicoDto.getTopList());
        }

        List list = execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("nome");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("situacao");
        listRetorno.add("numero");

        if (list != null && !list.isEmpty()) {
            List listaQuantidadeSolicitacaoServico = this.listConvertion(RelatorioQuantitativoSolicitacaoDTO.class, list, listRetorno);
            return listaQuantidadeSolicitacaoServico;
        }
        return null;
    }

    public Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodo(RelatorioKpiProdutividadeDTO dto) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT solicitacaoservico.idsolicitacaoservico, solicitacaoservico.datahorainicio, solicitacaoservico.datahoralimite, solicitacaoservico.dataHoraFim,solicitacaoservico.prazohh, solicitacaoservico.prazomm ,solicitacaoservico.idtipodemandaservico, servico.nomeServico, servico.idservico ");
        sql.append("FROM solicitacaoservico " + "INNER JOIN servicocontrato ON servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato "
                + "INNER JOIN servico ON servicocontrato.idservico = servico.idservico "
                + "INNER JOIN execucaosolicitacao ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico "
                + "INNER JOIN bpm_itemtrabalhofluxo ON execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia " + "WHERE " + "bpm_itemtrabalhofluxo.idresponsavelatual = ? "
                + "AND solicitacaoservico.datahorainicio BETWEEN ? AND ? ");
        parametro.add(Integer.valueOf(dto.getFuncionario()));
        parametro.add(dto.getDataInicio());
        parametro.add(dto.getDataFim());

        if (dto.getCheckMostrarIncidentes() != null && dto.getCheckMostrarRequisicoes() == null) {
            sql.append("and solicitacaoservico.idtipodemandaservico = ? ;");
            parametro.add(2);
        } else if (dto.getCheckMostrarIncidentes() == null && dto.getCheckMostrarRequisicoes() != null) {
            sql.append("and solicitacaoservico.idtipodemandaservico = ? ;");
            parametro.add(1);
        } else {
            sql.append(";");
        }

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("dataHoraFim");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("idTipoDemandaServico");
        listRetorno.add("nomeServico");
        listRetorno.add("idServico");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listaServicosPorAbertosParaDocumentacao(Date dataIncio, Date dataFim, boolean mostrarIncidentes, boolean mostrarRequisicoes) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT solicitacaoservico.idsolicitacaoservico, solicitacaoservico.datahorainicio, solicitacaoservico.datahoralimite, solicitacaoservico.dataHoraFim,solicitacaoservico.prazohh, solicitacaoservico.prazomm ,solicitacaoservico.idtipodemandaservico, servico.nomeServico, servico.idservico, solicitacaoservico.situacao ");
        sql.append("FROM solicitacaoservico solicitacaoservico"
                + " INNER JOIN servicocontrato ON servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato "
                + "Inner JOIN tipodemandaservico ON tipodemandaservico.idtipodemandaservico = solicitacaoservico.idtipodemandaservico "
                + " INNER JOIN execucaosolicitacao ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico INNER JOIN bpm_itemtrabalhofluxo ON execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia "
                + " INNER JOIN servico ON servicocontrato.idservico = servico.idservico  WHERE "
                + " (servico.nomeServico like 'Fabrica.Sistemas.Documentacao.Manutencao.Corretiva' or servico.nomeServico LIKE 'Fabrica.Sistemas.Documentacao.Manutencao.Evolutiva') "
                + " AND solicitacaoservico.datahorainicio BETWEEN ? AND ? ");
        parametro.add(dataIncio);
        Timestamp dtfim = this.transformaHoraFinal(dataFim);
        parametro.add(dtfim);

        if (mostrarIncidentes == true && mostrarRequisicoes == false) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("I");
        } else if (mostrarIncidentes == false && mostrarRequisicoes == true) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("R");
        } else if (mostrarIncidentes == true && mostrarRequisicoes == true) {
            sql.append(" And (tipodemandaservico.classificacao like ? or tipodemandaservico.classificacao like ? ) ");
            parametro.add("I");
            parametro.add("R");
        }

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("dataHoraFim");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("idTipoDemandaServico");
        listRetorno.add("nomeServico");
        listRetorno.add("idServico");
        listRetorno.add("situacao");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listaServicosPorAbertosPelotesteParaValidacao(Date dataIncio, Date dataFim, boolean mostrarIncidentes, boolean mostrarRequisicoes) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT solicitacaoservico.idsolicitacaoservico, solicitacaoservico.datahorainicio, solicitacaoservico.datahoralimite, solicitacaoservico.dataHoraFim,solicitacaoservico.prazohh,"
                + " solicitacaoservico.prazomm ,solicitacaoservico.idtipodemandaservico, servico.nomeServico, servico.idservico, solicitacaoservico.situacao,solicitacaoservico.idsolicitante ");
        sql.append(" FROM solicitacaoservico solicitacaoservico"
                + " INNER JOIN servicocontrato ON servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato "
                + "Inner JOIN tipodemandaservico ON tipodemandaservico.idtipodemandaservico = solicitacaoservico.idtipodemandaservico "
                + " INNER JOIN execucaosolicitacao ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico INNER JOIN bpm_itemtrabalhofluxo ON execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia "
                + " INNER JOIN servico ON servicocontrato.idservico = servico.idservico  WHERE (servico.nomeServico LIKE '"
                + ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVICO_TIPO_REQUISICAO_TESTE, "Homolocacao.Release.Evolutiva.Aplicativo") + "') "
                + " AND solicitacaoservico.datahorainicio BETWEEN ? AND ? ");
        parametro.add(dataIncio);
        Timestamp dtfim = this.transformaHoraFinal(dataFim);
        parametro.add(dtfim);

        if (mostrarIncidentes == true && mostrarRequisicoes == false) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("I");
        } else if (mostrarIncidentes == false && mostrarRequisicoes == true) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("R");
        } else if (mostrarIncidentes == true && mostrarRequisicoes == true) {
            sql.append(" And (tipodemandaservico.classificacao like ? or tipodemandaservico.classificacao like ? ) ");
            parametro.add("I");
            parametro.add("R");
        }

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("dataHoraFim");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("idTipoDemandaServico");
        listRetorno.add("nomeServico");
        listRetorno.add("idServico");
        listRetorno.add("situacao");
        listRetorno.add("idSolicitante");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodo(Date dataIncio, Date dataFim, int idFuncionario, boolean mostrarIncidentes, boolean mostrarRequisicoes,String situacao)
            throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT solicitacaoservico.idsolicitacaoservico, solicitacaoservico.datahorainicio, solicitacaoservico.datahoralimite, solicitacaoservico.dataHoraFim,solicitacaoservico.prazohh, solicitacaoservico.prazomm ,solicitacaoservico.idtipodemandaservico, servico.nomeServico, servico.idservico ");
        sql.append("FROM solicitacaoservico " + "INNER JOIN servicocontrato ON servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato "
                + "INNER JOIN servico ON servicocontrato.idservico = servico.idservico "
                + "INNER JOIN execucaosolicitacao ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico "
                + "Inner JOIN tipodemandaservico ON tipodemandaservico.idtipodemandaservico = solicitacaoservico.idtipodemandaservico "
                + "INNER JOIN bpm_itemtrabalhofluxo ON execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia "
                + "INNER JOIN bpm_elementofluxo ON bpm_elementofluxo.idelemento = bpm_itemtrabalhofluxo.idelemento "
                + "WHERE " + "bpm_itemtrabalhofluxo.idresponsavelatual = ? AND "
                + "bpm_elementofluxo.nome like '%"+ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.BPM_ELEMENTO_EXECUCAO, "Atender solicita")
                + "%' AND solicitacaoservico.datahorainicio BETWEEN ? AND ?  ");
        parametro.add(idFuncionario);/*%Atender solicitacao%Trabalhos*/
        parametro.add(dataIncio);
        Timestamp dtfim = this.transformaHoraFinal(dataFim);
        parametro.add(dtfim);

        if (mostrarIncidentes == true && mostrarRequisicoes == false) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("I");
        } else if (mostrarIncidentes == false && mostrarRequisicoes == true) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("R");
        } else if (mostrarIncidentes == true && mostrarRequisicoes == true) {
            sql.append(" And (tipodemandaservico.classificacao like ? or tipodemandaservico.classificacao like ? ) ");
            parametro.add("R");
            parametro.add("I");
        }

        if(!situacao.equalsIgnoreCase("")){
            sql.append(" and solicitacaoservico.situacao like ? ");
            parametro.add(situacao);
        }else{
            sql.append(" and (solicitacaoservico.situacao like 'Fechada' or solicitacaoservico.situacao like 'Cancelada' or solicitacaoservico.situacao like 'Resolvida' ) ");
        }

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("dataHoraFim");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("idTipoDemandaServico");
        listRetorno.add("nomeServico");
        listRetorno.add("idServico");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodoDocumentacao(Date dataIncio, Date dataFim, int idFuncionario, boolean mostrarIncidentes, boolean mostrarRequisicoes)
            throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT solicitacaoservico.idsolicitacaoservico, solicitacaoservico.datahorainicio, solicitacaoservico.datahoralimite, solicitacaoservico.dataHoraFim,solicitacaoservico.prazohh, solicitacaoservico.prazomm ,solicitacaoservico.idtipodemandaservico, servico.nomeServico, servico.idservico, solicitacaoservico.situacao ");
        sql.append("FROM solicitacaoservico solicitacaoservico" + " INNER JOIN servicocontrato ON servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato"
                + " INNER JOIN execucaosolicitacao ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico "
                + " INNER JOIN bpm_itemtrabalhofluxo ON execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia"
                + "Inner JOIN tipodemandaservico ON tipodemandaservico.idtipodemandaservico = solicitacaoservico.idtipodemandaservico "
                + " INNER JOIN servico ON servicocontrato.idservico = servico.idservico  WHERE solicitacaoservico.situacao not LIKE 'EmAndamento' and "
                + " bpm_itemtrabalhofluxo.idresponsavelatual = ? "
                + " AND (servico.nomeServico like 'Fabrica.Sistemas.Documentacao.Manutencao.Corretiva' or servico.nomeServico LIKE 'Fabrica.Sistemas.Documentacao.Manutencao.Evolutiva' )"
                + " AND solicitacaoservico.datahorainicio BETWEEN ? AND ? ");
        parametro.add(idFuncionario);
        parametro.add(dataIncio);
        Timestamp dtfim = this.transformaHoraFinal(dataFim);
        parametro.add(dtfim);

        if (mostrarIncidentes == true && mostrarRequisicoes == false) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("I");
        } else if (mostrarIncidentes == false && mostrarRequisicoes == true) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("R");
        } else if (mostrarIncidentes == true && mostrarRequisicoes == true) {
            sql.append(" And (tipodemandaservico.classificacao like = ? or tipodemandaservico.classificacao like = ? ) ");
            parametro.add("I");
            parametro.add("R");
        }

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("dataHoraFim");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("idTipoDemandaServico");
        listRetorno.add("nomeServico");
        listRetorno.add("idServico");
        listRetorno.add("situacao");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listaServicosPorResponsavelNoPeriodoDocumentacaoPorServico(Date dataIncio, Date dataFim, int idFuncionario, boolean mostrarIncidentes,
            boolean mostrarRequisicoes, String listaIdsServicosHomologacaoDocumentacao) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT DISTINCT solicitacaoservico.idsolicitacaoservico, solicitacaoservico.datahorainicio, solicitacaoservico.datahoralimite, solicitacaoservico.dataHoraFim,solicitacaoservico.prazohh, solicitacaoservico.prazomm ,solicitacaoservico.idtipodemandaservico, servico.nomeServico, servico.idservico, solicitacaoservico.situacao ");
        sql.append("FROM solicitacaoservico solicitacaoservico" + " INNER JOIN servicocontrato ON servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato"
                + " INNER JOIN execucaosolicitacao ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico "
                + "Inner JOIN tipodemandaservico ON tipodemandaservico.idtipodemandaservico = solicitacaoservico.idtipodemandaservico "
                + " INNER JOIN bpm_itemtrabalhofluxo ON execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia"
                + " INNER JOIN servico ON servicocontrato.idservico = servico.idservico  WHERE solicitacaoservico.situacao not LIKE 'EmAndamento' and "
                + " bpm_itemtrabalhofluxo.idresponsavelatual = ? " + " AND solicitacaoservico.datahorainicio BETWEEN ? AND ? AND ");
        parametro.add(idFuncionario);
        parametro.add(dataIncio);
        Timestamp dtfim = this.transformaHoraFinal(dataFim);
        parametro.add(dtfim);

        int aux = 1;
        String[] listaIdServicos = null;
        if (listaIdsServicosHomologacaoDocumentacao != null && listaIdsServicosHomologacaoDocumentacao.contains(";")) {
            listaIdServicos = listaIdsServicosHomologacaoDocumentacao.split(";");
            sql.append(" ( ");
            for (int i = 0; i < listaIdServicos.length; i++) {
                if (listaIdServicos.length > aux) {
                    sql.append(" servico.idservico = ? or ");
                    parametro.add(listaIdServicos[i]);
                } else {
                    sql.append(" servico.idservico = ? ");
                    parametro.add(listaIdServicos[i]);
                }
                aux++;
            }
            sql.append(" ) ");
        } else {
            sql.append(" (servico.idservico = ? ) ");
            parametro.add(listaIdsServicosHomologacaoDocumentacao);
        }

        if (mostrarIncidentes == true && mostrarRequisicoes == false) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("I");
        } else if (mostrarIncidentes == false && mostrarRequisicoes == true) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("R");
        } else if (mostrarIncidentes == true && mostrarRequisicoes == true) {
            sql.append(" And (tipodemandaservico.classificacao like ? or tipodemandaservico.classificacao like ? ) ");
            parametro.add(1);
            parametro.add(3);
        }

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("dataHoraFim");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("idTipoDemandaServico");
        listRetorno.add("nomeServico");
        listRetorno.add("idServico");
        listRetorno.add("situacao");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listaServicosPorSolicitanteNoPeriodoEnviadosAoteste(Date dataIncio, Date dataFim, int idFuncionario, boolean mostrarIncidentes, boolean mostrarRequisicoes)
            throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT solicitacaoservico.idsolicitacaoservico, solicitacaoservico.datahorainicio, solicitacaoservico.datahoralimite, solicitacaoservico.dataHoraFim,solicitacaoservico.prazohh, solicitacaoservico.prazomm ,solicitacaoservico.idtipodemandaservico, servico.nomeServico, servico.idservico, solicitacaoservico.situacao ");
        sql.append("FROM solicitacaoservico solicitacaoservico"
                + " INNER JOIN servicocontrato ON servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato "
                + "Inner JOIN tipodemandaservico ON tipodemandaservico.idtipodemandaservico = solicitacaoservico.idtipodemandaservico "
                + " INNER JOIN servico ON servicocontrato.idservico = servico.idservico  WHERE "
                + " solicitacaoservico.idsolicitante = ? "
                + " AND (servico.nomeServico like 'Fabrica.Sistemas.Testes.Manutencao.Corretiva' or servico.nomeServico LIKE 'Fabrica.Sistemas.Testes.Manutencao.Evolutiva' or servico.nomeServico LIKE 'Fabrica.Sistemas.Testes.Incidente') "
                + " AND solicitacaoservico.datahorainicio BETWEEN ? AND ? ");
        parametro.add(idFuncionario);
        parametro.add(dataIncio);
        Timestamp dtfim = this.transformaHoraFinal(dataFim);
        parametro.add(dtfim);

        if (mostrarIncidentes == true && mostrarRequisicoes == false) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("I");
        } else if (mostrarIncidentes == false && mostrarRequisicoes == true) {
            sql.append(" and tipodemandaservico.classificacao like ? ");
            parametro.add("R");
        } else if (mostrarIncidentes == true && mostrarRequisicoes == true) {
            sql.append(" And (tipodemandaservico.classificacao like ? or tipodemandaservico.classificacao like ? ) ");
            parametro.add(1);
            parametro.add(3);
        }
        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("dataHoraFim");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("idTipoDemandaServico");
        listRetorno.add("nomeServico");
        listRetorno.add("idServico");
        listRetorno.add("situacao");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<RelatorioQuantitativoRetornoDTO> listaServicosRetorno(SolicitacaoServicoDTO solicitacaoServicoDTO, String grupoRetorno) throws Exception {
        List parametro = new ArrayList();

        StringBuilder sql = new StringBuilder();

        sql.append("select distinct bpm_itemtrabalhofluxo.idinstancia,ocorrenciasolicitacao.idsolicitacaoservico,bpm_itemtrabalhofluxo.iditemtrabalho,bpm_elementofluxo.idelemento,bpm_elementofluxo.idfluxo   ");
        sql.append("from bpm_itemtrabalhofluxo    ");
        sql.append("INNER JOIN bpm_elementofluxo bpm_elementofluxo 	   ");
        sql.append("ON bpm_elementofluxo.idelemento = bpm_itemtrabalhofluxo.idelemento    ");
        sql.append("INNER JOIN execucaosolicitacao execucaosolicitacao    ");
        sql.append("ON bpm_itemtrabalhofluxo.idinstancia = execucaosolicitacao.idinstanciafluxo    ");
        sql.append("INNER JOIN ocorrenciasolicitacao ocorrenciasolicitacao    ");
        sql.append("ON ocorrenciasolicitacao.idsolicitacaoservico = execucaosolicitacao.idsolicitacaoservico    ");
        sql.append("INNER JOIN solicitacaoservico solicitacaoservico      ");
        sql.append("ON solicitacaoservico.idsolicitacaoservico = ocorrenciasolicitacao.idsolicitacaoservico      ");
        sql.append("INNER JOIN historicosolicitacaoservico historicosolicitacaoservico      ");
        sql.append("ON historicosolicitacaoservico.idocorrencia = ocorrenciasolicitacao.idocorrencia      ");
        sql.append("WHERE bpm_elementofluxo.nome  like '" + grupoRetorno + "' ");
        sql.append("AND ocorrenciasolicitacao.ocorrencia IS NOT NULL      ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append(" AND to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoServicoDTO.getDataInicio()));
            parametro.add(formatter.format(solicitacaoServicoDTO.getDataFim()));
        } else {
            sql.append(" AND solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?  ");
            parametro.add(solicitacaoServicoDTO.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoServicoDTO.getDataFim()));
        }
        sql.append("AND bpm_itemtrabalhofluxo.idinstancia in(   ");
        sql.append("select idinstancia from (   ");
        sql.append("select bpm_itemtrabalhofluxo.idinstancia,ocorrenciasolicitacao.idsolicitacaoservico,bpm_itemtrabalhofluxo.iditemtrabalho   ");
        sql.append("from bpm_itemtrabalhofluxo    ");
        sql.append("INNER JOIN bpm_elementofluxo bpm_elementofluxo 	   ");
        sql.append("ON bpm_elementofluxo.idelemento = bpm_itemtrabalhofluxo.idelemento    ");
        sql.append("INNER JOIN execucaosolicitacao execucaosolicitacao    ");
        sql.append("ON bpm_itemtrabalhofluxo.idinstancia = execucaosolicitacao.idinstanciafluxo     ");
        sql.append("INNER JOIN ocorrenciasolicitacao ocorrenciasolicitacao    ");
        sql.append("ON ocorrenciasolicitacao.idsolicitacaoservico = execucaosolicitacao.idsolicitacaoservico     ");
        sql.append("INNER JOIN solicitacaoservico solicitacaoservico      ");
        sql.append("ON solicitacaoservico.idsolicitacaoservico = ocorrenciasolicitacao.idsolicitacaoservico    ");
        sql.append("INNER JOIN historicosolicitacaoservico historicosolicitacaoservico      ");
        sql.append("ON historicosolicitacaoservico.idocorrencia = ocorrenciasolicitacao.idocorrencia      ");
        sql.append("WHERE bpm_elementofluxo.nome  like '" + grupoRetorno + "' ");
        sql.append("AND ocorrenciasolicitacao.ocorrencia IS NOT NULL      ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append(" AND to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            parametro.add(formatter.format(solicitacaoServicoDTO.getDataInicio()));
            parametro.add(formatter.format(solicitacaoServicoDTO.getDataFim()));
        } else {
            sql.append(" AND solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?  ");
            parametro.add(solicitacaoServicoDTO.getDataInicio());
            parametro.add(transformaHoraFinal(solicitacaoServicoDTO.getDataFim()));
        }
        if (solicitacaoServicoDTO != null && solicitacaoServicoDTO.getIdGrupoAtual() != null && !StringUtils.isNotBlank(solicitacaoServicoDTO.getIdGrupoAtual().toString())) {
            sql.append(" AND historicosolicitacaoservico.idgrupo = ?  ");
            parametro.add(solicitacaoServicoDTO.getIdGrupoAtual());
        }
        sql.append("group by bpm_itemtrabalhofluxo.iditemtrabalho, bpm_itemtrabalhofluxo.idinstancia,ocorrenciasolicitacao.idsolicitacaoservico )    ");
        sql.append("as tabela    ");
        sql.append("group by idinstancia   ");
        sql.append("having count(idinstancia) > 1)   ");

        List lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("idInstancia");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("idItemTrabalho");
        listRetorno.add("idElemento");
        listRetorno.add("idFluxo");

        if (lista != null && !lista.isEmpty()) {
            Collection<RelatorioQuantitativoRetornoDTO> listaQuantitativoRetorno = new ArrayList<RelatorioQuantitativoRetornoDTO>();
            listaQuantitativoRetorno = engine.listConvertion(RelatorioQuantitativoRetornoDTO.class, lista, listRetorno);
            return listaQuantitativoRetorno;
        } else {
            return null;
        }
    }

    public Collection<RelatorioQuantitativoRetornoDTO> listaServicosRetornoNomeResponsavel(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception {
        List parametro = new ArrayList();

        StringBuilder sql = new StringBuilder();

        sql.append("SELECT usuario.nome,bpm_itemtrabalhofluxo.idresponsavelatual ");
        sql.append("FROM   bpm_itemtrabalhofluxo bpm_itemtrabalhofluxo  ");
        sql.append("INNER JOIN ocorrenciasolicitacao ocorrenciasolicitacao ");
        sql.append("ON ocorrenciasolicitacao.idocorrencia = ? ");
        sql.append("INNER JOIN usuario usuario ");
        sql.append("ON usuario.idusuario = bpm_itemtrabalhofluxo.idresponsavelatual  ");
        sql.append("WHERE  bpm_itemtrabalhofluxo.iditemtrabalho < ocorrenciasolicitacao.iditemtrabalho ");
        sql.append("AND bpm_itemtrabalhofluxo.idinstancia = ? ");
        sql.append("ORDER  BY bpm_itemtrabalhofluxo.iditemtrabalho DESC LIMIT 1; ");

        parametro.add(relatorioQuantitativoRetornoDTO.getIdOcorrencia());
        parametro.add(relatorioQuantitativoRetornoDTO.getIdInstancia());

        List lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("nome");
        listRetorno.add("idUsuario");

        Collection<RelatorioQuantitativoRetornoDTO> listaQuantitativoRetorno = new ArrayList<RelatorioQuantitativoRetornoDTO>();
        listaQuantitativoRetorno = engine.listConvertion(RelatorioQuantitativoRetornoDTO.class, lista, listRetorno);
        return listaQuantitativoRetorno;
    }

    public Collection<SolicitacaoServicoDTO> listaSolicitacaoPorBaseConhecimento(SolicitacaoServicoDTO solicitacao) throws Exception {
        StringBuilder sql = new StringBuilder();
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento, faseservico e empregados estava utilizando INNER
         * JOIN, isso fazia com que as solicitações que não possuem origem contrato, fase ou solicitante não fossem retornadas. Alterado para LEFT JOIN
         */

        sql.append("SELECT solicitacaoservico.idbaseconhecimento,solicitacaoservico.idsolicitacaoservico,tempoAtendimentoHH,tempoAtendimentoMM, ");
        sql.append("datahorainicio, datahorafim,nomeservico, solicitacaoservico.situacao, dataHoraSolicitacao, dataHoraLimite, nomeTipoDemandaServico, prazohh, ");
        sql.append("prazomm, solicitacaoservico.descricao, resposta, grupo.sigla, seqreabertura, empregado.nome, faseservico.nomefase, origematendimento.descricao,prioridade.nomeprioridade ");
        sql.append(" FROM solicitacaoservico ");
        sql.append("INNER JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
        sql.append("INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        sql.append("INNER JOIN servico ON servicocontrato.idservico = servico.idservico  ");
        sql.append("LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual ");
        sql.append("LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante ");
        sql.append("LEFT JOIN  faseservico faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual ");
        sql.append("LEFT JOIN  origematendimento origematendimento  ON origematendimento.idorigem = solicitacaoservico.idorigem ");
        sql.append("INNER JOIN prioridade prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade ");
        sql.append("where solicitacaoservico.idbaseconhecimento = ?");

        parametro.add(solicitacao.getIdBaseConhecimento());

        listRetorno.add("idBaseConhecimento");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("tempoAtendimentoHH");
        listRetorno.add("tempoAtendimentoMM");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraFim");
        listRetorno.add("nomeServico");
        listRetorno.add("situacao");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("descricao");
        listRetorno.add("resposta");
        listRetorno.add("siglaGrupo");
        listRetorno.add("seqReabertura");
        listRetorno.add("nomeSolicitante");
        listRetorno.add("faseAtual");
        listRetorno.add("origem");
        listRetorno.add("prioridade");

        List list = execSQL(sql.toString(), parametro.toArray());
        if (list != null && !list.isEmpty()) {
            Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorBaseConhecimento = this.listConvertion(SolicitacaoServicoDTO.class, list, listRetorno);
            return listaSolicitacaoServicoPorBaseConhecimento;
        }

        return null;
    }

    /**
     * Retorna uma lista de solicitacao serviço de acordo com os parametro passados com o principal objetivo de trazer somente solicitações fechadas ou canceladas. Foi adicionado o seguinte trecho de
     * código no script 'and solicitacaoservico.idtipodemandaservico is not null' para não deixar mostrar na lista do relatório os dados que estiverem com o campo idtipodemandaservico vazio.
     *
     * @param relatorioSolicitacaoPorSolucionarDto
     * @return Collection
     * @throws Exception
     * @author thays.araujo
     * @author bruno.aquino - Data: 24/10/2013 - Horário: 14h24min - ID Citsmart: 122034
     */
    public Collection<RelatorioSolicitacaoPorExecutanteDTO> listaSolicitacaoPorExecutante(RelatorioSolicitacaoPorExecutanteDTO relatorioSolicitacaoPorSolucionarDto) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        /**
         * Checa de há limite para pesquisa
         * @author thyen.chang
         */
        boolean seLimita = !relatorioSolicitacaoPorSolucionarDto.getTopList().equals(0);
        StringBuilder sql = new StringBuilder();
        sql.append("select  ");
        /**
         * Limita pesquisa no SQLServer
         * @author thyen.chang
         */
        if ((seLimita)&&(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))){
            sql.append("TOP "+relatorioSolicitacaoPorSolucionarDto.getTopList().toString()+" ");
        }
        sql.append("solicitacaoservico.idsolicitacaoservico, servico.nomeservico, usuario.nome, solicitacaoservico.situacao from solicitacaoservico  ");
        sql.append("inner join servicocontrato on  servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato ");
        sql.append("inner join servico  on servicocontrato.idservico = servico.idservico ");
        sql.append("inner join contratos ");
        sql.append("        on contratos.idcontrato = servicocontrato.idcontrato ");
        sql.append("       and (upper(contratos.deleted)  = 'N' or contratos.deleted is null) ");
        sql.append("left join bpm_itemtrabalhofluxo on bpm_itemtrabalhofluxo.iditemtrabalho =  solicitacaoservico.idtarefaencerramento ");
        sql.append("left join usuario on usuario.idusuario = bpm_itemtrabalhofluxo.idresponsavelatual ");

        if (relatorioSolicitacaoPorSolucionarDto.getDataInicio() != null) {
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("where ");
                /**
                 * Limita pesquisa no Oracle
                 * @author thyen.chang
                 */
                if(seLimita){
                    sql.append(" ROWNUM <= ? AND ");
                    parametro.add(relatorioSolicitacaoPorSolucionarDto.getTopList());
                }
                sql.append("to_char(solicitacaoservico.datahorafim, 'YYYY-MM-DD') BETWEEN ? AND ? ");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                parametro.add(formatter.format(relatorioSolicitacaoPorSolucionarDto.getDataInicio()));
                parametro.add(formatter.format(relatorioSolicitacaoPorSolucionarDto.getDataFim()));
            } else {
                sql.append("where solicitacaoservico.datahorafim BETWEEN ? AND ? ");
                parametro.add(relatorioSolicitacaoPorSolucionarDto.getDataInicio());
                parametro.add(transformaHoraFinal(relatorioSolicitacaoPorSolucionarDto.getDataFim()));
            }
        }

        if (relatorioSolicitacaoPorSolucionarDto.getIdSolicitacaoServico() != null) {

            if (relatorioSolicitacaoPorSolucionarDto.getDataInicio() == null) {
                sql.append(" where solicitacaoservico.idsolicitacaoservico = ?  ");
                parametro.add(relatorioSolicitacaoPorSolucionarDto.getIdSolicitacaoServico());
            } else {
                sql.append("and solicitacaoservico.idsolicitacaoservico = ?  ");
                parametro.add(relatorioSolicitacaoPorSolucionarDto.getIdSolicitacaoServico());
            }

        }

        if (relatorioSolicitacaoPorSolucionarDto.getIdResponsavelAtual() != null) {
            sql.append("and bpm_itemtrabalhofluxo.idresponsavelatual = ? ");
            parametro.add(relatorioSolicitacaoPorSolucionarDto.getIdResponsavelAtual());
        }

        if (relatorioSolicitacaoPorSolucionarDto.getIdContrato() != null) {
            sql.append("and servicocontrato.idcontrato = ? ");
            parametro.add(relatorioSolicitacaoPorSolucionarDto.getIdContrato());
        }

        sql.append(" and solicitacaoservico.idtipodemandaservico is not null ");
        sql.append(" and solicitacaoservico.situacao in ('Fechada', 'Cancelada', 'Resolvida') ");
        sql.append(" ORDER BY usuario.nome ");
        /**
         * Limita pesquisa no Postgres e MySQL
         * @author thyen.chang
         */
        if((seLimita) && ((CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL))||(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL))) ){
            sql.append(" LIMIT ? ");
            parametro.add(relatorioSolicitacaoPorSolucionarDto.getTopList());
        }

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("nomeResponsavelAtual");
        listRetorno.add("situacao");

        if (list != null && !list.isEmpty()) {
            Collection<RelatorioSolicitacaoPorExecutanteDTO> listaSolicitacaoPorExecutante;
            listaSolicitacaoPorExecutante = this.listConvertion(RelatorioSolicitacaoPorExecutanteDTO.class, list, listRetorno);
            return listaSolicitacaoPorExecutante;
        } else {
            return null;
        }
    }

    /**
     * Retorna Solicitação servico de acordo com criterios passados.
     *
     * @return Collection<SolicitacaoServicoDTO>
     * @throws Exception
     * @author thays.araujo
     */
    public Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto) throws Exception {
        List listRetorno = new ArrayList();

        StringBuilder sql = new StringBuilder();

        List lista = new ArrayList();

        List parametros = new ArrayList();

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento e faseservico estava utilizando INNER JOIN, isso
         * fazia com que as solicitações que não possuem origem contrato ou fase não fossem retornadas. Alterado para LEFT JOIN
         */

        /* String SGBD = CITCorporeUtil.SGBD_PRINCIPAL; */
        sql.append("SELECT ");
        sql.append("tempoAtendimentoHH,tempoAtendimentoMM,solicitacaoservico.datahorainicio, solicitacaoservico.datahorafim, solicitacaoservico.idsolicitacaoservico, "
        		+ "nomeservico, unidade.nome, solicitacaoservico.situacao, dataHoraSolicitacao, dataHoraLimite, nomeTipoDemandaServico, "
        		+ "solicitacaoservico.prazohh, solicitacaoservico.prazomm, ");
        sql.append("solicitacaoservico.descricaoSemFormatacao, resposta, grupo.sigla, solicitacaoservico.seqreabertura, empregado.nome, "
        		+ "faseservico.nomefase, origematendimento.descricao,prioridade.nomeprioridade, usuario.nome, contratos.numero, idUsuarioResponsavelAtual,  ");
        sql.append("grupo.nome, localidade.nomelocalidade, u.nome AS responsavelAtual, itf.iditemtrabalho as idtarefa, "
        		+ "ef.nome AS nometarefa ");
        sql.append(montaSql(parametros, pesquisaSolicitacaoServicoDto, 0, true));
        /*sql.append("GROUP BY solicitacaoservico.idsolicitacaoservico, servico.nomeservico, unidade.nome, tipodemandaservico.nometipodemandaservico, "
        		+ " grupo.sigla, empregado.nome, faseservico.nomefase, origematendimento.descricao, prioridade.nomeprioridade, usuario.nome, "
        		+ " contratos.numero, grupo.nome, localidade.nomelocalidade, u.nome ");*/

        lista = this.execSQL(sql.toString(), parametros.toArray());

        listRetorno.add("tempoAtendimentoHH");
        listRetorno.add("tempoAtendimentoMM");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraFim");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("nomeUnidadeSolicitante");
        listRetorno.add("situacao");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("descricaoSemFormatacao");
        listRetorno.add("resposta");
        listRetorno.add("siglaGrupo");
        listRetorno.add("seqReabertura");
        listRetorno.add("nomeSolicitante");
        listRetorno.add("faseAtual");
        listRetorno.add("origem");
        listRetorno.add("prioridade");
        listRetorno.add("responsavel");
        listRetorno.add("contrato");
        listRetorno.add("idUsuarioResponsavelAtual");
        listRetorno.add("grupoAtual");
        listRetorno.add("localidade");
        listRetorno.add("responsavelAtual");
        listRetorno.add("idTarefa");
        listRetorno.add("nomeTarefa");
        List listaSolicitacoes = engine.listConvertion(getBean(), lista, listRetorno);

        return listaSolicitacoes;
    }

    public Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorServicoContrato(Integer idServicoContratoContabil) throws Exception {
        List condicao = new ArrayList();

        StringBuilder sql = new StringBuilder();

        List listRetorno = new ArrayList();

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("descricao");


        sql.append("select sol.idsolicitacaoservico,sol.descricao from solicitacaoservico sol ");
        sql.append("inner join servicocontrato on sol.idservicocontrato = servicocontrato.idservicocontrato ");
        sql.append("inner join servico on servicocontrato.idservico = servico.idservico ");
        if(idServicoContratoContabil!=null){
            sql.append("where servico.idservico = ? and sol.situacao = ? ");
            condicao.add(idServicoContratoContabil);
            condicao.add(Enumerados.SituacaoSolicitacaoServico.Fechada.toString());
        }
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), condicao.toArray());

        if(lista!=null){
            Collection<SolicitacaoServicoDTO> listaSolicitacaoServicoPorServicoContrato =  engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
            return listaSolicitacaoServicoPorServicoContrato;
        }
        return null;

        /*condicao.add(new Condition("idServicoContrato", "=", idServicoContratoContabil.intValue()));
		condicao.add(new Condition("situacao", "=", Enumerados.SituacaoSolicitacaoServico.Fechada.toString()));*/
        /*List ordenacao = new ArrayList();
		ordenacao.add(new Order("idSolicitacaoServico"));
		return super.findByCondition(condicao, ordenacao);*/
    }

    /**
     * Retorna uma lista de Solicitações Serviços e Problemas de acordo com o idServiço passado.
     *
     * @param relatorioAnaliseServicoDto
     * @return Collection<RelatorioAnaliseServicoDTO>
     * @throws Exception
     * @author thays.araujo
     */
    public Collection<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO> listaSolicitacaoServicoProblemaPorServico(RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO relatorioAnaliseServicoDto)
            throws Exception {
        List parametro = new ArrayList();

        StringBuilder sql = new StringBuilder();

        sql.append("select  solicitacaoservico.idsolicitacaoservico, problema.idproblema,servico.nomeServico  from solicitacaoservico solicitacaoservico ");
        sql.append("inner join servicocontrato on solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
        sql.append("INNER JOIN servico ON servicocontrato.idservico = servico.idservico  ");
        sql.append("left join solicitacaoservicoproblema on  solicitacaoservicoproblema.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
        sql.append("left join problema on problema.idproblema = solicitacaoservicoproblema.idproblema ");

        if (relatorioAnaliseServicoDto.getIdServico() != null) {
            sql.append(" where servicocontrato.idservico = ? ");
            parametro.add(relatorioAnaliseServicoDto.getIdServico());
        }

        sql.append("group by problema.idproblema,solicitacaoservico.idsolicitacaoservico,servico.nomeServico  ");

        List lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("idProblema");
        listRetorno.add("servico");

        if (lista != null && !lista.isEmpty()) {
            Collection<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO> listaServicoPorSolicitacaoServico = new ArrayList<RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO>();
            listaServicoPorSolicitacaoServico = engine.listConvertion(RelatorioQuantitativoSolicitacaoProblemaPorServicoDTO.class, lista, listRetorno);
            return listaServicoPorSolicitacaoServico;
        } else {
            return null;
        }
    }

    /**
     * Lista todas as solicitações pelo id do empregado, inclusive as solicitações resolvidas
     *
     * @param pgAtual
     * @param qtdPaginacao
     * @param gerenciamentoBean
     * @return
     * @throws Exception
     */
    public Collection<SolicitacaoServicoDTO> listaSolicitacoesPorIdEmpregado(Integer pgAtual, Integer qtdPaginacao, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {

        String ordernarPor = gerenciamentoBean.getOrdenarPor() == null ? "" : gerenciamentoBean.getOrdenarPor();
        String direcaoOrdenacao = gerenciamentoBean.getDirecaoOrdenacao() == null ? "" : gerenciamentoBean.getDirecaoOrdenacao() + " ";

        if (ordernarPor.equals("NSolicitacao")) {
            ordernarPor = " ORDER BY sol.idsolicitacaoservico ";
        } else if (ordernarPor.equals("servico")) {
            ordernarPor = " ORDER BY nomeservico ";
        } else if (ordernarPor.equals("responsavel")) {
            ordernarPor = " ORDER BY e2.nome ";
        } else if (ordernarPor.equals("prioridade")) {
            ordernarPor = " ORDER BY sol.idprioridade ";
        } else if (ordernarPor.equals("situacao")) {
            ordernarPor = " ORDER BY sol.situacao ";
        } else if (ordernarPor.equals("descricao")) {
            ordernarPor = " ORDER BY sol.descricao ";
        } else if (ordernarPor.equals("dataHoraLimite")) {
            ordernarPor = " ORDER BY sol.datahoralimite ";
        } else if (ordernarPor.equals("dataHoraLimiteCriacao")) {
            ordernarPor = " ORDER BY sol.datahorasolicitacao ";
        } else {
            ordernarPor = " ORDER BY sol.idresponsavel ";
        }
        
        ordernarPor += direcaoOrdenacao;
    	
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
        listRetorno.add("prazoCapturaHH");
        listRetorno.add("prazoCapturaMM");
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
        listRetorno.add("idUltimaAprovacao");
        listRetorno.add("dataHoraInicioSLA");
        listRetorno.add("dataHoraSuspensaoSLA");
        listRetorno.add("dataHoraReativacaoSLA");
        listRetorno.add("situacaoSLA");
        listRetorno.add("aprovacao");
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
        listRetorno.add("idInstanciaFluxo");

        StringBuilder sql = new StringBuilder();
        List parametros = new ArrayList();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" ;WITH TabelaTemporaria AS ( ");
        }

        sql.append("SELECT sol.idSolicitacaoServico, sol.idbaseconhecimento, sol.idServicoContrato, sol.idSolicitante, ");
        sql.append("       sol.idItemConfiguracao, sol.idItemConfiguracaoFilho, sol.idtipodemandaservico, sol.idcontatosolicitacaoservico, ");
        sql.append("       sol.idOrigem, sol.idResponsavel, sol.idTipoProblema, sol.idPrioridade, sol.idUnidade, sol.idFaseAtual, ");
        sql.append("       sol.idGrupoAtual, sol.dataHoraSolicitacao, sol.dataHoraLimite, sol.atendimentoPresencial, sol.prazoCapturaHH, sol.prazoCapturaMM, ");
        sql.append("       sol.prazoHH, sol.prazoMM, sol.descricao, sol.resposta, sol.dataHoraInicio, sol.dataHoraFim, sol.situacao, ");
        sql.append("       sol.idSolicitacaoPai, sol.detalhamentoCausa, sol.idCausaIncidente, sol.idCategoriaSolucao, ");
        sql.append("       sol.seqreabertura, sol.enviaEmailCriacao, sol.enviaEmailFinalizacao, sol.enviaEmailAcoes, ");
        sql.append("       sol.idgruponivel1, sol.solucaoTemporaria, sol.houveMudanca, sol.slaACombinar, sol.prazohhAnterior, ");
        sql.append("       sol.prazommAnterior, sol.idCalendario, sol.tempoDecorridoHH, sol.tempoDecorridoMM, sol.dataHoraSuspensao, ");
        sql.append("       sol.dataHoraReativacao, sol.impacto, sol.urgencia, sol.dataHoraCaptura, sol.tempoCapturaHH, sol.tempoCapturaMM, ");
        sql.append("       sol.tempoAtrasoHH, sol.tempoAtrasoMM, sol.tempoAtendimentoHH, sol.tempoAtendimentoMM, sol.idacordonivelservico, ");
        sql.append("       sol.idultimaaprovacao, sol.datahorainiciosla, sol.datahorasuspensaosla, sol.datahorareativacaosla, sol.situacaosla, aprov.aprovacao, ");
        sql.append("       s.idservico, s.nomeServico, td.idTipoDemandaServico as idtipodemandaservico2, td.nomeTipoDemandaServico, c.idContrato, c.numero, e1.nome, u1.nome as nomeUnidade1, ");
        sql.append("       e2.nome as nomeResponsavel, u2.nome as nomeUnidade2, oa.descricao as descricao2, p.nomeprioridade, fs.nomefase,  ");
        sql.append("       g1.sigla, g2.sigla as siglaGupo2, cs.nomecontato, cs.emailcontato, cs.telefonecontato, cs.localizacaofisica ,cs.idlocalidade , es.idInstanciaFluxo ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" , ROW_NUMBER() OVER (ORDER BY sol.idsolicitacaoservico) AS Row ");
        }

        sql.append("  FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");

        sql.append(" INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("WHERE sol.idsolicitacaopai is null ");


        if (listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
            // FILTRA CONTRATO DO USUÁRIO LOGADO - Só retorna as Solicitações dos Contratos em que o usuário logado está inserido.
            if (listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
                sql.append(" AND c.idcontrato in ( ");
                boolean verifica = true;
                for (ContratoDTO contrato : listContratoUsuarioLogado) {
                    if (verifica) {
                        sql.append(contrato.getIdContrato());
                        verifica = false;
                    } else {
                        sql.append(",");
                        sql.append(contrato.getIdContrato());
                    }
                }
                sql.append(" )");
            }

        }

        // Adiciona o filtro de pesquisa caso houver filtro
        adicionarFiltroPesquisa(sql, gerenciamentoBean, parametros);

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            Integer pgTotal = pgAtual * qtdPaginacao;
            if (pgTotal > 0) {
                pgAtual = pgTotal - qtdPaginacao;
            }
//            sql.append(" ORDER BY sol.idSolicitacaoServico LIMIT " + qtdPaginacao + " OFFSET " + pgAtual);
            sql.append(ordernarPor + " LIMIT " + qtdPaginacao + " OFFSET " + pgAtual);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
            Integer pgTotal = pgAtual * qtdPaginacao;
            if (pgTotal > 0) {
                pgAtual = pgTotal - qtdPaginacao;
            }
//            sql.append(" LIMIT " + pgAtual + ", " + qtdPaginacao);
            sql.append(ordernarPor + " LIMIT " + pgAtual + ", " + qtdPaginacao);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            Integer quantidadePaginator2 = new Integer(0);
            if (pgAtual > 0) {
                quantidadePaginator2 = qtdPaginacao * pgAtual;
                pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
            } else {
                quantidadePaginator2 = qtdPaginacao;
                pgAtual = 0;
            }
            sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row> " + pgAtual + " and Row<" + (quantidadePaginator2 + 1) + " ");
        }

        String sqlOracle = "";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            Integer quantidadePaginator2 = new Integer(0);
            if (pgAtual > 1) {
                quantidadePaginator2 = qtdPaginacao * pgAtual;
                pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
                pgAtual = pgAtual + 1;
            } else {
                quantidadePaginator2 = qtdPaginacao;
                pgAtual = 0;
            }
            int intInicio = pgAtual;
            int intLimite = quantidadePaginator2;
            sqlOracle = sql + ordernarPor;
            sqlOracle = paginacaoOracle(sql.toString(), intInicio, intLimite);
        }

        List lista = new ArrayList();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            lista = this.execSQL(sqlOracle, parametros.toArray());
        } else {
            lista = this.execSQL(sql.toString(), parametros.toArray());
        }

        if (lista != null && !lista.isEmpty()) {
            return engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        } else {
            return null;
        }
    }

    
    
    /**
     * Lista todas as solicitações pelo id do empregado, inclusive as solicitações resolvidas
     * Este método inclui o parâmetro "situacao", que permite o filtro das solicitações por situaçãõ (Fechado, Em Andamento, etc.)
     *
     * @param pgAtual
     * @param qtdPaginacao
     * @param gerenciamentoBean
     * @return
     * @throws Exception
     */
    public Collection<SolicitacaoServicoDTO> listaSolicitacoesPorIdEmpregado(Integer pgAtual, Integer qtdPaginacao, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado, String[] filtro) throws Exception {
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
        listRetorno.add("prazoCapturaHH");
        listRetorno.add("prazoCapturaMM");
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
        listRetorno.add("idUltimaAprovacao");
        listRetorno.add("dataHoraInicioSLA");
        listRetorno.add("dataHoraSuspensaoSLA");
        listRetorno.add("dataHoraReativacaoSLA");
        listRetorno.add("situacaoSLA");
        listRetorno.add("aprovacao");
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
        listRetorno.add("idInstanciaFluxo");

        StringBuilder sql = new StringBuilder();
        List parametros = new ArrayList();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" ;WITH TabelaTemporaria AS ( ");
        }

        sql.append("SELECT sol.idSolicitacaoServico, sol.idbaseconhecimento, sol.idServicoContrato, sol.idSolicitante, ");
        sql.append("       sol.idItemConfiguracao, sol.idItemConfiguracaoFilho, sol.idtipodemandaservico, sol.idcontatosolicitacaoservico, ");
        sql.append("       sol.idOrigem, sol.idResponsavel, sol.idTipoProblema, sol.idPrioridade, sol.idUnidade, sol.idFaseAtual, ");
        sql.append("       sol.idGrupoAtual, sol.dataHoraSolicitacao, sol.dataHoraLimite, sol.atendimentoPresencial, sol.prazoCapturaHH, sol.prazoCapturaMM, ");
        sql.append("       sol.prazoHH, sol.prazoMM, sol.descricao, sol.resposta, sol.dataHoraInicio, sol.dataHoraFim, sol.situacao, ");
        sql.append("       sol.idSolicitacaoPai, sol.detalhamentoCausa, sol.idCausaIncidente, sol.idCategoriaSolucao, ");
        sql.append("       sol.seqreabertura, sol.enviaEmailCriacao, sol.enviaEmailFinalizacao, sol.enviaEmailAcoes, ");
        sql.append("       sol.idgruponivel1, sol.solucaoTemporaria, sol.houveMudanca, sol.slaACombinar, sol.prazohhAnterior, ");
        sql.append("       sol.prazommAnterior, sol.idCalendario, sol.tempoDecorridoHH, sol.tempoDecorridoMM, sol.dataHoraSuspensao, ");
        sql.append("       sol.dataHoraReativacao, sol.impacto, sol.urgencia, sol.dataHoraCaptura, sol.tempoCapturaHH, sol.tempoCapturaMM, ");
        sql.append("       sol.tempoAtrasoHH, sol.tempoAtrasoMM, sol.tempoAtendimentoHH, sol.tempoAtendimentoMM, sol.idacordonivelservico, ");
        sql.append("       sol.idultimaaprovacao, sol.datahorainiciosla, sol.datahorasuspensaosla, sol.datahorareativacaosla, sol.situacaosla, aprov.aprovacao, ");
        sql.append("       s.idservico, s.nomeServico, td.idTipoDemandaServico as idtipodemandaservico2, td.nomeTipoDemandaServico, c.idContrato, c.numero, e1.nome, u1.nome as nomeUnidade1, ");
        sql.append("       e2.nome as nomeResponsavel, u2.nome as nomeUnidade2, oa.descricao as descricao2, p.nomeprioridade, fs.nomefase,  ");
        sql.append("       g1.sigla, g2.sigla as siglaGupo2, cs.nomecontato, cs.emailcontato, cs.telefonecontato, cs.localizacaofisica ,cs.idlocalidade , es.idInstanciaFluxo ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" , ROW_NUMBER() OVER (ORDER BY sol.idsolicitacaoservico) AS Row ");
        }

        sql.append("  FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");

        sql.append(" INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("WHERE sol.idsolicitacaopai is null ");


        if (listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
            // FILTRA CONTRATO DO USUÁRIO LOGADO - Só retorna as Solicitações dos Contratos em que o usuário logado está inserido.
            if (listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
                sql.append(" AND c.idcontrato in ( ");
                boolean verifica = true;
                for (ContratoDTO contrato : listContratoUsuarioLogado) {
                    if (verifica) {
                        sql.append(contrato.getIdContrato());
                        verifica = false;
                    } else {
                        sql.append(",");
                        sql.append(contrato.getIdContrato());
                    }
                }
                sql.append(" )");
            }

        }
        
        /**
         * Cristian em 03/12/2014: Implemntação do filtro por situação
         */
        
        
        String stringDelimitada = UtilStrings.StringArrayParaStringDelimitadaComVirgula(filtro);
        sql.append(" AND sol.situacao in ("+stringDelimitada+") ");
        
        // Adiciona o filtro de pesquisa caso houver filtro
        adicionarFiltroPesquisa(sql, gerenciamentoBean, parametros);

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            Integer pgTotal = pgAtual * qtdPaginacao;
            if (pgTotal > 0) {
                pgAtual = pgTotal - qtdPaginacao;
            }
            sql.append(" ORDER BY sol.idSolicitacaoServico LIMIT " + qtdPaginacao + " OFFSET " + pgAtual);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
            Integer pgTotal = pgAtual * qtdPaginacao;
            if (pgTotal > 0) {
                pgAtual = pgTotal - qtdPaginacao;
            }
            sql.append(" LIMIT " + pgAtual + ", " + qtdPaginacao);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            Integer quantidadePaginator2 = new Integer(0);
            if (pgAtual > 0) {
                quantidadePaginator2 = qtdPaginacao * pgAtual;
                pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
            } else {
                quantidadePaginator2 = qtdPaginacao;
                pgAtual = 0;
            }
            sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row> " + pgAtual + " and Row<" + (quantidadePaginator2 + 1) + " ");
        }

        String sqlOracle = "";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            Integer quantidadePaginator2 = new Integer(0);
            if (pgAtual > 1) {
                quantidadePaginator2 = qtdPaginacao * pgAtual;
                pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
                pgAtual = pgAtual + 1;
            } else {
                quantidadePaginator2 = qtdPaginacao;
                pgAtual = 0;
            }
            int intInicio = pgAtual;
            int intLimite = quantidadePaginator2;
            sqlOracle = paginacaoOracle(sql.toString(), intInicio, intLimite);
        }

        List lista = new ArrayList();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            lista = this.execSQL(sqlOracle, parametros.toArray());
        } else {
            lista = this.execSQL(sql.toString(), parametros.toArray());
        }

        if (lista != null && !lista.isEmpty()) {
            return engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        } else {
            return null;
        }
    }
    
    

    public Collection listaSolicitacoesSemPesquisaSatisfacao() throws Exception {
        List listRetorno = new ArrayList();
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("select idsolicitacaoservico from " + getTableName() + " solicitacaoservico  ");
        sql.append("where solicitacaoservico.idsolicitacaoservico NOT IN (select idsolicitacaoservico from pesquisasatisfacao) ");
        sql.append(" and UPPER(situacao) = 'FECHADA' ");
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());
        listRetorno.add("idSolicitacaoServico");
        if (lista != null && !lista.isEmpty()) {
            List listaResult = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
            return listaResult;
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listBySituacao(SituacaoSolicitacaoServico situacao) throws Exception {
        List condicao = new ArrayList();
        condicao.add(new Condition("situacao", "=", situacao.name()));
        return super.findByCondition(condicao, null);
    }

    public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefa) throws Exception {

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
        listRetorno.add("prazoCapturaHH");
        listRetorno.add("prazoCapturaMM");
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
        listRetorno.add("idUltimaAprovacao");
        listRetorno.add("dataHoraInicioSLA");
        listRetorno.add("dataHoraSuspensaoSLA");
        listRetorno.add("dataHoraReativacaoSLA");
        listRetorno.add("situacaoSLA");
        listRetorno.add("aprovacao");
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
        listRetorno.add("idInstanciaFluxo");
        listRetorno.add("vencendo");

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT sol.idSolicitacaoServico, sol.idbaseconhecimento, sol.idServicoContrato, sol.idSolicitante, ");
        sql.append("       sol.idItemConfiguracao, sol.idItemConfiguracaoFilho, sol.idtipodemandaservico, sol.idcontatosolicitacaoservico, ");
        sql.append("       sol.idOrigem, sol.idResponsavel, sol.idTipoProblema, sol.idPrioridade, sol.idUnidade, sol.idFaseAtual, ");
        sql.append("       sol.idGrupoAtual, sol.dataHoraSolicitacao, sol.dataHoraLimite, sol.atendimentoPresencial, sol.prazoCapturaHH, sol.prazoCapturaMM, ");
        sql.append("       sol.prazoHH, sol.prazoMM, sol.descricao, sol.resposta, sol.dataHoraInicio, sol.dataHoraFim, sol.situacao, ");
        sql.append("       sol.idSolicitacaoPai, sol.detalhamentoCausa, sol.idCausaIncidente, sol.idCategoriaSolucao, ");
        sql.append("       sol.seqreabertura, sol.enviaEmailCriacao, sol.enviaEmailFinalizacao, sol.enviaEmailAcoes, ");
        sql.append("       sol.idgruponivel1, sol.solucaoTemporaria, sol.houveMudanca, sol.slaACombinar, sol.prazohhAnterior, ");
        sql.append("       sol.prazommAnterior, sol.idCalendario, sol.tempoDecorridoHH, sol.tempoDecorridoMM, sol.dataHoraSuspensao, ");
        sql.append("       sol.dataHoraReativacao, sol.impacto, sol.urgencia, sol.dataHoraCaptura, sol.tempoCapturaHH, sol.tempoCapturaMM, ");
        sql.append("       sol.tempoAtrasoHH, sol.tempoAtrasoMM, sol.tempoAtendimentoHH, sol.tempoAtendimentoMM, sol.idacordonivelservico, ");
        sql.append("       sol.idultimaaprovacao, sol.datahorainiciosla, sol.datahorasuspensaosla, sol.datahorareativacaosla, sol.situacaosla, aprov.aprovacao, ");
        sql.append("       s.idservico, s.nomeServico, td.idTipoDemandaServico, td.nomeTipoDemandaServico, c.idContrato, c.numero, e1.nome, u1.nome, ");
        sql.append("       e2.nome, u2.nome, oa.descricao, p.nomeprioridade, fs.nomefase,  ");
        sql.append("       g1.sigla, g2.sigla, cs.nomecontato, cs.emailcontato, cs.telefonecontato, cs.localizacaofisica ,cs.idlocalidade , es.idInstanciaFluxo, sol.vencendo ");
        sql.append("  FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");

        sql.append(" INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("WHERE sol.idsolicitacaopai is null ");

        if (listTarefa != null && !listTarefa.isEmpty()) {
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("and es.idInstanciaFluxo in ( select * from table(sys.odcinumberlist( ");
            } else {
                sql.append("and es.idInstanciaFluxo in  ( ");
            }

            int size = listTarefa.size();
            int max = 1;
            int aux = 0;
            int CONSTANT = 999;
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdInstancia());

                    if (aux == size) {
                        sql.append(")))");
                    } else if (aux > (CONSTANT * (max - 1)) && aux <= (CONSTANT * max)) {
                        sql.append("))  union  select * from table(sys.odcinumberlist( ");
                        max++;
                    } else {
                        sql.append(",");
                    }
                }
            } else {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdInstancia());

                    if (aux == size) {
                        sql.append(")");
                    } else {
                        sql.append(",");
                    }
                }

            }

        } else {
            return null;
        }

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), null);

        if (lista != null && !lista.isEmpty()) {
            return engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        } else {
            return null;
        }
    }

    /**
     * Utilizado para a RENDERIZAÇÃO do GRÁFICO, pois no Gráfico não é necessário a utilização de Paginação. Esta consulta considera o Login do Usuário Logado, os Contratos em que está inserido e os
     * Filtros Selecionados na tela de Gerenciamento de Serviços.
     *
     * @param listTarefa
     * @param gerenciamentoBean
     * @param listContratoUsuarioLogado
     * @return Collection<SolicitacaoServicoDTO>
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 05.11.2013
     */
    public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefa, GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado)
            throws Exception {

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
        listRetorno.add("prazoCapturaHH");
        listRetorno.add("prazoCapturaMM");
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
        listRetorno.add("idUltimaAprovacao");
        listRetorno.add("dataHoraInicioSLA");
        listRetorno.add("dataHoraSuspensaoSLA");
        listRetorno.add("dataHoraReativacaoSLA");
        listRetorno.add("situacaoSLA");
        listRetorno.add("aprovacao");
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
        listRetorno.add("idInstanciaFluxo");
        listRetorno.add("vencendo");
        listRetorno.add("idTarefa");

        StringBuilder sql = new StringBuilder();
        List parametros = new ArrayList();

        sql.append("SELECT sol.idSolicitacaoServico, sol.idbaseconhecimento, sol.idServicoContrato, sol.idSolicitante, ");
        sql.append("       sol.idItemConfiguracao, sol.idItemConfiguracaoFilho, sol.idtipodemandaservico, sol.idcontatosolicitacaoservico, ");
        sql.append("       sol.idOrigem, sol.idResponsavel, sol.idTipoProblema, sol.idPrioridade, sol.idUnidade, sol.idFaseAtual, ");
        sql.append("       sol.idGrupoAtual, sol.dataHoraSolicitacao, sol.dataHoraLimite, sol.atendimentoPresencial, sol.prazoCapturaHH, sol.prazoCapturaMM, ");
        sql.append("       sol.prazoHH, sol.prazoMM, sol.descricao, sol.resposta, sol.dataHoraInicio, sol.dataHoraFim, sol.situacao, ");
        sql.append("       sol.idSolicitacaoPai, sol.detalhamentoCausa, sol.idCausaIncidente, sol.idCategoriaSolucao, ");
        sql.append("       sol.seqreabertura, sol.enviaEmailCriacao, sol.enviaEmailFinalizacao, sol.enviaEmailAcoes, ");
        sql.append("       sol.idgruponivel1, sol.solucaoTemporaria, sol.houveMudanca, sol.slaACombinar, sol.prazohhAnterior, ");
        sql.append("       sol.prazommAnterior, sol.idCalendario, sol.tempoDecorridoHH, sol.tempoDecorridoMM, sol.dataHoraSuspensao, ");
        sql.append("       sol.dataHoraReativacao, sol.impacto, sol.urgencia, sol.dataHoraCaptura, sol.tempoCapturaHH, sol.tempoCapturaMM, ");
        sql.append("       sol.tempoAtrasoHH, sol.tempoAtrasoMM, sol.tempoAtendimentoHH, sol.tempoAtendimentoMM, sol.idacordonivelservico, ");
        sql.append("       sol.idultimaaprovacao, sol.datahorainiciosla, sol.datahorasuspensaosla, sol.datahorareativacaosla, sol.situacaosla, aprov.aprovacao, ");
        sql.append("       s.idservico, s.nomeServico, td.idTipoDemandaServico, td.nomeTipoDemandaServico, c.idContrato, c.numero, e1.nome, u1.nome, ");
        sql.append("       e2.nome, u2.nome, oa.descricao, p.nomeprioridade, fs.nomefase,  ");
        sql.append("       g1.sigla, g2.sigla, cs.nomecontato, cs.emailcontato, cs.telefonecontato, cs.localizacaofisica ,cs.idlocalidade , es.idInstanciaFluxo, sol.vencendo, it.iditemtrabalho ");
        sql.append("  FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");
        sql.append("        INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("        INNER JOIN bpm_itemtrabalhofluxo it ON it.idinstancia = es.idinstanciafluxo ");
        sql.append(" WHERE sol.idsolicitacaopai is null ");

        if (listTarefa != null && !listTarefa.isEmpty() && listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("and it.iditemtrabalho in ( select * from table(sys.odcinumberlist( ");
            } else {
                sql.append("and it.iditemtrabalho in  ( ");
            }

            int size = listTarefa.size();
            int max = 1;
            int aux = 0;
            int CONSTANT = 999;
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdItemTrabalho());

                    if (aux == size) {
                        sql.append(")))");
                    } else if (aux > (CONSTANT * (max - 1)) && aux <= (CONSTANT * max)) {
                        sql.append("))  union  select * from table(sys.odcinumberlist( ");
                        max++;
                    } else {
                        sql.append(",");
                    }
                }
            } else {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdItemTrabalho());

                    if (aux == size) {
                        sql.append(")");
                    } else {
                        sql.append(",");
                    }
                }
            }

            // FILTRA CONTRATO DO USUÁRIO LOGADO - Só retorna as Solicitações dos Contratos em que o usuário logado está inserido.
            if (listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
                sql.append(" AND c.idcontrato in ( ");
                boolean verifica = true;
                for (ContratoDTO contrato : listContratoUsuarioLogado) {
                    if (verifica) {
                        sql.append(contrato.getIdContrato());
                        verifica = false;
                    } else {
                        sql.append(",");
                        sql.append(contrato.getIdContrato());
                    }
                }
                sql.append(" )");
            }

        } else {
            return null;
        }

        // Adiciona o filtro de pesquisa caso houver filtro
        adicionarFiltroPesquisa(sql, gerenciamentoBean, parametros);

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametros.toArray());

        if (lista != null && !lista.isEmpty()) {
            return engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        } else {
            return null;
        }
    }

    @SuppressWarnings("unused")
    public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefa, Integer pgAtual, Integer qtdPaginacao) throws Exception {

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
        listRetorno.add("prazoCapturaHH");
        listRetorno.add("prazoCapturaMM");
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
        listRetorno.add("idUltimaAprovacao");
        listRetorno.add("dataHoraInicioSLA");
        listRetorno.add("dataHoraSuspensaoSLA");
        listRetorno.add("dataHoraReativacaoSLA");
        listRetorno.add("situacaoSLA");
        listRetorno.add("aprovacao");
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
        listRetorno.add("idInstanciaFluxo");

        StringBuilder sql = new StringBuilder();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" ;WITH TabelaTemporaria AS ( ");
        }

        sql.append("SELECT sol.idSolicitacaoServico, sol.idbaseconhecimento, sol.idServicoContrato, sol.idSolicitante, ");
        sql.append("       sol.idItemConfiguracao, sol.idItemConfiguracaoFilho, sol.idtipodemandaservico, sol.idcontatosolicitacaoservico, ");
        sql.append("       sol.idOrigem, sol.idResponsavel, sol.idTipoProblema, sol.idPrioridade, sol.idUnidade, sol.idFaseAtual, ");
        sql.append("       sol.idGrupoAtual, sol.dataHoraSolicitacao, sol.dataHoraLimite, sol.atendimentoPresencial, sol.prazoCapturaHH, sol.prazoCapturaMM, ");
        sql.append("       sol.prazoHH, sol.prazoMM, sol.descricao, sol.resposta, sol.dataHoraInicio, sol.dataHoraFim, sol.situacao, ");
        sql.append("       sol.idSolicitacaoPai, sol.detalhamentoCausa, sol.idCausaIncidente, sol.idCategoriaSolucao, ");
        sql.append("       sol.seqreabertura, sol.enviaEmailCriacao, sol.enviaEmailFinalizacao, sol.enviaEmailAcoes, ");
        sql.append("       sol.idgruponivel1, sol.solucaoTemporaria, sol.houveMudanca, sol.slaACombinar, sol.prazohhAnterior, ");
        sql.append("       sol.prazommAnterior, sol.idCalendario, sol.tempoDecorridoHH, sol.tempoDecorridoMM, sol.dataHoraSuspensao, ");
        sql.append("       sol.dataHoraReativacao, sol.impacto, sol.urgencia, sol.dataHoraCaptura, sol.tempoCapturaHH, sol.tempoCapturaMM, ");
        sql.append("       sol.tempoAtrasoHH, sol.tempoAtrasoMM, sol.tempoAtendimentoHH, sol.tempoAtendimentoMM, sol.idacordonivelservico, ");
        sql.append("       sol.idultimaaprovacao, sol.datahorainiciosla, sol.datahorasuspensaosla, sol.datahorareativacaosla, sol.situacaosla, aprov.aprovacao, ");
        sql.append("       s.idservico, s.nomeServico, td.idTipoDemandaServico as idtipodemandaservico2, td.nomeTipoDemandaServico, c.idContrato, c.numero, e1.nome, u1.nome as nomeUnidade1, ");
        sql.append("       e2.nome as nomeResponsavel, u2.nome as nomeUnidade2, oa.descricao as descricao2, p.nomeprioridade, fs.nomefase,  ");
        sql.append("       g1.sigla, g2.sigla as siglaGupo2, cs.nomecontato, cs.emailcontato, cs.telefonecontato, cs.localizacaofisica ,cs.idlocalidade , es.idInstanciaFluxo ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" , ROW_NUMBER() OVER (ORDER BY sol.idsolicitacaoservico) AS Row ");
        }

        sql.append("  FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");

        sql.append(" INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("WHERE sol.idsolicitacaopai is null ");

        int qtdIds = 0;
        if (listTarefa != null && !listTarefa.isEmpty()) {
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("and es.idInstanciaFluxo in ( select * from table(sys.odcinumberlist( ");
            } else {
                sql.append("and es.idInstanciaFluxo in  ( ");
            }

            int size = listTarefa.size();
            int max = 1;
            int aux = 0;
            int CONSTANT = 999;
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdInstancia());

                    if (aux == size) {
                        sql.append(")))");
                    } else if (aux > (CONSTANT * (max - 1)) && aux <= (CONSTANT * max)) {
                        sql.append("))  union  select * from table(sys.odcinumberlist( ");
                        qtdIds++;
                        max++;
                    } else {
                        sql.append(",");
                    }
                }
            } else {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdInstancia());
                    if (aux == size) {
                        sql.append(")");
                    } else {
                        sql.append(",");
                    }
                }

            }

        } else {
            return null;
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            Integer pgTotal = pgAtual * qtdPaginacao;
            pgAtual = pgTotal - qtdPaginacao;
            sql.append(" ORDER BY sol.idSolicitacaoServico LIMIT " + qtdPaginacao + " OFFSET " + pgAtual);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
            Integer pgTotal = pgAtual * qtdPaginacao;
            pgAtual = pgTotal - qtdPaginacao;
            sql.append(" LIMIT " + pgAtual + ", " + qtdPaginacao);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            Integer quantidadePaginator2 = new Integer(0);
            if (pgAtual > 0) {
                quantidadePaginator2 = qtdPaginacao * pgAtual;
                pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
            } else {
                quantidadePaginator2 = qtdPaginacao;
                pgAtual = 0;
            }
            sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row> " + pgAtual + " and Row<" + (quantidadePaginator2 + 1) + " ");
        }

        String sqlOracle = "";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            Integer quantidadePaginator2 = new Integer(0);
            if (pgAtual > 1) {
                quantidadePaginator2 = qtdPaginacao * pgAtual;
                pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
                pgAtual = pgAtual + 1;
            } else {
                quantidadePaginator2 = qtdPaginacao;
                pgAtual = 0;
            }
            int intInicio = pgAtual;
            int intLimite = quantidadePaginator2;
            sqlOracle = paginacaoOracle(sql.toString(), intInicio, intLimite);
        }

        List lista = new ArrayList();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            lista = this.execSQL(sqlOracle, null);
        } else {
            lista = this.execSQL(sql.toString(), null);
        }

        if (lista != null && !lista.isEmpty()) {
            return engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        } else {
            return null;
        }
    }

    /**
     * Retorna a Lista de TarefaDTO com SolicitacaoServidoDTO de acordo com o Login, Lista de Contratos do Usuário Logado e os Filtros Selecionados na Tela de Gerenciamento de Serviços.
     *
     * @param listTarefas
     * @param qtdAtual
     * @param qtdAPaginacao
     * @param gerenciamentoBean
     * @param listContratoUsuarioLogado
     * @return Collection<SolicitacaoServicoDTO>
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 05.11.2013
     */
    public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefa, Integer pgAtual, Integer qtdPaginacao, GerenciamentoServicosDTO gerenciamentoBean,
            Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {

        String ordernarPor = gerenciamentoBean.getOrdenarPor() == null ? "" : gerenciamentoBean.getOrdenarPor();

        if (ordernarPor.equals("NSolicitacao")) {
            ordernarPor = " ORDER BY sol.idsolicitacaoservico ";
        } else if (ordernarPor.equals("servico")) {
            ordernarPor = " ORDER BY nomeservico ";
        } else if (ordernarPor.equals("responsavel")) {
            ordernarPor = " ORDER BY e2.nome ";
        } else if (ordernarPor.equals("prioridade")) {
            ordernarPor = " ORDER BY sol.idprioridade ";
        } else if (ordernarPor.equals("situacao")) {
            ordernarPor = " ORDER BY sol.situacao ";
        } else if (ordernarPor.equals("descricao")) {
            ordernarPor = " ORDER BY sol.descricao ";
        } else if (ordernarPor.equals("dataHoraLimite")) {
            ordernarPor = " ORDER BY sol.datahoralimite ";
        } else if (ordernarPor.equals("dataHoraLimiteCriacao")) {
            ordernarPor = " ORDER BY sol.datahorasolicitacao ";
        } else {
            ordernarPor = " ORDER BY sol.idresponsavel ";
        }

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
        listRetorno.add("prazoCapturaHH");
        listRetorno.add("prazoCapturaMM");
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
        listRetorno.add("idUltimaAprovacao");
        listRetorno.add("dataHoraInicioSLA");
        listRetorno.add("dataHoraSuspensaoSLA");
        listRetorno.add("dataHoraReativacaoSLA");
        listRetorno.add("situacaoSLA");
        listRetorno.add("aprovacao");
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
        listRetorno.add("idInstanciaFluxo");
        listRetorno.add("vencendo");
        listRetorno.add("idTarefa");
        listRetorno.add("idSolicitacaoRelacionada");
        listRetorno.add("qtdefilhas");
        listRetorno.add("qtdeItensConfiguracaoRelacionados");

        StringBuilder sql = new StringBuilder();
        List parametros = new ArrayList();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" ;WITH TabelaTemporaria AS ( ");
        }

        sql.append("SELECT sol.idSolicitacaoServico, sol.idbaseconhecimento, sol.idServicoContrato, sol.idSolicitante, ");
        sql.append("       sol.idItemConfiguracao, sol.idItemConfiguracaoFilho, sol.idtipodemandaservico, sol.idcontatosolicitacaoservico, ");
        sql.append("       sol.idOrigem, sol.idResponsavel, sol.idTipoProblema, sol.idPrioridade, sol.idUnidade, sol.idFaseAtual, ");
        sql.append("       sol.idGrupoAtual, sol.dataHoraSolicitacao, sol.dataHoraLimite, sol.atendimentoPresencial, sol.prazoCapturaHH, sol.prazoCapturaMM, ");
        sql.append("       sol.prazoHH, sol.prazoMM, sol.descricao, sol.resposta, sol.dataHoraInicio, sol.dataHoraFim, sol.situacao, ");
        sql.append("       sol.idSolicitacaoPai, sol.detalhamentoCausa, sol.idCausaIncidente, sol.idCategoriaSolucao, ");
        sql.append("       sol.seqreabertura, sol.enviaEmailCriacao, sol.enviaEmailFinalizacao, sol.enviaEmailAcoes, ");
        sql.append("       sol.idgruponivel1, sol.solucaoTemporaria, sol.houveMudanca, sol.slaACombinar, sol.prazohhAnterior, ");
        sql.append("       sol.prazommAnterior, sol.idCalendario, sol.tempoDecorridoHH, sol.tempoDecorridoMM, sol.dataHoraSuspensao, ");
        sql.append("       sol.dataHoraReativacao, sol.impacto, sol.urgencia, sol.dataHoraCaptura, sol.tempoCapturaHH, sol.tempoCapturaMM, ");
        sql.append("       sol.tempoAtrasoHH, sol.tempoAtrasoMM, sol.tempoAtendimentoHH, sol.tempoAtendimentoMM, sol.idacordonivelservico, ");
        sql.append("       sol.idultimaaprovacao, sol.datahorainiciosla, sol.datahorasuspensaosla, sol.datahorareativacaosla, sol.situacaosla, aprov.aprovacao, ");
        sql.append("       s.idservico, s.nomeServico, td.idTipoDemandaServico as idtipodemandaservico2, td.nomeTipoDemandaServico, c.idContrato, c.numero, e1.nome, u1.nome as nomeUnidade1, ");
        sql.append("       e2.nome as nomeResponsavel, u2.nome as nomeUnidade2, oa.descricao as descricao2, p.nomeprioridade, fs.nomefase,  ");
        sql.append("       g1.sigla, g2.sigla as siglaGupo2, cs.nomecontato, cs.emailcontato, cs.telefonecontato, cs.localizacaofisica ,cs.idlocalidade , es.idInstanciaFluxo, sol.vencendo, it.iditemtrabalho, sol.idSolicitacaoRelacionada, soma.qtdefilhas, totalItensConfiguracao.qtdeItensConfiguracaoRelacionados  ");

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            sql.append(" , ROW_NUMBER() OVER (" + ordernarPor + ") AS Row ");
        }

        sql.append("  FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");
        sql.append("        INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("        INNER JOIN bpm_itemtrabalhofluxo it ON it.idinstancia = es.idinstanciafluxo ");
        sql.append("		left join (select idSolicitacaoRelacionada, COUNT(idsolicitacaoservico) as qtdefilhas from solicitacaoservico"
                + "		    WHERE idSolicitacaoRelacionada is not null  group by idSolicitacaoRelacionada) as soma on soma.idSolicitacaoRelacionada = sol.idsolicitacaoservico ");
        sql.append("		LEFT JOIN (	SELECT idSolicitacaoservico, COUNT(idSolicitacaoservico) AS qtdeItensConfiguracaoRelacionados FROM itemcfgsolicitacaoserv sol INNER JOIN itemconfiguracao item ON item.iditemconfiguracao = sol.iditemconfiguracao"
                + " GROUP BY idSolicitacaoservico) AS totalItensConfiguracao ON totalItensConfiguracao.idSolicitacaoservico = sol.idsolicitacaoservico ");

        sql.append(" WHERE sol.idsolicitacaopai is null ");

        List<TarefaFluxoDTO> listaFiltrada = filtrarElementosDaLista(listTarefa, gerenciamentoBean);
        int size = listaFiltrada.size();
        if (size != 0) {
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("and it.iditemtrabalho in ( select * from table(sys.odcinumberlist( ");
            } else {
                sql.append("and it.iditemtrabalho in  ( ");
            }

            int max = 1;
            int aux = 1;
            int CONSTANT = 999;
            if (size != 0) {
                // Hack oracle
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                    for (TarefaFluxoDTO tarefaFluxoDto : listaFiltrada) {
                        aux += 1;
                        sql.append(tarefaFluxoDto.getIdItemTrabalho());

                        // Modelo antigo
                        // if (aux == size) {
                        if (aux > size) {
                            sql.append(")))");
                        } else if (aux > (CONSTANT * (max - 1)) && aux <= (CONSTANT * max)) {
                            sql.append("))  union  select * from table(sys.odcinumberlist( ");
                            max++;
                        } else {
                            sql.append(",");
                        }
                    }
                } else {
                    for (TarefaFluxoDTO tarefaFluxoDto : listaFiltrada) {
                        sql.append(tarefaFluxoDto.getIdItemTrabalho());
                        if (aux == size) {
                            sql.append(")");
                        } else {
                            sql.append(",");
                        }
                        aux += 1;
                    }

                }
            }

            // FILTRA CONTRATO DO USUÁRIO LOGADO - Só retorna as Solicitações dos Contratos em que o usuário logado está inserido.
            if (listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
                sql.append(" AND c.idcontrato in ( ");
                boolean verifica = true;
                for (ContratoDTO contrato : listContratoUsuarioLogado) {
                    if (verifica) {
                        sql.append(contrato.getIdContrato());
                        verifica = false;
                    } else {
                        sql.append(",");
                        sql.append(contrato.getIdContrato());
                    }
                }
                sql.append(" )");
            }

        } else {
            return null;
        }

        // Adiciona o filtro de pesquisa caso houver filtro
        adicionarFiltroPesquisa(sql, gerenciamentoBean, parametros);

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
            Integer pgTotal = pgAtual * qtdPaginacao;
            if (pgTotal > 0) {
                pgAtual = pgTotal - qtdPaginacao;
            }
            sql.append(ordernarPor + " LIMIT " + qtdPaginacao + " OFFSET " + pgAtual);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
            Integer pgTotal = pgAtual * qtdPaginacao;
            if (pgTotal > 0) {
                pgAtual = pgTotal - qtdPaginacao;
            }
            sql.append(ordernarPor + " LIMIT " + pgAtual + ", " + qtdPaginacao);
        }

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
            Integer quantidadePaginator2 = new Integer(0);
            if (pgAtual > 0) {
                quantidadePaginator2 = qtdPaginacao * pgAtual;
                pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
            } else {
                quantidadePaginator2 = qtdPaginacao;
                pgAtual = 0;
            }
            sql.append(" ) SELECT * FROM TabelaTemporaria WHERE Row> " + pgAtual + " and Row<" + (quantidadePaginator2 + 1) + " ");
        }

        String sqlOracle = "";
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            Integer quantidadePaginator2 = new Integer(0);
            if (pgAtual > 1) {
                quantidadePaginator2 = qtdPaginacao * pgAtual;
                pgAtual = (pgAtual * qtdPaginacao) - qtdPaginacao;
                pgAtual = pgAtual + 1;
            } else {
                quantidadePaginator2 = qtdPaginacao;
                pgAtual = 0;
            }
            int intInicio = pgAtual;
            int intLimite = quantidadePaginator2;
            // Modelo antigo
            // sqlOracle = sqlOracle + ordernarPor;
            sqlOracle = sql + ordernarPor;
            sqlOracle = "SELECT * FROM (SELECT PAGING.*, ROWNUM PAGING_RN FROM" + " (" + sqlOracle + ") PAGING WHERE (ROWNUM <= " + intLimite + "))" + " WHERE (PAGING_RN >= " + intInicio + ") ";
        }

        List lista = new ArrayList();

        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            lista = this.execSQL(sqlOracle, parametros.toArray());
        } else {
            lista = this.execSQL(sql.toString(), parametros.toArray());
        }

        if (lista != null && !lista.isEmpty()) {
            return engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listByTarefas(Collection<TarefaFluxoDTO> listTarefa, TipoSolicitacaoServico[] tiposSolicitacao) throws Exception {
        boolean bIncidentes = false;
        boolean bRequisicoes = false;
        boolean bCompras = false;
        boolean bViagens = false;
        boolean bRH = false;
        if (tiposSolicitacao != null) {
            for (TipoSolicitacaoServico tipo : tiposSolicitacao) {
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
        listRetorno.add("prazoCapturaHH");
        listRetorno.add("prazoCapturaMM");
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
        listRetorno.add("idUltimaAprovacao");
        listRetorno.add("dataHoraInicioSLA");
        listRetorno.add("dataHoraSuspensaoSLA");
        listRetorno.add("dataHoraReativacaoSLA");
        listRetorno.add("situacaoSLA");
        listRetorno.add("aprovacao");
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
        listRetorno.add("idInstanciaFluxo");
        listRetorno.add("idRequisicaoProduto");
        listRetorno.add("idRequisicaoViagem");
        listRetorno.add("idRequisicaoPessoal");
        listRetorno.add("classificacao");

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT sol.idSolicitacaoServico, sol.idbaseconhecimento, sol.idServicoContrato, sol.idSolicitante, ");
        sql.append("       sol.idItemConfiguracao, sol.idItemConfiguracaoFilho, sol.idtipodemandaservico, sol.idcontatosolicitacaoservico, ");
        sql.append("       sol.idOrigem, sol.idResponsavel, sol.idTipoProblema, sol.idPrioridade, sol.idUnidade, sol.idFaseAtual, ");
        sql.append("       sol.idGrupoAtual, sol.dataHoraSolicitacao, sol.dataHoraLimite, sol.atendimentoPresencial, sol.prazoCapturaHH, sol.prazoCapturaMM, ");
        sql.append("       sol.prazoHH, sol.prazoMM, sol.descricao, sol.resposta, sol.dataHoraInicio, sol.dataHoraFim, sol.situacao, ");
        sql.append("       sol.idSolicitacaoPai, sol.detalhamentoCausa, sol.idCausaIncidente, sol.idCategoriaSolucao, ");
        sql.append("       sol.seqreabertura, sol.enviaEmailCriacao, sol.enviaEmailFinalizacao, sol.enviaEmailAcoes, ");
        sql.append("       sol.idgruponivel1, sol.solucaoTemporaria, sol.houveMudanca, sol.slaACombinar, sol.prazohhAnterior, ");
        sql.append("       sol.prazommAnterior, sol.idCalendario, sol.tempoDecorridoHH, sol.tempoDecorridoMM, sol.dataHoraSuspensao, ");
        sql.append("       sol.dataHoraReativacao, sol.impacto, sol.urgencia, sol.dataHoraCaptura, sol.tempoCapturaHH, sol.tempoCapturaMM, ");
        sql.append("       sol.tempoAtrasoHH, sol.tempoAtrasoMM, sol.tempoAtendimentoHH, sol.tempoAtendimentoMM, sol.idacordonivelservico, ");
        sql.append("       sol.idultimaaprovacao, sol.datahorainiciosla, sol.datahorasuspensaosla, sol.datahorareativacaosla, sol.situacaosla, aprov.aprovacao, ");
        sql.append("       s.idservico, s.nomeServico, td.idTipoDemandaServico, td.nomeTipoDemandaServico, c.idContrato, c.numero, e1.nome, u1.nome, ");
        sql.append("       e2.nome, u2.nome, oa.descricao, p.nomeprioridade, fs.nomefase,  ");
        sql.append("       g1.sigla, g2.sigla, cs.nomecontato, cs.emailcontato, cs.telefonecontato, cs.localizacaofisica ,cs.idlocalidade , es.idInstanciaFluxo, ");
        sql.append("       reqprod.idsolicitacaoservico, reqviagem.idsolicitacaoservico, reqrh.idsolicitacaoservico, td.classificacao");
        sql.append("  FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");
        sql.append("        LEFT JOIN requisicaoproduto reqprod ON reqprod.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("        LEFT JOIN rh_requisicaopessoal reqrh ON reqrh.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("        LEFT JOIN requisicaoviagem reqviagem ON reqviagem.idsolicitacaoservico = sol.idsolicitacaoservico ");

        sql.append(" INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("WHERE sol.idsolicitacaopai is null ");

        boolean bFiltrouTipos = false;
        if (bIncidentes) {
            if (bIncidentes) {
                sql.append(" AND (td.classificacao = 'I' ");
            }
            bFiltrouTipos = true;
        }

        if (bRequisicoes) {
            if (bFiltrouTipos) {
                sql.append(" OR ");
            } else {
                sql.append(" AND (");
            }
            sql.append(" reqprod.idsolicitacaoservico IS NULL AND reqviagem.idsolicitacaoservico IS NULL AND reqrh.idsolicitacaoservico IS NULL AND td.classificacao = 'R' ");
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

        if (listTarefa != null && !listTarefa.isEmpty()) {
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("and es.idInstanciaFluxo in ( select * from table(sys.odcinumberlist( ");
            } else {
                sql.append("and es.idInstanciaFluxo in  ( ");
            }

            int size = listTarefa.size();
            int max = 1;
            int aux = 0;
            int CONSTANT = 999;
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdInstancia());

                    if (aux == size) {
                        sql.append(")))");
                    } else if (aux > (CONSTANT * (max - 1)) && aux <= (CONSTANT * max)) {
                        sql.append("))  union  select * from table(sys.odcinumberlist( ");
                        max++;
                    } else {
                        sql.append(",");
                    }
                }
            } else {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdInstancia());

                    if (aux == size) {
                        sql.append(")");
                    } else {
                        sql.append(",");
                    }
                }

            }

        } else {
            return null;
        }

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), null);

        Collection<SolicitacaoServicoDTO> result = null;
        if (lista != null && !lista.isEmpty()) {
            result = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        }

        if (result != null) {
            for (SolicitacaoServicoDTO solicitacaoDto : result) {
                if (solicitacaoDto.getIdRequisicaoProduto() != null) {
                    solicitacaoDto.setTipoSolicitacao(TipoSolicitacaoServico.COMPRA);
                } else if (solicitacaoDto.getIdRequisicaoViagem() != null) {
                    solicitacaoDto.setTipoSolicitacao(TipoSolicitacaoServico.VIAGEM);
                } else if (solicitacaoDto.getIdRequisicaoPessoal() != null) {
                    solicitacaoDto.setTipoSolicitacao(TipoSolicitacaoServico.RH);
                } else if (solicitacaoDto.getClassificacao() != null && solicitacaoDto.getClassificacao().equalsIgnoreCase("R")) {
                    solicitacaoDto.setTipoSolicitacao(TipoSolicitacaoServico.REQUISICAO);
                } else {
                    solicitacaoDto.setTipoSolicitacao(TipoSolicitacaoServico.INCIDENTE);
                }
            }
        }

        return result;
    }

    public SolicitacaoServicoDTO listIdentificacao(Integer idItemConfiguracao) throws Exception {
        List parametro = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select identificacao from ITEMCONFIGURACAO where iditemconfiguracao = " + idItemConfiguracao + " ");
        List lista = this.execSQL(sb.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("itemConfiguracao");
        List result = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        return (SolicitacaoServicoDTO) result.get(0);
    }

    public Collection<SolicitacaoServicoDTO> listIncidentesNaoFinalizados() throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT idsolicitacaoservico, situacao, datahorasolicitacao, datahoralimite, nometipodemandaservico, datahorafim ");
        sql.append("FROM solicitacaoservico ");
        sql.append("INNER JOIN tipodemandaservico ");
        sql.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
        sql.append("LEFT OUTER JOIN usuario ");
        sql.append("ON solicitacaoservico.idresponsavel = usuario.idusuario ");
        sql.append("LEFT OUTER JOIN grupo ");
        sql.append("ON solicitacaoservico.idgrupoatual = grupo.idgrupo ");
        sql.append("WHERE tipodemandaservico.classificacao LIKE 'I' AND ");
        sql.append("(UPPER(solicitacaoservico.situacao) <> 'FECHADA' AND UPPER(solicitacaoservico.situacao) <> 'CANCELADA' ");
        sql.append(" AND UPPER(solicitacaoservico.situacao) <> 'RECLASSIFICADA')");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("dataHoraFim");

        List listSolicitacoes = engine.listConvertion(getBean(), lista, listRetorno);

        return listSolicitacoes;
    }

    /**
     * Retorna Solicitaï¿½Ãµes de Serviços de acordo com o Tipo de Demanda e Usuï¿½rio.
     *
     * @param tipoDemandaServico
     * @param grupoSeguranca
     * @param usuario
     * @return <code>Collection<SolicitacaoServicoDTO></code>
     * @throws Exception
     * @author valdoilo
     */
    public Collection<SolicitacaoServicoDTO> listSolicitacaoServico(String tipoDemandaServico, GrupoDTO grupoSeguranca, UsuarioDTO usuario, Date dataInicio, Date dataFim, String situacao)
            throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT idsolicitacaoservico, situacao, datahorasolicitacao, datahoralimite, nometipodemandaservico, datahorafim ");
        sql.append("FROM solicitacaoservico ");
        sql.append("INNER JOIN tipodemandaservico ");
        sql.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
        sql.append("LEFT OUTER JOIN usuario ");
        sql.append("ON solicitacaoservico.idresponsavel = usuario.idusuario ");
        sql.append("LEFT OUTER JOIN grupo ");
        sql.append("ON solicitacaoservico.idgrupoatual = grupo.idgrupo ");

        boolean aux = false;
        if (tipoDemandaServico != null && !tipoDemandaServico.trim().isEmpty()) {
            sql.append("WHERE ");
            sql.append("tipodemandaservico.classificacao LIKE ? ");
            aux = true;
            parametro.add(tipoDemandaServico);
        }

        if (grupoSeguranca != null && grupoSeguranca.getIdGrupo() != null) {
            if (aux) {
                sql.append("AND grupo.idgrupo = ? ");
            } else {
                sql.append("WHERE ");
                sql.append("grupo.idgrupo = ? ");
                aux = true;
            }
            parametro.add(grupoSeguranca.getIdGrupo());
        }

        if (usuario != null) {
            if (aux) {
                sql.append("AND usuario.idusuario = ? ");
            } else {
                sql.append("WHERE ");
                sql.append("usuario.idusuario = ? ");
                aux = true;
            }
            parametro.add(usuario.getIdUsuario());
        }

        if (situacao != null && !situacao.equalsIgnoreCase("")) {
            if (aux) {
                sql.append("AND solicitacaoservico.situacao = ? ");
            } else {
                sql.append("WHERE ");
                sql.append("solicitacaoservico.situacao = ? ");
                aux = true;
            }
            parametro.add(situacao);
        }

        if (aux) {
            sql.append("AND ");
        } else {
            sql.append("WHERE ");
        }

        sql.append(" (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?)");
        parametro.add(dataInicio);
        parametro.add(dataFim);

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("dataHoraFim");

        List listSolicitacoes = engine.listConvertion(getBean(), lista, listRetorno);

        return listSolicitacoes;
    }

    /**
     * Retorna Solicitaï¿½Ãµes de Serviços de acordo com criterios passados.
     *
     * @return <code>Collection<SolicitacaoServicoDTO></code>
     * @throws Exception
     * @author emauri
     */
    public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoByCriterios(Collection colCriterios) throws Exception {
        List listRetorno = new ArrayList();

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela origematendimento, faseservico e empregados estava utilizando INNER
         * JOIN, isso fazia com que as solicitações que não possuem origem contrato, fase ou solicitante não fossem retornadas. Alterado para LEFT JOIN
         */

        /* String SGBD = CITCorporeUtil.SGBD_PRINCIPAL; */
        String sql = "SELECT tempoAtendimentoHH,tempoAtendimentoMM,datahorainicio, datahorafim, idsolicitacaoservico, nomeservico, CASE WHEN solicitacaoservico.situacao = 'EmAndamento' THEN 'Em Andamento' ELSE solicitacaoservico.situacao END AS situacao, dataHoraSolicitacao, dataHoraLimite, nomeTipoDemandaServico, prazohh, prazomm, "
                + "solicitacaoservico.descricao, resposta, grupo.sigla, seqreabertura, empregado.nome, faseservico.nomefase, origematendimento.descricao,prioridade.nomeprioridade, usuario.nome, contratos.numero  "
                + "FROM solicitacaoservico "
                + "INNER JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico "
                + "INNER JOIN servicocontrato ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato AND (idcontrato = {PARAM.idContrato}  OR {PARAM.idContrato} = -1 )"
                + "INNER JOIN servico ON servicocontrato.idservico = servico.idservico LEFT OUTER JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual "
                + "INNER JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato "
                + "LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante "
                + "inner join usuario usuario on usuario.idusuario = solicitacaoservico.idresponsavel "
                + "LEFT JOIN  faseservico faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual "
                + "LEFT JOIN  origematendimento origematendimento  ON origematendimento.idorigem = solicitacaoservico.idorigem "
                + "INNER JOIN prioridade prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade "
                + "LEFT JOIN unidade unidade ON unidade.idunidade = solicitacaoservico.idunidade "
                + "WHERE (UPPER(tipodemandaservico.classificacao) = UPPER('{PARAM.classificacao}') OR '{PARAM.classificacao}' = '*') "
                + "AND (solicitacaoservico.idSolicitacaoServico = {PARAM.idSolicitacaoServico} OR {PARAM.idSolicitacaoServico} = -1) "
                + "AND (servicocontrato.idservico = {PARAM.idServico} OR {PARAM.idServico} = -1) "
                + "AND (solicitacaoservico.idprioridade = {PARAM.idPrioridade} OR {PARAM.idPrioridade} = -1) "
                + "AND (solicitacaoservico.idorigem = {PARAM.idOrigem} OR {PARAM.idOrigem} = -1) "
                + "AND (solicitacaoservico.idunidade = {PARAM.idUnidade} OR {PARAM.idUnidade} = -1) "
                + "AND (solicitacaoservico.idfaseatual = {PARAM.idFaseAtual} OR {PARAM.idFaseAtual} = -1) "
                + "AND (solicitacaoservico.idgrupoatual = {PARAM.idGrupoAtual} OR {PARAM.idGrupoAtual} = -1) "
                + "AND (UPPER(solicitacaoservico.situacao) = UPPER('{PARAM.situacao}') OR UPPER('{PARAM.situacao}') = '*') "
                + "AND (solicitacaoservico.idsolicitante = {PARAM.idsolicitante} OR {PARAM.idsolicitante} = -1) "
                + "AND (solicitacaoservico.iditemconfiguracao = {PARAM.iditemconfiguracao} OR {PARAM.iditemconfiguracao} = -1) "
                + "AND (solicitacaoservico.idTipoDemandaServico = {PARAM.idTipoDemandaServico} OR {PARAM.idTipoDemandaServico} = -1) "
                + "AND (solicitacaoservico.idResponsavel = {PARAM.idResponsavel} OR {PARAM.idResponsavel} = -1) "
                + "AND (servico.idservico = {PARAM.idServico1} OR {PARAM.idServico1} = -1) "
                + "AND (UPPER(solicitacaoservico.descricao) like UPPER('%{PARAM.palavraChave}%') OR UPPER(servico.nomeservico) like UPPER('%{PARAM.palavraChave}%')) "
                + "AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) " + " ORDER BY {PARAM.ordenacao}";

        Date dataInicio = null;
        Timestamp dataFim = null;
        Date dataInicioFechamento = null;
        Timestamp dataFimFechamento = null;
        for (Iterator it = colCriterios.iterator(); it.hasNext();) {
            Condition condition = (Condition) it.next();
            if (condition.getFiledClass().equalsIgnoreCase("dataInicial")) {
                dataInicio = (Date) condition.getValue();
            } else if (condition.getFiledClass().equalsIgnoreCase("dataFinal")) {
                Object obj = condition.getValue();
                if (Date.class.isInstance(obj)) {
                    dataFim = new Timestamp(((Date) obj).getTime());
                } else if (Timestamp.class.isInstance(obj)) {
                    dataFim = (Timestamp) condition.getValue();
                }
            } else {
                sql = sql.replaceAll("\\{PARAM." + condition.getFiledClass() + "\\}", "" + condition.getValue());
            }
        }
        List lista = new ArrayList();
        List parametros = new ArrayList();
        parametros.add(dataInicio);
        parametros.add(dataFim);
        parametros.add(dataInicioFechamento);
        parametros.add(dataFimFechamento);

        lista = this.execSQL(sql, parametros.toArray());
        listRetorno.add("tempoAtendimentoHH");
        listRetorno.add("tempoAtendimentoMM");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraFim");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("situacao");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("descricao");
        listRetorno.add("resposta");
        listRetorno.add("siglaGrupo");
        listRetorno.add("seqReabertura");
        listRetorno.add("nomeSolicitante");
        listRetorno.add("faseAtual");
        listRetorno.add("origem");
        listRetorno.add("prioridade");
        listRetorno.add("responsavel");
        listRetorno.add("contrato");

        List listSolicitacoes = engine.listConvertion(getBean(), lista, listRetorno);

        return listSolicitacoes;
    }

    public List<SolicitacaoServicoDTO> listSolicitacaoServicoByItemConfiguracao(Integer idItemConfiguracao) throws Exception {
        List parametro = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT sol.idsolicitacaoservico, ser.nomeServico, tempoAtendimentoHH, tempoAtendimentoMM, datahorainicio, datahorafim, ");
        /**Alterado select [...] nome para emp.nome para evitar conflitos com novo campo nome na tabela itemconfiguracao
         * 
         * @author thyen.chang
         */
        sb.append(" sol.situacao, dataHoraSolicitacao, dataHoraLimite, sol.descricao, emp.nome ");
        sb.append("	FROM solicitacaoservico sol  ");
        sb.append("	INNER JOIN itemcfgsolicitacaoserv itemc ON sol.idsolicitacaoservico = itemc.idsolicitacaoservico ");
        sb.append("	INNER JOIN itemconfiguracao item ON itemc.iditemconfiguracao = item.iditemconfiguracao  ");
        sb.append("	INNER JOIN servicocontrato ON sol.idservicocontrato = servicocontrato.idservicocontrato  ");
        sb.append("	INNER JOIN servico  ser ON servicocontrato.idservico = ser.idservico  ");
        sb.append("	INNER JOIN empregados emp ON emp.idempregado = sol.idsolicitante  ");
        /**
         * Regra modificada para trazer todos os incidentes independente do status - Solicitante Emauri
         *
         * @author flavio.santana 20/11/2013 17:14
         */
        // sb.append("	where situacao = 'EmAndamento' and item.iditemconfiguracao = ?  ");
        sb.append("	where item.iditemconfiguracao = ?  ");
        sb.append(" ORDER BY dataHoraSolicitacao DESC ");
        /**
         * A regra relacionada ao status estava comentada
         *
         * @author flavio.santana 25/10/2013 14:00
         */
        parametro.add(idItemConfiguracao);

        List lista = this.execSQL(sb.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("tempoAtendimentoHH");
        listRetorno.add("tempoAtendimentoMM");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraFim");
        listRetorno.add("situacao");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("descricao");
        listRetorno.add("nomeSolicitante");

        List result = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);

        return result;
    }

    public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoEmAndamento(Integer idSolicitacaoServico) {

        ArrayList<Condition> condicoes = new ArrayList<Condition>();
        List<Order> ordenacao = new ArrayList<Order>();

        condicoes.add(new Condition("idSolicitacaoPai", "IS", null));
        condicoes.add(new Condition("situacao", "=", "EmAndamento"));

        condicoes.add(new Condition("idSolicitacaoServico", "<>", idSolicitacaoServico));

        ordenacao.add(new Order("idSolicitacaoServico"));

        Collection<SolicitacaoServicoDTO> listSolicitacoesPai = new ArrayList<SolicitacaoServicoDTO>();

        try {

            listSolicitacoesPai = this.findByCondition(condicoes, ordenacao);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return listSolicitacoesPai;
    }

    public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoNaoFinalizadas() throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT idsolicitacaoservico, situacao, datahorasolicitacao, datahoralimite, nometipodemandaservico, datahorafim ");
        sql.append("FROM solicitacaoservico ");
        sql.append("INNER JOIN tipodemandaservico ");
        sql.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
        sql.append("LEFT OUTER JOIN usuario ");
        sql.append("ON solicitacaoservico.idresponsavel = usuario.idusuario ");
        sql.append("LEFT OUTER JOIN grupo ");
        sql.append("ON solicitacaoservico.idgrupoatual = grupo.idgrupo ");
        sql.append("WHERE ");
        sql.append("(UPPER(solicitacaoservico.situacao) <> 'FECHADA' AND UPPER(solicitacaoservico.situacao) <> 'CANCELADA' ");
        sql.append(" AND UPPER(solicitacaoservico.situacao) <> 'RECLASSIFICADA')");

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("situacao");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("dataHoraFim");

        List listSolicitacoes = engine.listConvertion(getBean(), lista, listRetorno);

        return listSolicitacoes;
    }

    public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada(int idSolicitacaoPai) {

        ArrayList<Condition> condicoes = new ArrayList<Condition>();
        List<Order> ordenacao = new ArrayList<Order>();

        condicoes.add(new Condition("idSolicitacaoPai", "=", idSolicitacaoPai));

        ordenacao.add(new Order("idSolicitacaoServico"));

        Collection<SolicitacaoServicoDTO> listSolicitacoesPai = new ArrayList<SolicitacaoServicoDTO>();

        try {

            listSolicitacoesPai = this.findByCondition(condicoes, ordenacao);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return listSolicitacoesPai;
    }

    public Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionadaPai(int idSolicitacaoRelacionada) {

        ArrayList<Condition> condicoes = new ArrayList<Condition>();
        List<Order> ordenacao = new ArrayList<Order>();

        condicoes.add(new Condition("idSolicitacaoRelacionada", "=", idSolicitacaoRelacionada));

        ordenacao.add(new Order("idSolicitacaoServico"));

        Collection<SolicitacaoServicoDTO> listSolicitacoesPai = new ArrayList<SolicitacaoServicoDTO>();

        try {

            listSolicitacoesPai = this.findByCondition(condicoes, ordenacao);

        } catch (Exception e) {

            e.printStackTrace();

        }

        return listSolicitacoesPai;
    }

    public Collection listSolicitacoesByRegra() throws Exception {
        List listRetorno = new ArrayList();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT distinct (s.idsolicitacaoservico), s.idAcordoNivelServico, ans.tempoauto, ans.idprioridadeauto1, ans.idgrupo1, s.impacto, s.urgencia, ");
        sql.append("s.idprioridade, s.datahoralimite,  regra1.idcontrato as idcontrato, regra2.idservico as idservico, regra3.idsolicitante as idsolicitante, ");
        sql.append("regra4.idgrupo as idgrupo, regra5.idtipodemandaservico as idtipodemandaservico, s.vencendo, s.criouProblemaAutomatico, s.datahorasolicitacao, s.datahoralimite, s.prazohh, s.prazomm ");
        sql.append("FROM solicitacaoservico s ");
        sql.append("INNER JOIN servicocontrato servicocontrato on servicocontrato.idservicocontrato = s.idservicocontrato ");
        sql.append("INNER JOIN servico servico on servico.idservico = servicocontrato.idservico ");
        sql.append("INNER JOIN contratos contratos on contratos.idcontrato = servicocontrato.idcontrato ");
        sql.append("INNER JOIN acordonivelservico ans on ans.idacordonivelservico = s.idacordonivelservico ");
        sql.append("INNER JOIN empregados solicitante on solicitante.idempregado = s.idsolicitante ");
        sql.append("INNER JOIN tipodemandaservico tipodemandaservico on tipodemandaservico.idtipodemandaservico = s.idtipodemandaservico ");
        sql.append("LEFT JOIN grupo grupo on grupo.idgrupo = s.idgrupoatual ");
        sql.append("LEFT JOIN regraescalonamento regra1 on regra1.idcontrato = contratos.idcontrato ");
        sql.append("LEFT JOIN regraescalonamento regra2 on regra2.idservico = servico.idservico ");
        sql.append("LEFT JOIN regraescalonamento regra3 on regra3.idsolicitante = solicitante.idempregado ");
        sql.append("LEFT JOIN regraescalonamento regra4 on regra4.idgrupo = grupo.idgrupo ");
        sql.append("LEFT JOIN regraescalonamento regra5 on regra5.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
        sql.append("WHERE UPPER(s.situacao) not in ('FECHADA', 'CANCELADA', 'RESOLVIDA', 'SUSPENSA') ");

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("idAcordoNivelServico");
        listRetorno.add("tempoAuto");
        listRetorno.add("idPrioridadeAuto1");
        listRetorno.add("idGrupo1");
        listRetorno.add("impacto");
        listRetorno.add("urgencia");
        listRetorno.add("idPrioridade");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("idContrato");
        listRetorno.add("idServico");
        listRetorno.add("idSolicitante");
        listRetorno.add("idGrupo");
        listRetorno.add("idTipoDemandaServico");
        listRetorno.add("vencendo");
        listRetorno.add("criouProblemaAutomatico");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");

        List list = this.execSQL(sql.toString(), null);
        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<SolicitacaoServicoDTO> listSolicitacoesFilhas() throws Exception {
        List condicao = new ArrayList();
        condicao.add(new Condition("idSolicitacaoPai", "is not", null));
        return super.findByCondition(condicao, null);
    }

    public SolicitacaoServicoDTO obterQuantidadeSolicitacoesServico(Integer idServicoContrato, java.util.Date dataInicio, java.util.Date dataFim) throws Exception {
        List parametros = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append(" select ");
        sql.append(" count(ss.idsolicitacaoservico) quantidade ");
        sql.append(" from ");
        sql.append(" solicitacaoservico ss ");
        sql.append(" inner join ");
        sql.append(" servicocontrato sc on sc.idservicocontrato = ss.idservicocontrato ");
        sql.append(" where ");
        sql.append(" ss.idservicocontrato in (select vsc2.idservicocontrato from valorservicocontrato vsc2 where vsc2.idvalorservicocontrato = ?) ");
        sql.append(" and ");
        sql.append(" ss.datahorainicio between ? and ? and  UPPER(ss.situacao) = 'FECHADA'");

        parametros.add(idServicoContrato);

        /*
         * int dia = UtilDatas.getDiaMesAno(dataInicio, 1); int mes = UtilDatas.getDiaMesAno(dataInicio, 2); int ano = UtilDatas.getDiaMesAno(dataInicio, 3);
         */

        /* parametros.add(String.format("%d-%d-%d 00:00:00", ano, mes, dia)); */
        parametros.add(dataInicio);

        /*
         * dia = UtilDatas.getDiaMesAno(dataFim, 1); mes = UtilDatas.getDiaMesAno(dataFim, 2); ano = UtilDatas.getDiaMesAno(dataFim, 3);
         */

        /* parametros.add(String.format("%d-%d-%d 23:59:59", ano, mes, dia)); */
        parametros.add(transformaHoraFinal((Date) dataFim));

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametros.toArray());

        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("quantidade");
        List result = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);

        if (result != null && !result.isEmpty()) {
            return (SolicitacaoServicoDTO) result.get(0);
        } else {
            return null;
        }
    }

    public List<SolicitacaoServicoDTO> obterSolicitacoesServico(Date dataInicio, Date dataFim, int idContrato, int idServico) throws Exception {
        List<SolicitacaoServicoDTO> solicitacoesServicoDTO = null;

        StringBuilder sql = new StringBuilder();

        sql.append(" select ");
        sql.append(" ss.idsolicitacaoservico, s.nomeservico, ss.datahorainicio, ss.datahorafim, vsc.datainicio, vsc.datafim ");
        sql.append(" from ");
        sql.append(" solicitacaoservico ss ");
        sql.append(" inner join ");
        sql.append(" servicocontrato sc on sc.idservicocontrato = ss.idservicocontrato ");
        sql.append(" inner join ");
        sql.append(" valorservicocontrato vsc on vsc.idservicocontrato = sc.idservicocontrato ");
        sql.append(" inner join ");
        sql.append(" contratos c on c.idcontrato = sc.idcontrato ");
        sql.append(" inner join ");
        sql.append(" servico s on s.idservico = sc.idservico ");
        sql.append(" where ");
        sql.append(" c.idcontrato = ? and ");
        sql.append(" s.idservico = ? and ");
        sql.append(" ss.datahorainicio between ? and ? and ");
        sql.append(" UPPER(ss.situacao) = 'FECHADA' ");
        sql.append(" group by ");
        sql.append(" ss.idsolicitacaoservico, ");
        sql.append(" s.nomeservico, ");
        sql.append(" ss.datahorainicio, ");
        sql.append(" ss.datahorafim, ");
        sql.append(" vsc.valorServico, ");
        sql.append(" vsc.datainicio, ");
        sql.append(" vsc.datafim");

        Object[] params = { dataInicio, transformaHoraFinal(dataFim), idContrato, idServico };

        try {
            solicitacoesServicoDTO = this.execSQL(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização da solicitacaoServico.");
            e.printStackTrace();
        }
        return solicitacoesServicoDTO;
    }

    public String paginacaoOracle(String strSQL, int intInicio, int intLimite) {
        strSQL = strSQL + " order by sol.idsolicitacaoservico ";
        return "SELECT * FROM (SELECT PAGING.*, ROWNUM PAGING_RN FROM" + " (" + strSQL + ") PAGING WHERE (ROWNUM <= " + intLimite + "))" + " WHERE (PAGING_RN >= " + intInicio + ") ";
    }

    @SuppressWarnings("unused")
    public Integer qtdTipoDemandaPrioridade(List<TarefaFluxoDTO> listTarefa, Integer idTipoDemandaServico, Integer idPrioridade) throws Exception {

        StringBuilder sql = new StringBuilder();
        Integer total = 0;

        sql.append("SELECT COUNT(*) as quantidade ");

        sql.append("  FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");

        sql.append(" INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("WHERE sol.idsolicitacaopai is null ");

        int qtdIds = 0;
        if (listTarefa != null && !listTarefa.isEmpty()) {
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("and es.idInstanciaFluxo in ( select * from table(sys.odcinumberlist( ");
            } else {
                sql.append("and es.idInstanciaFluxo in  ( ");
            }

            int size = listTarefa.size();
            int max = 1;
            int aux = 0;
            int CONSTANT = 999;
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdInstancia());

                    if (aux == size) {
                        sql.append(")))");
                    } else if (aux > (CONSTANT * (max - 1)) && aux <= (CONSTANT * max)) {
                        sql.append("))  union  select * from table(sys.odcinumberlist( ");
                        qtdIds++;
                        max++;
                    } else {
                        sql.append(",");
                    }
                }
            } else {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdInstancia());
                    if (aux == size) {
                        sql.append(")");
                    } else {
                        sql.append(",");
                    }
                }

            }

            if (idTipoDemandaServico > 0) {
                sql.append(" AND s.idtipodemandaservico = " + idTipoDemandaServico);
            }

            if (idPrioridade > 0) {
                sql.append(" AND sol.idprioridade = " + idPrioridade);
            }

            Long quantidadeLong = 0l;
            BigDecimal totalLinhaBigDecimal;
            Integer totalLinhaInteger;
            List lista = new ArrayList();
            lista = this.execSQL(sql.toString(), null);
            if (lista != null) {
                Object[] listObject = (Object[]) lista.get(0);
                if (listObject != null && listObject.length > 0) {
                    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
                        quantidadeLong = (Long) listObject[0];
                    }
                    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                        totalLinhaBigDecimal = (BigDecimal) listObject[0];
                        quantidadeLong = totalLinhaBigDecimal.longValue();
                    }
                    if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
                        totalLinhaInteger = (Integer) listObject[0];
                        quantidadeLong = Long.valueOf(totalLinhaInteger);
                    }
                }
            }

            total = Integer.valueOf(quantidadeLong.toString());

        } else {
            return null;
        }
        return total;
    }

    public Collection<SolicitacaoServicoDTO> quantidadeSolicitacaoPorBaseConhecimento(SolicitacaoServicoDTO solicitacao) throws Exception {
        StringBuilder sql = new StringBuilder();
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();

        sql.append("select idbaseconhecimento,count(idbaseconhecimento) from solicitacaoservico where idbaseconhecimento = ? group by idbaseconhecimento");

        parametro.add(solicitacao.getIdBaseConhecimento());
        listRetorno.add("idBaseConhecimento");
        listRetorno.add("quantidade");

        List list = execSQL(sql.toString(), parametro.toArray());
        if (list != null && !list.isEmpty()) {
            Collection<SolicitacaoServicoDTO> listaQuantidadeSolicitacaoServicoPorBaseConhecimento = this.listConvertion(SolicitacaoServicoDTO.class, list, listRetorno);
            return listaQuantidadeSolicitacaoServicoPorBaseConhecimento;
            /*
             * for(SolicitacaoServicoDTO solicitacaoDto : listaQuantidadeSolicitacaoServicoPorBaseConhecimento){ return solicitacaoDto.getQuantidade(); }
             */
        }
        return null;
    }

    public Collection<SolicitacaoServicoDTO> relatorioControleSla(SolicitacaoServicoDTO solicitacaoServicoDTO) throws Exception {
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 10h00min - ID Citsmart: 120770 Motivo/Comentário: A tabela empregados estava utilizando INNER JOIN, isso fazia com que as
         * solicitações que não possuem solicitante não fossem retornadas. Alterado para LEFT JOIN
         */

        sql.append(" SELECT ss.prazohh,  ");
        sql.append("    ss.prazomm,  ");
        sql.append("    ss.idsolicitacaoservico,  ");
        sql.append("    ts.nometiposervico,  ");
        sql.append("    s.nomeservico,  ");
        sql.append("    sc.idcontrato, ");
        sql.append("    co.numero, ");
        sql.append("    e.nome,  ");
        sql.append("    ss.datahorasolicitacao, ");
        sql.append("   ss.datahoralimite, ");
        sql.append("   ss.datahorafim,  ");
        sql.append("   ss.idprioridade,  ");
        sql.append("   p.nomeprioridade,  ");
        sql.append("   ss.tempoatrasohh,  ");
        sql.append("    ss.tempoatrasomm,  ");
        sql.append("    ss.situacaosla,  ");
        sql.append("    ss.datahorasuspensaosla,  ");
        sql.append("    ss.datahorasuspensao,  ");
        sql.append("    ( CASE  ");
        sql.append("       WHEN UCASE(ss.situacao) LIKE ( 'EMANDAMENTO' ) THEN 'Em Andamento'  ");
        sql.append("      ELSE ss.situacao  ");
        sql.append("     end ), ");
        sql.append("    tempoatendimentohh,  ");
        sql.append("    tempoatendimentomm, ");
        sql.append("    ( CASE  ");
        sql.append("       WHEN g.nome IS NULL THEN '--'  ");
        sql.append("       ELSE g.nome ");
        sql.append("     end ) AS nomeGrupo,  ");
        sql.append("    ( CASE  WHEN ss.prazomm = 0 and ss.prazohh = 0 THEN  'S' ");
        sql.append("        ELSE ( CASE  ");
        sql.append("                 WHEN ss.datahoralimite IS NULL THEN 'N'  ");
        sql.append("                 WHEN ( CASE  ");
        sql.append("                         WHEN ss.datahorafim IS NOT NULL THEN ss.datahorafim  ");
        sql.append("                         WHEN ss.situacaosla = 'S' THEN ss.datahorasuspensaosla  ");
        sql.append("                         WHEN UCASE(ss.situacao) like 'SUSPENSA' THEN ss.datahorasuspensao  ");
        sql.append("                         WHEN UCASE(ss.situacao) like 'FECHADA' THEN ss.datahorafim  ");
        sql.append("                          ELSE CURRENT_TIMESTAMP ");
        sql.append("                        end ) >= ss.datahoralimite THEN 'S'  ");
        sql.append("                 ELSE 'N'  ");
        sql.append("               end )  ");
        sql.append("      end ) AS slaAtrasado ");
        sql.append(" FROM   solicitacaoservico ss  ");
        sql.append("     INNER JOIN execucaosolicitacao es ");
        sql.append("            ON es.idsolicitacaoservico = ss.idsolicitacaoservico ");
        sql.append("     INNER JOIN servicocontrato sc  ");
        sql.append("            ON sc.idservicocontrato = ss. idservicocontrato  ");
        sql.append("    INNER JOIN servico s  ");
        sql.append("           ON s.idservico = sc.idservico  ");
        sql.append("    INNER JOIN tiposervico ts  ");
        sql.append("            ON ts.idtiposervico = s.idtiposervico  ");
        sql.append("    INNER JOIN prioridade p  ");
        sql.append("            ON p.idprioridade = ss.idprioridade ");
        sql.append("    LEFT JOIN empregados e  ");
        sql.append("           ON e.idempregado = ss.idsolicitante  ");
        sql.append("   INNER JOIN contratos co  ");
        sql.append("           ON co.idcontrato = sc.idcontrato  ");
        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 23/10/2013 - Horário: 10h47min - ID Citsmart: 120770 Motivo/Comentário: O grupo estava utilizando INNER JOIN e quando não existia relacionamento o
         * resultado do grupo não era retornado.
         */
        sql.append("   LEFT JOIN grupo g  ");
        sql.append("           ON g.idgrupo = ss.idgrupoatual  ");
        sql.append(" inner join tipodemandaservico td on td.idtipodemandaservico = ss.idtipodemandaservico  ");

        if (solicitacaoServicoDTO.getIdSolicitacaoServico() != null) {
            sql.append("WHERE   ss.idsolicitacaoservico = ? ");
            parametro.add(solicitacaoServicoDTO.getIdSolicitacaoServico());
        } else {
            if (solicitacaoServicoDTO.getDataInicio() != null && solicitacaoServicoDTO.getDataFim() != null) {
                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                    sql.append("where to_char(ss.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
                } else {
                    sql.append("where ss.datahorasolicitacao BETWEEN ? AND ? ");
                }

                if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    parametro.add(formatter.format(solicitacaoServicoDTO.getDataInicio()));
                    parametro.add(formatter.format(solicitacaoServicoDTO.getDataFim()));
                } else {
                    parametro.add(solicitacaoServicoDTO.getDataInicio());
                    parametro.add(transformaHoraFinal(solicitacaoServicoDTO.getDataFim()));
                }
                /*
                 * Desenvolvedor: Rodrigo Pecci - Data: 25/10/2013 - Horário: 14h21min - ID Citsmart: 120770 Motivo/Comentário: Removida a condição que não exibia solicitações com contrato de serviços
                 * que foram deletados. Todos devem ser exibidos.
                 */
                if (solicitacaoServicoDTO.getIdContrato() != null) {
                    sql.append("AND sc.idcontrato = ? ");
                    parametro.add(solicitacaoServicoDTO.getIdContrato());
                }
                if (solicitacaoServicoDTO.getIdSolicitante() != null) {
                    sql.append("AND ss.idsolicitante = ? ");
                    parametro.add(solicitacaoServicoDTO.getIdSolicitante());
                }
                if (solicitacaoServicoDTO.getIdPrioridade() != null) {
                    sql.append("AND ss.idprioridade = ? ");
                    parametro.add(solicitacaoServicoDTO.getIdPrioridade());
                }
                if (solicitacaoServicoDTO.getIdGrupoAtual() != null) {
                    sql.append("AND ss.idgrupoatual = ? ");
                    parametro.add(solicitacaoServicoDTO.getIdGrupoAtual());
                }
                // Esse servico refere a tabela servico e nao ServicoContrato
                if (solicitacaoServicoDTO.getIdServico() != null) {
                    sql.append("AND s.idServico = ? ");
                    parametro.add(solicitacaoServicoDTO.getIdServico());
                }
                if (solicitacaoServicoDTO.getIdTipoServico() != null) {
                    sql.append("AND s.idtiposervico = ? ");
                    parametro.add(solicitacaoServicoDTO.getIdTipoServico());
                }
				if (solicitacaoServicoDTO.getIdUnidade() != null && !solicitacaoServicoDTO.getIdUnidade().equals(-1) && solicitacaoServicoDTO.getIdUnidade()!= 0) {
                    sql.append("AND ss.idUnidade = ? ");
                    parametro.add(solicitacaoServicoDTO.getIdUnidade());
                }
                if (solicitacaoServicoDTO.getNomeServico() != null && !solicitacaoServicoDTO.getNomeServico().isEmpty()) {
                    sql.append("AND (s.nomeservico like '%" + solicitacaoServicoDTO.getNomeServico() + "%' or s.nomeservico like '%" + solicitacaoServicoDTO.getNomeServico().toUpperCase() + "%') ");
                }

                if (solicitacaoServicoDTO.getIdOrigem() != null && !solicitacaoServicoDTO.getIdOrigem().equals(-1)) {
                    sql.append("AND ss.idorigem = ? ");
                    parametro.add(solicitacaoServicoDTO.getIdOrigem());
                }

                if (solicitacaoServicoDTO.getSla() != null && !solicitacaoServicoDTO.getSla().isEmpty()) {
                    String[] dividirSLA = solicitacaoServicoDTO.getSla().split(":");
                    sql.append("AND ss.prazohh = ? AND ss.prazomm = ? ");
                    parametro.add(Integer.parseInt(dividirSLA[0]));
                    parametro.add(Integer.parseInt(dividirSLA[1]));
                }

                if (solicitacaoServicoDTO.getSituacao() != null && !solicitacaoServicoDTO.getSituacao().trim().equalsIgnoreCase("")) {
                    sql.append("AND ss.situacao = ? ");
                    parametro.add(solicitacaoServicoDTO.getSituacao());
                }

                if (solicitacaoServicoDTO.getClassificacao() != null && !solicitacaoServicoDTO.getClassificacao().equalsIgnoreCase("")) {
                    sql.append("AND (td.classificacao like '%" + solicitacaoServicoDTO.getClassificacao() + "%' or td.classificacao like '%" + solicitacaoServicoDTO.getClassificacao().toUpperCase()
                            + "%') ");
                }

                sql.append("ORDER BY ss.idsolicitacaoservico");
            }
        }
        List lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeTipoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("idContrato");
        listRetorno.add("contrato");
        listRetorno.add("nomeSolicitante");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("dataHoraFim");
        listRetorno.add("idPrioridade");
        listRetorno.add("nomePrioridade");
        listRetorno.add("tempoAtrasoHH");
        listRetorno.add("tempoAtrasoMM");
        listRetorno.add("situacaoSLA");
        listRetorno.add("dataHoraSuspensaoSLA");
        listRetorno.add("dataHoraSuspensao");
        listRetorno.add("situacao");
        listRetorno.add("tempoAtendimentoHH");
        listRetorno.add("tempoAtendimentoMM");
        listRetorno.add("grupoAtual");
        listRetorno.add("atrasoSLAStr");
        List result = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        if (result != null) {
            return result;
        } else {
            return null;
        }
    }

    public SolicitacaoServicoDTO restoreAll(Integer idSolicitacao) throws Exception {
        List parametro = new ArrayList();
        parametro.add(idSolicitacao);

        String sql = getSQLRestoreAll();
        sql += "  WHERE sol.idsolicitacaoservico = ? ";

        List lista = this.execSQL(sql.toString(), parametro.toArray());

        if (lista != null && !lista.isEmpty()) {
            List listaResult = engine.listConvertion(SolicitacaoServicoDTO.class, lista, getColunasRestoreAll());
            return (SolicitacaoServicoDTO) listaResult.get(0);
        } else {
            return null;
        }
    }

    public SolicitacaoServicoDTO restoreByIdInstanciaFluxo(Integer idInstanciaFluxo) throws Exception {
        List parametro = new ArrayList();
        parametro.add(idInstanciaFluxo);

        String sql = getSQLRestoreAll();
        sql += " INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ";
        sql += " WHERE sol.idsolicitacaopai is null and es.idInstanciaFluxo = ? ";

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametro.toArray());

        if (lista != null && !lista.isEmpty()) {
            List listaResult = engine.listConvertion(SolicitacaoServicoDTO.class, lista, getColunasRestoreAll());
            return (SolicitacaoServicoDTO) listaResult.get(0);
        } else {
            return null;
        }
    }

    public SolicitacaoServicoDTO restoreInfoEmails(Integer idSolicitacao) throws Exception {
        List parametro = new ArrayList();
        parametro.add(idSolicitacao);

        String sql = "select o.ocorrencia as registroexecucao, o.dataregistro as dataRegistroOcorrencia, o.horaregistro as horaRegistroOcorrencia, o.registradopor, o.categoria as categoriaocorrencia "
                + " from solicitacaoservico sol "
                + " inner join ocorrenciasolicitacao o on sol.idsolicitacaoservico = o.idsolicitacaoservico "
                + " where sol.idsolicitacaoservico = ? order by idocorrencia desc";

        List lista = this.execSQL(sql.toString(), parametro.toArray());

        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("registroexecucao");
        listRetorno.add("dataRegistroOcorrencia");
        listRetorno.add("horaRegistroOcorrencia");
        listRetorno.add("registradoPor");
        listRetorno.add("categoriaOcorrencia");

        if (lista != null && !lista.isEmpty()) {
            List listaResult = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
            if (listaResult.size() >= 2) {
                return (SolicitacaoServicoDTO) listaResult.get(1);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public RelatorioQuantitativoRetornoDTO retornarIdEncerramento(String encerramento, RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception {

        List parametro = new ArrayList();
        StringBuilder sb = new StringBuilder();

        sb.append("select idelemento from bpm_elementofluxo where nome LIKE '" + encerramento + "'  and idFluxo = ?");

        parametro.add(relatorioQuantitativoRetornoDTO.getIdFluxo());

        List lista = this.execSQL(sb.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("idElemento");

        if (lista != null && !lista.isEmpty()) {
            List result = engine.listConvertion(RelatorioQuantitativoRetornoDTO.class, lista, listRetorno);
            return (RelatorioQuantitativoRetornoDTO) result.get(0);
        } else {
            return new RelatorioQuantitativoRetornoDTO();
        }
    }

    /**
     * Retorna SolicitacaoServico com as informações do contato setado.
     *
     * @param nomeContato
     * @return
     * @throws Exception
     */
    public SolicitacaoServicoDTO retornaSolicitacaoServicoComInformacoesDoContato(String nomeContato) throws Exception {
        List parametro = new ArrayList();

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT nome, email, telefone FROM EMPREGADOS WHERE nome = ? ");
        parametro.add(nomeContato);
        List lista = this.execSQL(sb.toString(), parametro.toArray());

        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("nomecontato");
        listRetorno.add("emailcontato");
        listRetorno.add("telefonecontato");

        List result = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);

        if (result != null && !result.isEmpty()) {
            return (SolicitacaoServicoDTO) result.get(0);
        } else {
            return null;
        }
    }

    /**
     * Retorna SolicitacaoServico com Item de Configuração do Solicitante selecionado.
     *
     * @param login
     * @return SolicitacaoServicoDTO
     * @throws Exception
     */
    public SolicitacaoServicoDTO retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(String login) throws Exception {
        List parametro = new ArrayList();

        StringBuilder sb = new StringBuilder();

        sb.append("SELECT identificacao,idItemConfiguracao FROM ITEMCONFIGURACAO WHERE IDITEMCONFIGURACAOPAI IS NULL AND DATAFIM IS NULL and identificacao  ");
        sb.append("like ?  order by datainicio desc");
        parametro.add(login + "%");
        List lista = this.execSQL(sb.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("itemConfiguracao");
        listRetorno.add("idItemConfiguracao");
        List result = engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);

        if (result != null && !result.isEmpty()) {
            return (SolicitacaoServicoDTO) result.get(0);
        } else {
            return null;
        }
    }

    public RelatorioQuantitativoRetornoDTO servicoRetorno(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception {
        List parametro = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("select ocorrenciasolicitacao.idocorrencia,ocorrenciasolicitacao.dataregistro, ocorrenciasolicitacao.horaregistro ");
        sql.append("from ocorrenciasolicitacao ocorrenciasolicitacao ");
        sql.append("INNER JOIN bpm_itemtrabalhofluxo bpm_itemtrabalhofluxo ");
        sql.append("ON bpm_itemtrabalhofluxo.iditemtrabalho = ocorrenciasolicitacao.iditemtrabalho ");
        sql.append("where ocorrenciasolicitacao.idsolicitacaoservico = ? ");
        sql.append("AND bpm_itemtrabalhofluxo.idinstancia = ?  ");
        sql.append("AND bpm_itemtrabalhofluxo.iditemtrabalho = ? ");
        sql.append("ORDER  BY ocorrenciasolicitacao.idocorrencia DESC LIMIT 1; ");

        parametro.add(relatorioQuantitativoRetornoDTO.getIdSolicitacaoServico());
        parametro.add(relatorioQuantitativoRetornoDTO.getIdInstancia());
        parametro.add(relatorioQuantitativoRetornoDTO.getIdItemTrabalho());

        List lista = this.execSQL(sql.toString(), parametro.toArray());
        List<String> listRetorno = new ArrayList<String>();
        listRetorno.add("idOcorrencia");
        listRetorno.add("dataRegistro");
        listRetorno.add("horaRegistro");

        if (lista != null && !lista.isEmpty()) {
            List result = engine.listConvertion(RelatorioQuantitativoRetornoDTO.class, lista, listRetorno);
            return (RelatorioQuantitativoRetornoDTO) result.get(0);
        } else {
            return null;
        }

    }

    public Integer totalDePaginas(Integer itensPorPagina, Collection<TarefaFluxoDTO> listTarefa) throws Exception {
        StringBuilder sql = new StringBuilder();
        // String ids = idsInstancia.toString();

        sql.append(" SELECT COUNT(*) ");
        sql.append(" FROM solicitacaoservico sol ");
        sql.append(" LEFT JOIN servicocontrato sc ON sc.idservicocontrato = sol.idservicocontrato ");
        sql.append(" LEFT JOIN contratos c ON c.idcontrato = sc.idcontrato ");
        sql.append(" LEFT JOIN servico s ON s.idservico = sc.idservico ");
        sql.append(" LEFT JOIN tipodemandaservico td ON td.idtipodemandaservico = s.idtipodemandaservico ");
        sql.append(" LEFT JOIN empregados e1 ON e1.idempregado = sol.idsolicitante ");
        sql.append(" LEFT JOIN unidade u1 ON u1.idunidade = e1.idunidade ");
        sql.append(" LEFT JOIN usuario usu ON usu.idusuario = sol.idresponsavel ");
        sql.append(" LEFT JOIN empregados e2 ON e2.idempregado = usu.idempregado ");
        sql.append(" LEFT JOIN unidade u2 ON u2.idunidade = e2.idunidade ");
        sql.append(" LEFT JOIN origematendimento oa ON oa.idorigem = sol.idorigem ");
        sql.append(" LEFT JOIN prioridade p ON p.idprioridade = sol.idprioridade ");
        sql.append(" LEFT JOIN faseservico fs ON fs.idfase = sol.idfaseatual ");
        sql.append(" LEFT JOIN grupo g1 ON g1.idgrupo = sol.idgrupoatual ");
        sql.append(" LEFT JOIN grupo g2 ON g2.idgrupo = sol.idgruponivel1 ");
        sql.append(" LEFT JOIN contatosolicitacaoservico cs ON cs.idcontatosolicitacaoservico = sol.idcontatosolicitacaoservico ");
        sql.append(" LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");

        sql.append(" INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append(" WHERE sol.idsolicitacaopai is null ");

        if (listTarefa != null && !listTarefa.isEmpty()) {
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("and es.idInstanciaFluxo in ( select * from table(sys.odcinumberlist( ");
            } else {
                sql.append("and es.idInstanciaFluxo in  ( ");
            }

            int size = listTarefa.size();
            int max = 1;
            int aux = 0;
            int CONSTANT = 999;
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdInstancia());

                    if (aux == size) {
                        sql.append(")))");
                    } else if (aux > (CONSTANT * (max - 1)) && aux <= (CONSTANT * max)) {
                        sql.append("))  union  select * from table(sys.odcinumberlist( ");
                        max++;
                    } else {
                        sql.append(",");
                    }
                }
            } else {
                for (TarefaFluxoDTO tarefaFluxoDto : listTarefa) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdInstancia());
                    if (aux == size) {
                        sql.append(")");
                    } else {
                        sql.append(",");
                    }
                }

            }

        } else {
            return null;
        }

        Long totalLinhaLong = 0l;
        Long totalPagina = 0l;
        Integer total = 0;
        BigDecimal totalLinhaBigDecimal;
        Integer totalLinhaInteger;
        int intLimite = itensPorPagina;
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), null);
        if (lista != null) {
            Object[] totalLinha = (Object[]) lista.get(0);
            if (totalLinha != null && totalLinha.length > 0) {
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
            if (totalLinhaLong % intLimite != 0) {
                totalPagina = totalPagina + 1;
            }
        }
        total = Integer.valueOf(totalPagina.toString());
        return total;
    }

    /**
     * Retorna o Total de Páginas (Quantidade Total de Solicitações dividido pela QTDE de Itens por Página) de acordo com as Tarefas e os Contratos do Usuário Logado.
     *
     * @param itensPorPagina
     * @param listTarefa
     * @param gerenciamentoServicosDTO
     * @return Integer - Número Total de Páginas
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 05.11.2013
     */
    public Integer totalDePaginas(Integer itensPorPagina, Collection<TarefaFluxoDTO> listTarefa, GerenciamentoServicosDTO gerenciamentoServicosDTO, Collection<ContratoDTO> listContratoUsuarioLogado)
            throws Exception {

        StringBuilder sql = new StringBuilder();
        List parametros = new ArrayList();

        sql.append(" SELECT COUNT(*) ");
        sql.append(" FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");
        sql.append("        INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("        INNER JOIN bpm_itemtrabalhofluxo it ON it.idinstancia = es.idinstanciafluxo ");
        sql.append(" WHERE sol.idsolicitacaopai is null ");

        List<TarefaFluxoDTO> listaFiltrada = filtrarElementosDaLista(listTarefa, gerenciamentoServicosDTO);
        int size = listaFiltrada.size();

        if (listaFiltrada != null && size != 0 && listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                // O oracle possui uma restrição de apenas 1000 registros na função IN por isso foi necessário realizar um UNION a cada 1000 registros. Realizado por Flávio.
                sql.append(" AND it.iditemtrabalho IN ( select * from table(sys.odcinumberlist( ");
            } else {
                sql.append(" AND it.iditemtrabalho IN  ( ");
            }

            int max = 1;
            int aux = 0;
            int CONSTANT = 999;

            // Hack oracle
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                for (TarefaFluxoDTO tarefaFluxoDto : listaFiltrada) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdItemTrabalho());

                    if (aux == size) {
                        sql.append(")))");
                    } else if (aux > (CONSTANT * (max - 1)) && aux <= (CONSTANT * max)) {
                        sql.append("))  UNION  SELECT * from table(sys.odcinumberlist( ");
                        max++;
                    } else {
                        sql.append(",");
                    }
                }
            } else {
                for (TarefaFluxoDTO tarefaFluxoDto : listaFiltrada) {
                    aux += 1;
                    sql.append(tarefaFluxoDto.getIdItemTrabalho());
                    if (aux == size) {
                        sql.append(")");
                    } else {
                        sql.append(",");
                    }
                }

            }

            // FILTRA CONTRATO DO USUÁRIO LOGADO - Só retorna as Solicitações dos Contratos em que o usuário logado está inserido.
            if (listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
                sql.append(" AND c.idcontrato in ( ");
                boolean a = true;
                for (ContratoDTO contrato : listContratoUsuarioLogado) {
                    if (a) {
                        sql.append(contrato.getIdContrato());
                        a = false;
                    } else {
                        sql.append(",");
                        sql.append(contrato.getIdContrato());
                    }
                }
                sql.append(" )");
            }
        } else {
            return 0;
        }

        // Adiciona o filtro de pesquisa caso houver filtro
        this.adicionarFiltroPesquisa(sql, gerenciamentoServicosDTO, parametros);

        Long totalLinhaLong = 0l;
        Long totalPagina = 0l;
        Integer total = 0;
        BigDecimal totalLinhaBigDecimal;
        Integer totalLinhaInteger;
        int intLimite = itensPorPagina;
        List lista = new ArrayList();

        lista = this.execSQL(sql.toString(), parametros.toArray());

        if (lista != null) {
            Object[] totalLinha = (Object[]) lista.get(0);
            if (totalLinha != null && totalLinha.length > 0) {
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
            if (totalLinhaLong % intLimite != 0) {
                totalPagina = totalPagina + 1;
            }
        }

        total = Integer.valueOf(totalPagina.toString());

        return total;
    }

    private Timestamp transformaHoraFinal(Date data) throws ParseException {
        String dataHora = data + " 23:59:59";
        String pattern = "yyyy-MM-dd hh:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        java.util.Date d = sdf.parse(dataHora);
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
        return sqlDate;
    }

    @Override
    public void updateNotNull(IDto obj) throws PersistenceException {
        super.updateNotNull(obj);
    }


    /**
     * Total de página para o portal
     * @param itensPorPagina
     * @param listTarefa
     * @return
     * @throws Exception
     */
    public Integer totalDePaginasPortal(Integer itensPorPagina, Collection<TarefaFluxoDTO> listTarefa, GerenciamentoServicosDTO gerenciamentoServicosDTO, Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {
        StringBuilder sql = new StringBuilder();
        List parametros = new ArrayList();

        sql.append(" SELECT COUNT(*) ");
        sql.append(" FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");

        sql.append(" INNER JOIN execucaosolicitacao es ON es.idsolicitacaoservico = sol.idsolicitacaoservico ");
        sql.append("WHERE sol.idsolicitacaopai is null ");

        if (listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
            // FILTRA CONTRATO DO USUÁRIO LOGADO - Só retorna as Solicitações dos Contratos em que o usuário logado está inserido.
            if (listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
                sql.append(" AND c.idcontrato in ( ");
                boolean verifica = true;
                for (ContratoDTO contrato : listContratoUsuarioLogado) {
                    if (verifica) {
                        sql.append(contrato.getIdContrato());
                        verifica = false;
                    } else {
                        sql.append(",");
                        sql.append(contrato.getIdContrato());
                    }
                }
                sql.append(" )");
            }
        }

        // Adiciona o filtro de pesquisa caso houver filtro
        adicionarFiltroPesquisa(sql, gerenciamentoServicosDTO, parametros);


        Long totalLinhaLong = 0l;
        Long totalPagina = 0l;
        Integer total = 0;
        BigDecimal totalLinhaBigDecimal;
        Integer totalLinhaInteger;
        int intLimite = itensPorPagina;
        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametros.toArray());
        if (lista != null) {
            Object[] totalLinha = (Object[]) lista.get(0);
            if (totalLinha != null && totalLinha.length > 0) {
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
            if (totalLinhaLong % intLimite != 0) {
                totalPagina = totalPagina + 1;
            }
        }
        total = Integer.valueOf(totalPagina.toString());
        return total;
    }




    /**
     * Seta uma solicitação como filha de outra.
     *
     * @param solicitacaoDto
     * @param condicoes
     * @author breno.guimaraes
     */
    public void updateSolicitacaoPai(Integer idSolicitacaoServicoFilha, Integer idSolicitacaoServicoPai) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + getTableName() + " SET idsolicitacaopai = ? WHERE (idsolicitacaoservico = ?)");
        Object[] params = { idSolicitacaoServicoPai, idSolicitacaoServicoFilha };
        try {
            this.execUpdate(sql.toString(), params);
        } catch (PersistenceException e) {
            System.out.println("Problemas com atualização da solicitacaoServico.");
            e.printStackTrace();
        }
    }

    public boolean validaQuantidadeRetorno(RelatorioQuantitativoRetornoDTO relatorioQuantitativoRetornoDTO) throws Exception {
        List parametro = new ArrayList();
        StringBuilder sb = new StringBuilder();

        sb.append("select count(idelemento) as total ");
        sb.append("from bpm_itemtrabalhofluxo ");
        sb.append("where idinstancia = ?  ");
        sb.append("and idelemento = ? ");
        sb.append("group by idelemento ");

        parametro.add(relatorioQuantitativoRetornoDTO.getIdInstancia());
        parametro.add(relatorioQuantitativoRetornoDTO.getIdElemento());

        List listaDados = this.execSQL(sb.toString(), parametro.toArray());
        Integer total = 0;
        if (listaDados != null) {
            Object[] row = (Object[]) listaDados.get(0);
            total = Integer.parseInt(row[0].toString());
        }
        if (total == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarExistenciaDeUnidade(Integer idUnidade) throws Exception {
        Object[] objs = new Object[] { idUnidade };
        String sql = "SELECT distinct idunidade FROM " + getTableName() + " WHERE idunidade = ?  ";
        List lista = this.execSQL(sql, objs);

        List listRetorno = new ArrayList();
        listRetorno.add("idUnidade");
        if (lista != null && !lista.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Collection<RelatorioEficaciaTesteDTO> listaSolicitacaoPorServicosAbertosNoPerido(Date dataIncio, Date dataFim, List<ServicoDTO> listaServicos) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT DISTINCT  solicitacaoservico.idsolicitacaoservico as numeroSolicitacao,servico.nomeServico as nomeServico,empregados.nome as solicitante,solicitacaoservico.datahorasolicitacao as aberturaSolicitacao,solicitacaoservico.situacao as situacao");
        sql.append(" FROM solicitacaoservico solicitacaoservico INNER JOIN servicocontrato ON solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
        sql.append(" AND solicitacaoservico.situacao <> 'Cancelada' AND servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato INNER JOIN tipodemandaservico ON tipodemandaservico.idtipodemandaservico = solicitacaoservico.idtipodemandaservico");
        sql.append(" INNER JOIN servico ON servicocontrato.idservico IN ( ");
        for (int i = 0; i<listaServicos.size();i++) {
            if( i != listaServicos.size()-1) {
                sql.append(listaServicos.get(i).getIdServico()+",");
            } else {
                sql.append(listaServicos.get(i).getIdServico());
            }
        }

        sql.append(" ) AND servicocontrato.idservico = servico.idservico INNER JOIN empregados ON solicitacaoservico.idsolicitante = empregados.idempregado ORDER BY servico.nomeServico, solicitacaoservico.idsolicitacaoservico");
        parametro.add(dataIncio);
        Timestamp dtfim = this.transformaHoraFinal(dataFim);
        parametro.add(dtfim);

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("numeroSolicitacao");
        listRetorno.add("nomeServico");
        listRetorno.add("solicitante");
        listRetorno.add("aberturaSolicitacao");
        listRetorno.add("situacao");


        if (list != null && !list.isEmpty()) {
            return  this.listConvertion(RelatorioEficaciaTesteDTO.class, list, listRetorno);
        } else {
            return null;
        }
    }

    public Collection<RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO> listaQtdSolicitacoesCanceladasFinalizadasporServicoNoPeriodo(Date dataIncio, Date dataFim, List<ServicoDTO> listaServicos) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        String servicos = "";
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        for (int i = 0; i<listaServicos.size();i++) {
            if( i != listaServicos.size()-1) {
                servicos += listaServicos.get(i).getIdServico()+",";
            } else {
                servicos += listaServicos.get(i).getIdServico().toString();
            }
        }
        sql.append("SELECT DISTINCT	servico.nomeServico as nomeServico,TotalAberto as totalAberto,qtdeSoliciatacoesCanceladasFinalizadas as qtdeSoliciatacoesCanceladasFinalizadas  FROM servico ");

        sql.append("JOIN (SELECT DISTINCT idservico ,COUNT(idservico) as TotalAberto	FROM solicitacaoservico solicitacaoservico JOIN servicocontrato ON solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? AND  ");
        sql.append("servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato AND servicocontrato.idservico IN ("+servicos+")GROUP BY idservico) abertas on servico.idservico = abertas.idservico ");

        sql.append("left JOIN (SELECT DISTINCT idservico, COUNT(idservico) as qtdeSoliciatacoesCanceladasFinalizadas FROM solicitacaoservico solicitacaoservico JOIN servicocontrato ON solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? AND  ");
        sql.append("servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato AND servicocontrato.idservico IN ("+servicos+") and (solicitacaoservico.situacao ='Cancelada' or solicitacaoservico.situacao = 'Fechada' ) ");
        sql.append("GROUP BY idservico) fechadasFinalizadas on servico.idservico = fechadasFinalizadas.idservico ORDER BY servico.nomeServico ");

        Timestamp dtfim = this.transformaHoraFinal(dataFim);
        parametro.add(dataIncio);
        parametro.add(dtfim);
        parametro.add(dataIncio);
        parametro.add(dtfim);


        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("nomeServico");
        listRetorno.add("totalAberto");
        listRetorno.add("qtdeSoliciatacoesCanceladasFinalizadas");


        if (list != null && !list.isEmpty()) {
            return  this.listConvertion(RelatorioDocumentacaoDeFuncionalidadesNovasOuAlteradasNoPeriodoDTO.class, list, listRetorno);
        } else {
            return null;
        }
    }

    /**
     * @param relatorioIncidentesNaoResolvidosDTO
     * @return
     * @throws Exception
     * @author bruno.aquino
     */
    public Collection<SolicitacaoServicoDTO> findSolicitacoesNaoResolvidasNoPrazoKPI(RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select ss.idsolicitacaoservico, t.nometipodemandaservico, ss.idusuarioresponsavelatual, u.nome as responsavelAtual, ");
        sql.append("e.nome as nomeSolicitante, g.nome as nomeGrupo, ");
        sql.append("ss.idsolicitante, ss.situacao, servico.nomeservico, datahorasolicitacao, c.numero as nomeContrato ");
        sql.append("from solicitacaoservico ss ");
        sql.append("inner join servicocontrato sc on ss.idservicocontrato = sc.idservicocontrato ");
        sql.append("inner join servico servico on servico.idservico = sc.idservico ");
        sql.append("inner join contratos c on c.idcontrato = sc.idcontrato ");
        sql.append("inner join tipodemandaservico t on t.idtipodemandaservico = ss.idtipodemandaservico ");

        sql.append("left join usuario u on u.idusuario = ss.idusuarioresponsavelatual ");
        sql.append("inner join empregados e on e.idempregado = ss.idsolicitante ");
        sql.append("inner join grupo g on ss.idgrupoatual = g.idgrupo ");
        sql.append("WHERE UPPER(ss.situacao) NOT IN (UPPER('Fechada'), UPPER('Cancelada'),UPPER('Resolvida')) ");
        sql.append("and datahorasolicitacao < ? ");

        parametro.add(relatorioIncidentesNaoResolvidosDTO.getPeriodoReferencia());

        if (relatorioIncidentesNaoResolvidosDTO.getListaServicos() != null && !relatorioIncidentesNaoResolvidosDTO.getListaServicos().isEmpty()) {

            int aux = 1;
            String[] listaServicosTela;
            listaServicosTela = relatorioIncidentesNaoResolvidosDTO.getListaServicos().split(";");
            if (listaServicosTela != null && listaServicosTela.length > 0) {
                sql.append("and ( ");
                for (String i : listaServicosTela) {
                    i = StringUtils.deleteWhitespace(i);
                    if (StringUtils.isNotBlank(i)) {
                        if (listaServicosTela.length > aux) {
                            sql.append(" sc.idservico = ? or ");
                            parametro.add(Integer.parseInt(i));
                        } else {
                            sql.append(" sc.idservico = ? ");
                            parametro.add(Integer.parseInt(i));
                        }
                    }
                    aux++;
                }
                sql.append(" ) ");
            }
        }

        if (relatorioIncidentesNaoResolvidosDTO.getListaGrupos() != null && !relatorioIncidentesNaoResolvidosDTO.getListaGrupos().isEmpty()) {

            int aux = 1;
            String[] listaGrupoTela;
            listaGrupoTela = relatorioIncidentesNaoResolvidosDTO.getListaGrupos().split(";");
            if (listaGrupoTela != null && listaGrupoTela.length > 0) {
                sql.append("and ( ");
                for (String i : listaGrupoTela) {
                    i = StringUtils.deleteWhitespace(i);
                    if (StringUtils.isNotBlank(i)) {
                        if (listaGrupoTela.length > aux) {
                            sql.append(" ss.idgrupoatual = ? or ");
                            parametro.add(Integer.parseInt(i));
                        } else {
                            sql.append(" ss.idgrupoatual = ? ");
                            parametro.add(Integer.parseInt(i));
                        }
                    }
                    aux++;
                }
                sql.append(" ) ");
            }
        }

        if (relatorioIncidentesNaoResolvidosDTO.getIdContrato() != null) {
            sql.append("and sc.idcontrato = ? ");
            parametro.add(relatorioIncidentesNaoResolvidosDTO.getIdContrato());
        }

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("idUsuarioResponsavelAtual");
        listRetorno.add("responsavelAtual");
        listRetorno.add("nomeSolicitante");
        listRetorno.add("nomeGrupo");
        listRetorno.add("idSolicitante");
        listRetorno.add("situacao");
        listRetorno.add("nomeServico");
        listRetorno.add("DataHoraSolicitacao");
        listRetorno.add("nomeContrato");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }

    /**
     * @param relatorioIncidentesNaoResolvidosDTO
     * @return
     * @throws Exception
     * @author bruno.aquino
     */
    public Collection<SolicitacaoServicoDTO> findSolicitacoesNaoResolvidasEntrePrazoKPI(RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select ss.idsolicitacaoservico, t.nometipodemandaservico, ss.idusuarioresponsavelatual, u.nome as responsavelAtual, ");
        sql.append("e.nome as nomeSolicitante, g.nome as nomeGrupo, ");
        sql.append("ss.idsolicitante, ss.situacao, servico.nomeservico, datahorasolicitacao, c.numero as nomeContrato ");
        sql.append("from solicitacaoservico ss ");
        sql.append("inner join servicocontrato sc on ss.idservicocontrato = sc.idservicocontrato ");
        sql.append("inner join servico servico on servico.idservico = sc.idservico ");
        sql.append("inner join contratos c on c.idcontrato = sc.idcontrato ");
        sql.append("inner join tipodemandaservico t on t.idtipodemandaservico = ss.idtipodemandaservico ");

        sql.append("left join usuario u on u.idusuario = ss.idusuarioresponsavelatual ");
        sql.append("inner join empregados e on e.idempregado = ss.idsolicitante ");
        sql.append("inner join grupo g on ss.idgrupoatual = g.idgrupo ");
        sql.append("WHERE UPPER(ss.situacao) NOT IN (UPPER('Fechada'), UPPER('Cancelada'),UPPER('Resolvida')) ");
        sql.append("and datahorasolicitacao BETWEEN ? AND ? ");

        parametro.add(relatorioIncidentesNaoResolvidosDTO.getPeriodoReferencia());
        parametro.add(relatorioIncidentesNaoResolvidosDTO.getDataReferencia());

        if (relatorioIncidentesNaoResolvidosDTO.getListaServicos() != null && !relatorioIncidentesNaoResolvidosDTO.getListaServicos().isEmpty()) {

            int aux = 1;
            String[] listaServicosTela;
            listaServicosTela = relatorioIncidentesNaoResolvidosDTO.getListaServicos().split(";");
            if (listaServicosTela != null && listaServicosTela.length > 0) {
                sql.append("and ( ");
                for (String i : listaServicosTela) {
                    i = StringUtils.deleteWhitespace(i);
                    if (StringUtils.isNotBlank(i)) {
                        if (listaServicosTela.length > aux) {
                            sql.append(" sc.idservico = ? or ");
                            parametro.add(Integer.parseInt(i));
                        } else {
                            sql.append(" sc.idservico = ? ");
                            parametro.add(Integer.parseInt(i));
                        }
                    }
                    aux++;
                }
                sql.append(" ) ");
            }
        }

        if (relatorioIncidentesNaoResolvidosDTO.getListaGrupos() != null && !relatorioIncidentesNaoResolvidosDTO.getListaGrupos().isEmpty()) {

            int aux = 1;
            String[] listaGrupoTela;
            listaGrupoTela = relatorioIncidentesNaoResolvidosDTO.getListaGrupos().split(";");
            if (listaGrupoTela != null && listaGrupoTela.length > 0) {
                sql.append("and ( ");
                for (String i : listaGrupoTela) {
                    i = StringUtils.deleteWhitespace(i);
                    if (StringUtils.isNotBlank(i)) {
                        if (listaGrupoTela.length > aux) {
                            sql.append(" ss.idgrupoatual = ? or ");
                            parametro.add(Integer.parseInt(i));
                        } else {
                            sql.append(" ss.idgrupoatual = ? ");
                            parametro.add(Integer.parseInt(i));
                        }
                    }
                    aux++;
                }
                sql.append(" ) ");
            }
        }

        if (relatorioIncidentesNaoResolvidosDTO.getIdContrato() != null) {
            sql.append("and sc.idcontrato = ? ");
            parametro.add(relatorioIncidentesNaoResolvidosDTO.getIdContrato());
        }

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("idUsuarioResponsavelAtual");
        listRetorno.add("responsavelAtual");
        listRetorno.add("nomeSolicitante");
        listRetorno.add("nomeGrupo");
        listRetorno.add("idSolicitante");
        listRetorno.add("situacao");
        listRetorno.add("nomeServico");
        listRetorno.add("DataHoraSolicitacao");
        listRetorno.add("nomeContrato");

        if (list != null && !list.isEmpty()) {
            return this.listConvertion(getBean(), list, listRetorno);
        } else {
            return null;
        }
    }


    /**
     * lista com os quantitativos por empregado de solicitações serviços emcaminhadas e foram concluidas com exito.
     * @param relatorioKpiProdutividadeDto
     * @return
     * @throws Exception
     * @author thays.araujo
     *
     */
    public Collection<RelatorioKpiProdutividadeDTO> listaQuantitativaEmpregadoSolicitacoesEmcaminhaExito(RelatorioKpiProdutividadeDTO relatorioKpiProdutividadeDto) throws Exception{

        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();

        sql.append("SELECT empregados.idempregado, ");
        sql.append("       empregados.nome, ");
        sql.append("       qtdeencaminhadas, ");
        sql.append("       qtdeexito ");
        sql.append("FROM   ( (SELECT idempregado, ");
        sql.append("               nome ");
        sql.append("        FROM   empregados ");

        if(relatorioKpiProdutividadeDto.getListaEmpregado() != null){
            sql.append("where idempregado in ( ");
            for(Iterator<EmpregadoDTO> i = relatorioKpiProdutividadeDto.getListaEmpregado().iterator(); i.hasNext();){
                EmpregadoDTO empregadoDto = i.next();
                if(!i.hasNext()){
                    sql.append("  ? ");
                    parametro.add(empregadoDto.getIdEmpregado());
                }else{
                    sql.append("  ?, ");
                    parametro.add(empregadoDto.getIdEmpregado());
                }

            }
        }

        sql.append(") ");
        sql.append(")  empregados ");
        sql.append("         LEFT JOIN (SELECT idempregado, ");
        sql.append("                           count(idsolicitacaoservico)  qtdeencaminhadas ");
        sql.append("                    FROM   ( (SELECT idempregado ");
        sql.append("                            FROM   empregados ");

        if(relatorioKpiProdutividadeDto.getListaEmpregado() != null){
            sql.append("where idempregado  in ( ");
            for(Iterator<EmpregadoDTO> i = relatorioKpiProdutividadeDto.getListaEmpregado().iterator(); i.hasNext();){
                EmpregadoDTO empregadoDto = i.next();
                if (!i.hasNext()) {
                    sql.append("  ? ");
                    parametro.add(empregadoDto.getIdEmpregado());
                }else{
                    sql.append("  ?, ");
                    parametro.add(empregadoDto.getIdEmpregado());
                }

            }
        }

        sql.append(") ");
        sql.append(")  e ");
        sql.append("                             LEFT JOIN (SELECT ss.idsolicitante, ");
        sql.append("                                               ss.idsolicitacaoservico ");
        sql.append("                                        FROM   servicocontrato  sc ");
        sql.append("                                               JOIN solicitacaoservico  ss ");
        sql.append("                                                 ON sc.idcontrato = ? ");
        parametro.add(relatorioKpiProdutividadeDto.getIdContrato());
        if(relatorioKpiProdutividadeDto.getListaServicos()!=null){
            sql.append("                                                    AND ( sc.idservico IN ( ");
            for(Iterator<ServicoDTO> i = relatorioKpiProdutividadeDto.getListaServicos().iterator(); i.hasNext();){
                ServicoDTO servicoDto = i.next();
                if (!i.hasNext()) {
                    sql.append("  ? ");
                    parametro.add(servicoDto.getIdServico());
                }else{
                    sql.append("  ?, ");
                    parametro.add(servicoDto.getIdServico());
                }

            }

            sql.append(") )");
        }

        sql.append("                                                    AND sc.idservicocontrato = ");
        sql.append("                                                        ss.idservicocontrato ");
        if(relatorioKpiProdutividadeDto.getListaEmpregado() != null){
            sql.append("                                                    AND ( ss.idsolicitante IN (");
            for(Iterator<EmpregadoDTO> i = relatorioKpiProdutividadeDto.getListaEmpregado().iterator(); i.hasNext();){
                EmpregadoDTO empregadoDto = i.next();
                if (!i.hasNext()) {
                    sql.append("  ? ");
                    parametro.add(empregadoDto.getIdEmpregado());
                }else{
                    sql.append("  ?, ");
                    parametro.add(empregadoDto.getIdEmpregado());
                }

            }
            sql.append(") ");
        }

        sql.append(")  ");
        sql.append("                                                    AND ( ( ");
        sql.append("                                               ss.situacao = 'Fechada' ) ");
        sql.append("                                                           OR ");
        sql.append("( ss.situacao = 'Cancelada' ) ) ");
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("AND ((ss.datahorasolicitacao) BETWEEN to_date(?,'YYYY-MM-DD' ) AND to_date(?,'YYYY-MM-DD hh24:mi:ss' ) )");
            parametro.add(relatorioKpiProdutividadeDto.getDataInicio());
            parametro.add(relatorioKpiProdutividadeDto.getDataFim() +" 23:59:59");
        } else{
            sql.append("AND ( ss.datahorasolicitacao ");
            sql.append("      BETWEEN ");
            sql.append("      ? AND ");
            sql.append("      ? ");
            sql.append("    ) ");
            parametro.add(relatorioKpiProdutividadeDto.getDataInicio());
            parametro.add(transformaHoraFinal(relatorioKpiProdutividadeDto.getDataFim()));
        }

        if(!relatorioKpiProdutividadeDto.getListaCausaIncidentes().isEmpty()){
            sql.append("AND ( ( ss.idcausaincidente NOT IN ");
            sql.append("        ( ");
            for(Iterator<CausaIncidenteDTO> i = relatorioKpiProdutividadeDto.getListaCausaIncidentes().iterator(); i.hasNext();){
                CausaIncidenteDTO causaIncidenteDto = i.next();
                if (!i.hasNext()) {
                    sql.append("  ? ");
                    parametro.add(causaIncidenteDto.getIdCausaIncidente());
                }else{
                    sql.append("  ?, ");
                    parametro.add(causaIncidenteDto.getIdCausaIncidente());
                }

            }

            sql.append(" ))or(ss.idcausaincidente is null)) ");
        }
        sql.append(") qten ");
        sql.append("ON idempregado = idsolicitante ) ");
        sql.append("GROUP  BY idempregado) c ");
        sql.append("ON empregados.idempregado = c.idempregado ");
        sql.append("LEFT JOIN (SELECT idempregado, ");
        sql.append("count(idsolicitacaoservico)  qtdeexito ");
        sql.append("FROM   ( (SELECT idempregado ");
        sql.append("FROM   empregados ");
        if(relatorioKpiProdutividadeDto.getListaEmpregado() != null){
            sql.append("WHERE  idempregado IN (  ");
            for(Iterator<EmpregadoDTO> i = relatorioKpiProdutividadeDto.getListaEmpregado().iterator(); i.hasNext();){
                EmpregadoDTO empregadoDto = i.next();
                if (!i.hasNext()) {
                    sql.append("  ? ");
                    parametro.add(empregadoDto.getIdEmpregado());
                }else{
                    sql.append("  ?, ");
                    parametro.add(empregadoDto.getIdEmpregado());
                }

            }
            sql.append(") ");
        }

        sql.append(")  e ");
        sql.append("LEFT JOIN (SELECT ss.idsolicitante, ");
        sql.append("ss.idsolicitacaoservico ");
        sql.append("FROM   servicocontrato  sc ");
        sql.append("JOIN solicitacaoservico  ss ");
        sql.append("ON sc.idcontrato = ? ");
        parametro.add(relatorioKpiProdutividadeDto.getIdContrato());
        if(relatorioKpiProdutividadeDto.getListaServicos()!=null){
            sql.append("AND ( sc.idservico IN ( ");
            for(Iterator<ServicoDTO> i = relatorioKpiProdutividadeDto.getListaServicos().iterator(); i.hasNext();){
                ServicoDTO servicoDto = i.next();
                if (!i.hasNext()) {
                    sql.append("  ? ");
                    parametro.add(servicoDto.getIdServico());
                }else{
                    sql.append("  ?, ");
                    parametro.add(servicoDto.getIdServico());
                }

            }
            sql.append("))");
        }
        sql.append("AND sc.idservicocontrato = ");
        sql.append("    ss.idservicocontrato ");
        if(relatorioKpiProdutividadeDto.getListaEmpregado() != null){
            sql.append("AND ( ss.idsolicitante IN ( ");
            for(Iterator<EmpregadoDTO> i = relatorioKpiProdutividadeDto.getListaEmpregado().iterator(); i.hasNext();){
                EmpregadoDTO empregadoDto = i.next();
                if (!i.hasNext()) {
                    sql.append("  ? ");
                    parametro.add(empregadoDto.getIdEmpregado());
                }else{
                    sql.append("  ?, ");
                    parametro.add(empregadoDto.getIdEmpregado());
                }

            }
            sql.append("))");
        }

        sql.append("AND ( ss.situacao = 'Fechada' ) ");
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            sql.append("AND ((ss.datahorasolicitacao) BETWEEN to_date(?,'YYYY-MM-DD' ) AND to_date(?,'YYYY-MM-DD hh24:mi:ss' ) )");
            parametro.add(relatorioKpiProdutividadeDto.getDataInicio());
            parametro.add(relatorioKpiProdutividadeDto.getDataFim() +" 23:59:59");

        } else{
            sql.append("AND ( ss.datahorasolicitacao ");
            sql.append("      BETWEEN ");
            sql.append("      ? AND ");
            sql.append("      ? ");
            sql.append("    ) ");
            parametro.add(relatorioKpiProdutividadeDto.getDataInicio());
            parametro.add(transformaHoraFinal(relatorioKpiProdutividadeDto.getDataFim()));

        }

        if(!relatorioKpiProdutividadeDto.getListaCausaIncidentes().isEmpty()){
            sql.append("AND ( ( ss.idcausaincidente NOT IN (");
            for(Iterator<CausaIncidenteDTO> i = relatorioKpiProdutividadeDto.getListaCausaIncidentes().iterator(); i.hasNext();){
                CausaIncidenteDTO causaIncidenteDto = i.next();
                if (!i.hasNext()) {
                    sql.append("  ? ");
                    parametro.add(causaIncidenteDto.getIdCausaIncidente());
                }else{
                    sql.append("  ?, ");
                    parametro.add(causaIncidenteDto.getIdCausaIncidente());
                }

            }
            sql.append("))");
            sql.append("       OR ( ss.idcausaincidente IS ");
            sql.append("            NULL )) ");
        }

        sql.append(") qtex ");
        sql.append("ON idempregado = idsolicitante ) ");
        sql.append("GROUP  BY idempregado) x ");
        sql.append("ON empregados.idempregado = x.idempregado ) ");
        sql.append("ORDER  BY nome");

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idEmpregado");
        listRetorno.add("funcionario");
        listRetorno.add("qtdeencaminhadas");
        listRetorno.add("qtdeexito");


        if (list != null && !list.isEmpty()) {
            return  this.listConvertion(RelatorioKpiProdutividadeDTO.class, list, listRetorno);
        }
        return null;
    }

    public Collection<SolicitacaoServicoDTO> listSolicitacoesFilhasFiltradas(GerenciamentoServicosDTO gerenciamentoBean, Collection<ContratoDTO> listContratoUsuarioLogado) throws Exception {
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
        listRetorno.add("prazoCapturaHH");
        listRetorno.add("prazoCapturaMM");
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
        listRetorno.add("idUltimaAprovacao");
        listRetorno.add("dataHoraInicioSLA");
        listRetorno.add("dataHoraSuspensaoSLA");
        listRetorno.add("dataHoraReativacaoSLA");
        listRetorno.add("situacaoSLA");
        listRetorno.add("aprovacao");
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
        listRetorno.add("vencendo");

        StringBuilder sql = new StringBuilder();
        List parametros = new ArrayList();

        sql.append("SELECT sol.idSolicitacaoServico, sol.idbaseconhecimento, sol.idServicoContrato, sol.idSolicitante, ");
        sql.append("       sol.idItemConfiguracao, sol.idItemConfiguracaoFilho, sol.idtipodemandaservico, sol.idcontatosolicitacaoservico, ");
        sql.append("       sol.idOrigem, sol.idResponsavel, sol.idTipoProblema, sol.idPrioridade, sol.idUnidade, sol.idFaseAtual, ");
        sql.append("       sol.idGrupoAtual, sol.dataHoraSolicitacao, sol.dataHoraLimite, sol.atendimentoPresencial, sol.prazoCapturaHH, sol.prazoCapturaMM, ");
        sql.append("       sol.prazoHH, sol.prazoMM, sol.descricao, sol.resposta, sol.dataHoraInicio, sol.dataHoraFim, sol.situacao, ");
        sql.append("       sol.idSolicitacaoPai, sol.detalhamentoCausa, sol.idCausaIncidente, sol.idCategoriaSolucao, ");
        sql.append("       sol.seqreabertura, sol.enviaEmailCriacao, sol.enviaEmailFinalizacao, sol.enviaEmailAcoes, ");
        sql.append("       sol.idgruponivel1, sol.solucaoTemporaria, sol.houveMudanca, sol.slaACombinar, sol.prazohhAnterior, ");
        sql.append("       sol.prazommAnterior, sol.idCalendario, sol.tempoDecorridoHH, sol.tempoDecorridoMM, sol.dataHoraSuspensao, ");
        sql.append("       sol.dataHoraReativacao, sol.impacto, sol.urgencia, sol.dataHoraCaptura, sol.tempoCapturaHH, sol.tempoCapturaMM, ");
        sql.append("       sol.tempoAtrasoHH, sol.tempoAtrasoMM, sol.tempoAtendimentoHH, sol.tempoAtendimentoMM, sol.idacordonivelservico, ");
        sql.append("       sol.idultimaaprovacao, sol.datahorainiciosla, sol.datahorasuspensaosla, sol.datahorareativacaosla, sol.situacaosla, aprov.aprovacao, ");
        sql.append("       s.idservico, s.nomeServico, td.idTipoDemandaServico, td.nomeTipoDemandaServico, c.idContrato, c.numero, e1.nome, u1.nome, ");
        sql.append("       e2.nome, u2.nome, oa.descricao, p.nomeprioridade, fs.nomefase,  ");
        sql.append("       g1.sigla, g2.sigla, cs.nomecontato, cs.emailcontato, cs.telefonecontato, cs.localizacaofisica ,cs.idlocalidade , sol.vencendo ");
        sql.append("  FROM solicitacaoservico sol ");
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
        sql.append("        LEFT JOIN aprovacaosolicitacaoservico aprov ON aprov.idaprovacaosolicitacaoservico = sol.idultimaaprovacao ");
        sql.append(" WHERE sol.idsolicitacaopai IS NOT NULL ");

        // FILTRA CONTRATO DO USUÁRIO LOGADO - Só retorna as Solicitações dos Contratos em que o usuário logado está inserido.
        if (listContratoUsuarioLogado != null && !listContratoUsuarioLogado.isEmpty()) {
            sql.append(" AND c.idcontrato in ( ");
            boolean verifica = true;
            for (ContratoDTO contrato : listContratoUsuarioLogado) {
                if (verifica) {
                    sql.append(contrato.getIdContrato());
                    verifica = false;
                } else {
                    sql.append(",");
                    sql.append(contrato.getIdContrato());
                }
            }
            sql.append(" )");
        }

        // Adiciona o filtro de pesquisa caso houver filtro
        adicionarFiltroPesquisa(sql, gerenciamentoBean, parametros);

        List lista = new ArrayList();
        lista = this.execSQL(sql.toString(), parametros.toArray());

        if (lista != null && !lista.isEmpty()) {
            return engine.listConvertion(SolicitacaoServicoDTO.class, lista, listRetorno);
        } else {
            return null;
        }
    }

    public List<SolicitacaoServicoDTO> listaSolicitacoesRelacionadasBaseconhecimento(Integer idBaseconhecimento)  throws Exception {
        List parametro = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("select con.idsolicitacaoservico");
        sb.append(" from conhecimentosolicitacaoservico con");
        sb.append(" inner join baseconhecimento bas on bas.idbaseconhecimento = con.idbaseconhecimento");
        sb.append(" where bas.idbaseconhecimento = ?");

        parametro.add(idBaseconhecimento);
        List listRetorno = new ArrayList();
        listRetorno.add("idSolicitacaoServico");

        List list = this.execSQL(sb.toString(), parametro.toArray());
        if (list != null && !list.isEmpty()) {
            return this.listConvertion(SolicitacaoServicoDTO.class, list, listRetorno);
        } else {
            return null;
        }
    }

    public boolean verificaPermGestorSolicitanteRH(Integer idSolicitante) throws PersistenceException {
        List parametro = new ArrayList();
        List list = new ArrayList();
        StringBuilder sql = new StringBuilder();
        /*
         * Permite funcionários do mesmo grupo
		sql.append("select distinct empregados.idempregado ");
		sql.append("from alcada join limitealcada on alcada.idalcada = limitealcada.idalcada and alcada.situacao = 'A' ");
		sql.append("join grupo on grupo.idgrupo = limitealcada.idgrupo ");
		sql.append("join gruposempregados on gruposempregados.idgrupo = grupo.idgrupo ");
		sql.append("join empregados on empregados.idempregado = gruposempregados.idempregado ");
		sql.append("where empregados.idempregado = ? and alcada.tipoalcada = 'Pessoal'  order by grupo.nome, empregados.nome");
         */

        //Somente o Responsável poderá solicitar
        sql.append("select distinct alcadacentroresultado.idempregado ");
        sql.append("from alcada join alcadacentroresultado on alcada.tipoalcada = 'Pessoal' and alcada.situacao = 'A' and ");
        sql.append("alcada.idalcada = alcadacentroresultado.idalcada and ");
        sql.append("alcadacentroresultado.idempregado=?");


        parametro.add(idSolicitante);

        list = this.execSQL(sql.toString(), parametro.toArray());

        if (list != null && !list.isEmpty()) {
            return list.size()>0;
        } else {
            return false;
        }
    }

    public Collection<RelatorioCausaSolucaoDTO> listaCausaSolicitacao(RelatorioCausaSolucaoDTO relatorioCausaSolicitacao) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select c.idcausaincidente, descricaocausa, count(idsolicitacaoservico) as numeroSolicitacoes from solicitacaoservico ss ");
        sql.append("left join causaincidente c on c.idcausaincidente = ss.idcausaincidente ");
        sql.append("inner join servicocontrato sc on ss.idservicocontrato = sc.idservicocontrato ");

        if (relatorioCausaSolicitacao.getDataInicio() != null) {
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("where to_char(ss.datahorafim, 'YYYY-MM-DD') BETWEEN ? AND ? ");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                parametro.add(formatter.format(relatorioCausaSolicitacao.getDataInicio()));
                parametro.add(formatter.format(relatorioCausaSolicitacao.getDataFim()));
            } else {
                sql.append("where ss.datahorafim BETWEEN ? AND ? ");
                parametro.add(relatorioCausaSolicitacao.getDataInicio());
                parametro.add(transformaHoraFinal(relatorioCausaSolicitacao.getDataFim()));
            }
        }

        if (relatorioCausaSolicitacao.getIdContrato() != null) {
            sql.append(" and sc.idcontrato = ?  ");
            parametro.add(relatorioCausaSolicitacao.getIdContrato());
        }

        if (relatorioCausaSolicitacao.getIdTipoDemandaServico() != null) {
            sql.append("and ss.idtipodemandaservico = ? ");
            parametro.add(relatorioCausaSolicitacao.getIdTipoDemandaServico());
        }

        if (relatorioCausaSolicitacao.getSituacao() != null && UtilStrings.isNotVazio(relatorioCausaSolicitacao.getSituacao())) {
            sql.append("and ss.situacao = ? ");
            parametro.add(relatorioCausaSolicitacao.getSituacao());
        } else {
            sql.append("and ss.situacao in ('Fechada', 'Cancelada') ");
        }

        if (relatorioCausaSolicitacao.getIdServicos() != null) {
            sql.append("and sc.idservico IN (" + StringUtils.join(relatorioCausaSolicitacao.getIdServicos(), ", ") + ") ");
        }

        if (relatorioCausaSolicitacao.getIdGrupos() != null) {
            sql.append("and ss.idgrupoatual IN (" + StringUtils.join(relatorioCausaSolicitacao.getIdGrupos(), ", ") + ") ");
        }

        if (relatorioCausaSolicitacao.getIdCausas() != null) {
            sql.append("and (" + (relatorioCausaSolicitacao.getExibeSemCausa() != null && relatorioCausaSolicitacao.getExibeSemCausa().equalsIgnoreCase("s") ? "ss.idcausaincidente is NULL OR " : "") + " ss.idcausaincidente IN (" + StringUtils.join(relatorioCausaSolicitacao.getIdCausas(), ", ") + ")) ");
        }

        sql.append("group by c.idcausaincidente, descricaocausa");

        System.out.println(sql + " - " + parametro.toString());

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idCausaIncidente");
        listRetorno.add("descricaoCausa");
        listRetorno.add("numeroSolicitacoes");

        if (list != null && !list.isEmpty()) {
            Collection<RelatorioCausaSolucaoDTO> listaSolicitacaoPorExecutante;
            listaSolicitacaoPorExecutante = this.listConvertion(RelatorioCausaSolucaoDTO.class, list, listRetorno);
            return listaSolicitacaoPorExecutante;
        } else {
            return null;
        }
    }

    public Collection<RelatorioCausaSolucaoDTO> listaSolucaoSolicitacao(RelatorioCausaSolucaoDTO relatorioCausaSolicitacao) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select cs.idcategoriasolucao, descricaocategoriasolucao, count(idsolicitacaoservico) as numeroSolicitacoes from solicitacaoservico ss ");
        sql.append("left join categoriasolucao cs on cs.idcategoriasolucao = ss.idcategoriasolucao ");
        sql.append("inner join servicocontrato sc on ss.idservicocontrato = sc.idservicocontrato ");

        if (relatorioCausaSolicitacao.getDataInicio() != null) {
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("where to_char(ss.datahorafim, 'YYYY-MM-DD') BETWEEN ? AND ? ");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                parametro.add(formatter.format(relatorioCausaSolicitacao.getDataInicio()));
                parametro.add(formatter.format(relatorioCausaSolicitacao.getDataFim()));
            } else {
                sql.append("where ss.datahorafim BETWEEN ? AND ? ");
                parametro.add(relatorioCausaSolicitacao.getDataInicio());
                parametro.add(transformaHoraFinal(relatorioCausaSolicitacao.getDataFim()));
            }
        }

        if (relatorioCausaSolicitacao.getIdContrato() != null) {
            sql.append(" and sc.idcontrato = ?  ");
            parametro.add(relatorioCausaSolicitacao.getIdContrato());
        }

        if (relatorioCausaSolicitacao.getIdTipoDemandaServico() != null) {
            sql.append("and ss.idtipodemandaservico = ? ");
            parametro.add(relatorioCausaSolicitacao.getIdTipoDemandaServico());
        }

        if (relatorioCausaSolicitacao.getSituacao() != null && UtilStrings.isNotVazio(relatorioCausaSolicitacao.getSituacao())) {
            sql.append("and ss.situacao = ? ");
            parametro.add(relatorioCausaSolicitacao.getSituacao());
        } else {
            sql.append("and ss.situacao in ('Fechada', 'Cancelada') ");
        }

        if (relatorioCausaSolicitacao.getIdServicos() != null) {
            sql.append("and sc.idservico IN (" + StringUtils.join(relatorioCausaSolicitacao.getIdServicos(), ", ") + ") ");
        }

        if (relatorioCausaSolicitacao.getIdGrupos() != null) {
            sql.append("and ss.idgrupoatual IN (" + StringUtils.join(relatorioCausaSolicitacao.getIdGrupos(), ", ") + ") ");
        }

        if (relatorioCausaSolicitacao.getIdSolucoes() != null) {
            sql.append("and (" + (relatorioCausaSolicitacao.getExibeSemSolucao() != null && relatorioCausaSolicitacao.getExibeSemSolucao().equalsIgnoreCase("s") ? "ss.idcategoriaSolucao is NULL OR " : "") + " ss.idcategoriaSolucao IN (" + StringUtils.join(relatorioCausaSolicitacao.getIdSolucoes(), ", ") + ")) ");
        }

        sql.append("group by cs.idcategoriasolucao, descricaocategoriasolucao");

        System.out.println(sql + " - " + parametro.toString());

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idCategoriaSolucao");
        listRetorno.add("descricaoCategoriaSolucao");
        listRetorno.add("numeroSolicitacoes");

        if (list != null && !list.isEmpty()) {
            Collection<RelatorioCausaSolucaoDTO> listaSolicitacaoPorExecutante;
            listaSolicitacaoPorExecutante = this.listConvertion(RelatorioCausaSolucaoDTO.class, list, listRetorno);
            return listaSolicitacaoPorExecutante;
        } else {
            return null;
        }
    }

    public Collection<RelatorioCausaSolucaoDTO> listaCausaSolucaoAnalitico(RelatorioCausaSolucaoDTO relatorioCausaSolicitacao) throws Exception {
        List parametro = new ArrayList();
        List listRetorno = new ArrayList();
        List list = new ArrayList();

        StringBuilder sql = new StringBuilder();
        sql.append("select ss.idsolicitacaoservico, ss.situacao as status, s.nomeservico, c.descricaocausa, cs.descricaocategoriasolucao from solicitacaoservico ss ");
        sql.append("join servicocontrato sc on ss.idservicocontrato = sc.idservicocontrato ");
        sql.append("join servico s on sc.idservico = s.idservico ");
        sql.append("left join causaincidente c on c.idcausaincidente = ss.idcausaincidente ");
        sql.append("left join categoriasolucao cs on cs.idcategoriasolucao = ss.idcategoriasolucao ");

        if (relatorioCausaSolicitacao.getDataInicio() != null) {
            if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
                sql.append("where to_char(ss.datahorafim, 'YYYY-MM-DD') BETWEEN ? AND ? ");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                parametro.add(formatter.format(relatorioCausaSolicitacao.getDataInicio()));
                parametro.add(formatter.format(relatorioCausaSolicitacao.getDataFim()));
            } else {
                sql.append("where ss.datahorafim BETWEEN ? AND ? ");
                parametro.add(relatorioCausaSolicitacao.getDataInicio());
                parametro.add(transformaHoraFinal(relatorioCausaSolicitacao.getDataFim()));
            }
        }

        if (relatorioCausaSolicitacao.getIdContrato() != null) {
            sql.append(" and sc.idcontrato = ?  ");
            parametro.add(relatorioCausaSolicitacao.getIdContrato());
        }

        if (relatorioCausaSolicitacao.getIdTipoDemandaServico() != null) {
            sql.append("and ss.idtipodemandaservico = ? ");
            parametro.add(relatorioCausaSolicitacao.getIdTipoDemandaServico());
        }

        if (relatorioCausaSolicitacao.getSituacao() != null && UtilStrings.isNotVazio(relatorioCausaSolicitacao.getSituacao())) {
            sql.append("and ss.situacao = ? ");
            parametro.add(relatorioCausaSolicitacao.getSituacao());
        } else {
            sql.append("and ss.situacao in ('Fechada', 'Cancelada') ");
        }

        if (relatorioCausaSolicitacao.getIdServicos() != null) {
            sql.append("and sc.idservico IN (" + StringUtils.join(relatorioCausaSolicitacao.getIdServicos(), ", ") + ") ");
        }

        if (relatorioCausaSolicitacao.getIdGrupos() != null) {
            sql.append("and ss.idgrupoatual IN (" + StringUtils.join(relatorioCausaSolicitacao.getIdGrupos(), ", ") + ") ");
        }

        if (relatorioCausaSolicitacao.getIdCausas() != null || relatorioCausaSolicitacao.getIdSolucoes() != null) {
            sql.append("and ( ");
            if (relatorioCausaSolicitacao.getIdCausas() != null) {
                sql.append((relatorioCausaSolicitacao.getExibeSemCausa() != null && relatorioCausaSolicitacao.getExibeSemCausa().equalsIgnoreCase("s") ? "ss.idcausaincidente is NULL OR " : "") + " ss.idcausaincidente IN (" + StringUtils.join(relatorioCausaSolicitacao.getIdCausas(), ", ") + ") ");
            }
            if (relatorioCausaSolicitacao.getIdSolucoes() != null) {
                sql.append((relatorioCausaSolicitacao.getIdCausas() != null ? " OR " : "") + (relatorioCausaSolicitacao.getExibeSemSolucao() != null && relatorioCausaSolicitacao.getExibeSemSolucao().equalsIgnoreCase("s") ? "ss.idcategoriaSolucao is NULL OR " : "") + " ss.idcategoriaSolucao IN (" + StringUtils.join(relatorioCausaSolicitacao.getIdSolucoes(), ", ") + ") ");
            }
            sql.append(") ");
        }

        System.out.println(sql + " - " + parametro.toString());

        list = this.execSQL(sql.toString(), parametro.toArray());

        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("status");
        listRetorno.add("nomeServico");
        listRetorno.add("descricaoCausa");
        listRetorno.add("descricaoCategoriaSolucao");

        if (list != null && !list.isEmpty()) {
            Collection<RelatorioCausaSolucaoDTO> listaSolicitacaoPorExecutante;
            listaSolicitacaoPorExecutante = this.listConvertion(RelatorioCausaSolucaoDTO.class, list, listRetorno);
            return listaSolicitacaoPorExecutante;
        } else {
            return null;
        }
    }
    
    /**
     * Método para listar número de solicitações fora do período fornecido pelo usuário
     * 
     * @param relatorioIncidentesNaoResolvidosDTO
     * @return
     * @throws PersistenceException
     * @author thyen.chang
     */
    public Integer numeroSolicitacoesForaPeriodo(RelatorioIncidentesNaoResolvidosDTO relatorioIncidentesNaoResolvidosDTO) throws PersistenceException{
        List parametro = new ArrayList();
        List retorno = new ArrayList();
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("SELECT idsolicitacaoservico ");
        sql.append("FROM solicitacaoservico ss ");
        sql.append("INNER JOIN servicocontrato sc ON ss.idservicocontrato = sc.idservicocontrato ");
        sql.append("INNER JOIN servico servico ON servico.idservico = sc.idservico ");
        sql.append("INNER JOIN contratos c ON c.idcontrato = sc.idcontrato ");
        sql.append("INNER JOIN tipodemandaservico t on t.idtipodemandaservico = ss.idtipodemandaservico ");

        sql.append("LEFT JOIN usuario u ON u.idusuario = ss.idusuarioresponsavelatual ");
        sql.append("INNER JOIN empregados e ON e.idempregado = ss.idsolicitante ");
        sql.append("INNER JOIN grupo g ON ss.idgrupoatual = g.idgrupo ");
        sql.append("WHERE (ss.situacao NOT LIKE '%Fechada%' AND ss.situacao NOT LIKE'%Cancelada%') ");
        sql.append("AND datahorasolicitacao < ? ");
        
        parametro.add(relatorioIncidentesNaoResolvidosDTO.getPeriodoReferencia());

        if (relatorioIncidentesNaoResolvidosDTO.getListaServicos() != null && !relatorioIncidentesNaoResolvidosDTO.getListaServicos().isEmpty()) {

            int aux = 1;
            String[] listaServicosTela;
            listaServicosTela = relatorioIncidentesNaoResolvidosDTO.getListaServicos().split(";");
            if (listaServicosTela != null && listaServicosTela.length > 0) {
                sql.append("AND ( ");
                for (String i : listaServicosTela) {
                    i = StringUtils.deleteWhitespace(i);
                    if (StringUtils.isNotBlank(i)) {
                        if (listaServicosTela.length > aux) {
                            sql.append(" sc.idservico = ? OR ");
                            parametro.add(Integer.parseInt(i));
                        } else {
                            sql.append(" sc.idservico = ? ");
                            parametro.add(Integer.parseInt(i));
                        }
                    }
                    aux++;
                }
                sql.append(" ) ");
            }
        }

        if (relatorioIncidentesNaoResolvidosDTO.getListaGrupos() != null && !relatorioIncidentesNaoResolvidosDTO.getListaGrupos().isEmpty()) {

            int aux = 1;
            String[] listaGrupoTela;
            listaGrupoTela = relatorioIncidentesNaoResolvidosDTO.getListaGrupos().split(";");
            if (listaGrupoTela != null && listaGrupoTela.length > 0) {
                sql.append("AND ( ");
                for (String i : listaGrupoTela) {
                    i = StringUtils.deleteWhitespace(i);
                    if (StringUtils.isNotBlank(i)) {
                        if (listaGrupoTela.length > aux) {
                            sql.append(" ss.idgrupoatual = ? OR ");
                            parametro.add(Integer.parseInt(i));
                        } else {
                            sql.append(" ss.idgrupoatual = ? ");
                            parametro.add(Integer.parseInt(i));
                        }
                    }
                    aux++;
                }
                sql.append(" ) ");
            }
        }

        if (relatorioIncidentesNaoResolvidosDTO.getIdContrato() != null) {
            sql.append("AND sc.idcontrato = ? ");
            parametro.add(relatorioIncidentesNaoResolvidosDTO.getIdContrato());
        }

        retorno = this.execSQL(sql.toString(), parametro.toArray());

        return retorno.size();
    }

    protected List<String> getListNamesFieldDB() {
        final Collection<Field> col = this.getFields();
        final List<String> lstRetorno = new ArrayList<>();
        if (col != null) {
            for (final Field field : col) {
                lstRetorno.add(field.getFieldDB());
            }
        }
        return lstRetorno;
    }

    public Long listaRelatorioGetQuantidadeRegistros(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto) throws ServiceException, Exception{
    	StringBuilder sql = new StringBuilder();
    	
    	List parametros = new ArrayList();
    	
    	List lista = new ArrayList();
    	
    	sql.append("SELECT COUNT(*) ");
        sql.append(montaSql(parametros, pesquisaSolicitacaoServicoDto, 0, true));
        
        lista = this.execSQL(sql.toString(), parametros.toArray());
        
        if(lista != null && !lista.isEmpty() && lista.get(0) != null)
        	if(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))
        		return ((Integer) ((Object[]) lista.get(0))[0]).longValue();
        	else if (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))
        		return ((BigDecimal) ((Object[]) lista.get(0))[0]).longValue();
        	else
        	return (Long) ((Object[]) lista.get(0))[0];
        else
        	return 0L;
    }
    
    public List<SolicitacaoServicoDTO> listRelatorioGetListaPaginada(PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto, Integer paginaAtual, Integer quantidadePorPagina) throws Exception{

    	StringBuilder sql = new StringBuilder();
    	
    	List parametros = new ArrayList();
    	
    	List lista = new ArrayList();
    	
    	List listRetorno = new ArrayList();
    	
    	if(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)){
    		sql.append(";WITH ResultadoTemporario AS ( ");
    	}
    	
    	if(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE)){
    		sql.append("SELECT * FROM( "
    				+ "SELECT rowNum AS rnum, a.* FROM ( ");
    	}
    	
    	sql.append("SELECT tempoAtendimentoHH,tempoAtendimentoMM, solicitacaoservico.datahorainicio, solicitacaoservico.datahorafim, solicitacaoservico.idsolicitacaoservico, "
        		+ "nomeservico, unidade.nome AS nomeUnidadeSolicitante, solicitacaoservico.situacao, dataHoraSolicitacao, dataHoraLimite, nomeTipoDemandaServico, "
        		+ "solicitacaoservico.prazohh, solicitacaoservico.prazomm, ");
        sql.append("solicitacaoservico.descricaoSemFormatacao, resposta, grupo.sigla, solicitacaoservico.seqreabertura, empregado.nome AS nomeSolicitante, "
        		+ "faseservico.nomefase, origematendimento.descricao,prioridade.nomeprioridade, usuario.nome AS nomeResponsavel, contratos.numero, idUsuarioResponsavelAtual,  ");
        sql.append("grupo.nome AS grupoAtual, localidade.nomelocalidade ");
        
    	if(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)){
    		sql.append(", ROW_NUMBER() OVER (ORDER BY solicitacaoservico.idsolicitacaoservico) as rowNum ");
    	}
    	
        sql.append(montaSql(parametros, pesquisaSolicitacaoServicoDto, 0, false));
        
    	if(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)){
    		sql.append(") SELECT * FROM ResultadoTemporario ");
    		sql.append("WHERE rowNum > " + ((paginaAtual - 1) * quantidadePorPagina) + " ");
    		sql.append("AND rowNum <= " + (paginaAtual * quantidadePorPagina) + " ");
    	}
    	
        if (pesquisaSolicitacaoServicoDto.getOrdenacao() != null && !(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))) {
            sql.append(" ORDER BY " + pesquisaSolicitacaoServicoDto.getOrdenacao() + "");
        }
        
        if(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE)){
    		sql.append(") a "
    				+ "WHERE rowNum <=" + (paginaAtual * quantidadePorPagina) + " "
					+ ") WHERE rnum > " + ((paginaAtual - 1) * quantidadePorPagina) + " ");
    	}
        
        if(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.MYSQL) || CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.POSTGRESQL)){
		    sql.append(" LIMIT ? OFFSET ? ");
		    parametros.add(quantidadePorPagina);
		    parametros.add(quantidadePorPagina * (paginaAtual - 1));
        }
        
        lista = this.execSQL(sql.toString(), parametros.toArray());
        if(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))
        	listRetorno.add("rowNumOracle");
        listRetorno.add("tempoAtendimentoHH");
        listRetorno.add("tempoAtendimentoMM");
        listRetorno.add("dataHoraInicio");
        listRetorno.add("dataHoraFim");
        listRetorno.add("idSolicitacaoServico");
        listRetorno.add("nomeServico");
        listRetorno.add("nomeUnidadeSolicitante");
        listRetorno.add("situacao");
        listRetorno.add("dataHoraSolicitacao");
        listRetorno.add("dataHoraLimite");
        listRetorno.add("nomeTipoDemandaServico");
        listRetorno.add("prazoHH");
        listRetorno.add("prazoMM");
        listRetorno.add("descricaoSemFormatacao");
        listRetorno.add("resposta");
        listRetorno.add("siglaGrupo");
        listRetorno.add("seqReabertura");
        listRetorno.add("nomeSolicitante");
        listRetorno.add("faseAtual");
        listRetorno.add("origem");
        listRetorno.add("prioridade");
        listRetorno.add("responsavel");
        listRetorno.add("contrato");
        listRetorno.add("idUsuarioResponsavelAtual");
        listRetorno.add("grupoAtual");
        listRetorno.add("localidade");
        
        List listaSolicitacoes = engine.listConvertion(getBean(), lista, listRetorno);

        return listaSolicitacoes;
    }
    
    private String montaSql(List parametros, PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto, Integer limiteConsulta, boolean isRelatorio) throws ServiceException, Exception{
    	StringBuilder sql = new StringBuilder();
    	UnidadeDao unidadeDao = new UnidadeDao();
    	
    	sql.append("FROM solicitacaoservico JOIN servicocontrato ON ");
        if ((pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null)&&(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa().intValue()>0)) {
            sql.append("(solicitacaoservico.idSolicitacaoServico = ?) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
        }
        if ((pesquisaSolicitacaoServicoDto.getIdPrioridade() != null)&&(pesquisaSolicitacaoServicoDto.getIdPrioridade().intValue()>0)) {
            sql.append("(solicitacaoservico.idprioridade = ? ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdPrioridade());
        }
        if ((pesquisaSolicitacaoServicoDto.getIdOrigem() != null)&&(pesquisaSolicitacaoServicoDto.getIdOrigem().intValue()>0)) {
            sql.append("(solicitacaoservico.idorigem = ? ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdOrigem());
        }
        if ((pesquisaSolicitacaoServicoDto.getIdUnidade() != null)&&(pesquisaSolicitacaoServicoDto.getIdUnidade().intValue()>0)) {
            sql.append("(solicitacaoservico.idunidade = ? ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdUnidade());
        }
        
        if ((pesquisaSolicitacaoServicoDto.getUsuarioLogado()!=null)&&(pesquisaSolicitacaoServicoDto.getUsuarioLogado().getIdUsuario()!=null)&&(pesquisaSolicitacaoServicoDto.getUsuarioLogado().getIdUsuario().intValue()>0)) {
        	sql.append("(solicitacaoservico.idunidade in ("+unidadeDao.obtenIDsUnidadesUsuario(pesquisaSolicitacaoServicoDto.getUsuarioLogado())+")) AND ");
        }

        if ((pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null)&&(pesquisaSolicitacaoServicoDto.getIdFaseAtual().intValue()>0)) {
            sql.append("(solicitacaoservico.idfaseatual = ? ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
        }
        if ((pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null)&&(pesquisaSolicitacaoServicoDto.getIdGrupoAtual().intValue()>0)) {
            sql.append("(solicitacaoservico.idgrupoatual = ? ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());
        }
        if ((pesquisaSolicitacaoServicoDto.getSituacao() != null) && (pesquisaSolicitacaoServicoDto.getSituacao().length()>0)) {
            sql.append("(solicitacaoservico.situacao = ? ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getSituacao());
        }
        if ((pesquisaSolicitacaoServicoDto.getIdSolicitante() != null)&&(pesquisaSolicitacaoServicoDto.getIdSolicitante().intValue()>0)) {
            sql.append("(solicitacaoservico.idsolicitante = ? ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdSolicitante());
        }
        if ((pesquisaSolicitacaoServicoDto.getIdItemConfiguracao() != null)&&(pesquisaSolicitacaoServicoDto.getIdItemConfiguracao().intValue()>0)) {
            sql.append("(solicitacaoservico.iditemconfiguracao = ? ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdItemConfiguracao());
        }
        if ((pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null)&&(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico().intValue()>0)) {
            sql.append("(solicitacaoservico.idTipoDemandaServico = ? ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
        }
        if ((pesquisaSolicitacaoServicoDto.getIdResponsavel() != null)&&(pesquisaSolicitacaoServicoDto.getIdResponsavel().intValue()>0)) {
            sql.append("(solicitacaoservico.idResponsavel = ?  ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdResponsavel());
        }
        if ((pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual() != null)&&(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual().intValue()>0)) {
            sql.append("(solicitacaoservico.idusuarioresponsavelatual = ?  ) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdUsuarioResponsavelAtual());
        }
        if (pesquisaSolicitacaoServicoDto.getPalavraChave() != null && !pesquisaSolicitacaoServicoDto.getPalavraChave().equalsIgnoreCase("")) {
        	if(CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER))
                sql.append("(solicitacaoservico.descricao like '%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%' COLLATE SQL_Latin1_General_CP1_CI_AS) AND ");
        	else
        		sql.append("(UPPER(solicitacaoservico.descricao) like UPPER('%" + pesquisaSolicitacaoServicoDto.getPalavraChave() + "%')) AND ");
        }
		if(pesquisaSolicitacaoServicoDto.getDataInicio() != null && pesquisaSolicitacaoServicoDto.getDataFim() != null) {
			sql.append("(solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) AND ");
			parametros.add(UtilDatas.getSqlDate(pesquisaSolicitacaoServicoDto.getDataInicio()));
			parametros.add(UtilDatas.getTimeStampComUltimaHoraDoDia(pesquisaSolicitacaoServicoDto.getDataFim()));
			}
		if ((pesquisaSolicitacaoServicoDto.getDataInicioFechamento() != null)&&(pesquisaSolicitacaoServicoDto.getDataFimFechamento() != null)&&(!StringUtils.equalsIgnoreCase(pesquisaSolicitacaoServicoDto.getDataInicioFechamento().toString(), "1970-01-01"))) {
			sql.append("(solicitacaoservico.datahorafim BETWEEN ? AND ?) AND ");
			parametros.add(UtilDatas.getSqlDate(pesquisaSolicitacaoServicoDto.getDataInicioFechamento()));
			parametros.add(UtilDatas.getTimeStampComUltimaHoraDoDia(pesquisaSolicitacaoServicoDto.getDataFimFechamento()));
		}
        if ((pesquisaSolicitacaoServicoDto.getIdContrato() != null)&&(pesquisaSolicitacaoServicoDto.getIdContrato().intValue()>0)) {
            sql.append("(servicocontrato.idcontrato = ?) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdContrato());
            }
        if ((pesquisaSolicitacaoServicoDto.getIdServico() != null)&&(pesquisaSolicitacaoServicoDto.getIdServico().intValue()>0)) {
            sql.append("(servicocontrato.idservico = ?) AND ");
            parametros.add(pesquisaSolicitacaoServicoDto.getIdServico());
        }
        sql.append("solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");

        sql.append("JOIN contratos ON servicocontrato.idcontrato = contratos.idcontrato ");
    	sql.append("JOIN servico ON servicocontrato.idservico = servico.idservico ");
    	sql.append("JOIN tipodemandaservico ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
    	sql.append("JOIN usuario on usuario.idusuario = solicitacaoservico.idresponsavel ");
    	if(isRelatorio){
	    	sql.append("JOIN execucaosolicitacao es ON es.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
	        sql.append("JOIN bpm_instanciafluxo ifluxo ON ifluxo.idinstancia = es.idinstanciafluxo AND ifluxo.idfluxo = es.idfluxo ");
	        sql.append("JOIN bpm_itemtrabalhofluxo itf ON ifluxo.idinstancia = itf.idinstancia ");
	        sql.append("JOIN bpm_elementofluxo ef ON itf.idelemento = ef.idelemento ");
	        sql.append("LEFT JOIN usuario u ON u.idusuario = itf.idresponsavelatual ");
    	}
        sql.append("LEFT JOIN faseservico ON faseservico.idfase = solicitacaoservico.idfaseAtual ");
        sql.append("LEFT JOIN grupo ON grupo.idgrupo = solicitacaoservico.idgrupoatual ");
        sql.append("LEFT JOIN empregados empregado ON empregado.idempregado = solicitacaoservico.idsolicitante ");
        sql.append("LEFT JOIN origematendimento ON origematendimento.idorigem = solicitacaoservico.idorigem ");
        sql.append("LEFT JOIN prioridade ON prioridade.idprioridade = solicitacaoservico.idprioridade ");
        sql.append("LEFT JOIN unidade ON unidade.idunidade = solicitacaoservico.idunidade ");
        sql.append("LEFT JOIN contatosolicitacaoservico ON contatosolicitacaoservico.idcontatosolicitacaoservico = solicitacaoservico.idcontatosolicitacaoservico ");
        sql.append("LEFT JOIN localidade ON contatosolicitacaoservico.idlocalidade = localidade.idlocalidade ");
        
        if(limiteConsulta != null && limiteConsulta > 0 && (CITCorporeUtil.SGBD_PRINCIPAL.trim().toUpperCase().equalsIgnoreCase(SQLConfig.ORACLE))){
			sql.append("WHERE ROWNUM <= ? ");
			parametros.add(limiteConsulta);
			if ((pesquisaSolicitacaoServicoDto.getIdLocalidade() != null) && (pesquisaSolicitacaoServicoDto.getIdLocalidade().intValue() > 0)) {
    			sql.append("AND localidade.idlocalidade = ?  ");
    			parametros.add(pesquisaSolicitacaoServicoDto.getIdLocalidade());
    		}
        }else{
        	if ((pesquisaSolicitacaoServicoDto.getIdLocalidade() != null) && (pesquisaSolicitacaoServicoDto.getIdLocalidade().intValue() > 0)) {
    			sql.append("WHERE localidade.idlocalidade = ?  ");
    			parametros.add(pesquisaSolicitacaoServicoDto.getIdLocalidade());
    		}
        }
    	return sql.toString();
    }

}