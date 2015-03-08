package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface PrioridadeAcordoNivelServicoService extends CrudService {
	public Collection findByIdUnidade(Integer parm) throws Exception;
	public void deleteByIdUnidade(Integer parm) throws Exception;
	public Collection findByIdAcordoNivelServico(Integer parm) throws Exception;
	public void deleteByIdAcordoNivelServico(Integer parm) throws Exception;
}
