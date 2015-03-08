package br.com.centralit.citcorpore.negocio;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CalculoJornadaDTO;
import br.com.centralit.citcorpore.bean.CalendarioDTO;
import br.com.centralit.citcorpore.bean.ExcecaoCalendarioDTO;
import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.bean.RecursoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.TipoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.integracao.CalendarioDao;
import br.com.centralit.citcorpore.integracao.ExcecaoCalendarioDao;
import br.com.centralit.citcorpore.integracao.FeriadoDao;
import br.com.centralit.citcorpore.integracao.JornadaTrabalhoDao;
import br.com.centralit.citcorpore.integracao.RecursoDao;
import br.com.centralit.citcorpore.integracao.ServicoContratoDao;
import br.com.centralit.citcorpore.integracao.TipoLiberacaoDAO;
import br.com.centralit.citcorpore.integracao.TipoMudancaDAO;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilI18N;

public class CalendarioServiceEjb extends CrudServiceImpl implements CalendarioService {

    public static final String HORA_INICIO_CALCULADA = "IC";
    public static final String HORA_TERMINO_CALCULADA = "TC";
    public static final String HORA_INICIO_JORNADA = "IJ";
    public static final String HORA_TERMINO_JORNADA = "TJ";

    public static final String FOLGA = "F";
    public static final String TRABALHO = "T";

    private TransactionControler transactionControler;

    private <DAO extends CrudDAO> void setTransacaoDao(final DAO dao) throws Exception {
        if (transactionControler != null) {
            this.getDao().setTransactionControler(transactionControler);
        }
    }

    private void setTransacao(final TransactionControler transactionControler) throws Exception {
        this.transactionControler = transactionControler;
    }

    private CalendarioDao dao;

    @Override
    protected CalendarioDao getDao() {
        if (dao == null) {
            dao = new CalendarioDao();
        }
        return dao;
    }

    /**
     * Determina a Data Hora final, ou seja, o Prazo Limite.
     *
     * @param calculoDto
     * @param bCalculaHoraInicio
     * @return CalculoJornadaDTO
     * @throws Exception
     */
    public CalculoJornadaDTO calculaDataHoraFinal(final CalculoJornadaDTO calculoDto, final boolean bCalculaHoraInicio, final TransactionControler tc) throws Exception {
        if (calculoDto.getIdCalendario() == null) {
            throw new Exception("ID do calendário não informado para cálculo da data e hora");
        }
        if (calculoDto.getDataHoraInicial() == null) {
            throw new Exception("Data e hora inicial não informadas para cálculo da data e hora");
        }
        if (calculoDto.getPrazoHH() == null) {
            throw new Exception("Prazo em horas não informado para cálculo da data e hora");
        }
        if (calculoDto.getPrazoMM() == null) {
            throw new Exception("Prazo em minutos não informado para cálculo da data e hora");
        }

        this.setTransacao(tc);
        final CalendarioDTO calendarioDto = this.recuperaCalendario(calculoDto.getIdCalendario());

        if (calendarioDto == null) {
            throw new Exception("Serviço Contrato sem calendário. Por favor selecione Calendário em Serviço Contrato!");
        }

        final double slaDefinido = calculoDto.getPrazoHH() + new Double(calculoDto.getPrazoMM()).doubleValue() / 60;

        final Timestamp dataHoraInicioSla = this.calculaDataHoraJornada(calendarioDto, calculoDto.getDataHoraInicial(), HORA_INICIO_CALCULADA, true);

        final double dataHoraInicioSlaDbl = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraInicioSla));

        double prazoDisponivel = this.calculaCargaHoraria(calendarioDto, dataHoraInicioSla);

        double cargaHoraria = prazoDisponivel;

        int difDias = 0;

        Timestamp dataHoraTermino = this.calculaDataHoraJornada(calendarioDto, dataHoraInicioSla, HORA_TERMINO_JORNADA, false);

        while (prazoDisponivel < slaDefinido) {
            dataHoraTermino = this.incrementaDias(dataHoraTermino, 1);
            dataHoraTermino = this.calculaDataHoraJornada(calendarioDto, dataHoraTermino, HORA_TERMINO_JORNADA, false);
            cargaHoraria = this.calculaCargaHorariaTotal(calendarioDto, dataHoraTermino);
            prazoDisponivel += cargaHoraria;
            difDias++;
        }

        if (prazoDisponivel > slaDefinido) {
            double hora = 0;

            if (difDias > 0) {
                double diferenca = cargaHoraria - (prazoDisponivel - slaDefinido);
                final JornadaTrabalhoDTO jornadaDto = this.recuperaJornada(calendarioDto, new Date(dataHoraTermino.getTime()), -1);
                final double inicio[] = jornadaDto.getInicio();
                final double termino[] = jornadaDto.getTermino();
                int i = 1;
                while (i <= 5 && inicio[i] != 99 && diferenca > 0) {
                    final double ch = termino[i] - inicio[i];
                    if (diferenca > ch) {
                        diferenca = diferenca - (termino[i] - inicio[i]);
                        if (diferenca < 0) {
                            diferenca = 0;
                        }
                        hora = inicio[i] + diferenca;
                    } else {
                        hora = inicio[i] + diferenca;
                        diferenca = 0;
                    }
                    i++;
                }
            } else {
                double diferenca = cargaHoraria - (prazoDisponivel - slaDefinido);
                final JornadaTrabalhoDTO jornadaDto = this.recuperaJornada(calendarioDto, new Date(dataHoraTermino.getTime()), -1);
                final double inicio[] = jornadaDto.getInicio();
                final double termino[] = jornadaDto.getTermino();
                int i = 1;
                while (i <= 5 && inicio[i] != 99 && diferenca > 0) {
                    if (dataHoraInicioSlaDbl >= inicio[i] && dataHoraInicioSlaDbl <= termino[i]) {

                        hora = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraInicioSla)) + diferenca;

                        for (int j = i; j <= 5; j++) {
                            if (termino[j] != 0.0 && hora > termino[j] && inicio[j + 1] != 99.9) {
                                hora += inicio[j + 1] - termino[j];
                                diferenca = 0;
                            }
                        }
                    }
                    i++;
                }
            }

            final Date dataRef = new Date(dataHoraTermino.getTime());

            dataHoraTermino = Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(hora) + ":00");
        }

        calculoDto.setDataHoraFinal(dataHoraTermino);
        return calculoDto;
    }

    /**
     * Realiza o cálculo de Tempo Decorrido de acordo com a data hora atual informada e o calendário.
     *
     * @param calculoDto
     *            - Instancia de CalculoJornadaDTO que deverá ser instanciada utilizando o construtor sobrecarregado que recebe o ID do Calendário e a Data Hora inicial.
     * @param dataHoraAtual
     *            - Timestamp da data hora atual.
     * @return CalculoJornadaDTO - DTO de CalculoJornada com os atributos TempoDecorridoHH e TempoDecorridoMM
     * @throws Exception
     * @author carlos.santos
     * @version 1.0 de 03.07.2013 por valdoilo.damasceno
     */
    public CalculoJornadaDTO calculaPrazoDecorrido(final CalculoJornadaDTO calculoDto, final Timestamp dataHoraAtual, final TransactionControler tc) throws Exception {
        if (calculoDto.getIdCalendario() == null) {
            throw new Exception("ID do calendário não informado para cálculo da data e hora");
        }
        if (calculoDto.getDataHoraInicial() == null) {
            throw new Exception("Data e hora inicial não informadas para cálculo da data e hora");
        }

        this.setTransacao(tc);
        final CalendarioDTO calendarioDto = this.recuperaCalendario(calculoDto.getIdCalendario());
        if (calculoDto.getDataHoraInicial().compareTo(dataHoraAtual) > 0) {
            throw new Exception("Data e Hora Inicial maior que Data e Hora Final");
        }

        Timestamp dataHoraInicial = this.calculaDataHoraJornada(calendarioDto, calculoDto.getDataHoraInicial(), HORA_INICIO_CALCULADA, true);

        double prazoDecorrido = 0.00;
        int difDias = this.calculaDiferencaDias(dataHoraAtual, dataHoraInicial);
        boolean bMaisDeUmDia = difDias > 0;
        if (difDias > 0) {
            prazoDecorrido += this.calculaCargaHoraria(calendarioDto, dataHoraInicial);
            while (difDias > 0) {
                dataHoraInicial = this.incrementaDias(dataHoraInicial, 1);
                dataHoraInicial = this.calculaDataHoraJornada(calendarioDto, dataHoraInicial, HORA_INICIO_JORNADA, false);
                difDias = this.calculaDiferencaDias(dataHoraAtual, dataHoraInicial);
                if (difDias > 0) {
                    prazoDecorrido += this.calculaCargaHorariaTotal(calendarioDto, dataHoraInicial);
                } else {
                    bMaisDeUmDia = false;
                }
            }
        }
        if (difDias == 0) {
            final JornadaTrabalhoDTO jornadaDto = this.recuperaJornada(calendarioDto, new Date(dataHoraInicial.getTime()), -1);
            final double horaAtual = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraAtual));
            final double inicio[] = jornadaDto.getInicio();
            if (inicio[1] < horaAtual) {
                boolean bCalculou = false;
                final double termino[] = jornadaDto.getTermino();
                for (int i = 1; i <= 5; i++) {
                    if (inicio[i] == 99) {
                        break;
                    }
                    if (inicio[i] <= horaAtual && termino[i] > horaAtual) {
                        int p = i;

                        if (bMaisDeUmDia) {
                            while (p > 1) {
                                prazoDecorrido += termino[p] - inicio[p]; // Acumula turnos da jornada - OBS VALDOILO - ISSO SÓ FUNCIONA QUANDO O NÚMERO DE DIAS É MAIOR QUE 1
                                p = p - 1;
                            }
                        }

                        if (horaAtual < termino[i]) {
                            double hrInicio = inicio[i];

                            if (bMaisDeUmDia) {
                                prazoDecorrido += horaAtual - hrInicio; // Acumula o tempo decorrido dentro do turno - OBS. VALDOÍLO - ISSO SÓ FUNCIONA QUANDO O NÚMERO DE DIAS É
                                                                        // MAIOR QUE 1
                            } else {
                                hrInicio = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraInicial));
                                prazoDecorrido += horaAtual - hrInicio;

                                double intervalo = 0;
                                for (int j = 1; j <= 5; j++) {
                                    if (jornadaDto.getTermino(j) != null && jornadaDto.getInicio(j + 1) != null) {
                                        final double hrTermino = Util.getHoraDbl(jornadaDto.getTermino(j));
                                        if (horaAtual > hrTermino && hrTermino > hrInicio) {
                                            intervalo += Util.calculaDuracao(jornadaDto.getTermino(j), jornadaDto.getInicio(j + 1));
                                        }
                                    }
                                }

                                if (intervalo < prazoDecorrido) {
                                    prazoDecorrido -= intervalo;
                                }
                            }

                        } else {
                            prazoDecorrido += termino[i] - inicio[i]; // Acumula turno integral da jornada
                        }
                        bCalculou = true;
                    }
                }
                if (!bCalculou) { // Ocorre quando a hora do tsRef está acima do horário de término da jornada
                    final double ch = this.calculaCargaHorariaTotal(calendarioDto, dataHoraInicial);
                    if (bMaisDeUmDia) { // Quando é mais de um dia, acumula a jornada total
                        prazoDecorrido += this.calculaCargaHorariaTotal(calendarioDto, dataHoraInicial);
                    } else {
                        final double horaInicioSlaDbl = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraInicial));
                        if (this.verificarSeHoraEstaForaDaJornadaDeTrabalho(jornadaDto, horaAtual)) { // Verifica se a hora do tsRef está num intervalo da jornada
                            prazoDecorrido = this.retornaProximaHoraTerminoJornada(jornadaDto, horaInicioSlaDbl) - horaInicioSlaDbl;
                        } else {
                            dataHoraInicial = Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(new Date(dataHoraInicial.getTime()), "yyyy-MM-dd") + " "
                                    + Util.getHoraFmtStr(horaAtual) + ":00");
                            prazoDecorrido = ch - this.calculaCargaHoraria(calendarioDto, dataHoraInicial);
                        }
                    }
                }
            }
        }

        calculoDto.setTempoDecorridoHH(new Integer(Util.getHora(prazoDecorrido)));
        calculoDto.setTempoDecorridoMM(new Integer(Util.getMinuto(prazoDecorrido)));
        return calculoDto;
    }

    public CalculoJornadaDTO calculaPrazoDecorridoNovo(final CalculoJornadaDTO calculoDto, final Timestamp dataHoraAtual, final TransactionControler tc) throws Exception {
        if (calculoDto.getIdCalendario() == null) {
            throw new Exception("ID do calendário não informado para cálculo da data e hora");
        }
        if (calculoDto.getDataHoraInicial() == null) {
            throw new Exception("Data e hora inicial não informadas para cálculo da data e hora");
        }

        this.setTransacao(tc);
        final CalendarioDTO calendarioDto = this.recuperaCalendario(calculoDto.getIdCalendario());
        if (calculoDto.getDataHoraInicial().compareTo(dataHoraAtual) > 0) {
            throw new Exception("Data e Hora Inicial maior que Data e Hora Final");
        }

        Timestamp dataHoraInicial = this.calculaDataHoraJornadaNovo(calendarioDto, calculoDto.getDataHoraInicial(), HORA_INICIO_CALCULADA, true);

        final Calendar calendarDataHoraAtual = Calendar.getInstance();
        calendarDataHoraAtual.setTimeInMillis(dataHoraAtual.getTime());

        final Calendar calendarDataHoraInicial = Calendar.getInstance();
        calendarDataHoraInicial.setTimeInMillis(dataHoraInicial.getTime());
        calendarDataHoraAtual.add(Calendar.SECOND, -calendarDataHoraInicial.get(Calendar.SECOND));
        final int segundos = calendarDataHoraAtual.get(Calendar.SECOND);

        double prazoDecorrido = 0.00;
        int difDias = this.calculaDiferencaDias(dataHoraAtual, dataHoraInicial);
        boolean bMaisDeUmDia = difDias > 0;
        if (difDias > 0) {
            prazoDecorrido += this.calculaCargaHoraria(calendarioDto, dataHoraInicial);
            while (difDias > 0) {
                dataHoraInicial = this.incrementaDias(dataHoraInicial, 1);
                dataHoraInicial = this.calculaDataHoraJornadaNovo(calendarioDto, dataHoraInicial, HORA_INICIO_JORNADA, false);
                difDias = this.calculaDiferencaDias(dataHoraAtual, dataHoraInicial);
                if (difDias > 0) {
                    prazoDecorrido += this.calculaCargaHorariaTotal(calendarioDto, dataHoraInicial);
                } else {
                    bMaisDeUmDia = false;
                }
            }
        }
        if (difDias == 0) {
            final JornadaTrabalhoDTO jornadaDto = this.recuperaJornada(calendarioDto, new Date(dataHoraInicial.getTime()), -1);
            final double horaAtual = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraAtual));
            final double inicio[] = jornadaDto.getInicio();
            if (inicio[1] < horaAtual) {
                boolean bCalculou = false;
                final double termino[] = jornadaDto.getTermino();
                for (int i = 1; i <= 5; i++) {
                    if (inicio[i] == 99) {
                        break;
                    }
                    if (inicio[i] <= horaAtual && termino[i] > horaAtual) {
                        int p = i;

                        if (bMaisDeUmDia) {
                            while (p > 1) {
                                prazoDecorrido += termino[p] - inicio[p]; // Acumula turnos da jornada - OBS VALDOILO - ISSO SÓ FUNCIONA QUANDO O NÚMERO DE DIAS É MAIOR QUE 1
                                p = p - 1;
                            }
                        }

                        if (horaAtual < termino[i]) {
                            double hrInicio = inicio[i];

                            if (bMaisDeUmDia) {
                                prazoDecorrido += horaAtual - hrInicio; // Acumula o tempo decorrido dentro do turno - OBS. VALDOÍLO - ISSO SÓ FUNCIONA QUANDO O NÚMERO DE DIAS É
                                                                        // MAIOR QUE 1
                            } else {
                                hrInicio = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraInicial));
                                prazoDecorrido += horaAtual - hrInicio;

                                double intervalo = 0;
                                for (int j = 1; j <= 5; j++) {
                                    if (jornadaDto.getTermino(j) != null && jornadaDto.getInicio(j + 1) != null) {
                                        final double hrTermino = Util.getHoraDbl(jornadaDto.getTermino(j));
                                        if (horaAtual > hrTermino && hrTermino > hrInicio) {
                                            intervalo += Util.calculaDuracao(jornadaDto.getTermino(j), jornadaDto.getInicio(j + 1));
                                        }
                                    }
                                }

                                if (intervalo < prazoDecorrido) {
                                    prazoDecorrido -= intervalo;
                                }
                            }

                        } else {
                            prazoDecorrido += termino[i] - inicio[i]; // Acumula turno integral da jornada
                        }
                        bCalculou = true;
                    }
                }
                if (!bCalculou) { // Ocorre quando a hora do tsRef está acima do horário de término da jornada
                    final double ch = this.calculaCargaHorariaTotal(calendarioDto, dataHoraInicial);
                    if (bMaisDeUmDia) { // Quando é mais de um dia, acumula a jornada total
                        prazoDecorrido += this.calculaCargaHorariaTotal(calendarioDto, dataHoraInicial);
                    } else {
                        final double horaInicioSlaDbl = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraInicial));
                        if (this.verificarSeHoraEstaForaDaJornadaDeTrabalho(jornadaDto, horaAtual)) { // Verifica se a hora do tsRef está num intervalo da jornada
                            prazoDecorrido = this.retornaProximaHoraTerminoJornada(jornadaDto, horaInicioSlaDbl) - horaInicioSlaDbl;
                        } else {
                            dataHoraInicial = Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(new Date(dataHoraInicial.getTime()), "yyyy-MM-dd") + " "
                                    + Util.getHoraFmtStr(horaAtual) + ":00");
                            prazoDecorrido = ch - this.calculaCargaHoraria(calendarioDto, dataHoraInicial);
                        }
                    }
                }
            }
        }

        calculoDto.setTempoDecorridoHH(new Integer(Util.getHora(prazoDecorrido)));
        calculoDto.setTempoDecorridoMM(new Integer(Util.getMinuto(prazoDecorrido)));
        calculoDto.setTempoDecorridoSS(segundos);
        return calculoDto;
    }

    private int calculaDiferencaDias(final Timestamp tsFinal, final Timestamp tsInicial) throws Exception {
        if (tsFinal.compareTo(tsInicial) < 0) {
            return -1;
        }
        int i = 0;
        final SimpleDateFormat spd = new SimpleDateFormat("yyyyMMdd");
        Date dataRef = new Date(tsInicial.getTime());
        String dataRefInv = spd.format(dataRef).trim();
        final String dataFinalInv = spd.format(new Date(tsFinal.getTime())).trim();
        while (dataRefInv.compareTo(dataFinalInv) < 0) {
            dataRef = new Date(UtilDatas.incrementaDiasEmData(dataRef, 1).getTime());
            dataRefInv = spd.format(dataRef).trim();
            i++;
        }
        return i;
    }

    public Timestamp incrementaDias(final Timestamp ts, final int qtdeDias) throws Exception {
        Date dataRef = new Date(ts.getTime());
        dataRef = new Date(UtilDatas.incrementaDiasEmData(dataRef, qtdeDias).getTime());
        return Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataRef, "yyyy-MM-dd") + " " + "00:00:00");
    }

    private Timestamp calculaDataHoraJornada(final CalendarioDTO calendarioDto, final Timestamp dataHoraRef, final String tipoCalculoHora, final boolean bUtilizaHorarioTimestamp)
            throws Exception {
        final JornadaTrabalhoDTO jornadaDto = this.verificaDiaUtil(calendarioDto, dataHoraRef, bUtilizaHorarioTimestamp);
        if (jornadaDto == null) {
            throw new Exception("Não existem jornadas configuradas para este calendário nos próximos 30 dias");
        }

        final double hora = Util.getHoraDbl(UtilDatas.getHoraHHMM(jornadaDto.getDataHoraInicial()));
        double horaUtil = hora;
        if (tipoCalculoHora.equalsIgnoreCase(HORA_INICIO_CALCULADA)) {
            horaUtil = this.calculaHoraUtilInicial(jornadaDto, hora);
        } else if (tipoCalculoHora.equalsIgnoreCase(HORA_INICIO_JORNADA)) {
            horaUtil = this.retornaHoraInicioJornada(jornadaDto);
        } else if (tipoCalculoHora.equalsIgnoreCase(HORA_TERMINO_JORNADA)) {
            horaUtil = this.retornaHoraTerminoJornada(jornadaDto);
        } else {
            horaUtil = this.calculaHoraUtilFinal(jornadaDto, hora);
        }

        final Date dataRef = new Date(jornadaDto.getDataHoraInicial().getTime());
        return Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(horaUtil) + ":00");
    }

    private Timestamp calculaDataHoraJornadaNovo(final CalendarioDTO calendarioDto, final Timestamp dataHoraRef, final String tipoCalculoHora,
            final boolean bUtilizaHorarioTimestamp) throws Exception {
        final JornadaTrabalhoDTO jornadaDto = this.verificaDiaUtilNovo(calendarioDto, dataHoraRef, bUtilizaHorarioTimestamp);
        if (jornadaDto == null) {
            throw new Exception("Não existem jornadas configuradas para este calendário nos próximos 30 dias");
        }

        final Calendar calendarDataHoraInicial = Calendar.getInstance();
        calendarDataHoraInicial.setTimeInMillis(jornadaDto.getDataHoraInicial().getTime());
        final int segundos = calendarDataHoraInicial.get(Calendar.SECOND);

        final double hora = Util.getHoraDbl(UtilDatas.getHoraHHMM(jornadaDto.getDataHoraInicial()));
        double horaUtil = hora;
        if (tipoCalculoHora.equalsIgnoreCase(HORA_INICIO_CALCULADA)) {
            horaUtil = this.calculaHoraUtilInicial(jornadaDto, hora);
        } else if (tipoCalculoHora.equalsIgnoreCase(HORA_INICIO_JORNADA)) {
            horaUtil = this.retornaHoraInicioJornada(jornadaDto);
        } else if (tipoCalculoHora.equalsIgnoreCase(HORA_TERMINO_JORNADA)) {
            horaUtil = this.retornaHoraTerminoJornada(jornadaDto);
        } else {
            horaUtil = this.calculaHoraUtilFinal(jornadaDto, hora);
        }

        final Date dataRef = new Date(jornadaDto.getDataHoraInicial().getTime());
        return Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(horaUtil) + ":" + UtilDatas.adicionaZeroAEsquerda(segundos));
    }

    public JornadaTrabalhoDTO verificaDiaUtil(final CalendarioDTO calendarioDto, final Timestamp dataHoraRef, final boolean bUtilizaHorarioTimestamp) throws Exception {
        double horaRef = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraRef));
        Date dataRef = new Date(dataHoraRef.getTime());
        double horaCalculo = -1;
        if (bUtilizaHorarioTimestamp) {
            horaCalculo = horaRef;
        }
        JornadaTrabalhoDTO jornadaDto = this.recuperaJornada(calendarioDto, dataRef, horaCalculo);
        int i = 0;
        while (jornadaDto == null && i < 30) {
            dataRef = new Date(UtilDatas.incrementaDiasEmData(dataRef, 1).getTime());
            jornadaDto = this.recuperaJornada(calendarioDto, dataRef, -1);
            if (jornadaDto != null) {
                horaRef = this.retornaHoraInicioJornada(jornadaDto);
            }
            i++;
        }

        if (jornadaDto != null) {
            final double horaInicio = this.retornaHoraInicioJornada(jornadaDto);
            if (horaRef > horaInicio) {
                jornadaDto.setDataHoraInicial(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(horaRef) + ":00"));
            } else {
                jornadaDto.setDataHoraInicial(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(horaInicio) + ":00"));
            }
        }

        return jornadaDto;
    }

    public JornadaTrabalhoDTO verificaDiaUtilNovo(final CalendarioDTO calendarioDto, final Timestamp dataHoraRef, final boolean bUtilizaHorarioTimestamp) throws Exception {

        final Calendar calendarDataHoraRef = Calendar.getInstance();
        calendarDataHoraRef.setTimeInMillis(dataHoraRef.getTime());
        final int segundos = calendarDataHoraRef.get(Calendar.SECOND);

        double horaRef = Util.getHoraDbl(UtilDatas.getHoraHHMM(dataHoraRef));
        Date dataRef = new Date(dataHoraRef.getTime());
        double horaCalculo = -1;
        if (bUtilizaHorarioTimestamp) {
            horaCalculo = horaRef;
        }
        JornadaTrabalhoDTO jornadaDto = this.recuperaJornada(calendarioDto, dataRef, horaCalculo);
        int i = 0;
        while (jornadaDto == null && i < 30) {
            dataRef = new Date(UtilDatas.incrementaDiasEmData(dataRef, 1).getTime());
            jornadaDto = this.recuperaJornada(calendarioDto, dataRef, -1);
            if (jornadaDto != null) {
                horaRef = this.retornaHoraInicioJornada(jornadaDto);
            }
            i++;
        }

        if (jornadaDto != null) {
            final double horaInicio = this.retornaHoraInicioJornada(jornadaDto);
            if (horaRef > horaInicio) {
                jornadaDto.setDataHoraInicial(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(horaRef) + ":"
                        + UtilDatas.adicionaZeroAEsquerda(segundos)));
            } else {
                jornadaDto.setDataHoraInicial(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataRef, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(horaInicio) + ":"
                        + UtilDatas.adicionaZeroAEsquerda(segundos)));
            }
        }

        return jornadaDto;
    }

    public JornadaTrabalhoDTO recuperaJornada(final CalendarioDTO calendarioDto, final Date dataRef, final double horaRef) throws Exception {
        final JornadaTrabalhoDTO jornadaDto = this.recuperaJornada(calendarioDto, dataRef);
        if (jornadaDto == null) {
            return null;
        }

        if (horaRef < 0) {
            return jornadaDto;
        }

        final double horaFinal = this.retornaHoraTerminoJornada(jornadaDto);
        if (horaFinal < horaRef) {
            return null;
        }

        // double horaInicial = retornaHoraInicioJornada(jornadaDto);
        // if (horaInicial > horaRef)
        // return null;

        return jornadaDto;
    }

    @Override
    public CalendarioDTO recuperaCalendario(final Integer idCalendario) throws Exception {
        if (transactionControler != null && transactionControler.isStarted() == true) {
            this.getDao().setTransactionControler(transactionControler);
        }
        final CalendarioDTO calendarioDto = new CalendarioDTO();
        calendarioDto.setIdCalendario(idCalendario);
        return (CalendarioDTO) this.getDao().restore(calendarioDto);
    }

    @Override
    public JornadaTrabalhoDTO recuperaJornada(final CalendarioDTO calendarioDto, final Date dataRef) throws Exception {
        Integer idJornada = calendarioDto.getIdJornada(dataRef);

        final ExcecaoCalendarioDTO excecaoDto = this.getExcecaoCalendarioDAO().findByIdCalendarioAndData(calendarioDto.getIdCalendario(), dataRef);
        if (excecaoDto != null && excecaoDto.getTipo().equalsIgnoreCase(TRABALHO)) {
            idJornada = excecaoDto.getIdJornada();
        }
        if (excecaoDto != null && excecaoDto.getTipo().equalsIgnoreCase(FOLGA)) {
            return null;
        }

        if (idJornada == null) {
            return null;
        }

        boolean bFeriado = false;
        if (calendarioDto.getConsideraFeriados() != null && calendarioDto.getConsideraFeriados().equalsIgnoreCase("S")) {
            bFeriado = this.getFeriadoDAO().isFeriado(dataRef, null, null);
        }

        if (bFeriado) {
            return null;
        }

        JornadaTrabalhoDTO jornadaDto = new JornadaTrabalhoDTO();
        jornadaDto.setIdJornada(idJornada);
        jornadaDto = (JornadaTrabalhoDTO) this.getJornadaTrabalhoDAO().restore(jornadaDto);
        return jornadaDto;
    }

    private double calculaHoraUtilInicial(final JornadaTrabalhoDTO jornadaDto, final double horaRef) throws Exception {
        if (jornadaDto == null) {
            return 0.0;
        }

        final double[] hrInicio = new double[] {99, 99, 99, 99, 99, 99};
        final double[] hrTermino = new double[] {0, 0, 0, 0, 0, 0};

        for (int i = 1; i <= 5; i++) {
            if (jornadaDto.getInicio(i) != null) {
                hrInicio[i] = Util.getHoraDbl(jornadaDto.getInicio(i));
                hrTermino[i] = Util.getHoraDbl(jornadaDto.getTermino(i));
            }
        }

        double horaUtilDbl = 0.0;
        int i = 1;
        while (horaUtilDbl == 0.0 && i <= 5) {
            if (hrInicio[i] != 99 && (hrInicio[i] <= horaRef || i > 1) && hrTermino[i] >= horaRef) {
                horaUtilDbl = hrInicio[i];
            }
            i++;
        }

        if (horaUtilDbl > horaRef) {
            return horaUtilDbl;
        } else {
            return horaRef;
        }
    }

    private double calculaHoraUtilFinal(final JornadaTrabalhoDTO jornadaDto, final double horaRef) throws Exception {
        if (jornadaDto == null) {
            return 0.0;
        }

        final double[] hrInicio = new double[] {99, 99, 99, 99, 99, 99};
        final double[] hrTermino = new double[] {0, 0, 0, 0, 0, 0};

        for (int i = 1; i <= 5; i++) {
            if (jornadaDto.getInicio(i) != null) {
                hrInicio[i] = Util.getHoraDbl(jornadaDto.getInicio(i));
                hrTermino[i] = Util.getHoraDbl(jornadaDto.getTermino(i));
            }
        }

        double horaUtilDbl = 99;
        int i = 1;
        while (horaUtilDbl == 99 && i <= 5) {
            if (hrTermino[i] != 99 && hrTermino[i] >= horaRef) {
                horaUtilDbl = hrTermino[i];
            }
            i++;
        }

        if (horaUtilDbl > horaRef) {
            return horaUtilDbl;
        } else {
            return horaRef;
        }
    }

    private double retornaHoraTerminoJornada(final JornadaTrabalhoDTO jornadaDto) throws Exception {
        double horaFinalDbl = -1;
        if (jornadaDto.getTermino5() != null && jornadaDto.getTermino5().trim().length() > 0) {
            horaFinalDbl = Util.getHoraDbl(jornadaDto.getTermino5());
        } else if (jornadaDto.getTermino4() != null && jornadaDto.getTermino4().trim().length() > 0) {
            horaFinalDbl = Util.getHoraDbl(jornadaDto.getTermino4());
        } else if (jornadaDto.getTermino3() != null && jornadaDto.getTermino3().trim().length() > 0) {
            horaFinalDbl = Util.getHoraDbl(jornadaDto.getTermino3());
        } else if (jornadaDto.getTermino2() != null && jornadaDto.getTermino2().trim().length() > 0) {
            horaFinalDbl = Util.getHoraDbl(jornadaDto.getTermino2());
        } else if (jornadaDto.getTermino1() != null && jornadaDto.getTermino1().trim().length() > 0) {
            horaFinalDbl = Util.getHoraDbl(jornadaDto.getTermino1());
        }

        return horaFinalDbl;
    }

    private double retornaHoraInicioJornada(final JornadaTrabalhoDTO jornadaDto) throws Exception {
        double horaInicialDbl = -1;
        if (jornadaDto.getInicio1() != null && jornadaDto.getInicio1().trim().length() > 0) {
            horaInicialDbl = Util.getHoraDbl(jornadaDto.getInicio1());
        } else if (jornadaDto.getInicio2() != null && jornadaDto.getInicio2().trim().length() > 0) {
            horaInicialDbl = Util.getHoraDbl(jornadaDto.getInicio2());
        } else if (jornadaDto.getInicio3() != null && jornadaDto.getInicio3().trim().length() > 0) {
            horaInicialDbl = Util.getHoraDbl(jornadaDto.getInicio3());
        } else if (jornadaDto.getInicio4() != null && jornadaDto.getInicio4().trim().length() > 0) {
            horaInicialDbl = Util.getHoraDbl(jornadaDto.getInicio4());
        } else if (jornadaDto.getInicio5() != null && jornadaDto.getInicio5().trim().length() > 0) {
            horaInicialDbl = Util.getHoraDbl(jornadaDto.getInicio5());
        }

        return horaInicialDbl;
    }

    private double calculaIntervalos(final JornadaTrabalhoDTO jornadaDto, final double horaInicial) throws Exception {
        double intervalo = 0;
        for (int i = 1; i <= 5; i++) {
            if (jornadaDto.getTermino(i) != null && jornadaDto.getInicio(i + 1) != null) {
                final double hr = Util.getHoraDbl(jornadaDto.getTermino(i));
                if (hr >= horaInicial) {
                    intervalo += Util.calculaDuracao(jornadaDto.getTermino(i), jornadaDto.getInicio(i + 1));
                }
            }
        }
        return intervalo;
    }

    /**
     * Verifica se a hora informada está fora da Jornada de trabalho. Ou seja, fora de um intervalo válido na Jornada.
     *
     * @param jornadaDto
     * @param hora
     * @return true - Está fora da Jornada de Trabalho.
     * @throws Exception
     * @author valdoilo.damasceno
     */
    private boolean verificarSeHoraEstaForaDaJornadaDeTrabalho(final JornadaTrabalhoDTO jornadaDto, final double hora) throws Exception {
        boolean result = true;
        final double inicio[] = jornadaDto.getInicio();
        final double termino[] = jornadaDto.getTermino();
        for (int i = 1; i <= 5; i++) {
            if (hora >= inicio[i] && hora <= termino[i]) {
                result = false;
                break;
            }
        }

        return result;
    }

    private double calculaCargaHoraria(final CalendarioDTO calendarioDto, final Timestamp ts) throws Exception {
        final Date dataRef = new Date(ts.getTime());
        final JornadaTrabalhoDTO jornadaDto = this.recuperaJornada(calendarioDto, dataRef);
        if (jornadaDto == null) {
            return 0.0;
        }

        final double horaInicial = Util.getHoraDbl(UtilDatas.getHoraHHMM(ts));
        final double horaFinal = this.retornaHoraTerminoJornada(jornadaDto);

        final double result = horaFinal - horaInicial - this.calculaIntervalos(jornadaDto, horaInicial);
        if (result > 0) {
            return result;
        }
        return 0;
    }

    private double calculaCargaHorariaTotal(final CalendarioDTO calendarioDto, final Timestamp ts) throws Exception {
        final Date dataRef = new Date(ts.getTime());
        final JornadaTrabalhoDTO jornadaDto = this.recuperaJornada(calendarioDto, dataRef);
        if (jornadaDto == null) {
            return 0.0;
        }

        final double horaInicial = this.retornaHoraInicioJornada(jornadaDto);
        final double horaFinal = this.retornaHoraTerminoJornada(jornadaDto);

        final double result = horaFinal - horaInicial - this.calculaIntervalos(jornadaDto, horaInicial);
        if (result > 0) {
            return result;
        } else {
            return 0;
        }
    }

    @Override
    public boolean jornadaDeTrabalhoEmUso(final JornadaTrabalhoDTO jornadaTrabalhoDTO) throws Exception {
        boolean resp = false;

        final Integer idJornada = jornadaTrabalhoDTO.getIdJornada();
        resp = this.getDao().verificaJornada(idJornada);

        return resp;
    }

    @Override
    public boolean verificaSeExisteCalendario(final CalendarioDTO calendarioDTO) throws Exception {
        return this.getDao().verificaSeExisteCalendario(calendarioDTO);
    }

    /**
     * retorna o fim de um jornada proxima a hora passada
     *
     * @param jornadaDto
     * @param hora
     * @return
     */
    private double retornaProximaHoraTerminoJornada(final JornadaTrabalhoDTO jornadaDto, final double hora) {
        final double termino[] = jornadaDto.getTermino();
        for (int i = 1; i <= 5; i++) {
            if (termino[i] == 0) {
                break;
            }
            if (termino[i] != 0 && termino[i] >= hora) {
                return termino[i];
            }
        }
        return 0;
    }

    /**
     * @author euler.ramos
     * @param idCalendario
     * @return
     * @throws Exception
     */
    @Override
    public Object verificaSePermiteExcluir(final DocumentHTML document, final HttpServletRequest request, final CalendarioDTO calendario) throws Exception {
        String resultado = "excluir";
        /*
         * Tabelas que se relacionam com calendario:
         * RECURSO
         * SERVICOCONTRATO
         * TIPOLIBERACAO
         * TIPOMUDANCA
         */
        final List<RecursoDTO> listaRecursos = this.getRecursoDAO().findByIdCalendario(calendario.getIdCalendario());
        if (listaRecursos != null && listaRecursos.size() > 0) {
            resultado = UtilI18N.internacionaliza(request, "calendario.naoExDevidoRecursos");
        } else {
            final List<ServicoContratoDTO> listaservicoContrato = this.getServicoContratoDAO().findByIdCalendario(calendario.getIdCalendario());
            if (listaservicoContrato != null && listaservicoContrato.size() > 0) {
                resultado = UtilI18N.internacionaliza(request, "calendario.naoExDevidoServicoContrato");
            } else {
                final List<TipoLiberacaoDTO> listaTipoLiberacao = this.getTipoLiberacaoDAO().findByIdCalendario(calendario.getIdCalendario());
                if (listaTipoLiberacao != null && listaTipoLiberacao.size() > 0) {
                    resultado = UtilI18N.internacionaliza(request, "calendario.naoExDevidoTipoLiberacao");
                } else {
                    final List<TipoMudancaDTO> listaTipoMudanca = this.getTipoMudancaDAO().findByIdCalendario(calendario.getIdCalendario());
                    if (listaTipoMudanca != null && listaTipoMudanca.size() > 0) {
                        resultado = UtilI18N.internacionaliza(request, "calendario.naoExDevidoTipoMudanca");
                    }
                }
            }
        }
        return resultado;
    }

    private ExcecaoCalendarioDao excecaoCalendarioDAO;

    private ExcecaoCalendarioDao getExcecaoCalendarioDAO() throws Exception {
        if (excecaoCalendarioDAO == null) {
            excecaoCalendarioDAO = new ExcecaoCalendarioDao();
            this.setTransacaoDao(excecaoCalendarioDAO);
        }
        return excecaoCalendarioDAO;
    }

    private FeriadoDao feriadoDAO;

    private FeriadoDao getFeriadoDAO() throws Exception {
        if (feriadoDAO == null) {
            feriadoDAO = new FeriadoDao();
            this.setTransacaoDao(feriadoDAO);
        }
        return feriadoDAO;
    }

    private JornadaTrabalhoDao jornadaTrabalhoDAO;

    private JornadaTrabalhoDao getJornadaTrabalhoDAO() throws Exception {
        if (jornadaTrabalhoDAO == null) {
            jornadaTrabalhoDAO = new JornadaTrabalhoDao();
            this.setTransacaoDao(jornadaTrabalhoDAO);
        }
        return jornadaTrabalhoDAO;
    }

    private RecursoDao recursoDAO;

    private RecursoDao getRecursoDAO() throws Exception {
        if (recursoDAO == null) {
            recursoDAO = new RecursoDao();
            this.setTransacaoDao(recursoDAO);
        }
        return recursoDAO;
    }

    private ServicoContratoDao servicoContratoDao;

    private ServicoContratoDao getServicoContratoDAO() throws Exception {
        if (servicoContratoDao == null) {
            servicoContratoDao = new ServicoContratoDao();
            this.setTransacaoDao(servicoContratoDao);
        }
        return servicoContratoDao;
    }

    private TipoLiberacaoDAO tipoLiberacaoDAO;

    private TipoLiberacaoDAO getTipoLiberacaoDAO() throws Exception {
        if (tipoLiberacaoDAO == null) {
            tipoLiberacaoDAO = new TipoLiberacaoDAO();
            this.setTransacaoDao(tipoLiberacaoDAO);
        }
        return tipoLiberacaoDAO;
    }

    private TipoMudancaDAO tipoMudancaDAO;

    private TipoMudancaDAO getTipoMudancaDAO() throws Exception {
        if (tipoMudancaDAO == null) {
            tipoMudancaDAO = new TipoMudancaDAO();
            this.setTransacaoDao(tipoMudancaDAO);
        }
        return tipoMudancaDAO;
    }

}
