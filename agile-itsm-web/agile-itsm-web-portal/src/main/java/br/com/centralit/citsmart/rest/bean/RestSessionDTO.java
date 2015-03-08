package br.com.centralit.citsmart.rest.bean;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

public class RestSessionDTO implements IDto {

    private static final long serialVersionUID = -4117354478284577409L;

    private HttpSession httpSession;
    private Timestamp creation;
    private long maxTime;

    public HttpSession getHttpSession() {
        return httpSession;
    }

    public void setHttpSession(final HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    public Timestamp getCreation() {
        return creation;
    }

    public void setCreation(final Timestamp creation) {
        this.creation = creation;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(final long maxTime) {
        this.maxTime = maxTime;
    }

    public String getSessionID() {
        if (httpSession != null) {
            return httpSession.getId();
        } else {
            return null;
        }
    }

    public boolean isValid() {
        if (httpSession == null) {
            return false;
        }
        long tempo;
        try {
            tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(UtilDatas.getDataHoraAtual(), creation) / 1000;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;
        }
        return tempo <= maxTime;
    }

    public UsuarioDTO getUser() {
        if (!this.isValid()) {
            return null;
        }
        return (UsuarioDTO) this.getHttpSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
    }

    public Integer getUserId() {
        final UsuarioDTO userDto = this.getUser();
        if (userDto == null) {
            return null;
        }
        return userDto.getIdUsuario();
    }

    public Integer getDptoId() {
        final UsuarioDTO userDto = this.getUser();
        if (userDto == null) {
            return null;
        }
        return userDto.getIdUnidade();
    }

}
