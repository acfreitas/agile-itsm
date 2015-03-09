package br.com.centralit.citcorpore.bean;

import java.util.List;

import br.com.centralit.citcorpore.rh.bean.CertificacaoDTO;
import br.com.centralit.citcorpore.rh.bean.CursoDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaDTO;

public abstract class RequisitoPerspectivaTecnicaAbstract implements RequisitoPerspectivaTecnicaInterface {

    private IdiomaDTO idiomaDto;
    private int idEscrita;
    private int idLeitura;
    private int idConversacao;
    private String experienciaAnterior;
    private List<CertificacaoDTO> listaCertificacoesDto;
    private List<CursoDTO> listaCursosDto;

    public IdiomaDTO getIdiomaDto() {
        return idiomaDto;
    }

    public void setIdiomaDto(IdiomaDTO idiomaDto) {
        this.idiomaDto = idiomaDto;
    }

    public int getIdEscrita() {
        return idEscrita;
    }

    public void setIdEscrita(int idEscrita) {
        this.idEscrita = idEscrita;
    }

    public int getIdLeitura() {
        return idLeitura;
    }

    public void setIdLeitura(int idLeitura) {
        this.idLeitura = idLeitura;
    }

    public int getIdConversacao() {
        return idConversacao;
    }

    public void setIdConversacao(int idConversacao) {
        this.idConversacao = idConversacao;
    }

    public String getExperienciaAnterior() {
        return experienciaAnterior;
    }

    public void setExperienciaAnterior(String experienciaAnterior) {
        this.experienciaAnterior = experienciaAnterior;
    }

    public List<CertificacaoDTO> getListaCertificacoesDto() {
        return listaCertificacoesDto;
    }

    public void setListaCertificacoesDto(List<CertificacaoDTO> listaCertificacoesDto) {
        this.listaCertificacoesDto = listaCertificacoesDto;
    }

    public List<CursoDTO> getListaCursosDto() {
        return listaCursosDto;
    }

    public void setListaCursosDto(List<CursoDTO> listaCursosDto) {
        this.listaCursosDto = listaCursosDto;
    }

}
