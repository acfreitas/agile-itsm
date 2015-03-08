package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RelatorioTop10IncidentesRequisicoesDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.Top10IncidentesRequisicoesDTO;
import br.com.centralit.citcorpore.integracao.Top10IncidentesRequisicoesDAO;
import br.com.citframework.service.CrudServiceImpl;

public class Top10IncidentesRequisicoesServiceEjb extends CrudServiceImpl implements Top10IncidentesRequisicoesService {

    private Top10IncidentesRequisicoesDAO dao;

    @Override
    protected Top10IncidentesRequisicoesDAO getDao() {
        if (dao == null) {
            dao = new Top10IncidentesRequisicoesDAO();
        }
        return dao;
    }

    @Override
    public ArrayList<Top10IncidentesRequisicoesDTO> listSolicitantesMaisAbriramIncSol(final RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
        return this.getDao().listSolicitantesMaisAbriramIncSol(relatorioTop10IncidentesRequisicoesDTO);
    }

    @Override
    public Collection<SolicitacaoServicoDTO> listDetalheSolicitanteMaisAbriuIncSol(final Integer idSolicitante,
            final RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
        return this.getDao().listDetalheSolicitanteMaisAbriuIncSol(idSolicitante, relatorioTop10IncidentesRequisicoesDTO);
    }

    @Override
    public ArrayList<Top10IncidentesRequisicoesDTO> listGruposMaisResolveramIncSol(final RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
        return this.getDao().listGruposMaisResolveramIncSol(relatorioTop10IncidentesRequisicoesDTO);
    }

    @Override
    public Collection<SolicitacaoServicoDTO> listDetalheGruposMaisResolveramIncSol(final Integer idGrupo,
            final RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
        return this.getDao().listDetalheGruposMaisResolveramIncSol(idGrupo, relatorioTop10IncidentesRequisicoesDTO);
    }

    @Override
    public ArrayList<Top10IncidentesRequisicoesDTO> listReqIncMaisSolicitados(final RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
        return this.getDao().listReqIncMaisSolicitados(relatorioTop10IncidentesRequisicoesDTO);
    }

    @Override
    public Collection<SolicitacaoServicoDTO> listDetalheReqIncMaisSolicitados(final Integer idTipoDemanda, final Integer idServico,
            final RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
        return this.getDao().listDetalheReqIncMaisSolicitados(idTipoDemanda, idServico, relatorioTop10IncidentesRequisicoesDTO);
    }

    @Override
    public ArrayList<Top10IncidentesRequisicoesDTO> listUnidadesMaisAbriramReqInc(final RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
        return this.getDao().listUnidadesMaisAbriramReqInc(relatorioTop10IncidentesRequisicoesDTO);
    }

    @Override
    public Collection<SolicitacaoServicoDTO> listDetalheUnidadesMaisAbriramReqInc(final Integer id,
            final RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
        return this.getDao().listDetalheUnidadesMaisAbriramReqInc(id, relatorioTop10IncidentesRequisicoesDTO);
    }

    @Override
    public ArrayList<Top10IncidentesRequisicoesDTO> listLocMaisAbriramReqInc(final RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
        return this.getDao().listLocMaisAbriramReqInc(relatorioTop10IncidentesRequisicoesDTO);
    }

    @Override
    public Collection<SolicitacaoServicoDTO> listDetalheLocMaisAbriramReqInc(final Integer id, final RelatorioTop10IncidentesRequisicoesDTO relatorioTop10IncidentesRequisicoesDTO) {
        return this.getDao().listDetalheLocMaisAbriramReqInc(id, relatorioTop10IncidentesRequisicoesDTO);
    }

}
