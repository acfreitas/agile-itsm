package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.citajax.html.DocumentHTML;

public class AutoCompleteTarefaAtual extends AbstractAutoComplete {

    @Override
    public Class<ElementoFluxoDTO> getBeanClass() {
        return ElementoFluxoDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // Corrige o enconding do parâmetro desejado.
        final String consulta = new String(request.getParameter("query").getBytes("ISO-8859-1"), "UTF-8");
        final ElementoFluxoDao elementoFluxoDao = new ElementoFluxoDao();

        List<ElementoFluxoDTO> lista;
        lista = elementoFluxoDao.listaElementoFluxo(consulta);
        if (lista == null) {
            lista = new ArrayList<ElementoFluxoDTO>();
        }

        final String json = getGSON().toJson(lista);

        request.setAttribute("json_response", json);
    }

}
