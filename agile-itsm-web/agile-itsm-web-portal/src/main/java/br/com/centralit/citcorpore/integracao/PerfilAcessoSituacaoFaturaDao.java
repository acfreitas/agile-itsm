package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PerfilAcessoSituacaoFaturaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PerfilAcessoSituacaoFaturaDao extends CrudDaoDefaultImpl {

	public PerfilAcessoSituacaoFaturaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idPerfil", "idPerfil", true, false, false, false));
		listFields.add(new Field("situacaoFatura", "situacaoFatura", true, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "PerfilAcessoSituacaoFatura";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return PerfilAcessoSituacaoFaturaDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	/**
	 * Retorna PerfilAcessoSituacaoFatura Ativos por idPerfilAcesso.
	 * 
	 * @param parm
	 * @return
	 * @throws Exception
	 */
	public Collection findByIdPerfil(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idPerfil", "=", parm));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("situacaoFatura"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdPerfil(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idPerfil", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findBySituacaoFatura(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("situacaoFatura", "=", parm));
		ordenacao.add(new Order("idPerfil"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteBySituacaoFatura(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("situacaoFatura", "=", parm));
		super.deleteByCondition(condicao);
	}
}
