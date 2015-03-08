package br.com.centralit.citcorpore.regras;

import java.util.HashMap;

import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.UtilDatas;

public class RegraProgramacaoAtividade_M extends RegraProgramacaoAtividade {
    
    public java.util.Date calculaProximaExecucao(ProgramacaoAtividadeDTO programacaoAtividadeDto, java.util.Date dataRef) throws Exception {
        if (programacaoAtividadeDto.getDataFim() != null && programacaoAtividadeDto.getDataFim().compareTo(dataRef) < 0)
            return null;
        
        java.util.Date proximaExecucao = null;        
        java.util.Date dataExecucao = programacaoAtividadeDto.getProximaExecucao(); 
        
        java.util.Date dataCalc = dataRef;
        if (dataCalc.compareTo(programacaoAtividadeDto.getDataInicio()) < 0)
            dataCalc = programacaoAtividadeDto.getDataInicio();
       
        if (dataExecucao == null || dataExecucao.compareTo(dataCalc) < 0) {
            String[] meses = new String[] {"", programacaoAtividadeDto.getJan(), programacaoAtividadeDto.getFev(), programacaoAtividadeDto.getMar()
                                             , programacaoAtividadeDto.getAbr(), programacaoAtividadeDto.getMai(), programacaoAtividadeDto.getJun()
                                             , programacaoAtividadeDto.getJul(), programacaoAtividadeDto.getAgo(), programacaoAtividadeDto.getSet()
                                             , programacaoAtividadeDto.getOut(), programacaoAtividadeDto.getNov(), programacaoAtividadeDto.getDez()};

            int dia = UtilDatas.getDay(dataCalc);
            int mes = UtilDatas.getMonth(dataCalc);
            int ano = UtilDatas.getYear(dataCalc);

            boolean bEncontrou = true;
            int diaExec = 0;
            int mesExec = mes;
            int anoExec = ano;
            switch (programacaoAtividadeDto.getPeriodicidadeMensal().intValue()) {
                case 1:
                    if (programacaoAtividadeDto.getDia().intValue() != 99)
                        diaExec = programacaoAtividadeDto.getDia().intValue();
                    else
                        diaExec = UtilDatas.getUltimoDiaMes(Util.getData(1, mesExec, anoExec));
                    if (!meses[mes].equals("S")) {
                        bEncontrou = false;
                        for (int i = mes; i < meses.length; i++) {
                            if (meses[i].equals("S")) {
                                mesExec = i;
                                bEncontrou = true;
                                break;
                            }
                        }
                        if (!bEncontrou) {
                            ano ++;
                            for (int i = 1; i < mes; i++) {
                                if (meses[i].equals("S")) {
                                    mesExec = i;
                                    bEncontrou = true;
                                    break;
                                }
                            }
                        }
                    }else if (dia > diaExec) {
                        bEncontrou = false;
                        for (int i = mes+1; i < meses.length; i++) {
                            if (meses[i].equals("S")) {
                                mesExec = i;
                                bEncontrou = true;
                                break;
                            }
                        }
                    }
                    if (bEncontrou && diaExec <= UtilDatas.getUltimoDiaMes(Util.getData(1, mesExec, anoExec)))
                        proximaExecucao = Util.getData(diaExec,mesExec,anoExec);
                    break;
                case 2:
                    if (!meses[mes].equals("S")) {
                        bEncontrou = false;
                        for (int i = mes; i < meses.length; i++) {
                            if (meses[i].equals("S")) {
                                mesExec = i;
                                bEncontrou = true;
                                break;
                            }
                        }
                        if (!bEncontrou) {
                            ano ++;
                            for (int i = 1; i < mes; i++) {
                                if (meses[i].equals("S")) {
                                    mesExec = i;
                                    bEncontrou = true;
                                    break;
                                }
                            }
                        }
                    }else{
                        Integer[] diasUteis = getDiasUteis(mesExec, anoExec);
                        diaExec = diasUteis[programacaoAtividadeDto.getDiaUtil()];
                        if (dia > diaExec) {
                            bEncontrou = false;
                            for (int i = mes+1; i < meses.length; i++) {
                                if (meses[i].equals("S")) {
                                    mesExec = i;
                                    bEncontrou = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (bEncontrou) { 
                        Integer[] diasUteis = getDiasUteis(mesExec, anoExec);
                        diaExec = diasUteis[programacaoAtividadeDto.getDiaUtil()];
                        proximaExecucao = Util.getData(diaExec,mesExec,anoExec);
                    }
                    break;
                case 3:
                    if (!meses[mes].equals("S")) {
                        bEncontrou = false;
                        for (int i = mes; i < meses.length; i++) {
                            if (meses[i].equals("S")) {
                                mesExec = i;
                                bEncontrou = true;
                                break;
                            }
                        }
                        if (!bEncontrou) {
                            ano ++;
                            for (int i = 1; i < mes; i++) {
                                if (meses[i].equals("S")) {
                                    mesExec = i;
                                    bEncontrou = true;
                                    break;
                                }
                            }
                        }
                    }else{
                        HashMap<Integer,Integer[]> mapSemanas = getSemanas(mesExec, anoExec);
                        Integer[] dias = mapSemanas.get(programacaoAtividadeDto.getDiaSemana().intValue());
                        int semana = programacaoAtividadeDto.getSeqDiaSemana().intValue();
                        if (semana == 5) {
                            while (dias[semana] == 0)
                                semana = semana - 1;
                        }
                        diaExec = dias[semana];
                        if (dia > diaExec) {
                            bEncontrou = false;
                            for (int i = mes+1; i < meses.length; i++) {
                                if (meses[i].equals("S")) {
                                    mesExec = i;
                                    bEncontrou = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (bEncontrou) { 
                        HashMap<Integer,Integer[]> mapSemanas = getSemanas(mesExec, anoExec);
                        Integer[] dias = mapSemanas.get(programacaoAtividadeDto.getDiaSemana().intValue());
                        int semana = programacaoAtividadeDto.getSeqDiaSemana().intValue();
                        if (semana == 5) {
                            while (dias[semana] == 0)
                                semana = semana - 1;
                        }
                        diaExec = dias[semana];
                        if (diaExec > 0)
                            proximaExecucao = Util.getData(diaExec,mesExec,anoExec);
                    }
                    break;
            }
        }else
            proximaExecucao = programacaoAtividadeDto.getProximaExecucao();
        return proximaExecucao;
    }
    
    public void valida(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        if (programacaoAtividadeDto.getPeriodicidadeMensal() == null)
            throw new LogicException("Periodicidade da programação semanal não foi informada");
        switch (programacaoAtividadeDto.getPeriodicidadeMensal().intValue()) {
            case 1:
                if (programacaoAtividadeDto.getDia() == null)
                    throw new LogicException("Dia do mês não foi informado");
                break;
            case 2:
                if (programacaoAtividadeDto.getDiaUtil() == null)
                    throw new LogicException("Dia útil do mês não foi informado");
                break;
            case 3:
                if (programacaoAtividadeDto.getSeqDiaSemana() == null)
                    throw new LogicException("Sequência do dia da semana não foi informado");
                if (programacaoAtividadeDto.getDiaSemana() == null)
                    throw new LogicException("Dia da semana não foi informado");
                break;
        } 
        if (programacaoAtividadeDto.getJan() == null)
            programacaoAtividadeDto.setJan("N");
        if (programacaoAtividadeDto.getFev() == null)
            programacaoAtividadeDto.setFev("N");                
        if (programacaoAtividadeDto.getMar() == null)
            programacaoAtividadeDto.setMar("N");
        if (programacaoAtividadeDto.getAbr() == null)
            programacaoAtividadeDto.setAbr("N");
        if (programacaoAtividadeDto.getMai() == null)
            programacaoAtividadeDto.setMai("N");
        if (programacaoAtividadeDto.getJun() == null)
            programacaoAtividadeDto.setJun("N");            
        if (programacaoAtividadeDto.getJul() == null)
            programacaoAtividadeDto.setJul("N");            
        if (programacaoAtividadeDto.getAgo() == null)
            programacaoAtividadeDto.setAgo("N");            
        if (programacaoAtividadeDto.getSet() == null)
            programacaoAtividadeDto.setSet("N");
        if (programacaoAtividadeDto.getOut() == null)
            programacaoAtividadeDto.setOut("N");
        if (programacaoAtividadeDto.getNov() == null)
            programacaoAtividadeDto.setNov("N");
        if (programacaoAtividadeDto.getDez() == null)
            programacaoAtividadeDto.setDez("N");            
        if (programacaoAtividadeDto.getJan().equals("N") && programacaoAtividadeDto.getFev().equals("N") && 
            programacaoAtividadeDto.getMar().equals("N") && programacaoAtividadeDto.getAbr().equals("N") &&
            programacaoAtividadeDto.getMai().equals("N") && programacaoAtividadeDto.getJun().equals("N") &&
            programacaoAtividadeDto.getJul().equals("N") && programacaoAtividadeDto.getAgo().equals("N") &&
            programacaoAtividadeDto.getSet().equals("N") && programacaoAtividadeDto.getOut().equals("N") &&
            programacaoAtividadeDto.getNov().equals("N") && programacaoAtividadeDto.getDez().equals("N"))
            throw new LogicException("Não foi informado nenhum mês");
    }    
    
    public String getDetalhamento(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        String descricao = "";      
        String[] seqDiaSemana = new String[] {"","primeira(o)","segunda(o)","terceira(o)","quarta(o)","última(o)"};
        String[] diaSemana = new String[] {"","domingo","segunda-feira","terça-feira","quarta-feira","quinta-feira","sexta-feira","sábado"};
        descricao = "Às "+programacaoAtividadeDto.getHoraInicio()+" ";
        switch (programacaoAtividadeDto.getPeriodicidadeMensal().intValue()) {
            case 1:
                descricao += " no dia "+programacaoAtividadeDto.getDia();
                break;
            case 2:
                if (programacaoAtividadeDto.getDiaUtil().intValue() != 99)
                    descricao += " no "+programacaoAtividadeDto.getDiaUtil()+"º dia útil";
                else
                    descricao += " no último dia útil";
                break;
            case 3:
                descricao += " no(a) "+seqDiaSemana[programacaoAtividadeDto.getSeqDiaSemana()]+" "+diaSemana[programacaoAtividadeDto.getDiaSemana()];
                break;
        } 
        String meses = "";
        if (programacaoAtividadeDto.getJan().equals("S"))
            meses += " janeiro";
        if (programacaoAtividadeDto.getFev().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " fevereiro";
        }
        if (programacaoAtividadeDto.getMar().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " março";
        }
        if (programacaoAtividadeDto.getAbr().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " abril";
        }
        if (programacaoAtividadeDto.getMai().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " maio";
        }
        if (programacaoAtividadeDto.getJun().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " junho";
        }
        if (programacaoAtividadeDto.getJul().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " julho";
        }
        if (programacaoAtividadeDto.getAgo().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " agosto";
        }
        if (programacaoAtividadeDto.getSet().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " setembro";
        }
        if (programacaoAtividadeDto.getOut().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " outubro";
        }
        if (programacaoAtividadeDto.getNov().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " novembro";
        }
        if (programacaoAtividadeDto.getDez().equals("S")) {
            if (meses.length() > 0)
                meses += ",";
            meses += " dezembro";
        }
        descricao += " de "+meses;
        return descricao;
    }
    
}
