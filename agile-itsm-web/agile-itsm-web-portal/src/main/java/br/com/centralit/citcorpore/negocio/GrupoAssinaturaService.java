package br.com.centralit.citcorpore.negocio;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.GrupoAssinaturaDTO;
import br.com.centralit.citcorpore.bean.ItemGrupoAssinaturaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

/**
 * @author euler.ramos
 *
 */
public interface GrupoAssinaturaService extends CrudService {

    public IDto create(IDto model, ArrayList<ItemGrupoAssinaturaDTO> listaAssinaturas) throws ServiceException, br.com.citframework.excecao.LogicException;

    boolean violaIndiceUnico(GrupoAssinaturaDTO grupoAssinaturaDTO) throws ServiceException;

    public void update(IDto model, ArrayList<ItemGrupoAssinaturaDTO> listaAssinaturas) throws ServiceException, br.com.citframework.excecao.LogicException;

    public boolean naoEstaSendoUtilizado(Integer idGrupoAssinatura) throws ServiceException;

    public Collection geraListaCamposAssinatura(Integer idGrupoAssinatura, HttpServletRequest request) throws Exception;
}
