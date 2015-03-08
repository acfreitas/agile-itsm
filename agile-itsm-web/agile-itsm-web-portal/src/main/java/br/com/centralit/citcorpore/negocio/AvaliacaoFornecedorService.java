package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudService;

public interface AvaliacaoFornecedorService extends CrudService {
    public Collection findByIdFornecedor(Integer parm) throws Exception;
    public boolean fornecedorQualificado(Integer idFornecedor) throws Exception;
}
