/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.citframework.service.CrudService;

/**
 * 
 * @author breno.guimaraes
 */
@Deprecated
public interface IncidentesRelacionadosService extends CrudService {
    ArrayList<SolicitacaoServicoDTO> listIncidentesRelacionados(int idSolicitacao);
}
