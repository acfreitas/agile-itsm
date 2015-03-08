package br.com.centralit.citcorpore.rh.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class LotacaoDTO implements IDto {
	 	private Integer idLotacao;
		private Integer idCargo;
		private Integer idEmpregado;
		private Integer idEmpresa;
		private Integer idFuncao;
		private Date dataInicio;
		private Date dataFim;
		
		private String nomeLotacao;
		
		public Integer getIdLotacao() {
			return idLotacao;
		}
		public void setIdLotacao(Integer idLotacao) {
			this.idLotacao = idLotacao;
		}
		public Integer getIdCargo() {
			return idCargo;
		}
		public void setIdCargo(Integer idCargo) {
			this.idCargo = idCargo;
		}
		public Integer getIdEmpregado() {
			return idEmpregado;
		}
		public void setIdEmpregado(Integer idEmpregado) {
			this.idEmpregado = idEmpregado;
		}
		public Integer getIdEmpresa() {
			return idEmpresa;
		}
		public void setIdEmpresa(Integer idEmpresa) {
			this.idEmpresa = idEmpresa;
		}
		public Integer getIdFuncao() {
			return idFuncao;
		}
		public void setIdFuncao(Integer idFuncao) {
			this.idFuncao = idFuncao;
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
		public String getNomeLotacao() {
			return nomeLotacao;
		}
		public void setNomeLotacao(String nomeLotacao) {
	        this.nomeLotacao = nomeLotacao;
	    }
		
	}