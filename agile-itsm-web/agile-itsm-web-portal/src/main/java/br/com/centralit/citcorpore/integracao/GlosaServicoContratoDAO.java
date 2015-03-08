package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GlosaServicoContratoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class GlosaServicoContratoDAO extends CrudDaoDefaultImpl {

	public GlosaServicoContratoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idGlosaServicoContrato", "idGlosaServicoContrato", true, true, false, false));
		listFields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
		listFields.add(new Field("quantidadeGlosa", "quantidadeGlosa", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		return listFields;
	}
	
	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public String getTableName() {
		return this.getOwner() + "GlosaServicoContrato";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return GlosaServicoContratoDTO.class;
	}
	
	public Collection findByIdServicoContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idServicoContrato", "=", parm));
		ordenacao.add(new Order("idGlosaServicoContrato"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public Collection quantidadeGlosaServico(Integer idServicoContrato) throws PersistenceException {
		
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		sql.append("SELECT quantidadeglosa, idservicocontrato FROM " + getTableName() + " WHERE idservicocontrato = ? and datafim is null ");
		parametro.add(idServicoContrato);
		List list = execSQL(sql.toString(), parametro.toArray());
		
		List listRetorno = new ArrayList();
		listRetorno.add("quantidadeGlosa");
		listRetorno.add("idServicoContrato");
		List<GlosaServicoContratoDTO> result = this.engine.listConvertion(getBean(), list, listRetorno);
		
		return result;
	}
	
	public void atualizaQuantidadeGlosa(Integer novaQuantidade, Integer idServicoContrato) throws PersistenceException {
		
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		sql.append("UPDATE " + getTableName().toLowerCase() + " SET quantidadeglosa = ? WHERE idservicocontrato = ? ");
		parametro.add(novaQuantidade);
		parametro.add(idServicoContrato);
		execUpdate(sql.toString(), parametro.toArray());
		
	}
	
}
