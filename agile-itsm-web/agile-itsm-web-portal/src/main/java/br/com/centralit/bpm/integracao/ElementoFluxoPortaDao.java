package br.com.centralit.bpm.integracao;

import br.com.centralit.bpm.dto.ElementoFluxoPortaDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;

public class ElementoFluxoPortaDao extends ElementoFluxoDao {

    @Override
    public Class<ElementoFluxoPortaDTO> getBean() {
        return ElementoFluxoPortaDTO.class;
    }

    @Override
    protected TipoElementoFluxo getTipoElemento() {
        return Enumerados.TipoElementoFluxo.Porta;
    }

}
