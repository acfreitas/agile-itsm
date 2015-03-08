package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PagamentoProjetoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class PagamentoProjetoDao extends CrudDaoDefaultImpl {
	public PagamentoProjetoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idPagamentoProjeto" ,"idPagamentoProjeto", true, true, false, false));
		listFields.add(new Field("idProjeto" ,"idProjeto", false, false, false, false));
		listFields.add(new Field("dataPagamento" ,"dataPagamento", false, false, false, false));
		listFields.add(new Field("valorPagamento" ,"valorPagamento", false, false, false, false));
		listFields.add(new Field("valorGlosa" ,"valorGlosa", false, false, false, false));
		listFields.add(new Field("detPagamento" ,"detPagamento", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("dataUltAlteracao" ,"dataUltAlteracao", false, false, false, false));
		listFields.add(new Field("horaUltAlteracao" ,"horaUltAlteracao", false, false, false, false));
		listFields.add(new Field("usuarioUltAlteracao" ,"usuarioUltAlteracao", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "PagamentoProjeto";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return PagamentoProjetoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdProjeto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProjeto", "=", parm)); 
		ordenacao.add(new Order("dataPagamento"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdProjeto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProjeto", "=", parm));
		super.deleteByCondition(condicao);
	}
	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}
	public Collection getTotaisByIdProjeto(Integer idProjetoParm) throws PersistenceException {
		String sql = "SELECT SUM(valorPagamento), SUM(valorGlosa) FROM PagamentoProjeto " +
				"WHERE PagamentoProjeto.idProjeto = ? ";
		List lstDados = this.execSQL(sql, new Object[]{idProjetoParm});
		List fields = new ArrayList();
		fields.add("valorPagamento");
		fields.add("valorGlosa");
		return this.listConvertion(getBean(), lstDados, fields);
	}	
}
