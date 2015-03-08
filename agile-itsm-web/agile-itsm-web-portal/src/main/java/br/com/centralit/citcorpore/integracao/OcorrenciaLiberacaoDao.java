/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.OcorrenciaLiberacaoDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author breno.guimaraes
 * 
 */
public class OcorrenciaLiberacaoDao extends CrudDaoDefaultImpl {

    public OcorrenciaLiberacaoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(IDto arg0) throws PersistenceException {
	return null;
    }

    @Override
    public Collection<Field> getFields() {
	Collection<Field> listFields = new ArrayList<>();
	listFields.add(new Field("idocorrencia" ,"idOcorrencia", true, true, false, false));
	listFields.add(new Field("idrequisicaoLiberacao" ,"idRequisicaoLiberacao", false, false, false, false));
	listFields.add(new Field("iditemtrabalho" ,"idItemTrabalho", false, false, false, false));
	listFields.add(new Field("idjustificativa" ,"idJustificativa", false, false, false, false));
	listFields.add(new Field("datainicio" ,"dataInicio", false, false, false, false));
	listFields.add(new Field("datafim" ,"dataFim", false, false, false, false));
	listFields.add(new Field("categoria" ,"categoria", false, false, false, false));
	listFields.add(new Field("origem" ,"origem", false, false, false, false));
	listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
	listFields.add(new Field("ocorrencia" ,"ocorrencia", false, false, false, false));
	listFields.add(new Field("informacoescontato" ,"informacoesContato", false, false, false, false));
	listFields.add(new Field("tempogasto" ,"tempoGasto", false, false, false, false));
	listFields.add(new Field("dataregistro" ,"dataregistro", false, false, false, false));
	listFields.add(new Field("horaregistro" ,"horaregistro", false, false, false, false));
	listFields.add(new Field("registradopor" ,"registradopor", false, false, false, false));
	listFields.add(new Field("complementojustificativa" ,"complementoJustificativa", false, false, false, false));
	listFields.add(new Field("dadosliberacao" ,"dadosLiberacao", false, false, false, false));
	listFields.add(new Field("idcategoriaocorrencia" ,"idCategoriaOcorrencia", false, false, false, false));
	listFields.add(new Field("idorigemocorrencia" ,"idOrigemOcorrencia", false, false, false, false));
	return listFields;
    }
    
    public Collection findByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws PersistenceException {
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	
	condicao.add(new Condition("idRequisicaoLiberacao", "=", idRequisicaoLiberacao));
	ordenacao.add(new Order("dataregistro"));
	ordenacao.add(new Order("idOcorrencia"));
	return super.findByCondition(condicao, ordenacao);
    }

    @Override
    public String getTableName() {
	return "ocorrencialiberacao";
    }

    @Override
    public Collection list() throws PersistenceException {
	return null;
    }

    @Override
    public Class getBean() {
	return OcorrenciaLiberacaoDTO.class;
    }

    public Collection<OcorrenciaMudancaDTO> listByIdMudancaAndCategoria(Integer idRequisicaoLiberacao, CategoriaOcorrencia categoria) throws PersistenceException {
    	List condicao = new ArrayList();
    	List ordenacao = new ArrayList();
    	
    	condicao.add(new Condition("idSolicitacaoServico", "=", idRequisicaoLiberacao));
    	condicao.add(new Condition("categoria", "=", categoria.name()));
    	ordenacao.add(new Order("dataregistro"));
    	ordenacao.add(new Order("idOcorrencia"));
    	return super.findByCondition(condicao, ordenacao);
   }
}
