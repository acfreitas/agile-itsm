package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.BIConsultaColunasDTO;
import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.centralit.citcorpore.integracao.BIConsultaColunasDao;
import br.com.centralit.citcorpore.integracao.BIConsultaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

public class BIConsultaServiceEjb extends CrudServiceImpl implements BIConsultaService {

    private BIConsultaDao dao;

    @Override
    protected BIConsultaDao getDao() {
        if (dao == null) {
            dao = new BIConsultaDao();
        }
        return dao;
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final BIConsultaColunasDao biConsultaColunasDao = new BIConsultaColunasDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            biConsultaColunasDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            model = crudDao.create(model);
            final BIConsultaDTO biConsultaDTO = (BIConsultaDTO) model;
            if (biConsultaDTO.getColColunas() != null) {
                for (final Iterator it = biConsultaDTO.getColColunas().iterator(); it.hasNext();) {
                    final BIConsultaColunasDTO biConsultaColunasDTO = (BIConsultaColunasDTO) it.next();
                    biConsultaColunasDTO.setIdConsulta(biConsultaDTO.getIdConsulta());
                    biConsultaColunasDao.create(biConsultaColunasDTO);
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
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final BIConsultaColunasDao biConsultaColunasDao = new BIConsultaColunasDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            biConsultaColunasDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(model);
            final BIConsultaDTO biConsultaDTO = (BIConsultaDTO) model;
            biConsultaColunasDao.deleteByIdConsulta(biConsultaDTO.getIdConsulta());
            if (biConsultaDTO.getColColunas() != null) {
                for (final Iterator it = biConsultaDTO.getColColunas().iterator(); it.hasNext();) {
                    final BIConsultaColunasDTO biConsultaColunasDTO = (BIConsultaColunasDTO) it.next();
                    biConsultaColunasDTO.setIdConsulta(biConsultaDTO.getIdConsulta());
                    biConsultaColunasDao.create(biConsultaColunasDTO);
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
    public Collection findByIdCategoria(final Integer parm) throws Exception {
        return this.getDao().findByIdCategoria(parm);
    }

    @Override
    public BIConsultaDTO getByIdentificacao(final String ident) throws Exception {
        return this.getDao().getByIdentificacao(ident);
    }

}
