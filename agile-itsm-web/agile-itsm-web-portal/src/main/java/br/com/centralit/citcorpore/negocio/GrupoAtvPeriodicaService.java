package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface GrupoAtvPeriodicaService extends CrudService {
	public Collection findByDescGrupoAtvPeriodica(String parm) throws Exception;
	public void deleteByDescGrupoAtvPeriodica(String parm) throws Exception;
	public Collection listGrupoAtividadePeriodicaAtiva() throws Exception;
	
}
