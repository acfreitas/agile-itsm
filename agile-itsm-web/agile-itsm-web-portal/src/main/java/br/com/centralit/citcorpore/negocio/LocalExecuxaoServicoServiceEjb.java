package br.com.centralit.citcorpore.negocio;

import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.bean.CategoriaSolucaoDTO;
import br.com.centralit.citcorpore.bean.LocalExecucaoServicosDto;
import br.com.centralit.citcorpore.integracao.LocalExecucaoServicosDao;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class LocalExecuxaoServicoServiceEjb extends CrudServiceImpl implements LocalExecuxaoServicoService {

    private LocalExecucaoServicosDao dao;

    @Override
    protected LocalExecucaoServicosDao getDao() {
        if (dao == null) {
            dao = new LocalExecucaoServicosDao();
        }
        return dao;
    }

    @Override
    public boolean verificarSeLocalExecucaoServicoPossuiServico(final HashMap mapFields) throws PersistenceException, ServiceException {
        final String idlocalexecucaoservico = mapFields.get("IDLOCALEXECUCAOSERVICO").toString().trim();
        try {
            final LocalExecucaoServicosDto dto = new LocalExecucaoServicosDto();
            dto.setIdLocalExecucaoServico(Integer.parseInt(idlocalexecucaoservico));
            return this.getDao().verificarSeLocalExecucaoServicoPossuiServico(dto);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String verificaDescricaoDuplicadaAoCriar(final HashMap mapFields) throws Exception {
        List<CategoriaSolucaoDTO> listaCategoriaSolucao = null;
        final String descricaoCategoria = mapFields.get("NOMELOCALEXECUCAOSERVICO").toString().trim();
        listaCategoriaSolucao = (List<CategoriaSolucaoDTO>) this.getDao().verificaDescricaoDuplicadaAoCriar(descricaoCategoria);
        if (listaCategoriaSolucao == null || listaCategoriaSolucao.isEmpty()) {
            return "1";
        } else {
            return "0";
        }
    }

    @Override
    public String verificaDescricaoDuplicadaAoAlterar(final HashMap mapFields) throws Exception {
        List<CategoriaSolucaoDTO> listaCategoriaSolucao = null;
        final String descricaoCategoria = mapFields.get("NOMELOCALEXECUCAOSERVICO").toString().trim();
        final String idCategoria = mapFields.get("IDLOCALEXECUCAOSERVICO").toString().trim();
        listaCategoriaSolucao = (List<CategoriaSolucaoDTO>) this.getDao().verificaDescricaoDuplicadaAoAlterar(Integer.valueOf(idCategoria), descricaoCategoria);
        if (listaCategoriaSolucao == null || listaCategoriaSolucao.isEmpty()) {
            return "1";
        } else {
            return "0";
        }
    }

}
