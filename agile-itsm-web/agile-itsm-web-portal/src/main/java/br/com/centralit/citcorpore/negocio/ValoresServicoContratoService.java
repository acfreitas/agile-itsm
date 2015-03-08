/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RelatorioValorServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ValoresServicoContratoDTO;
import br.com.citframework.service.CrudService;

@SuppressWarnings("rawtypes")
public interface ValoresServicoContratoService extends CrudService {

	public Collection findByIdServicoContrato(Integer parm) throws Exception;

	public void deleteByIdServicoContrato(Integer parm) throws Exception;

	public Collection obterValoresAtivosPorIdServicoContrato(Integer idServicoContrato) throws Exception;
	
	public boolean existeAtivos(Integer idServicoContrato) throws Exception;
	
	public Collection<RelatorioValorServicoContratoDTO> listaValoresServicoContrato(ValoresServicoContratoDTO parm) throws Exception;
	
}
