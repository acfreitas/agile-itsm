package br.com.centralit.citcorpore.negocio;

import java.util.HashMap;

import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ExportacaoContratosService extends CrudService {
	public ObjetoNegocioDTO restoreByName(String name) throws Exception;
	public String exportDB(String dbName, String filterAditional, String[] fieldValidExistence, String[] exclusion, String type) throws Exception;
	public String generateSQLIn(String xml, String id) throws Exception;
	public String recuperaXmlTabelas(Integer idContrato, Integer[] idGrupos, String exportarUnidades, String exportarAcordoNivelServico, String exportarCatalogoServico) throws Exception;
	public StringBuilder geraExportObjetoNegocio(HashMap hashValores, Integer idObjetoNegocio, String sqlDelete, String filterAditional, String order, String[] fieldValidExistence, String[] exclusion, String type) throws ServiceException, Exception;
}
