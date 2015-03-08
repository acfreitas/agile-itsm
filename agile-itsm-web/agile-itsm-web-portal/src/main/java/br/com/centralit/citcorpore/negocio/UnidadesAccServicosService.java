package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface UnidadesAccServicosService extends CrudService {
	
	public Collection findByIdUnidade(Integer parm) throws Exception;
	public void deleteByIdUnidade(Integer parm) throws Exception;
	public Collection findByIdServico(Integer parm) throws Exception;
	public void deleteByIdServico(Integer parm) throws Exception;
	
	public List deserealizaObjetosDoRequest(HttpServletRequest request) throws Exception;
	
	public Collection<UnidadesAccServicosDTO> consultarServicosAtivosPorUnidade(Integer idUnidade) throws Exception;
	
	/**
     * Exclui associação de Serviços com Unidade
     * 
     * @throws ServiceException
     * @throws PersistenceException
     * @author rodrigo.oliveira
     * @param idUnidade, idServico
     * @throws Exception
     */
    public void excluirAssociacaoServicosUnidade(Integer idUnidade, Integer idServico)
	    throws PersistenceException, ServiceException, Exception;
	
}
