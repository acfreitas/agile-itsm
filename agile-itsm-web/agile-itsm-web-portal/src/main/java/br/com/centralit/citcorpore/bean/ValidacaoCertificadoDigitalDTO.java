package br.com.centralit.citcorpore.bean;

import java.security.cert.X509Certificate;

import br.com.centralit.citcertdigital.bean.InfoCertificadoDigital;
import br.com.citframework.dto.IDto;

public class ValidacaoCertificadoDigitalDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4566460320507373015L;
	private String fileName;
	private String caminhoCompleto;
	private InfoCertificadoDigital infoCertificadoDigital;
	private X509Certificate cert;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public InfoCertificadoDigital getInfoCertificadoDigital() {
		return infoCertificadoDigital;
	}
	public void setInfoCertificadoDigital(
			InfoCertificadoDigital infoCertificadoDigital) {
		this.infoCertificadoDigital = infoCertificadoDigital;
	}
	public String getCaminhoCompleto() {
		return caminhoCompleto;
	}
	public void setCaminhoCompleto(String caminhoCompleto) {
		this.caminhoCompleto = caminhoCompleto;
	}
	public X509Certificate getCert() {
		return cert;
	}
	public void setCert(X509Certificate cert) {
		this.cert = cert;
	}
}
