package br.com.centralit.citcorpore.negocio;

import br.com.centralit.bpm.servico.ExecucaoFluxoService;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;

public interface ExecucaoConexaoBIService extends ExecucaoFluxoService {

    Integer totalPaginas(final Integer itensPorPagina, final String login) throws Exception;

    Integer obterTotalDePaginas(final Integer itensPorPagina, final String loginUsuario, final ConexaoBIDTO painelControleBIBean) throws Exception;

}
