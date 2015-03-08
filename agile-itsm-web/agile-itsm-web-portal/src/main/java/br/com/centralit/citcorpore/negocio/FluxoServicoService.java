package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface FluxoServicoService extends CrudService {

	public Collection findByIdServicoContrato(Integer parm) throws Exception;

	/**
	 * Retorna FluxoServicoDTO Principal Ativo de acordo com o idServicoContrato informado.
	 * 
	 * @param idServicoContrato
	 *            - Identificador do serviço contrato.
	 * @return FluxoServicoDTO - DTO de Fluxo Serviço.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public FluxoServicoDTO findPrincipalByIdServicoContrato(Integer idServicoContrato) throws Exception;

	public boolean validarFluxoServico(FluxoServicoDTO fluxoServicoDTO) throws Exception;

	public Collection findByIdFluxoServico(Integer parm) throws Exception;
}
