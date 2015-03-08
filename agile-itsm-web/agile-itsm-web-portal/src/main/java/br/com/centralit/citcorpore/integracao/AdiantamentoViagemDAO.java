package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AdiantamentoViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes","unchecked"})
public class AdiantamentoViagemDAO extends CrudDaoDefaultImpl {
	

	public AdiantamentoViagemDAO(){
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		AdiantamentoViagemDTO adiantamentoViagemDto = (AdiantamentoViagemDTO) obj;
		List list = new ArrayList();
		list.add(new Order("idAdiantamentoViagem"));
		return super.find(adiantamentoViagemDto,list);
	}
	
	@Override
	public String getTableName() {
		return this.getOwner() + "adiantamentoviagem";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idAdiantamentoViagem"));
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return AdiantamentoViagemDTO.class;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idadiantamentoviagem" ,"idAdiantamentoViagem", true, true, false, false));
		listFields.add(new Field("idresponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("idsolicitacaoservico" ,"idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idempregado" ,"idEmpregado", false, false, false, false));
		listFields.add(new Field("datahora" ,"dataHora", false, false, false, false));
		listFields.add(new Field("valortotaladiantado" ,"valorTotalAdiantado", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("observacoes" ,"observacoes", false, false, false, false));
		listFields.add(new Field("integrantefuncionario" ,"integranteFuncionario", false, false, false, false));
		
		// TODO Este campo esta em desuso, pode ser removido na proxima versão
		listFields.add(new Field("nomenaofuncionario" ,"nomeNaoFuncionario", false, false, false, false));
		
		return listFields;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public List<AdiantamentoViagemDTO> findBySolicitacaoAndEmpregado(AdiantamentoViagemDTO adiantamentoViagemDto) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", adiantamentoViagemDto.getIdSolicitacaoServico()));
		condicao.add(new Condition(Condition.AND, "idEmpregado", "=", adiantamentoViagemDto.getIdEmpregado()));
		return (List<AdiantamentoViagemDTO>) super.findByCondition(condicao, null);
	}
	
	
	/**
	 * Retorna uma lista de adiantamentos conforme idsolicitacao e idempregado passado
	 * 
	 * TODO este metodo é a mesma lógica do public List<AdiantamentoViagemDTO> findBySolicitacaoAndEmpregado(AdiantamentoViagemDTO adiantamentoViagemDto) throws Exception, seria legal apagar um dos dois.
	 * 
	 * @param solicitacaoServico
	 * @param idEmpregado
	 * @return
	 * @throws Exception
	 */
	public List<AdiantamentoViagemDTO> consultarPorSolicitacaoEEmpregado(Integer solicitacaoServico, Integer idEmpregado) throws PersistenceException {
		
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", solicitacaoServico));
		condicao.add(new Condition(Condition.AND, "idEmpregado", "=", idEmpregado));
		
		return (List<AdiantamentoViagemDTO>) super.findByCondition(condicao, null);
		
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public List<AdiantamentoViagemDTO> consultarPorSolicitacao(Integer solicitacaoServico) throws PersistenceException {
		
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", solicitacaoServico));
		
		return (List<AdiantamentoViagemDTO>) super.findByCondition(condicao, null);
		
	}

	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public List<AdiantamentoViagemDTO> consultarPorSolicitacaoENomeNaoFuncionario(Integer solicitacaoServico, String nomeNaoFunc) throws PersistenceException {
		
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", solicitacaoServico));
		condicao.add(new Condition(Condition.AND, "nomeNaoFuncionario", "=", nomeNaoFunc));
		
		return (List<AdiantamentoViagemDTO>) super.findByCondition(condicao, null);
		
	}
}