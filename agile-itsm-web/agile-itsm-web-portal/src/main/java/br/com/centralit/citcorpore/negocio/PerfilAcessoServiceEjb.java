/** CentralIT - CITSmart. */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoSituacaoFaturaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoSituacaoOSDTO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoGrupoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoMenuDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoPastaDAO;
import br.com.centralit.citcorpore.integracao.PerfilAcessoSituacaoFaturaDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoSituacaoOSDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoUsuarioDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * EJB de PerfilAcesso.
 *
 * @author thays.araujo
 *
 */
@SuppressWarnings("unchecked")
public class PerfilAcessoServiceEjb extends CrudServiceImpl implements PerfilAcessoService {

    private PerfilAcessoDTO perfilAcessoBean;
    private PerfilAcessoDao dao;

    @Override
    protected PerfilAcessoDao getDao() {
        if (dao == null) {
            dao = new PerfilAcessoDao();
        }
        return dao;
    }

    private Collection<PerfilAcessoMenuDTO> acessoMenuDto;

    @Override
    public void gerarGridPerfilAcesso(final DocumentHTML document, final Collection<PerfilAcessoDTO> perfisDeAcessoDaPasta) throws Exception {
        final Collection<PerfilAcessoDTO> todosOsPerfisDeAcesso = this.getDao().consultarPerfisDeAcessoAtivos();
        if (todosOsPerfisDeAcesso != null && !todosOsPerfisDeAcesso.isEmpty()) {
            int count = 0;
            document.executeScript("count = 0");
            for (final PerfilAcessoDTO perfil : todosOsPerfisDeAcesso) {
                count++;

                document.executeScript("restoreRow()");
                document.executeScript("seqSelecionada = " + count);

                String perfilAcesso = perfil.getNomePerfilAcesso() != null ? perfil.getNomePerfilAcesso() : "";
                final String aprovaBaseConhecimento = perfil.getAprovaBaseConhecimento() != null ? perfil.getAprovaBaseConhecimento() : "S";

                if (perfilAcesso != null) {
                    perfilAcesso = perfilAcesso.replaceAll("'", "");
                }

                document.executeScript("setRestorePerfilAcesso('" + perfil.getIdPerfilAcesso() + "'," + "'" + br.com.citframework.util.WebUtil.codificaEnter(perfilAcesso) + "',"
                        + "'" + br.com.citframework.util.WebUtil.codificaEnter(aprovaBaseConhecimento) + "')");

                if (perfisDeAcessoDaPasta != null && !perfisDeAcessoDaPasta.isEmpty()) {
                    for (final PerfilAcessoDTO perfilAcessoPasta : perfisDeAcessoDaPasta) {
                        document.executeScript("atribuirChecked('" + perfilAcessoPasta.getIdPerfilAcesso() + "'," + "'"
                                + br.com.citframework.util.WebUtil.codificaEnter(perfilAcessoPasta.getAprovaBaseConhecimento()) + "')");

                    }
                }
            }
            document.executeScript("exibeGrid()");
        } else {
            document.executeScript("ocultaGrid()");
        }
    }

    @Override
    public Collection<PerfilAcessoDTO> consultarPerfisDeAcesso(final PastaDTO pastaBean) throws ServiceException, Exception {

        if (pastaBean == null) {

            return this.getDao().consultarPerfisDeAcessoAtivos();

        } else {

            return this.getDao().consultarPerfisDeAcessoAtivos(pastaBean);
        }
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        this.setPerfilAcessoBean(model);

        final TransactionControler transactionControler = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaCreate(this.getPerfilAcessoBean());

            this.getDao().setTransactionControler(transactionControler);
            final PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
            final PerfilAcessoSituacaoOSDao perfilAcessoSituacaoOSDao = new PerfilAcessoSituacaoOSDao();
            final PerfilAcessoSituacaoFaturaDao perfilAcessoSituacaoFaturaDao = new PerfilAcessoSituacaoFaturaDao();
            perfilAcessoMenuDao.setTransactionControler(transactionControler);
            perfilAcessoSituacaoOSDao.setTransactionControler(transactionControler);
            perfilAcessoSituacaoFaturaDao.setTransactionControler(transactionControler);

            transactionControler.start();

            this.getPerfilAcessoBean().setDataInicio(UtilDatas.getDataAtual());
            this.setPerfilAcessoBean(this.getDao().create(this.getPerfilAcessoBean()));
            for (final PerfilAcessoMenuDTO dto : this.getPerfilAcessoBean().getAcessoMenus()) {
                dto.setIdPerfilAcesso(this.getPerfilAcessoBean().getIdPerfilAcesso());
                dto.setDataInicio(UtilDatas.getDataAtual());
                perfilAcessoMenuDao.create(dto);
            }
            if (this.getPerfilAcessoBean().getSituacaoos() != null) {
                for (int i = 0; i < this.getPerfilAcessoBean().getSituacaoos().length; i++) {
                    final PerfilAcessoSituacaoOSDTO perfilAcessoSituacaoOSDTO = new PerfilAcessoSituacaoOSDTO();
                    perfilAcessoSituacaoOSDTO.setIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
                    perfilAcessoSituacaoOSDTO.setSituacaoOs(this.getPerfilAcessoBean().getSituacaoos()[i]);
                    perfilAcessoSituacaoOSDTO.setDataInicio(UtilDatas.getDataAtual());
                    perfilAcessoSituacaoOSDao.create(perfilAcessoSituacaoOSDTO);
                }
            }
            if (this.getPerfilAcessoBean().getSituacaoFatura() != null) {
                for (int i = 0; i < this.getPerfilAcessoBean().getSituacaoFatura().length; i++) {
                    final PerfilAcessoSituacaoFaturaDTO perfilAcessoSituacaoFaturaDTO = new PerfilAcessoSituacaoFaturaDTO();
                    perfilAcessoSituacaoFaturaDTO.setIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
                    perfilAcessoSituacaoFaturaDTO.setSituacaoFatura(this.getPerfilAcessoBean().getSituacaoFatura()[i]);
                    perfilAcessoSituacaoFaturaDTO.setDataInicio(UtilDatas.getDataAtual());
                    perfilAcessoSituacaoFaturaDao.create(perfilAcessoSituacaoFaturaDTO);
                }
            }

            transactionControler.commit();
            transactionControler.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transactionControler, e);
        }
        return this.getPerfilAcessoBean();
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        this.setPerfilAcessoBean(model);
        final PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
        final PerfilAcessoDao perfilAcessoDao = this.getDao();
        final PerfilAcessoSituacaoOSDao perfilAcessoSituacaoOSDao = new PerfilAcessoSituacaoOSDao();
        final PerfilAcessoSituacaoFaturaDao perfilAcessoSituacaoFaturaDao = new PerfilAcessoSituacaoFaturaDao();
        final TransactionControler transactionControler = new TransactionControlerImpl(perfilAcessoDao.getAliasDB());

        try {
            this.validaCreate(this.getPerfilAcessoBean());
            perfilAcessoDao.setTransactionControler(transactionControler);
            perfilAcessoMenuDao.setTransactionControler(transactionControler);
            perfilAcessoSituacaoOSDao.setTransactionControler(transactionControler);
            perfilAcessoSituacaoFaturaDao.setTransactionControler(transactionControler);
            transactionControler.start();

            this.getPerfilAcessoBean().setDataInicio(UtilDatas.getDataAtual());
            perfilAcessoDao.update(this.getPerfilAcessoBean());

            final List<PerfilAcessoMenuDTO> perfisAcessoMenu = (List<PerfilAcessoMenuDTO>) perfilAcessoMenuDao.findByPerfilAcesso(this.getPerfilAcessoBean().getIdPerfilAcesso());

            if (perfisAcessoMenu != null) {
                for (final PerfilAcessoMenuDTO perfilAcessoMenu : perfisAcessoMenu) {
                    perfilAcessoMenuDao.delete(perfilAcessoMenu);
                }
            }

            if (this.getPerfilAcessoBean().getAcessoMenus() != null) {
                for (final PerfilAcessoMenuDTO dto : this.getPerfilAcessoBean().getAcessoMenus()) {
                    dto.setIdPerfilAcesso(this.getPerfilAcessoBean().getIdPerfilAcesso());
                    dto.setDataInicio(UtilDatas.getDataAtual());
                    perfilAcessoMenuDao.create(dto);
                }
            }

            perfilAcessoSituacaoOSDao.deleteByIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
            if (this.getPerfilAcessoBean().getSituacaoos() != null) {
                for (int i = 0; i < this.getPerfilAcessoBean().getSituacaoos().length; i++) {
                    final PerfilAcessoSituacaoOSDTO perfilAcessoSituacaoOSDTO = new PerfilAcessoSituacaoOSDTO();
                    perfilAcessoSituacaoOSDTO.setIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
                    perfilAcessoSituacaoOSDTO.setSituacaoOs(this.getPerfilAcessoBean().getSituacaoos()[i]);
                    perfilAcessoSituacaoOSDTO.setDataInicio(UtilDatas.getDataAtual());
                    perfilAcessoSituacaoOSDao.create(perfilAcessoSituacaoOSDTO);
                }
            }
            perfilAcessoSituacaoFaturaDao.deleteByIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
            if (this.getPerfilAcessoBean().getSituacaoFatura() != null) {
                for (int i = 0; i < this.getPerfilAcessoBean().getSituacaoFatura().length; i++) {
                    final PerfilAcessoSituacaoFaturaDTO perfilAcessoSituacaoFaturaDTO = new PerfilAcessoSituacaoFaturaDTO();
                    perfilAcessoSituacaoFaturaDTO.setIdPerfil(this.getPerfilAcessoBean().getIdPerfilAcesso());
                    perfilAcessoSituacaoFaturaDTO.setSituacaoFatura(this.getPerfilAcessoBean().getSituacaoFatura()[i]);
                    perfilAcessoSituacaoFaturaDTO.setDataInicio(UtilDatas.getDataAtual());
                    perfilAcessoSituacaoFaturaDao.create(perfilAcessoSituacaoFaturaDTO);
                }
            }

            transactionControler.commit();
            transactionControler.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(transactionControler, e);
        }
    }

    @Override
    public boolean excluirPerfilDeAcesso(final PerfilAcessoDTO perfilDeAcesso) throws Exception {
        this.setPerfilAcessoBean(perfilDeAcesso);

        final PerfilAcessoDao perfilAcessoDao = this.getDao();
        final PerfilAcessoMenuDao perfilAcessoMenuDao = new PerfilAcessoMenuDao();
        final PerfilAcessoPastaDAO perfilAcessoPastaDao = new PerfilAcessoPastaDAO();
        final PerfilAcessoUsuarioDAO perfilAcessoUsuarioDao = new PerfilAcessoUsuarioDAO();
        final PerfilAcessoGrupoDao perfilAcessoGrupoDao = new PerfilAcessoGrupoDao();

        if (perfilAcessoGrupoDao.findByIdPerfil(perfilDeAcesso.getIdPerfilAcesso()) != null || perfilAcessoUsuarioDao.findByIdPerfil(perfilDeAcesso.getIdPerfilAcesso()) != null
                || perfilAcessoPastaDao.findByIdPerfil(perfilDeAcesso.getIdPerfilAcesso()) != null) {

            return false;

        } else {

            final TransactionControler transaction = new TransactionControlerImpl(this.getDao().getAliasDB());
            perfilAcessoDao.setTransactionControler(transaction);
            perfilAcessoMenuDao.setTransactionControler(transaction);

            try {
                transaction.start();

                this.getPerfilAcessoBean().setDataFim(UtilDatas.getDataAtual());
                perfilAcessoDao.update(this.getPerfilAcessoBean());

                perfilAcessoMenuDao.excluirPerfisAcessoMenu(this.getPerfilAcessoBean());

                transaction.commit();
                transaction.close();

                return true;
            } catch (final Exception e) {
                e.printStackTrace();
                this.rollbackTransaction(transaction, e);
                return false;
            }
        }
    }

    /**
     * Configura Bean de PerfilAcesso.
     *
     * @param baseItemConfiguracaoBean
     * @author valdoilo.damasceno
     */
    private void setPerfilAcessoBean(final IDto perfilAcessobean) {
        perfilAcessoBean = (PerfilAcessoDTO) perfilAcessobean;
    }

    /**
     * Retorna Bean de BaseItemConfiguracao.
     *
     * @return valor do atributo baseItemConfiguracaoBean.
     * @author valdoilo.damasceno
     */
    public PerfilAcessoDTO getPerfilAcessoBean() {
        return perfilAcessoBean;
    }

    /**
     * Retorna Service de AcessoMenuService.
     *
     * @return ValorService
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public PerfilAcessoMenuService getAcessoMenuService() throws ServiceException, Exception {
        return (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
    }

    /**
     * @return valor do atributo perfisDeAcesso.
     */
    public Collection<PerfilAcessoMenuDTO> getAcessoMenu() {
        return acessoMenuDto;
    }

    /**
     * Define valor do atributo perfisDeAcesso.
     *
     * @param perfisDeAcesso
     */
    public void setAcessoMenu(final Collection<PerfilAcessoMenuDTO> acessoMenu) {
        acessoMenuDto = acessoMenu;
    }

    @Override
    public PerfilAcessoDTO listByName(final PerfilAcessoDTO obj) throws Exception {
        try {
            return this.getDao().listByName(obj);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean verificarSePerfilAcessoExiste(final PerfilAcessoDTO perfilAcesso) throws PersistenceException {
        return this.getDao().verificarSePerfilAcessoExiste(perfilAcesso);
    }

    @Override
    public Collection<PerfilAcessoDTO> consultarPerfisDeAcessoAtivos() throws Exception {
        return this.getDao().consultarPerfisDeAcessoAtivos();
    }

    @Override
    public PerfilAcessoDTO findByIdPerfilAcesso(final PerfilAcessoDTO perfilAcessoDTO) throws Exception {
        return this.getDao().findByIdPerfilAcesso(perfilAcessoDTO);
    }

    @Override
    public String getAcessoCitsmartByUsuario(final Integer idUsuario) throws PersistenceException, ServiceException {
        final String retorno = this.getDao().getAcessoCitsmartByUsuario(idUsuario);
        if (retorno != null && !retorno.isEmpty()) {
            return retorno;
        } else {
            return "N";
        }
    }

}
