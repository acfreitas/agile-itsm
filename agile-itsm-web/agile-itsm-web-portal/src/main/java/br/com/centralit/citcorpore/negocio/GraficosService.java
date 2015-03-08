/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.GraficoPizzaDTO;
import br.com.citframework.service.CrudService;

/**
 * @author breno.guimaraes
 *
 */
public interface GraficosService extends CrudService {

    ArrayList<GraficoPizzaDTO> getRelatorioPorNomeCategoria();

    ArrayList<GraficoPizzaDTO> getRelatorioPorSituacao();

    ArrayList<GraficoPizzaDTO> getRelatorioPorGrupo();

}
