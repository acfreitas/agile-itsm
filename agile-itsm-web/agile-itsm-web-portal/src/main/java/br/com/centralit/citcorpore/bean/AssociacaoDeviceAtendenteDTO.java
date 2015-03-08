package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.push.DevicePlatformType;

/**
 * DTO para persistência dos dados de alocação de um device a um atendente
 *
 * @author bruno.ribeiro - <a href="mailto:bruno.ribeiro@centrait.com.br">bruno.ribeiro@centrait.com.br</a>
 * @since 15/11/2014
 */
public class AssociacaoDeviceAtendenteDTO implements IDto {

    private static final long serialVersionUID = 3025354135360257061L;

    private Integer id;
    private Integer idUsuario;
    private String token;
    private Integer active;
    private String connection;
    private Integer devicePlatform;
    private String nomeAtendente;

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

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(final String connection) {
        this.connection = connection;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(final Integer active) {
        this.active = active;
    }

    public boolean isActive() {
        return this.getActive() == 1;
    }

    public Integer getDevicePlatform() {
        return devicePlatform;
    }

    public void setDevicePlatform(final Integer devicePlatform) {
        this.devicePlatform = devicePlatform;
    }

    public DevicePlatformType getDevicePlatformType() {
        if (this.getDevicePlatform() != null) {
            return DevicePlatformType.fromId(this.getDevicePlatform());
        }
        return null;
    }

    public void setDevicePlatformType(final DevicePlatformType devicePlatform) {
        if (devicePlatform != null) {
            this.setDevicePlatform(devicePlatform.getId());
        }
    }

    public String getNomeAtendente() {
        return nomeAtendente;
    }

    public void setNomeAtendente(final String nomeAtendente) {
        this.nomeAtendente = nomeAtendente;
    }

}
