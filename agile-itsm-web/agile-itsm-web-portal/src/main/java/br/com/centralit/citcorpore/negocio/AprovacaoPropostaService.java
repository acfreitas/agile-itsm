package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AprovacaoPropostaDTO;
import br.com.citframework.service.CrudService;

public interface AprovacaoPropostaService extends CrudService{

	/**
	 * Retorna uma lista de Aprovacao Mudança de acordo com a requisição passada
	 * @param idRequisicaoMudanca
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<AprovacaoPropostaDTO> listaAprovacaoPropostaPorIdRequisicaoMudanca(Integer idRequisicaoMudanca,Integer idGrupo, Integer idEmpregado) throws Exception ;

	/**
	 * Retorna quantidade de votos do tipo aprovada
	 * @param aprovacao
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Integer quantidadeAprovacaoPropostaPorVotoAprovada(AprovacaoPropostaDTO aprovacao,Integer idGrupo) throws Exception;

	/**
	 * Retorna quantidade de votos do tipo rejeitada
	 * @param aprovacao
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Integer quantidadeAprovacaoPropostaPorVotoRejeitada(AprovacaoPropostaDTO aprovacao,Integer idGrupo) throws Exception;

	/**
	 * Retorna verdadeiro ou falso caso idrequisicaomudanca tenha aprovacao
	 * @param idRequisicaoMudanca
	 * @return
	 * @throws Exception
	 */
	public Boolean validacaoAprovacaoProposta(Integer idRequisicaoMudanca) throws Exception;

	/**
	 * Retorna a quantidade de votacao de uma requisição mudança
	 * @param aprovacao
	 * @param idGrupo
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Integer quantidadeAprovacaoProposta(AprovacaoPropostaDTO aprovacao, Integer idGrupo) throws Exception;

}
