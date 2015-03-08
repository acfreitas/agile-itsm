package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AprovacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.AprovacaoPropostaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class AprovacaoMudancaDao extends CrudDaoDefaultImpl {

	public AprovacaoMudancaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
				return null;
	}
	
	private static final String SQL_DELETE_BY_ID_MUDANCA = 
	          "delete from  APROVACAOMUDANCA where idRequisicaoMudanca = ? and idhistoricomudanca is null ";

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAprovacaoMudanca", "idAprovacaoMudanca", true, true, false, false));
		listFields.add(new Field("idRequisicaoMudanca", "idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("idEmpregado", "idEmpregado", false, false, false, false));
		listFields.add(new Field("nomeEmpregado", "nomeEmpregado", false, false, false, false));
		listFields.add(new Field("voto", "voto", false, false, false, false));
		listFields.add(new Field("comentario", "comentario", false, false, false, false));
		listFields.add(new Field("datahorainicio", "dataHoraInicio", false, false, false, false));
		listFields.add(new Field("datahoravotacao", "datahoravotacao", false, false, false, false));
		listFields.add(new Field("idhistoricomudanca", "idHistoricoMudanca", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return "APROVACAOMUDANCA";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return AprovacaoMudancaDTO.class;
	}

	public Collection<AprovacaoMudancaDTO> listaAprovacaoMudancaPorIdRequisicaoMudanca(Integer idRequisicaoMudanca,Integer idGrupo, Integer idEmpregado) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct (aprovacaomudanca.idempregado), aprovacaomudanca.comentario, aprovacaomudanca.voto, aprovacaomudanca.idrequisicaomudanca,aprovacaomudanca.idaprovacaomudanca,empregados.nome,aprovacaomudanca.datahorainicio, aprovacaomudanca.datahoravotacao  ");
		sql.append("FROM " + getTableName() + " ");
		sql.append("inner join gruposempregados on gruposempregados.idempregado = aprovacaomudanca.idempregado ");
		sql.append("inner join empregados on empregados.idempregado = aprovacaomudanca.idempregado ");
		sql.append(" WHERE  aprovacaomudanca.idrequisicaomudanca = ? and idhistoricomudanca is null ");
		parametro.add(idRequisicaoMudanca);
		/*if(idGrupo!=null){
			sql.append("and gruposempregados.idgrupo = ?");
			parametro.add(idGrupo);
		}*/
		
		//parametro.add(idEmpregado);
		
		
		
		
		List lista = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idEmpregado");
		listRetorno.add("comentario");
		listRetorno.add("voto");
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("idAprovacaoMudanca");
		listRetorno.add("nomeEmpregado");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("dataHoraVotacao");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}
	public Collection<AprovacaoMudancaDTO> listByIdHistoricoMudanca(Integer idHistoricoMudanca) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct (aprovacaomudanca.idempregado), aprovacaomudanca.comentario, aprovacaomudanca.voto, aprovacaomudanca.idrequisicaomudanca,aprovacaomudanca.idaprovacaomudanca,empregados.nome,aprovacaomudanca.datahorainicio, aprovacaomudanca.datahoravotacao  ");
		sql.append("FROM " + getTableName() + " ");
		sql.append("inner join gruposempregados on gruposempregados.idempregado = aprovacaomudanca.idempregado ");
		sql.append("inner join empregados on empregados.idempregado = aprovacaomudanca.idempregado ");
		sql.append(" WHERE  aprovacaomudanca.idhistoricomudanca = ? ");
		parametro.add(idHistoricoMudanca);
		/*if(idGrupo!=null){
			sql.append("and gruposempregados.idgrupo = ?");
			parametro.add(idGrupo);
		}*/
		
		//parametro.add(idEmpregado);
		
		
		
		
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		
		List listRetorno = new ArrayList();
		listRetorno.add("idEmpregado");
		listRetorno.add("comentario");
		listRetorno.add("voto");
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("idAprovacaoMudanca");
		listRetorno.add("nomeEmpregado");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("dataHoraVotacao");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}
	
	public Collection<AprovacaoMudancaDTO> listaAprovacaoMudancaPorIdRequisicaoMudancaEHistorico(Integer idRequisicaoMudanca,Integer idGrupo, Integer idEmpregado) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT distinct (aprovacaomudanca.idempregado), aprovacaomudanca.comentario, aprovacaomudanca.voto, aprovacaomudanca.idrequisicaomudanca,aprovacaomudanca.idaprovacaomudanca,empregados.nome,aprovacaomudanca.datahorainicio, aprovacaomudanca.datahoravotacao  ");
		sql.append("FROM " + getTableName() + " ");
		sql.append("inner join gruposempregados on gruposempregados.idempregado = aprovacaomudanca.idempregado ");
		sql.append("inner join empregados on empregados.idempregado = aprovacaomudanca.idempregado ");
		sql.append(" WHERE  aprovacaomudanca.idrequisicaomudanca = ? and idhistoricomudanca is null ");
		parametro.add(idRequisicaoMudanca);
		/*if(idGrupo!=null){
			sql.append("and gruposempregados.idgrupo = ?");
			parametro.add(idGrupo);
		}*/
		
		//parametro.add(idEmpregado);
		
		
		
		
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		
		List listRetorno = new ArrayList();
		listRetorno.add("idEmpregado");
		listRetorno.add("comentario");
		listRetorno.add("voto");
		listRetorno.add("idRequisicaoMudanca");
		listRetorno.add("idAprovacaoMudanca");
		listRetorno.add("nomeEmpregado");
		listRetorno.add("dataHoraInicio");
		listRetorno.add("dataHoraVotacao");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}

	public void deleteByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws PersistenceException {
		/*List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));
		super.deleteByCondition(lstCondicao);*/
		super.execUpdate(SQL_DELETE_BY_ID_MUDANCA, new Object[]{idRequisicaoMudanca});
	}

	public void updateByIdRequisicaoMudanca(AprovacaoMudancaDTO aprovacaoMudancaDto) throws PersistenceException {
		super.updateNotNull(aprovacaoMudancaDto);
	}
	
	
	public void deleteLinha(Integer idRequisicaoMudanca, Integer idEmpregado) throws PersistenceException {
//		List parametro = new ArrayList();
//		parametro.add(idRequisicaoMudanca);
//		String sql = "DELETE FROM aprovacaomudanca where (datahoravotacao = 'Ainda não votou.' and idrequisicaomudanca = ?)";
//		this.execSQL(sql.toString(), parametro.toArray());
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));
		lstCondicao.add(new Condition("idEmpregado", "=", idEmpregado));
		lstCondicao.add(new Condition("idHistoricoMudanca", "is", null));
		super.deleteByCondition(lstCondicao);
	}

	public Integer quantidadeAprovacaoMudancaPorVotoAprovada(AprovacaoMudancaDTO aprovacao,Integer idGrupo) throws PersistenceException {

		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select count(voto) from  aprovacaomudanca ");
		/*if(idGrupo !=null){
			sql.append("inner join gruposempregados on gruposempregados.idempregado = aprovacaomudanca.idempregado ");
		}*/
		sql.append(" where idrequisicaomudanca = ?  and voto = ? and idhistoricomudanca is null");
		parametro.add(aprovacao.getIdRequisicaoMudanca());
		parametro.add(aprovacao.getVoto());
		/*if(idGrupo !=null){
			sql.append("and gruposempregados.idgrupo = ?");
			parametro.add(idGrupo);
		}*/
		
		

		List list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("quantidadeVotoAprovada");

		if (list != null && !list.isEmpty()) {

			AprovacaoMudancaDTO aprovacaoMudancaDto = (AprovacaoMudancaDTO) this.engine.listConvertion(AprovacaoMudancaDTO.class, list, listRetorno).get(0);

			return aprovacaoMudancaDto.getQuantidadeVotoAprovada();
			
			//return list.size();

		} else {
			return new Integer(0);
		}

	}

	public Integer quantidadeAprovacaoMudancaPorVotoRejeitada(AprovacaoMudancaDTO aprovacao,Integer idGrupo) throws PersistenceException {

		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select count(voto) from  aprovacaomudanca  ");
		/*if(idGrupo !=null){
			sql.append("inner join gruposempregados on gruposempregados.idempregado = aprovacaomudanca.idempregado ");
		}*/
		sql.append(" where idrequisicaomudanca = ?  and voto = ? and idhistoricomudanca is null");
		parametro.add(aprovacao.getIdRequisicaoMudanca());
		parametro.add(aprovacao.getVoto());
		/*if(idGrupo !=null){
			sql.append("and gruposempregados.idgrupo = ?");
			parametro.add(idGrupo);
		}*/
		
		

		List list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("quantidadeVotoRejeitada");

		if (list != null && !list.isEmpty()) {

			AprovacaoMudancaDTO aprovacaoMudancaDto = (AprovacaoMudancaDTO) this.engine.listConvertion(AprovacaoMudancaDTO.class, list, listRetorno).get(0);

			return aprovacaoMudancaDto.getQuantidadeVotoRejeitada();

		} else {
			return new Integer(0);
		}

	}
	
	
	public Integer quantidadeAprovacaoMudanca(AprovacaoMudancaDTO aprovacao, Integer idGrupo) throws PersistenceException {

		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select count(aprovacaomudanca.idempregado) from  aprovacaomudanca  ");
		/*if(idGrupo !=null){
			sql.append("inner join gruposempregados on gruposempregados.idempregado = aprovacaomudanca.idempregado ");
		}*/
		sql.append(" where idrequisicaomudanca = ? and idhistoricomudanca is null ");
		parametro.add(aprovacao.getIdRequisicaoMudanca());
		/*if(idGrupo !=null){
			sql.append("and gruposempregados.idgrupo = ?");
			parametro.add(idGrupo);
		}*/
		
		

		List list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("quantidadeAprovacaoMudanca");

		if (list != null && !list.isEmpty()) {

			AprovacaoMudancaDTO aprovacaoMudancaDto = (AprovacaoMudancaDTO) this.engine.listConvertion(AprovacaoMudancaDTO.class, list, listRetorno).get(0);

			return aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca();

		} else {
			return new Integer(0);
		}

	}

	public Boolean validacaoAprovacaoMudanca(Integer idRequisicaoMudanca) throws PersistenceException {

		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select idrequisicaomudanca from aprovacaomudanca where idrequisicaomudanca = ?");

		parametro.add(idRequisicaoMudanca);

		List list = this.execSQL(sql.toString(), parametro.toArray());


		if (list != null && !list.isEmpty()) {


			return true;

		} else {
			return false;
		}

	}
	
	public Integer quantidadeDeEmpregdosPorGrupo(Integer idGrupo) throws PersistenceException {

		List listRetorno = new ArrayList();
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select count(idgrupo) from  gruposempregados  ");
		sql.append("where gruposempregados.idgrupo = ?");
		parametro.add(idGrupo);

		List list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("quantidadeAprovacaoProposta");

		if (list != null && !list.isEmpty()) {
			AprovacaoPropostaDTO aprovacaopropostaDto = (AprovacaoPropostaDTO) this.engine.listConvertion(AprovacaoPropostaDTO.class, list, listRetorno).get(0);
			return aprovacaopropostaDto.getQuantidadeAprovacaoProposta();
		} else {
			return new Integer(0);
		}

	}
	

}
