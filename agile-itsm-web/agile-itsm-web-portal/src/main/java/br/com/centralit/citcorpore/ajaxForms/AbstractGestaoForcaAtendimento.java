package br.com.centralit.citcorpore.ajaxForms;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.AbstractGestaoForcaAtendimentoDTO;
import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CidadesService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

import com.google.gson.Gson;

/**
 * {@link AjaxFormAction} contendo operações comuns entre os controllers das telas da {@code Gestão da Força de Atendimento}
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 *
 */
public abstract class AbstractGestaoForcaAtendimento<E extends AbstractGestaoForcaAtendimentoDTO, R> extends AjaxFormAction {

    private static final Logger LOGGER = Logger.getLogger(AbstractGestaoForcaAtendimento.class.getName());

    protected static final Gson GSON = new Gson();

    protected static final String ID_CIDADE = "idCidade";
    protected static final String ID_CONTRATO = "idContrato";
    protected static final String ID_GRUPO = "idGrupo";
    protected static final String ID_UF = "idUF";

    protected static final String FUNC_WAIT_WINDOW_HIDE = "waitWindow.hide();";
    protected static final String FUNC_HANDLE_SEARCH_RESULT = "$().handleSearchResult(%s);";

    protected abstract List<R> getListResultSearch(final DocumentHTML document, final HttpServletRequest request) throws Exception;

    public void performSearch(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) {
        try {
            final List<R> result = this.getListResultSearch(document, request);
            document.executeScript(String.format(FUNC_HANDLE_SEARCH_RESULT, GSON.toJson(result)));
        } catch (final Exception e) {
            document.alert(UtilI18N.internacionaliza(request, "MSE02"));
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
        document.executeScript(FUNC_WAIT_WINDOW_HIDE);
    }

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HTMLForm form = document.getForm("form");
        form.clear();
    }

    public void loadComboUFs(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final HTMLSelect combo = document.getSelectById(ID_UF);
        this.inicializaCombo(combo, request);

        final Collection<UfDTO> list = this.getUFService().list();
        for (final UfDTO uf : list) {
            final Integer idUf = uf.getIdUf();
            if (idUf.intValue() != 0) {
                combo.addOption(uf.getIdUf().toString(), uf.getNomeUf());
            }
        }
        document.executeScript(FUNC_WAIT_WINDOW_HIDE);
    }

    public void loadComboCidades(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final E historico = (E) document.getBean();
        final Integer idUF = historico.getIdUF();

        final List<CidadesDTO> listCidade = (List) this.getCidadesService().listByIdUf(idUF);

        final HTMLSelect comboCidade = document.getSelectById(ID_CIDADE);
        this.inicializaCombo(comboCidade, request);
        if (listCidade != null) {
            for (final CidadesDTO cidade : listCidade) {
                comboCidade.addOption(cidade.getIdCidade().toString(), cidade.getNomeCidade());
            }
        }
        document.executeScript(FUNC_WAIT_WINDOW_HIDE);
    }

    public void loadComboContratos(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");

        if (StringUtils.isBlank(COLABORADORES_VINC_CONTRATOS)) {
            COLABORADORES_VINC_CONTRATOS = "N";
        }

        final HTMLSelect contratos = document.getSelectById(ID_CONTRATO);
        this.inicializaCombo(contratos, request);

        final UsuarioDTO usuario = WebUtil.getUsuario(request);
        Collection<ContratoDTO> listContratoAtivo = null;

        if (COLABORADORES_VINC_CONTRATOS != null && COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
            listContratoAtivo = this.getContratoService().findAtivosByIdEmpregado(usuario.getIdEmpregado());
        } else {
            listContratoAtivo = this.getContratoService().listAtivos();
        }

        if (listContratoAtivo != null && !listContratoAtivo.isEmpty()) {
            for (final ContratoDTO contrato : listContratoAtivo) {
                contrato.setNome(this.tratarNomeContrato(contrato, request, document.getLanguage()));
            }
            contratos.addOptions(listContratoAtivo, ID_CONTRATO, "nome", null);
        }
        document.executeScript(FUNC_WAIT_WINDOW_HIDE);
    }

    public void loadComboGrupos(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final E posicionamentoAtendente = (E) document.getBean();
        final HTMLSelect gruposExecutores = document.getSelectById(ID_GRUPO);
        gruposExecutores.removeAllOptions();

        gruposExecutores.addOption("0", this.makeSelectLabel(request));

        final Collection<GrupoDTO> grupos = this.getGrupoService().listGrupoAtivosByIdContrato(posicionamentoAtendente.getIdContrato());
        gruposExecutores.addOptions(grupos, ID_GRUPO, "nome", null);
        document.executeScript(FUNC_WAIT_WINDOW_HIDE);
    }

    protected void inicializaCombo(final HTMLSelect combo, final HttpServletRequest request) {
        combo.removeAllOptions();
        combo.addOption("0", this.makeSelectLabel(request));
    }

    protected void inicializarCombosOnLoad(final List<HTMLSelect> combos, final HttpServletRequest request) {
        for (final HTMLSelect combo : combos) {
            this.inicializaCombo(combo, request);
            combo.setSelectedIndex(0);
        }
    }

    private String tratarNomeContrato(final ContratoDTO contrato, final HttpServletRequest request, final String language) throws Exception {
        final ClienteDTO cliente = new ClienteDTO();
        cliente.setIdCliente(contrato.getIdCliente());

        final ClienteDTO clienteOnDB = (ClienteDTO) this.getClienteService().restore(cliente);

        final FornecedorDTO fornecedor = new FornecedorDTO();
        fornecedor.setIdFornecedor(contrato.getIdFornecedor());

        final FornecedorDTO fornecedorOnDb = (FornecedorDTO) this.getFornecedorService().restore(fornecedor);

        final Boolean hasNomeCliente = clienteOnDB != null && StringUtils.isNotBlank(clienteOnDB.getNomeRazaoSocial());
        final Boolean hasNomeFornecedor = fornecedorOnDb != null && StringUtils.isNotBlank(fornecedorOnDb.getRazaoSocial());

        final StringBuilder nomeContrato = new StringBuilder();
        nomeContrato.append(contrato.getNumero());
        nomeContrato.append(this.makeOfLabel(language));
        nomeContrato.append(UtilDatas.convertDateToString(TipoDate.DATE_DEFAULT, contrato.getDataContrato(), language));

        if (hasNomeCliente && hasNomeFornecedor) {
            nomeContrato.append(" (");
            nomeContrato.append(clienteOnDB.getNomeRazaoSocial());
            nomeContrato.append(" - ");
            nomeContrato.append(fornecedorOnDb.getRazaoSocial());
            nomeContrato.append(")");
        } else if (hasNomeCliente && !hasNomeFornecedor) {
            nomeContrato.append(" (");
            nomeContrato.append(clienteOnDB.getNomeRazaoSocial());
            nomeContrato.append(")");
        } else if (!hasNomeCliente && hasNomeFornecedor) {
            nomeContrato.append(" (");
            nomeContrato.append(fornecedorOnDb.getRazaoSocial());
            nomeContrato.append(")");
        }

        return nomeContrato.toString();
    }

    private String selectLabel;

    private String makeSelectLabel(final HttpServletRequest request) {
        if (StringUtils.isBlank(selectLabel)) {
            final StringBuilder select = new StringBuilder();
            select.append("--- ");
            select.append(UtilI18N.internacionaliza(request, "citcorpore.comum.selecioneItens"));
            select.append(" ---");
            selectLabel = select.toString();
        }
        return selectLabel;
    }

    private String ofLabel;

    private String makeOfLabel(final String language) {
        if (StringUtils.isBlank(ofLabel)) {
            ofLabel = " " + UtilI18N.internacionaliza(language, "citcorpore.comum.of") + " ";
        }
        return ofLabel;
    }

    protected E normalizeDates(final E bean) {
        final E normalized = bean;

        Date startDate = bean.getDataInicio();
        Date finishDate = bean.getDataFim();

        final Integer maxDayInterval = ParametroUtil.getValorParametro(ParametroSistema.PERIODO_MAXIMO_DIAS_LISTAGEM, "30");
        if (startDate == null && finishDate == null) {
            final Date today = new Date();
            startDate = UtilDatas.subtractDaysFromDate(this.adjustDateToInitOfDay(today), maxDayInterval);
            finishDate = UtilDatas.toTimestampSQL(this.adjustDateToFinalOfDay(today));
        } else if (startDate == null && finishDate != null) {
            startDate = UtilDatas.subtractDaysFromDate(this.adjustDateToInitOfDay(finishDate), maxDayInterval);
            finishDate = UtilDatas.toTimestampSQL(this.adjustDateToFinalOfDay(finishDate));
        } else if (startDate != null && finishDate == null) {
            startDate = UtilDatas.toTimestampSQL(this.adjustDateToInitOfDay(startDate));
            finishDate = UtilDatas.addDaysOnDate(this.adjustDateToFinalOfDay(startDate), maxDayInterval);
        }

        normalized.setDataInicio(UtilDatas.toDateSQL(startDate));
        normalized.setDataFim(UtilDatas.toDateSQL(finishDate));
        normalized.setTimestampInicio(UtilDatas.toTimestampSQL(this.adjustDateToInitOfDay(startDate)));
        normalized.setTimestampFim(UtilDatas.toTimestampSQL(this.adjustDateToFinalOfDay(finishDate)));

        return normalized;
    }

    private Date adjustDateToInitOfDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    private Date adjustDateToFinalOfDay(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

    private CidadesService cidadesService;
    private ClienteService clienteService;
    private ContratoService contratoService;
    private FornecedorService fornecedorService;
    private GrupoService grupoService;
    private UfService ufService;

    private CidadesService getCidadesService() throws Exception {
        if (cidadesService == null) {
            cidadesService = (CidadesService) ServiceLocator.getInstance().getService(CidadesService.class, null);
        }
        return cidadesService;
    }

    private ClienteService getClienteService() throws Exception {
        if (clienteService == null) {
            clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
        }
        return clienteService;
    }

    private ContratoService getContratoService() throws Exception {
        if (contratoService == null) {
            contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
        }
        return contratoService;
    }

    private FornecedorService getFornecedorService() throws Exception {
        if (fornecedorService == null) {
            fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
        }
        return fornecedorService;
    }

    private GrupoService getGrupoService() throws Exception {
        if (grupoService == null) {
            grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
        }
        return grupoService;
    }

    private UfService getUFService() throws Exception {
        if (ufService == null) {
            ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);
        }
        return ufService;
    }

}
