package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.LimiteAlcadaDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.ContratoDao;
import br.com.centralit.citcorpore.integracao.ContratosGruposDAO;
import br.com.centralit.citcorpore.integracao.EventoGrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoDao;
import br.com.centralit.citcorpore.integracao.GrupoEmailDao;
import br.com.centralit.citcorpore.integracao.GrupoEmpregadoDao;
import br.com.centralit.citcorpore.integracao.LimiteAlcadaDao;
import br.com.centralit.citcorpore.integracao.NotificacaoGrupoDao;
import br.com.centralit.citcorpore.integracao.PerfilAcessoGrupoDao;
import br.com.centralit.citcorpore.integracao.PermissoesFluxoDao;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * @author CentralIT
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class GrupoServiceEjb extends CrudServiceImpl implements GrupoService {

    private GrupoDTO grupoBean;

    private GrupoDao dao;

    @Override
    protected GrupoDao getDao() {
        if (dao == null) {
            dao = new GrupoDao();
        }
        return dao;
    }

    @Override
    public Collection findGruposAtivos() {
        final List<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("dataFim", "IS", null));
        try {
            return this.getDao().findByCondition(condicoes, null);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public IDto create(final IDto model, final HttpServletRequest request) throws ServiceException, LogicException {
        GrupoDTO grupoDto = (GrupoDTO) model;

        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());

        try {
            this.validaCreate(model);
            final List<GrupoEmpregadoDTO> listaEmpregados = (List<GrupoEmpregadoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GrupoEmpregadoDTO.class,
                    "empregadosSerializados", request);

            final List<GrupoEmailDTO> listaEmails = (List<GrupoEmailDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GrupoEmailDTO.class,
                    "emailsSerializados", request);

            final PerfilAcessoGrupoDao perfilAcessogrupoDao = new PerfilAcessoGrupoDao();
            final GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
            final GrupoEmailDao grupoEmailDao = new GrupoEmailDao();
            final PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
            final ContratosGruposDAO contratosGruposDao = new ContratosGruposDAO();

            this.getDao().setTransactionControler(tc);
            perfilAcessogrupoDao.setTransactionControler(tc);
            grupoEmpregadoDao.setTransactionControler(tc);
            permissoesFluxoDao.setTransactionControler(tc);
            contratosGruposDao.setTransactionControler(tc);

            final PerfilAcessoGrupoDTO dto = new PerfilAcessoGrupoDTO();

            tc.start();

            grupoDto.setDataInicio(UtilDatas.getDataAtual());

            grupoDto = (GrupoDTO) this.getDao().create(grupoDto);

            dto.setIdGrupo(grupoDto.getIdGrupo());
            dto.setIdPerfilAcessoGrupo(grupoDto.getIdPerfilAcessoGrupo());
            dto.setDataInicio(grupoDto.getDataInicio());

            perfilAcessogrupoDao.create(dto);

            if (grupoDto.getPermissoesFluxos() != null) {
                for (final PermissoesFluxoDTO permissoesFluxoAux : grupoDto.getPermissoesFluxos()) {
                    permissoesFluxoAux.setIdGrupo(grupoDto.getIdGrupo());
                    final PermissoesFluxoDTO permissoesFluxoAux2 = (PermissoesFluxoDTO) permissoesFluxoDao.restore(permissoesFluxoAux);
                    if (permissoesFluxoAux2 == null) {
                        permissoesFluxoDao.create(permissoesFluxoAux);
                    } else {
                        permissoesFluxoDao.update(permissoesFluxoAux);
                    }
                }
            }

            if (listaEmpregados != null && !listaEmpregados.isEmpty()) {

                for (final GrupoEmpregadoDTO grupoEmpregadoDto : listaEmpregados) {
                    grupoEmpregadoDto.setIdGrupo(grupoDto.getIdGrupo());
                    grupoEmpregadoDto.setIdEmpregado(grupoEmpregadoDto.getIdEmpregado());
                    grupoEmpregadoDto.setEnviaEmail(grupoEmpregadoDto.getEnviaEmail());
                    final GrupoEmpregadoDTO grupoEmpregadoAux = (GrupoEmpregadoDTO) grupoEmpregadoDao.restore(grupoEmpregadoDto);
                    if (grupoEmpregadoAux == null) {
                        grupoEmpregadoDao.create(grupoEmpregadoDto);
                    }
                }
            }

            if (listaEmails != null && !listaEmails.isEmpty()) {

                for (final GrupoEmailDTO grupoEmailDto : listaEmails) {
                    grupoEmailDto.setIdGrupo(grupoDto.getIdGrupo());
                    final GrupoEmailDTO grupoEmailAux = (GrupoEmailDTO) grupoEmailDao.restore(grupoEmailDto);
                    if (grupoEmailAux == null) {
                        grupoEmailDao.create(grupoEmailDto);
                    }
                }
            }

            if (grupoDto.getIdContrato() != null) {

                for (int i = 0; i < grupoDto.getIdContrato().length; i++) {

                    final ContratosGruposDTO contratosGruposDTO = new ContratosGruposDTO();

                    contratosGruposDTO.setIdGrupo(grupoDto.getIdGrupo());
                    contratosGruposDTO.setIdContrato(grupoDto.getIdContrato()[i]);
                    if (contratosGruposDTO.getIdContrato() != null) {
                        contratosGruposDao.create(contratosGruposDTO);
                    }
                }
            }

            tc.commit();

        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        return grupoDto;
    }

    @Override
    public void update(final IDto model, final HttpServletRequest request) throws ServiceException, LogicException {
        final GrupoDTO grupoDto = (GrupoDTO) model;
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            this.validaUpdate(model);
            final List<GrupoEmailDTO> listaEmails = (ArrayList<GrupoEmailDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GrupoEmailDTO.class,
                    "emailsSerializados", request);
            final PerfilAcessoGrupoDao perfilAcessogrupoDao = new PerfilAcessoGrupoDao();
            final GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
            final PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
            final ContratosGruposDAO contratosGruposDao = new ContratosGruposDAO();
            final GrupoEmailDao grupoEmailDao = new GrupoEmailDao();

            this.getDao().setTransactionControler(tc);
            perfilAcessogrupoDao.setTransactionControler(tc);
            grupoEmpregadoDao.setTransactionControler(tc);
            grupoEmailDao.setTransactionControler(tc);
            permissoesFluxoDao.setTransactionControler(tc);
            contratosGruposDao.setTransactionControler(tc);

            final PerfilAcessoGrupoDTO dto = new PerfilAcessoGrupoDTO();

            tc.start();

            this.getDao().update(grupoDto);

            final GrupoDTO grupo = (GrupoDTO) this.getDao().restore(grupoDto);

            dto.setIdPerfilAcessoGrupo(grupoDto.getIdPerfilAcessoGrupo());
            dto.setIdGrupo(grupoDto.getIdGrupo());
            dto.setDataInicio(grupoDto.getDataInicio());

            perfilAcessogrupoDao.delete(dto);

            perfilAcessogrupoDao.create(dto);

            permissoesFluxoDao.deleteByIdGrupo(grupoDto.getIdGrupo());
            if (grupoDto.getPermissoesFluxos() != null) {
                for (final PermissoesFluxoDTO permissoesFluxoAux : grupoDto.getPermissoesFluxos()) {
                    permissoesFluxoAux.setIdGrupo(grupoDto.getIdGrupo());

                    permissoesFluxoDao.create(permissoesFluxoAux);
                }
            }

            if (listaEmails != null && !listaEmails.isEmpty()) {
                grupoEmailDao.deleteByIdGrupo(grupoDto.getIdGrupo());
                for (final GrupoEmailDTO grupoEmailDto : listaEmails) {
                    grupoEmailDto.setIdGrupo(grupo.getIdGrupo());

                    grupoEmailDao.create(grupoEmailDto);
                }
            }

            contratosGruposDao.deleteByIdGrupo(grupoDto.getIdGrupo());
            if (grupoDto.getIdContrato() != null) {
                for (int i = 0; i < grupoDto.getIdContrato().length; i++) {
                    final ContratosGruposDTO contratosGruposDTO = new ContratosGruposDTO();
                    contratosGruposDTO.setIdGrupo(grupoDto.getIdGrupo());
                    contratosGruposDTO.setIdContrato(grupoDto.getIdContrato()[i]);
                    if (contratosGruposDTO.getIdContrato() != null) {
                        contratosGruposDao.create(contratosGruposDTO);
                    }
                }
            }

            tc.commit();

        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    @Override
    public void delete(final IDto model, final DocumentHTML document) throws ServiceException, LogicException {
        final GrupoDTO grupoDto = (GrupoDTO) model;

        if (!this.validaExclusaoGrupoNosParametros(grupoDto, document)) {
            return;
        }

        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        try {
            Integer idGrupo = 0;
            idGrupo = grupoDto.getIdGrupo();
            final PerfilAcessoGrupoDao perfilAcessogrupoDao = new PerfilAcessoGrupoDao();
            final PerfilAcessoGrupoDTO dto = new PerfilAcessoGrupoDTO();
            final GrupoEmpregadoDao grupoEmpregadoDao = new GrupoEmpregadoDao();
            final GrupoEmailDao grupoEmailDao = new GrupoEmailDao();
            final PermissoesFluxoDao permissoesFluxoDao = new PermissoesFluxoDao();
            final ContratoDao contratoDao = new ContratoDao();
            final EventoGrupoDao eventoGrupoDao = new EventoGrupoDao();
            final NotificacaoGrupoDao notificacaoGrupoDao = new NotificacaoGrupoDao();
            final LimiteAlcadaDao limiteAlcadaDao = new LimiteAlcadaDao();
            final ServicoContratoDao servicoContratoDao = new ServicoContratoDao();

            this.getDao().setTransactionControler(tc);
            perfilAcessogrupoDao.setTransactionControler(tc);
            grupoEmpregadoDao.setTransactionControler(tc);
            permissoesFluxoDao.setTransactionControler(tc);
            contratoDao.setTransactionControler(tc);
            eventoGrupoDao.setTransactionControler(tc);
            notificacaoGrupoDao.setTransactionControler(tc);
            limiteAlcadaDao.setTransactionControler(tc);
            servicoContratoDao.setTransactionControler(tc);

            tc.start();

            final Collection<PermissoesFluxoDTO> permissaoGrupo = permissoesFluxoDao.findByIdGrupo(idGrupo);
            final Collection<GrupoEmailDTO> grupoDeEmail = grupoEmailDao.findByIdGrupo(idGrupo);
            final Collection<GrupoEmpregadoDTO> grupoDeEmpregados = grupoEmpregadoDao.findByIdGrupo(idGrupo);
            final Collection<ContratoDTO> contratoDTO = contratoDao.findByIdGrupo(idGrupo);
            final Collection<EventoGrupoDTO> eventoGrupoDTO = eventoGrupoDao.findByIdGrupo(idGrupo);
            final Collection<LimiteAlcadaDTO> limiteAlcadaDTO = limiteAlcadaDao.findByIdGrupo(idGrupo);
            final Collection<ServicoContratoDTO> colecaoServicosVinculados = servicoContratoDao.findAtivosByIdGrupo(grupoDto.getIdGrupo());

            if (eventoGrupoDTO != null) {
                document.alert(this.i18nMessage("grupo.deletar.eventoGrupo"));
                return;
            }

            if (limiteAlcadaDTO != null) {
                document.alert(this.i18nMessage("grupo.deletar.limiteAlcadaGrupo"));
                return;
            }

            if (contratoDTO != null) {
                document.alert(this.i18nMessage("grupo.deletar.grupoContrato"));
                return;
            }

            if (grupoDeEmpregados != null) {
                document.alert(this.i18nMessage("grupo.deletar.grupoEmpregado"));
                return;
            }

            if (grupoDeEmail != null) {
                document.alert(this.i18nMessage("grupo.deletar.grupoEmail"));
                return;
            }

            if (colecaoServicosVinculados != null) {
                document.alert(this.i18nMessage("grupo.deletar.servicosGrupo"));
                return;
            }

            if (permissaoGrupo != null) {
                for (final PermissoesFluxoDTO permissoesFluxo : permissaoGrupo) {
                    if (permissoesFluxo.getCriar().equalsIgnoreCase("S")) {
                        document.alert(this.i18nMessage("grupo.deletar.grupoFluxo"));
                        return;
                    }
                    if (permissoesFluxo.getDelegar().equalsIgnoreCase("S")) {
                        document.alert(this.i18nMessage("grupo.deletar.grupoFluxo"));
                        return;
                    }
                    if (permissoesFluxo.getExecutar().equalsIgnoreCase("S")) {
                        document.alert(this.i18nMessage("grupo.deletar.grupoFluxo"));
                        return;
                    }
                    if (permissoesFluxo.getSuspender().equalsIgnoreCase("S")) {
                        document.alert(this.i18nMessage("grupo.deletar.grupoFluxo"));
                        return;
                    }
                    permissoesFluxoDao.delete(permissoesFluxo);
                }
                grupoDto.setSigla(null);
                grupoDto.setDataFim(UtilDatas.getDataAtual());
                this.getDao().update(grupoDto);
                dto.setIdGrupo(grupoDto.getIdGrupo());
                perfilAcessogrupoDao.updateDataFim(dto);
                notificacaoGrupoDao.deleteByIdGrupo(idGrupo);
                document.alert(this.i18nMessage("MSG07"));
                document.executeScript("deleteAllRows();");
                final HTMLForm form = document.getForm("form");
                form.clear();
                document.executeScript("limpar_LOOKUP_GRUPO()");
            }

            tc.commit();

        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        } finally {
            try {
                tc.close();
            } catch (final PersistenceException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    /**
     * Retorna Bean de BaseItemConfiguracao.
     *
     * @return valor do atributo baseItemConfiguracaoBean.
     * @author valdoilo.damasceno
     */
    public GrupoDTO getGrupoDto() {
        return grupoBean;
    }

    /**
     * Retorna Service de AcessoMenuService.
     *
     * @return ValorService
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public PerfilAcessoGrupoService getPerfilAcessoGrupoService() throws ServiceException, Exception {
        return (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        final GrupoDTO grupoDto = (GrupoDTO) arg0;
        if (this.getDao().restoreBySigla(grupoDto.getSigla()) != null) {
            throw new LogicException("Atenção, Já existe um grupo com esta sigla digite uma diferente.");
        }
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        final GrupoDTO grupoDto = (GrupoDTO) arg0;
        if (this.getDao().restoreBySigla(grupoDto) != null) {
            throw new LogicException("Atenção, Já existe um grupo com esta sigla digite uma diferente.");
        }
    }

    @Override
    public Collection listaGrupoEmpregado() throws Exception {
        try {
            return this.getDao().listarGrupoEmpregado();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection listaGrupoUsuario() throws Exception {
        try {
            return this.getDao().listarGrupoUsuario();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection getGruposByPessoa(final Integer idEmpregado) throws LogicException, ServiceException {
        try {
            return this.getDao().getGruposByIdEmpregado(idEmpregado);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection getGruposByEmpregado(final Integer idEmpregado) throws LogicException, ServiceException {
        try {
            return this.getDao().getGruposByIdEmpregadoAll(idEmpregado);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<GrupoDTO> listGruposServiceDesk() throws Exception {
        return this.getDao().listGruposServiceDesk();
    }

    @Override
    public Collection<GrupoDTO> listGruposServiceDeskByIdContrato(final Integer idContratoParm) throws Exception {
        return this.getDao().listGruposServiceDeskByIdContrato(idContratoParm);
    }

    @Override
    public boolean verificarSeGrupoExiste(final GrupoDTO grupo) throws PersistenceException {
        return this.getDao().verificarSeGrupoExiste(grupo);
    }

    @Override
    public Collection<GrupoDTO> listGrupoByIdContrato(final Integer idContrato) throws Exception {
        return this.getDao().listGrupoByIdContrato(idContrato);
    }

    @Override
    public Collection<GrupoDTO> listGrupoAtivosByIdContrato(final Integer idContrato) throws Exception {
        return this.getDao().listGruposAtivosByIdContrato(idContrato);
    }

    @Override
    public Collection getGruposByIdEmpregado(final Integer idEmpregado) throws Exception {
        return this.getDao().getGruposByIdEmpregado(idEmpregado);
    }

    @Override
    public Collection<String> listarEmailsPorGrupo(final Integer idGrupo) throws Exception {
        return this.getDao().listarEmailsPorGrupo(idGrupo);
    }

    @Override
    public GrupoDTO listGrupoById(final Integer idGrupo) throws Exception {
        return this.getDao().listGrupoById(idGrupo);
    }

    @Override
    public Collection listarGruposAtivos() throws Exception {
        return this.getDao().listarGruposAtivos();
    }

    @Override
    public Collection<GrupoDTO> listGruposComite() throws Exception {
        return this.getDao().listGruposComite();
    }

    @Override
    public Collection<GrupoDTO> listGruposNaoComite() throws Exception {
        return this.getDao().listGruposNaoComite();
    }

    @Override
    public Collection<GrupoDTO> listAllGrupos() throws Exception {
        return this.getDao().listAllGrupos();
    }

    @Override
    public Collection<String> listarPessoasEmailPorGrupo(final Integer idGrupo) throws Exception {
        return this.getDao().listarPessoasEmailPorGrupo(idGrupo);
    }

    @Override
    public Collection<GrupoDTO> listaGruposAtivos() throws Exception {
        return this.getDao().listaGruposAtivos();
    }

    @Override
    public Collection<GrupoDTO> listaGrupoEmpregado(final Integer idEmpregado) throws Exception {
        return this.getDao().listaGrupoEmpregado(idEmpregado);
    }

    public Boolean validaExclusaoGrupoNosParametros(final GrupoDTO grupoDto, final DocumentHTML document) {
        final Enumerados.ParametroSistema[] parametrosArray = Enumerados.ParametroSistema.values();

        Integer idGrupo = 0;
        String nomeGrupo = "";

        // tratar para o parametro de id 45 - ldap, os ids podem ser passados com ;
        final String ldapStr = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.ID_GRUPO_PADRAO_LDAP, "0");
        final String[] ldapArray = ldapStr.split(";");
        for (final String element : ldapArray) {
            if (grupoDto.getIdGrupo().intValue() == Integer.parseInt(element)) {
                document.alert(this.i18nMessage("grupo.deletar.parametro") + " 45 " + this.i18nMessage("grupo.deletar.parametroContinuacao"));
                return false;
            }
        }

        // tratar para todos os parametros do Enumerados
        for (final ParametroSistema element : parametrosArray) {
            final String nomeParametro = element.name();
            if (nomeParametro.contains("ID_GRUPO_PADRAO")) {
                try {
                    if (ParametroUtil.getValor(element) != null) {
                        idGrupo = Integer.parseInt(ParametroUtil.getValor(element));
                    }
                    if (idGrupo.intValue() == grupoDto.getIdGrupo().intValue()) {
                        document.alert(this.i18nMessage("grupo.deletar.parametro") + " " + element.id() + " " + this.i18nMessage("grupo.deletar.parametroContinuacao"));
                        return false;
                    }

                } catch (final Exception e) {
                    System.out.println("Parametro do sistema de ID " + element.id() + " null ou fora do padrão numérico");
                }
            } else if (nomeParametro.contains("NOME_GRUPO_PADRAO")) {
                try {
                    if (ParametroUtil.getValor(element) != null) {
                        nomeGrupo = ParametroUtil.getValor(element);
                    }
                    if (nomeGrupo.equalsIgnoreCase(grupoDto.getNome())) {
                        document.alert(this.i18nMessage("grupo.deletar.parametro") + " " + element.id() + " " + this.i18nMessage("grupo.deletar.parametroContinuacao"));
                        return false;
                    }
                } catch (final Exception e) {
                    System.out.println("Parametro do sistema de ID " + element.id() + " null");
                }

            }
        }
        return true;
    }

    @Override
    public Collection<GrupoDTO> listGruposPorUsuario(final int idUsuario) {
        Collection<GrupoDTO> list = new ArrayList();
        try {
            list = this.getDao().listGruposPorUsuario(idUsuario);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
