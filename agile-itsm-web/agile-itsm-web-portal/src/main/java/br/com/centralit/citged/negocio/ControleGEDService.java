package br.com.centralit.citged.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ControleGEDService extends CrudService {

    String getProximaPastaArmazenar() throws Exception;

    Collection listByIdTabelaAndID(final Integer idTabela, final Integer id) throws Exception;

    Collection convertListControleGEDToUploadDTO(final Collection colAnexosControleGED) throws Exception;

    /**
     * Pesquisa utilizada somente para arquivos anexados na Base de Conhecimento. idTabela = 4.
     *
     * @param idTabela
     * @param idBasePai
     * @param idBaseFilho
     * @return
     * @throws PersistenceException
     * @throws Exception
     */
    Collection listByIdTabelaAndIdBaseConhecimentoPaiEFilho(final Integer idTabela, final Integer idBasePai, final Integer idBaseFilho) throws PersistenceException, Exception;

    Collection listByIdTabelaAndIdBaseConhecimento(final Integer idTabela, final Integer idBaseConhecimento) throws Exception;

    Collection listByIdTabelaAndIdLiberacaoAndLigacao(final Integer idTabela, final Integer idRequisicaoLiberacao) throws Exception;

    ControleGEDDTO getControleGED(final AnexoBaseConhecimentoDTO anexoBaseConhecimento) throws Exception;

}
