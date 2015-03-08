package br.com.citframework.push;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Cria configurações para uso dos serviços de push. Toda configuração deve ser adicionada em {@link Key}
 * 
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since
 */
public class ConfigPushService {

    private final ConcurrentMap<Key, String> configs = new ConcurrentHashMap<>();

    public enum Key {

        GOOLE_API_KEY;

    }

    public void setValue(final Key key, final String value) {
        configs.put(key, value);
    }

    public String getByKey(final Key key) {
        return configs.get(key);
    }

}
