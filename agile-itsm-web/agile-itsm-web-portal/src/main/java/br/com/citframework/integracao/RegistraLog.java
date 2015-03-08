package br.com.citframework.integracao;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.log.Log;
import br.com.citframework.log.LogFactory;
import br.com.citframework.util.Reflexao;

public class RegistraLog implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(RegistraLog.class);

    private final Object obj;
    private final String operacao;
    private final PersistenceUtil persistenceUtil;
    private final UsuarioDTO usuarioSessao;
    private final String tableName;

    public RegistraLog(final Object obj, final String operacao, final PersistenceUtil persistenceUtil, final UsuarioDTO usuarioSessao, final String tableName) {
        super();
        this.obj = obj;
        this.operacao = operacao;
        this.persistenceUtil = persistenceUtil;
        this.usuarioSessao = usuarioSessao;
        this.tableName = tableName;
    }

    @Override
    public void run() {
        try {
            if (usuarioSessao != null) {
                final List<String> lista = Reflexao.getAllProperties(obj);
                final StringBuilder dados = new StringBuilder();
                for (int i = 0; i < lista.size(); i++) {
                    String campo = lista.get(i).toString();
                    final Object valor = Reflexao.getPropertyValue(obj, campo);
                    if (valor != null) {
                        campo = persistenceUtil.getCampoDB(campo);
                        if (dados.length() > 0) {
                            dados.append(";");
                        }
                        dados.append(campo);
                        dados.append(" = ");
                        dados.append(valor);
                    }
                }
                if (dados.length() > 0) {
                    final StringBuilder message = new StringBuilder();
                    message.append(tableName);
                    message.append(Log.SEPARADOR);
                    message.append(operacao);
                    message.append(Log.SEPARADOR);
                    message.append(dados);
                    message.append(Log.SEPARADOR);
                    message.append(usuarioSessao.getIdUsuario());
                    message.append(Log.SEPARADOR);
                    message.append(usuarioSessao.getNomeUsuario());
                    message.append(Log.SEPARADOR);
                    message.append(usuarioSessao.getStatus());
                    LogFactory.getLog().registraLog(message.toString(), this.getClass(), Log.INFO);
                }
            }
        } catch (final Exception e) {
            if (e instanceof IOException) {
                LOGGER.debug("Erro ao recuperar arquivo para escrita de log: " + e.getMessage());
            } else {
                LOGGER.debug(e.getMessage(), e);
            }
        }
    }

}
