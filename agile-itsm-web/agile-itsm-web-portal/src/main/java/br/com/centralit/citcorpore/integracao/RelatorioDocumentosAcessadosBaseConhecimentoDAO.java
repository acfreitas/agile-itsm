package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RelatorioDocumentosAcessadosBaseConhecimentoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class RelatorioDocumentosAcessadosBaseConhecimentoDAO extends CrudDaoDefaultImpl {

	public RelatorioDocumentosAcessadosBaseConhecimentoDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		return null;
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();
		listFields.add(new Field("idBaseConhecimento", "idBaseConhecimento", true, true, false, false));
		listFields.add(new Field("tituloBaseConhecimento", "tituloBaseConhecimento", false, false, false, false));
		listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
		listFields.add(new Field("nomeUsuario", "nomeUsuario", false, false, false, false));
		listFields.add(new Field("qtdeAcessos", "qtdeAcessos", false, false, false, false));
	
		return listFields;
	}

	@Override
	public String getTableName() {
		return null;
	}

	@Override
	public Collection list() throws PersistenceException {
		return null;
	}

	@Override
	public Class getBean() {
		return RelatorioDocumentosAcessadosBaseConhecimentoDTO.class;
	}
	
	
	/**
	 * Metodo para buscar os usuários que acessaram as bases de conhecimento
	 * @param relatorioDocumentosAcessadosBaseConhecimentoDTO
	 * @return
	 */
	
	public Collection<RelatorioDocumentosAcessadosBaseConhecimentoDTO> listarDocumentosAcessadosBaseConhecimentoAnalitico(RelatorioDocumentosAcessadosBaseConhecimentoDTO documentosAcessadosBaseConhecimentoDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			StringBuilder sql = new StringBuilder();
			
			parametro.add(UtilDatas.getSqlDate(documentosAcessadosBaseConhecimentoDTO.getDataInicial()));
			parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(documentosAcessadosBaseConhecimentoDTO.getDataFinal()));
			parametro.add(documentosAcessadosBaseConhecimentoDTO.getIdBaseConhecimento());
			
			listRetorno.add("nomeUsuario");
			listRetorno.add("qtdeAcessos");
				
			sql.append("SELECT usuario.nome, qtdeacessos ");
			sql.append("FROM (SELECT idusuario, sum(contadoracesso) AS qtdeacessos ");
			sql.append("FROM contadoracesso ");
			sql.append("WHERE datahoraacesso BETWEEN ? AND ? and idbaseconhecimento = ? ");
			
			if (documentosAcessadosBaseConhecimentoDTO.getIdUsuario().intValue()>0){
				sql.append("and idusuario = ? ");
				parametro.add(documentosAcessadosBaseConhecimentoDTO.getIdUsuario());
			}
			sql.append("GROUP BY idusuario) cb JOIN usuario ON cb.idusuario = usuario.idusuario ");
			sql.append("ORDER BY qtdeacessos desc, usuario.nome");
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<RelatorioDocumentosAcessadosBaseConhecimentoDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<RelatorioDocumentosAcessadosBaseConhecimentoDTO>() : result);
	}
	
	public ArrayList<RelatorioDocumentosAcessadosBaseConhecimentoDTO> listarDocumentosAcessadosBaseConhecimentoResumido(RelatorioDocumentosAcessadosBaseConhecimentoDTO relatorioDocumentosAcessadosBaseConhecimentoDTO) {
		List result;
		try {
			List resp = new ArrayList();
			List parametro = new ArrayList();
			List listRetorno = new ArrayList();
			
			StringBuilder sql = new StringBuilder();
			
			parametro.add(UtilDatas.getSqlDate(relatorioDocumentosAcessadosBaseConhecimentoDTO.getDataInicial()));
			parametro.add(UtilDatas.getTimeStampComUltimaHoraDoDia(relatorioDocumentosAcessadosBaseConhecimentoDTO.getDataFinal()));
			
			listRetorno.add("idBaseConhecimento");
			listRetorno.add("tituloBaseConhecimento");
			listRetorno.add("qtdeAcessos");
				
			sql.append("SELECT baseconhecimento.idbaseconhecimento, baseconhecimento.titulo, qtdeacessos ");
			sql.append("FROM (SELECT idbaseconhecimento, sum(contadoracesso) AS qtdeacessos ");
			      sql.append("FROM contadoracesso WHERE (datahoraacesso BETWEEN ? AND ?) ");
					if ((relatorioDocumentosAcessadosBaseConhecimentoDTO.getIdUsuario()!=null)&&(relatorioDocumentosAcessadosBaseConhecimentoDTO.getIdUsuario().intValue()>0)){
						sql.append(" and idusuario = ? ");
						parametro.add(relatorioDocumentosAcessadosBaseConhecimentoDTO.getIdUsuario());
					}				
			      sql.append("GROUP BY idbaseconhecimento) cb ");
			           sql.append("JOIN baseconhecimento ON cb.idbaseconhecimento = baseconhecimento.idbaseconhecimento ");
			if ((relatorioDocumentosAcessadosBaseConhecimentoDTO.getOrdenacao()!=null)&&(relatorioDocumentosAcessadosBaseConhecimentoDTO.getOrdenacao().intValue()<2)){
				sql.append(" ORDER BY qtdeacessos DESC, baseconhecimento.titulo");
			} else {
				sql.append(" ORDER BY baseconhecimento.titulo, qtdeacessos DESC");
			}
			
			resp = this.execSQL(sql.toString(), parametro.toArray());
			result = this.engine.listConvertion(getBean(), resp, listRetorno);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result = null;
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}
		return (ArrayList<RelatorioDocumentosAcessadosBaseConhecimentoDTO>) (((result == null)||(result.size()<=0)) ? new ArrayList<RelatorioDocumentosAcessadosBaseConhecimentoDTO>() : result);
	}

	

}