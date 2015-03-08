package br.com.centralit.citcorpore.bpm.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.InstanciaFluxoDTO;
import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.bpm.integracao.ItemTrabalhoFluxoDao;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.centralit.bpm.negocio.ExecucaoFluxo;
import br.com.centralit.bpm.negocio.InstanciaFluxo;
import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citcorpore.bean.CalculoJornadaDTO;
import br.com.centralit.citcorpore.bean.CategoriaProblemaDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoProblemaDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.JustificativaProblemaDTO;
import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.CategoriaProblemaDAO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.ExecucaoProblemaDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.ModeloEmailDao;
import br.com.centralit.citcorpore.integracao.OcorrenciaProblemaDAO;
import br.com.centralit.citcorpore.integracao.ProblemaDAO;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.negocio.CalendarioServiceEjb;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.OcorrenciaProblemaServiceEjb;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.ProblemaServiceEjb;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.FaseRequisicaoProblema;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoProblema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoProblema;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.comparacao.ObjectSimpleComparator;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

import com.google.gson.Gson;

@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class ExecucaoProblema extends ExecucaoFluxo {

    protected UsuarioDTO usuarioDto = null;
    private EmpregadoService empregadoService;
    protected ProblemaService problemaService;

    public String i18n_Message(final UsuarioDTO usuario, final String key) {
        if (usuario != null) {
            if (UtilI18N.internacionaliza(usuario.getLocale(), key) != null) {
                return UtilI18N.internacionaliza(usuario.getLocale(), key);
            }
            return key;
        }
        return key;
    }

    private EmpregadoService getEmpregadoService() throws ServiceException, Exception {
        if (empregadoService == null) {
            empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
        }
        return empregadoService;
    }

    private ProblemaService getProblemaService() throws ServiceException, Exception {
        if (problemaService == null) {
            problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
        }
        return problemaService;
    }

    public ExecucaoProblema(final TransactionControler tc) {
        super(tc);
    }

    public ExecucaoProblema() {
        super();
    }

    public ExecucaoProblema(final ProblemaDTO problemaDto, final TransactionControler tc) {
        super(problemaDto, tc);
    }

    @Override
    public InstanciaFluxo inicia(final String nomeFluxo, final Integer idFase) throws Exception {
        final TipoFluxoDao tipoFluxoDao = new TipoFluxoDao();
        final TipoFluxoDTO tipoFluxoDto = tipoFluxoDao.findByNome(nomeFluxo);
        if (tipoFluxoDto == null) {
            System.out.println("Fluxo " + nomeFluxo + " não existe");
            throw new Exception(i18n_Message("citcorpore.comum.fluxoNaoEncontrado"));
        }
        return inicia(new FluxoDao().findByTipoFluxo(tipoFluxoDto.getIdTipoFluxo()), idFase);
    }

    @Override
    public InstanciaFluxo inicia() throws Exception {
        InstanciaFluxo result = null;
        final InstanciaFluxoDTO instanciaFluxoDto = null;
        final CategoriaProblemaDTO categoriaProblemaDto = recuperaCategoriaProblema();
        if (categoriaProblemaDto != null) {

            if (categoriaProblemaDto.getIdTipoFluxo() != null) {

                result = inicia(new FluxoDao().findByTipoFluxo(categoriaProblemaDto.getIdTipoFluxo()), null);
            }
        } else {
            final String fluxoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(
                    ParametroSistema.NomeFluxoPadraoProblema, null);
            if (fluxoPadrao == null) {
                throw new Exception(i18n_Message("citcorpore.comum.fluxoNaoParametrizado"));
            }

            result = inicia(fluxoPadrao, null);
        }

        try {

            final String enviarNotificacao = ParametroUtil.getValor(
                    ParametroSistema.NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO, getTransacao(), "N");
            final String IdModeloEmailGrupoDestinoProblema = ParametroUtil.getValorParametroCitSmartHashMap(
                    ParametroSistema.ID_MODELO_EMAIL_GRUPO_DESTINO_PROBLEMA, "37");
            if (enviarNotificacao.equalsIgnoreCase("S") && getProblemaDto().escalada()) {
                enviaEmailGrupo(Integer.parseInt(IdModeloEmailGrupoDestinoProblema.trim()), getProblemaDto()
                        .getIdGrupoAtual());
            }
        } catch (final NumberFormatException e) {
            System.out.println("Não há modelo de e-mail setado nos parâmetros.");
        }
        return result;
    }

    @Override
    public InstanciaFluxo inicia(final FluxoDTO fluxoDto, final Integer idFase) throws Exception {
        if (fluxoDto == null) {
            throw new Exception(i18n_Message("citcorpore.comum.fluxoNaoEncontrado"));
        }

        this.fluxoDto = fluxoDto;

        final HashMap<String, Object> map = new HashMap();
        mapObjetoNegocio(map);
        final InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, map);

        final ExecucaoProblemaDTO execucaoProblemaDto = new ExecucaoProblemaDTO();
        execucaoProblemaDto.setIdProblema(getProblemaDto().getIdProblema());
        execucaoProblemaDto.setIdFase(getProblemaDto().getIdFaseAtual());
        execucaoProblemaDto.setIdFluxo(instanciaFluxo.getIdFluxo());
        execucaoProblemaDto.setIdInstanciaFluxo(instanciaFluxo.getIdInstancia());
        Integer seqReabertura = 0;
        if (getProblemaDto().getSeqReabertura() != null && getProblemaDto().getSeqReabertura().intValue() > 0) {
            seqReabertura = getProblemaDto().getSeqReabertura();
        }
        if (seqReabertura.intValue() > 0) {
            execucaoProblemaDto.setSeqReabertura(getProblemaDto().getSeqReabertura());
        }

        final ExecucaoProblemaDao execucaoProblemaDao = new ExecucaoProblemaDao();
        setTransacaoDao(execucaoProblemaDao);
        execucaoFluxoDto = (ExecucaoProblemaDTO) execucaoProblemaDao.create(execucaoProblemaDto);

        if (seqReabertura.intValue() == 0 && getProblemaDto().getEnviaEmailCriacao() != null
                && getProblemaDto().getEnviaEmailCriacao().equalsIgnoreCase("S")) {
            final String IdModeloEmailCriacaoProblema = ParametroUtil.getValorParametroCitSmartHashMap(
                    ParametroSistema.ID_MODELO_EMAIL_CRIACAO_PROBLEMA, "34");
            final Integer idModeloEmail = Integer.parseInt(IdModeloEmailCriacaoProblema.trim());
            enviaEmail(idModeloEmail);
        }
        return instanciaFluxo;
    }

    @Override
    public void executa(final String loginUsuario, final ObjetoNegocioFluxoDTO objetoNegocioDto,
            final Integer idItemTrabalho, final String acao, final HashMap<String, Object> map) throws Exception {
        if (acao.equals(Enumerados.ACAO_DELEGAR)) {
            return;
        }

        TarefaFluxoDTO tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
        if (tarefaFluxoDto == null) {
            return;
        }

        final ExecucaoProblemaDao execucaoProblemaDao = new ExecucaoProblemaDao();
        setTransacaoDao(execucaoProblemaDao);
        final ExecucaoProblemaDTO execucaoProblemaDto = execucaoProblemaDao.findByIdInstanciaFluxo(tarefaFluxoDto
                .getIdInstancia());
        if (execucaoProblemaDto == null) {
            return;
        }

        recuperaFluxo(execucaoProblemaDto.getIdFluxo());

        this.objetoNegocioDto = objetoNegocioDto;
        usuarioDto = new UsuarioDao().restoreByLogin(loginUsuario);

        final InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, tarefaFluxoDto.getIdInstancia());
        mapObjetoNegocio(instanciaFluxo.getObjetos(map));

        if (acao.equals(Enumerados.ACAO_INICIAR)) {
            instanciaFluxo.iniciaItemTrabalho(loginUsuario, tarefaFluxoDto.getIdItemTrabalho(), map);
        } else if (acao.equals(Enumerados.ACAO_EXECUTAR)) {
            tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
            final OcorrenciaProblemaDAO ocorrenciaProblemaDao = new OcorrenciaProblemaDAO();
            setTransacaoDao(ocorrenciaProblemaDao);
            final OcorrenciaProblemaDTO ocorrenciaProblemaDto = new OcorrenciaProblemaDTO();
            ocorrenciaProblemaDto.setIdProblema(getProblemaDto().getIdProblema());
            ocorrenciaProblemaDto.setDataregistro(UtilDatas.getDataAtual());
            ocorrenciaProblemaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
            Long tempo = new Long(0);
            if (tarefaFluxoDto.getDataHoraFinalizacao() != null) {
                tempo = (tarefaFluxoDto.getDataHoraFinalizacao().getTime() - tarefaFluxoDto.getDataHoraCriacao()
                        .getTime()) / 1000 / 60;
            }
            ocorrenciaProblemaDto.setTempoGasto(tempo.intValue());
            ocorrenciaProblemaDto.setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao
                    .getDescricao());
            ocorrenciaProblemaDto.setDataInicio(UtilDatas.getDataAtual());
            ocorrenciaProblemaDto.setDataFim(UtilDatas.getDataAtual());
            ocorrenciaProblemaDto.setInformacoesContato("não se aplica");
            ocorrenciaProblemaDto.setRegistradopor(loginUsuario);
            try {
                ocorrenciaProblemaDto.setDadosProblema(new Gson().toJson(getProblemaDto()));
            } catch (final Exception e) {
                e.printStackTrace();
            }
            ocorrenciaProblemaDto.setOcorrencia("Execução da tarefa \""
                    + tarefaFluxoDto.getElementoFluxoDto().getDocumentacao() + "\"");
            ocorrenciaProblemaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS
                    .getSigla().toString());
            ocorrenciaProblemaDto.setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Execucao
                    .getSigla());
            ocorrenciaProblemaDto.setIdItemTrabalho(idItemTrabalho);
            ocorrenciaProblemaDao.create(ocorrenciaProblemaDto);

            instanciaFluxo.executaItemTrabalho(loginUsuario, tarefaFluxoDto.getIdItemTrabalho(), map);

            if (getProblemaDto().getFase() != null && !getProblemaDto().getFase().equalsIgnoreCase("")) {
                final SituacaoRequisicaoProblema novaSituacao = FaseRequisicaoProblema.valueOf(
                        getProblemaDto().getFase()).getSituacao();
                if (novaSituacao != null) {
                    final ProblemaDAO problemaDao = new ProblemaDAO();
                    setTransacaoDao(problemaDao);
                    getProblemaDto().setStatus(novaSituacao.getDescricao());
                    problemaDao.updateNotNull(getProblemaDto());
                }
            }
        }

        if (getProblemaDto().getEnviaEmailAcoes() != null
                && getProblemaDto().getEnviaEmailAcoes().equalsIgnoreCase("S")) {
            final String IdModeloEmailProblemaAndamento = ParametroUtil.getValorParametroCitSmartHashMap(
                    ParametroSistema.ID_MODELO_EMAIL_ANDAMENTO_PROBLEMA, "35");
            enviaEmail(Integer.parseInt(IdModeloEmailProblemaAndamento.trim()));
        }

        if (tarefaFluxoDto.getElementoFluxoDto().getContabilizaSLA() == null
                || tarefaFluxoDto.getElementoFluxoDto().getContabilizaSLA().equalsIgnoreCase("S")) {
            if (getProblemaDto().getDataHoraCaptura() == null) {
                getProblemaDto().setDataHoraCaptura(UtilDatas.getDataHoraAtual());
                final ProblemaDAO problemaDao = new ProblemaDAO();
                setTransacaoDao(problemaDao);
                problemaDao.atualizaDataHoraCaptura(getProblemaDto());
            }
        }

    }

    @Override
    public void delega(final String loginUsuario, final ObjetoNegocioFluxoDTO objetoNegocioDto,
            final Integer idItemTrabalho, final String usuarioDestino, final String grupoDestino) throws Exception {
        final TarefaFluxoDTO tarefaFluxoDto = recuperaTarefa(idItemTrabalho);
        if (tarefaFluxoDto == null) {
            return;
        }

        final InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, tarefaFluxoDto.getIdInstancia());
        instanciaFluxo.delegaItemTrabalho(loginUsuario, idItemTrabalho, usuarioDestino, grupoDestino);

        this.objetoNegocioDto = objetoNegocioDto;

        final OcorrenciaProblemaDAO ocorrenciaProblemaDao = new OcorrenciaProblemaDAO();
        setTransacaoDao(ocorrenciaProblemaDao);
        final OcorrenciaProblemaDTO ocorrenciaProblemaDto = new OcorrenciaProblemaDTO();
        ocorrenciaProblemaDto.setIdProblema(getProblemaDto().getIdProblema());
        ocorrenciaProblemaDto.setDataregistro(UtilDatas.getDataAtual());
        ocorrenciaProblemaDto.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
        ocorrenciaProblemaDto.setTempoGasto(0);
        ocorrenciaProblemaDto
                .setDescricao(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Compartilhamento
                        .getDescricao());
        ocorrenciaProblemaDto.setDataInicio(UtilDatas.getDataAtual());
        ocorrenciaProblemaDto.setDataFim(UtilDatas.getDataAtual());
        ocorrenciaProblemaDto.setInformacoesContato("não se aplica");
        ocorrenciaProblemaDto.setRegistradopor(loginUsuario);
        try {
            ocorrenciaProblemaDto.setDadosProblema(new Gson().toJson(getProblemaDto()));
        } catch (final Exception e) {
            e.printStackTrace();
        }
        String ocorr = "Compartilhamento da tarefa \"" + tarefaFluxoDto.getElementoFluxoDto().getDocumentacao() + "\"";
        if (usuarioDestino != null) {
            ocorr += " com o usuário " + usuarioDestino;
        }
        if (grupoDestino != null) {
            ocorr += " com o grupo " + grupoDestino;
        }
        ocorrenciaProblemaDto.setOcorrencia(ocorr);
        ocorrenciaProblemaDto.setOrigem(br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia.OUTROS.getSigla()
                .toString());
        ocorrenciaProblemaDto
                .setCategoria(br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia.Compartilhamento
                        .getSigla());
        ocorrenciaProblemaDto.setIdItemTrabalho(idItemTrabalho);
        ocorrenciaProblemaDao.create(ocorrenciaProblemaDto);
    }

    @Override
    public void direcionaAtendimento(final String loginUsuario, final ObjetoNegocioFluxoDTO objetoNegocioDto,
            final String grupoAtendimento) throws Exception {
        if (getProblemaDto() == null) {
            return;
        }

        if (grupoAtendimento == null) {
            return;
        }

        final GrupoDTO grupoAtendimentoDto = new GrupoDao().restoreBySigla(grupoAtendimento);
        if (grupoAtendimentoDto == null) {
            return;
        }

        UsuarioDTO usuarioRespDto = new UsuarioDTO();
        usuarioRespDto.setIdUsuario(getProblemaDto().getIdResponsavel());
        usuarioRespDto = (UsuarioDTO) new UsuarioDao().restore(usuarioRespDto);

        this.objetoNegocioDto = objetoNegocioDto;

        final Collection<ExecucaoProblemaDTO> colExecucao = new ExecucaoProblemaDao().listByIdProblema(getProblemaDto()
                .getIdProblema());
        if (colExecucao != null) {
            final ItemTrabalhoFluxoDao itemTrabalhoFluxoDao = new ItemTrabalhoFluxoDao();
            setTransacaoDao(itemTrabalhoFluxoDao);

            final OcorrenciaProblemaDAO ocorrenciaProblemaDao = new OcorrenciaProblemaDAO();
            setTransacaoDao(ocorrenciaProblemaDao);
            for (final ExecucaoProblemaDTO execucaoProblemaDto : colExecucao) {
                final InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this,
                        execucaoProblemaDto.getIdInstanciaFluxo());
                final Collection<ItemTrabalhoFluxoDTO> colItens = itemTrabalhoFluxoDao
                        .findDisponiveisByIdInstancia(execucaoProblemaDto.getIdInstanciaFluxo());
                if (colItens != null) {
                    for (final ItemTrabalhoFluxoDTO itemTrabalhoFluxoDto : colItens) {
                        final ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(instanciaFluxo,
                                itemTrabalhoFluxoDto.getIdItemTrabalho());
                        itemTrabalho.redireciona(loginUsuario, null, grupoAtendimento);

                        String ocorr = "Direcionamento da tarefa \""
                                + itemTrabalho.getElementoFluxoDto().getDocumentacao() + "\"";
                        ocorr += " para o grupo " + grupoAtendimento;

                        OcorrenciaProblemaServiceEjb.create(getProblemaDto(), itemTrabalhoFluxoDto, ocorr,
                                OrigemOcorrencia.OUTROS, CategoriaOcorrencia.Direcionamento, "não se aplica",
                                CategoriaOcorrencia.Direcionamento.getDescricao(), loginUsuario, 0, null,
                                getTransacao());
                    }
                }
            }
        }

        try {
            final String enviarNotificacao = ParametroUtil.getValor(
                    ParametroSistema.NOTIFICAR_GRUPO_RECEPCAO_SOLICITACAO, getTransacao(), "N");
            if (enviarNotificacao.equalsIgnoreCase("S")) {
                enviaEmailGrupo(Integer.parseInt(ParametroUtil.getValor(ParametroSistema.ID_MODELO_EMAIL_GRUPO_DESTINO,
                        getTransacao(), null)), grupoAtendimentoDto.getIdGrupo());
            }
        } catch (final NumberFormatException e) {
            System.out.println("Não há modelo de e-mail setado nos parâmetros.");
        }
    }

    @Override
    public void mapObjetoNegocio(final Map<String, Object> map) throws Exception {
        final ProblemaDTO problemaDto = (ProblemaDTO) objetoNegocioDto;
        final ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(problemaDto.getIdProblema(),
                getTransacao());
        problemaDto.setIdGrupoAtual(problemaAuxDto.getIdGrupoAtual());
        problemaDto.setIdGrupoNivel1(problemaAuxDto.getIdGrupoNivel1());
        problemaDto.setNomeGrupoAtual(problemaAuxDto.getNomeGrupoAtual());

        adicionaObjeto("problema", problemaDto, map);
        if (usuarioDto != null) {
            adicionaObjeto("usuario", usuarioDto, map);
        } else if (problemaDto.getUsuarioDto() != null) {
            adicionaObjeto("usuario", problemaDto.getUsuarioDto(), map);
        }

        adicionaObjeto("execucaoFluxo", this, map);
        adicionaObjeto("problemaService", new ProblemaServiceEjb(), map);
    }

    public ProblemaDTO getProblemaDto() {
        return (ProblemaDTO) objetoNegocioDto;
    }

    @Override
    public List<TarefaFluxoDTO> recuperaTarefas(final String loginUsuario) throws Exception {
        List<TarefaFluxoDTO> result = null;
        Timestamp ts1 = UtilDatas.getDataHoraAtual();
        final List<TarefaFluxoDTO> listTarefas = super.recuperaTarefas(loginUsuario);
        Timestamp ts2 = UtilDatas.getDataHoraAtual();
        double tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(ts2, ts1);
       // System.out.println("########## Tempo super.recuperaTarefas: " + tempo);
        if (listTarefas != null) {
            ts1 = ts2;
            result = new ArrayList();
            final ProblemaServiceEjb problemaService = new ProblemaServiceEjb();

            final ExecucaoProblemaDao execucaoProblemaDao = new ExecucaoProblemaDao();

            for (final TarefaFluxoDTO tarefaDto : listTarefas) {
                final ExecucaoProblemaDTO execucaoProblemaDto = execucaoProblemaDao.findByIdInstanciaFluxo(tarefaDto
                        .getIdInstancia());
                if (execucaoProblemaDto != null && execucaoProblemaDto.getIdProblema() != null) {
                    final ProblemaDTO problemaDto = problemaService.restoreAll(execucaoProblemaDto.getIdProblema(),
                            null);
                    if (problemaDto != null) {
                        tarefaDto.setProblemaDto(problemaDto);
                        result.add(tarefaDto);
                    }
                }
            }

            /*
             * Collection<ProblemaDTO> listProblemaDto = problemaService.listByTarefas(listTarefas); //SE FOR UTILIZAR
             * "IDPROBLEMAPAI" ENTAO TEM QUE DESCOMENTAR ESSES CODIGOS -> DAVID // Collection<ProblemaDTO>
             * listProblemasFilhos = problemaService.listProblemasFilhos(); if (listProblemaDto != null &&
             * !listProblemaDto.isEmpty()){ for (ProblemaDTO problemaDto : listProblemaDto){ for (TarefaFluxoDTO
             * tarefaDto : listTarefas) { if (problemaDto.getIdInstanciaFluxo().equals(tarefaDto.getIdInstancia())) {
             * boolean possuiFilho = false; if (listProblemasFilhos != null && !listProblemasFilhos.isEmpty()) { for
             * (ProblemaDTO problemaDto2 : listProblemasFilhos) { if
             * (problemaDto.getIdProblema().equals(problemaDto2.getIdProblemaPai())) { possuiFilho = true; break; } } }
             * problemaDto.setPossuiFilho(possuiFilho); tarefaDto.setProblemaDto(problemaDto);
             * tarefaDto.setDataHoraLimite(problemaDto.getDataHoraLimite()); result.add(tarefaDto); } } } }
             */
            Collections.sort(result, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC));
            ts2 = UtilDatas.getDataHoraAtual();
            tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(ts2, ts1);
            //System.out.println("########## Tempo restore problemas: " + tempo);
        }
        return result;
    }

    public List<TarefaFluxoDTO> recuperaTarefas(final String loginUsuario, final String campoOrdenacao,
            final Boolean asc) throws Exception {
        List<TarefaFluxoDTO> result = null;

        final List<TarefaFluxoDTO> listTarefas = super.recuperaTarefas(loginUsuario);
        if (listTarefas != null) {
            result = new ArrayList();
            final ProblemaServiceEjb problemaService = new ProblemaServiceEjb();
            for (final TarefaFluxoDTO tarefaDto : listTarefas) {
                final ProblemaDTO problemaDto = problemaService.restoreByIdInstanciaFluxo(tarefaDto.getIdInstancia());
                if (problemaDto != null) {
                    tarefaDto.setProblemaDto(problemaDto);
                    tarefaDto.setDataHoraLimite(problemaDto.getDataHoraLimite());
                    result.add(tarefaDto);
                }
            }
        }
        return result;
    }

    private void atualizaFaseProblema(final Integer idFase) throws Exception {
        final ProblemaDAO problemaDao = new ProblemaDAO();
        setTransacaoDao(problemaDao);
        getProblemaDto().setIdFaseAtual(idFase);
        problemaDao.updateNotNull(getProblemaDto());
    }

    @Override
    public void enviaEmail(final String identificador) throws Exception {
        if (identificador == null) {
            return;
        }

        final ModeloEmailDTO modeloEmailDto = new ModeloEmailDao().findByIdentificador(identificador);
        if (modeloEmailDto != null) {
            enviaEmail(modeloEmailDto.getIdModeloEmail());
        }
    }

    public boolean isEmailHabilitado() throws Exception {
        final String enviaEmail = ParametroUtil.getValor(ParametroSistema.EnviaEmailFluxo, getTransacao(), "N");
        return enviaEmail.equalsIgnoreCase("S");
    }

    public String getRemetenteEmail() throws Exception {
        final String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao,
                getTransacao(), null);
        if (remetente == null) {
            throw new LogicException(i18n_Message("citcorpore.comum.remetenteParaNotificacoesNaoParametrizado"));
        }
        return remetente;
    }

    public void complementaInformacoesEmail(final ProblemaDTO problemaDto) throws Exception {
        final String urlSistema = ParametroUtil.getValor(ParametroSistema.URL_Sistema, getTransacao(), "");
        final String idHashValidacao = CriptoUtils.generateHash("CODED" + problemaDto.getIdProblema(), "MD5");
        problemaDto.setLinkPesquisaSatisfacao("<a href=\"" + urlSistema
                + "/pages/pesquisaSatisfacao/pesquisaSatisfacao.load?idProblema=" + problemaDto.getIdProblema()
                + "&hash=" + idHashValidacao + "\">Clique aqui para fazer a avaliação do Atendimento</a>");
    }

    @Override
    public void enviaEmail(final String identificador, final String[] destinatarios) throws Exception {
        if (identificador == null) {
            return;
        }

        if (destinatarios == null || destinatarios.length == 0) {
            return;
        }

        if (!isEmailHabilitado()) {
            return;
        }

        final ModeloEmailDTO modeloEmailDto = new ModeloEmailDao().findByIdentificador(identificador);
        if (modeloEmailDto == null) {
            return;
        }

        final String para = destinatarios[0];
        String cc = null;
        if (destinatarios.length > 1) {
            cc = "";
            for (int i = 1; i < destinatarios.length; i++) {
                cc += destinatarios[i] + ";";
            }
        }
        final String remetente = getRemetenteEmail();

        final ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(getProblemaDto().getIdProblema(),
                getTransacao());
        problemaAuxDto.setNomeTarefa(getProblemaDto().getNomeTarefa());

        complementaInformacoesEmail(problemaAuxDto);

        final MensagemEmail mensagem = new MensagemEmail(modeloEmailDto.getIdModeloEmail(),
                new IDto[] { problemaAuxDto });
        try {
            mensagem.envia(para, cc, remetente);
        } catch (final Exception e) {
        }

    }

    @Override
    public void enviaEmail(final Integer idModeloEmail) throws Exception {
        if (idModeloEmail == null) {
            return;
        }

        if (!isEmailHabilitado()) {
            return;
        }

        final String remetente = getRemetenteEmail();

        final ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(getProblemaDto().getIdProblema(),
                getTransacao());
        if (problemaAuxDto != null) {
            problemaAuxDto.setNomeTarefa(getProblemaDto().getNomeTarefa());
            problemaAuxDto.setNomeContato(getProblemaDto().getNomeContato());
            problemaAuxDto.setEmailContato(getProblemaDto().getEmailContato());

            complementaInformacoesEmail(problemaAuxDto);

            final MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] { problemaAuxDto });
            try {
                mensagem.envia(problemaAuxDto.getEmailContato(), remetente, remetente);
            } catch (final Exception e) {
            }
        }

    }

    public void enviaEmail(final Integer idModeloEmail, final ProblemaDTO problemaDto) throws Exception {
        if (idModeloEmail == null) {
            return;
        }

        if (!isEmailHabilitado()) {
            return;
        }

        final String remetente = getRemetenteEmail();
        complementaInformacoesEmail(problemaDto);

        final MensagemEmail mensagem = new MensagemEmail(idModeloEmail, new IDto[] { problemaDto });
        try {
            mensagem.envia(problemaDto.getEmailContato(), remetente, remetente);
        } catch (final Exception e) {
        }

        // ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(getProblemaDto().getIdProblema(),
        // getTransacao());
        // if(problemaAuxDto!=null){
        // problemaAuxDto.setNomeTarefa(getProblemaDto().getNomeTarefa());
        // problemaAuxDto.setNomeContato(getProblemaDto().getNomeContato());
        // problemaAuxDto.setEmailContato(getProblemaDto().getEmailContato());
        //
        // }
        //
    }

    /**
     * Notifica todos os Empregados de um grupo.
     * 
     * @param idModeloEmail
     * @throws Exception
     */
    public void enviaEmailGrupo(final Integer idModeloEmail, final Integer idGrupoDestino) throws Exception {
        MensagemEmail mensagem = null;

        if (idGrupoDestino == null) {
            return;
        }

        if (idModeloEmail == null) {
            return;
        }

        final GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class,
                null);

        List<String> emails = null;

        try {
            emails = (List<String>) grupoService.listarEmailsPorGrupo(idGrupoDestino);
        } catch (final Exception e) {
            return;
        }

        if (emails == null || emails.isEmpty()) {
            return;
        }

        final String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao,
                getTransacao(), null);
        if (remetente == null) {
            throw new LogicException(i18n_Message("citcorpore.comum.remetenteParaNotificacoesNaoParametrizado"));
        }

        final ProblemaDTO problemaAuxDto = new ProblemaServiceEjb().restoreAll(getProblemaDto().getIdProblema(),
                getTransacao());
        if (problemaAuxDto == null) {
            return;
        }
        problemaAuxDto.setNomeTarefa(getProblemaDto().getNomeTarefa());

        try {

            // EmpregadoDTO aux = null;
            for (final String email : emails) {
                final int posArroba = email.indexOf("@");
                if (posArroba > 0 && email.substring(posArroba).contains(".")) {
                    try {
                        mensagem = new MensagemEmail(idModeloEmail, new IDto[] { problemaAuxDto });

                        // aux = (EmpregadoDTO) getEmpregadoService().restore(e);
                        // if(aux != null && aux.getEmail() != null && !aux.getEmail().trim().equalsIgnoreCase("") ){
                        mensagem.envia(email, remetente, remetente);
                    } catch (final Exception e) {
                        // faz nada
                    }
                }
                // }
            }
        } catch (final Exception e) {
        }
    }

    private ServicoContratoDTO recuperaServicoContrato() throws Exception {
        final ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
        setTransacaoDao(servicoContratoDao);
        ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();
        if (getProblemaDto().getIdServicoContrato() != null) {
            servicoContratoDto.setIdServicoContrato(getProblemaDto().getIdServicoContrato());
        } else {
            final ProblemaDAO problemaDao = new ProblemaDAO();
            setTransacaoDao(problemaDao);
            final ProblemaDTO problemaDto = (ProblemaDTO) objetoNegocioDto;
            ProblemaDTO problemaAuxDto = new ProblemaDTO();
            problemaAuxDto.setIdProblema(problemaDto.getIdProblema());
            problemaAuxDto = (ProblemaDTO) problemaDao.restore(problemaAuxDto);
            servicoContratoDto.setIdServicoContrato(problemaAuxDto.getIdServicoContrato());
        }
        servicoContratoDto = (ServicoContratoDTO) servicoContratoDao.restore(servicoContratoDto);
        if (servicoContratoDto == null) {
            System.out.print("Serviço contrato não localizado");
            throw new LogicException(i18n_Message("problema.servicoContratoNaoLocalizado"));
        }

        return servicoContratoDto;
    }

    @Override
    public void encerra() throws Exception {
        final ProblemaDTO problemaDto = getProblemaDto();
        if (problemaDto == null) {
            throw new Exception(i18n_Message("problema.naoEncontrado"));
        }

        if (problemaDto.encerrada()) {
            return;
        }

        validaEncerramento();

        final Collection<ExecucaoProblemaDTO> colExecucao = new ExecucaoProblemaDao().listByIdProblema(getProblemaDto()
                .getIdProblema());
        if (colExecucao != null) {
            for (final ExecucaoProblemaDTO execucaoProblemaDto : colExecucao) {
                final InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this,
                        execucaoProblemaDto.getIdInstanciaFluxo());
                instanciaFluxo.encerra();
            }
        }

        if (!problemaDto.getStatus().equalsIgnoreCase(SituacaoRequisicaoProblema.Cancelada.getDescricao())) {
            problemaDto.setStatus(SituacaoRequisicaoProblema.Concluida.getDescricao());
        }

        problemaDto.setDataHoraFim(UtilDatas.getDataHoraAtual());
        calculaTempoCaptura(problemaDto);
        calculaTempoAtendimento(problemaDto);
        calculaTempoAtraso(problemaDto);
        final ProblemaDAO problemaDao = new ProblemaDAO();
        setTransacaoDao(problemaDao);
        problemaDao.updateNotNull(problemaDto);

        OcorrenciaProblemaServiceEjb.create(getProblemaDto(), null, null, OrigemOcorrencia.OUTROS,
                CategoriaOcorrencia.Encerramento, null, CategoriaOcorrencia.Encerramento.getDescricao(), "Automático",
                0, null, getTransacao());

        if (getProblemaDto().getEnviaEmailFinalizacao() != null
                && getProblemaDto().getEnviaEmailFinalizacao().equalsIgnoreCase("S")) {
            final String IdModeloEmailProblemaFinalizado = ParametroUtil.getValorParametroCitSmartHashMap(
                    ParametroSistema.ID_MODELO_EMAIL_FINALIZADO_PROBLEMA, "36");
            enviaEmail(Integer.parseInt(IdModeloEmailProblemaFinalizado.trim()), problemaDto);
            // O metodo encerra esta sendo rodado duas vezes, para evitar que o email seja enviado duas vezes tambem,
            // modifiquei o valor do atributo para que nao seja enviado na segunda vez, é apenas uma soluçao de contorno
            // sendo necessario descobrir
            // porque o fluxo chama essa metodo duas vezes e se o mesmo é necessario, no momento isso não esta causando
            // nenhum outro erro aparente.
            getProblemaDto().setEnviaEmailFinalizacao("N");
        }

    }

    // VOLTAR PARA ARRUMAR ESSE METODO POSTERIORMENTE, SE NECESSÁRIO -> DAVID

    // @Override
    @Override
    public void reabre(final String loginUsuario) throws Exception {
        /*
         * ProblemaDTO problemaDto = getProblemaDto(); if (problemaDto == null) throw new
         * Exception("Problema não encontrado"); if (!problemaDto.encerrada()) throw new
         * Exception("Problema não permite reabertura"); usuarioDto = new UsuarioDao().restoreByLogin(loginUsuario); int
         * seqReabertura = 1; ReaberturaSolicitacaoDao reaberturaSolicitacaoDao = new ReaberturaSolicitacaoDao();
         * setTransacaoDao(reaberturaSolicitacaoDao); Collection colReabertura =
         * reaberturaSolicitacaoDao.findByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico()); if
         * (colReabertura != null) seqReabertura = colReabertura.size() + 1; ReaberturaSolicitacaoDTO
         * reaberturaSolicitacaoDto = new ReaberturaSolicitacaoDTO();
         * reaberturaSolicitacaoDto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
         * reaberturaSolicitacaoDto.setSeqReabertura(seqReabertura);
         * reaberturaSolicitacaoDto.setIdResponsavel(usuarioDto.getIdUsuario());
         * reaberturaSolicitacaoDto.setDataHora(UtilDatas.getDataHoraAtual());
         * reaberturaSolicitacaoDao.create(reaberturaSolicitacaoDto);
         * solicitacaoServicoDto.setSituacao(SituacaoSolicitacaoServico.Reaberta.name());
         * solicitacaoServicoDto.setSeqReabertura(new Integer(seqReabertura)); //
         * solicitacaoServicoDto.setIdGrupoAtual(null); SolicitacaoServicoDao solicitacaoDao = new
         * SolicitacaoServicoDao(); setTransacaoDao(solicitacaoDao); solicitacaoDao.update(solicitacaoServicoDto);
         * OcorrenciaSolicitacaoServiceEjb.create(getSolicitacaoServicoDto(), null, null, OrigemOcorrencia.OUTROS,
         * CategoriaOcorrencia.Reabertura, null, CategoriaOcorrencia.Reabertura.getDescricao(), usuarioDto.getLogin(),
         * 0, null, getTransacao()); inicia();
         */
    }

    @Override
    public void suspende(final String loginUsuario) throws Exception {
        final ProblemaDTO problemaDto = getProblemaDto();
        if (problemaDto == null) {
            throw new Exception(i18n_Message("problema.naoEncontrado"));
        }

        // if (!problemaDto.emAtendimento())
        // throw new Exception("Problema não permite suspensão");

        final Timestamp tsAtual = UtilDatas.getDataHoraAtual();

        problemaDto.setDataHoraSuspensao(UtilDatas.getDataHoraAtual());
        problemaDto.setDataHoraReativacao(null);
        // suspendeSLA(problemaDto);

        final ProblemaDAO problemaDao = new ProblemaDAO();
        setTransacaoDao(problemaDao);
        problemaDto.setStatus(SituacaoProblema.Suspensa.name());
        problemaDao.update(problemaDto);

        final JustificativaProblemaDTO justificativaProblemaDto = new JustificativaProblemaDTO();
        justificativaProblemaDto.setIdJustificativaProblema(problemaDto.getIdJustificativaProblema());
        justificativaProblemaDto.setDescricaoProblema(problemaDto.getComplementoJustificativa());

        OcorrenciaProblemaServiceEjb.create(getProblemaDto(), null, null, OrigemOcorrencia.OUTROS,
                CategoriaOcorrencia.Suspensao, null, CategoriaOcorrencia.Suspensao.getDescricao(), loginUsuario, 0,
                justificativaProblemaDto, getTransacao());
    }

    @Override
    public void reativa(final String loginUsuario) throws Exception {
        final ProblemaDTO problemaDto = getProblemaDto();
        if (problemaDto == null) {
            throw new Exception(i18n_Message("problema.naoEncontrado"));
        }

        if (!problemaDto.suspensa()) {
            throw new Exception(i18n_Message("problema.naoPermiteReativacao"));
        }

        final ProblemaDAO problemaDao = new ProblemaDAO();
        setTransacaoDao(problemaDao);

        final Timestamp tsAtual = UtilDatas.getDataHoraAtual();

        problemaDto.setStatus(SituacaoProblema.EmAndamento.getDescricao());
        problemaDto.setDataHoraSuspensao(null);
        problemaDto.setDataHoraReativacao(tsAtual);
        // reativaSLA(problemaDto);
        problemaDao.update(problemaDto);

        OcorrenciaProblemaServiceEjb.create(getProblemaDto(), null, null, OrigemOcorrencia.OUTROS,
                CategoriaOcorrencia.Reativacao, null, CategoriaOcorrencia.Reativacao.getDescricao(), loginUsuario, 0,
                null, getTransacao());
    }

    private Integer getIdCalendario(final ProblemaDTO problemaDto) throws Exception {
        Integer idCalendario = problemaDto.getIdCalendario();
        if (problemaDto.getIdCalendario() == null) {
            final ServicoContratoDTO servicoContratoDto = new ServicoContratoDao().findByIdContratoAndIdServico(
                    problemaDto.getIdContrato(), problemaDto.getIdServico());
            if (servicoContratoDto == null) {
                System.out.print("Serviço contrato não localizado");
                throw new LogicException(i18n_Message("problema.servicoContratoNaoLocalizado"));
            }
            idCalendario = servicoContratoDto.getIdCalendario();
        }
        return idCalendario;
    }

    public void calculaTempoCaptura(final ProblemaDTO problemaDto) throws Exception {
        problemaDto.setTempoCapturaHH(0);
        problemaDto.setTempoCapturaMM(0);

        if (problemaDto.getDataHoraCaptura() == null) {
            return;
        }

        if (problemaDto.getDataHoraInicioSLA() == null) {
            return;
        }

        if (problemaDto.getDataHoraInicioSLA().compareTo(problemaDto.getDataHoraCaptura()) > 0) {
            return;
        }

        final Integer idCalendario = getIdCalendario(problemaDto);

        CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, problemaDto.getDataHoraInicioSLA());
        calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, problemaDto.getDataHoraCaptura(), null);

        problemaDto.setTempoCapturaHH(calculoDto.getTempoDecorridoHH().intValue());
        problemaDto.setTempoCapturaMM(calculoDto.getTempoDecorridoMM().intValue());
    }

    public void calculaTempoAtendimento(final ProblemaDTO problemaDto) throws Exception {
        if (problemaDto.getDataHoraInicioSLA() == null) {
            return;
        }

        final Integer idCalendario = getIdCalendario(problemaDto);

        Timestamp tsAtual = UtilDatas.getDataHoraAtual();
        if (problemaDto.getStatus().equalsIgnoreCase(SituacaoProblema.Fechada.name())) {
            tsAtual = problemaDto.getDataHoraFim();
        }

        Timestamp tsInicial = problemaDto.getDataHoraInicioSLA();
        if (problemaDto.getDataHoraReativacao() != null) {
            tsInicial = problemaDto.getDataHoraReativacao();
        }

        CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, tsInicial);
        calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, tsAtual, null);

        if (problemaDto.getTempoDecorridoHH() == null) {
            problemaDto.setTempoDecorridoHH(0);
        }
        if (problemaDto.getTempoDecorridoMM() == null) {
            problemaDto.setTempoDecorridoMM(0);
        }

        problemaDto.setTempoAtendimentoHH(new Integer(problemaDto.getTempoDecorridoHH().intValue()
                + calculoDto.getTempoDecorridoHH().intValue()));
        problemaDto.setTempoAtendimentoMM(new Integer(problemaDto.getTempoDecorridoMM().intValue()
                + calculoDto.getTempoDecorridoMM().intValue()));
    }

    public void calculaTempoAtraso(final ProblemaDTO problemaDto) throws Exception {
        problemaDto.setTempoAtrasoHH(0);
        problemaDto.setTempoAtrasoMM(0);
        if (problemaDto.getDataHoraLimite() != null) {
            final Timestamp dataHoraLimite = problemaDto.getDataHoraLimite();
            Timestamp dataHoraComparacao = UtilDatas.getDataHoraAtual();
            if (problemaDto.encerrada()) {
                dataHoraComparacao = problemaDto.getDataHoraFim();
            }
            if (dataHoraComparacao.compareTo(dataHoraLimite) > 0) {
                final long atrasoSLA = UtilDatas
                        .calculaDiferencaTempoEmMilisegundos(dataHoraComparacao, dataHoraLimite) / 1000;

                final String hora = Util.getHoraStr(new Double(atrasoSLA) / 3600);
                final int tam = hora.length();
                problemaDto.setTempoAtrasoHH(new Integer(hora.substring(0, tam - 2)));
                problemaDto.setTempoAtrasoMM(new Integer(hora.substring(tam - 2, tam)));
            }
        }
    }

    public EmpregadoDTO recuperaSolicitante(final ProblemaDTO problemaDto) throws Exception {
        if (problemaDto == null || problemaDto.getIdSolicitante() == null) {
            return null;
        }

        return new EmpregadoDao().restoreByIdEmpregado(problemaDto.getIdSolicitante());
    }

    public StringBuilder recuperaLoginResponsaveis() throws Exception {
        final StringBuilder result = new StringBuilder();
        final ProblemaDTO problemaDto = getProblemaDto();
        final UsuarioDao usuarioDao = new UsuarioDao();
        UsuarioDTO usuarioDto = usuarioDao.restoreByIdEmpregado(problemaDto.getIdSolicitante());
        if (usuarioDto != null) {
            result.append(usuarioDto.getLogin());
        }
        usuarioDto = usuarioDao.restoreByIdEmpregado(problemaDto.getIdResponsavel());
        if (usuarioDto != null) {
            if (result.length() > 0) {
                result.append(";");
            }
            result.append(usuarioDto.getLogin());
        }
        return result;
    }

    @Override
    public void executaEvento(final EventoFluxoDTO eventoFluxoDto) throws Exception {
        final ProblemaDAO problemaDao = new ProblemaDAO();
        final ProblemaDTO problemaDto = problemaDao.restoreByIdInstanciaFluxo(eventoFluxoDto.getIdInstancia());
        if (problemaDto == null) {
            System.out.println("Execução problemas do evento " + eventoFluxoDto.getIdItemTrabalho()
                    + " não encontrados");
            throw new LogicException(i18n_Message("problema.eventoNaoEncontrado"));

        }

        final TransactionControler tc = new TransactionControlerImpl(problemaDao.getAliasDB());
        setTransacao(tc);
        try {
            tc.start();

            setObjetoNegocioDto(problemaDto);
            final InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, eventoFluxoDto.getIdInstancia());

            final HashMap<String, Object> map = instanciaFluxo.getMapObj();
            instanciaFluxo.getObjetos(map);
            mapObjetoNegocio(map);
            instanciaFluxo.executaEvento(eventoFluxoDto.getIdItemTrabalho(), map);

            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            throw new ServiceException(e);
        } finally {
        	try {
				tc.close();
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
    }

    public void cancelaTarefa(final String loginUsuario, final ProblemaDTO problemaDto,
            final TarefaFluxoDTO tarefaFluxoDto, final String motivo) throws Exception {
        final InstanciaFluxo instanciaFluxo = new InstanciaFluxo(this, tarefaFluxoDto.getIdInstancia());
        instanciaFluxo.cancelaItemTrabalho(loginUsuario, tarefaFluxoDto.getIdItemTrabalho());

        String ocorrencia = "Cancelamento da tarefa \"" + tarefaFluxoDto.getElementoFluxoDto().getDocumentacao() + "\"";
        if (motivo != null && motivo.trim().length() > 0) {
            ocorrencia += ". Motivo: " + motivo;
        }

        Long tempo = new Long(0);
        if (tarefaFluxoDto.getDataHoraFinalizacao() != null) {
            tempo = (tarefaFluxoDto.getDataHoraFinalizacao().getTime() - tarefaFluxoDto.getDataHoraCriacao().getTime()) / 1000 / 60;
        }

        OcorrenciaProblemaServiceEjb.create(getProblemaDto(), tarefaFluxoDto, ocorrencia, OrigemOcorrencia.OUTROS,
                CategoriaOcorrencia.CancelamentoTarefa, "não se aplica",
                CategoriaOcorrencia.CancelamentoTarefa.getDescricao(), "Sistema", tempo.intValue(), null,
                getTransacao());
    }

    @Override
    public void validaEncerramento() throws Exception {
    }

    public String recuperaGrupoAprovador() throws Exception {
        final ProblemaDTO problemaDto = getProblemaDto();
        if (problemaDto == null) {
            throw new Exception(i18n_Message("problema.naoEncontrado"));
        }

        final ServicoContratoDTO servicoContratoDto = recuperaServicoContrato();
        if (servicoContratoDto.getIdGrupoAprovador() == null) {
            throw new Exception(i18n_Message("citcorpore.comum.grupoaprovadoNaoParametrizado"));
        }

        final GrupoDao grupoDao = new GrupoDao();
        setTransacaoDao(grupoDao);
        GrupoDTO grupoDto = new GrupoDTO();
        grupoDto.setIdGrupo(servicoContratoDto.getIdGrupoAprovador());
        grupoDto = (GrupoDTO) grupoDao.restore(grupoDto);
        if (grupoDto == null) {
            throw new Exception(i18n_Message("citcorpore.comum.grupoaprovadoNaoParametrizado"));
        }

        return grupoDto.getSigla();
    }

    @Override
    public void verificaSLA(final ItemTrabalho itemTrabalho) throws Exception {
        /*
         * ProblemaDTO problemaDto = getProblemaDto(); if (problemaDto == null) throw new
         * Exception("Problema não encontrado"); boolean bContabilizaSLA = true; if (itemTrabalho.getContabilizaSLA() !=
         * null) bContabilizaSLA = itemTrabalho.getContabilizaSLA().equalsIgnoreCase("S"); boolean bGravar = false; if
         * (bContabilizaSLA) { if (problemaDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.N.name())) {
         * iniciaSLA(problemaDto); bGravar = true; }if
         * (problemaDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.S.name())) { reativaSLA(problemaDto); bGravar =
         * true; } }else if (problemaDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.A.name())) {
         * suspendeSLA(problemaDto); bGravar = true; } if (bGravar) { ProblemaDAO problemaDao = new ProblemaDAO();
         * problemaDao.setTransactionControler(getTransacao()); problemaDao.updateNotNull(problemaDto); }
         */
    }

    public void determinaPrazoLimite(final ProblemaDTO problemaoDto, Integer idCalendario) throws Exception {
        if (problemaoDto.getDataHoraInicioSLA() == null) {
            return;
        }

        if (idCalendario == null || idCalendario.intValue() == 0) {
            final ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
            servicoContratoDao.setTransactionControler(getTransacao());
            final ServicoContratoDTO servicoContratoDto = servicoContratoDao.findByIdContratoAndIdServico(
                    problemaoDto.getIdContrato(), problemaoDto.getIdServico());
            if (servicoContratoDto == null) {
                throw new LogicException(i18n_Message(problemaoDto.getUsuarioDto(),
                        "solicitacaoservico.validacao.servicolocalizado"));
            }
            idCalendario = servicoContratoDto.getIdCalendario();
        }

        if (problemaoDto.getPrazoHH() == null) {
            problemaoDto.setPrazoHH(0);
        }
        if (problemaoDto.getPrazoMM() == null) {
            problemaoDto.setPrazoMM(0);
        }
        CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(idCalendario, problemaoDto.getDataHoraInicioSLA(),
                problemaoDto.getPrazoHH(), problemaoDto.getPrazoMM());
        calculoDto = new CalendarioServiceEjb().calculaDataHoraFinal(calculoDto, true, null);
        problemaoDto.setDataHoraLimite(calculoDto.getDataHoraFinal());
    }

    public void iniciaSLA(final ProblemaDTO problemaDto) throws Exception {
        if (!problemaDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.N.name())) {
            return;
        }

        problemaDto.setDataHoraInicioSLA(UtilDatas.getDataHoraAtual());
        problemaDto.setSituacaoSLA(SituacaoSLA.A.name());
        determinaPrazoLimite(problemaDto, null);

        OcorrenciaProblemaServiceEjb.create(getProblemaDto(), null, null, OrigemOcorrencia.OUTROS,
                CategoriaOcorrencia.InicioSLA, null, CategoriaOcorrencia.InicioSLA.getDescricao(), "Automático", 0,
                null, getTransacao());
    }

    public void suspendeSLA(final ProblemaDTO problemaDto) throws Exception {
        if (!problemaDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.A.name())) {
            return;
        }

        final Timestamp tsAtual = UtilDatas.getDataHoraAtual();
        Timestamp tsInicial = problemaDto.getDataHoraInicioSLA();
        if (problemaDto.getDataHoraReativacaoSLA() != null) {
            tsInicial = problemaDto.getDataHoraReativacao();
        }
        CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(problemaDto.getIdCalendario(), tsInicial);
        calculoDto = new CalendarioServiceEjb().calculaPrazoDecorrido(calculoDto, tsAtual, null);

        if (problemaDto.getTempoDecorridoHH() == null) {
            problemaDto.setTempoDecorridoHH(0);
        }
        if (problemaDto.getTempoDecorridoMM() == null) {
            problemaDto.setTempoDecorridoMM(0);
        }

        problemaDto.setSituacaoSLA(SituacaoSLA.S.name());
        problemaDto.setTempoDecorridoHH(new Integer(problemaDto.getTempoDecorridoHH().intValue()
                + calculoDto.getTempoDecorridoHH().intValue()));
        problemaDto.setTempoDecorridoMM(new Integer(problemaDto.getTempoDecorridoMM().intValue()
                + calculoDto.getTempoDecorridoMM().intValue()));
        problemaDto.setDataHoraSuspensaoSLA(tsAtual);
        problemaDto.setDataHoraReativacaoSLA(null);

        OcorrenciaProblemaServiceEjb.create(getProblemaDto(), null, null, OrigemOcorrencia.OUTROS,
                CategoriaOcorrencia.SuspensaoSLA, null, CategoriaOcorrencia.SuspensaoSLA.getDescricao(), "Automático",
                0, null, getTransacao());
    }

    public void reativaSLA(final ProblemaDTO problemaDto) throws Exception {
        if (!problemaDto.getSituacaoSLA().equalsIgnoreCase(SituacaoSLA.S.name())) {
            return;
        }

        final Timestamp tsAtual = UtilDatas.getDataHoraAtual();
        double prazo = problemaDto.getPrazoHH() + new Double(problemaDto.getPrazoMM()).doubleValue() / 60;
        final double tempo = problemaDto.getTempoDecorridoHH()
                + new Double(problemaDto.getTempoDecorridoMM()).doubleValue() / 60;
        prazo = prazo - tempo;
        if (prazo > 0) {
            CalculoJornadaDTO calculoDto = new CalculoJornadaDTO(problemaDto.getIdCalendario(), tsAtual,
                    Util.getHora(prazo), Util.getMinuto(prazo));

            calculoDto = new CalendarioServiceEjb().calculaDataHoraFinal(calculoDto, false, null);
            problemaDto.setDataHoraLimite(calculoDto.getDataHoraFinal());
        }

        problemaDto.setSituacaoSLA(SituacaoSLA.A.name());
        problemaDto.setDataHoraSuspensaoSLA(null);
        problemaDto.setDataHoraReativacaoSLA(tsAtual);

        OcorrenciaProblemaServiceEjb.create(getProblemaDto(), null, null, OrigemOcorrencia.OUTROS,
                CategoriaOcorrencia.ReativacaoSLA, null, CategoriaOcorrencia.ReativacaoSLA.getDescricao(),
                "Automático", 0, null, getTransacao());
    }

    /**
     * Método que consulta se um problema precisa ou não de solução de contorno.
     * 
     * @author thiagomonteiro
     * @return true ou false
     * @throws Exception
     */
    public boolean precisaSolucaoContorno() throws Exception {

        final ProblemaDTO problemaDTO = getProblemaDto();

        if (problemaDTO.getPrecisaSolucaoContorno() != null
                && problemaDTO.getPrecisaSolucaoContorno().equalsIgnoreCase("S")) {
            problemaDTO.setFase(FaseRequisicaoProblema.SolucaoContorno.name());
            final SituacaoRequisicaoProblema novaSituacao = FaseRequisicaoProblema.valueOf(getProblemaDto().getFase())
                    .getSituacao();
            if (novaSituacao != null) {
                final ProblemaDAO problemaDao = new ProblemaDAO();
                setTransacaoDao(problemaDao);
                getProblemaDto().setStatus(novaSituacao.getDescricao());
                problemaDao.updateNotNull(getProblemaDto());
            }
        } else {
            problemaDTO.setFase(FaseRequisicaoProblema.Resolucao.name());
            final SituacaoRequisicaoProblema novaSituacao = FaseRequisicaoProblema.valueOf(getProblemaDto().getFase())
                    .getSituacao();
            if (novaSituacao != null) {
                final ProblemaDAO problemaDao = new ProblemaDAO();
                setTransacaoDao(problemaDao);
                getProblemaDto().setStatus(novaSituacao.getDescricao());
                problemaDao.updateNotNull(getProblemaDto());
            }
        }

        return problemaDTO.getPrecisaSolucaoContorno() != null
                && problemaDTO.getPrecisaSolucaoContorno().equalsIgnoreCase("S");
    }

    /**
     * Método que consulta se um problema precisa ou não de uma mudança para ser solucionado.
     * 
     * @author thiagomonteiro
     * @return true ou false
     * @throws Exception
     */
    public boolean precisaMudanca() throws Exception {

        final ProblemaDTO problemaDTO = getProblemaDto();

        return problemaDTO.getIdProblemaMudanca() != null && problemaDTO.getIdProblemaMudanca() > 0;
    }

    /**
     * Método que consulta se um problema foi resolvido ou não.
     * 
     * @author thiagomonteiro
     * @return true ou false
     * @throws Exception
     */
    public boolean resolvido() throws Exception {

        final ProblemaDTO problemaDTO = getProblemaDto();

        if (problemaDTO.getResolvido() != null && problemaDTO.getResolvido().equalsIgnoreCase("S")) {
            problemaDTO.setFase(FaseRequisicaoProblema.Encerramento.name());
            final SituacaoRequisicaoProblema novaSituacao = FaseRequisicaoProblema.valueOf(getProblemaDto().getFase())
                    .getSituacao();
            if (novaSituacao != null) {
                final ProblemaDAO problemaDao = new ProblemaDAO();
                setTransacaoDao(problemaDao);
                getProblemaDto().setStatus(novaSituacao.getDescricao());
                problemaDao.updateNotNull(getProblemaDto());
            }
        } else {
            problemaDTO.setFase(FaseRequisicaoProblema.Registrada.name());
            final SituacaoRequisicaoProblema novaSituacao = FaseRequisicaoProblema.valueOf(getProblemaDto().getFase())
                    .getSituacao();
            if (novaSituacao != null) {
                final ProblemaDAO problemaDao = new ProblemaDAO();
                setTransacaoDao(problemaDao);
                getProblemaDto().setStatus(novaSituacao.getDescricao());
                problemaDao.updateNotNull(getProblemaDto());
            }
        }

        return problemaDTO.getResolvido() != null && problemaDTO.getResolvido().equalsIgnoreCase("S");
    }

    public void finalizaItemRelacionadoProblema(final ProblemaDTO problemaDTO) throws ServiceException, Exception {
        new ProblemaServiceEjb().fechaRelacionamentoProblema(problemaDTO, super.getTransacao());
    }

    /**
     * Método que consulta se um problema é grave ou não.
     * 
     * @author thiagomonteiro
     * @return true ou false
     * @throws Exception
     */
    public boolean grave() throws Exception {

        final ProblemaDTO problemaDTO = getProblemaDto();

        if (problemaDTO.getGrave() != null && problemaDTO.getGrave().equalsIgnoreCase("S")) {
            problemaDTO.setFase(FaseRequisicaoProblema.Revisar.name());
            final SituacaoRequisicaoProblema novaSituacao = FaseRequisicaoProblema.valueOf(getProblemaDto().getFase())
                    .getSituacao();
            if (novaSituacao != null) {
                final ProblemaDAO problemaDao = new ProblemaDAO();
                setTransacaoDao(problemaDao);
                getProblemaDto().setStatus(novaSituacao.getDescricao());
                problemaDao.updateNotNull(getProblemaDto());
            }
        }
        return problemaDTO.getGrave() != null && problemaDTO.getGrave().equalsIgnoreCase("S");
    }

    public CategoriaProblemaDTO recuperaCategoriaProblema() throws Exception {
        final CategoriaProblemaDAO categoriaProblemaDao = new CategoriaProblemaDAO();
        setTransacaoDao(categoriaProblemaDao);
        CategoriaProblemaDTO categoriaProblemaDto = new CategoriaProblemaDTO();
        if (getProblemaDto().getIdCategoriaProblema() != null) {
            categoriaProblemaDto.setIdCategoriaProblema(getProblemaDto().getIdCategoriaProblema());
            categoriaProblemaDto = (CategoriaProblemaDTO) categoriaProblemaDao.restore(categoriaProblemaDto);
        }

        if (categoriaProblemaDto == null) {

            throw new LogicException(i18n_Message("problema.categoriaProblemaNaoLocalizado"));
        }

        return categoriaProblemaDto;
    }
}