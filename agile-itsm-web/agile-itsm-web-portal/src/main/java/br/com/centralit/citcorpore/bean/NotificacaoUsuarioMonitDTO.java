/**
 * 
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

/**
 * @author valdoilo.damasceno
 * @since 13.06.2014
 */
public class NotificacaoUsuarioMonitDTO implements IDto {

	private static final long serialVersionUID = -8918038669670905991L;

	private Integer idNotificacaoUsuarioMonit;

	private Integer idMonitoramentoAtivos;

	private Integer idUsuario;

	private Date dataInicio;

	private Date dataFim;

	public Integer getIdNotificacaoUsuarioMonit() {
		return idNotificacaoUsuarioMonit;
	}

	public void setIdNotificacaoUsuarioMonit(Integer idNotificacaoUsuarioMonit) {
		this.idNotificacaoUsuarioMonit = idNotificacaoUsuarioMonit;
	}

	public Integer getIdMonitoramentoAtivos() {
		return idMonitoramentoAtivos;
	}

	public void setIdMonitoramentoAtivos(Integer idMonitoramentoAtivos) {
		this.idMonitoramentoAtivos = idMonitoramentoAtivos;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
