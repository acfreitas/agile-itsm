package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ExcecaoEmpregadoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface ExcecaoEmpregadoService extends CrudService {

	public Collection<ExcecaoEmpregadoDTO> listByIdEventoIdGrupo(Integer idEvento, Integer idGrupo) throws ServiceException;
	public Collection<ExcecaoEmpregadoDTO> listByIdEventoIdUnidade(Integer idEvento, Integer idUnidade) throws ServiceException;
}
