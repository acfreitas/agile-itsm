package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.bean.ImportConfigDTO;
import br.com.centralit.citcorpore.bean.ImportarDadosDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface ImportConfigService extends CrudService {
	
	public void excluirRegistroESubItens(ImportConfigDTO importConfigDTO) throws Exception;
	public ImportConfigDTO consultarImportConfigDTO(ImportarDadosDTO importarDadosDTO) throws Exception;
	public void excluirRegistroLogicamente(Integer idImportarDados) throws ServiceException, LogicException;
	
}