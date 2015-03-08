package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ExcecaoCalendarioDTO;
import br.com.citframework.service.CrudService;
public interface ExcecaoCalendarioService extends CrudService {
	public Collection findByIdCalendario(Integer parm) throws Exception;
	public void deleteByIdCalendario(Integer parm) throws Exception;
	public ExcecaoCalendarioDTO findByIdCalendarioAndData(Integer idCalendario, Date data) throws Exception;
}
