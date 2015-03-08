package br.com.centralit.citcorpore.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRPropertiesUtil;

import org.apache.log4j.Logger;

import br.com.centralit.bpm.batch.ThreadVerificaEventos;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.centralit.citcorpore.batch.MonitoraAsterisk;
import br.com.centralit.citcorpore.batch.MonitoraAtivosDiscovery;
import br.com.centralit.citcorpore.batch.MonitoraImportacaoDeDados;
import br.com.centralit.citcorpore.batch.MonitoraIncidentes;
import br.com.centralit.citcorpore.batch.ThreadCarregaXmlProcessamentoBatch;
import br.com.centralit.citcorpore.batch.ThreadIniciaGaleriaImagens;
import br.com.centralit.citcorpore.batch.ThreadIniciaProcessamentosBatch;
import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.PalavraGemeaDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.comm.server.ServidorSocket;
import br.com.centralit.citcorpore.metainfo.bean.BibliotecasExternasDTO;
import br.com.centralit.citcorpore.metainfo.bean.ExternalClassDTO;
import br.com.centralit.citcorpore.metainfo.negocio.BibliotecasExternasService;
import br.com.centralit.citcorpore.negocio.AnexoBaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.AtividadesServicoContratoService;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.ContadorAcessoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.PalavraGemeaService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoUsuarioService;
import br.com.centralit.citcorpore.negocio.ScriptsService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.negocio.VersaoService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.centralit.lucene.Lucene;
import br.com.centralit.nagios.MonitoraNagios;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.ConnectionProvider;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilTratamentoArquivos;
import br.com.citframework.util.XmlReadLookup;

@SuppressWarnings("unchecked")
public class CorporeContextListener implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(CorporeContextListener.class);

	@Override
	public void contextDestroyed(final ServletContextEvent event) {
	}

	/**
	 * Faz a inicializacao de elementos importantes do sistema.
	 */
	@Override
	public void contextInitialized(final ServletContextEvent event) {
		LOGGER.info("Iniciando CITSMart - Início");

		String dir = event.getServletContext().getRealPath("/");
		String strFinal = "";
		for (int i = 0; i < dir.length(); i++) {
			if (dir.charAt(i) == '\\') {
				strFinal = strFinal + "/";
			} else {
				strFinal = strFinal + dir.charAt(i);
			}
		}

		strFinal = strFinal.replaceAll("/./", "/");
		if (!strFinal.equalsIgnoreCase("") && strFinal.length() > 0) {
			if (strFinal.charAt(strFinal.length() - 1) != '\\' && strFinal.charAt(strFinal.length() - 1) != '/') {
				strFinal = strFinal + "/";
			}
		}

		dir = strFinal;

		final Properties props = System.getProperties();
		props.setProperty("user.dir", dir);
		CitAjaxUtil.CAMINHO_REAL_APP = dir;
		CITCorporeUtil.CAMINHO_REAL_APP = dir;

		LOGGER.info("CITSMart - Caminho da APP: " + dir);

		/*
		 * Rodrigo Pecci Acorse JBoss 4: Quando o jboss sobe como uma única instância: \jboss\server\default\conf\ Quando o jboss possui mais de uma instância: \jboss\server\<instancia>\conf\ JBoss 7:
		 * Outra propriedade é utilizada para recuperar o diretório de configuração. Quando o jboss sobe como uma única instância: \jboss\standalone\configuration\ Quando é utilizado cluster, tem
		 * domains e hosts: \jboss\domain\configuration\
		 */
		try {
			String serverConf = "";
			File fConf;

			serverConf = System.getProperty("jboss.server.config.dir");
			fConf = new File(serverConf + "/citsmart.cfg");

			if (!fConf.exists()) {
				serverConf = System.getProperty("jboss.server.config.url");
				fConf = new File(serverConf + "/citsmart.cfg");
			}

			if (fConf.exists()) {
				CITCorporeUtil.CAMINHO_REAL_CONFIG_FILE = fConf.getAbsolutePath();
			} else {
				final String searchFind = "server";
				int indexSearchWord = dir.indexOf(searchFind);
				indexSearchWord = dir.indexOf("/", indexSearchWord + searchFind.length() + 1);

				// Determina o diretorio de config, que pode ser /default/conf ou /all/conf ou /minimal/conf ou outro/<dir name>/conf
				String pathConfigStartMode = dir.substring(0, indexSearchWord);
				pathConfigStartMode += "/conf/citsmart.cfg";
				CITCorporeUtil.CAMINHO_REAL_CONFIG_FILE = pathConfigStartMode;
			}
		} catch (final Exception e) {
			final String searchFind = "server";
			int indexSearchWord = dir.indexOf(searchFind);
			indexSearchWord = dir.indexOf("/", indexSearchWord + searchFind.length() + 1);

			// Determina o diretorio de config, que pode ser /default/conf ou /all/conf ou /minimal/conf ou outro/<dir name>/conf
			String pathConfigStartMode = dir.substring(0, indexSearchWord);
			pathConfigStartMode += "/conf/citsmart.cfg";
			CITCorporeUtil.CAMINHO_REAL_CONFIG_FILE = pathConfigStartMode;
		}

		CITCorporeUtil.fazLeituraArquivoConfiguracao();

		CITCorporeUtil.LIST_EXTERNAL_CLASSES = new ArrayList<>();
		CITCorporeUtil.HSM_EXTERNAL_CLASSES = new HashMap<>();

		LOGGER.info("CITSMart - Caminho server: " + CITCorporeUtil.CAMINHO_SERVIDOR);

		CITCorporeUtil.SGBD_PRINCIPAL = AdaptacaoBD.getBancoUtilizado(); // este metodo atualizada o valor de CITCorporeUtil.SGBD_PRINCIPAL
		LOGGER.info("CITSMart - SGBD da conexao principal: " + CITCorporeUtil.SGBD_PRINCIPAL);

		final String packageName = "br.com.centralit.citcorpore.metainfo.complementos";
		final ClassLoader classLoader = br.com.centralit.citcorpore.metainfo.complementos.ComplementoSLA.class.getClassLoader();
		final String path = packageName.replace('.', '/');
		try {
			final Enumeration<URL> resources = classLoader.getResources(path);
			final List<File> dirs = new ArrayList<File>();
			while (resources.hasMoreElements()) {
				final URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			final List<ExternalClassDTO> classes = new ArrayList<>();
			for (final File directory : dirs) {
				try {
					String temp = directory.getAbsolutePath();
					temp = temp.replaceAll("%20", " ");
					final File fileNovo = new File(temp);
					classes.addAll(findClasses(fileNovo, packageName));
				} catch (final ClassNotFoundException e) {
					LOGGER.warn(e.getMessage(), e);
				}
			}
		} catch (final IOException e) {
			LOGGER.warn(e.getMessage(), e);
		}

		final ServletContext context = event.getServletContext();

		this.iniciarProcessoInstalacao(context);

		this.iniciarRotinaDeScripts();

		this.carregarParametros();

		LOGGER.info("CITSMart - Carregando Dicionários - Início");
		UtilI18N.initialize();
		LOGGER.info("CITSMart - Carregando Dicionários - Fim");

		this.carregarBibliotecasExternas();

		this.criarUsuarioConsultor();

		XmlReadLookup.getInstance();

		this.realizarCargaDeMenus();

		final ThreadCarregaXmlProcessamentoBatch threadCarregaXmlProcessamentoBatch = new ThreadCarregaXmlProcessamentoBatch();
		threadCarregaXmlProcessamentoBatch.start();

		final ThreadIniciaProcessamentosBatch thread = new ThreadIniciaProcessamentosBatch();
		thread.start();

		final ThreadIniciaGaleriaImagens threadIniciaGaleriaImagens = new ThreadIniciaGaleriaImagens();
		threadIniciaGaleriaImagens.start();

		if (CITCorporeUtil.START_INVENTARIO_ANTIGO) {
			final ServidorSocket servidor = new ServidorSocket();
			servidor.start();
		}

		if (CITCorporeUtil.START_MONITORA_NAGIOS) {
			final MonitoraNagios monitoraNagios = new MonitoraNagios();
			monitoraNagios.start();
		}

		if (CITCorporeUtil.START_MONITORA_DISCOVERY) {
			final MonitoraAtivosDiscovery monitoraAtivosDiscovery = new MonitoraAtivosDiscovery();
			monitoraAtivosDiscovery.start();
		}

		if (CITCorporeUtil.START_MONITORA_INCIDENTES) {
			final MonitoraIncidentes monitoraIncidentes = new MonitoraIncidentes();
			monitoraIncidentes.start();
		}

		if (CITCorporeUtil.START_VERIFICA_EVENTOS) {
			final ThreadVerificaEventos threadEventos = new ThreadVerificaEventos();
			threadEventos.start();
		}

		this.definirParametrosJasperReport();

		if (ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKATIVAR, "N").equals("S")) {
			final MonitoraAsterisk monitoraAsterisk = new MonitoraAsterisk();
			monitoraAsterisk.start();
		}

		this.criarAtualizarIndexPesquisaLucene();

		this.atualizarFormulasAntigasOrdemDeServico();

		LOGGER.info("Iniciando Citsmart - Fim");
	}

	/**
	 * Carrega bibliotecas externas.
	 */
	private void carregarBibliotecasExternas() {
		LOGGER.info("CITSMart - Carregando bibliotecas externas - Início");

		try {
			final BibliotecasExternasService bibliotecasExternasService = (BibliotecasExternasService) ServiceLocator.getInstance().getService(BibliotecasExternasService.class, null);
			final Collection<BibliotecasExternasDTO> colLibs = bibliotecasExternasService.list();
			if (colLibs != null) {
				for (final BibliotecasExternasDTO bibliotecasExternasDTO : colLibs) {
					try {
						final JarFile jarFile = new JarFile(bibliotecasExternasDTO.getCaminho());
						this.generateClassesFromLib(jarFile, bibliotecasExternasDTO.getCaminho(), bibliotecasExternasDTO.getIdBibliotecasExterna());
					} catch (final Exception e) {
						LOGGER.warn(String.format("CITSMart - Problema ao carregar biblioteca externa %s: %s", bibliotecasExternasDTO.getCaminho(), e.getMessage()), e);
					}
				}
			}
		} catch (final Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}

		LOGGER.info("CITSMart - Carregando bibliotecas externas - Fim");
	}

	/**
	 * Realizar carga de menus à partir do XML.
	 */
	private void realizarCargaDeMenus() {

		if (ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LER_ARQUIVO_PADRAO_XML_MENUS, "S").trim().equalsIgnoreCase("S")) {
			LOGGER.info("CITSMart - Carga do menu pelo arquivo xml padrão - Início");
			try {
				final MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);

				if (menuService != null) {
					final String separator = System.getProperty("file.separator");
					final String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs" + separator;

					final File file = new File(diretorioReceita + "menu.xml");

					menuService.gerarCarga(file);
					menuService.deletaMenusSemReferencia();
				}

				final PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
				perfilAcessoMenuService.atualizaPerfis();
			} catch (final Exception e) {
				LOGGER.warn("CITSMart - Erro ao carregar o arquivo xml padrão de menu: ", e);
			}
			LOGGER.info("CITSMart - Carga do menu pelo arquivo xml padrão - Fim");
		}
	}

	/**
	 * Define parametros do JasperReport Caso não haja fonte no servidor para imprimir relatório, não vai dar exception
	 */
	private void definirParametrosJasperReport() {
		final String fontePadrao = "SansSerif";
		final DefaultJasperReportsContext contexto = DefaultJasperReportsContext.getInstance();
		JRPropertiesUtil.getInstance(contexto).setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
		JRPropertiesUtil.getInstance(contexto).setProperty("net.sf.jasperreports.default.font.name", fontePadrao);
	}

	/**
	 * Verifica se ja foi feito a rotina de atualizar as fórmulas antigas das Ordem de Serviço
	 * 
	 * @author bruno.aquino
	 */
	private void atualizarFormulasAntigasOrdemDeServico() {

		if (ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.RECONFIGURAR_FORMULASOS_DASATIVIDADES, "S").equalsIgnoreCase("S")) {
			try {
				this.reconfigurarFormulaOS();
				final ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
				ParametroCorporeDTO parametro = new ParametroCorporeDTO();
				parametro.setId(229);
				parametro = (ParametroCorporeDTO) parametroCorporeService.restore(parametro);
				parametro.setValor("N");
				parametroCorporeService.update(parametro);
			} catch (final Exception e) {
				LOGGER.warn("Problema na atualizaçâo das formulas da OS: " + e.getMessage(), e);
			}
		}
	}

	/**
	 * Cria ou refaz todos os índices da pesquisa Lucene; será executado apenas uma vez ou quando o usuário setar "S" SIM para o parâmetro: LUCENE_REFAZER_INDICES
	 *
	 * @author euler.ramos;
	 */
	private void criarAtualizarIndexPesquisaLucene() {
		try {
			final String refazerIndicesLucene = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LUCENE_REFAZER_INDICES, "S");
			if (refazerIndicesLucene.isEmpty() || refazerIndicesLucene.equals("S")) {
				LOGGER.info("Realizando reindexação do Lucene - Início");
				Lucene lucene = new Lucene();
				Util.deleteDiretorioAndSubdiretorios(lucene.getDirBaseConhecimento()); // Destruindo índices corrompidos, quando o usuário solicita uma reindexação.
				Util.deleteDiretorioAndSubdiretorios(lucene.getDirAnexos());
				Util.createDiretorio(lucene.getDirBaseConhecimento());
				Util.createDiretorio(lucene.getDirAnexos());
				LOGGER.info("Realizando reindexação do Lucene - Base Conhecimento e Anexos ...");
				final BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
				final AnexoBaseConhecimentoService anexoBaseConhecimentoService = (AnexoBaseConhecimentoService) ServiceLocator.getInstance().getService(AnexoBaseConhecimentoService.class, null);
				final ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
				final Collection<BaseConhecimentoDTO> listaBaseConhecimento = baseConhecimentoService.listarBasesConhecimentoPublicadas();

				// Tive que criar este "for" porque o Service não traz o DTO alimentado completamente em todos os campos
				for (final BaseConhecimentoDTO baseConhecimentoDTO : listaBaseConhecimento) {
					// Avaliação - Média da nota dada pelos usuários
					final Double media = baseConhecimentoService.calcularNota(baseConhecimentoDTO.getIdBaseConhecimento());
					if (media != null) {
						baseConhecimentoDTO.setMedia(media.toString());
					} else {
						baseConhecimentoDTO.setMedia(null);
					}
					final ContadorAcessoService contadorAcessoService = (ContadorAcessoService) ServiceLocator.getInstance().getService(ContadorAcessoService.class, null);

					// Qtde de cliques
					final Integer quantidadeDeCliques = contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(baseConhecimentoDTO);
					if (quantidadeDeCliques != null) {
						baseConhecimentoDTO.setContadorCliques(quantidadeDeCliques);
					} else {
						baseConhecimentoDTO.setContadorCliques(0);
					}

					final List<AnexoBaseConhecimentoDTO> listaAnexosBaseConhecimento = (List<AnexoBaseConhecimentoDTO>) anexoBaseConhecimentoService
							.consultarAnexosDaBaseConhecimento(baseConhecimentoDTO);

					if (listaAnexosBaseConhecimento != null) {
						// Obtendo o conteúdo do Anexo diretamente do arquivo
						for (final AnexoBaseConhecimentoDTO anexoBaseConhecimento : listaAnexosBaseConhecimento) {
							final ControleGEDDTO controleGEDDTO = controleGEDService.getControleGED(anexoBaseConhecimento);
							if (controleGEDDTO != null && controleGEDDTO.getIdControleGED() != null && controleGEDDTO.getIdControleGED() > 0 && controleGEDDTO.getNomeArquivo() != null
									&& controleGEDDTO.getPasta() != null && controleGEDDTO.getExtensaoArquivo() != null) {
								// Obtendo o conteúdo do arquivo armazenado em disco! O Service não traz este campo preenchido no list
								try {
									final Arquivo arquivo = new Arquivo(controleGEDDTO);
									anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
								} catch (final Exception e) {
									LOGGER.warn(e.getMessage(), e);
								}
							}
						}
					}
					lucene.indexarBaseConhecimento(baseConhecimentoDTO, listaAnexosBaseConhecimento);
				}

				LOGGER.info("Realizando reindexação do Lucene - Palavras gemeas...");
				final PalavraGemeaService palavraGemeaService = (PalavraGemeaService) ServiceLocator.getInstance().getService(PalavraGemeaService.class, null);
				final Collection<PalavraGemeaDTO> listaPalavrasGemeas = palavraGemeaService.list();
				lucene = new Lucene();
				Util.deleteDiretorioAndSubdiretorios(lucene.getDirGemeas()); // Destruindo índices corrompidos, quando o usuário solicita uma reindexação.
				Util.createDiretorio(lucene.getDirGemeas());
				lucene.indexarListaPalavrasGemeas(listaPalavrasGemeas);

				final ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
				final ParametroCorporeDTO parametroCorporeDTO = parametroCorporeService.getParamentroAtivo(ParametroSistema.LUCENE_REFAZER_INDICES.id());
				parametroCorporeDTO.setValor("N");
				parametroCorporeService.atualizarParametros(parametroCorporeDTO);
				LOGGER.info("Realizando reindexação do Lucene - Fim");
			}

			/**
			 * Thread para importar os dados (ImportacaoDeDados)
			 */
			final String importaDados = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.HABILITAR_MIGRACAO_DE_DADOS_AUTOMATICA, "N");

			if (importaDados.equalsIgnoreCase("S")) {
				final Thread importacaoDeDados = new Thread(new MonitoraImportacaoDeDados());
				importacaoDeDados.start();
			}
		} catch (final Exception e) {
			LOGGER.warn("Problema na indexação Lucene: " + e.getMessage(), e);
		}
	}

	/**
	 * Atualiza, Cria e Carrega Parâmetros do Sistema.
	 */
	private void carregarParametros() {
		LOGGER.info("CITSMart - Criando parametros novos - Início");
		try {
			final ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
			parametroService.criarParametrosNovos();
		} catch (final Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		LOGGER.info("CITSMart - Criando parametros novos - Fim");
	}

	/**
	 * Aplica Scripts Novos de BD em atualização de versão.
	 */
	private void iniciarRotinaDeScripts() {
		LOGGER.info("CITSMart - Executando rotina de scripts - Início");
		try {
			final ScriptsService scriptsService = (ScriptsService) ServiceLocator.getInstance().getService(ScriptsService.class, null);
			final VersaoService versaoService = (VersaoService) ServiceLocator.getInstance().getService(VersaoService.class, null);

			final String erro = scriptsService.executaRotinaScripts();
			if (erro != null && !erro.isEmpty()) {
				LOGGER.info("CITSMart - Problema ao executar rotina. Detalhes:\n" + erro);
			}
			FiltroSegurancaCITSmart.setHaVersoesSemValidacao(versaoService.haVersoesSemValidacao());
		} catch (final Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
		LOGGER.info("CITSMart - Executando rotina de scripts - Fim");
	}

	/**
	 * Tratando a existencia de tabelas Adiciona na sessão o parametro de instalação
	 *
	 * @author flavio.santana
	 */
	private void iniciarProcessoInstalacao(final ServletContext context) {
		try (Connection conn = ConnectionProvider.getConnection(Constantes.getValue("DATABASE_ALIAS"));) {
			final DatabaseMetaData db_md = conn.getMetaData();
			ResultSet res = null;
			if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
				/*
				 * Rodrigo Pecci Acorse - 06/12/2013 15h30 - #126457 Adiciona o usuário utilizado na conexão para garantir que o oracle não irá olhar para outras tabelas.
				 */
				String userName = null;

				userName = conn.getMetaData().getUserName();
				if (userName.equals("")) {
					userName = null;
				}

				res = db_md.getTables(null, userName, "PARAMETROCORPORE", new String[] { "TABLE" });
			} else {
				res = db_md.getTables(null, null, "parametrocorpore", new String[] { "TABLE" });
			}

			if (!res.next()) {
				context.setAttribute("instalacao", new ServletException("CITSMart Instalacao"));
			}

			final Map<Integer, String> dadosSGBD = new HashMap<>();
			dadosSGBD.put(1, CITCorporeUtil.SGBD_PRINCIPAL);
			dadosSGBD.put(2, conn.getMetaData().getURL());
			dadosSGBD.put(3, conn.getMetaData().getSchemaTerm());
			context.setAttribute("dadosSGBD", dadosSGBD);
		} catch (final Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
	}

	/**
	 * Cria usuário Consultor caso não exista.
	 */
	private void criarUsuarioConsultor() {
		try {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			usuarioDTO.setLogin("consultor");
			usuarioDTO = usuarioService.listLogin(usuarioDTO);
			EmpregadoDTO empregadoDTO = new EmpregadoDTO();
			final PerfilAcessoUsuarioDTO perfilAcessoUsuarioDTO = new PerfilAcessoUsuarioDTO();
			final PerfilAcessoUsuarioService perfilAcessoUsuarioService = (PerfilAcessoUsuarioService) ServiceLocator.getInstance().getService(PerfilAcessoUsuarioService.class, null);
			final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			if (usuarioDTO == null) {
				usuarioDTO = new UsuarioDTO();
				empregadoDTO.setNome("Consultor");
				empregadoDTO.setDataCadastro(UtilDatas.getDataAtual());
				empregadoDTO.setNomeProcura("Consultor");
				empregadoDTO.setTelefone("não disponivel");
				empregadoDTO.setIdSituacaoFuncional(1);
				empregadoDTO.setEmail("consultoria@centralit.com.br");
				empregadoDTO = empregadoService.create(empregadoDTO);
				String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
				if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
					algoritmo = "SHA-1";
				}
				usuarioDTO.setLogin("consultor");
				usuarioDTO.setNomeUsuario("Consultor");
				usuarioDTO.setSenha(CriptoUtils.generateHash("admgoiania516", algoritmo));
				usuarioDTO.setDataInicio(UtilDatas.getDataAtual());
				usuarioDTO.setIdEmpregado(empregadoDTO.getIdEmpregado());
				usuarioDTO.setIdEmpresa(1);
				usuarioDTO.setStatus("A");
				usuarioService.create(usuarioDTO);
				perfilAcessoUsuarioDTO.setDataInicio(UtilDatas.getDataAtual());
				perfilAcessoUsuarioDTO.setIdPerfilAcesso(1);
				perfilAcessoUsuarioDTO.setIdUsuario(usuarioDTO.getIdUsuario());
				perfilAcessoUsuarioService.create(perfilAcessoUsuarioDTO);
			}
		} catch (final Exception e) {
			LOGGER.warn(e.getMessage(), e);
		}
	}

	private void generateClassesFromLib(final JarFile jar, final String pathJar, final Integer control) {
		final Enumeration<JarEntry> e = jar.entries();
		while (e.hasMoreElements()) {
			final JarEntry jarFile = e.nextElement();
			if (jarFile.getName().indexOf(".class") != -1) {
				final ExternalClassDTO externalClassDTO = new ExternalClassDTO();

				final String newNameJar = CITCorporeUtil.CAMINHO_REAL_APP + "WEB-INF/lib/LIBEXTCITCORPORE_" + control + ".jar";
				UtilTratamentoArquivos.copyFile(pathJar, newNameJar);

				externalClassDTO.setNameJar(newNameJar);
				externalClassDTO.setNameJarOriginal(pathJar);
				externalClassDTO.setNameClass(jarFile.getName());

				if (externalClassDTO.getNameClass() != null) {
					externalClassDTO.setNameClass(externalClassDTO.getNameClass().replaceAll("/", "."));
					externalClassDTO.setNameClass(externalClassDTO.getNameClass().replaceAll(".class", ""));
				}

				CITCorporeUtil.LIST_EXTERNAL_CLASSES.add(externalClassDTO);

				CITCorporeUtil.HSM_EXTERNAL_CLASSES.put(externalClassDTO.getNameClass(), newNameJar);
			}
		}
	}

	public void reconfigurarFormulaOS() throws ServiceException, Exception {
		final AtividadesServicoContratoService atividadesServicoContratoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class,
				null);

		final List<AtividadesServicoContratoDTO> lista = (List<AtividadesServicoContratoDTO>) atividadesServicoContratoService.listarPorFormula();

		if (lista != null && !lista.isEmpty()) {
			for (AtividadesServicoContratoDTO atividadesServicoContratoDTO : lista) {
				final String formulaBruta = atividadesServicoContratoDTO.getFormula();
				final String array[] = formulaBruta.split("x");

				String arrayAux[] = array;
				String[] arrayAuxFormula = null;
				String formula = "";
				String estruturaFormula = "";
				String formulaFinal = "";
				double quantidade = 0;
				if (!(atividadesServicoContratoDTO.getQuantidade() == null)) {
					quantidade = atividadesServicoContratoDTO.getQuantidade();
				}

				// fórmula que será visualizada
				if (atividadesServicoContratoDTO.getPeriodo() != null && atividadesServicoContratoDTO.getPeriodo().equalsIgnoreCase("5")) {
					arrayAux[2] = "Dias Corridos";
				} else if (atividadesServicoContratoDTO.getPeriodo() != null && atividadesServicoContratoDTO.getPeriodo().equalsIgnoreCase("4")) {
					arrayAux[2] = "Dias Úteis";
				}
				formula = arrayAux[0] + " * " + arrayAux[1] + " * " + arrayAux[2];

				// Estrutura da fórmula
				arrayAux = formulaBruta.split("x");
				arrayAux[0] = "vValor{horas}";
				arrayAux[1] = "vComplexidade";

				if (atividadesServicoContratoDTO.getPeriodo() != null && atividadesServicoContratoDTO.getPeriodo().equalsIgnoreCase("5")) {
					arrayAux[2] = "vDiasCorridos";

				} else if (atividadesServicoContratoDTO.getPeriodo() != null && atividadesServicoContratoDTO.getPeriodo().equalsIgnoreCase("4")) {
					arrayAux[2] = "vDiasUteis";

				} else {
					arrayAuxFormula = arrayAux[2].trim().split(" ");
					arrayAuxFormula[0] = "{Quantidade}vValor";
					arrayAux[2] = arrayAuxFormula[0] + "{Periodo}{" + arrayAuxFormula[1] + "}";
				}

				estruturaFormula = arrayAux[0] + "*" + arrayAux[1] + "*" + arrayAux[2];

				// Fórmula para cálculo
				arrayAux = formulaBruta.split("x");
				arrayAux[1] = atividadesServicoContratoDTO.getComplexidade();
				if (quantidade == 0) {
					if (atividadesServicoContratoDTO.getPeriodo() != null && atividadesServicoContratoDTO.getPeriodo().equalsIgnoreCase("5")) {
						arrayAux[2] = "vDiasCorridos";
					} else if (atividadesServicoContratoDTO.getPeriodo() != null && atividadesServicoContratoDTO.getPeriodo().equalsIgnoreCase("4")) {
						arrayAux[2] = "vDiasUteis";
					} else {
						arrayAux[2] = "";
					}
				} else {
					if (atividadesServicoContratoDTO.getPeriodo() != null && atividadesServicoContratoDTO.getPeriodo().equalsIgnoreCase("5")) {
						arrayAux[2] = "vDiasCorridos";
					} else if (atividadesServicoContratoDTO.getPeriodo() != null && atividadesServicoContratoDTO.getPeriodo().equalsIgnoreCase("4")) {
						arrayAux[2] = "vDiasUteis";
					} else {
						arrayAuxFormula = arrayAux[2].trim().split(" ");
						arrayAux[2] = arrayAuxFormula[0];
					}
				}
				formulaFinal = arrayAux[0] + "*" + arrayAux[1] + "*" + arrayAux[2];

				atividadesServicoContratoDTO = (AtividadesServicoContratoDTO) atividadesServicoContratoService.restore(atividadesServicoContratoDTO);
				atividadesServicoContratoDTO.setEstruturaFormulaOs(estruturaFormula);
				atividadesServicoContratoDTO.setFormula(formula);
				atividadesServicoContratoDTO.setFormulaCalculoFinal(formulaFinal);
				atividadesServicoContratoService.update(atividadesServicoContratoDTO);
			}
		}
	}

	private static List<ExternalClassDTO> findClasses(final File directory, final String packageName) throws ClassNotFoundException {
		final List<ExternalClassDTO> classes = new ArrayList<>();
		if (!directory.exists()) {
			return classes;
		}
		final File[] files = directory.listFiles();
		for (final File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				final ExternalClassDTO externalClassDTO = new ExternalClassDTO();
				externalClassDTO.setNameClass(packageName + '.' + file.getName());
				externalClassDTO.setNameClass(externalClassDTO.getNameClass().replaceAll("/", "."));
				externalClassDTO.setNameJar("CLASSES");
				externalClassDTO.setNameJarOriginal("CLASSES");

				CITCorporeUtil.LIST_EXTERNAL_CLASSES.add(externalClassDTO);

				CITCorporeUtil.HSM_EXTERNAL_CLASSES.put(externalClassDTO.getNameClass(), "CLASSES");
			}
		}
		return classes;
	}

}
