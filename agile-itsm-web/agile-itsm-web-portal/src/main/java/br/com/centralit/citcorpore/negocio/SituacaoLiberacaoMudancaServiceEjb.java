package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.SituacaoLiberacaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.SituacaoLiberacaoMudancaDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilI18N;

public class SituacaoLiberacaoMudancaServiceEjb extends CrudServiceImpl implements SituacaoLiberacaoMudancaService {

    private SituacaoLiberacaoMudancaDAO dao;

    @Override
    protected SituacaoLiberacaoMudancaDAO getDao() {
        if (dao == null) {
            dao = new SituacaoLiberacaoMudancaDAO();
        }
        return dao;
    }

    @Override
    public boolean consultaExistenciaSituacao(final SituacaoLiberacaoMudancaDTO obj) throws Exception {
        return this.getDao().consultarSituacoes(obj);
    }

    @Override
    public void deletarSituacao(final IDto model, final DocumentHTML document, final HttpServletRequest request) throws ServiceException, Exception {
        final SituacaoLiberacaoMudancaDTO situacaoDto = (SituacaoLiberacaoMudancaDTO) model;
        try {

            if (this.getDao().consultarSituacoes(situacaoDto)) {
                document.alert(this.i18nMessage("citcorpore.comum.registroNaoPodeSerExcluido"));
                return;
            } else {
                this.getDao().delete(situacaoDto);
                document.alert(UtilI18N.internacionaliza(request, "MSG07"));
            }
        } catch (final Exception e) {

        }
    }

    @Override
    public Collection<SituacaoLiberacaoMudancaDTO> listAll() throws ServiceException, Exception {
        try {
            return this.getDao().listAll();
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

}
