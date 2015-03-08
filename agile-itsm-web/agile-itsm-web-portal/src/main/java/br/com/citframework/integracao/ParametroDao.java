package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.ParametroDTO;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;

public class ParametroDao extends CrudDaoDefaultImpl {

    public ParametroDao() {
        super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(final IDto arg0) throws PersistenceException {
        return null;
    }

    @Override
    public Collection<Field> getFields() {
        final Collection<Field> listFields = new ArrayList<>();
        listFields.add(new Field("modulo", "modulo", true, false, false, false));
        listFields.add(new Field("nomeParametro", "nomeParametro", true, false, false, false));
        listFields.add(new Field("idEmpresa", "idEmpresa", true, false, false, false));
        listFields.add(new Field("valor", "valor", false, false, false, false));
        listFields.add(new Field("detalhamento", "detalhamento", false, false, false, false));
        return listFields;
    }

    @Override
    public String getTableName() {
        return "Parametros";
    }

    @Override
    public Collection list() throws PersistenceException {
        return null;
    }

    @Override
    public Class<ParametroDTO> getBean() {
        return ParametroDTO.class;
    }

    public ParametroDTO getValue(final String moduloParm, final String nomeParametroParm, final Integer idEmpresaParm) throws Exception {
        final ParametroDTO parmDto = new ParametroDTO();
        parmDto.setModulo(moduloParm);
        parmDto.setNomeParametro(nomeParametroParm);
        parmDto.setIdEmpresa(idEmpresaParm);
        return (ParametroDTO) this.restore(parmDto);
    }

    public void setValue(final String moduloParm, final String nomeParametroParm, final Integer idEmpresaParm, final String valorParm, final String detalhamentoParm)
            throws Exception {
        final ParametroDTO parmDto = new ParametroDTO();
        parmDto.setModulo(moduloParm);
        parmDto.setNomeParametro(nomeParametroParm);
        parmDto.setIdEmpresa(idEmpresaParm);
        parmDto.setValor(valorParm);
        parmDto.setDetalhamento(detalhamentoParm);

        final ParametroDTO parmDtoAux = this.getValue(moduloParm, nomeParametroParm, idEmpresaParm);
        if (parmDtoAux != null) { // Se ja existe, atualiza.
            this.update(parmDto);
        } else {
            this.create(parmDto);
        }
    }

}
