package br.com.citframework.util;

import static org.mockito.Mockito.mock;

import javax.naming.Context;
import javax.resource.ResourceException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.centralit.MockInitialContextRule;

/**
 * Classe de testes, mockada, para validação do comportamento de {@link JNDIFactory}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 19/08/2014
 *
 */
public final class JNDIFactoryTest {

    private static Context context = mock(Context.class);

    private static JNDIFactory factory = new JNDIFactory();

    private static final String JNDI_JAVA = "java:";
    private static final String JNDI_JAVA_TEST = JNDI_JAVA.concat("/test");
    private static final String JNDI_JAVA_TEST_NOTFOUND = JNDI_JAVA.concat("/notFound");

    private final String BINDED_STRING = "This is STRIINNNGGGG!!";

    @Rule
    public MockInitialContextRule mockInitialContextRule = new MockInitialContextRule(context);

    @BeforeClass
    public static void setUpClass() throws Exception {
        context.createSubcontext(JNDI_JAVA);
        context.createSubcontext(JNDI_JAVA_TEST);

        ReflectionUtils.setField(factory, "context", context);
    }

    @Test
    public void testPutResource() throws Exception {
        final Boolean result = factory.putResource(context, JNDI_JAVA_TEST, BINDED_STRING);
        Assert.assertTrue(result);
        Mockito.verify(context, Mockito.times(1)).bind(JNDI_JAVA_TEST, BINDED_STRING);
    }

    @Test
    public void testGetResource() throws Exception {
        factory.getResource(JNDI_JAVA_TEST);
        Mockito.when(context.lookup(JNDI_JAVA_TEST)).thenReturn(JNDI_JAVA_TEST);
        Mockito.verify(context, Mockito.times(1)).lookup(JNDI_JAVA_TEST);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGetResourceNotFound() throws Exception {
        Mockito.when(context.lookup(JNDI_JAVA_TEST_NOTFOUND)).thenThrow(ResourceException.class);
    }

}
