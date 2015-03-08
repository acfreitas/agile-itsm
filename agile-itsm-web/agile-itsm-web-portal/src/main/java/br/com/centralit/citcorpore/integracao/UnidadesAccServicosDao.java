package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class UnidadesAccServicosDao extends CrudDaoDefaultImpl {
	public UnidadesAccServicosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idUnidade" ,"idUnidade", true, false, false, false));
		listFields.add(new Field("idServico" ,"idServico", true, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "unidadesAccServicos";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return UnidadesAccServicosDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdUnidade(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idUnidade", "=", parm)); 
		ordenacao.add(new Order("idServico"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdUnidade(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idUnidade", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idServico", "=", parm)); 
		ordenacao.add(new Order("idUnidade"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdServico(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idServico", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public void deleteByIdServicoUnidade(Integer idUnidade, Integer idServico) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idUnidade", "=", idUnidade));
		condicao.add(new Condition("idServico", "=", idServico));
		super.deleteByCondition(condicao);
	}
	
	/**
	 * Prepara Lista de Retorno.
	 * 
	 * @return List
	 */
	private List prepararListaDeRetorno() {
		List listRetorno = new ArrayList();
		listRetorno.add("idServico");
		listRetorno.add("nomeServico");
		listRetorno.add("detalheServico");
		return listRetorno;
	}
	
	/**
	 * Verifica se já existe uma associação feita.
	 * 
	 * @param idServico e idUnidade
	 * @return boolean
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public boolean existeAssociacaoComUnidade(Integer idServico, Integer idUnidade) throws PersistenceException {
		
		List servicos = new ArrayList();
		servicos.add(new Order("idServico"));
		 
		UnidadesAccServicosDTO unidadesAccServicosDTO = new UnidadesAccServicosDTO();
		
		unidadesAccServicosDTO.setIdServico(idServico);
		
		if (idUnidade != null && !(idUnidade.intValue() == 0)) {
			unidadesAccServicosDTO.setIdUnidade(idUnidade);
		}
		
		List listaServicos = (List) super.find(unidadesAccServicosDTO, servicos);
		
		if (listaServicos == null || listaServicos.isEmpty()) {
		    return false;
		} else {
		    return true;
		}
	}
	
	/**
	 * Retorna os serviços para a unidade informada
	 * 
	 * @param idUnidade
	 * @return Collection
	 * @throws Exception
	 * @author rodrigo.oliveira
	 */
	public Collection consultaServicosPorUnidade(Integer idUnidade) throws PersistenceException {
		
		List parametro = new ArrayList();
		
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT s.idservico, s.nomeservico, s.detalheservico FROM servico as s ")
			.append("INNER JOIN unidadesaccservicos AS u ON s.idservico = u.idservico ")
			.append("WHERE u.idunidade = ? order by nomeservico asc ");
		
		parametro.add(idUnidade);
		
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = this.prepararListaDeRetorno();
		List result = null;
		
		result = this.engine.listConvertion(getBean(), lista, listRetorno);
		
		if (result == null || result.size() == 0)
			return null;
		
		HashMap map = new HashMap();
		Collection colFinal = new ArrayList();
		//O laco abaixo tira as duplicacoes. NAO COLOQUE DISTINCT NO SQL ACIMA, POIS NAO FUNCIONA NO ORACLE! EMAURI EM 04/10/2012.
		for (Iterator it = result.iterator(); it.hasNext();){
		    UnidadesAccServicosDTO unidadesAccServicosDTO = (UnidadesAccServicosDTO)it.next();
		    if (!map.containsKey("ID" + unidadesAccServicosDTO.getIdServico())){
			colFinal.add(unidadesAccServicosDTO);
			map.put("ID" + unidadesAccServicosDTO.getIdServico(), "ID" + unidadesAccServicosDTO.getIdServico());
		    }
		}
		
		return colFinal;
	}
}
