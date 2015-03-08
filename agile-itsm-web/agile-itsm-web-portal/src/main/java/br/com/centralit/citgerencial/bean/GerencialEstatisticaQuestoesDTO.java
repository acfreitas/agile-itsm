package br.com.centralit.citgerencial.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class GerencialEstatisticaQuestoesDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6119180764982054792L;
	private String descricao;
	private Integer valor;
	
	private String nome;
	private Date dataQuestionario;
	private String respostaTextual;
	private Double respostaPercentual;
	private Double respostaValor;	
	private Double respostaValor2;
	private Double respostaNumero;
	private Double respostaNumero2;
	private Date respostaData;
	private String respostaHora;
	private Integer respostaPressaoSistolica;
	private Integer respostaPressaoDiastolica;
	private Integer respostaMes;
	private Integer respostaAno;
	private Double respostaPeso;
	private Double respostaAltura;
	private Double respostaIMC;
	
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Integer getValor() {
		return valor;
	}
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	public Double getRespostaAltura() {
		return respostaAltura;
	}
	public void setRespostaAltura(Double respostaAltura) {
		this.respostaAltura = respostaAltura;
	}
	public Integer getRespostaAno() {
		return respostaAno;
	}
	public void setRespostaAno(Integer respostaAno) {
		this.respostaAno = respostaAno;
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
	public Double getRespostaIMC() {
		return respostaIMC;
	}
	public void setRespostaIMC(Double respostaIMC) {
		this.respostaIMC = respostaIMC;
	}
	public Integer getRespostaMes() {
		return respostaMes;
	}
	public void setRespostaMes(Integer respostaMes) {
		this.respostaMes = respostaMes;
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
	public Double getRespostaPeso() {
		return respostaPeso;
	}
	public void setRespostaPeso(Double respostaPeso) {
		this.respostaPeso = respostaPeso;
	}
	public Integer getRespostaPressaoDiastolica() {
		return respostaPressaoDiastolica;
	}
	public void setRespostaPressaoDiastolica(Integer respostaPressaoDiastolica) {
		this.respostaPressaoDiastolica = respostaPressaoDiastolica;
	}
	public Integer getRespostaPressaoSistolica() {
		return respostaPressaoSistolica;
	}
	public void setRespostaPressaoSistolica(Integer respostaPressaoSistolica) {
		this.respostaPressaoSistolica = respostaPressaoSistolica;
	}
	public String getRespostaTextual() {
		return respostaTextual;
	}
	public void setRespostaTextual(String respostaTextual) {
		this.respostaTextual = respostaTextual;
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
	public Date getDataQuestionario() {
		return dataQuestionario;
	}
	public void setDataQuestionario(Date dataQuestionario) {
		this.dataQuestionario = dataQuestionario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
}
