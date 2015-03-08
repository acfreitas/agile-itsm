package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bpm.negocio.ExecucaoSolicitacao;
import br.com.centralit.citcorpore.integracao.ConexaoBIDAO;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServiceImpl;

public class ExecucaoConexaoBIServiceEjb extends CrudServiceImpl implements ExecucaoConexaoBIService {

    @Override
    public Integer totalPaginas(final Integer itensPorPagina, final String loginUsuario) throws Exception {
        return new ExecucaoSolicitacao().totalPaginas(itensPorPagina, loginUsuario);
    }

    @Override
    public Integer obterTotalDePaginas(final Integer itensPorPagina, final String loginUsuario, final ConexaoBIDTO conexaoBIBean) throws Exception {
        Integer total = 0;

        final ConexaoBIDAO conexaoBIDao = new ConexaoBIDAO();

        // ESSA LISTA DE TAREFAS JÁ ESTÁ VINDO COM O DTO E NÃO DEVERIA VIR. CRIAR MÉTODO PARA TRAZER APENAS AS TAREFAS COM O IDINSTANCIA, QUE É A ÚNICA INFORMAÇÃO UTILIZADA NA
        // CONSULTA ABAIXO.
        // List<TarefaFluxoDTO> listTarefasComSolicitacaoServico = recuperaTarefas(loginUsuario);
        //
        // listTarefas = listTarefasComSolicitacaoServico;
        // Comentado para centalizar o método abaixo

        total = conexaoBIDao.totalDePaginas(itensPorPagina, null, conexaoBIBean);

        return total;
    }

    @Override
    public List<TarefaFluxoDTO> recuperaTarefas(final String loginUsuario) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TarefaFluxoDTO recuperaTarefa(final String loginUsuario, final Integer idTarefa) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delegaTarefa(final String loginUsuario, final Integer idTarefa, final String usuarioDestino, final String grupoDestino) throws Exception {
        // TODO Auto-generated method stub
    }

    @Override
    protected CrudDAO getDao() {
        return null;
    }

}
