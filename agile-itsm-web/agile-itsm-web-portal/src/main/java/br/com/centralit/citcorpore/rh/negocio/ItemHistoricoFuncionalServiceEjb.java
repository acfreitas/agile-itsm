package br.com.centralit.citcorpore.rh.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.UsuarioDao;
import br.com.centralit.citcorpore.rh.bean.CandidatoDTO;
import br.com.centralit.citcorpore.rh.bean.HistoricoFuncionalDTO;
import br.com.centralit.citcorpore.rh.integracao.CandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.HistoricoFuncionalDao;
import br.com.centralit.citcorpore.rh.integracao.ItemHistoricoFuncionalDao;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;

/**
 * @author david.silva
 *
 */
@SuppressWarnings("rawtypes")
public class ItemHistoricoFuncionalServiceEjb extends CrudServiceImpl implements ItemHistoricoFuncionalService {

    private ItemHistoricoFuncionalDao dao;

    @Override
    protected ItemHistoricoFuncionalDao getDao() {
        if (dao == null) {
            dao = new ItemHistoricoFuncionalDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdItemHistorico(final Integer idHistorico) throws Exception {
        return this.getDao().findByIdItemHistorico(idHistorico);
    }

    @Override
    public void inserirRegistroHistoricoAutomatico(final Integer idCurriculo, final Integer idResponsavel, final String titulo, String descricao, final TransactionControler tc)
            throws Exception {
        final HistoricoFuncionalDao historicoFuncionalDao = new HistoricoFuncionalDao();

        UsuarioDTO usuarioDto = new UsuarioDTO();
        final UsuarioDao usuarioDao = new UsuarioDao();

        usuarioDto = usuarioDao.restoreByIdEmpregado(idResponsavel);

        descricao += usuarioDto.getNomeUsuario();

        final HistoricoFuncionalDTO historicoFuncionalDto = historicoFuncionalDao.restoreByIdCurriculo(idCurriculo);

        this.getDao().inserirRegistroHistoricoAutomatico(historicoFuncionalDto.getIdCandidato(), idResponsavel, titulo, descricao, tc);
    }

    @Override
    public void inserirRegistroHistoricoAutomaticoClassificacao(final Integer idCurriculo, final Integer idResponsavel, final Integer idSolicitacao, final String classificacao)
            throws Exception {
        final HistoricoFuncionalDao historicoFuncionalDao = new HistoricoFuncionalDao();

        CandidatoDTO candidatoDto = new CandidatoDTO();
        final CandidatoDao candidatoDao = new CandidatoDao();

        UsuarioDTO usuarioDto = new UsuarioDTO();
        final UsuarioDao usuarioDao = new UsuarioDao();

        usuarioDto = usuarioDao.restoreByIdEmpregado(idResponsavel);
        final HistoricoFuncionalDTO historicoFuncionalDto = historicoFuncionalDao.restoreByIdCurriculo(idCurriculo);
        candidatoDto = candidatoDao.restoreByID(idCurriculo);

        if (candidatoDto != null && historicoFuncionalDto != null && usuarioDto != null) {
            final String titulo = "Classificação - Currículo em Processo de Seleção";
            final StringBuilder descricao = new StringBuilder();

            descricao.append("Candidato ");
            descricao.append(candidatoDto.getNome());

            if (classificacao.equalsIgnoreCase("A")) {
                descricao.append(" foi Aprovado ");
            }
            if (classificacao.equalsIgnoreCase("R")) {
                descricao.append(" foi Reprovado ");
            }
            if (classificacao.equalsIgnoreCase("D")) {
                descricao.append(" Desistiu ");
            }

            descricao.append("no Processo de Seleção ");

            if (idSolicitacao != null && idSolicitacao > 0) {
                descricao.append("referente a Requisição Pessoal Nº " + idSolicitacao);
            }

            this.getDao().inserirRegistroHistoricoAutomatico(historicoFuncionalDto.getIdCandidato(), usuarioDto.getIdUsuario(), titulo, descricao.toString(), null);
        }
    }

}
