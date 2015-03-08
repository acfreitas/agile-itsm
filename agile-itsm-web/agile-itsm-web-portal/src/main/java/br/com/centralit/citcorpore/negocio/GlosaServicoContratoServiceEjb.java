/**
 *
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.GlosaServicoContratoDTO;
import br.com.centralit.citcorpore.integracao.GlosaServicoContratoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author rodrigo.oliveira
 *
 */
public class GlosaServicoContratoServiceEjb extends CrudServiceImpl implements GlosaServicoContratoService {

    private GlosaServicoContratoDAO dao;

    @Override
    protected GlosaServicoContratoDAO getDao() {
        if (dao == null) {
            dao = new GlosaServicoContratoDAO();
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
    public Integer quantidadeGlosaServico(final Integer idServicoContrato) throws Exception {
        Integer quantidade = 0;
        try {
            final List<GlosaServicoContratoDTO> listResult = (List<GlosaServicoContratoDTO>) this.getDao().quantidadeGlosaServico(idServicoContrato);
            if (listResult != null && listResult.size() > 0) {
                quantidade = listResult.get(0).getQuantidadeGlosa();
            }
            return quantidade;
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void atualizaQuantidadeGlosa(final Integer novaQuantidade, final Integer idServicoContrato) throws Exception {
        try {
            this.getDao().atualizaQuantidadeGlosa(novaQuantidade, idServicoContrato);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
