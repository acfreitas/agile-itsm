package br.com.centralit.citquestionario.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilStrings;

public class QuestionarioDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2494938226014601549L;
	public static final String MODO_CADASTRO = "C"; 
	public static final String MODO_VISUALIZACAO = "V";
	
	private Integer idQuestionario;
	private Integer idSolicitacaoServico;
	private Integer idQuestionarioOrigem;
	private Integer idQuestionarioCopiar;
	private Integer idCategoriaQuestionario;
	private String nomeQuestionario;
	private String ativo;
	private String detalhaHistorico;
	private String mostrarAbreFecha;
	private String subQuestionario;
	
	private Integer idEmpresa;
	
	private Integer idIdentificadorResposta;
	private Integer idItem;
	
	private Collection colGrupos;
	
	private String acao;
	
	private String nomeGrupoQuestionario;
	private Integer idGrupoQuestionario;
	
	private String reload;
	
	private String modo;
	
	private Integer idProfissional;
    private Date dataQuestionario;
    
    private String javaScript;
	
	public Integer getIdCategoriaQuestionario() {
		return idCategoriaQuestionario;
	}
	public void setIdCategoriaQuestionario(Integer idCategoriaQuestionario) {
		this.idCategoriaQuestionario = idCategoriaQuestionario;
	}
	public Integer getIdQuestionario() {
		return idQuestionario;
	}
	public void setIdQuestionario(Integer idQuestionario) {
		this.idQuestionario = idQuestionario;
	}
	public Integer getIdQuestionarioOrigem() {
        return idQuestionarioOrigem;
    }
    public void setIdQuestionarioOrigem(Integer idQuestionarioOrigem) {
        this.idQuestionarioOrigem = idQuestionarioOrigem;
    }
    public String getNomeQuestionarioAndCodigo() {
        return UtilStrings.nullToVazio(nomeQuestionario) + " (Código: " + this.getIdQuestionario() + ")";
    }
    public String getNomeQuestionario() {
    	return nomeQuestionario;
    }
    public void setNomeQuestionario(String nomeQuestionario) {
		this.nomeQuestionario = nomeQuestionario;
	}
	public Collection getColGrupos() {
		return colGrupos;
	}
	public String getAtivo() {
        return ativo;
    }
    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
    public void setColGrupos(Collection colGrupos) {
		this.colGrupos = colGrupos;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
	public Integer getIdIdentificadorResposta() {
		return idIdentificadorResposta;
	}
	public void setIdIdentificadorResposta(Integer idIdentificadorResposta) {
		this.idIdentificadorResposta = idIdentificadorResposta;
	}
	public Integer getIdGrupoQuestionario() {
		return idGrupoQuestionario;
	}
	public void setIdGrupoQuestionario(Integer idGrupoQuestionario) {
		this.idGrupoQuestionario = idGrupoQuestionario;
	}
	public String getNomeGrupoQuestionario() {
		return nomeGrupoQuestionario;
	}
	public void setNomeGrupoQuestionario(String nomeGrupoQuestionario) {
		this.nomeGrupoQuestionario = nomeGrupoQuestionario;
	}
	public Integer getIdQuestionarioCopiar() {
		return idQuestionarioCopiar;
	}
	public void setIdQuestionarioCopiar(Integer idQuestionarioCopiar) {
		this.idQuestionarioCopiar = idQuestionarioCopiar;
	}
	public String getReload() {
		return reload;
	}
	public void setReload(String reload) {
		this.reload = reload;
	}
	public Integer getIdSolicitacaoServico() {
        return idSolicitacaoServico;
    }
    public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
        this.idSolicitacaoServico = idSolicitacaoServico;
    }
    public String getDetalhaHistorico() {
		return detalhaHistorico;
	}
	public void setDetalhaHistorico(String detalhaHistorico) {
		this.detalhaHistorico = detalhaHistorico;
	}
    public String getModo() {
        return modo;
    }
    public void setModo(String modo) {
        this.modo = modo;
    }
	public String getMostrarAbreFecha() {
		return mostrarAbreFecha;
	}
	public void setMostrarAbreFecha(String mostrarAbreFecha) {
		this.mostrarAbreFecha = mostrarAbreFecha;
	}
	public String getSubQuestionario() {
		return subQuestionario;
	}
	public void setSubQuestionario(String subQuestionario) {
		this.subQuestionario = subQuestionario;
	}
	public Integer getIdItem() {
		return idItem;
	}
	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}
    public Integer getIdProfissional() {
        return idProfissional;
    }
    public void setIdProfissional(Integer idProfissional) {
        this.idProfissional = idProfissional;
    }
    public Date getDataQuestionario() {
        return dataQuestionario;
    }
    public void setDataQuestionario(Date dataQuestionario) {
        this.dataQuestionario = dataQuestionario;
    }
    public String getJavaScript() {
        return javaScript;
    }
    public void setJavaScript(String javaScript) {
        this.javaScript = javaScript;
    }
	
}
