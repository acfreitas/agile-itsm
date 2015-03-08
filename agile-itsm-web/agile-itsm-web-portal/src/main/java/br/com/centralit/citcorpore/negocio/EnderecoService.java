package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.EnderecoDTO;
import br.com.citframework.service.CrudService;
public interface EnderecoService extends CrudService {
    public Collection<EnderecoDTO> recuperaEnderecosEntregaProduto() throws Exception;
    public EnderecoDTO recuperaEnderecoCompleto(EnderecoDTO endereco) throws Exception;
    public EnderecoDTO recuperaEnderecoComUnidade(Integer idEndereco) throws Exception;
}
