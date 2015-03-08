package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class LocalidadeUnidadeDAO extends CrudDaoDefaultImpl {

	public LocalidadeUnidadeDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
				return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idlocalidadeunidade", "idLocalidadeUnidade", true, true, false, false));
		listFields.add(new Field("idlocalidade", "idLocalidade", false, false, false, false));
		listFields.add(new Field("idunidade", "idUnidade", false, false, false, false));
		listFields.add(new Field("datainicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("datafim", "dataFim", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "localidadeunidade";
	}

	@Override
	public Collection list() throws PersistenceException {
				return null;
	}

	@Override
	public Class getBean() {
				return LocalidadeUnidadeDTO.class;

	}

	public Collection<LocalidadeUnidadeDTO> listaIdLocalidades(Integer idUnidade) throws PersistenceException {
		Object[] objs = new Object[] { idUnidade };
		String sql = "SELECT  idlocalidade FROM " + getTableName() + " WHERE idunidade = ? AND datafim is null ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idLocalidade");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}
	
	public boolean verificarExistenciaDeLocalidadeEmUnidade(Integer idLocalidade) throws PersistenceException {
		Object[] objs = new Object[] { idLocalidade };
		String sql = "SELECT distinct idlocalidade FROM " + getTableName() + " WHERE idLocalidade = ? AND datafim is null ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idLocalidade");
		if (lista != null && !lista.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void updateDataFim(LocalidadeUnidadeDTO obj) throws PersistenceException {
		List parametros = new ArrayList();
		parametros.add(UtilDatas.getDataAtual());
		parametros.add(obj.getIdLocalidade());
		
		String sql = "UPDATE " + getTableName() + " SET DATAFIM = ? WHERE IDLOCALIDADE = ? ";
			
		this.execUpdate(sql, parametros.toArray());
	}


	public void deleteByIdUnidade(Integer idUnidade) throws PersistenceException {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition("idUnidade", "=", idUnidade));
		super.deleteByCondition(lstCondicao);
	}

}
