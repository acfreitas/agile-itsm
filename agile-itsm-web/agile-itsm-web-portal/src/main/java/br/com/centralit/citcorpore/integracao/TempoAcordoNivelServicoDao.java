package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TempoAcordoNivelServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TempoAcordoNivelServicoDao extends CrudDaoDefaultImpl {

	public TempoAcordoNivelServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAcordoNivelServico", "idAcordoNivelServico", true, false, false, false));
		listFields.add(new Field("idPrioridade", "idPrioridade", true, false, false, false));
		listFields.add(new Field("idFase", "idFase", true, false, false, false));
		listFields.add(new Field("tempoHH", "tempoHH", false, false, false, false));
		listFields.add(new Field("tempoMM", "tempoMM", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "TempoAcordoNivelServico";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return TempoAcordoNivelServicoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public void deleteByIdAcordo(Integer idAcordoNivelServico) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdAcordo(Integer idAcordoNivelServico) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico));
		ordenacao.add(new Order("idFase"));
		ordenacao.add(new Order("idPrioridade"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdAcordoAndIdPrioridade(Integer idAcordoNivelServico, Integer idPrioridade) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico));
		condicao.add(new Condition("idPrioridade", "=", idPrioridade));
		ordenacao.add(new Order("idFase"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdAcordoAndFaseAndIdPrioridade(Integer idAcordoNivelServico, Integer idFase, Integer idPrioridade) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idAcordoNivelServico", "=", idAcordoNivelServico));
		condicao.add(new Condition("idFase", "=", idFase));
		condicao.add(new Condition("idPrioridade", "=", idPrioridade));
		ordenacao.add(new Order("idPrioridade"));
		return super.findByCondition(condicao, ordenacao);
	}
}
