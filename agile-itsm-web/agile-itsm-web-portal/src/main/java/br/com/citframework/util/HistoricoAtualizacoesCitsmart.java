package br.com.citframework.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ReleaseDTO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
/**
 * Classe utilitaria para leitura do arquivo de release do Citsmart
 * @author flavio.santana
 * @since 23/10/2013
 */
public class HistoricoAtualizacoesCitsmart {

	private static final String DEFAULT_CHARSET = "ISO-8859-1";
	private String charset  = DEFAULT_CHARSET;
	 
	public HistoricoAtualizacoesCitsmart() {
	}
	
	@SuppressWarnings("unchecked")
	public Collection<ReleaseDTO> lerXML(String path) throws IOException {
		
		Reader reader = null;
		Collection<ReleaseDTO> listRelease = null;
		XStream x = null;
		try {
			reader = (Reader) new InputStreamReader(new FileInputStream(path), getCharset());
			 x = new XStream(new DomDriver(getCharset()));
			listRelease = (Collection<ReleaseDTO>) x.fromXML(reader);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(reader!= null){
				reader.close();
			}
		}
		return listRelease;
	}
	
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}	 
}

