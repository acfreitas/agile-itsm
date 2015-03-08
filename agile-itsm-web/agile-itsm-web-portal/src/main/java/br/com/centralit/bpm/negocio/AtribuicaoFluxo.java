package br.com.centralit.bpm.negocio;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.GrupoBpmDTO;
import br.com.centralit.bpm.dto.UsuarioBpmDTO;
import br.com.centralit.bpm.integracao.AtribuicaoFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.TipoAtribuicao;
import br.com.citframework.util.UtilDatas;


public class AtribuicaoFluxo extends NegocioBpm {
	
	protected ItemTrabalho itemTrabalho;

	public AtribuicaoFluxo (ItemTrabalho itemTrabalho) {
		this.itemTrabalho = itemTrabalho;
		this.setTransacao(itemTrabalho.getInstanciaFluxo().getTransacao());
	}
	
	public AtribuicaoFluxoDTO registraAtribuicao(UsuarioBpmDTO usuario, GrupoBpmDTO grupo, TipoAtribuicao tipoAtribuicao) throws Exception {
		AtribuicaoFluxoDao atribuicaoFluxoDao = new AtribuicaoFluxoDao();
		setTransacaoDao(atribuicaoFluxoDao);
		AtribuicaoFluxoDTO atribuicaoFluxoDto = new AtribuicaoFluxoDTO();
		atribuicaoFluxoDto.setIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
		atribuicaoFluxoDto.setTipo(tipoAtribuicao.name());
		atribuicaoFluxoDto.setDataHora(UtilDatas.getDataHoraAtual());
		if (grupo != null)
			atribuicaoFluxoDto.setIdGrupo(grupo.getIdGrupo());
		if (usuario != null)
			atribuicaoFluxoDto.setIdUsuario(usuario.getIdUsuario());
		return (AtribuicaoFluxoDTO) atribuicaoFluxoDao.create(atribuicaoFluxoDto);		
	}

	public AtribuicaoFluxoDTO registraDelegacao(UsuarioBpmDTO responsavel, UsuarioBpmDTO usuario, GrupoBpmDTO grupo) throws Exception {
		AtribuicaoFluxoDao atribuicaoFluxoDao = new AtribuicaoFluxoDao();
		setTransacaoDao(atribuicaoFluxoDao);
		atribuicaoFluxoDao.deleteDelegacao(itemTrabalho.getIdItemTrabalho());
		
		new HistoricoItemTrabalho(itemTrabalho).registraDelegacao(responsavel, usuario, grupo);
		
		AtribuicaoFluxoDTO atribuicaoFluxoDto = new AtribuicaoFluxoDTO();
		atribuicaoFluxoDto.setIdItemTrabalho(itemTrabalho.getIdItemTrabalho());
		atribuicaoFluxoDto.setTipo(Enumerados.TipoAtribuicao.Delegacao.name());
		atribuicaoFluxoDto.setDataHora(UtilDatas.getDataHoraAtual());
		if (grupo != null)
			atribuicaoFluxoDto.setIdGrupo(grupo.getIdGrupo());
		if (usuario != null)
			atribuicaoFluxoDto.setIdUsuario(usuario.getIdUsuario());
		return (AtribuicaoFluxoDTO) atribuicaoFluxoDao.create(atribuicaoFluxoDto);		
	}
}
