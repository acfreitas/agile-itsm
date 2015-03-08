package br.com.citframework.util;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.resource.ResourceException;

import org.apache.commons.lang.StringUtils;

/**
 * Recupera recurso publicados na JNDI
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 19/08/2014
 *
 */
public final class JNDIFactory implements IContextFactory<Object> {

    private static final Logger LOGGER = Logger.getLogger(JNDIFactory.class.getName());

    private Context context;

    /**
     * Constrói um {@link JNDIFactory} com propriedades padrão do contexto
     *
     */
    public JNDIFactory() {
        try {
            context = new InitialContext();
        } catch (final NamingException e) {
            final String mensagem = "Ocorreu um erro ao inicializar o contexto JNDI: " + e.getMessage();
            LOGGER.log(Level.SEVERE, mensagem, e);
        }
    }

    /**
     * Constrói um {@link JNDIFactory} informado propriedades do contexto
     *
     * @param properties
     *            propriedades a serem utilizadas no contexto
     */
    public JNDIFactory(final Properties properties) {
        try {
            context = new InitialContext(properties);
        } catch (final NamingException e) {
            final String mensagem = "Ocorreu um erro ao inicializar o contexto JNDI: " + e.getMessage();
            LOGGER.log(Level.SEVERE, mensagem, e);
        }
    }

    @Override
    public Object getResource(final String resourceName) throws ResourceException {
        try {
            return context.lookup(this.normalizeJNDIResourceName(resourceName));
        } catch (final NamingException e) {
            final String mensagem = "Ocorreu um erro ao tentar localizar objeto na referência " + resourceName + " no servidor: " + e.getMessage();
            LOGGER.log(Level.SEVERE, mensagem, e);
            throw new ResourceException(mensagem, e);
        }
    }

    @Override
    public Boolean putResource(final Context context, final String name, final Object object) throws ResourceException {
        try {
            context.bind(name, object);
            return true;
        } catch (final NamingException e) {
            LOGGER.log(Level.WARNING, "Não foi possível fazer bind do objeto: " + e.getMessage(), e);
        }
        return false;
    }

    private static final String javaStart = "java:/";
    private static final String jbossStart = "jboss:/";
    private static final String contextoConexao = Constantes.getValue("CONTEXTO_CONEXAO");

    private String normalizeJNDIResourceName(final String jndiName) {
        final String[] jndiNameStarts = new String[] {javaStart, jbossStart};
        String completeJNDIName = jndiName;
        if (!StringUtils.startsWithAny(jndiName, jndiNameStarts)) {
            if (StringUtils.isNotBlank(contextoConexao)) {
                completeJNDIName = contextoConexao.concat(jndiName);
            } else {
                completeJNDIName = javaStart.concat(jndiName);
            }
        }
        return completeJNDIName;
    }

}
