package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

/**
 * Service de ImportanciaConhecimentoGrupo.
 * 
 * @author Vadoilo Damasceno
 * 
 */
public interface ImportanciaConhecimentoGrupoService extends CrudService {

	/**
	 * Exclui ImportanciaConhecimentoGrupo por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @param transactionControler
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void deleteByIdConhecimento(Integer idBaseConhecimento, TransactionControler transactionControler) throws Exception;

	/**
	 * Cria nova ImportanciaConhecimentoGrupo.
	 * 
	 * @param importanciaConhecimentoGrupo
	 * @param transactionControler
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public void create(ImportanciaConhecimentoGrupoDTO importanciaConhecimentoGrupo, TransactionControler transactionControler) throws Exception;

	/**
	 * Lista ImportanciaConhecimentoGrupo por idBaseConhecimento.
	 * 
	 * @param idBaseConhecimento
	 * @return Collection<ImportanciaConhecimentoGrupoDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<ImportanciaConhecimentoGrupoDTO> listByIdBaseConhecimento(Integer idBaseConhecimento) throws Exception;

	/**
	 * Obtem o Grau de Importância do Conhecimento para o Usuário e para os Grupos do Usuário.
	 * 
	 * @param baseConhecimentoDto
	 * @param listGrupoEmpregado
	 * @return Collection<ImportanciaConhecimentoGrupoDTO>
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public ImportanciaConhecimentoGrupoDTO obterGrauDeImportancia(BaseConhecimentoDTO baseConhecimentoDto, Collection<GrupoEmpregadoDTO> listGrupoEmpregado, UsuarioDTO usuarioDto) throws Exception;

}
