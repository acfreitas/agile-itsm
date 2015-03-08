package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface RequisicaoLiberacaoResponsavelService extends CrudService {
	public Collection findByIdLiberacao(Integer parm) throws Exception;
	public Collection findByIdLiberacaoEDataFim(Integer parm) throws Exception;
	public void deleteByIdLiberacao(Integer parm) throws Exception;
}
