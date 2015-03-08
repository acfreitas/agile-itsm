package br.com.centralit.citcorpore.negocio;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citcorpore.batch.ThreadMonitoraAtivosConfiguracao;
import br.com.centralit.citcorpore.bean.AuditoriaItemConfigDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.CaracteristicaTipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.HistoricoValorDTO;
import br.com.centralit.citcorpore.bean.ItemCfgSolicitacaoServDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.MonitoramentoAtivosDTO;
import br.com.centralit.citcorpore.bean.NotificacaoListaNegraEncontradosDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.SoftwaresListaNegraDTO;
import br.com.centralit.citcorpore.bean.SoftwaresListaNegraEncontradosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.AuditoriaItemConfigDao;
import br.com.centralit.citcorpore.integracao.CaracteristicaDao;
import br.com.centralit.citcorpore.integracao.CaracteristicaTipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.GrupoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.HistoricoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.HistoricoValorDAO;
import br.com.centralit.citcorpore.integracao.ItemCfgSolicitacaoServDAO;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.MonitoramentoAtivosDAO;
import br.com.centralit.citcorpore.integracao.ProblemaDAO;
import br.com.centralit.citcorpore.integracao.ProblemaItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.SoftwaresListaNegraDao;
import br.com.centralit.citcorpore.integracao.SoftwaresListaNegraEncontradosDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoDao;
import br.com.centralit.citcorpore.integracao.TipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.ValorDao;
import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.metainfo.script.ScriptRhinoJSExecute;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.CaracteristicaIdentificacao;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TagTipoItemConfiguracaoDefault;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.Order;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;

/**
 * @author Maycon.Fernandes
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ItemConfiguracaoServiceEjb extends CrudServiceImpl implements ItemConfiguracaoService {

	private final Lock lock = new ReentrantLock();
	private boolean situacao = false;

	private TransactionControler transactionControler;

	/** DAOs */
	private ItemConfiguracaoDao itemConfiguracaoDao;
	private TipoItemConfiguracaoDAO tipoItemConfiguracaoDao;
	private CaracteristicaTipoItemConfiguracaoDAO caracteristicaTipoItemConfiguracaoDao;
	private CaracteristicaDao caracteristicaDao;
	private ValorDao valorDao;
	private HistoricoItemConfiguracaoDAO historicoItemConfiguracaoDao;
	private HistoricoValorDAO historicoValorDao;
	private AuditoriaItemConfigDao auditoriaItemConfiguracaoDao;
	private SoftwaresListaNegraDao softwaresListaNegraDao;
	private SoftwaresListaNegraEncontradosDao softwaresListaNegraEncontradosDao;
	private ItemCfgSolicitacaoServDAO itemConfiguracaoSolicitacaoServicoDao;
	private ProblemaItemConfiguracaoDAO problemaItemConfiguracaoDao;
	private RequisicaoMudancaItemConfiguracaoDao requisicaoMudancaItemConfiguracaoDao;
	private RequisicaoLiberacaoItemConfiguracaoDao requisicaoLiberacaoItemConfiguracaoDao;

	private TransactionControler getTransactionControler() throws PersistenceException {
		if (transactionControler == null || !transactionControler.isStarted()) {
			transactionControler = new TransactionControlerImpl(Constantes.getValue("DATABASE_ALIAS"));
			transactionControler.start();
		}
		return transactionControler;
	}

	protected ItemConfiguracaoDao getDao() {
		if (itemConfiguracaoDao == null) {
			itemConfiguracaoDao = new ItemConfiguracaoDao();
		}
		return itemConfiguracaoDao;
	}

	private CaracteristicaDao getCaracteristicaDao() throws Exception {
		if (caracteristicaDao == null) {
			caracteristicaDao = new CaracteristicaDao();
			caracteristicaDao.setTransactionControler(getTransactionControler());
		}
		return caracteristicaDao;
	}

	private CaracteristicaTipoItemConfiguracaoDAO getCaracteristicaTipoItemConfiguracaoDao() throws Exception {
		if (caracteristicaTipoItemConfiguracaoDao == null) {
			caracteristicaTipoItemConfiguracaoDao = new CaracteristicaTipoItemConfiguracaoDAO();
			caracteristicaTipoItemConfiguracaoDao.setTransactionControler(getTransactionControler());
		}
		return caracteristicaTipoItemConfiguracaoDao;
	}

	private ItemCfgSolicitacaoServDAO getItemConfiguracaoSolicitacaoServicoDao() throws Exception {
		if (itemConfiguracaoSolicitacaoServicoDao == null) {
			itemConfiguracaoSolicitacaoServicoDao = new ItemCfgSolicitacaoServDAO();
			itemConfiguracaoSolicitacaoServicoDao.setTransactionControler(getTransactionControler());
		}
		return itemConfiguracaoSolicitacaoServicoDao;
	}

	private HistoricoValorDAO getHistoricoValorDao() throws Exception {
		if (historicoValorDao == null) {
			historicoValorDao = new HistoricoValorDAO();
			historicoValorDao.setTransactionControler(getTransactionControler());
		}
		return historicoValorDao;
	}

	private ProblemaItemConfiguracaoDAO getProblemaItemConfiguracaoDao() throws Exception {
		if (problemaItemConfiguracaoDao == null) {
			problemaItemConfiguracaoDao = new ProblemaItemConfiguracaoDAO();
			problemaItemConfiguracaoDao.setTransactionControler(getTransactionControler());
		}
		return problemaItemConfiguracaoDao;
	}

	private RequisicaoMudancaItemConfiguracaoDao getRequisicaoMudancaItemConfiguracaoDao() throws Exception {
		if (requisicaoMudancaItemConfiguracaoDao == null) {
			requisicaoMudancaItemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();
			requisicaoMudancaItemConfiguracaoDao.setTransactionControler(getTransactionControler());
		}
		return requisicaoMudancaItemConfiguracaoDao;
	}

	private RequisicaoLiberacaoItemConfiguracaoDao getRequisicaoLiberacaoItemConfiguracaoDao() throws Exception {
		if (requisicaoLiberacaoItemConfiguracaoDao == null) {
			requisicaoLiberacaoItemConfiguracaoDao = new RequisicaoLiberacaoItemConfiguracaoDao();
			requisicaoLiberacaoItemConfiguracaoDao.setTransactionControler(getTransactionControler());
		}
		return requisicaoLiberacaoItemConfiguracaoDao;
	}

	private HistoricoItemConfiguracaoDAO getHistoricoItemConfiguracaoDao() throws Exception {
		if (historicoItemConfiguracaoDao == null) {
			historicoItemConfiguracaoDao = new HistoricoItemConfiguracaoDAO();
			historicoItemConfiguracaoDao.setTransactionControler(getTransactionControler());
		}
		return historicoItemConfiguracaoDao;
	}

	private AuditoriaItemConfigDao getAuditoriaItemConfiguracaoDao() throws Exception {
		if (auditoriaItemConfiguracaoDao == null) {
			auditoriaItemConfiguracaoDao = new AuditoriaItemConfigDao();
			auditoriaItemConfiguracaoDao.setTransactionControler(getTransactionControler());
		}
		return auditoriaItemConfiguracaoDao;
	}

	private TipoItemConfiguracaoDAO getTipoItemConfiguracaoDAO() throws Exception {
		if (tipoItemConfiguracaoDao == null) {
			tipoItemConfiguracaoDao = new TipoItemConfiguracaoDAO();
			tipoItemConfiguracaoDao.setTransactionControler(getTransactionControler());
		}
		return tipoItemConfiguracaoDao;
	}

	private SoftwaresListaNegraDao getSoftwaresListaNegraDao() throws Exception {
		if (softwaresListaNegraDao == null) {
			softwaresListaNegraDao = new SoftwaresListaNegraDao();
			softwaresListaNegraDao.setTransactionControler(getTransactionControler());
		}
		return softwaresListaNegraDao;
	}

	private SoftwaresListaNegraEncontradosDao getSoftwaresListaNegraEncontradosDao() throws Exception {
		if (softwaresListaNegraEncontradosDao == null) {
			softwaresListaNegraEncontradosDao = new SoftwaresListaNegraEncontradosDao();
			softwaresListaNegraEncontradosDao.setTransactionControler(getTransactionControler());
		}
		return softwaresListaNegraEncontradosDao;
	}

	/**
	 * Retorna DAO de Valor.
	 *
	 * @return valor do atributo valorDao.
	 * @author valdoilo.damasceno
	 */
	private ValorDao getValorDao() {
		if (valorDao == null) {
			valorDao = new ValorDao();
		}
		return valorDao;
	}

	@Override
	public ItemConfiguracaoDTO createItemConfiguracao(ItemConfiguracaoDTO itemConfiguracao, UsuarioDTO user) throws Exception {

		/**
		 * Inicializando os objetos DAO
		 */
		try {
			lock.lock();
			situacao = false;

			/*
			 * Inserido por Emauri - 23/11/2013
			 */
			TransactionControler transactionControler = null;
			if (CITCorporeUtil.JDBC_ALIAS_INVENTORY != null && !CITCorporeUtil.JDBC_ALIAS_INVENTORY.trim().equalsIgnoreCase("")) {
				transactionControler = new TransactionControlerImpl(CITCorporeUtil.JDBC_ALIAS_INVENTORY);
			} else {
				transactionControler = new TransactionControlerImpl(getDao().getAliasDB());
			}
			/*
			 * Fim - Inserido por Emauri - 23/11/2013
			 */

			try {
				this.getCaracteristicaTipoItemConfiguracaoDao().setTransactionControler(transactionControler);
				this.getCaracteristicaDao().setTransactionControler(transactionControler);
				this.getValorDao().setTransactionControler(transactionControler);
				this.getTipoItemConfiguracaoDAO().setTransactionControler(transactionControler);
				this.getHistoricoItemConfiguracaoDao().setTransactionControler(transactionControler);
				this.getHistoricoValorDao().setTransactionControler(transactionControler);
				this.getSoftwaresListaNegraDao().setTransactionControler(transactionControler);
				this.getAuditoriaItemConfiguracaoDao().setTransactionControler(transactionControler);

				transactionControler.start();

				List<TipoItemConfiguracaoDTO> listaTipo = itemConfiguracao.getTipoItemConfiguracao();

				Collection<SoftwaresListaNegraDTO> colsoftwareslistanegra = this.getSoftwaresListaNegraDao().recuperaCollectionSoftwaresListaNegra();

				String softwareslistanegra = this.getSoftwaresListaNegraDao().recuperaStringSoftwaresListaNegra();
				Pattern p = Pattern.compile(softwareslistanegra.toLowerCase());

				NotificacaoListaNegraEncontradosDTO notificacaoListaNegraEncontradosDTO = new NotificacaoListaNegraEncontradosDTO();
				List<NotificacaoListaNegraEncontradosDTO> colListaNegraEncontrados = new ArrayList<NotificacaoListaNegraEncontradosDTO>();

				List<ItemConfiguracaoDTO> lstItemConfiguracaoFilhos = null;
				boolean verificaGravacaoIc = false;

				/**
				 * Se existe item de configuração com a mesma identificação
				 */
				ItemConfiguracaoDTO itemConfiguracaoExistente;
				if ((itemConfiguracaoExistente = this.getDao().findByIdentificacaoItemConfiguracao(itemConfiguracao)) != null) {

					/**
					 * Atualizo os dados do item de configuração
					 */
					HistoricoItemConfiguracaoDTO historicoIc = this.createHistoricoItem(itemConfiguracaoExistente, null);
					historicoIc = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().create(historicoIc);

					itemConfiguracaoExistente.setDataInicio(UtilDatas.getDataAtual());
					itemConfiguracaoExistente.setDtUltimaCaptura(UtilDatas.getDataHoraAtual());
					if (itemConfiguracaoExistente.getNome() == null)
						itemConfiguracaoExistente.setNome(itemConfiguracaoExistente.getIdentificacao());
					this.getDao().updateNotNull(itemConfiguracaoExistente);

					itemConfiguracao = itemConfiguracaoExistente;

					/**
					 * SETA DATAFIM Atualizando o itens filhos relacionados. Atribui DataFim para todos os Itens de Configuração Filhos. Em gravarItemConfiguracaoFilho esses Itens Filhos serão
					 * atualizados, conforme as informações do novo inventário, removendo a DataFim caso ainda exista no novo inventário. Essa verificação é feira através de sua identificação.
					 */
					this.getDao().updateItemConfiguracaoFilho(itemConfiguracao.getIdItemConfiguracao(), UtilDatas.getDataAtual(), UtilDatas.getDataHoraAtual());

					lstItemConfiguracaoFilhos = (List<ItemConfiguracaoDTO>) this.getDao().listItemConfiguracaoFilho(itemConfiguracao);

					if (lstItemConfiguracaoFilhos != null && !lstItemConfiguracaoFilhos.isEmpty()) {
						verificaGravacaoIc = true;
					}

				} else {
					/**
					 * Cria o item de configuração
					 */
					itemConfiguracao.setDataInicio(UtilDatas.getDataAtual());
					itemConfiguracao.setDtUltimaCaptura(UtilDatas.getDataHoraAtual());
					itemConfiguracao.setNome(itemConfiguracao.getIdentificacao());
					itemConfiguracao = (ItemConfiguracaoDTO) getDao().create(itemConfiguracao);

				}

				Integer qtdeThreads = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.MONITORAMENTO_ATIVOS_NUMERO_THREADS, "10"));

				ExecutorService executorService = Executors.newFixedThreadPool(qtdeThreads);

				/** Itera os tipos de item. **/
				for (TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO : listaTipo) {

					List<CaracteristicaDTO> lstCaracteristica = tipoItemConfiguracaoDTO.getCaracteristicas();

					/** Grava TipoItemConfiguracao. **/
					tipoItemConfiguracaoDTO = this.gravarTipoItemConfiguracao(tipoItemConfiguracaoDTO, executorService);

					tipoItemConfiguracaoDTO.setCaracteristicas(lstCaracteristica);

					/** Cria ou Atualiza o ItemConfiguracaoFilho. **/
					ItemConfiguracaoDTO itemConfiguracaoFilhoGravado = this.gravarItemConfiguracaoFilho(itemConfiguracao, tipoItemConfiguracaoDTO);

					if (lstCaracteristica != null && !lstCaracteristica.isEmpty()) {

						for (CaracteristicaDTO caracteristicaBean : lstCaracteristica) {

							ValorDTO valorDTO = caracteristicaBean.getValor();
							valorDTO.setValorStr(StringEscapeUtils.escapeJava(valorDTO.getValorStr()));

							caracteristicaBean = this.gravarCaracteristica(caracteristicaBean, tipoItemConfiguracaoDTO, this.getCaracteristicaDao(), this.getCaracteristicaTipoItemConfiguracaoDao());

							this.gravarValor(valorDTO, caracteristicaBean, itemConfiguracaoFilhoGravado, itemConfiguracaoFilhoGravado.getHistoricoItemConfiguracaoDTO(), verificaGravacaoIc,
									transactionControler, executorService);

							// Verifica se há Software Lista Negra Instalado
							if (tipoItemConfiguracaoDTO.getNome().equalsIgnoreCase("SOFTWARES") && (colsoftwareslistanegra != null && !colsoftwareslistanegra.isEmpty())
									&& (valorDTO.getValorStr() != null && !StringUtils.isBlank(valorDTO.getValorStr()))
									&& (tipoItemConfiguracaoDTO.getNome() != null && !StringUtils.isBlank(tipoItemConfiguracaoDTO.getNome()))) {

								Matcher m = p.matcher(valorDTO.getValorStr().toLowerCase());

								while (m.find()) {
									for (SoftwaresListaNegraDTO s : colsoftwareslistanegra) {
										if (s.getNomeSoftwaresListaNegra().equalsIgnoreCase(m.group())) {
											SoftwaresListaNegraEncontradosDTO softwaresListaNegraEncontradosDTO = new SoftwaresListaNegraEncontradosDTO();
											softwaresListaNegraEncontradosDTO.setIdsoftwareslistanegra(s.getIdSoftwaresListaNegra());
											softwaresListaNegraEncontradosDTO.setIditemconfiguracao(itemConfiguracaoFilhoGravado.getIdItemConfiguracao());
											softwaresListaNegraEncontradosDTO.setSoftwarelistanegraencontrado(valorDTO.getValorStr());
											softwaresListaNegraEncontradosDTO.setData(UtilDatas.getDataHoraAtual());
											getSoftwaresListaNegraEncontradosDao().create(softwaresListaNegraEncontradosDTO);

											notificacaoListaNegraEncontradosDTO.setComputador(itemConfiguracaoFilhoGravado.getIdentificacao());
											notificacaoListaNegraEncontradosDTO.setSoftwarelistanegra(s.getNomeSoftwaresListaNegra());
											notificacaoListaNegraEncontradosDTO.setSoftwareencontrado(valorDTO.getValorStr());
											notificacaoListaNegraEncontradosDTO.setDataHora(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, UtilDatas.getDataAtual(), null));
											colListaNegraEncontrados.add(notificacaoListaNegraEncontradosDTO);
											notificacaoListaNegraEncontradosDTO = new NotificacaoListaNegraEncontradosDTO();

											break;
										}
									}
								}
							}
						}
					}
				}

				executorService.shutdown();

				itemConfiguracao.setDataFim(UtilDatas.getDataAtual());

				List<ItemConfiguracaoDTO> listItemConfiguracaoDto = this.retornaItemConfiguracaoFinalizadoByIdItemConfiguracao(itemConfiguracao, getDao());

				if (listItemConfiguracaoDto != null && listItemConfiguracaoDto.size() > 0) {
					for (ItemConfiguracaoDTO itemConfiguracaoDTO : listItemConfiguracaoDto) {
						getAuditoriaItemConfiguracaoDao().create(this.gravarAuditoriaItemConfig(itemConfiguracaoDTO, null, null, null, "Desativado"));
					}
				}

				if (colListaNegraEncontrados != null && colListaNegraEncontrados.size() > 0) {
					notificacaoListaNegraEncontradosDTO.setTabelaListaNegra(formatarColNotifListaNegraEmail(colListaNegraEncontrados));
				}

				if (notificacaoListaNegraEncontradosDTO.getTabelaListaNegra() != null && !StringUtils.isBlank(notificacaoListaNegraEncontradosDTO.getTabelaListaNegra())) {

					try {

						enviaEmailGrupo(Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_SOFTWARE_LISTA_NEGRA, "").trim()),
								Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_RESPONSAVEL_SOFTWARE_LISTA_NEGRA, "").trim()),
								notificacaoListaNegraEncontradosDTO);

					} catch (NumberFormatException e) {

						System.out.println(i18nMessage("requisicaoLiberacao.emailNaoDefinido"));

					}
				}

				transactionControler.commit();

			} catch (Exception e) {

				e.printStackTrace();

				this.rollbackTransaction(transactionControler, e);

			} finally {
				try {
					transactionControler.close();
				} catch (PersistenceException e) {
					e.printStackTrace();
				}

			}
		} finally {
			lock.unlock();
		}

		return itemConfiguracao;
	}

	/**
	 * Formata em string a coleção de softwares lista negra, transformando em uma tabela HTML para enviar por email
	 *
	 * @author ronnie.lopes
	 *
	 */
	public String formatarColNotifListaNegraEmail(List<NotificacaoListaNegraEncontradosDTO> lista) {
		StringBuilder texto = new StringBuilder();

		if (lista != null && lista.size() > 0) {

			texto.append("<table border='1' align='left' width=100%>" + "     <tbody>" + "                  <tr>" + "                               <td><b>Computador</b></td>"
					+ "                 <td><b>Software Lista Negra</b></td>" + "                             <td><b>Software Encontrado</b></td>"
					+ "                                <td><b>Data Hora</b></td>" + "                  </tr>");
			for (NotificacaoListaNegraEncontradosDTO n : lista) {
				texto.append("  <tr>" + "                               <td>" + n.getComputador() + "</td>" + "                         <td>" + n.getSoftwarelistanegra() + "</td>"
						+ "                         <td>" + n.getSoftwareencontrado() + "</td>" + "                         <td>" + n.getDataHora() + "</td>" + "                 </tr>");
			}
			texto.append("</tbody>" + "</table>");
			return texto.toString();
		} else {
			return null;
		}
	}

	/**
	 * Notifica com relatório Softwares Lista Negra todos os responsáveis de um grupo
	 *
	 * @author ronnie.lopes
	 * @param idModeloEmail
	 *            , idGrupoDestino, notListaNegraEncontradosDTO
	 * @throws Exception
	 */
	public void enviaEmailGrupo(Integer idModeloEmail, Integer idGrupoDestino, NotificacaoListaNegraEncontradosDTO notListaNegraEncontradosDTO) throws Exception {
		MensagemEmail mensagem = null;

		if (idGrupoDestino == null) {
			return;
		}

		if (idModeloEmail == null) {
			return;
		}

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		List<String> emails = null;

		try {
			emails = (List<String>) grupoService.listarPessoasEmailPorGrupo(idGrupoDestino);

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		if (emails == null || emails.isEmpty()) {
			return;
		}

		String remetente = ParametroUtil.getValor(ParametroSistema.RemetenteNotificacoesSolicitacao, null, null);
		if (remetente == null)
			throw new LogicException(i18nMessage("requisicaoLiberacao.remetenteNaoDefinido"));

		Object[] emailsArray = new Object[(emails.size() / 2)];
		int j = 0;
		for (int i = 0; i < emails.size(); i++) {
			if (emails.get(i).contains("@")) {
				continue;
			} else {
				emailsArray[j] = emails.get(i);
				j++;
			}
		}

		try {
			int i = 0;
			for (String email : emails) {
				int posArroba = email.indexOf("@");
				if (posArroba > 0 && email.substring(posArroba).contains(".")) {
					try {
						if (StringUtils.isNotBlank(emailsArray[i].toString())) {
							String nomeContato = emailsArray[i].toString();
							notListaNegraEncontradosDTO.setNomeContato(nomeContato);
						}
						mensagem = new MensagemEmail(idModeloEmail, new IDto[] { notListaNegraEncontradosDTO });
						mensagem.envia(email, remetente, remetente);
						i++;
					} catch (Exception e) {
					}
				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * Grava Tipo Item configuracao
	 *
	 * @param tipoItemConfiguracaoDTO
	 * @param executorService
	 * @return tipoItemConfiguracaoDTO
	 * @throws Exception
	 */
	public synchronized TipoItemConfiguracaoDTO gravarTipoItemConfiguracao(TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO, ExecutorService executorService) throws Exception {

		try {

			List listTipoItemConf = getTipoItemConfiguracaoDAO().findByNomeTipoItemConfiguracao(tipoItemConfiguracaoDTO.getNome());

			if (listTipoItemConf == null || listTipoItemConf.size() == 0) {

				tipoItemConfiguracaoDTO.setDataInicio(UtilDatas.getDataAtual());
				tipoItemConfiguracaoDTO.setIdEmpresa(1);

				tipoItemConfiguracaoDTO = (TipoItemConfiguracaoDTO) getTipoItemConfiguracaoDAO().create(tipoItemConfiguracaoDTO);

			} else {

				tipoItemConfiguracaoDTO = (TipoItemConfiguracaoDTO) listTipoItemConf.get(0);

			}

			tipoItemConfiguracaoDTO.setSistema("S");
			this.tratarMonitoramentoAtivoDeConfiguracao(tipoItemConfiguracaoDTO, null, null, null, true, executorService);

		} catch (Exception e) {

			System.out.println("Problema ao gravar tipoItemConfiguracao: " + e);
			e.printStackTrace();

		}

		return tipoItemConfiguracaoDTO;
	}

	/**
	 * Gravar Caracteristica do Tipo Item Configuracao
	 *
	 * @param caracteristicaBean
	 * @param tipoitemconfiguracao
	 * @param caracteristicaDao
	 * @param caracteristicaTipoItemConfiguracaoDAO
	 * @return caracteristicaBean
	 * @throws Exception
	 */
	private synchronized CaracteristicaDTO gravarCaracteristica(CaracteristicaDTO caracteristicaBean, TipoItemConfiguracaoDTO tipoitemconfiguracao, CaracteristicaDao caracteristicaDao,
			CaracteristicaTipoItemConfiguracaoDAO caracteristicaTipoItemConfiguracaoDao) throws Exception {
		try {

			List lstExisteCaract = getCaracteristicaDao().consultarCaracteristicas("", caracteristicaBean.getTag(), false);

			if (lstExisteCaract == null || lstExisteCaract.size() == 0) {

				caracteristicaBean.setIdEmpresa(1);
				caracteristicaBean.setTipo("A");
				caracteristicaBean.setDataInicio(UtilDatas.getDataAtual());
				caracteristicaBean.setSistema("S");
				// caracteristicaBean.setSistema((char) 1);
				caracteristicaBean = (CaracteristicaDTO) getCaracteristicaDao().create(caracteristicaBean);

			} else {

				caracteristicaBean = (CaracteristicaDTO) lstExisteCaract.get(0);
			}

			List lstExisteCaractTipoItem = getCaracteristicaDao().consultarCaracteristicas(tipoitemconfiguracao.getTag(), caracteristicaBean.getTag(), false);

			if (lstExisteCaractTipoItem == null || lstExisteCaractTipoItem.size() == 0) {

				this.gravarCaracteristicaTipoItemConfiguracaoDTO(tipoitemconfiguracao, caracteristicaBean, caracteristicaTipoItemConfiguracaoDao);

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PROBLEMA AO GRAVAR CARACTERISTICA!");

		}
		return caracteristicaBean;
	}

	/**
	 * Grava Valor Relacionado a caracteristica de Item configuracao do tipo item Configuracao
	 *
	 * @param valorDTO
	 * @param valorDao
	 * @param caracteristicaBean
	 * @param itemConfiguracaoDTO
	 * @param executorService
	 * @return valorDTO
	 * @throws Exception
	 */
	private ValorDTO gravarValor(ValorDTO valorDTO, CaracteristicaDTO caracteristicaBean, ItemConfiguracaoDTO itemConfiguracaoFilhoGravado, HistoricoItemConfiguracaoDTO historicoIc,
			boolean verificaGravacaoIc, TransactionControler tc, ExecutorService executorService) throws Exception {
		try {

			ValorDTO valorBean = getValorDao().restoreItemConfiguracao(itemConfiguracaoFilhoGravado.getIdItemConfiguracao(), caracteristicaBean.getIdCaracteristica());

			if (valorBean == null) {
				valorDTO.setIdItemConfiguracao(itemConfiguracaoFilhoGravado.getIdItemConfiguracao());
				valorDTO.setIdCaracteristica(caracteristicaBean.getIdCaracteristica());
				valorDTO = (ValorDTO) getValorDao().create(valorDTO);
				if (verificaGravacaoIc) {
					// Verifica situacao para gravar log somente uma vez, como pode ser alterado somente a uma valor de uma caracteristica
					if (!this.situacao) {
						getAuditoriaItemConfiguracaoDao().create(this.gravarAuditoriaItemConfig(itemConfiguracaoFilhoGravado, null, null, null, "Criação"));
						this.situacao = true;
					}
				}
			} else {

				TipoItemConfiguracaoDTO tipoItemConfiguracaoDto = new TipoItemConfiguracaoDTO();

				tipoItemConfiguracaoDto.setId(itemConfiguracaoFilhoGravado.getIdTipoItemConfiguracao());

				this.tratarMonitoramentoAtivoDeConfiguracao(tipoItemConfiguracaoDto, caracteristicaBean, valorBean, valorDTO, false, executorService);

				ValorDTO valorByIdIC = getValorDao().restoreValorByIdItemConfiguracao(itemConfiguracaoFilhoGravado.getIdItemConfiguracao(), caracteristicaBean.getIdCaracteristica(),
						valorDTO.getValorStr());
				valorBean.setValorStr(valorDTO.getValorStr());

				if (historicoIc != null && historicoIc.getIdHistoricoIC() != null) {
					// Grava Historico valor item configuracao e grava auditoria
					this.getHistoricoValorDao().create(this.createHistoricoValor(valorBean, null, historicoIc.getIdHistoricoIC().intValue()));
				}

				if (valorByIdIC == null) {
					if (verificaGravacaoIc) {
						if (!this.situacao) {
							getAuditoriaItemConfiguracaoDao().create(this.gravarAuditoriaItemConfig(itemConfiguracaoFilhoGravado, null, null, null, "Alteração"));
							this.situacao = true;
						}
					}
				}

				getValorDao().update(valorBean);
			}

		} catch (Exception e) {
			System.out.println("Problema ao gravar valor: " + e.getMessage());
			e.printStackTrace();
		}
		return valorDTO;
	}

	/**
	 * Grava Item Configuracao Filho
	 *
	 * @param itemConfiguracaoPai
	 * @param tipoItemConfiguracaoDTO
	 * @return itemConfiguracaoDTO
	 * @throws Exception
	 */
	private synchronized ItemConfiguracaoDTO gravarItemConfiguracaoFilho(ItemConfiguracaoDTO itemConfiguracaoPai, TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO) throws Exception {

		ItemConfiguracaoDTO itemConfiguracaoFilho = new ItemConfiguracaoDTO();

		HistoricoItemConfiguracaoDTO historioIcDto = new HistoricoItemConfiguracaoDTO();

		try {

			itemConfiguracaoFilho.setIdItemConfiguracaoPai(itemConfiguracaoPai.getIdItemConfiguracao());
			itemConfiguracaoFilho.setIdTipoItemConfiguracao(tipoItemConfiguracaoDTO.getId());
			itemConfiguracaoFilho.setDataInicio(UtilDatas.getDataAtual());

			String identificacao = this.obterIdentificacaoItemConfiguracaoFilho(tipoItemConfiguracaoDTO);

			if (identificacao.length() > 400) {
				identificacao = identificacao.substring(0, 399);
			}

			itemConfiguracaoFilho.setIdentificacao(StringUtils.replace(identificacao, "\0 ", ""));

			itemConfiguracaoFilho = this.getDao().obterICFilhoPorIdentificacaoIdPaiEIdTipo(itemConfiguracaoFilho);

			if (itemConfiguracaoFilho.getIdItemConfiguracao() != null) {
				itemConfiguracaoFilho.setDataFim(null);

				this.getDao().updateItemConfiguracaoFilho(itemConfiguracaoFilho.getIdItemConfiguracao(), null, null);

				this.getDao().update(itemConfiguracaoFilho);

				if (itemConfiguracaoFilho.getIdItemConfiguracao() != null) {
					itemConfiguracaoFilho = (ItemConfiguracaoDTO) this.getDao().restore(itemConfiguracaoFilho);
					historioIcDto = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().create(this.createHistoricoItem(itemConfiguracaoFilho, null));
							}

				if (historioIcDto != null) {
					itemConfiguracaoFilho.setHistoricoItemConfiguracaoDTO(historioIcDto);
						}
			} else {
				itemConfiguracaoFilho = (ItemConfiguracaoDTO) this.getDao().create(itemConfiguracaoFilho);
					}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PROBLEMA AO GRAVAR ITEM CONFIGURACAO FILHO!");
		}

		return itemConfiguracaoFilho;
	}

	/**
	 * Grava Vinculo Caracteristica e Tipo Item congiguracao
	 *
	 * @param tipoItemConfiguracaoDTO
	 * @param caracteristicaBean
	 * @return caracteristicaTipoItemConfiguracaoBean
	 */
	private synchronized CaracteristicaTipoItemConfiguracaoDTO gravarCaracteristicaTipoItemConfiguracaoDTO(TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO, CaracteristicaDTO caracteristicaBean,
			CaracteristicaTipoItemConfiguracaoDAO caracteristicaTipoItemConfiguracaoDao) {

		CaracteristicaTipoItemConfiguracaoDTO caracteristicaTipoItemConfiguracaoBean = new CaracteristicaTipoItemConfiguracaoDTO();

		try {

			caracteristicaTipoItemConfiguracaoBean.setIdTipoItemConfiguracao(tipoItemConfiguracaoDTO.getId());
			caracteristicaTipoItemConfiguracaoBean.setIdCaracteristica(caracteristicaBean.getIdCaracteristica());
			caracteristicaTipoItemConfiguracaoBean.setDataInicio(UtilDatas.getDataAtual());
			caracteristicaTipoItemConfiguracaoBean.setNameInfoAgente(caracteristicaBean.getNome());

			getCaracteristicaTipoItemConfiguracaoDao().create(caracteristicaTipoItemConfiguracaoBean);

		} catch (Exception e) {
			System.out.println("PROBLEMA AO GRAVAR CARACTERISTICA TIPO ITEM CONFIGURACAO!");
		}

		return caracteristicaTipoItemConfiguracaoBean;
	}

	@Override
	public ItemConfiguracaoDTO restoreByIdItemConfiguracao(Integer idItemConfiguracao) throws Exception {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();

		condicoes.add(new Condition("idItemConfiguracao", "=", idItemConfiguracao));

		try {

			ArrayList<ItemConfiguracaoDTO> retorno = (ArrayList<ItemConfiguracaoDTO>) ((ItemConfiguracaoDao) getDao()).findByCondition(condicoes, null);

			if (retorno != null) {

				return retorno.get(0);

			}

		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;
	}

	@Override
	public ItemConfiguracaoDTO createItemConfiguracaoAplicacao(ItemConfiguracaoDTO itemConfiguracao, UsuarioDTO user) throws ServiceException, LogicException {

		TransactionControler transactionControler = new TransactionControlerImpl(getDao().getAliasDB());

		try {
			HistoricoItemConfiguracaoDTO ic = new HistoricoItemConfiguracaoDTO();

			this.validaCreate(itemConfiguracao);
			this.getDao().setTransactionControler(transactionControler);
			this.getHistoricoItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getValorDao().setTransactionControler(transactionControler);
			this.getAuditoriaItemConfiguracaoDao().setTransactionControler(transactionControler);

			transactionControler.start();

			if (itemConfiguracao != null && itemConfiguracao.getIdItemConfiguracaoPai() != null) {
				ItemConfiguracaoDTO itemConfiguracaoPai = new ItemConfiguracaoDTO();
				itemConfiguracaoPai.setIdItemConfiguracao(itemConfiguracao.getIdItemConfiguracaoPai());
				itemConfiguracaoPai = (ItemConfiguracaoDTO) this.restore(itemConfiguracaoPai);
			}

			if (itemConfiguracao != null) {
				itemConfiguracao.setDataInicio(UtilDatas.getDataAtual());
			}

			if (itemConfiguracao != null && itemConfiguracao.getIdGrupoItemConfiguracao() != null) {
				if (itemConfiguracao.getIdGrupoItemConfiguracao() == 1000) {
					throw new LogicException("Você deve criar um novo Grupo e fazer a vinculação com ele.");
				}
			}
			itemConfiguracao = (ItemConfiguracaoDTO) getDao().create(itemConfiguracao);
			if (itemConfiguracao.getIdItemConfiguracao() != null) {
				ic = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().create(this.createHistoricoItem(itemConfiguracao, user));
			}
			relacaoMudanca(itemConfiguracao);
			relacaoProblema(itemConfiguracao);
			relacaoIncidente(itemConfiguracao);
			relacaoLiberacao(itemConfiguracao);

			enviarEmailNotificacao(itemConfiguracao, transactionControler, "CRIA_IC");

			this.criarEAssociarValorDaCaracteristicaAoItemConfiguracao(itemConfiguracao, user, ic.getIdHistoricoIC());

			transactionControler.commit();

		} catch (Exception e) {

			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);

		} finally {
			try {
				transactionControler.close();
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		return itemConfiguracao;
	}

	/**
	 * Retorna Service de Valor.
	 *
	 * @return ValorService
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public ValorService getValorService() throws ServiceException, Exception {
		return (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
	}

	/**
	 * Retorna Service de Valor.
	 *
	 * @return ValorService
	 * @throws ServiceException
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public UsuarioService getUsuarioService() throws ServiceException, Exception {
		return (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
	}

	/**
	 * Cria ou Atualiza associação do Valor da Característica ao ítem de Configuração.
	 *
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	@Override
	public void criarEAssociarValorDaCaracteristicaAoItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDto, UsuarioDTO user, Integer idHistoricoIC) throws Exception {

		if (itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas() != null && itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas() != null
				&& !itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().isEmpty() && idHistoricoIC != null) {
			boolean situacao = false;

			for (int i = 0; i < itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().size(); i++) {

				ValorDTO valor = this.getValorDao().restoreItemConfiguracao(itemConfiguracaoDto.getIdItemConfiguracao(),
						((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getIdCaracteristica());

				if (valor != null) {

					valor.setValorStr(((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getValorString());
					valor.setValorLongo(((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getValorString());

					ValorDTO valorDto = this.getValorDao().restoreValorByIdItemConfiguracao(itemConfiguracaoDto.getIdItemConfiguracao(),
							((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getIdCaracteristica(), valor.getValorStr());
					HistoricoValorDTO historicoValorbean = this.getHistoricoValorDao().restoreValorByIdValor(valor.getIdValor());
					this.getValorDao().update(valor);
					this.getHistoricoValorDao().create(this.createHistoricoValor(valor, user, idHistoricoIC));

					if (valorDto == null) {
						if (!situacao) {
							this.getAuditoriaItemConfiguracaoDao().create(this.gravarAuditoriaItemConfig(itemConfiguracaoDto, null, null, user, "Alteração"));
							situacao = true;
						}

					} else if (historicoValorbean == null) {
						if (!situacao) {
							this.getAuditoriaItemConfiguracaoDao().create(this.gravarAuditoriaItemConfig(itemConfiguracaoDto, null, null, user, "Alteração"));
							situacao = true;
						}
					}

				} else {
					HistoricoItemConfiguracaoDTO historioConfiguracaoDTO = new HistoricoItemConfiguracaoDTO();
					valor = new ValorDTO();
					valor.setIdItemConfiguracao(itemConfiguracaoDto.getIdItemConfiguracao());
					valor.setIdCaracteristica(((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getIdCaracteristica());
					valor.setValorStr(((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getValorString());
					valor.setValorLongo(((CaracteristicaDTO) itemConfiguracaoDto.getTipoItemConfiguracaoSerializadas().getCaracteristicas().get(i)).getValorString());
					this.getValorDao().create(valor);

					this.getHistoricoValorDao().create(this.createHistoricoValor(valor, user, idHistoricoIC));
					historioConfiguracaoDTO.setIdHistoricoIC(idHistoricoIC);
					itemConfiguracaoDto.setHistoricoItemConfiguracaoDTO(historioConfiguracaoDTO);
					if (!situacao) {
						this.getAuditoriaItemConfiguracaoDao().create(this.gravarAuditoriaItemConfig(itemConfiguracaoDto, null, null, user, "Criação"));
						situacao = true;
					}

				}
			}
		}
	}

	@Override
	public ItemConfiguracaoDTO listIdUsuario(String obj) throws Exception {

		try {

			return getDao().listIdUsuario(obj);

		} catch (Exception e) {

			throw new ServiceException(e);

		}
	}

	/**
	 * Alterado para não criar múltiplas transações 02/01/2015 - 14:09
	 *
	 * @author rafael.soyer
	 * @author thyen.chan
	 */
	@Override
	public void updateItemConfiguracao(IDto ItemConfiguracao, UsuarioDTO user) throws ServiceException, LogicException {

		ItemConfiguracaoDTO itemConfiguracaoDto = new ItemConfiguracaoDTO();
		TransactionControler transactionControler = new TransactionControlerImpl(getDao().getAliasDB());

		HistoricoItemConfiguracaoDTO ic = new HistoricoItemConfiguracaoDTO();
		itemConfiguracaoDto = (ItemConfiguracaoDTO) ItemConfiguracao;
		try {

			this.getHistoricoItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getRequisicaoMudancaItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getProblemaItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getItemConfiguracaoSolicitacaoServicoDao().setTransactionControler(transactionControler);
			this.getRequisicaoLiberacaoItemConfiguracaoDao().setTransactionControler(transactionControler);
			this.getValorDao().setTransactionControler(transactionControler);
			this.getHistoricoValorDao().setTransactionControler(transactionControler);
			transactionControler.start();
			/* Gravando o historico */
			if (itemConfiguracaoDto.getIdItemConfiguracao() != null) {
				ic = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().create(this.createHistoricoItem(itemConfiguracaoDto, user));
			}

			relacaoMudanca(itemConfiguracaoDto);
			relacaoProblema(itemConfiguracaoDto);
			relacaoIncidente(itemConfiguracaoDto);
			relacaoLiberacao(itemConfiguracaoDto);

			enviarEmailNotificacao(itemConfiguracaoDto, transactionControler, "ALT_IC");

			transactionControler.commit();
			transactionControler.closeQuietly();

			this.criarEAssociarValorDaCaracteristicaAoItemConfiguracao(itemConfiguracaoDto, user, ic.getIdHistoricoIC());
			getDao().update(itemConfiguracaoDto);

		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		}

	}

	public void relacaoMudanca(ItemConfiguracaoDTO item) throws ServiceException, Exception {
		/* Gravando o relacionamento com mudanca */
		if (item.getIdMudanca() != null) {
			RequisicaoMudancaItemConfiguracaoDTO m = new RequisicaoMudancaItemConfiguracaoDTO();
			m.setIdItemConfiguracao(item.getIdItemConfiguracao());
			m.setIdRequisicaoMudanca(item.getIdMudanca());
			if (!this.getRequisicaoMudancaItemConfiguracaoDao().verificaSeCadastrado(m))
				this.getRequisicaoMudancaItemConfiguracaoDao().create(m);
		}
	}

	public void relacaoProblema(ItemConfiguracaoDTO item) throws ServiceException, Exception {
		/* Gravando o relacionamento com problema */
		if (item.getIdProblema() != null) {
			ProblemaItemConfiguracaoDTO m = new ProblemaItemConfiguracaoDTO();
			m.setIdItemConfiguracao(item.getIdItemConfiguracao());
			m.setIdProblema(item.getIdProblema());
			if (!this.getProblemaItemConfiguracaoDao().verificaSeCadastrado(m))
				this.getProblemaItemConfiguracaoDao().create(m);
		}
	}

	public void relacaoIncidente(ItemConfiguracaoDTO item) throws ServiceException, Exception {
		/* Gravando o relacionamento com incidente */
		if (item.getIdIncidente() != null) {
			ItemCfgSolicitacaoServDTO m = new ItemCfgSolicitacaoServDTO();
			m.setIdItemConfiguracao(item.getIdItemConfiguracao());
			m.setIdSolicitacaoServico(item.getIdIncidente());
			m.setDataInicio(UtilDatas.getDataAtual());
			if (!this.getItemConfiguracaoSolicitacaoServicoDao().verificaSeCadastrado(m))
				this.getItemConfiguracaoSolicitacaoServicoDao().create(m);
		}
	}

	@Override
	public void restaurarBaseline(ItemConfiguracaoDTO item, UsuarioDTO user) throws Exception {
		ItemConfiguracaoDTO itemConfiguracaoDto = new ItemConfiguracaoDTO();
		TransactionControler transactionControler = new TransactionControlerImpl(getDao().getAliasDB());
		this.getValorDao().setTransactionControler(transactionControler);

		this.getHistoricoItemConfiguracaoDao().setTransactionControler(transactionControler);

		HistoricoItemConfiguracaoDTO ic = new HistoricoItemConfiguracaoDTO();
		itemConfiguracaoDto = (ItemConfiguracaoDTO) item;
		try {

			/* Gravando o historico */
			if (itemConfiguracaoDto.getIdItemConfiguracao() != null) {
				ic = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().create(this.createHistoricoItem(itemConfiguracaoDto, user));
			}
			transactionControler.start();
			itemConfiguracaoDto.setDataInicio(UtilDatas.getDataAtual());
			getDao().update(itemConfiguracaoDto);
			this.getValorDao().deleteByIdItemConfiguracao(itemConfiguracaoDto.getIdItemConfiguracao());
			if (itemConfiguracaoDto.getValores() != null) {
				for (ValorDTO valorDto : itemConfiguracaoDto.getValores()) {
					this.getValorDao().create(valorDto);
					this.getHistoricoValorDao().create(this.createHistoricoValor(valorDto, user, ic.getIdHistoricoIC()));
				}
			}
			transactionControler.commit();

		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		} finally {
			try {
				transactionControler.close();
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	/* Historico de item configuração */
	@Override
	public HistoricoItemConfiguracaoDTO createHistoricoItem(ItemConfiguracaoDTO itemConfiguracao, UsuarioDTO user) throws Exception {
		HistoricoItemConfiguracaoDTO historico = new HistoricoItemConfiguracaoDTO();
		Reflexao.copyPropertyValues(itemConfiguracao, historico);

		DecimalFormat fmt = new DecimalFormat("0.0");
		HistoricoItemConfiguracaoDTO ultVersao = new HistoricoItemConfiguracaoDTO();
		ultVersao = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().maxIdHistorico(itemConfiguracao);
		if (ultVersao.getIdHistoricoIC() != null) {
			ultVersao = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().restore(ultVersao);
			String sVersaoConvertida = fmt.format(ultVersao.getHistoricoVersao());
			historico.setHistoricoVersao((ultVersao.getHistoricoVersao() == null ? 1d : +new BigDecimal(Double.parseDouble(sVersaoConvertida.replace(",", ".")) + 0.1f).setScale(1,
					BigDecimal.ROUND_DOWN).floatValue()));
		} else {
			historico.setHistoricoVersao(1d);
		}

		historico.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());
		if (user != null) {
			if (user.getIdEmpregado() == null) {
				historico.setIdAutorAlteracao(1);
			} else {
				historico.setIdAutorAlteracao(user.getIdEmpregado());
			}

		} else {
			historico.setIdAutorAlteracao(1);
		}

		return historico;
	}

	/* Historico de valor item configuração */
	@Override
	public HistoricoValorDTO createHistoricoValor(ValorDTO valor, UsuarioDTO user, Integer idHistoricoIC) throws Exception {
		HistoricoValorDTO historico = new HistoricoValorDTO();
		Reflexao.copyPropertyValues(valor, historico);
		historico.setBaseLine("");
		historico.setIdHistoricoIC(idHistoricoIC);
		historico.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

		if (user == null) {
			historico.setIdAutorAlteracao(1);
		} else {
			historico.setIdAutorAlteracao(user.getIdEmpregado());
		}

		return historico;
	}

	@Override
	public void delete(IDto itemConfiguracao) throws ServiceException, LogicException {

		ItemConfiguracaoDTO itemConfiguracaoDto = new ItemConfiguracaoDTO();

		itemConfiguracaoDto = (ItemConfiguracaoDTO) itemConfiguracao;

		try {
			if (itemConfiguracaoDto.getIdItemConfiguracaoPai() == null) {

				TransactionControler transactionControler = new TransactionControlerImpl(getDao().getAliasDB());

				try {

					transactionControler.start();
					Collection<ItemConfiguracaoDTO> listItemConfiguracaoFilho = getDao().listItemConfiguracaoFilho(itemConfiguracaoDto);

					for (ItemConfiguracaoDTO itemConfiguracaoFilho : listItemConfiguracaoFilho) {

						itemConfiguracaoFilho = (ItemConfiguracaoDTO) this.restore(itemConfiguracaoFilho);
						itemConfiguracaoFilho.setDataFim(UtilDatas.getDataAtual());

						getDao().update(itemConfiguracaoFilho);
					}

					itemConfiguracaoDto.setDataFim(UtilDatas.getDataAtual());
					getDao().update(itemConfiguracaoDto);

					transactionControler.commit();

				} catch (Exception e) {

					e.printStackTrace();
					this.rollbackTransaction(transactionControler, e);

				} finally {
					try {
						transactionControler.close();
					} catch (PersistenceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			} else {

				itemConfiguracaoDto.setDataFim(UtilDatas.getDataAtual());
				getDao().update(itemConfiguracaoDto);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean validaDuplicidadeItemConfiguracao(ItemConfiguracaoDTO bean) throws Exception {
		return getDao().validaDuplicidadeItemConfiguracao(bean);
	}

	/**
	 * Retorna o Id da Empresa.
	 *
	 * @param request
	 * @return
	 */
	public static Integer getIdEmpresa(HttpServletRequest req) {
		UsuarioDTO usuario = WebUtil.getUsuario(req);
		return usuario.getIdEmpresa();
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listByGrupo(GrupoItemConfiguracaoDTO grupoICDto, String criticidade, String status) throws Exception {
		return getDao().listByGrupo(grupoICDto, criticidade, status);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listByGrupo(GrupoItemConfiguracaoDTO grupoICDto, ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		return getDao().listByGrupo(grupoICDto, itemConfiguracaoDTO);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(String criticidade, String status) throws Exception {
		return getDao().listItensSemGrupo(criticidade, status);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(String criticidade, String status, String sistemaOperacional, String grupoTrabalho, String tipoMembroDominio, String usuario,
			String processador, List softwares) throws Exception {
		return getDao().listItensSemGrupo(criticidade, status, sistemaOperacional, grupoTrabalho, tipoMembroDominio, usuario, processador, softwares);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listItensSemGrupo(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		return getDao().listItensSemGrupo(itemConfiguracaoDTO);
	}

	@Override
	public void atualizaGrupo(ItemConfiguracaoDTO itemConfiguracaoDTO, UsuarioDTO user) throws Exception {

		TransactionControler transactionControler = new TransactionControlerImpl(getDao().getAliasDB());
		try {
			this.getHistoricoItemConfiguracaoDao().setTransactionControler(transactionControler);

			transactionControler.start();
			getDao().atualizaGrupo(itemConfiguracaoDTO);
			Collection<ItemConfiguracaoDTO> listItemConfiguracaoFilho = getDao().listItemConfiguracaoFilho(itemConfiguracaoDTO);

			for (ItemConfiguracaoDTO itemConfiguracaoFilho : listItemConfiguracaoFilho) {

				itemConfiguracaoFilho = (ItemConfiguracaoDTO) this.restore(itemConfiguracaoFilho);
				itemConfiguracaoFilho.setIdGrupoItemConfiguracao(itemConfiguracaoDTO.getIdGrupoItemConfiguracao());
				getDao().atualizaGrupo(itemConfiguracaoFilho);

			}
			itemConfiguracaoDTO = (ItemConfiguracaoDTO) this.restore(itemConfiguracaoDTO);

			enviarEmailNotificacao(itemConfiguracaoDTO, transactionControler, "ALT_IC_GRUPO");

			transactionControler.commit();

		} catch (Exception e) {
			e.printStackTrace();
			this.rollbackTransaction(transactionControler, e);
		} finally {
			try {
				transactionControler.close();
			} catch (PersistenceException e) {
				e.printStackTrace();
			}

		}

	}

	@Override
	public Collection<ItemConfiguracaoDTO> listByEvento(Integer idEvento) throws Exception {
		try {
			ItemConfiguracaoDao dao = (ItemConfiguracaoDao) getDao();
			return dao.listByEvento(idEvento);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public boolean VerificaSeCadastrado(ItemConfiguracaoDTO itemDTO) throws Exception {
		return getDao().VerificaSeCadastrado(itemDTO);
	}

	@Override
	public void updateNotNull(IDto dto) {
		try {
			validaUpdate(dto);
			((ItemConfiguracaoDao) getDao()).updateNotNull(dto);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean verificaItemCriticos(Integer idItemConfiguracao) throws Exception {
		RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
		ProblemaDAO problemaDao = new ProblemaDAO();
		SolicitacaoServicoDao solicitacaoServicoDao = new SolicitacaoServicoDao();

		List<RequisicaoMudancaDTO> listMudanca = requisicaoMudancaDao.listMudancaByIdItemConfiguracao(idItemConfiguracao);
		List<ProblemaDTO> listProblema = problemaDao.listProblemaByIdItemConfiguracao(idItemConfiguracao);
		List<SolicitacaoServicoDTO> listSolicitacao = solicitacaoServicoDao.listSolicitacaoServicoByItemConfiguracao(idItemConfiguracao);

		if (listMudanca != null && listMudanca.size() > 0) {
			return true;
		}

		if (listSolicitacao != null && listSolicitacao.size() > 0) {
			return true;
		}

		if (listProblema != null && listProblema.size() > 0) {
			return true;
		}

		return false;
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listByIdItemConfiguracaoPai(Integer idItemPai) throws Exception {
		try {
			ItemConfiguracaoDao dao = (ItemConfiguracaoDao) getDao();
			return dao.findByIdItemConfiguracaoPai(idItemPai);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<ItemConfiguracaoDTO> pesquisaDataExpiracao(Date data) throws Exception {
		return getDao().pesquisaDataExpiracao(data);
	}

	@Override
	public boolean verificaMidiaSoftware(Integer idMidiaSoftware) throws Exception {
		return getDao().verificaMidiaSoftware(idMidiaSoftware);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdMudanca(Integer idMudanca) throws Exception {
		return getDao().listItemConfiguracaoByIdMudanca(idMudanca);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdProblema(Integer problema) throws Exception {
		return getDao().listItemConfiguracaoByIdProblema(problema);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdIncidente(Integer idIncidente) throws Exception {
		return getDao().listItemConfiguracaoByIdIncidente(idIncidente);
	}

	@Override
	public void enviarEmailNotificacao(ItemConfiguracaoDTO itemConfiguracaoDTO, TransactionControler transactionControler, String notificacao) throws Exception {

		EmpregadoDao empregadoDao = new EmpregadoDao();
		GrupoItemConfiguracaoDAO grupoItemConfiguracaoDAO = new GrupoItemConfiguracaoDAO();
		EmpregadoDTO emp = new EmpregadoDTO();
		GrupoItemConfiguracaoDTO grupoItemConfiguracaoDTO = new GrupoItemConfiguracaoDTO();

		if (transactionControler != null) {
			empregadoDao.setTransactionControler(transactionControler);
		}

		String remetente = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.RemetenteNotificacoesSolicitacao, null);
		String ID_MODELO_EMAIL_AVISAR_CRIACAO_IC = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_CRIACAO_IC, "16");
		String ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_ALTERACAO_IC, "14");
		String ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC_GRUPO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_MODELO_EMAIL_AVISAR_ALTERACAO_IC_GRUPO, "15");
		String ID_MODELO_EMAIL = "";

		if (ID_MODELO_EMAIL_AVISAR_CRIACAO_IC == null || ID_MODELO_EMAIL_AVISAR_CRIACAO_IC.isEmpty()) {
			ID_MODELO_EMAIL_AVISAR_CRIACAO_IC = "16";
		}

		if (ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC == null || ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC.isEmpty()) {
			ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC = "14";
		}

		if (ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC_GRUPO == null || ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC_GRUPO.isEmpty()) {
			ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC_GRUPO = "15";
		}

		if (notificacao.equals("CRIA_IC")) {
			ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_CRIACAO_IC;
		} else if (notificacao.equals("ALT_IC")) {
			ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC;
		} else if (notificacao.equals("ALT_IC_GRUPO")) {
			ID_MODELO_EMAIL = ID_MODELO_EMAIL_AVISAR_ATUALIZACAO_IC_GRUPO;
		}

		if (!ID_MODELO_EMAIL.isEmpty()) {

			String PADRAO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ENVIO_PADRAO_EMAIL_IC, "1");
			if (PADRAO == null || PADRAO.isEmpty()) {
				PADRAO = "1";
			}
			if (PADRAO.equals("1")) {
				// Enviando email para o grupo do IC
				if (itemConfiguracaoDTO.getIdGrupoItemConfiguracao() != null) {
					grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(itemConfiguracaoDTO.getIdGrupoItemConfiguracao());
					if (grupoItemConfiguracaoDAO.VerificaSeCadastrado(grupoItemConfiguracaoDTO)) {
						grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoDAO.restore(grupoItemConfiguracaoDTO);
						itemConfiguracaoDTO.setNomeGrupoItemConfiguracao(grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao());
					} else
						grupoItemConfiguracaoDTO = null;
				}
				if (grupoItemConfiguracaoDTO != null) {
					MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL.trim()), new IDto[] { itemConfiguracaoDTO });
					if (grupoItemConfiguracaoDTO.getEmailGrupoItemConfiguracao() != null) {
						mensagem.envia(grupoItemConfiguracaoDTO.getEmailGrupoItemConfiguracao(), "", remetente);
					}
				}
			} else if (PADRAO.equals("2")) {
				// Enviando email para o proprietário do IC
				if (itemConfiguracaoDTO.getIdProprietario() != null) {
					emp.setIdEmpregado(itemConfiguracaoDTO.getIdProprietario());
					emp = (EmpregadoDTO) empregadoDao.restore(emp);
				}
				if (emp != null) {
					MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL), new IDto[] { itemConfiguracaoDTO });
					if (emp.getEmail() != null) {
						mensagem.envia(emp.getEmail(), "", remetente);
					}
				}
			} else if (PADRAO.equals("3")) {
				// Enviando email para o grupo do IC
				if (itemConfiguracaoDTO.getIdGrupoItemConfiguracao() != null) {
					grupoItemConfiguracaoDTO.setIdGrupoItemConfiguracao(itemConfiguracaoDTO.getIdGrupoItemConfiguracao());
					if (grupoItemConfiguracaoDAO.VerificaSeCadastrado(grupoItemConfiguracaoDTO)) {
						grupoItemConfiguracaoDTO = (GrupoItemConfiguracaoDTO) grupoItemConfiguracaoDAO.restore(grupoItemConfiguracaoDTO);
						itemConfiguracaoDTO.setNomeGrupoItemConfiguracao(grupoItemConfiguracaoDTO.getNomeGrupoItemConfiguracao());
					} else
						grupoItemConfiguracaoDTO = null;
				}
				if (grupoItemConfiguracaoDTO != null) {
					MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL), new IDto[] { itemConfiguracaoDTO });
					if (grupoItemConfiguracaoDTO.getEmailGrupoItemConfiguracao() != null) {
						mensagem.envia(grupoItemConfiguracaoDTO.getEmailGrupoItemConfiguracao(), "", remetente);
					}
				}

				// Enviando email para o proprietário do IC
				if (itemConfiguracaoDTO.getIdProprietario() != null) {
					emp.setIdEmpregado(itemConfiguracaoDTO.getIdProprietario());
					emp = (EmpregadoDTO) empregadoDao.restore(emp);
				}
				if (emp != null) {
					MensagemEmail mensagem = new MensagemEmail(Integer.parseInt(ID_MODELO_EMAIL), new IDto[] { itemConfiguracaoDTO });
					if (emp.getEmail() != null) {
						mensagem.envia(emp.getEmail(), "", remetente);
					}
				}
			}
		}

	}

	@Override
	public Collection<ItemConfiguracaoDTO> listaItemConfiguracaoPorBaseConhecimento(ItemConfiguracaoDTO itemConfiguracao) throws Exception {
		return getDao().listaItemConfiguracaoPorBaseConhecimento(itemConfiguracao);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> quantidadeItemConfiguracaoPorBaseConhecimento(ItemConfiguracaoDTO itemConfiguracao) throws Exception {
		return getDao().quantidadeItemConfiguracaoPorBaseConhecimento(itemConfiguracao);
	}

	@Override
	public Collection findByConhecimento(BaseConhecimentoDTO baseConhecimentoDto) throws ServiceException, LogicException {
		try {
			return getDao().findByConhecimento(baseConhecimentoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection findByIdItemConfiguracaoPai(Integer parm) throws Exception {
		return getDao().findByIdItemConfiguracaoPai(parm);
	}

	@Override
	public Integer quantidadeMidiaSoftware(ItemConfiguracaoDTO itemDTO) throws Exception {
		return getDao().quantidadeMidiaSoftware(itemDTO);
	}

	@Override
	public List<ItemConfiguracaoDTO> listaItemConfiguracaoOfficePak(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		return getDao().listaItemConfiguracaoOfficePak(itemConfiguracaoDTO);
	}

	@Override
	public List<ItemConfiguracaoDTO> listaItemConfiguracaoOfficePak(ItemConfiguracaoDTO itemConfiguracaoDTO, String chave) throws Exception {
		return getDao().listaItemConfiguracaoOfficePak(itemConfiguracaoDTO, chave);
	}

	@Override
	public void atualizaParaGrupoProducao(int idItem) throws ServiceException, Exception {

		Integer ID_CICLO_PRODUCAO_PADRAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO_PADRAO, "1003").isEmpty() ? "1003"
				: ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO_PADRAO, "1003")));
		Integer ID_CICLO_PRODUCAO = Integer.parseInt((ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO, "998").isEmpty() ? "998" : ParametroUtil
				.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_IC_PRODUCAO, "998")));

		ItemConfiguracaoDTO itemDto = new ItemConfiguracaoDTO();
		itemDto.setIdItemConfiguracao(idItem);
		itemDto = (ItemConfiguracaoDTO) getDao().restore(itemDto);

		GrupoItemConfiguracaoDAO grupoDao = new GrupoItemConfiguracaoDAO();
		GrupoItemConfiguracaoDTO grupo = new GrupoItemConfiguracaoDTO();
		if (grupo != null && itemDto != null) {
			grupo.setIdGrupoItemConfiguracao(itemDto.getIdGrupoItemConfiguracao());
		}

		if (grupo != null && grupo.getIdGrupoItemConfiguracao() != null) {
			grupo = (GrupoItemConfiguracaoDTO) grupoDao.restore(grupo);
		}

		if (grupo != null) {
			if (grupo.getIdGrupoItemConfiguracaoPai() != null && grupo.getIdGrupoItemConfiguracaoPai().intValue() > 0)
				if (grupo.getIdGrupoItemConfiguracaoPai().equals(ID_CICLO_PRODUCAO) || grupo.getIdGrupoItemConfiguracaoPai().equals(ID_CICLO_PRODUCAO_PADRAO))
					return;
		}

		if (itemDto != null) {
			if (!itemDto.getIdGrupoItemConfiguracao().equals(ID_CICLO_PRODUCAO_PADRAO)) {
				getDao().atualizaIdGrupoPadrao(itemDto, ID_CICLO_PRODUCAO_PADRAO);
			}
		}
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listItemConfiguracaoByIdLiberacao(Integer idLiberacao) throws Exception {
		return getDao().listItemConfiguracaoByIdLiberacao(idLiberacao);
	}

	public void relacaoLiberacao(ItemConfiguracaoDTO item) throws ServiceException, Exception {
		/* Gravando o relacionamento com liberação */
		if (item.getIdLiberacao() != null) {
			RequisicaoLiberacaoItemConfiguracaoDTO liberacao = new RequisicaoLiberacaoItemConfiguracaoDTO();
			liberacao.setIdItemConfiguracao(item.getIdItemConfiguracao());
			liberacao.setIdRequisicaoLiberacao(item.getIdLiberacao());
			if (!this.getRequisicaoLiberacaoItemConfiguracaoDao().verificaSeCadastrado(liberacao))
				this.getRequisicaoLiberacaoItemConfiguracaoDao().create(liberacao);
		}
	}

	/* Historico de item configuração para ser chamado pelos modulo de liberacao, mudanca e problema para setar a origem da modificação */
	@Override
	public void createHistoricoItemComOrigem(ItemConfiguracaoDTO itemConfiguracao, RequisicaoLiberacaoDTO liberacao, String origem) throws Exception {
		HistoricoItemConfiguracaoDTO historico = new HistoricoItemConfiguracaoDTO();
		UsuarioDTO user = new UsuarioDTO();
		Reflexao.copyPropertyValues(itemConfiguracao, historico);
		HistoricoItemConfiguracaoDAO dao = new HistoricoItemConfiguracaoDAO();

		user = liberacao.getUsuarioDto();

		historico.setOrigem(origem);
		historico.setIdOrigemModificacao(liberacao.getIdRequisicaoLiberacao());

		HistoricoItemConfiguracaoDTO ultVersao = new HistoricoItemConfiguracaoDTO();
		ultVersao = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().maxIdHistorico(itemConfiguracao);
		if (ultVersao.getIdHistoricoIC() != null) {
			ultVersao = (HistoricoItemConfiguracaoDTO) this.getHistoricoItemConfiguracaoDao().restore(ultVersao);
			historico.setHistoricoVersao((ultVersao.getHistoricoVersao() == null ? 1d : +new BigDecimal(ultVersao.getHistoricoVersao() + 0.1f).setScale(1, BigDecimal.ROUND_DOWN).floatValue()));
		} else {
			historico.setHistoricoVersao(1d);
		}

		historico.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());
		if (user != null) {
			if (user.getIdEmpregado() == null) {
				historico.setIdAutorAlteracao(1);
			} else {
				historico.setIdAutorAlteracao(user.getIdEmpregado());
			}

		} else {
			historico.setIdAutorAlteracao(1);
		}
		dao.create(historico);
	}

	public AuditoriaItemConfigDTO gravarAuditoriaItemConfig(ItemConfiguracaoDTO itemConfiguracaoDTO, HistoricoValorDTO historicoValorDTO, ValorDTO valorDto, UsuarioDTO usr, String tipoAlteracao) {
		AuditoriaItemConfigDTO auditoriaItemConfigDTO = new AuditoriaItemConfigDTO();

		try {

			if (itemConfiguracaoDTO != null && itemConfiguracaoDTO.getIdItemConfiguracao() != null) {

				if (itemConfiguracaoDTO.getHistoricoItemConfiguracaoDTO() != null) {
					auditoriaItemConfigDTO.setIdHistoricoIC(itemConfiguracaoDTO.getHistoricoItemConfiguracaoDTO().getIdHistoricoIC());
				}

				auditoriaItemConfigDTO.setIdItemConfiguracao(itemConfiguracaoDTO.getIdItemConfiguracao());
				auditoriaItemConfigDTO.setIdItemConfiguracaoPai(itemConfiguracaoDTO.getIdItemConfiguracaoPai());
				if (auditoriaItemConfigDTO.getIdHistoricoValor() != null) {
					auditoriaItemConfigDTO.setIdHistoricoValor(auditoriaItemConfigDTO.getIdHistoricoValor());
				}
			}

			if (usr != null && usr.getIdUsuario() != null) {
				auditoriaItemConfigDTO.setIdUsuario(usr.getIdUsuario());
			}

			if (tipoAlteracao != null) {
				auditoriaItemConfigDTO.setTipoAlteracao(tipoAlteracao);
			}

			if (historicoValorDTO != null && historicoValorDTO.getIdHistoricoValor() != null) {
				auditoriaItemConfigDTO.setIdHistoricoValor(historicoValorDTO.getIdHistoricoValor());
				auditoriaItemConfigDTO.setIdHistoricoIC(historicoValorDTO.getIdHistoricoIC());
			} else if (valorDto != null && valorDto.getIdValor() != null) {
				auditoriaItemConfigDTO.setIdHistoricoValor(valorDto.getIdValor());
			}

			auditoriaItemConfigDTO.setDataHoraAlteracao(UtilDatas.getDataHoraAtual());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return auditoriaItemConfigDTO;
	}

	public void finalizarItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDto, TransactionControler tc) {
		try {
			if (itemConfiguracaoDto != null && itemConfiguracaoDto.getIdItemConfiguracao() != null) {
				getDao().setTransactionControler(tc);
				itemConfiguracaoDto.setDataExpiracao(UtilDatas.getDataAtual());
				itemConfiguracaoDto.setDataFim(UtilDatas.getDataAtual());
				getDao().finalizarItemConfiguracao(itemConfiguracaoDto);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<AuditoriaItemConfigDTO> historicoAlteracaoItemConfiguracaoByIdItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {

		/** Comentado por não está sendo usado. valdoilo.damasceno */
		// ItemConfiguracaoDTO itemConfiguracaoRetornoDTO = (ItemConfiguracaoDTO) restore(itemConfiguracaoDTO);

		return getAuditoriaItemConfiguracaoDao().historicoAlteracaoItemConfiguracaoByIdItemConfiguracao(itemConfiguracaoDTO);

	}

	/**
	 *
	 * necessario passa data de finalização
	 *
	 * @param itemConfiguracaoDTO
	 * @return lista de item configuração finalizados "Desistalando"
	 * @throws Exception
	 */
	public List<ItemConfiguracaoDTO> retornaItemConfiguracaoFinalizadoByIdItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO, ItemConfiguracaoDao itemConfiguracaoDao) throws Exception {

		return getDao().listItemConfiguracaoFinalizadosByIdItemconfiguracao(itemConfiguracaoDTO);
	}

	@Override
	public boolean atualizaStatus(Integer item, Integer status) {
		return getDao().atualizaStatus(item, status);
	}

	@Override
	public ItemConfiguracaoDTO obterICFilhoPorIdentificacaoIdPaiEIdTipo(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		try {
			return this.getDao().obterICFilhoPorIdentificacaoIdPaiEIdTipo(itemConfiguracaoDTO);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ItemConfiguracaoDTO findByIdentificacaoItemConfiguracao(ItemConfiguracaoDTO itemConfiguracaoDTO) throws Exception {
		try {
			ItemConfiguracaoDao dao = (ItemConfiguracaoDao) getDao();
			return dao.findByIdentificacaoItemConfiguracao(itemConfiguracaoDTO);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listByIdGrupoAndTipoItemAndIdItemPaiAtivos(Integer idGrupo, Integer idTipo, Integer idPai) throws Exception {
		try {
			ItemConfiguracaoDao dao = (ItemConfiguracaoDao) getDao();
			return dao.listByIdGrupoAndTipoItemAndIdItemPaiAtivos(idGrupo, idTipo, idPai);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listByIdItemPaiAndTagTipoItemCfg(Integer idItemConfiguracaoPai, String tagTipoCfg) throws Exception {
		return getDao().listByIdItemPaiAndTagTipoItemCfg(idItemConfiguracaoPai, tagTipoCfg);
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listAtivos() throws Exception {
		return getDao().listAtivos();
	}

	@Override
	public Collection<ItemConfiguracaoDTO> listByIdentificacao(String identif) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("identificacao", "like", "%" + identif + "%"));
		condicao.add(new Condition("idItemConfiguracaoPai", "IS", null));

		ordenacao.add(new Order("idItemConfiguracaoPai"));
		List<ItemConfiguracaoDTO> lstItemCongConfiguracaoPai = (List) getDao().findByCondition(condicao, ordenacao);

		return lstItemCongConfiguracaoPai;
	}

	/**
	 * @param itemConfiguracaoDTO
	 * @param caracteristicaBean
	 * @param valorAntigo
	 * @param valorNovo
	 * @since 16.05.2014
	 * @author valdoilo.damasceno
	 * @param executorService
	 */
	private void tratarMonitoramentoAtivoDeConfiguracao(TipoItemConfiguracaoDTO tipoItemConfiguracaoDto, CaracteristicaDTO caracteristicaBean, ValorDTO valorAntigo, ValorDTO valorNovo,
			boolean isScript, ExecutorService executorService) {

		if (CITCorporeUtil.START_MONITORING_ASSETS) {
			MonitoramentoAtivosDAO monitoramentoAtivosDao = new MonitoramentoAtivosDAO();

			MonitoramentoAtivosDTO monitoramentoAtivosDto = null;

			if (tipoItemConfiguracaoDto != null && tipoItemConfiguracaoDto.getId() != null && caracteristicaBean != null && caracteristicaBean.getIdCaracteristica() != null && !isScript) {

				monitoramentoAtivosDto = monitoramentoAtivosDao.obterMonitorametoAtivoDaCaracteristica(tipoItemConfiguracaoDto.getId(), caracteristicaBean.getIdCaracteristica());

				if (monitoramentoAtivosDto != null && monitoramentoAtivosDto.getIdMonitoramentoAtivos() != null) {

					TipoItemConfiguracaoDTO tipoItemConfiguracaoMonitorado = new TipoItemConfiguracaoDTO();
					CaracteristicaDTO caracteristicaMonitorada = new CaracteristicaDTO();

					tipoItemConfiguracaoMonitorado.setId(tipoItemConfiguracaoDto.getId());
					caracteristicaMonitorada.setIdCaracteristica(caracteristicaBean.getIdCaracteristica());

					if (monitoramentoAtivosDto.getTipoRegra() != null && monitoramentoAtivosDto.getTipoRegra().equalsIgnoreCase("c")) {

						// Verifica se o valor foi alterado.
						if (valorAntigo != null && valorNovo != null && !valorAntigo.getValorStr().equalsIgnoreCase(valorNovo.getValorStr())) {

							ThreadMonitoraAtivosConfiguracao threadMonitoraAtivosConfiguracao = new ThreadMonitoraAtivosConfiguracao(monitoramentoAtivosDto, tipoItemConfiguracaoMonitorado,
									caracteristicaMonitorada, valorAntigo, valorNovo);

							executorService.execute(threadMonitoraAtivosConfiguracao);
						}
					}
				}

			} else {
				if (tipoItemConfiguracaoDto != null && tipoItemConfiguracaoDto.getId() != null && isScript) {

					monitoramentoAtivosDto = monitoramentoAtivosDao.obterMonitorametoAtivoDoTipoItemConfiguracao(tipoItemConfiguracaoDto.getId());

					if (monitoramentoAtivosDto != null && monitoramentoAtivosDto.getIdMonitoramentoAtivos() != null && monitoramentoAtivosDto.getTitulo() != null
							&& StringUtils.isNotBlank(monitoramentoAtivosDto.getTitulo()) && monitoramentoAtivosDto.getScript() != null && StringUtils.isNotBlank(monitoramentoAtivosDto.getScript())) {

						ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
						Context cx = Context.enter();
						Scriptable scope = cx.initStandardObjects();
						scope.put("tipoItemConfiguracaoDto", scope, tipoItemConfiguracaoDto);
						scope.put("monitoramentoAtivosDto", scope, monitoramentoAtivosDto);
						scope.put("out", scope, System.out);
						try {
							scriptExecute.processScript(cx, scope, monitoramentoAtivosDto.getScript(), monitoramentoAtivosDto.getTitulo());

							if (monitoramentoAtivosDto.getScriptSuccess()) {
								ThreadMonitoraAtivosConfiguracao threadMonitoraAtivosConfiguracao = new ThreadMonitoraAtivosConfiguracao(monitoramentoAtivosDto, null, null, valorAntigo, valorNovo);

								executorService.execute(threadMonitoraAtivosConfiguracao);
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

	/**
	 * Obtem a Identificação do Item de Configuração de acordo com os valores das características que o identifica como único.
	 *
	 * @param tipoItemConfiguracaoDTO
	 *            - Tipo do Item de Configuração com suas devidas características e valores.
	 * @return String - Identificação do Item de Configuração.
	 * @author valdoilo.damasceno
	 * @since 19.01.2015
	 */
	public String obterIdentificacaoItemConfiguracaoFilho(TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO) {

		TagTipoItemConfiguracaoDefault tagTipoItemConfiguracaoDefault = TagTipoItemConfiguracaoDefault.valueOf(tipoItemConfiguracaoDTO.getTag());

		try {

			switch (tagTipoItemConfiguracaoDefault) {
			case HARDWARE:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.NAME.name(), CaracteristicaIdentificacao.IPADDR.name());
			case BIOS:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.SMANUFACTURER.name(), CaracteristicaIdentificacao.SSN.name());
			case MEMORIES:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.CAPTION.name(), CaracteristicaIdentificacao.SERIALNUMBER.name(),
						CaracteristicaIdentificacao.CAPACITY.name());
			case INPUTS:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.TYPE.name(), CaracteristicaIdentificacao.DEVICEID.name());
			case ACCOUNTS:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.DOMAIN.name(), CaracteristicaIdentificacao.NAME.name());
			case ENVIRONMENTS:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.USERNAME.name(), CaracteristicaIdentificacao.NAME.name());
			case SERVICES:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.CAPTION.name(), CaracteristicaIdentificacao.STARTMODE.name());
			case STORAGES:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.NAME.name(), CaracteristicaIdentificacao.TYPE.name(),
						CaracteristicaIdentificacao.DISKSIZE.name());
			case NETWORKS:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.DESCRIPTION.name(), CaracteristicaIdentificacao.MACADDR.name());
			case SOFTWARES:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.NAME.name(), CaracteristicaIdentificacao.VERSION.name());
			case OFFICEPACK:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.PRODUCT.name(), CaracteristicaIdentificacao.PRODUCTID.name());
			case CAPTION:
				return this.obterIdentificacao(tipoItemConfiguracaoDTO.getCaracteristicas(), CaracteristicaIdentificacao.SMANUFACTURER.name(), CaracteristicaIdentificacao.SSN.name());
			default:
				return ((CaracteristicaDTO) tipoItemConfiguracaoDTO.getCaracteristicas().get(0)).getValorString();
			}
		} catch (Exception e) {
			return "Não identificado";
		}
	}

	/**
	 * Obtém a Identificação do Item de Configuração identificacao = valorCaracterística1 + valorCaracterística2.
	 *
	 * @param listCaracteristicaDto
	 *            - Lista de características.
	 * @param tagCaracteristica1
	 *            - TAG da Característica 1 de Identificação.
	 * @param tagCaracteristica2
	 *            - TAG da Característica 2 de Identificação.
	 * @return String - Identificação do Item de Configuração de acordo com as TAGs informadas.
	 * @author valdoilo.damasceno
	 * @since 19.01.2015
	 */
	private String obterIdentificacao(List<CaracteristicaDTO> listCaracteristicaDto, final String tagCaracteristica1, final String tagCaracteristica2) {

		StringBuilder nome1 = new StringBuilder();
		StringBuilder nome2 = new StringBuilder();

		if (listCaracteristicaDto != null && !listCaracteristicaDto.isEmpty()) {

			for (CaracteristicaDTO caracteristicaDto : listCaracteristicaDto) {

				try {

					CaracteristicaIdentificacao caracteristicaIdentificacao = CaracteristicaIdentificacao.valueOf(caracteristicaDto.getTag());

					if (caracteristicaIdentificacao != null && (nome1.toString().isEmpty() || nome2.toString().isEmpty())
							&& (caracteristicaIdentificacao.getNomeCaracteristica().equals(tagCaracteristica1))) {

						nome1.append(caracteristicaDto.getValor().getValorStr() + " - ");

					} else {

						if (caracteristicaIdentificacao != null && (nome1.toString().isEmpty() || nome2.toString().isEmpty())
								&& (caracteristicaIdentificacao.getNomeCaracteristica().equals(tagCaracteristica2))) {

							nome2.append(caracteristicaDto.getValor().getValorStr());

						}
					}
				} catch (Exception e) {
					/** Tag do Item de Configuração não é chave, prosseguir **/
				}
			}

			return nome1.toString() + nome2.toString();
		}

		return "Não Identificado ";
	}

	/**
	 * Obtém a Identificação do Item de Configuração identificacao = valorCaracterística1 + valorCaracterística2 + valorCaracterística3.
	 *
	 * @param listCaracteristicaDto
	 *            - Lista de características.
	 * @param tagCaracteristica1
	 *            - TAG da Característica 1 de Identificação.
	 * @param tagCaracteristica2
	 *            - TAG da Característica 2 de Identificação.
	 * @param tagCaracteristica3
	 *            - TAG da Característica 3 de Identificação.
	 * @return String - Identificação do Item de Configuração de acordo com as TAGs informadas.
	 * @author valdoilo.damasceno
	 * @since 19.01.2015
	 */
	private String obterIdentificacao(List<CaracteristicaDTO> listCaracteristicaDto, final String tagCaracteristica1, final String tagCaracteristica2, final String tagCaracteristica3) {

		StringBuilder nome1 = new StringBuilder();
		StringBuilder nome2 = new StringBuilder();
		StringBuilder nome3 = new StringBuilder();

		if (listCaracteristicaDto != null && !listCaracteristicaDto.isEmpty()) {

			for (CaracteristicaDTO caracteristicaDto : listCaracteristicaDto) {

				try {
					CaracteristicaIdentificacao caracteristicaIdentificacao = CaracteristicaIdentificacao.valueOf(caracteristicaDto.getTag());

					if (caracteristicaIdentificacao != null && (nome1.toString().isEmpty() || nome2.toString().isEmpty() || nome2.toString().isEmpty())) {

						if (caracteristicaIdentificacao.getNomeCaracteristica().equals(tagCaracteristica1)) {

							nome1.append(caracteristicaDto.getValor().getValorStr() + " - ");

						} else if (caracteristicaIdentificacao.getNomeCaracteristica().equals(tagCaracteristica2)) {

							nome2.append(caracteristicaDto.getValor().getValorStr() + " - ");

						} else if (caracteristicaIdentificacao.getNomeCaracteristica().equals(tagCaracteristica3)) {
							if (caracteristicaIdentificacao.getNomeCaracteristica().equals("DISKSIZE") || caracteristicaIdentificacao.getNomeCaracteristica().equals("CAPACITY")) {
								try {
									Double tamanho = Double.parseDouble(caracteristicaDto.getValor().getValorStr());
									tamanho /= Math.pow(1024, 3);
									nome3.append(new DecimalFormat(".00").format(tamanho).toString() + " GB");
								} catch (NumberFormatException e) {
									/** Valor não-numérico no campo de capacidade ou tamanho de disco. Retornar valor deste. **/
									nome3.append(caracteristicaDto.getValor().getValorStr());
								}
							} else {
								nome3.append("SIZE UNKNOWN");
							}

						} else {

							if (!nome1.toString().isEmpty() && !nome2.toString().isEmpty() && !nome3.toString().isEmpty()) {
								return nome1.toString() + nome2.toString() + nome3.toString();
							}
						}
					}
				} catch (Exception e) {

				}
			}
		}
		if (!nome1.toString().isEmpty() && !nome2.toString().isEmpty() && !nome3.toString().isEmpty()) {
			return nome1.toString() + nome2.toString() + nome3.toString();
		} else {
			return "Não Identificado ";
		}
	}
}