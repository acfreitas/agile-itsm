package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.citframework.service.CrudService;

public interface ObjetoNegocioService extends CrudService {

    Collection listAtivos() throws Exception;

    Collection findByNomeTabelaDB(final String nomeTabelaDBParm) throws Exception;

    ObjetoNegocioDTO findByNomeObjetoNegocio(final String nomeObjetoNegocio) throws Exception;

    ObjetoNegocioDTO getByNomeTabelaDB(final String nomeObjetoNegocio) throws Exception;

    void updateDisable(final ObjetoNegocioDTO objetoNegocioDTO) throws Exception;

}
