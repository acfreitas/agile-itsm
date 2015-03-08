package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JornadaTrabalhoDTO;
import br.com.centralit.citcorpore.integracao.CalendarioDao;
import br.com.centralit.citcorpore.integracao.JornadaTrabalhoDao;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("rawtypes")
public class JornadaTrabalhoServiceEjb extends CrudServiceImpl implements JornadaTrabalhoService {

    private JornadaTrabalhoDao dao;

    @Override
    protected JornadaTrabalhoDao getDao() {
        if (dao == null) {
            dao = new JornadaTrabalhoDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        this.calculaCargaHoraria((JornadaTrabalhoDTO) arg0);
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        this.calculaCargaHoraria((JornadaTrabalhoDTO) arg0);
    }

    protected void calculaCargaHoraria(final JornadaTrabalhoDTO jornadaTrabalhoDto) throws Exception {
        double cargaHoraria = 0.0;
        if (jornadaTrabalhoDto.getInicio1() != null && jornadaTrabalhoDto.getInicio1().trim().length() > 0) {
            if (jornadaTrabalhoDto.getTermino1() == null) {
                throw new Exception(this.i18nMessage("jornadaTrabalho.horaTerminoIntervaloUm"));
            }

            final String jornadaInicio = this.tratarDoisPontos(jornadaTrabalhoDto.getInicio1());
            final String jornadaTermino = this.tratarDoisPontos(jornadaTrabalhoDto.getTermino1());

            cargaHoraria += Util.calculaDuracao(jornadaInicio, jornadaTermino);

        }
        if (jornadaTrabalhoDto.getInicio2() != null && jornadaTrabalhoDto.getInicio2().trim().length() > 0) {
            if (jornadaTrabalhoDto.getTermino2() == null) {
                throw new Exception(this.i18nMessage("jornadaTrabalho.horaTerminoIntervaloDois"));
            }

            final String jornadaInicio = this.tratarDoisPontos(jornadaTrabalhoDto.getInicio2());
            final String jornadaTermino = this.tratarDoisPontos(jornadaTrabalhoDto.getTermino2());

            cargaHoraria += Util.calculaDuracao(jornadaInicio, jornadaTermino);

        }
        if (jornadaTrabalhoDto.getInicio3() != null && jornadaTrabalhoDto.getInicio3().trim().length() > 0) {
            if (jornadaTrabalhoDto.getTermino3() == null) {
                throw new Exception(this.i18nMessage("jornadaTrabalho.horaTerminoIntervaloTres"));
            }

            final String jornadaInicio = this.tratarDoisPontos(jornadaTrabalhoDto.getInicio3());
            final String jornadaTermino = this.tratarDoisPontos(jornadaTrabalhoDto.getTermino3());

            cargaHoraria += Util.calculaDuracao(jornadaInicio, jornadaTermino);

        }
        if (jornadaTrabalhoDto.getInicio4() != null && jornadaTrabalhoDto.getInicio4().trim().length() > 0) {
            if (jornadaTrabalhoDto.getTermino4() == null) {
                throw new Exception(this.i18nMessage("jornadaTrabalho.horaTerminoIntervaloQuatro"));
            }

            final String jornadaInicio = this.tratarDoisPontos(jornadaTrabalhoDto.getInicio4());
            final String jornadaTermino = this.tratarDoisPontos(jornadaTrabalhoDto.getTermino4());

            cargaHoraria += Util.calculaDuracao(jornadaInicio, jornadaTermino);
        }
        if (jornadaTrabalhoDto.getInicio5() != null && jornadaTrabalhoDto.getInicio5().trim().length() > 0) {
            if (jornadaTrabalhoDto.getTermino5() == null) {
                throw new Exception(this.i18nMessage("jornadaTrabalho.horaTerminoIntervaloCinco"));
            }

            final String jornadaInicio = this.tratarDoisPontos(jornadaTrabalhoDto.getInicio5());
            final String jornadaTermino = this.tratarDoisPontos(jornadaTrabalhoDto.getTermino5());

            cargaHoraria += Util.calculaDuracao(jornadaInicio, jornadaTermino);
        }
        jornadaTrabalhoDto.setCargaHoraria(Util.getHoraFmtStr(cargaHoraria));
    }

    public String tratarDoisPontos(String hora) throws Exception {
        String[] arrayHora;

        arrayHora = hora.split(":");
        hora = arrayHora[0] + arrayHora[1];

        return hora;
    }

    @Override
    public String deletarJornada(final IDto model) throws ServiceException, Exception {
        String resp = "";

        final JornadaTrabalhoDTO jornadaTrabalhoDTO = (JornadaTrabalhoDTO) model;
        final CalendarioDao calendarioDao = new CalendarioDao();
        final JornadaTrabalhoDao jornadaTrabalhoDao = new JornadaTrabalhoDao();

        try {
            if (calendarioDao.verificaJornada(jornadaTrabalhoDTO.getIdJornada())) {
                resp = this.i18nMessage("citcorpore.comum.registroNaoPodeSerExcluido");
            } else {
                jornadaTrabalhoDTO.setDataFim(UtilDatas.getDataAtual());
                jornadaTrabalhoDao.updateNotNull(jornadaTrabalhoDTO);
                resp = this.i18nMessage("MSG07");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        return resp;
    }

    @Override
    public Collection listarJornadasAtivas() throws Exception {
        return this.getDao().listarJornadasAtivas();
    }

    @Override
    public boolean verificaJornadaExistente(final JornadaTrabalhoDTO jornadaTrabalho) throws Exception {
        return this.getDao().verificaJornadaExistente(jornadaTrabalho);
    }

}
