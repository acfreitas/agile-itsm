package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface TarefaLinhaBaseProjetoService extends CrudService {
	public Collection findByIdLinhaBaseProjeto(Integer parm) throws Exception;
	public void deleteByIdLinhaBaseProjeto(Integer parm) throws Exception;
	public Collection findCarteiraByIdEmpregado(Integer idEmpregado) throws Exception;
	public Collection findByIdTarefaLinhaBaseProjetoMigr(Integer idTarefaLinhaBaseProjetoMigr) throws Exception;
	public Collection findByIdTarefaLinhaBaseProjetoPai(Integer idTarefaLinhaBaseProjetoPai) throws Exception;
}
