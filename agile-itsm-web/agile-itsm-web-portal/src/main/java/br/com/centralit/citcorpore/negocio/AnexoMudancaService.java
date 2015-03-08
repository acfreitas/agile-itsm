/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AnexoMudancaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.citframework.service.CrudService;

/**
 *
 * @author breno.guimaraes
 */
public interface AnexoMudancaService extends CrudService {

    void create(final Collection<UploadDTO> arquivosUpados, final Integer idMudanca) throws Exception;

    Collection<AnexoMudancaDTO> consultarAnexosMudanca(final Integer idMudanca) throws Exception;

}
