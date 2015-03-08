package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface TemplateImpressaoService extends CrudService {
	public Collection findByIdTipoTemplateImp(Integer parm) throws Exception;
	public void deleteByIdTipoTemplateImp(Integer parm) throws Exception;
}
