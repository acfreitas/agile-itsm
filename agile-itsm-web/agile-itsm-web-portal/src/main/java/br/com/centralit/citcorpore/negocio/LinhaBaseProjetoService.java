package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.service.CrudService;
public interface LinhaBaseProjetoService extends CrudService {
	public Collection findByIdProjeto(Integer parm) throws Exception;
	public void deleteByIdProjeto(Integer parm) throws Exception;
	public void updateAutorizacaoMudanca(IDto model) throws Exception;
}
