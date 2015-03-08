package br.com.centralit.citcorpore.regras;

import java.util.Calendar;

import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.UtilDatas;

public class RegraProgramacaoAtividade_S extends RegraProgramacaoAtividade {
    
    public java.util.Date calculaProximaExecucao(ProgramacaoAtividadeDTO programacaoAtividadeDto, java.util.Date dataRef) throws Exception {
        if (programacaoAtividadeDto.getDataFim() != null && programacaoAtividadeDto.getDataFim().compareTo(dataRef) < 0)
            return null;
        
        java.util.Date proximaExecucao = null;        
        java.util.Date dataExecucao = programacaoAtividadeDto.getProximaExecucao(); 
        
        if (dataExecucao == null)
            dataExecucao = programacaoAtividadeDto.getDataInicio();
       
        if (dataExecucao.compareTo(dataRef) <= 0) {
            String[] dias = new String[] {"", programacaoAtividadeDto.getDom(), programacaoAtividadeDto.getSeg(), programacaoAtividadeDto.getTer()
                                            , programacaoAtividadeDto.getQua(), programacaoAtividadeDto.getQui(), programacaoAtividadeDto.getSex(), programacaoAtividadeDto.getSab()};

            int primeiroDia = 0;
            int ultimoDia = 0;
            for (int i = 1; i < dias.length; i++) {
                if (dias[i].equals("S")) {
                    ultimoDia = i;
                    if (primeiroDia == 0)
                        primeiroDia = i;
                }
            }
            
            while (dataExecucao.compareTo(dataRef) < 0) {
                int diaSemana = Util.getDiaSemana(dataExecucao);
                if (diaSemana == ultimoDia) {
                    java.util.Date primeiraExecucaoSemana = dataExecucao;                
                    while (Util.getDiaSemana(primeiraExecucaoSemana) != primeiroDia) 
                        primeiraExecucaoSemana = UtilDatas.alteraData(primeiraExecucaoSemana, -1, Calendar.DAY_OF_MONTH);
                    dataExecucao = UtilDatas.alteraData(primeiraExecucaoSemana, programacaoAtividadeDto.getPeriodicidadeSemanal()*7, Calendar.DAY_OF_MONTH);
                }else
                    dataExecucao = UtilDatas.alteraData(dataExecucao, 1, Calendar.DAY_OF_MONTH);
                while (!dias[Util.getDiaSemana(dataExecucao)].equals("S")) 
                    dataExecucao = UtilDatas.alteraData(dataExecucao, 1, Calendar.DAY_OF_MONTH);
            }

            proximaExecucao = dataExecucao;
        }else
            proximaExecucao = programacaoAtividadeDto.getProximaExecucao();
        return proximaExecucao;
    }
    
    public void valida(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        if (programacaoAtividadeDto.getPeriodicidadeSemanal() == null)
            throw new LogicException("Periodicidade da programação semanal não foi informada");
        if (programacaoAtividadeDto.getSeg() == null)
            programacaoAtividadeDto.setSeg("N");
        if (programacaoAtividadeDto.getTer() == null)
            programacaoAtividadeDto.setTer("N");
        if (programacaoAtividadeDto.getQua() == null)
            programacaoAtividadeDto.setQua("N");
        if (programacaoAtividadeDto.getQui() == null)
            programacaoAtividadeDto.setQui("N");
        if (programacaoAtividadeDto.getSex() == null)
            programacaoAtividadeDto.setSex("N");
        if (programacaoAtividadeDto.getSab() == null)
            programacaoAtividadeDto.setSab("N");
        if (programacaoAtividadeDto.getDom() == null)
            programacaoAtividadeDto.setDom("N");
        if (programacaoAtividadeDto.getSeg().equals("N") && programacaoAtividadeDto.getTer().equals("N") && 
            programacaoAtividadeDto.getQua().equals("N") && programacaoAtividadeDto.getQui().equals("N") &&
            programacaoAtividadeDto.getSex().equals("N") && programacaoAtividadeDto.getSab().equals("N") && programacaoAtividadeDto.getDom().equals("N"))
            throw new LogicException("Não foi informado nenhum dia da semana");
        String[] dias = new String[] {"", programacaoAtividadeDto.getDom(), programacaoAtividadeDto.getSeg(), programacaoAtividadeDto.getTer()
                                        , programacaoAtividadeDto.getQua(), programacaoAtividadeDto.getQui(), programacaoAtividadeDto.getSex(), programacaoAtividadeDto.getSab()};
        int diaSemana = Util.getDiaSemana(programacaoAtividadeDto.getDataInicio());
        if (dias[diaSemana].equals("N"))
            throw new LogicException("Data de início não coincide com nenhum dia da semana informado");
    }    
    
    public String getDetalhamento(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        String descricao = "";      
        descricao = "Às "+programacaoAtividadeDto.getHoraInicio()+" todo(a)";
        String dias = "";
        if (programacaoAtividadeDto.getSeg().equals("S"))
            dias += " segunda";
        if (programacaoAtividadeDto.getTer().equals("S")) {
            if (dias.length() > 0)
                dias += ",";
            dias += " terça";
        }
        if (programacaoAtividadeDto.getQua().equals("S")) {
            if (dias.length() > 0)
                dias += ",";
            dias += " quarta";
        }
        if (programacaoAtividadeDto.getQui().equals("S")) {
            if (dias.length() > 0)
                dias += ",";
            dias += " quinta";
        }
        if (programacaoAtividadeDto.getSex().equals("S")) {
            if (dias.length() > 0)
                dias += ",";
            dias += " sexta";
        }
        if (programacaoAtividadeDto.getSab().equals("S")) {
            if (dias.length() > 0)
                dias += ",";
            dias += " sábado";
        }
        if (programacaoAtividadeDto.getDom().equals("S")) {
            if (dias.length() > 0)
                dias += ",";
            dias += " domingo";
        }
        descricao += dias;
        if (programacaoAtividadeDto.getPeriodicidadeSemanal().intValue() > 1)
            descricao += " a cada "+programacaoAtividadeDto.getPeriodicidadeSemanal().intValue()+" semanas";
        return descricao;
    }
    
}
