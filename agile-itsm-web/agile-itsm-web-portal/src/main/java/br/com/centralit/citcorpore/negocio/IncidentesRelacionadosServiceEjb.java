/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.IncidentesRelacionadosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.integracao.IncidentesRelacionadosDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author breno.guimaraes
 */
@Deprecated
public class IncidentesRelacionadosServiceEjb extends CrudServiceImpl implements IncidentesRelacionadosService {

    private IncidentesRelacionadosDAO dao;
    private SolicitacaoServicoServiceEjb solicitacaoServicoServiceEjb;

    @Override
    protected IncidentesRelacionadosDAO getDao() {
        if (dao == null) {
            dao = new IncidentesRelacionadosDAO();
        }
        return dao;
    }

    private SolicitacaoServicoServiceEjb getSolicitacaoServicoEjb() {
        if (solicitacaoServicoServiceEjb == null) {
            solicitacaoServicoServiceEjb = new SolicitacaoServicoServiceEjb();
        }

        return solicitacaoServicoServiceEjb;
    }

    @Override
    public ArrayList<SolicitacaoServicoDTO> listIncidentesRelacionados(final int idSolicitacao) {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("idIncidente", "=", idSolicitacao));
        final SolicitacaoServicoDTO solicitacao = new SolicitacaoServicoDTO();

        final ArrayList<SolicitacaoServicoDTO> retorno = new ArrayList<SolicitacaoServicoDTO>();

        try {
            // pega lista de ids dos incidentes relacionados ao passado como argumento.
            final Collection<IncidentesRelacionadosDTO> incidentesRelacionados = this.getDao().findByCondition(condicoes, null);
            // preenche uma lista com os incidentes relacionados buscando pelos ids obtidos.
            if (incidentesRelacionados != null) {
                for (final IncidentesRelacionadosDTO inc : incidentesRelacionados) {
                    // solicitacao.setIdSolicitacaoServico(inc.getIdIncidenteRelacionado());
                    retorno.add((SolicitacaoServicoDTO) this.getSolicitacaoServicoEjb().restore(solicitacao));
                }
            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
        return retorno;
    }

    @Override
    public IDto create(final IDto dto) throws ServiceException, LogicException {
        final IncidentesRelacionadosDTO icRelacionadoDto = (IncidentesRelacionadosDTO) dto;
        return super.create(icRelacionadoDto);
    }

}
