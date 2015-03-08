package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.util.Arvore;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

@SuppressWarnings("rawtypes")
public class AutoCompleteUnidade extends AjaxFormAction {

	@Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String consulta = request.getParameter("query");

        final String UNIDADE_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS,
                "N");

        
		Integer idContrato = null;
        if (UNIDADE_VINC_CONTRATOS != null && UNIDADE_VINC_CONTRATOS.equalsIgnoreCase("S")) {
            final String idContratoStr = request.getParameter("idContrato");
            if (idContratoStr != null && !idContratoStr.equalsIgnoreCase("")) {
				idContrato = Integer.parseInt(idContratoStr);
			} else {
				idContrato = -1;
			}
		} else {
			idContrato = 0;
		}
        
 
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		EmpregadoDTO empregadoDTO = new EmpregadoDTO();
		try {
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			empregadoDTO.setIdEmpregado(usuario.getIdEmpregado());
			empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
		} catch (LogicException e1) {
			e1.printStackTrace();
		} catch (ServiceException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer idUnidadeColaborador = ((empregadoDTO.getIdUnidade() != null) && (empregadoDTO.getIdUnidade().intValue() > 0)) ? empregadoDTO.getIdUnidade() : 0;
	    
		
		final String consideraHierarquia = request.getParameter("consideraHierarquia");
		
		String tipoHierarquia;
		if ((consideraHierarquia==null) ) {
			tipoHierarquia = "1";
			
		} else {
			tipoHierarquia = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.TIPO_HIERARQUIA_UNIDADE, "1");	
		}

		Collection<UnidadeDTO> listaResultado = new ArrayList<UnidadeDTO>();
		Arvore arvore = new Arvore();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		arvore = unidadeService.obtemArvoreUnidades(consulta, idContrato, idUnidadeColaborador, tipoHierarquia, 40);

        final Gson gson = new Gson();
		String json = "";
		if (request.getParameter("colection") != null) {
			listaResultado.clear();
			UnidadeDTO unidadeDTO;
			for (int i = 0; i < arvore.getListaID().size(); i++) {
				unidadeDTO = new UnidadeDTO();
				unidadeDTO.setIdUnidade(arvore.getListaID().get(i));
				unidadeDTO.setNome(arvore.getListaTexto().get(i));
				listaResultado.add(unidadeDTO);
			}
			json = gson.toJson(listaResultado);
		} else {
            final AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
			autoCompleteDTO.setQuery(consulta);
			autoCompleteDTO.setSuggestions(arvore.getListaTexto());
			autoCompleteDTO.setData(arvore.getListaID());
			json = gson.toJson(autoCompleteDTO);
		}
		request.setAttribute("json_response", json);
	}

	@Override
	public Class getBeanClass() {
		return UnidadeDTO.class;
	}

}