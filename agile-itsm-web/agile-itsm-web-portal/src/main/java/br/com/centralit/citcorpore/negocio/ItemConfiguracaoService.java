/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.centralit.citcorpore.bean.AuditoriaItemConfigDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoValorDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudService;

/**
 * @author VMD
 * 
 */
@SuppressWarnings("rawtypes")
public interface ItemConfiguracaoService extends CrudService {

	public ItemConfiguracaoDTO restoreByIdItemConfiguracao(Integer idItemConfiguracao) throws Exception;

	public ItemConfiguracaoDTO createItemConfiguracao(ItemConfiguracaoDTO itemConfiguracao, UsuarioDTO user) throws ServiceException, LogicException, Exception;

	public ItemConfiguracaoDTO createItemConfiguracaoAplicacao(ItemConfiguracaoDTO itemConfiguracao, UsuarioDTO user) throws ServiceException, LogicException;

	public ItemConfiguracaoDTO listIdUsuario(String obj) throws Exception;

	public void updateItemConfiguracao(IDto ItemConfiguracao, UsuarioDTO user) throws ServiceException, LogicException;

	public void criarEAssociarValorDaCaracteristicaAoItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDto, UsuarioDTO user, Integer IdHistoricoIC) throws Exception;

	public Collection<ItemConfiguracaoDTO> listByGrupo(GrupoItemConfiguracaoDTO grupoICDto, String criticidade, String status) throws Exception;

	public Collection<ItemConfiguracaoDTO> listByGrupo(GrupoItemConfiguracaoDTO grupoICDto, ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception;

	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(String criticidade, String status) throws Exception;

	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception;

	public boolean validaDuplicidadeItemConfiguracao(ItemConfiguracaoDTO bean) throws Exception;

	public void atualizaGrupo(ItemConfiguracaoDTO itemConfiguracaoDTO, UsuarioDTO user) throws Exception;

	public Collection<ItemConfiguracaoDTO> listByEvento(Integer idEvento) throws Exception;

	public Collection<ItemConfiguracaoDTO> pesquisaDataExpiracao(Date data) throws Exception;

	public boolean VerificaSeCadastrado(ItemConfiguracaoDTO itemDTO) throws Exception;

	public void updateNotNull(IDto dto) throws Exception;

	public Collection<ItemConfiguracaoDTO> listByIdItemConfiguracaoPai(Integer idItemPai) throws Exception;

	public void restaurarBaseline(ItemConfiguracaoDTO item, UsuarioDTO user) throws Exception;

	public boolean verificaItemCriticos(Integer idItemConfiguracao) throws Exception;

	public boolean verificaMidiaSoftware(Integer idMidiaSoftware) throws Exception;

	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdMudanca(Integer idMudanca) throws Exception;

	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdProblema(Integer idProblema) throws Exception;

	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdIncidente(Integer idIncidente) throws Exception;

	public void enviarEmailNotificacao(ItemConfiguracaoDTO itemConfiguracaoDTO, TransactionControler transactionControler, String notificacao) throws Exception;

	public Collection<ItemConfiguracaoDTO> listaItemConfiguracaoPorBaseConhecimento(ItemConfiguracaoDTO itemConfiguracao) throws Exception;

	public Collection<ItemConfiguracaoDTO> quantidadeItemConfiguracaoPorBaseConhecimento(ItemConfiguracaoDTO itemConfiguracao) throws Exception;

	public HistoricoValorDTO createHistoricoValor(ValorDTO valor, UsuarioDTO user, Integer idHistoricoIC) throws Exception;

	public HistoricoItemConfiguracaoDTO createHistoricoItem(ItemConfiguracaoDTO itemConfiguracao, UsuarioDTO user) throws Exception;

	public Collection findByIdItemConfiguracaoPai(Integer parm) throws Exception;

	/**
	 * Restaura o Item de Configuração filho de acordo com o idItemConfiguracaoPai, Identificacao e IdTipoItemConfiguracao.
	 * 
	 * @param itemConfiguracaoFilho
	 *            - Item de Configuração Filho.
	 * @return ItemConfiguracaoDTO - Item de Configuração encontrado.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 19.01.2015
	 */
	public ItemConfiguracaoDTO obterICFilhoPorIdentificacaoIdPaiEIdTipo(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception;

	public Integer quantidadeMidiaSoftware(ItemConfiguracaoDTO itemDTO) throws Exception;

	/**
	 * Retorna Itens de Configuração associados ao conhecimento informado.
	 * 
	 * @param baseConhecimentoDto
	 * @return Collection
	 * @throws ServiceException
	 * @throws LogicException
	 * @author Vadoilo Damasceno
	 */
	public Collection findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException;

	public List<AuditoriaItemConfigDTO> historicoAlteracaoItemConfiguracaoByIdItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception;

	public List<ItemConfiguracaoDTO> listaItemConfiguracaoOfficePak(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception;

	public List<ItemConfiguracaoDTO> listaItemConfiguracaoOfficePak(ItemConfiguracaoDTO itemConfiguracaoDTO, String chave) throws Exception;

	public void atualizaParaGrupoProducao(int idItem) throws ServiceException, Exception;

	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdLiberacao(Integer idLiberacao) throws Exception;

	public void createHistoricoItemComOrigem(ItemConfiguracaoDTO itemConfiguracao, RequisicaoLiberacaoDTO liberacao, String origem) throws Exception;

	public boolean atualizaStatus(Integer item, Integer status);

	public ItemConfiguracaoDTO findByIdentificacaoItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception;

	public Collection<ItemConfiguracaoDTO> listByIdGrupoAndTipoItemAndIdItemPaiAtivos(Integer idGrupo, Integer idTipo, Integer idPai) throws Exception;

	public Collection<ItemConfiguracaoDTO> listByIdItemPaiAndTagTipoItemCfg(Integer idItemConfiguracaoPai, String tagTipoCfg) throws Exception;

	public Collection<ItemConfiguracaoDTO> listAtivos() throws Exception;

	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(String criticidade, String status, String sistemaOperacional, String grupoTrabalho, String tipoMembroDominio, String usuario,
			String processador, List softwares) throws Exception;

	public Collection<ItemConfiguracaoDTO> listByIdentificacao(String identif) throws Exception;

}