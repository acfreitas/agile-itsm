package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.AtividadesServicoContratoDao;
import br.com.centralit.citcorpore.integracao.FluxoServicoDao;
import br.com.centralit.citcorpore.integracao.ResultadosEsperadosDAO;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.ValorAjusteGlosaDAO;
import br.com.centralit.citcorpore.integracao.ValoresServicoContratoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

@SuppressWarnings("rawtypes")
public class ServicoContratoServiceEjb extends CrudServiceImpl implements ServicoContratoService {

    private ServicoContratoDao dao;

    @Override
    protected ServicoContratoDao getDao() {
        if (dao == null) {
            dao = new ServicoContratoDao();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) model;
        final ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
        final FluxoServicoDao fluxoServicoDao = new FluxoServicoDao();
        final TransactionControler tc = new TransactionControlerImpl(servicoContratoDao.getAliasDB());
        try {
            fluxoServicoDao.setTransactionControler(tc);
            tc.start();
            final List<FluxoServicoDTO> listaFluxo = servicoContratoDTO.getListaFluxo();
            servicoContratoDTO = (ServicoContratoDTO) servicoContratoDao.create(servicoContratoDTO);
            if (listaFluxo != null) {
                fluxoServicoDao.deleteByIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
                for (final FluxoServicoDTO fluxoServicoDTO : listaFluxo) {
                    fluxoServicoDTO.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
                    fluxoServicoDTO.setDeleted(null);
                    fluxoServicoDao.create(fluxoServicoDTO);
                }
            }

            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            e.getStackTrace();
        } finally {
            tc.closeQuietly();

        }
        return servicoContratoDTO;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        final ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) model;
        final ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
        final FluxoServicoDao fluxoServicoDao = new FluxoServicoDao();
        final TransactionControler tc = new TransactionControlerImpl(servicoContratoDao.getAliasDB());
        try {
            fluxoServicoDao.setTransactionControler(tc);
            tc.start();
            final List<FluxoServicoDTO> listaFluxo = servicoContratoDTO.getListaFluxo();
            servicoContratoDao.update(servicoContratoDTO);
            if (listaFluxo != null) {
                fluxoServicoDao.deleteByIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
                for (final FluxoServicoDTO fluxoServicoDTO : listaFluxo) {
                    fluxoServicoDTO.setIdServicoContrato(servicoContratoDTO.getIdServicoContrato());
                    fluxoServicoDTO.setDeleted(null);
                    fluxoServicoDao.create(fluxoServicoDTO);
                }
            }

            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            e.printStackTrace();
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public Collection findByIdServico(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdServico(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdServico(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deletarByIdServicoContrato(final IDto model, final DocumentHTML document) throws ServiceException, Exception {
        final ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) model;
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        final ServicoContratoDao servicoContratoDao = new ServicoContratoDao();
        final AcordoNivelServicoDao acordoNivelServicoDao = new AcordoNivelServicoDao();
        final AtividadesServicoContratoDao atividadesServicoContratoDao = new AtividadesServicoContratoDao();
        final ValoresServicoContratoDao valoresServicoContratoDao = new ValoresServicoContratoDao();
        final ResultadosEsperadosDAO resultadosEsperadosDAO = new ResultadosEsperadosDAO();
        final ValorAjusteGlosaDAO valorAjusteGlosaDAO = new ValorAjusteGlosaDAO();
        try {
            this.validaUpdate(model);

            servicoContratoDao.setTransactionControler(tc);
            acordoNivelServicoDao.setTransactionControler(tc);
            atividadesServicoContratoDao.setTransactionControler(tc);
            valoresServicoContratoDao.setTransactionControler(tc);
            resultadosEsperadosDAO.setTransactionControler(tc);
            valorAjusteGlosaDAO.setTransactionControler(tc);

            tc.start();

            servicoContratoDTO.setDataFim(UtilDatas.getDataAtual());
            valorAjusteGlosaDAO.updateValorAjusteGlosa(servicoContratoDTO.getIdServicoContrato());
            resultadosEsperadosDAO.updateResultadosEsperados(servicoContratoDTO.getIdServicoContrato());
            valoresServicoContratoDao.updateValoresServicoContrato(servicoContratoDTO.getIdServicoContrato(), servicoContratoDTO.getDataFim());
            acordoNivelServicoDao.updateAcordoNivelServico(servicoContratoDTO.getIdServicoContrato(), servicoContratoDTO.getDataFim());
            atividadesServicoContratoDao.updateAtividadesServicoContrato(servicoContratoDTO.getIdServicoContrato());
            servicoContratoDao.updateServicoContrato(servicoContratoDTO.getIdServicoContrato(), servicoContratoDTO.getDataFim());

            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public Collection findByIdContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findServicoComNomeByIdContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findServicoComNomeByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdContratoPaginada(final ServicoContratoDTO servicoContratoDTO, final String paginacao, final Integer pagAtual, final Integer pagAtualAux,
            final Integer totalPag, final Integer quantidadePaginator, final String campoPesquisa) throws Exception {
        try {
            return this.getDao().findByIdContratoPaginada(servicoContratoDTO, paginacao, pagAtual, pagAtualAux, totalPag, quantidadePaginator, campoPesquisa);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ServicoContratoDTO findByIdContratoAndIdServico(final Integer idContrato, final Integer idServico) throws Exception {
        try {
            return this.getDao().findByIdContratoAndIdServico(idContrato, idServico);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdContratoDistinct(final Integer idContrato) throws Exception {
        try {
            return this.getDao().findByIdContratoDistinct(idContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void setDataFim(final HashMap map) throws Exception {
        final String idServicoCon = (String) map.get("IDSERVICOCONTRATO");
        Integer idServicoContrato = new Integer(0);
        if (idServicoCon != null && !StringUtils.trim(idServicoCon).isEmpty()) {
            idServicoContrato = Integer.parseInt(idServicoCon);
        }

        try {
            if (idServicoContrato != null && idServicoContrato != 0) {
                this.getDao().setDataFim(idServicoContrato);
            }
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

    }

    @Override
    public Collection listarServicosPorFornecedor(final Integer idFornecedor) throws Exception {
        try {
            return this.getDao().findByIdFornecedor(idFornecedor);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean validaServicoContrato(final HashMap mapFields) throws Exception {
        // Pegando valores do parâmetro recebido
        final String idContratoTxt = (String) mapFields.get("IDCONTRATO");
        final String idServicoTxt = (String) mapFields.get("IDSERVICO");

        Integer idContrato = null;
        Integer idServico = null;

        if (idContratoTxt != null && !idContratoTxt.equals("")) {
            idContrato = Integer.parseInt(idContratoTxt);
        }

        if (idServicoTxt != null && !idServicoTxt.equals("")) {
            idServico = Integer.parseInt(idServicoTxt);
        }

        if (idContrato != null && idServico != null) {
            return this.getDao().validaServicoContrato(idContrato, idServico);
        }
        return false;
    }

    @Override
    public Collection findServicoContratoByIdContrato(final Integer idContrato) throws Exception {
        try {
            return this.getDao().findServicoContratoByIdContrato(idContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String existeServicoContratoByIdServico(final HashMap mapFields) throws Exception {
        // Pegando valores do parâmetro recebido
        final String idServicoTxt = (String) mapFields.get("IDSERVICO");

        Integer idServico = null;

        if (idServicoTxt != null && !idServicoTxt.equals("")) {
            idServico = Integer.parseInt(idServicoTxt);
        }

        try {
            final Collection result = this.getDao().findServicoContratoByIdServico(idServico);
            if (result == null || result.size() == 0) {
                return "0";
            } else {
                return "1";
            }
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public ServicoContratoDTO findByIdServicoContrato(final Integer idServico, final Integer idContrato) throws Exception {
        return this.getDao().findByIdServicoContrato(idServico, idContrato);
    }

    @Override
    public boolean pesquisaServicosVinculados(final DocumentHTML document, final Map map, final HttpServletRequest request) throws Exception {
        final String idContratoStr = (String) map.get("IDCONTRATO");

        /*
         * Desenvolvedor: Rodrigo Pecci - Data: 03/11/2013 - Horário: 16h20min
         * Motivo/Comentário: Foi adicionada a validação para garantir que o id do contrato existe
         */
        if (idContratoStr == null || idContratoStr.equals("")) {
            document.alert(UtilI18N.internacionaliza(request, "dinamicview.nenhumRegistroSelecionado"));
            return false;
        }

        final Integer idContrato = Integer.parseInt(idContratoStr);

        final Collection<ServicoContratoDTO> colecaoServicosVinculados = this.getDao().findByIdContrato(idContrato);

        if (colecaoServicosVinculados == null || colecaoServicosVinculados.isEmpty()) {
            return true;
        } else {
            document.alert(UtilI18N.internacionaliza(request, "dinamicview.existem") + " " + colecaoServicosVinculados.size() + " "
                    + UtilI18N.internacionaliza(request, "dinamicview.servicosvinculados"));
            return false;
        }

    }

    @Override
    public ServicoContratoDTO findByIdServicoContrato(final Integer idServicoContrato) throws Exception {
        return this.getDao().findByIdServicoContrato(idServicoContrato);
    }

    @Override
    public boolean verificaSeExisteSolicitacaoAbertaVinculadoComServico(final Integer idServico, final Integer idContrato) throws Exception {
        return this.getDao().verificaSeExisteSolicitacaoAbertaVinculadoComServico(idServico, idContrato);
    }

    @Override
    public boolean verificaServicoEstaVinculadoContrato(final Integer idSolicitacaoServico) throws Exception {
        return this.getDao().verificaServicoEstaVinculadoContrato(idSolicitacaoServico);
    }

    @Override
    public ServicoContratoDTO findByIdSolicitacaoServico(final Integer idSolicitacaoServico) throws Exception {
        return this.getDao().findByIdSolicitacaoServico(idSolicitacaoServico);
    }

}
