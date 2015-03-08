package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.rh.bean.AtitudeCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EntrevistaCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.HistoricoCandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.HistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.integracao.AtitudeCandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.CurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.EntrevistaCandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.HistoricoFuncionalDao;
import br.com.centralit.citcorpore.rh.integracao.ItemHistoricoFuncionalDao;
import br.com.centralit.citcorpore.util.Enumerados.TipoEntrevista;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings({"rawtypes"})
public class EntrevistaCandidatoServiceEjb extends CrudServiceImpl implements EntrevistaCandidatoService {

    private EntrevistaCandidatoDao dao;

    @Override
    protected EntrevistaCandidatoDao getDao() {
        if (dao == null) {
            dao = new EntrevistaCandidatoDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        this.validaAtualizacao(arg0);
    }

    private void validaAtualizacao(final Object arg0) throws Exception {

        final EntrevistaCandidatoDTO entrevistaCandidatoDto = (EntrevistaCandidatoDTO) arg0;

        if (entrevistaCandidatoDto.getTipoEntrevista().equalsIgnoreCase(TipoEntrevista.RH.name())) {

            if (entrevistaCandidatoDto.getPlanoCarreira() == null || entrevistaCandidatoDto.getPlanoCarreira().trim().equals("")) {
                throw new LogicException("Plano de carreira não informado");
            }

            if (entrevistaCandidatoDto.getCaracteristicas() == null || entrevistaCandidatoDto.getCaracteristicas().trim().equals("")) {
                throw new LogicException("Características não informadas");
            }

            if (entrevistaCandidatoDto.getTrabalhoEmEquipe() == null || entrevistaCandidatoDto.getTrabalhoEmEquipe().trim().equals("")) {
                throw new LogicException("Trabalho em Equipe não informado");
            }

            if (entrevistaCandidatoDto.getPossuiOutraAtividade() == null) {
                throw new LogicException("Indicador se possui outra atividade não informado");
            }

            if (entrevistaCandidatoDto.getPossuiOutraAtividade().equalsIgnoreCase("S")
                    && (entrevistaCandidatoDto.getOutraAtividade() == null || entrevistaCandidatoDto.getOutraAtividade().trim().equals(""))) {
                throw new LogicException("Outra atividade não informada");
            }

            // if (entrevistaCandidatoDto.getConcordaExclusividade() == null)
            // throw new LogicException("Indicador se concorda com exclusividade não informado");

            if (entrevistaCandidatoDto.getSalarioAtual() == null || entrevistaCandidatoDto.getSalarioAtual().doubleValue() == 0) {
                throw new LogicException("Salário atual não informado");
            }

            if (entrevistaCandidatoDto.getPretensaoSalarial() == null || entrevistaCandidatoDto.getPretensaoSalarial().doubleValue() == 0) {
                throw new LogicException("Pretensão salarial não informada");
            }

            if (entrevistaCandidatoDto.getDataDisponibilidade() == null) {
                throw new LogicException("Data da disponibilidade não informada");
            }

            if (entrevistaCandidatoDto.getCompetencias() == null || entrevistaCandidatoDto.getCompetencias().trim().equals("")) {
                throw new LogicException("Competências não informadas");
            }
            // foi comentado por cleison por não etender a lógica e por estar dando erro.
            if (entrevistaCandidatoDto.getColAtitudes() == null || entrevistaCandidatoDto.getColAtitudes().isEmpty()) {

                throw new LogicException("Atitudes não informadas");
            } else {

                for (final AtitudeCandidatoDTO atitudeCandidatoDto : entrevistaCandidatoDto.getColAtitudes()) {

                    if (atitudeCandidatoDto.getAvaliacao() == null || atitudeCandidatoDto.getAvaliacao().equalsIgnoreCase("")) {

                        throw new LogicException("Informe a avaliação para a atitude \"" + atitudeCandidatoDto.getDescricao() + "\"");
                    }
                }
            }
        } else {
            if (entrevistaCandidatoDto.getResultado() == null) {

                throw new LogicException("Resultado não informado");
            }
        }

    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        this.validaAtualizacao(arg0);
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final EntrevistaCandidatoDao entrevistaCandidatoDao = new EntrevistaCandidatoDao();
        final AtitudeCandidatoDao atitudeCandidatoDao = new AtitudeCandidatoDao();
        final TransactionControler tc = new TransactionControlerImpl(atitudeCandidatoDao.getAliasDB());

        try {

            this.validaCreate(model);

            // Seta o TransactionController para os DAOs
            entrevistaCandidatoDao.setTransactionControler(tc);
            atitudeCandidatoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            final EntrevistaCandidatoDTO entrevistaCandidatoDto = (EntrevistaCandidatoDTO) model;
            if (entrevistaCandidatoDto.getTipoEntrevista().equalsIgnoreCase(TipoEntrevista.RH.name())) {
                entrevistaCandidatoDto.setResultado("N");
            }

            if (entrevistaCandidatoDto.getConcordaExclusividade() == null) {
                throw new LogicException("Sem concorda Exclusividade");
            }

            model = entrevistaCandidatoDao.create(model);
            this.atualizaAnexos(entrevistaCandidatoDto, tc);

            // grava historico do candidato
            if (entrevistaCandidatoDto.getResultado() != null && !entrevistaCandidatoDto.getResultado().equals("N")) {
                this.gravaHistoricoCandidato(entrevistaCandidatoDto, tc);
            }

            if (entrevistaCandidatoDto.getTipoEntrevista().equalsIgnoreCase(TipoEntrevista.RH.name()) && entrevistaCandidatoDto.getColAtitudes() != null) {
                for (final AtitudeCandidatoDTO atitudeCandidatoDto : entrevistaCandidatoDto.getColAtitudes()) {
                    atitudeCandidatoDto.setIdEntrevista(entrevistaCandidatoDto.getIdEntrevista());
                    atitudeCandidatoDao.create(atitudeCandidatoDto);
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
            return model;
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
        return model;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {

        // Instancia Objeto controlador de transacao
        final EntrevistaCandidatoDao entrevistaCandidatoDao = new EntrevistaCandidatoDao();
        final AtitudeCandidatoDao atitudeCandidatoDao = new AtitudeCandidatoDao();
        final TransactionControler tc = new TransactionControlerImpl(atitudeCandidatoDao.getAliasDB());

        try {
            tc.start();

            this.validaUpdate(model);

            // Seta o TransactionController para os DAOs
            final EntrevistaCandidatoDTO entrevistaCandidatoDto = (EntrevistaCandidatoDTO) model;
            entrevistaCandidatoDao.setTransactionControler(tc);
            atitudeCandidatoDao.setTransactionControler(tc);

            entrevistaCandidatoDao.updateNotNull(entrevistaCandidatoDto);

            this.atualizaAnexos(entrevistaCandidatoDto, tc);

            // grava historico do candidato
            if (entrevistaCandidatoDto.getResultado() != null && !entrevistaCandidatoDto.getResultado().equals("")) {
                this.gravaHistoricoCandidato(entrevistaCandidatoDto, tc);
            }

            if (entrevistaCandidatoDto.getTipoEntrevista().equalsIgnoreCase(TipoEntrevista.RH.name())) {
                atitudeCandidatoDao.deleteByIdEntrevista(entrevistaCandidatoDto.getIdEntrevista());
                if (entrevistaCandidatoDto.getColAtitudes() != null) {
                    for (final AtitudeCandidatoDTO atitudeCandidatoDto : entrevistaCandidatoDto.getColAtitudes()) {
                        atitudeCandidatoDto.setIdEntrevista(entrevistaCandidatoDto.getIdEntrevista());
                        atitudeCandidatoDao.create(atitudeCandidatoDto);
                    }
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public EntrevistaCandidatoDTO findByIdTriagemAndIdCurriculo(final Integer idTriagem, final Integer idCurriculo) throws Exception {
        return this.getDao().findByIdTriagemAndIdCurriculo(idTriagem, idCurriculo);
    }

    public void gravaHistoricoCandidato(final EntrevistaCandidatoDTO entrevistaCandidatoDto, final TransactionControler tc) throws ServiceException, Exception {
        final HistoricoCandidatoDTO historicoCandidatoDTO = new HistoricoCandidatoDTO();
        historicoCandidatoDTO.setIdCurriculo(entrevistaCandidatoDto.getIdCurriculo());
        historicoCandidatoDTO.setIdEntrevista(entrevistaCandidatoDto.getIdEntrevista());
        historicoCandidatoDTO.setResultado(entrevistaCandidatoDto.getResultado());
        historicoCandidatoDTO.setIdSolicitacaoServico(entrevistaCandidatoDto.getIdSolicitacaoServico());

        final HistoricoCandidatoService historicoCandidatoService = (HistoricoCandidatoService) ServiceLocator.getInstance().getService(HistoricoCandidatoService.class, null);
        historicoCandidatoService.create(historicoCandidatoDTO);

    }

    private void atualizaAnexos(final EntrevistaCandidatoDTO entrevistaCandidatoDto, final TransactionControler tc) throws Exception {
        new ControleGEDServiceBean()
                .atualizaAnexos(entrevistaCandidatoDto.getAnexos(), ControleGEDDTO.TABELA_RH_ENTREVISTA_CANDIDATO, entrevistaCandidatoDto.getIdEntrevista(), tc);
    }

    @Override
    public Collection listCurriculosAprovadosPorOrdemMaiorNota(final Integer idTriagem) throws Exception {
        return this.getDao().listCurriculosAprovadosPorOrdemMaiorNota(idTriagem);
    }

    @Override
    public Boolean seCandidatoAprovado(final TriagemRequisicaoPessoalDTO triagemRequisicaoPessoalDTO) throws Exception {
        return this.getDao().seCandidatoAprovado(triagemRequisicaoPessoalDTO);
    }

    /**
     * Desenvolvedor: David Rodrigues - Data: 26/03/2014 - Horário: 14:36 - ID Citsmart: 0 Motivo/Comentário: Adaptação
     * no codido para funcionamento do Historico Funcional (Item Historico Funcional)
     */
    @Override
    public void inserirRegistroHistorico(final Integer idCurriculo, final Integer idEntrevistador, String titulo, final Integer idSolicitacao, final Double notaAvaliacao,
            final String resultado) throws Exception {
        final HistoricoFuncionalDao funcionalDao = new HistoricoFuncionalDao();
        HistoricoFuncionalDTO funcionalDTO = new HistoricoFuncionalDTO();

        UsuarioDTO usuarioDto = new UsuarioDTO();
        final UsuarioDao usuarioDao = new UsuarioDao();

        CurriculoDTO curriculoDto = new CurriculoDTO();
        final CurriculoDao curriculoDao = new CurriculoDao();

        final ItemHistoricoFuncionalDao dao = new ItemHistoricoFuncionalDao();

        funcionalDTO = funcionalDao.restoreByIdCurriculo(idCurriculo);
        usuarioDto = usuarioDao.restoreByIdEmpregadosDeUsuarios(idEntrevistador);
        curriculoDto = curriculoDao.restoreByID(idCurriculo);

        if (titulo.equalsIgnoreCase("Entrevista RH")) {
            titulo = "";
            titulo = "Avaliação da Entrevista com o RH";
        }
        if (titulo.equalsIgnoreCase("Entrevista Gestor")) {
            titulo = "";
            titulo = "Inclusão do Currículo em Processo de Seleção";
        }

        final StringBuilder descricao = new StringBuilder();

        descricao.append("Candidato ");

        if (curriculoDto.getNome() != null && !curriculoDto.getNome().equalsIgnoreCase("")) {
            descricao.append(curriculoDto.getNome());
        }

        descricao.append(" esta participando do processo de seleção ");

        if (idSolicitacao != null && idSolicitacao > 0) {
            descricao.append("referente a Requisição Pessoal Nº " + idSolicitacao);
        }

        if (notaAvaliacao != null && notaAvaliacao > 0) {
            descricao.append(" recebeu a seguinte Nota de Avaliação: " + notaAvaliacao);
        }

        if (resultado.equalsIgnoreCase("A")) {
            descricao.append(". Resultado - Aprovado. ");
        }
        if (resultado.equalsIgnoreCase("R")) {
            descricao.append(". Resultado - Reprovado. ");
        }
        if (resultado.equalsIgnoreCase("S")) {
            descricao.append(". Resultado - 2º Oportunidade. ");
        }
        if (resultado.equalsIgnoreCase("D")) {
            descricao.append(". Resultado - Descarte. ");
        }

        if (funcionalDTO != null && funcionalDTO.getIdCandidato() != null) {
            dao.inserirRegistroHistoricoAutomatico(funcionalDTO.getIdCandidato(), usuarioDto.getIdUsuario(), titulo, descricao.toString(), null);
        }
    }

}
