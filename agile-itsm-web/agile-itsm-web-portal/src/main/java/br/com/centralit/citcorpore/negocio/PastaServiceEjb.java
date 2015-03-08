/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.NotificacaoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoPastaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoGrupoDao;
import br.com.centralit.citcorpore.integracao.NotificacaoUsuarioDao;
import br.com.centralit.citcorpore.integracao.PastaDAO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoPastaDAO;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.PermissaoAcessoPasta;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * ServiceEJB de Pasta.
 *
 * @author valdoilo.damasceno
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class PastaServiceEjb extends CrudServiceImpl implements PastaService {

	private PastaDAO dao;

	@Override
	protected PastaDAO getDao() {
		if (dao == null) {
			dao = new PastaDAO();
		}
		return dao;
	}

	@Override
	public PastaDTO create(PastaDTO novaPasta) throws ServiceException, LogicException {
		PastaDTO pastaDto = novaPasta;
		NotificacaoDTO notificacaoDto = new NotificacaoDTO();

		NotificacaoDao notificacaoDao = new NotificacaoDao();
		NotificacaoGrupoDao notificacaoGrupoDao = new NotificacaoGrupoDao();
		NotificacaoUsuarioDao notificacaoUsuarioDao = new NotificacaoUsuarioDao();
		PerfilAcessoPastaDAO perfilAcessoPastaDao = new PerfilAcessoPastaDAO();

		TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

		try {
			this.getDao().setTransactionControler(tc);
			perfilAcessoPastaDao.setTransactionControler(tc);
			notificacaoDao.setTransactionControler(tc);
			notificacaoGrupoDao.setTransactionControler(tc);
			notificacaoUsuarioDao.setTransactionControler(tc);

			tc.start();

			if (pastaDto.getTitulo() != null && !StringUtils.isEmpty(pastaDto.getTitulo().trim()) && pastaDto.getTipoNotificacao() != null
					&& !StringUtils.isEmpty(pastaDto.getTipoNotificacao().trim())) {
				notificacaoDto.setDataInicio(UtilDatas.getDataAtual());

				notificacaoDto = this.criarNotificacao(pastaDto, tc);
			}

			if (notificacaoDto.getIdNotificacao() != null) {

				pastaDto.setIdNotificacao(notificacaoDto.getIdNotificacao());

			}

			pastaDto.setDataInicio(UtilDatas.getDataAtual());

			pastaDto = (PastaDTO) this.getDao().create(pastaDto);

			this.enviarEmailNotificacaoPasta(pastaDto, tc, "C");

			this.criarPerfisDeAcessoAPasta(pastaDto, tc);

			tc.commit();
			tc.close();
		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}

		return pastaDto;
	}

	@Override
	public void update(PastaDTO pasta) throws ServiceException, LogicException {
		PastaDTO pastaDto = pasta;
		NotificacaoDTO notificacaoDto = new NotificacaoDTO();

		PerfilAcessoPastaDAO perfilAcessoPastaDao = new PerfilAcessoPastaDAO();

		TransactionControler tc = new TransactionControlerImpl(getDao().getAliasDB());

		try {
			tc.start();

			if (pastaDto.getTitulo() != null && !StringUtils.isEmpty(pastaDto.getTitulo().trim()) && pastaDto.getTipoNotificacao() != null
					&& !StringUtils.isEmpty(pastaDto.getTipoNotificacao().trim())) {
				notificacaoDto.setDataInicio(UtilDatas.getDataAtual());

				notificacaoDto = this.criarNotificacao(pastaDto, tc);
			}

			super.update(pastaDto);

			perfilAcessoPastaDao.excluirPerfisDeAcessoPasta(pastaDto);

			this.criarPerfisDeAcessoAPasta(pastaDto, tc);

			this.enviarEmailNotificacaoPasta(pastaDto, tc, "U");

			tc.commit();
			tc.close();

		} catch (Exception e) {
			this.rollbackTransaction(tc, e);
		}
	}

	@Override
	public boolean excluirPasta(PastaDTO pastaBean) throws Exception {
		if (this.getDao().isEmUso(pastaBean)) {
			return false;
		}

		pastaBean.setDataFim(UtilDatas.getDataAtual());

		this.getDao().update(pastaBean);

		this.enviarEmailNotificacaoPasta(pastaBean, null, "D");

		return true;
	}

	/**
	 * Cria PerfilsDeAcessoPasta
	 *
	 * @param perfisDeAcesso
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	private void criarPerfisDeAcessoAPasta(PastaDTO pastaDto, TransactionControler transactionControler) throws Exception {
		PerfilAcessoPastaDAO perfilAcessoPastaDao = new PerfilAcessoPastaDAO();

		perfilAcessoPastaDao.setTransactionControler(transactionControler);

		if (pastaDto.getHerdaPermissoes() == null || !StringUtils.containsOnly(pastaDto.getHerdaPermissoes().toUpperCase().trim(), "S")) {
			if (pastaDto.getPerfisDeAcesso() != null && !pastaDto.getPerfisDeAcesso().isEmpty()) {
				for (PerfilAcessoPastaDTO perfilAcessoPastaDto : pastaDto.getPerfisDeAcesso()) {
					perfilAcessoPastaDto.setIdPasta(pastaDto.getId());
					perfilAcessoPastaDto.setDataInicio(UtilDatas.getDataAtual());

					perfilAcessoPastaDao.create(perfilAcessoPastaDto);
				}
			}
		}
	}

	private void enviarEmailNotificacaoPasta(PastaDTO pastaDto, TransactionControler transactionControler, String crud) throws Exception {
		EmpregadoDao empregadoDao = new EmpregadoDao();
		NotificacaoDao notificacaoDao = new NotificacaoDao();

		NotificacaoDTO notificacaoDTO = new NotificacaoDTO();
		Collection<EmpregadoDTO> colEmpregados = new ArrayList();

		if (transactionControler != null) {
			empregadoDao.setTransactionControler(transactionControler);
			notificacaoDao.setTransactionControler(transactionControler);
		}

		if (pastaDto.getIdNotificacao() != null && !pastaDto.getIdNotificacao().equals("")) {
			notificacaoDTO.setIdNotificacao(pastaDto.getIdNotificacao());
			notificacaoDTO = (NotificacaoDTO) notificacaoDao.restore(notificacaoDTO);

			String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
			String ID_MODELO_EMAIL_AVISAR_CRIACAO_PASTA = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_CRIACAO_PASTA, "8");
			String ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_PASTA = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_PASTA, "9");
			String ID_MODELO_EMAIL_AVISAR_EXCLUSAO_PASTA = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_EXCLUSAO_PASTA, "10");
			String ID_MODELO_EMAIL = "";

			if (ID_MODELO_EMAIL_AVISAR_CRIACAO_PASTA == null || ID_MODELO_EMAIL_AVISAR_CRIACAO_PASTA.isEmpty()) {
				ID_MODELO_EMAIL_AVISAR_CRIACAO_PASTA = "8";
			}

			if (ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_PASTA == null || ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_PASTA.isEmpty()) {
				ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_PASTA = "9";
			}

			if (ID_MODELO_EMAIL_AVISAR_EXCLUSAO_PASTA == null || ID_MODELO_EMAIL_AVISAR_EXCLUSAO_PASTA.isEmpty()) {
				ID_MODELO_EMAIL_AVISAR_EXCLUSAO_PASTA = "10";
			}

			if (crud.equals("C")) {
				if (notificacaoDTO.getTipoNotificacao().equals("T") || notificacaoDTO.getTipoNotificacao().equals("C")) {
					ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_CRIACAO_PASTA;
				}
			} else if (crud.equals("U")) {
				if (notificacaoDTO.getTipoNotificacao().equals("T") || notificacaoDTO.getTipoNotificacao().equals("A")) {
					ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_PASTA;
				}
			} else {
				if (notificacaoDTO.getTipoNotificacao().equals("T") || notificacaoDTO.getTipoNotificacao().equals("E")) {
					ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_EXCLUSAO_PASTA;
				}
			}

			if (!ID_MODELO_EMAIL.isEmpty()) {
				colEmpregados = empregadoDao.listarEmailsNotificacoesPasta(pastaDto.getId());

				if (colEmpregados != null) {
					for (EmpregadoDTO empregados : colEmpregados) {
						MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL.trim()), new IDto[] {pastaDto});

						if (empregados.getEmail() != null) {
							mensagem.envia(empregados.getEmail(), "", remetente);
						}
					}
				}
			}
		}

	}

	@Override
	public Collection<PastaDTO> consultarPastasAtivas() throws ServiceException, Exception {
		return this.getDao().consultarPastasAtivas();
	}

	@Override
	public Collection<PastaDTO> listSubPastaByPerfilAcessoUsuario(PastaDTO pastaSuperior, UsuarioDTO usuario) throws Exception {
		return this.getDao().listSubPastaByPerfilAcessoUsuario(pastaSuperior, usuario);
	}

	public Collection<PastaDTO> listPastasESubpastas(UsuarioDTO usuario) throws Exception {
		Collection<PastaDTO> listPastasESubPastas = new ArrayList<PastaDTO>();

		try {
			Collection<PastaDTO> listPastasSuperior = getDao().listPastaByPerfilAcessoUsuario(usuario);

			if (listPastasSuperior != null) {
				for (PastaDTO pastaSuperior : listPastasSuperior) {
					pastaSuperior.setNivel(0);

					listPastasESubPastas.add(pastaSuperior);

					Collection<PastaDTO> listPastaAux = listarSubPastas(pastaSuperior, 0, usuario);

					if (listPastaAux != null && listPastaAux.size() > 0) {
						listPastasESubPastas.addAll(listPastaAux);
					}
				}
			}

			return listPastasESubPastas;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection<PastaDTO> listPastasESubpastas() throws Exception {
		Collection<PastaDTO> listPastasESubPastas = new ArrayList<PastaDTO>();

		try {
			Collection<PastaDTO> listPastaSuperiorSemPai = getDao().listPastaSuperiorSemPai();
			if (listPastaSuperiorSemPai != null) {
				for (PastaDTO pastaDto : listPastaSuperiorSemPai) {
					pastaDto.setNivel(0);

					listPastasESubPastas.add(pastaDto);

					Collection<PastaDTO> listPastaAux = listarSubPastas(pastaDto, 0);

					if (listPastaAux != null && listPastaAux.size() > 0) {
						listPastasESubPastas.addAll(listPastaAux);
					}
				}
			}

			return listPastasESubPastas;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	/**
	 * Lista Subpastas da pasta informada.
	 *
	 * @param pastaSuperior
	 * @param nivel
	 * @return Collection<PastaDTO>
	 * @throws Exception
	 */
	public Collection<PastaDTO> listarSubPastas(PastaDTO pastaSuperior, Integer nivel) throws Exception {
		Collection<PastaDTO> listSubPastas = getDao().listSubPastas(pastaSuperior);

		Collection<PastaDTO> listSubpastasFinal = new ArrayList<PastaDTO>();

		if (listSubPastas != null) {
			for (PastaDTO pastaDto : listSubPastas) {
				pastaDto.setNivel(nivel + 1);

				listSubpastasFinal.add(pastaDto);

				Collection<PastaDTO> listSubSubPasta = listarSubPastas(pastaDto, pastaDto.getNivel());

				if (listSubSubPasta != null && listSubSubPasta.size() > 0) {
					listSubpastasFinal.addAll(listSubSubPasta);
				}
			}
		}

		return listSubpastasFinal;
	}

	/**
	 * Lista Subpastas de acordo com o PERFILACESSO do USUÁRIO.
	 *
	 * @param pastaSuperior
	 * @param nivel
	 * @return Collection<PastaDTO>
	 * @throws Exception
	 */
	public Collection<PastaDTO> listarSubPastas(PastaDTO pastaSuperior, Integer nivel, UsuarioDTO usuarioDto) throws Exception {
		PerfilAcessoPastaDAO perfilAcessoPastaDao = new PerfilAcessoPastaDAO();

		Collection<PastaDTO> listSubPastas = getDao().listSubPastas(pastaSuperior);

		Collection<PastaDTO> listSubpastasFinal = new ArrayList<PastaDTO>();

		if (listSubPastas != null) {

			boolean aux = false;
			for (PastaDTO subPasta : listSubPastas) {

				if (subPasta.getHerdaPermissoes() != null && StringUtils.contains(subPasta.getHerdaPermissoes().toUpperCase().trim(), "S")) {

					PastaDTO pastaDoadora = new PastaDTO();

					pastaDoadora = this.obterHerancaDePermissao(subPasta);

					PermissaoAcessoPasta permissao = perfilAcessoPastaDao.verificarPermissaoDeAcessoPasta(usuarioDto, pastaDoadora.getId());

					if (permissao != null && !PermissaoAcessoPasta.SEMPERMISSAO.equals(permissao)) {

						aux = true;

					}

				} else {

					PermissaoAcessoPasta permissao = perfilAcessoPastaDao.verificarPermissaoDeAcessoPasta(usuarioDto, subPasta.getId());

					if (permissao != null && !PermissaoAcessoPasta.SEMPERMISSAO.equals(permissao)) {

						aux = true;

					} else {

						aux = false;
					}

				}

				if (aux) {

					subPasta.setNivel(nivel + 1);

					listSubpastasFinal.add(subPasta);

					Collection<PastaDTO> listSubSubPasta = listarSubPastas(subPasta, subPasta.getNivel(), usuarioDto);

					if (listSubSubPasta != null && listSubSubPasta.size() > 0) {

						listSubpastasFinal.addAll(listSubSubPasta);
					}

				}

			}

		}

		return listSubpastasFinal;
	}

	public PastaDTO obterHerancaDePermissao(PastaDTO subPasta) throws ServiceException, LogicException {

 		if (subPasta != null && subPasta.getIdPastaPai() != null && subPasta.getHerdaPermissoes() != null
				&& StringUtils.contains(subPasta.getHerdaPermissoes().toUpperCase().trim(), "S")) {

			PastaDTO pastaPai = new PastaDTO();

			pastaPai.setId(subPasta.getIdPastaPai());

			pastaPai = (PastaDTO) this.restore(pastaPai);

			if (pastaPai.getIdPastaPai() != null && subPasta.getHerdaPermissoes() != null && StringUtils.contains(subPasta.getHerdaPermissoes().toUpperCase().trim(), "S")) {
				return obterHerancaDePermissao(pastaPai);
			} else {
				return pastaPai;
			}

		} else {
			return subPasta;
		}

	}

	@Override
	public List<PastaDTO> listPastaByUsuario(UsuarioDTO usuario) throws Exception {
		return this.getDao().listPastaByPerfilAcessoUsuario(usuario);
	}

	@Override
	public boolean verificaSeExistePasta(PastaDTO pastaDTO) throws Exception {
		return getDao().verificaSeExistePasta(pastaDTO);
	}

	/**
	 * Verifica se usuário possui acesso a pasta, considerando a sua hierarquia superior de pastas. Caso o usuário não possua acesso a alguma pasta superior, o usuário não possuirá
	 * acesso a subpasta
	 * informada.
	 *
	 * @param pastaDto
	 * @param usuarioDto
	 * @return true - se possuir acesso; false - se não possuir acesso.
	 * @throws ServiceException
	 * @throws Exception
	 */
	public boolean verificaPermissaoDeAcessoPasta(PastaDTO pastaDto, UsuarioDTO usuarioDto) throws ServiceException, Exception {
		if (pastaDto.getIdPastaPai() != null && !StringUtils.isBlank(pastaDto.getIdPastaPai().toString())) {
			boolean aux = false;
			while (pastaDto.getIdPastaPai() != null && !StringUtils.isBlank(pastaDto.getIdPastaPai().toString())) {

				if (this.verificarSeUsuarioPossuiAcessoPasta(pastaDto, usuarioDto)) {
					pastaDto = retornaPastaPai(pastaDto);

					aux = true;
				} else {
					return false;
				}
			}

			aux = this.verificarSeUsuarioPossuiAcessoPasta(pastaDto, usuarioDto);

			return aux;
		} else {
			return this.verificarSeUsuarioPossuiAcessoPasta(pastaDto, usuarioDto);
		}

	}

	/**
	 * Verifica se Usuário possui acesso a pasta informada.
	 *
	 * @param pastaDto
	 * @param usuarioDto
	 * @return true - caso possua; false - se não possuir.
	 * @throws Exception
	 * @throws ServiceException
	 */
	public boolean verificarSeUsuarioPossuiAcessoPasta(PastaDTO pastaDto, UsuarioDTO usuarioDto) throws Exception {
		return getDao().verificarSeUsuarioPossuiAcessoPasta(pastaDto, usuarioDto);
	}

	public PastaDTO retornaPastaPai(PastaDTO subPasta) throws ServiceException, Exception {
		PastaDTO pastaSuperior = new PastaDTO();

		pastaSuperior.setId(subPasta.getIdPastaPai());

		pastaSuperior = (PastaDTO) this.getDao().restore(pastaSuperior);

		return pastaSuperior;
	}

	public NotificacaoDTO criarNotificacao(PastaDTO pastaDto, TransactionControler transactionControler) throws ServiceException, Exception {
		NotificacaoDTO notificacaoDto = new NotificacaoDTO();

		NotificacaoService notificacaoService = (NotificacaoService) ServiceLocator.getInstance().getService(NotificacaoService.class, null);

		if (transactionControler == null) {
			transactionControler = new TransactionControlerImpl(getDao().getAliasDB());
			transactionControler.start();
		}

		if (pastaDto.getIdNotificacao() != null) {
			notificacaoDto.setIdNotificacao(pastaDto.getIdNotificacao());
			notificacaoDto.setListaDeUsuario(pastaDto.getListaDeUsuario());
			notificacaoDto.setListaDeGrupo(pastaDto.getListaDeGrupo());
			notificacaoDto.setTitulo(pastaDto.getTitulo());
			notificacaoDto.setTipoNotificacao(pastaDto.getTipoNotificacao());
			notificacaoDto.setOrigemNotificacao(Enumerados.OrigemNotificacao.P.name());

			notificacaoService.update(notificacaoDto, transactionControler);

			return notificacaoDto;
		} else {
			notificacaoDto.setListaDeUsuario(pastaDto.getListaDeUsuario());
			notificacaoDto.setListaDeGrupo(pastaDto.getListaDeGrupo());
			notificacaoDto.setTitulo(pastaDto.getTitulo());
			notificacaoDto.setTipoNotificacao(pastaDto.getTipoNotificacao());
			notificacaoDto.setOrigemNotificacao(Enumerados.OrigemNotificacao.P.name());
			return (NotificacaoDTO) notificacaoService.create(notificacaoDto, transactionControler);
		}
	}

	@Override
	public Collection listPastaSuperiorSemPai() throws Exception {
		return this.getDao().listPastaSuperiorSemPai();
	}

	@Override
	public Collection<PastaDTO> listPastaSuperiorFAQSemPai() throws Exception {
		Collection<PastaDTO> listPastaSuperior = this.listPastaSuperiorSemPai();

		Collection<PastaDTO> listPastaSuperiorFAQSemPai = new ArrayList<PastaDTO>();

		if (listPastaSuperior != null && !listPastaSuperior.isEmpty()) {
			for (PastaDTO pastaSuperior : listPastaSuperior) {
				if (verificarSePastaOuSubPastaPossuiFAQ(pastaSuperior)) {
					listPastaSuperiorFAQSemPai.add(pastaSuperior);
				}
			}
		}

		return listPastaSuperiorFAQSemPai;
	}

	public boolean verificarSePastaOuSubPastaPossuiFAQ(PastaDTO pastaDto) throws Exception {
		boolean aux = getDao().verificarSePastaPossuiFaq(pastaDto);

		if (aux) {
			return true;
		} else {
			Collection<PastaDTO> subPastas = listSubPastas(pastaDto);

			if (subPastas != null && !subPastas.isEmpty()) {

				for (PastaDTO subPasta : subPastas) {
					aux = verificarSePastaOuSubPastaPossuiFAQ(subPasta);

					if (aux) {
						return true;
					}
				}
				return false;
			} else {
				return false;
			}
		}
	}

	public boolean verificarSePastaOuSubPastaPossuiErroConhecido(PastaDTO pastaDto) throws Exception {
		boolean aux = getDao().verificarSePastaPossuiErroConhecido(pastaDto);

		if (aux) {
			return true;
		} else {
			Collection<PastaDTO> subPastas = listSubPastas(pastaDto);

			if (subPastas != null && !subPastas.isEmpty()) {

				for (PastaDTO subPasta : subPastas) {
					aux = verificarSePastaOuSubPastaPossuiErroConhecido(subPasta);

					if (aux) {
						return true;
					}
				}
				return false;
			} else {
				return false;
			}
		}
	}

	@Override
	public Collection<PastaDTO> listSubPastas(PastaDTO pastaSuperior) throws Exception {
		return getDao().listSubPastas(pastaSuperior);
	}

	@Override
	public Collection<PastaDTO> listSubPastasFAQ(PastaDTO pasta) throws Exception {
		Collection<PastaDTO> listSubPasta = getDao().listSubPastas(pasta);

		Collection<PastaDTO> listSubPastasFAQ = new ArrayList();

		if (listSubPasta != null && !listSubPasta.isEmpty()) {
			for (PastaDTO subPasta : listSubPasta) {
				if (verificarSePastaOuSubPastaPossuiFAQ(subPasta)) {
					listSubPastasFAQ.add(subPasta);
				}
			}
		}

		return listSubPastasFAQ;
	}

	@Override
	public Collection<PastaDTO> listPastaSuperiorErroConhecidoSemPai() throws Exception {
		Collection<PastaDTO> listPastaSuperior = this.listPastaSuperiorSemPai();

		Collection<PastaDTO> listPastaSuperiorErroConhecidoSemPai = new ArrayList<PastaDTO>();

		if (listPastaSuperior != null && !listPastaSuperior.isEmpty()) {
			for (PastaDTO pastaSuperior : listPastaSuperior) {
				if (verificarSePastaOuSubPastaPossuiErroConhecido(pastaSuperior)) {
					listPastaSuperiorErroConhecidoSemPai.add(pastaSuperior);
				}
			}
		}

		return listPastaSuperiorErroConhecidoSemPai;
	}

	@Override
	public Collection<PastaDTO> listSubPastasErroConhecido(PastaDTO pasta) throws Exception {
		Collection<PastaDTO> listSubPasta = getDao().listSubPastas(pasta);

		Collection<PastaDTO> listSubPastasErroConhecido = new ArrayList();

		if (listSubPasta != null && !listSubPasta.isEmpty()) {
			for (PastaDTO subPasta : listSubPasta) {
				if (verificarSePastaOuSubPastaPossuiErroConhecido(subPasta)) {
					listSubPastasErroConhecido.add(subPasta);
				}
			}
		}

		return listSubPastasErroConhecido;
	}

	@Override
	public PastaDTO idpastaPaiEHerdaDaPastaPai(Integer idPastaFilho) throws Exception {
		return getDao().idpastaPaiEHerdaDaPastaPai(idPastaFilho);
	}

}
