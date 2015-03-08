package br.com.centralit.citgerencial.generateservices.requisicaoMudanca;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.centralit.citcorpore.bean.RiscoDTO;
import br.com.centralit.citcorpore.integracao.GraficosDao;
import br.com.centralit.citcorpore.negocio.RiscoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados.TipoDate;
import br.com.centralit.citgerencial.bean.GerencialGenerateService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;

/**
 * @author rodrigo.oliveira
 * @since 14/08/2012
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GenerateServiceMudancaPorRisco extends GerencialGenerateService {

	private HashMap novoParametro = new HashMap();

	public List execute(HashMap parametersValues, Collection paramtersDefinition) throws ParseException {

		Set set = parametersValues.entrySet();
		Iterator i = set.iterator();

		while (i.hasNext()) {
			Map.Entry entrada = (Map.Entry) i.next();
			getNovoParametro().put(entrada.getKey(), entrada.getValue());
		}

		String datainicial = (String) getNovoParametro().get("PARAM.dataInicial");
		String datafinal = (String) getNovoParametro().get("PARAM.dataFinal");

		Date datafim = new Date();
		Date datainicio = new Date();

		try {
			datainicio = UtilDatas.convertStringToDate(TipoDate.DATE_DEFAULT, datainicial, super.getLanguage(paramtersDefinition));
			datafim = UtilDatas.convertStringToDate(TipoDate.DATE_DEFAULT, datafinal, super.getLanguage(paramtersDefinition));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(datafim);
		calendar.add(GregorianCalendar.DATE, 1);

		if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.MYSQL)) {
			getNovoParametro().put("PARAM.dataInicial", UtilDatas.convertDateToString(TipoDate.FORMAT_DATABASE, datainicio, super.getLanguage(paramtersDefinition)));
			getNovoParametro().put("PARAM.dataFinal", UtilDatas.convertDateToString(TipoDate.FORMAT_DATABASE, calendar.getTime(), super.getLanguage(paramtersDefinition)));
		} else {
			getNovoParametro().put("PARAM.dataInicial", new java.sql.Date(datainicio.getTime()));
			getNovoParametro().put("PARAM.dataFinal", new java.sql.Date(calendar.getTime().getTime()));
		}

		List col = new ArrayList();
		List listaRetorno = new ArrayList();
		GraficosDao graficosDao = new GraficosDao();

		try {
			RiscoService riscoService = (RiscoService) ServiceLocator.getInstance().getService(RiscoService.class, null);
			ArrayList<RiscoDTO> riscoAtivo = (ArrayList<RiscoDTO>) riscoService.riscoAtivo();

			if (riscoAtivo != null) {
				for (RiscoDTO riscoDTO : riscoAtivo) {
					col = (List) graficosDao.consultaMudancaPorRisco(getNovoParametro(), riscoDTO);
					if (col != null) {
						listaRetorno.add(col.get(0));
						col = new ArrayList();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// resetando parâmetro
		setNovoParametro(null);

		return listaRetorno;
	}

	public HashMap getNovoParametro() {
		return novoParametro;
	}

	public void setNovoParametro(HashMap novoParametro) {
		this.novoParametro = novoParametro;
	}

}
