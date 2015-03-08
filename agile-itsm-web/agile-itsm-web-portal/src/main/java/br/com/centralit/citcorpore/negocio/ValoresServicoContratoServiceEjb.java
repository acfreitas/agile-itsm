package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.RelatorioValorServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ValoresServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.ValoresServicoContratoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings({"rawtypes"})
public class ValoresServicoContratoServiceEjb extends CrudServiceImpl implements ValoresServicoContratoService {

    private ValoresServicoContratoDao dao;

    @Override
    protected ValoresServicoContratoDao getDao() {
        if (dao == null) {
            dao = new ValoresServicoContratoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdServicoContrato(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdServicoContrato(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection obterValoresAtivosPorIdServicoContrato(final Integer idServicoContrato) throws ServiceException {
        try {
            return this.getDao().obterValoresAtivosPorIdServicoContrato(idServicoContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean existeAtivos(final Integer idServicoContrato) throws Exception {
        try {
            return this.getDao().existeAtivos(idServicoContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection<RelatorioValorServicoContratoDTO> listaValoresServicoContrato(final ValoresServicoContratoDTO parm) throws Exception {
        try {
            return this.getDao().listaValoresServicoContrato(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
