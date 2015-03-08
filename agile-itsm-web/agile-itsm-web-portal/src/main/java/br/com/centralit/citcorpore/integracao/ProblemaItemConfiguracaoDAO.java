package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ProblemaItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ProblemaItemConfiguracaoDAO extends CrudDaoDefaultImpl {


	public ProblemaItemConfiguracaoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idProblemaItemConfiguracao", "idProblemaItemConfiguracao", true, true, false, false));
		listFields.add(new Field("idProblema", "idProblema", false, false, false, false));
		listFields.add(new Field("idItemConfiguracao", "idItemConfiguracao", false, false, false, false));
		listFields.add(new Field("descricaoProblema", "descricaoProblema", false, false, false, false));
		return listFields;
	}

	public ProblemaItemConfiguracaoDTO findByIdItemConfiguracaoEIdProblema(Integer idItemConfiguracao, Integer idProblema) throws PersistenceException {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("idItemConfiguracao", "=", idItemConfiguracao));
		condicoes.add(new Condition("idProblema", "=", idProblema));
		
		ArrayList<ProblemaItemConfiguracaoDTO> lista = (ArrayList<ProblemaItemConfiguracaoDTO>) findByCondition(condicoes, null);
		
		if(lista != null){
			return lista.get(0);
		}
		
		return null;
	}
	
	/**
	 * Verifica se existe outro item igual criado.
	 * Se existir retorna 'true', senao retorna 'false';
	 */
	public boolean verificaSeCadastrado(ProblemaItemConfiguracaoDTO itemDTO) throws PersistenceException {
		boolean estaCadastrado;		
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + getTableName() + " where idItemConfiguracao = ? and idProblema = ?  ");
		parametro.add(itemDTO.getIdItemConfiguracao());
		parametro.add(itemDTO.getIdProblema());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaCadastrado = true;
		} else {
			estaCadastrado = false;
		}
		return estaCadastrado;
	}
	
	/**
	 * Deleta os que estão no banco mas não estão na lista passada para update.
	 * 
	 * @param idProblema
	 * @param listaICs
	 * @throws Exception
	 */
	public void deletaOsQueNaoEstaoNaListaByIdProblema(Integer idProblema, ArrayList<ProblemaItemConfiguracaoDTO> listaICs) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", idProblema));

		if(listaICs != null && listaICs.size() > 0){
			for (ProblemaItemConfiguracaoDTO p : listaICs) {
				condicao.add(new Condition("idItemConfiguracao", "<>", p.getIdItemConfiguracao()));
			}
		}
		this.deleteByCondition(condicao);
	}

	public String getTableName() {
		return this.getOwner() + "problemaitemconfiguracao";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ProblemaItemConfiguracaoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdProblemaItemConfiguracao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idProblemaItemConfiguracao", "=", parm));
		ordenacao.add(new Order("idProblemaItemConfiguracao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdProblemaItemConfiguracao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblemaItemConfiguracao", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", parm));
		ordenacao.add(new Order("idProblema"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdProblema(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProblema", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdItemConfiguracao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemConfiguracao", "=", parm));
		ordenacao.add(new Order("idItemConfiguracao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdItemConfiguracao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemConfiguracao", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public List<ItemConfiguracaoDTO> listItemConfiguracaoByIdProblema (Integer idProblema) throws PersistenceException {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		sql.append("select  ic.iditemconfiguracao  ");
		sql.append(" from itemconfiguracao ic    ");
		sql.append(" inner join problemaitemconfiguracao pi ");
		sql.append(" on ic.iditemconfiguracao = pi.iditemconfiguracao ");
		sql.append(" where pi.idproblema = ? ");
	
		parametro.add(idProblema);
		
		fields.add("idItemConfiguracao");
   
		List dados =  this.execSQL(sql.toString(), parametro.toArray());
		 
		return (List<ItemConfiguracaoDTO>) this.listConvertion(ItemConfiguracaoDTO.class, dados, fields);
	}
	
	public ProblemaItemConfiguracaoDTO restoreByIdProblema(Integer idSolicitacao) throws PersistenceException {
		List parametro = new ArrayList();
		parametro.add(idSolicitacao);
		
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("idProblemaItemConfiguracao");
		listRetorno.add("idProblema");
		listRetorno.add("idItemConfiguracao");
		listRetorno.add("descricaoProblema");

		String sql = " select * from problemaitemconfiguracao where idproblema = ? ";

		List lista = this.execSQL(sql.toString(), parametro.toArray());

		if (lista != null && !lista.isEmpty()) {
			List listaResult = this.engine.listConvertion(ProblemaItemConfiguracaoDTO.class, lista, listRetorno);
			return (ProblemaItemConfiguracaoDTO) listaResult.get(0);
		} else {
			return null;
		}
	}
}
