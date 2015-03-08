package br.com.centralit.citcorpore.negocio;

import br.com.citframework.service.CrudService;

public interface InstalacaoService extends CrudService {

    boolean isSucessoInstalacao() throws Exception;

}
