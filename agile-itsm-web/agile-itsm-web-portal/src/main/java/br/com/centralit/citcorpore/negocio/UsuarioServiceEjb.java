package br.com.centralit.citcorpore.negocio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.ADUserDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EmpresaDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.LoginDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.EmpresaDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.ParametroCorporeDAO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoGrupoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoUsuarioDAO;
import br.com.centralit.citcorpore.integracao.UnidadeDao;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.integracao.ad.LDAPUtils;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * @author Centralit
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class UsuarioServiceEjb extends CrudServiceImpl implements UsuarioService {

    /** Bean de PerfilAcesso. */
    private UsuarioDTO usuarioBean;

    private UsuarioDao dao;

    @Override
    protected UsuarioDao getDao() {
        if (dao == null) {
            dao = new UsuarioDao();
        }
        return dao;
    }

    /**
     * Cria usuário e perfil acesso.
     *
     * @see br.com.citframework.service.CrudServiceImpl#create(br.com.citframework.dto.IDto)
     */
    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        this.setUsuarioBean(model);

        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaCreate(this.getUsuarioBean());

			this.getDao().setTransactionControler(transaction);
            this.getGrupoempregadoDao().setTransactionControler(transaction);

            transaction.start();

            this.setUsuarioBean(this.getDao().create(this.getUsuarioBean()));

            this.criarPerfilAcessoUsuario(this.getUsuarioBean(), transaction);

            if (this.getUsuarioBean().getColGrupoEmpregado() != null) {
				this.getGrupoempregadoDao().deleteByIdEmpregado(this.getUsuarioBean().getIdEmpregado());
                for (final GrupoEmpregadoDTO grupoempregadoDTO : this.getUsuarioBean().getColGrupoEmpregado()) {
                    grupoempregadoDTO.setIdEmpregado(this.getUsuarioBean().getIdEmpregado());
                    grupoempregadoDTO.setIdGrupo(grupoempregadoDTO.getIdGrupo());
                    this.getGrupoempregadoDao().create(grupoempregadoDTO);
                }
            }

            transaction.commit();
            transaction.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transaction, e);
        }

        return this.getUsuarioBean();
    }

    @Override
    public IDto createFirs(final IDto model) throws ServiceException, LogicException {
        final UsuarioDTO usrDto = (UsuarioDTO) model;
        final PerfilAcessoUsuarioDTO perfilAcDTO = new PerfilAcessoUsuarioDTO();
        final EmpregadoDTO empregadoDTO = new EmpregadoDTO();
        GrupoDTO grupoDTO = new GrupoDTO();
        final UnidadeDTO unidadeDTO = new UnidadeDTO();
        EmpresaDTO empresaDTO = new EmpresaDTO();
        PerfilAcessoDTO perfilAcessoDTO = new PerfilAcessoDTO();
        final PerfilAcessoGrupoDTO perfilAcessoGrupoDTO = new PerfilAcessoGrupoDTO();

        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());

        final PerfilAcessoUsuarioDAO perfilAcDao = new PerfilAcessoUsuarioDAO();
        final PerfilAcessoGrupoDao perfilAcessoGrupoDao = new PerfilAcessoGrupoDao();
        final EmpregadoDao empregadoDao = new EmpregadoDao();
        final GrupoDao grupoDao = new GrupoDao();
        final UnidadeDao uniDao = new UnidadeDao();
        final ParametroCorporeDAO paramDao = new ParametroCorporeDAO();
        final EmpresaDao empresaDao = new EmpresaDao();
        final PerfilAcessoDao perfilAcessoDao = new PerfilAcessoDao();
        try {
            this.getDao().setTransactionControler(transaction);
            perfilAcDao.setTransactionControler(transaction);
            perfilAcessoGrupoDao.setTransactionControler(transaction);
            empregadoDao.setTransactionControler(transaction);
            grupoDao.setTransactionControler(transaction);
            uniDao.setTransactionControler(transaction);
            empresaDao.setTransactionControler(transaction);
            perfilAcessoDao.setTransactionControler(transaction);
            paramDao.setTransactionControler(transaction);

            transaction.start();

            perfilAcessoDTO.setDataInicio(UtilDatas.getDataAtual());
            perfilAcessoDTO.setNomePerfilAcesso("Administrador");
            perfilAcessoDTO = (PerfilAcessoDTO) perfilAcessoDao.create(perfilAcessoDTO);

            empresaDTO.setDataInicio(UtilDatas.getDataAtual());
            empresaDTO.setDetalhamento("");
            empresaDTO.setNomeEmpresa("CENTRAL IT");
            empresaDTO = (EmpresaDTO) empresaDao.create(empresaDTO);

            grupoDTO.setDataInicio(UtilDatas.getDataAtual());
            grupoDTO.setDescricao("");
            grupoDTO.setIdEmpresa(empresaDTO.getIdEmpresa());
            grupoDTO.setNome("Desenvolvimento");
            grupoDTO.setIdPerfilAcessoGrupo(perfilAcessoDTO.getIdPerfilAcesso());
            grupoDTO = (GrupoDTO) grupoDao.create(grupoDTO);

            perfilAcessoGrupoDTO.setIdGrupo(grupoDTO.getIdGrupo());
            perfilAcessoGrupoDTO.setIdPerfilAcessoGrupo(grupoDTO.getIdPerfilAcessoGrupo());
            perfilAcessoGrupoDTO.setDataInicio(grupoDTO.getDataInicio());
            perfilAcessoGrupoDao.create(perfilAcessoGrupoDTO);

            unidadeDTO.setDataInicio(UtilDatas.getDataAtual());
            unidadeDTO.setDescricao("");
            unidadeDTO.setEmail("");
            unidadeDTO.setIdEmpresa(empresaDTO.getIdEmpresa());
            unidadeDTO.setIdGrupo(grupoDTO.getIdGrupo());
            unidadeDTO.setNome("Desenvolvimento");
            uniDao.create(unidadeDTO);

            empregadoDTO.setDataAdmissao(UtilDatas.getDataAtual());
            empregadoDTO.setTipo("A");
            empregadoDTO.setSexo("M");
            empregadoDTO.setDataNascimento(UtilDatas.getDataAtual());
            empregadoDTO.setIdGrupo(grupoDTO.getIdGrupo());
            empregadoDTO.setIdUnidade(unidadeDTO.getIdUnidade());
            empregadoDTO.setNome("Administrador");
            empregadoDTO.setNomeProcura("Administrador");
            empregadoDTO.setIdSituacaoFuncional(1);
            empregadoDao.create(empregadoDTO);

            this.getParametroSmartService().criarParametrosNovos();

            usrDto.setIdEmpregado(empregadoDTO.getIdEmpregado());
            usrDto.setIdEmpresa(empresaDTO.getIdEmpresa());
            usrDto.setIdGrupo(grupoDTO.getIdGrupo());
            usrDto.setIdUnidade(unidadeDTO.getIdUnidade());
            this.getDao().create(usrDto);

            perfilAcDTO.setDataInicio(UtilDatas.getDataAtual());
            perfilAcDTO.setIdUsuario(usrDto.getIdUsuario());
            perfilAcDTO.setIdPerfilAcesso(perfilAcessoDTO.getIdPerfilAcesso());
            perfilAcDao.create(perfilAcDTO);

            transaction.commit();
            transaction.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transaction, e);
        }
        return usrDto;
    }

    @Override
    public void updateNotNull(final IDto dto) {
        try {
            this.validaUpdate(dto);
            this.getDao().updateNotNull(dto);
        } catch (final ServiceException e) {
            e.printStackTrace();
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cria ou atualiza perfil de acesso do usuário.
     *
     * @param transaction
     * @param perfilAcessoUsuarioDao
     * @param perfilAcessoUsuario
     * @throws Exception
     */
    private void criarPerfilAcessoUsuario(final UsuarioDTO usuario, final TransactionControler transaction) throws Exception {
        PerfilAcessoUsuarioDTO perfilAcessoUsuario;
        final PerfilAcessoUsuarioDAO perfilAcessoUserDao = new PerfilAcessoUsuarioDAO();
        perfilAcessoUserDao.setTransactionControler(transaction);

        final Integer idPerfilAcessoUsuarioSelecionado = usuario.getIdPerfilAcessoUsuario();

        if (usuario.getIdUsuario() != null) {
            perfilAcessoUsuario = perfilAcessoUserDao.obterPerfilAcessoUsuario(usuario);

            if (perfilAcessoUsuario == null) {
                if (idPerfilAcessoUsuarioSelecionado != null) {
                    perfilAcessoUsuario = new PerfilAcessoUsuarioDTO();

                    perfilAcessoUsuario.setIdUsuario(usuario.getIdUsuario());
                    perfilAcessoUsuario.setDataInicio(UtilDatas.getDataAtual());
                    perfilAcessoUsuario.setIdPerfilAcesso(idPerfilAcessoUsuarioSelecionado);

                    perfilAcessoUserDao.create(perfilAcessoUsuario);
                }
            } else {
                if (idPerfilAcessoUsuarioSelecionado == null) {
                    perfilAcessoUserDao.delete(perfilAcessoUsuario);
                } else {
                    perfilAcessoUsuario.setIdPerfilAcesso(idPerfilAcessoUsuarioSelecionado);
                    perfilAcessoUserDao.update(perfilAcessoUsuario);
                }
            }
        } else {
            perfilAcessoUsuario = new PerfilAcessoUsuarioDTO();

            perfilAcessoUsuario.setIdPerfilAcesso(usuario.getIdPerfilAcessoUsuario());
            perfilAcessoUsuario.setIdUsuario(this.getUsuarioBean().getIdUsuario());
            perfilAcessoUsuario.setDataInicio(UtilDatas.getDataAtual());

            perfilAcessoUserDao.create(perfilAcessoUsuario);
        }
    }

    @Override
    public void delete(final IDto model) throws ServiceException, LogicException {
        this.setUsuarioBean(model);
        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            final PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();
            final GrupoEmpregadoDao grupoempregadoDao = new GrupoEmpregadoDao();
            this.getDao().setTransactionControler(transaction);
            perfilAcessoUsuarioDao.setTransactionControler(transaction);
            grupoempregadoDao.setTransactionControler(transaction);

            final PerfilAcessoUsuarioDTO perfilAcessoUsuario = new PerfilAcessoUsuarioDTO();

            transaction.start();
            this.getUsuarioBean().setStatus("I");
            this.getDao().update(this.getUsuarioBean());
            if (this.getUsuarioBean().getIdPerfilAcessoUsuario() != null) {
                perfilAcessoUsuario.setIdUsuario(this.getUsuarioBean().getIdUsuario());
                perfilAcessoUsuarioDao.delete(perfilAcessoUsuario);
            }
            grupoempregadoDao.deleteByIdEmpregado(this.getUsuarioBean().getIdEmpregado());

            transaction.commit();
            transaction.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transaction, e);
        }
    }

    @Override
    public void update(final IDto usuarioDto) throws ServiceException, LogicException {
        final UsuarioDTO usuarioSelecionado = (UsuarioDTO) usuarioDto;

        final UsuarioDao usuarioDao = new UsuarioDao();

        final PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();

        final GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();

        final TransactionControler transaction = new TransactionControlerImpl(usuarioDao.getAliasDB());

        try {
            this.validaUpdate(usuarioSelecionado);

            usuarioDao.setTransactionControler(transaction);
            perfilAcessoUsuarioDao.setTransactionControler(transaction);
            grupoEmpregadoDao.setTransactionControler(transaction);

            transaction.start();

            usuarioDao.updateNotNull(usuarioSelecionado);

            PerfilAcessoUsuarioDTO perfilAcessoUsuario;

            final Integer idPerfilAcessoUsuarioSelecionado = usuarioSelecionado.getIdPerfilAcessoUsuario();

            perfilAcessoUsuario = perfilAcessoUsuarioDao.obterPerfilAcessoUsuario(usuarioSelecionado);

            if (perfilAcessoUsuario == null) {
                if (idPerfilAcessoUsuarioSelecionado != null) {
                    perfilAcessoUsuario = new PerfilAcessoUsuarioDTO();
                    perfilAcessoUsuario.setIdUsuario(usuarioSelecionado.getIdUsuario());
                    perfilAcessoUsuario.setDataInicio(UtilDatas.getDataAtual());
                    perfilAcessoUsuario.setIdPerfilAcesso(idPerfilAcessoUsuarioSelecionado);
                    perfilAcessoUsuarioDao.create(perfilAcessoUsuario);
                }
            } else {
                if (idPerfilAcessoUsuarioSelecionado == null) {
                    perfilAcessoUsuarioDao.delete(perfilAcessoUsuario);
                } else {
                    perfilAcessoUsuario.setIdPerfilAcesso(idPerfilAcessoUsuarioSelecionado);
                    perfilAcessoUsuarioDao.update(perfilAcessoUsuario);
                }
            }

            if (usuarioSelecionado.getColGrupoEmpregado() != null) {
                grupoEmpregadoDao.deleteByIdEmpregado(usuarioSelecionado.getIdEmpregado());
                for (final GrupoEmpregadoDTO grupoempregadoDto : usuarioSelecionado.getColGrupoEmpregado()) {
                    grupoempregadoDto.setIdEmpregado(usuarioSelecionado.getIdEmpregado());
                    grupoempregadoDto.setIdGrupo(grupoempregadoDto.getIdGrupo());
                    grupoEmpregadoDao.create(grupoempregadoDto);
                }
            }

            transaction.commit();
            transaction.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transaction, e);
        }
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        final UsuarioDTO usuarioDTO = (UsuarioDTO) arg0;

        this.setPerfilAcessoUsuarioDefault(usuarioDTO);
    }

    private void setPerfilAcessoUsuarioDefault(final UsuarioDTO usuarioDTO) throws ServiceException, Exception {
        if (usuarioDTO.getIdPerfilAcessoUsuario() != null) {
            return;
        }

        try {
            final String idPerfilAcessoDefault = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_PERFIL_ACESSO_DEFAULT, null);

            if (idPerfilAcessoDefault != null) {
                usuarioDTO.setIdPerfilAcessoUsuario(Integer.parseInt(idPerfilAcessoDefault.trim()));
            }
        } catch (final NumberFormatException e) {
            System.out.println("Parâmetro de perfil default não definido.");
        }
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        final UsuarioDTO usuarioDTO = (UsuarioDTO) arg0;
        this.setPerfilAcessoUsuarioDefault(usuarioDTO);
    }

    @Override
    public UsuarioDTO restoreByLogin(final String login) throws ServiceException, LogicException {
        try {
            return this.getDao().restoreByLogin(login);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UsuarioDTO restoreByLogin(final String login, final String senha) throws ServiceException, LogicException {
        try {
            return this.getDao().restoreByLoginSenha(login, senha);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UsuarioDTO restoreByIdEmpregado(final Integer idEmpregado) throws ServiceException, Exception {
        return this.getDao().restoreByIdEmpregado(idEmpregado);
    }

    @Override
    public void deleteByIdEmpregado(final Integer idEmpregado) throws ServiceException, Exception {
        final UsuarioDTO usuario = this.restoreByIdEmpregado(idEmpregado);

        if (usuario != null) {
            usuario.setStatus("I");
        }
        this.update(usuario);
    }

    /**
     * @return valor do atributo usuarioBean.
     */
    public UsuarioDTO getUsuarioBean() {
        return usuarioBean;
    }

    /**
     * Configura Bean de PerfilAcesso.
     *
     * @param baseItemConfiguracaoBean
     * @author valdoilo.damasceno
     */
    private void setUsuarioBean(final IDto usuarioBean) {
        this.usuarioBean = (UsuarioDTO) usuarioBean;
    }

    @Override
    public UsuarioDTO listStatus(final UsuarioDTO obj) throws Exception {
        try {
            return this.getDao().listStatus(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UsuarioDTO listLogin(final UsuarioDTO obj) throws Exception {
        try {
            return this.getDao().listLogin(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UsuarioDTO listUsuarioExistente(final UsuarioDTO obj) throws Exception {
        try {
            return this.getDao().listUsuarioExistente(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    private ParametroCorporeService parametroCorporeService;

    private ParametroCorporeService getParametroSmartService() throws Exception {
        if (parametroCorporeService == null) {
            parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
        }
        return parametroCorporeService;
    }

    @Override
    public void sincronizaUsuarioAD(final ADUserDTO usuarioAd, final LoginDTO login, final Boolean isImport) throws ServiceException, Exception {
        final UsuarioDao usuarioDao = new UsuarioDao();
        final EmpregadoDao empregadoDao = new EmpregadoDao();
        final PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();
        final GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();

        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            usuarioDao.setTransactionControler(transaction);
            empregadoDao.setTransactionControler(transaction);
            perfilAcessoUsuarioDao.setTransactionControler(transaction);
            grupoEmpregadoDao.setTransactionControler(transaction);

            transaction.start();

            if (usuarioAd != null) {
                UsuarioDTO novoUsuarioDoAD = new UsuarioDTO();

                novoUsuarioDoAD.setLogin(login.getUser());
                novoUsuarioDoAD.setSenha(login.getSenha());

                final UsuarioDTO usuarioAux = usuarioDao.restoreByLogin(novoUsuarioDoAD.getLogin());

                novoUsuarioDoAD = usuarioAux == null ? new UsuarioDTO() : usuarioAux;

                novoUsuarioDoAD.setStatus(usuarioAd.getUserAccountControl() == "514" ? "I" : "A");

                if (isImport != null && isImport) {
                    novoUsuarioDoAD.setLdap("N");
                } else {
                    novoUsuarioDoAD.setLdap("S");
                }

                String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");

                if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {

                    algoritmo = "SHA-1";
                }

                novoUsuarioDoAD.setSenha(CriptoUtils.generateHash(login.getSenha(), algoritmo));

                novoUsuarioDoAD.setLogin(login.getUser());

                final String parametroLDAP_SN_LAST_NAME = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_SN_LAST_NAME, "N").trim();

                String PARAMETRO_LDAP_ATRIBUTO = null;
                if (usuarioAd != null && usuarioAd.getLdapAtributo() != null && usuarioAd.getLdapAtributo().equalsIgnoreCase("")) {
                    PARAMETRO_LDAP_ATRIBUTO = usuarioAd.getLdapAtributo();
                } else {
                    PARAMETRO_LDAP_ATRIBUTO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_ATRIBUTO, "CN").trim();
                }

				if (parametroLDAP_SN_LAST_NAME != null && parametroLDAP_SN_LAST_NAME.equalsIgnoreCase("S") && (PARAMETRO_LDAP_ATRIBUTO == null || StringUtils.isEmpty(PARAMETRO_LDAP_ATRIBUTO.trim()))) {
                    PARAMETRO_LDAP_ATRIBUTO = "SN";

                    if (PARAMETRO_LDAP_ATRIBUTO != null && !StringUtils.isEmpty(PARAMETRO_LDAP_ATRIBUTO.trim())) {
                        final String[] atributosRetorno = LDAPUtils.getADFieldList();

                        final int numeroRegistros = atributosRetorno.length;
                        novoUsuarioDoAD.setNomeUsuario(this.obterNomeUsuario(usuarioAd, numeroRegistros, PARAMETRO_LDAP_ATRIBUTO).trim());
                    }
                } else {
                    if (PARAMETRO_LDAP_ATRIBUTO != null && !StringUtils.isEmpty(PARAMETRO_LDAP_ATRIBUTO.trim())) {
                        final String[] atributosRetorno = LDAPUtils.getADFieldList();

                        final int numeroRegistros = atributosRetorno.length;
                        novoUsuarioDoAD.setNomeUsuario(this.obterNomeUsuario(usuarioAd, numeroRegistros, PARAMETRO_LDAP_ATRIBUTO).trim());
                    }
                }

                EmpregadoDTO empregadoDTO = new EmpregadoDTO();
                if (novoUsuarioDoAD.getIdEmpregado() != null && novoUsuarioDoAD.getIdEmpregado() != 0) {
                    empregadoDTO.setIdEmpregado(novoUsuarioDoAD.getIdEmpregado());
                    empregadoDTO = (EmpregadoDTO) empregadoDao.restore(empregadoDTO);

                    if (empregadoDTO == null) {
                        empregadoDTO = new EmpregadoDTO();
                    }

                    if (empregadoDTO.getDataFim() != null) {
                        empregadoDTO.setDataFim(null);
                    }
                } else if (novoUsuarioDoAD.getNomeUsuario() != null) {
                    final UsuarioDTO usuario = usuarioDao.restoreByNomeUsuario(novoUsuarioDoAD.getNomeUsuario());

                    if (usuario != null && usuario.getIdEmpregado() != null && usuario.getIdEmpregado() != 0) {

                        empregadoDTO.setIdEmpregado(usuario.getIdEmpregado());
                        empregadoDTO = (EmpregadoDTO) empregadoDao.restore(empregadoDTO);

                        if (empregadoDTO == null) {
                            empregadoDTO = new EmpregadoDTO();
                        }

                        if (empregadoDTO.getDataFim() != null) {
                            empregadoDTO.setDataFim(null);
                        }
                    }
                }

                if (empregadoDTO.getTelefone() == null) {
                    empregadoDTO.setTelefone(LDAPUtils.nullToNaoDisponivel(usuarioAd.getTelephoneNumber()));
                } else {
                    if (empregadoDTO.getTelefone().equals("Não disponível")) {
                        empregadoDTO.setTelefone(LDAPUtils.nullToNaoDisponivel(usuarioAd.getTelephoneNumber()));
                    }
                }
                if (empregadoDTO.getEmail() == null) {
                    if (usuarioAd.getMail() != null) {
                        empregadoDTO.setEmail(LDAPUtils.nullToNaoDisponivel(usuarioAd.getMail().trim()));
                    }
                } else {
                    if (usuarioAd.getMail() != null) {
                        empregadoDTO.setEmail(LDAPUtils.nullToNaoDisponivel(usuarioAd.getMail().trim()));
                    }
                }

                if (empregadoDTO.getNome() == null) {
                    empregadoDTO.setNome(LDAPUtils.nullToNaoDisponivel(novoUsuarioDoAD.getNomeUsuario()));
                } else {
                    if (empregadoDTO.getNome().equals("Não disponível")) {
                        empregadoDTO.setNome(LDAPUtils.nullToNaoDisponivel(novoUsuarioDoAD.getNomeUsuario()));
                    }
                }

                if (empregadoDTO.getNomeProcura() == null) {
                    empregadoDTO.setNomeProcura(LDAPUtils.nullToNaoDisponivel(novoUsuarioDoAD.getNomeUsuario()));
                } else {
                    if (empregadoDTO.getNomeProcura().equals("Não disponível")) {
                        empregadoDTO.setNomeProcura(LDAPUtils.nullToNaoDisponivel(novoUsuarioDoAD.getNomeUsuario()));
                    }
                }

                if (empregadoDTO.getIdSituacaoFuncional() == null) {
                    empregadoDTO.setIdSituacaoFuncional(usuarioAd.getUserAccountControl() == "514" ? 2 : 1);
                }

                Integer idGrupoPadrao = null;
                if (usuarioAd.getIdGrupo() != null && !usuarioAd.getIdGrupo().equalsIgnoreCase("")) {
                    idGrupoPadrao = new Integer(usuarioAd.getIdGrupo());
                } else {
                    idGrupoPadrao = this.obterIdGrupoPadrao();
                }

                if (empregadoDTO.getIdEmpregado() != null) {
                    final Collection gruposEmpregado = grupoEmpregadoDao.findAtivosByIdEmpregado(empregadoDTO.getIdEmpregado());

                    if ((gruposEmpregado == null || gruposEmpregado.isEmpty()) && idGrupoPadrao != null) {
                        final GrupoEmpregadoDTO grupoEmpregadoDto = new GrupoEmpregadoDTO();

                        grupoEmpregadoDto.setIdEmpregado(empregadoDTO.getIdEmpregado());
                        grupoEmpregadoDto.setIdGrupo(idGrupoPadrao);

                        grupoEmpregadoDao.create(grupoEmpregadoDto);
                    }

                    empregadoDao.update(empregadoDTO);
                } else {
                    empregadoDao.create(empregadoDTO);

                    if (idGrupoPadrao != null) {

                        final GrupoEmpregadoDTO grupoEmpregadoDto = new GrupoEmpregadoDTO();

                        grupoEmpregadoDto.setIdEmpregado(empregadoDTO.getIdEmpregado());
                        grupoEmpregadoDto.setIdGrupo(idGrupoPadrao);

                        grupoEmpregadoDao.create(grupoEmpregadoDto);
                    }
                }

                novoUsuarioDoAD.setIdEmpresa(1);

                novoUsuarioDoAD.setIdEmpregado(empregadoDTO.getIdEmpregado());
                if (novoUsuarioDoAD.getIdUsuario() != null) {
                    novoUsuarioDoAD.setIdPerfilAcessoUsuario(this.getIdPerfilAcessoUsuario(novoUsuarioDoAD.getIdUsuario()));

                    this.update(novoUsuarioDoAD);

                    perfilAcessoUsuarioDao.reativaPerfisUsuario(novoUsuarioDoAD.getIdUsuario());
                } else {
                    novoUsuarioDoAD = (UsuarioDTO) usuarioDao.create(novoUsuarioDoAD);
                }
            }

            transaction.commit();
            transaction.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transaction, e);
        }

    }

    @Override
    public synchronized void sincronizaUsuarioAD(final ADUserDTO usuarioAd, final Integer idGrupoSolicitante) throws ServiceException, Exception {
        final UsuarioDao usuarioDao = new UsuarioDao();
        final EmpregadoDao empregadoDao = new EmpregadoDao();
        final GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
        final GrupoDao grupoDao = new GrupoDao();

        final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());

		if (usuarioAd != null && usuarioAd.getsAMAccountName() != null && (!usuarioAd.getsAMAccountName().equalsIgnoreCase("admin") || !usuarioAd.getsAMAccountName().equalsIgnoreCase("consultor"))) {

            try {
                usuarioDao.setTransactionControler(transaction);
                empregadoDao.setTransactionControler(transaction);
                grupoEmpregadoDao.setTransactionControler(transaction);

                transaction.start();

                final UsuarioDTO usuarioAux = usuarioDao.restoreByLogin(usuarioAd.getsAMAccountName());
                UsuarioDTO novoUsuarioDoAD = usuarioAux == null ? new UsuarioDTO() : usuarioAux;

                if (novoUsuarioDoAD.getSenha() == null) {
                    novoUsuarioDoAD.setSenha("");
                }

                String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
                if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
                    algoritmo = "SHA-1";
                }

                novoUsuarioDoAD.setSenha(CriptoUtils.generateHash(novoUsuarioDoAD.getSenha(), algoritmo));

                novoUsuarioDoAD.setStatus(usuarioAd.getUserAccountControl() == "514" ? "I" : "A");

                novoUsuarioDoAD.setLdap("S");

                novoUsuarioDoAD.setLogin(usuarioAd.getsAMAccountName());
                String PARAMETRO_LDAP_ATRIBUTO = "";
                if (usuarioAd.getLdapAtributo() != null && !usuarioAd.getLdapAtributo().equalsIgnoreCase("")) {
                    PARAMETRO_LDAP_ATRIBUTO = usuarioAd.getLdapAtributo();
                } else {
                    PARAMETRO_LDAP_ATRIBUTO = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_ATRIBUTO, "CN").trim();
                }

                if (PARAMETRO_LDAP_ATRIBUTO != null && !StringUtils.isEmpty(PARAMETRO_LDAP_ATRIBUTO.trim())) {

                    final String[] atributosRetorno = LDAPUtils.getADFieldList();

                    final int numeroRegistros = atributosRetorno.length;

                    novoUsuarioDoAD.setNomeUsuario(this.obterNomeUsuario(usuarioAd, numeroRegistros, PARAMETRO_LDAP_ATRIBUTO).trim());
                }

                EmpregadoDTO empregadoDTO = new EmpregadoDTO();
                if (novoUsuarioDoAD.getIdEmpregado() != null && novoUsuarioDoAD.getIdEmpregado() != 0) {
                    empregadoDTO.setIdEmpregado(novoUsuarioDoAD.getIdEmpregado());
                    empregadoDTO = (EmpregadoDTO) empregadoDao.restore(empregadoDTO);

                    if (empregadoDTO.getDataFim() != null) {
                        empregadoDTO.setDataFim(null);
                    }
                }

                else if (novoUsuarioDoAD.getNomeUsuario() != null) {

                    final UsuarioDTO usuario = usuarioDao.restoreByNomeUsuario(novoUsuarioDoAD.getNomeUsuario());

                    if (usuario != null && usuario.getIdEmpregado() != null && usuario.getIdEmpregado() != 0) {

                        empregadoDTO.setIdEmpregado(usuario.getIdEmpregado());
                        empregadoDTO = (EmpregadoDTO) empregadoDao.restore(empregadoDTO);

                        if (empregadoDTO == null) {
                            empregadoDTO = new EmpregadoDTO();
                        }

                        if (empregadoDTO.getDataFim() != null) {
                            empregadoDTO.setDataFim(null);
                        }
                    }

                }

                if (empregadoDTO.getTelefone() == null) {

                    empregadoDTO.setTelefone(LDAPUtils.nullToNaoDisponivel(usuarioAd.getTelephoneNumber()));

                } else {
                    if (empregadoDTO.getTelefone().equals("Não disponível")) {
                        empregadoDTO.setTelefone(LDAPUtils.nullToNaoDisponivel(usuarioAd.getTelephoneNumber()));
                    }
                }

                if (empregadoDTO.getEmail() == null) {

                    empregadoDTO.setEmail(LDAPUtils.nullToNaoDisponivel(usuarioAd.getMail()));
                } else {
                    if (empregadoDTO.getEmail().equals("Não disponível")) {
                        empregadoDTO.setEmail(LDAPUtils.nullToNaoDisponivel(usuarioAd.getMail()));
                    }
                }

                empregadoDTO.setEmail(empregadoDTO.getEmail().trim().toLowerCase()); // Caixa baixa para todos os emails, pois em algum servidores o envio não acontece

                if (empregadoDTO.getNome() == null) {
                    empregadoDTO.setNome(LDAPUtils.nullToNaoDisponivel(novoUsuarioDoAD.getNomeUsuario()));

                } else {
                    if (empregadoDTO.getNome().equals("Não disponível") || !empregadoDTO.getNome().equalsIgnoreCase(novoUsuarioDoAD.getNomeUsuario())) {
                        empregadoDTO.setNome(LDAPUtils.nullToNaoDisponivel(novoUsuarioDoAD.getNomeUsuario()));
                    }
                }

                empregadoDTO.setNome(empregadoDTO.getNome().trim()); // Removendo os spaços no nome do usuário

                if (empregadoDTO.getNomeProcura() == null) {
                    empregadoDTO.setNomeProcura(LDAPUtils.nullToNaoDisponivel(novoUsuarioDoAD.getNomeUsuario()));
                } else {
                    if (empregadoDTO.getNomeProcura().equals("Não disponível")) {
                        empregadoDTO.setNomeProcura(LDAPUtils.nullToNaoDisponivel(novoUsuarioDoAD.getNomeUsuario()));
                    }
                }

                if (empregadoDTO.getIdSituacaoFuncional() == null) {
                    empregadoDTO.setIdSituacaoFuncional(usuarioAd.getUserAccountControl() == "514" ? 2 : 1);
                }

                Integer idGrupoPadrao;

                if (idGrupoSolicitante != null && idGrupoSolicitante != 0) {
                    idGrupoPadrao = idGrupoSolicitante;
                } else {
                    if (usuarioAd.getIdGrupo() != null && !usuarioAd.getIdGrupo().equalsIgnoreCase("")) {
                        idGrupoPadrao = new Integer(usuarioAd.getIdGrupo());
                    } else {
                        idGrupoPadrao = this.obterIdGrupoPadrao();
                    }
                }

                if (empregadoDTO.getIdEmpregado() != null) {
                    final Collection gruposEmpregado = grupoEmpregadoDao.findAtivosByIdEmpregado(empregadoDTO.getIdEmpregado());

                    if ((gruposEmpregado == null || gruposEmpregado.isEmpty()) && idGrupoPadrao != null) {
                        // Verifica se Já tem o grupo padrão mesmo deletado senão dá erro de chave duplicada
                        final Collection colPadrao = grupoEmpregadoDao.findEmpregado(idGrupoPadrao, empregadoDTO.getIdEmpregado());
                        if (colPadrao == null || colPadrao.isEmpty()) {
                            final GrupoEmpregadoDTO grupoEmpregadoDto = new GrupoEmpregadoDTO();

                            grupoEmpregadoDto.setIdEmpregado(empregadoDTO.getIdEmpregado());
                            grupoEmpregadoDto.setIdGrupo(idGrupoPadrao);

                            grupoEmpregadoDao.create(grupoEmpregadoDto);
                        }
                    }
                    empregadoDao.updateNotNull(empregadoDTO);

                } else {

                    empregadoDao.create(empregadoDTO);

                    if (idGrupoPadrao != null) {
                        GrupoDTO grupoPadrao = new GrupoDTO();
                        grupoPadrao.setIdGrupo(idGrupoPadrao);

                        grupoPadrao = (GrupoDTO) grupoDao.restore(grupoPadrao);

                        if (grupoPadrao.getDataFim() == null) {

                            final GrupoEmpregadoDTO grupoEmpregadoDto = new GrupoEmpregadoDTO();

                            grupoEmpregadoDto.setIdEmpregado(empregadoDTO.getIdEmpregado());
                            grupoEmpregadoDto.setIdGrupo(grupoPadrao.getIdGrupo());

                            grupoEmpregadoDao.create(grupoEmpregadoDto);
                        }
                    }
                }

                novoUsuarioDoAD.setIdEmpresa(1);

                final String parametroMETODO_AUTENTICACAO_Pasta = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.METODO_AUTENTICACAO_Pasta, "2").trim();

                novoUsuarioDoAD.setIdEmpregado(empregadoDTO.getIdEmpregado());

                if (parametroMETODO_AUTENTICACAO_Pasta != null && parametroMETODO_AUTENTICACAO_Pasta.trim().equalsIgnoreCase("1")) {
                    if (novoUsuarioDoAD.getIdUsuario() == null) {
                        novoUsuarioDoAD.setStatus("I");
                        novoUsuarioDoAD = (UsuarioDTO) usuarioDao.create(novoUsuarioDoAD);
                    }
                } else {
                    if (novoUsuarioDoAD.getIdUsuario() != null) {
                        usuarioDao.updateNotNull(novoUsuarioDoAD);
                    } else {
                        novoUsuarioDoAD = (UsuarioDTO) usuarioDao.create(novoUsuarioDoAD);
                    }
                }

                transaction.commit();
                transaction.close();
            } catch (final Exception e) {
                e.printStackTrace();
                this.rollbackTransaction(transaction, e);
            }
        }
    }

    private Integer obterIdGrupoPadrao() throws NumberFormatException, Exception {
        final ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

        final String grupoPadrao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_LDAP, "");

        if (grupoPadrao.indexOf(";") > 0) {
            return Integer.parseInt(grupoPadrao.split(";")[0]);
        }

        Integer idGrupoPadrao = null;

        if (grupoPadrao != null && !StringUtils.isBlank(grupoPadrao.trim())) {
            idGrupoPadrao = Integer.parseInt(parametroService.getParamentroAtivo(45).getValor().trim());
        }

        return idGrupoPadrao;
    }

    private Integer getIdPerfilAcessoUsuario(final Integer idUsuario) throws ServiceException, Exception {
        final PerfilAcessoUsuarioService perfilAcessoService = (PerfilAcessoUsuarioService) ServiceLocator.getInstance().getService(PerfilAcessoUsuarioService.class, null);
        PerfilAcessoUsuarioDTO perfilAcessoDTO = new PerfilAcessoUsuarioDTO();
        perfilAcessoDTO.setIdUsuario(idUsuario);
        perfilAcessoDTO = perfilAcessoService.listByIdUsuario(perfilAcessoDTO);
        if (perfilAcessoDTO == null) {
            return null;
        }
        return perfilAcessoDTO.getIdPerfilAcesso();
    }

    @Override
    public UsuarioDTO restoreByIdEmpregadosDeUsuarios(final Integer idEmpregado) throws Exception {
        final UsuarioDao usuarioDao = new UsuarioDao();
        return usuarioDao.restoreByIdEmpregadosDeUsuarios(idEmpregado);
    }

    /**
     * Verifica se usuário informado é um usuário do AD.
     *
     * @param usuarioDto
     * @return true - Usuário do AD; false - Usuário cadastrado pelo sistema.
     * @throws Exception
     */
    @Override
    public boolean usuarioIsAD(UsuarioDTO usuarioDto) throws Exception {
        if (usuarioDto != null && usuarioDto.getIdUsuario() != null) {
            final UsuarioDao usuarioDao = new UsuarioDao();

            usuarioDto = (UsuarioDTO) usuarioDao.restore(usuarioDto);

            if (usuarioDto.getLdap() != null && usuarioDto.getLdap().equalsIgnoreCase("s")) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public UsuarioDTO restoreByID(final Integer id) throws ServiceException, LogicException {
        try {
            return this.getDao().restoreByID(id);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void gerarCarga(final File arquivo) throws IOException {
        final BufferedReader arquivoReader = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "WINDOWS-1252"));

        boolean primeiraLinha = true;

        int contador = 0;
        while (arquivoReader.ready()) {
            final LoginDTO login = new LoginDTO();

            final ADUserDTO usuarioAd = new ADUserDTO();

            final String linhaAux = arquivoReader.readLine();

            String linha = null;
            if (linhaAux != null) {
                linha = new String(linhaAux);
                linha = linha.trim();
            }

            String[] linhaQuebrada = null;
            if (linha != null) {
                linhaQuebrada = linha.split("\n");
            }

            if (linhaQuebrada != null && linhaQuebrada.length > 0) {
                for (final String string : linhaQuebrada) {
                    final String[] colunasArray = string.split(";");

                    if (colunasArray.length > 0) {
                        // TÍTULO
                        if (primeiraLinha) {
                            primeiraLinha = false;
                            break;
                        }

                        int numeroDaColuna = 0;
                        String sufixoEmail = "";
                        for (final String conteudoDaCelula : colunasArray) {
                            if (conteudoDaCelula != null && !StringUtils.isEmpty(conteudoDaCelula)) {
                                // USERNAME
                                if (numeroDaColuna == 0) {

                                    usuarioAd.setSN(conteudoDaCelula);
                                    usuarioAd.setDisplayName(conteudoDaCelula);
                                    usuarioAd.setCN(conteudoDaCelula);

                                    numeroDaColuna++;
                                    continue;
                                }

                                // SUFIXO
                                if (numeroDaColuna == 1) {
                                    sufixoEmail = conteudoDaCelula;

                                    numeroDaColuna++;
                                    continue;
                                }

                                // LOGIN
                                if (numeroDaColuna == 2) {
                                    login.setUser(conteudoDaCelula.trim());

                                    login.setSenha("");

                                    usuarioAd.setsAMAccountName(conteudoDaCelula.trim());

                                    if (usuarioAd.getsAMAccountName() != null && !StringUtils.isEmpty(usuarioAd.getsAMAccountName())) {
                                        usuarioAd.setMail(login.getUser().trim() + sufixoEmail.trim());
                                        usuarioAd.setMailNickname(usuarioAd.getsAMAccountName().trim());
                                    }
                                    numeroDaColuna++;
                                }
                            }

                            try {
                                this.sincronizaUsuarioAD(usuarioAd, login, Boolean.TRUE);

                                contador++;

                                System.out.println(">>> SUCESSO NA GRAVAÇÃO  >> Usuário >> " + contador + " >>> " + usuarioAd.getSN() + "Login: >> " + login.getUser());
                            } catch (final ServiceException e) {
                                System.out.println(">>> ERROR >> Erro ao gravar o usuário >>> " + usuarioAd.getSN());
                                e.printStackTrace();
                            } catch (final Exception e) {
                                System.out.println(">>> ERROR >> Erro ao gravar o usuário >>> " + usuarioAd.getSN());
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        arquivoReader.close();
    }

    /**
     * Obtem Nome do Usuário de acordo com o Parâmetro LDAP_ATRIBUTO.
     *
     * @param usuarioAd
     * @param numeroRegistros
     * @param ldapAtributo
     * @return String
     * @author Vadoilo Damasceno
     */
    private String obterNomeUsuario(final ADUserDTO usuarioAd, final Integer numeroRegistros, final String ldapAtributo) {
        if (numeroRegistros > 0) {
            for (int i = 0; i <= numeroRegistros; i++) {
                if (ldapAtributo.equalsIgnoreCase("CN")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getCN());
                }
                if (ldapAtributo.equalsIgnoreCase("description")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getDescription());
                }
                if (ldapAtributo.equalsIgnoreCase("displayName")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getDisplayName());
                }
                if (ldapAtributo.equalsIgnoreCase("DN")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getDN());
                }
                if (ldapAtributo.equalsIgnoreCase("givenName")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getGivenName());
                }
                if (ldapAtributo.equalsIgnoreCase("homeDrive")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getHomeDrive());
                }
                if (ldapAtributo.equalsIgnoreCase("name")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getName());
                }
                if (ldapAtributo.equalsIgnoreCase("objectCategory")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getObjectCategory());
                }
                if (ldapAtributo.equalsIgnoreCase("objectClass")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getObjectClass());
                }
                if (ldapAtributo.equalsIgnoreCase("physicalDeliveryOfficeName")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getPhysicalDeliveryOfficeName());
                }
                if (ldapAtributo.equalsIgnoreCase("profilePath")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getProfilePath());
                }
                if (ldapAtributo.equalsIgnoreCase("sAMAccountName")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getsAMAccountName());
                }
                if (ldapAtributo.equalsIgnoreCase("SN")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getSN());
                }
                if (ldapAtributo.equalsIgnoreCase("userAccountControl")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getUserAccountControl());
                }
                if (ldapAtributo.equalsIgnoreCase("userPrincipalName")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getUserPrincipalName());
                }
                if (ldapAtributo.equalsIgnoreCase("homeMDB")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getHomeMDB());
                }
                if (ldapAtributo.equalsIgnoreCase("legacyExchangeDN")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getLegacyExchangeDN());
                }
                if (ldapAtributo.equalsIgnoreCase("mail")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getMail());
                }
                if (ldapAtributo.equalsIgnoreCase("mAPIRecipient")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getmAPIRecipient());
                }
                if (ldapAtributo.equalsIgnoreCase("mailNickname")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getMailNickname());
                }
                if (ldapAtributo.equalsIgnoreCase("c")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getC());
                }
                if (ldapAtributo.equalsIgnoreCase("company")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getCompany());
                }
                if (ldapAtributo.equalsIgnoreCase("department")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getDepartment());
                }
                if (ldapAtributo.equalsIgnoreCase("homephone")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getHomephone());
                }
                if (ldapAtributo.equalsIgnoreCase("l")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getL());
                }
                if (ldapAtributo.equalsIgnoreCase("location")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getLocation());
                }
                if (ldapAtributo.equalsIgnoreCase("manager")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getManager());
                }
                if (ldapAtributo.equalsIgnoreCase("mobile")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getMobile());
                }
                if (ldapAtributo.equalsIgnoreCase("OU")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getOU());
                }
                if (ldapAtributo.equalsIgnoreCase("postalCode")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getPostalCode());
                }
                if (ldapAtributo.equalsIgnoreCase("st")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getSt());
                }
                if (ldapAtributo.equalsIgnoreCase("streetAddress")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getStreetAddress());
                }
                if (ldapAtributo.equalsIgnoreCase("telephoneNumber")) {
                    return LDAPUtils.nullToNaoDisponivel(usuarioAd.getTelephoneNumber());
                }
            }
        }
        return LDAPUtils.nullToNaoDisponivel(usuarioAd.getsAMAccountName());
    }

    @Override
    public boolean listSeVazio() throws Exception {
        final UsuarioDao usuarioDao = new UsuarioDao();
        return usuarioDao.listSeVazio();
    }

    @Override
    public Collection listAtivos() throws Exception {
        final UsuarioDao usuarioDao = new UsuarioDao();
        return usuarioDao.listAtivos();
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        UsuarioDTO usuarioDto = (UsuarioDTO) model;

        try {
            usuarioDto = (UsuarioDTO) this.getDao().restore(model);
			if(usuarioDto != null)
            usuarioDto.setColGrupoEmpregado(this.getGrupoempregadoDao().findByIdEmpregadoNome(usuarioDto.getIdEmpregado()));
        } catch (final Exception e) {
            e.printStackTrace();
            throw new ServiceException(e);
        }
        return usuarioDto;
    }

    @Override
    public Long retornaQuantidadeUsuariosAtivos() throws Exception {
        return this.getDao().retornaQuantidadeUsuariosAtivos();
    }

    /**
     * Consulta os usuarios pelo campo nome
     *
     * @param nome
     * @return
     * @throws Exception
     */
    @Override
    public Collection<UsuarioDTO> consultarUsuarioPorNomeAutoComplete(final String nome) throws Exception {
        try {
            return this.getDao().consultarUsuarioPorNomeAutoComplete(nome);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    private GrupoEmpregadoDao grupoempregadoDao;

    private GrupoEmpregadoDao getGrupoempregadoDao() {
        if (grupoempregadoDao == null) {
            grupoempregadoDao = new GrupoEmpregadoDao();
        }
        return grupoempregadoDao;
    }

}
