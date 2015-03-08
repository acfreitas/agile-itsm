package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LogImportacaoBIDTO;
import br.com.centralit.citcorpore.integracao.LogImportacaoBIDao;
import br.com.citframework.service.CrudServiceImpl;

public class LogImportacaoBIServiceEjb extends CrudServiceImpl implements LogImportacaoBIService {

    private LogImportacaoBIDao dao;

    @Override
    protected LogImportacaoBIDao getDao() {
        if (dao == null) {
            dao = new LogImportacaoBIDao();
        }
        return dao;
    }

    @Override
    public Collection<LogImportacaoBIDTO> listarLogsByConexaoBI(final Integer idConexaoBI) throws Exception {
        return this.getDao().listarLogsByConexaoBI(idConexaoBI);
    }

    @Override
    public Integer calculaTotalPaginas(final Integer idConexaoBI, final Integer itensPorPagina) throws Exception {
        try {
            return this.getDao().calculaTotalPaginas(idConexaoBI, itensPorPagina);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Collection<LogImportacaoBIDTO> paginacaoLog(final Integer idConexaoBI, final Integer pgAtual, final Integer qtdPaginacao) throws Exception {
        try {
            return this.getDao().paginacaoLog(idConexaoBI, pgAtual, qtdPaginacao);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
