/**
 *
 */
package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

/**
 * @author flavio.santana
 *         Bean dos objetos de instalação do sistema
 */
public class StartDTO implements IDto {

    private static final long serialVersionUID = 7717115402594824882L;

    private String current;
    private Integer idStart;
    private Integer passoInstalacao;
    private Date dataInicio;
    private Date dataFim;
    private String locale;

    private String versao;

    private Collection<String> conteudo;

    /*
     * Atributos de Conexão
     */
    private String driverConexao;

    /*
     * Atributos de Empresa
     */
    private Integer idEmpresa;
    private String nomeEmpresa;
    private String detalhamento;
    /*
     * Atributos LDAP
     */
    private String metodoAutenticacao;
    private String idAtributoLdap;
    private String atributoLdap;
    private String valorAtributoLdap;
    private Collection<LdapDTO> listLdapDTO;
    /*
     * Atributos Sistema
     */
    private String urlSistema;
    private String diretorioUpload;
    /*
     * Atributos de E-mail
     */
    private Collection<EmailDTO> listEmailDTO;
    /*
     * Atributos de Log
     */
    private Collection<LogDTO> listLogDTO;
    /*
     * Atributos de GED
     */
    private Collection<GedDTO> listGedDTO;
    /*
     * Atributos de SMTP
     */
    private Collection<SmtpDTO> listSmtpDTO;
    /*
     * Atributos de IC
     */
    private Collection<ICDTO> listIcDTO;
    /*
     * Atributos de Base conhecimento
     */
    private Collection<BaseDTO> listBaseDTO;
    /*
     * Atributos de Base conhecimento
     */
    private Collection<GeralDTO> listGeralDTO;
    /*
     * paginaSolicitacao
     */
    private String paginaSolicitacao;
    private String diretorio;
    private String campoDiretorio;

    public Integer getIdStart() {
        return idStart;
    }

    public Integer getPassoInstalacao() {
        return passoInstalacao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setIdStart(Integer idStart) {
        this.idStart = idStart;
    }

    public void setPassoInstalacao(Integer passoInstalacao) {
        this.passoInstalacao = passoInstalacao;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public String getDriverConexao() {
        return driverConexao;
    }

    public void setDriverConexao(String driverConexao) {
        this.driverConexao = driverConexao;
    }

    /**
     * @return a sessão corrente
     */
    public String getCurrent() {
        return current;
    }

    /**
     * @param define
     *            a sessão corrente
     */
    public void setCurrent(String current) {
        this.current = current;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public String getDetalhamento() {
        return detalhamento;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public void setDetalhamento(String detalheEmpresa) {
        this.detalhamento = detalheEmpresa;
    }

    public String getMetodoAutenticacao() {
        return metodoAutenticacao;
    }

    public void setMetodoAutenticacao(String metodoAutenticacao) {
        this.metodoAutenticacao = metodoAutenticacao;
    }

    public String getIdAtributoLdap() {
        return idAtributoLdap;
    }

    public String getAtributoLdap() {
        return atributoLdap;
    }

    public String getValorAtributoLdap() {
        return valorAtributoLdap;
    }

    public Collection<LdapDTO> getListLdapDTO() {
        return listLdapDTO;
    }

    public void setIdAtributoLdap(String idAtributoLdap) {
        this.idAtributoLdap = idAtributoLdap;
    }

    public void setAtributoLdap(String atributoLdap) {
        this.atributoLdap = atributoLdap;
    }

    public void setValorAtributoLdap(String valorAtributoLdap) {
        this.valorAtributoLdap = valorAtributoLdap;
    }

    public void setListLdapDTO(Collection<LdapDTO> listLdapDTO) {
        this.listLdapDTO = listLdapDTO;
    }

    public String getUrlSistema() {
        return urlSistema;
    }

    public String getDiretorioUpload() {
        return diretorioUpload;
    }

    public void setUrlSistema(String urlSistema) {
        this.urlSistema = urlSistema;
    }

    public void setDiretorioUpload(String diretorioUpload) {
        this.diretorioUpload = diretorioUpload;
    }

    public Collection<EmailDTO> getListEmailDTO() {
        return listEmailDTO;
    }

    public void setListEmailDTO(Collection<EmailDTO> listEmailDTO) {
        this.listEmailDTO = listEmailDTO;
    }

    public Collection<LogDTO> getListLogDTO() {
        return listLogDTO;
    }

    public void setListLogDTO(Collection<LogDTO> listLogDTO) {
        this.listLogDTO = listLogDTO;
    }

    public Collection<GedDTO> getListGedDTO() {
        return listGedDTO;
    }

    public void setListGedDTO(Collection<GedDTO> listGedDTO) {
        this.listGedDTO = listGedDTO;
    }

    public Collection<SmtpDTO> getListSmtpDTO() {
        return listSmtpDTO;
    }

    public void setListSmtpDTO(Collection<SmtpDTO> listSmtpDTO) {
        this.listSmtpDTO = listSmtpDTO;
    }

    public Collection<ICDTO> getListIcDTO() {
        return listIcDTO;
    }

    public void setListIcDTO(Collection<ICDTO> listIcDTO) {
        this.listIcDTO = listIcDTO;
    }

    public Collection<BaseDTO> getListBaseDTO() {
        return listBaseDTO;
    }

    public void setListBaseDTO(Collection<BaseDTO> listBaseDTO) {
        this.listBaseDTO = listBaseDTO;
    }

    public Collection<GeralDTO> getListGeralDTO() {
        return listGeralDTO;
    }

    public void setListGeralDTO(Collection<GeralDTO> listGeralDTO) {
        this.listGeralDTO = listGeralDTO;
    }

    public String getPaginaSolicitacao() {
        return paginaSolicitacao;
    }

    public void setPaginaSolicitacao(String paginaSolicitacao) {
        this.paginaSolicitacao = paginaSolicitacao;
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public String getCampoDiretorio() {
        return campoDiretorio;
    }

    public void setCampoDiretorio(String campoDiretorio) {
        this.campoDiretorio = campoDiretorio;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getVersao() {
        return versao;
    }

    public void setVersao(String versao) {
        this.versao = versao;
    }

    public Collection<String> getConteudo() {
        return conteudo;
    }

    public void setConteudo(Collection<String> conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((idAtributoLdap == null) ? 0 : idAtributoLdap.hashCode());
        result = prime * result + ((idEmpresa == null) ? 0 : idEmpresa.hashCode());
        result = prime * result + ((idStart == null) ? 0 : idStart.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null) {
            return super.equals(obj);
        }
        return false;
    }

}
