package br.com.centralit.citcorpore.negocio;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.integracao.InfoCatalogoServicoDao;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class InfoCatalogoServicoServiceEjb extends CrudServiceImpl implements InfoCatalogoServicoService {

    private InfoCatalogoServicoDao dao;

    @Override
    protected InfoCatalogoServicoDao getDao() {
        if (dao == null) {
            dao = new InfoCatalogoServicoDao();
        }
        return dao;
    }

    @Override
    public Collection<InfoCatalogoServicoDTO> findByCatalogoServico(final Integer idCatalogoServico) throws ServiceException, Exception {
        return this.getDao().findByCatalogoServico(idCatalogoServico);
    }

    @Override
    public boolean findByContratoServico(final Integer idContratoServico) throws ServiceException, Exception {
        return this.getDao().findByContratoServico(idContratoServico);
    }

    @Override
    public InfoCatalogoServicoDTO findById(final Integer idInfoCatalogo) throws ServiceException, Exception {
        return this.getDao().findById(idInfoCatalogo);
    }

    @Override
    public void gravaInformacoesGED(final Collection colArquivosUpload, final Integer idEmpresa, final InfoCatalogoServicoDTO infoCatalogoServicoDTO, final TransactionControler tc)
            throws Exception {

        // Setando a transaction no GED
        final ControleGEDDao controleGEDDao = new ControleGEDDao();
        if (tc != null) {
            controleGEDDao.setTransactionControler(tc);
        }

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
        String pasta = "";
        if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
            pasta = controleGEDDao.getProximaPastaArmazenar();
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
            ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
            controleGEDDTO.setIdTabela(ControleGEDDTO.TABELA_SOLICITACAOSERVICO);
            controleGEDDTO.setId(infoCatalogoServicoDTO.getIdInfoCatalogoServico());
            controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
            controleGEDDTO.setDescricaoArquivo(uploadDTO.getDescricao());
            controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDTO.getNameFile()));
            controleGEDDTO.setPasta(pasta);
            controleGEDDTO.setNomeArquivo(uploadDTO.getNameFile());

            if (!uploadDTO.getTemporario().equalsIgnoreCase("S")) { // Se nao //
                                                                    // for //
                                                                    // temporario
                continue;
            }

            if (PRONTUARIO_GED_INTERNO.trim().equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados.trim())) { // Se
                // utiliza
                // GED
                // interno e eh BD
                controleGEDDTO.setPathArquivo(uploadDTO.getPath()); // Isso vai
                                                                    // fazer a
                                                                    // gravacao
                                                                    // no BD.
                                                                    // dento do
                                                                    // create
                                                                    // abaixo.
            } else {
                controleGEDDTO.setPathArquivo(null);
            }
            controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
            if (controleGEDDTO != null) {
                uploadDTO.setIdControleGED(controleGEDDTO.getIdControleGED());
            }
            if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se
                                                                                                                         // utiliza
                                                                                                                         // GED
                // interno e nao eh BD
                if (controleGEDDTO != null) {
                    try {
                        final File arquivo = new File(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "."
                                + Util.getFileExtension(uploadDTO.getNameFile()));
                        CriptoUtils.encryptFile(uploadDTO.getPath(), PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged",
                                System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));
                        arquivo.delete();
                    } catch (final Exception e) {

                    }

                }
            } /*
               * else if (!PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) { // Se // utiliza // GED // externo // FALTA IMPLEMENTAR!!! }
               */
        }
    }

}
