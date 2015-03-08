package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.VinculoVisaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
@SuppressWarnings("unchecked")
public class VinculaOsIncidenteDao extends CrudDaoDefaultImpl {
	
	public VinculaOsIncidenteDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idOS" ,"idOS", true, false, false, false));
		listFields.add(new Field("idSolicitacaoServico" ,"idSolicitacaoServico", true, false, false, false));
		listFields.add(new Field("idAtividadesOS" ,"idAtividadesOS", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "VinculaOsIncidente";
	}
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idOS"));
		return super.list(ordenacao);
	}

	public Class getBean() {
		return VinculoVisaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	
	public Collection findByIdOS(Integer idOS) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idOS", "=", idOS));
		ordenacao.add(new Order("idSolicitacaoServico"));
		return super.findByCondition(condicao, ordenacao);		
	}
	
	public Collection findByIdAtividadeOS(Integer idAtividadesOS) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idAtividadesOS", "=", idAtividadesOS));
		ordenacao.add(new Order("idSolicitacaoServico"));
		Collection resp = super.findByCondition(condicao, ordenacao);
		if(resp != null){
			return resp;
		}else{
			return new ArrayList();
		}
	}
	
	public void deleteByIdOs(Integer idOs) throws PersistenceException {
		List parametros = new ArrayList();
		String sql = "DELETE FROM " + this.getTableName() + " WHERE idOS = ? ";
		parametros.add(idOs);
		this.execUpdate(sql, parametros.toArray());
	}
	
	public void deleteByIdAtividadeOS(Integer idAtividadeOS) throws PersistenceException {
		List parametros = new ArrayList();
		String sql = "DELETE FROM " + this.getTableName() + " WHERE idAtividadesOS = ? ";
		parametros.add(idAtividadeOS);
		this.execUpdate(sql, parametros.toArray());
	}
	
	public boolean verificaServicoSelecionado(Integer idServicoContratoContabil) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idServicoContratoContabil));
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idOS"));
		Collection resp = super.findByCondition(condicao, ordenacao);
		if(resp != null && resp.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean verificaServicoJaVinculado(Integer idOS, Integer idServicoContratoContabil) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idOS", "<>", idOS));
		condicao.add(new Condition("idSolicitacaoServico", "=", idServicoContratoContabil));
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idOS"));
		Collection resp = super.findByCondition(condicao, ordenacao);
		if(resp != null && resp.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
}
