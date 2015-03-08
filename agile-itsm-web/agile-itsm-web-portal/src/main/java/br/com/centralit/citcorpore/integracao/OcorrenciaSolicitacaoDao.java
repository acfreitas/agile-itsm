/**
 *
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OcorrenciaSolicitacaoDao extends CrudDaoDefaultImpl {

	private static final String TABLE_NAME = "ocorrenciasolicitacao";

	public OcorrenciaSolicitacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idocorrencia", "idOcorrencia", true, true, false, false));
		listFields.add(new Field("idsolicitacaoservico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("iditemtrabalho", "idItemTrabalho", false, false, false, false));
		listFields.add(new Field("idjustificativa", "idJustificativa", false, false, false, false));
		listFields.add(new Field("datainicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("datafim", "dataFim", false, false, false, false));
		listFields.add(new Field("categoria", "categoria", false, false, false, false));
		listFields.add(new Field("origem", "origem", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("ocorrencia", "ocorrencia", false, false, false, false));
		listFields.add(new Field("informacoescontato", "informacoesContato", false, false, false, false));
		listFields.add(new Field("tempogasto", "tempoGasto", false, false, false, false));
		listFields.add(new Field("dataregistro", "dataregistro", false, false, false, false));
		listFields.add(new Field("horaregistro", "horaregistro", false, false, false, false));
		listFields.add(new Field("registradopor", "registradopor", false, false, false, false));
		listFields.add(new Field("complementojustificativa", "complementoJustificativa", false, false, false, false));
		listFields.add(new Field("dadossolicitacao", "dadosSolicitacao", false, false, false, false));
		listFields.add(new Field("idcategoriaocorrencia", "idCategoriaOcorrencia", false, false, false, false));
		listFields.add(new Field("idorigemocorrencia", "idOrigemOcorrencia", false, false, false, false));
		listFields.add(new Field("notificarsolicitante", "notificarSolicitante", false, false, false, false));
		listFields.add(new Field("notificarresponsavel", "notificarResponsavel", false, false, false, false));
		return listFields;
	}

	public Collection findByIdSolicitacaoServico(Integer idSolicitacaoServicoParm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServicoParm));
		ordenacao.add(new Order("dataregistro"));
		ordenacao.add(new Order("idOcorrencia"));
		return super.findByCondition(condicao, ordenacao);
	}

	public OcorrenciaSolicitacaoDTO findUltimoByIdSolicitacaoServico(Integer idSolicitacaoServicoParm) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();

		StringBuilder sql = new StringBuilder();

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.POSTGRESQL) || CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.MYSQL)) {
			sql.append("select o.idocorrencia, o.idjustificativa, o.idsolicitacaoservico, o.iditemtrabalho, o.dataregistro, o.horaregistro, o.registradopor, "
					+ " o.descricao, o.datainicio, o.datafim, o.complementojustificativa, o.dadossolicitacao, o.informacoescontato, "
					+ " o.categoria, o.origem, o.tempogasto, o.ocorrencia, o.idcategoriaocorrencia, o.idorigemocorrencia, o.notificarsolicitante " + " from ocorrenciasolicitacao o " + " where o.idsolicitacaoservico = ? "
					+ " order by o.idocorrencia desc limit 1 ");

		} else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {

			sql.append("select o.idocorrencia, o.idjustificativa, o.idsolicitacaoservico, o.iditemtrabalho, o.dataregistro, o.horaregistro, o.registradopor, "
					+ " o.descricao, o.datainicio, o.datafim, o.complementojustificativa, o.dadossolicitacao, o.informacoescontato, "
					+ " o.categoria, o.origem, o.tempogasto, o.ocorrencia, o.idcategoriaocorrencia, o.idorigemocorrencia, o.notificarsolicitante " + " from ocorrenciasolicitacao o "
					+ " where o.idsolicitacaoservico = ? and rownum = 1 " + " order by o.idocorrencia desc  ");

		} else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {

			sql.append("select top 1 o.idocorrencia, o.idjustificativa, o.idsolicitacaoservico, o.iditemtrabalho, o.dataregistro, o.horaregistro, o.registradopor, "
					+ " o.descricao, o.datainicio, o.datafim, o.complementojustificativa, o.dadossolicitacao, o.informacoescontato, "
					+ " o.categoria, o.origem, o.tempogasto, o.ocorrencia, o.idcategoriaocorrencia, o.idorigemocorrencia, o.notificarsolicitante " + " from ocorrenciasolicitacao o " + " where o.idsolicitacaoservico = ? "
					+ " order by o.idocorrencia desc  ");
		}

		parametro.add(idSolicitacaoServicoParm);

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idOcorrencia");
		listRetorno.add("idJustificativa");
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("idItemTrabalho");
		listRetorno.add("dataregistro");
		listRetorno.add("horaregistro");
		listRetorno.add("registradopor");
		listRetorno.add("descricao");
		listRetorno.add("dataInicio");
		listRetorno.add("dataFim");
		listRetorno.add("complementoJustificativa");
		listRetorno.add("dadosSolicitacao");
		listRetorno.add("informacoesContato");
		listRetorno.add("categoria");
		listRetorno.add("origem");
		listRetorno.add("tempoGasto");
		listRetorno.add("ocorrencia");
		listRetorno.add("idCategoriaOcorrencia");
		listRetorno.add("idOrigemOcorrencia");
		listRetorno.add("notificarSolicitante");

		if (list != null && !list.isEmpty()) {
			List result = this.listConvertion(getBean(), list, listRetorno);
			return (OcorrenciaSolicitacaoDTO) result.get(0);
		} else {
			return null;
		}
	}

	public OcorrenciaSolicitacaoDTO findUltimoByIdSolicitacaoServicoAndOcorrencia(Integer idSolicitacaoServico) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		String strOcorr = "Escalação automática";

		StringBuilder sql = new StringBuilder();
		sql.append("select o.idocorrencia, o.idjustificativa, o.idsolicitacaoservico, o.iditemtrabalho, o.dataregistro, o.horaregistro, o.registradopor, "
				+ " o.descricao, o.datainicio, o.datafim, o.complementojustificativa, o.dadossolicitacao, o.informacoescontato, "
				+ " o.categoria, o.origem, o.tempogasto, o.ocorrencia, o.idcategoriaocorrencia, o.idorigemocorrencia, o.notificarsolicitante " + " from ocorrenciasolicitacao o "
				+ " where o.idsolicitacaoservico = ? and o.idocorrencia = (select max(idocorrencia) from ocorrenciasolicitacao where ocorrencia like '%" + strOcorr + "%' )"
				+ " order by o.idocorrencia desc");

		parametro.add(idSolicitacaoServico);

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idOcorrencia");
		listRetorno.add("idJustificativa");
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("idItemTrabalho");
		listRetorno.add("dataregistro");
		listRetorno.add("horaregistro");
		listRetorno.add("registradopor");
		listRetorno.add("descricao");
		listRetorno.add("dataInicio");
		listRetorno.add("dataFim");
		listRetorno.add("complementoJustificativa");
		listRetorno.add("dadosSolicitacao");
		listRetorno.add("informacoesContato");
		listRetorno.add("categoria");
		listRetorno.add("origem");
		listRetorno.add("tempoGasto");
		listRetorno.add("ocorrencia");
		listRetorno.add("idCategoriaOcorrencia");
		listRetorno.add("idOrigemOcorrencia");
		listRetorno.add("notificarSolicitante");

		if (list != null && !list.isEmpty()) {
			List result = this.listConvertion(getBean(), list, listRetorno);
			return (OcorrenciaSolicitacaoDTO) result.get(0);
		} else {
			return null;
		}
	}

	public Collection findOcorrenciaDoTesteByIdSolicitacaoServico(Integer idSolicitacaoServicoParm) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		Collection<SolicitacaoServicoDTO> solicitacoes = new ArrayList();
		StringBuilder sql = new StringBuilder();

		String string = "Execução da tarefa \"Testes/Controle de qualidade\"";

		sql.append("select o.ocorrencia, u.idusuario from ocorrenciasolicitacao o " + " inner join solicitacaoservico s on o.idsolicitacaoservico = s.idsolicitacaoservico "
				+ " inner join usuario u on o.registradopor = u.login " + " where o.ocorrencia like '%" + string + "%' and s.idsolicitacaoservico = ?");

		parametro.add(idSolicitacaoServicoParm);

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("ocorrencia");
		listRetorno.add("idUsuario");

		if (list != null && !list.isEmpty()) {
			solicitacoes = (Collection<SolicitacaoServicoDTO>) this.listConvertion(getBean(), list, listRetorno);
		}
		return solicitacoes;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return OcorrenciaSolicitacaoDTO.class;
	}

	public Collection<OcorrenciaSolicitacaoDTO> listByIdSolicitacaoAndCategoria(Integer idSolicitacaoServico, CategoriaOcorrencia categoria) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		condicao.add(new Condition("categoria", "=", categoria.name()));
		ordenacao.add(new Order("dataregistro"));
		ordenacao.add(new Order("idOcorrencia"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection<OcorrenciaSolicitacaoDTO> findByIdPessoaEDataAtendidasGrupoTeste(Integer idPessoa, Date dataInicio, Date dataFim) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();

		StringBuilder sql = new StringBuilder();
		sql.append("select solicitacaoservico.idsolicitacaoservico, usuario.nome, historicosolicitacaoservico.idresponsavelatual, solicitacaoservico.urgencia "
				+ " from ocorrenciasolicitacao ocorrenciasolicitacao " + " INNER JOIN solicitacaoservico solicitacaoservico "
				+ " ON solicitacaoservico.idsolicitacaoservico = ocorrenciasolicitacao.idsolicitacaoservico " + " INNER JOIN historicosolicitacaoservico historicosolicitacaoservico "
				+ " ON historicosolicitacaoservico.idocorrencia = ocorrenciasolicitacao.idocorrencia " + " INNER JOIN usuario usuario "
				+ " ON usuario.idusuario = historicosolicitacaoservico.idresponsavelatual " + " WHERE ocorrenciasolicitacao.ocorrencia like '%Execução da tarefa \"Testes/Controle de qualidade\"%' "
				+ " and solicitacaoservico.datahorainiciosla between ? and ? " + " and solicitacaoservico.datahorafim < solicitacaoservico.datahoralimite "
				+ " and historicosolicitacaoservico.idresponsavelatual = ? " + " group by solicitacaoservico.idsolicitacaoservico " + " order by solicitacaoservico.idsolicitacaoservico ");

		parametro.add(dataInicio);
		parametro.add(dataFim);
		parametro.add(idPessoa);

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("nome");
		listRetorno.add("idResponsavelAtual");
		listRetorno.add("urgencia");

		if (list != null && !list.isEmpty()) {
			return (Collection<OcorrenciaSolicitacaoDTO>) this.listConvertion(getBean(), list, listRetorno);
		} else {
			return null;
		}
	}

	public Collection<OcorrenciaSolicitacaoDTO> findByIdPessoaGrupoTeste(Integer idPessoa, Date dataInicio, Date dataFim) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();

		StringBuilder sql = new StringBuilder();
		sql.append("select solicitacaoservico.idsolicitacaoservico, usuario.nome, historicosolicitacaoservico.idresponsavelatual, solicitacaoservico.urgencia, "
				+ " solicitacaoservico.datahoralimite, solicitacaoservico.datahorafim " + " from ocorrenciasolicitacao ocorrenciasolicitacao " + " INNER JOIN solicitacaoservico solicitacaoservico "
				+ " ON solicitacaoservico.idsolicitacaoservico = ocorrenciasolicitacao.idsolicitacaoservico " + " INNER JOIN historicosolicitacaoservico historicosolicitacaoservico "
				+ " ON historicosolicitacaoservico.idocorrencia = ocorrenciasolicitacao.idocorrencia " + " INNER JOIN usuario usuario "
				+ " ON usuario.idusuario = historicosolicitacaoservico.idresponsavelatual " + " WHERE ocorrenciasolicitacao.ocorrencia like '%Execução da tarefa \"Testes/Controle de qualidade\"%' "
				+ " and solicitacaoservico.datahorainiciosla between ? and ? " + " and historicosolicitacaoservico.idresponsavelatual = ? " + " group by solicitacaoservico.idsolicitacaoservico "
				+ " order by solicitacaoservico.idsolicitacaoservico ");

		parametro.add(dataInicio);
		parametro.add(dataFim);
		parametro.add(idPessoa);

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("nome");
		listRetorno.add("idResponsavelAtual");
		listRetorno.add("urgencia");
		listRetorno.add("dataHoraLimite");
		listRetorno.add("dataHoraFim");

		if (list != null && !list.isEmpty()) {
			return (Collection<OcorrenciaSolicitacaoDTO>) this.listConvertion(getBean(), list, listRetorno);
		} else {
			return null;
		}
	}

	public OcorrenciaSolicitacaoDTO findByIdOcorrencia(Integer idOcorrencia) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sb = new StringBuilder();

		sb.append("select dadossolicitacao from ocorrenciasolicitacao where idocorrencia = ?");

		parametro.add(idOcorrencia);

		List lista = this.execSQL(sb.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("dadosSolicitacao");

		if (lista != null && !lista.isEmpty()) {
			List result = this.engine.listConvertion(OcorrenciaSolicitacaoDTO.class, lista, listRetorno);
			return (OcorrenciaSolicitacaoDTO) result.get(0);
		} else {
			return new OcorrenciaSolicitacaoDTO();
		}
	}

	public OcorrenciaSolicitacaoDTO quantidadeDeOcorrenciasDeAlteracaoSlaPorNumeroDaSolicitacao(Integer idSolicitacaoServico) throws PersistenceException {
		List parametro = new ArrayList();
		StringBuilder sb = new StringBuilder();

		sb.append("SELECT count(oc.idsolicitacaoservico) as totalOcorrenciasAlterarcaoSlaPorSolicitacao FROM	ocorrenciasolicitacao oc WHERE	(oc.categoria LIKE 'MudancaSLA' ) AND  oc.idsolicitacaoservico = ?");

		parametro.add(idSolicitacaoServico);

		List lista = this.execSQL(sb.toString(), parametro.toArray());
		List<String> listRetorno = new ArrayList<String>();
		listRetorno.add("totalOcorrenciasAlterarcaoSlaPorSolicitacao");

		if (lista != null && !lista.isEmpty()) {
			List result = this.engine.listConvertion(OcorrenciaSolicitacaoDTO.class, lista, listRetorno);
			return (OcorrenciaSolicitacaoDTO) result.get(0);
		} else {
			return new OcorrenciaSolicitacaoDTO();
		}
	}
	

	public OcorrenciaSolicitacaoDTO findUltimaTarefaByIdSolicitacaoServico(Integer idSolicitacaoServicoParm) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServicoParm));
		condicao.add(new Condition("idItemTrabalho", "IS NOT", null));
		ordenacao.add(new Order("dataregistro", Order.DESC));
		List<OcorrenciaSolicitacaoDTO> result = (List<OcorrenciaSolicitacaoDTO>) super.findByCondition(condicao, ordenacao);
		if (result != null && !result.isEmpty())
			return result.get(0);
		else
			return null;
}

}
