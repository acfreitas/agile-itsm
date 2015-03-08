package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
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
public class GrupoItemConfiguracaoDAO extends CrudDaoDefaultImpl {

	public GrupoItemConfiguracaoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idGrupoItemConfiguracao", "idGrupoItemConfiguracao", true, true, false, false));
		listFields.add(new Field("idGrupoItemConfiguracaoPai", "idGrupoItemConfiguracaoPai", false, false, false, false));
		listFields.add(new Field("nomeGrupoItemConfiguracao", "nomeGrupoItemConfiguracao", false, false, false, false));
		listFields.add(new Field("emailGrupoItemConfiguracao", "emailGrupoItemConfiguracao", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		return listFields;
	}

	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	public String getTableName() {
		return this.getOwner() + "grupoitemconfiguracao";
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		List condicao = new ArrayList();
		ordenacao.add(new Order("nomeGrupoItemConfiguracao"));
		condicao.add(new Condition("dataFim", "is", null));

		return super.findByCondition(condicao, ordenacao);
	}

	public Class getBean() {
		return GrupoItemConfiguracaoDTO.class;
	}


	/**
	 * Verifica se existe outro grupo igual criado.
	 * Se existir retorna 'true', senao retorna 'false';
	 *
	 * @param grupoItemConfiguracao
	 * @return estaCadastrado
	 * @throws PersistenceException
	 */
	public boolean VerificaSeCadastrado(GrupoItemConfiguracaoDTO grupoItemConfiguracao) throws PersistenceException {
		boolean estaCadastrado = false;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL)) {
			//Se o banco for POSTGRESQL utilizar 'ilike' se outro banco utilizar 'like'.
		    sql.append("select nomegrupoitemconfiguracao from " + getTableName() + "  where nomegrupoitemconfiguracao ilike ? and datafim is null  ");
		}else{
			sql.append("select nomegrupoitemconfiguracao from " + getTableName() + "  where nomegrupoitemconfiguracao like ? and datafim is null  ");
		}
		parametro.add(grupoItemConfiguracao.getNomeGrupoItemConfiguracao());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaCadastrado = true;
		}
		return estaCadastrado;
	}

	@Override
	public IDto createWithID(IDto obj) throws PersistenceException {
		return super.createWithID(obj);
	}

	/**
	 * Apenas verica se existe não aplicando a data fim
	 * (Antigamente duplicado)
	 *
	 * @param grupoItemConfiguracao
	 * @return estaCadastrado
	 * @throws PersistenceException
	 */
	public boolean verificaSeExiste(GrupoItemConfiguracaoDTO grupoItemConfiguracao) throws PersistenceException {
		boolean estaCadastrado;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select idGrupoItemConfiguracao from " + getTableName() + "  where  idGrupoItemConfiguracao = ? ");
		parametro.add(grupoItemConfiguracao.getIdGrupoItemConfiguracao());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaCadastrado = true;
		} else {
			estaCadastrado = false;
		}
		return estaCadastrado;
	}


	/**
	 * Verifica se existe relacionamento do grupo com outras tabelas.
	 * Se existir retorna 'true', se nao existir retorna 'false';
	 *
	 * @param grupoItemConfiguracao
	 * @return estaRelacionado
	 * @throws PersistenceException
	 */
	public boolean VerificaSeRelacionado(GrupoItemConfiguracaoDTO grupoItemConfiguracao) throws PersistenceException {
		boolean estaRelacionado;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select idGrupoItemConfiguracao from " + getTableName() + "  where  nomeGrupoItemConfiguracao = ? and datafim is null  ");
		parametro.add(grupoItemConfiguracao.getNomeGrupoItemConfiguracao());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaRelacionado = true;
		} else {
			estaRelacionado = false;
		}
		return estaRelacionado;
	}


	/**
	 * Verifica se existe relacionamento do grupo com item configuracao.
	 * Se existir retorna 'true', se nao existir retorna 'false';
	 *
	 * @param grupoItemConfiguracao
	 * @throws PersistenceException
	 */
	public boolean verificaICRelacionados(GrupoItemConfiguracaoDTO grupoItemConfiguracao) throws PersistenceException {
		boolean estaRelacionado;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select *from itemconfiguracao  where  idGrupoItemConfiguracao = ? and datafim is null  ");
		parametro.add(grupoItemConfiguracao.getIdGrupoItemConfiguracao());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			estaRelacionado = true;
		} else {
			estaRelacionado = false;
		}
		return estaRelacionado;
	}


	/**
	 * Lista os grupos associados ao evento passado como parametro.
	 *
	 * @param idEvento
	 * @return Collection<GrupoItemConfiguracaoDTO> relacionados ao evento
	 * @throws Exception
	 */
	public Collection<GrupoItemConfiguracaoDTO> listByEvento(Integer idEvento) throws PersistenceException {
		String sql = "SELECT idgrupoitemconfiguracao, nomegrupoitemconfiguracao FROM "
				+ getTableName() +" INNER JOIN eventogrupo AS eg ON eg.idgrupo = idgrupoitemconfiguracao WHERE eg.idevento = ?";
		List<?> dados = this.execSQL(sql, new Object[] { idEvento });
		List<String> fields = new ArrayList<String>();
		fields.add("idGrupoItemConfiguracao");
		fields.add("nomeGrupoItemConfiguracao");
		return this.listConvertion(getBean(), dados, fields);

	}

	/**
	 * Lista os grupos associados ao evento passado como parametro.
	 *
	 * @param IDPAI
	 * @return Collection<GrupoItemConfiguracaoDTO>
	 * @throws Exception
	 */
	public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracaoPai(Integer idGrupoItemConfiguracaoPai) throws PersistenceException {
		String sql = "SELECT idgrupoitemconfiguracao, nomegrupoitemconfiguracao FROM "
				+ getTableName() +"  WHERE idGrupoItemConfiguracaoPai = ? AND dataFim IS NULL";
		List<?> dados = this.execSQL(sql, new Object[] { idGrupoItemConfiguracaoPai });
		List<String> fields = new ArrayList<String>();
		fields.add("idGrupoItemConfiguracao");
		fields.add("nomeGrupoItemConfiguracao");
		return this.listConvertion(getBean(), dados, fields);

	}

	public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracaoPaiNull() throws PersistenceException {
		String sql = "SELECT idgrupoitemconfiguracao, nomegrupoitemconfiguracao FROM "
				+ getTableName() +"  WHERE idGrupoItemConfiguracaoPai IS NULL AND dataFim IS NULL";
		List<?> dados = this.execSQL(sql, null);
		List<String> fields = new ArrayList<String>();
		fields.add("idGrupoItemConfiguracao");
		fields.add("nomeGrupoItemConfiguracao");
		return this.listConvertion(getBean(), dados, fields);

	}

	/**
	 * Lista os grupos associados ao evento passado como parametro.
	 *
	 * @param IDPAI
	 * @return Collection<GrupoItemConfiguracaoDTO>
	 * @throws Exception
	 */
	///*Thiago Fernandes - 03/11/2013 - 09:40 - Sol. 121468 - Correção de sql de busca por lista os grupos associados ao evento passado como parametro.
	public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracaoDesenvolvimento(Integer idGrupoItemConfiguracaoPai) throws PersistenceException {
		String sql = "SELECT idgrupoitemconfiguracao, nomegrupoitemconfiguracao FROM "
				+ getTableName() +"  WHERE (idGrupoItemConfiguracaoPai = ? OR idGrupoItemConfiguracaoPai IS NULL) AND dataFim IS NULL";
		List<?> dados = this.execSQL(sql, new Object[] { idGrupoItemConfiguracaoPai });
		List<String> fields = new ArrayList<String>();
		fields.add("idGrupoItemConfiguracao");
		fields.add("nomeGrupoItemConfiguracao");
		return this.listConvertion(getBean(), dados, fields);

	}

	/**
	 * Lista os grupos associados ao evento passado como parametro.
	 *
	 * @param IDPAI
	 * @return Collection<GrupoItemConfiguracaoDTO>
	 * @throws Exception
	 */
	public Collection<GrupoItemConfiguracaoDTO> listByIdGrupoItemConfiguracao(Integer idGrupoItemConfiguracaoPai) throws PersistenceException {
		String sql = "SELECT idgrupoitemconfiguracao, nomegrupoitemconfiguracao FROM "
				+ getTableName() +"  WHERE idGrupoItemConfiguracaoPai = ? AND dataFim IS NULL";
		List<?> dados = this.execSQL(sql, new Object[] { idGrupoItemConfiguracaoPai });
		List<String> fields = new ArrayList<String>();
		fields.add("idGrupoItemConfiguracao");
		fields.add("nomeGrupoItemConfiguracao");
		return this.listConvertion(getBean(), dados, fields);

	}

	public boolean verificaGrupoPai(GrupoItemConfiguracaoDTO grupoItemConfiguracao) throws PersistenceException {
		boolean isOk = false;
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select idGrupoItemConfiguracao from " + getTableName() + "  where  idGrupoItemConfiguracaoPai = ? and idGrupoItemConfiguracao = ? and datafim is null  ");
		parametro.add(grupoItemConfiguracao.getIdGrupoItemConfiguracaoPai());
		parametro.add(grupoItemConfiguracao.getIdGrupoItemConfiguracao());
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			isOk = true;
		}
		return isOk;
	}

	public boolean verificaGrupoPadrao(GrupoItemConfiguracaoDTO grupoItemConfiguracao) throws PersistenceException {
		boolean isOk = false;
		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "select idGrupoItemConfiguracao from " + getTableName() + "  where  idGrupoItemConfiguracao = ? and datafim is null  ";
		parametro.add(grupoItemConfiguracao.getIdGrupoItemConfiguracao());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			isOk = true;
		}
		return isOk;
	}

	public void atualizaGrupoPadrao(GrupoItemConfiguracaoDTO g) throws PersistenceException {
		super.update(g);
		/*String sql = "update " + getTableName() + " set idGrupoItemConfiguracao = ? where nomegrupoitemconfiguracao = ? ";
		Object[] parametros = {g.getIdGrupoItemConfiguracao(),g.getNomeGrupoItemConfiguracao()};
		try {
			this.execUpdate(sql, parametros);
		} catch (PersistenceException e) {}*/
	}
}