package br.com.centralit.citcorpore.util;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 
 *
 */
@SuppressWarnings("unused")
public class UtilImagem {
	
	

	/**
	 * 
	 * Verifica se uma imagem passada atraves de uma url existe
	 * 
	 * @author riubbe.oliveira
	 * @param urlImagem
	 * @return 
	 */
	public static boolean verificaSeImagemExiste(String urlImagem){
		Image image = null;
        try {
            URL url = new URL(urlImagem);
            image = ImageIO.read(url);
            return true;
            
        } catch (IOException e) {
            System.out.println("Imagem "+ urlImagem +" Não encontrada");
            return false;
        }
	}
	
}
