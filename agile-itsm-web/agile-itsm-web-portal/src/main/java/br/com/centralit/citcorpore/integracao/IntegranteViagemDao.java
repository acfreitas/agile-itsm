package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class IntegranteViagemDao extends CrudDaoDefaultImpl {
	
	public IntegranteViagemDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	
	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idintegranteviagem", "idIntegranteViagem", true, true, false, false));
		listFields.add(new Field("idsolicitacaoservico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idempregado", "idEmpregado", false, false, false, false));
		listFields.add(new Field("idrespprestacaocontas", "idRespPrestacaoContas", false, false, false, false));
		listFields.add(new Field("integrantefuncionario", "integranteFuncionario", false, false, false, false));
		listFields.add(new Field("remarcacao", "remarcacao", false, false, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));
		listFields.add(new Field("iditemtrabalho", "idItemTrabalho", false, false, false, false));
		listFields.add(new Field("emprestacaocontas", "emPrestacaoContas", false, false, false, false));
		listFields.add(new Field("idtarefa", "idTarefa", false, false, false, false));
		listFields.add(new Field("infonaofuncionario", "infoNaoFuncionario", false, false, false, false));
		listFields.add(new Field("estado", "estado", false, false, false, false));
		
		return listFields;
	}

	@Override
	public String getTableName() {
		return "integranteviagem";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return IntegranteViagemDTO.class;
	}
	
	/**
	 * Retorna uma coleção de todos os integrante ligados a idsolicitacao
	 * 
	 * @param idSolicitacao
	 * @return
	 * @throws Exception
	 */
	public Collection<IntegranteViagemDTO> findAllByIdSolicitacao(Integer idSolicitacao) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
//		condicao.add(new Condition(Condition.AND, "remarcacao", "=", "N"));
		return super.findByCondition(condicao, null);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection<IntegranteViagemDTO> findAllByIdSolicitacaoRemarcacaoNao(Integer idSolicitacao) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		condicao.add(new Condition(Condition.AND, "remarcacao", "=", "N"));
		return super.findByCondition(condicao, null);
	}
	
	/**
	 * Retorna uma coleção de integrante que foram remarcados e estão ligados a idsolicitacao
	 * 
	 * @param idSolicitacao
	 * @return
	 * @throws Exception
	 */
	public Collection<IntegranteViagemDTO> findAllRemarcacaoByIdSolicitacao(Integer idSolicitacao) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		condicao.add(new Condition(Condition.AND, "remarcacao", "=", "S"));
		return super.findByCondition(condicao, null);
	}
	
	/**
	 * Busca um coleção de integrantes que não estão na etapa de prestação de contas com base no idsolicitacao passado
	 * 
	 * @param idSolicitacao
	 * @return
	 * @throws Exception
	 */
	public Collection<IntegranteViagemDTO> findAllPrestacaoContasByIdSolicitacao(Integer idSolicitacao) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		condicao.add(new Condition(Condition.AND, "remarcacao", "=", "N"));
		condicao.add(new Condition(Condition.AND, "emPrestacaoContas", "<>", "S"));
		/*condicao.add(new Order("idintegranteviagem"));*/
		return super.findByCondition(condicao, null);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection<IntegranteViagemDTO> findAllPrestacaoContasByIdSolicitacaoTarefa(Integer idSolicitacao, Integer idTarefa) throws PersistenceException {
		ArrayList parametros = new ArrayList();
		List list = new ArrayList();
		List fields = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT idintegranteviagem, idsolicitacaoservico,  idempregado, idrespprestacaocontas ");
		sql.append("FROM   integranteviagem ");
		sql.append("WHERE  idsolicitacaoservico = ? ");
		sql.append("AND idtarefa IS NULL");
		
		parametros.add(idSolicitacao);
		list = this.execSQL(sql.toString(), parametros.toArray());
		fields.add("idIntegranteViagem");
		fields.add("idSolicitacaoServico");
		fields.add("idEmpregado");
		fields.add("idRespPrestacaoContas");

		return  listConvertion(IntegranteViagemDTO.class, list, fields );
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public IntegranteViagemDTO findByIdSolicitacaoServicoIdEmpregado(Integer idSolicitacao, Integer idEmpregado) throws PersistenceException {
		ArrayList parametros = new ArrayList();
		List list = new ArrayList();
		List fields = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT * ");
		sql.append("FROM   integranteviagem ");
		sql.append("WHERE  idsolicitacaoservico = ? ");
		sql.append("AND idempregado = ?");
		
		parametros.add(idSolicitacao);
		parametros.add(idEmpregado);
		list = this.execSQL(sql.toString(), parametros.toArray());
		fields.add("idIntegranteViagem");
		fields.add("idSolicitacaoServico");
		fields.add("idEmpregado");
		fields.add("idRespPrestacaoContas");
		fields.add("idTarefa");

		Collection<IntegranteViagemDTO> integranteViagemDTOs = listConvertion(IntegranteViagemDTO.class, list, fields );
		if(integranteViagemDTOs != null){
			for(IntegranteViagemDTO integranteViagemDTO: integranteViagemDTOs){
				integranteViagemDTO = (IntegranteViagemDTO) restore(integranteViagemDTO);
				return integranteViagemDTO;
			}
		}
		
		return null;
		
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection<IntegranteViagemDTO> findAllByIdSolicitacaoAndViagemRemarcada(SolicitacaoServicoDTO solicitacaoServicoDTO) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition(Condition.AND, "idSolicitacaoServico", "=", solicitacaoServicoDTO.getIdSolicitacaoServico()));
		condicao.add(new Condition(Condition.AND, "remarcacao", "=", "S"));
		return super.findByCondition(condicao, null);
	}
	
	/**
	 * Atualiza os dados de remarcação do integrante com base no integranteDTO passado 
	 * 
	 * @param integrante
	 * @return
	 * @throws Exception
	 */
	public Boolean updateBySolicitacaoEmpregado(IntegranteViagemDTO integrante)throws PersistenceException {
		List parametros = new ArrayList();

		String sql = "update " + getTableName() + " set remarcacao = ? where idSolicitacaoServico = ? ";
		
		parametros.add(integrante.getRemarcacao());
		parametros.add(integrante.getIdSolicitacaoServico());
		
		if (integrante.getIdEmpregado() != null && integrante.getIdEmpregado() > 0) {
			sql += " and idEmpregado = ? ";
			parametros.add(integrante.getIdEmpregado());
		}
		
		try {
			super.execUpdate(sql, parametros.toArray());
			return true;
		} catch (PersistenceException e) {
			return false;
		}		
	}	
	
	/**
	 * Verifica se houve remarcação para a solicitacao passada
	 * 
	 * @param idsolicitacaoservico
	 * @return
	 * @throws PersistenceException
	 */
	public boolean isHouveRemarcacaoViagem(Integer idsolicitacaoservico) throws PersistenceException{
		ArrayList parametros = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT DISTINCT i.iditemtrabalho, ");
		sql.append("                i.idinstancia, ");
		sql.append("                aff.idusuario, ");
		sql.append("                aff.tipo, ");
		sql.append("                el.template, ");
		sql.append("                i.situacao, ");
		sql.append("                inte.idempregado ");
		sql.append("FROM   bpm_atribuicaofluxo aff ");
		sql.append("       INNER JOIN bpm_itemtrabalhofluxo i ");
		sql.append("               ON aff.iditemtrabalho = i.iditemtrabalho ");
		sql.append("       INNER JOIN bpm_elementofluxo el ");
		sql.append("               ON el.idelemento = i.idelemento ");
		sql.append("       INNER JOIN execucaosolicitacao ex ");
		sql.append("               ON ex.idinstanciafluxo = i.idinstancia ");
		sql.append("       INNER JOIN integranteviagem inte ");
		sql.append("               ON inte.idtarefa = i.iditemtrabalho ");
		sql.append("       INNER JOIN itemcontrolefinanceiroviagem item ");
		sql.append("               ON item.idempregado = inte.idempregado ");
		sql.append("                  AND item.idsolicitacaoservico = ex.idsolicitacaoservico ");
		sql.append("       INNER JOIN controlefinanceiroviagem contr ");
		sql.append("               ON contr.idcontrolefinanceiroviagem = ");
		sql.append("                  item.idcontrolefinanceiroviagem ");
		sql.append("WHERE  ex.idsolicitacaoservico = ? ");
		sql.append("       AND contr.iditemtrabalho IS NULL ");
		sql.append("       AND inte.remarcacao = ? ");
		sql.append("       AND el.template = ? ");
		sql.append("       AND item.datafim IS NULL ");
		sql.append("       AND aff.tipo <> 'Acompanhamento' ");
		sql.append("       AND i.situacao <> 'Cancelado' ");
		sql.append("ORDER  BY aff.idatribuicao DESC ");

		parametros.add(idsolicitacaoservico);
		parametros.add("S");		
		parametros.add("PrestacaoContasViagem");
		list = this.execSQL(sql.toString(), parametros.toArray());
		
		if(list!=null && !list.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Retorna uma coleção de integrantes com base nos dados de idsolicitacaoservico e idempregado do integranteDTO passado
	 * 
	 * @param integrante
	 * @return
	 * @throws Exception
	 */
	public Collection<IntegranteViagemDTO> restoreByIntegranteSolicitacao(IntegranteViagemDTO integrante) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();		
		condicao.add(new Condition("idSolicitacaoServico", "=", integrante.getIdSolicitacaoServico()));
		condicao.add(new Condition(Condition.AND, "idEmpregado", "=", integrante.getIdEmpregado()));
		ordenacao.add(new Order("idEmpregado"));		
		return super.findByCondition(condicao, ordenacao);
	}	
	
	/**
	 * Verifica se o integrante existe com base no idsolicitacao e idempregado passados
	 * 
	 * @param idsolicitacaoServico
	 * @param idEmpregado
	 * @return
	 * @throws Exception
	 */
	public boolean verificarSeIntegranteViagemExiste(Integer idsolicitacaoServico,Integer idEmpregado) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idsolicitacaoServico));
		condicao.add(new Condition("idEmpregado", "=", idEmpregado));
		condicao.add(new Condition(Condition.AND, "remarcacao", "=", "N"));
		List lista = (List) super.findByCondition(condicao, null);
		if(lista!=null && !lista.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Integer retornaQtdeIntegrantes(Integer idSolicitacao) throws PersistenceException {
		
		Collection<IntegranteViagemDTO> listaIntegrantes = this.findAllByIdSolicitacao(idSolicitacao);
		
		if(listaIntegrantes == null)
			return new Integer(0);
		else
			return listaIntegrantes.size();
		
	}
	
	/**
	 * Recupera uma coleção de integrantes ornenados pelo idempregado com base no idsolicitacaoservico e idempregado passados
	 * 
	 * @param idsolicitacaoServico
	 * @param idEmpregado
	 * @return
	 * @throws Exception
	 */
	public Collection<IntegranteViagemDTO> recuperaIntegranteByEmpregado(Integer idsolicitacaoServico,Integer idEmpregado) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idsolicitacaoServico));
		condicao.add(new Condition(Condition.AND, "idEmpregado", "=", idEmpregado));
		//condicao.add(new Condition(Condition.AND, "remarcacao", "=", "N"));
		ordenacao.add(new Order("idEmpregado"));
		
		return super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Recupera uma coleção de integrantes ordenados por idempregado com base no idsolicitacaoservico e idresponsavel passados
	 * 
	 * @param idsolicitacaoServico
	 * @param idResponsavel
	 * @return
	 * @throws Exception
	 */
	public Collection<IntegranteViagemDTO> recuperaIntegranteByResponsavel(Integer idsolicitacaoServico,Integer idResponsavel) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idsolicitacaoServico));
		condicao.add(new Condition(Condition.AND, "idRespPrestacaoContas", "=", idResponsavel));
		//condicao.add(new Condition(Condition.AND, "remarcacao", "=", "N"));
		ordenacao.add(new Order("idEmpregado"));
		
		return super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection<IntegranteViagemDTO> recuperaIntegranteByNomeNaoFuncionario(Integer idsolicitacaoServico, String nomeNaoFuncionario) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idsolicitacaoServico));
		condicao.add(new Condition(Condition.AND, "nomeNaoFuncionario", "=", nomeNaoFuncionario));
		//condicao.add(new Condition(Condition.AND, "remarcacao", "=", "N"));
		ordenacao.add(new Order("idEmpregado"));
		
		return super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Recupera um integrante com base no idsolicitacaoservico e idempregado passado
	 * 
	 * @param idsolicitacaoServico
	 * @param idEmpregado
	 * @return
	 * @throws Exception
	 */
	public IntegranteViagemDTO recuperaIntegrante(Integer idsolicitacaoServico,Integer idEmpregado) throws PersistenceException {
		
		List result = new ArrayList<IntegranteViagemDTO>();
		
		result = (List) recuperaIntegranteByEmpregado(idsolicitacaoServico, idEmpregado);
		if(result != null){
			return (IntegranteViagemDTO) result.get(0);
		}else{
			Integer idResponsavel = idEmpregado;
			result =  (List) recuperaIntegranteByResponsavel(idsolicitacaoServico, idResponsavel);
			if(result!=null){
				return (IntegranteViagemDTO) result.get(0);
			}
		}
		
		return null;
	}
	
	/**
	 * Recupera um integrante com base no idsolicitacaoservico e idtarefa passado
	 * 
	 * @param idSolicitacaoServico
	 * @param idTarefa
	 * @return
	 * @throws Exception
	 */
	public IntegranteViagemDTO getIntegranteByIdSolicitacaoAndTarefa(Integer idSolicitacaoServico, Integer idTarefa) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		List result = new ArrayList<IntegranteViagemDTO>();
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition(Condition.AND, "idTarefa", "=", idTarefa));
		ordenacao.add(new Order("idEmpregado"));
		result = (List) super.findByCondition(condicao, ordenacao);
		
		if(result != null && !result.isEmpty()) {
			return (IntegranteViagemDTO) result.get(0);
		}
		
		return null;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public IntegranteViagemDTO recuperaIntegrante(Integer idsolicitacaoServico,Integer idEmpregado,String funcionario) throws PersistenceException {
		
		List result = new ArrayList<IntegranteViagemDTO>();
		
		if(funcionario == null)
			return null;
		
		if(funcionario.equals("S")){
			
			//Recupera pelo empregado
			
			result = (List) recuperaIntegranteByEmpregado(idsolicitacaoServico, idEmpregado);
			if(result != null)
				return (IntegranteViagemDTO) result.get(0);			
			
		} else {
			
			//Recupera pelo responsavel
			result =  (List) recuperaIntegranteByResponsavel(idsolicitacaoServico, idEmpregado);
			if(result != null)
				return (IntegranteViagemDTO) result.get(0);
			
		}
		
		return null;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public IntegranteViagemDTO recuperaIntegranteNaoFuncionario(Integer idsolicitacaoServico, String nomeNaoFuncionario) throws PersistenceException {
		
		List result = new ArrayList<IntegranteViagemDTO>();
		
		result = (List) recuperaIntegranteByNomeNaoFuncionario(idsolicitacaoServico, nomeNaoFuncionario);
		if(result != null){
			return (IntegranteViagemDTO) result.get(0);
		}
		
		return null;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public IntegranteViagemDTO findById(Integer idIntegranteViagem) throws PersistenceException {
		
		List condicao = new ArrayList();
		
		condicao.add(new Condition("idIntegranteViagem", "=", idIntegranteViagem));
		
		List result = new ArrayList<IntegranteViagemDTO>();
		
		result = (List) super.findByCondition(condicao, null);
				
		if(result != null && !result.isEmpty())
			return (IntegranteViagemDTO) result.get(0);
		else
			return null;
		
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public void atualizarIdItemTrabalho(Integer idTarefa, Integer idSolicitacaoServico){
		
		List parametros = new ArrayList();
		
		String sql = "update " + getTableName() + " set iditemtrabalho = ? "
				+ " where remarcacao = 'S' and idsolicitacaoservico = ?";
		
		parametros.add(idTarefa);
		parametros.add(idSolicitacaoServico);
		
		try {
			super.execUpdate(sql, parametros.toArray());
		} catch (PersistenceException e) {
			return;
		}				
		
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public void atualizarRemarcacaoDoIntegrante(Integer idIntegrante){
		
		List parametros = new ArrayList();

		String sql = "update " + getTableName() + " set remarcacao = 'N', emprestacaocontas = 'N', idtarefa = null "
				+ " where remarcacao = 'S' and idintegranteviagem = ?";
		
		parametros.add(idIntegrante);
		
		try {
			super.execUpdate(sql, parametros.toArray());
		} catch (PersistenceException e) {
			return;
		}				
		
	}
	
	/**
	 * 
	 * 
	 * @param integranteViagemDTO
	 * @param eOu
	 * @return
	 * @throws Exception
	 */
	public Collection<IntegranteViagemDTO> findAllIntegrantesParaRemarcacao(IntegranteViagemDTO integranteViagemDTO, String eOu) throws PersistenceException {
		ArrayList parametros = new ArrayList();
		List list = new ArrayList();
		List fields = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct iv.idintegranteviagem, iv.nome, iv.estado ");
		sql.append("FROM   integranteviagem  iv ");
		sql.append("inner join roteiroviagem rt on rt.idintegrante = iv.idintegranteviagem ");
		sql.append("WHERE (iv.estado = 'Aguardando Adiantamento' or  iv.estado = 'Aguardando Prestação de Contas')");
		sql.append("and rt.volta >= CURDATE() ");
		sql.append("and rt.datafim is null ");
		
		if(integranteViagemDTO.getIdSolicitacaoServico() != null && !integranteViagemDTO.getIdSolicitacaoServico().equals("")){
			sql.append("AND iv.idsolicitacaoservico = ? ");
			parametros.add(integranteViagemDTO.getIdSolicitacaoServico());
		}
		
		if(integranteViagemDTO.getIdEmpregado() != null && !integranteViagemDTO.getIdEmpregado().equals("")){
			sql.append("AND iv.idempregado = ?");
			parametros.add(integranteViagemDTO.getIdEmpregado());
		}else{
			if(integranteViagemDTO.getNomeEmpregado() != null && !integranteViagemDTO.getNomeEmpregado().equalsIgnoreCase("")){
				sql.append("AND iv.nome like '%"+integranteViagemDTO.getNomeEmpregado()+"%'");
			}
		}
		
		if(integranteViagemDTO.getDataInicio() != null && integranteViagemDTO.getDataFim() != null){
			if(integranteViagemDTO.getDataInicio() != null && integranteViagemDTO.getDataInicioAux() != null){
				sql.append("and ( (rt.ida >= ? ");
				sql.append("and rt.ida <= ?) ");
				
				parametros.add(integranteViagemDTO.getDataInicio());
				parametros.add(integranteViagemDTO.getDataInicioAux());
			}
			
			if(integranteViagemDTO.getDataFim() != null){
				if(eOu != null && eOu.equalsIgnoreCase("or")){
					sql.append("or (rt.volta >= ? ");
					sql.append("and rt.volta <= ?)) ");
				}else{
					sql.append("and (rt.volta >= ? ");
					sql.append("and rt.volta <= ?)) ");
				}
				
				parametros.add(integranteViagemDTO.getDataFim());
				parametros.add(integranteViagemDTO.getDataFimAux());
			}
		}else{
			if(integranteViagemDTO.getDataInicio() != null && integranteViagemDTO.getDataInicioAux() != null){
				sql.append("and (rt.ida >= ? ");
				sql.append("and rt.ida <= ?) ");
				
				parametros.add(integranteViagemDTO.getDataInicio());
				parametros.add(integranteViagemDTO.getDataInicioAux());
			}
			
			if(integranteViagemDTO.getDataFim() != null && integranteViagemDTO.getDataFimAux() != null){
				sql.append("and (rt.volta >= ? ");
				sql.append("and rt.volta <= ?) ");
				
				parametros.add(integranteViagemDTO.getDataFim());
				parametros.add(integranteViagemDTO.getDataFimAux());
			}
		}
		
		list = this.execSQL(sql.toString(), parametros.toArray());
		fields.add("idIntegranteViagem");
		fields.add("nome");
		fields.add("estado");

		return  listConvertion(IntegranteViagemDTO.class, list, fields );
	}
	
	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
				super.updateNotNull(obj);
	}

	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemByCompras(Integer idSolicitacaoServico) throws PersistenceException {

		List condicao = new ArrayList();
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("estado", "=", "Aguardando Financeiro"));
		
		List result = new ArrayList<IntegranteViagemDTO>();
		
		result = (List) super.findByCondition(condicao, null);
				
		if(result != null && !result.isEmpty())
			return result;
		else
			return null;
	}

	/**
	 * 
	 * 
	 * @param idSolicitacao
	 * @param estado
	 * @return
	 * @throws Exception
	 */
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemByIdSolicitacaoEstado(Integer idSolicitacao, String estado) throws PersistenceException {
		
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		condicao.add(new Condition("estado", "=", estado));
		
		List result = new ArrayList<IntegranteViagemDTO>();
		result = (List) super.findByCondition(condicao, null);
		
		if(result != null && !result.isEmpty())
			return result;
		else
			return null;
	}
	
	/**
	 * Recupera uma coleção de integrantes que podem estar ou não na etapa de prestacao de contas depende do parametro "prestContas" e do "estado" passados. 
	 * 
	 * @param idSolicitacao
	 * @param estado
	 * @param prestContas
	 * @return
	 * @throws Exception
	 */
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemByIdSolicitacaoEstadoPrestConta(Integer idSolicitacao, String estado, String prestContas) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		condicao.add(new Condition(Condition.AND, "remarcacao", "=", "N"));
		condicao.add(new Condition(Condition.AND, "emPrestacaoContas", "=", prestContas));
		condicao.add(new Condition(Condition.AND, "estado", "=", estado));
		condicao.add(new Condition(Condition.AND, "idTarefa", "is", null));
		/*condicao.add(new Order("idintegranteviagem"));*/
		return super.findByCondition(condicao, null);
	}
	
	/**
	 * Busca o integrante pelo idsolicitacao e idtarefa passados 
	 * 
	 * @param idSolicitacao
	 * @param idTarefa
	 * @return
	 * @throws Exception
	 */
	public IntegranteViagemDTO findByIdSolicitacaoServicoIdTarefa(Integer idSolicitacao, Integer idTarefa) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();		
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		condicao.add(new Condition(Condition.AND, "idTarefa", "=", idTarefa));
		ordenacao.add(new Order("idIntegranteViagem"));	
		
		List result = new ArrayList<IntegranteViagemDTO>();
		result = (List) super.findByCondition(condicao, null);

		if(result != null && !result.isEmpty())
			return (IntegranteViagemDTO) result.get(0);
		else
			return null;
	}	
}