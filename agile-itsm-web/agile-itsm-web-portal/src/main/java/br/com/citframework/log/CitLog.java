package br.com.citframework.log;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTratamentoArquivos;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CitLog implements Log {

    private String pathLog;
    private String fileLog;
    private String extLog;

    /**
     * @author breno.guimaraes
     */
    public CitLog() {
        this.inicializarParametros();
    }

    private void inicializarParametros() {
        pathLog = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.PATH_LOG, "C:/Program Files/jboss/server/default/deploy/CitCorpore.war/tempFiles");
        fileLog = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.FILE_LOG, "/log");
        extLog = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.EXT_LOG, "txt");
    }

    @Override
    public void registraLog(final String mensagem, final Class classe, final String tipoMensagem) throws Exception {
        final Date dataAtual = UtilDatas.getDataAtual();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String nomeArquivo = pathLog + fileLog + "_" + sdf.format(dataAtual) + "." + extLog;
        synchronized (nomeArquivo) {
            final List lista = new ArrayList();
            sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            lista.add("[" + tipoMensagem + "] - " + sdf.format(dataAtual) + " - " + classe.getName() + " - " + mensagem);
            UtilTratamentoArquivos.geraFileTXT(nomeArquivo, lista, true);
        }
    }

    @Override
    public void registraLog(final Exception e, final Class classe, final String tipoMensagem) throws Exception {
        final Date dataAtual = UtilDatas.getDataAtual();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String nomeArquivo = pathLog + fileLog + "_" + sdf.format(dataAtual) + "." + extLog;
        final List lista = new ArrayList();
        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        lista.add("[" + tipoMensagem + "] - " + sdf.format(dataAtual) + " - " + classe.getName() + " - Exception:");
        synchronized (nomeArquivo) {
            try (final FileOutputStream fos = new FileOutputStream(nomeArquivo, true)) {
                final PrintStream out = new PrintStream(fos);
                UtilTratamentoArquivos.geraFileTXT(nomeArquivo, lista, out);
                e.printStackTrace(out);
            }
        }
    }

}
