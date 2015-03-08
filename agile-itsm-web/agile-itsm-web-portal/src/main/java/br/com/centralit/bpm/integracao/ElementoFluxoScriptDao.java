package br.com.centralit.bpm.integracao;

import br.com.centralit.bpm.dto.ElementoFluxoScriptDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;

public class ElementoFluxoScriptDao extends ElementoFluxoDao {

    @Override
    public Class<ElementoFluxoScriptDTO> getBean() {
        return ElementoFluxoScriptDTO.class;
    }

    @Override
    protected TipoElementoFluxo getTipoElemento() {
        return Enumerados.TipoElementoFluxo.Script;
    }

}
