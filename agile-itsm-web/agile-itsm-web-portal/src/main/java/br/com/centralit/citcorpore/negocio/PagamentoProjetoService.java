package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;
public interface PagamentoProjetoService extends CrudService {
	public Collection findByIdProjeto(Integer parm) throws Exception;
	public void deleteByIdProjeto(Integer parm) throws Exception;
	public void updateSituacao(IDto model) throws ServiceException, LogicException;
	public Collection getTotaisByIdProjeto(Integer idProjetoParm) throws Exception;
}
