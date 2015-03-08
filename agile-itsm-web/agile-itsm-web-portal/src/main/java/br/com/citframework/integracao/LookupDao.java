package br.com.citframework.integracao;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.dto.LookupDTO;
import br.com.citframework.dto.Usuario;

public abstract class LookupDao extends DaoTransactDefaultImpl {

    public LookupDao(final String alias, final Usuario usuario) {
        super(alias, usuario);
    }

    public abstract List processLookup(final LookupDTO lookupObject, final HttpServletRequest request) throws Exception;

    public String getValueParmLookup(final LookupDTO lookupObject, final int number) {
        if (number == 1) {
            return lookupObject.getParm1();
        } else if (number == 2) {
            return lookupObject.getParm2();
        } else if (number == 3) {
            return lookupObject.getParm3();
        } else if (number == 4) {
            return lookupObject.getParm4();
        } else if (number == 5) {
            return lookupObject.getParm5();
        } else if (number == 6) {
            return lookupObject.getParm6();
        } else if (number == 7) {
            return lookupObject.getParm7();
        } else if (number == 8) {
            return lookupObject.getParm8();
        } else if (number == 9) {
            return lookupObject.getParm9();
        } else if (number == 10) {
            return lookupObject.getParm10();
        }
        return null;
    }

}
