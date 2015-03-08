package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ADUserDTO;
import br.com.centralit.citcorpore.bean.BaseDTO;
import br.com.centralit.citcorpore.bean.EmailDTO;
import br.com.centralit.citcorpore.bean.EmpresaDTO;
import br.com.centralit.citcorpore.bean.GedDTO;
import br.com.centralit.citcorpore.bean.GeralDTO;
import br.com.centralit.citcorpore.bean.ICDTO;
import br.com.centralit.citcorpore.bean.InstalacaoDTO;
import br.com.centralit.citcorpore.bean.LdapDTO;
import br.com.centralit.citcorpore.bean.LogDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.ReleaseDTO;
import br.com.centralit.citcorpore.bean.SmtpDTO;
import br.com.centralit.citcorpore.bean.StartDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.integracao.ad.LDAPUtils;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.centralit.citcorpore.metainfo.negocio.VisaoService;
import br.com.centralit.citcorpore.negocio.DataBaseMetaDadosService;
import br.com.centralit.citcorpore.negocio.EmpresaService;
import br.com.centralit.citcorpore.negocio.InstalacaoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.negocio.ScriptsService;
import br.com.centralit.citcorpore.negocio.VersaoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.FiltroSegurancaCITSmart;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citgerencial.util.Util;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.ConnectionProvider;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.ScriptRunner;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.XmlReadLookup;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Controller da página de instalação do sistema
 *
 * @author flavio.santana
 *
 */
public class Start extends AjaxFormAction {

    private final String DEFAULT = "";

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String sessao = (String) request.getSession().getAttribute("passoInstalacao");
        if (sessao != null) {
            document.executeScript("setNext('" + sessao + "')");

            this.carregaEmail(document, request, response);
            this.carregaEmpresa(document, request, response);
            this.carregaParametrosLDAP(document, request, response);
            this.carregaGED(document, request, response);
            this.carregaLog(document, request, response);
            this.carregaSMTP(document, request, response);
            /**
             * Se versão free não carrega parametros de IC
             */
            if (!br.com.citframework.util.Util.isVersionFree(request)) {
                this.carregaParametrosIC(document, request, response);
            }
            this.carregaParametrosBase(document, request, response);
            this.carregaParametrosGerais(document, request, response);
        } else {
            this.reload(document, request, null);
        }
    }

    /**
     * Gera carga inicial do banco
     *
     * @throws Exception
     * @throws ServiceException
     */
    public void gerarCargaInicial(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO start = (StartDTO) document.getBean();
        final String sessao = (String) request.getSession().getAttribute("passoInstalacao");

        if (this.verificaParametrosConexao(document, request, response)) {
            try (Connection conn = ConnectionProvider.getConnection(Constantes.getValue("DATABASE_ALIAS"))) {
                if (sessao == null) {
                    this.setSession(request, start.getCurrent());

                    final ScriptRunner runner = new ScriptRunner(conn, true, true);
                    runner.setDelimiter("(;(\r)?\n)|(--\n)", false);
                    try {
                        runner.runScript(new File(CITCorporeUtil.CAMINHO_REAL_APP + "/scripts_deploy/" + conn.getMetaData().getDatabaseProductName().replaceAll(" ", "_") + ".sql"));
                    } catch (final Exception er) {}
                    /**
                     * Mata da sessão o parametro de instalação
                     */
                    final ServletContext context = request.getSession().getServletContext();
                    context.setAttribute("instalacao", null);
                    this.reload(document, request, null);
                } else {
                    this.carregaEmpresa(document, request, response);
                    document.executeScript("setNext('" + start.getCurrent() + "')");
                }
            } catch (final Exception e) {
                this.setSession(request, null);
            }
        } else {
            document.alert(UtilI18N.internacionaliza(request, "start.instalacao.validaConexao"));
            document.executeScript("habilitaInstall();");
        }
    }

    /**
     * Verifica o status da conexão
     *
     * @throws SQLException
     */
    public boolean verificaParametrosConexao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final StartDTO conexao = (StartDTO) document.getBean();

        final Connection conn = ConnectionProvider.getConnection(Constantes.getValue("DATABASE_ALIAS"));
        if (conn.getMetaData().getDatabaseProductName().equalsIgnoreCase(conexao.getDriverConexao())) {
            conn.close();
            return true;
        }
        return false;
    }

    /**
     * Infomações iniciais de configuração da Empresa
     *
     * @throws Exception
     * @throws ServiceException
     */
    public void cadastraEmpresa(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO start = (StartDTO) document.getBean();

        final EmpresaDTO empresaDTO = new EmpresaDTO();
        final EmpresaService empresaService = (EmpresaService) ServiceLocator.getInstance().getService(EmpresaService.class, null);
        if ("".equals(start.getNomeEmpresa()) || "Default".equals(start.getNomeEmpresa().trim())) {
            document.alert(UtilI18N.internacionaliza(request, "start.instalacao.validaEmpresa"));
            document.executeScript("document.frmEmpresa.nomeEmpresa.focus()");
        } else {
            empresaDTO.setIdEmpresa(1); // Definindo o id padrão da carga
            empresaDTO.setNomeEmpresa(start.getNomeEmpresa());
            empresaDTO.setDetalhamento(start.getDetalhamento());
            empresaDTO.setDataInicio(UtilDatas.getDataAtual());
            empresaService.update(empresaDTO);
            this.setSession(request, start.getCurrent());
            document.executeScript("setNext('" + start.getCurrent() + "')");
            this.carregaParametrosLDAP(document, request, response);
        }
    }

    /**
     * Carrega as informações de empresa
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void carregaEmpresa(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final EmpresaService empresaService = (EmpresaService) ServiceLocator.getInstance().getService(EmpresaService.class, null);
        EmpresaDTO empresaDTO = new EmpresaDTO();
        empresaDTO.setIdEmpresa(1);
        empresaDTO = (EmpresaDTO) empresaService.restore(empresaDTO);
        final HTMLForm form = document.getForm("frmEmpresa");

        if (empresaDTO != null) {
            form.setValues(empresaDTO);
        }
    }

    /**
     * Carrega os paramatros do LDAP
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void carregaParametrosLDAP(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        document.executeScript("deleteAllRowsTabelaAtributosLdap()");

        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_URL.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.LDAP_URL.getCampoParametroInternacionalizado(request)) + "','" + this.escapeValor(Enumerados.ParametroSistema.LDAP_URL)
                + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.DOMINIO_AD.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.DOMINIO_AD.getCampoParametroInternacionalizado(request)) + "','"
                + this.escapeValor(Enumerados.ParametroSistema.DOMINIO_AD) + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_SUBDOMINIO.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.LDAP_SUBDOMINIO.getCampoParametroInternacionalizado(request)) + "','"
                + this.escapeValor(Enumerados.ParametroSistema.LDAP_SUBDOMINIO) + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO.getCampoParametroInternacionalizado(request)) + "','"
                + this.escapeValor(Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO) + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LOGIN_AD.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.LOGIN_AD.getCampoParametroInternacionalizado(request)) + "','" + this.escapeValor(Enumerados.ParametroSistema.LOGIN_AD)
                + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.SENHA_AD.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.SENHA_AD.getCampoParametroInternacionalizado(request)) + "','" + this.escapeValor(Enumerados.ParametroSistema.SENHA_AD)
                + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_FILTRO.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.LDAP_FILTRO.getCampoParametroInternacionalizado(request)) + "','"
                + this.escapeValor(Enumerados.ParametroSistema.LDAP_FILTRO) + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_ATRIBUTO.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.LDAP_ATRIBUTO.getCampoParametroInternacionalizado(request)) + "','"
                + this.escapeValor(Enumerados.ParametroSistema.LDAP_ATRIBUTO) + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_SN_LAST_NAME.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.LDAP_SN_LAST_NAME.getCampoParametroInternacionalizado(request)) + "','"
                + this.escapeValor(Enumerados.ParametroSistema.LDAP_SN_LAST_NAME) + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT.getCampoParametroInternacionalizado(request)) + "','"
                + this.escapeValor(Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT) + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP.getCampoParametroInternacionalizado(request)) + "','"
                + this.escapeValor(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP) + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD.id() + ",'"
                + this.escape(Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD.getCampoParametroInternacionalizado(request)) + "','"
                + this.escapeValor(Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD) + " ')");
        document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_MOSTRA_BOTAO.id() + ",'"
                + StringEscapeUtils.escapeJavaScript(Enumerados.ParametroSistema.LDAP_MOSTRA_BOTAO.getCampoParametroInternacionalizado(request)) + "','"
                + this.escapeValor(Enumerados.ParametroSistema.LDAP_MOSTRA_BOTAO, "N") + " ')");
    }

    /**
     * Grava os parametros do LDAP informados pelo usuário
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void configuraLDAP(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO ldap = (StartDTO) document.getBean();
        final ParametroCorporeService parametroCorporeService = this.getParametroCorporeService();

        /* Atualiza o metodo de autencicação de pasta 1 Proprio / 2LDAP */
        final ParametroCorporeDTO metodoAutenticacaoPasta = new ParametroCorporeDTO();
        metodoAutenticacaoPasta.setId(Enumerados.ParametroSistema.METODO_AUTENTICACAO_Pasta.id());
        metodoAutenticacaoPasta.setValor(ldap.getMetodoAutenticacao());
        this.getParametroCorporeService().atualizarParametros(metodoAutenticacaoPasta);

        if (ldap.getMetodoAutenticacao().equals("2")) {
            final Collection<ADUserDTO> listaAdUser = LDAPUtils.testarConexao();

            for (final ADUserDTO adUserDto : listaAdUser) {
                if (adUserDto != null) {
                    ldap.setListLdapDTO(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(LdapDTO.class, "listAtributoLdapSerializado", request));
                    if (ldap.getListLdapDTO() != null && !ldap.getListLdapDTO().isEmpty()) {
                        for (final LdapDTO parametroLDAP : ldap.getListLdapDTO()) {
                            final ParametroCorporeDTO parametroCorpore = new ParametroCorporeDTO();
                            parametroCorpore.setId(Integer.parseInt(parametroLDAP.getIdAtributoLdap().trim()));
                            parametroCorpore.setValor(parametroLDAP.getValorAtributoLdap().trim());
                            parametroCorporeService.atualizarParametros(parametroCorpore);
                        }
                    }
                    document.executeScript("setNext('" + ldap.getCurrent() + "')");
                } else {
                    document.alert(UtilI18N.internacionaliza(request, "instalacao.parametrosConexaoInvalidos"));
                }
            }
        } else {
            document.executeScript("setNext('" + ldap.getCurrent() + "')");
        }
        this.setSession(request, ldap.getCurrent());
        this.carregaEmail(document, request, response);
    }

    /**
     * Testa os parametros de Conexão LDAP
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void testaLDAP(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final Collection<ADUserDTO> listaAdUserDto = LDAPUtils.testarConexao();

        for (final ADUserDTO adUserDto : listaAdUserDto) {
            if (adUserDto != null) {
                final StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sAMAccountName: " + this.getDisponivel(request, adUserDto.getsAMAccountName()));
                stringBuilder.append("\nE-mail: " + this.getDisponivel(request, adUserDto.getMail()));
                stringBuilder.append("\nCN: " + this.getDisponivel(request, adUserDto.getCN()));
                stringBuilder.append("\nSN: " + this.getDisponivel(request, adUserDto.getSN()));
                stringBuilder.append("\nDN: " + this.getDisponivel(request, adUserDto.getDN()));
                stringBuilder.append("\nDisplay Name: " + this.getDisponivel(request, adUserDto.getDisplayName()));

                document.alert(stringBuilder.toString());
            } else {
                document.alert(UtilI18N.internacionaliza(request, "ldap.conexaofalhou"));
            }
        }
    }

    /**
     *
     * @param request
     * @param atributoLdap
     * @return
     */
    private String getDisponivel(final HttpServletRequest request, final String atributoLdap) {
        if (StringUtils.isBlank(atributoLdap)) {
            return UtilI18N.internacionaliza(request, "ldap.naodiponivel");
        }
        return atributoLdap;
    }

    @SuppressWarnings("unchecked")
    public void configuraEmail(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO emailDto = (StartDTO) document.getBean();
        final ParametroCorporeService parametroCorporeService = this.getParametroCorporeService();

        emailDto.setListEmailDTO(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EmailDTO.class, "listAtributoEmailSerializado", request));
        if (emailDto.getListEmailDTO() != null && !emailDto.getListEmailDTO().isEmpty()) {
            for (final EmailDTO parametroLdap : emailDto.getListEmailDTO()) {
                final ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
                parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoEmail().trim()));
                parametroCorporeDto.setValor(parametroLdap.getValorAtributoEmail().trim());
                parametroCorporeService.atualizarParametros(parametroCorporeDto);
            }
        }
        this.setSession(request, emailDto.getCurrent());
        document.executeScript("setNext('" + emailDto.getCurrent() + "')");
        this.carregaLog(document, request, response);
    }

    public void carregaEmail(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        document.executeScript("deleteAllRowsTabelaAtributosEmail()");

        document.executeScript("addLinhaEmail(" + Enumerados.ParametroSistema.RemetenteNotificacoesSolicitacao.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.RemetenteNotificacoesSolicitacao.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.RemetenteNotificacoesSolicitacao) + "'," + "'N')");
        document.executeScript("addLinhaEmail(" + Enumerados.ParametroSistema.EmailUsuario.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.EmailUsuario.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.EmailUsuario) + "'," + "'N')");
        document.executeScript("addLinhaEmail(" + Enumerados.ParametroSistema.EmailSenha.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.EmailSenha.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.EmailSenha) + "'," + "'N')");
        document.executeScript("addLinhaEmail(" + Enumerados.ParametroSistema.EmailAutenticacao.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.EmailAutenticacao.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.EmailAutenticacao) + "'," + "'S')");
        document.executeScript("changeCheck()");
    }

    /**
     * Carrega as informações dos parametros do sistema para log
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void carregaLog(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        document.executeScript("deleteAllRowsTabelaAtributosLog()");

        document.executeScript("addLinhaLog(" + Enumerados.ParametroSistema.USE_LOG.id() + "," + "'"
                + StringEscapeUtils.escapeJavaScript(Enumerados.ParametroSistema.USE_LOG.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.USE_LOG) + "'," + "'S','','','')");
        document.executeScript("addLinhaLog(" + Enumerados.ParametroSistema.FILE_LOG.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.FILE_LOG.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.FILE_LOG) + "'," + "'N', '', '', '')");
        document.executeScript("addLinhaLog(" + Enumerados.ParametroSistema.PATH_LOG.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.PATH_LOG.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.PATH_LOG) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);')");
        document.executeScript("addLinhaLog(" + Enumerados.ParametroSistema.TIPO_LOG.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.TIPO_LOG.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.TIPO_LOG) + "'," + "'N','true','','')");
        document.executeScript("addLinhaLog(" + Enumerados.ParametroSistema.EXT_LOG.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.EXT_LOG.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.EXT_LOG) + "'," + "'N','','','')");
        document.executeScript("changeCheck()");
    }

    /**
     * Grava as informações de log do sistema
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void configuraLog(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO log = (StartDTO) document.getBean();

        log.setListLogDTO(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(LogDTO.class, "listAtributoLogSerializado", request));
        if (log.getListLogDTO() != null && !log.getListLogDTO().isEmpty()) {
            for (final LogDTO parametroLdap : log.getListLogDTO()) {
                final ParametroCorporeDTO parametroCorpore = new ParametroCorporeDTO();
                parametroCorpore.setId(Integer.parseInt(parametroLdap.getIdAtributoLog().trim()));
                parametroCorpore.setValor(parametroLdap.getValorAtributoLog().trim());
                this.getParametroCorporeService().atualizarParametros(parametroCorpore);
            }
        }
        this.setSession(request, log.getCurrent());
        document.executeScript("setNext('" + log.getCurrent() + "')");
        this.carregaGED(document, request, response);
    }

    /**
     * Carrega as informações do gerenciamento eletronico de documentos
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void carregaGED(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        document.executeScript("deleteAllRowsTabelaAtributosGed()");
        document.executeScript("addLinhaGed(" + Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);')");

        document.executeScript("addLinhaGed(" + Enumerados.ParametroSistema.GedDiretorio.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.GedDiretorio.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.GedDiretorio) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);')");
        document.executeScript("changeCheck()");
    }

    /**
     * Carrega as informações do gerenciamento eletronico de documentos
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void carregaSMTP(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        document.executeScript("deleteAllRowsTabelaAtributosSMTP()");

        document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.EmailSMTP.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.EmailSMTP.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.EmailSMTP) + "'," + "'N', '')");
        document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Servidor.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.SMTP_LEITURA_Servidor.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Servidor) + "'," + "'N', '')");
        document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Caixa.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.SMTP_LEITURA_Caixa.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Caixa) + "'," + "'N', '')");
        document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Senha.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.SMTP_LEITURA_Senha.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Senha) + "'," + "'N', '')");
        document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Provider.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.SMTP_LEITURA_Provider.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Provider) + "'," + "'N', '')");
        document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Porta.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.SMTP_LEITURA_Porta.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Porta) + "'," + "'N', 'Format[Numero]')");
        document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Pasta.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.SMTP_LEITURA_Pasta.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Pasta) + "'," + "'N', '')");
        document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_LIMITE_.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.SMTP_LEITURA_LIMITE_.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_LIMITE_) + "'," + "'N', 'Format[Numero]')");
        document.executeScript("changeCheck()");
    }

    /**
     * Grava as informações de Ged do sistema
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void configuraGed(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO ged = (StartDTO) document.getBean();
        final ParametroCorporeService parametroCorporeService = this.getParametroCorporeService();

        /* Atualiza ged interno */
        final ParametroCorporeDTO gedInterno = new ParametroCorporeDTO();
        gedInterno.setId(Enumerados.ParametroSistema.GedInterno.id());
        gedInterno.setValor("S");
        parametroCorporeService.atualizarParametros(gedInterno);

        /* Atualiza a ged interno bd */
        final ParametroCorporeDTO gedInternoBD = new ParametroCorporeDTO();
        gedInternoBD.setId(Enumerados.ParametroSistema.GedInternoBD.id());
        gedInternoBD.setValor("N");
        parametroCorporeService.atualizarParametros(gedInternoBD);

        ged.setListGedDTO(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GedDTO.class, "listAtributoGedSerializado", request));
        if (ged.getListGedDTO() != null && !ged.getListGedDTO().isEmpty()) {
            for (final GedDTO parametroLdap : ged.getListGedDTO()) {
                final ParametroCorporeDTO parametroCorpore = new ParametroCorporeDTO();
                parametroCorpore.setId(Integer.parseInt(parametroLdap.getIdAtributoGed().trim()));
                parametroCorpore.setValor(parametroLdap.getValorAtributoGed().trim());
                parametroCorporeService.atualizarParametros(parametroCorpore);
            }
        }
        this.setSession(request, ged.getCurrent());
        document.executeScript("setNext('" + ged.getCurrent() + "')");
        this.carregaSMTP(document, request, response);
    }

    /**
     * Grava as informações de SMTP do sistema
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void configuraSMTP(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO smtp = (StartDTO) document.getBean();

        smtp.setListSmtpDTO(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(SmtpDTO.class, "listAtributoSMTPSerializado", request));
        if (smtp.getListSmtpDTO() != null && !smtp.getListSmtpDTO().isEmpty()) {
            for (final SmtpDTO parametroLdap : smtp.getListSmtpDTO()) {
                final ParametroCorporeDTO parametroCorpore = new ParametroCorporeDTO();
                parametroCorpore.setId(Integer.parseInt(parametroLdap.getIdAtributoSMTP().trim()));
                parametroCorpore.setValor(parametroLdap.getValorAtributoSMTP().trim());
                this.getParametroCorporeService().atualizarParametros(parametroCorpore);
            }
        }
        this.setSession(request, smtp.getCurrent());
        document.executeScript("setNext('" + smtp.getCurrent() + "')");
        this.carregaParametrosIC(document, request, response);
    }

    /**
     * Carrega as informações do gerenciamento eletronico de documentos
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void carregaParametrosIC(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        document.executeScript("deleteAllRowsTabelaAtributosIC()");

        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_DESENVOLVIMENTO.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_DESENVOLVIMENTO.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_DESENVOLVIMENTO) + "'," + "'N', '', '', '', '')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_PRODUCAO.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_PRODUCAO.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_PRODUCAO) + "'," + "'N', '', '', '', '')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_HOMOLOGACAO.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_HOMOLOGACAO.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_HOMOLOGACAO) + "'," + "'N', '', '', '', '')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.NOME_INVENTARIO.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.NOME_INVENTARIO.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.NOME_INVENTARIO) + "'," + "'N', '', '', '', '')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.ITEM_CONFIGURACAO_MUDANCA.id() + "," + "'"
                + StringEscapeUtils.escapeJavaScript(Enumerados.ParametroSistema.ITEM_CONFIGURACAO_MUDANCA.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.ITEM_CONFIGURACAO_MUDANCA) + "'," + "'S', '', '', '', '')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.ENVIO_PADRAO_EMAIL_IC.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.ENVIO_PADRAO_EMAIL_IC.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.ENVIO_PADRAO_EMAIL_IC) + "'," + "'N', '', '', '', 'Format[Numero]')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_LICENCA.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_LICENCA.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_LICENCA) + "'," + "'N', '', '', '', 'Format[Numero]')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.ENVIAR_EMAIL_DATAEXPIRACAO.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.ENVIAR_EMAIL_DATAEXPIRACAO.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.ENVIAR_EMAIL_DATAEXPIRACAO) + "'," + "'N', '', '', '', 'Format[Numero]')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.CaminhoArquivoNetMap.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.CaminhoArquivoNetMap.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.CaminhoArquivoNetMap) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);', '')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.FaixaIp.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.FaixaIp.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.FaixaIp) + "'," + "'N', '', '', '', '')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.DiretorioXmlAgente.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.DiretorioXmlAgente.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.DiretorioXmlAgente) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);', '')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.CaminhoNmap.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.CaminhoNmap.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.CaminhoNmap) + "'," + "'N', '', '', '', '')");
        document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.DiasInventario.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.DiasInventario.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.DiasInventario) + "'," + "'N', '', '', '' , 'Format[Numero]')");
        document.executeScript("changeCheck()");
    }

    /**
     * Grava as informações de parametros para item de configuração do sistema
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void configuraParametrosIC(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO paramIC = (StartDTO) document.getBean();
        paramIC.setListIcDTO(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ICDTO.class, "listAtributoICSerializado", request));
        if (paramIC.getListIcDTO() != null && !paramIC.getListIcDTO().isEmpty()) {
            for (final ICDTO parametroLdap : paramIC.getListIcDTO()) {
                final ParametroCorporeDTO parametroCorpore = new ParametroCorporeDTO();
                parametroCorpore.setId(Integer.parseInt(parametroLdap.getIdAtributoIC().trim()));
                parametroCorpore.setValor(parametroLdap.getValorAtributoIC().trim());
                this.getParametroCorporeService().atualizarParametros(parametroCorpore);
            }
        }
        this.setSession(request, paramIC.getCurrent());
        document.executeScript("setNext('" + paramIC.getCurrent() + "')");
        this.carregaParametrosBase(document, request, response);
    }

    /**
     * Carrega as informações de base de conhecimento
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void carregaParametrosBase(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        document.executeScript("deleteAllRowsTabelaAtributosBase()");
        document.executeScript("addLinhaParametrosBase(" + Enumerados.ParametroSistema.LUCENE_DIR_BASECONHECIMENTO.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.LUCENE_DIR_BASECONHECIMENTO.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.LUCENE_DIR_BASECONHECIMENTO) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);', '')");
        document.executeScript("addLinhaParametrosBase(" + Enumerados.ParametroSistema.LUCENE_DIR_PALAVRAGEMEA.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.LUCENE_DIR_PALAVRAGEMEA.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.LUCENE_DIR_PALAVRAGEMEA) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);', '')");
        document.executeScript("addLinhaParametrosBase(" + Enumerados.ParametroSistema.LUCENE_DIR_ANEXOBASECONHECIMENTO.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.LUCENE_DIR_ANEXOBASECONHECIMENTO.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.LUCENE_DIR_ANEXOBASECONHECIMENTO) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);', '')");
        document.executeScript("addLinhaParametrosBase(" + Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_BASECONHECIMENTO.id() + "," + "'"
                + this.escape(Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_BASECONHECIMENTO.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_BASECONHECIMENTO) + "'," + "'N', '', '', '', 'Format[Numero]')");
        document.executeScript("changeCheck()");
    }

    /**
     * Grava as informações de parametros para base de conhecimento do sistema
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void configuraParametrosBase(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO paramBase = (StartDTO) document.getBean();
        paramBase.setListBaseDTO(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(BaseDTO.class, "listAtributoBaseSerializado", request));
        if (paramBase.getListBaseDTO() != null && !paramBase.getListBaseDTO().isEmpty()) {
            for (final BaseDTO parametroLdap : paramBase.getListBaseDTO()) {
                final ParametroCorporeDTO parametroCorpore = new ParametroCorporeDTO();
                parametroCorpore.setId(Integer.parseInt(parametroLdap.getIdAtributoBase().trim()));
                parametroCorpore.setValor(parametroLdap.getValorAtributoBase().trim());
                this.getParametroCorporeService().atualizarParametros(parametroCorpore);
            }
        }
        this.setSession(request, paramBase.getCurrent());
        document.executeScript("setNext('" + paramBase.getCurrent() + "')");
        this.carregaParametrosGerais(document, request, response);
    }

    /**
     * Carrega as informações de base de conhecimento
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void carregaParametrosGerais(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        document.executeScript("deleteAllRowsTabelaAtributosGerais()");

        document.executeScript("addLinhaParametrosGerais(" + Enumerados.ParametroSistema.URL_Sistema.id() + "," + "'"
                + StringEscapeUtils.escapeJavaScript(Enumerados.ParametroSistema.URL_Sistema.getCampoParametroInternacionalizado(request)) + "'," + "'"
                + this.escapeValor(Enumerados.ParametroSistema.URL_Sistema) + "'," + "'N','')");

        /*
         * Rodrigo Pecci Acorse - 09/12/2013 14h25 - #126457 Adiciona o input que solicita o nome do schema somente se o banco de dados for diferente de Sql Server e Oracle
         */
        if (!CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER) && !CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            document.executeScript("addLinhaParametrosGerais(" + Enumerados.ParametroSistema.DB_SCHEMA.id() + "," + "'"
                    + this.escape(Enumerados.ParametroSistema.DB_SCHEMA.getCampoParametroInternacionalizado(request)) + "'," + "'"
                    + this.escapeValor(Enumerados.ParametroSistema.DB_SCHEMA) + "'," + "'N','true')");
        }
        document.executeScript("changeCheck()");
    }

    @SuppressWarnings("unchecked")
    public void configuraParametrosGerais(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO paramBase = (StartDTO) document.getBean();
        final ParametroCorporeService parametroCorporeService = this.getParametroCorporeService();
        final InstalacaoService instalacaoService = this.getInstalacaoService();
        final MenuService menuService = this.getMenuService();

        this.carregaScript();

        /*
         * Rodrigo Pecci Acorse - 06/12/2013 15h30 - #126457 Seta o schema nos parametros do sistema se o banco for Oracle.
         */
        if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
            String userName = null;

            final DataSource ds = (DataSource) new InitialContext().lookup(Constantes.getValue("CONTEXTO_CONEXAO") + Constantes.getValue("DATABASE_ALIAS"));
            if (ds != null) {
                try (Connection conn = ds.getConnection()) {
                    userName = conn.getMetaData().getUserName();
                    if (userName != null && !userName.equals("")) {
                        final ParametroCorporeDTO parametroCorporeSchema = new ParametroCorporeDTO();
                        parametroCorporeSchema.setId(Enumerados.ParametroSistema.DB_SCHEMA.id());
                        parametroCorporeSchema.setValor(userName);
                        parametroCorporeService.atualizarParametros(parametroCorporeSchema);
                    }
                }
            }
        }

        final ParametroCorporeDTO cadastroSolicitacaoServico = new ParametroCorporeDTO();
        cadastroSolicitacaoServico.setId(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO.id());
        cadastroSolicitacaoServico.setValor("/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load");
        parametroCorporeService.atualizarParametros(cadastroSolicitacaoServico);

        final ParametroCorporeDTO nomeFluxoPadraoServicos = new ParametroCorporeDTO();
        nomeFluxoPadraoServicos.setId(Enumerados.ParametroSistema.NomeFluxoPadraoServicos.id());
        nomeFluxoPadraoServicos.setValor("SolicitacaoServico");
        parametroCorporeService.atualizarParametros(nomeFluxoPadraoServicos);

        final ParametroCorporeDTO grupoPadraoNivel1 = new ParametroCorporeDTO();
        grupoPadraoNivel1.setId(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_NIVEL1.id());
        grupoPadraoNivel1.setValor("2");
        parametroCorporeService.atualizarParametros(grupoPadraoNivel1);

        final ParametroCorporeDTO idFaseExecucaoServicos = new ParametroCorporeDTO();
        idFaseExecucaoServicos.setId(Enumerados.ParametroSistema.IDFaseExecucaoServicos.id());
        idFaseExecucaoServicos.setValor("2");
        parametroCorporeService.atualizarParametros(idFaseExecucaoServicos);

        final ParametroCorporeDTO colaboradoresVinculoContratos = new ParametroCorporeDTO();
        colaboradoresVinculoContratos.setId(Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS.id());
        colaboradoresVinculoContratos.setValor("S");
        parametroCorporeService.atualizarParametros(colaboradoresVinculoContratos);

        paramBase.setListGeralDTO(br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GeralDTO.class, "listAtributoGeraisSerializado", request));
        if (paramBase.getListGeralDTO() != null && !paramBase.getListGeralDTO().isEmpty()) {
            for (final GeralDTO parametroLdap : paramBase.getListGeralDTO()) {
                final ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
                parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoGeral().trim()));
                parametroCorporeDto.setValor(parametroLdap.getValorAtributoGeral().trim());
                parametroCorporeService.atualizarParametros(parametroCorporeDto);
            }
        }

        try {
            System.out.println("CITSMART - Criando parametros novos... iniciando.");
            parametroCorporeService.criarParametrosNovos();
            System.out.println("CITSMART - Criando parametros novos... pronto.");
        } catch (final Exception e) {
            e.printStackTrace();
        }

        // Carrega os menus
        final String separator = System.getProperty("file.separator");
        final String diretorioReceita = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs" + separator;
        final File file = new File(diretorioReceita + "menu.xml");
        menuService.gerarCarga(file);

        /* Carregando as dinamic views */
        try {
            this.carregaVisoes(request);

            request.getSession().setAttribute("passoInstalacao", null);

            final ScriptsService scriptsService = this.getScriptsService();
            if (scriptsService.haScriptDeVersaoComErro()) {
                scriptsService.marcaErrosScriptsComoCorrigidos();
            }
            final VersaoService versaoService = this.getVersaoService();
            versaoService.validaVersoes(WebUtil.getUsuario(request));

            /**
             * Define a instalação com sucesso
             */
            final InstalacaoDTO instalacaoDTO = new InstalacaoDTO();
            instalacaoDTO.setSucesso("S");
            instalacaoService.create(instalacaoDTO);

            this.reload(document, request, "citcorpore.comum.citsmartInstaladoComSucesso");
            // forcei o usuário relogar por problemas de carregamento de menu.
            request.getSession(true).setAttribute("menuPadrao", null);
        } catch (final Exception ex) {
            document.alert(ex.getMessage());
            document.executeScript("habilita();");
        }

    }

    @Override
    public Class<StartDTO> getBeanClass() {
        return StartDTO.class;
    }

    /**
     * Defini o valor da sessão para os passos da instalação
     *
     * @param request
     * @param sessao
     */
    public void setSession(final HttpServletRequest request, final String sessao) {
        final String s = (String) request.getSession().getAttribute("passoInstalacao");
        if (s != null) {
            try {
                if (Integer.valueOf(UtilStrings.apenasNumeros(sessao)) > Integer.valueOf(UtilStrings.apenasNumeros(s))) {
                    request.getSession().setAttribute("passoInstalacao", sessao);
                }
            } catch (final Exception e) {}
        } else {
            request.getSession().setAttribute("passoInstalacao", sessao);
        }
    }

    /**
     * Dá um location para a página inicial
     *
     * @param document
     * @param request
     */
    public void reload(final DocumentHTML document, final HttpServletRequest request, final String mensagem) {
        String comando = "window.location = '" + request.getContextPath() + "/pages/index/index.load";
        if (mensagem != null && !mensagem.trim().isEmpty()) {
            comando += "?mensagem=" + mensagem;
        }
        comando += "';";
        document.executeScript(comando);
    }

    /**
     * Dá um location para a página inicial
     *
     * @param document
     * @param request
     */
    public void reloadPaginaScript(final DocumentHTML document, final HttpServletRequest request) {
        document.executeScript("window.location = '" + request.getContextPath() + "/pages/scripts/scripts.load?upgrade=sim';");
    }

    /**
     * Valida se Existe o diretório
     *
     * @param document
     * @param request
     * @param response
     * @throws ServiceException
     * @throws Exception
     */
    public void validaDiretorio(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws ServiceException, Exception {
        final StartDTO diretorio = (StartDTO) document.getBean();
        final String campo = diretorio.getCampoDiretorio();
        if (!diretorio.getDiretorio().equals("")) {
            if (!new File(diretorio.getDiretorio()).isDirectory()) {
                document.alert(UtilI18N.internacionaliza(request, "start.instalacao.diretorioNaoEncontrado"));
                if (campo != null) {
                    document.executeScript("$('#" + campo + "').val('');");
                    document.executeScript("$('#" + campo + "').focus();");
                }
            }
        }
    }

    /**
     * Realiza a leitura de um arquivo e incrementa em uma lista de uploads
     *
     * @param dir
     * @param lista
     * @return
     */
    public static List<UploadDTO> listDirectoryAppend(final File dir, final java.util.List<UploadDTO> lista) {
        if (dir.isDirectory()) {
            final String[] filhos = dir.list();
            for (final String filho : filhos) {
                final File nome = new File(dir, filho);
                if (nome.isFile()) {
                    if (nome.getName().endsWith(".citVision")) {
                        lista.add(new UploadDTO(nome.getName(), nome.getPath()));
                    }
                } else if (nome.isDirectory()) {
                    listDirectoryAppend(nome, lista);
                }
            }
        } else {
            lista.add(new UploadDTO(dir.getName(), dir.getPath()));
        }
        return lista;
    }

    /**
     * Carrega as Visoes da Dinamic Views para instalação do sistema
     *
     * @throws ServiceException
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    public void carregaVisoes(final HttpServletRequest request) throws ServiceException, Exception {
        final DataBaseMetaDadosService dataBaseMetaDadosService = (DataBaseMetaDadosService) ServiceLocator.getInstance().getService(DataBaseMetaDadosService.class, null);

        final Collection colection = dataBaseMetaDadosService.getDataBaseMetaDadosUtil();
        if (colection != null && !colection.isEmpty()) {
            /* Carregando metaDados */
            dataBaseMetaDadosService.carregaTodosMetaDados(colection);

            final List<UploadDTO> lista = new ArrayList<UploadDTO>();
            listDirectoryAppend(new File(CITCorporeUtil.CAMINHO_REAL_APP + "/visoesExportadas"), lista);

            this.importaVisoes(lista);
        } else {
            throw new Exception(UtilI18N.internacionaliza(request, "start.metaDadosException"));
        }
    }

    /**
     * Carrega rotina de script
     *
     * @throws ServiceException
     * @throws Exception
     */
    public void carregaScript() {
        try {
            final ScriptsService scriptsService = this.getScriptsService();
            System.out.println("CITSMART - Executando rotina de scripts... iniciando.");
            final String erro = scriptsService.executaRotinaScripts();
            if (erro != null && !erro.isEmpty()) {
                System.out.println("CITSMART - Problema ao executar rotina. Detalhes:\n" + erro);
            } else {
                System.out.println("CITSMART - Executando rotina de scripts... pronto.");
            }
            final VersaoService versaoService = this.getVersaoService();
            FiltroSegurancaCITSmart.setHaVersoesSemValidacao(versaoService.haVersoesSemValidacao());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param arquivosUpados
     * @throws Exception
     * @throws ServiceException
     */
    public void importaVisoes(final List<UploadDTO> arquivosUpados) throws ServiceException, Exception {
        final VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
        FileReader reader = null;
        VisaoDTO visaoAtual = null;
        VisaoDTO visaoDtoXML = new VisaoDTO();

        int countImport = 0;
        int countAtualiza = 0;

        if (arquivosUpados != null && arquivosUpados.size() > 0) {

            for (final UploadDTO uploadDTO : arquivosUpados) {
                final String path = uploadDTO.getPath();
                if (path != null && !path.isEmpty()) {
                    try {
                        reader = new FileReader(path);
                    } catch (final FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    final XStream x = new XStream(new DomDriver("ISO-8859-1"));
                    visaoDtoXML = (VisaoDTO) x.fromXML(reader);
                    visaoAtual = visaoService.visaoExistente(visaoDtoXML.getIdentificador());
                    // Determina o tipo da importação
                    if (visaoAtual == null) {
                        try {
                            visaoService.importar(visaoDtoXML);
                            countImport++;
                        } catch (final Exception e) {
                            System.out.println("Erro ao importar visão: " + visaoDtoXML.getIdentificador());
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            visaoService.atualizarVisao(visaoAtual, visaoDtoXML);
                            countAtualiza++;
                        } catch (final Exception e) {
                            System.out.println("Erro ao atualizar visão: " + visaoDtoXML.getIdentificador());
                            e.printStackTrace();
                        }
                    }
                }
            }
            System.out.println("Visões importadas com sucesso: " + countImport + "\n" + "Visões atualizadas com sucesso: " + countAtualiza);

        } else {
            System.out.println("Nenhum arquivo foi selecionado!");
        }

    }

    private String escape(final String valor) {
        return StringEscapeUtils.escapeJavaScript(valor);
    }

    private String escapeValor(final ParametroSistema parametro, final String df) throws Exception {
        return ParametroUtil.getValorParametroCitSmartHashMap(parametro, df);
    }

    private String escapeValor(final ParametroSistema parametro) throws Exception {
        return this.escape(ParametroUtil.getValorParametroCitSmartHashMap(parametro, DEFAULT));
    }

    public void internacionaliza(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final StartDTO bean = (StartDTO) document.getBean();

        final String idiomaPadrao = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.IDIOMAPADRAO, "");

        request.getSession(true).setAttribute("menu", null);
        request.getSession(true).setAttribute("menuPadrao", null);

        if (bean != null) {
            if (bean.getLocale() != null) {
                WebUtil.setLocale(bean.getLocale().trim(), request);
                XmlReadLookup.getInstance(new Locale(bean.getLocale().trim()));
            } else {
                WebUtil.setLocale(idiomaPadrao, request);
                XmlReadLookup.getInstance(new Locale(idiomaPadrao));
            }
        } else {
            WebUtil.setLocale(idiomaPadrao, request);
            XmlReadLookup.getInstance(new Locale(idiomaPadrao));
        }
        document.executeScript("window.location.reload()");
    }

    public void buscaHistoricoPorVersao(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final StartDTO startDTO = (StartDTO) document.getBean();

        String locale = UtilI18N.PORTUGUESE_SIGLA;
        final HttpSession session = request.getSession();
        if (session.getAttribute("locale") != null && StringUtils.isNotBlank(session.getAttribute("locale").toString())) {
            locale = session.getAttribute("locale").toString();
        }

        Reader reader = null;

        final String separator = System.getProperty("file.separator");
        final String path = CITCorporeUtil.CAMINHO_REAL_APP + "XMLs" + separator + "release_" + locale + ".xml";

        try {
            reader = new InputStreamReader(new FileInputStream(path), "ISO-8859-1");
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }

        final XStream x = new XStream(new DomDriver("ISO-8859-1"));
        final Collection<ReleaseDTO> listRelease = (Collection<ReleaseDTO>) x.fromXML(reader);

        try {
            if (reader != null) {
                reader.close();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        if (listRelease != null && !listRelease.isEmpty()) {
            final StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append("<div id='historicoRelease' style='overflow: auto;'>");

            int countRelease = 0;

            for (final ReleaseDTO releaseDto : listRelease) {
                if (startDTO.getVersao().equals(releaseDto.getVersao())) {
                    stringBuilder.append("<div id='release" + countRelease + "' style='width: 100%; text-align: justify;'>");
                    stringBuilder.append("<div>");
                    stringBuilder.append("<br>");

                    if (releaseDto.getConteudo() != null && !releaseDto.getConteudo().isEmpty()) {
                        int i = 0;
                        for (final String item : releaseDto.getConteudo()) {
                            ++i;
                            stringBuilder.append("<div>");
                            stringBuilder.append("<span  style='font-weight:bold;'>");
                            stringBuilder.append(i);
                            stringBuilder.append(" ");
                            stringBuilder.append("</span>");
                            stringBuilder.append(Util.encodeHTML(item));
                            stringBuilder.append("</div>");
                            stringBuilder.append("<br>");
                        }
                    }
                    stringBuilder.append("</div>");
                    stringBuilder.append("</div>");

                    ++countRelease;
                }

                stringBuilder.append("</div>");

                document.getElementById("divRelease").setInnerHTML(stringBuilder.toString());
            }
        }
    }

    private InstalacaoService instalacaoService;
    private MenuService menuService;
    private ParametroCorporeService parametroCorporeService;
    private ScriptsService scriptsService;
    private VersaoService versaoService;

    private InstalacaoService getInstalacaoService() throws Exception {
        if (instalacaoService == null) {
            instalacaoService = (InstalacaoService) ServiceLocator.getInstance().getService(InstalacaoService.class, null);
        }
        return instalacaoService;
    }

    private MenuService getMenuService() throws Exception {
        if (menuService == null) {
            menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
        }
        return menuService;
    }

    private ParametroCorporeService getParametroCorporeService() throws Exception {
        if (parametroCorporeService == null) {
            parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
        }
        return parametroCorporeService;
    }

    private ScriptsService getScriptsService() throws Exception {
        if (scriptsService == null) {
            scriptsService = (ScriptsService) ServiceLocator.getInstance().getService(ScriptsService.class, null);
        }
        return scriptsService;
    }

    private VersaoService getVersaoService() throws Exception {
        if (versaoService == null) {
            versaoService = (VersaoService) ServiceLocator.getInstance().getService(VersaoService.class, null);
        }
        return versaoService;
    }

}
