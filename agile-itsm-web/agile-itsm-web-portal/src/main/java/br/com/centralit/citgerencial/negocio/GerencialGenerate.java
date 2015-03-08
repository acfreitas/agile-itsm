package br.com.centralit.citgerencial.negocio;

import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citgerencial.bean.GerencialInfoGenerateDTO;
import br.com.centralit.citgerencial.bean.GerencialItemInformationDTO;
import br.com.centralit.citgerencial.bean.GerencialItemPainelDTO;
import br.com.centralit.citgerencial.bean.GerencialOptionsDTO;
import br.com.centralit.citgerencial.bean.GerencialPainelDTO;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudService;

public interface GerencialGenerate extends CrudService {

    Object generate(final GerencialItemInformationDTO gerencialItemDto, final Usuario usuario, final GerencialInfoGenerateDTO infoGenerate,
            final GerencialItemPainelDTO gerencialItemPainelAuxDto, final GerencialPainelDTO gerencialPainelDto, final HttpServletRequest request) throws ServiceException;

    Collection executaSQLOptions(final GerencialOptionsDTO options, final GerencialPainelDTO gerencialPainelDto, final HashMap hashParametros, final Usuario user)
            throws ServiceException;

    Collection executaSQLOptions(final GerencialOptionsDTO options, final Collection listParameters, final HashMap hashParametros, final Usuario user) throws ServiceException;

    Object geraTabelaVazia(final GerencialInfoGenerateDTO infoGenerate, final HttpServletRequest request);

}
