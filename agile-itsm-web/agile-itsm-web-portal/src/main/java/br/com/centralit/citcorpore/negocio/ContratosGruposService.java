/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.citframework.service.CrudService;

/**
 * @author Centralit
 * 
 */
public interface ContratosGruposService extends CrudService {

	public Collection<ContratosGruposDTO> findByIdGrupo(Integer idGrupo) throws Exception;

	public Collection<ContratosGruposDTO> findByIdEmpregado(Integer idEmpregado) throws Exception;

	public void deleteByIdGrupo(Integer idGrupo) throws Exception;

	public Collection<ContratosGruposDTO> findByIdContrato(Integer idContrato) throws Exception;

	public void deleteByIdContrato(Integer idContrato) throws Exception;
	
	public Collection<ContratosGruposDTO> findByGrupos(Collection<GrupoDTO> gruposEmpregado) throws Exception;
	/*
	 * Verifica se tem o contrato vinculado a lista de grupos
	 */
	public boolean hasContrato(Collection<GrupoEmpregadoDTO> gruposEmpregado, ContratoDTO contrato) throws Exception;

}
