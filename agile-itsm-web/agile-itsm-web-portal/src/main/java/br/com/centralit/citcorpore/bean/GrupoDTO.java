package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateTimeAdapter;

@SuppressWarnings("rawtypes")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Grupo") 
public class GrupoDTO implements IDto {

	private static final long serialVersionUID = -7848776827100833523L;
	
	private String permiteSuspensaoReativacao;
	
	private Integer idGrupo;

	private Integer idTipoFluxo;

	private Integer idEmpresa;

	private Integer idPerfilAcessoGrupo;

	private String nome;

	private String criar;

	private String executar;

	private String delegar;

	private String suspender;

	@XmlElement(name = "dataInicio")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)	
	private Date dataInicio;

	@XmlElement(name = "dataFim")
	@XmlJavaTypeAdapter(DateTimeAdapter.class)	
	private Date dataFim;

	private String descricao;

	private Collection colItens;

	private Integer[] idContrato;

	private String empregadosSerializados;
	
	private String empregadosSerializadosAux;
	
	private String emailsSerializados;

    private Integer paginaSelecionadaColaborador;
	
	private String sigla;

	private String serviceDesk;

	private ArrayList<PermissoesFluxoDTO> permissoesFluxos;
	
	private String comiteConsultivoMudanca;
	
	/*Notificações de e-mail*/
	private String abertura;
	private String andamento;
	private String encerramento;
	
	private String[] colEmpregadoCheckado = null;
	
	private String AllEmpregadosCheckados;
	/**
	 * @return valor do atributo idGrupo.
	 */
	public Integer getIdGrupo() {
		return idGrupo;
	}

	/**
	 * Define valor do atributo idGrupo.
	 * 
	 * @param idGrupo
	 */
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	/**
	 * @return valor do atributo idEmpresa.
	 */
	public Integer getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * Define valor do atributo idEmpresa.
	 * 
	 * @param idEmpresa
	 */
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	/**
	 * @return valor do atributo idPerfilAcessoGrupo.
	 */
	public Integer getIdPerfilAcessoGrupo() {
		return idPerfilAcessoGrupo;
	}

	/**
	 * Define valor do atributo idPerfilAcessoGrupo.
	 * 
	 * @param idPerfilAcessoGrupo
	 */
	public void setIdPerfilAcessoGrupo(Integer idPerfilAcessoGrupo) {
		this.idPerfilAcessoGrupo = idPerfilAcessoGrupo;
	}

	/**
	 * @return valor do atributo nome.
	 */
	public String getNome() {
		return Util.tratarAspasSimples( nome );
	}

	/**
	 * Define valor do atributo nome.
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * @return valor do atributo dataInicio.
	 */
	public Date getDataInicio() {
		return dataInicio;
	}

	/**
	 * Define valor do atributo dataInicio.
	 * 
	 * @param dataInicio
	 */
	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	/**
	 * @return valor do atributo dataFim.
	 */
	public Date getDataFim() {
		return dataFim;
	}

	/**
	 * Define valor do atributo dataFim.
	 * 
	 * @param dataFim
	 */
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	/**
	 * @return valor do atributo descricao.
	 */
	public String getDescricao() {
		return Util.tratarAspasSimples(descricao);
	}

	/**
	 * Define valor do atributo descricao.
	 * 
	 * @param descricao
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * @return valor do atributo colItens.
	 */
	public Collection getColItens() {
		return colItens;
	}

	/**
	 * Define valor do atributo colItens.
	 * 
	 * @param colItens
	 */
	public void setColItens(Collection colItens) {
		this.colItens = colItens;
	}

	public String getSigla() {
		return Util.tratarAspasSimples(sigla);
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getServiceDesk() {
		return serviceDesk;
	}

	public void setServiceDesk(String serviceDesk) {
		this.serviceDesk = serviceDesk;
	}

	/**
	 * @return the empregadosSerializados
	 */
	public String getEmpregadosSerializados() {
		return empregadosSerializados;
	}

	/**
	 * @param empregadosSerializados
	 *            the empregadosSerializados to set
	 */
	public void setEmpregadosSerializados(String empregadosSerializados) {
		this.empregadosSerializados = empregadosSerializados;
	}

	public String getEmailsSerializados() {
		return emailsSerializados;
	}

	public void setEmailsSerializados(String emailsSerializados) {
		this.emailsSerializados = emailsSerializados;
	}

	/**
	 * @return the idTipoFluxo
	 */
	public Integer getIdTipoFluxo() {
		return idTipoFluxo;
	}

	/**
	 * @param idTipoFluxo
	 *            the idTipoFluxo to set
	 */
	public void setIdTipoFluxo(Integer idTipoFluxo) {
		this.idTipoFluxo = idTipoFluxo;
	}

	/**
	 * @return the criar
	 */
	public String getCriar() {
		return criar;
	}

	/**
	 * @param criar
	 *            the criar to set
	 */
	public void setCriar(String criar) {
		this.criar = criar;
	}

	/**
	 * @return the executar
	 */
	public String getExecutar() {
		return executar;
	}

	/**
	 * @param executar
	 *            the executar to set
	 */
	public void setExecutar(String executar) {
		this.executar = executar;
	}

	/**
	 * @return the delegar
	 */
	public String getDelegar() {
		return delegar;
	}

	/**
	 * @param delegar
	 *            the delegar to set
	 */
	public void setDelegar(String delegar) {
		this.delegar = delegar;
	}

	/**
	 * @return the suspender
	 */
	public String getSuspender() {
		return suspender;
	}

	/**
	 * @param suspender
	 *            the suspender to set
	 */
	public void setSuspender(String suspender) {
		this.suspender = suspender;
	}

	public ArrayList<PermissoesFluxoDTO> getPermissoesFluxos() {
		return permissoesFluxos;
	}

	public void setPermissoesFluxos(ArrayList<PermissoesFluxoDTO> permissoesFluxos) {
		this.permissoesFluxos = permissoesFluxos;
	}

	public Integer[] getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer[] idContrato) {
		this.idContrato = idContrato;
	}

	public String getAbertura() {
		return abertura;
	}

	public void setAbertura(String abertura) {
		this.abertura = abertura;
	}

	public String getAndamento() {
		return andamento;
	}

	public void setAndamento(String andamento) {
		this.andamento = andamento;
	}

	public String getEncerramento() {
		return encerramento;
	}

	public void setEncerramento(String encerramento) {
		this.encerramento = encerramento;
	}

	/**
	 * @return the comiteControleMudanca
	 */
	public String getComiteConsultivoMudanca() {
		return comiteConsultivoMudanca;
	}

	/**
	 * @param comiteControleMudanca the comiteControleMudanca to set
	 */
	public void setComiteConsultivoMudanca(String comiteControleMudanca) {
		this.comiteConsultivoMudanca = comiteControleMudanca;
	}

	public Integer getPaginaSelecionadaColaborador() {
		return paginaSelecionadaColaborador;
	}

	public void setPaginaSelecionadaColaborador(Integer paginaSelecionadaColaborador) {
		this.paginaSelecionadaColaborador = paginaSelecionadaColaborador;
	}

	public String getEmpregadosSerializadosAux() {
		return empregadosSerializadosAux;
	}

	public void setEmpregadosSerializadosAux(String empregadosSerializadosAux) {
		this.empregadosSerializadosAux = empregadosSerializadosAux;
	}

	public String getPermiteSuspensaoReativacao() {
		return permiteSuspensaoReativacao;
	}

	public void setPermiteSuspensaoReativacao(String permiteSuspensaoReativacao) {
		this.permiteSuspensaoReativacao = permiteSuspensaoReativacao;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idGrupo == null) ? 0 : idGrupo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GrupoDTO other = (GrupoDTO) obj;
		if (idGrupo == null) {
			if (other.idGrupo != null)
				return false;
		} else if (!idGrupo.equals(other.idGrupo))
			return false;
		return true;
	}

	/**
	 * @return the AllEmpregadosCheckados
	 */
	public String[] getColEmpregadoCheckado() {
		return colEmpregadoCheckado;
	}

	/**
	 * @param AllEmpregadosCheckados the AllEmpregadosCheckados to set
	 */
	public void setColEmpregadoCheckado(String checkados) {
		 if (checkados != null)   
	            this.colEmpregadoCheckado = checkados.split(";");
	        else
	            this.colEmpregadoCheckado = new String[]{};
	}

	/**
	 * @return the allEmpregadosCheckados
	 */
	public String getAllEmpregadosCheckados() {
		return AllEmpregadosCheckados;
	}

	/**
	 * @param allEmpregadosCheckados the allEmpregadosCheckados to set
	 */
	public void setAllEmpregadosCheckados(String allEmpregadosCheckados) {
		AllEmpregadosCheckados = allEmpregadosCheckados;
	}

}