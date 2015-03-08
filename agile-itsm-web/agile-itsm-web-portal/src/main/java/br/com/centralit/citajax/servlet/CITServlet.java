package br.com.centralit.citajax.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.centralit.citajax.config.CitAjaxConfig;
import br.com.centralit.citajax.config.RedirectItem;
import br.com.centralit.citajax.framework.CITAutoCompleteProcess;
import br.com.centralit.citajax.framework.CITFacadeProcess;
import br.com.centralit.citajax.framework.CITObjectProcess;
import br.com.centralit.citajax.html.ScriptExecute;
import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LookupDTO;
import br.com.citframework.excecao.DuplicateUniqueException;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.LookupProcessService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes", "unchecked"})
public class CITServlet extends HttpServlet {

    private static final long serialVersionUID = 7373420780718728389L;

    private static final Logger LOGGER = Logger.getLogger(CITServlet.class);

    /**
     * Processa as requisicoes.
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getRequestURI();
        String ext = "";

        String serverAdd = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS");
        if (serverAdd == null) {
            serverAdd = "";
        }
        br.com.citframework.util.Constantes.SERVER_ADDRESS = serverAdd;

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (final Exception e) {
            LOGGER.warn("ERRO AO ATRIBUIR UTF-8 no request! --> " + e.getMessage());
        }

        try {
            response.setCharacterEncoding("UTF-8");
        } catch (final Exception e) {
            LOGGER.warn("ERRO AO ATRIBUIR UTF-8 no response! --> " + e.getMessage());
        }

        final Boolean isIE = request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > -1;
        if (isIE != null && isIE) {
            // Quando for IE coloca na sessao que eh IE.
            request.getSession(true).setAttribute("isIE", "true");
        }

        try {
            if (pathInfo != null) {
                // Executa um acao
                ext = this.getObjectExt(pathInfo);
                ext = ext.replaceAll("#", ""); // Evita problemas com href="#"

                // Converte objetos Java em JavaScript.
                if (pathInfo.indexOf("objects/") > -1 || "JSX".equalsIgnoreCase(ext)) {
                    final CITObjectProcess citProcess = new CITObjectProcess();
                    String strResult;
                    try {
                        strResult = citProcess.process(pathInfo, this.getServletContext());
                    } catch (final Exception e) {
                        LOGGER.error(e.getMessage(), e);
                        throw new ServletException(e);
                    }
                    if (strResult != null) {
                        response.setContentType("text/javascript; charset=UTF-8");
                        try (final PrintWriter out = response.getWriter()) {
                            out.write(strResult);
                        }
                    }
                    return;
                }
                if (pathInfo.indexOf("ajaxFacade/") > -1) {
                    final CITObjectProcess citProcess = new CITObjectProcess();
                    String strResult;
                    try {
                        strResult = citProcess.process(pathInfo, this.getServletContext());
                    } catch (final Exception e) {
                        LOGGER.error(e.getMessage(), e);
                        throw new ServletException(e);
                    }
                    if (strResult != null) {
                        response.setContentType("text/javascript; charset=UTF-8");
                        try (final PrintWriter out = response.getWriter()) {
                            out.write(strResult);
                        }
                    }
                    return;
                }

                // Operacoes de CRUD - Manipulacao de dados
                if ("save".equalsIgnoreCase(ext) || "restore".equalsIgnoreCase(ext) || "event".equalsIgnoreCase(ext)) {
                    final CITFacadeProcess citFacadeProcess = new CITFacadeProcess();
                    String strResult;
                    try {
                        strResult = citFacadeProcess.process(pathInfo, this.getServletContext(), request, response);
                    } catch (final Exception e) {
                        LOGGER.error(e.getMessage(), e);
                        throw new ServletException(e);
                    }
                    if (strResult != null) {
                        response.setContentType("text/javascript; charset=UTF-8");
                        try (final PrintWriter out = response.getWriter()) {
                            if (out != null) {
                                out.write(strResult);
                            }
                        } catch (final IOException e) {
                            LOGGER.warn(e.getMessage(), e);
                        }
                    } else {
                        response.setContentType("text/javascript; charset=UTF-8");
                        try (final PrintWriter out = response.getWriter()) {
                            if (out != null) {
                                out.write("alert('Retorno vazio de chamada Ajax')");
                            }
                        } catch (final IOException e) {
                            LOGGER.warn(e.getMessage(), e);
                        }
                    }
                    return;
                }

                // Operacoes de Carregamento de paginas
                if ("load".equalsIgnoreCase(ext)) {
                    /**
                     * Verifica se o parametro de instalação está em sessão
                     */
                    final ServletContext context = request.getSession().getServletContext();
                    if (context.getAttribute("instalacao") != null) {
                        pathInfo = "/citsmart/pages/start/start.load";
                    }

                    final CITFacadeProcess citFacadeProcess = new CITFacadeProcess();
                    String strResult;
                    try {
                        strResult = citFacadeProcess.process(pathInfo, this.getServletContext(), request, response);
                    } catch (final Exception e) {
                        LOGGER.error(e.getMessage(), e);
                        throw new ServletException(e);
                    }
                    final String strForm = this.getObjectName(pathInfo);

                    request.getSession().setAttribute("retornoLoad" + strForm, strResult);
                    String url = "";

                    final CitAjaxConfig config = CitAjaxConfig.getInstance();
                    final RedirectItem redirectItem = config.getPathInConfig(pathInfo, request);
                    if (redirectItem != null) {
                        url = redirectItem.getPathOut();
                    } else {
                        if (Constantes.getValue("CAMINHO_PAGES") != null) {
                            url = Constantes.getValue("CAMINHO_PAGES") + "/pages/" + strForm + "/" + strForm + ".jsp";
                        } else {
                            url = "/pages/" + strForm + "/" + strForm + ".jsp";
                        }
                    }

                    LOGGER.debug("URL ENCAMINHAMENTO>>>: " + url);

                    final boolean inSession = request.getSession(false) != null;
                    if (!inSession) {
                        LOGGER.warn("ATENÇÃO >>> SESSÃO INVÁLIDA >>> URL >> " + pathInfo);
                    }

                    if (!response.isCommitted() && inSession) {
                        final RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                        dispatcher.forward(request, response);
                        return;
                    }
                    return;
                }

                // Auto complete
                if ("complete".equalsIgnoreCase(ext)) {
                    final CITAutoCompleteProcess citFacadeAutoComplete = new CITAutoCompleteProcess();
                    String strResult;
                    try {
                        strResult = citFacadeAutoComplete.process(pathInfo, this.getServletContext(), request, response);
                    } catch (final Exception e) {
                        LOGGER.error(e.getMessage(), e);
                        throw new ServletException(e);
                    }

                    response.setContentType("text/html; charset=ISO-8859-1");
                    try (final PrintWriter out = response.getWriter()) {
                        out.write(strResult);
                    }
                    return;
                }

                // Pega retorno da acao processada anteriormente
                if ("get".equalsIgnoreCase(ext)) {
                    final String strForm = this.getObjectName(pathInfo);
                    final String strResult = (String) request.getSession().getAttribute("retornoLoad" + strForm);
                    if (strResult != null) {
                        response.setContentType("text/javascript; charset=UTF-8");
                        try (final PrintWriter out = response.getWriter()) {
                            out.write(strResult);
                        }
                    } else {
                        response.setContentType("text/javascript; charset=UTF-8");
                        try (final PrintWriter out = response.getWriter()) {
                            if (out != null) {
                                out.write("alert('Retorno vazio de chamada Ajax')");
                            }
                        } catch (final IOException e) {
                            LOGGER.warn(e.getMessage(), e);
                        }
                    }
                }

                // Operacoes de Busca de Informacoes
                if ("find".equalsIgnoreCase(ext)) {
                    final String urlErro = "/pages/lookup/erro.jsp";
                    try {
                        final IDto user = (IDto) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
                        final LookupProcessService lookupService = (LookupProcessService) ServiceLocator.getInstance().getService(LookupProcessService.class, null);
                        final LookupDTO lookup = new LookupDTO();
                        lookup.setAcao(UtilStrings.decodeCaracteresEspeciais(request.getParameter("acao")));
                        lookup.setNomeLookup(UtilStrings.decodeCaracteresEspeciais(request.getParameter("nomeLookup")));
                        lookup.setParm1(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm1")));
                        lookup.setParm2(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm2")));
                        lookup.setParm3(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm3")));
                        lookup.setParm4(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm4")));
                        lookup.setParm5(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm5")));
                        lookup.setParm6(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm6")));
                        lookup.setParm7(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm7")));
                        lookup.setParm8(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm8")));
                        lookup.setParm9(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm9")));
                        lookup.setParm10(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm10")));
                        lookup.setCheckbox(UtilStrings.decodeCaracteresEspeciais(request.getParameter("checkbox")));
                        String paginacao = "";
                        if (request.getParameter("paginacao") != null) {
                            paginacao = UtilStrings.decodeCaracteresEspeciais(request.getParameter("paginacao"));
                        }
                        lookup.setPaginacao(paginacao);
                        lookup.setParmCount(Integer.parseInt("0" + UtilStrings.nullToVazio(request.getParameter("parmCount"))));
                        if (user != null) {
                            lookup.setUser(user);
                        }

                        Collection colRetorno = null;
                        colRetorno = lookupService.process(lookup, request);
                        request.setAttribute("retorno", colRetorno);

                        final String urlRedirecionarLookup = "/pages/lookup/retornoAjax.jsp";
                        if (Constantes.getValue("CAMINHO_PAGES") == null) {
                            System.out.println("############################################# CITAJAX ##############################################");
                            System.out.println("####################################################################################################");
                            System.out.println("##### ATENCAO: a configuracao da chave 'CAMINHO_PAGES' esta nula no arquivo de Constantes.properties");
                            System.out.println("##### Isto fara que o Lookup nao funcione corretamente!");
                            System.out.println("##### Redirecionamento do lookup: " + Constantes.getValue("CAMINHO_PAGES") + urlRedirecionarLookup);
                            System.out.println("####################################################################################################");
                        }

                        final boolean inSession = request.getSession(false) != null;
                        if (!inSession) {
                            LOGGER.warn("ATENÇÃO >>> SESSÃO INVÁLIDA >>> URL >> " + pathInfo);
                        }

                        final String url = Constantes.getValue("CAMINHO_PAGES") + urlRedirecionarLookup;
                        if (!response.isCommitted() && inSession) {
                            final RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                            dispatcher.forward(request, response);
                        }
                    } catch (final LogicException e) {
                        LOGGER.warn(e.getMessage(), e);
                        request.setAttribute("mensagem", UtilI18N.internacionaliza(request, e.getMessage().replaceAll("br.com.citframework.excecao.LogicException: ", "")));
                        if (Constantes.getValue("CAMINHO_PAGES") == null) {
                            System.out.println("############################################# CITAJAX ##############################################");
                            System.out.println("####################################################################################################");
                            System.out.println("##### ATENCAO: a configuracao da chave 'CAMINHO_PAGES' esta nula no arquivo de Constantes.properties");
                            System.out.println("##### Isto fara que o Lookup nao funcione corretamente!");
                            System.out.println("##### Redirecionamento para erro: " + Constantes.getValue("CAMINHO_PAGES") + urlErro);
                            System.out.println("####################################################################################################");
                        }
                        final boolean inSession = request.getSession(false) != null;
                        if (!inSession) {
                            LOGGER.warn("ATENÇÃO >>> SESSÃO INVÁLIDA >>> URL >> " + pathInfo);
                        }

                        final String url = Constantes.getValue("CAMINHO_PAGES") + urlErro;
                        if (!response.isCommitted() && inSession) {
                            final RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                            dispatcher.forward(request, response);
                        }
                    } catch (final ServiceException e) {
                        LOGGER.warn(e.getMessage(), e);
                        if (Constantes.getValue("CAMINHO_PAGES") == null) {
                            System.out.println("############################################# CITAJAX ##############################################");
                            System.out.println("####################################################################################################");
                            System.out.println("##### ATENCAO: a configuracao da chave 'CAMINHO_PAGES' esta nula no arquivo de Constantes.properties");
                            System.out.println("##### Isto fara que o Lookup nao funcione corretamente!");
                            System.out.println("##### Redirecionamento para erro: " + Constantes.getValue("CAMINHO_PAGES") + urlErro);
                            System.out.println("####################################################################################################");
                        }
                        final boolean inSession = request.getSession(false) != null;
                        if (!inSession) {
                            LOGGER.warn("ATENÇÃO >>> SESSÃO INVÁLIDA >>> URL >> " + pathInfo);
                        }

                        final String url = Constantes.getValue("CAMINHO_PAGES") + urlErro;
                        if (!response.isCommitted() && inSession) {
                            final RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                            dispatcher.forward(request, response);
                        }
                    } catch (final Exception e) {
                        LOGGER.warn(e.getMessage(), e);
                        if (Constantes.getValue("CAMINHO_PAGES") == null) {
                            System.out.println("############################################# CITAJAX ##############################################");
                            System.out.println("####################################################################################################");
                            System.out.println("##### ATENCAO: a configuracao da chave 'CAMINHO_PAGES' esta nula no arquivo de Constantes.properties");
                            System.out.println("##### Isto fara que o Lookup nao funcione corretamente!");
                            System.out.println("##### Redirecionamento para erro: " + Constantes.getValue("CAMINHO_PAGES") + urlErro);
                            System.out.println("####################################################################################################");
                        }
                        final boolean inSession = request.getSession(false) != null;
                        if (!inSession) {
                            LOGGER.warn("ATENÇÃO >>> SESSÃO INVÁLIDA >>> URL >> " + pathInfo);
                        }

                        final String url = Constantes.getValue("CAMINHO_PAGES") + urlErro;
                        if (!response.isCommitted() && inSession) {
                            final RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                            dispatcher.forward(request, response);
                        }
                    }

                }
            }
        } catch (final Exception e) {
            LOGGER.warn(e.getMessage(), e);

            String mensagemErro = e.getMessage();
            Throwable ex = e;
            while (ex.getCause() != null) {
                if (ex.getCause() != null) {
                    ex = ex.getCause();
                }
            }
            if (ex != null && ex.getMessage() != null) {
                mensagemErro = ex.getMessage();
            }
            if (mensagemErro == null) {
                mensagemErro = "";
            }

            mensagemErro = mensagemErro.replaceAll("br\\.com\\.citframework\\.excecao\\.FKReferenceException\\:", "");
            mensagemErro = mensagemErro.replaceAll("br\\.com\\.citframework\\.excecao\\.LogicException\\:", "");
            mensagemErro = mensagemErro.replaceAll("br\\.com\\.centralit\\.citcorpore\\.exception\\.LogicException\\:", "");
            mensagemErro = mensagemErro.replaceAll("br\\.com\\.centralit\\.citajax\\.exception\\.LogicException\\:", "");
            mensagemErro = mensagemErro.replaceAll("br\\.com\\.citframework\\.excecao\\.DuplicateUniqueException\\:", "");
            mensagemErro = UtilI18N.internacionaliza(request, mensagemErro);
            String exec = "";
            final String strForm = this.getObjectName(pathInfo);
            if (!LogicException.class.isInstance(ex)) {
                exec = "Ocorreu um problema na execução: " + mensagemErro + ", contate a equipe de suporte do Citsmart";
            } else if (DuplicateUniqueException.class.isInstance(ex)) {
                exec = "Já existe um campo com este :" + mensagemErro;
            } else {
                exec = mensagemErro;
            }
            if (mensagemErro.indexOf("ORA-") > -1) {
                exec = "Problemas no Banco de dados: " + mensagemErro + ", contate a equipe de suporte do Citsmart";
            }
            if (mensagemErro.indexOf("connection") > -1) {
                exec = "Problemas na Conexão com o Banco de dados: " + mensagemErro + ", contate a equipe de suporte do Citsmart";
            }
            exec = exec.replaceAll("'", "\"");
            if ("load".equalsIgnoreCase(ext)) {
                final Collection colRetorno = new ArrayList();
                ScriptExecute script = new ScriptExecute();

                script.setScript("try{JANELA_AGUARDE_MENU.hide();}catch(ex){}");
                colRetorno.add(script);
                script = new ScriptExecute();
                script.setScript("alert('" + CitAjaxWebUtil.codificaEnterByChar(exec, "") + "')");
                colRetorno.add(script);

                String strResult = "";
                try {
                    strResult = CitAjaxWebUtil.serializeObjects(colRetorno, true, CitAjaxWebUtil.getLanguage(request));
                } catch (final Exception e1) {
                    strResult = "";
                }

                final boolean inSession = request.getSession(false) != null;
                if (!inSession) {
                    LOGGER.warn("ATENÇÃO >>> SESSÃO INVÁLIDA >>> URL >> " + pathInfo);
                }

                if (!response.isCommitted() && inSession) {
                    request.getSession().setAttribute("retornoLoad" + strForm, strResult);
                }

                CitAjaxConfig config = null;
                try {
                    config = CitAjaxConfig.getInstance();
                } catch (final Exception t) {
                    LOGGER.warn(t.getMessage(), t);
                }

                if (config != null) {
                    String url = "";
                    final RedirectItem redirectItem = config.getPathInConfig(pathInfo, request);
                    if (redirectItem != null) {
                        url = redirectItem.getPathOut();
                    } else {
                        if (Constantes.getValue("CAMINHO_PAGES") != null) {
                            url = Constantes.getValue("CAMINHO_PAGES") + "/pages/" + strForm + "/" + strForm + ".jsp";
                        } else {
                            url = "/pages/" + strForm + "/" + strForm + ".jsp";
                        }
                    }
                    LOGGER.debug("URL ENCAMINHAMENTO>>>: " + url);

                    if (!response.isCommitted() && inSession) {
                        final RequestDispatcher dispatcher = request.getRequestDispatcher(url);
                        dispatcher.forward(request, response);
                    }
                }
                return;
            } else {
                final Collection colRetorno = new ArrayList();
                ScriptExecute script = new ScriptExecute();

                script.setScript("try{JANELA_AGUARDE_MENU.hide();}catch(ex){}");
                colRetorno.add(script);
                exec = exec.replaceAll("'", "\"");
                script = new ScriptExecute();
                script.setScript("alert('" + CitAjaxWebUtil.codificaEnterByChar(exec, "") + "')");
                colRetorno.add(script);
                try (final PrintWriter out = response.getWriter()) {
                    if (out != null) {
                        out.write(CitAjaxWebUtil.serializeObjects(colRetorno, true, CitAjaxWebUtil.getLanguage(request)));
                    }
                } catch (final Exception t) {
                    LOGGER.warn(t.getMessage(), t);
                }
                response.setContentType("text/javascript; charset=UTF-8");
            }
        }
    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        this.processRequest(request, response);
    }

    public String getObjectName(final String path) {
        String strResult = "";
        boolean b = false;
        for (int i = path.length() - 1; i >= 0; i--) {
            if (b) {
                if (path.charAt(i) == '/') {
                    return strResult;
                } else {
                    strResult = path.charAt(i) + strResult;
                }
            } else {
                if (path.charAt(i) == '.') {
                    b = true;
                }
            }
        }
        return strResult;
    }

    public String getObjectExt(final String path) {
        String strResult = "";
        for (int i = path.length() - 1; i >= 0; i--) {
            if (path.charAt(i) == '.') {
                return strResult;
            } else {
                strResult = path.charAt(i) + strResult;
            }
        }
        return strResult;
    }

}
