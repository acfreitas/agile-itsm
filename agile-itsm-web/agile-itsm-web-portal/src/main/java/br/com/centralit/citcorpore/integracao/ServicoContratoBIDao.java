package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ServicoContratoBIDao extends CrudDaoDefaultImpl {
	public ServicoContratoBIDao() {
		super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
	}

	public static String strSGBDPrincipal = null;

	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idServicoContrato", "idServicoContrato", true, true, false, false));
		listFields.add(new Field("idServico", "idServico", false, false, false, false));
		listFields.add(new Field("idContrato", "idContrato", false, false, false, false));
		listFields.add(new Field("idCondicaoOperacao", "idCondicaoOperacao", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("observacao", "observacao", false, false, false, false));
		listFields.add(new Field("custo", "custo", false, false, false, false));
		listFields.add(new Field("restricoesPressup", "restricoesPressup", false, false, false, false));
		listFields.add(new Field("objetivo", "objetivo", false, false, false, false));
		listFields.add(new Field("permiteSLANoCadInc", "permiteSLANoCadInc", false, false, false, false));
		listFields.add(new Field("linkProcesso", "linkProcesso", false, false, false, false));
		listFields.add(new Field("descricaoProcesso", "descricaoProcesso", false, false, false, false));
		listFields.add(new Field("tipoDescProcess", "tipoDescProcess", false, false, false, false));
		listFields.add(new Field("areaRequisitante", "areaRequisitante", false, false, false, false));
		listFields.add(new Field("idModeloEmailCriacao", "idModeloEmailCriacao", false, false, false, false));
		listFields.add(new Field("idModeloEmailFinalizacao", "idModeloEmailFinalizacao", false, false, false, false));
		listFields.add(new Field("idModeloEmailAcoes", "idModeloEmailAcoes", false, false, false, false));
		listFields.add(new Field("idGrupoNivel1", "idGrupoNivel1", false, false, false, false));
		listFields.add(new Field("idGrupoExecutor", "idGrupoExecutor", false, false, false, false));
		listFields.add(new Field("idGrupoAprovador", "idGrupoAprovador", false, false, false, false));
		listFields.add(new Field("idCalendario", "idCalendario", false, false, false, false));
		listFields.add(new Field("permSLATempoACombinar", "permSLATempoACombinar", false, false, false, false));
		listFields.add(new Field("permMudancaSLA", "permMudancaSLA", false, false, false, false));
		listFields.add(new Field("permMudancaCalendario", "permMudancaCalendario", false, false, false, false));
		listFields.add(new Field("deleted", "deleted", false, false, false, false));
		listFields.add(new Field("idConexaoBI", "idConexaoBI", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return this.getOwner() + "ServicoContrato";
	}

	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return ServicoContratoDTO.class;
	}

	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

}
