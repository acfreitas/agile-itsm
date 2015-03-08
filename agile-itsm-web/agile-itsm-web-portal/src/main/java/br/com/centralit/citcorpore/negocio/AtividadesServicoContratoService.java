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
	 * Retorna Atividades Ativas do Serviço Contrato pelo Id do Serviço
	 * Contrato.
	 * 
	 * @param idServicoContrato
	 * @return atividadesServicoContrato
	 * @throws Exception
	 */
	public Collection obterAtividadesAtivasPorIdServicoContrato(Integer idServicoContrato) throws Exception;
	
	/**
	 * Método para atualizar a observacao de os não homologadas
	 * 
	 * @param mapFields
	 * @throws Exception
	 */
	public boolean atualizaObservacao(HashMap mapFields) throws Exception;
	
	/**
	 * Método para calcular fórmula
	 * 
	 * @param mapFields
	 * @throws Exception
	 */
	public String calculaFormula(HashMap mapFields) throws Exception;
	
	/**
	 * Verifica se complexidades estão cadastradas
	 * 
	 * @param mapFields
	 * @throws Exception
	 */
	public boolean verificaComplexidade(HashMap mapFields) throws Exception;
	
	/**
	 * Método que retorna os serviços vinculado ao contrato em questão
	 * 
	 * @param mapFields
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public Collection preencheComboServicoContrato(HashMap mapFields, String language) throws Exception;
	
	public Double calculaFormula(AtividadesServicoContratoDTO atividadesServicoContrato) throws Exception;
	
	public Collection listarPorFormula() throws Exception;
}
