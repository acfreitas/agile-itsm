package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class EventTotalsDTO implements IDto {
	private int qtdeCritical;
	private int qtdeWarning;
	private int qtdeOk;
	private int qtdeUnknown;
	
	private int qtdeDown;
	private int qtdeUp;
	
	public EventTotalsDTO(){
		qtdeCritical = 0;
		qtdeWarning = 0;
		qtdeOk = 0;
		qtdeUnknown = 0;
		
		qtdeDown = 0;
		qtdeUp = 0;
	}
	public int getQtdeCritical() {
		return qtdeCritical;
	}
	public void setQtdeCritical(int qtdeCritical) {
		this.qtdeCritical = qtdeCritical;
	}
	public int getQtdeWarning() {
		return qtdeWarning;
	}
	public void setQtdeWarning(int qtdeWarning) {
		this.qtdeWarning = qtdeWarning;
	}
	public int getQtdeOk() {
		return qtdeOk;
	}
	public void setQtdeOk(int qtdeOk) {
		this.qtdeOk = qtdeOk;
	}
	public int getQtdeUnknown() {
		return qtdeUnknown;
	}
	public void setQtdeUnknown(int qtdeUnknown) {
		this.qtdeUnknown = qtdeUnknown;
	}
	public int getQtdeDown() {
		return qtdeDown;
	}
	public void setQtdeDown(int qtdeDown) {
		this.qtdeDown = qtdeDown;
	}
	public int getQtdeUp() {
		return qtdeUp;
	}
	public void setQtdeUp(int qtdeUp) {
		this.qtdeUp = qtdeUp;
	}
}
