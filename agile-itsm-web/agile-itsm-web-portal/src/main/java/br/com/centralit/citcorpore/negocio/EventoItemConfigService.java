package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface EventoItemConfigService extends CrudService {

    public ValorDTO pegarCaminhoItemConfig(String nomeBaseItemConfig) throws ServiceException;
    
    /**
     * Traz os dados da Network do item de configuração
     * 
     * @param idItemConfiguracao
     * @return
     * @throws ServiceException
     */
    public Collection<CaracteristicaDTO> pegarNetworksItemConfiguracao(Integer idItemConfiguracao) throws ServiceException;
    
    /**
     * Traz o nome do Sistema Operacional instalado no item de configuração
     * 
     * @param idItemConfiguracao
     * @return String nome do SO
     * @throws ServiceException
     */
    public String pegarSistemaOperacionalItemConfiguracao(Integer idItemConfiguracao) throws ServiceException;

}
