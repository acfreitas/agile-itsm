package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface TempoAcordoNivelServicoService extends CrudService {
	public Collection findByIdAcordoAndIdPrioridade(Integer idAcordoNivelServico, Integer idPrioridade) throws Exception;
	public Collection findByIdAcordoAndFaseAndIdPrioridade(Integer idAcordoNivelServico, Integer idFase, Integer idPrioridade) throws Exception;
}
