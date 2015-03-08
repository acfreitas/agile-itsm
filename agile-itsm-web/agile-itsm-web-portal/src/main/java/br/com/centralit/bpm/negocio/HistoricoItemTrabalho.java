package br.com.centralit.bpm.negocio;

import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.HistoricoItemTrabalhoDTO;
import br.com.centralit.bpm.dto.UsuarioBpmDTO;
import br.com.centralit.bpm.integracao.HistoricoItemTrabalhoDao;
import br.com.centralit.bpm.util.Enumerados.AcaoItemTrabalho;
import br.com.citframework.util.UtilDatas;


public class HistoricoItemTrabalho extends NegocioBpm {
	
	protected ItemTrabalho itemTrabalho;

	public HistoricoItemTrabalho (ItemTrabalho itemTrabalho) {
		this.itemTrabalho = itemTrabalho;
		this.setTransacao(itemTrabalho.getInstanciaFluxo().getTransacao());
	}
	
	public HistoricoItemTrabalhoDTO registra(UsuarioBpmDTO responsavel, AcaoItemTrabalho acao) throws Exception {
		HistoricoItemTrabalhoDao historicoItemDao = new HistoricoItemTrabalhoDao();
		setTransacaoDao(historicoItemDao);

		HistoricoItemTrabalhoDTO historicoDto = new HistoricoItemTrabalhoDTO();
		historicoDto.setIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
		historicoDto.setIdResponsavel(responsavel.getIdUsuario());
		historicoDto.setDataHora(UtilDatas.getDataHoraAtual());
		historicoDto.setAcao(acao.name());
		return (HistoricoItemTrabalhoDTO) historicoItemDao.create(historicoDto);		
		
	}

	public HistoricoItemTrabalhoDTO registraDelegacao(UsuarioBpmDTO responsavel, UsuarioBpmDTO usuario, GrupoBpmDTO grupo) throws Exception {
		HistoricoItemTrabalhoDao historicoItemDao = new HistoricoItemTrabalhoDao();
		setTransacaoDao(historicoItemDao);

		HistoricoItemTrabalhoDTO historicoDto = new HistoricoItemTrabalhoDTO();
		historicoDto.setIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
		historicoDto.setIdResponsavel(responsavel.getIdUsuario());
		historicoDto.setDataHora(UtilDatas.getDataHoraAtual());
		historicoDto.setAcao(AcaoItemTrabalho.Delegar.name());
		if (grupo != null)
			historicoDto.setIdGrupo(grupo.getIdGrupo());
		if (usuario != null)
			historicoDto.setIdUsuario(usuario.getIdUsuario());
		return (HistoricoItemTrabalhoDTO) historicoItemDao.create(historicoDto);		
	}

}
