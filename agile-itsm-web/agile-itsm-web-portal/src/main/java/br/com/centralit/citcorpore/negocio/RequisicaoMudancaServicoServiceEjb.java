/**
 * CentralIT - CITSmart
 */
package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

public class RequisicaoMudancaServicoServiceEjb extends CrudServiceImpl implements RequisicaoMudancaServicoService {

    private RequisicaoMudancaServicoDao dao;

    @Override
    protected RequisicaoMudancaServicoDao getDao() {
        if (dao == null) {
            dao = new RequisicaoMudancaServicoDao();
        }
        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<RequisicaoMudancaServicoDTO> listByIdRequisicaoMudanca(final Integer idRequisicaoMudanca) throws ServiceException, Exception {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();

        condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));

        return (ArrayList<RequisicaoMudancaServicoDTO>) this.getDao().findByCondition(condicoes, null);
    }

    /**
     * Retorna o item de relacionamento específico sem a chave primária da tabela. Uma espécie de consulta por chave composta.
     *
     * @param dto
     * @return
     * @throws Exception
     * @throws ServiceException
     */
    @Override
    @SuppressWarnings("unchecked")
    public RequisicaoMudancaServicoDTO restoreByChaveComposta(final RequisicaoMudancaServicoDTO dto) throws ServiceException, Exception {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();

        condicoes.add(new Condition("idRequisicaoMudanca", "=", dto.getIdRequisicaoMudanca()));
        condicoes.add(new Condition("idServico", "=", dto.getIdServico()));

        final ArrayList<RequisicaoMudancaServicoDTO> retorno = (ArrayList<RequisicaoMudancaServicoDTO>) this.getDao().findByCondition(condicoes, null);
        if (retorno != null) {
            return retorno.get(0);
        }

        return null;
    }

}
