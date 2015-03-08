package br.com.centralit.citcorpore.ajaxForms;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.InternacionalizarDTO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.XmlReadLookup;

public class Internacionalizar extends AjaxFormAction {

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

    }

    public void internacionaliza(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final InternacionalizarDTO bean = (InternacionalizarDTO) document.getBean();

        final String idiomaPadrao = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.IDIOMAPADRAO, "");

        request.getSession(true).setAttribute("menu", null);
        request.getSession(true).setAttribute("menuPadrao", null);

        if (bean != null && StringUtils.isNotBlank(bean.getLocale())) {
            WebUtil.setLocale(bean.getLocale().trim(), request);
            XmlReadLookup.getInstance(new Locale(bean.getLocale().trim()));
        } else {
            WebUtil.setLocale(idiomaPadrao, request);
            XmlReadLookup.getInstance(new Locale(idiomaPadrao));
        }

        document.executeScript("window.location.reload(true)");
    }

    /**
     * Realiza a internacionalização das opções de Sim(S)/Não(N) do sistema
     *
     * <p>
     * Ex: Se o usuário estiver utilizando o sistema com o idioma em inglês, ele deverá entrar com o valor "Y" para Sim e "N" para Não. O sistema deve converter o valor inserido no
     * input para o valor utilizado internamente. Ex: Y = S
     * </p>
     *
     * @param request
     * @param method
     * @param opt
     * @return String
     * @author rodrigo.acorse
     * @since 21/03/2014
     */
    public static String internacionalizaOptionSN(final HttpServletRequest request, final String method, String opt) {
        final String lang = (String) request.getSession().getAttribute("locale");

        if (lang != null && lang.equalsIgnoreCase("en") && method.equalsIgnoreCase("save")) {
            if (opt.trim().equalsIgnoreCase("y")) {
                opt = "S";
            }
        } else if (lang != null && lang.equalsIgnoreCase("en") && method.equalsIgnoreCase("restore")) {
            if (opt.trim().equalsIgnoreCase("s")) {
                opt = "Y";
            }
        }

        return opt;
    }

    @Override
    public Class<InternacionalizarDTO> getBeanClass() {
        return InternacionalizarDTO.class;
    }

}
