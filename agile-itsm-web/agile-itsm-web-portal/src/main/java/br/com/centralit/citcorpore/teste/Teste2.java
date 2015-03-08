package br.com.centralit.citcorpore.teste;

import br.com.centralit.citcorpore.util.LDAPManager;


public class Teste2 {
	public static void main(String[] args) throws Exception {
		LDAPManager ldapManager = LDAPManager.getInstance("10.100.100.2", 389);
		if (ldapManager.isValidUser("emauri", "pereba05")){
			System.out.println("USUARIO VALIDO!");
		}else{
			System.out.println("USUARIO INVALIDO!");
		}
	}
}
