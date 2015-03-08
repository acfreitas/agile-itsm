package br.com.centralit.bpm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilStrings;

public class UtilScript {

    public static Object executaScript(final String nome, String script, final Map<String, Object> objetos) throws Exception {
        Object object = null;
        final Map<String, Object> params = getParams(objetos);
        if (script != null) {
            script = substituiParametros(script, params);
            Context cx = Context.enter();
            final Scriptable scope = cx.initStandardObjects();
            scope.put("params", scope, params);
            try {
                object = cx.evaluateString(scope, script, "script", 1, null);
            } catch (final Exception e) {
                final String msg = e.getLocalizedMessage(); // + "\n" + script;
                throw new Exception(msg);
            } finally {
                Context.exit();
                cx = null;
            }
            cx = null;
        }
        return object;
    }

    public static Map<String, Object> getParams(final Map<String, Object> objetos) throws Exception {
        final Map<String, Object> params = new HashMap<>();
        for (final String key : objetos.keySet()) {
            final Object objeto = objetos.get(key);
            params.put(key, objeto);
            try {
                final List lstGets = Reflexao.findGets(objeto);
                for (int i = 0; i < lstGets.size(); i++) {
                    final String propriedade = UtilStrings.convertePrimeiraLetra((String) lstGets.get(i), "L");
                    final Object value = Reflexao.getPropertyValue(objeto, propriedade);
                    if (value != null) {
                        final String id = key + "." + UtilStrings.convertePrimeiraLetra(propriedade, "L");
                        params.put(id, value);
                    }
                }
            } catch (final Exception e) {

            }
        }
        return params;
    }

    public static String substituiParametros(String str, final Map<String, Object> params) throws Exception {
        if (str != null) {
            for (final String key : params.keySet()) {
                str = str.replaceAll("\\#\\{" + key + "\\}", "params.get(\"" + key + "\")");
            }
        }
        return str;
    }

}
