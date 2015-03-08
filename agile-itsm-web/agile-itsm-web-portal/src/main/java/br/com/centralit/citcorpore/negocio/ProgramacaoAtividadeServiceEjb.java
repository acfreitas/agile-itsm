package br.com.centralit.citcorpore.negocio;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.AtividadesOSDTO;
import br.com.centralit.citcorpore.bean.EventoDTO;
import br.com.centralit.citcorpore.bean.ExecucaoAtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.integracao.AtividadesOSDao;
import br.com.centralit.citcorpore.integracao.ExecucaoAtividadePeriodicaDao;
import br.com.centralit.citcorpore.integracao.ProgramacaoAtividadeDao;
import br.com.centralit.citcorpore.regras.RegraProgramacaoAtividade;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;

public class ProgramacaoAtividadeServiceEjb extends CrudServiceImpl implements ProgramacaoAtividadeService {

    private ProgramacaoAtividadeDao dao;

    @Override
    protected ProgramacaoAtividadeDao getDao() {
        if (dao == null) {
            dao = new ProgramacaoAtividadeDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        final ProgramacaoAtividadeDTO programacaoAtividadeDto = (ProgramacaoAtividadeDTO) arg0;
        this.validaProgramacao(programacaoAtividadeDto);
        programacaoAtividadeDto.setHoraInicio(programacaoAtividadeDto.getHoraInicio().replaceAll(":", ""));
        programacaoAtividadeDto.setHoraFim(programacaoAtividadeDto.getHoraFim().replaceAll(":", ""));
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        this.validaCreate(arg0);
    }

    @Override
    public Collection findByIdAtividadePeriodica(final Integer parm) throws Exception {
        try {
            final Collection<ProgramacaoAtividadeDTO> col = this.getDao().findByIdAtividadePeriodica(parm);
            if (col != null) {
                for (final ProgramacaoAtividadeDTO programacaoAtividadeDto : col) {

                    if (programacaoAtividadeDto.getIdAtividadesOs() != null) {
                        final AtividadesOSService atividadesOSService = (AtividadesOSService) ServiceLocator.getInstance().getService(AtividadesOSService.class, null);
                        AtividadesOSDTO atividadesOSDTO = new AtividadesOSDTO();
                        atividadesOSDTO.setIdAtividadesOS(programacaoAtividadeDto.getIdAtividadesOs());
                        atividadesOSDTO = (AtividadesOSDTO) atividadesOSService.restore(atividadesOSDTO);

                        programacaoAtividadeDto.setNomeAtividadeOs(atividadesOSDTO.getDescricaoAtividade());
                    }

                    programacaoAtividadeDto.setHoraInicio(UtilFormatacao.formataHoraHHMM(programacaoAtividadeDto.getHoraInicio()));
                    programacaoAtividadeDto.setHoraFim(UtilFormatacao.formataHoraHHMM(programacaoAtividadeDto.getHoraFim()));
                    RegraProgramacaoAtividade.setDescricao(programacaoAtividadeDto);
                }
            }
            return col;
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

    public EventoDTO getEvento(final AtividadePeriodicaDTO atividadePeriodicaDto, final ProgramacaoAtividadeDTO programacaoAtividadeDto, final java.util.Date dataRef,
            final String hora) throws Exception {
        final String horaFmt = UtilFormatacao.formataHoraHHMM(hora);

        final EventoDTO eventoDto = new EventoDTO();
        eventoDto.setId("" + programacaoAtividadeDto.getIdProgramacaoAtividade() + "#" + hora);
        eventoDto.setIdProgramacao(programacaoAtividadeDto.getIdProgramacaoAtividade());
        if (atividadePeriodicaDto.getNomeTipoMudanca() != null) {
            eventoDto.setTitle(atividadePeriodicaDto.getTituloAtividade() + "\n" + "Tipo: " + atividadePeriodicaDto.getNomeTipoMudanca());
        } else {
            eventoDto.setTitle(atividadePeriodicaDto.getTituloAtividade());
        }
        eventoDto.setNomeTipoMudanca(atividadePeriodicaDto.getNomeTipoMudanca());
        eventoDto.setStart(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataRef, "yyyy-MM-dd") + " " + horaFmt + ":00"));

        double aux = Util.getHoraDbl(hora) + programacaoAtividadeDto.getDuracaoEstimada().doubleValue() / 60;
        java.util.Date dataAux = dataRef;
        if (aux > 24) {
            aux = aux - 24;
            dataAux = UtilDatas.alteraData(dataAux, 1, Calendar.DAY_OF_MONTH);
        }
        eventoDto.setEnd(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(dataAux, "yyyy-MM-dd") + " " + Util.getHoraFmtStr(aux) + ":00"));
        eventoDto.setAllDay(false);
        eventoDto.setClassName("agendado");
        eventoDto.setHoraInicio(horaFmt);
        eventoDto.setData(dataRef);

        this.adicionaAtividadeOs(atividadePeriodicaDto, programacaoAtividadeDto, eventoDto);

        return eventoDto;
    }

    private void adicionaAtividadeOs(final AtividadePeriodicaDTO atividadePeriodicaDto, final ProgramacaoAtividadeDTO programacaoAtividadeDto, final EventoDTO eventoDto)
            throws Exception {
        final AtividadesOSDao atividadesOSDao = new AtividadesOSDao();
        final AtividadesOSDTO atividadesOSDTO = new AtividadesOSDTO();

        if (programacaoAtividadeDto != null) {
            AtividadesOSDTO beanatividadesOS = new AtividadesOSDTO();
            if (programacaoAtividadeDto.getIdAtividadesOs() != null) {
                atividadesOSDTO.setIdAtividadesOS(programacaoAtividadeDto.getIdAtividadesOs());
            }
            if (atividadesOSDTO.getIdAtividadesOS() != null) {
                final List<AtividadesOSDTO> listAtividadesOS = (List<AtividadesOSDTO>) atividadesOSDao.listOSNumeroAtividade(atividadesOSDTO.getIdAtividadesOS());
                if (listAtividadesOS != null && listAtividadesOS.size() > 0) {
                    beanatividadesOS = listAtividadesOS.get(0);
                }

            }
            if (beanatividadesOS != null) {
                if (beanatividadesOS.getNumeroOS() != null) {
                    eventoDto.setNumeroOS(beanatividadesOS.getNumeroOS());
                }
                if (beanatividadesOS.getDescricaoAtividade() != null) {
                    eventoDto.setDescricaoAtividadeOS(beanatividadesOS.getDescricaoAtividade());
                    eventoDto.setTitle("(O.S: " + beanatividadesOS.getNumeroOS() + ")" + beanatividadesOS.getDescricaoAtividade());
                }

            }
        }
    }

    public Collection<EventoDTO> verificaExecucao(final AtividadePeriodicaDTO atividadePeriodicaDto, final ProgramacaoAtividadeDTO programacaoAtividadeDto,
            final java.util.Date dataRef) throws Exception {
        final Collection<EventoDTO> colEventos = new ArrayList<>();
        final java.util.Date dataExecucao = RegraProgramacaoAtividade.getDataProximaExecucao(programacaoAtividadeDto, dataRef);
        if (dataExecucao != null && dataExecucao.compareTo(dataRef) >= 0) {
            programacaoAtividadeDto.setProximaExecucao(new java.sql.Date(dataExecucao.getTime()));
        }
        if (dataExecucao != null && dataExecucao.compareTo(dataRef) == 0) {
            colEventos.add(this.getEvento(atividadePeriodicaDto, programacaoAtividadeDto, dataRef, programacaoAtividadeDto.getHoraInicio()));
            if (programacaoAtividadeDto.getRepeticao().equals("S")) {
                double intervalo = programacaoAtividadeDto.getRepeticaoIntervalo().doubleValue();
                if (programacaoAtividadeDto.getRepeticaoTipoIntervalo().equals("M")) {
                    intervalo = intervalo / 60;
                }
                final double limite = Util.getHoraDbl(programacaoAtividadeDto.getHoraFim());
                double proximaHora = Util.getHoraDbl(programacaoAtividadeDto.getHoraInicio()) + intervalo;
                while (proximaHora <= limite) {
                    colEventos.add(this.getEvento(atividadePeriodicaDto, programacaoAtividadeDto, dataRef, Util.getHoraStr(proximaHora)));
                    proximaHora += intervalo;
                }
            }
        }
        return colEventos;
    }

    @Override
    public Collection<EventoDTO> findEventosAgenda(final AtividadePeriodicaDTO atividadePeriodicaDto, final java.util.Date dataInicio, final Integer qtdeDias) throws Exception {
        final Collection<ProgramacaoAtividadeDTO> colProgramacao = new ProgramacaoAtividadeDao().findByIdAtividadePeriodica(atividadePeriodicaDto.getIdAtividadePeriodica());
        if (colProgramacao == null) {
            return null;
        }

        final Collection<EventoDTO> colEventos = new ArrayList<>();
        for (int i = 0; i <= qtdeDias; i++) {
            final Calendar calendar = Calendar.getInstance();
            final java.util.Date dataRef = UtilDatas.alteraData(dataInicio, i, Calendar.DAY_OF_MONTH);
            calendar.setTime(dataRef);
            for (final ProgramacaoAtividadeDTO programacaoAtividadeDto : colProgramacao) {
                colEventos.addAll(this.verificaExecucao(atividadePeriodicaDto, programacaoAtividadeDto, dataRef));
            }
        }

        final ExecucaoAtividadePeriodicaDao execucaoAtividadeDao = new ExecucaoAtividadePeriodicaDao();
        for (final EventoDTO eventoDto : colEventos) {
            Integer idExecucaoAtividadePeriodica = new Integer(0);

            final List lstExec = (List) execucaoAtividadeDao.findByAtvDataHora(atividadePeriodicaDto.getIdAtividadePeriodica(), new java.sql.Date(eventoDto.getData().getTime()),
                    eventoDto.getHoraInicio(), eventoDto.getIdProgramacao());
            if (lstExec != null && lstExec.size() > 0) {
                final ExecucaoAtividadePeriodicaDTO execucaoAtividadePeriodicaDTO = (ExecucaoAtividadePeriodicaDTO) lstExec.get(0);
                idExecucaoAtividadePeriodica = execucaoAtividadePeriodicaDTO.getIdExecucaoAtividadePeriodica();
                if (execucaoAtividadePeriodicaDTO.getSituacao() == null) {
                    execucaoAtividadePeriodicaDTO.setSituacao("");
                }
                if (execucaoAtividadePeriodicaDTO.getSituacao().equalsIgnoreCase("E")) {
                    eventoDto.setClassName("emExecucao");
                    eventoDto.setTitle(eventoDto.getTitle() + "\n" + "Atualizado em: " + UtilDatas.dateToSTR(execucaoAtividadePeriodicaDTO.getDataExecucao()) + " - "
                            + execucaoAtividadePeriodicaDTO.getHoraExecucao());
                }
                if (execucaoAtividadePeriodicaDTO.getSituacao().equalsIgnoreCase("S")) {
                    eventoDto.setClassName("suspenso");
                    eventoDto.setTitle(eventoDto.getTitle() + "\n" + "Registro em: " + UtilDatas.dateToSTR(execucaoAtividadePeriodicaDTO.getDataExecucao()) + " - "
                            + execucaoAtividadePeriodicaDTO.getHoraExecucao());
                }
                if (execucaoAtividadePeriodicaDTO.getSituacao().equalsIgnoreCase("F")) {
                    eventoDto.setClassName("executado");
                    eventoDto.setTitle(eventoDto.getTitle() + "\n" + "Executado em: " + UtilDatas.dateToSTR(execucaoAtividadePeriodicaDTO.getDataExecucao()) + " - "
                            + execucaoAtividadePeriodicaDTO.getHoraExecucao());
                }
            }

            eventoDto.setIdExecucao(idExecucaoAtividadePeriodica);
        }
        return colEventos;
    }

    @Override
    public void validaProgramacao(final ProgramacaoAtividadeDTO programacaoAtividadeDto) throws Exception {
        RegraProgramacaoAtividade.validaProgramacao(programacaoAtividadeDto);
    }

    @Override
    public Collection findByIdAtividadePeriodicaOrderDataHora(final Integer parm) throws Exception {
        try {
            final Collection<ProgramacaoAtividadeDTO> col = this.getDao().findByIdAtividadePeriodicaOrderDataHora(parm);
            if (col != null) {
                for (final ProgramacaoAtividadeDTO programacaoAtividadeDto : col) {
                    programacaoAtividadeDto.setHoraInicio(UtilFormatacao.formataHoraHHMM(programacaoAtividadeDto.getHoraInicio()));
                    programacaoAtividadeDto.setHoraFim(UtilFormatacao.formataHoraHHMM(programacaoAtividadeDto.getHoraFim()));
                    RegraProgramacaoAtividade.setDescricao(programacaoAtividadeDto);
                }
            }
            return col;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
