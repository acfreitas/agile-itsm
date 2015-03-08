package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.sql.Date;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.ExecucaoAtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.integracao.AnexoDao;
import br.com.centralit.citcorpore.integracao.ExecucaoAtividadePeriodicaDao;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class ExecucaoAtividadePeriodicaServiceEjb extends CrudServiceImpl implements ExecucaoAtividadePeriodicaService {

    private ExecucaoAtividadePeriodicaDao dao;

    @Override
    protected ExecucaoAtividadePeriodicaDao getDao() {
        if (dao == null) {
            dao = new ExecucaoAtividadePeriodicaDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        final ExecucaoAtividadePeriodicaDTO execucaoAtividadePeriodicaDto = (ExecucaoAtividadePeriodicaDTO) arg0;
        if (!execucaoAtividadePeriodicaDto.getSituacao().equals("S")) {
            execucaoAtividadePeriodicaDto.setIdMotivoSuspensao(null);
            execucaoAtividadePeriodicaDto.setComplementoMotivoSuspensao(null);
        }
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        this.validaCreate(arg0);
    }

    @Override
    public Collection findByIdAtividadePeriodica(final Integer idAtividadePeriodicaParm) throws Exception {
        try {
            return this.getDao().findByIdAtividadePeriodica(idAtividadePeriodicaParm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdAtividadePeriodica(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdAtividadePeriodica(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdEmpregado(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdEmpregado(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final AnexoDao anexoDao = new AnexoDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            anexoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            model = crudDao.create(model);
            final ExecucaoAtividadePeriodicaDTO execucaoAtividadeDto = (ExecucaoAtividadePeriodicaDTO) model;

            this.gravaInformacoesGED(execucaoAtividadeDto.getColArquivosUpload(), 1, execucaoAtividadeDto);

            // Faz commit e fecha a transacao.
            tc.commit();

            return model;
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
        return model;
    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {
        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final AnexoDao anexoDao = new AnexoDao();
        final TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            // Faz validacao, caso exista.
            this.validaUpdate(model);

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            anexoDao.setTransactionControler(tc);

            // Inicia transacao
            tc.start();

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(model);
            final ExecucaoAtividadePeriodicaDTO execucaoAtividadeDto = (ExecucaoAtividadePeriodicaDTO) model;
            this.gravaInformacoesGED(execucaoAtividadeDto.getColArquivosUpload(), 1, execucaoAtividadeDto);

            // Faz commit e fecha a transacao.
            tc.commit();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
            tc.closeQuietly();
        }
    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {
        try {
            final IDto obj = this.getDao().restore(model);
            final ExecucaoAtividadePeriodicaDTO execucaoAtividadeDto = (ExecucaoAtividadePeriodicaDTO) obj;
            final ControleGEDDao controleGedDao = new ControleGEDDao();
            final Collection col = controleGedDao.listByIdTabelaAndID(ControleGEDDTO.TABELA_EXECUCAOATIVIDADE, execucaoAtividadeDto.getIdExecucaoAtividadePeriodica());
            execucaoAtividadeDto.setColArquivosUpload(col);
            return obj;
        } catch (final LogicException e) {
            throw new ServiceException(e);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    public void gravaInformacoesGED(final Collection colArquivosUpload, final int idEmpresa, final ExecucaoAtividadePeriodicaDTO execucaoAtividadeDto) throws Exception {
        String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "");
        if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.trim().equalsIgnoreCase("")) {
            PRONTUARIO_GED_DIRETORIO = "";
        }

        if (PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
            PRONTUARIO_GED_DIRETORIO = Constantes.getValue("DIRETORIO_GED");
        }

        if (PRONTUARIO_GED_DIRETORIO == null || PRONTUARIO_GED_DIRETORIO.equalsIgnoreCase("")) {
            PRONTUARIO_GED_DIRETORIO = "/ged";
        }
        String PRONTUARIO_GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");
        if (PRONTUARIO_GED_INTERNO == null) {
            PRONTUARIO_GED_INTERNO = "S";
        }
        String prontuarioGedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");
        if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados)) {
            prontuarioGedInternoBancoDados = "N";
        }
        final ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        String pasta = "";
        if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
            pasta = controleGEDService.getProximaPastaArmazenar();
            File fileDir = new File(PRONTUARIO_GED_DIRETORIO);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            fileDir = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }
        for (final Iterator it = colArquivosUpload.iterator(); it.hasNext();) {
            final UploadDTO uploadDTO = (UploadDTO) it.next();
            if (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se nao for temporario
                continue;
            }
            ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
            controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_EXECUCAOATIVIDADE);
            controleGEDDTO.setId(execucaoAtividadeDto.getIdExecucaoAtividadePeriodica());
            controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
            controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
            controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
            controleGEDDTO.setPasta(pasta);
            controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());

            if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se utiliza GED
                // interno e eh BD
                controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso vai fazer a gravacao no BD. dento do create abaixo.
            } else {
                controleGEDDTO.setPathArquivo(null);
            }

            controleGEDDTO = controleGEDService.create(controleGEDDTO);
            if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se utiliza GED
                // interno e nao eh BD
                if (controleGEDDTO != null) {
                    final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                            + Util.getFileExtension(uploadDTO.getNameFile()));
                    CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged",
                            System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));

                    arquivo.delete();
                }
            } /*
               * else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se
               * // utiliza
               * // GED
               * // externo
               * // FALTA IMPLEMENTAR!!!
               * }
               */
        }
    }

    @Override
    public Collection findBlackoutByIdMudancaAndPeriodo(final Integer idMudanca, final Date dataInicio, final Date dataFim) throws Exception {
        return this.getDao().findBlackoutByIdMudancaAndPeriodo(idMudanca, dataInicio, dataFim);
    }

}
