package br.com.centralit.bpm.negocio;

import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.integracao.ItemTrabalhoFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.citframework.util.UtilDatas;

public class Evento extends ItemTrabalho {

	@Override
	public List<ItemTrabalho> resolve() throws Exception {
		registra();
		return null;
	}
	
	@Override
	public void executa(String loginUsuario, Map<String, Object> objetos) throws Exception {
		ItemTrabalhoFluxoDao itemTrabalhoFluxoDao = new ItemTrabalhoFluxoDao();
		setTransacaoDao(itemTrabalhoFluxoDao);
		
		itemTrabalhoDto.setDataHoraExecucao(UtilDatas.getDataHoraAtual());
        itemTrabalhoFluxoDao.update(itemTrabalhoDto);
	}
	
	@Override
	public void registra() throws Exception {
		if (existe())
			return;
		
		ItemTrabalhoFluxoDao itemTrabalhoDao = new ItemTrabalhoFluxoDao();
		setTransacaoDao(itemTrabalhoDao);
		
		itemTrabalhoDto = new ItemTrabalhoFluxoDTO();
		itemTrabalhoDto.setIdElemento(elementoFluxoDto.getIdElemento());
		itemTrabalhoDto.setIdInstancia(instanciaFluxo.getIdInstancia());
		itemTrabalhoDto.setDataHoraCriacao(UtilDatas.getDataHoraAtual());
		itemTrabalhoDto.setSituacao(Enumerados.SituacaoItemTrabalho.Criado.name());
		itemTrabalhoDto = (ItemTrabalhoFluxoDTO) itemTrabalhoDao.create(itemTrabalhoDto);
	}

	
	@Override
	public boolean executavel() {
		return true;
	}	
	
	@Override
	public boolean resolvido() {
	    return itemTrabalhoDto.getDataHoraExecucao() != null;
	}
}
