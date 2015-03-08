/**
 * 
 */
package br.com.centralit.citajax.framework;

import org.directwebremoting.Browser;
import org.directwebremoting.ScriptSessions;

/**
 * @author valdoilo.damasceno
 * 
 */
public class AjaxReverse extends Browser {

	/**
	 * Executa o Ajax Reverso chamando uma fun��o JavaScript com seus respectivos par�metros.
	 * 
	 * @param nomeDaFuncaoJavaScript
	 *            - Nome da Fun��o JavaScript.
	 * @param parametrosDaFuncao
	 *            - Par�metros da Fun��o JavaScript.
	 * @since 21.11.2013
	 * @author valdoilo.damasceno
	 */
	public static void executarAjaxReverseWithCurrentPage(final String nomeDaFuncaoJavaScript, final Object... parametrosDaFuncao) {

		Browser.withCurrentPage(new Runnable() {

			public void run() {

				ScriptSessions.addFunctionCall(nomeDaFuncaoJavaScript, parametrosDaFuncao);

			}

		});
	}

	/**
	 * Executa o Ajax Reverso chamando uma fun��o JavaScript com seus respectivos par�metros.
	 * 
	 * @param nomeDaFuncaoJavaScript
	 *            - Nome da Fun��o JavaScript.
	 * @param parametrosDaFuncao
	 *            - Par�metros da Fun��o JavaScript.
	 * @since 28.11.2013
	 * @author valdoilo.damasceno
	 */
	public static void executarAjaxReverseWithAllSessions(final String nomeDaFuncaoJavaScript, final Object... parametrosDaFuncao) {

		Browser.withAllSessions(new Runnable() {

			public void run() {

				ScriptSessions.addFunctionCall(nomeDaFuncaoJavaScript, parametrosDaFuncao);

			}

		});
	}
}
