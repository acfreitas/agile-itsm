package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AlcadaCentroResultadoDAO extends CrudDaoDefaultImpl {


	public AlcadaCentroResultadoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return super.find(obj, null);
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idalcadacentroresultado",
				"idAlcadaCentroResultado", true, true, false, false));
		listFields.add(new Field("idcentroresultado", "idCentroResultado",
				false, false, false, false));
		listFields.add(new Field("idempregado", "idEmpregado", false, false,
				false, false));
		listFields.add(new Field("idalcada", "idAlcada", false, false, false,
				false));
		listFields.add(new Field("datainicio", "dataInicio", false, false,
				false, false));
		listFields.add(new Field("datafim", "dataFim", false, false, false,
				false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return this.getOwner() + "alcadacentroresultado";
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idAlcadaCentroResultado"));

		return super.list(ordenacao);
	}

	@Override
	public Class getBean() {
		return AlcadaCentroResultadoDTO.class;
	}

	public Collection<AlcadaCentroResultadoDTO> findByIdCentroResultadoAndIdAlcada(
			Integer idCentroResultado, Integer idAlcada) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idCentroResultado", "=", idCentroResultado));
		condicao.add(new Condition("idAlcada", "=", idAlcada));
		ordenacao.add(new Order("idAlcadaCentroResultado"));

		Collection<AlcadaCentroResultadoDTO> col = super.findByCondition(
				condicao, ordenacao);
		Collection<AlcadaCentroResultadoDTO> result = new ArrayList();

		if (col != null) {
			for (AlcadaCentroResultadoDTO alcadaCentroResultadoDto : col) {
				if (alcadaCentroResultadoDto.getDataFim() == null
						|| (alcadaCentroResultadoDto.getDataFim() != null && alcadaCentroResultadoDto
								.getDataFim().compareTo(
										UtilDatas.getDataAtual()) >= 0))
					result.add(alcadaCentroResultadoDto);
			}
		}
		return result;
	}

	/**
	 * Retorna true ou falso
	 * 
	 * @param obj
	 * @return boolean
	 * @throws Exception
	 * @author thays.araujo
	 */
	public boolean verificarVinculoCentroResultado(Integer obj) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idalcadacentroresultado From " + getTableName()
				+ "  where  idcentroresultado = ?    ";

		parametro.add(obj);
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

    public Collection<AlcadaCentroResultadoDTO> findByIdCentroResultado(Integer idCentroResultado) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();

        condicao.add(new Condition("idCentroResultado", "=", idCentroResultado));
        ordenacao.add(new Order("idAlcada"));        
        ordenacao.add(new Order("dataInicio"));
        return super.findByCondition(condicao, ordenacao);
    }
    
    public void deleteByIdCentroResultado(Integer idCentroResultado) throws PersistenceException {
        List condicao = new ArrayList();
        condicao.add(new Condition("idCentroResultado", "=", idCentroResultado));
        super.deleteByCondition(condicao);
    }
}
