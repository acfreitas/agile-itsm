package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Date;

import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.OcorrenciaSolicitacaoDao;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

/**
 * @author breno.guimaraes
 *
 */
public class OcorrenciaSolicitacaoServiceEjb extends CrudServiceImpl implements OcorrenciaSolicitacaoService {

    private OcorrenciaSolicitacaoDao ocorrenciaSolicitacaoDao;

    @Override
    protected OcorrenciaSolicitacaoDao getDao() {
        if (ocorrenciaSolicitacaoDao == null) {
            ocorrenciaSolicitacaoDao = new OcorrenciaSolicitacaoDao();
        }
        return ocorrenciaSolicitacaoDao;
    }

    @Override
    public Collection findByIdSolicitacaoServico(final Integer idSolicitacaoServicoParm) throws Exception {
        return this.getDao().findByIdSolicitacaoServico(idSolicitacaoServicoParm);
    }

    @Override
    public OcorrenciaSolicitacaoDTO findUltimoByIdSolicitacaoServico(final Integer idSolicitacaoServicoParm) throws Exception {
        return this.getDao().findUltimoByIdSolicitacaoServico(idSolicitacaoServicoParm);
    }

    @Override
    public Collection findOcorrenciasDoTesteIdSolicitacaoServico(final Integer idSolicitacaoServicoParm) throws Exception {
        return this.getDao().findOcorrenciaDoTesteByIdSolicitacaoServico(idSolicitacaoServicoParm);
    }

    public static OcorrenciaSolicitacaoDTO create(final SolicitacaoServicoDTO solicitacaoServicoDto, final ItemTrabalhoFluxoDTO itemTrabalhoFluxoDto, final String ocorrencia,
            final OrigemOcorrencia origem, final CategoriaOcorrencia categoria, final String informacoesContato, final String descricao, final UsuarioDTO usuarioDTO,
            final int tempo, final JustificativaSolicitacaoDTO justificativaDto, final TransactionControler tc) throws Exception {
        final OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO = new OcorrenciaSolicitacaoDTO();
        ocorrenciaSolicitacaoDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
        ocorrenciaSolicitacaoDTO.setDataregistro(UtilDatas.getDataAtual());
        ocorrenciaSolicitacaoDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
        ocorrenciaSolicitacaoDTO.setTempoGasto(tempo);
        ocorrenciaSolicitacaoDTO.setDescricao(descricao);
        ocorrenciaSolicitacaoDTO.setDataInicio(UtilDatas.getDataAtual());
        ocorrenciaSolicitacaoDTO.setDataFim(UtilDatas.getDataAtual());
        ocorrenciaSolicitacaoDTO.setInformacoesContato(informacoesContato);
        ocorrenciaSolicitacaoDTO.setRegistradopor(usuarioDTO.getLogin());
        try {
            ocorrenciaSolicitacaoDTO.setDadosSolicitacao(new Gson().toJson(solicitacaoServicoDto));
        } catch (final Exception e) {
            System.out.println("Problema na gravação dos dados da solicitação de serviço de id " + solicitacaoServicoDto.getIdSolicitacaoServico());
            e.printStackTrace();
        }
        ocorrenciaSolicitacaoDTO.setOcorrencia(ocorrencia);
        ocorrenciaSolicitacaoDTO.setOrigem(origem.getSigla().toString());
        ocorrenciaSolicitacaoDTO.setCategoria(categoria.getSigla());
        if (itemTrabalhoFluxoDto != null) {
            ocorrenciaSolicitacaoDTO.setIdItemTrabalho(itemTrabalhoFluxoDto.getIdItemTrabalho());
        }
        if (justificativaDto != null) {
            ocorrenciaSolicitacaoDTO.setIdJustificativa(justificativaDto.getIdJustificativa());
            ocorrenciaSolicitacaoDTO.setComplementoJustificativa(justificativaDto.getComplementoJustificativa());
        }

        final OcorrenciaSolicitacaoDao ocorrenciaSolicitacaoDao = new OcorrenciaSolicitacaoDao();
        if (tc != null) {
            ocorrenciaSolicitacaoDao.setTransactionControler(tc);
        }
        return (OcorrenciaSolicitacaoDTO) ocorrenciaSolicitacaoDao.create(ocorrenciaSolicitacaoDTO);
    }

    @Override
    public Collection<OcorrenciaSolicitacaoDTO> findByIdPessoaEDataAtendidasGrupoTeste(final Integer idPessoa, final Date dataInicio, final Date dataFim) throws Exception {
        try {
            return this.getDao().findByIdPessoaEDataAtendidasGrupoTeste(idPessoa, dataInicio, dataFim);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<OcorrenciaSolicitacaoDTO> findByIdPessoaGrupoTeste(final Integer idPessoa, final Date dataInicio, final Date dataFim) throws Exception {
        try {
            return this.getDao().findByIdPessoaGrupoTeste(idPessoa, dataInicio, dataFim);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public OcorrenciaSolicitacaoDTO findByIdOcorrencia(final Integer idOcorrencia) throws Exception {
        try {
            return this.getDao().findByIdOcorrencia(idOcorrencia);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public long quantidadeDeOcorrenciasDeAlteracaoSlaPorNumeroDaSolicitacao(final int idSolicitacaoServico) throws Exception {
        final OcorrenciaSolicitacaoDTO lista = this.getDao().quantidadeDeOcorrenciasDeAlteracaoSlaPorNumeroDaSolicitacao(idSolicitacaoServico);

        return lista.getTotalOcorrenciasAlterarcaoSlaPorSolicitacao();
    }

}
