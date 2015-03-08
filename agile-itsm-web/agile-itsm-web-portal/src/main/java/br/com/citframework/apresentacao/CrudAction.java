package br.com.citframework.apresentacao;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.service.CrudService;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Mensagens;



public abstract class CrudAction extends SecurityAction {

	protected ActionForward executar(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (map.getPath().indexOf("/inserir") > -1) {
			return inserir(map, form, request, response);
		} else if (map.getPath().indexOf("/alterar") > -1) {
			return alterar(map, form, request, response);
		} else if (map.getPath().indexOf("/excluir") > -1) {
			return excluir(map, form, request, response);
		} else if (map.getPath().indexOf("/consultar") > -1) {
			return consultar(map, form, request, response);
		} else if (map.getPath().indexOf("/listar") > -1) {
			return listar(map, form, request, response);
		} else if (map.getPath().indexOf("/exibirInsercao") > -1) {
			return exibirInsercao(map, form, request, response);
		} else if (map.getPath().indexOf("/exibirAlteracao") > -1) {
			return exibirAlteracao(map, form, request, response);
		} else if (map.getPath().indexOf("/visualizar") > -1) {
			return visualizar(map, form, request, response);
		} else if (map.getPath().indexOf("/exibirConsulta") > -1) {
			return exibirConsulta(map, form, request, response);
		} else if (map.getPath().indexOf("/alternarLista") > -1) {
			return alternarLista(map, form, request, response);
		} else
			return null;
	}

	protected abstract ActionForward alternarLista(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception;
	protected abstract CrudService getService(HttpServletRequest request) throws Exception;
	protected abstract IDto getBean() throws Exception;
	protected abstract void validaInsercao(ActionForm form, HttpServletRequest request) throws Exception;
	protected abstract void validaAlteracao(ActionForm form, HttpServletRequest request) throws Exception;
	protected abstract void validaExclusao(ActionForm form, HttpServletRequest request) throws Exception;
	protected abstract void validaConsulta(ActionForm form, HttpServletRequest request) throws Exception;
	protected abstract void carregaListasCadastro(ActionForm form, HttpServletRequest request) throws Exception;
	protected abstract void carregaListasConsulta(ActionForm form, HttpServletRequest request) throws Exception;
	protected abstract void carregaListasVisualizacao(ActionForm form, HttpServletRequest request) throws Exception;

	protected ActionForward inserir(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		IDto bean = getBean();
		validaInsercao(form, request);
		CrudService business = getService(request);
		copyFormToModel(form, bean, request);
		business.create(bean);
		gravaMensagem(Mensagens.getValue("MSG05") , request);
		// form.reset(map,request);
		return map.findForward(Constantes.getValue("SUCESSO"));
	}

	protected ActionForward alterar(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		IDto bean = getBean();
		validaAlteracao(form, request);
		CrudService business = getService(request);
		copyFormToModel(form, bean, request);
		business.update(bean);
		gravaMensagem(Mensagens.getValue("MSG06"), request);
		// form.reset(map,request);
		return map.findForward(Constantes.getValue("SUCESSO"));
	}

	protected ActionForward excluir(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		setTipoTela(Constantes.getValue("VISUALIZACAO"), request);
	 	validaExclusao(form, request);
		IDto bean = getBean();
		CrudService business = getService(request);
		copyFormToModel(form, bean, request);
		business.delete( bean);
		gravaMensagem(Mensagens.getValue("MSG07"), request);
		return map.findForward(Constantes.getValue("SUCESSO"));
	}

	protected ActionForward consultar(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		IDto bean = getBean();
		validaConsulta(form, request);
		CrudService business = getService(request);
		copyFormToModel(form, bean, request);
		Collection col = business.find(bean);
		if (col == null || col.size() == 0) {
			throw new LogicException(Mensagens.getValue("MSG04"));
		}
		request.setAttribute(Constantes.getValue("LISTA_CONSULTA"), col);
		carregaListasConsulta(form, request);
		return map.findForward(Constantes.getValue("SUCESSO"));
	}

	protected ActionForward listar(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CrudService business = getService(request);
		Collection col = business.list();
		request.setAttribute(Constantes.getValue("LISTA"), col);
		return map.findForward(Constantes.getValue("SUCESSO"));
	}

	protected ActionForward exibirInsercao(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		setTipoTela(Constantes.getValue("INSERCAO"), request);
		carregaListasCadastro(form, request);
		return map.findForward(Constantes.getValue("SUCESSO"));
	}

	protected ActionForward exibirAlteracao(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (request.getRequestURL().indexOf("/exibirAlteracao.do") > -1) {
			IDto bean = getBean();
			copyFormToModel(form, bean, request);
			CrudService business = getService(request);
			bean = business.restore(bean);
			copyModelToForm(bean, form, request);
		}
		setTipoTela(Constantes.getValue("ALTERACAO"), request);
		carregaListasCadastro(form, request);
		return map.findForward(Constantes.getValue("SUCESSO"));
	}

	protected ActionForward visualizar(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		setTipoTela(Constantes.getValue("VISUALIZACAO"), request);
		IDto bean = getBean();
		copyFormToModel(form, bean, request);
		CrudService business = getService(request);
		bean = business.restore(bean);
		copyModelToForm(bean, form, request);
		carregaListasVisualizacao(form, request);
		return map.findForward(Constantes.getValue("SUCESSO"));
	}

	protected ActionForward exibirConsulta(ActionMapping map, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		carregaListasConsulta(form, request);
		form.reset(map, request);
				
		setTipoTela(Constantes.getValue("EXIBIRCONSULTA"), request);
		return map.findForward(Constantes.getValue("SUCESSO"));
	}

	protected void setTipoTela(String tipo, HttpServletRequest req) {
		req.setAttribute(Constantes.getValue("TIPO_TELA"), tipo);
	}

}
