package br.com.citframework.log;

import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;

public class LogFactory {

    public static String CIT_LOG = "CIT_LOG";
    public static String LOG4J_LOG = "LOG4J_LOG";
    public static String DB_LOG = "DB_LOG";

    public static Log getLog() {
        String tipoLog = null;
        try {
            tipoLog = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.TIPO_LOG, "DB_LOG");
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (tipoLog == null) {
            tipoLog = LOG4J_LOG;
        }

        if (tipoLog.equals(CIT_LOG)) {
            return new CitLog();
        }
        if (tipoLog.equals(LOG4J_LOG)) {
            return new Log4jLog();
        }
        if (tipoLog.equals(DB_LOG)) {
            return new DbLog();
        }
        return null;
    }

}
