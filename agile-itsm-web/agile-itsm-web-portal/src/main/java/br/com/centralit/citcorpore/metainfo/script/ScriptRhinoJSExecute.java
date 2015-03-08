package br.com.centralit.citcorpore.metainfo.script;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citcorpore.metainfo.negocio.DinamicViewsService;
import br.com.citframework.service.ServiceLocator;

public class ScriptRhinoJSExecute {
    public Object processScript(Context cx, Scriptable scope, String script, String sourceName) throws Exception {
        DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance().getService(DinamicViewsService.class, null);

        Object language = scope.get("language", scope);
        script = dinamicViewsService.internacionalizaScript(script, language.toString());

        return cx.evaluateString(scope, script, sourceName, 1, null);
    }
}
