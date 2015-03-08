package br.com.centralit.citcorpore.metainfo.integracao;

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

public class VinculoVisaoDao extends CrudDaoDefaultImpl {
	public VinculoVisaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		final List<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idVisaoRelacionada" ,"idVisaoRelacionada", true, false, false, false));
		listFields.add(new Field("seq" ,"seq", true, false, false, false));
		listFields.add(new Field("tipoVinculo" ,"tipoVinculo", false, false, false, false));
		listFields.add(new Field("idGrupoVisaoPai" ,"idGrupoVisaoPai", false, false, false, false));
		listFields.add(new Field("idCamposObjetoNegocioPai" ,"idCamposObjetoNegocioPai", false, false, false, false));
		listFields.add(new Field("idGrupoVisaoFilho" ,"idGrupoVisaoFilho", false, false, false, false));
		listFields.add(new Field("idCamposObjetoNegocioFilho" ,"idCamposObjetoNegocioFilho", false, false, false, false));
		listFields.add(new Field("idCamposObjetoNegocioPaiNN" ,"idCamposObjetoNegocioPaiNN", false, false, false, false));
		listFields.add(new Field("idCamposObjetoNegocioFilhoNN" ,"idCamposObjetoNegocioFilhoNN", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "VinculoVisao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return VinculoVisaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdVisaoRelacionada(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idVisaoRelacionada", "=", parm)); 
		ordenacao.add(new Order("seq"));
		return super.findByCondition(condicao, ordenacao);
	}	
	public Collection findByIdGrupoVisaoPai(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idGrupoVisaoPai", "=", parm)); 
		ordenacao.add(new Order("seq"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdGrupoVisaoPai(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idGrupoVisaoPai", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdCamposObjetoNegocioPai(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCamposObjetoNegocioPai", "=", parm)); 
		ordenacao.add(new Order("seq"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCamposObjetoNegocioPai(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCamposObjetoNegocioPai", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdGrupoVisaoFilho(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idGrupoVisaoFilho", "=", parm)); 
		ordenacao.add(new Order("seq"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdGrupoVisaoFilho(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idGrupoVisaoFilho", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdCamposObjetoNegocioFilho(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCamposObjetoNegocioFilho", "=", parm)); 
		ordenacao.add(new Order("seq"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCamposObjetoNegocioFilho(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCamposObjetoNegocioFilho", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdCamposObjetoNegocioPaiNN(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCamposObjetoNegocioPaiNN", "=", parm)); 
		ordenacao.add(new Order("seq"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCamposObjetoNegocioPaiNN(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCamposObjetoNegocioPaiNN", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdCamposObjetoNegocioFilhoNN(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCamposObjetoNegocioFilhoNN", "=", parm)); 
		ordenacao.add(new Order("seq"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdCamposObjetoNegocioFilhoNN(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCamposObjetoNegocioFilhoNN", "=", parm));
		super.deleteByCondition(condicao);
	}
	public void deleteByIdVisaoPai(Integer idVisaoParm) throws Exception {
		VisaoRelacionadaDao visaoRelacionadaDao = new VisaoRelacionadaDao();
		String sql = "DELETE FROM " + this.getTableName() + " WHERE idVisaoRelacionada IN (";
		sql += "SELECT idVisaoRelacionada FROM " + visaoRelacionadaDao.getTableName() + " WHERE idVisaoPai = ?) and idVisaoRelacionada > 0 ";
		List parametros = new ArrayList();
		parametros.add(idVisaoParm);
		this.execUpdate(sql, parametros.toArray());
	}	
}
