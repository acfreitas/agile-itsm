package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.BIDashBoardDTO;
import br.com.centralit.citcorpore.bean.BIItemDashBoardDTO;
import br.com.centralit.citcorpore.integracao.BIDashBoardDao;
import br.com.centralit.citcorpore.integracao.BIItemDashBoardDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class BIDashBoardServiceEjb extends CrudServiceImpl implements BIDashBoardService {

    private BIDashBoardDao dao;

    @Override
    protected BIDashBoardDao getDao() {
        if (dao == null) {
            dao = new BIDashBoardDao();
        }
        return dao;
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final BIItemDashBoardDao biItemDashBoardDao = new BIItemDashBoardDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            biItemDashBoardDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            model = crudDao.create(model);
            final BIDashBoardDTO biDashBoardDTO = (BIDashBoardDTO) model;
            if (biDashBoardDTO != null) {
                final Collection col = biDashBoardDTO.getColItens();
                if (col != null) {
                    for (final Iterator it = col.iterator(); it.hasNext();) {
                        final BIItemDashBoardDTO biItemDashBoardDTO = (BIItemDashBoardDTO) it.next();
                        biItemDashBoardDTO.setIdDashBoard(biDashBoardDTO.getIdDashBoard());
                        biItemDashBoardDao.create(biItemDashBoardDTO);
                    }
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();

            return model;
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
        return model;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final CrudDAO crudDao = this.getDao();
        final BIItemDashBoardDao biItemDashBoardDao = new BIItemDashBoardDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            biItemDashBoardDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(model);
            final BIDashBoardDTO biDashBoardDTO = (BIDashBoardDTO) model;
            biItemDashBoardDao.deleteByIdDashBoard(biDashBoardDTO.getIdDashBoard());
            if (biDashBoardDTO != null) {
                final Collection col = biDashBoardDTO.getColItens();
                if (col != null) {
                    for (final Iterator it = col.iterator(); it.hasNext();) {
                        final BIItemDashBoardDTO biItemDashBoardDTO = (BIItemDashBoardDTO) it.next();
                        biItemDashBoardDTO.setIdDashBoard(biDashBoardDTO.getIdDashBoard());
                        biItemDashBoardDao.create(biItemDashBoardDTO);
                    }
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public IDto getByIdentificacao(final String ident) throws Exception {
        return this.getDao().getByIdentificacao(ident);
    }

}
