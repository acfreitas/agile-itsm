package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ModeloEmailDTO;
import br.com.centralit.citcorpore.integracao.ModeloEmailDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;

@SuppressWarnings("rawtypes")
public class ModeloEmailServiceEjb extends CrudServiceImpl implements ModeloEmailService {

    private ModeloEmailDao dao;

    @Override
    protected ModeloEmailDao getDao() {
        if (dao == null) {
            dao = new ModeloEmailDao();
        }
        return dao;
    }

    @Override
    protected void validaCreate(final Object arg0) throws Exception {
        final ModeloEmailDTO modeloDto = (ModeloEmailDTO) arg0;

        if (modeloDto.getIdentificador() == null) {
            throw new ServiceException(this.i18nMessage("modeloemail.IdentificadorNaoDefinido"));
        }

        if (modeloDto.getIdentificador().indexOf(" ") >= 0) {
            throw new ServiceException(this.i18nMessage("modeloemail.identificadorNaoPodeConterEspacos"));
        }

        final ModeloEmailDTO modeloAuxDto = this.getDao().findByIdentificador(modeloDto.getIdentificador());

        if (modeloAuxDto != null) {
            throw new ServiceException(this.i18nMessage("modeloemail.jaExisteModeloEmailComEsseIdentificador"));
        }
    }

    @Override
    protected void validaUpdate(final Object arg0) throws Exception {
        final ModeloEmailDTO modeloDto = (ModeloEmailDTO) arg0;

        if (modeloDto.getIdentificador().trim() != null) {
            modeloDto.setIdentificador(modeloDto.getIdentificador().trim());
        }

        if (modeloDto.getIdentificador() == null) {
            throw new ServiceException(this.i18nMessage("modeloemail.IdentificadorNaoDefinido"));
        }

        if (modeloDto.getIdentificador().indexOf(" ") >= 0) {
            throw new ServiceException(this.i18nMessage("modeloemail.identificadorNaoPodeConterEspacos"));
        }

        final ModeloEmailDTO modeloAuxDto = this.getDao().findByIdentificador(modeloDto.getIdentificador());

        if (modeloAuxDto != null && modeloAuxDto.getIdModeloEmail().intValue() != modeloDto.getIdModeloEmail().intValue()) {
            throw new ServiceException(this.i18nMessage("modeloemail.jaExisteModeloEmailComEsseIdentificador"));
        }
    }

    @Override
    public Collection getAtivos() throws Exception {
        return this.getDao().getAtivos();
    }

    @Override
    public ModeloEmailDTO findByIdentificador(final String identificador) throws Exception {
        ModeloEmailDTO modeloEmailDTO = null;

        if (identificador != null && !identificador.trim().equals("")) {
            modeloEmailDTO = this.getDao().findByIdentificador(identificador);
        }

        return modeloEmailDTO;
    }

}
