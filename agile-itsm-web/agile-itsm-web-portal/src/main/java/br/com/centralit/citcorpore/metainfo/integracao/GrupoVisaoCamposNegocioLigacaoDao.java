package br.com.centralit.citcorpore.metainfo.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioLigacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GrupoVisaoCamposNegocioLigacaoDao extends CrudDaoDefaultImpl {

	public GrupoVisaoCamposNegocioLigacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		final List<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idGrupoVisaoCamposNegocioLigaca", "idGrupoVisaoCamposNegocioLigacao", true, true, false, false));
		listFields.add(new Field("idGrupoVisao", "idGrupoVisao", false, false, false, false));
		listFields.add(new Field("idCamposObjetoNegocio", "idCamposObjetoNegocio", false, false, false, false));
		listFields.add(new Field("idCamposObjetoNegocioLigacao", "idCamposObjetoNegocioLigacao", false, false, false, false));
		listFields.add(new Field("tipoLigacao", "tipoLigacao", false, false, false, false));
		listFields.add(new Field("filtro", "filtro", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "GrupoVisaoCamposNegocioLigacao";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return GrupoVisaoCamposNegocioLigacaoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdGrupoVisaoAndIdCamposObjetoNegocio(Integer idGrpVisao, Integer idCamposObjetoNegocio) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idGrupoVisao", "=", idGrpVisao));
		condicao.add(new Condition("idCamposObjetoNegocio", "=", idCamposObjetoNegocio));
		ordenacao.add(new Order("idGrupoVisaoCamposNegocioLigacao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdGrupoVisao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idGrupoVisao", "=", parm));
		ordenacao.add(new Order("idGrupoVisaoCamposNegocioLigacao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdGrupoVisao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idGrupoVisao", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdCamposObjetoNegocio(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idCamposObjetoNegocio", "=", parm));
		ordenacao.add(new Order("idGrupoVisaoCamposNegocioLigacao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdCamposObjetoNegocio(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCamposObjetoNegocio", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdCamposObjetoNegocioLigacao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idCamposObjetoNegocioLigacao", "=", parm));
		ordenacao.add(new Order("idGrupoVisaoCamposNegocioLigacao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdCamposObjetoNegocioLigacao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCamposObjetoNegocioLigacao", "=", parm));
		super.deleteByCondition(condicao);
	}

	public void deleteByIdVisao(Integer idVisaoParm) throws Exception {
		GrupoVisaoDao grupoVisaoDao = new GrupoVisaoDao();
		String sql = "DELETE FROM " + this.getTableName() + " WHERE idGrupoVisao IN (";
		sql += "SELECT idGrupoVisao FROM " + grupoVisaoDao.getTableName() + " WHERE idVisao = ?) and idGrupoVisao > 0 ";
		List parametros = new ArrayList();
		parametros.add(idVisaoParm);
		this.execUpdate(sql, parametros.toArray());
	}
}
