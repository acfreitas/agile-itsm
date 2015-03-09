package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.ADUserDTO;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ContadorAcessoDTO;
import br.com.centralit.citcorpore.bean.ContatoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EmailSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ImpactoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.ItemCfgSolicitacaoServDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoEvtMonDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.ad.LDAPUtils;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.CategoriaServicoService;
import br.com.centralit.citcorpore.negocio.CategoriaSolucaoService;
import br.com.centralit.citcorpore.negocio.CausaIncidenteService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ConhecimentoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ContadorAcessoService;
import br.com.centralit.citcorpore.negocio.ContatoSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmailSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.EventoMonitConhecimentoService;
import br.com.centralit.citcorpore.negocio.EventoMonitoramentoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ImportanciaConhecimentoGrupoService;
import br.com.centralit.citcorpore.negocio.ItemCfgSolicitacaoServService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.LocalidadeService;
import br.com.centralit.citcorpore.negocio.LocalidadeUnidadeService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.PrioridadeSolicitacoesService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoEvtMonService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TemplateSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.negocio.ValorService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.Enumerados.TipoOrigemLeituraEmail;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.centralit.lucene.Lucene;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes", "unchecked"})
public class SolicitacaoServicoMultiContratos extends AjaxFormAction {

    private String calcularDinamicamente;

    ContratoDTO contratoDtoAux = new ContratoDTO();
    Collection<ContratoDTO> listContratosUsuario = null;

    private Boolean acao = false;

    private PrioridadeSolicitacoesService prioridadeSolicitacoesService;

    private ProblemaService problemaService;

    private ConhecimentoSolicitacaoService conhecimentoSolicitacaoService;

    private RequisicaoMudancaService requisicaoMudancaService;

    private UnidadeService unidadeService;

    private GrupoService grupoService;

    private SolicitacaoServicoService solicitacaoServicoService;

    private ClienteService clienteService;

    private OrigemAtendimentoService origemAtendimentoService;

    private ContratoService contratoService;

    private ServicoContratoService servicoContratoService;

    private CategoriaSolucaoService categoriaSolucaoService;

    private CausaIncidenteService causaIncidenteService;

    private TipoDemandaServicoService tipoDemandaService;

    private FornecedorService fornecedorService;

    private AcordoNivelServicoService acordoNivelServicoService;

    private AcordoServicoContratoService acordoServicoContratoService;

    private GrupoEmpregadoService grupoEmpregadoService;

    private ImportanciaConhecimentoGrupoService importanciaConhecimentoGrupoService;

    private ItemConfiguracaoService itemConfiguracaoService;

    private ItemCfgSolicitacaoServService itemCfgSolicitacaoServService;

    private UsuarioService usuarioService;

    private EmpregadoService empregadoService;

    private TipoItemConfiguracaoService tipoItemConfiguracaoService;

    private CategoriaServicoService categoriaService;

    private ControleGEDService controleGedService;

    private ServicoService servicoService;

    private OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService;

    private JustificativaSolicitacaoService justificativaSolicitacaoService;

    private ValorService valorService;

    private BaseConhecimentoService baseConhecimentoService;

    private LocalidadeService localidadeService;

    private ContadorAcessoService contadorAcessoService;

    private LocalidadeUnidadeService localidadeUnidadeService;

    public boolean validaParametrosUpload() {
        final String GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.GedDiretorio, "");
        if (GED_DIRETORIO == null || GED_DIRETORIO.trim().equals("")) {
            return false;
        }
        final File pastaGed = new File(GED_DIRETORIO);
        if (!pastaGed.exists()) {
            return false;
        }
        final String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
        if (DISKFILEUPLOAD_REPOSITORYPATH == null || DISKFILEUPLOAD_REPOSITORYPATH.trim().equals("")) {
            return false;
        }
        final File pastaUpload = new File(DISKFILEUPLOAD_REPOSITORYPATH);
        if (!pastaUpload.exists()) {
            return false;
        }
        return true;
    }

    public void chamaComboOrigem(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        preencherComboOrigem(document, request, response);
    }

    /**
     * Alterado método para carragar somente o essencial para o primeiro passo
     *
     * @author thyen.chang
     * @since 09/02/2015 - OPERAÇÃO USAIN BOLT
     */
    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        request.setAttribute("parametrosUploadValidos", validaParametrosUpload());

        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS")
                    + request.getContextPath() + "'");
            return;
        }

        document.executeScript("habilitaBotaoGravar()");

        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");

        /**
         * Adicionado para fazer limpeza do upload que está na sessão .
         *
         * @author maycon.fernandes
         * @since 28/10/2013 08:21
         */
        request.getSession(true).setAttribute("colUploadsGED2", null);

        request.getSession(true).setAttribute("dados_solicit_quest", null);

        document.executeScript("document.getElementById(\"divCategoriaServico\").style.display = 'none'");

        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
            request.getSession().removeAttribute("segundoPassoLoad");
            request.getSession().removeAttribute("terceiroPassoLoad");
            request.getSession().removeAttribute("quartoPassoLoad");
            carregaSegundoPasso(document, request, response);
            carregaTerceiroPasso(document, request, response);
            carregaQuartoPasso(document, request, response);
            restore(document, request, response);
            solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        } else {
            document.getElementById("quantidadeAnexos").setValue("0");
            document.getElementById("quantidadeOcorrencias").setValue("0");
            document.getElementById("quantidadeNovasSolicitacoes").setValue("0");
            document.getElementById("quantidadeIncidentesRelacionados").setValue("0");
            document.getElementById("quantidadeProblema").setValue("0");
            document.getElementById("quantidadeMudanca").setValue("0");
            document.getElementById("quantidadeItemConfiguracao").setValue("0");
            document.getElementById("quantidadeBaseConhecimento").setValue("0");
        }

        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEditar() == null) {
            solicitacaoServicoDto.setEditar("");
            document.executeScript("$('#liNovasolicitacao').addClass('inativo')");
        }
        /*
         * Desenvolvedor: Thiago Matias - Data: 08/11/2013 - Horário: 09:30 - ID Citsmart: 123357 - Motivo/Comentário: Verificando a
         * variavel editar da URL está setada com N, pois se estiver é para
         * setar no objeto e sdesabilitar os campos abaixo
         */
        if (solicitacaoServicoDto != null && request.getParameter("editar") != null
                && request.getParameter("editar").equalsIgnoreCase("N")) {
            solicitacaoServicoDto.setEditar("N");
        }

        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEditar().equalsIgnoreCase("N")) {
            document.getElementById("idOrigem").setDisabled(true);
            document.getElementById("solicitante").setDisabled(true);
            document.getElementById("telefonecontato").setDisabled(true);
            document.getElementById("ramal").setDisabled(true);
            document.getElementById("idUnidade").setDisabled(true);

            if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")) {
                document.getElementById("unidadeDes").setDisabled(true);
            }

            document.getElementById("idLocalidade").setDisabled(true);
            document.getElementById("emailcontato").setDisabled(true);
            document.getElementById("descricao").setDisabled(true);
            document.getElementById("idCausaIncidente").setDisabled(true);
            document.getElementById("idCategoriaSolucao").setDisabled(true);
            document.getElementById("solucaoTemporaria").setDisabled(true);
            document.executeScript("document.getElementById('divBotoes').style.display = 'none';");
            document.executeScript("bloqueiaBotoesVisualizacao()");
            document.executeScript("$('#uniform-gravaSolucaoRespostaBaseConhecimento').addClass('disabled')");
            document.getElementById("gravaSolucaoRespostaBaseConhecimento").setDisabled(true);
            document.executeScript("desabilitaSituacao()");
            document.executeScript("$('#tituloSolicitacao').removeClass('inativo')");
            document.getElementById("enviaEmailCriacao").setDisabled(true);
            document.executeScript("$('#uniform-enviaEmailCriacao').addClass('disabled')");
            document.getElementById("enviaEmailFinalizacao").setDisabled(true);
            document.executeScript("$('#uniform-enviaEmailFinalizacao').addClass('disabled')");
            document.getElementById("enviaEmailAcoes").setDisabled(true);
            document.executeScript("$('#uniform-enviaEmailAcoes').addClass('disabled')");
            document.executeScript("$('#addProblema').attr('disabled', 'disabled')");

        } else if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEditar().equalsIgnoreCase("S")) {
            document.executeScript("$('#liNovasolicitacao').removeClass('inativo')");
            document.executeScript("$('#tituloSolicitacao').removeClass('inativo')");
        }

        carregarComboContrato(document, usuario, solicitacaoServicoDto);

        final String acaoFluxo = request.getParameter("acaoFluxo");
        if (acaoFluxo != null && acaoFluxo.equalsIgnoreCase("E")) {
            document.executeScript("mostrarPassoQuatroExecucaoTarefa()");
        }

        String tarefaAssociada = "N";
        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdTarefa() != null) {
            tarefaAssociada = "S";
        }

        /**
         * RECLASSIFICAR SOLICITACAO Visualiza o 3 passo do cadastro de solicitação de serviço, mostrando apenas campos especificos para a
         * reclassificação da solicitação
         **/
        String visualizarPasso = request.getParameter("visualizarPasso");
        visualizarPasso = UtilStrings.nullToVazio(visualizarPasso);
        if (visualizarPasso != null && visualizarPasso.equalsIgnoreCase("C")) {
            document.executeScript("visualizaCollapse3()");
            document.getElementById("reclassicarSolicitacao").setValue("S");
            tarefaAssociada = "N";
        } else {
            document.getElementById("reclassicarSolicitacao").setValue("N");
        }

        request.setAttribute("tarefaAssociada", tarefaAssociada);

        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getUrgencia() != null
                && StringUtils.isNotBlank(solicitacaoServicoDto.getUrgencia())) {
            document.getElementById("urgencia").setValue(solicitacaoServicoDto.getUrgencia().trim());
        }

        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getImpacto() != null
                && StringUtils.isNotBlank(solicitacaoServicoDto.getImpacto())) {
            document.getElementById("impacto").setValue(solicitacaoServicoDto.getImpacto().trim());
        }

        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdContrato() != null) {
            document.getElementById("idContrato").setValue("" + solicitacaoServicoDto.getIdContrato());
        }

        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdServicoContrato() != null) {
            document.getElementById("idServico").setValue("" + solicitacaoServicoDto.getIdServico());
        }

        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdContrato() != null) {
            verificaGrupoExecutor(document, request, response);
            document.getSelectById("idGrupoAtual").setValue("" + solicitacaoServicoDto.getIdGrupoAtual());
        }

        document.executeScript("JANELA_AGUARDE_MENU.hide();$('#loading_overlay').hide();");

        if (request.getParameter("idEmpregado") == null) {
            document.executeScript("parent.fecharJanelaAguarde();");
        } else {
            solicitacaoServicoDto.setIsIframe("true");
            document.getElementById("isIframe").setValue("true");
        }

        alimentarSolicitanteChamadaAsterisk(document, request, response, solicitacaoServicoDto, UNIDADE_AUTOCOMPLETE);

        final HTMLForm form = document.getForm("form");
        form.setValues(solicitacaoServicoDto);

        String sla = "";

        /*
         * Mário Júnior - 04/12/2013 #Solicitação-125972 Alterado pois na vizualização e na execução o SLA não é mostrado no 3 passo da
         * solicitação.
         */
        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdContrato() != null
                && solicitacaoServicoDto.getIdServico() != null) {
            if (solicitacaoServicoDto.getPrazoHH() != null && solicitacaoServicoDto.getPrazoMM() != null) {
                String hh = solicitacaoServicoDto.getPrazoHH().toString();
                String mm = solicitacaoServicoDto.getPrazoMM().toString();
                if (hh.equals("0") && mm.equals("0")) {
                    sla = UtilI18N.internacionaliza(request, "citcorpore.comum.aCombinar");
                } else if (!hh.equals("0") || !mm.equals("0")) {
                    if (hh.length() == 1) {
                        hh = "0" + hh;
                    }
                    if (mm.length() == 1) {
                        mm = "0" + mm;
                    }

                    sla = hh + ":" + mm;
                }
            }
            if (sla.equals("")) {
                sla = "N/A";
            }
            document.executeScript("document.getElementById('tdResultadoSLAPrevisto').innerHTML = '" + sla + "';");
        }

        final String mostraGravarBaseConhec = ParametroUtil.getValorParametroCitSmartHashMap(
                ParametroSistema.MOSTRAR_GRAVAR_BASE_CONHECIMENTO, "S");
        document.executeScript("validaExibicaoBaseConhecimento('" + mostraGravarBaseConhec + "')");

        if (solicitacaoServicoDto == null || solicitacaoServicoDto != null
                && solicitacaoServicoDto.getIdSolicitacaoServico() == null) {
            request.getSession().removeAttribute("segundoPassoLoad");
            request.getSession().removeAttribute("terceiroPassoLoad");
            request.getSession().removeAttribute("quartoPassoLoad");
        }

        solicitacaoServicoDto = null;

    }

    /**
     * Carrega segunndo passo da solicitação
     *
     * @author thyen.chang
     * @since 09/02/2015 - OPERAÇÃO USAIN BOLT
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void carregaSegundoPasso(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        if (request.getSession().getAttribute("segundoPassoLoad") == null) {
            final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(
                    br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");

            final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

            StringBuilder objeto;
            if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")) {
                objeto = new StringBuilder();
                objeto.append("<label  class='strong  campoObrigatorio'>");
                objeto.append(UtilI18N.internacionaliza(request, "unidade.unidade"));
                objeto.append("</label>");
                objeto.append("<input type='text' name='unidadeDes' id='unidadeDes' style='width: 100%;' onkeypress='onkeypressUnidadeDes();' onfocus='montaParametrosAutocompleteUnidade();'>");
                objeto.append("<input type='hidden' name='idUnidade' id='idUnidade' value='0'/>");
                document.getElementById("divUnidade").setInnerHTML(objeto.toString());
                document.executeScript("geraAutoComplete()");
            } else {
                objeto = new StringBuilder();
                objeto.append("<label  class='strong  campoObrigatorio'>");
                objeto.append(UtilI18N.internacionaliza(request, "unidade.unidade"));
                objeto.append("</label>");

                objeto.append("<select  class='span12' name='idUnidade' id='idUnidade' required='required' onchange='document.form.fireEvent(\"preencherComboLocalidade\");'></select>");

                document.getElementById("divUnidade").setInnerHTML(objeto.toString());
            }

            preencherComboOrigem(document, request, response);

            carregarComboGrupoAtual(document, request);

            carregaUnidade(document, request, response);

            Integer idSolicitacaoRelacionada = null;
            if (request.getParameter("idSolicitacaoRelacionada") != null
                    && !request.getParameter("idSolicitacaoRelacionada").equalsIgnoreCase("")) {
                idSolicitacaoRelacionada = Integer.parseInt(request.getParameter("idSolicitacaoRelacionada"));

                Integer idContrato = null;

                SolicitacaoServicoDTO solicitacaoServico = new SolicitacaoServicoDTO();
                final SolicitacaoServicoDTO solicitacaoServicoInformacoesContato = new SolicitacaoServicoDTO();

                if (request.getParameter("idContrato") != null
                        && !request.getParameter("idContrato").equalsIgnoreCase("")) {
                    idContrato = Integer.parseInt(request.getParameter("idContrato"));

                    if (idContrato != null) {
                        solicitacaoServico.setIdContrato(idContrato);
                    }
                }

                solicitacaoServico.setIdSolicitacaoRelacionada(idSolicitacaoRelacionada);

                verificaGrupoExecutor(document, request, response);
                verificaImpactoUrgencia(document, request, response);

                if (idContrato != null) {
                    document.executeScript("adicionarIdContratoNaLookup(" + idContrato + ")");
                }

                solicitacaoServico = getSolicitacaoServicoService().restoreAll(idSolicitacaoRelacionada);

                if (solicitacaoServico != null) {
                    solicitacaoServicoInformacoesContato.setIdSolicitante(solicitacaoServico.getIdSolicitante());
                    solicitacaoServicoInformacoesContato.setSolicitante(solicitacaoServico.getSolicitante());
                    solicitacaoServicoInformacoesContato.setNomecontato(solicitacaoServico.getNomecontato());
                    solicitacaoServicoInformacoesContato.setTelefonecontato(solicitacaoServico.getTelefonecontato());
                    solicitacaoServicoInformacoesContato.setEmailcontato(solicitacaoServico.getEmailcontato());
                    solicitacaoServicoInformacoesContato.setRamal(solicitacaoServico.getRamal());
                    solicitacaoServicoInformacoesContato.setObservacao(solicitacaoServico.getObservacao());
                    solicitacaoServicoInformacoesContato.setIdUnidade(solicitacaoServico.getIdUnidade());

                    if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")
                            && solicitacaoServicoInformacoesContato.getIdUnidade() != null) {
                        solicitacaoServicoInformacoesContato.setUnidadeDes(getUnidadeService().retornaNomeUnidadeByID(
                                solicitacaoServicoInformacoesContato.getIdUnidade()));
                    }
                    solicitacaoServicoInformacoesContato.setIdSolicitacaoRelacionada(idSolicitacaoRelacionada);
                }

                if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() == null
                        && solicitacaoServicoDto.getIdSolicitacaoRelacionada() != null) {
                    ((SolicitacaoServicoDTO) document.getBean())
                    .setIdSolicitante(solicitacaoServico.getIdSolicitante());
                    renderizaHistoricoSolicitacoesEmAndamentoUsuario(document, request, response);
                }

                document.getForm("form").setValues(solicitacaoServicoInformacoesContato);

                if (solicitacaoServico != null) {
                    preencherComboLocalidade(document, request, response);
                }
            }

            if (acao && idSolicitacaoRelacionada == null) {
                if (solicitacaoServicoDto.getIdSolicitacaoServico() == null
                        || solicitacaoServicoDto.getIdSolicitacaoServico().intValue() == 0) {
                    verificaGrupoExecutor(document, request, response);
                    verificaImpactoUrgencia(document, request, response);
                }
            }

            preencherComboLocalidade(document, request, response);

            if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")
                    && solicitacaoServicoDto.getIdUnidade() != null) {
                solicitacaoServicoDto.setUnidadeDes(getUnidadeService().retornaNomeUnidadeByID(
                        solicitacaoServicoDto.getIdUnidade()));
            }

            request.getSession().setAttribute("segundoPassoLoad", true);

        }

    }

    /**
     * Carrega segunndo passo da solicitação
     *
     * @author thyen.chang
     * @since 09/02/2015 - OPERAÇÃO USAIN BOLT
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void carregaTerceiroPasso(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        if (request.getSession().getAttribute("terceiroPassoLoad") == null) {

            final UsuarioDTO usuario = WebUtil.getUsuario(request);

            carregarCombosImpactoUrgente(document, request);

            final Collection<GrupoDTO> listGrupoDoEmpregadoLogado = getGrupoService().getGruposByEmpregado(
                    usuario.getIdEmpregado());

            if (listGrupoDoEmpregadoLogado != null && !listGrupoDoEmpregadoLogado.isEmpty()) {
                boolean isAbertura = false;
                boolean isEncerramento = false;
                boolean isAndamento = false;

                for (final GrupoDTO grupoDto : listGrupoDoEmpregadoLogado) {

                    if (isAbertura && isEncerramento && isAndamento) {
                        break;
                    } else {
                        if (grupoDto.getAbertura() != null && grupoDto.getAbertura().trim().equals("S") && !isAbertura) {
                            document.getElementById("enviaEmailCriacao").setDisabled(true);
                            document.executeScript("$('#uniform-enviaEmailCriacao').addClass('disabled')");
                            isAbertura = true;
                        }
                        if (grupoDto.getEncerramento() != null && grupoDto.getEncerramento().trim().equals("S")
                                && !isEncerramento) {
                            document.getElementById("enviaEmailFinalizacao").setDisabled(true);
                            document.executeScript("$('#uniform-enviaEmailFinalizacao').addClass('disabled')");
                            isEncerramento = true;
                        }
                        if (grupoDto.getAndamento() != null && grupoDto.getAndamento().trim().equals("S")
                                && isAndamento) {
                            document.getElementById("enviaEmailAcoes").setDisabled(true);
                            document.executeScript("$('#uniform-enviaEmailAcoes').addClass('disabled')");
                            isAndamento = true;
                        }
                    }
                }
            }

            carregarComboTipoDemanda(document, request);

            request.getSession().setAttribute("terceiroPassoLoad", true);

        }

    }

    /**
     * Carrega segunndo passo da solicitação
     *
     * @author thyen.chang
     * @since 09/02/2015 - OPERAÇÃO USAIN BOLT
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void carregaQuartoPasso(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        if (request.getSession().getAttribute("quartoPassoLoad") == null) {

            carregarComboCausa(document, request);

            carregarComboCategoriaSolucao(document, request);

            request.getSession().setAttribute("quartoPassoLoad", true);

        }

    }

    private void alimentarSolicitanteChamadaAsterisk(DocumentHTML document, HttpServletRequest request,
            HttpServletResponse response, SolicitacaoServicoDTO solicitacaoServicoDto, String unidade_AUTOCOMPLETE)
                    throws ServiceException, Exception {

        if (request.getParameter("idEmpregado") != null && !request.getParameter("idEmpregado").equals("")
                && !request.getParameter("idEmpregado").equals("NaN")) {
            final Integer idEmpregado = Integer.parseInt(request.getParameter("idEmpregado"));

            // Contratos vinculados ao colaborador
            final Collection<ContratoDTO> listContratosEmpregado = getContratoService().findAtivosByIdEmpregado(
                    idEmpregado);

            ContratoDTO contratoSelecionado = null;

            // Encontra o primeiro contrato do colaborador que está presente na lista de contratos do usuário
            if (listContratosEmpregado != null && listContratosEmpregado.size() > 0) {
                for (final ContratoDTO contratoEmpregado : listContratosEmpregado) {
                    if (listContratosUsuario.contains(contratoEmpregado)) {
                        contratoSelecionado = contratoEmpregado;
                        break;
                    }
                }
            }

            // Só alimenta o solicitante se ele estiver lotado no contrato ao qual o usuário está vinculado
            if (contratoSelecionado == null) {
                document.alert(UtilI18N.internacionaliza(request, "asterisk.alertaSolicitanteNaoPertenceContrato"));
            } else {
                solicitacaoServicoDto.setIdContrato(contratoSelecionado.getIdContrato()); // Setando o ID do contrato selecionado
                EmpregadoDTO empregadoDTO = new EmpregadoDTO();
                empregadoDTO.setIdEmpregado(idEmpregado);
                empregadoDTO = (EmpregadoDTO) getEmpregadoService().restore(empregadoDTO);
                if (empregadoDTO != null && empregadoDTO.getIdEmpregado() != null) {
                    document.getElementById("idSolicitante").setValue(empregadoDTO.getIdEmpregado().toString());
                }
                if (empregadoDTO != null) {
                    document.getElementById("solicitante").setValue(empregadoDTO.getNome());
                }
                if (empregadoDTO != null) {
                    solicitacaoServicoDto.setNomecontato(empregadoDTO.getNome());
                    solicitacaoServicoDto.setTelefonecontato(empregadoDTO.getTelefone());
                    solicitacaoServicoDto.setRamal(empregadoDTO.getRamal());
                    solicitacaoServicoDto.setEmailcontato(empregadoDTO.getEmail().trim());
                    solicitacaoServicoDto.setIdUnidade(empregadoDTO.getIdUnidade());

                    if (unidade_AUTOCOMPLETE != null && unidade_AUTOCOMPLETE.equalsIgnoreCase("S")
                            && solicitacaoServicoDto.getIdUnidade() != null) {
                        solicitacaoServicoDto.setUnidadeDes(getUnidadeService().retornaNomeUnidadeByID(
                                solicitacaoServicoDto.getIdUnidade()));
                    }

                    solicitacaoServicoDto.setRamal(empregadoDTO.getRamal());
                }
                aplicaOrigemHelpDesk(document);
            }
        }
    }

    private void aplicaOrigemHelpDesk(final DocumentHTML document) throws ServiceException, Exception {
        final OrigemAtendimentoDTO origemAtendimentoDTO = getOrigemAtendimentoService().buscarOrigemAtendimento(
                "Help Desk");
        if (origemAtendimentoDTO != null && origemAtendimentoDTO.getIdOrigem() != null) {
            try {
                document.getElementById("idOrigem").setValue(origemAtendimentoDTO.getIdOrigem().toString());
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Carrega Combo de Contratos de acordo com PARÂMETRO de Vinculo de Colaboradores.
     *
     * @param document
     * @param usuario
     * @param solicitacaoServicoDto
     * @param contratoService
     * @throws Exception
     * @throws LogicException
     * @throws ServiceException
     * @author valdoilo.damasceno
     * @since 03.11.2013
     */
    private void carregarComboContrato(final DocumentHTML document, final UsuarioDTO usuario,
            final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception, LogicException, ServiceException {

        Collection<ContratoDTO> listContratoAtivo = null;

        String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");

        if (COLABORADORES_VINC_CONTRATOS == null) {
            COLABORADORES_VINC_CONTRATOS = "N";
        }

        // COMBO CONTRATO
        document.getSelectById("idContrato").removeAllOptions();

        // NÃO HÁ NECESSIDADE DE CARREGAR TODOS OS CONTRATOS
        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null) {

            ContratoDTO contratoDto = new ContratoDTO();

            contratoDto.setIdContrato(solicitacaoServicoDto.getIdContrato());

            contratoDto = (ContratoDTO) getContratoService().restore(contratoDto);

            contratoDto.setNome(tratarNomeContrato(contratoDto, document.getLanguage()));

            document.getSelectById("idContrato").addOption("" + contratoDto.getIdContrato(), contratoDto.getNome());

        } else {

            // HÁ NECESSIDADE DE CARREGAR TODOS OS CONTRATOS (de acordo com o Usuário Logado)
            if (COLABORADORES_VINC_CONTRATOS != null && COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {

                // PARÂMETRO DE VINCULO ATIVO
                listContratoAtivo = getContratoService().findAtivosByIdEmpregado(usuario.getIdEmpregado());

            } else {

                // PARÂMETRO DE VINCULO INATIVO
                listContratoAtivo = getContratoService().listAtivos();

            }

            if (listContratoAtivo != null && !listContratoAtivo.isEmpty()) {

                for (final ContratoDTO contratoDto : listContratoAtivo) {
                    contratoDto.setNome(tratarNomeContrato(contratoDto, document.getLanguage()));
                }

                if (listContratoAtivo.size() > 1) {
                    document.getSelectById("idContrato").addOption("", "Selecione");

                    document.getSelectById("idContrato").addOptions(listContratoAtivo, "idContrato", "nome", null);

                } else {
                    final ContratoDTO contratoDto = ((List<ContratoDTO>) listContratoAtivo).get(0);

                    document.executeScript("adicionarIdContratoNaLookup(" + contratoDto.getIdContrato() + ")");

                    document.getSelectById("idContrato").addOption("" + contratoDto.getIdContrato(),
                            contratoDto.getNome());

                    acao = true;

                    // É utilizado para carregar as Unidades.
                    contratoDtoAux.setIdContrato(contratoDto.getIdContrato());
                }
            }

        }
        // Lista utilizada para verificar se o empregado da chamada Asterisk está inserido no contrato que o usuário está vinculado
        listContratosUsuario = listContratoAtivo;
    }

    /**
     * Carrega Combo Categoria Solução.
     *
     * @param document
     * @param request
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 03.11.2013
     */
    private void carregarComboCategoriaSolucao(final DocumentHTML document, final HttpServletRequest request)
            throws ServiceException, Exception {

        final Collection listCategoriaSolucao = getCategoriaSolucaoService().listHierarquia();

        final HTMLSelect idCategoriaSolucao = document.getSelectById("idCategoriaSolucao");

        idCategoriaSolucao.removeAllOptions();

        idCategoriaSolucao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        if (listCategoriaSolucao != null && !listCategoriaSolucao.isEmpty()) {
            idCategoriaSolucao.addOptions(listCategoriaSolucao, "idCategoriaSolucao", "descricaoCategoriaNivel", null);
        }
    }

    /**
     * Carrega Combo Causa.
     *
     * @param document
     * @param request
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 03.11.2013
     */
    private void carregarComboCausa(final DocumentHTML document, final HttpServletRequest request)
            throws ServiceException, Exception {

        final Collection colCausas = getCausaIncidenteService().listHierarquia();

        final HTMLSelect idCausa = document.getSelectById("idCausaIncidente");

        idCausa.removeAllOptions();

        idCausa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        if (colCausas != null && !colCausas.isEmpty()) {
            idCausa.addOptions(colCausas, "idCausaIncidente", "descricaoCausaNivel", null);
        }
    }

    /**
     * Carrega Combo Grupo Atual.
     *
     * @param document
     * @param request
     * @throws Exception
     * @throws ServiceException
     * @author valdoilo.damasceno
     * @since 03.11.2013
     */
    private void carregarComboGrupoAtual(final DocumentHTML document, final HttpServletRequest request)
            throws Exception, ServiceException {

        final HTMLSelect idGrupoAtual = document.getSelectById("idGrupoAtual");

        idGrupoAtual.removeAllOptions();

        idGrupoAtual.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        final Collection<GrupoDTO> listGrupoServiceDesk = getGrupoService().listGruposServiceDesk();

        if (listGrupoServiceDesk != null) {
            idGrupoAtual.addOptions(listGrupoServiceDesk, "idGrupo", "nome", null);
        }
    }

    /**
     * Carrega Combo de Tipo Demanda Serviço.
     *
     * @param document
     * @param request
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damasceno
     * @since 03.11.2013
     */
    private void carregarComboTipoDemanda(final DocumentHTML document, final HttpServletRequest request)
            throws ServiceException, Exception {

        final HTMLSelect idTipoDemandaServico = document.getSelectById("idTipoDemandaServico");

        idTipoDemandaServico.removeAllOptions();

        idTipoDemandaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        final Collection<TipoDemandaServicoDTO> listTipoDemanda = getTipoDemandaService().listSolicitacoes();

        if (listTipoDemanda != null) {
            idTipoDemandaServico.addOptions(listTipoDemanda, "idTipoDemandaServico", "nomeTipoDemandaServico", null);
        }
    }

    /**
     * Carrega Combos Impact e Urgência.
     *
     * @param document
     * @param request
     * @throws Exception
     * @throws ServiceException
     * @author valdoilo.damasceno
     * @since 03.11.2013
     */
    private void carregarCombosImpactoUrgente(final DocumentHTML document, final HttpServletRequest request)
            throws Exception, ServiceException {

        final HTMLSelect urgencia = document.getSelectById("urgencia");

        urgencia.removeAllOptions();

        final HTMLSelect impacto = document.getSelectById("impacto");

        impacto.removeAllOptions();

        if (!getCalcularDinamicamente().trim().equalsIgnoreCase("S")) {

            urgencia.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
            urgencia.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
            urgencia.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));

            impacto.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
            impacto.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
            impacto.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));

        } else {

            final Collection<UrgenciaDTO> listUrgenciaDTO = getPrioridadeSolicitacoesService().consultaUrgencia();

            if (listUrgenciaDTO != null && !listUrgenciaDTO.isEmpty()) {
                for (final UrgenciaDTO urgenciaTemp : listUrgenciaDTO) {
                    urgencia.addOption(urgenciaTemp.getSiglaUrgencia().toString(), urgenciaTemp.getNivelUrgencia());
                }
            }

            final Collection<ImpactoDTO> listImpactoDTO = getPrioridadeSolicitacoesService().consultaImpacto();

            if (listImpactoDTO != null && !listImpactoDTO.isEmpty()) {
                for (final ImpactoDTO impactoTemp : listImpactoDTO) {
                    impacto.addOption(impactoTemp.getSiglaImpacto().toString(), impactoTemp.getNivelImpacto());
                }
            }
        }
    }

    /**
     * Concatena ao Nome do Contrato o Número do Contrato + Data do Contrato + Nome do Cliente + Nome do Fornecedor.
     *
     * @param contratoDto
     * @return String - Nome do Contrato tratado.
     * @throws Exception
     * @author valdoilo.damasceno
     * @param language
     * @since 03.11.2013
     */
    private String tratarNomeContrato(final ContratoDTO contratoDto, final String language) throws Exception {

        String nomeCliente = "";
        String nomeFornecedor = "";

        ClienteDTO clienteDto = new ClienteDTO();

        clienteDto.setIdCliente(contratoDto.getIdCliente());

        clienteDto = (ClienteDTO) getClienteService().restore(clienteDto);

        if (clienteDto != null) {
            nomeCliente = clienteDto.getNomeRazaoSocial();
        }

        FornecedorDTO fornecedorDto = new FornecedorDTO();

        fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());

        fornecedorDto = (FornecedorDTO) getFornecedorService().restore(fornecedorDto);

        if (fornecedorDto != null) {
            nomeFornecedor = fornecedorDto.getRazaoSocial();
        }

        final String nomeContrato = "" + contratoDto.getNumero() + " de "
                + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), language) + " ("
                + nomeCliente + " - " + nomeFornecedor + ")";

        return nomeContrato;
    }

    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        boolean bExisteSolicitacao = false;
        SolicitacaoServicoQuestionarioDTO solicitacaoServicoQuestionarioDto = null;
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            final String CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(
                    Enumerados.ParametroSistema.CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO, "N");

            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS")
                        + request.getContextPath() + "'");
                return;
            }

            SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
            if (solicitacaoServicoDto != null) {
                bExisteSolicitacao = solicitacaoServicoDto.getIdSolicitacaoServico() != null;
            }

            final boolean bAlterarSituacao = solicitacaoServicoDto.getAlterarSituacao() != null
                    && solicitacaoServicoDto.getAlterarSituacao().equalsIgnoreCase("S");

            final SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator
                    .getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
            final TemplateSolicitacaoServicoService templateService = (TemplateSolicitacaoServicoService) ServiceLocator
                    .getInstance().getService(TemplateSolicitacaoServicoService.class,
                            WebUtil.getUsuarioSistema(request));
            final TemplateSolicitacaoServicoDTO templateDto = templateService
                    .recuperaTemplateServico(solicitacaoServicoDto);
            if (templateDto != null && templateDto.isQuestionario()) {
                final Timestamp ts1 = UtilDatas.getDataHoraAtual();
                double tempo = 0;
                solicitacaoServicoQuestionarioDto = (SolicitacaoServicoQuestionarioDTO) request.getSession()
                        .getAttribute("dados_solicit_quest");
                while (solicitacaoServicoQuestionarioDto == null && tempo <= 10000) {
                    solicitacaoServicoQuestionarioDto = (SolicitacaoServicoQuestionarioDTO) request.getSession()
                            .getAttribute("dados_solicit_quest");
                    final Timestamp ts2 = UtilDatas.getDataHoraAtual();
                    tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(ts2, ts1);
                }
                if (solicitacaoServicoQuestionarioDto == null) {
                    document.alert(UtilI18N.internacionaliza(request,
                            "solicitacaoservico.validacao.informacoesComplementares"));
                    carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
                    return;
                }
            }

            solicitacaoServicoService.deserializaInformacoesComplementares(solicitacaoServicoDto,
                    solicitacaoServicoQuestionarioDto);

            final BaseConhecimentoDTO baseConhecimento = new BaseConhecimentoDTO();
            final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

            if (solicitacaoServicoDto != null) {
                baseConhecimento.setTitulo(solicitacaoServicoDto.getTituloBaseConhecimento());
                baseConhecimento.setConteudo("Descrição: " + solicitacaoServicoDto.getDescricaoSemFormatacao()
                        + "<br><br>" + "Solução/Resposta: " + solicitacaoServicoDto.getResposta());
            }
            baseConhecimento.setOrigem("5");// Serviço
            baseConhecimento
            .setDataExpiracao(UtilDatas.getSqlDate(UtilDatas.geraUmAnoSeguinte(UtilDatas.getDataAtual())));
            baseConhecimento.setStatus("N");
            baseConhecimento.setErroConhecido("S");
            baseConhecimento.setSituacao("EAV");
            baseConhecimento.setPrivacidade("C");
            baseConhecimento.setDataInicio(UtilDatas.getDataAtual());
            baseConhecimento.setArquivado("N");
            baseConhecimento.setVersao("1.0");
            baseConhecimento.setIdUsuarioAutor(usuarioDto.getIdUsuario());
            if (solicitacaoServicoDto != null) {
                solicitacaoServicoDto.setBeanBaseConhecimento(baseConhecimento);
            }

            if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdUnidade() == null
                    && solicitacaoServicoDto.getIdUnidade() != 0) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.unidadecontato"));
                verificaImpactoUrgencia(document, request, response);
                document.executeScript("habilitaBotaoGravar()");
                if (!bAlterarSituacao) {
                    document.executeScript("desabilitarSituacao();");
                }
                if (solicitacaoServicoQuestionarioDto != null) {
                    carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
                }
                return;

            }

            if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEscalar() != null
                    && solicitacaoServicoDto.getEscalar().equalsIgnoreCase("S")) {
                if (solicitacaoServicoDto.getIdGrupoAtual() == null) {
                    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.grupoatendimento"));
                    verificaImpactoUrgencia(document, request, response);
                    document.executeScript("habilitaBotaoGravar()");
                    if (!bAlterarSituacao) {
                        document.executeScript("desabilitarSituacao();");
                    }
                    if (solicitacaoServicoQuestionarioDto != null) {
                        carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
                    }
                    return;
                }
            }
            ServicoContratoDTO servicoContratoDto = null;
            if (solicitacaoServicoDto != null) {
                servicoContratoDto = getServicoContratoService().findByIdContratoAndIdServico(
                        solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
            }
            if (servicoContratoDto != null) {
                if (servicoContratoDto != null && solicitacaoServicoDto.getIdGrupoNivel1() == null
                        || solicitacaoServicoDto.getIdGrupoNivel1().intValue() <= 0) {
                    Integer idGrupoNivel1 = null;
                    if (servicoContratoDto.getIdGrupoNivel1() != null
                            && servicoContratoDto.getIdGrupoNivel1().intValue() > 0) {
                        idGrupoNivel1 = servicoContratoDto.getIdGrupoNivel1();
                    } else {
                        final String idGrupoN1 = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, null,
                                null);
                        if (idGrupoN1 != null && !idGrupoN1.trim().equalsIgnoreCase("")) {
                            try {
                                idGrupoNivel1 = new Integer(idGrupoN1);
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (idGrupoNivel1 == null || idGrupoNivel1.intValue() <= 0) {
                        document.alert(UtilI18N.internacionaliza(request,
                                "solicitacaoservico.validacao.grupoatendnivel"));
                        if (solicitacaoServicoQuestionarioDto != null) {
                            carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
                        }
                        return;
                    }

                }
            }

            final List<ConhecimentoSolicitacaoDTO> colConhecimentoSolicitacao = (List<ConhecimentoSolicitacaoDTO>) br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(ConhecimentoSolicitacaoDTO.class,
                            "colConhecimentoSolicitacao_Serialize", request);
            if (solicitacaoServicoDto != null) {
                solicitacaoServicoDto.setColConhecimentoSolicitacaoSerialize(colConhecimentoSolicitacao);
            }

            if (solicitacaoServicoDto != null
                    && solicitacaoServicoDto.getSituacao() != null
                    && solicitacaoServicoDto.getSituacao().equalsIgnoreCase(
                            Enumerados.SituacaoSolicitacaoServico.Resolvida.name())) {

                if (solicitacaoServicoDto.getResposta().trim().equalsIgnoreCase("")) {
                    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.solucaoresposta"));
                    verificaImpactoUrgencia(document, request, response);
                    document.executeScript("habilitaBotaoGravar()");
                    if (!bAlterarSituacao) {
                        document.executeScript("desabilitarSituacao();");
                    }
                    if (solicitacaoServicoQuestionarioDto != null) {
                        carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
                    }
                    return;
                }
                final TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator
                        .getInstance().getService(TipoDemandaServicoService.class, WebUtil.getUsuarioSistema(request));
                TipoDemandaServicoDTO tipoDemandaServicoDTO = new TipoDemandaServicoDTO();
                tipoDemandaServicoDTO.setIdTipoDemandaServico(solicitacaoServicoDto.getIdTipoDemandaServico());
                if (tipoDemandaServicoDTO.getIdTipoDemandaServico() == null) {
                    final SolicitacaoServicoDTO solicitacaoServicoAux = solicitacaoServicoService
                            .restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
                    tipoDemandaServicoDTO.setIdTipoDemandaServico(solicitacaoServicoAux.getIdTipoDemandaServico());
                }
                tipoDemandaServicoDTO = (TipoDemandaServicoDTO) tipoDemandaServicoService
                        .restore(tipoDemandaServicoDTO);
                if (tipoDemandaServicoDTO != null) {
                    if (tipoDemandaServicoDTO.getClassificacao().equalsIgnoreCase("I")) {
                        if (CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO.trim().equalsIgnoreCase("S")) {
                            if (solicitacaoServicoDto.getIdCausaIncidente() == null) {
                                document.alert(UtilI18N.internacionaliza(request,
                                        "citcorpore.comum.validacao.classifiqueincidente"));
                                verificaImpactoUrgencia(document, request, response);
                                document.executeScript("habilitaBotaoGravar()");
                                if (!bAlterarSituacao) {
                                    document.executeScript("desabilitarSituacao();");
                                }
                                if (solicitacaoServicoQuestionarioDto != null) {
                                    carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
                                }
                                return;
                            }
                            if (solicitacaoServicoDto.getIdCategoriaSolucao() == null) {
                                document.alert(UtilI18N.internacionaliza(request,
                                        "citcorpore.comum.validacao.classifiquesolucao"));
                                verificaImpactoUrgencia(document, request, response);
                                document.executeScript("habilitaBotaoGravar()");
                                if (!bAlterarSituacao) {
                                    document.executeScript("desabilitarSituacao();");
                                }
                                if (solicitacaoServicoQuestionarioDto != null) {
                                    carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
                                }
                                return;
                            }
                        }
                    }
                }

                final boolean bvalidaBaseConhecimento = solicitacaoServicoDto.getValidaBaseConhecimento() != null
                        && solicitacaoServicoDto.getValidaBaseConhecimento().equalsIgnoreCase("S");

                if (bvalidaBaseConhecimento) {
                    final boolean informouBaseConhecimento = solicitacaoServicoDto
                            .getColConhecimentoSolicitacaoSerialize() != null
                            && solicitacaoServicoDto.getColConhecimentoSolicitacaoSerialize().size() > 0;
                    if (!informouBaseConhecimento) {
                        document.alert(UtilI18N
                                        .internacionaliza(request, "citcorpore.comum.validacao.baseconhecimento"));
                        verificaImpactoUrgencia(document, request, response);
                        document.executeScript("habilitaBotaoGravar()");
                        if (!bAlterarSituacao) {
                            document.executeScript("desabilitarSituacao();");
                        }
                        if (solicitacaoServicoQuestionarioDto != null) {
                            carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
                        }
                        return;
                    }
                }
            }

            final Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute(
                    "colUploadsGED2");

            if (solicitacaoServicoDto != null) {
                solicitacaoServicoDto.setColArquivosUpload(arquivosUpados);
            }

            final Collection colItensProblema = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                    ProblemaDTO.class, "colItensProblema_Serialize", request);
            if (solicitacaoServicoDto != null) {
                solicitacaoServicoDto.setColItensProblema(colItensProblema);
            }

            final List<RequisicaoMudancaDTO> colItensMudanca = (List<RequisicaoMudancaDTO>) br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(RequisicaoMudancaDTO.class, "colItensMudanca_Serialize", request);
            if (solicitacaoServicoDto != null) {
                solicitacaoServicoDto.setColItensMudanca(colItensMudanca);
            }

            final List<ItemConfiguracaoDTO> colItensIC = (List<ItemConfiguracaoDTO>) br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(ItemConfiguracaoDTO.class, "colItensIC_Serialize", request);
            if (solicitacaoServicoDto != null) {
                solicitacaoServicoDto.setColItensICSerialize(colItensIC);
            }

            final List<BaseConhecimentoDTO> colItensBaseConhecimento = (List<BaseConhecimentoDTO>) br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(BaseConhecimentoDTO.class, "colItensBaseConhecimento_Serialize",
                            request);
            if (solicitacaoServicoDto != null) {
                solicitacaoServicoDto.setColItensBaseConhecimento(colItensBaseConhecimento);
            }

            if (solicitacaoServicoDto != null) {
                solicitacaoServicoDto.setUsuarioDto(usuario);
                solicitacaoServicoDto.setRegistradoPor(usuario.getNomeUsuario());
            }

            try {
                if (solicitacaoServicoDto != null
                        && (solicitacaoServicoDto.getIdSolicitacaoServico() == null || solicitacaoServicoDto
                        .getIdSolicitacaoServico().intValue() == 0)) {
                    solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService
                            .create(solicitacaoServicoDto);

                    // Registra o email se tiver sido utilizado
                    final EmailSolicitacaoServicoService emailSolicitacaoServicoService = (EmailSolicitacaoServicoService) ServiceLocator
                            .getInstance().getService(EmailSolicitacaoServicoService.class,
                                    WebUtil.getUsuarioSistema(request));
                    if (solicitacaoServicoDto != null && solicitacaoServicoDto.getMessageId() != null
                            && solicitacaoServicoDto.getMessageId().trim().length() > 0) {
                        final EmailSolicitacaoServicoDTO emailDto = new EmailSolicitacaoServicoDTO();
                        emailDto.setIdSolicitacao(solicitacaoServicoDto.getIdSolicitacaoServico());
                        emailDto.setIdMessage(solicitacaoServicoDto.getMessageId());
                        emailDto.setOrigem(TipoOrigemLeituraEmail.SOLICITACAO_SERVICO.toString());
                        emailSolicitacaoServicoService.create(emailDto);
                    }

                    document.executeScript("$('#divInformacoesComplementares').switchClass( 'ativo', 'inativo', null );");

                    String comando = "mostraMensagemInsercao('<h3>" + UtilI18N.internacionaliza(request, "MSG05")
                            + ".<br>" + UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao")
                            + " <b><u>" + solicitacaoServicoDto.getIdSolicitacaoServico() + "</u></b> "
                            + UtilI18N.internacionaliza(request, "citcorpore.comum.crida") + ".<br><br>"
                            + UtilI18N.internacionaliza(request, "prioridade.prioridade") + ": "
                            + solicitacaoServicoDto.getIdPrioridade();
                    if (solicitacaoServicoDto.getPrazoHH() > 0 || solicitacaoServicoDto.getPrazoMM() > 0) {
                        comando = comando + " - SLA: " + solicitacaoServicoDto.getSLAStr() + "";
                    }
                    comando = comando + "</h3>')";
                    document.executeScript(comando);
                    return;
                } else {
                    // Registra o email se tiver sido utilizado
                    final EmailSolicitacaoServicoService emailSolicitacaoServicoService = (EmailSolicitacaoServicoService) ServiceLocator
                            .getInstance().getService(EmailSolicitacaoServicoService.class,
                                    WebUtil.getUsuarioSistema(request));
                    if (solicitacaoServicoDto != null && solicitacaoServicoDto.getMessageId() != null
                            && solicitacaoServicoDto.getMessageId().trim().length() > 0) {
                        EmailSolicitacaoServicoDTO emailDto = emailSolicitacaoServicoService
                                .getEmailByIdSolicitacaoAndOrigem(solicitacaoServicoDto.getIdSolicitacaoServico(),
                                        TipoOrigemLeituraEmail.SOLICITACAO_SERVICO.toString());

                        if (emailDto != null && emailDto.getIdEmail() != null) {
                            emailDto.setIdMessage(solicitacaoServicoDto.getMessageId());
                            emailDto.setOrigem(TipoOrigemLeituraEmail.SOLICITACAO_SERVICO.toString());
                            emailSolicitacaoServicoService.update(emailDto);
                        } else {
                            emailDto = new EmailSolicitacaoServicoDTO();
                            emailDto.setIdSolicitacao(solicitacaoServicoDto.getIdSolicitacaoServico());
                            emailDto.setIdMessage(solicitacaoServicoDto.getMessageId());
                            emailDto.setOrigem(TipoOrigemLeituraEmail.SOLICITACAO_SERVICO.toString());
                            emailSolicitacaoServicoService.create(emailDto);
                        }
                    }

                    document.alert(UtilI18N.internacionaliza(request, "MSG06"));
                }
            } catch (final Exception e) {
                if (!bExisteSolicitacao) {
                    solicitacaoServicoDto.setIdSolicitacaoServico(null);
                }
                if (solicitacaoServicoQuestionarioDto != null) {
                    carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
                }
                String msgErro = e.getMessage();
                msgErro = msgErro.replaceAll("java.lang.Exception:", "");
                msgErro = msgErro.replaceAll("br.com.citframework.excecao.ServiceException:", "");
                msgErro = msgErro.replaceAll("br.com.citframework.excecao.LogicException:", "");
                msgErro = msgErro.replaceAll("br.com.citframework.excecao.LogicException:", "");
                msgErro = msgErro.replaceAll("br.com.centralit.citcorpore.exception.LogicException:", "");
                msgErro = msgErro.replaceAll("br.com.centralit.citajax.exception.LogicException:", "");
                msgErro = msgErro.replaceAll("Wrapped", "");
                msgErro = msgErro.replaceAll("params.get\\(\"execucaoFluxo\"\\).recuperaGrupoAprovador\\(\\);", "");
                msgErro = msgErro.replaceAll("!params.get\\(\"execucaoFluxo\"\\).exigeAutorizacao\\(\\);", "");
                msgErro = msgErro.replaceAll("\\(script#1\\)", "");
                document.alert(UtilI18N.internacionaliza(request, msgErro));
                verificaImpactoUrgencia(document, request, response);
                document.executeScript("habilitaBotaoGravar()");
                if (!bAlterarSituacao) {
                    document.executeScript("desabilitarSituacao();");
                }
                return;
            }

            if (solicitacaoServicoDto != null && solicitacaoServicoDto.getReclassificar() != null
                    && solicitacaoServicoDto.getReclassificar().equals("S")) {
                document.executeScript("document.getElementById('divBotoes').style.display = 'block';");
            }

            carregaInformacoesComplementares(document, request, solicitacaoServicoDto);

            final String reclassificar = request.getParameter("reclassificar");
            final String gravaContinua = request.getParameter("acaoFluxo");
            if (reclassificar != null && reclassificar != "" && reclassificar.equals("S") || gravaContinua != null
                    && gravaContinua != "" && gravaContinua.equals("I")) {
                document.executeScript("parent.refreshTelaGerenciamento()");
                request.getSession().setAttribute("gravaEContinua", null);
            } else {
                document.executeScript("parent.carregaListaServico()");
            }
            solicitacaoServicoDto = null;
        } finally {
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        }
    }

    private boolean isContratoInList(final Integer idContrato, final Collection colContratosColab) {
        if (colContratosColab != null) {
            for (final Iterator it = colContratosColab.iterator(); it.hasNext();) {
                final ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
                if (contratosGruposDTO.getIdContrato().intValue() == idContrato.intValue()) {
                    return true;
                }
            }
        }
        return false;
    }

    public void sincronizaAD(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        if (solicitacaoServicoDto.getFiltroADPesq() == null) {
            solicitacaoServicoDto.setFiltroADPesq(document.getElementById("filtroADPesq").getValue());
        }

        ContratoDTO contratoDto = new ContratoDTO();

        contratoDto.setIdContrato(solicitacaoServicoDto.getIdContrato());

        contratoDto = (ContratoDTO) getContratoService().restore(contratoDto);

        final Collection<ADUserDTO> listUsuariosADDto = LDAPUtils.consultaEmpregado(
                solicitacaoServicoDto.getFiltroADPesq(), contratoDto.getIdGrupoSolicitante());

        if (listUsuariosADDto != null && !listUsuariosADDto.isEmpty()) {

            for (final ADUserDTO usuarioADDto : listUsuariosADDto) {

                document.getElementById("POPUP_SINCRONIZACAO_DETALHE").setInnerHTML(
                        "Sincronização concluída com sucesso. Favor fazer a busca na lookup de colaborador.");
            }

        } else {

            document.getElementById("POPUP_SINCRONIZACAO_DETALHE").setInnerHTML("Nenhum resultado encontrado.");

        }
        document.executeScript("fechar_aguarde();");

        solicitacaoServicoDto = null;

        contratoDto = null;
    }

    /**
     * Preenche a Combo de Unidades de acordo com o Parâmetro UNIDADE_VINC_CONTRATOS.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void carregaUnidade(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");

        // Se o parâmetro de ativação do campo autocomplete unidade for diferente de 'N' carrego a combo de unidade
        if (UNIDADE_AUTOCOMPLETE != null && !UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")) {

            final String parametroUnidadeVinculadoAContratos = ParametroUtil.getValorParametroCitSmartHashMap(
                    Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");

            SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

            if (solicitacaoServicoDto.getIdSolicitacaoServico() != null
                    && solicitacaoServicoDto.getIdSolicitacaoServico().intValue() > 0) {

                solicitacaoServicoDto = (SolicitacaoServicoDTO) getSolicitacaoServicoService().restore(
                        solicitacaoServicoDto);

                ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
                servicoContratoDTO.setIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());
                if (solicitacaoServicoDto.getIdServicoContrato() != null) {
                    servicoContratoDTO = (ServicoContratoDTO) getServicoContratoService().restore(servicoContratoDTO);
                } else {
                    servicoContratoDTO = null;
                }
                if (servicoContratoDTO != null) {
                    solicitacaoServicoDto.setIdServico(servicoContratoDTO.getIdServico());
                    solicitacaoServicoDto.setIdContrato(servicoContratoDTO.getIdContrato());
                }

            }

            if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
                solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
            }

            final HTMLSelect comboUnidadeMultContratos = document.getSelectById("idUnidade");

            inicializarCombo(comboUnidadeMultContratos, request);

            if (parametroUnidadeVinculadoAContratos.trim().equalsIgnoreCase("S")) {

                final Integer idContrato = solicitacaoServicoDto.getIdContrato();

                if (idContrato != null) {

                    final ArrayList<UnidadeDTO> unidades = (ArrayList) getUnidadeService()
                            .listHierarquiaMultiContratos(idContrato);

                    if (unidades != null && !unidades.isEmpty()) {
                        for (final UnidadeDTO unidade : unidades) {
                            if (unidade.getDataFim() == null) {
                                comboUnidadeMultContratos.addOption(
                                        Util.tratarAspasSimples(unidade.getIdUnidade().toString()),
                                        Util.tratarAspasSimples(unidade.getNomeNivel()));
                            }

                        }
                    }
                }
            } else {

                final ArrayList<UnidadeDTO> unidades = (ArrayList) getUnidadeService().listHierarquia();

                if (unidades != null && !unidades.isEmpty()) {
                    for (final UnidadeDTO unidade : unidades) {
                        if (unidade.getDataFim() == null) {
                            comboUnidadeMultContratos.addOption(
                                    Util.tratarAspasSimples(unidade.getIdUnidade().toString()),
                                    Util.tratarAspasSimples(unidade.getNomeNivel()));
                        }
                    }
                }
            }

            solicitacaoServicoDto = null;
        }

    }

    private void inicializarCombo(final HTMLSelect componenteCombo, final HttpServletRequest request) {
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("0", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
    }

    public void carregaServicos(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        // carregaServicosMulti(document, request, response);
    }

    public void verificaImpactoUrgencia(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
            solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
        }
        document.getSelectById("impacto").setDisabled(false);
        document.getSelectById("urgencia").setDisabled(false);
        if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdContrato() == null) {
            return;
        }

        ServicoContratoDTO servicoContratoDto = getServicoContratoService().findByIdContratoAndIdServico(
                solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());

        if (servicoContratoDto != null) {
            AcordoNivelServicoDTO acordoNivelServicoDto = getAcordoNivelServicoService().findAtivoByIdServicoContrato(
                    servicoContratoDto.getIdServicoContrato(), "T");
            if (acordoNivelServicoDto == null) {
                // Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato, entao busca um acordo geral que esteja
                // vinculado ao servicocontrato.
                final AcordoServicoContratoDTO acordoServicoContratoDTO = getAcordoServicoContratoService()
                        .findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
                if (acordoServicoContratoDTO == null) {
                    document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.tempoacordo"));
                    return;
                }
                // Apos achar a vinculacao do acordo com o servicocontrato, entao faz um restore do acordo de nivel de servico.
                acordoNivelServicoDto = new AcordoNivelServicoDTO();
                acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
                acordoNivelServicoDto = (AcordoNivelServicoDTO) new AcordoNivelServicoDao()
                .restore(acordoNivelServicoDto);
                if (acordoNivelServicoDto == null) {
                    // Se nao houver acordo especifico, ou seja, associado direto ao servicocontrato
                    document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.tempoacordo"));
                    return;
                }
            }
            if (acordoNivelServicoDto.getImpacto() != null) {
                document.getSelectById("impacto").setValue("" + acordoNivelServicoDto.getImpacto());
                if (acordoNivelServicoDto.getPermiteMudarImpUrg() != null
                        && acordoNivelServicoDto.getPermiteMudarImpUrg().equalsIgnoreCase("N")) {
                    document.getSelectById("impacto").setDisabled(true);
                }
            } else {
                document.getSelectById("impacto").setValue("B");
            }
            if (acordoNivelServicoDto.getUrgencia() != null) {
                document.getSelectById("urgencia").setValue("" + acordoNivelServicoDto.getUrgencia());
                if (acordoNivelServicoDto.getPermiteMudarImpUrg() != null
                        && acordoNivelServicoDto.getPermiteMudarImpUrg().equalsIgnoreCase("N")) {
                    document.getSelectById("urgencia").setDisabled(true);
                }
            } else {
                document.getSelectById("urgencia").setValue("B");
            }
        } else {
            document.getSelectById("impacto").setValue("B");
            document.getSelectById("urgencia").setValue("B");
        }

        servicoContratoDto = null;

        solicitacaoServicoDto = null;
    }

    public void restore(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        final String editar = request.getParameter("editar");

        final SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator
                .getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
        final ContatoSolicitacaoServicoService contatoSolicitacaoServicoService = (ContatoSolicitacaoServicoService) ServiceLocator
                .getInstance().getService(ContatoSolicitacaoServicoService.class, null);
        final SolicitacaoServicoEvtMonService solicitacaoServicoEvtMonService = (SolicitacaoServicoEvtMonService) ServiceLocator
                .getInstance().getService(SolicitacaoServicoEvtMonService.class, WebUtil.getUsuarioSistema(request));
        final EventoMonitoramentoService eventoMonitoramentoService = (EventoMonitoramentoService) ServiceLocator
                .getInstance().getService(EventoMonitoramentoService.class, WebUtil.getUsuarioSistema(request));
        final EventoMonitConhecimentoService eventoMonitConhecimentoService = (EventoMonitConhecimentoService) ServiceLocator
                .getInstance().getService(EventoMonitConhecimentoService.class, WebUtil.getUsuarioSistema(request));
        final BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance()
                .getService(BaseConhecimentoService.class, WebUtil.getUsuarioSistema(request));

        final Integer idTarefa = solicitacaoServicoDto.getIdTarefa();
        final String acaoFluxo = solicitacaoServicoDto.getAcaoFluxo();
        final String reclassificar = solicitacaoServicoDto.getReclassificar();
        final String escalar = solicitacaoServicoDto.getEscalar();
        final String alterarSituacao = solicitacaoServicoDto.getAlterarSituacao();

        final String validaBaseConhecimento = solicitacaoServicoDto.getValidaBaseConhecimento();

        solicitacaoServicoDto = solicitacaoServicoService.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());

        if (solicitacaoServicoDto == null) {
            document.alert(UtilI18N.internacionaliza(request,
                    "solicitacaoServico.solicitacaoServico.msg.registronaoencotrado"));
            return;
        }

        solicitacaoServicoDto.setIdTarefa(idTarefa);
        solicitacaoServicoDto.setAcaoFluxo(acaoFluxo);
        solicitacaoServicoDto.setReclassificar(reclassificar);
        solicitacaoServicoDto.setEscalar(escalar);
        solicitacaoServicoDto.setAlterarSituacao(alterarSituacao);
        solicitacaoServicoDto.setValidaBaseConhecimento(validaBaseConhecimento);

        EmpregadoDTO empregadoDTO = new EmpregadoDTO();
        if (solicitacaoServicoDto.getIdSolicitante() != null) {
            empregadoDTO.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
            empregadoDTO = (EmpregadoDTO) getEmpregadoService().restore(empregadoDTO);
        }
        ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
        servicoContratoDTO.setIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());

        if (solicitacaoServicoDto.getIdServicoContrato() != null) {
            servicoContratoDTO = (ServicoContratoDTO) getServicoContratoService().restore(servicoContratoDTO);
        } else {
            servicoContratoDTO = null;
        }

        if (servicoContratoDTO != null) {
            solicitacaoServicoDto.setIdServico(servicoContratoDTO.getIdServico());
            solicitacaoServicoDto.setIdContrato(servicoContratoDTO.getIdContrato());
            ServicoDTO servicoDto = new ServicoDTO();
            servicoDto.setIdServico(servicoContratoDTO.getIdServico());
            servicoDto = (ServicoDTO) getServicoService().restore(servicoDto);

            if (servicoDto != null) {
                solicitacaoServicoDto.setIdCategoriaServico(servicoDto.getIdCategoriaServico());
            }

        }

        ContatoSolicitacaoServicoDTO contatoSolicitacaoServicoDTO = null;
        if (solicitacaoServicoDto.getIdContatoSolicitacaoServico() != null) {
            contatoSolicitacaoServicoDTO = new ContatoSolicitacaoServicoDTO();
            contatoSolicitacaoServicoDTO.setIdcontatosolicitacaoservico(solicitacaoServicoDto
                    .getIdContatoSolicitacaoServico());
            contatoSolicitacaoServicoDTO = (ContatoSolicitacaoServicoDTO) contatoSolicitacaoServicoService
                    .restore(contatoSolicitacaoServicoDTO);
        }

        if (contatoSolicitacaoServicoDTO != null) {
            solicitacaoServicoDto.setNomecontato(contatoSolicitacaoServicoDTO.getNomecontato());
            solicitacaoServicoDto.setEmailcontato(contatoSolicitacaoServicoDTO.getEmailcontato());
            solicitacaoServicoDto.setTelefonecontato(contatoSolicitacaoServicoDTO.getTelefonecontato());
            solicitacaoServicoDto.setRamal(contatoSolicitacaoServicoDTO.getRamal());
            solicitacaoServicoDto.setObservacao(contatoSolicitacaoServicoDTO.getObservacao());
            solicitacaoServicoDto.setIdLocalidade(contatoSolicitacaoServicoDTO.getIdLocalidade());
            preencherComboLocalidade(document, request, response);
        }

        ItemConfiguracaoDTO itemConfiguracaoDTO = null;
        ItemConfiguracaoDTO itemConfiguracaoFilhoDTO = null;
        String tagItemCfg = "";
        if (solicitacaoServicoDto.getIdItemConfiguracao() != null) {
            itemConfiguracaoDTO = new ItemConfiguracaoDTO();
            itemConfiguracaoDTO.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracao());
            itemConfiguracaoDTO = (ItemConfiguracaoDTO) getItemConfiguracaoService().restore(itemConfiguracaoDTO);

            if (solicitacaoServicoDto.getIdItemConfiguracaoFilho() != null) {
                itemConfiguracaoFilhoDTO = new ItemConfiguracaoDTO();
                itemConfiguracaoFilhoDTO.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracaoFilho());
                itemConfiguracaoFilhoDTO = (ItemConfiguracaoDTO) getItemConfiguracaoService().restore(
                        itemConfiguracaoFilhoDTO);
                if (itemConfiguracaoFilhoDTO != null && itemConfiguracaoFilhoDTO.getIdTipoItemConfiguracao() != null) {
                    TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO = new TipoItemConfiguracaoDTO();
                    tipoItemConfiguracaoDTO.setId(itemConfiguracaoFilhoDTO.getIdTipoItemConfiguracao());
                    tipoItemConfiguracaoDTO = (TipoItemConfiguracaoDTO) getItemConfiguracaoService().restore(
                            tipoItemConfiguracaoDTO);
                    if (tipoItemConfiguracaoDTO != null) {
                        tagItemCfg = tipoItemConfiguracaoDTO.getTag();
                    }
                }
            }
        }

        if (solicitacaoServicoDto.getSolicitanteUnidade() == null) {
            solicitacaoServicoDto.setSolicitanteUnidade("");
        }
        if (solicitacaoServicoDto.getSolicitante() == null) {
            solicitacaoServicoDto.setSolicitante("");
        }

        if (itemConfiguracaoDTO != null) {
            document.getTextBoxById("itemConfiguracao").setValue(itemConfiguracaoDTO.getIdentificacao());
            /* document.executeScript("exibeCampos()"); */
            document.setBean(solicitacaoServicoDto);
            if (tagItemCfg != null && tagItemCfg.equalsIgnoreCase("SOFTWARES")) {
                document.executeScript("document.form.caracteristica[0].checked = true");
                preecherComboSoftware(document, request, response);
            } else {
                preecherComboHardware(document, request, response);
            }
        }

        if (solicitacaoServicoDto.getEditar() == null) {
            solicitacaoServicoDto.setEditar("S");
        }

        if (empregadoDTO != null) {
            document.getTextBoxById("solicitante").setValue(empregadoDTO.getNome());
        }

        if (solicitacaoServicoDto.getReclassificar() != null
                && solicitacaoServicoDto.getReclassificar().equalsIgnoreCase("S")) {
            // document.getElementById("divMessage").setInnerHTML("<font color='red'>" + UtilI18N.internacionaliza(request,
            // "solicitacaoServico.solicitacaoServico.msg.reclassificacao") + ".</font>");
        } else {
            document.getSelectById("idCategoriaServico").setDisabled(true);
            document.getSelectById("idContrato").setDisabled(true);
            document.getSelectById("idServico").setDisabled(true);
            document.getSelectById("idTipoDemandaServico").setDisabled(true);
            document.getSelectById("utilizaCategoriaServico").setDisabled(true);
            document.getSelectById("checkUtilizaCategoriaServico").setDisabled(true);
            document.getSelectById("uniform-utilizaCategoriaServico").setDisabled(true);
            document.getSelectById("urgencia").setDisabled(true);
            document.getSelectById("impacto").setDisabled(true);
            if (solicitacaoServicoDto.getEditar() == null) {
                solicitacaoServicoDto.setEditar("S");
            }
        }

        final boolean bEscalar = solicitacaoServicoDto.getEscalar() != null
                && solicitacaoServicoDto.getEscalar().equalsIgnoreCase("S");
        final boolean bAlterarSituacao = solicitacaoServicoDto.getAlterarSituacao() != null
                && solicitacaoServicoDto.getAlterarSituacao().equalsIgnoreCase("S");
        if (!bAlterarSituacao) {
            document.executeScript("desabilitarSituacao();");
        }
        if (!bEscalar) {
            document.getSelectById("idGrupoAtual").setDisabled(true);
        }

        final Collection colAnexos = getControleGedService().listByIdTabelaAndID(
                ControleGEDDTO.TABELA_SOLICITACAOSERVICO, solicitacaoServicoDto.getIdSolicitacaoServico());
        final Collection colAnexosUploadDTO = getControleGedService().convertListControleGEDToUploadDTO(colAnexos);
        String quantidadeAnexosStr = "0";
        if (colAnexosUploadDTO != null && colAnexosUploadDTO.size() > 0) {
            final Integer quantidadeAnexos = colAnexosUploadDTO.size();
            quantidadeAnexosStr = String.valueOf(quantidadeAnexos);
            document.getElementById("quantidadeAnexos").setValue(quantidadeAnexosStr);
        } else {
            document.getElementById("quantidadeAnexos").setValue(quantidadeAnexosStr);
        }

        request.getSession(true).setAttribute("colUploadsGED2", colAnexosUploadDTO);
        request.getSession().setAttribute("colUploadsGED2", colAnexosUploadDTO);

        final Collection colOcorrencias = getOcorrenciaSolicitacaoService().findByIdSolicitacaoServico(
                solicitacaoServicoDto.getIdSolicitacaoServico());
        if (colOcorrencias != null && colOcorrencias.size() > 0) {

            final String str = listInfoRegExecucaoSolicitacao(colOcorrencias, request);
            request.setAttribute("strRegistrosExecucao", str);

            /**
             * Quantitativo de ocorrencia
             *
             * @author thays.araujo
             */
            String quantidadeOcorrenciasStr = "0";
            Integer quantidadeOcorrencias;
            quantidadeOcorrencias = colOcorrencias.size();
            quantidadeOcorrenciasStr = String.valueOf(quantidadeOcorrencias);
            document.getElementById("quantidadeOcorrencias").setValue(quantidadeOcorrenciasStr);

        }

        carregaInformacoesComplementares(document, request, solicitacaoServicoDto);

        /**
         * Verifica o uso da versão free
         */
        if (!br.com.citframework.util.Util.isVersionFree(request)) {

            final HTMLTable tblProblema = document.getTableById("tblProblema");
            tblProblema.deleteAllRows();

            if (solicitacaoServicoDto != null) {
                final ProblemaDTO problemadto = new ProblemaDTO();
                problemadto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
                final Collection col = getProblemaService().findByIdSolictacaoServico(
                        problemadto.getIdSolicitacaoServico());
                if (col != null) {
                    tblProblema.addRowsByCollection(col, new String[] {"numberAndTitulo", "status", ""},
                            new String[] {"idProblema"}, "Problema já cadastrado!!",
                            new String[] {"exibeIconesProblema"}, null, null);
                    document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");
                    /**
                     * Quantitativo de Problemas
                     *
                     * @author thays.araujo
                     */
                    String quantidadeProblemaStr = "0";
                    Integer quantidadeProblema;
                    quantidadeProblema = col.size();
                    quantidadeProblemaStr = String.valueOf(quantidadeProblema);
                    document.getElementById("quantidadeProblema").setValue(quantidadeProblemaStr);
                }
            }

            final HTMLTable tblMudanca = document.getTableById("tblMudanca");
            tblMudanca.deleteAllRows();

            if (solicitacaoServicoDto != null) {
                final RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaDTO();
                requisicaoMudancaDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
                final Collection col = getRequisicaoMudancaService().findBySolictacaoServico(requisicaoMudancaDTO);
                if (col != null) {
                    tblMudanca.addRowsByCollection(col, new String[] {"numberAndTitulo", "status", ""},
                            new String[] {"idRequisicaoMudanca"}, "Mudança já cadastrado!!",
                            new String[] {"exibeIconesMudanca"}, null, null);
                    document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblMudanca', 'tblMudanca');");

                    /**
                     * Quantitativo de Mudanca
                     *
                     * @author thays.araujo
                     */
                    String quantidadeMudancaStr = "0";
                    Integer quantidadeMudanca;
                    quantidadeMudanca = col.size();
                    quantidadeMudancaStr = String.valueOf(quantidadeMudanca);
                    document.getElementById("quantidadeMudanca").setValue(quantidadeMudancaStr);
                }
            }

            final HTMLTable tblBaseConhecimento = document.getTableById("tblBaseConhecimento");
            tblBaseConhecimento.deleteAllRows();
            final ConhecimentoSolicitacaoDTO conhecimentoSolicitacaoDTO = new ConhecimentoSolicitacaoDTO();
            conhecimentoSolicitacaoDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
            final Collection colConhecimentoSolicitacao = getConhecimentoSolicitacaoService().findBySolictacaoServico(
                    conhecimentoSolicitacaoDTO);

            if (colConhecimentoSolicitacao != null) {
                tblBaseConhecimento.addRowsByCollection(colConhecimentoSolicitacao, new String[] {"idBaseConhecimento",
                        "titulo", ""}, new String[] {"idBaseConhecimento"},
                        UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"),
                        new String[] {"exibeIconesBaseConhecimento"}, null, null);

                document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblBaseConhecimento', 'tblBaseConhecimento');");

                /**
                 * Quantitativo de Base Conhecimento
                 *
                 * @author thays.araujo
                 */
                String quantidadeBaseConhecimentoStr = "0";
                Integer quantidadeBaseConhecimento;
                quantidadeBaseConhecimento = colConhecimentoSolicitacao.size();
                quantidadeBaseConhecimentoStr = String.valueOf(quantidadeBaseConhecimento);
                document.getElementById("quantidadeBaseConhecimento").setValue(quantidadeBaseConhecimentoStr);
            }

            final HTMLTable tblIC = document.getTableById("tblIC");
            tblIC.deleteAllRows();

            if (solicitacaoServicoDto != null) {
                final ItemCfgSolicitacaoServDTO itemCfgSolicitacaoServDTO = new ItemCfgSolicitacaoServDTO();
                final Collection col = getItemCfgSolicitacaoServService().findByIdSolicitacaoServico(
                        solicitacaoServicoDto.getIdSolicitacaoServico());

                if (col != null) {
                    for (final Iterator it = col.iterator(); it.hasNext();) {
                        final ItemCfgSolicitacaoServDTO itemCfgSolicitacaoServAux = (ItemCfgSolicitacaoServDTO) it
                                .next();
                        ItemConfiguracaoDTO itemConfiguracaoAux = new ItemConfiguracaoDTO();
                        itemConfiguracaoAux.setIdItemConfiguracao(itemCfgSolicitacaoServAux.getIdItemConfiguracao());
                        itemConfiguracaoAux = (ItemConfiguracaoDTO) getItemConfiguracaoService().restore(
                                itemConfiguracaoAux);
                        if (itemConfiguracaoAux != null) {
                            itemCfgSolicitacaoServAux.setIdentificacaoStatus(itemConfiguracaoAux
                                    .getIdentificacaoStatus());
                        }
                    }
                }

                if (col != null) {
                    tblIC.addRowsByCollection(col, new String[] {"idItemConfiguracao", "identificacao", "", ""},
                            new String[] {"idItemConfiguracao"}, "Item Configuração já cadastrado!!",
                            new String[] {"exibeIconesIC"}, null, null);

                    document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblIC', 'tblIC');");

                    /**
                     * Quantitativo de Item Configuração
                     *
                     * @author thays.araujo
                     */
                    String quantidadeItemConfiguracaoStr = "0";
                    Integer quantidadeItemConfiguracao;
                    quantidadeItemConfiguracao = col.size();
                    quantidadeItemConfiguracaoStr = String.valueOf(quantidadeItemConfiguracao);
                    document.getElementById("quantidadeItemConfiguracao").setValue(quantidadeItemConfiguracaoStr);
                }
            }
        }

        abrirListaDeSubSolicitacoes(document, request, response);
        /**
         * Quantitativo de Incidentes Relacionados
         *
         * @author thays.araujo
         */
        setQuantitativoIncidentesRelacionados(document, request, response);

        final StringBuilder strEventos = new StringBuilder();
        final Collection colEventsSolic = solicitacaoServicoEvtMonService.findByIdSolicitacao(solicitacaoServicoDto
                .getIdSolicitacaoServico());
        if (colEventsSolic != null && colEventsSolic.size() > 0) {
            strEventos.append("<table border='1' width='100%'>");
            for (final Iterator it = colEventsSolic.iterator(); it.hasNext();) {
                final SolicitacaoServicoEvtMonDTO solicitacaoServicoEvtMonDTO = (SolicitacaoServicoEvtMonDTO) it.next();
                EventoMonitoramentoDTO eventoMonitoramentoDto = new EventoMonitoramentoDTO();
                eventoMonitoramentoDto.setIdEventoMonitoramento(solicitacaoServicoEvtMonDTO.getIdEventoMonitoramento());
                eventoMonitoramentoDto = (EventoMonitoramentoDTO) eventoMonitoramentoService
                        .restore(eventoMonitoramentoDto);
                if (eventoMonitoramentoDto != null) {
                    final Collection<EventoMonitConhecimentoDTO> colEventos = eventoMonitConhecimentoService
                            .listByIdEventoMonitoramento(eventoMonitoramentoDto.getIdEventoMonitoramento());
                    Integer[] ids = null;
                    if (colEventos != null && colEventos.size() > 0) {
                        ids = new Integer[colEventos.size()];
                        int x = 0;
                        for (final Object element : colEventos) {
                            final EventoMonitConhecimentoDTO eventoMonitConhecimentoDTO = (EventoMonitConhecimentoDTO) element;
                            ids[x] = eventoMonitConhecimentoDTO.getIdBaseConhecimento();
                            x++;
                        }
                    }

                    final Collection colBasesConhec = baseConhecimentoService.listarBaseConhecimentoByIds(ids);

                    final StringBuilder strBC = new StringBuilder();

                    if (colBasesConhec != null && colBasesConhec.size() > 0) {
                        strBC.append("<table width='100%'>");
                        for (final Iterator itBC = colBasesConhec.iterator(); itBC.hasNext();) {
                            final BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) itBC.next();
                            final String onclickStr = "onclick='abreConhecimento(\""
                                    + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")
                                    + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
                                    + "/pages\", \"idBaseConhecimento=" + baseConhecimentoDto.getIdBaseConhecimento()
                                    + "\")'";
                            strBC.append("<tr>");
                            strBC.append("<td>");
                            strBC.append("<img style='cursor:pointer' src='"
                                    + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")
                                    + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
                                    + "/imagens/script.png' border='0' " + onclickStr + "/>");
                            strBC.append("</td>");
                            strBC.append("<td style='cursor:pointer' " + onclickStr + ">");
                            strBC.append("" + UtilStrings.retiraAspasApostrofe(baseConhecimentoDto.getTitulo()));
                            strBC.append("</td>");
                            strBC.append("</tr>");
                        }
                        strBC.append("</table>");
                    }

                    strEventos.append("<tr>");
                    strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
                    strEventos.append("<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")
                            + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
                            + "/imagens/relampago.png' border='0'/>");
                    strEventos.append("</td>");
                    strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
                    strEventos.append(UtilStrings.retiraAspasApostrofe(eventoMonitoramentoDto.getNomeEvento()));
                    strEventos.append("</td>");
                    strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
                    strEventos.append(UtilStrings.retiraAspasApostrofe(UtilStrings
                            .nullToVazio(solicitacaoServicoEvtMonDTO.getNomeHost())));
                    strEventos.append("</td>");
                    strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
                    strEventos.append(UtilStrings.retiraAspasApostrofe(UtilStrings
                            .nullToVazio(solicitacaoServicoEvtMonDTO.getNomeService())));
                    strEventos.append("</td>");
                    strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
                    strEventos.append(strBC.toString());
                    strEventos.append("</td>");
                    strEventos.append("</tr>");
                }
            }
            strEventos.append("</table>");
        }

        /*
         * Desenvolvedor: Mário Júnior - Data: 23/10/2013 - Horário: 16h00min - ID Citsmart: 122010 - Motivo/Comentário: Inserção do título
         * da solicitação, informação do frame de solicitação
         */
        final String tarefa = request.getParameter("idTarefa");
        String responsavelAtual = "";
        String tarefaAtual = "";

        if (solicitacaoServicoDto.getIdTarefa() != null || tarefa != null) {
            ItemTrabalho itemTrabalho = null;
            if (solicitacaoServicoDto.getIdTarefa() != null) {
                itemTrabalho = solicitacaoServicoService.getItemTrabalho(solicitacaoServicoDto.getIdTarefa());
            } else {
                final Integer idTarefaStr = Integer.parseInt(tarefa);
                itemTrabalho = solicitacaoServicoService.getItemTrabalho(idTarefaStr);
            }
            if (itemTrabalho != null) {
                tarefaAtual = itemTrabalho.getElementoFluxoDto().getNome();
                if (itemTrabalho.getItemTrabalhoDto() != null) {
                    if (itemTrabalho.getItemTrabalhoDto().getIdResponsavelAtual() != null) {
                        UsuarioDTO usuarioDto = new UsuarioDTO();
                        usuarioDto.setIdUsuario(itemTrabalho.getItemTrabalhoDto().getIdResponsavelAtual());
                        usuarioDto = (UsuarioDTO) getUsuarioService().restore(usuarioDto);
                        if (usuarioDto != null) {
                            EmpregadoDTO empDto = new EmpregadoDTO();
                            empDto.setIdEmpregado(usuarioDto.getIdEmpregado());
                            empDto = (EmpregadoDTO) getEmpregadoService().restore(empDto);
                            if (empDto != null) {
                                responsavelAtual = empDto.getNome();
                            }
                        }
                    }
                }
            }
        }

        document.executeScript("informaNumeroSolicitacao(\"" + solicitacaoServicoDto.getIdSolicitacaoServico()
                + "\", \"" + UtilStrings.nullToVazio(responsavelAtual) + "\", \""
                + UtilStrings.nullToVazio(tarefaAtual) + "\")");
        document.executeScript("$('#tituloSolicitacao').removeClass('inativo')");

        // Isto permite que nas classes herdadas, seja colocado o Bean no document.
        document.setBean(solicitacaoServicoDto);

        if (solicitacaoServicoDto.getObservacao() != null && !solicitacaoServicoDto.getObservacao().equals("")) {
            document.executeScript("setValorTextArea(\"#observacao\",'"
                    + StringEscapeUtils.escapeJavaScript(solicitacaoServicoDto.getObservacao()) + "', '"
                    + (editar != null ? editar : 'S') + "')");
        }
        if (solicitacaoServicoDto.getDescricao() != null && !solicitacaoServicoDto.getDescricao().equals("")) {
            document.executeScript("setValorTextArea(\"#descricao\",'"
                    + StringEscapeUtils.escapeJavaScript(solicitacaoServicoDto.getDescricao()) + "', '"
                    + (editar != null ? editar : 'S') + "')");
        }
        if (solicitacaoServicoDto.getDetalhamentoCausa() != null
                && !solicitacaoServicoDto.getDetalhamentoCausa().equals("")) {
            document.executeScript("setValorTextArea(\"#detalhamentoCausa\",'"
                    + StringEscapeUtils.escapeJavaScript(solicitacaoServicoDto.getDetalhamentoCausa()) + "', '"
                    + (editar != null ? editar : 'S') + "')");
        }
        if (solicitacaoServicoDto.getResposta() != null && !solicitacaoServicoDto.getResposta().equals("")) {
            document.executeScript("setValorTextArea(\"#resposta\",'"
                    + StringEscapeUtils.escapeJavaScript(solicitacaoServicoDto.getResposta()) + "', '"
                    + (editar != null ? editar : 'S') + "')");
        }
        if (solicitacaoServicoDto.getSolicitante().isEmpty()) {
            document.executeScript("validaCampoExecutanteNullparaVazio()");
        }
        if (editar != null && editar.equals("N")) {
            document.executeScript("document.getElementById('divBotoes').style.display = 'none';");
        }

        if (solicitacaoServicoDto.getEditar() == null) {
            solicitacaoServicoDto.setEditar("S");
        }
        if (solicitacaoServicoDto.getEditar().equalsIgnoreCase("N")) {
            document.getTextBoxById("servicoBusca").setDisabled(true);
        } else {
            if (solicitacaoServicoDto.getReclassificar() == null
                    || solicitacaoServicoDto.getReclassificar().equalsIgnoreCase("N")) {
                document.getTextBoxById("servicoBusca").setDisabled(true);
            }
        }

        if (solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
            renderizaHistoricoSolicitacoesEmAndamentoUsuario(document, request, response);
            carregaBaseConhecimentoAssoc(document, request, response);
        }

        solicitacaoServicoDto = null;
    }

    public void verificaGrupoExecutor(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
            solicitacaoServicoDto.setIdContrato(contratoDtoAux.getIdContrato());
        }
        String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
        if (COLABORADORES_VINC_CONTRATOS == null) {
            COLABORADORES_VINC_CONTRATOS = "N";
        }
        if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
            final HTMLSelect idGrupoAtual = document.getSelectById("idGrupoAtual");
            idGrupoAtual.removeAllOptions();
            idGrupoAtual.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            final Collection colGrupos = getGrupoService().listGruposServiceDeskByIdContrato(
                    solicitacaoServicoDto.getIdContrato());
            if (colGrupos != null) {
                idGrupoAtual.addOptions(colGrupos, "idGrupo", "nome", null);
            }
        }

        verificaGrupoExecutorInterno(document, solicitacaoServicoDto);

        solicitacaoServicoDto = null;
    }

    public void verificaGrupoExecutorInterno(final DocumentHTML document,
            final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
        if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdContrato() == null) {
            return;
        }

        final ServicoContratoDTO servicoContratoDto = getServicoContratoService().findByIdContratoAndIdServico(
                solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
        if (servicoContratoDto != null && servicoContratoDto.getIdGrupoExecutor() != null) {
            document.getElementById("idGrupoAtual").setValue("" + servicoContratoDto.getIdGrupoExecutor());
        } else {
            document.getElementById("idGrupoAtual").setValue("");
        }
    }

    public void carregarModalDuplicarSolicitacao(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws ServiceException, Exception {

        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");

        String situacao;
        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getSituacao() != null) {
            situacao = solicitacaoServicoDto.getSituacao();
        } else {
            situacao = "";
        }

        Integer idGrupoAtual;
        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdGrupoAtual() != null) {
            idGrupoAtual = solicitacaoServicoDto.getIdGrupoAtual();
        } else {
            idGrupoAtual = -1;
        }

        solicitacaoServicoDto = (SolicitacaoServicoDTO) getSolicitacaoServicoService().restore(solicitacaoServicoDto);

        ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();

        servicoContratoDto.setIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());

        servicoContratoDto = (ServicoContratoDTO) getServicoContratoService().restore(servicoContratoDto);

        solicitacaoServicoDto.setIdContrato(servicoContratoDto.getIdContrato());

        document.getElementById("idContrato").setValue(solicitacaoServicoDto.getIdContrato().toString());

        final HTMLForm formSolicitacaoServico = document.getForm("formInformacoesContato");
        formSolicitacaoServico.setValues(solicitacaoServicoDto);

        preencherComboOrigem(document, request, response);

        StringBuilder objeto;
        if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")) {
            objeto = new StringBuilder();
            objeto.append("<label  class='strong  campoObrigatorio'>");
            objeto.append(UtilI18N.internacionaliza(request, "unidade.unidade"));
            objeto.append("</label>");
            objeto.append("<input type='text' name='unidadeDes' id='unidadeDes' style='width: 100%;' onkeypress='onkeypressUnidadeDes();' onfocus='montaParametrosAutocompleteUnidade();'>");
            objeto.append("<input type='hidden' name='idUnidade' id='idUnidade' value='0'/>");
            document.getElementById("divUnidade").setInnerHTML(objeto.toString());
            document.executeScript("geraAutoComplete()");
        } else {
            objeto = new StringBuilder();
            objeto.append("<label  class='strong  campoObrigatorio'>");
            objeto.append(UtilI18N.internacionaliza(request, "unidade.unidade"));
            objeto.append("</label>");
            objeto.append("<select  class='span12' name='idUnidade' id='idUnidade' required='required' onchange='document.formInformacoesContato.fireEvent(\"preencherComboLocalidade\");'></select>");
            document.getElementById("divUnidade").setInnerHTML(objeto.toString());
        }

        carregaUnidade(document, request, response);

        preencherComboLocalidade(document, request, response);
        /*
         * Rodrigo Pecci Acorse - 03/12/2013 14h40 - #126139 Ao setar o DTO da solicitação de serviços, a situação e grupo executor do
         * filtro estava sendo preenchida indevidamente. As linhas abaixo
         * preenchem os selects com os valores corretos.
         */
        document.executeScript("$('#situacao').find('option[value=\"" + situacao + "\"]').attr(\"selected\",true);");
        document.executeScript("$('#idGrupoAtual').find('option[value=\"" + idGrupoAtual
                + "\"]').attr(\"selected\",true);");

        solicitacaoServicoDto = null;
        servicoContratoDto = null;
    }

    public void duplicarSolicitacao(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws ServiceException, Exception {

        SolicitacaoServicoDTO novaSolicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        UsuarioDTO usuarioDto = new UsuarioDTO();

        usuarioDto = WebUtil.getUsuario(request);

        SolicitacaoServicoDTO solicitacaoServicoOrigem = new SolicitacaoServicoDTO();
        ServicoContratoDTO servicoContratoDto = new ServicoContratoDTO();

        solicitacaoServicoOrigem.setIdSolicitacaoServico(novaSolicitacaoServicoDto.getIdSolicitacaoServico());

        solicitacaoServicoOrigem = (SolicitacaoServicoDTO) getSolicitacaoServicoService().restore(
                solicitacaoServicoOrigem);

        servicoContratoDto.setIdServicoContrato(solicitacaoServicoOrigem.getIdServicoContrato());

        servicoContratoDto = (ServicoContratoDTO) getServicoContratoService().restore(servicoContratoDto);

        novaSolicitacaoServicoDto.setIdSolicitacaoServico(null);
        novaSolicitacaoServicoDto.setIdSolicitacaoPai(solicitacaoServicoOrigem.getIdSolicitacaoServico());
        novaSolicitacaoServicoDto.setIdContatoSolicitacaoServico(null);

        novaSolicitacaoServicoDto.setIdServico(servicoContratoDto.getIdServico());

        novaSolicitacaoServicoDto.setUsuarioDto(usuarioDto);
        novaSolicitacaoServicoDto.setDescricao(solicitacaoServicoOrigem.getDescricao());
        novaSolicitacaoServicoDto.setSituacao(solicitacaoServicoOrigem.getSituacao());
        novaSolicitacaoServicoDto.setRegistroexecucao("");
        novaSolicitacaoServicoDto.setEnviaEmailCriacao("S");

        novaSolicitacaoServicoDto = (SolicitacaoServicoDTO) getSolicitacaoServicoService().create(
                novaSolicitacaoServicoDto);

        document.alert(UtilI18N.internacionaliza(request, "gerenciaservico.duplicadacomsucesso"));

        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
        document.executeScript("parent.refreshTelaGerenciamento()");

        novaSolicitacaoServicoDto = null;
        solicitacaoServicoOrigem = null;
        servicoContratoDto = null;

    }

    public void restauraSolicitante(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");

        final SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator
                .getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));

        EmpregadoDTO empregadoDto = new EmpregadoDTO();
        empregadoDto.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
        empregadoDto = (EmpregadoDTO) getEmpregadoService().restore(empregadoDto);

        if (empregadoDto != null) {
            solicitacaoServicoDto.setSolicitante(empregadoDto.getNome());
            solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
            solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());
            solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail());
            solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());

            if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")
                    && solicitacaoServicoDto.getIdUnidade() != null) {
                solicitacaoServicoDto.setUnidadeDes(getUnidadeService().retornaNomeUnidadeByID(
                        solicitacaoServicoDto.getIdUnidade()));
            }

            preencherComboLocalidade(document, request, response);
        }

        UsuarioDTO usuarioDto = new UsuarioDTO();
        if (empregadoDto != null) {
            usuarioDto = getUsuarioService().restoreByIdEmpregado(empregadoDto.getIdEmpregado());
        }

        if (usuarioDto != null) {
            final String login = usuarioDto.getLogin();

            final SolicitacaoServicoDTO solicitacaoServicoComItemConfiguracaoDoSolicitante = solicitacaoServicoService
                    .retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(login);

            if (solicitacaoServicoComItemConfiguracaoDoSolicitante != null) {
                solicitacaoServicoDto.setIdItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante
                        .getIdItemConfiguracao());
                solicitacaoServicoDto.setItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante
                        .getItemConfiguracao());
                document.executeScript("exibeCampos()");
            }
        }

        final HTMLForm formSolicitacaoServico = document.getForm("formInformacoesContato");
        formSolicitacaoServico.setValues(solicitacaoServicoDto);
        document.executeScript("fecharPopup(\"#POPUP_SOLICITANTE\")");

        solicitacaoServicoDto = null;

    }

    public void restoreSolicitante(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");

        final SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator
                .getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));

        EmpregadoDTO empregadoDto = new EmpregadoDTO();
        empregadoDto.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
        empregadoDto = (EmpregadoDTO) getEmpregadoService().restore(empregadoDto);

        if (empregadoDto != null) {
            solicitacaoServicoDto.setNomeSolicitante(empregadoDto.getNome());
            solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
            solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());
            solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail());
            solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());

            if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")
                    && solicitacaoServicoDto.getIdUnidade() != null) {
                solicitacaoServicoDto.setUnidadeDes(getUnidadeService().retornaNomeUnidadeByID(
                        solicitacaoServicoDto.getIdUnidade()));
            }
        }

        UsuarioDTO usuarioDto = new UsuarioDTO();
        if (empregadoDto != null) {
            usuarioDto = getUsuarioService().restoreByIdEmpregado(empregadoDto.getIdEmpregado());
        }

        if (usuarioDto != null) {
            final String login = usuarioDto.getLogin();

            final SolicitacaoServicoDTO solicitacaoServicoComItemConfiguracaoDoSolicitante = solicitacaoServicoService
                    .retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(login);

            if (solicitacaoServicoComItemConfiguracaoDoSolicitante != null) {
                solicitacaoServicoDto.setIdItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante
                        .getIdItemConfiguracao());
                solicitacaoServicoDto.setItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante
                        .getItemConfiguracao());
            }
        }

        final HTMLForm formSolicitacaoServico = document.getForm("formInformacoesContato");
        formSolicitacaoServico.setValues(solicitacaoServicoDto);
        document.executeScript("fecharPopup(\"#POPUP_SOLICITANTE\")");

        document.executeScript("setDataEditor()");

        // verifica se tem historico para mostrar botao de historico
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance()
                .getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
        List<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(
                solicitacaoServicoDto.getIdSolicitante(), null);
        if (resumo == null || resumo.size() <= 0) {
            document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'none')");
        } else {
            document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'inline')");
        }

        resumo = solicitacaoService.findSolicitacoesServicosUsuario(solicitacaoServicoDto.getIdSolicitante(), null,
                null);
        if (resumo == null || resumo.size() <= 0) {
            document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'none')");
        } else {
            document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'inline')");
        }

        solicitacaoServicoDto = null;
    }

    private void carregaComboOrigem(final DocumentHTML document, final HttpServletRequest request)
            throws ServiceException, Exception, LogicException {

        final HTMLSelect origem = document.getSelectById("idOrigem");

        origem.removeAllOptions();
        inicializarCombo(origem, request);

        final Collection listOrigem = getOrigemAtendimentoService().list();

        if (listOrigem != null) {

            origem.addOptions(listOrigem, "idOrigem", "descricao", null);
        }
    }

    public void preenchePorEmail(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws ServiceException, Exception {
        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");

        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        EmpregadoDTO empregadoDTO = new EmpregadoDTO();
        empregadoDTO = getEmpregadoService().listEmpregadoContrato(solicitacaoServicoDto.getIdContrato(),
                solicitacaoServicoDto.getEmailcontato());
        if (empregadoDTO != null) {
            document.getElementById("idSolicitante").setValue(empregadoDTO.getIdEmpregado().toString());
            document.getElementById("nomecontato").setValue(empregadoDTO.getNome());
            document.getElementById("telefonecontato").setValue(empregadoDTO.getTelefone());
            document.getElementById("idUnidade").setValue(
                    Util.tratarAspasSimples(empregadoDTO.getIdUnidade().toString()));

            if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")
                    && empregadoDTO.getIdUnidade() != null) {
                document.getElementById("unidadeDes").setValue(
                        Util.tratarAspasSimples(unidadeService.retornaNomeUnidadeByID(empregadoDTO.getIdUnidade())));
            }

            document.getElementById("solicitante").setValue(empregadoDTO.getNome());
            document.getElementById("idOrigem").setValue("3");
        } else {
            document.getElementById("emailcontato").setValue("");
        }

        document.executeScript("$('#modal_leituraEmails').modal('hide');");

        solicitacaoServicoDto = null;
        empregadoDTO = null;
    }

    public void calculaSLA(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        String sla = "";
        try {
            final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
            if (solicitacaoServicoDto.getIdContrato() == null || solicitacaoServicoDto.getIdContrato().intValue() == 0) {
                throw new LogicException("Contrato não encontrado");
            }
            if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdServico().intValue() == 0) {
                throw new LogicException("Serviço não encontrado");
            }

            sla = getSolicitacaoServicoService().calculaSLA(solicitacaoServicoDto, request);

            if (sla.equals("")) {
                sla = "N/A";
            }

            document.executeScript("stopLoading();");
            document.executeScript("$('#tdResultadoSLAPrevisto').text('" + sla + "');");
            document.executeScript("$('#tdResultadoSLAPrevisto').css('display','block)'");

        } catch (final Exception e) {
            e.printStackTrace();
            document.executeScript("stopLoading()");
            if (sla.equals("")) {
                sla = "N/A";
            }
            document.executeScript("document.getElementById('tdResultadoSLAPrevisto').innerHTML = '" + sla + "';");
        }
    }

    public void atualizaGridProblema(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();
        ProblemaDTO problemaDTO = new ProblemaDTO();

        problemaDTO.setIdProblema(bean.getIdProblema());

        problemaDTO = (ProblemaDTO) getProblemaService().restore(problemaDTO);
        if (problemaDTO == null) {
            return;
        }
        final HTMLTable tblProblema = document.getTableById("tblProblema");

        if (problemaDTO.getSequenciaProblema() == null) {
            tblProblema.addRow(problemaDTO, new String[] {"numberAndTitulo", "status", ""},
                    new String[] {"idProblema"}, "Problema já cadastrado!!", new String[] {"exibeIconesProblema"},
                    null, null);
        } else {
            tblProblema.updateRow(problemaDTO, new String[] {"numberAndTitulo", "status", ""},
                    new String[] {"idProblema"}, "Problema já cadastrado!!", new String[] {"exibeIconesProblema"},
                    "buscaProblema", null, problemaDTO.getSequenciaProblema());
        }
        document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");
        document.executeScript("fecharModalProblema();");

        bean = null;
    }

    public void atualizaGridMudanca(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();

        RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaDTO();

        requisicaoMudancaDTO.setIdRequisicaoMudanca(bean.getIdRequisicaoMudanca());

        requisicaoMudancaDTO = (RequisicaoMudancaDTO) getRequisicaoMudancaService().restore(requisicaoMudancaDTO);

        final HTMLTable tblMudanca = document.getTableById("tblMudanca");

        if (requisicaoMudancaDTO.getSequenciaMudanca() == null) {
            tblMudanca.addRow(requisicaoMudancaDTO, new String[] {"numberAndTitulo", "status", ""},
                    new String[] {"idRequisicaoMudanca"}, "Mudança já cadastrado!!",
                    new String[] {"exibeIconesMudanca"}, null, null);
        } else {
            tblMudanca.updateRow(requisicaoMudancaDTO, new String[] {"numberAndTitulo", "status", ""},
                    new String[] {"idRequisicaoMudanca"}, "Mudança já cadastrado!!",
                    new String[] {"exibeIconesMudanca"}, "buscaMudanca", null,
                    requisicaoMudancaDTO.getSequenciaMudanca());
        }
        document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblMudanca', 'tblMudanca');");
        document.executeScript("fecharModalMudanca();");

        bean = null;
    }

    public void atualizaGridBaseConhecimento(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();

        BaseConhecimentoDTO baseConhecimentoDTO = new BaseConhecimentoDTO();

        if (solicitacaoServicoDTO.getIdItemBaseConhecimento() != null) {
            baseConhecimentoDTO.setIdBaseConhecimento(solicitacaoServicoDTO.getIdItemBaseConhecimento());
            baseConhecimentoDTO = (BaseConhecimentoDTO) getBaseConhecimentoService().restore(baseConhecimentoDTO);

            final HTMLTable tblBaseConhecimento = document.getTableById("tblBaseConhecimento");

            if (baseConhecimentoDTO.getSequenciaBaseConhecimento() == null) {
                tblBaseConhecimento.addRow(baseConhecimentoDTO, new String[] {"idBaseConhecimento", "titulo", ""},
                        new String[] {"idBaseConhecimento"},
                        UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"),
                        new String[] {"exibeIconesBaseConhecimento"}, null, null);
            } else {
                tblBaseConhecimento.updateRow(baseConhecimentoDTO, new String[] {"idBaseConhecimento", "titulo", ""},
                        new String[] {"idBaseConhecimento"},
                        UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"),
                        new String[] {"exibeIconesBaseConhecimento"}, null, null,
                        baseConhecimentoDTO.getSequenciaBaseConhecimento());
            }
            document.executeScript("setQuantitativoBaseConhecimento()");
            document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblBaseConhecimento', 'tblBaseConhecimento');");
            document.executeScript("fecharBaseConhecimento();");
        }
    }

    public void atualizaGridItemConfiguracao(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();

        ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();

        if (solicitacaoServicoDTO.getIdItemBaseConhecimento() != null) {
            itemConfiguracaoDTO.setIdItemConfiguracao(solicitacaoServicoDTO.getIdItemBaseConhecimento());
            itemConfiguracaoDTO = (ItemConfiguracaoDTO) getItemConfiguracaoService().restore(itemConfiguracaoDTO);

            final HTMLTable tblBaseConhecimento = document.getTableById("tblIC");

            if (itemConfiguracaoDTO.getSequenciaIC() == null) {
                tblBaseConhecimento.addRow(itemConfiguracaoDTO,
                        new String[] {"idItemConfiguracao", "descricao", "", ""}, new String[] {"idItemConfiguracao"},
                        "Item Configuração já cadastrado!!", new String[] {"exibeIconesMudanca"},
                        "abreItemConfiguracao", null);

            } else {
                tblBaseConhecimento.updateRow(itemConfiguracaoDTO, new String[] {"idItemConfiguracao", "descricao", "",
                ""}, new String[] {"idBaseConhecimento"},
                UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"),
                new String[] {"exibeIconesBaseConhecimento"}, null, null, itemConfiguracaoDTO.getSequenciaIC());
            }
            document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblIC', 'tblIC');");
            document.executeScript("fecharModalItemConfiguracao();");
        }
    }

    public void carregaInformacoesComplementares(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        carregaInformacoesComplementares(document, request, solicitacaoServicoDto);

        solicitacaoServicoDto = null;
    }

    private boolean validaPermissoesDoTemplate(final DocumentHTML document, final HttpServletRequest request,
            final SolicitacaoServicoDTO solicitacaoServicoDto, final String urlTemplate) throws ServiceException {
        boolean validacao = true;
        if (urlTemplate.contains("/requisicaoPessoal.load")) {
            try {
                if (!getSolicitacaoServicoService().verificaPermGestorSolicitanteRH(
                        solicitacaoServicoDto.getIdSolicitante())) {
                    validacao = false;
                    document.alert(UtilI18N.internacionaliza(request,
                            "requisicaoPessoal.somenteGestoresSupDirPodemAbrirReqPessoal"));
                    document.executeScript("limparServico();");
                }
            } catch (final PersistenceException e) {
                validacao = false;
                e.printStackTrace();
            }
        }
        return validacao;
    }

    private void carregaInformacoesComplementares(final DocumentHTML document, final HttpServletRequest request,
            final SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
        document.executeScript("document.getElementById('flagGrupo').value = 1;");
        document.executeScript("document.getElementById('divGrupoAtual').style.display = 'block';");
        document.executeScript("document.getElementById('divSituacao').style.display = 'block';");
        document.executeScript("document.getElementById('solucao').style.display = 'block';");
        document.executeScript("document.getElementById('divUrgencia').style.display = 'block';");
        document.executeScript("document.getElementById('divImpacto').style.display = 'block';");
        document.executeScript("document.getElementById('divNotificacaoEmail').style.display = 'block';");
        document.executeScript("document.getElementById('divProblema').style.display = 'block';");
        document.executeScript("document.getElementById('divMudanca').style.display = 'block';");
        document.executeScript("document.getElementById('divItemConfiguracao').style.display = 'block';");
        document.executeScript("document.getElementById('col4').style.display = 'block';");

        final TemplateSolicitacaoServicoService templateService = (TemplateSolicitacaoServicoService) ServiceLocator
                .getInstance().getService(TemplateSolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
        ServicoContratoDTO servicoContrato = null;

        // Valida permissoes do template
        if (validaPermissoesDoTemplate(document, request, solicitacaoServicoDto, getSolicitacaoServicoService()
                .getUrlInformacoesComplementares(solicitacaoServicoDto))) {
            request.getSession().setAttribute("idSolicitante", solicitacaoServicoDto.getIdSolicitante());
            document.executeScript("exibirInformacoesComplementares(\""
                    + solicitacaoServicoService.getUrlInformacoesComplementares(solicitacaoServicoDto) + "\");");
            // Incluído, quando questionário for recarregado.
            document.executeScript("incluiInfoComplSeQuestionario(\""
                    + solicitacaoServicoService.getUrlInformacoesComplementares(solicitacaoServicoDto) + "\");");
            final TemplateSolicitacaoServicoDTO templateDto = templateService
                    .recuperaTemplateServico(solicitacaoServicoDto);

            if (templateDto != null) {
                carregaInformacoesComplementaresTemplate(document, request, templateDto, solicitacaoServicoDto);
            }

            final String exandiTelaPadrao = (String) request.getSession().getAttribute("expandiuTela");
            /**
             * @author david.silva - data 25/06/2014 ajustarTelaPadraoTemplate() - responsavel por expandir a tela de Solicitação de
             *         Serviço.
             *
             *         Alteração mario.haysaki inserção de flag para tela de expandir tela.
             */
            if (solicitacaoServicoDto.getIdContrato() != null && solicitacaoServicoDto.getIdServico() != null) {
                servicoContrato = getServicoContratoService().findByIdContratoAndIdServico(
                        solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
                if (servicoContrato != null && servicoContrato.getExpandir() != null
                        && servicoContrato.getExpandir().equalsIgnoreCase("S")) {
                    document.executeScript("ajustarTelaPadraoTemplate();");
                    request.getSession().setAttribute("expandiuTela", "S");
                } else if (exandiTelaPadrao != null && exandiTelaPadrao.equals("S")) {
                    document.executeScript("ajustarTelaPadraoCitsmart()");
                    request.getSession().setAttribute("expandiuTela", null);
                }
            }
        }
    }

    /**
     * @author david.silva
     *
     *         Separação do codigo que cuida da geração do template, para um metodo expecifico.
     */
    private void carregaInformacoesComplementaresTemplate(final DocumentHTML document,
            final HttpServletRequest request, final TemplateSolicitacaoServicoDTO templateDto,
            final SolicitacaoServicoDTO solicitacaoServicoDto) {

        ServicoContratoDTO servicoContrato = null;
        try {
            servicoContrato = getServicoContratoService().findByIdContratoAndIdServico(
                    solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (templateDto.getScriptAposRecuperacao() != null
                && !StringUtils.isBlank(templateDto.getScriptAposRecuperacao())) {
            document.executeScript(templateDto.getScriptAposRecuperacao());
        }
        if (!templateDto.getHabilitaDirecionamento().equalsIgnoreCase("S")) {
            document.executeScript("document.getElementById('flagGrupo').value = 0;");
            document.executeScript("document.getElementById('divGrupoAtual').style.display = 'none';");
        }
        if (!templateDto.getHabilitaSituacao().equalsIgnoreCase("S")) {
            document.executeScript("document.getElementById('divSituacao').style.display = 'none';");
        }
        if (!templateDto.getHabilitaSolucao().equalsIgnoreCase("S")) {
            document.executeScript("document.getElementById('solucao').style.display = 'none';");
        }
        if (!templateDto.getHabilitaUrgenciaImpacto().equalsIgnoreCase("S")) {
            document.executeScript("document.getElementById('divUrgencia').style.display = 'none';");
            document.executeScript("document.getElementById('divImpacto').style.display = 'none';");
        }
        if (!templateDto.getHabilitaNotificacaoEmail().equalsIgnoreCase("S")) {
            document.executeScript("document.getElementById('divNotificacaoEmail').style.display = 'none';");
        }
        if (!templateDto.getHabilitaProblema().equalsIgnoreCase("S")) {
            document.executeScript("document.getElementById('divProblema').style.display = 'none';");
        }
        if (!templateDto.getHabilitaMudanca().equalsIgnoreCase("S")) {
            document.executeScript("document.getElementById('divMudanca').style.display = 'none';");
        }
        if (!templateDto.getHabilitaItemConfiguracao().equalsIgnoreCase("S")) {
            document.executeScript("document.getElementById('divItemConfiguracao').style.display = 'none';");
        }
        if (!templateDto.getHabilitaSolicitacaoRelacionada().equalsIgnoreCase("S")) {
            if (request.getAttribute("tarefaAssociada") != null
                    && !((String) request.getAttribute("tarefaAssociada")).equalsIgnoreCase("N")) {
                document.executeScript("document.getElementById('liIncidentesRelacionados').style.display = 'none';");
            }
        }
        if (!templateDto.getHabilitaGravarEContinuar().equalsIgnoreCase("S")
                && solicitacaoServicoDto.getIdTarefa() != null) {
            document.executeScript("document.getElementById('btnGravarEContinuar').style.display = 'none';");
        }

        /*
         * Desenvolvedor: Riubbe Oliveira - Data: 08/11/2013 - Horário: 17:39 - ID Citsmart: 123538 Motivo/Comentário: Esta parte do codigo
         * estava sendo comentada, isso nao deve ser feito. É nesse
         * momento que é carregado a informação da altura do template, se estiver tendo problema com a altura da sua template entre no
         * sistema e altera o tamanho por lá.
         */
        if (templateDto.getAlturaDiv() != null) {
            document.executeScript("document.getElementById('divInformacoesComplementares').style.height = '"
                    + templateDto.getAlturaDiv().intValue() + "px';");
        }
        if (!templateDto.getHabilitaSituacao().equalsIgnoreCase("S")
                && !templateDto.getHabilitaSolucao().equalsIgnoreCase("S")) {
            document.executeScript("document.getElementById('col4').style.display = 'none';");
        }

    }

    public void trataTemplatesViagens(final TemplateSolicitacaoServicoDTO templateDto,
            final SolicitacaoServicoDTO solicitacaoServicoDto, final HttpServletRequest request,
            final DocumentHTML document) {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (templateDto != null
                && templateDto.getIdentificacao() != null
                && (templateDto.getIdentificacao().equalsIgnoreCase("ControleFinanceiroViagem")
                        || templateDto.getIdentificacao().equalsIgnoreCase("AdiantamentoViagem")
                        || templateDto.getIdentificacao().equalsIgnoreCase("AlteracaoRequisicaoViagem")
                        || templateDto.getIdentificacao().equalsIgnoreCase("ExecComprasViagem") || templateDto
                        .getIdentificacao().equalsIgnoreCase("AutorizacaoViagem"))) {
            // regra: permite que o usuário solicitante cancele a solicitação durante essas fases do fluxo
            // mas o usuário não pode marcar a solicitação como resolvida
            document.executeScript("document.getElementById('col4').style.display = 'block';");
            document.executeScript("document.getElementById('divSituacao').style.display = 'block';");
            document.executeScript("document.getElementById('radioResolvida').disabled=true;");
        }
    }

    public void pesquisaBaseConhecimento(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        final BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();
        baseConhecimentoDto.setTermoPesquisa(solicitacaoServicoDto.getDescricaoSemFormatacao());

        final UsuarioDTO usuario = WebUtil.getUsuario(request);

        List<BaseConhecimentoDTO> listaPesquisaBaseConhecimento = null;

        if ((baseConhecimentoDto.getTermoPesquisa() == null || baseConhecimentoDto.getTermoPesquisa().trim()
                .equalsIgnoreCase(""))
                && baseConhecimentoDto.getIdUsuarioAutorPesquisa() == null
                && baseConhecimentoDto.getIdUsuarioAprovadorPesquisa() == null
                && baseConhecimentoDto.getDataInicioPesquisa() == null
                && baseConhecimentoDto.getDataPublicacaoPesquisa() == null
                && (baseConhecimentoDto.getTermoPesquisaNota() == null || baseConhecimentoDto.getTermoPesquisaNota()
                .equalsIgnoreCase(""))) {

            document.alert("Informe um termo para pesquisa");

            return;

        } else {
            document.executeScript("$('#modal_pesquisaSolucaoBaseConhecimento').modal('show')");
            final Lucene lucene = new Lucene();
            listaPesquisaBaseConhecimento = lucene.pesquisaBaseConhecimento(baseConhecimentoDto);
        }

        final StringBuilder TabelaDados = new StringBuilder("<table>");

        final CompararBaseConhecimentoGrauImportancia comparaGrauDeImportancia = new CompararBaseConhecimentoGrauImportancia();

        if (listaPesquisaBaseConhecimento != null && !listaPesquisaBaseConhecimento.isEmpty()) {

            for (final BaseConhecimentoDTO baseConhecimentoDTO : listaPesquisaBaseConhecimento) {
                if (baseConhecimentoDTO != null && baseConhecimentoDTO.getIdPasta() != null) {
                    if (baseConhecimentoDTO != null) {
                        if (getBaseConhecimentoService()
                                .obterGrauDeImportanciaParaUsuario(baseConhecimentoDTO, usuario) != null) {
                            final Integer grauImportancia = getBaseConhecimentoService()
                                    .obterGrauDeImportanciaParaUsuario(baseConhecimentoDTO, usuario);
                            baseConhecimentoDTO.setGrauImportancia(grauImportancia);
                        }
                    }
                }
            }

            Collections.sort(listaPesquisaBaseConhecimento, comparaGrauDeImportancia);

            for (final BaseConhecimentoDTO dto : listaPesquisaBaseConhecimento) {

                final Integer importancia = dto.getGrauImportancia();

                String titulo = UtilStrings.nullToVazio(dto.getTitulo());

                titulo = titulo.replaceAll("\"", "");
                titulo = titulo.replaceAll("/", "");

                TabelaDados.append("<ul>");
                TabelaDados.append("<tr style='height: 25px !important;'>");
                TabelaDados
                .append("<td style='FONT-WEIGHT: bold; FONT-SIZE: small; FONT-FAMILY: Arial; width: 422px;'>");
                TabelaDados.append("<li>");

                TabelaDados.append("<a href='#' onclick='contadorClicks(" + dto.getIdBaseConhecimento()
                        + ");abreVISBASECONHECIMENTO(" + dto.getIdBaseConhecimento() + ");'>");

                TabelaDados.append(titulo + getGrauImportancia(request, importancia) + "</a>");
                TabelaDados.append("</li>");
                TabelaDados.append("</td>");
                TabelaDados.append("</tr>");
                TabelaDados.append("</ul>");
            }

        } else {
            TabelaDados.append("<tr style='height: 25px !important;'>");
            TabelaDados
            .append("<td style='FONT-WEIGHT: bold; FONT-SIZE: small; FONT-FAMILY: 'Arial'; width : 422px;'>");
            TabelaDados.append("<label> " + UtilI18N.internacionaliza(request, "citcorpore.comum.resultado")
                    + "</label>");
            TabelaDados.append("</td>");
            TabelaDados.append("</tr>");
        }

        TabelaDados.append("</table>");

        document.getElementById("resultPesquisa").setInnerHTML(TabelaDados.toString());

        document.getElementById("modal_pesquisaSolucaoBaseConhecimento").setVisible(true);
    }

    public void contadorDeClicks(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        final UsuarioDTO usuario = WebUtil.getUsuario(request);

        BaseConhecimentoDTO baseConhecimentoDto = new BaseConhecimentoDTO();
        baseConhecimentoDto.setIdBaseConhecimento(solicitacaoServicoDto.getIdBaseConhecimento());
        baseConhecimentoDto = (BaseConhecimentoDTO) getBaseConhecimentoService().restore(baseConhecimentoDto);

        final ContadorAcessoDTO contadorAcessoDto = new ContadorAcessoDTO();
        if (contadorAcessoDto.getIdContadorAcesso() == null) {
            contadorAcessoDto.setIdUsuario(usuario.getIdUsuario());
            contadorAcessoDto.setIdBaseConhecimento(baseConhecimentoDto.getIdBaseConhecimento());
            contadorAcessoDto.setDataHoraAcesso(UtilDatas.getDataHoraAtual());
            contadorAcessoDto.setContadorAcesso(1);
            if (getContadorAcessoService().verificarDataHoraDoContadorDeAcesso(contadorAcessoDto)) {
                getContadorAcessoService().create(contadorAcessoDto);

                // Avaliação - Média da nota dada pelos usuários
                final Double media = getBaseConhecimentoService().calcularNota(
                        baseConhecimentoDto.getIdBaseConhecimento());
                if (media != null) {
                    baseConhecimentoDto.setMedia(media.toString());
                } else {
                    baseConhecimentoDto.setMedia(null);
                }

                // Qtde de cliques
                final Integer quantidadeDeCliques = getContadorAcessoService().quantidadesDeAcessoPorBaseConhecimnto(
                        baseConhecimentoDto);
                if (quantidadeDeCliques != null) {
                    baseConhecimentoDto.setContadorCliques(quantidadeDeCliques);
                } else {
                    baseConhecimentoDto.setContadorCliques(0);
                }

                final Lucene lucene = new Lucene();
                lucene.indexarBaseConhecimento(baseConhecimentoDto);
            }
        }
    }

    /**
     * Alterado por desenvolvedor: rcs (Rafael César Soyer) data: 09/01/2015
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void listarServicosPorContratoDemandaCategoria(final DocumentHTML document,
            final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        final HTMLTable tblListaServicos = document.getTableById("tblListaServicos");
        tblListaServicos.deleteAllRows();

        final Collection<ServicoDTO> colServicos = getServicoService().findByIdTipoDemandaAndIdContrato(
                solicitacaoServicoDto.getIdTipoDemandaServico(), solicitacaoServicoDto.getIdContrato(),
                solicitacaoServicoDto.getIdCategoriaServico());
        if (colServicos != null && !colServicos.isEmpty()) {
            tblListaServicos.addRowsByCollection(colServicos, new String[] {"idServico", "nomeServico"}, null, null,
                    null, "selecionarServico", null);
            document.executeScript("$('#modal_infoServicos').modal('show')");
        } else {
            if (solicitacaoServicoDto.getIdCategoriaServico() == null) {
                document.alert(UtilI18N.internacionaliza(request,
                        "solicitacaoServico.semServicoParaTipoSolicitacaoContrato"));
            } else {
                document.alert(UtilI18N.internacionaliza(request,
                        "solicitacaoServico.semServicoParaTipoSolicitacaoContratoCategoriaServico"));
            }
        }

        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    public void preencherComboOrigem(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final HTMLSelect selectOrigem = document.getSelectById("idOrigem");
        selectOrigem.removeAllOptions();
        final ArrayList<OrigemAtendimentoDTO> todasOrigens = (ArrayList) getOrigemAtendimentoService().list();
        final ArrayList<OrigemAtendimentoDTO> origensNaoExcluidas = new ArrayList<OrigemAtendimentoDTO>();
        final String origemPadrao = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.ORIGEM_PADRAO, "");

        selectOrigem.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        if (todasOrigens != null) {
            for (final OrigemAtendimentoDTO origemAtendimento : todasOrigens) {
                if (origemAtendimento.getDataFim() == null) {
                    origensNaoExcluidas.add(origemAtendimento);
                }
            }
            selectOrigem.addOptions(origensNaoExcluidas, "idOrigem", "descricao", origemPadrao);
        }
    }

    /**
     * Preenche a combo Localidade.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    public void preencherComboLocalidade(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        LocalidadeDTO localidadeDto = new LocalidadeDTO();

        Collection<LocalidadeUnidadeDTO> listaIdlocalidadePorUnidade = null;

        Collection<LocalidadeDTO> listaIdlocalidade = null;

        final String TIRAR_VINCULO_LOCALIDADE_UNIDADE = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.TIRAR_VINCULO_LOCALIDADE_UNIDADE, "N");

        final HTMLSelect comboLocalidade = document.getSelectById("idLocalidade");
        comboLocalidade.removeAllOptions();
        if (TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("N")
                || TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("")) {
            if (solicitacaoServicoDto.getIdUnidade() != null) {
                listaIdlocalidadePorUnidade = getLocalidadeUnidadeService().listaIdLocalidades(
                        solicitacaoServicoDto.getIdUnidade());
            }
            if (listaIdlocalidadePorUnidade != null) {
                inicializarComboLocalidade(comboLocalidade, request);
                for (final LocalidadeUnidadeDTO localidadeUnidadeDto : listaIdlocalidadePorUnidade) {
                    localidadeDto.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
                    localidadeDto = (LocalidadeDTO) getLocalidadeService().restore(localidadeDto);
                    comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(),
                            localidadeDto.getNomeLocalidade());
                }

            }
        } else {
            listaIdlocalidade = getLocalidadeService().listLocalidade();
            if (listaIdlocalidade != null) {
                inicializarComboLocalidade(comboLocalidade, request);
                for (final LocalidadeDTO localidadeDTO : listaIdlocalidade) {
                    localidadeDto.setIdLocalidade(localidadeDTO.getIdLocalidade());
                    localidadeDto = (LocalidadeDTO) getLocalidadeService().restore(localidadeDto);
                    comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(),
                            localidadeDto.getNomeLocalidade());
                }
            }

        }
        solicitacaoServicoDto = null;
    }

    /**
     * @return the calcularDinamicamente
     * @throws Exception
     */
    public String getCalcularDinamicamente() throws Exception {
        calcularDinamicamente = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.CALCULAR_PRIORIDADE_SOLICITACAO_DINAMICAMENTE, "N");
        return calcularDinamicamente.trim();
    }

    private void inicializarComboLocalidade(final HTMLSelect componenteCombo, final HttpServletRequest request) {
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
    }

    private void preparaTelaInclusao(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        // document.getElementById("btOcorrencias").setVisible(false);
        // document.getElementById("btIncidentesRelacionados").setVisible(false);
    }

    public void carregaBaseConhecimentoAssoc(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        document.getElementById("divScript").setInnerHTML(
                UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.scriptservico"));
        if (solicitacaoServicoDto.getIdServico() == null) {
            return;
        }
        ServicoDTO servicoDto = new ServicoDTO();
        servicoDto.setIdServico(solicitacaoServicoDto.getIdServico());
        servicoDto = (ServicoDTO) getServicoService().restore(servicoDto);
        if (servicoDto != null) {
            if (servicoDto.getIdBaseconhecimento() != null) {
                BaseConhecimentoDTO baseConhecimentoDTO = new BaseConhecimentoDTO();
                baseConhecimentoDTO.setIdBaseConhecimento(servicoDto.getIdBaseconhecimento());
                baseConhecimentoDTO = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDTO);
                if (baseConhecimentoDTO != null) {
                    document.getElementById("divScript").setInnerHTML(baseConhecimentoDTO.getConteudo());
                    document.getElementById("countScript").setInnerHTML("1");
                    document.executeScript("destaqueScript()");
                } else {
                    document.getElementById("countScript").setInnerHTML("0");
                    document.executeScript("$('#divMenuScript').removeClass('ui-state-highlight')");
                }
            } else {
                document.getElementById("countScript").setInnerHTML("0");
            }
        }

        solicitacaoServicoDto = null;
    }

    public void carregaContratos(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        final HTMLSelect idContrato = document.getSelectById("idContrato");
        idContrato.removeAllOptions();

        if (solicitacaoServicoDto.getIdServico() == null) {
            return;
        }

        final Collection<ServicoContratoDTO> colContratos = getServicoContratoService().findByIdServico(
                solicitacaoServicoDto.getIdServico());
        if (colContratos != null) {
            if (colContratos.size() > 1) {
                idContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            } else {
                final ServicoContratoDTO servicoContratoDto = (ServicoContratoDTO) ((List) colContratos).get(0);
                solicitacaoServicoDto.setIdContrato(servicoContratoDto.getIdContrato());
                verificaGrupoExecutorInterno(document, solicitacaoServicoDto);
                verificaImpactoUrgencia(document, request, response);
            }
            for (final ServicoContratoDTO servicoContratoDto : colContratos) {
                if (servicoContratoDto.getDeleted() == null || servicoContratoDto.getDeleted().equalsIgnoreCase("N")) {
                    ContratoDTO contratoDto = new ContratoDTO();
                    contratoDto.setIdContrato(servicoContratoDto.getIdContrato());
                    contratoDto = (ContratoDTO) getContratoService().restore(contratoDto);
                    if (contratoDto != null) {
                        if (contratoDto.getDeleted() == null || contratoDto.getDeleted().equalsIgnoreCase("N")) {
                            String id = contratoDto.getNumero();
                            FornecedorDTO fornecedorDto = new FornecedorDTO();
                            fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
                            fornecedorDto = (FornecedorDTO) getFornecedorService().restore(fornecedorDto);
                            if (fornecedorDto != null) {
                                id += " - " + fornecedorDto.getRazaoSocial();
                            }
                            idContrato.addOptionIfNotExists("" + contratoDto.getIdContrato(), id);
                        }
                    }
                }
            }
        }

        solicitacaoServicoDto = null;
    }

    /**
     * Preenche a combo Software.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    public void preecherComboSoftware(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        final ItemConfiguracaoDTO valor = new ItemConfiguracaoDTO();
        final HTMLSelect comboSoftware = document.getSelectById("idItemConfiguracaoPai");
        inicializarCombo(comboSoftware, request);
        valor.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracao());
        for (final ValorDTO valores : getListaCaracteristicaSoftware(valor, "SOFTWARES")) {
            comboSoftware.addOption(valores.getIdItemConfiguracao().toString(), valores.getValorStr());
        }

        solicitacaoServicoDto = null;
    }

    public Collection<ValorDTO> getListaCaracteristicaSoftware(final ItemConfiguracaoDTO itemConfiguracao,
            final String tagTipoItemConfiguracao) throws ServiceException, Exception {
        final TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();
        tipoItemConfiguracao.setTag(tagTipoItemConfiguracao);
        return getValorService().findByItemAndTipoItemConfiguracaoSofware(itemConfiguracao, tipoItemConfiguracao);

    }

    /**
     * Preenche a combo Hardware.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    public void preecherComboHardware(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        final ItemConfiguracaoDTO valor = new ItemConfiguracaoDTO();
        final HTMLSelect comboHardware = document.getSelectById("idItemConfiguracaoFilho");
        Integer idItemAnterior = -9999;
        valor.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracao());
        inicializarCombo(comboHardware, request);
        for (final ValorDTO valores : getListaCaracteristica(valor, "HARDWARE")) {
            if (idItemAnterior.intValue() != valores.getIdItemConfiguracao().intValue()) {
                comboHardware.addOption(valores.getIdItemConfiguracao().toString(),
                        valores.getTagtipoitemconfiguracao() + " - Id: " + valores.getIdItemConfiguracao());
            }
            idItemAnterior = valores.getIdItemConfiguracao();
            comboHardware.addOption(valores.getIdItemConfiguracao().toString(), valores.getNomeCaracteristica() + " - "
                    + valores.getValorStr());
        }

        solicitacaoServicoDto = null;

    }

    public String listInfoRegExecucaoSolicitacao(final Collection col, final HttpServletRequest request)
            throws ServiceException, Exception {

        final CategoriaOcorrenciaDAO categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO();
        final OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();

        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = new CategoriaOcorrenciaDTO();
        OrigemOcorrenciaDTO origemOcorrenciaDTO = new OrigemOcorrenciaDTO();

        final StringBuilder strBuffer = new StringBuilder();
        strBuffer.append("<table width='100%' border='1'>");
        strBuffer.append("<tr>");
        strBuffer.append("<td class='linhaSubtituloGridOcorr'>");
        strBuffer.append(UtilI18N.internacionaliza(request, "citcorpore.comum.datahora"));
        strBuffer.append("</td>");
        strBuffer.append("<td class='linhaSubtituloGridOcorr'>");
        strBuffer.append(UtilI18N.internacionaliza(request, "solicitacaoServico.informacaoexecucao"));
        strBuffer.append("</td>");
        strBuffer.append("</tr>");

        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoAux = (OcorrenciaSolicitacaoDTO) it.next();

                if (ocorrenciaSolicitacaoAux.getOcorrencia() != null) {
                    final Source source = new Source(ocorrenciaSolicitacaoAux.getOcorrencia());
                    ocorrenciaSolicitacaoAux.setOcorrencia(source.getTextExtractor().toString());
                }

                if (categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() != null
                        && categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() != 0) {
                    categoriaOcorrenciaDTO
                    .setIdCategoriaOcorrencia(ocorrenciaSolicitacaoAux.getIdCategoriaOcorrencia());
                    categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO
                            .restore(categoriaOcorrenciaDTO);
                }

                if (origemOcorrenciaDTO.getIdOrigemOcorrencia() != null
                        && origemOcorrenciaDTO.getIdOrigemOcorrencia() != 0) {
                    origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaSolicitacaoAux.getIdOrigemOcorrencia());
                    origemOcorrenciaDAO.restore(origemOcorrenciaDTO);
                }

                String ocorrencia = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getOcorrencia());
                String descricao = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getDescricao());
                String informacoesContato = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getInformacoesContato());
                ocorrencia = ocorrencia.replaceAll("\"", "");
                descricao = descricao.replaceAll("\"", "");
                informacoesContato = informacoesContato.replaceAll("\"", "");
                ocorrencia = ocorrencia.replaceAll("\n", "<br>");
                descricao = descricao.replaceAll("\n", "<br>");
                informacoesContato = informacoesContato.replaceAll("\n", "<br>");
                ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", ""));
                descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", ""));
                informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", ""));
                strBuffer.append("<tr>");
                strBuffer.append("<td style='border:1px solid black'>");
                strBuffer.append("<b>"
                        + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT,
                                ocorrenciaSolicitacaoAux.getDataregistro(), WebUtil.getLanguage(request)) + " - "
                                + ocorrenciaSolicitacaoAux.getHoraregistro());

                String strRegPor = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getRegistradopor());
                try {
                    if (ocorrenciaSolicitacaoAux.getRegistradopor() != null
                            && !ocorrenciaSolicitacaoAux.getRegistradopor().trim().equalsIgnoreCase("Automático")) {
                        final UsuarioDTO usuarioDto = getUsuarioService().restoreByLogin(
                                ocorrenciaSolicitacaoAux.getRegistradopor());
                        if (usuarioDto != null) {
                            final EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuarioDto
                                    .getIdEmpregado());
                            strRegPor = strRegPor + " - " + empregadoDto.getNome();
                        }
                    }
                } catch (final Exception e) {
                    e.printStackTrace();
                }

                strBuffer.append(" - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.registradopor")
                        + ": <b>" + strRegPor + "</b>");
                strBuffer.append("</td>");
                strBuffer.append("<td style='border:1px solid black'>");
                strBuffer.append("<b>" + ocorrenciaSolicitacaoAux.getDescricao() + "<br><br></b>");
                strBuffer.append("<b>" + ocorrencia + "<br><br></b>");

                if (ocorrenciaSolicitacaoAux.getCategoria() != null
                        && !ocorrenciaSolicitacaoAux.getCategoria().equals("")) {
                    if (ocorrenciaSolicitacaoAux.getCategoria().trim()
                            .equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.toString())
                            || ocorrenciaSolicitacaoAux.getCategoria().trim()
                            .equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.toString())
                            || ocorrenciaSolicitacaoAux.getCategoria().trim()
                            .equalsIgnoreCase(Enumerados.CategoriaOcorrencia.SuspensaoSLA.toString())) {
                        JustificativaSolicitacaoDTO justificativaSolicitacaoDTO = new JustificativaSolicitacaoDTO();
                        if (ocorrenciaSolicitacaoAux.getIdJustificativa() != null) {
                            justificativaSolicitacaoDTO.setIdJustificativa(ocorrenciaSolicitacaoAux
                                    .getIdJustificativa());
                            justificativaSolicitacaoDTO = (JustificativaSolicitacaoDTO) getJustificativaSolicitacaoService()
                                    .restore(justificativaSolicitacaoDTO);
                            if (justificativaSolicitacaoDTO != null) {
                                strBuffer.append(UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa")
                                        + ": <b>" + justificativaSolicitacaoDTO.getDescricaoJustificativa()
                                        + "<br><br></b>");
                            }
                        }
                        if (!UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getComplementoJustificativa()).trim()
                                .equalsIgnoreCase("")) {
                            strBuffer.append("<b>"
                                    + UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getComplementoJustificativa())
                                    + "<br><br></b>");
                        }
                    }
                }
                strBuffer.append("</td>");
                strBuffer.append("</tr>");
            }
        }
        strBuffer.append("</table>");

        categoriaOcorrenciaDTO = null;
        origemOcorrenciaDTO = null;

        return strBuffer.toString();
    }

    public void abrirListaDeSubSolicitacoes(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        String html = "";
        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
            final SolicitacaoServicoDTO solicitacaoServicoAux = (SolicitacaoServicoDTO) getSolicitacaoServicoService()
                    .restore(solicitacaoServicoDto);
            final Collection colFinal = new ArrayList();
            if (solicitacaoServicoAux != null) {
                if (solicitacaoServicoAux.getIdSolicitacaoRelacionada() != null) {
                    solicitacaoServicoAux.setIdSolicitacaoServico(solicitacaoServicoAux.getIdSolicitacaoRelacionada());
                    final SolicitacaoServicoDTO solicitacaoServicoAux2 = (SolicitacaoServicoDTO) getSolicitacaoServicoService()
                            .restore(solicitacaoServicoAux);
                    if (solicitacaoServicoAux2 != null) {
                        colFinal.add(solicitacaoServicoAux2);
                    }
                }
            }

            final Collection<SolicitacaoServicoDTO> solicitacoesRelacionadas = getSolicitacaoServicoService()
                    .listSolicitacaoServicoRelacionadaPai(solicitacaoServicoDto.getIdSolicitacaoServico());
            if (solicitacoesRelacionadas != null) {
                colFinal.addAll(solicitacoesRelacionadas);
            }

            final StringBuilder script = new StringBuilder();

            /**
             * Quantitativo de subsolicitações
             *
             * @author thays.araujo
             */
            Integer quantidadeNovasSolicitacoes;
            String quantidadeNovasSolicitacoesStr;
            if (colFinal != null) {
                quantidadeNovasSolicitacoes = colFinal.size();
                quantidadeNovasSolicitacoesStr = String.valueOf(quantidadeNovasSolicitacoes);
                document.getElementById("quantidadeNovasSolicitacoes").setValue(quantidadeNovasSolicitacoesStr);
            }

            html = gerarHtmlComListaSubSolicitacoes(colFinal, script, request);

            document.getElementById("solicitacaoRelacionada").setInnerHTML(html);
        }

        solicitacaoServicoDto = null;
    }

    /**
     * Retorna Grau de Importância.
     *
     * @param request
     * @param importancia
     * @return String
     * @author Vadoilo Damasceno
     */
    public String getGrauImportancia(final HttpServletRequest request, final Integer importancia) {
        if (importancia != null) {
            if (importancia == 1) {
                return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": "
                        + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.baixo");
            } else {
                if (importancia == 2) {
                    return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": "
                            + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.medio");
                } else {
                    if (importancia == 3) {
                        return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": "
                                + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.alto");
                    }
                }
            }
        }
        return "";
    }

    private String gerarHtmlComListaSubSolicitacoes(
            final Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada, final StringBuilder script,
            final HttpServletRequest request) {
        final StringBuilder html = new StringBuilder();

        html.append("<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' width='100%'");
        html.append("<tr>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.dataabertura") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.resposta") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.prazoLimite") + "</th>");
        html.append("</tr>");

        if (listSolicitacaoServicoRelacionada != null && !listSolicitacaoServicoRelacionada.isEmpty()) {

            for (final SolicitacaoServicoDTO solicitacaoServicoRelacionada : listSolicitacaoServicoRelacionada) {
                html.append("<tr>");
                html.append("<hidden id='idSolicitante' value='" + solicitacaoServicoRelacionada.getIdSolicitante()
                        + "'/>");
                html.append("<hidden id='idResponsavel' value='" + solicitacaoServicoRelacionada.getIdResponsavel()
                        + "'/>");
                html.append("<td style='text-align: center;'>"
                        + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "</td>");
                html.append("<td id='dataHoraSolicitacao'>"
                        + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS,
                                solicitacaoServicoRelacionada.getDataHoraSolicitacao(), WebUtil.getLanguage(request))
                                + "</td>");
                html.append("<td>" + solicitacaoServicoRelacionada.getPrazoHH() + ":"
                        + solicitacaoServicoRelacionada.getPrazoMM() + "</td>");
                html.append("<td>" + solicitacaoServicoRelacionada.getDescricao() + "</td>");
                html.append("<td>"
                        + (solicitacaoServicoRelacionada.getResposta() != null ? solicitacaoServicoRelacionada
                                .getResposta() : "-") + "</td>");
                if (solicitacaoServicoRelacionada.getSituacao().equals("EmAndamento")) {
                    html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento")
                            + "</td>");
                } else if (solicitacaoServicoRelacionada.getSituacao().equals("Suspensa")) {
                    html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa")
                            + "</td>");
                } else if (solicitacaoServicoRelacionada.getSituacao().equals("Cancelada")) {
                    html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada")
                            + "</td>");
                } else if (solicitacaoServicoRelacionada.getSituacao().equals("Resolvida")) {
                    html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Resolvida")
                            + "</td>");
                } else {
                    html.append("<td>" + solicitacaoServicoRelacionada.getSituacao() + "</td>");
                }

                if (solicitacaoServicoRelacionada.getDataHoraLimite() != null) {
                    html.append("<td>"
                            + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS,
                                    solicitacaoServicoRelacionada.getDataHoraLimite(), WebUtil.getLanguage(request))
                                    + "</td>");
                } else {
                    html.append("<td>À combinar</td>");
                }

                html.append("</tr>");
            }
        }
        html.append("</table>");
        return html.toString();
    }

    /**
     * Retorna lista de características.
     *
     * @param idItemConfiguracao
     * @param tagTipoItemConfiguracao
     * @return listaCaracteristica
     * @throws ServiceException
     * @throws Exception
     * @author rosana.godinho
     */
    public Collection<ValorDTO> getListaCaracteristica(final ItemConfiguracaoDTO itemConfiguracao,
            final String tagTipoItemConfiguracao) throws ServiceException, Exception {
        final TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();
        tipoItemConfiguracao.setTag(tagTipoItemConfiguracao);
        return getValorService().findByItemAndTipoItemConfiguracao(itemConfiguracao, tipoItemConfiguracao);

    }

    @Override
    public Class getBeanClass() {
        return SolicitacaoServicoDTO.class;
    }

    public void marcarChecksEmail(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {

        document.getCheckboxById("enviaEmailCriacao").setChecked(false);
        document.getCheckboxById("enviaEmailAcoes").setChecked(false);
        document.getCheckboxById("enviaEmailFinalizacao").setChecked(false);

        final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        GrupoDTO grupoDTO = new GrupoDTO();
        if (solicitacaoServicoDto != null) {
            grupoDTO.setIdGrupo(solicitacaoServicoDto.getIdGrupoAtual());
        }

        if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdGrupoAtual() != null
                && !solicitacaoServicoDto.getIdGrupoAtual().equals("")) {
            grupoDTO = (GrupoDTO) getGrupoService().restore(grupoDTO);
        }

        if (grupoDTO.getAbertura() != null) {
            if (grupoDTO.getAbertura().equalsIgnoreCase("S")) {
                document.getCheckboxById("enviaEmailCriacao").setValue("S");
            }
        }
        if (grupoDTO.getAndamento() != null) {
            if (grupoDTO.getAndamento().equalsIgnoreCase("S")) {
                document.getCheckboxById("enviaEmailAcoes").setValue("S");
            }
        }
        if (grupoDTO.getEncerramento() != null) {
            if (grupoDTO.getEncerramento().equalsIgnoreCase("S")) {
                document.getCheckboxById("enviaEmailFinalizacao").setValue("S");
            }
        }
    }

    /**
     * Cria Combo de Categoria Serviço Ativas.
     *
     * @param document
     * @throws ServiceException
     * @throws Exception
     * @throws LogicException
     */
    public void criarComboCategoriaServico(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        document.executeScript("document.getElementById(\"divCategoriaServico\").style.display = 'block'");

        final HTMLSelect idCategoriaServico = document.getSelectById("idCategoriaServico");
        idCategoriaServico.removeAllOptions();
        idCategoriaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        final Collection listaDeCategoriasAtivas = categoriaService.listHierarquia();
        if (listaDeCategoriasAtivas != null) {
            idCategoriaServico.addOptions(listaDeCategoriasAtivas, "idCategoriaServico", "nomeCategoriaServicoNivel",
                    null);
        }

        document.executeScript("JANELA_AGUARDE_MENU.hide();");
    }

    /**
     * Restaura Colaborador selecionado como Solicitante, obtendo e atribuindo informaï¿½ï¿½es de Contato, Item de Configuraï¿½ï¿½o e
     * Histï¿½rico de Solicitaï¿½ï¿½es.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void restoreColaboradorSolicitante(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(
                br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");

        final SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator
                .getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance()
                .getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));

        EmpregadoDTO empregadoDto = new EmpregadoDTO();
        empregadoDto.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
        empregadoDto = (EmpregadoDTO) getEmpregadoService().restore(empregadoDto);

        if (empregadoDto != null) {
            solicitacaoServicoDto.setSolicitante(empregadoDto.getNome());
            solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
            solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());
            solicitacaoServicoDto.setRamal(empregadoDto.getRamal());

            if (empregadoDto.getEmail() != null) {
                empregadoDto.setEmail(empregadoDto.getEmail().trim());
            }
            solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail());
            solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());

            if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")
                    && solicitacaoServicoDto.getIdUnidade() != null) {
                solicitacaoServicoDto.setUnidadeDes(getUnidadeService().retornaNomeUnidadeByID(
                        solicitacaoServicoDto.getIdUnidade()));
            }

            solicitacaoServicoDto.setRamal(empregadoDto.getRamal());

            preencherComboLocalidade(document, request, response);
        }

        UsuarioDTO usuarioDto = new UsuarioDTO();

        if (empregadoDto != null && empregadoDto.getIdEmpregado() != null) {
            usuarioDto = getUsuarioService().restoreByIdEmpregado(empregadoDto.getIdEmpregado());
        }

        if (usuarioDto != null) {
            final String login = usuarioDto.getLogin();

            final SolicitacaoServicoDTO solicitacaoServicoComItemConfiguracaoDoSolicitante = solicitacaoServicoService
                    .retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(login);

            if (solicitacaoServicoComItemConfiguracaoDoSolicitante != null) {
                solicitacaoServicoDto.setIdItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante
                        .getIdItemConfiguracao());
                solicitacaoServicoDto.setItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante
                        .getItemConfiguracao());
            }
        }

        final HTMLForm form = document.getForm("form");

        document.executeScript("setDataEditor()");

        form.setValues(solicitacaoServicoDto);

        document.executeScript("fecharPopup(\"#POPUP_SOLICITANTE\")");

        List<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(
                solicitacaoServicoDto.getIdSolicitante(), null);
        if (resumo == null || resumo.size() <= 0) {
            document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'none')");
        } else {
            document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'inline')");
        }

        resumo = solicitacaoService.findSolicitacoesServicosUsuario(solicitacaoServicoDto.getIdSolicitante(), null,
                null);
        if (resumo == null || resumo.size() <= 0) {
            document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'none')");
        } else {
            document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'inline')");
        }

        String controleAccUnidade = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.CONTROLE_ACC_UNIDADE_INC_SOLIC, "N");

        if (!UtilStrings.isNotVazio(controleAccUnidade)) {
            controleAccUnidade = "N";
        }

        if (controleAccUnidade.trim().equalsIgnoreCase("S")) {
            carregaServicos(document, request, response);
        }

        document.executeScript("camposObrigatoriosSolicitante()");

        solicitacaoServicoDto = null;

        document.executeScript("fecharJanelaAguarde();");
    }

    public void restoreItemConfiguracao(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();
        ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();

        itemConfiguracaoDTO.setIdItemConfiguracao(bean.getIdItemConfiguracao());
        itemConfiguracaoDTO = (ItemConfiguracaoDTO) getItemConfiguracaoService().restore(itemConfiguracaoDTO);

        final HTMLTable tblIC = document.getTableById("tblIC");

        if (itemConfiguracaoDTO.getSequenciaIC() == null) {
            tblIC.addRow(itemConfiguracaoDTO, new String[] {"idItemConfiguracao", "identificacao", "", ""},
                    new String[] {"idItemConfiguracao"}, "Item Configuração já cadastrado!!",
                    new String[] {"exibeIconesIC"}, null, null);
        } else {
            tblIC.updateRow(itemConfiguracaoDTO, new String[] {"idItemConfiguracao", "identificacaoStatus", "", ""},
                    new String[] {"idItemConfiguracao"}, "Item Configuração já cadastrado!!",
                    new String[] {"exibeIconesIC"}, null, null, itemConfiguracaoDTO.getSequenciaIC());
        }
        document.executeScript("setquantitativoItemConfiguracao()");

        document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblIC', 'tblIC');");

        document.executeScript("$('#modal_pesquisaItemConfiguracao').modal('hide');");

        // metodo para setar urgencia e impacto de acordo com o item de configuração
        int prioridade = 0;
        int prioridadeObj = 0;
        List<ItemConfiguracaoDTO> colItensIC = (List<ItemConfiguracaoDTO>) br.com.citframework.util.WebUtil
                .deserializeCollectionFromRequest(ItemConfiguracaoDTO.class, "colItensIC_Serialize", request);
        if (colItensIC == null) {
            colItensIC = new ArrayList<ItemConfiguracaoDTO>();
        }
        colItensIC.add(itemConfiguracaoDTO);
        if (colItensIC != null) {
            for (ItemConfiguracaoDTO itemConfiguracaoDTO2 : colItensIC) {
                itemConfiguracaoDTO2 = (ItemConfiguracaoDTO) getItemConfiguracaoService().restore(itemConfiguracaoDTO2);
                if (itemConfiguracaoDTO2.getUrgencia() != null && itemConfiguracaoDTO2.getImpacto() != null) {
                    if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("B")
                            && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("B")) {
                        prioridadeObj = 1;
                    } else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("B")
                            && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("M")) {
                        prioridadeObj = 2;
                    } else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("B")
                            && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("A")) {
                        prioridadeObj = 3;
                    } else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("M")
                            && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("B")) {
                        prioridadeObj = 2;
                    } else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("M")
                            && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("M")) {
                        prioridadeObj = 3;
                    } else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("M")
                            && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("A")) {
                        prioridadeObj = 4;
                    } else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("A")
                            && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("B")) {
                        prioridadeObj = 3;
                    } else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("A")
                            && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("M")) {
                        prioridadeObj = 4;
                    } else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("A")
                            && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("A")) {
                        prioridadeObj = 5;
                    }
                    if (prioridadeObj > prioridade) {
                        prioridade = prioridadeObj;
                        document.getSelectById("urgencia").setValue(itemConfiguracaoDTO2.getUrgencia());
                        document.getSelectById("impacto").setValue(itemConfiguracaoDTO2.getImpacto());
                    }
                }
            }
        }

        bean = null;
    }

    /**
     * @author breno.guimaraes
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void renderizaHistoricoSolicitacoesIncidente(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws ServiceException, Exception {

        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance()
                .getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));

        Integer idIc = null;
        if (request.getParameter("idItemConfiguracao") != null
                && !request.getParameter("idItemConfiguracao").equals("")) {
            idIc = Integer.parseInt(request.getParameter("idItemConfiguracao"));
        }

        final List<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(null, idIc);
        final StringBuilder script = new StringBuilder();
        document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request, "true"));

        document.executeScript(script.toString());
        document.executeScript("temporizador.init();");
        document.executeScript("$(\"#tblResumo\").dialog(\"open\");");
    }

    /**
     * @author breno.guimaraes
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void renderizaHistoricoSolicitacoesIC(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws ServiceException, Exception {

        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance()
                .getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));

        Integer idIc = null;
        if (request.getParameter("idItemConfiguracao") != null
                && !request.getParameter("idItemConfiguracao").equals("")) {
            idIc = Integer.parseInt(request.getParameter("idItemConfiguracao"));
        }

        final List<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(null, idIc);
        final StringBuilder script = new StringBuilder();
        document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request, "true"));

        document.executeScript(script.toString());
        document.executeScript("temporizador.init();");
        document.executeScript("$(\"#tblResumo\").dialog(\"open\");");
    }

    /**
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     * @author breno.guimaraes
     */
    public void renderizaHistoricoSolicitacoesUsuario(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws ServiceException, Exception {
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance()
                .getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
        Integer idSolicitante = null;
        if (request.getParameter("idSolicitante") != null && !request.getParameter("idSolicitante").equals("")) {
            idSolicitante = Integer.parseInt(request.getParameter("idSolicitante"));
        }

        final List<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(idSolicitante,
                null);
        final StringBuilder script = new StringBuilder();
        document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request, "true"));

        document.executeScript(script.toString());
        document.executeScript("temporizador.init();");
        document.executeScript("$(\"#tblResumo\").dialog(\"open\");");
    }

    /**
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     * @author breno.guimaraes
     */
    public void renderizaHistoricoSolicitacoesEmAndamentoUsuario(final DocumentHTML document,
            final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance()
                .getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
        Integer idSolicitante = null;

        String ehRestauracaoSolicitacao = "";
        String ehCriacaoSolicitacao = "";

        if (request.getParameter("idSolicitante") != null && !request.getParameter("idSolicitante").equals("")) {
            ehCriacaoSolicitacao = "true";
        } else {
            ehRestauracaoSolicitacao = "true";
        }

        if (ehCriacaoSolicitacao.equalsIgnoreCase("true")) {
            idSolicitante = Integer.parseInt(request.getParameter("idSolicitante"));
        }

        if (ehRestauracaoSolicitacao.equalsIgnoreCase("true")) {
            idSolicitante = ((SolicitacaoServicoDTO) document.getBean()).getIdSolicitante();
        }

        String situacao = request.getParameter("situacaoFiltroSolicitante");
        String campoBusca = request.getParameter("buscaFiltroSolicitante");

        String situacaoFiltro = "";

        if (situacao != null) {
            if (situacao.equalsIgnoreCase("EmAndamento") || situacao == null || situacao.isEmpty()) {
                situacaoFiltro = "EmAndamento";
                situacao = UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento");
            } else if (situacao.equalsIgnoreCase("Cancelada")) {
                situacaoFiltro = "Cancelada";
                situacao = UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Cancelada");
            } else if (situacao.equalsIgnoreCase("Suspensa")) {
                situacaoFiltro = "Suspensa";
                situacao = UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Suspensa");
            } else {
                situacao = situacao.trim();
                situacaoFiltro = situacao;
            }
        }

        if (campoBusca != null) {
            campoBusca = UtilStrings.decodeCaracteresEspeciais(campoBusca);
            campoBusca = campoBusca.trim();
        }

        final StringBuilder script = new StringBuilder();
        final StringBuilder filtro = new StringBuilder();

        final List<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(idSolicitante,
                situacaoFiltro, campoBusca);
        String corpoHTML = "";

        if (ehCriacaoSolicitacao.equalsIgnoreCase("true")) {
            corpoHTML = montaHTMLResumoSolicitacoes(resumo, script, request, "true");
        } else {
            corpoHTML = montaHTMLResumoSolicitacoes(resumo, script, request, "false");
        }
        if (resumo.size() > 0) {

            document.getElementById("countSolicitacoesAbertasSolicitante").setInnerHTML("" + resumo.size());
            document.executeScript("destaqueSolicitacaoMesmoUsuario()");
        } else {
            document.getElementById("countSolicitacoesAbertasSolicitante").setInnerHTML("0");
            document.executeScript("$('#divMenuSolicitacao').removeClass('ui-state-highlight');");
        }
        filtro.append(corpoHTML);

        document.getElementById("tblResumo2").setInnerHTML(filtro.toString());

        document.executeScript(script.toString());
        if (ehCriacaoSolicitacao.equalsIgnoreCase("true")) {
            document.executeScript("temporizador.init();");
        }

    }

    /**
     * @param resumo
     *            Lista de solicitaÃ§Ãµes que serÃ¡ montada.
     * @param script
     *            A string que serÃ¡ alimentada por referÃªncia para ser executada posteriormente.
     * @return
     * @author breno.guimaraes
     */
    private String montaHTMLResumoSolicitacoes(final List<SolicitacaoServicoDTO> resumo, final StringBuilder script,
            final HttpServletRequest request, final String utilizaTemporizador) {

        final String language = (String) request.getSession().getAttribute("locale");

        final StringBuilder html = new StringBuilder();

        html.append("<div style='overflow:auto'>");
        html.append("<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' width='100%'");
        html.append("<tr>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.dataabertura") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.descricao") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisa.resposta") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.horalimite") + "</th>");
        html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.temporestante"));
        html.append("</th>");
        html.append("</tr>");
        for (final SolicitacaoServicoDTO r : resumo) {
            html.append("<tr class='trSolicitacaoUsuario' onclick=\"detalheSolicitacao('"
                    + r.getContrato()
                    + "#"
                    + r.getNomecontato()
                    + "#"
                    + r.getEmailcontato()
                    + "#"
                    + r.getTelefonecontato()
                    + "#"
                    + r.getDemanda()
                    + "#"
                    + r.getServico()
                    + "#"
                    + r.getSituacao()
                    + "')\" style=\"cursor:default\" onMouseOver=\"javascript:this.style.backgroundColor='#CFCFCF'\" onMouseOut=\"javascript:this.style.backgroundColor=''\" >");
            html.append("<hidden id='idSolicitante' value='" + r.getIdSolicitante() + "'/>");
            html.append("<hidden id='idResponsavel' value='" + r.getIdResponsavel() + "'/>");
            html.append("<td>" + r.getIdSolicitacaoServico() + "</td>");
            html.append("<td id='dataHoraSolicitacao'>"
                    + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraSolicitacao(),
                            language) + "</td>");
            html.append("<td>" + r.getPrazoHH() + ":" + r.getPrazoMM() + "</td>");
            html.append("<td>" + r.getDescricao() + "</td>");
            html.append("<td>"
                    + (r.getResposta() != null && !r.getResposta().equals("") ? UtilStrings.unescapeJavaString(r
                            .getResposta()) : "-") + "</td>");
            if (r.getSituacao().equalsIgnoreCase("EmAndamento")) {
                html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.EmAndamento")
                        + "</td>");
            } else if (r.getSituacao().equalsIgnoreCase("Fechada")) {
                html.append("<td>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao.Fechada")
                        + "</td>");
            } else {
                html.append("<td>" + r.getSituacao() + "</td>");
            }
            if (r.getDataHoraLimite() != null) {
                html.append("<td>"
                        + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, r.getDataHoraLimite(),
                                language) + "</td>");
                if (r.getSituacao().equals("EmAndamento") && utilizaTemporizador.equalsIgnoreCase("true")) {
                    script.append("temporizador.addOuvinte(new Solicitacao('tempoRestante"
                            + r.getIdSolicitacaoServico() + "', " + "'barraProgresso" + r.getIdSolicitacaoServico()
                            + "', " + "'" + r.getDataHoraSolicitacao() + "', '" + r.getDataHoraLimite() + "'));");
                }
                html.append("<td><label id='tempoRestante" + r.getIdSolicitacaoServico() + "'></label>");
                html.append("<div id='barraProgresso" + r.getIdSolicitacaoServico() + "'></div></td>");
            } else {
                html.append("<td>&nbsp;</td>");
                html.append("<td>&nbsp;</td>");
            }
            html.append("</tr>");
        }
        html.append("</table>");
        html.append("</div>");

        return html.toString();
    }

    public void listHistorico(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance()
                .getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        final Collection col = solicitacaoService.getHistoricoByIdSolicitacao(solicitacaoServicoDto
                .getIdSolicitacaoServico());

        final StringBuilder strBuilder = new StringBuilder();

        strBuilder.append("<table width='100%'>");
        strBuilder.append("<tr>");
        strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
        strBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.datahora"));
        strBuilder.append("</td>");
        strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
        strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.seqreabertura"));
        strBuilder.append("</td>");
        strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
        strBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.responsavel"));
        strBuilder.append("</td>");
        strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
        strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.acao"));
        strBuilder.append("</td>");
        strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
        strBuilder.append(UtilI18N.internacionaliza(request, "tarefa.tarefa"));
        strBuilder.append("</td>");
        strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
        strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.atribuidogrupo"));
        strBuilder.append("</td>");
        strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
        strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.atribuidousuario"));
        strBuilder.append("</td>");
        strBuilder.append("</tr>");
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final SolicitacaoServicoDTO solicitacaoServicoAux = (SolicitacaoServicoDTO) it.next();
                strBuilder.append("<tr>");
                strBuilder.append("<td style='border:1px solid black'>");
                strBuilder.append(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT,
                        solicitacaoServicoAux.getDataHora(), WebUtil.getLanguage(request))
                        + " " + UtilDatas.formatHoraFormatadaHHMMSSStr(solicitacaoServicoAux.getDataHora()));
                strBuilder.append("</td>");
                strBuilder.append("<td style='border:1px solid black'>");
                if (solicitacaoServicoAux.getSeqReabertura() == null) {
                    strBuilder.append("--");
                } else {
                    strBuilder.append(solicitacaoServicoAux.getSeqReabertura());
                }
                strBuilder.append("</td>");
                strBuilder.append("<td style='border:1px solid black'>");
                strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getResponsavel()));
                strBuilder.append("</td>");
                strBuilder.append("<td style='border:1px solid black'>");
                strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getAcaoFluxo()));
                strBuilder.append("</td>");
                strBuilder.append("<td style='border:1px solid black'>");
                strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getTarefa()));
                strBuilder.append("</td>");
                strBuilder.append("<td style='border:1px solid black'>");
                strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getSiglaGrupo()));
                strBuilder.append("</td>");
                strBuilder.append("<td style='border:1px solid black'>");
                strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getNomeUsuario()));
                strBuilder.append("</td>");
                strBuilder.append("</tr>");
            }
        }
        strBuilder.append("</table>");
        document.getElementById("divResultHistorico").setInnerHTML(strBuilder.toString());

        solicitacaoServicoDto = null;
    }

    public void gravarAnexo(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        final SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator
                .getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
        final Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute(
                "colUploadsGED");
        solicitacaoServicoDto.setColArquivosUpload(arquivosUpados);
        document.getElementById("contatdorAnexo").setValue("" + arquivosUpados.size());
        // Rotina para gravar no banco
        if (solicitacaoServicoDto.getColArquivosUpload() != null
                && solicitacaoServicoDto.getColArquivosUpload().size() > 0) {
            Integer idEmpresa = WebUtil.getIdEmpresa(request);
            if (idEmpresa == null) {
                idEmpresa = 1;
            }
            solicitacaoServicoService.gravaInformacoesGED(solicitacaoServicoDto.getColArquivosUpload(), idEmpresa,
                    solicitacaoServicoDto, null);
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
            document.executeScript("('#POPUP_menuAnexos').dialog('close');");
        }

        solicitacaoServicoDto = null;
    }

    public Integer carregarProblema(final Integer indice, final Integer id) throws ServiceException,
    br.com.citframework.excecao.LogicException, Exception {
        ProblemaDTO problemadto = new ProblemaDTO();
        problemadto.setIdSolicitacaoServico(id);
        if (id != null) {
            final Collection col = getProblemaService().findBySolictacaoServico(problemadto);
            if (col == null) {
                return null;
            }
            problemadto = (ProblemaDTO) ((List) col).get(indice);
            if (problemadto == null) {
                return null;
            }
        }
        return problemadto.getIdProblema();
    }

    public Integer obterGrauDeImportanciaParaUsuario(final BaseConhecimentoDTO baseConhecimentoDto,
            final UsuarioDTO usuarioDto) throws Exception {

        final Collection<GrupoEmpregadoDTO> listGrupoEmpregadoDto = getGrupoEmpregadoService().findByIdEmpregado(
                usuarioDto.getIdEmpregado());

        final ImportanciaConhecimentoGrupoDTO importanciaConhecimento = getImportanciaConhecimentoGrupoService()
                .obterGrauDeImportancia(baseConhecimentoDto, listGrupoEmpregadoDto, usuarioDto);

        if (importanciaConhecimento != null) {
            return Integer.parseInt(importanciaConhecimento.getGrauImportancia());
        } else {
            return 0;
        }
    }

    /**
     * Adicionado para fazer limpeza da seção quando for gerenciamento de Serviço
     *
     * @author mario.junior
     * @since 31/10/2013 09:36
     */
    public void carregaFlagGerenciamento(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        request.getSession(true).setAttribute("flagGerenciamento", "S");
    }

    public void flagGerenciamentoClose(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        request.getSession(true).setAttribute("flagGerenciamento", null);
    }

    /**
     * Metodo para gerar o quantitativo de Incidentes Relacionados com a solicitação Pai.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    public void setQuantitativoIncidentesRelacionados(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

        if (solicitacaoServicoDto.getIdSolicitacaoServico() != null) {

            final Collection<SolicitacaoServicoDTO> solicitacoesRelacionadas = solicitacaoServicoService
                    .listSolicitacaoServicoRelacionada(solicitacaoServicoDto.getIdSolicitacaoServico());
            Integer quantidadeIncidentesRelacionados;
            String quantidadeIncidentesRelacionadosStr = "0";
            if (solicitacoesRelacionadas != null) {
                quantidadeIncidentesRelacionados = solicitacoesRelacionadas.size();
                quantidadeIncidentesRelacionadosStr = String.valueOf(quantidadeIncidentesRelacionados);
                document.getElementById("quantidadeIncidentesRelacionados").setValue(
                        quantidadeIncidentesRelacionadosStr);
            }
        }

    }

    /**
     * Adicionado para colocar a descrição da solicitação de serviço na sessão e enviar para a requisição de problema
     *
     * @author bruno.aquino
     * @since 02/06/2014 16:00
     */
    public void setarDescricaoNaSessaoCadastrarProblema(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        request.getSession().setAttribute("DescricaoSolicitacao", solicitacaoServicoDto.getDescricao());
        document.executeScript("cadastrarProblemaAbrirFame()");
    }

    /**
     * Adicionado para colocar a descrição da solicitação de serviço na sessão e enviar para a requisição de mudança
     *
     * @author bruno.aquino
     * @since 02/06/2014 16:00
     */
    public void setarDescricaoNaSessaoCadastrarMudanca(final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
        request.getSession().setAttribute("DescricaoSolicitacao", solicitacaoServicoDto.getDescricao());
        document.executeScript("cadastrarMudancaAbrirFame()");
    }

    public UnidadeService getUnidadeService() throws ServiceException {
        if (unidadeService == null) {
            unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
        }
        return unidadeService;
    }

    private ProblemaService getProblemaService() throws ServiceException, Exception {
        if (problemaService == null) {
            problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
        }
        return problemaService;
    }

    private RequisicaoMudancaService getRequisicaoMudancaService() throws ServiceException, Exception {
        if (requisicaoMudancaService == null) {
            requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(
                    RequisicaoMudancaService.class, null);
        }
        return requisicaoMudancaService;
    }

    private ConhecimentoSolicitacaoService getConhecimentoSolicitacaoService() throws ServiceException {
        if (conhecimentoSolicitacaoService == null) {
            conhecimentoSolicitacaoService = (ConhecimentoSolicitacaoService) ServiceLocator.getInstance().getService(
                    ConhecimentoSolicitacaoService.class, null);
        }
        return conhecimentoSolicitacaoService;
    }

    public GrupoService getGrupoService() throws ServiceException {
        if (grupoService == null) {
            grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
        }
        return grupoService;
    }

    public SolicitacaoServicoService getSolicitacaoServicoService() throws ServiceException {
        if (solicitacaoServicoService == null) {
            solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(
                    SolicitacaoServicoService.class, null);
        }
        return solicitacaoServicoService;
    }

    public ClienteService getClienteService() throws ServiceException {
        if (clienteService == null) {
            clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
        }
        return clienteService;
    }

    public OrigemAtendimentoService getOrigemAtendimentoService() throws ServiceException {
        if (origemAtendimentoService == null) {
            origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(
                    OrigemAtendimentoService.class, null);
        }
        return origemAtendimentoService;
    }

    public ContratoService getContratoService() throws ServiceException {
        if (contratoService == null) {
            contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
        }
        return contratoService;
    }

    public ServicoContratoService getServicoContratoService() throws ServiceException {
        if (servicoContratoService == null) {
            servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(
                    ServicoContratoService.class, null);
        }
        return servicoContratoService;
    }

    public CategoriaSolucaoService getCategoriaSolucaoService() throws ServiceException {
        if (categoriaSolucaoService == null) {
            categoriaSolucaoService = (CategoriaSolucaoService) ServiceLocator.getInstance().getService(
                    CategoriaSolucaoService.class, null);
        }
        return categoriaSolucaoService;
    }

    public CausaIncidenteService getCausaIncidenteService() throws ServiceException {
        if (causaIncidenteService == null) {
            causaIncidenteService = (CausaIncidenteService) ServiceLocator.getInstance().getService(
                    CausaIncidenteService.class, null);
        }
        return causaIncidenteService;
    }

    public TipoDemandaServicoService getTipoDemandaService() throws ServiceException {
        if (tipoDemandaService == null) {
            tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(
                    TipoDemandaServicoService.class, null);
        }
        return tipoDemandaService;
    }

    public FornecedorService getFornecedorService() throws ServiceException {
        if (fornecedorService == null) {
            fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class,
                    null);
        }
        return fornecedorService;
    }

    public AcordoNivelServicoService getAcordoNivelServicoService() throws ServiceException {
        if (acordoNivelServicoService == null) {
            acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(
                    AcordoNivelServicoService.class, null);
        }
        return acordoNivelServicoService;
    }

    public AcordoServicoContratoService getAcordoServicoContratoService() throws ServiceException {
        if (acordoServicoContratoService == null) {
            acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(
                    AcordoServicoContratoService.class, null);
        }
        return acordoServicoContratoService;
    }

    public GrupoEmpregadoService getGrupoEmpregadoService() throws ServiceException {
        if (grupoEmpregadoService == null) {
            grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(
                    GrupoEmpregadoService.class, null);
        }
        return grupoEmpregadoService;
    }

    public ImportanciaConhecimentoGrupoService getImportanciaConhecimentoGrupoService() throws ServiceException {
        if (importanciaConhecimentoGrupoService == null) {
            importanciaConhecimentoGrupoService = (ImportanciaConhecimentoGrupoService) ServiceLocator.getInstance()
                    .getService(ImportanciaConhecimentoGrupoService.class, null);
        }
        return importanciaConhecimentoGrupoService;
    }

    public ItemConfiguracaoService getItemConfiguracaoService() throws ServiceException {
        if (itemConfiguracaoService == null) {
            itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(
                    ItemConfiguracaoService.class, null);
        }
        return itemConfiguracaoService;
    }

    public ItemCfgSolicitacaoServService getItemCfgSolicitacaoServService() throws ServiceException {
        if (itemCfgSolicitacaoServService == null) {
            itemCfgSolicitacaoServService = (ItemCfgSolicitacaoServService) ServiceLocator.getInstance().getService(
                    ItemCfgSolicitacaoServService.class, null);
        }
        return itemCfgSolicitacaoServService;
    }

    public UsuarioService getUsuarioService() throws ServiceException {
        if (usuarioService == null) {
            usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        }
        return usuarioService;
    }

    public EmpregadoService getEmpregadoService() throws ServiceException, Exception {
        if (empregadoService == null) {
            empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
        }
        return empregadoService;
    }

    public TipoItemConfiguracaoService getTipoItemConfiguracaoService() throws ServiceException {
        if (tipoItemConfiguracaoService == null) {
            tipoItemConfiguracaoService = (TipoItemConfiguracaoService) ServiceLocator.getInstance().getService(
                    TipoItemConfiguracaoService.class, null);
        }
        return tipoItemConfiguracaoService;
    }

    public CategoriaServicoService getCategoriaService() throws ServiceException {
        if (categoriaService == null) {
            categoriaService = (CategoriaServicoService) ServiceLocator.getInstance().getService(
                    CategoriaServicoService.class, null);
        }
        return categoriaService;
    }

    public ControleGEDService getControleGedService() throws ServiceException {
        if (controleGedService == null) {
            controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class,
                    null);
        }
        return controleGedService;
    }

    public PrioridadeSolicitacoesService getPrioridadeSolicitacoesService() throws ServiceException {
        if (prioridadeSolicitacoesService == null) {
            prioridadeSolicitacoesService = (PrioridadeSolicitacoesService) ServiceLocator.getInstance().getService(
                    PrioridadeSolicitacoesService.class, null);
        }
        return prioridadeSolicitacoesService;
    }

    public ServicoService getServicoService() throws ServiceException {
        if (servicoService == null) {
            servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
        }
        return servicoService;
    }

    public OcorrenciaSolicitacaoService getOcorrenciaSolicitacaoService() throws ServiceException {
        if (ocorrenciaSolicitacaoService == null) {
            ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(
                    OcorrenciaSolicitacaoService.class, null);
        }
        return ocorrenciaSolicitacaoService;
    }

    public JustificativaSolicitacaoService getJustificativaSolicitacaoService() throws ServiceException {
        if (justificativaSolicitacaoService == null) {
            justificativaSolicitacaoService = (JustificativaSolicitacaoService) ServiceLocator.getInstance()
                    .getService(JustificativaSolicitacaoService.class, null);
        }
        return justificativaSolicitacaoService;
    }

    public ValorService getValorService() throws ServiceException {
        if (valorService == null) {
            valorService = (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
        }
        return valorService;
    }

    public BaseConhecimentoService getBaseConhecimentoService() throws ServiceException {
        if (baseConhecimentoService == null) {
            baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(
                    BaseConhecimentoService.class, null);
        }
        return baseConhecimentoService;
    }

    public LocalidadeService getLocalidadeService() throws ServiceException {
        if (localidadeService == null) {
            localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class,
                    null);
        }
        return localidadeService;
    }

    public ContadorAcessoService getContadorAcessoService() throws ServiceException {
        if (contadorAcessoService == null) {
            contadorAcessoService = (ContadorAcessoService) ServiceLocator.getInstance().getService(
                    ContadorAcessoService.class, null);
        }
        return contadorAcessoService;
    }

    public LocalidadeUnidadeService getLocalidadeUnidadeService() throws ServiceException {
        if (localidadeUnidadeService == null) {
            localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(
                    LocalidadeUnidadeService.class, null);
        }
        return localidadeUnidadeService;
    }

}
