package br.com.citframework.integracao;

import org.apache.log4j.Logger;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.LogDados;
import br.com.citframework.service.LogDadosService;
import br.com.citframework.service.LogDadosServiceBean;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class RegistraLogDinamicView implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(RegistraLogDinamicView.class);

    private final Object[] obj;
    private final String operacao;
    private final String sqlExec;
    private final UsuarioDTO usuarioSessao;
    private final String tableName;

    public RegistraLogDinamicView(final Object[] obj, final String operacao, final String sqlExec, final UsuarioDTO usuarioSessao, final String tableName) {
        this.obj = obj;
        this.operacao = operacao;
        this.sqlExec = sqlExec;
        this.usuarioSessao = usuarioSessao;
        this.tableName = tableName;
    }

    @Override
    public void run() {
        if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
            if (usuarioSessao != null) {
                String dados = "";
                if (obj != null) {
                    for (final Object element : obj) {
                        if (!dados.trim().equalsIgnoreCase("")) {
                            dados += ", ";
                        }
                        dados += "[" + element.toString() + "]";
                    }
                }
                LogDados logDados = new LogDados();
                logDados.setDtAtualizacao(UtilDatas.getDataHoraAtual());
                logDados.setIdUsuario(usuarioSessao.getIdUsuario());
                logDados.setDataInicio(UtilDatas.getDataAtual());
                logDados.setDataLog(UtilDatas.getDataHoraAtual());
                logDados.setNomeTabela(tableName);
                logDados.setOperacao(operacao);
                logDados.setLocalOrigem(usuarioSessao.getNomeUsuario());
                logDados.setDados("Execute ... {" + sqlExec + "} Data: {" + dados + "}");

                final LogDadosService lds = new LogDadosServiceBean();

                try {
                    logDados = lds.create(logDados);
                } catch (final Exception e) {
                    LOGGER.debug(e.getMessage(), e);
                }
            }
        }
    }

}
