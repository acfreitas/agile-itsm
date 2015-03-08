package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.SchedulerException;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.batch.JobService;
import br.com.centralit.citcorpore.bean.ExecucaoBatchDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.integracao.ExecucaoBatchDao;
import br.com.centralit.citcorpore.negocio.ProcessamentoBatchService;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.service.ServiceLocator;
//import org.apache.tools.ant.util.DateUtils;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings({ "rawtypes", "unused" })
public class ProcessamentoBatch extends AjaxFormAction {

	public Class getBeanClass() {
		return ProcessamentoBatchDTO.class;
	}

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ProcessamentoBatchDTO processamentoBatchBean = (ProcessamentoBatchDTO) document.getBean();
		ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
		processamentoBatchService.populaSelects(document, request, response);
		HTMLSelect comboTipo = (HTMLSelect) document.getSelectById("tipo");
		if (comboTipo!=null){
			comboTipo.addOption("C", UtilI18N.internacionaliza(request, "processamentoBatch.classeJava"));
			comboTipo.addOption("S", UtilI18N.internacionaliza(request, "processamentoBatch.sql"));
		}
	}

	public void restore(DocumentHTML document, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		ProcessamentoBatchDTO processamentoBatchDTO = (ProcessamentoBatchDTO) document.getBean();
		ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
		processamentoBatchDTO = (ProcessamentoBatchDTO) processamentoBatchService.restore(processamentoBatchDTO);

		processamentoBatchService.setaPropriedadesExpressaoCron(processamentoBatchDTO);

		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(processamentoBatchDTO);
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse arg2) throws Exception {
		ProcessamentoBatchDTO processamentoBatchDTO = (ProcessamentoBatchDTO) document.getBean();
		ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
		processamentoBatchDTO.setConteudo(processamentoBatchDTO.getConteudo().trim());
		processamentoBatchService.montaExpressaoCron(processamentoBatchDTO);

		if (processamentoBatchService.validaExpressaoCron(document,request,processamentoBatchDTO)){
			if (processamentoBatchDTO.getIdProcessamentoBatch() != null && processamentoBatchDTO.getIdProcessamentoBatch().intValue() > 0) {
				processamentoBatchService.update(processamentoBatchDTO);
				
				if (processamentoBatchDTO != null && processamentoBatchDTO.getSituacao() != null && processamentoBatchDTO.getSituacao().equalsIgnoreCase("A")) {
					processamentoBatchService.agendaJob(processamentoBatchDTO, document, request);	
				}
				
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			} else {
				boolean existeDuplicidade = processamentoBatchService.existeDuplicidade(processamentoBatchDTO);

				if (existeDuplicidade) {
					document.alert(UtilI18N.internacionaliza(request, "MSE01"));
					return;
				} else {
					processamentoBatchDTO = (ProcessamentoBatchDTO) processamentoBatchService.create(processamentoBatchDTO);
					
					if (processamentoBatchDTO != null && processamentoBatchDTO.getSituacao() != null && processamentoBatchDTO.getSituacao().equalsIgnoreCase("A")) {
						processamentoBatchService.agendaJob(processamentoBatchDTO, document, request);
					}
					
					document.alert(UtilI18N.internacionaliza(request, "MSG05"));
				}
			}
			HTMLForm form = document.getForm("form");
			form.clear();
		}
	}
	
	@SuppressWarnings("unchecked")
	public void mostrarExecucoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		ProcessamentoBatchDTO processamentoBatchBean = (ProcessamentoBatchDTO) document.getBean();
		StringBuilder tabela = new StringBuilder();
		try {
			HTMLTable tblExecucao = document.getTableById("tblExecucao");
			tblExecucao.deleteAllRows();
			tabela.append("<table id='tblExecucao' name='tblExecucao' class='table  table-bordered'>");
			tabela.append("<tr>");
			tabela.append("<th height='10px' width='15%'>"+UtilI18N.internacionaliza(request,"citcorpore.comum.datahora")+"</th>");
			tabela.append("<th height='10px' width='85%'>"+UtilI18N.internacionaliza(request,"citcorpore.comum.conteudo")+"</th>");
			tabela.append("</tr>");
			if (processamentoBatchBean.getIdProcessamentoBatch() != null) {
				ExecucaoBatchDao execDao = new ExecucaoBatchDao();
				Collection<ExecucaoBatchDTO> listaExecucoes = execDao.findByIdProcBatch(processamentoBatchBean.getIdProcessamentoBatch());
				if (listaExecucoes != null) {
					
					String txtDataHora = "";
					String txtConteudo = "";
					
					Integer i = 1;
					for (ExecucaoBatchDTO execucaoBatchDTO : listaExecucoes) {
						
						if (i<11){
							txtDataHora = (String) ((execucaoBatchDTO.getDataHora()!=null)? Util.formatDateDDMMYYYYHHMMSS(execucaoBatchDTO.getDataHora()):"");
							txtConteudo = (String) ((execucaoBatchDTO.getConteudo()!=null)?execucaoBatchDTO.getConteudo().toString():"");
							
							tabela.append("<tr>");
							tabela.append("<th>"+txtDataHora+"</th>");
							tabela.append("<th>"+txtConteudo+"</th>");
							tabela.append("</tr>");
						} else {
							break;
						}
						
						i+=1;
					}
				}
			}
			tabela.append("</table>");
			tblExecucao.setInnerHTML(tabela.toString());
			document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblExecucao', 'tblExecucao');");
			document.executeScript("$('#modal_Execucoes').modal();");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listaProcessamentosBatch(DocumentHTML document, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		org.quartz.SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
		// org.quartz.Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		org.quartz.Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void executaJobService(DocumentHTML document, HttpServletRequest arg1, HttpServletResponse arg2) throws Exception {
		ProcessamentoBatchDTO processamentoBatchBean = (ProcessamentoBatchDTO) document.getBean();
		if (processamentoBatchBean.getNomeClasseJobService() == null || processamentoBatchBean.getNomeClasseJobService().trim().length() == 0)
			return;

		JobService jobService = (JobService) Class.forName(processamentoBatchBean.getNomeClasseJobService()).newInstance();
		jobService.execute(null);
	}
	
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse arg2) throws Exception {
		ProcessamentoBatchDTO processamentoBatchBean = (ProcessamentoBatchDTO) document.getBean();
		ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);

		if (processamentoBatchBean.getIdProcessamentoBatch() == null || processamentoBatchBean.getIdProcessamentoBatch().intValue() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.necessarioSelecionarRegistro"));
			return;
		} else {
			processamentoBatchBean.setSituacao("I");
			processamentoBatchService.update(processamentoBatchBean);
			document.alert(UtilI18N.internacionaliza(request, "MSG07"));
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		document.executeScript("document.location.reload()");
	}

}