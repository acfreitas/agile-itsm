package br.com.centralit.bpm.integracao;

import br.com.centralit.bpm.dto.ElementoFluxoEventoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;

public class ElementoFluxoEventoDao extends ElementoFluxoDao {

    @Override
    public Class<ElementoFluxoEventoDTO> getBean() {
        return ElementoFluxoEventoDTO.class;
    }

    @Override
    protected TipoElementoFluxo getTipoElemento() {
        return Enumerados.TipoElementoFluxo.Evento;
    }

}
