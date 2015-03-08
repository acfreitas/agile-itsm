package br.com.centralit.citcorpore.bean;

import java.util.Collection;

import br.com.citframework.dto.IDto;

/**
 * Classe que encapsula os parâmetros para recuperação de tarefas do fluxo
 *
 * @author carlos.santos
 * @since 27.01.2015 - Operação Usain Bolt.
 */
public class ParamRecuperacaoTarefasDTO implements IDto {

    private static final long serialVersionUID = -3415074213123939278L;

    private String loginUsuario;
    private GerenciamentoServicosDTO gerenciamentoServicosDto;
    private Collection<ContratoDTO> contratosUsuario;

    private Integer idTarefa;
    private UsuarioDTO usuarioDto;

    private boolean somenteTotalizacao;

    public ParamRecuperacaoTarefasDTO(final String loginUsuario, final GerenciamentoServicosDTO gerenciamentoServicosDto, final Collection<ContratoDTO> contratosUsuario) {
        this.loginUsuario = loginUsuario;
        this.gerenciamentoServicosDto = gerenciamentoServicosDto;
        this.contratosUsuario = contratosUsuario;
    }

    public ParamRecuperacaoTarefasDTO(final String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public ParamRecuperacaoTarefasDTO(final String loginUsuario, final Collection<ContratoDTO> contratosUsuario) {
        this.loginUsuario = loginUsuario;
        this.contratosUsuario = contratosUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(final String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public GerenciamentoServicosDTO getGerenciamentoServicosDto() {
        return gerenciamentoServicosDto;
    }

    public void setGerenciamentoServicosDto(final GerenciamentoServicosDTO gerenciamentoServicosDto) {
        this.gerenciamentoServicosDto = gerenciamentoServicosDto;
    }

    public Collection<ContratoDTO> getContratosUsuario() {
        return contratosUsuario;
    }

    public void setContratosUsuario(final Collection<ContratoDTO> contratosUsuario) {
        this.contratosUsuario = contratosUsuario;
    }

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public void setIdTarefa(final Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public UsuarioDTO getUsuarioDto() {
        return usuarioDto;
    }

    public void setUsuarioDto(final UsuarioDTO usuarioDto) {
        this.usuarioDto = usuarioDto;
    }

    public boolean isSomenteTotalizacao() {
        return somenteTotalizacao;
    }

    public void setSomenteTotalizacao(final boolean somenteTotalizacao) {
        this.somenteTotalizacao = somenteTotalizacao;
    }

}
