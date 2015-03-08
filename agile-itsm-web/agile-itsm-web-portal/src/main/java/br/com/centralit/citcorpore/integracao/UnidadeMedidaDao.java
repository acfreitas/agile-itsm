package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class UnidadeMedidaDao extends CrudDaoDefaultImpl {
	public UnidadeMedidaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idUnidadeMedida", "idUnidadeMedida", true, true, false, false));
		listFields.add(new Field("nomeUnidadeMedida", "nomeUnidadeMedida", false, false, false, false));
		listFields.add(new Field("siglaUnidadeMedida", "siglaUnidadeMedida", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "UnidadeMedida";
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeUnidadeMedida"));
		return super.list(list);
	}

	public Class getBean() {
		return UnidadeMedidaDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	/*
	 * public Collection consultarUnidadesMedidasAtivas(UnidadeMedidaDTO unidadeMedidaDTO) throws PersistenceException { List condicao = new ArrayList(); List ordenacao = new ArrayList(); condicao.add(new
	 * Condition("nomeUnidadeMedida", "=", unidadeMedidaDTO.getNomeUnidadeMedida())); condicao.add(new Condition("situacao", "<>", "I")); ordenacao.add(new Order("nomeUnidadeMedida")); return
	 * super.findByCondition(condicao, ordenacao); }
	 */

	/**
	 * Retorna true ou falso caso nomeUnidadeMedida ja exista no banco de dados
	 * @param marcaDto
	 * @return boolean
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean consultarUnidadesMedidas(UnidadeMedidaDTO unidadeMedidaDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select idunidademedida from " + getTableName() + "  where  nomeUnidadeMedida = ?");

		parametro.add(unidadeMedidaDTO.getNomeUnidadeMedida());

		if (unidadeMedidaDTO.getIdUnidadeMedida() != null) {
			sql.append("and idunidademedida <> ?");
			parametro.add(unidadeMedidaDTO.getIdUnidadeMedida());
		}

		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
