package br.com.centralit.citcorpore.rh.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.LoginDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ad.LDAPUtils;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.negocio.CandidatoService;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.integracao.PersistenceEngine;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class LoginCandidato extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String logout = request.getParameter("logout");
		if(logout!=null && logout.equals("yes")){
			request.getSession().setAttribute("CANDIDATO", null);
			request.getSession().setAttribute("colUploadsGED", null);
		}
		
		if(request.getParameter("metodoAutenticacao") != null)
			request.setAttribute("metodoAutenticacao", request.getParameter("metodoAutenticacao"));
		
	}
	
	/**
	 * A demanda original era para atender candidatos internos e externos, sendo que <code>autenticarCandidato()</code> 
	 * tinha o propósito original de autenticar o candidato externo através de um método específico e o colaborador, 
	 * que é considerado uma candidato interno, pelo método padrão de autencicação que estiver definido no Citsmart, 
	 * que pode ou não utilizar o Active Directory (AD).
	 * 
	 * @param document 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void autenticarCandidato(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		CandidatoDTO candidatoDTO = (CandidatoDTO)document.getBean();
		CandidatoService candidatoService = (CandidatoService) ServiceLocator.getInstance().getService(CandidatoService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		
		String metodoAutenticacao = candidatoDTO.getMetodoAutenticacao();
		
		String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
		if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {
			algoritmo = "SHA-1";
		}
		
		if (metodoAutenticacao != null && metodoAutenticacao.equalsIgnoreCase("AD")) {
			LoginDTO loginDTO = new LoginDTO();
			String user = (request.getParameter("user")).trim();
			String senha = candidatoDTO.getSenha();
			
			loginDTO.setLogin(user);
			loginDTO.setUser(user);
			loginDTO.setSenha(senha);
			
			UsuarioDTO usuarioDTO = null;
			String metodoAutenticacaoParametro = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.METODO_AUTENTICACAO_Pasta, "1");
			
			// Verifica se esta configurado o LDAP no sistema
			if(metodoAutenticacaoParametro != null && metodoAutenticacaoParametro.trim().equalsIgnoreCase("2")) {
				usuarioService.sincronizaUsuarioAD(LDAPUtils.autenticacaoAD(loginDTO.getUser(), loginDTO.getSenha()), loginDTO, false);
			}
			
			loginDTO.setSenha(CriptoUtils.generateHash(senha, algoritmo));
			
			usuarioDTO = usuarioService.restoreByLogin(loginDTO.getUser(), loginDTO.getSenha());
			
			if(usuarioDTO == null) {
				document.executeScript("fechar_aguarde();");
				document.alert(UtilI18N.internacionaliza(request, "login.nao_confere"));
				return ;
			}
			
			if (metodoAutenticacaoParametro == null || metodoAutenticacaoParametro.trim().equalsIgnoreCase("")) {
				document.executeScript("fechar_aguarde();");
				document.alert(UtilI18N.internacionaliza(request, "login.metodoAutenticaoNaoConfigurado"));
				return;
			}
			
			if (usuarioDTO.getStatus().equalsIgnoreCase("A") && loginDTO.getSenha().equals(usuarioDTO.getSenha())) {
				EmpregadoDTO empregadoDTO = empregadoService.restoreByIdEmpregado(usuarioDTO.getIdEmpregado());
				
				if(empregadoDTO != null) {
					candidatoDTO = candidatoService.restoreByIdEmpregado(empregadoDTO.getIdEmpregado());
					
					// utilizado para log
					PersistenceEngine.setUsuarioSessao(usuarioDTO);
					
					if(candidatoDTO != null) {
						// Se existe o Candidato, Configura a sessão
						candidatoDTO.setMetodoAutenticacao("AD");
						
						request.getSession(true).setAttribute("CANDIDATO",candidatoDTO);
						document.executeScript("window.location = '" +CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+"/pages/templateCurriculoTrabalheConosco/templateCurriculoTrabalheConosco.load';");
					} else {
						// Se não existe, cria um novo candidato a partir dos dados do Empregado
						CandidatoDTO candidatoDTONovo = new CandidatoDTO();
						
						candidatoDTONovo.setNome(empregadoDTO.getNome());
						candidatoDTONovo.setEmail(empregadoDTO.getEmail());
						candidatoDTONovo.setSenha(CriptoUtils.generateHash(senha, algoritmo));
						candidatoDTONovo.setCpf(empregadoDTO.getCpf());
						candidatoDTONovo.setSenha(usuarioDTO.getSenha());
						candidatoDTONovo.setDataInicio(UtilDatas.getDataAtual());
						candidatoDTONovo.setSituacao("C");
						candidatoDTONovo.setTipo("C");
						candidatoDTONovo.setAutenticado("S");
						candidatoDTONovo.setIdEmpregado(empregadoDTO.getIdEmpregado());
						candidatoDTO = (CandidatoDTO)candidatoService.create(candidatoDTONovo);
						
						candidatoDTO.setMetodoAutenticacao("AD");
						
						request.getSession(true).setAttribute("CANDIDATO",candidatoDTO);
						document.executeScript("window.location = '" +CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+"/pages/templateCurriculoTrabalheConosco/templateCurriculoTrabalheConosco.load';");
					}
				} else {
					document.executeScript("fechar_aguarde()");
					document.alert("Ocorreu um erro ao carregar os dados do candidato!\nEntre em contato com o suporte!");
					return ;
				}
			} else {
				document.executeScript("fechar_aguarde();");
				document.alert(UtilI18N.internacionaliza(request, "login.nao_confere"));
				request.getSession().invalidate();
				return;
			}
		} else {
			boolean candidatoAutenticado = false,
					candidatoEmpregado = false;
			
			candidatoDTO.setSenha(CriptoUtils.generateHash(candidatoDTO.getSenha(), algoritmo));
			
			Collection<CandidatoDTO>candidatosCadastrados = candidatoService.findByCpf(candidatoDTO.getCpf());
			if(candidatosCadastrados!= null){
				for(CandidatoDTO  candidatoaux : candidatosCadastrados){
					// Verifica se é um empregado tentando acessar com o CPF
					if(candidatoaux.getIdEmpregado() != null) {
						candidatoEmpregado = true;
					}
					if(candidatoaux.getAutenticado()!= null && candidatoaux.getAutenticado().equalsIgnoreCase("N")){
						document.alert(UtilI18N.internacionaliza(request,"candidato.usarioNaoAutenticado"));
						document.executeScript("fechar_aguarde()");
						return;
					}
					
					if(candidatoaux.getSenha().equals(candidatoDTO.getSenha())){
						candidatoAutenticado = true; 
					}
				}
				
				if(candidatoAutenticado){
					candidatoDTO = candidatoService.restoreByCpf(candidatoDTO.getCpf());
					// Se for um candidato tentando acessar com o CPF, configura o metodo de autenticacao como AD
					if(candidatoEmpregado) {
						candidatoDTO.setMetodoAutenticacao("AD");
					}
					request.getSession(true).setAttribute("CANDIDATO",candidatoDTO);
					document.executeScript("window.location = '" +CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+"/pages/trabalheConosco/trabalheConosco.load'; ");				
				} else{
					document.alert(UtilI18N.internacionaliza(request, "candidato.senhaInvalida"));
				}
				document.executeScript("fechar_aguarde()");
				
			} else {
				document.executeScript("fechar_aguarde()");
				document.alert(UtilI18N.internacionaliza(request, "candidato.nenhumaContaEncontrada"));
			}
		}
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return CandidatoDTO.class;
	}

}
