package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.citframework.service.CrudService;

public interface CidadesService extends CrudService {

	public Collection<CidadesDTO> listByIdCidades(CidadesDTO obj) throws Exception;

	public Collection<CidadesDTO> findByNome(String nome) throws Exception;

	public CidadesDTO findCidadeUF(Integer idCidade) throws Exception;

	public Collection<CidadesDTO> listByIdUf(Integer idUf) throws Exception;
	
	public Collection<CidadesDTO> findByIdEstadoAndNomeCidade(Integer idEstado, String nomeCidade) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Collection findNomeByIdCidade(Integer idCidade) throws Exception;
	
	public CidadesDTO findByIdCidade(Integer idCidade) throws Exception;

}