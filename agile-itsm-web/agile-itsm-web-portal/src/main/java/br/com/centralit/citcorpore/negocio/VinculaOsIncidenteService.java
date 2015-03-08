package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
public interface VinculaOsIncidenteService extends CrudService {
	public Collection findByIdOS(Integer idOS) throws Exception;
	public Collection findByIdAtividadeOS(Integer idAtividadeOS) throws Exception;
	public void deleteByIdOs(Integer idOS) throws Exception;
	public void deleteByIdAtividadeOS(Integer idAtividadeOS) throws Exception;
	public boolean verificaServicoSelecionado(Integer idServicoContratoContabil) throws Exception;
	public boolean verificaServicoJaVinculado(Integer idOS, Integer idServicoContratoContabil) throws Exception;
}
