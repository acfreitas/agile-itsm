package br.com.centralit.citcorpore.bi.operation;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.NoHttpResponseException;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import br.com.centralit.citcorpore.bean.BICitsmartResultRotinaDTO;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bean.LogImportacaoBIDTO;
import br.com.centralit.citcorpore.bi.utils.BICitsmartUtils;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.negocio.ConexaoBIService;
import br.com.centralit.citcorpore.negocio.LogImportacaoBIService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ImportInfoField;
import br.com.centralit.citcorpore.util.ImportInfoRecord;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilImportData;
import br.com.centralit.citsmart.rest.schema.BICitsmart;
import br.com.centralit.citsmart.rest.schema.BICitsmartResp;
import br.com.centralit.citsmart.rest.schema.BICitsmartRespDeserializer;
import br.com.centralit.citsmart.rest.schema.CtLoginResp;
import br.com.citframework.excecao.ConnectionException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.TransactionOperationException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BICitsmartOperation {

	/**
	 * Executa a rotina automatica para recuperar as tabelas e gravar no BI do Citsmart
	 *
	 * @param conexaoBIDTO
	 * @param idProcessamentoBatch
	 * @return BICitsmartResultRotinaDTO
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	public BICitsmartResultRotinaDTO execucaoRotinaAutomatica(ConexaoBIDTO conexaoBIDTO, final Integer idProcessamentoBatch) throws Exception {

		BICitsmartResultRotinaDTO resultRotina = new BICitsmartResultRotinaDTO();
		resultRotina.setResultado(true);

		LogImportacaoBIDTO logImportacaoBI = new LogImportacaoBIDTO();
		logImportacaoBI.setDataHoraInicio(new Timestamp(new java.util.Date().getTime()));

		try {

			if (conexaoBIDTO == null) {
				resultRotina.setResultado(false);
				resultRotina.concatMensagem("As informações da conexão não foram preenchidas corretamente para a execução da rotina automática!");
			} else {
				if (conexaoBIDTO.getIdConexaoBI() == null) {
					resultRotina.setResultado(false);
					resultRotina.concatMensagem("Conexão BI não encontrada!");
				}
			}

			if (resultRotina.isResultado()) {

				if (conexaoBIDTO.getTipoImportacao() == null || conexaoBIDTO.getTipoImportacao().equalsIgnoreCase("A")) {

					if (!conexaoBIDTO.getLink().equals("") && !conexaoBIDTO.getLogin().equals("") && !conexaoBIDTO.getSenha().equals("")) {
						resultRotina = rotinaAutoBICitsmart(conexaoBIDTO.getIdConexaoBI(), conexaoBIDTO.getLink(), conexaoBIDTO.getLogin(), conexaoBIDTO.getSenha());
					} else {
						resultRotina.setResultado(false);
						resultRotina.concatMensagem("As informações da conexão não foram preenchidas corretamente para a execução da rotina automática!");
					}
				} else {
					resultRotina.setResultado(false);
					resultRotina.concatMensagem("A execução da rotina automática está desativada!");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			resultRotina.setResultado(false);
			resultRotina.concatMensagem("- Falha na rotina automática (Falha)");
		} catch (Error e) {
			e.printStackTrace();
			resultRotina.setResultado(false);
			resultRotina.concatMensagem("- Falha na rotina automática (Erro)");
		} finally {

			logImportacaoBI.setDataHoraFim(new Timestamp(System.currentTimeMillis()));

			if (idProcessamentoBatch != null) {
				logImportacaoBI.concatDetalhamento("Importação Automática BI Citsmart (via Agendamento).");
				logImportacaoBI.concatDetalhamento("Processo: " + idProcessamentoBatch);
			} else {
				logImportacaoBI.concatDetalhamento("Importação Automática BI Citsmart (via opção Executar Agora).");
			}

			logImportacaoBI.concatDetalhamento(resultRotina.getMensagem());

			if (resultRotina.isResultado()) {
				final Timestamp dataHoraExecucao = new Timestamp(System.currentTimeMillis());
				// Atualiza a data da última importação
				conexaoBIDTO.setDataHoraUltimaImportacao(dataHoraExecucao);
				logImportacaoBI.concatDetalhamento("Execução realizada com sucesso!");
				logImportacaoBI.setStatus("S");
			} else {
				logImportacaoBI.concatDetalhamento("Falha na execução da rotina!");
				logImportacaoBI.setStatus("F");
			}

			// Grava o log da importação
			logImportacaoBI.setTipo("A");
			logImportacaoBI.setIdConexaoBI(conexaoBIDTO.getIdConexaoBI());

			final LogImportacaoBIService logImportacaoBIService = (LogImportacaoBIService) ServiceLocator.getInstance().getService(LogImportacaoBIService.class, null);
			logImportacaoBIService.create(logImportacaoBI);

			// Atualiza o status integração da Conexão BI
			conexaoBIDTO.setStatus(logImportacaoBI.getStatus());
			final ConexaoBIService conexaoBIService = (ConexaoBIService) ServiceLocator.getInstance().getService(ConexaoBIService.class, null);
			conexaoBIService.update(conexaoBIDTO);

		}
		return resultRotina;
	}

	/**
	 * Executa a rotina de acesso ao webservice do cliente do BI Citsmart indicado
	 *
	 * @param url
	 * @param usuario
	 * @param senha
	 * @param tipo
	 * @return BICitsmartResultRotinaDTO
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	public BICitsmartResultRotinaDTO rotinaAutoBICitsmart(final Integer idConexao, String url, final String usuario, final String senha) throws Exception {
		BICitsmartResultRotinaDTO resultRotina = new BICitsmartResultRotinaDTO();
		resultRotina.setResultado(false);

		BICitsmart biCitsmart = new BICitsmart();

		if (!url.substring(url.length() - 1).equals("/")) {
			url += "/";
		}
		try {
			final URL urlTest = new URL(url);
			final URLConnection conn = urlTest.openConnection();
			conn.connect();

			BICitsmartResultRotinaDTO autenticacao = BICitsmartUtils.autenticacaoComJSON(url, usuario, senha);

			if (autenticacao.isResultado()) {
				biCitsmart.setSessionID(autenticacao.getSessionID());

				ClientRequest request = new ClientRequest(url + "services/bicitsmart/recuperarTabelas");

				String biCitsmartJson = new Gson().toJson(biCitsmart);

				request.body(MediaType.APPLICATION_JSON, biCitsmartJson);
				request.accept(MediaType.APPLICATION_JSON);

				final ClientResponse<String> response = request.post(String.class);

				if (response != null && response.getStatus() != 200) {
					BICitsmartResp resp = new BICitsmartResp();

					GsonBuilder gb = new GsonBuilder();
					gb.registerTypeAdapter(BICitsmartResp.class, new BICitsmartRespDeserializer());
					Gson gson = gb.create();

					InputStream ioos = new ByteArrayInputStream(response.getEntity().getBytes("UTF-8"));
					JsonReader reader = new JsonReader(new InputStreamReader(ioos, "UTF-8"));

					resp = gson.fromJson(reader, BICitsmartResp.class);

					if (resp != null && resp.getError().getDescription() != null && !resp.getError().getDescription().equals("")) {
						resultRotina.concatMensagem("- Estabelecimento da conexão: " + response.getStatus() + " - " + resp.getError().getDescription() + " (Falha) ");
					} else {
						resultRotina.concatMensagem("- Estabelecimento da conexão HTTP (Falha): Erro " + response.getStatus());
					}
				} else {
					resultRotina.concatMensagem("- Estabelecimento da conexão HTTP (OK)");
					resultRotina.concatMensagem(autenticacao.getMensagem());

					final String xmlString = StringEscapeUtils.unescapeHtml(response.getEntity());

					final BICitsmartResultRotinaDTO resultPersistencia = this.persisteDadosBICitsmart(idConexao, xmlString, false);
					if (resultPersistencia.isResultado()) {
						resultRotina.setResultado(true);
					}
					resultRotina.concatMensagem(resultPersistencia.getMensagem());
				}

			} else {
				resultRotina.concatMensagem(autenticacao.getMensagem());
			}
		} catch (MalformedURLException murle) {
			murle.printStackTrace();
			resultRotina.concatMensagem("- Validação de URL (Falha)");
		} catch (NoHttpResponseException nhttpe) {
			nhttpe.printStackTrace();
			resultRotina.concatMensagem("- URL sem resposta (Falha)");
		} catch (JsonIOException jio) {
			jio.printStackTrace();
			resultRotina.concatMensagem("- JSON erro de IO (Falha)");
		} catch (JsonSyntaxException jsyn) {
			jsyn.printStackTrace();
			resultRotina.concatMensagem("- JSON sintaxe incorreta (Falha)");
		} catch (IOException e) {
			resultRotina.concatMensagem("- Estabelecimento da conexão (Falha)");
		} catch (Exception e) {
			e.printStackTrace();
			resultRotina.concatMensagem("- Falha na rotina automática (Falha)");
		} catch (Error e) {
			e.printStackTrace();
			resultRotina.concatMensagem("- Falha na rotina automática (Erro)");
		}
		return resultRotina;
	}

	/**
	 * Executa o teste de acesso ao webservice do cliente do BI Citsmart indicado
	 *
	 * @param url
	 * @param usuario
	 * @param senha
	 * @return BICitsmartResultRotinaDTO
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	public BICitsmartResultRotinaDTO testeConexaoClienteBICitsmart(final HttpServletRequest req, final Integer idConexaoBI, String url, final String usuario, final String senha) throws Exception {
		BICitsmartResultRotinaDTO resultRotina = new BICitsmartResultRotinaDTO();
		resultRotina.setResultado(false);
		LogImportacaoBIDTO logImportacaoBI = new LogImportacaoBIDTO();

		try {

			final String input = "{\"userName\":\"" + usuario + "\",\"password\":\"" + senha + "\"}";
			logImportacaoBI.setDataHoraInicio(new Timestamp(new java.util.Date().getTime()));
			logImportacaoBI.setStatus("F");

			if (!url.substring(url.length() - 1).equals("/")) {
				url += "/";
			}

			final URL urlTest = new URL(url);
			final URLConnection conn = urlTest.openConnection();
			conn.connect();
			ClientRequest request = new ClientRequest(url + "services/login");
			request.accept(MediaType.APPLICATION_JSON);
			request.body(MediaType.APPLICATION_JSON, input);

			final ClientResponse<String> response = request.post(String.class);

			if (response != null && response.getStatus() != 200) {
				final CtLoginResp resp = new Gson().fromJson(response.getEntity(), CtLoginResp.class);
				if (resp != null && resp.getError().getDescription() != null && !resp.getError().getDescription().equals("")) {
					resultRotina.concatMensagem(UtilI18N.internacionaliza(req, "conexaoBI.testeConexaoErroFalhaConexao") + " " + resp.getError().getDescription());
				} else {
					resultRotina.concatMensagem(UtilI18N.internacionaliza(req, "conexaoBI.testeConexaoErroFalhaHTTP"));
				}
			} else {
				resultRotina.concatMensagem(UtilI18N.internacionaliza(req, "conexaoBI.testeConexaoSucesso"));
				logImportacaoBI.setStatus("S");
				resultRotina.setResultado(true);
			}

		} catch (final MalformedURLException e) {
			resultRotina.concatMensagem(UtilI18N.internacionaliza(req, "conexaoBI.testeConexaoErroUrlInvalida"));
		} catch (ConnectException e) {
			resultRotina.concatMensagem(UtilI18N.internacionaliza(req, "conexaoBI.testeConexaoFalhaConexaoNegada"));
		} catch (final IOException e) {
			resultRotina.concatMensagem(UtilI18N.internacionaliza(req, "conexaoBI.testeConexaoErroConexaoNaoEstabelecida"));
		} catch (JsonSyntaxException e) {
			resultRotina.concatMensagem(UtilI18N.internacionaliza(req, "conexaoBI.testeConexaoErroFalhaHTTP"));
		} catch (Exception ex) {
			ex.printStackTrace();
			resultRotina.concatMensagem(UtilI18N.internacionaliza(req, "Exceção identificada (FALHA)"));
		} catch (Error e) {
			e.printStackTrace();
			resultRotina.concatMensagem(UtilI18N.internacionaliza(req, "MSE02"));
		} finally {
			// Grava o log da importação.
			logImportacaoBI.setDataHoraFim(new Timestamp(System.currentTimeMillis()));
			logImportacaoBI.concatDetalhamento(resultRotina.getMensagem());
			logImportacaoBI.setTipo("T");
			logImportacaoBI.setIdConexaoBI(idConexaoBI);

			final LogImportacaoBIService logImportacaoBIService = (LogImportacaoBIService) ServiceLocator.getInstance().getService(LogImportacaoBIService.class, null);
			logImportacaoBIService.create(logImportacaoBI);
		}
		return resultRotina;
	}

	/**
	 * Executa a exportação manual das tabelas do BI do Citsmart
	 *
	 * @param path
	 * @return boolean
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	public boolean exportacaoManualBICitsmart(String path) throws Exception {
		boolean resultado = true;
		try {

			final String idConexaoBI = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.BICITSMART_ID_CONEXAO, "");
			if (idConexaoBI == null || idConexaoBI.equals("")) {
				resultado = false;
			} else {
				final String xmlString = StringEscapeUtils.unescapeHtml(BICitsmartUtils.recuperaXmlTabelasBICitsmart(true));

				if ((xmlString != null) && (xmlString.length() > 0)) {

					if (!path.substring(path.length() - 1).equals("/")) {
						path += "/";
					}

					final Calendar c = Calendar.getInstance();

					// Utiliza o caminho do Parametro "BI Citsmart - Caminho exportação manual" e concatena com o nome do arquivo.
					path = path + "bi_citsmart_exportacao_" + idConexaoBI + "_" + UtilDatas.getDataAtual() + "_" + c.get(Calendar.HOUR_OF_DAY) + "h" + c.get(Calendar.MINUTE) + ".xml";

					File file = new File(path);
					Writer out = new OutputStreamWriter(new FileOutputStream(file), "ISO-8859-1");
					out.write(xmlString);
					out.close();
				} else {
					resultado = false;
				}

			}

		} catch (Exception ex) {
			ex.printStackTrace();
			resultado = false;
		} catch (Error e) {
			e.printStackTrace();
			resultado = false;
		}

		return resultado;
	}

	/**
	 * Executa a exportação manual das tabelas do BI do Citsmart
	 *
	 * @param path
	 * @return boolean
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	public BICitsmartResultRotinaDTO exportacaoManualDownloadBICitsmart() throws Exception {
		BICitsmartResultRotinaDTO resultRotina = new BICitsmartResultRotinaDTO();

		try {
			final String idConexaoBI = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.BICITSMART_ID_CONEXAO, "");
			if (idConexaoBI == null || idConexaoBI.equals("")) {
				resultRotina.setResultado(false);
				resultRotina.setMensagem("Parâmetro ID Conexão BI não informado.");
			} else {
				final String xmlString = StringEscapeUtils.unescapeHtml(BICitsmartUtils.recuperaXmlTabelasBICitsmart(true));
				resultRotina.setResultado(true);
				resultRotina.setMensagem(xmlString);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			resultRotina.setResultado(false);
			resultRotina.setMensagem("MSG01");
		} catch (Error e) {
			e.printStackTrace();
			resultRotina.setResultado(false);
			resultRotina.setMensagem("MSG01");
		}

		return resultRotina;
	}

	/**
	 * Executa a importação manual das tabelas do BI do Citsmart
	 *
	 * @param idConexao
	 * @param xmlPath
	 * @return boolean
	 * @throws Exception
	 * @author rodrigo.acorse
	 */
	public boolean importacaoManualBICitsmart(final Integer idConexao, final String xmlPath) throws Exception {
		String xmlSource = "";
		boolean resultado = false;
		
		LogImportacaoBIDTO logImportacaoBI = new LogImportacaoBIDTO();
		logImportacaoBI.setDataHoraInicio(new Timestamp(new java.util.Date().getTime()));
		logImportacaoBI.setStatus("F");
		logImportacaoBI.clear();

		try {
			xmlSource = FileUtils.readFileToString(new File(xmlPath), "ISO-8859-1");
			if (!xmlSource.equals("")) {
				BICitsmartResultRotinaDTO resultPersistencia = this.persisteDadosBICitsmart(idConexao, xmlSource, true);

				logImportacaoBI.concatDetalhamento("Importação Manual BI Citsmart.");
				logImportacaoBI.concatDetalhamento(resultPersistencia.getMensagem());

				if (resultPersistencia.isResultado()) {
					logImportacaoBI.concatDetalhamento("Importação realizada com sucesso!");
					logImportacaoBI.setStatus("S");
					resultado = true;
				} else {
					logImportacaoBI.concatDetalhamento("Falha na execução da importação manual!");
				}
			} else {
				logImportacaoBI.concatDetalhamento("- Leitura do XML (Falha)");
			}
		} catch (IOException e) {
			e.printStackTrace();
			logImportacaoBI.concatDetalhamento("- Conversão do XML (Falha)");
		} catch (Exception ex) {
			ex.printStackTrace();
			logImportacaoBI.concatDetalhamento("Exceção identificada (FALHA)");
		} catch (Error e) {
			e.printStackTrace();
			logImportacaoBI.concatDetalhamento("Erro identificado (ERRO)");
		} finally {

			// Grava o log da importação.
			logImportacaoBI.setDataHoraFim(new Timestamp(System.currentTimeMillis()));
			logImportacaoBI.setTipo("M");
			logImportacaoBI.setIdConexaoBI(idConexao);

			final LogImportacaoBIService logImportacaoBIService = (LogImportacaoBIService) ServiceLocator.getInstance().getService(LogImportacaoBIService.class, null);
			logImportacaoBIService.create(logImportacaoBI);

			// Atualiza status da Conexão BI
			ConexaoBIDTO conexaoBIDTO = new ConexaoBIDTO();
			conexaoBIDTO.setIdConexaoBI(idConexao);
			final ConexaoBIService conexaoBIService = (ConexaoBIService) ServiceLocator.getInstance().getService(ConexaoBIService.class, null);
			conexaoBIDTO = (ConexaoBIDTO) conexaoBIService.restore(conexaoBIDTO);
			conexaoBIDTO.setStatus(logImportacaoBI.getStatus());
			if (resultado) {
				conexaoBIDTO.setDataHoraUltimaImportacao(logImportacaoBI.getDataHoraInicio());
			}
			conexaoBIService.update(conexaoBIDTO);
		}
		return resultado;
	}

	/**
	 * Executa a persistencia dos dados para o BI do Citsmart
	 *
	 * @param idConexao
	 * @param xml
	 * @return BICitsmartResultRotinaDTO
	 * @author rodrigo.acorse
	 * @throws ConnectionException
	 * @throws TransactionOperationException
	 */
	public BICitsmartResultRotinaDTO persisteDadosBICitsmart(Integer idConexao, String xml, boolean xmlComIdConexao) throws PersistenceException {
		BICitsmartResultRotinaDTO resultRotina = new BICitsmartResultRotinaDTO();
		TransactionControlerImpl transactionControler = new TransactionControlerImpl(Constantes.getValue("DATABASE_BI_ALIAS"));

		// euler.ramos
		// Declarando variáveis que serão utilizadas na iteração e, como são em sucessivas execuções, estavam onerando o Garbage Collection nas Declarações sucessivas
		ImportInfoRecord importInfoRecord;
		StringBuilder sqlInsert = new StringBuilder();
		StringBuilder sqlInsertFields = new StringBuilder();
		StringBuilder sqlInsertValues = new StringBuilder();
		StringBuilder sqlUpdate = new StringBuilder();
		StringBuilder sqlUpdateFields = new StringBuilder();
		StringBuilder sqlSelect = new StringBuilder();
		StringBuilder sqlWhere = new StringBuilder();
		List colRecordsGeral;
		List lstParmsInsert = new ArrayList();
		List lstParmsUpdate = new ArrayList();
		List lstParmsWhere = new ArrayList();
		List lst = new ArrayList();
		ImportInfoField importInfoField;
		String nomePrimeiroCampo;
		String typeDB;
		String field;
		String fieldValue;

		resultRotina.setResultado(true);

		try {

			// Obtendo objetos das tabelas e seus registros no XML
			colRecordsGeral = UtilImportData.readXMLSource(xml);

			transactionControler.start();

			JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_BI_ALIAS"), null);
			jdbcEngine.setTransactionControler(transactionControler);

			for (Iterator itRecords = colRecordsGeral.iterator(); itRecords.hasNext();) {
				importInfoRecord = (ImportInfoRecord) itRecords.next();
				if ((importInfoRecord.getTableName() != null) && (importInfoRecord.getTableName().trim().length() > 0)) {
					sqlInsertFields.delete(0, sqlInsertFields.length());
					sqlInsertValues.delete(0, sqlInsertValues.length());
					sqlUpdateFields.delete(0, sqlUpdateFields.length());
					sqlSelect.delete(0, sqlSelect.length());
					sqlWhere.delete(0, sqlWhere.length());

					lstParmsInsert.clear();
					lstParmsUpdate.clear();
					lstParmsWhere.clear();

					nomePrimeiroCampo = "";
					for (Iterator it = importInfoRecord.getColFields().iterator(); it.hasNext();) {
						importInfoField = (ImportInfoField) it.next();

						typeDB = importInfoField.getType();
						field = importInfoField.getNameField();
						fieldValue = importInfoField.getValueField();

						if (field.equalsIgnoreCase("IDCONEXAOBI") && Integer.parseInt(fieldValue) != idConexao) {
							transactionControler.rollback();
							resultRotina.setResultado(false);
							resultRotina.concatMensagem("- Persistência de dados (Falha): O ID da conexão no xml não corresponde a conexão selecionada.");
							break;
						} else {
							/*
							 * Rodrigo Pecci Acorse - 03/01/2014 10h15 - #128776 Remove o .000 dos campos numéricos para que a persistência seja feita corretamente.
							 */
							if (importInfoField.getType().startsWith("NUMERIC")) {
								if (fieldValue.indexOf(".000") > -1 && fieldValue.indexOf(".000") == fieldValue.length() - 4) {
									fieldValue = fieldValue.substring(0, fieldValue.length() - 4);
								}
							}

							if (nomePrimeiroCampo.trim().equalsIgnoreCase("")) {
								nomePrimeiroCampo = field;
							}

							if (sqlInsertFields.length() > 0) {
								sqlInsertFields.append(",");
							}
							sqlInsertFields.append(field);

							if (sqlInsertValues.length() > 0) {
								sqlInsertValues.append(",");
							}
							sqlInsertValues.append("?");

							if (importInfoField.isKey()) {
								if (sqlWhere.length() > 0) {
									sqlWhere.append(" AND ");
								}
								sqlWhere.append(field).append(" = ?");

								if (typeDB.startsWith("MONEY") || typeDB.startsWith("DOUBLE") || typeDB.startsWith("DECIMAL") || typeDB.startsWith("NUMERIC") || typeDB.startsWith("NUMBER")
										|| typeDB.startsWith("REAL") || typeDB.startsWith("FLOAT")) {
									lstParmsWhere.add(fieldValue);
								} else {
									lstParmsWhere.add(MetaUtil.convertType(importInfoField.getType(), fieldValue, null, null));
								}
							} else {
								if (sqlUpdateFields.length() > 0) {
									sqlUpdateFields.append(",");
								}
								sqlUpdateFields.append(field).append(" = ?");

								if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
									lstParmsUpdate.add(null);
								} else {
									if (typeDB.startsWith("MONEY") || typeDB.startsWith("DOUBLE") || typeDB.startsWith("DECIMAL") || typeDB.startsWith("NUMERIC") || typeDB.startsWith("NUMBER")
											|| typeDB.startsWith("REAL") || typeDB.startsWith("FLOAT")) {
										lstParmsUpdate.add(fieldValue);
									} else {
										lstParmsUpdate.add(MetaUtil.convertType(importInfoField.getType(), fieldValue, null, null));
									}
								}
							}

							if (importInfoField.getValueField().trim().equalsIgnoreCase("null")) {
								lstParmsInsert.add(null);
							} else {
								if (typeDB.startsWith("MONEY") || typeDB.startsWith("DOUBLE") || typeDB.startsWith("DECIMAL") || typeDB.startsWith("NUMERIC") || typeDB.startsWith("NUMBER")
										|| typeDB.startsWith("REAL") || typeDB.startsWith("FLOAT")) {
									lstParmsInsert.add(fieldValue);
								} else {
									lstParmsInsert.add(MetaUtil.convertType(importInfoField.getType(), fieldValue, null, null));
								}
							}
						}
					}

					if (resultRotina.isResultado()) {
						if (!xmlComIdConexao) {
							// Seta o IDCONEXAOBI para o Insert
							if (sqlInsertFields.length() > 0) {
								sqlInsertFields.append(",");
							}
							sqlInsertFields.append("IDCONEXAOBI");

							if (sqlInsertValues.length() > 0) {
								sqlInsertValues.append(",");
							}
							sqlInsertValues.append("?");

							lstParmsInsert.add(idConexao);

							// Seta o IDCONEXAOBI para o Select
							if (sqlWhere.length() > 0) {
								sqlWhere.append(" AND ");
							}
							sqlWhere.append("IDCONEXAOBI = ?");

							lstParmsWhere.add(idConexao);
						}

						lstParmsUpdate.addAll(lstParmsWhere);

						// Monta select final
						sqlSelect.append("SELECT ").append(nomePrimeiroCampo).append(" FROM ").append(importInfoRecord.getTableName()).append(" WHERE ").append(sqlWhere);

						lst.clear();
						lst = jdbcEngine.execSQL(sqlSelect.toString(), lstParmsWhere.toArray(), 0);
						if (lst == null || lst.size() == 0) {
							if (sqlInsertFields.length() > 0 && sqlInsertValues.length() > 0) {
								sqlInsert.delete(0, sqlInsert.length());
								sqlInsert.append("INSERT INTO ").append(importInfoRecord.getTableName()).append("(").append(sqlInsertFields.toString()).append(") VALUES (")
										.append(sqlInsertValues.toString()).append(")");
								jdbcEngine.execUpdate(sqlInsert.toString(), lstParmsInsert.toArray());
							}
						} else {
							if (sqlUpdateFields.length() > 0 && sqlWhere.length() > 0) {
								sqlUpdate.delete(0, sqlUpdate.length());
								sqlUpdate.append("UPDATE ").append(importInfoRecord.getTableName()).append(" SET ").append(sqlUpdateFields.toString()).append(" WHERE ").append(sqlWhere.toString());
								jdbcEngine.execUpdate(sqlUpdate.toString(), lstParmsUpdate.toArray());
							}
						}
					}
				}
			}

			if (resultRotina.isResultado()) {
				transactionControler.commit();
			}

		} catch (PersistenceException e) {

			e.printStackTrace();

			resultRotina.setResultado(false);
			resultRotina.concatMensagem("- Persistência de dados (Falha).");
			resultRotina.concatMensagem("INSERT: " + sqlInsert.toString() + " - " + lstParmsInsert);
			resultRotina.concatMensagem("UPDATE: " + sqlUpdate.toString() + " - " + lstParmsUpdate);
			resultRotina.concatMensagem("SELECT: " + sqlSelect.toString() + " - " + lstParmsWhere);

			return resultRotina;
		} catch (OutOfMemoryError ome) {
			resultRotina.setResultado(false);
			resultRotina.concatMensagem("- Out of memory error (ERRO)");
			if (transactionControler.isStarted()) {
				transactionControler.rollback();
			}
			ome.printStackTrace();
		} catch (Exception ex) {
			resultRotina.setResultado(false);
			resultRotina.concatMensagem("- Falha na Persistência de dados (FALHA)");
			if (transactionControler.isStarted()) {
				transactionControler.rollback();
			}
			ex.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
			resultRotina.setResultado(false);
			resultRotina.concatMensagem("- Erro na Persistência de dados (ERRO)");
		} finally {
			try {
				if (transactionControler.isStarted()) {
					transactionControler.close();
				}
			} catch (PersistenceException e) {
				e.printStackTrace();
			}
		}

		if (resultRotina.isResultado()) {
			resultRotina.concatMensagem("- Persistência de dados (OK)");
		}

		return resultRotina;
	}

}
