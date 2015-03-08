package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.centralit.citcorpore.bean.InventarioXMLDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

public class InventarioXMLDAO extends CrudDaoDefaultImpl {
    public InventarioXMLDAO() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }
    
    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();
	listFields.add(new Field("idInventarioxml", "idInventarioxml", true, true, false, false));
	listFields.add(new Field("idNetMap", "idNetMap", false, false, false, false));
	listFields.add(new Field("nome", "nome", false, false, false, false));
	listFields.add(new Field("conteudo", "conteudo", false, false, false, false));
	listFields.add(new Field("datainicial", "datainicial", false, false, false, false));
	listFields.add(new Field("datafinal", "datafinal", false, false, false, false));
	return listFields;
    }

    public String getTableName() {
	return this.getOwner() + "InventarioXML";
    }

    public Collection list() throws PersistenceException {
	return null;
    }

    public Class getBean() {
	return InventarioXMLDTO.class;
    }

    public Collection find(IDto arg0) throws PersistenceException {
	return null;
    }
    
    /**
	 * Verifica se o ultimo inventario foi gerado no tempo parametrizado.
	 * 
	 * @return Falso se o inventario estiver desatualizado.
	 * @throws Exception Excecao ganerica.
	 */
	public boolean inventarioAtualizado (String ip, java.util.Date dataExpiracao) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List<Date> parametro = new ArrayList<Date>();
		
		if(CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)){
			sql.append("SELECT top(1) * FROM inventarioxml "); 
		}else{
			sql.append("SELECT * FROM inventarioxml ");
		}
		
		sql.append("WHERE nome like '%" + ip + "%' ");
		sql.append("AND datainicial >= ? ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE))
			sql.append("AND ROWNUM = 1");
		else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL))
				sql.append("offset 1");
		else if(!CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER))
			sql.append("LIMIT 1");
		
		parametro.add(dataExpiracao);
		
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		
		if (lista != null && !lista.isEmpty()) 
            return true;
        else
            return false;
	}
}
