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
public class ScriptMonitDTO implements IDto {

	private static final long serialVersionUID = -272158181369701516L;

	private Integer idScriptMonit;

	private Integer idMonitoramentoAtivos;

	private String script;

	private Date dataInicio;

	private Date DataFim;

	public Integer getIdScriptMonit() {
		return idScriptMonit;
	}

	public void setIdScriptMonit(Integer idScriptMonit) {
		this.idScriptMonit = idScriptMonit;
	}

	public Integer getIdMonitoramentoAtivos() {
		return idMonitoramentoAtivos;
	}

	public void setIdMonitoramentoAtivos(Integer idMonitoramentoAtivos) {
		this.idMonitoramentoAtivos = idMonitoramentoAtivos;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return DataFim;
	}

	public void setDataFim(Date dataFim) {
		DataFim = dataFim;
	}

}
