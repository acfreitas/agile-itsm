package br.com.citframework.util;

import br.com.citframework.dto.Usuario;

public class Run {

	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		
		Usuario usua = new Usuario();
		usua.setNomeUsuario("W");
		usua.setMatricula("1234");
		
		Reflexao.clearAllProperties(usua);
		
		System.out.println(usua.getMatricula()+" "+usua.getNomeUsuario());
		System.out.println("fim");

	}

}
