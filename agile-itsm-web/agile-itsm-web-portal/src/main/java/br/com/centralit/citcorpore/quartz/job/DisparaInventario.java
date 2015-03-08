package br.com.centralit.citcorpore.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.comm.server.Servidor;

public class DisparaInventario implements Job {

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        try {
            Servidor.executarPesquisaIPGerarInvenario();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
