package br.com.centralit.citcorpore.rh.negocio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xssf.extractor.XSSFExcelExtractor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.pdfbox.cos.COSDocument;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;

import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.rh.bean.CertificacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CompetenciaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.CurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EmailCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.EnderecoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.ExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FormacaoCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.FuncaoExperienciaProfissionalCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.HistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.bean.IdiomaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.ItemHistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.bean.PesquisaCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.RequisicaoPessoalDTO;
import br.com.centralit.citcorpore.rh.bean.TelefoneCurriculoDTO;
import br.com.centralit.citcorpore.rh.bean.TreinamentoCurriculoDTO;
import br.com.centralit.citcorpore.rh.integracao.CertificacaoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.CompetenciaCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.CurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.EmailCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.EnderecoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.ExperienciaProfissionalCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.FormacaoCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.FuncaoExperienciaProfissionalCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.HistoricoFuncionalDao;
import br.com.centralit.citcorpore.rh.integracao.IdiomaCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.ItemHistoricoFuncionalDao;
import br.com.centralit.citcorpore.rh.integracao.TelefoneCurriculoDao;
import br.com.centralit.citcorpore.rh.integracao.TreinamentoCurriculoDao;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.centralit.citged.negocio.ControleGEDServiceBean;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"unchecked", "rawtypes"})
public class CurriculoServiceEjb extends CrudServiceImpl implements CurriculoService {

    private CurriculoDao dao;

    @Override
    protected CurriculoDao getDao() {
        if (dao == null) {
            dao = new CurriculoDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws ServiceException, LogicException {
        this.validaColecoes((CurriculoDTO) arg0);
    }

    private void validaColecoes(final CurriculoDTO curriculoDTO) throws ServiceException, LogicException {
        if (curriculoDTO.getColTelefones() == null || curriculoDTO.getColTelefones().isEmpty()) {
            throw new LogicException("É necessário registrar ao menos um telefone");
        }

        if (curriculoDTO.getColEnderecos() == null || curriculoDTO.getColEnderecos().isEmpty()) {
            throw new LogicException("É necessário ao menos um endereço");
        }

        if (curriculoDTO.getColEmail() == null || curriculoDTO.getColEmail().isEmpty()) {
            throw new LogicException("É necessário ao menos um email");
        }

        if (curriculoDTO.getColFormacao() == null || curriculoDTO.getColFormacao().isEmpty()) {
            throw new LogicException("É necessário ao menos uma formação");
        }
    }

    private void validaAnexos(final CurriculoDTO curriculoDto) throws LogicException {
        if (curriculoDto.getAnexos() != null) {
            if (curriculoDto.getAnexos().size() > 1) {
                throw new LogicException("Só é possível anexar um arquivo");
            }
        }
    }

    private void atualizaAnexos(final CurriculoDTO curriculoDto, final TransactionControler tc) throws Exception {

        this.validaAnexos(curriculoDto);

        final ControleGEDServiceBean controleGedService = new ControleGEDServiceBean();

        controleGedService.atualizaAnexos(curriculoDto.getAnexos(), ControleGEDDTO.TABELA_CURRICULO, curriculoDto.getIdCurriculo(), tc);

        if (curriculoDto.getFoto() != null) {
            final UploadDTO foto = curriculoDto.getFoto();
            foto.setDescricao("Foto currículo " + curriculoDto.getNome());
            final Collection<UploadDTO> anexos = new ArrayList();
            anexos.add(foto);
            controleGedService.atualizaAnexos(anexos, ControleGEDDTO.FOTO_CURRICULO, curriculoDto.getIdCurriculo(), tc);
        }
    }

    /**
     * Método foi alterado para associa-lo a um candidato, mantendo um historico funcional do mesmo
     *
     */
    @Override
    public IDto create(IDto model) throws ServiceException, LogicException {

        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final TelefoneCurriculoDao telefoneCurriculoDao = new TelefoneCurriculoDao();
        final EnderecoCurriculoDao enderecoCurriculoDao = new EnderecoCurriculoDao();
        final FormacaoCurriculoDao formacaoCurriculoDao = new FormacaoCurriculoDao();
        final TreinamentoCurriculoDao treinamentoCurriculoDao = new TreinamentoCurriculoDao();
        final EmailCurriculoDao emailCurriculoDao = new EmailCurriculoDao();
        final CertificacaoCurriculoDao certificacaoCurriculoDao = new CertificacaoCurriculoDao();
        final IdiomaCurriculoDao idiomaCurriculoDao = new IdiomaCurriculoDao();

        final ExperienciaProfissionalCurriculoDao experienciaProfissionalCurriculoDao = new ExperienciaProfissionalCurriculoDao();

        final CompetenciaCurriculoDao competenciaCurriculoDao = new CompetenciaCurriculoDao();

        final CurriculoDTO curriculoDTO = (CurriculoDTO) model;
        TransactionControler tc = null;

        try {

            tc = crudDao.getTransactionControler();

            // Faz validacao, caso exista.

            this.validaCreate(model);

            // Seta o TransactionController para os DAOs

            crudDao.setTransactionControler(tc);
            telefoneCurriculoDao.setTransactionControler(tc);
            enderecoCurriculoDao.setTransactionControler(tc);
            emailCurriculoDao.setTransactionControler(tc);
            formacaoCurriculoDao.setTransactionControler(tc);
            certificacaoCurriculoDao.setTransactionControler(tc);
            idiomaCurriculoDao.setTransactionControler(tc);
            treinamentoCurriculoDao.setTransactionControler(tc);

            experienciaProfissionalCurriculoDao.setTransactionControler(tc);

            competenciaCurriculoDao.setTransactionControler(tc);

            // Executa operacoes pertinentes ao negocio.

            /**
             * Re-Tratamento de CPF e EMAIL repetidos
             */
            if (curriculoDTO != null) {
                final boolean cpfJaCadastrado = this.getDao().verificaCPFJaCadastrado(curriculoDTO.getIdCurriculo(), curriculoDTO.getCpf());
                boolean emailJaCadastrado = false;

                List colEmails = new ArrayList();
                colEmails = (List) curriculoDTO.getColEmail();

                for (int i = 0; i < colEmails.size(); i++) {
                    final EmailCurriculoDTO emailAux = (EmailCurriculoDTO) colEmails.get(i);
                    if (emailAux.getPrincipal().equalsIgnoreCase("S")) {
                        emailJaCadastrado = emailCurriculoDao.verificaEmailPrincipalJaCadastrado(curriculoDTO.getIdCurriculo(), emailAux.getDescricaoEmail());
                        break;
                    }
                }

                if (cpfJaCadastrado) {
                    throw new LogicException(this.i18nMessage("rh.cpfJaCadastrado"));
                }
                if (emailJaCadastrado) {
                    throw new LogicException(this.i18nMessage("rh.emailPrincipalJaCadastrado"));
                }
            }

            model = crudDao.create(curriculoDTO);
            if (curriculoDTO.getColTelefones() != null) {
                for (final java.util.Iterator it = curriculoDTO.getColTelefones().iterator(); it.hasNext();) {
                    final TelefoneCurriculoDTO telefoneCurriculoDto = (TelefoneCurriculoDTO) it.next();
                    telefoneCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    telefoneCurriculoDao.create(telefoneCurriculoDto);
                }

            }

            if (curriculoDTO.getColEnderecos() != null) {
                for (final Iterator it = curriculoDTO.getColEnderecos().iterator(); it.hasNext();) {
                    final EnderecoCurriculoDTO enderecoCurriculoDto = (EnderecoCurriculoDTO) it.next();
                    enderecoCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    enderecoCurriculoDao.create(enderecoCurriculoDto);
                }
            }

            if (curriculoDTO.getColFormacao() != null) {
                for (final Iterator it = curriculoDTO.getColFormacao().iterator(); it.hasNext();) {
                    final FormacaoCurriculoDTO formacaoCurriculoDto = (FormacaoCurriculoDTO) it.next();
                    formacaoCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    formacaoCurriculoDao.create(formacaoCurriculoDto);
                }
            }

            if (curriculoDTO.getColEmail() != null) {
                for (final Iterator it = curriculoDTO.getColEmail().iterator(); it.hasNext();) {
                    final EmailCurriculoDTO emCurriculoDto = (EmailCurriculoDTO) it.next();;
                    emCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    emailCurriculoDao.create(emCurriculoDto);
                }
            }

            if (curriculoDTO.getColCertificacao() != null) {
                for (final Iterator it = curriculoDTO.getColCertificacao().iterator(); it.hasNext();) {
                    final CertificacaoCurriculoDTO cerCurriculoDto = (CertificacaoCurriculoDTO) it.next();;
                    cerCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    certificacaoCurriculoDao.create(cerCurriculoDto);
                }
            }

            if (curriculoDTO.getColIdioma() != null) {

                for (final Iterator it = curriculoDTO.getColIdioma().iterator();

                it.hasNext();) {

                    final IdiomaCurriculoDTO idiomaCurriculoDto = (IdiomaCurriculoDTO) it.next();;

                    idiomaCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());

                    idiomaCurriculoDao.create(idiomaCurriculoDto);

                }

            }

            if (curriculoDTO.getColExperienciaProfissional() != null) {
                final FuncaoExperienciaProfissionalCurriculoDao dao = new FuncaoExperienciaProfissionalCurriculoDao();
                dao.setTransactionControler(tc);

                for (final Object obj : curriculoDTO.getColExperienciaProfissional()) {
                    ExperienciaProfissionalCurriculoDTO experienciaDTO = (ExperienciaProfissionalCurriculoDTO) obj;

                    experienciaDTO.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    experienciaDTO = (ExperienciaProfissionalCurriculoDTO) experienciaProfissionalCurriculoDao.create(experienciaDTO);
                    if (experienciaDTO.getColFuncao() != null) {
                        final Collection<FuncaoExperienciaProfissionalCurriculoDTO> colDescFuncao = experienciaDTO.getColFuncao();
                        for (final FuncaoExperienciaProfissionalCurriculoDTO funcao : colDescFuncao) {
                            funcao.setIdExperienciaProfissional(experienciaDTO.getIdExperienciaProfissional());
                            dao.create(funcao);
                        }
                    }
                }

            }

            if (curriculoDTO.getColCompetencias() != null) {

                for (final Object obj : curriculoDTO.getColCompetencias()) {
                    final CompetenciaCurriculoDTO competenciaDto = (CompetenciaCurriculoDTO) obj;

                    competenciaDto.setIdCurriculo(curriculoDTO.getIdCurriculo());

                    competenciaCurriculoDao.create(competenciaDto);

                }

            }

            if (curriculoDTO.getColTreinamentos() != null) {
                for (final Object element : curriculoDTO.getColTreinamentos()) {
                    final TreinamentoCurriculoDTO treinamentoCurriculoDTO = (TreinamentoCurriculoDTO) element;;
                    treinamentoCurriculoDTO.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    treinamentoCurriculoDao.create(treinamentoCurriculoDTO);
                }
            }

            this.atualizaAnexos(curriculoDTO, tc);
            // indexarAnexoDocumentoLucene(curriculoDTO);

            /**
             * Inicio metodo Historico Funcional - David Rodrigues
             * - Mudança para create Historico Funcional
             * - Mudança para create Item Historico Funcional
             *
             */

            HistoricoFuncionalDTO historicoFuncionalDto = new HistoricoFuncionalDTO();
            final ItemHistoricoFuncionalDTO itemHistoricoFuncionalDto = new ItemHistoricoFuncionalDTO();

            final HistoricoFuncionalDao historicoFuncionalDao = new HistoricoFuncionalDao();
            final ItemHistoricoFuncionalDao itemHistoricoFuncionalDao = new ItemHistoricoFuncionalDao();

            Integer idResponsavel = curriculoDTO.getIdUsuarioSessao();

            historicoFuncionalDao.setTransactionControler(tc);
            itemHistoricoFuncionalDao.setTransactionControler(tc);

            if (curriculoDTO != null) {
                historicoFuncionalDto.setIdCandidato(curriculoDTO.getIdCandidato());
                historicoFuncionalDto.setIdCurriculo(curriculoDTO.getIdCurriculo());
                historicoFuncionalDto.setDtCriacao(UtilDatas.getDataAtual());

                historicoFuncionalDto = (HistoricoFuncionalDTO) historicoFuncionalDao.create(historicoFuncionalDto);

                if (historicoFuncionalDto != null) {
                    if (idResponsavel == null) {
                        final String idResp = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_USUARIO_CANDIDATO_EXTERNO, "1");
                        idResponsavel = new Integer(idResp);
                    }
                    itemHistoricoFuncionalDto.setIdHistoricoFuncional(historicoFuncionalDto.getIdHistoricoFuncional());
                    itemHistoricoFuncionalDto.setDtCriacao(UtilDatas.getDataAtual());
                    itemHistoricoFuncionalDto.setIdResponsavel(idResponsavel);
                    itemHistoricoFuncionalDto.setTitulo("Criação Curriculo do Candidato");
                    itemHistoricoFuncionalDto.setDescricao("O candidato: " + curriculoDTO.getNome() + ", foi inserido com sucesso na base de dados");
                    itemHistoricoFuncionalDto.setTipo("A");
                    itemHistoricoFuncionalDao.create(itemHistoricoFuncionalDto);
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

            return model;

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            e.printStackTrace();
        }

        return model;

    }

    @Override
    public void update(final IDto model) throws ServiceException, LogicException {

        // Instancia Objeto controlador de transacao
        final CrudDAO crudDao = this.getDao();
        final TelefoneCurriculoDao telefoneCurriculoDao = new TelefoneCurriculoDao();
        final EnderecoCurriculoDao enderecoCurriculoDao = new EnderecoCurriculoDao();
        final FormacaoCurriculoDao formacaoCurriculoDao = new FormacaoCurriculoDao();
        final EmailCurriculoDao emailCurriculoDao = new EmailCurriculoDao();
        final CertificacaoCurriculoDao certificacaoCurriculoDao = new CertificacaoCurriculoDao();
        final IdiomaCurriculoDao idiomaCurriculoDao = new IdiomaCurriculoDao();
        final CompetenciaCurriculoDao competenciaCurriculoDao = new CompetenciaCurriculoDao();
        final ExperienciaProfissionalCurriculoDao experienciaProfissionalCurriculoDao = new ExperienciaProfissionalCurriculoDao();
        final TreinamentoCurriculoDao treinamentoCurriculoDao = new TreinamentoCurriculoDao();
        final HistoricoFuncionalDao historicoFuncionalDao = new HistoricoFuncionalDao();
        final ItemHistoricoFuncionalDao itemHistoricoFuncionalDao = new ItemHistoricoFuncionalDao();

        final CurriculoDTO curriculoDTO = (CurriculoDTO) model;

        TransactionControler tc = null;

        try {

            tc = crudDao.getTransactionControler();

            // Faz validacao, caso exista.
            this.validaCreate(model);

            // Instancia ou obtem os DAOs necessarios.

            // Seta o TransactionController para os DAOs
            crudDao.setTransactionControler(tc);
            telefoneCurriculoDao.setTransactionControler(tc);
            enderecoCurriculoDao.setTransactionControler(tc);
            emailCurriculoDao.setTransactionControler(tc);
            formacaoCurriculoDao.setTransactionControler(tc);
            certificacaoCurriculoDao.setTransactionControler(tc);
            idiomaCurriculoDao.setTransactionControler(tc);
            competenciaCurriculoDao.setTransactionControler(tc);
            experienciaProfissionalCurriculoDao.setTransactionControler(tc);
            treinamentoCurriculoDao.setTransactionControler(tc);
            historicoFuncionalDao.setTransactionControler(tc);
            itemHistoricoFuncionalDao.setTransactionControler(tc);

            // Executa operacoes pertinentes ao negocio.
            crudDao.update(curriculoDTO);

            telefoneCurriculoDao.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());

            if (curriculoDTO.getColTelefones() != null) {

                for (final java.util.Iterator it = curriculoDTO.getColTelefones().iterator(); it.hasNext();) {
                    final TelefoneCurriculoDTO telefoneCurriculoDto = (TelefoneCurriculoDTO) it.next();
                    telefoneCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());

                    telefoneCurriculoDao.create(telefoneCurriculoDto);
                }
            }

            enderecoCurriculoDao.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());

            if (curriculoDTO.getColEnderecos() != null) {

                for (final Iterator it = curriculoDTO.getColEnderecos().iterator(); it.hasNext();) {

                    final EnderecoCurriculoDTO enderecoCurriculoDto = (EnderecoCurriculoDTO) it.next();
                    enderecoCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());

                    enderecoCurriculoDao.create(enderecoCurriculoDto);
                }
            }

            formacaoCurriculoDao.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());

            if (curriculoDTO.getColFormacao() != null) {

                for (final Iterator it = curriculoDTO.getColFormacao().iterator(); it.hasNext();) {

                    final FormacaoCurriculoDTO formacaoCurriculoDto = (FormacaoCurriculoDTO) it.next();
                    formacaoCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    formacaoCurriculoDao.create(formacaoCurriculoDto);

                }

            }

            emailCurriculoDao.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());

            if (curriculoDTO.getColEmail() != null) {

                for (final Iterator it = curriculoDTO.getColEmail().iterator(); it.hasNext();) {

                    final EmailCurriculoDTO emCurriculoDto = (EmailCurriculoDTO) it.next();
                    emCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    emailCurriculoDao.create(emCurriculoDto);

                }

            }

            certificacaoCurriculoDao.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());

            if (curriculoDTO.getColCertificacao() != null) {

                for (final Iterator it = curriculoDTO.getColCertificacao().iterator(); it.hasNext();) {

                    final CertificacaoCurriculoDTO cerCurriculoDto = (CertificacaoCurriculoDTO) it.next();
                    cerCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());

                    certificacaoCurriculoDao.create(cerCurriculoDto);
                }
            }

            treinamentoCurriculoDao.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());

            if (curriculoDTO.getColTreinamentos() != null) {
                for (final Object element : curriculoDTO.getColTreinamentos()) {
                    final TreinamentoCurriculoDTO treinamentoCurriculoDTO = (TreinamentoCurriculoDTO) element;;
                    treinamentoCurriculoDTO.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    treinamentoCurriculoDao.create(treinamentoCurriculoDTO);
                }
            }

            idiomaCurriculoDao.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());

            if (curriculoDTO.getColIdioma() != null) {

                for (final Iterator it = curriculoDTO.getColIdioma().iterator();

                it.hasNext();) {

                    final IdiomaCurriculoDTO idiomaCurriculoDto = (IdiomaCurriculoDTO) it.next();;

                    idiomaCurriculoDto.setIdCurriculo(curriculoDTO.getIdCurriculo());

                    idiomaCurriculoDao.create(idiomaCurriculoDto);

                }

            }

            competenciaCurriculoDao.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());

            if (curriculoDTO.getColCompetencias() != null) {

                for (final Object obj : curriculoDTO.getColCompetencias()) {

                    final CompetenciaCurriculoDTO competencia = (CompetenciaCurriculoDTO) obj;

                    competencia.setIdCurriculo(curriculoDTO.getIdCurriculo());

                    competenciaCurriculoDao.create(competencia);

                }

            }

            if (curriculoDTO.getColExperienciaProfissional() != null) {
                final FuncaoExperienciaProfissionalCurriculoDao funcaoExperienciaProfissionalCurriculoDao = new FuncaoExperienciaProfissionalCurriculoDao();
                funcaoExperienciaProfissionalCurriculoDao.setTransactionControler(tc);

                final Collection<ExperienciaProfissionalCurriculoDTO> colExp = experienciaProfissionalCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());
                if (colExp != null && colExp.size() > 0) {
                    for (final ExperienciaProfissionalCurriculoDTO experiencia : colExp) {
                        funcaoExperienciaProfissionalCurriculoDao.deleteByIdExperienciaProfissional(experiencia.getIdExperienciaProfissional());
                    }
                }

                experienciaProfissionalCurriculoDao.deleteByIdCurriculo(curriculoDTO.getIdCurriculo());

                for (final Object obj : curriculoDTO.getColExperienciaProfissional()) {
                    ExperienciaProfissionalCurriculoDTO experienciaDTO = (ExperienciaProfissionalCurriculoDTO) obj;

                    experienciaDTO.setIdCurriculo(curriculoDTO.getIdCurriculo());
                    experienciaDTO = (ExperienciaProfissionalCurriculoDTO) experienciaProfissionalCurriculoDao.create(experienciaDTO);
                    if (experienciaDTO.getColFuncao() != null) {
                        final Collection<FuncaoExperienciaProfissionalCurriculoDTO> colDescFuncao = experienciaDTO.getColFuncao();
                        // Deletando geral
                        funcaoExperienciaProfissionalCurriculoDao.deleteByIdExperienciaProfissional(experienciaDTO.getIdExperienciaProfissional());
                        for (final FuncaoExperienciaProfissionalCurriculoDTO funcao : colDescFuncao) {
                            funcao.setIdExperienciaProfissional(experienciaDTO.getIdExperienciaProfissional());
                            funcaoExperienciaProfissionalCurriculoDao.create(funcao);
                        }
                    }
                }

            }

            this.atualizaAnexos(curriculoDTO, tc);

            /**
             * Inicio metodo Historico Funcional - David Rodrigues
             * - Mudança para create Historico Funcional
             * - Mudança para create Item Historico Funcional
             *
             */

            if (curriculoDTO != null) {

                HistoricoFuncionalDTO historicoFuncionalDto = new HistoricoFuncionalDTO();
                final ItemHistoricoFuncionalDTO itemHistoricoFuncionalDto = new ItemHistoricoFuncionalDTO();

                historicoFuncionalDto.setIdCandidato(curriculoDTO.getIdCandidato());
                historicoFuncionalDto.setIdCurriculo(curriculoDTO.getIdCurriculo());
                historicoFuncionalDto.setDtCriacao(UtilDatas.getDataAtual());

                historicoFuncionalDto = historicoFuncionalDao.restoreByIdCurriculo(curriculoDTO.getIdCurriculo());

                Integer idResponsavel = curriculoDTO.getIdUsuarioSessao();

                if (historicoFuncionalDto != null) {
                    if (idResponsavel == null) {
                        final String idResp = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ID_USUARIO_CANDIDATO_EXTERNO, "1");
                        idResponsavel = new Integer(idResp);
                    }
                    itemHistoricoFuncionalDto.setIdHistoricoFuncional(historicoFuncionalDto.getIdHistoricoFuncional());
                    itemHistoricoFuncionalDto.setDtCriacao(UtilDatas.getDataAtual());
                    itemHistoricoFuncionalDto.setIdResponsavel(idResponsavel);
                    itemHistoricoFuncionalDto.setTitulo("Alteração Curriculo do Candidato");
                    itemHistoricoFuncionalDto.setDescricao("O curriculo do candidato: " + curriculoDTO.getNome() + ", foi alterado com sucesso na base de dados");
                    itemHistoricoFuncionalDto.setTipo("A");

                    itemHistoricoFuncionalDao.create(itemHistoricoFuncionalDto);
                }
            }

            // Faz commit e fecha a transacao.
            tc.commit();
            tc.close();

        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
            e.printStackTrace();
        }

    }

    @Override
    public IDto restore(final IDto model) throws ServiceException, LogicException {

        try {

            CurriculoDTO curriculoDTO = (CurriculoDTO) model;

            final TelefoneCurriculoDao telefoneCurriculoDao = new TelefoneCurriculoDao();

            final Collection colTelefones = telefoneCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());

            final EnderecoCurriculoDao enderecoCurriculoDao = new EnderecoCurriculoDao();

            final Collection colEnderecos = enderecoCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());

            final FormacaoCurriculoDao formacaoCurriculoDao = new FormacaoCurriculoDao();

            final Collection colFormacao = formacaoCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());

            final EmailCurriculoDao emailCurriculoDao = new EmailCurriculoDao();

            final Collection colEmail = emailCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());

            final CertificacaoCurriculoDao certificacaoCurriculoDao = new CertificacaoCurriculoDao();

            final Collection colCertificacao = certificacaoCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());

            final TreinamentoCurriculoDao treinamentoCurriculoDao = new TreinamentoCurriculoDao();

            final Collection colTreinamento = treinamentoCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());

            final ExperienciaProfissionalCurriculoDao experienciaDao = new ExperienciaProfissionalCurriculoDao();
            final FuncaoExperienciaProfissionalCurriculoDao funcaoDao = new FuncaoExperienciaProfissionalCurriculoDao();

            final ArrayList<ExperienciaProfissionalCurriculoDTO> colExperiencias = new ArrayList<ExperienciaProfissionalCurriculoDTO>();
            final Collection<ExperienciaProfissionalCurriculoDTO> colExperienciasAux = experienciaDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());
            if (colExperienciasAux != null && colExperienciasAux.size() > 0) {
                for (final ExperienciaProfissionalCurriculoDTO experienciaDTO : colExperienciasAux) {
                    experienciaDTO.setColFuncao(funcaoDao.findByIdExperienciaProfissional(experienciaDTO.getIdExperienciaProfissional()));
                    colExperiencias.add(experienciaDTO);
                }

                // Ordena os itens em ordem descrescente
                int position = 0;
                final int count = colExperiencias.size();
                for (int i = 0; i < count; i++) {
                    Date maiorData = colExperiencias.get(i).getDataInicial();
                    colExperiencias.get(i);
                    position = i;
                    for (int j = i + 1; j < count; j++) {
                        if (maiorData.before(colExperiencias.get(j).getDataInicial())) {
                            maiorData = colExperiencias.get(j).getDataInicial();
                            colExperiencias.get(j);
                            position = j;
                            break;
                        }
                    }

                    if (position != i) {
                        final ExperienciaProfissionalCurriculoDTO aux = colExperiencias.get(i);
                        colExperiencias.set(i, colExperiencias.get(position));
                        colExperiencias.set(position, aux);
                    }
                }
            }

            final CompetenciaCurriculoDao competenciaCurriculoDao = new CompetenciaCurriculoDao();
            final Collection colcompetencias = competenciaCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());

            final IdiomaCurriculoDao idiomaCurriculoDao = new IdiomaCurriculoDao();

            final Collection<IdiomaCurriculoDTO> colIdioma = idiomaCurriculoDao.findByIdCurriculo(curriculoDTO.getIdCurriculo());

            curriculoDTO = (CurriculoDTO) this.getDao().restore(curriculoDTO);

            curriculoDTO.setColTelefones(colTelefones);

            curriculoDTO.setColEnderecos(colEnderecos);

            curriculoDTO.setColFormacao(colFormacao);

            curriculoDTO.setColEmail(colEmail);

            curriculoDTO.setColCertificacao(colCertificacao);

            curriculoDTO.setColIdioma(colIdioma);

            curriculoDTO.setColTreinamentos(colTreinamento);

            curriculoDTO.setColExperienciaProfissional(colExperiencias);

            curriculoDTO.setColCompetencias(colcompetencias);

            return curriculoDTO;

        } catch (final Exception e) {

            throw new ServiceException(e);

        }

    }

    public String getFromGed(final ControleGEDDTO controleGEDDTO) throws Exception {

        final Integer idEmpresa = 1;

        String pasta = "";

        if (controleGEDDTO != null) {

            pasta = controleGEDDTO.getPasta();

        }

        String PRONTUARIO_GED_DIRETORIO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.GedDiretorio, "/usr/local/gedCitsmart/");

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

        if (PRONTUARIO_GED_INTERNO.equalsIgnoreCase("S")) {

            final String fileRec = CITCorporeUtil.CAMINHO_REAL_APP + "tempUpload/REC_FROM_GED_" + controleGEDDTO.getIdControleGED() + "." + controleGEDDTO.getExtensaoArquivo();

            CriptoUtils.decryptFile(PRONTUARIO_GED_DIRETORIO + "/" + idEmpresa + "/" + pasta + "/" + controleGEDDTO.getIdControleGED() + ".ged", fileRec,

            System.getProperties().get("user.dir") + Constantes.getValue("CAMINHO_CHAVE_PRIVADA"));

            return fileRec;

        }

        return null;

    }

    public String[] extrairFormatoMicrosoftWord(final String caminhoDocumento) {

        File file = null;

        WordExtractor extractor = null;

        String[] fileData = null;

        try {

            file = new File(caminhoDocumento);

            final FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            final HWPFDocument document = new HWPFDocument(fis);

            extractor = new WordExtractor(document);

            fileData = extractor.getParagraphText();

            extractor.close();

            fis.close();

        } catch (final Exception ex) {

            ex.printStackTrace();

        }

        return fileData;

    }

    public StringBuilder extrairFormatoDOCX(final String caminhoDocumento) {

        File file = null;

        XWPFWordExtractor extractor = null;

        final StringBuilder texto = new StringBuilder();

        try {

            file = new File(caminhoDocumento);

            final FileInputStream fis = new FileInputStream(file.getAbsolutePath());

            final XWPFDocument document = new XWPFDocument(fis);

            extractor = new XWPFWordExtractor(document);

            texto.append(extractor.getText());

            extractor.close();

            fis.close();

        } catch (final Exception ex) {

            ex.printStackTrace();

        }

        return texto;

    }

    public StringBuilder extrairFormatoXLSX(final String caminhoDocumento) {

        final StringBuilder texto = new StringBuilder();

        try {

            final File file = new File(caminhoDocumento);

            final FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());

            final XSSFWorkbook document = new XSSFWorkbook(fileInputStream);

            final XSSFExcelExtractor extractor = new XSSFExcelExtractor(document);

            extractor.setFormulasNotResults(true);

            extractor.setIncludeSheetNames(true);

            texto.append(extractor.getText());

            extractor.close();

            fileInputStream.close();

        } catch (final Exception ex) {

            ex.printStackTrace();

        }

        return texto;

    }

    public StringBuilder extrairFormatoXLS(final String caminhoDocumento) {

        final StringBuilder texto = new StringBuilder();

        try {

            final File file = new File(caminhoDocumento);

            final FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());

            final HSSFWorkbook document = new HSSFWorkbook(fileInputStream);

            final ExcelExtractor extractor = new ExcelExtractor(document);

            extractor.setFormulasNotResults(true);

            extractor.setIncludeSheetNames(true);

            texto.append(extractor.getText());

            extractor.close();

            fileInputStream.close();

        } catch (final Exception ex) {

            ex.printStackTrace();

        }

        return texto;

    }

    public StringBuilder extrairFormatoPDF(final String caminhoDocumento) throws IOException {

        final StringBuilder texto = new StringBuilder();

        PDFParser parser;

        FileInputStream fi;

        try {

            fi = new FileInputStream(new File(caminhoDocumento));

            parser = new PDFParser(fi);

            parser.parse();

            final COSDocument cd = parser.getDocument();

            final PDFTextStripper stripper = new PDFTextStripper();

            texto.append(stripper.getText(new PDDocument(cd)));

            cd.close();

            fi.close();

        } catch (final FileNotFoundException e1) {

            e1.printStackTrace();

        } catch (final IOException e1) {

            e1.printStackTrace();

        }

        return texto;

    }

    public StringBuilder extrairFormatoTxt(final String caminhoDocumento) {

        File file = null;

        final StringBuilder texto = new StringBuilder("");

        try {

            file = new File(caminhoDocumento);

            final FileInputStream fis = new FileInputStream(file);

            int ln;

            while ((ln = fis.read()) != -1) {

                texto.append((char) ln);

            }

            fis.close();

        } catch (final FileNotFoundException e) {

            e.printStackTrace();

        } catch (final IOException e) {

            e.printStackTrace();

        }

        return texto;

    }

    /**
     * Método respónsavel pelo retorno do caminho da foto utilizando o ControleGED e Upload
     *
     */
    @Override
    public String retornarCaminhoFoto(final Integer idCurriculo) throws Exception {
        final ControleGEDDao controleGEDDao = new ControleGEDDao();
        final ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
        String caminhoFoto = "";
        try {
            Collection colAnexosFotos = controleGEDDao.listByIdTabelaAndID(ControleGEDDTO.FOTO_CURRICULO, idCurriculo);
            colAnexosFotos = controleGedService.convertListControleGEDToUploadDTO(colAnexosFotos);
            if (colAnexosFotos != null && colAnexosFotos.size() > 0) {
                final Iterator it = colAnexosFotos.iterator();
                // UploadFileDTO uploadItem = (UploadFileDTO)it.next();
                final UploadDTO uploadItem = (UploadDTO) it.next();

                caminhoFoto = uploadItem.getCaminhoRelativo();
            }
        } catch (final Exception e) {

            e.printStackTrace();
        }

        return caminhoFoto;

    }

    @Override
    public Collection<CurriculoDTO> listaCurriculosPorCriterios(final PesquisaCurriculoDTO pesquisaCurriculoDto) throws Exception {
        return this.getDao().listaCurriculosPorCriterios(pesquisaCurriculoDto);
    }

    @Override
    public CurriculoDTO findIdByCpf(final String cpf) throws Exception {
        return this.getDao().findIdByCpf(cpf);
    }

    @Override
    public boolean verificaEmailPrincipalJaCadastrado(final Integer idCurriculo, final String email) throws Exception {
        final EmailCurriculoDao emailCurriculoDao = new EmailCurriculoDao();
        return emailCurriculoDao.verificaEmailPrincipalJaCadastrado(idCurriculo, email);
    }

    @Override
    public boolean verificaCPFJaCadastrado(final Integer idCurriculo, final String cpf) throws Exception {
        return this.getDao().verificaCPFJaCadastrado(idCurriculo, cpf);
    }

    @Override
    public Integer calculaTotalPaginas(final RequisicaoPessoalDTO requisicaoPessoalDTO, final String idsCurriculosTriados, final Integer itensPorPagina) throws Exception {
        return this.getDao().contaCurriculosPorCriterios(requisicaoPessoalDTO, idsCurriculosTriados, itensPorPagina);
    }

}
