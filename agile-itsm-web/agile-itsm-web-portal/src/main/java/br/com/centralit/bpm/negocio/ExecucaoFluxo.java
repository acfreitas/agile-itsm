package br.com.centralit.bpm.negocio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.ElementoFluxoInicioDTO;
import br.com.centralit.bpm.dto.ElementoFluxoTarefaDTO;
import br.com.centralit.bpm.dto.ExecucaoFluxoDTO;
import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.FormDinamicoDTO;
import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.ObjetoNegocioFluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.dto.UsuarioBpmDTO;
import br.com.centralit.bpm.integracao.AtribuicaoFluxoDao;
import br.com.centralit.bpm.integracao.ElementoFluxoDao;
import br.com.centralit.bpm.integracao.ElementoFluxoInicioDao;
import br.com.centralit.bpm.integracao.FluxoDao;
import br.com.centralit.bpm.integracao.FormDinamicoDao;
import br.com.centralit.bpm.integracao.TarefaFluxoDao;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.UtilI18N;

public abstract class ExecucaoFluxo extends NegocioBpm implements IExecucaoFluxo {

	protected FluxoDTO fluxoDto;
	protected ExecucaoFluxoDTO execucaoFluxoDto;
	protected ObjetoNegocioFluxoDTO objetoNegocioDto;
	protected Usuario usuario;

	protected void rollbackTransaction(TransactionControler tc, Exception ex) throws ServiceException, LogicException {
		try {
			ex.printStackTrace();
			if (tc.isStarted()) { // Se estiver startada, entao faz roolback.
				tc.rollback();
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			tc.closeQuietly();
		}

		if (ex instanceof LogicException) {
			throw (LogicException) ex;
		}
		throw new ServiceException(ex);
	}

	protected void recuperaFluxo(String nomeFluxo) throws Exception {
		this.fluxoDto = new FluxoDao().findByNome(nomeFluxo);
		if (fluxoDto == null)
			throw new Exception("Fluxo " + nomeFluxo + " não existe");
	}

	protected void recuperaFluxo(Integer idFluxo) throws Exception {
		fluxoDto = new FluxoDTO();
		fluxoDto.setIdFluxo(idFluxo);
		fluxoDto = (FluxoDTO) new FluxoDao().restore(fluxoDto);
		if (fluxoDto == null)
			throw new Exception("Fluxo " + idFluxo + " não existe");
	}

	public static ExecucaoFluxo getInstancia(ExecucaoFluxo execucaoFluxo) throws Exception {
		ExecucaoFluxo novoFluxo = execucaoFluxo.getClass().newInstance();
		novoFluxo.objetoNegocioDto = execucaoFluxo.getObjetoNegocioDto();
		novoFluxo.setTransacao(execucaoFluxo.getTransacao());
		return novoFluxo;
	}

	public abstract InstanciaFluxo inicia() throws Exception;

	public abstract InstanciaFluxo inicia(String nomeFluxo, Integer idFase) throws Exception;

	public abstract InstanciaFluxo inicia(FluxoDTO fluxoDto, Integer idFase) throws Exception;

	public abstract void encerra() throws Exception;

	public abstract void executa(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, Integer idItemTrabalho, String acao, HashMap<String, Object> map) throws Exception;

	public abstract void enviaEmail(Integer idModeloEmail) throws Exception;

	public abstract void enviaEmail(String identificador) throws Exception;

	public abstract void enviaEmail(String identificador, String[] destinatarios) throws Exception;

	public abstract void reabre(String loginUsuario) throws Exception;

	public abstract void suspende(String loginUsuario) throws Exception;

	public abstract void reativa(String loginUsuario) throws Exception;

	public abstract void delega(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, Integer idItemTrabalho, String usuarioDestino, String grupoDestino) throws Exception;

	public abstract void direcionaAtendimento(String loginUsuario, ObjetoNegocioFluxoDTO objetoNegocioDto, String grupoAtendimento) throws Exception;

	public abstract void verificaSLA(ItemTrabalho itemTrabalho) throws Exception;

	@Override
	public void atribuiAcompanhamento(ItemTrabalho itemTrabalho, String usuario, String grupo) throws Exception {
		itemTrabalho.atribui(usuario, grupo, TipoAtribuicao.Acompanhamento);
	}

	public ExecucaoFluxo(ObjetoNegocioFluxoDTO objetoNegocioDto, TransactionControler tc, Usuario usuario) {
		this.objetoNegocioDto = objetoNegocioDto;
		this.fluxoDto = null;
		this.usuario = usuario;
		this.setTransacao(tc);
	}

	public ExecucaoFluxo(ObjetoNegocioFluxoDTO objetoNegocioDto, TransactionControler tc) {
		this.objetoNegocioDto = objetoNegocioDto;
		this.fluxoDto = null;
		this.setTransacao(tc);
	}

	public ExecucaoFluxo(TransactionControler tc) {
		this.objetoNegocioDto = null;
		this.fluxoDto = null;
		this.setTransacao(tc);
	}

	public ExecucaoFluxo() {
		this.fluxoDto = null;
		this.objetoNegocioDto = null;
		this.setTransacao(null);
	}

	public abstract void mapObjetoNegocio(Map<String, Object> map) throws Exception;

	public TarefaFluxoDTO recuperaTarefa(Integer idTarefa) throws Exception {
		TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
		setTransacaoDao(tarefaFluxoDao);
		TarefaFluxoDTO tarefaFluxoDto = new TarefaFluxoDTO();
		tarefaFluxoDto.setIdItemTrabalho(idTarefa);
		tarefaFluxoDto = (TarefaFluxoDTO) tarefaFluxoDao.restore(tarefaFluxoDto);
		ElementoFluxoDTO elementoDto = new ElementoFluxoDao().restore(tarefaFluxoDto.getIdElemento());
		tarefaFluxoDto.setElementoFluxoDto(elementoDto);
		return tarefaFluxoDto;
	}

	public ElementoFluxoInicioDTO getElementoInicioDto() throws Exception {
		ElementoFluxoInicioDao elementoInicioDao = new ElementoFluxoInicioDao();
		setTransacaoDao(elementoInicioDao);
		return elementoInicioDao.restoreByIdFluxo(this.getIdFluxo());
	}

	protected AtribuicaoFluxoDTO recuperaDelegacao(Integer idItemTrabalho, AtribuicaoFluxoDao atribuicaoTarefaDao) throws Exception {
		Collection<AtribuicaoFluxoDTO> colDelegacao = atribuicaoTarefaDao.findByIdItemTrabalhoAndTipo(idItemTrabalho, TipoAtribuicao.Delegacao.name());
		if (colDelegacao != null && !colDelegacao.isEmpty())
			return ((List<AtribuicaoFluxoDTO>) colDelegacao).get(0);
		else
			return null;
	}
	/**
	 * Recupera a Lista de Tarefas de acordo com o Login do Usuário.
	 *
	 * @param loginUsuario
	 * @return
	 * @throws Exception
	 */
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario) throws Exception {
		return recuperaTarefas(loginUsuario, null);
	}

	/**
	 * Recupera a Lista de Tarefas de acordo com o Login do Usuário e/ou idTarefa.
	 *
	 * @param loginUsuario
	 * @param idTarefa
	 * @return
	 * @throws Exception
	 */
	public List<TarefaFluxoDTO> recuperaTarefas(String loginUsuario, Integer idTarefa) throws Exception {
		List<TarefaFluxoDTO> tarefas = new ArrayList<TarefaFluxoDTO>();

		UsuarioGrupo usuarioGrupo = new UsuarioGrupo();
		UsuarioBpmDTO usuarioDto = usuarioGrupo.recuperaUsuario(loginUsuario);
		if (usuarioDto != null) {
			List<GrupoBpmDTO> listGrupoBpmDTO = usuarioGrupo.getGruposDoUsuario(loginUsuario);

			AtribuicaoFluxoDao atribuicaoTarefaDao = new AtribuicaoFluxoDao();
			setTransacaoDao(atribuicaoTarefaDao);
			TarefaFluxoDao tarefaFluxoDao = new TarefaFluxoDao();
			setTransacaoDao(tarefaFluxoDao);

			tarefas = tarefaFluxoDao.findDisponiveisByIdUsuarioAndGruposBpm(usuarioDto.getIdUsuario(), listGrupoBpmDTO, idTarefa);

			if (tarefas != null && !tarefas.isEmpty()) {
				for (TarefaFluxoDTO tarefaFluxoDto : tarefas) {
					tarefaFluxoDto.setSomenteAcompanhamento(tarefaFluxoDto.getTipoAtribuicao().equals(TipoAtribuicao.Acompanhamento.name()));
				}
			}

			Map<String, FluxoDTO> mapFluxos = new HashMap<>();
			Map<String, ElementoFluxoTarefaDTO> mapElementos = new HashMap<>();
			Map<String, PermissoesFluxoDTO> mapPermissoes = new HashMap<>();
			Map<String, UsuarioBpmDTO> mapUsuarios = new HashMap<>();
			Map<String, GrupoBpmDTO> mapGrupos = new HashMap<>();

			PermissoesFluxo permissoesFluxo = new PermissoesFluxo();
			ElementoFluxoDao elementoFluxoDao = new ElementoFluxoDao();
			setTransacaoDao(elementoFluxoDao);
			FluxoDao fluxoDao = new FluxoDao();
			setTransacaoDao(fluxoDao);
			FormDinamicoDao formDinamicoDao = new FormDinamicoDao();
			setTransacaoDao(formDinamicoDao);

			for (TarefaFluxoDTO tarefaFluxoDto : tarefas) {
				String executar = "N";
				String delegar = "N";
				String suspender = "N";
				String reativar = "N";
				String alterarSLA = "N";

				if (listGrupoBpmDTO != null && !tarefaFluxoDto.isSomenteAcompanhamento()) {
					if (tarefaFluxoDto.getIdFluxo() == null)
						throw new LogicException("Erro na recuperação do fluxo");

					FluxoDTO fluxoBean = mapFluxos.get("" + tarefaFluxoDto.getIdFluxo());
					if (fluxoBean == null) {
						fluxoBean = new FluxoDTO();
						fluxoBean.setIdFluxo(tarefaFluxoDto.getIdFluxo());

						// Aqui está um exemplo de uma restrição que temos no nosso framework quanto ao retorno de itens relacionados ao Objeto.
						fluxoBean = (FluxoDTO) fluxoDao.restore(fluxoBean);
						if (fluxoBean == null)
							throw new LogicException("Erro na recuperação do fluxo");
						mapFluxos.put("" + tarefaFluxoDto.getIdFluxo(), fluxoBean);
					}

					for (GrupoBpmDTO grupoBpmDto : listGrupoBpmDTO) {
						String chave = grupoBpmDto.getIdGrupo() + "|" + fluxoBean.getIdTipoFluxo();
						PermissoesFluxoDTO permissoesFluxoDto = mapPermissoes.get(chave);
						if (permissoesFluxoDto == null) {
							permissoesFluxoDto = permissoesFluxo.getPermissoesFluxo(grupoBpmDto, fluxoBean);
							if (permissoesFluxoDto == null)
								continue;
							mapPermissoes.put(chave, permissoesFluxoDto);
						}
						if (!executar.equalsIgnoreCase("S") && permissoesFluxoDto.getExecutar() != null && permissoesFluxoDto.getExecutar().equals("S"))
							executar = "S";
						if (!delegar.equalsIgnoreCase("S") && permissoesFluxoDto.getDelegar() != null && permissoesFluxoDto.getDelegar().equals("S"))
							delegar = "S";
						if (!suspender.equalsIgnoreCase("S") && permissoesFluxoDto.getSuspender() != null && permissoesFluxoDto.getSuspender().equals("S"))
							suspender = "S";
						if (!reativar.equalsIgnoreCase("S") && permissoesFluxoDto.getReativar() != null && permissoesFluxoDto.getReativar().equals("S"))
							reativar = "S";
						if (!alterarSLA.equalsIgnoreCase("S") && permissoesFluxoDto.getAlterarSLA() != null && permissoesFluxoDto.getAlterarSLA().equals("S"))
							alterarSLA = "S";
					}
				}
				tarefaFluxoDto.setExecutar(executar);
				tarefaFluxoDto.setDelegar(delegar);
				tarefaFluxoDto.setSuspender(suspender);
				tarefaFluxoDto.setReativar(reativar);
				tarefaFluxoDto.setAlterarSLA(alterarSLA);

				ElementoFluxoTarefaDTO elementoFluxoDto = mapElementos.get("" + tarefaFluxoDto.getIdElemento());
				if (elementoFluxoDto == null) {
					try {
						elementoFluxoDto = (ElementoFluxoTarefaDTO) elementoFluxoDao.restore(tarefaFluxoDto.getIdElemento());
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (elementoFluxoDto == null)
						throw new LogicException("Erro na recuperação do elemento de fluxo com ID " + tarefaFluxoDto.getIdElemento() + " na instância " + tarefaFluxoDto.getIdInstancia());
					mapElementos.put("" + tarefaFluxoDto.getIdElemento(), elementoFluxoDto);
				}

				tarefaFluxoDto.setElementoFluxoDto(elementoFluxoDto);

				// Antes não havia validação para espaços em branco, por isso a consulta era realizada mesmo quando getVisao estava em branco. A validação não é a ideal, no entanto não podemos
				// utilizar outras classes do projeto para tal.
				if (StringUtils.isNotBlank(elementoFluxoDto.getVisao())) {
					FormDinamicoDTO visaoDto = formDinamicoDao.findByIdentificador(elementoFluxoDto.getVisao().trim());
					if (visaoDto != null)
						tarefaFluxoDto.setIdVisao(visaoDto.getIdVisao());
				}

				tarefaFluxoDto.setResponsavelAtual("");
				tarefaFluxoDto.setCompartilhamento("");
				if (tarefaFluxoDto.getIdResponsavelAtual() != null) {
					UsuarioBpmDTO usuarioAtualDto = mapUsuarios.get("" + tarefaFluxoDto.getIdResponsavelAtual());
					if (usuarioAtualDto == null) {
						usuarioAtualDto = usuarioGrupo.recuperaUsuario(tarefaFluxoDto.getIdResponsavelAtual());
						if (usuarioAtualDto == null)
							continue;
						mapUsuarios.put("" + tarefaFluxoDto.getIdResponsavelAtual(), usuarioAtualDto);
					}
					tarefaFluxoDto.setResponsavelAtual(usuarioAtualDto.getNome());
				}

				AtribuicaoFluxoDTO atribuicaoDelegacaoDto = recuperaDelegacao(tarefaFluxoDto.getIdItemTrabalho(), atribuicaoTarefaDao);
				if (atribuicaoDelegacaoDto == null)
					continue;
				if (atribuicaoDelegacaoDto.getIdUsuario() != null) {
					UsuarioBpmDTO usuarioAtualDto = mapUsuarios.get("" + atribuicaoDelegacaoDto.getIdUsuario());
					if (usuarioAtualDto == null) {
						usuarioAtualDto = usuarioGrupo.recuperaUsuario(atribuicaoDelegacaoDto.getIdUsuario());
						if (usuarioAtualDto == null)
							continue;
						mapUsuarios.put("" + atribuicaoDelegacaoDto.getIdUsuario(), usuarioAtualDto);
					}
					tarefaFluxoDto.setCompartilhamento(usuarioAtualDto.getNome());
				} else {
					GrupoBpmDTO grupoAtualDto = mapGrupos.get("" + atribuicaoDelegacaoDto.getIdGrupo());
					if (grupoAtualDto == null) {
						grupoAtualDto = usuarioGrupo.recuperaGrupo(atribuicaoDelegacaoDto.getIdGrupo());
						if (grupoAtualDto == null)
							continue;
						mapGrupos.put("" + atribuicaoDelegacaoDto.getIdGrupo(), grupoAtualDto);
					}
					tarefaFluxoDto.setCompartilhamento(grupoAtualDto.getSigla());
				}
			}

		}

		return tarefas;
	}

	public Integer getIdFluxo() {
		if (fluxoDto != null)
			return fluxoDto.getIdFluxo();
		else
			return null;
	}

	public String getNomeFluxo() {
		if (fluxoDto != null)
			return fluxoDto.getNomeFluxo();
		else
			return null;
	}

	public String getDescricao() {
		if (fluxoDto != null)
			return fluxoDto.getDescricao();
		else
			return null;
	}

	public Integer getIdTipoFluxo() {
		if (fluxoDto != null)
			return fluxoDto.getIdTipoFluxo();
		else
			return null;
	}

	public String getVersao() {
		if (fluxoDto != null)
			return fluxoDto.getVersao();
		else
			return null;
	}

	public Date getDataInicio() {
		if (fluxoDto != null)
			return fluxoDto.getDataInicio();
		else
			return null;
	}

	public Date getDataFim() {
		if (fluxoDto != null)
			return fluxoDto.getDataFim();
		else
			return null;
	}

	public String[] getVariaveis() {
		if (fluxoDto != null)
			return fluxoDto.getColVariaveis();
		else
			return null;
	}

	public ObjetoNegocioFluxoDTO getObjetoNegocioDto() {
		return objetoNegocioDto;
	}

	public ExecucaoFluxoDTO getExecucaoFluxoDto() {
		return execucaoFluxoDto;
	}

	public void setObjetoNegocioDto(ObjetoNegocioFluxoDTO objetoNegocioDto) {
		this.objetoNegocioDto = objetoNegocioDto;
	}

	/**
	 * Mensagem do arquivo Properties
	 *
	 * @param key
	 * @return Mensagem Internacionalizada
	 */
	public String i18n_Message(String key) {
		if (usuario != null) {
			if (UtilI18N.internacionaliza(usuario.getLocale(), key) != null) {
				return UtilI18N.internacionaliza(usuario.getLocale(), key);
			}
			return key;
		}
		return key;
	}

}
