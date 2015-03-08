package br.com.centralit.citsmart.rest.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import br.com.centralit.citsmart.rest.schema.BICitsmart;
import br.com.centralit.citsmart.rest.schema.CtLoginResp;

import com.google.gson.Gson;

public class RestBICitsmartExecute {
	
	/**
	 * Autentica o usuário utilizando JSON
	 * 
	 * @param url
	 * @param usuario
	 * @param senha
	 * @return String
	 * @throws Exception
	 */
	public static String autenticacaoComJSON(String url, String usuario, String senha) throws Exception {
		String input = "{\"userName\":\"" + usuario + "\",\"password\":\"" + senha + "\"}";
   
		ClientRequest request = new ClientRequest(url + "services/login");

		request.accept(MediaType.APPLICATION_JSON);
		request.body(MediaType.APPLICATION_JSON, input);

		ClientResponse<String> response = request.post(String.class);
	 
		if (response.getStatus() != 200) {
			throw new RuntimeException("Chamada falhou: HTTP error code : "	+ response.getStatus() + response.getEntity());
		}
 
		BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));
	 
		String output;
		System.out.println("#### CHAMADA COM JSON - > \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}
 
		CtLoginResp resp = new Gson().fromJson(response.getEntity(), CtLoginResp.class);
		return resp.getSessionID();
	}
	
	/**
	 * Faz o post e utiliza o webservice para recuperar as tabelas do BI do Citsmart.
	 * 
	 * @param url
	 * @param usuario
	 * @param senha
	 * @param tipo
	 * @return String
	 * @throws Exception
	 */
	public static String recuperaTabelas(String url, String usuario, String senha, String tipo) throws Exception {
		BICitsmart biCitsmart = new BICitsmart();
		
		String autenticacao = autenticacaoComJSON(url, usuario, senha);
		
		// -- Atribui a sessão
		biCitsmart.setSessionID(autenticacao);
	
		ClientRequest request = new ClientRequest(url + "services/bicitsmart/recuperarTabelas");

		request.body(MediaType.APPLICATION_XML, biCitsmart);
		request.accept(MediaType.APPLICATION_XML);

		ClientResponse<String> response = request.post(String.class);
 
		if (response.getStatus() != 200) {
			throw new RuntimeException("Chamada falhou: HTTP error code : " + response.getStatus() + response.getEntity());
		}
		
		//BufferedReader br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(response.getEntity().getBytes())));
		String xmlString = response.getEntity();
		
		//JAXB.marshal(xmlFile, "D:\\temp\\resp.xml");
		
		File file = new File("D:\\temp\\resp.xml");
		Writer out = new OutputStreamWriter(new FileOutputStream(file), "ISO-8859-1");

		out.write(xmlString);
		out.close();
		
		//InputStream ioos = new ByteArrayInputStream(xmlFile.getBytes());
		//CtLoginResp resp = JAXB.unmarshal(ioos, CtLoginResp.class);
        
		return xmlString;
	}
	
	public static void main(String[] args) throws Exception {
		String retorno = recuperaTabelas("http://localhost/citsmart/", "admin", "admgoiania516", "auto");
		
	}

}
