package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;

import br.com.centralit.citcorpore.util.Enumerados.SituacaoCotacao;
import br.com.citframework.dto.IDto;

public class CotacaoDTO implements IDto {
	private Integer idCotacao;
	private String identificacao;
	private Integer idEmpresa;
	private Timestamp dataHoraCadastro;
	private Integer idResponsavel;
	private String observacoes;
	private String situacao;
	private Date dataFinalPrevista;
	
	private Date dataInicialRequisicao;
	private Date dataFinalRequisicao;
	private Integer idCentroCusto;
	private Integer idProjeto;
	
	private Integer idItemCotacao;
	private Integer idFornecedor;
	private Integer idItemColeta;
    private Integer idFornecedorColeta;
    
    private UsuarioDTO usuarioDto;
    private String situacaoStr;
    
	private String colCriterios_Serialize;

	public Integer getIdCotacao(){
		return this.idCotacao;
	}
	public void setIdCotacao(Integer parm){
		this.idCotacao = parm;
	}

	public Integer getIdEmpresa(){
		return this.idEmpresa;
	}
	public void setIdEmpresa(Integer parm){
		this.idEmpresa = parm;
	}

	public Integer getIdResponsavel(){
		return this.idResponsavel;
	}
	public void setIdResponsavel(Integer parm){
		this.idResponsavel = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
    public String getIdentificacao() {
        return identificacao;
    }
    public void setIdentificacao(String identificacao) {
        this.identificacao = identificacao;
    }
    public Date getDataInicialRequisicao() {
        return dataInicialRequisicao;
    }
    public void setDataInicialRequisicao(Date dataInicialRequisicao) {
        this.dataInicialRequisicao = dataInicialRequisicao;
    }
    public Date getDataFinalRequisicao() {
        return dataFinalRequisicao;
    }
    public void setDataFinalRequisicao(Date dataFinalRequisicao) {
        this.dataFinalRequisicao = dataFinalRequisicao;
    }
    public Integer getIdCentroCusto() {
        return idCentroCusto;
    }
    public void setIdCentroCusto(Integer idCentroCusto) {
        this.idCentroCusto = idCentroCusto;
    }
    public Integer getIdProjeto() {
        return idProjeto;
    }
    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }
    public String getObservacoes() {
        return observacoes;
    }
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
    public Timestamp getDataHoraCadastro() {
        return dataHoraCadastro;
    }
    public void setDataHoraCadastro(Timestamp dataHoraCadastro) {
        this.dataHoraCadastro = dataHoraCadastro;
    }
    public Date getDataFinalPrevista() {
        return dataFinalPrevista;
    }
    public void setDataFinalPrevista(Date dataFinalPrevista) {
        this.dataFinalPrevista = dataFinalPrevista;
    }
    public String getColCriterios_Serialize() {
        return colCriterios_Serialize;
    }
    public void setColCriterios_Serialize(String colCriterios_Serialize) {
        this.colCriterios_Serialize = colCriterios_Serialize;
    }
    public Integer getIdItemCotacao() {
        return idItemCotacao;
    }
    public void setIdItemCotacao(Integer idItemCotacao) {
        this.idItemCotacao = idItemCotacao;
    }
    public Integer getIdFornecedor() {
        return idFornecedor;
    }
    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
    public Integer getIdItemColeta() {
        return idItemColeta;
    }
    public void setIdItemColeta(Integer idItemColeta) {
        this.idItemColeta = idItemColeta;
    }
    public Integer getIdFornecedorColeta() {
        return idFornecedorColeta;
    }
    public void setIdFornecedorColeta(Integer idFornecedorColeta) {
        this.idFornecedorColeta = idFornecedorColeta;
    }
    public UsuarioDTO getUsuarioDto() {
        return usuarioDto;
    }
    public void setUsuarioDto(UsuarioDTO usuarioDto) {
        this.usuarioDto = usuarioDto;
    }
    public String getSituacaoStr() {
        situacaoStr = "";
        if (situacao != null)
            situacaoStr = SituacaoCotacao.valueOf(situacao).getDescricao();
        return situacaoStr;
    }
    public void setSituacaoStr(String situacaoStr) {
        this.situacaoStr = situacaoStr;
    }

}
