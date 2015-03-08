package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoUsuarioDAO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

/**
 * @author CentralIT
 */
@SuppressWarnings("rawtypes")
public class EmpregadoServiceEjb extends CrudServiceImpl implements EmpregadoService {

    private EmpregadoDao dao;

    @Override
    protected EmpregadoDao getDao() {
        if (dao == null) {
            dao = new EmpregadoDao();
        }
        return dao;
    }

    @Override
    protected void validaDelete(final Object obj) throws Exception {
        final SolicitacaoServicoServiceEjb solicitacaoServicoService = new SolicitacaoServicoServiceEjb();
        final EmpregadoDTO empAux = (EmpregadoDTO) obj;
        if (solicitacaoServicoService.temSolicitacaoServicoAbertaDoEmpregado(empAux.getIdEmpregado())) {
            throw new LogicException(this.i18nMessage("colaborador.existemSolicitacoesNomeDesteEmpregado"));
        }
    }

    @Override
    protected void validaUpdate(final Object obj) throws Exception {
        final EmpregadoDTO empregado = (EmpregadoDTO) obj;
        final UsuarioServiceEjb usuarioServiceEjb = new UsuarioServiceEjb();
        final UsuarioDTO usuarioNovaSituacao = usuarioServiceEjb.restoreByIdEmpregado(empregado.getIdEmpregado());
        if (usuarioNovaSituacao != null) {
            if (empregado.getIdSituacaoFuncional() == 1) {
                // ativado
                usuarioNovaSituacao.setStatus("A");
            } else {
                // desativado
                usuarioNovaSituacao.setStatus("I");
            }

            usuarioServiceEjb.updateNotNull(usuarioNovaSituacao);
        }
    }

    @Override
    public EmpregadoDTO restoreEmpregadoSeAtivo(final EmpregadoDTO empregadoDto) {
        return this.getDao().restoreEmpregadoSeAtivo(empregadoDto);
    }

    @Override
    public EmpregadoDTO calcularCustos(final EmpregadoDTO empregado) throws ServiceException, LogicException {
        double valorCustoTotal = 0;
        double valorSalario = 0;
        double custoHora = 0;
        if (empregado.getValorSalario() == null) {
            empregado.setValorSalario(0.0);
        }
        valorSalario = empregado.getValorSalario();
        if (empregado.getTipo().equalsIgnoreCase("E")) { // CLT
            valorCustoTotal = valorSalario * 1.78; // 78% de encargos
        } else if (empregado.getTipo().equalsIgnoreCase("P")) {// PJ
            valorCustoTotal = valorSalario * 1.1; // 10% de encargos
        } else if (empregado.getTipo().equalsIgnoreCase("F")) {// Free Lancer
            valorCustoTotal = valorSalario;
        } else if (empregado.getTipo().equalsIgnoreCase("S")) {// Estagio
            valorCustoTotal = valorSalario;
        } else if (empregado.getTipo().equalsIgnoreCase("X")) {// Socio
            valorCustoTotal = valorSalario;
        }
        // Acrescenta 25% de encargos na produtividade.
        if (empregado.getValorProdutividadeMedia() == null) {
            empregado.setValorProdutividadeMedia(0.0);
        }
        if (empregado.getValorPlanoSaudeMedia() == null) {
            empregado.setValorPlanoSaudeMedia(0.0);
        }
        if (empregado.getValorVRefMedia() == null) {
            empregado.setValorVRefMedia(0.0);
        }
        if (empregado.getValorVTraMedia() == null) {
            empregado.setValorVTraMedia(0.0);
        }
        valorCustoTotal = valorCustoTotal + empregado.getValorProdutividadeMedia() * 1.25;
        valorCustoTotal = valorCustoTotal + empregado.getValorPlanoSaudeMedia();
        valorCustoTotal = valorCustoTotal + empregado.getValorVRefMedia();
        valorCustoTotal = valorCustoTotal + empregado.getValorVTraMedia();
        custoHora = valorCustoTotal / 168;
        empregado.setCustoPorHora(custoHora);
        empregado.setCustoTotalMes(valorCustoTotal);
        return empregado;
    }

    @Override
    public Collection<EmpregadoDTO> listEmpregadosByIdGrupo(final Integer idGrupo) throws ServiceException {
        try {
            return this.getDao().listEmpregadosByIdGrupo(idGrupo);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<EmpregadoDTO> listEmpregadosByIdUnidade(final Integer idUnidade) throws ServiceException {
        try {
            return this.getDao().listEmpregadosByIdUnidade(idUnidade);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listarIdEmpregados(final Integer limit, final Integer offset) throws Exception {
        try {
            return this.getDao().listarIdEmpregados(limit, offset);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmpregadoDTO restoreByIdEmpregado(final Integer idEmpregado) throws Exception {
        try {
            return this.getDao().restoreByIdEmpregado(idEmpregado);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmpregadoDTO restoreEmpregadosAtivosById(final Integer idEmpregado) {
        try {
            return this.getDao().restoreEmpregadoAtivoById(idEmpregado);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return new EmpregadoDTO();
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final EmpregadoDTO empregadoDto = (EmpregadoDTO) model;

        try {
            this.validaUpdate(model);

            final UsuarioServiceEjb usuarioServiceEjb = new UsuarioServiceEjb();
            final UsuarioDTO usuarioDto = usuarioServiceEjb.restoreByIdEmpregado(empregadoDto.getIdEmpregado());

            if (usuarioDto != null) {
                usuarioDto.setNomeUsuario(empregadoDto.getNome());
                usuarioServiceEjb.updateNotNull(usuarioDto);
            }

            this.getDao().update(empregadoDto);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Collection<EmpregadoDTO> listEmpregadosGrupo(final Integer idEmpregado, final Integer idGrupo) throws Exception {
        try {
            return this.getDao().listEmpregadosGrupo(idEmpregado, idGrupo);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteEmpregado(final IDto model) throws ServiceException, Exception {
        final EmpregadoDTO empregadoDto = (EmpregadoDTO) model;

        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        final UsuarioDao usuarioDao = new UsuarioDao();
        final PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();
        final EmpregadoDao empregadoDao = new EmpregadoDao();
        try {
            this.validaUpdate(model);
            this.validaDelete(model);

            empregadoDao.setTransactionControler(tc);
            usuarioDao.setTransactionControler(tc);
            perfilAcessoUsuarioDao.setTransactionControler(tc);

            tc.start();

            Integer idEmpregado = 0;
            idEmpregado = empregadoDto.getIdEmpregado();
            UsuarioDTO usuarioDto = new UsuarioDTO();
            PerfilAcessoUsuarioDTO perfilAcessoUsuarioDTO = new PerfilAcessoUsuarioDTO();
            usuarioDto = usuarioDao.restoreByIdEmpregadosDeUsuarios(idEmpregado);

            if (usuarioDto != null) {
                usuarioDto = (UsuarioDTO) usuarioDao.restore(usuarioDto);
                usuarioDto.setStatus("I");
                usuarioDao.update(usuarioDto);

                perfilAcessoUsuarioDTO.setIdUsuario(usuarioDto.getIdUsuario());
                perfilAcessoUsuarioDTO = (PerfilAcessoUsuarioDTO) perfilAcessoUsuarioDao.restorePerfilAcessoUsuario(perfilAcessoUsuarioDTO);

                if (perfilAcessoUsuarioDTO != null) {
                    perfilAcessoUsuarioDao.delete(perfilAcessoUsuarioDTO);
                }
            }

            empregadoDto.setDataFim(UtilDatas.getDataAtual());
            empregadoDao.update(model);

            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(final EmpregadoDTO empregado) throws ServiceException, Exception {

    }

    @Override
    public void updateNotNull(final IDto dto) {
        try {
            this.getDao().updateNotNull(dto);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        try {
            this.validaCreate(model);

            model = this.getDao().create(model);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return model;
    }

    @Override
    public Integer consultaUnidadeDoEmpregado(final Integer idEmpregado) throws Exception {
        try {
            return this.getDao().consultaUnidadeDoEmpregado(idEmpregado);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<EmpregadoDTO> listEmailContrato(final Integer idContrato) throws ServiceException {
        try {
            return this.getDao().listEmailContrato(idContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<EmpregadoDTO> listEmailContrato() throws ServiceException {
        try {
            return this.getDao().listEmailContrato();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmpregadoDTO listEmpregadoContrato(final Integer idContrato, final String email) throws ServiceException {
        try {
            return this.getDao().listEmpregadoContrato(idContrato, email);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<EmpregadoDTO> listEmpregadoByContratoAndUnidadeAndEmpregados(final Integer idContrato, final Integer idUnidade, final Integer[] idEmpregados,
            final UsuarioDTO usuario, final ArrayList<UnidadeDTO> listUnidadeContrato) throws ServiceException {
        try {
            return this.getDao().listEmpregadoByContratoAndUnidadeAndEmpregados(idContrato, idUnidade, idEmpregados, usuario, listUnidadeContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmpregadoDTO listEmpregadoContrato(final String email) throws ServiceException {
        try {
            return this.getDao().listEmpregadoContrato(email);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listEmpregadoContrato(final Integer idContrato) throws ServiceException {
        try {
            return this.getDao().listEmpregadoContrato(idContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listarEmailsNotificacoesConhecimento(final Integer idConhecimento) throws Exception {
        return this.getDao().listarEmailsNotificacoesConhecimento(idConhecimento);
    }

    @Override
    public boolean verificarEmpregadosAtivos(final EmpregadoDTO obj) throws Exception {
        return this.getDao().verificarEmpregadosAtivos(obj);
    }

    @Override
    public EmpregadoDTO restoreByEmail(final String email) throws Exception {
        return this.getDao().restoreByEmail(email);
    }

    @Override
    public Collection findByNomeEmpregado(final EmpregadoDTO empregadoDTO) throws Exception {
        return this.getDao().findByNomeEmpregado(empregadoDTO);
    }

    public Collection findByNomeEmpregado(final String nome) throws Exception {
        return this.getDao().findByNomeEmpregado(nome);
    }

    @Override
    public EmpregadoDTO restoreByNomeEmpregado(final EmpregadoDTO empregadoDTO) throws Exception {
        return this.getDao().restoreByNomeEmpregado(empregadoDTO);
    }

    @Override
    public EmpregadoDTO restauraTodos(final EmpregadoDTO param) throws Exception {
        return (EmpregadoDTO) this.getDao().restauraTodos(param);
    }

    @Override
    public Collection<EmpregadoDTO> findSolicitanteByNomeAndIdContratoAndIdUnidade(final String nome, final Integer idContrato, final Integer idUnidade) throws Exception {
        return this.getDao().findSolicitanteByNomeAndIdContratoAndIdUnidade(nome, idContrato, idUnidade);
    }

    @Override
    public EmpregadoDTO findByTelefoneOrRamal(final String telefone) throws Exception {
        return this.getDao().findByTelefoneOrRamal(telefone);
    }

    /**
     * Restaura o EmpregadoDTO com o Nome cargo a partir do ID Empregado informado.
     *
     * @param idEmpregado
     * @return EmpregadoDTO com NomeCargo
     * @author maycon.fernandes
     */
    @Override
    public EmpregadoDTO restoreEmpregadoAndNomeCargoByIdEmpegado(final Integer idEmpregado) throws Exception {
        return this.getDao().restoreEmpregadoAndNomeCargoByIdEmpegado(idEmpregado);
    }

    @Override
    public EmpregadoDTO restoreEmpregadoWithIdContratoPadraoByIdEmpregado(final Integer idEmpregado) throws Exception {
        return this.getDao().restoreEmpregadoWithIdContratoPadraoByIdEmpregado(idEmpregado);
    }

    /**
     * Consulta o nome dos empregados com nome diferente de Administrador e data fim diferente de zero
     *
     * @param nomeEmpregado
     * @return Collection<EmpregadoDTO> Com empregados com nome diferente de Administrador e data fim diferente de zero
     * @throws Exception
     */
    @Override
    public Collection<EmpregadoDTO> consultarNomeEmpregadoSemAdministrador(final String nome) throws Exception {
        return this.getDao().consultarNomeEmpregadoSemAdministrador(nome);
    }

    @Override
    public Collection<EmpregadoDTO> consultarNomeNaoEmpregado(final String nome) throws Exception {
        return this.getDao().consultarNomeNaoEmpregado(nome);
    }

    @Override
    public EmpregadoDTO restoreByCPF(final String cpf) throws Exception {
        return this.getDao().restoreByCPF(cpf);
    }

    @Override
    public List<EmpregadoDTO> findByNomeEmpregadoAndGrupo(final String nomeEmpregado, final Integer idGrupo) throws Exception {
        return this.getDao().findByNomeEmpregadoAndGrupo(nomeEmpregado, idGrupo);
    }

    @Override
    public EmpregadoDTO restoreByIdUsuario(final Integer idUsuario) {
        return this.getDao().restoreByIdUsuario(idUsuario);
    }

}
