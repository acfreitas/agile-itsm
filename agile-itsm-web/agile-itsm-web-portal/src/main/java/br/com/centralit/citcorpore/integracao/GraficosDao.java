package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.CategoriaServicoDTO;
import br.com.centralit.citcorpore.bean.GraficoPizzaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RiscoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

/**
 * @author rosana.godinho
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GraficosDao extends CrudDaoDefaultImpl {

    public GraficosDao() {
    	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public String getTableName() {
    	return null;
    }

    public Collection find(IDto obj) throws PersistenceException {
    	return null;
    }

    public Class getBean() {
    	return CategoriaServicoDTO.class;
    }

    @Override
    public Collection<Field> getFields() {
    	return null;
    }

    @Override
    public Collection list() throws PersistenceException {
    	return null;
    }

    public ArrayList<GraficoPizzaDTO> getRelatorioPorNomeCategoria() {

    	StringBuilder sql = new StringBuilder();
			sql.append("SELECT nomecategoriaservico as valor1, COUNT(*) as valor2 ");
			sql.append("FROM solicitacaoservico ");
			sql.append("INNER JOIN execucaosolicitacao ");
			sql.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
			sql.append("INNER JOIN tipodemandaservico ");
			sql.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
			sql.append("INNER JOIN servicocontrato ");
			sql.append("ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
			sql.append("INNER JOIN servico ");
			sql.append("ON servico.idservico = servicocontrato.idservico ");
			sql.append("INNER JOIN categoriaservico ");
			sql.append("ON categoriaservico.idcategoriaservico = servico.idcategoriaservico ");
			sql.append("WHERE tipodemandaservico.classificacao LIKE 'I' ");
			sql.append("AND (UPPER(solicitacaoservico.situacao) <> 'FECHADA' AND UPPER(solicitacaoservico.situacao) <> 'CANCELADA' ");
			sql.append("AND UPPER(solicitacaoservico.situacao) <> 'RECLASSIFICADA') ");
			sql.append("GROUP BY nomecategoriaservico ");

		List<String> camposRetorno = new ArrayList<String>();
		ArrayList<GraficoPizzaDTO> result = new ArrayList<GraficoPizzaDTO>();
		List lista = null;
		try {
		    lista = this.execSQL(sql.toString(), null);
		    camposRetorno.add("campo");
		    camposRetorno.add("valor");

		    result = (ArrayList)this.engine.listConvertion(GraficoPizzaDTO.class, lista, camposRetorno);
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return result;
	}

    public ArrayList<GraficoPizzaDTO> getRelatorioPorSituacao() {

    	StringBuilder sql = new StringBuilder();
			sql.append("SELECT situacao as valor1, COUNT(*) as valor2 ");
			sql.append("FROM solicitacaoservico ");
			sql.append("INNER JOIN execucaosolicitacao ");
			sql.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
			sql.append("INNER JOIN tipodemandaservico ");
			sql.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
			sql.append("INNER JOIN servicocontrato ");
			sql.append("ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ");
			sql.append("WHERE tipodemandaservico.classificacao LIKE 'I' ");
			sql.append("AND (UPPER(solicitacaoservico.situacao) <> 'FECHADA' AND UPPER(solicitacaoservico.situacao) <> 'CANCELADA' ");
			sql.append("AND UPPER(solicitacaoservico.situacao) <> 'RECLASSIFICADA') ");
			sql.append("GROUP BY situacao ");

		List<String> camposRetorno = new ArrayList<String>();
		ArrayList<GraficoPizzaDTO> result = new ArrayList<GraficoPizzaDTO>();
		List lista = null;
		try {
		    lista = this.execSQL(sql.toString(), null);
		    camposRetorno.add("campo");
		    camposRetorno.add("valor");

		    result = (ArrayList)this.engine.listConvertion(GraficoPizzaDTO.class, lista, camposRetorno);
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return result;
    }

    public ArrayList<GraficoPizzaDTO> getRelatorioPorGrupo() {

    	StringBuilder sql = new StringBuilder();
			sql.append("SELECT grupo.nome as valor1, COUNT(*) as valor2 ");
			sql.append("FROM solicitacaoservico ");
			sql.append("INNER JOIN execucaosolicitacao ");
			sql.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
			sql.append("INNER JOIN tipodemandaservico ");
			sql.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ");
			sql.append("INNER JOIN grupo ");
			sql.append("ON solicitacaoservico.idgrupoatual = grupo.idgrupo ");
			sql.append("WHERE tipodemandaservico.classificacao LIKE 'I' ");
			sql.append("AND (UPPER(solicitacaoservico.situacao) <> 'FECHADA' AND UPPER(solicitacaoservico.situacao) <> 'CANCELADA' ");
			sql.append("AND UPPER(solicitacaoservico.situacao) <> 'RECLASSIFICADA') ");
			sql.append("GROUP BY grupo.nome ");

		List<String> camposRetorno = new ArrayList<String>();
		ArrayList<GraficoPizzaDTO> result = new ArrayList<GraficoPizzaDTO>();
		List lista = null;
		try {
		    lista = this.execSQL(sql.toString(), null);
		    camposRetorno.add("campo");
		    camposRetorno.add("valor");

		    result = (ArrayList)this.engine.listConvertion(GraficoPizzaDTO.class, lista, camposRetorno);
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return result;
    }

    /**
     * @author rodrigo.oliveira
     * @param HashMap de parâmetros
     * @return Collection de resultados da busca
     */

    public Collection consultaIncidentesOrigem(HashMap parametros, String tipo){


		List parametrosBusca = new ArrayList();

    	//TODO I para indicentes e R para requisiçoes
    	parametrosBusca.add(tipo);

    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));

    	StringBuilder sql = new StringBuilder();


		sql.append("SELECT origematendimento.descricao as valor1, COUNT(*) as valor2 FROM solicitacaoservico ")
			.append("INNER JOIN execucaosolicitacao ")
			.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ")
			.append("INNER JOIN tipodemandaservico ")
			.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ")
			.append("INNER JOIN origematendimento ")
			.append("ON solicitacaoservico.idorigem = origematendimento.idorigem ")
			.append("INNER JOIN servicocontrato ")
			.append("ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ")
			.append("INNER JOIN servico ")
			.append("ON servicocontrato.idservico = servico.idservico ")
			.append("INNER JOIN tiposervico ")
			.append("ON servico.idtiposervico = tiposervico.idtiposervico ")
			.append("WHERE tipodemandaservico.classificacao LIKE ? ")
			.append("AND (servicocontrato.idservico = ? OR ? = -1) ")
			.append("AND (servicocontrato.idcontrato = ? OR ? = -1) ")
			.append("AND (tiposervico.idtiposervico = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idprioridade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idorigem = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idunidade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idfaseatual = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.situacao = ? OR ? = '*') ")
			.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ")
			.append("GROUP BY origematendimento.descricao ");

		this.acrescentarNaSqlOLimitadorDeRegistros(sql, obterValorParametroNumeroMaximoDeRegistros(parametros));

		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

    	return lista;
    }

	/**
	 * Retorna o Número Máximo de registros que deverá ser retornado na consulta.
	 *
	 * @param parametros
	 * @return Número Máximo de Registros
	 * @author valdoilo.damasceno
	 */
	private Integer obterValorParametroNumeroMaximoDeRegistros(HashMap parametros) {

		String valor = (String) parametros.get("PARAM.topList");

		if (StringUtils.isNotBlank(valor) && !valor.trim().equals("*")) {

			try {

				return new Integer(valor);

			} catch (NumberFormatException e) {

				return null;
			}

		} else {
			return null;
		}
	}

	/**
	 * Acrescenta no SQL a condição para limitar o retorno de registros de acordo com o valor selecionado.
	 *
	 * @param sql
	 *            - String SQL.
	 * @param maximoRegistros
	 *            - Número máximo de registros.
	 * @author valdoilo.damasceno
	 */
	private void acrescentarNaSqlOLimitadorDeRegistros(StringBuilder sql, Integer maximoRegistros) {

		if (maximoRegistros == null)
			return;

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {

			sql.insert(0, "SELECT valor1, valor2 FROM (");
			sql.insert(sql.length(), ") where rownum <= " + maximoRegistros);

		} else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {

			sql.replace(0, 6, " select valor1, valor2 from (select ROW_NUMBER() OVER(order by (select 1)) rownum, ");
			sql.append(" ) as teste where rownum between 0 and " + maximoRegistros);

		} else {
			sql.append(" LIMIT " + maximoRegistros);
		}
	}

    /**
     * @author rodrigo.oliveira
     * @param HashMap de parâmetros
     * @return Collection de resultados da busca
     */
    public Collection consultaIncidentesPorFase(HashMap parametros, String tipo){

    	List parametrosBusca = new ArrayList();

    	//TODO I para indicentes e R para requisiçoes
    	parametrosBusca.add(tipo);

    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));

    	StringBuilder sql = new StringBuilder();

    	sql.append("SELECT nomefase as valor1, COUNT(*) as valor2 FROM solicitacaoservico ")
    		.append("INNER JOIN execucaosolicitacao ")
			.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ")
			.append("INNER JOIN tipodemandaservico ")
			.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ")
			.append("INNER JOIN faseservico ")
			.append("ON solicitacaoservico.idfaseatual = faseservico.idfase ")
			.append("INNER JOIN servicocontrato ")
			.append("ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ")
			.append("INNER JOIN servico ")
			.append("ON servicocontrato.idservico = servico.idservico ")
			.append("INNER JOIN tiposervico ")
			.append("ON servico.idtiposervico = tiposervico.idtiposervico ")
			.append("WHERE tipodemandaservico.classificacao LIKE ? ")
			.append("AND (servicocontrato.idservico = ? OR ? = -1) ")
			.append("AND (servicocontrato.idcontrato = ? OR ? = -1) ")
			.append("AND (tiposervico.idtiposervico = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idprioridade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idorigem = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idunidade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idfaseatual = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.situacao = ? OR ? = '*') ")
			.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ")
			.append("GROUP BY nomefase ");

    	this.acrescentarNaSqlOLimitadorDeRegistros(sql, obterValorParametroNumeroMaximoDeRegistros(parametros));

		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

    	return lista;
    }

    /**
     * @author rodrigo.oliveira
     * @param HashMap de parâmetros
     * @return Collection de resultados da busca
     */
    public Collection consultaIncidentesPorServico(HashMap parametros, String tipo){

    	List parametrosBusca = new ArrayList();

    	StringBuilder sql = new StringBuilder();

		sql.append("SELECT nomeservico as valor1, COUNT(*) as valor2 FROM solicitacaoservico ")
			.append("INNER JOIN execucaosolicitacao ")
			.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ")
			.append("INNER JOIN tipodemandaservico ")
			.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ")
			.append("INNER JOIN servicocontrato ")
			.append(" ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ")
			.append("INNER JOIN servico ")
			.append("ON servicocontrato.idservico = servico.idservico ")
			.append("INNER JOIN tiposervico ")
			.append("ON servico.idtiposervico = tiposervico.idtiposervico ")
			.append("WHERE tipodemandaservico.classificacao LIKE ? ")
			.append("AND (servicocontrato.idservico = ? OR ? = -1) ")
			.append("AND (servicocontrato.idcontrato = ? OR ? = -1) ")
			.append("AND (tiposervico.idtiposervico = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idprioridade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idorigem = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idunidade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idfaseatual = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.situacao = ? OR ? = '*') ")
			.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ")
			.append("GROUP BY nomeservico ");

    	//TODO I para indicentes e R para requisiçoes
    	parametrosBusca.add(tipo);
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));

		this.acrescentarNaSqlOLimitadorDeRegistros(sql, obterValorParametroNumeroMaximoDeRegistros(parametros));

		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

    	return lista;
    }

    /**
     * @author rodrigo.oliveira
     * @param HashMap de parâmetros
     * @return Collection de resultados da busca
     */
    public Collection consultaIncidentesPorSituacao(HashMap parametros, String tipo){

    	List parametrosBusca = new ArrayList();

    	//TODO I para indicentes e R para requisiçoes
    	parametrosBusca.add(tipo);

    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));

    	StringBuilder sql = new StringBuilder();

		sql.append("SELECT solicitacaoservico.situacao as valor1, COUNT(*) as valor2 FROM solicitacaoservico ")
			.append("INNER JOIN execucaosolicitacao ")
			.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ")
			.append("INNER JOIN tipodemandaservico ")
			.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ")
			.append("INNER JOIN servicocontrato ")
			.append("ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ")
			.append("INNER JOIN servico ")
			.append("ON servicocontrato.idservico = servico.idservico ")
			.append("INNER JOIN tiposervico ")
			.append("ON servico.idtiposervico = tiposervico.idtiposervico ")
			.append("WHERE tipodemandaservico.classificacao LIKE ? ")
			.append("AND (servicocontrato.idservico = ? OR ? = -1) ")
			.append("AND (servicocontrato.idcontrato = ? OR ? = -1) ")
			.append("AND (tiposervico.idtiposervico = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idprioridade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idorigem = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idunidade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idfaseatual = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.situacao = ? OR ? = '*') ")
			.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ")
			.append("GROUP BY solicitacaoservico.situacao ");

		this.acrescentarNaSqlOLimitadorDeRegistros(sql, obterValorParametroNumeroMaximoDeRegistros(parametros));

		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

    	return lista;
    }

    /**
     * @author rodrigo.oliveira
     * @param HashMap de parâmetros
     * @return Collection de resultados da busca
     */
    public Collection consultaIncidentesPorUnidade(HashMap parametros, String tipo){

    	List parametrosBusca = new ArrayList();

    	//TODO I para indicentes e R para requisiçoes
    	parametrosBusca.add(tipo);

    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));

    	StringBuilder sql = new StringBuilder();

		sql.append("SELECT unidade.nome as valor1, COUNT(*) as valor2 FROM solicitacaoservico ")
			.append("INNER JOIN execucaosolicitacao ")
			.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ")
			.append("INNER JOIN tipodemandaservico ")
			.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ")
			.append("LEFT OUTER JOIN unidade ")
			.append("ON solicitacaoservico.idunidade = unidade.idunidade ")
			.append("INNER JOIN servicocontrato ")
			.append("ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ")
			.append("INNER JOIN servico ")
			.append("ON servicocontrato.idservico = servico.idservico ")
			.append("INNER JOIN tiposervico ")
			.append("ON servico.idtiposervico = tiposervico.idtiposervico ")
			.append("WHERE tipodemandaservico.classificacao LIKE ? ")
			.append("AND (servicocontrato.idservico = ? OR ? = -1) ")
			.append("AND (servicocontrato.idcontrato = ? OR ? = -1) ")
			.append("AND (tiposervico.idtiposervico = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idprioridade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idorigem = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idunidade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idfaseatual = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.situacao = ? OR ? = '*') ")
			.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ")
			.append("GROUP BY unidade.nome ");

		this.acrescentarNaSqlOLimitadorDeRegistros(sql, obterValorParametroNumeroMaximoDeRegistros(parametros));

		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

    	return lista;
    }

    /**
     * @author rodrigo.oliveira
     * @param HashMap de parâmetros
     * @return Collection de resultados da busca
     */
    public Collection consultaIncidentesPrioridade(HashMap parametros, String tipo){

    	List parametrosBusca = new ArrayList();

    	//TODO I para indicentes e R para requisiçoes
    	parametrosBusca.add(tipo);

    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));

    	StringBuilder sql = new StringBuilder();

		sql.append("SELECT nomeprioridade as valor1, COUNT(*) as valor2 FROM solicitacaoservico ")
			.append("INNER JOIN execucaosolicitacao ")
			.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ")
			.append("INNER JOIN tipodemandaservico ")
			.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ")
			.append("INNER JOIN prioridade ")
			.append("ON solicitacaoservico.idprioridade = prioridade.idprioridade ")
			.append("INNER JOIN servicocontrato ")
			.append("ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ")
			.append("INNER JOIN servico ")
			.append("ON servicocontrato.idservico = servico.idservico ")
			.append("INNER JOIN tiposervico ")
			.append("ON servico.idtiposervico = tiposervico.idtiposervico ")
			.append("INNER JOIN contratos ")
			.append("ON servicocontrato.idcontrato = contratos.idcontrato ")
			.append("WHERE tipodemandaservico.classificacao LIKE ? ")
			.append("AND (servicocontrato.idservico = ? OR ? = -1) ")
			.append("AND (servicocontrato.idcontrato = ? OR ? = -1) ")
			.append("AND (tiposervico.idtiposervico = ? OR ? = -1)")
			.append("AND (solicitacaoservico.idprioridade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idorigem = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idunidade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idfaseatual = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.situacao = ? OR ? = '*') ")
			.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ")
			.append("GROUP BY nomeprioridade ");

		this.acrescentarNaSqlOLimitadorDeRegistros(sql, obterValorParametroNumeroMaximoDeRegistros(parametros));

		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

    	return lista;
    }

    /**
     * @author rodrigo.oliveira
     * @param HashMap de parâmetros
     * @return Collection de resultados da busca
     */
    public Collection consultaPesquisaSatisfacao(HashMap parametros, String tipo){

    	List parametrosBusca = new ArrayList();

    	// I para indicentes e R para requisiçoes, * para ambos
    	parametrosBusca.add(tipo);
    	parametrosBusca.add(tipo);

    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));


    	StringBuilder sql = new StringBuilder();

		sql.append("SELECT (CASE nota WHEN 4 THEN 'Ótimo' ")
			.append("WHEN 3 THEN 'Bom' ")
			.append("WHEN 2 THEN 'Regular' ")
			.append("WHEN 1 THEN 'Ruim' ")
			.append("ELSE '--' END) ")
			.append(", COUNT(*) ")
			.append("FROM solicitacaoservico ")
			.append("INNER JOIN execucaosolicitacao ")
			.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ")
			.append("LEFT OUTER JOIN pesquisasatisfacao ")
			.append("ON pesquisasatisfacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ")
			.append("INNER JOIN tipodemandaservico ")
			.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ")
			.append("INNER JOIN servicocontrato ")
			.append("ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ")
			.append("INNER JOIN servico ")
			.append("ON servicocontrato.idservico = servico.idservico ")
			.append("INNER JOIN tiposervico ")
			.append("ON servico.idtiposervico = tiposervico.idtiposervico ")
			.append("INNER JOIN contratos ")
			.append("ON servicocontrato.idcontrato = contratos.idcontrato ")
			.append("WHERE  ")
			.append("(tipodemandaservico.classificacao LIKE ? OR ? = '*') ")
			.append("AND (servicocontrato.idservico = ? OR ? = -1) ")
			.append("AND (servicocontrato.idcontrato = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idprioridade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idorigem = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idunidade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ")
			.append("GROUP BY NOTA");

		this.acrescentarNaSqlOLimitadorDeRegistros(sql, obterValorParametroNumeroMaximoDeRegistros(parametros));

		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

    	return lista;
    }

    /**
     * @author flavio.junior
     * @param HashMap de parâmetros
     * @return Collection de resultados da busca
     */
    public Collection consultaIncidentesPorContrato(HashMap parametros, String tipo){

    	List parametrosBusca = new ArrayList();

    	//TODO I para indicentes e R para requisiçoes
    	parametrosBusca.add(tipo);

    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));

    	StringBuilder sql = new StringBuilder();

		sql.append("SELECT contratos.numero as valor1, COUNT(*) as valor2 FROM solicitacaoservico ")
			.append("INNER JOIN execucaosolicitacao ")
			.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ")
			.append("INNER JOIN tipodemandaservico ")
			.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ")
			.append("LEFT OUTER JOIN unidade ")
			.append("ON solicitacaoservico.idunidade = unidade.idunidade ")
			.append("INNER JOIN servicocontrato ")
			.append("ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ")
			.append("INNER JOIN servico ")
			.append("ON servicocontrato.idservico = servico.idservico ")
			.append("INNER JOIN tiposervico ")
			.append("ON servico.idtiposervico = tiposervico.idtiposervico ")
			.append("INNER JOIN contratos ")
			.append("ON servicocontrato.idcontrato = contratos.idcontrato ")
			.append("WHERE tipodemandaservico.classificacao LIKE ? ")
			.append("AND (servicocontrato.idservico = ? OR ? = -1) ")
			.append("AND (servicocontrato.idcontrato = ? OR ? = -1) ")
			.append("AND (tiposervico.idtiposervico = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idprioridade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idorigem = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idunidade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idfaseatual = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.situacao = ? OR ? = '*') ")
			.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ")
			.append("GROUP BY contratos.numero");

		this.acrescentarNaSqlOLimitadorDeRegistros(sql, obterValorParametroNumeroMaximoDeRegistros(parametros));

		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}
    	return lista;
    }

    /**
     * @author flavio.junior
     * @param HashMap de parâmetros
     * @return Collection de resultados da busca
     */
    public Collection consultaIncidentesPorTipoServico(HashMap parametros, String tipo){

    	List parametrosBusca = new ArrayList();

    	//TODO I para indicentes e R para requisiçoes
    	parametrosBusca.add(tipo);

    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idServico")));
    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idContrato")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idTipoServico")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idPrioridade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idOrigem")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idUnidade")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idFase")));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.situacao"));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));

    	StringBuilder sql = new StringBuilder();

		sql.append("SELECT tiposervico.nometiposervico as valor1, COUNT(*) as valor2 FROM solicitacaoservico ")
			.append("INNER JOIN execucaosolicitacao ")
			.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ")
			.append("INNER JOIN tipodemandaservico ")
			.append("ON solicitacaoservico.idtipodemandaservico = tipodemandaservico.idtipodemandaservico ")
			.append("LEFT OUTER JOIN unidade ")
			.append("ON solicitacaoservico.idunidade = unidade.idunidade ")
			.append("INNER JOIN servicocontrato ")
			.append("ON solicitacaoservico.idservicocontrato = servicocontrato.idservicocontrato ")
			.append("INNER JOIN contratos ")
			.append("ON servicocontrato.idcontrato = contratos.idcontrato ")
			.append("INNER JOIN servico ")
			.append("ON servicocontrato.idservico = servico.idservico ")
			.append("INNER JOIN tiposervico ")
			.append("ON servico.idtiposervico = tiposervico.idtiposervico ")
			.append("WHERE tipodemandaservico.classificacao LIKE ? ")
			.append("AND (servicocontrato.idservico = ? OR ? = -1) ")
			.append("AND (servicocontrato.idcontrato = ? OR ? = -1) ")
			.append("AND (tiposervico.idtiposervico = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idprioridade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idorigem = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idunidade = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.idfaseatual = ? OR ? = -1) ")
			.append("AND (solicitacaoservico.situacao = ? OR ? = '*') ")
			.append("AND (solicitacaoservico.datahorasolicitacao BETWEEN ? AND ?) ")
			.append("GROUP BY tiposervico.nometiposervico ");

		this.acrescentarNaSqlOLimitadorDeRegistros(sql, obterValorParametroNumeroMaximoDeRegistros(parametros));

		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}
    	return lista;
    }

	public Collection consultaMudancaPorImpacto(HashMap parametros, String tipo){

	    	List parametrosBusca = new ArrayList();

	    	//TODO I para indicentes e R para requisiçoes
	    	//parametrosBusca.add(tipo);

	    	parametrosBusca.add(String.valueOf(parametros.get("PARAM.situacaoMudanca")));
	    	parametrosBusca.add(String.valueOf(parametros.get("PARAM.situacaoMudanca")));
	    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idtipomudanca")));
	    	parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idtipomudanca")));
	    	parametrosBusca.add(parametros.get("PARAM.dataInicial"));
			parametrosBusca.add(parametros.get("PARAM.dataFinal"));
			parametrosBusca.add(String.valueOf(parametros.get("PARAM.urgencia")));
			parametrosBusca.add(String.valueOf(parametros.get("PARAM.urgencia")));

	    	StringBuilder sql = new StringBuilder();

	    	if (tipo.equals("Baixa")) {
	    		sql.append("SELECT 'Baixa' AS impacto, COUNT(*) AS quantidade FROM requisicaomudanca ");
	    	}
	    	if (tipo.equals("Media")) {
	    		sql.append("SELECT 'Média' AS impacto, COUNT(*) AS quantidade FROM requisicaomudanca ");
	    	}
	    	if (tipo.equals("Alta")) {
	    		sql.append("SELECT 'Alta' AS impacto, COUNT(*) AS quantidade FROM requisicaomudanca ");
	    	}

	    		sql.append(" WHERE (status = ? OR ? = '*') ");
	    		sql.append(" AND (idtipomudanca = ? OR ? = -1) ");
				sql.append(" AND (DATAHORAINICIO BETWEEN ? AND ?) ");

			if (tipo.equals("Baixa")) {
				sql.append(" AND (nivelimpacto = 'B') ");
			}
			if (tipo.equals("Media")) {
				sql.append(" AND (nivelimpacto = 'M') ");
			}
			if (tipo.equals("Alta")) {
				sql.append(" AND (nivelimpacto = 'A') ");
			}
				sql.append(" AND (nivelurgencia = ? OR ? = '*') ");

			Collection lista = null;

			try {
				lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
			} catch (PersistenceException e) {
			    e.printStackTrace();
			} catch (Exception e) {
			    e.printStackTrace();
			}

	    	return lista;
	    }


	public Collection<RequisicaoMudancaDTO> consultaMudancaPorSLAAtrazo(HashMap parametros, String tipo) throws PersistenceException {

		List parametrosBusca = new ArrayList();
		List fields = new ArrayList();
		List list = new ArrayList();
		//TODO I para indicentes e R para requisiçoes
		//parametrosBusca.add(tipo);

		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idtipomudanca")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idtipomudanca")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.situacaoMudanca")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.situacaoMudanca")));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.impacto")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.impacto")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.urgencia")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.urgencia")));

		StringBuilder sql = new StringBuilder();


		sql.append("SELECT titulo, prazohh, prazomm, datahoraconclusao, datahoratermino, status FROM requisicaomudanca ");
		sql.append(" WHERE datahoratermino IS NOT NULL ");
		sql.append(" AND (idtipomudanca = ? OR ? = -1 ) ");
		sql.append(" AND (status = ? OR ? = '*') ");
		sql.append(" AND (DATAHORAINICIO BETWEEN ? AND ? ) ");
		sql.append(" AND (nivelimpacto = ? OR ? = '*' ) ");
		sql.append(" AND (nivelurgencia = ? OR ? = '*' ) ");

		list = this.execSQL(sql.toString(), parametrosBusca.toArray());

		fields.add("titulo");
		fields.add("prazoHH");
		fields.add("prazoMM");
		fields.add("dataHoraConclusao");
		fields.add("dataHoraTermino");
		fields.add("status");

		if (list != null && !list.isEmpty()) {
			return (List<RequisicaoMudancaDTO>) this.listConvertion(RequisicaoMudancaDTO.class, list, fields);
		} else {
			return null;
		}

	}

	public Collection consultaMudancaPorRisco(HashMap parametros, RiscoDTO riscoDTO){

		List parametrosBusca = new ArrayList();

		//TODO I para indicentes e R para requisiçoes
		//parametrosBusca.add(tipo);

		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.situacaoMudanca")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.situacaoMudanca")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.nivel")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.nivel")));

		StringBuilder sql = new StringBuilder();

		if (riscoDTO != null && riscoDTO.getNomeRisco() != null && riscoDTO.getIdRisco() != null) {
			sql.append("SELECT '"+riscoDTO.getNomeRisco()+"' AS risco, COUNT(*) AS quantidade FROM requisicaomudanca rm ");
			sql.append(" INNER JOIN requisicaomudancarisco rmr ON rmr.idrequisicaomudanca = rm.idrequisicaomudanca ");
			sql.append(" INNER JOIN risco r ON r.idrisco = rmr.idrisco ");
			sql.append(" WHERE r.idrisco = " + riscoDTO.getIdRisco());
			sql.append(" AND (r.datainicio BETWEEN ? AND ? ) ");
			sql.append(" AND (status = ? OR ? = '*') ");
			sql.append(" AND (r.nivelrisco = ? OR ? = -1 ) ");

		}


		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return lista;
	}

	public Collection consultaMudancaPorServico(HashMap parametros, String tipo){

		List parametrosBusca = new ArrayList();

		//TODO I para indicentes e R para requisiçoes
		//parametrosBusca.add(tipo);

		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idtipomudanca")));
		parametrosBusca.add(Integer.parseInt((String) parametros.get("PARAM.idtipomudanca")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.situacaoMudanca")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.situacaoMudanca")));
		parametrosBusca.add(parametros.get("PARAM.dataInicial"));
		parametrosBusca.add(parametros.get("PARAM.dataFinal"));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.impacto")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.impacto")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.urgencia")));
		parametrosBusca.add(String.valueOf(parametros.get("PARAM.urgencia")));

		StringBuilder sql = new StringBuilder();


		sql.append("SELECT rm.titulo, count(*) FROM requisicaomudanca rm ");
		sql.append("INNER JOIN requisicaomudancaservico rms ON rms.idrequisicaomudanca = rm.idrequisicaomudanca ");
		sql.append("INNER JOIN servico r ON r.idservico = rms.idservico ");
		sql.append(" WHERE (idtipomudanca = ? OR ? = -1) ");
		sql.append(" AND (status = ? OR ? = '*') ");
		sql.append(" AND (DATAHORAINICIO BETWEEN ? AND ?) ");
		sql.append(" AND (nivelimpacto = ? OR ? = '*') ");
		sql.append(" AND (nivelurgencia = ? OR ? = '*') ");
		sql.append(" GROUP BY rm.titulo ");

		Collection lista = null;

		try {
			lista = this.execSQL(sql.toString(), parametrosBusca.toArray());
		} catch (PersistenceException e) {
		    e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}

		return lista;
	}

}
