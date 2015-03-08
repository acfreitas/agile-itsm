package br.com.centralit.citcorpore.negocio;

import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.CronExpression;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.batch.JobProcessamentoBatchExecuteSQL;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.integracao.ProcessamentoBatchDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilI18N;

public class ProcessamentoBatchServiceEjb extends CrudServiceImpl implements ProcessamentoBatchService{

	private static final long serialVersionUID = -2335223427371986078L;

	private ProcessamentoBatchDao dao;

	protected ProcessamentoBatchDao getDao() {
		if (dao == null) {
			dao = new ProcessamentoBatchDao();
		}
		return dao;
	}

	public Collection getAtivos() throws Exception {
		return getDao().getAtivos();
	}

	public boolean existeDuplicidade(ProcessamentoBatchDTO processamentoBatch) throws Exception {
		return getDao().existeDuplicidade(processamentoBatch);
	}

	public boolean existeDuplicidadeClasse(ProcessamentoBatchDTO processamentoBatch) throws Exception{
		return getDao().existeDuplicidadeClasse(processamentoBatch);
	}

	@Override
	public ProcessamentoBatchDTO findById(Integer idProcessamentoBatchPadrao) {
		return getDao().findById(idProcessamentoBatchPadrao);
	}

	public void agendaJob(ProcessamentoBatchDTO procDto, DocumentHTML document, HttpServletRequest request) {
		org.quartz.SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
		org.quartz.Scheduler scheduler = null;
		try {
			scheduler = schedulerFactory.getScheduler("CitSmartMonitor");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}

		boolean bAgendouFase1 = false;
		boolean bAgendouFase2 = false;
		if (scheduler != null) {
			if (procDto.getSituacao().equalsIgnoreCase("A")) {
				try {
					JobDetail jobDetailSQLs = new JobDetail("Processamento_CITSMART_" + procDto.getIdProcessamentoBatch(), "grupoBatch_CITSMART", JobProcessamentoBatchExecuteSQL.class);
					CronTrigger cronTrigger = new CronTrigger("ProcessamentoBatchCITSMART_" + procDto.getIdProcessamentoBatch(), "CITSMART_PROC_BATCH", procDto.getExpressaoCRON());
					String str[] = scheduler.getJobNames("grupoBatch_CITSMART");
					boolean existeJobAdicionado = false;
					if (str != null) {
						for (int i = 0; i < str.length; i++) {
							if (str[i].equalsIgnoreCase("Processamento_CITSMART_" + procDto.getIdProcessamentoBatch())) {
								existeJobAdicionado = true;
							}
						}
					}
					if (existeJobAdicionado) {
						try {
							boolean retDelJob = scheduler.deleteJob("Processamento_CITSMART_" + procDto.getIdProcessamentoBatch(), "grupoBatch_CITSMART");
							System.out.println("Exclusao do Job - " + "Processamento_CITSMART_" + procDto.getIdProcessamentoBatch() + ": " + retDelJob);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					scheduler.scheduleJob(jobDetailSQLs, cronTrigger);
					bAgendouFase1 = true;
					System.out.println("JOB INICIADO COM SUCESSO!!! " + procDto.getIdProcessamentoBatch() + " --> " + procDto.getExpressaoCRON());
				} catch (SchedulerException e) {
					System.out.println("PROBLEMAS AO AGENDAR JOB: " + "Processamento batch CITSMART - SQL: " + procDto.getIdProcessamentoBatch());
					e.printStackTrace();
				} catch (ParseException e) {
					System.out.println("PROBLEMAS AO AGENDAR JOB: " + "Processamento batch CITSMART - SQL: " + procDto.getIdProcessamentoBatch());
					e.printStackTrace();
				}
			}
		} else {
			System.out.println("SCHEDULER NAO ENCONTRADO - Problemas no start de Processamentos Batch!!!");
		}
		if (bAgendouFase1){
			try {
				scheduler.start();
				bAgendouFase2 = true;
			} catch (SchedulerException e) {
				System.out.println("PROBLEMAS AO START OS JOBS BATCH SQLs!!!");
				e.printStackTrace();
			}
		}
		if ((!bAgendouFase1) || (!bAgendouFase2)) {
			document.alert(UtilI18N.internacionaliza(request, "processamentoBatch.problemaNoAgendamento"));
		}
	}

	public void montaExpressaoCron(ProcessamentoBatchDTO processamentoBatchDTO) {
		String expressaoCron = new String();
		if (processamentoBatchDTO.getSegundos() != null) {
			expressaoCron += processamentoBatchDTO.getSegundos() + " ";
		}
		if (processamentoBatchDTO.getMinutos() != null) {
			expressaoCron += processamentoBatchDTO.getMinutos() + " ";
		}
		if (processamentoBatchDTO.getHoras() != null) {
			expressaoCron += processamentoBatchDTO.getHoras() + " ";
		}
		if (processamentoBatchDTO.getDiaDoMes() != null) {
			expressaoCron += processamentoBatchDTO.getDiaDoMes() + " ";
		}
		if (processamentoBatchDTO.getMes() != null) {
			expressaoCron += processamentoBatchDTO.getMes() + " ";
		}
		if (processamentoBatchDTO.getDiaDaSemana() != null) {
			expressaoCron += processamentoBatchDTO.getDiaDaSemana() + " ";
		}
		if (processamentoBatchDTO.getAno() != null) {
			expressaoCron += processamentoBatchDTO.getAno();
		}
		processamentoBatchDTO.setExpressaoCRON(expressaoCron);
	}

	public void setaPropriedadesExpressaoCron(ProcessamentoBatchDTO processamentoBatchDTO) {
		if (processamentoBatchDTO.getExpressaoCRON() != null && !processamentoBatchDTO.getExpressaoCRON().isEmpty()) {
			String expressaoCron = processamentoBatchDTO.getExpressaoCRON().trim();
			expressaoCron = expressaoCron.replaceAll("  ", " ");
			String partes[] = expressaoCron.split(" ");
			if (partes.length > 5) {
				processamentoBatchDTO.setSegundos(partes[0]);
				processamentoBatchDTO.setMinutos(partes[1]);
				processamentoBatchDTO.setHoras(partes[2]);
				processamentoBatchDTO.setDiaDoMes(partes[3]);
				processamentoBatchDTO.setMes(partes[4]);
				processamentoBatchDTO.setDiaDaSemana(partes[5]);
			}
			if (partes.length > 6) {
				processamentoBatchDTO.setAno(partes[6]);
			}
		}
	}

	/**
	 * @author euler.ramos
	 */
	public boolean validaExpressaoCron(DocumentHTML document, HttpServletRequest request, ProcessamentoBatchDTO processamentoBatchDTO) {
		if ((processamentoBatchDTO.getDiaDoMes() != null)&&(processamentoBatchDTO.getDiaDaSemana() != null)) {
			//'?' can only be specfied for Day-of-Month -OR- Day-of-Week.
			if ((processamentoBatchDTO.getDiaDoMes().equalsIgnoreCase("?"))&&(processamentoBatchDTO.getDiaDaSemana().equalsIgnoreCase("?"))) {
				document.alert(UtilI18N.internacionaliza(request,"processamentoBatch.aoMenosDiaMesOuDiaSemana"));
				return false;
			}
			//Somente o Dia do mês ou dia da semana deve ser informado.
			//Support for specifying both a day-of-week AND a day-of-month parameter is not implemented.
			if ((!processamentoBatchDTO.getDiaDoMes().equalsIgnoreCase("?"))&&(!processamentoBatchDTO.getDiaDaSemana().equalsIgnoreCase("?"))) {
				document.alert(UtilI18N.internacionaliza(request,"processamentoBatch.diaMesdiaSemanaMesmoTempo"));
				return false;
			}
		}
		//Verificando a expressão cron para saber se define um agendamento passível de ser executado.
		if((processamentoBatchDTO.getExpressaoCRON() != null)&&(processamentoBatchDTO.getExpressaoCRON().length()>0)){
			Date dtProximaEx = this.proximaExecucao(processamentoBatchDTO.getExpressaoCRON());
			if (dtProximaEx == null){
				document.alert(UtilI18N.internacionaliza(request,"processamentoBatch.naoSeraExecutado"));
				return false;
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request,"processamentoBatch.expressaoCronInvalida"));
			return false;
		}
		return true;
	}

	/**
	* @author euler.ramos
	*/
	public boolean permiteAgendamento(String cronExpr){
		boolean resultado = false;
		if(CronExpression.isValidExpression(cronExpr)){
			Date dataProxExec = this.proximaExecucao(cronExpr);
			if (dataProxExec!=null){
				resultado = true;
			}
		}
		return resultado;
	}

	public void populaSelects(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HTMLSelect comboAno = (HTMLSelect) document.getSelectById("ano");
		comboAno.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		for (Integer ano = 2014; ano <= 2020; ano++) {
			comboAno.addOption(ano.toString(), ano.toString());
		}

		HTMLSelect comboDiaDaSemana = (HTMLSelect) document.getSelectById("diaDaSemana");
		comboDiaDaSemana.addOption("?", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboDiaDaSemana.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		comboDiaDaSemana.addOption("2", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.segundaFeira"));
		comboDiaDaSemana.addOption("3", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.tercaFeira"));
		comboDiaDaSemana.addOption("4", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.quartaFeira"));
		comboDiaDaSemana.addOption("5", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.quintaFeira"));
		comboDiaDaSemana.addOption("6", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.sextaFeira"));
		comboDiaDaSemana.addOption("7", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.sabado"));
		comboDiaDaSemana.addOption("1", UtilI18N.internacionaliza(request, "citcorpore.texto.diaSemana.domingo"));

		HTMLSelect comboDiaDoMes = (HTMLSelect) document.getSelectById("diaDoMes");
		comboDiaDoMes.addOption("?", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		comboDiaDoMes.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		for (Integer dia = 1; dia <= 31; dia++) {
			comboDiaDoMes.addOption(dia.toString(), dia.toString());
		}

		HTMLSelect comboHoras = (HTMLSelect) document.getSelectById("horas");
		comboHoras.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todas"));
		for (Integer hora = 0; hora <= 23; hora++) {
			comboHoras.addOption(hora.toString(), hora.toString());
		}

		HTMLSelect comboMes = (HTMLSelect) document.getSelectById("mes");
		comboMes.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		comboMes.addOption("1", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.janeiro"));
		comboMes.addOption("2", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.fevereiro"));
		comboMes.addOption("3", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.marco"));
		comboMes.addOption("4", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.abril"));
		comboMes.addOption("5", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.maio"));
		comboMes.addOption("6", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.junho"));
		comboMes.addOption("7", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.julho"));
		comboMes.addOption("8", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.agosto"));
		comboMes.addOption("9", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.setembro"));
		comboMes.addOption("10", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.outubro"));
		comboMes.addOption("11", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.novembro"));
		comboMes.addOption("12", UtilI18N.internacionaliza(request, "citcorpore.texto.mes.dezembro"));

		HTMLSelect comboMinutos = (HTMLSelect) document.getSelectById("minutos");
		comboMinutos.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		for (Integer minuto = 0; minuto <= 59; minuto++) {
			comboMinutos.addOption(minuto.toString(), minuto.toString());
		}

		HTMLSelect comboSegundos = (HTMLSelect) document.getSelectById("segundos");
		comboSegundos.addOption("*", UtilI18N.internacionaliza(request, "citcorpore.comum.todos"));
		for (Integer segundo = 0; segundo <= 59; segundo++) {
			comboSegundos.addOption(segundo.toString(), segundo.toString());
		}

		HTMLSelect comboSituacao = (HTMLSelect) document.getSelectById("situacao");
		comboSituacao.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.ativo"));
		comboSituacao.addOption("I", UtilI18N.internacionaliza(request, "citcorpore.comum.inativo"));
		comboSituacao.setSelectedIndex(1);

	}

	/**
	 * Retorna a próxima data de execução definida na expressão Cron passada como parâmetro
	 * @author euler.ramos
	 */
	public Date proximaExecucao(String expressaoCron){
		try {
			Date data = new Date();
			CronExpression cronExpression = new CronExpression(expressaoCron);
			return cronExpression.getNextValidTimeAfter(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ProcessamentoBatchDTO getAgendamentoPadrao() throws ServiceException, Exception {
		return getDao().getAgendamentoPadrao();
	}

}