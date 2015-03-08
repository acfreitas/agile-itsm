package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.ContratoBIDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

/**
 * @author Thays
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ContratoBIDao extends CrudDaoDefaultImpl {


	public ContratoBIDao() {
		super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
	}

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idContrato", "idContrato", true, true, false, false));
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
		listFields.add(new Field("idconexaobi", "idConexaoBI", false, false, false, false));
		listFields.add(new Field("idsuperintendente", "idSuperintendente", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "Contratos";
	}

	public Collection list() throws PersistenceException {
		/*
		 * List condicao = new ArrayList(); List ordenacao = new ArrayList(); condicao.add(new Condition("deleted", "=", "n")); ordenacao.add(new Order("numero")); return
		 * super.findByCondition(condicao, ordenacao);
		 */
		Collection colFinal = new ArrayList();
		Collection col = super.list("numero");
		for (Iterator it = col.iterator(); it.hasNext();) {
			ContratoBIDTO contratoDto = (ContratoBIDTO) it.next();
			if (contratoDto.getDeleted() == null || contratoDto.getDeleted().equalsIgnoreCase("n")) {
				colFinal.add(contratoDto);
			}
		}
		return colFinal;
	}

	public Class getBean() {
		return ContratoBIDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
}
