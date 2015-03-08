package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PedidoPortalDTO;
import br.com.centralit.citcorpore.bean.PortalDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface PortalService extends CrudService {

	public Collection<PortalDTO> findByCondition(Integer i) throws ServiceException, Exception;
	public Collection<PortalDTO> findByCondition(Integer idUsuario, Integer idItem) throws ServiceException, Exception;
	public Collection<PortalDTO> listByUsuario(Integer idUsuario) throws Exception;
	public PedidoPortalDTO criarPedidoSolicitacao(PedidoPortalDTO pedidoPortalDTO, UsuarioDTO usuarioDTO,  Collection<SolicitacaoServicoQuestionarioDTO> colecaoRespQuestionario, Collection<UploadDTO> arquivosUpados) throws ServiceException, Exception;
	public void relacionaImpactoUrgencia(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception;
	
	/**
	 * @return
	 */
	public boolean existeQuestionario(final Integer idServico) throws ServiceException, Exception;
	
	/**
	 * @param idServico
	 * @return
	 */
	public boolean existeQuestionarioServico(final Integer idServico) throws ServiceException, Exception;
	
	/**
	 * @param idServicoCatalogo
	 * @return
	 * @throws ServiceException 
	 */
	public Integer obterIdQuestionarioServico(final Integer idServicoCatalogo) throws ServiceException;
	
}
