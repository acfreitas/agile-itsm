package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.citframework.service.CrudService;

/**
 * @author rosana.godinho
 *
 */
public interface InformacaoItemConfiguracaoService extends CrudService {

    public InformacaoItemConfiguracaoDTO listByInformacao(ItemConfiguracaoDTO itemConfiguracao) throws Exception ;
}
