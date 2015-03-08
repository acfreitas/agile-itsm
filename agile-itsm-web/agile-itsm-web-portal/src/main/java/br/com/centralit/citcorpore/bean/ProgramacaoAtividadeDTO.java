package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ProgramacaoAtividadeDTO implements IDto {
	private Integer idProgramacaoAtividade;
	private Integer idAtividadePeriodica;
    private String tipoAgendamento;     
    private Date dataInicio;
    private Date dataFim;
    private Integer duracaoEstimada;
    private Integer periodicidadeDiaria;
    private Integer periodicidadeSemanal;
    private Integer periodicidadeMensal;
    private Integer dia;
    private Integer diaUtil;
    private Integer diaSemana;
    private Integer seqDiaSemana;
    private String seg;
    private String ter;
    private String qua;
    private String qui;
    private String sex;
    private String sab;
    private String dom;
    private String jan;
    private String fev;
    private String mar;
    private String abr;
    private String mai;
    private String jun;
    private String jul;
    private String ago;
    private String set;
    private String out;
    private String nov;
    private String dez;
    private String repeticao;
    private Integer repeticaoIntervalo;
    private String repeticaoTipoIntervalo;
    private String horaInicio;
    private String horaFim;
    
    private String tipoAgendamentoDescr;
    private String detalhamento;
    private String repeticaoDescr;
    private String horaInicioFmt;
    private String duracaoEstimadaDescr;
    
    private Integer sequencia;
    private Date proximaExecucao;
    private Integer idAtividadesOs;
    private String nomeAtividadeOs;
    
    private Collection colItensOSAtividade;
    
    public String getTipoAgendamentoDescr() {
        return tipoAgendamentoDescr;
    }
    
    public void setTipoAgendamentoDescr(String tipoAgendamentoDescr) {
        this.tipoAgendamentoDescr = tipoAgendamentoDescr;
    }
    public String getHoraInicioFmt() {
        return horaInicioFmt;
    }

    public void setHoraInicioFmt(String horaInicioFmt) {
        this.horaInicioFmt = horaInicioFmt;
    }

    public Integer getIdProgramacaoAtividade() {
        return idProgramacaoAtividade;
    }
    public void setIdProgramacaoAtividade(Integer idProgramacaoAtividade) {
        this.idProgramacaoAtividade = idProgramacaoAtividade;
    }
    public Integer getIdAtividadePeriodica() {
        return idAtividadePeriodica;
    }
    public void setIdAtividadePeriodica(Integer idAtividadePeriodica) {
        this.idAtividadePeriodica = idAtividadePeriodica;
    }
    public String getTipoAgendamento() {
        return tipoAgendamento;
    }
    public void setTipoAgendamento(String tipoAgendamento) {
        this.tipoAgendamento = tipoAgendamento;
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
    public Integer getDuracaoEstimada() {
        return duracaoEstimada;
    }
    public void setDuracaoEstimada(Integer duracaoEstimada) {
        this.duracaoEstimada = duracaoEstimada;
    }

    public Integer getPeriodicidadeDiaria() {
        return periodicidadeDiaria;
    }

    public void setPeriodicidadeDiaria(Integer periodicidadeDiaria) {
        this.periodicidadeDiaria = periodicidadeDiaria;
    }

    public Integer getPeriodicidadeSemanal() {
        return periodicidadeSemanal;
    }

    public void setPeriodicidadeSemanal(Integer periodicidadeSemanal) {
        this.periodicidadeSemanal = periodicidadeSemanal;
    }


    public Integer getPeriodicidadeMensal() {
        return periodicidadeMensal;
    }

    public void setPeriodicidadeMensal(Integer periodicidadeMensal) {
        this.periodicidadeMensal = periodicidadeMensal;
    }

    public String getSeg() {
        return seg;
    }
    public void setSeg(String seg) {
        this.seg = seg;
    }
    public String getTer() {
        return ter;
    }
    public void setTer(String ter) {
        this.ter = ter;
    }
    public String getQua() {
        return qua;
    }
    public void setQua(String qua) {
        this.qua = qua;
    }
    public String getQui() {
        return qui;
    }
    public void setQui(String qui) {
        this.qui = qui;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSab() {
        return sab;
    }
    public void setSab(String sab) {
        this.sab = sab;
    }
    public String getDom() {
        return dom;
    }
    public void setDom(String dom) {
        this.dom = dom;
    }
    public String getJan() {
        return jan;
    }
    public void setJan(String jan) {
        this.jan = jan;
    }
    public String getFev() {
        return fev;
    }
    public void setFev(String fev) {
        this.fev = fev;
    }
    public String getMar() {
        return mar;
    }
    public void setMar(String mar) {
        this.mar = mar;
    }
    public String getAbr() {
        return abr;
    }
    public void setAbr(String abr) {
        this.abr = abr;
    }
    public String getMai() {
        return mai;
    }
    public void setMai(String mai) {
        this.mai = mai;
    }
    public String getJun() {
        return jun;
    }
    public void setJun(String jun) {
        this.jun = jun;
    }
    public String getJul() {
        return jul;
    }
    public void setJul(String jul) {
        this.jul = jul;
    }
    public String getAgo() {
        return ago;
    }
    public void setAgo(String ago) {
        this.ago = ago;
    }
    public String getSet() {
        return set;
    }
    public void setSet(String set) {
        this.set = set;
    }
    public String getOut() {
        return out;
    }
    public void setOut(String out) {
        this.out = out;
    }
    public String getNov() {
        return nov;
    }
    public void setNov(String nov) {
        this.nov = nov;
    }
    public String getDez() {
        return dez;
    }
    public void setDez(String dez) {
        this.dez = dez;
    }
    public String getRepeticao() {
        return repeticao;
    }
    public void setRepeticao(String repeticao) {
        this.repeticao = repeticao;
    }
    public Integer getRepeticaoIntervalo() {
        return repeticaoIntervalo;
    }
    public void setRepeticaoIntervalo(Integer repeticaoIntervalo) {
        this.repeticaoIntervalo = repeticaoIntervalo;
    }
    public String getRepeticaoTipoIntervalo() {
        return repeticaoTipoIntervalo;
    }
    public void setRepeticaoTipoIntervalo(String repeticaoTipoIntervalo) {
        this.repeticaoTipoIntervalo = repeticaoTipoIntervalo;
    }
    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public String getHoraFim() {
        return horaFim;
    }
    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public Integer getSequencia() {
        return sequencia;
    }

    public void setSequencia(Integer sequencia) {
        this.sequencia = sequencia;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getDiaUtil() {
        return diaUtil;
    }

    public void setDiaUtil(Integer diaUtil) {
        this.diaUtil = diaUtil;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(Integer diaSemana) {
        this.diaSemana = diaSemana;
    }

    public Integer getSeqDiaSemana() {
        return seqDiaSemana;
    }

    public void setSeqDiaSemana(Integer seqDiaSemana) {
        this.seqDiaSemana = seqDiaSemana;
    }

    public String getDetalhamento() {
        return detalhamento;
    }

    public void setDetalhamento(String detalhamento) {
        this.detalhamento = detalhamento;
    }

    public String getRepeticaoDescr() {
        return repeticaoDescr;
    }

    public void setRepeticaoDescr(String repeticaoDescr) {
        this.repeticaoDescr = repeticaoDescr;
    }

    public String getDuracaoEstimadaDescr() {
        return duracaoEstimadaDescr;
    }

    public void setDuracaoEstimadaDescr(String duracaoEstimadaDescr) {
        this.duracaoEstimadaDescr = duracaoEstimadaDescr;
    }

    public Date getProximaExecucao() {
        return proximaExecucao;
    }

    public void setProximaExecucao(Date proximaExecucao) {
        this.proximaExecucao = proximaExecucao;
    }

	/**
	 * @return the idAtividadesOs
	 */
	public Integer getIdAtividadesOs() {
		return idAtividadesOs;
	}

	/**
	 * @param idAtividadesOs the idAtividadesOs to set
	 */
	public void setIdAtividadesOs(Integer idAtividadesOs) {
		this.idAtividadesOs = idAtividadesOs;
	}

	/**
	 * @return the nomeAtividadeOs
	 */
	public String getNomeAtividadeOs() {
		return nomeAtividadeOs;
	}

	/**
	 * @param nomeAtividadeOs the nomeAtividadeOs to set
	 */
	public void setNomeAtividadeOs(String nomeAtividadeOs) {
		this.nomeAtividadeOs = nomeAtividadeOs;
	}


}
