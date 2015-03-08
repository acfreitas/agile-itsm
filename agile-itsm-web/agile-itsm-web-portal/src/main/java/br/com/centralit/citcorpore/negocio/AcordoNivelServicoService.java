package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoHistoricoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface AcordoNivelServicoService extends CrudService {

    Collection findByIdServicoContrato(Integer parm) throws Exception;

    Collection consultaPorIdServicoContrato(Integer parm) throws Exception;

    void deleteByIdServicoContrato(Integer parm) throws Exception;

    Collection findByIdPrioridadePadrao(Integer parm) throws Exception;

    void deleteByIdPrioridadePadrao(Integer parm) throws Exception;

    void copiarSLA(Integer idAcordoNivelServico, Integer idServicoContratoOrigem, Integer[] idServicoCopiarPara)
            throws Exception;

    AcordoNivelServicoDTO findAtivoByIdServicoContrato(Integer idServicoContrato, String tipo) throws Exception;

    List<ServicoContratoDTO> buscaServicosComContrato(String tituloSla) throws Exception;

    boolean verificaSeNomeExiste(HashMap mapFields) throws Exception;

    List<AcordoNivelServicoDTO> findAcordosSemVinculacaoDireta() throws Exception;

    /**
     * Cria um novo acordo de nível de serviço
     *
     * @param acordoNivelServicoDTO
     * @param acordoNivelServicoHistoricoDTO
     * @return AcordoNivelServicoDTO
     * @throws Exception
     * @author rodrigo.oliveira
     */
    AcordoNivelServicoDTO create(AcordoNivelServicoDTO acordoNivelServicoDTO,
            AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO) throws ServiceException, LogicException;

    /**
     * Atualiza um novo acordo de nível de serviço
     *
     * @param acordoNivelServicoDTO
     * @param acordoNivelServicoHistoricoDTO
     * @return AcordoNivelServicoDTO
     * @throws Exception
     * @author rodrigo.oliveira
     */
    AcordoNivelServicoDTO update(AcordoNivelServicoDTO acordoNivelServicoDTO,
            AcordoNivelServicoHistoricoDTO acordoNivelServicoHistoricoDTO) throws ServiceException, LogicException;

    /**
     * Exclui Base de Conhecimento.
     *
     * @param baseConhecimentoBean
     * @param isAprovaBaseConhecimento
     * @throws Exception
     * @author rodrigo.oliveira
     */
    void excluir(AcordoNivelServicoDTO acordoNivelServicoDTO) throws Exception;

    List<AcordoNivelServicoDTO> findIdEmailByIdSolicitacaoServico(Integer idSolicitacaoServico) throws Exception;

    String verificaIdAcordoNivelServico(HashMap mapFields) throws Exception;

}
