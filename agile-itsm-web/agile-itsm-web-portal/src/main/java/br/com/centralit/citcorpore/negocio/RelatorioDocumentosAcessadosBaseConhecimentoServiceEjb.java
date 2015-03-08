package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RelatorioDocumentosAcessadosBaseConhecimentoDTO;
import br.com.centralit.citcorpore.integracao.RelatorioDocumentosAcessadosBaseConhecimentoDAO;
import br.com.centralit.citcorpore.integracao.RelatorioSolicitacaoReabertaDAO;
import br.com.citframework.service.CrudServiceImpl;

public class RelatorioDocumentosAcessadosBaseConhecimentoServiceEjb extends CrudServiceImpl implements RelatorioDocumentosAcessadosBaseConhecimentoService {

    private RelatorioSolicitacaoReabertaDAO dao;
    private RelatorioDocumentosAcessadosBaseConhecimentoDAO baseConhecimentoDAO;

    @Override
    protected RelatorioSolicitacaoReabertaDAO getDao() {
        if (dao == null) {
            dao = new RelatorioSolicitacaoReabertaDAO();
        }
        return dao;
    }

    private RelatorioDocumentosAcessadosBaseConhecimentoDAO getBaseConhecimentoDAO() {
        if (baseConhecimentoDAO == null) {
            baseConhecimentoDAO = new RelatorioDocumentosAcessadosBaseConhecimentoDAO();
        }
        return baseConhecimentoDAO;
    }

    @Override
    public Collection<RelatorioDocumentosAcessadosBaseConhecimentoDTO> listarDocumentosAcessadosBaseConhecimentoAnalitico(
            final RelatorioDocumentosAcessadosBaseConhecimentoDTO relatorioDocumentosAcessadosBaseConhecimentoDTO) {
        return this.getBaseConhecimentoDAO().listarDocumentosAcessadosBaseConhecimentoAnalitico(relatorioDocumentosAcessadosBaseConhecimentoDTO);
    }

    @Override
    public ArrayList<RelatorioDocumentosAcessadosBaseConhecimentoDTO> listarDocumentosAcessadosBaseConhecimentoResumido(
            final RelatorioDocumentosAcessadosBaseConhecimentoDTO relatorioDocumentosAcessadosBaseConhecimentoDTO) {
        return this.getBaseConhecimentoDAO().listarDocumentosAcessadosBaseConhecimentoResumido(relatorioDocumentosAcessadosBaseConhecimentoDTO);
    }

}
