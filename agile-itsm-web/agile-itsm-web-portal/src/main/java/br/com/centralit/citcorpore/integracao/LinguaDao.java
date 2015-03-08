package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class LinguaDao extends CrudDaoDefaultImpl {

	public LinguaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idlingua", "idLingua", true, true, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));
		listFields.add(new Field("sigla", "sigla", false, false, false, false));
		listFields.add(new Field("datainicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("datafim", "dataFim", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "LINGUA";
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		List condicao = new ArrayList();
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("nome"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public Collection<LinguaDTO> listarAtivos() throws PersistenceException {
		List ordenacao = new ArrayList();
		List condicao = new ArrayList();
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("nome"));
		return super.findByCondition(condicao, ordenacao);
	}

	@Override
	public Class getBean() {
		return LinguaDTO.class;
	}

	public boolean consultarLinguaAtivas(LinguaDTO obj) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idlingua From " + getTableName() + "  where  nome = ?   and dataFim is null ";

		if (obj.getIdLingua() != null) {
			sql += " and idlingua <> " + obj.getIdLingua();
		}

		parametro.add(obj.getNome());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	public LinguaDTO getIdLingua(LinguaDTO obj) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select idlingua from " + getTableName() + " where datafim is null ");

		if (obj.getSigla() != null) {
			sql.append(" and  UPPER(sigla) = ? ");
			parametro.add(obj.getSigla().toUpperCase());
		}

		list = this.execSQL(sql.toString(), parametro.toArray());
		List listaRetorno = new ArrayList();
		listaRetorno.add("idLingua");
		List<LinguaDTO> getidLingua = this.listConvertion(LinguaDTO.class, list, listaRetorno);

		if (getidLingua != null && getidLingua.size() > 0) {
			return getidLingua.get(0);
		}

		return null;

	}

}
