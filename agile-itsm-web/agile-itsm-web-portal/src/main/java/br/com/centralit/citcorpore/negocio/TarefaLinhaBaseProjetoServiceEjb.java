package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import java.util.Iterator;

import br.com.centralit.citcorpore.bean.TarefaLinhaBaseProjetoDTO;
import br.com.centralit.citcorpore.integracao.TarefaLinhaBaseProjetoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceImpl;

public class TarefaLinhaBaseProjetoServiceEjb extends CrudServiceImpl implements TarefaLinhaBaseProjetoService {

    private TarefaLinhaBaseProjetoDao dao;

    @Override
    protected TarefaLinhaBaseProjetoDao getDao() {
        if (dao == null) {
            dao = new TarefaLinhaBaseProjetoDao();
        }
        return dao;
    }

    @Override
    public Collection findByIdLinhaBaseProjeto(final Integer parm) throws Exception {
        try {
            return this.getDao().findByIdLinhaBaseProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteByIdLinhaBaseProjeto(final Integer parm) throws Exception {
        try {
            this.getDao().deleteByIdLinhaBaseProjeto(parm);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findCarteiraByIdEmpregado(final Integer idEmpregado) throws Exception {
        try {
            return this.getDao().findCarteiraByIdEmpregado(idEmpregado);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdTarefaLinhaBaseProjetoMigr(final Integer idTarefaLinhaBaseProjetoMigr) throws Exception {
        try {
            return this.getDao().findByIdTarefaLinhaBaseProjetoMigr(idTarefaLinhaBaseProjetoMigr);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Collection findByIdTarefaLinhaBaseProjetoPai(final Integer idTarefaLinhaBaseProjetoPai) throws Exception {
        try {
            return this.getDao().findByIdTarefaLinhaBaseProjetoPai(idTarefaLinhaBaseProjetoPai);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    public void atualizaInfoProporcionais(final TransactionControler tc, final Integer idTarefaBase) throws Exception {
        // --Faz o calculo do proporcional das tarefas acima.
        final TarefaLinhaBaseProjetoDao tarefaLinhaBaseProjetoDao = new TarefaLinhaBaseProjetoDao();
        tarefaLinhaBaseProjetoDao.setTransactionControler(tc);
        //
        TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoDTO = new TarefaLinhaBaseProjetoDTO();
        tarefaLinhaBaseProjetoDTO.setIdTarefaLinhaBaseProjeto(idTarefaBase);
        tarefaLinhaBaseProjetoDTO = (TarefaLinhaBaseProjetoDTO) tarefaLinhaBaseProjetoDao.restore(tarefaLinhaBaseProjetoDTO);
        if (tarefaLinhaBaseProjetoDTO != null && tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoPai() != null) {
            final Collection colTarefas = tarefaLinhaBaseProjetoDao.calculaValoresTarefasFilhas(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoPai());
            if (colTarefas != null) {
                for (final Iterator it = colTarefas.iterator(); it.hasNext();) {
                    final TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAux = (TarefaLinhaBaseProjetoDTO) it.next();
                    final TarefaLinhaBaseProjetoDTO tarefaLinhaBaseProjetoAtu = new TarefaLinhaBaseProjetoDTO();
                    tarefaLinhaBaseProjetoAtu.setIdTarefaLinhaBaseProjeto(tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoPai());
                    tarefaLinhaBaseProjetoAtu.setCusto(tarefaLinhaBaseProjetoAux.getCusto());
                    tarefaLinhaBaseProjetoAtu.setCustoPerfil(tarefaLinhaBaseProjetoAux.getCustoPerfil());
                    tarefaLinhaBaseProjetoAtu.setTempoTotAlocMinutos(tarefaLinhaBaseProjetoAux.getTempoTotAlocMinutos());
                    tarefaLinhaBaseProjetoAtu.setProgresso(tarefaLinhaBaseProjetoAux.getProgresso());
                    if (tarefaLinhaBaseProjetoAtu.getProgresso() != null) {
                        tarefaLinhaBaseProjetoAtu.setProgresso(tarefaLinhaBaseProjetoAtu.getProgresso().doubleValue() * 100);
                    } else {
                        tarefaLinhaBaseProjetoAtu.setProgresso(new Double(0));
                    }
                    if (tarefaLinhaBaseProjetoAtu.getProgresso().doubleValue() > 100) {
                        tarefaLinhaBaseProjetoAtu.setProgresso(new Double(100));
                    }
                    if (tarefaLinhaBaseProjetoAtu.getProgresso().doubleValue() >= 100) {
                        tarefaLinhaBaseProjetoAtu.setSituacao(TarefaLinhaBaseProjetoDTO.PRONTO);
                    }
                    tarefaLinhaBaseProjetoDao.updateNotNull(tarefaLinhaBaseProjetoAtu);
                }
            }
            if (tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoPai() != null) {
                this.atualizaInfoProporcionais(tc, tarefaLinhaBaseProjetoDTO.getIdTarefaLinhaBaseProjetoPai());
            }
        }
    }

}
