package br.com.centralit.citcorpore.bean;

import br.com.centralit.citcorpore.util.Enumerados.ResultadoValidacao;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.Constantes;


public class MensagemRegraNegocioDTO implements IDto {
    public static final String AVISO = "A";
    public static final String ERRO = "E";

    private String tipo;
	private String mensagem;
	
    public MensagemRegraNegocioDTO() {
        this.tipo = "";
        this.mensagem = "";
    }
	   
	public MensagemRegraNegocioDTO(String tipo, String mensagem) {
	    this.tipo = tipo;
	    this.mensagem = mensagem;
	}
	
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
	
    public String getImagem() {
        String imagem = "vazio.gif";
        if (tipo != null && !tipo.trim().equals("")) {
            if (tipo.equalsIgnoreCase(ResultadoValidacao.V.name())) {
                imagem = "validado.png";
            }else if (tipo.equalsIgnoreCase(ResultadoValidacao.E.name()) || tipo.equalsIgnoreCase(ResultadoValidacao.I.name())) {
                imagem = "erro.png";
            }else{
                imagem = "excl.gif";
            }
        }
        return Constantes.getValue("CONTEXTO_APLICACAO")+"/imagens/"+imagem;
    }
    
}
