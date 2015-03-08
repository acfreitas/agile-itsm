package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface BIItemDashBoardService extends CrudService {
	public Collection findByIdDashBoard(Integer parm) throws Exception;
	public void deleteByIdDashBoard(Integer parm) throws Exception;
	public Collection findByIdConsulta(Integer parm) throws Exception;
	public void deleteByIdConsulta(Integer parm) throws Exception;
}
