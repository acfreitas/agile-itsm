package br.com.citframework.util;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import br.com.citframework.excecao.LogicException;

public class UtilHashMaps {

    public static String generateString(final Map<?, ?> map) throws LogicException {
        String retorno = "";

        final Set set = map.entrySet();
        final Iterator i = set.iterator();
        while (i.hasNext()) {
            final Entry me = (Entry) i.next();
            retorno = retorno + me.getKey() + ": " + me.getValue() + "\n";
        }

        return retorno;
    }

}
