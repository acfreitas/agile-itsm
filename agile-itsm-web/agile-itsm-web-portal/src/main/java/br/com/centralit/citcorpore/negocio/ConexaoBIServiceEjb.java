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
     * Respons�vel por listar todas as conex�es existentes ativas e inativas na tela Painel de Controle.
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
     * Respons�vel por verificar se j� existe o nome a ser cadastrado.
     *
     * @author thiago.barbosa
     */
    @Override
    public boolean jaExisteRegistroComMesmoNome(final ConexaoBIDTO conexaoBIDTO) throws Exception {
        return this.getDao().jaExisteRegistroComMesmoNome(conexaoBIDTO);
    }

    /**
     * Respons�vel por verificar se j� existe o link a ser cadastrado.
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

        // ESSA LISTA DE TAREFAS J� EST� VINDO COM O DTO E N�O DEVERIA VIR. CRIAR M�TODO PARA TRAZER APENAS AS TAREFAS COM O IDINSTANCIA, QUE � A �NICA INFORMA��O UTILIZADA NA
        // CONSULTA ABAIXO.
        // List<TarefaFluxoDTO> listTarefasComSolicitacaoServico = recuperaTarefas(loginUsuario);
        //
        // listTarefas = listTarefasComSolicitacaoServico;
        // Comentado para centalizar o m�todo abaixo

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
     *         Retorna o Agendamento da Conex�o. Que pode ser Espec�fico ou Padr�o
     *         Quando o Espec�fico n�o existe ou est� inativo o sistema retorna o Agendamento padr�o para a atividade de importa��o
     *         se ela n�o for manual!
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
     *         Retorna a data hora da pr�xima execu��o.
     */
    @Override
    public Date getProxDtExecucao(final ConexaoBIDTO conexaoBIDTO) throws ServiceException, Exception {
        Date data = null;
        // Filtrando somente as Conex�es Autom�ticas!
        if (conexaoBIDTO != null && conexaoBIDTO.getTipoImportacao() != null && conexaoBIDTO.getTipoImportacao().equalsIgnoreCase("A")) {
            final ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
            // Obtendo o agendamento atual da conex�o [Espec�fico ou Padr�o]
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
        // Filtrando somente as Conex�es Autom�ticas!
        if (conexaoBIDTO != null && conexaoBIDTO.getTipoImportacao() != null && conexaoBIDTO.getTipoImportacao().equalsIgnoreCase("A")) {
            final ProcessamentoBatchService processamentoBatchService = (ProcessamentoBatchService) ServiceLocator.getInstance().getService(ProcessamentoBatchService.class, null);
            // Obtendo o agendamento atual da conex�o [Espec�fico ou Padr�o]
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
                // valida��o se a data a ser executada estiver ap�s a dataHoraAtual
                if (dataForm.compareTo(dataHoraAtual) < 0) {
                    resultValidacao.setResultado(false);
                    resultValidacao.setMensagem("N�o foi poss�vel agendar, data/hora deste agendamento, inferior a data/hora atual!");
                } else if (dataForm.compareTo(dataProExecPadraoOuEspecifica) <= 0) { // valida��o se a data a ser executada estiver antes de uma hora da proxima execucao
                    resultValidacao.setResultado(true);
                } else if (dataForm.compareTo(dataProExecPadraoOuEspecifica) > 0 && dataForm.compareTo(dataHoraRealProxAgendamento) < 0) { // caso esteja a menos de uma hora da
                                                                                                                                           // proxima execu��o
                    resultValidacao.setResultado(false);
                    resultValidacao.setMensagem("N�o foi poss�vel agendar, data/hora deste agendamento est� a menos de uma hora da data/hora da pr�xima execu��o agendada!");
                } else {
                    resultValidacao.setResultado(false);// caso esteja superior a data/hora da proxima execu��o agendada
                    resultValidacao.setMensagem("N�o foi poss�vel agendar, data/hora deste agendamento, superior a data/hora da pr�xima execu��o agendada!");
                }
            }
        } else {
            resultValidacao.setResultado(true);
        }
        return resultValidacao;
    }

}
