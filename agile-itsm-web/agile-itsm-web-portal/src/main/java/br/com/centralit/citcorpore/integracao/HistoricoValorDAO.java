package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoValorDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HistoricoValorDAO extends CrudDaoDefaultImpl {

	public HistoricoValorDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("IDHISTORICOVALOR", "idHistoricoValor", true, true, false, false));
		listFields.add(new Field("IDVALOR", "idValor", false, false, false, false));
		listFields.add(new Field("IDITEMCONFIGURACAO", "idItemConfiguracao", false, false, false, false));
		listFields.add(new Field("IDBASEITEMCONFIGURACAO", "idBaseItemConfiguracao", false, false, false, false));
		listFields.add(new Field("IDCARACTERISTICA", "idCaracteristica", false, false, false, false));
		listFields.add(new Field("IDHISTORICOIC", "idHistoricoIC", false, false, false, false));
		listFields.add(new Field("VALORSTR", "valorStr", false, false, false, false));
		listFields.add(new Field("VALORLONGO", "valorLongo", false, false, false, false));
		listFields.add(new Field("VALORDECIMAL", "valorDecimal", false, false, false, false));
		listFields.add(new Field("VALORDATE", "valorDate", false, false, false, false));
		listFields.add(new Field("dataHoraAlteracao", "dataHoraAlteracao", false, false, false, false));
		listFields.add(new Field("idAutorAlteracao", "idAutorAlteracao", false, false, false, false));
		listFields.add(new Field("baseLine", "baseLine", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
				return this.getOwner() + "HISTORICOVALOR";
	}

	public List<HistoricoValorDTO> listHistoricoValorByIdHistoricoIc(Integer idHistoricoIc) throws PersistenceException {
		List listRetorno = new ArrayList();
		List parametros = new ArrayList();

		String sql = " select 	hvalor.idHistoricoValor, hvalor.idValor, hvalor.idItemConfiguracao, hvalor.idBaseItemConfiguracao, hvalor.idCaracteristica, "+
				" hvalor.idHistoricoIC, hvalor.valorStr, hvalor.valorLongo, hvalor.valorDecimal, hvalor.valorDate, hvalor.dataHoraAlteracao, 	hvalor.idAutorAlteracao, hvalor.baseLine, car.nomecaracteristica "+
				"  from historicovalor hvalor  "+
				" inner join caracteristica car on hvalor.idcaracteristica = car.idcaracteristica where idhistoricoic = ? ";

		 parametros.add(idHistoricoIc);
		 listRetorno = execSQL(sql, parametros.toArray());

		 List listFields = listFields();
		 listFields.add("nomeCaracteristica");

		return listConvertion(HistoricoValorDTO.class, listRetorno, listFields);
	}

/*	public List<HistoricoValorDTO> listHistoricoValorByIdHistoricoIc(Integer idHistoricoIc) throws PersistenceException {
		List listRetorno = new ArrayList();
		List parametros = new ArrayList();

		String sql = " select 	hvalor.idHistoricoValor, hvalor.idValor, hvalor.idItemConfiguracao, hvalor.idBaseItemConfiguracao, hvalor.idCaracteristica, "
				+ " hvalor.idHistoricoIC, hvalor.valorStr, hvalor.valorLongo, hvalor.valorDecimal, hvalor.valorDate, hvalor.dataHoraAlteracao, 	hvalor.idAutorAlteracao, hvalor.baseLine, car.nomecaracteristica " + "  from historicovalor hvalor  "
				+ " inner join caracteristica car on hvalor.idcaracteristica = car.idcaracteristica where idhistoricoic = ? ";

		parametros.add(idHistoricoIc);
		listRetorno = execSQL(sql, parametros.toArray());

		List listFields = listFields();
		listFields.add("nomeCaracteristica");

		return listConvertion(HistoricoValorDTO.class, listRetorno, listFields);
	}*/

	@Override
	public Collection list() throws PersistenceException {
				return null;
	}

	@Override
	public Class getBean() {
				return HistoricoValorDTO.class;
	}

	public List listFields(){
		List listFields = new ArrayList<>();
		listFields.add("idHistoricoValor");
		listFields.add("idValor");
		listFields.add("idItemConfiguracao");
		listFields.add("idBaseItemConfiguracao");
		listFields.add("idCaracteristica");
		listFields.add("idHistoricoIC");
		listFields.add("valorStr");
		listFields.add("valorLongo");
		listFields.add("valorDecimal");
		listFields.add("valorDate");
		listFields.add("dataHoraAlteracao");
		listFields.add("idAutorAlteracao");
		listFields.add("baseLine");

		return listFields;
	}

    public HistoricoValorDTO restoreValorByIdValor(Integer  idValor) throws PersistenceException {
		List condicao = new ArrayList();

		condicao.add(new Condition("idValor", "=", idValor));

		List ordenacao = new ArrayList();
		ordenacao.add(new Order("valorStr"));
		List resultado = (List) super.findByCondition(condicao, ordenacao);

		if (resultado != null && !resultado.isEmpty()) {
		    return (HistoricoValorDTO) resultado.get(0);
		} else {
		    return null;
		}
    }

	public Collection findByIdHitoricoIC(Integer idHistoricoIc) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("IDHISTORICOIC", "=", idHistoricoIc));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdHitoricoIC(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("IDHISTORICOIC", "=", parm));
		super.deleteByCondition(condicao);
	}


}
