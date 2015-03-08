package br.com.centralit.citcorpore.ajaxForms;

import java.util.Comparator;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;

public class CompararBaseConhecimento implements Comparator<BaseConhecimentoDTO> {

	public int compare(BaseConhecimentoDTO o1, BaseConhecimentoDTO o2) {
		return (((Integer) o1.getContadorCliques()).compareTo((Integer) o2.getContadorCliques()) * -1);
	}

}

class CompararBaseConhecimentoGrauImportancia implements Comparator<BaseConhecimentoDTO> {

	public int compare(BaseConhecimentoDTO baseConhecimento1, BaseConhecimentoDTO baseConhecimento2) {
		
			return (((Integer) baseConhecimento1.getGrauImportancia()).compareTo((Integer) baseConhecimento2.getGrauImportancia()) * -1);
		
	}

}

class CompararBaseConhecimentoMedia implements Comparator<BaseConhecimentoDTO> {

	@Override
	public int compare(BaseConhecimentoDTO baseConhecimento1, BaseConhecimentoDTO baseConhecimento2) {

		return ((baseConhecimento1.getMedia()).compareTo(baseConhecimento2.getMedia()) * -1);
	}

}

class CompararBaseConhecimentoPorVersao implements Comparator<BaseConhecimentoDTO> {

	@Override
	public int compare(BaseConhecimentoDTO baseConhecimento1, BaseConhecimentoDTO baseConhecimento2) {

		return ((baseConhecimento1.getVersao()).compareTo(baseConhecimento2.getVersao()) * -1);
	}
}