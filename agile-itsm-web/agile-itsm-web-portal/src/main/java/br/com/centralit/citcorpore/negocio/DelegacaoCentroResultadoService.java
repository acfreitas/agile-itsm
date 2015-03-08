package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.DelegacaoCentroResultadoDTO;
import br.com.citframework.service.CrudService;
public interface DelegacaoCentroResultadoService extends CrudService {
	public Collection findByIdResponsavelAndIdCentroResultado(Integer idResponsavel, Integer idCentroResultado) throws Exception;
	public void revoga(DelegacaoCentroResultadoDTO delegacaoCentroResultadoDto) throws Exception;
}
