package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.citframework.service.CrudService;
public interface BIConsultaService extends CrudService {
	public Collection findByIdCategoria(Integer parm) throws Exception;
	public BIConsultaDTO getByIdentificacao(String ident) throws Exception;
}
