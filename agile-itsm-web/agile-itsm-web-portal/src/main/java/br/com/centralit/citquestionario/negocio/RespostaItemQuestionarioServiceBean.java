package br.com.centralit.citquestionario.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemAuxiliarDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioAnexosDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioOpcoesDTO;
import br.com.centralit.citquestionario.integracao.QuestaoQuestionarioDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioAnexosDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioDao;
import br.com.centralit.citquestionario.integracao.RespostaItemQuestionarioOpcoesDao;
import br.com.centralit.citquestionario.util.ConstantesQuestionario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class RespostaItemQuestionarioServiceBean extends CrudServiceImpl implements RespostaItemQuestionarioService {

    private RespostaItemQuestionarioDao dao;

    @Override
    protected RespostaItemQuestionarioDao getDao() {
        if (dao == null) {
            dao = new RespostaItemQuestionarioDao();
        }
        return dao;
    }

    /**
     * Recebe uma Collection de RespostaItemAuxiliarDTO e processa.
     *
     * @param tc
     * @param col
     * @param idIdentificadorResposta
     * @throws Exception
     */
    public Collection processCollection(final TransactionControler tc, final Collection col, final Collection colAnexos, final Integer idIdentificadorResposta,
            final HttpServletRequest request) throws Exception {
        QuestaoQuestionarioDTO questaoQuestionarioDto;
        RespostaItemQuestionarioDTO respostaItemQuestionarioDto;
        RespostaItemQuestionarioOpcoesDTO respostaItemQuestionarioOpcoesDto;

        final QuestaoQuestionarioDao questaoDao = new QuestaoQuestionarioDao();
        final RespostaItemQuestionarioDao respostaItemDao = new RespostaItemQuestionarioDao();
        final RespostaItemQuestionarioOpcoesDao respItemOpcoesDao = new RespostaItemQuestionarioOpcoesDao();
        final RespostaItemQuestionarioAnexosDao respostaItemQuestionarioAnexosDao = new RespostaItemQuestionarioAnexosDao();

        questaoDao.setTransactionControler(tc);
        respostaItemDao.setTransactionControler(tc);
        respItemOpcoesDao.setTransactionControler(tc);
        respostaItemQuestionarioAnexosDao.setTransactionControler(tc);

        final Collection result = new ArrayList(); // Esta collection apenas armazena os CIDS. Para verificacao entre outro passo sobre vigilancia epidemiologica.

        if (colAnexos != null && colAnexos.size() > 0) {
            boolean bPrimVez = true;
            RespostaItemQuestionarioDTO respostaItemQuestionarioDtoAux = new RespostaItemQuestionarioDTO();
            for (final Iterator it = colAnexos.iterator(); it.hasNext();) {
                final RespostaItemQuestionarioAnexosDTO respItemAnexo = (RespostaItemQuestionarioAnexosDTO) it.next();
                if (bPrimVez) {
                    respostaItemQuestionarioDtoAux.setIdQuestaoQuestionario(respItemAnexo.getIdQuestaoQuestionario());
                    respostaItemQuestionarioDtoAux.setIdIdentificadorResposta(idIdentificadorResposta);
                    respostaItemQuestionarioDtoAux = (RespostaItemQuestionarioDTO) respostaItemDao.create(respostaItemQuestionarioDtoAux);
                }
                bPrimVez = false;

                respItemAnexo.setIdRespostaItemQuestionario(respostaItemQuestionarioDtoAux.getIdRespostaItemQuestionario());
                respostaItemQuestionarioAnexosDao.create(respItemAnexo);
            }
        }
        if (col != null) {
            for (final Iterator it = col.iterator(); it.hasNext();) {
                final RespostaItemAuxiliarDTO respItem = (RespostaItemAuxiliarDTO) it.next();
                if (respItem.getFieldName().length() < 9) {
                    continue;
                }
                if (respItem.getFieldName().substring(0, 9).equalsIgnoreCase("campoDyn_")) {
                    String idQuestaoStr = respItem.getFieldName().substring(9);
                    if (idQuestaoStr.indexOf(" ") != -1) {
                        idQuestaoStr = idQuestaoStr.substring(0, idQuestaoStr.indexOf(" "));
                    }
                    questaoQuestionarioDto = new QuestaoQuestionarioDTO();
                    questaoQuestionarioDto.setIdQuestaoQuestionario(new Integer(Integer.parseInt(idQuestaoStr)));

                    questaoQuestionarioDto = (QuestaoQuestionarioDTO) questaoDao.restore(questaoQuestionarioDto);

                    respostaItemQuestionarioDto = new RespostaItemQuestionarioDTO();
                    respostaItemQuestionarioDto.setIdIdentificadorResposta(idIdentificadorResposta);
                    respostaItemQuestionarioDto.setIdQuestaoQuestionario(new Integer(Integer.parseInt(idQuestaoStr)));

                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("T") || questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("L")
                            || questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("A") || questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("9")) {
                        respostaItemQuestionarioDto.setRespostaTextual(respItem.getFieldValue());
                        if (questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")) {
                            if (respostaItemQuestionarioDto.getRespostaTextual().equalsIgnoreCase("")) {
                                throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
                            }
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("N")) {
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            String aux = respItem.getFieldValue().replaceAll("\\.", "");
                            aux = aux.replaceAll("\\,", "\\.");
                            try {
                                respostaItemQuestionarioDto.setRespostaNumero(Double.valueOf(aux));
                            } catch (final Exception e) {
                                e.printStackTrace();
                                respostaItemQuestionarioDto.setRespostaNumero(new Double(0));
                            }
                            if (respostaItemQuestionarioDto.getRespostaNumero() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")) {
                                throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
                            }
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("V")) {
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            String aux = respItem.getFieldValue().replaceAll("\\.", "");
                            aux = aux.replaceAll("\\,", "\\.");
                            try {
                                respostaItemQuestionarioDto.setRespostaValor(Double.valueOf(aux));
                            } catch (final Exception e) {
                                e.printStackTrace();
                                respostaItemQuestionarioDto.setRespostaValor(new Double(0));
                            }
                            if (respostaItemQuestionarioDto.getRespostaValor() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")) {
                                throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
                            }
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("%")) {
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            String aux = respItem.getFieldValue().replaceAll("\\.", "");;
                            aux = aux.replaceAll("\\,", "\\.");
                            try {
                                respostaItemQuestionarioDto.setRespostaPercentual(Double.valueOf(aux));
                            } catch (final Exception e) {
                                e.printStackTrace();
                                respostaItemQuestionarioDto.setRespostaPercentual(new Double(0));
                            }

                            if (respostaItemQuestionarioDto.getRespostaPercentual() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")) {
                                throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
                            }
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("D")) {
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            try {
                                respostaItemQuestionarioDto.setRespostaData(UtilDatas.strToSQLDate(respItem.getFieldValue()));
                            } catch (final Exception e) {
                                e.printStackTrace();
                                respostaItemQuestionarioDto.setRespostaData(null);
                            }
                            if (respostaItemQuestionarioDto.getRespostaData() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")) {
                                throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
                            }
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("H")) {
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            respostaItemQuestionarioDto.setRespostaHora(respItem.getFieldValue().replaceAll(":", ""));
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("Q")) { // Frequencia Cardiaca
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            final String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
                            if (resp != null) {
                                if (resp[0] != null && !resp[0].trim().equalsIgnoreCase("")) {
                                    try {
                                        respostaItemQuestionarioDto.setRespostaValor(new Double(resp[0]));
                                    } catch (final Exception e) {
                                        e.printStackTrace();
                                        respostaItemQuestionarioDto.setRespostaValor(new Double(0));
                                    }
                                }
                            }
                            if (respostaItemQuestionarioDto.getRespostaValor() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")) {
                                throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
                            }
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("G")) { // Mes e Ano
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            final String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
                            if (resp != null) {
                                if (resp[0] != null && !resp[0].trim().equalsIgnoreCase("")) {
                                    respostaItemQuestionarioDto.setRespostaMes(new Integer(resp[0]));
                                }
                                if (resp.length > 1) {
                                    if (resp[1] != null && !resp[1].trim().equalsIgnoreCase("")) {
                                        respostaItemQuestionarioDto.setRespostaAno(new Integer(resp[1]));
                                    }
                                }
                            }
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("*")) { // % e Valor
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            final String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
                            if (resp != null) {
                                if (resp[0] != null && !resp[0].trim().equalsIgnoreCase("")) {
                                    resp[0] = resp[0].replaceAll("\\.", "");
                                    resp[0] = resp[0].replaceAll("\\,", "\\.");
                                    respostaItemQuestionarioDto.setRespostaPercentual(new Double(resp[0]));
                                }
                                if (resp.length > 1) {
                                    if (resp[1] != null && !resp[1].trim().equalsIgnoreCase("")) {
                                        resp[1] = resp[1].replaceAll("\\.", "");
                                        resp[1] = resp[1].replaceAll("\\,", "\\.");
                                        respostaItemQuestionarioDto.setRespostaValor(new Double(resp[1]));
                                    }
                                }
                            }
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("1")) { // Faixa de Valores Numeros
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            final String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
                            if (resp != null) {
                                if (resp[0] != null && !resp[0].trim().equalsIgnoreCase("")) {
                                    resp[0] = resp[0].replaceAll("\\.", "");
                                    resp[0] = resp[0].replaceAll("\\,", "\\.");
                                    respostaItemQuestionarioDto.setRespostaNumero(new Double(resp[0]));
                                }
                                if (resp.length > 1) {
                                    if (resp[1] != null && !resp[1].trim().equalsIgnoreCase("")) {
                                        resp[1] = resp[1].replaceAll("\\.", "");
                                        resp[1] = resp[1].replaceAll("\\,", "\\.");
                                        respostaItemQuestionarioDto.setRespostaNumero2(new Double(resp[1]));
                                    }
                                }
                            }
                            if (respostaItemQuestionarioDto.getRespostaNumero2() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")) {
                                throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
                            }
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("2")) { // Faixa de Valores Decimais
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            final String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
                            if (resp != null) {
                                if (resp[0] != null && !resp[0].trim().equalsIgnoreCase("")) {
                                    resp[0] = resp[0].replaceAll("\\.", "");
                                    resp[0] = resp[0].replaceAll("\\,", "\\.");
                                    respostaItemQuestionarioDto.setRespostaValor(new Double(resp[0]));
                                }
                                if (resp.length > 1) {
                                    if (resp[1] != null && !resp[1].trim().equalsIgnoreCase("")) {
                                        resp[1] = resp[1].replaceAll("\\.", "");
                                        resp[1] = resp[1].replaceAll("\\,", "\\.");
                                        respostaItemQuestionarioDto.setRespostaValor2(new Double(resp[1]));
                                    }
                                }
                            }
                            if (respostaItemQuestionarioDto.getRespostaValor2() == null && questaoQuestionarioDto.getObrigatoria().equalsIgnoreCase("S")) {
                                throw new LogicException(UtilI18N.internacionaliza(request, "questionario.camposObrigatoriosNaoPreenchidos"));
                            }
                        }
                    }
                    if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("8")) { // Listagem
                        if (respItem.getFieldValue() != null && !respItem.getFieldValue().trim().equalsIgnoreCase("")) {
                            respostaItemQuestionarioDto.setRespostaIdListagem(respItem.getFieldValue().trim());
                        }
                    }
                    respostaItemQuestionarioDto = (RespostaItemQuestionarioDTO) respostaItemDao.create(respostaItemQuestionarioDto);

                    if (!respItem.isMultiple()) {
                        if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("R") || questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("C")
                                || questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("X")) {
                            respostaItemQuestionarioOpcoesDto = new RespostaItemQuestionarioOpcoesDTO();
                            respostaItemQuestionarioOpcoesDto.setIdOpcaoRespostaQuestionario(new Integer(Integer.parseInt(respItem.getFieldValue())));
                            respostaItemQuestionarioOpcoesDto.setIdRespostaItemQuestionario(respostaItemQuestionarioDto.getIdRespostaItemQuestionario());
                            respItemOpcoesDao.create(respostaItemQuestionarioOpcoesDto);
                        }
                    } else {
                        if (questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("R") || questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("C")
                                || questaoQuestionarioDto.getTipoQuestao().equalsIgnoreCase("X")) {
                            respItem.setFieldValue(respItem.getFieldValue() + ConstantesQuestionario.CARACTER_SEPARADOR);
                            final String[] resp = respItem.getFieldValue().split(ConstantesQuestionario.CARACTER_SEPARADOR);
                            if (resp != null) {
                                for (int i = 0; i < resp.length; i++) {
                                    if (resp[i] != null && !resp[i].trim().equalsIgnoreCase("")) {
                                        respostaItemQuestionarioOpcoesDto = new RespostaItemQuestionarioOpcoesDTO();
                                        try {
                                            respostaItemQuestionarioOpcoesDto.setIdOpcaoRespostaQuestionario(new Integer(Integer.parseInt(resp[i])));
                                            respostaItemQuestionarioOpcoesDto.setIdRespostaItemQuestionario(respostaItemQuestionarioDto.getIdRespostaItemQuestionario());
                                            respItemOpcoesDao.create(respostaItemQuestionarioOpcoesDto);
                                        } catch (final Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void deleteByIdQuestaoAndIdentificadorResposta(final Integer idQuestaoQuestionario, final Integer idIdentificadorResposta) throws Exception {
        this.getDao().deleteByIdQuestaoAndIdentificadorResposta(idQuestaoQuestionario, idIdentificadorResposta);
    }

    @Override
    public Collection listByIdIdentificadorAndIdQuestao(final Integer idIdentificadorResposta, final Integer idQuestaoQuestionario) throws Exception {
        return this.getDao().listByIdIdentificadorAndIdQuestao(idIdentificadorResposta, idQuestaoQuestionario);
    }

    @Override
    public Collection getRespostasOpcoesByIdRespostaItemQuestionario(final Integer idRespostaItemQuestionario) throws Exception {
        final RespostaItemQuestionarioOpcoesDao respostaItemQuestionarioOpcoesDao = new RespostaItemQuestionarioOpcoesDao();
        return respostaItemQuestionarioOpcoesDao.getRespostasOpcoesByIdRespostaItemQuestionario(idRespostaItemQuestionario);
    }

    @Override
    public void deleteByIdIdentificadorResposta(final RespostaItemQuestionarioDTO resposta) throws ServiceException, LogicException {
        final RespostaItemQuestionarioDao dao = this.getDao();
        final TransactionControler tc = new TransactionControlerImpl(dao.getAliasDB());
        try {
            this.deleteByIdIdentificadorResposta(resposta, tc);
            tc.commit();
            tc.close();
        } catch (final Exception e) {
            e.printStackTrace();
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public void deleteByIdIdentificadorResposta(final RespostaItemQuestionarioDTO resposta, final TransactionControler tc) throws Exception {
        this.validaDelete(resposta);
        final RespostaItemQuestionarioDao dao = this.getDao();
        final RespostaItemQuestionarioAnexosDao riqaDao = new RespostaItemQuestionarioAnexosDao();
        final RespostaItemQuestionarioOpcoesDao riqoDao = new RespostaItemQuestionarioOpcoesDao();
        dao.setTransactionControler(tc);
        riqaDao.setTransactionControler(tc);
        riqoDao.setTransactionControler(tc);
        tc.start();

        final List where = new ArrayList();
        where.add(new Condition("idIdentificadorResposta", "=", resposta.getIdIdentificadorResposta()));
        final Collection respostas = dao.findByCondition(where, new ArrayList());

        if (respostas != null && !respostas.isEmpty()) {
            RespostaItemQuestionarioDTO resp = null;
            for (final Iterator it = respostas.iterator(); it.hasNext();) {
                resp = (RespostaItemQuestionarioDTO) it.next();
                riqoDao.deleteByIdRespostaItemQuestionario(resp.getIdRespostaItemQuestionario());
                riqaDao.deleteByIdRespostaItemQuestionario(resp.getIdRespostaItemQuestionario());
            }
            dao.deleteByIdIdentificadorResposta(resposta.getIdIdentificadorResposta());
        }
    }

}
