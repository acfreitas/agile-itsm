package br.com.centralit.citsmart.rest.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.LoginDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.ad.LDAPUtils;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoUsuarioService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtLogin;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.PersistenceEngine;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class RestSessionServiceEjb implements RestSessionService {

    private static final Map<String, RestSessionDTO> mapSessions = new HashMap<>();

    @Override
    public RestSessionDTO newSession(final HttpServletRequest httpRequest, final CtLogin login) throws Exception {
        this.authentication(httpRequest, login);

        final RestSessionDTO sessionDto = new RestSessionDTO();
        sessionDto.setCreation(UtilDatas.getDataHoraAtual());
        sessionDto.setHttpSession(httpRequest.getSession());

        sessionDto.setMaxTime(1800);
        mapSessions.put(sessionDto.getSessionID(), sessionDto);
        return sessionDto;
    }

    @Override
    public RestSessionDTO getSession(final String sessionID) {
        RestSessionDTO result = mapSessions.get(sessionID);
        if (result != null && !result.isValid()) {
            mapSessions.remove(result.getSessionID());
            result.setHttpSession(null);
            result = null;
        }
        return result;
    }

    public CtLogin authentication(final HttpServletRequest httpRequest, final CtLogin login) throws Exception {
        boolean isAdmin = false;
        if (login != null) {
            if (login.getPassword() == null || login.getPassword().trim().equalsIgnoreCase("")) {
                throw new LogicException(UtilI18N.internacionaliza(httpRequest, "bi.painelControle.conexao.senhaNaoInformada"));
            }
            if (login.getUserName() == null || login.getUserName().trim().equalsIgnoreCase("")) {
                throw new LogicException(UtilI18N.internacionaliza(httpRequest, "bi.painelControle.conexao.usuarioNaoInformado"));
            }
        } else {
            throw new LogicException(UtilI18N.internacionaliza(httpRequest, "bi.painelControle.conexao.usuarioNaoInformado"));
        }

        final UsuarioDTO usrDto = new UsuarioDTO();

        final String metodoAutenticacao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.METODO_AUTENTICACAO_Pasta, "2");

        isAdmin = "admin".equalsIgnoreCase(login.getUserName()) || "consultor".equalsIgnoreCase(login.getUserName());

        if (metodoAutenticacao != null && metodoAutenticacao.trim().equalsIgnoreCase("2") && !isAdmin) {
            final LoginDTO loginDto = new LoginDTO();
            loginDto.setUser(login.getUserName());
            loginDto.setSenha(login.getPassword());
            this.getUsuarioService().sincronizaUsuarioAD(LDAPUtils.autenticacaoAD(login.getUserName(), login.getPassword()), loginDto, false);
        }

        final boolean veririficaVazio = this.getUsuarioService().listSeVazio();

        String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
        if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {

            algoritmo = "SHA-1";
        }

        login.setPassword(CriptoUtils.generateHash(login.getPassword(), algoritmo));

        if (!veririficaVazio && "admin".equalsIgnoreCase(login.getUserName())) {
            usrDto.setDataInicio(UtilDatas.getDataAtual());
            usrDto.setLogin(login.getUserName());
            usrDto.setSenha(login.getPassword());
            usrDto.setNomeUsuario("Administrador");
            usrDto.setStatus("A");
            this.getUsuarioService().createFirs(usrDto);
        }
        final UsuarioDTO usuarioBean = this.getUsuarioService().restoreByLogin(login.getUserName(), login.getPassword());
        if (usuarioBean == null) {
            throw new LogicException(UtilI18N.internacionaliza(httpRequest, "bi.painelControle.conexao.usuarioSenhaInvalida"));
        }

        if (metodoAutenticacao == null || metodoAutenticacao.trim().equalsIgnoreCase("")) {
            throw new LogicException(UtilI18N.internacionaliza(httpRequest, "bi.painelControle.conexao.MetodoNaoConfigurado"));
        }

        if (metodoAutenticacao != null) {
            if (usuarioBean.getStatus().equalsIgnoreCase("A") && login.getPassword().equals(usuarioBean.getSenha())) {
                if (usuarioBean.getIdEmpresa() == null) {
                    usuarioBean.setIdEmpresa(1);
                }
                final Usuario usuarioFramework = new Usuario();
                final UsuarioDTO usr = new UsuarioDTO();
                usr.setIdUsuario(usuarioBean.getIdUsuario());
                usr.setNomeUsuario(usuarioBean.getNomeUsuario());
                usr.setIdGrupo(usuarioBean.getIdGrupo());
                usr.setIdEmpresa(usuarioBean.getIdEmpresa());
                usr.setIdEmpregado(usuarioBean.getIdEmpregado());
                usr.setLogin(usuarioBean.getLogin());
                usr.setStatus(usuarioBean.getStatus());
                usr.setIdPerfilAcessoUsuario(this.getProfile(usuarioBean.getIdUsuario()));
                // utilizado para log
                PersistenceEngine.setUsuarioSessao(usuarioBean);
                Reflexao.copyPropertyValues(usr, usuarioFramework);
                br.com.citframework.util.WebUtil.setUsuario(usuarioFramework, httpRequest);
                final Collection<GrupoDTO> colGrupos = this.getGrupoService().getGruposByPessoa(usuarioBean.getIdEmpregado());
                GrupoDTO grpSeg;

                String[] grupos = null;
                if (colGrupos != null) {
                    grupos = new String[colGrupos.size()];
                    for (int i = 0; i < colGrupos.size(); i++) {
                        grpSeg = (GrupoDTO) ((List) colGrupos).get(i);
                        grupos[i] = grpSeg.getSigla();
                    }
                } else {
                    grupos = new String[1];
                    grupos[0] = "";
                }

                usr.setGrupos(grupos);
                usr.setColGrupos(colGrupos);

                usr.setLocale(WebUtil.getLanguage(httpRequest));

                final EmpregadoDTO empregadoDto = new EmpregadoDao().restoreByIdEmpregado(usuarioBean.getIdEmpregado());
                if (empregadoDto != null && empregadoDto.getIdUnidade() != null) {
                    usr.setIdUnidade(empregadoDto.getIdUnidade());
                }

                httpRequest.getSession().setAttribute("locale", usr.getLocale());

                WebUtil.setUsuario(usr, httpRequest);
            } else {
                throw new LogicException(UtilI18N.internacionaliza(httpRequest, "bi.painelControle.conexao.MetodoNaoConfigurado"));
            }
        }

        return login;
    }

    private Integer getProfile(final Integer idUsuario) throws ServiceException, Exception {
        PerfilAcessoUsuarioDTO perfilAcessoDTO = new PerfilAcessoUsuarioDTO();
        perfilAcessoDTO.setIdUsuario(idUsuario);
        perfilAcessoDTO = this.getPerfilAcessoService().listByIdUsuario(perfilAcessoDTO);
        if (perfilAcessoDTO == null) {
            return null;
        }
        return perfilAcessoDTO.getIdPerfilAcesso();
    }

    private GrupoService grupoService;
    private PerfilAcessoUsuarioService perfilAcessoService;
    private UsuarioService usuarioService;

    public GrupoService getGrupoService() throws ServiceException {
        if (grupoService == null) {
            grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
        }
        return grupoService;
    }

    private PerfilAcessoUsuarioService getPerfilAcessoService() throws ServiceException {
        if (perfilAcessoService == null) {
            perfilAcessoService = (PerfilAcessoUsuarioService) ServiceLocator.getInstance().getService(PerfilAcessoUsuarioService.class, null);
        }
        return perfilAcessoService;
    }

    public UsuarioService getUsuarioService() throws ServiceException {
        if (usuarioService == null) {
            usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
        }
        return usuarioService;
    }

}
