package br.com.centralit.testeselenium.helper;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import br.com.citframework.util.Mensagens;

public abstract class UtilTeste
{

	/**
	 * Gera numero aleatorio
	 * @param tamanho
	 * @return
	 */
	public static String geraNumero(int tamanho) {
		String str = "";
		String chave = "0123456789";
		Random rand = new Random();
		
		if(tamanho <= 0)
			tamanho = 1;
		
		while(str.length() < tamanho) {
			str = str + chave.charAt(rand.nextInt(chave.length()));
		}
		return str;
	}
	
	/**
	 * Gera uma string aleatoria
	 * @param tamanho
	 * @return
	 */
	public static String geraString(int tamanho) {
		String str = "";
		String chave = "qwertyuiopasdfghjklzxcvbnm";
		Random rand = new Random();
		while(str.length() < tamanho) {
			str = str + chave.charAt(rand.nextInt(chave.length()));
		}
		return str;
	}
	
	public static String geraString(String tamanho) {
		String str = "";
		String chave = "qwertyuiopasdfghjklzxcvbnm";
		Random rand = new Random();
		int comprimento= Integer.parseInt(tamanho);
		while(str.length() < comprimento) {
			str = str + chave.charAt(rand.nextInt(chave.length()));
		}
		return str;
	}
	
	public static String geraTextArea() {
		String base = "qwertyuiopasdfghjklçzxcvbnm7894561230avcqwertyuiopasdfghjklçzxcvbnm7894561230avcqwertyuiopasdfghjklçzxcvbnm7894561230avcqwertyuiopasdfghjklçzxcvbnm7894561230avcqwertyuiopasdfghjklçzxcvbnm7894561230avc";
		String str = "";
		for(int i = 0; i < 25; i++) {
			str = str+base;
		}
		return str;
	}

	
	public static String geraCPF() {

		Random aleatorio = new Random();
		String CPF = ""; 		
		String CPFMascara = "";
		int[] numeros = new int[9];
		int[] multiplicacao = new int[9];
		int[] multiplicacao2 = new int[10];
		int soma = 0;
		int primeiroDigito;
		int segundoDigito;
		int resto;
		
		for(int i = 0; i < 9; i++) {
			numeros[i] = aleatorio.nextInt(10);
		}
		
		for(int i = 10, j = 0; i >=2 && j <9; i--, j++) {
			multiplicacao[j] = i;
		}
		
		for(int i = 0; i < 9; i++) {
			multiplicacao[i] = multiplicacao[i] * numeros[i];
		}
		
		for(int x: multiplicacao) {
			soma = soma + x;
		}
		
		resto = soma%11;
		if(resto < 2) {
			primeiroDigito = 0;
		} else {
			primeiroDigito = 11-resto;
		}
		
		soma = 0;
		
		for(int i = 11, j = 0; i >=2 && j <10; i--, j++) {
			multiplicacao2[j] = i;
		}
		
		for(int i = 0; i < 9; i++) {
			multiplicacao2[i] = multiplicacao2[i] * numeros[i];
		}
		multiplicacao2[9] = primeiroDigito * multiplicacao2[9];
		
		for(int x: multiplicacao2) {
			soma = soma + x;
		}
		
		resto = soma%11;
		if(resto < 2) {
			segundoDigito = 0;
		} else {
			segundoDigito = 11-resto;
		}
		
		for(int x: numeros) {
			CPF = CPF+x;
		}
		CPF = CPF+primeiroDigito+segundoDigito;
		
		for(int i = 0; i < 11; i++) {
			if(i ==3 || i == 6) {
				CPFMascara = CPFMascara+".";
			}
			if(i ==9) {
				CPFMascara = CPFMascara+"-";
			}
			CPFMascara = CPFMascara + CPF.charAt(i);
		}
		return CPFMascara;
	}
	
	
	public static String geraCPFSemMascara() {

		Random aleatorio = new Random();
		String CPF = ""; 		
		int[] numeros = new int[9];
		int[] multiplicacao = new int[9];
		int[] multiplicacao2 = new int[10];
		int soma = 0;
		int primeiroDigito;
		int segundoDigito;
		int resto;
		
		for(int i = 0; i < 9; i++) {
			numeros[i] = aleatorio.nextInt(10);
		}
		
		for(int i = 10, j = 0; i >=2 && j <9; i--, j++) {
			multiplicacao[j] = i;
		}
		
		for(int i = 0; i < 9; i++) {
			multiplicacao[i] = multiplicacao[i] * numeros[i];
		}
		
		for(int x: multiplicacao) {
			soma = soma + x;
		}
		
		resto = soma%11;
		if(resto < 2) {
			primeiroDigito = 0;
		} else {
			primeiroDigito = 11-resto;
		}
		
		soma = 0;
		
		for(int i = 11, j = 0; i >=2 && j <10; i--, j++) {
			multiplicacao2[j] = i;
		}
		
		for(int i = 0; i < 9; i++) {
			multiplicacao2[i] = multiplicacao2[i] * numeros[i];
		}
		multiplicacao2[9] = primeiroDigito * multiplicacao2[9];
		
		for(int x: multiplicacao2) {
			soma = soma + x;
		}
		
		resto = soma%11;
		if(resto < 2) {
			segundoDigito = 0;
		} else {
			segundoDigito = 11-resto;
		}
		
		for(int x: numeros) {
			CPF = CPF+x;
		}
		return CPF+primeiroDigito+segundoDigito;
	}
	
	
	public static String geraCNPJ() {

		Random aleatorio = new Random();
		String CNPJ = ""; 		
		String CNPJMascara = "";
		int[] numeros = new int[12];
		int[] multiplicacao = {5,4,3,2,9,8,7,6,5,4,3,2};
		int[] multiplicacao2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};
		int soma = 0;
		int primeiroDigito;
		int segundoDigito;
		int resto;
		
		for(int i = 0; i < 12; i++) {
			numeros[i] = aleatorio.nextInt(10);
		}
		
		for(int i = 0; i < 12; i++) {
			multiplicacao[i] = multiplicacao[i] * numeros[i];
		}
		
		for(int x: multiplicacao) {
			soma = soma + x;
		}
		
		resto = soma%11;
		if(resto < 2) {
			primeiroDigito = 0;
		} else {
			primeiroDigito = 11-resto;
		}
		
		soma = 0;
		
		for(int i = 0; i < 12; i++) {
			multiplicacao2[i] = multiplicacao2[i] * numeros[i];
		}
		multiplicacao2[12] = primeiroDigito * multiplicacao2[12];
		
		for(int x: multiplicacao2) {
			soma = soma + x;
		}
		
		resto = soma%11;
		if(resto < 2) {
			segundoDigito = 0;
		} else {
			segundoDigito = 11-resto;
		}
		
		for(int x: numeros) {
			CNPJ = CNPJ+x;
		}
		CNPJ = CNPJ+primeiroDigito+segundoDigito;
		
		for(int i = 0; i < 14; i++) {
			if(i ==2 || i == 6) {
				CNPJMascara = CNPJMascara+".";
			}
			if(i ==9) {
				CNPJMascara = CNPJMascara+"/";
			}
			if(i == 12) {
				CNPJMascara = CNPJMascara+"-";
			}
			CNPJMascara = CNPJMascara + CNPJ.charAt(i);
		}
		return CNPJMascara;
	}
	
	public static String geraCNPJSemMascara() {

		Random aleatorio = new Random();
		String CNPJ = ""; 		
		int[] numeros = new int[12];
		int[] multiplicacao = {5,4,3,2,9,8,7,6,5,4,3,2};
		int[] multiplicacao2 = {6,5,4,3,2,9,8,7,6,5,4,3,2};
		int soma = 0;
		int primeiroDigito;
		int segundoDigito;
		int resto;
		
		for(int i = 0; i < 12; i++) {
			numeros[i] = aleatorio.nextInt(10);
		}
		
		for(int i = 0; i < 12; i++) {
			multiplicacao[i] = multiplicacao[i] * numeros[i];
		}
		
		for(int x: multiplicacao) {
			soma = soma + x;
		}
		
		resto = soma%11;
		if(resto < 2) {
			primeiroDigito = 0;
		} else {
			primeiroDigito = 11-resto;
		}
		
		soma = 0;
		
		for(int i = 0; i < 12; i++) {
			multiplicacao2[i] = multiplicacao2[i] * numeros[i];
		}
		multiplicacao2[12] = primeiroDigito * multiplicacao2[12];
		
		for(int x: multiplicacao2) {
			soma = soma + x;
		}
		
		resto = soma%11;
		if(resto < 2) {
			segundoDigito = 0;
		} else {
			segundoDigito = 11-resto;
		}
		
		for(int x: numeros) {
			CNPJ = CNPJ+x;
		}
		CNPJ = CNPJ+primeiroDigito+segundoDigito;
		return CNPJ;
	}
	
	  //===============================================================================================
	  // Calcula o tempo em hora, minuto, segundo
	  //===============================================================================================
	  /**
	   * Calcula o tempo em hora, minuto, segundo
	   * @param seconds
	   * @return
	   */
	  public static String calculateTime(long seconds) 
	  { 
		  int day = (int)TimeUnit.SECONDS.toDays(seconds);        
		  long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
		  long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
		  long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
		  
	      return day + " - " + hours + ":" + minute + ":" + second ;
	  }
	
	  
	  
	  
	  
	  public static String tempoAgoraMaisUmtempo(long somatempo) 
	  {
		  DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		  Calendar cal = Calendar.getInstance(); 
		  long sumDate = cal.getTime().getTime() + somatempo ; 		
		  return dateFormat.format(sumDate);
	  }

	  
	  /**
	   * Retorna uma String com valor = "teste" caso a string passado como parametro seja nula ou vazia
	   * 
	   * @param descricao
	   * @return 
	   */
	  public static String retornaStringValida(String descricao){
		  
		  if(descricao == null || descricao.length() <= 0 || descricao.trim().length() <= 0)
			  return "teste";
		  else
			  return descricao;
		  
	  }
	  
	  /**
		 * Realiza a leitura de um arquivo properties
		 * 
		 * @param String
		 * 					-	Nome do arquivo properties
		 * @return Properties - Propriedades
		 * 
		 */
		public static Properties obterProperties(String nomeDoArquivo) throws IOException {

			Properties props = new Properties();

			InputStream inputStreamSettedInLoad = null;
			
			String fileName = nomeDoArquivo;

			try {

				ClassLoader load = Mensagens.class.getClassLoader();
				InputStream is = load.getResourceAsStream(fileName);

				if (is == null) {
					is = ClassLoader.getSystemResourceAsStream(fileName);
				}
				if (is == null) {
					is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
				}
				if (is == null) {
					is = inputStreamSettedInLoad;
				}

				try {
					if (is != null) {
						props.load(is);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (SecurityException e1) {
				e1.printStackTrace();
			}

			return props;
		}
}