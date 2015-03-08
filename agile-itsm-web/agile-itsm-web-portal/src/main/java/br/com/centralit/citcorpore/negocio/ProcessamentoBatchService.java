package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface ProcessamentoBatchService extends CrudService {
	public Collection getAtivos() throws Exception;
	public boolean existeDuplicidade(ProcessamentoBatchDTO processamentoBatch) throws Exception;
	public boolean existeDuplicidadeClasse(ProcessamentoBatchDTO processamentoBatch) throws Exception;
	public ProcessamentoBatchDTO findById(Integer idProcessamentoBatchPadrao);
	public void agendaJob(ProcessamentoBatchDTO processamentoBatchDTO, DocumentHTML document, HttpServletRequest request);
	public void setaPropriedadesExpressaoCron(ProcessamentoBatchDTO processamentoBatchDTO);
	public void populaSelects(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public void montaExpressaoCron(ProcessamentoBatchDTO processamentoBatchDTO);
	public boolean validaExpressaoCron(DocumentHTML document, HttpServletRequest request, ProcessamentoBatchDTO processamentoBatchDTO);
	public ProcessamentoBatchDTO getAgendamentoPadrao() throws ServiceException, Exception;
	public Date proximaExecucao(String expressaoCRON);
	public boolean permiteAgendamento(String expressaoCRON);
}
