/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoPastaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.PastaService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.PermissaoAcessoPasta;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;

/**
 * DAO de PerfilAcessoPasta.
 *
 * @author valdoilo.damasceno
 *
 */
public class PerfilAcessoPastaDAO extends CrudDaoDefaultImpl {

	public PerfilAcessoPastaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection<PerfilAcessoPastaDTO> find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDPERFIL", "idPerfilAcesso", true, false, false, false));
		listFields.add(new Field("IDPASTA", "idPasta", true, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		listFields.add(new Field("APROVABASECONHECIMENTO", "aprovaBaseConhecimento", false, false, false, false));
		listFields.add(new Field("PERMITELEITURA", "permiteLeitura", false, false, false, false));
		listFields.add(new Field("PERMITELEITURAGRAVACAO", "permiteLeituraGravacao", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "PERFILACESSOPASTA";
	}

	@Override
	public Collection list() throws PersistenceException {
		List<Order> ordenacao = new ArrayList<>();
		ordenacao.add(new Order("IDPERFIL"));
		return super.list(ordenacao);
	}

	@Override
	public Class<PerfilAcessoPastaDTO> getBean() {
		return PerfilAcessoPastaDTO.class;
	}

	/**
	 * Consulta Perfil de Acesso Pasta pelo idPasta e idPerfilPasta.
	 *
	 * @param idPasta
	 * @param idPerfilAcesso
	 * @return PerfilAcessoPastaDTO
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public PerfilAcessoPastaDTO findByIdPastaAndIdPerfilAcesso(Integer idPasta, Integer idPerfilAcesso) throws Exception {
		List<Order> ordenacao = new ArrayList<>();
		List<Condition> condicao = new ArrayList<>();

		condicao.add(new Condition("idPasta", "=", idPasta));
		condicao.add(new Condition("idPerfilAcesso", "=", idPerfilAcesso));
		ordenacao.add(new Order("idPerfilAcesso"));

		List list = (List) this.findByCondition(condicao, ordenacao);

		if (list != null && !list.isEmpty()) {
			return (PerfilAcessoPastaDTO) list.get(0);
		}

		return null;
	}

	/**
	 * Consulta Perfil de Acesso Pasta pelo idPasta .
	 *
	 * @param idPasta
	 * @param idPerfilAcesso
	 * @return PerfilAcessoPastaDTO
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<PerfilAcessoPastaDTO> findByIdPasta(Integer idPasta) throws Exception {
		List<String> ordenacao = new ArrayList<>();
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct idPerfil from perfilacessopasta where idpasta = ").append(idPasta);
		List list = execSQL(sql.toString(), null);
		ordenacao.add("idPerfilAcesso");
		if (list != null && !list.isEmpty()) {
			List listaIdPerfilAcesso = this.engine.listConvertion(PerfilAcessoPastaDTO.class, list, ordenacao);
			return listaIdPerfilAcesso;
		}
		return null;
	}

	/**
	 * Retorna PerfilAcessoSituacaoPasta Ativos por idPerfilAcesso.
	 *
	 * @param idPerfilAcesso
	 * @return
	 * @throws Exception
	 */
	public Collection findByIdPerfil(Integer idPerfilAcesso) throws Exception {
		List<Condition> condicao = new ArrayList<>();
		List<Order> ordenacao = new ArrayList<>();
		condicao.add(new Condition("idPerfilAcesso", "=", idPerfilAcesso));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("idPerfilAcesso"));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Exclui Perfis de Acesso à Pasta.
	 *
	 * @param pastaBean
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void excluirPerfisDeAcessoPasta(PastaDTO pastaBean) throws Exception {
		List<Condition> condicao = new ArrayList<>();
		condicao.add(new Condition("idPasta", "=", pastaBean.getId()));
		this.deleteByCondition(condicao);
	}

	/**
	 * Busca somente pastas com perfil de acesso onde usuario logado tenha acesso... Funcionalidade #340
	 *
	 * @param usuario
	 * @param idgrupo
	 * @return
	 * @throws Exception
	 */
	public List<PerfilAcessoPastaDTO> validaPasta(UsuarioDTO usuario) throws Exception {
		StringBuilder sql = new StringBuilder();
		List<Integer> parametros = new ArrayList<>();

		List<String> fields = new ArrayList<>();
		fields.add("idPasta");
		fields.add("nomePasta");

		if (usuario.getIdPerfilAcessoUsuario() != null) {
			sql.append("SELECT pasta.idpasta AS idpasta, pasta.nome AS nomePasta FROM ").append(getTableName()).append(" perfilAcessPast ");
			sql.append("JOIN pasta pasta ON pasta.idpasta = perfilAcessPast.idpasta WHERE perfilAcessPast.idperfil = ? AND pasta.datafim IS NULL ORDER BY pasta.nome");
			parametros.add(usuario.getIdPerfilAcessoUsuario());
		} else {
			sql.append("SELECT past.idpasta AS idPasta, past.nome AS nomePasta FROM perfilacessogrupo pag JOIN perfilacessopasta pap ON pag.idperfil = pap.idperfil ");
			sql.append("JOIN pasta past ON past.idpasta = pap.idpasta WHERE pag.idgrupo = ? AND past.datafim IS NULL ");
			parametros.add(usuario.getIdGrupo());
		}

		return this.engine.listConvertion(this.getBean(), execSQL(sql.toString(), parametros.toArray()), fields);
	}

	/**
	 * Verifica se Usuário Pode Aprovar Base de Conhecimento da pasta Selecionada.
	 *
	 * @param usuario
	 * @param idPasta
	 * @return Boolean
	 * @author valdoilo.damasceno
	 * @throws Exception
	 */
	public boolean usuarioAprovaBaseConhecimentoParaPastaSelecionada(UsuarioDTO usuario, Integer idPasta) throws Exception {
		List<String> retorno = new ArrayList<>();

		retorno.add("aprovaBaseConhecimento");

		if (getPerfilAcessoUsuarioDAO().verificarSeUsuarioPossuiPerfilAcessoEspecifico(usuario)) {
			StringBuilder sql = new StringBuilder();
			List<Integer> parametros = new ArrayList<>();

			sql.append("SELECT perfilacessopasta.aprovabaseconhecimento FROM usuario ");
			sql.append("INNER JOIN  perfilacessousuario ON usuario.idusuario = perfilacessousuario.idusuario ");
			sql.append("INNER JOIN perfilacesso ON perfilacesso.idperfil = perfilacessousuario.idperfil ");
			sql.append("INNER JOIN perfilacessopasta ON perfilacesso.idperfil = perfilacessopasta.idperfil ");
			sql.append("WHERE usuario.idusuario = ? AND perfilacessopasta.idpasta = ?");

			parametros.add(usuario.getIdUsuario());
			parametros.add(idPasta);

			List list = execSQL(sql.toString(), parametros.toArray());

			if (aprovaBaseConhecimento(retorno, list)) {
				return true;
			} else if (verificarSeGrupoAprovaBaseConhecimento(usuario, idPasta, retorno)) {
				return true;
			} else {
				return verificaPastaFilhoHerdaPermissaoPastaPai(usuario, idPasta, retorno);
			}
		}
		return verificarSeGrupoAprovaBaseConhecimento(usuario, idPasta, retorno);
	}

	/**
	 * @param usuario
	 * @param idPasta
	 * @param sql
	 * @param parametros
	 * @param retorno
	 * @return
	 * @throws PersistenceException
	 * @throws Exception
	 */
	private Boolean verificarSeGrupoAprovaBaseConhecimento(UsuarioDTO usuario, Integer idPasta, List retorno) throws PersistenceException, Exception {
		if (usuario == null) {
			return false;
		}

		StringBuilder sql = new StringBuilder();
		List<Integer> parametros = new ArrayList<>();

		sql.append("SELECT perfilacessopasta.aprovabaseconhecimento FROM grupo ");
		sql.append("INNER JOIN perfilacessogrupo ON grupo.idgrupo = perfilacessogrupo.idgrupo ");
		sql.append("INNER JOIN perfilacesso ON perfilacesso.idperfil = perfilacessogrupo.idperfil ");
		sql.append("INNER JOIN perfilacessopasta ON perfilacesso.idperfil = perfilacessopasta.idperfil ");
		sql.append("WHERE perfilacessopasta.idpasta = ? ");

		parametros.add(idPasta);

		Collection<GrupoEmpregadoDTO> listGrupoEmpregado = new ArrayList<GrupoEmpregadoDTO>();

		listGrupoEmpregado = getGrupoEmpregadoService().findByIdEmpregado(usuario.getIdEmpregado());

		if (listGrupoEmpregado != null && !listGrupoEmpregado.isEmpty()) {

			boolean aux = true;
			for (GrupoEmpregadoDTO grupoEmpregado : listGrupoEmpregado) {
				if (aux) {
					sql.append(" AND (grupo.idgrupo = ? ");
					parametros.add(grupoEmpregado.getIdGrupo());
					aux = false;
				} else {
					sql.append(" OR grupo.idgrupo = ? ");
					parametros.add(grupoEmpregado.getIdGrupo());
				}
			}
			sql.append(" )");
		}

		List list = execSQL(sql.toString(), parametros.toArray());

		if (list != null && !list.isEmpty()) {
			Collection<PerfilAcessoPastaDTO> listPerfilAcessoPasta = this.engine.listConvertion(this.getBean(), list, retorno);

			for (PerfilAcessoPastaDTO perfilAcessoPastaDto : listPerfilAcessoPasta) {
				if (perfilAcessoPastaDto.getAprovaBaseConhecimento().equalsIgnoreCase("s")) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Verifica se Retorno aprovaBaseConhecimento é igual a "S".
	 *
	 * @param retorno
	 * @param list
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	private boolean aprovaBaseConhecimento(List retorno, List list) throws Exception {
		if (list != null && !list.isEmpty()) {
			PerfilAcessoPastaDTO perfilAcessoPasta = (PerfilAcessoPastaDTO) this.engine.listConvertion(this.getBean(), list, retorno).get(0);

			if (perfilAcessoPasta.getAprovaBaseConhecimento().equalsIgnoreCase("s")) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * Obtém Permissão de Acesso.
	 *
	 * @param retorno
	 * @param listPerfilAcessoPastaAux
	 * @return SEMPERMISSAO, LEITURA ou LEITURAGRAVACAO.
	 * @throws Exception
	 */
	private PermissaoAcessoPasta possuiPermissaoLeituraGravacao(List retorno, List listPerfilAcessoPastaAux) throws Exception {
		if (listPerfilAcessoPastaAux != null && !listPerfilAcessoPastaAux.isEmpty()) {
			Collection<PerfilAcessoPastaDTO> listPerfilAcessoPasta = this.engine.listConvertion(this.getBean(), listPerfilAcessoPastaAux, retorno);

			for (PerfilAcessoPastaDTO perfilAcessoPasta : listPerfilAcessoPasta) {
				if (perfilAcessoPasta.getPermiteLeitura() != null && perfilAcessoPasta.getPermiteLeitura().equalsIgnoreCase("s")) {
					return Enumerados.PermissaoAcessoPasta.LEITURA;
				} else {
					return Enumerados.PermissaoAcessoPasta.LEITURAGRAVACAO;
				}
			}
		}
		return null;
	}

	/**
	 * Verifica as Permissões de Acesso do Usuário a Pasta informada.
	 *
	 * @param usuario
	 * @param idPasta
	 * @return true - Possui permissão de Leitura/Gravação; false - Possui permissõa de Leitura;
	 * @throws PersistenceException
	 * @throws Exception
	 */
	public PermissaoAcessoPasta verificarPermissaoDeAcessoPasta(UsuarioDTO usuario, Integer idPasta) throws PersistenceException, Exception {
		List<String> retorno = new ArrayList<>();

		retorno.add("permiteLeitura");
		retorno.add("permiteLeituraGravacao");

		if (getPerfilAcessoUsuarioDAO().verificarSeUsuarioPossuiPerfilAcessoEspecifico(usuario)) {
			StringBuilder sql = new StringBuilder();
			List<Integer> parametros = new ArrayList<>();

			sql.append("SELECT perfilacessopasta.permiteleitura, perfilacessopasta.permiteleituragravacao ");
			sql.append("FROM usuario ");
			sql.append("INNER JOIN  perfilacessousuario ON usuario.idusuario = perfilacessousuario.idusuario ");
			sql.append("INNER JOIN perfilacesso ON perfilacesso.idperfil = perfilacessousuario.idperfil ");
			sql.append("INNER JOIN perfilacessopasta ON perfilacesso.idperfil = perfilacessopasta.idperfil ");
			sql.append("WHERE usuario.idusuario = ? AND perfilacessopasta.idpasta = ?");

			parametros.add(usuario.getIdUsuario());
			parametros.add(idPasta);

			List list = execSQL(sql.toString(), parametros.toArray());

			PermissaoAcessoPasta permissao = possuiPermissaoLeituraGravacao(retorno, list);

			if (permissao != null) {
				return permissao;
			} else {
				return verificarSeGrupoPossuiPermissaoLeituraGravacao(usuario, idPasta, retorno);
			}
		}
		return verificarSeGrupoPossuiPermissaoLeituraGravacao(usuario, idPasta, retorno);
	}

	/**
	 * Verifica se Grupo informado possui Permissão Leitura/Gravação.
	 *
	 * @param usuario
	 * @param idPasta
	 * @param retorno
	 * @return true - Grupo possui permissão Leitura/Gravação; false - Grupo possui permissão Leitura;
	 * @throws PersistenceException
	 * @throws Exception
	 */
	private PermissaoAcessoPasta verificarSeGrupoPossuiPermissaoLeituraGravacao(UsuarioDTO usuario, Integer idPasta, List retorno) throws PersistenceException, Exception {
		if (usuario == null) {
			return null;
		}

		Collection<GrupoEmpregadoDTO> listGrupoEmpregado = new ArrayList<GrupoEmpregadoDTO>();

		listGrupoEmpregado = getGrupoEmpregadoService().findByIdEmpregado(usuario.getIdEmpregado());

		if (listGrupoEmpregado != null && !listGrupoEmpregado.isEmpty()) {
			StringBuilder sql = new StringBuilder();
			List<Integer> parametros = new ArrayList<>();

			sql.append("SELECT perfilacessopasta.permiteleitura, perfilacessopasta.permiteleituragravacao ");
			sql.append("FROM grupo ");
			sql.append("INNER JOIN perfilacessogrupo ON grupo.idgrupo = perfilacessogrupo.idgrupo ");
			sql.append("INNER JOIN perfilacesso ON perfilacesso.idperfil = perfilacessogrupo.idperfil ");
			sql.append("INNER JOIN perfilacessopasta ON perfilacesso.idperfil = perfilacessopasta.idperfil ");
			sql.append("WHERE ");

			boolean aux = true;
			for (GrupoEmpregadoDTO grupoEmpregado : listGrupoEmpregado) {
				if (aux) {
					sql.append(" (grupo.idgrupo = ? ");
					parametros.add(grupoEmpregado.getIdGrupo());
					aux = false;
				} else {
					sql.append(" OR grupo.idgrupo = ? ");
					parametros.add(grupoEmpregado.getIdGrupo());
				}
			}

			sql.append(" ) and perfilacessopasta.idpasta = ? ");

			parametros.add(idPasta);

			List list = execSQL(sql.toString(), parametros.toArray());

			return possuiPermissaoLeituraGravacao(retorno, list);
		}

		return null;
	}

	/**
	 * Lista PERFILACESSOPASTADTO ATIVOS.
	 *
	 * @param idPasta
	 * @return
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public Collection<PerfilAcessoPastaDTO> listByIdPasta(Integer idPasta) throws Exception {
		List<String> listRetorno = new ArrayList<>();
		List<Integer> parametros = new ArrayList<>();

		StringBuilder sql = new StringBuilder();

		listRetorno.add("idPerfilAcesso");
		listRetorno.add("idPasta");
		listRetorno.add("aprovaBaseConhecimento");
		listRetorno.add("permiteLeitura");
		listRetorno.add("permiteLeituraGravacao");

		sql.append("select idPerfil, idpasta, aprovabaseconhecimento, permiteleitura, permiteleituragravacao from perfilacessopasta where idpasta = ? and datafim is null");
		parametros.add(idPasta);

		List list = execSQL(sql.toString(), parametros.toArray());

		if (list != null && !list.isEmpty()) {
			List listaIdPerfilAcesso = this.engine.listConvertion(PerfilAcessoPastaDTO.class, list, listRetorno);
			return listaIdPerfilAcesso;
		}

		return null;
	}

	/**
	 * Verifica se Pasta filho herda permissões da pasta pai.
	 *
	 * @param usuario
	 * @param idPasta
	 * @param retorno
	 * @return
	 * @throws Exception
	 * @author mario.haysaki
	 */
	public boolean verificaPastaFilhoHerdaPermissaoPastaPai(UsuarioDTO usuario, Integer idPasta, List retorno) throws Exception {
		if (usuario == null) {
			return false;
		}
		PastaDTO pastaDto = new PastaDTO();
		if (idPasta != null) {
			pastaDto = getPastaService().idpastaPaiEHerdaDaPastaPai(idPasta);
			if (pastaDto != null) {
				if (pastaDto.getIdPastaPai() != null && pastaDto.getHerdaPermissoes() != null && pastaDto.getHerdaPermissoes().equalsIgnoreCase("s")) {
					return verificarSeGrupoAprovaBaseConhecimento(usuario, pastaDto.getIdPastaPai(), retorno);
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return false;
	}

	private PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao;

	private PerfilAcessoUsuarioDAO getPerfilAcessoUsuarioDAO() {
		if (perfilAcessoUsuarioDao == null) {
			perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();
		}
		return perfilAcessoUsuarioDao;
	}

	private GrupoEmpregadoService grupoEmpregadoService;

	private GrupoEmpregadoService getGrupoEmpregadoService() throws Exception {
		if (grupoEmpregadoService == null) {
			grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		}
		return grupoEmpregadoService;
	}

	private PastaService pastaService;

	private PastaService getPastaService() throws Exception {
		if (pastaService == null) {
			pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
		}
		return pastaService;
	}

}
