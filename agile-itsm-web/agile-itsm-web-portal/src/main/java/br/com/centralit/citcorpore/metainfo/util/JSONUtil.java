package br.com.centralit.citcorpore.metainfo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONUtil {

    public static Map<String, Object> convertJsonToMap(final String strData, final boolean convKeyToUpperCase) {
        if (strData == null) {
            return null;
        }
        final JsonParser parserJson = new JsonParser();
        final JsonElement element = parserJson.parse(strData);
        if (JsonObject.class.isInstance(element)) {
            final JsonObject object = (JsonObject) element;
            if (object != null) {
                final Set<Map.Entry<String, JsonElement>> set = object.entrySet();
                final Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
                final Map<String, Object> map = new HashMap<>();
                while (iterator.hasNext()) {
                    final Map.Entry<String, JsonElement> entry = iterator.next();
                    String key = entry.getKey();
                    final JsonElement value = entry.getValue();
                    if (convKeyToUpperCase) {
                        if (key != null) {
                            key = key.toUpperCase();
                        }
                    }
                    if (!value.isJsonPrimitive()) {
                        if (value.isJsonArray()) {
                            map.put(key, JSONUtil.convertJsonArrayToCollection((JsonArray) value, convKeyToUpperCase));
                        } else {
                            map.put(key, JSONUtil.convertJsonToMap(value.toString(), convKeyToUpperCase));
                        }
                    } else {
                        map.put(key, value.getAsString());
                    }
                }
                return map;
            }
        }
        if (JsonArray.class.isInstance(element)) {
            final JsonArray array = (JsonArray) element;
            final Map<String, Object> map = new HashMap<>();
            map.put("ARRAY", JSONUtil.convertJsonArrayToCollection(array, convKeyToUpperCase));
            return map;
        }
        return null;
    }

    public static Collection<Map<String, Object>> convertJsonArrayToCollection(final JsonArray array, final boolean convUpperCase) {
        if (array != null) {
            final Iterator<JsonElement> iterator = array.iterator();
            final Collection<Map<String, Object>> col = new ArrayList<>();
            while (iterator.hasNext()) {
                final Object obj = iterator.next();
                col.add(JSONUtil.convertJsonToMap(obj.toString(), convUpperCase));
            }
            return col;
        }
        return null;
    }

}
