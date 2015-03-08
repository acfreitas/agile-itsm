package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 *
 * @author breno.guimaraes
 *
 */
public interface ImagemItemConfiguracaoService extends CrudService {

    Collection findByIdServico(final int idServico) throws LogicException, ServiceException;

    Collection findByIdImagemItemConfiguracaoPai(final int id) throws LogicException, ServiceException;

    Collection findItensRelacionadosHierarquia(final Integer idItemCfg) throws Exception;

    Collection findServicosRelacionadosHierarquia(final Integer idItemCfg) throws Exception;

}
