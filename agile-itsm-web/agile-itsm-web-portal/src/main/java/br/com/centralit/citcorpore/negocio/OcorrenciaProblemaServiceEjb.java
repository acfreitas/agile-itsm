package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.citcorpore.bean.JustificativaProblemaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.integracao.OcorrenciaProblemaDAO;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

@SuppressWarnings("rawtypes")
public class OcorrenciaProblemaServiceEjb extends CrudServiceImpl implements OcorrenciaProblemaService {

    private OcorrenciaProblemaDAO dao;

    @Override
    protected OcorrenciaProblemaDAO getDao() {
        if (dao == null) {
            dao = new OcorrenciaProblemaDAO();
        }
        return dao;
    }

    @Override
    public Collection findByIdProblema(final Integer idProblema) throws Exception {
        return this.getDao().findByIdProblema(idProblema);
    }

    public static OcorrenciaProblemaDTO create(final ProblemaDTO problemaDto, final ItemTrabalhoFluxoDTO itemTrabalhoFluxoDto, final String ocorrencia,
            final OrigemOcorrencia origem, final CategoriaOcorrencia categoria, final String informacoesContato, final String descricao, final String loginUsuario,
            final int tempo, final JustificativaProblemaDTO justificativaDto, final TransactionControler tc) throws Exception {
        final OcorrenciaProblemaDTO ocorrenciaProblemaDTO = new OcorrenciaProblemaDTO();
        ocorrenciaProblemaDTO.setIdProblema(problemaDto.getIdProblema());
        ocorrenciaProblemaDTO.setDataregistro(UtilDatas.getDataAtual());
        ocorrenciaProblemaDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
        ocorrenciaProblemaDTO.setTempoGasto(tempo);
        ocorrenciaProblemaDTO.setDescricao(descricao);
        ocorrenciaProblemaDTO.setDataInicio(UtilDatas.getDataAtual());
        ocorrenciaProblemaDTO.setDataFim(UtilDatas.getDataAtual());
        ocorrenciaProblemaDTO.setInformacoesContato(informacoesContato);
        ocorrenciaProblemaDTO.setRegistradopor(loginUsuario);
        try {
            ocorrenciaProblemaDTO.setDadosProblema(new Gson().toJson(problemaDto));
        } catch (final Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ocorrenciaProblemaDTO.setOcorrencia(ocorrencia);
        ocorrenciaProblemaDTO.setOrigem(origem.getSigla().toString());
        ocorrenciaProblemaDTO.setCategoria(categoria.getSigla());
        if (itemTrabalhoFluxoDto != null) {
            ocorrenciaProblemaDTO.setIdItemTrabalho(itemTrabalhoFluxoDto.getIdItemTrabalho());
        }
        if (justificativaDto != null) {
            ocorrenciaProblemaDTO.setIdJustificativa(justificativaDto.getIdJustificativaProblema());
            ocorrenciaProblemaDTO.setComplementoJustificativa(justificativaDto.getDescricaoProblema());
        }

        final OcorrenciaProblemaDAO ocorrenciaProblemaDao = new OcorrenciaProblemaDAO();
        if (tc != null) {
            ocorrenciaProblemaDao.setTransactionControler(tc);
        }
        return (OcorrenciaProblemaDTO) ocorrenciaProblemaDao.create(ocorrenciaProblemaDTO);
    }

}
