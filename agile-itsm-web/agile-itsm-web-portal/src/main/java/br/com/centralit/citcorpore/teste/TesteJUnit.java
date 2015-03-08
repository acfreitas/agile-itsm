package br.com.centralit.citcorpore.teste;


import junit.framework.TestCase;

import org.junit.Test;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class TesteJUnit extends TestCase {

	@Test
	public void testMain() {
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
				assertNotNull(usuarioDTO.getIdUsuario());
			} catch (ServiceException e) {
				e.printStackTrace();
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
