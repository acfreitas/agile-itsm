package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.FaixaValoresRecursoDTO;
import br.com.centralit.citcorpore.bean.RecursoDTO;
import br.com.centralit.citcorpore.integracao.FaixaValoresRecursoDao;
import br.com.centralit.citcorpore.integracao.RecursoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class RecursoServiceEjb extends CrudServiceImpl implements RecursoService {

    private RecursoDao dao;

    @Override
    protected RecursoDao getDao() {
        if (dao == null) {
            dao = new RecursoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdGrupoRecurso(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdGrupoRecurso(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdGrupoRecurso(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdGrupoRecurso(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final FaixaValoresRecursoDao faixaDao = new FaixaValoresRecursoDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            faixaDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            model = crudDao.create(model);
            final RecursoDTO objDto = (RecursoDTO) model;
            if (objDto.getColFaixasValores() != null) {
                for (final Iterator it = objDto.getColFaixasValores().iterator(); it.hasNext();) {
                    final FaixaValoresRecursoDTO faixaDto = (FaixaValoresRecursoDTO) it.next();
                    faixaDto.setIdRecurso(objDto.getIdRecurso());

                    faixaDao.create(faixaDto);
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
        final FaixaValoresRecursoDao faixaDao = new FaixaValoresRecursoDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            faixaDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            final RecursoDTO objDto = (RecursoDTO) model;

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(model);
            faixaDao.deleteByIdRecurso(objDto.getIdRecurso());

            if (objDto.getColFaixasValores() != null) {
                for (final Iterator it = objDto.getColFaixasValores().iterator(); it.hasNext();) {
                    final FaixaValoresRecursoDTO faixaDto = (FaixaValoresRecursoDTO) it.next();
                    faixaDto.setIdRecurso(objDto.getIdRecurso());

                    faixaDao.create(faixaDto);
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
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        final RecursoDTO recursoDTO = (RecursoDTO) super.restore(model);
        if (recursoDTO != null) {
            final FaixaValoresRecursoDao faixaDao = new FaixaValoresRecursoDao();
            Collection col;
            try {
                col = faixaDao.findByIdRecurso(recursoDTO.getIdRecurso());
            } catch (final Exception e) {
                e.printStackTrace();
                throw new ServiceException(e);
            }
            recursoDTO.setColFaixasValores(col);
        }
        return recursoDTO;
    }

    @Override
    public Collection findByHostName(final String hostName) throws Exception {
        try {
            return this.getDao().findByHostName(hostName);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
