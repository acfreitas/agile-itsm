package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class HistoricoPushMessageDTO implements IDto {

    private static final long serialVersionUID = -8136843752304932841L;

    private Integer id;
    private Integer idUsuario;
    private String message;
    private Timestamp dateTime;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(final Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(final Timestamp dateTime) {
        this.dateTime = dateTime;
    }

}
