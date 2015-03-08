package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoItemConfiguracaoService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings("rawtypes")
public class RequisicaoLiberacaoItemConfiguracao extends AjaxFormAction implements IRequisicaoMudancaRelacionamento{

	private RequisicaoLiberacaoItemConfiguracaoService requisicaoLiberacaoItemConfiguracaoService;
	private ItemConfiguracaoService itemConfiguracaoService;

	@Override
	public Class getBeanClass() {
		return RequisicaoLiberacaoItemConfiguracaoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	}

	/**
	 * Registra os ics relacionados na entidade relacionamento
	 *
	 * @param idRequisicaoLiberacao
	 * @param listaICsDeserializada
	 * @author murilo.pacheco
	 * @throws Exception
	 * @throws ServiceException
	 * @throws LogicException
	 */
	@SuppressWarnings("unchecked")
	public void gravarRelacionamentos(Integer idRequisicaoLiberacao, Collection listaDeserializada) throws LogicException, ServiceException, Exception{

		ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> ics = (ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO>) listaDeserializada;
		ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> icsBanco = null;
		RequisicaoLiberacaoItemConfiguracaoDTO aux = null;

		if(listaDeserializada != null){
			//se não existir no banco, cria, caso contrário, atualiza
			for(RequisicaoLiberacaoItemConfiguracaoDTO i : ics){

				i.setIdRequisicaoLiberacao(idRequisicaoLiberacao);

				aux = (RequisicaoLiberacaoItemConfiguracaoDTO) getRequisicaoLiberacaoItemConfiguracaoService().restoreByChaveComposta(i);

				if(aux == null){
					getRequisicaoLiberacaoItemConfiguracaoService().create(i);
				} else {
					getRequisicaoLiberacaoItemConfiguracaoService().update(aux);
				}
			}
		}
		//confere se existe algo no banco que não está na lista salva, e deleta
		icsBanco = getRequisicaoLiberacaoItemConfiguracaoService().listByIdRequisicaoLiberacao(idRequisicaoLiberacao);
		if (icsBanco != null) {
			for(RequisicaoLiberacaoItemConfiguracaoDTO i : icsBanco){
				if(!requisicaoMudancaICExisteNaLista(i, ics)){
					getRequisicaoLiberacaoItemConfiguracaoService().delete(i);
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
	private boolean requisicaoMudancaICExisteNaLista(RequisicaoLiberacaoItemConfiguracaoDTO item, ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> lista){
		if(lista == null) return false;

		for(RequisicaoLiberacaoItemConfiguracaoDTO l : lista){

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
	public ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> listItensRelacionadosRequisicaoMudanca(RequisicaoLiberacaoDTO requisicaoLiberacaoDTO) throws ServiceException, Exception{
		ItemConfiguracaoDTO ic = null;

		ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> listaReqLiberacaoIC =
					getRequisicaoLiberacaoItemConfiguracaoService().listByIdRequisicaoLiberacao(requisicaoLiberacaoDTO.getIdRequisicaoLiberacao());

		//atribui nome para os itens retornados
		if(listaReqLiberacaoIC != null){
			for(RequisicaoLiberacaoItemConfiguracaoDTO r : listaReqLiberacaoIC){
				ic = getItemConfiguracaoService().restoreByIdItemConfiguracao(r.getIdItemConfiguracao());
				if(ic != null){
					r.setNomeItemConfiguracao(ic.getIdentificacao());
				}
			}
		}

		return listaReqLiberacaoIC;
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
	private RequisicaoLiberacaoItemConfiguracaoService getRequisicaoLiberacaoItemConfiguracaoService() throws ServiceException, Exception{
		if(requisicaoLiberacaoItemConfiguracaoService == null){
			requisicaoLiberacaoItemConfiguracaoService = (RequisicaoLiberacaoItemConfiguracaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoItemConfiguracaoService.class, null);
		}
		return requisicaoLiberacaoItemConfiguracaoService ;
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

	@Override
	public ArrayList listItensRelacionadosRequisicaoMudanca(
			RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException,
			Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
