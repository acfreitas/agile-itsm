package br.com.centralit.citcorpore.metainfo.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.bean.LookupDTO;
import br.com.citframework.service.CrudService;

public interface LookupService extends CrudService {

    Collection findSimple(final LookupDTO parm) throws Exception;

    String findSimpleString(final LookupDTO parm) throws Exception;

}
