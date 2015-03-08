package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.InformacoesContratoConfigDTO;
import br.com.centralit.citcorpore.bean.InformacoesContratoPerfSegDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class InformacoesContratoPerfSegDao extends CrudDaoDefaultImpl {

	public InformacoesContratoPerfSegDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws PersistenceException
	{
	    List order =  new ArrayList();
	    order.add(new Order("idPerfilSeguranca", "ASC"));
	    return super.find(obj, order);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idInformacoesContratoConfig" ,"idInformacoesContratoConfig", true, false, false, true));
		listFields.add(new Field("idPerfilSeguranca" ,"idPerfilSeguranca", true, false, false, true));
		
		return listFields;
	}

	public String getTableName() {
		return "InformacoesContratoPerfSeg";
	}

	public Collection list() throws PersistenceException {
		return null;
	}
	
	public Class getBean() {
		return InformacoesContratoPerfSegDTO.class;
	}

	public Collection findByIdInformacoesContratoConfig(Integer idInformacoesContratoConfig) throws Exception
	{
		List lstCond = new ArrayList();
		List lstOrdem = new ArrayList();
		
		lstCond.add(new Condition("idInformacoesContratoConfig", "=", idInformacoesContratoConfig));
		
		lstOrdem.add(new Order("idPerfilSeguranca"));
	    return super.findByCondition(lstCond, lstOrdem);
	}
	
    public void deleteAllByIdInformacoesContratoConfig(
            InformacoesContratoConfigDTO contrato) throws PersistenceException {
    	List condicao = new ArrayList();
    	condicao.add(new Condition("idInformacoesContratoConfig", "=", contrato.getIdInformacoesContratoConfig()));
    	super.deleteByCondition(condicao);
    }
	
}