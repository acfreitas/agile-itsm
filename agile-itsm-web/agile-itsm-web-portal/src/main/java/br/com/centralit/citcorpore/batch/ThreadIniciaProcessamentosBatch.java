package br.com.centralit.citcorpore.batch;

import java.util.Collection;
import java.util.Iterator;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.negocio.ProcessamentoBatchService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

public class ThreadIniciaProcessamentosBatch extends Thread {

    @Override
    public void run() {
        try {
            sleep(180000);
        } catch (final InterruptedException e2) {
            e2.printStackTrace();
        }
        System.out.println("INICIANDO O START DOS PROCESSAMENTOS !!!");

        ProcessamentoBatchService procBatchService = null;
        try {
            procBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
        } catch (final ServiceException e1) {
            e1.printStackTrace();
        } catch (final Exception e1) {
            e1.printStackTrace();
        }
        Collection col = null;
        if (procBatchService != null) {
            try {
                col = procBatchService.list();
            } catch (final LogicException e) {
                e.printStackTrace();
            } catch (final ServiceException e) {
                e.printStackTrace();
            }
        }
        if (col != null && col.size() > 0) {
            final SchedulerFactory schedulerFactory = new StdSchedulerFactory();
            Scheduler scheduler = null;
            try {
                scheduler = schedulerFactory.getScheduler("CitSmartMonitor");
            } catch (final SchedulerException e) {
                e.printStackTrace();
            }

            boolean bEntrou = false;
            if (scheduler != null) {
                try {
                    for (final Iterator it = col.iterator(); it.hasNext();) {
                        ProcessamentoBatchDTO procDto = (ProcessamentoBatchDTO) it.next();
                        if (procDto.getSituacao().equalsIgnoreCase("A") && !procDto.getExpressaoCRON().isEmpty()) {
                            try {
                                // Somente será agendado se as configurações estão corretas
                                if (procBatchService.permiteAgendamento(procDto.getExpressaoCRON())) {
                                    final JobDetail jobDetailSQLs = new JobDetail("Processamento_CITSMART_" + procDto.getIdProcessamentoBatch(), "grupoBatch_CITSMART",
                                            JobProcessamentoBatchExecuteSQL.class);
                                    final CronTrigger cronTrigger = new CronTrigger("ProcessamentoBatchCITSMART_" + procDto.getIdProcessamentoBatch(), "CITSMART_PROC_BATCH",
                                            procDto.getExpressaoCRON());
                                    scheduler.scheduleJob(jobDetailSQLs, cronTrigger);
                                    bEntrou = true;
                                    System.out.println("JOB INICIADO COM SUCESSO!!! " + procDto.getIdProcessamentoBatch() + " --> " + procDto.getExpressaoCRON() + " ("
                                            + procDto.getDescricao() + ")");
                                }
                            } catch (final SchedulerException e) {
                                System.out.println("PROBLEMAS AO AGENDAR JOB: " + "Processamento batch CITSMART - SQL: " + procDto.getIdProcessamentoBatch());
                                e.printStackTrace();
                            }
                        }
                        procDto = null;
                    }
                } catch (final Exception e) {
                    System.out.println("PROBLEMAS AO AGENDAR JOB: " + "Processamento batch CITSMART - SQL: ");
                }
            } else {
                System.out.println("SCHEDULER NAO ENCONTRADO - Problemas no start de Processamentos Batch!!!");
            }
            if (bEntrou) {
                try {
                    scheduler.start();
                } catch (final SchedulerException e) {
                    System.out.println("PROBLEMAS AO START OS JOBS BATCH SQLs!!!");
                    e.printStackTrace();
                }
            }
        }
    }

}
