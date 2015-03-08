package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class ValorDao extends CrudDaoDefaultImpl {

    public ValorDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();
	listFields.add(new Field("IDVALOR", "idValor", true, true, false, false));
	listFields.add(new Field("IDITEMCONFIGURACAO", "idItemConfiguracao", false, false, false, false));
	listFields.add(new Field("IDBASEITEMCONFIGURACAO", "idBaseItemConfiguracao", false, false, false, false));
	listFields.add(new Field("IDCARACTERISTICA", "idCaracteristica", false, false, false, false));
	listFields.add(new Field("VALORSTR", "valorStr", false, false, false, false));
	listFields.add(new Field("VALORLONGO", "valorLongo", false, false, false, false));
	listFields.add(new Field("VALORDECIMAL", "valorDecimal", false, false, false, false));
	listFields.add(new Field("VALORDATE", "valorDate", false, false, false, false));
	return listFields;
    }

    /**
     * Consulta valor por idItemConfiguracao.
     * 
     * @param idItemConfiguracao
     * @return Collection
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public Collection findByIdItemConfiguracao(Integer idItemConfiguracao) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("idItemConfiguracao", "=", idItemConfiguracao));
	ordenacao.add(new Order("numero"));
	return super.findByCondition(condicao, ordenacao);
    }

    /**
     * Exclui valor por idItemConfiguracao.
     * 
     * @param idItemConfiguracao
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public void deleteByIdItemConfiguracao(Integer idItemConfiguracao) throws PersistenceException {
	List condicao = new ArrayList();
	condicao.add(new Condition("idItemConfiguracao", "=", idItemConfiguracao));
	super.deleteByCondition(condicao);
    }

    /**
     * Consulta Valor por idCaracteristica.
     * 
     * @param idCaracteristica
     * @return Collection
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public Collection findByIdCaracteristica(Integer idCaracteristica) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	condicao.add(new Condition("idCaracteristica", "=", idCaracteristica));
	ordenacao.add(new Order("numero"));
	return super.findByCondition(condicao, ordenacao);
    }

    /**
     * Exclui valor por idCaracteristica.
     * 
     * @param idCaracteristica
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public void deleteByIdCaracteristica(Integer idCaracteristica) throws PersistenceException {
	List condicao = new ArrayList();
	condicao.add(new Condition("idCaracteristica", "=", idCaracteristica));
	super.deleteByCondition(condicao);
    }

    /**
     * Retorna Valor da Característica do Item Configuração.
     * 
     * @param idBaseItemConfiguracao
     * @param idCaracteristica
     * @return ValorDTO
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public ValorDTO restore(boolean isItemConfigruacao, Integer idBaseItemConfiguracao, Integer idCaracteristica) throws PersistenceException {
	List condicao = new ArrayList();
	if (isItemConfigruacao) {
	    condicao.add(new Condition("idItemConfiguracao", "=", idBaseItemConfiguracao));
	} else {
	    condicao.add(new Condition("idBaseItemConfiguracao", "=", idBaseItemConfiguracao));
	}
	condicao.add(new Condition("idCaracteristica", "=", idCaracteristica));
	List ordenacao = new ArrayList();
	ordenacao.add(new Order("valorStr"));
	List resultado = (List) super.findByCondition(condicao, ordenacao);

	if (resultado != null && !resultado.isEmpty()) {
	    return (ValorDTO) resultado.get(0);
	} else {
	    return null;
	}
    }
    
    /**
     * Retorna Valor da Característica do Item Configuração.
     * 
     * @param idBaseItemConfiguracao
     * @param idCaracteristica
     * @return ValorDTO
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public ValorDTO restoreItemConfiguracao(Integer idItemConfiguracao, Integer idCaracteristica) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemConfiguracao", "=", idItemConfiguracao));
		condicao.add(new Condition("idCaracteristica", "=", idCaracteristica));
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("valorStr"));
		List resultado = (List) super.findByCondition(condicao, ordenacao);
	
		if (resultado != null && !resultado.isEmpty()) {
		    return (ValorDTO) resultado.get(0);
		} else {
		    return null;
		}
    }
    
    public ValorDTO restoreValorByIdItemConfiguracao(Integer idItemConfiguracao, Integer idCaracteristica, String valor) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idItemConfiguracao", "=", idItemConfiguracao));
		condicao.add(new Condition("idCaracteristica", "=", idCaracteristica));
		condicao.add(new Condition("valorStr", "=", valor));
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("valorStr"));
		List resultado = (List) super.findByCondition(condicao, ordenacao);
	
		if (resultado != null && !resultado.isEmpty()) {
		    return (ValorDTO) resultado.get(0);
		} else {
		    return null;
		}
    }

    /**
     * Retorna características e seus respectivos valores.
     * 
     * @param itemConfiguracao
     *            - ItemConfiguracaoDTO.
     * @param tipoItemConfiguracao
     *            - TipoItemConfiguracaoDTO
     * @return listCaracteristica
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public Collection<ValorDTO> findByItemAndTipoItemConfiguracao(ItemConfiguracaoDTO itemConfiguracao, TipoItemConfiguracaoDTO tipoItemConfiguracao) throws PersistenceException {
	StringBuilder sql = new StringBuilder();

	List parametro = new ArrayList();

	this.gerarSqlFindByItemAndTipoItemConfiguracao(itemConfiguracao, tipoItemConfiguracao, sql, parametro);

	List list = this.execSQL(sql.toString(), parametro.toArray());

	Collection<ValorDTO> listCaracteristica = this.engine.listConvertion(this.getBean(), list, this.getListaDeRetornoCaracteristicaValor());

	return listCaracteristica;
    }

    /**
     * 
     * Gerar SQL para a consulta de características e seus respectivos valores.
     * 
     * @param itemConfiguracao
     * @param tipoItemConfiguracao
     * @param sql
     * @param parametro
     * @author valdoilo.damasceno
     */
    private void gerarSqlFindByItemAndTipoItemConfiguracao(ItemConfiguracaoDTO itemConfiguracao, TipoItemConfiguracaoDTO tipoItemConfiguracao, StringBuilder sql, List parametro) {
		String sqlHardware = "";
		parametro.add(itemConfiguracao.getIdItemConfiguracao());
		if (tipoItemConfiguracao.getTag().equalsIgnoreCase("HARDWARE")) {
		    sqlHardware += "AND (tipo.tagtipoitemconfiguracao IN ('HARDWARE', 'DEVICEID', 'MEMORIES' , 'INPUTS', 'PORTS', 'CONTROLLERS', 'SOUNDS', 'STORAGES', 'DRIVES', 'NETWORKS', 'VIDEOS', 'MONITORS', 'PRINTERS',  'QUERY', 'RAM', 'SLOTS', 'MODEMS', 'TAG_TIPO_IC_01', 'VIDEOS') ";
		    sqlHardware += " OR tipo.categoria = " + tipoItemConfiguracao.getCategoria() + ")";
		} else {
		    sqlHardware += "AND (tipo.tagtipoitemconfiguracao LIKE ?";
		    parametro.add("%" + tipoItemConfiguracao.getTag());
		    sqlHardware += " OR tipo.categoria = " + tipoItemConfiguracao.getCategoria() + ")";
		}
		sql.append("SELECT item.iditemconfiguracao, item.datainicio, caracteristica.idcaracteristica, caracteristica.nomecaracteristica, valor.idvalor, valor.valorstr, caracteristica.tagcaracteristica, tipo.tagtipoitemconfiguracao ");
		sql.append("FROM itemconfiguracao item ");
		sql.append("INNER JOIN tipoitemconfiguracao tipo ON item.idtipoitemconfiguracao = tipo.idtipoitemconfiguracao and (item.datafim is null) ");
		sql.append("INNER JOIN tipoitemcfgcaracteristica tipocaracteristica ON tipo.idtipoitemconfiguracao = tipocaracteristica.idtipoitemconfiguracao ");
		sql.append("INNER JOIN caracteristica caracteristica ON tipocaracteristica.idcaracteristica = caracteristica.idcaracteristica ");
		sql.append("INNER JOIN valor valor ON caracteristica.idcaracteristica = valor.idcaracteristica AND valor.iditemconfiguracao = item.iditemconfiguracao ");
		sql.append("WHERE item.iditemconfiguracaopai = ? " + sqlHardware + " ORDER BY tipo.tagtipoitemconfiguracao, item.iditemconfiguracao");
    }
    
    /**
     * 
     * Gerar SQL para a consulta de características e seus respectivos valores.
     * 
     * @param itemConfiguracao
     * @param tipoItemConfiguracao
     * @param sql
     * @param parametro
     * @author valdoilo.damasceno
     * @throws throws Exception 
     */
    public Collection<ValorDTO> findByItemAndTipoItemConfiguracaoSofware(ItemConfiguracaoDTO itemConfiguracao, TipoItemConfiguracaoDTO tipoItemConfiguracao)  throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();
		String sqlSofware = "";
		parametro.add(itemConfiguracao.getIdItemConfiguracao());
		sqlSofware += "AND (tipo.tagtipoitemconfiguracao LIKE ?";
		parametro.add("%" + tipoItemConfiguracao.getTag());
		sqlSofware += " OR tipo.categoria = " + tipoItemConfiguracao.getCategoria() + ")";
		sql.append("SELECT item.iditemconfiguracao, item.datainicio, caracteristica.idcaracteristica, caracteristica.nomecaracteristica, valor.valorstr, caracteristica.tagcaracteristica, tipo.tagtipoitemconfiguracao ");
		sql.append("FROM itemconfiguracao item ");
		sql.append("INNER JOIN tipoitemconfiguracao tipo ON item.idtipoitemconfiguracao = tipo.idtipoitemconfiguracao and (item.datafim is null) ");
		sql.append("INNER JOIN tipoitemcfgcaracteristica tipocaracteristica ON tipo.idtipoitemconfiguracao = tipocaracteristica.idtipoitemconfiguracao ");
		sql.append("INNER JOIN caracteristica caracteristica ON tipocaracteristica.idcaracteristica = caracteristica.idcaracteristica ");
		sql.append("INNER JOIN valor valor ON caracteristica.idcaracteristica = valor.idcaracteristica AND valor.iditemconfiguracao = item.iditemconfiguracao ");
		sql.append("WHERE item.iditemconfiguracao = ? " + sqlSofware + " AND caracteristica.tagcaracteristica LIKE 'NAME%'  ORDER BY item.iditemconfiguracao");
		List list = this.execSQL(sql.toString(), parametro.toArray());
		Collection<ValorDTO> listCaracteristica = this.engine.listConvertion(this.getBean(), list, this.getListaDeRetornoCaracteristicaValor());
		
		return listCaracteristica;
    }
    

    /**
     * Monta lista de retorno (idItemConfiguracao, nomeCaracteristica, valorStr, valorDate, dataInicio)
     * 
     * @return List
     * @author valdoilo.damasceno
     */
    private List getListaDeRetornoCaracteristicaValor() {
	List listRetorno = new ArrayList();
	listRetorno.add("idItemConfiguracao");
	listRetorno.add("dataInicio");
	listRetorno.add("idCaracteristica");
	listRetorno.add("nomeCaracteristica");
	listRetorno.add("idValor");
	listRetorno.add("valorStr");
	listRetorno.add("tag");
	listRetorno.add("tagtipoitemconfiguracao");
	return listRetorno;
    }

    public String getTableName() {
	return this.getOwner() + "Valor";
    }

    public Collection list() throws PersistenceException {
	return null;
    }

    public Class getBean() {
	return ValorDTO.class;
    }

    public Collection find(IDto arg0) throws PersistenceException {
	return null;
    }
    public Collection listByItemConfiguracaoAndTagCaracteristica(Integer idItemConfiguracao, String tag) throws PersistenceException {
		String sql = "SELECT " + this.getNamesFieldsStr("valor") + " " +
				"FROM valor INNER JOIN caracteristica ON valor.idcaracteristica = caracteristica.idcaracteristica  where iditemconfiguracao = ? AND datafim is null " +
				"AND tagcaracteristica = ?";
		List<?> dados = this.execSQL(sql, new Object[] { idItemConfiguracao, tag });
		List<String> fields = this.getListNamesFieldClass();
		return this.listConvertion(getBean(), dados, fields);    	
    }    
    public Collection listUniqueValuesByTagCaracteristica(String tag) throws PersistenceException {
    	String sql = "";
    	if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase("mysql")){
    		sql = "SELECT DISTINCT  replace(replace(valorstr,'\\\\\\\\','-'),'\\\\', '-')  FROM valor INNER JOIN caracteristica ON valor.idcaracteristica = caracteristica.idcaracteristica  where datafim is null AND tagcaracteristica = ? AND valorstr <> '' ORDER BY 1";
    	}else{
    		sql = "SELECT DISTINCT  replace(replace(valorstr,'\\\\','-'), '\\', '-')FROM valor INNER JOIN caracteristica ON valor.idcaracteristica = caracteristica.idcaracteristica  where datafim is null AND tagcaracteristica =? AND valorstr <> '' ORDER BY 1";
    	}
		List<?> dados = this.execSQL(sql, new Object[] { tag });
		List<String> fields = new ArrayList();
		fields.add("valorStr");
		return this.listConvertion(getBean(), dados, fields);    	
    }        
}
