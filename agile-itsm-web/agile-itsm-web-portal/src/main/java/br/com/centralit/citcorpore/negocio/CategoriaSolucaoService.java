package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Map;

import br.com.centralit.citcorpore.bean.CategoriaSolucaoDTO;
import br.com.citframework.service.CrudService;

public interface CategoriaSolucaoService extends CrudService {

    Collection<CategoriaSolucaoDTO> findByIdCategoriaSolucaoPai(final Integer parm) throws Exception;

    void deleteByIdCategoriaSolucaoPai(final Integer parm) throws Exception;

    Collection<CategoriaSolucaoDTO> listHierarquia() throws Exception;

    Collection<CategoriaSolucaoDTO> getCollectionHierarquia(final Integer idCateg, final Integer nivel) throws Exception;

    String verificaDescricaoDuplicadaCategoriaAoCriar(final Map mapFields) throws Exception;

    String verificaDescricaoDuplicadaCategoriaAoAtualizar(final Map mapFields) throws Exception;

    Collection<CategoriaSolucaoDTO> listaCategoriasSolucaoAtivas() throws Exception;

}
