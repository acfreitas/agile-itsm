package br.com.centralit.citcorpore.integracao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.ComplexidadeDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

/**
 * @author Thays
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ContratoDao extends CrudDaoDefaultImpl {


	public ContratoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idContrato", "idContrato", true, true, false, false));
		listFields.add(new Field("idCliente", "idCliente", false, false, false, false));
		listFields.add(new Field("numero", "numero", false, false, false, false));
		listFields.add(new Field("objeto", "objeto", false, false, false, false));
		listFields.add(new Field("dataContrato", "dataContrato", false, false, false, false));
		listFields.add(new Field("dataFimContrato", "dataFimContrato", false, false, false, false));
		listFields.add(new Field("valorEstimado", "valorEstimado", false, false, false, false));
		listFields.add(new Field("tipoTempoEstimado", "tipoTempoEstimado", false, false, false, false));
		listFields.add(new Field("tempoEstimado", "tempoEstimado", false, false, false, false));
		listFields.add(new Field("tipo", "tipo", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("idMoeda", "idMoeda", false, false, false, false));
		listFields.add(new Field("cotacaoMoeda", "cotacaoMoeda", false, false, false, false));
		listFields.add(new Field("idFornecedor", "idFornecedor", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		listFields.add(new Field("idgruposolicitante", "idGrupoSolicitante", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "Contratos";
	}

	public Collection list() throws PersistenceException {
		/*
		 * List condicao = new ArrayList(); List ordenacao = new ArrayList(); condicao.add(new Condition("deleted", "=", "n")); ordenacao.add(new Order("numero")); return
		 * super.findByCondition(condicao, ordenacao);
		 */
		Collection colFinal = new ArrayList();
		Collection col = super.list("numero");
		for (Iterator it = col.iterator(); it.hasNext();) {
			ContratoDTO contratoDto = (ContratoDTO) it.next();
			if (contratoDto.getDeleted() == null || contratoDto.getDeleted().equalsIgnoreCase("n")) {
				colFinal.add(contratoDto);
			}
		}
		return colFinal;
	}

	public Class getBean() {
		return ContratoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdCliente(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idCliente", "=", parm));
		ordenacao.add(new Order("numero"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdFornecedor(Integer parm) throws PersistenceException {
		String sql = "SELECT " + this.getNamesFieldsStr() + " FROM " + this.getTableName() + " WHERE ";
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL))
			sql += "UPPER(deleted) IS NULL OR UPPER(deleted) = 'N' ";
		else if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER))
			sql += "deleted IS NULL OR deleted = 'N' ";
		else
			sql += "deleted IS NULL OR deleted = 'N' ";
		sql += "ORDER BY numero";
		List lst = this.execSQL(sql, null);
		return this.listConvertion(this.getBean(), lst, this.getListNamesFieldClass());
	}

	public void deleteByIdCliente(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCliente", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idContrato", "=", parm));
		return super.findByCondition(condicao, ordenacao);
	}

	/*
	 * public Collection<RelatorioAcompanhamentoDTO> getnumeros() throws PersistenceException { StringBuilder sql = new StringBuilder(); List listaRetorno = new ArrayList();
	 * sql.append("select numero from contratos where deleted = 'n'"); List lista = execSQL(sql.toString(), null); listaRetorno.add("nome"); if(lista!=null){ Collection<RelatorioAcompanhamentoDTO>
	 * listaNumero = this.listConvertion(RelatorioAcompanhamentoDTO.class, lista, listaRetorno); return listaNumero; } return null; }
	 */

	public Double consultaComplexidade(Integer idServicoContrato, String complexidade) throws PersistenceException {

		Integer idContrato = consultaIdContrato(idServicoContrato);

		List resp = new ArrayList();
		List parametro = new ArrayList();
		parametro.add(idContrato);
		parametro.add(complexidade);

		String sql = "SELECT * FROM complexidade WHERE idcontrato = ? AND complexidade = ? ";

		resp = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idContrato");
		listRetorno.add("complexidade");
		listRetorno.add("valorComplexidade");

		List<ComplexidadeDTO> listconvertion = this.engine.listConvertion(ComplexidadeDTO.class, resp, listRetorno);

		if (listconvertion != null && !listconvertion.isEmpty()) {
			BigDecimal textValor = listconvertion.get(0).getValorComplexidade();
			if (textValor != null) {
				return textValor.doubleValue();
			} else {
				return new Double(0);
			}
		} else {
			return new Double(0);
		}
	}

	/**
	 * Retorna uma lista de complexidade de acordo com o contrato passado.
	 * 
	 * @param idServicoContrato
	 * @return
	 * @throws Exception
	 */
	public Collection<ComplexidadeDTO> listaComplexidadePorContrato(Integer idServicoContrato) throws PersistenceException {

		Integer idContrato = consultaIdContrato(idServicoContrato);

		List lista = new ArrayList();
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT idContrato,complexidade,valorComplexidade FROM complexidade   ");

		if (idContrato != null) {
			sql.append("where idcontrato = ?");
			parametro.add(idContrato);
		}

		lista = this.execSQL(sql.toString(), parametro.toArray());

		List listaRetorno = new ArrayList();
		listaRetorno.add("idContrato");
		listaRetorno.add("complexidade");
		listaRetorno.add("valorComplexidade");

		if (lista != null && !lista.isEmpty()) {
			Collection<ComplexidadeDTO> listaComplexidadePorContrato = this.listConvertion(ComplexidadeDTO.class, lista, listaRetorno);
			return listaComplexidadePorContrato;
		} else {
			return null;
		}
	}

	private Integer consultaIdContrato(Integer idServicoContrato) throws PersistenceException {

		List resp = new ArrayList();
		List parametro = new ArrayList();
		parametro.add(idServicoContrato);

		String sql = "SELECT idcontrato FROM servicocontrato WHERE idservicocontrato = ? ";

		resp = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();
		listRetorno.add("idContrato");

		List<ServicoContratoDTO> listconvertion = this.engine.listConvertion(ServicoContratoDTO.class, resp, listRetorno);

		if (listconvertion != null && !listconvertion.isEmpty()) {
			return listconvertion.get(0).getIdContrato();
		} else {
			return new Integer(0);
		}
	}

	public Collection listByIdAcordoNivelServicoAndTipo(Integer idAcordoNivelServicoParm, String tipoParm) throws PersistenceException {
		List lista = new ArrayList();
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT " + this.getNamesFieldsStr() + " FROM contratos WHERE idcontrato IN ");
		sql.append("(SELECT SC.idcontrato FROM acordoservicocontrato ASCC ");
		sql.append("INNER JOIN servicocontrato SC ON SC.idservicocontrato = ASCC.idservicocontrato ");
		sql.append("INNER JOIN contratos C ON C.idcontrato = SC.idcontrato ");
		sql.append("WHERE ASCC.idacordonivelservico = ? and C.tipo = ?)");

		parametro.add(idAcordoNivelServicoParm);
		parametro.add(tipoParm);

		lista = this.execSQL(sql.toString(), parametro.toArray());

		if (lista != null && !lista.isEmpty()) {
			Collection listaRetorno = this.listConvertion(getBean(), lista, this.getListNamesFieldClass());
			return listaRetorno;
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param idGrupo
	 * @return Collection de Contrato
	 * @throws Exception
	 */
	public Collection findByIdGrupo(Integer idGrupo) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idGrupoSolicitante", "=", idGrupo));
		ordenacao.add(new Order("numero"));
		return super.findByCondition(condicao, ordenacao);
	}

	/**
	 * Lista Contratos Ativos (Situação Ativa e DataFim maior que a data Atual).
	 * 
	 * @return Collection<ContratoDTO> - Lista de Contratos Ativos.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 30.10.2013
	 */
	public Collection<ContratoDTO> listAtivos() throws PersistenceException {

		StringBuilder sql = new StringBuilder();
		List listParametro = new ArrayList();

		sql.append(" SELECT ");
		sql.append(this.getNamesFieldsStr());
		sql.append(" FROM contratos WHERE ");
		sql.append(" (deleted IS NULL OR deleted = 'N' OR deleted = 'n') ");
		sql.append(" AND (datafimcontrato IS NULL OR datafimcontrato > ? )");

		sql.append(" AND (situacao = 'A' ) ");
		sql.append(" ORDER BY idcontrato ");

		listParametro.add(UtilDatas.getDataAtual());

		List list = this.execSQL(sql.toString(), listParametro.toArray());

		return this.listConvertion(this.getBean(), list, this.getListNamesFieldClass());
	}

	/**
	 * Retorna a Lista de Contratos Ativos (Situação Ativa e DataFim maior que a data Atual). que estão relacionados aos Grupos informados.
	 * 
	 * @param listGrupoDto
	 *            - Lista de GrupoDTO.
	 * @return Collection<ContratoDTO> - Lista de Contratos Ativos encontrados.
	 * @author valdoilo.damasceno
	 * @since 30.10.2013
	 */
	public Collection<ContratoDTO> findAtivosByGrupos(Collection<GrupoDTO> listGrupoDto) throws PersistenceException {

		if (listGrupoDto != null && !listGrupoDto.isEmpty()) {

			List listParametro = new ArrayList();

			StringBuilder sql = new StringBuilder();

			sql.append("SELECT contratos.idcontrato,  ");
			sql.append("       idcliente,  ");
			sql.append("       numero,  ");
			sql.append("       datacontrato,  ");
			sql.append("       datafimcontrato,  ");
			sql.append("       valorestimado,  ");
			sql.append("       tipotempoestimado,  ");
			sql.append("       tempoestimado,  ");
			sql.append("       tipo,  ");
			sql.append("	   situacao,  ");
			sql.append("	   idmoeda,  ");
			sql.append("       cotacaomoeda,  ");
			sql.append("       idfornecedor,  ");
			sql.append("       deleted,  ");
			sql.append("       idgruposolicitante  ");
			sql.append("FROM   contratos  ");
			sql.append("       INNER JOIN contratosgrupos  ");
			sql.append("               ON contratos.idcontrato = contratosgrupos.idcontrato  ");

			boolean aux = true;
			for (GrupoDTO grupo : listGrupoDto) {

				if (aux) {
					sql.append(" WHERE contratosgrupos.idgrupo = ? ");
					listParametro.add(grupo.getIdGrupo());
					aux = false;
				} else {
					sql.append(" OR contratosgrupos.idgrupo = ? ");
					listParametro.add(grupo.getIdGrupo());
				}
			}

			sql.append("       AND ( deleted IS NULL  ");
			sql.append("              OR deleted = 'N'  ");
			sql.append("              OR deleted = 'n' )  ");
			sql.append("       AND ( datafimcontrato IS NULL  ");
			sql.append("              OR datafimcontrato > ? )  ");
			sql.append("       AND ( situacao = 'A' )  ");
			sql.append("GROUP  BY contratos.idcontrato,  ");
			sql.append("          idcliente,  ");
			sql.append("          numero,  ");
			sql.append("          datacontrato,  ");
			sql.append("          datafimcontrato,  ");
			sql.append("          valorestimado,  ");
			sql.append("          tipotempoestimado,  ");
			sql.append("          tempoestimado,  ");
			sql.append("          tipo,  ");
			sql.append("          situacao,  ");
			sql.append("          idmoeda,  ");
			sql.append("          cotacaomoeda,  ");
			sql.append("          idfornecedor,  ");
			sql.append("          deleted,  ");
			sql.append("          idgruposolicitante  ");
			sql.append("ORDER  BY contratos.idcontrato  ");

			listParametro.add(UtilDatas.getDataAtual());

			List list;

			list = this.execSQL(sql.toString(), listParametro.toArray());

			List listRetorno = new ArrayList();
			listRetorno.add("idContrato");
			listRetorno.add("idCliente");
			listRetorno.add("numero");
			listRetorno.add("dataContrato");
			listRetorno.add("dataFimContrato");
			listRetorno.add("valorEstimado");
			listRetorno.add("tipoTempoEstimado");
			listRetorno.add("tempoEstimado");
			listRetorno.add("tipo");
			listRetorno.add("situacao");
			listRetorno.add("idMoeda");
			listRetorno.add("cotacaoMoeda");
			listRetorno.add("idFornecedor");
			listRetorno.add("deleted");
			listRetorno.add("idGrupoSolicitante");

			return this.engine.listConvertion(this.getBean(), list, listRetorno);

		} else {
			return null;
		}
	}

	/**
	 * Retorna a lista de Contratos com o nome da Razão Social do Cliente do Contrato.
	 * 
	 * @return Collection<ContratoDTO>
	 * @throws ServiceException
	 * @throws Exception
	 * @since 04.06.2014
	 * @author valdoilo.damasceno
	 */
	public Collection<ContratoDTO> listAtivosWithNomeRazaoSocialCliente() throws PersistenceException {

		StringBuilder sql = new StringBuilder();

		List listParametro = new ArrayList();
		List listFieldsRetorno = new ArrayList<>(this.getListNamesFieldClass());

		listFieldsRetorno.add("razaoSocialCliente");

		sql.append(" SELECT ");
		sql.append(this.getNamesFieldsStr("contratos") + ", clientes.nomerazaosocial");
		sql.append(" FROM contratos ");
		sql.append(" INNER JOIN clientes ");
		sql.append(" ON contratos.idcliente = clientes.idcliente ");
		sql.append(" WHERE ");
		sql.append(" (contratos.deleted IS NULL OR contratos.deleted = 'N' OR contratos.deleted = 'n') ");
		sql.append(" AND (contratos.datafimcontrato IS NULL OR contratos.datafimcontrato > ? )");

		sql.append(" AND (contratos.situacao = 'A' ) ");
		sql.append(" ORDER BY contratos.idcontrato ");

		listParametro.add(UtilDatas.getDataAtual());

		List list = this.execSQL(sql.toString(), listParametro.toArray());

		return this.listConvertion(this.getBean(), list, listFieldsRetorno);
	}

}
