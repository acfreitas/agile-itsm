package br.com.centralit.citcorpore.metainfo.bean;

import java.util.Collection;
import java.util.Map;

import br.com.citframework.dto.IDto;

public class DinamicViewsDTO implements IDto {

    private static final long serialVersionUID = -843622611996605689L;

    private Integer idVisao;
    private Integer idVisaoEdit;
    private Integer idVisaoPesquisa;
    private Integer dinamicViewsIdVisao;
    private Integer dinamicViewsIdVisaoPesquisaSelecionada;
    private String dinamicViewsAcaoPesquisaSelecionada;
    private VisaoDTO visaoDto;
    private Collection colGrupos;

    private String dinamicViewsJson_data;
    private String jsonMatriz;
    private String jsonDataEdit;
    private String dinamicViewsDadosAdicionais;
    private String dinamicViewsJson_tempData;
    private String keyControl;

    private Map<String, Object> dinamicViewsMapDadosAdicional;

    private String modoExibicao;
    private Integer idFluxo;
    private Integer idTarefa;
    private String acaoFluxo;

    private String identificacao;

    private String id;
    private String saveInfo;

    private String msgRetorno = "";

    private boolean abortFuncaoPrincipal = false;

    public Collection getColGrupos() {
        return colGrupos;
    }

    public void setColGrupos(final Collection colGrupos) {
        this.colGrupos = colGrupos;
    }

    public Integer getIdVisao() {
        return idVisao;
    }

    public void setIdVisao(final Integer idVisao) {
        this.idVisao = idVisao;
    }

    public VisaoDTO getVisaoDto() {
        return visaoDto;
    }

    public void setVisaoDto(final VisaoDTO visaoDto) {
        this.visaoDto = visaoDto;
    }

    public Integer getDinamicViewsIdVisao() {
        return dinamicViewsIdVisao;
    }

    public void setDinamicViewsIdVisao(final Integer dinamicViewsIdVisao) {
        this.dinamicViewsIdVisao = dinamicViewsIdVisao;
    }

    public Integer getIdVisaoPesquisa() {
        return idVisaoPesquisa;
    }

    public void setIdVisaoPesquisa(final Integer idVisaoPesquisa) {
        this.idVisaoPesquisa = idVisaoPesquisa;
    }

    public String getDinamicViewsJson_data() {
        return dinamicViewsJson_data;
    }

    public void setDinamicViewsJson_data(final String dinamicViewsJson_data) {
        this.dinamicViewsJson_data = dinamicViewsJson_data;
    }

    public Integer getDinamicViewsIdVisaoPesquisaSelecionada() {
        return dinamicViewsIdVisaoPesquisaSelecionada;
    }

    public void setDinamicViewsIdVisaoPesquisaSelecionada(final Integer dinamicViewsIdVisaoPesquisaSelecionada) {
        this.dinamicViewsIdVisaoPesquisaSelecionada = dinamicViewsIdVisaoPesquisaSelecionada;
    }

    public String getDinamicViewsAcaoPesquisaSelecionada() {
        return dinamicViewsAcaoPesquisaSelecionada;
    }

    public void setDinamicViewsAcaoPesquisaSelecionada(final String dinamicViewsAcaoPesquisaSelecionada) {
        this.dinamicViewsAcaoPesquisaSelecionada = dinamicViewsAcaoPesquisaSelecionada;
    }

    public String getDinamicViewsDadosAdicionais() {
        return dinamicViewsDadosAdicionais;
    }

    public void setDinamicViewsDadosAdicionais(final String dinamicViewsDadosAdicionais) {
        this.dinamicViewsDadosAdicionais = dinamicViewsDadosAdicionais;
    }

    public Map<String, Object> getDinamicViewsMapDadosAdicional() {
        return dinamicViewsMapDadosAdicional;
    }

    public void setDinamicViewsMapDadosAdicional(final Map<String, Object> dinamicViewsMapDadosAdicional) {
        this.dinamicViewsMapDadosAdicional = dinamicViewsMapDadosAdicional;
    }

    public Integer getIdVisaoEdit() {
        return idVisaoEdit;
    }

    public void setIdVisaoEdit(final Integer idVisaoEdit) {
        this.idVisaoEdit = idVisaoEdit;
    }

    public String getJsonDataEdit() {
        return jsonDataEdit;
    }

    public void setJsonDataEdit(final String jsonDataEdit) {
        this.jsonDataEdit = jsonDataEdit;
    }

    public Integer getIdFluxo() {
        return idFluxo;
    }

    public void setIdFluxo(final Integer idFluxo) {
        this.idFluxo = idFluxo;
    }

    public Integer getIdTarefa() {
        return idTarefa;
    }

    public String getModoExibicao() {
        return modoExibicao;
    }

    public void setModoExibicao(final String modoExibicao) {
        this.modoExibicao = modoExibicao;
    }

    public void setIdTarefa(final Integer idTarefa) {
        this.idTarefa = idTarefa;
    }

    public String getAcaoFluxo() {
        return acaoFluxo;
    }

    public void setAcaoFluxo(final String acaoFluxo) {
        this.acaoFluxo = acaoFluxo;
    }

    public String getIdentificacao() {
        return identificacao;
    }

    public void setIdentificacao(final String identificacao) {
        this.identificacao = identificacao;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getSaveInfo() {
        return saveInfo;
    }

    public void setSaveInfo(final String saveInfo) {
        this.saveInfo = saveInfo;
    }

    public boolean isAbortFuncaoPrincipal() {
        return abortFuncaoPrincipal;
    }

    public void setAbortFuncaoPrincipal(final boolean abortFuncaoPrincipal) {
        this.abortFuncaoPrincipal = abortFuncaoPrincipal;
    }

    public String getMsgRetorno() {
        return msgRetorno;
    }

    public void setMsgRetorno(final String msgRetorno) {
        this.msgRetorno = msgRetorno;
    }

    public String getJsonMatriz() {
        return jsonMatriz;
    }

    public void setJsonMatriz(final String jsonMatriz) {
        this.jsonMatriz = jsonMatriz;
    }

    public String getDinamicViewsJson_tempData() {
        return dinamicViewsJson_tempData;
    }

    public void setDinamicViewsJson_tempData(final String dinamicViewsJson_tempData) {
        this.dinamicViewsJson_tempData = dinamicViewsJson_tempData;
    }

    public String getKeyControl() {
        return keyControl;
    }

    public void setKeyControl(final String keyControl) {
        this.keyControl = keyControl;
    }

}
