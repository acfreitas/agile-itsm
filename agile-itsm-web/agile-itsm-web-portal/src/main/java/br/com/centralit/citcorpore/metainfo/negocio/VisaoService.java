package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface VisaoService extends CrudService {

    Collection listAtivos() throws Exception;

    VisaoDTO findByIdentificador(final String identificador) throws Exception;

    void deleteVisao(final IDto model) throws Exception;

    void importar(final VisaoDTO visaoXML) throws Exception;

    void atualizarVisao(final VisaoDTO visaoAtual, final VisaoDTO visaoXML) throws Exception;

    VisaoDTO visaoExistente(final String identificadorVisao) throws ServiceException, Exception;

}
