package br.com.centralit.citcorpore.quartz.job;

import java.text.Normalizer;
import java.util.Collection;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.citframework.service.ServiceLocator;

public class AtualizaNomeProcuraEmpregados implements Job {
	@SuppressWarnings("unchecked")
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

			boolean aindaExistemRegistros = true;
			int intervalo = 200;
			int indice = 0;
			do {
				Collection<EmpregadoDTO> empregados = empregadoService.listarIdEmpregados(intervalo, indice);
				if(empregados != null){
					for (EmpregadoDTO empregado : empregados) {
						empregado = (EmpregadoDTO) empregadoService.restore(empregado);
						String nomeProcura = empregado.getNome();
						nomeProcura = nomeProcura.trim();
						nomeProcura = nomeProcura.toUpperCase();
						nomeProcura = Normalizer.normalize(nomeProcura, Normalizer.Form.NFD);
						nomeProcura = nomeProcura.replaceAll("[^\\p{ASCII}]", "");
						empregado.setNomeProcura(nomeProcura);
						empregadoService.update(empregado);
					}
				}

				indice += intervalo;
				if (empregados == null || empregados.size() < intervalo) {
					aindaExistemRegistros = false;
				}

			} while (aindaExistemRegistros);
			System.out.println("Atualização de nomeProcura dos empregados concluída");
		} catch (Exception e) {
			System.out.println("ERRO -  " + e.getMessage());
		}
	}
}
