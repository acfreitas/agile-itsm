package br.com.centralit.citcorpore.metainfo.util;

import java.util.Map;

public class HashMapUtil {

    public static Map map;

    public static String getFieldInHash(final String name) {
        if (map == null) {
            return null;
        }
        if (name == null) {
            return null;
        }
        return (String) map.get(name.toUpperCase());
    }

}
