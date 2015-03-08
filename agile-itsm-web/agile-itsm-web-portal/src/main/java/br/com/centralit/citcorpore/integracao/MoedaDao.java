package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MoedaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.InvalidTransactionControler;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MoedaDao extends CrudDaoDefaultImpl {

	public MoedaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public MoedaDao(TransactionControler tc, Usuario usuario)
			throws InvalidTransactionControler {
		super(tc, usuario);
		
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idMoeda", "idMoeda", true, true, false, false));
		listFields.add(new Field("nomeMoeda", "nomeMoeda", false, false, false, false));
		listFields.add(new Field("usarCotacao", "usarCotacao", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return "MOEDAS";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeMoeda"));
        return super.list(list);	}

	@Override
	public Class getBean() {
		return MoedaDTO.class;
	}
	
	/**
	 * Verifica se existe uma moeda com o mesmo nome já cadastrada
	 * Se existir retorna 'true', se nao existir retorna 'false';
	 * 
	 * @param moedaDTO
	 * @return estaCadastrato
	 * @throws PersistenceException
	 */
	public boolean verificaSeCadastrado(MoedaDTO moedaDTO) throws PersistenceException {
		boolean estaCadastrato;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select idMoeda from " + getTableName() + "  where  nomeMoeda = ? and datafim is null  ");
		parametro.add(moedaDTO.getNomeMoeda());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaCadastrato = true;
		} else {
			estaCadastrato = false;
		}
		return estaCadastrato;
	}


	/**
	 * Verifica se existe relacionamento de moeda com outras tabelas.
	 * Se existir retorna 'true', se nao existir retorna 'false';
	 * 
	 * @param moedaDTO
	 * @throws PersistenceException
	 */
	public boolean verificaRelacionamento(MoedaDTO moedaDTO) throws PersistenceException {
		boolean estaRelacionado;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select idcontrato from contratos where  idMoeda = ? and deleted is null ");
		parametro.add(moedaDTO.getIdMoeda());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaRelacionado = true;
		} else {
			estaRelacionado = false;
		}
		return estaRelacionado;
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
				super.updateNotNull(obj);
	}
	
	
	public Collection findAtivos() throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("dataFim","IS", null));
		ordenacao.add(new Order("nomeMoeda"));
		return super.findByCondition(condicao, ordenacao);
	}

}
