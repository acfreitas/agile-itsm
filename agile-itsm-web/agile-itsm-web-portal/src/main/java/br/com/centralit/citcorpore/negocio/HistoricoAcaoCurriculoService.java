package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

/**
 * @author ygor.magalhaes
 *
 */
public interface HistoricoAcaoCurriculoService extends CrudService {
	 public Collection listByIdCurriculo(Integer idCurriculo) throws Exception;

}
