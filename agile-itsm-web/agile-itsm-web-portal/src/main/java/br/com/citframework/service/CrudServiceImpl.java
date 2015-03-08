package br.com.citframework.service;

import java.util.Collection;

import org.apache.log4j.Logger;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.util.UtilI18N;

public abstract class CrudServiceImpl implements CrudService, IService {

    private static final Logger LOGGER = Logger.getLogger(CrudServiceImpl.class);

    protected abstract CrudDAO getDao();

    protected void validaCreate(final Object obj) throws Exception {}

    protected void validaUpdate(final Object obj) throws Exception {}

    protected void validaDelete(final Object obj) throws Exception {}

    protected void validaFind(final Object obj) throws Exception {}

    protected Usuario usuario;

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        IDto createdModel = model;

        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            createdModel = crudDao.create(model);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return createdModel;
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        try {
            return this.getDao().restore(model);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(model);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void delete(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaDelete(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            crudDao.delete(model);

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public Collection list() throws ServiceException, LogicException {
        try {
            return this.getDao().list();
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection find(final IDto obj) throws LogicException, ServiceException {
        try {
            this.validaFind(obj);
            return this.getDao().find(obj);
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
    }

    /**
     * Mensagem do arquivo Properties
     *
     * @param key
     * @return Mensagem Internacionalizada
     */
    public String i18nMessage(final String key) {
        if (usuario != null) {
            final String value = UtilI18N.internacionaliza(usuario.getLocale(), key);
            if (value != null) {
                return value;
            }
        }
        return key;
    }

    /**
     * Recupera uma mensagem internacilizada, de acordo com o {@code locale}, formatando-a com os {@code params}, caso informados
     *
     * @param locale
     *            locale para buscar o valor da {@code key}
     * @param key
     *            chave a ser procurada
     * @param params
     *            parâmetros para formatação, caso haja
     * @return
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 30/09/2014
     */
    public String i18nMessage(final String locale, final String key, final Object... params) {
        return UtilI18N.internacionaliza(locale, key, params);
    }

    protected void rollbackTransaction(final TransactionControler tc, final Exception ex) throws ServiceException, LogicException {
        LOGGER.warn(ex.getMessage(), ex);

        if (tc != null) {
            try {
                if (tc.isStarted()) { // Se estiver startada, entao faz roolback.
                    tc.rollback();
                }

                tc.closeQuietly();
            } catch (final Exception e) {
                LOGGER.warn(e.getMessage(), e);
            }
        }

        if (ex instanceof LogicException) {
            throw (LogicException) ex;
        }
        throw new ServiceException(ex);
    }

    @Override
    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }

}
