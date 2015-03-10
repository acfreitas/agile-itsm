package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteUsuario extends AbstractAutoComplete {

    @Override
    public Class<UsuarioDTO> getBeanClass() {
        return UsuarioDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String consulta = request.getParameter("query");
        final Collection<UsuarioDTO> usuarios = getService().consultarUsuarioPorNomeAutoComplete(consulta);

        final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
        final List<String> lst = new ArrayList<>();
        final List<Integer> lstVal = new ArrayList<>();

        for (final UsuarioDTO usuario : usuarios) {
            lst.add(usuario.getNomeUsuario());
            lstVal.add(usuario.getIdUsuario());
        }

        autoCompleteDTO.setQuery(consulta);
        autoCompleteDTO.setSuggestions(lst);
        autoCompleteDTO.setData(lstVal);

        final String json = getGSON().toJson(autoCompleteDTO);
        request.setAttribute("json_response", json);
    }

    private static UsuarioService service;

    private UsuarioService getService() throws Exception {
        if (service == null) {
            service = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        }
        return service;
    }

}
