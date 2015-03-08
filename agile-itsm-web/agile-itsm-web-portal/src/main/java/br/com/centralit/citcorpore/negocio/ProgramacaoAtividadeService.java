package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.EventoDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.citframework.service.CrudService;
public interface ProgramacaoAtividadeService extends CrudService {
	public Collection findByIdAtividadePeriodica(Integer parm) throws Exception;
	public Collection findByIdAtividadePeriodicaOrderDataHora(Integer parm) throws Exception;
	public void deleteByIdAtividadePeriodica(Integer parm) throws Exception;
	
	public void validaProgramacao(ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception;
	public Collection<EventoDTO> findEventosAgenda(AtividadePeriodicaDTO atividadePeriodicaDto, java.util.Date dataRef, Integer qtdeDias) throws Exception;
}
