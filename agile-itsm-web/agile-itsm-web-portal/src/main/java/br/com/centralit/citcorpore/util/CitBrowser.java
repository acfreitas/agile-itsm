package br.com.centralit.citcorpore.util;

import javax.servlet.http.HttpServletRequest;

import nl.bitwalker.useragentutils.UserAgent;
import nl.bitwalker.useragentutils.Version;

public class CitBrowser {
	String nome;
	int versao;
	
	public CitBrowser(HttpServletRequest request) {
		String userAgent = request.getHeader("user-agent");
		UserAgent ua = UserAgent.parseUserAgentString(userAgent);
		Version browserVersion = ua.getBrowserVersion();
		this.nome = ua.getBrowser().toString();
		if(browserVersion != null)
			this.versao = Integer.parseInt(browserVersion.getMajorVersion());	
	}

	public String getNome() {
		return nome;
	}

	public int getVersao() {
		return versao;
	}
	
	public int valido(){
		if ((this.nome.contains("IE"))||(this.nome.contains("Explorer"))) {
			if (this.versao<10){
				return 0;
			}
		}
		/*else {
			if (this.nome.contains("Firefox")) {
				if (this.versao<25){
					return -1;
				}
			} else {
				if (this.nome.contains("Chrome")) {
					if (this.versao<30){
						return -2;
					}
				}
			}
		}*/
		return 1;
	}
	
}