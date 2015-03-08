package br.com.centralit.citquestionario.negocio;

import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citquestionario.bean.GrupoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.OpcaoRespostaQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioAnexosDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.centralit.citquestionario.integracao.GrupoQuestionarioDao;
import br.com.centralit.citquestionario.integracao.OpcaoRespostaQuestionarioDao;
import br.com.centralit.citquestionario.integracao.QuestaoQuestionarioDao;
import br.com.centralit.citquestionario.integracao.QuestionarioDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioAnexosDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioOpcoesDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.WebUtil;

public class QuestionarioServiceBean extends CrudServiceImpl implements QuestionarioService {

    private QuestionarioDao dao;

    @Override
    protected QuestionarioDao getDao() {
        if (dao == null) {
            dao = new QuestionarioDao();
        }
        return dao;
    }

    private void verificaAtributosQuestao(final QuestaoQuestionarioDTO questaoDto) throws Exception {
        if (questaoDto.getSigla() != null && questaoDto.getSigla().trim().equalsIgnoreCase("")) {
            questaoDto.setSigla(null);
        }
        if (questaoDto.getObrigatoria() == null || questaoDto.getObrigatoria().trim().equalsIgnoreCase("")) {
            questaoDto.setObrigatoria("N");
        }
        if (questaoDto.getInfoResposta() == null || questaoDto.getInfoResposta().trim().equalsIgnoreCase("")) {
            questaoDto.setInfoResposta("L");
        }
        if (questaoDto.getTipo() == null || questaoDto.getTipo().trim().equalsIgnoreCase("")) {
            questaoDto.setTipo("Q");
        }
        if (questaoDto.getTituloQuestaoQuestionario() == null || questaoDto.getTituloQuestaoQuestionario().trim().equalsIgnoreCase("")) {
            questaoDto.setTituloQuestaoQuestionario("  ");
        }
        if (questaoDto.getImprime() == null || questaoDto.getImprime().trim().equalsIgnoreCase("")) {
            questaoDto.setImprime("S");
        }
        if (questaoDto.getCalculada() == null || questaoDto.getCalculada().trim().equalsIgnoreCase("")) {
            questaoDto.setCalculada("N");
        }
        if (questaoDto.getEditavel() == null || questaoDto.getEditavel().trim().equalsIgnoreCase("")) {
            questaoDto.setEditavel("N");
        }
        if (questaoDto.getUltimoValor() == null || questaoDto.getUltimoValor().trim().equalsIgnoreCase("")) {
            questaoDto.setUltimoValor("N");
        }
    }

    private void validaQuestoes(final Object arg0) throws Exception {
        final QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
        final QuestionarioDTO questionarioDto = (QuestionarioDTO) arg0;
        final Iterator itGrupo = questionarioDto.getColGrupos().iterator();
        for (; itGrupo.hasNext();) {
            final GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO) itGrupo.next();

            if (grupoDto.getColQuestoes() != null) {
                final Iterator itQuestao = grupoDto.getColQuestoes().iterator();
                for (; itQuestao.hasNext();) {
                    final QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO) itQuestao.next();
                    if (questaoDto.getSigla() != null && !questaoDto.getSigla().trim().equalsIgnoreCase("")) {
                        Integer idQuestao = questaoDto.getIdQuestaoQuestionario();
                        if (idQuestao == null) {
                            idQuestao = 0;
                        }
                        Integer idQuestaoOrigem = questaoDto.getIdQuestaoOrigem();
                        if (idQuestaoOrigem == null) {
                            idQuestaoOrigem = 0;
                        }
                        final Collection colQuestoesSigla = questaoDao.listBySiglaAndIdQuestao(questaoDto.getSigla(), idQuestaoOrigem, idQuestao);
                        if (colQuestoesSigla.size() > 0) {
                            // throw new LogicException("A sigla "+questaoDto.getSigla()+" já está sendo utilizada por outra questão.");
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        this.validaQuestoes(arg0);
    }

    @Override
    protected void validaDelete(final Object arg0) throws Exception {

    }

    @Override
    protected void validaFind(final Object arg0) throws Exception {

    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        this.validaQuestoes(arg0);
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
        final GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
        final OpcaoRespostaQuestionarioDao opcaoRespostaDao = new OpcaoRespostaQuestionarioDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        final QuestionarioDTO questionarioDto = (QuestionarioDTO) model;
        try {
            // Faz validacao, caso exista.
            this.validaCreate(questionarioDto);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            questaoDao.setTransactionControler(tc);
            grupoDao.setTransactionControler(tc);
            opcaoRespostaDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            ((QuestionarioDTO) model).setAtivo("S");
            model = crudDao.create(model);
            ((QuestionarioDTO) model).setIdQuestionarioOrigem(((QuestionarioDTO) model).getIdQuestionario());
            crudDao.update(model);

            final Iterator itGrupo = questionarioDto.getColGrupos().iterator();
            int i = 1;
            for (; itGrupo.hasNext();) {
                GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO) itGrupo.next();
                grupoDto.setIdQuestionario(questionarioDto.getIdQuestionario());
                grupoDto.setOrdem(new Integer(i));
                grupoDto = (GrupoQuestionarioDTO) grupoDao.create(grupoDto);
                i++;

                if (grupoDto.getColQuestoes() != null) {
                    final Iterator itQuestao = grupoDto.getColQuestoes().iterator();
                    int sequenciaQuestaoAux = 1;
                    for (; itQuestao.hasNext();) {
                        QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO) itQuestao.next();
                        questaoDto.setSequenciaQuestao(new Integer(sequenciaQuestaoAux));
                        sequenciaQuestaoAux++;
                        questaoDto.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());

                        if (questaoDto.getTituloQuestaoQuestionario() != null) {
                            questaoDto.setIdQuestaoQuestionario(null);
                            this.verificaAtributosQuestao(questaoDto);

                            questaoDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoDto);

                            if (questaoDto.getColOpcoesResposta() != null) {
                                for (final Iterator itOpcoes = questaoDto.getColOpcoesResposta().iterator(); itOpcoes.hasNext();) {
                                    final OpcaoRespostaQuestionarioDTO opcaoRespostaDto = (OpcaoRespostaQuestionarioDTO) itOpcoes.next();
                                    opcaoRespostaDto.setIdQuestaoQuestionario(questaoDto.getIdQuestaoQuestionario());
                                    if (opcaoRespostaDto.getExibeComplemento().equalsIgnoreCase("S") && opcaoRespostaDto.getQuestaoComplementoDto() != null) {
                                        QuestaoQuestionarioDTO questaoComplementoDto = opcaoRespostaDto.getQuestaoComplementoDto();
                                        questaoComplementoDto.setIdQuestaoQuestionario(null);
                                        questaoComplementoDto.setIdGrupoQuestionario(null);
                                        questaoComplementoDto.setIdQuestaoAgrupadora(null);
                                        questaoComplementoDto.setIdQuestaoOrigem(null);
                                        questaoComplementoDto.setSequenciaQuestao(1);
                                        questaoComplementoDto.setUltimoValor(questaoDto.getUltimoValor());
                                        this.verificaAtributosQuestao(questaoComplementoDto);

                                        questaoComplementoDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoComplementoDto);
                                        opcaoRespostaDto.setIdQuestaoComplemento(questaoComplementoDto.getIdQuestaoQuestionario());
                                    }
                                    opcaoRespostaDao.create(opcaoRespostaDto);
                                }
                            }
                            if (questaoDto.getColQuestoesAgrupadas() != null) {
                                final Iterator itQuestaoAgrupada = questaoDto.getColQuestoesAgrupadas().iterator();
                                int sequenciaQuestaoAgrupada = 1;
                                for (; itQuestaoAgrupada.hasNext();) {
                                    QuestaoQuestionarioDTO questaoAgrupadaDto = (QuestaoQuestionarioDTO) itQuestaoAgrupada.next();
                                    questaoAgrupadaDto.setSequenciaQuestao(new Integer(sequenciaQuestaoAgrupada));
                                    sequenciaQuestaoAgrupada++;
                                    questaoAgrupadaDto.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    questaoAgrupadaDto.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());
                                    questaoAgrupadaDto.setUltimoValor(questaoDto.getUltimoValor());

                                    if (questaoAgrupadaDto.getTituloQuestaoQuestionario() != null) {
                                        questaoAgrupadaDto.setIdQuestaoQuestionario(null);
                                        this.verificaAtributosQuestao(questaoAgrupadaDto);

                                        questaoAgrupadaDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoAgrupadaDto);

                                        if (questaoAgrupadaDto.getColOpcoesResposta() != null) {
                                            for (final Iterator itOpcoes = questaoAgrupadaDto.getColOpcoesResposta().iterator(); itOpcoes.hasNext();) {
                                                final OpcaoRespostaQuestionarioDTO opcaoRespostaDto = (OpcaoRespostaQuestionarioDTO) itOpcoes.next();
                                                opcaoRespostaDto.setIdQuestaoQuestionario(questaoAgrupadaDto.getIdQuestaoQuestionario());

                                                opcaoRespostaDao.create(opcaoRespostaDto);
                                            }
                                        }
                                    }
                                }
                            }
                            if (questaoDto.getColCabecalhosLinha() != null) {
                                final Iterator itCabecalho = questaoDto.getColCabecalhosLinha().iterator();
                                int sequenciaCabecalho = 1;
                                for (; itCabecalho.hasNext();) {
                                    QuestaoQuestionarioDTO cabecalho = (QuestaoQuestionarioDTO) itCabecalho.next();
                                    cabecalho.setSequenciaQuestao(new Integer(sequenciaCabecalho));
                                    sequenciaCabecalho++;
                                    cabecalho.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    cabecalho.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());

                                    if (cabecalho.getTituloQuestaoQuestionario() != null) {
                                        cabecalho.setIdQuestaoQuestionario(null);
                                        this.verificaAtributosQuestao(cabecalho);

                                        cabecalho = (QuestaoQuestionarioDTO) questaoDao.create(cabecalho);
                                    }
                                }
                            }
                            if (questaoDto.getColCabecalhosColuna() != null) {
                                final Iterator itCabecalho = questaoDto.getColCabecalhosColuna().iterator();
                                int sequenciaCabecalho = 1;
                                for (; itCabecalho.hasNext();) {
                                    QuestaoQuestionarioDTO cabecalho = (QuestaoQuestionarioDTO) itCabecalho.next();
                                    cabecalho.setSequenciaQuestao(new Integer(sequenciaCabecalho));
                                    sequenciaCabecalho++;
                                    cabecalho.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    cabecalho.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());

                                    if (cabecalho.getTituloQuestaoQuestionario() != null) {
                                        cabecalho.setIdQuestaoQuestionario(null);
                                        this.verificaAtributosQuestao(cabecalho);

                                        cabecalho = (QuestaoQuestionarioDTO) questaoDao.create(cabecalho);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

            return model;
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final QuestionarioDao crudDao = new QuestionarioDao();
        final QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
        final GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
        final OpcaoRespostaQuestionarioDao opcaoRespostaDao = new OpcaoRespostaQuestionarioDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        QuestionarioDTO questionarioDto = (QuestionarioDTO) model;
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            questaoDao.setTransactionControler(tc);
            grupoDao.setTransactionControler(tc);
            opcaoRespostaDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            if (this.existeResposta(questionarioDto.getIdQuestionario())) {
                questionarioDto.setAtivo("N");
                crudDao.updateNotNull(questionarioDto);
                questionarioDto.setIdQuestionarioOrigem(questionarioDto.getIdQuestionarioOrigem());
                questionarioDto.setIdQuestionario(null);
                questionarioDto.setAtivo("S");
                questionarioDto = (QuestionarioDTO) crudDao.create(questionarioDto);
            } else {
                questionarioDto.setAtivo("S");
                crudDao.update(questionarioDto);
                final Collection colGruposRecuperado = grupoDao.listByIdQuestionario(questionarioDto.getIdQuestionario());
                final Iterator itGrupoRecuperado = colGruposRecuperado.iterator();
                for (; itGrupoRecuperado.hasNext();) {
                    final GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO) itGrupoRecuperado.next();
                    questaoDao.deleteByIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                    grupoDao.delete(grupoDto);
                }
            }

            int i = 1;
            final Iterator itGrupo = questionarioDto.getColGrupos().iterator();
            for (; itGrupo.hasNext();) {
                GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO) itGrupo.next();
                grupoDto.setIdQuestionario(questionarioDto.getIdQuestionario());
                grupoDto.setOrdem(new Integer(i));
                grupoDto = (GrupoQuestionarioDTO) grupoDao.create(grupoDto);
                i++;

                if (grupoDto.getColQuestoes() != null) {
                    final Iterator itQuestao = grupoDto.getColQuestoes().iterator();
                    int sequenciaQuestaoAux = 1;
                    for (; itQuestao.hasNext();) {
                        QuestaoQuestionarioDTO questaoDto = (QuestaoQuestionarioDTO) itQuestao.next();
                        questaoDto.setSequenciaQuestao(new Integer(sequenciaQuestaoAux));
                        sequenciaQuestaoAux++;
                        questaoDto.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());

                        if (questaoDto.getTituloQuestaoQuestionario() != null) {
                            questaoDto.setIdQuestaoQuestionario(null);
                            this.verificaAtributosQuestao(questaoDto);

                            questaoDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoDto);

                            if (questaoDto.getColOpcoesResposta() != null) {
                                for (final Iterator itOpcoes = questaoDto.getColOpcoesResposta().iterator(); itOpcoes.hasNext();) {
                                    final OpcaoRespostaQuestionarioDTO opcaoRespostaDto = (OpcaoRespostaQuestionarioDTO) itOpcoes.next();
                                    opcaoRespostaDto.setIdQuestaoQuestionario(questaoDto.getIdQuestaoQuestionario());
                                    if (opcaoRespostaDto.getExibeComplemento().equalsIgnoreCase("S") && opcaoRespostaDto.getQuestaoComplementoDto() != null) {
                                        QuestaoQuestionarioDTO questaoComplementoDto = opcaoRespostaDto.getQuestaoComplementoDto();
                                        questaoComplementoDto.setIdQuestaoQuestionario(null);
                                        questaoComplementoDto.setIdGrupoQuestionario(null);
                                        questaoComplementoDto.setIdQuestaoAgrupadora(null);
                                        questaoComplementoDto.setIdQuestaoOrigem(null);
                                        questaoComplementoDto.setSequenciaQuestao(1);
                                        questaoComplementoDto.setUltimoValor(questaoDto.getUltimoValor());
                                        this.verificaAtributosQuestao(questaoComplementoDto);

                                        questaoComplementoDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoComplementoDto);
                                        if (questaoComplementoDto.getColOpcoesResposta() != null) {
                                            for (final Iterator itOpcoesComp = questaoComplementoDto.getColOpcoesResposta().iterator(); itOpcoesComp.hasNext();) {
                                                final OpcaoRespostaQuestionarioDTO opcaoRespostaCompDto = (OpcaoRespostaQuestionarioDTO) itOpcoesComp.next();
                                                opcaoRespostaCompDto.setIdQuestaoQuestionario(questaoComplementoDto.getIdQuestaoQuestionario());
                                                opcaoRespostaDao.create(opcaoRespostaCompDto);
                                            }
                                        }
                                        opcaoRespostaDto.setIdQuestaoComplemento(questaoComplementoDto.getIdQuestaoQuestionario());
                                    }
                                    opcaoRespostaDto.setIdOpcaoRespostaQuestionario(null);
                                    opcaoRespostaDao.create(opcaoRespostaDto);
                                }
                            }
                            if (questaoDto.getColQuestoesAgrupadas() != null) {
                                final Iterator itQuestaoAgrupada = questaoDto.getColQuestoesAgrupadas().iterator();
                                int sequenciaQuestaoAgrupada = 1;
                                for (; itQuestaoAgrupada.hasNext();) {
                                    QuestaoQuestionarioDTO questaoAgrupadaDto = (QuestaoQuestionarioDTO) itQuestaoAgrupada.next();
                                    questaoAgrupadaDto.setSequenciaQuestao(new Integer(sequenciaQuestaoAgrupada));
                                    sequenciaQuestaoAgrupada++;
                                    questaoAgrupadaDto.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    questaoAgrupadaDto.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());
                                    questaoAgrupadaDto.setUltimoValor(questaoDto.getUltimoValor());

                                    if (questaoAgrupadaDto.getTituloQuestaoQuestionario() != null) {
                                        questaoAgrupadaDto.setIdQuestaoQuestionario(null);
                                        this.verificaAtributosQuestao(questaoAgrupadaDto);

                                        questaoAgrupadaDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoAgrupadaDto);

                                        if (questaoAgrupadaDto.getColOpcoesResposta() != null) {
                                            for (final Iterator itOpcoes = questaoAgrupadaDto.getColOpcoesResposta().iterator(); itOpcoes.hasNext();) {
                                                final OpcaoRespostaQuestionarioDTO opcaoRespostaDto = (OpcaoRespostaQuestionarioDTO) itOpcoes.next();
                                                opcaoRespostaDto.setIdQuestaoQuestionario(questaoAgrupadaDto.getIdQuestaoQuestionario());

                                                opcaoRespostaDao.create(opcaoRespostaDto);
                                            }
                                        }
                                    }
                                }
                            }
                            if (questaoDto.getColCabecalhosLinha() != null) {
                                final Iterator itCabecalho = questaoDto.getColCabecalhosLinha().iterator();
                                int sequenciaCabecalho = 1;
                                for (; itCabecalho.hasNext();) {
                                    QuestaoQuestionarioDTO cabecalho = (QuestaoQuestionarioDTO) itCabecalho.next();
                                    cabecalho.setSequenciaQuestao(new Integer(sequenciaCabecalho));
                                    sequenciaCabecalho++;
                                    cabecalho.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    cabecalho.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());

                                    if (cabecalho.getTituloQuestaoQuestionario() != null) {
                                        cabecalho.setIdQuestaoQuestionario(null);
                                        this.verificaAtributosQuestao(cabecalho);

                                        cabecalho = (QuestaoQuestionarioDTO) questaoDao.create(cabecalho);
                                    }
                                }
                            }
                            if (questaoDto.getColCabecalhosColuna() != null) {
                                final Iterator itCabecalho = questaoDto.getColCabecalhosColuna().iterator();
                                int sequenciaCabecalho = 1;
                                for (; itCabecalho.hasNext();) {
                                    QuestaoQuestionarioDTO cabecalho = (QuestaoQuestionarioDTO) itCabecalho.next();
                                    cabecalho.setSequenciaQuestao(new Integer(sequenciaCabecalho));
                                    sequenciaCabecalho++;
                                    cabecalho.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());
                                    cabecalho.setIdQuestaoAgrupadora(questaoDto.getIdQuestaoQuestionario());

                                    if (cabecalho.getTituloQuestaoQuestionario() != null) {
                                        cabecalho.setIdQuestaoQuestionario(null);
                                        this.verificaAtributosQuestao(cabecalho);

                                        cabecalho = (QuestaoQuestionarioDTO) questaoDao.create(cabecalho);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void updateOrdemGrupos(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        final QuestionarioDTO questionarioDto = (QuestionarioDTO) model;
        try {
            // Seta o TransactionController para os DAOs
            grupoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();
            final Iterator itGrupo = questionarioDto.getColGrupos().iterator();
            int i = 1;
            for (; itGrupo.hasNext();) {
                final GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO) itGrupo.next();
                grupoDto.setIdQuestionario(questionarioDto.getIdQuestionario());
                grupoDto.setNomeGrupoQuestionario(null);
                grupoDto.setOrdem(new Integer(i));

                grupoDao.updateOrdem(grupoDto);

                i++;
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void updateNomeGrupo(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        final QuestionarioDTO questionarioDto = (QuestionarioDTO) model;
        try {
            // Seta o TransactionController para os DAOs
            grupoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            final GrupoQuestionarioDTO grupoDto = new GrupoQuestionarioDTO();
            grupoDto.setIdGrupoQuestionario(questionarioDto.getIdGrupoQuestionario());
            grupoDto.setNomeGrupoQuestionario(questionarioDto.getNomeGrupoQuestionario());
            grupoDto.setIdQuestionario(null);
            grupoDto.setOrdem(null);

            grupoDao.updateNome(grupoDto);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    private void recuperaOpcoesResposta(final QuestaoQuestionarioDTO questDto, final Collection colRespostas) throws ServiceException, LogicException {
        final RespostaItemQuestionarioOpcoesDao respItemQuestOpDao = new RespostaItemQuestionarioOpcoesDao();
        final RespostaItemQuestionarioAnexosDao respostaItemQuestionarioAnexosDao = new RespostaItemQuestionarioAnexosDao();

        try {
            if (colRespostas != null) {
                if (colRespostas.size() > 0) {
                    for (final Iterator itAux = colRespostas.iterator(); itAux.hasNext();) {
                        final RespostaItemQuestionarioDTO respostaItemDto = (RespostaItemQuestionarioDTO) itAux.next();
                        if (colRespostas.size() == 1) {
                            questDto.setRespostaItemDto(respostaItemDto);
                        }
                        final Collection colOpcoesResposta = respItemQuestOpDao.getRespostasOpcoesByIdRespostaItemQuestionario(respostaItemDto.getIdRespostaItemQuestionario());
                        respostaItemDto.setColOpcoesResposta(colOpcoesResposta);

                        if (questDto.getTipoQuestao().equalsIgnoreCase("M")) { // Galeria Multimidia
                            final Collection colRelacaoAnexos = respostaItemQuestionarioAnexosDao.listByIdRespostaItemQuestionario(respostaItemDto.getIdRespostaItemQuestionario());
                            if (colRelacaoAnexos != null) {
                                final ControleGEDDao controleGedDao = new ControleGEDDao();
                                for (final Iterator itAnexos = colRelacaoAnexos.iterator(); itAnexos.hasNext();) {
                                    final RespostaItemQuestionarioAnexosDTO respAnexoDto = (RespostaItemQuestionarioAnexosDTO) itAnexos.next();
                                    ControleGEDDTO controleGedDto = new ControleGEDDTO();
                                    final String idControleGEDStr = respAnexoDto.getCaminhoAnexo();
                                    if (idControleGEDStr != null) {
                                        Integer idControleGED;
                                        try {
                                            idControleGED = new Integer(idControleGEDStr);
                                        } catch (final Exception e) {
                                            idControleGED = new Integer(0);
                                        }
                                        controleGedDto.setIdControleGED(idControleGED);
                                        controleGedDto = (ControleGEDDTO) controleGedDao.restore(controleGedDto);
                                        if (controleGedDto != null) {
                                            respAnexoDto.setNomeArquivo(controleGedDto.getNomeArquivo());
                                            respAnexoDto.setIdControleGED(controleGedDto.getIdControleGED());
                                        }
                                    }
                                }
                            }
                            respostaItemDto.setColRelacaoAnexos(colRelacaoAnexos);
                        }
                    }
                    questDto.setSerializeRespostas(WebUtil.serializeObjects(colRespostas));
                    questDto.setColRespostas(colRespostas);
                }
            }
        } catch (final LogicException e) {
            throw new ServiceException(e);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    private void obtemRespostaQuestao(final QuestaoQuestionarioDTO questDto, final Integer idIdentificadorResposta) throws ServiceException, LogicException {
        final RespostaItemQuestionarioDao respostaDao = new RespostaItemQuestionarioDao();

        try {
            Collection colRespostas = null;
            if (questDto.getTipo().equalsIgnoreCase("L")) {
                colRespostas = respostaDao.listByIdIdentificadorAndIdTabela(idIdentificadorResposta, questDto.getIdQuestaoQuestionario());
            } else {
                colRespostas = respostaDao.listByIdIdentificadorAndIdQuestao(idIdentificadorResposta, questDto.getIdQuestaoQuestionario());
            }
            this.recuperaOpcoesResposta(questDto, colRespostas);
        } catch (final LogicException e) {
            throw new ServiceException(e);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    private void obtemUltimaResposta(final QuestaoQuestionarioDTO questDto, final Integer idSolicitacaoServico) throws ServiceException, LogicException {
        /*
         * if (questDto.getUltimoValor().equalsIgnoreCase("N")) {
         * return;
         * }
         * RespostaItemQuestionarioDao respostaDao = new RespostaItemQuestionarioDao();
         * QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
         * try {
         * Collection colRespostas = new ArrayList();
         * if (questDto.getTipo().equalsIgnoreCase("L") || questDto.getTipo().equalsIgnoreCase("M")) {
         * if (questDto.getColQuestoesAgrupadas() != null && questDto.getColQuestoesAgrupadas().size() > 0) {
         * // recupera a última questao respondida da matriz ou tabela
         * RespostaItemQuestionarioDTO respostaItemDto = respostaDao.getUltimaRespostaBySiglaQuestaoAgrupadora(questDto.getSigla(), idPessoa);
         * if (respostaItemDto != null) {
         * Collection colRespQuestaoAgrupada = null;
         * QuestaoQuestionarioDTO questaoAuxDto = new QuestaoQuestionarioDTO();
         * // recupera as respostas da ultima matriz ou tabela
         * questaoAuxDto.setIdQuestaoQuestionario(respostaItemDto.getIdQuestaoQuestionario());
         * questaoAuxDto = (QuestaoQuestionarioDTO) questaoDao.restore(questaoAuxDto);
         * colRespQuestaoAgrupada = respostaDao.listByIdIdentificadorAndIdTabela(respostaItemDto.getIdIdentificadorResposta(), questaoAuxDto.getIdQuestaoAgrupadora());
         * if (colRespQuestaoAgrupada != null && colRespQuestaoAgrupada.size() > 0) {
         * for (Iterator itResp = colRespQuestaoAgrupada.iterator(); itResp.hasNext();){
         * RespostaItemQuestionarioDTO respQuestaoDto = (RespostaItemQuestionarioDTO) itResp.next();
         * questaoAuxDto.setIdQuestaoQuestionario(respQuestaoDto.getIdQuestaoQuestionario());
         * questaoAuxDto = (QuestaoQuestionarioDTO) questaoDao.restore(questaoAuxDto);
         * if (questaoAuxDto.getSigla() != null) {
         * // localiza a questão com a mesma sigla
         * for (Iterator itQuest = questDto.getColQuestoesAgrupadas().iterator(); itQuest.hasNext();){
         * QuestaoQuestionarioDTO questaoAgrupadaDto = (QuestaoQuestionarioDTO) itQuest.next();
         * if (questaoAgrupadaDto.getSigla() != null && questaoAgrupadaDto.getSigla().equalsIgnoreCase(questaoAuxDto.getSigla())) {
         * respQuestaoDto.setIdQuestaoQuestionario(questaoAgrupadaDto.getIdQuestaoQuestionario());
         * if (questaoAgrupadaDto.getColRespostas() == null) {
         * questaoAgrupadaDto.setColRespostas(new ArrayList());
         * }
         * questaoAgrupadaDto.getColRespostas().add(respQuestaoDto);
         * if (questDto.getTipo().equalsIgnoreCase("M")) {
         * questaoAgrupadaDto.setRespostaItemDto(respQuestaoDto);
         * }
         * colRespostas.add(respQuestaoDto);
         * break;
         * }
         * }
         * }
         * }
         * }
         * }
         * }
         * }else{
         * // recupera a última resposta dos outros tipos de questão
         * RespostaItemQuestionarioDTO respostaItemDto = null;
         * if (questDto.getUltimoValor().equalsIgnoreCase("S")) {
         * respostaItemDto = respostaDao.getUltimaRespostaByIdQuestao(questDto.getIdQuestaoOrigem(), idPessoa);
         * }else if (questDto.getSigla() != null){
         * respostaItemDto = respostaDao.getUltimaRespostaBySiglaQuestao(questDto.getSigla(), idPessoa);
         * }
         * if (respostaItemDto != null) {
         * // associa a resposta à questão
         * respostaItemDto.setIdQuestaoQuestionario(questDto.getIdQuestaoQuestionario());
         * colRespostas.add(respostaItemDto);
         * }
         * }
         * // monta as opções de resposta
         * recuperaOpcoesResposta(questDto, colRespostas);
         * // limpa o identificador e id da resposta
         * for (Iterator itAux = colRespostas.iterator(); itAux.hasNext();){
         * RespostaItemQuestionarioDTO respostaItemDto = (RespostaItemQuestionarioDTO)itAux.next();
         * respostaItemDto.setIdIdentificadorResposta(null);
         * respostaItemDto.setIdRespostaItemQuestionario(null);
         * }
         * }catch(LogicException e){
         * throw new ServiceException(e);
         * }catch(Exception e){
         * throw new ServiceException(e);
         * }
         */
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        final GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
        final QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
        final OpcaoRespostaQuestionarioDao opcaoRespostaDao = new OpcaoRespostaQuestionarioDao();
        final RespostaItemQuestionarioDao respostaDao = new RespostaItemQuestionarioDao();
        final RespostaItemQuestionarioOpcoesDao respItemQuestOpDao = new RespostaItemQuestionarioOpcoesDao();
        QuestionarioDTO questionarioDto = null;

        final QuestionarioDTO questionarioDtoParm = (QuestionarioDTO) model;
        try {
            if (questionarioDtoParm.getIdIdentificadorResposta() == null && questionarioDtoParm.getIdQuestionarioOrigem() != null) {
                questionarioDto = this.restoreByIdOrigem(questionarioDtoParm.getIdQuestionarioOrigem());
            }

            if (questionarioDto == null) {
                questionarioDto = (QuestionarioDTO) this.getDao().restore(model);
            }

            if (questionarioDto == null) {
                return null;
            }

            final Collection colGruposRecuperado = grupoDao.listByIdQuestionario(questionarioDto.getIdQuestionario());
            if(colGruposRecuperado == null){
            	return null;
            }
        	questionarioDto.setColGrupos(colGruposRecuperado);

            final Iterator it = colGruposRecuperado.iterator();
            for (; it.hasNext();) {
                final GrupoQuestionarioDTO grupoDto = (GrupoQuestionarioDTO) it.next();

                final Collection colQuestoes = questaoDao.listByIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());

                if (colQuestoes != null) {
                    final Iterator itQuestoes = colQuestoes.iterator();
                    for (; itQuestoes.hasNext();) {
                        final QuestaoQuestionarioDTO questDto = (QuestaoQuestionarioDTO) itQuestoes.next();
                        if (questDto.getValorPermitido1() == null) {
                            questDto.setValorPermitido1(new Double(0));
                        }
                        if (questDto.getValorPermitido2() == null) {
                            questDto.setValorPermitido2(new Double(0));
                        }

                        final Collection colOpcoesResposta = opcaoRespostaDao.listByIdQuestaoQuestionario(questDto.getIdQuestaoQuestionario());
                        if (colOpcoesResposta != null) {
                            for (final Iterator itOpcao = colOpcoesResposta.iterator(); itOpcao.hasNext();) {
                                final OpcaoRespostaQuestionarioDTO opcaoResposta = (OpcaoRespostaQuestionarioDTO) itOpcao.next();
                                if (opcaoResposta.getExibeComplemento() != null && opcaoResposta.getExibeComplemento().equalsIgnoreCase("S")
                                        && opcaoResposta.getIdQuestaoComplemento() != null) {
                                    QuestaoQuestionarioDTO questaoComplementoDto = new QuestaoQuestionarioDTO();
                                    questaoComplementoDto.setIdQuestaoQuestionario(opcaoResposta.getIdQuestaoComplemento());
                                    questaoComplementoDto = (QuestaoQuestionarioDTO) questaoDao.restore(questaoComplementoDto);
                                    if (questionarioDtoParm.getIdIdentificadorResposta() != null) {
                                        this.obtemRespostaQuestao(questaoComplementoDto, questionarioDtoParm.getIdIdentificadorResposta());
                                    } else if (questaoComplementoDto.getUltimoValor() != null && !questaoComplementoDto.getUltimoValor().equalsIgnoreCase("N")
                                            && questionarioDtoParm.getModo() != null && questionarioDtoParm.getModo().equalsIgnoreCase(QuestionarioDTO.MODO_VISUALIZACAO)) {
                                        this.obtemUltimaResposta(questaoComplementoDto, questionarioDtoParm.getIdSolicitacaoServico());
                                    }
                                    final Collection colOpcoesRespostaComplemento = opcaoRespostaDao.listByIdQuestaoQuestionario(questaoComplementoDto.getIdQuestaoQuestionario());
                                    questaoComplementoDto.setSerializeOpcoesResposta(WebUtil.serializeObjects(colOpcoesRespostaComplemento));
                                    questaoComplementoDto.setColOpcoesResposta(colOpcoesRespostaComplemento);
                                    opcaoResposta.setSerializeQuestaoComplemento(WebUtil.serializeObject(questaoComplementoDto));
                                    opcaoResposta.setQuestaoComplementoDto(questaoComplementoDto);
                                }
                            }
                        }
                        questDto.setSerializeOpcoesResposta(WebUtil.serializeObjects(colOpcoesResposta));
                        questDto.setColOpcoesResposta(colOpcoesResposta);

                        final Collection colQuestoesAgrupadas = questaoDao.listByIdQuestaoAgrupadora(questDto.getIdQuestaoQuestionario());
                        questDto.setColQuestoesAgrupadas(colQuestoesAgrupadas);
                        if (colQuestoesAgrupadas != null) {
                            final Iterator itQuestoesAgrup = colQuestoesAgrupadas.iterator();
                            for (; itQuestoesAgrup.hasNext();) {
                                final QuestaoQuestionarioDTO questAgrup = (QuestaoQuestionarioDTO) itQuestoesAgrup.next();

                                if (questionarioDtoParm.getIdIdentificadorResposta() != null) {
                                    Collection colRespostas = null;
                                    if (questAgrup.getTipo().equalsIgnoreCase("L")) {
                                        colRespostas = respostaDao.listByIdIdentificadorAndIdTabela(questionarioDtoParm.getIdIdentificadorResposta(),
                                                questAgrup.getIdQuestaoQuestionario());
                                    } else {
                                        colRespostas = respostaDao.listByIdIdentificadorAndIdQuestao(questionarioDtoParm.getIdIdentificadorResposta(),
                                                questAgrup.getIdQuestaoQuestionario());
                                    }
                                    if (colRespostas != null) {
                                        if (colRespostas.size() > 0) {
                                            for (final Iterator itAux = colRespostas.iterator(); itAux.hasNext();) {
                                                final RespostaItemQuestionarioDTO respostaItemDto = (RespostaItemQuestionarioDTO) itAux.next();
                                                if (colRespostas.size() == 1) {
                                                    questAgrup.setRespostaItemDto(respostaItemDto);
                                                }
                                                if (questAgrup.getTipoQuestao().equalsIgnoreCase("R") || questAgrup.getTipoQuestao().equalsIgnoreCase("C")
                                                        || questAgrup.getTipoQuestao().equalsIgnoreCase("X")) {
                                                    final Collection colOpcoesRespostaAgrup = respItemQuestOpDao.getRespostasOpcoesByIdRespostaItemQuestionario(respostaItemDto
                                                            .getIdRespostaItemQuestionario());
                                                    respostaItemDto.setColOpcoesResposta(colOpcoesRespostaAgrup);
                                                }
                                            }
                                            questAgrup.setSerializeRespostas(WebUtil.serializeObjects(colRespostas));
                                            questAgrup.setColRespostas(colRespostas);
                                        }
                                    }
                                }

                                final Collection colOpcoesRespostaAgrup = opcaoRespostaDao.listByIdQuestaoQuestionario(questAgrup.getIdQuestaoQuestionario());
                                questAgrup.setSerializeOpcoesResposta(WebUtil.serializeObjects(colOpcoesRespostaAgrup));
                                questAgrup.setColOpcoesResposta(colOpcoesRespostaAgrup);
                            }
                        }

                        if (questionarioDtoParm.getIdIdentificadorResposta() != null) {
                            this.obtemRespostaQuestao(questDto, questionarioDtoParm.getIdIdentificadorResposta());
                        } else if (questDto.getUltimoValor() != null && !questDto.getUltimoValor().equalsIgnoreCase("N") && questionarioDtoParm.getModo() != null
                                && questionarioDtoParm.getModo().equalsIgnoreCase(QuestionarioDTO.MODO_VISUALIZACAO)) {
                            this.obtemUltimaResposta(questDto, questionarioDtoParm.getIdSolicitacaoServico());
                        }

                        final Collection colCabecalhosLinha = questaoDao.listCabecalhosLinha(questDto.getIdQuestaoQuestionario());
                        questDto.setSerializeCabecalhosLinha(WebUtil.serializeObjects(colCabecalhosLinha));
                        questDto.setColCabecalhosLinha(colCabecalhosLinha);
                        final Collection colCabecalhosColuna = questaoDao.listCabecalhosColuna(questDto.getIdQuestaoQuestionario());
                        questDto.setSerializeCabecalhosColuna(WebUtil.serializeObjects(colCabecalhosColuna));
                        questDto.setColCabecalhosColuna(colCabecalhosColuna);
                    }

                }
                grupoDto.setColQuestoes(colQuestoes);
            }

            return questionarioDto;
        } catch (final LogicException e) {
            throw new ServiceException(e);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listByIdEmpresa(final Integer idEmpresa) throws Exception {
        return this.getDao().listByIdEmpresa(idEmpresa);
    }

    @Override
    public Collection listByIdEmpresaAndAplicacao(final Integer idEmpresa, final String aplicacao) throws Exception {
        return this.getDao().listByIdEmpresaAndAplicacao(idEmpresa, aplicacao);
    }

    public boolean existeResposta(final Integer idQuestionario) throws Exception {
        return this.getDao().existeResposta(idQuestionario);
    }

    @Override
    public QuestionarioDTO restoreByIdOrigem(final Integer idQuestionarioOrigem) throws Exception {
        return this.getDao().restoreByIdOrigem(idQuestionarioOrigem);
    }

    @Override
    public void copyGroup(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
        final GrupoQuestionarioDao grupoDao = new GrupoQuestionarioDao();
        final OpcaoRespostaQuestionarioDao opcaoRespostaDao = new OpcaoRespostaQuestionarioDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        final QuestionarioDTO questionarioDto = (QuestionarioDTO) model;
        try {
            // Seta o TransactionController para os DAOs
            questaoDao.setTransactionControler(tc);
            grupoDao.setTransactionControler(tc);
            opcaoRespostaDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            final Collection colQuestoes = questaoDao.listByIdGrupoQuestionario(questionarioDto.getIdGrupoQuestionario());

            GrupoQuestionarioDTO grupoDto = new GrupoQuestionarioDTO();
            grupoDto.setIdQuestionario(questionarioDto.getIdQuestionarioCopiar());
            grupoDto.setNomeGrupoQuestionario(questionarioDto.getNomeGrupoQuestionario());
            grupoDto = (GrupoQuestionarioDTO) grupoDao.create(grupoDto);

            final Iterator itQuestoes = colQuestoes.iterator();
            for (; itQuestoes.hasNext();) {
                QuestaoQuestionarioDTO questaoQuestionarioDto = (QuestaoQuestionarioDTO) itQuestoes.next();
                questaoQuestionarioDto.setIdGrupoQuestionario(grupoDto.getIdGrupoQuestionario());

                final Collection colOpcoesResposta = opcaoRespostaDao.listByIdQuestaoQuestionario(questaoQuestionarioDto.getIdQuestaoQuestionario());

                questaoQuestionarioDto = (QuestaoQuestionarioDTO) questaoDao.create(questaoQuestionarioDto);

                if (colOpcoesResposta != null) {
                    for (final Iterator itOp = colOpcoesResposta.iterator(); itOp.hasNext();) {
                        final OpcaoRespostaQuestionarioDTO opRespDto = (OpcaoRespostaQuestionarioDTO) itOp.next();
                        opRespDto.setIdQuestaoQuestionario(questaoQuestionarioDto.getIdQuestaoQuestionario());

                        opcaoRespostaDao.create(opRespDto);
                    }
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public Collection listOpcoesRespostaItemQuestionarioOpcoes(final Integer idRespostaItemQuestionario) throws Exception {
        final RespostaItemQuestionarioOpcoesDao dao = new RespostaItemQuestionarioOpcoesDao();
        return dao.getRespostasOpcoesByIdRespostaItemQuestionario(idRespostaItemQuestionario);
    }

    @Override
    public boolean existeQuestaoObrigatoria(final Integer idQuestionario) throws Exception {
        return this.getDao().existeQuestaoObrigatoria(idQuestionario);
    }

}
