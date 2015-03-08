package br.com.centralit.bpm.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.bpm.dto.ElementoFluxoDTO;
import br.com.centralit.bpm.dto.PropriedadeElementoDTO;
import br.com.citframework.util.Reflexao;

public class Design {

    private List<PropriedadeElementoDTO> propriedades;
    private List<ElementoFluxoDTO> elementos;

    public List<PropriedadeElementoDTO> getPropriedades() {
        return propriedades;
    }

    public void setPropriedades(final List<PropriedadeElementoDTO> propriedades) {
        this.propriedades = propriedades;
    }

    public PropriedadeElementoDTO getPropriedade(final String id) {
        if (propriedades == null) {
            return null;
        }

        PropriedadeElementoDTO result = null;
        for (final PropriedadeElementoDTO propriedadeDto : propriedades) {
            if (propriedadeDto.getId().equalsIgnoreCase(id)) {
                result = propriedadeDto;
                break;
            }
        }
        return result;
    }

    public ElementoFluxoDTO getElemento(final String tipo) {
        if (elementos == null) {
            return null;
        }

        ElementoFluxoDTO result = null;
        for (final ElementoFluxoDTO elementoDto : elementos) {
            if (elementoDto.getTipoElemento().equalsIgnoreCase(tipo)) {
                result = elementoDto;
                break;
            }
        }
        return result;
    }

    public List<ElementoFluxoDTO> getElementos() {
        return elementos;
    }

    public void setElementos(final List<ElementoFluxoDTO> elementos) {
        this.elementos = elementos;
    }

    public void configuraElementos() {
        if (propriedades == null || elementos == null) {
            return;
        }

        final HashMap<String, PropriedadeElementoDTO> mapProp = new HashMap<>();
        for (final PropriedadeElementoDTO propriedade : propriedades) {
            if (propriedade != null) {
                if (propriedade.getValorDefault() == null) {
                    propriedade.setValorDefault("");
                }
                mapProp.put(propriedade.getId(), propriedade);
            }
        }

        for (final ElementoFluxoDTO elemento : elementos) {
            if (elemento != null && elemento.getLstPropriedades() != null) {
                final List<PropriedadeElementoDTO> lst = new ArrayList<>();
                for (int i = 0; i < elemento.getLstPropriedades().length; i++) {
                    if (mapProp.get(elemento.getLstPropriedades()[i]) != null) {
                        final PropriedadeElementoDTO propriedade = mapProp.get(elemento.getLstPropriedades()[i]);
                        lst.add(propriedade);
                        try {
                            Reflexao.setPropertyValue(elemento, elemento.getLstPropriedades()[i], propriedade.getValorDefault());
                        } catch (final Exception e) {}
                    }
                }
                elemento.setPropriedades(lst);
            }
        }
    }

}
