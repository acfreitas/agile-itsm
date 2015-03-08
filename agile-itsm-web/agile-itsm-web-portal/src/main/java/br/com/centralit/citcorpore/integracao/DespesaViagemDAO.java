package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.DespesaViagemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"rawtypes","unchecked"})
public class DespesaViagemDAO extends CrudDaoDefaultImpl {
	

	public DespesaViagemDAO(){
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}
	
	@Override
	public String getTableName() {
		return this.getOwner() + "despesaviagem";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return DespesaViagemDTO.class;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("iddespesaviagem" ,"idDespesaViagem", true, true, false, false));
		listFields.add(new Field("datainicio" ,"dataInicio", false, false, false, false));
		listFields.add(new Field("datafim" ,"dataFim", false, false, false, false));
		listFields.add(new Field("idroteiro" ,"idRoteiro", false, false, false, false));
		listFields.add(new Field("idtipo" ,"idTipo", false, false, false, false));
		listFields.add(new Field("idparceiro" ,"idFornecedor", false, false, false, false));
		listFields.add(new Field("valor" ,"valor", false, false, false, false));
		listFields.add(new Field("validade" ,"validade", false, false, false, false));
		listFields.add(new Field("original" ,"original", false, false, false, false));
		listFields.add(new Field("idsolicitacaoservico" ,"idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("prestacaocontas" ,"prestacaoContas", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("quantidade" ,"quantidade", false, false, false, false));
		listFields.add(new Field("datahoracompra" ,"dataHoraCompra", false, false, false, false));
		listFields.add(new Field("idresponsavelcompra" ,"idResponsavelCompra", false, false, false, false));
		listFields.add(new Field("idmoeda" ,"idMoeda", false, false, false, false));
		listFields.add(new Field("idformapagamento" ,"idFormaPagamento", false, false, false, false));
		listFields.add(new Field("observacoes" ,"observacoes", false, false, false, false));
		
		return listFields;
	}
	
	/**
	 * Retorna a despesa do integrante com o idroteiro passado
	 * 
	 * @param idSolicitacaoServico
	 * @return
	 * @throws Exception
	 * @author renato.jesus
	 */
	public Collection<DespesaViagemDTO> findDespesaViagemByIdRoteiro(Integer idroteiro) throws PersistenceException {
		List condicao = new ArrayList();
		List result = new ArrayList<DespesaViagemDTO>();
		List ordenacao= new ArrayList();
		
		condicao.add(new Condition("idRoteiro", "=", idroteiro));
		ordenacao.add(new Order("idDespesaViagem"));
		
		result = (List) super.findByCondition(condicao, ordenacao);
		
		if(result != null && !result.isEmpty()) {
			return result;
		}
		
		return null;
	}
	
	/**
	 * Retorna a despesa do integrante com o idroteiro passado
	 * 
	 * @param idSolicitacaoServico
	 * @return
	 * @throws Exception
	 * @author renato.jesus
	 */
	public Collection<DespesaViagemDTO> findDespesaViagemByIdSolicitacao(Integer idSolicitacao) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		List result = new ArrayList<DespesaViagemDTO>();
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		condicao.add(new Condition("dataFim", "is", null));
		ordenacao.add(new Order("validade"));
		result = (List) super.findByCondition(condicao, ordenacao);
		
		if(result != null && !result.isEmpty()) {
			return result;
		}
		
		return null;
	}
	
	/**
	 * Retorna a despesa do integrante com o idroteiro e se permite ('S') ou não adiantamento ('N')
	 * 
	 * @param idRoteiro
	 * @param permiteAdiantamento
	 * @return
	 * @throws Exception
	 * @author renato.jesus
	 */
	public Collection<DespesaViagemDTO> findDespesaViagemByIdRoteiroAndPermiteAdiantamento(Integer idRoteiro, String permiteAdiantamento) throws PersistenceException {
		ArrayList parametros = new ArrayList();
		List list = new ArrayList();
		List fields = (ArrayList) this.getFields();
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT despesaviagem.* FROM ");
		sql.append("despesaviagem ");
		sql.append("LEFT JOIN tipomovimfinanceiraviagem ");
		sql.append("ON despesaviagem.idtipo=tipomovimfinanceiraviagem.idtipomovimfinanceiraviagem ");
		sql.append("WHERE despesaviagem.idroteiro=? ");
		sql.append("AND tipomovimfinanceiraviagem.permiteadiantamento=? ");
		
		parametros.add(idRoteiro);
		parametros.add(permiteAdiantamento);
		
		list = this.execSQL(sql.toString(), parametros.toArray());
		
		return listConvertion(DespesaViagemDTO.class, list, fields);
	}
	
	/**
	 * Busca o valor total da viagem pelo idsolicitacao, e calculado todo o valor desde os itens originais até os de remarcação
	 * 
	 * @param idSolicitacao
	 * @return
	 * @throws Exception
	 */
	public Double buscaValorTotalViagem(Integer idSolicitacao) throws PersistenceException {
		List condicao = new ArrayList();
		Collection<DespesaViagemDTO> result = new ArrayList<DespesaViagemDTO>();
		Double valorTotal = 0.0;
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		result = super.findByCondition(condicao, null);
		
		if(result != null && !result.isEmpty()) {
			for(DespesaViagemDTO dto: result){
				valorTotal = (dto.getValor()*dto.getQuantidade()) + valorTotal;
			}
		}
		return valorTotal;
	}
	
	public Double buscaValorTotalViagemAtivo(Integer idSolicitacao) throws PersistenceException {
		List condicao = new ArrayList();
		Collection<DespesaViagemDTO> result = new ArrayList<DespesaViagemDTO>();
		Double valorTotal = 0.0;
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		condicao.add(new Condition("dataFim", "is", null));
		result = super.findByCondition(condicao, null);
		
		if(result != null && !result.isEmpty()) {
			for(DespesaViagemDTO dto: result){
				valorTotal = (dto.getValor()*dto.getQuantidade()) + valorTotal;
			}
		}
		return valorTotal;
	}
	
	public Double buscaValorTotalViagemInativo(Integer idSolicitacao) throws PersistenceException {
		List condicao = new ArrayList();
		Collection<DespesaViagemDTO> result = new ArrayList<DespesaViagemDTO>();
		Double valorTotal = 0.0;
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		condicao.add(new Condition("dataFim", "is not", null));
		result = super.findByCondition(condicao, null);
		
		if(result != null && !result.isEmpty()) {
			for(DespesaViagemDTO dto: result){
				valorTotal = (dto.getValor()*dto.getQuantidade()) + valorTotal;
			}
		}
		return valorTotal;
	}
	
	/**
	 * Busca o valor de historico da viagem, é calculado apenas o valor dos itens originais
	 * 
	 * @param idSolicitacao
	 * @return
	 * @throws Exception
	 */
	public Double buscaValorViagemHistorico(Integer idSolicitacao) throws PersistenceException {
		Collection<DespesaViagemDTO> result = new ArrayList<DespesaViagemDTO>();
		Double valorTotal = 0.0;
		
		ArrayList parametros = new ArrayList();
		List list = new ArrayList();
		List fields = (ArrayList) this.getFields();
		
		StringBuilder sql = new StringBuilder();
		sql.append("select despesaviagem.* from despesaviagem where idsolicitacaoservico=? and (situacao<>'Remarcacao' or situacao is null);");
		
		parametros.add(idSolicitacao);
		
		list = this.execSQL(sql.toString(), parametros.toArray());
		result = listConvertion(DespesaViagemDTO.class, list, fields);
		
		if(result != null && !result.isEmpty()) {
			for(DespesaViagemDTO dto: result){
				valorTotal = (dto.getValor()*dto.getQuantidade()) + valorTotal;
			}
		}
		
		return valorTotal;
	}
	
	/**
	 * Lista todos os itens ligado ao idsolicitacao e idroteiro passado
	 * 
	 * @param idSolicitacao
	 * @param idRoteiro
	 * @return
	 * @throws Exception
	 */
	public Collection<DespesaViagemDTO> listaItensCompra(Integer idSolicitacao, Integer idRoteiro) throws PersistenceException {
		List condicao = new ArrayList();
		Collection<DespesaViagemDTO> result = new ArrayList<DespesaViagemDTO>();
		
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacao));
		condicao.add(new Condition("idRoteiro", "=", idRoteiro));
		result = super.findByCondition(condicao, null);
		
		if(result != null && !result.isEmpty()) {
			return result;
		}
		
		return null;
	}
	

	/**
	 * Retorna a despesa com datafim null do integrante com o idroteiro passado
	 * 
	 * @param idSolicitacaoServico
	 * @return
	 * @throws Exception
	 * @author thiago.borges	
	 */
	public Collection<DespesaViagemDTO> findDespesasAtivasViagemByIdRoteiro(Integer idroteiro) throws PersistenceException {
		List condicao = new ArrayList();
		List result = new ArrayList<DespesaViagemDTO>();
		
		condicao.add(new Condition("idRoteiro", "=", idroteiro));
		condicao.add(new Condition("dataFim", "is", null));
		result = (List) super.findByCondition(condicao, null);
		
		if(result != null && !result.isEmpty()) {
			return result;
		}
		
		return null;
	}
	
	/**
	 * Retorna todas as despesas relacionadas ao roteiro passado
	 * 
	 * @param idRoteiro
	 * @return
	 * @throws Exception
	 * @author thiago.borges	
	 */
	public Collection<DespesaViagemDTO> findTodasDespesasViagemByIdRoteiro(Integer idroteiro) throws PersistenceException {
		List condicao = new ArrayList();
		List result = new ArrayList<DespesaViagemDTO>();
		
		condicao.add(new Condition("idRoteiro", "=", idroteiro));
		result = (List) super.findByCondition(condicao, null);
		
		if(result != null && !result.isEmpty()) {
			return result;
		}
		
		return null;
	}
	
	/**
	 * TODO Este metodo esta em desuso, pode ser removido na proxima versão
	 */
	public Collection<DespesaViagemDTO> findHitoricoDespesaViagemByIdRoteiro(Integer idroteiro) throws PersistenceException {
		List condicao = new ArrayList();
		List result = new ArrayList<DespesaViagemDTO>();
		
		condicao.add(new Condition("idRoteiro", "=", idroteiro));
		condicao.add(new Condition("dataFim", "is not", null));
		result = (List) super.findByCondition(condicao, null);
		
		if(result != null && !result.isEmpty()) {
			return result;
		}
		
		return null;
	}
	
	/**
	 * Retorna o valor total apenas dos itens que exigem prestação de contas
	 * 
	 * @param idSolicitacao
	 * @param idEmpregado
	 * @return
	 * @throws Exception
	 */
	public Double getTotalDespesaViagemPrestacaoContas(Integer idSolicitacao, Integer idEmpregado) throws PersistenceException {
		ArrayList parametros = new ArrayList();
		List list = new ArrayList();
		List fields = (ArrayList) this.getFields();
		Double valorTotal = 0.0;
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT despesaviagem.* FROM ");
		sql.append("despesaviagem ");
		sql.append("LEFT JOIN tipomovimfinanceiraviagem ");
		sql.append("ON despesaviagem.idtipo=tipomovimfinanceiraviagem.idtipomovimfinanceiraviagem ");
		sql.append("LEFT JOIN integranteviagem ");
		sql.append("ON integranteviagem.idempregado=? ");
		sql.append("LEFT JOIN roteiroviagem ");
		sql.append("ON integranteviagem.idintegranteviagem=roteiroviagem.idintegrante ");
		sql.append("WHERE despesaviagem.idsolicitacaoservico=? ");
		sql.append("AND despesaviagem.idroteiro=roteiroviagem.idroteiroviagem ");
		sql.append("AND tipomovimfinanceiraviagem.exigeprestacaoconta='S'");
		
		parametros.add(idEmpregado);
		parametros.add(idSolicitacao);
		
		list = this.execSQL(sql.toString(), parametros.toArray());
		
		Collection<DespesaViagemDTO> colDespesaViagem = listConvertion(DespesaViagemDTO.class, list, fields);
		
		if(colDespesaViagem != null && !colDespesaViagem.isEmpty()) {
			for(DespesaViagemDTO despesaViagemDTO : colDespesaViagem){
				valorTotal = (despesaViagemDTO.getValor()*despesaViagemDTO.getQuantidade()) + valorTotal;
			}
		}
		
		return valorTotal;
	}
}
