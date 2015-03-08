package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class FluxoServicoDao extends CrudDaoDefaultImpl {

	public FluxoServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	private List getColunasRestoreAll() {
		List listRetorno = new ArrayList();
		listRetorno.add("idFluxoServico");
		listRetorno.add("idServicoContrato");
		listRetorno.add("idTipoFluxo");
		listRetorno.add("idFase");
		listRetorno.add("principal");
		listRetorno.add("deleted");
		return listRetorno;
	}

	private String getSQLRestoreAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT idFluxoServico, idServicoContrato, idTipoFluxo, idFase, principal, deleted FROM FluxoServico ");
		sql.append(" WHERE (deleted is null or UCASE(deleted) = 'N') ");

		return sql.toString();
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idFluxoServico", "idFluxoServico", true, true, false, false));
		listFields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
		listFields.add(new Field("idTipoFluxo", "idTipoFluxo", false, false, false, false));
		listFields.add(new Field("idFase", "idFase", false, false, false, false));
		listFields.add(new Field("principal", "principal", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "FluxoServico";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return FluxoServicoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdServicoContrato(Integer parm) throws PersistenceException {
		List parametro = new ArrayList();
		parametro.add(parm);

		String sql = getSQLRestoreAll();
		sql += "  AND idServicoContrato = ? " + "ORDER BY idTipoFluxo ";

		List lista = this.execSQL(sql.toString(), parametro.toArray());

		return this.engine.listConvertion(FluxoServicoDTO.class, lista, getColunasRestoreAll());
	}

	public Collection findByIdFluxoServico(Integer parm) throws PersistenceException {
		List parametro = new ArrayList();
		parametro.add(parm);

		String sql = getSQLRestoreAll();
		sql += "  AND idFluxoServico = ? " + "ORDER BY idTipoFluxo ";

		List lista = this.execSQL(sql.toString(), parametro.toArray());

		return this.engine.listConvertion(FluxoServicoDTO.class, lista, getColunasRestoreAll());
	}

	public Collection findByIdServicoContratoAndIdFase(Integer idServicoContrato, Integer idFase) throws PersistenceException {
		List parametro = new ArrayList();
		parametro.add(idServicoContrato);
		parametro.add(idFase);

		String sql = getSQLRestoreAll();
		sql += "  AND idServicoContrato = ? " + "  AND idFase = ? " + "ORDER BY idTipoFluxo ";

		List lista = this.execSQL(sql.toString(), parametro.toArray());

		return this.engine.listConvertion(FluxoServicoDTO.class, lista, getColunasRestoreAll());
	}

	public FluxoServicoDTO findByIdServicoContratoAndIdTipoFluxo(Integer idServicoContrato, Integer idTipoFluxo) throws PersistenceException {
		List parametro = new ArrayList();
		parametro.add(idServicoContrato);
		parametro.add(idTipoFluxo);

		String sql = getSQLRestoreAll();
		sql += "  AND idServicoContrato = ? " + "  AND idTipoFluxo = ? " + "ORDER BY idTipoFluxo ";

		List lista = this.execSQL(sql.toString(), parametro.toArray());

		FluxoServicoDTO fluxoServicoDTO = null;
		List<FluxoServicoDTO> collection = this.engine.listConvertion(FluxoServicoDTO.class, lista, getColunasRestoreAll());
		if (collection != null && !collection.isEmpty()) {
			fluxoServicoDTO = collection.get(0);
		}

		return fluxoServicoDTO;
	}

	/**
	 * Retorna FluxoServicoDTO Principal Ativo de acordo com o idServicoContrato informado.
	 * 
	 * @param idServicoContrato
	 * @return FluxoServicoDTO
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public FluxoServicoDTO findPrincipalByIdServicoContrato(Integer idServicoContrato) throws PersistenceException {
		List parametro = new ArrayList();
		parametro.add(idServicoContrato);

		String sql = getSQLRestoreAll();
		sql += "  AND principal = 'S'" + "  AND idServicoContrato = ? " + "ORDER BY idTipoFluxo ";

		List lista = this.execSQL(sql.toString(), parametro.toArray());

		if (lista != null && !lista.isEmpty()) {
			List listaResult = this.engine.listConvertion(FluxoServicoDTO.class, lista, getColunasRestoreAll());
			return (FluxoServicoDTO) listaResult.get(0);
		} else {
			return null;
		}
	}

	public boolean validarFluxoServico(FluxoServicoDTO fluxoServicoDTO) {
		try {
			List parametro = new ArrayList();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT idFluxoServico, idServicoContrato, idTipoFluxo, idFase, principal, deleted FROM FluxoServico");
			sql.append(" WHERE (deleted is null or UCASE(deleted) = 'N') AND idfase = ? AND idTipoFluxo = ? AND idServicoContrato = ? AND principal like '" + fluxoServicoDTO.getPrincipal() + "'");

			parametro.add(fluxoServicoDTO.getIdFase());
			parametro.add(fluxoServicoDTO.getIdTipoFluxo());
			parametro.add(fluxoServicoDTO.getIdServicoContrato());

			List lista = this.execSQL(sql.toString(), parametro.toArray());

			if (lista != null && !lista.isEmpty()) {
				return false;
			} else {
				return true;
			}
		} catch (PersistenceException e) {
			e.printStackTrace();
			return false;
		}
	}

	public void deleteByIdServicoContrato(Integer idServicocContrato) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idServicoContrato", "=", idServicocContrato));
		FluxoServicoDTO fluxoServicoDTO = new FluxoServicoDTO();
		fluxoServicoDTO.setDeleted("y");
		super.updateNotNullByCondition(fluxoServicoDTO, condicao);
		// super.deleteByCondition(condicao);
	}

}
