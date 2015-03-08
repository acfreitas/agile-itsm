package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.BIConsultaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class BIConsultaDao extends CrudDaoDefaultImpl {
	public BIConsultaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idConsulta" ,"idConsulta", true, true, false, false));
		listFields.add(new Field("identificacao" ,"identificacao", false, false, false, false));
		listFields.add(new Field("nomeConsulta" ,"nomeConsulta", false, false, false, false));
		listFields.add(new Field("tipoConsulta" ,"tipoConsulta", false, false, false, false));
		listFields.add(new Field("textoSQL" ,"textoSQL", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("acaoCruzado" ,"acaoCruzado", false, false, false, false));
		listFields.add(new Field("template" ,"template", false, false, false, false));
		listFields.add(new Field("scriptExec" ,"scriptExec", false, false, false, false));
		listFields.add(new Field("parametros" ,"parametros", false, false, false, false));
		listFields.add(new Field("idCategoria" ,"idCategoria", false, false, false, false));
		listFields.add(new Field("naoAtualizBase" ,"naoAtualizBase", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "BI_Consulta";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return BIConsultaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
	public Collection findByIdCategoria(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idCategoria", "=", parm)); 
		ordenacao.add(new Order("nomeConsulta"));
		return super.findByCondition(condicao, ordenacao);
	}
	public BIConsultaDTO getByIdentificacao(String ident) throws PersistenceException {
		String sql = "select " + this.getNamesFieldsStr() + " from " + this.getTableName() + " where identificacao = ?";
		List lstParms = new ArrayList();
		lstParms.add(ident);
		List lstDados = this.execSQL(sql, lstParms.toArray());
		List lst = this.listConvertion(getBean(), lstDados, this.getListNamesFieldClass());
		if (lst == null || lst.size() == 0){
			return null;
		}
		return (BIConsultaDTO)lst.get(0);
	}
}
