package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleRendimentoUsuarioDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface ControleRendimentoUsuarioService extends CrudService{
	
	public Collection<ControleRendimentoUsuarioDTO> findByIdControleRendimentoUsuario(Integer idGrupo, String mes, String ano) throws ServiceException, LogicException;
	public Collection<ControleRendimentoUsuarioDTO> findByIdControleRendimentoMelhoresUsuario(Integer idGrupo, String mesInicio, String mesFim, String ano, String anoFim, Boolean deUmAnoParaOOutro) throws ServiceException, LogicException;
	public Collection<ControleRendimentoUsuarioDTO> findIdsControleRendimentoUsuarioPorPeriodo(Integer idGrupo, String mesInicio, String mesFim, String ano, String anoFim, Boolean deUmAnoParaOOutro) throws ServiceException, LogicException;		
}
