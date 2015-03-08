package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.GrupoItemConfiguracaoDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface GrupoItemConfiguracaoService extends CrudService {
	
	public boolean VerificaSeCadastrado(GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO) throws PersistenceException;
	
	/**
	 * Método que traz a lista de Grupo de item de configuração relacionados ao evento passado como parâmetro
	 * 
	 * @param idEvento
	 * @return Collection<GrupoItemConfiguracaoDTO> relacionado ao evento
	 * @throws ServiceException
	 */
	public Collection<GrupoItemConfiguracaoDTO> listByEvento(Integer idEvento) throws ServiceException;
	
	public void updateNotNull(IDto dto) throws Exception;
	
	public boolean verificaICRelacionados(GrupoItemConfiguracaoDTO grupoItemConfiguracao) throws PersistenceException, Exception;
	
	public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracaoPai(Integer idGrupoItemConfiguracaoPai) throws Exception;
	
	public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracaoDesenvolvimento(Integer idGrupoItemConfiguracaoPai) throws Exception;
	
	public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracao(Integer idGrupoItemConfiguracaoPai) throws Exception;
	
	public Collection listHierarquiaGruposByIdGrupo(Integer idGrupo, GrupoItemConfiguracaoDAO dao) throws Exception;
	
	public Collection listHierarquiaGrupoPaiNull() throws Exception;
	
	public void autenticaGrupoPadrao(int id, int idPai, String nome) throws Exception;
	
}