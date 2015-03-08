package br.com.centralit;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * Fábrica de {@link Context} para ser usada como mocks nos tests
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 20/08/2014
 *
 */
public class MockInitialContextFactory implements InitialContextFactory {

    private static final ThreadLocal<Context> currentContext = new ThreadLocal<>();

    @Override
    public Context getInitialContext(final Hashtable<?, ?> environment) throws NamingException {
        return currentContext.get();
    }

    public static void setCurrentContext(final Context context) {
        currentContext.set(context);
    }

    public static void clearCurrentContext() {
        currentContext.remove();
    }
}
