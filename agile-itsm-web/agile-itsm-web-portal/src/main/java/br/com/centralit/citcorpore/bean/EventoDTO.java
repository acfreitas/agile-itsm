package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;
import java.util.Date;

import br.com.citframework.dto.IDto;

public class EventoDTO implements IDto {
	private String id;
	private String title;
	private Timestamp start;
	private Timestamp end;
	private Boolean allDay;
	private String className;
	private String url;
	
	private String horaInicio;
	private Date data;
	private Integer idExecucao;
	private Integer idProgramacao;
	private String numeroOS;
	private String descricaoAtividadeOS;
	private String nomeTipoMudanca;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getStart() {
		return start;
	}
	public void setStart(Timestamp start) {
		this.start = start;
	}
	public Timestamp getEnd() {
		return end;
	}
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	public Boolean getAllDay() {
		return allDay;
	}
	public void setAllDay(Boolean allDay) {
		this.allDay = allDay;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
    public Integer getIdExecucao() {
        return idExecucao;
    }
    public void setIdExecucao(Integer idExecucao) {
        this.idExecucao = idExecucao;
    }
    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public Date getData() {
        return data;
    }
    public void setData(Date data) {
        this.data = data;
    }
    public Integer getIdProgramacao() {
        return idProgramacao;
    }
    public void setIdProgramacao(Integer idProgramacao) {
        this.idProgramacao = idProgramacao;
    }
	/**
	 * @return the numeroOS
	 */
	public String getNumeroOS() {
		return numeroOS;
	}
	/**
	 * @param numeroOS the numeroOS to set
	 */
	public void setNumeroOS(String numeroOS) {
		this.numeroOS = numeroOS;
	}
	/**
	 * @return the descricaoAtividadeOS
	 */
	public String getDescricaoAtividadeOS() {
		return descricaoAtividadeOS;
	}
	/**
	 * @param descricaoAtividadeOS the descricaoAtividadeOS to set
	 */
	public void setDescricaoAtividadeOS(String descricaoAtividadeOS) {
		this.descricaoAtividadeOS = descricaoAtividadeOS;
	}
	
	public String getNomeTipoMudanca() {
		return nomeTipoMudanca;
	}
	public void setNomeTipoMudanca(String nomeTipoMudanca) {
		this.nomeTipoMudanca = nomeTipoMudanca;
	}
}
