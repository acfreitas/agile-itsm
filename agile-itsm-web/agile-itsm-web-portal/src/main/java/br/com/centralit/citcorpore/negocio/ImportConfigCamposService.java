package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ImportConfigCamposService extends CrudService {
	public Collection findByIdImportConfig(Integer parm) throws Exception;
	public void deleteByIdImportConfig(Integer parm) throws Exception;
	public Collection findByIdImportarDados(ImportarDadosDTO importarDadosDTO) throws Exception;
}