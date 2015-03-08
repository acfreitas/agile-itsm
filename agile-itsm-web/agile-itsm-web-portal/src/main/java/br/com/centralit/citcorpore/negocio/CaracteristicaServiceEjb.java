/**
 * CentralIT - CITCorpore.
 *
 * @author CentralIT
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.CaracteristicaDao;
import br.com.centralit.citcorpore.integracao.CaracteristicaTipoItemConfiguracaoDAO;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * EJB de Característica Service.
 *
 * @author valdoilo.damasceno
 *
 */
public class CaracteristicaServiceEjb extends CrudServiceImpl implements CaracteristicaService {

    private CaracteristicaDao dao;

    @Override
    protected CaracteristicaDao getDao() {
        if (dao == null) {
            dao = new CaracteristicaDao();
        }
        return dao;
    }

    @Override
    public void create(final CaracteristicaDTO caracteristica, final HttpServletRequest request) throws ServiceException, LogicException {
        /*
         * Retirado da rotina para validação da pink elephant caracteristica.setSistema("N");
         * Acrescentado novamente por apresentar erro ao gravar característica. No Oracle, campo não pode ser Null.
         */
        caracteristica.setSistema("N");
        caracteristica.setNome(caracteristica.getNome().replaceAll("[<>]", ""));
        caracteristica.setTag(caracteristica.getTag().replaceAll("[<>]", ""));
        caracteristica.setDataInicio(UtilDatas.getDataAtual());
        caracteristica.setTipo("");
        caracteristica.setIdEmpresa(WebUtil.getIdEmpresa(request));
        super.create(caracteristica);
    }

    @Override
    public void excluirCaracteristica(final CaracteristicaDTO caracteristica) throws ServiceException, Exception {
        if (this.getCaracteristicaTipoItemConfiguracaoDao().existeAssociacaoComCaracteristica(caracteristica.getIdCaracteristica(), null)) {
            throw new LogicException("Característica não pode ser excluída!");
        } else {
            caracteristica.setDataFim(UtilDatas.getDataAtual());
            super.update(caracteristica);
        }
    }

    @SuppressWarnings({ "unchecked"})
    @Override
    public Collection<CaracteristicaDTO> consultarCaracteristicasAtivas(final Integer idTipoItemConfiguracao) throws ServiceException {
        try {
            return this.getDao().consultarCaracteristicasAtivas(idTipoItemConfiguracao);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    @SuppressWarnings({ "unchecked"})
    @Override
    public Collection<CaracteristicaDTO> consultarCaracteristicasAtivas(final Integer idTipoItemConfiguracao, final String[] arrCaracteristicas) throws ServiceException {
        try {
            return this.getDao().consultarCaracteristicasAtivas(idTipoItemConfiguracao, arrCaracteristicas);
        } catch (final Exception e) {
            throw new ServiceException(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see br.com.centralit.citcorpore.negocio.CaracteristicaService# consultarCaracteristicasComValores(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public Collection<CaracteristicaDTO> consultarCaracteristicasComValores(final Integer idTipoItemConfiguracao, final Integer idBaseItemConfiguracao) throws LogicException,
            ServiceException, Exception {
        final Collection<CaracteristicaDTO> caracteristicas = this.consultarCaracteristicasAtivas(idTipoItemConfiguracao);

        for (final CaracteristicaDTO caracteristica : caracteristicas) {
            final ValorDTO valor = this.getValorService().restore(idBaseItemConfiguracao, caracteristica.getIdCaracteristica());

            if (valor != null && valor.getValorStr() != null) {
                caracteristica.setValorString(valor.getValorStr());
            }
        }
        return caracteristicas;
    }

    /*
     * (non-Javadoc)
     * @see br.com.centralit.citcorpore.negocio.CaracteristicaService# consultarCaracteristicasComValores(java.lang.Integer, java.lang.Integer)
     */
    @Override
    public Collection<CaracteristicaDTO> consultarCaracteristicasComValoresItemConfiguracao(final Integer idTipoItemConfiguracao, final Integer idItemConfiguracao)
            throws LogicException, ServiceException, Exception {
        final Collection<CaracteristicaDTO> caracteristicas = this.consultarCaracteristicasAtivas(idTipoItemConfiguracao);
        if (caracteristicas != null) {
            for (final CaracteristicaDTO caracteristica : caracteristicas) {
                final ValorDTO valor = this.getValorService().restoreItemConfiguracao(idItemConfiguracao, caracteristica.getIdCaracteristica());

                if (valor != null && valor.getValorStr() != null) {
                    caracteristica.setValorString(valor.getValorStr());
                }
            }
        }
        return caracteristicas;
    }

    @Override
    public Collection<CaracteristicaDTO> consultarCaracteristicasComValoresItemConfiguracao(final Integer idTipoItemConfiguracao, final Integer idItemConfiguracao,
            final String[] arr) throws LogicException, ServiceException, Exception {
        final Collection<CaracteristicaDTO> caracteristicas = this.consultarCaracteristicasAtivas(idTipoItemConfiguracao, arr);
        if (caracteristicas != null) {
            for (final CaracteristicaDTO caracteristica : caracteristicas) {
                final ValorDTO valor = this.getValorService().restoreItemConfiguracao(idItemConfiguracao, caracteristica.getIdCaracteristica());

                if (valor != null && valor.getValorStr() != null) {
                    caracteristica.setValorString(valor.getValorStr());
                }
            }
        }
        return caracteristicas;
    }

    /**
     * Retorna Service de Valor.
     *
     * @return ValorService
     * @throws ServiceException
     * @throws Exception
     * @author valdoilo.damasceno
     */
    public ValorService getValorService() throws ServiceException, Exception {
        return (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
    }

    /**
     * Retorna DAO de CaracteristicaTipoItemConfiguracao.
     *
     * @return CaracteristicaTipoItemConfiguracaoDAO
     * @author VMD
     */
    public CaracteristicaTipoItemConfiguracaoDAO getCaracteristicaTipoItemConfiguracaoDao() {
        return new CaracteristicaTipoItemConfiguracaoDAO();
    }

    @Override
    public boolean verificarSeCaracteristicaExiste(final CaracteristicaDTO caracteristica) throws PersistenceException {
        return this.getDao().verificarSeCaracteristicaExiste(caracteristica);
    }

}
