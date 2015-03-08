package br.com.centralit.bpm.batch;

import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.negocio.ExecucaoFluxo;

public class ThreadExecutaEvento extends Thread {

    private EventoFluxoDTO eventoFluxoDto = null;

    public ThreadExecutaEvento(final EventoFluxoDTO eventoFluxoDto) throws Exception {
        this.eventoFluxoDto = eventoFluxoDto;
    }

    @Override
    public void run() {
        try {
            if (eventoFluxoDto == null) {
                return;
            }

            ExecucaoFluxo execucaoFluxo = null;
            if (eventoFluxoDto.getNomeClasseFluxo() != null) {
                execucaoFluxo = (ExecucaoFluxo) Class.forName(eventoFluxoDto.getNomeClasseFluxo()).newInstance();
            }
            if (execucaoFluxo != null) {
                execucaoFluxo.executaEvento(eventoFluxoDto);
            } else {
                System.out.println("#############################################################################################");
                System.out.println("Problemas na execução dos eventos bpm");
                System.out.println("Classe de fluxo do evento " + eventoFluxoDto.getIdItemTrabalho() + " não parametrizada");
                System.out.println("#############################################################################################");
            }
        } catch (final Exception e) {
            System.out.println("#########################################");
            System.out.println("Problemas na execução dos eventos bpm");
            System.out.println("#########################################");
            e.printStackTrace();
        }
    }

}
