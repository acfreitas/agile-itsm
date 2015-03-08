package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.SituacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class SituacaoServicoDao extends CrudDaoDefaultImpl {

	public SituacaoServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return SituacaoServicoDTO.class;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDSITUACAOSERVICO", "idSituacaoServico", true, true, false, false));
		listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
		listFields.add(new Field("NOMESITUACAOSERVICO", "nomeSituacaoServico", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

		return listFields;
	}

	public String getTableName() {
		return "SITUACAOSERVICO";
	}

	public Collection find(IDto obj) throws PersistenceException {
		List ordem = new ArrayList();
		ordem.add(new Order("nomeSituacaoServico"));
		return super.find(obj, ordem);
	}

	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeSituacaoServico"));
		return super.list(list);
	}
	
	public boolean existeServicoAssociado(Integer idSituacaoServico) throws PersistenceException {
		
		List parametro = new ArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT IDSITUACAOSERVICO FROM SERVICO WHERE IDSITUACAOSERVICO = ? and (deleted is null or deleted = 'n') ");
		parametro.add(idSituacaoServico);
		List lista = this.execSQL(sql.toString(), parametro.toArray());
		
		if(lista.size()>0) return true;
		
		return false;
	}

}
