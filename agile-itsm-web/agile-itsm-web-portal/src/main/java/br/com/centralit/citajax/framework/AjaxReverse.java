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
	 * Executa o Ajax Reverso chamando uma função JavaScript com seus respectivos parâmetros.
	 * 
	 * @param nomeDaFuncaoJavaScript
	 *            - Nome da Função JavaScript.
	 * @param parametrosDaFuncao
	 *            - Parâmetros da Função JavaScript.
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
	 * Executa o Ajax Reverso chamando uma função JavaScript com seus respectivos parâmetros.
	 * 
	 * @param nomeDaFuncaoJavaScript
	 *            - Nome da Função JavaScript.
	 * @param parametrosDaFuncao
	 *            - Parâmetros da Função JavaScript.
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
