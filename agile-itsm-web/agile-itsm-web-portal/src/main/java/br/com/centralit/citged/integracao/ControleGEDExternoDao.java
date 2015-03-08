package br.com.centralit.citged.integracao;

import java.util.HashMap;

import br.com.citframework.dto.IDto;

public abstract class ControleGEDExternoDao {
	public abstract IDto create(IDto dto, HashMap infoAdicional) throws Exception;
	public abstract void update(IDto dto, HashMap infoAdicional) throws Exception;
}
