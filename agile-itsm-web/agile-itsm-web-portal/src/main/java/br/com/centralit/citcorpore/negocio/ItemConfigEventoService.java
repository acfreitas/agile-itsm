package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfigEventoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface ItemConfigEventoService extends CrudService {

    /**
     * Lista os itens de configuração dos eventos cadastrados.
     * 
     * @param idEvento
     * @return Collection
     * @throws ServiceException
     * @author daniel.queiroz
     */
    public Collection<ItemConfigEventoDTO> listByIdEvento(Integer idEvento) throws ServiceException;

    /**
     * Verifica a data e hora que foi cadastrado os eventos.
     * 
     * @return Collection
     * @throws ServiceException
     * @author daniel.queiroz
     */
    public Collection<ItemConfigEventoDTO> verificarDataHoraEvento() throws ServiceException;
}
