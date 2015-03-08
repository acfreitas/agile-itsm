package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.DadosEmailRegOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface OcorrenciaService extends CrudService {
	public Collection findByDemanda(Integer idDemanda) throws LogicException, ServiceException;
	public void updateResposta(IDto bean) throws LogicException, ServiceException;
	public Collection<OcorrenciaDTO> findByIdSolicitacao(Integer idSolicitacaoServico) throws Exception;
	public OcorrenciaDTO findSiglaGrupoExecutorByIdSolicitacao(Integer idSolicitacaoServico) throws Exception;
	
	/**
	 * Metodo responsavel por obter os dados de controle de email, foi criado um DTO DadosEmailRegOcorrenciaDTO criado para facilitar o retorno de alguns campos especificos para 
	 * essa funcionalidade, como:
	 * <p>  
	 * -idResponsavelAtual	
	 * -idGrupoAtual;
	 * 
	 * @param idSolicitacaoServico
	 * @return
	 * @throws Exception
	 */
	public DadosEmailRegOcorrenciaDTO obterDadosResponsavelEmailRegOcorrencia(Integer idSolicitacaoServico) throws Exception;
	
}
