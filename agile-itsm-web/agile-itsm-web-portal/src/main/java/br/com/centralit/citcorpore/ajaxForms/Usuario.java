package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoUsuarioService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author CentralIT
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class Usuario extends AjaxFormAction {

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        CITCorporeUtil.limparFormulario(document);
        document.focusInFirstActivateField(null);
        preencherComboPerfilAcessoUsuario(document, request, response);

    }

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuarioDto = (UsuarioDTO) document.getBean();
        Collection<GrupoEmpregadoDTO> colGrupo = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GrupoEmpregadoDTO.class, "colGrupoSerialize", request);

        UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        EmpregadoDTO empregadoBean = new EmpregadoDTO();
        EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

        usuarioDto.setIdUnidade(empregadoBean.getIdUnidade());
        usuarioDto.setIdGrupo(empregadoBean.getIdGrupo());

        if (colGrupo != null) {
            usuarioDto.setColGrupoEmpregado(colGrupo);
        } else {
            usuarioDto.setColGrupoEmpregado(new ArrayList<GrupoEmpregadoDTO>());
        }

        String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
        if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
            algoritmo = "SHA-1";
        }

        if (usuarioDto.getIdUsuario() == null || usuarioDto.getIdUsuario() == 0) {

            usuarioDto.setSenha(CriptoUtils.generateHash(usuarioDto.getSenha(), algoritmo));

            UsuarioDTO objStatus = usuarioService.listStatus(usuarioDto);
            if (objStatus == null) {
                UsuarioDTO objLogin = usuarioService.listLogin(usuarioDto);

                if ((objLogin == null)) {
                    usuarioDto.setIdEmpresa(WebUtil.getIdEmpresa(request));
                    usuarioDto.setStatus("A");

                    usuarioService.create(usuarioDto);

                    document.alert(UtilI18N.internacionaliza(request, "MSG05"));
                } else {
                    document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
                }
            } else {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
            }
        } else {

            if (usuarioDto.getSenha() != null && !StringUtils.isBlank(usuarioDto.getSenha())) {
                usuarioDto.setSenha(CriptoUtils.generateHash(usuarioDto.getSenha(), algoritmo));
            } else {
                usuarioDto.setSenha(null);
            }

            if (usuarioDto.getLdap() != null && usuarioDto.getLdap().equalsIgnoreCase("S")) {

                String metodoAutenticacao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.METODO_AUTENTICACAO_Pasta, "2");

                if (metodoAutenticacao != null && metodoAutenticacao.equalsIgnoreCase("2")) {

                    UsuarioDTO usuarioAux = new UsuarioDTO();

                    usuarioAux.setIdUsuario(usuarioDto.getIdUsuario());

                    usuarioAux = (UsuarioDTO) usuarioService.restore(usuarioAux);

                    if ((!usuarioAux.getNomeUsuario().equals(usuarioDto.getNomeUsuario())) || (!usuarioAux.getLogin().equals(usuarioDto.getLogin())) || (usuarioDto.getSenha() != null)
                            || (!usuarioAux.getIdEmpregado().equals(usuarioDto.getIdEmpregado()))) {

                        document.alert(UtilI18N.internacionaliza(request, "usuario.mensagensDeAutenticacaoLDAP"));

                        this.restore(document, request, response);

                        return;
                    }

                }
            }

            UsuarioDTO objUsuario = usuarioService.listUsuarioExistente(usuarioDto);

            if (objUsuario == null) {
                usuarioService.update(usuarioDto);
                document.alert(UtilI18N.internacionaliza(request, "MSG06"));
            } else{
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
            }
        }

        HTMLForm form = document.getForm("form");
        form.clear();

        document.executeScript("limpar()");
    }

    /**
     * @param document
     * @param request
     * @param response
     * @throws Exception
     *             Metodo colocar status Inativo quando for solicitado a exclusão do usuario.
     */
    public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuarioDto = (UsuarioDTO) document.getBean();

        UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

        if (usuarioDto.getIdUsuario().intValue() > 0) {
            usuarioService.delete((usuarioDto));
        }

        HTMLForm form = document.getForm("form");
        form.clear();
        document.alert(UtilI18N.internacionaliza(request, "MSG07"));

        document.executeScript("limpar()");
    }

    /**
     * @param document
     * @param request
     * @param response
     * @throws Exception
     *             Metodo para restaura os campos.
     */
    public void restoreEmpregado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuarioDto = (UsuarioDTO) document.getBean();
        UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

        GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);


        EmpregadoDTO empregadoBean = new EmpregadoDTO();
        EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
        empregadoBean.setIdEmpregado(usuarioDto.getIdEmpregado());
        empregadoBean = (EmpregadoDTO) empregadoService.restore(empregadoBean);
        usuarioDto.setNomeUsuario(empregadoBean.getNome());

        HTMLForm form = document.getForm("form");
        form.setValues(usuarioDto);

        //Preenche os grupos do empregado
        Collection<GrupoEmpregadoDTO> gruposEmpregado = grupoEmpregadoService.findByIdEmpregadoNome(usuarioDto.getIdEmpregado());

        document.executeScript("deleteAllRows()");
        if (gruposEmpregado != null) {
            for (GrupoEmpregadoDTO grupoempregadoDto : gruposEmpregado) {
                if (grupoempregadoDto != null) {
                    HTMLTable table;
                    table = document.getTableById("tabelaGrupo");
                    table.addRow(grupoempregadoDto, new String[] { "", "idGrupo", "sigla" }, null, null, new String[] { "gerarButtonDeleteGrupo" }, null, null);
                }
            }
        }

        document.executeScript("fecharPopup()");
    }

    public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuarioDto = (UsuarioDTO) document.getBean();
        UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        PerfilAcessoUsuarioService perfilAcessoUsuarioService = (PerfilAcessoUsuarioService) ServiceLocator.getInstance().getService(PerfilAcessoUsuarioService.class, null);
        GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
        PerfilAcessoUsuarioDTO perfilAcessoUsuarioDto = new PerfilAcessoUsuarioDTO();
        Integer idUsuario = null;

        usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
        usuarioDto.setSenha(null);
        idUsuario = usuarioDto.getIdUsuario();
        perfilAcessoUsuarioDto.setIdUsuario(idUsuario);

        PerfilAcessoUsuarioDTO dadoPerfilAcessoUsuario = perfilAcessoUsuarioService.listByIdUsuario(perfilAcessoUsuarioDto);

        if ((dadoPerfilAcessoUsuario != null)) {
            this.preencherComboPerfilAcessoUsuario(document, request, response);
            usuarioDto.setIdPerfilAcessoUsuario(dadoPerfilAcessoUsuario.getIdPerfilAcesso());
        }

        document.executeScript("deleteAllRows()");
        // Collection<GrupoDTO> colGrupo = grupoService.listaGrupoEmpregado(usuarioDto.getIdEmpregado());
        if (usuarioDto.getColGrupoEmpregado() != null) {
            for (GrupoEmpregadoDTO grupoempregadoDto : usuarioDto.getColGrupoEmpregado()) {
                if (grupoempregadoDto != null) {
                    HTMLTable table;
                    table = document.getTableById("tabelaGrupo");
                    table.deleteAllRows();
                    table.addRowsByCollection(usuarioDto.getColGrupoEmpregado(), new String[] { "", "idGrupo", "sigla" }, null, null, new String[] { "gerarButtonDeleteGrupo" }, null, null);
                }
            }
        }

        if(usuarioDto!= null && usuarioDto.getLdap()!= null && usuarioDto.getLdap().equalsIgnoreCase("S")){
        	document.executeScript("$('#divSenha').hide();");
			document.executeScript("$('#divAlterarSenha').hide();");
        }else{
        	document.executeScript("$('#divAlterarSenha').show();");
        }

        HTMLForm form = document.getForm("form");
        form.clear();
        form.setValues(usuarioDto);

    }

    /**
     * Preenche a combo PerfilAcesso.
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    private void preencherComboPerfilAcessoUsuario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuarioDto = (UsuarioDTO) document.getBean();

        PerfilAcessoService perfilAcessoService = (PerfilAcessoService) ServiceLocator.getInstance().getService(PerfilAcessoService.class, null);
        HTMLSelect comboPerfilAcessoUsuario = document.getSelectById("idPerfilAcessoUsuario");
        ArrayList<PerfilAcessoDTO> perfilAcessos = (ArrayList) perfilAcessoService.list();
        Integer idUsuario = null;

        inicializarCombo(comboPerfilAcessoUsuario, request);

        for (PerfilAcessoDTO perfilAcessoDto : perfilAcessos) {
            if (perfilAcessoDto.getDataFim() == null) {
                comboPerfilAcessoUsuario.addOption(perfilAcessoDto.getIdPerfilAcesso().toString(), StringEscapeUtils.escapeJavaScript(perfilAcessoDto.getNomePerfilAcesso()));
            }
        }
    }

    /**
     * Iniciliza combo.
     *
     * @param componenteCombo
     */
    private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
        componenteCombo.removeAllOptions();
        componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
    }

    /**
     * Usuario logado vai ter a possibilidade de fazer alteração de sua senha
     *
     * @param document
     * @param request
     * @param response
     * @throws Exception
     */
    public void alterarSenha(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDTO usuarioDto = (UsuarioDTO) document.getBean();
        UsuarioDTO usuario = new UsuarioDTO();

        UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

        usuarioDto.setIdUsuario(WebUtil.getUsuario(request).getIdUsuario());
        usuario = (UsuarioDTO) usuarioService.restore(usuarioDto);

        String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");

        if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
            algoritmo = "SHA-1";
        }

        if (usuarioDto.getIdUsuario() != null && usuarioDto.getIdUsuario() != 0) {
            usuario.setSenha(CriptoUtils.generateHash(usuarioDto.getSenha(), algoritmo));
            usuarioService.updateNotNull(usuario);
            document.alert(UtilI18N.internacionaliza(request, "MSG06"));
        }

        HTMLForm form = document.getForm("form");
        form.clear();

    }

    @Override
    public Class getBeanClass() {
        return UsuarioDTO.class;
    }

}
