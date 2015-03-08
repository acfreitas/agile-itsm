package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citcorpore.metainfo.bean.TableSearchDTO;
import br.com.citframework.service.CrudService;

/**
 * Service respons�vel pelas consultas das telas que s�o DinamicViews.
 *
 */
@SuppressWarnings("rawtypes")
public interface TableSearchService extends CrudService {

    /**
     * Realiza consulta nas Telas que s�o DinamicViews.
     *
     * @param parm
     * @param tableVinc
     * @param map
     * @return String com os resultados da consulta.
     * @throws Exception
     */
    String findItens(final TableSearchDTO parm, final boolean tableVinc, final Map map, final HttpServletRequest request) throws Exception;

    String getInfoMatriz(final TableSearchDTO parm, final boolean tableVinc, final Map map, final HttpServletRequest request) throws Exception;

}
