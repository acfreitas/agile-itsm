package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class OrigemAtendimentoDao extends CrudDaoDefaultImpl {

	public OrigemAtendimentoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idOrigem", "idOrigem", true, true, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "OrigemAtendimento";
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idOrigem"));
		return super.list(list);
	}

	public Class getBean() {
		return OrigemAtendimentoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	/**
	 * Retorna lista de status de usuário.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean consultarOrigemAtendimentoAtivos(OrigemAtendimentoDTO obj) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idorigem From " + getTableName() + "  where  descricao = ?   and dataFim is null ";

		if (obj.getIdOrigem() != null) {
			sql += " and idorigem <> " + obj.getIdOrigem();
		}

		parametro.add(obj.getDescricao());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	public Collection<OrigemAtendimentoDTO> listarTodosAtivos() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("descricao"));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * @author euler.ramos
	 * Busca a Origem de Atendimento filtrando pela descrição passada como parâmetro
	 * @param descricao
	 * @return
	 */
	public OrigemAtendimentoDTO buscarOrigemAtendimento(String descricao) {
		List result;
		try {
			List parametro = new ArrayList();
			List resp = new ArrayList();
			List listRetorno = new ArrayList();

			listRetorno.add("idOrigem");
			listRetorno.add("descricao");
			listRetorno.add("dataInicio");
			listRetorno.add("dataFim");

			String sql = "select idOrigem,descricao,dataInicio,dataFim From " + getTableName() + "  where  descricao = ?   and dataFim is null ";
			parametro.add(descricao);

			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(OrigemAtendimentoDTO.class, resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (OrigemAtendimentoDTO) (((result == null) || (result.size() <= 0)) ? new OrigemAtendimentoDTO() : result.get(0));
	}
	
}
