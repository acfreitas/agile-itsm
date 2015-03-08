package br.com.centralit.citcorpore.regras;

import java.util.Calendar;

import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.UtilDatas;

public class RegraProgramacaoAtividade_D extends RegraProgramacaoAtividade {
    
    public java.util.Date calculaProximaExecucao(ProgramacaoAtividadeDTO programacaoAtividadeDto, java.util.Date dataRef) throws Exception {
        java.util.Date proximaExecucao = null;        
        java.util.Date dataExecucao = programacaoAtividadeDto.getProximaExecucao();                
        if (dataExecucao == null)  
            dataExecucao = programacaoAtividadeDto.getDataInicio();
        if (dataExecucao.compareTo(dataRef) <= 0 && (programacaoAtividadeDto.getDataFim() == null || programacaoAtividadeDto.getDataFim().compareTo(dataRef) >= 0)) {
            while (dataExecucao.compareTo(dataRef) < 0) 
                dataExecucao = UtilDatas.alteraData(dataExecucao, programacaoAtividadeDto.getPeriodicidadeDiaria().intValue(), Calendar.DAY_OF_MONTH);
            proximaExecucao = dataExecucao;
        }
        return proximaExecucao;
    }
    
    public void valida(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        if (programacaoAtividadeDto.getPeriodicidadeDiaria() == null)
            throw new LogicException("Periodicidade da programação diária não foi informada");
    } 
    
    public String getDetalhamento(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        String descricao = "";      
        if (programacaoAtividadeDto.getPeriodicidadeDiaria().intValue() == 1)
            descricao = "Todo dia às "+programacaoAtividadeDto.getHoraInicio();
        else
            descricao = "Às "+programacaoAtividadeDto.getHoraInicio()+" a cada "+programacaoAtividadeDto.getPeriodicidadeDiaria().intValue()+" dias";
        return descricao;
    }
    
}
