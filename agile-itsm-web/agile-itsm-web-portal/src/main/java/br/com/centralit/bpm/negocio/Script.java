package br.com.centralit.bpm.negocio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.bpm.integracao.ItemTrabalhoFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.UtilScript;
import br.com.citframework.util.UtilDatas;

public class Script extends ItemTrabalho {

	@Override
	public List<ItemTrabalho> resolve() throws Exception {
		registra();
		executa(null,null);
		SequenciaFluxo sequenciaFluxo = new SequenciaFluxo(instanciaFluxo);
		return sequenciaFluxo.getDestinos(this);
	}

	@Override
	public void executa(String loginUsuario, Map<String, Object> objetos) throws Exception {
		if (resolvido())
			return;

		ItemTrabalhoFluxoDao itemTrabalhoFluxoDao = new ItemTrabalhoFluxoDao();
		setTransacaoDao(itemTrabalhoFluxoDao);

		if (objetos == null)
			objetos = new HashMap<>();

		this.instanciaFluxo.getExecucaoFluxo().mapObjetoNegocio(objetos);
		this.instanciaFluxo.getObjetos(objetos);

		adicionaObjeto("itemTrabalho", this, objetos);
		UtilScript.executaScript(elementoFluxoDto.getNome(), elementoFluxoDto.getScript(), objetos);

		itemTrabalhoDto.setSituacao(Enumerados.SituacaoItemTrabalho.Executado.name());
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

	public String getScript() {
		return elementoFluxoDto.getScript();
	}

	@Override
	public boolean executavel() {
		return true;
	}
}
