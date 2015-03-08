package br.com.centralit.citquestionario.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.WebUtil;

public class OpcaoRespostaQuestionarioDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5403015375300471184L;
	private Integer idOpcaoRespostaQuestionario;
	private Integer idQuestaoQuestionario;
	private String titulo;
	private Integer peso;
    private String valor;
    private String geraAlerta;
    private String exibeComplemento;
    private Integer idQuestaoComplemento;
    private String serializeQuestaoComplemento;
    private QuestaoQuestionarioDTO questaoComplementoDto;
    
    private boolean selecionada;
	
    public Integer getIdOpcaoRespostaQuestionario() {
		return idOpcaoRespostaQuestionario;
	}
	public void setIdOpcaoRespostaQuestionario(Integer idOpcaoRespostaQuestionario) {
		this.idOpcaoRespostaQuestionario = idOpcaoRespostaQuestionario;
	}
	public Integer getIdQuestaoQuestionario() {
		return idQuestaoQuestionario;
	}
	public void setIdQuestaoQuestionario(Integer idQuestaoQuestionario) {
		this.idQuestaoQuestionario = idQuestaoQuestionario;
	}
	public String getTitulo() {
		return titulo;
	}
	public String getTituloSemFmt() {
		return UtilHTML.retiraFormatacaoHTML(titulo);
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}  
	public Integer getPeso() {
        	return peso;
	}
	public void setPeso(Integer peso) {
    	this.peso = peso;
	}
	public String getTituloExibicao() {
    	if (peso !=null && peso > 0){
        	return titulo + " [Peso " + peso + "]";
    	}else
        	return titulo;
	}
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public String getGeraAlerta() {
        return geraAlerta;
    }
    public void setGeraAlerta(String geraAlerta) {
        this.geraAlerta = geraAlerta;
    }
    public String getExibeComplemento() {
        return exibeComplemento;
    }
    public void setExibeComplemento(String exibeComplemento) {
        this.exibeComplemento = exibeComplemento;
    }
    public Integer getIdQuestaoComplemento() {
        return idQuestaoComplemento;
    }
    public void setIdQuestaoComplemento(Integer idQuestaoComplemento) {
        this.idQuestaoComplemento = idQuestaoComplemento;
    }
    public String getSerializeQuestaoComplemento() {
        if (serializeQuestaoComplemento == null){
            try {
                serializeQuestaoComplemento = WebUtil.serializeObject(getQuestaoComplementoDto());
            } catch (Exception e) {
                serializeQuestaoComplemento = "";
                e.printStackTrace();
            }
        }
        return serializeQuestaoComplemento;
    }
    public void setSerializeQuestaoComplemento(String serializeQuestaoComplemento) {
        this.serializeQuestaoComplemento = serializeQuestaoComplemento;
    }
    public QuestaoQuestionarioDTO getQuestaoComplementoDto() {
        return questaoComplementoDto;
    }
    public void setQuestaoComplementoDto(
            QuestaoQuestionarioDTO questaoComplementoDto) {
        this.questaoComplementoDto = questaoComplementoDto;
    }
    public boolean isSelecionada() {
        return selecionada;
    }
    public void setSelecionada(boolean selecionada) {
        this.selecionada = selecionada;
    }
	
}
