package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemCfgSolicitacaoServDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ItemCfgSolicitacaoServDAO extends CrudDaoDefaultImpl {


	public ItemCfgSolicitacaoServDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idsolicitacaoservico", "idsolicitacaoservico", false, false, false, false));
		listFields.add(new Field("idItemConfiguracao", "idItemConfiguracao", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		return listFields;
	}

	public ItemCfgSolicitacaoServDTO findByIdItemConfiguracaoEidsolicitacaoservico(Integer idItemConfiguracao, Integer idsolicitacaoservico) throws PersistenceException {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idItemConfiguracao", "=", idItemConfiguracao));
		condicoes.add(new Condition("idsolicitacaoservico", "=", idsolicitacaoservico));
		
		ArrayList<ItemCfgSolicitacaoServDTO> lista = (ArrayList<ItemCfgSolicitacaoServDTO>) findByCondition(condicoes, null);
		
		if(lista != null){
			return lista.get(0);
		}
		
		return null;
	}
	
	/**
	 * Verifica se existe outro item igual criado.
	 * Se existir retorna 'true', senao retorna 'false';
	 */
	public boolean verificaSeCadastrado(ItemCfgSolicitacaoServDTO itemDTO) throws PersistenceException {
		boolean estaCadastrado;		
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + getTableName() + " where idItemConfiguracao = ? and idsolicitacaoservico = ?  ");
		parametro.add(itemDTO.getIdItemConfiguracao());
		parametro.add(itemDTO.getIdSolicitacaoServico());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaCadastrado = true;
		} else {
			estaCadastrado = false;
		}
		return estaCadastrado;
	}

	public String getTableName() {
		return this.getOwner() + "ItemCfgSolicitacaoServ";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ItemCfgSolicitacaoServDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdItemConfiguracao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemConfiguracao", "=", parm));
		ordenacao.add(new Order("idItemConfiguracao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdSolicitacaoServico(Integer parm) throws PersistenceException {
		
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select sol.idSolicitacaoservico, sol.iditemconfiguracao, item.identificacao, item.iditemconfiguracaopai from itemcfgsolicitacaoserv sol ");
		sql.append("inner join itemconfiguracao item on item.iditemconfiguracao = sol.iditemconfiguracao ");
		sql.append("where sol.idsolicitacaoservico = ? ");
		parametro.add(parm);

		List retorno = this.execSQL(sql.toString(), parametro.toArray());
		
		list.add("idsolicitacaoservico");
		list.add("idItemConfiguracao");
		list.add("identificacao");
		list.add("idItemConfiguracaoPai");

		

		return listConvertion(ItemCfgSolicitacaoServDTO.class, retorno, list);
	}

	public void deleteByIdItemConfiguracao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemConfiguracao", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public void deleteByIdSolicitacaoServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idsolicitacaoservico", "=", parm));
		super.deleteByCondition(condicao);
	}
	
}
