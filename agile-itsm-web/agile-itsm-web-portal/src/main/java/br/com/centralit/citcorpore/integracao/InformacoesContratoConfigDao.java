package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.InformacoesContratoConfigDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class InformacoesContratoConfigDao extends CrudDaoDefaultImpl {
	private static final String SQL_GET_LISTA_SEM_PAI = "SELECT nome, idInformacoesContratoConfig, idInformacoesContratoConfigPai, idEmpresa, descricao, funcionalidadePath, funcItem, " +
	"idQuestionario, situacao, validacoes " + 
	"FROM InformacoesContratoConfig icCfg " +
	"WHERE icCfg.idEmpresa = ? AND idInformacoesContratoConfigPai IS NULL AND situacao = 'A' " +
	"ORDER BY ordem";
	
	public InformacoesContratoConfigDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		
		listFields.add(new Field("idInformacoesContratoConfig", "idInformacoesContratoConfig", true, true, false, false));
		listFields.add(new Field("idInformacoesContratoConfigPai", "idInformacoesContratoConfigPai", false, false, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("funcionalidadePath", "funcionalidadePath", false, false, false, false));
		listFields.add(new Field("funcItem", "funcItem", false, false, false, false));
		listFields.add(new Field("idQuestionario", "idQuestionario", false, false, false, false));
		listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("ordem", "ordem", false, false, false, false));
		listFields.add(new Field("infoAdicional", "infoAdicional", false, false, false, false));
		listFields.add(new Field("funcAdicionalAposGravacao", "funcAdicionalAposGravacao", false, false, false, false));
		listFields.add(new Field("chamarFuncAddAposGravar", "chamarFuncAddAposGravar", false, false, false, false));
		listFields.add(new Field("chamarFuncAddHistorico", "chamarFuncAddHistorico", false, false, false, false));
		listFields.add(new Field("iconeFuncHistorico", "iconeFuncHistorico", false, false, false, false));
		listFields.add(new Field("iconeFuncHistoricoFinal", "iconeFuncHistoricoFinal", false, false, false, false));
		listFields.add(new Field("validacoes", "validacoes", false, false, false, false));
		listFields.add(new Field("segurancaUnidade", "segurancaUnidade", false, false, false, false));
		listFields.add(new Field("segurancaUnidadePCMSO", "segurancaUnidadePCMSO", false, false, false, false));
		listFields.add(new Field("segurancaUnidadeENFERM", "segurancaUnidadeENFERM", false, false, false, false));
		
		return listFields;		
	}

	public String getTableName() {
		return "InformacoescontratoConfig";
	}

	public Collection list() throws PersistenceException {
	    List ordenacao = new ArrayList(); 
	    ordenacao.add(new Order("nome", "asc"));
		return super.list(ordenacao);
	}

	public Class getBean() {
		return InformacoesContratoConfigDTO.class;
	}
	
	public Collection getAtivos() throws PersistenceException {
		List lstOrder = new ArrayList();
		List lstCondicao = new ArrayList();
		
		lstCondicao.add(new Condition("descricao", "", ""));
		lstOrder.add(new Order("nome"));
		
		return super.findByCondition(lstCondicao, lstOrder);
	}
	
	public Collection findSemPai(Integer idEmpresa) throws PersistenceException {
		Object[] objs = new Object[] {idEmpresa};
		List lista = this.execSQL(SQL_GET_LISTA_SEM_PAI, objs);
		
		List listRetorno = new ArrayList();
		listRetorno.add("nome");
		listRetorno.add("idInformacoesContratoConfig");
		listRetorno.add("idInformacoesContratoConfigPai");
		listRetorno.add("idEmpresa");
		listRetorno.add("descricao");
		listRetorno.add("funcionalidadePath");
		listRetorno.add("funcItem");
		listRetorno.add("idQuestionario");
		listRetorno.add("situacao");
		listRetorno.add("validacoes");
		
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;		
	}	
	
	public Collection findByPai(Integer idPai) throws PersistenceException {
		InformacoesContratoConfigDTO peCfg = new InformacoesContratoConfigDTO();
		peCfg.setIdInformacoesContratoConfigPai(idPai);
		peCfg.setSituacao("A");
		List list = new ArrayList();
		list.add(new Order("ordem"));		
		return super.find(peCfg, list);
	}	
	public Collection findByNome(String nome) throws PersistenceException {
		List lstCondicao = new ArrayList();
		InformacoesContratoConfigDTO icCfg = new InformacoesContratoConfigDTO();
		icCfg.setNome(nome);
		lstCondicao.add(new Condition("nome", "=", icCfg.getNome()));
		List list = new ArrayList();
		list.add(new Order("nome"));		
		list.add(new Order("situacao"));  //Isto faz com que os ativos venham na frente!		
		return super.findByCondition(lstCondicao, list);		
	}
}
