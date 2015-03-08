/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.core.DataBase;
import br.com.citframework.integracao.core.Page;
import br.com.citframework.integracao.core.PageImpl;
import br.com.citframework.integracao.core.Pageable;
import br.com.citframework.integracao.core.PagingQueryUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

/**
 * @author valdoilo.damasceno
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseConhecimentoDAO extends CrudDaoDefaultImpl {

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("IDBASECONHECIMENTO", "idBaseConhecimento", true, true, false, false));
		listFields.add(new Field("IDPASTA", "idPasta", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		listFields.add(new Field("TITULO", "titulo", false, false, false, false));
		listFields.add(new Field("CONTEUDO", "conteudo", false, false, false, false));
		listFields.add(new Field("STATUS", "status", false, false, false, false));
		listFields.add(new Field("IDBASECONHECIMENTOPAI", "idBaseConhecimentoPai", false, false, false, false));
		listFields.add(new Field("DATAEXPIRACAO", "dataExpiracao", false, false, false, false));
		listFields.add(new Field("VERSAO", "versao", false, false, false, false));
		listFields.add(new Field("IDUSUARIOAUTOR", "idUsuarioAutor", false, false, false, false));
		listFields.add(new Field("IDUSUARIOAPROVADOR", "idUsuarioAprovador", false, false, false, false));
		listFields.add(new Field("FONTEREFERENCIA", "fonteReferencia", false, false, false, false));
		listFields.add(new Field("DATAPUBLICACAO", "dataPublicacao", false, false, false, false));
		listFields.add(new Field("IDNOTIFICACAO", "idNotificacao", false, false, false, false));
		listFields.add(new Field("JUSTIFICATIVAOBSERVACAO", "justificativaObservacao", false, false, false, false));
		listFields.add(new Field("FAQ", "faq", false, false, false, false));
		listFields.add(new Field("ORIGEM", "origem", false, false, false, false));
		listFields.add(new Field("ARQUIVADO", "arquivado", false, false, false, false));
		listFields.add(new Field("IDHISTORICOBASECONHECIMENTO", "idHistoricoBaseConhecimento", false, false, false, false));
		listFields.add(new Field("PRIVACIDADE", "privacidade", false, false, false, false));
		listFields.add(new Field("SITUACAO", "situacao", false, false, false, false));
		listFields.add(new Field("gerenciamentoDisponibilidade", "gerenciamentoDisponibilidade", false, false, false, false));
		listFields.add(new Field("direitoAutoral", "direitoAutoral", false, false, false, false));
		listFields.add(new Field("legislacao", "legislacao", false, false, false, false));
		listFields.add(new Field("CONTEUDOSEMFORMATACAO", "conteudoSemFormatacao", false, false, false, false));
		listFields.add(new Field("ERROCONHECIDO", "erroConhecido", false, false, false, false));
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "BASECONHECIMENTO";
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idBaseConhecimento"));
		return super.list(ordenacao);
	}

	@Override
	public Class getBean() {
		return BaseConhecimentoDTO.class;
	}

	public BaseConhecimentoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}

	/**
	 * Buscar lista de Bases de Conhecimento por Serviço
	 * 
	 * @param baseConhecimentoDto
	 * @return
	 * @throws Exception
	 */
	public List<BaseConhecimentoDTO> findByServico(SolicitacaoServicoDTO solicitacaoServicoDto) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select baseconhecimento.idbaseconhecimento, baseconhecimento.titulo ");
		sql.append("from baseconhecimento inner join conhecimentosolicitacaoservico on baseconhecimento.idbaseconhecimento = conhecimentosolicitacaoservico.idbaseconhecimento ");
		sql.append("where conhecimentosolicitacaoservico.idsolicitacaoservico = ? ");

		parametro.add(solicitacaoServicoDto.getIdSolicitacaoServico());

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idbaseconhecimento");
		listRetorno.add("titulo");

		if (list != null && !list.isEmpty()) {

			return (List<BaseConhecimentoDTO>) this.listConvertion(getBean(), list, listRetorno);

		} else {

			return null;
		}
	}

	public BaseConhecimentoDTO findByIdSolicitacaoServico(SolicitacaoServicoDTO solicitacaoServicoDto) throws PersistenceException {
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select idbaseconhecimento, titulo ");
		sql.append("from baseconhecimento ");
		sql.append("where idsolicitacaoservico = ? ");

		parametro.add(solicitacaoServicoDto.getIdSolicitacaoServico());

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("titulo");

		if (list != null && !list.isEmpty()) {

			return (BaseConhecimentoDTO) this.listConvertion(getBean(), list, listRetorno).get(0);

		} else {

			return null;
		}
	}

	/**
	 * Lista Base de Conhecimento por Pasta.
	 * 
	 * @param pasta
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 */
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByPasta(PastaDTO pasta) throws PersistenceException {

		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		Date dataAtual = UtilDatas.getDataAtual();

		sql.append("select baseconhecimento.idbaseconhecimento,baseconhecimento.idpasta,baseconhecimento.titulo, baseConhecimento.privacidade FROM pasta pasta ");
		sql.append("INNER JOIN baseconhecimento baseconhecimento ON pasta.idpasta = baseconhecimento.idpasta  ");
		sql.append("where pasta.idpasta = ? AND baseconhecimento.dataFim is null and baseconhecimento.status = ? AND (baseconhecimento.arquivado IS NULL OR baseconhecimento.arquivado = ?) ");
		sql.append(" and dataexpiracao >= ? ");

		parametro.add(pasta.getId());

		parametro.add("S");

		parametro.add("N");

		parametro.add(dataAtual);

		List lista = new ArrayList();

		lista = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("idPasta");
		listRetorno.add("titulo");
		listRetorno.add("privacidade");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);

		return result;
	}

	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByIds(Integer[] ids) throws PersistenceException {

		List parametro = new ArrayList();

		String sql = "";

		Date dataAtual = UtilDatas.getDataAtual();

		sql = "select baseconhecimento.idbaseconhecimento,baseconhecimento.idpasta,baseconhecimento.titulo, baseConhecimento.privacidade FROM pasta pasta ";
		sql += "INNER JOIN baseconhecimento baseconhecimento ON pasta.idpasta = baseconhecimento.idpasta  ";
		sql += "where baseconhecimento.idbaseconhecimento in (#ID#) AND baseconhecimento.dataFim is null and baseconhecimento.status = ? AND (baseconhecimento.arquivado IS NULL OR baseconhecimento.arquivado = ?) ";
		sql += " and dataexpiracao >= ? ";

		String idsStr = "-9999";
		if (ids != null) {
			for (int i = 0; i < ids.length; i++) {
				idsStr = idsStr + ",";
				idsStr = idsStr + ids[i];
			}
		}

		sql = sql.replaceAll("#ID#", idsStr);

		parametro.add("S");

		parametro.add("N");

		parametro.add(dataAtual);

		List lista = new ArrayList();

		lista = this.execSQL(sql, parametro.toArray());

		List listRetorno = new ArrayList();

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("idPasta");
		listRetorno.add("titulo");
		listRetorno.add("privacidade");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);

		return result;
	}

	/**
	 * Lista Base de Conhecimento por Pasta para relatório.
	 * 
	 * @param pasta
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 */
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoByPastaRelatorio(PastaDTO pasta) throws PersistenceException {

		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("SELECT baseConhecimento.idBaseConhecimento,baseConhecimento.idpasta,baseConhecimento.titulo FROM pasta pasta ");
		sql.append("INNER JOIN baseConhecimento baseConhecimento ON pasta.idpasta = baseConhecimento.idpasta  ");
		sql.append("where pasta.idpasta = ? AND baseconhecimento.dataFim is null ");

		parametro.add(pasta.getId());

		List lista = new ArrayList();

		lista = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("idPasta");
		listRetorno.add("titulo");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);

		return result;
	}

	/**
	 * Verifica se base conhecimento informada possui nova versão.
	 * 
	 * @param baseConhecimento
	 * @return
	 * @throws Exception
	 */
	public boolean verificarSeBaseConhecimentoJaPossuiNovaVersao(BaseConhecimentoDTO baseConhecimento) throws PersistenceException {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("titulo", "like", StringUtils.remove(baseConhecimento.getTitulo(), " - v" + baseConhecimento.getVersao()) + " - %"));

		ordenacao.add(new Order("titulo"));

		Collection<BaseConhecimentoDTO> list = this.findByCondition(condicao, ordenacao);

		if (list != null && !list.isEmpty()) {

			for (BaseConhecimentoDTO base : list) {

				if (base.getDataFim() == null) {
					if (baseConhecimento.getVersao() == null || baseConhecimento.getVersao().trim().equalsIgnoreCase("")) {
						baseConhecimento.setVersao("1.0");
					}
					if (base.getVersao() == null || base.getVersao().trim().equalsIgnoreCase("")) {
						base.setVersao("1.0");
					}
					if (Double.parseDouble(baseConhecimento.getVersao()) < Double.parseDouble(base.getVersao())) {
						return true;
					}
				}

			}
		}
		return false;
	}

	/**
	 * Método para verificar se já existe uma base de conhecimento com o mesmo nome
	 * 
	 * @author rodrigo.oliveira
	 * @param BaseConhecimentoDTO
	 * @return se caso existir retorna true
	 */
	public boolean verificaSeBaseConhecimentoExiste(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {

		List parametro = new ArrayList();
		List list = new ArrayList();
		String sql = "SELECT idbaseconhecimento FROM " + getTableName() + " WHERE titulo = ? AND dataFim IS NULL ";

		if (baseConhecimentoDTO.getIdBaseConhecimento() != null) {
			sql += " AND idbaseconhecimento <> " + baseConhecimentoDTO.getIdBaseConhecimento();
		}

		parametro.add(baseConhecimentoDTO.getTitulo());
		list = this.execSQL(sql, parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Busca comentarios por nota maior... Funcionalidade #368
	 * 
	 * @param baseconhecimento
	 * @param
	 * @return
	 * @throws Exception
	 */
	public List<BaseConhecimentoDTO> validaNota(BaseConhecimentoDTO baseconhecimento) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametros = new ArrayList();

		List fields = new ArrayList();
		fields.add("termoPesquisaNota");

		sql.append("SELECT coment.nota AS termoPesquisaNota FROM " + getTableName() + " baseConhec ");
		sql.append("JOIN comentarios coment ON coment.idbaseconhecimento = baseConhec.idbaseconhecimento WHERE nota >= ? AND coment.idbaseconhecimento = ? ");

		parametros.add(baseconhecimento.getTermoPesquisaNota());
		parametros.add(baseconhecimento.getIdBaseConhecimento());

		return this.engine.listConvertion(this.getBean(), execSQL(sql.toString(), parametros.toArray()), fields);
	}

	public Collection<BaseConhecimentoDTO> listaBaseConhecimento(BaseConhecimentoDTO baseConhecimento) throws PersistenceException {

		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listaRetornor = new ArrayList();
		List list = new ArrayList();

		sql.append("select distinct baseconhecimento.idbaseconhecimento,baseconhecimento.titulo,baseconhecimento.versao,baseconhecimento.datainicio, ");

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
			sql.append(" cast(baseconhecimento.conteudo as varchar(4000)) conteudo, ");
		} else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append(" cast(baseconhecimento.conteudo as varchar2(4000)) conteudo, ");
		} else {
			sql.append(" baseconhecimento.conteudo, ");
		}

		sql.append("baseconhecimento.nome,baseconhecimento.dataexpiracao,baseconhecimento.status,baseconhecimento.datapublicacao, ");

		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.SQLSERVER)) {
			sql.append(" cast(baseconhecimento.conteudosemformatacao as varchar(4000)) conteudosemformatacao ");
		} else if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append(" cast(baseconhecimento.conteudosemformatacao as varchar2(4000)) conteudosemformatacao ");
		} else {
			sql.append(" baseconhecimento.conteudosemformatacao ");
		}

		if (baseConhecimento.getUltimoAcesso() != null && baseConhecimento.getUltimoAcesso().equals("S")) {
			sql.append(",ultimoacesso.datahoraacesso");
		}
		sql.append("  from (select baseconhecimento.idbaseconhecimento,baseconhecimento.titulo,baseconhecimento.versao,baseconhecimento.datainicio,baseconhecimento.conteudo, pasta.nome, pasta.idpasta, ");
		sql.append(" baseconhecimento.dataexpiracao,baseconhecimento.status,baseconhecimento.datapublicacao,baseconhecimento.conteudosemformatacao, baseconhecimento.datafim ");
		sql.append("from baseconhecimento,pasta where  pasta.idpasta = baseconhecimento.idpasta) baseconhecimento ");
		if (baseConhecimento.getUltimoAcesso() != null && baseConhecimento.getUltimoAcesso().equals("S")) {
			sql.append("inner join (select idbaseconhecimento, max(datahoraacesso) as datahoraacesso from contadoracesso ");
			sql.append("group by idbaseconhecimento)  ultimoacesso on baseconhecimento.idbaseconhecimento = ultimoacesso.idbaseconhecimento ");
		}
		sql.append(" left join (select distinct idbaseconhecimento from comentarios) comentarios on baseconhecimento.idbaseconhecimento = comentarios.idbaseconhecimento ");
		sql.append(" left join contadoracesso contadoracesso ON contadoracesso.idbaseconhecimento = baseconhecimento.idbaseconhecimento ");
		sql.append("where  baseconhecimento.datafim is null  ");
		if (baseConhecimento.getIdPasta() != null) {
			sql.append(" and baseconhecimento.idpasta = ? ");
			parametro.add(baseConhecimento.getIdPasta());
		}
		if (baseConhecimento.getIdBaseConhecimento() != null) {
			sql.append(" and baseconhecimento.idbaseconhecimento = ? ");
			parametro.add(baseConhecimento.getIdBaseConhecimento());
		}
		if (baseConhecimento.getTermoPesquisaNota() != null && baseConhecimento.getTermoPesquisaNota().equalsIgnoreCase("S")) {
			sql.append("  and comentarios.idbaseconhecimento is null ");
		}
		if (baseConhecimento.getStatus() != null && !baseConhecimento.getStatus().equalsIgnoreCase("")) {
			sql.append(" and UPPER(baseconhecimento.status) = UPPER(?)");
			parametro.add(baseConhecimento.getStatus());
		}
		if (baseConhecimento.getDataInicio() != null && baseConhecimento.getDataFim() != null) {
			sql.append("  and datainicio BETWEEN ? and ? ");
			parametro.add(baseConhecimento.getDataInicio());
			parametro.add(baseConhecimento.getDataFim());
		}
		if (baseConhecimento.getDataInicioPublicacao() != null && baseConhecimento.getDataFimPublicacao() != null) {
			sql.append("  and baseconhecimento.datapublicacao BETWEEN ? and ? ");
			parametro.add(baseConhecimento.getDataInicioPublicacao());
			parametro.add(baseConhecimento.getDataFimPublicacao());
		}
		if (baseConhecimento.getDataInicioExpiracao() != null && baseConhecimento.getDataFimExpiracao() != null) {
			sql.append("  and baseconhecimento.dataexpiracao BETWEEN ? and ? ");
			parametro.add(baseConhecimento.getDataInicioExpiracao());
			parametro.add(baseConhecimento.getDataFimExpiracao());
		}
		if (baseConhecimento.getDataInicioAcesso() != null && baseConhecimento.getDataFimAcesso() != null) {
			sql.append("  and contadoracesso.datahoraacesso BETWEEN ? and ? ");
			parametro.add(baseConhecimento.getDataInicioAcesso());
			parametro.add(baseConhecimento.getDataFimAcesso());
		}

		if (baseConhecimento.getUltimoAcesso() != null && baseConhecimento.getUltimoAcesso().equals("S")) {
			sql.append("order by   ultimoacesso.datahoraacesso desc");
		}

		list = this.execSQL(sql.toString(), parametro.toArray());
		listaRetornor.add("idBaseConhecimento");
		listaRetornor.add("titulo");
		listaRetornor.add("versao");
		listaRetornor.add("dataInicio");
		listaRetornor.add("conteudo");
		listaRetornor.add("nomePasta");
		listaRetornor.add("dataExpiracao");
		listaRetornor.add("status");
		listaRetornor.add("dataPublicacao");
		listaRetornor.add("conteudoSemFormatacao");
		if (baseConhecimento.getUltimoAcesso() != null && baseConhecimento.getUltimoAcesso().equals("S")) {
			listaRetornor.add("dataHoraAcesso");
		}

		if (list != null && !list.isEmpty()) {
			Collection<BaseConhecimentoDTO> listaBaseConhecimento = this.listConvertion(BaseConhecimentoDTO.class, list, listaRetornor);
			return listaBaseConhecimento;
		}

		return null;

	}

	/**
	 * Verifica se base conhecimento informada possui nova versão.
	 * 
	 * @param baseConhecimento
	 * @return
	 * @throws Exception
	 */
	public Collection<BaseConhecimentoDTO> listaBaseConhecimentoUltimasVersoes(BaseConhecimentoDTO baseConhecimento) throws PersistenceException {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		List listaRetornor = new ArrayList();
		List list = new ArrayList();

		sql.append("select  baseconhecimento.conteudosemformatacao,baseconhecimento.idbaseconhecimento,baseconhecimento.titulo,baseconhecimento.versao,baseconhecimento.datainicio,baseconhecimento.conteudo,");
		sql.append("pasta.nome,baseconhecimento.dataexpiracao,CASE WHEN baseconhecimento.status ='S' THEN 'Publicado' WHEN baseconhecimento.status ='N' THEN 'Não Publicado' ELSE baseconhecimento.status END as status, baseconhecimento.idusuarioautor, baseconhecimento.idusuarioaprovador,baseconhecimento.datapublicacao ");
		if (baseConhecimento.getUltimoAcesso() != null && baseConhecimento.getUltimoAcesso().equals("S")) {
			sql.append(",contadoracesso.datahoraacesso ");
		}
		sql.append(" from baseconhecimento ");
		sql.append("inner join ");

		sql.append("(select max(idbaseconhecimento) as idbaseconhecimento, max(versao) as ver, (case when versao = '1.0' ");

		if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.ORACLE)) {
			sql.append("then titulo else substr(titulo,1, length(titulo) - 7) end) as cas from baseconhecimento where datafim is null and status = 'S' ");
			sql.append("group by case when versao = '1.0' then titulo else substr(titulo,1, length(titulo) - 7) end) as aux ");
		} else if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase(SQLConfig.SQLSERVER)) {
			sql.append("then titulo else substring(titulo,1, len(titulo) - 7) end) as cas from baseconhecimento where datafim is null and status = 'S' ");
			sql.append("group by case when versao = '1.0' then titulo else substring(titulo,1, len(titulo) - 7) end) as aux ");
		} else {
			sql.append("then titulo else substring(titulo,1, length(titulo) - 7) end) as cas from baseconhecimento where datafim is null and status = 'S' ");
			sql.append("group by case when versao = '1.0' then titulo else substring(titulo,1, length(titulo) - 7) end) as aux ");
		}
		sql.append("on baseconhecimento.idbaseconhecimento = aux.idbaseconhecimento ");
		if (baseConhecimento.getUltimoAcesso() != null && baseConhecimento.getUltimoAcesso().equals("S")) {
			sql.append("inner join (select idbaseconhecimento, max(datahoraacesso) as datahoraacesso from contadoracesso ");
			sql.append("group by idbaseconhecimento)  contadoracesso on baseconhecimento.idbaseconhecimento = contadoracesso.idbaseconhecimento ");
		}

		sql.append("inner join pasta on pasta.idpasta = baseconhecimento.idpasta ");
		sql.append("left join comentarios on comentarios.idbaseconhecimento = baseconhecimento.idbaseconhecimento ");
		sql.append("where  baseconhecimento.datafim is null  ");

		if (baseConhecimento.getIdPasta() != null) {
			sql.append(" and baseconhecimento.idpasta = ? ");
			parametro.add(baseConhecimento.getIdPasta());
		}
		if (baseConhecimento.getIdBaseConhecimento() != null) {
			sql.append(" and baseconhecimento.idbaseconhecimento = ? ");
			parametro.add(baseConhecimento.getIdBaseConhecimento());
		}
		if (baseConhecimento.getTermoPesquisaNota() != null && baseConhecimento.getTermoPesquisaNota().equalsIgnoreCase("S")) {
			sql.append("  and idcomentario is null ");
		}
		if (baseConhecimento.getStatus() != null && !baseConhecimento.getStatus().equalsIgnoreCase("")) {
			sql.append(" and baseconhecimento.status = ?");
			parametro.add(baseConhecimento.getStatus());
		}

		if (baseConhecimento.getDataExpiracao() != null) {
			sql.append(" and baseconhecimento.dataexpiracao = ?");
			parametro.add(baseConhecimento.getDataExpiracao());
		}

		if (baseConhecimento.getDataInicio() != null && baseConhecimento.getDataFim() != null) {
			sql.append("  and baseconhecimento.datainicio BETWEEN ? and ? ");
			parametro.add(baseConhecimento.getDataInicio());
			parametro.add(baseConhecimento.getDataFim());
		}

		if (baseConhecimento.getDataInicioPublicacao() != null && baseConhecimento.getDataFimPublicacao() != null) {
			sql.append("  and baseconhecimento.datapublicacao BETWEEN ? and ? ");
			parametro.add(baseConhecimento.getDataInicioPublicacao());
			parametro.add(baseConhecimento.getDataFimPublicacao());
		}

		if (baseConhecimento.getDataInicioExpiracao() != null && baseConhecimento.getDataFimExpiracao() != null) {
			sql.append("  and baseconhecimento.dataexpiracao BETWEEN ? and ? ");
			parametro.add(baseConhecimento.getDataInicioExpiracao());
			parametro.add(baseConhecimento.getDataFimExpiracao());
		}

		if (baseConhecimento.getUltimoAcesso() != null && baseConhecimento.getUltimoAcesso().equals("S")) {
			sql.append("order by contadoracesso.datahoraacesso desc");
		}

		list = this.execSQL(sql.toString(), parametro.toArray());
		listaRetornor.add("conteudoSemFormatacao");
		listaRetornor.add("idBaseConhecimento");
		listaRetornor.add("titulo");
		listaRetornor.add("versao");
		listaRetornor.add("dataInicio");
		listaRetornor.add("conteudo");
		listaRetornor.add("nomePasta");
		listaRetornor.add("dataExpiracao");
		listaRetornor.add("status");
		listaRetornor.add("idUsuarioAutor");
		listaRetornor.add("idUsuarioAprovador");
		listaRetornor.add("dataPublicacao");
		if (baseConhecimento.getUltimoAcesso() != null && baseConhecimento.getUltimoAcesso().equals("S")) {
			listaRetornor.add("dataHoraAcesso");
		}

		if (list != null && !list.isEmpty()) {
			Collection<BaseConhecimentoDTO> listaBaseConhecimentoUltimaVersao = this.listConvertion(BaseConhecimentoDTO.class, list, listaRetornor);
			return listaBaseConhecimentoUltimaVersao;
		}
		return null;
	}

	/**
	 * Lista Base de Conhecimento do Tipo FAQ da pasta informada.
	 * 
	 * @param pasta
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 * @author Thays
	 */
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoFAQByPasta(PastaDTO pasta) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		Date dataAtual = UtilDatas.getDataAtual();

		sql.append("SELECT baseConhecimento.idBaseConhecimento,baseConhecimento.idpasta,baseConhecimento.titulo, baseConhecimento.conteudo FROM PASTA pasta ");
		sql.append("INNER JOIN BASECONHECIMENTO baseConhecimento ON pasta.idpasta = baseConhecimento.idpasta  ");
		sql.append("where pasta.idpasta = ? AND baseconhecimento.dataFim is null and baseconhecimento.status = ? AND baseconhecimento.faq = ?  AND (baseconhecimento.arquivado IS NULL OR baseconhecimento.arquivado = ?)  ");
		sql.append(" and dataexpiracao >= ? ");

		parametro.add(pasta.getId());
		parametro.add("S");
		parametro.add("S");
		parametro.add("N");
		parametro.add(dataAtual);

		List lista = new ArrayList();

		lista = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("idPasta");
		listRetorno.add("titulo");
		listRetorno.add("conteudo");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);

		return result;
	}

	/**
	 * Lista Base de Conhecimento do Tipo Erro Conhecido da pasta informada.
	 * 
	 * @param pasta
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 * @author Thays
	 */
	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoErroConhecidoByPasta(PastaDTO pasta) throws PersistenceException {
		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		Date dataAtual = UtilDatas.getDataAtual();

		sql.append("SELECT baseConhecimento.idBaseConhecimento,baseConhecimento.idpasta,baseConhecimento.titulo, baseConhecimento.conteudo FROM PASTA pasta ");
		sql.append("INNER JOIN BASECONHECIMENTO baseConhecimento ON pasta.idpasta = baseConhecimento.idpasta  ");
		sql.append("where pasta.idpasta = ? AND baseconhecimento.dataFim is null and baseconhecimento.status = ? AND baseconhecimento.erroconhecido = ?  AND (baseconhecimento.arquivado IS NULL OR baseconhecimento.arquivado = ?)  ");
		sql.append(" and dataexpiracao >= ? ");

		parametro.add(pasta.getId());
		parametro.add("S");
		parametro.add("S");
		parametro.add("N");
		parametro.add(dataAtual);

		List lista = new ArrayList();

		lista = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("idPasta");
		listRetorno.add("titulo");
		listRetorno.add("conteudo");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);

		return result;
	}

	/**
	 * Retorna uma base conhecimento de acordo com os parametros passado.
	 * 
	 * @param baseConhecimento
	 * @return BaseConhecimentoDTO
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public BaseConhecimentoDTO getBaseConhecimento(BaseConhecimentoDTO baseConhecimento) throws PersistenceException {

		BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();

		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select idbaseconhecimento,idpasta,titulo from baseconhecimento where datafim is null AND baseconhecimento.status = ?  AND (baseconhecimento.arquivado IS NULL OR baseconhecimento.arquivado = ?) ");

		parametro.add("S");

		parametro.add("N");

		if (baseConhecimento.getIdBaseConhecimento() != null) {
			sql.append("AND  baseconhecimento.idbaseconhecimento = ? ");
			parametro.add(baseConhecimento.getIdBaseConhecimento());
		}

		if (baseConhecimento.getIdUsuarioAutorPesquisa() != null) {
			sql.append("AND  baseconhecimento.idusuarioautor = ? ");
			parametro.add(baseConhecimento.getIdUsuarioAutorPesquisa());
		}

		if (baseConhecimento.getIdUsuarioAprovadorPesquisa() != null) {
			sql.append("AND  baseconhecimento.idusuarioaprovador = ? ");
			parametro.add(baseConhecimento.getIdUsuarioAprovadorPesquisa());
		}

		if (baseConhecimento.getDataInicioPesquisa() != null) {
			sql.append("AND  baseconhecimento.datainicio = ? ");
			parametro.add(baseConhecimento.getDataInicioPesquisa());
		}

		if (baseConhecimento.getDataPublicacaoPesquisa() != null) {
			sql.append("AND  baseconhecimento.datapublicacao = ? ");
			parametro.add(baseConhecimento.getDataPublicacaoPesquisa());
		}

		List lista = new ArrayList();

		lista = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("idPasta");
		listRetorno.add("titulo");

		if (lista != null && !lista.isEmpty()) {
			List<BaseConhecimentoDTO> listaBaseConhecimento = this.listConvertion(BaseConhecimentoDTO.class, lista, listRetorno);
			return listaBaseConhecimento.get(0);
		}

		return baseConhecimentoDto;

	}

	/**
	 * Retorna uma lista base conhecimento de acordo com os parametros passado.
	 * 
	 * @param baseConhecimento
	 * @return BaseConhecimentoDTO
	 * @throws Exception
	 * @author Thays.araujo
	 */
	public Collection<BaseConhecimentoDTO> listPesquisaBaseConhecimento(BaseConhecimentoDTO baseConhecimento) throws PersistenceException {

		List parametro = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select idbaseconhecimento,idpasta,titulo from baseconhecimento where datafim is null AND baseconhecimento.status = ? AND (baseconhecimento.arquivado IS NULL OR baseconhecimento.arquivado = ?) ");

		parametro.add("S");

		parametro.add("N");

		if (baseConhecimento.getIdUsuarioAutorPesquisa() != null) {
			sql.append("AND  baseconhecimento.idusuarioautor = ? ");
			parametro.add(baseConhecimento.getIdUsuarioAutorPesquisa());
		}

		if (baseConhecimento.getIdUsuarioAprovadorPesquisa() != null) {
			sql.append("AND  baseconhecimento.idusuarioaprovador = ? ");
			parametro.add(baseConhecimento.getIdUsuarioAprovadorPesquisa());
		}

		if (baseConhecimento.getDataInicioPesquisa() != null) {
			sql.append("AND  baseconhecimento.datainicio = ? ");
			parametro.add(baseConhecimento.getDataInicioPesquisa());
		}

		if (baseConhecimento.getDataPublicacaoPesquisa() != null) {
			sql.append("AND  baseconhecimento.datapublicacao = ? ");
			parametro.add(baseConhecimento.getDataPublicacaoPesquisa());
		}

		List lista = new ArrayList();

		lista = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("idPasta");
		listRetorno.add("titulo");

		if (lista != null && !lista.isEmpty()) {
			List<BaseConhecimentoDTO> listaBaseConhecimento = this.listConvertion(BaseConhecimentoDTO.class, lista, listRetorno);
			return listaBaseConhecimento;
		}
		return null;
	}

	@Override
	public void updateNotNull(IDto obj) throws PersistenceException {
		super.updateNotNull(obj);
	}

	/**
	 * Arquiva Conhecimento da Base de Conhecimentos.
	 * 
	 * @param baseConhecimentoDto
	 * @author Vadoilo Damasceno
	 * @throws Exception
	 */
	public void arquivarConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws PersistenceException {
		this.updateNotNull(baseConhecimentoDto);
	}

	/**
	 * Restaura Conhecimento Arquivado da Base de Conhecimentos.
	 * 
	 * @param baseConhecimentoDto
	 * @author Vadoilo Damasceno
	 * @throws Exception
	 */
	public void restaurarConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws PersistenceException {
		this.updateNotNull(baseConhecimentoDto);
	}

	/**
	 * Retorna lista de versões anteriores da Base de Conhecimento informada.
	 * 
	 * @param baseConhecimento
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 */
	public Collection<BaseConhecimentoDTO> obterHistoricoDeVersoes(BaseConhecimentoDTO baseConhecimento) throws PersistenceException {
		List parametro = new ArrayList();
		List lista = new ArrayList();

		StringBuilder sql = new StringBuilder();

		sql.append("select idbaseConhecimento,titulo,versao from baseconhecimento ");
		if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("ORACLE")) {
			sql.append(" where (case when versao = '1.0' then titulo else substr(titulo,1, length(titulo) - 7) end ) ");
			sql.append(" in ");
			sql.append(" (select case when versao = '1.0' then titulo else substr(titulo,1, length(titulo) - 7) end from baseconhecimento where datafim is null and status = ? ");

			// geber.costa
			// verifica se a base do banco é sqlserver, pois no caso dele ao inves de usar o length usa-se len
		} else if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("SQLSERVER")) {
			{
				sql.append(" where status = 'S' and (case when versao = '1.0' then titulo else substring(titulo,1, len(titulo) - 7) end ) ");
				sql.append(" in ");
				sql.append(" (select case when versao = '1.0' then titulo else substring(titulo,1, len(titulo) - 7) end from baseconhecimento where datafim is null and status = ? ");
			}
		}

		else {
			sql.append(" where status = 'S' and (case when versao = '1.0' then titulo else substring(titulo,1, length(titulo) - 7) end ) ");
			sql.append(" in ");
			sql.append(" (select case when versao = '1.0' then titulo else substring(titulo,1, length(titulo) - 7) end from baseconhecimento where datafim is null and status = ? ");
		}

		sql.append(" and titulo = ?) and idbaseconhecimento <> ?");

		parametro.add("S");
		parametro.add(baseConhecimento.getTitulo());
		parametro.add(baseConhecimento.getIdBaseConhecimento());

		lista = this.execSQL(sql.toString(), parametro.toArray());

		List listRetorno = new ArrayList();

		listRetorno.add("idBaseConhecimento");
		listRetorno.add("titulo");
		listRetorno.add("versao");

		if (lista != null && !lista.isEmpty()) {
			List<BaseConhecimentoDTO> listBaseConhecimentoVersoesAnteriores = this.listConvertion(BaseConhecimentoDTO.class, lista, listRetorno);
			return listBaseConhecimentoVersoesAnteriores;
		}

		return null;
	}

	public Collection<BaseConhecimentoDTO> listaBaseConhecimentoTotal(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add(new Order("idBaseConhecimento"));
		return super.list(ordenacao);
	}

	public Collection<BaseConhecimentoDTO> consultaConhecimentoPorAutor(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select u.nome, count(b.titulo) ").append("from usuario u ").append("inner join baseconhecimento b on u.idusuario = b.idusuarioautor ")
				.append("where b.datainicio between ? and ? and b.datafim is null and b.arquivado = ? ").append("group by u.nome ").append("order by u.nome ");

		parametro.add(baseConhecimentoDTO.getDataInicio());
		parametro.add(baseConhecimentoDTO.getDataFim());
		parametro.add("N");

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("nomeUsuario");
		listRetornor.add("qtdConhecimentoPorUsuario");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}
	}

	public Collection<BaseConhecimentoDTO> consultaConhecimentoPorAprovador(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select u.nome, count(b.titulo) ").append("from usuario u ").append("inner join baseconhecimento b on u.idusuario = b.idusuarioaprovador ")
				.append("where b.datainicio between ? and ? and b.datafim is null and b.arquivado = ? ").append("group by u.nome ").append("order by u.nome ");

		parametro.add(baseConhecimentoDTO.getDataInicio());
		parametro.add(baseConhecimentoDTO.getDataFim());
		parametro.add("N");

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("nomeAprovador");
		listRetornor.add("qtdConhecimentoPorAprovador");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}
	}

	public Collection<BaseConhecimentoDTO> consultaConhecimentosPublicadosPorOrigem(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select origem, count(*) ").append("from " + this.getTableName() + " ").append("where datapublicacao between ? and ? and datafim is null and arquivado = ? ")
				.append("group by origem ").append("order by origem ");

		parametro.add(baseConhecimentoDTO.getDataInicio());
		parametro.add(baseConhecimentoDTO.getDataFim());
		parametro.add("N");

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("origem");
		listRetornor.add("qtdConhecimentoPublicadoPorOrigem");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}

	}

	public Collection<BaseConhecimentoDTO> consultaConhecimentosNaoPublicadosPorOrigem(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select origem, count(*) ").append("from " + this.getTableName() + " ").append("where datainicio between ? and ? and datafim is null and arquivado = ? and datapublicacao is null ")
				.append("group by origem ").append("order by origem ");

		parametro.add(baseConhecimentoDTO.getDataInicio());
		parametro.add(baseConhecimentoDTO.getDataFim());
		parametro.add("N");

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("origem");
		listRetornor.add("qtdConhecimentoNaoPublicadoPorOrigem");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}

	}

	public Collection<BaseConhecimentoDTO> consultaConhecimentoQuantitativoEmLista(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {
		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select b.idbaseconhecimento, b.titulo ").append("from " + this.getTableName() + " b ").append("where b.datainicio between ? and ? and b.datafim is null and b.arquivado = ? ")
				.append("order by b.titulo ");

		parametro.add(baseConhecimentoDTO.getDataInicio());
		parametro.add(baseConhecimentoDTO.getDataFim());
		parametro.add("N");

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("idBaseConhecimento");
		listRetornor.add("titulo");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}

	}

	public Collection<BaseConhecimentoDTO> consultaIncidenteLista(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {

		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select b.idbaseconhecimento, b.titulo, ss.idsolicitacaoservico ").append("from " + this.getTableName() + " b ").append("inner join conhecimentosolicitacaoservico css ")
				.append("on css.idbaseconhecimento = b.idbaseconhecimento ").append("INNER JOIN solicitacaoservico ss ").append("ON ss.idsolicitacaoservico = css.idsolicitacaoservico ")
				.append("INNER JOIN tipodemandaservico tds ").append("ON tds.idtipodemandaservico = ss.idtipodemandaservico ").append("AND tds.classificacao = 'I' ")
				.append("where b.idbaseconhecimento = ? ").append("order by b.idbaseconhecimento ");

		parametro.add(baseConhecimentoDTO.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("idBaseConhecimento");
		listRetornor.add("titulo");
		listRetornor.add("idSolicitacaoServicoIncidente");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}

	}

	public Collection<BaseConhecimentoDTO> consultaRequisicaoLista(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {

		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select b.idbaseconhecimento, b.titulo, ss.idsolicitacaoservico ").append("from " + this.getTableName() + " b ").append("inner join conhecimentosolicitacaoservico css ")
				.append("on css.idbaseconhecimento = b.idbaseconhecimento ").append("INNER JOIN solicitacaoservico ss ").append("ON ss.idsolicitacaoservico = css.idsolicitacaoservico ")
				.append("INNER JOIN tipodemandaservico tds ").append("ON tds.idtipodemandaservico = ss.idtipodemandaservico ").append("AND tds.classificacao = 'R' ")
				.append("where b.idbaseconhecimento = ? ").append("order by b.idbaseconhecimento; ");

		parametro.add(baseConhecimentoDTO.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("idBaseConhecimento");
		listRetornor.add("titulo");
		listRetornor.add("idSolicitacaoServicoIncidente");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}

	}

	public Collection<BaseConhecimentoDTO> consultaProblemaLista(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {

		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select b.idbaseconhecimento, b.titulo, p.idproblema ").append("from " + this.getTableName() + " b ").append("inner join conhecimentoproblema cp ")
				.append("on cp.idbaseconhecimento = b.idbaseconhecimento ").append("inner join problema p ").append("on p.idproblema = cp.idproblema ").append("where b.idbaseconhecimento = ? ")
				.append("order by b.idbaseconhecimento; ");

		parametro.add(baseConhecimentoDTO.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("idBaseConhecimento");
		listRetornor.add("titulo");
		listRetornor.add("idProblema");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}

	}

	public Collection<BaseConhecimentoDTO> consultaMudancaLista(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {

		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select b.idbaseconhecimento, b.titulo, rm.idrequisicaomudanca ").append("from " + this.getTableName() + " b ").append("inner join conhecimentomudanca cm ")
				.append("on cm.idbaseconhecimento = b.idbaseconhecimento ").append("inner join requisicaomudanca rm ").append("on rm.idrequisicaomudanca = cm.idbaseconhecimento ")
				.append("where b.idbaseconhecimento = ? ").append("order by b.idbaseconhecimento; ");

		parametro.add(baseConhecimentoDTO.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("idBaseConhecimento");
		listRetornor.add("titulo");
		listRetornor.add("idRequisicaoMudanca");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}

	}

	public Collection<BaseConhecimentoDTO> consultaItemConfiguracaoLista(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {

		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select b.idbaseconhecimento, b.titulo, ic.identificacao ").append("from " + this.getTableName() + " b ").append("inner join conhecimentoic cic ")
				.append("on cic.idbaseconhecimento = b.idbaseconhecimento ").append("inner join itemconfiguracao ic ").append("on ic.iditemconfiguracao = cic.iditemconfiguracao ")
				.append("where b.idbaseconhecimento = ? ").append("order by b.idbaseconhecimento; ");

		parametro.add(baseConhecimentoDTO.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("idBaseConhecimento");
		listRetornor.add("titulo");
		listRetornor.add("identificacao");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}

	}

	public Collection<BaseConhecimentoDTO> consultaServicoLista(BaseConhecimentoDTO baseConhecimentoDTO) throws PersistenceException {

		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("select b.idbaseconhecimento, b.titulo, ").append("case ").append("when s.idbaseconhecimento is null ").append("then 'n' ").append("else ").append("'s' ").append("end ")
				.append("from " + this.getTableName() + " b ").append("inner join servico s ").append("on b.idbaseconhecimento = s.idbaseconhecimento ").append("where b.idbaseconhecimento = ? ")
				.append("order by b.idbaseconhecimento; ");

		parametro.add(baseConhecimentoDTO.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("idBaseConhecimento");
		listRetornor.add("titulo");
		listRetornor.add("vinculaConhecimentoServico");

		if (list != null) {
			return this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
		} else {
			return new ArrayList();
		}

	}

	public Collection<BaseConhecimentoDTO> listarBaseConhecimentoFAQ() throws PersistenceException {

		List fields = new ArrayList();
		List list = new ArrayList();
		String sql = " select idBaseConhecimento, titulo, conteudo, idpasta from baseconhecimento where faq = 'S' and datafim is null and status = 'S' and (arquivado is null or arquivado = 'N')  ";

		list = this.execSQL(sql, null);
		fields.add("idBaseConhecimento");
		fields.add("titulo");
		fields.add("conteudo");
		fields.add("idPasta");

		if (list != null && !list.isEmpty()) {
			return (Collection<BaseConhecimentoDTO>) this.listConvertion(getBean(), list, fields);
		} else {
			return null;
		}

	}

	/**
	 * Retorna lista todas as bases de conhecimento não excluídas, publicadas e não arquivadas!
	 * 
	 * @author euler.ramos
	 * @return Collection<BaseConhecimentoDTO>
	 * @throws Exception
	 */
	public Collection<BaseConhecimentoDTO> listarBasesConhecimentoPublicadas() throws PersistenceException {
		String sql = "select idbaseconhecimento,idpasta,datainicio,datafim,titulo,conteudo,status,idbaseconhecimentopai,dataexpiracao,versao,idnotificacao,justificativaobservacao,datapublicacao,fontereferencia,"
				+ "faq,arquivado,idusuarioautor,idusuarioaprovador,idhistoricobaseconhecimento,origem,privacidade,situacao,gerenciamentoDisponibilidade,direitoAutoral,legislacao,conteudosemformatacao,erroconhecido "
				+ "FROM baseconhecimento where (baseconhecimento.dataFim is null) and baseconhecimento.status = 'S' AND (baseconhecimento.arquivado IS NULL OR baseconhecimento.arquivado = 'N') ";
		List lista = new ArrayList();
		lista = this.execSQL(sql, null);

		List listRetorno = new ArrayList();
		listRetorno.add("idBaseConhecimento");
		listRetorno.add("idPasta");
		listRetorno.add("dataInicio");
		listRetorno.add("dataFim");
		listRetorno.add("titulo");
		listRetorno.add("conteudo");
		listRetorno.add("status");
		listRetorno.add("idBaseConhecimentoPai");
		listRetorno.add("dataExpiracao");
		listRetorno.add("versao");
		listRetorno.add("idNotificacao");
		listRetorno.add("justificativaObservacao");
		listRetorno.add("dataPublicacao");
		listRetorno.add("fonteReferencia");
		listRetorno.add("faq");
		listRetorno.add("arquivado");
		listRetorno.add("idUsuarioAutor");
		listRetorno.add("idUsuarioAprovador");
		listRetorno.add("idHistoricoBaseConhecimento");
		listRetorno.add("origem");
		listRetorno.add("privacidade");
		listRetorno.add("situacao");
		listRetorno.add("gerenciamentoDisponibilidade");
		listRetorno.add("direitoAutoral");
		listRetorno.add("legislacao");
		listRetorno.add("conteudoSemFormatacao");
		listRetorno.add("erroConhecido");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return (result == null ? new ArrayList<AnexoBaseConhecimentoDTO>() : result);
	}

	/**
	 * Encontra BaseConhecimento pelo ID
	 * 
	 * @author thiago.oliveira
	 * @throws Exception
	 */
	public List<BaseConhecimentoDTO> findByIdBaseConhecimento(Integer id) throws PersistenceException {
		List resp = new ArrayList();

		Collection fields = getFields();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}

		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE idbaseconhecimento = ? ORDER BY idbaseconhecimento";
		parametro.add(id);
		resp = this.execSQL(sql, parametro.toArray());

		List result = this.engine.listConvertion(getBean(), resp, listRetorno);
		return (result == null ? new ArrayList<BaseConhecimentoDTO>() : result);
	}

	/**
	 * Encontra a BaseConhecimento pelo nome
	 * 
	 * @author thiago.oliveira
	 * @throws Exception
	 */
	public List<BaseConhecimentoDTO> findByBaseConhecimento(String titulo) throws PersistenceException {
		List resp = new ArrayList();

		Collection fields = getFields();
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		String campos = "";
		for (Iterator it = fields.iterator(); it.hasNext();) {
			Field field = (Field) it.next();
			if (!campos.trim().equalsIgnoreCase("")) {
				campos = campos + ",";
			}
			campos = campos + field.getFieldDB();
			listRetorno.add(field.getFieldClass());
		}

		String sql = "SELECT " + campos + " FROM " + getTableName() + " WHERE titulo = ? ORDER BY titulo";
		parametro.add(titulo);
		resp = this.execSQL(sql, parametro.toArray());

		List result = this.engine.listConvertion(getBean(), resp, listRetorno);
		return (result == null ? new ArrayList<BaseConhecimentoDTO>() : result);
	}

	/**
	 * Retorna a lista de base de conhecimento do portal paginada
	 * 
	 * @author thyen.chang
	 * @since 06/02/2015 - OPERAÇÃO USAIN BOLT
	 * @param pageable
	 *            - Objeto que contém qual página e número de elementos a serem pesquisados
	 * @param isTotalizacao
	 *            - Se a busca é para buscar o número total de elementos da consulta
	 * @param titulo - Título da base de conhecimento a ser pesquisado
	 * @return
	 * @throws PersistenceException
	 */
	public Page<BaseConhecimentoDTO> listarBaseConhecimentoPortal(final Pageable pageable, final boolean isTotalizacao, String titulo) throws PersistenceException {

		Page<BaseConhecimentoDTO> taskPage;

		
		StringBuilder selectQueryPiece = new StringBuilder("SELECT idbaseconhecimento,idpasta,datainicio,datafim,titulo,conteudo,status,idbaseconhecimentopai,dataexpiracao,versao,"
				+ "idnotificacao,justificativaobservacao,datapublicacao,fontereferencia," + "faq,arquivado,idusuarioautor,idusuarioaprovador,idhistoricobaseconhecimento,"
				+ "origem,privacidade,situacao,gerenciamentoDisponibilidade,direitoAutoral,legislacao,conteudosemformatacao,erroconhecido ");

		StringBuilder ordenarPor = new StringBuilder(" ORDER BY idbaseconhecimento ");

		StringBuilder fromWhereQueryPiece = new StringBuilder("FROM baseconhecimento " + "WHERE (baseconhecimento.dataFim is null) " + "AND baseconhecimento.status = 'S' "
				+ "AND (baseconhecimento.arquivado IS NULL OR baseconhecimento.arquivado = 'N') ");

		if(titulo != null && !titulo.isEmpty()){
			fromWhereQueryPiece.append(" AND baseconhecimento.titulo LIKE '%" + titulo + "%' ");
		}
		
		List lista = new ArrayList();

		String sql = "";
		if (!isTotalizacao) {
			if (MAIN_SGBD.equals(DataBase.MSSQLSERVER)) {
				sql = PagingQueryUtil.constructsSQLServerPagingPiece(pageable, selectQueryPiece.toString(), ordenarPor.toString(), fromWhereQueryPiece.toString());
			} else {
				selectQueryPiece.append(fromWhereQueryPiece);
				selectQueryPiece.append(ordenarPor);
				sql = PagingQueryUtil.concatPagingPieceOnQuery(pageable, selectQueryPiece.toString(), MAIN_SGBD);
			}

			lista = this.execSQL(sql, null);

			List listRetorno = new ArrayList();
			listRetorno.add("idBaseConhecimento");
			listRetorno.add("idPasta");
			listRetorno.add("dataInicio");
			listRetorno.add("dataFim");
			listRetorno.add("titulo");
			listRetorno.add("conteudo");
			listRetorno.add("status");
			listRetorno.add("idBaseConhecimentoPai");
			listRetorno.add("dataExpiracao");
			listRetorno.add("versao");
			listRetorno.add("idNotificacao");
			listRetorno.add("justificativaObservacao");
			listRetorno.add("dataPublicacao");
			listRetorno.add("fonteReferencia");
			listRetorno.add("faq");
			listRetorno.add("arquivado");
			listRetorno.add("idUsuarioAutor");
			listRetorno.add("idUsuarioAprovador");
			listRetorno.add("idHistoricoBaseConhecimento");
			listRetorno.add("origem");
			listRetorno.add("privacidade");
			listRetorno.add("situacao");
			listRetorno.add("gerenciamentoDisponibilidade");
			listRetorno.add("direitoAutoral");
			listRetorno.add("legislacao");
			listRetorno.add("conteudoSemFormatacao");
			listRetorno.add("erroConhecido");

			List<BaseConhecimentoDTO> result = this.engine.listConvertion(getBean(), lista, listRetorno);
			taskPage = new PageImpl<BaseConhecimentoDTO>(result, pageable, 1L);
		} else {
			final StringBuilder sqlCount = this.countQueryPiece(fromWhereQueryPiece);
			final Long totalElements = this.countElements(sqlCount.toString(), null);
			final List<BaseConhecimentoDTO> result = new ArrayList<BaseConhecimentoDTO>();
			taskPage = this.makePage(result, pageable, totalElements);
		}

		return taskPage;
	}

	/**
	 * Retorna a lista de FAQ do portal paginada
	 * 
	 * @author thyen.chang
	 * @since 06/02/2015 - OPERAÇÃO USAIN BOLT
	 * @param pageable
	 *            - Objeto que contém qual página e número de elementos a serem pesquisados
	 * @param isTotalizacao
	 *            - Se a busca é para buscar o número total de elementos da consulta
	 * @param titulo - Título da base de conhecimento a ser pesquisado
	 * @return
	 * @throws PersistenceException
	 */
	public Page<BaseConhecimentoDTO> listarBaseConhecimentoFAQPortal(final Pageable pageable, final boolean isTotalizacao, String titulo) throws PersistenceException {

		Page<BaseConhecimentoDTO> taskPage;

		StringBuilder selectQueryPiece = new StringBuilder("select idBaseConhecimento, titulo, conteudo, idpasta ");

		StringBuilder ordenarPor = new StringBuilder(" ORDER BY idbaseconhecimento ");

		StringBuilder fromWhereQueryPiece = new StringBuilder("from baseconhecimento where faq = 'S' and datafim is null and status = 'S' and (arquivado is null or arquivado = 'N')");

		if(titulo != null && !titulo.isEmpty()){
			fromWhereQueryPiece.append(" AND baseconhecimento.titulo LIKE '%" + titulo +"%' ");
		}
		
		List list = new ArrayList();
		String sql = "";

		if (!isTotalizacao) {
			if (MAIN_SGBD.equals(DataBase.MSSQLSERVER)) {
				sql = PagingQueryUtil.constructsSQLServerPagingPiece(pageable, selectQueryPiece.toString(), ordenarPor.toString(), fromWhereQueryPiece.toString());
			} else {
				selectQueryPiece.append(fromWhereQueryPiece);
				selectQueryPiece.append(ordenarPor);
				sql = PagingQueryUtil.concatPagingPieceOnQuery(pageable, selectQueryPiece.toString(), MAIN_SGBD);
			}
			list = this.execSQL(sql, null);

			List fields = new ArrayList();
			fields.add("idBaseConhecimento");
			fields.add("titulo");
			fields.add("conteudo");
			fields.add("idPasta");
			List<BaseConhecimentoDTO> result = this.engine.listConvertion(getBean(), list, fields);
			taskPage = new PageImpl<BaseConhecimentoDTO>(result, pageable, 1L);
		} else {
			final StringBuilder sqlCount = this.countQueryPiece(fromWhereQueryPiece);
			final Long totalElements = this.countElements(sqlCount.toString(), null);
			final List<BaseConhecimentoDTO> result = new ArrayList<BaseConhecimentoDTO>();
			taskPage = this.makePage(result, pageable, totalElements);
		}

		return taskPage;

	}

}