package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AvaliacaoFornecedorDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AvaliacaoFornecedorDao extends CrudDaoDefaultImpl {
	public AvaliacaoFornecedorDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idAvaliacaoFornecedor" ,"idAvaliacaoFornecedor", true, true, false, false));
		listFields.add(new Field("idFornecedor" ,"idFornecedor", false, false, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("dataAvaliacao" ,"dataAvaliacao", false, false, false, false));
		listFields.add(new Field("decisaoQualificacao" ,"decisaoQualificacao", false, false, false, false));
		listFields.add(new Field("observacoes" ,"observacoesAvaliacaoFornecedor", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "avaliacaoFornecedor";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return AvaliacaoFornecedorDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
    public Collection findByIdFornecedor(Integer parm) throws PersistenceException {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("idFornecedor", "=", parm)); 
        ordenacao.add(new Order("idAvaliacaoFornecedor"));
        return super.findByCondition(condicao, ordenacao);
    }
	
}
