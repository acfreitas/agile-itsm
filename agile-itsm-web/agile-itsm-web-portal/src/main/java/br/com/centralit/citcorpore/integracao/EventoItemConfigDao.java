package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.EventoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.EventoItemConfigDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings("unchecked")
public class EventoItemConfigDao extends CrudDaoDefaultImpl {

    public EventoItemConfigDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    public Class getBean() {
	return EventoItemConfigDTO.class;
    }

    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();

	listFields.add(new Field("IDEVENTO", "idEvento", true, true, false, false));
	listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
	listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
	listFields.add(new Field("LIGARCASODESL", "ligarCasoDesl", false, false, false, false));
	listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
	listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
	listFields.add(new Field("USUARIO", "usuario", false, false, false, false));
	listFields.add(new Field("SENHA", "senha", false, false, false, false));

	return listFields;
    }

    public String getTableName() {
	return "EVENTO";
    }

    public Collection find(IDto obj) throws PersistenceException {
	return null;
    }

    public Collection list() throws PersistenceException {
	return null;
    }

    public ValorDTO pegarSistemaOperacionalEmpregado(Integer idEmpregado) throws PersistenceException {
	String sql = "select v.valorstr from valor v join caracteristica c on c.idcaracteristica = v.idcaracteristica "
		+ "join itemconfiguracao ic on ic.iditemconfiguracao = v.iditemconfiguracao "
		+ "where c.tagcaracteristica = 'OSNAME' and ic.iditemconfiguracaopai in (select c.iditemconfiguracaopai from usuario a "
		+ "join valor b on b.valorstr = a.login join itemconfiguracao c on c.iditemconfiguracao = b.iditemconfiguracao where a.idempregado = ?)";
	List dado = this.execSQL(sql, new Object[] { idEmpregado });
	List field = new ArrayList();
	field.add("valorStr");
	List result = listConvertion(ValorDTO.class, dado, field);
	if (result != null && result.size() > 0) {
	    return (ValorDTO) result.get(0);
	}
	return null;
    }

    public ValorDTO pegarCaminhoItemConfig(String nomeBaseItemConfig) throws PersistenceException {
	String sql = "SELECT V.VALORSTR FROM ITEMCONFIGURACAO IC JOIN TIPOITEMCONFIGURACAO TIC ON TIC.IDTIPOITEMCONFIGURACAO = IC.IDTIPOITEMCONFIGURACAO "
		+ "JOIN TIPOITEMCFGCARACTERISTICA TICC ON TICC.IDTIPOITEMCONFIGURACAO = TIC.IDTIPOITEMCONFIGURACAO "
		+ "JOIN CARACTERISTICA CA ON CA.IDCARACTERISTICA = TICC.IDCARACTERISTICA "
		+ "JOIN VALOR V ON V.IDITEMCONFIGURACAO = IC.IDITEMCONFIGURACAO AND V.IDCARACTERISTICA = TICC.IDCARACTERISTICA "
		+ "WHERE IC.DATAFIM IS NULL AND TIC.TAGTIPOITEMCONFIGURACAO = 'SOFTWARES' AND CA.TAGCARACTERISTICA = 'FOLDER' "
		+ "AND IC.IDITEMCONFIGURACAO IN (SELECT A.IDITEMCONFIGURACAO FROM ITEMCONFIGURACAO A "
		+ "JOIN TIPOITEMCONFIGURACAO B ON B.IDTIPOITEMCONFIGURACAO = A.IDTIPOITEMCONFIGURACAO "
		+ "JOIN TIPOITEMCFGCARACTERISTICA C ON C.IDTIPOITEMCONFIGURACAO = B.IDTIPOITEMCONFIGURACAO JOIN CARACTERISTICA D ON D.IDCARACTERISTICA = C.IDCARACTERISTICA "
		+ "JOIN VALOR V ON V.IDITEMCONFIGURACAO = A.IDITEMCONFIGURACAO AND V.IDCARACTERISTICA = C.IDCARACTERISTICA "
		+ "WHERE A.DATAFIM IS NULL AND B.TAGTIPOITEMCONFIGURACAO = 'SOFTWARES' AND D.TAGCARACTERISTICA = 'NAME' AND V.VALORSTR = ?)";
	List dado = this.execSQL(sql, new Object[] { nomeBaseItemConfig });
	List field = new ArrayList();
	field.add("valorStr");
	List result = listConvertion(ValorDTO.class, dado, field);
	if (result != null && result.size() > 0) {
	    return (ValorDTO) result.get(0);
	}
	return null;
    }

    /*public Collection<CaracteristicaDTO> pegarNetworksEmpregado(Integer idEmpregado) throws PersistenceException {
	String sql = "select b.valorstr, a.tagcaracteristica from caracteristica a join valor b on a.idcaracteristica = b.idcaracteristica "
		+ "join itemconfiguracao c on b.iditemconfiguracao = c.iditemconfiguracao where c.iditemconfiguracao in (select d.iditemconfiguracao "
		+ "from (select b.valorstr from caracteristica a join valor b on a.idcaracteristica = b.idcaracteristica "
		+ "join itemconfiguracao c on c.iditemconfiguracao = b.iditemconfiguracao where a.tagcaracteristica = 'IPADDR') a join "
		+ "(select b.valorstr, b.iditemconfiguracao from caracteristica a join valor b on a.idcaracteristica = b.idcaracteristica "
		+ "join itemconfiguracao c on c.iditemconfiguracao = b.iditemconfiguracao where a.tagcaracteristica = 'IPADDRESS') b on a.valorstr = b.valorstr "
		+ "join itemconfiguracao d on d.iditemconfiguracao = b.iditemconfiguracao where d.iditemconfiguracaopai in (select c.iditemconfiguracaopai from usuario a "
		+ "join valor b on b.valorstr = a.login " + "join itemconfiguracao c on c.iditemconfiguracao = b.iditemconfiguracao "
		+ "where a.idempregado = ?)) and c.datafim is null order by c.datainicio desc, c.iditemconfiguracaopai desc";
	List dados = this.execSQL(sql, new Object[] { idEmpregado });
	List fields = new ArrayList();
	fields.add("valorString");
	fields.add("tag");
	return this.listConvertion(CaracteristicaDTO.class, dados, fields);
    }*/

    public Integer getIdItemConfiguracaoPai(Integer idEmpregado) throws PersistenceException {
	String sql = "select c.iditemconfiguracaopai from usuario a " + "join valor b on b.valorstr = a.login join itemconfiguracao c on c.iditemconfiguracao = b.iditemconfiguracao "
		+ "where a.idempregado = ? and c.datafim is null order by c.datainicio desc, c.iditemconfiguracaopai desc";

	List dados = this.execSQL(sql, new Object[] { idEmpregado });
	List fields = new ArrayList();
	fields.add("idItemConfiguracaoPai");
	List result = this.listConvertion(EventoEmpregadoDTO.class, dados, fields);

	if (result != null && result.size() > 0) {
	    return ((EventoEmpregadoDTO) result.get(0)).getIdItemConfiguracaoPai();
	}
	return null;
    }

    public Collection<CaracteristicaDTO> pegarNetworksItemConfiguracao(Integer idItemConfiguracao) throws PersistenceException {
		String sql = "SELECT v.valorstr, c.tagcaracteristica FROM valor AS v " +
				"INNER JOIN itemconfiguracao AS i ON i.iditemconfiguracao = v.iditemconfiguracao " +
				"INNER JOIN caracteristica AS c ON c.idcaracteristica = v.idcaracteristica AND v.iditemconfiguracao = i.iditemconfiguracao " +
				"INNER JOIN tipoitemconfiguracao AS t ON t.idtipoitemconfiguracao = i.idtipoitemconfiguracao " +
				"AND (c.tagcaracteristica = 'MACADDR' " +
				"OR c.tagcaracteristica = 'IPADDRESS' " +
				"OR c.tagcaracteristica = 'IPMASK' ) " +
				"AND t.tagtipoitemconfiguracao = 'NETWORKS' " +
				"WHERE i.iditemconfiguracaopai = ? " +
				"AND i.datafim IS NULL " +
				"ORDER BY c.tagcaracteristica;";
		List<?> dados = this.execSQL(sql, new Object[] { idItemConfiguracao });
		List<String> fields = new ArrayList<String>();
		fields.add("valorString");
		fields.add("tag");
		return this.listConvertion(CaracteristicaDTO.class, dados, fields);
    }
    
    /**
     * Método que retorna o nome do Sistema operacional instalado no item de configuração
     * 
     * @param idItemConfiguracao
     * @return String nome do SO
     * @throws Exception
     */
    public String pegarSistemaOperacionalItemConfiguracao(Integer idItemConfiguracao) throws PersistenceException {
    	String sql = "SELECT	v.valorstr " +
    			"FROM	valor v " +
    			"INNER JOIN	caracteristica AS c " +
    			"ON c.idcaracteristica = v.idcaracteristica " +
    			"INNER JOIN	itemconfiguracao AS ic " +
    			"ON ic.iditemconfiguracao = v.iditemconfiguracao " +
    			"INNER JOIN	tipoitemconfiguracao AS tp " +
    			"ON tp.idtipoitemconfiguracao = ic.idtipoitemconfiguracao " +
    			"WHERE	c.tagcaracteristica = 'OSNAME' " +
    			"AND tp.tagtipoitemconfiguracao = 'HARDWARE' " +
    			"AND	ic.iditemconfiguracaopai = ?";
		List<?> dado = this.execSQL(sql, new Object[] { idItemConfiguracao });
		List<String> field = new ArrayList<String>();
		field.add("valorStr");
		List<?> result = listConvertion(ValorDTO.class, dado, field);
		if (result != null && result.size() > 0) {
		    return ((ValorDTO) result.get(0)).getValorStr();
		}
		return null;
    }

}
