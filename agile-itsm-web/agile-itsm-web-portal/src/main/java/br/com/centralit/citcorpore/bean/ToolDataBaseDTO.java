package br.com.centralit.citcorpore.bean;
import br.com.citframework.dto.IDto;
public class ToolDataBaseDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String tabela;
	private String tipoAcao;
	private String strExec;
	private String quantRows;
	public String getTabela() {
		return tabela;
	}
	public void setTabela(String tabela) {
		this.tabela = tabela;
	}
	public String getTipoAcao() {
		return tipoAcao;
	}
	public void setTipoAcao(String tipoAcao) {
		this.tipoAcao = tipoAcao;
	}
	public String getStrExec() {
		return strExec;
	}
	public void setStrExec(String strExec) {
		this.strExec = strExec;
	}
	public String getQuantRows() {
		return quantRows;
	}
	public void setQuantRows(String quantRows) {
		this.quantRows = quantRows;
	}

}
