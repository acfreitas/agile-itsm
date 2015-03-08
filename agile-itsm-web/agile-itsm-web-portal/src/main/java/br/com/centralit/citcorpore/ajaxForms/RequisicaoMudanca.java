package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.htmlparser.jericho.Source;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.AprovacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.AprovacaoPropostaDTO;
import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoAtvPeriodicaDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.HistoricoMudancaDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.LiberacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaResponsavelDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaRiscoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoMudancaDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AprovacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.GrupoRequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.HistoricoMudancaDao;
import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.ProblemaDAO;
import br.com.centralit.citcorpore.integracao.ProblemaMudancaDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaResponsavelDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaRiscoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoMudancaDao;
import br.com.centralit.citcorpore.negocio.AprovacaoMudancaService;
import br.com.centralit.citcorpore.negocio.AprovacaoPropostaService;
import br.com.centralit.citcorpore.negocio.AtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.CategoriaSolucaoService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContatoRequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoAtvPeriodicaService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoRequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.HistoricoMudancaService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.JustificativaRequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.LiberacaoMudancaService;
import br.com.centralit.citcorpore.negocio.LocalidadeService;
import br.com.centralit.citcorpore.negocio.LocalidadeUnidadeService;
import br.com.centralit.citcorpore.negocio.OcorrenciaMudancaService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ProblemaMudancaService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaResponsavelService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaRiscoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaServiceEjb;
import br.com.centralit.citcorpore.negocio.TemplateSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoMudancaService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

/**
 * @author breno.guimaraes
 *
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class RequisicaoMudanca extends AjaxFormAction {

    private RequisicaoMudancaService requisicaoMudancaService;
    private EmpregadoService empregadoService;

    private RequisicaoMudancaServiceEjb reqMudancaICAction;
    private RequisicaoMudancaServico reqMudancaServicoAction;

    private RequisicaoMudancaDTO requisicaoMudancaDto;
    private UsuarioDTO usuario;

    @Override
    public Class<RequisicaoMudancaDTO> getBeanClass() {
        return RequisicaoMudancaDTO.class;
    }

    private final ContratoDTO contratoDtoAux = new ContratoDTO();

    private Boolean acao = false;
    private static HttpServletRequest requestGlobal;
    private static DocumentHTML documentGlobal;

    /*
     * Thiago Fernandes - 23/10/2013 - Sol. 121468 - Realização das correções dos testes feitos nas telas do sistema. Branch 3.0.3. Assim que aver apenas um contrato deve ser
     * seleciona-lo como default.
     */
    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
        StringBuilder objeto;
        if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")) {
            objeto = new StringBuilder();
            objeto.append("<input type='text' name='unidadeDes' id='unidadeDes' style='width: 100%;' onkeypress='onkeypressUnidadeDes();'>");
            objeto.append("<input type='hidden' name='idUnidade' id='idUnidade' value='0'/>");
            document.getElementById("divUnidade").setInnerHTML(objeto.toString());
            document.executeScript("montaParametrosAutocompleteUnidade()");
        } else {
            objeto = new StringBuilder();
            objeto.append("<select name='idUnidade' id = 'idUnidade' onchange='document.form.fireEvent(\"preencherComboLocalidade\")' class='Valid[Required] Description[colaborador.cadastroUnidade]'></select>");
            document.getElementById("divUnidade").setInnerHTML(objeto.toString());
        }

        document.executeScript("$('#abas').show()");
        document.executeScript("$('#requisicaMudancaStatus').show()");
        document.executeScript("$('#statusCancelado').hide()");

        /**
         * Adicionado para fazer limpeza do upload que está na sessão
         * Modificado para quando for solicitação serviço
         *
         * @author maycon.fernandes
         * @author mario.junior
         * @since 28/10/2013 08:21
         * @author thiago.oliveira
         * @since 29/10/2013 08:21
         */
        request.getSession(true).setAttribute("colUploadPlanoDeReversaoGED", null);
        request.getSession(true).setAttribute("colUploadRequisicaoMudancaGED", null);
        final String flagGerenciamento = (String) request.getSession(true).getAttribute("flagGerenciamento");
        if (flagGerenciamento != null && flagGerenciamento.equalsIgnoreCase("S")) {
            request.getSession(true).setAttribute("flagGerenciamento", null);
        }

        final String descricaoSolicitacao = (String) request.getSession().getAttribute("DescricaoSolicitacao");
        request.getSession().removeAttribute("DescricaoSolicitacao");
        final String iframeSolicitacao = request.getParameter("solicitacaoServico");

        if (descricaoSolicitacao != null && !descricaoSolicitacao.equalsIgnoreCase("")) {
            document.getElementById("DescricaoAuxliar").setInnerHTML(descricaoSolicitacao);
            document.executeScript("setarDescricao()");
        }

        // INICIO_LOAD
        if (requisicaoMudancaDto == null || requisicaoMudancaDto.getIdRequisicaoMudanca() == null) {
            document.getElementById("btOcorrencias").setVisible(false);
        }
        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        final GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

        final Collection<GrupoDTO> lstGrupos = grupoService.getGruposByEmpregado(usuario.getIdEmpregado());

        if (lstGrupos != null) {
            for (final GrupoDTO g : lstGrupos) {
                if (g.getAbertura() != null && g.getAbertura().trim().equals("S")) {
                    document.getElementById("enviaEmailCriacao").setDisabled(true);
                }
                document.getElementById("enviaEmailGrupoComite").setDisabled(true);
                if (g.getEncerramento() != null && g.getEncerramento().trim().equals("S")) {
                    document.getElementById("enviaEmailFinalizacao").setDisabled(true);
                }
                if (g.getAndamento() != null && g.getAndamento().trim().equals("S")) {
                    document.getElementById("enviaEmailAcoes").setDisabled(true);
                }
            }
        }

        final ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
        final ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
        final FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
        final ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);

        final Collection colContratos = contratoService.listAtivos();

        String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
        if (COLABORADORES_VINC_CONTRATOS == null) {
            COLABORADORES_VINC_CONTRATOS = "N";
        }
        Collection colContratosColab = null;
        if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
            colContratosColab = contratosGruposService.findByIdEmpregado(usuario.getIdEmpregado());
        }

        final Collection<ContratoDTO> listaContratos = new ArrayList<ContratoDTO>();
        document.getSelectById("idContrato").removeAllOptions();
        if (colContratos != null) {
            if (colContratos.size() > 1) {
                // ((HTMLSelect) document.getSelectById("idContrato")).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            } else {
                acao = true;
            }

            for (final Iterator it = colContratos.iterator(); it.hasNext();) {
                final ContratoDTO contratoDto = (ContratoDTO) it.next();
                if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {
                    // Se parametro de colaboradores por contrato ativo, entao filtra.
                    if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
                        if (colContratosColab == null) {
                            continue;
                        }
                        if (!this.isContratoInList(contratoDto.getIdContrato(), colContratosColab)) {
                            continue;
                        }
                    }

                    String nomeCliente = "";
                    String nomeForn = "";
                    ClienteDTO clienteDto = new ClienteDTO();
                    clienteDto.setIdCliente(contratoDto.getIdCliente());
                    clienteDto = (ClienteDTO) clienteService.restore(clienteDto);
                    if (clienteDto != null) {
                        nomeCliente = clienteDto.getNomeRazaoSocial();
                    }
                    FornecedorDTO fornecedorDto = new FornecedorDTO();
                    fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
                    fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
                    if (fornecedorDto != null) {
                        nomeForn = fornecedorDto.getRazaoSocial();
                    }
                    contratoDtoAux.setIdContrato(contratoDto.getIdContrato());
                    if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
                        final String nomeContrato = "" + contratoDto.getNumero() + " de "
                                + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contratoDto.getDataContrato(), WebUtil.getLanguage(request)) + " (" + nomeCliente + " - "
                                + nomeForn + ")";
                        contratoDto.setNome(nomeContrato);
                        listaContratos.add(contratoDto);
                    }
                }
            }
        }

        /* Thiago Fernandes - 28/10/2013 - 1410 - Sol. 121468 - Assim que aver apenas um contrato deve ser preenchido a combo unidade de acordo com o contrato. */
        if (listaContratos != null) {
            if (listaContratos.size() == 1) {
                for (final ContratoDTO contratoDto : listaContratos) {
                    document.getSelectById("idContrato").addOption("" + contratoDto.getIdContrato(), contratoDto.getNome());
                    requisicaoMudancaDto.setIdContrato(contratoDto.getIdContrato());
                    this.carregaUnidade(document, request, response);
                }
            }
            if (listaContratos.size() > 1) {
                document.getSelectById("idContrato").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
                for (final ContratoDTO contratoDto : listaContratos) {
                    document.getSelectById("idContrato").addOption("" + contratoDto.getIdContrato(), contratoDto.getNome());
                }
            }
        }

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdContrato() != null) {
            document.getElementById("idContrato").setValue("" + requisicaoMudancaDto.getIdContrato());
        }

        if (request.getParameter("idContrato") != null && !request.getParameter("idContrato").equalsIgnoreCase("")) {
            Integer idContrato = 0;
            idContrato = Integer.parseInt(request.getParameter("idContrato"));

            if (idContrato != null) {
                document.getElementById("idContrato").setValue("" + idContrato);
            }
        }

        if (requisicaoMudancaDto != null) {
            requisicaoMudancaDto.getIdContrato();
        }
        String tarefaAssociada = "N";
        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdTarefa() != null) {
            tarefaAssociada = "S";
        }
        request.setAttribute("tarefaAssociada", tarefaAssociada);

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdContrato() != null) {
            this.verificaGrupoExecutor(document, request, response);
            document.getElementById("idGrupoAtual").setValue("" + requisicaoMudancaDto.getIdGrupoAtual());
        }
        if (acao) {
            if (requisicaoMudancaDto.getIdRequisicaoMudanca() == null || requisicaoMudancaDto.getIdRequisicaoMudanca().intValue() == 0) {
                this.verificaGrupoExecutor(document, request, response);
                if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S") && requisicaoMudancaDto.getIdUnidade() != null) {
                    requisicaoMudancaDto.setUnidadeDes(unidadeService.retornaNomeUnidadeByID(requisicaoMudancaDto.getIdUnidade()));
                } else {
                    this.carregaUnidade(document, request, response);
                }
            }

        }

        // limpa os formularios e dos anexos.
        this.limpar(document, request, response);

        document.executeScript("$('#loading_overlay').hide();");

        this.preencherComboComite(document, request, response);
        this.preencherComboGrupoExecutor(document, request, response);
        this.preencherComboCategoriaSolucao(document, request, response);
        this.preencherComboTipoMudanca(document, request, response);

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            this.restore(document, request, response);
        }

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            request.setAttribute("idRequisicaoMudanca", requisicaoMudancaDto.getIdRequisicaoMudanca());
        }

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdRequisicaoMudanca() != null && requisicaoMudancaDto.getIdGrupoComite() != null) {
            document.getElementById("idGrupoComite").setDisabled(true);
        }

        document.executeScript("$('#div_ehProposta').hide();");

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdTarefa() != null) {
            this.carregaInformacoesComplementares(document, request, requisicaoMudancaDto);
        }

        // carregar o grupo de atividade periódica para agendamento
        final HTMLForm form = document.getForm("form");
        // form.clear();

        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdGrupoAtvPeriodica() != null) {
            document.getSelectById("idGrupoAtvPeriodica").removeAllOptions();
            final GrupoAtvPeriodicaService grupoService2 = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);

            final ArrayList<GrupoAtvPeriodicaDTO> grupos = (ArrayList) grupoService2.list();
            if (grupos != null) {
                for (final GrupoAtvPeriodicaDTO grupo : grupos) {
                    if (requisicaoMudancaDto.getIdGrupoAtvPeriodica().equals(grupo.getIdGrupoAtvPeriodica())) {
                        document.getSelectById("idGrupoAtvPeriodica").addOption(grupo.getIdGrupoAtvPeriodica().toString(), grupo.getNomeGrupoAtvPeriodica());
                    }
                }
            }
        } else {

            final AtividadePeriodicaDTO atividadePeriodicaDTO = new AtividadePeriodicaDTO();
            if (requisicaoMudancaDto != null) {
                atividadePeriodicaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            }
            final HTMLSelect idGrupoAtvPeriodica = document.getSelectById("idGrupoAtvPeriodica");
            idGrupoAtvPeriodica.removeAllOptions();
            final GrupoAtvPeriodicaService grupoAtvPeriodicaService = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);
            final Collection colGrupos = grupoAtvPeriodicaService.listGrupoAtividadePeriodicaAtiva();
            idGrupoAtvPeriodica.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            idGrupoAtvPeriodica.addOptions(colGrupos, "idGrupoAtvPeriodica", "nomeGrupoAtvPeriodica", null);

            form.setValues(atividadePeriodicaDTO);
        }
        document.getElementById("iframeSolicitacao").setValue(iframeSolicitacao);
        document.executeScript("event()");
        requisicaoMudancaDto = null;
        // FIM_LOAD
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

    public void verificaGrupoExecutor(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();
        if (requisicaoMudancaDto.getIdContrato() == null || requisicaoMudancaDto.getIdContrato().intValue() == 0) {
            requisicaoMudancaDto.setIdContrato(contratoDtoAux.getIdContrato());
        }
        String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
        if (COLABORADORES_VINC_CONTRATOS == null) {
            COLABORADORES_VINC_CONTRATOS = "N";
        }
        if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
            final HTMLSelect idGrupoAtual = document.getSelectById("idGrupoAtual");
            idGrupoAtual.removeAllOptions();
            idGrupoAtual.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
            final GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
            final Collection colGrupos = grupoSegurancaService.listGruposServiceDeskByIdContrato(requisicaoMudancaDto.getIdContrato());
            if (colGrupos != null) {
                idGrupoAtual.addOptions(colGrupos, "idGrupo", "nome", null);
            }
        }

        this.verificaGrupoExecutorInterno(document, requisicaoMudancaDto);

        requisicaoMudancaDto = null;
    }

    public void verificaGrupoExecutorInterno(final DocumentHTML document, final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        if (requisicaoMudancaDto.getIdRequisicaoMudanca() == null || requisicaoMudancaDto.getIdContrato() == null) {
            return;
        }

        final RequisicaoMudancaService servicoContratoService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
        final ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(requisicaoMudancaDto.getIdContrato(),
                requisicaoMudancaDto.getIdRequisicaoMudanca());
        if (servicoContratoDto != null && servicoContratoDto.getIdGrupoExecutor() != null) {
            document.getElementById("idGrupoAtual").setValue("" + servicoContratoDto.getIdGrupoExecutor());
        } else {
            document.getElementById("idGrupoAtual").setValue("");
        }
    }

    /**
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author breno.guimaraes
     */
    public void restore(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {

        this.setRequisicaoMudancaDto((RequisicaoMudancaDTO) document.getBean());

        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");

        document.executeScript("$('#abas').show()");
        document.executeScript("$('#requisicaMudancaStatus').show()");

        final Integer idTarefa = this.getRequisicaoMudancaDto().getIdTarefa();
        final String acaoFluxo = this.getRequisicaoMudancaDto().getAcaoFluxo();
        final String escalar = this.getRequisicaoMudancaDto().getEscalar();
        final String alterarSituacao = this.getRequisicaoMudancaDto().getAlterarSituacao();
        String fase = this.getRequisicaoMudancaDto().getFase();
        final String editar = requisicaoMudancaDto.getEditar();

        this.setRequisicaoMudancaDto(this.getRequisicaoMudancaService(request).restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca()));

        if (fase == null) {
            fase = this.getRequisicaoMudancaDto().getFase();
        }

        document.getElementById("ehPropostaAux").setDisabled(true);

        if (fase != null) {
            if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
                if (fase.equalsIgnoreCase("Proposta")) {
                    document.getElementById("abaRelacionarAprovacoesMudanca").setVisible(false);
                }

                if (fase.equalsIgnoreCase("Proposta")) {
                    document.getElementById("abaRelacionarAprovacoesProposta").setVisible(true);
                    document.getElementById("relacionarAprovacoesProposta").setVisible(true);
                } else {
                    document.getElementById("abaRelacionarAprovacoesProposta").setVisible(false);
                    document.getElementById("relacionarAprovacoesProposta").setVisible(false);
                }

                if (!fase.equalsIgnoreCase("Proposta")) {
                    document.getElementById("labelEhProposta").setVisible(false);
                }
            }
        }
        this.atribuirNomeProprietarioESolicitanteParaRequisicaoDto(this.getRequisicaoMudancaDto());
        this.atualizaInformacoesRelacionamentos(document, request, response);

        this.restoreInformacoesContato(this.getRequisicaoMudancaDto(), document, request, response);

        if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S") && this.getRequisicaoMudancaDto().getIdUnidade() != null) {
            this.getRequisicaoMudancaDto().setUnidadeDes(unidadeService.retornaNomeUnidadeByID(this.getRequisicaoMudancaDto().getIdUnidade()));
        } else {
            this.restoreComboUnidade(this.getRequisicaoMudancaDto(), document, request, response);
        }

        this.restoreComboLocalidade(this.getRequisicaoMudancaDto(), document, request, response);

        this.preencherComboTipoMudanca(document, request, response);

        this.getRequisicaoMudancaDto().setIdTarefa(idTarefa);
        this.getRequisicaoMudancaDto().setAcaoFluxo(acaoFluxo);
        this.getRequisicaoMudancaDto().setEscalar(escalar);
        this.getRequisicaoMudancaDto().setAlterarSituacao(alterarSituacao);
        this.getRequisicaoMudancaDto().setFase(fase);

        final HTMLForm form = document.getForm("form");
        form.clear();
        if (this.getRequisicaoMudancaDto().getIdContrato() != null) {
            document.getSelectById("idContrato").setDisabled(true);
        }

        if (this.getRequisicaoMudancaDto().getIdSolicitante() != null) {
            document.getSelectById("addSolicitante").setDisabled(true);
        }

        if (this.getRequisicaoMudancaDto().getIdTipoMudanca() != null) {
            document.getSelectById("idTipoMudanca").setDisabled(true);
        }

        if (this.getRequisicaoMudancaDto().getIdGrupoAtual() != null) {
            document.getSelectById("idGrupoAtual").setDisabled(true);
        }

        if (this.getRequisicaoMudancaDto().getIdContatoRequisicaoMudanca() != null) {
            document.getSelectById("contato").setDisabled(true);
        }
        if (this.getRequisicaoMudancaDto().getIdGrupoComite() != null) {
            document.getSelectById("idGrupoComite").setDisabled(true);
        }

        this.restaurarAnexos(request, requisicaoMudancaDto);
        this.restaurarAnexosPlanoDeReversao(request, requisicaoMudancaDto);

        if (this.listInfoRegExecucaoRequisicaoMudanca(this.getRequisicaoMudancaDto(), request) != null) {
            document.getElementById("tblOcorrencias").setInnerHTML(this.listInfoRegExecucaoRequisicaoMudanca(requisicaoMudancaDto, request));
        }

        this.montarTabelaAprovacoesProposta(document, request, response, this.getRequisicaoMudancaDto());
        this.montarTabelaAprovacoesMudanca(document, request, response, this.getRequisicaoMudancaDto());
        this.quantidadeAprovacaoMudancaPorVotoAprovada(document, request, response, requisicaoMudancaDto);
        this.quantidadeAprovacaoMudancaPorVotoRejeitada(document, request, response, requisicaoMudancaDto);
        this.quantidadeAprovacaoPropostaPorVotoAprovada(document, request, response, requisicaoMudancaDto);
        this.quantidadeAprovacaoPropostaPorVotoRejeitada(document, request, response, requisicaoMudancaDto);

        requisicaoMudancaDto.getAlterarSituacao();
        requisicaoMudancaDto.getAlterarSituacao().equalsIgnoreCase("S");

        // restaurar-anexos
        final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        final Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaoMudancaDto.getIdRequisicaoMudanca());
        final Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
        // Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Criação de Upload para requisição mudança para evitar conflitos com outras telas do sistema que usão upload.
        request.getSession(true).setAttribute("colUploadRequisicaoMudancaGED", colAnexosUploadDTO);
        // fim-restaurar-anexos

        // Responsável
        final HTMLTable tblResponsavel = document.getTableById("tblResponsavel");
        tblResponsavel.deleteAllRows();

        final RequisicaoMudancaResponsavelService mudancaResponsavelService = (RequisicaoMudancaResponsavelService) ServiceLocator.getInstance().getService(
                RequisicaoMudancaResponsavelService.class, WebUtil.getUsuarioSistema(request));
        final Collection<RequisicaoMudancaResponsavelDTO> responsavel = mudancaResponsavelService.findByIdMudancaEDataFim(this.getRequisicaoMudancaDto().getIdRequisicaoMudanca());
        if (editar != null && editar.equalsIgnoreCase("N")) {
            tblResponsavel.addRowsByCollection(responsavel, new String[] {"", "idResponsavel", "nomeResponsavel", "nomeCargo", "telResponsavel", "emailResponsavel",
                    "papelResponsavel"}, null, "", null, null, null);
        } else {
            tblResponsavel.addRowsByCollection(responsavel, new String[] {"", "idResponsavel", "nomeResponsavel", "nomeCargo", "telResponsavel", "emailResponsavel",
                    "papelResponsavel"}, null, "", new String[] {"gerarImgDelResponsavel"}, null, null);
        }

        form.setValues(this.getRequisicaoMudancaDto());
        form.setValueText("dataHoraInicioAgendada", null,
                UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, this.getRequisicaoMudancaDto().getDataHoraInicioAgendada(), WebUtil.getLanguage(request)));
        form.setValueText("dataHoraTerminoAgendada", null,
                UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, this.getRequisicaoMudancaDto().getDataHoraTerminoAgendada(), WebUtil.getLanguage(request)));
        form.setValueText("dataHoraConclusao", null,
                UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, this.getRequisicaoMudancaDto().getDataHoraConclusao(), WebUtil.getLanguage(request)));
        if (this.getRequisicaoMudancaDto().getNomeCategoriaMudanca() != null && !this.getRequisicaoMudancaDto().getNomeCategoriaMudanca().equalsIgnoreCase("")) {
            document.executeScript("$('#nomeCategoriaMudanca').attr('disabled', " + false + ");");
            document.executeScript("$('#div_categoria').show();");
        }
        final String statusSetado = "<input type='radio' id='status' name='status' value='" + this.getRequisicaoMudancaDto().getStatus() + "' checked='checked' />"
                + this.getRequisicaoMudancaDto().getStatus() + "";
        document.getElementById("statusSetado").setInnerHTML(statusSetado);
        document.executeScript("restaurar()");

        if (editar == null || editar.equalsIgnoreCase("")) {
            this.getRequisicaoMudancaDto().setEditar("S");
        } else if (editar.equalsIgnoreCase("N")) {
            document.executeScript("$('#divBarraFerramentas').hide()");
            document.executeScript("$('#divBotoes').hide()");
            document.getForm("form").lockForm();
        }

        // Criada para mostrar a fase da requisição.
        String faseMudancaRequisicao = "";
        String sePropostaAprovada = "";
        String seRequisicaoAprovada = "";
        if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            final RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
            final TransactionControler tc = new TransactionControlerImpl(requisicaoMudancaDao.getAliasDB());

            try {
                tc.start();

                final RequisicaoMudancaService servicoContratoService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
                if (requisicaoMudancaDto.getFase() != null) {
                    if (requisicaoMudancaDto.getFase().equals("Proposta")) {
                        sePropostaAprovada = servicoContratoService.verificaAprovacaoProposta(requisicaoMudancaDto, tc);
                        if (sePropostaAprovada.equals("reprovado")) {
                            faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoPropostaReprovada");
                        } else if (sePropostaAprovada.equals("aprovado")) {
                            faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoPropostaAprovada");
                        } else {
                            faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoPropostaAguardando");
                        }
                    } else if (requisicaoMudancaDto.getFase().equals("Aprovacao")) {
                        seRequisicaoAprovada = servicoContratoService.verificaAprovacaoMudanca(requisicaoMudancaDto, tc);
                        if (seRequisicaoAprovada.equals("reprovado")) {
                            faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoReprovada");
                        } else if (seRequisicaoAprovada.equals("aprovado")) {
                            faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoAprovada");
                        } else {
                            faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoAguardando");
                        }
                    } else if (requisicaoMudancaDto.getFase().equals("Planejamento")) {
                        faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoPlanejamento");
                    } else if (requisicaoMudancaDto.getFase().equals("Execucao")) {
                        faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoExecucao");
                    } else if (requisicaoMudancaDto.getFase().equals("Avaliacao")) {
                        faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoExecucao");
                    }

                    if (faseMudancaRequisicao != null && faseMudancaRequisicao != "") {
                        request.setAttribute("faseMudancaRequisicao", faseMudancaRequisicao);
                    }
                }
            } catch (final Exception e) {
                e.printStackTrace();
            } finally {
                tc.closeQuietly();
            }
        }

        final GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        final Collection<GrupoDTO> lstGrupos = grupoService.getGruposByEmpregado(usuario.getIdEmpregado());

        this.mostraHistoricoMudanca(document, request, response, requisicaoMudancaDto);

        if (lstGrupos != null) {
            for (final GrupoDTO g : lstGrupos) {
                if (this.getRequisicaoMudancaService(request).verificaPermissaoGrupoCancelar(this.getRequisicaoMudancaDto().getIdTipoMudanca(), g.getIdGrupo())) {
                    document.executeScript("$('#statusCancelado').show()");
                    break;
                }
            }
        }

    }

    private void inicializarCombo(final HTMLSelect componenteCombo, final HttpServletRequest request) {
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
    }

    private void inicializarComboLocalidade(final HTMLSelect componenteCombo, final HttpServletRequest request) {
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
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
    public void preencherComboLocalidade(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

        final LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);

        final LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);

        LocalidadeDTO localidadeDto = new LocalidadeDTO();

        Collection<LocalidadeUnidadeDTO> listaIdlocalidadePorUnidade = null;

        Collection<LocalidadeDTO> listaIdlocalidade = null;

        final String TIRAR_VINCULO_LOCALIDADE_UNIDADE = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.TIRAR_VINCULO_LOCALIDADE_UNIDADE, "N");

        final HTMLSelect comboLocalidade = document.getSelectById("idLocalidade");
        comboLocalidade.removeAllOptions();
        if (TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("N") || TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("")) {
            if (requisicaoMudancaDto.getIdUnidade() != null) {
                listaIdlocalidadePorUnidade = localidadeUnidadeService.listaIdLocalidades(requisicaoMudancaDto.getIdUnidade());
            }
            if (listaIdlocalidadePorUnidade != null) {
                this.inicializarComboLocalidade(comboLocalidade, request);
                for (final LocalidadeUnidadeDTO localidadeUnidadeDto : listaIdlocalidadePorUnidade) {
                    localidadeDto.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
                    localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
                    comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), StringEscapeUtils.escapeJavaScript(localidadeDto.getNomeLocalidade().toString()));
                }

            }
        } else {
            listaIdlocalidade = localidadeService.listLocalidade();
            if (listaIdlocalidade != null) {
                this.inicializarComboLocalidade(comboLocalidade, request);
                for (final LocalidadeDTO localidadeDTO : listaIdlocalidade) {
                    localidadeDto.setIdLocalidade(localidadeDTO.getIdLocalidade());
                    localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
                    comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), StringEscapeUtils.escapeJavaScript(localidadeDto.getNomeLocalidade().toString()));
                }
            }

        }
    }

    /**
     * CarregarColaborador
     */
    public void restoreColaboradorSolicitante(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        RequisicaoMudancaDTO requisicaoMudanca = (RequisicaoMudancaDTO) document.getBean();

        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

        final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

        EmpregadoDTO empregadoDto = new EmpregadoDTO();
        if (requisicaoMudanca.getIdSolicitante() != null) {
            empregadoDto.setIdEmpregado(requisicaoMudanca.getIdSolicitante());
            empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);

            requisicaoMudanca.setNomeSolicitante(empregadoDto.getNome());
            requisicaoMudanca.setNomeContato(empregadoDto.getNome());
            requisicaoMudanca.setTelefoneContato(empregadoDto.getTelefone());
            requisicaoMudanca.setRamal(empregadoDto.getRamal());
            requisicaoMudanca.setEmailSolicitante(empregadoDto.getEmail().trim());
            requisicaoMudanca.setObservacao(empregadoDto.getObservacoes());
            requisicaoMudanca.setIdUnidade(empregadoDto.getIdUnidade());

            if (UNIDADE_AUTOCOMPLETE != null && UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S") && requisicaoMudanca.getIdUnidade() != null) {
                requisicaoMudanca.setUnidadeDes(unidadeService.retornaNomeUnidadeByID(requisicaoMudanca.getIdUnidade()));
            }

            this.preencherComboLocalidade(document, request, response);
        }

        document.executeScript("$('#POPUP_SOLICITANTE').dialog('close')");

        final HTMLForm form = document.getForm("form");
        form.setValues(requisicaoMudanca);
        document.executeScript("fecharPopup(\"#POPUP_EMPREGADO\")");

        requisicaoMudanca = null;
    }

    /**
     * CarregarContatoRequisicao
     */
    public void restoreInformacoesContato(final RequisicaoMudancaDTO requisicaoMudancaDto, final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        final ContatoRequisicaoMudancaService contatoRequisicaoMudancaService = (ContatoRequisicaoMudancaService) ServiceLocator.getInstance().getService(
                ContatoRequisicaoMudancaService.class, null);

        ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();
        if (requisicaoMudancaDto.getIdContatoRequisicaoMudanca() != null) {
            contatoRequisicaoMudancaDto.setIdContatoRequisicaoMudanca(requisicaoMudancaDto.getIdContatoRequisicaoMudanca());
            contatoRequisicaoMudancaDto = (ContatoRequisicaoMudancaDTO) contatoRequisicaoMudancaService.restore(contatoRequisicaoMudancaDto);
        }
        if (contatoRequisicaoMudancaDto != null) {
            requisicaoMudancaDto.setNomeContato(contatoRequisicaoMudancaDto.getNomecontato());
            requisicaoMudancaDto.setTelefoneContato(contatoRequisicaoMudancaDto.getTelefonecontato());
            requisicaoMudancaDto.setRamal(contatoRequisicaoMudancaDto.getRamal());
            requisicaoMudancaDto.setEmailSolicitante(contatoRequisicaoMudancaDto.getEmailcontato().trim());
            requisicaoMudancaDto.setObservacao(contatoRequisicaoMudancaDto.getObservacao());
            requisicaoMudancaDto.setIdLocalidade(contatoRequisicaoMudancaDto.getIdLocalidade());
        }
    }

    public void restoreComboUnidade(RequisicaoMudancaDTO requisicaoMudanca, final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
        if (requisicaoMudanca.getIdRequisicaoMudanca() != null && requisicaoMudanca.getIdRequisicaoMudanca().intValue() > 0) {
            final String validarComboUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");

            requisicaoMudanca = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudanca);

            if (requisicaoMudanca.getIdContrato() == null || requisicaoMudanca.getIdContrato().intValue() == 0) {
                requisicaoMudanca.setIdContrato(contratoDtoAux.getIdContrato());
            }
            if (requisicaoMudanca.getIdUnidade() != null) {
                final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
                final HTMLSelect comboUnidade = document.getSelectById("idUnidade");
                this.inicializarCombo(comboUnidade, request);
                if (validarComboUnidade.trim().equalsIgnoreCase("S")) {
                    final Integer idContrato = requisicaoMudanca.getIdContrato();
                    final ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);
                    if (unidades != null) {
                        for (final UnidadeDTO unidade : unidades) {
                            if (unidade.getDataFim() == null) {
                                comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel().toString()));
                            }

                        }
                    }
                } else {
                    final ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();
                    if (unidades != null) {
                        for (final UnidadeDTO unidade : unidades) {
                            if (unidade.getDataFim() == null) {
                                comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel().toString()));
                            }
                        }
                    }
                }
            }
        }
    }

    public void restoreComboLocalidade(final RequisicaoMudancaDTO requisicaoMudanca, final DocumentHTML document, final HttpServletRequest request,
            final HttpServletResponse response) throws Exception {
        if (requisicaoMudanca.getIdRequisicaoMudanca() != null && requisicaoMudanca.getIdRequisicaoMudanca().intValue() > 0) {

            final String TIRAR_VINCULO_LOCALIDADE_UNIDADE = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.TIRAR_VINCULO_LOCALIDADE_UNIDADE, "N");

            if (requisicaoMudanca.getIdContrato() == null || requisicaoMudanca.getIdContrato().intValue() == 0) {
                requisicaoMudanca.setIdContrato(contratoDtoAux.getIdContrato());
            }

            if (requisicaoMudanca.getIdLocalidade() != null) {

                final LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);
                final LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);
                LocalidadeDTO localidadeDto = new LocalidadeDTO();
                Collection<LocalidadeUnidadeDTO> listaIdlocalidadePorUnidade = null;
                Collection<LocalidadeDTO> listaIdlocalidade = null;

                final HTMLSelect comboLocalidade = document.getSelectById("idLocalidade");
                comboLocalidade.removeAllOptions();
                if (TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("N") || TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("")) {
                    if (requisicaoMudanca.getIdUnidade() != null) {
                        listaIdlocalidadePorUnidade = localidadeUnidadeService.listaIdLocalidades(requisicaoMudanca.getIdUnidade());
                    }
                    if (listaIdlocalidadePorUnidade != null) {
                        this.inicializarComboLocalidade(comboLocalidade, request);
                        for (final LocalidadeUnidadeDTO localidadeUnidadeDto : listaIdlocalidadePorUnidade) {
                            localidadeDto.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
                            localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
                            comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), StringEscapeUtils.escapeJavaScript(localidadeDto.getNomeLocalidade()));
                        }

                    }
                } else {
                    listaIdlocalidade = localidadeService.listLocalidade();
                    if (listaIdlocalidade != null) {
                        this.inicializarComboLocalidade(comboLocalidade, request);
                        for (final LocalidadeDTO localidadeDTO : listaIdlocalidade) {
                            localidadeDto.setIdLocalidade(localidadeDTO.getIdLocalidade());
                            localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
                            comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), StringEscapeUtils.escapeJavaScript(localidadeDto.getNomeLocalidade()));
                        }
                    }
                }
            }
        }
    }

    public void carregaUnidade(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String UNIDADE_AUTOCOMPLETE = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.UNIDADE_AUTOCOMPLETE, "N");
        if (UNIDADE_AUTOCOMPLETE != null && !UNIDADE_AUTOCOMPLETE.equalsIgnoreCase("S")) {
            final String validarComboUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
            RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

            if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null && requisicaoMudancaDto.getIdRequisicaoMudanca().intValue() > 0) {
                final RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);

                contratoDtoAux.setIdContrato(requisicaoMudancaDto.getIdContrato());

                requisicaoMudancaDto = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDto);
            }

            if (requisicaoMudancaDto.getIdContrato() == null || requisicaoMudancaDto.getIdContrato().intValue() == 0) {
                requisicaoMudancaDto.setIdContrato(contratoDtoAux.getIdContrato());
            }

            final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
            final HTMLSelect comboUnidade = document.getSelectById("idUnidade");
            this.inicializarCombo(comboUnidade, request);
            if (validarComboUnidade.trim().equalsIgnoreCase("S")) {
                final Integer idContrato = requisicaoMudancaDto.getIdContrato();
                final ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);
                if (unidades != null) {
                    for (final UnidadeDTO unidade : unidades) {
                        if (unidade.getDataFim() == null) {
                            comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel().toString()));
                        }

                    }
                }
            } else {
                final ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();
                if (unidades != null) {
                    for (final UnidadeDTO unidade : unidades) {
                        if (unidade.getDataFim() == null) {
                            comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel().toString()));
                        }
                    }
                }
            }

            requisicaoMudancaDto = null;
        }

    }

    /**
     * Centraliza atualização de informações dos objetos que se relacionam com a mudança.
     *
     * @throws ServiceException
     * @throws Exception
     */
    private void atualizaInformacoesRelacionamentos(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException,
            Exception {
        // informações dos ics relacionados
        final ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listaICsRelacionados = this.getReqMudancaICAction().listItensRelacionadosRequisicaoMudanca(requisicaoMudancaDto);
        if (listaICsRelacionados != null && listaICsRelacionados.size() > 0) {
            requisicaoMudancaDto.setItensConfiguracaoRelacionadosSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaICsRelacionados, WebUtil.getLanguage(request)));
        }

        final RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
        // informações dos servicos relacionados
        final ArrayList<RequisicaoMudancaServicoDTO> listaServicosRelacionados = this.getReqMudancaServicoAction().listItensRelacionadosRequisicaoMudanca(requisicaoMudancaDto);
        if (listaServicosRelacionados != null && listaServicosRelacionados.size() > 0) {
            requisicaoMudancaDto.setServicosRelacionadosSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaServicosRelacionados, WebUtil.getLanguage(request)));
        }

        // informações dos servicos relacionados
        final LiberacaoMudancaService liberacaoMudancaService = (LiberacaoMudancaService) ServiceLocator.getInstance().getService(LiberacaoMudancaService.class, null);
        final ArrayList<LiberacaoMudancaDTO> liberacaoMudanca = (ArrayList<LiberacaoMudancaDTO>) liberacaoMudancaService.findByIdRequisicaoMudanca(
                requisicaoMudancaDto.getIdLiberacao(), requisicaoMudancaDto.getIdRequisicaoMudanca());
        if (liberacaoMudanca != null && liberacaoMudanca.size() > 0) {
            requisicaoMudancaDto.setLiberacoesRelacionadosSerializado(br.com.citframework.util.WebUtil.serializeObjects(liberacaoMudanca, WebUtil.getLanguage(request)));
        }

        final ArrayList<RequisicaoMudancaDTO> listaSolicitacaoServico = (ArrayList<RequisicaoMudancaDTO>) requisicaoMudancaService.listMudancaByIdSolicitacao(requisicaoMudancaDto);
        if (listaSolicitacaoServico != null && listaSolicitacaoServico.size() > 0) {
            requisicaoMudancaDto.setSolicitacaoServicoSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaSolicitacaoServico, WebUtil.getLanguage(request)));
        }

        final ProblemaMudancaService problemaMudancaService = (ProblemaMudancaService) ServiceLocator.getInstance().getService(ProblemaMudancaService.class, null);
        final ArrayList<ProblemaMudancaDTO> listaProblemaMudanca = (ArrayList<ProblemaMudancaDTO>) problemaMudancaService.findByIdRequisicaoMudanca(requisicaoMudancaDto
                .getIdRequisicaoMudanca());
        if (listaProblemaMudanca != null && listaProblemaMudanca.size() > 0) {
            requisicaoMudancaDto.setProblemaSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaProblemaMudanca, WebUtil.getLanguage(request)));
        }

        final RequisicaoMudancaRiscoService requisicaoMudancaRiscoService = (RequisicaoMudancaRiscoService) ServiceLocator.getInstance().getService(
                RequisicaoMudancaRiscoService.class, null);
        final ArrayList<RequisicaoMudancaRiscoDTO> listaRiscos = (ArrayList<RequisicaoMudancaRiscoDTO>) requisicaoMudancaRiscoService
                .findByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
        if (listaRiscos != null && listaRiscos.size() > 0) {
            requisicaoMudancaDto.setRiscoSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaRiscos, WebUtil.getLanguage(request)));
        }

        final GrupoRequisicaoMudancaService grupoRequisicaoMudancaService = (GrupoRequisicaoMudancaService) ServiceLocator.getInstance().getService(
                GrupoRequisicaoMudancaService.class, null);
        final ArrayList<GrupoRequisicaoMudancaDTO> listaGrupoRequisicaoMudanca = (ArrayList<GrupoRequisicaoMudancaDTO>) grupoRequisicaoMudancaService
                .findByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
        if (listaGrupoRequisicaoMudanca != null && listaGrupoRequisicaoMudanca.size() > 0) {
            requisicaoMudancaDto.setGrupoMudancaSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaGrupoRequisicaoMudanca, WebUtil.getLanguage(request)));
        }

    }

    /**
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author breno.guimaraes
     */
    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        document.executeScript("exibeJanelaAguarde()");

        final UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
        if (usuario == null) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
            document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
            return;
        }

        requestGlobal = request;
        documentGlobal = document;

        this.setRequisicaoMudancaDto((RequisicaoMudancaDTO) document.getBean());
        this.getRequisicaoMudancaDto().setUsuarioDto(usuario);
        this.getRequisicaoMudancaDto().setDataHoraTermino(this.getRequisicaoMudancaDto().getDataHoraTerminoAgendada());
        this.getRequisicaoMudancaDto().setEnviaEmailCriacao("S");
        this.getRequisicaoMudancaDto().setEnviaEmailAcoes("S");
        this.getRequisicaoMudancaDto().setEnviaEmailFinalizacao("S");
        this.getRequisicaoMudancaDto().setEnviaEmailGrupoComite("S");

        this.getRequisicaoMudancaDto().setTitulo(this.getRequisicaoMudancaDto().getTitulo());
        this.getRequisicaoMudancaDto().setDescricao(this.getRequisicaoMudancaDto().getDescricao());
        this.getRequisicaoMudancaDto().setObservacao(this.getRequisicaoMudancaDto().getObservacao());
        this.getRequisicaoMudancaDto().setTipo(this.getRequisicaoMudancaDto().getTipo());

        // geber.costa
        this.getRequisicaoMudancaDto().setIdRequisicaoMudanca(this.getRequisicaoMudancaDto().getIdRequisicaoMudanca());
        this.getRequisicaoMudancaDto().setStatus(this.getRequisicaoMudancaDto().getStatus());
        this.getRequisicaoMudancaDto().setSituacaoLiberacao(this.getRequisicaoMudancaDto().getSituacaoLiberacao());

        this.getRequisicaoMudancaDto().setIdGrupoAtvPeriodica(this.getRequisicaoMudancaDto().getIdGrupoAtvPeriodica());

        try {
            /* Inicio Deserialização */
            final ArrayList<SolicitacaoServicoDTO> listIdSolicitacaoServico = (ArrayList<SolicitacaoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                    SolicitacaoServicoDTO.class, "solicitacaoServicoSerializado", request);
            this.getRequisicaoMudancaDto().setListIdSolicitacaoServico(listIdSolicitacaoServico);

            List<AprovacaoMudancaDTO> listAprovacoMudanca = (List<AprovacaoMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                    AprovacaoMudancaDTO.class, "aprovacaoMudancaServicoSerializado", request);

            List<AprovacaoPropostaDTO> listAprovacoProposta;

            if (this.getRequisicaoMudancaDto().getFase().equalsIgnoreCase("Proposta")) {
                listAprovacoProposta = (List<AprovacaoPropostaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(AprovacaoPropostaDTO.class,
                        "aprovacaoPropostaServicoSerializado", request);
                if (listAprovacoProposta != null) {
                    listAprovacoProposta = this.setaDataHoraVotacoesProposta(listAprovacoProposta, usuario, request);
                }

                this.getRequisicaoMudancaDto().setListAprovacaoPropostaDTO(listAprovacoProposta);
            }

            if (listAprovacoMudanca != null) {
                listAprovacoMudanca = this.setaDataHoraVotacoesMudanca(listAprovacoMudanca, usuario, request);
            }

            this.getRequisicaoMudancaDto().setListAprovacaoMudancaDTO(listAprovacoMudanca);

            final ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listRequisicaoMudancaItemConfiguracaoDTO = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(RequisicaoMudancaItemConfiguracaoDTO.class, "itensConfiguracaoRelacionadosSerializado", request);
            this.getRequisicaoMudancaDto().setListRequisicaoMudancaItemConfiguracaoDTO(listRequisicaoMudancaItemConfiguracaoDTO);

            final ArrayList<ProblemaMudancaDTO> listProblemaMudancaDTO = (ArrayList<ProblemaMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                    ProblemaMudancaDTO.class, "problemaSerializado", request);
            this.getRequisicaoMudancaDto().setListProblemaMudancaDTO(listProblemaMudancaDTO);

            final ArrayList<GrupoRequisicaoMudancaDTO> listGrupoRequisicaoMudancaDTO = (ArrayList<GrupoRequisicaoMudancaDTO>) br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(GrupoRequisicaoMudancaDTO.class, "grupoMudancaSerializado", request);
            this.getRequisicaoMudancaDto().setListGrupoRequisicaoMudancaDTO(listGrupoRequisicaoMudancaDTO);

            final ArrayList<RequisicaoMudancaServicoDTO> listRequisicaoMudancaServicoDTO = (ArrayList<RequisicaoMudancaServicoDTO>) br.com.citframework.util.WebUtil
                    .deserializeCollectionFromRequest(RequisicaoMudancaServicoDTO.class, "servicosRelacionadosSerializado", request);
            this.getRequisicaoMudancaDto().setListRequisicaoMudancaServicoDTO(listRequisicaoMudancaServicoDTO);

            final ArrayList<RequisicaoMudancaRiscoDTO> listRiscosDTO = (ArrayList<RequisicaoMudancaRiscoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                    RequisicaoMudancaRiscoDTO.class, "riscoSerializado", request);
            this.getRequisicaoMudancaDto().setListRequisicaoMudancaRiscoDTO(listRiscosDTO);

            final Collection<RequisicaoMudancaResponsavelDTO> colResponsavel = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                    RequisicaoMudancaResponsavelDTO.class, "responsavel_serialize", request);
            this.getRequisicaoMudancaDto().setColResponsaveis(colResponsavel);

            // geber.costa

            final ArrayList<LiberacaoMudancaDTO> listLiberacoesDTO = (ArrayList<LiberacaoMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                    LiberacaoMudancaDTO.class, "liberacoesRelacionadosSerializado", request);
            this.getRequisicaoMudancaDto().setListLiberacaoMudancaDTO(listLiberacoesDTO);

            if (this.getRequisicaoMudancaDto().getIdRequisicaoMudanca() == null || this.getRequisicaoMudancaDto().getIdRequisicaoMudanca() == 0) {

                if (this.getRequisicaoMudancaDto().getEhPropostaAux() != null) {
                    if (this.getRequisicaoMudancaDto().getEhPropostaAux().equalsIgnoreCase("S")) {
                        this.getRequisicaoMudancaDto().setEhProposta(true);
                        if (this.getRequisicaoMudancaDto().getFase() == null || this.getRequisicaoMudancaDto().getFase().equals("")
                                || UtilStrings.stringVazia(this.getRequisicaoMudancaDto().getFase())) {
                            this.getRequisicaoMudancaDto().setFase("Proposta");
                        }
                    }
                } else {
                    this.getRequisicaoMudancaDto().setEhPropostaAux("N");
                    this.getRequisicaoMudancaDto().setEhProposta(false);
                }

                this.gravarAnexoMudanca(document, request, response, this.getRequisicaoMudancaDto());
                this.gravarAnexosPlanosDeReversao(document, request, response, this.getRequisicaoMudancaDto());

                final RequisicaoMudancaService r = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
                this.setRequisicaoMudancaDto(r.create(this.getRequisicaoMudancaDto()));

                if (this.getRequisicaoMudancaDto() != null && this.getRequisicaoMudancaDto().getIframeSolicitacao().equalsIgnoreCase("true")) {
                    document.executeScript("parent.inserirMudancaNalista('" + this.getRequisicaoMudancaDto().getIdRequisicaoMudanca() + "')");
                }

                final TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);
                final Collection collTipoMudanca = tipoMudancaService.findByIdTipoMudanca(requisicaoMudancaDto.getIdTipoMudanca());
                final TipoMudancaDTO tipoMudanca = (TipoMudancaDTO) ((List) collTipoMudanca).get(0);

                document.executeScript("escondeJanelaAguarde()");

                String comando = "mostraMensagemInsercao('" + UtilI18N.internacionaliza(request, "MSG05") + ".<br>"
                        + UtilI18N.internacionaliza(request, "gerenciamentoMudanca.numerorequisicao") + " <b><u>" + this.getRequisicaoMudancaDto().getIdRequisicaoMudanca()
                        + "</u></b> " + UtilI18N.internacionaliza(request, "citcorpore.comum.crida") + ".<br><br>" + UtilI18N.internacionaliza(request, "contrato.tipo") + ":"
                        + StringEscapeUtils.escapeJavaScript(tipoMudanca.getNomeTipoMudanca().toString()) + ".<br>";
                comando = comando + "')";
                document.executeScript(comando);
                return;
            } else {
                requisicaoMudancaDto.setColArquivosUpload(this.gravarAnexoMudanca(document, request, response, this.getRequisicaoMudancaDto()));
                requisicaoMudancaDto.setColUploadPlanoDeReversaoGED(this.gravarAnexosPlanosDeReversao(document, request, response, this.getRequisicaoMudancaDto()));
                RequisicaoMudancaDTO requisicaoMudancaAuxDto = new RequisicaoMudancaDTO();
                if (this.getRequisicaoMudancaDto().getIdRequisicaoMudanca() != null) {
                    requisicaoMudancaAuxDto.setIdRequisicaoMudanca(this.getRequisicaoMudancaDto().getIdRequisicaoMudanca());
                    requisicaoMudancaAuxDto = (RequisicaoMudancaDTO) this.getRequisicaoMudancaService(request).restore(requisicaoMudancaAuxDto);
                    if (requisicaoMudancaAuxDto.getIdContrato() != null) {
                        this.getRequisicaoMudancaDto().setIdContrato(requisicaoMudancaAuxDto.getIdContrato());
                        this.getRequisicaoMudancaDto().setEhPropostaAux(requisicaoMudancaAuxDto.getEhPropostaAux());
                    }
                    if (requisicaoMudancaAuxDto.getIdGrupoComite() != null) {
                        this.getRequisicaoMudancaDto().setIdGrupoComite(requisicaoMudancaAuxDto.getIdGrupoComite());
                    }
                }

                final RequisicaoMudancaService r = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
                final boolean planoDeReversaoInformado = r.planoDeReversaoInformado(this.getRequisicaoMudancaDto(), request);
                if (planoDeReversaoInformado == true) {
                    r.update(this.getRequisicaoMudancaDto(), request);
                } else {
                    document.executeScript("abrirAbaPlanoDeReversao();");
                    return;
                }

                document.alert(UtilI18N.internacionaliza(request, "MSG06"));
            }

        } catch (final Exception e) {
            System.out.println("Falha na transação.");
            throw new ServiceException(e);
        }

        document.executeScript("escondeJanelaAguarde()");
        final HTMLForm form = document.getForm("form");
        form.clear();
        document.executeScript("fechar();");

    }

    public void saveBaseline(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usrDto = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
        if (usrDto == null) {
            return;
        }
        final HistoricoMudancaDao historicoMudancaDao = new HistoricoMudancaDao();
        final List<HistoricoMudancaDTO> set = (ArrayList<HistoricoMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(HistoricoMudancaDTO.class,
                "baselinesSerializadas", request);
        if (set != null) {
            for (final HistoricoMudancaDTO historicoMudancaDTO : set) {
                HistoricoMudancaDTO novo = new HistoricoMudancaDTO();
                novo.setBaseLine("SIM");
                if (historicoMudancaDTO.getIdHistoricoMudanca() != null) {
                    novo.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
                    novo = (HistoricoMudancaDTO) historicoMudancaDao.restore(novo);
                    novo.setBaseLine("SIM");
                    historicoMudancaDao.update(novo);
                }
            }
            document.alert(UtilI18N.internacionaliza(request, "itemConfiguracaoTree.baselineGravadasSucesso"));
            this.load(document, request, response);
        }
    }

    public static Boolean salvaGrupoAtvPeriodicaEAgenda(final RequisicaoMudancaDTO requisicaoMudancaDto) throws ServiceException, Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(requestGlobal);
        if (usuario == null) {
            documentGlobal.alert(UtilI18N.internacionaliza(requestGlobal, "citcorpore.comum.sessaoExpirada"));
            documentGlobal.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + requestGlobal.getContextPath() + "'");
            return false;
        }

        final AtividadePeriodicaDTO atividadePeriodicaDTO = new AtividadePeriodicaDTO();
        atividadePeriodicaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());

        atividadePeriodicaDTO.setDuracaoEstimada((int) calculaDuracaoEstimada(requisicaoMudancaDto));
        atividadePeriodicaDTO.setIdGrupoAtvPeriodica(requisicaoMudancaDto.getIdGrupoAtvPeriodica());
        atividadePeriodicaDTO.setDataInicio(transformaDataStringEmDate(requisicaoMudancaDto.getDataInicioStr()));
        atividadePeriodicaDTO.setHoraInicio(requisicaoMudancaDto.getHoraAgendamentoInicial());

        if (atividadePeriodicaDTO.getDuracaoEstimada() == null || atividadePeriodicaDTO.getDuracaoEstimada().intValue() == 0) {
            return false;
        } else if (requisicaoMudancaDto.getIdGrupoAtvPeriodica() == null) {
            return false;
        }
        final GrupoAtvPeriodicaService grupoAtvPeriodicaService = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);
        String orient = "";
        String ocorr = "";
        if (!ocorr.equalsIgnoreCase("")) {
            ocorr += "\n";
        }
        ocorr += UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.dataagendamento") + " " + UtilDatas.dateToSTR(atividadePeriodicaDTO.getDataInicio());
        if (!ocorr.equalsIgnoreCase("")) {
            ocorr += "\n";
        }
        ocorr += UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.horaagendamento") + " " + atividadePeriodicaDTO.getHoraInicio();
        if (!ocorr.equalsIgnoreCase("")) {
            ocorr += "\n";
        }
        ocorr += UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.duracaoestimada") + " " + atividadePeriodicaDTO.getDuracaoEstimada();
        GrupoAtvPeriodicaDTO grupoAtvPeriodicaDTO = new GrupoAtvPeriodicaDTO();
        grupoAtvPeriodicaDTO.setIdGrupoAtvPeriodica(atividadePeriodicaDTO.getIdGrupoAtvPeriodica());
        grupoAtvPeriodicaDTO = (GrupoAtvPeriodicaDTO) grupoAtvPeriodicaService.restore(grupoAtvPeriodicaDTO);
        if (grupoAtvPeriodicaDTO != null) {
            ocorr += "\n" + UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.grupo") + ": " + grupoAtvPeriodicaDTO.getNomeGrupoAtvPeriodica();
        }
        if (atividadePeriodicaDTO.getOrientacaoTecnica() != null) {
            orient = atividadePeriodicaDTO.getOrientacaoTecnica();
            ocorr += "\n" + UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.orientacaotecnica") + ": \n" + atividadePeriodicaDTO.getOrientacaoTecnica();
        }
        orient += "\n\n" + UtilI18N.internacionaliza(requestGlobal, "requisicaoMudanca.requisicaoMudanca") + ": \n" + requisicaoMudancaDto.getDescricao();

        atividadePeriodicaDTO.setTituloAtividade(UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.requisicaoMudanca") + " "
                + atividadePeriodicaDTO.getIdRequisicaoMudanca());
        atividadePeriodicaDTO.setDescricao(requisicaoMudancaDto.getDescricao());
        atividadePeriodicaDTO.setDataCriacao(UtilDatas.getDataAtual());
        atividadePeriodicaDTO.setCriadoPor(usuario.getNomeUsuario());
        atividadePeriodicaDTO.setIdContrato(requisicaoMudancaDto.getIdContrato());
        atividadePeriodicaDTO.setOrientacaoTecnica(orient);

        final Collection colItens = new ArrayList();
        final ProgramacaoAtividadeDTO programacaoAtividadeDTO = new ProgramacaoAtividadeDTO();
        programacaoAtividadeDTO.setTipoAgendamento("U");
        programacaoAtividadeDTO.setDataInicio(atividadePeriodicaDTO.getDataInicio());
        programacaoAtividadeDTO.setHoraInicio(atividadePeriodicaDTO.getHoraInicio());
        programacaoAtividadeDTO.setHoraFim("00:00");
        programacaoAtividadeDTO.setDuracaoEstimada(atividadePeriodicaDTO.getDuracaoEstimada());
        programacaoAtividadeDTO.setRepeticao("N");
        colItens.add(programacaoAtividadeDTO);

        final AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
        atividadePeriodicaDTO.setColItens(colItens);

        // verifica se já não houve agendamento para essa requisição
        final Collection<AtividadePeriodicaDTO> listAtividade = atividadePeriodicaService.findByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
        if (listAtividade != null) {
            // só é possível fazer um agendamento para a mudança, logo a lista deverá vir só com um registro ao atualizar na tela, esse registro único deve ser atualizado
            int idAtvPeriodica = 0;
            idAtvPeriodica = listAtividade.iterator().next().getIdAtividadePeriodica();
            atividadePeriodicaDTO.setIdAtividadePeriodica(idAtvPeriodica);
            atividadePeriodicaService.update(atividadePeriodicaDTO);
        } else {
            atividadePeriodicaService.create(atividadePeriodicaDTO);
        }

        final OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(
                OcorrenciaSolicitacaoService.class, null);
        final OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO = new OcorrenciaSolicitacaoDTO();
        ocorrenciaSolicitacaoDTO.setIdSolicitacaoServico(atividadePeriodicaDTO.getIdSolicitacaoServico());
        ocorrenciaSolicitacaoDTO.setDataregistro(UtilDatas.getDataAtual());
        ocorrenciaSolicitacaoDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
        ocorrenciaSolicitacaoDTO.setTempoGasto(0);
        ocorrenciaSolicitacaoDTO.setDescricao(Enumerados.CategoriaOcorrencia.Agendamento.getDescricao());
        ocorrenciaSolicitacaoDTO.setDataInicio(UtilDatas.getDataAtual());
        ocorrenciaSolicitacaoDTO.setDataFim(UtilDatas.getDataAtual());
        ocorrenciaSolicitacaoDTO.setInformacoesContato(UtilI18N.internacionaliza(requestGlobal, "MSG013"));
        ocorrenciaSolicitacaoDTO.setRegistradopor(usuario.getNomeUsuario());
        ocorrenciaSolicitacaoDTO.setOcorrencia(ocorr);
        ocorrenciaSolicitacaoDTO.setOrigem(Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
        ocorrenciaSolicitacaoDTO.setCategoria(Enumerados.CategoriaOcorrencia.Agendamento.getSigla());
        ocorrenciaSolicitacaoDTO.setIdItemTrabalho(requisicaoMudancaDto.getIdTarefa());
        ocorrenciaSolicitacaoService.create(ocorrenciaSolicitacaoDTO);

        return true;
    }

    public static long calculaDuracaoEstimada(final RequisicaoMudancaDTO requisicaoMudancaDto) {
        final long duracao = requisicaoMudancaDto.getDataHoraTerminoAgendada().getTime() - requisicaoMudancaDto.getDataHoraInicioAgendada().getTime();
        final long minutos = duracao / (1000 * 60);
        return minutos;
    }

    public static java.sql.Date transformaDataStringEmDate(final String dataSemFormatacao) throws ParseException {
        final DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
        final java.sql.Date data = new java.sql.Date(fmt.parse(dataSemFormatacao).getTime());
        return data;
    }

    /**
     * Gravo no banco de dados apenas a aprovação de mudança do usuário(Se o mesmo for aprovador)
     *
     * @author Bruno.franco
     * @author flavio.santana
     */
    public List<AprovacaoMudancaDTO> setaDataHoraVotacoesMudanca(final List<AprovacaoMudancaDTO> listAprovacoMudanca, final UsuarioDTO usuario, final HttpServletRequest request) {
        final List<AprovacaoMudancaDTO> listaAtualizada = new ArrayList<>();
        for (final AprovacaoMudancaDTO aprovacaoMudancaDTO : listAprovacoMudanca) {
            if (UtilStrings.isNotVazio(aprovacaoMudancaDTO.getVoto())) {
                if (usuario.getNomeUsuario().equalsIgnoreCase(aprovacaoMudancaDTO.getNomeEmpregado())
                        || usuario.getIdEmpregado().intValue() == aprovacaoMudancaDTO.getIdEmpregado().intValue()) {
                    final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    final String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());
                    aprovacaoMudancaDTO.setDataHoraVotacao(date);
                    listaAtualizada.add(aprovacaoMudancaDTO);
                }
            }
        }
        return listaAtualizada;
    }

    /**
     *
     * Gravo no banco de dados apenas a aprovação de proposta do usuário(Se o mesmo for aprovador)
     */
    public List<AprovacaoPropostaDTO> setaDataHoraVotacoesProposta(final List<AprovacaoPropostaDTO> listAprovacoProposta, final UsuarioDTO usuario, final HttpServletRequest request) {
        final List<AprovacaoPropostaDTO> listaAtualizada = new ArrayList<>();
        for (final AprovacaoPropostaDTO aprovacaoPropostaDTO : listAprovacoProposta) {
            if (UtilStrings.isNotVazio(aprovacaoPropostaDTO.getVoto())) {
                if (usuario.getNomeUsuario().equalsIgnoreCase(aprovacaoPropostaDTO.getNomeEmpregado())
                        || usuario.getIdEmpregado().intValue() == aprovacaoPropostaDTO.getIdEmpregado().intValue()) {
                    final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    final String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());
                    aprovacaoPropostaDTO.setDataHoraVotacao(date);
                    listaAtualizada.add(aprovacaoPropostaDTO);
                }
            }
        }
        return listaAtualizada;
    }

    /**
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author breno.guimaraes
     */
    public void delete(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();
    }

    /**
     * preencher combo de tipo fluxo
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author geber.costa
     */
    public void preencherComboTipoMudanca(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);
        final HTMLSelect comboTipoMudanca = document.getSelectById("idTipoMudanca");
        final ArrayList<TipoMudancaDTO> listTipoMudanca = (ArrayList) tipoMudancaService.getAtivos();

        comboTipoMudanca.removeAllOptions();
        comboTipoMudanca.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        if (listTipoMudanca != null) {
            for (final TipoMudancaDTO tipoMudancaDTO : listTipoMudanca) {
                if (tipoMudancaDTO.getIdTipoMudanca() != null || tipoMudancaDTO.getIdTipoMudanca() > 0) {
                    comboTipoMudanca.addOption(tipoMudancaDTO.getIdTipoMudanca().toString(), StringEscapeUtils.escapeJavaScript(tipoMudancaDTO.getNomeTipoMudanca().toString()));
                }
            }
        }
    }

    public void tratarCaracterItemConfiguracao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) {
        requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();
        ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
        String descricaoItemConfiguracao = "";
        String descricaoTratada = "";
        try {
            if (requisicaoMudancaDto != null && requisicaoMudancaDto.getHiddenDescricaoItemConfiguracao() != null) {
                descricaoItemConfiguracao = requisicaoMudancaDto.getHiddenDescricaoItemConfiguracao();
                descricaoItemConfiguracao = descricaoItemConfiguracao.replaceAll("\"", "");
                descricaoItemConfiguracao = descricaoItemConfiguracao.replaceAll("\'", "");
                descricaoTratada = StringEscapeUtils.escapeJavaScript(descricaoItemConfiguracao.toString().trim());

                final ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
                requisicaoMudancaDto.getHiddenIdItemConfiguracao();
                if (requisicaoMudancaDto != null && requisicaoMudancaDto.getHiddenIdItemConfiguracao() != null && Integer.SIZE > 0) {
                    itemConfiguracaoDTO.setIdItemConfiguracao(requisicaoMudancaDto.getHiddenIdItemConfiguracao());
                    itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO);
                    document.getElementById("hiddenIdItemConfiguracao").setValue(itemConfiguracaoDTO.getIdItemConfiguracao().toString());
                }

                document.executeScript("atualizarTabela('" + descricaoTratada + "','" + itemConfiguracaoDTO.getIdentificacao() + "')");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Atualiza as informações de nome de proprietario e nome de solicitante em uma requisicaoMudancaDto, caso haja.
     *
     * @param requisicaoMudancaDto
     * @throws ServiceException
     * @throws Exception
     */
    private void atribuirNomeProprietarioESolicitanteParaRequisicaoDto(final RequisicaoMudancaDTO requisicaoMudancaDto) throws ServiceException, Exception {
        if (requisicaoMudancaDto == null) {
            return;
        }

        final Integer idProprietario = requisicaoMudancaDto.getIdProprietario();
        final Integer idSolicitante = requisicaoMudancaDto.getIdSolicitante();

        if (idProprietario != null && idSolicitante != null) {
            requisicaoMudancaDto.setNomeProprietario(this.getEmpregadoService().restoreByIdEmpregado(idProprietario).getNome());
            requisicaoMudancaDto.setNomeSolicitante(this.getEmpregadoService().restoreByIdEmpregado(idSolicitante).getNome());
        }
    }

    /**
     * @return RequisicaoMudancaService
     * @throws ServiceException
     * @throws Exception
     * @author breno.guimaraes
     */
    private RequisicaoMudancaService getRequisicaoMudancaService(final HttpServletRequest request) throws Exception {
        if (requisicaoMudancaService == null) {
            requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request));
        }
        return requisicaoMudancaService;
    }

    /**
     * @return EmpregadoService
     * @throws ServiceException
     * @throws Exception
     * @author breno.guimaraes
     */
    private EmpregadoService getEmpregadoService() throws ServiceException {
        if (empregadoService == null) {
            empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
        }
        return empregadoService;
    }

    private RequisicaoMudancaServiceEjb getReqMudancaICAction() {
        if (reqMudancaICAction == null) {
            reqMudancaICAction = new RequisicaoMudancaServiceEjb();
        }

        return reqMudancaICAction;
    }

    private RequisicaoMudancaServico getReqMudancaServicoAction() {
        if (reqMudancaServicoAction == null) {
            reqMudancaServicoAction = new RequisicaoMudancaServico();
        }

        return reqMudancaServicoAction;
    }

    public void preencherComboComite(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        document.getSelectById("idGrupoComite").removeAllOptions();

        final GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

        final Collection<GrupoDTO> listGrupo = grupoService.listGruposComite();

        document.getSelectById("idGrupoComite").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        document.getSelectById("idGrupoComite").addOptions(listGrupo, "idGrupo", "nome", null);
    }

    /**
     * Preenche a combo com os grupos que nao fazem parte do CCM para gerar definir como Grupo executor
     *
     * @author Riubbe Oliveira
     *
     */
    public void preencherComboGrupoExecutor(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        document.getSelectById("idGrupoAtual").removeAllOptions();

        final GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

        final Collection<GrupoDTO> listGrupo = grupoService.listGruposNaoComite();

        document.getSelectById("idGrupoAtual").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

        document.getSelectById("idGrupoAtual").addOptions(listGrupo, "idGrupo", "nome", null);
    }

    /**
     * Metodo para montar grid de aprovação da requisição mudanca
     *
     * @param document
     * @param request
     * @param response
     * @param requisicaoMudanca
     * @throws Exception
     * @author thays.araujo
     */
    public void montarTabelaAprovacoesMudanca(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final RequisicaoMudancaDTO requisicaoMudanca) throws Exception {
        final UsuarioDTO usuario = WebUtil.getUsuario(request);

        EmpregadoDTO empregadoDto = new EmpregadoDTO();

        final GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);

        final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

        final AprovacaoMudancaService aprovacaoMudancaService = (AprovacaoMudancaService) ServiceLocator.getInstance().getService(AprovacaoMudancaService.class, null);

        Collection<GrupoEmpregadoDTO> listaGrupoEmpregados = null;

        Collection<AprovacaoMudancaDTO> listaAprovacaoMudanca = new ArrayList<>();

        final Set<AprovacaoMudancaDTO> setListaAprovacaoMudanca = new HashSet<>();

        boolean validacao;

        document.executeScript("deleteAllRowsMudanca()");
        document.executeScript("zerarContadores()");

        if (requisicaoMudanca.getIdRequisicaoMudanca() != null) {
            listaAprovacaoMudanca = aprovacaoMudancaService.listaAprovacaoMudancaPorIdRequisicaoMudanca(requisicaoMudanca.getIdRequisicaoMudanca(),
                    requisicaoMudanca.getIdGrupoComite(), usuario.getIdEmpregado());
        }

        if (requisicaoMudanca.getIdGrupoComite() != null) {
            listaGrupoEmpregados = grupoEmpregadoService.findByIdGrupo(requisicaoMudanca.getIdGrupoComite());
        } else {
            listaGrupoEmpregados = grupoEmpregadoService.findByIdGrupo(requisicaoMudanca.getIdGrupoAtual());
        }

        if (listaAprovacaoMudanca != null) {
            for (final AprovacaoMudancaDTO aprovacaoMudancaDto : listaAprovacaoMudanca) {
                setListaAprovacaoMudanca.add(aprovacaoMudancaDto);
            }
        }
        if (listaGrupoEmpregados != null) {
            for (final GrupoEmpregadoDTO grupoEmpregadoDTO : listaGrupoEmpregados) {
                final AprovacaoMudancaDTO aprovacao = new AprovacaoMudancaDTO();
                if (grupoEmpregadoDTO.getIdEmpregado() != null) {
                    empregadoDto.setIdEmpregado(grupoEmpregadoDTO.getIdEmpregado());
                    empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
                    aprovacao.setIdEmpregado(empregadoDto.getIdEmpregado());
                    aprovacao.setNomeEmpregado(empregadoDto.getNome());

                    setListaAprovacaoMudanca.add(aprovacao);
                }

            }

        }

        if (requisicaoMudanca.getIdProprietario() != null) {
            final AprovacaoMudancaDTO aprovacaoDto = new AprovacaoMudancaDTO();
            if (usuario.getIdUsuario().intValue() == requisicaoMudanca.getIdProprietario().intValue()) {
                empregadoDto.setIdEmpregado(usuario.getIdEmpregado());
                empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
                aprovacaoDto.setIdEmpregado(empregadoDto.getIdEmpregado());
                aprovacaoDto.setNomeEmpregado(empregadoDto.getNome());
                setListaAprovacaoMudanca.add(aprovacaoDto);

            }
        }

        if (setListaAprovacaoMudanca != null) {
            for (final AprovacaoMudancaDTO aprovacaoMudancaDto : setListaAprovacaoMudanca) {
                if (aprovacaoMudancaDto.getIdEmpregado() != null) {
                    if (aprovacaoMudancaDto.getComentario() == null) {
                        aprovacaoMudancaDto.setComentario("");
                    }
                    if (aprovacaoMudancaDto.getDataHoraInicio() == null) {
                        aprovacaoMudancaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
                    }
                    if (usuario.getIdEmpregado().intValue() == aprovacaoMudancaDto.getIdEmpregado().intValue()) {
                        validacao = false;
                    } else {
                        validacao = true;
                    }
                    if (aprovacaoMudancaDto.getDataHoraVotacao() == null) {
                        aprovacaoMudancaDto.setDataHoraVotacao(UtilI18N.internacionaliza(request, "requisicaoMudanca.aindaNaoVotou"));
                    }
                    document.executeScript("addLinhaTabelaAprovacaoMudanca('" + aprovacaoMudancaDto.getIdEmpregado() + "','" + aprovacaoMudancaDto.getNomeEmpregado() + "','"
                            + aprovacaoMudancaDto.getComentario() + "','" + aprovacaoMudancaDto.getDataHoraVotacao() + "','" + validacao + "','" + true + "')");
                    document.executeScript("atribuirCheckedVotoMudanca('" + aprovacaoMudancaDto.getVoto() + "')");
                }
            }

        }

    }

    public void montarTabelaAprovacoesProposta(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        final UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

        EmpregadoDTO empregadoDto = new EmpregadoDTO();

        final GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);

        final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

        final AprovacaoPropostaService aprovacaoPropostaService = (AprovacaoPropostaService) ServiceLocator.getInstance().getService(AprovacaoPropostaService.class, null);

        Collection<GrupoEmpregadoDTO> listaGrupoEmpregados = null;

        Collection<AprovacaoPropostaDTO> listaAprovacaoProposta = new ArrayList<AprovacaoPropostaDTO>();

        final Set<AprovacaoPropostaDTO> setListaAprovacaoProposta = new HashSet<AprovacaoPropostaDTO>();

        boolean validacao;

        document.executeScript("deleteAllRowsProposta()");
        document.executeScript("zerarContadores()");

        if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            listaAprovacaoProposta = aprovacaoPropostaService.listaAprovacaoPropostaPorIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca(),
                    requisicaoMudancaDto.getIdGrupoComite(), usuarioDto.getIdEmpregado());
        }

        if (requisicaoMudancaDto.getIdGrupoComite() != null) {
            listaGrupoEmpregados = grupoEmpregadoService.findByIdGrupo(requisicaoMudancaDto.getIdGrupoComite());
        } else {
            listaGrupoEmpregados = grupoEmpregadoService.findByIdGrupo(requisicaoMudancaDto.getIdGrupoAtual());
        }

        if (listaAprovacaoProposta != null) {
            for (final AprovacaoPropostaDTO aprovacaoPropostaDto : listaAprovacaoProposta) {
                setListaAprovacaoProposta.add(aprovacaoPropostaDto);
            }
        }
        if (listaGrupoEmpregados != null) {
            for (final GrupoEmpregadoDTO grupoEmpregadoDTO : listaGrupoEmpregados) {
                final AprovacaoPropostaDTO aprovacao = new AprovacaoPropostaDTO();
                if (grupoEmpregadoDTO.getIdEmpregado() != null) {
                    empregadoDto.setIdEmpregado(grupoEmpregadoDTO.getIdEmpregado());
                    empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
                    aprovacao.setIdEmpregado(empregadoDto.getIdEmpregado());
                    aprovacao.setNomeEmpregado(empregadoDto.getNome());

                    setListaAprovacaoProposta.add(aprovacao);
                }
            }
        }

        if (requisicaoMudancaDto.getIdProprietario() != null) {
            final AprovacaoPropostaDTO aprovacaoDto = new AprovacaoPropostaDTO();
            if (usuarioDto.getIdUsuario().intValue() == requisicaoMudancaDto.getIdProprietario().intValue()) {
                empregadoDto.setIdEmpregado(usuarioDto.getIdEmpregado());
                empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
                aprovacaoDto.setIdEmpregado(empregadoDto.getIdEmpregado());
                aprovacaoDto.setNomeEmpregado(empregadoDto.getNome());
                setListaAprovacaoProposta.add(aprovacaoDto);
            }
        }

        if (setListaAprovacaoProposta != null) {
            for (final AprovacaoPropostaDTO aprovacaoPropostaDto : setListaAprovacaoProposta) {
                if (aprovacaoPropostaDto.getIdEmpregado() != null) {
                    if (aprovacaoPropostaDto.getComentario() == null) {
                        aprovacaoPropostaDto.setComentario("");
                    }
                    if (aprovacaoPropostaDto.getDataHoraInicio() == null) {
                        aprovacaoPropostaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
                    }
                    if (usuarioDto.getIdEmpregado().intValue() == aprovacaoPropostaDto.getIdEmpregado().intValue()) {
                        validacao = false;
                    } else {
                        validacao = true;
                    }
                    if (aprovacaoPropostaDto.getDataHoraVotacao() == null) {
                        aprovacaoPropostaDto.setDataHoraVotacao(UtilI18N.internacionaliza(request, "requisicaoMudanca.aindaNaoVotou"));
                    }
                    document.executeScript("addLinhaTabelaAprovacaoProposta('" + aprovacaoPropostaDto.getIdEmpregado() + "','" + aprovacaoPropostaDto.getNomeEmpregado() + "','"
                            + aprovacaoPropostaDto.getComentario() + "','" + aprovacaoPropostaDto.getDataHoraVotacao() + "','" + validacao + "','" + true + "')");
                    document.executeScript("atribuirCheckedVotoProposta('" + aprovacaoPropostaDto.getVoto() + "')");
                }
            }

        }

    }

    public void validacaoAvancaFluxo(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response, final RequisicaoMudancaDTO requisicaoMudanca)
            throws Exception {
        final AprovacaoMudancaDTO aprovacaoMudancaDto = new AprovacaoMudancaDTO();
        final AprovacaoMudancaService aprovacaoMudancaService = (AprovacaoMudancaService) ServiceLocator.getInstance().getService(AprovacaoMudancaService.class, null);
        if (requisicaoMudanca.getIdRequisicaoMudanca() != null) {
            aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudanca.getIdRequisicaoMudanca());
            aprovacaoMudancaDto.setVoto("A");
            aprovacaoMudancaDto.setQuantidadeVotoAprovada(aprovacaoMudancaService.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto,
                    requisicaoMudanca.getIdGrupoComite()));
            aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudanca.getIdRequisicaoMudanca());
            aprovacaoMudancaDto.setVoto("R");
            aprovacaoMudancaDto.setQuantidadeVotoRejeitada(aprovacaoMudancaService.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto,
                    requisicaoMudanca.getIdGrupoComite()));
            aprovacaoMudancaDto.setQuantidadeAprovacaoMudanca(aprovacaoMudancaService.quantidadeAprovacaoMudanca(aprovacaoMudancaDto, requisicaoMudanca.getIdGrupoComite()));

        }
        if (aprovacaoMudancaDto.getQuantidadeVotoAprovada() > 0) {

            if (aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca().intValue() == aprovacaoMudancaDto.getQuantidadeVotoAprovada()) {
                document.executeScript("$('#btnGravarEFinalizar').show()");
            } else {
                if (aprovacaoMudancaDto.getQuantidadeVotoAprovada() >= aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca() / 2 + 1) {
                    document.executeScript("$('#btnGravarEFinalizar').show()");
                }
            }
        }

    }

    public void quantidadeAprovacaoMudancaPorVotoAprovada(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        final StringBuilder hmtl = new StringBuilder();
        final AprovacaoMudancaDTO aprovacaoMudancaDto = new AprovacaoMudancaDTO();
        final AprovacaoMudancaService aprovacaoMudancaService = (AprovacaoMudancaService) ServiceLocator.getInstance().getService(AprovacaoMudancaService.class, null);
        if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoMudancaDto.setVoto("A");
            aprovacaoMudancaDto.setQuantidadeVotoAprovada(aprovacaoMudancaService.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto,
                    requisicaoMudancaDto.getIdGrupoComite()));
            if (aprovacaoMudancaDto.getQuantidadeVotoAprovada() != null) {
                hmtl.append("<div>" + UtilI18N.internacionaliza(request, "itemRequisicaoProduto.qtdeAprovada") + ": " + aprovacaoMudancaDto.getQuantidadeVotoAprovada() + "</div>");
                document.getElementById("quantidadePorVotoAprovadaMudanca").setInnerHTML(hmtl.toString());
            }
        }

    }

    public void quantidadeAprovacaoPropostaPorVotoAprovada(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        final StringBuilder hmtl = new StringBuilder();
        final AprovacaoPropostaDTO aprovacaoPropostaDto = new AprovacaoPropostaDTO();
        final AprovacaoPropostaService aprovacaoPropostaService = (AprovacaoPropostaService) ServiceLocator.getInstance().getService(AprovacaoPropostaService.class, null);
        if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoPropostaDto.setVoto("A");
            aprovacaoPropostaDto.setQuantidadeVotoAprovada(aprovacaoPropostaService.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacaoPropostaDto,
                    requisicaoMudancaDto.getIdGrupoComite()));
            if (aprovacaoPropostaDto.getQuantidadeVotoAprovada() != null) {
                hmtl.append("<div>" + UtilI18N.internacionaliza(request, "itemRequisicaoProduto.qtdeAprovada") + ": " + aprovacaoPropostaDto.getQuantidadeVotoAprovada() + "</div>");
                document.getElementById("quantidadePorVotoAprovadaProposta").setInnerHTML(hmtl.toString());
            }
        }

    }

    public void quantidadeAprovacaoMudancaPorVotoRejeitada(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        final StringBuilder hmtl = new StringBuilder();
        final AprovacaoMudancaDTO aprovacaoMudancaDto = new AprovacaoMudancaDTO();
        final AprovacaoMudancaService aprovacaoMudancaService = (AprovacaoMudancaService) ServiceLocator.getInstance().getService(AprovacaoMudancaService.class, null);
        if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoMudancaDto.setVoto("R");
            aprovacaoMudancaDto.setQuantidadeVotoRejeitada(aprovacaoMudancaService.quantidadeAprovacaoMudancaPorVotoRejeitada(aprovacaoMudancaDto,
                    requisicaoMudancaDto.getIdGrupoComite()));
            if (aprovacaoMudancaDto.getQuantidadeVotoRejeitada() != null) {
                hmtl.append("<div>" + UtilI18N.internacionaliza(request, "requisicaoMudanca.quantidadeAprovacaoMudancaRejeitda") + ": "
                        + aprovacaoMudancaDto.getQuantidadeVotoRejeitada() + "</div>");
                document.getElementById("quantidadePorVotoRejeitadaMudanca").setInnerHTML(hmtl.toString());
            }
        }

    }

    public void quantidadeAprovacaoPropostaPorVotoRejeitada(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        final StringBuilder hmtl = new StringBuilder();
        final AprovacaoPropostaDTO aprovacaoPropostaDto = new AprovacaoPropostaDTO();
        final AprovacaoPropostaService aprovacaoPropostaService = (AprovacaoPropostaService) ServiceLocator.getInstance().getService(AprovacaoPropostaService.class, null);
        if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
            aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
            aprovacaoPropostaDto.setVoto("R");
            aprovacaoPropostaDto.setQuantidadeVotoAprovada(aprovacaoPropostaService.quantidadeAprovacaoPropostaPorVotoRejeitada(aprovacaoPropostaDto,
                    requisicaoMudancaDto.getIdGrupoComite()));
            if (aprovacaoPropostaDto.getQuantidadeVotoAprovada() != null) {
                hmtl.append("<div>" + UtilI18N.internacionaliza(request, "requisicaoMudanca.quantidadeAprovacaoMudancaRejeitda") + ": "
                        + aprovacaoPropostaDto.getQuantidadeVotoAprovada() + "</div>");
                document.getElementById("quantidadePorVotoRejeitadaProposta").setInnerHTML(hmtl.toString());
            }
        }

    }

    /**
     * Retorna uma lista de informações da entidade ocorrencia
     *
     * @param requisicaoMudancaDto
     * @param request
     * @return
     * @throws ServiceException
     * @throws Exception
     * @author geber.costa
     */
    public String listInfoRegExecucaoRequisicaoMudanca(final RequisicaoMudancaDTO requisicaoMudancaDto, final HttpServletRequest request) throws ServiceException, Exception {
        String ocorrenciaAux = "";
        String tamanhoAux = "";
        final JustificativaRequisicaoMudancaService justificativaRequisicaoMudancaService = (JustificativaRequisicaoMudancaService) ServiceLocator.getInstance().getService(
                JustificativaRequisicaoMudancaService.class, null);

        final OcorrenciaMudancaService ocorrenciaMudancaService = (OcorrenciaMudancaService) ServiceLocator.getInstance().getService(OcorrenciaMudancaService.class, null);

        final Collection<OcorrenciaMudancaDTO> col = ocorrenciaMudancaService.findByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());

        CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = new CategoriaOcorrenciaDTO();

        String strBuffer = "<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' style='table-layout: fixed;'>";
        strBuffer += "<tr>";
        strBuffer += "<th style='width:20%;'>";
        strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.datahora");
        strBuffer += "</th>";
        strBuffer += "<th>";
        strBuffer += UtilI18N.internacionaliza(request, "solicitacaoServico.informacaoexecucao");
        strBuffer += "</th>";
        strBuffer += "</tr>";

        if (col != null) {
            for (final OcorrenciaMudancaDTO ocorrenciaMudancaDto : col) {
                if (ocorrenciaMudancaDto.getOcorrencia() != null) {
                    final Source source = new Source(ocorrenciaMudancaDto.getOcorrencia());
                    ocorrenciaMudancaDto.setOcorrencia(source.getTextExtractor().toString());
                }

                String ocorrencia = UtilStrings.nullToVazio(ocorrenciaMudancaDto.getOcorrencia());
                if (ocorrencia != null) {
                    int tamanhoString = 0;
                    int tamanhoAQuebrar = 0;
                    int x = 200;
                    tamanhoString = ocorrencia.length();
                    tamanhoAQuebrar = tamanhoString / x;

                    for (int y = 0; y < tamanhoAQuebrar; y++) {
                        ocorrenciaAux += ocorrencia.substring(tamanhoAux.length(), x) + "\n";
                        tamanhoAux += ocorrencia.substring(tamanhoAux.length(), x);
                        x = x + 200;
                    }
                    if (tamanhoAQuebrar > 0) {
                        ocorrenciaAux += ocorrencia.substring(tamanhoAux.length(), tamanhoString);
                    }
                }
                String descricao = UtilStrings.nullToVazio(ocorrenciaMudancaDto.getDescricao());
                String informacoesContato = UtilStrings.nullToVazio(ocorrenciaMudancaDto.getInformacoesContato());
                if (ocorrenciaAux != null && ocorrenciaAux.length() > 0) {
                    ocorrencia = ocorrenciaAux;
                }
                ocorrencia = ocorrencia.replaceAll("\"", "");
                descricao = descricao.replaceAll("\"", "");
                informacoesContato = informacoesContato.replaceAll("\"", "");
                ocorrencia = ocorrencia.replaceAll("\n", "<br>");
                descricao = descricao.replaceAll("\n", "<br>");
                informacoesContato = informacoesContato.replaceAll("\n", "<br>");
                ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", ""));
                descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", ""));
                informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", ""));
                strBuffer += "<tr>";
                strBuffer += "<td>";
                strBuffer += "<b>" + UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, ocorrenciaMudancaDto.getDataregistro(), WebUtil.getLanguage(request)) + " - "
                        + ocorrenciaMudancaDto.getHoraregistro();
                strBuffer += " - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.registradopor") + ": <b>" + ocorrenciaMudancaDto.getRegistradopor() + "</b>";
                strBuffer += "</td>";
                strBuffer += "<td style='word-wrap: break-word;overflow:hidden;'>";
                strBuffer += "<b>" + ocorrenciaMudancaDto.getDescricao() + "<br><br></b>";
                strBuffer += "<b>" + ocorrencia + "<br><br></b>";

                if (ocorrenciaMudancaDto.getCategoria() != null) {
                    if (ocorrenciaMudancaDto.getCategoria().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.toString())
                            || ocorrenciaMudancaDto.getCategoria().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.toString())) {
                        JustificativaRequisicaoMudancaDTO justificativaSolicitacaoDTO = new JustificativaRequisicaoMudancaDTO();
                        if (ocorrenciaMudancaDto.getIdJustificativa() != null) {
                            justificativaSolicitacaoDTO.setIdJustificativaMudanca(ocorrenciaMudancaDto.getIdJustificativa());
                            justificativaSolicitacaoDTO = (JustificativaRequisicaoMudancaDTO) justificativaRequisicaoMudancaService.restore(justificativaSolicitacaoDTO);
                            if (justificativaSolicitacaoDTO != null) {
                                strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": <b>"
                                        + justificativaSolicitacaoDTO.getDescricaoJustificativa() + "<br><br></b>";
                            }
                        }
                        if (!UtilStrings.nullToVazio(ocorrenciaMudancaDto.getComplementoJustificativa()).trim().equalsIgnoreCase("")) {
                            strBuffer += "<b>" + UtilStrings.nullToVazio(ocorrenciaMudancaDto.getComplementoJustificativa()) + "<br><br></b>";
                        }
                    }
                }

                if (ocorrenciaMudancaDto.getOcorrencia() != null) {
                    if (categoriaOcorrenciaDTO.getNome() != null && !categoriaOcorrenciaDTO.getNome().equals("")) {
                        if (categoriaOcorrenciaDTO.getNome().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.toString())
                                || categoriaOcorrenciaDTO.getNome().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.toString())) {
                            JustificativaRequisicaoMudancaDTO justificativaSolicitacaoDTO = new JustificativaRequisicaoMudancaDTO();
                            if (ocorrenciaMudancaDto.getIdJustificativa() != null) {
                                justificativaSolicitacaoDTO.setIdJustificativaMudanca(ocorrenciaMudancaDto.getIdJustificativa());
                                justificativaSolicitacaoDTO = (JustificativaRequisicaoMudancaDTO) justificativaRequisicaoMudancaService.restore(justificativaSolicitacaoDTO);
                                if (justificativaSolicitacaoDTO != null) {
                                    strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": <b>"
                                            + justificativaSolicitacaoDTO.getDescricaoJustificativa() + "<br><br></b>";
                                }
                            }
                            if (!UtilStrings.nullToVazio(ocorrenciaMudancaDto.getComplementoJustificativa()).trim().equalsIgnoreCase("")) {
                                strBuffer += "<b>" + UtilStrings.nullToVazio(ocorrenciaMudancaDto.getComplementoJustificativa()) + "<br><br></b>";
                            }
                        }
                    }
                }
                ocorrenciaAux = "";
                tamanhoAux = "";
                strBuffer += "</td>";
                strBuffer += "</tr>";
            }
        }
        strBuffer += "</table>";

        categoriaOcorrenciaDTO = null;
        ocorrenciaAux = "";
        tamanhoAux = "";

        return strBuffer;
    }

    public RequisicaoMudancaDTO getRequisicaoMudancaDto() {
        return requisicaoMudancaDto;
    }

    public void setRequisicaoMudancaDto(final RequisicaoMudancaDTO requisicaoMudancaDto) {
        this.requisicaoMudancaDto = requisicaoMudancaDto;
    }

    public void validacaoCategoriaMudanca(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequisicaoMudancaDTO RequisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

        final TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);

        TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();

        if (RequisicaoMudancaDto.getIdTipoMudanca() != null) {
            tipoMudancaDto.setIdTipoMudanca(RequisicaoMudancaDto.getIdTipoMudanca());

            tipoMudancaDto = (TipoMudancaDTO) tipoMudancaService.restore(tipoMudancaDto);

            document.getElementById("idGrupoAtual").setValue("" + tipoMudancaDto.getIdGrupoExecutor());

            if (tipoMudancaDto.getNomeTipoMudanca() != null && tipoMudancaDto.getNomeTipoMudanca().equalsIgnoreCase("Normal")) {
                document.executeScript("$('#nomeCategoriaMudanca').attr('disabled', " + false + ");");
                document.executeScript("$('#div_categoria').show();");

                document.executeScript("$('#div_ehProposta').show();");
            } else {
                document.executeScript("$('#div_categoria').hide();");
                document.executeScript("$('#nomeCategoriaMudanca').attr('disabled', " + true + ");");

                document.executeScript("$('#div_ehProposta').hide();");
            }
        }

    }

    protected void restaurarAnexos(final HttpServletRequest request, final RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException, Exception {
        final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        final Collection colAnexos = controleGedService.listByIdTabelaAndIdLiberacaoAndLigacao(ControleGEDDTO.TABELA_REQUISICAOMUDANCA,
                requisicaoMudancaDTO.getIdRequisicaoMudanca());
        final Collection<UploadDTO> colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

        /**
         * =================================
         * Restaura anexo(s) principal.
         * =================================
         * **/
        if (colAnexosUploadDTO != null) {
            for (final UploadDTO uploadDTO : colAnexosUploadDTO) {
                if (uploadDTO.getDescricao() == null) {
                    uploadDTO.setDescricao("");
                }
            }
        }

        // Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Criação de Upload para requisição mudança para evitar conflitos com outras telas do sistema que usão upload.
        request.getSession(true).setAttribute("colUploadRequisicaoMudancaGED", colAnexosUploadDTO);
    }

    protected void restaurarAnexosPlanoDeReversao(final HttpServletRequest request, final RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException, Exception {
        Collection<UploadDTO> colAnexosUploadDTO = null;
        final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        final Collection colAnexos = controleGedService.listByIdTabelaAndIdLiberacaoAndLigacao(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA,
                requisicaoMudancaDTO.getIdRequisicaoMudanca());
        colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

        /**
         * =================================
         * Restaura anexo(s) principal.
         * =================================
         * **/
        if (colAnexosUploadDTO != null) {
            for (final UploadDTO uploadDTO : colAnexosUploadDTO) {
                if (uploadDTO.getDescricao() == null) {
                    uploadDTO.setDescricao("");
                }
                if (!UtilStrings.isNotVazio(uploadDTO.getVersao())) {
                    uploadDTO.setVersao(" ");
                }
            }
        }

        request.getSession(true).setAttribute("colUploadPlanoDeReversaoGED", colAnexosUploadDTO);
    }

    public Collection gravarAnexoMudanca(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final RequisicaoMudancaDTO requisicaoMudanca) throws Exception {
        // deleta os anexos referentes a essa requisicao de mudança para poder regravá-los
        final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        final Collection<UploadDTO> colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaoMudanca.getIdRequisicaoMudanca());
        final Collection<UploadDTO> colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

        if (colAnexosUploadDTO != null) {
            for (final Object element : colAnexosUploadDTO) {
                final UploadDTO object = (UploadDTO) element;
                controleGedService.delete(object);
            }
        }

        Integer idEmpresa = null;
        // Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Criação de Upload para requisição mudança para evitar conflitos com outras telas do sistema que usão upload.
        final Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadRequisicaoMudancaGED");
        requisicaoMudanca.setColArquivosUpload(arquivosUpados);
        // Rotina para gravar no banco
        if (requisicaoMudanca.getColArquivosUpload() != null && requisicaoMudanca.getColArquivosUpload().size() > 0) {
            idEmpresa = WebUtil.getIdEmpresa(request);
            if (idEmpresa == null) {
                idEmpresa = 1;
            }
        }
        requisicaoMudanca.setIdEmpresa(idEmpresa);

        return requisicaoMudanca.getColArquivosUpload();
    }

    public Collection gravarAnexosPlanosDeReversao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
        Integer idEmpresa = null;
        final Collection<UploadDTO> arquivosReversaoUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadPlanoDeReversaoGED");
        requisicaoMudancaDto.setColUploadPlanoDeReversaoGED(arquivosReversaoUpados);
        // Rotina para gravar no banco
        if (requisicaoMudancaDto.getColUploadPlanoDeReversaoGED() != null && requisicaoMudancaDto.getColUploadPlanoDeReversaoGED().size() > 0) {
            idEmpresa = WebUtil.getIdEmpresa(request);
            if (idEmpresa == null) {
                idEmpresa = 1;
            }
        }
        requisicaoMudancaDto.setIdEmpresa(idEmpresa);

        return requisicaoMudancaDto.getColUploadPlanoDeReversaoGED();
    }

    public void imprimirRelatorioReqMudanca(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();
        final OcorrenciaMudancaService ocorrenciaMudancaService = (OcorrenciaMudancaService) ServiceLocator.getInstance().getService(OcorrenciaMudancaService.class, null);

        final Collection<OcorrenciaMudancaDTO> col = ocorrenciaMudancaService.findByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());

        JRDataSource dataSource = null;

        final HttpSession session = request.getSession();
        usuario = WebUtil.getUsuario(request);

        Map<String, Object> parametros = new HashMap<String, Object>();
        parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);

        parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioRequisicaoMudanca.relatorioRequisicaoMudanca"));
        parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
        parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
        parametros.put("NOME_USUARIO", usuario.getNomeUsuario());

        if (col.size() == 0) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
            document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
            return;
        }

        try {
            dataSource = new JRBeanCollectionDataSource(col);

            final Date dt = new Date();
            final String strCompl = "" + dt.getTime();
            final String caminhoJasper = CITCorporeUtil.CAMINHO_REAL_APP + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioRequisicaoMudanca.jasper";
            final String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "/tempFiles";
            final String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

            final JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);

            // Instancia o virtualizador
            final JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);

            // Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
            parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

            // Preenche o relatório e exibe numa GUI
            final JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);

            JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioRequisicaoMudanca" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");

            document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url="
                    + diretorioRelativoOS + "/RelatorioRequisicaoMudanca" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
        } catch (final OutOfMemoryError e) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
        }
        document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(final UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public void verificarParametroAnexos(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
        if (DISKFILEUPLOAD_REPOSITORYPATH == null) {
            DISKFILEUPLOAD_REPOSITORYPATH = "";
        }
        if (DISKFILEUPLOAD_REPOSITORYPATH.equals("")) {
            throw new LogicException(UtilI18N.internacionaliza(request, "citcorpore.comum.anexosUploadSemParametro"));
        }
        final File pasta = new File(DISKFILEUPLOAD_REPOSITORYPATH);
        if (!pasta.exists()) {
            throw new LogicException(UtilI18N.internacionaliza(request, "citcorpore.comum.pastaIndicadaNaoExiste"));
        }
    }

    private void carregaInformacoesComplementares(final DocumentHTML document, final HttpServletRequest request, final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final TemplateSolicitacaoServicoService templateService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(
                TemplateSolicitacaoServicoService.class, br.com.centralit.citcorpore.util.WebUtil.getUsuarioSistema(request));

        document.executeScript("exibirInformacoesComplementares('" + this.getRequisicaoMudancaService(request).getUrlInformacoesComplementares(requisicaoMudancaDTO) + "');");
        final TemplateSolicitacaoServicoDTO templateDto = templateService.recuperaTemplateRequisicaoMudanca(requisicaoMudancaDTO);

        if (templateDto != null) {
            if (templateDto.getAlturaDiv() != null) {
                document.executeScript("document.getElementById('divInformacoesComplementares').style.height = '" + templateDto.getAlturaDiv().intValue() + "px';");

            }

        }
        document.executeScript("escondeJanelaAguarde()");
    }

    public void criaTabelaLiberacao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final LiberacaoMudancaService liberacaoMudancaService = (LiberacaoMudancaService) ServiceLocator.getInstance().getService(LiberacaoMudancaService.class, null);
        final ArrayList<LiberacaoMudancaDTO> liberacaoMudanca = (ArrayList<LiberacaoMudancaDTO>) liberacaoMudancaService.findByIdRequisicaoMudanca(
                requisicaoMudancaDto.getIdLiberacao(), requisicaoMudancaDto.getIdRequisicaoMudanca());
        if (liberacaoMudanca != null) {
            HTMLTable table;
            table = document.getTableById("tblLiberacao");
            table.deleteAllRows();
            table.addRowsByCollection(liberacaoMudanca, new String[] {"idLiberacao", "titulo", "descricao", "status", "situacaoLiberacao"}, null, null,
                    new String[] {"gerarButtonDeleteVersao"}, null, null);
        }
    }

    public void restoreImpactoUrgenciaPorTipoMudanca(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

        TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();

        final TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);

        if (requisicaoMudancaDto.getIdTipoMudanca() != null) {
            tipoMudancaDto.setIdTipoMudanca(requisicaoMudancaDto.getIdTipoMudanca());
            tipoMudancaDto = (TipoMudancaDTO) tipoMudancaService.restore(tipoMudancaDto);
        }

        if (tipoMudancaDto != null) {
            requisicaoMudancaDto.setNivelImpacto(tipoMudancaDto.getImpacto());
            requisicaoMudancaDto.setNivelUrgencia(tipoMudancaDto.getUrgencia());

        }

        final HTMLForm form = document.getForm("form");
        form.setValues(requisicaoMudancaDto);
        document.executeScript("atualizaPrioridade()");
    }

    /**
     * @author geber.costa
     * @param document
     * @param request
     * @param response
     * @throws Exception
     *
     *             Método que pega do jsp o id da liberação , faz a validação , restaura e executa o método '' devolvendo o objeto com os valores preenchidos
     */
    public void inserirRequisicaoLiberacao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String idLib = request.getParameter("liberacao#idRequisicaoLiberacao");
        RequisicaoLiberacaoDTO requisicaoLiberacaoDto = new RequisicaoLiberacaoDTO();
        final Integer idLiberacao = new Integer(idLib);
        if (requisicaoLiberacaoDto != null) {
            requisicaoLiberacaoDto.setIdRequisicaoLiberacao(idLiberacao);

            final RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class,
                    WebUtil.getUsuarioSistema(request));
            requisicaoLiberacaoDto = (RequisicaoLiberacaoDTO) requisicaoLiberacaoService.restore(requisicaoLiberacaoDto);

            // caso contrário ele seta o valor vazio e executa a função javascript
            if (requisicaoLiberacaoDto.getSituacao().toUpperCase().toString().equalsIgnoreCase("E")) {

                requisicaoLiberacaoDto.setSituacaoLiberacao(UtilI18N.internacionaliza(request, "liberacao.emExecucao"));
            } else if (requisicaoLiberacaoDto.getSituacao().toUpperCase().toString().equalsIgnoreCase("R")) {

                requisicaoLiberacaoDto.setSituacaoLiberacao(UtilI18N.internacionaliza(request, "citcorpore.comum.resolvida"));

            } else if (requisicaoLiberacaoDto.getSituacao().toUpperCase().toString().equalsIgnoreCase("N")) {

                requisicaoLiberacaoDto.setSituacaoLiberacao(UtilI18N.internacionaliza(request, "requisicaoLiberacao.naoResolvida"));
            } else {
                requisicaoLiberacaoDto.setSituacaoLiberacao("");
            }

            document.executeScript("adicionaLiberacaoMudanca('" + requisicaoLiberacaoDto.getIdRequisicaoLiberacao() + "','" + requisicaoLiberacaoDto.getTitulo() + "','"
                    + requisicaoLiberacaoDto.getDescricao() + "','" + requisicaoLiberacaoDto.getStatus() + "')");
        }
    }

    public void limpar(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        // Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Criação de Upload para requisição mudança para evitar conflitos com outras telas do sistema que usão upload.
        request.getSession(true).setAttribute("colUploadRequisicaoMudancaGED", null);
        request.getSession(true).setAttribute("colUploadPlanoDeReversaoGED", null);
    }

    public void mostraHistoricoMudanca(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response,
            final RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
        final HistoricoMudancaService mudancaService = (HistoricoMudancaService) ServiceLocator.getInstance().getService(HistoricoMudancaService.class, null);
        final Collection<String> colbaselines = new ArrayList<>();

        final HTMLElement divPrincipal = document.getElementById("contentBaseline");
        final StringBuilder subDiv = new StringBuilder();
        subDiv.append("" + "<div class='formBody'> " + "	<table id='tblBaselines' class='tableLess table table-bordered table-striped'> 	" + "		<thead>" + "			<tr>" + "				<th>"
                + UtilI18N.internacionaliza(request, "liberacao.baseline") + "</th>	" + "				<th>" + UtilI18N.internacionaliza(request, "liberacao.versaoHistorico") + "</th>	"
                + "				<th>" + UtilI18N.internacionaliza(request, "liberacao.executorModificacao") + "</th>	" + "				<th>"
                + UtilI18N.internacionaliza(request, "colaborador.colaborador") + "</th>	" + "				<th>"
                + UtilI18N.internacionaliza(request, "liberacao.idRequisicaoMudancaVinculada") + "</th>	" + "				<th>"
                + UtilI18N.internacionaliza(request, "citcorpore.comum.historico") + "</th>	" + "			</tr>" + "		</thead>");
        List<HistoricoMudancaDTO> listHistoricoMudancas = new ArrayList<>();
        listHistoricoMudancas = mudancaService.listHistoricoMudancaByIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());

        if (listHistoricoMudancas != null) {
            int count = 0;
            boolean flag = false;
            document.executeScript("document.form.idHistoricoMudanca.value = " + requisicaoMudancaDTO.getIdRequisicaoMudanca());
            document.executeScript("countHistorico = 0");
            for (final HistoricoMudancaDTO historicoMudancaDTO : listHistoricoMudancas) {
                flag = historicoMudancaDTO.getBaseLine() != null && historicoMudancaDTO.getBaseLine().equals("SIM") ? true : false;
                String disabled = "";
                count++;
                final DecimalFormat df = new DecimalFormat("0.##");
                String versao = df.format(historicoMudancaDTO.getHistoricoVersao());
                versao = versao.replace(",", ".");
                document.executeScript("seqBaseline = " + count);
                if (flag) {
                    disabled = "disabled='disabled'";
                    colbaselines.add("idHistoricoMudanca" + count);
                }
                subDiv.append("<tbody>" + "	<tr>" + "		<td width='5%'>" + "<input type='checkbox' " + disabled + " id='idHistoricoMudanca" + count + "'"
                        + " name='idHistoricoMudanca" + count + "' value='0" + historicoMudancaDTO.getIdHistoricoMudanca().toString() + "'/></td>" + "		<td>" + versao + " - "
                        + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, historicoMudancaDTO.getDataHoraModificacao(), WebUtil.getLanguage(request)) + "</td>"
                        + "		<td width='15%'>" + (historicoMudancaDTO.getNomeExecutorModificacao() == null ? "" : historicoMudancaDTO.getNomeExecutorModificacao()) + "</td>"
                        + "		<td>" + (historicoMudancaDTO.getNomeProprietario() == null ? "" : historicoMudancaDTO.getNomeProprietario()) + "</td>" + "		<td>"
                        + (historicoMudancaDTO.getIdRequisicaoMudanca() == null ? "" : historicoMudancaDTO.getIdRequisicaoMudanca()) + "</td>" + "		<td>"
                        + "			<a href='javascript:;' class='even' id='even-" + historicoMudancaDTO.getIdHistoricoMudanca() + "'>"
                        + "		<img src='../../template_new/images/icons/small/grey/documents.png' alt='" + UtilI18N.internacionaliza(request, "itemConfiguracaoTree.historico")
                        + "' " + "title='" + UtilI18N.internacionaliza(request, "itemConfiguracaoTree.historico") + "' /></a>");
                if (flag) {
                    subDiv.append("		<a href='javascript:;' onclick='restaurarHistorico(\"" + historicoMudancaDTO.getIdHistoricoMudanca() + "\")'>"
                            + "			<img src='../../template_new/images/icons/small/grey/refresh_3.png' alt='" + UtilI18N.internacionaliza(request, "itemConfiguracaoTree.restaurar")
                            + "' " + "title='" + UtilI18N.internacionaliza(request, "itemConfiguracaoTree.restaurar") + "' /></a>");
                }

                subDiv.append("		</td>" + "	</tr>" + "	<tr class='sel' id='sel-" + historicoMudancaDTO.getIdHistoricoMudanca() + "'>" + "		<td colspan='9'>"
                        + "			<div class='sel-s'>" + "				<table class='table table-bordered table-striped' width='100%'><thead><tr><th>"
                        + UtilI18N.internacionaliza(request, "citcorpore.comum.grupoExecutor") + "</th><th>" + UtilI18N.internacionaliza(request, "itemConfiguracaoTree.impacto")
                        + "</th><th>" + UtilI18N.internacionaliza(request, "itemConfiguracaoTree.urgencia") + "</th>" + "<th>"
                        + UtilI18N.internacionaliza(request, "requisicaoMudanca.horaAgendamentoFinal") + " "
                        + UtilI18N.internacionaliza(request, "citcorpore.ui.janela.popup.titulo.Agendamento") + "</th>" + "<th>"
                        + UtilI18N.internacionaliza(request, "pesquisasolicitacao.fase") + "</th></tr></thead><tbody>");
                subDiv.append("<tr><td>"
                        + (historicoMudancaDTO.getStatus() == null ? "" : historicoMudancaDTO.getStatus())
                        + "</td>"
                        + "<td>"
                        + (historicoMudancaDTO.getNivelImpacto() == "B" ? UtilI18N.internacionaliza(request, "citcorpore.comum.baixa")
                                : historicoMudancaDTO.getNivelImpacto() == "M" ? UtilI18N.internacionaliza(request, "citcorpore.comum.media") : UtilI18N.internacionaliza(request,
                                        "citcorpore.comum.alta"))
                        + "</td>"
                        + "<td>"
                        + (historicoMudancaDTO.getNivelUrgencia() == "B" ? UtilI18N.internacionaliza(request, "citcorpore.comum.baixa")
                                : historicoMudancaDTO.getNivelUrgencia() == "M" ? UtilI18N.internacionaliza(request, "citcorpore.comum.media") : UtilI18N.internacionaliza(request,
                                        "citcorpore.comum.alta")) + "</td>" + "<td>"
                        + UtilDatas.convertDateToString(TipoDate.TIMESTAMP_WITH_SECONDS, historicoMudancaDTO.getDataHoraTerminoAgendada(), WebUtil.getLanguage(request)) + "</td>"
                        + "<td>" + (historicoMudancaDTO.getFase() == null ? "" : historicoMudancaDTO.getFase()) + "</td></tr>");
                subDiv.append("</tbody></table>" + "	</div>" + "		</td>" + "	</tr>" + "</tbody>");
            }
        }
        subDiv.append("	</table>" + "</div>");
        divPrincipal.setInnerHTML(subDiv.toString());
        document.executeScript("marcarCheckbox(" + colbaselines + ")");
        for (final String str : colbaselines) {
            document.getCheckboxById(str).setChecked(true);
        }
    }

    public void preencherComboCategoriaSolucao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final CategoriaSolucaoService categoriaSolucaoService = (CategoriaSolucaoService) ServiceLocator.getInstance().getService(CategoriaSolucaoService.class, null);
        final Collection colCategSolucao = categoriaSolucaoService.listHierarquia();
        final HTMLSelect idCategoriaSolucao = document.getSelectById("idCategoriaSolucao");
        idCategoriaSolucao.removeAllOptions();
        idCategoriaSolucao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        if (colCategSolucao != null) {
            idCategoriaSolucao.addOptions(colCategSolucao, "idCategoriaSolucao", "descricaoCategoriaNivel", null);
        }
    }

    public void restaurarBaseline(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final UsuarioDTO usrDto = br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
        final HistoricoMudancaDao historicoMudancaDao = new HistoricoMudancaDao();
        if (usrDto == null) {
            return;
        }
        final RequisicaoMudancaDTO requisicaoMudancaDTO = (RequisicaoMudancaDTO) document.getBean();
        final RequisicaoMudancaDTO requisicaoMudancaDTOAux = new RequisicaoMudancaDTO();
        HistoricoMudancaDTO historicoMudancaDTO = new HistoricoMudancaDTO();
        historicoMudancaDTO.setIdHistoricoMudanca(Integer.parseInt(request.getParameter("idHistoricoMudanca")));

        final RequisicaoMudancaService requisicaoLiberacaoService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
        historicoMudancaDTO = (HistoricoMudancaDTO) historicoMudancaDao.restore(historicoMudancaDTO);

        // Realizando a Reflexão de Item de Configuração
        Reflexao.copyPropertyValues(historicoMudancaDTO, requisicaoMudancaDTOAux);

        List<RequisicaoMudancaItemConfiguracaoDTO> colItemconfiguracao = new ArrayList<RequisicaoMudancaItemConfiguracaoDTO>();
        final RequisicaoMudancaItemConfiguracaoDao itemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();
        colItemconfiguracao = (List<RequisicaoMudancaItemConfiguracaoDTO>) itemConfiguracaoDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
        requisicaoMudancaDTOAux.setListRequisicaoMudancaItemConfiguracaoDTO(colItemconfiguracao);

        List<ProblemaMudancaDTO> colProblemas = new ArrayList<ProblemaMudancaDTO>();
        final ProblemaMudancaDAO problemaMudancaDAO = new ProblemaMudancaDAO();
        colProblemas = (List<ProblemaMudancaDTO>) problemaMudancaDAO.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
        requisicaoMudancaDTOAux.setListProblemaMudancaDTO(colProblemas);

        List<GrupoRequisicaoMudancaDTO> colGrupoRequisicaoMudanca = new ArrayList<GrupoRequisicaoMudancaDTO>();
        final GrupoRequisicaoMudancaDao grupoRequisicaoMudancaDAO = new GrupoRequisicaoMudancaDao();
        colGrupoRequisicaoMudanca = (List<GrupoRequisicaoMudancaDTO>) grupoRequisicaoMudancaDAO.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
        requisicaoMudancaDTOAux.setListGrupoRequisicaoMudancaDTO(colGrupoRequisicaoMudanca);

        List<RequisicaoMudancaRiscoDTO> colMudancaRiscoDTOs = new ArrayList<RequisicaoMudancaRiscoDTO>();
        final RequisicaoMudancaRiscoDao requisicaoMudancaRiscoDao = new RequisicaoMudancaRiscoDao();
        colMudancaRiscoDTOs = (List<RequisicaoMudancaRiscoDTO>) requisicaoMudancaRiscoDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
        requisicaoMudancaDTOAux.setListRequisicaoMudancaRiscoDTO(colMudancaRiscoDTOs);

        List<AprovacaoMudancaDTO> aprovacaoMudancaDTOs = new ArrayList<AprovacaoMudancaDTO>();
        final AprovacaoMudancaDao aprovacaoMudancaDao = new AprovacaoMudancaDao();
        aprovacaoMudancaDTOs = (List<AprovacaoMudancaDTO>) aprovacaoMudancaDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
        requisicaoMudancaDTOAux.setListAprovacaoMudancaDTO(aprovacaoMudancaDTOs);

        List<LiberacaoMudancaDTO> liberacaoMudancaDTOs = new ArrayList<LiberacaoMudancaDTO>();
        final LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
        liberacaoMudancaDTOs = (List<LiberacaoMudancaDTO>) liberacaoMudancaDao.listByIdHistoricoMudanca2(historicoMudancaDTO.getIdHistoricoMudanca());
        requisicaoMudancaDTOAux.setListLiberacaoMudancaDTO(liberacaoMudancaDTOs);

        List<RequisicaoMudancaServicoDTO> requisicaoMudancaServicoDTOs = new ArrayList<RequisicaoMudancaServicoDTO>();
        final RequisicaoMudancaServicoDao requisicaoMudancaServicoDao = new RequisicaoMudancaServicoDao();
        requisicaoMudancaServicoDTOs = (List<RequisicaoMudancaServicoDTO>) requisicaoMudancaServicoDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
        requisicaoMudancaDTOAux.setListRequisicaoMudancaServicoDTO(requisicaoMudancaServicoDTOs);

        List<SolicitacaoServicoMudancaDTO> solicitacaoServicoMudancaDTOs = new ArrayList<SolicitacaoServicoMudancaDTO>();
        final List<SolicitacaoServicoDTO> solicitacaoServicos = new ArrayList<SolicitacaoServicoDTO>();
        SolicitacaoServicoDTO solicitacaoServicoDTO = new SolicitacaoServicoDTO();
        final SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
        solicitacaoServicoMudancaDTOs = (List<SolicitacaoServicoMudancaDTO>) solicitacaoServicoMudancaDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
        for (final SolicitacaoServicoMudancaDTO solicitacaoServicoMudancaDTO : solicitacaoServicoMudancaDTOs) {
            solicitacaoServicoDTO.setIdRequisicaoMudanca(solicitacaoServicoMudancaDTO.getIdRequisicaoMudanca());
            solicitacaoServicoDTO.setIdSolicitacaoServico(solicitacaoServicoMudancaDTO.getIdSolicitacaoServico());
            solicitacaoServicos.add(solicitacaoServicoDTO);
            solicitacaoServicoDTO = new SolicitacaoServicoDTO();
        }
        requisicaoMudancaDTOAux.setListIdSolicitacaoServico(solicitacaoServicos);

        List<RequisicaoMudancaResponsavelDTO> respMudancaDTOs = new ArrayList<RequisicaoMudancaResponsavelDTO>();
        final RequisicaoMudancaResponsavelDao respMudancaDao = new RequisicaoMudancaResponsavelDao();
        respMudancaDTOs = (List<RequisicaoMudancaResponsavelDTO>) respMudancaDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
        requisicaoMudancaDTOAux.setColResponsaveis(respMudancaDTOs);

        // esse bloco preenche os anexos do historico
        Collection<UploadDTO> listuploadDTO = new ArrayList<UploadDTO>();
        final ControleGEDDao controleGEDDao = new ControleGEDDao();
        listuploadDTO = controleGEDDao.listByIdTabelaAndIdHistorico(ControleGEDDTO.TABELA_REQUISICAOLIBERACAO, historicoMudancaDTO.getIdHistoricoMudanca());
        requisicaoMudancaDTOAux.setColArquivosUpload(listuploadDTO);

        final UsuarioDTO usr = new UsuarioDTO();
        usr.setIdUsuario(historicoMudancaDTO.getIdProprietario());
        requisicaoMudancaDTOAux.setUsuarioDto(usr);
        requisicaoLiberacaoService.update(requisicaoMudancaDTOAux, request);

        document.setBean(requisicaoMudancaDTOAux);

        document.executeScript("uploadAnexos.refresh()");
        this.load(document, request, response);

        String comando = "mostraMensagemRestaurarBaseline('" + UtilI18N.internacionaliza(request, "MSG15") + ".<br>"
                + UtilI18N.internacionaliza(request, "requisicaoLiberacao.requisicaoLiberacao") + " <b><u>" + requisicaoMudancaDTO.getIdRequisicaoMudanca() + "</u></b> "
                + UtilI18N.internacionaliza(request, "citcorpore.comum.restaurada") + ".<br><br>" + "Versão: "
                + UtilStrings.nullToVazio(requisicaoMudancaDTOAux.getIdRequisicaoMudanca().toString()) + "<br>";
        comando = comando + "')";

        document.executeScript(comando);
    }

    public void verificarItensRelacionados(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final ArrayList<SolicitacaoServicoDTO> listIdSolicitacaoServico = (ArrayList<SolicitacaoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                SolicitacaoServicoDTO.class, "solicitacaoServicoSerializado", request);
        final ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listRequisicaoMudancaItemConfiguracaoDTO = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) br.com.citframework.util.WebUtil
                .deserializeCollectionFromRequest(RequisicaoMudancaItemConfiguracaoDTO.class, "itensConfiguracaoRelacionadosSerializado", request);
        final ArrayList<ProblemaMudancaDTO> listProblemaMudancaDTO = (ArrayList<ProblemaMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                ProblemaMudancaDTO.class, "problemaSerializado", request);

        boolean existeItensRelaiconados = false;

        if (listIdSolicitacaoServico != null && listIdSolicitacaoServico.size() > 0) {
            existeItensRelaiconados = true;
        } else if (listRequisicaoMudancaItemConfiguracaoDTO != null && listRequisicaoMudancaItemConfiguracaoDTO.size() > 0) {
            existeItensRelaiconados = true;
        } else if (listProblemaMudancaDTO != null && listProblemaMudancaDTO.size() > 0) {
            existeItensRelaiconados = true;
        }

        if (existeItensRelaiconados) {
            document.executeScript("verificarItensRelacionados(false)");
        } else {
            document.executeScript("gravar()");
        }
    }

    public void adicionaTabelaLOOKUP_PROBLEMA(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final ProblemaDAO problemaDao = new ProblemaDAO();
        final RequisicaoMudancaDTO requisicaoMudancaDTO = (RequisicaoMudancaDTO) document.getBean();

        requisicaoMudancaDTO.setColAllLOOKUP_PROBLEMA(requisicaoMudancaDTO.getColAllLOOKUP_PROBLEMA());
        requisicaoMudancaDTO.setColProblemaCheckado(requisicaoMudancaDTO.getColAllLOOKUP_PROBLEMA());
        // Declarando arrays de strings
        final ArrayList<String> listaValoresGrid = new ArrayList<String>();
        final ArrayList<String> listaValoresCheckados = new ArrayList<String>();

        final ArrayList<ProblemaMudancaDTO> listProblemaMudancaDTO = (ArrayList<ProblemaMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
                ProblemaMudancaDTO.class, "problemaSerializado", request);

        if (listProblemaMudancaDTO != null) {
            for (final ProblemaMudancaDTO problemaMudanca : listProblemaMudancaDTO) {
                listaValoresGrid.add(problemaMudanca.getIdProblema().toString());
            }
        }
        for (final String problemaCheckado : requisicaoMudancaDTO.getColProblemaCheckado()) {
            listaValoresCheckados.add(problemaCheckado);
        }

        // Neste trecho do codigo adiciona a uma Collection os valores que NAO estão se repetindo entre os dois Arrays
        final Set<String> valoresUnicosGrid = new HashSet<String>();
        valoresUnicosGrid.addAll(listaValoresGrid);
        valoresUnicosGrid.addAll(listaValoresCheckados);

        ProblemaDTO problema = new ProblemaDTO();
        ProblemaDTO problemaDTO = new ProblemaDTO();
        document.executeScript("tabelaProblema.limpaLista();");
        for (final String idProblemaCheckado : valoresUnicosGrid) {
            if (idProblemaCheckado != null && !idProblemaCheckado.equals("")) {
                final Integer idProblemasCheckados = Integer.parseInt(idProblemaCheckado);
                problemaDTO = new ProblemaDTO();
                problemaDTO.setIdProblema(idProblemasCheckados);
                problema = (ProblemaDTO) problemaDao.restore(problemaDTO);
                document.executeScript("addLinhaTabelaProblema('" + problema.getIdProblema() + "', '" + problema.getIdProblema() + "-" + problema.getTitulo() + "-"
                        + problema.getStatus() + "',false)");
            } else {
                continue;
            }
        }
    }

}
