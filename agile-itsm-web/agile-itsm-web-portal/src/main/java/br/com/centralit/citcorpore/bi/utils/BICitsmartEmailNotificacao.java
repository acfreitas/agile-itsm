package br.com.centralit.citcorpore.bi.utils;

import java.util.Map;

import br.com.centralit.citcorpore.mail.MensagemEmail;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;

/**
 * @author euler.ramos
 *
 */
public class BICitsmartEmailNotificacao {
	String emailGeral;
	String emailConexao;
	String emailFrom;
	Integer idModeloEmail;
	Map<String, String> map;
	boolean notificar;

	public BICitsmartEmailNotificacao(){
		this.emailGeral = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.BICITSMART_EMAIL_NOTIFICACAO_GERAL, null);
		this.emailFrom = "citsmart@centralit.com.br";
		this.notificar = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.BICITSMART_NOTIFICAR_ERRO_IMPORTACAO_POR_EMAIL, "N").equalsIgnoreCase("S");
	}
	
	private void enviaEmailGeral(){
		try {
			MensagemEmail mensagemEmail = new MensagemEmail(this.idModeloEmail, this.map);
			if ((this.emailGeral!=null)&&(this.emailGeral.length()>0)){
				mensagemEmail.envia(this.emailGeral, null, this.emailFrom);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CITSMART - Problema no envio de notificação de erro da Importação Automática BICitsmart; e-mail geral: " + this.emailGeral);
		}
	}
	
	private void enviaEmailConexao(){
		try {
			MensagemEmail mensagemEmail = new MensagemEmail(this.idModeloEmail, this.map);
			if ((this.emailConexao!=null)&&(this.emailConexao.length()>0)){
				mensagemEmail.envia(this.emailConexao, null, this.emailFrom);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CITSMART - Problema no envio de notificação de erro da Importação Automática BICitsmart; e-mail conexão: " + this.emailConexao);
		}
	}
	
	public void envia(){
		//Se o parâmetro permite a notificação
		if (this.notificar){
			if ((this.idModeloEmail!=null)&&(this.idModeloEmail>0)){
				try {
					this.enviaEmailGeral();
					this.enviaEmailConexao();
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("CITSMART - Problema no envio de notificação de erro da Importação Automática BICitsmart;");
				}
			}
		}
	}

	public void setEmailConexao(String emailConexao) {
		this.emailConexao = emailConexao;
	}

	public void setModeloEmail(String modelo) {
		switch (modelo) {
		case "Exceção":
			this.idModeloEmail = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.BICITSMART_ID_MODELO_EMAIL_ERRO_AGEND_EXCECAO, "0").trim());
			break;
		case "Específico":
			this.idModeloEmail = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.BICITSMART_ID_MODELO_EMAIL_ERRO_AGEND_ESPECIFICO, "0").trim());
			break;
		case "Padrão":
			this.idModeloEmail = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.BICITSMART_ID_MODELO_EMAIL_ERRO_AGEND_PADRAO, "0").trim());
			break;
		case "Parâmetro":
			this.idModeloEmail = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.BICITSMART_ID_MODELO_EMAIL_ERRO_PARAMETRO, "0").trim());
			break;
		case "Problema":
			this.idModeloEmail = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.BICITSMART_ID_MODELO_EMAIL_ERRO_EXCECUCAO, "0").trim());
			break;			
		default:
			this.idModeloEmail = 0;
		}
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	
}