package br.com.centralit.bpm.integracao;

import br.com.centralit.bpm.dto.ElementoFluxoEmailDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;

public class ElementoFluxoEmailDao extends ElementoFluxoDao {

    @Override
    public Class<ElementoFluxoEmailDTO> getBean() {
        return ElementoFluxoEmailDTO.class;
    }

    @Override
    protected TipoElementoFluxo getTipoElemento() {
        return Enumerados.TipoElementoFluxo.Email;
    }

}
