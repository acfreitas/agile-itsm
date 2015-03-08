/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NotificacaoUsuarioMonitDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * @author valdoilo.damasceno
 * @since 13.06.2014
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class NotificacaoUsuarioMonitDAO extends CrudDaoDefaultImpl {

	public NotificacaoUsuarioMonitDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDNOTIFICACAOUSUARIOMONIT", "idNotificacaoUsuarioMonit", true, true, false, false));
		listFields.add(new Field("IDMONITORAMENTOATIVOS", "idMonitoramentoAtivos", false, false, false, false));
		listFields.add(new Field("IDUSUARIO", "idUsuario", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "notificacaousuariomonit";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return NotificacaoUsuarioMonitDTO.class;
	}
	
	public Collection<NotificacaoUsuarioMonitDTO> restoreByIdMonitoramentoAtivos(Integer idMonitoramentoAtivos) throws PersistenceException {
		List codicao = new ArrayList();

		codicao.add(new Condition("idMonitoramentoAtivos", "=", idMonitoramentoAtivos));
		
		return this.findByCondition(codicao, null);
	}

	public boolean deleteByIdMonitoramentoAtivos(Integer idMonitoramentoAtivos) throws PersistenceException {
		List codicao = new ArrayList();

		codicao.add(new Condition("idMonitoramentoAtivos", "=", idMonitoramentoAtivos));

		return (this.deleteByCondition(codicao) != 0);

	}
}
