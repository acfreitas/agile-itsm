package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoUnidadeDTO;
import br.com.centralit.citcorpore.integracao.TipoUnidadeDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings("rawtypes")
public class TipoUnidadeServiceEjb extends CrudServiceImpl implements TipoUnidadeService {

    private TipoUnidadeDao dao;

    @Override
    protected TipoUnidadeDao getDao() {
        if (dao == null) {
            dao = new TipoUnidadeDao();
        }

        return dao;
    }

    public Collection list(final List ordenacao) throws LogicException, ServiceException {
        return null;
    }

    public Collection list(final String ordenacao) throws LogicException, ServiceException {
        return null;
    }

    @Override
    public IDto create(final IDto model) throws ServiceException, LogicException {
        final TipoUnidadeDTO tipoUnidade = (TipoUnidadeDTO) model;

        tipoUnidade.setDataInicio(UtilDatas.getDataAtual());

        return super.create(tipoUnidade);
    }

    @Override
    public boolean jaExisteUnidadeComMesmoNome(final String nome) {
        final ArrayList<Condition> condicoes = new ArrayList<Condition>();
        condicoes.add(new Condition("nomeTipoUnidade", "=", nome));
        condicoes.add(new Condition("dataFim", "is", null));
        Collection retorno = null;
        try {
            retorno = this.getDao().findByCondition(condicoes, null);
        } catch (final Exception e) {
            e.printStackTrace();
        }
        return retorno == null ? false : true;
    }

}
