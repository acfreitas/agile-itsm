package br.com.centralit.citcorpore.negocio;

import java.util.Iterator;

import br.com.centralit.citcorpore.bean.ImportConfigCamposDTO;
import br.com.centralit.citcorpore.bean.ImportConfigDTO;
import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.centralit.citcorpore.integracao.ImportConfigCamposDao;
import br.com.centralit.citcorpore.integracao.ImportConfigDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ImportConfigServiceEjb extends CrudServiceImpl implements ImportConfigService {

    private ImportConfigDao dao;

    @Override
    protected ImportConfigDao getDao() {
        if (dao == null) {
            dao = new ImportConfigDao();
        }
        return dao;
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        final ImportConfigDTO importConfigDTO = (ImportConfigDTO) model;
        final ImportConfigCamposDao importConfigCamposDao = new ImportConfigCamposDao();
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            this.getDao().setTransactionControler(tc);
            importConfigCamposDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            model = this.getDao().create(model);

            if (importConfigDTO.getColDadosCampos() != null) {
                for (final Iterator it = importConfigDTO.getColDadosCampos().iterator(); it.hasNext();) {
                    final ImportConfigCamposDTO importConfigCamposDTO = (ImportConfigCamposDTO) it.next();
                    importConfigCamposDTO.setIdImportConfig(importConfigDTO.getIdImportConfig());
                    importConfigCamposDao.create(importConfigCamposDTO);
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
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        final ImportConfigDTO importConfigDTO = (ImportConfigDTO) model;
        final ImportConfigCamposDao importConfigCamposDao = new ImportConfigCamposDao();
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Seta o TransactionController para os DAOs
            this.getDao().setTransactionControler(tc);
            importConfigCamposDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            this.getDao().update(model);

            importConfigCamposDao.deleteByIdImportConfig(importConfigDTO.getIdImportConfig());
            if (importConfigDTO.getColDadosCampos() != null) {
                for (final Iterator it = importConfigDTO.getColDadosCampos().iterator(); it.hasNext();) {
                    final ImportConfigCamposDTO importConfigCamposDTO = (ImportConfigCamposDTO) it.next();
                    importConfigCamposDTO.setIdImportConfig(importConfigDTO.getIdImportConfig());
                    importConfigCamposDao.create(importConfigCamposDTO);
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    /**
     * Exclui o registro ImportConfigDTO e seus itens ImportConfigCampos
     */
    @Override
    public void excluirRegistroESubItens(final ImportConfigDTO importConfigDTO) throws ServiceException, LogicException {

        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        final ImportConfigCamposDao importConfigCamposDao = new ImportConfigCamposDao();

        try {

            // Seta o TransactionController para os DAOs
            this.getDao().setTransactionControler(tc);
            // importConfigCamposDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            importConfigCamposDao.deleteByIdImportConfig(importConfigDTO.getIdImportConfig());
            this.getDao().deleteByIdImportConfig(importConfigDTO.getIdImportConfig());

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }

    }

    @Override
    public ImportConfigDTO consultarImportConfigDTO(final ImportarDadosDTO importarDadosDTO) throws Exception {
        return this.getDao().consultarImportConfigDTO(importarDadosDTO);

    }

    /**
     *
     * Exclui o registro logicamente setando o campo dataFim com a data de execução
     *
     * @param importConfigDTO
     * @throws ServiceException
     * @throws LogicException
     */
    @Override
    public void excluirRegistroLogicamente(final Integer idImportarDados) throws ServiceException, LogicException {

        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        final ImportConfigCamposDao importConfigCamposDao = new ImportConfigCamposDao();

        try {

            // Seta o TransactionController para os DAOs
            this.getDao().setTransactionControler(tc);
            importConfigCamposDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Exclui logicamente o registro
            importConfigCamposDao.deletarLogicamentePorIdImportarDados(idImportarDados);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }

    }

}
