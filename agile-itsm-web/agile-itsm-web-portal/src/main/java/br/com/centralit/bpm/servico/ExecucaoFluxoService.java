package br.com.centralit.bpm.servico;

import java.util.List;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.citframework.service.CrudService;

public interface ExecucaoFluxoService extends CrudService {

    List<TarefaFluxoDTO> recuperaTarefas(final String loginUsuario) throws Exception;

    TarefaFluxoDTO recuperaTarefa(final String loginUsuario, final Integer idTarefa) throws Exception;

    void delegaTarefa(final String loginUsuario, final Integer idTarefa, final String usuarioDestino, final String grupoDestino) throws Exception;

}
