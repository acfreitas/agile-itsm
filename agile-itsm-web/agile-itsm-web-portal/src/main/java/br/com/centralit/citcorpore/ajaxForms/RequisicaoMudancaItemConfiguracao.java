package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaItemConfiguracaoService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings("rawtypes")
public class RequisicaoMudancaItemConfiguracao extends AjaxFormAction implements IRequisicaoMudancaRelacionamento{

	private RequisicaoMudancaItemConfiguracaoService requisicaoMudancaItemConfiguracaoService;
	private ItemConfiguracaoService itemConfiguracaoService;

	@Override
	public Class getBeanClass() {
		return RequisicaoMudancaItemConfiguracaoDTO.class;
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

		ArrayList<RequisicaoMudancaItemConfiguracaoDTO> ics = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) listaDeserializada;
		ArrayList<RequisicaoMudancaItemConfiguracaoDTO> icsBanco = null;
		RequisicaoMudancaItemConfiguracaoDTO aux = null;

		if(listaDeserializada != null){
			//se não existir no banco, cria, caso contrário, atualiza
			for(RequisicaoMudancaItemConfiguracaoDTO i : ics){

				i.setIdRequisicaoMudanca(idRequisicaoMudanca);

				aux = (RequisicaoMudancaItemConfiguracaoDTO) getRequisicaoMudancaItemConfiguracaoService().restoreByChaveComposta(i);

				if(aux == null){
					getRequisicaoMudancaItemConfiguracaoService().create(i);
				} else {
					getRequisicaoMudancaItemConfiguracaoService().update(aux);
				}
			}
		}
		//confere se existe algo no banco que não está na lista salva, e deleta
		icsBanco = getRequisicaoMudancaItemConfiguracaoService().listByIdRequisicaoMudanca(idRequisicaoMudanca);
		if (icsBanco != null) {
			for(RequisicaoMudancaItemConfiguracaoDTO i : icsBanco){
				if(!requisicaoMudancaICExisteNaLista(i, ics)){
					getRequisicaoMudancaItemConfiguracaoService().delete(i);
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
	private boolean requisicaoMudancaICExisteNaLista(RequisicaoMudancaItemConfiguracaoDTO item, ArrayList<RequisicaoMudancaItemConfiguracaoDTO> lista){
		if(lista == null) return false;

		for(RequisicaoMudancaItemConfiguracaoDTO l : lista){

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
	 * @param requisicaoMudancaItemConfiguracaoDTO
	 * @throws ServiceException
	 * @throws Exception
	 */
	public ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listItensRelacionadosRequisicaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException, Exception{
		ItemConfiguracaoDTO ic = null;

		ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listaReqMudancaIC =
					getRequisicaoMudancaItemConfiguracaoService().listByIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());

		//atribui nome para os itens retornados
		if(listaReqMudancaIC != null){
			for(RequisicaoMudancaItemConfiguracaoDTO r : listaReqMudancaIC){
				ic = getItemConfiguracaoService().restoreByIdItemConfiguracao(r.getIdItemConfiguracao());
				if(ic != null){
					r.setNomeItemConfiguracao(ic.getIdentificacao());
				}
			}
		}

		return listaReqMudancaIC;
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
	 * @return RequisicaoMudancaItemConfigurcaoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	private RequisicaoMudancaItemConfiguracaoService getRequisicaoMudancaItemConfiguracaoService() throws ServiceException, Exception{
		if(requisicaoMudancaItemConfiguracaoService == null){
			requisicaoMudancaItemConfiguracaoService = (RequisicaoMudancaItemConfiguracaoService) ServiceLocator.getInstance().getService(RequisicaoMudancaItemConfiguracaoService.class, null);
		}
		return requisicaoMudancaItemConfiguracaoService ;
	}

	/**
	 * @return EmpregadoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	private ItemConfiguracaoService getItemConfiguracaoService() throws ServiceException, Exception{
		if(itemConfiguracaoService == null){
			itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		}
		return itemConfiguracaoService;
	}
}
