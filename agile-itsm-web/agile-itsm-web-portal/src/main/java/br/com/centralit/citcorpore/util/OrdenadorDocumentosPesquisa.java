package br.com.centralit.citcorpore.util;

import java.util.Comparator;

import br.com.centralit.citcorpore.bean.BaseConhecimentoPesquisaDTO;

public class OrdenadorDocumentosPesquisa implements Comparator<BaseConhecimentoPesquisaDTO> {

    public int compare(BaseConhecimentoPesquisaDTO c1, BaseConhecimentoPesquisaDTO c2) {
	int contador1 = ((BaseConhecimentoPesquisaDTO) c1).getContadorCliques();
	int contador2 = ((BaseConhecimentoPesquisaDTO) c2).getContadorCliques();

	return contador2 - contador1;
    }

}