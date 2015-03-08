package br.com.centralit.citquestionario.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.citframework.dto.Usuario;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.WebUtil;

public class AcessaQuestionario extends AjaxFormAction {

    @Override
    public Class<Usuario> getBeanClass() {
        return Usuario.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final Usuario acessaQuestionarioDTO = (Usuario) document.getBean();
        if (acessaQuestionarioDTO == null) {
            request.getSession().invalidate();
            return;
        }
        if (acessaQuestionarioDTO.getIdUsuario() == null) {
            request.getSession().invalidate();
            return;
        }

        if (Constantes.SERVER_ADDRESS == null || Constantes.SERVER_ADDRESS.equalsIgnoreCase("")) {
            String url = request.getRequestURL().toString();
            int index = -1;
            if (request.getContextPath() != null && !request.getContextPath().equalsIgnoreCase("") && !request.getContextPath().equalsIgnoreCase("/")) {
                index = url.indexOf(request.getContextPath());
            }
            if (index > -1) {
                url = url.substring(0, index);
            } else {
                String urlInicial = "";
                index = url.indexOf("http://");
                if (index > -1) {
                    url = url.substring(6, index);
                    urlInicial = "http://";
                } else {
                    index = url.indexOf("https://");
                    if (index > -1) {
                        url = url.substring(7, index);
                        urlInicial = "https://";
                    }
                }
                for (int i = 0; i < url.length(); i++) {
                    if (url.charAt(i) == '/') {
                        break;
                    } else {
                        urlInicial += url.charAt(i);
                    }
                }
                url = urlInicial;
            }
            br.com.citframework.util.Constantes.SERVER_ADDRESS = url;
        }

        acessaQuestionarioDTO.setIdUsuarioSistema(new Integer(acessaQuestionarioDTO.getIdUsuario()));
        WebUtil.setUsuario(acessaQuestionarioDTO, request);
    }

}
