package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.LinhaBaseProjetoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class LinhaBaseProjetoDao extends CrudDaoDefaultImpl {
	public LinhaBaseProjetoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idLinhaBaseProjeto" ,"idLinhaBaseProjeto", true, true, false, false));
		listFields.add(new Field("idProjeto" ,"idProjeto", false, false, false, false));
		listFields.add(new Field("dataLinhaBase" ,"dataLinhaBase", false, false, false, false));
		listFields.add(new Field("horaLinhaBase" ,"horaLinhaBase", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("dataUltAlteracao" ,"dataUltAlteracao", false, false, false, false));
		listFields.add(new Field("horaUltAlteracao" ,"horaUltAlteracao", false, false, false, false));
		listFields.add(new Field("usuarioUltAlteracao" ,"usuarioUltAlteracao", false, false, false, false));
		listFields.add(new Field("justificativaMudanca" ,"justificativaMudanca", false, false, false, false));
		listFields.add(new Field("dataSolMudanca" ,"dataSolMudanca", false, false, false, false));
		listFields.add(new Field("horaSolMudanca" ,"horaSolMudanca", false, false, false, false));
		listFields.add(new Field("usuarioSolMudanca" ,"usuarioSolMudanca", false, false, false, false));		
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "LinhaBaseProjeto";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return LinhaBaseProjetoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdProjeto(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProjeto", "=", parm)); 
		ordenacao.add(new Order("idLinhaBaseProjeto", Order.DESC));
		ordenacao.add(new Order("dataLinhaBase", Order.DESC));
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
	
	public void inativaLinhasBaseAnteriorByIdProjeto(Integer idProjetoParm) throws PersistenceException {
		String sql = "UPDATE " + this.getTableName() + " SET situacao = '" + LinhaBaseProjetoDTO.INATIVO + "' where idProjeto = ?";
		super.execUpdate(sql, new Object[] {idProjetoParm});
	}
	
}
