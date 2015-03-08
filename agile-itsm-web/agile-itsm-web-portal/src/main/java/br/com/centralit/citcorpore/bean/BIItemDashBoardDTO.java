package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class BIItemDashBoardDTO implements IDto {
	private Integer idItemDashBoard;
	private Integer idDashBoard;
	private Integer idConsulta;
	private String titulo;
	private Integer posicao;
	private Integer itemTop;
	private Integer itemLeft;
	private Integer itemWidth;
	private Integer itemHeight;
	private String parmsSubst;

	public Integer getIdItemDashBoard(){
		return this.idItemDashBoard;
	}
	public void setIdItemDashBoard(Integer parm){
		this.idItemDashBoard = parm;
	}

	public Integer getIdDashBoard(){
		return this.idDashBoard;
	}
	public void setIdDashBoard(Integer parm){
		this.idDashBoard = parm;
	}

	public Integer getIdConsulta(){
		return this.idConsulta;
	}
	public void setIdConsulta(Integer parm){
		this.idConsulta = parm;
	}

	public String getTitulo(){
		return this.titulo;
	}
	public void setTitulo(String parm){
		this.titulo = parm;
	}

	public Integer getPosicao(){
		return this.posicao;
	}
	public void setPosicao(Integer parm){
		this.posicao = parm;
	}

	public Integer getItemTop(){
		return this.itemTop;
	}
	public void setItemTop(Integer parm){
		this.itemTop = parm;
	}

	public Integer getItemLeft(){
		return this.itemLeft;
	}
	public void setItemLeft(Integer parm){
		this.itemLeft = parm;
	}

	public Integer getItemWidth(){
		return this.itemWidth;
	}
	public void setItemWidth(Integer parm){
		this.itemWidth = parm;
	}

	public Integer getItemHeight(){
		return this.itemHeight;
	}
	public void setItemHeight(Integer parm){
		this.itemHeight = parm;
	}
	public String getParmsSubst() {
		return parmsSubst;
	}
	public void setParmsSubst(String parmsSubst) {
		this.parmsSubst = parmsSubst;
	}

}
