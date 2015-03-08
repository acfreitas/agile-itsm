package br.com.centralit.citcorpore.integracao.ad;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.SizeLimitExceededException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.PagedResultsControl;
import javax.naming.ldap.PagedResultsResponseControl;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.ADUserDTO;
import br.com.centralit.citcorpore.bean.ServidorContextoDTO;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings({ "unchecked", "rawtypes" })
public final class LDAPUtils {

    protected static LDAPUtils instanceLdap;

    public static String INITIAL_CTX = "com.sun.jndi.ldap.LdapCtxFactory";

    public static String SERVIDOR = "";// = "ldap://stj.gov.br:389";

    public static String CONNECTION_TYPE = "simple";

    public static String ADMIN_DN = "";// = "ldapciep";

    public static String ADMIN_PW = "";// = "ldapses0p";

    public static String BASE_DN = "";// = "dc=stj,dc=gov,dc=br";

    public static String SUB_DOMINIO = ""; //"ou =*, dc=*";

    public static String MSG_ERROR_LDAP_CONNECTION = "Não foi possível obter um contexto LDAP";

    public static String MSG_ERROR_LDAP_VALIDATION_USER = "Username ou Password Inválida";

    public static String FILTRO = "";

    public static String LDAP_FILTRO = "";

    public static String LDAP_ATRIBUTO = "";

    public static String GRUPOPADRAO = "";

    public static String ID_PERFIL_ACESSO_DEFAULT = "";

    public static String NUMERO_COLABORADORES_CONSULTA_AD = "";


    private LDAPUtils() {}

    public static LDAPUtils getInstance() {
        if (instanceLdap == null) {
            instanceLdap = new LDAPUtils();
        }
        return instanceLdap;
    }

    static {
        inicializaDados();
    }

    private static void inicializaDados() {
        String[] loginAd;
        String[] ldad_sufixo;
        String admin_dn_login = "";
        String admin_dominio = "";

        try {

            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LOGIN_AD, " ").trim().equalsIgnoreCase("")) {
                admin_dn_login = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LOGIN_AD, " ").trim();
                ADMIN_DN = admin_dn_login;
            }

            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO, " ").trim().equalsIgnoreCase("")) {
                loginAd = admin_dn_login.split(";");
                ldad_sufixo = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO, " ").trim().split(";");
                for (int i = 0; i < ldad_sufixo.length; i++) {
                    admin_dominio += loginAd[i]+ldad_sufixo[i]+";";
                }
                ADMIN_DN = admin_dominio;
            }
            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SENHA_AD, " ").trim().equalsIgnoreCase("")) {
                ADMIN_PW = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SENHA_AD, " ").trim();
            }
            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_URL, " ").trim().equalsIgnoreCase("")) {
                SERVIDOR = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_URL, " ").trim();
            }
            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DOMINIO_AD, " ").trim().equalsIgnoreCase("")) {
                BASE_DN = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DOMINIO_AD, " ").trim();
            }

            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_SUBDOMINIO, " ").trim().equalsIgnoreCase("")) {
                SUB_DOMINIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_SUBDOMINIO, " ").trim();
            }

            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_FILTRO, " ").trim().equalsIgnoreCase("")) {
                LDAP_FILTRO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_FILTRO, " ").trim();
            }
            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_ATRIBUTO, " ").trim().equalsIgnoreCase("")) {
                LDAP_ATRIBUTO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_ATRIBUTO, " ").trim();
            }
            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT, " ").trim().equalsIgnoreCase("")) {
                ID_PERFIL_ACESSO_DEFAULT = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT, " ").trim();
            }
            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP, " ").trim().equalsIgnoreCase("")) {
                GRUPOPADRAO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP, " ").trim();
            }
            if (!ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD, " ").trim().equalsIgnoreCase("")) {
                NUMERO_COLABORADORES_CONSULTA_AD = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD, " ").trim();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ADUserDTO autenticacaoAD(String login, String password) throws Exception {

        inicializaDados();
        ADUserDTO adUserAux = null;

        NamingEnumeration results = null;
        NamingEnumeration cursor = null;

        Collection<ServidorContextoDTO> listContexto = null;

        SearchControls search = null;
        SearchControls searchUser = null;
        String filtro = null;
        boolean usuarioAutenticado = false;

        listContexto = createLdapConnection();

        for (ServidorContextoDTO dirContext : listContexto) {

            if (dirContext != null && dirContext.isAtivo()) {

                search = new SearchControls();
                search.setSearchScope(SearchControls.SUBTREE_SCOPE);

                searchUser = new SearchControls();
                searchUser.setSearchScope(SearchControls.SUBTREE_SCOPE);


                String[] atributosParaRetornar = new String[1];
                /**
                 * Realiza a Validação Caso o parametro openLDAP esteja ativo
                 * sambaNTPassword - Atributo de Senha configurada para o openLDAP
                 * distinguishedName - (Default) Atributo de senha padrão
                 */
                String parametroOpenLdap = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_OPEN_LDAP, "N");
                if (parametroOpenLdap.equals("S")) {
                    atributosParaRetornar[0] = "uid";
                } else {
                    atributosParaRetornar[0] = "distinguishedName";
                }

                searchUser.setReturningAttributes(atributosParaRetornar);

                String[] atributosRetorno = getADFieldList();
                search.setReturningAttributes(atributosRetorno);

                /**
                 * uid - Referencia ao CPF
                 * sAMAccountName - (Default) Login do usuario
                 */
                if (parametroOpenLdap.equals("S")) {
                    filtro = "(&(uid=" + login + "))";
                } else {
                    filtro = "(&(sAMAccountName=" + login + "))";
                }

                try {

                    String subDominioValido ="";
                    cursor = dirContext.getDirContext().search(dirContext.getBaseDominio(), filtro, searchUser);

                    if (cursor.hasMoreElements()) {

                        SearchResult result = (SearchResult) cursor.nextElement();
                        Attributes att = result.getAttributes();
                        String dn = "";
                        /**
                         * sambaNTPassword - Atributo de Senha configurada para o openLDAP distinguishedName - (Default) Atributo de senha padrão
                         */
                        if (parametroOpenLdap.equals("S")) {
                            dn = (String) att.get("uid").get();
                            if(dirContext.getSubDominio() != null){
                                String[] subDomino = dirContext.getSubDominio().split(";");
                                for(int i = 0 ;i < subDomino.length;i++){
                                    if(validateUser("uid=" + dn + "," + subDomino[i]+","+dirContext.getBaseDominio(),  password)){
                                        subDominioValido = subDomino[i];
                                        usuarioAutenticado = true;
                                        break;
                                    }
                                }
                            }else{
                                usuarioAutenticado = validateUser("uid=" + dn + "," + dirContext.getBaseDominio(),  password);
                            }
                        } else {
                            dn = (String) att.get("distinguishedName").get();
                            usuarioAutenticado = validateUser(dn, password);
                        }
                    }

                    if (usuarioAutenticado) {
                        if(subDominioValido!=""){
                            results = dirContext.getDirContext().search(subDominioValido+","+dirContext.getBaseDominio(), filtro, search);
                        }else{
                            results = dirContext.getDirContext().search(dirContext.getBaseDominio(), filtro, search);
                        }

                        if (results != null && results.hasMoreElements()) {

                            while (results != null && results.hasMoreElements()) {

                                adUserAux = new ADUserDTO();

                                System.out.println("------------- INÍCIO --------------");
                                String retorno = null;

                                SearchResult searchResult = (SearchResult) results.next();

                                for (int i = 0; i < atributosRetorno.length; i++) {

                                    if (searchResult.getAttributes().get(atributosRetorno[i]) != null) {

                                        retorno = nullToNaoDisponivel(searchResult.getAttributes().get(atributosRetorno[i]).toString());

                                        setPropertyToAdUserDTO(adUserAux, atributosRetorno[i], retorno.split(":")[1]);
                                    }
                                }

                                System.out.println(adUserAux.getUserPrincipalName() + " - " + adUserAux.getsAMAccountName());

                                System.out.println("------------- FIM --------------");
                            }
                            adUserAux.setIdGrupo(dirContext.getGrupoPadrao());
                            adUserAux.setLdapAtributo(dirContext.getLdpaAtributo());
                            break;
                        }
                    }

                } catch (NamingException e) {
                    System.out.println(MSG_ERROR_LDAP_CONNECTION);
                    e.printStackTrace();
                }
            }
        }
        return adUserAux;
    }

    /**
     * Sincroniza todos os usuários do AD com o CITSMart
     *
     * @return <code>ArrayList<ADUserDTO></code>
     * @author Vadoilo Damasceno
     * @throws Exception
     */
    public static synchronized Collection<ADUserDTO> sincronizaUsuariosAD() throws Exception {

        UsuarioService usuarioService;
        ArrayList<ADUserDTO> listaUsuariosAD = new ArrayList<ADUserDTO>();
        inicializaDados();
        ADUserDTO adUserAux = null;

        NamingEnumeration results = null;

        SearchControls searchControls = null;
        String filtro = null;

        for (ServidorContextoDTO context : dadosConexao()) {

            LdapContext ldapContexto = context.getLdapContext();

            String var = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_OPEN_LDAP, "N");


            if (ldapContexto != null) {

                searchControls = new SearchControls();
                searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

                try {
                    System.out.println("  \n \n LDAP - ROTINA DE SINCRONIZAÇÃO COM AD INICIADA  \n \n");
                    String numColaboradoresPorPag;
                    Integer pageSize = new Integer(4000);

                    if (context.getNumeroColaboradores() != null && !context.getNumeroColaboradores().equals("")) {
                        numColaboradoresPorPag = context.getNumeroColaboradores();
                    }else{
                        numColaboradoresPorPag = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD, "4000").trim();
                    }


                    if (numColaboradoresPorPag != null && !StringUtils.isEmpty(numColaboradoresPorPag)) {

                        pageSize = Integer.parseInt(numColaboradoresPorPag.trim());
                    }

                    byte[] cookie = null;
                    int contador = 0;
                    int total;
                    int numPagina = 0;

                    String[] atributosRetorno = getADFieldList();
                    searchControls.setReturningAttributes(atributosRetorno);

                    //filtro caso venha pelo context ele pega esse valor, senão pega o valor padrão do parametro;
                    if (context.getLdpaFiltro() != null && context.getLdpaFiltro().equals("")) {
                        filtro = context.getLdpaFiltro();
                    }else{
                        filtro = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_FILTRO, "(&(objectCategory=person)(objectClass=user))");
                    }

                    if(filtro.indexOf(";") > 0) {
                        filtro = context.getLdpaFiltro().split(";")[0];
                    }

                    usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

                    if(var.equals("S")) {
                        ldapContexto.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.NONCRITICAL) });
                    } else {
                        ldapContexto.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.CRITICAL) });
                    }

                    do {

                        numPagina++;

                        results = ldapContexto.search(context.getBaseDominio(), filtro, searchControls);

                        System.out.println(" \n \n LDAP - PÁGINA " + numPagina + " - INICIADA \n \n");

                        while (results != null && results.hasMoreElements()) {
                            contador++;

                            adUserAux = new ADUserDTO();

                            System.out.println("---------------------------------------");

                            String retorno = null;
                            SearchResult searchResult = (SearchResult) results.next();

                            for (int i = 0; i < atributosRetorno.length; i++) {

                                if (searchResult.getAttributes().get(atributosRetorno[i]) != null) {

                                    retorno = nullToNaoDisponivel(searchResult.getAttributes().get(atributosRetorno[i]).toString());
                                    setPropertyToAdUserDTO(adUserAux, atributosRetorno[i], retorno.split(":")[1]);
                                }

                            }

                            System.out.println(contador + " - " + adUserAux.getsAMAccountName() + " - " + adUserAux.getUserPrincipalName());
                            System.out.println("---------------------------------------");

                            listaUsuariosAD.add(adUserAux);
                            adUserAux.setLdapAtributo(context.getLdpaAtributo());
                            adUserAux.setIdGrupo(context.getGrupoPadrao());
                            usuarioService.sincronizaUsuarioAD(adUserAux, 0);
                        }

                        // Examine the paged results control response
                        Control[] controls = ldapContexto.getResponseControls();
                        if (controls != null) {
                            for (int i = 0; i < controls.length; i++) {
                                if (controls[i] instanceof PagedResultsResponseControl) {
                                    PagedResultsResponseControl prrc = (PagedResultsResponseControl) controls[i];
                                    total = prrc.getResultSize();
                                    cookie = prrc.getCookie();
                                } else {
                                    // Handle other response controls (if any)
                                }
                            }
                        }

                        // Re-activate paged results]
                        if(var.equals("S")) {
                            ldapContexto.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.NONCRITICAL) });
                        } else {
                            ldapContexto.setRequestControls(new Control[] { new PagedResultsControl(pageSize, cookie, Control.CRITICAL) });
                        }

                    } while (cookie != null);

                    System.out.println("LDAP - ROTINA DE SINCRONIZAÇÃO COM AD FINALIZADA.");
                    System.out.println("LDAP - TOTAL DE REGISTROS RECUPERADOS = " + contador);

                } catch (AuthenticationException ex) {

                    String[] erro = ex.toString().split(":");
                    String[] e = erro[erro.length - 1].split(",");

                    if (e[1].compareTo(" data 525") == 0) {
                        System.out.println("Usuario invalido");
                    } else if (e[1].compareTo(" data 52e") == 0) {
                        System.out.println("Senha invalida");
                    }

                } catch (SizeLimitExceededException sizeLimit) {

                    System.out.println("Limite excedeu...");
                    sizeLimit.printStackTrace();

                } catch (NamingException ex) {
                    ex.printStackTrace();
                    System.out.println("NamingException is: " + ex);

                } catch (IOException ie) {
                    System.err.println("LDAP - IOException ");
                    ie.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        ldapContexto.close();
                        ldapContexto = null;
                    } catch (NamingException e) {
                        System.out.println("LDAP - NamingException - não foi possível fechar ldapContexto.");
                        e.printStackTrace();
                    }
                }
            }
        }
        return listaUsuariosAD;
    }

    /**
     * Consulta empregado a partir da Tela de Solicitação Serviço. Pesquisa é realizada pelo Login do Usuário.
     *
     * @param login
     * @param idGrupoSolicitante
     * @return ADUserDTO
     * @throws Exception
     */
    public static ArrayList<ADUserDTO> consultaEmpregado(String login, Integer idGrupoSolicitante) throws Exception {

        UsuarioService usuarioService;

        inicializaDados();
        ADUserDTO adUserAux = null;

        NamingEnumeration results = null;
        NamingEnumeration cursor = null;

        String flag = null;
        Hashtable active = new Hashtable();
        //	DirContext contexto = null;
        Collection<ServidorContextoDTO> listContexto = null;
        SearchControls search = null;
        SearchControls searchUser = null;
        ArrayList<ADUserDTO> listaUsuariosAD = new ArrayList<ADUserDTO>();
        String filtro = null;
        boolean usuarioAutenticado = false;

        listContexto = createLdapConnection();

        for (ServidorContextoDTO dirContext : listContexto) {
            String var = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_OPEN_LDAP, "N");


            if (dirContext != null) {

                search = new SearchControls();
                search.setSearchScope(SearchControls.SUBTREE_SCOPE);

                searchUser = new SearchControls();
                searchUser.setSearchScope(SearchControls.SUBTREE_SCOPE);

                String[] atributosParaRetornar = new String[1];

                if(var.equals("S")) {
                    atributosParaRetornar[0] = "uid";
                } else {
                    atributosParaRetornar[0] = "distinguishedName";
                }

                searchUser.setReturningAttributes(atributosParaRetornar);

                String[] atributosRetorno = getADFieldList();
                search.setReturningAttributes(atributosRetorno);

                if(var.equals("S")) {
                    filtro = "(&(uid=" + login + "))";
                } else {
                    filtro = "(&(sAMAccountName=" + login + "))";
                }

                try {

                    usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

                    results = dirContext.getDirContext().search(dirContext.getBaseDominio(), filtro, search);

                    if (results != null && results.hasMoreElements()) {

                        while (results != null && results.hasMoreElements()) {

                            adUserAux = new ADUserDTO();

                            System.out.println("------------- INÍCIO --------------");
                            String retorno = null;

                            SearchResult searchResult = (SearchResult) results.next();

                            for (int i = 0; i < atributosRetorno.length; i++) {

                                if (searchResult.getAttributes().get(atributosRetorno[i]) != null) {

                                    retorno = nullToNaoDisponivel(searchResult.getAttributes().get(atributosRetorno[i]).toString());

                                    setPropertyToAdUserDTO(adUserAux, atributosRetorno[i], retorno.split(":")[1]);
                                }
                            }

                            System.out.println(adUserAux.getUserPrincipalName() + " - " + adUserAux.getsAMAccountName());
                            System.out.println("------------- FIM --------------");

                            usuarioService.sincronizaUsuarioAD(adUserAux, idGrupoSolicitante);

                            listaUsuariosAD.add(adUserAux);
                        }
                    }
                } catch (AuthenticationException ex) {

                    String[] erro = ex.toString().split(":");
                    String[] e = erro[erro.length - 1].split(",");

                    if (e[1].compareTo(" data 525") == 0) {
                        flag = "Usuario invalido";
                    } else if (e[1].compareTo(" data 52e") == 0) {
                        flag = "Sennha invalida";
                    }

                } catch (NamingException ex) {

                    System.out.println("NamingException is: " + ex);

                } catch (Exception e) {

                    e.printStackTrace();

                } finally {

                    System.out.println(flag);

                }
            }

        }
        return listaUsuariosAD;
    }

    private static void setPropertyToAdUserDTO(ADUserDTO adUserObject, String name, String value) throws Exception {
        String var = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_OPEN_LDAP, "N");
        if (name.equalsIgnoreCase("CN")) {
            adUserObject.setCN(value);
            if(var.equals("S")) {
                adUserObject.setUserPrincipalName(value);
            }
            return;
        }
        if (name.equalsIgnoreCase("description")) {
            adUserObject.setDescription(value);
            return;
        }
        if (name.equalsIgnoreCase("displayName")) {
            adUserObject.setDisplayName(value);
            return;
        }
        if (name.equalsIgnoreCase("DN")) {
            adUserObject.setDN(value);
            return;
        }
        if (name.equalsIgnoreCase("givenName")) {
            adUserObject.setGivenName(value);
            return;
        }

        if (name.equalsIgnoreCase("homeDrive")) {
            adUserObject.setHomeDrive(value);
            return;
        }
        if (name.equalsIgnoreCase("name")) {
            adUserObject.setName(value);
            return;
        }
        if (name.equalsIgnoreCase("objectCategory")) {
            adUserObject.setObjectCategory(value);
            return;
        }
        if (name.equalsIgnoreCase("objectClass")) {
            adUserObject.setObjectClass(value);
            return;
        }
        if (name.equalsIgnoreCase("physicalDeliveryOfficeName")) {
            adUserObject.setPhysicalDeliveryOfficeName(value);
            return;
        }

        if (name.equalsIgnoreCase("profilePath")) {
            adUserObject.setProfilePath(value);
            return;
        }
        if (name.equalsIgnoreCase("uid") && var.equals("S")) {
            adUserObject.setsAMAccountName(value);
            return;
        } else if (name.equalsIgnoreCase("sAMAccountName")) {
            adUserObject.setsAMAccountName(value);
            return;
        }
        if (name.equalsIgnoreCase("SN")) {
            adUserObject.setSN(value);
            return;
        }
        if (name.equalsIgnoreCase("userAccountControl")) {
            adUserObject.setUserAccountControl(value);
            return;
        }
        if (name.equalsIgnoreCase("userPrincipalName")) {
            adUserObject.setUserPrincipalName(value);
            return;
        }

        if (name.equalsIgnoreCase("homeMDB")) {
            adUserObject.setHomeMDB(value);
            return;
        }
        if (name.equalsIgnoreCase("legacyExchangeDN")) {
            adUserObject.setLegacyExchangeDN(value);
            return;
        }
        if (name.equalsIgnoreCase("mail")) {
            adUserObject.setMail(value);
            return;
        }
        if (name.equalsIgnoreCase("mAPIRecipient")) {
            adUserObject.setmAPIRecipient(value);
            return;
        }
        if (name.equalsIgnoreCase("mailNickname")) {
            adUserObject.setMailNickname(value);
            return;
        }
        if (name.equalsIgnoreCase("c")) {
            adUserObject.setC(value);
            return;
        }
        if (name.equalsIgnoreCase("company")) {
            adUserObject.setCompany(value);
            return;
        }
        if (name.equalsIgnoreCase("department")) {
            adUserObject.setDepartment(value);
            return;
        }
        if (name.equalsIgnoreCase("homephone")) {
            adUserObject.setHomephone(value);
            return;
        }
        if (name.equalsIgnoreCase("l")) {
            adUserObject.setL(value);
            return;
        }
        if (name.equalsIgnoreCase("location")) {
            adUserObject.setLocation(value);
            return;
        }
        if (name.equalsIgnoreCase("manager")) {
            adUserObject.setManager(value);
            return;
        }
        if (name.equalsIgnoreCase("mobile")) {
            adUserObject.setMobile(value);
            return;
        }
        if (name.equalsIgnoreCase("OU")) {
            adUserObject.setOU(value);
            return;
        }
        if (name.equalsIgnoreCase("postalCode")) {
            adUserObject.setPostalCode(value);
            return;
        }
        if (name.equalsIgnoreCase("st")) {
            adUserObject.setSt(value);
            return;
        }
        if (name.equalsIgnoreCase("streetAddress")) {
            adUserObject.setStreetAddress(value);
            return;
        }
        if (name.equalsIgnoreCase("telephoneNumber")) {
            adUserObject.setTelephoneNumber(value);
            return;
        }

    }

    public static String[] getADFieldList() {
        String[] fields = { "CN", "description", "displayName", "DN", "givenName", "homeDrive", "name", "objectCategory", "objectClass", "physicalDeliveryOfficeName", "profilePath", "sAMAccountName", "SN",
                "userAccountControl", "userPrincipalName", "homeMDB", "legacyExchangeDN", "mail", "mAPIRecipient", "mailNickname", "c", "company", "department", "homephone", "l", "location", "manager", "mobile", "OU",
                "postalCode", "st", "streetAddress", "telephoneNumber", "uid" };

        return fields;
    }

    public static String nullToNaoDisponivel(String value) {
        return value == null ? "Não disponível" : value;
    }

    private static Collection<ServidorContextoDTO> createLdapConnection() {

        DirContext contexto = null;
        Hashtable env = new Hashtable();
        Collection<DirContext> ctx = new ArrayList<DirContext>();
        ServidorContextoDTO servidorContextoDTO = null;
        Collection<ServidorContextoDTO> listServidor = new ArrayList<ServidorContextoDTO>();

        // Especifica INITIAL CONTEXT
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CTX);

        String[] servidor = SERVIDOR.trim().split(";");
        String[] admin_dn = ADMIN_DN.trim().split(";");
        String[] admin_pw = ADMIN_PW.trim().split(";");


        String[] ldap_filtro = null;
        String[] ldap_atributo = null;
        String[] grupo_padrao = null;
        String[] perfil_acesso = null;
        String[] numero_colaboradores = null;
        String[] base_dominio = null;
        String[] sub_pastaDominio = null;

        if (!LDAP_FILTRO.equals("")) {
            ldap_filtro = LDAP_FILTRO.trim().split(";");
        }
        if (!LDAP_ATRIBUTO.equals("")) {
            ldap_atributo  = LDAP_ATRIBUTO.trim().split(";");
        }
        if (!GRUPOPADRAO.equals("")) {
            grupo_padrao = GRUPOPADRAO .trim().split(";");
        }
        if (!ID_PERFIL_ACESSO_DEFAULT.equals("")) {
            perfil_acesso  = ID_PERFIL_ACESSO_DEFAULT.trim().split(";");
        }
        if (!NUMERO_COLABORADORES_CONSULTA_AD.equals("")) {
            numero_colaboradores   = NUMERO_COLABORADORES_CONSULTA_AD .trim().split(";");
        }
        if (!BASE_DN.equals("")) {
            base_dominio = BASE_DN.trim().split(";");
        }
        if (!SUB_DOMINIO.equals("")) {
            sub_pastaDominio = SUB_DOMINIO.trim().split("&");
        }

        for (int i = 0; i < servidor.length; i++) {

            servidorContextoDTO = new ServidorContextoDTO();

            // Especifica o IP/Nome e a porta do servidor LDAP
            env.put(Context.PROVIDER_URL, servidor[i]);
            // Usuário ADMIN0
            env.put(Context.SECURITY_PRINCIPAL, admin_dn[i]);
            // Senha ADMIN
            env.put(Context.SECURITY_CREDENTIALS, admin_pw[i]);
            // Tipo de Conexão
            env.put(Context.SECURITY_AUTHENTICATION, CONNECTION_TYPE);
            // Cria um Initial Context

            try {
                contexto = new InitialDirContext(env);

                if (contexto != null) {
                    servidorContextoDTO.setDirContext(contexto);
                    servidorContextoDTO.setServidor(servidor[i]);
                    if (grupo_padrao != null && grupo_padrao.length != 0) {
                        servidorContextoDTO.setGrupoPadrao(grupo_padrao[i]);
                    }
                    if (ldap_atributo != null && ldap_atributo.length != 0) {
                        servidorContextoDTO.setLdpaAtributo(ldap_atributo[i]);
                    }
                    if (ldap_filtro != null && ldap_filtro.length != 0) {
                        servidorContextoDTO.setLdpaFiltro(ldap_filtro[i]);
                    }
                    if (numero_colaboradores != null && numero_colaboradores.length != 0) {
                        servidorContextoDTO.setNumeroColaboradores(numero_colaboradores[i]);
                    }
                    if (perfil_acesso != null && perfil_acesso.length != 0) {
                        servidorContextoDTO.setPerfilAcesso(perfil_acesso[i]);
                    }
                    if (base_dominio != null && base_dominio.length != 0) {
                        servidorContextoDTO.setBaseDominio(base_dominio[i]);
                    }
                    if (sub_pastaDominio != null && sub_pastaDominio.length != 0) {
                        servidorContextoDTO.setSubDominio(sub_pastaDominio[i]);
                    }
                    servidorContextoDTO.setAtivo(true);
                    listServidor.add(servidorContextoDTO);
                }

                //contexto.close();
            } catch (NamingException e) {
                servidorContextoDTO.setDirContext(contexto);
                servidorContextoDTO.setServidor(servidor[i]);
                servidorContextoDTO.setAtivo(false);
                listServidor.add(servidorContextoDTO);

                System.out.println(MSG_ERROR_LDAP_CONNECTION);
                e.printStackTrace();
            }
        }

        return listServidor;
    }

    private static boolean validateUser(String dn, String senha) {

        DirContext ldapCtx = null;
        boolean bResult = false;

        Hashtable env = new Hashtable();
        String[] servidor = SERVIDOR.trim().split(";");

        for (int i = 0; i < servidor.length; i++) {
            // Especifica INITIAL CONTEXT
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CTX);
            // Especifica o IP/Nome e a porta do servidor LDAP
            env.put(Context.PROVIDER_URL, servidor[i]);
            // Ldap Distingued Name
            env.put(Context.SECURITY_PRINCIPAL, dn);
            // Senha Usuário
            env.put(Context.SECURITY_CREDENTIALS, senha);
            // Tipo de Conexão
            env.put(Context.SECURITY_AUTHENTICATION, CONNECTION_TYPE);

            try {
                // Cria um Initial Context
                ldapCtx = new InitialDirContext(env);
            } catch (AuthenticationException auEx) {
            	/**
				 * A forma como esta rotina está implementada faz com que o sistema tente autenticar o usuário para cada subdomínio, até o final da lista. Para cada subdomínio, portanto, é exibida
				 * essa mensagem, caso o usuário não consiga autenticação. Para evitar a poluição do LOG esta mensagem foi comentada. valdoilo.damasceno
				 */
                //System.out.println(MSG_ERROR_LDAP_VALIDATION_USER + "[LDAP: error code 49 - Invalid Credentials]");
                //auEx.printStackTrace();
            } catch (NamingException ne) {
                System.out.println(MSG_ERROR_LDAP_CONNECTION);
                ne.printStackTrace();
            } finally {
                if (ldapCtx != null) {
                    bResult = true;
                    try {
                        ldapCtx.close();
                    } catch (NamingException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
        return bResult;
    }

    /**
     * Testa conexão com LDAP.
     *
     * @return boolean
     * @author valdoilo.damasceno
     * @throws Exception
     */
    public static Collection<ADUserDTO> testarConexao() throws Exception {

        UsuarioService usuarioService;

        inicializaDados();
        ADUserDTO adUserAux = null;

        NamingEnumeration results = null;
        Collection<ADUserDTO> listAdUserAux = new ArrayList<ADUserDTO>();
        Hashtable active = new Hashtable();

        SearchControls searchControls = null;
        String filtro = null;
        boolean usuarioAutenticado = false;

        for (ServidorContextoDTO context : dadosConexao()) {
            adUserAux = new ADUserDTO();
            LdapContext ldapContexto = context.getLdapContext();

            ArrayList<ADUserDTO> listaUsuariosAD = new ArrayList<ADUserDTO>();
            String var = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_OPEN_LDAP, "N");

            if (ldapContexto != null) {
                searchControls = new SearchControls();
                searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

                try {
                    Integer pageSize = new Integer(1);

                    byte[] cookie = null;
                    int contador = 0;
                    int total;
                    int numPagina = 0;

                    String[] atributosRetorno = getADFieldList();
                    searchControls.setReturningAttributes(atributosRetorno);

                    //filtro caso venha pelo context ele pega esse valor, senão pega o valor padrão do parametro;
                    if (context.getLdpaFiltro() != null && !context.getLdpaFiltro().equalsIgnoreCase("")) {
                        filtro = context.getLdpaFiltro();
                    }else{
                        filtro = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_FILTRO, "(&(objectCategory=person)(objectClass=user))");
                    }

                    usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);

                    if(var.equals("S")) {
                        ldapContexto.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.NONCRITICAL) });
                    } else {
                        ldapContexto.setRequestControls(new Control[] { new PagedResultsControl(pageSize, Control.CRITICAL) });
                    }

                    numPagina++;
                    results = ldapContexto.search(context.getBaseDominio(), filtro, searchControls);

                    while (results != null && results.hasMoreElements() && contador < 1) {
                        contador++;

                        String retorno = null;
                        SearchResult searchResult = (SearchResult) results.next();

                        for (int i = 0; i < atributosRetorno.length; i++) {
                            if (searchResult.getAttributes().get(atributosRetorno[i]) != null) {
                                retorno = nullToNaoDisponivel(searchResult.getAttributes().get(atributosRetorno[i]).toString());
                                setPropertyToAdUserDTO(adUserAux, atributosRetorno[i], retorno.split(":")[1]);
                            }

                        }
                    }
                } catch (AuthenticationException ex) {
                    String[] erro = ex.toString().split(":");
                    String[] e = erro[erro.length - 1].split(",");

                    if (e[1].compareTo(" data 525") == 0) {
                        System.out.println("Usuario invalido");
                    } else if (e[1].compareTo(" data 52e") == 0) {
                        System.out.println("Senha invalida");
                    }
                } catch (SizeLimitExceededException sizeLimit) {
                    System.out.println("Limite excedeu...");
                    sizeLimit.printStackTrace();
                } catch (NamingException ex) {
                    ex.printStackTrace();
                    System.out.println("NamingException is: " + ex);
                } catch (IOException ie) {
                    System.err.println("LDAP - IOException ");
                    ie.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        ldapContexto.close();
                        ldapContexto = null;
                        adUserAux.setAtivo(true);
                        adUserAux.setServer(context.getServidor());
                        listAdUserAux.add(adUserAux);
                    } catch (NamingException e) {
                        System.out.println("LDAP - NamingException - não foi possível fechar ldapContexto.");
                        e.printStackTrace();
                    }
                }
            }else{
                adUserAux.setAtivo(false);
                adUserAux.setServer(context.getServidor());
                listAdUserAux.add(adUserAux);
            }
        }

        return listAdUserAux;//olhar
    }

    private static Collection<ServidorContextoDTO> dadosConexao() throws Exception {

        Collection<DirContext> dirContext = new ArrayList<DirContext>();
        Collection<ServidorContextoDTO> listServidor = new ArrayList<ServidorContextoDTO>();
        Hashtable env = new Hashtable();

        LdapContext contexto = null;

        String[] servidor = SERVIDOR.trim().split(";");
        String[] admin_dn = ADMIN_DN.trim().split(";");
        String[] admin_pw = ADMIN_PW.trim().split(";");

        String[] ldap_filtro = null;
        String[] ldap_atributo = null;
        String[] grupo_padrao = null;
        String[] perfil_acesso = null;
        String[] numero_colaboradores = null;
        String[] base_dominio = null;

        if (!LDAP_FILTRO.equals("")) {
            ldap_filtro = LDAP_FILTRO.trim().split(";");
        }
        if (!LDAP_ATRIBUTO.equals("")) {
            ldap_atributo  = LDAP_ATRIBUTO.trim().split(";");
        }
        if (!GRUPOPADRAO.equals("")) {
            grupo_padrao = GRUPOPADRAO .trim().split(";");
        }
        if (!ID_PERFIL_ACESSO_DEFAULT.equals("")) {
            perfil_acesso  = ID_PERFIL_ACESSO_DEFAULT.trim().split(";");
        }
        if (!NUMERO_COLABORADORES_CONSULTA_AD.equals("")) {
            numero_colaboradores   = NUMERO_COLABORADORES_CONSULTA_AD .trim().split(";");
        }
        if (!BASE_DN.equals("")) {
            base_dominio = BASE_DN.trim().split(";");
        }

        for (int i = 0; i < servidor.length; i++) {
            ServidorContextoDTO servidorContextoDTO = new ServidorContextoDTO();

            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CTX);

            env.put(Context.PROVIDER_URL, servidor[i]);

            env.put(Context.SECURITY_PRINCIPAL, admin_dn[i]);

            env.put(Context.SECURITY_CREDENTIALS, admin_pw[i]);

            env.put(Context.SECURITY_AUTHENTICATION, CONNECTION_TYPE);

            try {
                servidorContextoDTO.setServidor(servidor[i]);
                contexto = new InitialLdapContext(env, null);

                if (contexto != null) {
                    servidorContextoDTO.setLdapContext(contexto);

                    if (grupo_padrao != null && grupo_padrao.length != 0) {
                        servidorContextoDTO.setGrupoPadrao(grupo_padrao[i]);
                    }
                    if (ldap_atributo != null && ldap_atributo.length != 0) {
                        servidorContextoDTO.setLdpaAtributo(ldap_atributo[i]);
                    }
                    if (ldap_filtro != null && ldap_filtro.length != 0) {
                        servidorContextoDTO.setLdpaFiltro(ldap_filtro[i]);
                    }
                    if (numero_colaboradores != null && numero_colaboradores.length != 0) {
                        servidorContextoDTO.setNumeroColaboradores(numero_colaboradores[i]);
                    }
                    if (perfil_acesso != null && perfil_acesso.length != 0) {
                        servidorContextoDTO.setPerfilAcesso(perfil_acesso[i]);
                    }
                    if (base_dominio != null && base_dominio.length != 0) {
                        servidorContextoDTO.setBaseDominio(base_dominio[i]);
                    }
                    servidorContextoDTO.setAtivo(true);
                    listServidor.add(servidorContextoDTO);
                }

                //contexto.close();
            } catch (NamingException e) {
                servidorContextoDTO.setAtivo(false);
                listServidor.add(servidorContextoDTO);
                System.out.println(MSG_ERROR_LDAP_CONNECTION);
                e.printStackTrace();
            }
        }
        return listServidor;
    }
}