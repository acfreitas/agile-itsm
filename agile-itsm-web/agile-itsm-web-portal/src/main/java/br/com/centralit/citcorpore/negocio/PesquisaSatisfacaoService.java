/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PesquisaSatisfacaoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author valdoilo
 * 
 */
public interface PesquisaSatisfacaoService extends CrudService {
	Collection<PesquisaSatisfacaoDTO> getPesquisaByIdSolicitacao(int idServico);
	
	Collection<PesquisaSatisfacaoDTO> relatorioPesquisaSatisfacao(PesquisaSatisfacaoDTO pesquisaSatisfacaoDTO) throws ServiceException, Exception;
	
	public PesquisaSatisfacaoDTO findByIdSolicitacaoServico(Integer idSolicitacaoServico) throws Exception;
}
