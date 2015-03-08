package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MidiaSoftwareChaveDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;


public class MidiaSoftwareChaveDao extends CrudDaoDefaultImpl {

	/**
     * @author flavio.santana
     */
	
	public MidiaSoftwareChaveDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idMidiaSoftwareChave", "idMidiaSoftwareChave", true, true, false, false));
		listFields.add(new Field("idMidiaSoftware", "idMidiaSoftware", false, false, false, false));
		listFields.add(new Field("chave", "chave", false, false, false, false));
		listFields.add(new Field("qtdPermissoes", "qtdPermissoes", false, false, false, false));
		
		return listFields;
	}
	
	public String getTableName() {
		return "MIDIASOFTWARECHAVE";
	}
	
	@SuppressWarnings("unchecked")
	public Collection<MidiaSoftwareChaveDTO> find(IDto obj) throws PersistenceException {
		List<MidiaSoftwareChaveDTO> ordem = new ArrayList<MidiaSoftwareChaveDTO>();
		return super.find(obj, ordem);
	}
	
	@SuppressWarnings("unchecked")
	public Collection<MidiaSoftwareChaveDTO> list() throws PersistenceException {
		List<MidiaSoftwareChaveDTO> list = new ArrayList<MidiaSoftwareChaveDTO>();

		return super.list(list);
	}
	
	@SuppressWarnings("rawtypes")
	public Class getBean() {
		return MidiaSoftwareChaveDTO.class;
	}
	
	public void deleteByIdMidiaSoftware(Integer idMidiaSoftware) throws PersistenceException {
		String sql = "DELETE FROM " + getTableName() +" WHERE idMidiaSoftware = ? ";
        super.execUpdate(sql, new Object[]{idMidiaSoftware});
    }
	
	@SuppressWarnings("unchecked")
	public Collection<MidiaSoftwareChaveDTO> findByMidiaSoftware (Integer idMidiaSoftware) throws PersistenceException {
		String sql = "SELECT idMidiaSoftwareChave, chave, qtdPermissoes  FROM " + getTableName() + "  WHERE idMidiaSoftware = ?";
		List<MidiaSoftwareChaveDTO> dados = this.execSQL(sql, new Object[] { idMidiaSoftware });
		List<String> fields = new ArrayList<String>();
		fields.add("idMidiaSoftwareChave");
		fields.add("chave");
		fields.add("qtdPermissoes");
		return this.listConvertion(getBean(), dados, fields);
	}
}
