package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MonitoramentoAtivosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MonitoramentoAtivosDAO extends CrudDaoDefaultImpl {

	public MonitoramentoAtivosDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDMONITORAMENTOATIVOS", "idMonitoramentoAtivos", true, true, false, false));
		listFields.add(new Field("IDTIPOITEMCONFIGURACAO", "idTipoItemConfiguracao", false, false, false, false));
		listFields.add(new Field("TITULO", "titulo", false, false, false, false));
		listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
		listFields.add(new Field("TIPOREGRA", "tipoRegra", false, false, false, false));
		listFields.add(new Field("ENVIAREMAIL", "enviarEmail", false, false, false, false));
		listFields.add(new Field("CRIARPROBLEMA", "criarProblema", false, false, false, false));
		listFields.add(new Field("CRIARINCIDENTE", "criarIncidente", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));

		return listFields;
	}

	/**
	 * Retorna MonitoramentoAtivo relacionado à Característica do Tipo Item Configuração informado.
	 * 
	 * @param idTipoItemConfiguracao
	 *            - Identificador do Tipo Item Configuração.
	 * @param idCaracteristica
	 *            - Identificador da Característica.
	 * @return MonitoramentoAtivosDTO relacionado.
	 * @since 16.06.2014
	 * @author valdoilo.damasceno
	 */
	public MonitoramentoAtivosDTO obterMonitorametoAtivoDaCaracteristica(Integer idTipoItemConfiguracao, Integer idCaracteristica) {

		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();
		List dados = new ArrayList();
		List listMonitoramentoAtivosDTO = new ArrayList();

		sql.append(" select " + this.getNamesFieldsStr("m"));
		sql.append(" from monitoramentoativos m ");
		sql.append(" join caracteristicamonit c on m.idmonitoramentoativos = c.idmonitoramentoativos ");
		sql.append(" where m.idtipoitemconfiguracao = ? and c.idcaracteristica = ? and m.tiporegra = ? and m.datafim is null ");

		parametros.add(idTipoItemConfiguracao);
		parametros.add(idCaracteristica);
		parametros.add("c");

		try {
			dados = this.execSQL(sql.toString(), parametros.toArray());
			listMonitoramentoAtivosDTO = this.listConvertion(MonitoramentoAtivosDTO.class, dados, getListNamesFieldClass());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (listMonitoramentoAtivosDTO != null && !listMonitoramentoAtivosDTO.isEmpty()) {
			return (MonitoramentoAtivosDTO) listMonitoramentoAtivosDTO.get(0);
		}

		return null;
	}

	/**
	 * Obtém o MonitoramentoAtivo do TipoItemConfiguração.
	 * 
	 * @param idTipoItemConfiguracao
	 *            - Identificador do Tipo de Item Configuração.
	 * @return MonitoramentoAtivosDTO
	 * @since 16.06.2014
	 * @author valdoilo.damasceno
	 */
	public MonitoramentoAtivosDTO obterMonitorametoAtivoDoTipoItemConfiguracao(Integer idTipoItemConfiguracao) {

		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();
		List dados = new ArrayList();
		List listRetorno = getListNamesFieldClass();
		listRetorno.add("script");
		
		List listMonitoramentoAtivosDTO = new ArrayList();

		sql.append(" select " + this.getNamesFieldsStr("m") + ", s.script ");
		sql.append(" from monitoramentoativos m ");
		sql.append(" join scriptmonit s on m.idmonitoramentoativos = s.idmonitoramentoativos ");
		sql.append(" where m.idtipoitemconfiguracao = ? and m.tiporegra = ? and m.datafim is null ");

		parametros.add(idTipoItemConfiguracao);
		parametros.add("s");

		try {
			dados = this.execSQL(sql.toString(), parametros.toArray());
			listMonitoramentoAtivosDTO = this.listConvertion(MonitoramentoAtivosDTO.class, dados, listRetorno);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (listMonitoramentoAtivosDTO != null && !listMonitoramentoAtivosDTO.isEmpty()) {
			return (MonitoramentoAtivosDTO) listMonitoramentoAtivosDTO.get(0);
		}

		return null;
	}

	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	@Override
	public String getTableName() {
		return "monitoramentoativos";
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return MonitoramentoAtivosDTO.class;
	}

}