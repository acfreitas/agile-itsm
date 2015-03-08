package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface LinguaService extends CrudService {

    boolean consultarLinguaAtivas(final LinguaDTO obj) throws ServiceException;

    LinguaDTO getIdLingua(final LinguaDTO obj) throws ServiceException;

    Collection<LinguaDTO> listarAtivos() throws ServiceException;

}
