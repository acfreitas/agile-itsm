package br.com.centralit.citcorpore.batch;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.ExecucaoBatchDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.integracao.ExecucaoBatchDao;
import br.com.centralit.citcorpore.negocio.ProcessamentoBatchService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class JobProcessamentoBatchExecuteSQL implements Job {

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		String strNomeJob = jobExecutionContext.getJobDetail().getName();
		System.out.println("----> Iniciando Processamento Batch: " + strNomeJob);
		String numStr = UtilStrings.apenasNumeros(strNomeJob);
		Integer idProcessamentoBatch = new Integer(numStr);
		
	    ProcessamentoBatchDTO procBean = new ProcessamentoBatchDTO();
	    procBean.setIdProcessamentoBatch(idProcessamentoBatch);
	    ProcessamentoBatchService procService = null;
		try {
			procService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ExecucaoBatchDao execucaoBatchDao = new ExecucaoBatchDao();
		if (procService != null){
		    try {
				procBean = (ProcessamentoBatchDTO) procService.restore(procBean);
			} catch (LogicException e) {
				e.printStackTrace();
				procBean = null;
			} catch (ServiceException e) {
				e.printStackTrace();
				procBean = null;
			}
			if (procBean != null){
				Timestamp tsExecucao = UtilDatas.getDataHoraAtual();
				if (procBean.getSituacao().equalsIgnoreCase("A")){
					String retorno = "";
					System.out.println("JOB INICIANDO EXECUCAO -> " + procBean.getIdProcessamentoBatch() + " --> " + procBean.getExpressaoCRON() + " (" + procBean.getDescricao() + ")");
					if (procBean.getTipo().equalsIgnoreCase("S")){
						retorno = executaSQL(procBean.getConteudo(), strNomeJob);
					}else{
						try {
							retorno = executaClasse(procBean.getConteudo(), strNomeJob,jobExecutionContext);
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
					ExecucaoBatchDTO execDto = new ExecucaoBatchDTO();
					execDto.setIdProcessamentoBatch(idProcessamentoBatch);
					execDto.setDataHora(tsExecucao);
					execDto.setConteudo(retorno);
					try {
						execucaoBatchDao.create(execDto);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else{
					ExecucaoBatchDTO execDto = new ExecucaoBatchDTO();
					execDto.setIdProcessamentoBatch(idProcessamentoBatch);
					execDto.setDataHora(tsExecucao);
					execDto.setConteudo("PROCESSAMENTO INATIVO! CADASTRO INATIVO!");
					try {
						execucaoBatchDao.create(execDto);
					} catch (Exception e) {
						e.printStackTrace();
					}					
				}
			}
		}
	}
	
	private String executaSQL(String sql, String nomeJob){
        JdbcEngine engineDb = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
        int num = 0;
        try {
        	num = engineDb.execUpdate(sql, null);
        	System.out.println(nomeJob + " - EXECUÇÃO OK! Número de Linhas atualizadas: " + num);
		} catch (PersistenceException e1) {
			System.out.println(nomeJob + " - Problemas na execucao: ");
			e1.printStackTrace();
		    Writer writer = new StringWriter();
		    PrintWriter printWriter = new PrintWriter(writer);
		    e1.printStackTrace(printWriter);
		    return writer.toString();			
		}  		
		return "EXECUÇÃO OK! Número de Linhas atualizadas: " + num;
	}
	
	private String executaClasse(String pathClasseParm, String nomeJob){
		Class classe = null;
		try {
			classe = Class.forName(pathClasseParm);
		} catch (ClassNotFoundException e1) {
			System.out.println(nomeJob + " - Problemas na execucao: ");
			e1.printStackTrace();
		    Writer writer = new StringWriter();
		    PrintWriter printWriter = new PrintWriter(writer);
		    e1.printStackTrace(printWriter);
		    return writer.toString();				
		}
		Object objeto = null;
		try {
			objeto = classe.newInstance();
		} catch (InstantiationException e1) {
			System.out.println(nomeJob + " - Problemas na execucao: ");
			e1.printStackTrace();
		    Writer writer = new StringWriter();
		    PrintWriter printWriter = new PrintWriter(writer);
		    e1.printStackTrace(printWriter);
		    return writer.toString();			
		} catch (IllegalAccessException e1) {
			System.out.println(nomeJob + " - Problemas na execucao: ");
			e1.printStackTrace();
		    Writer writer = new StringWriter();
		    PrintWriter printWriter = new PrintWriter(writer);
		    e1.printStackTrace(printWriter);
		    return writer.toString();			
		}
		
		Method mtd = Reflexao.findMethod("executar", objeto);
		if (mtd != null){
			try {
				mtd.invoke(objeto, null);
			} catch (IllegalArgumentException e1) {
				System.out.println(nomeJob + " - Problemas na execucao: ");
				e1.printStackTrace();
			    Writer writer = new StringWriter();
			    PrintWriter printWriter = new PrintWriter(writer);
			    e1.printStackTrace(printWriter);
			    return writer.toString();				
			} catch (IllegalAccessException e1) {
				System.out.println(nomeJob + " - Problemas na execucao: ");
				e1.printStackTrace();
			    Writer writer = new StringWriter();
			    PrintWriter printWriter = new PrintWriter(writer);
			    e1.printStackTrace(printWriter);
			    return writer.toString();				
			} catch (InvocationTargetException e1) {
				System.out.println(nomeJob + " - Problemas na execucao: ");
				e1.printStackTrace();
			    Writer writer = new StringWriter();
			    PrintWriter printWriter = new PrintWriter(writer);
			    e1.printStackTrace(printWriter);
			    return writer.toString();				
			}
		}else{
			System.out.println(nomeJob + " - Problemas na execucao: ");
			System.out.println(nomeJob + " PROBLEMA: METODO NAO ENCONTRADAO (executar)!");
			return "PROBLEMA: METODO NAO ENCONTRADAO (executar)!";
		}
		
		return "EXECUÇÃO OK!" ;
	}
	/*Refatorando o metodo executa classe com novo paramentro de contexto
	 * Alterado também o metodo a ser executado para 'execute'
	 * 
	 * */
	private String executaClasse(String pathClasseParm, String nomeJob, JobExecutionContext jobExecutionContext) throws JobExecutionException, ClassNotFoundException{
		
		Class classe = Class.forName(pathClasseParm);
		
			try {
				Object objeto = classe.newInstance();
				Method method = Reflexao.findMethod("execute", objeto);
				if(method!=null) {
					method.invoke(objeto, jobExecutionContext);
				}
				else {
					System.out.println(nomeJob + " - Problemas na execucao: ");
					System.out.println(nomeJob + " PROBLEMA: METODO NAO ENCONTRADAO (execute)!");
					return "PROBLEMA: METODO NAO ENCONTRADAO (execute)!";
				}				
			} catch(Exception ex) {
				System.out.println(nomeJob + " - Problemas na execucao: ");			
				throw new JobExecutionException(ex.getMessage());
			}
		
		return "EXECUÇÃO OK!" ;
	}

}
