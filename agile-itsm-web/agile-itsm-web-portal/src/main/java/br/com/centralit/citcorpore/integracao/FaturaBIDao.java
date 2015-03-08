package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.FaturaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FaturaBIDao extends CrudDaoDefaultImpl {
	public FaturaBIDao() {
		super(Constantes.getValue("DATABASE_BI_ALIAS"), null);
	}
	
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idFatura" ,"idFatura", true, true, false, false));
		listFields.add(new Field("idContrato" ,"idContrato", false, false, false, false));
		listFields.add(new Field("dataInicial" ,"dataInicial", false, false, false, false));
		listFields.add(new Field("dataFinal" ,"dataFinal", false, false, false, false));
		listFields.add(new Field("descricaoFatura" ,"descricaoFatura", false, false, false, false));
		listFields.add(new Field("valorCotacaoMoeda" ,"valorCotacaoMoeda", false, false, false, false));
		listFields.add(new Field("dataCriacao" ,"dataCriacao", false, false, false, false));
		listFields.add(new Field("dataUltModificacao" ,"dataUltModificacao", false, false, false, false));
		listFields.add(new Field("valorPrevistoSomaOS" ,"valorPrevistoSomaOS", false, false, false, false));
		listFields.add(new Field("valorSomaGlosasOS" ,"valorSomaGlosasOS", false, false, false, false));
		listFields.add(new Field("valorExecutadoSomaOS" ,"valorExecutadoSomaOS", false, false, false, false));
		listFields.add(new Field("observacao" ,"observacao", false, false, false, false));
		listFields.add(new Field("aprovacaoGestor" ,"aprovacaoGestor", false, false, false, false));
		listFields.add(new Field("aprovacaoFiscal" ,"aprovacaoFiscal", false, false, false, false));
		listFields.add(new Field("saldoPrevisto" ,"saldoPrevisto", false, false, false, false));
		listFields.add(new Field("situacaoFatura" ,"situacaoFatura", false, false, false, false));
		listFields.add(new Field("idConexaoBI" ,"idConexaoBI", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return "Fatura";
	}
	public Collection list() throws PersistenceException {
		return null;
	}

	public Class getBean() {
		return FaturaDTO.class;
	}
	public Collection find(IDto arg0) throws PersistenceException {
		return null;
	}
}
