package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaGaleriaImagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class CategoriaGaleriaImagemDao extends CrudDaoDefaultImpl {


	public CategoriaGaleriaImagemDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@SuppressWarnings("rawtypes")
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idCategoriaGaleriaImagem", "idCategoriaGaleriaImagem", true, true, false, false));
		listFields.add(new Field("nomeCategoria", "nomeCategoria", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return "CategoriaGaleriaImagem";
	}

	@SuppressWarnings("rawtypes")
	public Collection list() throws PersistenceException {
		return super.list("nomeCategoria");
	}

	@SuppressWarnings("rawtypes")
	public Class getBean() {
		return CategoriaGaleriaImagemDTO.class;
	}

	/**
	 * Retorna lista de status de usuário.
	 *
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean consultarCategoriaImagemAtivos(CategoriaGaleriaImagemDTO obj) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idcategoriagaleriaimagem from " + getTableName() + "  where  nomecategoria = ?   and dataFim is null ";

		if(obj.getIdCategoriaGaleriaImagem() != null){
			sql+=" and idcategoriagaleriaimagem <> "+ obj.getIdCategoriaGaleriaImagem();
		}

		parametro.add(obj.getNomeCategoria());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
