package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.util.Arvore;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

public class AutoCompleteUnidade extends AbstractAutoComplete {

    @Override
    public Class<UnidadeDTO> getBeanClass() {
        return UnidadeDTO.class;
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String consulta = request.getParameter("query");

        final String unidadeVinculoContrato = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");

        Integer idContrato = 0;
        if (unidadeVinculoContrato != null && unidadeVinculoContrato.equalsIgnoreCase("S")) {
            final String idContratoStr = request.getParameter("idContrato");
            idContrato = NumberUtils.toInt(idContratoStr, -1);
        }

        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        EmpregadoDTO empregado = new EmpregadoDTO();
        try {
            final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
            empregado.setIdEmpregado(usuario.getIdEmpregado());
            empregado = (EmpregadoDTO) empregadoService.restore(empregado);
        } catch (final Exception e) {
            e.printStackTrace();
        }

        final Integer idUnidadeColaborador = empregado.getIdUnidade() != null && empregado.getIdUnidade().intValue() > 0 ? empregado.getIdUnidade() : 0;

        final String consideraHierarquia = request.getParameter("consideraHierarquia");

        String tipoHierarquia;
        if (consideraHierarquia == null) {
            tipoHierarquia = "1";

        } else {
            tipoHierarquia = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.TIPO_HIERARQUIA_UNIDADE, "1");
        }

        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
        final Arvore arvore = unidadeService.obtemArvoreUnidades(consulta, idContrato, idUnidadeColaborador, tipoHierarquia, 40);

        String json = "";
        final Collection<UnidadeDTO> listaResultado = new ArrayList<>();
        if (request.getParameter("colection") != null) {
            listaResultado.clear();
            UnidadeDTO unidadeDTO;
            for (int i = 0; i < arvore.getListaID().size(); i++) {
                unidadeDTO = new UnidadeDTO();
                unidadeDTO.setIdUnidade(arvore.getListaID().get(i));
                unidadeDTO.setNome(arvore.getListaTexto().get(i));
                listaResultado.add(unidadeDTO);
            }
            json = getGSON().toJson(listaResultado);
        } else {
            final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
            autoCompleteDTO.setQuery(consulta);
            autoCompleteDTO.setSuggestions(arvore.getListaTexto());
            autoCompleteDTO.setData(arvore.getListaID());
            json = getGSON().toJson(autoCompleteDTO);
        }
        request.setAttribute("json_response", json);
    }

}
