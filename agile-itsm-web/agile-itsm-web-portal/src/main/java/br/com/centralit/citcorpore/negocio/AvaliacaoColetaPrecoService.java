package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface AvaliacaoColetaPrecoService extends CrudService {
	public Collection findByIdColetaPreco(Integer idColetaPreco) throws Exception;
	public void deleteByIdColetaPreco(Integer idColetaPreco) throws Exception;
}
