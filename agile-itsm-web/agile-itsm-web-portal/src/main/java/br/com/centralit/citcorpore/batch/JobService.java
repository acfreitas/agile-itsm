package br.com.centralit.citcorpore.batch;

import java.util.ResourceBundle;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.citframework.log.Log;
import br.com.citframework.log.LogFactory;
import br.com.citframework.mail.MailManager;
import br.com.citframework.mail.MailMessage;
import br.com.citframework.util.Constantes;

public abstract class JobService implements Job {

	private static Log log = LogFactory.getLog();
	protected static final String MAIL_CFG_FILE = "MailJob";
	protected static final String DEFAULT_MAIL;
	private Integer idEmpresa;
	
	static {
		ResourceBundle mailConfig = ResourceBundle.getBundle(MAIL_CFG_FILE);
		DEFAULT_MAIL = mailConfig.getString("default.mail");
	}	
	
	private JobExecutionContext contexto;
	
	public JobService() {
		String idEmpStr = Constantes.getValue("ID_EMPRESA_PROC_BATCH");
		if (idEmpStr == null || idEmpStr.trim().equalsIgnoreCase("")){
			idEmpresa = new Integer(1);
		}else{
			idEmpresa = new Integer(idEmpStr);
		}
	}

	/**
	 * <p>
	 * Chamado por <code>{@link org.quartz.Scheduler}</code> quando
	 * <code>{@link org.quartz.Trigger}</code> dispara o que está relacionado com a <code>Job</code>.
	 * </p>
	 * 
	 * @throws JobExecutionException
	 *             if there is an exception while executing the job.
	 */
	public void execute(JobExecutionContext contexto)
			throws JobExecutionException {
		try {
			log.registraLog("Iniciando a execução do job " + this.getClass().getName() + " em " + new java.util.Date() , this.getClass(), Log.INFO);
		} catch (Exception e1) {
			System.out.println("Problemas com o registro de LOGS...");
			e1.printStackTrace();
		}
		this.contexto = contexto;
		try {
			executar();
            MailMessage msg = new MailMessage(DEFAULT_MAIL, "", "", 
                    br.com.citframework.util.Constantes.getValue(
                            "EMAIL_FROM"), "EXECUÇÃO DE JOB - BATCH: " 
                            + this.getClass().getName(), "Execução OK.");
            MailManager mail = new MailManager();
            mail.send(msg);			
		} catch (Exception e) {
			try{
				log.registraLog("Erro na execução do job " 
				        + this.getClass().getName() + " em " 
				        + new java.util.Date() + " -> " + e.getMessage(), 
				        this.getClass(), Log.ERROR);
				log.registraLog(e, this.getClass(), Log.ERROR);
			} catch (Exception e1) {
				System.out.println("Problemas com o registro de LOGS...");
				e1.printStackTrace();
			}	
            MailMessage msg = new MailMessage(DEFAULT_MAIL, "", "", 
                    br.com.citframework.util.Constantes.getValue(
                            "EMAIL_FROM"), 
                            "PROBLEMA - EXECUÇÃO DE JOB - BATCH: " 
                            + this.getClass().getName(), 
                            "Erro na execução do job " 
                            + this.getClass().getName() + " em " 
                            + new java.util.Date() + " -> " + e.getMessage());
            MailManager mail = new MailManager();
            try {
                mail.send(msg);
            } catch (Exception e1) {
                e1.printStackTrace();
            } 
		}
		try {
			log.registraLog("Finalizando a execução do job " + this.getClass().getName() + " em " + new java.util.Date() , this.getClass(), Log.INFO);
		} catch (Exception e1) {
			System.out.println("Problemas com o registro de LOGS...");
			e1.printStackTrace();
		}
	}
	
	public static Log getLog() {
		return log;
	}

	public static void setLog(Log log) {
		JobService.log = log;
	}

	public JobExecutionContext getContexto() {
		return contexto;
	}

	public void setContexto(JobExecutionContext contexto) {
		this.contexto = contexto;
	}
	
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	
	public abstract void executar() throws Exception;
}