package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

;

@SuppressWarnings("serial")
public class GlosaOSDao extends CrudDaoDefaultImpl {
	public GlosaOSDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idGlosaOS", "idGlosaOS", true, true, false, false));
		listFields.add(new Field("idOs", "idOs", false, false, false, false));
		listFields.add(new Field("dataCriacao", "dataCriacao", false, false, false, false));
		listFields.add(new Field("dataUltModificacao", "dataUltModificacao", false, false, false, false));
		listFields.add(new Field("descricaoGlosa", "descricaoGlosa", false, false, false, false));
		listFields.add(new Field("ocorrencias", "ocorrencias", false, false, false, false));
		listFields.add(new Field("percAplicado", "percAplicado", false, false, false, false));
		listFields.add(new Field("custoGlosa", "custoGlosa", false, false, false, false));
		listFields.add(new Field("numeroOcorrencias", "numeroOcorrencias", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "GlosaOS";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return GlosaOSDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdOs(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idOs", "=", parm));
		ordenacao.add(new Order("idGlosaOS"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdOs(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idOs", "=", parm));
		super.deleteByCondition(condicao);
	}

	public void deleteByOsNotIn(Integer idOs, String notIn) throws PersistenceException {
		String sql = "DELETE FROM " + this.getTableName() + " WHERE idOs = ? AND idGlosaOS NOT IN (?)";
		super.execUpdate(sql, new Object[] { idOs,  notIn });
	}

	public Double retornarCustoGlosaOSByIdOs(Integer idOs) throws PersistenceException {
		String sql = "select sum(custoglosa) from glosaos where idos = ?";
		List dados = this.execSQL(sql, new Object[] { idOs });
		List fields = new ArrayList();
		fields.add("custoGlosa");

		Collection<GlosaOSDTO> listaDeGlosaOS = this.listConvertion(getBean(), dados, fields);

		if (listaDeGlosaOS != null && !listaDeGlosaOS.isEmpty()) {
			for (GlosaOSDTO glosa : listaDeGlosaOS) {
				return glosa.getCustoGlosa();
			}
			return null;
		} else {
			return null;
		}

	}

	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public Collection<GlosaOSDTO> listaDeGlosas(Integer idOs) throws PersistenceException {
		List listRetorno = new ArrayList();
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		sql.append("select idglosaos, descricaoglosa,ocorrencias,percaplicado,custoglosa,numeroOcorrencias FROM " + getTableName() + " where idos = ?");
		parametro.add(idOs);
		
		List lista = new ArrayList();
		lista = this.execSQL(sql.toString(), parametro.toArray());
		listRetorno.add("idGlosaOS");
		listRetorno.add("descricaoGlosa");
		listRetorno.add("ocorrencias");
		listRetorno.add("percAplicado");
		listRetorno.add("custoGlosa");
		listRetorno.add("numeroOcorrencias");
		if (lista != null && !lista.isEmpty()) {
			List listaDeGlosas = this.engine.listConvertion(GlosaOSDTO.class, lista, listRetorno);
			return listaDeGlosas;

		} else {

			return new ArrayList();
		}

	}

}
