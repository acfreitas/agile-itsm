package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface LimiteAlcadaService extends CrudService {
	public void removerPorIdAlcada(Integer idAlcada) throws Exception;
	public Collection findByIdAlcada(Integer idAlcada) throws Exception;
}
