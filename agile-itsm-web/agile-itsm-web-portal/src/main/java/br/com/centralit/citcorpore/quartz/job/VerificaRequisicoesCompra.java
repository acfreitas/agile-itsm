package br.com.centralit.citcorpore.quartz.job;

import java.util.Collection;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.RequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoRequisicaoProduto;
import br.com.centralit.citcorpore.integracao.RequisicaoProdutoDao;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;

public class VerificaRequisicoesCompra implements Job {
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {
		RequisicaoProdutoDao requisicaoProdutoDao = new RequisicaoProdutoDao();
        TransactionControler tc = null;
        try{
        	Collection<RequisicaoProdutoDTO> requisicoes = requisicaoProdutoDao.consultaRequisicoesEmAndamento();
			if (requisicoes != null) {
				ExecucaoRequisicaoProduto execucaoRequisicao = new ExecucaoRequisicaoProduto();
				for (RequisicaoProdutoDTO requisicaoProdutoDto : requisicoes) {
			        tc = new TransactionControlerImpl(requisicaoProdutoDao.getAliasDB());
					try {
						tc.start();
				        execucaoRequisicao.setObjetoNegocioDto(requisicaoProdutoDto);
				        execucaoRequisicao.setTransacao(tc);
				        execucaoRequisicao.verificaExpiracao();
						tc.commit();
			        } catch (Exception ex) {
			            ex.printStackTrace();
			            try {
			                if (tc.isStarted()) { // Se estiver startada, entao faz roolback.
			                    tc.rollback();
			                }
			            } catch (final Exception e) {
			            	e.printStackTrace();
			            }
			        }finally{
						try {
							tc.close();
							tc = null;
						} catch (PersistenceException e) {
						}
			        }
				}
			}
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
