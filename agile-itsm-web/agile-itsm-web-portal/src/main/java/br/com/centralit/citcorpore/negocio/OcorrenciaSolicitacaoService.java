package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Date;

import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.citframework.service.CrudService;

/**
 * @author breno.guimaraes
 *
 */
public interface OcorrenciaSolicitacaoService extends CrudService {
    public Collection findByIdSolicitacaoServico(Integer idSolicitacaoServicoParm) throws Exception;
    public OcorrenciaSolicitacaoDTO findUltimoByIdSolicitacaoServico(Integer idSolicitacaoServicoParm) throws Exception;
    public Collection findOcorrenciasDoTesteIdSolicitacaoServico(Integer idSolicitacaoServicoParm) throws Exception;
    public Collection<OcorrenciaSolicitacaoDTO> findByIdPessoaEDataAtendidasGrupoTeste(Integer idPessoa, Date dataInicio, Date dataFim) throws Exception;
    public Collection<OcorrenciaSolicitacaoDTO> findByIdPessoaGrupoTeste(Integer idPessoa, Date dataInicio, Date dataFim) throws Exception;
    public OcorrenciaSolicitacaoDTO findByIdOcorrencia(Integer idOcorrencia) throws Exception;
    public long quantidadeDeOcorrenciasDeAlteracaoSlaPorNumeroDaSolicitacao(int idSolicitacaoServico) throws Exception;
}
