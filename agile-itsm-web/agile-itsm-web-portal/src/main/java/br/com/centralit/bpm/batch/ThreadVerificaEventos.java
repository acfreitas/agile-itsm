package br.com.centralit.bpm.batch;

import java.sql.Timestamp;
import java.util.Collection;

import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.integracao.EventoFluxoDao;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilDatas;

public class ThreadVerificaEventos extends Thread {

    private EventoFluxoDao eventoFluxoDao;
    private Collection<EventoFluxoDTO> eventosDisponiveis;

    @Override
    public void run() {
        while (true) {
            try {
                sleep(5000);
                this.carregaEventosDisponiveis();
                if (eventosDisponiveis != null) {
                    for (final EventoFluxoDTO eventoFluxoDto : eventosDisponiveis) {
                        sleep(500);
                        boolean bExecuta = true;
                        if (eventoFluxoDto.getDataHoraExecucao() != null && eventoFluxoDto.getIntervalo() != null) {
                            final Timestamp ts = UtilDatas.somaSegundos(eventoFluxoDto.getDataHoraExecucao(), eventoFluxoDto.getIntervalo());
                            bExecuta = ts.compareTo(UtilDatas.getDataHoraAtual()) <= 0;
                        }
                        if (bExecuta) {
                            sleep(1000);

                            // Forma Antiga, sem usar executor para controlar as threads
                            final ThreadExecutaEvento thread = new ThreadExecutaEvento(eventoFluxoDto);
                            thread.start();
                        }
                    }
                }
            } catch (final Exception e) {
                System.out.println("#########################################");
                System.out.println("Problemas na execução dos eventos bpm");
                System.out.println("#########################################");
                e.printStackTrace();
            }
        }
    }

    private void carregaEventosDisponiveis() {
        final EventoFluxoDao eventoFluxoDao = this.getEventoFluxoDao();
        try {
            eventosDisponiveis = eventoFluxoDao.findDisponiveis();
        } catch (final Exception e) {
            eventosDisponiveis = null;
            System.out.println("#########################################");
            System.out.println("Problemas na carga dos eventos bpm       ");
            System.out.println("#########################################");
            e.printStackTrace();
        } finally {
            this.closeConnectionOnTransaction();
        }
    }

    private EventoFluxoDao getEventoFluxoDao() {
        if (eventoFluxoDao == null) {
            eventoFluxoDao = new EventoFluxoDao();
        }
        return eventoFluxoDao;
    }

    private void closeConnectionOnTransaction() {
        try {
            final TransactionControler tc = this.getEventoFluxoDao().getTransactionControler();
            if (tc != null) {
                tc.closeQuietly();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
