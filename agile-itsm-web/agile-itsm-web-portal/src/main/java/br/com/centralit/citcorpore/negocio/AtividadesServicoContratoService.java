/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;

import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface AtividadesServicoContratoService extends CrudService {

	public Collection findByIdServicoContrato(Integer parm) throws Exception;

	public void deleteByIdServicoContrato(Integer parm) throws Exception;

	/**
	 * Retorna Atividades Ativas do Servi�o Contrato pelo Id do Servi�o
	 * Contrato.
	 * 
	 * @param idServicoContrato
	 * @return atividadesServicoContrato
	 * @throws Exception
	 */
	public Collection obterAtividadesAtivasPorIdServicoContrato(Integer idServicoContrato) throws Exception;
	
	/**
	 * M�todo para atualizar a observacao de os n�o homologadas
	 * 
	 * @param mapFields
	 * @throws Exception
	 */
	public boolean atualizaObservacao(HashMap mapFields) throws Exception;
	
	/**
	 * M�todo para calcular f�rmula
	 * 
	 * @param mapFields
	 * @throws Exception
	 */
	public String calculaFormula(HashMap mapFields) throws Exception;
	
	/**
	 * Verifica se complexidades est�o cadastradas
	 * 
	 * @param mapFields
	 * @throws Exception
	 */
	public boolean verificaComplexidade(HashMap mapFields) throws Exception;
	
	/**
	 * M�todo que retorna os servi�os vinculado ao contrato em quest�o
	 * 
	 * @param mapFields
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public Collection preencheComboServicoContrato(HashMap mapFields, String language) throws Exception;
	
	public Double calculaFormula(AtividadesServicoContratoDTO atividadesServicoContrato) throws Exception;
	
	public Collection listarPorFormula() throws Exception;
}
