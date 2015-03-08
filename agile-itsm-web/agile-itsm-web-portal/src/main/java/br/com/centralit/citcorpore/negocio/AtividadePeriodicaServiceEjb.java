package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.AgendaAtvPeriodicasDTO;
import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.OSAtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.OSDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.integracao.AtividadePeriodicaDao;
import br.com.centralit.citcorpore.integracao.OSAtividadePeriodicaDao;
import br.com.centralit.citcorpore.integracao.ProgramacaoAtividadeDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class AtividadePeriodicaServiceEjb extends CrudServiceImpl implements AtividadePeriodicaService {

    private AtividadePeriodicaDao dao;

    @Override
    protected AtividadePeriodicaDao getDao() {
        if (dao == null) {
            dao = new AtividadePeriodicaDao();
        }
        return dao;
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final ProgramacaoAtividadeDao programacaoAtividadeDao = new ProgramacaoAtividadeDao();
        final OSAtividadePeriodicaDao oSAtividadePeriodicaDao = new OSAtividadePeriodicaDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);
            final AtividadePeriodicaDTO atividadePeriodicaDTO = (AtividadePeriodicaDTO) model;
            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            programacaoAtividadeDao.setTransactionControler(tc);
            oSAtividadePeriodicaDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            model = crudDao.create(model);
            if (atividadePeriodicaDTO.getColItens() != null) {
                for (final Iterator it = atividadePeriodicaDTO.getColItens().iterator(); it.hasNext();) {
                    final ProgramacaoAtividadeDTO programacaoAtividadeDTO = (ProgramacaoAtividadeDTO) it.next();
                    programacaoAtividadeDTO.setIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
                    programacaoAtividadeDTO.setHoraInicio(programacaoAtividadeDTO.getHoraInicio().replaceAll(":", ""));
                    programacaoAtividadeDTO.setHoraFim(programacaoAtividadeDTO.getHoraFim().replaceAll(":", ""));
                    programacaoAtividadeDao.create(programacaoAtividadeDTO);
                }
            }

            if (atividadePeriodicaDTO.getColItensOS() != null) {
                for (final Iterator it = atividadePeriodicaDTO.getColItensOS().iterator(); it.hasNext();) {
                    final OSDTO oSDTO = (OSDTO) it.next();
                    final OSAtividadePeriodicaDTO oSAtividadePeriodicadto = new OSAtividadePeriodicaDTO();
                    oSAtividadePeriodicadto.setIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
                    oSAtividadePeriodicadto.setIdOs(oSDTO.getIdOS());
                    oSAtividadePeriodicaDao.create(oSAtividadePeriodicadto);
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
        final CrudDAO crudDao = this.getDao();
        final ProgramacaoAtividadeDao programacaoAtividadeDao = new ProgramacaoAtividadeDao();
        final OSAtividadePeriodicaDao oSAtividadePeriodicaDao = new OSAtividadePeriodicaDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);
            final AtividadePeriodicaDTO atividadePeriodicaDTO = (AtividadePeriodicaDTO) model;

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            programacaoAtividadeDao.setTransactionControler(tc);
            oSAtividadePeriodicaDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(model);
            programacaoAtividadeDao.deleteByIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
            if (atividadePeriodicaDTO.getColItens() != null) {
                for (final Iterator it = atividadePeriodicaDTO.getColItens().iterator(); it.hasNext();) {
                    final ProgramacaoAtividadeDTO programacaoAtividadeDTO = (ProgramacaoAtividadeDTO) it.next();
                    programacaoAtividadeDTO.setIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
                    programacaoAtividadeDTO.setHoraInicio(programacaoAtividadeDTO.getHoraInicio().replaceAll(":", ""));
                    programacaoAtividadeDTO.setHoraFim(programacaoAtividadeDTO.getHoraFim().replaceAll(":", ""));
                    programacaoAtividadeDao.create(programacaoAtividadeDTO);
                }
            }

            oSAtividadePeriodicaDao.deleteByIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
            if (atividadePeriodicaDTO.getColItensOS() != null) {
                for (final Iterator it = atividadePeriodicaDTO.getColItensOS().iterator(); it.hasNext();) {
                    final OSDTO oSDTO = (OSDTO) it.next();
                    final OSAtividadePeriodicaDTO oSAtividadePeriodicadto = new OSAtividadePeriodicaDTO();
                    oSAtividadePeriodicadto.setIdAtividadePeriodica(atividadePeriodicaDTO.getIdAtividadePeriodica());
                    oSAtividadePeriodicadto.setIdOs(oSDTO.getIdOS());
                    oSAtividadePeriodicaDao.create(oSAtividadePeriodicadto);
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
    public Collection findByIdSolicitacaoServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdSolicitacaoServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdProcedimentoTecnico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProcedimentoTecnico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProcedimentoTecnico(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdProcedimentoTecnico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdGrupoAtvPeriodica(final AgendaAtvPeriodicasDTO agendaAtvPeriodicasDTO) throws Exception {
        Collection<AtividadePeriodicaDTO> colAgendamentoVinculados = new ArrayList<AtividadePeriodicaDTO>();
        Collection<AtividadePeriodicaDTO> colAgendamentoSemVinculacao = new ArrayList<AtividadePeriodicaDTO>();
        Collection<AtividadePeriodicaDTO> colAgendamentoMudanca = new ArrayList<AtividadePeriodicaDTO>();
        final Collection<AtividadePeriodicaDTO> colRetorno = new ArrayList<AtividadePeriodicaDTO>();
        try {
            // Só vai entrar caso tenha selecionado algum item do Grupo de Atividades.
            if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoAtvPeriodica() != 0 && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null) {
                // Só vai entrar caso tenha selecioando a opção Gerência Mudança do Grupo Pesquisa.
                if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() == 2) {
                    colAgendamentoMudanca = this.getDao().listSomenteReqMudanca(agendaAtvPeriodicasDTO);
                    if (colAgendamentoMudanca != null) {
                        colRetorno.addAll(colAgendamentoMudanca);
                    }
                } else {
                    if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != 0) {
                        colAgendamentoVinculados = this.getDao().listAgendamentoVinculados(agendaAtvPeriodicasDTO);
                        if (colAgendamentoVinculados != null) {
                            colRetorno.addAll(colAgendamentoVinculados);
                        }
                    }
                }
                // Caso não tenha selecionado nenhuma opção do Grupo Pesquisa, ai vou trazer todos.
                if (agendaAtvPeriodicasDTO != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() != null && agendaAtvPeriodicasDTO.getIdGrupoPesquisa() == 0) {
                    colAgendamentoSemVinculacao = this.getDao().listAgendamentoSemVinculacao(agendaAtvPeriodicasDTO);
                    colAgendamentoMudanca = this.getDao().listSomenteReqMudanca(agendaAtvPeriodicasDTO);
                    if (colAgendamentoSemVinculacao != null) {
                        colRetorno.addAll(colAgendamentoSemVinculacao);
                    }
                    if (colAgendamentoMudanca != null) {
                        colRetorno.addAll(colAgendamentoMudanca);
                    }
                } else {
                    colAgendamentoSemVinculacao = this.getDao().listAgendamentoSemVinculacao(agendaAtvPeriodicasDTO);
                    if (colAgendamentoSemVinculacao != null) {
                        colRetorno.addAll(colAgendamentoSemVinculacao);
                    }
                }
            }
            // return this.getDao().findByIdGrupoAtvPeriodica(agendaAtvPeriodicasDTO);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
        return colRetorno;
    }

    @Override
    public void deleteByIdGrupoAtvPeriodica(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdGrupoAtvPeriodica(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByTituloAtividade(final Integer parm) throws Exception {
        try {
            return this.getDao().findByTituloAtividade(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByTituloAtividade(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByTituloAtividade(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Metodo que verifica se existe um registro com os mesmos dados na base de dados.
     *
     * @param nomeAtividade
     *            - nome da atividade periodica.
     * @return Retorna 'true' se existir um registro igual e 'false' caso contrario.
     * @throws Exception
     */
    @Override
    public boolean existeDuplicacao(final String tituloAtividade, final Integer idAtividade) throws Exception {
        return this.getDao().existeDuplicacao(tituloAtividade, idAtividade);
    }

    @Override
    public Collection findByIdRequisicaoMudanca(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdRequisicaoMudanca(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdProblema(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProblema(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdRequisicaoLiberacao(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdRequisicaoLiberacao(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
