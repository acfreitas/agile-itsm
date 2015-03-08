package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Comparator;

import br.com.citframework.dto.IDto;

public class ScriptsDTO implements IDto, Comparator<ScriptsDTO>{
	private static final long serialVersionUID = 1L;

	static public final String TIPO_CONSULTA = "consulta";
	static public final String TIPO_UPDATE = "update";

	static public final int TIPO_CRIAR_TABELA = 1;
	static public final int TIPO_INSERIR_REGISTRO = 2;
	static public final int TIPO_DELETAR_REGISTRO = 3;
	static public final int TIPO_ADICIONAR_COLUNA = 4;
	static public final int TIPO_ADICIONAR_CONSTRAINT = 5;
	static public final int TIPO_ALTERAR_COLUNA = 6;
	static public final int TIPO_DELETAR_COLUNA = 7;
	static public final int TIPO_DELETAR_TABELA = 8;

	private Date dataFim;
	private Date dataInicio;
	private String descricao;
	private String historico;
	private Integer idScript;
	private Integer idVersao;
	private String nome;
	private String sqlQuery;
	private String tipo;

	public Date getDataFim() {
		return dataFim;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public String getDescricao() {
		return descricao;
	}

	public String getHistorico() {
		return historico;
	}

	public Integer getIdScript() {
		return idScript;
	}

	public Integer getIdVersao() {
		return idVersao;
	}

	public String getNome() {
		return nome;
	}

	public String getSqlQuery() {
		return sqlQuery;
	}

	public String getTipo() {
		return tipo;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public void setIdScript(Integer idScript) {
		this.idScript = idScript;
	}

	public void setIdVersao(Integer idVersao) {
		this.idVersao = idVersao;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setSqlQuery(String setSql) {
		this.sqlQuery = setSql;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public int compare(ScriptsDTO o1, ScriptsDTO o2) {
		return o1.getNome().compareToIgnoreCase(o2.getNome());
	}

}
