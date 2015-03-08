package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.TesteCITSmartDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContatoSolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoServiceEjb;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.TesteCITSMartService;
import br.com.centralit.citcorpore.negocio.TesteCITSmartEjb;
import br.com.centralit.citcorpore.negocio.UsuarioServiceEjb;
import br.com.centralit.citcorpore.teste.AlcadaCentroResultadoTest;
import br.com.centralit.citcorpore.teste.AnexoTest;
import br.com.centralit.citcorpore.teste.AtividadePeriodicaTest;
import br.com.centralit.citcorpore.teste.AtribuidaCompartilhadaTest;
import br.com.centralit.citcorpore.teste.BaseConhecimentoTest;
import br.com.centralit.citcorpore.teste.BuscaContratoSolicitacaoTest;
import br.com.centralit.citcorpore.teste.CalendarioTest;
import br.com.centralit.citcorpore.teste.CapturaTarefaTest;
import br.com.centralit.citcorpore.teste.CaracteristicaTest;
import br.com.centralit.citcorpore.teste.CargosTest;
import br.com.centralit.citcorpore.teste.CatalogoServicoTest;
import br.com.centralit.citcorpore.teste.CategoriaPostTest;
import br.com.centralit.citcorpore.teste.CategoriaProdutoTest;
import br.com.centralit.citcorpore.teste.CategoriaServicoTest;
import br.com.centralit.citcorpore.teste.CategoriaSolucaoTest;
import br.com.centralit.citcorpore.teste.CausaIncidenteTest;
import br.com.centralit.citcorpore.teste.CentroResultadoTest;
import br.com.centralit.citcorpore.teste.ClienteTest;
import br.com.centralit.citcorpore.teste.ComandoSistemaOperacionalTest;
import br.com.centralit.citcorpore.teste.ComandoTest;
import br.com.centralit.citcorpore.teste.CondicaoOperacaoTest;
import br.com.centralit.citcorpore.teste.ContatoSolicitacaoTest;
import br.com.centralit.citcorpore.teste.ContratoTest;
import br.com.centralit.citcorpore.teste.DelegacaoTarefaTest;
import br.com.centralit.citcorpore.teste.EmpregadoTest;
import br.com.centralit.citcorpore.teste.EmpresaTest;
import br.com.centralit.citcorpore.teste.EventoTest;
import br.com.centralit.citcorpore.teste.ExecutarTarefaTest;
import br.com.centralit.citcorpore.teste.FornecedorTest;
import br.com.centralit.citcorpore.teste.GrupoAtvPeriodicaTest;
import br.com.centralit.citcorpore.teste.GrupoTest;
import br.com.centralit.citcorpore.teste.ImportanciaNegocioTest;
import br.com.centralit.citcorpore.teste.IniciarTarefaTest;
import br.com.centralit.citcorpore.teste.ItemConfiguracaoTest;
import br.com.centralit.citcorpore.teste.JornadaTrabalhoTest;
import br.com.centralit.citcorpore.teste.JustificativaFalhasTest;
import br.com.centralit.citcorpore.teste.JustificativaParecerTest;
import br.com.centralit.citcorpore.teste.LocalidadeTest;
import br.com.centralit.citcorpore.teste.MarcaTest;
import br.com.centralit.citcorpore.teste.MenuTest;
import br.com.centralit.citcorpore.teste.ModeloEmailTest;
import br.com.centralit.citcorpore.teste.MudancaTest;
import br.com.centralit.citcorpore.teste.MudarSLATest;
import br.com.centralit.citcorpore.teste.OcorrenciaSolicitacaoTest;
import br.com.centralit.citcorpore.teste.PalavraGemeaTest;
import br.com.centralit.citcorpore.teste.PastaTest;
import br.com.centralit.citcorpore.teste.PerfilAcessoTest;
import br.com.centralit.citcorpore.teste.PesquisaSolicitacaoTest;
import br.com.centralit.citcorpore.teste.PrioridadeTest;
import br.com.centralit.citcorpore.teste.ProblemaTest;
import br.com.centralit.citcorpore.teste.ProdutoTest;
import br.com.centralit.citcorpore.teste.ReativaSolicitacaoTest;
import br.com.centralit.citcorpore.teste.ReclassificarSolicitacaoTest;
import br.com.centralit.citcorpore.teste.RegistroExecucaoTest;
import br.com.centralit.citcorpore.teste.RequisicaoProdutoTest;
import br.com.centralit.citcorpore.teste.SistemaOperacionalTest;
import br.com.centralit.citcorpore.teste.SoftwareInsDesTest;
import br.com.centralit.citcorpore.teste.SolicitacaoServicoMultiContratosTest;
import br.com.centralit.citcorpore.teste.SolicitacaoServicoTest;
import br.com.centralit.citcorpore.teste.SubSolicitacaoTest;
import br.com.centralit.citcorpore.teste.SuspenderSolicitacaoTest;
import br.com.centralit.citcorpore.teste.TipoEventoServicoTest;
import br.com.centralit.citcorpore.teste.TipoItemConfiguracaoTest;
import br.com.centralit.citcorpore.teste.TipoServicoTest;
import br.com.centralit.citcorpore.teste.TipoUnidadeTest;
import br.com.centralit.citcorpore.teste.UnidadeMedidaTest;
import br.com.centralit.citcorpore.teste.UnidadeTest;
import br.com.centralit.citcorpore.teste.UsuarioTest;
import br.com.centralit.citcorpore.teste.VerificaEmailTest;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.UtilDatas;

public class TesteCITSmart extends AjaxFormAction {
	private UsuarioTest testUsuario;
	private SolicitacaoServicoTest testSolicicao;
	private OcorrenciaSolicitacaoTest testOcorrencia;
	private ContatoSolicitacaoTest testContato;
	private RegistroExecucaoTest testRegistroExecucao;
	private ReclassificarSolicitacaoTest testReclassifica;
	private AnexoTest testAnexo;
	private BaseConhecimentoTest testBaseConhecimento;
	private AtribuidaCompartilhadaTest testAtribuidaCompartilhada;
	private BuscaContratoSolicitacaoTest testBusca;
	private AtividadePeriodicaTest testAtividade;
	private CapturaTarefaTest testCaptura;
	private DelegacaoTarefaTest testDelegacao;
	private EmpregadoTest testEmpregado;
	private ExecutarTarefaTest testExecuta;
	private IniciarTarefaTest testInicia;
	private MudarSLATest testMudaSLA;
	private ReativaSolicitacaoTest testReativa;
	private ContratoTest testServicoContrato;
	private SuspenderSolicitacaoTest testSuspender;
	private ProblemaTest testProblema;
	private SubSolicitacaoTest testSubSolicitacao;
	private MudancaTest testRequisicaoMudanca;
	private VerificaEmailTest testVerificaEmail;
	private ClienteTest testCliente;
	private FornecedorTest testFornecedor;
	private MenuTest testMenu;
	private MarcaTest testMarca;
	private ProdutoTest testProduto;
	private EmpresaTest testEmpresa;
	private GrupoTest testGrupo;
	private CargosTest testCargos;
	private UnidadeTest testUnidade;
	private JornadaTrabalhoTest testJornadaTrabalho;
	private LocalidadeTest testLocalidade;
	private PerfilAcessoTest testPerfilAcesso;
	private CalendarioTest testCalendario;
	private TipoUnidadeTest testTipoUnidade;
	private ComandoTest testComando;
	private SistemaOperacionalTest testSistemaOperacional;
	private ComandoSistemaOperacionalTest testComandoSistemaOperacional;
	private TipoEventoServicoTest testTipoEventoServico;
	private JustificativaFalhasTest testJustificativaFalhas;
	private ImportanciaNegocioTest testImportanciaNegocio;
	private PrioridadeTest testPrioridade;
	private CausaIncidenteTest testCausaIncidente;
	private GrupoAtvPeriodicaTest testGrupoAtvPeriodica;
	private ModeloEmailTest testModeloEmail;
	private CategoriaSolucaoTest testCategoriaSolucao;
	private CategoriaPostTest testCategoriaPost;
	private AlcadaCentroResultadoTest testAlcadaCentroResultado;
	private CategoriaProdutoTest testCategoriaProduto;
	private CondicaoOperacaoTest testCondicaoOperacao;
	private JustificativaParecerTest testJustificativaParecer;
	private UnidadeMedidaTest testUnidadeMedida;
	private RequisicaoProdutoTest testRequisicaoProduto;
	private CentroResultadoTest testCentroResultado;
	private PastaTest testPasta;
	private PalavraGemeaTest testPalavraGemea;
	private CatalogoServicoTest testCatalogoServico;
	private TipoServicoTest testTipoServico;
	private CategoriaServicoTest testCategoriaServico;
	private ItemConfiguracaoTest testItemConfiguracao;
	private TipoItemConfiguracaoTest testTipoItemConfiguracao;
	private CaracteristicaTest testCaracteristica;
	private SolicitacaoServicoMultiContratosTest testMultiContrato;
	private SoftwareInsDesTest testSoftwareInsDes;
	private EventoTest testEvento;
	private PesquisaSolicitacaoTest testPesquisaSolicitacao;
	
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	}
	public void testCadUsuario(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testUsuario = new UsuarioTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			//Teste Create
			testeDto.setMetodo("create");
			testeDto.setResultado(testUsuario.testCreate());
			testeService.create(testeDto);
			resultRetorno.append(UsuarioServiceEjb.class.getCanonicalName() + ":" + "create --> " + testeDto.getResultado());
			//Teste update
			testeDto.setMetodo("update");
			/*testeDto.setResultado(testUsuario.testUpdate());	*/
			testeService.create(testeDto);
			resultRetorno.append(UsuarioServiceEjb.class.getCanonicalName() + ":" + "update --> " + testeDto.getResultado());
			//Teste delete
			testeDto.setMetodo("delete");
			/*testeDto.setResultado(testUsuario.testDelete());*/	
			testeService.delete(testeDto);
			resultRetorno.append(UsuarioServiceEjb.class.getCanonicalName() + ":" + "delete --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadIncidente(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testSolicicao = new SolicitacaoServicoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("create");
			testeDto.setResultado(testSolicicao.testCreateSolicitacao());
			testeService.create(testeDto);
			resultRetorno.append(SolicitacaoServicoServiceEjb.class.getCanonicalName() + ":" + "create --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadOcorrencia (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testOcorrencia = new OcorrenciaSolicitacaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			//Teste Create
			testeDto.setMetodo("create");
			testeDto.setResultado(testOcorrencia.testCreateOcorrencia());
			testeService.create(testeDto);
			resultRetorno.append(OcorrenciaSolicitacaoServiceEjb.class.getCanonicalName() + ":" + "create --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadContato (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testContato = new ContatoSolicitacaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			//Teste Create
			testeDto.setMetodo("create");
			testeDto.setResultado(testContato.testCreateContato());
			testeService.create(testeDto);
			resultRetorno.append(ContatoSolicitacaoServicoServiceEjb.class.getCanonicalName() + ":" + "create --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testRegistroExecucao (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testRegistroExecucao = new RegistroExecucaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Registro");
			testeDto.setResultado(testRegistroExecucao.testRegistrarExecucao());
			testeService.create(testeDto);
			resultRetorno.append(RegistroExecucaoTest.class.getCanonicalName() + ":" + "Registro Execução --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testReclassificaSolicitacao (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testReclassifica = new ReclassificarSolicitacaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Reclassifica");
			testeDto.setResultado(testReclassifica.testReclassificarSolicitacao());
			testeService.create(testeDto);
			resultRetorno.append(ReclassificarSolicitacaoTest.class.getCanonicalName() + ":" + "Reclassifica --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testAnexo (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testAnexo = new AnexoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Anexo");
			testeDto.setResultado(testAnexo.testCreateAnexo());
			testeService.create(testeDto);
			resultRetorno.append(AnexoTest.class.getCanonicalName() + ":" + "Anexo --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadBaseConhecimento (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testBaseConhecimento = new BaseConhecimentoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Base Conhecimento");
			testeDto.setResultado(testBaseConhecimento.testCreateBaseConhecimento());
			testeService.create(testeDto);
			resultRetorno.append(BaseConhecimentoTest.class.getCanonicalName() + ":" + "Base Conhecimento --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testAtribuidaCompartilhada (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testAtribuidaCompartilhada = new AtribuidaCompartilhadaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Atribuida/Compartilhada");
			testeDto.setResultado(testAtribuidaCompartilhada.testAtribuirCompartilhar());
			testeService.create(testeDto);
			resultRetorno.append(AtribuidaCompartilhadaTest.class.getCanonicalName() + ":" + "Atribuida/Compartilhada --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testBusca (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testBusca = new BuscaContratoSolicitacaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Busca Contrato/Solicitacao");
			testeDto.setResultado(testBusca.testPesquisa());
			testeService.create(testeDto);
			resultRetorno.append(BuscaContratoSolicitacaoTest.class.getCanonicalName() + ":" + "Busca Contrato/Solicitacao --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testAtividadePeriodica (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testAtividade = new AtividadePeriodicaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Atividade Periodica");
			testeDto.setResultado(testAtividade.testAtividadePeriodica());
			testeService.create(testeDto);
			resultRetorno.append(AtividadePeriodicaTest.class.getCanonicalName() + ":" + "Atividade Periodica --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCapturarTarefa (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCaptura = new CapturaTarefaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Captura Tarefa");
			testeDto.setResultado(testCaptura.testRealizaSolicitacao());
			testeService.create(testeDto);
			resultRetorno.append(CapturaTarefaTest.class.getCanonicalName() + ":" + "Captura Tarefa --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testDelegacaoTarefa (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testDelegacao = new DelegacaoTarefaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Delega");
			testeDto.setResultado(testDelegacao.testDelegaTarefa());
			testeService.create(testeDto);
			resultRetorno.append(DelegacaoTarefaTest.class.getCanonicalName() + ":" + "Deleta --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadEmpregado (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testEmpregado = new EmpregadoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Cadastro Empregado");
			testeDto.setResultado(testEmpregado.testCreateEmpregado());
			testeService.create(testeDto);
			resultRetorno.append(EmpregadoTest.class.getCanonicalName() + ":" + "Cadastro Empregado --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testExecutarTarefa (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testExecuta = new ExecutarTarefaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Executa");
			testeDto.setResultado(testExecuta.testIniciarExecutarTarefa());
			testeService.create(testeDto);
			resultRetorno.append(ExecutarTarefaTest.class.getCanonicalName() + ":" + "Executa --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testIniciaTarefa (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testInicia = new IniciarTarefaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Inicia");
			testeDto.setResultado(testInicia.testIniciarTarefa());
			testeService.create(testeDto);
			resultRetorno.append(IniciarTarefaTest.class.getCanonicalName() + ":" + "Inicia --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	
	public void testMudancaSLA (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testMudaSLA = new MudarSLATest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Muda SLA");
			testeDto.setResultado(testMudaSLA.testMudarSLA());
			testeService.create(testeDto);
			resultRetorno.append(MudarSLATest.class.getCanonicalName() + ":" + "Muda SLA --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testReativaSolicitacao (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testReativa = new ReativaSolicitacaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Reativa Solicitação");
			testeDto.setResultado(testReativa.testRealizaSolicitacao());
			testeService.create(testeDto);
			resultRetorno.append(ReativaSolicitacaoTest.class.getCanonicalName() + ":" + "Reativa Solicitacao --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadContrato (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testServicoContrato = new ContratoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Cadastro Contrato");
			testeDto.setResultado(testServicoContrato.testCreateContrato());
			testeService.create(testeDto);
			resultRetorno.append(ContratoTest.class.getCanonicalName() + ":" + "Cadastro Contrato --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadMultiContratos (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testMultiContrato = new SolicitacaoServicoMultiContratosTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Cadastro Multi Contrato");
			testeDto.setResultado(testMultiContrato.testCreateMultiContratos());
			testeService.create(testeDto);
			resultRetorno.append(SolicitacaoServicoMultiContratosTest.class.getCanonicalName() + ":" + "Cadastro Multi Contrato --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testSuspenderSolicitacao (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testSuspender = new SuspenderSolicitacaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Suspender Solicitação");
			testeDto.setResultado(testSuspender.testSuspenderSolicitacao());
			testeService.create(testeDto);
			resultRetorno.append(SuspenderSolicitacaoTest.class.getCanonicalName() + ":" + "Suspender Solicitação --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testProblemas (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testProblema = new ProblemaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Create Problema");
			testeDto.setResultado(testProblema.testCreateProblema());
			testeService.create(testeDto);
			resultRetorno.append(ProblemaTest.class.getCanonicalName() + ":" + "Create Problema --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testSubSolicitacoes (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testSubSolicitacao = new SubSolicitacaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Create Sub-Solicitação");
			testeDto.setResultado(testSubSolicitacao.testCreateSubSolicitacao());
			testeService.create(testeDto);
			resultRetorno.append(SubSolicitacaoTest.class.getCanonicalName() + ":" + "Create Sub-Solicitação --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testMudancas (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testRequisicaoMudanca = new MudancaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Create Requisição Mudança");
			testeDto.setResultado(testRequisicaoMudanca.testCreateMudanca());
			testeService.create(testeDto);
			resultRetorno.append(MudancaTest.class.getCanonicalName() + ":" + "Create Requisição Mudança --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testVerificacaoEmail (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testVerificaEmail = new VerificaEmailTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Verificação de Email");
			testeDto.setResultado(testVerificaEmail.testVerificaEmail());
			testeService.create(testeDto);
			resultRetorno.append(VerificaEmailTest.class.getCanonicalName() + ":" + "Verificação de Email --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCliente (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCliente = new  ClienteTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Cliente");
			testeDto.setResultado(testCliente.testCliente());
			testeService.create(testeDto);
			resultRetorno.append(ClienteTest.class.getCanonicalName() + ":" + "Cliente --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadFornecedor (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testFornecedor = new  FornecedorTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Fornecedor");
			testeDto.setResultado(testFornecedor.testFornecedor());
			testeService.create(testeDto);
			resultRetorno.append(FornecedorTest.class.getCanonicalName() + ":" + "Fornecedor --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadMenu (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testMenu = new  MenuTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Menu");
			testeDto.setResultado(testMenu.testMenu());
			testeService.create(testeDto);
			resultRetorno.append(MenuTest.class.getCanonicalName() + ":" + "Menu --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadMarca (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testMarca = new  MarcaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Marca");
			testeDto.setResultado(testMarca.testMarca());
			testeService.create(testeDto);
			resultRetorno.append(MarcaTest.class.getCanonicalName() + ":" + "Marca --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadProduto (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testProduto = new  ProdutoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Produto");
			testeDto.setResultado(testProduto.testProduto());
			testeService.create(testeDto);
			resultRetorno.append(ProdutoTest.class.getCanonicalName() + ":" + "Produto --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadEmpresa (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testEmpresa = new  EmpresaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Empresa");
			testeDto.setResultado(testEmpresa.testEmpresa());
			testeService.create(testeDto);
			resultRetorno.append(EmpresaTest.class.getCanonicalName() + ":" + "Empresa --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadGrupo (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testGrupo = new  GrupoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Grupo");
			testeDto.setResultado(testGrupo.testGrupo());
			testeService.create(testeDto);
			resultRetorno.append(GrupoTest.class.getCanonicalName() + ":" + "Grupo --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCargos (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCargos = new  CargosTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Cargos");
			testeDto.setResultado(testCargos.testCargos());
			testeService.create(testeDto);
			resultRetorno.append(CargosTest.class.getCanonicalName() + ":" + "Cargos --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadUnidade (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testUnidade = new  UnidadeTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Unidade");
			testeDto.setResultado(testUnidade.testUnidade());
			testeService.create(testeDto);
			resultRetorno.append(UnidadeTest.class.getCanonicalName() + ":" + "Unidade --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadJornadaTrabalho (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testJornadaTrabalho = new  JornadaTrabalhoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Jornada de Trabalho");
			testeDto.setResultado(testJornadaTrabalho.testJornadaTrabalho());
			testeService.create(testeDto);
			resultRetorno.append(JornadaTrabalhoTest.class.getCanonicalName() + ":" + "Jornada de Trabalho --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadLocalidade (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testLocalidade = new  LocalidadeTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Localidade");
			testeDto.setResultado(testLocalidade.testLocalidade());
			testeService.create(testeDto);
			resultRetorno.append(LocalidadeTest.class.getCanonicalName() + ":" + "Localidade --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadPerfilAcesso (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testPerfilAcesso = new  PerfilAcessoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Perfil de Acesso");
			testeDto.setResultado(testPerfilAcesso.testPerfilAcesso());
			testeService.create(testeDto);
			resultRetorno.append(PerfilAcessoTest.class.getCanonicalName() + ":" + "Perfil de Acesso --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCalendario (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCalendario = new  CalendarioTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Calendario");
			testeDto.setResultado(testCalendario.testCalendario());
			testeService.create(testeDto);
			resultRetorno.append(CalendarioTest.class.getCanonicalName() + ":" + "Calendario --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadTipoUnidade (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testTipoUnidade = new  TipoUnidadeTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Tipo de Unidade");
			testeDto.setResultado(testTipoUnidade.testTipoUnidade());
			testeService.create(testeDto);
			resultRetorno.append(TipoUnidadeTest.class.getCanonicalName() + ":" + "Tipo de Unidade --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadComando (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testComando = new  ComandoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Comando");
			testeDto.setResultado(testComando.testComando());
			testeService.create(testeDto);
			resultRetorno.append(ComandoTest.class.getCanonicalName() + ":" + "Comando --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadSistemaOperacional (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testSistemaOperacional = new  SistemaOperacionalTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Sistema Operacional");
			testeDto.setResultado(testSistemaOperacional.testSistemaOperacional());
			testeService.create(testeDto);
			resultRetorno.append(SistemaOperacionalTest.class.getCanonicalName() + ":" + "Sistema Operacional --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadComandoSistemaOperacional (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testComandoSistemaOperacional = new  ComandoSistemaOperacionalTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Comando Sistema Operacional");
			testeDto.setResultado(testComandoSistemaOperacional.testComandoSistemaOperacional());
			testeService.create(testeDto);
			resultRetorno.append(ComandoSistemaOperacionalTest.class.getCanonicalName() + ":" + "Comando Sistema Operacional --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCategoriaSolucao (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCategoriaSolucao = new  CategoriaSolucaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Categoria Solução");
			testeDto.setResultado(testCategoriaSolucao.testCategoriaSolucao());
			testeService.create(testeDto);
			resultRetorno.append(CategoriaSolucaoTest.class.getCanonicalName() + ":" + "Categoria Solução --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadPrioridade (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testPrioridade = new  PrioridadeTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Prioridade");
			testeDto.setResultado(testPrioridade.testPrioridade());
			testeService.create(testeDto);
			resultRetorno.append(PrioridadeTest.class.getCanonicalName() + ":" + "Prioridade --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadTipoEventoServico (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testTipoEventoServico = new  TipoEventoServicoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Tipo Evento Serviço");
			testeDto.setResultado(testTipoEventoServico.testTipoEventoServico());
			testeService.create(testeDto);
			resultRetorno.append(TipoEventoServicoTest.class.getCanonicalName() + ":" + "Tipo Evento Serviço --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadJustificativaFalhas (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testJustificativaFalhas = new  JustificativaFalhasTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Justificativa Falhas");
			testeDto.setResultado(testJustificativaFalhas.testJustificativaFalhas());
			testeService.create(testeDto);
			resultRetorno.append(JustificativaFalhasTest.class.getCanonicalName() + ":" + "Justificativa Falhas --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadImportanciaNegocio (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testImportanciaNegocio = new  ImportanciaNegocioTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Importancia Negocio");
			testeDto.setResultado(testImportanciaNegocio.testImportanciaNegocio());
			testeService.create(testeDto);
			resultRetorno.append(ImportanciaNegocioTest.class.getCanonicalName() + ":" + "Importancia Negocio --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCausaIncidente (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCausaIncidente = new  CausaIncidenteTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Causa Incidente");
			testeDto.setResultado(testCausaIncidente.testCausaIncidente());
			testeService.create(testeDto);
			resultRetorno.append(CausaIncidenteTest.class.getCanonicalName() + ":" + "Causa Incidente --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadGrupoAtvPeriodica (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testGrupoAtvPeriodica = new  GrupoAtvPeriodicaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Grupo Atividade Periodica");
			testeDto.setResultado(testGrupoAtvPeriodica.testGrupoAtvPeriodica());
			testeService.create(testeDto);
			resultRetorno.append(GrupoAtvPeriodicaTest.class.getCanonicalName() + ":" + "Grupo Atividade Periodica --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadModeloEmail (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testModeloEmail = new  ModeloEmailTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Modelo de Email");
			testeDto.setResultado(testModeloEmail.testModeloEmail());
			testeService.create(testeDto);
			resultRetorno.append(ModeloEmailTest.class.getCanonicalName() + ":" + "Modelo de Email --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCategoriaPost (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCategoriaPost = new  CategoriaPostTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Categoria Postagem");
			testeDto.setResultado(testCategoriaPost.testCategoriaPost());
			testeService.create(testeDto);
			resultRetorno.append(CategoriaPostTest.class.getCanonicalName() + ":" + "Categoria Postagem --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadUnidadeMedida (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testUnidadeMedida = new  UnidadeMedidaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Unidade Medida");
			testeDto.setResultado(testUnidadeMedida.testUnidadeMedida());
			testeService.create(testeDto);
			resultRetorno.append(UnidadeMedidaTest.class.getCanonicalName() + ":" + "Unidade Medida --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadRequisicaoProduto (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testRequisicaoProduto = new  RequisicaoProdutoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Requisição Produto");
			testeDto.setResultado(testRequisicaoProduto.testRequisicaoProduto());
			testeService.create(testeDto);
			resultRetorno.append(RequisicaoProdutoTest.class.getCanonicalName() + ":" + "Requisição Produto --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadJustificativaParecer (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testJustificativaParecer = new  JustificativaParecerTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Justificativa Parecer");
			testeDto.setResultado(testJustificativaParecer.testJustificativaParecer());
			testeService.create(testeDto);
			resultRetorno.append(JustificativaParecerTest.class.getCanonicalName() + ":" + "Justificativa Parecer --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCondicaoOperacao (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCondicaoOperacao = new  CondicaoOperacaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Condição Operação");
			testeDto.setResultado(testCondicaoOperacao.testCondicaoOperacao());
			testeService.create(testeDto);
			resultRetorno.append(CondicaoOperacaoTest.class.getCanonicalName() + ":" + "Condição Operação --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCategoriaProduto (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCategoriaProduto = new  CategoriaProdutoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Categoria Produto");
			testeDto.setResultado(testCategoriaProduto.testCategoriaProduto());
			testeService.create(testeDto);
			resultRetorno.append(CategoriaProdutoTest.class.getCanonicalName() + ":" + "Categoria Produto --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCentroResultado (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCentroResultado = new  CentroResultadoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Centro Resultado");
			testeDto.setResultado(testCentroResultado.testCentroResultado());
			testeService.create(testeDto);
			resultRetorno.append(CentroResultadoTest.class.getCanonicalName() + ":" + "Centro Resultado --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadAlcadaCentroResultado (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testAlcadaCentroResultado = new  AlcadaCentroResultadoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Alcada Centro de Resuldado");
			testeDto.setResultado(testAlcadaCentroResultado.testAlcadaCentroResultado());
			testeService.create(testeDto);
			resultRetorno.append(AlcadaCentroResultadoTest.class.getCanonicalName() + ":" + "Alçada Centro de Resuldado --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadPasta (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testPasta = new  PastaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Pasta");
			testeDto.setResultado(testPasta.testPasta());
			testeService.create(testeDto);
			resultRetorno.append(PastaTest.class.getCanonicalName() + ":" + "Pasta --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadPalavraGemea (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testPalavraGemea = new  PalavraGemeaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Palavra Gêmea");
			testeDto.setResultado(testPalavraGemea.testPalavraGemea());
			testeService.create(testeDto);
			resultRetorno.append(PalavraGemeaTest.class.getCanonicalName() + ":" + "Palavra Gêmea --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCatalogoServico (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCatalogoServico = new  CatalogoServicoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Catalogo Serviço");
			testeDto.setResultado(testCatalogoServico.testCatalogoServico());
			testeService.create(testeDto);
			resultRetorno.append(CatalogoServicoTest.class.getCanonicalName() + ":" + "Catalogo Serviço --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadTipoServico (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testTipoServico = new  TipoServicoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Tipo Serviço");
			testeDto.setResultado(testTipoServico.testTipoServico());
			testeService.create(testeDto);
			resultRetorno.append(TipoServicoTest.class.getCanonicalName() + ":" + "Tipo Serviço --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCategoriaServico (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCategoriaServico = new  CategoriaServicoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Categoria Serviço");
			testeDto.setResultado(testCategoriaServico.testCliente());
			testeService.create(testeDto);
			resultRetorno.append(PalavraGemeaTest.class.getCanonicalName() + ":" + "Categoria Serviço --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadItemConfiguracao (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testItemConfiguracao = new  ItemConfiguracaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Item Configuração");
			testeDto.setResultado(testItemConfiguracao.testItemConfiguracao());
			testeService.create(testeDto);
			resultRetorno.append(ItemConfiguracaoTest.class.getCanonicalName() + ":" + "Item Configuração --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadTipoItemConfiguracao (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testTipoItemConfiguracao = new  TipoItemConfiguracaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Tipo Item Configuração");
			testeDto.setResultado(testTipoItemConfiguracao.testTipoItemConfiguracao());
			testeService.create(testeDto);
			resultRetorno.append(TipoItemConfiguracaoTest.class.getCanonicalName() + ":" + "Tipo Item Configuração --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadCaracteristica (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testCaracteristica = new  CaracteristicaTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Caracteristica");
			testeDto.setResultado(testCaracteristica.testCaracteristica());
			testeService.create(testeDto);
			resultRetorno.append(CaracteristicaTest.class.getCanonicalName() + ":" + "Caracteristica --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadSoftwareInsDes (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testSoftwareInsDes = new  SoftwareInsDesTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Software Ins/Des");
			testeDto.setResultado(testSoftwareInsDes.testSoftwareInsDes());
			testeService.create(testeDto);
			resultRetorno.append(SoftwareInsDesTest.class.getCanonicalName() + ":" + "Software Ins/Des --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testCadEvento (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testEvento = new  EventoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Evento");
			testeDto.setResultado(testEvento.testEvento());
			testeService.create(testeDto);
			resultRetorno.append(EventoTest.class.getCanonicalName() + ":" + "Evento --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public void testPesquisaSolicitacao (DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response){
		try {
			StringBuilder resultRetorno = new StringBuilder();
			UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
			TesteCITSMartService testeService = new TesteCITSmartEjb();
			testPesquisaSolicitacao = new  PesquisaSolicitacaoTest();			
			if (usrDto == null) {
				return;
			}
			TesteCITSmartDTO testeDto = new TesteCITSmartDTO();
			testeDto.setClasse(UsuarioServiceEjb.class.getCanonicalName());
			testeDto.setDataHora(UtilDatas.getDataAtual());
			testeDto.setMetodo("Pesquisa Solicitação");
			testeDto.setResultado(testPesquisaSolicitacao.testPesquisaSolicitacao(document, request, response));
			testeService.create(testeDto);
			resultRetorno.append(PesquisaSolicitacaoTest.class.getCanonicalName() + ":" + "Pesquisa Solicitaçao --> " + testeDto.getResultado());
			document.alert(resultRetorno.toString());
		} catch (Exception e) {
		}
	}
	
	public Class<TesteCITSmartDTO> getBeanClass() {
		return TesteCITSmartDTO.class;
	}
}
