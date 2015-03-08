
package br.com.centralit.citcorpore.bean;

import java.sql.Date;

/**
 * @author geber.costa
 */
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.citframework.dto.IDto;

public class OcorrenciaProblemaDTO implements IDto{

	private static final long serialVersionUID = -3055161845325828805L;

    private Integer idOcorrencia;
    private Integer idProblema;
    private Date dataInicio;
    private Date dataFim;
    private String categoria;
    private String origem;
    private String descricao;
    private String ocorrencia;
    private String informacoesContato;
    private Integer tempoGasto;
    private Date dataregistro;
    private String horaregistro;
    private String registradopor;
    private Integer idItemTrabalho;
    private String dadosProblema;
   
    
    private Integer idJustificativa;
    private String complementoJustificativa;
    
    private Integer idCategoriaOcorrencia;
    private Integer idOrigemOcorrencia;

    public Integer getIdOcorrencia() {
	return idOcorrencia;
    }

    public void setIdOcorrencia(Integer idOcorrencia) {
	this.idOcorrencia = idOcorrencia;
    }

    public String getCategoria() {
	return categoria;
    }
    public String getCategoriaDescricao() {
	if (categoria == null){
	    return "";
	}
	for(Enumerados.CategoriaOcorrencia c : Enumerados.CategoriaOcorrencia.values()){
	    if (categoria.equalsIgnoreCase(c.getSigla().toString())){
		return c.getDescricao();
	    }
	}	
	return "";
    }    

    public void setCategoria(String categoria) {
	this.categoria = categoria;
    }

    public String getOrigem() {
	return origem;
    }
    
    public String getOrigemDescricao() {
	if (origem == null){
	    return "";
	}
	for(Enumerados.OrigemOcorrencia o : Enumerados.OrigemOcorrencia.values()){
	    if (origem.equalsIgnoreCase(o.getSigla().toString())){
		return o.getDescricao();
	    }
	}	
	return "";
    }    

    public void setOrigem(String origem) {
	this.origem = origem;
    }

    public String getDescricao() {
	return descricao;
    }

    public void setDescricao(String descricao) {
	this.descricao = descricao;
    }

    public String getOcorrencia() {
	return ocorrencia;
    }

    public void setOcorrencia(String ocorrencia) {
	this.ocorrencia = ocorrencia;
    }

    public String getInformacoesContato() {
	return informacoesContato;
    }

    public void setInformacoesContato(String informacoesContato) {
	this.informacoesContato = informacoesContato;
    }

    public Integer getTempoGasto() {
	return tempoGasto;
    }

    public void setTempoGasto(Integer tempoGasto) {
	this.tempoGasto = tempoGasto;
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

    public Date getDataregistro() {
        return dataregistro;
    }

    public void setDataregistro(Date dataregistro) {
        this.dataregistro = dataregistro;
    }

    public String getHoraregistro() {
        return horaregistro;
    }

    public void setHoraregistro(String horaregistro) {
        this.horaregistro = horaregistro;
    }

    public String getRegistradopor() {
        return registradopor;
    }

    public void setRegistradopor(String registradopor) {
        this.registradopor = registradopor;
    }

	public Integer getIdItemTrabalho() {
		return idItemTrabalho;
	}

	public void setIdItemTrabalho(Integer idItemTrabalho) {
		this.idItemTrabalho = idItemTrabalho;
	}

	public Integer getIdJustificativa() {
		return idJustificativa;
	}

	public void setIdJustificativa(Integer idJustificativa) {
		this.idJustificativa = idJustificativa;
	}

	public String getComplementoJustificativa() {
		return complementoJustificativa;
	}

	public void setComplementoJustificativa(String complementoJustificativa) {
		this.complementoJustificativa = complementoJustificativa;
	}

	public Integer getIdProblema() {
		return idProblema;
	}

	public void setIdProblema(Integer idProblema) {
		this.idProblema = idProblema;
	}

	public String getDadosProblema() {
		return dadosProblema;
	}

	public void setDadosProblema(String dadosProblema) {
		this.dadosProblema = dadosProblema;
	}

	public Integer getIdCategoriaOcorrencia() {
		return idCategoriaOcorrencia;
	}

	public void setIdCategoriaOcorrencia(Integer idCategoriaOcorrencia) {
		this.idCategoriaOcorrencia = idCategoriaOcorrencia;
	}

	public Integer getIdOrigemOcorrencia() {
		return idOrigemOcorrencia;
	}

	public void setIdOrigemOcorrencia(Integer idOrigemOcorrencia) {
		this.idOrigemOcorrencia = idOrigemOcorrencia;
	}
}
