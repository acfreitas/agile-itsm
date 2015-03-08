package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaServicoDao;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaServicoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings("rawtypes")
public class RequisicaoMudancaServico extends AjaxFormAction implements IRequisicaoMudancaRelacionamento{

	private RequisicaoMudancaServicoService requisicaoMudancaServicoService;
	private ServicoService servicoService;

	public RequisicaoMudancaServico() {

	}

	@Override
	public Class getBeanClass() {
		return RequisicaoMudancaServicoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	/**
	 * Registra os ics relacionados na entidade relacionamento
	 *
	 * @param idRequisicaoMudanca
	 * @param listaICsDeserializada
	 * @author breno.guimaraes
	 * @throws Exception
	 * @throws ServiceException
	 * @throws LogicException
	 */
	@SuppressWarnings("unchecked")
	public void gravarRelacionamentos(Integer idRequisicaoMudanca, Collection listaDeserializada) throws LogicException, ServiceException, Exception{
		ArrayList<RequisicaoMudancaServicoDTO> reqMudancaServicos = (ArrayList<RequisicaoMudancaServicoDTO>) listaDeserializada;
		ArrayList<RequisicaoMudancaServicoDTO> servicosBanco = null;
		RequisicaoMudancaServicoDTO aux = null;

		if(listaDeserializada != null){
			//se não existir no banco, cria, caso contrário, atualiza

			for(RequisicaoMudancaServicoDTO i : reqMudancaServicos){
				i.setIdRequisicaoMudanca(idRequisicaoMudanca);

				aux = (RequisicaoMudancaServicoDTO) getRequisicaoMudancaServicoService().restoreByChaveComposta(i);

				if(aux == null){
					getRequisicaoMudancaServicoService().create(i);
				} else {
					getRequisicaoMudancaServicoService().update(aux);
				}
			}

		}
		//confere se existe algo no banco que não está na lista salva, e deleta
		servicosBanco = getRequisicaoMudancaServicoService().listByIdRequisicaoMudanca(idRequisicaoMudanca);
		if(servicosBanco != null){
			for(RequisicaoMudancaServicoDTO i : servicosBanco){
				if(!requisicaoMudancaServicoExisteNaLista(i, reqMudancaServicos)){
					getRequisicaoMudancaServicoService().delete(i);
				}
			}
		}
	}

	/**
	 * Verifica se o item existe na lista.
	 *
	 * @param item
	 * @param lista
	 * @return
	 */
	private boolean requisicaoMudancaServicoExisteNaLista(RequisicaoMudancaServicoDTO item, ArrayList<RequisicaoMudancaServicoDTO> lista){
		if(lista == null) return false;

		for(RequisicaoMudancaServicoDTO l : lista){
			//equals sobrescrito
			if(l.equals(item)){
				return true;
			}
		}

		return false;
	}

	/**
	 * Lista os ics relacionados a requisição de mudança
	 * e atribui o nome do item de configuração para correta
	 * restauração de suas informações na table
	 *
	 * @param RequisicaoMudancaServicoDTO
	 * @throws ServiceException
	 * @throws Exception
	 */
	public ArrayList<RequisicaoMudancaServicoDTO> listItensRelacionadosRequisicaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException, Exception{
		ServicoDTO req = null;

		ArrayList<RequisicaoMudancaServicoDTO> listaReqMudancaServico = (ArrayList<RequisicaoMudancaServicoDTO>) new RequisicaoMudancaServicoDao().findByIdMudancaEDataFim(requisicaoMudancaDTO.getIdRequisicaoMudanca());
					//getRequisicaoMudancaServicoService().listByIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());

		//atribui informações adicionais para os itens retornados
		if(listaReqMudancaServico != null){
			for(RequisicaoMudancaServicoDTO r : listaReqMudancaServico){
				req = (ServicoDTO) getServicoService().restore(r);
				r.setNome(req.getNomeServico());
				r.setDescricao(req.getDetalheServico());
			}
		}

		return listaReqMudancaServico;
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}

	/**
	 * @return EmpregadoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	private RequisicaoMudancaServicoService getRequisicaoMudancaServicoService() throws ServiceException, Exception{
		if(requisicaoMudancaServicoService == null){
			requisicaoMudancaServicoService = (RequisicaoMudancaServicoService) ServiceLocator.getInstance().getService(RequisicaoMudancaServicoService.class, null);
		}
		return requisicaoMudancaServicoService;
	}

	/**
	 * @return EmpregadoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	private ServicoService getServicoService() throws ServiceException, Exception{
		if(servicoService == null){
			servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		}
		return servicoService;
	}
}
