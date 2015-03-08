package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaItemConfiguracaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class RequisicaoMudancaItemConfiguracaoServiceEjb extends CrudServiceImpl implements RequisicaoMudancaItemConfiguracaoService {

    private RequisicaoMudancaItemConfiguracaoDao dao;

    @Override
    protected RequisicaoMudancaItemConfiguracaoDao getDao() {
        if (dao == null) {
            dao = new RequisicaoMudancaItemConfiguracaoDao();
        }
        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listByIdRequisicaoMudanca(final Integer idRequisicaoMudanca) throws ServiceException, Exception {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();

        condicoes.add(new Condition("idRequisicaoMudanca", "=", idRequisicaoMudanca));

        return (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) this.getDao().findByCondition(condicoes, null);
    }

    /**
     * Retorna o item de relacionamento específico sem a chave primária da tabela.
     * Uma espécie de consulta por chave composta.
     *
     * @param dto
     * @return
     * @throws Exception
     * @throws ServiceException
     */
    @Override
    @SuppressWarnings("unchecked")
    public RequisicaoMudancaItemConfiguracaoDTO restoreByChaveComposta(final RequisicaoMudancaItemConfiguracaoDTO dto) throws ServiceException, Exception {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();

        condicoes.add(new Condition("idRequisicaoMudanca", "=", dto.getIdRequisicaoMudanca()));
        condicoes.add(new Condition("idItemConfiguracao", "=", dto.getIdItemConfiguracao()));

        final ArrayList<RequisicaoMudancaItemConfiguracaoDTO> retorno = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) this.getDao().findByCondition(condicoes, null);

        if (retorno != null) {
            return retorno.get(0);
        }

        return null;
    }

    @Override
    public Collection findByIdItemConfiguracao(final Integer parm) throws Exception {
        return this.getDao().findByIdItemConfiguracao(parm);
    }

}
