package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoGEDDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HistoricoGEDDao extends CrudDaoDefaultImpl {

	public HistoricoGEDDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
		// TODO Auto-generated constructor stub
	}

	/**
     *
     */

	@Override
	public Collection find(IDto obj) throws PersistenceException {

		return null;
	}

	@Override
	public Collection<Field> getFields() {

		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idligacao_historico_ged", "idLigacaoHistoricoGed", true, true, false, false));
		listFields.add(new Field("idcontroleged", "idControleGed", false, false, false, false));
		listFields.add(new Field("idrequisicaoliberacao", "idRequisicaoMudanca", false, false, false, false));
		listFields.add(new Field("idhistoricoliberacao", "idHistoricoMudanca", false, false, false, false));
		listFields.add(new Field("idtabela", "idTabela", false, false, false, false));
		listFields.add(new Field("datafim", "dataFim", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "LIGACAO_HISTORICO_GED";
	}

	@Override
	public Collection list() throws PersistenceException {
		List list = new ArrayList();
		return super.list(list);
	}

	@Override
	public Class getBean() {
		return HistoricoGEDDTO.class;
	}

	/*public Collection listByIdTabelaAndIdLiberacao(Integer idTabela, Integer idRequisicaoLiberacao) throws PersistenceException {
		List parametros = new ArrayList();

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT idligacao_historico_ged, idcontroleged, idrequisicaoliberacao, idhistoricoliberacao, idtabela ");
		sql.append("FROM ligacao_historico_ged ");
		sql.append("WHERE idtabela = ? AND idrequisicaoliberacao = ? and datafim is null");

		parametros.add(idTabela);
		parametros.add(idRequisicaoLiberacao);

		List list = this.execSQL(sql.toString(), parametros.toArray());

		return this.engine.listConvertion(this.getBean(), list, (List) getFields());
	}*/


	public Collection listByIdTabelaAndIdLiberacao(Integer idTabela, Integer idRequisicaoLiberacao) throws PersistenceException {
		List lstRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();

		parametro.add(idTabela);
		parametro.add(idRequisicaoLiberacao);

		sql.append("SELECT idligacao_historico_ged, idcontroleged, idrequisicaoliberacao, idhistoricoliberacao, idtabela ");
		sql.append("FROM ligacao_historico_ged ");
		sql.append("WHERE idtabela = ? AND idrequisicaoliberacao = ? and datafim is null");




		list = this.execSQL(sql.toString(),  parametro.toArray());

		lstRetorno.add("idLigacaoHistoricoGed");
		lstRetorno.add("idControleGed");
		lstRetorno.add("idRequisicaoLiberacao");
		lstRetorno.add("idHistoricoLiberacao");
		lstRetorno.add("idTabela");


		if (list != null && !list.isEmpty()) {

			return (List<UploadDTO>) this.listConvertion(this.getBean(), list, lstRetorno);

		} else {

			return null;
		}
	}
	public Collection listByIdTabelaAndIdLiberacaoEDataFim(Integer idTabela, Integer idRequisicaoLiberacao) throws PersistenceException {
		List lstRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();

		parametro.add(idTabela);
		parametro.add(idRequisicaoLiberacao);

		sql.append("SELECT idligacao_historico_ged, idcontroleged, idrequisicaoliberacao, idhistoricoliberacao, idtabela ");
		sql.append("FROM ligacao_historico_ged ");
		sql.append("WHERE idtabela = ? AND idrequisicaoliberacao = ? and datafim is not null");




		list = this.execSQL(sql.toString(),  parametro.toArray());

		lstRetorno.add("idLigacaoHistoricoGed");
		lstRetorno.add("idControleGed");
		lstRetorno.add("idRequisicaoLiberacao");
		lstRetorno.add("idHistoricoLiberacao");
		lstRetorno.add("idTabela");


		if (list != null && !list.isEmpty()) {

			return (List<UploadDTO>) this.listConvertion(this.getBean(), list, lstRetorno);

		} else {

			return null;
		}
	}

}
