/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EventoMonitoramentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author Vadoilo Damasceno
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class EventoMonitoramentoDAO extends CrudDaoDefaultImpl {

	public EventoMonitoramentoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDEVENTOMONITORAMENTO", "idEventoMonitoramento", true, true, false, false));
		listFields.add(new Field("NOMEEVENTO", "nomeEvento", false, false, false, false));
		listFields.add(new Field("DETALHAMENTO", "detalhamento", false, false, false, false));
		listFields.add(new Field("CRIADOPOR", "criadoPor", false, false, false, false));
		listFields.add(new Field("MODIFICADOPOR", "modificadoPor", false, false, false, false));
		listFields.add(new Field("DATACRIACAO", "dataCriacao", false, false, false, false));
		listFields.add(new Field("ULTMODIFICACAO", "ultimaModificacao", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "EVENTOMONITORAMENTO";
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idEventoMonitoramento"));
		return super.list(ordenacao);
	}

	@Override
	public Class getBean() {
		return EventoMonitoramentoDTO.class;
	}

}
