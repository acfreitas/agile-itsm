package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.negocio.UfService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

/**
 * @author CentralIT
 *
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class Empregado extends AjaxFormAction {

    private EmpregadoDTO empregadoBean;

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        setEmpregadoBean((EmpregadoDTO) document.getBean());
        final HTMLSelect comboUfOrgaoExpedidor = document.getSelectById("idUFOrgExpedidor");
        final HTMLSelect comboCtpsIdUf = document.getSelectById("ctpsIdUf");
        final HTMLSelect comboIdSituacaoFuncional = document.getSelectById("idSituacaoFuncional");
        final HTMLSelect comboTipo = document.getSelectById("tipo");
        final HTMLSelect comboEstadoCivil = document.getSelectById("estadoCivil");

        comboTipo.removeAllOptions();
        comboTipo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        comboTipo.addOption("C", UtilI18N.internacionaliza(request, "colaborador.contratoEmpresaPJ"));
        comboTipo.addOption("E", UtilI18N.internacionaliza(request, "colaborador.empregadoCLT"));
        comboTipo.addOption("T", UtilI18N.internacionaliza(request, "colaborador.estagio"));
        comboTipo.addOption("F", UtilI18N.internacionaliza(request, "colaborador.freeLancer"));
        comboTipo.addOption("N", UtilI18N.internacionaliza(request, "colaborador.naoFuncionario"));
        comboTipo.addOption("O", UtilI18N.internacionaliza(request, "colaborador.outros"));
        comboTipo.addOption("X", UtilI18N.internacionaliza(request, "colaborador.socio"));
        comboTipo.addOption("S", UtilI18N.internacionaliza(request, "colaborador.solicitante"));

        final UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

        final Collection colUfs = ufService.list();

        comboUfOrgaoExpedidor.removeAllOptions();
        comboUfOrgaoExpedidor.addOption("", "--");
        comboUfOrgaoExpedidor.addOptions(colUfs, "idUf", "siglaUf", null);
        comboCtpsIdUf.removeAllOptions();
        comboCtpsIdUf.addOption("", "--");
        comboCtpsIdUf.addOptions(colUfs, "idUf", "siglaUf", null);

        comboIdSituacaoFuncional.removeAllOptions();
        comboIdSituacaoFuncional.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        comboIdSituacaoFuncional.addOption("1", UtilI18N.internacionaliza(request, "citcorpore.comum.ativo"));
        comboIdSituacaoFuncional.addOption("2", UtilI18N.internacionaliza(request, "citcorpore.comum.inativo"));

        comboEstadoCivil.removeAllOptions();
        comboEstadoCivil.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
        comboEstadoCivil.addOption("2", UtilI18N.internacionaliza(request, "colaborador.casado"));
        comboEstadoCivil.addOption("3", UtilI18N.internacionaliza(request, "colaborador.divorciado"));
        comboEstadoCivil.addOption("5", UtilI18N.internacionaliza(request, "colaborador.separadoJudicialmente"));
        comboEstadoCivil.addOption("1", UtilI18N.internacionaliza(request, "colaborador.solteiro"));
        comboEstadoCivil.addOption("4", UtilI18N.internacionaliza(request, "colaborador.viuvo"));
        final String validarComboUnidade = ParametroUtil.getValorParametroCitSmartHashMap(
                Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");

        if (validarComboUnidade.trim().equalsIgnoreCase("S")) {
            if (empregadoBean.getIdContrato() != null) {
                carregaUnidade(document, request, response);
            } else {
                preencherComboUnidade(document, request, response);
            }
        } else {
            preencherComboUnidade(document, request, response);
        }
        preencherComboCargos(document, request, response);
        preencherComboGrupos(document, request, response, getEmpregadoBean().getIdContrato());
        document.focusInFirstActivateField(document.getForm("form"));
    }

    /**
     * Inclui Novo Empregado.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        setEmpregadoBean((EmpregadoDTO) document.getBean());

        if (getEmpregadoService().verificarEmpregadosAtivos(getEmpregadoBean())) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
            return;
        }

        if (getEmpregadoBean().getIdEmpregado() == null || getEmpregadoBean().getIdEmpregado().intValue() == 0) {
            getEmpregadoBean().setNomeProcura(getEmpregadoBean().getNome().trim());
            getEmpregadoBean().setNome(getEmpregadoBean().getNome().trim());
            getEmpregadoBean().setEmail(getEmpregadoBean().getEmail().trim());
            getEmpregadoBean().setDataCadastro(UtilDatas.getDataAtual());
            if (getEmpregadoBean().getValorSalario() != null) {
                getEmpregadoService().calcularCustos(getEmpregadoBean());
            }

            final EmpregadoDTO empregadoDto = getEmpregadoService().create(getEmpregadoBean());

            if (getEmpregadoBean().getIdGrupo() != null) {
                final GrupoEmpregadoDTO grupoEmpregadoDto = new GrupoEmpregadoDTO();

                grupoEmpregadoDto.setIdEmpregado(empregadoDto.getIdEmpregado());
                grupoEmpregadoDto.setIdGrupo(getEmpregadoBean().getIdGrupo());
                if (getEmpregadoBean().getCpf() != null && !getEmpregadoBean().getCpf().equalsIgnoreCase("")) {
                    getEmpregadoBean().setCpf(getEmpregadoBean().getCpf().replace("-", "").replace(".", ""));
                }
                getEmpregadoBean().setCpf(getEmpregadoBean().getCpf().replace("-", "").replace(".", ""));
                getGrupoEmpregadoService().create(grupoEmpregadoDto);

            }
            final HTMLForm form = document.getForm("form");
            form.clear();
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        } else {
            getEmpregadoBean().setNomeProcura(getEmpregadoBean().getNome());
            getEmpregadoBean().setEmail(getEmpregadoBean().getEmail().trim());
            if (getEmpregadoBean().getValorSalario() != null) {
                getEmpregadoService().calcularCustos(getEmpregadoBean());
            }
            if (getEmpregadoBean().getCpf() != null && !getEmpregadoBean().getCpf().equalsIgnoreCase("")) {
                getEmpregadoBean().setCpf(getEmpregadoBean().getCpf().replace("-", "").replace(".", ""));
            }
            // this.getEmpregadoBean().getNome().trim();
            getEmpregadoService().update(getEmpregadoBean());
            final HTMLForm form = document.getForm("form");
            form.clear();
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        }

        if (getEmpregadoBean().getIframe() != null && getEmpregadoBean().getIframe().equalsIgnoreCase("true")) {
            document.executeScript("parent.fecharAddSolicitante()");
        } else {
            final HTMLForm form = document.getForm("form");
            form.clear();
        }
    }

    /**
     * Exclui Empregado atribuindo sua data fim em Empregado.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Integer idEmpregado = 0;
        setEmpregadoBean((EmpregadoDTO) document.getBean());

        final GrupoEmpregadoDTO grupoEmpregadoDTO = new GrupoEmpregadoDTO();
        final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(
                UsuarioService.class, null);
        final ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance()
                .getService(ParametroCorporeService.class, null);
        final ParametroCorporeDTO parametroCITSmart = parametroService
                .getParamentroAtivo(ParametroSistema.METODO_AUTENTICACAO_Pasta.id());

        if (getEmpregadoBean().getIdEmpregado() != null) {
            if (parametroCITSmart != null && parametroCITSmart.getValor().trim().equalsIgnoreCase("2")) {
                UsuarioDTO usuarioDto = new UsuarioDTO();
                usuarioDto = usuarioService.restoreByIdEmpregadosDeUsuarios(getEmpregadoBean().getIdEmpregado());

                if (usuarioDto != null) {
                    if (usuarioDto.getStatus() != null && usuarioDto.getStatus().equalsIgnoreCase("A")) {
                        document.alert(UtilI18N.internacionaliza(request,
                                "colaborador.mensagensDeAutenticacaoLDAPColaboradorAtivo"));
                        return;
                    }
                }
            }

            if (getEmpregadoBean().getIdEmpregado().intValue() > 0) {
                grupoEmpregadoDTO.setIdEmpregado(getEmpregadoBean().getIdEmpregado());
                idEmpregado = grupoEmpregadoDTO.getIdEmpregado();
                final Collection<GrupoEmpregadoDTO> empregadosDeGrupo = getGrupoEmpregadoService().findByIdEmpregado(
                        idEmpregado);

                if (empregadosDeGrupo != null) {
                    for (final GrupoEmpregadoDTO grupoEmpregado : empregadosDeGrupo) {
                        getGrupoEmpregadoService().deleteByIdGrupoAndEmpregado(grupoEmpregado.getIdGrupo(),
                                grupoEmpregado.getIdEmpregado());
                    }
                }
                /* Usado assim pois nao funciona o i18n no serviceEJB sem passar o usuario da sessao. */
                getEmpregadoService().deleteEmpregado(getEmpregadoBean());
            }

            final HTMLForm form = document.getForm("form");
            form.clear();
            document.executeScript("limpar_LOOKUP_EMPREGADO()");
            document.alert(UtilI18N.internacionaliza(request, "MSG07"));
        } else {
            document.alert(UtilI18N.internacionaliza(request, "colaborador.codigoColaboradorInvalido"));
        }

    }

    /**
     * Recupera empregado.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        setEmpregadoBean((EmpregadoDTO) document.getBean());
        setEmpregadoBean(getEmpregadoService().restore(getEmpregadoBean()));
        final HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(getEmpregadoBean());
    }

    /**
     * Iniciliza combo.
     *
     * @param componenteCombo
     * @author thays.araujo
     */
    private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
    }

    /**
     * Preenche a combo Unidade.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     * @author thays.araujo
     */
    public void preencherComboUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(
                UnidadeService.class, null);
        final HTMLSelect comboUnidade = document.getSelectById("idUnidade");
        final ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();

        inicializarCombo(comboUnidade, request);
        if (unidades != null) {
            for (final UnidadeDTO unidade : unidades) {
                if (unidade.getDataFim() == null) {
                    comboUnidade.addOption(unidade.getIdUnidade().toString(),
                            StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
                }
            }
        }

    }

    public void carregaUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(
                UnidadeService.class, null);
        final EmpregadoDTO empregadoDTO = empregadoBean;
        final HTMLSelect comboUnidade = document.getSelectById("idUnidade");
        final Integer idContrato = empregadoDTO.getIdContrato();
        final ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);

        inicializarCombo(comboUnidade, request);
        if (unidades != null) {
            for (final UnidadeDTO unidade : unidades) {
                if (unidade.getDataFim() == null) {
                    comboUnidade.addOption(unidade.getIdUnidade().toString(),
                            StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
                }
            }
        }

    }

    /**
     * Preenche combo de Grupos.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        final GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class,
                null);
        final HTMLSelect comboGrupo = document.getSelectById("idGrupo");
        final ArrayList<GrupoDTO> grupos = (ArrayList) grupoService.list();
        inicializarCombo(comboGrupo, request);
        for (final GrupoDTO grupo : grupos) {
            if (grupo.getDataFim() == null) {
                comboGrupo.addOption(grupo.getIdGrupo().toString(), grupo.getNome());
            }
        }
    }

    /**
     * Preenche combo de Grupos.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void preencherComboCargos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
            throws Exception {

    }

    /**
     * Preenche Combo de Grupos associados ao Contrato selecionado.
     *
     * @param document
     * @param request
     * @param response
     * @param idContrato
     * @throws Exception
     */
    public void preencherComboGrupos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response,
            Integer idContrato) throws Exception {

        if (idContrato != null) {
            document.executeScript("exibirDivGruposContrato();");
            final ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(
                    ContratoService.class, null);

            ContratoDTO contratroDTO = new ContratoDTO();
            contratroDTO.setIdContrato(idContrato);

            contratroDTO = (ContratoDTO) contratoService.restore(contratroDTO);

            if (contratroDTO.getIdGrupoSolicitante() != null) {

                GrupoDTO grupoDto = new GrupoDTO();

                grupoDto.setIdGrupo(contratroDTO.getIdGrupoSolicitante());

                final GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(
                        GrupoService.class, null);

                final HTMLSelect comboGrupo = document.getSelectById("idGrupo");

                inicializarCombo(comboGrupo, request);

                grupoDto = (GrupoDTO) grupoService.restore(grupoDto);

                if (idContrato != null) {

                    comboGrupo.addOption(grupoDto.getIdGrupo().toString(), grupoDto.getNome());
                }
            }

        } else {
            document.executeScript("ocultarDivGruposContrato();");
        }

    }

    @Override
    public Class<EmpregadoDTO> getBeanClass() {
        return EmpregadoDTO.class;
    }

    private EmpregadoService empregadoService;

    private EmpregadoService getEmpregadoService() throws Exception {
        if (empregadoService == null) {
            empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
        }
        return empregadoService;
    }

    private GrupoEmpregadoService grupoEmpregadoService;

    private GrupoEmpregadoService getGrupoEmpregadoService() throws Exception {
        if (grupoEmpregadoService == null) {
            grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(
                    GrupoEmpregadoService.class, null);
        }
        return grupoEmpregadoService;
    }

    private void setEmpregadoBean(IDto empregado) {
        empregadoBean = (EmpregadoDTO) empregado;
    }

    private EmpregadoDTO getEmpregadoBean() {
        return empregadoBean;
    }

}
