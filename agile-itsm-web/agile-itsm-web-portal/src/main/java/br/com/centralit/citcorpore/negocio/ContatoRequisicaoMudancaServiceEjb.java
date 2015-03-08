package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.integracao.ContatoRequisicaoMudancaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

public class ContatoRequisicaoMudancaServiceEjb extends CrudServiceImpl implements ContatoRequisicaoMudancaService {

    private ContatoRequisicaoMudancaDao dao;

    @Override
    protected ContatoRequisicaoMudancaDao getDao() {
        if (dao == null) {
            dao = new ContatoRequisicaoMudancaDao();
        }
        return dao;
    }

    @Override
    public synchronized IDto create(final IDto model) throws ServiceException, LogicException {
        return super.create(model);
    }

    @Override
    public ContatoRequisicaoMudancaDTO restoreContatosById(final Integer idContatoRequisicaoMudanca) {
        ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDTO = new ContatoRequisicaoMudancaDTO();
        contatoRequisicaoMudancaDTO.setIdContatoRequisicaoMudanca(idContatoRequisicaoMudanca);
        try {
            contatoRequisicaoMudancaDTO = (ContatoRequisicaoMudancaDTO) this.getDao().restore(contatoRequisicaoMudancaDTO);
        } catch (final Exception e) {
            e.printStackTrace();
            System.out.println("Contato Requisicao Liberacão não foi encontrado com esse ID");
        }
        return contatoRequisicaoMudancaDTO;
    }

}
