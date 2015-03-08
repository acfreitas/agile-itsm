package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.DeParaCatalogoServicosBIDTO;
import br.com.citframework.service.CrudService;

public interface DeParaCatalogoServicosBIService extends CrudService {

    DeParaCatalogoServicosBIDTO findByidServicoDe(Integer idServicoDe, Integer idConexaoBI);

    Collection<DeParaCatalogoServicosBIDTO> findByidServicoPara(Integer idServicoPara, Integer idConexaoBI);

    Collection<DeParaCatalogoServicosBIDTO> findByidConexao(Integer idConexaoBI);

}
