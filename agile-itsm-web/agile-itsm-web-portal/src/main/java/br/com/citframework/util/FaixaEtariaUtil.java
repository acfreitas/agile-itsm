package br.com.citframework.util;

import java.util.Collection;
import java.util.Iterator;

import br.com.citframework.dto.FaixaEtariaDTO;

public class FaixaEtariaUtil {
	public static Collection getFaixasEtariasFromString(String faixas){
		return UtilStrings.getFaixasEtariasFromString(faixas);
	}
	public static int getIndiceFromCollectionFaixaEtaria(Collection colFaixas, Integer idade, boolean igualInicio, boolean igualFim){
		int ret = -1;
		if (colFaixas != null){
			for(Iterator it = colFaixas.iterator(); it.hasNext();){
				ret++;
				FaixaEtariaDTO faixaDto = (FaixaEtariaDTO)it.next();
				
				boolean inicioOK = false;
				boolean fimOK = false;
				/* avalia o inicio */
				if (igualInicio){
					if (idade.intValue() >= faixaDto.getInicio().intValue()){
						inicioOK = true;
					}
				}else{
					if (idade.intValue() > faixaDto.getInicio().intValue()){
						inicioOK = true;
					}
				}
				/* avalia o fim */
				if (igualFim){
					if (idade.intValue() <= faixaDto.getFim().intValue()){
						fimOK = true;
					}
				}else{
					if (idade.intValue() < faixaDto.getFim().intValue()){
						fimOK = true;
					}
				}	
				if (inicioOK && fimOK){
					return ret;
				}
			}
			if (ret >= colFaixas.size()){
				ret = -1;
			}			
		}
		return ret;
	}
}
