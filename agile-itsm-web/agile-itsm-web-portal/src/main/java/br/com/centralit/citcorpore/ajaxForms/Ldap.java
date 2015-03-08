/**
 * 
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.ADUserDTO;
import br.com.centralit.citcorpore.bean.LdapDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.integracao.ad.LDAPUtils;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilI18N;

/**
 * @author Valdoílo
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class Ldap extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.executeScript("deleteAllRowsTabelaAtributosLdap()");

		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_URL.id() + ",'" + Enumerados.ParametroSistema.LDAP_URL.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_URL, ""), "'", "\'") + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.DOMINIO_AD.id() + ",'" + Enumerados.ParametroSistema.DOMINIO_AD.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DOMINIO_AD, ""), "'", "\'") + " ')");	
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_SUBDOMINIO.id() + ",'" + Enumerados.ParametroSistema.LDAP_SUBDOMINIO.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_SUBDOMINIO, ""), "'", "\'") + " ')");	
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO.id() + ",'" + Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO, ""), "'", "\'") + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LOGIN_AD.id() + ",'" + Enumerados.ParametroSistema.LOGIN_AD.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LOGIN_AD, ""), "'", "\'") + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.SENHA_AD.id() + ",'" + Enumerados.ParametroSistema.SENHA_AD.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.SENHA_AD, ""), "'", "\'") + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_FILTRO.id() + ",'" + Enumerados.ParametroSistema.LDAP_FILTRO.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_FILTRO, ""), "'", "\'") + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_ATRIBUTO.id() + ",'" + Enumerados.ParametroSistema.LDAP_ATRIBUTO.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_ATRIBUTO, ""), "'", "\'") + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_SN_LAST_NAME.id() + ",'" + Enumerados.ParametroSistema.LDAP_SN_LAST_NAME.getCampoParametroInternacionalizado(request) + "','"
				+ Internacionalizar.internacionalizaOptionSN(request, "restore", StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_SN_LAST_NAME, ""), "'", "\'")) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT.id() + ",'" + Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT, ""), "'", "\'") + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP.id() + ",'" + Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP, ""), "'", "\'") + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD.id() + ",'" + Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD.getCampoParametroInternacionalizado(request) + "','"
				+ StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD, ""), "'", "\'") + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_MOSTRA_BOTAO.id() + ",'" + StringUtils.replace(Enumerados.ParametroSistema.LDAP_MOSTRA_BOTAO.getCampoParametroInternacionalizado(request), "'", "\\'") + "','"
				+ Internacionalizar.internacionalizaOptionSN(request, "restore", StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_MOSTRA_BOTAO, "N"), "\'", "\\'")) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_OPEN_LDAP.id() + ",'" + StringUtils.replace(Enumerados.ParametroSistema.LDAP_OPEN_LDAP.getCampoParametroInternacionalizado(request), "'", "\\'") + "','"
				+ Internacionalizar.internacionalizaOptionSN(request, "restore", StringUtils.replace(ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LDAP_OPEN_LDAP, "N"), "\'", "\\'")) + " ')");
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		LdapDTO ldapDto = (LdapDTO) document.getBean();

		ldapDto.setListLdapDTO((Collection<LdapDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(LdapDTO.class, "listAtributoLdapSerializado", request));

		ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

		if (ldapDto.getListLdapDTO() != null && !ldapDto.getListLdapDTO().isEmpty()) {
			String[] totalParametros= null;
			for (LdapDTO parametroLdap : ldapDto.getListLdapDTO()) {
				ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
				
				parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoLdap().trim()));
				
				parametroCorporeDto = (ParametroCorporeDTO) parametroCorporeService.restore(parametroCorporeDto);
				/*
				 * Se o parâmetro for do tipo boolean, faz a conversão para o valor correto 
				 */
				if (parametroCorporeDto.getTipoDado() != null && parametroCorporeDto.getTipoDado().equalsIgnoreCase("Boolean")) {
					parametroCorporeDto.setValor(Internacionalizar.internacionalizaOptionSN(request, "save", parametroLdap.getValorAtributoLdap()));
				} else {
					parametroCorporeDto.setValor(parametroLdap.getValorAtributoLdap().trim().replaceAll(" ", ""));
				}

				parametroCorporeService.atualizarParametros(parametroCorporeDto);

			}

			document.alert(UtilI18N.internacionaliza(request, "ldap.atualizadocomsucesso"));
		}

	}

	public void testarConexao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Collection<ADUserDTO> listaAdUserDto = LDAPUtils.testarConexao();

		if (listaAdUserDto != null && !listaAdUserDto.isEmpty()) {
			
		
		for (ADUserDTO adUserDto : listaAdUserDto) {
			
		
		if (adUserDto != null && adUserDto.isAtivo()) {
			document.alert(UtilI18N.internacionaliza(request, "ldap.conexaocom") + getDisponivel(request, adUserDto.getServer()) +" "+UtilI18N.internacionaliza(request, "ldap.conexaorealizadasucesso")+"\n  \n sAMAccountName: " + getDisponivel(request, adUserDto.getsAMAccountName()) + "\n E-mail: "
					+ getDisponivel(request, adUserDto.getMail()) + "\n CN: " + getDisponivel(request, adUserDto.getCN()) + "\n SN: " + getDisponivel(request, adUserDto.getSN()) + "\n DN: "
					+ getDisponivel(request, adUserDto.getDN()) + "\n Display Name: " + getDisponivel(request, adUserDto.getDisplayName()));
		} else {
			document.alert(UtilI18N.internacionaliza(request, "ldap.conexaocom") + " "+getDisponivel(request, adUserDto.getServer()) + " " +UtilI18N.internacionaliza(request, "ldap.conexaofalhou"));
		}
		}
		}else{
			document.alert(UtilI18N.internacionaliza(request, "ldap.todasconexoesfalharam"));
		}
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	/**
	 * Testa os parametros de conexão e Sincroniza com o LDAP
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void sincronizaLDAP(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Collection<ADUserDTO> listaAdUserDto = LDAPUtils.testarConexao();
		document.executeScript("JANELA_AGUARDE_MENU.show();");
		if (listaAdUserDto != null && !listaAdUserDto.isEmpty()) {	
			LDAPUtils.sincronizaUsuariosAD();
			document.alert(UtilI18N.internacionaliza(request, "ldap.sincronizacaoConcluida") +"\n");
		} else {
			document.alert(UtilI18N.internacionaliza(request, "ldap.conexaofalhou"));
		}
		document.executeScript("JANELA_AGUARDE_MENU.hide();");
	}
	
	private String getDisponivel(HttpServletRequest request, String atributoLdap) {

		if (atributoLdap != null) {
			if (StringUtils.isBlank(atributoLdap)) {
				return UtilI18N.internacionaliza(request, "ldap.naodiponivel");
			}

			return atributoLdap;
		}

		return UtilI18N.internacionaliza(request, "ldap.naodiponivel");
	}

	@Override
	public Class getBeanClass() {
		return LdapDTO.class;
	}

}
