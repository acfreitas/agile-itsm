package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.AgendaAtvPeriodicasDTO;
import br.com.citframework.service.CrudService;
@SuppressWarnings("rawtypes")
public interface AtividadePeriodicaService extends CrudService {
	public Collection findByIdContrato(Integer parm) throws Exception;
	public void deleteByIdContrato(Integer parm) throws Exception;
	public Collection findByIdProcedimentoTecnico(Integer parm) throws Exception;
	public void deleteByIdProcedimentoTecnico(Integer parm) throws Exception;
	public Collection findByIdGrupoAtvPeriodica(AgendaAtvPeriodicasDTO agendaAtvPeriodicasDTO) throws Exception;
	public void deleteByIdGrupoAtvPeriodica(Integer parm) throws Exception;
	
	public Collection findByTituloAtividade(Integer parm) throws Exception;
	public void deleteByTituloAtividade(Integer parm) throws Exception;
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception;
	public boolean existeDuplicacao(String tituloAtividade, Integer idAtividade) throws Exception;
	
	/**
	 * Retorna uma lista de atividades periodicas de acordo com a requisição mudança passado
	 * @param parm
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception ;
	
	/* adicionado por david.junior
	 * para o gerenciamentoProblemas
	*/
	public Collection findByIdProblema(Integer parm) throws Exception ;
	
	public Collection findByIdRequisicaoLiberacao(Integer parm) throws Exception ;	
}
