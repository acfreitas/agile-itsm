package br.com.centralit.citcorpore.negocio;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudService;
public interface BIDashBoardService extends CrudService {
	public IDto getByIdentificacao(String ident) throws Exception;
}
