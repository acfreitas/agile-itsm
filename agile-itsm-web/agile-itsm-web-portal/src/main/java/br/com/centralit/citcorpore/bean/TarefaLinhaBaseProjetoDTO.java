package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

public class TarefaLinhaBaseProjetoDTO implements IDto {
	public static final String ATIVO = "A";
	public static final String PRONTO = "P";
	public static final String FALHA = "F";
	public static final String SUSPENSA = "S";
	public static final String SEMDEFINICAO = "?";
	
	private Integer idTarefaLinhaBaseProjeto;
	private Integer idLinhaBaseProjeto;
	private Integer sequencia;
	private Integer idCalendario;
	private Integer idTarefaLinhaBaseProjetoVinc;
	private java.sql.Date dataInicio;
	private java.sql.Date dataFim;
	private String codeTarefa;
	private String nomeTarefa;
	private String detalhamentoTarefa;
	private Double progresso;
	private Double duracaoHora;
	private Integer nivel;
	private String idInternal;
	private String collapsed;
	private Double custo;
	private Double custoPerfil;
	private String situacao;
	private String estimada;
	private Double trabalho;
	private java.sql.Date dataInicioReal;
	private java.sql.Date dataFimReal;
	private Double duracaoHoraReal;
	private Double custoReal;
	private Double custoRealPerfil;
	private Integer idTarefaLinhaBaseProjetoMigr;
	private Integer idTarefaLinhaBaseProjetoPai;
	private Double tempoTotAlocMinutos;
	private Integer idPagamentoProjeto;
	private Integer idMarcoPagamentoPrj;
	private String depends;
	
	
	public String getDepends() {
		return depends;
	}
	public void setDepends(String depends) {
		this.depends = depends;
	}
	private Integer idRecursoTarefaLinBaseProj;
	private Integer idPerfilContrato;
	private Integer idEmpregado;
	private Double percentualAloc;
	private String tempoAloc;
	private Double percentualExec;
	
	
	private Collection colRecursos;
	private Collection colProdutos;
	private Collection colPerfis;
	
	private String nomesRecursos;
	private String nomesProdutos;
	private String nomesPerfis;
	private String esforcoPorOS;
	
	
	
	public Integer getIdTarefaLinhaBaseProjeto(){
		return this.idTarefaLinhaBaseProjeto;
	}
	public void setIdTarefaLinhaBaseProjeto(Integer parm){
		this.idTarefaLinhaBaseProjeto = parm;
	}

	public Integer getIdLinhaBaseProjeto(){
		return this.idLinhaBaseProjeto;
	}
	public void setIdLinhaBaseProjeto(Integer parm){
		this.idLinhaBaseProjeto = parm;
	}

	public Integer getSequencia(){
		return this.sequencia;
	}
	public void setSequencia(Integer parm){
		this.sequencia = parm;
	}

	public Integer getIdCalendario(){
		return this.idCalendario;
	}
	public void setIdCalendario(Integer parm){
		this.idCalendario = parm;
	}

	public Integer getIdTarefaLinhaBaseProjetoVinc(){
		return this.idTarefaLinhaBaseProjetoVinc;
	}
	public void setIdTarefaLinhaBaseProjetoVinc(Integer parm){
		this.idTarefaLinhaBaseProjetoVinc = parm;
	}

	public java.sql.Date getDataInicio(){
		return this.dataInicio;
	}
	public void setDataInicio(java.sql.Date parm){
		this.dataInicio = parm;
	}

	public java.sql.Date getDataFim(){
		return this.dataFim;
	}
	public void setDataFim(java.sql.Date parm){
		this.dataFim = parm;
	}

	public String getCodeTarefa(){
		return this.codeTarefa;
	}
	public void setCodeTarefa(String parm){
		this.codeTarefa = parm;
	}
	public String getNomeTarefaNivel(){
		if (this.getNivel() == null){
			return this.nomeTarefa;	
		}
		String str = "";
		for (int i = 0; i < this.getNivel(); i++){
			str += "   "; 
		}
		return str + this.nomeTarefa;
	}
	public String getNomeTarefaNivelHTML(){
		String ret = getNomeTarefaNivel();
		if (ret != null){
			ret = ret.replaceAll(" ", "&nbsp;");
			return ret;
		}
		return ret;
	}
	public String getNomeTarefaNivelPonto(){
		String ret = getNomeTarefaNivel();
		if (ret != null){
			ret = ret.replaceAll(" ", ".");
			return ret;
		}
		return ret;
	}	
	public String getNomeTarefaNivelPontoHTMLEncoded(){
		String ret = getNomeTarefaNivel();
		if (ret != null){
			ret = ret.replaceAll(" ", ".");
			return UtilHTML.encodeHTML(UtilStrings.nullToVazio(ret));
		}
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(ret));
	}	
	public String getNomeTarefa(){
		return this.nomeTarefa;
	}
	public void setNomeTarefa(String parm){
		this.nomeTarefa = parm;
	}

	public String getDetalhamentoTarefa(){
		return this.detalhamentoTarefa;
	}
	public void setDetalhamentoTarefa(String parm){
		this.detalhamentoTarefa = parm;
	}

	public Double getProgresso(){
		return this.progresso;
	}
	public void setProgresso(Double parm){
		this.progresso = parm;
	}

	public Double getDuracaoHora(){
		return this.duracaoHora;
	}
	public void setDuracaoHora(Double parm){
		this.duracaoHora = parm;
	}
	public boolean isTarefaFase(){
		int niv = 0;
		if (nivel != null){
			niv = nivel.intValue();
		}
		if (niv <= 1){
			if (this.getColProdutos() != null && this.getColProdutos().size() > 0){
				return false;
			}
			if (this.getColRecursos() != null && this.getColRecursos().size() > 0){
				return false;
			}
			return true;
		}
		return false;
	}
	public Integer getNivel(){
		return this.nivel;
	}
	public void setNivel(Integer parm){
		this.nivel = parm;
	}

	public String getIdInternal(){
		return this.idInternal;
	}
	public void setIdInternal(String parm){
		this.idInternal = parm;
	}

	public String getCollapsed(){
		return this.collapsed;
	}
	public void setCollapsed(String parm){
		this.collapsed = parm;
	}

	public Double getCusto(){
		return this.custo;
	}
	public void setCusto(Double parm){
		this.custo = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

	public String getEstimada(){
		return this.estimada;
	}
	public void setEstimada(String parm){
		this.estimada = parm;
	}

	public Double getTrabalho(){
		return this.trabalho;
	}
	public void setTrabalho(Double parm){
		this.trabalho = parm;
	}

	public java.sql.Date getDataInicioReal(){
		return this.dataInicioReal;
	}
	public void setDataInicioReal(java.sql.Date parm){
		this.dataInicioReal = parm;
	}

	public java.sql.Date getDataFimReal(){
		return this.dataFimReal;
	}
	public void setDataFimReal(java.sql.Date parm){
		this.dataFimReal = parm;
	}

	public Double getDuracaoHoraReal(){
		return this.duracaoHoraReal;
	}
	public void setDuracaoHoraReal(Double parm){
		this.duracaoHoraReal = parm;
	}

	public Double getCustoReal(){
		return this.custoReal;
	}
	public void setCustoReal(Double parm){
		this.custoReal = parm;
	}
	public Collection getColRecursos() {
		return colRecursos;
	}
	public void setColRecursos(Collection colRecursos) {
		this.colRecursos = colRecursos;
	}
	public String getProdutos(){
		return "------ XXXXX-----";
	}
	public Collection getColProdutos() {
		return colProdutos;
	}
	public void setColProdutos(Collection colProdutos) {
		this.colProdutos = colProdutos;
	}
	public Integer getIdRecursoTarefaLinBaseProj() {
		return idRecursoTarefaLinBaseProj;
	}
	public void setIdRecursoTarefaLinBaseProj(Integer idRecursoTarefaLinBaseProj) {
		this.idRecursoTarefaLinBaseProj = idRecursoTarefaLinBaseProj;
	}
	public Integer getIdPerfilContrato() {
		return idPerfilContrato;
	}
	public void setIdPerfilContrato(Integer idPerfilContrato) {
		this.idPerfilContrato = idPerfilContrato;
	}
	public Integer getIdEmpregado() {
		return idEmpregado;
	}
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}
	public Double getPercentualAloc() {
		return percentualAloc;
	}
	public void setPercentualAloc(Double percentualAloc) {
		this.percentualAloc = percentualAloc;
	}
	public String getTempoAloc() {
		return tempoAloc;
	}
	public void setTempoAloc(String tempoAloc) {
		this.tempoAloc = tempoAloc;
	}
	public Double getPercentualExec() {
		return percentualExec;
	}
	public void setPercentualExec(Double percentualExec) {
		this.percentualExec = percentualExec;
	}
	public Integer getIdTarefaLinhaBaseProjetoMigr() {
		return idTarefaLinhaBaseProjetoMigr;
	}
	public void setIdTarefaLinhaBaseProjetoMigr(Integer idTarefaLinhaBaseProjetoMigr) {
		this.idTarefaLinhaBaseProjetoMigr = idTarefaLinhaBaseProjetoMigr;
	}
	public String getNomesRecursos() {
		return nomesRecursos;
	}
	public void setNomesRecursos(String nomesRecursos) {
		this.nomesRecursos = nomesRecursos;
	}
	public boolean isTemProdutos(){
		if (nomesProdutos == null){
			return false;
		}
		if (nomesProdutos.trim().equalsIgnoreCase("")){
			return false;
		}
		return true;
	}
	public String getNomesProdutos() {
		return nomesProdutos;
	}
	public String getNomesProdutosHTMLEncoded() {
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(nomesProdutos));
	}
	public void setNomesProdutos(String nomesProdutos) {
		this.nomesProdutos = nomesProdutos;
	}
	public Double getCustoPerfil() {
		return custoPerfil;
	}
	public String getCustoPerfilStr() {
		if (custoPerfil == null){
			return "";
		}
		return UtilFormatacao.formatDouble(custoPerfil,2);
	}	
	public String getCustoPerfilStrHTMLEncoded() {
		if (custoPerfil == null){
			return "";
		}
		return UtilHTML.encodeHTML(UtilStrings.nullToVazio(UtilFormatacao.formatDouble(custoPerfil,2)));
	}	
	public void setCustoPerfil(Double custoPerfil) {
		this.custoPerfil = custoPerfil;
	}
	public Double getCustoRealPerfil() {
		return custoRealPerfil;
	}
	public void setCustoRealPerfil(Double custoRealPerfil) {
		this.custoRealPerfil = custoRealPerfil;
	}
	public Integer getIdTarefaLinhaBaseProjetoPai() {
		return idTarefaLinhaBaseProjetoPai;
	}
	public void setIdTarefaLinhaBaseProjetoPai(Integer idTarefaLinhaBaseProjetoPai) {
		this.idTarefaLinhaBaseProjetoPai = idTarefaLinhaBaseProjetoPai;
	}
	public Collection getColPerfis() {
		return colPerfis;
	}
	public void setColPerfis(Collection colPerfis) {
		this.colPerfis = colPerfis;
	}
	public String getNomesPerfis() {
		return nomesPerfis;
	}
	public void setNomesPerfis(String nomesPerfis) {
		this.nomesPerfis = nomesPerfis;
	}
	public Double getTempoTotAlocMinutos() {
		return tempoTotAlocMinutos;
	}
	public void setTempoTotAlocMinutos(Double tempoTotAlocMinutos) {
		this.tempoTotAlocMinutos = tempoTotAlocMinutos;
	}
	public Integer getIdPagamentoProjeto() {
		return idPagamentoProjeto;
	}
	public void setIdPagamentoProjeto(Integer idPagamentoProjeto) {
		this.idPagamentoProjeto = idPagamentoProjeto;
	}
	public Integer getIdMarcoPagamentoPrj() {
		return idMarcoPagamentoPrj;
	}
	public void setIdMarcoPagamentoPrj(Integer idMarcoPagamentoPrj) {
		this.idMarcoPagamentoPrj = idMarcoPagamentoPrj;
	}
	public String getEsforcoPorOS() {
		return esforcoPorOS;
	}
	public void setEsforcoPorOS(String esforcoPorOS) {
		this.esforcoPorOS = esforcoPorOS;
	}

}
