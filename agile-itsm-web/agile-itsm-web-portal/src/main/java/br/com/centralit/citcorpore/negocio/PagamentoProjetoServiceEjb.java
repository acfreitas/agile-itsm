package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PagamentoProjetoDTO;
import br.com.centralit.citcorpore.bean.TarefaLinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.integracao.PagamentoProjetoDao;
import br.com.centralit.citcorpore.integracao.TarefaLinhaBaseProjetoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class PagamentoProjetoServiceEjb extends CrudServiceImpl implements PagamentoProjetoService {

    private PagamentoProjetoDao dao;

    @Override
    protected PagamentoProjetoDao getDao() {
        if (dao == null) {
            dao = new PagamentoProjetoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdProjeto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdProjeto(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final TarefaLinhaBaseProjetoDao tarefaLinhaBaseProjetoDao = new TarefaLinhaBaseProjetoDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            tarefaLinhaBaseProjetoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();
            double valorPagamento = 0;

            // Executa operacoes pertinentes ao negocio.
            final PagamentoProjetoDTO pagamentoProjetoDTO = (PagamentoProjetoDTO) model;
            pagamentoProjetoDTO.setValorPagamento(new Double(0));
            model = crudDao.create(model);
            if (pagamentoProjetoDTO.getIdTarefasParaPagamento() != null) {
                for (int i = 0; i < pagamentoProjetoDTO.getIdTarefasParaPagamento().length; i++) {
                    final TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = new TarefaLinhaBaseProjetoDTO();
                    tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjeto(pagamentoProjetoDTO.getIdTarefasParaPagamento()[i]);
                    final TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.restore(tarefaLinhaBaseProjetoDTO);
                    if (tarefaLinhaBaseProjetoAux != null) {
                        if (tarefaLinhaBaseProjetoAux.getCustoPerfil() != null) {
                            valorPagamento = valorPagamento + tarefaLinhaBaseProjetoAux.getCustoPerfil().doubleValue();
                        }
                    }
                    tarefaLinhaBaseProjetoDTO.setIdPagamentoProjeto(pagamentoProjetoDTO.getIdPagamentoProjeto());
                    tarefaLinhaBaseProjetoDao.updateNotNull(tarefaLinhaBaseProjetoDTO);
                }
            }
            pagamentoProjetoDTO.setValorPagamento(valorPagamento);
            crudDao.update(pagamentoProjetoDTO);

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
        final TarefaLinhaBaseProjetoDao tarefaLinhaBaseProjetoDao = new TarefaLinhaBaseProjetoDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            tarefaLinhaBaseProjetoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();
            double valorPagamento = 0;

            // Executa operacoes pertinentes ao negocio.
            final PagamentoProjetoDTO pagamentoProjetoDTO = (PagamentoProjetoDTO) model;
            pagamentoProjetoDTO.setValorPagamento(new Double(0));
            crudDao.update(model);
            if (pagamentoProjetoDTO.getIdTarefasParaPagamento() != null) {
                for (int i = 0; i < pagamentoProjetoDTO.getIdTarefasParaPagamento().length; i++) {
                    final TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = new TarefaLinhaBaseProjetoDTO();
                    tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjeto(pagamentoProjetoDTO.getIdTarefasParaPagamento()[i]);
                    final TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.restore(tarefaLinhaBaseProjetoDTO);
                    if (tarefaLinhaBaseProjetoAux != null) {
                        if (tarefaLinhaBaseProjetoAux.getCustoPerfil() != null) {
                            valorPagamento = valorPagamento + tarefaLinhaBaseProjetoAux.getCustoPerfil().doubleValue();
                        }
                    }
                    tarefaLinhaBaseProjetoDTO.setIdPagamentoProjeto(pagamentoProjetoDTO.getIdPagamentoProjeto());
                    tarefaLinhaBaseProjetoDao.updateNotNull(tarefaLinhaBaseProjetoDTO);
                }
            }
            pagamentoProjetoDTO.setValorPagamento(valorPagamento);
            crudDao.update(pagamentoProjetoDTO);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void updateSituacao(final IDto model) throws ServiceException, LogicException {
        final PagamentoProjetoDTO pagamentoProjetoDTO = new PagamentoProjetoDTO();
        final PagamentoProjetoDTO pagamentoProjetoParm = (PagamentoProjetoDTO) model;
        pagamentoProjetoDTO.setIdPagamentoProjeto(pagamentoProjetoParm.getIdPagamentoProjeto());
        pagamentoProjetoDTO.setSituacao("P");
        pagamentoProjetoDTO.setDataUltAlteracao(pagamentoProjetoParm.getDataUltAlteracao());
        pagamentoProjetoDTO.setHoraUltAlteracao(pagamentoProjetoParm.getHoraUltAlteracao());
        pagamentoProjetoDTO.setUsuarioUltAlteracao(pagamentoProjetoParm.getUsuarioUltAlteracao());
        pagamentoProjetoDTO.setDataPagamento(pagamentoProjetoParm.getDataPagamentoAtu());
        try {
            this.getDao().updateNotNull(pagamentoProjetoDTO);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection getTotaisByIdProjeto(final Integer idProjetoParm) throws Exception {
        try {
            return this.getDao().getTotaisByIdProjeto(idProjetoParm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
