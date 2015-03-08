package br.com.citframework.log;

import java.sql.Timestamp;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import br.com.citframework.dto.LogDados;
import br.com.citframework.service.LogDadosService;
import br.com.citframework.service.LogDadosServiceBean;
//import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class DbLog implements Log {

    private static final Logger LOGGER = Logger.getLogger(DbLog.class);

    @Override
    public void registraLog(final String mensagem, final Class<?> classe, final String tipoMensagem) throws Exception {
        try {
            if (mensagem.indexOf(Log.SEPARADOR) > 0) {
                final StringTokenizer stok = new StringTokenizer(mensagem, SEPARADOR);
                int i = 0;
                String nomeTabela = "";
                String operacao = "";
                String dados = "";
                String idUsuario = "";
                String login = "";
                String ipOrigem = "";

                while (stok.hasMoreTokens()) {
                    final String tok = stok.nextToken();
                    if (i == 0) {
                        nomeTabela = tok;
                    } else if (i == 1) {
                        operacao = tok;
                    } else if (i == 2) {
                        dados = tok;
                    } else if (i == 3) {
                        idUsuario = tok;
                    } else if (i == 4) {
                        login = tok;
                    } else if (i == 5) {
                        ipOrigem = tok;
                    }
                    i++;
                }

                if (nomeTabela.equalsIgnoreCase("logdados")) {
                    return;
                }
                final LogDados ld = new LogDados();
                ld.setDados(dados);
                final Timestamp dataAtual = UtilDatas.getDataHoraAtual();
                ld.setDtAtualizacao(dataAtual);
                ld.setDataLog(dataAtual);
                ld.setIdUsuario(new Integer(idUsuario));
                ld.setLocalOrigem(ipOrigem);
                ld.setOperacao(operacao);
                ld.setNomeTabela(nomeTabela);
                ld.setNomeUsuario(login);

                this.getLogDadosService().create(ld);

                final DbLogArquivo dbLogArquivo = new DbLogArquivo();
                final String ldString = dbLogArquivo.formatarStringLogDados(ld);
                dbLogArquivo.registraLog(ldString, classe, tipoMensagem);
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);
        }
    }

    @Override
    public void registraLog(final Exception e, final Class<?> classe, final String tipoMensagem) throws Exception {}

    private LogDadosService logDadosService;

    private LogDadosService getLogDadosService() {
        if (logDadosService == null) {
            logDadosService = new LogDadosServiceBean();
        }
        return logDadosService;
    }

}
