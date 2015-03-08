/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author Centralit
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ContratosGruposDAO extends CrudDaoDefaultImpl {

	public ContratosGruposDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idGrupo", "idGrupo", true, false, false, false));
		listFields.add(new Field("idContrato", "idContrato", true, false, false, false));

		return listFields;
	}

	public Collection<ContratosGruposDTO> findByIdGrupo(Integer idGrupo) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idGrupo", "=", idGrupo));

		ordenacao.add(new Order("idContrato"));

		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdGrupo(Integer idGrupo) throws PersistenceException {

		List condicao = new ArrayList();

		condicao.add(new Condition("idGrupo", "=", idGrupo));

		super.deleteByCondition(condicao);
	}

	public Collection findByGrupos(Collection<GrupoDTO> gruposEmpregado) {

		if (gruposEmpregado != null && !gruposEmpregado.isEmpty()) {

			List result = null;
			StringBuilder sql = new StringBuilder();
			ArrayList<Integer> parametros = new ArrayList<Integer>();

			sql.append("SELECT distinct idcontrato ");
			sql.append("FROM contratosgrupos ");
			sql.append("WHERE contratosgrupos.idgrupo = ?");

			boolean aux = true;
			for (GrupoDTO grupo : gruposEmpregado) {

				if (aux) {
					parametros.add(grupo.getIdGrupo());
					aux = false;
					continue;
				} else {
					sql.append(" OR contratosgrupos.idgrupo = ?");
					parametros.add(grupo.getIdGrupo());
				}
			}

			sql.append(" ORDER BY contratosgrupos.idcontrato");
			Object[] paramsFinal = parametros.size() == 0 ? null : parametros.toArray();
			List lista;
			try {
				lista = this.execSQL(sql.toString(), paramsFinal);

				List listRetorno = new ArrayList();
				listRetorno.add("idContrato");

				result = this.engine.listConvertion(ContratosGruposDTO.class, lista, listRetorno);
			} catch (PersistenceException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;

		}

		return null;
	}

	public Collection<MenuDTO> listarMenusPorPerfil(UsuarioDTO usuario, Integer idMenuPai, boolean horizontal) throws PersistenceException {
		List result = null;
		StringBuilder sql = new StringBuilder();
		ArrayList<Integer> parametros = new ArrayList<Integer>();

		sql.append("SELECT distinct m.idmenu, m.idmenupai, m.nome, m.datainicio, m.datafim, m.descricao, m.ordem, m.link, m.imagem ");
		sql.append("FROM perfilacessomenu a ");
		sql.append("JOIN menu m ON m.idmenu = a.idmenu ");
		sql.append("WHERE m.datafim IS NULL AND (a.pesquisa <> 'N' OR a.inclui <> 'N' OR a.altera <> 'N' OR a.deleta <> 'N')");

		if (idMenuPai == null) {
			sql.append("AND m.idmenupai IS NULL ");
		} else {
			sql.append("AND m.idmenupai = ? ");
			parametros.add(idMenuPai);
		}

		if (usuario != null) {

			PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();

			PerfilAcessoUsuarioDTO perfilAcessoEspecifico = new PerfilAcessoUsuarioDTO();

			perfilAcessoEspecifico = perfilAcessoUsuarioDao.obterPerfilAcessoUsuario(usuario);

			if (perfilAcessoEspecifico != null) {
				sql.append("AND (a.idperfilacesso = ? ");
				parametros.add(perfilAcessoEspecifico.getIdPerfilAcesso());

				GrupoDao grupoDao = new GrupoDao();
				PerfilAcessoGrupoDao perfilAcessoGrupoDao = new PerfilAcessoGrupoDao();

				Collection<GrupoDTO> gruposDoEmpregado = grupoDao.getGruposByIdEmpregado(usuario.getIdEmpregado());

				if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
					for (GrupoDTO grupo : gruposDoEmpregado) {
						PerfilAcessoGrupoDTO perfilAcessoGrupo = perfilAcessoGrupoDao.obterPerfilAcessoGrupo(grupo);

						if (perfilAcessoGrupo != null) {
							sql.append("OR a.idperfilacesso = ? ");
							parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
						}
					}
				}
				sql.append(")");

			} else {
				GrupoDao grupoDao = new GrupoDao();
				PerfilAcessoGrupoDao perfilAcessoGrupoDao = new PerfilAcessoGrupoDao();

				Collection<GrupoDTO> gruposDoEmpregado = grupoDao.getGruposByIdEmpregado(usuario.getIdEmpregado());

				if (gruposDoEmpregado != null && !gruposDoEmpregado.isEmpty()) {
					boolean aux = true;
					for (GrupoDTO grupo : gruposDoEmpregado) {
						PerfilAcessoGrupoDTO perfilAcessoGrupo = perfilAcessoGrupoDao.obterPerfilAcessoGrupo(grupo);

						if (perfilAcessoGrupo != null) {
							if (aux) {
								sql.append("AND (a.idperfilacesso = ? ");
								parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
								aux = false;
							} else {
								sql.append("OR a.idperfilacesso = ? ");
								parametros.add(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
							}
						}
					}
					if (!aux) {
						sql.append(")");
					}
				}
			}
		}
		sql.append("AND m.horizontal = ");
		if (horizontal) {
			sql.append(" 'S' ");
			sql.append("AND m.link IS NOT NULL");
		} else {
			sql.append(" 'N' ");
		}

		sql.append(" ORDER BY ordem, idmenupai");
		Object[] paramsFinal = parametros.size() == 0 ? null : parametros.toArray();
		List lista;
		try {
			lista = this.execSQL(sql.toString(), paramsFinal);

			List listRetorno = new ArrayList();
			listRetorno.add("idMenu");
			listRetorno.add("idMenuPai");
			listRetorno.add("nome");
			listRetorno.add("dataInicio");
			listRetorno.add("dataFim");
			listRetorno.add("descricao");
			listRetorno.add("ordem");
			listRetorno.add("link");
			listRetorno.add("imagem");

			result = this.engine.listConvertion(MenuDTO.class, lista, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Collection<ContratosGruposDTO> findByIdContrato(Integer idContrato) throws PersistenceException {

		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idContrato", "=", idContrato));
		ordenacao.add(new Order("idGrupo"));

		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdContrato(Integer idContrato) throws PersistenceException {
		List condicao = new ArrayList();

		condicao.add(new Condition("idContrato", "=", idContrato));

		super.deleteByCondition(condicao);
	}

	@Override
	public String getTableName() {
		return "contratosgrupos";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return ContratosGruposDTO.class;
	}

	public boolean hasContrato(Collection<GrupoEmpregadoDTO> gruposEmpregado, ContratoDTO contrato) {
		if (gruposEmpregado != null && !gruposEmpregado.isEmpty() && contrato != null) {
			List result = null;
			StringBuilder sql = new StringBuilder();
			ArrayList<Integer> parametros = new ArrayList<Integer>();

			sql.append("SELECT distinct idcontrato ");
			sql.append("FROM contratosgrupos ");
			sql.append("WHERE contratosgrupos.idgrupo in (");
			int i=0;
			for (GrupoEmpregadoDTO grupo : gruposEmpregado) {
					if (i > 0) 
						sql.append(",");
					sql.append(grupo.getIdGrupo());
					i++;
			}
			sql.append(") and idContrato = ?");
			sql.append(" ORDER BY contratosgrupos.idcontrato");
			parametros.add(contrato.getIdContrato());
			List lista;
			try {
				lista = this.execSQL(sql.toString(), parametros.toArray());

				List listRetorno = new ArrayList();
				listRetorno.add("idContrato");

				result = this.engine.listConvertion(ContratosGruposDTO.class, lista, listRetorno);
			} catch (PersistenceException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(result!=null && !result.isEmpty())
			return true;
		}
		return false;
	}

}
