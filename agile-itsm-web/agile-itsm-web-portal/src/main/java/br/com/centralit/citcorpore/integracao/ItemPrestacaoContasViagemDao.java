package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author ronnie.lopes
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ItemPrestacaoContasViagemDao extends CrudDaoDefaultImpl {

	/**
     * 
     */
	public ItemPrestacaoContasViagemDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idItemPrestContasViagem", "idItemPrestContasViagem", true, true, false, false));
		listFields.add(new Field("idPrestacaoContasViagem", "idPrestacaoContasViagem", false, false, false, false));
		listFields.add(new Field("idItemDespesaViagem", "idItemDespesaViagem", false, false, false, false));
		listFields.add(new Field("idFornecedor", "idFornecedor", false, false, false, false));
		listFields.add(new Field("data", "data", false, false, false, false));
		listFields.add(new Field("nomeFornecedor", "nomeFornecedor", false, false, false, false));
		listFields.add(new Field("numeroDocumento", "numeroDocumento", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("valor", "valor", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return getOwner() + "itemPrestacaoContasViagem";
	}

	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("nomeFornecedor"));
		return super.find(obj, ordem);
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeFornecedor"));
		return super.list(list);
	}

	public Class getBean() {
		return ItemPrestacaoContasViagemDTO.class;
	}
	
	
	public Collection<ItemPrestacaoContasViagemDTO> listByPrestacaoContas(Integer idPrestacaoContasViagem) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idPrestacaoContasViagem", "=", idPrestacaoContasViagem));
		return super.findByCondition(condicao, null);
	}
	
	public void deleteByIdPrestacaoContasViagem(Integer idPrestacaoContasViagem) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idPrestacaoContasViagem", "=", idPrestacaoContasViagem));
		super.deleteByCondition(condicao);
	}
}
