package br.com.centralit.citcorpore.metainfo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;

public class RuntimeScript {

    public Object executeClass(final String classToExecute, final Object parms[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        final Class<?> classe = Class.forName(classToExecute);
        final Object objeto = classe.newInstance();

        final Method mtd = CitAjaxReflexao.findMethod("execute", objeto);
        final Object retorno = mtd.invoke(objeto, parms);
        return retorno;
    }

}
