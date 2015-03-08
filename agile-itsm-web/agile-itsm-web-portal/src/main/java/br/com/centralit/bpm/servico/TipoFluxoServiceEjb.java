package br.com.centralit.bpm.servico;

import java.util.Collection;

import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.centralit.bpm.integracao.TipoFluxoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class TipoFluxoServiceEjb extends CrudServiceImpl implements TipoFluxoService {

    private TipoFluxoDao tipoFluxoDao;

    @Override
    protected TipoFluxoDao getDao() {
        if (tipoFluxoDao == null) {
            tipoFluxoDao = new TipoFluxoDao();
        }
        return tipoFluxoDao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        final TipoFluxoDTO tipoFluxoDto = (TipoFluxoDTO) arg0;
        final TipoFluxoDTO tipoFluxoAuxDto = this.getDao().findByNome(tipoFluxoDto.getNomeFluxo());
        if (tipoFluxoAuxDto != null) {
            throw new ServiceException("Já existe um tipo de fluxo com esse nome.");
        }
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        final TipoFluxoDTO tipoFluxoDto = (TipoFluxoDTO) arg0;
        final TipoFluxoDTO tipoFluxoAuxDto = this.getDao().findByNome(tipoFluxoDto.getNomeFluxo());

        if (tipoFluxoAuxDto != null && tipoFluxoAuxDto.getIdTipoFluxo().intValue() != tipoFluxoDto.getIdTipoFluxo().intValue()) {
            throw new ServiceException("Já existe um tipo de fluxo com esse nome.");
        }
    }

    @Override
    public Collection findByIdProcessoNegocio(final Integer idProcessoNegocio) throws Exception {
        return this.getDao().findByIdProcessoNegocio(idProcessoNegocio);
    }
}
