package br.com.centralit.citquestionario.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class RespostaItemQuestionarioDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1416429503059971030L;
	private Integer idRespostaItemQuestionario;
	private Integer idIdentificadorResposta;
	private Integer idQuestaoQuestionario;
	private Integer sequencialResposta;
	private String respostaTextual;
	private Double respostaPercentual;
	private Double respostaValor;	
	private Double respostaValor2;
	private Double respostaNumero;
	private Double respostaNumero2;
	private Date respostaData;
	private String respostaHora;
	private Integer respostaDia;
	private Integer respostaMes;
	private Integer respostaAno;
	private String respostaIdListagem;
	
	private String tipoQuestao;
	private String sexo;
	
	private Collection colOpcoesResposta;
	private Collection colRelacaoAnexos;

	private Double total;
	
	private Date dataQuestionario;
	
	private String informacoesRelatorio[];
	
	public Integer getIdIdentificadorResposta() {
		return idIdentificadorResposta;
	}
	public void setIdIdentificadorResposta(Integer idIdentificadorResposta) {
		this.idIdentificadorResposta = idIdentificadorResposta;
	}
	public Integer getIdQuestaoQuestionario() {
		return idQuestaoQuestionario;
	}
	public void setIdQuestaoQuestionario(Integer idQuestaoQuestionario) {
		this.idQuestaoQuestionario = idQuestaoQuestionario;
	}
	public Integer getIdRespostaItemQuestionario() {
		return idRespostaItemQuestionario;
	}
	public void setIdRespostaItemQuestionario(Integer idRespostaItemQuestionario) {
		this.idRespostaItemQuestionario = idRespostaItemQuestionario;
	}
	public Integer getSequencialResposta() {
        return sequencialResposta;
    }
    public void setSequencialResposta(Integer sequencialResposta) {
        this.sequencialResposta = sequencialResposta;
    }
    public Double getRespostaNumero() {
		return respostaNumero;
	}
	public void setRespostaNumero(Double respostaNumero) {
		this.respostaNumero = respostaNumero;
	}
	public Double getRespostaNumero2() {
		return respostaNumero2;
	}
	public void setRespostaNumero2(Double respostaNumero2) {
		this.respostaNumero2 = respostaNumero2;
	}
	public Double getRespostaPercentual() {
		return respostaPercentual;
	}
	public void setRespostaPercentual(Double respostaPercentual) {
		this.respostaPercentual = respostaPercentual;
	}
	public String getRespostaTextual() {
		return respostaTextual;
	}
	public void setRespostaTextual(String respostaTextual) {
		this.respostaTextual = respostaTextual;
	}
	public Double getRespostaValorNumericoPreenchido() {
		if (this.getRespostaValor() != null && this.getRespostaValor().doubleValue() > 0){
			return this.getRespostaValor();
		}
		if (this.getRespostaNumero() != null && this.getRespostaNumero().doubleValue() > 0){
			return this.getRespostaNumero();
		}
		if (this.getRespostaPercentual() != null && this.getRespostaPercentual().doubleValue() > 0){
			return this.getRespostaPercentual();
		}
		return this.getRespostaValor();
	}
	public Double getRespostaValor() {
		return respostaValor;
	}
	public void setRespostaValor(Double respostaValor) {
		this.respostaValor = respostaValor;
	}
	public Double getRespostaValor2() {
		return respostaValor2;
	}
	public void setRespostaValor2(Double respostaValor2) {
		this.respostaValor2 = respostaValor2;
	}
	public Date getRespostaData() {
		return respostaData;
	}
	public void setRespostaData(Date respostaData) {
		this.respostaData = respostaData;
	}
	public String getRespostaHora() {
		return respostaHora;
	}
	public void setRespostaHora(String respostaHora) {
		this.respostaHora = respostaHora;
	}
	public Collection getColOpcoesResposta() {
		return colOpcoesResposta;
	}
	public void setColOpcoesResposta(Collection colOpcoesResposta) {
		this.colOpcoesResposta = colOpcoesResposta;
	}
	public Integer getRespostaAno() {
		return respostaAno;
	}
	public void setRespostaAno(Integer respostaAno) {
		this.respostaAno = respostaAno;
	}
	public Integer getRespostaMes() {
		return respostaMes;
	}
	public void setRespostaMes(Integer respostaMes) {
		this.respostaMes = respostaMes;
	}
    public String getRespostaIdListagem() {
        return respostaIdListagem;
    }
    public void setRespostaIdListagem(String respostaIdListagem) {
        this.respostaIdListagem = respostaIdListagem;
    }
	public Collection getColRelacaoAnexos() {
		return colRelacaoAnexos;
	}
	public void setColRelacaoAnexos(Collection colRelacaoAnexos) {
		this.colRelacaoAnexos = colRelacaoAnexos;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
    public Date getDataQuestionario() {
        return dataQuestionario;
    }
    public void setDataQuestionario(Date dataQuestionario) {
        this.dataQuestionario = dataQuestionario;
    }
	public String getTipoQuestao() {
		return tipoQuestao;
	}
	public void setTipoQuestao(String tipoQuestao) {
		this.tipoQuestao = tipoQuestao;
	}
	public boolean existeResposta() {
	    return  this.getRespostaTextual() != null ||
        	    this.getRespostaPercentual() != null ||
        	    this.getRespostaValor() != null ||   
        	    this.getRespostaValor2() != null ||
        	    this.getRespostaNumero() != null ||
        	    this.getRespostaNumero2() != null ||
        	    this.getRespostaData() != null ||
        	    this.getRespostaHora() != null ||
        	    this.getRespostaMes() != null ||
        	    this.getRespostaAno() != null;
	}
	public Integer getRespostaDia() {
		return respostaDia;
	}
	public void setRespostaDia(Integer respostaDia) {
		this.respostaDia = respostaDia;
	}
	public String[] getInformacoesRelatorio() {
		return informacoesRelatorio;
	}
	public void setInformacoesRelatorio(String[] informacoesRelatorio) {
		this.informacoesRelatorio = informacoesRelatorio;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
}
