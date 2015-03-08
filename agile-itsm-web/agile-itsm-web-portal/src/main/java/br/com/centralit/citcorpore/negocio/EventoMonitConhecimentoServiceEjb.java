/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO;
import br.com.centralit.citcorpore.integracao.EventoMonitConhecimentoDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author Vadoilo Damasceno
 *
 */
public class EventoMonitConhecimentoServiceEjb extends CrudServiceImpl implements EventoMonitConhecimentoService {

    private EventoMonitConhecimentoDAO dao;

    @Override
    protected EventoMonitConhecimentoDAO getDao() {
        if (dao == null) {
            dao = new EventoMonitConhecimentoDAO();
        }
        return dao;
    }

    @Override
    public void deleteByIdConhecimento(final Integer idBaseConhecimento, final TransactionControler transactionControler) throws Exception {
        final EventoMonitConhecimentoDAO eventoMonitConhecimentoDao = new EventoMonitConhecimentoDAO();
        eventoMonitConhecimentoDao.setTransactionControler(transactionControler);
        eventoMonitConhecimentoDao.deleteByIdConhecimento(idBaseConhecimento);
    }

    @Override
    public void create(final EventoMonitConhecimentoDTO eventoMonitConhecimentoDto, final TransactionControler transactionControler) throws Exception {
        final EventoMonitConhecimentoDAO eventoMonitConhecimentoDao = new EventoMonitConhecimentoDAO();
        eventoMonitConhecimentoDao.setTransactionControler(transactionControler);
        eventoMonitConhecimentoDao.create(eventoMonitConhecimentoDto);
    }

    @Override
    public Collection<EventoMonitConhecimentoDTO> listByIdBaseConhecimento(final Integer idBaseConhecimento) throws Exception {
        return this.getDao().listByIdBaseConhecimento(idBaseConhecimento);
    }

    @Override
    public Collection<EventoMonitConhecimentoDTO> listByIdEventoMonitoramento(final Integer idEventoMonitoramento) throws Exception {
        return this.getDao().listByIdEventoMonitoramento(idEventoMonitoramento);
    }

    @Override
    public boolean verificarEventoMonitoramentoComConhecimento(final Integer idEventoMonitoramento) throws Exception {
        return this.getDao().verificarEventoMonitoramentoComConhecimento(idEventoMonitoramento);
    }

}
