package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FormaPagamentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes","unchecked"})
public class FormaPagamentoDAO extends CrudDaoDefaultImpl{

	public FormaPagamentoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@Override
	public Collection find(IDto obj) throws PersistenceException {

		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDFORMAPAGAMENTO", "idFormaPagamento", true, true, false, false));
		listFields.add(new Field("NOMEFORMAPAGAMENTO", "nomeFormaPagamento", false, false, false, false));
		listFields.add(new Field("SITUACAO", "situacao", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "FORMAPAGAMENTO";
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		List condicao = new ArrayList();
		condicao.add(new Condition("situacao", "like", "A"));
		ordenacao.add(new Order("nomeFormaPagamento"));
		return super.findByCondition(condicao,ordenacao);
	}

	@Override
	public Class getBean() {
		return FormaPagamentoDTO.class;
	}

	/**
	 * Retorna lista de status de usuário.
	 * 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public boolean consultarFormaPagamento(FormaPagamentoDTO obj) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idFormaPagamento from " + getTableName() + " where nomeFormaPagamento = ? and situacao like 'A'";
		
		if(obj.getIdFormaPagamento() != null){
			sql+=" and idFormaPagamento <> "+ obj.getIdFormaPagamento();
		}
				
		parametro.add(obj.getNomeFormaPagamento());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
}
