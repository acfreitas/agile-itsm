package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import br.com.centralit.citcorpore.bean.BICitsmartResultRotinaDTO;
import br.com.centralit.citcorpore.bean.ConexaoBIDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.centralit.citcorpore.integracao.ConexaoBIDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;

public class ConexaoBIServiceEjb extends CrudServiceImpl implements ConexaoBIService {

    private ConexaoBIDAO dao;

    @Override
    protected ConexaoBIDAO getDao() {
        if (dao == null) {
            dao = new ConexaoBIDAO();
        }
        return dao;
    }

    /**
     * Responsável por listar todas as conexões existentes ativas e inativas na tela Painel de Controle.
     *
     * @author thiago.barbosa
     */
    @Override
    @SuppressWarnings("unchecked")
    public Collection<ConexaoBIDTO> listAll() throws Exception {
        return this.getDao().list();
    }

    @Override
    public Collection findByIdConexao(final ConexaoBIDTO conexaoBIDTO) throws Exception {
        return this.getDao().list();
    }

    /**
     * Responsável por verificar se já existe o nome a ser cadastrado.
     *
     * @author thiago.barbosa
     */
    @Override
    public boolean jaExisteRegistroComMesmoNome(final ConexaoBIDTO conexaoBIDTO) throws Exception {
        return this.getDao().jaExisteRegistroComMesmoNome(conexaoBIDTO);
    }

    /**
     * Responsável por verificar se já existe o link a ser cadastrado.
     *
     * @author thiago.barbosa
     */
    @Override
    public boolean jaExisteRegistroComMesmoLink(final ConexaoBIDTO conexaoBIDTO) throws Exception {
        return this.getDao().jaExisteRegistroComMesmoLink(conexaoBIDTO);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection listarConexoesPaginadas(final Collection<ConexaoBIDTO> conexaoBIDTO, final Integer pgAtual, final Integer qtdPaginacao) throws Exception {
        return this.getDao().listarConexoesPaginadas(conexaoBIDTO, pgAtual, qtdPaginacao);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Collection listarConexoesPaginadasFiltradas(final ConexaoBIDTO conexaoBIDTO, final Integer pgAtual, final Integer qtdPaginacao) throws Exception {
        return this.getDao().listarConexoesPaginadasFiltradas(conexaoBIDTO, pgAtual, qtdPaginacao);
    }

    @Override
    public Integer obterTotalDePaginas(final Integer itensPorPagina, final String loginUsuario, final ConexaoBIDTO conexaoBIBean) throws Exception {
        Integer total = 0;

        // ESSA LISTA DE TAREFAS JÁ ESTÁ VINDO COM O DTO E NÃO DEVERIA VIR. CRIAR MÉTODO PARA TRAZER APENAS AS TAREFAS COM O IDINSTANCIA, QUE É A ÚNICA INFORMAÇÃO UTILIZADA NA
        // CONSULTA ABAIXO.
        // List<TarefaFluxoDTO> listTarefasComSolicitacaoServico = recuperaTarefas(loginUsuario);
        //
        // listTarefas = listTarefasComSolicitacaoServico;
        // Comentado para centalizar o método abaixo

        total = this.getDao().totalDePaginas(itensPorPagina, null, conexaoBIBean);

        return total;
    }

    @Override
    public ConexaoBIDTO findByIdProcessBatch(final Integer idProcessamentoBatch) throws Exception {
        return this.getDao().findByIdProcessBatch(idProcessamentoBatch);
    }

    @Override
    public ArrayList<ConexaoBIDTO> listarConexoesAutomaticasSemAgendEspOuExcecao() throws ServiceException, Exception {
        return this.getDao().listarConexoesAutomaticasSemAgendEspOuExcecao();
    }

    private void adicionaID(final StringBuilder idsProcessamentos, final Integer id) {
        if (id != null && id.intValue() > 0) {
            if (idsProcessamentos.length() > 0) {
                idsProcessamentos.append("," + id.toString());
            } else {
                idsProcessamentos.append(id.toString());
            }
        }
    }

    /**
     * @author euler.ramos
     *         Retorna os IDs dos ProcessamentosBatch presentes na tabela ConexaoBI.
     */
    @Override
    @SuppressWarnings("unchecked")
    public String getIdProcEspecificoOuExcecao() throws Exception {
        final StringBuilder idsProcessamentos = new StringBuilder();
        final ArrayList<ConexaoBIDTO> conexoes = (ArrayList<ConexaoBIDTO>) this.getDao().list();
        for (final ConexaoBIDTO conexaoBIDTO : conexoes) {
            this.adicionaID(idsProcessamentos, conexaoBIDTO.getIdProcessamentoBatchEspecifico());
            this.adicionaID(idsProcessamentos, conexaoBIDTO.getIdProcessamentoBatchExcecao());
        }
        return idsProcessamentos.toString();
    }

    /**
     * @author euler.ramos
     *         Retorna o Agendamento da Conexão. Que pode ser Específico ou Padrão
     *         Quando o Específico não existe ou está inativo o sistema retorna o Agendamento padrão para a atividade de importação
     *         se ela não for manual!
     */
    public ProcessamentoBatchDTO agendamentoAtivo(final ConexaoBIDTO conexaoBIDTO) throws Exception {
        final ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
        ProcessamentoBatchDTO processamentoBatchDTO = new ProcessamentoBatchDTO();
        if (conexaoBIDTO.getIdProcessamentoBatchEspecifico() != null) {
            processamentoBatchDTO.setIdProcessamentoBatch(conexaoBIDTO.getIdProcessamentoBatchEspecifico());
            processamentoBatchDTO = (ProcessamentoBatchDTO) processamentoBatchService.restore(processamentoBatchDTO);
            if (processamentoBatchDTO != null && processamentoBatchDTO.getSituacao() != null) {
                if (processamentoBatchDTO.getSituacao().equalsIgnoreCase("A")) {
                    return processamentoBatchDTO;
                }
            }
        }
        return processamentoBatchService.getAgendamentoPadrao();
    }

    public ProcessamentoBatchDTO agendamentoExcecaoAtivo(final ConexaoBIDTO conexaoBIDTO) throws Exception {
        if (conexaoBIDTO.getIdProcessamentoBatchExcecao() != null) {
            final ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
            ProcessamentoBatchDTO processamentoBatchDTO = new ProcessamentoBatchDTO();
            processamentoBatchDTO.setIdProcessamentoBatch(conexaoBIDTO.getIdProcessamentoBatchExcecao());
            processamentoBatchDTO = (ProcessamentoBatchDTO) processamentoBatchService.restore(processamentoBatchDTO);
            if (processamentoBatchDTO != null && processamentoBatchDTO.getSituacao() != null && processamentoBatchDTO.getSituacao().equalsIgnoreCase("A")) {
                return processamentoBatchDTO;
            }
        }
        return null;
    }

    /**
     * @author euler.ramos
     *         Retorna a data hora da próxima execução.
     */
    @Override
    public Date getProxDtExecucao(final ConexaoBIDTO conexaoBIDTO) throws ServiceException, Exception {
        Date data = null;
        // Filtrando somente as Conexões Automáticas!
        if (conexaoBIDTO != null && conexaoBIDTO.getTipoImportacao() != null && conexaoBIDTO.getTipoImportacao().equalsIgnoreCase("A")) {
            final ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
            // Obtendo o agendamento atual da conexão [Específico ou Padrão]
            final ProcessamentoBatchDTO processamentoBatchAg = this.agendamentoAtivo(conexaoBIDTO);
            final ProcessamentoBatchDTO processamentoBatchExc = this.agendamentoExcecaoAtivo(conexaoBIDTO);
            if (processamentoBatchAg != null && processamentoBatchAg.getExpressaoCRON() != null && processamentoBatchAg.getExpressaoCRON().length() > 0) {
                Date dataEsp = null;
                if (processamentoBatchAg != null && processamentoBatchAg.getExpressaoCRON() != null && processamentoBatchAg.getExpressaoCRON().length() > 0) {
                    dataEsp = processamentoBatchService.proximaExecucao(processamentoBatchAg.getExpressaoCRON());
                }
                Date dataExc = null;
                if (processamentoBatchExc != null && processamentoBatchExc.getExpressaoCRON() != null && processamentoBatchExc.getExpressaoCRON().length() > 0) {
                    dataExc = processamentoBatchService.proximaExecucao(processamentoBatchExc.getExpressaoCRON());
                }
                if (dataEsp != null) {
                    data = dataEsp;
                }
                if (data != null) {
                    if (dataExc != null) {
                        return data.compareTo(dataExc) <= 0 ? data : dataExc;
                    } else {
                        return data;
                    }
                } else {
                    return dataExc;
                }
            } else {
                if (processamentoBatchExc != null && processamentoBatchExc.getExpressaoCRON() != null && processamentoBatchExc.getExpressaoCRON().length() > 0) {
                    data = processamentoBatchService.proximaExecucao(processamentoBatchExc.getExpressaoCRON());
                }
            }
        }
        return data;
    }

    @Override
    @SuppressWarnings("deprecation")
    public Date getProxDtExecucaoPadraoOuEspecifica(final ConexaoBIDTO conexaoBIDTO) throws ServiceException, Exception {
        Date data = null;
        // Filtrando somente as Conexões Automáticas!
        if (conexaoBIDTO != null && conexaoBIDTO.getTipoImportacao() != null && conexaoBIDTO.getTipoImportacao().equalsIgnoreCase("A")) {
            final ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
            // Obtendo o agendamento atual da conexão [Específico ou Padrão]
            final ProcessamentoBatchDTO processamentoBatchAg = this.agendamentoAtivo(conexaoBIDTO);
            if (processamentoBatchAg != null && processamentoBatchAg.getExpressaoCRON() != null && processamentoBatchAg.getExpressaoCRON().length() > 0) {
                Date dataEsp = null;
                if (processamentoBatchAg != null && processamentoBatchAg.getExpressaoCRON() != null && processamentoBatchAg.getExpressaoCRON().length() > 0) {
                    dataEsp = processamentoBatchService.proximaExecucao(processamentoBatchAg.getExpressaoCRON());
                }
                // subtraindo uma hora da data da proxima execucao
                if (dataEsp != null) {
                    dataEsp.setHours(dataEsp.getHours() - 1);
                    data = dataEsp;
                }
            }
        }
        return data;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BICitsmartResultRotinaDTO validaAgendamentoExcecao(final ConexaoBIDTO conexaoBIDTO, final ProcessamentoBatchDTO processamentoBatchDTO) throws ServiceException,
            Exception {
        final BICitsmartResultRotinaDTO resultValidacao = new BICitsmartResultRotinaDTO();
        resultValidacao.setResultado(false);
        final Date dataProExecPadraoOuEspecifica = this.getProxDtExecucaoPadraoOuEspecifica(conexaoBIDTO);
        final Date dataHoraAtual = new Date();
        final Date dataHoraRealProxAgendamento = this.getProxDtExecucaoPadraoOuEspecifica(conexaoBIDTO);
        final ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
        if (dataProExecPadraoOuEspecifica != null) {
            dataHoraRealProxAgendamento.setHours(dataProExecPadraoOuEspecifica.getHours() + 1);
            final Date dataForm = processamentoBatchService.proximaExecucao(processamentoBatchDTO.getExpressaoCRON());
            if (dataForm != null) {
                // validação se a data a ser executada estiver após a dataHoraAtual
                if (dataForm.compareTo(dataHoraAtual) < 0) {
                    resultValidacao.setResultado(false);
                    resultValidacao.setMensagem("Não foi possível agendar, data/hora deste agendamento, inferior a data/hora atual!");
                } else if (dataForm.compareTo(dataProExecPadraoOuEspecifica) <= 0) { // validação se a data a ser executada estiver antes de uma hora da proxima execucao
                    resultValidacao.setResultado(true);
                } else if (dataForm.compareTo(dataProExecPadraoOuEspecifica) > 0 && dataForm.compareTo(dataHoraRealProxAgendamento) < 0) { // caso esteja a menos de uma hora da
                                                                                                                                           // proxima execução
                    resultValidacao.setResultado(false);
                    resultValidacao.setMensagem("Não foi possível agendar, data/hora deste agendamento está a menos de uma hora da data/hora da próxima execução agendada!");
                } else {
                    resultValidacao.setResultado(false);// caso esteja superior a data/hora da proxima execução agendada
                    resultValidacao.setMensagem("Não foi possível agendar, data/hora deste agendamento, superior a data/hora da próxima execução agendada!");
                }
            }
        } else {
            resultValidacao.setResultado(true);
        }
        return resultValidacao;
    }

}
