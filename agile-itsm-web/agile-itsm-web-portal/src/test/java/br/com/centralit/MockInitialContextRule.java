package br.com.centralit;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * {@link TestRule} para mockar {@link InitialContext}, quando pertinente
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 20/08/2014
 *
 */
public class MockInitialContextRule implements TestRule {

    private final Context context;

    public MockInitialContextRule(final Context context) {
        this.context = context;
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {

            @Override
            public void evaluate() throws Throwable {
                System.setProperty(Context.INITIAL_CONTEXT_FACTORY, MockInitialContextFactory.class.getName());
                MockInitialContextFactory.setCurrentContext(context);
                try {
                    base.evaluate();
                } finally {
                    System.clearProperty(Context.INITIAL_CONTEXT_FACTORY);
                    MockInitialContextFactory.clearCurrentContext();
                }
            }
        };

    }

}
