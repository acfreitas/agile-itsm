package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CentroResultadoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CentroResultadoDao extends CrudDaoDefaultImpl {
	
	public CentroResultadoDao() {		
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	private static final String SQL_GET_LISTA_SEM_PAI =
			"select " + 
		        "idcentroresultado, codigocentroresultado, nomecentroresultado, " + 
		        "idcentroresultadopai, permiterequisicaoproduto, situacao " +
		    "from " +
		        "centroresultado " +
		    "where " +
		        "idcentroresultadopai is null " +
		    "order by " +
		        "codigocentroresultado";
	
	public Collection<Field> getFields() {
		
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idcentroresultado" ,"idCentroResultado", true, true, false, false) );		
		listFields.add(new Field("codigocentroresultado" ,"codigoCentroResultado", false, false, false, false) );
		listFields.add(new Field("nomecentroresultado" ,"nomeCentroResultado", false, false, false, false) );
		listFields.add(new Field("idcentroresultadopai" ,"idCentroResultadoPai", false, false, false, false) );
        listFields.add(new Field("permiterequisicaoproduto" ,"permiteRequisicaoProduto", false, false, false, false) );
        listFields.add(new Field("situacao", "situacao", false, false, false, false) );
				
		return listFields;
	}
	
	public String getTableName() {
		return this.getOwner() + "centroresultado";
	}
	
	public Collection list() throws PersistenceException {
		
        List ordenacao = new ArrayList();        
        ordenacao.add(new Order("idCentroResultado") );
        
        return super.list(ordenacao);
	}

    public Collection listAtivos() throws PersistenceException {
    	
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        
        condicao.add(new Condition("situacao", "=", "A") );
        ordenacao.add(new Order("nomeCentroResultado") );
        
        return super.findByCondition(condicao, ordenacao);
    }
    
    public Collection listPermiteRequisicaoProduto() throws PersistenceException {
    	
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();      
        
        condicao.add(new Condition("situacao", "=", "A") ); 
        condicao.add(new Condition("permiteRequisicaoProduto", "=", "S") ); 
        ordenacao.add(new Order("nomeCentroResultado") );
        
        return super.findByCondition(condicao, ordenacao);
    }
    
    public Collection findSemPai() throws PersistenceException {
    	
        Object[] objs = new Object[] {};
        List lista = this.execSQL(SQL_GET_LISTA_SEM_PAI, objs);
        
        List listRetorno = new ArrayList();
        
        listRetorno.add("idCentroResultado"); 
        listRetorno.add("codigoCentroResultado");
        listRetorno.add("nomeCentroResultado");
        listRetorno.add("idCentroResultadoPai");
        listRetorno.add("permiteRequisicaoProduto");
        listRetorno.add("situacao");
        
        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        
        return result;
    }   
    
    public Collection findByIdPai(Integer idPai) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();        
        
        condicao.add(new Condition("idCentroResultadoPai", "=", idPai) ); 
        ordenacao.add(new Order("codigoCentroResultado") );
        
        return super.findByCondition(condicao, ordenacao);
    }
    
    public Class getBean() {
    	return CentroResultadoDTO.class;
	}
    
	public Collection find(IDto arg0) throws PersistenceException {
		return super.find(arg0, null);
	}
	
	public boolean temFilhos(int idCentroResultado) {
		boolean resposta = false;
		
		try {
			List resultadoSQL = this.execSQL(String.format("select * from centroresultado where idcentroresultadopai = %d", idCentroResultado), null);
			
			if (!resultadoSQL.isEmpty() ) {
				resposta = true;
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resposta;
	}
	
	public Collection findByIdAlcada(Integer idAlcada) throws PersistenceException {
		List resultado = null;
		
		if (idAlcada != null && idAlcada > 0) {
			try {
				resultado = this.execSQL(String.format("select * from alcadacentroresultado where idalcada = %d", idAlcada), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return resultado;
	}
	
	
	/**
	 * Metodo que consulta todos os centro de custos filhos vinculados a uma alçada
	 * 
	 * @param idPai = Centro de resultado Pai
	 * @return
	 * @throws Exception
	 */
	public Collection consultarCentroDeCustoComVinculoNaAlcada(Integer idPai) throws PersistenceException {
		
		List resultado = null;
		
		if (idPai != null && idPai > 0) {
			try {
				
				List lista = this.execSQL("select * from centroresultado where idcentroresultado in " +
						"(select idcentroresultado from alcadacentroresultado) and idcentroresultadopai = " + idPai, null);
		        
		        List listRetorno = new ArrayList();
		        
		        listRetorno.add("idCentroResultado"); 
		        listRetorno.add("codigoCentroResultado");
		        listRetorno.add("nomeCentroResultado");
		        listRetorno.add("idCentroResultadoPai");
		        listRetorno.add("permiteRequisicaoProduto");
		        listRetorno.add("situacao");
		        
		        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		        
		        return result;
		        
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return resultado;
	}

	/**
	 * Metodo que consulta todos os centro de custos filhos vinculados a uma alçada de viagem
	 * 
	 * @param idPai = Centro de resultado Pai
	 * @return
	 * @throws Exception
	 */
	public Collection consultarCentroDeCustoComVinculoViagemNaAlcada(Integer idPai) throws PersistenceException {
		
		List resultado = null;
		
		if (idPai != null && idPai > 0) {
			try {
				
				List lista = this.execSQL("select * from centroresultado where idcentroresultado in " +
						"(select distinct idcentroresultado from alcadacentroresultado acr inner join alcada a on a.idalcada = acr.idalcada "
						+ " where a.nomealcada like '%Viagem%') and idcentroresultadopai = " + idPai, null);
		        
		        List listRetorno = new ArrayList();
		        
		        listRetorno.add("idCentroResultado"); 
		        listRetorno.add("codigoCentroResultado");
		        listRetorno.add("nomeCentroResultado");
		        listRetorno.add("idCentroResultadoPai");
		        listRetorno.add("permiteRequisicaoProduto");
		        listRetorno.add("situacao");
		        
		        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		        
		        return result;
		        
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return resultado;
	}
	
	public Collection<CentroResultadoDTO> findPaisVinculados(Integer idSolicitante, String tipoAlcada) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sb = new StringBuilder();
		
		sb.append("select centroresultado.idcentroresultado, centroresultado.codigocentroresultado, centroresultado.nomecentroresultado,");
		sb.append("centroresultado.idcentroresultadopai, centroresultado.permiterequisicaoproduto, centroresultado.situacao ");
		sb.append("from centroresultado join (select distinct centroresultado.idcentroresultadopai as idpai from centroresultado join (");
									sb.append("select distinct alcadacentroresultado.idcentroresultado ");
									sb.append("from alcada join alcadacentroresultado on alcada.tipoalcada = ? and alcada.situacao = 'A' and ");
																			  sb.append("alcada.idalcada = alcadacentroresultado.idalcada and "); 
																		      sb.append("alcadacentroresultado.idempregado=?) a on centroresultado.idcentroresultado = a.idcentroresultado) relpai on centroresultado.idcentroresultado = relpai.idpai ");
		sb.append("order by	codigocentroresultado");
		parametro.add(tipoAlcada);
		parametro.add(idSolicitante);
		List listRetorno = new ArrayList();
		listRetorno.add("idCentroResultado"); 
        listRetorno.add("codigoCentroResultado");
        listRetorno.add("nomeCentroResultado");
        listRetorno.add("idCentroResultadoPai");
        listRetorno.add("permiteRequisicaoProduto");
        listRetorno.add("situacao");
		
		List list = this.execSQL(sb.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return (List<CentroResultadoDTO>) this.listConvertion(CentroResultadoDTO.class, list, listRetorno);
		} else {
			return null;
		}
	}
	
	public Collection<CentroResultadoDTO> findPaisVinculados(String tipoAlcada) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sb = new StringBuilder();
		
		sb.append("select distinct cr.idcentroresultado, cr.codigocentroresultado, cr.nomecentroresultado, cr.idcentroresultadopai, cr.permiterequisicaoproduto, cr.situacao from centroresultado cr ")
		.append(" inner join alcadacentroresultado acr on cr.idcentroresultado = acr.idcentroresultado inner join alcada a on a.idalcada = acr.idalcada ")
		.append(" where a.tipoalcada = ? and a.situacao = 'A' order by cr.codigocentroresultado; ");
		parametro.add(tipoAlcada);
		List listRetorno = new ArrayList();
		listRetorno.add("idCentroResultado"); 
        listRetorno.add("codigoCentroResultado");
        listRetorno.add("nomeCentroResultado");
        listRetorno.add("idCentroResultadoPai");
        listRetorno.add("permiteRequisicaoProduto");
        listRetorno.add("situacao");
		
		List list = this.execSQL(sb.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return (List<CentroResultadoDTO>) this.listConvertion(CentroResultadoDTO.class, list, listRetorno);
		} else {
			return null;
		}
	}
	
	public Collection<CentroResultadoDTO> findFilhosVinculados(Integer idPai,Integer idSolicitante, String tipoAlcada) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sb = new StringBuilder();
		
		sb.append("select centroresultado.idcentroresultado, centroresultado.codigocentroresultado, centroresultado.nomecentroresultado,");
		sb.append("centroresultado.idcentroresultadopai, centroresultado.permiterequisicaoproduto, centroresultado.situacao ");
		sb.append("from centroresultado join (");
									sb.append("select distinct alcadacentroresultado.idcentroresultado as idfilho ");
									sb.append("from alcada join alcadacentroresultado on alcada.tipoalcada = ? and alcada.situacao = 'A' and ");
																			  sb.append("alcada.idalcada = alcadacentroresultado.idalcada and "); 
																		      sb.append("alcadacentroresultado.idempregado=?) a on centroresultado.idcentroresultado = a.idfilho and ");
																		      sb.append("idCentroResultadoPai = ? ");
		sb.append("order by	codigocentroresultado");
		parametro.add(tipoAlcada);
		parametro.add(idSolicitante);
		parametro.add(idPai);
		List listRetorno = new ArrayList();
		listRetorno.add("idCentroResultado"); 
        listRetorno.add("codigoCentroResultado");
        listRetorno.add("nomeCentroResultado");
        listRetorno.add("idCentroResultadoPai");
        listRetorno.add("permiteRequisicaoProduto");
        listRetorno.add("situacao");
		
		List list = this.execSQL(sb.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return (List<CentroResultadoDTO>) this.listConvertion(CentroResultadoDTO.class, list, listRetorno);
		} else {
			return null;
		}
	}
}