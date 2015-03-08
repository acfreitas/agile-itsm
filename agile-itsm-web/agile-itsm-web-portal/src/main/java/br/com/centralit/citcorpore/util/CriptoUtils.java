/*
 * Created on 01/10/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @author CentralIT
 */
public final class CriptoUtils {
	private static final String hexDigits = "0123456789abcdef";
	/**
	 * Gera o codigo hash de uma string
	 * @param str
	 * @param algoritmo
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String generateHash(String str, String algoritmo) throws NoSuchAlgorithmException{
		byte[] b = CriptoUtils.digest(str.getBytes(), algoritmo);
		String hash = CriptoUtils.byteArrayToHexString(b);	
		return hash;
	}
	public static String generateHash(byte[] bt, String algoritmo) throws NoSuchAlgorithmException{
		byte[] b = CriptoUtils.digest(bt, algoritmo);
		String hash = CriptoUtils.byteArrayToHexString(b);	
		return hash;
	}
	/**
	* Realiza um digest em um array de bytes através do algoritmo especificado
	* @param input - O array de bytes a ser criptografado
	* @param algoritmo - O algoritmo a ser utilizado
	* @return byte[] - O resultado da criptografia
	* @throws NoSuchAlgorithmException - Caso o algoritmo fornecido não seja
	* válido
	*/
	public static byte[] digest(byte[] input, String algoritmo)
		throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algoritmo);
		md.reset();
		return md.digest(input);
	}

	/**
	 * Converte o array de bytes em uma representação hexadecimal.
	 * @param input - O array de bytes a ser convertido.
	 * @return Uma String com a representação hexa do array
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder buf = new StringBuilder();

		for (int i = 0; i < b.length; i++) {
			int j = ((int) b[i]) & 0xFF; 
			buf.append(hexDigits.charAt(j / 16)); 
			buf.append(hexDigits.charAt(j % 16)); 
		}
     
		return buf.toString();
	}

	/**
	 * Converte uma String hexa no array de bytes correspondente.
	 * @param hexa - A String hexa
	 * @return O vetor de bytes
	 * @throws IllegalArgumentException - Caso a String não sej auma
	 * representação haxadecimal válida
	 */
	public static byte[] hexStringToByteArray(String hexa)
		throws IllegalArgumentException {

		//verifica se a String possui uma quantidade par de elementos
		if (hexa.length() % 2 != 0) {
			throw new IllegalArgumentException("String hexa inválida"); 
		}

		byte[] b = new byte[hexa.length() / 2];

		for (int i = 0; i < hexa.length(); i+=2) {
			b[i / 2] = (byte) ((hexDigits.indexOf(hexa.charAt(i)) << 4) |
				(hexDigits.indexOf(hexa.charAt(i + 1)))); 
		}
		return b;
	}
	
	public static void generateKeyPair(int keySize, String fileChavePublica, String fileChavePrivada) throws NoSuchAlgorithmException, FileNotFoundException, IOException{
	    KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
	    SecureRandom random = new SecureRandom();
	    pairgen.initialize(keySize, random);
	    KeyPair keyPair = pairgen.generateKeyPair();
	    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileChavePublica));
	    out.writeObject(keyPair.getPublic());
	    out.close();            
	    out = new ObjectOutputStream(new FileOutputStream(fileChavePrivada));
	    out.writeObject(keyPair.getPrivate());
	    out.close();  	
	}
	
	public static void encryptFile(String fileACriptografar, String fileCriptografado, String fileChavePublica) throws FileNotFoundException, IOException, ClassNotFoundException, GeneralSecurityException{
        // wrap with RSA public key
		FileInputStream keyIn = new FileInputStream(fileChavePublica);
        encryptFile(fileACriptografar, fileCriptografado, keyIn);
        keyIn.close();            

	}
	public static void encryptFile(String fileACriptografar, String fileCriptografado, InputStream publicKeyIn) throws FileNotFoundException, IOException, ClassNotFoundException, GeneralSecurityException{
		KeyGenerator keygen = KeyGenerator.getInstance("AES");
		SecureRandom random = new SecureRandom();
		keygen.init(random);
		SecretKey key = keygen.generateKey();
		
		// wrap with RSA public key
		ObjectInputStream keyIn = new ObjectInputStream(publicKeyIn);
		Key publicKey = (Key) keyIn.readObject();
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.WRAP_MODE, publicKey);
		byte[] wrappedKey = cipher.wrap(key);
		DataOutputStream out = new DataOutputStream(new FileOutputStream(fileCriptografado));
		out.writeInt(wrappedKey.length);
		out.write(wrappedKey);
		
		InputStream in = new FileInputStream(fileACriptografar);
		cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		crypt(in, out, cipher);
		in.close();
		out.close();		
	}
	
	public static void decryptFile(String fileCriptografado, String fileDecriptografado, String fileChavePrivada) throws FileNotFoundException, IOException, ClassNotFoundException, GeneralSecurityException{
		if (arquivoExiste(fileCriptografado)){
			// wrap with RSA public key
			FileInputStream keyIn = new FileInputStream(fileChavePrivada);
	        decryptFile(fileCriptografado, fileDecriptografado, keyIn);
	        keyIn.close();  		
		}
	}
	
	public static void decryptFile(String fileCriptografado, String fileDecriptografado, InputStream privateKeyIn) throws FileNotFoundException, IOException, ClassNotFoundException, GeneralSecurityException{
		if (arquivoExiste(fileCriptografado)){
			DataInputStream in = new DataInputStream(new FileInputStream(fileCriptografado));
			int length = in.readInt();
			byte[] wrappedKey = new byte[length];
			in.read(wrappedKey, 0, length);
			
			// unwrap with RSA private key
			ObjectInputStream keyIn = new ObjectInputStream(privateKeyIn);
			Key privateKey = (Key) keyIn.readObject();
			
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.UNWRAP_MODE, privateKey);
			Key key = cipher.unwrap(wrappedKey, "AES", Cipher.SECRET_KEY);
			
			OutputStream out = new FileOutputStream(fileDecriptografado);
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			
			crypt(in, out, cipher);
			in.close();
			out.close();		
		}
	}
	
	  /**
    Uses a cipher to transform the bytes in an input stream
    and sends the transformed bytes to an output stream.
    @param in the input stream
    @param out the output stream
    @param cipher the cipher that transforms the bytes
	  */
	 private static void crypt(InputStream in, OutputStream out,
	    Cipher cipher) throws IOException, GeneralSecurityException{
	    int blockSize = cipher.getBlockSize();
	    int outputSize = cipher.getOutputSize(blockSize);
	    byte[] inBytes = new byte[blockSize];
	    byte[] outBytes = new byte[outputSize];
	
	    int inLength = 0;;
	    boolean more = true;
	    while (more)
	    {
	       inLength = in.read(inBytes);
	       if (inLength == blockSize)
	       {
	          int outLength = cipher.update(inBytes, 0, blockSize, outBytes);
	          out.write(outBytes, 0, outLength);
	       }
	       else more = false;         
	    }
	    if (inLength > 0)
	       outBytes = cipher.doFinal(inBytes, 0, inLength);
	    else
	       outBytes = cipher.doFinal();
	    out.write(outBytes);
	 }
	
	public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, GeneralSecurityException{
		/*
		byte[] b = CriptoUtils.digest("DEMO".getBytes(), "SHA-1");
		String senhaCriptografada = CriptoUtils.byteArrayToHexString(b);
		
		System.out.println(" A SENHA CRIPTOGRAFADA EH: " + senhaCriptografada);
		*/
		
		/*
		try {
			CriptoUtils.generateKeyPair(512, "C:\\temp\\chavePublica.key", "C:\\temp\\chavePrivada.key");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		CriptoUtils.encryptFile("C:\\BancoBrasil\\PICT0016.AVI", "C:\\BancoBrasil\\PICT0016_CRIPTO.AVI", "C:\\temp\\chavePublica.key");
		CriptoUtils.decryptFile("C:\\BancoBrasil\\PICT0016_CRIPTO.AVI", "C:\\BancoBrasil\\PICT0016_DECRIPT.AVI", "C:\\temp\\chavePrivada.key");
	}
	
	public static boolean arquivoExiste(String arquivo){
		File file = new File(arquivo);
		if (file.exists()){
			return true;
		} else {
	    	return false;
	    }
	}
}