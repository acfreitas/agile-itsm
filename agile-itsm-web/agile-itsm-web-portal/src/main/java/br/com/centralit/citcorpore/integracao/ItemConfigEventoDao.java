package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemConfigEventoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ItemConfigEventoDao extends CrudDaoDefaultImpl {

    public ItemConfigEventoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public Class getBean() {
	return ItemConfigEventoDTO.class;
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();
	
	listFields.add(new Field("IDITEMCONFIGURACAOEVENTO", "idItemConfiguracaoEvento", true, true, false, false));
	listFields.add(new Field("IDITEMCONFIGURACAO", "idItemConfiguracao", false, false, false, false));
	listFields.add(new Field("IDEVENTO", "idEvento", false, false, false, false));
	listFields.add(new Field("TIPOEXECUCAO", "tipoExecucao", false, false, false, false));
	listFields.add(new Field("GERARQUANDO", "gerarQuando", false, false, false, false));
	listFields.add(new Field("DATA", "data", false, false, false, false));
	listFields.add(new Field("HORA", "hora", false, false, false, false));
	listFields.add(new Field("IDBASEITEMCONFIGURACAO", "idBaseItemConfiguracao", false, false, false, false));
	listFields.add(new Field("LINHACOMANDO", "linhaComando", false, false, false, false));
	listFields.add(new Field("LINHACOMANDOLINUX", "linhaComandoLinux", false, false, false, false));

	return listFields;
    }

    public String getTableName() {
	return "ITEMCONFIGURACAOEVENTO";
    }

    public Collection find(IDto obj) throws PersistenceException {
	return null;
    }

    public Collection list() throws PersistenceException {
	return null;
    }

    public Collection<ItemConfigEventoDTO> listByIdEvento(Integer idEvento) throws PersistenceException {
	String sql = "select ic.idItemConfiguracao, bic.idbaseitemconfiguracao, bic.nomebaseitemconfiguracao, v.valorstr, ice.tipoExecucao, ice.gerarQuando, ice.data, ice.hora, ice.linhaComando, ice.linhaComandoLinux " +
		"from " + getTableName() + " ice " + 
		"left join itemConfiguracao ic on ic.iditemconfiguracao = ice.iditemconfiguracao " + 
		"left join baseitemconfiguracao bic on bic.idbaseitemconfiguracao = ice.idbaseitemconfiguracao " +
		"left join valor v on v.iditemconfiguracao = ic.iditemconfiguracao " +
		"where ice.idEvento = ? and (v.idcaracteristica in (select idcaracteristica from caracteristica where tagcaracteristica = 'NAME') or v.idcaracteristica is null)";
	List dados = this.execSQL(sql, new Object[] { idEvento });
	List fields = new ArrayList();
	fields.add("idItemConfiguracao");
	fields.add("idBaseItemConfiguracao");
	fields.add("nomeBaseItemConfiguracao");
	fields.add("identificacao");
	fields.add("tipoExecucao");
	fields.add("gerarQuando");
	fields.add("data");
	fields.add("hora");
	fields.add("linhaComando");
	fields.add("linhaComandoLinux");
	return this.listConvertion(getBean(), dados, fields);
    }

    public void deleteByIdEvento(Integer idEvento) throws PersistenceException {
	List lstCondicao = new ArrayList();
	lstCondicao.add(new Condition(Condition.AND, "idEvento", "=", idEvento));
	super.deleteByCondition(lstCondicao);
    }

    public Collection<ItemConfigEventoDTO> verificaDataHoraEvento() throws PersistenceException {
	String sql = "SELECT IDITEMCONFIGURACAO, IDBASEITEMCONFIGURACAO, IDEVENTO, TIPOEXECUCAO, LINHACOMANDO, LINHACOMANDOLINUX FROM " + getTableName() + " WHERE DATA = ? AND HORA = ? AND GERARQUANDO <> 'A'";
	List dados = this.execSQL(sql, new Object[] { UtilDatas.getDataAtual(), UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()).replaceAll(":", "") });
	List fields = new ArrayList();
	fields.add("idItemConfiguracao");
	fields.add("idBaseItemConfiguracao");
	fields.add("idEvento");
	fields.add("tipoExecucao");
	fields.add("linhaComando");
	fields.add("linhaComandoLinux");
	return this.listConvertion(getBean(), dados, fields);
    }
    
    
   

}
