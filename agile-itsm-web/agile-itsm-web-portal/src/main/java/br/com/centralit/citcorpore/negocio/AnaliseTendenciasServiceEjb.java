package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.AnaliseTendenciasDTO;
import br.com.centralit.citcorpore.bean.TendenciaDTO;
import br.com.centralit.citcorpore.bean.TendenciaGanttDTO;
import br.com.centralit.citcorpore.integracao.AnaliseTendenciasDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author euler.ramos
 *
 */
public class AnaliseTendenciasServiceEjb extends CrudServiceImpl implements AnaliseTendenciasService {

    private AnaliseTendenciasDAO dao;

    @Override
    protected AnaliseTendenciasDAO getDao() {
        if (dao == null) {
            dao = new AnaliseTendenciasDAO();
        }
        return dao;
    }

    @Override
    public List<TendenciaDTO> buscarTendenciasServico(final AnaliseTendenciasDTO analiseTendenciasDTO) throws ServiceException {
        return this.getDao().buscarTendenciasServico(analiseTendenciasDTO);
    }

    @Override
    public List<TendenciaDTO> buscarTendenciasCausa(final AnaliseTendenciasDTO analiseTendenciasDTO) throws ServiceException {
        return this.getDao().buscarTendenciasCausa(analiseTendenciasDTO);
    }

    @Override
    public List<TendenciaDTO> buscarTendenciasItemConfiguracao(final AnaliseTendenciasDTO analiseTendenciasDTO) throws ServiceException {
        return this.getDao().buscarTendenciasItemConfiguracao(analiseTendenciasDTO);
    }

    @Override
    public List<TendenciaGanttDTO> listarGraficoGanttServico(final AnaliseTendenciasDTO analiseTendenciasDTO, final Integer idServico) throws ServiceException {
        return this.getDao().listarGraficoGanttServico(analiseTendenciasDTO, idServico);
    }

    @Override
    public List<TendenciaGanttDTO> listarGraficoGanttCausa(final AnaliseTendenciasDTO analiseTendenciasDTO, final Integer idCausa) throws ServiceException {
        return this.getDao().listarGraficoGanttCausa(analiseTendenciasDTO, idCausa);
    }

    @Override
    public List<TendenciaGanttDTO> listarGraficoGanttItemConfiguracao(final AnaliseTendenciasDTO analiseTendenciasDTO, final Integer idItemConfiguracao) throws ServiceException {
        return this.getDao().listarGraficoGanttItemConfiguracao(analiseTendenciasDTO, idItemConfiguracao);
    }

}
