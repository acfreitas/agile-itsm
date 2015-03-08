package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class NotificacaoUsuarioDao extends CrudDaoDefaultImpl {

	public NotificacaoUsuarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
				return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idnotificacao", "idNotificacao", true, false, false, false));
		listFields.add(new Field("idusuario", "idUsuario", true, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
				return "NOTIFICACAOUSUARIO";
	}

	@Override
	public Collection list() throws PersistenceException {
				return null;
	}

	@Override
	public Class getBean() {
				return NotificacaoUsuarioDTO.class;
	}
	
	public Collection<NotificacaoUsuarioDTO> listaIdUsuario(Integer idNotificacao) throws PersistenceException {
		Object[] objs = new Object[] { idNotificacao };
		String sql = "SELECT  idusuario FROM " + getTableName() + " WHERE idnotificacao = ?  ";
		List lista = this.execSQL(sql, objs);

		List listRetorno = new ArrayList();
		listRetorno.add("idUsuario");
		if (lista != null && !lista.isEmpty()) {
			return this.engine.listConvertion(getBean(), lista, listRetorno);
		} else {
			return null;
		}
	}
	
	public void deleteByIdNotificacaoUsuario(Integer idNotificacao) throws PersistenceException {
		List lstCondicao = new ArrayList();
		lstCondicao.add(new Condition("idNotificacao", "=", idNotificacao));
		super.deleteByCondition(lstCondicao);
	}

}
