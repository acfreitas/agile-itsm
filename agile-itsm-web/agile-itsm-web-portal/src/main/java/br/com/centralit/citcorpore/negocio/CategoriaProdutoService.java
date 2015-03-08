package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.CategoriaProdutoDTO;
import br.com.citframework.service.CrudService;
public interface CategoriaProdutoService extends CrudService {
    public Collection listAtivas() throws Exception;
    public void recuperaImagem(CategoriaProdutoDTO categoriaProdutoDto) throws Exception;
    public Collection findByIdPai(Integer idPai) throws Exception;
    public boolean existeIgual(CategoriaProdutoDTO categoriaProduto) throws Exception;
    public String getHierarquiaHTML(String acao) throws Exception;
}
