package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilHTML;

public class AssinaturaAprovacaoProjetoDTO implements IDto, Comparable<AssinaturaAprovacaoProjetoDTO> {

	private Integer idAssinaturaAprovacaoProjeto;
	private Integer idProjeto;
	private Integer idEmpregadoAssinatura;
	private String papel;
	private String ordem;
	private EmpregadoDTO empregadoDTO;
	private String nome;
	private String matricula;
	
	
	public Integer getIdAssinaturaAprovacaoProjeto() {
		return idAssinaturaAprovacaoProjeto;
	}
	public void setIdAssinaturaAprovacaoProjeto(
			Integer idAssinaturaAprovacaoProjeto) {
		this.idAssinaturaAprovacaoProjeto = idAssinaturaAprovacaoProjeto;
	}
	public Integer getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}
	public Integer getIdEmpregadoAssinatura() {
		return idEmpregadoAssinatura;
	}
	public void setIdEmpregadoAssinatura(Integer idEmpregadoAssinatura) {
		this.idEmpregadoAssinatura = idEmpregadoAssinatura;
	}
	public String getPapel() {
		return papel;
	}
	public String getPapelHTMLEncoded() {
		return UtilHTML.encodeHTML(papel);
	}
	public void setPapel(String papel) {
		this.papel = papel;
	}
	public String getOrdem() {
		return ordem;
	}
	public void setOrdem(String ordem) {
		this.ordem = ordem;
	}
	public EmpregadoDTO getEmpregadoDTO() {
		return empregadoDTO;
	}
	public void setEmpregadoDTO(EmpregadoDTO empregadoDTO) {
		this.empregadoDTO = empregadoDTO;
	}
	public String getNome() {
		return nome;
	}
	public String getNomeHTMLEncoded() {
		return UtilHTML.encodeHTML(nome);
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMatricula() {
		return matricula;
	}
	public String getMatriculaHTMLEncoded() {
		return UtilHTML.encodeHTML(matricula);
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public int compareTo(AssinaturaAprovacaoProjetoDTO assinatura){
		return ordem.compareTo(assinatura.getOrdem());
	}
	
}
