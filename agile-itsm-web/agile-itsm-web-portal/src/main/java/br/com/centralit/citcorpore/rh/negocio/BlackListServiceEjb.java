package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.citcorpore.rh.bean.BlackListDTO;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.integracao.BlackListDao;
import br.com.centralit.citcorpore.rh.integracao.CandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.ItemHistoricoFuncionalDao;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author david.silva
 *
 */
public class BlackListServiceEjb extends CrudServiceImpl implements BlackListService {

    private BlackListDao dao;

    @Override
    protected BlackListDao getDao() {
        if (dao == null) {
            dao = new BlackListDao();
        }
        return dao;
    }

    @Override
    public boolean isCandidatoBlackList(final Integer idCandidato) throws Exception {
        return this.getDao().isCandidatoListaNegra(idCandidato);
    }

    @Override
    public BlackListDTO retornaBlackList(final Integer idCandidato) throws Exception {
        return this.getDao().retornaBlackList(idCandidato);
    }

    /**
     * Desenvolvedor: David Rodrigues - Data: 26/03/2014 - Horário: 14:36 - ID Citsmart: 0
     *
     * Motivo/Comentário: Adaptação no codido para funcionamento do Historico Funcional (Item Historico Funcional)
     */
    @Override
    public void inserirRegistroHistorico(final Integer idCandidato, final Integer idResponsavel, final boolean listaNegra) throws Exception {
        final ItemHistoricoFuncionalDao itemHistoricoFuncionalDao = new ItemHistoricoFuncionalDao();

        String titulo = "";
        final StringBuilder descricao = new StringBuilder();

        final CandidatoDao candidatoDao = new CandidatoDao();
        CandidatoDTO candidatoDto = new CandidatoDTO();

        candidatoDto = candidatoDao.restoreByID(idCandidato);

        descricao.append("Candidato ");

        if (listaNegra) {
            titulo = "Inclusão na Lista Negra";
            descricao.append(candidatoDto.getNome());
            descricao.append(", foi incluído na Lista Negra");
        } else {
            titulo = "Remoção da Lista Negra";
            descricao.append(candidatoDto.getNome());
            descricao.append(", foi removido da Lista Negra");
        }

        itemHistoricoFuncionalDao.inserirRegistroHistoricoAutomatico(idCandidato, idResponsavel, titulo, descricao.toString(), null);
    }

}
