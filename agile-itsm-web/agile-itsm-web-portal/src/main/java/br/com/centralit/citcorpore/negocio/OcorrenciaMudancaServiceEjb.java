package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.bpm.dto.ItemTrabalhoFluxoDTO;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.OcorrenciaMudancaDao;
import br.com.centralit.citcorpore.util.Enumerados.CategoriaOcorrencia;
import br.com.centralit.citcorpore.util.Enumerados.OrigemOcorrencia;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

import com.google.gson.Gson;

/**
 * @author breno.guimaraes
 *
 */
public class OcorrenciaMudancaServiceEjb extends CrudServiceImpl implements OcorrenciaMudancaService {

    private OcorrenciaMudancaDao dao;

    @Override
    protected OcorrenciaMudancaDao getDao() {
        if (dao == null) {
            dao = new OcorrenciaMudancaDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdRequisicaoMudanca(final Integer idRequisicaoMudanca) throws Exception {
        return this.getDao().findByIdRequisicaoMudanca(idRequisicaoMudanca);
    }

    private static final OcorrenciaMudancaDao ocorrenciaMudancaDao = new OcorrenciaMudancaDao();

    public static OcorrenciaMudancaDTO create(final RequisicaoMudancaDTO requisicaoMudancaDto, final ItemTrabalhoFluxoDTO itemTrabalhoFluxoDto, final String ocorrencia,
            final OrigemOcorrencia origem, final CategoriaOcorrencia categoria, final String informacoesContato, final String descricao, final UsuarioDTO usuarioDTO,
            final int tempo, final JustificativaRequisicaoMudancaDTO justificativaDto, final TransactionControler tc) throws Exception {
        final OcorrenciaMudancaDTO ocorrenciaMudancaDTO = new OcorrenciaMudancaDTO();
        ocorrenciaMudancaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
        ocorrenciaMudancaDTO.setDataregistro(UtilDatas.getDataAtual());
        ocorrenciaMudancaDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
        ocorrenciaMudancaDTO.setTempoGasto(tempo);
        ocorrenciaMudancaDTO.setDescricao(descricao);
        ocorrenciaMudancaDTO.setDataInicio(UtilDatas.getDataAtual());
        ocorrenciaMudancaDTO.setDataFim(UtilDatas.getDataAtual());
        ocorrenciaMudancaDTO.setInformacoesContato(informacoesContato);
        ocorrenciaMudancaDTO.setRegistradopor(usuarioDTO.getLogin());
        try {
            ocorrenciaMudancaDTO.setDadosMudanca(new Gson().toJson(requisicaoMudancaDto));
        } catch (final Exception e) {
            System.out.println("Problema na gravação dos dados da ocorrência da mudança - Objeto GSON");
            // e.printStackTrace();
        }
        ocorrenciaMudancaDTO.setOcorrencia(ocorrencia);
        ocorrenciaMudancaDTO.setOrigem(origem.getSigla().toString());
        ocorrenciaMudancaDTO.setCategoria(categoria.getSigla());
        if (itemTrabalhoFluxoDto != null) {
            ocorrenciaMudancaDTO.setIdItemTrabalho(itemTrabalhoFluxoDto.getIdItemTrabalho());
        }
        if (justificativaDto != null) {
            ocorrenciaMudancaDTO.setIdJustificativa(justificativaDto.getIdJustificativaMudanca());
            ocorrenciaMudancaDTO.setComplementoJustificativa(justificativaDto.getDescricaoJustificativa());
        }

        if (tc != null) {
            ocorrenciaMudancaDao.setTransactionControler(tc);
        }
        return (OcorrenciaMudancaDTO) ocorrenciaMudancaDao.create(ocorrenciaMudancaDTO);
    }

}
