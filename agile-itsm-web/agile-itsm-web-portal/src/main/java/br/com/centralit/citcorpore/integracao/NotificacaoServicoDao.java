package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NotificacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NotificacaoServicoDao extends CrudDaoDefaultImpl {

	public NotificacaoServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
				return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idNotificacao", "idNotificacao", true, false, false, false));
		listFields.add(new Field("idServico", "idServico", true, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "NOTIFICACAOSERVICO";
	}

	@Override
	public Collection list() throws PersistenceException {
				return null;
	}

	@Override
	public Class getBean() {
				return NotificacaoServicoDTO.class;
	}
	
	public Collection<NotificacaoServicoDTO> listaIdServico(Integer idservico) throws PersistenceException {
		Object[] objs = new Object[] { idservico };
		String sql = "SELECT  idservico, idNotificacao FROM " + getTableName() + " WHERE idservico = ?  ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idServico");
		listRetorno.add("idNotificacao");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}
	
	public Collection<NotificacaoServicoDTO> listaIdNotificacao(Integer idNotificacao) throws PersistenceException {
		Object[] objs = new Object[] { idNotificacao };
		String sql = "SELECT  idservico, idNotificacao FROM " + getTableName() + " WHERE idNotificacao = ?  ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idServico");
		listRetorno.add("idNotificacao");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}
	
	public boolean existeServico(Integer idNotificacao, Integer idservico) throws PersistenceException {
		Object[] objs = new Object[] { idNotificacao, idservico };
		String sql = "SELECT  idservico, idNotificacao FROM " + getTableName() + " WHERE idNotificacao = ? AND idservico = ?  ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idServico");
		listRetorno.add("idNotificacao");
		if (lista != null && !lista.isEmpty()) {
			return true;
		} 
		return false;
	}

	public void deleteByIdNotificacaoServico(Integer idNotificacao) throws PersistenceException {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition("idNotificacao", "=", idNotificacao));
		super.deleteByCondition(lstCondicao);
	}
	
}
