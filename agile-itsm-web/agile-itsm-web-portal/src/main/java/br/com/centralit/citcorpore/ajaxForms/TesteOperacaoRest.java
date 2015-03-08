package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXB;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.TesteOperacaoRestDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citsmart.rest.schema.CtAddServiceRequest;
import br.com.centralit.citsmart.rest.schema.CtAddServiceRequestResp;
import br.com.centralit.citsmart.rest.schema.CtListTasks;
import br.com.centralit.citsmart.rest.schema.CtListTasksResp;
import br.com.centralit.citsmart.rest.schema.CtLogin;
import br.com.centralit.citsmart.rest.schema.CtLoginResp;
import br.com.centralit.citsmart.rest.schema.CtNotificationFeedback;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetById;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetByUser;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetReasons;
import br.com.centralit.citsmart.rest.schema.CtNotificationGetReasonsResp;
import br.com.centralit.citsmart.rest.schema.CtNotificationNew;
import br.com.centralit.citsmart.rest.schema.CtService;
import br.com.centralit.citsmart.rest.schema.CtServiceRequest;
import br.com.centralit.citsmart.rest.schema.StServiceRequestPriority;
import br.com.centralit.citsmart.rest.schema.StServiceRequestType;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilXMLDate;

import com.google.gson.Gson;

public class TesteOperacaoRest extends AjaxFormAction {

    @Override
    public void load(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        final String urlSistema = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO");
        document.executeScript("document.form.url.value = '" + urlSistema + "';");

        final ServicoContratoService servicoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class,
                WebUtil.getUsuarioSistema(request));
        final HTMLSelect idServico = document.getSelectById("idServico");
        idServico.removeAllOptions();
        idServico.addOption("", "-- Selecione --");
        final Collection<ServicoContratoDTO> colServicos = servicoService.findServicoContratoByIdContrato(new Integer(1));
        if (colServicos != null && !colServicos.isEmpty()) {
            for (final ServicoContratoDTO servicoDto : colServicos) {
                idServico.addOption("" + servicoDto.getIdServico(), servicoDto.getNomeServico() + " (" + servicoDto.getIdServico() + ")");
            }
        }

        final UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, WebUtil.getUsuarioSistema(request));
        final HTMLSelect loginSolicitante = document.getSelectById("loginSolicitante");
        loginSolicitante.removeAllOptions();
        loginSolicitante.addOption("", "-- Selecione --");
        final Collection colUsuarios = usuarioService.listAtivos();
        if (colUsuarios != null && !colUsuarios.isEmpty()) {
            loginSolicitante.addOptions(colUsuarios, "login", "nomeUsuario", null);
        }

        final HTMLSelect tipo = document.getSelectById("tipo");
        tipo.removeAllOptions();
        tipo.addOption("", "-- Selecione --");
        tipo.addOption("R", "Requisição");
        tipo.addOption("I", "Incidente");

        final HTMLSelect formatoSaida = document.getSelectById("formatoSaida");
        formatoSaida.addOption("XML", "XML");
        formatoSaida.addOption("JSON", "JSON");

        final HTMLSelect impacto = document.getSelectById("impacto");
        impacto.removeAllOptions();
        impacto.addOption("", "-- Selecione --");
        impacto.addOption("L", "Baixo");
        impacto.addOption("M", "Médio");
        impacto.addOption("H", "Alto");

        final HTMLSelect urgencia = document.getSelectById("urgencia");
        urgencia.removeAllOptions();
        urgencia.addOption("", "-- Selecione --");
        urgencia.addOption("L", "Baixa");
        urgencia.addOption("M", "Média");
        urgencia.addOption("H", "Alta");
    }

    public void autentica(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }

            final TesteOperacaoRestDTO operacaoDto = (TesteOperacaoRestDTO) document.getBean();

            final CtLogin login = new CtLogin();
            login.setUserName(operacaoDto.getLoginUsuario());
            login.setPassword(operacaoDto.getSenha());

            final String input = new Gson().toJson(login);

            final ClientRequest clientRequest = new ClientRequest(operacaoDto.getUrl() + "/services/login");

            clientRequest.body(MediaType.APPLICATION_JSON, input);
            clientRequest.accept(MediaType.APPLICATION_JSON);

            String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

            File fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
            fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            final String complem = "" + UtilDatas.getDataHoraAtual().getTime();
            final String fileName = strTempUpload + "/RESP" + complem + ".JSON";
            final String caminhoRelativo = Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + usuario.getIdEmpresa() + "/RESP" + complem + ".JSON";

            try (final OutputStream os = new FileOutputStream(fileName)) {
                final ClientResponse<String> clientResponse = clientRequest.post(String.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final String saida = clientResponse.getEntity();
                System.out.println("###### SAIDA: " + saida);
                final String str = "<html><head></head><body><table><tr><td colspan='2'><b>/services/login</b></td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Parâmetros:</b></td><td>"
                        + input + "</td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Saída:</b></td><td>" + saida + "</td></tr></table></body></html>";
                os.write(str.getBytes());

                document.executeScript("window.open('" + caminhoRelativo + "')");

                final CtLoginResp resp = new Gson().fromJson(saida, CtLoginResp.class);
                document.executeScript("document.form.idSessao.value = '" + UtilStrings.nullToVazio(resp.getSessionID()) + "';");
            }
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    public void save(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }

            final TesteOperacaoRestDTO operacaoDto = (TesteOperacaoRestDTO) document.getBean();

            final CtAddServiceRequest addServiceRequest = new CtAddServiceRequest();
            final CtServiceRequest serviceRequest = new CtServiceRequest();
            final CtService service = new CtService();

            // -- Atribui a sessão
            addServiceRequest.setSessionID(operacaoDto.getIdSessao());
            addServiceRequest.setMessageID("addServiceRequest");

            // -- Atributos obrigatórios
            final ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, WebUtil.getUsuarioSistema(request));
            ServicoDTO servicoDto = new ServicoDTO();
            servicoDto.setIdServico(operacaoDto.getIdServico());
            servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

            service.setCode("" + operacaoDto.getIdServico());
            service.setDescription(servicoDto.getNomeServico());
            serviceRequest.setService(service);

            serviceRequest.setNumber(operacaoDto.getNumero());
            serviceRequest.setStartDateTime(UtilXMLDate.toXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));
            serviceRequest.setDescription(operacaoDto.getDescricao());
            serviceRequest.setType(StServiceRequestType.valueOf(operacaoDto.getTipo()));
            serviceRequest.setUserID(operacaoDto.getLoginSolicitante());
            serviceRequest.setImpact(StServiceRequestPriority.valueOf(operacaoDto.getImpacto()));
            serviceRequest.setUrgency(StServiceRequestPriority.valueOf(operacaoDto.getUrgencia()));

            addServiceRequest.setServiceRequestSource(serviceRequest);

            final ClientRequest clientRequest = new ClientRequest(operacaoDto.getUrl() + "/services/execute");

            clientRequest.body(MediaType.APPLICATION_XML, addServiceRequest);
            if (operacaoDto.getFormatoSaida().equalsIgnoreCase("JSON")) {
                clientRequest.accept(MediaType.APPLICATION_JSON);
            } else if (operacaoDto.getFormatoSaida().equalsIgnoreCase("HTML")) {
                clientRequest.accept(MediaType.APPLICATION_XHTML_XML);
            } else {
                clientRequest.accept(MediaType.APPLICATION_XML);
            }

            String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

            File fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
            fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            final String fileName = strTempUpload + "/RESP." + operacaoDto.getFormatoSaida();
            final String caminhoRelativo = Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + usuario.getIdEmpresa() + "/RESP." + operacaoDto.getFormatoSaida();
            final OutputStream os = new FileOutputStream(fileName);

            if (operacaoDto.getFormatoSaida().equals("XML")) {
                final ClientResponse<CtAddServiceRequestResp> clientResponse = clientRequest.post(CtAddServiceRequestResp.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final CtAddServiceRequestResp resp = clientResponse.getEntity();
                JAXB.marshal(resp, os);
            } else {
                final ClientResponse<String> clientResponse = clientRequest.post(String.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final String str = "<html><head></head><body>" + clientResponse.getEntity() + "</body></html>";
                os.write(str.getBytes());
            }

            document.executeScript("window.open('" + caminhoRelativo + "')");
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    public void addServiceRequestPortal(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }

            final TesteOperacaoRestDTO operacaoDto = (TesteOperacaoRestDTO) document.getBean();

            final CtAddServiceRequest addServiceRequest = new CtAddServiceRequest();
            final CtServiceRequest serviceRequest = new CtServiceRequest();
            final CtService service = new CtService();

            // -- Atribui a sessão
            addServiceRequest.setSessionID(operacaoDto.getIdSessao());
            addServiceRequest.setMessageID("addServiceRequestPortal");

            serviceRequest.setDescription(operacaoDto.getDescricaoPortal());
            serviceRequest.setUserID(operacaoDto.getLoginSolicitante());
            serviceRequest.setService(service);

            addServiceRequest.setServiceRequestSource(serviceRequest);

            final ClientRequest clientRequest = new ClientRequest(operacaoDto.getUrl() + "/services/execute");

            clientRequest.body(MediaType.APPLICATION_XML, addServiceRequest);
            if (operacaoDto.getFormatoSaida().equalsIgnoreCase("JSON")) {
                clientRequest.accept(MediaType.APPLICATION_JSON);
            } else if (operacaoDto.getFormatoSaida().equalsIgnoreCase("HTML")) {
                clientRequest.accept(MediaType.APPLICATION_XHTML_XML);
            } else {
                clientRequest.accept(MediaType.APPLICATION_XML);
            }

            String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

            File fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
            fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            final String fileName = strTempUpload + "/RESP." + operacaoDto.getFormatoSaida();
            final String caminhoRelativo = Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + usuario.getIdEmpresa() + "/RESP." + operacaoDto.getFormatoSaida();
            final OutputStream os = new FileOutputStream(fileName);

            if (operacaoDto.getFormatoSaida().equals("XML")) {
                final ClientResponse<CtAddServiceRequestResp> clientResponse = clientRequest.post(CtAddServiceRequestResp.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final CtAddServiceRequestResp resp = clientResponse.getEntity();
                JAXB.marshal(resp, os);
            } else {
                final ClientResponse<String> clientResponse = clientRequest.post(String.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final String str = "<html><head></head><body>" + clientResponse.getEntity() + "</body></html>";
                os.write(str.getBytes());
            }

            document.executeScript("window.open('" + caminhoRelativo + "')");
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    public void listTasks(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }

            final TesteOperacaoRestDTO operacaoDto = (TesteOperacaoRestDTO) document.getBean();

            final CtListTasks listTasks = new CtListTasks();
            listTasks.setListarCompras(operacaoDto.getListaCompras());
            listTasks.setListarIncidentes(operacaoDto.getListaIncidentes());
            listTasks.setListarRequisicoes(operacaoDto.getListaRequisicoes());
            listTasks.setListarRH(operacaoDto.getListaRH());
            listTasks.setListarViagens(operacaoDto.getListaViagens());

            // -- Atribui a sessão
            listTasks.setSessionID(operacaoDto.getIdSessao());
            listTasks.setMessageID("listTasks");

            final ClientRequest clientRequest = new ClientRequest(operacaoDto.getUrl() + "/services/execute");

            clientRequest.body(MediaType.APPLICATION_XML, listTasks);
            if (operacaoDto.getFormatoSaida().equalsIgnoreCase("JSON")) {
                clientRequest.accept(MediaType.APPLICATION_JSON);
            } else if (operacaoDto.getFormatoSaida().equalsIgnoreCase("HTML")) {
                clientRequest.accept(MediaType.APPLICATION_XHTML_XML);
            } else {
                clientRequest.accept(MediaType.APPLICATION_XML);
            }

            String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

            File fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
            fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            final String fileName = strTempUpload + "/RESP." + operacaoDto.getFormatoSaida();
            final String caminhoRelativo = Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + usuario.getIdEmpresa() + "/RESP." + operacaoDto.getFormatoSaida();
            final OutputStream os = new FileOutputStream(fileName);

            if (operacaoDto.getFormatoSaida().equals("XML")) {
                final ClientResponse<CtListTasksResp> clientResponse = clientRequest.post(CtListTasksResp.class);
                // ClientResponse<CtListObjects> clientResponse = clientRequest.post(CtListObjects.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final CtListTasksResp resp = clientResponse.getEntity();
                // CtListObjects resp = clientResponse.getEntity();
                JAXB.marshal(resp, os);
            } else {
                final ClientResponse<String> clientResponse = clientRequest.post(String.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final String str = "<html><head></head><body>" + clientResponse.getEntity() + "</body></html>";
                os.write(str.getBytes());
            }

            document.executeScript("window.open('" + caminhoRelativo + "')");
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    public void notification_getByUser(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }

            final TesteOperacaoRestDTO operacaoDto = (TesteOperacaoRestDTO) document.getBean();

            final CtNotificationGetByUser getByUser = new CtNotificationGetByUser();
            getByUser.setNotificationType(operacaoDto.getTipoListagem());

            // -- Atribui a sessão
            getByUser.setSessionID(operacaoDto.getIdSessao());
            getByUser.setOnlyApproval(operacaoDto.getSomenteEmAprovacao());

            final String input = new Gson().toJson(getByUser);

            final ClientRequest clientRequest = new ClientRequest(operacaoDto.getUrl() + "/mobile/notification/getByUser");

            clientRequest.body(MediaType.APPLICATION_JSON, input);
            clientRequest.accept(MediaType.APPLICATION_JSON);

            String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

            File fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
            fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            final String complem = "" + UtilDatas.getDataHoraAtual().getTime();
            final String fileName = strTempUpload + "/RESP" + complem + ".JSON";
            final String caminhoRelativo = Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + usuario.getIdEmpresa() + "/RESP" + complem + ".JSON";

            try (final OutputStream os = new FileOutputStream(fileName)) {
                final ClientResponse<String> clientResponse = clientRequest.post(String.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final String saida = clientResponse.getEntity();
                System.out.println("###### SAIDA: " + saida);
                final String str = "<html><head></head><body><table><tr><td colspan='2'><b>/mobile/notification/getByUser</b></td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Parâmetros:</b></td><td>"
                        + input + "</td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Saída:</b></td><td>" + saida + "</td></tr></table></body></html>";
                os.write(str.getBytes());
            }

            document.executeScript("window.open('" + caminhoRelativo + "')");
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    public void notification_getById(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }

            final TesteOperacaoRestDTO operacaoDto = (TesteOperacaoRestDTO) document.getBean();

            final CtNotificationGetById getById = new CtNotificationGetById();
            getById.setTaskId(new BigInteger("" + operacaoDto.getIdTarefa()));

            // -- Atribui a sessão
            getById.setSessionID(operacaoDto.getIdSessao());

            final String input = new Gson().toJson(getById);

            final ClientRequest clientRequest = new ClientRequest(operacaoDto.getUrl() + "/mobile/notification/getById");

            clientRequest.body(MediaType.APPLICATION_JSON, input);
            clientRequest.accept(MediaType.APPLICATION_JSON);

            String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

            File fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
            fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            final String complem = "" + UtilDatas.getDataHoraAtual().getTime();
            final String fileName = strTempUpload + "/RESP" + complem + ".JSON";
            final String caminhoRelativo = Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + usuario.getIdEmpresa() + "/RESP" + complem + ".JSON";

            try (final OutputStream os = new FileOutputStream(fileName)) {
                final ClientResponse<String> clientResponse = clientRequest.post(String.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final String str = "<html><head></head><body><table><tr><td colspan='2'><b>/mobile/notification/getById</b></td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Parâmetros:</b></td><td>"
                        + input + "</td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Saída:</b></td><td>" + clientResponse.getEntity() + "</td></tr></table></body></html>";
                os.write(str.getBytes());
            }

            document.executeScript("window.open('" + caminhoRelativo + "')");
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    public void notification_new(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }

            final TesteOperacaoRestDTO operacaoDto = (TesteOperacaoRestDTO) document.getBean();

            final CtNotificationNew newNotif = new CtNotificationNew();
            newNotif.setDescription(operacaoDto.getDescricaoPortal());

            // -- Atribui a sessão
            newNotif.setSessionID(operacaoDto.getIdSessao());

            final String input = new Gson().toJson(newNotif);

            final ClientRequest clientRequest = new ClientRequest(operacaoDto.getUrl() + "/mobile/notification/new");

            clientRequest.body(MediaType.APPLICATION_JSON, input);
            clientRequest.accept(MediaType.APPLICATION_JSON);

            String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

            File fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
            fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            final String complem = "" + UtilDatas.getDataHoraAtual().getTime();
            final String fileName = strTempUpload + "/RESP" + complem + ".JSON";
            final String caminhoRelativo = Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + usuario.getIdEmpresa() + "/RESP" + complem + ".JSON";

            try (final OutputStream os = new FileOutputStream(fileName)) {
                final ClientResponse<String> clientResponse = clientRequest.post(String.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final String str = "<html><head></head><body><table><tr><td colspan='2'><b>/mobile/notification/new</b></td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Parâmetros:</b></td><td>"
                        + input + "</td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Saída:</b></td><td>" + clientResponse.getEntity() + "</td></tr></table></body></html>";
                os.write(str.getBytes());
            }

            document.executeScript("window.open('" + caminhoRelativo + "')");
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    public void notification_getReasons(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }

            final TesteOperacaoRestDTO operacaoDto = (TesteOperacaoRestDTO) document.getBean();

            final CtNotificationGetReasons getReasons = new CtNotificationGetReasons();
            getReasons.setTaskId(new BigInteger("" + operacaoDto.getIdTarefa()));

            // -- Atribui a sessão
            getReasons.setSessionID(operacaoDto.getIdSessao());

            final String input = new Gson().toJson(getReasons);

            final ClientRequest clientRequest = new ClientRequest(operacaoDto.getUrl() + "/mobile/notification/getReasons");

            clientRequest.body(MediaType.APPLICATION_JSON, input);
            clientRequest.accept(MediaType.APPLICATION_JSON);

            String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

            File fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
            fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            final String complem = "" + UtilDatas.getDataHoraAtual().getTime();
            final String fileName = strTempUpload + "/RESP" + complem + ".JSON";
            final String caminhoRelativo = Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + usuario.getIdEmpresa() + "/RESP" + complem + ".JSON";

            try (final OutputStream os = new FileOutputStream(fileName)) {
                final ClientResponse<String> clientResponse = clientRequest.post(String.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final String str = "<html><head></head><body><table><tr><td colspan='2'><b>/mobile/notification/getReasons</b></td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Parâmetros:</b></td><td>"
                        + input + "</td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Saída:</b></td><td>" + clientResponse.getEntity() + "</td></tr></table></body></html>";
                os.write(str.getBytes());
            }

            document.executeScript("window.open('" + caminhoRelativo + "')");

            final HTMLSelect idJustificativa = document.getSelectById("idJustificativa");
            idJustificativa.removeAllOptions();
            idJustificativa.addOption("", "-- Selecione --");

            clientRequest.body(MediaType.APPLICATION_XML, getReasons);
            final ClientResponse<CtNotificationGetReasonsResp> clienteResponse2 = clientRequest.post(CtNotificationGetReasonsResp.class);
            if (clienteResponse2.getStatus() != 200 && clienteResponse2.getStatus() != 412) {
                throw new RuntimeException("Chamada falhou -> HTTP error code : " + clienteResponse2.getStatus() + clienteResponse2.getEntity());
            }

            final CtNotificationGetReasonsResp resp = clienteResponse2.getEntity();
            if (resp.getError() == null && resp.getReasons() != null) {
                idJustificativa.addOptions(resp.getReasons(), "id", "desc", null);
            }

        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    public void notification_feedback(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }

            final TesteOperacaoRestDTO operacaoDto = (TesteOperacaoRestDTO) document.getBean();

            final CtNotificationFeedback feedback = new CtNotificationFeedback();
            feedback.setTaskId(new BigInteger("" + operacaoDto.getIdTarefa()));
            if (operacaoDto.getIdJustificativa() != null) {
                feedback.setReasonId(new Integer(1));
            }
            feedback.setFeedback(operacaoDto.getFeedback());
            feedback.setComments("Comentarios");

            // -- Atribui a sessão
            feedback.setSessionID(operacaoDto.getIdSessao());

            final String input = new Gson().toJson(feedback);

            final ClientRequest clientRequest = new ClientRequest(operacaoDto.getUrl() + "/mobile/notification/feedback");

            clientRequest.body(MediaType.APPLICATION_JSON, input);
            clientRequest.accept(MediaType.APPLICATION_JSON);

            String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

            File fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
            fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            final String complem = "" + UtilDatas.getDataHoraAtual().getTime();
            final String fileName = strTempUpload + "/RESP" + complem + ".JSON";
            final String caminhoRelativo = Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + usuario.getIdEmpresa() + "/RESP" + complem + ".JSON";

            try (final OutputStream os = new FileOutputStream(fileName)) {
                final ClientResponse<String> clientResponse = clientRequest.post(String.class);
                if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                    throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
                }
                final String str = "<html><head></head><body><table><tr><td colspan='2'><b>/mobile/notification/feedback</b></td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Parâmetros:</b></td><td>"
                        + input + "</td></tr><tr><td>&nbsp;</td></tr><tr><td><b>Saída:</b></td><td>" + clientResponse.getEntity() + "</td></tr></table></body></html>";
                os.write(str.getBytes());
            }

            document.executeScript("window.open('" + caminhoRelativo + "')");
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    public void saveVersaoAnterior(final DocumentHTML document, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
        try {
            final UsuarioDTO usuario = WebUtil.getUsuario(request);
            if (usuario == null) {
                document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
                document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
                return;
            }

            final TesteOperacaoRestDTO operacaoDto = (TesteOperacaoRestDTO) document.getBean();

            final br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequest addServiceRequest = new br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequest();
            final CtServiceRequest serviceRequest = new CtServiceRequest();
            final CtService service = new CtService();

            // -- Atribui a sessão
            addServiceRequest.setSessionID(operacaoDto.getIdSessao());

            // -- Atributos obrigatórios
            final ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, WebUtil.getUsuarioSistema(request));
            ServicoDTO servicoDto = new ServicoDTO();
            servicoDto.setIdServico(operacaoDto.getIdServico());
            servicoDto = (ServicoDTO) servicoService.restore(servicoDto);

            service.setCode("" + operacaoDto.getIdServico());
            service.setDescription(servicoDto.getNomeServico());
            serviceRequest.setService(service);

            serviceRequest.setNumber(operacaoDto.getNumero());
            serviceRequest.setStartDateTime(UtilXMLDate.toXMLGregorianCalendar(UtilDatas.getDataHoraAtual()));
            serviceRequest.setDescription(operacaoDto.getDescricao());
            serviceRequest.setType(StServiceRequestType.valueOf(operacaoDto.getTipo()));
            serviceRequest.setUserID(operacaoDto.getLoginSolicitante());
            serviceRequest.setImpact(StServiceRequestPriority.valueOf(operacaoDto.getImpacto()));
            serviceRequest.setUrgency(StServiceRequestPriority.valueOf(operacaoDto.getUrgencia()));

            addServiceRequest.setServiceRequestSource(serviceRequest);

            final ClientRequest clientRequest = new ClientRequest(operacaoDto.getUrl() + "/services/addServiceRequest");

            clientRequest.body(MediaType.APPLICATION_XML, addServiceRequest);
            clientRequest.accept(MediaType.APPLICATION_JSON);

            final ClientResponse<br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequestResp> clientResponse = clientRequest
                    .post(br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequestResp.class);

            if (clientResponse.getStatus() != 200 && clientResponse.getStatus() != 412) {
                throw new RuntimeException("Chamada falhou -> HTTP error code : " + clientResponse.getStatus() + clientResponse.getEntity());
            }

            final br.com.centralit.citsmart.rest.schema.old.CtAddServiceRequestResp resp = clientResponse.getEntity();

            String strTempUpload = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload";

            File fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }
            strTempUpload = strTempUpload + "/" + usuario.getIdEmpresa();
            fileDir = new File(strTempUpload);
            if (!fileDir.exists()) {
                fileDir.mkdir();
            }

            final String fileName = strTempUpload + "/RESP.XML";
            final String caminhoRelativo = Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/" + usuario.getIdEmpresa() + "/RESP.XML";
            final OutputStream os = new FileOutputStream(fileName);
            JAXB.marshal(resp, os);

            document.executeScript("window.open('" + caminhoRelativo + "')");
        } finally {
            document.executeScript("JANELA_AGUARDE_MENU.hide();");
        }
    }

    @Override
    public Class<TesteOperacaoRestDTO> getBeanClass() {
        return TesteOperacaoRestDTO.class;
    }

}
