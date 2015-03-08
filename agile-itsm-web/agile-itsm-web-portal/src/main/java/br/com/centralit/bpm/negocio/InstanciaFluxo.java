package br.com.centralit.bpm.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.centralit.bpm.dto.AtribuicaoFluxoDTO;
import br.com.centralit.bpm.dto.ElementoFluxoInicioDTO;
import br.com.centralit.bpm.dto.EventoFluxoDTO;
import br.com.centralit.bpm.dto.InstanciaFluxoDTO;
import br.com.centralit.bpm.dto.ObjetoInstanciaFluxoDTO;
import br.com.centralit.bpm.integracao.AtribuicaoFluxoDao;
import br.com.centralit.bpm.integracao.EventoFluxoDao;
import br.com.centralit.bpm.integracao.InstanciaFluxoDao;
import br.com.centralit.bpm.integracao.ObjetoInstanciaFluxoDao;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.bpm.util.Enumerados.SituacaoInstanciaFluxo;
import br.com.centralit.bpm.util.UtilScript;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

public class InstanciaFluxo extends NegocioBpm{

	private InstanciaFluxoDTO instanciaFluxoDto;
	private HashMap<String, Object> mapObj;

	public InstanciaFluxoDTO getInstanciaFluxoDto() {
		return instanciaFluxoDto;
	}

	private ExecucaoFluxo execucaoFluxo;

	public InstanciaFluxo(ExecucaoFluxo execucaoFluxo, Map<String, Object> objetos) throws Exception {
		this.execucaoFluxo = execucaoFluxo;
		setTransacao(execucaoFluxo.getTransacao());
		criaInstancia(objetos);
	}

	private void criaInstancia(Map<String, Object> objetos) throws Exception {
		try {
			InstanciaFluxoDao instanciaFluxoDao = new InstanciaFluxoDao();
			setTransacaoDao(instanciaFluxoDao);

			instanciaFluxoDto = new InstanciaFluxoDTO();
			instanciaFluxoDto.setIdFluxo(execucaoFluxo.getIdFluxo());
			instanciaFluxoDto.setDataHoraCriacao(UtilDatas.getDataHoraAtual());
			instanciaFluxoDto.setSituacao(Enumerados.INSTANCIA_ABERTA);
			instanciaFluxoDto = (InstanciaFluxoDTO) instanciaFluxoDao.create(instanciaFluxoDto);

			atualizaObjetosInstancia(objetos);
			ElementoFluxoInicioDTO elementoInicioDto = execucaoFluxo.getElementoInicioDto();
			avanca(elementoInicioDto.getIdElemento());
		} catch (Exception e) {
			e.printStackTrace();
			throw new LogicException("Erro na criação da instância do fluxo. Verifique o desenho e a parametrização do fluxo.");
		}
	}

	public InstanciaFluxo(ExecucaoFluxo execucaoFluxo, Integer idInstanciaFluxo) throws Exception {
		this.execucaoFluxo = execucaoFluxo;
		setTransacao(execucaoFluxo.getTransacao());
		InstanciaFluxoDao instanciaFluxoDao = new InstanciaFluxoDao();
		setTransacaoDao(instanciaFluxoDao);
		instanciaFluxoDto = new InstanciaFluxoDTO();
		instanciaFluxoDto.setIdInstancia(idInstanciaFluxo);
		instanciaFluxoDto = (InstanciaFluxoDTO) instanciaFluxoDao.restore(instanciaFluxoDto);
	}

	private void avanca(Integer idElemento) throws Exception{
		ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalhoDeElemento(this, idElemento);
		avanca(itemTrabalho);
	}

	private void avanca(ItemTrabalho itemTrabalho) throws Exception{
		List<ItemTrabalho> itens = itemTrabalho.resolve();
		if (itens != null) {
			for (ItemTrabalho elementoDestino : itens)
				avanca(elementoDestino);
		}
	}

	public Map<String, Object> getObjetos() throws Exception{
		return getObjetos(null);
	}

	public Map<String, Object> getObjetos(Map<String, Object> mapObjetos) throws Exception{
		ObjetoInstanciaFluxoDao objetoInstanciaDao = new ObjetoInstanciaFluxoDao();
		setTransacaoDao(objetoInstanciaDao);
		if (mapObjetos == null)
			mapObjetos = new HashMap<>();

		adicionaObjeto("instanciafluxo",this, mapObjetos);
		adicionaObjeto("transacao",this.getTransacao(), mapObjetos);

		if (mapObj != null && mapObjetos != mapObj && !mapObj.isEmpty()) {
			for(String var: mapObj.keySet())
				adicionaObjeto(var,mapObj.get(var), mapObjetos);
		}
		Collection<ObjetoInstanciaFluxoDTO> colObjetos = objetoInstanciaDao.findByIdInstancia(getIdInstancia());
		if (colObjetos != null) {
			Gson gson = new Gson();
			for (ObjetoInstanciaFluxoDTO objetoInstanciaDto : colObjetos) {
				if (mapObjetos.get(objetoInstanciaDto.getNomeObjeto()) == null && objetoInstanciaDto.getNomeClasse() != null && objetoInstanciaDto.getValor() != null) {
				    try{
    					Object objeto = gson.fromJson(objetoInstanciaDto.getValor(), Class.forName(objetoInstanciaDto.getNomeClasse()));
    					adicionaObjeto(objetoInstanciaDto.getNomeObjeto(),objeto, mapObjetos);
				    }catch (Exception e) {
				    }
				}
			}
		}
		return mapObjetos;
	}

	public void encerra() throws Exception {
		if (instanciaFluxoDto.getSituacao().equalsIgnoreCase(Enumerados.INSTANCIA_ENCERRADA))
			return;

		AtribuicaoFluxoDao atribuicaoFluxoDao = new AtribuicaoFluxoDao();
		setTransacaoDao(atribuicaoFluxoDao);
		Collection<AtribuicaoFluxoDTO> colAtribuicao = atribuicaoFluxoDao.findByDisponiveisByIdInstancia(instanciaFluxoDto.getIdInstancia());
		if (colAtribuicao != null) {
			for (AtribuicaoFluxoDTO atribuicaoFluxoDto : colAtribuicao) {
				ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(this, atribuicaoFluxoDto.getIdItemTrabalho());
				itemTrabalho.cancela(null);
			}
		}

        EventoFluxoDao eventoFluxoDao = new EventoFluxoDao();
        setTransacaoDao(eventoFluxoDao);
        Collection<EventoFluxoDTO> colEventos = eventoFluxoDao.findDisponiveis(instanciaFluxoDto.getIdInstancia());
        if (colEventos != null) {
            for (EventoFluxoDTO eventoFluxoDto : colEventos) {
                ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(this, eventoFluxoDto.getIdItemTrabalho());
                itemTrabalho.encerra();
            }
        }

        InstanciaFluxoDao instanciaFluxoDao = new InstanciaFluxoDao();
		setTransacaoDao(instanciaFluxoDao);

		instanciaFluxoDto.setSituacao(Enumerados.INSTANCIA_ENCERRADA);
		instanciaFluxoDto.setDataHoraFinalizacao(UtilDatas.getDataHoraAtual());
		instanciaFluxoDao.update(instanciaFluxoDto);
	}

	public ExecucaoFluxo getExecucaoFluxo() {
		return this.execucaoFluxo;
	}

	public Integer getIdInstancia() {
		if (instanciaFluxoDto != null)
			return instanciaFluxoDto.getIdInstancia();
		else
			return null;
	}

	public Integer getIdFluxo() {
		if (instanciaFluxoDto != null)
			return instanciaFluxoDto.getIdFluxo();
		else
			return null;
	}

	public Timestamp getDataHoraCriacao() {
		return instanciaFluxoDto.getDataHoraCriacao();
	}

	public SituacaoInstanciaFluxo getSituacao() {
		return Enumerados.SituacaoInstanciaFluxo.valueOf(instanciaFluxoDto.getSituacao());
	}

	public Timestamp getDataHoraFinalizacao() {
		return instanciaFluxoDto.getDataHoraFinalizacao();
	}

	public void executaItemTrabalho(String loginUsuario, Integer idItemTrabalho, Map<String, Object> objetos) throws Exception {
		ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(this, idItemTrabalho);
		if (itemTrabalho.resolvido())
			throw new LogicException("O item de trabalho '"+itemTrabalho.getElementoFluxoDto().getDocumentacao()+"' já foi executado.");

		itemTrabalho.executa(loginUsuario, objetos);
		atualizaObjetosInstancia(objetos);
		if (itemTrabalho.finalizado()) {
			SequenciaFluxo sequenciaFluxo = new SequenciaFluxo(this);
			List<ItemTrabalho> itens = sequenciaFluxo.getDestinos(itemTrabalho);
			if (itens != null) {
				for (ItemTrabalho itemDestino : itens)
					avanca(itemDestino);
			}
		}
	}

	private boolean condicaoAtendida(String condicao) throws Exception {
        return new Condicao(this,condicao,"Condicao").executa();
    }

    public void executaEvento(Integer idItemTrabalho, Map<String, Object> objetos) throws Exception {
        Evento evento = (Evento) ItemTrabalho.getItemTrabalho(this, idItemTrabalho);
        if (!condicaoAtendida(evento.getElementoFluxoDto().getCondicaoDisparo()))
            return;

        evento.executa(null, objetos);

        SequenciaFluxo sequenciaFluxo = new SequenciaFluxo(this);
        List<ItemTrabalho> itens = sequenciaFluxo.getDestinos(evento);
        if (itens != null) {
            for (ItemTrabalho itemDestino : itens)
                avanca(itemDestino);
        }
    }

	public void iniciaItemTrabalho(String loginUsuario, Integer idItemTrabalho, Map<String, Object> objetos) throws Exception {
		ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(this, idItemTrabalho);
		itemTrabalho.inicia(loginUsuario, objetos);
		atualizaObjetosInstancia(objetos);
	}

	public void delegaItemTrabalho(String loginUsuario, Integer idItemTrabalho, String usuarioDestino, String grupoDestino) throws Exception {
		ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(this, idItemTrabalho);
		itemTrabalho.delega(loginUsuario, usuarioDestino, grupoDestino);
	}

	private void atualizaObjetosInstancia(Map<String, Object> objetos) throws Exception {
		if (execucaoFluxo.getVariaveis() != null) {
			ObjetoInstanciaFluxoDao objetoInstanciaFluxoDao = new ObjetoInstanciaFluxoDao();
			setTransacaoDao(objetoInstanciaFluxoDao);
			List colObjetos = new ArrayList<>();

			Gson gson = new Gson();
			Map<String, Object> params = UtilScript.getParams(objetos);
			String[] variaveis = execucaoFluxo.getVariaveis();
			for (int i = 0; i < variaveis.length; i++) {
				Object obj = params.get(variaveis[i]);
				if (obj == null)
					continue;
				ObjetoInstanciaFluxoDTO objetoInstanciaDto = null;
				if (ObjetoInstanciaFluxoDTO.class.isInstance(obj)) {
					objetoInstanciaDto = (ObjetoInstanciaFluxoDTO) obj;
				}else{
					objetoInstanciaDto = new ObjetoInstanciaFluxoDTO();
					objetoInstanciaDto.setNomeObjeto(variaveis[i]);
					objetoInstanciaDto.setNomeClasse(obj.getClass().getName());
					objetoInstanciaDto.setCampoChave("N");
					objetoInstanciaDto.setObjetoPrincipal("S");
					try{
					    objetoInstanciaDto.setValor(gson.toJson(obj));
					}catch (Exception e) {
                    }
				}
				objetoInstanciaFluxoDao.deleteByIdInstanciaAndNome(instanciaFluxoDto.getIdInstancia(), objetoInstanciaDto.getNomeObjeto());
				objetoInstanciaDto.setIdObjetoInstancia(null);
				objetoInstanciaDto.setIdInstancia(instanciaFluxoDto.getIdInstancia());
				objetoInstanciaDto.setTipoAssociacao(Enumerados.TIPO_ASSOCIACAO_INSTANCIA);
				objetoInstanciaDto = (ObjetoInstanciaFluxoDTO) objetoInstanciaFluxoDao.create(objetoInstanciaDto);
				colObjetos.add(objetoInstanciaDto);
			}

			instanciaFluxoDto.setColObjetos(colObjetos);
		}
		mapObj = new HashMap<>();
		for(String var: objetos.keySet()) {
			Object obj = objetos.get(var);
			mapObj.put(var, obj);
		}
	}

	public void teste() {
		System.out.println("############# IDINSTANCIA: "+getIdInstancia());
	}

    public HashMap<String, Object> getMapObj() {
        if (mapObj == null)
            mapObj = new HashMap<>();
        return mapObj;
    }

    public void setMapObj(HashMap<String, Object> mapObj) {
        this.mapObj = mapObj;
    }

    public void cancelaItemTrabalho(String loginUsuario, Integer idItemTrabalho) throws Exception {
        ItemTrabalho itemTrabalho = ItemTrabalho.getItemTrabalho(this, idItemTrabalho);
        itemTrabalho.cancela(loginUsuario);
    }

    public void verificaSLA(ItemTrabalho itemTrabalho) throws Exception {
        execucaoFluxo.verificaSLA(itemTrabalho);
    }

}
