package br.com.centralit.citsmart.rest.util;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.exception.ExceptionUtils;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.operation.IRestOperation;
import br.com.centralit.citsmart.rest.schema.CtMessage;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.centralit.citsmart.rest.service.RestExecutionService;
import br.com.centralit.citsmart.rest.service.RestLogService;
import br.com.centralit.citsmart.rest.service.RestOperationService;
import br.com.centralit.citsmart.rest.service.RestParameterService;
import br.com.centralit.citsmart.rest.service.RestPermissionService;
import br.com.centralit.citsmart.rest.service.RestSessionService;
import br.com.centralit.citsmart.rest.service.RestSessionServiceEjb;
import br.com.centralit.citsmart.rest.v2.util.RESTOperations;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.ReflectionUtils;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilI18N;

public class RestUtil {

    private static final Logger LOGGER = Logger.getLogger(RestUtil.class.getName());

    private RestUtil() {}

    public static String stackToString(final Exception e) {
        return "------\n" + ExceptionUtils.getFullStackTrace(e) + "------\n";
    }

    public static Usuario getUsuarioSistema(final RestSessionDTO restSessionDto) throws Exception {
        if (restSessionDto == null) {
            return null;
        }

        final Usuario usr = new Usuario();
        final UsuarioDTO usuario = (UsuarioDTO) restSessionDto.getHttpSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
        if (usuario != null) {
            if (restSessionDto.getHttpSession().getAttribute("locale") != null && !restSessionDto.getHttpSession().getAttribute("locale").equals("")) {
                usuario.setLocale((String) restSessionDto.getHttpSession().getAttribute("locale"));
            } else {
                usuario.setLocale("");
            }
            Reflexao.copyPropertyValues(usuario, usr);
        } else {
            return null;
        }

        return usr;
    }

    public static RestSessionService getRestSessionService(final RestSessionDTO restSessionDto) {
        final RestSessionService service = new RestSessionServiceEjb();
        return service;
    }

    public static RestOperationService getRestOperationService(final RestSessionDTO restSessionDto) {
        try {
            return (RestOperationService) ServiceLocator.getInstance().getService(RestOperationService.class, getUsuarioSistema(restSessionDto));
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    public static RestPermissionService getRestPermissionService(final RestSessionDTO restSessionDto) {
        try {
            return (RestPermissionService) ServiceLocator.getInstance().getService(RestPermissionService.class, getUsuarioSistema(restSessionDto));
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    public static RestExecutionService getRestExecutionService(final RestSessionDTO restSessionDto) {
        try {
            return (RestExecutionService) ServiceLocator.getInstance().getService(RestExecutionService.class, getUsuarioSistema(restSessionDto));
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    public static RestLogService getRestLogService(final RestSessionDTO restSessionDto) {
        try {
            return (RestLogService) ServiceLocator.getInstance().getService(RestLogService.class, getUsuarioSistema(restSessionDto));
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    public static RestParameterService getRestParameterService(final RestSessionDTO restSessionDto) {
        try {
            return (RestParameterService) ServiceLocator.getInstance().getService(RestParameterService.class, getUsuarioSistema(restSessionDto));
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    public static ExecucaoSolicitacaoService getExecucaoSolicitacaoService(final RestSessionDTO restSessionDto) {
        try {
            return (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, getUsuarioSistema(restSessionDto));
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    public static boolean isValidSession(final RestSessionDTO restSessionDto) {
        return restSessionDto != null && restSessionDto.isValid();
    }

    public static EmpregadoDTO getEmpregadoByLogin(final String login) {
        try {
            final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
            final UsuarioDTO usuarioDto = usuarioService.restoreByLogin(login);
            if (usuarioDto == null) {
                return null;
            }

            final EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
            return empregadoService.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
        } catch (final Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            return null;
        }
    }

    public static String i18nMessage(final RestSessionDTO restSessionDto, final String key) {
        return UtilI18N.internacionaliza(restSessionDto.getUser().getLocale(), key);
    }

    /**
     * Imprime em log mensagem de erro e lança {@link RuntimeException}
     *
     * @param logger
     *            {@link Logger} da classe
     * @param e
     *            {@link ServiceException}
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 03/10/2014
     */
    public static void handleServiceException(final Logger logger, final ServiceException e) {
        final String message = "Error on getting service: " + e.getMessage();
        logger.log(Level.SEVERE, e.getMessage(), e);
        throw new RuntimeException(message, e);
    }

    /**
     * Executa um método de acordo com os parâmetros configurados em {@link RESTOperations} para a {@code messageID}
     *
     * @param instance
     *            instância do objeto que implementa a ação
     * @param session
     *            sessão do usuário
     * @param restOperation
     * @param message
     *            objeto que contém a {@code messageID} para encontrar classe e método de execução
     * @return
     * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
     * @since 29/09/2014
     */
    public static <E extends IRestOperation<?, ?>> CtMessageResp execute(final E instance, final RestSessionDTO session, final RestOperationDTO restOperation,
            final CtMessage message) {
        final String messageId = message.getMessageID();
        final RESTOperations operation = RESTOperations.fromMessageId(messageId);
        final String methodName = operation.getMethodName();
        final Class<?>[] methodArgs = operation.getMethodArgs();
        final Method method = ReflectionUtils.findMethod(instance.getClass(), methodName, methodArgs);
        ReflectionUtils.makeAccessible(method);
        final Object[] parameters = ReflectionUtils.getListParameterForTypes(methodArgs, session, restOperation, message);
        return (CtMessageResp) ReflectionUtils.invokeMethod(method, instance, parameters);
    }

}
