package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.VersaoDTO;
import br.com.citframework.service.CrudService;

public interface VersaoService extends CrudService {

    VersaoDTO buscaVersaoPorNome(String nome) throws Exception;

    void validaVersoes(UsuarioDTO usuario) throws Exception;

    boolean haVersoesSemValidacao() throws Exception;

    VersaoDTO versaoASerValidada() throws Exception;

    Collection<VersaoDTO> versoesComErrosScripts() throws Exception;

}
