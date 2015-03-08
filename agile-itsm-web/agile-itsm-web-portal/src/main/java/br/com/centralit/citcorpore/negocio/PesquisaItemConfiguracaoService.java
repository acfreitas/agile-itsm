package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.PesquisaItemConfiguracaoDTO;
import br.com.citframework.service.CrudService;

public interface PesquisaItemConfiguracaoService extends CrudService {

    public Collection<ItemConfiguracaoDTO> listByIdItemconfiguracao(PesquisaItemConfiguracaoDTO pesquisa)
	    throws Exception;
    
    public Collection<ItemConfiguracaoDTO> listByIdItemconfiguracaoNaoDesativados(PesquisaItemConfiguracaoDTO pesquisa)
    	    throws Exception;

}
