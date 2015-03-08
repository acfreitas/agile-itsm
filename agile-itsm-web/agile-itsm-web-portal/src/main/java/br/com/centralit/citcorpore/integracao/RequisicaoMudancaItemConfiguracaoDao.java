package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RelatorioMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RequisicaoMudancaItemConfiguracaoDao extends CrudDaoDefaultImpl {

	public RequisicaoMudancaItemConfiguracaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idrequisicaomudancaitemconfiguracao", "idRequisicaoMudancaItemConfiguracao", true, true, false, false));
		listFields.add(new Field("idrequisicaomudanca", "idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("iditemconfiguracao", "idItemConfiguracao", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "requisicaomudancaitemconfiguracao";
	}

	/**
	 * Verifica se existe outro item igual criado. Se existir retorna 'true', senao retorna 'false';
	 */
	public boolean verificaSeCadastrado(RequisicaoMudancaItemConfiguracaoDTO itemDTO) throws Exception {
		boolean estaCadastrado;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + getTableName() + " where iditemconfiguracao = ? and idrequisicaomudanca = ?  ");
		parametro.add(itemDTO.getIdItemConfiguracao());
		parametro.add(itemDTO.getIdRequisicaoMudanca());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaCadastrado = true;
		} else {
			estaCadastrado = false;
		}
		return estaCadastrado;
	}

	public Class getBean() {
		return RequisicaoMudancaItemConfiguracaoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return super.find(arg0, null);
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	@Override
	public int deleteByCondition(List condicao) throws PersistenceException {
		return super.deleteByCondition(condicao);
	}

	@Override
	public Collection list() throws PersistenceException {
		return super.list("idrequisicaomudancaitemconfiguracao");
	}

	// bruno.aquino
	public Collection listMudancaItemConfigRelatorio(int idMudanca, int idItemConfig, Date dataInicial, Date dataFinal, int idContrato) throws Exception {

		List fields = new ArrayList();

		List parametros = new ArrayList();

		List resultado;

		String sql = "select requisicaomudanca.idrequisicaomudanca, requisicaomudanca.titulo,requisicaomudanca.descricao,grupo.nome, requisicaomudanca.datahorainicio";

		if (idMudanca != 0 && idItemConfig == 0) {
			sql += " from requisicaomudancaitemconfiguracao JOIN requisicaomudanca on requisicaomudancaitemconfiguracao.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca "
					+ " JOIN itemconfiguracao ON  requisicaomudancaitemconfiguracao.iditemconfiguracao = itemconfiguracao.iditemconfiguracao "
					+ " JOIN grupo ON requisicaomudanca.idgrupoatual = grupo.idgrupo "
					+ " and requisicaomudancaitemconfiguracao.idrequisicaomudanca = ? and "
					+ " requisicaomudanca.datahorainicio between ? and ?";


			parametros.add(idMudanca);
		} else if (idItemConfig != 0 && idMudanca == 0) {
			sql += " from requisicaomudancaitemconfiguracao JOIN requisicaomudanca on requisicaomudancaitemconfiguracao.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca "
					+ " JOIN itemconfiguracao ON  requisicaomudancaitemconfiguracao.iditemconfiguracao = itemconfiguracao.iditemconfiguracao "
					+ " JOIN grupo ON requisicaomudanca.idgrupoatual = grupo.idgrupo "
					+ " and requisicaomudancaitemconfiguracao.iditemconfiguracao = ? and "
					+ " requisicaomudanca.datahorainicio between ? and ? ";

			parametros.add(idItemConfig);
		} else if (idItemConfig != 0 && idMudanca != 0) {
			sql += " from requisicaomudancaitemconfiguracao JOIN requisicaomudanca on requisicaomudancaitemconfiguracao.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca "
					+ " JOIN itemconfiguracao ON  requisicaomudancaitemconfiguracao.iditemconfiguracao = itemconfiguracao.iditemconfiguracao "
				    + " JOIN grupo ON requisicaomudanca.idgrupoatual = grupo.idgrupo"
				    + " and requisicaomudancaitemconfiguracao.iditemconfiguracao = ? and "
				    + " requisicaomudancaitemconfiguracao.idrequisicaomudanca = ? and "
				    + " requisicaomudanca.datahorainicio between ? and ?";

			parametros.add(idItemConfig);
			parametros.add(idMudanca);
		} else {

			sql += " from requisicaomudancaitemconfiguracao JOIN requisicaomudanca on requisicaomudancaitemconfiguracao.idrequisicaomudanca = requisicaomudanca.idrequisicaomudanca "
					+ " JOIN itemconfiguracao ON  requisicaomudancaitemconfiguracao.iditemconfiguracao = itemconfiguracao.iditemconfiguracao "
					+ " JOIN grupo ON requisicaomudanca.idgrupoatual = grupo.idgrupo and " + " requisicaomudanca.datahorainicio between ? and ?";
		}

		parametros.add(UtilDatas.strToTimestamp(dataInicial.toString()));
		parametros.add(Timestamp.valueOf(dataFinal.toString() + " 23:59:00"));

		if (idContrato != 0) {
			sql += " and requisicaomudanca.idcontrato = ? ";
			parametros.add(idContrato);
		}
		sql += " GROUP BY requisicaomudanca.idrequisicaomudanca, requisicaomudanca.titulo,requisicaomudanca.descricao,  grupo.nome, requisicaomudanca.datahorainicio order by requisicaomudanca.idrequisicaomudanca ";

		System.out.println(sql);
		resultado = execSQL(sql, parametros.toArray());

		fields.add("idNumeroMudanca");
		fields.add("tituloMudanca");
		fields.add("descricaoProblemaMudanca");
		fields.add("grupoMudanca");
		fields.add("dataMudanca");

		return (Collection<RelatorioMudancaItemConfiguracaoDTO>) listConvertion(RelatorioMudancaItemConfiguracaoDTO.class, resultado, fields);

	}

	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idRequisicaoMudanca", "=", parm));
		ordenacao.add(new Order("idItemConfiguracao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdItemConfiguracao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemConfiguracao", "=", parm));
		ordenacao.add(new Order("idRequisicaoMudanca"));
		return super.findByCondition(condicao, ordenacao);
	}

	public ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));

		return (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) super.findByCondition(condicoes, null);
	}

	/**
	 * Retorna o item de relacionamento específico sem a chave primária da tabela. Uma espécie de consulta por chave composta.
	 *
	 * @param dto
	 * @return
	 * @throws Exception
	 * @throws ServiceException
	 */
	public RequisicaoMudancaItemConfiguracaoDTO restoreByChaveComposta(RequisicaoMudancaItemConfiguracaoDTO dto) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoMudanca", "=", dto.getIdRequisicaoMudanca()));
		condicoes.add(new Condition("idItemConfiguracao", "=", dto.getIdItemConfiguracao()));

		ArrayList<RequisicaoMudancaItemConfiguracaoDTO> retorno = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) super.findByCondition(condicoes, null);

		if (retorno != null) {
			return retorno.get(0);
		}

		return null;
	}

	public void deleteByIdRequisicaoMudanca(Integer idRequisicaoMudanca) throws ServiceException, Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));

		super.deleteByCondition(condicoes);
	}

	public Collection findByIdMudancaEDataFim(Integer idRequisicaoMudanca) throws Exception {
		List fields = new ArrayList();

		String sql = " select distinct idrequisicaomudancaitemconfiguracao,idrequisicaomudanca,iditemconfiguracao,descricao,dataFim from requisicaomudancaitemconfiguracao where  idrequisicaomudanca = ? and datafim is null";

		List resultado = execSQL(sql, new Object[] { idRequisicaoMudanca });

		fields.add("idRequisicaoMudancaItemConfiguracao");
		fields.add("idRequisicaoMudanca");
		fields.add("idItemConfiguracao");
		fields.add("descricao");
		fields.add("dataFim");

		return listConvertion(getBean(), resultado, fields);
	}

	public Collection listByIdHistoricoMudanca(Integer idHistoricoMudanca) throws Exception {
		List fields = new ArrayList();

		String sql = " select rq.idrequisicaomudancaitemconfiguracao, rq.idrequisicaomudanca, rq.iditemconfiguracao,descricao, rq.dataFim " + "from requisicaomudancaitemconfiguracao rq "
				+ "inner join ligacao_mud_hist_ic ligic on ligic.idrequisicaomudancaitemconfiguracao = rq.idrequisicaomudancaitemconfiguracao " + "where  ligic.idhistoricomudanca = ?";

		List resultado = execSQL(sql, new Object[] { idHistoricoMudanca });

		fields.add("idRequisicaoMudancaItemConfiguracao");
		fields.add("idRequisicaoMudanca");
		fields.add("idItemConfiguracao");
		fields.add("descricao");
		fields.add("dataFim");

		return listConvertion(getBean(), resultado, fields);
	}

	public List<ItemConfiguracaoDTO> listItemConfiguracaoByIdMudanca(Integer idRequisicaoMudanca) throws Exception {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select  ic.iditemconfiguracao , ic.identificacao, ic.status ");
		sql.append(" from itemconfiguracao ic    ");
		sql.append(" inner join requisicaomudancaitemconfiguracao ri ");
		sql.append(" on ic.iditemconfiguracao = ri.iditemconfiguracao ");
		sql.append(" where ri.idrequisicaomudanca = ? ");

		parametro.add(idRequisicaoMudanca);

		fields.add("idItemConfiguracao");
		fields.add("identificacao");
		fields.add("status");

		List dados = this.execSQL(sql.toString(), parametro.toArray());

		return (List<ItemConfiguracaoDTO>) this.listConvertion(ItemConfiguracaoDTO.class, dados, fields);
	}

}
