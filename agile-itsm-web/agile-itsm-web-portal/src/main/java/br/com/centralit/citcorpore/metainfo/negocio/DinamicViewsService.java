package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.metainfo.bean.DinamicViewsDTO;
import br.com.citframework.service.CrudService;

public interface DinamicViewsService extends CrudService {

    void save(final UsuarioDTO usuarioDto, final DinamicViewsDTO dinamicViewDto, final Map map, final HttpServletRequest request) throws Exception;

    void saveMatriz(final UsuarioDTO usuarioDto, final DinamicViewsDTO dinamicViewDto, final HttpServletRequest request) throws Exception;

    Collection restoreVisao(final Integer idVisao, final Collection colFilter) throws Exception;

    String internacionalizaScript(final String script, final Locale locale) throws Exception;

    String internacionalizaScript(final String script, final String locale) throws Exception;

    void setInfoSave(final Integer idVisao, final Collection colCamposPK, final Collection colCamposTodos) throws Exception;

    boolean isPKExists(final Collection colCamposPK, final Map hashValores) throws Exception;

}
