package br.com.centralit.citged.negocio;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citged.bean.AssinaturaControleGEDDTO;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.AssinaturaControleGEDDao;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ControleGEDServiceBean extends CrudServiceImpl implements ControleGEDService {

    private ControleGEDDao dao;

    protected ControleGEDDao getDao() {
        if (dao == null) {
            dao = new ControleGEDDao();
        }
        return dao;
    }

    public Collection listByIdTabelaAndID(Integer idTabela, Integer id) throws Exception {
        return getDao().listByIdTabelaAndID(idTabela, id);
    }

    public String getProximaPastaArmazenar() throws Exception {
        return getDao().getProximaPastaArmazenar();
    }

    public Collection convertListControleGEDToUploadDTO(Collection colAnexosControleGED) throws Exception {
        if (colAnexosControleGED == null) {
            return null;
        }
        Collection colFinal = new ArrayList();
        for (Iterator it = colAnexosControleGED.iterator(); it.hasNext();) {
            ControleGEDDTO controleGedDto = (ControleGEDDTO) it.next();
            UploadDTO uploadDto = new UploadDTO();
            uploadDto.setIdControleGED(controleGedDto.getIdControleGED());
            uploadDto.setId("" + controleGedDto.getId());
            if (controleGedDto.getDescricaoArquivo() != null) {
                uploadDto.setDescricao(controleGedDto.getDescricaoArquivo());
            } else {
                uploadDto.setDescricao("");
            }
            uploadDto.setNameFile(controleGedDto.getNomeArquivo());
            uploadDto.setPath(controleGedDto.getPathArquivo());
            uploadDto.setTemporario("N");
            uploadDto.setSituacao("");
            uploadDto.setPath("ID=" + controleGedDto.getIdControleGED());
            if (controleGedDto.getVersao() != null) {
                if (!controleGedDto.getVersao().trim().equalsIgnoreCase("")) {
                    uploadDto.setVersao(controleGedDto.getVersao());
                }
            }
            uploadDto.setCaminhoRelativo(getRelativePathFromGed(controleGedDto));
            colFinal.add(uploadDto);
        }
        return colFinal;
    }

    public IDto create(IDto model) throws ServiceException, LogicException {
        CrudDAO crudDao = getDao();
        AssinaturaControleGEDDao assDao = new AssinaturaControleGEDDao();
        TransactionControler tc = new TransactionControlerImpl(crudDao.getAliasDB());
        try {
            validaCreate(model);
            assDao.setTransactionControler(tc);
            crudDao.setTransactionControler(tc);
            tc.start();
            ControleGEDDTO ged = (ControleGEDDTO) crudDao.create(model);
            if (ged != null && ged.getPathsAssinaturas() != null && !ged.getPathsAssinaturas().isEmpty()) {
                AssinaturaControleGEDDTO ass = null;
                for (int i = 0; i < ged.getPathsAssinaturas().size(); i++) {
                    ass = new AssinaturaControleGEDDTO();
                    ass.setIdControleGED(ged.getIdControleGED());
                    ass.setPastaControleGed(ged.getPasta());
                    ass.setPathAssinatura(ged.getPathsAssinaturas().get(i).toString());
                    assDao.create(ass);
                }
            }
            tc.commit();

            return model;
        } catch (Exception e) {
            this.rollbackTransaction(tc, e);
        } finally {
        	try {
				tc.close();
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        }
        return model;
    }

    @Override
    public Collection listByIdTabelaAndIdBaseConhecimentoPaiEFilho(Integer idTabela, Integer idBasePai, Integer idBaseFilho) throws Exception {
        return getDao().listByIdTabelaAndIdBaseConhecimentoPaiEFilho(idTabela, idBasePai, idBaseFilho);
    }

    public Collection listByIdTabelaAndIdBaseConhecimento(Integer idTabela, Integer idBaseConhecimento) throws Exception {
        return getDao().listByIdTabelaAndIdBaseConhecimento(idTabela, idBaseConhecimento);
    }

    public Collection listByIdTabelaAndIdLiberacaoAndLigacao(Integer idTabela, Integer idRequisicaoLiberacao) throws Exception {
        return getDao().listByIdTabelaAndIdLiberacaoAndLigacao(idTabela, idRequisicaoLiberacao);
    }

    public String getRelativePathFromGed(ControleGEDDTO controleGEDDTO) throws Exception {
        if (controleGEDDTO == null)
            return null;
        try {
            Integer idEmpresa = 1;
            String pasta = controleGEDDTO.getPasta();

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
            if (!UtilStrings.isNotVazio(prontuarioGedInternoBancoDados))
                prontuarioGedInternoBancoDados = "N";
            if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {
                if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S") && "S".equalsIgnoreCase(prontuarioGedInternoBancoDados)) { // Se utiliza GED
                    // interno e eh BD
                    // FALTA IMPLEMENTAR!
                } else {
                    String fileRec = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload/REC_FROM_GED_" + controleGEDDTO.getIdControleGED() + "." + controleGEDDTO.getExtensaoArquivo();
                    CriptoUtils.decryptFile(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", fileRec, System
                            .getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));

                    return Constantes.getValue("CONTEXTO_APLICACAO") + "/tempUpload/REC_FROM_GED_" + controleGEDDTO.getIdControleGED() + "." + controleGEDDTO.getExtensaoArquivo();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void atualizaAnexos(Collection<UploadDTO> anexos, int idTabela, Integer id, TransactionControler tc) throws Exception {
        ControleGEDDao controleGEDDao = this.getDao();
        controleGEDDao.setTransactionControler(tc);
        String gedInternoBancoDados = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInternoBD, "N");

        if (!UtilStrings.isNotVazio(gedInternoBancoDados)) {
            gedInternoBancoDados = "N";
        }

        String GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "/usr/local/gedCitsmart/");

        String GED_INTERNO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedInterno, "S");

        String pasta = getProximaPastaArmazenar();
        if (GED_INTERNO.equalsIgnoreCase("S")) {
            pasta = getProximaPastaArmazenar();
            File fileDir = new File(GED_DIRETORIO);

            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            fileDir = new File(GED_DIRETORIO + "/1");

            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }

            fileDir = new File(GED_DIRETORIO + "/1/" + pasta);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
        }

        HashMap<String, UploadDTO> mapUpload = new HashMap();
        if (anexos != null) {
            for (UploadDTO uploadDto : anexos) {
                if (uploadDto.getIdControleGED() != null) {
                    mapUpload.put("" + uploadDto.getIdControleGED(), uploadDto);
                    continue;
                }
                ControleGEDDTO controleGEDDTO = new ControleGEDDTO();
                controleGEDDTO.setIdTabela(idTabela);
                controleGEDDTO.setId(id);
                controleGEDDTO.setDataHora(UtilDatas.getDataAtual());
                controleGEDDTO.setDescricaoArquivo(uploadDto.getDescricao());
                controleGEDDTO.setExtensaoArquivo(Util.getFileExtension(uploadDto.getNameFile()));
                controleGEDDTO.setPasta(pasta);
                controleGEDDTO.setNomeArquivo(uploadDto.getNameFile());
                controleGEDDTO = (ControleGEDDTO) controleGEDDao.create(controleGEDDTO);
                uploadDto.setControleGEDDto(controleGEDDTO);

                if (controleGEDDTO != null) {
                    mapUpload.put("" + controleGEDDTO.getIdControleGED(), uploadDto);
                }
                if (GED_INTERNO.equalsIgnoreCase("S") && !"S".equalsIgnoreCase(gedInternoBancoDados)) {

                    if (controleGEDDTO != null) {

                        File arquivo = new File(GED_DIRETORIO + "/1/" + pasta + "/" + controleGEDDTO.getIdControleGED() + "." + Util.getFileExtension(uploadDto.getNameFile()));

                        CriptoUtils.encryptFile(uploadDto.getPath(), GED_DIRETORIO + "/1/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged",
                                System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PUBLICA"));

                        arquivo.delete();
                    }

                }
            }
        }

        Collection<ControleGEDDTO> colGed = controleGEDDao.listByIdTabelaAndID(idTabela, id);
        if (colGed == null)
            return;

        for (ControleGEDDTO controleGEDDto : colGed) {
            if (mapUpload.get("" + controleGEDDto.getIdControleGED()) != null)
                continue;
            controleGEDDao.delete(controleGEDDto);
        }

    }

    public ControleGEDDTO getControleGED(AnexoBaseConhecimentoDTO anexoBaseConhecimento) throws Exception {
        return getDao().getControleGED(anexoBaseConhecimento);
    }

}
