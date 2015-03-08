package br.com.centralit.citcorpore.regras;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;

import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.integracao.FeriadoDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoAgendamento;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.UtilDatas;

public abstract class RegraProgramacaoAtividade implements Serializable {
    
    public static RegraProgramacaoAtividade getRegraFromDto(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        return (RegraProgramacaoAtividade) Class.forName(RegraProgramacaoAtividade.class.getName()+"_"+programacaoAtividadeDto.getTipoAgendamento()).newInstance();
    }
    
    public static java.util.Date getDataProximaExecucao(ProgramacaoAtividadeDTO programacaoAtividadeDto, java.util.Date dataRef) throws Exception {
        return getRegraFromDto(programacaoAtividadeDto).calculaProximaExecucao(programacaoAtividadeDto, dataRef);
    }
    

    public static Integer[] getDiasUteis(int mes, int ano) throws Exception {
        FeriadoDao feriadoDao = new FeriadoDao();
        Integer[] diasUteis = new Integer[100];
        for (int i = 0; i < diasUteis.length; i++) 
            diasUteis[i] = new Integer(0);
        int m = 1;
        java.util.Date dataRef = Util.getData(1, mes, ano);
        while (UtilDatas.getMonth(dataRef) == mes) {
            int diaSemana = Util.getDiaSemana(dataRef); 
            if (diaSemana != 1 && diaSemana != 7 && !feriadoDao.isFeriado(new java.sql.Date(dataRef.getTime()), null, null)) {
                diasUteis[m] = Util.getDay(dataRef);
                m++;
            }
            dataRef = UtilDatas.alteraData(dataRef, 1, Calendar.DAY_OF_MONTH);
        }
        diasUteis[99] = diasUteis[m-1];
        return diasUteis;
    }    
    
    public static HashMap getSemanas(int mes, int ano) throws Exception {
        Integer[] contSemanas = new Integer[] {0,0,0,0,0,0,0,0};
        HashMap<Integer,Integer[]> mapSemanas = new HashMap();
        mapSemanas.put(new Integer(1), new Integer[]{0,0,0,0,0,0,0});
        mapSemanas.put(new Integer(2), new Integer[]{0,0,0,0,0,0,0});
        mapSemanas.put(new Integer(3), new Integer[]{0,0,0,0,0,0,0});
        mapSemanas.put(new Integer(4), new Integer[]{0,0,0,0,0,0,0});
        mapSemanas.put(new Integer(5), new Integer[]{0,0,0,0,0,0,0});
        mapSemanas.put(new Integer(6), new Integer[]{0,0,0,0,0,0,0});
        mapSemanas.put(new Integer(7), new Integer[]{0,0,0,0,0,0,0});
        
        java.util.Date dataRef = Util.getData(1, mes, ano);
        while (UtilDatas.getMonth(dataRef) == mes) {
            int diaSemana = Util.getDiaSemana(dataRef);
            contSemanas[diaSemana] = contSemanas[diaSemana].intValue() + 1;
            int semana = contSemanas[diaSemana].intValue();
            Integer[] dias = mapSemanas.get(diaSemana);
            dias[semana] = Util.getDay(dataRef);
            dataRef = UtilDatas.alteraData(dataRef, 1, Calendar.DAY_OF_MONTH);
        }
        return mapSemanas;
    } 
    
    public static void validaProgramacao(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        if (programacaoAtividadeDto.getTipoAgendamento() == null) 
            throw new LogicException("Tipo de agendamento não foi informado");
        if (programacaoAtividadeDto.getDataInicio() == null) 
            throw new LogicException("Data de início não foi informada");
        if (programacaoAtividadeDto.getHoraInicio() == null || programacaoAtividadeDto.getHoraInicio().length() < 4) 
            throw new LogicException("Hora de início não foi informada");
        if (!Util.isHoraValida(programacaoAtividadeDto.getHoraInicio()))
            throw new LogicException("Hora de início inválida");
        if (programacaoAtividadeDto.getDuracaoEstimada() == null) 
            throw new LogicException("Duração estimada não foi informada");
        if (programacaoAtividadeDto.getDataFim() != null && programacaoAtividadeDto.getDataFim().compareTo(programacaoAtividadeDto.getDataInicio()) < 0)
            throw new LogicException("A data de término é maior que a data de início");
        if (programacaoAtividadeDto.getRepeticao() == null)
            programacaoAtividadeDto.setRepeticao("N");
        if (programacaoAtividadeDto.getRepeticao().equals("S")) {
            if (programacaoAtividadeDto.getRepeticaoIntervalo() == null)
                throw new LogicException("Intervalo de repetição não foi informado");
            if (programacaoAtividadeDto.getRepeticaoTipoIntervalo() == null)
                throw new LogicException("Tipo de intervalo da repetição não foi informado");
            if (programacaoAtividadeDto.getHoraFim() == null || programacaoAtividadeDto.getHoraFim().length() < 4)
                throw new LogicException("Hora de término da repetição não foi informada");
            if (!Util.isHoraValida(programacaoAtividadeDto.getHoraFim()))
                throw new LogicException("Hora de término da repetição inválida");
        }
        RegraProgramacaoAtividade regraImpl = getRegraFromDto(programacaoAtividadeDto);
        regraImpl.valida(programacaoAtividadeDto);
        setDescricao(programacaoAtividadeDto);
    }
    
    public static void setDescricao(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        RegraProgramacaoAtividade regraImpl = getRegraFromDto(programacaoAtividadeDto);
        programacaoAtividadeDto.setTipoAgendamentoDescr(TipoAgendamento.valueOf(programacaoAtividadeDto.getTipoAgendamento()).getDescricao());
        programacaoAtividadeDto.setDetalhamento(regraImpl.getDetalhamento(programacaoAtividadeDto));
        programacaoAtividadeDto.setRepeticaoDescr(regraImpl.getRepeticaoDescr(programacaoAtividadeDto));
        programacaoAtividadeDto.setDuracaoEstimadaDescr(programacaoAtividadeDto.getDuracaoEstimada().intValue()+" minuto(s)");
    }
    
    public String getRepeticaoDescr(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        String descricao = "";
        if (programacaoAtividadeDto.getRepeticao().equals("S")) {
            descricao = "A cada "+programacaoAtividadeDto.getRepeticaoIntervalo().intValue();
            if (programacaoAtividadeDto.getRepeticaoTipoIntervalo().equals("M"))
                descricao += " minuto(s)";
            else
                descricao += " hora(s)";
            descricao += " até "+programacaoAtividadeDto.getHoraFim();
        }
        return descricao;
    }
    
    public abstract java.util.Date calculaProximaExecucao(ProgramacaoAtividadeDTO programacaoAtividadeDto, java.util.Date dataRef) throws Exception;
    public abstract String getDetalhamento(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception;
    public abstract void valida(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception;

}
