package br.com.centralit.citcorpore.integracao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.ContadorAcessoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ContadorAcessoDao extends CrudDaoDefaultImpl {

	public ContadorAcessoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add("idcontadoracesso");
		return super.find(obj, ordenacao);
	}

	@Override
	public Collection<Field> getFields() {
		Collection<Field> listFields = new ArrayList<>();

		listFields.add(new Field("idcontadoracesso", "idContadorAcesso", true, true, false, false));
		listFields.add(new Field("idusuario", "idUsuario", false, false, false, false));
		listFields.add(new Field("idbaseconhecimento", "idBaseConhecimento", false, false, false, false));
		listFields.add(new Field("datahoraacesso", "dataHoraAcesso", false, false, false, false));
		listFields.add(new Field("contadoracesso", "contadorAcesso", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "contadoracesso";
	}

	@Override
	public Collection list() throws PersistenceException {
		List ordenacao = new ArrayList();
		ordenacao.add("idcontadoracesso");
		return super.list(ordenacao);
	}

	public boolean verificarDataHoraDoContadorDeAcesso(ContadorAcessoDTO contadorDto) throws Exception {
		Long retornoDeHoras;

		boolean gravar = false;

		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT MAX(datahoraacesso) FROM " + getTableName() + " where idusuario = ? and idbaseconhecimento = ? ");
		parametro.add(contadorDto.getIdUsuario());
		parametro.add(contadorDto.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("dataHoraAcesso");
		if (list != null && !list.isEmpty()) {
			Collection<ContadorAcessoDTO> listaContadorAcesso = this.listConvertion(getBean(), list, listRetornor);
			for (ContadorAcessoDTO contador : listaContadorAcesso) {
					if(contador.getDataHoraAcesso()!=null && contadorDto.getDataHoraAcesso() !=null){
						retornoDeHoras = UtilDatas.calculaDiferencaTempoEmMilisegundos(contadorDto.getDataHoraAcesso(), contador.getDataHoraAcesso());
						retornoDeHoras = retornoDeHoras / 3600000;
						if (retornoDeHoras >= 1) {
							gravar = true;
						} else {
							gravar = false;
						}
					}else{
						return true;
					}

			}
			return gravar;
		}
		return true;

	}

	public Integer quantidadesDeAcessoPorBaseConhecimnto(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT count(contadoracesso) FROM "+ getTableName() +" where idbaseconhecimento = ? ");
		parametro.add(baseConhecimentoDTO.getIdBaseConhecimento());

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("contadorCliques");
		if (list != null) {
			Collection<BaseConhecimentoDTO> listaBaseConhecimento = this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
			for (BaseConhecimentoDTO baseConhecimento : listaBaseConhecimento) {
				return baseConhecimento.getContadorCliques();
			}
		}
		return null;

	}

	public Integer quantidadesDeAcessoPorPeriodo(BaseConhecimentoDTO baseConhecimentoDTO) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		List listRetornor = new ArrayList();
		StringBuilder sql = new StringBuilder();
		Integer contadorAcesso = 0;

		sql.append("SELECT count(contadoracesso) FROM "+ getTableName());
		if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("ORACLE")) {
			sql.append(" WHERE trunc(datahoraacesso) BETWEEN to_date(?, 'yyyy-mm-dd') and to_date(?, 'yyyy-mm-dd') ");
		}else{
			sql.append(" WHERE datahoraacesso BETWEEN ? and ? ");
		}

		if (CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("ORACLE")) {
			parametro.add(UtilDatas.dateToSTRWithFormat(baseConhecimentoDTO.getDataInicio(), "yyyy-MM-dd"));
			parametro.add(UtilDatas.dateToSTRWithFormat(baseConhecimentoDTO.getDataFim(), "yyyy-MM-dd"));
		}else{
			parametro.add(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(baseConhecimentoDTO.getDataInicio(), "yyyy-MM-dd") + " 00:00:00"));
			//parametro.add(baseConhecimentoDTO.getDataInicio() + " 00:00:00");
			parametro.add(Timestamp.valueOf(UtilDatas.dateToSTRWithFormat(baseConhecimentoDTO.getDataFim(), "yyyy-MM-dd") + " 23:59:59"));
			//parametro.add(baseConhecimentoDTO.getDataFim() + " 23:59:59");
		}

		list = this.execSQL(sql.toString(), parametro.toArray());
		listRetornor.add("contadorCliques");
		if (list != null) {
			Collection<BaseConhecimentoDTO> listaBaseConhecimento = this.listConvertion(BaseConhecimentoDTO.class, list, listRetornor);
			for (BaseConhecimentoDTO baseConhecimento : listaBaseConhecimento) {
				contadorAcesso = baseConhecimento.getContadorCliques();
			}
		}
		return contadorAcesso;
	}

	@Override
	public Class getBean() {
		return ContadorAcessoDTO.class;
	}

}
