package br.com.citframework.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.dto.LookupDTO;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.LookupDao;
import br.com.citframework.integracao.LookupProcessDefaultDao;
import br.com.citframework.util.LookupFieldUtil;

public class LookupProcessServiceEjb implements LookupProcessService {

    protected Usuario usuario;

    protected LookupProcessDefaultDao getDao(final LookupDTO lookup) throws ServiceException {
        final LookupFieldUtil lookupUtil = new LookupFieldUtil();
        final String daoProcess = lookupUtil.getDaoProcessor(lookup.getNomeLookup());
        if (daoProcess.equalsIgnoreCase("DEFAULT")) {
            return new LookupProcessDefaultDao();
        } else {
            try {
                final Class<?> classe = Class.forName(daoProcess);
                return (LookupProcessDefaultDao) classe.newInstance();
            } catch (final ClassNotFoundException e) {
                e.printStackTrace();
                throw new ServiceException(e);
            } catch (final InstantiationException e) {
                e.printStackTrace();
                throw new ServiceException(e);
            } catch (final IllegalAccessException e) {
                e.printStackTrace();
                throw new ServiceException(e);
            }
        }
    }

    @Override
    public Collection process(final Object obj, final HttpServletRequest request) throws LogicException, ServiceException {
        final LookupDTO lookup = (LookupDTO) obj;

        final LookupDao dao = this.getDao(lookup);
        if (dao == null) {
            throw new ServiceException("Nao encontrado DAO adequado para processamento deste Lookup!");
        }
        try {
            return dao.processLookup(lookup, request);
        } catch (final LogicException e) {
            throw new LogicException(e);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    private LookupProcessDefaultDao dao;

    protected LookupProcessDefaultDao getDao() throws ServiceException {
        if (dao == null) {
            dao = new LookupProcessDefaultDao();
        }
        return dao;
    }

    @Override
    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }

}
