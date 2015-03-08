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
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.CargosService;
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

    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.setEmpregadoBean((EmpregadoDTO) document.getBean());
        HTMLSelect comboUfOrgaoExpedidor = (HTMLSelect) document.getSelectById("idUFOrgExpedidor");
        HTMLSelect comboCtpsIdUf = (HTMLSelect) document.getSelectById("ctpsIdUf");
        HTMLSelect comboIdSituacaoFuncional = (HTMLSelect) document.getSelectById("idSituacaoFuncional");
        HTMLSelect comboTipo = (HTMLSelect) document.getSelectById("tipo");
        HTMLSelect comboEstadoCivil = (HTMLSelect) document.getSelectById("estadoCivil");

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

        UfService ufService = (UfService) ServiceLocator.getInstance().getService(UfService.class, null);

        Collection colUfs = ufService.list();

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
        String validarComboUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");

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
        preencherComboGrupos(document, request, response, this.getEmpregadoBean().getIdContrato());
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
        this.setEmpregadoBean((EmpregadoDTO) document.getBean());

        if (this.getEmpregadoService().verificarEmpregadosAtivos(this.getEmpregadoBean())) {
            document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
            return;
        }

        if (this.getEmpregadoBean().getIdEmpregado() == null || this.getEmpregadoBean().getIdEmpregado().intValue() == 0) {
            this.getEmpregadoBean().setNomeProcura(this.getEmpregadoBean().getNome().trim());
            this.getEmpregadoBean().setNome(this.getEmpregadoBean().getNome().trim());
			this.getEmpregadoBean().setEmail(this.getEmpregadoBean().getEmail().trim());
            this.getEmpregadoBean().setDataCadastro(UtilDatas.getDataAtual());
            if (this.getEmpregadoBean().getValorSalario() != null) {
                this.getEmpregadoService().calcularCustos(this.getEmpregadoBean());
            }

            EmpregadoDTO empregadoDto = (EmpregadoDTO) this.getEmpregadoService().create(this.getEmpregadoBean());

            if (this.getEmpregadoBean().getIdGrupo() != null) {
                GrupoEmpregadoDTO grupoEmpregadoDto = new GrupoEmpregadoDTO();

                grupoEmpregadoDto.setIdEmpregado(empregadoDto.getIdEmpregado());
                grupoEmpregadoDto.setIdGrupo(this.getEmpregadoBean().getIdGrupo());
                if (this.getEmpregadoBean().getCpf() != null && !this.getEmpregadoBean().getCpf().equalsIgnoreCase("")) {
                    this.getEmpregadoBean().setCpf(this.getEmpregadoBean().getCpf().replace("-", "").replace(".", ""));
                }
                this.getEmpregadoBean().setCpf(this.getEmpregadoBean().getCpf().replace("-", "").replace(".", ""));
                getGrupoEmpregadoService().create(grupoEmpregadoDto);

            }
            HTMLForm form = document.getForm("form");
            form.clear();
            document.alert(UtilI18N.internacionaliza(request, "MSG05"));
        } else {
            this.getEmpregadoBean().setNomeProcura(this.getEmpregadoBean().getNome());
			this.getEmpregadoBean().setEmail(this.getEmpregadoBean().getEmail().trim());
            if (this.getEmpregadoBean().getValorSalario() != null) {
                this.getEmpregadoService().calcularCustos(this.getEmpregadoBean());
            }
            if (this.getEmpregadoBean().getCpf() != null && !this.getEmpregadoBean().getCpf().equalsIgnoreCase("")) {
                this.getEmpregadoBean().setCpf(this.getEmpregadoBean().getCpf().replace("-", "").replace(".", ""));
            }
            // this.getEmpregadoBean().getNome().trim();
            this.getEmpregadoService().update(this.getEmpregadoBean());
            HTMLForm form = document.getForm("form");
            form.clear();
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        }

        if (this.getEmpregadoBean().getIframe() != null && this.getEmpregadoBean().getIframe().equalsIgnoreCase("true")) {
            document.executeScript("parent.fecharAddSolicitante()");
        } else {
            HTMLForm form = document.getForm("form");
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
    public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer idEmpregado = 0;
        this.setEmpregadoBean((EmpregadoDTO) document.getBean());

        GrupoEmpregadoDTO grupoEmpregadoDTO = new GrupoEmpregadoDTO();
        UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
        ParametroCorporeDTO parametroCITSmart = parametroService.getParamentroAtivo(ParametroSistema.METODO_AUTENTICACAO_Pasta.id());

        if (this.getEmpregadoBean().getIdEmpregado() != null) {
            if (parametroCITSmart != null && parametroCITSmart.getValor().trim().equalsIgnoreCase("2")) {
                UsuarioDTO usuarioDto = new UsuarioDTO();
                usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregadosDeUsuarios(this.getEmpregadoBean().getIdEmpregado());

                if (usuarioDto != null) {
                    if (usuarioDto.getStatus() != null && usuarioDto.getStatus().equalsIgnoreCase("A")) {
                        document.alert(UtilI18N.internacionaliza(request, "colaborador.mensagensDeAutenticacaoLDAPColaboradorAtivo"));
                        return;
                    }
                }
            }

            if (this.getEmpregadoBean().getIdEmpregado().intValue() > 0) {
                grupoEmpregadoDTO.setIdEmpregado(getEmpregadoBean().getIdEmpregado());
                idEmpregado = grupoEmpregadoDTO.getIdEmpregado();
                Collection<GrupoEmpregadoDTO> empregadosDeGrupo = (Collection<GrupoEmpregadoDTO>) getGrupoEmpregadoService().findByIdEmpregado(idEmpregado);

                if (empregadosDeGrupo != null) {
                    for (GrupoEmpregadoDTO grupoEmpregado : empregadosDeGrupo) {
                        getGrupoEmpregadoService().deleteByIdGrupoAndEmpregado(grupoEmpregado.getIdGrupo(), grupoEmpregado.getIdEmpregado());
                    }
                }
                /* Usado assim pois nao funciona o i18n no serviceEJB sem passar o usuario da sessao. */
                getEmpregadoService().deleteEmpregado(getEmpregadoBean());
            }

            HTMLForm form = document.getForm("form");
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
    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        this.setEmpregadoBean((EmpregadoDTO) document.getBean());
        this.setEmpregadoBean(this.getEmpregadoService().restore(getEmpregadoBean()));
        HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(this.getEmpregadoBean());
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
    public void preencherComboUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
        HTMLSelect comboUnidade = (HTMLSelect) document.getSelectById("idUnidade");
        ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();

        inicializarCombo(comboUnidade, request);
        if (unidades != null) {
            for (UnidadeDTO unidade : unidades)
                if (unidade.getDataFim() == null)
                    comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
        }

    }

    public void carregaUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
        EmpregadoDTO empregadoDTO = empregadoBean;
        HTMLSelect comboUnidade = (HTMLSelect) document.getSelectById("idUnidade");
        Integer idContrato = empregadoDTO.getIdContrato();
        ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);

        inicializarCombo(comboUnidade, request);
        if (unidades != null) {
            for (UnidadeDTO unidade : unidades)
                if (unidade.getDataFim() == null)
                    comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
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
    public void preencherComboGrupo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
        HTMLSelect comboGrupo = (HTMLSelect) document.getSelectById("idGrupo");
        ArrayList<GrupoDTO> grupos = (ArrayList) grupoService.list();
        inicializarCombo(comboGrupo, request);
        for (GrupoDTO grupo : grupos)
            if (grupo.getDataFim() == null)
                comboGrupo.addOption(grupo.getIdGrupo().toString(), grupo.getNome());
    }

    /**
     * Preenche combo de Grupos.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void preencherComboCargos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CargosService cargosService = (CargosService) ServiceLocator.getInstance().getService(CargosService.class, null);
        HTMLSelect comboCargos = (HTMLSelect) document.getSelectById("idCargo");
        ArrayList<CargosDTO> cargos = (ArrayList) cargosService.list();
        inicializarCombo(comboCargos, request);
        for (CargosDTO cargo : cargos)
            if (cargo.getDataFim() == null)
                comboCargos.addOption(cargo.getIdCargo().toString(), StringEscapeUtils.escapeJavaScript(cargo.getNomeCargo()));
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
    public void preencherComboGrupos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Integer idContrato) throws Exception {

        if (idContrato != null) {
            document.executeScript("exibirDivGruposContrato();");
            ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);

            ContratoDTO contratroDTO = new ContratoDTO();
            contratroDTO.setIdContrato(idContrato);

            contratroDTO = (ContratoDTO) contratoService.restore(contratroDTO);

            if (contratroDTO.getIdGrupoSolicitante() != null) {

                GrupoDTO grupoDto = new GrupoDTO();

                grupoDto.setIdGrupo(contratroDTO.getIdGrupoSolicitante());

                GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

                HTMLSelect comboGrupo = (HTMLSelect) document.getSelectById("idGrupo");

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
            grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
        }
        return grupoEmpregadoService;
    }

    private void setEmpregadoBean(IDto empregado) {
        this.empregadoBean = (EmpregadoDTO) empregado;
    }

    private EmpregadoDTO getEmpregadoBean() {
        return this.empregadoBean;
    }

}
