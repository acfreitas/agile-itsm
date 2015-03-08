package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.JustificacaoEventoHistoricoDTO;
import br.com.centralit.citcorpore.bean.JustificacaoFalhasDTO;
import br.com.centralit.citcorpore.integracao.JustificacaoFalhasDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class JustificacaoFalhasServiceEjb extends CrudServiceImpl implements JustificacaoFalhasService {

    private JustificacaoFalhasDao dao;

    @Override
    protected JustificacaoFalhasDao getDao() {
        if (dao == null) {
            dao = new JustificacaoFalhasDao();
        }
        return dao;
    }

    @Override
    public IDto create(final IDto dto) {
        final JustificacaoFalhasDTO justificacaoDto = (JustificacaoFalhasDTO) dto;
        justificacaoDto.setData(UtilDatas.getDataAtual());
        justificacaoDto.setHora(UtilDatas.getHoraAtual().toString().substring(0, 4));
        IDto retorno = null;
        try {
            retorno = super.create(justificacaoDto);
        } catch (final ServiceException e) {
            e.printStackTrace();
        } catch (final LogicException e) {
            e.printStackTrace();
        }
        return retorno;
    };

    @Override
    public void teste(final IDto dto) {
        this.getDao().findEventosComFalha(dto);
    }

    @Override
    public ArrayList<JustificacaoEventoHistoricoDTO> findEventosComFalha(final JustificacaoFalhasDTO dto) {
        return this.getDao().findEventosComFalha(dto);
    }

}
