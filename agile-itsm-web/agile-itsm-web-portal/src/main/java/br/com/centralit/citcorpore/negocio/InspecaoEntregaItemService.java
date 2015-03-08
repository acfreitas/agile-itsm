package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface InspecaoEntregaItemService extends CrudService {
	public Collection findByIdEntrega(Integer parm) throws Exception;
	public void deleteByIdEntrega(Integer parm) throws Exception;
}
