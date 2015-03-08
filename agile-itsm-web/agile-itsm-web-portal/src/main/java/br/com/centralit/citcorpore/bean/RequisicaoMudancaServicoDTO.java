package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.DateAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "RequisicaoMudancaServico")
public class RequisicaoMudancaServicoDTO implements IDto {

    private static final long serialVersionUID = -7757267504212503274L;

    private Integer idRequisicaoMudancaServico;
    private Integer idRequisicaoMudanca;
    private Integer idServico;

    // campos para mera visualização
    private String nome;
    private String descricao;

    @XmlElement(name = "dataFim")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date dataFim;

    public Integer getIdRequisicaoMudancaServico() {
        return idRequisicaoMudancaServico;
    }

    public void setIdRequisicaoMudancaServico(final Integer idRequisicaoMudancaServico) {
        this.idRequisicaoMudancaServico = idRequisicaoMudancaServico;
    }

    public Integer getIdRequisicaoMudanca() {
        return idRequisicaoMudanca;
    }

    public void setIdRequisicaoMudanca(final Integer idRequisicaoMudanca) {
        this.idRequisicaoMudanca = idRequisicaoMudanca;
    }

    public Integer getIdServico() {
        return idServico;
    }

    public void setIdServico(final Integer idServico) {
        this.idServico = idServico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(final String descricao) {
        this.descricao = descricao;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(final Date dataFim) {
        this.dataFim = dataFim;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (idRequisicaoMudanca == null ? 0 : idRequisicaoMudanca.hashCode());
        result = prime * result + (idRequisicaoMudancaServico == null ? 0 : idRequisicaoMudancaServico.hashCode());
        result = prime * result + (idServico == null ? 0 : idServico.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (this.getClass() != obj.getClass()) {
            return false;
        }

        if (obj instanceof RequisicaoMudancaServicoDTO) {
            final RequisicaoMudancaServicoDTO objComparacao = (RequisicaoMudancaServicoDTO) obj;
            if (objComparacao.getIdServico().equals(this.getIdServico()) && objComparacao.getIdRequisicaoMudanca().equals(this.getIdRequisicaoMudanca())) {
                return true;
            }
        }

        return false;
    }

}
