/**
 * CentralIT - CITSmart.
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.CaracteristicaTipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.CaracteristicaTipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.TipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

/**
 * EJB de Tipo Item Configuração.
 *
 * @author valdoilo.damasceno
 */
public class TipoItemConfiguracaoServiceEjb extends CrudServiceImpl implements TipoItemConfiguracaoService {

    private TipoItemConfiguracaoDAO dao;

    @Override
    protected TipoItemConfiguracaoDAO getDao() {
        if (dao == null) {
            dao = new TipoItemConfiguracaoDAO();
        }
        return dao;
    }

    private CaracteristicaTipoItemConfiguracaoDAO caracteristicaTipoItemConfiguracaoDao;

    private TipoItemConfiguracaoDTO tipoItemConfiguracaoBean;

    /*
     * (non-Javadoc)
     * @see br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService#create (br.com.citframework.dto.IDto, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public IDto create(final IDto tipoItemConfiguracao, final HttpServletRequest request) throws ServiceException, LogicException {
        this.setTipoItemConfiguracaoBean(tipoItemConfiguracao);

        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            this.validaCreate(this.getTipoItemConfiguracaoBean());
            this.getDao().setTransactionControler(tc);
            this.getCaracteristicaTipoItemConfiguracaoDao().setTransactionControler(tc);

            tc.start();

            this.getTipoItemConfiguracaoBean().setDataInicio(UtilDatas.getDataAtual());
            this.getTipoItemConfiguracaoBean().setIdEmpresa(WebUtil.getIdEmpresa(request));
            this.getDao().create(tipoItemConfiguracao);

            this.criarEAssociarCaracteristicaAoTipoItemConfiguracao();

            tc.commit();
            tc.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        }
        return this.getTipoItemConfiguracaoBean();
    }

    /*
     * (non-Javadoc)
     * @see br.com.citframework.service.CrudServicePojoImpl#update(br.com.citframework .dto.IDto)
     */
    @Override
    public void update(final IDto tipoItemConfiguracao) throws ServiceException, LogicException {
        this.setTipoItemConfiguracaoBean(tipoItemConfiguracao);
        try {
            this.criarEAssociarCaracteristicaAoTipoItemConfiguracao();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        super.update(this.getTipoItemConfiguracaoBean());
    }

    /**
     * Associa Característica ao Tipo Item Configuração.
     *
     * @throws Exception
     * @author valdoilo.damasceno
     */
    private void criarEAssociarCaracteristicaAoTipoItemConfiguracao() throws Exception {
        if (this.getTipoItemConfiguracaoBean().getCaracteristicas() != null && !this.getTipoItemConfiguracaoBean().getCaracteristicas().isEmpty()) {
            for (int i = 0; i < this.getTipoItemConfiguracaoBean().getCaracteristicas().size(); i++) {
                final Integer idCaracteristica = ((CaracteristicaDTO) this.getTipoItemConfiguracaoBean().getCaracteristicas().get(i)).getIdCaracteristica();

                final CaracteristicaTipoItemConfiguracaoDTO caracteristicaTipoItemConfiguracaoBean = new CaracteristicaTipoItemConfiguracaoDTO();

                if (!this.getCaracteristicaTipoItemConfiguracaoDao().existeAssociacaoComCaracteristica(idCaracteristica, this.getTipoItemConfiguracaoBean().getId())) {
                    caracteristicaTipoItemConfiguracaoBean.setIdTipoItemConfiguracao(this.getTipoItemConfiguracaoBean().getId());
                    caracteristicaTipoItemConfiguracaoBean.setIdCaracteristica(idCaracteristica);
                    caracteristicaTipoItemConfiguracaoBean.setDataInicio(UtilDatas.getDataAtual());
                    this.getCaracteristicaTipoItemConfiguracaoDao().create(caracteristicaTipoItemConfiguracaoBean);
                    // throw new LogicException("teste");
                }
            }
        }
    }

    @Override
    public void restaurarGridCaracteristicas(final DocumentHTML document, final Collection<CaracteristicaDTO> caracteristicas) {
        if (caracteristicas != null && !caracteristicas.isEmpty()) {
            int count = 0;
            document.executeScript("countCaracteristica = 0");
            for (final CaracteristicaDTO caracteristicaBean : caracteristicas) {
                count++;

                document.executeScript("restoreRow()");
                document.executeScript("seqSelecionada = " + count);

                String caracteristica = caracteristicaBean.getNome() != null ? caracteristicaBean.getNome() : "";
                String tag = caracteristicaBean.getTag() != null ? caracteristicaBean.getTag() : "";
                String valor = caracteristicaBean.getValorString() != null ? caracteristicaBean.getValorString() : "";
                String descricao = caracteristicaBean.getDescricao() != null ? caracteristicaBean.getDescricao() : "";
                final String idEmpresa = caracteristicaBean.getIdEmpresa().toString() != null ? caracteristicaBean.getIdEmpresa().toString() : "";
                final String dataInicio = caracteristicaBean.getDataInicio().toString() != null ? caracteristicaBean.getDataInicio().toString() : "";
                final String dataFim = caracteristicaBean.getDataFim() != null ? caracteristicaBean.getDataFim().toString() : "";

                if (caracteristica != null) {
                    caracteristica = caracteristica.replaceAll("'", "");
                }
                if (tag != null) {
                    tag = tag.replaceAll("'", "");
                }
                if (valor != null) {
                    valor = valor.replaceAll("'", "");
                }
                if (descricao != null) {
                    descricao = descricao.replaceAll("'", "");
                }

                document.executeScript("setRestoreCaracteristica('" + caracteristicaBean.getIdCaracteristica() + "'," + "'"
                        + br.com.citframework.util.WebUtil.codificaEnter(caracteristica) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(tag) + "'," + "'"
                        + br.com.citframework.util.WebUtil.codificaEnter(StringEscapeUtils.escapeJavaScript(valor)) + "'," + "'"
                        + br.com.citframework.util.WebUtil.codificaEnter(descricao) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(idEmpresa) + "'," + "'"
                        + br.com.citframework.util.WebUtil.codificaEnter(dataInicio) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(dataFim) + "')");
            }
            document.executeScript("exibeGrid()");
        } else {
            document.executeScript("ocultaGrid()");
        }
    }

    @Override
    public void restaurarGridCaracteristicasItemConfiguracaoFilho(final DocumentHTML document, final Collection<CaracteristicaDTO> caracteristicas) {
        if (caracteristicas != null && !caracteristicas.isEmpty()) {
            int count = 0;
            document.executeScript("countCaracteristica = 0");
            for (final CaracteristicaDTO caracteristicaBean : caracteristicas) {
                count++;

                document.executeScript("restoreRowCaracteristicaItemFilho()");
                document.executeScript("seqSelecionada = " + count);

                String caracteristica = caracteristicaBean.getNome() != null ? caracteristicaBean.getNome() : "";
                String tag = caracteristicaBean.getTag() != null ? caracteristicaBean.getTag() : "";
                String valor = caracteristicaBean.getValorString() != null ? caracteristicaBean.getValorString() : "";
                String descricao = caracteristicaBean.getDescricao() != null ? caracteristicaBean.getDescricao() : "";
                final String idEmpresa = caracteristicaBean.getIdEmpresa().toString() != null ? caracteristicaBean.getIdEmpresa().toString() : "";
                final String dataInicio = caracteristicaBean.getDataInicio().toString() != null ? caracteristicaBean.getDataInicio().toString() : "";
                final String dataFim = caracteristicaBean.getDataFim() != null ? caracteristicaBean.getDataFim().toString() : "";

                if (caracteristica != null) {
                    caracteristica = caracteristica.replaceAll("'", "");
                }
                if (tag != null) {
                    tag = tag.replaceAll("'", "");
                }
                if (valor != null) {
                    valor = valor.replaceAll("'", "");
                }
                if (descricao != null) {
                    descricao = descricao.replaceAll("'", "");
                }

                document.executeScript("setRestoreCaracteristicaItemConfiguracaoFilho('" + caracteristicaBean.getIdCaracteristica() + "'," + "'"
                        + br.com.citframework.util.WebUtil.codificaEnter(caracteristica) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(tag) + "'," + "'"
                        + br.com.citframework.util.WebUtil.codificaEnter(valor) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(descricao) + "'," + "'"
                        + br.com.citframework.util.WebUtil.codificaEnter(idEmpresa) + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(dataInicio) + "'," + "'"
                        + br.com.citframework.util.WebUtil.codificaEnter(dataFim) + "')");
            }
            document.executeScript("exibeGridPatrimonioItemFilho()");
        } else {
            document.executeScript("ocultaGridPatrimonioItemFilho()");
        }
    }

    /**
     * @return valor do atributo caracteristicaTipoItemConfiguracaoDao.
     * @author valdoilo.damasceno
     */
    private CaracteristicaTipoItemConfiguracaoDAO getCaracteristicaTipoItemConfiguracaoDao() {
        if (caracteristicaTipoItemConfiguracaoDao == null) {
            caracteristicaTipoItemConfiguracaoDao = new CaracteristicaTipoItemConfiguracaoDAO();
        }
        return caracteristicaTipoItemConfiguracaoDao;
    }

    /**
     * Retorna Tipo Item Configuração.
     *
     * @return TipoItemConfiguracaoDTO
     * @author VMD
     */
    private TipoItemConfiguracaoDTO getTipoItemConfiguracaoBean() {
        return tipoItemConfiguracaoBean;
    }

    /**
     * Configura Tipo Item Configuração.
     *
     * @param tipoItemConfiguracao
     *            IDto
     * @author VMD
     */
    private void setTipoItemConfiguracaoBean(final IDto tipoItemConfiguracao) {
        tipoItemConfiguracaoBean = (TipoItemConfiguracaoDTO) tipoItemConfiguracao;
    }

    @Override
    public boolean verificarSeTipoItemConfiguracaoExiste(final TipoItemConfiguracaoDTO tipoItemConfiguracao) throws PersistenceException {
        final TipoItemConfiguracaoDAO tipoItemConfiguracaoDao = new TipoItemConfiguracaoDAO();
        return tipoItemConfiguracaoDao.verificarSeTipoItemConfiguracaoExiste(tipoItemConfiguracao);
    }

    @Override
    public boolean verificaAssociacaoItemConfiguracao(final TipoItemConfiguracaoDTO tipoItemConfiguracao) throws PersistenceException {
        final TipoItemConfiguracaoDAO tipoItemConfiguracaoDAO = new TipoItemConfiguracaoDAO();
        return tipoItemConfiguracaoDAO.verificaAssociacaoTipoConfiguracao(tipoItemConfiguracao);
    }

}
