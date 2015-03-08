package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTest;

public class UsuarioTest {
	public String testCreate() {
		UsuarioService usuarioService;
		try {
			usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setDataInicio(UtilDatas.getDataAtual());
			usuarioDTO.setDataFim(new java.sql.Date(05, 02, 2012));
			usuarioDTO.setLogin("Teste.Teste");
			usuarioDTO.setNomeUsuario("Testando");
			usuarioDTO.setSenha("abc123");
			usuarioDTO.setSenhaNovamente("abc123");
			usuarioDTO.setIdEmpregado(12313);
			usuarioDTO.setIdEmpresa(1);
			usuarioDTO.setSeguencia(12345);
			usuarioDTO.setIdGrupo(2);
			usuarioDTO.setIdPerfilAcessoUsuario(3);
			usuarioDTO.setIdUnidade(4);
			usuarioDTO.setStatus("A");
			usuarioDTO.setLdap("S");
			usuarioDTO.setLocale("Central IT");
			usuarioDTO.setUltimoAcessoPortal(UtilDatas.getDataHoraAtual());
			usuarioService.create(usuarioDTO);
			return new UtilTest().testNotNull(usuarioDTO.getIdUsuario());
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	//Testa Update
	public String testUpdate() {
		UsuarioService usuarioService;
		try {
			usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			UsuarioDTO usuarioDTOAux = new UsuarioDTO();
			usuarioDTO.setIdUsuario(2);
			usuarioDTO.setLogin("Teste");
			usuarioDTO.setNomeUsuario("Teste1");
			usuarioDTO.setSenha("abc123");
			usuarioDTO.setSenhaNovamente("abc123");
			usuarioDTO.setIdEmpregado(12313);
			usuarioDTO.setIdEmpresa(1);
			usuarioDTO.setSeguencia(12345);
			usuarioDTO.setStatus("I");
			usuarioDTO.setLdap("S");
			usuarioService.update(usuarioDTO);
			usuarioDTOAux.setIdUsuario(2);
			usuarioDTOAux = (UsuarioDTO) usuarioService.restore(usuarioDTOAux);
			return new UtilTest().testEquals(usuarioDTO, usuarioDTOAux);
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	// Testa exclusão
	public String testDelete() {
		UsuarioService usuarioService;
		try {
			usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			UsuarioDTO usuarioDTOAux = new UsuarioDTO();
			usuarioDTO.setIdUsuario(2);
			usuarioDTO.setLogin("Teste");
			usuarioDTO.setNomeUsuario("Teste1");
			usuarioDTO.setSenha("abc123");
			usuarioDTO.setSenhaNovamente("abc123");
			usuarioDTO.setIdEmpregado(12313);
			usuarioDTO.setIdEmpresa(1);
			usuarioDTO.setSeguencia(12345);
			usuarioDTO.setStatus("I");
			usuarioDTO.setLdap("S");
			usuarioService.update(usuarioDTO);
			usuarioDTOAux.setIdUsuario(2);
			usuarioDTOAux = (UsuarioDTO) usuarioService.restore(usuarioDTOAux);
			return new UtilTest().testEquals(usuarioDTO.getStatus(), "I");
		} catch (ServiceException e) {
			e.printStackTrace();
			return e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();

		}
	}
}
