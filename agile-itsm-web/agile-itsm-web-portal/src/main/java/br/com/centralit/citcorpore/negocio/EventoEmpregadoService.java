package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoEmpregadoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface EventoEmpregadoService extends CrudService {

    public Collection<EventoEmpregadoDTO> listByIdEvento(Integer idEvento) throws ServiceException;

    public Collection<EventoEmpregadoDTO> listByIdEventoGrupo(Integer idEvento) throws ServiceException;

    public Collection<EventoEmpregadoDTO> listByIdEventoUnidade(Integer idEvento) throws ServiceException;

    public Collection<EventoEmpregadoDTO> listByIdEventoEmpregado(Integer idEvento) throws ServiceException;
}
