package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudService;
public interface PerfilContratoService extends CrudService {
	public Collection findByIdContrato(Integer parm) throws Exception;
	public void deleteByIdContrato(Integer parm) throws Exception;
	public Collection getTotaisByIdTarefaLinhaBaseProjeto(Integer idTarefaLinhaBaseProjetoParm) throws Exception;
	public Collection getTotaisByIdLinhaBaseProjeto(Integer idLinhaBaseProjetoParm) throws Exception;
	public Collection getTotaisByIdMarcoPagamentoPrj(Integer idMarcoPagamentoPrjParm, Integer idLinhaBaseProjetoParm) throws Exception;
	public Collection getTotaisSEMIdMarcoPagamentoPrj(Integer idLinhaBaseProjetoParm) throws Exception;
}
