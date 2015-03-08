package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.CargosDTO;
import br.com.centralit.citcorpore.integracao.CargosDao;
import br.com.centralit.citcorpore.integracao.EmpregadoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.util.UtilDatas;

public class CargosServiceEjb extends CrudServiceImpl implements CargosService {

    private CargosDao dao;

    @Override
    protected CargosDao getDao() {
        if (dao == null) {
            dao = new CargosDao();
        }
        return dao;
    }

    @Override
    public void deletarCargo(final IDto model, final DocumentHTML document) throws ServiceException, Exception {
        final CargosDTO cargosDto = (CargosDTO) model;
        final TransactionControler tc = new TransactionControlerImpl(this.getDao().getAliasDB());
        final EmpregadoDao empregadoDao = new EmpregadoDao();

        try {
            this.validaUpdate(model);
            empregadoDao.setTransactionControler(tc);
            this.getDao().setTransactionControler(tc);
            tc.start();
            if (empregadoDao.verificarSeCargoPossuiEmpregado(cargosDto.getIdCargo())) {
                document.alert(this.i18nMessage("citcorpore.comum.registroNaoPodeSerExcluido"));
                return;
            } else {
                cargosDto.setDataFim(UtilDatas.getDataAtual());
                this.getDao().update(model);
                document.alert(this.i18nMessage("MSG07"));
                tc.commit();
                tc.close();
            }
        } catch (final Exception e) {
            this.rollbackTransaction(tc, e);
        }
    }

    @Override
    public boolean consultarCargosAtivos(final CargosDTO obj) throws Exception {
        return this.getDao().consultarCargosAtivos(obj);
    }

    @Override
    public Collection<CargosDTO> seCargoJaCadastrado(final CargosDTO cargosDTO) throws Exception {
        return this.getDao().seCargoJaCadastrado(cargosDTO);
    }

    @Override
    public Collection<CargosDTO> listarAtivos() throws Exception {
        return this.getDao().listarAtivos();
    }

    @Override
    public Collection findByNome(final String nome) throws Exception {
        return this.getDao().findByNome(nome);
    }

}
