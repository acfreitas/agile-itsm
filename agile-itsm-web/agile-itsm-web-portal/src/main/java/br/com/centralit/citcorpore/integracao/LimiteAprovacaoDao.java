package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.LimiteAprovacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class LimiteAprovacaoDao extends CrudDaoDefaultImpl {
	public LimiteAprovacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idLimiteAprovacao" ,"idLimiteAprovacao", true, true, false, false));
		listFields.add(new Field("tipoLimitePorValor" ,"tipoLimitePorValor", false, false, false, false));
		listFields.add(new Field("abrangenciaCentroResultado" ,"abrangenciaCentroResultado", false, false, false, false));
		listFields.add(new Field("identificacao" ,"identificacao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "LimiteAprovacao";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return LimiteAprovacaoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
}
