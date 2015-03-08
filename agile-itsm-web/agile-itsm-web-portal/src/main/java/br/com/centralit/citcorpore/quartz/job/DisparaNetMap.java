package br.com.centralit.citcorpore.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.comm.server.NetDiscover;

public class DisparaNetMap implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        new Thread(new NetDiscover()).start();
    }

}
