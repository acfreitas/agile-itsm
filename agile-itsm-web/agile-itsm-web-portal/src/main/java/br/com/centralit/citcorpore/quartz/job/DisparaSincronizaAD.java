package br.com.centralit.citcorpore.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.integracao.ad.ThreadSincronizaAD;

public class DisparaSincronizaAD implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        new Thread(new ThreadSincronizaAD()).start();
    }

}
