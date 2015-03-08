package br.com.citframework.service;

import java.util.logging.Level;
import java.util.logging.Logger;

import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.excecao.ServiceNotFoundException;
import br.com.citframework.util.Constantes;

public class ServiceLocator {

    private static final Logger LOGGER = Logger.getLogger(ServiceLocator.class.getName());

    private static ServiceLocator singleton;

    private static final String SUFIXO_SERVICE = Constantes.getValue("SUFIXO_SERVICE");

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (singleton == null) {
            synchronized (ServiceLocator.class) {
                if (singleton == null) {
                    singleton = new ServiceLocator();
                }
            }
        }
        return singleton;
    }

    public Object getService(final Class<?> iservice, final Usuario usr) throws ServiceException {
        final String nome = iservice.getName();
        try {
            final Object obj = Class.forName(nome + SUFIXO_SERVICE).newInstance();
            ((IService) obj).setUsuario(usr);
            return obj;
        } catch (final Exception e) {
            try {
                // Se nao conseguiu com o sufixo indicado nos parametros, faz com Bean pra ter certeza de que nao existe.
                final Object obj = Class.forName(nome + "Bean").newInstance();
                ((IService) obj).setUsuario(usr);
                return obj;
            } catch (final Exception e2) {
                final String message = String.format("Classe %s%s não existe", nome, SUFIXO_SERVICE);
                LOGGER.log(Level.SEVERE, message + e.getMessage(), e);
                throw new ServiceNotFoundException(message);
            }
        }
    }

}
