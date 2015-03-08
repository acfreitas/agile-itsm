package br.com.centralit.citajax.html;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings("rawtypes")
public abstract class AjaxFormAction {

    private static final Logger LOGGER = Logger.getLogger(AjaxFormAction.class);

    public abstract void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception;

    /**
     * Utilizado para setar qual classe representa o bean.
     *
     * @return
     */
    public abstract Class<?> getBeanClass();

    public Object getUsuario(final HttpServletRequest request) {
        return request.getSession().getAttribute("USUARIO");
    }

    public String getFromGed(final Integer idControleGed) throws Exception {
        final Integer idEmpresa = 1;
        final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
        controleGEDDTO.setIdControleGED(idControleGed);
        controleGEDDTO = (ControleGEDDTO) controleGedService.restore(controleGEDDTO);
        String pasta = "";
        if (controleGEDDTO != null) {
            pasta = controleGEDDTO.getPasta();
        }

        String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, null);
        if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
            PRONTUARIO_GED_DIRETORIO = "";
        }

        if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
            PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
        }

        if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
            PRONTUARIO_GED_DIRETORIO = "/ged";
        }
        String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
        if (PRONTUARIO_GED_INTERNO == null) {
            PRONTUARIO_GED_INTERNO = "S";
        }
        String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
        if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
            prontuarioGedInternoBancoDados = "N";
        }
        if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
            if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se utiliza GED interno e eh BD
                // TODO falta implementar!
            } else {
                final String fileRec = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload/REC_FROM_GED_" + controleGEDDTO.getIdControleGED() + "." + controleGEDDTO.getExtensaoArquivo();
                CriptoUtils.decryptFile(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", fileRec, System
                        .getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));

                return fileRec;
            }
        }
        return null;
    }

    public String i18n_Message(final String key, final UsuarioDTO usuario) {
        if (usuario != null) {
            if (UtilI18N.internacionaliza(usuario.getLocale(), key) != null) {
                return UtilI18N.internacionaliza(usuario.getLocale(), key);
            }
            return key;
        }
        return key;
    }

    /**
     * Retorna a linguagem que foi passada no request que está em paramtersDefinition. ATENÇÃO: UTILIZADO SOMENTE PARA A GERAÇÃO DE RELATÓRIOS EM PAINEL. Este método também está
     * implementando em
     * br.com.centralit.citgerencial.bean.GerencialGenerateService.java.
     *
     * @param paramtersDefinition
     * @return String - Language
     * @author valdoilo.damasceno
     * @since 06.02.2014
     */
    public String getLanguage(final Collection paramtersDefinition) {
        String language = UtilI18N.PORTUGUESE_SIGLA;

        for (final Iterator iterator = paramtersDefinition.iterator(); iterator.hasNext();) {
            final Object parametro = iterator.next();

            if (parametro != null && "org.apache.catalina.connector.RequestFacade".equals(parametro.getClass().getName())) {
                final HttpServletRequest request = (HttpServletRequest) parametro;

                final String aux = (String) request.getSession().getAttribute("locale");

                if (aux != null && StringUtils.isNotBlank(aux)) {
                    language = aux;
                }

                break;
            }
        }

        return language;
    }

    protected void debugValuesFromRequest(final Map<String, Object> hashValores) {
        final Set<Entry<String, Object>> set = hashValores.entrySet();
        final Iterator<Entry<String, Object>> i = set.iterator();

        LOGGER.debug("------- VALORES DO REQUEST: -------");
        while (i.hasNext()) {
            final Entry<String, Object> me = i.next();
            LOGGER.debug("-------------> [" + me.getKey() + "]: [" + me.getValue() + "]");
        }
    }

}
