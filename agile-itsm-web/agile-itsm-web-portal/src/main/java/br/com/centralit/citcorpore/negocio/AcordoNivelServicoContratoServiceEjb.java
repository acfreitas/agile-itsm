package br.com.centralit.citcorpore.negocio;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ResultadosEsperadosDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoContratoDao;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.ResultadosEsperadosDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({"rawtypes", "unchecked"})
public class AcordoNivelServicoContratoServiceEjb extends CrudServiceImpl implements AcordoNivelServicoContratoService {

    private AcordoNivelServicoContratoDao dao;

    @Override
    protected AcordoNivelServicoContratoDao getDao() {
        if (dao == null) {
            dao = new AcordoNivelServicoContratoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean verificaDataContrato(final HashMap mapFields) throws Exception {

        final String dtInicioSLA = (String) mapFields.get("DATAINICIO");
        final Integer idContrato = Integer.parseInt((String) mapFields.get("SESSION.NUMERO_CONTRATO_EDICAO"));

        Date dataInicioSLA = null;

        try {
            dataInicioSLA = new SimpleDateFormat("dd/MM/yyyy").parse(dtInicioSLA);
        } catch (final ParseException e) {
            e.printStackTrace();
        }

        ContratoDTO contratoDto = new ContratoDTO();
        final ContratoServiceEjb contratoService = new ContratoServiceEjb();
        Date dataInicioContrato = null;
        Date dataFimContrato = null;

        if (idContrato != null) {
            contratoDto.setIdContrato(idContrato);
            contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
            dataInicioContrato = contratoDto.getDataContrato();
            dataFimContrato = contratoDto.getDataFimContrato();
        } else {
            return false;
        }

        if (dataFimContrato != null && dataInicioSLA != null && dataInicioContrato != null) {
            final boolean resp = UtilDatas.dataEntreIntervalo(dataInicioSLA, dataInicioContrato, dataFimContrato);
            if (resp) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public Collection consultaResultadosEsperados(final ResultadosEsperadosDTO resultadosEsperadosDTO) throws Exception {
        final ResultadosEsperadosDAO dao = new ResultadosEsperadosDAO();
        final Collection colRetorno = new ArrayList();
        try {
            final Collection col = dao.findByIdServicoContrato(resultadosEsperadosDTO.getIdServicoContrato());
            if (col != null && col.size() > 0) {
                for (final Iterator it = col.iterator(); it.hasNext();) {
                    final ResultadosEsperadosDTO resultados = (ResultadosEsperadosDTO) it.next();
                    if (resultados.getDeleted() != null) {
                        resultados.setDeleted(resultados.getDeleted().trim());
                    }
                    if (resultados.getDeleted() == null || resultados.getDeleted().equalsIgnoreCase("N") || resultados.getDeleted().trim().equals("")) {
                        if (!this.consultaAcordoNivelServicoAtivo(resultados)) {
                            colRetorno.add(resultados);
                        }
                    }
                }
            }
            return colRetorno;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    private boolean consultaAcordoNivelServicoAtivo(final ResultadosEsperadosDTO resultadosEsperadosDTO) throws ServiceException {
        final AcordoNivelServicoDao dao = new AcordoNivelServicoDao();
        AcordoNivelServicoDTO acordoNivelServicoContratoDTO = new AcordoNivelServicoDTO();
        acordoNivelServicoContratoDTO.setIdAcordoNivelServico(resultadosEsperadosDTO.getIdAcordoNivelServico());
        try {
            acordoNivelServicoContratoDTO = (AcordoNivelServicoDTO) dao.restore(acordoNivelServicoContratoDTO);
            final String situacao = acordoNivelServicoContratoDTO.getSituacao();
            if (!situacao.equalsIgnoreCase("A")) {
                return true;
            } else {
                return false;
            }
        } catch (final Exception e) {
            throw new ServiceException(e);
        }

    }

}
