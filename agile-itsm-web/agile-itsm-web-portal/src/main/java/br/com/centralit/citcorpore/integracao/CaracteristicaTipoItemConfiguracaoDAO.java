/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CaracteristicaTipoItemConfiguracaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

/**
 * DAO de CaracteristicaTipoItemConfiguracao - Tabela tipoItemCfgCaracteristica
 * no BD.
 * 
 * @author valdoilo.damasceno
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CaracteristicaTipoItemConfiguracaoDAO extends CrudDaoDefaultImpl {

    /**
     * Construtor.
     * 
     * @author valdoilo.damasceno
     */
    public CaracteristicaTipoItemConfiguracaoDAO() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(IDto arg0) throws PersistenceException {
	return null;
    }

    @Override
    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("ID", "id", true, true, false, false));
	listFields.add(new Field("IDTIPOITEMCONFIGURACAO", "idTipoItemConfiguracao", false, false, false, false));
	listFields.add(new Field("IDCARACTERISTICA", "idCaracteristica", false, false, false, false));
	listFields.add(new Field("NAMEINFOAGENTE", "nameInfoAgente", false, false, false, false));
	listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
	listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

	return listFields;
    }

    @Override
    public String getTableName() {
	return "TIPOITEMCFGCARACTERISTICA";
    }

    @Override
    public Collection list() throws PersistenceException {
	return null;
    }

    @Override
    public Class getBean() {
	return CaracteristicaTipoItemConfiguracaoDTO.class;
    }

    /**
     * Verifica se Característica está associada a algum Tipo Item Configuração.
     * 
     * @param idCaracteristica
     * @return boolean
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public boolean existeAssociacaoComCaracteristica(Integer idCaracteristica, Integer idTipoItemConfiguracao) throws PersistenceException {
    	boolean exists;		
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("select * from " + getTableName() + " where idCaracteristica = ? ");
		parametro.add(idCaracteristica);
		if (idTipoItemConfiguracao != null && !(idTipoItemConfiguracao.intValue() == 0)) {
			sql.append(" and idTipoItemConfiguracao = ? AND dataFim IS NULL ");
			parametro.add(idTipoItemConfiguracao);
		}		
		sql.append(" AND dataFim IS NULL ");
		
		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) 
			exists = true;
		else 
			exists = false;
		return exists;
    }
 

    /**
     * Exclui associação de Característica com Tipo Item Configuração.
     * 
     * @param idTipoItemConfiguracao
     * @param idCaracteristica
     * @throws PersistenceException
     * @author valdoilo.damasceno
     */
    public void excluirAssociacaoCaracteristicaTipoItemConfiguracao(Integer idTipoItemConfiguracao, Integer idCaracteristica)
	    throws PersistenceException {
	Object[] parametros = new Object[] { UtilDatas.getDataAtual(), idTipoItemConfiguracao, idCaracteristica };

	String sql = "UPDATE " + getTableName() + " SET datafim = ? WHERE idtipoitemconfiguracao = ? AND idcaracteristica = ?";
	execUpdate(sql, parametros);
    }
}
