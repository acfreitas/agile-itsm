/**
 * CentralIT - CITSmart.
 *
 * @author valdoilo.damasceno
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.integracao.CaracteristicaTipoItemConfiguracaoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceImpl;
import br.com.citframework.service.ServiceLocator;

/**
 * EJB de CaracteristicaTipoItemConfiguracao.
 *
 * @author valdoilo.damasceno
 */
public class CaracteristicaTipoItemConfiguracaoServiceEjb extends CrudServiceImpl implements CaracteristicaTipoItemConfiguracaoService {

    private CaracteristicaTipoItemConfiguracaoDAO dao;

    @Override
    protected CaracteristicaTipoItemConfiguracaoDAO getDao() {
        if (dao == null) {
            dao = new CaracteristicaTipoItemConfiguracaoDAO();
        }
        return dao;
    }

    /*
     * (non-Javadoc)
     * @see
     * br.com.centralit.citcorpore.negocio.CaracteristicaTipoItemConfiguracaoService
     * #excluirAssociacaoCaracteristicaTipoItemConfiguracao(java.lang.Integer,
     * java.lang.Integer)
     */
    @Override
    public void excluirAssociacaoCaracteristicaTipoItemConfiguracao(final Integer idTipoItemConfiguracao, final Integer idCaracteristica) throws Exception {
        if (idCaracteristica != null && idCaracteristica.intValue() != 0) {
            this.getDao().excluirAssociacaoCaracteristicaTipoItemConfiguracao(idTipoItemConfiguracao, idCaracteristica);
        } else {
            final Collection<CaracteristicaDTO> caracteristicas = this.getCaracteristicaService().consultarCaracteristicasAtivas(idTipoItemConfiguracao);

            if (caracteristicas != null && !caracteristicas.isEmpty()) {
                for (final CaracteristicaDTO caracteristica : caracteristicas) {
                    this.getDao().excluirAssociacaoCaracteristicaTipoItemConfiguracao(idTipoItemConfiguracao, caracteristica.getIdCaracteristica());
                }
            }
        }
    }

    /**
     * Retorna Service de Caracteristica.
     *
     * @return CaracteristicaService
     * @throws ServiceException
     * @throws Exception
     */
    public CaracteristicaService getCaracteristicaService() throws ServiceException, Exception {
        return (CaracteristicaService) ServiceLocator.getInstance().getService(CaracteristicaService.class, null);
    }

}
