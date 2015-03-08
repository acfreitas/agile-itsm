package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.RequisicaoLiberacaoItemConfiguracaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class RequisicaoLiberacaoItemConfiguracaoServiceEjb extends CrudServiceImpl implements RequisicaoLiberacaoItemConfiguracaoService {

    private RequisicaoLiberacaoItemConfiguracaoDao dao;

    @Override
    protected RequisicaoLiberacaoItemConfiguracaoDao getDao() {
        if (dao == null) {
            dao = new RequisicaoLiberacaoItemConfiguracaoDao();
        }
        return dao;
    }

    @SuppressWarnings("unchecked")
    @Override
    public ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO> listByIdRequisicaoLiberacao(final Integer idRequisicaoLiberacao) throws ServiceException, Exception {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();

        condicoes.add(new Condition("idRequisicaoLiberacao", "=", idRequisicaoLiberacao));

        return (ArrayList<RequisicaoLiberacaoItemConfiguracaoDTO>) this.getDao().findByCondition(condicoes, null);
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

    @Override
    public RequisicaoLiberacaoItemConfiguracaoDTO restoreByChaveComposta(final RequisicaoLiberacaoItemConfiguracaoDTO dto) throws ServiceException, Exception {
        return null;
    }

    @Override
    public Collection findByIdRequisicaoLiberacao(final Integer parm) throws Exception {
        return this.getDao().findByIdRequisicaoLiberacao(parm);
    }

    @Override
    public RequisicaoLiberacaoItemConfiguracaoDTO findByIdReqLiberacao(final Integer parm) throws Exception {
        return this.getDao().findByIdReqLiberacao(parm);
    }

}
