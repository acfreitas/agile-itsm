package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.FaturaApuracaoANSDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FaturaApuracaoANSBIDao extends CrudDaoDefaultImpl {

    public FaturaApuracaoANSBIDao() {
    	super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
    }

    public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idFaturaApuracaoANS", "idFaturaApuracaoANS", true, true, false, false));
		listFields.add(new Field("idFatura", "idFatura", false, false, false, false));
		listFields.add(new Field("idAcordoNivelServicoContrato", "idAcordoNivelServicoContrato", false, false, false, false));
		listFields.add(new Field("valorApurado", "valorApurado", false, false, false, false));
		listFields.add(new Field("detalhamento", "detalhamento", false, false, false, false));
		listFields.add(new Field("percentualGlosa", "percentualGlosa", false, false, false, false));
		listFields.add(new Field("valorGlosa", "valorGlosa", false, false, false, false));
		listFields.add(new Field("dataApuracao", "dataApuracao", false, false, false, false));
		listFields.add(new Field("idConexaoBI", "idConexaoBI", false, false, false, false));
		return listFields;
    }

    public String getTableName() {
    	return "FaturaApuracaoANS";
    }

    public Collection list() throws PersistenceException {
    	return null;
    }

    public Class getBean() {
    	return FaturaApuracaoANSDTO.class;
    }

    public Collection find(IDto arg0) throws PersistenceException {
    	return null;
    }

}
