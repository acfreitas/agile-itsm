package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class EventosDao extends CrudDaoDefaultImpl {

	public EventosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Class getBean() {
		return EventosDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("IDEVENTO", "idEvento", true, true, false, false));
		listFields.add(new Field("IDUSUARIO", "idUsuario", false, false, false, false));
		listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
		listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
		listFields.add(new Field("GERARQUANDO", "gerarQuando", false, false, false, false));
		listFields.add(new Field("LIGARCASODESL", "ligarCasoDesl", false, false, false, false));
		listFields.add(new Field("DATACRIACAO", "dataCriacao", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		
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

}
