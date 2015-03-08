package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author ronnie.lopes
 * 
 */

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PrestacaoContasViagemDao extends CrudDaoDefaultImpl {
	
	public PrestacaoContasViagemDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idprestacaocontasviagem", "idPrestacaoContasViagem", true, true, false, false));
		listFields.add(new Field("idresponsavel", "idResponsavel", false, false, false, false));
		listFields.add(new Field("idaprovacao", "idAprovacao", false, false, false, false));
		listFields.add(new Field("idsolicitacaoservico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idempregado", "idEmpregado", false, false, false, false));
		listFields.add(new Field("datahora", "dataHora", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("iditemtrabalho", "idItemTrabalho", false, false, false, false));
		listFields.add(new Field("integrantefuncionario", "integranteFuncionario", false, false, false, false));
		
		// TODO Este campo esta em desuso, pode ser removido na proxima versão
		listFields.add(new Field("nomenaofuncionario", "nomeNaoFuncionario", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "prestacaocontasviagem";
	}

	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("idPrestacaoContasViagem"));
		return super.find(obj, ordem);
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("idPrestacaoContasViagem"));
		return super.list(list);
	}

	public Class getBean() {
		return PrestacaoContasViagemDTO.class;
	}
	
	/**
	 * Retorna um objeto PrestacaoContasViagemDTO por SolicitacaoServico e Empregado
	 * 
	 * @param idSolicitacaoServico
	 * @param idEmpregado
	 * @return
	 * @throws Exception
	 */
	public PrestacaoContasViagemDTO findBySolicitacaoAndEmpregado(Integer idSolicitacaoServico, Integer idEmpregado) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition(Condition.AND ,"idEmpregado", "=", idEmpregado));
		List result = (List) super.findByCondition(condicao, null);
		if(result != null){
			return (PrestacaoContasViagemDTO) result.get(0);
		}
		return null;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public PrestacaoContasViagemDTO findBySolicitacaoAndNomeNaoFuncionario(Integer idSolicitacaoServico, String nomeNaoFunc) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition(Condition.AND ,"nomeNaoFuncionario", "=", nomeNaoFunc));
		List result = (List) super.findByCondition(condicao, null);
		if(result != null){
			return (PrestacaoContasViagemDTO) result.get(0);
		}
		return null;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public List findBySolicitacao(Integer idSolicitacaoServico, Integer idEmpregado) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		return (List) super.findByCondition(condicao, null);
	}
	
	/**
	 * Retorna uma lista de prestação de contas conforme idsolicitacaoservico passados
	 * 
	 * @param idSolicitacaoServico
	 * @return
	 * @throws Exception
	 */
	public List findBySolicitacao(Integer idSolicitacaoServico) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("idItemTrabalho", "is", null));
		return (List) super.findByCondition(condicao, null);
	}
	
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection findBySolicitacaoAndTafera(Integer idSolicitacaoServico) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA)); 
        condicao.add(new Condition("idItemTrabalho", "IS", null)); 
        ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection findBySolicitacaoAndConferencia(Integer idSolicitacaoServico) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA)); 
		condicao.add(new Condition("idItemTrabalho", "is", null)); 
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Verifica se a requisição esta na etapa de prestação de contas
	 * 
	 * @param requisicaoViagemDto
	 * @return
	 * @throws Exception
	 */
	public boolean isEstadoPrestacaoContas(RequisicaoViagemDTO requisicaoViagemDto) throws PersistenceException {
		List result = null;
		if(requisicaoViagemDto.getEstado().equalsIgnoreCase(RequisicaoViagemDTO.AGUARDANDO_PRESTACAOCONTAS)){
			if(requisicaoViagemDto.getTarefaIniciada() != null && requisicaoViagemDto.getTarefaIniciada().equalsIgnoreCase("S"))
				return false;
			List condicao = new ArrayList();
			List ordenacao = new ArrayList();
			condicao.add(new Condition("idSolicitacaoServico", "=", requisicaoViagemDto.getIdSolicitacaoServico()));
			condicao.add(new Condition("idItemTrabalho", "IS", null)); 
			ordenacao.add(new Order("idPrestacaoContasViagem"));
			
			result = (List) super.findByCondition(condicao, ordenacao);
			if(result == null)
				return true;
		}
		return  false;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public boolean isEstadoAutorizacao(RequisicaoViagemDTO requisicaoViagemDto) throws PersistenceException {
		List result = null;
		if(requisicaoViagemDto.getEstado().equalsIgnoreCase(RequisicaoViagemDTO.AGUARDANDO_APROVACAO)){
			if(requisicaoViagemDto.getTarefaIniciada() != null && requisicaoViagemDto.getTarefaIniciada().equalsIgnoreCase("S"))
				return false;
			List condicao = new ArrayList();
			List ordenacao = new ArrayList();
			condicao.add(new Condition("idSolicitacaoServico", "=", requisicaoViagemDto.getIdSolicitacaoServico()));
			condicao.add(new Condition("idItemTrabalho", "IS", null)); 
			ordenacao.add(new Order("idPrestacaoContasViagem"));
			
			result = (List) super.findByCondition(condicao, ordenacao);
			if(result == null)
				return true;
		}
		return  false;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public boolean isViagemRemarcado(RequisicaoViagemDTO requisicaoViagemDto) throws PersistenceException {
		List result = null;
		if(requisicaoViagemDto.getEstado().equalsIgnoreCase(RequisicaoViagemDTO.REMARCADO)){
			List condicao = new ArrayList();
			List ordenacao = new ArrayList();
			condicao.add(new Condition("idSolicitacaoServico", "=", requisicaoViagemDto.getIdSolicitacaoServico()));
			condicao.add(new Condition("idItemTrabalho", "IS", null)); 
			ordenacao.add(new Order("idPrestacaoContasViagem"));
			
			result = (List) super.findByCondition(condicao, ordenacao);
			if(result == null)
				return true;
		}
		return  false;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection findByCorrigirAndSolicitacao(Integer idSolicitacaoServico) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.EM_CORRECAO));
		condicao.add(new Condition("idItemTrabalho", "is", null));
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection findBySolicitacaoEmConferencia(Integer idSolicitacaoServico) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.EM_CONFERENCIA)); 
		condicao.add(new Condition("idItemTrabalho", "is not", null)); 
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}

	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public PrestacaoContasViagemDTO findBySolicitacaoAndTarefa(Integer idSolicitacaoServico, Integer idTarefa) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		List result = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("idItemTrabalho", "=", idTarefa));
		result = (List) findByCondition(condicao, ordenacao);
		if(result != null)
			return (PrestacaoContasViagemDTO) result.get(0);
		else
			return null;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public PrestacaoContasViagemDTO findNaoAprovados(Integer idSolicitacaoServico, Integer idTarefa) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		List result = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("idItemTrabalho", "is", null));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.EM_CORRECAO));
		result = (List) findByCondition(condicao, ordenacao);
		if(result != null){
			return (PrestacaoContasViagemDTO) result.get(0);
		}
		return null;
	}
	
	/**
	 * Recupera a prestação de contas conforme idtarefa e idsolicitacaoservico passados
	 * 
	 * @param idTarefa
	 * @param idSolicitacaoServico
	 * @return
	 * @throws Exception
	 */
	public PrestacaoContasViagemDTO findByTarefaAndSolicitacao(Integer idTarefa, Integer idSolicitacaoServico) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		List result = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("idItemTrabalho", "=", idTarefa));
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		result = (List) super.findByCondition(condicao, ordenacao);
		
		if(result != null)
			return (PrestacaoContasViagemDTO) result.get(0);
		else
			return null;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public boolean verificaSeTodasPrestacaoAprovadas(Integer idSolicitacao) throws PersistenceException {
		IntegranteViagemDao integrantesDao = new IntegranteViagemDao();
		Integer totalIntegrantes = integrantesDao.retornaQtdeIntegrantes(idSolicitacao);
		
		Collection<PrestacaoContasViagemDTO> colPrestacao = this.findBySolicitacao(idSolicitacao, null);
		if(colPrestacao != null){
			if(colPrestacao.size() != totalIntegrantes)
				return false;	
			for(PrestacaoContasViagemDTO prestacaoDto : colPrestacao){
				if(!prestacaoDto.getSituacao().equalsIgnoreCase(PrestacaoContasViagemDTO.APROVADA)){
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Retorna uma coleção de prestação de contas que esta na etapa de conferencia
	 * 
	 * @param idSolicitacaoServico
	 * @return
	 * @throws Exception
	 */
	public Collection findBySolicitacaoAndTaferaConferencia(Integer idSolicitacaoServico) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
        condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.AGUARDANDO_CONFERENCIA)); 
        condicao.add(new Condition("idItemTrabalho", "IS", null)); 
        ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection findBySolicitacaoAndTaferaCorrecao(Integer idSolicitacaoServico) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.EM_CORRECAO)); 
		condicao.add(new Condition("idItemTrabalho", "IS", null)); 
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection findBySolicitacaoEmpregadoSeCorrecao(Integer idSolicitacaoServico, Integer idEmpregado) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", PrestacaoContasViagemDTO.EM_CORRECAO)); 
		condicao.add(new Condition("idEmpregado", "=", idEmpregado)); 
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Retorna uma lista de prestação de contas conforme idtarefa passados
	 * 
	 * @param idTarefa
	 * @return
	 * @throws Exception
	 */
	public Collection findByTarefa(Integer idTarefa) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemTrabalho", "=", idTarefa)); 
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Retorna uma lista de prestação de contas conforme idsolicitacaoservico e situacao passados, mas que não tenha iditemtrabalho associado a ele
	 * 
	 * @param idSolicitacaoServico
	 * @param situacao
	 * @return
	 * @throws Exception
	 */
	public Collection findBySituacaoAndNull(Integer idSolicitacaoServico, String situacao) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", situacao));
		condicao.add(new Condition("idItemTrabalho", "is", null));
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection findBySituacaoAndNotNull(Integer idSolicitacaoServico, String situacao) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", situacao));
		condicao.add(new Condition("idItemTrabalho", "is not", null));
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Retorna uma lista de prestação de contas conforme idsolicitacaoservico e situacao passados
	 * 
	 * @param idSolicitacaoServico
	 * @param situacao
	 * @return
	 * @throws Exception
	 */
	public Collection findBySituacao(Integer idSolicitacaoServico, String situacao) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("situacao", "=", situacao));
		ordenacao.add(new Order("idPrestacaoContasViagem"));
		return  super.findByCondition(condicao, ordenacao);
	}
}
