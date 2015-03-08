package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LimiteAprovacaoProcessoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class LimiteAprovacaoProcessoDao extends CrudDaoDefaultImpl {
	public LimiteAprovacaoProcessoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idLimiteAprovacao" ,"idLimiteAprovacao", true, false, false, false));
		listFields.add(new Field("idProcessoNegocio" ,"idProcessoNegocio", true, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "LimiteAprovacaoProcesso";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return LimiteAprovacaoProcessoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdLimiteAprovacao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idLimiteAprovacao", "=", parm)); 
		ordenacao.add(new Order("idProcessoNegocio"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdProcessoNegocio(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProcessoNegocio", "=", parm)); 
		ordenacao.add(new Order("idLimiteAprovacao"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdLimiteAprovacao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idLimiteAprovacao", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdProcessoNegocioAndNotIdLimiteAprovacao(Integer idProcessoNegocio, Integer idLimiteAprovacao) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProcessoNegocio", "=", idProcessoNegocio)); 
		condicao.add(new Condition("idLimiteAprovacao", "<>", idLimiteAprovacao)); 
		ordenacao.add(new Order("idProcessoNegocio"));
		return super.findByCondition(condicao, ordenacao);
	}
	
    public Collection findDuplicados(Integer idLimiteAprovacao, Integer idProcessoNegocio) throws PersistenceException {
    	String sql = "select lp.idLimiteAprovacao, lp.idProcessoNegocio "+
    				 " from limiteaprovacaoprocesso lp inner join "+
    				 "                                 limiteaprovacaoautoridade la ON la.idlimiteaprovacao = lp.idlimiteaprovacao "+
    				 " where "+
    				 "      lp.idlimiteaprovacao  = ? "+
    				 "   and lp.idprocessonegocio = ? "+
    				 "   and exists( select 1 from limiteaprovacaoprocesso lp1 inner join "+
    				 "                             limiteaprovacaoautoridade la1 ON la1.idlimiteaprovacao = lp1.idlimiteaprovacao "+
    				 "                where lp1.idprocessonegocio = lp.idprocessonegocio "+
    				 "                  and la1.idlimiteaprovacao <> la.idlimiteaprovacao "+
    				 "                  and la1.idnivelautoridade = la.idnivelautoridade) ";
    	
        Object[] objs = new Object[] {idLimiteAprovacao, idProcessoNegocio};
        List lista = this.execSQL(sql, objs);
        
        List listRetorno = new ArrayList();
        
        listRetorno.add("idLimiteAprovacao"); 
        listRetorno.add("idProcessoNegocio");
        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        
        return result;
    } 

}
