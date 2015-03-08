/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.PesquisaSatisfacaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.PesquisaSatisfacaoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

/**
 * @author valdoilo
 *
 */
public class PesquisaSatisfacaoServiceEjb extends CrudServiceImpl implements PesquisaSatisfacaoService {

    private PesquisaSatisfacaoDAO dao;

    @Override
    protected PesquisaSatisfacaoDAO getDao() {
        if (dao == null) {
            dao = new PesquisaSatisfacaoDAO();
        }
        return dao;
    }

    @Override
    public Collection<PesquisaSatisfacaoDTO> getPesquisaByIdSolicitacao(final int idServico) {
        return this.getDao().getPesquisaByIdSolicitacao(idServico);
    }

    @Override
    public Collection<PesquisaSatisfacaoDTO> relatorioPesquisaSatisfacao(final PesquisaSatisfacaoDTO pesquisaSatisfacaoDTO) throws Exception {
        return this.getDao().relatorioPesquisaSatisfacao(pesquisaSatisfacaoDTO);
    }

    public String buscarResponsavelExecucao(final Integer idSolcilitacao) throws Exception {
        final OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(
                OcorrenciaSolicitacaoService.class, null);
        @SuppressWarnings("unchecked") final Collection<OcorrenciaSolicitacaoDTO> col = ocorrenciaSolicitacaoService.findByIdSolicitacaoServico(idSolcilitacao);
        for (final OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO : col) {
            String dadosSolicitacao = UtilStrings.nullToVazio(ocorrenciaSolicitacaoDTO.getDadosSolicitacao());
            SolicitacaoServicoDTO solicitacaoDto = null;
            if (dadosSolicitacao.length() > 0) {
                try {
                    solicitacaoDto = new Gson().fromJson(dadosSolicitacao, SolicitacaoServicoDTO.class);
                    if (solicitacaoDto != null) {
                        dadosSolicitacao = solicitacaoDto.getDadosStr();

                        if (solicitacaoDto.getSituacao().equals("Resolvido") && !solicitacaoDto.getRegistradoPor().equals("Automático")) {
                            return solicitacaoDto.getRegistradoPor();
                        }
                    }
                } catch (final Exception e) {
                    dadosSolicitacao = "";
                }
            }
        }
        return null;
    }

    @Override
    public PesquisaSatisfacaoDTO findByIdSolicitacaoServico(final Integer idSolicitacaoServico) throws Exception {
        try {
            return this.getDao().findByIdSolicitacaoServico(idSolicitacaoServico);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
