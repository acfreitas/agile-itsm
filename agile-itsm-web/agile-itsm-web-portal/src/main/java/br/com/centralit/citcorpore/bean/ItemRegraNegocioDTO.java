package br.com.centralit.citcorpore.bean;

import java.util.Collection;
import java.util.Iterator;

import br.com.citframework.dto.IDto;

public class ItemRegraNegocioDTO implements IDto {
    
    private String resultadoValidacao;
    private String mensagensValidacao;
    private boolean ignoraErroImpeditivo;
    
    private String imagem;
    private String mensagensFmtHTML;
    
    public String getResultadoValidacao() {
        return resultadoValidacao;
    }
    public void setResultadoValidacao(String resultadoValidacao) {
        this.resultadoValidacao = resultadoValidacao;
    }
    public String getMensagensValidacao() {
        return mensagensValidacao;
    }

    public void setMensagensValidacao(String mensagensValidacao) {
        this.mensagensValidacao = mensagensValidacao;
    } 
    
    public String getMensagensFmtHTML() {
        if (mensagensValidacao == null || mensagensValidacao.trim().equals("")) return "";
        
        Collection colMensagens = null;
        try {
            colMensagens = getColMensagensValidacao();
        } catch (Exception e) {
        }
        if (colMensagens == null || colMensagens.size() == 0) return "";

        mensagensFmtHTML = "";
        for(Iterator it = colMensagens.iterator(); it.hasNext();){
            MensagemRegraNegocioDTO mensagemDto = (MensagemRegraNegocioDTO)it.next();
            mensagensFmtHTML += "<img src='" + mensagemDto.getImagem() + "'>" + mensagemDto.getMensagem() + "<br>";
        }
        return mensagensFmtHTML;
    }
    
    public String getImagem() {
        MensagemRegraNegocioDTO mensagemDto = new MensagemRegraNegocioDTO();
        mensagemDto.setTipo(resultadoValidacao);
        imagem = mensagemDto.getImagem();
        return imagem;
    }
    
    public Collection getColMensagensValidacao() throws Exception {
        if (this.getMensagensValidacao() == null) return null;
        return br.com.citframework.util.WebUtil.deserializeCollectionFromString(MensagemRegraNegocioDTO.class, this.getMensagensValidacao());
    }
    
    public void setColMensagensValidacao(Collection colMensagens) throws Exception {
       if (colMensagens == null) {
           setMensagensValidacao(null);
       }else{
           setMensagensValidacao(br.com.citframework.util.WebUtil.serializeObjects(colMensagens));  
       }
       
    }
    public boolean isIgnoraErroImpeditivo() {
        return ignoraErroImpeditivo;
    }
    public boolean getIgnoraErroImpeditivo() {
        return ignoraErroImpeditivo;
    }
    public void setIgnoraErroImpeditivo(boolean ignoraErroImpeditivo) {
        this.ignoraErroImpeditivo = ignoraErroImpeditivo;
    }
    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
    public void setMensagensFmtHTML(String mensagensFmtHTML) {
        this.mensagensFmtHTML = mensagensFmtHTML;
    }
}