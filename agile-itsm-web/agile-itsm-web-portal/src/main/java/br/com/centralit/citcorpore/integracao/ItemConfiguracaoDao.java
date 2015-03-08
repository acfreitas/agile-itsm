package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.MidiaSoftwareChaveDTO;
import br.com.centralit.citcorpore.bean.PesquisaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.util.AdaptacaoBD;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class ItemConfiguracaoDao extends CrudDaoDefaultImpl {

	public ItemConfiguracaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idItemConfiguracao", "idItemConfiguracao", true, true, false, false));
		listFields.add(new Field("identificacao", "identificacao", false, false, false, false));
		listFields.add(new Field("familia", "familia", false, false, false, false));
		listFields.add(new Field("classe", "classe", false, false, false, false));
		listFields.add(new Field("idProprietario", "idProprietario", false, false, false, false));
		listFields.add(new Field("localidade", "localidade", false, false, false, false));
		listFields.add(new Field("status", "status", false, false, false, false));
		listFields.add(new Field("versao", "versao", false, false, false, false));
		listFields.add(new Field("criticidade", "criticidade", false, false, false, false));
		listFields.add(new Field("numeroSerie", "numeroSerie", false, false, false, false));
		listFields.add(new Field("dataExpiracao", "dataExpiracao", false, false, false, false));
		listFields.add(new Field("idItemConfiguracaoPai", "idItemConfiguracaoPai", false, false, false, false));
		listFields.add(new Field("idTipoItemConfiguracao", "idTipoItemConfiguracao", false, false, false, false));
		listFields.add(new Field("idGrupoItemConfiguracao", "idGrupoItemConfiguracao", false, false, false, false));
		listFields.add(new Field("idProblema", "idProblema", false, false, false, false));
		listFields.add(new Field("idIncidente", "idIncidente", false, false, false, false));
		listFields.add(new Field("idMudanca", "idMudanca", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("idMidiaSoftware", "idMidiaSoftware", false, false, false, false));
		listFields.add(new Field("impacto", "impacto", false, false, false, false));
		listFields.add(new Field("urgencia", "urgencia", false, false, false, false));
		listFields.add(new Field("idbaseconhecimento", "idBaseConhecimento", false, false, false, false));
		listFields.add(new Field("dtultimacaptura", "dtUltimaCaptura", false, false, false, false));
		listFields.add(new Field("idliberacao", "idLiberacao", false, false, false, false));
		listFields.add(new Field("idcontrato", "idContrato", false, false, false, false));
		listFields.add(new Field("idresponsavel", "idResponsavel", false, false, false, false));
		listFields.add(new Field("ativofixo", "ativoFixo", false, false, false, false));
		listFields.add(new Field("datahoradesinstalacao", "datahoradesinstalacao", false, false, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));
		listFields.add(new Field("informacoesAdicionais", "informacoesAdicionais", false, false, false, false));
		listFields.add(new Field("idgruporesponsavel", "idGrupoResponsavel", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "ItemConfiguracao";

	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ItemConfiguracaoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection findByCondition(List condicao, List ordenacao) throws PersistenceException {
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdItemConfiguracaoPai(Integer parm) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sqlItemConfiguracao = new StringBuilder();

		sqlItemConfiguracao.append("SELECT itemConfiguracao.identificacao, itemConfiguracao.idItemConfiguracao, itemConfiguracao.status, tipoitemconfiguracao.imagem, itemConfiguracao.nome ");
		sqlItemConfiguracao.append("FROM ITEMCONFIGURACAO itemConfiguracao left join tipoitemconfiguracao on itemConfiguracao.idtipoitemconfiguracao = tipoitemconfiguracao.idtipoitemconfiguracao ");
		sqlItemConfiguracao.append("WHERE itemConfiguracao.idItemConfiguracaoPai = ? and itemConfiguracao.dataFim is null ");
		sqlItemConfiguracao.append("ORDER BY identificacao");

		parametro.add(parm);

		List lista = this.execSQL(sqlItemConfiguracao.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("identificacao");
		listRetorno.add("idItemConfiguracao");
		listRetorno.add("status");
		listRetorno.add("imagem");
		listRetorno.add("nome");
		Collection<ItemConfiguracaoDTO> result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	/**
	 * Restaura o Item de Configuração filho de acordo com o idItemConfiguracaoPai, identificacao e idTipoItemConfiguracao.
	 * 
	 * @param itemConfiguracaoFilho
	 *            - Item de Configuração Filho.
	 * @return ItemConfiguracaoDTO - Item de Configuração encontrado.
	 * @throws Exception
	 * @author valdoilo.damasceno
	 * @since 19.01.2015
	 */
	public ItemConfiguracaoDTO obterICFilhoPorIdentificacaoIdPaiEIdTipo(ItemConfiguracaoDTO itemConfiguracaoFilho) throws PersistenceException {

		ItemConfiguracaoDTO itemEncontrado = null;

		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("identificacao", "=", StringUtils.replace(itemConfiguracaoFilho.getIdentificacao(), "\0 ", "")));

		if (itemConfiguracaoFilho.getIdItemConfiguracaoPai() != null) {
			condicao.add(new Condition("idItemConfiguracaoPai", "=", itemConfiguracaoFilho.getIdItemConfiguracaoPai()));
			condicao.add(new Condition("idTipoItemConfiguracao", "=", itemConfiguracaoFilho.getIdTipoItemConfiguracao()));
		}

		ordenacao.add(new Order("identificacao"));

		List result = (List) super.findByCondition(condicao, ordenacao);

		if (result != null && !result.isEmpty()) {
			itemConfiguracaoFilho = (ItemConfiguracaoDTO) result.get(0);
	}

		return itemConfiguracaoFilho;
	}

	public void deleteByIdItemConfiguracaoPai(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemConfiguracaoPai", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdTipoItemConfiguracao(String parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idTipoItemConfiguracao", "=", parm));
		ordenacao.add(new Order("numero"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdTipoItemConfiguracao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idTipoItemConfiguracao", "=", parm));
		super.deleteByCondition(condicao);
	}

	public Collection findByIdTipoItemConfiguracao(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemConfiguracao", "=", parm));
		ordenacao.add(new Order("identificacao"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void updateItemConfiguracaoFilho(Integer idItemConfiguracao, Date data, Timestamp datahoradesinstalacao) throws PersistenceException {
		List parametros = new ArrayList();

		ItemConfiguracaoDTO bean = new ItemConfiguracaoDTO();

		if (data != null) {
			parametros.add(data);
		} else {

			parametros.add(null);
		}

		if(datahoradesinstalacao != null){
			parametros.add(datahoradesinstalacao);
		}else{
			parametros.add(null);
		}

		parametros.add(idItemConfiguracao);

		String sql = "UPDATE " + getTableName() + " SET dataFim = ?, datahoradesinstalacao = ? WHERE idItemConfiguracaoPai = ? and dataFim is null";
		execUpdate(sql, parametros.toArray());
	}

	private List prepararListaDeRetorno() {
		List listRetorno = new ArrayList();
		listRetorno.add("idItemConfiguracao");
		return listRetorno;
	}

	public Collection<ItemConfiguracaoDTO> listByIdentificacao(PesquisaItemConfiguracaoDTO pesquisa) throws PersistenceException {
		List parametro = new ArrayList();
		SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();

		StringBuilder sqlItemConfiguracao = new StringBuilder();

		sqlItemConfiguracao.append("SELECT itemConfiguracao.identificacao, itemConfiguracao.idItemConfiguracao ");
		sqlItemConfiguracao.append("FROM ITEMCONFIGURACAO itemConfiguracao ");
		sqlItemConfiguracao.append("LEFT JOIN ITEMCONFIGURACAOEVENTO itemConfiguracaoEvento ");
		sqlItemConfiguracao.append("ON itemConfiguracaoEvento.IDITEMCONFIGURACAO = itemConfiguracao.IDITEMCONFIGURACAO ");
		sqlItemConfiguracao.append("WHERE  itemConfiguracao.DATAFIM IS NULL  ");

		if (pesquisa.getItemRelacionado() != null && pesquisa.getItemRelacionado().equalsIgnoreCase("S")) {
			if (pesquisa.getIdItemConfiguracaoFilho() != null) {
				sqlItemConfiguracao.append("AND itemConfiguracao.idItemConfiguracao = ? ");
				parametro.add(pesquisa.getIdItemConfiguracaoFilho());
			}

			if (pesquisa.getIdItemConfiguracao() != null) {
				sqlItemConfiguracao.append(" AND itemConfiguracao.IDITEMCONFIGURACAOPAI = ? ");
				parametro.add(pesquisa.getIdItemConfiguracao());
			}

		} else {
			if (pesquisa.getIp() != null && !pesquisa.getIp().equals("")) {
				sqlItemConfiguracao.append(" AND itemConfiguracao.identificacao like ? ");
				parametro.add("%" + pesquisa.getIp() + "%");
			}

			if (pesquisa.getIdItemConfiguracao() != null) {
				sqlItemConfiguracao.append("AND itemConfiguracao.idItemConfiguracao = ? ");
				parametro.add(pesquisa.getIdItemConfiguracao());
			}

			sqlItemConfiguracao.append(" AND itemConfiguracao.IDITEMCONFIGURACAOPAI IS NULL ");
		}

		if (pesquisa.getIdGrupoItemConfiguracao() != null) {
			sqlItemConfiguracao.append("AND itemConfiguracao.idGrupoItemConfiguracao = ? ");
			parametro.add(pesquisa.getIdGrupoItemConfiguracao());
		}

		if (pesquisa.getInstalacao() != null & pesquisa.getDesinstalacao() == null) {
			sqlItemConfiguracao.append("AND (itemConfiguracaoEvento.tipoexecucao = ? or itemconfiguracaoevento.tipoexecucao is null) ");
			parametro.add(pesquisa.getInstalacao());
		}

		if (pesquisa.getDesinstalacao() != null & pesquisa.getInstalacao() == null) {
			sqlItemConfiguracao.append("AND (itemConfiguracaoEvento.tipoexecucao = ? or itemconfiguracaoevento.tipoexecucao is null)  ");
			parametro.add(pesquisa.getDesinstalacao());
		}
		/*
		 * alteração feita para que os dados com valor igual a null possam vir na pesquisa .
		 * 
		 * @thays.araujo
		 */
		if(pesquisa.getDesinstalacao() != null && pesquisa.getInstalacao()!=null){
			sqlItemConfiguracao.append("and (itemconfiguracaoevento.tipoexecucao = ? or itemconfiguracaoevento.tipoexecucao = ? or itemconfiguracaoevento.tipoexecucao is null) ");
			parametro.add(pesquisa.getDesinstalacao());
			parametro.add(pesquisa.getInstalacao());
		}

		if (pesquisa.getDataInicio() != null) {

			if (pesquisa.getInventario() != null) {
				sqlItemConfiguracao.append("AND itemConfiguracao.datainicio BETWEEN ? AND ? ");
				parametro.add(pesquisa.getDataInicio());
				parametro.add(pesquisa.getDataFim());
			} else {
				sqlItemConfiguracao.append("AND itemConfiguracaoEvento.data BETWEEN ? AND ? ");
				parametro.add(pesquisa.getDataInicio());
				parametro.add(pesquisa.getDataFim());
			}
		}
		if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)){
			sqlItemConfiguracao.append("ORDER BY len(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}else{
			sqlItemConfiguracao.append("ORDER BY length(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}

		List lista = this.execSQL(sqlItemConfiguracao.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("identificacao");
		listRetorno.add("idItemConfiguracao");
		Collection<ItemConfiguracaoDTO> result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	public Collection<ItemConfiguracaoDTO> listByIdentificacaoNaoDesativados(PesquisaItemConfiguracaoDTO pesquisa) throws PersistenceException {
		List parametro = new ArrayList();
		SolicitacaoServicoDao solicitacaoDao = new SolicitacaoServicoDao();

		StringBuilder sqlItemConfiguracao = new StringBuilder();

		sqlItemConfiguracao.append("SELECT itemConfiguracao.identificacao, itemConfiguracao.idItemConfiguracao ");
		sqlItemConfiguracao.append("FROM ITEMCONFIGURACAO itemConfiguracao ");
		sqlItemConfiguracao.append("LEFT JOIN ITEMCONFIGURACAOEVENTO itemConfiguracaoEvento ");
		sqlItemConfiguracao.append("ON itemConfiguracaoEvento.IDITEMCONFIGURACAO = itemConfiguracao.IDITEMCONFIGURACAO ");
		sqlItemConfiguracao.append("WHERE  itemConfiguracao.DATAFIM IS NULL  and itemConfiguracao.STATUS <> 2");

		if (pesquisa.getItemRelacionado() != null && pesquisa.getItemRelacionado().equalsIgnoreCase("S")) {
			if (pesquisa.getIdItemConfiguracaoFilho() != null) {
				sqlItemConfiguracao.append("AND itemConfiguracao.idItemConfiguracao = ? ");
				parametro.add(pesquisa.getIdItemConfiguracaoFilho());
			}

			if (pesquisa.getIdItemConfiguracao() != null) {
				sqlItemConfiguracao.append(" AND itemConfiguracao.IDITEMCONFIGURACAOPAI = ? ");
				parametro.add(pesquisa.getIdItemConfiguracao());
			}

		} else {
			if (pesquisa.getIp() != null && !pesquisa.getIp().equals("")) {
				sqlItemConfiguracao.append(" AND itemConfiguracao.identificacao like ? ");
				parametro.add("%" + pesquisa.getIp() + "%");
			}

			if (pesquisa.getIdItemConfiguracao() != null) {
				sqlItemConfiguracao.append("AND itemConfiguracao.idItemConfiguracao = ? ");
				parametro.add(pesquisa.getIdItemConfiguracao());
			}

			sqlItemConfiguracao.append(" AND itemConfiguracao.IDITEMCONFIGURACAOPAI IS NULL ");
		}

		if (pesquisa.getIdGrupoItemConfiguracao() != null) {
			sqlItemConfiguracao.append("AND itemConfiguracao.idGrupoItemConfiguracao = ? ");
			parametro.add(pesquisa.getIdGrupoItemConfiguracao());
		}

		if (pesquisa.getInstalacao() != null & pesquisa.getDesinstalacao() == null) {
			sqlItemConfiguracao.append("AND (itemConfiguracaoEvento.tipoexecucao = ? or itemconfiguracaoevento.tipoexecucao is null) ");
			parametro.add(pesquisa.getInstalacao());
		}

		if (pesquisa.getDesinstalacao() != null & pesquisa.getInstalacao() == null) {
			sqlItemConfiguracao.append("AND (itemConfiguracaoEvento.tipoexecucao = ? or itemconfiguracaoevento.tipoexecucao is null)  ");
			parametro.add(pesquisa.getDesinstalacao());
		}
		/*
		 * alteração feita para que os dados com valor igual a null possam vir na pesquisa .
		 * 
		 * @thays.araujo
		 */
		if(pesquisa.getDesinstalacao() != null && pesquisa.getInstalacao()!=null){
			sqlItemConfiguracao.append("and (itemconfiguracaoevento.tipoexecucao = ? or itemconfiguracaoevento.tipoexecucao = ? or itemconfiguracaoevento.tipoexecucao is null) ");
			parametro.add(pesquisa.getDesinstalacao());
			parametro.add(pesquisa.getInstalacao());
		}

		if (pesquisa.getDataInicio() != null) {

			if (pesquisa.getInventario() != null) {
				sqlItemConfiguracao.append("AND itemConfiguracao.datainicio BETWEEN ? AND ? ");
				parametro.add(pesquisa.getDataInicio());
				parametro.add(pesquisa.getDataFim());
			} else {
				sqlItemConfiguracao.append("AND itemConfiguracaoEvento.data BETWEEN ? AND ? ");
				parametro.add(pesquisa.getDataInicio());
				parametro.add(pesquisa.getDataFim());
			}
		}
		if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equalsIgnoreCase(SQLConfig.SQLSERVER)){
			sqlItemConfiguracao.append("ORDER BY len(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}else{
			sqlItemConfiguracao.append("ORDER BY length(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}

		List lista = this.execSQL(sqlItemConfiguracao.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("identificacao");
		listRetorno.add("idItemConfiguracao");
		Collection<ItemConfiguracaoDTO> result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	public Collection<ItemConfiguracaoDTO> pesquisaDataExpiracao(java.util.Date data) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT ic.iditemconfiguracao, ic.identificacao, ic.idgrupoitemconfiguracao, ");
		sql.append("ic.idproprietario, gic.nomegrupoitemconfiguracao, gic.emailGrupoItemConfiguracao, ic.dataexpiracao	");
		sql.append("FROM itemconfiguracao ic, grupoitemconfiguracao gic ");
		sql.append("WHERE ic.idgrupoitemconfiguracao = gic.idgrupoitemconfiguracao AND dataexpiracao = ? AND ic.datafim IS NULL");

		parametro.add(data);
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();

		listRetorno.add("idItemConfiguracao");
		listRetorno.add("identificacao");
		listRetorno.add("idGrupoItemConfiguracao");
		listRetorno.add("idProprietario");
		listRetorno.add("nomeGrupoItemConfiguracao");
		listRetorno.add("emailGrupoItemConfiguracao");
		listRetorno.add("dataExpiracao");

		List result = this.engine.listConvertion(ItemConfiguracaoDTO.class, lista, listRetorno);

		return (Collection<ItemConfiguracaoDTO>) result;
	}

	public InformacaoItemConfiguracaoDTO listByInformacao(ItemConfiguracaoDTO itemConfiguracao) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT itemConfiguracao.idItemConfiguracao ");
		sb.append("FROM ITEMCONFIGURACAO itemConfiguracao ");
		sb.append("Where itemConfiguracao.iditemconfiguracao = ? ");

		parametro.add(itemConfiguracao.getIdItemConfiguracao());
		List lista = this.execSQL(sb.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();

		listRetorno.add("idItemConfiguracao");

		List result = this.engine.listConvertion(InformacaoItemConfiguracaoDTO.class, lista, listRetorno);
		return (InformacaoItemConfiguracaoDTO) result.get(0);
	}

	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoFilho(ItemConfiguracaoDTO itemConfiguracao) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT idItemConfiguracao, idTipoItemConfiguracao ");
		sb.append("FROM " + getTableName() + " itemConfiguracao ");
		sb.append("Where iditemconfiguracaopai = ? ");

		parametro.add(itemConfiguracao.getIdItemConfiguracao());
		List lista = this.execSQL(sb.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();

		listRetorno.add("idItemConfiguracao");
		listRetorno.add("idTipoItemConfiguracao");

		List result = this.engine.listConvertion(ItemConfiguracaoDTO.class, lista, listRetorno);
		if (result != null) {
			return (Collection<ItemConfiguracaoDTO>) result;
		} else {
			return null;
		}

	}

	public ItemConfiguracaoDTO listIdUsuario(String obj) throws PersistenceException {
		List parametro = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = "select idusuario from Usuario where Login  = ? ";
		parametro.add(obj);
		list = this.execSQL(sql, parametro.toArray());
		if (list.isEmpty()) {
			return null;
		} else {
			return (ItemConfiguracaoDTO) this.listConvertion(getBean(), list, fields).get(0);

		}

	}

	/**
	 * Busca os itens de configuracao relacionados ao grupo passado como parametro.
	 *
	 * @param grupoICDto
	 *            GrupoItemConfiguracaoDTO
	 * @return Lista de itens relacionados aos grupos passados.
	 * @throws Exception
	 *             Excecao generica.
	 */
	public Collection<ItemConfiguracaoDTO> listByGrupo(GrupoItemConfiguracaoDTO grupoICDto, String criticidade, String status) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sqlItemConfiguracao = new StringBuilder();

		sqlItemConfiguracao.append("SELECT itemConfiguracao.identificacao, itemConfiguracao.idItemConfiguracao, itemConfiguracao.status, tipoitemconfiguracao.imagem, itemConfiguracao.nome ");
		sqlItemConfiguracao.append("FROM ITEMCONFIGURACAO itemConfiguracao left join tipoitemconfiguracao on itemConfiguracao.idtipoitemconfiguracao = tipoitemconfiguracao.idtipoitemconfiguracao ");
		sqlItemConfiguracao.append("INNER JOIN GRUPOITEMCONFIGURACAO grupoItemConfiguracao ");
		sqlItemConfiguracao.append("ON grupoItemConfiguracao.IDGRUPOITEMCONFIGURACAO = itemConfiguracao.IDGRUPOITEMCONFIGURACAO ");
		sqlItemConfiguracao.append("WHERE itemConfiguracao.IDITEMCONFIGURACAOPAI IS NULL AND itemConfiguracao.DATAFIM IS NULL ");

		if (grupoICDto.getIdGrupoItemConfiguracao() != null) {
			sqlItemConfiguracao.append("AND itemConfiguracao.idGrupoItemConfiguracao = ? ");
			parametro.add(grupoICDto.getIdGrupoItemConfiguracao());
		}
		if(!criticidade.equals("") ){
			sqlItemConfiguracao.append("AND itemConfiguracao.criticidade = ? ");
			parametro.add(criticidade);
		}
		if(!status.equals("")){
			sqlItemConfiguracao.append("AND itemConfiguracao.status = ? ");
			if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("POSTGRES")){
				parametro.add(new Integer(status));
			}else{
				parametro.add(status);
			}
		}

		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")){
			sqlItemConfiguracao.append("ORDER BY LEN(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}else{
			sqlItemConfiguracao.append("ORDER BY length(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}

		List lista = this.execSQL(sqlItemConfiguracao.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("identificacao");
		listRetorno.add("idItemConfiguracao");
		listRetorno.add("status");
		listRetorno.add("imagem");
		listRetorno.add("nome");
		Collection<ItemConfiguracaoDTO> result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	public Collection<ItemConfiguracaoDTO> listAtivos() throws PersistenceException {
		String sql = "SELECT itemConfiguracao.identificacao, itemConfiguracao.idItemConfiguracao, itemConfiguracao.status, tipoitemconfiguracao.imagem, itemConfiguracao.dtultimacaptura, "
				+ "itemConfiguracao.nome FROM ITEMCONFIGURACAO itemConfiguracao "
				+ " left join tipoitemconfiguracao on itemConfiguracao.idtipoitemconfiguracao = tipoitemconfiguracao.idtipoitemconfiguracao "
				+ " WHERE itemConfiguracao.IDITEMCONFIGURACAOPAI IS NULL " + " AND itemConfiguracao.DATAFIM IS NULL ";
		if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")) {
			sql += " ORDER BY len(itemConfiguracao.identificacao), itemConfiguracao.identificacao";
		} else {
			sql += " ORDER BY length(itemConfiguracao.identificacao), itemConfiguracao.identificacao";
		}
		List lista = this.execSQL(sql, null);
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("identificacao");
		listRetorno.add("idItemConfiguracao");
		listRetorno.add("status");
		listRetorno.add("imagem");
		listRetorno.add("dtUltimaCaptura");
		listRetorno.add("nome");
		Collection<ItemConfiguracaoDTO> result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	/**
	 * Busca os itens de configuracao relacionados ao grupo passado como parametro.
	 *
	 * @param grupoICDto
	 *            GrupoItemConfiguracaoDTO
	 * @return Lista de itens relacionados aos grupos passados.
	 * @throws Exception
	 *             Excecao generica.
	 */
	public Collection<ItemConfiguracaoDTO> listByGrupo(GrupoItemConfiguracaoDTO grupoICDto, ItemConfiguracaoDTO itemConfiguracaoDTO) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sqlItemConfiguracao = new StringBuilder();

		sqlItemConfiguracao.append("SELECT itemConfiguracao.identificacao, itemConfiguracao.idItemConfiguracao, itemConfiguracao.status, tipoitemconfiguracao.imagem, itemconfiguracao.nome ");
		sqlItemConfiguracao.append("FROM ITEMCONFIGURACAO itemConfiguracao left join tipoitemconfiguracao on itemConfiguracao.idtipoitemconfiguracao = tipoitemconfiguracao.idtipoitemconfiguracao ");
		sqlItemConfiguracao.append("INNER JOIN GRUPOITEMCONFIGURACAO grupoItemConfiguracao ");
		sqlItemConfiguracao.append("ON grupoItemConfiguracao.IDGRUPOITEMCONFIGURACAO = itemConfiguracao.IDGRUPOITEMCONFIGURACAO ");
		sqlItemConfiguracao.append("WHERE itemConfiguracao.IDITEMCONFIGURACAOPAI IS NULL AND itemConfiguracao.DATAFIM IS NULL ");

		if (grupoICDto.getIdGrupoItemConfiguracao() != null) {
			sqlItemConfiguracao.append("AND itemConfiguracao.idGrupoItemConfiguracao = ? ");
			parametro.add(grupoICDto.getIdGrupoItemConfiguracao());
		}
		if(itemConfiguracaoDTO.getCriticidade() != null ){
			sqlItemConfiguracao.append("AND itemConfiguracao.criticidade = ? ");
			parametro.add(itemConfiguracaoDTO.getCriticidade());
		}
		if(itemConfiguracaoDTO.getStatus() != null ){
			sqlItemConfiguracao.append("AND itemConfiguracao.status = ? ");
			if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("POSTGRES")){
				parametro.add(new Integer(itemConfiguracaoDTO.getStatus()));
			}else{
				parametro.add(itemConfiguracaoDTO.getStatus());
			}
		}
		if(itemConfiguracaoDTO.getIdentificacao() != null ) {
			sqlItemConfiguracao.append("AND itemConfiguracao.identificacao like '%"+itemConfiguracaoDTO.getIdentificacao()+"%' ");
		}

		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")){
			sqlItemConfiguracao.append("ORDER BY LEN(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}else{
			sqlItemConfiguracao.append("ORDER BY length(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}

		List lista = this.execSQL(sqlItemConfiguracao.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("identificacao");
		listRetorno.add("idItemConfiguracao");
		listRetorno.add("status");
		listRetorno.add("imagem");
		listRetorno.add("nome");
		Collection<ItemConfiguracaoDTO> result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	/**
	 * Busca a lista de itens que nao estao relacionados aos grupos.
	 *
	 * @return lista de ItemConfiguracaoDTO.
	 * @throws Exception
	 *             Excecao generica.
	 */
	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(String criticidade, String status) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sqlItemConfiguracao = new StringBuilder();

		sqlItemConfiguracao.append("SELECT itemConfiguracao.identificacao, itemConfiguracao.idItemConfiguracao, itemConfiguracao.status, tipoitemconfiguracao.imagem, itemConfiguracao.nome ");
		sqlItemConfiguracao.append("FROM ITEMCONFIGURACAO itemConfiguracao left join tipoitemconfiguracao on itemConfiguracao.idtipoitemconfiguracao = tipoitemconfiguracao.idtipoitemconfiguracao ");
		sqlItemConfiguracao.append("WHERE itemConfiguracao.IDITEMCONFIGURACAOPAI IS NULL ");
		sqlItemConfiguracao.append("AND itemConfiguracao.DATAFIM IS NULL ");
		sqlItemConfiguracao.append("AND itemConfiguracao.idgrupoitemconfiguracao IS NULL ");

		if(!criticidade.equals("") ){

			sqlItemConfiguracao.append("AND itemConfiguracao.criticidade = ? ");
			parametro.add(criticidade);
		}
		if(!status.equals("")){
			sqlItemConfiguracao.append("AND itemConfiguracao.status = ? ");
			if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("POSTGRES")){
				parametro.add(new Integer(status));
			}else{
				parametro.add(status);
			}

		}
		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")){
			sqlItemConfiguracao.append("ORDER BY LEN(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}else{
			sqlItemConfiguracao.append("ORDER BY length(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}

		List lista = this.execSQL(sqlItemConfiguracao.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("identificacao");
		listRetorno.add("idItemConfiguracao");
		listRetorno.add("status");
		listRetorno.add("imagem");
		listRetorno.add("nome");
		Collection<ItemConfiguracaoDTO> result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	/**
	 * Busca a lista de itens que nao estao relacionados aos grupos.
	 *
	 * @return lista de ItemConfiguracaoDTO.
	 * @throws Exception
	 *             Excecao generica.
	 */
	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(String criticidade, String status, String sistemaOperacional, String grupoTrabalho, String tipoMembroDominio, String usuario,
			String processador, List softwares) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sqlItemConfiguracao = new StringBuilder();

		sqlItemConfiguracao.append("SELECT itemConfiguracao.identificacao, itemConfiguracao.idItemConfiguracao, itemConfiguracao.status, tipoitemconfiguracao.imagem, itemconfiguracao.nome ");
		sqlItemConfiguracao.append("FROM ITEMCONFIGURACAO itemConfiguracao left join tipoitemconfiguracao on itemConfiguracao.idtipoitemconfiguracao = tipoitemconfiguracao.idtipoitemconfiguracao ");
		sqlItemConfiguracao.append("WHERE itemConfiguracao.IDITEMCONFIGURACAOPAI IS NULL ");
		sqlItemConfiguracao.append("AND itemConfiguracao.DATAFIM IS NULL ");
		sqlItemConfiguracao.append("AND itemConfiguracao.idgrupoitemconfiguracao IS NULL ");

		if(criticidade != null && !criticidade.equals("") && !criticidade.equals("0")){
			sqlItemConfiguracao.append("AND itemConfiguracao.criticidade = ? ");
			if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("POSTGRES")){
				parametro.add(new Integer(criticidade));
			}else{
				parametro.add(criticidade);
			}
		}
		if(status != null && !status.equals("") && !status.equals("0")){
			sqlItemConfiguracao.append("AND itemConfiguracao.status = ? ");
			if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("POSTGRES")){
				parametro.add(new Integer(status));
			}else{
				parametro.add(status);
			}
		}
		if(sistemaOperacional != null && !sistemaOperacional.equals("")){
			String itemStr = sistemaOperacional.replaceAll("'", "");
			sqlItemConfiguracao.append(" AND itemConfiguracao.iditemconfiguracao IN ");
			sqlItemConfiguracao.append("(");
			sqlItemConfiguracao.append("SELECT itemConfiguracaoAux.IDITEMCONFIGURACAOPAI FROM ITEMCONFIGURACAO itemConfiguracaoAux ");
			sqlItemConfiguracao.append("WHERE itemConfiguracaoAux.DATAFIM IS NULL ");
			sqlItemConfiguracao.append("AND itemConfiguracaoAux.iditemconfiguracao IN ");
			sqlItemConfiguracao
					.append("(SELECT iditemconfiguracao FROM valor INNER JOIN caracteristica ON valor.idcaracteristica = caracteristica.idcaracteristica  where datafim is null AND tagcaracteristica = 'OSNAME' AND valorstr like '"
							+ itemStr + "%') ");
			sqlItemConfiguracao.append(") ");
		}
		if(grupoTrabalho != null && !grupoTrabalho.equals("")){
			String itemStr = grupoTrabalho.replaceAll("'", "");
			sqlItemConfiguracao.append(" AND itemConfiguracao.iditemconfiguracao IN ");
			sqlItemConfiguracao.append("(");
			sqlItemConfiguracao.append("SELECT itemConfiguracaoAux.IDITEMCONFIGURACAOPAI FROM ITEMCONFIGURACAO itemConfiguracaoAux ");
			sqlItemConfiguracao.append("WHERE itemConfiguracaoAux.DATAFIM IS NULL ");
			sqlItemConfiguracao.append("AND itemConfiguracaoAux.iditemconfiguracao IN ");
			sqlItemConfiguracao
					.append("(SELECT iditemconfiguracao FROM valor INNER JOIN caracteristica ON valor.idcaracteristica = caracteristica.idcaracteristica  where datafim is null AND tagcaracteristica = 'WORKGROUP' AND valorstr like '"
							+ itemStr + "%') ");
			sqlItemConfiguracao.append(") ");
		}
		if(tipoMembroDominio != null && !tipoMembroDominio.equals("")){
			String itemStr = tipoMembroDominio.replaceAll("'", "");
			sqlItemConfiguracao.append(" AND itemConfiguracao.iditemconfiguracao IN ");
			sqlItemConfiguracao.append("(");
			sqlItemConfiguracao.append("SELECT itemConfiguracaoAux.IDITEMCONFIGURACAOPAI FROM ITEMCONFIGURACAO itemConfiguracaoAux ");
			sqlItemConfiguracao.append("WHERE itemConfiguracaoAux.DATAFIM IS NULL ");
			sqlItemConfiguracao.append("AND itemConfiguracaoAux.iditemconfiguracao IN ");
			sqlItemConfiguracao
					.append("(SELECT iditemconfiguracao FROM valor INNER JOIN caracteristica ON valor.idcaracteristica = caracteristica.idcaracteristica  where datafim is null AND tagcaracteristica = 'DESCDOMAINROLE' AND valorstr like '"
							+ itemStr + "%') ");
			sqlItemConfiguracao.append(") ");
		}
		if(usuario != null && !usuario.equals("")){
			String itemStr = usuario.replaceAll("'", "");
			sqlItemConfiguracao.append(" AND itemConfiguracao.iditemconfiguracao IN ");
			sqlItemConfiguracao.append("(");
			sqlItemConfiguracao.append("SELECT itemConfiguracaoAux.IDITEMCONFIGURACAOPAI FROM ITEMCONFIGURACAO itemConfiguracaoAux ");
			sqlItemConfiguracao.append("WHERE itemConfiguracaoAux.DATAFIM IS NULL ");
			sqlItemConfiguracao.append("AND itemConfiguracaoAux.iditemconfiguracao IN ");
			sqlItemConfiguracao
					.append("(SELECT iditemconfiguracao FROM valor INNER JOIN caracteristica ON valor.idcaracteristica = caracteristica.idcaracteristica  where datafim is null AND tagcaracteristica = 'USERID' AND valorstr like '%"
							+ itemStr.trim() + "%') ");
			sqlItemConfiguracao.append(") ");
		}
		if(processador != null && !processador.equals("")){
			String itemStr = processador.replaceAll("'", "");
			itemStr = processador.replaceAll("\"", "");
			sqlItemConfiguracao.append(" AND itemConfiguracao.iditemconfiguracao IN ");
			sqlItemConfiguracao.append("(");
			sqlItemConfiguracao.append("SELECT itemConfiguracaoAux.IDITEMCONFIGURACAOPAI FROM ITEMCONFIGURACAO itemConfiguracaoAux ");
			sqlItemConfiguracao.append("WHERE itemConfiguracaoAux.DATAFIM IS NULL ");
			sqlItemConfiguracao.append("AND itemConfiguracaoAux.iditemconfiguracao IN ");
			sqlItemConfiguracao
					.append("(SELECT iditemconfiguracao FROM valor INNER JOIN caracteristica ON valor.idcaracteristica = caracteristica.idcaracteristica  where datafim is null AND tagcaracteristica = 'PROCESSORT' AND valorstr like '%"
							+ itemStr.trim() + "%') ");
			sqlItemConfiguracao.append(") ");
		}
		if (softwares != null && softwares.size() > 0){
			String uppFieldFunction = AdaptacaoBD.getUpperFunction("valorstr");
			if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")){
				uppFieldFunction = uppFieldFunction.toUpperCase();
			}
			for (Iterator it = softwares.iterator(); it.hasNext();){
				String soft = (String)it.next();
				if (soft != null && !soft.trim().equalsIgnoreCase("")){
					String itemStr = soft.replaceAll("'", "");
					sqlItemConfiguracao.append(" AND itemConfiguracao.iditemconfiguracao IN ");
					sqlItemConfiguracao.append("(");
					sqlItemConfiguracao.append("SELECT itemConfiguracaoAux.IDITEMCONFIGURACAOPAI FROM ITEMCONFIGURACAO itemConfiguracaoAux ");
					sqlItemConfiguracao.append("WHERE itemConfiguracaoAux.DATAFIM IS NULL ");
					sqlItemConfiguracao.append("AND itemConfiguracaoAux.iditemconfiguracao IN ");
					sqlItemConfiguracao
							.append("(SELECT iditemconfiguracao FROM valor INNER JOIN caracteristica ON valor.idcaracteristica = caracteristica.idcaracteristica  where datafim is null AND tagcaracteristica = 'PUBLISHER' AND "
									+ uppFieldFunction + " like '%" + itemStr.trim().toUpperCase() + "%') ");
					sqlItemConfiguracao.append(") ");
				}
			}
		}

		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")){
			sqlItemConfiguracao.append(" ORDER BY LEN(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}else{
			sqlItemConfiguracao.append(" ORDER BY length(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}

		List lista = this.execSQL(sqlItemConfiguracao.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("identificacao");
		listRetorno.add("idItemConfiguracao");
		listRetorno.add("status");
		listRetorno.add("imagem");
		listRetorno.add("nome");
		Collection<ItemConfiguracaoDTO> result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	/**
	 * Busca a lista de itens que nao estao relacionados aos grupos.
	 *
	 * @return lista de ItemConfiguracaoDTO.
	 * @throws Exception
	 *             Excecao generica.
	 */
	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(ItemConfiguracaoDTO itemConfiguracaoDTO) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sqlItemConfiguracao = new StringBuilder();

		sqlItemConfiguracao.append("SELECT itemConfiguracao.identificacao, itemConfiguracao.idItemConfiguracao, itemConfiguracao.status, tipoitemconfiguracao.imagem, itemconfiguracao.nome ");
		sqlItemConfiguracao.append("FROM ITEMCONFIGURACAO itemConfiguracao left join tipoitemconfiguracao on itemConfiguracao.idtipoitemconfiguracao = tipoitemconfiguracao.idtipoitemconfiguracao ");
		sqlItemConfiguracao.append("WHERE itemConfiguracao.IDITEMCONFIGURACAOPAI IS NULL ");
		sqlItemConfiguracao.append("AND itemConfiguracao.DATAFIM IS NULL ");
		sqlItemConfiguracao.append("AND itemConfiguracao.idgrupoitemconfiguracao IS NULL ");

		if(itemConfiguracaoDTO.getCriticidade() != null ){
			sqlItemConfiguracao.append("AND itemConfiguracao.criticidade = ? ");
			parametro.add(itemConfiguracaoDTO.getCriticidade());
		}
		if(itemConfiguracaoDTO.getStatus() != null ){
			sqlItemConfiguracao.append("AND itemConfiguracao.status = ? ");
			if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("POSTGRES")){
				parametro.add(new Integer(itemConfiguracaoDTO.getStatus()));
			}else{
				parametro.add(itemConfiguracaoDTO.getStatus());
			}
		}
		if(itemConfiguracaoDTO.getIdentificacao() != null ){
			sqlItemConfiguracao.append("AND itemConfiguracao.identificacao like '%"+itemConfiguracaoDTO.getIdentificacao()+"%' ");
		}
		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("SQLSERVER")){
			sqlItemConfiguracao.append("ORDER BY LEN(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}else{
			sqlItemConfiguracao.append("ORDER BY length(itemConfiguracao.identificacao), itemConfiguracao.identificacao");
		}

		List lista = this.execSQL(sqlItemConfiguracao.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("identificacao");
		listRetorno.add("idItemConfiguracao");
		listRetorno.add("status");
		listRetorno.add("imagem");
		listRetorno.add("nome");
		Collection<ItemConfiguracaoDTO> result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	public void atualizaGrupo(ItemConfiguracaoDTO itemConfiguracaoDTO) throws PersistenceException {
		List parametros = new ArrayList();
		StringBuilder sqlItemConfiguracao = new StringBuilder();

		if (itemConfiguracaoDTO != null) {

			if (itemConfiguracaoDTO.getIdGrupoItemConfiguracao() != null && itemConfiguracaoDTO.getIdGrupoItemConfiguracao() > 0) {
				parametros.add(itemConfiguracaoDTO.getIdGrupoItemConfiguracao());
			} else {
				parametros.add(null);
			}

			if (itemConfiguracaoDTO.getIdItemConfiguracao() != null && itemConfiguracaoDTO.getIdItemConfiguracao() > 0) {
				parametros.add(itemConfiguracaoDTO.getIdItemConfiguracao());
			} else {
				parametros.add(null);
			}

		} else {
			parametros.add(null);
			parametros.add(null);
		}

		sqlItemConfiguracao.append("UPDATE " + getTableName() + " SET idGrupoItemConfiguracao = ? WHERE idItemConfiguracao = ?");

		execUpdate(sqlItemConfiguracao.toString(), parametros.toArray());
	}

	/**
	 * Método que lista os Itens de configuração associado com o evento passado como parâmetro
	 *
	 * @param idEvento
	 * @return Collection<ItemConfiguracaoDTO> relacionados ao evento
	 * @throws Exception
	 */
	public Collection<ItemConfiguracaoDTO> listByEvento(Integer idEvento) throws PersistenceException {
		String sql = "SELECT ic.iditemconfiguracao, ic.identificacao, ic.nome FROM " + getTableName()
				+ " AS ic INNER JOIN eventoitemconfiguracao AS eic ON eic.iditemconfiguracao = ic.iditemconfiguracao WHERE eic.idevento = ?";
		List<?> dados = this.execSQL(sql, new Object[] { idEvento });
		List<String> fields = new ArrayList<String>();
		fields.add("idItemConfiguracao");
		fields.add("identificacao");
		fields.add("nome");
		return this.listConvertion(getBean(), dados, fields);

	}

	/**
	 * Verifica se existe midia vinculada. Se existir retorna 'true', senao retorna 'false';
	 *
	 * @param itemConfiguracao
	 * @return exists
	 * @throws Exception
	 */
	public boolean verificaMidiaSoftware(Integer idMidiaSoftware) throws PersistenceException {
		boolean exists;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select idItemConfiguracao from " + getTableName() + " where idMidiaSoftware = ? and datafim is null  ");
		parametro.add(idMidiaSoftware);
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			exists = true;
		} else {
			exists = false;
		}
		return exists;
	}

	/**
	 * Verifica quantidade de midias. Retorna quantidade
	 *
	 * @param idMidiaSoftware
	 * @return exists
	 * @throws Exception
	 */
	public Integer quantidadeMidiaSoftware(ItemConfiguracaoDTO itemDTO) throws PersistenceException {
		boolean exists;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select *from " + getTableName() + " itemConfiguracao inner join midiasoftware midiaSoftware "
				+ " on midiaSoftware.idMidiaSoftware = itemConfiguracao.idMidiaSoftware  where midiaSoftware.idMidiaSoftware = ? and midiaSoftware.datafim is null ");

		parametro.add(itemDTO.getIdMidiaSoftware());
		if(itemDTO.getIdItemConfiguracao()!=null) {
			sql.append(" and itemconfiguracao.iditemconfiguracao not in (select idItemConfiguracao from " + getTableName() + " where idItemConfiguracao = ?) ");
			parametro.add(itemDTO.getIdItemConfiguracao());
		}
		list = this.execSQL(sql.toString(), parametro.toArray());
		return list.size();
	}

	/**
	 * Verifica se existe outro item igual criado. Se existir retorna 'true', senao retorna 'false';
	 *
	 * @param itemConfiguracao
	 * @return estaCadastrado
	 * @throws Exception
	 */
	public boolean VerificaSeCadastrado(ItemConfiguracaoDTO itemDTO) throws PersistenceException {
		boolean estaCadastrado;

		List parametro = new ArrayList();
		List list = new ArrayList();
		List fields = new ArrayList();

		StringBuilder sql = new StringBuilder();
		sql.append("select idItemConfiguracao from " + getTableName() + " where ( LOWER(identificacao) =  LOWER(?) ");
		parametro.add(itemDTO.getIdentificacao());
		sql.append("or LOWER(nome) = LOWER(?) ) ");
		parametro.add(itemDTO.getNome());
		sql.append("and datafim is null ");
		sql.append("and iditemconfiguracaopai is null ");
		if(itemDTO.getIdItemConfiguracao() != null){
			sql.append("and idItemConfiguracao <> ? ");
			parametro.add(itemDTO.getIdItemConfiguracao());
		}

		list = this.execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			estaCadastrado = true;
		} else {
			estaCadastrado = false;
		}
		return estaCadastrado;
	}

	/**
	 * Verifica duplicidade de um item de configuração com o mesmo nome e se o item retornado na lista não é o próprio item que está sendo consultado para permitir alteração. Caso exista retorna TRUE.
	 * Caso não exista retorna FALSE;
	 * 
	 * @author cleziomar.egidio 25/11/2014
	 */
	public boolean validaDuplicidadeItemConfiguracao(ItemConfiguracaoDTO bean) throws PersistenceException {
		List condicao = new ArrayList();
		List result = null;
		if(bean != null){
			condicao.add(new Condition("identificacao", "=", bean.getIdentificacao()));
		}
		if (bean != null && bean.getIdItemConfiguracaoPai() != null) {
			condicao.add(new Condition("idItemConfiguracaoPai", "=", bean.getIdItemConfiguracaoPai()));
			result = (List) super.findByCondition(condicao, null);
		} else {
			if(bean != null){
				condicao.add(new Condition("idItemConfiguracao", "=", bean.getIdItemConfiguracao()));
			}
			condicao.add(new Condition("idItemConfiguracaoPai", "=", "is null"));
			result = (List) super.findByCondition(condicao, null);
		}

		if (result != null && result.size() > 0) {
			if (result.size() == 1) {
				try{
						ItemConfiguracaoDTO itemCorrente = (ItemConfiguracaoDTO) result.get(0);
					if (itemCorrente.getIdItemConfiguracao().intValue() == bean.getIdItemConfiguracao().intValue()) {
							return false;
					} else
						return true;

				} catch (Exception e) {
					 e.printStackTrace();
					 return true;

				}

			} else
				return true;

		}

		return false;
	}

	// bruno.aquino
	public Collection listItemConfiguracaoPorSolicitacaoServicoEmAndamento(int idSolicitante, String situacao, String idservico) throws PersistenceException {

		List fields = new ArrayList();
		String sql = null;
		List parametros = new ArrayList();
		List resultado;

		if(idservico.equalsIgnoreCase("")){

			sql = "select idSolicitacaoServico,DescricaoSemFormatacao,idItemConfiguracao from solicitacaoservico  where iditemconfiguracao is not null and situacao =  ? and idsolicitante = ?";
			parametros.add(situacao);
			parametros.add(idSolicitante);

		}else{

			sql = "select idSolicitacaoServico,DescricaoSemFormatacao,idItemConfiguracao from solicitacaoservico  where iditemconfiguracao is not null and situacao =  ? and idsolicitante = ? and idsolicitacaoservico = ?";
			parametros.add(situacao);
			parametros.add(idSolicitante);
			parametros.add(idservico);
		}

		resultado = this.execSQL(sql, parametros.toArray());
		fields.add("idSolicitacaoServico");
		fields.add("DescricaoSemFormatacao");
		fields.add("idItemConfiguracao");
		return (Collection<SolicitacaoServicoDTO>) listConvertion(SolicitacaoServicoDTO.class, resultado, fields);
	}

	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdMudanca(Integer idMudanca) throws PersistenceException {
		String sql = "SELECT ic.identificacao, ic.familia, ic.classe, ic.localidade FROM " + getTableName()
				+ " AS ic INNER JOIN requisicaomudancaitemconfiguracao AS eic ON eic.iditemconfiguracao = ic.iditemconfiguracao "
				+ "INNER JOIN requisicaomudanca AS rq ON rq.idrequisicaomudanca = eic.idrequisicaomudanca WHERE rq.idrequisicaomudanca = ?";
		List<?> dados = this.execSQL(sql, new Object[] { idMudanca });
		List<String> fields = new ArrayList<String>();
		fields.add("identificacao");
		fields.add("familia");
		fields.add("classe");
		fields.add("localidade");
		return this.listConvertion(getBean(), dados, fields);
	}

	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdProblema(Integer problema) throws PersistenceException {
		String sql = "SELECT ic.identificacao, ic.familia, ic.classe, ic.localidade FROM " + getTableName()
				+ " AS ic INNER JOIN problemaitemconfiguracao AS eic ON eic.iditemconfiguracao = ic.iditemconfiguracao "
				+ "INNER JOIN problema AS rq ON rq.idproblema = eic.idproblema WHERE rq.idproblema = ?";
		List<?> dados = this.execSQL(sql, new Object[] { problema });
		List<String> fields = new ArrayList<String>();
		fields.add("identificacao");
		fields.add("familia");
		fields.add("classe");
		fields.add("localidade");
		return this.listConvertion(getBean(), dados, fields);
	}

	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdIncidente(Integer idIncidente) throws PersistenceException {
		String sql = "SELECT ic.identificacao, ic.familia, ic.classe, ic.localidade FROM " + getTableName()
				+ " AS ic INNER JOIN itemcfgsolicitacaoserv AS eic ON eic.iditemconfiguracao = ic.iditemconfiguracao "
				+ "INNER JOIN solicitacaoServico AS rq ON rq.idsolicitacaoservico = eic.idsolicitacaoservico WHERE rq.idsolicitacaoservico = ?";
		List<?> dados = this.execSQL(sql, new Object[] { idIncidente });
		List<String> fields = new ArrayList<String>();
		fields.add("identificacao");
		fields.add("familia");
		fields.add("classe");
		fields.add("localidade");
		return this.listConvertion(getBean(), dados, fields);
	}

	public Collection<ItemConfiguracaoDTO> quantidadeItemConfiguracaoPorBaseConhecimento(ItemConfiguracaoDTO itemConfiguracao) throws PersistenceException {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();

		sql.append("select idbaseconhecimento,count(idbaseconhecimento) from ITEMCONFIGURACAO where idbaseconhecimento = ? group by idbaseconhecimento");

		parametro.add(itemConfiguracao.getIdBaseConhecimento());
		listRetorno.add("idBaseConhecimento");
		listRetorno.add("quantidade");

		List list = execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			Collection<ItemConfiguracaoDTO> listaQuantidadeItemConfiguracaoPorBaseConhecimento = this.listConvertion(ItemConfiguracaoDTO.class, list, listRetorno);
			return listaQuantidadeItemConfiguracaoPorBaseConhecimento;
		}
		return null;
	}

	public Collection<ItemConfiguracaoDTO> listaItemConfiguracaoPorBaseConhecimento(ItemConfiguracaoDTO itemConfiguracao) throws PersistenceException {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();

		sql.append("SELECT ic.identificacao, ic.datainicio, ic.versao, ic.familia, ic.dataexpiracao ");
		sql.append("FROM ITEMCONFIGURACAO ic where ic.idbaseconhecimento = ?");

		parametro.add(itemConfiguracao.getIdBaseConhecimento());

		listRetorno.add("identificacao");
		listRetorno.add("dataInicio");
		listRetorno.add("versao");
		listRetorno.add("familia");
		listRetorno.add("dataExpiracao");

		List list = execSQL(sql.toString(), parametro.toArray());

		if (list != null && !list.isEmpty()) {
			Collection<ItemConfiguracaoDTO> listaItemConfiguracaoPorBaseConhecimento = this.listConvertion(ItemConfiguracaoDTO.class, list, listRetorno);
			return listaItemConfiguracaoPorBaseConhecimento;
		}

		return null;
	}

	/**
	 * Retorna Itens de Configuração associadas a Base de Conhecimento.
	 *
	 * @param baseConhecimentoDto
	 * @return List<ItemConfiguracaoDTO>
	 * @throws Exception
	 * @author Vadoilo Damasceno
	 */
	public List<ItemConfiguracaoDTO> findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select itemconfiguracao.idItemConfiguracao, itemconfiguracao.identificacao from itemconfiguracao ");
		sql.append("inner join conhecimentoic on itemconfiguracao.iditemconfiguracao = conhecimentoic.iditemconfiguracao ");
		sql.append("where conhecimentoic.idbaseconhecimento = ? ");

		parametro.add(baseConhecimentoDto.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idItemConfiguracao");
		listRetorno.add("identificacao");

		if (list != null && !list.isEmpty()) {

			return (List<ItemConfiguracaoDTO>) this.listConvertion(getBean(), list, listRetorno);

		} else {

			return null;
		}
	}

	/**
	 * Retorna os itens de configuração que possuem o pacote office
	 * 
	 * @throws Exception
	 */
	public List<ItemConfiguracaoDTO> listaItemConfiguracaoOfficePak(ItemConfiguracaoDTO itemConfiguracaoDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select distinct itemconfiguracao.idItemConfiguracao, itemconfiguracao.idTipoItemConfiguracao, itemconfiguracao.identificacao, ");
		sql.append("(select identificacao from itemconfiguracao itemconfiguracaopai where itemconfiguracaopai.iditemconfiguracao = itemconfiguracao.iditemconfiguracaopai) identificacaoPai, ");
		sql.append("(select dtultimacaptura from itemconfiguracao itemconfiguracaopai where itemconfiguracaopai.iditemconfiguracao = itemconfiguracao.iditemconfiguracaopai) dtultimacaptura ");
		sql.append("from itemconfiguracao ");
		sql.append("inner join valor valor on valor.iditemconfiguracao = itemconfiguracao.iditemconfiguracao ");
		sql.append("inner join caracteristica caracteristica on caracteristica.idcaracteristica  = valor.idcaracteristica ");
		sql.append("where caracteristica.nomecaracteristica = 'OFFICEKEY' ");

		if(itemConfiguracaoDTO.getMidiaSoftwareChaves() != null) {
			if(itemConfiguracaoDTO.getContem() != null && itemConfiguracaoDTO.getContem().equals("S"))
				sql.append("and valor.valorstr in( ");
			else
				sql.append("and valor.valorstr not in( ");
			boolean flag = false;
			for (MidiaSoftwareChaveDTO object : itemConfiguracaoDTO.getMidiaSoftwareChaves()) {
				if(!flag) {
					sql.append(" '" + object.getChave() + "' ");
					flag = true;
				} else {
					sql.append(" , '" + object.getChave() + "' ");
				}
			}
			sql.append(")");
		}

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idItemConfiguracao");
		listRetorno.add("idTipoItemConfiguracao");
		listRetorno.add("identificacao");
		listRetorno.add("identificacaoPai");
		listRetorno.add("dtUltimaCaptura");

		if (list != null && !list.isEmpty()) {
			return (List<ItemConfiguracaoDTO>) this.listConvertion(getBean(), list, listRetorno);
		} else {
			return null;
		}
	}

	/**
	 * Retorna os itens de configuração que possuem o pacote office
	 * 
	 * @throws Exception
	 */
	public List<ItemConfiguracaoDTO> listaItemConfiguracaoOfficePak(ItemConfiguracaoDTO itemConfiguracaoDTO, String chave) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select distinct itemconfiguracao.idItemConfiguracao, itemconfiguracao.idTipoItemConfiguracao, itemconfiguracao.identificacao, ");
		sql.append("(select identificacao from itemconfiguracao itemconfiguracaopai where itemconfiguracaopai.iditemconfiguracao = itemconfiguracao.iditemconfiguracaopai) identificacaoPai, ");
		sql.append("(select dtultimacaptura from itemconfiguracao itemconfiguracaopai where itemconfiguracaopai.iditemconfiguracao = itemconfiguracao.iditemconfiguracaopai) dtultimacaptura ");
		sql.append("from itemconfiguracao ");
		sql.append("inner join valor valor on valor.iditemconfiguracao = itemconfiguracao.iditemconfiguracao ");
		sql.append("inner join caracteristica caracteristica on caracteristica.idcaracteristica  = valor.idcaracteristica ");
		sql.append("where caracteristica.nomecaracteristica = 'OFFICEKEY' ");
		sql.append("and valor.valorstr = '" + chave + "'");

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idItemConfiguracao");
		listRetorno.add("idTipoItemConfiguracao");
		listRetorno.add("identificacao");
		listRetorno.add("identificacaoPai");
		listRetorno.add("dtUltimaCaptura");

		if (list != null && !list.isEmpty()) {
			return (List<ItemConfiguracaoDTO>) this.listConvertion(getBean(), list, listRetorno);
		} else {
			return null;
		}
	}

	public boolean atualizaIdGrupoPadrao(ItemConfiguracaoDTO ic, int id){
		String sql = "update " + getTableName() + " set idGrupoItemConfiguracao = ? where idItemConfiguracao = ? ";
		Object[] parametros = {id, ic.getIdItemConfiguracao()};
		try {
			this.execUpdate(sql, parametros);
			return true;
		} catch (PersistenceException e) {
			return false;
		}
	}

	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdLiberacao(Integer idLiberacao) throws PersistenceException {
		String sql = "SELECT ic.identificacao, ic.familia, ic.classe, ic.localidade FROM " + getTableName()
				+ " AS ic INNER JOIN requisicaoliberacaoitemconfiguracao AS eic ON eic.iditemconfiguracao = ic.iditemconfiguracao "
				+ "INNER JOIN liberacao AS rq ON rq.idliberacao = eic.idrequisicaoliberacao WHERE rq.idliberacao = ?";
		List<?> dados = this.execSQL(sql, new Object[] { idLiberacao });
		List<String> fields = new ArrayList<String>();
		fields.add("identificacao");
		fields.add("familia");
		fields.add("classe");
		fields.add("localidade");
		return this.listConvertion(getBean(), dados, fields);
	}

	public boolean atualizaStatus(Integer item, Integer status){
		String sql = "update " + getTableName() + " set status = ? where idItemConfiguracao = ? ";
		Object[] parametros = {status, item};
		try {
			this.execUpdate(sql, parametros);
			return true;
		} catch (PersistenceException e) {
			return false;
		}
	}

	/**
	 * Para finalizar O Item Configuração é necessário adicionar Data Fim
	 *
	 * @param itemConfiguracaoDTO
	 * @author maycon.silva
	 */
	public void finalizarItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO) {
		try {
			updateNotNull(itemConfiguracaoDTO);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<ItemConfiguracaoDTO> listItemConfiguracaoFinalizadosByIdItemconfiguracao(ItemConfiguracaoDTO itemConfiguracaoDto) throws PersistenceException {
		List parametro = new ArrayList();

		String sql = " select  idItemConfiguracao	,identificacao	,familia	,classe	,idProprietario	,localidade	,status	,versao	,criticidade	,numeroSerie	,dataExpiracao	,idItemConfiguracaoPai	,idTipoItemConfiguracao	,idGrupoItemConfiguracao	,idProblema "
				+ " ,idIncidente	,idMudanca	,dataInicio	,dataFim	,idMidiaSoftware	,impacto	,urgencia	,idbaseconhecimento	,dtultimacaptura	,idliberacao	,idcontrato	,idresponsavel	, ativofixo from itemconfiguracao  where iditemconfiguracaoPai = ?  ";

		if(itemConfiguracaoDto != null){
			parametro.add(itemConfiguracaoDto.getIdItemConfiguracao());
		}
		if(itemConfiguracaoDto != null && itemConfiguracaoDto.getDataFim() != null){
			sql +=  " and  dataFim  =  ?  ";
			parametro.add(itemConfiguracaoDto.getDataFim());
		}

		if(itemConfiguracaoDto != null && itemConfiguracaoDto.getDatahoradesinstalacao() != null){
			sql +=  " and  datahoradesinstalacao  =  ?  ";
			parametro.add(itemConfiguracaoDto.getDatahoradesinstalacao());
		}else{
			return null;
		}

	 	List dados = execSQL(sql, parametro.toArray());

		return (List<ItemConfiguracaoDTO>) listConvertion (getBean(), dados, this.getField()) ;
	}

	public ItemConfiguracaoDTO findByIdentificacaoItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sqlItemConfiguracao = new StringBuilder();

		sqlItemConfiguracao.append("SELECT idItemConfiguracao, identificacao ");
		sqlItemConfiguracao.append("FROM itemConfiguracao WHERE dataFim IS NULL ");
		sqlItemConfiguracao.append("AND identificacao = ?");

		parametro.add(itemConfiguracaoDTO.getIdentificacao());

		List lista = this.execSQL(sqlItemConfiguracao.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("idItemConfiguracao");
		listRetorno.add("identificacao");

		if (lista != null && !lista.isEmpty()) {
			return (ItemConfiguracaoDTO) this.engine.listConvertion(getBean(), lista, listRetorno).get(0);
		} else {
			return null;
		}
	}

	public List getField() {
		List listFields = new ArrayList<>();
		listFields.add("idItemConfiguracao");
		listFields.add("identificacao");
		listFields.add("familia");
		listFields.add("classe");
		listFields.add("idProprietario");
		listFields.add("localidade");
		listFields.add("status");
		listFields.add("versao");
		listFields.add("criticidade");
		listFields.add("numeroSerie");
		listFields.add("dataExpiracao");
		listFields.add("idItemConfiguracaoPai");
		listFields.add("idTipoItemConfiguracao");
		listFields.add("idGrupoItemConfiguracao");
		listFields.add("idProblema");
		listFields.add("idIncidente");
		listFields.add("idMudanca");
		listFields.add("dataInicio");
		listFields.add("dataFim");
		listFields.add("idMidiaSoftware");
		listFields.add("impacto");
		listFields.add("urgencia");
		listFields.add("idbaseconhecimento");
		listFields.add("dtultimacaptura");
		listFields.add("idliberacao");
		listFields.add("idcontrato");
		listFields.add("idresponsavel");
		listFields.add("ativofixo");
		return listFields;
	}

	public Collection<ItemConfiguracaoDTO> listByIdGrupoAndTipoItemAndIdItemPaiAtivos(Integer idGrupo, Integer idTipo, Integer idPai) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sb = new StringBuilder();

		sb.append("SELECT " + this.getNamesFieldsStr());
		sb.append("FROM " + getTableName() + " ");
		if (idGrupo == null){
			sb.append("Where idgrupoitemconfiguracao is null ");
		}else{
			sb.append("Where idgrupoitemconfiguracao = ? ");
			parametro.add(idGrupo);
		}
		sb.append(" and datafim is null ");
		sb.append(" and and idtipoitemconfiguracao = ? ");
		parametro.add(idTipo);
		sb.append(" and and iditemconfiguracaopai = ? ");
		parametro.add(idPai);
		sb.append(" order by iditemconfiguracao ");

		List lista = this.execSQL(sb.toString(), parametro.toArray());
		List<String> listRetorno = this.getListNamesFieldClass();

		List result = this.engine.listConvertion(ItemConfiguracaoDTO.class, lista, listRetorno);
		if (result != null) {
			return (Collection<ItemConfiguracaoDTO>) result;
		} else {
			return null;
		}

	}

	public Collection<ItemConfiguracaoDTO> listByIdItemPaiAndTagTipoItemCfg(Integer idItemConfiguracaoPai, String tagTipoCfg) throws PersistenceException {
		String sql = "SELECT " + this.getNamesFieldsStr() + " " + "FROM itemconfiguracao where iditemconfiguracaopai = ? AND idtipoitemconfiguracao IN "
				+ "(SELECT idtipoitemconfiguracao FROM tipoitemconfiguracao where tagtipoitemconfiguracao = ?) " + "AND datafim IS NULL";
		List<?> dados = this.execSQL(sql, new Object[] { idItemConfiguracaoPai, tagTipoCfg });
		List<String> fields = this.getListNamesFieldClass();
		return this.listConvertion(getBean(), dados, fields);
	}

	public ItemConfiguracaoDTO findByIdItemConfiguracaoWithIdentificacaoPai(Integer idItemConfiguracao) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();
		List listRetorno = new ArrayList();

		sql.append("SELECT ic.identificacao, icp.identificacao ");
		sql.append("FROM itemconfiguracao ic ");
		sql.append("JOIN itemconfiguracao icp ON icp.iditemconfiguracao = ic.iditemconfiguracaopai ");
		sql.append("WHERE ic.iditemconfiguracao = ? ");
		parametros.add(idItemConfiguracao);

		List retorno = this.execSQL(sql.toString(), parametros.toArray());

		listRetorno.add("identificacao");
		listRetorno.add("identificacaoPai");

		List result = this.engine.listConvertion(ItemConfiguracaoDTO.class, retorno, listRetorno);

		if (result != null && !result.isEmpty())
			return (ItemConfiguracaoDTO) result.get(0);
		else
			return null;
}
}
