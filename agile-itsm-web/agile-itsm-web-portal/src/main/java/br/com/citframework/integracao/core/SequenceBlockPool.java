package br.com.citframework.integracao.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class SequenceBlockPool {

    private static final Map<String, SequenceBlockCache> sbcPool = new ConcurrentHashMap<>();

    public static void putToPool(final String alias, final SequenceBlockCache cache) {
        if (sbcPool.get(alias) == null) {
            sbcPool.put(alias, cache);
        }
    }

    public static SequenceBlockCache getFromPool(final String alias) {
        SequenceBlockCache sbc = sbcPool.get(alias);
        if (sbc == null) {
            sbc = new SequenceBlockCache(alias);
            putToPool(alias, sbc);
        }
        return sbc;
    }

}
