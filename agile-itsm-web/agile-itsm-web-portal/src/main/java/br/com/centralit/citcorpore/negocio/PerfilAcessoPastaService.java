/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoPastaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.Enumerados.PermissaoAcessoPasta;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * Service de PerfilAcessoPasta.
 * 
 * @author valdoilo.damasceno
 */
public interface PerfilAcessoPastaService extends CrudService {

	/**
	 * Verifica se Usu�rio Pode Aprovar Base de Conhecimento da pasta Selecionada.
	 * 
	 * @param usuario
	 * @param idPasta
	 * @return Boolean
	 * @author valdoilo.damasceno
	 * @throws ServiceException
	 * @throws Exception
	 */
	public boolean verificarSeUsuarioAprovaBaseConhecimentoParaPastaSelecionada(UsuarioDTO usuario, Integer idPasta) throws ServiceException, Exception;

	public List<PerfilAcessoPastaDTO> validaPasta(UsuarioDTO usuario) throws Exception;

	public Collection<PerfilAcessoPastaDTO> findByIdPasta(Integer idPasta) throws Exception;

	/**
	 * Lista PERFILACESSOPASTA ATIVOS.
	 * 
	 * @param idPasta
	 * @return Collection<PerfilAcessoPastaDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<PerfilAcessoPastaDTO> listByIdPasta(Integer idPasta) throws Exception;

	/**
	 * Verifica Permiss�o de Acesso a Pasta Informada de acordo com a Heran�a de Permiss�o, caso a Pasta possua..
	 * 
	 * @param usuario
	 * @param idPasta
	 * @return SemPermissao = Sem permiss�o de acesso; Leitura = Permiss�o de Leitura; LeituraGravacao = Permiss�o de Leitura/Grava��o.
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public PermissaoAcessoPasta verificarPermissaoDeAcessoPasta(UsuarioDTO usuario, PastaDTO pastaDto) throws Exception;
}
