package br.com.centralit.citcorpore.regras;

import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;

public class RegraProgramacaoAtividade_U extends RegraProgramacaoAtividade {
    
    public java.util.Date calculaProximaExecucao(ProgramacaoAtividadeDTO programacaoAtividadeDto, java.util.Date dataRef) throws Exception {
        java.util.Date proximaExecucao = null;        
        if (programacaoAtividadeDto.getDataInicio().compareTo(dataRef) >= 0)
            proximaExecucao = programacaoAtividadeDto.getDataInicio();
        return proximaExecucao;
    }
    
    public void valida(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
    }    
    
    public String getDetalhamento(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        String descricao = "Às "+programacaoAtividadeDto.getHoraInicio();
        return descricao;
    }
    
}
