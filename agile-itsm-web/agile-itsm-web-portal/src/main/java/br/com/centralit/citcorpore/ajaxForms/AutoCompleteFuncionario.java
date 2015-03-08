package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.AutoCompleteDTO;
import br.com.centralit.citcorpore.bean.FuncionarioDTO;
import br.com.centralit.citcorpore.rh.negocio.FuncionarioService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

/**
 * @author david.silva
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class AutoCompleteFuncionario extends AjaxFormAction{
	
	@Override
	public Class getBeanClass() {
		return FuncionarioDTO.class;
	}
	
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String consulta = request.getParameter("query");
		FuncionarioService service = (FuncionarioService) ServiceLocator.getInstance().getService(FuncionarioService.class, null);
		
		Collection<FuncionarioDTO> funcionarios =  new ArrayList<FuncionarioDTO>();
		funcionarios = service.findByNome(consulta);
		
		AutoCompleteDTO autoCompleteDTO = new AutoCompleteDTO();
		Gson gson = new Gson();
		List lst = new ArrayList();
		List lstVal = new ArrayList();
		
		if (funcionarios != null && !funcionarios.isEmpty()){

			for(FuncionarioDTO funcionario : funcionarios) {
				lst.add(funcionario.getNome()); 
				lstVal.add(funcionario.getIdEmpregado());
			}
				
		}
		
		autoCompleteDTO.setQuery(consulta);
		autoCompleteDTO.setSuggestions(lst);
		autoCompleteDTO.setData(lstVal);
		
		String json = gson.toJson(autoCompleteDTO);
		request.setAttribute("json_response", json);
		
	}

}
