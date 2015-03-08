package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface BIDashBoardSegurService extends CrudService {
	public Collection findByIdPerfilSeguranca(Integer parm) throws Exception;
	public void deleteByIdPerfilSeguranca(Integer parm) throws Exception;
	public Collection findByIdDashBoard(Integer parm) throws Exception;
	public void deleteByIdDashBoard(Integer parm) throws Exception;
}
