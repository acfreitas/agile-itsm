package br.com.centralit.bpm.integracao;

import br.com.centralit.bpm.dto.ElementoFluxoFinalizacaoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoElementoFluxo;

public class ElementoFluxoFinalizacaoDao extends ElementoFluxoDao {

    @Override
    public Class<ElementoFluxoFinalizacaoDTO> getBean() {
        return ElementoFluxoFinalizacaoDTO.class;
    }

    @Override
    protected TipoElementoFluxo getTipoElemento() {
        return Enumerados.TipoElementoFluxo.Finalizacao;
    }

}
