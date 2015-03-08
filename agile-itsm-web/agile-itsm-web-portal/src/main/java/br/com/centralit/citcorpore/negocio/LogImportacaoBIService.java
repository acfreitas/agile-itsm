package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LogImportacaoBIDTO;
import br.com.citframework.service.CrudService;

public interface LogImportacaoBIService extends CrudService {
	public Collection<LogImportacaoBIDTO> listarLogsByConexaoBI(Integer idConexaoBI) throws Exception;
	public Integer calculaTotalPaginas(Integer idConexaoBI, Integer itensPorPagina) throws Exception;	
	public Collection<LogImportacaoBIDTO> paginacaoLog(Integer idConexaoBI, Integer pgAtual, Integer qtdPaginacao) throws Exception;
}
