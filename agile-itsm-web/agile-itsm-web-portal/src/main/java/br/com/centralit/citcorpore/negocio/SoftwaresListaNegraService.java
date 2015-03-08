package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RelatorioListaNegraDTO;
import br.com.citframework.service.CrudService;

public interface SoftwaresListaNegraService extends CrudService {
	boolean verficiarSoftwareListaNegraMesmoNome(String nome); 
	public Collection<RelatorioListaNegraDTO> listaRelatorioListaNegra(RelatorioListaNegraDTO relatorioListaNegraDTO) throws Exception;
}
