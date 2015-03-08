package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratoHistoricoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author Thays
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ContratoHistoricoDao extends CrudDaoDefaultImpl {


	public ContratoHistoricoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idContrato_Hist", "idContrato_Hist", true, true, false, false));
		listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
		listFields.add(new Field("idCliente", "idCliente", false, false, false, false));
		listFields.add(new Field("numero", "numero", false, false, false, false));
		listFields.add(new Field("objeto", "objeto", false, false, false, false));
		listFields.add(new Field("dataContrato", "dataContrato", false, false, false, false));
		listFields.add(new Field("dataFimContrato", "dataFimContrato", false, false, false, false));
		listFields.add(new Field("valorEstimado", "valorEstimado", false, false, false, false));
		listFields.add(new Field("tipoTempoEstimado", "tipoTempoEstimado", false, false, false, false));
		listFields.add(new Field("tempoEstimado", "tempoEstimado", false, false, false, false));
		listFields.add(new Field("tipo", "tipo", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("idMoeda", "idMoeda", false, false, false, false));
		listFields.add(new Field("cotacaoMoeda", "cotacaoMoeda", false, false, false, false));
		listFields.add(new Field("idFornecedor", "idFornecedor", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		listFields.add(new Field("idgruposolicitante", "idGrupoSolicitante", false, false, false, false));
		listFields.add(new Field("criadoEm" ,"criadoEm", false, false, false, false));
		listFields.add(new Field("criadoPor" ,"criadoPor", false, false, false, false));
		listFields.add(new Field("modificadoEm" ,"modificadoEm", false, false, false, false));
		listFields.add(new Field("modificadoPor" ,"modificadoPor", false, false, false, false));
		listFields.add(new Field("conteudodados" ,"conteudodados", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "contratos_hist";
	}

	public Collection list() throws PersistenceException {
		/*
		 * List condicao = new ArrayList(); List ordenacao = new ArrayList(); condicao.add(new Condition("deleted", "=", "n")); ordenacao.add(new Order("numero")); return
		 * super.findByCondition(condicao, ordenacao);
		 */
		Collection colFinal = new ArrayList();
		Collection col = super.list("numero");
		for (Iterator it = col.iterator(); it.hasNext();) {
			ContratoDTO contratoDto = (ContratoDTO) it.next();
			if (contratoDto.getDeleted() == null || contratoDto.getDeleted().equalsIgnoreCase("n")) {
				colFinal.add(contratoDto);
			}
		}
		return colFinal;
	}

	public Class getBean() {
		return ContratoHistoricoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	public Collection findByIdCliente(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idCliente", "=", parm));
		ordenacao.add(new Order("numero"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	public Collection findByIdContratoOrderHist(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idContrato", "=", parm));
		ordenacao.add(new Order("idContrato_Hist"));
		return super.findByCondition(condicao, ordenacao);
	}	

	public void deleteByIdCliente(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		condicao.add(new Condition("idCliente", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public Collection findByIdContrato(Integer parm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idContrato", "=", parm));
		return super.findByCondition(condicao, ordenacao);
	}

}
