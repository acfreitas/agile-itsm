package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

public class ProjetoDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7848776827100833523L;
	private Integer idProjeto;
	private Integer idProjetoAutorizacao;
    private Integer idProjetoPai;
	private Integer idCliente;
	private String nomeProjeto;
	private String detalhamento;
	private String situacao;
	private Double valorEstimado;
	
	private Integer nivel;
	private Integer idContrato;
	
	private String vinculoOS;
	private Integer idOs;
	
	private String sigla;
	private String emergencial;
	private String severidade;
	private String nomeGestor;
	private Integer idRequisicaoMudanca;
	private Integer idLiberacao;
	
	private Integer idServicoContrato;
	private Integer ano;
	private String numero;
	private String demanda;
	private String objetivo;	
	private String nomeAreaRequisitante;
	private String nomeServicoOS;
	private Date dataEmissao;
	
    private String deleted;
	
	private Integer idLinhaBaseProjeto;
	private String justificativaMudanca;
	
	private Collection colRecursos;
	
	private Collection colAssinaturasAprovacoes;
	
	public String getDetalhamento() {
		return detalhamento;
	}
	public String getDetalhamentoFmt() {
		if (detalhamento == null){
			return "";
		}
		return detalhamento;
	}	
    public String getDetalhamentoFmtHTMEncoded() {
        if (detalhamento == null){
                return "";
        }
        return StringEscapeUtils.escapeHtml(detalhamento);
    }
	public void setDetalhamento(String detalhamento) {
		this.detalhamento = detalhamento;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Integer getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}
	public String getNomeProjeto() {
		return nomeProjeto;
	}
	public String getNomeProjetoHTMLEncoded() {
		return UtilHTML.encodeHTML(nomeProjeto);
	}
	public void setNomeProjeto(String nomeProjeto) {
		this.nomeProjeto = nomeProjeto;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public Double getValorEstimado() {
		return valorEstimado;
	}
	public void setValorEstimado(Double valorEstimado) {
		this.valorEstimado = valorEstimado;
	}
    public Integer getIdProjetoPai() {
        return idProjetoPai;
    }
    public void setIdProjetoPai(Integer idProjetoPai) {
        this.idProjetoPai = idProjetoPai;
    }
    public Integer getNivel() {
        return nivel;
    }
    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }
    
    public String getNomeHierarquizado() {
        
        if (nomeProjeto == null) 
            return "";
        
        if (idProjeto == null)
            return "";
        
        if (this.getNivel() == null) 
            return idProjeto + " " + nomeProjeto;
        
        String aux = "";
        
        for (int i = 0; i < this.getNivel().intValue(); i++) {
            aux += ".....";
        }
        
        return aux + idProjeto + " " + nomeProjeto;
    }
	public Integer getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}
	public Collection getColRecursos() {
		return colRecursos;
	}
	public void setColRecursos(Collection colRecursos) {
		this.colRecursos = colRecursos;
	}
	public String getVinculoOS() {
		return vinculoOS;
	}
	public void setVinculoOS(String vinculoOS) {
		this.vinculoOS = vinculoOS;
	}
	public Integer getIdOs() {
		return idOs;
	}
	public void setIdOs(Integer idOs) {
		this.idOs = idOs;
	}
	public Integer getIdServicoContrato() {
		return idServicoContrato;
	}
	public void setIdServicoContrato(Integer idServicoContrato) {
		this.idServicoContrato = idServicoContrato;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	public String getNumeroStr() {
		if (numero == null){
			return "";
		}
		return UtilStrings.nullToVazio(numero);
	}
	public String getNumeroStrHTMLEnconded() {
		if (numero == null){
			return "";
		}
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(numero));
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getDemanda() {
		return demanda;
	}
	public void setDemanda(String demanda) {
		this.demanda = demanda;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getNomeAreaRequisitante() {
		return UtilStrings.nullToVazio(nomeAreaRequisitante);
	}
	public String getNomeAreaRequisitanteHTMLEncoded() {
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(nomeAreaRequisitante));
	}
	public void setNomeAreaRequisitante(String nomeAreaRequisitante) {
		this.nomeAreaRequisitante = nomeAreaRequisitante;
	}
	public String getNomeServicoOS() {
		return UtilStrings.nullToVazio(nomeServicoOS);
	}
	public String getNomeServicoOSHTMLEncoded() {
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(nomeServicoOS));
	}
	public void setNomeServicoOS(String nomeServicoOS) {
		this.nomeServicoOS = nomeServicoOS;
	}
	public String getSigla() {
		return UtilStrings.nullToVazio(sigla);
	}
	public String getSiglaHTMLEncoded() {
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(sigla));
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public String getEmergencialStr() {
		if (emergencial == null){
			return "";
		}
		if (emergencial.equalsIgnoreCase("S")){
			return "Sim";
		}else if (emergencial.equalsIgnoreCase("N")){
			return "Não";
		}
		return "";
	}	
	public String getEmergencialStrHTMLEncoded() {
		if (emergencial == null){
			return "";
		}
		if (emergencial.equalsIgnoreCase("S")){
			return UtilHTML.encodeHTML("Sim");
		}else if (emergencial.equalsIgnoreCase("N")){
			return UtilHTML.encodeHTML("Não");
		}
		return "";
	}	
	public String getEmergencial() {
		return emergencial;
	}
	public void setEmergencial(String emergencial) {
		this.emergencial = emergencial;
	}
	public String getSeveridade() {
		return UtilStrings.nullToVazio(severidade);
	}
	public String getSeveridadeHTMLEncoded() {
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(severidade));
	}
	public void setSeveridade(String severidade) {
		this.severidade = severidade;
	}
	public String getNomeGestor() {
		return UtilStrings.nullToVazio(nomeGestor);
	}
	public String getNomeGestorHTMLEncoded() {
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(nomeGestor));
	}
	public void setNomeGestor(String nomeGestor) {
		this.nomeGestor = nomeGestor;
	}
	public Integer getIdRequisicaoMudanca() {
		return idRequisicaoMudanca;
	}
	public void setIdRequisicaoMudanca(Integer idRequisicaoMudanca) {
		this.idRequisicaoMudanca = idRequisicaoMudanca;
	}
	public Integer getIdLiberacao() {
		return idLiberacao;
	}
	public void setIdLiberacao(Integer idLiberacao) {
		this.idLiberacao = idLiberacao;
	}
	public String getDataEmissaoStr() {
		if (dataEmissao == null){
			return "";
		}
		return UtilDatas.dateToSTR(dataEmissao);
	}
	public String getDataEmissaoStrHTMLEncoded() {
		if (dataEmissao == null){
			return "";
		}
		return UtilHTML.encodeHTML(UtilDatas.dateToSTR(dataEmissao));
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public Integer getIdLinhaBaseProjeto() {
		return idLinhaBaseProjeto;
	}
	public void setIdLinhaBaseProjeto(Integer idLinhaBaseProjeto) {
		this.idLinhaBaseProjeto = idLinhaBaseProjeto;
	}
	public String getJustificativaMudanca() {
		return justificativaMudanca;
	}
	public void setJustificativaMudanca(String justificativaMudanca) {
		this.justificativaMudanca = justificativaMudanca;
	}
	public Integer getIdProjetoAutorizacao() {
		return idProjetoAutorizacao;
	}
	public void setIdProjetoAutorizacao(Integer idProjetoAutorizacao) {
		this.idProjetoAutorizacao = idProjetoAutorizacao;
	}
	public Collection getColAssinaturasAprovacoes() {
		return colAssinaturasAprovacoes;
	}
	public void setColAssinaturasAprovacoes(Collection colAssinaturasAprovacoes) {
		this.colAssinaturasAprovacoes = colAssinaturasAprovacoes;
	}
	public String getDeleted() {
		return deleted;
	}
	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}
	
	
}
