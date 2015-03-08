package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class TemplateSolicitacaoServicoDao extends CrudDaoDefaultImpl {
	public TemplateSolicitacaoServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idTemplate" ,"idTemplate", true, true, false, false));
		listFields.add(new Field("nomeTemplate" ,"nomeTemplate", false, false, false, false));
        listFields.add(new Field("identificacao" ,"identificacao", false, false, false, false));
        listFields.add(new Field("nomeClasseAction" ,"nomeClasseAction", false, false, false, false));
		listFields.add(new Field("nomeClasseServico" ,"nomeClasseServico", false, false, false, false));
        listFields.add(new Field("nomeClasseDto" ,"nomeClasseDto", false, false, false, false));
		listFields.add(new Field("urlRecuperacao" ,"urlRecuperacao", false, false, false, false));
        listFields.add(new Field("habilitaDirecionamento" ,"habilitaDirecionamento", false, false, false, false));
        listFields.add(new Field("habilitaSituacao" ,"habilitaSituacao", false, false, false, false));
        listFields.add(new Field("habilitaSolucao" ,"habilitaSolucao", false, false, false, false));
        listFields.add(new Field("habilitaUrgenciaImpacto" ,"habilitaUrgenciaImpacto", false, false, false, false));
        listFields.add(new Field("habilitaNotificacaoEmail" ,"habilitaNotificacaoEmail", false, false, false, false));
        listFields.add(new Field("habilitaProblema" ,"habilitaProblema", false, false, false, false));
        listFields.add(new Field("habilitaMudanca" ,"habilitaMudanca", false, false, false, false));
        listFields.add(new Field("habilitaItemConfiguracao" ,"habilitaItemConfiguracao", false, false, false, false));
        listFields.add(new Field("habilitaSolicitacaoRelacionada" ,"habilitaSolicitacaoRelacionada", false, false, false, false));
        listFields.add(new Field("habilitaGravarEContinuar" ,"habilitaGravarEContinuar", false, false, false, false));
        listFields.add(new Field("scriptAposRecuperacao" ,"scriptAposRecuperacao", false, false, false, false));
        listFields.add(new Field("alturaDiv" ,"alturaDiv", false, false, false, false));
        listFields.add(new Field("idQuestionario" ,"idQuestionario", false, false, false, false));
        listFields.add(new Field("aprovacao" ,"aprovacao", false, false, false, false));        
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "TemplateSolicitacaoServico";
	}
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		list.add(new Order("nomeTemplate"));
		return super.list(list);
	}

	public Class getBean() {
		return TemplateSolicitacaoServicoDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
    public TemplateSolicitacaoServicoDTO findByIdentificacao(String identificacao) throws PersistenceException {
        List condicao = new ArrayList();
        condicao.add(new Condition("identificacao", "=", identificacao));
        
        List result = (List) super.findByCondition(condicao, null);
        if (result != null && !result.isEmpty())
            return (TemplateSolicitacaoServicoDTO) result.get(0);
        else
            return null;
    }       
    public List<TemplateSolicitacaoServicoDTO> listComAprovacao() throws PersistenceException {
        List condicao = new ArrayList();
        condicao.add(new Condition("aprovacao", "=", "S"));
        return (List<TemplateSolicitacaoServicoDTO>) super.findByCondition(condicao, null);
    }  	
}
