/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.HistoricoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.integracao.HistoricoBaseConhecimentoDAO;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Vadoilo Damasceno
 *
 */
public class HistoricoBaseConhecimentoServiceEjb extends CrudServiceImpl implements HistoricoBaseConhecimentoService {

    private HistoricoBaseConhecimentoDAO dao;

    @Override
    protected HistoricoBaseConhecimentoDAO getDao() {
        if (dao == null) {
            dao = new HistoricoBaseConhecimentoDAO();
        }
        return dao;
    }

    @Override
    public Collection<HistoricoBaseConhecimentoDTO> obterHistoricoDeAlteracao(final HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDto) throws Exception {
        return this.getDao().obterHistoricoDeAlteracao(historicoBaseConhecimentoDto);
    }

    @Override
    public Collection<HistoricoBaseConhecimentoDTO> obterHistoricoDeAlteracaoPorPeriodo(final HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDTO) throws Exception {
        Collection<HistoricoBaseConhecimentoDTO> listaHistoricoBaseConhecimentoDTOs = null;
        try {
            listaHistoricoBaseConhecimentoDTOs = this.getDao().list();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return listaHistoricoBaseConhecimentoDTOs;
    }

}
