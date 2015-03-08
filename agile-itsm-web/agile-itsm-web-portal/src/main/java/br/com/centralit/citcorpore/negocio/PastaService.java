/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

/**
 * Service de Pasta.
 * 
 * @author valdoilo.damasceno
 */
/**
 * @author Thays
 * 
 */
@SuppressWarnings("rawtypes")
public interface PastaService extends CrudService {

	/**
	 * Lista Pasta e Subpastas conforme PERFIL DE ACESSO do Usuário LOGADO.
	 * 
	 * @param usuario
	 * @return Collection<PastaDTO>
	 * @throws Exception
	 */
	public Collection<PastaDTO> listPastasESubpastas(UsuarioDTO usuario) throws Exception;

	/**
	 * Lista Pasta e Subpastas.
	 * 
	 * @param usuario
	 * @return Collection<PastaDTO>
	 * @throws Exception
	 */
	public Collection<PastaDTO> listPastasESubpastas() throws Exception;

	/**
	 * Consulta Pastas Ativas. Pastas que não possuem dataFim.
	 * 
	 * @return pastasAtivas
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public Collection<PastaDTO> consultarPastasAtivas() throws ServiceException, Exception;

	/**
	 * Cria uma Nova Pasta no CITSmart.
	 * 
	 * @param pastaBean
	 * @return PastaDTO
	 * @throws LogicException
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public PastaDTO create(PastaDTO pastaBean) throws LogicException, ServiceException;

	/**
	 * Atualiza pasta.
	 * 
	 * @param pastaBean
	 * @return
	 * @throws LogicException
	 * @throws ServiceException
	 */
	public void update(PastaDTO pastaBean) throws LogicException, ServiceException;

	/**
	 * Exclui Pasta.
	 * 
	 * @param pastaBean
	 * @return boolean
	 * @author valdoilo.damasceno
	 * @throws Exception
	 */
	public boolean excluirPasta(PastaDTO pastaBean) throws Exception;

	/**
	 * Lista Pastas Ativas que não possuem pasta pai.
	 * 
	 * @return listaPastas
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection listPastaSuperiorSemPai() throws Exception;

	/**
	 * Consulta Pastas que não possuam Pastas Filhas.
	 * 
	 * @return listaPastas
	 * @throws ServiceException
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<PastaDTO> listSubPastaByPerfilAcessoUsuario(PastaDTO subPastas, UsuarioDTO usuario) throws Exception;

	/**
	 * Busca somente pastas com perfil de acesso onde usuario logado tenha acesso... Funcionalidade #340
	 * 
	 * @param usuario
	 * @param idgrupo
	 * @return
	 * @throws Exception
	 * @author thiago.cardoso
	 */
	public List<PastaDTO> listPastaByUsuario(UsuarioDTO usuario) throws Exception;

	/**
	 * Método para verificar se caso exista uma pasta com o mesmo nome
	 * 
	 * @author rodrigo.oliveira
	 * @param pastaDTO
	 * @return Se caso exista pasta com o mesmo nome retorna true
	 * @throws Exception
	 */
	public boolean verificaSeExistePasta(PastaDTO pastaDTO) throws Exception;

	/**
	 * Verifica se Usuário possui acesso a pasta informada.
	 * 
	 * @param pastaDto
	 * @param usuarioDto
	 * @return true - caso possua; false - se não possuir.
	 * @throws Exception
	 * @throws ServiceException
	 */
	public boolean verificarSeUsuarioPossuiAcessoPasta(PastaDTO pastaDto, UsuarioDTO usuarioDto) throws Exception;

	/**
	 * Verifica se usuário possui acesso a pasta, considerando a sua hierarquia superior de pastas. Caso o usuário não possua acesso a alguma pasta superior, o usuário não possuirá acesso a subpasta
	 * informada.
	 * 
	 * @param pastaDto
	 * @param usuarioDto
	 * @return true - se possuir acesso; false - se não possuir acesso.
	 * @throws ServiceException
	 * @throws Exception
	 */
	public boolean verificaPermissaoDeAcessoPasta(PastaDTO pastaDto, UsuarioDTO usuarioDto) throws ServiceException, Exception;

	/**
	 * Retorna PastaDTO que da permissão para Pasta Informada.
	 * 
	 * @param subPasta
	 * @return PastaDTO
	 * @throws ServiceException
	 * @throws LogicException
	 * @author Vadoilo Damasceno
	 */
	public PastaDTO obterHerancaDePermissao(PastaDTO subPasta) throws ServiceException, LogicException;

	/**
	 * Retorna lista de subpastas
	 * 
	 * @param pastaSuperior
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<PastaDTO> listSubPastas(PastaDTO pastaSuperior) throws Exception;

	/**
	 * Cria Notificação
	 * 
	 * @param pastaDto
	 * @param transactionControler
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 * @author Thays
	 */
	public NotificacaoDTO criarNotificacao(PastaDTO pastaDto, TransactionControler transactionControler) throws ServiceException, Exception;

	/**
	 * Lista Pasta Pai.
	 * 
	 * @return Collection<PastaDTO>
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Collection<PastaDTO> listPastaSuperiorFAQSemPai() throws Exception;

	/**
	 * Lista SubPastas do Tipo FAQ.
	 * 
	 * @param pasta
	 * @return Collection<PastaDTO>
	 * @throws Exception
	 * @author Thays
	 */
	public Collection<PastaDTO> listSubPastasFAQ(PastaDTO pasta) throws Exception;
	
	/**
	 * Lista Pasta Pai.
	 * 
	 * @return Collection<PastaDTO>
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Collection<PastaDTO> listPastaSuperiorErroConhecidoSemPai() throws Exception;
	
	
	/**
	 * Lista SubPastas do Tipo Erro Conhecido.
	 * 
	 * @param pasta
	 * @return Collection<PastaDTO>
	 * @throws Exception
	 * @author Thays
	 */
	public Collection<PastaDTO> listSubPastasErroConhecido(PastaDTO pasta) throws Exception;
	
	
	/**
	 * Verifica se existe pasta pai e se ele herda.
	 * @param idPastaFilho
	 * @return
	 * @throws Exception
	 * @author mario.haysaki
	 */
	public PastaDTO idpastaPaiEHerdaDaPastaPai(Integer idPastaFilho) throws Exception;
	
	

}
