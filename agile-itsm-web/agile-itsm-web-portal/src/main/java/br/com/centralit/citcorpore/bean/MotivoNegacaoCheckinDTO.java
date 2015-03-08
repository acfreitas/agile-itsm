package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class MotivoNegacaoCheckinDTO implements IDto {

    private static final long serialVersionUID = -4031855517258858021L;

    private Integer idMotivo;
    private String descricao;
    private Date dataFim;

    public Integer getIdMotivo() {
        return idMotivo;
    }

    public void setIdMotivo(final Integer idMotivo) {
        this.idMotivo = idMotivo;
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

}
